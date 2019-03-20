package omni.impl.seq;
import java.util.function.Predicate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import omni.api.OmniIterator;
import omni.impl.seq.CharArrSeq.UncheckedList;
import omni.impl.seq.CharArrSeq.CheckedList;
import omni.impl.seq.CharArrSeq.UncheckedStack;
import omni.impl.seq.CharArrSeq.CheckedStack;
import java.util.ConcurrentModificationException;
import omni.impl.CheckedCollectionTest;
import omni.util.TypeConversionUtil;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.util.EqualityUtil;
import java.util.Random;
import omni.util.charPredicates;
import omni.util.charArrayBuilder;
import omni.function.CharPredicate;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class CharSeqRemoveIfTest
{
  @Test
  public void testEmptyRemoveIfArrSeqUncheckedStack()
  {
    CharPredicate filter=charPredicates.MarkAll.getPred(null,0);
    var seq=new UncheckedStack();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Character>)filter::test));
  }
  @Test
  public void testRemoveIfUncheckedStack()
  {
      {
        var arr=new char[100];
        var seq=new UncheckedStack(100,arr);
        CharPredicate pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100);
      }
      {
        var arr=new char[100];
        var seq=new UncheckedStack(100,arr);
        Predicate<? super Character> pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100);
      }
      {
        var arr=new char[100];
        var seq=new UncheckedStack(100,arr);
        CharPredicate pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
      {
        var arr=new char[100];
        var seq=new UncheckedStack(100,arr);
        Predicate<? super Character> pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
      {
        CharPredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(2)));
        };
        var arr=new char[100];
        var seq=new UncheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-2);
        for(int i=0,valIndex=0;i<100-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(2)));
        };
        var arr=new char[100];
        var seq=new UncheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-2);
        for(int i=0,valIndex=0;i<100-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
      } 
      {
        CharPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1));
        };
        var arr=new char[100];
        var seq=new UncheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1));
        };
        var arr=new char[100];
        var seq=new UncheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
      } 
      {
        CharPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(100-1));
        };
        var arr=new char[100];
        var seq=new UncheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTochar(100-1));
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(100-1));
        };
        var arr=new char[100];
        var seq=new UncheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTochar(100-1));
      } 
      {
        CharPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(3-1));
        };
        var arr=new char[3];
        var seq=new UncheckedStack(3,arr);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTochar(3-1));
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(3-1));
        };
        var arr=new char[3];
        var seq=new UncheckedStack(3,arr);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTochar(3-1));
      } 
      {
        CharPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(50-1));
        };
        var arr=new char[50];
        var seq=new UncheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTochar(50-1));
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(50-1));
        };
        var arr=new char[50];
        var seq=new UncheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTochar(50-1));
      } 
      {
        CharPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0));
        };
        var arr=new char[100];
        var seq=new UncheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-1);
        for(int i=0,valIndex=1;i<100-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0));
        };
        var arr=new char[100];
        var seq=new UncheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-1);
        for(int i=0,valIndex=1;i<100-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
      } 
      {
        CharPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0));
        };
        var arr=new char[50];
        var seq=new UncheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-1);
        for(int i=0,valIndex=1;i<50-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0));
        };
        var arr=new char[50];
        var seq=new UncheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-1);
        for(int i=0,valIndex=1;i<50-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
      } 
      {
        CharPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(50-2));
        };
        var arr=new char[50];
        var seq=new UncheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-2);
        for(int i=0,valIndex=1;i<50-3;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
        Assertions.assertEquals(arr[50-3],TypeConversionUtil.convertTochar(50-1));
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(50-2));
        };
        var arr=new char[50];
        var seq=new UncheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-2);
        for(int i=0,valIndex=1;i<50-3;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
        Assertions.assertEquals(arr[50-3],TypeConversionUtil.convertTochar(50-1));
      } 
  }
  @Test
  public void testEmptyRemoveIfArrSeqUncheckedList()
  {
    CharPredicate filter=charPredicates.MarkAll.getPred(null,0);
    var seq=new UncheckedList();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Character>)filter::test));
  }
  @Test
  public void testRemoveIfUncheckedList()
  {
      {
        var arr=new char[100];
        var seq=new UncheckedList(100,arr);
        CharPredicate pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100);
      }
      {
        var arr=new char[100];
        var seq=new UncheckedList(100,arr);
        Predicate<? super Character> pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100);
      }
      {
        var arr=new char[100];
        var seq=new UncheckedList(100,arr);
        CharPredicate pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
      {
        var arr=new char[100];
        var seq=new UncheckedList(100,arr);
        Predicate<? super Character> pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
      {
        CharPredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(2)));
        };
        var arr=new char[100];
        var seq=new UncheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-2);
        for(int i=0,valIndex=0;i<100-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(2)));
        };
        var arr=new char[100];
        var seq=new UncheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-2);
        for(int i=0,valIndex=0;i<100-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
      } 
      {
        CharPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1));
        };
        var arr=new char[100];
        var seq=new UncheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1));
        };
        var arr=new char[100];
        var seq=new UncheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
      } 
      {
        CharPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(100-1));
        };
        var arr=new char[100];
        var seq=new UncheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTochar(100-1));
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(100-1));
        };
        var arr=new char[100];
        var seq=new UncheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTochar(100-1));
      } 
      {
        CharPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(3-1));
        };
        var arr=new char[3];
        var seq=new UncheckedList(3,arr);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTochar(3-1));
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(3-1));
        };
        var arr=new char[3];
        var seq=new UncheckedList(3,arr);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTochar(3-1));
      } 
      {
        CharPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(50-1));
        };
        var arr=new char[50];
        var seq=new UncheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTochar(50-1));
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(50-1));
        };
        var arr=new char[50];
        var seq=new UncheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTochar(50-1));
      } 
      {
        CharPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0));
        };
        var arr=new char[100];
        var seq=new UncheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-1);
        for(int i=0,valIndex=1;i<100-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0));
        };
        var arr=new char[100];
        var seq=new UncheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-1);
        for(int i=0,valIndex=1;i<100-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
      } 
      {
        CharPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0));
        };
        var arr=new char[50];
        var seq=new UncheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-1);
        for(int i=0,valIndex=1;i<50-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0));
        };
        var arr=new char[50];
        var seq=new UncheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-1);
        for(int i=0,valIndex=1;i<50-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
      } 
      {
        CharPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(50-2));
        };
        var arr=new char[50];
        var seq=new UncheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-2);
        for(int i=0,valIndex=1;i<50-3;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
        Assertions.assertEquals(arr[50-3],TypeConversionUtil.convertTochar(50-1));
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(50-2));
        };
        var arr=new char[50];
        var seq=new UncheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-2);
        for(int i=0,valIndex=1;i<50-3;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
        Assertions.assertEquals(arr[50-3],TypeConversionUtil.convertTochar(50-1));
      } 
  }
  @Test
  public void testEmptyRemoveIfArrSeqCheckedStack()
  {
    CharPredicate filter=charPredicates.MarkAll.getPred(null,0);
    var seq=new CheckedStack();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Character>)filter::test));
  }
  @Test
  public void testRemoveIfCheckedStack()
  {
      {
        var arr=new char[100];
        var seq=new CheckedStack(100,arr);
        CharPredicate pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100);
        Assertions.assertEquals(0,seq.modCount);
      }
      {
        var arr=new char[100];
        var seq=new CheckedStack(100,arr);
        Predicate<? super Character> pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100);
        Assertions.assertEquals(0,seq.modCount);
      }
      {
        var arr=new char[100];
        var seq=new CheckedStack(100,arr);
        CharPredicate pred=val->false;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popChar();
          seq.push(tmp);
        })));
      }
      {
        var arr=new char[100];
        var seq=new CheckedStack(100,arr);
        Predicate<? super Character> pred=val->false;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popChar();
          seq.push(tmp);
        })));
      }
      {
        var arr=new char[100];
        var seq=new CheckedStack(100,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((CharPredicate)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new char[100];
        var seq=new CheckedStack(100,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((Predicate<? super Character>)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new char[100];
        var seq=new CheckedStack(100,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((CharPredicate)(v)->
          {
            var tmp=seq.popChar();
            seq.push(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new char[100];
        var seq=new CheckedStack(100,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<? super Character>)(v)->
          {
            var tmp=seq.popChar();
            seq.push(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new char[100];
        var seq=new CheckedStack(100,arr);
        CharPredicate pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(seq.modCount!=0);
      }
      {
        var arr=new char[100];
        var seq=new CheckedStack(100,arr);
        Predicate<? super Character> pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(seq.modCount!=0);
      }
      {
        var arr=new char[100];
        var seq=new CheckedStack(100,arr);
        CharPredicate pred=val->true;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popChar();
          seq.push(tmp);
        })));
      }
      {
        var arr=new char[100];
        var seq=new CheckedStack(100,arr);
        Predicate<? super Character> pred=val->true;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popChar();
          seq.push(tmp);
        })));
      }
      {
        var arr=new char[100];
        var seq=new CheckedStack(100,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((CharPredicate)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new char[100];
        var seq=new CheckedStack(100,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((Predicate<? super Character>)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new char[100];
        var seq=new CheckedStack(100,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((CharPredicate)(v)->
          {
            var tmp=seq.popChar();
            seq.push(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new char[100];
        var seq=new CheckedStack(100,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<? super Character>)(v)->
          {
            var tmp=seq.popChar();
            seq.push(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        CharPredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(2)));
        };
        var arr=new char[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-2);
        for(int i=0,valIndex=0;i<100-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(2)));
        };
        var arr=new char[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-2);
        for(int i=0,valIndex=0;i<100-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        CharPredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(2)));
        };
        var arr=new char[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popChar();
          seq.push(tmp);
        })));
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(2)));
        };
        var arr=new char[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popChar();
          seq.push(tmp);
        })));
      } 
      {
        CharPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1));
        };
        var arr=new char[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1));
        };
        var arr=new char[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        CharPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1));
        };
        var arr=new char[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popChar();
          seq.push(tmp);
        })));
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1));
        };
        var arr=new char[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popChar();
          seq.push(tmp);
        })));
      } 
      {
        CharPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(100-1));
        };
        var arr=new char[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTochar(100-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(100-1));
        };
        var arr=new char[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTochar(100-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        CharPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(100-1));
        };
        var arr=new char[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popChar();
          seq.push(tmp);
        })));
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(100-1));
        };
        var arr=new char[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popChar();
          seq.push(tmp);
        })));
      } 
      {
        CharPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(3-1));
        };
        var arr=new char[3];
        var seq=new CheckedStack(3,arr);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTochar(3-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(3-1));
        };
        var arr=new char[3];
        var seq=new CheckedStack(3,arr);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTochar(3-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        CharPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(3-1));
        };
        var arr=new char[3];
        var seq=new CheckedStack(3,arr);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popChar();
          seq.push(tmp);
        })));
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(3-1));
        };
        var arr=new char[3];
        var seq=new CheckedStack(3,arr);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popChar();
          seq.push(tmp);
        })));
      } 
      {
        CharPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(50-1));
        };
        var arr=new char[50];
        var seq=new CheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTochar(50-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(50-1));
        };
        var arr=new char[50];
        var seq=new CheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTochar(50-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        CharPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(50-1));
        };
        var arr=new char[50];
        var seq=new CheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popChar();
          seq.push(tmp);
        })));
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(50-1));
        };
        var arr=new char[50];
        var seq=new CheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popChar();
          seq.push(tmp);
        })));
      } 
      {
        CharPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0));
        };
        var arr=new char[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-1);
        for(int i=0,valIndex=1;i<100-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0));
        };
        var arr=new char[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-1);
        for(int i=0,valIndex=1;i<100-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        CharPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0));
        };
        var arr=new char[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popChar();
          seq.push(tmp);
        })));
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0));
        };
        var arr=new char[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popChar();
          seq.push(tmp);
        })));
      } 
      {
        CharPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0));
        };
        var arr=new char[50];
        var seq=new CheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-1);
        for(int i=0,valIndex=1;i<50-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0));
        };
        var arr=new char[50];
        var seq=new CheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-1);
        for(int i=0,valIndex=1;i<50-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        CharPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0));
        };
        var arr=new char[50];
        var seq=new CheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popChar();
          seq.push(tmp);
        })));
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0));
        };
        var arr=new char[50];
        var seq=new CheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popChar();
          seq.push(tmp);
        })));
      } 
      {
        CharPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(50-2));
        };
        var arr=new char[50];
        var seq=new CheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-2);
        for(int i=0,valIndex=1;i<50-3;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
        Assertions.assertEquals(arr[50-3],TypeConversionUtil.convertTochar(50-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(50-2));
        };
        var arr=new char[50];
        var seq=new CheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-2);
        for(int i=0,valIndex=1;i<50-3;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
        Assertions.assertEquals(arr[50-3],TypeConversionUtil.convertTochar(50-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        CharPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(50-2));
        };
        var arr=new char[50];
        var seq=new CheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popChar();
          seq.push(tmp);
        })));
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(50-2));
        };
        var arr=new char[50];
        var seq=new CheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popChar();
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
      var val=TypeConversionUtil.convertTochar(i);
      seq.add(val);
    }
    Random rand=new Random(0);
    for(var predicateGenerator:charPredicates.values())
    {
      for(int m=predicateGenerator.getMLo(),mHi=predicateGenerator.getMHi(),repsBound=predicateGenerator.getNumReps();m<=mHi;m=predicateGenerator.incrementM(m))
      {
        for(int reps=0;reps<repsBound;++reps)
        {
          {
            var seqClone=(OmniStack.OfChar)seq.clone();
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
            var seqClone=(OmniStack.OfChar)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(Character.MIN_VALUE);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(pred));
          }
          {
            var seqClone=(OmniStack.OfChar)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              if(!seqClone.isEmpty())
              {
                seqClone.pop();
              }
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Character>)pred::test));
          }
          {
            var seqClone=(OmniStack.OfChar)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(Character.MIN_VALUE);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Character>)pred::test));
          }
        }
      }
    }
  }
  */
  @Test
  public void testEmptyRemoveIfArrSeqCheckedList()
  {
    CharPredicate filter=charPredicates.MarkAll.getPred(null,0);
    var seq=new CheckedList();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Character>)filter::test));
  }
  @Test
  public void testRemoveIfCheckedList()
  {
      {
        var arr=new char[100];
        var seq=new CheckedList(100,arr);
        CharPredicate pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100);
        Assertions.assertEquals(0,seq.modCount);
      }
      {
        var arr=new char[100];
        var seq=new CheckedList(100,arr);
        Predicate<? super Character> pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100);
        Assertions.assertEquals(0,seq.modCount);
      }
      {
        var arr=new char[100];
        var seq=new CheckedList(100,arr);
        CharPredicate pred=val->false;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeCharAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new char[100];
        var seq=new CheckedList(100,arr);
        Predicate<? super Character> pred=val->false;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeCharAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new char[100];
        var seq=new CheckedList(100,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((CharPredicate)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new char[100];
        var seq=new CheckedList(100,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((Predicate<? super Character>)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new char[100];
        var seq=new CheckedList(100,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((CharPredicate)(v)->
          {
            var tmp=seq.removeCharAt(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new char[100];
        var seq=new CheckedList(100,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<? super Character>)(v)->
          {
            var tmp=seq.removeCharAt(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new char[100];
        var seq=new CheckedList(100,arr);
        CharPredicate pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(seq.modCount!=0);
      }
      {
        var arr=new char[100];
        var seq=new CheckedList(100,arr);
        Predicate<? super Character> pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(seq.modCount!=0);
      }
      {
        var arr=new char[100];
        var seq=new CheckedList(100,arr);
        CharPredicate pred=val->true;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeCharAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new char[100];
        var seq=new CheckedList(100,arr);
        Predicate<? super Character> pred=val->true;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeCharAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new char[100];
        var seq=new CheckedList(100,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((CharPredicate)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new char[100];
        var seq=new CheckedList(100,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((Predicate<? super Character>)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new char[100];
        var seq=new CheckedList(100,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((CharPredicate)(v)->
          {
            var tmp=seq.getChar(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new char[100];
        var seq=new CheckedList(100,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<? super Character>)(v)->
          {
            var tmp=seq.getChar(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        CharPredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(2)));
        };
        var arr=new char[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-2);
        for(int i=0,valIndex=0;i<100-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(2)));
        };
        var arr=new char[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-2);
        for(int i=0,valIndex=0;i<100-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        CharPredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(2)));
        };
        var arr=new char[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeCharAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(2)));
        };
        var arr=new char[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeCharAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        CharPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1));
        };
        var arr=new char[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1));
        };
        var arr=new char[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        CharPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1));
        };
        var arr=new char[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeCharAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1));
        };
        var arr=new char[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeCharAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        CharPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(100-1));
        };
        var arr=new char[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTochar(100-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(100-1));
        };
        var arr=new char[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTochar(100-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        CharPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(100-1));
        };
        var arr=new char[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeCharAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(100-1));
        };
        var arr=new char[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeCharAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        CharPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(3-1));
        };
        var arr=new char[3];
        var seq=new CheckedList(3,arr);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTochar(3-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(3-1));
        };
        var arr=new char[3];
        var seq=new CheckedList(3,arr);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTochar(3-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        CharPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(3-1));
        };
        var arr=new char[3];
        var seq=new CheckedList(3,arr);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeCharAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(3-1));
        };
        var arr=new char[3];
        var seq=new CheckedList(3,arr);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeCharAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        CharPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(50-1));
        };
        var arr=new char[50];
        var seq=new CheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTochar(50-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(50-1));
        };
        var arr=new char[50];
        var seq=new CheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTochar(50-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        CharPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(50-1));
        };
        var arr=new char[50];
        var seq=new CheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeCharAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(50-1));
        };
        var arr=new char[50];
        var seq=new CheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeCharAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        CharPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0));
        };
        var arr=new char[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-1);
        for(int i=0,valIndex=1;i<100-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0));
        };
        var arr=new char[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-1);
        for(int i=0,valIndex=1;i<100-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        CharPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0));
        };
        var arr=new char[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeCharAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0));
        };
        var arr=new char[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeCharAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        CharPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0));
        };
        var arr=new char[50];
        var seq=new CheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-1);
        for(int i=0,valIndex=1;i<50-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0));
        };
        var arr=new char[50];
        var seq=new CheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-1);
        for(int i=0,valIndex=1;i<50-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        CharPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0));
        };
        var arr=new char[50];
        var seq=new CheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeCharAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0));
        };
        var arr=new char[50];
        var seq=new CheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeCharAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        CharPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(50-2));
        };
        var arr=new char[50];
        var seq=new CheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-2);
        for(int i=0,valIndex=1;i<50-3;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
        Assertions.assertEquals(arr[50-3],TypeConversionUtil.convertTochar(50-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(50-2));
        };
        var arr=new char[50];
        var seq=new CheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-2);
        for(int i=0,valIndex=1;i<50-3;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
        Assertions.assertEquals(arr[50-3],TypeConversionUtil.convertTochar(50-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        CharPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(50-2));
        };
        var arr=new char[50];
        var seq=new CheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeCharAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(50-2));
        };
        var arr=new char[50];
        var seq=new CheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeCharAt(seq.size()-1);
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
      var val=TypeConversionUtil.convertTochar(i);
      seq.add(val);
    }
    Random rand=new Random(0);
    for(var predicateGenerator:charPredicates.values())
    {
      for(int m=predicateGenerator.getMLo(),mHi=predicateGenerator.getMHi(),repsBound=predicateGenerator.getNumReps();m<=mHi;m=predicateGenerator.incrementM(m))
      {
        for(int reps=0;reps<repsBound;++reps)
        {
          {
            var seqClone=(OmniList.OfChar)seq.clone();
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
            var seqClone=(OmniList.OfChar)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(Character.MIN_VALUE);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(pred));
          }
          {
            var seqClone=(OmniList.OfChar)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              if(!seqClone.isEmpty())
              {
                seqClone.remove(0);
              }
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Character>)pred::test));
          }
          {
            var seqClone=(OmniList.OfChar)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(Character.MIN_VALUE);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Character>)pred::test));
          }
        }
      }
    }
  }
  */
  @Test
  public void testEmptyRemoveIfArrSeqUncheckedSubList()
  {
    CharPredicate filter=charPredicates.MarkAll.getPred(null,0);
    var root=new UncheckedList();
    var seq=root.subList(0,0);
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Character>)filter::test));
  }
  @Test
  public void testRemoveIfUncheckedSubList()
  {
      {
        var arr=new char[100];
        var root=new UncheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        CharPredicate pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100);
      }
      {
        var arr=new char[100];
        var root=new UncheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        Predicate<? super Character> pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100);
      }
      {
        var arr=new char[100];
        var root=new UncheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        CharPredicate pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
      {
        var arr=new char[100];
        var root=new UncheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        Predicate<? super Character> pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
      {
        CharPredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(2)));
        };
        var arr=new char[100];
        var root=new UncheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-2);
        for(int i=0,valIndex=0;i<100-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(2)));
        };
        var arr=new char[100];
        var root=new UncheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-2);
        for(int i=0,valIndex=0;i<100-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
      } 
      {
        CharPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1));
        };
        var arr=new char[100];
        var root=new UncheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1));
        };
        var arr=new char[100];
        var root=new UncheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
      } 
      {
        CharPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(100-1));
        };
        var arr=new char[100];
        var root=new UncheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTochar(100-1));
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(100-1));
        };
        var arr=new char[100];
        var root=new UncheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTochar(100-1));
      } 
      {
        CharPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(3-1));
        };
        var arr=new char[3];
        var root=new UncheckedList(3,arr);
        var subList=root.subList(0,3);
        var seq=subList.subList(0,3);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTochar(3-1));
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(3-1));
        };
        var arr=new char[3];
        var root=new UncheckedList(3,arr);
        var subList=root.subList(0,3);
        var seq=subList.subList(0,3);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTochar(3-1));
      } 
      {
        CharPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(50-1));
        };
        var arr=new char[50];
        var root=new UncheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTochar(50-1));
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(50-1));
        };
        var arr=new char[50];
        var root=new UncheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTochar(50-1));
      } 
      {
        CharPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0));
        };
        var arr=new char[100];
        var root=new UncheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-1);
        for(int i=0,valIndex=1;i<100-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0));
        };
        var arr=new char[100];
        var root=new UncheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-1);
        for(int i=0,valIndex=1;i<100-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
      } 
      {
        CharPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0));
        };
        var arr=new char[50];
        var root=new UncheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-1);
        for(int i=0,valIndex=1;i<50-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0));
        };
        var arr=new char[50];
        var root=new UncheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-1);
        for(int i=0,valIndex=1;i<50-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
      } 
      {
        CharPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(50-2));
        };
        var arr=new char[50];
        var root=new UncheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-2);
        for(int i=0,valIndex=1;i<50-3;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
        Assertions.assertEquals(arr[50-3],TypeConversionUtil.convertTochar(50-1));
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(50-2));
        };
        var arr=new char[50];
        var root=new UncheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-2);
        for(int i=0,valIndex=1;i<50-3;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
        Assertions.assertEquals(arr[50-3],TypeConversionUtil.convertTochar(50-1));
      } 
  }
  @Test
  public void testEmptyRemoveIfArrSeqCheckedSubList()
  {
    CharPredicate filter=charPredicates.MarkAll.getPred(null,0);
    var root=new CheckedList();
    var seq=root.subList(0,0);
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Character>)filter::test));
  }
  @Test
  public void testRemoveIfCheckedSubList()
  {
      {
        var arr=new char[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        CharPredicate pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100);
        Assertions.assertEquals(0,root.modCount);
      }
      {
        var arr=new char[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        Predicate<? super Character> pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100);
        Assertions.assertEquals(0,root.modCount);
      }
      {
        var arr=new char[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        CharPredicate pred=val->false;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeCharAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new char[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        Predicate<? super Character> pred=val->false;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeCharAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new char[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((CharPredicate)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new char[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((Predicate<? super Character>)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new char[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((CharPredicate)(v)->
          {
            var tmp=seq.removeCharAt(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new char[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<? super Character>)(v)->
          {
            var tmp=seq.removeCharAt(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new char[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        CharPredicate pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(root.modCount!=0);
      }
      {
        var arr=new char[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        Predicate<? super Character> pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(root.modCount!=0);
      }
      {
        var arr=new char[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        CharPredicate pred=val->true;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeCharAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new char[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        Predicate<? super Character> pred=val->true;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeCharAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new char[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((CharPredicate)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new char[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((Predicate<? super Character>)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new char[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((CharPredicate)(v)->
          {
            var tmp=seq.getChar(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new char[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<? super Character>)(v)->
          {
            var tmp=seq.getChar(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        CharPredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(2)));
        };
        var arr=new char[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-2);
        for(int i=0,valIndex=0;i<100-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(2)));
        };
        var arr=new char[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-2);
        for(int i=0,valIndex=0;i<100-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        CharPredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(2)));
        };
        var arr=new char[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeCharAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(2)));
        };
        var arr=new char[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeCharAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        CharPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1));
        };
        var arr=new char[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1));
        };
        var arr=new char[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        CharPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1));
        };
        var arr=new char[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeCharAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1));
        };
        var arr=new char[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeCharAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        CharPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(100-1));
        };
        var arr=new char[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTochar(100-1));
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(100-1));
        };
        var arr=new char[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTochar(100-1));
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        CharPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(100-1));
        };
        var arr=new char[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeCharAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(100-1));
        };
        var arr=new char[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeCharAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        CharPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(3-1));
        };
        var arr=new char[3];
        var root=new CheckedList(3,arr);
        var subList=root.subList(0,3);
        var seq=subList.subList(0,3);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTochar(3-1));
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(3-1));
        };
        var arr=new char[3];
        var root=new CheckedList(3,arr);
        var subList=root.subList(0,3);
        var seq=subList.subList(0,3);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTochar(3-1));
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        CharPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(3-1));
        };
        var arr=new char[3];
        var root=new CheckedList(3,arr);
        var subList=root.subList(0,3);
        var seq=subList.subList(0,3);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeCharAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(3-1));
        };
        var arr=new char[3];
        var root=new CheckedList(3,arr);
        var subList=root.subList(0,3);
        var seq=subList.subList(0,3);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeCharAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        CharPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(50-1));
        };
        var arr=new char[50];
        var root=new CheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTochar(50-1));
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(50-1));
        };
        var arr=new char[50];
        var root=new CheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTochar(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTochar(50-1));
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        CharPredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(50-1));
        };
        var arr=new char[50];
        var root=new CheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeCharAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(50-1));
        };
        var arr=new char[50];
        var root=new CheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeCharAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        CharPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0));
        };
        var arr=new char[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-1);
        for(int i=0,valIndex=1;i<100-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0));
        };
        var arr=new char[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-1);
        for(int i=0,valIndex=1;i<100-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        CharPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0));
        };
        var arr=new char[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeCharAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0));
        };
        var arr=new char[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeCharAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        CharPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0));
        };
        var arr=new char[50];
        var root=new CheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-1);
        for(int i=0,valIndex=1;i<50-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0));
        };
        var arr=new char[50];
        var root=new CheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-1);
        for(int i=0,valIndex=1;i<50-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        CharPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0));
        };
        var arr=new char[50];
        var root=new CheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeCharAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0));
        };
        var arr=new char[50];
        var root=new CheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeCharAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        CharPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(50-2));
        };
        var arr=new char[50];
        var root=new CheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-2);
        for(int i=0,valIndex=1;i<50-3;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
        Assertions.assertEquals(arr[50-3],TypeConversionUtil.convertTochar(50-1));
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(50-2));
        };
        var arr=new char[50];
        var root=new CheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-2);
        for(int i=0,valIndex=1;i<50-3;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTochar(valIndex));
        }
        Assertions.assertEquals(arr[50-3],TypeConversionUtil.convertTochar(50-1));
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        CharPredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(50-2));
        };
        var arr=new char[50];
        var root=new CheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeCharAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Character> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(50-2));
        };
        var arr=new char[50];
        var root=new CheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTochar(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeCharAt(seq.size()-1);
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
      var val=TypeConversionUtil.convertTochar(i);
      seq.add(val);
    }
    Random rand=new Random(0);
    for(var predicateGenerator:charPredicates.values())
    {
      for(int m=predicateGenerator.getMLo(),mHi=predicateGenerator.getMHi(),repsBound=predicateGenerator.getNumReps();m<=mHi;m=predicateGenerator.incrementM(m))
      {
        for(int reps=0;reps<repsBound;++reps)
        {
          {
            var seqClone=(OmniList.OfChar)seq.clone();
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
            var seqClone=(OmniList.OfChar)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(Character.MIN_VALUE);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(pred));
          }
          {
            var seqClone=(OmniList.OfChar)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              if(!seqClone.isEmpty())
              {
                seqClone.remove(0);
              }
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Character>)pred::test));
          }
          {
            var seqClone=(OmniList.OfChar)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(Character.MIN_VALUE);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Character>)pred::test));
          }
        }
      }
    }
  }
  */
}
