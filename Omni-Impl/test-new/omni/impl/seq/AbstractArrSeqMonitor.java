package omni.impl.seq;

import java.io.Externalizable;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import omni.api.OmniCollection;
import omni.impl.CheckedType;
import omni.impl.DataType;
import omni.impl.MonitoredObjectOutputStream;
import omni.impl.MonitoredRemoveIfPredicate;
import omni.impl.MonitoredSequence;
import omni.util.OmniArray;

public abstract class AbstractArrSeqMonitor<SEQ extends OmniCollection<?>> implements MonitoredSequence<SEQ>{
  final DataType dataType;
  final CheckedType checkedType;
  final SEQ seq;
  int expectedSize;
  int expectedCapacity;
  int expectedModCount;
  Object expectedArr;
  abstract SEQ initSeq();
  abstract SEQ initSeq(int initCap);
  AbstractArrSeqMonitor(CheckedType checkedType,DataType dataType){
    this.dataType=dataType;
    this.checkedType=checkedType;
    this.seq=initSeq();
    updateCollectionState();
  }
  AbstractArrSeqMonitor(CheckedType checkedType,DataType dataType,int initCap){
    this.dataType=dataType;
    this.checkedType=checkedType;
    this.seq=initSeq(initCap);
    updateCollectionState();
  }
  @Override public CheckedType getCheckedType(){
    return checkedType;
  }
  @Override public SEQ getCollection(){
    return seq;
  }
  @Override public DataType getDataType(){
    return dataType;
  }
  @Override public void modParent(){
    throw new UnsupportedOperationException();
  }
  @Override public void modRoot(){
    throw new UnsupportedOperationException();
  }
  @Override public int size(){
    return expectedSize;
  }
  @Override public void updateClearState(){
    expectedSize=0;
    ++expectedModCount;
    if(dataType==DataType.REF) {
      var arr=(Object[])expectedArr;
      for(int i=expectedCapacity;--i>=0;) {
        arr[i]=null;
      }
    }
  }
  @Override public void updateCollectionState(){
    this.expectedSize=((AbstractSeq<?>)seq).size;
    if(checkedType.checked) {
      updateModCount();
    }
    switch(dataType) {
    case BOOLEAN:{
      var castSeq=(BooleanArrSeq)seq;
      var actualArr=castSeq.arr;
      var expectedArr=(boolean[])this.expectedArr;
      if(actualArr==null || actualArr==OmniArray.OfBoolean.DEFAULT_ARR) {
        this.expectedArr=actualArr;
        this.expectedCapacity=0;
      }else {
        if(expectedArr==null) {
          System.arraycopy(actualArr,0,this.expectedArr=new boolean[actualArr.length],0,expectedSize);
        }else {
          if(expectedArr.length!=actualArr.length) {
            this.expectedArr=expectedArr=new boolean[actualArr.length];
          }
          System.arraycopy(actualArr,0,expectedArr,0,expectedSize);
        }
        this.expectedCapacity=actualArr.length;
      }
      break;
    }
    case BYTE:{
      var castSeq=(ByteArrSeq)seq;
      var actualArr=castSeq.arr;
      var expectedArr=(byte[])this.expectedArr;
      if(actualArr==null || actualArr==OmniArray.OfByte.DEFAULT_ARR) {
        this.expectedArr=actualArr;
        this.expectedCapacity=0;
      }else {
        if(expectedArr==null) {
          System.arraycopy(actualArr,0,this.expectedArr=new byte[actualArr.length],0,expectedSize);
        }else {
          if(expectedArr.length!=actualArr.length) {
            this.expectedArr=expectedArr=new byte[actualArr.length];
          }
          System.arraycopy(actualArr,0,expectedArr,0,expectedSize);
        }
        this.expectedCapacity=actualArr.length;
      }
      break;
    }
    case CHAR:{
      var castSeq=(CharArrSeq)seq;
      var actualArr=castSeq.arr;
      var expectedArr=(char[])this.expectedArr;
      if(actualArr==null || actualArr==OmniArray.OfChar.DEFAULT_ARR) {
        this.expectedArr=actualArr;
        this.expectedCapacity=0;
      }else {
        if(expectedArr==null) {
          System.arraycopy(actualArr,0,this.expectedArr=new char[actualArr.length],0,expectedSize);
        }else {
          if(expectedArr.length!=actualArr.length) {
            this.expectedArr=expectedArr=new char[actualArr.length];
          }
          System.arraycopy(actualArr,0,expectedArr,0,expectedSize);
        }
        this.expectedCapacity=actualArr.length;
      }
      break;
    }
    case SHORT:{
      var castSeq=(ShortArrSeq)seq;
      var actualArr=castSeq.arr;
      var expectedArr=(short[])this.expectedArr;
      if(actualArr==null || actualArr==OmniArray.OfShort.DEFAULT_ARR) {
        this.expectedArr=actualArr;
        this.expectedCapacity=0;
      }else {
        if(expectedArr==null) {
          System.arraycopy(actualArr,0,this.expectedArr=new short[actualArr.length],0,expectedSize);
        }else {
          if(expectedArr.length!=actualArr.length) {
            this.expectedArr=expectedArr=new short[actualArr.length];
          }
          System.arraycopy(actualArr,0,expectedArr,0,expectedSize);
        }
        this.expectedCapacity=actualArr.length;
      }
      break;
    }
    case INT:{
      var castSeq=(IntArrSeq)seq;
      var actualArr=castSeq.arr;
      var expectedArr=(int[])this.expectedArr;
      if(actualArr==null || actualArr==OmniArray.OfInt.DEFAULT_ARR) {
        this.expectedArr=actualArr;
        this.expectedCapacity=0;
      }else {
        if(expectedArr==null) {
          System.arraycopy(actualArr,0,this.expectedArr=new int[actualArr.length],0,expectedSize);
        }else {
          if(expectedArr.length!=actualArr.length) {
            this.expectedArr=expectedArr=new int[actualArr.length];
          }
          System.arraycopy(actualArr,0,expectedArr,0,expectedSize);
        }
        this.expectedCapacity=actualArr.length;
      }
      break;
    }
    case LONG:{
      var castSeq=(LongArrSeq)seq;
      var actualArr=castSeq.arr;
      var expectedArr=(long[])this.expectedArr;
      if(actualArr==null || actualArr==OmniArray.OfLong.DEFAULT_ARR) {
        this.expectedArr=actualArr;
        this.expectedCapacity=0;
      }else {
        if(expectedArr==null) {
          System.arraycopy(actualArr,0,this.expectedArr=new long[actualArr.length],0,expectedSize);
        }else {
          if(expectedArr.length!=actualArr.length) {
            this.expectedArr=expectedArr=new long[actualArr.length];
          }
          System.arraycopy(actualArr,0,expectedArr,0,expectedSize);
        }
        this.expectedCapacity=actualArr.length;
      }
      break;
    }
    case FLOAT:{
      var castSeq=(FloatArrSeq)seq;
      var actualArr=castSeq.arr;
      var expectedArr=(float[])this.expectedArr;
      if(actualArr==null || actualArr==OmniArray.OfFloat.DEFAULT_ARR) {
        this.expectedArr=actualArr;
        this.expectedCapacity=0;
      }else {
        if(expectedArr==null) {
          System.arraycopy(actualArr,0,this.expectedArr=new float[actualArr.length],0,expectedSize);
        }else {
          if(expectedArr.length!=actualArr.length) {
            this.expectedArr=expectedArr=new float[actualArr.length];
          }
          System.arraycopy(actualArr,0,expectedArr,0,expectedSize);
        }
        this.expectedCapacity=actualArr.length;
      }
      break;
    }
    case DOUBLE:{
      var castSeq=(DoubleArrSeq)seq;
      var actualArr=castSeq.arr;
      var expectedArr=(double[])this.expectedArr;
      if(actualArr==null || actualArr==OmniArray.OfDouble.DEFAULT_ARR) {
        this.expectedArr=actualArr;
        this.expectedCapacity=0;
      }else {
        if(expectedArr==null) {
          System.arraycopy(actualArr,0,this.expectedArr=new double[actualArr.length],0,expectedSize);
        }else {
          if(expectedArr.length!=actualArr.length) {
            this.expectedArr=expectedArr=new double[actualArr.length];
          }
          System.arraycopy(actualArr,0,expectedArr,0,expectedSize);
        }
        this.expectedCapacity=actualArr.length;
      }
      break;
    }
    case REF:{
      var castSeq=(RefArrSeq<?>)seq;
      var actualArr=castSeq.arr;
      var expectedArr=(Object[])this.expectedArr;
      if(actualArr==null || actualArr==OmniArray.OfRef.DEFAULT_ARR) {
        this.expectedArr=actualArr;
        this.expectedCapacity=0;
      }else {
        if(expectedArr==null) {
          System.arraycopy(actualArr,0,this.expectedArr=new Object[actualArr.length],0,expectedSize);
        }else {
          if(expectedArr.length!=actualArr.length) {
            this.expectedArr=expectedArr=new Object[actualArr.length];
          }
          System.arraycopy(actualArr,0,expectedArr,0,expectedSize);
        }
        this.expectedCapacity=actualArr.length;
      }
      break;
    }
    default:
      throw dataType.invalid();
    }
  }
  @Override public void verifyCollectionState(){
    int expectedSize;
    Assertions.assertEquals(expectedSize=this.expectedSize,((AbstractSeq<?>)seq).size);
    if(checkedType.checked) {
      verifyModCount();
    }
    switch(dataType) {
    case BOOLEAN:{
      var actualArr=((BooleanArrSeq)seq).arr;
      int expectedCapacity;
      if((expectedCapacity=this.expectedCapacity)==0) {
        Assertions.assertSame(expectedArr,actualArr);
      }else {
        Assertions.assertEquals(expectedCapacity,actualArr.length);
        var expectedArr=(boolean[])this.expectedArr;
        while(expectedSize!=0) {
          Assertions.assertEquals(expectedArr[--expectedSize],actualArr[expectedSize]);
        }
      }
      break;
    }
    case BYTE:{
      var actualArr=((ByteArrSeq)seq).arr;
      int expectedCapacity;
      if((expectedCapacity=this.expectedCapacity)==0) {
        Assertions.assertSame(expectedArr,actualArr);
      }else {
        Assertions.assertEquals(expectedCapacity,actualArr.length);
        var expectedArr=(byte[])this.expectedArr;
        while(expectedSize!=0) {
          Assertions.assertEquals(expectedArr[--expectedSize],actualArr[expectedSize]);
        }
      }
      break;
    }
    case CHAR:{
      var actualArr=((CharArrSeq)seq).arr;
      int expectedCapacity;
      if((expectedCapacity=this.expectedCapacity)==0) {
        Assertions.assertSame(expectedArr,actualArr);
      }else {
        Assertions.assertEquals(expectedCapacity,actualArr.length);
        var expectedArr=(char[])this.expectedArr;
        while(expectedSize!=0) {
          Assertions.assertEquals(expectedArr[--expectedSize],actualArr[expectedSize]);
        }
      }
      break;
    }
    case DOUBLE:{
      var actualArr=((DoubleArrSeq)seq).arr;
      int expectedCapacity;
      if((expectedCapacity=this.expectedCapacity)==0) {
        Assertions.assertSame(expectedArr,actualArr);
      }else {
        Assertions.assertEquals(expectedCapacity,actualArr.length);
        var expectedArr=(double[])this.expectedArr;
        while(expectedSize!=0) {
          Assertions.assertEquals(expectedArr[--expectedSize],actualArr[expectedSize]);
        }
      }
      break;
    }
    case FLOAT:{
      var actualArr=((FloatArrSeq)seq).arr;
      int expectedCapacity;
      if((expectedCapacity=this.expectedCapacity)==0) {
        Assertions.assertSame(expectedArr,actualArr);
      }else {
        Assertions.assertEquals(expectedCapacity,actualArr.length);
        var expectedArr=(float[])this.expectedArr;
        while(expectedSize!=0) {
          Assertions.assertEquals(expectedArr[--expectedSize],actualArr[expectedSize]);
        }
      }
      break;
    }
    case INT:{
      var actualArr=((IntArrSeq)seq).arr;
      int expectedCapacity;
      if((expectedCapacity=this.expectedCapacity)==0) {
        Assertions.assertSame(expectedArr,actualArr);
      }else {
        Assertions.assertEquals(expectedCapacity,actualArr.length);
        var expectedArr=(int[])this.expectedArr;
        while(expectedSize!=0) {
          Assertions.assertEquals(expectedArr[--expectedSize],actualArr[expectedSize]);
        }
      }
      break;
    }
    case LONG:{
      var actualArr=((LongArrSeq)seq).arr;
      int expectedCapacity;
      if((expectedCapacity=this.expectedCapacity)==0) {
        Assertions.assertSame(expectedArr,actualArr);
      }else {
        Assertions.assertEquals(expectedCapacity,actualArr.length);
        var expectedArr=(long[])this.expectedArr;
        while(expectedSize!=0) {
          Assertions.assertEquals(expectedArr[--expectedSize],actualArr[expectedSize]);
        }
      }
      break;
    }
    case REF:{
      var actualArr=((RefArrSeq<?>)seq).arr;
      int expectedCapacity;
      if((expectedCapacity=this.expectedCapacity)==0) {
        Assertions.assertSame(expectedArr,actualArr);
      }else {
        var expectedArr=(Object[])this.expectedArr;
        int i=0;
        while(i!=expectedSize) {
          Assertions.assertSame(expectedArr[i],actualArr[i++]);
        }
        while(i!=expectedCapacity) {
          Assertions.assertNull(actualArr[i++]);
        }
      }
      break;
    }
    case SHORT:{
      var actualArr=((ShortArrSeq)seq).arr;
      int expectedCapacity;
      if((expectedCapacity=this.expectedCapacity)==0) {
        Assertions.assertSame(expectedArr,actualArr);
      }else {
        Assertions.assertEquals(expectedCapacity,actualArr.length);
        var expectedArr=(short[])this.expectedArr;
        while(expectedSize!=0) {
          Assertions.assertEquals(expectedArr[--expectedSize],actualArr[expectedSize]);
        }
      }
      break;
    }
    default:
      throw dataType.invalid();
    }
  }
  @Override public void verifyClone(Object clone){
    verifyCloneTypeAndModCount(clone);
    Assertions.assertNotSame(clone,seq);
    int size;
    Assertions.assertEquals(size=((AbstractSeq<?>)seq).size,((AbstractSeq<?>)clone).size);
    switch(dataType) {
    case BOOLEAN:{
      var origArr=((BooleanArrSeq)seq).arr;
      var cloneArr=((BooleanArrSeq)clone).arr;
      if(origArr==OmniArray.OfBoolean.DEFAULT_ARR) {
        Assertions.assertSame(origArr,cloneArr);
      }else {
        Assertions.assertNotSame(origArr,cloneArr);
        while(size!=0) {
          Assertions.assertEquals(origArr[--size],cloneArr[size]);
        }
      }
      break;
    }
    case BYTE:{
      var origArr=((ByteArrSeq)seq).arr;
      var cloneArr=((ByteArrSeq)clone).arr;
      if(origArr==OmniArray.OfByte.DEFAULT_ARR) {
        Assertions.assertSame(origArr,cloneArr);
      }else {
        Assertions.assertNotSame(origArr,cloneArr);
        while(size!=0) {
          Assertions.assertEquals(origArr[--size],cloneArr[size]);
        }
      }
      break;
    }
    case CHAR:{
      var origArr=((CharArrSeq)seq).arr;
      var cloneArr=((CharArrSeq)clone).arr;
      if(origArr==OmniArray.OfChar.DEFAULT_ARR) {
        Assertions.assertSame(origArr,cloneArr);
      }else {
        Assertions.assertNotSame(origArr,cloneArr);
        while(size!=0) {
          Assertions.assertEquals(origArr[--size],cloneArr[size]);
        }
      }
      break;
    }
    case SHORT:{
      var origArr=((ShortArrSeq)seq).arr;
      var cloneArr=((ShortArrSeq)clone).arr;
      if(origArr==OmniArray.OfShort.DEFAULT_ARR) {
        Assertions.assertSame(origArr,cloneArr);
      }else {
        Assertions.assertNotSame(origArr,cloneArr);
        while(size!=0) {
          Assertions.assertEquals(origArr[--size],cloneArr[size]);
        }
      }
      break;
    }
    case INT:{
      var origArr=((IntArrSeq)seq).arr;
      var cloneArr=((IntArrSeq)clone).arr;
      if(origArr==OmniArray.OfInt.DEFAULT_ARR) {
        Assertions.assertSame(origArr,cloneArr);
      }else {
        Assertions.assertNotSame(origArr,cloneArr);
        while(size!=0) {
          Assertions.assertEquals(origArr[--size],cloneArr[size]);
        }
      }
      break;
    }
    case LONG:{
      var origArr=((LongArrSeq)seq).arr;
      var cloneArr=((LongArrSeq)clone).arr;
      if(origArr==OmniArray.OfLong.DEFAULT_ARR) {
        Assertions.assertSame(origArr,cloneArr);
      }else {
        Assertions.assertNotSame(origArr,cloneArr);
        while(size!=0) {
          Assertions.assertEquals(origArr[--size],cloneArr[size]);
        }
      }
      break;
    }
    case FLOAT:{
      var origArr=((FloatArrSeq)seq).arr;
      var cloneArr=((FloatArrSeq)clone).arr;
      if(origArr==OmniArray.OfFloat.DEFAULT_ARR) {
        Assertions.assertSame(origArr,cloneArr);
      }else {
        Assertions.assertNotSame(origArr,cloneArr);
        while(size!=0) {
          Assertions.assertEquals(origArr[--size],cloneArr[size]);
        }
      }
      break;
    }
    case DOUBLE:{
      var origArr=((DoubleArrSeq)seq).arr;
      var cloneArr=((DoubleArrSeq)clone).arr;
      if(origArr==OmniArray.OfDouble.DEFAULT_ARR) {
        Assertions.assertSame(origArr,cloneArr);
      }else {
        Assertions.assertNotSame(origArr,cloneArr);
        while(size!=0) {
          Assertions.assertEquals(origArr[--size],cloneArr[size]);
        }
      }
      break;
    }
    case REF:{
      var origArr=((RefArrSeq<?>)seq).arr;
      var cloneArr=((RefArrSeq<?>)clone).arr;
      if(origArr==OmniArray.OfRef.DEFAULT_ARR) {
        Assertions.assertSame(origArr,cloneArr);
      }else {
        Assertions.assertNotSame(origArr,cloneArr);
        while(size!=0) {
          Assertions.assertSame(origArr[--size],cloneArr[size]);
        }
      }
      break;
    }
    default:
      throw dataType.invalid();
    }
  }
  @Override public void verifyRemoveIf(boolean result,MonitoredRemoveIfPredicate filter){
    if(dataType==DataType.BOOLEAN) {
      if(result) {
        var expectedArr=(boolean[])this.expectedArr;
        ++expectedModCount;
        if(filter.removedVals.contains(Boolean.TRUE)) {
          if(filter.removedVals.contains(Boolean.FALSE)) {
            Assertions.assertTrue(filter.retainedVals.isEmpty());
            Assertions.assertEquals(0,filter.numRetained);
            int expectedSize;
            boolean firstVal=expectedArr[expectedSize=this.expectedSize-1];
            for(;;) {
              if(expectedArr[--expectedSize]^firstVal) {
                //we are expecting this condition to be met before expectedSize==-1. Otherwise, something is wrong.
                break;
              }
            }
            this.expectedSize=0;
          }else {
            int i=0;
            for(int expectedSize=this.expectedSize;;++i) {
              if(expectedArr[i]) {
                for(int j=i+1;j<expectedSize;++j) {
                  if(!expectedArr[j]) {
                    expectedArr[i++]=false;
                  }
                }
                break;
              }
            }
            this.expectedSize=i;
          }
        }else {
          int i=0;
          for(int expectedSize=this.expectedSize;;++i) {
            if(!expectedArr[i]) {
              for(int j=i+1;j<expectedSize;++j) {
                if(expectedArr[j]) {
                  expectedArr[i++]=true;
                }
              }
              break;
            }
          }
          this.expectedSize=i;
        }
      }else {
        var expectedArr=((BooleanArrSeq)seq).arr;
        Assertions.assertTrue(filter.removedVals.isEmpty());
        if(filter.retainedVals.contains(Boolean.TRUE)) {
          if(filter.retainedVals.contains(Boolean.FALSE)) {
            int expectedSize;
            boolean firstVal=expectedArr[expectedSize=this.expectedSize-1];
            for(;;) {
              if(expectedArr[--expectedSize]^firstVal) {
                //we are expecting this condition to be met before expectedSize==-1. Otherwise, something is wrong.
                break;
              }
            }
          }else {
            for(int i=this.expectedSize;--i>=0;) {
              Assertions.assertTrue(expectedArr[i]);
            }
          }
        }else {
          if(filter.retainedVals.contains(Boolean.FALSE)) {
            for(int i=this.expectedSize;--i>=0;) {
              Assertions.assertFalse(expectedArr[i]);
            }
          }else {
            Assertions.assertEquals(0,expectedSize);
          }
        }
      }
    }else {
      Assertions.assertEquals(expectedSize,filter.numCalls);
      if(result) {
        int i=0;
        switch(dataType) {
        case BYTE:{
          var arr=(byte[])expectedArr;
          for(int bound=this.expectedSize;;++i) {
            var val=arr[i];
            if(filter.removedVals.contains(val)) {
              Assertions.assertFalse(filter.retainedVals.contains(val));
              for(int j=i+1;j<bound;++j) {
                if(filter.retainedVals.contains(val=arr[j])) {
                  arr[i++]=val;
                  Assertions.assertFalse(filter.removedVals.contains(val));
                }else {
                  Assertions.assertTrue(filter.removedVals.contains(val));
                }
              }
              break;
            }else {
              Assertions.assertTrue(filter.retainedVals.contains(val));
            }
          }
          break;
        }
        case CHAR:{
          var arr=(char[])expectedArr;
          for(int bound=this.expectedSize;;++i) {
            var val=arr[i];
            if(filter.removedVals.contains(val)) {
              Assertions.assertFalse(filter.retainedVals.contains(val));
              for(int j=i+1;j<bound;++j) {
                if(filter.retainedVals.contains(val=arr[j])) {
                  arr[i++]=val;
                  Assertions.assertFalse(filter.removedVals.contains(val));
                }else {
                  Assertions.assertTrue(filter.removedVals.contains(val));
                }
              }
              break;
            }else {
              Assertions.assertTrue(filter.retainedVals.contains(val));
            }
          }
          break;
        }
        case DOUBLE:{
          var arr=(double[])expectedArr;
          for(int bound=this.expectedSize;;++i) {
            var val=arr[i];
            if(filter.removedVals.contains(val)) {
              Assertions.assertFalse(filter.retainedVals.contains(val));
              for(int j=i+1;j<bound;++j) {
                if(filter.retainedVals.contains(val=arr[j])) {
                  arr[i++]=val;
                  Assertions.assertFalse(filter.removedVals.contains(val));
                }else {
                  Assertions.assertTrue(filter.removedVals.contains(val));
                }
              }
              break;
            }else {
              Assertions.assertTrue(filter.retainedVals.contains(val));
            }
          }
          break;
        }
        case FLOAT:{
          var arr=(float[])expectedArr;
          for(int bound=this.expectedSize;;++i) {
            var val=arr[i];
            if(filter.removedVals.contains(val)) {
              Assertions.assertFalse(filter.retainedVals.contains(val));
              for(int j=i+1;j<bound;++j) {
                if(filter.retainedVals.contains(val=arr[j])) {
                  arr[i++]=val;
                  Assertions.assertFalse(filter.removedVals.contains(val));
                }else {
                  Assertions.assertTrue(filter.removedVals.contains(val));
                }
              }
              break;
            }else {
              Assertions.assertTrue(filter.retainedVals.contains(val));
            }
          }
          break;
        }
        case INT:{
          var arr=(int[])expectedArr;
          for(int bound=this.expectedSize;;++i) {
            var val=arr[i];
            if(filter.removedVals.contains(val)) {
              Assertions.assertFalse(filter.retainedVals.contains(val));
              for(int j=i+1;j<bound;++j) {
                if(filter.retainedVals.contains(val=arr[j])) {
                  arr[i++]=val;
                  Assertions.assertFalse(filter.removedVals.contains(val));
                }else {
                  Assertions.assertTrue(filter.removedVals.contains(val));
                }
              }
              break;
            }else {
              Assertions.assertTrue(filter.retainedVals.contains(val));
            }
          }
          break;
        }
        case LONG:{
          var arr=(long[])expectedArr;
          for(int bound=this.expectedSize;;++i) {
            var val=arr[i];
            if(filter.removedVals.contains(val)) {
              Assertions.assertFalse(filter.retainedVals.contains(val));
              for(int j=i+1;j<bound;++j) {
                if(filter.retainedVals.contains(val=arr[j])) {
                  arr[i++]=val;
                  Assertions.assertFalse(filter.removedVals.contains(val));
                }else {
                  Assertions.assertTrue(filter.removedVals.contains(val));
                }
              }
              break;
            }else {
              Assertions.assertTrue(filter.retainedVals.contains(val));
            }
          }
          break;
        }
        case REF:{
          var arr=(Object[])expectedArr;
          for(int bound=this.expectedSize;;++i) {
            var val=arr[i];
            if(filter.removedVals.contains(val)) {
              Assertions.assertFalse(filter.retainedVals.contains(val));
              for(int j=i+1;j<bound;++j) {
                if(filter.retainedVals.contains(val=arr[j])) {
                  arr[i++]=val;
                  Assertions.assertFalse(filter.removedVals.contains(val));
                }else {
                  Assertions.assertTrue(filter.removedVals.contains(val));
                }
              }
              break;
            }else {
              Assertions.assertTrue(filter.retainedVals.contains(val));
            }
          }
          for(int expectedCapacity=this.expectedCapacity,j=i;++j<expectedCapacity;) {
            arr[j]=null;
          }
          break;
        }
        case SHORT:{
          var arr=(short[])expectedArr;
          for(int bound=this.expectedSize;;++i) {
            var val=arr[i];
            if(filter.removedVals.contains(val)) {
              Assertions.assertFalse(filter.retainedVals.contains(val));
              for(int j=i+1;j<bound;++j) {
                if(filter.retainedVals.contains(val=arr[j])) {
                  arr[i++]=val;
                  Assertions.assertFalse(filter.removedVals.contains(val));
                }else {
                  Assertions.assertTrue(filter.removedVals.contains(val));
                }
              }
              break;
            }else {
              Assertions.assertTrue(filter.retainedVals.contains(val));
            }
          }
          break;
        }
        default:
          throw dataType.invalid();
        }
        Assertions.assertEquals(filter.numRemoved,expectedSize-i);
        Assertions.assertEquals(filter.numRetained,i);
        this.expectedSize=i;
      }else {
        Assertions.assertTrue(filter.removedVals.isEmpty());
        Assertions.assertEquals(0,filter.numRemoved);
        Assertions.assertEquals(expectedSize,filter.numRetained);
        var itr=seq.iterator();
        while(itr.hasNext()) {
          Assertions.assertTrue(filter.retainedVals.contains(itr.next()));
        }
      }
    }
  }
  @Override public void verifyArrayIsCopy(Object arr){
    switch(dataType) {
    case BOOLEAN:{
      var actualArr=((BooleanArrSeq)seq).arr;
      if(actualArr==null || actualArr.length==0) {
        Assertions.assertSame(arr,actualArr);
      }else {
        Assertions.assertNotSame(arr,actualArr);
      }
      break;
    }
    case BYTE:{
      var actualArr=((ByteArrSeq)seq).arr;
      if(actualArr==null || actualArr.length==0) {
        Assertions.assertSame(arr,actualArr);
      }else {
        Assertions.assertNotSame(arr,actualArr);
      }
      break;
    }
    case CHAR:{
      var actualArr=((CharArrSeq)seq).arr;
      if(actualArr==null || actualArr.length==0) {
        Assertions.assertSame(arr,actualArr);
      }else {
        Assertions.assertNotSame(arr,actualArr);
      }
      break;
    }
    case DOUBLE:{
      var actualArr=((DoubleArrSeq)seq).arr;
      if(actualArr==null || actualArr.length==0) {
        Assertions.assertSame(arr,actualArr);
      }else {
        Assertions.assertNotSame(arr,actualArr);
      }
      break;
    }
    case FLOAT:{
      var actualArr=((FloatArrSeq)seq).arr;
      if(actualArr==null || actualArr.length==0) {
        Assertions.assertSame(arr,actualArr);
      }else {
        Assertions.assertNotSame(arr,actualArr);
      }
      break;
    }
    case INT:{
      var actualArr=((IntArrSeq)seq).arr;
      if(actualArr==null || actualArr.length==0) {
        Assertions.assertSame(arr,actualArr);
      }else {
        Assertions.assertNotSame(arr,actualArr);
      }
      break;
    }
    case LONG:{
      var actualArr=((LongArrSeq)seq).arr;
      if(actualArr==null || actualArr.length==0) {
        Assertions.assertSame(arr,actualArr);
      }else {
        Assertions.assertNotSame(arr,actualArr);
      }
      break;
    }
    case REF:{
      var actualArr=((RefArrSeq<?>)seq).arr;
      if(actualArr==null || actualArr.length==0) {
        Assertions.assertSame(arr,actualArr);
      }else {
        Assertions.assertNotSame(arr,actualArr);
      }
      break;
    }
    case SHORT:{
      var actualArr=((ShortArrSeq)seq).arr;
      if(actualArr==null || actualArr.length==0) {
        Assertions.assertSame(arr,actualArr);
      }else {
        Assertions.assertNotSame(arr,actualArr);
      }
      break;
    }
    default:
      throw dataType.invalid();
    }
    
  }
  @Override public void writeObjectImpl(MonitoredObjectOutputStream oos) throws IOException{
    ((Externalizable)seq).writeExternal(oos);
  }
  @Override public void updateAddState(Object inputVal,DataType inputType){
    ++expectedModCount;
    switch(dataType) {
    case BOOLEAN:{
      var expectedArr=(boolean[])this.expectedArr;
      if(expectedSize==expectedCapacity) {
        if(expectedArr==OmniArray.OfBoolean.DEFAULT_ARR) {
          this.expectedArr=expectedArr=new boolean[OmniArray.DEFAULT_ARR_SEQ_CAP];
        }else if(expectedArr==null){
          this.expectedArr=expectedArr=new boolean[1];
        }else {
          var newArr=new boolean[OmniArray.growBy50Pct(expectedCapacity)];
          System.arraycopy(expectedArr,0,newArr,0,expectedSize);
          this.expectedArr=expectedArr=newArr;
        }
        expectedCapacity=expectedArr.length;
      }
      expectedArr[expectedSize]=(boolean)inputVal;
      break;
    }
    case BYTE:{
      byte inputCast;
      switch(inputType) {
      case BOOLEAN:
        inputCast=((boolean)inputVal)?(byte)1:(byte)0;
        break;
      case BYTE:
        inputCast=(byte)inputVal;
        break;
      default:
        throw inputType.invalid();
      }
      var expectedArr=(byte[])this.expectedArr;
      if(expectedSize==expectedCapacity) {
        if(expectedArr==OmniArray.OfByte.DEFAULT_ARR) {
          this.expectedArr=expectedArr=new byte[OmniArray.DEFAULT_ARR_SEQ_CAP];
        }else if(expectedArr==null){
          this.expectedArr=expectedArr=new byte[1];
        }else {
          var newArr=new byte[OmniArray.growBy50Pct(expectedCapacity)];
          System.arraycopy(expectedArr,0,newArr,0,expectedSize);
          this.expectedArr=expectedArr=newArr;
        }
        expectedCapacity=expectedArr.length;
      }
      expectedArr[expectedSize]=inputCast;
      break;
    }
    case CHAR:{
      char inputCast;
      switch(inputType) {
      case BOOLEAN:
        inputCast=((boolean)inputVal)?(char)1:(char)0;
        break;
      case CHAR:
        inputCast=(char)inputVal;
        break;
      default:
        throw inputType.invalid();
      }
      var expectedArr=(char[])this.expectedArr;
      if(expectedSize==expectedCapacity) {
        if(expectedArr==OmniArray.OfChar.DEFAULT_ARR) {
          this.expectedArr=expectedArr=new char[OmniArray.DEFAULT_ARR_SEQ_CAP];
        }else if(expectedArr==null){
          this.expectedArr=expectedArr=new char[1];
        }else {
          var newArr=new char[OmniArray.growBy50Pct(expectedCapacity)];
          System.arraycopy(expectedArr,0,newArr,0,expectedSize);
          this.expectedArr=expectedArr=newArr;
        }
        expectedCapacity=expectedArr.length;
      }
      expectedArr[expectedSize]=inputCast;
      break;
    }
    case SHORT:{
      short inputCast;
      switch(inputType) {
      case BOOLEAN:
        inputCast=((boolean)inputVal)?(short)1:(short)0;
        break;
      case BYTE:
        inputCast=(short)(byte)inputVal;
        break;
      case SHORT:
        inputCast=(short)inputVal;
        break;
      default:
        throw inputType.invalid();
      }
      var expectedArr=(short[])this.expectedArr;
      if(expectedSize==expectedCapacity) {
        if(expectedArr==OmniArray.OfShort.DEFAULT_ARR) {
          this.expectedArr=expectedArr=new short[OmniArray.DEFAULT_ARR_SEQ_CAP];
        }else if(expectedArr==null){
          this.expectedArr=expectedArr=new short[1];
        }else {
          var newArr=new short[OmniArray.growBy50Pct(expectedCapacity)];
          System.arraycopy(expectedArr,0,newArr,0,expectedSize);
          this.expectedArr=expectedArr=newArr;
        }
        expectedCapacity=expectedArr.length;
      }
      expectedArr[expectedSize]=inputCast;
      break;
    }
    case INT:{
      int inputCast;
      switch(inputType) {
      case BOOLEAN:
        inputCast=((boolean)inputVal)?1:0;
        break;
      case BYTE:
        inputCast=(int)(byte)inputVal;
        break;
      case CHAR:
        inputCast=(int)(char)inputVal;
        break;
      case SHORT:
        inputCast=(int)(short)inputVal;
        break;
      case INT:
        inputCast=(int)inputVal;
        break;
      default:
        throw inputType.invalid();
      }
      var expectedArr=(int[])this.expectedArr;
      if(expectedSize==expectedCapacity) {
        if(expectedArr==OmniArray.OfInt.DEFAULT_ARR) {
          this.expectedArr=expectedArr=new int[OmniArray.DEFAULT_ARR_SEQ_CAP];
        }else if(expectedArr==null){
          this.expectedArr=expectedArr=new int[1];
        }else {
          var newArr=new int[OmniArray.growBy50Pct(expectedCapacity)];
          System.arraycopy(expectedArr,0,newArr,0,expectedSize);
          this.expectedArr=expectedArr=newArr;
        }
        expectedCapacity=expectedArr.length;
      }
      expectedArr[expectedSize]=inputCast;
      break;
    }
    case LONG:{
      long inputCast;
      switch(inputType) {
      case BOOLEAN:
        inputCast=((boolean)inputVal)?1L:0L;
        break;
      case BYTE:
        inputCast=(long)(byte)inputVal;
        break;
      case CHAR:
        inputCast=(long)(char)inputVal;
        break;
      case SHORT:
        inputCast=(long)(short)inputVal;
        break;
      case INT:
        inputCast=(long)(int)inputVal;
        break;
      case LONG:
        inputCast=(long)inputVal;
        break;
      default:
        throw inputType.invalid();
      }
      var expectedArr=(long[])this.expectedArr;
      if(expectedSize==expectedCapacity) {
        if(expectedArr==OmniArray.OfLong.DEFAULT_ARR) {
          this.expectedArr=expectedArr=new long[OmniArray.DEFAULT_ARR_SEQ_CAP];
        }else if(expectedArr==null){
          this.expectedArr=expectedArr=new long[1];
        }else {
          var newArr=new long[OmniArray.growBy50Pct(expectedCapacity)];
          System.arraycopy(expectedArr,0,newArr,0,expectedSize);
          this.expectedArr=expectedArr=newArr;
        }
        expectedCapacity=expectedArr.length;
      }
      expectedArr[expectedSize]=inputCast;
      break;
    }
    case FLOAT:{
      float inputCast;
      switch(inputType) {
      case BOOLEAN:
        inputCast=((boolean)inputVal)?1.0f:0.0f;
        break;
      case BYTE:
        inputCast=(float)(byte)inputVal;
        break;
      case CHAR:
        inputCast=(float)(char)inputVal;
        break;
      case SHORT:
        inputCast=(float)(short)inputVal;
        break;
      case INT:
        inputCast=(float)(int)inputVal;
        break;
      case LONG:
        inputCast=(float)(long)inputVal;
        break;
      case FLOAT:
        inputCast=(float)inputVal;
        break;
      default:
        throw inputType.invalid();
      }
      var expectedArr=(float[])this.expectedArr;
      if(expectedSize==expectedCapacity) {
        if(expectedArr==OmniArray.OfFloat.DEFAULT_ARR) {
          this.expectedArr=expectedArr=new float[OmniArray.DEFAULT_ARR_SEQ_CAP];
        }else if(expectedArr==null){
          this.expectedArr=expectedArr=new float[1];
        }else {
          var newArr=new float[OmniArray.growBy50Pct(expectedCapacity)];
          System.arraycopy(expectedArr,0,newArr,0,expectedSize);
          this.expectedArr=expectedArr=newArr;
        }
        expectedCapacity=expectedArr.length;
      }
      expectedArr[expectedSize]=inputCast;
      break;
    }
    case DOUBLE:{
      double inputCast;
      switch(inputType) {
      case BOOLEAN:
        inputCast=((boolean)inputVal)?1.0:0.0;
        break;
      case BYTE:
        inputCast=(double)(byte)inputVal;
        break;
      case CHAR:
        inputCast=(double)(char)inputVal;
        break;
      case SHORT:
        inputCast=(double)(short)inputVal;
        break;
      case INT:
        inputCast=(double)(int)inputVal;
        break;
      case LONG:
        inputCast=(double)(long)inputVal;
        break;
      case FLOAT:
        inputCast=(double)(float)inputVal;
        break;
      case DOUBLE:
        inputCast=(double)inputVal;
        break;
      default:
        throw inputType.invalid();
      }
      var expectedArr=(double[])this.expectedArr;
      if(expectedSize==expectedCapacity) {
        if(expectedArr==OmniArray.OfDouble.DEFAULT_ARR) {
          this.expectedArr=expectedArr=new double[OmniArray.DEFAULT_ARR_SEQ_CAP];
        }else if(expectedArr==null){
          this.expectedArr=expectedArr=new double[1];
        }else {
          var newArr=new double[OmniArray.growBy50Pct(expectedCapacity)];
          System.arraycopy(expectedArr,0,newArr,0,expectedSize);
          this.expectedArr=expectedArr=newArr;
        }
        expectedCapacity=expectedArr.length;
      }
      expectedArr[expectedSize]=inputCast;
      break;
    }
    case REF:{
      var expectedArr=(Object[])this.expectedArr;
      if(expectedSize==expectedCapacity) {
        if(expectedArr==OmniArray.OfRef.DEFAULT_ARR) {
          this.expectedArr=expectedArr=new Object[OmniArray.DEFAULT_ARR_SEQ_CAP];
        }else if(expectedArr==null){
          this.expectedArr=expectedArr=new Object[1];
        }else {
          var newArr=new Object[OmniArray.growBy50Pct(expectedCapacity)];
          System.arraycopy(expectedArr,0,newArr,0,expectedSize);
          this.expectedArr=expectedArr=newArr;
        }
        expectedCapacity=expectedArr.length;
      }
      expectedArr[expectedSize]=inputVal;
      break;
    }
    default:
      throw dataType.invalid();
    }
    ++expectedSize;
  }
  abstract void updateModCount();
  abstract void verifyModCount();
  abstract void verifyCloneTypeAndModCount(Object clone);
}
