package omni.impl.seq;
import java.util.function.Predicate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import omni.api.OmniIterator;
import omni.impl.seq.ByteArrSeq.UncheckedList;
import omni.impl.seq.ByteArrSeq.CheckedList;
import omni.impl.seq.ByteArrSeq.UncheckedStack;
import omni.impl.seq.ByteArrSeq.CheckedStack;
import java.util.ConcurrentModificationException;
import omni.impl.CheckedCollectionTest;
import omni.util.TypeConversionUtil;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.util.EqualityUtil;
import java.util.Random;
import omni.util.bytePredicates;
import omni.util.byteArrayBuilder;
import omni.function.BytePredicate;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class ByteSeqRemoveIfTest
{
  @Test
  public void testEmptyRemoveIfArrSeqUncheckedStack()
  {
    BytePredicate filter=bytePredicates.MarkAll.getPred(null,0);
    var seq=new UncheckedStack();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Byte>)filter::test));
  }
  @Test
  public void testRemoveIfUncheckedStack()
  {
      {
        var arr=new byte[100];
        var seq=new UncheckedStack(100,arr);
        BytePredicate pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100);
      }
      {
        var arr=new byte[100];
        var seq=new UncheckedStack(100,arr);
        Predicate<? super Byte> pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100);
      }
      {
        var arr=new byte[100];
        var seq=new UncheckedStack(100,arr);
        BytePredicate pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
      {
        var arr=new byte[100];
        var seq=new UncheckedStack(100,arr);
        Predicate<? super Byte> pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
      {
        BytePredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(2)));
        };
        var arr=new byte[100];
        var seq=new UncheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-2);
        for(int i=0,valIndex=0;i<100-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(2)));
        };
        var arr=new byte[100];
        var seq=new UncheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-2);
        for(int i=0,valIndex=0;i<100-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
      } 
      {
        BytePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1));
        };
        var arr=new byte[100];
        var seq=new UncheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1));
        };
        var arr=new byte[100];
        var seq=new UncheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
      } 
      {
        BytePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(100-1));
        };
        var arr=new byte[100];
        var seq=new UncheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTobyte(100-1));
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(100-1));
        };
        var arr=new byte[100];
        var seq=new UncheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTobyte(100-1));
      } 
      {
        BytePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(3-1));
        };
        var arr=new byte[3];
        var seq=new UncheckedStack(3,arr);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTobyte(3-1));
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(3-1));
        };
        var arr=new byte[3];
        var seq=new UncheckedStack(3,arr);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTobyte(3-1));
      } 
      {
        BytePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(50-1));
        };
        var arr=new byte[50];
        var seq=new UncheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTobyte(50-1));
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(50-1));
        };
        var arr=new byte[50];
        var seq=new UncheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTobyte(50-1));
      } 
      {
        BytePredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0));
        };
        var arr=new byte[100];
        var seq=new UncheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-1);
        for(int i=0,valIndex=1;i<100-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0));
        };
        var arr=new byte[100];
        var seq=new UncheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-1);
        for(int i=0,valIndex=1;i<100-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
      } 
      {
        BytePredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0));
        };
        var arr=new byte[50];
        var seq=new UncheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-1);
        for(int i=0,valIndex=1;i<50-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0));
        };
        var arr=new byte[50];
        var seq=new UncheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-1);
        for(int i=0,valIndex=1;i<50-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
      } 
      {
        BytePredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(50-2));
        };
        var arr=new byte[50];
        var seq=new UncheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-2);
        for(int i=0,valIndex=1;i<50-3;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
        Assertions.assertEquals(arr[50-3],TypeConversionUtil.convertTobyte(50-1));
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(50-2));
        };
        var arr=new byte[50];
        var seq=new UncheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-2);
        for(int i=0,valIndex=1;i<50-3;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
        Assertions.assertEquals(arr[50-3],TypeConversionUtil.convertTobyte(50-1));
      } 
  }
  @Test
  public void testEmptyRemoveIfArrSeqUncheckedList()
  {
    BytePredicate filter=bytePredicates.MarkAll.getPred(null,0);
    var seq=new UncheckedList();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Byte>)filter::test));
  }
  @Test
  public void testRemoveIfUncheckedList()
  {
      {
        var arr=new byte[100];
        var seq=new UncheckedList(100,arr);
        BytePredicate pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100);
      }
      {
        var arr=new byte[100];
        var seq=new UncheckedList(100,arr);
        Predicate<? super Byte> pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100);
      }
      {
        var arr=new byte[100];
        var seq=new UncheckedList(100,arr);
        BytePredicate pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
      {
        var arr=new byte[100];
        var seq=new UncheckedList(100,arr);
        Predicate<? super Byte> pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
      {
        BytePredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(2)));
        };
        var arr=new byte[100];
        var seq=new UncheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-2);
        for(int i=0,valIndex=0;i<100-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(2)));
        };
        var arr=new byte[100];
        var seq=new UncheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-2);
        for(int i=0,valIndex=0;i<100-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
      } 
      {
        BytePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1));
        };
        var arr=new byte[100];
        var seq=new UncheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1));
        };
        var arr=new byte[100];
        var seq=new UncheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
      } 
      {
        BytePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(100-1));
        };
        var arr=new byte[100];
        var seq=new UncheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTobyte(100-1));
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(100-1));
        };
        var arr=new byte[100];
        var seq=new UncheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTobyte(100-1));
      } 
      {
        BytePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(3-1));
        };
        var arr=new byte[3];
        var seq=new UncheckedList(3,arr);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTobyte(3-1));
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(3-1));
        };
        var arr=new byte[3];
        var seq=new UncheckedList(3,arr);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTobyte(3-1));
      } 
      {
        BytePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(50-1));
        };
        var arr=new byte[50];
        var seq=new UncheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTobyte(50-1));
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(50-1));
        };
        var arr=new byte[50];
        var seq=new UncheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTobyte(50-1));
      } 
      {
        BytePredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0));
        };
        var arr=new byte[100];
        var seq=new UncheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-1);
        for(int i=0,valIndex=1;i<100-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0));
        };
        var arr=new byte[100];
        var seq=new UncheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-1);
        for(int i=0,valIndex=1;i<100-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
      } 
      {
        BytePredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0));
        };
        var arr=new byte[50];
        var seq=new UncheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-1);
        for(int i=0,valIndex=1;i<50-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0));
        };
        var arr=new byte[50];
        var seq=new UncheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-1);
        for(int i=0,valIndex=1;i<50-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
      } 
      {
        BytePredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(50-2));
        };
        var arr=new byte[50];
        var seq=new UncheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-2);
        for(int i=0,valIndex=1;i<50-3;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
        Assertions.assertEquals(arr[50-3],TypeConversionUtil.convertTobyte(50-1));
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(50-2));
        };
        var arr=new byte[50];
        var seq=new UncheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-2);
        for(int i=0,valIndex=1;i<50-3;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
        Assertions.assertEquals(arr[50-3],TypeConversionUtil.convertTobyte(50-1));
      } 
  }
  @Test
  public void testEmptyRemoveIfArrSeqCheckedStack()
  {
    BytePredicate filter=bytePredicates.MarkAll.getPred(null,0);
    var seq=new CheckedStack();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Byte>)filter::test));
  }
  @Test
  public void testRemoveIfCheckedStack()
  {
      {
        var arr=new byte[100];
        var seq=new CheckedStack(100,arr);
        BytePredicate pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100);
        Assertions.assertEquals(0,seq.modCount);
      }
      {
        var arr=new byte[100];
        var seq=new CheckedStack(100,arr);
        Predicate<? super Byte> pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100);
        Assertions.assertEquals(0,seq.modCount);
      }
      {
        var arr=new byte[100];
        var seq=new CheckedStack(100,arr);
        BytePredicate pred=val->false;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popByte();
          seq.push(tmp);
        })));
      }
      {
        var arr=new byte[100];
        var seq=new CheckedStack(100,arr);
        Predicate<? super Byte> pred=val->false;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popByte();
          seq.push(tmp);
        })));
      }
      {
        var arr=new byte[100];
        var seq=new CheckedStack(100,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((BytePredicate)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new byte[100];
        var seq=new CheckedStack(100,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((Predicate<? super Byte>)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new byte[100];
        var seq=new CheckedStack(100,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((BytePredicate)(v)->
          {
            var tmp=seq.popByte();
            seq.push(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new byte[100];
        var seq=new CheckedStack(100,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<? super Byte>)(v)->
          {
            var tmp=seq.popByte();
            seq.push(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new byte[100];
        var seq=new CheckedStack(100,arr);
        BytePredicate pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(seq.modCount!=0);
      }
      {
        var arr=new byte[100];
        var seq=new CheckedStack(100,arr);
        Predicate<? super Byte> pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(seq.modCount!=0);
      }
      {
        var arr=new byte[100];
        var seq=new CheckedStack(100,arr);
        BytePredicate pred=val->true;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popByte();
          seq.push(tmp);
        })));
      }
      {
        var arr=new byte[100];
        var seq=new CheckedStack(100,arr);
        Predicate<? super Byte> pred=val->true;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popByte();
          seq.push(tmp);
        })));
      }
      {
        var arr=new byte[100];
        var seq=new CheckedStack(100,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((BytePredicate)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new byte[100];
        var seq=new CheckedStack(100,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((Predicate<? super Byte>)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new byte[100];
        var seq=new CheckedStack(100,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((BytePredicate)(v)->
          {
            var tmp=seq.popByte();
            seq.push(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new byte[100];
        var seq=new CheckedStack(100,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<? super Byte>)(v)->
          {
            var tmp=seq.popByte();
            seq.push(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        BytePredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(2)));
        };
        var arr=new byte[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-2);
        for(int i=0,valIndex=0;i<100-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(2)));
        };
        var arr=new byte[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-2);
        for(int i=0,valIndex=0;i<100-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        BytePredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(2)));
        };
        var arr=new byte[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popByte();
          seq.push(tmp);
        })));
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(2)));
        };
        var arr=new byte[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popByte();
          seq.push(tmp);
        })));
      } 
      {
        BytePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1));
        };
        var arr=new byte[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1));
        };
        var arr=new byte[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        BytePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1));
        };
        var arr=new byte[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popByte();
          seq.push(tmp);
        })));
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1));
        };
        var arr=new byte[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popByte();
          seq.push(tmp);
        })));
      } 
      {
        BytePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(100-1));
        };
        var arr=new byte[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTobyte(100-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(100-1));
        };
        var arr=new byte[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTobyte(100-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        BytePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(100-1));
        };
        var arr=new byte[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popByte();
          seq.push(tmp);
        })));
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(100-1));
        };
        var arr=new byte[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popByte();
          seq.push(tmp);
        })));
      } 
      {
        BytePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(3-1));
        };
        var arr=new byte[3];
        var seq=new CheckedStack(3,arr);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTobyte(3-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(3-1));
        };
        var arr=new byte[3];
        var seq=new CheckedStack(3,arr);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTobyte(3-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        BytePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(3-1));
        };
        var arr=new byte[3];
        var seq=new CheckedStack(3,arr);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popByte();
          seq.push(tmp);
        })));
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(3-1));
        };
        var arr=new byte[3];
        var seq=new CheckedStack(3,arr);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popByte();
          seq.push(tmp);
        })));
      } 
      {
        BytePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(50-1));
        };
        var arr=new byte[50];
        var seq=new CheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTobyte(50-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(50-1));
        };
        var arr=new byte[50];
        var seq=new CheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTobyte(50-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        BytePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(50-1));
        };
        var arr=new byte[50];
        var seq=new CheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popByte();
          seq.push(tmp);
        })));
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(50-1));
        };
        var arr=new byte[50];
        var seq=new CheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popByte();
          seq.push(tmp);
        })));
      } 
      {
        BytePredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0));
        };
        var arr=new byte[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-1);
        for(int i=0,valIndex=1;i<100-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0));
        };
        var arr=new byte[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-1);
        for(int i=0,valIndex=1;i<100-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        BytePredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0));
        };
        var arr=new byte[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popByte();
          seq.push(tmp);
        })));
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0));
        };
        var arr=new byte[100];
        var seq=new CheckedStack(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popByte();
          seq.push(tmp);
        })));
      } 
      {
        BytePredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0));
        };
        var arr=new byte[50];
        var seq=new CheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-1);
        for(int i=0,valIndex=1;i<50-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0));
        };
        var arr=new byte[50];
        var seq=new CheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-1);
        for(int i=0,valIndex=1;i<50-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        BytePredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0));
        };
        var arr=new byte[50];
        var seq=new CheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popByte();
          seq.push(tmp);
        })));
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0));
        };
        var arr=new byte[50];
        var seq=new CheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popByte();
          seq.push(tmp);
        })));
      } 
      {
        BytePredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(50-2));
        };
        var arr=new byte[50];
        var seq=new CheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-2);
        for(int i=0,valIndex=1;i<50-3;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
        Assertions.assertEquals(arr[50-3],TypeConversionUtil.convertTobyte(50-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(50-2));
        };
        var arr=new byte[50];
        var seq=new CheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-2);
        for(int i=0,valIndex=1;i<50-3;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
        Assertions.assertEquals(arr[50-3],TypeConversionUtil.convertTobyte(50-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        BytePredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(50-2));
        };
        var arr=new byte[50];
        var seq=new CheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popByte();
          seq.push(tmp);
        })));
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(50-2));
        };
        var arr=new byte[50];
        var seq=new CheckedStack(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popByte();
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
      var val=TypeConversionUtil.convertTobyte(i);
      seq.add(val);
    }
    Random rand=new Random(0);
    for(var predicateGenerator:bytePredicates.values())
    {
      for(int m=predicateGenerator.getMLo(),mHi=predicateGenerator.getMHi(),repsBound=predicateGenerator.getNumReps();m<=mHi;m=predicateGenerator.incrementM(m))
      {
        for(int reps=0;reps<repsBound;++reps)
        {
          {
            var seqClone=(OmniStack.OfByte)seq.clone();
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
            var seqClone=(OmniStack.OfByte)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(Byte.MIN_VALUE);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(pred));
          }
          {
            var seqClone=(OmniStack.OfByte)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              if(!seqClone.isEmpty())
              {
                seqClone.pop();
              }
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Byte>)pred::test));
          }
          {
            var seqClone=(OmniStack.OfByte)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(Byte.MIN_VALUE);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Byte>)pred::test));
          }
        }
      }
    }
  }
  */
  @Test
  public void testEmptyRemoveIfArrSeqCheckedList()
  {
    BytePredicate filter=bytePredicates.MarkAll.getPred(null,0);
    var seq=new CheckedList();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Byte>)filter::test));
  }
  @Test
  public void testRemoveIfCheckedList()
  {
      {
        var arr=new byte[100];
        var seq=new CheckedList(100,arr);
        BytePredicate pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100);
        Assertions.assertEquals(0,seq.modCount);
      }
      {
        var arr=new byte[100];
        var seq=new CheckedList(100,arr);
        Predicate<? super Byte> pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100);
        Assertions.assertEquals(0,seq.modCount);
      }
      {
        var arr=new byte[100];
        var seq=new CheckedList(100,arr);
        BytePredicate pred=val->false;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeByteAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new byte[100];
        var seq=new CheckedList(100,arr);
        Predicate<? super Byte> pred=val->false;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeByteAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new byte[100];
        var seq=new CheckedList(100,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((BytePredicate)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new byte[100];
        var seq=new CheckedList(100,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((Predicate<? super Byte>)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new byte[100];
        var seq=new CheckedList(100,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((BytePredicate)(v)->
          {
            var tmp=seq.removeByteAt(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new byte[100];
        var seq=new CheckedList(100,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<? super Byte>)(v)->
          {
            var tmp=seq.removeByteAt(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new byte[100];
        var seq=new CheckedList(100,arr);
        BytePredicate pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(seq.modCount!=0);
      }
      {
        var arr=new byte[100];
        var seq=new CheckedList(100,arr);
        Predicate<? super Byte> pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(seq.modCount!=0);
      }
      {
        var arr=new byte[100];
        var seq=new CheckedList(100,arr);
        BytePredicate pred=val->true;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeByteAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new byte[100];
        var seq=new CheckedList(100,arr);
        Predicate<? super Byte> pred=val->true;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeByteAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new byte[100];
        var seq=new CheckedList(100,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((BytePredicate)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new byte[100];
        var seq=new CheckedList(100,arr);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((Predicate<? super Byte>)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new byte[100];
        var seq=new CheckedList(100,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((BytePredicate)(v)->
          {
            var tmp=seq.getByte(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new byte[100];
        var seq=new CheckedList(100,arr);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<? super Byte>)(v)->
          {
            var tmp=seq.getByte(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        BytePredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(2)));
        };
        var arr=new byte[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-2);
        for(int i=0,valIndex=0;i<100-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(2)));
        };
        var arr=new byte[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-2);
        for(int i=0,valIndex=0;i<100-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        BytePredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(2)));
        };
        var arr=new byte[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeByteAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(2)));
        };
        var arr=new byte[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeByteAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        BytePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1));
        };
        var arr=new byte[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1));
        };
        var arr=new byte[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        BytePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1));
        };
        var arr=new byte[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeByteAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1));
        };
        var arr=new byte[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeByteAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        BytePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(100-1));
        };
        var arr=new byte[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTobyte(100-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(100-1));
        };
        var arr=new byte[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTobyte(100-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        BytePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(100-1));
        };
        var arr=new byte[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeByteAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(100-1));
        };
        var arr=new byte[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeByteAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        BytePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(3-1));
        };
        var arr=new byte[3];
        var seq=new CheckedList(3,arr);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTobyte(3-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(3-1));
        };
        var arr=new byte[3];
        var seq=new CheckedList(3,arr);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTobyte(3-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        BytePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(3-1));
        };
        var arr=new byte[3];
        var seq=new CheckedList(3,arr);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeByteAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(3-1));
        };
        var arr=new byte[3];
        var seq=new CheckedList(3,arr);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeByteAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        BytePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(50-1));
        };
        var arr=new byte[50];
        var seq=new CheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTobyte(50-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(50-1));
        };
        var arr=new byte[50];
        var seq=new CheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTobyte(50-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        BytePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(50-1));
        };
        var arr=new byte[50];
        var seq=new CheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeByteAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(50-1));
        };
        var arr=new byte[50];
        var seq=new CheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeByteAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        BytePredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0));
        };
        var arr=new byte[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-1);
        for(int i=0,valIndex=1;i<100-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0));
        };
        var arr=new byte[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-1);
        for(int i=0,valIndex=1;i<100-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        BytePredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0));
        };
        var arr=new byte[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeByteAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0));
        };
        var arr=new byte[100];
        var seq=new CheckedList(100,arr);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeByteAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        BytePredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0));
        };
        var arr=new byte[50];
        var seq=new CheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-1);
        for(int i=0,valIndex=1;i<50-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0));
        };
        var arr=new byte[50];
        var seq=new CheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-1);
        for(int i=0,valIndex=1;i<50-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        BytePredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0));
        };
        var arr=new byte[50];
        var seq=new CheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeByteAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0));
        };
        var arr=new byte[50];
        var seq=new CheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeByteAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        BytePredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(50-2));
        };
        var arr=new byte[50];
        var seq=new CheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-2);
        for(int i=0,valIndex=1;i<50-3;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
        Assertions.assertEquals(arr[50-3],TypeConversionUtil.convertTobyte(50-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(50-2));
        };
        var arr=new byte[50];
        var seq=new CheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-2);
        for(int i=0,valIndex=1;i<50-3;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
        Assertions.assertEquals(arr[50-3],TypeConversionUtil.convertTobyte(50-1));
        Assertions.assertTrue(seq.modCount!=0);
      } 
      {
        BytePredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(50-2));
        };
        var arr=new byte[50];
        var seq=new CheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeByteAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(50-2));
        };
        var arr=new byte[50];
        var seq=new CheckedList(50,arr);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeByteAt(seq.size()-1);
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
      var val=TypeConversionUtil.convertTobyte(i);
      seq.add(val);
    }
    Random rand=new Random(0);
    for(var predicateGenerator:bytePredicates.values())
    {
      for(int m=predicateGenerator.getMLo(),mHi=predicateGenerator.getMHi(),repsBound=predicateGenerator.getNumReps();m<=mHi;m=predicateGenerator.incrementM(m))
      {
        for(int reps=0;reps<repsBound;++reps)
        {
          {
            var seqClone=(OmniList.OfByte)seq.clone();
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
            var seqClone=(OmniList.OfByte)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(Byte.MIN_VALUE);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(pred));
          }
          {
            var seqClone=(OmniList.OfByte)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              if(!seqClone.isEmpty())
              {
                seqClone.remove(0);
              }
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Byte>)pred::test));
          }
          {
            var seqClone=(OmniList.OfByte)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(Byte.MIN_VALUE);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Byte>)pred::test));
          }
        }
      }
    }
  }
  */
  @Test
  public void testEmptyRemoveIfArrSeqUncheckedSubList()
  {
    BytePredicate filter=bytePredicates.MarkAll.getPred(null,0);
    var root=new UncheckedList();
    var seq=root.subList(0,0);
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Byte>)filter::test));
  }
  @Test
  public void testRemoveIfUncheckedSubList()
  {
      {
        var arr=new byte[100];
        var root=new UncheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        BytePredicate pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100);
      }
      {
        var arr=new byte[100];
        var root=new UncheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        Predicate<? super Byte> pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100);
      }
      {
        var arr=new byte[100];
        var root=new UncheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        BytePredicate pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
      {
        var arr=new byte[100];
        var root=new UncheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        Predicate<? super Byte> pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
      {
        BytePredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(2)));
        };
        var arr=new byte[100];
        var root=new UncheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-2);
        for(int i=0,valIndex=0;i<100-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(2)));
        };
        var arr=new byte[100];
        var root=new UncheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-2);
        for(int i=0,valIndex=0;i<100-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
      } 
      {
        BytePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1));
        };
        var arr=new byte[100];
        var root=new UncheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1));
        };
        var arr=new byte[100];
        var root=new UncheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
      } 
      {
        BytePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(100-1));
        };
        var arr=new byte[100];
        var root=new UncheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTobyte(100-1));
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(100-1));
        };
        var arr=new byte[100];
        var root=new UncheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTobyte(100-1));
      } 
      {
        BytePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(3-1));
        };
        var arr=new byte[3];
        var root=new UncheckedList(3,arr);
        var subList=root.subList(0,3);
        var seq=subList.subList(0,3);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTobyte(3-1));
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(3-1));
        };
        var arr=new byte[3];
        var root=new UncheckedList(3,arr);
        var subList=root.subList(0,3);
        var seq=subList.subList(0,3);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTobyte(3-1));
      } 
      {
        BytePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(50-1));
        };
        var arr=new byte[50];
        var root=new UncheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTobyte(50-1));
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(50-1));
        };
        var arr=new byte[50];
        var root=new UncheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTobyte(50-1));
      } 
      {
        BytePredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0));
        };
        var arr=new byte[100];
        var root=new UncheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-1);
        for(int i=0,valIndex=1;i<100-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0));
        };
        var arr=new byte[100];
        var root=new UncheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-1);
        for(int i=0,valIndex=1;i<100-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
      } 
      {
        BytePredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0));
        };
        var arr=new byte[50];
        var root=new UncheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-1);
        for(int i=0,valIndex=1;i<50-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0));
        };
        var arr=new byte[50];
        var root=new UncheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-1);
        for(int i=0,valIndex=1;i<50-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
      } 
      {
        BytePredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(50-2));
        };
        var arr=new byte[50];
        var root=new UncheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-2);
        for(int i=0,valIndex=1;i<50-3;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
        Assertions.assertEquals(arr[50-3],TypeConversionUtil.convertTobyte(50-1));
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(50-2));
        };
        var arr=new byte[50];
        var root=new UncheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-2);
        for(int i=0,valIndex=1;i<50-3;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
        Assertions.assertEquals(arr[50-3],TypeConversionUtil.convertTobyte(50-1));
      } 
  }
  @Test
  public void testEmptyRemoveIfArrSeqCheckedSubList()
  {
    BytePredicate filter=bytePredicates.MarkAll.getPred(null,0);
    var root=new CheckedList();
    var seq=root.subList(0,0);
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Byte>)filter::test));
  }
  @Test
  public void testRemoveIfCheckedSubList()
  {
      {
        var arr=new byte[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        BytePredicate pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100);
        Assertions.assertEquals(0,root.modCount);
      }
      {
        var arr=new byte[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        Predicate<? super Byte> pred=val->false;
        Assertions.assertFalse(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100);
        Assertions.assertEquals(0,root.modCount);
      }
      {
        var arr=new byte[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        BytePredicate pred=val->false;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeByteAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new byte[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        Predicate<? super Byte> pred=val->false;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeByteAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new byte[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((BytePredicate)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new byte[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((Predicate<? super Byte>)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new byte[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((BytePredicate)(v)->
          {
            var tmp=seq.removeByteAt(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new byte[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<? super Byte>)(v)->
          {
            var tmp=seq.removeByteAt(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new byte[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        BytePredicate pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(root.modCount!=0);
      }
      {
        var arr=new byte[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        Predicate<? super Byte> pred=val->true;
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(root.modCount!=0);
      }
      {
        var arr=new byte[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        BytePredicate pred=val->true;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeByteAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new byte[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        Predicate<? super Byte> pred=val->true;
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeByteAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
      {
        var arr=new byte[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((BytePredicate)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new byte[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((Predicate<? super Byte>)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new byte[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((BytePredicate)(v)->
          {
            var tmp=seq.getByte(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        var arr=new byte[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<? super Byte>)(v)->
          {
            var tmp=seq.getByte(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
      {
        BytePredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(2)));
        };
        var arr=new byte[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-2);
        for(int i=0,valIndex=0;i<100-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(2)));
        };
        var arr=new byte[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-2);
        for(int i=0,valIndex=0;i<100-2;++i,++valIndex)
        {
          if(i==0 || i==1)
          {
            ++valIndex;
          }
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        BytePredicate pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(2)));
        };
        var arr=new byte[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeByteAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return (EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0))
            ||
             EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(2)));
        };
        var arr=new byte[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeByteAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        BytePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1));
        };
        var arr=new byte[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1));
        };
        var arr=new byte[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),1);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        BytePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1));
        };
        var arr=new byte[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeByteAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1));
        };
        var arr=new byte[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeByteAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        BytePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(100-1));
        };
        var arr=new byte[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTobyte(100-1));
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(100-1));
        };
        var arr=new byte[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTobyte(100-1));
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        BytePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(100-1));
        };
        var arr=new byte[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeByteAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(100-1));
        };
        var arr=new byte[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeByteAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        BytePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(3-1));
        };
        var arr=new byte[3];
        var root=new CheckedList(3,arr);
        var subList=root.subList(0,3);
        var seq=subList.subList(0,3);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTobyte(3-1));
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(3-1));
        };
        var arr=new byte[3];
        var root=new CheckedList(3,arr);
        var subList=root.subList(0,3);
        var seq=subList.subList(0,3);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTobyte(3-1));
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        BytePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(3-1));
        };
        var arr=new byte[3];
        var root=new CheckedList(3,arr);
        var subList=root.subList(0,3);
        var seq=subList.subList(0,3);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeByteAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(3-1));
        };
        var arr=new byte[3];
        var root=new CheckedList(3,arr);
        var subList=root.subList(0,3);
        var seq=subList.subList(0,3);
        for(int i=0;i<3;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeByteAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        BytePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(50-1));
        };
        var arr=new byte[50];
        var root=new CheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTobyte(50-1));
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(50-1));
        };
        var arr=new byte[50];
        var root=new CheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),2);
        Assertions.assertEquals(arr[0],TypeConversionUtil.convertTobyte(1));
        Assertions.assertEquals(arr[1],TypeConversionUtil.convertTobyte(50-1));
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        BytePredicate pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(50-1));
        };
        var arr=new byte[50];
        var root=new CheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeByteAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(50-1));
        };
        var arr=new byte[50];
        var root=new CheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeByteAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        BytePredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0));
        };
        var arr=new byte[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-1);
        for(int i=0,valIndex=1;i<100-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0));
        };
        var arr=new byte[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),100-1);
        for(int i=0,valIndex=1;i<100-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        BytePredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0));
        };
        var arr=new byte[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeByteAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0));
        };
        var arr=new byte[100];
        var root=new CheckedList(100,arr);
        var subList=root.subList(0,100);
        var seq=subList.subList(0,100);
        for(int i=0;i<100;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeByteAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        BytePredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0));
        };
        var arr=new byte[50];
        var root=new CheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-1);
        for(int i=0,valIndex=1;i<50-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0));
        };
        var arr=new byte[50];
        var root=new CheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-1);
        for(int i=0,valIndex=1;i<50-1;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        BytePredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0));
        };
        var arr=new byte[50];
        var root=new CheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeByteAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0));
        };
        var arr=new byte[50];
        var root=new CheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeByteAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        BytePredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(50-2));
        };
        var arr=new byte[50];
        var root=new CheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-2);
        for(int i=0,valIndex=1;i<50-3;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
        Assertions.assertEquals(arr[50-3],TypeConversionUtil.convertTobyte(50-1));
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(50-2));
        };
        var arr=new byte[50];
        var root=new CheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertTrue(seq.removeIf(pred));
        Assertions.assertEquals(seq.size(),50-2);
        for(int i=0,valIndex=1;i<50-3;++i,++valIndex)
        {
          Assertions.assertEquals(arr[i],TypeConversionUtil.convertTobyte(valIndex));
        }
        Assertions.assertEquals(arr[50-3],TypeConversionUtil.convertTobyte(50-1));
        Assertions.assertTrue(root.modCount!=0);
      } 
      {
        BytePredicate pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(50-2));
        };
        var arr=new byte[50];
        var root=new CheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeByteAt(seq.size()-1);
          seq.add(tmp);
        })));
      } 
      {
        Predicate<? super Byte> pred=(val)->
        {
          return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(50-2));
        };
        var arr=new byte[50];
        var root=new CheckedList(50,arr);
        var subList=root.subList(0,50);
        var seq=subList.subList(0,50);
        for(int i=0;i<50;++i)
        {
          arr[i]=TypeConversionUtil.convertTobyte(i);
        }
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeByteAt(seq.size()-1);
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
      var val=TypeConversionUtil.convertTobyte(i);
      seq.add(val);
    }
    Random rand=new Random(0);
    for(var predicateGenerator:bytePredicates.values())
    {
      for(int m=predicateGenerator.getMLo(),mHi=predicateGenerator.getMHi(),repsBound=predicateGenerator.getNumReps();m<=mHi;m=predicateGenerator.incrementM(m))
      {
        for(int reps=0;reps<repsBound;++reps)
        {
          {
            var seqClone=(OmniList.OfByte)seq.clone();
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
            var seqClone=(OmniList.OfByte)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(Byte.MIN_VALUE);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(pred));
          }
          {
            var seqClone=(OmniList.OfByte)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              if(!seqClone.isEmpty())
              {
                seqClone.remove(0);
              }
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Byte>)pred::test));
          }
          {
            var seqClone=(OmniList.OfByte)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(Byte.MIN_VALUE);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Byte>)pred::test));
          }
        }
      }
    }
  }
  */
}
