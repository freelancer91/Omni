package omni.impl;
import omni.util.TypeConversionUtil;
import omni.api.OmniCollection;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import org.junit.jupiter.api.Assertions;
public enum CharOutputTestArgType{
  ARRAY_TYPE{
    @Override public void verifyItrPrevious(OmniIterator.OfChar itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(valToConvert),((OmniListIterator.OfChar)itr).previousChar());
    }
    @Override public void verifyItrNext(OmniIterator.OfChar itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(valToConvert),itr.nextChar());
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
  }
  ,
  DOUBLE{
    @Override public void verifyItrPrevious(OmniIterator.OfChar itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),((OmniListIterator.OfChar)itr).previousDouble());
    }
    @Override public void verifyItrNext(OmniIterator.OfChar itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),itr.nextDouble());
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
  }
  ,
  LONG{
    @Override public void verifyItrPrevious(OmniIterator.OfChar itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(valToConvert),((OmniListIterator.OfChar)itr).previousLong());
    }
    @Override public void verifyItrNext(OmniIterator.OfChar itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(valToConvert),itr.nextLong());
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
  }
  ;
  public abstract void verifyItrPrevious(OmniIterator.OfChar itr,int valToConvert);
  public abstract void verifyItrNext(OmniIterator.OfChar itr,int valToConvert);
  //TODO other method tests
}
