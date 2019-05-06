package omni.impl;
import omni.util.TypeConversionUtil;
import omni.api.OmniCollection;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.api.OmniQueue;
import omni.api.OmniDeque;
import omni.api.OmniListIterator;
import org.junit.jupiter.api.Assertions;
public enum IntInputTestArgType{
  ARRAY_TYPE{
    public void callDequeAddLast(OmniCollection.OfInt seq,int valToConvert){((OmniDeque.OfInt)seq).addLast(TypeConversionUtil.convertToint(valToConvert));}
    public void callDequeAddFirst(OmniCollection.OfInt seq,int valToConvert){((OmniDeque.OfInt)seq).addFirst(TypeConversionUtil.convertToint(valToConvert));}
    public boolean callDequeOfferLast(OmniCollection.OfInt seq,int valToConvert){return ((OmniDeque.OfInt)seq).offerLast(TypeConversionUtil.convertToint(valToConvert));}
    public boolean callDequeOfferFirst(OmniCollection.OfInt seq,int valToConvert){return ((OmniDeque.OfInt)seq).offerFirst(TypeConversionUtil.convertToint(valToConvert));}
    public boolean callQueueOffer(OmniCollection.OfInt seq,int valToConvert){return ((OmniQueue.OfInt)seq).offer(TypeConversionUtil.convertToint(valToConvert));}
    public void callListItrAdd(OmniListIterator.OfInt itr,int valToConvert){itr.add(TypeConversionUtil.convertToint(valToConvert));}
    public void callListItrSet(OmniListIterator.OfInt itr,int valToConvert){itr.set(TypeConversionUtil.convertToint(valToConvert));}
    public void callListPut(OmniCollection.OfInt seq,int index,int valToConvert){((OmniList.OfInt)seq).put(index,TypeConversionUtil.convertToint(valToConvert));}
    public void callListAdd(OmniCollection.OfInt seq,int index,int valToConvert){((OmniList.OfInt)seq).add(index,TypeConversionUtil.convertToint(valToConvert));}
    public void callStackPush(OmniCollection.OfInt seq,int valToConvert){((OmniStack.OfInt)seq).push(TypeConversionUtil.convertToint(valToConvert));}
    public boolean callCollectionAdd(OmniCollection.OfInt seq,int valToConvert){return seq.add(TypeConversionUtil.convertToint(valToConvert));}
    public void verifyVal(int expectedValToConvert,int actualVal){Assertions.assertEquals(TypeConversionUtil.convertToint(expectedValToConvert),actualVal);}
  }
  ,
  BOXED_TYPE{
    public void callDequeAddLast(OmniCollection.OfInt seq,int valToConvert){((OmniDeque.OfInt)seq).addLast(TypeConversionUtil.convertToInteger(valToConvert));}
    public void callDequeAddFirst(OmniCollection.OfInt seq,int valToConvert){((OmniDeque.OfInt)seq).addFirst(TypeConversionUtil.convertToInteger(valToConvert));}
    public boolean callDequeOfferLast(OmniCollection.OfInt seq,int valToConvert){return ((OmniDeque.OfInt)seq).offerLast(TypeConversionUtil.convertToInteger(valToConvert));}
    public boolean callDequeOfferFirst(OmniCollection.OfInt seq,int valToConvert){return ((OmniDeque.OfInt)seq).offerFirst(TypeConversionUtil.convertToInteger(valToConvert));}
    public boolean callQueueOffer(OmniCollection.OfInt seq,int valToConvert){return ((OmniQueue.OfInt)seq).offer(TypeConversionUtil.convertToInteger(valToConvert));}
    public void callListItrAdd(OmniListIterator.OfInt itr,int valToConvert){itr.add(TypeConversionUtil.convertToInteger(valToConvert));}
    public void callListItrSet(OmniListIterator.OfInt itr,int valToConvert){itr.set(TypeConversionUtil.convertToInteger(valToConvert));}
    public void callListPut(OmniCollection.OfInt seq,int index,int valToConvert){((OmniList.OfInt)seq).put(index,TypeConversionUtil.convertToInteger(valToConvert));}
    public void callListAdd(OmniCollection.OfInt seq,int index,int valToConvert){((OmniList.OfInt)seq).add(index,TypeConversionUtil.convertToInteger(valToConvert));}
    public void callStackPush(OmniCollection.OfInt seq,int valToConvert){((OmniStack.OfInt)seq).push(TypeConversionUtil.convertToInteger(valToConvert));}
    public boolean callCollectionAdd(OmniCollection.OfInt seq,int valToConvert){return seq.add(TypeConversionUtil.convertToInteger(valToConvert));}
    public void verifyVal(int expectedValToConvert,int actualVal){Assertions.assertEquals(TypeConversionUtil.convertToint(expectedValToConvert),actualVal);}
  }
  ,
  PRIMITIVE_BOOLEAN{
    public void callDequeAddLast(OmniCollection.OfInt seq,int valToConvert){((OmniDeque.OfInt)seq).addLast(TypeConversionUtil.convertToboolean(valToConvert));}
    public void callDequeAddFirst(OmniCollection.OfInt seq,int valToConvert){((OmniDeque.OfInt)seq).addFirst(TypeConversionUtil.convertToboolean(valToConvert));}
    public boolean callDequeOfferLast(OmniCollection.OfInt seq,int valToConvert){return ((OmniDeque.OfInt)seq).offerLast(TypeConversionUtil.convertToboolean(valToConvert));}
    public boolean callDequeOfferFirst(OmniCollection.OfInt seq,int valToConvert){return ((OmniDeque.OfInt)seq).offerFirst(TypeConversionUtil.convertToboolean(valToConvert));}
    public boolean callQueueOffer(OmniCollection.OfInt seq,int valToConvert){return ((OmniQueue.OfInt)seq).offer(TypeConversionUtil.convertToboolean(valToConvert));}
    public void callListItrAdd(OmniListIterator.OfInt itr,int valToConvert){itr.add(TypeConversionUtil.convertToboolean(valToConvert));}
    public void callListItrSet(OmniListIterator.OfInt itr,int valToConvert){itr.set(TypeConversionUtil.convertToboolean(valToConvert));}
    public void callListPut(OmniCollection.OfInt seq,int index,int valToConvert){((OmniList.OfInt)seq).put(index,TypeConversionUtil.convertToboolean(valToConvert));}
    public void callListAdd(OmniCollection.OfInt seq,int index,int valToConvert){((OmniList.OfInt)seq).add(index,TypeConversionUtil.convertToboolean(valToConvert));}
    public void callStackPush(OmniCollection.OfInt seq,int valToConvert){((OmniStack.OfInt)seq).push(TypeConversionUtil.convertToboolean(valToConvert));}
    public boolean callCollectionAdd(OmniCollection.OfInt seq,int valToConvert){return seq.add(TypeConversionUtil.convertToboolean(valToConvert));}
    public void verifyVal(int expectedValToConvert,int actualVal){Assertions.assertEquals(TypeConversionUtil.convertTointboolean(expectedValToConvert),actualVal);}
  }
  ,
  BOXED_BOOLEAN{
    public void callDequeAddLast(OmniCollection.OfInt seq,int valToConvert){((OmniDeque.OfInt)seq).addLast(TypeConversionUtil.convertToBoolean(valToConvert));}
    public void callDequeAddFirst(OmniCollection.OfInt seq,int valToConvert){((OmniDeque.OfInt)seq).addFirst(TypeConversionUtil.convertToBoolean(valToConvert));}
    public boolean callDequeOfferLast(OmniCollection.OfInt seq,int valToConvert){return ((OmniDeque.OfInt)seq).offerLast(TypeConversionUtil.convertToBoolean(valToConvert));}
    public boolean callDequeOfferFirst(OmniCollection.OfInt seq,int valToConvert){return ((OmniDeque.OfInt)seq).offerFirst(TypeConversionUtil.convertToBoolean(valToConvert));}
    public boolean callQueueOffer(OmniCollection.OfInt seq,int valToConvert){return ((OmniQueue.OfInt)seq).offer(TypeConversionUtil.convertToBoolean(valToConvert));}
    public void callListItrAdd(OmniListIterator.OfInt itr,int valToConvert){itr.add(TypeConversionUtil.convertToBoolean(valToConvert));}
    public void callListItrSet(OmniListIterator.OfInt itr,int valToConvert){itr.set(TypeConversionUtil.convertToBoolean(valToConvert));}
    public void callListPut(OmniCollection.OfInt seq,int index,int valToConvert){((OmniList.OfInt)seq).put(index,TypeConversionUtil.convertToBoolean(valToConvert));}
    public void callListAdd(OmniCollection.OfInt seq,int index,int valToConvert){((OmniList.OfInt)seq).add(index,TypeConversionUtil.convertToBoolean(valToConvert));}
    public void callStackPush(OmniCollection.OfInt seq,int valToConvert){((OmniStack.OfInt)seq).push(TypeConversionUtil.convertToBoolean(valToConvert));}
    public boolean callCollectionAdd(OmniCollection.OfInt seq,int valToConvert){return seq.add(TypeConversionUtil.convertToBoolean(valToConvert));}
    public void verifyVal(int expectedValToConvert,int actualVal){Assertions.assertEquals(TypeConversionUtil.convertTointboolean(expectedValToConvert),actualVal);}
  }
  ,
  PRIMITIVE_BYTE{
    public void callDequeAddLast(OmniCollection.OfInt seq,int valToConvert){((OmniDeque.OfInt)seq).addLast(TypeConversionUtil.convertTobyte(valToConvert));}
    public void callDequeAddFirst(OmniCollection.OfInt seq,int valToConvert){((OmniDeque.OfInt)seq).addFirst(TypeConversionUtil.convertTobyte(valToConvert));}
    public boolean callDequeOfferLast(OmniCollection.OfInt seq,int valToConvert){return ((OmniDeque.OfInt)seq).offerLast(TypeConversionUtil.convertTobyte(valToConvert));}
    public boolean callDequeOfferFirst(OmniCollection.OfInt seq,int valToConvert){return ((OmniDeque.OfInt)seq).offerFirst(TypeConversionUtil.convertTobyte(valToConvert));}
    public boolean callQueueOffer(OmniCollection.OfInt seq,int valToConvert){return ((OmniQueue.OfInt)seq).offer(TypeConversionUtil.convertTobyte(valToConvert));}
    public void callListItrAdd(OmniListIterator.OfInt itr,int valToConvert){itr.add(TypeConversionUtil.convertTobyte(valToConvert));}
    public void callListItrSet(OmniListIterator.OfInt itr,int valToConvert){itr.set(TypeConversionUtil.convertTobyte(valToConvert));}
    public void callListPut(OmniCollection.OfInt seq,int index,int valToConvert){((OmniList.OfInt)seq).put(index,TypeConversionUtil.convertTobyte(valToConvert));}
    public void callListAdd(OmniCollection.OfInt seq,int index,int valToConvert){((OmniList.OfInt)seq).add(index,TypeConversionUtil.convertTobyte(valToConvert));}
    public void callStackPush(OmniCollection.OfInt seq,int valToConvert){((OmniStack.OfInt)seq).push(TypeConversionUtil.convertTobyte(valToConvert));}
    public boolean callCollectionAdd(OmniCollection.OfInt seq,int valToConvert){return seq.add(TypeConversionUtil.convertTobyte(valToConvert));}
    public void verifyVal(int expectedValToConvert,int actualVal){Assertions.assertEquals(TypeConversionUtil.convertTointbyte(expectedValToConvert),actualVal);}
  }
  ,
  BOXED_BYTE{
    public void callDequeAddLast(OmniCollection.OfInt seq,int valToConvert){((OmniDeque.OfInt)seq).addLast(TypeConversionUtil.convertToByte(valToConvert));}
    public void callDequeAddFirst(OmniCollection.OfInt seq,int valToConvert){((OmniDeque.OfInt)seq).addFirst(TypeConversionUtil.convertToByte(valToConvert));}
    public boolean callDequeOfferLast(OmniCollection.OfInt seq,int valToConvert){return ((OmniDeque.OfInt)seq).offerLast(TypeConversionUtil.convertToByte(valToConvert));}
    public boolean callDequeOfferFirst(OmniCollection.OfInt seq,int valToConvert){return ((OmniDeque.OfInt)seq).offerFirst(TypeConversionUtil.convertToByte(valToConvert));}
    public boolean callQueueOffer(OmniCollection.OfInt seq,int valToConvert){return ((OmniQueue.OfInt)seq).offer(TypeConversionUtil.convertToByte(valToConvert));}
    public void callListItrAdd(OmniListIterator.OfInt itr,int valToConvert){itr.add(TypeConversionUtil.convertToByte(valToConvert));}
    public void callListItrSet(OmniListIterator.OfInt itr,int valToConvert){itr.set(TypeConversionUtil.convertToByte(valToConvert));}
    public void callListPut(OmniCollection.OfInt seq,int index,int valToConvert){((OmniList.OfInt)seq).put(index,TypeConversionUtil.convertToByte(valToConvert));}
    public void callListAdd(OmniCollection.OfInt seq,int index,int valToConvert){((OmniList.OfInt)seq).add(index,TypeConversionUtil.convertToByte(valToConvert));}
    public void callStackPush(OmniCollection.OfInt seq,int valToConvert){((OmniStack.OfInt)seq).push(TypeConversionUtil.convertToByte(valToConvert));}
    public boolean callCollectionAdd(OmniCollection.OfInt seq,int valToConvert){return seq.add(TypeConversionUtil.convertToByte(valToConvert));}
    public void verifyVal(int expectedValToConvert,int actualVal){Assertions.assertEquals(TypeConversionUtil.convertTointbyte(expectedValToConvert),actualVal);}
  }
  ,
  PRIMTIVE_SHORT{
    public void callDequeAddLast(OmniCollection.OfInt seq,int valToConvert){((OmniDeque.OfInt)seq).addLast(TypeConversionUtil.convertToshort(valToConvert));}
    public void callDequeAddFirst(OmniCollection.OfInt seq,int valToConvert){((OmniDeque.OfInt)seq).addFirst(TypeConversionUtil.convertToshort(valToConvert));}
    public boolean callDequeOfferLast(OmniCollection.OfInt seq,int valToConvert){return ((OmniDeque.OfInt)seq).offerLast(TypeConversionUtil.convertToshort(valToConvert));}
    public boolean callDequeOfferFirst(OmniCollection.OfInt seq,int valToConvert){return ((OmniDeque.OfInt)seq).offerFirst(TypeConversionUtil.convertToshort(valToConvert));}
    public boolean callQueueOffer(OmniCollection.OfInt seq,int valToConvert){return ((OmniQueue.OfInt)seq).offer(TypeConversionUtil.convertToshort(valToConvert));}
    public void callListItrAdd(OmniListIterator.OfInt itr,int valToConvert){itr.add(TypeConversionUtil.convertToshort(valToConvert));}
    public void callListItrSet(OmniListIterator.OfInt itr,int valToConvert){itr.set(TypeConversionUtil.convertToshort(valToConvert));}
    public void callListPut(OmniCollection.OfInt seq,int index,int valToConvert){((OmniList.OfInt)seq).put(index,TypeConversionUtil.convertToshort(valToConvert));}
    public void callListAdd(OmniCollection.OfInt seq,int index,int valToConvert){((OmniList.OfInt)seq).add(index,TypeConversionUtil.convertToshort(valToConvert));}
    public void callStackPush(OmniCollection.OfInt seq,int valToConvert){((OmniStack.OfInt)seq).push(TypeConversionUtil.convertToshort(valToConvert));}
    public boolean callCollectionAdd(OmniCollection.OfInt seq,int valToConvert){return seq.add(TypeConversionUtil.convertToshort(valToConvert));}
    public void verifyVal(int expectedValToConvert,int actualVal){Assertions.assertEquals(TypeConversionUtil.convertToint(expectedValToConvert),actualVal);}
  }
  ,
  BOXED_SHORT{
    public void callDequeAddLast(OmniCollection.OfInt seq,int valToConvert){((OmniDeque.OfInt)seq).addLast(TypeConversionUtil.convertToShort(valToConvert));}
    public void callDequeAddFirst(OmniCollection.OfInt seq,int valToConvert){((OmniDeque.OfInt)seq).addFirst(TypeConversionUtil.convertToShort(valToConvert));}
    public boolean callDequeOfferLast(OmniCollection.OfInt seq,int valToConvert){return ((OmniDeque.OfInt)seq).offerLast(TypeConversionUtil.convertToShort(valToConvert));}
    public boolean callDequeOfferFirst(OmniCollection.OfInt seq,int valToConvert){return ((OmniDeque.OfInt)seq).offerFirst(TypeConversionUtil.convertToShort(valToConvert));}
    public boolean callQueueOffer(OmniCollection.OfInt seq,int valToConvert){return ((OmniQueue.OfInt)seq).offer(TypeConversionUtil.convertToShort(valToConvert));}
    public void callListItrAdd(OmniListIterator.OfInt itr,int valToConvert){itr.add(TypeConversionUtil.convertToShort(valToConvert));}
    public void callListItrSet(OmniListIterator.OfInt itr,int valToConvert){itr.set(TypeConversionUtil.convertToShort(valToConvert));}
    public void callListPut(OmniCollection.OfInt seq,int index,int valToConvert){((OmniList.OfInt)seq).put(index,TypeConversionUtil.convertToShort(valToConvert));}
    public void callListAdd(OmniCollection.OfInt seq,int index,int valToConvert){((OmniList.OfInt)seq).add(index,TypeConversionUtil.convertToShort(valToConvert));}
    public void callStackPush(OmniCollection.OfInt seq,int valToConvert){((OmniStack.OfInt)seq).push(TypeConversionUtil.convertToShort(valToConvert));}
    public boolean callCollectionAdd(OmniCollection.OfInt seq,int valToConvert){return seq.add(TypeConversionUtil.convertToShort(valToConvert));}
    public void verifyVal(int expectedValToConvert,int actualVal){Assertions.assertEquals(TypeConversionUtil.convertTointshort(expectedValToConvert),actualVal);}
  }
  ,
  PRIMITIVE_CHAR{
    public void callDequeAddLast(OmniCollection.OfInt seq,int valToConvert){((OmniDeque.OfInt)seq).addLast(TypeConversionUtil.convertTochar(valToConvert));}
    public void callDequeAddFirst(OmniCollection.OfInt seq,int valToConvert){((OmniDeque.OfInt)seq).addFirst(TypeConversionUtil.convertTochar(valToConvert));}
    public boolean callDequeOfferLast(OmniCollection.OfInt seq,int valToConvert){return ((OmniDeque.OfInt)seq).offerLast(TypeConversionUtil.convertTochar(valToConvert));}
    public boolean callDequeOfferFirst(OmniCollection.OfInt seq,int valToConvert){return ((OmniDeque.OfInt)seq).offerFirst(TypeConversionUtil.convertTochar(valToConvert));}
    public boolean callQueueOffer(OmniCollection.OfInt seq,int valToConvert){return ((OmniQueue.OfInt)seq).offer(TypeConversionUtil.convertTochar(valToConvert));}
    public void callListItrAdd(OmniListIterator.OfInt itr,int valToConvert){itr.add(TypeConversionUtil.convertTochar(valToConvert));}
    public void callListItrSet(OmniListIterator.OfInt itr,int valToConvert){itr.set(TypeConversionUtil.convertTochar(valToConvert));}
    public void callListPut(OmniCollection.OfInt seq,int index,int valToConvert){((OmniList.OfInt)seq).put(index,TypeConversionUtil.convertTochar(valToConvert));}
    public void callListAdd(OmniCollection.OfInt seq,int index,int valToConvert){((OmniList.OfInt)seq).add(index,TypeConversionUtil.convertTochar(valToConvert));}
    public void callStackPush(OmniCollection.OfInt seq,int valToConvert){((OmniStack.OfInt)seq).push(TypeConversionUtil.convertTochar(valToConvert));}
    public boolean callCollectionAdd(OmniCollection.OfInt seq,int valToConvert){return seq.add(TypeConversionUtil.convertTochar(valToConvert));}
    public void verifyVal(int expectedValToConvert,int actualVal){Assertions.assertEquals(TypeConversionUtil.convertTointchar(expectedValToConvert),actualVal);}
  }
  ,
  BOXED_CHAR{
    public void callDequeAddLast(OmniCollection.OfInt seq,int valToConvert){((OmniDeque.OfInt)seq).addLast(TypeConversionUtil.convertToCharacter(valToConvert));}
    public void callDequeAddFirst(OmniCollection.OfInt seq,int valToConvert){((OmniDeque.OfInt)seq).addFirst(TypeConversionUtil.convertToCharacter(valToConvert));}
    public boolean callDequeOfferLast(OmniCollection.OfInt seq,int valToConvert){return ((OmniDeque.OfInt)seq).offerLast(TypeConversionUtil.convertToCharacter(valToConvert));}
    public boolean callDequeOfferFirst(OmniCollection.OfInt seq,int valToConvert){return ((OmniDeque.OfInt)seq).offerFirst(TypeConversionUtil.convertToCharacter(valToConvert));}
    public boolean callQueueOffer(OmniCollection.OfInt seq,int valToConvert){return ((OmniQueue.OfInt)seq).offer(TypeConversionUtil.convertToCharacter(valToConvert));}
    public void callListItrAdd(OmniListIterator.OfInt itr,int valToConvert){itr.add(TypeConversionUtil.convertToCharacter(valToConvert));}
    public void callListItrSet(OmniListIterator.OfInt itr,int valToConvert){itr.set(TypeConversionUtil.convertToCharacter(valToConvert));}
    public void callListPut(OmniCollection.OfInt seq,int index,int valToConvert){((OmniList.OfInt)seq).put(index,TypeConversionUtil.convertToCharacter(valToConvert));}
    public void callListAdd(OmniCollection.OfInt seq,int index,int valToConvert){((OmniList.OfInt)seq).add(index,TypeConversionUtil.convertToCharacter(valToConvert));}
    public void callStackPush(OmniCollection.OfInt seq,int valToConvert){((OmniStack.OfInt)seq).push(TypeConversionUtil.convertToCharacter(valToConvert));}
    public boolean callCollectionAdd(OmniCollection.OfInt seq,int valToConvert){return seq.add(TypeConversionUtil.convertToCharacter(valToConvert));}
    public void verifyVal(int expectedValToConvert,int actualVal){Assertions.assertEquals(TypeConversionUtil.convertTointchar(expectedValToConvert),actualVal);}
  }
  ;
    public abstract void callDequeAddLast(OmniCollection.OfInt seq,int valToConvert);
    public abstract void callDequeAddFirst(OmniCollection.OfInt seq,int valToConvert);
    public abstract boolean callDequeOfferLast(OmniCollection.OfInt seq,int valToConvert);
    public abstract boolean callDequeOfferFirst(OmniCollection.OfInt seq,int valToConvert);
    public abstract boolean callQueueOffer(OmniCollection.OfInt seq,int valToConvert);
    public abstract void callListItrAdd(OmniListIterator.OfInt itr,int valToConvert);
    public abstract void callListItrSet(OmniListIterator.OfInt itr,int valToConvert);
    public abstract void callListPut(OmniCollection.OfInt seq,int index,int valToConvert);
    public abstract void callListAdd(OmniCollection.OfInt seq,int index,int valToConvert);
    public abstract void callStackPush(OmniCollection.OfInt seq,int valToConvert);
    public abstract boolean callCollectionAdd(OmniCollection.OfInt seq,int valToConvert);
    public abstract void verifyVal(int expectedValToConvert,int actualVal);
}
