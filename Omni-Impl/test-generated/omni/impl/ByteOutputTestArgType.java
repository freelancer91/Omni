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
    @Override public void verifyStackPop(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(expectedVal),((OmniStack.OfByte)col).popByte());
    }
    @Override public void verifyQueueElement(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(expectedVal),((OmniQueue.OfByte)col).byteElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(expectedVal),((OmniQueue.OfByte)col).removeByte());
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
    @Override public void verifyStackPop(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToByte(expectedVal),((OmniStack.OfByte)col).pop());
    }
    @Override public void verifyQueueElement(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToByte(expectedVal),((OmniQueue.OfByte)col).element());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToByte(expectedVal),((OmniQueue.OfByte)col).remove());
    }
  }
  ,
  DOUBLE{
    @Override public void verifyItrPrevious(OmniIterator.OfByte itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),((OmniListIterator.OfByte)itr).previousDouble());
    }
    @Override public void verifyItrNext(OmniIterator.OfByte itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),itr.nextDouble());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfByte col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),((OmniList.OfByte)col).removeDoubleAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfByte col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),((OmniList.OfByte)col).getDouble(index));
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
        Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((PeekAndPollIfc.DoubleOutput<?>)col).pollDouble());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((PeekAndPollIfc.DoubleOutput<?>)col).peekDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((PeekAndPollIfc.DoubleOutput<?>)col).peekDouble());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniStack.OfByte)col).popDouble());
    }
    @Override public void verifyQueueElement(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniQueue.OfByte)col).doubleElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniQueue.OfByte)col).removeDouble());
    }
  }
  ,
  FLOAT{
    @Override public void verifyItrPrevious(OmniIterator.OfByte itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(valToConvert),((OmniListIterator.OfByte)itr).previousFloat());
    }
    @Override public void verifyItrNext(OmniIterator.OfByte itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(valToConvert),itr.nextFloat());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfByte col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(valToConvert),((OmniList.OfByte)col).removeFloatAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfByte col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(valToConvert),((OmniList.OfByte)col).getFloat(index));
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
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((PeekAndPollIfc.FloatOutput<?>)col).pollFloat());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((PeekAndPollIfc.FloatOutput<?>)col).peekFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((PeekAndPollIfc.FloatOutput<?>)col).peekFloat());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniStack.OfByte)col).popFloat());
    }
    @Override public void verifyQueueElement(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniQueue.OfByte)col).floatElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniQueue.OfByte)col).removeFloat());
    }
  }
  ,
  LONG{
    @Override public void verifyItrPrevious(OmniIterator.OfByte itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(valToConvert),((OmniListIterator.OfByte)itr).previousLong());
    }
    @Override public void verifyItrNext(OmniIterator.OfByte itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(valToConvert),itr.nextLong());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfByte col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(valToConvert),((OmniList.OfByte)col).removeLongAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfByte col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(valToConvert),((OmniList.OfByte)col).getLong(index));
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
        Assertions.assertEquals(TypeConversionUtil.convertTolong(expectedVal),((PeekAndPollIfc.LongOutput<?>)col).pollLong());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Long.MIN_VALUE,((PeekAndPollIfc.LongOutput<?>)col).peekLong());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTolong(expectedVal),((PeekAndPollIfc.LongOutput<?>)col).peekLong());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(expectedVal),((OmniStack.OfByte)col).popLong());
    }
    @Override public void verifyQueueElement(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(expectedVal),((OmniQueue.OfByte)col).longElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(expectedVal),((OmniQueue.OfByte)col).removeLong());
    }
  }
  ,
  INT{
    @Override public void verifyItrPrevious(OmniIterator.OfByte itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToint(valToConvert),((OmniListIterator.OfByte)itr).previousInt());
    }
    @Override public void verifyItrNext(OmniIterator.OfByte itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToint(valToConvert),itr.nextInt());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfByte col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToint(valToConvert),((OmniList.OfByte)col).removeIntAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfByte col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToint(valToConvert),((OmniList.OfByte)col).getInt(index));
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
        Assertions.assertEquals(TypeConversionUtil.convertToint(expectedVal),((PeekAndPollIfc.IntOutput<?>)col).pollInt());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Integer.MIN_VALUE,((PeekAndPollIfc.IntOutput<?>)col).peekInt());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToint(expectedVal),((PeekAndPollIfc.IntOutput<?>)col).peekInt());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToint(expectedVal),((OmniStack.OfByte)col).popInt());
    }
    @Override public void verifyQueueElement(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToint(expectedVal),((OmniQueue.OfByte)col).intElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToint(expectedVal),((OmniQueue.OfByte)col).removeInt());
    }
  }
  ,
  SHORT{
    @Override public void verifyItrPrevious(OmniIterator.OfByte itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToshort(valToConvert),((OmniListIterator.OfByte)itr).previousShort());
    }
    @Override public void verifyItrNext(OmniIterator.OfByte itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToshort(valToConvert),itr.nextShort());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfByte col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToshort(valToConvert),((OmniList.OfByte)col).removeShortAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfByte col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToshort(valToConvert),((OmniList.OfByte)col).getShort(index));
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
        Assertions.assertEquals(TypeConversionUtil.convertToshort(expectedVal),((PeekAndPollIfc.ShortOutput<?>)col).pollShort());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfByte col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Short.MIN_VALUE,((PeekAndPollIfc.ShortOutput<?>)col).peekShort());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToshort(expectedVal),((PeekAndPollIfc.ShortOutput<?>)col).peekShort());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToshort(expectedVal),((OmniStack.OfByte)col).popShort());
    }
    @Override public void verifyQueueElement(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToshort(expectedVal),((OmniQueue.OfByte)col).shortElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfByte col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToshort(expectedVal),((OmniQueue.OfByte)col).removeShort());
    }
  }
  ;
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
  //TODO other method tests
}
