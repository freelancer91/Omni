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
public enum ByteOutputTestArgType{
  ARRAY_TYPE{
    @Override public void verifyItrPrevious(OmniIterator.OfByte itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(valToConvert),((OmniListIterator.OfByte)itr).previousByte());
    }
    @Override public void verifyItrNext(OmniIterator.OfByte itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(valToConvert),itr.nextByte());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfByte col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(valToConvert),((OmniList.OfByte)col).removeByteAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfByte col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(valToConvert),((OmniList.OfByte)col).getByte(index));
    }
    @Override public void verifyToArray(OmniCollection.OfByte col,int expectedSize){
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
    @Override public void verifyPoll(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Byte.MIN_VALUE,((PeekAndPollIfc.ByteOutput<?>)col).pollByte());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTobyte(expectedVal),((PeekAndPollIfc.ByteOutput<?>)col).pollByte());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Byte.MIN_VALUE,((PeekAndPollIfc.ByteOutput<?>)col).peekByte());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTobyte(expectedVal),((PeekAndPollIfc.ByteOutput<?>)col).peekByte());
      }
    }
    @Override public void verifyDequePollLast(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Byte.MIN_VALUE,((OmniDeque.OfByte)col).pollLastByte());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTobyte(expectedVal),((OmniDeque.OfByte)col).pollLastByte());
      }
    }
    @Override public void verifyDequePollFirst(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Byte.MIN_VALUE,((OmniDeque.OfByte)col).pollFirstByte());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTobyte(expectedVal),((OmniDeque.OfByte)col).pollFirstByte());
      }
    }
    @Override public void verifyDequePeekLast(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Byte.MIN_VALUE,((OmniDeque.OfByte)col).peekLastByte());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTobyte(expectedVal),((OmniDeque.OfByte)col).peekLastByte());
      }
    }
    @Override public void verifyDequePeekFirst(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Byte.MIN_VALUE,((OmniDeque.OfByte)col).peekFirstByte());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTobyte(expectedVal),((OmniDeque.OfByte)col).peekFirstByte());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(expectedVal),((OmniStack.OfByte)col).popByte());
    }
    @Override public void verifyQueueElement(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(expectedVal),((OmniQueue.OfByte)col).byteElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(expectedVal),((OmniQueue.OfByte)col).removeByte());
    }
    @Override public void verifyDequeRemoveFirst(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(expectedVal),((OmniDeque.OfByte)col).removeFirstByte());
    }
    @Override public void verifyDequeRemoveLast(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(expectedVal),((OmniDeque.OfByte)col).removeLastByte());
    }
    @Override public void verifyDequeGetFirst(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(expectedVal),((OmniDeque.OfByte)col).getFirstByte());
    }
    @Override public void verifyDequeGetLast(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(expectedVal),((OmniDeque.OfByte)col).getLastByte());
    }
  }
  ,
  BOXED_TYPE{
    @Override public void verifyItrPrevious(OmniIterator.OfByte itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToByte(valToConvert),((OmniListIterator.OfByte)itr).previous());
    }
    @Override public void verifyItrNext(OmniIterator.OfByte itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToByte(valToConvert),itr.next());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfByte col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToByte(valToConvert),((OmniList.OfByte)col).remove(index));
    }
    @Override public void verifyListGet(OmniCollection.OfByte col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToByte(valToConvert),((OmniList.OfByte)col).get(index));
    }
    @Override public void verifyToArray(OmniCollection.OfByte col,int expectedSize){
      var arr=col.toArray();
      if(expectedSize==0){
        Assertions.assertSame(OmniArray.OfByte.DEFAULT_BOXED_ARR,arr);
      }else{
        Assertions.assertEquals(expectedSize,arr.length);
        var itr=col.iterator();
        for(int i=0;i<expectedSize;++i){
          Assertions.assertEquals(itr.next(),arr[i]);
        }
      }
    }
    @Override public void verifyPoll(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertNull(((PeekAndPollIfc<?>)col).poll());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToByte(expectedVal),((PeekAndPollIfc<?>)col).poll());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertNull(((PeekAndPollIfc<?>)col).peek());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToByte(expectedVal),((PeekAndPollIfc<?>)col).peek());
      }
    }
    @Override public void verifyDequePollLast(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertNull(((OmniDeque.OfByte)col).pollLast());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToByte(expectedVal),((OmniDeque.OfByte)col).pollLast());
      }
    }
    @Override public void verifyDequePollFirst(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertNull(((OmniDeque.OfByte)col).pollFirst());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToByte(expectedVal),((OmniDeque.OfByte)col).pollFirst());
      }
    }
    @Override public void verifyDequePeekLast(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertNull(((OmniDeque.OfByte)col).peekLast());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToByte(expectedVal),((OmniDeque.OfByte)col).peekLast());
      }
    }
    @Override public void verifyDequePeekFirst(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertNull(((OmniDeque.OfByte)col).peekFirst());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToByte(expectedVal),((OmniDeque.OfByte)col).peekFirst());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToByte(expectedVal),((OmniStack.OfByte)col).pop());
    }
    @Override public void verifyQueueElement(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToByte(expectedVal),((OmniQueue.OfByte)col).element());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToByte(expectedVal),((OmniQueue.OfByte)col).remove());
    }
    @Override public void verifyDequeRemoveFirst(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToByte(expectedVal),((OmniDeque.OfByte)col).removeFirst());
    }
    @Override public void verifyDequeRemoveLast(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToByte(expectedVal),((OmniDeque.OfByte)col).removeLast());
    }
    @Override public void verifyDequeGetFirst(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToByte(expectedVal),((OmniDeque.OfByte)col).getFirst());
    }
    @Override public void verifyDequeGetLast(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToByte(expectedVal),((OmniDeque.OfByte)col).getLast());
    }
  }
  ,
  DOUBLE{
    @Override public void verifyItrPrevious(OmniIterator.OfByte itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodoublebyte(valToConvert),((OmniListIterator.OfByte)itr).previousDouble());
    }
    @Override public void verifyItrNext(OmniIterator.OfByte itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodoublebyte(valToConvert),itr.nextDouble());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfByte col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodoublebyte(valToConvert),((OmniList.OfByte)col).removeDoubleAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfByte col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodoublebyte(valToConvert),((OmniList.OfByte)col).getDouble(index));
    }
    @Override public void verifyToArray(OmniCollection.OfByte col,int expectedSize){
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
    @Override public void verifyPoll(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((PeekAndPollIfc.DoubleOutput<?>)col).pollDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodoublebyte(expectedVal),((PeekAndPollIfc.DoubleOutput<?>)col).pollDouble());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((PeekAndPollIfc.DoubleOutput<?>)col).peekDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodoublebyte(expectedVal),((PeekAndPollIfc.DoubleOutput<?>)col).peekDouble());
      }
    }
    @Override public void verifyDequePollLast(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((OmniDeque.OfByte)col).pollLastDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodoublebyte(expectedVal),((OmniDeque.OfByte)col).pollLastDouble());
      }
    }
    @Override public void verifyDequePollFirst(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((OmniDeque.OfByte)col).pollFirstDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodoublebyte(expectedVal),((OmniDeque.OfByte)col).pollFirstDouble());
      }
    }
    @Override public void verifyDequePeekLast(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((OmniDeque.OfByte)col).peekLastDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodoublebyte(expectedVal),((OmniDeque.OfByte)col).peekLastDouble());
      }
    }
    @Override public void verifyDequePeekFirst(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((OmniDeque.OfByte)col).peekFirstDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodoublebyte(expectedVal),((OmniDeque.OfByte)col).peekFirstDouble());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodoublebyte(expectedVal),((OmniStack.OfByte)col).popDouble());
    }
    @Override public void verifyQueueElement(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodoublebyte(expectedVal),((OmniQueue.OfByte)col).doubleElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodoublebyte(expectedVal),((OmniQueue.OfByte)col).removeDouble());
    }
    @Override public void verifyDequeRemoveFirst(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodoublebyte(expectedVal),((OmniDeque.OfByte)col).removeFirstDouble());
    }
    @Override public void verifyDequeRemoveLast(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodoublebyte(expectedVal),((OmniDeque.OfByte)col).removeLastDouble());
    }
    @Override public void verifyDequeGetFirst(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodoublebyte(expectedVal),((OmniDeque.OfByte)col).getFirstDouble());
    }
    @Override public void verifyDequeGetLast(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodoublebyte(expectedVal),((OmniDeque.OfByte)col).getLastDouble());
    }
  }
  ,
  FLOAT{
    @Override public void verifyItrPrevious(OmniIterator.OfByte itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloatbyte(valToConvert),((OmniListIterator.OfByte)itr).previousFloat());
    }
    @Override public void verifyItrNext(OmniIterator.OfByte itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloatbyte(valToConvert),itr.nextFloat());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfByte col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloatbyte(valToConvert),((OmniList.OfByte)col).removeFloatAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfByte col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloatbyte(valToConvert),((OmniList.OfByte)col).getFloat(index));
    }
    @Override public void verifyToArray(OmniCollection.OfByte col,int expectedSize){
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
    @Override public void verifyPoll(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((PeekAndPollIfc.FloatOutput<?>)col).pollFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloatbyte(expectedVal),((PeekAndPollIfc.FloatOutput<?>)col).pollFloat());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((PeekAndPollIfc.FloatOutput<?>)col).peekFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloatbyte(expectedVal),((PeekAndPollIfc.FloatOutput<?>)col).peekFloat());
      }
    }
    @Override public void verifyDequePollLast(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((OmniDeque.OfByte)col).pollLastFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloatbyte(expectedVal),((OmniDeque.OfByte)col).pollLastFloat());
      }
    }
    @Override public void verifyDequePollFirst(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((OmniDeque.OfByte)col).pollFirstFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloatbyte(expectedVal),((OmniDeque.OfByte)col).pollFirstFloat());
      }
    }
    @Override public void verifyDequePeekLast(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((OmniDeque.OfByte)col).peekLastFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloatbyte(expectedVal),((OmniDeque.OfByte)col).peekLastFloat());
      }
    }
    @Override public void verifyDequePeekFirst(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((OmniDeque.OfByte)col).peekFirstFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloatbyte(expectedVal),((OmniDeque.OfByte)col).peekFirstFloat());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloatbyte(expectedVal),((OmniStack.OfByte)col).popFloat());
    }
    @Override public void verifyQueueElement(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloatbyte(expectedVal),((OmniQueue.OfByte)col).floatElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloatbyte(expectedVal),((OmniQueue.OfByte)col).removeFloat());
    }
    @Override public void verifyDequeRemoveFirst(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloatbyte(expectedVal),((OmniDeque.OfByte)col).removeFirstFloat());
    }
    @Override public void verifyDequeRemoveLast(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloatbyte(expectedVal),((OmniDeque.OfByte)col).removeLastFloat());
    }
    @Override public void verifyDequeGetFirst(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloatbyte(expectedVal),((OmniDeque.OfByte)col).getFirstFloat());
    }
    @Override public void verifyDequeGetLast(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloatbyte(expectedVal),((OmniDeque.OfByte)col).getLastFloat());
    }
  }
  ,
  LONG{
    @Override public void verifyItrPrevious(OmniIterator.OfByte itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolongbyte(valToConvert),((OmniListIterator.OfByte)itr).previousLong());
    }
    @Override public void verifyItrNext(OmniIterator.OfByte itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolongbyte(valToConvert),itr.nextLong());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfByte col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolongbyte(valToConvert),((OmniList.OfByte)col).removeLongAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfByte col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolongbyte(valToConvert),((OmniList.OfByte)col).getLong(index));
    }
    @Override public void verifyToArray(OmniCollection.OfByte col,int expectedSize){
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
    @Override public void verifyPoll(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Long.MIN_VALUE,((PeekAndPollIfc.LongOutput<?>)col).pollLong());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTolongbyte(expectedVal),((PeekAndPollIfc.LongOutput<?>)col).pollLong());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Long.MIN_VALUE,((PeekAndPollIfc.LongOutput<?>)col).peekLong());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTolongbyte(expectedVal),((PeekAndPollIfc.LongOutput<?>)col).peekLong());
      }
    }
    @Override public void verifyDequePollLast(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Long.MIN_VALUE,((OmniDeque.OfByte)col).pollLastLong());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTolongbyte(expectedVal),((OmniDeque.OfByte)col).pollLastLong());
      }
    }
    @Override public void verifyDequePollFirst(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Long.MIN_VALUE,((OmniDeque.OfByte)col).pollFirstLong());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTolongbyte(expectedVal),((OmniDeque.OfByte)col).pollFirstLong());
      }
    }
    @Override public void verifyDequePeekLast(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Long.MIN_VALUE,((OmniDeque.OfByte)col).peekLastLong());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTolongbyte(expectedVal),((OmniDeque.OfByte)col).peekLastLong());
      }
    }
    @Override public void verifyDequePeekFirst(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Long.MIN_VALUE,((OmniDeque.OfByte)col).peekFirstLong());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTolongbyte(expectedVal),((OmniDeque.OfByte)col).peekFirstLong());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolongbyte(expectedVal),((OmniStack.OfByte)col).popLong());
    }
    @Override public void verifyQueueElement(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolongbyte(expectedVal),((OmniQueue.OfByte)col).longElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolongbyte(expectedVal),((OmniQueue.OfByte)col).removeLong());
    }
    @Override public void verifyDequeRemoveFirst(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolongbyte(expectedVal),((OmniDeque.OfByte)col).removeFirstLong());
    }
    @Override public void verifyDequeRemoveLast(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolongbyte(expectedVal),((OmniDeque.OfByte)col).removeLastLong());
    }
    @Override public void verifyDequeGetFirst(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolongbyte(expectedVal),((OmniDeque.OfByte)col).getFirstLong());
    }
    @Override public void verifyDequeGetLast(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolongbyte(expectedVal),((OmniDeque.OfByte)col).getLastLong());
    }
  }
  ,
  INT{
    @Override public void verifyItrPrevious(OmniIterator.OfByte itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTointbyte(valToConvert),((OmniListIterator.OfByte)itr).previousInt());
    }
    @Override public void verifyItrNext(OmniIterator.OfByte itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTointbyte(valToConvert),itr.nextInt());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfByte col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTointbyte(valToConvert),((OmniList.OfByte)col).removeIntAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfByte col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTointbyte(valToConvert),((OmniList.OfByte)col).getInt(index));
    }
    @Override public void verifyToArray(OmniCollection.OfByte col,int expectedSize){
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
    @Override public void verifyPoll(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Integer.MIN_VALUE,((PeekAndPollIfc.IntOutput<?>)col).pollInt());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTointbyte(expectedVal),((PeekAndPollIfc.IntOutput<?>)col).pollInt());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Integer.MIN_VALUE,((PeekAndPollIfc.IntOutput<?>)col).peekInt());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTointbyte(expectedVal),((PeekAndPollIfc.IntOutput<?>)col).peekInt());
      }
    }
    @Override public void verifyDequePollLast(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Integer.MIN_VALUE,((OmniDeque.OfByte)col).pollLastInt());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTointbyte(expectedVal),((OmniDeque.OfByte)col).pollLastInt());
      }
    }
    @Override public void verifyDequePollFirst(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Integer.MIN_VALUE,((OmniDeque.OfByte)col).pollFirstInt());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTointbyte(expectedVal),((OmniDeque.OfByte)col).pollFirstInt());
      }
    }
    @Override public void verifyDequePeekLast(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Integer.MIN_VALUE,((OmniDeque.OfByte)col).peekLastInt());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTointbyte(expectedVal),((OmniDeque.OfByte)col).peekLastInt());
      }
    }
    @Override public void verifyDequePeekFirst(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Integer.MIN_VALUE,((OmniDeque.OfByte)col).peekFirstInt());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTointbyte(expectedVal),((OmniDeque.OfByte)col).peekFirstInt());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTointbyte(expectedVal),((OmniStack.OfByte)col).popInt());
    }
    @Override public void verifyQueueElement(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTointbyte(expectedVal),((OmniQueue.OfByte)col).intElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTointbyte(expectedVal),((OmniQueue.OfByte)col).removeInt());
    }
    @Override public void verifyDequeRemoveFirst(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTointbyte(expectedVal),((OmniDeque.OfByte)col).removeFirstInt());
    }
    @Override public void verifyDequeRemoveLast(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTointbyte(expectedVal),((OmniDeque.OfByte)col).removeLastInt());
    }
    @Override public void verifyDequeGetFirst(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTointbyte(expectedVal),((OmniDeque.OfByte)col).getFirstInt());
    }
    @Override public void verifyDequeGetLast(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTointbyte(expectedVal),((OmniDeque.OfByte)col).getLastInt());
    }
  }
  ,
  SHORT{
    @Override public void verifyItrPrevious(OmniIterator.OfByte itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToshortbyte(valToConvert),((OmniListIterator.OfByte)itr).previousShort());
    }
    @Override public void verifyItrNext(OmniIterator.OfByte itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToshortbyte(valToConvert),itr.nextShort());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfByte col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToshortbyte(valToConvert),((OmniList.OfByte)col).removeShortAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfByte col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToshortbyte(valToConvert),((OmniList.OfByte)col).getShort(index));
    }
    @Override public void verifyToArray(OmniCollection.OfByte col,int expectedSize){
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
    @Override public void verifyPoll(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Short.MIN_VALUE,((PeekAndPollIfc.ShortOutput<?>)col).pollShort());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToshortbyte(expectedVal),((PeekAndPollIfc.ShortOutput<?>)col).pollShort());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Short.MIN_VALUE,((PeekAndPollIfc.ShortOutput<?>)col).peekShort());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToshortbyte(expectedVal),((PeekAndPollIfc.ShortOutput<?>)col).peekShort());
      }
    }
    @Override public void verifyDequePollLast(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Short.MIN_VALUE,((OmniDeque.OfByte)col).pollLastShort());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToshortbyte(expectedVal),((OmniDeque.OfByte)col).pollLastShort());
      }
    }
    @Override public void verifyDequePollFirst(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Short.MIN_VALUE,((OmniDeque.OfByte)col).pollFirstShort());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToshortbyte(expectedVal),((OmniDeque.OfByte)col).pollFirstShort());
      }
    }
    @Override public void verifyDequePeekLast(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Short.MIN_VALUE,((OmniDeque.OfByte)col).peekLastShort());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToshortbyte(expectedVal),((OmniDeque.OfByte)col).peekLastShort());
      }
    }
    @Override public void verifyDequePeekFirst(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Short.MIN_VALUE,((OmniDeque.OfByte)col).peekFirstShort());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToshortbyte(expectedVal),((OmniDeque.OfByte)col).peekFirstShort());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToshortbyte(expectedVal),((OmniStack.OfByte)col).popShort());
    }
    @Override public void verifyQueueElement(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToshortbyte(expectedVal),((OmniQueue.OfByte)col).shortElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToshortbyte(expectedVal),((OmniQueue.OfByte)col).removeShort());
    }
    @Override public void verifyDequeRemoveFirst(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToshortbyte(expectedVal),((OmniDeque.OfByte)col).removeFirstShort());
    }
    @Override public void verifyDequeRemoveLast(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToshortbyte(expectedVal),((OmniDeque.OfByte)col).removeLastShort());
    }
    @Override public void verifyDequeGetFirst(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToshortbyte(expectedVal),((OmniDeque.OfByte)col).getFirstShort());
    }
    @Override public void verifyDequeGetLast(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToshortbyte(expectedVal),((OmniDeque.OfByte)col).getLastShort());
    }
  }
  ;
  public abstract void verifyDequePeekLast(OmniCollection.OfByte col,int expectedSize,int expectedVal);
  public abstract void verifyDequePollLast(OmniCollection.OfByte col,int expectedSize,int expectedVal);
  public abstract void verifyDequePeekFirst(OmniCollection.OfByte col,int expectedSize,int expectedVal);
  public abstract void verifyDequePollFirst(OmniCollection.OfByte col,int expectedSize,int expectedVal);
  public abstract void verifyDequeRemoveLast(OmniCollection.OfByte col,int expectedVal);
  public abstract void verifyDequeRemoveFirst(OmniCollection.OfByte col,int expectedVal);
  public abstract void verifyDequeGetLast(OmniCollection.OfByte col,int expectedVal);
  public abstract void verifyDequeGetFirst(OmniCollection.OfByte col,int expectedVal);
  public abstract void verifyItrPrevious(OmniIterator.OfByte itr,int valToConvert);
  public abstract void verifyItrNext(OmniIterator.OfByte itr,int valToConvert);
  public abstract void verifyListRemoveAt(OmniCollection.OfByte col,int index,int valToConvert);
  public abstract void verifyListGet(OmniCollection.OfByte col,int index,int valToConvert);
  public abstract void verifyToArray(OmniCollection.OfByte col,int expectedSize);
  public abstract void verifyPoll(OmniCollection.OfByte col,int expectedSize,int expectedVal);
  public abstract void verifyStackPop(OmniCollection.OfByte col,int expectedVal);
  public abstract void verifyQueueRemove(OmniCollection.OfByte col,int expectedVal);
  public abstract void verifyQueueElement(OmniCollection.OfByte col,int expectedVal);
  public abstract void verifyPeek(OmniCollection.OfByte col,int expectedSize,int expectedVal);
}
