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
public enum DoubleOutputTestArgType{
  ARRAY_TYPE{
    @Override public void verifyItrPrevious(OmniIterator.OfDouble itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),((OmniListIterator.OfDouble)itr).previousDouble());
    }
    @Override public void verifyItrNext(OmniIterator.OfDouble itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),itr.nextDouble());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfDouble col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),((OmniList.OfDouble)col).removeDoubleAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfDouble col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),((OmniList.OfDouble)col).getDouble(index));
    }
    @Override public void verifyToArray(OmniCollection.OfDouble col,int expectedSize){
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
    @Override public void verifyPoll(OmniCollection.OfDouble col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((PeekAndPollIfc.DoubleInput)col).pollDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((PeekAndPollIfc.DoubleInput)col).pollDouble());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfDouble col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((PeekAndPollIfc.DoubleInput)col).peekDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((PeekAndPollIfc.DoubleInput)col).peekDouble());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfDouble col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniStack.OfDouble)col).popDouble());
    }
    @Override public void verifyQueueElement(OmniCollection.OfDouble col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniQueue.OfDouble)col).doubleElement());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfDouble col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniQueue.OfDouble)col).removeDouble());
    }
  }
  ,
  BOXED_TYPE{
    @Override public void verifyItrPrevious(OmniIterator.OfDouble itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToDouble(valToConvert),((OmniListIterator.OfDouble)itr).previous());
    }
    @Override public void verifyItrNext(OmniIterator.OfDouble itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToDouble(valToConvert),itr.next());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfDouble col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToDouble(valToConvert),((OmniList.OfDouble)col).remove(index));
    }
    @Override public void verifyListGet(OmniCollection.OfDouble col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToDouble(valToConvert),((OmniList.OfDouble)col).get(index));
    }
    @Override public void verifyToArray(OmniCollection.OfDouble col,int expectedSize){
      var arr=col.toArray();
      if(expectedSize==0){
        Assertions.assertSame(OmniArray.OfDouble.DEFAULT_BOXED_ARR,arr);
      }else{
        Assertions.assertEquals(expectedSize,arr.length);
        var itr=col.iterator();
        for(int i=0;i<expectedSize;++i){
          Assertions.assertEquals(itr.next(),arr[i]);
        }
      }
    }
    @Override public void verifyPoll(OmniCollection.OfDouble col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertNull(((PeekAndPollIfc<?>)col).poll());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToDouble(expectedVal),((PeekAndPollIfc<?>)col).poll());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfDouble col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertNull(((PeekAndPollIfc<?>)col).peek());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToDouble(expectedVal),((PeekAndPollIfc<?>)col).peek());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfDouble col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToDouble(expectedVal),((OmniStack.OfDouble)col).pop());
    }
    @Override public void verifyQueueElement(OmniCollection.OfDouble col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToDouble(expectedVal),((OmniQueue.OfDouble)col).element());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfDouble col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToDouble(expectedVal),((OmniQueue.OfDouble)col).remove());
    }
  }
  ;
  public abstract void verifyItrPrevious(OmniIterator.OfDouble itr,int valToConvert);
  public abstract void verifyItrNext(OmniIterator.OfDouble itr,int valToConvert);
  public abstract void verifyListRemoveAt(OmniCollection.OfDouble col,int index,int valToConvert);
  public abstract void verifyListGet(OmniCollection.OfDouble col,int index,int valToConvert);
  public abstract void verifyToArray(OmniCollection.OfDouble col,int expectedSize);
  public abstract void verifyPoll(OmniCollection.OfDouble col,int expectedSize,int expectedVal);
  public abstract void verifyStackPop(OmniCollection.OfDouble col,int expectedVal);
  public abstract void verifyQueueRemove(OmniCollection.OfDouble col,int expectedVal);
  public abstract void verifyQueueElement(OmniCollection.OfDouble col,int expectedVal);
  public abstract void verifyPeek(OmniCollection.OfDouble col,int expectedSize,int expectedVal);
  //TODO other method tests
}
