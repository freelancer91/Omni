package omni.impl;
import omni.util.TypeConversionUtil;
import omni.api.OmniCollection;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.api.OmniQueue;
import omni.api.OmniDeque;
import omni.api.OmniListIterator;
import org.junit.jupiter.api.Assertions;
public enum ShortInputTestArgType{
  ARRAY_TYPE{
    public void callDequeAddLast(OmniCollection.OfShort seq,int valToConvert){((OmniDeque.OfShort)seq).addLast(TypeConversionUtil.convertToshort(valToConvert));}
    public void callDequeAddFirst(OmniCollection.OfShort seq,int valToConvert){((OmniDeque.OfShort)seq).addFirst(TypeConversionUtil.convertToshort(valToConvert));}
    public boolean callDequeOfferLast(OmniCollection.OfShort seq,int valToConvert){return ((OmniDeque.OfShort)seq).offerLast(TypeConversionUtil.convertToshort(valToConvert));}
    public boolean callDequeOfferFirst(OmniCollection.OfShort seq,int valToConvert){return ((OmniDeque.OfShort)seq).offerFirst(TypeConversionUtil.convertToshort(valToConvert));}
    public boolean callQueueOffer(OmniCollection.OfShort seq,int valToConvert){return ((OmniQueue.OfShort)seq).offer(TypeConversionUtil.convertToshort(valToConvert));}
    public void callListItrAdd(OmniListIterator.OfShort itr,int valToConvert){itr.add(TypeConversionUtil.convertToshort(valToConvert));}
    public void callListItrSet(OmniListIterator.OfShort itr,int valToConvert){itr.set(TypeConversionUtil.convertToshort(valToConvert));}
    public void callListPut(OmniCollection.OfShort seq,int index,int valToConvert){((OmniList.OfShort)seq).put(index,TypeConversionUtil.convertToshort(valToConvert));}
    public void callListAdd(OmniCollection.OfShort seq,int index,int valToConvert){((OmniList.OfShort)seq).add(index,TypeConversionUtil.convertToshort(valToConvert));}
    public void callStackPush(OmniCollection.OfShort seq,int valToConvert){((OmniStack.OfShort)seq).push(TypeConversionUtil.convertToshort(valToConvert));}
    public boolean callCollectionAdd(OmniCollection.OfShort seq,int valToConvert){return seq.add(TypeConversionUtil.convertToshort(valToConvert));}
    public void verifyVal(int expectedValToConvert,short actualVal){Assertions.assertEquals(TypeConversionUtil.convertToshort(expectedValToConvert),actualVal);}
  }
  ,
  BOXED_TYPE{
    public void callDequeAddLast(OmniCollection.OfShort seq,int valToConvert){((OmniDeque.OfShort)seq).addLast(TypeConversionUtil.convertToShort(valToConvert));}
    public void callDequeAddFirst(OmniCollection.OfShort seq,int valToConvert){((OmniDeque.OfShort)seq).addFirst(TypeConversionUtil.convertToShort(valToConvert));}
    public boolean callDequeOfferLast(OmniCollection.OfShort seq,int valToConvert){return ((OmniDeque.OfShort)seq).offerLast(TypeConversionUtil.convertToShort(valToConvert));}
    public boolean callDequeOfferFirst(OmniCollection.OfShort seq,int valToConvert){return ((OmniDeque.OfShort)seq).offerFirst(TypeConversionUtil.convertToShort(valToConvert));}
    public boolean callQueueOffer(OmniCollection.OfShort seq,int valToConvert){return ((OmniQueue.OfShort)seq).offer(TypeConversionUtil.convertToShort(valToConvert));}
    public void callListItrAdd(OmniListIterator.OfShort itr,int valToConvert){itr.add(TypeConversionUtil.convertToShort(valToConvert));}
    public void callListItrSet(OmniListIterator.OfShort itr,int valToConvert){itr.set(TypeConversionUtil.convertToShort(valToConvert));}
    public void callListPut(OmniCollection.OfShort seq,int index,int valToConvert){((OmniList.OfShort)seq).put(index,TypeConversionUtil.convertToShort(valToConvert));}
    public void callListAdd(OmniCollection.OfShort seq,int index,int valToConvert){((OmniList.OfShort)seq).add(index,TypeConversionUtil.convertToShort(valToConvert));}
    public void callStackPush(OmniCollection.OfShort seq,int valToConvert){((OmniStack.OfShort)seq).push(TypeConversionUtil.convertToShort(valToConvert));}
    public boolean callCollectionAdd(OmniCollection.OfShort seq,int valToConvert){return seq.add(TypeConversionUtil.convertToShort(valToConvert));}
    public void verifyVal(int expectedValToConvert,short actualVal){Assertions.assertEquals(TypeConversionUtil.convertToshort(expectedValToConvert),actualVal);}
  }
  ,
  PRIMITIVE_BOOLEAN{
    public void callDequeAddLast(OmniCollection.OfShort seq,int valToConvert){((OmniDeque.OfShort)seq).addLast(TypeConversionUtil.convertToboolean(valToConvert));}
    public void callDequeAddFirst(OmniCollection.OfShort seq,int valToConvert){((OmniDeque.OfShort)seq).addFirst(TypeConversionUtil.convertToboolean(valToConvert));}
    public boolean callDequeOfferLast(OmniCollection.OfShort seq,int valToConvert){return ((OmniDeque.OfShort)seq).offerLast(TypeConversionUtil.convertToboolean(valToConvert));}
    public boolean callDequeOfferFirst(OmniCollection.OfShort seq,int valToConvert){return ((OmniDeque.OfShort)seq).offerFirst(TypeConversionUtil.convertToboolean(valToConvert));}
    public boolean callQueueOffer(OmniCollection.OfShort seq,int valToConvert){return ((OmniQueue.OfShort)seq).offer(TypeConversionUtil.convertToboolean(valToConvert));}
    public void callListItrAdd(OmniListIterator.OfShort itr,int valToConvert){itr.add(TypeConversionUtil.convertToboolean(valToConvert));}
    public void callListItrSet(OmniListIterator.OfShort itr,int valToConvert){itr.set(TypeConversionUtil.convertToboolean(valToConvert));}
    public void callListPut(OmniCollection.OfShort seq,int index,int valToConvert){((OmniList.OfShort)seq).put(index,TypeConversionUtil.convertToboolean(valToConvert));}
    public void callListAdd(OmniCollection.OfShort seq,int index,int valToConvert){((OmniList.OfShort)seq).add(index,TypeConversionUtil.convertToboolean(valToConvert));}
    public void callStackPush(OmniCollection.OfShort seq,int valToConvert){((OmniStack.OfShort)seq).push(TypeConversionUtil.convertToboolean(valToConvert));}
    public boolean callCollectionAdd(OmniCollection.OfShort seq,int valToConvert){return seq.add(TypeConversionUtil.convertToboolean(valToConvert));}
    public void verifyVal(int expectedValToConvert,short actualVal){Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(expectedValToConvert),actualVal);}
  }
  ,
  BOXED_BOOLEAN{
    public void callDequeAddLast(OmniCollection.OfShort seq,int valToConvert){((OmniDeque.OfShort)seq).addLast(TypeConversionUtil.convertToBoolean(valToConvert));}
    public void callDequeAddFirst(OmniCollection.OfShort seq,int valToConvert){((OmniDeque.OfShort)seq).addFirst(TypeConversionUtil.convertToBoolean(valToConvert));}
    public boolean callDequeOfferLast(OmniCollection.OfShort seq,int valToConvert){return ((OmniDeque.OfShort)seq).offerLast(TypeConversionUtil.convertToBoolean(valToConvert));}
    public boolean callDequeOfferFirst(OmniCollection.OfShort seq,int valToConvert){return ((OmniDeque.OfShort)seq).offerFirst(TypeConversionUtil.convertToBoolean(valToConvert));}
    public boolean callQueueOffer(OmniCollection.OfShort seq,int valToConvert){return ((OmniQueue.OfShort)seq).offer(TypeConversionUtil.convertToBoolean(valToConvert));}
    public void callListItrAdd(OmniListIterator.OfShort itr,int valToConvert){itr.add(TypeConversionUtil.convertToBoolean(valToConvert));}
    public void callListItrSet(OmniListIterator.OfShort itr,int valToConvert){itr.set(TypeConversionUtil.convertToBoolean(valToConvert));}
    public void callListPut(OmniCollection.OfShort seq,int index,int valToConvert){((OmniList.OfShort)seq).put(index,TypeConversionUtil.convertToBoolean(valToConvert));}
    public void callListAdd(OmniCollection.OfShort seq,int index,int valToConvert){((OmniList.OfShort)seq).add(index,TypeConversionUtil.convertToBoolean(valToConvert));}
    public void callStackPush(OmniCollection.OfShort seq,int valToConvert){((OmniStack.OfShort)seq).push(TypeConversionUtil.convertToBoolean(valToConvert));}
    public boolean callCollectionAdd(OmniCollection.OfShort seq,int valToConvert){return seq.add(TypeConversionUtil.convertToBoolean(valToConvert));}
    public void verifyVal(int expectedValToConvert,short actualVal){Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(expectedValToConvert),actualVal);}
  }
  ,
  PRIMITIVE_BYTE{
    public void callDequeAddLast(OmniCollection.OfShort seq,int valToConvert){((OmniDeque.OfShort)seq).addLast(TypeConversionUtil.convertTobyte(valToConvert));}
    public void callDequeAddFirst(OmniCollection.OfShort seq,int valToConvert){((OmniDeque.OfShort)seq).addFirst(TypeConversionUtil.convertTobyte(valToConvert));}
    public boolean callDequeOfferLast(OmniCollection.OfShort seq,int valToConvert){return ((OmniDeque.OfShort)seq).offerLast(TypeConversionUtil.convertTobyte(valToConvert));}
    public boolean callDequeOfferFirst(OmniCollection.OfShort seq,int valToConvert){return ((OmniDeque.OfShort)seq).offerFirst(TypeConversionUtil.convertTobyte(valToConvert));}
    public boolean callQueueOffer(OmniCollection.OfShort seq,int valToConvert){return ((OmniQueue.OfShort)seq).offer(TypeConversionUtil.convertTobyte(valToConvert));}
    public void callListItrAdd(OmniListIterator.OfShort itr,int valToConvert){itr.add(TypeConversionUtil.convertTobyte(valToConvert));}
    public void callListItrSet(OmniListIterator.OfShort itr,int valToConvert){itr.set(TypeConversionUtil.convertTobyte(valToConvert));}
    public void callListPut(OmniCollection.OfShort seq,int index,int valToConvert){((OmniList.OfShort)seq).put(index,TypeConversionUtil.convertTobyte(valToConvert));}
    public void callListAdd(OmniCollection.OfShort seq,int index,int valToConvert){((OmniList.OfShort)seq).add(index,TypeConversionUtil.convertTobyte(valToConvert));}
    public void callStackPush(OmniCollection.OfShort seq,int valToConvert){((OmniStack.OfShort)seq).push(TypeConversionUtil.convertTobyte(valToConvert));}
    public boolean callCollectionAdd(OmniCollection.OfShort seq,int valToConvert){return seq.add(TypeConversionUtil.convertTobyte(valToConvert));}
    public void verifyVal(int expectedValToConvert,short actualVal){Assertions.assertEquals(TypeConversionUtil.convertToshortbyte(expectedValToConvert),actualVal);}
  }
  ,
  BOXED_BYTE{
    public void callDequeAddLast(OmniCollection.OfShort seq,int valToConvert){((OmniDeque.OfShort)seq).addLast(TypeConversionUtil.convertToByte(valToConvert));}
    public void callDequeAddFirst(OmniCollection.OfShort seq,int valToConvert){((OmniDeque.OfShort)seq).addFirst(TypeConversionUtil.convertToByte(valToConvert));}
    public boolean callDequeOfferLast(OmniCollection.OfShort seq,int valToConvert){return ((OmniDeque.OfShort)seq).offerLast(TypeConversionUtil.convertToByte(valToConvert));}
    public boolean callDequeOfferFirst(OmniCollection.OfShort seq,int valToConvert){return ((OmniDeque.OfShort)seq).offerFirst(TypeConversionUtil.convertToByte(valToConvert));}
    public boolean callQueueOffer(OmniCollection.OfShort seq,int valToConvert){return ((OmniQueue.OfShort)seq).offer(TypeConversionUtil.convertToByte(valToConvert));}
    public void callListItrAdd(OmniListIterator.OfShort itr,int valToConvert){itr.add(TypeConversionUtil.convertToByte(valToConvert));}
    public void callListItrSet(OmniListIterator.OfShort itr,int valToConvert){itr.set(TypeConversionUtil.convertToByte(valToConvert));}
    public void callListPut(OmniCollection.OfShort seq,int index,int valToConvert){((OmniList.OfShort)seq).put(index,TypeConversionUtil.convertToByte(valToConvert));}
    public void callListAdd(OmniCollection.OfShort seq,int index,int valToConvert){((OmniList.OfShort)seq).add(index,TypeConversionUtil.convertToByte(valToConvert));}
    public void callStackPush(OmniCollection.OfShort seq,int valToConvert){((OmniStack.OfShort)seq).push(TypeConversionUtil.convertToByte(valToConvert));}
    public boolean callCollectionAdd(OmniCollection.OfShort seq,int valToConvert){return seq.add(TypeConversionUtil.convertToByte(valToConvert));}
    public void verifyVal(int expectedValToConvert,short actualVal){Assertions.assertEquals(TypeConversionUtil.convertToshortbyte(expectedValToConvert),actualVal);}
  }
  ;
    public abstract void callDequeAddLast(OmniCollection.OfShort seq,int valToConvert);
    public abstract void callDequeAddFirst(OmniCollection.OfShort seq,int valToConvert);
    public abstract boolean callDequeOfferLast(OmniCollection.OfShort seq,int valToConvert);
    public abstract boolean callDequeOfferFirst(OmniCollection.OfShort seq,int valToConvert);
    public abstract boolean callQueueOffer(OmniCollection.OfShort seq,int valToConvert);
    public abstract void callListItrAdd(OmniListIterator.OfShort itr,int valToConvert);
    public abstract void callListItrSet(OmniListIterator.OfShort itr,int valToConvert);
    public abstract void callListPut(OmniCollection.OfShort seq,int index,int valToConvert);
    public abstract void callListAdd(OmniCollection.OfShort seq,int index,int valToConvert);
    public abstract void callStackPush(OmniCollection.OfShort seq,int valToConvert);
    public abstract boolean callCollectionAdd(OmniCollection.OfShort seq,int valToConvert);
    public abstract void verifyVal(int expectedValToConvert,short actualVal);
}
