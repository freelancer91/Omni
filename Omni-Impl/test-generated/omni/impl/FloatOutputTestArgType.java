package omni.impl;
import omni.util.TypeConversionUtil;
import omni.api.OmniCollection;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import org.junit.jupiter.api.Assertions;
public enum FloatOutputTestArgType{
  ARRAY_TYPE{
    @Override public void verifyItrPrevious(OmniIterator.OfFloat itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(valToConvert),((OmniListIterator.OfFloat)itr).previousFloat());
    }
    @Override public void verifyItrNext(OmniIterator.OfFloat itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(valToConvert),itr.nextFloat());
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
  }
  ,
  DOUBLE{
    @Override public void verifyItrPrevious(OmniIterator.OfFloat itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),((OmniListIterator.OfFloat)itr).previousDouble());
    }
    @Override public void verifyItrNext(OmniIterator.OfFloat itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),itr.nextDouble());
    }
  }
  ;
  public abstract void verifyItrPrevious(OmniIterator.OfFloat itr,int valToConvert);
  public abstract void verifyItrNext(OmniIterator.OfFloat itr,int valToConvert);
  //TODO other method tests
}
