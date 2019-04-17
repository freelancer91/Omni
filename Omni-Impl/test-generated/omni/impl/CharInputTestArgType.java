package omni.impl;
import omni.util.TypeConversionUtil;
import omni.api.OmniCollection;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.api.OmniQueue;
import omni.api.OmniListIterator;
import org.junit.jupiter.api.Assertions;
public enum CharInputTestArgType{
  ARRAY_TYPE{
    public boolean callQueueOffer(OmniCollection.OfChar seq,int valToConvert){return ((OmniQueue.OfChar)seq).offer(TypeConversionUtil.convertTochar(valToConvert));}
    public void callListItrAdd(OmniListIterator.OfChar itr,int valToConvert){itr.add(TypeConversionUtil.convertTochar(valToConvert));}
    public void callListItrSet(OmniListIterator.OfChar itr,int valToConvert){itr.set(TypeConversionUtil.convertTochar(valToConvert));}
    public void callListPut(OmniCollection.OfChar seq,int index,int valToConvert){((OmniList.OfChar)seq).put(index,TypeConversionUtil.convertTochar(valToConvert));}
    public void callListAdd(OmniCollection.OfChar seq,int index,int valToConvert){((OmniList.OfChar)seq).add(index,TypeConversionUtil.convertTochar(valToConvert));}
    public void callStackPush(OmniCollection.OfChar seq,int valToConvert){((OmniStack.OfChar)seq).push(TypeConversionUtil.convertTochar(valToConvert));}
    public boolean callCollectionAdd(OmniCollection.OfChar seq,int valToConvert){return seq.add(TypeConversionUtil.convertTochar(valToConvert));}
    public void verifyVal(int expectedValToConvert,char actualVal){Assertions.assertEquals(TypeConversionUtil.convertTochar(expectedValToConvert),actualVal);}
  }
  ,
  BOXED_TYPE{
    public boolean callQueueOffer(OmniCollection.OfChar seq,int valToConvert){return ((OmniQueue.OfChar)seq).offer(TypeConversionUtil.convertToCharacter(valToConvert));}
    public void callListItrAdd(OmniListIterator.OfChar itr,int valToConvert){itr.add(TypeConversionUtil.convertToCharacter(valToConvert));}
    public void callListItrSet(OmniListIterator.OfChar itr,int valToConvert){itr.set(TypeConversionUtil.convertToCharacter(valToConvert));}
    public void callListPut(OmniCollection.OfChar seq,int index,int valToConvert){((OmniList.OfChar)seq).put(index,TypeConversionUtil.convertToCharacter(valToConvert));}
    public void callListAdd(OmniCollection.OfChar seq,int index,int valToConvert){((OmniList.OfChar)seq).add(index,TypeConversionUtil.convertToCharacter(valToConvert));}
    public void callStackPush(OmniCollection.OfChar seq,int valToConvert){((OmniStack.OfChar)seq).push(TypeConversionUtil.convertToCharacter(valToConvert));}
    public boolean callCollectionAdd(OmniCollection.OfChar seq,int valToConvert){return seq.add(TypeConversionUtil.convertToCharacter(valToConvert));}
    public void verifyVal(int expectedValToConvert,char actualVal){Assertions.assertEquals(TypeConversionUtil.convertTochar(expectedValToConvert),actualVal);}
  }
  ,
  PRIMITIVE_BOOLEAN{
    public boolean callQueueOffer(OmniCollection.OfChar seq,int valToConvert){return ((OmniQueue.OfChar)seq).offer(TypeConversionUtil.convertToboolean(valToConvert));}
    public void callListItrAdd(OmniListIterator.OfChar itr,int valToConvert){itr.add(TypeConversionUtil.convertToboolean(valToConvert));}
    public void callListItrSet(OmniListIterator.OfChar itr,int valToConvert){itr.set(TypeConversionUtil.convertToboolean(valToConvert));}
    public void callListPut(OmniCollection.OfChar seq,int index,int valToConvert){((OmniList.OfChar)seq).put(index,TypeConversionUtil.convertToboolean(valToConvert));}
    public void callListAdd(OmniCollection.OfChar seq,int index,int valToConvert){((OmniList.OfChar)seq).add(index,TypeConversionUtil.convertToboolean(valToConvert));}
    public void callStackPush(OmniCollection.OfChar seq,int valToConvert){((OmniStack.OfChar)seq).push(TypeConversionUtil.convertToboolean(valToConvert));}
    public boolean callCollectionAdd(OmniCollection.OfChar seq,int valToConvert){return seq.add(TypeConversionUtil.convertToboolean(valToConvert));}
    public void verifyVal(int expectedValToConvert,char actualVal){Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(expectedValToConvert),actualVal);}
  }
  ,
  BOXED_BOOLEAN{
    public boolean callQueueOffer(OmniCollection.OfChar seq,int valToConvert){return ((OmniQueue.OfChar)seq).offer(TypeConversionUtil.convertToBoolean(valToConvert));}
    public void callListItrAdd(OmniListIterator.OfChar itr,int valToConvert){itr.add(TypeConversionUtil.convertToBoolean(valToConvert));}
    public void callListItrSet(OmniListIterator.OfChar itr,int valToConvert){itr.set(TypeConversionUtil.convertToBoolean(valToConvert));}
    public void callListPut(OmniCollection.OfChar seq,int index,int valToConvert){((OmniList.OfChar)seq).put(index,TypeConversionUtil.convertToBoolean(valToConvert));}
    public void callListAdd(OmniCollection.OfChar seq,int index,int valToConvert){((OmniList.OfChar)seq).add(index,TypeConversionUtil.convertToBoolean(valToConvert));}
    public void callStackPush(OmniCollection.OfChar seq,int valToConvert){((OmniStack.OfChar)seq).push(TypeConversionUtil.convertToBoolean(valToConvert));}
    public boolean callCollectionAdd(OmniCollection.OfChar seq,int valToConvert){return seq.add(TypeConversionUtil.convertToBoolean(valToConvert));}
    public void verifyVal(int expectedValToConvert,char actualVal){Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(expectedValToConvert),actualVal);}
  }
  ;
    public abstract boolean callQueueOffer(OmniCollection.OfChar seq,int valToConvert);
    public abstract void callListItrAdd(OmniListIterator.OfChar itr,int valToConvert);
    public abstract void callListItrSet(OmniListIterator.OfChar itr,int valToConvert);
    public abstract void callListPut(OmniCollection.OfChar seq,int index,int valToConvert);
    public abstract void callListAdd(OmniCollection.OfChar seq,int index,int valToConvert);
    public abstract void callStackPush(OmniCollection.OfChar seq,int valToConvert);
    public abstract boolean callCollectionAdd(OmniCollection.OfChar seq,int valToConvert);
    public abstract void verifyVal(int expectedValToConvert,char actualVal);
}
