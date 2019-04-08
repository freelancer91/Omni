package omni.impl;
import omni.util.TypeConversionUtil;
import omni.api.OmniCollection;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import omni.util.OmniArray;
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
    @Override public void verifyStackPoll(OmniCollection.OfDouble col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((OmniStack.OfDouble)col).pollDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniStack.OfDouble)col).pollDouble());
      }
    }
    @Override public void verifyStackPeek(OmniCollection.OfDouble col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((OmniStack.OfDouble)col).peekDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniStack.OfDouble)col).peekDouble());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfDouble col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniStack.OfDouble)col).popDouble());
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
    @Override public void verifyStackPoll(OmniCollection.OfDouble col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(null,((OmniStack.OfDouble)col).poll());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToDouble(expectedVal),((OmniStack.OfDouble)col).poll());
      }
    }
    @Override public void verifyStackPeek(OmniCollection.OfDouble col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(null,((OmniStack.OfDouble)col).peek());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToDouble(expectedVal),((OmniStack.OfDouble)col).peek());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfDouble col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToDouble(expectedVal),((OmniStack.OfDouble)col).pop());
    }
  }
  ;
  public abstract void verifyItrPrevious(OmniIterator.OfDouble itr,int valToConvert);
  public abstract void verifyItrNext(OmniIterator.OfDouble itr,int valToConvert);
  public abstract void verifyListRemoveAt(OmniCollection.OfDouble col,int index,int valToConvert);
  public abstract void verifyListGet(OmniCollection.OfDouble col,int index,int valToConvert);
  public abstract void verifyToArray(OmniCollection.OfDouble col,int expectedSize);
  public abstract void verifyStackPoll(OmniCollection.OfDouble col,int expectedSize,int expectedVal);
  public abstract void verifyStackPop(OmniCollection.OfDouble col,int expectedVal);
  public abstract void verifyStackPeek(OmniCollection.OfDouble col,int expectedSize,int expectedVal);
  //TODO other method tests
}
