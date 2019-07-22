package omni.impl;

import java.util.Objects;
import org.junit.jupiter.api.Assertions;
import omni.api.OmniCollection;
import omni.api.OmniIterator;
import omni.impl.QueryVal.QueryValModification;

public interface MonitoredSequence<SEQ extends OmniCollection<?>> extends MonitoredCollection<SEQ>{
    default Object verifyPoll(DataType outputType) {
        throw new UnsupportedOperationException();
    }
    default Object verifyPeek(DataType outputType) {
        throw new UnsupportedOperationException();
    }
    Object removeFirst();
   
    
    @Override
    @SuppressWarnings("unchecked")
    default boolean add(int val) {
        SEQ collection=getCollection();
        DataType dataType=getDataType();
        Object inputVal;
        switch(dataType) {
        case BOOLEAN:{
            boolean v;
            ((OmniCollection.OfBoolean)collection).add(v=(val&1)!=0);
            inputVal=v;
            break;
        }
        case BYTE:{
            byte v;
            ((OmniCollection.OfByte)collection).add(v=(byte)val);
            inputVal=v;
            break;
        }
        case CHAR:{
            char v;
            ((OmniCollection.OfChar)collection).add(v=(char)val);
            inputVal=v;
            break;
        }
        case SHORT:{
            short v;
            ((OmniCollection.OfShort)collection).add(v=(short)val);
            inputVal=v;
            break;
        }
        case INT:{
            int v;
            ((OmniCollection.OfInt)collection).add(v=val);
            inputVal=v;
            break;
        }
        case LONG:{
            long v;
            ((OmniCollection.OfLong)collection).add(v=val);
            inputVal=v;
            break;
        }
        case FLOAT:{
            float v;
            ((OmniCollection.OfFloat)collection).add(v=val);
            inputVal=v;
            break;
        }
        case DOUBLE:{
            double v;
            ((OmniCollection.OfDouble)collection).add(v=val);
            inputVal=v;
            break;
        }
        case REF:{
            ((OmniCollection.OfRef<Object>)collection).add(inputVal=val);
            break;
        }
        default:
            throw dataType.invalid();
        }
        updateAddState(inputVal,dataType);
        return true;
    }


  void updateRemoveIndexState(int index);
    MonitoredIterator<? extends OmniIterator<?>,SEQ> getMonitoredIterator(int index,IteratorType itrType);
    void verifyGetResult(int index,Object result,DataType outputType);
    void updateAddState(Object inputVal,DataType inputType);
    @Override default boolean verifyAdd(Object inputVal,DataType inputType,FunctionCallType functionCallType){
        var collection=getCollection();
        Assertions.assertTrue(inputType.callAdd(inputVal,collection,functionCallType));
        updateAddState(inputVal,inputType);
        verifyCollectionState();
        return true;
    }
    void updateRemoveValState(Object inputVal,DataType inputType);
    @Override default boolean verifyRemoveVal(QueryVal queryVal,DataType inputType,QueryCastType queryCastType,
            QueryValModification modification){
        var collection=getCollection();
        Object inputVal=queryVal.getInputVal(inputType,modification);
        boolean result=queryCastType.callremoveVal(collection,inputVal,inputType);
        if(result) {
            updateRemoveValState(inputVal,inputType);
        }
        verifyCollectionState();
        return result;
    }


    @Override default void verifyHashCode(int hashCode){
        var dataType=getDataType();
        var collection=getCollection();
        int expectedHash=1;
        switch(dataType) {
        case BOOLEAN:{
            var itr=(OmniIterator.OfBoolean)collection.iterator();
            while(itr.hasNext()) {
                expectedHash=expectedHash*31+Boolean.hashCode(itr.nextBoolean());
            }
            break;
        }
        case BYTE:{
            var itr=(OmniIterator.OfByte)collection.iterator();
            while(itr.hasNext()) {
                expectedHash=expectedHash*31+Byte.hashCode(itr.nextByte());
            }
            break;
        }
        case CHAR:{
            var itr=(OmniIterator.OfChar)collection.iterator();
            while(itr.hasNext()) {
                expectedHash=expectedHash*31+Character.hashCode(itr.nextChar());
            }
            break;
        }
        case DOUBLE:{
            var itr=(OmniIterator.OfDouble)collection.iterator();
            while(itr.hasNext()) {
                expectedHash=expectedHash*31+Double.hashCode(itr.nextDouble());
            }
            break;
        }
        case FLOAT:{
            var itr=(OmniIterator.OfFloat)collection.iterator();
            while(itr.hasNext()) {
                expectedHash=expectedHash*31+Float.hashCode(itr.nextFloat());
            }
            break;
        }
        case INT:{
            var itr=(OmniIterator.OfInt)collection.iterator();
            while(itr.hasNext()) {
                expectedHash=expectedHash*31+Integer.hashCode(itr.nextInt());
            }
            break;
        }
        case LONG:{
            var itr=(OmniIterator.OfLong)collection.iterator();
            while(itr.hasNext()) {
                expectedHash=expectedHash * 31 + Long.hashCode(itr.nextLong());
            }
            break;
        }
        case REF:{
            var itr=(OmniIterator.OfRef<?>)collection.iterator();
            while(itr.hasNext()) {
                expectedHash=expectedHash*31+Objects.hashCode(itr.next());
            }
            break;
        }
        case SHORT:{
            var itr=(OmniIterator.OfShort)collection.iterator();
            while(itr.hasNext()) {
                expectedHash=expectedHash*31+Short.hashCode(itr.nextShort());
            }
            break;
        }
        default:
            throw dataType.invalid();
        }
        Assertions.assertEquals(expectedHash,hashCode);
    }


}
