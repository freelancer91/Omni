package omni.impl.set;
import java.util.NoSuchElementException;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import org.junit.jupiter.api.Assertions;
import omni.impl.CheckedType;
import omni.impl.DataType;
import omni.impl.FunctionCallType;
import omni.impl.IllegalModification;
import omni.impl.IteratorRemoveScenario;
import omni.impl.IteratorType;
import omni.impl.MonitoredFunctionGen;
import omni.impl.MonitoredRemoveIfPredicateGen;
import omni.impl.QueryCastType;
import omni.impl.QueryVal;
import omni.impl.StructType;
import omni.util.TestExecutorService;
public class ByteSetImplTest{
    private static final long[] WORDSTATES=new long[]{0x0000000000000000L,0x0000000000000001L,0x0000000000000002L,
            0x0000000000000004L,0x7fffffffffffffffL,0x8000000000000000L,0xfffffffffffffffeL,0xffffffffffffffffL,};
    private static final double[] RANDOM_THRESHOLDS=new double[]{0.01,0.05,0.10,0.25,0.50,0.75,0.90,0.95,0.99};
    private static final double[] NON_RANDOM_THRESHOLD=new double[]{0.5};
    private static int getSize(long...words){
        int size=0;
        for(final var word:words){
            size+=Long.bitCount(word);
        }
        return size;
    }
    private static void testforEach_ConsumerHelper(MonitoredFunctionGen functionGen,FunctionCallType functionCallType,
            long randSeed,CheckedType checkedType,long...expectedWords){
        final var monitor=new ByteSetImplMonitor(checkedType,expectedWords);
        if(functionGen.expectedException == null || monitor.size() == 0){
            monitor.verifyForEach(functionGen,functionCallType,randSeed);
        }else{
            Assertions.assertThrows(functionGen.expectedException,
                    ()->monitor.verifyForEach(functionGen,functionCallType,randSeed));
            monitor.verifyCollectionState();
        }
    }
    private static void testItrforEachRemaining_ConsumerHelper(int itrScenario,IllegalModification preMod,
            MonitoredFunctionGen functionGen,FunctionCallType functionCallType,long randSeed,CheckedType checkedType,
            long...expectedWords){
        final var setMonitor=new ByteSetImplMonitor(checkedType,expectedWords);
        final var itrMonitor=setMonitor.getMonitoredIterator();
        switch(itrScenario){
        case 1:
            itrMonitor.iterateForward();
            break;
        case 2:
            itrMonitor.iterateForward();
            itrMonitor.remove();
        default:
        }
        final int numLeft=itrMonitor.getNumLeft();
        itrMonitor.illegalMod(preMod);
        final Class<? extends Throwable> expectedException=numLeft == 0?null
                :preMod.expectedException == null?functionGen.expectedException:preMod.expectedException;
        if(expectedException == null){
            itrMonitor.verifyForEachRemaining(functionGen,functionCallType,randSeed);
        }else{
            Assertions.assertThrows(expectedException,
                    ()->itrMonitor.verifyForEachRemaining(functionGen,functionCallType,randSeed));
            itrMonitor.verifyIteratorState();
            setMonitor.verifyCollectionState();
        }
    }
    private static void testItrnext_voidHelper(IllegalModification preMod,DataType outputType,CheckedType checkedType,
            long...expectedWords){
        final var setMonitor=new ByteSetImplMonitor(checkedType,expectedWords);
        final var itrMonitor=setMonitor.getMonitoredIterator();
        itrMonitor.illegalMod(preMod);
        if(preMod.expectedException == null){
            while(itrMonitor.hasNext()){
                itrMonitor.verifyNext(outputType);
            }
            Assertions.assertFalse(itrMonitor.getIterator().hasNext());
            if(checkedType.checked){
                Assertions.assertThrows(NoSuchElementException.class,()->itrMonitor.verifyNext(outputType));
            }
        }else{
            Assertions.assertThrows(preMod.expectedException,()->itrMonitor.verifyNext(outputType));
        }
        itrMonitor.verifyIteratorState();
        setMonitor.verifyCollectionState();
    }
    private static void testItrremove_voidHelper(int itrCount,IteratorRemoveScenario itrRemoveScenario,
            IllegalModification preMod,CheckedType checkedType,long...expectedWords){
        final var setMonitor=new ByteSetImplMonitor(checkedType,expectedWords);
        final var itrMonitor=setMonitor.getMonitoredIterator();
        for(int i=0;i < itrCount;++i){
            itrMonitor.iterateForward();
        }
        if(itrRemoveScenario == IteratorRemoveScenario.PostRemove){
            itrMonitor.remove();
        }
        itrMonitor.illegalMod(preMod);
        final Class<? extends Throwable> expectedException=itrRemoveScenario.expectedException == null
                ?preMod.expectedException
                :itrRemoveScenario.expectedException;
        if(expectedException == null){
            for(;;){
                itrMonitor.verifyRemove();
                if(!itrMonitor.hasNext()){
                    break;
                }
                itrMonitor.iterateForward();
            }
        }else{
            Assertions.assertThrows(expectedException,()->itrMonitor.verifyRemove());
            itrMonitor.verifyIteratorState();
            setMonitor.verifyCollectionState();
        }
    }
    private static void testremoveIf_PredicateHelper(MonitoredRemoveIfPredicateGen filterGen,
            FunctionCallType functionCallType,long randSeed,double threshold,CheckedType checkedType,
            long...expectedWords){
        final var monitor=new ByteSetImplMonitor(checkedType,expectedWords);
        final var filter=filterGen.getMonitoredRemoveIfPredicate(monitor,threshold,randSeed);
        final int sizeBefore=monitor.size();
        if(filterGen.expectedException == null || sizeBefore == 0){
            final boolean result=monitor.verifyRemoveIf(filter,functionCallType);
            if(sizeBefore == 0b00){
                Assertions.assertFalse(result);
            }else{
                switch(filterGen){
                case Random:
                    Assertions.assertEquals(filter.numRemoved != 0,result);
                    break;
                case RemoveAll:
                    Assertions.assertTrue(monitor.set.isEmpty());
                    Assertions.assertTrue(result);
                    break;
                case RemoveFalse:
                    Assertions.assertFalse(monitor.set.contains(false));
                    Assertions.assertEquals((expectedWords[2] & 0b01) != 0,result);
                    break;
                case RemoveNone:
                    Assertions.assertFalse(result);
                    Assertions.assertFalse(monitor.set.isEmpty());
                    break;
                case RemoveTrue:
                    Assertions.assertFalse(monitor.set.contains(true));
                    Assertions.assertEquals((expectedWords[2] & 0b10) != 0,result);
                    break;
                default:
                    throw new UnsupportedOperationException("Unknown filterGen " + filterGen);
                }
            }
        }else{
            Assertions.assertThrows(filterGen.expectedException,()->monitor.verifyRemoveIf(filter,functionCallType));
            monitor.verifyCollectionState();
        }
    }
    @org.junit.jupiter.api.Test
    public void testadd_val(){
        for(final var inputType:DataType.BYTE.mayBeAddedTo()){
            final int min=inputType.getMinInt();
            final int max=inputType.getMaxInt();
            for(final var checkedType:CheckedType.values()){
                for(final long word0:WORDSTATES){
                    for(final long word1:WORDSTATES){
                        for(final long word2:WORDSTATES){
                            for(final long word3:WORDSTATES){
                                for(final var functionCallType:FunctionCallType.values()){
                                    TestExecutorService.submitTest(()->{
                                        final var monitor=new ByteSetImplMonitor(checkedType,word0,word1,word2,word3);
                                        for(int i=min;i <= max;++i){
                                            Assertions.assertEquals(
                                                    !monitor.set.contains((byte)DataType.BYTE.convertVal(i)),
                                                    monitor.verifyAdd(inputType.convertVal(i),inputType,
                                                            functionCallType));
                                        }
                                    });
                                }
                            }
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("ByteSetImplTest.testadd_val");
    }
    @org.junit.jupiter.api.Test
    public void testclear_void(){
        final BasicTest test=(checkedType,expectedWords)->new ByteSetImplMonitor(checkedType,expectedWords)
                .verifyClear();
        test.runAllTests("ByteSetImplTest.testclear_void");
    }
    @org.junit.jupiter.api.Test
    public void testclone_void(){
        final BasicTest test=(checkedType,expectedWords)->new ByteSetImplMonitor(checkedType,expectedWords)
                .verifyClone();
        test.runAllTests("ByteSetImplTest.testclone_void");
    }
    @org.junit.jupiter.api.Test
    public void testConstructor_longlonglonglong(){
        final BasicTest test=(checkedType,expectedWords)->new ByteSetImplMonitor(checkedType,expectedWords)
                .verifyCollectionState();
        test.runAllTests("ByteSetImplTest.testConstructor_longlonglonglong");
    }
    @org.junit.jupiter.api.Test
    public void testConstructor_longlonglonglongint(){
        for(final var word0:WORDSTATES){
            for(final var word1:WORDSTATES){
                for(final var word2:WORDSTATES){
                    for(final var word3:WORDSTATES){
                        TestExecutorService.submitTest(()->new ByteSetImplMonitor(CheckedType.CHECKED,
                                getSize(word0,word1,word2,word3),word0,word1,word2,word3).verifyCollectionState());
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("ByteSetImplTest.testConstructor_longlonglonglongint");
    }
    @org.junit.jupiter.api.Test
    public void testConstructor_void(){
        for(final var checkedType:CheckedType.values()){
            TestExecutorService.submitTest(()->new ByteSetImplMonitor(checkedType).verifyCollectionState());
        }
        TestExecutorService.completeAllTests("ByteSetImplTest.testConstructor_void");
    }
    @org.junit.jupiter.api.Test
    public void testcontains_val(){
        final QueryTest test=(monitor,queryVal,inputType,castType,modification)->monitor.verifyContains(queryVal,
                inputType,castType,modification);
        test.runAllTests("ByteSetImplTest.testcontains_val");
    }
    @org.junit.jupiter.api.Test
    public void testforEach_Consumer(){
        for(final var functionGen:StructType.ByteSetImpl.validMonitoredFunctionGens){
            for(final var word0:WORDSTATES){
                for(final var word1:WORDSTATES){
                    for(final var word2:WORDSTATES){
                        for(final var word3:WORDSTATES){
                            final int size=getSize(word0,word1,word2,word3);
                            for(final var functionCallType:FunctionCallType.values()){
                                final long randSeedBound=functionGen.randomized && size > 1 && !functionCallType.boxed
                                        ?100
                                        :0;
                                for(final var checkedType:CheckedType.values()){
                                    if(checkedType.checked || functionGen.expectedException == null || size == 0){
                                        LongStream.rangeClosed(0,randSeedBound)
                                                .forEach(randSeed->TestExecutorService.submitTest(
                                                        ()->testforEach_ConsumerHelper(functionGen,functionCallType,
                                                                randSeed,checkedType,word0,word1,word2,word3)));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("ByteSetImplTest.forEach_Consumer");
    }
    @org.junit.jupiter.api.Test
    public void testhashCode_void(){
        final BasicTest test=(checkedType,expectedWords)->new ByteSetImplMonitor(checkedType,expectedWords)
                .verifyHashCode();
        test.runAllTests("ByteSetImplTest.testhashCode_void");
    }
    @org.junit.jupiter.api.Test
    public void testisEmpty_void(){
        final BasicTest test=(checkedType,expectedWords)->new ByteSetImplMonitor(checkedType,expectedWords)
                .verifyIsEmpty();
        test.runAllTests("ByteSetImplTest.testisEmpty_void");
    }
    @org.junit.jupiter.api.Test
    public void testiterator_void(){
        final BasicTest test=(checkedType,expectedWords)->new ByteSetImplMonitor(checkedType,expectedWords)
                .getMonitoredIterator().verifyIteratorState();
        test.runAllTests("ByteSetImplTest.testiterator_void");
    }
    @org.junit.jupiter.api.Test
    public void testItrclone_void(){
        final BasicTest test=(checkedType,expectedWords)->new ByteSetImplMonitor(checkedType,expectedWords)
                .getMonitoredIterator().verifyClone();
        test.runAllTests("ByteSetImplTest.testItrclone_void");
    }
    @org.junit.jupiter.api.Test
    public void testItrforEachRemaining_Consumer(){
        for(final var checkedType:CheckedType.values()){
            for(final long word0:WORDSTATES){
                for(final long word1:WORDSTATES){
                    for(final long word2:WORDSTATES){
                        for(final long word3:WORDSTATES){
                            final int size=getSize(word0,word1,word2,word3);
                            for(final var functionGen:IteratorType.AscendingItr.validMonitoredFunctionGens){
                                if(checkedType.checked || functionGen.expectedException == null || size == 0){
                                    for(final var preMod:IteratorType.AscendingItr.validPreMods){
                                        if(checkedType.checked || preMod.expectedException == null || size == 0){
                                            int itrScenarioMax=0;
                                            if(size > 1){
                                                itrScenarioMax=2;
                                            }
                                            IntStream.rangeClosed(0,itrScenarioMax).forEach(itrScenario->{
                                                LongStream.rangeClosed(0,
                                                        preMod.expectedException == null && functionGen.randomized
                                                                && size > 1 && itrScenario == 0?100:0)
                                                        .forEach(randSeed->{
                                                            for(final var functionCallType:FunctionCallType.values()){
                                                                TestExecutorService.submitTest(
                                                                        ()->testItrforEachRemaining_ConsumerHelper(
                                                                                itrScenario,preMod,functionGen,
                                                                                functionCallType,randSeed,checkedType,
                                                                                word0,word1,word2,word3));
                                                            }
                                                        });
                                            });
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("ByteSetImplTest.testItrforEachRemaining_Consumer");
    }
    @org.junit.jupiter.api.Test
    public void testItrhasNext_void(){
        final BasicTest test=(checkedType,expectedWords)->{
            final var setMonitor=new ByteSetImplMonitor(checkedType,expectedWords);
            final var itrMonitor=setMonitor.getMonitoredIterator();
            final var setSize=getSize(expectedWords);
            for(int i=0;i < setSize;++i){
                Assertions.assertTrue(itrMonitor.verifyHasNext());
                itrMonitor.iterateForward();
            }
            Assertions.assertFalse(itrMonitor.verifyHasNext());
        };
        test.runAllTests("ByteSetImplTest.testItrhasNext_void");
    }
    @org.junit.jupiter.api.Test
    public void testItrnext_void(){
        for(final var outputType:DataType.BYTE.validOutputTypes()){
            for(final var checkedType:CheckedType.values()){
                for(final var preMod:IteratorType.AscendingItr.validPreMods){
                    if(checkedType.checked || preMod.expectedException == null){
                        for(final long word0:WORDSTATES){
                            for(final long word1:WORDSTATES){
                                for(final long word2:WORDSTATES){
                                    for(final long word3:WORDSTATES){
                                        TestExecutorService.submitTest(()->testItrnext_voidHelper(preMod,outputType,
                                                checkedType,word0,word1,word2,word3));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("ByteSetImplTest.testItrnext_void");
    }
    @org.junit.jupiter.api.Test
    public void testItrremove_void(){
        for(final var checkedType:CheckedType.values()){
            for(final long word0:WORDSTATES){
                for(final long word1:WORDSTATES){
                    for(final long word2:WORDSTATES){
                        for(final long word3:WORDSTATES){
                            final int setSize=getSize(word0,word1,word2,word3);
                            for(final var itrRemoveScenario:IteratorType.AscendingItr.validItrRemoveScenarios){
                                if((setSize != 0 || itrRemoveScenario == IteratorRemoveScenario.PostInit)
                                        && (checkedType.checked || itrRemoveScenario.expectedException == null)){
                                    for(final var preMod:IteratorType.AscendingItr.validPreMods){
                                        if(checkedType.checked || preMod.expectedException == null){
                                            int itrOffset,itrBound;
                                            if(itrRemoveScenario == IteratorRemoveScenario.PostInit){
                                                itrOffset=itrBound=0;
                                            }else{
                                                itrOffset=1;
                                                itrBound=setSize;
                                            }
                                            IntStream.rangeClosed(itrOffset,itrBound)
                                                    .forEach(itrCount->TestExecutorService.submitTest(
                                                            ()->testItrremove_voidHelper(itrCount,itrRemoveScenario,
                                                                    preMod,checkedType,word0,word1,word2,word3)));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("ByteSetImplTest.testItrremove_void");
    }
    @org.junit.jupiter.api.Test
    public void testReadAndWrite(){
        final MonitoredFunctionGenTest test=(functionGen,checkedType,
                expectedWords)->new ByteSetImplMonitor(checkedType,expectedWords).verifyReadAndWrite(functionGen);
        test.runAllTests("ByteSetImplTest.testReadAndWrite");
    }
    @org.junit.jupiter.api.Test
    public void testremoveIf_Predicate(){
        for(final var filterGen:StructType.ByteSetImpl.validMonitoredRemoveIfPredicateGens){
            for(final var word0:WORDSTATES){
                for(final var word1:WORDSTATES){
                    for(final var word2:WORDSTATES){
                        for(final var word3:WORDSTATES){
                            final int setSize=getSize(word0,word1,word2,word3);
                            for(final var functionCallType:FunctionCallType.values()){
                                final long randSeedBound;
                                final double[] thresholdArr;
                                if(filterGen.randomized && setSize > 1 && !functionCallType.boxed){
                                    randSeedBound=100;
                                    thresholdArr=RANDOM_THRESHOLDS;
                                }else{
                                    randSeedBound=0;
                                    thresholdArr=NON_RANDOM_THRESHOLD;
                                }
                                for(final var checkedType:CheckedType.values()){
                                    if(checkedType.checked || filterGen.expectedException == null || setSize == 0){
                                        LongStream.rangeClosed(0,randSeedBound)
                                                .forEach(randSeed->DoubleStream.of(thresholdArr)
                                                        .forEach(threshold->TestExecutorService
                                                                .submitTest(()->testremoveIf_PredicateHelper(filterGen,
                                                                        functionCallType,randSeed,threshold,checkedType,
                                                                        word0,word1,word2,word3))));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("ByteSetImplTest.testremoveIf_Predicate");
    }
    @org.junit.jupiter.api.Test
    public void testremoveVal_val(){
        final QueryTest test=(monitor,queryVal,inputType,castType,modification)->monitor.verifyRemoveVal(queryVal,
                inputType,castType,modification);
        test.runAllTests("ByteSetImplTest.testremoveVal_val");
    }
    @org.junit.jupiter.api.Test
    public void testsize_void(){
        final BasicTest test=(checkedType,expectedWords)->new ByteSetImplMonitor(checkedType,expectedWords)
                .verifySize();
        test.runAllTests("ByteSetImplTest.testsize_void");
    }
    @org.junit.jupiter.api.Test
    public void testtoArray_IntFunction(){
        final MonitoredFunctionGenTest test=(functionGen,checkedType,expectedWords)->{
            final var monitor=new ByteSetImplMonitor(checkedType,expectedWords);
            if(functionGen.expectedException == null){
                monitor.verifyToArray(functionGen);
            }else{
                Assertions.assertThrows(functionGen.expectedException,()->monitor.verifyToArray(functionGen));
                monitor.verifyCollectionState();
            }
        };
        test.runAllTests("ByteSetImplTest.testToArray_IntFunction");
    }
    @org.junit.jupiter.api.Test
    public void testtoArray_ObjectArray(){
        for(final var checkedType:CheckedType.values()){
            for(final long word0:WORDSTATES){
                for(final long word1:WORDSTATES){
                    for(final long word2:WORDSTATES){
                        for(final long word3:WORDSTATES){
                            final int size=getSize();
                            for(int arrSize=0,increment=Math.max(1,size / 10),bound=size + increment
                                    + 2;arrSize <= bound;arrSize+=increment){
                                final Object[] paramArr=new Object[arrSize];
                                TestExecutorService
                                        .submitTest(()->new ByteSetImplMonitor(checkedType,word0,word1,word2,word3)
                                                .verifyToArray(paramArr));
                            }
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("ByteSetImplTest.testtoArray_ObjectArray");
    }
    @org.junit.jupiter.api.Test
    public void testtoArray_void(){
        for(final var outputType:DataType.BYTE.validOutputTypes()){
            for(final var checkedType:CheckedType.values()){
                for(final var word0:WORDSTATES){
                    for(final var word1:WORDSTATES){
                        for(final var word2:WORDSTATES){
                            for(final var word3:WORDSTATES){
                                TestExecutorService.submitTest(()->outputType
                                        .verifyToArray(new ByteSetImplMonitor(checkedType,word0,word1,word2,word3)));
                            }
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("ByteSetImplTest.testtoArray_void");
    }
    @org.junit.jupiter.api.Test
    public void testtoString_void(){
        final BasicTest test=(checkedType,expectedWords)->new ByteSetImplMonitor(checkedType,expectedWords)
                .verifyToString();
        test.runAllTests("ByteSetImplTest.testtoString_void");
    }
    @org.junit.jupiter.api.AfterEach
    public void verifyAllExecuted(){
        int numTestsRemaining;
        if((numTestsRemaining=TestExecutorService.getNumRemainingTasks()) != 0){
            System.err.println("Warning: there were " + numTestsRemaining + " tests that were not completed");
        }
        TestExecutorService.reset();
    }
    private interface BasicTest{
        default void runAllTests(String testName){
            for(final var checkedType:CheckedType.values()){
                for(final long word0:WORDSTATES){
                    for(final long word1:WORDSTATES){
                        for(final long word2:WORDSTATES){
                            for(final long word3:WORDSTATES){
                                TestExecutorService.submitTest(()->runTest(checkedType,word0,word1,word2,word3));
                            }
                        }
                    }
                }
            }
            TestExecutorService.completeAllTests(testName);
        }
        void runTest(CheckedType checkedType,long...expectedWords);
    }
    private static interface MonitoredFunctionGenTest{
        default void runAllTests(String testName){
            for(final var functionGen:StructType.ByteSetImpl.validMonitoredFunctionGens){
                for(final var checkedType:CheckedType.values()){
                    if(checkedType.checked || functionGen.expectedException == null){
                        for(final long word0:WORDSTATES){
                            for(final long word1:WORDSTATES){
                                for(final long word2:WORDSTATES){
                                    for(final long word3:WORDSTATES){
                                        TestExecutorService.submitTest(
                                                ()->runTest(functionGen,checkedType,word0,word1,word2,word3));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            TestExecutorService.completeAllTests(testName);
        }
        void runTest(MonitoredFunctionGen functionGen,CheckedType checkedType,long...expectedWords);
    }
    private static interface QueryTest{
        boolean callMethod(ByteSetImplMonitor monitor,QueryVal queryVal,DataType inputType,QueryCastType castType,
                QueryVal.QueryValModification modification);
        default void runAllTests(String testName){
            for(final var queryVal:QueryVal.values()){
                queryVal.validQueryCombos.forEach((modification,castTypesToInputTypes)->{
                    castTypesToInputTypes.forEach((castType,inputTypes)->{
                        inputTypes.forEach(inputType->{
                            final boolean queryCanReturnTrue=queryVal.queryCanReturnTrue(modification,castType,
                                    inputType,DataType.BYTE);
                            for(final var checkedType:CheckedType.values()){
                                for(final var word0:WORDSTATES){
                                    for(final var word1:WORDSTATES){
                                        for(final var word2:WORDSTATES){
                                            for(final var word3:WORDSTATES){
                                                TestExecutorService.submitTest(
                                                        ()->runTest(queryCanReturnTrue,queryVal,modification,inputType,
                                                                castType,checkedType,word0,word1,word2,word3));
                                            }
                                        }
                                    }
                                }
                            }
                        });
                    });
                });
            }
            TestExecutorService.completeAllTests(testName);
        }
        default void runTest(boolean queryCanReturnTrue,QueryVal queryVal,QueryVal.QueryValModification modification,
                DataType inputType,QueryCastType castType,CheckedType checkedType,long...expectedWords){
            boolean expectedResult;
            if(queryCanReturnTrue){
                final byte byteInputVal=queryVal.getByteVal(modification);
                expectedResult=(expectedWords[(byteInputVal >> 6) + 2] & 1L << byteInputVal) != 0;
            }else{
                expectedResult=false;
            }
            final boolean actualResult=callMethod(new ByteSetImplMonitor(checkedType,expectedWords),queryVal,inputType,
                    castType,modification);
            Assertions.assertEquals(expectedResult,actualResult);
        }
    }
}
