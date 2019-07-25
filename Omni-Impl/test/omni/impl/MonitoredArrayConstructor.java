package omni.impl;

import java.util.function.IntFunction;

public class MonitoredArrayConstructor<T> implements IntFunction<T[]>{
    public int numCalls;
    public DataType dataType;
    MonitoredArrayConstructor(MonitoredCollection<?> collection){
        this.dataType=collection.getDataType();
    }
    protected void throwingCall(){
    }
    @SuppressWarnings("unchecked")
    @Override
    public T[] apply(int arrSize){
        ++numCalls;
        throwingCall();
        switch(dataType){
        case BOOLEAN:
            return (T[])new Boolean[arrSize];
        case BYTE:
            return (T[])new Byte[arrSize];
        case CHAR:
            return (T[])new Character[arrSize];
        case SHORT:
            return (T[])new Short[arrSize];
        case INT:
            return (T[])new Integer[arrSize];
        case LONG:
            return (T[])new Long[arrSize];
        case FLOAT:
            return (T[])new Float[arrSize];
        case DOUBLE:
            return (T[])new Double[arrSize];
        case REF:
            return (T[])new Object[arrSize];
        }
        throw dataType.invalid();
    }
}
