package omni.impl;

import java.util.Objects;
import org.junit.jupiter.api.Assertions;
import omni.api.OmniIterator;
import omni.api.OmniSet;
import omni.impl.QueryVal.QueryValModification;
public interface MonitoredSet<SET extends OmniSet<?>>extends MonitoredCollection<SET>{
    @Override
    @SuppressWarnings("unchecked") default boolean add(int val) {
        SET collection=getCollection();
        DataType dataType=getDataType();
        Object inputVal;
        boolean result;
        switch(dataType) {
        case BOOLEAN:{
            boolean v;
            result=((OmniSet.OfBoolean)collection).add(v=(val&1)!=0);
            inputVal=v;
            break;
        }
        case BYTE:{
            byte v;
            result=((OmniSet.OfByte)collection).add(v=(byte)val);
            inputVal=v;
            break;
        }
        case CHAR:{
            char v;
            result=((OmniSet.OfChar)collection).add(v=(char)val);
            inputVal=v;
            break;
        }
        case SHORT:{
            short v;
            result=((OmniSet.OfShort)collection).add(v=(short)val);
            inputVal=v;
            break;
        }
        case INT:{
            int v;
            result=((OmniSet.OfInt)collection).add(v=val);
            inputVal=v;
            break;
        }
        case LONG:{
            long v;
            result=((OmniSet.OfLong)collection).add(v=val);
            inputVal=v;
            break;
        }
        case FLOAT:{
            float v;
            result=((OmniSet.OfFloat)collection).add(v=val);
            inputVal=v;
            break;
        }
        case DOUBLE:{
            double v;
            result=((OmniSet.OfDouble)collection).add(v=val);
            inputVal=v;
            break;
        }
        case REF:{
            result=((OmniSet.OfRef<Object>)collection).add(inputVal=val);
            break;
        }
        default:
            throw dataType.invalid();
        }
        updateAddState(inputVal,dataType,result);
        return result;
    }
    
    default void verifyAdd(DataType inputType,FunctionCallType functionCallType,final Object inputVal){
        var collection=getCollection();
        boolean alreadyContains;
        switch(inputType){
        case BOOLEAN:
            alreadyContains=collection.contains((boolean)inputVal);
            break;
        case BYTE:
            alreadyContains=collection.contains((byte)inputVal);
            break;
        case CHAR:
            alreadyContains=collection.contains((char)inputVal);
            break;
        case SHORT:
            alreadyContains=collection.contains((short)inputVal);
            break;
        case INT:
            alreadyContains=collection.contains((int)inputVal);
            break;
        case LONG:
            alreadyContains=collection.contains((long)inputVal);
            break;
        case FLOAT:
            alreadyContains=collection.contains((float)inputVal);
            break;
        case DOUBLE:
            alreadyContains=collection.contains((double)inputVal);
            break;
        case REF:
            alreadyContains=collection.contains(inputVal);
            break;
        default:
            throw inputType.invalid();
        }
        Assertions.assertNotEquals(alreadyContains,this.verifyAdd(inputVal,inputType,functionCallType));
    }
    void updateAddState(Object inputVal,DataType inputType,boolean result);
    interface MonitoredSetIterator<ITR extends OmniIterator<?>,SET extends OmniSet<?>> extends MonitoredCollection.MonitoredIterator<ITR,SET>{
        @Override default IteratorType getIteratorType(){
            return IteratorType.AscendingItr;
        }

    }
    void removeFromExpectedState(QueryVal queryVal,QueryValModification modification);
    @Override default boolean verifyRemoveVal(QueryVal queryVal,DataType inputType,QueryCastType queryCastType,
            QueryValModification modification){
        SET collection=getCollection();
        Object inputVal=queryVal.getInputVal(inputType,modification);
        int sizeBefore=collection.size();
        boolean containsBefore=queryCastType.callcontains(collection,inputVal,inputType);
        final boolean result;
        try{
            result=queryCastType.callremoveVal(collection,inputVal,inputType);
            boolean containsAfter=queryCastType.callcontains(collection,inputVal,inputType);
            int sizeAfter=collection.size();
            if(result) {
                Assertions.assertNotEquals(containsBefore,containsAfter);
                Assertions.assertEquals(sizeBefore,sizeAfter+1);
                removeFromExpectedState(queryVal,modification);
            }else {
                Assertions.assertEquals(containsBefore,containsAfter);
                Assertions.assertEquals(sizeBefore,sizeAfter);
            }
        }finally{
            verifyCollectionState();
        }
        return result;
    }
    @Override
    default boolean verifyAdd(Object inputVal,DataType inputType,FunctionCallType functionCallType){
        SET collection=getCollection();
        boolean containsBefore=inputType.callcontains(collection,inputVal,functionCallType);
        int sizeBefore=collection.size();
        final boolean result;
        try{
            result=inputType.callAdd(inputVal,collection,functionCallType);
            updateAddState(inputVal,inputType,result);
        }finally{
            verifyCollectionState();
        }
        int sizeAfter=collection.size();
        boolean containsAfter=inputType.callcontains(collection,inputVal,functionCallType);
        Assertions.assertEquals(!result,containsBefore);
        Assertions.assertTrue(containsAfter);
        Assertions.assertEquals(sizeBefore,result?sizeAfter - 1:sizeAfter);

        return result;
    }
    @Override default MonitoredIterator<? extends OmniIterator<?>,SET> getMonitoredIterator(IteratorType itrType){
        if(itrType!=IteratorType.AscendingItr) {
            throw itrType.invalid();
        }
        return getMonitoredIterator();
    }

    @Override default void modParent(){
        throw new UnsupportedOperationException();
    }
    @Override default void modRoot(){
        throw new UnsupportedOperationException();
    }
    @Override
    default void verifyMASSIVEToString(String string,String testName){
        var dataType=getDataType();
        switch(dataType){
        case INT:
        case LONG:
        case FLOAT:
            dataType.verifyToString(string,getCollection(),testName);
            return;
        default:
        }
        throw dataType.invalid();
    }
    @Override default void verifyHashCode(int hashCode){
        int expectedHashCode=0;
        SET collection=getCollection();
        var itr=collection.iterator();
        while(itr.hasNext()) {
            expectedHashCode+=Objects.hashCode(itr.next());
        }
        Assertions.assertEquals(expectedHashCode,hashCode);
    }
}
