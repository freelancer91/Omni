package omni.impl.seq;

import java.io.Externalizable;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import omni.api.OmniCollection;
import omni.api.OmniIterator;
import omni.api.OmniQueue;
import omni.api.OmniStack;
import omni.impl.BooleanSnglLnkNode;
import omni.impl.ByteSnglLnkNode;
import omni.impl.CharSnglLnkNode;
import omni.impl.CheckedType;
import omni.impl.DataType;
import omni.impl.DoubleSnglLnkNode;
import omni.impl.FloatSnglLnkNode;
import omni.impl.FunctionCallType;
import omni.impl.IntSnglLnkNode;
import omni.impl.IteratorType;
import omni.impl.LongSnglLnkNode;
import omni.impl.MonitoredCollection;
import omni.impl.MonitoredFunction;
import omni.impl.MonitoredFunctionGen;
import omni.impl.MonitoredObjectGen;
import omni.impl.MonitoredQueue;
import omni.impl.MonitoredSequence;
import omni.impl.MonitoredStack;
import omni.impl.QueryCastType;
import omni.impl.QueryVal;
import omni.impl.RefSnglLnkNode;
import omni.impl.ShortSnglLnkNode;
import omni.impl.StructType;
import omni.util.OmniArray;
import omni.util.TestExecutorService;

public class SnglLnkSeqTest{
    private static final double[] RANDOM_THRESHOLDS=new double[]{0.01,0.05,0.10,0.25,0.50,0.75,0.90,0.95,0.99};
    private static final double[] POSITIONS=new double[]{-1,0,0.25,0.5,0.75,1.0};
    private static final int[] SHORT_SIZES=new int[]{0,10};
    private static final int[] MEDIUM_SIZES=new int[]{0,1,2,3,4,5,10};
    private static final int[] SIZES=new int[]{0,1,2,3,4,5,10,20,30,40,50,60,70,80,90,100};
    private static final int[] REMOVE_IF_SIZES=new int[]{0,1,2,3,4,5,6,7,8,9,10,20,30,40,50,60,70,90,80,100};
    private static final StructType[] ALL_STRUCTS=new StructType[] {StructType.SnglLnkQueue,StructType.SnglLnkStack};
//    private static final EnumMap<StructType,EnumMap<CheckedType,EnumMap<DataType,SequenceInitParams>>> INIT_PARAMS=new EnumMap<>(StructType.class);
//    static {
//        for(var structType:new StructType[] {StructType.SnglLnkQueue,StructType.SnglLnkStack}) {
//            final var structMap=new EnumMap<CheckedType,EnumMap<DataType,SequenceInitParams>>(CheckedType.class);
//            INIT_PARAMS.put(structType,structMap);
//            for(var checkedType:CheckedType.values()) {
//                final var checkedMap=new EnumMap<DataType,SequenceInitParams>(DataType.class);
//                for(var collectionType:DataType.values()) {
//                    checkedMap.put(collectionType,new SequenceInitParams(structType,collectionType,checkedType,OmniArray.OfInt.DEFAULT_ARR,OmniArray.OfInt.DEFAULT_ARR));
//                }
//            }
//        }
//    }
    
    private static interface AddTest<MONITOR extends MonitoredCollection<?>>{
        void runTest(MONITOR monitor,Object inputval,DataType inputType,FunctionCallType functionCallType);
        private void runAllTests(String testName,StructType...structTypes) {
            for(var collectionType:DataType.values()) {
                for(var inputType:collectionType.mayBeAddedTo()) {
                    for(var functionCallType:inputType.validFunctionCalls) {
                        for(var checkedType:CheckedType.values()) {
                            for(var structType:structTypes) {
                                  TestExecutorService.submitTest(()->{
                                  final var monitor=getMonitoredSequence(structType, checkedType, collectionType,10);
                                  for(int i=0;i < 10;++i){
                                      monitor.verifyAdd(inputType.convertValUnchecked(i),inputType,
                                              functionCallType);
                                  }
                              });
                            }
                        }
                    }
                }
            }
            TestExecutorService.completeAllTests(testName);
        }
    }
    
    @Test
    public void testadd_val(){
        AddTest<?> test=(monitor,inputVal,inputType,functionCallType)->Assertions.assertTrue(monitor.verifyAdd(inputVal,inputType,functionCallType));
        test.runAllTests("SnglLnkSeqTest.testadd_val",ALL_STRUCTS);
    }
    @Test
    public void testclear_void(){
        BasicTest test=MonitoredSequence::verifyClear;
        test.runAllTests("SnglLnkSeqTest.testclear_void");
    }
    @Test
    public void testclone_void(){
        BasicTest test=MonitoredSequence::verifyClone;
        test.runAllTests("SnglLnkSeqTest.testclone_void");
    }
    @Test
    public void testConstructor_void(){
        for(final var checkedType:CheckedType.values()){
            for(final var collectionType:DataType.values()){
                TestExecutorService.submitTest(()->new SnglLnkStackMonitor<>(checkedType,collectionType).verifyCollectionState());
                TestExecutorService.submitTest(()->new SnglLnkQueueMonitor<>(checkedType,collectionType).verifyCollectionState());
            }
        }
        TestExecutorService.completeAllTests("SnglLnkSeqTest.testConstructor_void");
    }
    @Test
    public void testcontains_val(){
        final QueryTest<MonitoredSequence<?>> test=(monitor,queryVal,inputType,castType,modification,monitoredObjectGen,
                position,seqSize)->{
            if(monitoredObjectGen == null){
                Assertions.assertEquals(position >= 0,monitor.verifyContains(queryVal,inputType,castType,modification));
            }else{
                monitor.verifyThrowingContains(monitoredObjectGen);
            }
        };
        test.runAllTests("SnglLnkSeqTest.testcontains_val",true);
    }
    
    
    
    @Test
    public void testelement_void() {
        PopTest<MonitoredQueue<?>> test=(monitor,outputType)->{
            monitor.verifyElement(outputType);
            if(!monitor.isEmpty()) {
                monitor.removeFirst();
            }
        };
        test.runAllTests("SnglLnkSeqTest.testelement_void",true,StructType.SnglLnkQueue);
    }
    @Test
    public void testforEach_Consumer(){
        final MonitoredFunctionTest<MonitoredSequence<?>> test=(monitor,functionGen,functionCallType,
                randSeed)->{
                    if(functionGen.expectedException == null || monitor.isEmpty()){
                        monitor.verifyForEach(functionGen,functionCallType,randSeed);
                    }else{
                        Assertions.assertThrows(functionGen.expectedException,
                                ()->monitor.verifyForEach(functionGen,functionCallType,randSeed));
                    }
        };
        test.runAllTests("SnglLnkSeqTest.testforEach_Consumer",100);
    }
    @Test
    public void testhashCode_void(){
        final ToStringAndHashCodeTest test=new ToStringAndHashCodeTest(){
            @Override
            public void callRaw(OmniCollection<?> seq){
                seq.hashCode();
            }
            @Override
            public void callVerify(MonitoredSequence<?> monitor){
                monitor.verifyHashCode();
            }
        };
        test.runAllTests("SnglLnkSeqTest.testhashCode_void");
    }
    @Test
    public void testisEmpty_void(){
        BasicTest test=MonitoredSequence::verifyIsEmpty;
        test.runAllTests("SnglLnkSeqTest.testisEmpty_void");
    }
    @Test
    public void testiterator_void(){
        BasicTest test=(monitor)->{
            monitor.getMonitoredIterator().verifyIteratorState();
            monitor.verifyCollectionState();
        };
        test.runAllTests("SnglLnkSeqTest.testiterator_void");
    }
    @Test
    public void testItrclone_void() {
        //TODO
    }
    @Tag("ForEachRemaining")
    @Test
    public void testItrforEachRemaining_Consumer(){
        //TODO
    }
    @Test
    public void testItrhasNext_void(){
        //TODO
    }
    @Test
    public void testItrnext_void(){
        //TODO
    }
    @Tag("ItrRemove")
    @Test
    public void testItrremove_void(){
        //TODO
    }
    @Tag("MASSIVEtoString")
    @Test
    public void testMASSIVEtoString(){
        //TODO
    }
    @Test
    public void testoffer_val(){
        AddTest<MonitoredQueue<?>> test=(monitor,inputVal,inputType,functionCallType)->Assertions.assertTrue(monitor.verifyOffer(inputVal,inputType,functionCallType));
        test.runAllTests("SnglLnkSeqTest.testoffer_val",StructType.SnglLnkQueue);
    }
    @Test
    public void testpeek_void(){
        PopTest<?> test=(monitor,outputType)->{
            monitor.verifyPeek(outputType);
            if(!monitor.isEmpty()) {
                monitor.removeFirst();
            }
        };
        test.runAllTests("SnglLnkSeqTest.testpeek_void",false,ALL_STRUCTS);
    }
    @Test
    public void testpoll_void(){
        PopTest<?> test=MonitoredSequence::verifyPoll;
        test.runAllTests("SnglLnkSeqTest.testpoll_void",false,ALL_STRUCTS);
    }
    @Test
    public void testpop_void(){
        PopTest<MonitoredStack<?>> test=MonitoredStack::verifyPop;
        test.runAllTests("SnglLnkSeqTest.testpop_void",true,StructType.SnglLnkStack);
    }
    @Test
    public void testpush_val(){
        AddTest<MonitoredStack<?>> test=MonitoredStack::verifyPush;
        test.runAllTests("SnglLnkSeqTest.testpush_val",StructType.SnglLnkStack);
    }
    @Tag("ReadAndWrite")
    @Test
    public void testReadAndWrite(){
        final MonitoredFunctionTest<MonitoredSequence<?>> test=(monitor,functionGen,functionCallType,
                randSeed)->{
                    if(functionGen.expectedException == null){
                        Assertions.assertDoesNotThrow(()->monitor.verifyReadAndWrite(functionGen));
                    }else{
                        Assertions.assertThrows(functionGen.expectedException,()->monitor.verifyReadAndWrite(functionGen));
                    }
        };
        test.runAllTests("SnglLnkSeqTest.testReadAndWrite",0);
    }
    private static interface PopTest<MONITOR extends MonitoredSequence<?>>{
        void processNextElement(MONITOR monitor,DataType outputType);
        private void runAllTests(String testName,boolean throwOnEmpty,StructType...structTypes) {
            for(var structType:structTypes) {
                for(var checkedType:CheckedType.values()) {
                    for(var collectionType:DataType.values()) {
                        for(var outputType:collectionType.validOutputTypes()) {
                            TestExecutorService.submitTest(()->{
                                @SuppressWarnings("unchecked")
                                var monitor=(MONITOR)SequenceInitialization.Ascending.initialize(getMonitoredSequence(structType,checkedType, collectionType,10),10,0);
                                for(int i=0;i<10;++i) {
                                    processNextElement(monitor,outputType);
                                }
                                if(throwOnEmpty) {
                                    if(checkedType.checked) {
                                        Assertions.assertThrows(NoSuchElementException.class,()->processNextElement(monitor,outputType));
                                    }
                                }else {
                                    processNextElement(monitor,outputType);
                                }
                            });
                        }
                        
                    }
                }
            }
            
            TestExecutorService.completeAllTests(testName);
        }
    }
    @Test
    public void testremove_void(){
        PopTest<MonitoredQueue<?>> test=MonitoredQueue::verifyRemove;
        test.runAllTests("SnglLnkSeqTest.testremove_void",true,StructType.SnglLnkQueue);
    }
    @Tag("RemoveIf")
    @Test
    public void testremoveIf_Predicate(){
        //TODO
    }
    @Test
    public void testremoveVal_val(){
        final QueryTest<MonitoredSequence<?>> test=(monitor,queryVal,inputType,castType,modification,monitoredObjectGen,
                position,seqSize)->{
            if(monitoredObjectGen == null){
                Assertions.assertEquals(position >= 0,monitor.verifyRemoveVal(queryVal,inputType,castType,modification));
            }else{
                monitor.verifyThrowingRemoveVal(monitoredObjectGen);
            }
        };
        test.runAllTests("SnglLnkSeqTest.testremoveVal_val",true);
    }
    @Test
    public void testsearch_val(){
        final QueryTest<MonitoredStack<?>> test=(monitor,queryVal,inputType,castType,modification,monitoredObjectGen,
                position,seqSize)->{
            if(monitoredObjectGen == null){
                int expectedIndex;
                if(position >= 0){
                    expectedIndex=1 + (int)Math.round(position * seqSize);
                }else{
                    expectedIndex=-1;
                }
                Assertions.assertEquals(expectedIndex,monitor.verifySearch(queryVal,inputType,castType,modification));
            }else{
                monitor.verifyThrowingSearch(monitoredObjectGen);
            }
        };
        test.runAllTests("SnglLnkSeqTest.testsearch_val",false);
    }
    @Test
    public void testsize_void(){
        BasicTest test=MonitoredSequence::verifySize;
        test.runAllTests("SnglLnkSeqTest.testsize_void");
    }
    @Test
    public void testtoArray_IntFunction(){
        final MonitoredFunctionTest<MonitoredSequence<?>> test=(monitor,functionGen,functionCallType,
                randSeed)->{
            if(functionGen.expectedException == null){
                monitor.verifyToArray(functionGen);
            }else{
                Assertions.assertThrows(functionGen.expectedException,()->monitor.verifyToArray(functionGen));
            }
            
        };
        test.runAllTests("SnglLnkSeqTest.testtoArray_IntFunction",0);
    }
    @Test
    public void testtoArray_ObjectArray(){
        //TODO
    }
    @Test
    public void testtoArray_void(){
        final BasicTest test=(monitor)->{
            for(final var outputType:monitor.getDataType().validOutputTypes()){
                outputType.verifyToArray(monitor);
            }
        };
        test.runAllTests("SnglLnkSeqTest.testtoArray_void");
    }
    @Test
    public void testtoString_void(){
        final ToStringAndHashCodeTest test=new ToStringAndHashCodeTest(){
            @Override
            public void callRaw(OmniCollection<?> seq){
                seq.toString();
            }
            @Override
            public void callVerify(MonitoredSequence<?> monitor){
                monitor.verifyToString();
            }
        };
        test.runAllTests("SnglLnkSeqTest.testtoString_void");
    }
    @org.junit.jupiter.api.AfterEach
    public void verifyAllExecuted(){
        int numTestsRemaining;
        if((numTestsRemaining=TestExecutorService.getNumRemainingTasks()) != 0){
            System.err.println("Warning: there were " + numTestsRemaining + " tests that were not completed");
        }
        TestExecutorService.reset();
    }
    private static MonitoredSequence<?> getMonitoredSequence(StructType structType,CheckedType checkedType, DataType collectionType,int initCapacity){
        switch(structType) {
        case SnglLnkQueue:
            return new SnglLnkQueueMonitor<>(checkedType,collectionType,initCapacity);
        case SnglLnkStack:
            return new SnglLnkStackMonitor<>(checkedType,collectionType,initCapacity);
        default:
            throw structType.invalid();
        }
    }
    private static AbstractSnglLnkSeqMonitor<?> getMonitoredSequence(StructType structType,CheckedType checkedType, DataType collectionType){
        switch(structType) {
        case SnglLnkQueue:
            return new SnglLnkQueueMonitor<>(checkedType,collectionType);
        case SnglLnkStack:
            return new SnglLnkStackMonitor<>(checkedType,collectionType);
        default:
            throw structType.invalid();
        }
    }
    
