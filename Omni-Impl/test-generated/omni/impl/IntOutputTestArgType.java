package omni.impl;
import omni.util.TypeConversionUtil;
import omni.api.OmniCollection;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import org.junit.jupiter.api.Assertions;
public enum IntOutputTestArgType{
  ARRAY_TYPE{
    @Override public void verifyItrPrevious(OmniIterator.OfInt itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToint(valToConvert),((OmniListIterator.OfInt)itr).previousInt());
    }
    @Override public void verifyItrNext(OmniIterator.OfInt itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToint(valToConvert),itr.nextInt());
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
  }
  ,
  DOUBLE{
    @Override public void verifyItrPrevious(OmniIterator.OfInt itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),((OmniListIterator.OfInt)itr).previousDouble());
    }
    @Override public void verifyItrNext(OmniIterator.OfInt itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),itr.nextDouble());
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
  }
  ,
  LONG{
    @Override public void verifyItrPrevious(OmniIterator.OfInt itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(valToConvert),((OmniListIterator.OfInt)itr).previousLong());
    }
    @Override public void verifyItrNext(OmniIterator.OfInt itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(valToConvert),itr.nextLong());
    }
  }
  ;
  public abstract void verifyItrPrevious(OmniIterator.OfInt itr,int valToConvert);
  public abstract void verifyItrNext(OmniIterator.OfInt itr,int valToConvert);
  //TODO other method tests
}
