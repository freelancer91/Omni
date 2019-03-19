package omni.impl.seq;
import java.util.function.Predicate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import omni.api.OmniIterator;
import omni.impl.seq.DoubleArrSeq.UncheckedList;
import omni.impl.seq.DoubleArrSeq.CheckedList;
import omni.impl.seq.DoubleArrSeq.UncheckedStack;
import omni.impl.seq.DoubleArrSeq.CheckedStack;
import java.util.ConcurrentModificationException;
import omni.impl.CheckedCollectionTest;
import omni.util.TypeConversionUtil;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.util.EqualityUtil;
import java.util.Random;
import omni.util.doublePredicates;
import omni.util.doubleArrayBuilder;
import java.util.function.DoublePredicate;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class DoubleSeqRemoveIfTest
{
  @Test
  public void testEmptyRemoveIfArrSeqUncheckedStack()
  {
    DoublePredicate filter=doublePredicates.MarkAll.getPred(null,0);
    var seq=new UncheckedStack();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Double>)filter::test));
  }
  @Test
  public void testRemoveIfUncheckedStack()
  {
      {
        var arr=new double[10];
        var seq=new UncheckedStack(10,arr);
        DoublePredicate pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),10);
      }
      {
        var arr=new double[10];
        var seq=new UncheckedStack(10,arr);
        Predicate<? super Double> pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),10);
      }
      {
        var arr=new double[10];
        var seq=new UncheckedStack(10,arr);
        DoublePredicate pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
      {
        var arr=new double[10];
        var seq=new UncheckedStack(10,arr);
        Predicate<? super Double> pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
      {
        int length=100;
        DoublePredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(2)));
        };
        var arr=new double[length];
        var seq=new UncheckedStack(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTodouble(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),length-2);
        for(int i=0,valIndex=0;i<length-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTodouble(valIndex));
        }
      } 
      {
        int length=100;
        Predicate<? super Double> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(2)));
        };
        var arr=new double[length];
        var seq=new UncheckedStack(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTodouble(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),length-2);
        for(int i=0,valIndex=0;i<length-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTodouble(valIndex));
        }
      } 
      {
        int length=100;
        DoublePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(1));
        };
        var arr=new double[length];
        var seq=new UncheckedStack(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTodouble(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTodouble(1));
      } 
      {
        int length=100;
        Predicate<? super Double> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(1));
        };
        var arr=new double[length];
        var seq=new UncheckedStack(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTodouble(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTodouble(1));
      } 
  }
  @Test
  public void testEmptyRemoveIfArrSeqUncheckedList()
  {
    DoublePredicate filter=doublePredicates.MarkAll.getPred(null,0);
    var seq=new UncheckedList();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Double>)filter::test));
  }
  @Test
  public void testRemoveIfUncheckedList()
  {
      {
        var arr=new double[10];
        var seq=new UncheckedList(10,arr);
        DoublePredicate pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),10);
      }
      {
        var arr=new double[10];
        var seq=new UncheckedList(10,arr);
        Predicate<? super Double> pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),10);
      }
      {
        var arr=new double[10];
        var seq=new UncheckedList(10,arr);
        DoublePredicate pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
      {
        var arr=new double[10];
        var seq=new UncheckedList(10,arr);
        Predicate<? super Double> pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
      {
        int length=100;
        DoublePredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(2)));
        };
        var arr=new double[length];
        var seq=new UncheckedList(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTodouble(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),length-2);
        for(int i=0,valIndex=0;i<length-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTodouble(valIndex));
        }
      } 
      {
        int length=100;
        Predicate<? super Double> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(2)));
        };
        var arr=new double[length];
        var seq=new UncheckedList(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTodouble(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),length-2);
        for(int i=0,valIndex=0;i<length-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTodouble(valIndex));
        }
      } 
      {
        int length=100;
        DoublePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(1));
        };
        var arr=new double[length];
        var seq=new UncheckedList(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTodouble(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTodouble(1));
      } 
      {
        int length=100;
        Predicate<? super Double> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(1));
        };
        var arr=new double[length];
        var seq=new UncheckedList(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTodouble(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTodouble(1));
      } 
  }
  @Test
  public void testEmptyRemoveIfArrSeqCheckedStack()
  {
    DoublePredicate filter=doublePredicates.MarkAll.getPred(null,0);
    var seq=new CheckedStack();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Double>)filter::test));
  }
  @Test
  public void testRemoveIfCheckedStack()
  {
      {
        var arr=new double[10];
        var seq=new CheckedStack(10,arr);
        DoublePredicate pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),10);
        Assertions.assertEquals(0,seq.modCount);
      }
      {
        var arr=new double[10];
        var seq=new CheckedStack(10,arr);
        Predicate<? super Double> pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),10);
        Assertions.assertEquals(0,seq.modCount);
      }
      {
        var arr=new double[10];
        var seq=new CheckedStack(10,arr);
        DoublePredicate pred=val->false;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popDouble();
          seq.push(tmp);
        })));
      }
      {
        var arr=new double[10];
        var seq=new CheckedStack(10,arr);
        Predicate<? super Double> pred=val->false;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popDouble();
          seq.push(tmp);
        })));
      }
      {
        var arr=new double[10];
        var seq=new CheckedStack(10,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((DoublePredicate)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new double[10];
        var seq=new CheckedStack(10,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((Predicate<? super Double>)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new double[10];
        var seq=new CheckedStack(10,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((DoublePredicate)(v)->
          {
            var tmp=seq.popDouble();
            seq.push(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new double[10];
        var seq=new CheckedStack(10,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<? super Double>)(v)->
          {
            var tmp=seq.popDouble();
            seq.push(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new double[10];
        var seq=new CheckedStack(10,arr);
        DoublePredicate pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(seq.modCount!=0);
      }
      {
        var arr=new double[10];
        var seq=new CheckedStack(10,arr);
        Predicate<? super Double> pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(seq.modCount!=0);
      }
      {
        var arr=new double[10];
        var seq=new CheckedStack(10,arr);
        DoublePredicate pred=val->true;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popDouble();
          seq.push(tmp);
        })));
      }
      {
        var arr=new double[10];
        var seq=new CheckedStack(10,arr);
        Predicate<? super Double> pred=val->true;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popDouble();
          seq.push(tmp);
        })));
      }
      {
        var arr=new double[10];
        var seq=new CheckedStack(10,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((DoublePredicate)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new double[10];
        var seq=new CheckedStack(10,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((Predicate<? super Double>)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new double[10];
        var seq=new CheckedStack(10,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((DoublePredicate)(v)->
          {
            var tmp=seq.popDouble();
            seq.push(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new double[10];
        var seq=new CheckedStack(10,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<? super Double>)(v)->
          {
            var tmp=seq.popDouble();
            seq.push(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        int length=100;
        DoublePredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(2)));
        };
        var arr=new double[length];
        var seq=new CheckedStack(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTodouble(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),length-2);
        for(int i=0,valIndex=0;i<length-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTodouble(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        int length=100;
        Predicate<? super Double> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(2)));
        };
        var arr=new double[length];
        var seq=new CheckedStack(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTodouble(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),length-2);
        for(int i=0,valIndex=0;i<length-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTodouble(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        int length=100;
        DoublePredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(2)));
        };
        var arr=new double[length];
        var seq=new CheckedStack(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTodouble(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popDouble();
          seq.push(tmp);
        })));
      } 
      {
        int length=100;
        Predicate<? super Double> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(2)));
        };
        var arr=new double[length];
        var seq=new CheckedStack(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTodouble(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popDouble();
          seq.push(tmp);
        })));
      } 
      {
        int length=100;
        DoublePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(1));
        };
        var arr=new double[length];
        var seq=new CheckedStack(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTodouble(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTodouble(1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        int length=100;
        Predicate<? super Double> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(1));
        };
        var arr=new double[length];
        var seq=new CheckedStack(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTodouble(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTodouble(1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        int length=100;
        DoublePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(1));
        };
        var arr=new double[length];
        var seq=new CheckedStack(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTodouble(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popDouble();
          seq.push(tmp);
        })));
      } 
      {
        int length=100;
        Predicate<? super Double> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(1));
        };
        var arr=new double[length];
        var seq=new CheckedStack(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTodouble(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popDouble();
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
      var val=TypeConversionUtil.convertTodouble(i);
      seq.add(val);
    }
    Random rand=new Random(0);
    for(var predicateGenerator:doublePredicates.values())
    {
      for(int m=predicateGenerator.getMLo(),mHi=predicateGenerator.getMHi(),repsBound=predicateGenerator.getNumReps();m<=mHi;m=predicateGenerator.incrementM(m))
      {
        for(int reps=0;reps<repsBound;++reps)
        {
          {
            var seqClone=(OmniStack.OfDouble)seq.clone();
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
            var seqClone=(OmniStack.OfDouble)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(Double.NaN);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(pred));
          }
          {
            var seqClone=(OmniStack.OfDouble)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              if(!seqClone.isEmpty())
              {
                seqClone.pop();
              }
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Double>)pred::test));
          }
          {
            var seqClone=(OmniStack.OfDouble)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(Double.NaN);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Double>)pred::test));
          }
        }
      }
    }
  }
  */
  @Test
  public void testEmptyRemoveIfArrSeqCheckedList()
  {
    DoublePredicate filter=doublePredicates.MarkAll.getPred(null,0);
    var seq=new CheckedList();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Double>)filter::test));
  }
  @Test
  public void testRemoveIfCheckedList()
  {
      {
        var arr=new double[10];
        var seq=new CheckedList(10,arr);
        DoublePredicate pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),10);
        Assertions.assertEquals(0,seq.modCount);
      }
      {
        var arr=new double[10];
        var seq=new CheckedList(10,arr);
        Predicate<? super Double> pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),10);
        Assertions.assertEquals(0,seq.modCount);
      }
      {
        var arr=new double[10];
        var seq=new CheckedList(10,arr);
        DoublePredicate pred=val->false;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeDoubleAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new double[10];
        var seq=new CheckedList(10,arr);
        Predicate<? super Double> pred=val->false;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeDoubleAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new double[10];
        var seq=new CheckedList(10,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((DoublePredicate)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new double[10];
        var seq=new CheckedList(10,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((Predicate<? super Double>)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new double[10];
        var seq=new CheckedList(10,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((DoublePredicate)(v)->
          {
            var tmp=seq.removeDoubleAt(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new double[10];
        var seq=new CheckedList(10,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<? super Double>)(v)->
          {
            var tmp=seq.removeDoubleAt(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new double[10];
        var seq=new CheckedList(10,arr);
        DoublePredicate pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(seq.modCount!=0);
      }
      {
        var arr=new double[10];
        var seq=new CheckedList(10,arr);
        Predicate<? super Double> pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(seq.modCount!=0);
      }
      {
        var arr=new double[10];
        var seq=new CheckedList(10,arr);
        DoublePredicate pred=val->true;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeDoubleAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new double[10];
        var seq=new CheckedList(10,arr);
        Predicate<? super Double> pred=val->true;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeDoubleAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new double[10];
        var seq=new CheckedList(10,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((DoublePredicate)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new double[10];
        var seq=new CheckedList(10,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((Predicate<? super Double>)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new double[10];
        var seq=new CheckedList(10,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((DoublePredicate)(v)->
          {
            var tmp=seq.getDouble(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new double[10];
        var seq=new CheckedList(10,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<? super Double>)(v)->
          {
            var tmp=seq.getDouble(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        int length=100;
        DoublePredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(2)));
        };
        var arr=new double[length];
        var seq=new CheckedList(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTodouble(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),length-2);
        for(int i=0,valIndex=0;i<length-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTodouble(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        int length=100;
        Predicate<? super Double> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(2)));
        };
        var arr=new double[length];
        var seq=new CheckedList(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTodouble(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),length-2);
        for(int i=0,valIndex=0;i<length-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTodouble(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        int length=100;
        DoublePredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(2)));
        };
        var arr=new double[length];
        var seq=new CheckedList(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTodouble(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeDoubleAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        int length=100;
        Predicate<? super Double> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(2)));
        };
        var arr=new double[length];
        var seq=new CheckedList(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTodouble(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeDoubleAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        int length=100;
        DoublePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(1));
        };
        var arr=new double[length];
        var seq=new CheckedList(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTodouble(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTodouble(1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        int length=100;
        Predicate<? super Double> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(1));
        };
        var arr=new double[length];
        var seq=new CheckedList(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTodouble(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTodouble(1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        int length=100;
        DoublePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(1));
        };
        var arr=new double[length];
        var seq=new CheckedList(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTodouble(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeDoubleAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        int length=100;
        Predicate<? super Double> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(1));
        };
        var arr=new double[length];
        var seq=new CheckedList(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTodouble(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeDoubleAt(seq.size()-1);
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
      var val=TypeConversionUtil.convertTodouble(i);
      seq.add(val);
    }
    Random rand=new Random(0);
    for(var predicateGenerator:doublePredicates.values())
    {
      for(int m=predicateGenerator.getMLo(),mHi=predicateGenerator.getMHi(),repsBound=predicateGenerator.getNumReps();m<=mHi;m=predicateGenerator.incrementM(m))
      {
        for(int reps=0;reps<repsBound;++reps)
        {
          {
            var seqClone=(OmniList.OfDouble)seq.clone();
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
            var seqClone=(OmniList.OfDouble)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(Double.NaN);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(pred));
          }
          {
            var seqClone=(OmniList.OfDouble)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              if(!seqClone.isEmpty())
              {
                seqClone.remove(0);
              }
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Double>)pred::test));
          }
          {
            var seqClone=(OmniList.OfDouble)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(Double.NaN);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Double>)pred::test));
          }
        }
      }
    }
  }
  */
  @Test
  public void testEmptyRemoveIfArrSeqUncheckedSubList()
  {
    DoublePredicate filter=doublePredicates.MarkAll.getPred(null,0);
    var root=new UncheckedList();
    var seq=root.subList(0,0);
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Double>)filter::test));
  }
  @Test
  public void testRemoveIfUncheckedSubList()
  {
      {
        var arr=new double[10];
        var root=new UncheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        DoublePredicate pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),10);
      }
      {
        var arr=new double[10];
        var root=new UncheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Predicate<? super Double> pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),10);
      }
      {
        var arr=new double[10];
        var root=new UncheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        DoublePredicate pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
      {
        var arr=new double[10];
        var root=new UncheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Predicate<? super Double> pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
      {
        int length=100;
        DoublePredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(2)));
        };
        var arr=new double[length];
        var root=new UncheckedList(length,arr);
        var subList=root.subList(0,length);
        var seq=subList.subList(0,length);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTodouble(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),length-2);
        for(int i=0,valIndex=0;i<length-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTodouble(valIndex));
        }
      } 
      {
        int length=100;
        Predicate<? super Double> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(2)));
        };
        var arr=new double[length];
        var root=new UncheckedList(length,arr);
        var subList=root.subList(0,length);
        var seq=subList.subList(0,length);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTodouble(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),length-2);
        for(int i=0,valIndex=0;i<length-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTodouble(valIndex));
        }
      } 
      {
        int length=100;
        DoublePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(1));
        };
        var arr=new double[length];
        var root=new UncheckedList(length,arr);
        var subList=root.subList(0,length);
        var seq=subList.subList(0,length);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTodouble(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTodouble(1));
      } 
      {
        int length=100;
        Predicate<? super Double> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(1));
        };
        var arr=new double[length];
        var root=new UncheckedList(length,arr);
        var subList=root.subList(0,length);
        var seq=subList.subList(0,length);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTodouble(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTodouble(1));
      } 
  }
  @Test
  public void testEmptyRemoveIfArrSeqCheckedSubList()
  {
    DoublePredicate filter=doublePredicates.MarkAll.getPred(null,0);
    var root=new CheckedList();
    var seq=root.subList(0,0);
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Double>)filter::test));
  }
  @Test
  public void testRemoveIfCheckedSubList()
  {
      {
        var arr=new double[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        DoublePredicate pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),10);
        Assertions.assertEquals(0,root.modCount);
      }
      {
        var arr=new double[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Predicate<? super Double> pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),10);
        Assertions.assertEquals(0,root.modCount);
      }
      {
        var arr=new double[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        DoublePredicate pred=val->false;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeDoubleAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new double[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Predicate<? super Double> pred=val->false;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeDoubleAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new double[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((DoublePredicate)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new double[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((Predicate<? super Double>)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new double[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((DoublePredicate)(v)->
          {
            var tmp=seq.removeDoubleAt(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new double[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<? super Double>)(v)->
          {
            var tmp=seq.removeDoubleAt(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new double[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        DoublePredicate pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(root.modCount!=0);
      }
      {
        var arr=new double[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Predicate<? super Double> pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(root.modCount!=0);
      }
      {
        var arr=new double[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        DoublePredicate pred=val->true;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeDoubleAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new double[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Predicate<? super Double> pred=val->true;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeDoubleAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new double[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((DoublePredicate)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new double[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((Predicate<? super Double>)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new double[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((DoublePredicate)(v)->
          {
            var tmp=seq.getDouble(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new double[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<? super Double>)(v)->
          {
            var tmp=seq.getDouble(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        int length=100;
        DoublePredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(2)));
        };
        var arr=new double[length];
        var root=new CheckedList(length,arr);
        var subList=root.subList(0,length);
        var seq=subList.subList(0,length);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTodouble(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),length-2);
        for(int i=0,valIndex=0;i<length-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTodouble(valIndex));
        }
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        int length=100;
        Predicate<? super Double> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(2)));
        };
        var arr=new double[length];
        var root=new CheckedList(length,arr);
        var subList=root.subList(0,length);
        var seq=subList.subList(0,length);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTodouble(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),length-2);
        for(int i=0,valIndex=0;i<length-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTodouble(valIndex));
        }
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        int length=100;
        DoublePredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(2)));
        };
        var arr=new double[length];
        var root=new CheckedList(length,arr);
        var subList=root.subList(0,length);
        var seq=subList.subList(0,length);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTodouble(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeDoubleAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        int length=100;
        Predicate<? super Double> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(2)));
        };
        var arr=new double[length];
        var root=new CheckedList(length,arr);
        var subList=root.subList(0,length);
        var seq=subList.subList(0,length);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTodouble(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeDoubleAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        int length=100;
        DoublePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(1));
        };
        var arr=new double[length];
        var root=new CheckedList(length,arr);
        var subList=root.subList(0,length);
        var seq=subList.subList(0,length);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTodouble(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTodouble(1));
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        int length=100;
        Predicate<? super Double> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(1));
        };
        var arr=new double[length];
        var root=new CheckedList(length,arr);
        var subList=root.subList(0,length);
        var seq=subList.subList(0,length);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTodouble(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTodouble(1));
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        int length=100;
        DoublePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(1));
        };
        var arr=new double[length];
        var root=new CheckedList(length,arr);
        var subList=root.subList(0,length);
        var seq=subList.subList(0,length);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTodouble(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeDoubleAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        int length=100;
        Predicate<? super Double> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(1));
        };
        var arr=new double[length];
        var root=new CheckedList(length,arr);
        var subList=root.subList(0,length);
        var seq=subList.subList(0,length);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertTodouble(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeDoubleAt(seq.size()-1);
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
      var val=TypeConversionUtil.convertTodouble(i);
      seq.add(val);
    }
    Random rand=new Random(0);
    for(var predicateGenerator:doublePredicates.values())
    {
      for(int m=predicateGenerator.getMLo(),mHi=predicateGenerator.getMHi(),repsBound=predicateGenerator.getNumReps();m<=mHi;m=predicateGenerator.incrementM(m))
      {
        for(int reps=0;reps<repsBound;++reps)
        {
          {
            var seqClone=(OmniList.OfDouble)seq.clone();
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
            var seqClone=(OmniList.OfDouble)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(Double.NaN);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(pred));
          }
          {
            var seqClone=(OmniList.OfDouble)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              if(!seqClone.isEmpty())
              {
                seqClone.remove(0);
              }
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Double>)pred::test));
          }
          {
            var seqClone=(OmniList.OfDouble)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(Double.NaN);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Double>)pred::test));
          }
        }
      }
    }
  }
  */
}
