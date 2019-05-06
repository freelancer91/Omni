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
public enum CharOutputTestArgType{
  ARRAY_TYPE{
    @Override public void verifyItrPrevious(OmniIterator.OfChar itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(valToConvert),((OmniListIterator.OfChar)itr).previousChar());
    }
    @Override public void verifyItrNext(OmniIterator.OfChar itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(valToConvert),itr.nextChar());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfChar col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(valToConvert),((OmniList.OfChar)col).removeCharAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfChar col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(valToConvert),((OmniList.OfChar)col).getChar(index));
    }
    @Override public void verifyToArray(OmniCollection.OfChar col,int expectedSize){
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
    @Override public void verifyPoll(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Character.MIN_VALUE,((PeekAndPollIfc.CharOutput<?>)col).pollChar());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTochar(expectedVal),((PeekAndPollIfc.CharOutput<?>)col).pollChar());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Character.MIN_VALUE,((PeekAndPollIfc.CharOutput<?>)col).peekChar());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTochar(expectedVal),((PeekAndPollIfc.CharOutput<?>)col).peekChar());
      }
    }
    @Override public void verifyDequePollLast(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Character.MIN_VALUE,((OmniDeque.OfChar)col).pollLastChar());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTochar(expectedVal),((OmniDeque.OfChar)col).pollLastChar());
      }
    }
    @Override public void verifyDequePollFirst(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Character.MIN_VALUE,((OmniDeque.OfChar)col).pollFirstChar());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTochar(expectedVal),((OmniDeque.OfChar)col).pollFirstChar());
      }
    }
    @Override public void verifyDequePeekLast(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Character.MIN_VALUE,((OmniDeque.OfChar)col).peekLastChar());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTochar(expectedVal),((OmniDeque.OfChar)col).peekLastChar());
      }
    }
    @Override public void verifyDequePeekFirst(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Character.MIN_VALUE,((OmniDeque.OfChar)col).peekFirstChar());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTochar(expectedVal),((OmniDeque.OfChar)col).peekFirstChar());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(expectedVal),((OmniStack.OfChar)col).popChar());
    }
    @Override public void verifyQueueElement(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(expectedVal),((OmniQueue.OfChar)col).charElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(expectedVal),((OmniQueue.OfChar)col).removeChar());
    }
    @Override public void verifyDequeRemoveFirst(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(expectedVal),((OmniDeque.OfChar)col).removeFirstChar());
    }
    @Override public void verifyDequeRemoveLast(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(expectedVal),((OmniDeque.OfChar)col).removeLastChar());
    }
    @Override public void verifyDequeGetFirst(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(expectedVal),((OmniDeque.OfChar)col).getFirstChar());
    }
    @Override public void verifyDequeGetLast(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(expectedVal),((OmniDeque.OfChar)col).getLastChar());
    }
  }
  ,
  BOXED_TYPE{
    @Override public void verifyItrPrevious(OmniIterator.OfChar itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToCharacter(valToConvert),((OmniListIterator.OfChar)itr).previous());
    }
    @Override public void verifyItrNext(OmniIterator.OfChar itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToCharacter(valToConvert),itr.next());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfChar col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToCharacter(valToConvert),((OmniList.OfChar)col).remove(index));
    }
    @Override public void verifyListGet(OmniCollection.OfChar col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToCharacter(valToConvert),((OmniList.OfChar)col).get(index));
    }
    @Override public void verifyToArray(OmniCollection.OfChar col,int expectedSize){
      var arr=col.toArray();
      if(expectedSize==0){
        Assertions.assertSame(OmniArray.OfChar.DEFAULT_BOXED_ARR,arr);
      }else{
        Assertions.assertEquals(expectedSize,arr.length);
        var itr=col.iterator();
        for(int i=0;i<expectedSize;++i){
          Assertions.assertEquals(itr.next(),arr[i]);
        }
      }
    }
    @Override public void verifyPoll(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertNull(((PeekAndPollIfc<?>)col).poll());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToCharacter(expectedVal),((PeekAndPollIfc<?>)col).poll());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertNull(((PeekAndPollIfc<?>)col).peek());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToCharacter(expectedVal),((PeekAndPollIfc<?>)col).peek());
      }
    }
    @Override public void verifyDequePollLast(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertNull(((OmniDeque.OfChar)col).pollLast());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToCharacter(expectedVal),((OmniDeque.OfChar)col).pollLast());
      }
    }
    @Override public void verifyDequePollFirst(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertNull(((OmniDeque.OfChar)col).pollFirst());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToCharacter(expectedVal),((OmniDeque.OfChar)col).pollFirst());
      }
    }
    @Override public void verifyDequePeekLast(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertNull(((OmniDeque.OfChar)col).peekLast());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToCharacter(expectedVal),((OmniDeque.OfChar)col).peekLast());
      }
    }
    @Override public void verifyDequePeekFirst(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertNull(((OmniDeque.OfChar)col).peekFirst());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToCharacter(expectedVal),((OmniDeque.OfChar)col).peekFirst());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToCharacter(expectedVal),((OmniStack.OfChar)col).pop());
    }
    @Override public void verifyQueueElement(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToCharacter(expectedVal),((OmniQueue.OfChar)col).element());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToCharacter(expectedVal),((OmniQueue.OfChar)col).remove());
    }
    @Override public void verifyDequeRemoveFirst(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToCharacter(expectedVal),((OmniDeque.OfChar)col).removeFirst());
    }
    @Override public void verifyDequeRemoveLast(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToCharacter(expectedVal),((OmniDeque.OfChar)col).removeLast());
    }
    @Override public void verifyDequeGetFirst(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToCharacter(expectedVal),((OmniDeque.OfChar)col).getFirst());
    }
    @Override public void verifyDequeGetLast(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToCharacter(expectedVal),((OmniDeque.OfChar)col).getLast());
    }
  }
  ,
  DOUBLE{
    @Override public void verifyItrPrevious(OmniIterator.OfChar itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodoublechar(valToConvert),((OmniListIterator.OfChar)itr).previousDouble());
    }
    @Override public void verifyItrNext(OmniIterator.OfChar itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodoublechar(valToConvert),itr.nextDouble());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfChar col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodoublechar(valToConvert),((OmniList.OfChar)col).removeDoubleAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfChar col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodoublechar(valToConvert),((OmniList.OfChar)col).getDouble(index));
    }
    @Override public void verifyToArray(OmniCollection.OfChar col,int expectedSize){
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
    @Override public void verifyPoll(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((PeekAndPollIfc.DoubleOutput<?>)col).pollDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodoublechar(expectedVal),((PeekAndPollIfc.DoubleOutput<?>)col).pollDouble());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((PeekAndPollIfc.DoubleOutput<?>)col).peekDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodoublechar(expectedVal),((PeekAndPollIfc.DoubleOutput<?>)col).peekDouble());
      }
    }
    @Override public void verifyDequePollLast(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((OmniDeque.OfChar)col).pollLastDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodoublechar(expectedVal),((OmniDeque.OfChar)col).pollLastDouble());
      }
    }
    @Override public void verifyDequePollFirst(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((OmniDeque.OfChar)col).pollFirstDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodoublechar(expectedVal),((OmniDeque.OfChar)col).pollFirstDouble());
      }
    }
    @Override public void verifyDequePeekLast(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((OmniDeque.OfChar)col).peekLastDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodoublechar(expectedVal),((OmniDeque.OfChar)col).peekLastDouble());
      }
    }
    @Override public void verifyDequePeekFirst(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((OmniDeque.OfChar)col).peekFirstDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodoublechar(expectedVal),((OmniDeque.OfChar)col).peekFirstDouble());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodoublechar(expectedVal),((OmniStack.OfChar)col).popDouble());
    }
    @Override public void verifyQueueElement(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodoublechar(expectedVal),((OmniQueue.OfChar)col).doubleElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodoublechar(expectedVal),((OmniQueue.OfChar)col).removeDouble());
    }
    @Override public void verifyDequeRemoveFirst(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodoublechar(expectedVal),((OmniDeque.OfChar)col).removeFirstDouble());
    }
    @Override public void verifyDequeRemoveLast(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodoublechar(expectedVal),((OmniDeque.OfChar)col).removeLastDouble());
    }
    @Override public void verifyDequeGetFirst(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodoublechar(expectedVal),((OmniDeque.OfChar)col).getFirstDouble());
    }
    @Override public void verifyDequeGetLast(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodoublechar(expectedVal),((OmniDeque.OfChar)col).getLastDouble());
    }
  }
  ,
  FLOAT{
    @Override public void verifyItrPrevious(OmniIterator.OfChar itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloatchar(valToConvert),((OmniListIterator.OfChar)itr).previousFloat());
    }
    @Override public void verifyItrNext(OmniIterator.OfChar itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloatchar(valToConvert),itr.nextFloat());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfChar col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloatchar(valToConvert),((OmniList.OfChar)col).removeFloatAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfChar col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloatchar(valToConvert),((OmniList.OfChar)col).getFloat(index));
    }
    @Override public void verifyToArray(OmniCollection.OfChar col,int expectedSize){
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
    @Override public void verifyPoll(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((PeekAndPollIfc.FloatOutput<?>)col).pollFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloatchar(expectedVal),((PeekAndPollIfc.FloatOutput<?>)col).pollFloat());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((PeekAndPollIfc.FloatOutput<?>)col).peekFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloatchar(expectedVal),((PeekAndPollIfc.FloatOutput<?>)col).peekFloat());
      }
    }
    @Override public void verifyDequePollLast(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((OmniDeque.OfChar)col).pollLastFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloatchar(expectedVal),((OmniDeque.OfChar)col).pollLastFloat());
      }
    }
    @Override public void verifyDequePollFirst(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((OmniDeque.OfChar)col).pollFirstFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloatchar(expectedVal),((OmniDeque.OfChar)col).pollFirstFloat());
      }
    }
    @Override public void verifyDequePeekLast(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((OmniDeque.OfChar)col).peekLastFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloatchar(expectedVal),((OmniDeque.OfChar)col).peekLastFloat());
      }
    }
    @Override public void verifyDequePeekFirst(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((OmniDeque.OfChar)col).peekFirstFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloatchar(expectedVal),((OmniDeque.OfChar)col).peekFirstFloat());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloatchar(expectedVal),((OmniStack.OfChar)col).popFloat());
    }
    @Override public void verifyQueueElement(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloatchar(expectedVal),((OmniQueue.OfChar)col).floatElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloatchar(expectedVal),((OmniQueue.OfChar)col).removeFloat());
    }
    @Override public void verifyDequeRemoveFirst(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloatchar(expectedVal),((OmniDeque.OfChar)col).removeFirstFloat());
    }
    @Override public void verifyDequeRemoveLast(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloatchar(expectedVal),((OmniDeque.OfChar)col).removeLastFloat());
    }
    @Override public void verifyDequeGetFirst(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloatchar(expectedVal),((OmniDeque.OfChar)col).getFirstFloat());
    }
    @Override public void verifyDequeGetLast(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloatchar(expectedVal),((OmniDeque.OfChar)col).getLastFloat());
    }
  }
  ,
  LONG{
    @Override public void verifyItrPrevious(OmniIterator.OfChar itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolongchar(valToConvert),((OmniListIterator.OfChar)itr).previousLong());
    }
    @Override public void verifyItrNext(OmniIterator.OfChar itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolongchar(valToConvert),itr.nextLong());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfChar col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolongchar(valToConvert),((OmniList.OfChar)col).removeLongAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfChar col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolongchar(valToConvert),((OmniList.OfChar)col).getLong(index));
    }
    @Override public void verifyToArray(OmniCollection.OfChar col,int expectedSize){
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
    @Override public void verifyPoll(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Long.MIN_VALUE,((PeekAndPollIfc.LongOutput<?>)col).pollLong());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTolongchar(expectedVal),((PeekAndPollIfc.LongOutput<?>)col).pollLong());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Long.MIN_VALUE,((PeekAndPollIfc.LongOutput<?>)col).peekLong());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTolongchar(expectedVal),((PeekAndPollIfc.LongOutput<?>)col).peekLong());
      }
    }
    @Override public void verifyDequePollLast(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Long.MIN_VALUE,((OmniDeque.OfChar)col).pollLastLong());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTolongchar(expectedVal),((OmniDeque.OfChar)col).pollLastLong());
      }
    }
    @Override public void verifyDequePollFirst(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Long.MIN_VALUE,((OmniDeque.OfChar)col).pollFirstLong());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTolongchar(expectedVal),((OmniDeque.OfChar)col).pollFirstLong());
      }
    }
    @Override public void verifyDequePeekLast(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Long.MIN_VALUE,((OmniDeque.OfChar)col).peekLastLong());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTolongchar(expectedVal),((OmniDeque.OfChar)col).peekLastLong());
      }
    }
    @Override public void verifyDequePeekFirst(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Long.MIN_VALUE,((OmniDeque.OfChar)col).peekFirstLong());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTolongchar(expectedVal),((OmniDeque.OfChar)col).peekFirstLong());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolongchar(expectedVal),((OmniStack.OfChar)col).popLong());
    }
    @Override public void verifyQueueElement(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolongchar(expectedVal),((OmniQueue.OfChar)col).longElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolongchar(expectedVal),((OmniQueue.OfChar)col).removeLong());
    }
    @Override public void verifyDequeRemoveFirst(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolongchar(expectedVal),((OmniDeque.OfChar)col).removeFirstLong());
    }
    @Override public void verifyDequeRemoveLast(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolongchar(expectedVal),((OmniDeque.OfChar)col).removeLastLong());
    }
    @Override public void verifyDequeGetFirst(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolongchar(expectedVal),((OmniDeque.OfChar)col).getFirstLong());
    }
    @Override public void verifyDequeGetLast(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolongchar(expectedVal),((OmniDeque.OfChar)col).getLastLong());
    }
  }
  ,
  INT{
    @Override public void verifyItrPrevious(OmniIterator.OfChar itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTointchar(valToConvert),((OmniListIterator.OfChar)itr).previousInt());
    }
    @Override public void verifyItrNext(OmniIterator.OfChar itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTointchar(valToConvert),itr.nextInt());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfChar col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTointchar(valToConvert),((OmniList.OfChar)col).removeIntAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfChar col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTointchar(valToConvert),((OmniList.OfChar)col).getInt(index));
    }
    @Override public void verifyToArray(OmniCollection.OfChar col,int expectedSize){
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
    @Override public void verifyPoll(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Integer.MIN_VALUE,((PeekAndPollIfc.IntOutput<?>)col).pollInt());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTointchar(expectedVal),((PeekAndPollIfc.IntOutput<?>)col).pollInt());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Integer.MIN_VALUE,((PeekAndPollIfc.IntOutput<?>)col).peekInt());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTointchar(expectedVal),((PeekAndPollIfc.IntOutput<?>)col).peekInt());
      }
    }
    @Override public void verifyDequePollLast(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Integer.MIN_VALUE,((OmniDeque.OfChar)col).pollLastInt());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTointchar(expectedVal),((OmniDeque.OfChar)col).pollLastInt());
      }
    }
    @Override public void verifyDequePollFirst(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Integer.MIN_VALUE,((OmniDeque.OfChar)col).pollFirstInt());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTointchar(expectedVal),((OmniDeque.OfChar)col).pollFirstInt());
      }
    }
    @Override public void verifyDequePeekLast(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Integer.MIN_VALUE,((OmniDeque.OfChar)col).peekLastInt());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTointchar(expectedVal),((OmniDeque.OfChar)col).peekLastInt());
      }
    }
    @Override public void verifyDequePeekFirst(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Integer.MIN_VALUE,((OmniDeque.OfChar)col).peekFirstInt());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTointchar(expectedVal),((OmniDeque.OfChar)col).peekFirstInt());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTointchar(expectedVal),((OmniStack.OfChar)col).popInt());
    }
    @Override public void verifyQueueElement(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTointchar(expectedVal),((OmniQueue.OfChar)col).intElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTointchar(expectedVal),((OmniQueue.OfChar)col).removeInt());
    }
    @Override public void verifyDequeRemoveFirst(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTointchar(expectedVal),((OmniDeque.OfChar)col).removeFirstInt());
    }
    @Override public void verifyDequeRemoveLast(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTointchar(expectedVal),((OmniDeque.OfChar)col).removeLastInt());
    }
    @Override public void verifyDequeGetFirst(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTointchar(expectedVal),((OmniDeque.OfChar)col).getFirstInt());
    }
    @Override public void verifyDequeGetLast(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTointchar(expectedVal),((OmniDeque.OfChar)col).getLastInt());
    }
  }
  ;
  public abstract void verifyDequePeekLast(OmniCollection.OfChar col,int expectedSize,int expectedVal);
  public abstract void verifyDequePollLast(OmniCollection.OfChar col,int expectedSize,int expectedVal);
  public abstract void verifyDequePeekFirst(OmniCollection.OfChar col,int expectedSize,int expectedVal);
  public abstract void verifyDequePollFirst(OmniCollection.OfChar col,int expectedSize,int expectedVal);
  public abstract void verifyDequeRemoveLast(OmniCollection.OfChar col,int expectedVal);
  public abstract void verifyDequeRemoveFirst(OmniCollection.OfChar col,int expectedVal);
  public abstract void verifyDequeGetLast(OmniCollection.OfChar col,int expectedVal);
  public abstract void verifyDequeGetFirst(OmniCollection.OfChar col,int expectedVal);
  public abstract void verifyItrPrevious(OmniIterator.OfChar itr,int valToConvert);
  public abstract void verifyItrNext(OmniIterator.OfChar itr,int valToConvert);
  public abstract void verifyListRemoveAt(OmniCollection.OfChar col,int index,int valToConvert);
  public abstract void verifyListGet(OmniCollection.OfChar col,int index,int valToConvert);
  public abstract void verifyToArray(OmniCollection.OfChar col,int expectedSize);
  public abstract void verifyPoll(OmniCollection.OfChar col,int expectedSize,int expectedVal);
  public abstract void verifyStackPop(OmniCollection.OfChar col,int expectedVal);
  public abstract void verifyQueueRemove(OmniCollection.OfChar col,int expectedVal);
  public abstract void verifyQueueElement(OmniCollection.OfChar col,int expectedVal);
  public abstract void verifyPeek(OmniCollection.OfChar col,int expectedSize,int expectedVal);
}
