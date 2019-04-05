package omni.impl;
import omni.util.TypeConversionUtil;
import omni.api.OmniCollection;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import org.junit.jupiter.api.Assertions;
public enum ByteOutputTestArgType{
  ARRAY_TYPE{
    @Override public void verifyItrPrevious(OmniIterator.OfByte itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(valToConvert),((OmniListIterator.OfByte)itr).previousByte());
    }
    @Override public void verifyItrNext(OmniIterator.OfByte itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(valToConvert),itr.nextByte());
    }
  }
  ,
  BOXED_TYPE{
    @Override public void verifyItrPrevious(OmniIterator.OfByte itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToByte(valToConvert),((OmniListIterator.OfByte)itr).previous());
    }
    @Override public void verifyItrNext(OmniIterator.OfByte itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToByte(valToConvert),itr.next());
    }
  }
  ,
  DOUBLE{
    @Override public void verifyItrPrevious(OmniIterator.OfByte itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),((OmniListIterator.OfByte)itr).previousDouble());
    }
    @Override public void verifyItrNext(OmniIterator.OfByte itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(valToConvert),itr.nextDouble());
    }
  }
  ,
  FLOAT{
    @Override public void verifyItrPrevious(OmniIterator.OfByte itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(valToConvert),((OmniListIterator.OfByte)itr).previousFloat());
    }
    @Override public void verifyItrNext(OmniIterator.OfByte itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(valToConvert),itr.nextFloat());
    }
  }
  ,
  LONG{
    @Override public void verifyItrPrevious(OmniIterator.OfByte itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(valToConvert),((OmniListIterator.OfByte)itr).previousLong());
    }
    @Override public void verifyItrNext(OmniIterator.OfByte itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(valToConvert),itr.nextLong());
    }
  }
  ,
  INT{
    @Override public void verifyItrPrevious(OmniIterator.OfByte itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToint(valToConvert),((OmniListIterator.OfByte)itr).previousInt());
    }
    @Override public void verifyItrNext(OmniIterator.OfByte itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToint(valToConvert),itr.nextInt());
    }
  }
  ,
  SHORT{
    @Override public void verifyItrPrevious(OmniIterator.OfByte itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToshort(valToConvert),((OmniListIterator.OfByte)itr).previousShort());
    }
    @Override public void verifyItrNext(OmniIterator.OfByte itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToshort(valToConvert),itr.nextShort());
    }
  }
  ;
  public abstract void verifyItrPrevious(OmniIterator.OfByte itr,int valToConvert);
  public abstract void verifyItrNext(OmniIterator.OfByte itr,int valToConvert);
  //TODO other method tests
}
