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
public enum IntOutputTestArgType{
  ARRAY_TYPE{
    @Override public void verifyItrPrevious(OmniIterator.OfInt itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToint(valToConvert),((OmniListIterator.OfInt)itr).previousInt());
    }
    @Override public void verifyItrNext(OmniIterator.OfInt itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToint(valToConvert),itr.nextInt());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfInt col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToint(valToConvert),((OmniList.OfInt)col).removeIntAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfInt col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToint(valToConvert),((OmniList.OfInt)col).getInt(index));
    }
    @Override public void verifyToArray(OmniCollection.OfInt col,int expectedSize){
      var arr=col.toIntArray();
      if(expectedSize==0){
        Assertions.assertSame(OmniArray.OfInt.DEFAULT_ARR,arr);
      }else{
        Assertions.assertEquals(expectedSize,arr.length);
        var itr=col.iterator();
        for(int i=0;i<expectedSize;++i){
          Assertions.assertEquals(itr.nextInt(),arr[i]);
        }
      }
    }
    @Override public void verifyPoll(OmniCollection.OfInt col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Integer.MIN_VALUE,((PeekAndPollIfc.IntOutput<?>)col).pollInt());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToint(expectedVal),((PeekAndPollIfc.IntOutput<?>)col).pollInt());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfInt col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Integer.MIN_VALUE,((PeekAndPollIfc.IntOutput<?>)col).peekInt());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToint(expectedVal),((PeekAndPollIfc.IntOutput<?>)col).peekInt());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfInt col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToint(expectedVal),((OmniStack.OfInt)col).popInt());
    }
    @Override public void verifyQueueElement(OmniCollection.OfInt col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToint(expectedVal),((OmniQueue.OfInt)col).intElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfInt col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToint(expectedVal),((OmniQueue.OfInt)col).removeInt());
    }
  }
  ,
  BOXED_TYPE{
    @Override public void verifyItrPrevious(OmniIterator.OfInt itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToInteger(valToConvert),((OmniListIterator.OfInt)itr).previous());
    }
    @Override public void verifyItrNext(OmniIterator.OfInt itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToInteger(valToConvert),itr.next());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfInt col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToInteger(valToConvert),((OmniList.OfInt)col).remove(index));
    }
    @Override public void verifyListGet(OmniCollection.OfInt col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToInteger(valToConvert),((OmniList.OfInt)col).get(index));
    }
    @Override public void verifyToArray(OmniCollection.OfInt col,int expectedSize){
      var arr=col.toArray();
      if(expectedSize==0){
        Assertions.assertSame(OmniArray.OfInt.DEFAULT_BOXED_ARR,arr);
      }else{
        Assertions.assertEquals(expectedSize,arr.length);
        var itr=col.iterator();
        for(int i=0;i<expectedSize;++i){
          Assertions.assertEquals(itr.next(),arr[i]);
        }
      }
    }
    @Override public void verifyPoll(OmniCollection.OfInt col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertNull(((PeekAndPollIfc<?>)col).poll());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToInteger(expectedVal),((PeekAndPollIfc<?>)col).poll());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfInt col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertNull(((PeekAndPollIfc<?>)col).peek());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToInteger(expectedVal),((PeekAndPollIfc<?>)col).peek());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfInt col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToInteger(expectedVal),((OmniStack.OfInt)col).pop());
    }
    @Override public void verifyQueueElement(OmniCollection.OfInt col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToInteger(expectedVal),((OmniQueue.OfInt)col).element());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfInt col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToInteger(expectedVal),((OmniQueue.OfInt)col).remove());
    }
  }
  ,
  DOUBLE{
    @Override public void verifyItrPrevious(OmniIterator.OfInt itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),((OmniListIterator.OfInt)itr).previousDouble());
    }
    @Override public void verifyItrNext(OmniIterator.OfInt itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),itr.nextDouble());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfInt col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),((OmniList.OfInt)col).removeDoubleAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfInt col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),((OmniList.OfInt)col).getDouble(index));
    }
    @Override public void verifyToArray(OmniCollection.OfInt col,int expectedSize){
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
    @Override public void verifyPoll(OmniCollection.OfInt col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((PeekAndPollIfc.DoubleOutput<?>)col).pollDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((PeekAndPollIfc.DoubleOutput<?>)col).pollDouble());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfInt col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((PeekAndPollIfc.DoubleOutput<?>)col).peekDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((PeekAndPollIfc.DoubleOutput<?>)col).peekDouble());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfInt col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniStack.OfInt)col).popDouble());
    }
    @Override public void verifyQueueElement(OmniCollection.OfInt col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniQueue.OfInt)col).doubleElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfInt col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniQueue.OfInt)col).removeDouble());
    }
  }
  ,
  FLOAT{
    @Override public void verifyItrPrevious(OmniIterator.OfInt itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(valToConvert),((OmniListIterator.OfInt)itr).previousFloat());
    }
    @Override public void verifyItrNext(OmniIterator.OfInt itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(valToConvert),itr.nextFloat());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfInt col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(valToConvert),((OmniList.OfInt)col).removeFloatAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfInt col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(valToConvert),((OmniList.OfInt)col).getFloat(index));
    }
    @Override public void verifyToArray(OmniCollection.OfInt col,int expectedSize){
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
    @Override public void verifyPoll(OmniCollection.OfInt col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((PeekAndPollIfc.FloatOutput<?>)col).pollFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((PeekAndPollIfc.FloatOutput<?>)col).pollFloat());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfInt col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((PeekAndPollIfc.FloatOutput<?>)col).peekFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((PeekAndPollIfc.FloatOutput<?>)col).peekFloat());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfInt col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniStack.OfInt)col).popFloat());
    }
    @Override public void verifyQueueElement(OmniCollection.OfInt col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniQueue.OfInt)col).floatElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfInt col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniQueue.OfInt)col).removeFloat());
    }
  }
  ,
  LONG{
    @Override public void verifyItrPrevious(OmniIterator.OfInt itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(valToConvert),((OmniListIterator.OfInt)itr).previousLong());
    }
    @Override public void verifyItrNext(OmniIterator.OfInt itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(valToConvert),itr.nextLong());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfInt col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(valToConvert),((OmniList.OfInt)col).removeLongAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfInt col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(valToConvert),((OmniList.OfInt)col).getLong(index));
    }
    @Override public void verifyToArray(OmniCollection.OfInt col,int expectedSize){
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
    @Override public void verifyPoll(OmniCollection.OfInt col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Long.MIN_VALUE,((PeekAndPollIfc.LongOutput<?>)col).pollLong());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTolong(expectedVal),((PeekAndPollIfc.LongOutput<?>)col).pollLong());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfInt col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Long.MIN_VALUE,((PeekAndPollIfc.LongOutput<?>)col).peekLong());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTolong(expectedVal),((PeekAndPollIfc.LongOutput<?>)col).peekLong());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfInt col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(expectedVal),((OmniStack.OfInt)col).popLong());
    }
    @Override public void verifyQueueElement(OmniCollection.OfInt col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(expectedVal),((OmniQueue.OfInt)col).longElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfInt col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(expectedVal),((OmniQueue.OfInt)col).removeLong());
    }
  }
  ;
  public abstract void verifyItrPrevious(OmniIterator.OfInt itr,int valToConvert);
  public abstract void verifyItrNext(OmniIterator.OfInt itr,int valToConvert);
  public abstract void verifyListRemoveAt(OmniCollection.OfInt col,int index,int valToConvert);
  public abstract void verifyListGet(OmniCollection.OfInt col,int index,int valToConvert);
  public abstract void verifyToArray(OmniCollection.OfInt col,int expectedSize);
  public abstract void verifyPoll(OmniCollection.OfInt col,int expectedSize,int expectedVal);
  public abstract void verifyStackPop(OmniCollection.OfInt col,int expectedVal);
  public abstract void verifyQueueRemove(OmniCollection.OfInt col,int expectedVal);
  public abstract void verifyQueueElement(OmniCollection.OfInt col,int expectedVal);
  public abstract void verifyPeek(OmniCollection.OfInt col,int expectedSize,int expectedVal);
  //TODO other method tests
}
