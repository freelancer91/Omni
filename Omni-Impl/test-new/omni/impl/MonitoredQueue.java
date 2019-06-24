package omni.impl;

import omni.api.OmniQueue;
public interface MonitoredQueue<QUE extends OmniQueue<?>>extends MonitoredCollection<QUE>{
    Object verifyElement(DataType outputType);
    Object verifyRemove(DataType outputType);
    boolean verifyOffer(Object inputVal,DataType inputType,boolean boxed);
}
