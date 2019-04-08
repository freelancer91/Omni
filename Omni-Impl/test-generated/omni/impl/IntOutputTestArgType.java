package omni.impl;
import omni.util.TypeConversionUtil;
import omni.api.OmniCollection;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import omni.util.OmniArray;
import org.junit.jupiter.api.Assertions;
public enum IntOutputTestArgType{
  ARRAY_TYPE{
    @Override public void verifyItrPrevious(OmniIterator.OfInt itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToint(valToConvert),((OmniListIterator.OfInt)itr).previousInt());
    }
    @Override public void verifyItrNext(OmniIterator.OfInt itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToint(valToConvert),itr.nextInt());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfInt col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToint(valToConvert),((OmniList.OfInt)col).removeIntAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfInt col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToint(valToConvert),((OmniList.OfInt)col).getInt(index));
    }
    @Override public void verifyToArray(OmniCollection.OfInt col,int expectedSize){
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
    @Override public void verifyStackPoll(OmniCollection.OfInt col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Integer.MIN_VALUE,((OmniStack.OfInt)col).pollInt());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToint(expectedVal),((OmniStack.OfInt)col).pollInt());
      }
    }
    @Override public void verifyStackPeek(OmniCollection.OfInt col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Integer.MIN_VALUE,((OmniStack.OfInt)col).peekInt());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToint(expectedVal),((OmniStack.OfInt)col).peekInt());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfInt col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToint(expectedVal),((OmniStack.OfInt)col).popInt());
    }
  }
  ,
  BOXED_TYPE{
    @Override public void verifyItrPrevious(OmniIterator.OfInt itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToInteger(valToConvert),((OmniListIterator.OfInt)itr).previous());
    }
    @Override public void verifyItrNext(OmniIterator.OfInt itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToInteger(valToConvert),itr.next());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfInt col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToInteger(valToConvert),((OmniList.OfInt)col).remove(index));
    }
    @Override public void verifyListGet(OmniCollection.OfInt col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToInteger(valToConvert),((OmniList.OfInt)col).get(index));
    }
    @Override public void verifyToArray(OmniCollection.OfInt col,int expectedSize){
      var arr=col.toArray();
      if(expectedSize==0){
        Assertions.assertSame(OmniArray.OfInt.DEFAULT_BOXED_ARR,arr);
      }else{
        Assertions.assertEquals(expectedSize,arr.length);
        var itr=col.iterator();
        for(int i=0;i<expectedSize;++i){
          Assertions.assertEquals(itr.next(),arr[i]);
        }
      }
    }
    @Override public void verifyStackPoll(OmniCollection.OfInt col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(null,((OmniStack.OfInt)col).poll());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToInteger(expectedVal),((OmniStack.OfInt)col).poll());
      }
    }
    @Override public void verifyStackPeek(OmniCollection.OfInt col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(null,((OmniStack.OfInt)col).peek());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToInteger(expectedVal),((OmniStack.OfInt)col).peek());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfInt col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToInteger(expectedVal),((OmniStack.OfInt)col).pop());
    }
  }
  ,
  DOUBLE{
    @Override public void verifyItrPrevious(OmniIterator.OfInt itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),((OmniListIterator.OfInt)itr).previousDouble());
    }
    @Override public void verifyItrNext(OmniIterator.OfInt itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),itr.nextDouble());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfInt col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),((OmniList.OfInt)col).removeDoubleAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfInt col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),((OmniList.OfInt)col).getDouble(index));
    }
    @Override public void verifyToArray(OmniCollection.OfInt col,int expectedSize){
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
    @Override public void verifyStackPoll(OmniCollection.OfInt col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((OmniStack.OfInt)col).pollDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniStack.OfInt)col).pollDouble());
      }
    }
    @Override public void verifyStackPeek(OmniCollection.OfInt col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((OmniStack.OfInt)col).peekDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniStack.OfInt)col).peekDouble());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfInt col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniStack.OfInt)col).popDouble());
    }
  }
  ,
  FLOAT{
    @Override public void verifyItrPrevious(OmniIterator.OfInt itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(valToConvert),((OmniListIterator.OfInt)itr).previousFloat());
    }
    @Override public void verifyItrNext(OmniIterator.OfInt itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(valToConvert),itr.nextFloat());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfInt col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(valToConvert),((OmniList.OfInt)col).removeFloatAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfInt col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(valToConvert),((OmniList.OfInt)col).getFloat(index));
    }
    @Override public void verifyToArray(OmniCollection.OfInt col,int expectedSize){
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
    @Override public void verifyStackPoll(OmniCollection.OfInt col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((OmniStack.OfInt)col).pollFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniStack.OfInt)col).pollFloat());
      }
    }
    @Override public void verifyStackPeek(OmniCollection.OfInt col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((OmniStack.OfInt)col).peekFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniStack.OfInt)col).peekFloat());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfInt col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniStack.OfInt)col).popFloat());
    }
  }
  ,
  LONG{
    @Override public void verifyItrPrevious(OmniIterator.OfInt itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(valToConvert),((OmniListIterator.OfInt)itr).previousLong());
    }
    @Override public void verifyItrNext(OmniIterator.OfInt itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(valToConvert),itr.nextLong());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfInt col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(valToConvert),((OmniList.OfInt)col).removeLongAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfInt col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(valToConvert),((OmniList.OfInt)col).getLong(index));
    }
    @Override public void verifyToArray(OmniCollection.OfInt col,int expectedSize){
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
    @Override public void verifyStackPoll(OmniCollection.OfInt col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Long.MIN_VALUE,((OmniStack.OfInt)col).pollLong());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTolong(expectedVal),((OmniStack.OfInt)col).pollLong());
      }
    }
    @Override public void verifyStackPeek(OmniCollection.OfInt col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Long.MIN_VALUE,((OmniStack.OfInt)col).peekLong());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTolong(expectedVal),((OmniStack.OfInt)col).peekLong());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfInt col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(expectedVal),((OmniStack.OfInt)col).popLong());
    }
  }
  ;
  public abstract void verifyItrPrevious(OmniIterator.OfInt itr,int valToConvert);
  public abstract void verifyItrNext(OmniIterator.OfInt itr,int valToConvert);
  public abstract void verifyListRemoveAt(OmniCollection.OfInt col,int index,int valToConvert);
  public abstract void verifyListGet(OmniCollection.OfInt col,int index,int valToConvert);
  public abstract void verifyToArray(OmniCollection.OfInt col,int expectedSize);
  public abstract void verifyStackPoll(OmniCollection.OfInt col,int expectedSize,int expectedVal);
  public abstract void verifyStackPop(OmniCollection.OfInt col,int expectedVal);
  public abstract void verifyStackPeek(OmniCollection.OfInt col,int expectedSize,int expectedVal);
  //TODO other method tests
}
