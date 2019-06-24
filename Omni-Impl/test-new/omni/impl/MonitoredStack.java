package omni.impl;

import omni.api.OmniStack;
public interface MonitoredStack<STK extends OmniStack<?>>extends MonitoredCollection<STK>{
    void verifyPush(Object inputVal,DataType inputType,boolean boxed);
    Object verifyPop(DataType outputType);
    Object verifyPoll(DataType outputType);
    Object verifyPeek(DataType outputType);
    int verifySearch(QueryVal queryVal,DataType inputType,QueryCastType queryCastType,
            QueryVal.QueryValModification modification);
}
