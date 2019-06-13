package omni.impl;
import omni.util.TypeConversionUtil;
import omni.api.OmniCollection;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.api.OmniQueue;
import omni.api.OmniDeque;
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
    @Override public void verifyDequePollLast(OmniCollection.OfInt col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Integer.MIN_VALUE,((OmniDeque.OfInt)col).pollLastInt());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToint(expectedVal),((OmniDeque.OfInt)col).pollLastInt());
      }
    }
    @Override public void verifyDequePollFirst(OmniCollection.OfInt col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Integer.MIN_VALUE,((OmniDeque.OfInt)col).pollFirstInt());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToint(expectedVal),((OmniDeque.OfInt)col).pollFirstInt());
      }
    }
    @Override public void verifyDequePeekLast(OmniCollection.OfInt col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Integer.MIN_VALUE,((OmniDeque.OfInt)col).peekLastInt());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToint(expectedVal),((OmniDeque.OfInt)col).peekLastInt());
      }
    }
    @Override public void verifyDequePeekFirst(OmniCollection.OfInt col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Integer.MIN_VALUE,((OmniDeque.OfInt)col).peekFirstInt());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToint(expectedVal),((OmniDeque.OfInt)col).peekFirstInt());
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
    @Override public void verifyDequeRemoveFirst(OmniCollection.OfInt col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToint(expectedVal),((OmniDeque.OfInt)col).removeFirstInt());
    }
    @Override public void verifyDequeRemoveLast(OmniCollection.OfInt col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToint(expectedVal),((OmniDeque.OfInt)col).removeLastInt());
    }
    @Override public void verifyDequeGetFirst(OmniCollection.OfInt col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToint(expectedVal),((OmniDeque.OfInt)col).getFirstInt());
    }
    @Override public void verifyDequeGetLast(OmniCollection.OfInt col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToint(expectedVal),((OmniDeque.OfInt)col).getLastInt());
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
    @Override public void verifyDequePollLast(OmniCollection.OfInt col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertNull(((OmniDeque.OfInt)col).pollLast());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToInteger(expectedVal),((OmniDeque.OfInt)col).pollLast());
      }
    }
    @Override public void verifyDequePollFirst(OmniCollection.OfInt col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertNull(((OmniDeque.OfInt)col).pollFirst());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToInteger(expectedVal),((OmniDeque.OfInt)col).pollFirst());
      }
    }
    @Override public void verifyDequePeekLast(OmniCollection.OfInt col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertNull(((OmniDeque.OfInt)col).peekLast());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToInteger(expectedVal),((OmniDeque.OfInt)col).peekLast());
      }
    }
    @Override public void verifyDequePeekFirst(OmniCollection.OfInt col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertNull(((OmniDeque.OfInt)col).peekFirst());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToInteger(expectedVal),((OmniDeque.OfInt)col).peekFirst());
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
    @Override public void verifyDequeRemoveFirst(OmniCollection.OfInt col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToInteger(expectedVal),((OmniDeque.OfInt)col).removeFirst());
    }
    @Override public void verifyDequeRemoveLast(OmniCollection.OfInt col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToInteger(expectedVal),((OmniDeque.OfInt)col).removeLast());
    }
    @Override public void verifyDequeGetFirst(OmniCollection.OfInt col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToInteger(expectedVal),((OmniDeque.OfInt)col).getFirst());
    }
    @Override public void verifyDequeGetLast(OmniCollection.OfInt col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToInteger(expectedVal),((OmniDeque.OfInt)col).getLast());
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
    @Override public void verifyDequePollLast(OmniCollection.OfInt col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((OmniDeque.OfInt)col).pollLastDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniDeque.OfInt)col).pollLastDouble());
      }
    }
    @Override public void verifyDequePollFirst(OmniCollection.OfInt col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((OmniDeque.OfInt)col).pollFirstDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniDeque.OfInt)col).pollFirstDouble());
      }
    }
    @Override public void verifyDequePeekLast(OmniCollection.OfInt col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((OmniDeque.OfInt)col).peekLastDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniDeque.OfInt)col).peekLastDouble());
      }
    }
    @Override public void verifyDequePeekFirst(OmniCollection.OfInt col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((OmniDeque.OfInt)col).peekFirstDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniDeque.OfInt)col).peekFirstDouble());
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
    @Override public void verifyDequeRemoveFirst(OmniCollection.OfInt col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniDeque.OfInt)col).removeFirstDouble());
    }
    @Override public void verifyDequeRemoveLast(OmniCollection.OfInt col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniDeque.OfInt)col).removeLastDouble());
    }
    @Override public void verifyDequeGetFirst(OmniCollection.OfInt col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniDeque.OfInt)col).getFirstDouble());
    }
    @Override public void verifyDequeGetLast(OmniCollection.OfInt col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniDeque.OfInt)col).getLastDouble());
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
    @Override public void verifyDequePollLast(OmniCollection.OfInt col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((OmniDeque.OfInt)col).pollLastFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniDeque.OfInt)col).pollLastFloat());
      }
    }
    @Override public void verifyDequePollFirst(OmniCollection.OfInt col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((OmniDeque.OfInt)col).pollFirstFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniDeque.OfInt)col).pollFirstFloat());
      }
    }
    @Override public void verifyDequePeekLast(OmniCollection.OfInt col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((OmniDeque.OfInt)col).peekLastFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniDeque.OfInt)col).peekLastFloat());
      }
    }
    @Override public void verifyDequePeekFirst(OmniCollection.OfInt col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((OmniDeque.OfInt)col).peekFirstFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniDeque.OfInt)col).peekFirstFloat());
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
    @Override public void verifyDequeRemoveFirst(OmniCollection.OfInt col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniDeque.OfInt)col).removeFirstFloat());
    }
    @Override public void verifyDequeRemoveLast(OmniCollection.OfInt col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniDeque.OfInt)col).removeLastFloat());
    }
    @Override public void verifyDequeGetFirst(OmniCollection.OfInt col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniDeque.OfInt)col).getFirstFloat());
    }
    @Override public void verifyDequeGetLast(OmniCollection.OfInt col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniDeque.OfInt)col).getLastFloat());
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
    @Override public void verifyDequePollLast(OmniCollection.OfInt col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Long.MIN_VALUE,((OmniDeque.OfInt)col).pollLastLong());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTolong(expectedVal),((OmniDeque.OfInt)col).pollLastLong());
      }
    }
    @Override public void verifyDequePollFirst(OmniCollection.OfInt col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Long.MIN_VALUE,((OmniDeque.OfInt)col).pollFirstLong());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTolong(expectedVal),((OmniDeque.OfInt)col).pollFirstLong());
      }
    }
    @Override public void verifyDequePeekLast(OmniCollection.OfInt col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Long.MIN_VALUE,((OmniDeque.OfInt)col).peekLastLong());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTolong(expectedVal),((OmniDeque.OfInt)col).peekLastLong());
      }
    }
    @Override public void verifyDequePeekFirst(OmniCollection.OfInt col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Long.MIN_VALUE,((OmniDeque.OfInt)col).peekFirstLong());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTolong(expectedVal),((OmniDeque.OfInt)col).peekFirstLong());
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
    @Override public void verifyDequeRemoveFirst(OmniCollection.OfInt col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(expectedVal),((OmniDeque.OfInt)col).removeFirstLong());
    }
    @Override public void verifyDequeRemoveLast(OmniCollection.OfInt col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(expectedVal),((OmniDeque.OfInt)col).removeLastLong());
    }
    @Override public void verifyDequeGetFirst(OmniCollection.OfInt col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(expectedVal),((OmniDeque.OfInt)col).getFirstLong());
    }
    @Override public void verifyDequeGetLast(OmniCollection.OfInt col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(expectedVal),((OmniDeque.OfInt)col).getLastLong());
    }
  }
  ;
  public abstract void verifyDequePeekLast(OmniCollection.OfInt col,int expectedSize,int expectedVal);
  public abstract void verifyDequePollLast(OmniCollection.OfInt col,int expectedSize,int expectedVal);
  public abstract void verifyDequePeekFirst(OmniCollection.OfInt col,int expectedSize,int expectedVal);
  public abstract void verifyDequePollFirst(OmniCollection.OfInt col,int expectedSize,int expectedVal);
  public abstract void verifyDequeRemoveLast(OmniCollection.OfInt col,int expectedVal);
  public abstract void verifyDequeRemoveFirst(OmniCollection.OfInt col,int expectedVal);
  public abstract void verifyDequeGetLast(OmniCollection.OfInt col,int expectedVal);
  public abstract void verifyDequeGetFirst(OmniCollection.OfInt col,int expectedVal);
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
}
