package omni.impl;
import omni.util.TypeConversionUtil;
import omni.api.OmniCollection;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.api.OmniQueue;
import omni.api.OmniDeque;
import omni.api.OmniListIterator;
import org.junit.jupiter.api.Assertions;
@SuppressWarnings({"rawtypes","unchecked"}) 
public enum RefInputTestArgType{
  ARRAY_TYPE{
    public void callDequeAddLast(OmniCollection.OfRef seq,int valToConvert){((OmniDeque.OfRef)seq).addLast(TypeConversionUtil.convertToObject(valToConvert));}
    public void callDequeAddFirst(OmniCollection.OfRef seq,int valToConvert){((OmniDeque.OfRef)seq).addFirst(TypeConversionUtil.convertToObject(valToConvert));}
    public boolean callDequeOfferLast(OmniCollection.OfRef seq,int valToConvert){return ((OmniDeque.OfRef)seq).offerLast(TypeConversionUtil.convertToObject(valToConvert));}
    public boolean callDequeOfferFirst(OmniCollection.OfRef seq,int valToConvert){return ((OmniDeque.OfRef)seq).offerFirst(TypeConversionUtil.convertToObject(valToConvert));}
    public boolean callQueueOffer(OmniCollection.OfRef seq,int valToConvert){return ((OmniQueue.OfRef)seq).offer(TypeConversionUtil.convertToObject(valToConvert));}
    public void callListItrAdd(OmniListIterator.OfRef itr,int valToConvert){itr.add(TypeConversionUtil.convertToObject(valToConvert));}
    public void callListItrSet(OmniListIterator.OfRef itr,int valToConvert){itr.set(TypeConversionUtil.convertToObject(valToConvert));}
    public void callListPut(OmniCollection.OfRef seq,int index,int valToConvert){((OmniList.OfRef)seq).put(index,TypeConversionUtil.convertToObject(valToConvert));}
    public void callListAdd(OmniCollection.OfRef seq,int index,int valToConvert){((OmniList.OfRef)seq).add(index,TypeConversionUtil.convertToObject(valToConvert));}
    public void callStackPush(OmniCollection.OfRef seq,int valToConvert){((OmniStack.OfRef)seq).push(TypeConversionUtil.convertToObject(valToConvert));}
    public boolean callCollectionAdd(OmniCollection.OfRef seq,int valToConvert){return seq.add(TypeConversionUtil.convertToObject(valToConvert));}
    public void verifyVal(int expectedValToConvert,Object actualVal){Assertions.assertEquals(TypeConversionUtil.convertToObject(expectedValToConvert),actualVal);}
  }
  ;
    public abstract void callDequeAddLast(OmniCollection.OfRef seq,int valToConvert);
    public abstract void callDequeAddFirst(OmniCollection.OfRef seq,int valToConvert);
    public abstract boolean callDequeOfferLast(OmniCollection.OfRef seq,int valToConvert);
    public abstract boolean callDequeOfferFirst(OmniCollection.OfRef seq,int valToConvert);
    public abstract boolean callQueueOffer(OmniCollection.OfRef seq,int valToConvert);
    public abstract void callListItrAdd(OmniListIterator.OfRef itr,int valToConvert);
    public abstract void callListItrSet(OmniListIterator.OfRef itr,int valToConvert);
    public abstract void callListPut(OmniCollection.OfRef seq,int index,int valToConvert);
    public abstract void callListAdd(OmniCollection.OfRef seq,int index,int valToConvert);
    public abstract void callStackPush(OmniCollection.OfRef seq,int valToConvert);
    public abstract boolean callCollectionAdd(OmniCollection.OfRef seq,int valToConvert);
    public abstract void verifyVal(int expectedValToConvert,Object actualVal);
}
