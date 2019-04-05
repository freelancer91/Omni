package omni.impl;
import omni.util.TypeConversionUtil;
import omni.api.OmniCollection;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import org.junit.jupiter.api.Assertions;
public enum DoubleOutputTestArgType{
  ARRAY_TYPE{
    @Override public void verifyItrPrevious(OmniIterator.OfDouble itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),((OmniListIterator.OfDouble)itr).previousDouble());
    }
    @Override public void verifyItrNext(OmniIterator.OfDouble itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),itr.nextDouble());
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
  }
  ;
  public abstract void verifyItrPrevious(OmniIterator.OfDouble itr,int valToConvert);
  public abstract void verifyItrNext(OmniIterator.OfDouble itr,int valToConvert);
  //TODO other method tests
}
