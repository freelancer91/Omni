package omni.impl;
import omni.util.TypeConversionUtil;
import omni.api.OmniCollection;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import org.junit.jupiter.api.Assertions;
public enum BooleanOutputTestArgType{
  ARRAY_TYPE{
    @Override public void verifyItrPrevious(OmniIterator.OfBoolean itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(valToConvert),((OmniListIterator.OfBoolean)itr).previousBoolean());
    }
    @Override public void verifyItrNext(OmniIterator.OfBoolean itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(valToConvert),itr.nextBoolean());
    }
  }
  ,
  BOXED_TYPE{
    @Override public void verifyItrPrevious(OmniIterator.OfBoolean itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToBoolean(valToConvert),((OmniListIterator.OfBoolean)itr).previous());
    }
    @Override public void verifyItrNext(OmniIterator.OfBoolean itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToBoolean(valToConvert),itr.next());
    }
  }
  ,
  DOUBLE{
    @Override public void verifyItrPrevious(OmniIterator.OfBoolean itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodoubleboolean(valToConvert),((OmniListIterator.OfBoolean)itr).previousDouble());
    }
    @Override public void verifyItrNext(OmniIterator.OfBoolean itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodoubleboolean(valToConvert),itr.nextDouble());
    }
  }
  ,
  FLOAT{
    @Override public void verifyItrPrevious(OmniIterator.OfBoolean itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(valToConvert),((OmniListIterator.OfBoolean)itr).previousFloat());
    }
    @Override public void verifyItrNext(OmniIterator.OfBoolean itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(valToConvert),itr.nextFloat());
    }
  }
  ,
  LONG{
    @Override public void verifyItrPrevious(OmniIterator.OfBoolean itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolongboolean(valToConvert),((OmniListIterator.OfBoolean)itr).previousLong());
    }
    @Override public void verifyItrNext(OmniIterator.OfBoolean itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolongboolean(valToConvert),itr.nextLong());
    }
  }
  ,
  INT{
    @Override public void verifyItrPrevious(OmniIterator.OfBoolean itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(valToConvert),((OmniListIterator.OfBoolean)itr).previousInt());
    }
    @Override public void verifyItrNext(OmniIterator.OfBoolean itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(valToConvert),itr.nextInt());
    }
  }
  ,
  SHORT{
    @Override public void verifyItrPrevious(OmniIterator.OfBoolean itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(valToConvert),((OmniListIterator.OfBoolean)itr).previousShort());
    }
    @Override public void verifyItrNext(OmniIterator.OfBoolean itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(valToConvert),itr.nextShort());
    }
  }
  ,
  BYTE{
    @Override public void verifyItrPrevious(OmniIterator.OfBoolean itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(valToConvert),((OmniListIterator.OfBoolean)itr).previousByte());
    }
    @Override public void verifyItrNext(OmniIterator.OfBoolean itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(valToConvert),itr.nextByte());
    }
  }
  ,
  CHAR{
    @Override public void verifyItrPrevious(OmniIterator.OfBoolean itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(valToConvert),((OmniListIterator.OfBoolean)itr).previousChar());
    }
    @Override public void verifyItrNext(OmniIterator.OfBoolean itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(valToConvert),itr.nextChar());
    }
  }
  ;
  public abstract void verifyItrPrevious(OmniIterator.OfBoolean itr,int valToConvert);
  public abstract void verifyItrNext(OmniIterator.OfBoolean itr,int valToConvert);
  //TODO other method tests
}
