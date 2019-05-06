package omni.impl;
import omni.util.TypeConversionUtil;
import omni.api.OmniCollection;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.api.OmniQueue;
import omni.api.OmniDeque;
import omni.api.OmniListIterator;
import org.junit.jupiter.api.Assertions;
public enum BooleanInputTestArgType{
  ARRAY_TYPE{
    public void callDequeAddLast(OmniCollection.OfBoolean seq,int valToConvert){((OmniDeque.OfBoolean)seq).addLast(TypeConversionUtil.convertToboolean(valToConvert));}
    public void callDequeAddFirst(OmniCollection.OfBoolean seq,int valToConvert){((OmniDeque.OfBoolean)seq).addFirst(TypeConversionUtil.convertToboolean(valToConvert));}
    public boolean callDequeOfferLast(OmniCollection.OfBoolean seq,int valToConvert){return ((OmniDeque.OfBoolean)seq).offerLast(TypeConversionUtil.convertToboolean(valToConvert));}
    public boolean callDequeOfferFirst(OmniCollection.OfBoolean seq,int valToConvert){return ((OmniDeque.OfBoolean)seq).offerFirst(TypeConversionUtil.convertToboolean(valToConvert));}
    public boolean callQueueOffer(OmniCollection.OfBoolean seq,int valToConvert){return ((OmniQueue.OfBoolean)seq).offer(TypeConversionUtil.convertToboolean(valToConvert));}
    public void callListItrAdd(OmniListIterator.OfBoolean itr,int valToConvert){itr.add(TypeConversionUtil.convertToboolean(valToConvert));}
    public void callListItrSet(OmniListIterator.OfBoolean itr,int valToConvert){itr.set(TypeConversionUtil.convertToboolean(valToConvert));}
    public void callListPut(OmniCollection.OfBoolean seq,int index,int valToConvert){((OmniList.OfBoolean)seq).put(index,TypeConversionUtil.convertToboolean(valToConvert));}
    public void callListAdd(OmniCollection.OfBoolean seq,int index,int valToConvert){((OmniList.OfBoolean)seq).add(index,TypeConversionUtil.convertToboolean(valToConvert));}
    public void callStackPush(OmniCollection.OfBoolean seq,int valToConvert){((OmniStack.OfBoolean)seq).push(TypeConversionUtil.convertToboolean(valToConvert));}
    public boolean callCollectionAdd(OmniCollection.OfBoolean seq,int valToConvert){return seq.add(TypeConversionUtil.convertToboolean(valToConvert));}
    public void verifyVal(int expectedValToConvert,boolean actualVal){Assertions.assertEquals(TypeConversionUtil.convertToboolean(expectedValToConvert),actualVal);}
  }
  ,
  BOXED_TYPE{
    public void callDequeAddLast(OmniCollection.OfBoolean seq,int valToConvert){((OmniDeque.OfBoolean)seq).addLast(TypeConversionUtil.convertToBoolean(valToConvert));}
    public void callDequeAddFirst(OmniCollection.OfBoolean seq,int valToConvert){((OmniDeque.OfBoolean)seq).addFirst(TypeConversionUtil.convertToBoolean(valToConvert));}
    public boolean callDequeOfferLast(OmniCollection.OfBoolean seq,int valToConvert){return ((OmniDeque.OfBoolean)seq).offerLast(TypeConversionUtil.convertToBoolean(valToConvert));}
    public boolean callDequeOfferFirst(OmniCollection.OfBoolean seq,int valToConvert){return ((OmniDeque.OfBoolean)seq).offerFirst(TypeConversionUtil.convertToBoolean(valToConvert));}
    public boolean callQueueOffer(OmniCollection.OfBoolean seq,int valToConvert){return ((OmniQueue.OfBoolean)seq).offer(TypeConversionUtil.convertToBoolean(valToConvert));}
    public void callListItrAdd(OmniListIterator.OfBoolean itr,int valToConvert){itr.add(TypeConversionUtil.convertToBoolean(valToConvert));}
    public void callListItrSet(OmniListIterator.OfBoolean itr,int valToConvert){itr.set(TypeConversionUtil.convertToBoolean(valToConvert));}
    public void callListPut(OmniCollection.OfBoolean seq,int index,int valToConvert){((OmniList.OfBoolean)seq).put(index,TypeConversionUtil.convertToBoolean(valToConvert));}
    public void callListAdd(OmniCollection.OfBoolean seq,int index,int valToConvert){((OmniList.OfBoolean)seq).add(index,TypeConversionUtil.convertToBoolean(valToConvert));}
    public void callStackPush(OmniCollection.OfBoolean seq,int valToConvert){((OmniStack.OfBoolean)seq).push(TypeConversionUtil.convertToBoolean(valToConvert));}
    public boolean callCollectionAdd(OmniCollection.OfBoolean seq,int valToConvert){return seq.add(TypeConversionUtil.convertToBoolean(valToConvert));}
    public void verifyVal(int expectedValToConvert,boolean actualVal){Assertions.assertEquals(TypeConversionUtil.convertToboolean(expectedValToConvert),actualVal);}
  }
  ;
    public abstract void callDequeAddLast(OmniCollection.OfBoolean seq,int valToConvert);
    public abstract void callDequeAddFirst(OmniCollection.OfBoolean seq,int valToConvert);
    public abstract boolean callDequeOfferLast(OmniCollection.OfBoolean seq,int valToConvert);
    public abstract boolean callDequeOfferFirst(OmniCollection.OfBoolean seq,int valToConvert);
    public abstract boolean callQueueOffer(OmniCollection.OfBoolean seq,int valToConvert);
    public abstract void callListItrAdd(OmniListIterator.OfBoolean itr,int valToConvert);
    public abstract void callListItrSet(OmniListIterator.OfBoolean itr,int valToConvert);
    public abstract void callListPut(OmniCollection.OfBoolean seq,int index,int valToConvert);
    public abstract void callListAdd(OmniCollection.OfBoolean seq,int index,int valToConvert);
    public abstract void callStackPush(OmniCollection.OfBoolean seq,int valToConvert);
    public abstract boolean callCollectionAdd(OmniCollection.OfBoolean seq,int valToConvert);
    public abstract void verifyVal(int expectedValToConvert,boolean actualVal);
}
