package omni.impl;
import omni.util.TypeConversionUtil;
import omni.api.OmniCollection;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.api.OmniQueue;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import omni.util.OmniArray;
import omni.util.PeekAndPollIfc;
import org.junit.jupiter.api.Assertions;
@SuppressWarnings({"rawtypes"}) 
public enum RefOutputTestArgType{
  ARRAY_TYPE{
    @Override public void verifyItrPrevious(OmniIterator.OfRef itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToObject(valToConvert),((OmniListIterator.OfRef)itr).previous());
    }
    @Override public void verifyItrNext(OmniIterator.OfRef itr,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToObject(valToConvert),itr.next());
    }
    @Override public void verifyListRemoveAt(OmniCollection.OfRef col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToObject(valToConvert),((OmniList.OfRef)col).remove(index));
    }
    @Override public void verifyListGet(OmniCollection.OfRef col,int index,int valToConvert){
      Assertions.assertEquals(TypeConversionUtil.convertToObject(valToConvert),((OmniList.OfRef)col).get(index));
    }
    @Override public void verifyToArray(OmniCollection.OfRef col,int expectedSize){
      var arr=col.toArray();
      if(expectedSize==0){
        Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,arr);
      }else{
        Assertions.assertEquals(expectedSize,arr.length);
        var itr=col.iterator();
        for(int i=0;i<expectedSize;++i){
          Assertions.assertSame(itr.next(),arr[i]);
        }
      }
    }
    @Override public void verifyPoll(OmniCollection.OfRef col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(null,((PeekAndPollIfc<?>)col).poll());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToObject(expectedVal),((PeekAndPollIfc<?>)col).poll());
      }
    }
    @Override public void verifyPeek(OmniCollection.OfRef col,int expectedSize,int expectedVal){
      if(expectedSize==0){
        Assertions.assertEquals(null,((PeekAndPollIfc<?>)col).peek());
      }else{
        Assertions.assertEquals(TypeConversionUtil.convertToObject(expectedVal),((PeekAndPollIfc<?>)col).peek());
      }
    }
    @Override public void verifyStackPop(OmniCollection.OfRef col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToObject(expectedVal),((OmniStack.OfRef)col).pop());
    }
    @Override public void verifyQueueElement(OmniCollection.OfRef col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToObject(expectedVal),((OmniQueue.OfRef)col).element());
    }
    @Override public void verifyQueueRemove(OmniCollection.OfRef col,int expectedVal){
      Assertions.assertEquals(TypeConversionUtil.convertToObject(expectedVal),((OmniQueue.OfRef)col).remove());
    }
  }
  ;
  public abstract void verifyItrPrevious(OmniIterator.OfRef itr,int valToConvert);
  public abstract void verifyItrNext(OmniIterator.OfRef itr,int valToConvert);
  public abstract void verifyListRemoveAt(OmniCollection.OfRef col,int index,int valToConvert);
  public abstract void verifyListGet(OmniCollection.OfRef col,int index,int valToConvert);
  public abstract void verifyToArray(OmniCollection.OfRef col,int expectedSize);
  public abstract void verifyPoll(OmniCollection.OfRef col,int expectedSize,int expectedVal);
  public abstract void verifyStackPop(OmniCollection.OfRef col,int expectedVal);
  public abstract void verifyQueueRemove(OmniCollection.OfRef col,int expectedVal);
  public abstract void verifyQueueElement(OmniCollection.OfRef col,int expectedVal);
  public abstract void verifyPeek(OmniCollection.OfRef col,int expectedSize,int expectedVal);
  //TODO other method tests
}
