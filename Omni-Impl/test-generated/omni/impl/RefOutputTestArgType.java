package omni.impl;
import omni.util.TypeConversionUtil;
import omni.api.OmniCollection;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import org.junit.jupiter.api.Assertions;
@SuppressWarnings({"rawtypes","unchecked"}) 
public enum RefOutputTestArgType{
  ARRAY_TYPE{
    @Override public void verifyItrPrevious(OmniIterator.OfRef itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToObject(valToConvert),((OmniListIterator.OfRef)itr).previous());
    }
    @Override public void verifyItrNext(OmniIterator.OfRef itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToObject(valToConvert),itr.next());
    }
  }
  ;
  public abstract void verifyItrPrevious(OmniIterator.OfRef itr,int valToConvert);
  public abstract void verifyItrNext(OmniIterator.OfRef itr,int valToConvert);
  //TODO other method tests
}
