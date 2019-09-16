package omni.impl;

import omni.api.OmniQueue;
public interface MonitoredQueue<QUE extends OmniQueue<?>>extends MonitoredSequence<QUE>{
    Object verifyElement(DataType outputType);
    Object verifyRemove(DataType outputType);
    boolean verifyOffer(Object inputVal,DataType inputType,FunctionCallType functionCallType);
//    default Object verifyClone(){
//      final Object clone;
//      try{
//          clone=getCollection().clone();
//      }finally{
//          verifyCollectionState();
//      }
//      verifyClone(clone);
//      return clone;
//  }
//    Object verifyPoll(DataType outputType);
//    Object verifyPeek(DataType outputType);
//    
//    
}
