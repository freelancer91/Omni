package omni.impl;

import omni.api.OmniList;
import omni.api.OmniListIterator;
public interface MonitoredList<ITR extends OmniListIterator<?>,LST extends OmniList<?>>extends MonitoredCollection<LST>{
    interface MonitoredListIterator<ITR extends OmniListIterator<?>,LST extends OmniList<?>>extends MonitoredIterator<ITR,LST>{
        @Override
        MonitoredList<ITR,LST> getMonitoredCollection();
        @Override
        ITR getIterator();
        void iterateReverse();
        boolean hasPrevious();
        Object verifyPrevious(DataType outputType);
        boolean verifyHasPrevious();
        int nextIndex();
        int previousIndex();
        int verifyNextIndex();
        int verifyPreviousIndex();
        void verifyAdd(Object inputVal,DataType inputType,boolean boxed);
    }
    MonitoredListIterator<ITR,LST> getMonitoredListIterator();
    MonitoredListIterator<ITR,LST> getMonitoredListIterator(int index);
    Object verifyGet(int index,DataType outputType);
    Object verifySet(Object inputVal,DataType inputType,boolean boxed);
    void verifyPut(Object inputVal,DataType inputType,boolean boxed);
    void verifyAdd(int index,Object inputVal,DataType inputType,boolean boxed);
    Object verifyRemoveAtIndex(int index,DataType outputType);
    int verifyIndexOf(QueryVal queryVal,DataType inputType,QueryCastType queryCastType,
            QueryVal.QueryValModification modification);
    int verifyLastIndexOf(QueryVal queryVal,DataType inputType,QueryCastType queryCastType,
            QueryVal.QueryValModification modification);
    OmniList<?> verifySubList(int fromIndex,int toIndex);
    void verifyReplaceAll(MonitoredFunctionGen functionGen,FunctionCallType functionCallType);
    // verify sort
}