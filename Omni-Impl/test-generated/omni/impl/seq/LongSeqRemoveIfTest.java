package omni.impl.seq;
import java.util.function.Predicate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import omni.api.OmniIterator;
import omni.impl.seq.LongArrSeq.UncheckedList;
import omni.impl.seq.LongArrSeq.CheckedList;
import omni.impl.seq.LongArrSeq.UncheckedStack;
import omni.impl.seq.LongArrSeq.CheckedStack;
import java.util.ConcurrentModificationException;
import omni.impl.CheckedCollectionTest;
import omni.util.TypeConversionUtil;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.util.EqualityUtil;
import java.util.Random;
import omni.util.longPredicates;
import omni.util.longArrayBuilder;
import java.util.function.LongPredicate;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class LongSeqRemoveIfTest
{
  @Test
  public void testEmptyRemoveIfArrSeqUncheckedStack()
  {
    LongPredicate filter=longPredicates.MarkAll.getPred(null,0);
    var seq=new UncheckedStack();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Long>)filter::test));
  }
  @Test
  public void testRemoveIfUncheckedStack()
  {
      {
        var arr=new long[10];
        var seq=new UncheckedStack(10,arr);
        LongPredicate pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),10);
      }
      {
        var arr=new long[10];
        var seq=new UncheckedStack(10,arr);
        Predicate<? super Long> pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),10);
      }
      {
        var arr=new long[10];
        var seq=new UncheckedStack(10,arr);
        LongPredicate pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
      {
        var arr=new long[10];
        var seq=new UncheckedStack(10,arr);
        Predicate<? super Long> pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
      {
        int length=100;
        LongPredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(2)));
        };
        var arr=new long[length];
        var seq=new UncheckedStack(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTolong(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),length-2);
        for(int i=0,valIndex=0;i<length-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTolong(valIndex));
        }
      } 
      {
        int length=100;
        Predicate<? super Long> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(2)));
        };
        var arr=new long[length];
        var seq=new UncheckedStack(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTolong(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),length-2);
        for(int i=0,valIndex=0;i<length-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTolong(valIndex));
        }
      } 
      {
        int length=100;
        LongPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(1));
        };
        var arr=new long[length];
        var seq=new UncheckedStack(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTolong(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTolong(1));
      } 
      {
        int length=100;
        Predicate<? super Long> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(1));
        };
        var arr=new long[length];
        var seq=new UncheckedStack(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTolong(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTolong(1));
      } 
  }
  @Test
  public void testEmptyRemoveIfArrSeqUncheckedList()
  {
    LongPredicate filter=longPredicates.MarkAll.getPred(null,0);
    var seq=new UncheckedList();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Long>)filter::test));
  }
  @Test
  public void testRemoveIfUncheckedList()
  {
      {
        var arr=new long[10];
        var seq=new UncheckedList(10,arr);
        LongPredicate pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),10);
      }
      {
        var arr=new long[10];
        var seq=new UncheckedList(10,arr);
        Predicate<? super Long> pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),10);
      }
      {
        var arr=new long[10];
        var seq=new UncheckedList(10,arr);
        LongPredicate pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
      {
        var arr=new long[10];
        var seq=new UncheckedList(10,arr);
        Predicate<? super Long> pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
      {
        int length=100;
        LongPredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(2)));
        };
        var arr=new long[length];
        var seq=new UncheckedList(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTolong(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),length-2);
        for(int i=0,valIndex=0;i<length-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTolong(valIndex));
        }
      } 
      {
        int length=100;
        Predicate<? super Long> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(2)));
        };
        var arr=new long[length];
        var seq=new UncheckedList(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTolong(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),length-2);
        for(int i=0,valIndex=0;i<length-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTolong(valIndex));
        }
      } 
      {
        int length=100;
        LongPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(1));
        };
        var arr=new long[length];
        var seq=new UncheckedList(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTolong(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTolong(1));
      } 
      {
        int length=100;
        Predicate<? super Long> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(1));
        };
        var arr=new long[length];
        var seq=new UncheckedList(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTolong(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTolong(1));
      } 
  }
  @Test
  public void testEmptyRemoveIfArrSeqCheckedStack()
  {
    LongPredicate filter=longPredicates.MarkAll.getPred(null,0);
    var seq=new CheckedStack();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Long>)filter::test));
  }
  @Test
  public void testRemoveIfCheckedStack()
  {
      {
        var arr=new long[10];
        var seq=new CheckedStack(10,arr);
        LongPredicate pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),10);
        Assertions.assertEquals(0,seq.modCount);
      }
      {
        var arr=new long[10];
        var seq=new CheckedStack(10,arr);
        Predicate<? super Long> pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),10);
        Assertions.assertEquals(0,seq.modCount);
      }
      {
        var arr=new long[10];
        var seq=new CheckedStack(10,arr);
        LongPredicate pred=val->false;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popLong();
          seq.push(tmp);
        })));
      }
      {
        var arr=new long[10];
        var seq=new CheckedStack(10,arr);
        Predicate<? super Long> pred=val->false;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popLong();
          seq.push(tmp);
        })));
      }
      {
        var arr=new long[10];
        var seq=new CheckedStack(10,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((LongPredicate)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new long[10];
        var seq=new CheckedStack(10,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((Predicate<? super Long>)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new long[10];
        var seq=new CheckedStack(10,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((LongPredicate)(v)->
          {
            var tmp=seq.popLong();
            seq.push(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new long[10];
        var seq=new CheckedStack(10,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<? super Long>)(v)->
          {
            var tmp=seq.popLong();
            seq.push(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new long[10];
        var seq=new CheckedStack(10,arr);
        LongPredicate pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(seq.modCount!=0);
      }
      {
        var arr=new long[10];
        var seq=new CheckedStack(10,arr);
        Predicate<? super Long> pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(seq.modCount!=0);
      }
      {
        var arr=new long[10];
        var seq=new CheckedStack(10,arr);
        LongPredicate pred=val->true;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popLong();
          seq.push(tmp);
        })));
      }
      {
        var arr=new long[10];
        var seq=new CheckedStack(10,arr);
        Predicate<? super Long> pred=val->true;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popLong();
          seq.push(tmp);
        })));
      }
      {
        var arr=new long[10];
        var seq=new CheckedStack(10,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((LongPredicate)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new long[10];
        var seq=new CheckedStack(10,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((Predicate<? super Long>)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new long[10];
        var seq=new CheckedStack(10,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((LongPredicate)(v)->
          {
            var tmp=seq.popLong();
            seq.push(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new long[10];
        var seq=new CheckedStack(10,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<? super Long>)(v)->
          {
            var tmp=seq.popLong();
            seq.push(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        int length=100;
        LongPredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(2)));
        };
        var arr=new long[length];
        var seq=new CheckedStack(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTolong(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),length-2);
        for(int i=0,valIndex=0;i<length-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTolong(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        int length=100;
        Predicate<? super Long> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(2)));
        };
        var arr=new long[length];
        var seq=new CheckedStack(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTolong(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),length-2);
        for(int i=0,valIndex=0;i<length-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTolong(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        int length=100;
        LongPredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(2)));
        };
        var arr=new long[length];
        var seq=new CheckedStack(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTolong(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popLong();
          seq.push(tmp);
        })));
      } 
      {
        int length=100;
        Predicate<? super Long> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(2)));
        };
        var arr=new long[length];
        var seq=new CheckedStack(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTolong(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popLong();
          seq.push(tmp);
        })));
      } 
      {
        int length=100;
        LongPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(1));
        };
        var arr=new long[length];
        var seq=new CheckedStack(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTolong(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTolong(1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        int length=100;
        Predicate<? super Long> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(1));
        };
        var arr=new long[length];
        var seq=new CheckedStack(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTolong(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTolong(1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        int length=100;
        LongPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(1));
        };
        var arr=new long[length];
        var seq=new CheckedStack(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTolong(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popLong();
          seq.push(tmp);
        })));
      } 
      {
        int length=100;
        Predicate<? super Long> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(1));
        };
        var arr=new long[length];
        var seq=new CheckedStack(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTolong(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popLong();
          seq.push(tmp);
        })));
      } 
  }
  /*
  @Test
  public void testRemoveIfModCheckArrSeqCheckedStack()
  {
    var seq=new CheckedStack();
    for(int i=0;i<1000;++i)
    {
      var val=TypeConversionUtil.convertTolong(i);
      seq.add(val);
    }
    Random rand=new Random(0);
    for(var predicateGenerator:longPredicates.values())
    {
      for(int m=predicateGenerator.getMLo(),mHi=predicateGenerator.getMHi(),repsBound=predicateGenerator.getNumReps();m<=mHi;m=predicateGenerator.incrementM(m))
      {
        for(int reps=0;reps<repsBound;++reps)
        {
          {
            var seqClone=(OmniStack.OfLong)seq.clone();
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
            var seqClone=(OmniStack.OfLong)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(Long.MIN_VALUE);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(pred));
          }
          {
            var seqClone=(OmniStack.OfLong)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              if(!seqClone.isEmpty())
              {
                seqClone.pop();
              }
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Long>)pred::test));
          }
          {
            var seqClone=(OmniStack.OfLong)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(Long.MIN_VALUE);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Long>)pred::test));
          }
        }
      }
    }
  }
  */
  @Test
  public void testEmptyRemoveIfArrSeqCheckedList()
  {
    LongPredicate filter=longPredicates.MarkAll.getPred(null,0);
    var seq=new CheckedList();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Long>)filter::test));
  }
  @Test
  public void testRemoveIfCheckedList()
  {
      {
        var arr=new long[10];
        var seq=new CheckedList(10,arr);
        LongPredicate pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),10);
        Assertions.assertEquals(0,seq.modCount);
      }
      {
        var arr=new long[10];
        var seq=new CheckedList(10,arr);
        Predicate<? super Long> pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),10);
        Assertions.assertEquals(0,seq.modCount);
      }
      {
        var arr=new long[10];
        var seq=new CheckedList(10,arr);
        LongPredicate pred=val->false;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeLongAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new long[10];
        var seq=new CheckedList(10,arr);
        Predicate<? super Long> pred=val->false;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeLongAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new long[10];
        var seq=new CheckedList(10,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((LongPredicate)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new long[10];
        var seq=new CheckedList(10,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((Predicate<? super Long>)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new long[10];
        var seq=new CheckedList(10,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((LongPredicate)(v)->
          {
            var tmp=seq.removeLongAt(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new long[10];
        var seq=new CheckedList(10,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<? super Long>)(v)->
          {
            var tmp=seq.removeLongAt(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new long[10];
        var seq=new CheckedList(10,arr);
        LongPredicate pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(seq.modCount!=0);
      }
      {
        var arr=new long[10];
        var seq=new CheckedList(10,arr);
        Predicate<? super Long> pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(seq.modCount!=0);
      }
      {
        var arr=new long[10];
        var seq=new CheckedList(10,arr);
        LongPredicate pred=val->true;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeLongAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new long[10];
        var seq=new CheckedList(10,arr);
        Predicate<? super Long> pred=val->true;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeLongAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new long[10];
        var seq=new CheckedList(10,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((LongPredicate)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new long[10];
        var seq=new CheckedList(10,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((Predicate<? super Long>)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new long[10];
        var seq=new CheckedList(10,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((LongPredicate)(v)->
          {
            var tmp=seq.getLong(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new long[10];
        var seq=new CheckedList(10,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<? super Long>)(v)->
          {
            var tmp=seq.getLong(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        int length=100;
        LongPredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(2)));
        };
        var arr=new long[length];
        var seq=new CheckedList(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTolong(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),length-2);
        for(int i=0,valIndex=0;i<length-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTolong(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        int length=100;
        Predicate<? super Long> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(2)));
        };
        var arr=new long[length];
        var seq=new CheckedList(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTolong(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),length-2);
        for(int i=0,valIndex=0;i<length-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTolong(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        int length=100;
        LongPredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(2)));
        };
        var arr=new long[length];
        var seq=new CheckedList(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTolong(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeLongAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        int length=100;
        Predicate<? super Long> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(2)));
        };
        var arr=new long[length];
        var seq=new CheckedList(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTolong(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeLongAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        int length=100;
        LongPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(1));
        };
        var arr=new long[length];
        var seq=new CheckedList(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTolong(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTolong(1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        int length=100;
        Predicate<? super Long> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(1));
        };
        var arr=new long[length];
        var seq=new CheckedList(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTolong(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTolong(1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        int length=100;
        LongPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(1));
        };
        var arr=new long[length];
        var seq=new CheckedList(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTolong(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeLongAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        int length=100;
        Predicate<? super Long> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(1));
        };
        var arr=new long[length];
        var seq=new CheckedList(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTolong(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeLongAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
  }
  /*
  @Test
  public void testRemoveIfModCheckArrSeqCheckedList()
  {
    var seq=new CheckedList();
    for(int i=0;i<1000;++i)
    {
      var val=TypeConversionUtil.convertTolong(i);
      seq.add(val);
    }
    Random rand=new Random(0);
    for(var predicateGenerator:longPredicates.values())
    {
      for(int m=predicateGenerator.getMLo(),mHi=predicateGenerator.getMHi(),repsBound=predicateGenerator.getNumReps();m<=mHi;m=predicateGenerator.incrementM(m))
      {
        for(int reps=0;reps<repsBound;++reps)
        {
          {
            var seqClone=(OmniList.OfLong)seq.clone();
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
            var seqClone=(OmniList.OfLong)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(Long.MIN_VALUE);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(pred));
          }
          {
            var seqClone=(OmniList.OfLong)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              if(!seqClone.isEmpty())
              {
                seqClone.remove(0);
              }
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Long>)pred::test));
          }
          {
            var seqClone=(OmniList.OfLong)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(Long.MIN_VALUE);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Long>)pred::test));
          }
        }
      }
    }
  }
  */
  @Test
  public void testEmptyRemoveIfArrSeqUncheckedSubList()
  {
    LongPredicate filter=longPredicates.MarkAll.getPred(null,0);
    var root=new UncheckedList();
    var seq=root.subList(0,0);
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Long>)filter::test));
  }
  @Test
  public void testRemoveIfUncheckedSubList()
  {
      {
        var arr=new long[10];
        var root=new UncheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        LongPredicate pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),10);
      }
      {
        var arr=new long[10];
        var root=new UncheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Predicate<? super Long> pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),10);
      }
      {
        var arr=new long[10];
        var root=new UncheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        LongPredicate pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
      {
        var arr=new long[10];
        var root=new UncheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Predicate<? super Long> pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
      {
        int length=100;
        LongPredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(2)));
        };
        var arr=new long[length];
        var root=new UncheckedList(length,arr);
        var subList=root.subList(0,length);
        var seq=subList.subList(0,length);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTolong(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),length-2);
        for(int i=0,valIndex=0;i<length-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTolong(valIndex));
        }
      } 
      {
        int length=100;
        Predicate<? super Long> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(2)));
        };
        var arr=new long[length];
        var root=new UncheckedList(length,arr);
        var subList=root.subList(0,length);
        var seq=subList.subList(0,length);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTolong(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),length-2);
        for(int i=0,valIndex=0;i<length-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTolong(valIndex));
        }
      } 
      {
        int length=100;
        LongPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(1));
        };
        var arr=new long[length];
        var root=new UncheckedList(length,arr);
        var subList=root.subList(0,length);
        var seq=subList.subList(0,length);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTolong(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTolong(1));
      } 
      {
        int length=100;
        Predicate<? super Long> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(1));
        };
        var arr=new long[length];
        var root=new UncheckedList(length,arr);
        var subList=root.subList(0,length);
        var seq=subList.subList(0,length);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTolong(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTolong(1));
      } 
  }
  @Test
  public void testEmptyRemoveIfArrSeqCheckedSubList()
  {
    LongPredicate filter=longPredicates.MarkAll.getPred(null,0);
    var root=new CheckedList();
    var seq=root.subList(0,0);
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Long>)filter::test));
  }
  @Test
  public void testRemoveIfCheckedSubList()
  {
      {
        var arr=new long[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        LongPredicate pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),10);
        Assertions.assertEquals(0,root.modCount);
      }
      {
        var arr=new long[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Predicate<? super Long> pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),10);
        Assertions.assertEquals(0,root.modCount);
      }
      {
        var arr=new long[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        LongPredicate pred=val->false;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeLongAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new long[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Predicate<? super Long> pred=val->false;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeLongAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new long[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((LongPredicate)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new long[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((Predicate<? super Long>)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new long[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((LongPredicate)(v)->
          {
            var tmp=seq.removeLongAt(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new long[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<? super Long>)(v)->
          {
            var tmp=seq.removeLongAt(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new long[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        LongPredicate pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(root.modCount!=0);
      }
      {
        var arr=new long[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Predicate<? super Long> pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(root.modCount!=0);
      }
      {
        var arr=new long[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        LongPredicate pred=val->true;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeLongAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new long[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Predicate<? super Long> pred=val->true;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeLongAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new long[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((LongPredicate)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new long[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((Predicate<? super Long>)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new long[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((LongPredicate)(v)->
          {
            var tmp=seq.getLong(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new long[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<? super Long>)(v)->
          {
            var tmp=seq.getLong(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        int length=100;
        LongPredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(2)));
        };
        var arr=new long[length];
        var root=new CheckedList(length,arr);
        var subList=root.subList(0,length);
        var seq=subList.subList(0,length);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTolong(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),length-2);
        for(int i=0,valIndex=0;i<length-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTolong(valIndex));
        }
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        int length=100;
        Predicate<? super Long> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(2)));
        };
        var arr=new long[length];
        var root=new CheckedList(length,arr);
        var subList=root.subList(0,length);
        var seq=subList.subList(0,length);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTolong(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),length-2);
        for(int i=0,valIndex=0;i<length-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTolong(valIndex));
        }
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        int length=100;
        LongPredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(2)));
        };
        var arr=new long[length];
        var root=new CheckedList(length,arr);
        var subList=root.subList(0,length);
        var seq=subList.subList(0,length);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTolong(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeLongAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        int length=100;
        Predicate<? super Long> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(2)));
        };
        var arr=new long[length];
        var root=new CheckedList(length,arr);
        var subList=root.subList(0,length);
        var seq=subList.subList(0,length);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTolong(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeLongAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        int length=100;
        LongPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(1));
        };
        var arr=new long[length];
        var root=new CheckedList(length,arr);
        var subList=root.subList(0,length);
        var seq=subList.subList(0,length);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTolong(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTolong(1));
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        int length=100;
        Predicate<? super Long> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(1));
        };
        var arr=new long[length];
        var root=new CheckedList(length,arr);
        var subList=root.subList(0,length);
        var seq=subList.subList(0,length);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTolong(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTolong(1));
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        int length=100;
        LongPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(1));
        };
        var arr=new long[length];
        var root=new CheckedList(length,arr);
        var subList=root.subList(0,length);
        var seq=subList.subList(0,length);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTolong(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeLongAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        int length=100;
        Predicate<? super Long> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(1));
        };
        var arr=new long[length];
        var root=new CheckedList(length,arr);
        var subList=root.subList(0,length);
        var seq=subList.subList(0,length);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTolong(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeLongAt(seq.size()-1);
          seq.add(tmp);
        })));
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
      var val=TypeConversionUtil.convertTolong(i);
      seq.add(val);
    }
    Random rand=new Random(0);
    for(var predicateGenerator:longPredicates.values())
    {
      for(int m=predicateGenerator.getMLo(),mHi=predicateGenerator.getMHi(),repsBound=predicateGenerator.getNumReps();m<=mHi;m=predicateGenerator.incrementM(m))
      {
        for(int reps=0;reps<repsBound;++reps)
        {
          {
            var seqClone=(OmniList.OfLong)seq.clone();
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
            var seqClone=(OmniList.OfLong)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(Long.MIN_VALUE);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(pred));
          }
          {
            var seqClone=(OmniList.OfLong)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              if(!seqClone.isEmpty())
              {
                seqClone.remove(0);
              }
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Long>)pred::test));
          }
          {
            var seqClone=(OmniList.OfLong)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(Long.MIN_VALUE);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Long>)pred::test));
          }
        }
      }
    }
  }
  */
}
