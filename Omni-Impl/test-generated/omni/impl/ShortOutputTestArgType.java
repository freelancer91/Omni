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
public enum ShortOutputTestArgType{
  ARRAY_TYPE{
    @Override public void verifyItrPrevious(OmniIterator.OfShort itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToshort(valToConvert),((OmniListIterator.OfShort)itr).previousShort());
    }
    @Override public void verifyItrNext(OmniIterator.OfShort itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToshort(valToConvert),itr.nextShort());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfShort col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToshort(valToConvert),((OmniList.OfShort)col).removeShortAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfShort col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToshort(valToConvert),((OmniList.OfShort)col).getShort(index));
    }
    @Override public void verifyToArray(OmniCollection.OfShort col,int expectedSize){
      var arr=col.toShortArray();
      if(expectedSize==0){
        Assertions.assertSame(OmniArray.OfShort.DEFAULT_ARR,arr);
      }else{
        Assertions.assertEquals(expectedSize,arr.length);
        var itr=col.iterator();
        for(int i=0;i<expectedSize;++i){
          Assertions.assertEquals(itr.nextShort(),arr[i]);
        }
      }
    }
    @Override public void verifyPoll(OmniCollection.OfShort col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Short.MIN_VALUE,((PeekAndPollIfc.ShortInput)col).pollShort());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToshort(expectedVal),((PeekAndPollIfc.ShortInput)col).pollShort());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfShort col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Short.MIN_VALUE,((PeekAndPollIfc.ShortInput)col).peekShort());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToshort(expectedVal),((PeekAndPollIfc.ShortInput)col).peekShort());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfShort col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToshort(expectedVal),((OmniStack.OfShort)col).popShort());
    }
    @Override public void verifyQueueElement(OmniCollection.OfShort col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToshort(expectedVal),((OmniQueue.OfShort)col).shortElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfShort col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToshort(expectedVal),((OmniQueue.OfShort)col).removeShort());
    }
  }
  ,
  BOXED_TYPE{
    @Override public void verifyItrPrevious(OmniIterator.OfShort itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToShort(valToConvert),((OmniListIterator.OfShort)itr).previous());
    }
    @Override public void verifyItrNext(OmniIterator.OfShort itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToShort(valToConvert),itr.next());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfShort col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToShort(valToConvert),((OmniList.OfShort)col).remove(index));
    }
    @Override public void verifyListGet(OmniCollection.OfShort col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToShort(valToConvert),((OmniList.OfShort)col).get(index));
    }
    @Override public void verifyToArray(OmniCollection.OfShort col,int expectedSize){
      var arr=col.toArray();
      if(expectedSize==0){
        Assertions.assertSame(OmniArray.OfShort.DEFAULT_BOXED_ARR,arr);
      }else{
        Assertions.assertEquals(expectedSize,arr.length);
        var itr=col.iterator();
        for(int i=0;i<expectedSize;++i){
          Assertions.assertEquals(itr.next(),arr[i]);
        }
      }
    }
    @Override public void verifyPoll(OmniCollection.OfShort col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertNull(((PeekAndPollIfc<?>)col).poll());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToShort(expectedVal),((PeekAndPollIfc<?>)col).poll());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfShort col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertNull(((PeekAndPollIfc<?>)col).peek());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToShort(expectedVal),((PeekAndPollIfc<?>)col).peek());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfShort col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToShort(expectedVal),((OmniStack.OfShort)col).pop());
    }
    @Override public void verifyQueueElement(OmniCollection.OfShort col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToShort(expectedVal),((OmniQueue.OfShort)col).element());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfShort col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToShort(expectedVal),((OmniQueue.OfShort)col).remove());
    }
  }
  ,
  DOUBLE{
    @Override public void verifyItrPrevious(OmniIterator.OfShort itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),((OmniListIterator.OfShort)itr).previousDouble());
    }
    @Override public void verifyItrNext(OmniIterator.OfShort itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),itr.nextDouble());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfShort col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),((OmniList.OfShort)col).removeDoubleAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfShort col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),((OmniList.OfShort)col).getDouble(index));
    }
    @Override public void verifyToArray(OmniCollection.OfShort col,int expectedSize){
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
    @Override public void verifyPoll(OmniCollection.OfShort col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((PeekAndPollIfc.DoubleInput)col).pollDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((PeekAndPollIfc.DoubleInput)col).pollDouble());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfShort col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((PeekAndPollIfc.DoubleInput)col).peekDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((PeekAndPollIfc.DoubleInput)col).peekDouble());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfShort col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniStack.OfShort)col).popDouble());
    }
    @Override public void verifyQueueElement(OmniCollection.OfShort col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniQueue.OfShort)col).doubleElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfShort col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniQueue.OfShort)col).removeDouble());
    }
  }
  ,
  FLOAT{
    @Override public void verifyItrPrevious(OmniIterator.OfShort itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(valToConvert),((OmniListIterator.OfShort)itr).previousFloat());
    }
    @Override public void verifyItrNext(OmniIterator.OfShort itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(valToConvert),itr.nextFloat());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfShort col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(valToConvert),((OmniList.OfShort)col).removeFloatAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfShort col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(valToConvert),((OmniList.OfShort)col).getFloat(index));
    }
    @Override public void verifyToArray(OmniCollection.OfShort col,int expectedSize){
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
    @Override public void verifyPoll(OmniCollection.OfShort col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((PeekAndPollIfc.FloatInput)col).pollFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((PeekAndPollIfc.FloatInput)col).pollFloat());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfShort col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((PeekAndPollIfc.FloatInput)col).peekFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((PeekAndPollIfc.FloatInput)col).peekFloat());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfShort col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniStack.OfShort)col).popFloat());
    }
    @Override public void verifyQueueElement(OmniCollection.OfShort col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniQueue.OfShort)col).floatElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfShort col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniQueue.OfShort)col).removeFloat());
    }
  }
  ,
  LONG{
    @Override public void verifyItrPrevious(OmniIterator.OfShort itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(valToConvert),((OmniListIterator.OfShort)itr).previousLong());
    }
    @Override public void verifyItrNext(OmniIterator.OfShort itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(valToConvert),itr.nextLong());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfShort col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(valToConvert),((OmniList.OfShort)col).removeLongAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfShort col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(valToConvert),((OmniList.OfShort)col).getLong(index));
    }
    @Override public void verifyToArray(OmniCollection.OfShort col,int expectedSize){
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
    @Override public void verifyPoll(OmniCollection.OfShort col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Long.MIN_VALUE,((PeekAndPollIfc.LongInput)col).pollLong());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTolong(expectedVal),((PeekAndPollIfc.LongInput)col).pollLong());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfShort col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Long.MIN_VALUE,((PeekAndPollIfc.LongInput)col).peekLong());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTolong(expectedVal),((PeekAndPollIfc.LongInput)col).peekLong());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfShort col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(expectedVal),((OmniStack.OfShort)col).popLong());
    }
    @Override public void verifyQueueElement(OmniCollection.OfShort col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(expectedVal),((OmniQueue.OfShort)col).longElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfShort col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(expectedVal),((OmniQueue.OfShort)col).removeLong());
    }
  }
  ,
  INT{
    @Override public void verifyItrPrevious(OmniIterator.OfShort itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToint(valToConvert),((OmniListIterator.OfShort)itr).previousInt());
    }
    @Override public void verifyItrNext(OmniIterator.OfShort itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToint(valToConvert),itr.nextInt());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfShort col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToint(valToConvert),((OmniList.OfShort)col).removeIntAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfShort col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToint(valToConvert),((OmniList.OfShort)col).getInt(index));
    }
    @Override public void verifyToArray(OmniCollection.OfShort col,int expectedSize){
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
    @Override public void verifyPoll(OmniCollection.OfShort col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Integer.MIN_VALUE,((PeekAndPollIfc.IntInput)col).pollInt());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToint(expectedVal),((PeekAndPollIfc.IntInput)col).pollInt());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfShort col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Integer.MIN_VALUE,((PeekAndPollIfc.IntInput)col).peekInt());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToint(expectedVal),((PeekAndPollIfc.IntInput)col).peekInt());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfShort col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToint(expectedVal),((OmniStack.OfShort)col).popInt());
    }
    @Override public void verifyQueueElement(OmniCollection.OfShort col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToint(expectedVal),((OmniQueue.OfShort)col).intElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfShort col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToint(expectedVal),((OmniQueue.OfShort)col).removeInt());
    }
  }
  ;
  public abstract void verifyItrPrevious(OmniIterator.OfShort itr,int valToConvert);
  public abstract void verifyItrNext(OmniIterator.OfShort itr,int valToConvert);
  public abstract void verifyListRemoveAt(OmniCollection.OfShort col,int index,int valToConvert);
  public abstract void verifyListGet(OmniCollection.OfShort col,int index,int valToConvert);
  public abstract void verifyToArray(OmniCollection.OfShort col,int expectedSize);
  public abstract void verifyPoll(OmniCollection.OfShort col,int expectedSize,int expectedVal);
  public abstract void verifyStackPop(OmniCollection.OfShort col,int expectedVal);
  public abstract void verifyQueueRemove(OmniCollection.OfShort col,int expectedVal);
  public abstract void verifyQueueElement(OmniCollection.OfShort col,int expectedVal);
  public abstract void verifyPeek(OmniCollection.OfShort col,int expectedSize,int expectedVal);
  //TODO other method tests
}
