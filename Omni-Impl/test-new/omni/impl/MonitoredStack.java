package omni.impl;

import org.junit.jupiter.api.Assertions;
import omni.api.OmniIterator;
import omni.api.OmniStack;
import omni.util.PeekAndPollIfc;
public interface MonitoredStack<STK extends OmniStack<?>>extends MonitoredSequence<OmniIterator<?>,STK>{
  
    @Override default MonitoredIterator<? extends OmniIterator<?>,STK> getMonitoredIterator(IteratorType itrType){

      if(itrType!=IteratorType.AscendingItr) {
        throw itrType.invalid();
      }
      return getMonitoredIterator();
    
  }
    @Override default MonitoredIterator<? extends OmniIterator<?>,STK> getMonitoredIterator(int index,IteratorType itrType){

      if(itrType!=IteratorType.AscendingItr) {
        throw itrType.invalid();
      }
      var itrMonitor= getMonitoredIterator();
      while(--index>=0 && itrMonitor.hasNext()) {
        itrMonitor.iterateForward();
      }
      return itrMonitor;
    
  }
    default void verifyPush(Object inputVal,DataType inputType,FunctionCallType functionCallType) {
      var collection=getCollection();
      inputType.callStackPush(inputVal,collection,functionCallType);
      updateAddState(inputVal,inputType);
      verifyCollectionState();
    }
    
    default Object verifyPop(DataType outputType) {

      Object result;
      Object expected=null;
      int size;
      if(0 < (size=size())){
          expected=outputType.callPeek((PeekAndPollIfc<?>)getCollection());
      }
      try{
          result=outputType.callStackPop(this);
          updateRemoveIndexState(size-1);
      }finally{
          verifyCollectionState();
      }
      if(outputType == DataType.REF && getDataType() == DataType.REF){
          Assertions.assertSame(expected,result);
      }else{
          Assertions.assertEquals(expected,result);
      }
      return result;
  
    }
    default Object verifyPoll(DataType outputType) {

      Object result;
      Object expected=outputType.callPeek((PeekAndPollIfc<?>)getCollection());
      int size=size();
      try{
          result=outputType.callPoll((PeekAndPollIfc<?>)getCollection());
          if(size>0) {
            updateRemoveIndexState(size-1);
          }
          
      }finally{
          verifyCollectionState();
      }
      if(size==0 || (outputType == DataType.REF && getDataType() == DataType.REF)){
          Assertions.assertSame(expected,result);
      }else{
          Assertions.assertEquals(expected,result);
      }
      return result;
  
    }
    default Object verifyPeek(DataType outputType) {

      Object result;
      int size=size();
      try {
          result=outputType.callPeek((PeekAndPollIfc<?>)getCollection());
      }finally{
          verifyCollectionState();
      }
      if(size==0) {
        Assertions.assertSame(outputType.defaultVal,result);
      }else {
        verifyGetResult(size-1,result,outputType);
      }
      
      return result;
  
    }
    default int verifyThrowingSearch(MonitoredObjectGen monitoredObjectGen){
      Object inputVal=monitoredObjectGen.getMonitoredObject(this);
      try{
          return QueryCastType.ToObject.callsearch(getCollection(),inputVal,DataType.REF);
      }finally{
          verifyCollectionState();
      }
  }
    default int verifySearch(QueryVal queryVal,DataType inputType,QueryCastType queryCastType,
            QueryVal.QueryValModification modification) {

      try{
          return queryCastType.callsearch(getCollection(),queryVal.getInputVal(inputType,modification),inputType);
      }finally{
          verifyCollectionState();
      }
  
    }
}
