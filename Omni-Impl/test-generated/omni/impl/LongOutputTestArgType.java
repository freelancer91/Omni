package omni.impl;
import omni.util.TypeConversionUtil;
import omni.api.OmniCollection;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.api.OmniQueue;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import omni.util.OmniArray;
import omni.util.PeekAndPollIfc;
import org.junit.jupiter.api.Assertions;
public enum LongOutputTestArgType{
  ARRAY_TYPE{
    @Override public void verifyItrPrevious(OmniIterator.OfLong itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(valToConvert),((OmniListIterator.OfLong)itr).previousLong());
    }
    @Override public void verifyItrNext(OmniIterator.OfLong itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(valToConvert),itr.nextLong());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfLong col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(valToConvert),((OmniList.OfLong)col).removeLongAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfLong col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(valToConvert),((OmniList.OfLong)col).getLong(index));
    }
    @Override public void verifyToArray(OmniCollection.OfLong col,int expectedSize){
      var arr=col.toLongArray();
      if(expectedSize==0){
        Assertions.assertSame(OmniArray.OfLong.DEFAULT_ARR,arr);
      }else{
        Assertions.assertEquals(expectedSize,arr.length);
        var itr=col.iterator();
        for(int i=0;i<expectedSize;++i){
          Assertions.assertEquals(itr.nextLong(),arr[i]);
        }
      }
    }
    @Override public void verifyPoll(OmniCollection.OfLong col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Long.MIN_VALUE,((PeekAndPollIfc.LongInput)col).pollLong());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTolong(expectedVal),((PeekAndPollIfc.LongInput)col).pollLong());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfLong col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Long.MIN_VALUE,((PeekAndPollIfc.LongInput)col).peekLong());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTolong(expectedVal),((PeekAndPollIfc.LongInput)col).peekLong());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfLong col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(expectedVal),((OmniStack.OfLong)col).popLong());
    }
    @Override public void verifyQueueElement(OmniCollection.OfLong col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(expectedVal),((OmniQueue.OfLong)col).longElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfLong col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(expectedVal),((OmniQueue.OfLong)col).removeLong());
    }
  }
  ,
  BOXED_TYPE{
    @Override public void verifyItrPrevious(OmniIterator.OfLong itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToLong(valToConvert),((OmniListIterator.OfLong)itr).previous());
    }
    @Override public void verifyItrNext(OmniIterator.OfLong itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToLong(valToConvert),itr.next());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfLong col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToLong(valToConvert),((OmniList.OfLong)col).remove(index));
    }
    @Override public void verifyListGet(OmniCollection.OfLong col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToLong(valToConvert),((OmniList.OfLong)col).get(index));
    }
    @Override public void verifyToArray(OmniCollection.OfLong col,int expectedSize){
      var arr=col.toArray();
      if(expectedSize==0){
        Assertions.assertSame(OmniArray.OfLong.DEFAULT_BOXED_ARR,arr);
      }else{
        Assertions.assertEquals(expectedSize,arr.length);
        var itr=col.iterator();
        for(int i=0;i<expectedSize;++i){
          Assertions.assertEquals(itr.next(),arr[i]);
        }
      }
    }
    @Override public void verifyPoll(OmniCollection.OfLong col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertNull(((PeekAndPollIfc<?>)col).poll());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToLong(expectedVal),((PeekAndPollIfc<?>)col).poll());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfLong col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertNull(((PeekAndPollIfc<?>)col).peek());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToLong(expectedVal),((PeekAndPollIfc<?>)col).peek());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfLong col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToLong(expectedVal),((OmniStack.OfLong)col).pop());
    }
    @Override public void verifyQueueElement(OmniCollection.OfLong col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToLong(expectedVal),((OmniQueue.OfLong)col).element());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfLong col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToLong(expectedVal),((OmniQueue.OfLong)col).remove());
    }
  }
  ,
  DOUBLE{
    @Override public void verifyItrPrevious(OmniIterator.OfLong itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),((OmniListIterator.OfLong)itr).previousDouble());
    }
    @Override public void verifyItrNext(OmniIterator.OfLong itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),itr.nextDouble());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfLong col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),((OmniList.OfLong)col).removeDoubleAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfLong col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),((OmniList.OfLong)col).getDouble(index));
    }
    @Override public void verifyToArray(OmniCollection.OfLong col,int expectedSize){
      var arr=col.toDoubleArray();
      if(expectedSize==0){
        Assertions.assertSame(OmniArray.OfDouble.DEFAULT_ARR,arr);
      }else{
        Assertions.assertEquals(expectedSize,arr.length);
        var itr=col.iterator();
        for(int i=0;i<expectedSize;++i){
          Assertions.assertEquals(itr.nextDouble(),arr[i]);
        }
      }
    }
    @Override public void verifyPoll(OmniCollection.OfLong col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((PeekAndPollIfc.DoubleInput)col).pollDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((PeekAndPollIfc.DoubleInput)col).pollDouble());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfLong col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((PeekAndPollIfc.DoubleInput)col).peekDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((PeekAndPollIfc.DoubleInput)col).peekDouble());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfLong col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniStack.OfLong)col).popDouble());
    }
    @Override public void verifyQueueElement(OmniCollection.OfLong col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniQueue.OfLong)col).doubleElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfLong col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniQueue.OfLong)col).removeDouble());
    }
  }
  ,
  FLOAT{
    @Override public void verifyItrPrevious(OmniIterator.OfLong itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(valToConvert),((OmniListIterator.OfLong)itr).previousFloat());
    }
    @Override public void verifyItrNext(OmniIterator.OfLong itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(valToConvert),itr.nextFloat());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfLong col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(valToConvert),((OmniList.OfLong)col).removeFloatAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfLong col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(valToConvert),((OmniList.OfLong)col).getFloat(index));
    }
    @Override public void verifyToArray(OmniCollection.OfLong col,int expectedSize){
      var arr=col.toFloatArray();
      if(expectedSize==0){
        Assertions.assertSame(OmniArray.OfFloat.DEFAULT_ARR,arr);
      }else{
        Assertions.assertEquals(expectedSize,arr.length);
        var itr=col.iterator();
        for(int i=0;i<expectedSize;++i){
          Assertions.assertEquals(itr.nextFloat(),arr[i]);
        }
      }
    }
    @Override public void verifyPoll(OmniCollection.OfLong col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((PeekAndPollIfc.FloatInput)col).pollFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((PeekAndPollIfc.FloatInput)col).pollFloat());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfLong col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((PeekAndPollIfc.FloatInput)col).peekFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((PeekAndPollIfc.FloatInput)col).peekFloat());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfLong col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniStack.OfLong)col).popFloat());
    }
    @Override public void verifyQueueElement(OmniCollection.OfLong col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniQueue.OfLong)col).floatElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfLong col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniQueue.OfLong)col).removeFloat());
    }
  }
  ;
  public abstract void verifyItrPrevious(OmniIterator.OfLong itr,int valToConvert);
  public abstract void verifyItrNext(OmniIterator.OfLong itr,int valToConvert);
  public abstract void verifyListRemoveAt(OmniCollection.OfLong col,int index,int valToConvert);
  public abstract void verifyListGet(OmniCollection.OfLong col,int index,int valToConvert);
  public abstract void verifyToArray(OmniCollection.OfLong col,int expectedSize);
  public abstract void verifyPoll(OmniCollection.OfLong col,int expectedSize,int expectedVal);
  public abstract void verifyStackPop(OmniCollection.OfLong col,int expectedVal);
  public abstract void verifyQueueRemove(OmniCollection.OfLong col,int expectedVal);
  public abstract void verifyQueueElement(OmniCollection.OfLong col,int expectedVal);
  public abstract void verifyPeek(OmniCollection.OfLong col,int expectedSize,int expectedVal);
  //TODO other method tests
}
