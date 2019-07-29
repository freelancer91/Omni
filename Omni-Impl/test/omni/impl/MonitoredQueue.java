package omni.impl;

import omni.api.OmniQueue;
public interface MonitoredQueue<QUE extends OmniQueue<?>>extends MonitoredSequence<QUE>{
    Object verifyElement(DataType outputType);
    Object verifyRemove(DataType outputType);
    boolean verifyOffer(Object inputVal,DataType inputType,FunctionCallType functionCallType);
//    Object verifyPoll(DataType outputType);
//    Object verifyPeek(DataType outputType);
//    
//    
}
