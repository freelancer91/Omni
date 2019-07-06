package omni.impl;

import java.util.Objects;
import org.junit.jupiter.api.Assertions;
import omni.api.OmniIterator;
import omni.api.OmniSet;
import omni.impl.QueryVal.QueryValModification;
public interface MonitoredSet<SET extends OmniSet<?>>extends MonitoredCollection<SET>{
    void updateAddState(Object inputVal,DataType inputType,boolean result);
    interface MonitoredSetIterator<ITR extends OmniIterator<?>,SET extends OmniSet<?>> extends MonitoredCollection.MonitoredIterator<ITR,SET>{
        @Override default IteratorType getIteratorType(){
            return IteratorType.AscendingItr;
        }
        void verifyIteratorState(Object itr);
        @Override
        default void verifyIteratorState(){
            verifyIteratorState(getIterator());
        }
        @Override
        default void verifyClone(Object clone){
            Assertions.assertNotSame(clone,getIterator());
            verifyIteratorState(clone);
        }
        @Override default void modItr(){
            if(!hasNext()) {
                throw new UnsupportedOperationException("Cannot modify an iterator in a depleted state");
            }
            ITR itr=getIterator();
            itr.next();
            updateIteratorState();
        }
        void updateItrNextState();
        void verifyNextResult(DataType outputType,Object result);
        @Override
        default Object verifyNext(DataType outputType){
            Object result=outputType.callIteratorNext(getIterator());
            verifyNextResult(outputType,result);
            updateItrNextState();
            verifyIteratorState();
            getMonitoredCollection().verifyCollectionState();

            return result;
        }
        void updateItrRemoveState();
        @Override
        default void verifyRemove(){
            getIterator().remove();
            updateItrRemoveState();
            verifyIteratorState();
            getMonitoredCollection().verifyCollectionState();
        }
    }
    void removeFromExpectedState(QueryVal queryVal,QueryValModification modification);
    @Override default boolean verifyRemoveVal(QueryVal queryVal,DataType inputType,QueryCastType queryCastType,
            QueryValModification modification){
        SET collection=getCollection();
        Object inputVal=queryVal.getInputVal(inputType,modification);
        int sizeBefore=collection.size();
        boolean containsBefore=queryCastType.callcontains(collection,inputVal,inputType);
        boolean result=queryCastType.callremoveVal(collection,inputVal,inputType);
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
        return result;
    }
    @Override
    default boolean verifyAdd(Object inputVal,DataType inputType,FunctionCallType functionCallType){
        SET collection=getCollection();
        boolean containsBefore=inputType.callcontains(collection,inputVal,functionCallType);
        int sizeBefore=collection.size();
        boolean result=inputType.callCollectionAdd(inputVal,collection,functionCallType);
        int sizeAfter=collection.size();
        boolean containsAfter=inputType.callcontains(collection,inputVal,functionCallType);
        Assertions.assertEquals(!result,containsBefore);
        Assertions.assertTrue(containsAfter);
        Assertions.assertEquals(sizeBefore,result?sizeAfter - 1:sizeAfter);
        updateAddState(inputVal,inputType,result);
        verifyCollectionState();
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
    @Override
    default void verifyToString(String string){
        getDataType().verifyToString(string,getCollection());
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
