package omni.impl.seq;
import java.util.function.Predicate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.impl.seq.FloatArrSeq.UncheckedList;
import omni.impl.seq.FloatArrSeq.CheckedList;
import omni.impl.seq.FloatArrSeq.UncheckedStack;
import omni.impl.seq.FloatArrSeq.CheckedStack;
import java.util.ConcurrentModificationException;
import omni.impl.CheckedCollectionTest;
import omni.util.TypeConversionUtil;
import omni.util.EqualityUtil;
import omni.util.floatPredicates;
import omni.function.FloatPredicate;
public class FloatArrSeqRemoveIfTest
{
//TODO place sanity checks for checked sequence modification behavior
  @Test
  public void testEmptyRemoveIfArrSeqUncheckedStack()
  {
    FloatPredicate filter=floatPredicates.MarkAll.getPred(null,0);
    var seq=new UncheckedStack();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Float>)filter::test));
  }
  @Test
  public void testRemoveIfUncheckedStack()
  {
      {
        var arr=new float[100];
        var seq=new UncheckedStack(100,arr);
        FloatPredicate pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100);
      }
      {
        var arr=new float[100];
        var seq=new UncheckedStack(100,arr);
        Predicate<? super Float> pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100);
      }
      {
        var arr=new float[100];
        var seq=new UncheckedStack(100,arr);
        FloatPredicate pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
      {
        var arr=new float[100];
        var seq=new UncheckedStack(100,arr);
        Predicate<? super Float> pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
      {
        FloatPredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(2)));
        };
        var arr=new float[100];
        var seq=new UncheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-2);
        for(int i=0,valIndex=0;i<100-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(2)));
        };
        var arr=new float[100];
        var seq=new UncheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-2);
        for(int i=0,valIndex=0;i<100-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
      } 
      {
        FloatPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1));
        };
        var arr=new float[100];
        var seq=new UncheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1));
        };
        var arr=new float[100];
        var seq=new UncheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
      } 
      {
        FloatPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(100-1));
        };
        var arr=new float[100];
        var seq=new UncheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTofloat(100-1));
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(100-1));
        };
        var arr=new float[100];
        var seq=new UncheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTofloat(100-1));
      } 
      {
        FloatPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(3-1));
        };
        var arr=new float[3];
        var seq=new UncheckedStack(3,arr);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTofloat(3-1));
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(3-1));
        };
        var arr=new float[3];
        var seq=new UncheckedStack(3,arr);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTofloat(3-1));
      } 
      {
        FloatPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(50-1));
        };
        var arr=new float[50];
        var seq=new UncheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTofloat(50-1));
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(50-1));
        };
        var arr=new float[50];
        var seq=new UncheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTofloat(50-1));
      } 
      {
        FloatPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0));
        };
        var arr=new float[100];
        var seq=new UncheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-1);
        for(int i=0,valIndex=1;i<100-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0));
        };
        var arr=new float[100];
        var seq=new UncheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-1);
        for(int i=0,valIndex=1;i<100-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
      } 
      {
        FloatPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0));
        };
        var arr=new float[50];
        var seq=new UncheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-1);
        for(int i=0,valIndex=1;i<50-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0));
        };
        var arr=new float[50];
        var seq=new UncheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-1);
        for(int i=0,valIndex=1;i<50-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
      } 
      {
        FloatPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(50-2));
        };
        var arr=new float[50];
        var seq=new UncheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-2);
        for(int i=0,valIndex=1;i<50-3;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
        Assertions.assertEquals(arr[50-3],TypeConversionUtil.convertTofloat(50-1));
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(50-2));
        };
        var arr=new float[50];
        var seq=new UncheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-2);
        for(int i=0,valIndex=1;i<50-3;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
        Assertions.assertEquals(arr[50-3],TypeConversionUtil.convertTofloat(50-1));
      } 
  }
  @Test
  public void testEmptyRemoveIfArrSeqUncheckedList()
  {
    FloatPredicate filter=floatPredicates.MarkAll.getPred(null,0);
    var seq=new UncheckedList();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Float>)filter::test));
  }
  @Test
  public void testRemoveIfUncheckedList()
  {
      {
        var arr=new float[100];
        var seq=new UncheckedList(100,arr);
        FloatPredicate pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100);
      }
      {
        var arr=new float[100];
        var seq=new UncheckedList(100,arr);
        Predicate<? super Float> pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100);
      }
      {
        var arr=new float[100];
        var seq=new UncheckedList(100,arr);
        FloatPredicate pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
      {
        var arr=new float[100];
        var seq=new UncheckedList(100,arr);
        Predicate<? super Float> pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
      {
        FloatPredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(2)));
        };
        var arr=new float[100];
        var seq=new UncheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-2);
        for(int i=0,valIndex=0;i<100-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(2)));
        };
        var arr=new float[100];
        var seq=new UncheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-2);
        for(int i=0,valIndex=0;i<100-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
      } 
      {
        FloatPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1));
        };
        var arr=new float[100];
        var seq=new UncheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1));
        };
        var arr=new float[100];
        var seq=new UncheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
      } 
      {
        FloatPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(100-1));
        };
        var arr=new float[100];
        var seq=new UncheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTofloat(100-1));
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(100-1));
        };
        var arr=new float[100];
        var seq=new UncheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTofloat(100-1));
      } 
      {
        FloatPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(3-1));
        };
        var arr=new float[3];
        var seq=new UncheckedList(3,arr);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTofloat(3-1));
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(3-1));
        };
        var arr=new float[3];
        var seq=new UncheckedList(3,arr);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTofloat(3-1));
      } 
      {
        FloatPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(50-1));
        };
        var arr=new float[50];
        var seq=new UncheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTofloat(50-1));
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(50-1));
        };
        var arr=new float[50];
        var seq=new UncheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTofloat(50-1));
      } 
      {
        FloatPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0));
        };
        var arr=new float[100];
        var seq=new UncheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-1);
        for(int i=0,valIndex=1;i<100-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0));
        };
        var arr=new float[100];
        var seq=new UncheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-1);
        for(int i=0,valIndex=1;i<100-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
      } 
      {
        FloatPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0));
        };
        var arr=new float[50];
        var seq=new UncheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-1);
        for(int i=0,valIndex=1;i<50-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0));
        };
        var arr=new float[50];
        var seq=new UncheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-1);
        for(int i=0,valIndex=1;i<50-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
      } 
      {
        FloatPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(50-2));
        };
        var arr=new float[50];
        var seq=new UncheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-2);
        for(int i=0,valIndex=1;i<50-3;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
        Assertions.assertEquals(arr[50-3],TypeConversionUtil.convertTofloat(50-1));
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(50-2));
        };
        var arr=new float[50];
        var seq=new UncheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-2);
        for(int i=0,valIndex=1;i<50-3;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
        Assertions.assertEquals(arr[50-3],TypeConversionUtil.convertTofloat(50-1));
      } 
  }
  @Test
  public void testEmptyRemoveIfArrSeqCheckedStack()
  {
    FloatPredicate filter=floatPredicates.MarkAll.getPred(null,0);
    var seq=new CheckedStack();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Float>)filter::test));
  }
  @Test
  public void testRemoveIfCheckedStack()
  {
      {
        var arr=new float[100];
        var seq=new CheckedStack(100,arr);
        FloatPredicate pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100);
        Assertions.assertEquals(0,seq.modCount);
      }
      {
        var arr=new float[100];
        var seq=new CheckedStack(100,arr);
        Predicate<? super Float> pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100);
        Assertions.assertEquals(0,seq.modCount);
      }
      {
        var arr=new float[100];
        var seq=new CheckedStack(100,arr);
        FloatPredicate pred=val->false;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popFloat();
          seq.push(tmp);
        })));
      }
      {
        var arr=new float[100];
        var seq=new CheckedStack(100,arr);
        Predicate<? super Float> pred=val->false;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popFloat();
          seq.push(tmp);
        })));
      }
      {
        var arr=new float[100];
        var seq=new CheckedStack(100,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((FloatPredicate)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new float[100];
        var seq=new CheckedStack(100,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((Predicate<? super Float>)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new float[100];
        var seq=new CheckedStack(100,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((FloatPredicate)(v)->
          {
            var tmp=seq.popFloat();
            seq.push(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new float[100];
        var seq=new CheckedStack(100,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<? super Float>)(v)->
          {
            var tmp=seq.popFloat();
            seq.push(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new float[100];
        var seq=new CheckedStack(100,arr);
        FloatPredicate pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(seq.modCount!=0);
      }
      {
        var arr=new float[100];
        var seq=new CheckedStack(100,arr);
        Predicate<? super Float> pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(seq.modCount!=0);
      }
      {
        var arr=new float[100];
        var seq=new CheckedStack(100,arr);
        FloatPredicate pred=val->true;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popFloat();
          seq.push(tmp);
        })));
      }
      {
        var arr=new float[100];
        var seq=new CheckedStack(100,arr);
        Predicate<? super Float> pred=val->true;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popFloat();
          seq.push(tmp);
        })));
      }
      {
        var arr=new float[100];
        var seq=new CheckedStack(100,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((FloatPredicate)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new float[100];
        var seq=new CheckedStack(100,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((Predicate<? super Float>)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new float[100];
        var seq=new CheckedStack(100,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((FloatPredicate)(v)->
          {
            var tmp=seq.popFloat();
            seq.push(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new float[100];
        var seq=new CheckedStack(100,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<? super Float>)(v)->
          {
            var tmp=seq.popFloat();
            seq.push(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        FloatPredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(2)));
        };
        var arr=new float[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-2);
        for(int i=0,valIndex=0;i<100-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(2)));
        };
        var arr=new float[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-2);
        for(int i=0,valIndex=0;i<100-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        FloatPredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(2)));
        };
        var arr=new float[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popFloat();
          seq.push(tmp);
        })));
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(2)));
        };
        var arr=new float[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popFloat();
          seq.push(tmp);
        })));
      } 
      {
        FloatPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1));
        };
        var arr=new float[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1));
        };
        var arr=new float[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        FloatPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1));
        };
        var arr=new float[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popFloat();
          seq.push(tmp);
        })));
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1));
        };
        var arr=new float[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popFloat();
          seq.push(tmp);
        })));
      } 
      {
        FloatPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(100-1));
        };
        var arr=new float[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTofloat(100-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(100-1));
        };
        var arr=new float[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTofloat(100-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        FloatPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(100-1));
        };
        var arr=new float[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popFloat();
          seq.push(tmp);
        })));
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(100-1));
        };
        var arr=new float[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popFloat();
          seq.push(tmp);
        })));
      } 
      {
        FloatPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(3-1));
        };
        var arr=new float[3];
        var seq=new CheckedStack(3,arr);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTofloat(3-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(3-1));
        };
        var arr=new float[3];
        var seq=new CheckedStack(3,arr);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTofloat(3-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        FloatPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(3-1));
        };
        var arr=new float[3];
        var seq=new CheckedStack(3,arr);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popFloat();
          seq.push(tmp);
        })));
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(3-1));
        };
        var arr=new float[3];
        var seq=new CheckedStack(3,arr);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popFloat();
          seq.push(tmp);
        })));
      } 
      {
        FloatPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(50-1));
        };
        var arr=new float[50];
        var seq=new CheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTofloat(50-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(50-1));
        };
        var arr=new float[50];
        var seq=new CheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTofloat(50-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        FloatPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(50-1));
        };
        var arr=new float[50];
        var seq=new CheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popFloat();
          seq.push(tmp);
        })));
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(50-1));
        };
        var arr=new float[50];
        var seq=new CheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popFloat();
          seq.push(tmp);
        })));
      } 
      {
        FloatPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0));
        };
        var arr=new float[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-1);
        for(int i=0,valIndex=1;i<100-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0));
        };
        var arr=new float[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-1);
        for(int i=0,valIndex=1;i<100-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        FloatPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0));
        };
        var arr=new float[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popFloat();
          seq.push(tmp);
        })));
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0));
        };
        var arr=new float[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popFloat();
          seq.push(tmp);
        })));
      } 
      {
        FloatPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0));
        };
        var arr=new float[50];
        var seq=new CheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-1);
        for(int i=0,valIndex=1;i<50-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0));
        };
        var arr=new float[50];
        var seq=new CheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-1);
        for(int i=0,valIndex=1;i<50-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        FloatPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0));
        };
        var arr=new float[50];
        var seq=new CheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popFloat();
          seq.push(tmp);
        })));
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0));
        };
        var arr=new float[50];
        var seq=new CheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popFloat();
          seq.push(tmp);
        })));
      } 
      {
        FloatPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(50-2));
        };
        var arr=new float[50];
        var seq=new CheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-2);
        for(int i=0,valIndex=1;i<50-3;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
        Assertions.assertEquals(arr[50-3],TypeConversionUtil.convertTofloat(50-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(50-2));
        };
        var arr=new float[50];
        var seq=new CheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-2);
        for(int i=0,valIndex=1;i<50-3;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
        Assertions.assertEquals(arr[50-3],TypeConversionUtil.convertTofloat(50-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        FloatPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(50-2));
        };
        var arr=new float[50];
        var seq=new CheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popFloat();
          seq.push(tmp);
        })));
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(50-2));
        };
        var arr=new float[50];
        var seq=new CheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popFloat();
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
      var val=TypeConversionUtil.convertTofloat(i);
      seq.add(val);
    }
    Random rand=new Random(0);
    for(var predicateGenerator:floatPredicates.values())
    {
      for(int m=predicateGenerator.getMLo(),mHi=predicateGenerator.getMHi(),repsBound=predicateGenerator.getNumReps();m<=mHi;m=predicateGenerator.incrementM(m))
      {
        for(int reps=0;reps<repsBound;++reps)
        {
          {
            var seqClone=(OmniStack.OfFloat)seq.clone();
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
            var seqClone=(OmniStack.OfFloat)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(Float.NaN);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(pred));
          }
          {
            var seqClone=(OmniStack.OfFloat)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              if(!seqClone.isEmpty())
              {
                seqClone.pop();
              }
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Float>)pred::test));
          }
          {
            var seqClone=(OmniStack.OfFloat)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(Float.NaN);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Float>)pred::test));
          }
        }
      }
    }
  }
  */
  @Test
  public void testEmptyRemoveIfArrSeqCheckedList()
  {
    FloatPredicate filter=floatPredicates.MarkAll.getPred(null,0);
    var seq=new CheckedList();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Float>)filter::test));
  }
  @Test
  public void testRemoveIfCheckedList()
  {
      {
        var arr=new float[100];
        var seq=new CheckedList(100,arr);
        FloatPredicate pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100);
        Assertions.assertEquals(0,seq.modCount);
      }
      {
        var arr=new float[100];
        var seq=new CheckedList(100,arr);
        Predicate<? super Float> pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100);
        Assertions.assertEquals(0,seq.modCount);
      }
      {
        var arr=new float[100];
        var seq=new CheckedList(100,arr);
        FloatPredicate pred=val->false;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeFloatAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new float[100];
        var seq=new CheckedList(100,arr);
        Predicate<? super Float> pred=val->false;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeFloatAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new float[100];
        var seq=new CheckedList(100,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((FloatPredicate)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new float[100];
        var seq=new CheckedList(100,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((Predicate<? super Float>)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new float[100];
        var seq=new CheckedList(100,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((FloatPredicate)(v)->
          {
            var tmp=seq.removeFloatAt(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new float[100];
        var seq=new CheckedList(100,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<? super Float>)(v)->
          {
            var tmp=seq.removeFloatAt(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new float[100];
        var seq=new CheckedList(100,arr);
        FloatPredicate pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(seq.modCount!=0);
      }
      {
        var arr=new float[100];
        var seq=new CheckedList(100,arr);
        Predicate<? super Float> pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(seq.modCount!=0);
      }
      {
        var arr=new float[100];
        var seq=new CheckedList(100,arr);
        FloatPredicate pred=val->true;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeFloatAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new float[100];
        var seq=new CheckedList(100,arr);
        Predicate<? super Float> pred=val->true;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeFloatAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new float[100];
        var seq=new CheckedList(100,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((FloatPredicate)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new float[100];
        var seq=new CheckedList(100,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((Predicate<? super Float>)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new float[100];
        var seq=new CheckedList(100,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((FloatPredicate)(v)->
          {
            var tmp=seq.getFloat(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new float[100];
        var seq=new CheckedList(100,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<? super Float>)(v)->
          {
            var tmp=seq.getFloat(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        FloatPredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(2)));
        };
        var arr=new float[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-2);
        for(int i=0,valIndex=0;i<100-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(2)));
        };
        var arr=new float[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-2);
        for(int i=0,valIndex=0;i<100-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        FloatPredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(2)));
        };
        var arr=new float[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeFloatAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(2)));
        };
        var arr=new float[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeFloatAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        FloatPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1));
        };
        var arr=new float[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1));
        };
        var arr=new float[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        FloatPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1));
        };
        var arr=new float[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeFloatAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1));
        };
        var arr=new float[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeFloatAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        FloatPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(100-1));
        };
        var arr=new float[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTofloat(100-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(100-1));
        };
        var arr=new float[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTofloat(100-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        FloatPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(100-1));
        };
        var arr=new float[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeFloatAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(100-1));
        };
        var arr=new float[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeFloatAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        FloatPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(3-1));
        };
        var arr=new float[3];
        var seq=new CheckedList(3,arr);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTofloat(3-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(3-1));
        };
        var arr=new float[3];
        var seq=new CheckedList(3,arr);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTofloat(3-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        FloatPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(3-1));
        };
        var arr=new float[3];
        var seq=new CheckedList(3,arr);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeFloatAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(3-1));
        };
        var arr=new float[3];
        var seq=new CheckedList(3,arr);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeFloatAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        FloatPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(50-1));
        };
        var arr=new float[50];
        var seq=new CheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTofloat(50-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(50-1));
        };
        var arr=new float[50];
        var seq=new CheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTofloat(50-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        FloatPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(50-1));
        };
        var arr=new float[50];
        var seq=new CheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeFloatAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(50-1));
        };
        var arr=new float[50];
        var seq=new CheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeFloatAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        FloatPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0));
        };
        var arr=new float[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-1);
        for(int i=0,valIndex=1;i<100-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0));
        };
        var arr=new float[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-1);
        for(int i=0,valIndex=1;i<100-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        FloatPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0));
        };
        var arr=new float[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeFloatAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0));
        };
        var arr=new float[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeFloatAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        FloatPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0));
        };
        var arr=new float[50];
        var seq=new CheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-1);
        for(int i=0,valIndex=1;i<50-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0));
        };
        var arr=new float[50];
        var seq=new CheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-1);
        for(int i=0,valIndex=1;i<50-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        FloatPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0));
        };
        var arr=new float[50];
        var seq=new CheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeFloatAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0));
        };
        var arr=new float[50];
        var seq=new CheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeFloatAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        FloatPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(50-2));
        };
        var arr=new float[50];
        var seq=new CheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-2);
        for(int i=0,valIndex=1;i<50-3;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
        Assertions.assertEquals(arr[50-3],TypeConversionUtil.convertTofloat(50-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(50-2));
        };
        var arr=new float[50];
        var seq=new CheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-2);
        for(int i=0,valIndex=1;i<50-3;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
        Assertions.assertEquals(arr[50-3],TypeConversionUtil.convertTofloat(50-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        FloatPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(50-2));
        };
        var arr=new float[50];
        var seq=new CheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeFloatAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(50-2));
        };
        var arr=new float[50];
        var seq=new CheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeFloatAt(seq.size()-1);
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
      var val=TypeConversionUtil.convertTofloat(i);
      seq.add(val);
    }
    Random rand=new Random(0);
    for(var predicateGenerator:floatPredicates.values())
    {
      for(int m=predicateGenerator.getMLo(),mHi=predicateGenerator.getMHi(),repsBound=predicateGenerator.getNumReps();m<=mHi;m=predicateGenerator.incrementM(m))
      {
        for(int reps=0;reps<repsBound;++reps)
        {
          {
            var seqClone=(OmniList.OfFloat)seq.clone();
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
            var seqClone=(OmniList.OfFloat)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(Float.NaN);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(pred));
          }
          {
            var seqClone=(OmniList.OfFloat)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              if(!seqClone.isEmpty())
              {
                seqClone.remove(0);
              }
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Float>)pred::test));
          }
          {
            var seqClone=(OmniList.OfFloat)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(Float.NaN);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Float>)pred::test));
          }
        }
      }
    }
  }
  */
  @Test
  public void testEmptyRemoveIfArrSeqUncheckedSubList()
  {
    FloatPredicate filter=floatPredicates.MarkAll.getPred(null,0);
    var root=new UncheckedList();
    var seq=root.subList(0,0);
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Float>)filter::test));
  }
  @Test
  public void testRemoveIfUncheckedSubList()
  {
      {
        var arr=new float[100];
        var root=new UncheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        FloatPredicate pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100);
      }
      {
        var arr=new float[100];
        var root=new UncheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        Predicate<? super Float> pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100);
      }
      {
        var arr=new float[100];
        var root=new UncheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        FloatPredicate pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
      {
        var arr=new float[100];
        var root=new UncheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        Predicate<? super Float> pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
      {
        FloatPredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(2)));
        };
        var arr=new float[100];
        var root=new UncheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-2);
        for(int i=0,valIndex=0;i<100-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(2)));
        };
        var arr=new float[100];
        var root=new UncheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-2);
        for(int i=0,valIndex=0;i<100-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
      } 
      {
        FloatPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1));
        };
        var arr=new float[100];
        var root=new UncheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1));
        };
        var arr=new float[100];
        var root=new UncheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
      } 
      {
        FloatPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(100-1));
        };
        var arr=new float[100];
        var root=new UncheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTofloat(100-1));
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(100-1));
        };
        var arr=new float[100];
        var root=new UncheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTofloat(100-1));
      } 
      {
        FloatPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(3-1));
        };
        var arr=new float[3];
        var root=new UncheckedList(3,arr);
        var subList=root.subList(0,3);
        var seq=subList.subList(0,3);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTofloat(3-1));
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(3-1));
        };
        var arr=new float[3];
        var root=new UncheckedList(3,arr);
        var subList=root.subList(0,3);
        var seq=subList.subList(0,3);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTofloat(3-1));
      } 
      {
        FloatPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(50-1));
        };
        var arr=new float[50];
        var root=new UncheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTofloat(50-1));
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(50-1));
        };
        var arr=new float[50];
        var root=new UncheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTofloat(50-1));
      } 
      {
        FloatPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0));
        };
        var arr=new float[100];
        var root=new UncheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-1);
        for(int i=0,valIndex=1;i<100-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0));
        };
        var arr=new float[100];
        var root=new UncheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-1);
        for(int i=0,valIndex=1;i<100-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
      } 
      {
        FloatPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0));
        };
        var arr=new float[50];
        var root=new UncheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-1);
        for(int i=0,valIndex=1;i<50-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0));
        };
        var arr=new float[50];
        var root=new UncheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-1);
        for(int i=0,valIndex=1;i<50-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
      } 
      {
        FloatPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(50-2));
        };
        var arr=new float[50];
        var root=new UncheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-2);
        for(int i=0,valIndex=1;i<50-3;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
        Assertions.assertEquals(arr[50-3],TypeConversionUtil.convertTofloat(50-1));
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(50-2));
        };
        var arr=new float[50];
        var root=new UncheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-2);
        for(int i=0,valIndex=1;i<50-3;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
        Assertions.assertEquals(arr[50-3],TypeConversionUtil.convertTofloat(50-1));
      } 
  }
  @Test
  public void testEmptyRemoveIfArrSeqCheckedSubList()
  {
    FloatPredicate filter=floatPredicates.MarkAll.getPred(null,0);
    var root=new CheckedList();
    var seq=root.subList(0,0);
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Float>)filter::test));
  }
  @Test
  public void testRemoveIfCheckedSubList()
  {
      {
        var arr=new float[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        FloatPredicate pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100);
        Assertions.assertEquals(0,root.modCount);
      }
      {
        var arr=new float[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        Predicate<? super Float> pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100);
        Assertions.assertEquals(0,root.modCount);
      }
      {
        var arr=new float[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        FloatPredicate pred=val->false;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeFloatAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new float[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        Predicate<? super Float> pred=val->false;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeFloatAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new float[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((FloatPredicate)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new float[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((Predicate<? super Float>)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new float[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((FloatPredicate)(v)->
          {
            var tmp=seq.removeFloatAt(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new float[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<? super Float>)(v)->
          {
            var tmp=seq.removeFloatAt(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new float[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        FloatPredicate pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(root.modCount!=0);
      }
      {
        var arr=new float[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        Predicate<? super Float> pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(root.modCount!=0);
      }
      {
        var arr=new float[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        FloatPredicate pred=val->true;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeFloatAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new float[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        Predicate<? super Float> pred=val->true;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeFloatAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new float[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((FloatPredicate)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new float[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((Predicate<? super Float>)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new float[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((FloatPredicate)(v)->
          {
            var tmp=seq.getFloat(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new float[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<? super Float>)(v)->
          {
            var tmp=seq.getFloat(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        FloatPredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(2)));
        };
        var arr=new float[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-2);
        for(int i=0,valIndex=0;i<100-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(2)));
        };
        var arr=new float[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-2);
        for(int i=0,valIndex=0;i<100-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        FloatPredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(2)));
        };
        var arr=new float[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeFloatAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(2)));
        };
        var arr=new float[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeFloatAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        FloatPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1));
        };
        var arr=new float[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1));
        };
        var arr=new float[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        FloatPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1));
        };
        var arr=new float[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeFloatAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1));
        };
        var arr=new float[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeFloatAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        FloatPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(100-1));
        };
        var arr=new float[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTofloat(100-1));
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(100-1));
        };
        var arr=new float[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTofloat(100-1));
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        FloatPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(100-1));
        };
        var arr=new float[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeFloatAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(100-1));
        };
        var arr=new float[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeFloatAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        FloatPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(3-1));
        };
        var arr=new float[3];
        var root=new CheckedList(3,arr);
        var subList=root.subList(0,3);
        var seq=subList.subList(0,3);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTofloat(3-1));
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(3-1));
        };
        var arr=new float[3];
        var root=new CheckedList(3,arr);
        var subList=root.subList(0,3);
        var seq=subList.subList(0,3);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTofloat(3-1));
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        FloatPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(3-1));
        };
        var arr=new float[3];
        var root=new CheckedList(3,arr);
        var subList=root.subList(0,3);
        var seq=subList.subList(0,3);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeFloatAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(3-1));
        };
        var arr=new float[3];
        var root=new CheckedList(3,arr);
        var subList=root.subList(0,3);
        var seq=subList.subList(0,3);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeFloatAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        FloatPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(50-1));
        };
        var arr=new float[50];
        var root=new CheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTofloat(50-1));
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(50-1));
        };
        var arr=new float[50];
        var root=new CheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTofloat(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTofloat(50-1));
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        FloatPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(50-1));
        };
        var arr=new float[50];
        var root=new CheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeFloatAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(50-1));
        };
        var arr=new float[50];
        var root=new CheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeFloatAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        FloatPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0));
        };
        var arr=new float[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-1);
        for(int i=0,valIndex=1;i<100-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0));
        };
        var arr=new float[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-1);
        for(int i=0,valIndex=1;i<100-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        FloatPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0));
        };
        var arr=new float[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeFloatAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0));
        };
        var arr=new float[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeFloatAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        FloatPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0));
        };
        var arr=new float[50];
        var root=new CheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-1);
        for(int i=0,valIndex=1;i<50-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0));
        };
        var arr=new float[50];
        var root=new CheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-1);
        for(int i=0,valIndex=1;i<50-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        FloatPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0));
        };
        var arr=new float[50];
        var root=new CheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeFloatAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0));
        };
        var arr=new float[50];
        var root=new CheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeFloatAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        FloatPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(50-2));
        };
        var arr=new float[50];
        var root=new CheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-2);
        for(int i=0,valIndex=1;i<50-3;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
        Assertions.assertEquals(arr[50-3],TypeConversionUtil.convertTofloat(50-1));
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(50-2));
        };
        var arr=new float[50];
        var root=new CheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-2);
        for(int i=0,valIndex=1;i<50-3;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTofloat(valIndex));
        }
        Assertions.assertEquals(arr[50-3],TypeConversionUtil.convertTofloat(50-1));
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        FloatPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(50-2));
        };
        var arr=new float[50];
        var root=new CheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeFloatAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Float> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(50-2));
        };
        var arr=new float[50];
        var root=new CheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTofloat(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeFloatAt(seq.size()-1);
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
      var val=TypeConversionUtil.convertTofloat(i);
      seq.add(val);
    }
    Random rand=new Random(0);
    for(var predicateGenerator:floatPredicates.values())
    {
      for(int m=predicateGenerator.getMLo(),mHi=predicateGenerator.getMHi(),repsBound=predicateGenerator.getNumReps();m<=mHi;m=predicateGenerator.incrementM(m))
      {
        for(int reps=0;reps<repsBound;++reps)
        {
          {
            var seqClone=(OmniList.OfFloat)seq.clone();
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
            var seqClone=(OmniList.OfFloat)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(Float.NaN);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(pred));
          }
          {
            var seqClone=(OmniList.OfFloat)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              if(!seqClone.isEmpty())
              {
                seqClone.remove(0);
              }
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Float>)pred::test));
          }
          {
            var seqClone=(OmniList.OfFloat)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(Float.NaN);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Float>)pred::test));
          }
        }
      }
    }
  }
  */
}
