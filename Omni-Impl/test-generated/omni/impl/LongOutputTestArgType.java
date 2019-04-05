package omni.impl;
import omni.util.TypeConversionUtil;
import omni.api.OmniCollection;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import org.junit.jupiter.api.Assertions;
public enum LongOutputTestArgType{
  ARRAY_TYPE{
    @Override public void verifyItrPrevious(OmniIterator.OfLong itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(valToConvert),((OmniListIterator.OfLong)itr).previousLong());
    }
    @Override public void verifyItrNext(OmniIterator.OfLong itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(valToConvert),itr.nextLong());
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
  }
  ,
  DOUBLE{
    @Override public void verifyItrPrevious(OmniIterator.OfLong itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),((OmniListIterator.OfLong)itr).previousDouble());
    }
    @Override public void verifyItrNext(OmniIterator.OfLong itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),itr.nextDouble());
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
  }
  ;
  public abstract void verifyItrPrevious(OmniIterator.OfLong itr,int valToConvert);
  public abstract void verifyItrNext(OmniIterator.OfLong itr,int valToConvert);
  //TODO other method tests
}
