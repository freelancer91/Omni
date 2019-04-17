package omni.impl;
import omni.util.TypeConversionUtil;
import omni.api.OmniCollection;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.api.OmniQueue;
import omni.api.OmniListIterator;
import org.junit.jupiter.api.Assertions;
public enum FloatInputTestArgType{
  ARRAY_TYPE{
    public boolean callQueueOffer(OmniCollection.OfFloat seq,int valToConvert){return ((OmniQueue.OfFloat)seq).offer(TypeConversionUtil.convertTofloat(valToConvert));}
    public void callListItrAdd(OmniListIterator.OfFloat itr,int valToConvert){itr.add(TypeConversionUtil.convertTofloat(valToConvert));}
    public void callListItrSet(OmniListIterator.OfFloat itr,int valToConvert){itr.set(TypeConversionUtil.convertTofloat(valToConvert));}
    public void callListPut(OmniCollection.OfFloat seq,int index,int valToConvert){((OmniList.OfFloat)seq).put(index,TypeConversionUtil.convertTofloat(valToConvert));}
    public void callListAdd(OmniCollection.OfFloat seq,int index,int valToConvert){((OmniList.OfFloat)seq).add(index,TypeConversionUtil.convertTofloat(valToConvert));}
    public void callStackPush(OmniCollection.OfFloat seq,int valToConvert){((OmniStack.OfFloat)seq).push(TypeConversionUtil.convertTofloat(valToConvert));}
    public boolean callCollectionAdd(OmniCollection.OfFloat seq,int valToConvert){return seq.add(TypeConversionUtil.convertTofloat(valToConvert));}
    public void verifyVal(int expectedValToConvert,float actualVal){Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedValToConvert),actualVal);}
  }
  ,
  BOXED_TYPE{
    public boolean callQueueOffer(OmniCollection.OfFloat seq,int valToConvert){return ((OmniQueue.OfFloat)seq).offer(TypeConversionUtil.convertToFloat(valToConvert));}
    public void callListItrAdd(OmniListIterator.OfFloat itr,int valToConvert){itr.add(TypeConversionUtil.convertToFloat(valToConvert));}
    public void callListItrSet(OmniListIterator.OfFloat itr,int valToConvert){itr.set(TypeConversionUtil.convertToFloat(valToConvert));}
    public void callListPut(OmniCollection.OfFloat seq,int index,int valToConvert){((OmniList.OfFloat)seq).put(index,TypeConversionUtil.convertToFloat(valToConvert));}
    public void callListAdd(OmniCollection.OfFloat seq,int index,int valToConvert){((OmniList.OfFloat)seq).add(index,TypeConversionUtil.convertToFloat(valToConvert));}
    public void callStackPush(OmniCollection.OfFloat seq,int valToConvert){((OmniStack.OfFloat)seq).push(TypeConversionUtil.convertToFloat(valToConvert));}
    public boolean callCollectionAdd(OmniCollection.OfFloat seq,int valToConvert){return seq.add(TypeConversionUtil.convertToFloat(valToConvert));}
    public void verifyVal(int expectedValToConvert,float actualVal){Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedValToConvert),actualVal);}
  }
  ,
  PRIMITIVE_BOOLEAN{
    public boolean callQueueOffer(OmniCollection.OfFloat seq,int valToConvert){return ((OmniQueue.OfFloat)seq).offer(TypeConversionUtil.convertToboolean(valToConvert));}
    public void callListItrAdd(OmniListIterator.OfFloat itr,int valToConvert){itr.add(TypeConversionUtil.convertToboolean(valToConvert));}
    public void callListItrSet(OmniListIterator.OfFloat itr,int valToConvert){itr.set(TypeConversionUtil.convertToboolean(valToConvert));}
    public void callListPut(OmniCollection.OfFloat seq,int index,int valToConvert){((OmniList.OfFloat)seq).put(index,TypeConversionUtil.convertToboolean(valToConvert));}
    public void callListAdd(OmniCollection.OfFloat seq,int index,int valToConvert){((OmniList.OfFloat)seq).add(index,TypeConversionUtil.convertToboolean(valToConvert));}
    public void callStackPush(OmniCollection.OfFloat seq,int valToConvert){((OmniStack.OfFloat)seq).push(TypeConversionUtil.convertToboolean(valToConvert));}
    public boolean callCollectionAdd(OmniCollection.OfFloat seq,int valToConvert){return seq.add(TypeConversionUtil.convertToboolean(valToConvert));}
    public void verifyVal(int expectedValToConvert,float actualVal){Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(expectedValToConvert),actualVal);}
  }
  ,
  BOXED_BOOLEAN{
    public boolean callQueueOffer(OmniCollection.OfFloat seq,int valToConvert){return ((OmniQueue.OfFloat)seq).offer(TypeConversionUtil.convertToBoolean(valToConvert));}
    public void callListItrAdd(OmniListIterator.OfFloat itr,int valToConvert){itr.add(TypeConversionUtil.convertToBoolean(valToConvert));}
    public void callListItrSet(OmniListIterator.OfFloat itr,int valToConvert){itr.set(TypeConversionUtil.convertToBoolean(valToConvert));}
    public void callListPut(OmniCollection.OfFloat seq,int index,int valToConvert){((OmniList.OfFloat)seq).put(index,TypeConversionUtil.convertToBoolean(valToConvert));}
    public void callListAdd(OmniCollection.OfFloat seq,int index,int valToConvert){((OmniList.OfFloat)seq).add(index,TypeConversionUtil.convertToBoolean(valToConvert));}
    public void callStackPush(OmniCollection.OfFloat seq,int valToConvert){((OmniStack.OfFloat)seq).push(TypeConversionUtil.convertToBoolean(valToConvert));}
    public boolean callCollectionAdd(OmniCollection.OfFloat seq,int valToConvert){return seq.add(TypeConversionUtil.convertToBoolean(valToConvert));}
    public void verifyVal(int expectedValToConvert,float actualVal){Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(expectedValToConvert),actualVal);}
  }
  ,
  PRIMITIVE_BYTE{
    public boolean callQueueOffer(OmniCollection.OfFloat seq,int valToConvert){return ((OmniQueue.OfFloat)seq).offer(TypeConversionUtil.convertTobyte(valToConvert));}
    public void callListItrAdd(OmniListIterator.OfFloat itr,int valToConvert){itr.add(TypeConversionUtil.convertTobyte(valToConvert));}
    public void callListItrSet(OmniListIterator.OfFloat itr,int valToConvert){itr.set(TypeConversionUtil.convertTobyte(valToConvert));}
    public void callListPut(OmniCollection.OfFloat seq,int index,int valToConvert){((OmniList.OfFloat)seq).put(index,TypeConversionUtil.convertTobyte(valToConvert));}
    public void callListAdd(OmniCollection.OfFloat seq,int index,int valToConvert){((OmniList.OfFloat)seq).add(index,TypeConversionUtil.convertTobyte(valToConvert));}
    public void callStackPush(OmniCollection.OfFloat seq,int valToConvert){((OmniStack.OfFloat)seq).push(TypeConversionUtil.convertTobyte(valToConvert));}
    public boolean callCollectionAdd(OmniCollection.OfFloat seq,int valToConvert){return seq.add(TypeConversionUtil.convertTobyte(valToConvert));}
    public void verifyVal(int expectedValToConvert,float actualVal){Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedValToConvert),actualVal);}
  }
  ,
  BOXED_BYTE{
    public boolean callQueueOffer(OmniCollection.OfFloat seq,int valToConvert){return ((OmniQueue.OfFloat)seq).offer(TypeConversionUtil.convertToByte(valToConvert));}
    public void callListItrAdd(OmniListIterator.OfFloat itr,int valToConvert){itr.add(TypeConversionUtil.convertToByte(valToConvert));}
    public void callListItrSet(OmniListIterator.OfFloat itr,int valToConvert){itr.set(TypeConversionUtil.convertToByte(valToConvert));}
    public void callListPut(OmniCollection.OfFloat seq,int index,int valToConvert){((OmniList.OfFloat)seq).put(index,TypeConversionUtil.convertToByte(valToConvert));}
    public void callListAdd(OmniCollection.OfFloat seq,int index,int valToConvert){((OmniList.OfFloat)seq).add(index,TypeConversionUtil.convertToByte(valToConvert));}
    public void callStackPush(OmniCollection.OfFloat seq,int valToConvert){((OmniStack.OfFloat)seq).push(TypeConversionUtil.convertToByte(valToConvert));}
    public boolean callCollectionAdd(OmniCollection.OfFloat seq,int valToConvert){return seq.add(TypeConversionUtil.convertToByte(valToConvert));}
    public void verifyVal(int expectedValToConvert,float actualVal){Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedValToConvert),actualVal);}
  }
  ,
  PRIMTIVE_SHORT{
    public boolean callQueueOffer(OmniCollection.OfFloat seq,int valToConvert){return ((OmniQueue.OfFloat)seq).offer(TypeConversionUtil.convertToshort(valToConvert));}
    public void callListItrAdd(OmniListIterator.OfFloat itr,int valToConvert){itr.add(TypeConversionUtil.convertToshort(valToConvert));}
    public void callListItrSet(OmniListIterator.OfFloat itr,int valToConvert){itr.set(TypeConversionUtil.convertToshort(valToConvert));}
    public void callListPut(OmniCollection.OfFloat seq,int index,int valToConvert){((OmniList.OfFloat)seq).put(index,TypeConversionUtil.convertToshort(valToConvert));}
    public void callListAdd(OmniCollection.OfFloat seq,int index,int valToConvert){((OmniList.OfFloat)seq).add(index,TypeConversionUtil.convertToshort(valToConvert));}
    public void callStackPush(OmniCollection.OfFloat seq,int valToConvert){((OmniStack.OfFloat)seq).push(TypeConversionUtil.convertToshort(valToConvert));}
    public boolean callCollectionAdd(OmniCollection.OfFloat seq,int valToConvert){return seq.add(TypeConversionUtil.convertToshort(valToConvert));}
    public void verifyVal(int expectedValToConvert,float actualVal){Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedValToConvert),actualVal);}
  }
  ,
  BOXED_SHORT{
    public boolean callQueueOffer(OmniCollection.OfFloat seq,int valToConvert){return ((OmniQueue.OfFloat)seq).offer(TypeConversionUtil.convertToShort(valToConvert));}
    public void callListItrAdd(OmniListIterator.OfFloat itr,int valToConvert){itr.add(TypeConversionUtil.convertToShort(valToConvert));}
    public void callListItrSet(OmniListIterator.OfFloat itr,int valToConvert){itr.set(TypeConversionUtil.convertToShort(valToConvert));}
    public void callListPut(OmniCollection.OfFloat seq,int index,int valToConvert){((OmniList.OfFloat)seq).put(index,TypeConversionUtil.convertToShort(valToConvert));}
    public void callListAdd(OmniCollection.OfFloat seq,int index,int valToConvert){((OmniList.OfFloat)seq).add(index,TypeConversionUtil.convertToShort(valToConvert));}
    public void callStackPush(OmniCollection.OfFloat seq,int valToConvert){((OmniStack.OfFloat)seq).push(TypeConversionUtil.convertToShort(valToConvert));}
    public boolean callCollectionAdd(OmniCollection.OfFloat seq,int valToConvert){return seq.add(TypeConversionUtil.convertToShort(valToConvert));}
    public void verifyVal(int expectedValToConvert,float actualVal){Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedValToConvert),actualVal);}
  }
  ,
  PRIMITIVE_CHAR{
    public boolean callQueueOffer(OmniCollection.OfFloat seq,int valToConvert){return ((OmniQueue.OfFloat)seq).offer(TypeConversionUtil.convertTochar(valToConvert));}
    public void callListItrAdd(OmniListIterator.OfFloat itr,int valToConvert){itr.add(TypeConversionUtil.convertTochar(valToConvert));}
    public void callListItrSet(OmniListIterator.OfFloat itr,int valToConvert){itr.set(TypeConversionUtil.convertTochar(valToConvert));}
    public void callListPut(OmniCollection.OfFloat seq,int index,int valToConvert){((OmniList.OfFloat)seq).put(index,TypeConversionUtil.convertTochar(valToConvert));}
    public void callListAdd(OmniCollection.OfFloat seq,int index,int valToConvert){((OmniList.OfFloat)seq).add(index,TypeConversionUtil.convertTochar(valToConvert));}
    public void callStackPush(OmniCollection.OfFloat seq,int valToConvert){((OmniStack.OfFloat)seq).push(TypeConversionUtil.convertTochar(valToConvert));}
    public boolean callCollectionAdd(OmniCollection.OfFloat seq,int valToConvert){return seq.add(TypeConversionUtil.convertTochar(valToConvert));}
    public void verifyVal(int expectedValToConvert,float actualVal){Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedValToConvert),actualVal);}
  }
  ,
  BOXED_CHAR{
    public boolean callQueueOffer(OmniCollection.OfFloat seq,int valToConvert){return ((OmniQueue.OfFloat)seq).offer(TypeConversionUtil.convertToCharacter(valToConvert));}
    public void callListItrAdd(OmniListIterator.OfFloat itr,int valToConvert){itr.add(TypeConversionUtil.convertToCharacter(valToConvert));}
    public void callListItrSet(OmniListIterator.OfFloat itr,int valToConvert){itr.set(TypeConversionUtil.convertToCharacter(valToConvert));}
    public void callListPut(OmniCollection.OfFloat seq,int index,int valToConvert){((OmniList.OfFloat)seq).put(index,TypeConversionUtil.convertToCharacter(valToConvert));}
    public void callListAdd(OmniCollection.OfFloat seq,int index,int valToConvert){((OmniList.OfFloat)seq).add(index,TypeConversionUtil.convertToCharacter(valToConvert));}
    public void callStackPush(OmniCollection.OfFloat seq,int valToConvert){((OmniStack.OfFloat)seq).push(TypeConversionUtil.convertToCharacter(valToConvert));}
    public boolean callCollectionAdd(OmniCollection.OfFloat seq,int valToConvert){return seq.add(TypeConversionUtil.convertToCharacter(valToConvert));}
    public void verifyVal(int expectedValToConvert,float actualVal){Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedValToConvert),actualVal);}
  }
  ,
  PRIMITIVE_INT{
    public boolean callQueueOffer(OmniCollection.OfFloat seq,int valToConvert){return ((OmniQueue.OfFloat)seq).offer(TypeConversionUtil.convertToint(valToConvert));}
    public void callListItrAdd(OmniListIterator.OfFloat itr,int valToConvert){itr.add(TypeConversionUtil.convertToint(valToConvert));}
    public void callListItrSet(OmniListIterator.OfFloat itr,int valToConvert){itr.set(TypeConversionUtil.convertToint(valToConvert));}
    public void callListPut(OmniCollection.OfFloat seq,int index,int valToConvert){((OmniList.OfFloat)seq).put(index,TypeConversionUtil.convertToint(valToConvert));}
    public void callListAdd(OmniCollection.OfFloat seq,int index,int valToConvert){((OmniList.OfFloat)seq).add(index,TypeConversionUtil.convertToint(valToConvert));}
    public void callStackPush(OmniCollection.OfFloat seq,int valToConvert){((OmniStack.OfFloat)seq).push(TypeConversionUtil.convertToint(valToConvert));}
    public boolean callCollectionAdd(OmniCollection.OfFloat seq,int valToConvert){return seq.add(TypeConversionUtil.convertToint(valToConvert));}
    public void verifyVal(int expectedValToConvert,float actualVal){Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedValToConvert),actualVal);}
  }
  ,
  BOXED_INT{
    public boolean callQueueOffer(OmniCollection.OfFloat seq,int valToConvert){return ((OmniQueue.OfFloat)seq).offer(TypeConversionUtil.convertToInteger(valToConvert));}
    public void callListItrAdd(OmniListIterator.OfFloat itr,int valToConvert){itr.add(TypeConversionUtil.convertToInteger(valToConvert));}
    public void callListItrSet(OmniListIterator.OfFloat itr,int valToConvert){itr.set(TypeConversionUtil.convertToInteger(valToConvert));}
    public void callListPut(OmniCollection.OfFloat seq,int index,int valToConvert){((OmniList.OfFloat)seq).put(index,TypeConversionUtil.convertToInteger(valToConvert));}
    public void callListAdd(OmniCollection.OfFloat seq,int index,int valToConvert){((OmniList.OfFloat)seq).add(index,TypeConversionUtil.convertToInteger(valToConvert));}
    public void callStackPush(OmniCollection.OfFloat seq,int valToConvert){((OmniStack.OfFloat)seq).push(TypeConversionUtil.convertToInteger(valToConvert));}
    public boolean callCollectionAdd(OmniCollection.OfFloat seq,int valToConvert){return seq.add(TypeConversionUtil.convertToInteger(valToConvert));}
    public void verifyVal(int expectedValToConvert,float actualVal){Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedValToConvert),actualVal);}
  }
  ,
  PRIMITIVE_LONG{
    public boolean callQueueOffer(OmniCollection.OfFloat seq,int valToConvert){return ((OmniQueue.OfFloat)seq).offer(TypeConversionUtil.convertTolong(valToConvert));}
    public void callListItrAdd(OmniListIterator.OfFloat itr,int valToConvert){itr.add(TypeConversionUtil.convertTolong(valToConvert));}
    public void callListItrSet(OmniListIterator.OfFloat itr,int valToConvert){itr.set(TypeConversionUtil.convertTolong(valToConvert));}
    public void callListPut(OmniCollection.OfFloat seq,int index,int valToConvert){((OmniList.OfFloat)seq).put(index,TypeConversionUtil.convertTolong(valToConvert));}
    public void callListAdd(OmniCollection.OfFloat seq,int index,int valToConvert){((OmniList.OfFloat)seq).add(index,TypeConversionUtil.convertTolong(valToConvert));}
    public void callStackPush(OmniCollection.OfFloat seq,int valToConvert){((OmniStack.OfFloat)seq).push(TypeConversionUtil.convertTolong(valToConvert));}
    public boolean callCollectionAdd(OmniCollection.OfFloat seq,int valToConvert){return seq.add(TypeConversionUtil.convertTolong(valToConvert));}
    public void verifyVal(int expectedValToConvert,float actualVal){Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedValToConvert),actualVal);}
  }
  ,
  BOXED_LONG{
    public boolean callQueueOffer(OmniCollection.OfFloat seq,int valToConvert){return ((OmniQueue.OfFloat)seq).offer(TypeConversionUtil.convertToLong(valToConvert));}
    public void callListItrAdd(OmniListIterator.OfFloat itr,int valToConvert){itr.add(TypeConversionUtil.convertToLong(valToConvert));}
    public void callListItrSet(OmniListIterator.OfFloat itr,int valToConvert){itr.set(TypeConversionUtil.convertToLong(valToConvert));}
    public void callListPut(OmniCollection.OfFloat seq,int index,int valToConvert){((OmniList.OfFloat)seq).put(index,TypeConversionUtil.convertToLong(valToConvert));}
    public void callListAdd(OmniCollection.OfFloat seq,int index,int valToConvert){((OmniList.OfFloat)seq).add(index,TypeConversionUtil.convertToLong(valToConvert));}
    public void callStackPush(OmniCollection.OfFloat seq,int valToConvert){((OmniStack.OfFloat)seq).push(TypeConversionUtil.convertToLong(valToConvert));}
    public boolean callCollectionAdd(OmniCollection.OfFloat seq,int valToConvert){return seq.add(TypeConversionUtil.convertToLong(valToConvert));}
    public void verifyVal(int expectedValToConvert,float actualVal){Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedValToConvert),actualVal);}
  }
  ;
    public abstract boolean callQueueOffer(OmniCollection.OfFloat seq,int valToConvert);
    public abstract void callListItrAdd(OmniListIterator.OfFloat itr,int valToConvert);
    public abstract void callListItrSet(OmniListIterator.OfFloat itr,int valToConvert);
    public abstract void callListPut(OmniCollection.OfFloat seq,int index,int valToConvert);
    public abstract void callListAdd(OmniCollection.OfFloat seq,int index,int valToConvert);
    public abstract void callStackPush(OmniCollection.OfFloat seq,int valToConvert);
    public abstract boolean callCollectionAdd(OmniCollection.OfFloat seq,int valToConvert);
    public abstract void verifyVal(int expectedValToConvert,float actualVal);
}
