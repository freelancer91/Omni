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
    @Override public void verifyStackPop(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(expectedVal),((OmniStack.OfChar)col).popChar());
    }
    @Override public void verifyQueueElement(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(expectedVal),((OmniQueue.OfChar)col).charElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(expectedVal),((OmniQueue.OfChar)col).removeChar());
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
    @Override public void verifyStackPop(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToCharacter(expectedVal),((OmniStack.OfChar)col).pop());
    }
    @Override public void verifyQueueElement(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToCharacter(expectedVal),((OmniQueue.OfChar)col).element());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToCharacter(expectedVal),((OmniQueue.OfChar)col).remove());
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
    @Override public void verifyStackPop(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodoublechar(expectedVal),((OmniStack.OfChar)col).popDouble());
    }
    @Override public void verifyQueueElement(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodoublechar(expectedVal),((OmniQueue.OfChar)col).doubleElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodoublechar(expectedVal),((OmniQueue.OfChar)col).removeDouble());
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
    @Override public void verifyStackPop(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloatchar(expectedVal),((OmniStack.OfChar)col).popFloat());
    }
    @Override public void verifyQueueElement(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloatchar(expectedVal),((OmniQueue.OfChar)col).floatElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloatchar(expectedVal),((OmniQueue.OfChar)col).removeFloat());
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
    @Override public void verifyStackPop(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolongchar(expectedVal),((OmniStack.OfChar)col).popLong());
    }
    @Override public void verifyQueueElement(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolongchar(expectedVal),((OmniQueue.OfChar)col).longElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolongchar(expectedVal),((OmniQueue.OfChar)col).removeLong());
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
    @Override public void verifyStackPop(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTointchar(expectedVal),((OmniStack.OfChar)col).popInt());
    }
    @Override public void verifyQueueElement(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTointchar(expectedVal),((OmniQueue.OfChar)col).intElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTointchar(expectedVal),((OmniQueue.OfChar)col).removeInt());
    }
  }
  ;
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
  //TODO other method tests
}
