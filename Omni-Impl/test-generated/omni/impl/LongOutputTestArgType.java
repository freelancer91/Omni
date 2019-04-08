package omni.impl;
import omni.util.TypeConversionUtil;
import omni.api.OmniCollection;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import omni.util.OmniArray;
import org.junit.jupiter.api.Assertions;
public enum LongOutputTestArgType{
  ARRAY_TYPE{
    @Override public void verifyItrPrevious(OmniIterator.OfLong itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(valToConvert),((OmniListIterator.OfLong)itr).previousLong());
    }
    @Override public void verifyItrNext(OmniIterator.OfLong itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(valToConvert),itr.nextLong());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfLong col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(valToConvert),((OmniList.OfLong)col).removeLongAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfLong col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(valToConvert),((OmniList.OfLong)col).getLong(index));
    }
    @Override public void verifyToArray(OmniCollection.OfLong col,int expectedSize){
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
    @Override public void verifyStackPoll(OmniCollection.OfLong col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Long.MIN_VALUE,((OmniStack.OfLong)col).pollLong());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTolong(expectedVal),((OmniStack.OfLong)col).pollLong());
      }
    }
    @Override public void verifyStackPeek(OmniCollection.OfLong col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Long.MIN_VALUE,((OmniStack.OfLong)col).peekLong());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTolong(expectedVal),((OmniStack.OfLong)col).peekLong());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfLong col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(expectedVal),((OmniStack.OfLong)col).popLong());
    }
  }
  ,
  BOXED_TYPE{
    @Override public void verifyItrPrevious(OmniIterator.OfLong itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToLong(valToConvert),((OmniListIterator.OfLong)itr).previous());
    }
    @Override public void verifyItrNext(OmniIterator.OfLong itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToLong(valToConvert),itr.next());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfLong col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToLong(valToConvert),((OmniList.OfLong)col).remove(index));
    }
    @Override public void verifyListGet(OmniCollection.OfLong col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToLong(valToConvert),((OmniList.OfLong)col).get(index));
    }
    @Override public void verifyToArray(OmniCollection.OfLong col,int expectedSize){
      var arr=col.toArray();
      if(expectedSize==0){
        Assertions.assertSame(OmniArray.OfLong.DEFAULT_BOXED_ARR,arr);
      }else{
        Assertions.assertEquals(expectedSize,arr.length);
        var itr=col.iterator();
        for(int i=0;i<expectedSize;++i){
          Assertions.assertEquals(itr.next(),arr[i]);
        }
      }
    }
    @Override public void verifyStackPoll(OmniCollection.OfLong col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(null,((OmniStack.OfLong)col).poll());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToLong(expectedVal),((OmniStack.OfLong)col).poll());
      }
    }
    @Override public void verifyStackPeek(OmniCollection.OfLong col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(null,((OmniStack.OfLong)col).peek());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToLong(expectedVal),((OmniStack.OfLong)col).peek());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfLong col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToLong(expectedVal),((OmniStack.OfLong)col).pop());
    }
  }
  ,
  DOUBLE{
    @Override public void verifyItrPrevious(OmniIterator.OfLong itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),((OmniListIterator.OfLong)itr).previousDouble());
    }
    @Override public void verifyItrNext(OmniIterator.OfLong itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),itr.nextDouble());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfLong col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),((OmniList.OfLong)col).removeDoubleAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfLong col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),((OmniList.OfLong)col).getDouble(index));
    }
    @Override public void verifyToArray(OmniCollection.OfLong col,int expectedSize){
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
    @Override public void verifyStackPoll(OmniCollection.OfLong col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((OmniStack.OfLong)col).pollDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniStack.OfLong)col).pollDouble());
      }
    }
    @Override public void verifyStackPeek(OmniCollection.OfLong col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((OmniStack.OfLong)col).peekDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniStack.OfLong)col).peekDouble());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfLong col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniStack.OfLong)col).popDouble());
    }
  }
  ,
  FLOAT{
    @Override public void verifyItrPrevious(OmniIterator.OfLong itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(valToConvert),((OmniListIterator.OfLong)itr).previousFloat());
    }
    @Override public void verifyItrNext(OmniIterator.OfLong itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(valToConvert),itr.nextFloat());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfLong col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(valToConvert),((OmniList.OfLong)col).removeFloatAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfLong col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(valToConvert),((OmniList.OfLong)col).getFloat(index));
    }
    @Override public void verifyToArray(OmniCollection.OfLong col,int expectedSize){
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
    @Override public void verifyStackPoll(OmniCollection.OfLong col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((OmniStack.OfLong)col).pollFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniStack.OfLong)col).pollFloat());
      }
    }
    @Override public void verifyStackPeek(OmniCollection.OfLong col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((OmniStack.OfLong)col).peekFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniStack.OfLong)col).peekFloat());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfLong col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniStack.OfLong)col).popFloat());
    }
  }
  ;
  public abstract void verifyItrPrevious(OmniIterator.OfLong itr,int valToConvert);
  public abstract void verifyItrNext(OmniIterator.OfLong itr,int valToConvert);
  public abstract void verifyListRemoveAt(OmniCollection.OfLong col,int index,int valToConvert);
  public abstract void verifyListGet(OmniCollection.OfLong col,int index,int valToConvert);
  public abstract void verifyToArray(OmniCollection.OfLong col,int expectedSize);
  public abstract void verifyStackPoll(OmniCollection.OfLong col,int expectedSize,int expectedVal);
  public abstract void verifyStackPop(OmniCollection.OfLong col,int expectedVal);
  public abstract void verifyStackPeek(OmniCollection.OfLong col,int expectedSize,int expectedVal);
  //TODO other method tests
}
