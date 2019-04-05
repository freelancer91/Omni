package omni.impl;
import omni.util.TypeConversionUtil;
import omni.api.OmniCollection;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import org.junit.jupiter.api.Assertions;
public enum ShortOutputTestArgType{
  ARRAY_TYPE{
    @Override public void verifyItrPrevious(OmniIterator.OfShort itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToshort(valToConvert),((OmniListIterator.OfShort)itr).previousShort());
    }
    @Override public void verifyItrNext(OmniIterator.OfShort itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToshort(valToConvert),itr.nextShort());
    }
  }
  ,
  BOXED_TYPE{
    @Override public void verifyItrPrevious(OmniIterator.OfShort itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToShort(valToConvert),((OmniListIterator.OfShort)itr).previous());
    }
    @Override public void verifyItrNext(OmniIterator.OfShort itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToShort(valToConvert),itr.next());
    }
  }
  ,
  DOUBLE{
    @Override public void verifyItrPrevious(OmniIterator.OfShort itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),((OmniListIterator.OfShort)itr).previousDouble());
    }
    @Override public void verifyItrNext(OmniIterator.OfShort itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),itr.nextDouble());
    }
  }
  ,
  FLOAT{
    @Override public void verifyItrPrevious(OmniIterator.OfShort itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(valToConvert),((OmniListIterator.OfShort)itr).previousFloat());
    }
    @Override public void verifyItrNext(OmniIterator.OfShort itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(valToConvert),itr.nextFloat());
    }
  }
  ,
  LONG{
    @Override public void verifyItrPrevious(OmniIterator.OfShort itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(valToConvert),((OmniListIterator.OfShort)itr).previousLong());
    }
    @Override public void verifyItrNext(OmniIterator.OfShort itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(valToConvert),itr.nextLong());
    }
  }
  ,
  INT{
    @Override public void verifyItrPrevious(OmniIterator.OfShort itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToint(valToConvert),((OmniListIterator.OfShort)itr).previousInt());
    }
    @Override public void verifyItrNext(OmniIterator.OfShort itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToint(valToConvert),itr.nextInt());
    }
  }
  ;
  public abstract void verifyItrPrevious(OmniIterator.OfShort itr,int valToConvert);
  public abstract void verifyItrNext(OmniIterator.OfShort itr,int valToConvert);
  //TODO other method tests
}
