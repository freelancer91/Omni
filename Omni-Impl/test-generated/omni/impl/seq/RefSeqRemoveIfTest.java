package omni.impl.seq;
import java.util.function.Predicate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import omni.api.OmniIterator;
import omni.impl.seq.RefArrSeq.UncheckedList;
import omni.impl.seq.RefArrSeq.CheckedList;
import omni.impl.seq.RefArrSeq.UncheckedStack;
import omni.impl.seq.RefArrSeq.CheckedStack;
import java.util.ConcurrentModificationException;
import omni.impl.CheckedCollectionTest;
import omni.util.TypeConversionUtil;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.util.EqualityUtil;
import java.util.Random;
import omni.util.IntegerPredicates;
import omni.util.IntegerArrayBuilder;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class RefSeqRemoveIfTest
{
  @Test
  public void testEmptyRemoveIfArrSeqUncheckedStack()
  {
    Predicate filter=IntegerPredicates.MarkAll.getPred(null,0);
    var seq=new UncheckedStack();
    Assertions.assertFalse(seq.removeIf(filter));
  }
  @Test
  public void testRemoveIfUncheckedStack()
  {
      {
        var arr=new Object[10];
        var seq=new UncheckedStack(10,arr);
        Predicate<? super Integer> pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),10);
      }
      {
        var arr=new Object[10];
        var seq=new UncheckedStack(10,arr);
        Predicate<? super Integer> pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
      {
        int length=100;
        Predicate<? super Integer> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertToInteger(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertToInteger(2)));
        };
        var arr=new Object[length];
        var seq=new UncheckedStack(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToInteger(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),length-2);
        for(int i=0,valIndex=0;i<length-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertToInteger(valIndex));
        }
      } 
      {
        int length=100;
        Predicate<? super Integer> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertToInteger(1));
        };
        var arr=new Object[length];
        var seq=new UncheckedStack(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToInteger(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertToInteger(1));
      } 
  }
  @Test
  public void testEmptyRemoveIfArrSeqUncheckedList()
  {
    Predicate filter=IntegerPredicates.MarkAll.getPred(null,0);
    var seq=new UncheckedList();
    Assertions.assertFalse(seq.removeIf(filter));
  }
  @Test
  public void testRemoveIfUncheckedList()
  {
      {
        var arr=new Object[10];
        var seq=new UncheckedList(10,arr);
        Predicate<? super Integer> pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),10);
      }
      {
        var arr=new Object[10];
        var seq=new UncheckedList(10,arr);
        Predicate<? super Integer> pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
      {
        int length=100;
        Predicate<? super Integer> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertToInteger(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertToInteger(2)));
        };
        var arr=new Object[length];
        var seq=new UncheckedList(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToInteger(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),length-2);
        for(int i=0,valIndex=0;i<length-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertToInteger(valIndex));
        }
      } 
      {
        int length=100;
        Predicate<? super Integer> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertToInteger(1));
        };
        var arr=new Object[length];
        var seq=new UncheckedList(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToInteger(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertToInteger(1));
      } 
  }
  @Test
  public void testEmptyRemoveIfArrSeqCheckedStack()
  {
    Predicate filter=IntegerPredicates.MarkAll.getPred(null,0);
    var seq=new CheckedStack();
    Assertions.assertFalse(seq.removeIf(filter));
  }
  @Test
  public void testRemoveIfCheckedStack()
  {
      {
        var arr=new Object[10];
        var seq=new CheckedStack(10,arr);
        Predicate<? super Integer> pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),10);
        Assertions.assertEquals(0,seq.modCount);
      }
      {
        var arr=new Object[10];
        var seq=new CheckedStack(10,arr);
        Predicate<? super Integer> pred=val->false;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.pop();
          seq.push(tmp);
        })));
      }
      {
        var arr=new Object[10];
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
        var arr=new Object[10];
        var seq=new CheckedStack(10,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<? super Integer>)(v)->
          {
            var tmp=seq.pop();
            seq.push(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new Object[10];
        var seq=new CheckedStack(10,arr);
        Predicate<? super Integer> pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(seq.modCount!=0);
      }
      {
        var arr=new Object[10];
        var seq=new CheckedStack(10,arr);
        Predicate<? super Integer> pred=val->true;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.pop();
          seq.push(tmp);
        })));
      }
      {
        var arr=new Object[10];
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
        var arr=new Object[10];
        var seq=new CheckedStack(10,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<? super Integer>)(v)->
          {
            var tmp=seq.pop();
            seq.push(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        int length=100;
        Predicate<? super Integer> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertToInteger(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertToInteger(2)));
        };
        var arr=new Object[length];
        var seq=new CheckedStack(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToInteger(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),length-2);
        for(int i=0,valIndex=0;i<length-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertToInteger(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        int length=100;
        Predicate<? super Integer> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertToInteger(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertToInteger(2)));
        };
        var arr=new Object[length];
        var seq=new CheckedStack(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToInteger(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.pop();
          seq.push(tmp);
        })));
      } 
      {
        int length=100;
        Predicate<? super Integer> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertToInteger(1));
        };
        var arr=new Object[length];
        var seq=new CheckedStack(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToInteger(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertToInteger(1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        int length=100;
        Predicate<? super Integer> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertToInteger(1));
        };
        var arr=new Object[length];
        var seq=new CheckedStack(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToInteger(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.pop();
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
      var val=TypeConversionUtil.convertToInteger(i);
      seq.add(val);
    }
    Random rand=new Random(0);
    for(var predicateGenerator:IntegerPredicates.values())
    {
      for(int m=predicateGenerator.getMLo(),mHi=predicateGenerator.getMHi(),repsBound=predicateGenerator.getNumReps();m<=mHi;m=predicateGenerator.incrementM(m))
      {
        for(int reps=0;reps<repsBound;++reps)
        {
          {
            var seqClone=(OmniStack.OfRef)seq.clone();
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
            var seqClone=(OmniStack.OfRef)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(null);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(pred));
          }
        }
      }
    }
  }
  */
  @Test
  public void testEmptyRemoveIfArrSeqCheckedList()
  {
    Predicate filter=IntegerPredicates.MarkAll.getPred(null,0);
    var seq=new CheckedList();
    Assertions.assertFalse(seq.removeIf(filter));
  }
  @Test
  public void testRemoveIfCheckedList()
  {
      {
        var arr=new Object[10];
        var seq=new CheckedList(10,arr);
        Predicate<? super Integer> pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),10);
        Assertions.assertEquals(0,seq.modCount);
      }
      {
        var arr=new Object[10];
        var seq=new CheckedList(10,arr);
        Predicate<? super Integer> pred=val->false;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.remove(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new Object[10];
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
        var arr=new Object[10];
        var seq=new CheckedList(10,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<? super Integer>)(v)->
          {
            var tmp=seq.remove(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new Object[10];
        var seq=new CheckedList(10,arr);
        Predicate<? super Integer> pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(seq.modCount!=0);
      }
      {
        var arr=new Object[10];
        var seq=new CheckedList(10,arr);
        Predicate<? super Integer> pred=val->true;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.remove(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new Object[10];
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
        var arr=new Object[10];
        var seq=new CheckedList(10,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<? super Integer>)(v)->
          {
            var tmp=seq.get(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        int length=100;
        Predicate<? super Integer> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertToInteger(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertToInteger(2)));
        };
        var arr=new Object[length];
        var seq=new CheckedList(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToInteger(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),length-2);
        for(int i=0,valIndex=0;i<length-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertToInteger(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        int length=100;
        Predicate<? super Integer> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertToInteger(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertToInteger(2)));
        };
        var arr=new Object[length];
        var seq=new CheckedList(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToInteger(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.remove(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        int length=100;
        Predicate<? super Integer> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertToInteger(1));
        };
        var arr=new Object[length];
        var seq=new CheckedList(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToInteger(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertToInteger(1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        int length=100;
        Predicate<? super Integer> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertToInteger(1));
        };
        var arr=new Object[length];
        var seq=new CheckedList(length,arr);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToInteger(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.remove(seq.size()-1);
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
      var val=TypeConversionUtil.convertToInteger(i);
      seq.add(val);
    }
    Random rand=new Random(0);
    for(var predicateGenerator:IntegerPredicates.values())
    {
      for(int m=predicateGenerator.getMLo(),mHi=predicateGenerator.getMHi(),repsBound=predicateGenerator.getNumReps();m<=mHi;m=predicateGenerator.incrementM(m))
      {
        for(int reps=0;reps<repsBound;++reps)
        {
          {
            var seqClone=(OmniList.OfRef)seq.clone();
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
            var seqClone=(OmniList.OfRef)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(null);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(pred));
          }
        }
      }
    }
  }
  */
  @Test
  public void testEmptyRemoveIfArrSeqUncheckedSubList()
  {
    Predicate filter=IntegerPredicates.MarkAll.getPred(null,0);
    var root=new UncheckedList();
    var seq=root.subList(0,0);
    Assertions.assertFalse(seq.removeIf(filter));
  }
  @Test
  public void testRemoveIfUncheckedSubList()
  {
      {
        var arr=new Object[10];
        var root=new UncheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Predicate<? super Integer> pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),10);
      }
      {
        var arr=new Object[10];
        var root=new UncheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Predicate<? super Integer> pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
      {
        int length=100;
        Predicate<? super Integer> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertToInteger(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertToInteger(2)));
        };
        var arr=new Object[length];
        var root=new UncheckedList(length,arr);
        var subList=root.subList(0,length);
        var seq=subList.subList(0,length);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToInteger(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),length-2);
        for(int i=0,valIndex=0;i<length-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertToInteger(valIndex));
        }
      } 
      {
        int length=100;
        Predicate<? super Integer> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertToInteger(1));
        };
        var arr=new Object[length];
        var root=new UncheckedList(length,arr);
        var subList=root.subList(0,length);
        var seq=subList.subList(0,length);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToInteger(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertToInteger(1));
      } 
  }
  @Test
  public void testEmptyRemoveIfArrSeqCheckedSubList()
  {
    Predicate filter=IntegerPredicates.MarkAll.getPred(null,0);
    var root=new CheckedList();
    var seq=root.subList(0,0);
    Assertions.assertFalse(seq.removeIf(filter));
  }
  @Test
  public void testRemoveIfCheckedSubList()
  {
      {
        var arr=new Object[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Predicate<? super Integer> pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),10);
        Assertions.assertEquals(0,root.modCount);
      }
      {
        var arr=new Object[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Predicate<? super Integer> pred=val->false;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.remove(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new Object[10];
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
        var arr=new Object[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<? super Integer>)(v)->
          {
            var tmp=seq.remove(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new Object[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Predicate<? super Integer> pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(root.modCount!=0);
      }
      {
        var arr=new Object[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Predicate<? super Integer> pred=val->true;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.remove(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new Object[10];
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
        var arr=new Object[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<? super Integer>)(v)->
          {
            var tmp=seq.get(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        int length=100;
        Predicate<? super Integer> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertToInteger(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertToInteger(2)));
        };
        var arr=new Object[length];
        var root=new CheckedList(length,arr);
        var subList=root.subList(0,length);
        var seq=subList.subList(0,length);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToInteger(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),length-2);
        for(int i=0,valIndex=0;i<length-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertToInteger(valIndex));
        }
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        int length=100;
        Predicate<? super Integer> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertToInteger(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertToInteger(2)));
        };
        var arr=new Object[length];
        var root=new CheckedList(length,arr);
        var subList=root.subList(0,length);
        var seq=subList.subList(0,length);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToInteger(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.remove(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        int length=100;
        Predicate<? super Integer> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertToInteger(1));
        };
        var arr=new Object[length];
        var root=new CheckedList(length,arr);
        var subList=root.subList(0,length);
        var seq=subList.subList(0,length);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToInteger(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertToInteger(1));
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        int length=100;
        Predicate<? super Integer> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertToInteger(1));
        };
        var arr=new Object[length];
        var root=new CheckedList(length,arr);
        var subList=root.subList(0,length);
        var seq=subList.subList(0,length);
        for(int i=0;i<length;++i)
        {
          arr[i]=TypeConversionUtil.convertToInteger(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.remove(seq.size()-1);
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
      var val=TypeConversionUtil.convertToInteger(i);
      seq.add(val);
    }
    Random rand=new Random(0);
    for(var predicateGenerator:IntegerPredicates.values())
    {
      for(int m=predicateGenerator.getMLo(),mHi=predicateGenerator.getMHi(),repsBound=predicateGenerator.getNumReps();m<=mHi;m=predicateGenerator.incrementM(m))
      {
        for(int reps=0;reps<repsBound;++reps)
        {
          {
            var seqClone=(OmniList.OfRef)seq.clone();
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
            var seqClone=(OmniList.OfRef)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(null);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(pred));
          }
        }
      }
    }
  }
  */
}
