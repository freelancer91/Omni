package omni.impl;

import omni.api.OmniStack;
public interface MonitoredStack<STK extends OmniStack<?>>extends MonitoredSequence<STK>{
  
  default Object verifyClone(){
    final Object clone;
    try{
        clone=getCollection().clone();
    }finally{
        verifyCollectionState();
    }
    verifyClone(clone);
    return clone;
}
    
    void verifyPush(Object inputVal,DataType inputType,FunctionCallType functionCallType);
    Object verifyPop(DataType outputType);
//    Object verifyPoll(DataType outputType);
//    Object verifyPeek(DataType outputType);
    
    default int verifyThrowingSearch(MonitoredObjectGen monitoredObjectGen){
      Object inputVal=monitoredObjectGen.getMonitoredObject(this,0);
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
