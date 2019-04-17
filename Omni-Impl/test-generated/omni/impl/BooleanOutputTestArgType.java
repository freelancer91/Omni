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
        Assertions.assertEquals(false,((PeekAndPollIfc.BooleanInput)col).pollBoolean());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToboolean(expectedVal),((PeekAndPollIfc.BooleanInput)col).pollBoolean());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(false,((PeekAndPollIfc.BooleanInput)col).peekBoolean());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToboolean(expectedVal),((PeekAndPollIfc.BooleanInput)col).peekBoolean());
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
    @Override public void verifyStackPop(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToBoolean(expectedVal),((OmniStack.OfBoolean)col).pop());
    }
    @Override public void verifyQueueElement(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToBoolean(expectedVal),((OmniQueue.OfBoolean)col).element());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfBoolean col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToBoolean(expectedVal),((OmniQueue.OfBoolean)col).remove());
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
        Assertions.assertEquals(Double.NaN,((PeekAndPollIfc.DoubleInput)col).pollDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodoubleboolean(expectedVal),((PeekAndPollIfc.DoubleInput)col).pollDouble());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((PeekAndPollIfc.DoubleInput)col).peekDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodoubleboolean(expectedVal),((PeekAndPollIfc.DoubleInput)col).peekDouble());
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
        Assertions.assertEquals(Float.NaN,((PeekAndPollIfc.FloatInput)col).pollFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(expectedVal),((PeekAndPollIfc.FloatInput)col).pollFloat());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((PeekAndPollIfc.FloatInput)col).peekFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(expectedVal),((PeekAndPollIfc.FloatInput)col).peekFloat());
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
        Assertions.assertEquals(Long.MIN_VALUE,((PeekAndPollIfc.LongInput)col).pollLong());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTolongboolean(expectedVal),((PeekAndPollIfc.LongInput)col).pollLong());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Long.MIN_VALUE,((PeekAndPollIfc.LongInput)col).peekLong());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTolongboolean(expectedVal),((PeekAndPollIfc.LongInput)col).peekLong());
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
        Assertions.assertEquals(Integer.MIN_VALUE,((PeekAndPollIfc.IntInput)col).pollInt());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTointboolean(expectedVal),((PeekAndPollIfc.IntInput)col).pollInt());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Integer.MIN_VALUE,((PeekAndPollIfc.IntInput)col).peekInt());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTointboolean(expectedVal),((PeekAndPollIfc.IntInput)col).peekInt());
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
        Assertions.assertEquals(Short.MIN_VALUE,((PeekAndPollIfc.ShortInput)col).pollShort());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(expectedVal),((PeekAndPollIfc.ShortInput)col).pollShort());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Short.MIN_VALUE,((PeekAndPollIfc.ShortInput)col).peekShort());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(expectedVal),((PeekAndPollIfc.ShortInput)col).peekShort());
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
        Assertions.assertEquals(Byte.MIN_VALUE,((PeekAndPollIfc.ByteInput)col).pollByte());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(expectedVal),((PeekAndPollIfc.ByteInput)col).pollByte());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Byte.MIN_VALUE,((PeekAndPollIfc.ByteInput)col).peekByte());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(expectedVal),((PeekAndPollIfc.ByteInput)col).peekByte());
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
        Assertions.assertEquals(Character.MIN_VALUE,((PeekAndPollIfc.CharInput)col).pollChar());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(expectedVal),((PeekAndPollIfc.CharInput)col).pollChar());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfBoolean col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Character.MIN_VALUE,((PeekAndPollIfc.CharInput)col).peekChar());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(expectedVal),((PeekAndPollIfc.CharInput)col).peekChar());
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
  }
  ;
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
  //TODO other method tests
}
