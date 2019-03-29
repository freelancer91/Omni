package omni.impl;
import omni.util.TypeConversionUtil;
import omni.api.OmniCollection;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.api.OmniListIterator;
import org.junit.jupiter.api.Assertions;
public enum ByteInputTestArgType{
  ARRAY_TYPE{
    public void callListItrAdd(OmniListIterator.OfByte itr,int valToConvert){itr.add(TypeConversionUtil.convertTobyte(valToConvert));}
    public void callListItrSet(OmniListIterator.OfByte itr,int valToConvert){itr.set(TypeConversionUtil.convertTobyte(valToConvert));}
    public void callListPut(OmniCollection.OfByte seq,int index,int valToConvert){((OmniList.OfByte)seq).put(index,TypeConversionUtil.convertTobyte(valToConvert));}
    public void callListAdd(OmniCollection.OfByte seq,int index,int valToConvert){((OmniList.OfByte)seq).add(index,TypeConversionUtil.convertTobyte(valToConvert));}
    public void callStackPush(OmniCollection.OfByte seq,int valToConvert){((OmniStack.OfByte)seq).push(TypeConversionUtil.convertTobyte(valToConvert));}
    public boolean callCollectionAdd(OmniCollection.OfByte seq,int valToConvert){return seq.add(TypeConversionUtil.convertTobyte(valToConvert));}
    public void verifyVal(int expectedValToConvert,byte actualVal){Assertions.assertEquals(TypeConversionUtil.convertTobyte(expectedValToConvert),actualVal);}
  }
  ,
  BOXED_TYPE{
    public void callListItrAdd(OmniListIterator.OfByte itr,int valToConvert){itr.add(TypeConversionUtil.convertToByte(valToConvert));}
    public void callListItrSet(OmniListIterator.OfByte itr,int valToConvert){itr.set(TypeConversionUtil.convertToByte(valToConvert));}
    public void callListPut(OmniCollection.OfByte seq,int index,int valToConvert){((OmniList.OfByte)seq).put(index,TypeConversionUtil.convertToByte(valToConvert));}
    public void callListAdd(OmniCollection.OfByte seq,int index,int valToConvert){((OmniList.OfByte)seq).add(index,TypeConversionUtil.convertToByte(valToConvert));}
    public void callStackPush(OmniCollection.OfByte seq,int valToConvert){((OmniStack.OfByte)seq).push(TypeConversionUtil.convertToByte(valToConvert));}
    public boolean callCollectionAdd(OmniCollection.OfByte seq,int valToConvert){return seq.add(TypeConversionUtil.convertToByte(valToConvert));}
    public void verifyVal(int expectedValToConvert,byte actualVal){Assertions.assertEquals(TypeConversionUtil.convertTobyte(expectedValToConvert),actualVal);}
  }
  ,
  PRIMITIVE_BOOLEAN{
    public void callListItrAdd(OmniListIterator.OfByte itr,int valToConvert){itr.add(TypeConversionUtil.convertToboolean(valToConvert));}
    public void callListItrSet(OmniListIterator.OfByte itr,int valToConvert){itr.set(TypeConversionUtil.convertToboolean(valToConvert));}
    public void callListPut(OmniCollection.OfByte seq,int index,int valToConvert){((OmniList.OfByte)seq).put(index,TypeConversionUtil.convertToboolean(valToConvert));}
    public void callListAdd(OmniCollection.OfByte seq,int index,int valToConvert){((OmniList.OfByte)seq).add(index,TypeConversionUtil.convertToboolean(valToConvert));}
    public void callStackPush(OmniCollection.OfByte seq,int valToConvert){((OmniStack.OfByte)seq).push(TypeConversionUtil.convertToboolean(valToConvert));}
    public boolean callCollectionAdd(OmniCollection.OfByte seq,int valToConvert){return seq.add(TypeConversionUtil.convertToboolean(valToConvert));}
    public void verifyVal(int expectedValToConvert,byte actualVal){Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(expectedValToConvert),actualVal);}
  }
  ,
  BOXED_BOOLEAN{
    public void callListItrAdd(OmniListIterator.OfByte itr,int valToConvert){itr.add(TypeConversionUtil.convertToBoolean(valToConvert));}
    public void callListItrSet(OmniListIterator.OfByte itr,int valToConvert){itr.set(TypeConversionUtil.convertToBoolean(valToConvert));}
    public void callListPut(OmniCollection.OfByte seq,int index,int valToConvert){((OmniList.OfByte)seq).put(index,TypeConversionUtil.convertToBoolean(valToConvert));}
    public void callListAdd(OmniCollection.OfByte seq,int index,int valToConvert){((OmniList.OfByte)seq).add(index,TypeConversionUtil.convertToBoolean(valToConvert));}
    public void callStackPush(OmniCollection.OfByte seq,int valToConvert){((OmniStack.OfByte)seq).push(TypeConversionUtil.convertToBoolean(valToConvert));}
    public boolean callCollectionAdd(OmniCollection.OfByte seq,int valToConvert){return seq.add(TypeConversionUtil.convertToBoolean(valToConvert));}
    public void verifyVal(int expectedValToConvert,byte actualVal){Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(expectedValToConvert),actualVal);}
  }
  ;
    public abstract void callListItrAdd(OmniListIterator.OfByte itr,int valToConvert);
    public abstract void callListItrSet(OmniListIterator.OfByte itr,int valToConvert);
    public abstract void callListPut(OmniCollection.OfByte seq,int index,int valToConvert);
    public abstract void callListAdd(OmniCollection.OfByte seq,int index,int valToConvert);
    public abstract void callStackPush(OmniCollection.OfByte seq,int valToConvert);
    public abstract boolean callCollectionAdd(OmniCollection.OfByte seq,int valToConvert);
    public abstract void verifyVal(int expectedValToConvert,byte actualVal);
}
