package omni.impl;

import omni.api.OmniDeque;
public interface MonitoredDeque<DEQ extends OmniDeque<?>>extends MonitoredQueue<DEQ>,MonitoredStack<DEQ>{
    MonitoredIterator<?,DEQ> getMonitoredDescendingIterator();
    boolean verifyRemoveFirstOccurrence(QueryVal queryVal,DataType inputType,QueryCastType queryCastType,
            QueryVal.QueryValModification modification);
    boolean verifyRemoveLastOccurrence(QueryVal queryVal,DataType inputType,QueryCastType queryCastType,
            QueryVal.QueryValModification modification);
    void verifyAddFirst(Object inputVal,DataType inputType,boolean boxed);
    void verifyAddLast(Object inputVal,DataType inputType,boolean boxed);
    boolean verifyOfferFirst(Object inputVal,DataType inputType,boolean boxed);
    boolean verifyOfferLast(Object inputVal,DataType inputType,boolean boxed);
    Object verifyPeekFirst(DataType outputType);
    Object verifyPeekLast(DataType outputType);
    Object verifyPollFirst(DataType outputType);
    Object verifyPollLast(DataType outputType);
    Object verifyGetFirst(DataType outputType);
    Object verifyGetLast(DataType outputType);
    Object verifyRemoveFirst(DataType outputType);
    Object verifyRemoveLast(DataType outputType);
}