    private static interface BasicTest{
        void runTest(MonitoredSequence<?> monitor);
        private void runStackTests(String testName) {
            for(var checkedType:CheckedType.values()) {
                for(var collectionType:DataType.values()) {
                    for(int size:SIZES) {
                        TestExecutorService.submitTest(()->runTest(SequenceInitialization.Ascending.initialize(new SnglLnkStackMonitor<>(checkedType,collectionType,size),size,0)));
                    }
                }
            }
            TestExecutorService.completeAllTests(testName);
        }
        private void runQueueTests(String testName) {
            for(var checkedType:CheckedType.values()) {
                for(var collectionType:DataType.values()) {
                    for(int size:SIZES) {
                        TestExecutorService.submitTest(()->runTest(SequenceInitialization.Ascending.initialize(new SnglLnkQueueMonitor<>(checkedType,collectionType,size),size,0)));
                    }
                }
            }
            TestExecutorService.completeAllTests(testName);
        }
        
        private void runAllTests(String testName) {
            for(var structType:ALL_STRUCTS) {
                for(var checkedType:CheckedType.values()) {
                    for(var collectionType:DataType.values()) {
                        for(int size:SIZES) {
                            TestExecutorService.submitTest(()->runTest(SequenceInitialization.Ascending.initialize(getMonitoredSequence(structType,checkedType,collectionType),size,0)));
                        }
                    }
                }
            }
            TestExecutorService.completeAllTests(testName);
        }
    }
    private static interface MonitoredFunctionTest<MONITOR extends MonitoredSequence<?>>{
        void runTest(MONITOR monitor,MonitoredFunctionGen functionGen,FunctionCallType functionCallType,long randSeed);
        @SuppressWarnings("unchecked")
        private void runAllTests(String testName,long maxRand){
            for(var collectionType:DataType.values()) {
                for(var size:MEDIUM_SIZES) {
                    final int initValBound=collectionType == DataType.BOOLEAN && size != 0?1:0;
                    for(var checkedType:CheckedType.values()) {
                        for(var structType:ALL_STRUCTS) {
                            for(final var functionGen:structType.validMonitoredFunctionGens){
                                if(functionGen.expectedException==null || checkedType.checked) {
                                    for(final var functionCallType:collectionType.validFunctionCalls){
                                        final long randSeedBound=size > 1 && functionGen.randomized
                                                && !functionCallType.boxed?maxRand:0;
                                        for(long tmpRandSeed=0;tmpRandSeed <= randSeedBound;++tmpRandSeed){
                                            final long randSeed=tmpRandSeed;
                                            for(int tmpInitVal=0;tmpInitVal <= initValBound;++tmpInitVal){
                                                final int initVal=tmpInitVal;
                                                TestExecutorService.submitTest(()->runTest(
                                                        (MONITOR)SequenceInitialization.Ascending
                                                                .initialize(
                                                                        getMonitoredSequence(structType,checkedType,collectionType,size),
                                                                        size,initVal),
                                                        functionGen,functionCallType,randSeed));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            TestExecutorService.completeAllTests(testName);
        }
    }
    private static interface QueryTest<MONITOR extends MonitoredSequence<?>>{
        void callAndVerifyResult(MONITOR monitor,QueryVal queryVal,DataType inputType,QueryCastType castType,
                QueryVal.QueryValModification modification,MonitoredObjectGen monitoredObjectGen,double position,
                int seqSize);
        @SuppressWarnings("unchecked")
        private void runTest(StructType structType,CheckedType checkedType,DataType collectionType,QueryVal queryVal,QueryVal.QueryValModification modification,QueryCastType castType,DataType inputType,MonitoredObjectGen monitoredObjectGen,int seqSize,double position) {
            final var monitor=getMonitoredSequence(structType,checkedType,collectionType,seqSize);
            if(position < 0){
                switch(collectionType){
                case BOOLEAN:
                    queryVal.initDoesNotContain((OmniCollection.OfBoolean)monitor.getCollection(),seqSize,0,
                            modification);
                    break;
                case BYTE:
                    queryVal.initDoesNotContain((OmniCollection.OfByte)monitor.getCollection(),seqSize,0,modification);
                    break;
                case CHAR:
                    queryVal.initDoesNotContain((OmniCollection.OfChar)monitor.getCollection(),seqSize,0,modification);
                    break;
                case DOUBLE:
                    queryVal.initDoesNotContain((OmniCollection.OfDouble)monitor.getCollection(),seqSize,0,
                            modification);
                    break;
                case FLOAT:
                    queryVal.initDoesNotContain((OmniCollection.OfFloat)monitor.getCollection(),seqSize,0,modification);
                    break;
                case INT:
                    queryVal.initDoesNotContain((OmniCollection.OfInt)monitor.getCollection(),seqSize,0,modification);
                    break;
                case LONG:
                    queryVal.initDoesNotContain((OmniCollection.OfLong)monitor.getCollection(),seqSize,0,modification);
                    break;
                case REF:
                    queryVal.initDoesNotContain((OmniCollection.OfRef<Object>)monitor.getCollection(),seqSize,0,
                            modification);
                    break;
                case SHORT:
                    queryVal.initDoesNotContain((OmniCollection.OfShort)monitor.getCollection(),seqSize,0,modification);
                    break;
                default:
                    throw collectionType.invalid();
                }
            }else{
                switch(collectionType){
                case BOOLEAN:
                    queryVal.initContains((OmniCollection.OfBoolean)monitor.getCollection(),seqSize,0,position,
                            modification);
                    break;
                case BYTE:
                    queryVal.initContains((OmniCollection.OfByte)monitor.getCollection(),seqSize,0,position,
                            modification);
                    break;
                case CHAR:
                    queryVal.initContains((OmniCollection.OfChar)monitor.getCollection(),seqSize,0,position,
                            modification);
                    break;
                case DOUBLE:
                    queryVal.initContains((OmniCollection.OfDouble)monitor.getCollection(),seqSize,0,position,
                            modification);
                    break;
                case FLOAT:
                    queryVal.initContains((OmniCollection.OfFloat)monitor.getCollection(),seqSize,0,position,
                            modification);
                    break;
                case INT:
                    queryVal.initContains((OmniCollection.OfInt)monitor.getCollection(),seqSize,0,position,
                            modification);
                    break;
                case LONG:
                    queryVal.initContains((OmniCollection.OfLong)monitor.getCollection(),seqSize,0,position,
                            modification);
                    break;
                case REF:
                    queryVal.initContains((MonitoredCollection<? extends OmniCollection.OfRef<Object>>)monitor,seqSize,
                            0,position,modification,inputType,monitoredObjectGen);
                    break;
                case SHORT:
                    queryVal.initContains((OmniCollection.OfShort)monitor.getCollection(),seqSize,0,position,
                            modification);
                    break;
                default:
                    throw collectionType.invalid();
                }
            }
            monitor.updateCollectionState();
            callAndVerifyResult((MONITOR)monitor,queryVal,inputType,castType,modification,monitoredObjectGen,position,
                    seqSize);
        }
        private void runAllTests(String testName,boolean useAllStructs) {
            for(var structType:ALL_STRUCTS) {
                if(useAllStructs || structType==StructType.SnglLnkStack) {
                    for(var collectionType:DataType.values()) {
                        for(var queryVal:QueryVal.values()) {
                            if(collectionType.isValidQueryVal(queryVal)) {
                                queryVal.validQueryCombos.forEach((modification,castTypesToInputTypes)->{
                                    castTypesToInputTypes.forEach((castType,inputTypes)->{
                                      inputTypes.forEach(inputType->{
                                         boolean queryCanReturnTrue=queryVal.queryCanReturnTrue(modification,castType,inputType,collectionType) ;
                                         for(var checkedType:CheckedType.values()) {
                                             if(queryVal==QueryVal.NonNull) {
                                                 for(var objGen:structType.validMonitoredObjectGens) {
                                                     for(var size:MEDIUM_SIZES) {
                                                         if(size!=0 && objGen.expectedException!=null && checkedType.checked) {
                                                             TestExecutorService.submitTest(()->Assertions.assertThrows(objGen.expectedException,()->runTest(structType,checkedType,collectionType,queryVal,modification,castType,inputType,objGen,size,size==0?-1:0)));
                                                         }
                                                     }
                                                 }
                                             }else {
                                                 for(final var size:MEDIUM_SIZES){
                                                     for(final var position:POSITIONS){
                                                         if(position >= 0){
                                                             if(!queryCanReturnTrue){
                                                                 continue;
                                                             }
                                                             switch(size){
                                                             case 3:
                                                                 if(position == 0.5d){
                                                                     break;
                                                                 }
                                                             case 2:
                                                                 if(position == 1.0d){
                                                                     break;
                                                                 }
                                                             case 1:
                                                                 if(position == 0.0d){
                                                                     break;
                                                                 }
                                                             case 0:
                                                                 continue;
                                                             case 4:
                                                                 if(position != 0.5d){
                                                                     break;
                                                                 }
                                                             default:
                                                                 continue;
                                                             }
                                                         }
                                                         TestExecutorService.submitTest(()->runTest(structType,checkedType,collectionType,queryVal,modification,castType,inputType,null,size,position));
                                                     }
                                                 }
                                             }
                                         }
                                      });
                                    });
                                });
                            }
                        }
                    }
                
                }
                
                
            }
            TestExecutorService.completeAllTests(testName);
        }
    }
    private static interface ToStringAndHashCodeTest{
        void callVerify(MonitoredSequence<?> monitor);
        void callRaw(OmniCollection<?> collection);
        private void runAllTests(String testName) {
            for(var structType:ALL_STRUCTS) {
                for(var checkedType:CheckedType.values()) {
                    for(var collectionType:DataType.values()) {
                        for(var size:SIZES) {
                            if(collectionType==DataType.REF) {
                                for(var objGen:structType.validMonitoredObjectGens) {
                                    if(size==0 || objGen.expectedException==null) {
                                        TestExecutorService.submitTest(()->callVerify(SequenceInitialization.Ascending.initialize(getMonitoredSequence(structType,checkedType,collectionType,size),size,0)));
                                    }else if(checkedType.checked) {
                                        TestExecutorService.submitTest(()->{
                                            final var throwSwitch=new MonitoredObjectGen.ThrowSwitch();
                                            final var monitor=SequenceInitialization.Ascending
                                                    .initializeWithMonitoredObj(
                                                            getMonitoredSequence(structType,checkedType,collectionType,size),size,0,
                                                            objGen,throwSwitch);
                                            Assertions.assertThrows(objGen.expectedException,()->{
                                                try{
                                                    callRaw(monitor.getCollection());
                                                }finally{
                                                    throwSwitch.doThrow=false;
                                                    monitor.verifyCollectionState();
                                                }
                                            });
                                        });
                                    }
                                }
                            }else {
                                int initValBound=size>0 && collectionType==DataType.BOOLEAN?1:0;
                                for(int tmpInitVal=0;tmpInitVal<=initValBound;++tmpInitVal) {
                                    final int initVal=tmpInitVal;
                                    TestExecutorService.submitTest(()->callVerify(SequenceInitialization.Ascending.initialize(getMonitoredSequence(structType,checkedType,collectionType,size),size,initVal)));
                                }
                            }
                        }
                    }
                }
            }
            
        }
    }
    private static class SnglLnkQueueMonitor<SEQ extends AbstractSeq<E> & Externalizable & OmniQueue<E>,E> extends AbstractSnglLnkSeqMonitor<SEQ> implements MonitoredQueue<SEQ>{

        class ItrMonitor extends AbstractSnglLnkSeqMonitor<SEQ>.AbstractItrMonitor{

            ItrMonitor(){
                super(0);
            }
            @Override
            void updateItrNextIndex() {
                this.expectedLastRetIndex=this.expectedCurrIndex++;
            }

            @Override
            public int getNumLeft(){
                return expectedSize-expectedCurrIndex;
            }

            @Override
            public void verifyForEachRemaining(MonitoredFunction function){
                
                int expectedCurrIndex=this.expectedCurrIndex;
                int expectedSize=SnglLnkQueueMonitor.this.expectedSize;
                if(expectedCurrIndex<expectedSize) {
                    super.verifyForEachRemainingHelper(function);
                    this.expectedLastRetIndex=expectedSize-1;
                    this.expectedCurrIndex=expectedSize;
                }
            }

            @Override
            public void verifyCloneHelper(Object clone){
                var itr=this.itr;
                final var checked=checkedType.checked;
                switch(dataType) {
                case BOOLEAN:
                    Assertions.assertSame(expectedNext,FieldAndMethodAccessor.BooleanSnglLnkSeq.AbstractItr.next(itr));
                    Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.BooleanSnglLnkSeq.AbstractItr.curr(itr));
                    Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.BooleanSnglLnkSeq.AbstractItr.prev(itr));
                    if(checked) {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanSnglLnkSeq.CheckedQueue.Itr.parent(itr));
                        Assertions.assertSame(expectedItrModCount,FieldAndMethodAccessor.BooleanSnglLnkSeq.CheckedQueue.Itr.modCount(itr));
                    }else {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanSnglLnkSeq.UncheckedQueue.Itr.parent(itr));
                    }
                    break;
                case BYTE:
                    Assertions.assertSame(expectedNext,FieldAndMethodAccessor.ByteSnglLnkSeq.AbstractItr.next(itr));
                    Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.ByteSnglLnkSeq.AbstractItr.curr(itr));
                    Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.ByteSnglLnkSeq.AbstractItr.prev(itr));
                    if(checked) {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.ByteSnglLnkSeq.CheckedQueue.Itr.parent(itr));
                        Assertions.assertSame(expectedItrModCount,FieldAndMethodAccessor.ByteSnglLnkSeq.CheckedQueue.Itr.modCount(itr));
                    }else {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.ByteSnglLnkSeq.UncheckedQueue.Itr.parent(itr));
                    }
                    break;
                case CHAR:
                    Assertions.assertSame(expectedNext,FieldAndMethodAccessor.CharSnglLnkSeq.AbstractItr.next(itr));
                    Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.CharSnglLnkSeq.AbstractItr.curr(itr));
                    Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.CharSnglLnkSeq.AbstractItr.prev(itr));
                    if(checked) {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.CharSnglLnkSeq.CheckedQueue.Itr.parent(itr));
                        Assertions.assertSame(expectedItrModCount,FieldAndMethodAccessor.CharSnglLnkSeq.CheckedQueue.Itr.modCount(itr));
                    }else {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.CharSnglLnkSeq.UncheckedQueue.Itr.parent(itr));
                    }
                    break;
                case DOUBLE:
                    Assertions.assertSame(expectedNext,FieldAndMethodAccessor.DoubleSnglLnkSeq.AbstractItr.next(itr));
                    Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.DoubleSnglLnkSeq.AbstractItr.curr(itr));
                    Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.DoubleSnglLnkSeq.AbstractItr.prev(itr));
                    if(checked) {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleSnglLnkSeq.CheckedQueue.Itr.parent(itr));
                        Assertions.assertSame(expectedItrModCount,FieldAndMethodAccessor.DoubleSnglLnkSeq.CheckedQueue.Itr.modCount(itr));
                    }else {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleSnglLnkSeq.UncheckedQueue.Itr.parent(itr));
                    }
                    break;
                case FLOAT:
                    Assertions.assertSame(expectedNext,FieldAndMethodAccessor.FloatSnglLnkSeq.AbstractItr.next(itr));
                    Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.FloatSnglLnkSeq.AbstractItr.curr(itr));
                    Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.FloatSnglLnkSeq.AbstractItr.prev(itr));
                    if(checked) {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.FloatSnglLnkSeq.CheckedQueue.Itr.parent(itr));
                        Assertions.assertSame(expectedItrModCount,FieldAndMethodAccessor.FloatSnglLnkSeq.CheckedQueue.Itr.modCount(itr));
                    }else {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.FloatSnglLnkSeq.UncheckedQueue.Itr.parent(itr));
                    }
                    break;
                case INT:
                    Assertions.assertSame(expectedNext,FieldAndMethodAccessor.IntSnglLnkSeq.AbstractItr.next(itr));
                    Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.IntSnglLnkSeq.AbstractItr.curr(itr));
                    Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.IntSnglLnkSeq.AbstractItr.prev(itr));
                    if(checked) {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.IntSnglLnkSeq.CheckedQueue.Itr.parent(itr));
                        Assertions.assertSame(expectedItrModCount,FieldAndMethodAccessor.IntSnglLnkSeq.CheckedQueue.Itr.modCount(itr));
                    }else {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.IntSnglLnkSeq.UncheckedQueue.Itr.parent(itr));
                    }
                    break;
                case LONG:
                    Assertions.assertSame(expectedNext,FieldAndMethodAccessor.LongSnglLnkSeq.AbstractItr.next(itr));
                    Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.LongSnglLnkSeq.AbstractItr.curr(itr));
                    Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.LongSnglLnkSeq.AbstractItr.prev(itr));
                    if(checked) {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.LongSnglLnkSeq.CheckedQueue.Itr.parent(itr));
                        Assertions.assertSame(expectedItrModCount,FieldAndMethodAccessor.LongSnglLnkSeq.CheckedQueue.Itr.modCount(itr));
                    }else {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.LongSnglLnkSeq.UncheckedQueue.Itr.parent(itr));
                    }
                    break;
                case REF:
                    Assertions.assertSame(expectedNext,FieldAndMethodAccessor.RefSnglLnkSeq.AbstractItr.next(itr));
                    Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.RefSnglLnkSeq.AbstractItr.curr(itr));
                    Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.RefSnglLnkSeq.AbstractItr.prev(itr));
                    if(checked) {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.RefSnglLnkSeq.CheckedQueue.Itr.parent(itr));
                        Assertions.assertSame(expectedItrModCount,FieldAndMethodAccessor.RefSnglLnkSeq.CheckedQueue.Itr.modCount(itr));
                    }else {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.RefSnglLnkSeq.UncheckedQueue.Itr.parent(itr));
                    }
                    break;
                case SHORT:
                    Assertions.assertSame(expectedNext,FieldAndMethodAccessor.ShortSnglLnkSeq.AbstractItr.next(itr));
                    Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.ShortSnglLnkSeq.AbstractItr.curr(itr));
                    Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.ShortSnglLnkSeq.AbstractItr.prev(itr));
                    if(checked) {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.ShortSnglLnkSeq.CheckedQueue.Itr.parent(itr));
                        Assertions.assertSame(expectedItrModCount,FieldAndMethodAccessor.ShortSnglLnkSeq.CheckedQueue.Itr.modCount(itr));
                    }else {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.ShortSnglLnkSeq.UncheckedQueue.Itr.parent(itr));
                    }
                    break;
                default:
                    throw dataType.invalid();
                }
            }
            
        }
        
        SnglLnkQueueMonitor(CheckedType checkedType,DataType dataType,int initCap){
            super(checkedType,dataType,initCap);
        }

        SnglLnkQueueMonitor(CheckedType checkedType,DataType dataType){
            super(checkedType,dataType);
        }

        

        @Override
        public MonitoredIterator<? extends OmniIterator<?>,SEQ> getMonitoredIterator(){
            return new ItrMonitor();
        }

        @Override
        public StructType getStructType(){
            return StructType.SnglLnkQueue;
        }

        @Override
        public void modCollection(){
            switch(dataType) {
            case BOOLEAN:
                ++((BooleanSnglLnkSeq.CheckedQueue)seq).modCount;
                break;
            case BYTE:
                ++((ByteSnglLnkSeq.CheckedQueue)seq).modCount;
                break;
            case CHAR:
                ++((CharSnglLnkSeq.CheckedQueue)seq).modCount;
                break;
            case DOUBLE:
                ++((DoubleSnglLnkSeq.CheckedQueue)seq).modCount;
                break;
            case FLOAT:
                ++((FloatSnglLnkSeq.CheckedQueue)seq).modCount;
                break;
            case INT:
                ++((IntSnglLnkSeq.CheckedQueue)seq).modCount;
                break;
            case LONG:
                ++((LongSnglLnkSeq.CheckedQueue)seq).modCount;
                break;
            case REF:
                ++((RefSnglLnkSeq.CheckedQueue<?>)seq).modCount;
                break;
            case SHORT:
                ++((ShortSnglLnkSeq.CheckedQueue)seq).modCount;
                break;
            default:
                throw dataType.invalid();
            
            }
            ++expectedModCount;
        }

        @Override
        public Object verifyElement(DataType outputType){
            Object result;
            Object expected=null;
            if(expectedSize!=0) {
                expected=super.get(0,outputType);
            }
            try {
                result=outputType.callElement(seq);
            }finally {
                verifyCollectionState();
            }
            if(outputType==DataType.REF && dataType==DataType.REF) {
                Assertions.assertSame(expected,result);
            }else {
                Assertions.assertEquals(expected,result);
            }
            return result;
        }

        @Override
        public Object verifyRemove(DataType outputType){
            Object result;
            Object expected=null;
            if(expectedSize!=0) {
                expected=super.get(0,outputType);
            }
            try {
                result=outputType.callRemove(seq);
                super.updateRemoveIndexState(0);
            }finally {
                verifyCollectionState();
            }
            if(outputType==DataType.REF && dataType==DataType.REF) {
                Assertions.assertSame(expected,result);
            }else {
                Assertions.assertEquals(expected,result);
            }
            return result;
          }

        @Override
        public boolean verifyOffer(Object inputVal,DataType inputType,FunctionCallType functionCallType){
            boolean result=inputType.callOffer(inputVal,seq,functionCallType);
            if(result) {
                super.updateAddState(expectedSize,inputVal,inputType);
            }
            verifyCollectionState();
            return result;
        }

        @Override
        public Object verifyPoll(DataType outputType){
            Object result;
            Object expected=outputType.callPeek(seq);
            boolean isEmpty=expectedSize==0;
            try{
                result=outputType.callPoll(seq);
                if(!isEmpty) {
                  super.updateRemoveIndexState(0);
                }
                
            }finally{
                verifyCollectionState();
            }
            Assertions.assertEquals(expected,result);
            return result;
        
          }

        @Override
        public Object verifyPeek(DataType outputType){
            Object result;
            boolean isEmpty=expectedSize==0;
            try {
                result=outputType.callPeek(seq);
            }finally {
                verifyCollectionState();
            }
            if(isEmpty) {
                Assertions.assertEquals(outputType.defaultVal,result);
            }else {
                super.verifyGetResult(0,result,outputType);
            }
            return result;
        }

        @Override
        void verifyBooleanIntegrity(){
            int expectedSize;
            Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
            final var cast=(BooleanSnglLnkSeq.UncheckedQueue)seq;
            final var expectedArr=(boolean[])this.expectedArr;
            if(checkedType.checked) {
                Assertions.assertEquals(expectedModCount,((BooleanSnglLnkSeq.CheckedQueue)cast).modCount);
            }
            var headNode=cast.head;
            if(expectedSize>0) {
                for(int i=0,bound=expectedSize-1;;) {
                    Assertions.assertEquals(expectedArr[i],headNode.val);
                    if(++i==bound) {
                        break;
                    }
                    headNode=headNode.next;
                }
                Assertions.assertSame(headNode,cast.tail);
                headNode=headNode.next;
            }
            Assertions.assertNull(headNode);
        }

        @Override
        void verifyByteIntegrity(){
            int expectedSize;
            Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
            final var cast=(ByteSnglLnkSeq.UncheckedQueue)seq;
            final var expectedArr=(byte[])this.expectedArr;
            if(checkedType.checked) {
                Assertions.assertEquals(expectedModCount,((ByteSnglLnkSeq.CheckedQueue)cast).modCount);
            }
            var headNode=cast.head;
            if(expectedSize>0) {
                for(int i=0,bound=expectedSize-1;;) {
                    Assertions.assertEquals(expectedArr[i],headNode.val);
                    if(++i==bound) {
                        break;
                    }
                    headNode=headNode.next;
                }
                Assertions.assertSame(headNode,cast.tail);
                headNode=headNode.next;
            }
            Assertions.assertNull(headNode);
        }

        @Override
        void verifyCharIntegrity(){
            int expectedSize;
            Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
            final var cast=(CharSnglLnkSeq.UncheckedQueue)seq;
            final var expectedArr=(char[])this.expectedArr;
            if(checkedType.checked) {
                Assertions.assertEquals(expectedModCount,((CharSnglLnkSeq.CheckedQueue)cast).modCount);
            }
            var headNode=cast.head;
            if(expectedSize>0) {
                for(int i=0,bound=expectedSize-1;;) {
                    Assertions.assertEquals(expectedArr[i],headNode.val);
                    if(++i==bound) {
                        break;
                    }
                    headNode=headNode.next;
                }
                Assertions.assertSame(headNode,cast.tail);
                headNode=headNode.next;
            }
            Assertions.assertNull(headNode);
        }

        @Override
        void verifyShortIntegrity(){
            int expectedSize;
            Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
            final var cast=(ShortSnglLnkSeq.UncheckedQueue)seq;
            final var expectedArr=(short[])this.expectedArr;
            if(checkedType.checked) {
                Assertions.assertEquals(expectedModCount,((ShortSnglLnkSeq.CheckedQueue)cast).modCount);
            }
            var headNode=cast.head;
            if(expectedSize>0) {
                for(int i=0,bound=expectedSize-1;;) {
                    Assertions.assertEquals(expectedArr[i],headNode.val);
                    if(++i==bound) {
                        break;
                    }
                    headNode=headNode.next;
                }
                Assertions.assertSame(headNode,cast.tail);
                headNode=headNode.next;
            }
            Assertions.assertNull(headNode);
        }

        @Override
        void verifyIntIntegrity(){
            int expectedSize;
            Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
            final var cast=(IntSnglLnkSeq.UncheckedQueue)seq;
            final var expectedArr=(int[])this.expectedArr;
            if(checkedType.checked) {
                Assertions.assertEquals(expectedModCount,((IntSnglLnkSeq.CheckedQueue)cast).modCount);
            }
            var headNode=cast.head;
            if(expectedSize>0) {
                for(int i=0,bound=expectedSize-1;;) {
                    Assertions.assertEquals(expectedArr[i],headNode.val);
                    if(++i==bound) {
                        break;
                    }
                    headNode=headNode.next;
                }
                Assertions.assertSame(headNode,cast.tail);
                headNode=headNode.next;
            }
            Assertions.assertNull(headNode);
        }

        @Override
        void verifyLongIntegrity(){
            int expectedSize;
            Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
            final var cast=(LongSnglLnkSeq.UncheckedQueue)seq;
            final var expectedArr=(long[])this.expectedArr;
            if(checkedType.checked) {
                Assertions.assertEquals(expectedModCount,((LongSnglLnkSeq.CheckedQueue)cast).modCount);
            }
            var headNode=cast.head;
            if(expectedSize>0) {
                for(int i=0,bound=expectedSize-1;;) {
                    Assertions.assertEquals(expectedArr[i],headNode.val);
                    if(++i==bound) {
                        break;
                    }
                    headNode=headNode.next;
                }
                Assertions.assertSame(headNode,cast.tail);
                headNode=headNode.next;
            }
            Assertions.assertNull(headNode);
        }

        @Override
        void verifyFloatIntegrity(){
            int expectedSize;
            Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
            final var cast=(FloatSnglLnkSeq.UncheckedQueue)seq;
            final var expectedArr=(float[])this.expectedArr;
            if(checkedType.checked) {
                Assertions.assertEquals(expectedModCount,((FloatSnglLnkSeq.CheckedQueue)cast).modCount);
            }
            var headNode=cast.head;
            if(expectedSize>0) {
                for(int i=0,bound=expectedSize-1;;) {
                    Assertions.assertEquals(expectedArr[i],headNode.val);
                    if(++i==bound) {
                        break;
                    }
                    headNode=headNode.next;
                }
                Assertions.assertSame(headNode,cast.tail);
                headNode=headNode.next;
            }
            Assertions.assertNull(headNode);
        }

        @Override
        void verifyDoubleIntegrity(){
            int expectedSize;
            Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
            final var cast=(DoubleSnglLnkSeq.UncheckedQueue)seq;
            final var expectedArr=(double[])this.expectedArr;
            if(checkedType.checked) {
                Assertions.assertEquals(expectedModCount,((DoubleSnglLnkSeq.CheckedQueue)cast).modCount);
            }
            var headNode=cast.head;
            if(expectedSize>0) {
                for(int i=0,bound=expectedSize-1;;) {
                    Assertions.assertEquals(expectedArr[i],headNode.val);
                    if(++i==bound) {
                        break;
                    }
                    headNode=headNode.next;
                }
                Assertions.assertSame(headNode,cast.tail);
                headNode=headNode.next;
            }
            Assertions.assertNull(headNode);
        }

        @Override
        void verifyRefIntegrity(){
            int expectedSize;
            Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
            final var cast=(RefSnglLnkSeq.UncheckedQueue<?>)seq;
            final var expectedArr=(Object[])this.expectedArr;
            if(checkedType.checked) {
                Assertions.assertEquals(expectedModCount,((RefSnglLnkSeq.CheckedQueue<?>)cast).modCount);
            }
            var headNode=cast.head;
            if(expectedSize>0) {
                for(int i=0,bound=expectedSize-1;;) {
                    Assertions.assertSame(expectedArr[i],headNode.val);
                    if(++i==bound) {
                        break;
                    }
                    headNode=headNode.next;
                }
                Assertions.assertSame(headNode,cast.tail);
                headNode=headNode.next;
            }
            Assertions.assertNull(headNode);
        }

        @Override
        void verifyBooleanClone(Object clone){
            Assertions.assertNotSame(clone,seq);
            final var cloneCast=(BooleanSnglLnkSeq.UncheckedQueue)clone;
            int size;
            Assertions.assertEquals(size=seq.size,cloneCast.size);
            Assertions.assertEquals(checkedType.checked,cloneCast instanceof BooleanSnglLnkSeq.CheckedQueue);
            if(checkedType.checked){
                Assertions.assertEquals(0,((BooleanSnglLnkSeq.CheckedQueue)cloneCast).modCount);
            }else{
                Assertions.assertTrue(cloneCast instanceof BooleanSnglLnkSeq.UncheckedQueue);
            }
            var cloneHead=cloneCast.head;
            var thisHead=((BooleanSnglLnkSeq)seq).head;
            if(size>0) {

                for(;;) {
                    Assertions.assertNotSame(cloneHead,thisHead);
                    Assertions.assertEquals(cloneHead.val,thisHead.val);
                    if(--size==0) {
                        break;
                    }
                    cloneHead=cloneHead.next;
                    thisHead=thisHead.next;
                }
                Assertions.assertSame(cloneHead,cloneCast.tail);
                cloneHead=cloneHead.next;
                thisHead=thisHead.next;
            }
            Assertions.assertNull(cloneHead);
            Assertions.assertNull(thisHead);
        }

        @Override
        void verifyByteClone(Object clone){
            Assertions.assertNotSame(clone,seq);
            final var cloneCast=(ByteSnglLnkSeq.UncheckedQueue)clone;
            int size;
            Assertions.assertEquals(size=seq.size,cloneCast.size);
            Assertions.assertEquals(checkedType.checked,cloneCast instanceof ByteSnglLnkSeq.CheckedQueue);
            if(checkedType.checked){
                Assertions.assertEquals(0,((ByteSnglLnkSeq.CheckedQueue)cloneCast).modCount);
            }else{
                Assertions.assertTrue(cloneCast instanceof ByteSnglLnkSeq.UncheckedQueue);
            }
            var cloneHead=cloneCast.head;
            var thisHead=((ByteSnglLnkSeq)seq).head;
            if(size>0) {

                for(;;) {
                    Assertions.assertNotSame(cloneHead,thisHead);
                    Assertions.assertEquals(cloneHead.val,thisHead.val);
                    if(--size==0) {
                        break;
                    }
                    cloneHead=cloneHead.next;
                    thisHead=thisHead.next;
                }
                Assertions.assertSame(cloneHead,cloneCast.tail);
                cloneHead=cloneHead.next;
                thisHead=thisHead.next;
            }
            Assertions.assertNull(cloneHead);
            Assertions.assertNull(thisHead);
        }

        @Override
        void verifyCharClone(Object clone){
            Assertions.assertNotSame(clone,seq);
            final var cloneCast=(CharSnglLnkSeq.UncheckedQueue)clone;
            int size;
            Assertions.assertEquals(size=seq.size,cloneCast.size);
            Assertions.assertEquals(checkedType.checked,cloneCast instanceof CharSnglLnkSeq.CheckedQueue);
            if(checkedType.checked){
                Assertions.assertEquals(0,((CharSnglLnkSeq.CheckedQueue)cloneCast).modCount);
            }else{
                Assertions.assertTrue(cloneCast instanceof CharSnglLnkSeq.UncheckedQueue);
            }
            var cloneHead=cloneCast.head;
            var thisHead=((CharSnglLnkSeq)seq).head;
            if(size>0) {

                for(;;) {
                    Assertions.assertNotSame(cloneHead,thisHead);
                    Assertions.assertEquals(cloneHead.val,thisHead.val);
                    if(--size==0) {
                        break;
                    }
                    cloneHead=cloneHead.next;
                    thisHead=thisHead.next;
                }
                Assertions.assertSame(cloneHead,cloneCast.tail);
                cloneHead=cloneHead.next;
                thisHead=thisHead.next;
            }
            Assertions.assertNull(cloneHead);
            Assertions.assertNull(thisHead);
        }

        @Override
        void verifyShortClone(Object clone){
            Assertions.assertNotSame(clone,seq);
            final var cloneCast=(ShortSnglLnkSeq.UncheckedQueue)clone;
            int size;
            Assertions.assertEquals(size=seq.size,cloneCast.size);
            Assertions.assertEquals(checkedType.checked,cloneCast instanceof ShortSnglLnkSeq.CheckedQueue);
            if(checkedType.checked){
                Assertions.assertEquals(0,((ShortSnglLnkSeq.CheckedQueue)cloneCast).modCount);
            }else{
                Assertions.assertTrue(cloneCast instanceof ShortSnglLnkSeq.UncheckedQueue);
            }
            var cloneHead=cloneCast.head;
            var thisHead=((ShortSnglLnkSeq)seq).head;
            if(size>0) {

                for(;;) {
                    Assertions.assertNotSame(cloneHead,thisHead);
                    Assertions.assertEquals(cloneHead.val,thisHead.val);
                    if(--size==0) {
                        break;
                    }
                    cloneHead=cloneHead.next;
                    thisHead=thisHead.next;
                }
                Assertions.assertSame(cloneHead,cloneCast.tail);
                cloneHead=cloneHead.next;
                thisHead=thisHead.next;
            }
            Assertions.assertNull(cloneHead);
            Assertions.assertNull(thisHead);
        }

        @Override
        void verifyIntClone(Object clone){
            Assertions.assertNotSame(clone,seq);
            final var cloneCast=(IntSnglLnkSeq.UncheckedQueue)clone;
            int size;
            Assertions.assertEquals(size=seq.size,cloneCast.size);
            Assertions.assertEquals(checkedType.checked,cloneCast instanceof IntSnglLnkSeq.CheckedQueue);
            if(checkedType.checked){
                Assertions.assertEquals(0,((IntSnglLnkSeq.CheckedQueue)cloneCast).modCount);
            }else{
                Assertions.assertTrue(cloneCast instanceof IntSnglLnkSeq.UncheckedQueue);
            }
            var cloneHead=cloneCast.head;
            var thisHead=((IntSnglLnkSeq)seq).head;
            if(size>0) {

                for(;;) {
                    Assertions.assertNotSame(cloneHead,thisHead);
                    Assertions.assertEquals(cloneHead.val,thisHead.val);
                    if(--size==0) {
                        break;
                    }
                    cloneHead=cloneHead.next;
                    thisHead=thisHead.next;
                }
                Assertions.assertSame(cloneHead,cloneCast.tail);
                cloneHead=cloneHead.next;
                thisHead=thisHead.next;
            }
            Assertions.assertNull(cloneHead);
            Assertions.assertNull(thisHead);
        }

        @Override
        void verifyLongClone(Object clone){
            Assertions.assertNotSame(clone,seq);
            final var cloneCast=(LongSnglLnkSeq.UncheckedQueue)clone;
            int size;
            Assertions.assertEquals(size=seq.size,cloneCast.size);
            Assertions.assertEquals(checkedType.checked,cloneCast instanceof LongSnglLnkSeq.CheckedQueue);
            if(checkedType.checked){
                Assertions.assertEquals(0,((LongSnglLnkSeq.CheckedQueue)cloneCast).modCount);
            }else{
                Assertions.assertTrue(cloneCast instanceof LongSnglLnkSeq.UncheckedQueue);
            }
            var cloneHead=cloneCast.head;
            var thisHead=((LongSnglLnkSeq)seq).head;
            if(size>0) {

                for(;;) {
                    Assertions.assertNotSame(cloneHead,thisHead);
                    Assertions.assertEquals(cloneHead.val,thisHead.val);
                    if(--size==0) {
                        break;
                    }
                    cloneHead=cloneHead.next;
                    thisHead=thisHead.next;
                }
                Assertions.assertSame(cloneHead,cloneCast.tail);
                cloneHead=cloneHead.next;
                thisHead=thisHead.next;
            }
            Assertions.assertNull(cloneHead);
            Assertions.assertNull(thisHead);
        }

        @Override
        void verifyFloatClone(Object clone){
            Assertions.assertNotSame(clone,seq);
            final var cloneCast=(FloatSnglLnkSeq.UncheckedQueue)clone;
            int size;
            Assertions.assertEquals(size=seq.size,cloneCast.size);
            Assertions.assertEquals(checkedType.checked,cloneCast instanceof FloatSnglLnkSeq.CheckedQueue);
            if(checkedType.checked){
                Assertions.assertEquals(0,((FloatSnglLnkSeq.CheckedQueue)cloneCast).modCount);
            }else{
                Assertions.assertTrue(cloneCast instanceof FloatSnglLnkSeq.UncheckedQueue);
            }
            var cloneHead=cloneCast.head;
            var thisHead=((FloatSnglLnkSeq)seq).head;
            if(size>0) {

                for(;;) {
                    Assertions.assertNotSame(cloneHead,thisHead);
                    Assertions.assertEquals(cloneHead.val,thisHead.val);
                    if(--size==0) {
                        break;
                    }
                    cloneHead=cloneHead.next;
                    thisHead=thisHead.next;
                }
                Assertions.assertSame(cloneHead,cloneCast.tail);
                cloneHead=cloneHead.next;
                thisHead=thisHead.next;
            }
            Assertions.assertNull(cloneHead);
            Assertions.assertNull(thisHead);
        }

        @Override
        void verifyDoubleClone(Object clone){
            Assertions.assertNotSame(clone,seq);
            final var cloneCast=(DoubleSnglLnkSeq.UncheckedQueue)clone;
            int size;
            Assertions.assertEquals(size=seq.size,cloneCast.size);
            Assertions.assertEquals(checkedType.checked,cloneCast instanceof DoubleSnglLnkSeq.CheckedQueue);
            if(checkedType.checked){
                Assertions.assertEquals(0,((DoubleSnglLnkSeq.CheckedQueue)cloneCast).modCount);
            }else{
                Assertions.assertTrue(cloneCast instanceof DoubleSnglLnkSeq.UncheckedQueue);
            }
            var cloneHead=cloneCast.head;
            var thisHead=((DoubleSnglLnkSeq)seq).head;
            if(size>0) {

                for(;;) {
                    Assertions.assertNotSame(cloneHead,thisHead);
                    Assertions.assertEquals(cloneHead.val,thisHead.val);
                    if(--size==0) {
                        break;
                    }
                    cloneHead=cloneHead.next;
                    thisHead=thisHead.next;
                }
                Assertions.assertSame(cloneHead,cloneCast.tail);
                cloneHead=cloneHead.next;
                thisHead=thisHead.next;
            }
            Assertions.assertNull(cloneHead);
            Assertions.assertNull(thisHead);
        }

        @Override
        void verifyRefClone(Object clone,boolean refIsSame){
            Assertions.assertNotSame(clone,seq);
            final var cloneCast=(RefSnglLnkSeq.UncheckedQueue<?>)clone;
            int size;
            Assertions.assertEquals(size=seq.size,cloneCast.size);
            Assertions.assertEquals(checkedType.checked,cloneCast instanceof RefSnglLnkSeq.CheckedQueue);
            if(checkedType.checked){
                Assertions.assertEquals(0,((RefSnglLnkSeq.CheckedQueue<?>)cloneCast).modCount);
            }else{
                Assertions.assertTrue(cloneCast instanceof RefSnglLnkSeq.UncheckedQueue);
            }
            var cloneHead=cloneCast.head;
            var thisHead=((RefSnglLnkSeq<?>)seq).head;
            if(size>0) {
                if(refIsSame) {
                    for(;;) {
                        Assertions.assertNotSame(cloneHead,thisHead);
                        Assertions.assertSame(cloneHead.val,thisHead.val);
                        if(--size==0) {
                            break;
                        }
                        cloneHead=cloneHead.next;
                        thisHead=thisHead.next;
                    }
                }else {
                    for(;;) {
                        Assertions.assertNotSame(cloneHead,thisHead);
                        Assertions.assertEquals(cloneHead.val,thisHead.val);
                        if(--size==0) {
                            break;
                        }
                        cloneHead=cloneHead.next;
                        thisHead=thisHead.next;
                    }
                }
                Assertions.assertSame(cloneHead,cloneCast.tail);
                cloneHead=cloneHead.next;
                thisHead=thisHead.next;
            }
            Assertions.assertNull(cloneHead);
            Assertions.assertNull(thisHead);
        }


        @Override
        void copyBooleanListContents(){
            int expectedSize=this.expectedSize;
            final var cast=(BooleanSnglLnkSeq)seq;
            var expectedArr=(boolean[])this.expectedArr;
            int expectedCapacity;
            if((expectedCapacity=this.expectedCapacity)<expectedSize) {
                this.expectedArr=expectedArr=new boolean[this.expectedCapacity=expectedArr == OmniArray.OfBoolean.DEFAULT_ARR
                        && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP?OmniArray.DEFAULT_ARR_SEQ_CAP
                                :OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
            }
            var currNode=cast.head;
            int i=0;
            while(currNode!=null) {
                expectedArr[i]=currNode.val;
                currNode=currNode.next;
                ++i;
            }
        }

        @Override
        void copyByteListContents(){
            int expectedSize=this.expectedSize;
            final var cast=(ByteSnglLnkSeq)seq;
            var expectedArr=(byte[])this.expectedArr;
            int expectedCapacity;
            if((expectedCapacity=this.expectedCapacity)<expectedSize) {
                this.expectedArr=expectedArr=new byte[this.expectedCapacity=expectedArr == OmniArray.OfByte.DEFAULT_ARR
                        && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP?OmniArray.DEFAULT_ARR_SEQ_CAP
                                :OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
            }
            var currNode=cast.head;
            int i=0;
            while(currNode!=null) {
                expectedArr[i]=currNode.val;
                currNode=currNode.next;
                ++i;
            }
        }

        @Override
        void copyCharListContents(){
            int expectedSize=this.expectedSize;
            final var cast=(CharSnglLnkSeq)seq;
            var expectedArr=(char[])this.expectedArr;
            int expectedCapacity;
            if((expectedCapacity=this.expectedCapacity)<expectedSize) {
                this.expectedArr=expectedArr=new char[this.expectedCapacity=expectedArr == OmniArray.OfChar.DEFAULT_ARR
                        && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP?OmniArray.DEFAULT_ARR_SEQ_CAP
                                :OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
            }
            var currNode=cast.head;
            int i=0;
            while(currNode!=null) {
                expectedArr[i]=currNode.val;
                currNode=currNode.next;
                ++i;
            }
        }

        @Override
        void copyShortListContents(){
            int expectedSize=this.expectedSize;
            final var cast=(ShortSnglLnkSeq)seq;
            var expectedArr=(short[])this.expectedArr;
            int expectedCapacity;
            if((expectedCapacity=this.expectedCapacity)<expectedSize) {
                this.expectedArr=expectedArr=new short[this.expectedCapacity=expectedArr == OmniArray.OfShort.DEFAULT_ARR
                        && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP?OmniArray.DEFAULT_ARR_SEQ_CAP
                                :OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
            }
            var currNode=cast.head;
            int i=0;
            while(currNode!=null) {
                expectedArr[i]=currNode.val;
                currNode=currNode.next;
                ++i;
            }
        }

        @Override
        void copyIntListContents(){
            int expectedSize=this.expectedSize;
            final var cast=(IntSnglLnkSeq)seq;
            var expectedArr=(int[])this.expectedArr;
            int expectedCapacity;
            if((expectedCapacity=this.expectedCapacity)<expectedSize) {
                this.expectedArr=expectedArr=new int[this.expectedCapacity=expectedArr == OmniArray.OfInt.DEFAULT_ARR
                        && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP?OmniArray.DEFAULT_ARR_SEQ_CAP
                                :OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
            }
            var currNode=cast.head;
            int i=0;
            while(currNode!=null) {
                expectedArr[i]=currNode.val;
                currNode=currNode.next;
                ++i;
            }
        }

        @Override
        void copyLongListContents(){
            int expectedSize=this.expectedSize;
            final var cast=(LongSnglLnkSeq)seq;
            var expectedArr=(long[])this.expectedArr;
            int expectedCapacity;
            if((expectedCapacity=this.expectedCapacity)<expectedSize) {
                this.expectedArr=expectedArr=new long[this.expectedCapacity=expectedArr == OmniArray.OfLong.DEFAULT_ARR
                        && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP?OmniArray.DEFAULT_ARR_SEQ_CAP
                                :OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
            }
            var currNode=cast.head;
            int i=0;
            while(currNode!=null) {
                expectedArr[i]=currNode.val;
                currNode=currNode.next;
                ++i;
            }
        }

        @Override
        void copyFloatListContents(){
            int expectedSize=this.expectedSize;
            final var cast=(FloatSnglLnkSeq)seq;
            var expectedArr=(float[])this.expectedArr;
            int expectedCapacity;
            if((expectedCapacity=this.expectedCapacity)<expectedSize) {
                this.expectedArr=expectedArr=new float[this.expectedCapacity=expectedArr == OmniArray.OfFloat.DEFAULT_ARR
                        && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP?OmniArray.DEFAULT_ARR_SEQ_CAP
                                :OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
            }
            var currNode=cast.head;
            int i=0;
            while(currNode!=null) {
                expectedArr[i]=currNode.val;
                currNode=currNode.next;
                ++i;
            }
        }

        @Override
        void copyDoubleListContents(){
            int expectedSize=this.expectedSize;
            final var cast=(DoubleSnglLnkSeq)seq;
            var expectedArr=(double[])this.expectedArr;
            int expectedCapacity;
            if((expectedCapacity=this.expectedCapacity)<expectedSize) {
                this.expectedArr=expectedArr=new double[this.expectedCapacity=expectedArr == OmniArray.OfDouble.DEFAULT_ARR
                        && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP?OmniArray.DEFAULT_ARR_SEQ_CAP
                                :OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
            }
            var currNode=cast.head;
            int i=0;
            while(currNode!=null) {
                expectedArr[i]=currNode.val;
                currNode=currNode.next;
                ++i;
            }
        }

        @Override
        void copyRefListContents(){
            int expectedSize=this.expectedSize;
            final var cast=(RefSnglLnkSeq<?>)seq;
            var expectedArr=(Object[])this.expectedArr;
            int expectedCapacity;
            if((expectedCapacity=this.expectedCapacity)<expectedSize) {
                this.expectedArr=expectedArr=new Object[this.expectedCapacity=expectedArr == OmniArray.OfRef.DEFAULT_ARR
                        && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP?OmniArray.DEFAULT_ARR_SEQ_CAP
                                :OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
            }
            var currNode=cast.head;
            int i=0;
            while(currNode!=null) {
                expectedArr[i]=currNode.val;
                currNode=currNode.next;
                ++i;
            }
        }

        @SuppressWarnings("unchecked")
        @Override
        SEQ initSeq(){
            final var checked=checkedType.checked;
            switch(dataType) {
            case BOOLEAN:
                if(checked) {
                    return (SEQ)new BooleanSnglLnkSeq.CheckedQueue();
                }
                return (SEQ)new BooleanSnglLnkSeq.UncheckedQueue();
            case BYTE:
                if(checked) {
                    return (SEQ)new ByteSnglLnkSeq.CheckedQueue();
                }
                return (SEQ)new ByteSnglLnkSeq.UncheckedQueue();
            case CHAR:
                if(checked) {
                    return (SEQ)new CharSnglLnkSeq.CheckedQueue();
                }
                return (SEQ)new CharSnglLnkSeq.UncheckedQueue();
            case DOUBLE:
                if(checked) {
                    return (SEQ)new DoubleSnglLnkSeq.CheckedQueue();
                }
                return (SEQ)new DoubleSnglLnkSeq.UncheckedQueue();
            case FLOAT:
                if(checked) {
                    return (SEQ)new FloatSnglLnkSeq.CheckedQueue();
                }
                return (SEQ)new FloatSnglLnkSeq.UncheckedQueue();
            case INT:
                if(checked) {
                    return (SEQ)new IntSnglLnkSeq.CheckedQueue();
                }
                return (SEQ)new IntSnglLnkSeq.UncheckedQueue();
            case LONG:
                if(checked) {
                    return (SEQ)new LongSnglLnkSeq.CheckedQueue();
                }
                return (SEQ)new LongSnglLnkSeq.UncheckedQueue();
            case REF:
                if(checked) {
                    return (SEQ)new RefSnglLnkSeq.CheckedQueue<>();
                }
                return (SEQ)new RefSnglLnkSeq.UncheckedQueue<>();
            case SHORT:
                if(checked) {
                    return (SEQ)new ShortSnglLnkSeq.CheckedQueue();
                }
                return (SEQ)new ShortSnglLnkSeq.UncheckedQueue();
            default:
                throw dataType.invalid();
            }
        }

        @Override
        void updateModCount(){
            switch(dataType) {
            case BOOLEAN:
                this.expectedModCount=((BooleanSnglLnkSeq.CheckedQueue)seq).modCount;
                break;
            case BYTE:
                this.expectedModCount=((ByteSnglLnkSeq.CheckedQueue)seq).modCount;
                break;
            case CHAR:
                this.expectedModCount=((CharSnglLnkSeq.CheckedQueue)seq).modCount;
                break;
            case DOUBLE:
                this.expectedModCount=((DoubleSnglLnkSeq.CheckedQueue)seq).modCount;
                break;
            case FLOAT:
                this.expectedModCount=((FloatSnglLnkSeq.CheckedQueue)seq).modCount;
                break;
            case INT:
                this.expectedModCount=((IntSnglLnkSeq.CheckedQueue)seq).modCount;
                break;
            case LONG:
                this.expectedModCount=((LongSnglLnkSeq.CheckedQueue)seq).modCount;
                break;
            case REF:
                this.expectedModCount=((RefSnglLnkSeq.CheckedQueue<?>)seq).modCount;
                break;
            case SHORT:
                this.expectedModCount=((ShortSnglLnkSeq.CheckedQueue)seq).modCount;
                break;
            default:
                throw dataType.invalid();
            }
        }
        
    }
    private static class SnglLnkStackMonitor<SEQ extends AbstractSeq<E> & Externalizable & OmniStack<E>,E> extends AbstractSnglLnkSeqMonitor<SEQ> implements MonitoredStack<SEQ>{

        SnglLnkStackMonitor(CheckedType checkedType,DataType dataType,int initCap){
            super(checkedType,dataType,initCap);
        }

        SnglLnkStackMonitor(CheckedType checkedType,DataType dataType){
            super(checkedType,dataType);
        }

        class ItrMonitor extends AbstractSnglLnkSeqMonitor<SEQ>.AbstractItrMonitor{
            
            ItrMonitor(){
                super(expectedSize);
            }

            @Override
            public int getNumLeft(){
                return this.expectedCurrIndex;
            }

            @Override
            public void verifyForEachRemaining(MonitoredFunction function){
                
                int expectedCurrIndex=this.expectedCurrIndex;
                if(expectedCurrIndex>0) {
                    super.verifyForEachRemainingHelper(function);
                    this.expectedLastRetIndex=expectedSize-1;
                    this.expectedCurrIndex=expectedSize;
                }
            }

            @Override
            public void verifyCloneHelper(Object clone){
                final var checked=checkedType.checked;
                var itr=this.itr;
                switch(dataType) {
                case BOOLEAN:
                    Assertions.assertSame(expectedNext,FieldAndMethodAccessor.BooleanSnglLnkSeq.AbstractItr.next(itr));
                    Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.BooleanSnglLnkSeq.AbstractItr.curr(itr));
                    Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.BooleanSnglLnkSeq.AbstractItr.prev(itr));
                    if(checked) {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanSnglLnkSeq.CheckedStack.Itr.parent(itr));
                        Assertions.assertSame(expectedItrModCount,FieldAndMethodAccessor.BooleanSnglLnkSeq.CheckedStack.Itr.modCount(itr));
                    }else {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanSnglLnkSeq.UncheckedStack.Itr.parent(itr));
                    }
                    break;
                case BYTE:
                    Assertions.assertSame(expectedNext,FieldAndMethodAccessor.ByteSnglLnkSeq.AbstractItr.next(itr));
                    Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.ByteSnglLnkSeq.AbstractItr.curr(itr));
                    Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.ByteSnglLnkSeq.AbstractItr.prev(itr));
                    if(checked) {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.ByteSnglLnkSeq.CheckedStack.Itr.parent(itr));
                        Assertions.assertSame(expectedItrModCount,FieldAndMethodAccessor.ByteSnglLnkSeq.CheckedStack.Itr.modCount(itr));
                    }else {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.ByteSnglLnkSeq.UncheckedStack.Itr.parent(itr));
                    }
                    break;
                case CHAR:
                    Assertions.assertSame(expectedNext,FieldAndMethodAccessor.CharSnglLnkSeq.AbstractItr.next(itr));
                    Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.CharSnglLnkSeq.AbstractItr.curr(itr));
                    Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.CharSnglLnkSeq.AbstractItr.prev(itr));
                    if(checked) {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.CharSnglLnkSeq.CheckedStack.Itr.parent(itr));
                        Assertions.assertSame(expectedItrModCount,FieldAndMethodAccessor.CharSnglLnkSeq.CheckedStack.Itr.modCount(itr));
                    }else {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.CharSnglLnkSeq.UncheckedStack.Itr.parent(itr));
                    }
                    break;
                case DOUBLE:
                    Assertions.assertSame(expectedNext,FieldAndMethodAccessor.DoubleSnglLnkSeq.AbstractItr.next(itr));
                    Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.DoubleSnglLnkSeq.AbstractItr.curr(itr));
                    Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.DoubleSnglLnkSeq.AbstractItr.prev(itr));
                    if(checked) {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleSnglLnkSeq.CheckedStack.Itr.parent(itr));
                        Assertions.assertSame(expectedItrModCount,FieldAndMethodAccessor.DoubleSnglLnkSeq.CheckedStack.Itr.modCount(itr));
                    }else {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleSnglLnkSeq.UncheckedStack.Itr.parent(itr));
                    }
                    break;
                case FLOAT:
                    Assertions.assertSame(expectedNext,FieldAndMethodAccessor.FloatSnglLnkSeq.AbstractItr.next(itr));
                    Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.FloatSnglLnkSeq.AbstractItr.curr(itr));
                    Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.FloatSnglLnkSeq.AbstractItr.prev(itr));
                    if(checked) {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.FloatSnglLnkSeq.CheckedStack.Itr.parent(itr));
                        Assertions.assertSame(expectedItrModCount,FieldAndMethodAccessor.FloatSnglLnkSeq.CheckedStack.Itr.modCount(itr));
                    }else {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.FloatSnglLnkSeq.UncheckedStack.Itr.parent(itr));
                    }
                    break;
                case INT:
                    Assertions.assertSame(expectedNext,FieldAndMethodAccessor.IntSnglLnkSeq.AbstractItr.next(itr));
                    Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.IntSnglLnkSeq.AbstractItr.curr(itr));
                    Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.IntSnglLnkSeq.AbstractItr.prev(itr));
                    if(checked) {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.IntSnglLnkSeq.CheckedStack.Itr.parent(itr));
                        Assertions.assertSame(expectedItrModCount,FieldAndMethodAccessor.IntSnglLnkSeq.CheckedStack.Itr.modCount(itr));
                    }else {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.IntSnglLnkSeq.UncheckedStack.Itr.parent(itr));
                    }
                    break;
                case LONG:
                    Assertions.assertSame(expectedNext,FieldAndMethodAccessor.LongSnglLnkSeq.AbstractItr.next(itr));
                    Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.LongSnglLnkSeq.AbstractItr.curr(itr));
                    Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.LongSnglLnkSeq.AbstractItr.prev(itr));
                    if(checked) {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.LongSnglLnkSeq.CheckedStack.Itr.parent(itr));
                        Assertions.assertSame(expectedItrModCount,FieldAndMethodAccessor.LongSnglLnkSeq.CheckedStack.Itr.modCount(itr));
                    }else {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.LongSnglLnkSeq.UncheckedStack.Itr.parent(itr));
                    }
                    break;
                case REF:
                    Assertions.assertSame(expectedNext,FieldAndMethodAccessor.RefSnglLnkSeq.AbstractItr.next(itr));
                    Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.RefSnglLnkSeq.AbstractItr.curr(itr));
                    Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.RefSnglLnkSeq.AbstractItr.prev(itr));
                    if(checked) {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.RefSnglLnkSeq.CheckedStack.Itr.parent(itr));
                        Assertions.assertSame(expectedItrModCount,FieldAndMethodAccessor.RefSnglLnkSeq.CheckedStack.Itr.modCount(itr));
                    }else {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.RefSnglLnkSeq.UncheckedStack.Itr.parent(itr));
                    }
                    break;
                case SHORT:
                    Assertions.assertSame(expectedNext,FieldAndMethodAccessor.ShortSnglLnkSeq.AbstractItr.next(itr));
                    Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.ShortSnglLnkSeq.AbstractItr.curr(itr));
                    Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.ShortSnglLnkSeq.AbstractItr.prev(itr));
                    if(checked) {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.ShortSnglLnkSeq.CheckedStack.Itr.parent(itr));
                        Assertions.assertSame(expectedItrModCount,FieldAndMethodAccessor.ShortSnglLnkSeq.CheckedStack.Itr.modCount(itr));
                    }else {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.ShortSnglLnkSeq.UncheckedStack.Itr.parent(itr));
                    }
                    break;
                default:
                    throw dataType.invalid();
                }
            }

            @Override
            void updateItrNextIndex(){
                this.expectedLastRetIndex=--expectedCurrIndex;
            }
        }
        


        @Override
        public MonitoredIterator<? extends OmniIterator<?>,SEQ> getMonitoredIterator(){
            return new ItrMonitor();
        }

        @Override
        public StructType getStructType(){
            return StructType.SnglLnkStack;
        }

        @Override
        public void modCollection(){
            switch(dataType) {
            case BOOLEAN:
                ++((BooleanSnglLnkSeq.CheckedStack)seq).modCount;
                break;
            case BYTE:
                ++((ByteSnglLnkSeq.CheckedStack)seq).modCount;
                break;
            case CHAR:
                ++((CharSnglLnkSeq.CheckedStack)seq).modCount;
                break;
            case DOUBLE:
                ++((DoubleSnglLnkSeq.CheckedStack)seq).modCount;
                break;
            case FLOAT:
                ++((FloatSnglLnkSeq.CheckedStack)seq).modCount;
                break;
            case INT:
                ++((IntSnglLnkSeq.CheckedStack)seq).modCount;
                break;
            case LONG:
                ++((LongSnglLnkSeq.CheckedStack)seq).modCount;
                break;
            case REF:
                ++((RefSnglLnkSeq.CheckedStack<?>)seq).modCount;
                break;
            case SHORT:
                ++((ShortSnglLnkSeq.CheckedStack)seq).modCount;
                break;
            default:
                throw dataType.invalid();
            
            }
            ++expectedModCount;
        }

        @Override
        public void verifyPush(Object inputVal,DataType inputType,FunctionCallType functionCallType){
            inputType.callPush(inputVal,seq,functionCallType);
            updateAddState(expectedSize,inputVal,inputType);
            verifyCollectionState();
        }

        @Override
        public Object verifyPop(DataType outputType){
            Object result;
            Object expected=null;
            if(expectedSize!=0) {
                expected=super.get(expectedSize-1,outputType);
            }
            try {
                result=outputType.callPop(seq);
                super.updateRemoveIndexState(expectedSize-1);
            }finally {
                verifyCollectionState();
            }
            if(outputType==DataType.REF && dataType==DataType.REF) {
                Assertions.assertSame(expected,result);
            }else {
                Assertions.assertEquals(expected,result);
            }
            return result;
          }

        @Override
        public Object verifyPoll(DataType outputType){
            Object result;
            boolean isEmpty=expectedSize==0;
            Object expected=isEmpty?outputType.defaultVal:super.get(expectedSize-1);
            try{
                result=outputType.callPoll(seq);
                if(!isEmpty) {
                  super.updateRemoveIndexState(expectedSize-1);
                }
                
            }finally{
                verifyCollectionState();
            }
            Assertions.assertEquals(expected,result);
            return result;
        
          }

        @Override
        public Object verifyPeek(DataType outputType){
            Object result;
            boolean isEmpty=expectedSize==0;
            try {
                result=outputType.callPeek(seq);
            }finally {
                verifyCollectionState();
            }
            if(isEmpty) {
                Assertions.assertEquals(outputType.defaultVal,result);
            }else {
                super.verifyGetResult(expectedSize-1,result,outputType);
            }
            return result;
        }

        @Override
        void verifyBooleanIntegrity(){
            final int expectedSize;
            Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
            final var cast=(BooleanSnglLnkSeq)seq;
            final var expectedArr=(boolean[])this.expectedArr;
            if(checkedType.checked) {
                Assertions.assertEquals(expectedModCount,((BooleanSnglLnkSeq.CheckedStack)cast).modCount);
            }
            var headNode=cast.head;
            if(expectedSize!=0) {
                for(int i=expectedSize;--i>=0;) {
                    Assertions.assertEquals(expectedArr[i],headNode.val);
                    headNode=headNode.next;
                }
            }
            Assertions.assertNull(headNode);
        }

        @Override
        void verifyByteIntegrity(){
            final int expectedSize;
            Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
            final var cast=(ByteSnglLnkSeq)seq;
            final var expectedArr=(byte[])this.expectedArr;
            if(checkedType.checked) {
                Assertions.assertEquals(expectedModCount,((ByteSnglLnkSeq.CheckedStack)cast).modCount);
            }
            var headNode=cast.head;
            if(expectedSize!=0) {
                for(int i=expectedSize;--i>=0;) {
                    Assertions.assertEquals(expectedArr[i],headNode.val);
                    headNode=headNode.next;
                }
            }
            Assertions.assertNull(headNode);
        }

        @Override
        void verifyCharIntegrity(){
            final int expectedSize;
            Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
            final var cast=(CharSnglLnkSeq)seq;
            final var expectedArr=(char[])this.expectedArr;
            if(checkedType.checked) {
                Assertions.assertEquals(expectedModCount,((CharSnglLnkSeq.CheckedStack)cast).modCount);
            }
            var headNode=cast.head;
            if(expectedSize!=0) {
                for(int i=expectedSize;--i>=0;) {
                    Assertions.assertEquals(expectedArr[i],headNode.val);
                    headNode=headNode.next;
                }
            }
            Assertions.assertNull(headNode);
        }

        @Override
        void verifyShortIntegrity(){
            final int expectedSize;
            Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
            final var cast=(ShortSnglLnkSeq)seq;
            final var expectedArr=(short[])this.expectedArr;
            if(checkedType.checked) {
                Assertions.assertEquals(expectedModCount,((ShortSnglLnkSeq.CheckedStack)cast).modCount);
            }
            var headNode=cast.head;
            if(expectedSize!=0) {
                for(int i=expectedSize;--i>=0;) {
                    Assertions.assertEquals(expectedArr[i],headNode.val);
                    headNode=headNode.next;
                }
            }
            Assertions.assertNull(headNode);
        }

        @Override
        void verifyIntIntegrity(){
            final int expectedSize;
            Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
            final var cast=(IntSnglLnkSeq)seq;
            final var expectedArr=(int[])this.expectedArr;
            if(checkedType.checked) {
                Assertions.assertEquals(expectedModCount,((IntSnglLnkSeq.CheckedStack)cast).modCount);
            }
            var headNode=cast.head;
            if(expectedSize!=0) {
                for(int i=expectedSize;--i>=0;) {
                    Assertions.assertEquals(expectedArr[i],headNode.val);
                    headNode=headNode.next;
                }
            }
            Assertions.assertNull(headNode);
        }

        @Override
        void verifyLongIntegrity(){
            final int expectedSize;
            Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
            final var cast=(LongSnglLnkSeq)seq;
            final var expectedArr=(long[])this.expectedArr;
            if(checkedType.checked) {
                Assertions.assertEquals(expectedModCount,((LongSnglLnkSeq.CheckedStack)cast).modCount);
            }
            var headNode=cast.head;
            if(expectedSize!=0) {
                for(int i=expectedSize;--i>=0;) {
                    Assertions.assertEquals(expectedArr[i],headNode.val);
                    headNode=headNode.next;
                }
            }
            Assertions.assertNull(headNode);
        }

        @Override
        void verifyFloatIntegrity(){
            final int expectedSize;
            Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
            final var cast=(FloatSnglLnkSeq)seq;
            final var expectedArr=(float[])this.expectedArr;
            if(checkedType.checked) {
                Assertions.assertEquals(expectedModCount,((FloatSnglLnkSeq.CheckedStack)cast).modCount);
            }
            var headNode=cast.head;
            if(expectedSize!=0) {
                for(int i=expectedSize;--i>=0;) {
                    Assertions.assertEquals(expectedArr[i],headNode.val);
                    headNode=headNode.next;
                }
            }
            Assertions.assertNull(headNode);
        }

        @Override
        void verifyDoubleIntegrity(){
            final int expectedSize;
            Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
            final var cast=(DoubleSnglLnkSeq)seq;
            final var expectedArr=(double[])this.expectedArr;
            if(checkedType.checked) {
                Assertions.assertEquals(expectedModCount,((DoubleSnglLnkSeq.CheckedStack)cast).modCount);
            }
            var headNode=cast.head;
            if(expectedSize!=0) {
                for(int i=expectedSize;--i>=0;) {
                    Assertions.assertEquals(expectedArr[i],headNode.val);
                    headNode=headNode.next;
                }
            }
            Assertions.assertNull(headNode);
        }

        @Override
        void verifyRefIntegrity(){
            final int expectedSize;
            Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
            final var cast=(RefSnglLnkSeq<?>)seq;
            final var expectedArr=(Object[])this.expectedArr;
            if(checkedType.checked) {
                Assertions.assertEquals(expectedModCount,((RefSnglLnkSeq.CheckedStack<?>)cast).modCount);
            }
            var headNode=cast.head;
            if(expectedSize!=0) {
                for(int i=expectedSize;--i>=0;) {
                    Assertions.assertSame(expectedArr[i],headNode.val);
                    headNode=headNode.next;
                }
            }
            Assertions.assertNull(headNode);
        }

        @Override
        void verifyBooleanClone(Object clone){
            Assertions.assertNotSame(clone,seq);
            final var cloneCast=(BooleanSnglLnkSeq)clone;
            int size;
            Assertions.assertEquals(size=seq.size,cloneCast.size);
            Assertions.assertEquals(checkedType.checked,cloneCast instanceof BooleanSnglLnkSeq.CheckedStack);
            if(checkedType.checked){
                Assertions.assertEquals(0,((BooleanSnglLnkSeq.CheckedStack)cloneCast).modCount);
            }else{
                Assertions.assertTrue(cloneCast instanceof BooleanSnglLnkSeq.UncheckedStack);
            }
            var cloneHead=cloneCast.head;
            var thisHead=((BooleanSnglLnkSeq)seq).head;
            while(--size>=0) {
                Assertions.assertNotSame(cloneHead,thisHead);
                Assertions.assertEquals(cloneHead.val,thisHead.val);
                cloneHead=cloneHead.next;
                thisHead=thisHead.next;
            }
            Assertions.assertNull(cloneHead);
            Assertions.assertNull(thisHead);
        }

        @Override
        void verifyByteClone(Object clone){
            Assertions.assertNotSame(clone,seq);
            final var cloneCast=(ByteSnglLnkSeq)clone;
            int size;
            Assertions.assertEquals(size=seq.size,cloneCast.size);
            Assertions.assertEquals(checkedType.checked,cloneCast instanceof ByteSnglLnkSeq.CheckedStack);
            if(checkedType.checked){
                Assertions.assertEquals(0,((ByteSnglLnkSeq.CheckedStack)cloneCast).modCount);
            }else{
                Assertions.assertTrue(cloneCast instanceof ByteSnglLnkSeq.UncheckedStack);
            }
            var cloneHead=cloneCast.head;
            var thisHead=((ByteSnglLnkSeq)seq).head;
            while(--size>=0) {
                Assertions.assertNotSame(cloneHead,thisHead);
                Assertions.assertEquals(cloneHead.val,thisHead.val);
                cloneHead=cloneHead.next;
                thisHead=thisHead.next;
            }
            Assertions.assertNull(cloneHead);
            Assertions.assertNull(thisHead);
        }

        @Override
        void verifyCharClone(Object clone){
            Assertions.assertNotSame(clone,seq);
            final var cloneCast=(CharSnglLnkSeq)clone;
            int size;
            Assertions.assertEquals(size=seq.size,cloneCast.size);
            Assertions.assertEquals(checkedType.checked,cloneCast instanceof CharSnglLnkSeq.CheckedStack);
            if(checkedType.checked){
                Assertions.assertEquals(0,((CharSnglLnkSeq.CheckedStack)cloneCast).modCount);
            }else{
                Assertions.assertTrue(cloneCast instanceof CharSnglLnkSeq.UncheckedStack);
            }
            var cloneHead=cloneCast.head;
            var thisHead=((CharSnglLnkSeq)seq).head;
            while(--size>=0) {
                Assertions.assertNotSame(cloneHead,thisHead);
                Assertions.assertEquals(cloneHead.val,thisHead.val);
                cloneHead=cloneHead.next;
                thisHead=thisHead.next;
            }
            Assertions.assertNull(cloneHead);
            Assertions.assertNull(thisHead);
        }

        @Override
        void verifyShortClone(Object clone){
            Assertions.assertNotSame(clone,seq);
            final var cloneCast=(ShortSnglLnkSeq)clone;
            int size;
            Assertions.assertEquals(size=seq.size,cloneCast.size);
            Assertions.assertEquals(checkedType.checked,cloneCast instanceof ShortSnglLnkSeq.CheckedStack);
            if(checkedType.checked){
                Assertions.assertEquals(0,((ShortSnglLnkSeq.CheckedStack)cloneCast).modCount);
            }else{
                Assertions.assertTrue(cloneCast instanceof ShortSnglLnkSeq.UncheckedStack);
            }
            var cloneHead=cloneCast.head;
            var thisHead=((ShortSnglLnkSeq)seq).head;
            while(--size>=0) {
                Assertions.assertNotSame(cloneHead,thisHead);
                Assertions.assertEquals(cloneHead.val,thisHead.val);
                cloneHead=cloneHead.next;
                thisHead=thisHead.next;
            }
            Assertions.assertNull(cloneHead);
            Assertions.assertNull(thisHead);
        }

        @Override
        void verifyIntClone(Object clone){
            Assertions.assertNotSame(clone,seq);
            final var cloneCast=(IntSnglLnkSeq)clone;
            int size;
            Assertions.assertEquals(size=seq.size,cloneCast.size);
            Assertions.assertEquals(checkedType.checked,cloneCast instanceof IntSnglLnkSeq.CheckedStack);
            if(checkedType.checked){
                Assertions.assertEquals(0,((IntSnglLnkSeq.CheckedStack)cloneCast).modCount);
            }else{
                Assertions.assertTrue(cloneCast instanceof IntSnglLnkSeq.UncheckedStack);
            }
            var cloneHead=cloneCast.head;
            var thisHead=((IntSnglLnkSeq)seq).head;
            while(--size>=0) {
                Assertions.assertNotSame(cloneHead,thisHead);
                Assertions.assertEquals(cloneHead.val,thisHead.val);
                cloneHead=cloneHead.next;
                thisHead=thisHead.next;
            }
            Assertions.assertNull(cloneHead);
            Assertions.assertNull(thisHead);
        }

        @Override
        void verifyLongClone(Object clone){
            Assertions.assertNotSame(clone,seq);
            final var cloneCast=(LongSnglLnkSeq)clone;
            int size;
            Assertions.assertEquals(size=seq.size,cloneCast.size);
            Assertions.assertEquals(checkedType.checked,cloneCast instanceof LongSnglLnkSeq.CheckedStack);
            if(checkedType.checked){
                Assertions.assertEquals(0,((LongSnglLnkSeq.CheckedStack)cloneCast).modCount);
            }else{
                Assertions.assertTrue(cloneCast instanceof LongSnglLnkSeq.UncheckedStack);
            }
            var cloneHead=cloneCast.head;
            var thisHead=((LongSnglLnkSeq)seq).head;
            while(--size>=0) {
                Assertions.assertNotSame(cloneHead,thisHead);
                Assertions.assertEquals(cloneHead.val,thisHead.val);
                cloneHead=cloneHead.next;
                thisHead=thisHead.next;
            }
            Assertions.assertNull(cloneHead);
            Assertions.assertNull(thisHead);
        }

        @Override
        void verifyFloatClone(Object clone){
            Assertions.assertNotSame(clone,seq);
            final var cloneCast=(FloatSnglLnkSeq)clone;
            int size;
            Assertions.assertEquals(size=seq.size,cloneCast.size);
            Assertions.assertEquals(checkedType.checked,cloneCast instanceof FloatSnglLnkSeq.CheckedStack);
            if(checkedType.checked){
                Assertions.assertEquals(0,((FloatSnglLnkSeq.CheckedStack)cloneCast).modCount);
            }else{
                Assertions.assertTrue(cloneCast instanceof FloatSnglLnkSeq.UncheckedStack);
            }
            var cloneHead=cloneCast.head;
            var thisHead=((FloatSnglLnkSeq)seq).head;
            while(--size>=0) {
                Assertions.assertNotSame(cloneHead,thisHead);
                Assertions.assertEquals(cloneHead.val,thisHead.val);
                cloneHead=cloneHead.next;
                thisHead=thisHead.next;
            }
            Assertions.assertNull(cloneHead);
            Assertions.assertNull(thisHead);
        }

        @Override
        void verifyDoubleClone(Object clone){
            Assertions.assertNotSame(clone,seq);
            final var cloneCast=(DoubleSnglLnkSeq)clone;
            int size;
            Assertions.assertEquals(size=seq.size,cloneCast.size);
            Assertions.assertEquals(checkedType.checked,cloneCast instanceof DoubleSnglLnkSeq.CheckedStack);
            if(checkedType.checked){
                Assertions.assertEquals(0,((DoubleSnglLnkSeq.CheckedStack)cloneCast).modCount);
            }else{
                Assertions.assertTrue(cloneCast instanceof DoubleSnglLnkSeq.UncheckedStack);
            }
            var cloneHead=cloneCast.head;
            var thisHead=((DoubleSnglLnkSeq)seq).head;
            while(--size>=0) {
                Assertions.assertNotSame(cloneHead,thisHead);
                Assertions.assertEquals(cloneHead.val,thisHead.val);
                cloneHead=cloneHead.next;
                thisHead=thisHead.next;
            }
            Assertions.assertNull(cloneHead);
            Assertions.assertNull(thisHead);
        }

        @Override
        void verifyRefClone(Object clone,boolean refIsSame){
            Assertions.assertNotSame(clone,seq);
            final var cloneCast=(RefSnglLnkSeq<?>)clone;
            int size;
            Assertions.assertEquals(size=seq.size,cloneCast.size);
            Assertions.assertEquals(checkedType.checked,cloneCast instanceof RefSnglLnkSeq.CheckedStack);
            if(checkedType.checked){
                Assertions.assertEquals(0,((RefSnglLnkSeq.CheckedStack<?>)cloneCast).modCount);
            }else{
                Assertions.assertTrue(cloneCast instanceof RefSnglLnkSeq.UncheckedStack);
            }
            var cloneHead=cloneCast.head;
            var thisHead=((RefSnglLnkSeq<?>)seq).head;
            if(refIsSame) {
                while(--size>=0) {
                    Assertions.assertNotSame(cloneHead,thisHead);
                    Assertions.assertSame(cloneHead.val,thisHead.val);
                    cloneHead=cloneHead.next;
                    thisHead=thisHead.next;
                }
            }else {
                while(--size>=0) {
                    Assertions.assertNotSame(cloneHead,thisHead);
                    Assertions.assertEquals(cloneHead.val,thisHead.val);
                    cloneHead=cloneHead.next;
                    thisHead=thisHead.next;
                }
            }
            Assertions.assertNull(cloneHead);
            Assertions.assertNull(thisHead);
        }

        @Override
        void copyBooleanListContents(){
            int expectedSize=this.expectedSize;
            final var cast=(BooleanSnglLnkSeq)seq;
            var expectedArr=(boolean[])this.expectedArr;
            int expectedCapacity;
            if((expectedCapacity=this.expectedCapacity)<expectedSize) {
                this.expectedArr=expectedArr=new boolean[this.expectedCapacity=expectedArr == OmniArray.OfBoolean.DEFAULT_ARR
                        && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP?OmniArray.DEFAULT_ARR_SEQ_CAP
                                :OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
            }
            var currNode=cast.head;
            while(currNode!=null) {
                expectedArr[--expectedSize]=currNode.val;
                currNode=currNode.next;
            }
        }

        @Override
        void copyByteListContents(){
            int expectedSize=this.expectedSize;
            final var cast=(ByteSnglLnkSeq)seq;
            var expectedArr=(byte[])this.expectedArr;
            int expectedCapacity;
            if((expectedCapacity=this.expectedCapacity)<expectedSize) {
                this.expectedArr=expectedArr=new byte[this.expectedCapacity=expectedArr == OmniArray.OfByte.DEFAULT_ARR
                        && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP?OmniArray.DEFAULT_ARR_SEQ_CAP
                                :OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
            }
            var currNode=cast.head;
            while(currNode!=null) {
                expectedArr[--expectedSize]=currNode.val;
                currNode=currNode.next;
            }
        }

        @Override
        void copyCharListContents(){
            int expectedSize=this.expectedSize;
            final var cast=(CharSnglLnkSeq)seq;
            var expectedArr=(char[])this.expectedArr;
            int expectedCapacity;
            if((expectedCapacity=this.expectedCapacity)<expectedSize) {
                this.expectedArr=expectedArr=new char[this.expectedCapacity=expectedArr == OmniArray.OfChar.DEFAULT_ARR
                        && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP?OmniArray.DEFAULT_ARR_SEQ_CAP
                                :OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
            }
            var currNode=cast.head;
            while(currNode!=null) {
                expectedArr[--expectedSize]=currNode.val;
                currNode=currNode.next;
            }
        }

        @Override
        void copyShortListContents(){
            int expectedSize=this.expectedSize;
            final var cast=(ShortSnglLnkSeq)seq;
            var expectedArr=(short[])this.expectedArr;
            int expectedCapacity;
            if((expectedCapacity=this.expectedCapacity)<expectedSize) {
                this.expectedArr=expectedArr=new short[this.expectedCapacity=expectedArr == OmniArray.OfShort.DEFAULT_ARR
                        && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP?OmniArray.DEFAULT_ARR_SEQ_CAP
                                :OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
            }
            var currNode=cast.head;
            while(currNode!=null) {
                expectedArr[--expectedSize]=currNode.val;
                currNode=currNode.next;
            }
        }

        @Override
        void copyIntListContents(){
            int expectedSize=this.expectedSize;
            final var cast=(IntSnglLnkSeq)seq;
            var expectedArr=(int[])this.expectedArr;
            int expectedCapacity;
            if((expectedCapacity=this.expectedCapacity)<expectedSize) {
                this.expectedArr=expectedArr=new int[this.expectedCapacity=expectedArr == OmniArray.OfInt.DEFAULT_ARR
                        && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP?OmniArray.DEFAULT_ARR_SEQ_CAP
                                :OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
            }
            var currNode=cast.head;
            while(currNode!=null) {
                expectedArr[--expectedSize]=currNode.val;
                currNode=currNode.next;
            }
        }

        @Override
        void copyLongListContents(){
            int expectedSize=this.expectedSize;
            final var cast=(LongSnglLnkSeq)seq;
            var expectedArr=(long[])this.expectedArr;
            int expectedCapacity;
            if((expectedCapacity=this.expectedCapacity)<expectedSize) {
                this.expectedArr=expectedArr=new long[this.expectedCapacity=expectedArr == OmniArray.OfLong.DEFAULT_ARR
                        && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP?OmniArray.DEFAULT_ARR_SEQ_CAP
                                :OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
            }
            var currNode=cast.head;
            while(currNode!=null) {
                expectedArr[--expectedSize]=currNode.val;
                currNode=currNode.next;
            }
        }

        @Override
        void copyFloatListContents(){
            int expectedSize=this.expectedSize;
            final var cast=(FloatSnglLnkSeq)seq;
            var expectedArr=(float[])this.expectedArr;
            int expectedCapacity;
            if((expectedCapacity=this.expectedCapacity)<expectedSize) {
                this.expectedArr=expectedArr=new float[this.expectedCapacity=expectedArr == OmniArray.OfFloat.DEFAULT_ARR
                        && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP?OmniArray.DEFAULT_ARR_SEQ_CAP
                                :OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
            }
            var currNode=cast.head;
            while(currNode!=null) {
                expectedArr[--expectedSize]=currNode.val;
                currNode=currNode.next;
            }
        }

        @Override
        void copyDoubleListContents(){
            int expectedSize=this.expectedSize;
            final var cast=(DoubleSnglLnkSeq)seq;
            var expectedArr=(double[])this.expectedArr;
            int expectedCapacity;
            if((expectedCapacity=this.expectedCapacity)<expectedSize) {
                this.expectedArr=expectedArr=new double[this.expectedCapacity=expectedArr == OmniArray.OfDouble.DEFAULT_ARR
                        && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP?OmniArray.DEFAULT_ARR_SEQ_CAP
                                :OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
            }
            var currNode=cast.head;
            while(currNode!=null) {
                expectedArr[--expectedSize]=currNode.val;
                currNode=currNode.next;
            }
        }

        @Override
        void copyRefListContents(){
            int expectedSize=this.expectedSize;
            final var cast=(RefSnglLnkSeq<?>)seq;
            var expectedArr=(Object[])this.expectedArr;
            int expectedCapacity;
            if((expectedCapacity=this.expectedCapacity)<expectedSize) {
                this.expectedArr=expectedArr=new Object[this.expectedCapacity=expectedArr == OmniArray.OfRef.DEFAULT_ARR
                        && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP?OmniArray.DEFAULT_ARR_SEQ_CAP
                                :OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
            }
            var currNode=cast.head;
            while(currNode!=null) {
                expectedArr[--expectedSize]=currNode.val;
                currNode=currNode.next;
            }
        }

        @SuppressWarnings("unchecked")
        @Override
        SEQ initSeq(){
            final var checked=checkedType.checked;
            switch(dataType) {
            case BOOLEAN:
                if(checked) {
                    return (SEQ)new BooleanSnglLnkSeq.CheckedStack();
                }
                return (SEQ)new BooleanSnglLnkSeq.UncheckedStack();
            case BYTE:
                if(checked) {
                    return (SEQ)new ByteSnglLnkSeq.CheckedStack();
                }
                return (SEQ)new ByteSnglLnkSeq.UncheckedStack();
            case CHAR:
                if(checked) {
                    return (SEQ)new CharSnglLnkSeq.CheckedStack();
                }
                return (SEQ)new CharSnglLnkSeq.UncheckedStack();
            case DOUBLE:
                if(checked) {
                    return (SEQ)new DoubleSnglLnkSeq.CheckedStack();
                }
                return (SEQ)new DoubleSnglLnkSeq.UncheckedStack();
            case FLOAT:
                if(checked) {
                    return (SEQ)new FloatSnglLnkSeq.CheckedStack();
                }
                return (SEQ)new FloatSnglLnkSeq.UncheckedStack();
            case INT:
                if(checked) {
                    return (SEQ)new IntSnglLnkSeq.CheckedStack();
                }
                return (SEQ)new IntSnglLnkSeq.UncheckedStack();
            case LONG:
                if(checked) {
                    return (SEQ)new LongSnglLnkSeq.CheckedStack();
                }
                return (SEQ)new LongSnglLnkSeq.UncheckedStack();
            case REF:
                if(checked) {
                    return (SEQ)new RefSnglLnkSeq.CheckedStack<>();
                }
                return (SEQ)new RefSnglLnkSeq.UncheckedStack<>();
            case SHORT:
                if(checked) {
                    return (SEQ)new ShortSnglLnkSeq.CheckedStack();
                }
                return (SEQ)new ShortSnglLnkSeq.UncheckedStack();
            default:
                throw dataType.invalid();
            }
        }

        @Override
        void updateModCount(){
            switch(dataType) {
            case BOOLEAN:
                this.expectedModCount=((BooleanSnglLnkSeq.CheckedStack)seq).modCount;
                break;
            case BYTE:
                this.expectedModCount=((ByteSnglLnkSeq.CheckedStack)seq).modCount;
                break;
            case CHAR:
                this.expectedModCount=((CharSnglLnkSeq.CheckedStack)seq).modCount;
                break;
            case DOUBLE:
                this.expectedModCount=((DoubleSnglLnkSeq.CheckedStack)seq).modCount;
                break;
            case FLOAT:
                this.expectedModCount=((FloatSnglLnkSeq.CheckedStack)seq).modCount;
                break;
            case INT:
                this.expectedModCount=((IntSnglLnkSeq.CheckedStack)seq).modCount;
                break;
            case LONG:
                this.expectedModCount=((LongSnglLnkSeq.CheckedStack)seq).modCount;
                break;
            case REF:
                this.expectedModCount=((RefSnglLnkSeq.CheckedStack<?>)seq).modCount;
                break;
            case SHORT:
                this.expectedModCount=((ShortSnglLnkSeq.CheckedStack)seq).modCount;
                break;
            default:
                throw dataType.invalid();
            }
        }
        
    }
    private static abstract class AbstractSnglLnkSeqMonitor<SEQ extends AbstractSeq<?> & Externalizable> extends AbstractSequenceMonitor<SEQ>{

        AbstractSnglLnkSeqMonitor(CheckedType checkedType,DataType dataType,int initCap){
            super(checkedType,dataType,initCap);
        }

        AbstractSnglLnkSeqMonitor(CheckedType checkedType,DataType dataType){
            super(checkedType,dataType);
        }

        @Override
        public MonitoredIterator<? extends OmniIterator<?>,SEQ> getMonitoredIterator(int index,IteratorType itrType){
            var itrMonitor=getMonitoredIterator(itrType);
            while(index>0 && itrMonitor.hasNext()) {
                itrMonitor.iterateForward();
            }
            return itrMonitor;
        }

        @Override
        public MonitoredIterator<? extends OmniIterator<?>,SEQ> getMonitoredIterator(IteratorType itrType){
            if(itrType!=IteratorType.AscendingItr) {
                throw itrType.invalid();
            }
            return getMonitoredIterator();
        }
        
        private Object getNext(Object node) {
            switch(dataType) {
            case BOOLEAN:
                return ((BooleanSnglLnkNode)node).next;
            case BYTE:
                return ((ByteSnglLnkNode)node).next;
            case CHAR:
                return ((CharSnglLnkNode)node).next;
            case DOUBLE:
                return ((DoubleSnglLnkNode)node).next;
            case FLOAT:
                return ((FloatSnglLnkNode)node).next;
            case INT:
                return ((IntSnglLnkNode)node).next;
            case LONG:
                return ((LongSnglLnkNode)node).next;
            case REF:
                return ((RefSnglLnkNode<?>)node).next;
            case SHORT:
                return ((ShortSnglLnkNode)node).next;
            default:
                throw dataType.invalid();
            }
        }
        
        private Object getHead() {
            switch(dataType) {
            case BOOLEAN:
                return ((BooleanSnglLnkSeq)seq).head;
            case BYTE:
                return ((ByteSnglLnkSeq)seq).head;
            case CHAR:
                return ((CharSnglLnkSeq)seq).head;
            case DOUBLE:
                return ((DoubleSnglLnkSeq)seq).head;
            case FLOAT:
                return ((FloatSnglLnkSeq)seq).head;
            case INT:
                return ((IntSnglLnkSeq)seq).head;
            case LONG:
                return ((LongSnglLnkSeq)seq).head;
            case REF:
                return ((RefSnglLnkSeq<?>)seq).head;
            case SHORT:
                return ((ShortSnglLnkSeq)seq).head;
            default:
                throw dataType.invalid();
            }
        }
        
        abstract class AbstractItrMonitor implements MonitoredCollection.MonitoredIterator<OmniIterator<?>,SEQ>{
            final OmniIterator<?> itr;
            Object expectedPrev;
            Object expectedCurr;
            Object expectedNext;
            int expectedItrModCount;
            int expectedCurrIndex;
            int expectedLastRetIndex;
            AbstractItrMonitor(int expectedCurrIndex){
                this.itr=seq.iterator();
                this.expectedNext=getHead();
                this.expectedItrModCount=expectedModCount;
                this.expectedCurrIndex=expectedCurrIndex;
                this.expectedLastRetIndex=-1;
            }
            private void verifyForEachRemainingHelper(MonitoredFunction function) {
                final var itr=function.iterator();
                Object curr=this.expectedCurr;
                Object prev;
                switch(dataType) {
                case BOOLEAN:{
                    var next=(BooleanSnglLnkNode)expectedNext;
                    do {
                        Assertions.assertEquals(next.val,(boolean)itr.next());
                        prev=curr;
                        curr=next;
                    }while((next=next.next)!=null);
                    break;
                }
                case BYTE:{
                    var next=(ByteSnglLnkNode)expectedNext;
                    do {
                        Assertions.assertEquals(next.val,(byte)itr.next());
                        prev=curr;
                        curr=next;
                    }while((next=next.next)!=null);
                    break;
                }
                case CHAR:{
                    var next=(CharSnglLnkNode)expectedNext;
                    do {
                        Assertions.assertEquals(next.val,(char)itr.next());
                        prev=curr;
                        curr=next;
                    }while((next=next.next)!=null);
                    break;
                }
                case DOUBLE:{
                    var next=(DoubleSnglLnkNode)expectedNext;
                    do {
                        Assertions.assertEquals(next.val,(double)itr.next());
                        prev=curr;
                        curr=next;
                    }while((next=next.next)!=null);
                    break;
                }
                case FLOAT:{
                    var next=(FloatSnglLnkNode)expectedNext;
                    do {
                        Assertions.assertEquals(next.val,(float)itr.next());
                        prev=curr;
                        curr=next;
                    }while((next=next.next)!=null);
                    break;
                }
                case INT:{
                    var next=(IntSnglLnkNode)expectedNext;
                    do {
                        Assertions.assertEquals(next.val,(int)itr.next());
                        prev=curr;
                        curr=next;
                    }while((next=next.next)!=null);
                    break;
                }
                case LONG:{
                    var next=(LongSnglLnkNode)expectedNext;
                    do {
                        Assertions.assertEquals(next.val,(long)itr.next());
                        prev=curr;
                        curr=next;
                    }while((next=next.next)!=null);
                    break;
                }
                case REF:{
                    var next=(RefSnglLnkNode<?>)expectedNext;
                    do {
                        Assertions.assertSame(next.val,itr.next());
                        prev=curr;
                        curr=next;
                    }while((next=next.next)!=null);
                    break;
                }
                case SHORT:{
                    var next=(ShortSnglLnkNode)expectedNext;
                    do {
                        Assertions.assertEquals(next.val,(short)itr.next());
                        prev=curr;
                        curr=next;
                    }while((next=next.next)!=null);
                    break;
                }
                default:
                    throw dataType.invalid();
                }
                this.expectedNext=null;
                this.expectedCurr=curr;
                this.expectedPrev=prev;
            }
            abstract void updateItrNextIndex();
            @Override
            public void updateItrNextState(){
              Object newCurr;
              this.expectedNext=getNext( newCurr=this.expectedNext);
              this.expectedPrev=this.expectedCurr;
              this.expectedCurr=newCurr;
              updateItrNextIndex();
            }

            @Override
            public void updateItrRemoveState(){
                updateRemoveIndexState(expectedCurrIndex=expectedLastRetIndex);
                this.expectedCurr=this.expectedPrev;
                ++expectedItrModCount;
                this.expectedLastRetIndex=-1;
            }
            @Override
            public boolean nextWasJustCalled(){
                return this.expectedLastRetIndex!=-1;
            }
          
            @Override
            public void verifyNextResult(DataType outputType,Object result){
                verifyGetResult(expectedLastRetIndex,result,outputType);
            }
          
            @Override
            public OmniIterator<?> getIterator(){
                return itr;
            }
            @Override
            public IteratorType getIteratorType(){
                return IteratorType.AscendingItr;
            }
            @Override
            public MonitoredCollection<SEQ> getMonitoredCollection(){
                return AbstractSnglLnkSeqMonitor.this;
            }
            @Override
            public boolean hasNext(){
                return expectedNext!=null;
            }
        }
        
        @Override
        SEQ initSeq(int initCap){
            return initSeq();
        }
        abstract void verifyBooleanIntegrity();
        abstract void verifyByteIntegrity();
        abstract void verifyCharIntegrity();
        abstract void verifyShortIntegrity();
        abstract void verifyIntIntegrity();
        abstract void verifyLongIntegrity();
        abstract void verifyFloatIntegrity();
        abstract void verifyDoubleIntegrity();
        abstract void verifyRefIntegrity();
        abstract void verifyBooleanClone(Object clone);
        abstract void verifyByteClone(Object clone);
        abstract void verifyCharClone(Object clone);
        abstract void verifyShortClone(Object clone);
        abstract void verifyIntClone(Object clone);
        abstract void verifyLongClone(Object clone);
        abstract void verifyFloatClone(Object clone);
        abstract void verifyDoubleClone(Object clone);
        abstract void verifyRefClone(Object clone,boolean refIsSame);
        abstract void copyBooleanListContents();
        abstract void copyByteListContents();
        abstract void copyCharListContents();
        abstract void copyShortListContents();
        abstract void copyIntListContents();
        abstract void copyLongListContents();
        abstract void copyFloatListContents();
        abstract void copyDoubleListContents();
        abstract void copyRefListContents();

        @Override
        public void verifyCollectionState(){
            switch(dataType){
            case BOOLEAN:{
                verifyBooleanIntegrity();
                break;
            }
            case BYTE:{
                verifyByteIntegrity();
                break;
            }
            case CHAR:{
                verifyCharIntegrity();
                break;
            }
            case DOUBLE:{
                verifyDoubleIntegrity();
                break;
            }
            case FLOAT:{
                verifyFloatIntegrity();
                break;
            }
            case INT:{
                verifyIntIntegrity();
                break;
            }
            case LONG:{
                verifyLongIntegrity();
                break;
            }
            case REF:{
                verifyRefIntegrity();
                break;
            }
            case SHORT:{
                verifyShortIntegrity();
                break;
            }
            default:
                throw dataType.invalid();
            }
        }

        @Override
        public void verifyClone(Object clone){
            switch(dataType){
            case BOOLEAN:{
                verifyBooleanClone(clone);
                break;
            }
            case BYTE:{
                verifyByteClone(clone);
                break;
            }
            case CHAR:{
                verifyCharClone(clone);
                break;
            }
            case DOUBLE:{
                verifyDoubleClone(clone);
                break;
            }
            case FLOAT:{
                verifyFloatClone(clone);
                break;
            }
            case INT:{
                verifyIntClone(clone);
                break;
            }
            case LONG:{
                verifyLongClone(clone);
                break;
            }
            case REF:{
                verifyRefClone(clone,true);
                break;
            }
            case SHORT:{
                verifyShortClone(clone);
                break;
            }
            default:{
                throw dataType.invalid();
            }
            }
        }

        @Override
        public void verifyArrayIsCopy(Object arr,boolean emptyArrayMayBeSame){
            //nothing to do
        }

        @Override
        public void copyListContents(){
            switch(dataType){
            case BOOLEAN:{
                copyBooleanListContents();
                break;
            }
            case BYTE:{
                copyByteListContents();
                break;
            }
            case CHAR:{
                copyCharListContents();
                break;
            }
            case DOUBLE:{
                copyDoubleListContents();
                break;
            }
            case FLOAT:{
                copyFloatListContents();
                break;
            }
            case INT:{
                copyIntListContents();
                break;
            }
            case LONG:{
                copyLongListContents();
                break;
            }
            case REF:{
                copyRefListContents();
                break;
            }
            case SHORT:{
                copyShortListContents();
                break;
            }
            default:
                throw dataType.invalid();
            }
        }

      
        
    }
}
