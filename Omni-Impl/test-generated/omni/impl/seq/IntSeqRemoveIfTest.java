package omni.impl.seq;
import java.util.function.Predicate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import omni.api.OmniIterator;
import omni.impl.seq.IntArrSeq.UncheckedList;
import omni.impl.seq.IntArrSeq.CheckedList;
import omni.impl.seq.IntArrSeq.UncheckedStack;
import omni.impl.seq.IntArrSeq.CheckedStack;
import java.util.ConcurrentModificationException;
import omni.impl.CheckedCollectionTest;
import omni.util.TypeConversionUtil;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.util.EqualityUtil;
import java.util.Random;
import omni.util.intPredicates;
import omni.util.intArrayBuilder;
import java.util.function.IntPredicate;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class IntSeqRemoveIfTest
{
  @Test
  public void testEmptyRemoveIfArrSeqUncheckedStack()
  {
    IntPredicate filter=intPredicates.MarkAll.getPred(null,0);
    var seq=new UncheckedStack();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Integer>)filter::test));
  }
  @Test
  public void testRemoveIfUncheckedStack()
  {
      {
        var arr=new int[10];
        var seq=new UncheckedStack(10,arr);
        IntPredicate pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),10);
      }
      {
        var arr=new int[10];
        var seq=new UncheckedStack(10,arr);
        Predicate<? super Integer> pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),10);
      }
      {
        var arr=new int[10];
        var seq=new UncheckedStack(10,arr);
        IntPredicate pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
      {
        var arr=new int[10];
        var seq=new UncheckedStack(10,arr);
        Predicate<? super Integer> pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
      {
        int length=100;
        IntPredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(2)));
        };
        var arr=new int[length];
        var seq=new UncheckedStack(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToint(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),length-2);
        for(int i=0,valIndex=0;i<length-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertToint(valIndex));
        }
      } 
      {
        int length=100;
        Predicate<? super Integer> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(2)));
        };
        var arr=new int[length];
        var seq=new UncheckedStack(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToint(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),length-2);
        for(int i=0,valIndex=0;i<length-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertToint(valIndex));
        }
      } 
      {
        int length=100;
        IntPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(1));
        };
        var arr=new int[length];
        var seq=new UncheckedStack(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToint(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertToint(1));
      } 
      {
        int length=100;
        Predicate<? super Integer> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(1));
        };
        var arr=new int[length];
        var seq=new UncheckedStack(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToint(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertToint(1));
      } 
  }
  @Test
  public void testEmptyRemoveIfArrSeqUncheckedList()
  {
    IntPredicate filter=intPredicates.MarkAll.getPred(null,0);
    var seq=new UncheckedList();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Integer>)filter::test));
  }
  @Test
  public void testRemoveIfUncheckedList()
  {
      {
        var arr=new int[10];
        var seq=new UncheckedList(10,arr);
        IntPredicate pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),10);
      }
      {
        var arr=new int[10];
        var seq=new UncheckedList(10,arr);
        Predicate<? super Integer> pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),10);
      }
      {
        var arr=new int[10];
        var seq=new UncheckedList(10,arr);
        IntPredicate pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
      {
        var arr=new int[10];
        var seq=new UncheckedList(10,arr);
        Predicate<? super Integer> pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
      {
        int length=100;
        IntPredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(2)));
        };
        var arr=new int[length];
        var seq=new UncheckedList(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToint(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),length-2);
        for(int i=0,valIndex=0;i<length-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertToint(valIndex));
        }
      } 
      {
        int length=100;
        Predicate<? super Integer> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(2)));
        };
        var arr=new int[length];
        var seq=new UncheckedList(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToint(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),length-2);
        for(int i=0,valIndex=0;i<length-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertToint(valIndex));
        }
      } 
      {
        int length=100;
        IntPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(1));
        };
        var arr=new int[length];
        var seq=new UncheckedList(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToint(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertToint(1));
      } 
      {
        int length=100;
        Predicate<? super Integer> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(1));
        };
        var arr=new int[length];
        var seq=new UncheckedList(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToint(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertToint(1));
      } 
  }
  @Test
  public void testEmptyRemoveIfArrSeqCheckedStack()
  {
    IntPredicate filter=intPredicates.MarkAll.getPred(null,0);
    var seq=new CheckedStack();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Integer>)filter::test));
  }
  @Test
  public void testRemoveIfCheckedStack()
  {
      {
        var arr=new int[10];
        var seq=new CheckedStack(10,arr);
        IntPredicate pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),10);
        Assertions.assertEquals(0,seq.modCount);
      }
      {
        var arr=new int[10];
        var seq=new CheckedStack(10,arr);
        Predicate<? super Integer> pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),10);
        Assertions.assertEquals(0,seq.modCount);
      }
      {
        var arr=new int[10];
        var seq=new CheckedStack(10,arr);
        IntPredicate pred=val->false;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popInt();
          seq.push(tmp);
        })));
      }
      {
        var arr=new int[10];
        var seq=new CheckedStack(10,arr);
        Predicate<? super Integer> pred=val->false;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popInt();
          seq.push(tmp);
        })));
      }
      {
        var arr=new int[10];
        var seq=new CheckedStack(10,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((IntPredicate)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new int[10];
        var seq=new CheckedStack(10,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((Predicate<? super Integer>)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new int[10];
        var seq=new CheckedStack(10,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((IntPredicate)(v)->
          {
            var tmp=seq.popInt();
            seq.push(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new int[10];
        var seq=new CheckedStack(10,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<? super Integer>)(v)->
          {
            var tmp=seq.popInt();
            seq.push(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new int[10];
        var seq=new CheckedStack(10,arr);
        IntPredicate pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(seq.modCount!=0);
      }
      {
        var arr=new int[10];
        var seq=new CheckedStack(10,arr);
        Predicate<? super Integer> pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(seq.modCount!=0);
      }
      {
        var arr=new int[10];
        var seq=new CheckedStack(10,arr);
        IntPredicate pred=val->true;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popInt();
          seq.push(tmp);
        })));
      }
      {
        var arr=new int[10];
        var seq=new CheckedStack(10,arr);
        Predicate<? super Integer> pred=val->true;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popInt();
          seq.push(tmp);
        })));
      }
      {
        var arr=new int[10];
        var seq=new CheckedStack(10,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((IntPredicate)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new int[10];
        var seq=new CheckedStack(10,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((Predicate<? super Integer>)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new int[10];
        var seq=new CheckedStack(10,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((IntPredicate)(v)->
          {
            var tmp=seq.popInt();
            seq.push(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new int[10];
        var seq=new CheckedStack(10,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<? super Integer>)(v)->
          {
            var tmp=seq.popInt();
            seq.push(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        int length=100;
        IntPredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(2)));
        };
        var arr=new int[length];
        var seq=new CheckedStack(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToint(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),length-2);
        for(int i=0,valIndex=0;i<length-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertToint(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        int length=100;
        Predicate<? super Integer> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(2)));
        };
        var arr=new int[length];
        var seq=new CheckedStack(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToint(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),length-2);
        for(int i=0,valIndex=0;i<length-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertToint(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        int length=100;
        IntPredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(2)));
        };
        var arr=new int[length];
        var seq=new CheckedStack(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToint(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popInt();
          seq.push(tmp);
        })));
      } 
      {
        int length=100;
        Predicate<? super Integer> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(2)));
        };
        var arr=new int[length];
        var seq=new CheckedStack(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToint(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popInt();
          seq.push(tmp);
        })));
      } 
      {
        int length=100;
        IntPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(1));
        };
        var arr=new int[length];
        var seq=new CheckedStack(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToint(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertToint(1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        int length=100;
        Predicate<? super Integer> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(1));
        };
        var arr=new int[length];
        var seq=new CheckedStack(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToint(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertToint(1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        int length=100;
        IntPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(1));
        };
        var arr=new int[length];
        var seq=new CheckedStack(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToint(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popInt();
          seq.push(tmp);
        })));
      } 
      {
        int length=100;
        Predicate<? super Integer> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(1));
        };
        var arr=new int[length];
        var seq=new CheckedStack(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToint(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popInt();
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
      var val=TypeConversionUtil.convertToint(i);
      seq.add(val);
    }
    Random rand=new Random(0);
    for(var predicateGenerator:intPredicates.values())
    {
      for(int m=predicateGenerator.getMLo(),mHi=predicateGenerator.getMHi(),repsBound=predicateGenerator.getNumReps();m<=mHi;m=predicateGenerator.incrementM(m))
      {
        for(int reps=0;reps<repsBound;++reps)
        {
          {
            var seqClone=(OmniStack.OfInt)seq.clone();
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
            var seqClone=(OmniStack.OfInt)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(Integer.MIN_VALUE);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(pred));
          }
          {
            var seqClone=(OmniStack.OfInt)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              if(!seqClone.isEmpty())
              {
                seqClone.pop();
              }
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Integer>)pred::test));
          }
          {
            var seqClone=(OmniStack.OfInt)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(Integer.MIN_VALUE);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Integer>)pred::test));
          }
        }
      }
    }
  }
  */
  @Test
  public void testEmptyRemoveIfArrSeqCheckedList()
  {
    IntPredicate filter=intPredicates.MarkAll.getPred(null,0);
    var seq=new CheckedList();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Integer>)filter::test));
  }
  @Test
  public void testRemoveIfCheckedList()
  {
      {
        var arr=new int[10];
        var seq=new CheckedList(10,arr);
        IntPredicate pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),10);
        Assertions.assertEquals(0,seq.modCount);
      }
      {
        var arr=new int[10];
        var seq=new CheckedList(10,arr);
        Predicate<? super Integer> pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),10);
        Assertions.assertEquals(0,seq.modCount);
      }
      {
        var arr=new int[10];
        var seq=new CheckedList(10,arr);
        IntPredicate pred=val->false;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeIntAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new int[10];
        var seq=new CheckedList(10,arr);
        Predicate<? super Integer> pred=val->false;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeIntAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new int[10];
        var seq=new CheckedList(10,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((IntPredicate)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new int[10];
        var seq=new CheckedList(10,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((Predicate<? super Integer>)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new int[10];
        var seq=new CheckedList(10,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((IntPredicate)(v)->
          {
            var tmp=seq.removeIntAt(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new int[10];
        var seq=new CheckedList(10,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<? super Integer>)(v)->
          {
            var tmp=seq.removeIntAt(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new int[10];
        var seq=new CheckedList(10,arr);
        IntPredicate pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(seq.modCount!=0);
      }
      {
        var arr=new int[10];
        var seq=new CheckedList(10,arr);
        Predicate<? super Integer> pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(seq.modCount!=0);
      }
      {
        var arr=new int[10];
        var seq=new CheckedList(10,arr);
        IntPredicate pred=val->true;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeIntAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new int[10];
        var seq=new CheckedList(10,arr);
        Predicate<? super Integer> pred=val->true;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeIntAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new int[10];
        var seq=new CheckedList(10,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((IntPredicate)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new int[10];
        var seq=new CheckedList(10,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((Predicate<? super Integer>)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new int[10];
        var seq=new CheckedList(10,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((IntPredicate)(v)->
          {
            var tmp=seq.getInt(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new int[10];
        var seq=new CheckedList(10,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<? super Integer>)(v)->
          {
            var tmp=seq.getInt(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        int length=100;
        IntPredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(2)));
        };
        var arr=new int[length];
        var seq=new CheckedList(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToint(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),length-2);
        for(int i=0,valIndex=0;i<length-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertToint(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        int length=100;
        Predicate<? super Integer> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(2)));
        };
        var arr=new int[length];
        var seq=new CheckedList(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToint(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),length-2);
        for(int i=0,valIndex=0;i<length-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertToint(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        int length=100;
        IntPredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(2)));
        };
        var arr=new int[length];
        var seq=new CheckedList(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToint(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeIntAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        int length=100;
        Predicate<? super Integer> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(2)));
        };
        var arr=new int[length];
        var seq=new CheckedList(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToint(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeIntAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        int length=100;
        IntPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(1));
        };
        var arr=new int[length];
        var seq=new CheckedList(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToint(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertToint(1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        int length=100;
        Predicate<? super Integer> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(1));
        };
        var arr=new int[length];
        var seq=new CheckedList(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToint(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertToint(1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        int length=100;
        IntPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(1));
        };
        var arr=new int[length];
        var seq=new CheckedList(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToint(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeIntAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        int length=100;
        Predicate<? super Integer> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(1));
        };
        var arr=new int[length];
        var seq=new CheckedList(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToint(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeIntAt(seq.size()-1);
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
      var val=TypeConversionUtil.convertToint(i);
      seq.add(val);
    }
    Random rand=new Random(0);
    for(var predicateGenerator:intPredicates.values())
    {
      for(int m=predicateGenerator.getMLo(),mHi=predicateGenerator.getMHi(),repsBound=predicateGenerator.getNumReps();m<=mHi;m=predicateGenerator.incrementM(m))
      {
        for(int reps=0;reps<repsBound;++reps)
        {
          {
            var seqClone=(OmniList.OfInt)seq.clone();
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
            var seqClone=(OmniList.OfInt)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(Integer.MIN_VALUE);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(pred));
          }
          {
            var seqClone=(OmniList.OfInt)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              if(!seqClone.isEmpty())
              {
                seqClone.remove(0);
              }
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Integer>)pred::test));
          }
          {
            var seqClone=(OmniList.OfInt)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(Integer.MIN_VALUE);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Integer>)pred::test));
          }
        }
      }
    }
  }
  */
  @Test
  public void testEmptyRemoveIfArrSeqUncheckedSubList()
  {
    IntPredicate filter=intPredicates.MarkAll.getPred(null,0);
    var root=new UncheckedList();
    var seq=root.subList(0,0);
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Integer>)filter::test));
  }
  @Test
  public void testRemoveIfUncheckedSubList()
  {
      {
        var arr=new int[10];
        var root=new UncheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        IntPredicate pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),10);
      }
      {
        var arr=new int[10];
        var root=new UncheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Predicate<? super Integer> pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),10);
      }
      {
        var arr=new int[10];
        var root=new UncheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        IntPredicate pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
      {
        var arr=new int[10];
        var root=new UncheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Predicate<? super Integer> pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
      {
        int length=100;
        IntPredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(2)));
        };
        var arr=new int[length];
        var root=new UncheckedList(length,arr);
        var subList=root.subList(0,length);
        var seq=subList.subList(0,length);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToint(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),length-2);
        for(int i=0,valIndex=0;i<length-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertToint(valIndex));
        }
      } 
      {
        int length=100;
        Predicate<? super Integer> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(2)));
        };
        var arr=new int[length];
        var root=new UncheckedList(length,arr);
        var subList=root.subList(0,length);
        var seq=subList.subList(0,length);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToint(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),length-2);
        for(int i=0,valIndex=0;i<length-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertToint(valIndex));
        }
      } 
      {
        int length=100;
        IntPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(1));
        };
        var arr=new int[length];
        var root=new UncheckedList(length,arr);
        var subList=root.subList(0,length);
        var seq=subList.subList(0,length);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToint(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertToint(1));
      } 
      {
        int length=100;
        Predicate<? super Integer> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(1));
        };
        var arr=new int[length];
        var root=new UncheckedList(length,arr);
        var subList=root.subList(0,length);
        var seq=subList.subList(0,length);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToint(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertToint(1));
      } 
  }
  @Test
  public void testEmptyRemoveIfArrSeqCheckedSubList()
  {
    IntPredicate filter=intPredicates.MarkAll.getPred(null,0);
    var root=new CheckedList();
    var seq=root.subList(0,0);
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Integer>)filter::test));
  }
  @Test
  public void testRemoveIfCheckedSubList()
  {
      {
        var arr=new int[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        IntPredicate pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),10);
        Assertions.assertEquals(0,root.modCount);
      }
      {
        var arr=new int[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Predicate<? super Integer> pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),10);
        Assertions.assertEquals(0,root.modCount);
      }
      {
        var arr=new int[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        IntPredicate pred=val->false;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeIntAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new int[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Predicate<? super Integer> pred=val->false;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeIntAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new int[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((IntPredicate)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new int[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((Predicate<? super Integer>)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new int[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((IntPredicate)(v)->
          {
            var tmp=seq.removeIntAt(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new int[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<? super Integer>)(v)->
          {
            var tmp=seq.removeIntAt(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new int[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        IntPredicate pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(root.modCount!=0);
      }
      {
        var arr=new int[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Predicate<? super Integer> pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(root.modCount!=0);
      }
      {
        var arr=new int[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        IntPredicate pred=val->true;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeIntAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new int[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Predicate<? super Integer> pred=val->true;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeIntAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new int[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((IntPredicate)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new int[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((Predicate<? super Integer>)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new int[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((IntPredicate)(v)->
          {
            var tmp=seq.getInt(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new int[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<? super Integer>)(v)->
          {
            var tmp=seq.getInt(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        int length=100;
        IntPredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(2)));
        };
        var arr=new int[length];
        var root=new CheckedList(length,arr);
        var subList=root.subList(0,length);
        var seq=subList.subList(0,length);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToint(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),length-2);
        for(int i=0,valIndex=0;i<length-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertToint(valIndex));
        }
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        int length=100;
        Predicate<? super Integer> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(2)));
        };
        var arr=new int[length];
        var root=new CheckedList(length,arr);
        var subList=root.subList(0,length);
        var seq=subList.subList(0,length);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToint(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),length-2);
        for(int i=0,valIndex=0;i<length-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertToint(valIndex));
        }
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        int length=100;
        IntPredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(2)));
        };
        var arr=new int[length];
        var root=new CheckedList(length,arr);
        var subList=root.subList(0,length);
        var seq=subList.subList(0,length);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToint(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeIntAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        int length=100;
        Predicate<? super Integer> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(2)));
        };
        var arr=new int[length];
        var root=new CheckedList(length,arr);
        var subList=root.subList(0,length);
        var seq=subList.subList(0,length);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToint(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeIntAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        int length=100;
        IntPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(1));
        };
        var arr=new int[length];
        var root=new CheckedList(length,arr);
        var subList=root.subList(0,length);
        var seq=subList.subList(0,length);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToint(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertToint(1));
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        int length=100;
        Predicate<? super Integer> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(1));
        };
        var arr=new int[length];
        var root=new CheckedList(length,arr);
        var subList=root.subList(0,length);
        var seq=subList.subList(0,length);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToint(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertToint(1));
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        int length=100;
        IntPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(1));
        };
        var arr=new int[length];
        var root=new CheckedList(length,arr);
        var subList=root.subList(0,length);
        var seq=subList.subList(0,length);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToint(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeIntAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        int length=100;
        Predicate<? super Integer> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(1));
        };
        var arr=new int[length];
        var root=new CheckedList(length,arr);
        var subList=root.subList(0,length);
        var seq=subList.subList(0,length);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToint(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeIntAt(seq.size()-1);
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
      var val=TypeConversionUtil.convertToint(i);
      seq.add(val);
    }
    Random rand=new Random(0);
    for(var predicateGenerator:intPredicates.values())
    {
      for(int m=predicateGenerator.getMLo(),mHi=predicateGenerator.getMHi(),repsBound=predicateGenerator.getNumReps();m<=mHi;m=predicateGenerator.incrementM(m))
      {
        for(int reps=0;reps<repsBound;++reps)
        {
          {
            var seqClone=(OmniList.OfInt)seq.clone();
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
            var seqClone=(OmniList.OfInt)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(Integer.MIN_VALUE);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(pred));
          }
          {
            var seqClone=(OmniList.OfInt)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              if(!seqClone.isEmpty())
              {
                seqClone.remove(0);
              }
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Integer>)pred::test));
          }
          {
            var seqClone=(OmniList.OfInt)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(Integer.MIN_VALUE);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Integer>)pred::test));
          }
        }
      }
    }
  }
  */
}
