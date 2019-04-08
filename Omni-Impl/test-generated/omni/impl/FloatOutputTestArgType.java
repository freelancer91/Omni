package omni.impl;
import omni.util.TypeConversionUtil;
import omni.api.OmniCollection;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import omni.util.OmniArray;
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
    @Override public void verifyStackPoll(OmniCollection.OfFloat col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((OmniStack.OfFloat)col).pollFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniStack.OfFloat)col).pollFloat());
      }
    }
    @Override public void verifyStackPeek(OmniCollection.OfFloat col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((OmniStack.OfFloat)col).peekFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniStack.OfFloat)col).peekFloat());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfFloat col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniStack.OfFloat)col).popFloat());
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
    @Override public void verifyStackPoll(OmniCollection.OfFloat col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(null,((OmniStack.OfFloat)col).poll());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToFloat(expectedVal),((OmniStack.OfFloat)col).poll());
      }
    }
    @Override public void verifyStackPeek(OmniCollection.OfFloat col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(null,((OmniStack.OfFloat)col).peek());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToFloat(expectedVal),((OmniStack.OfFloat)col).peek());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfFloat col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToFloat(expectedVal),((OmniStack.OfFloat)col).pop());
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
    @Override public void verifyStackPoll(OmniCollection.OfFloat col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((OmniStack.OfFloat)col).pollDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniStack.OfFloat)col).pollDouble());
      }
    }
    @Override public void verifyStackPeek(OmniCollection.OfFloat col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((OmniStack.OfFloat)col).peekDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniStack.OfFloat)col).peekDouble());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfFloat col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniStack.OfFloat)col).popDouble());
    }
  }
  ;
  public abstract void verifyItrPrevious(OmniIterator.OfFloat itr,int valToConvert);
  public abstract void verifyItrNext(OmniIterator.OfFloat itr,int valToConvert);
  public abstract void verifyListRemoveAt(OmniCollection.OfFloat col,int index,int valToConvert);
  public abstract void verifyListGet(OmniCollection.OfFloat col,int index,int valToConvert);
  public abstract void verifyToArray(OmniCollection.OfFloat col,int expectedSize);
  public abstract void verifyStackPoll(OmniCollection.OfFloat col,int expectedSize,int expectedVal);
  public abstract void verifyStackPop(OmniCollection.OfFloat col,int expectedVal);
  public abstract void verifyStackPeek(OmniCollection.OfFloat col,int expectedSize,int expectedVal);
  //TODO other method tests
}
