package omni.impl;

import org.junit.jupiter.api.Assertions;
import omni.api.OmniDeque;
public interface MonitoredDeque<DEQ extends OmniDeque<?>>extends MonitoredQueue<DEQ>,MonitoredStack<DEQ>{
    MonitoredIterator<?,DEQ> getMonitoredDescendingIterator();
    default boolean verifyRemoveFirstOccurrence(QueryVal queryVal,DataType inputType,QueryCastType queryCastType,
            QueryVal.QueryValModification modification) {

        var collection=getCollection();
        Object inputVal=queryVal.getInputVal(inputType,modification);
        boolean result=queryCastType.callremoveFirstOccurrence(collection,inputVal,inputType);
        if(result) {
            updateRemoveValState(inputVal,inputType);
        }
        verifyCollectionState();
        return result;
    
    }
    void updateRemoveLastOccurrenceState(Object inputVal,DataType inputType);
    default boolean verifyRemoveLastOccurrence(QueryVal queryVal,DataType inputType,QueryCastType queryCastType,
            QueryVal.QueryValModification modification) {
        var collection=getCollection();
        Object inputVal=queryVal.getInputVal(inputType,modification);
        boolean result=queryCastType.callremoveLastOccurrence(collection,inputVal,inputType);
        if(result) {
            updateRemoveLastOccurrenceState(inputVal,inputType);
        }
        verifyCollectionState();
        return result;
    }
    void updateAddFirstState(Object inputVal,DataType inputType);
    default void verifyAddFirst(Object inputVal,DataType inputType,FunctionCallType functionCallType) {
        inputType.callAddFirst(inputVal,getCollection(),functionCallType);
        updateAddFirstState(inputVal,inputType);
        verifyCollectionState();
    }
    @Override
    default void verifyPush(Object inputVal,DataType inputType,FunctionCallType functionCallType) {
        inputType.callPush(inputVal,getCollection(),functionCallType);
        updateAddFirstState(inputVal,inputType);
        verifyCollectionState();
    }
    default void verifyAddLast(Object inputVal,DataType inputType,FunctionCallType functionCallType) {
        inputType.callAddLast(inputVal,getCollection(),functionCallType);
        updateAddState(inputVal,inputType);
        verifyCollectionState();
    }
    default boolean verifyOfferFirst(Object inputVal,DataType inputType,FunctionCallType functionCallType) {
        boolean result=inputType.callOfferFirst(inputVal,getCollection(),functionCallType);
        if(result) {
            updateAddFirstState(inputVal,inputType);
        }
        verifyCollectionState();
        return result;
    }
    default boolean verifyOfferLast(Object inputVal,DataType inputType,FunctionCallType functionCallType) {
        boolean result=inputType.callOfferLast(inputVal,getCollection(),functionCallType);
        if(result) {
            updateAddState(inputVal,inputType);
        }
        verifyCollectionState();
        return result;
    }
    @Override
    default boolean verifyOffer(Object inputVal,DataType inputType,FunctionCallType functionCallType) {
        boolean result=inputType.callOffer(inputVal,getCollection(),functionCallType);
        if(result) {
            updateAddState(inputVal,inputType);
        }
        verifyCollectionState();
        return result;
    }
    default Object verifyPeekFirst(DataType outputType) {
        Object result;
        boolean isEmpty=this.isEmpty();
        try {
            result=outputType.callPeekFirst(getCollection());
        }finally {
            verifyCollectionState();
        }
        if(isEmpty) {
            Assertions.assertEquals(outputType.defaultVal,result);
        }else {
            verifyGetResult(0,result,outputType);
        }
        return result;
    }
    default Object verifyPeekLast(DataType outputType) {
        Object result;
        int size=size();
        try {
            result=outputType.callPeekLast(getCollection());
        }finally {
            verifyCollectionState();
        }
        if(size==0) {
            Assertions.assertEquals(outputType.defaultVal,result);
        }else {
            verifyGetResult(size-1,result,outputType);
        }
        return result;
    }
    default Object verifyPollFirst(DataType outputType) {
        var collection=getCollection();
        Object result;
        Object expected=outputType.callPeekFirst(collection);
        boolean isEmpty=this.isEmpty();
        try{
            result=outputType.callPollFirst(collection);
            if(!isEmpty) {
              updateRemoveIndexState(0);
            }
            
        }finally{
            verifyCollectionState();
        }
        Assertions.assertEquals(expected,result);
        return result;
    
      }
    default Object verifyPollLast(DataType outputType) {
        var collection=getCollection();
        Object result;
        Object expected=outputType.callPeekLast(collection);
        int size=size();
        try{
            result=outputType.callPollLast(collection);
            if(size!=0) {
              updateRemoveIndexState(size-1);
            }
            
        }finally{
            verifyCollectionState();
        }
        Assertions.assertEquals(expected,result);
        return result;
    
      }
    default Object verifyGetFirst(DataType outputType) {
        Object result;
        Object expected=null;
        var collection=getCollection();
        if(!isEmpty()) {
            expected=outputType.callPeekFirst(collection);
        }
        try {
            result=outputType.callGetFirst(collection);
        }finally {
            verifyCollectionState();
        }
        if(outputType==DataType.REF && getDataType()==DataType.REF) {
            Assertions.assertSame(expected,result);
        }else {
            Assertions.assertEquals(expected,result);
        }
        return result;
    }
    default Object verifyGetLast(DataType outputType) {
        Object result;
        Object expected=null;
        var collection=getCollection();
        if(!isEmpty()) {
            expected=outputType.callPeekLast(collection);
        }
        try {
            result=outputType.callGetLast(collection);
        }finally {
            verifyCollectionState();
        }
        if(outputType==DataType.REF && getDataType()==DataType.REF) {
            Assertions.assertSame(expected,result);
        }else {
            Assertions.assertEquals(expected,result);
        }
        return result;
    }
    default Object verifyRemoveFirst(DataType outputType) {
        Object result;
        Object expected=null;
        var collection=getCollection();
        if(!isEmpty()) {
            expected=outputType.callPeekFirst(collection);
        }
        try {
            result=outputType.callRemoveFirst(collection);
            updateRemoveIndexState(0);
        }finally {
            verifyCollectionState();
        }
        if(outputType==DataType.REF && getDataType()==DataType.REF) {
            Assertions.assertSame(expected,result);
        }else {
            Assertions.assertEquals(expected,result);
        }
        return result;
      }
    @Override
    default Object verifyPop(DataType outputType) {
        Object result;
        Object expected=null;
        var collection=getCollection();
        if(!isEmpty()) {
            expected=outputType.callPeekFirst(collection);
        }
        try {
            result=outputType.callPop(collection);
            updateRemoveIndexState(0);
        }finally {
            verifyCollectionState();
        }
        if(outputType==DataType.REF && getDataType()==DataType.REF) {
            Assertions.assertSame(expected,result);
        }else {
            Assertions.assertEquals(expected,result);
        }
        return result;
      }
    default Object verifyRemoveLast(DataType outputType) {
        Object result;
        Object expected=null;
        var collection=getCollection();
        int size;
        if((size=size())!=0) {
            expected=outputType.callPeekLast(collection);
        }
        try {
            result=outputType.callRemoveLast(collection);
            updateRemoveIndexState(size-1);
        }finally {
            verifyCollectionState();
        }
        if(outputType==DataType.REF && getDataType()==DataType.REF) {
            Assertions.assertSame(expected,result);
        }else {
            Assertions.assertEquals(expected,result);
        }
        return result;
      }
    @Override
    default Object verifyPoll(DataType outputType) {
        var collection=getCollection();
        Object result;
        Object expected=outputType.callPeek(collection);
        boolean isEmpty=this.isEmpty();
        try{
            result=outputType.callPoll(collection);
            if(!isEmpty) {
              updateRemoveIndexState(0);
            }
            
        }finally{
            verifyCollectionState();
        }
        Assertions.assertEquals(expected,result);
        return result;
    
      }
    @Override
    default Object verifyElement(DataType outputType){
        Object result;
        Object expected=null;
        var collection=getCollection();
        if(!isEmpty()) {
            expected=outputType.callPeekFirst(collection);
        }
        try {
            result=outputType.callElement(collection);
        }finally {
            verifyCollectionState();
        }
        if(outputType==DataType.REF && getDataType()==DataType.REF) {
            Assertions.assertSame(expected,result);
        }else {
            Assertions.assertEquals(expected,result);
        }
        return result;
    }

    @Override
    default Object verifyRemove(DataType outputType){
        Object result;
        Object expected=null;
        var collection=getCollection();
        if(!isEmpty()) {
            expected=outputType.callPeekFirst(collection);
        }
        try {
            result=outputType.callRemove(collection);
            updateRemoveIndexState(0);
        }finally {
            verifyCollectionState();
        }
        if(outputType==DataType.REF && getDataType()==DataType.REF) {
            Assertions.assertSame(expected,result);
        }else {
            Assertions.assertEquals(expected,result);
        }
        return result;
      }
    @Override
    default Object verifyPeek(DataType outputType) {
        Object result;
        boolean isEmpty=this.isEmpty();
        try {
            result=outputType.callPeek(getCollection());
        }finally {
            verifyCollectionState();
        }
        if(isEmpty) {
            Assertions.assertEquals(outputType.defaultVal,result);
        }else {
            verifyGetResult(0,result,outputType);
        }
        return result;
    }
}
