package omni.impl;

import java.util.Objects;
import org.junit.jupiter.api.Assertions;
import omni.api.OmniCollection;
import omni.api.OmniIterator;
import omni.impl.QueryVal.QueryValModification;
import omni.util.TestExecutorService;

public interface MonitoredSequence<SEQ extends OmniCollection<?>> extends MonitoredCollection<SEQ>{



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

    @Override default void verifyMASSIVEToString(String string,String testName){
        Assertions.assertEquals('[',string.charAt(0));
        int stringSize;
        Assertions.assertEquals(']',string.charAt(stringSize=string.length()-1));
        if(stringSize>1) {
            int numBatches=TestExecutorService.numWorkers;
            DataType dataType;
            switch(dataType=getDataType()) {
            case BOOLEAN:{
                Assertions.assertEquals('t',1);
                Assertions.assertEquals('r',2);
                Assertions.assertEquals('u',3);
                Assertions.assertEquals('e',4);
                int batchSize=(int)Math.ceil((double)(stringSize-6)/(double)numBatches);
                int batchOffset=5;
                for(;;) {
                    final int finalBatchOffset;
                    if((finalBatchOffset=batchOffset)==stringSize) {
                        break;
                    }
                    if(( batchOffset+=batchSize)>=stringSize) {
                        batchOffset=stringSize;
                    }
                    final int batchBound=batchOffset;
                    TestExecutorService.submitTest(()->{
                        int i=finalBatchOffset;
                        do {
                            Assertions.assertEquals(',',string.charAt(i));
                            Assertions.assertEquals(' ',string.charAt(++i));
                            Assertions.assertEquals('t',string.charAt(++i));
                            Assertions.assertEquals('r',string.charAt(++i));
                            Assertions.assertEquals('u',string.charAt(++i));
                            Assertions.assertEquals('e',string.charAt(++i));
                        }while(++i!=batchBound);
                    });
                }
                break;
            }
            case BYTE:
            case SHORT:
            case INT:
            case LONG:{
                Assertions.assertEquals('0',1);
                int batchSize=(int)Math.ceil((double)(stringSize-3)/(double)numBatches);
                int batchOffset=2;
                for(;;) {
                    final int finalBatchOffset;
                    if((finalBatchOffset=batchOffset)==stringSize) {
                        break;
                    }
                    if((batchOffset+=batchSize)>=stringSize) {
                        batchOffset=stringSize;
                    }
                    final int batchBound=batchOffset;
                    TestExecutorService.submitTest(()->{
                        int i=finalBatchOffset;
                        do {
                            Assertions.assertEquals(',',string.charAt(i));
                            Assertions.assertEquals(' ',string.charAt(++i));
                            Assertions.assertEquals('0',string.charAt(++i));
                        }while(++i!=batchBound);
                    });
                }
                break;
            }
            case FLOAT:{
                Assertions.assertEquals('0',1);
                Assertions.assertEquals('.',2);
                Assertions.assertEquals('0',3);
                int batchSize=(int)Math.ceil((double)(stringSize-5)/(double)numBatches);
                int batchOffset=4;
                for(;;) {
                    final int finalBatchOffset;
                    if((finalBatchOffset=batchOffset)==stringSize) {
                        break;
                    }
                    if((batchOffset+=batchSize)>=stringSize) {
                        batchOffset=stringSize;
                    }
                    final int batchBound=batchOffset;
                    TestExecutorService.submitTest(()->{
                        int i=finalBatchOffset;
                        do {
                            Assertions.assertEquals(',',string.charAt(i));
                            Assertions.assertEquals(' ',string.charAt(++i));
                            Assertions.assertEquals('0',string.charAt(++i));
                            Assertions.assertEquals('.',string.charAt(++i));
                            Assertions.assertEquals('0',string.charAt(++i));
                        }while(++i!=batchBound);
                    });
                }
                break;
            }
            default:
                throw dataType.invalid();
            }
            TestExecutorService.completeAllTests(testName);
        }
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
