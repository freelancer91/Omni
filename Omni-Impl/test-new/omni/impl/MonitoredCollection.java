package omni.impl;

import omni.api.OmniCollection;
import omni.api.OmniIterator;
public interface MonitoredCollection<COL extends OmniCollection>{
    public default Object convertInitialVal(Object initialVal){
        var dataType=this.getDataType();
        switch(dataType){
        case BOOLEAN:{
            if(initialVal instanceof Boolean){
                return (int)(((Boolean)initialVal).booleanValue()?1:0);
            }else if(initialVal instanceof Number){
                return (int)((Number)initialVal).intValue();
            }else if(initialVal instanceof Character){
                return (int)((Character)initialVal).charValue();
            }
            throw new UnsupportedOperationException("Unsupported initialVal " + initialVal);
        }
        case BYTE:
        case INT:
        case SHORT:{
            if(initialVal instanceof Number){
                return (int)((Number)initialVal).intValue();
            }else if(initialVal instanceof Boolean){
                return (int)(((Boolean)initialVal).booleanValue()?1:0);
            }else if(initialVal instanceof Character){
                return (int)((Character)initialVal).charValue();
            }
            throw new UnsupportedOperationException("Unsupported initialVal " + initialVal);
        }
        case CHAR:{
            if(initialVal instanceof Character){
                return (int)((Character)initialVal).charValue();
            }else if(initialVal instanceof Number){
                return (int)((Number)initialVal).intValue();
            }else if(initialVal instanceof Boolean){
                return (int)(((Boolean)initialVal).booleanValue()?1:0);
            }
            throw new UnsupportedOperationException("Unsupported initialVal " + initialVal);
        }
        case DOUBLE:{
            if(initialVal instanceof Number){
                return (double)((Number)initialVal).doubleValue();
            }else if(initialVal instanceof Boolean){
                return (double)(((Boolean)initialVal).booleanValue()?1d:0d);
            }else if(initialVal instanceof Character){
                return (double)((Character)initialVal).charValue();
            }
            throw new UnsupportedOperationException("Unsupported initialVal " + initialVal);
        }
        case FLOAT:{
            if(initialVal instanceof Number){
                return (float)((Number)initialVal).floatValue();
            }else if(initialVal instanceof Boolean){
                return (float)(((Boolean)initialVal).booleanValue()?1f:0f);
            }else if(initialVal instanceof Character){
                return (float)((Character)initialVal).charValue();
            }
            throw new UnsupportedOperationException("Unsupported initialVal " + initialVal);
        }
        case LONG:
        case REF:{
            if(initialVal instanceof Number){
                return (long)((Number)initialVal).longValue();
            }else if(initialVal instanceof Boolean){
                return (long)(((Boolean)initialVal).booleanValue()?1L:0L);
            }else if(initialVal instanceof Character){
                return (long)((Character)initialVal).charValue();
            }
            throw new UnsupportedOperationException("Unsupported initialVal " + initialVal);
        }
        }
        throw new UnsupportedOperationException("Unknown dataType " + dataType);
    }
    COL getCollection();
    void verifyCollectionState();
    int verifySize();
    int size();
    boolean verifyIsEmpty();
    boolean isEmpty();
    String verifyToString();
    int verifyHashCode();
    DataType getDataType();
    StructType getStructType();
    CheckedType getCheckedType();
    Object verifyClone();
    boolean add(int val);
    void verifyReadAndWrite(MonitoredFunctionGen monitoredFunctionGen);
    // TODO only valid on ref types
    boolean add(MonitoredObject monitoredObject);
    boolean verifyAdd(Object inputVal,DataType inputType,boolean boxed);
    boolean verifyRemoveVal(QueryVal queryVal,DataType inputType,QueryCastType queryCastType,
            QueryVal.QueryValModification modification);
    boolean verifyContains(QueryVal queryVal,DataType inputType,QueryCastType queryCastType,
            QueryVal.QueryValModification modification);
    void initContains(QueryVal queryVal,int setSize,double containsPosition,Object initialVal);
    void initDoesNotContains(QueryVal queryVal,int setSize,Object initialVal);
    void verifyForEach(MonitoredFunctionGen functionGen,FunctionCallType functionCallType);
    boolean verifyRemoveIf(MonitoredRemoveIfPredicateGen filterGen,FunctionCallType functionCallType);
    Object verifyToArray(DataType outputType);
    Object[] verifyToArray(MonitoredFunctionGen monitoredFunctionGen);
    <T> T[] verifyToArray(T[] arr);
    void illegalMod(IllegalModification modType);
    MonitoredIterator<COL> getMonitoredIterator();
    MonitoredIterator<COL> getMonitoredIterator(IteratorType itrType);
    MonitoredIterator<COL> getMonitoredIterator(IteratorType itrType,int index);
    @SuppressWarnings("rawtypes")
    interface MonitoredIterator<COL extends OmniCollection>{
        OmniIterator getIterator();
        MonitoredCollection<COL> getMonitoredCollection();
        void iterateForward();
        boolean hasNext();
        Object verifyNext(DataType outputType);
        boolean verifyHasNext();
        void verifyForEachRemaining(MonitoredFunctionGen functionGen,FunctionCallType functionCallType);
        void remove();
        void verifyRemove();
        IteratorType getIteratorType();
        void verifyIteratorState();
    }
}
