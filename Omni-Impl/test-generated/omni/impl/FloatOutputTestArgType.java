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
public enum FloatOutputTestArgType{
  ARRAY_TYPE{
    @Override public void verifyItrPrevious(OmniIterator.OfFloat itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(valToConvert),((OmniListIterator.OfFloat)itr).previousFloat());
    }
    @Override public void verifyItrNext(OmniIterator.OfFloat itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(valToConvert),itr.nextFloat());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfFloat col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(valToConvert),((OmniList.OfFloat)col).removeFloatAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfFloat col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(valToConvert),((OmniList.OfFloat)col).getFloat(index));
    }
    @Override public void verifyToArray(OmniCollection.OfFloat col,int expectedSize){
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
    @Override public void verifyPoll(OmniCollection.OfFloat col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((PeekAndPollIfc.FloatOutput<?>)col).pollFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((PeekAndPollIfc.FloatOutput<?>)col).pollFloat());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfFloat col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((PeekAndPollIfc.FloatOutput<?>)col).peekFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((PeekAndPollIfc.FloatOutput<?>)col).peekFloat());
      }
    }
    @Override public void verifyDequePollLast(OmniCollection.OfFloat col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((OmniDeque.OfFloat)col).pollLastFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniDeque.OfFloat)col).pollLastFloat());
      }
    }
    @Override public void verifyDequePollFirst(OmniCollection.OfFloat col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((OmniDeque.OfFloat)col).pollFirstFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniDeque.OfFloat)col).pollFirstFloat());
      }
    }
    @Override public void verifyDequePeekLast(OmniCollection.OfFloat col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((OmniDeque.OfFloat)col).peekLastFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniDeque.OfFloat)col).peekLastFloat());
      }
    }
    @Override public void verifyDequePeekFirst(OmniCollection.OfFloat col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((OmniDeque.OfFloat)col).peekFirstFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniDeque.OfFloat)col).peekFirstFloat());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfFloat col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniStack.OfFloat)col).popFloat());
    }
    @Override public void verifyQueueElement(OmniCollection.OfFloat col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniQueue.OfFloat)col).floatElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfFloat col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniQueue.OfFloat)col).removeFloat());
    }
    @Override public void verifyDequeRemoveFirst(OmniCollection.OfFloat col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniDeque.OfFloat)col).removeFirstFloat());
    }
    @Override public void verifyDequeRemoveLast(OmniCollection.OfFloat col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniDeque.OfFloat)col).removeLastFloat());
    }
    @Override public void verifyDequeGetFirst(OmniCollection.OfFloat col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniDeque.OfFloat)col).getFirstFloat());
    }
    @Override public void verifyDequeGetLast(OmniCollection.OfFloat col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniDeque.OfFloat)col).getLastFloat());
    }
  }
  ,
  BOXED_TYPE{
    @Override public void verifyItrPrevious(OmniIterator.OfFloat itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToFloat(valToConvert),((OmniListIterator.OfFloat)itr).previous());
    }
    @Override public void verifyItrNext(OmniIterator.OfFloat itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToFloat(valToConvert),itr.next());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfFloat col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToFloat(valToConvert),((OmniList.OfFloat)col).remove(index));
    }
    @Override public void verifyListGet(OmniCollection.OfFloat col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToFloat(valToConvert),((OmniList.OfFloat)col).get(index));
    }
    @Override public void verifyToArray(OmniCollection.OfFloat col,int expectedSize){
      var arr=col.toArray();
      if(expectedSize==0){
        Assertions.assertSame(OmniArray.OfFloat.DEFAULT_BOXED_ARR,arr);
      }else{
        Assertions.assertEquals(expectedSize,arr.length);
        var itr=col.iterator();
        for(int i=0;i<expectedSize;++i){
          Assertions.assertEquals(itr.next(),arr[i]);
        }
      }
    }
    @Override public void verifyPoll(OmniCollection.OfFloat col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertNull(((PeekAndPollIfc<?>)col).poll());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToFloat(expectedVal),((PeekAndPollIfc<?>)col).poll());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfFloat col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertNull(((PeekAndPollIfc<?>)col).peek());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToFloat(expectedVal),((PeekAndPollIfc<?>)col).peek());
      }
    }
    @Override public void verifyDequePollLast(OmniCollection.OfFloat col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertNull(((OmniDeque.OfFloat)col).pollLast());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToFloat(expectedVal),((OmniDeque.OfFloat)col).pollLast());
      }
    }
    @Override public void verifyDequePollFirst(OmniCollection.OfFloat col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertNull(((OmniDeque.OfFloat)col).pollFirst());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToFloat(expectedVal),((OmniDeque.OfFloat)col).pollFirst());
      }
    }
    @Override public void verifyDequePeekLast(OmniCollection.OfFloat col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertNull(((OmniDeque.OfFloat)col).peekLast());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToFloat(expectedVal),((OmniDeque.OfFloat)col).peekLast());
      }
    }
    @Override public void verifyDequePeekFirst(OmniCollection.OfFloat col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertNull(((OmniDeque.OfFloat)col).peekFirst());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToFloat(expectedVal),((OmniDeque.OfFloat)col).peekFirst());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfFloat col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToFloat(expectedVal),((OmniStack.OfFloat)col).pop());
    }
    @Override public void verifyQueueElement(OmniCollection.OfFloat col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToFloat(expectedVal),((OmniQueue.OfFloat)col).element());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfFloat col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToFloat(expectedVal),((OmniQueue.OfFloat)col).remove());
    }
    @Override public void verifyDequeRemoveFirst(OmniCollection.OfFloat col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToFloat(expectedVal),((OmniDeque.OfFloat)col).removeFirst());
    }
    @Override public void verifyDequeRemoveLast(OmniCollection.OfFloat col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToFloat(expectedVal),((OmniDeque.OfFloat)col).removeLast());
    }
    @Override public void verifyDequeGetFirst(OmniCollection.OfFloat col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToFloat(expectedVal),((OmniDeque.OfFloat)col).getFirst());
    }
    @Override public void verifyDequeGetLast(OmniCollection.OfFloat col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToFloat(expectedVal),((OmniDeque.OfFloat)col).getLast());
    }
  }
  ,
  DOUBLE{
    @Override public void verifyItrPrevious(OmniIterator.OfFloat itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),((OmniListIterator.OfFloat)itr).previousDouble());
    }
    @Override public void verifyItrNext(OmniIterator.OfFloat itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),itr.nextDouble());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfFloat col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),((OmniList.OfFloat)col).removeDoubleAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfFloat col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),((OmniList.OfFloat)col).getDouble(index));
    }
    @Override public void verifyToArray(OmniCollection.OfFloat col,int expectedSize){
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
    @Override public void verifyPoll(OmniCollection.OfFloat col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((PeekAndPollIfc.DoubleOutput<?>)col).pollDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((PeekAndPollIfc.DoubleOutput<?>)col).pollDouble());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfFloat col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((PeekAndPollIfc.DoubleOutput<?>)col).peekDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((PeekAndPollIfc.DoubleOutput<?>)col).peekDouble());
      }
    }
    @Override public void verifyDequePollLast(OmniCollection.OfFloat col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((OmniDeque.OfFloat)col).pollLastDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniDeque.OfFloat)col).pollLastDouble());
      }
    }
    @Override public void verifyDequePollFirst(OmniCollection.OfFloat col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((OmniDeque.OfFloat)col).pollFirstDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniDeque.OfFloat)col).pollFirstDouble());
      }
    }
    @Override public void verifyDequePeekLast(OmniCollection.OfFloat col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((OmniDeque.OfFloat)col).peekLastDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniDeque.OfFloat)col).peekLastDouble());
      }
    }
    @Override public void verifyDequePeekFirst(OmniCollection.OfFloat col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((OmniDeque.OfFloat)col).peekFirstDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniDeque.OfFloat)col).peekFirstDouble());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfFloat col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniStack.OfFloat)col).popDouble());
    }
    @Override public void verifyQueueElement(OmniCollection.OfFloat col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniQueue.OfFloat)col).doubleElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfFloat col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniQueue.OfFloat)col).removeDouble());
    }
    @Override public void verifyDequeRemoveFirst(OmniCollection.OfFloat col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniDeque.OfFloat)col).removeFirstDouble());
    }
    @Override public void verifyDequeRemoveLast(OmniCollection.OfFloat col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniDeque.OfFloat)col).removeLastDouble());
    }
    @Override public void verifyDequeGetFirst(OmniCollection.OfFloat col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniDeque.OfFloat)col).getFirstDouble());
    }
    @Override public void verifyDequeGetLast(OmniCollection.OfFloat col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniDeque.OfFloat)col).getLastDouble());
    }
  }
  ;
  public abstract void verifyDequePeekLast(OmniCollection.OfFloat col,int expectedSize,int expectedVal);
  public abstract void verifyDequePollLast(OmniCollection.OfFloat col,int expectedSize,int expectedVal);
  public abstract void verifyDequePeekFirst(OmniCollection.OfFloat col,int expectedSize,int expectedVal);
  public abstract void verifyDequePollFirst(OmniCollection.OfFloat col,int expectedSize,int expectedVal);
  public abstract void verifyDequeRemoveLast(OmniCollection.OfFloat col,int expectedVal);
  public abstract void verifyDequeRemoveFirst(OmniCollection.OfFloat col,int expectedVal);
  public abstract void verifyDequeGetLast(OmniCollection.OfFloat col,int expectedVal);
  public abstract void verifyDequeGetFirst(OmniCollection.OfFloat col,int expectedVal);
  public abstract void verifyItrPrevious(OmniIterator.OfFloat itr,int valToConvert);
  public abstract void verifyItrNext(OmniIterator.OfFloat itr,int valToConvert);
  public abstract void verifyListRemoveAt(OmniCollection.OfFloat col,int index,int valToConvert);
  public abstract void verifyListGet(OmniCollection.OfFloat col,int index,int valToConvert);
  public abstract void verifyToArray(OmniCollection.OfFloat col,int expectedSize);
  public abstract void verifyPoll(OmniCollection.OfFloat col,int expectedSize,int expectedVal);
  public abstract void verifyStackPop(OmniCollection.OfFloat col,int expectedVal);
  public abstract void verifyQueueRemove(OmniCollection.OfFloat col,int expectedVal);
  public abstract void verifyQueueElement(OmniCollection.OfFloat col,int expectedVal);
  public abstract void verifyPeek(OmniCollection.OfFloat col,int expectedSize,int expectedVal);
}
