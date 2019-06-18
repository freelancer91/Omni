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
public enum BooleanOutputTestArgType{
  ARRAY_TYPE{
    @Override public void verifyItrPrevious(OmniIterator.OfBoolean itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(valToConvert),((OmniListIterator.OfBoolean)itr).previousBoolean());
    }
    @Override public void verifyItrNext(OmniIterator.OfBoolean itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(valToConvert),itr.nextBoolean());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfBoolean col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(valToConvert),((OmniList.OfBoolean)col).removeBooleanAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfBoolean col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(valToConvert),((OmniList.OfBoolean)col).getBoolean(index));
    }
    @Override public void verifyToArray(OmniCollection.OfBoolean col,int expectedSize){
      var arr=col.toBooleanArray();
      if(expectedSize==0){
        Assertions.assertSame(OmniArray.OfBoolean.DEFAULT_ARR,arr);
      }else{
        Assertions.assertEquals(expectedSize,arr.length);
        var itr=col.iterator();
        for(int i=0;i<expectedSize;++i){
          Assertions.assertEquals(itr.nextBoolean(),arr[i]);
        }
      }
    }
    @Override public void verifyPoll(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(false,((PeekAndPollIfc.BooleanOutput<?>)col).pollBoolean());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToboolean(expectedVal),((PeekAndPollIfc.BooleanOutput<?>)col).pollBoolean());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(false,((PeekAndPollIfc.BooleanOutput<?>)col).peekBoolean());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToboolean(expectedVal),((PeekAndPollIfc.BooleanOutput<?>)col).peekBoolean());
      }
    }
    @Override public void verifyDequePollLast(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(false,((OmniDeque.OfBoolean)col).pollLastBoolean());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToboolean(expectedVal),((OmniDeque.OfBoolean)col).pollLastBoolean());
      }
    }
    @Override public void verifyDequePollFirst(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(false,((OmniDeque.OfBoolean)col).pollFirstBoolean());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToboolean(expectedVal),((OmniDeque.OfBoolean)col).pollFirstBoolean());
      }
    }
    @Override public void verifyDequePeekLast(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(false,((OmniDeque.OfBoolean)col).peekLastBoolean());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToboolean(expectedVal),((OmniDeque.OfBoolean)col).peekLastBoolean());
      }
    }
    @Override public void verifyDequePeekFirst(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(false,((OmniDeque.OfBoolean)col).peekFirstBoolean());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToboolean(expectedVal),((OmniDeque.OfBoolean)col).peekFirstBoolean());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(expectedVal),((OmniStack.OfBoolean)col).popBoolean());
    }
    @Override public void verifyQueueElement(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(expectedVal),((OmniQueue.OfBoolean)col).booleanElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(expectedVal),((OmniQueue.OfBoolean)col).removeBoolean());
    }
    @Override public void verifyDequeRemoveFirst(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(expectedVal),((OmniDeque.OfBoolean)col).removeFirstBoolean());
    }
    @Override public void verifyDequeRemoveLast(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(expectedVal),((OmniDeque.OfBoolean)col).removeLastBoolean());
    }
    @Override public void verifyDequeGetFirst(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(expectedVal),((OmniDeque.OfBoolean)col).getFirstBoolean());
    }
    @Override public void verifyDequeGetLast(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(expectedVal),((OmniDeque.OfBoolean)col).getLastBoolean());
    }
  }
  ,
  BOXED_TYPE{
    @Override public void verifyItrPrevious(OmniIterator.OfBoolean itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToBoolean(valToConvert),((OmniListIterator.OfBoolean)itr).previous());
    }
    @Override public void verifyItrNext(OmniIterator.OfBoolean itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToBoolean(valToConvert),itr.next());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfBoolean col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToBoolean(valToConvert),((OmniList.OfBoolean)col).remove(index));
    }
    @Override public void verifyListGet(OmniCollection.OfBoolean col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToBoolean(valToConvert),((OmniList.OfBoolean)col).get(index));
    }
    @Override public void verifyToArray(OmniCollection.OfBoolean col,int expectedSize){
      var arr=col.toArray();
      if(expectedSize==0){
        Assertions.assertSame(OmniArray.OfBoolean.DEFAULT_BOXED_ARR,arr);
      }else{
        Assertions.assertEquals(expectedSize,arr.length);
        var itr=col.iterator();
        for(int i=0;i<expectedSize;++i){
          Assertions.assertEquals(itr.next(),arr[i]);
        }
      }
    }
    @Override public void verifyPoll(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertNull(((PeekAndPollIfc<?>)col).poll());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToBoolean(expectedVal),((PeekAndPollIfc<?>)col).poll());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertNull(((PeekAndPollIfc<?>)col).peek());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToBoolean(expectedVal),((PeekAndPollIfc<?>)col).peek());
      }
    }
    @Override public void verifyDequePollLast(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertNull(((OmniDeque.OfBoolean)col).pollLast());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToBoolean(expectedVal),((OmniDeque.OfBoolean)col).pollLast());
      }
    }
    @Override public void verifyDequePollFirst(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertNull(((OmniDeque.OfBoolean)col).pollFirst());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToBoolean(expectedVal),((OmniDeque.OfBoolean)col).pollFirst());
      }
    }
    @Override public void verifyDequePeekLast(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertNull(((OmniDeque.OfBoolean)col).peekLast());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToBoolean(expectedVal),((OmniDeque.OfBoolean)col).peekLast());
      }
    }
    @Override public void verifyDequePeekFirst(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertNull(((OmniDeque.OfBoolean)col).peekFirst());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToBoolean(expectedVal),((OmniDeque.OfBoolean)col).peekFirst());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToBoolean(expectedVal),((OmniStack.OfBoolean)col).pop());
    }
    @Override public void verifyQueueElement(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToBoolean(expectedVal),((OmniQueue.OfBoolean)col).element());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToBoolean(expectedVal),((OmniQueue.OfBoolean)col).remove());
    }
    @Override public void verifyDequeRemoveFirst(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToBoolean(expectedVal),((OmniDeque.OfBoolean)col).removeFirst());
    }
    @Override public void verifyDequeRemoveLast(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToBoolean(expectedVal),((OmniDeque.OfBoolean)col).removeLast());
    }
    @Override public void verifyDequeGetFirst(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToBoolean(expectedVal),((OmniDeque.OfBoolean)col).getFirst());
    }
    @Override public void verifyDequeGetLast(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToBoolean(expectedVal),((OmniDeque.OfBoolean)col).getLast());
    }
  }
  ,
  DOUBLE{
    @Override public void verifyItrPrevious(OmniIterator.OfBoolean itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodoubleboolean(valToConvert),((OmniListIterator.OfBoolean)itr).previousDouble());
    }
    @Override public void verifyItrNext(OmniIterator.OfBoolean itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodoubleboolean(valToConvert),itr.nextDouble());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfBoolean col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodoubleboolean(valToConvert),((OmniList.OfBoolean)col).removeDoubleAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfBoolean col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodoubleboolean(valToConvert),((OmniList.OfBoolean)col).getDouble(index));
    }
    @Override public void verifyToArray(OmniCollection.OfBoolean col,int expectedSize){
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
    @Override public void verifyPoll(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((PeekAndPollIfc.DoubleOutput<?>)col).pollDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodoubleboolean(expectedVal),((PeekAndPollIfc.DoubleOutput<?>)col).pollDouble());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((PeekAndPollIfc.DoubleOutput<?>)col).peekDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodoubleboolean(expectedVal),((PeekAndPollIfc.DoubleOutput<?>)col).peekDouble());
      }
    }
    @Override public void verifyDequePollLast(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((OmniDeque.OfBoolean)col).pollLastDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodoubleboolean(expectedVal),((OmniDeque.OfBoolean)col).pollLastDouble());
      }
    }
    @Override public void verifyDequePollFirst(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((OmniDeque.OfBoolean)col).pollFirstDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodoubleboolean(expectedVal),((OmniDeque.OfBoolean)col).pollFirstDouble());
      }
    }
    @Override public void verifyDequePeekLast(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((OmniDeque.OfBoolean)col).peekLastDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodoubleboolean(expectedVal),((OmniDeque.OfBoolean)col).peekLastDouble());
      }
    }
    @Override public void verifyDequePeekFirst(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((OmniDeque.OfBoolean)col).peekFirstDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodoubleboolean(expectedVal),((OmniDeque.OfBoolean)col).peekFirstDouble());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodoubleboolean(expectedVal),((OmniStack.OfBoolean)col).popDouble());
    }
    @Override public void verifyQueueElement(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodoubleboolean(expectedVal),((OmniQueue.OfBoolean)col).doubleElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodoubleboolean(expectedVal),((OmniQueue.OfBoolean)col).removeDouble());
    }
    @Override public void verifyDequeRemoveFirst(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodoubleboolean(expectedVal),((OmniDeque.OfBoolean)col).removeFirstDouble());
    }
    @Override public void verifyDequeRemoveLast(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodoubleboolean(expectedVal),((OmniDeque.OfBoolean)col).removeLastDouble());
    }
    @Override public void verifyDequeGetFirst(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodoubleboolean(expectedVal),((OmniDeque.OfBoolean)col).getFirstDouble());
    }
    @Override public void verifyDequeGetLast(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodoubleboolean(expectedVal),((OmniDeque.OfBoolean)col).getLastDouble());
    }
  }
  ,
  FLOAT{
    @Override public void verifyItrPrevious(OmniIterator.OfBoolean itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(valToConvert),((OmniListIterator.OfBoolean)itr).previousFloat());
    }
    @Override public void verifyItrNext(OmniIterator.OfBoolean itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(valToConvert),itr.nextFloat());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfBoolean col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(valToConvert),((OmniList.OfBoolean)col).removeFloatAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfBoolean col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(valToConvert),((OmniList.OfBoolean)col).getFloat(index));
    }
    @Override public void verifyToArray(OmniCollection.OfBoolean col,int expectedSize){
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
    @Override public void verifyPoll(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((PeekAndPollIfc.FloatOutput<?>)col).pollFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(expectedVal),((PeekAndPollIfc.FloatOutput<?>)col).pollFloat());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((PeekAndPollIfc.FloatOutput<?>)col).peekFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(expectedVal),((PeekAndPollIfc.FloatOutput<?>)col).peekFloat());
      }
    }
    @Override public void verifyDequePollLast(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((OmniDeque.OfBoolean)col).pollLastFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(expectedVal),((OmniDeque.OfBoolean)col).pollLastFloat());
      }
    }
    @Override public void verifyDequePollFirst(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((OmniDeque.OfBoolean)col).pollFirstFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(expectedVal),((OmniDeque.OfBoolean)col).pollFirstFloat());
      }
    }
    @Override public void verifyDequePeekLast(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((OmniDeque.OfBoolean)col).peekLastFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(expectedVal),((OmniDeque.OfBoolean)col).peekLastFloat());
      }
    }
    @Override public void verifyDequePeekFirst(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((OmniDeque.OfBoolean)col).peekFirstFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(expectedVal),((OmniDeque.OfBoolean)col).peekFirstFloat());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(expectedVal),((OmniStack.OfBoolean)col).popFloat());
    }
    @Override public void verifyQueueElement(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(expectedVal),((OmniQueue.OfBoolean)col).floatElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(expectedVal),((OmniQueue.OfBoolean)col).removeFloat());
    }
    @Override public void verifyDequeRemoveFirst(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(expectedVal),((OmniDeque.OfBoolean)col).removeFirstFloat());
    }
    @Override public void verifyDequeRemoveLast(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(expectedVal),((OmniDeque.OfBoolean)col).removeLastFloat());
    }
    @Override public void verifyDequeGetFirst(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(expectedVal),((OmniDeque.OfBoolean)col).getFirstFloat());
    }
    @Override public void verifyDequeGetLast(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(expectedVal),((OmniDeque.OfBoolean)col).getLastFloat());
    }
  }
  ,
  LONG{
    @Override public void verifyItrPrevious(OmniIterator.OfBoolean itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolongboolean(valToConvert),((OmniListIterator.OfBoolean)itr).previousLong());
    }
    @Override public void verifyItrNext(OmniIterator.OfBoolean itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolongboolean(valToConvert),itr.nextLong());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfBoolean col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolongboolean(valToConvert),((OmniList.OfBoolean)col).removeLongAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfBoolean col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolongboolean(valToConvert),((OmniList.OfBoolean)col).getLong(index));
    }
    @Override public void verifyToArray(OmniCollection.OfBoolean col,int expectedSize){
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
    @Override public void verifyPoll(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Long.MIN_VALUE,((PeekAndPollIfc.LongOutput<?>)col).pollLong());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTolongboolean(expectedVal),((PeekAndPollIfc.LongOutput<?>)col).pollLong());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Long.MIN_VALUE,((PeekAndPollIfc.LongOutput<?>)col).peekLong());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTolongboolean(expectedVal),((PeekAndPollIfc.LongOutput<?>)col).peekLong());
      }
    }
    @Override public void verifyDequePollLast(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Long.MIN_VALUE,((OmniDeque.OfBoolean)col).pollLastLong());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTolongboolean(expectedVal),((OmniDeque.OfBoolean)col).pollLastLong());
      }
    }
    @Override public void verifyDequePollFirst(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Long.MIN_VALUE,((OmniDeque.OfBoolean)col).pollFirstLong());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTolongboolean(expectedVal),((OmniDeque.OfBoolean)col).pollFirstLong());
      }
    }
    @Override public void verifyDequePeekLast(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Long.MIN_VALUE,((OmniDeque.OfBoolean)col).peekLastLong());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTolongboolean(expectedVal),((OmniDeque.OfBoolean)col).peekLastLong());
      }
    }
    @Override public void verifyDequePeekFirst(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Long.MIN_VALUE,((OmniDeque.OfBoolean)col).peekFirstLong());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTolongboolean(expectedVal),((OmniDeque.OfBoolean)col).peekFirstLong());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolongboolean(expectedVal),((OmniStack.OfBoolean)col).popLong());
    }
    @Override public void verifyQueueElement(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolongboolean(expectedVal),((OmniQueue.OfBoolean)col).longElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolongboolean(expectedVal),((OmniQueue.OfBoolean)col).removeLong());
    }
    @Override public void verifyDequeRemoveFirst(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolongboolean(expectedVal),((OmniDeque.OfBoolean)col).removeFirstLong());
    }
    @Override public void verifyDequeRemoveLast(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolongboolean(expectedVal),((OmniDeque.OfBoolean)col).removeLastLong());
    }
    @Override public void verifyDequeGetFirst(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolongboolean(expectedVal),((OmniDeque.OfBoolean)col).getFirstLong());
    }
    @Override public void verifyDequeGetLast(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolongboolean(expectedVal),((OmniDeque.OfBoolean)col).getLastLong());
    }
  }
  ,
  INT{
    @Override public void verifyItrPrevious(OmniIterator.OfBoolean itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(valToConvert),((OmniListIterator.OfBoolean)itr).previousInt());
    }
    @Override public void verifyItrNext(OmniIterator.OfBoolean itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(valToConvert),itr.nextInt());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfBoolean col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(valToConvert),((OmniList.OfBoolean)col).removeIntAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfBoolean col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(valToConvert),((OmniList.OfBoolean)col).getInt(index));
    }
    @Override public void verifyToArray(OmniCollection.OfBoolean col,int expectedSize){
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
    @Override public void verifyPoll(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Integer.MIN_VALUE,((PeekAndPollIfc.IntOutput<?>)col).pollInt());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTointboolean(expectedVal),((PeekAndPollIfc.IntOutput<?>)col).pollInt());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Integer.MIN_VALUE,((PeekAndPollIfc.IntOutput<?>)col).peekInt());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTointboolean(expectedVal),((PeekAndPollIfc.IntOutput<?>)col).peekInt());
      }
    }
    @Override public void verifyDequePollLast(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Integer.MIN_VALUE,((OmniDeque.OfBoolean)col).pollLastInt());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTointboolean(expectedVal),((OmniDeque.OfBoolean)col).pollLastInt());
      }
    }
    @Override public void verifyDequePollFirst(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Integer.MIN_VALUE,((OmniDeque.OfBoolean)col).pollFirstInt());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTointboolean(expectedVal),((OmniDeque.OfBoolean)col).pollFirstInt());
      }
    }
    @Override public void verifyDequePeekLast(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Integer.MIN_VALUE,((OmniDeque.OfBoolean)col).peekLastInt());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTointboolean(expectedVal),((OmniDeque.OfBoolean)col).peekLastInt());
      }
    }
    @Override public void verifyDequePeekFirst(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Integer.MIN_VALUE,((OmniDeque.OfBoolean)col).peekFirstInt());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTointboolean(expectedVal),((OmniDeque.OfBoolean)col).peekFirstInt());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(expectedVal),((OmniStack.OfBoolean)col).popInt());
    }
    @Override public void verifyQueueElement(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(expectedVal),((OmniQueue.OfBoolean)col).intElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(expectedVal),((OmniQueue.OfBoolean)col).removeInt());
    }
    @Override public void verifyDequeRemoveFirst(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(expectedVal),((OmniDeque.OfBoolean)col).removeFirstInt());
    }
    @Override public void verifyDequeRemoveLast(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(expectedVal),((OmniDeque.OfBoolean)col).removeLastInt());
    }
    @Override public void verifyDequeGetFirst(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(expectedVal),((OmniDeque.OfBoolean)col).getFirstInt());
    }
    @Override public void verifyDequeGetLast(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(expectedVal),((OmniDeque.OfBoolean)col).getLastInt());
    }
  }
  ,
  SHORT{
    @Override public void verifyItrPrevious(OmniIterator.OfBoolean itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(valToConvert),((OmniListIterator.OfBoolean)itr).previousShort());
    }
    @Override public void verifyItrNext(OmniIterator.OfBoolean itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(valToConvert),itr.nextShort());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfBoolean col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(valToConvert),((OmniList.OfBoolean)col).removeShortAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfBoolean col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(valToConvert),((OmniList.OfBoolean)col).getShort(index));
    }
    @Override public void verifyToArray(OmniCollection.OfBoolean col,int expectedSize){
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
    @Override public void verifyPoll(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Short.MIN_VALUE,((PeekAndPollIfc.ShortOutput<?>)col).pollShort());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(expectedVal),((PeekAndPollIfc.ShortOutput<?>)col).pollShort());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Short.MIN_VALUE,((PeekAndPollIfc.ShortOutput<?>)col).peekShort());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(expectedVal),((PeekAndPollIfc.ShortOutput<?>)col).peekShort());
      }
    }
    @Override public void verifyDequePollLast(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Short.MIN_VALUE,((OmniDeque.OfBoolean)col).pollLastShort());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(expectedVal),((OmniDeque.OfBoolean)col).pollLastShort());
      }
    }
    @Override public void verifyDequePollFirst(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Short.MIN_VALUE,((OmniDeque.OfBoolean)col).pollFirstShort());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(expectedVal),((OmniDeque.OfBoolean)col).pollFirstShort());
      }
    }
    @Override public void verifyDequePeekLast(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Short.MIN_VALUE,((OmniDeque.OfBoolean)col).peekLastShort());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(expectedVal),((OmniDeque.OfBoolean)col).peekLastShort());
      }
    }
    @Override public void verifyDequePeekFirst(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Short.MIN_VALUE,((OmniDeque.OfBoolean)col).peekFirstShort());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(expectedVal),((OmniDeque.OfBoolean)col).peekFirstShort());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(expectedVal),((OmniStack.OfBoolean)col).popShort());
    }
    @Override public void verifyQueueElement(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(expectedVal),((OmniQueue.OfBoolean)col).shortElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(expectedVal),((OmniQueue.OfBoolean)col).removeShort());
    }
    @Override public void verifyDequeRemoveFirst(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(expectedVal),((OmniDeque.OfBoolean)col).removeFirstShort());
    }
    @Override public void verifyDequeRemoveLast(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(expectedVal),((OmniDeque.OfBoolean)col).removeLastShort());
    }
    @Override public void verifyDequeGetFirst(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(expectedVal),((OmniDeque.OfBoolean)col).getFirstShort());
    }
    @Override public void verifyDequeGetLast(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(expectedVal),((OmniDeque.OfBoolean)col).getLastShort());
    }
  }
  ,
  BYTE{
    @Override public void verifyItrPrevious(OmniIterator.OfBoolean itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(valToConvert),((OmniListIterator.OfBoolean)itr).previousByte());
    }
    @Override public void verifyItrNext(OmniIterator.OfBoolean itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(valToConvert),itr.nextByte());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfBoolean col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(valToConvert),((OmniList.OfBoolean)col).removeByteAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfBoolean col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(valToConvert),((OmniList.OfBoolean)col).getByte(index));
    }
    @Override public void verifyToArray(OmniCollection.OfBoolean col,int expectedSize){
      var arr=col.toByteArray();
      if(expectedSize==0){
        Assertions.assertSame(OmniArray.OfByte.DEFAULT_ARR,arr);
      }else{
        Assertions.assertEquals(expectedSize,arr.length);
        var itr=col.iterator();
        for(int i=0;i<expectedSize;++i){
          Assertions.assertEquals(itr.nextByte(),arr[i]);
        }
      }
    }
    @Override public void verifyPoll(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Byte.MIN_VALUE,((PeekAndPollIfc.ByteOutput<?>)col).pollByte());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(expectedVal),((PeekAndPollIfc.ByteOutput<?>)col).pollByte());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Byte.MIN_VALUE,((PeekAndPollIfc.ByteOutput<?>)col).peekByte());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(expectedVal),((PeekAndPollIfc.ByteOutput<?>)col).peekByte());
      }
    }
    @Override public void verifyDequePollLast(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Byte.MIN_VALUE,((OmniDeque.OfBoolean)col).pollLastByte());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(expectedVal),((OmniDeque.OfBoolean)col).pollLastByte());
      }
    }
    @Override public void verifyDequePollFirst(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Byte.MIN_VALUE,((OmniDeque.OfBoolean)col).pollFirstByte());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(expectedVal),((OmniDeque.OfBoolean)col).pollFirstByte());
      }
    }
    @Override public void verifyDequePeekLast(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Byte.MIN_VALUE,((OmniDeque.OfBoolean)col).peekLastByte());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(expectedVal),((OmniDeque.OfBoolean)col).peekLastByte());
      }
    }
    @Override public void verifyDequePeekFirst(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Byte.MIN_VALUE,((OmniDeque.OfBoolean)col).peekFirstByte());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(expectedVal),((OmniDeque.OfBoolean)col).peekFirstByte());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(expectedVal),((OmniStack.OfBoolean)col).popByte());
    }
    @Override public void verifyQueueElement(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(expectedVal),((OmniQueue.OfBoolean)col).byteElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(expectedVal),((OmniQueue.OfBoolean)col).removeByte());
    }
    @Override public void verifyDequeRemoveFirst(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(expectedVal),((OmniDeque.OfBoolean)col).removeFirstByte());
    }
    @Override public void verifyDequeRemoveLast(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(expectedVal),((OmniDeque.OfBoolean)col).removeLastByte());
    }
    @Override public void verifyDequeGetFirst(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(expectedVal),((OmniDeque.OfBoolean)col).getFirstByte());
    }
    @Override public void verifyDequeGetLast(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(expectedVal),((OmniDeque.OfBoolean)col).getLastByte());
    }
  }
  ,
  CHAR{
    @Override public void verifyItrPrevious(OmniIterator.OfBoolean itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(valToConvert),((OmniListIterator.OfBoolean)itr).previousChar());
    }
    @Override public void verifyItrNext(OmniIterator.OfBoolean itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(valToConvert),itr.nextChar());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfBoolean col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(valToConvert),((OmniList.OfBoolean)col).removeCharAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfBoolean col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(valToConvert),((OmniList.OfBoolean)col).getChar(index));
    }
    @Override public void verifyToArray(OmniCollection.OfBoolean col,int expectedSize){
      var arr=col.toCharArray();
      if(expectedSize==0){
        Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,arr);
      }else{
        Assertions.assertEquals(expectedSize,arr.length);
        var itr=col.iterator();
        for(int i=0;i<expectedSize;++i){
          Assertions.assertEquals(itr.nextChar(),arr[i]);
        }
      }
    }
    @Override public void verifyPoll(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Character.MIN_VALUE,((PeekAndPollIfc.CharOutput<?>)col).pollChar());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(expectedVal),((PeekAndPollIfc.CharOutput<?>)col).pollChar());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Character.MIN_VALUE,((PeekAndPollIfc.CharOutput<?>)col).peekChar());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(expectedVal),((PeekAndPollIfc.CharOutput<?>)col).peekChar());
      }
    }
    @Override public void verifyDequePollLast(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Character.MIN_VALUE,((OmniDeque.OfBoolean)col).pollLastChar());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(expectedVal),((OmniDeque.OfBoolean)col).pollLastChar());
      }
    }
    @Override public void verifyDequePollFirst(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Character.MIN_VALUE,((OmniDeque.OfBoolean)col).pollFirstChar());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(expectedVal),((OmniDeque.OfBoolean)col).pollFirstChar());
      }
    }
    @Override public void verifyDequePeekLast(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Character.MIN_VALUE,((OmniDeque.OfBoolean)col).peekLastChar());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(expectedVal),((OmniDeque.OfBoolean)col).peekLastChar());
      }
    }
    @Override public void verifyDequePeekFirst(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Character.MIN_VALUE,((OmniDeque.OfBoolean)col).peekFirstChar());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(expectedVal),((OmniDeque.OfBoolean)col).peekFirstChar());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(expectedVal),((OmniStack.OfBoolean)col).popChar());
    }
    @Override public void verifyQueueElement(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(expectedVal),((OmniQueue.OfBoolean)col).charElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(expectedVal),((OmniQueue.OfBoolean)col).removeChar());
    }
    @Override public void verifyDequeRemoveFirst(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(expectedVal),((OmniDeque.OfBoolean)col).removeFirstChar());
    }
    @Override public void verifyDequeRemoveLast(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(expectedVal),((OmniDeque.OfBoolean)col).removeLastChar());
    }
    @Override public void verifyDequeGetFirst(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(expectedVal),((OmniDeque.OfBoolean)col).getFirstChar());
    }
    @Override public void verifyDequeGetLast(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(expectedVal),((OmniDeque.OfBoolean)col).getLastChar());
    }
  }
  ;
  public abstract void verifyDequePeekLast(OmniCollection.OfBoolean col,int expectedSize,int expectedVal);
  public abstract void verifyDequePollLast(OmniCollection.OfBoolean col,int expectedSize,int expectedVal);
  public abstract void verifyDequePeekFirst(OmniCollection.OfBoolean col,int expectedSize,int expectedVal);
  public abstract void verifyDequePollFirst(OmniCollection.OfBoolean col,int expectedSize,int expectedVal);
  public abstract void verifyDequeRemoveLast(OmniCollection.OfBoolean col,int expectedVal);
  public abstract void verifyDequeRemoveFirst(OmniCollection.OfBoolean col,int expectedVal);
  public abstract void verifyDequeGetLast(OmniCollection.OfBoolean col,int expectedVal);
  public abstract void verifyDequeGetFirst(OmniCollection.OfBoolean col,int expectedVal);
  public abstract void verifyItrPrevious(OmniIterator.OfBoolean itr,int valToConvert);
  public abstract void verifyItrNext(OmniIterator.OfBoolean itr,int valToConvert);
  public abstract void verifyListRemoveAt(OmniCollection.OfBoolean col,int index,int valToConvert);
  public abstract void verifyListGet(OmniCollection.OfBoolean col,int index,int valToConvert);
  public abstract void verifyToArray(OmniCollection.OfBoolean col,int expectedSize);
  public abstract void verifyPoll(OmniCollection.OfBoolean col,int expectedSize,int expectedVal);
  public abstract void verifyStackPop(OmniCollection.OfBoolean col,int expectedVal);
  public abstract void verifyQueueRemove(OmniCollection.OfBoolean col,int expectedVal);
  public abstract void verifyQueueElement(OmniCollection.OfBoolean col,int expectedVal);
  public abstract void verifyPeek(OmniCollection.OfBoolean col,int expectedSize,int expectedVal);
}
