package omni.impl;
import omni.util.TypeConversionUtil;
import omni.api.OmniCollection;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import omni.util.OmniArray;
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
    @Override public void verifyStackPoll(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Character.MIN_VALUE,((OmniStack.OfChar)col).pollChar());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTochar(expectedVal),((OmniStack.OfChar)col).pollChar());
      }
    }
    @Override public void verifyStackPeek(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Character.MIN_VALUE,((OmniStack.OfChar)col).peekChar());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTochar(expectedVal),((OmniStack.OfChar)col).peekChar());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(expectedVal),((OmniStack.OfChar)col).popChar());
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
    @Override public void verifyStackPoll(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(null,((OmniStack.OfChar)col).poll());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToCharacter(expectedVal),((OmniStack.OfChar)col).poll());
      }
    }
    @Override public void verifyStackPeek(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(null,((OmniStack.OfChar)col).peek());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToCharacter(expectedVal),((OmniStack.OfChar)col).peek());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToCharacter(expectedVal),((OmniStack.OfChar)col).pop());
    }
  }
  ,
  DOUBLE{
    @Override public void verifyItrPrevious(OmniIterator.OfChar itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),((OmniListIterator.OfChar)itr).previousDouble());
    }
    @Override public void verifyItrNext(OmniIterator.OfChar itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),itr.nextDouble());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfChar col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),((OmniList.OfChar)col).removeDoubleAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfChar col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),((OmniList.OfChar)col).getDouble(index));
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
    @Override public void verifyStackPoll(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((OmniStack.OfChar)col).pollDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniStack.OfChar)col).pollDouble());
      }
    }
    @Override public void verifyStackPeek(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Double.NaN,((OmniStack.OfChar)col).peekDouble());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniStack.OfChar)col).peekDouble());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(expectedVal),((OmniStack.OfChar)col).popDouble());
    }
  }
  ,
  FLOAT{
    @Override public void verifyItrPrevious(OmniIterator.OfChar itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(valToConvert),((OmniListIterator.OfChar)itr).previousFloat());
    }
    @Override public void verifyItrNext(OmniIterator.OfChar itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(valToConvert),itr.nextFloat());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfChar col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(valToConvert),((OmniList.OfChar)col).removeFloatAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfChar col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(valToConvert),((OmniList.OfChar)col).getFloat(index));
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
    @Override public void verifyStackPoll(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((OmniStack.OfChar)col).pollFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniStack.OfChar)col).pollFloat());
      }
    }
    @Override public void verifyStackPeek(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Float.NaN,((OmniStack.OfChar)col).peekFloat());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniStack.OfChar)col).peekFloat());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedVal),((OmniStack.OfChar)col).popFloat());
    }
  }
  ,
  LONG{
    @Override public void verifyItrPrevious(OmniIterator.OfChar itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(valToConvert),((OmniListIterator.OfChar)itr).previousLong());
    }
    @Override public void verifyItrNext(OmniIterator.OfChar itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(valToConvert),itr.nextLong());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfChar col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(valToConvert),((OmniList.OfChar)col).removeLongAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfChar col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(valToConvert),((OmniList.OfChar)col).getLong(index));
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
    @Override public void verifyStackPoll(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Long.MIN_VALUE,((OmniStack.OfChar)col).pollLong());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTolong(expectedVal),((OmniStack.OfChar)col).pollLong());
      }
    }
    @Override public void verifyStackPeek(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Long.MIN_VALUE,((OmniStack.OfChar)col).peekLong());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertTolong(expectedVal),((OmniStack.OfChar)col).peekLong());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(expectedVal),((OmniStack.OfChar)col).popLong());
    }
  }
  ,
  INT{
    @Override public void verifyItrPrevious(OmniIterator.OfChar itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToint(valToConvert),((OmniListIterator.OfChar)itr).previousInt());
    }
    @Override public void verifyItrNext(OmniIterator.OfChar itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToint(valToConvert),itr.nextInt());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfChar col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToint(valToConvert),((OmniList.OfChar)col).removeIntAt(index));
    }
    @Override public void verifyListGet(OmniCollection.OfChar col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToint(valToConvert),((OmniList.OfChar)col).getInt(index));
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
    @Override public void verifyStackPoll(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Integer.MIN_VALUE,((OmniStack.OfChar)col).pollInt());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToint(expectedVal),((OmniStack.OfChar)col).pollInt());
      }
    }
    @Override public void verifyStackPeek(OmniCollection.OfChar col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(Integer.MIN_VALUE,((OmniStack.OfChar)col).peekInt());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToint(expectedVal),((OmniStack.OfChar)col).peekInt());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfChar col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToint(expectedVal),((OmniStack.OfChar)col).popInt());
    }
  }
  ;
  public abstract void verifyItrPrevious(OmniIterator.OfChar itr,int valToConvert);
  public abstract void verifyItrNext(OmniIterator.OfChar itr,int valToConvert);
  public abstract void verifyListRemoveAt(OmniCollection.OfChar col,int index,int valToConvert);
  public abstract void verifyListGet(OmniCollection.OfChar col,int index,int valToConvert);
  public abstract void verifyToArray(OmniCollection.OfChar col,int expectedSize);
  public abstract void verifyStackPoll(OmniCollection.OfChar col,int expectedSize,int expectedVal);
  public abstract void verifyStackPop(OmniCollection.OfChar col,int expectedVal);
  public abstract void verifyStackPeek(OmniCollection.OfChar col,int expectedSize,int expectedVal);
  //TODO other method tests
}
