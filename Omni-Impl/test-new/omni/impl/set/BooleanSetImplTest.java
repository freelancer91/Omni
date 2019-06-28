package omni.impl.set;
import java.util.NoSuchElementException;
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
public class BooleanSetImplTest{
    private static final int[] STATES=new int[]{0b00, // empty
            0b01, // false
            0b10, // true
            0b11, // false, true
    };
    private static final boolean[] POSSIBLE_VALS=new boolean[]{false,true};
    private static void testforEach_ConsumerHelper(MonitoredFunctionGen functionGen,FunctionCallType functionCallType,
            long randSeed,CheckedType checkedType,int state){
        final var monitor=new BooleanSetImplMonitor(checkedType,state);
        if(functionGen.expectedException == null || state == 0b00){
            monitor.verifyForEach(functionGen,functionCallType,randSeed);
        }else{
            Assertions.assertThrows(functionGen.expectedException,
                    ()->monitor.verifyForEach(functionGen,functionCallType,randSeed));
            monitor.verifyCollectionState();
        }
    }
    private static void testItrforEachRemaining_ConsumerHelper(int itrScenario,IllegalModification preMod,
            MonitoredFunctionGen functionGen,FunctionCallType functionCallType,long randSeed,CheckedType checkedType,
            int state){
        final var setMonitor=new BooleanSetImplMonitor(checkedType,state);
        final var itrMonitor=setMonitor.getMonitoredIterator();
        int adjustedState=state;
        switch(itrScenario){
        case 1:
            itrMonitor.iterateForward();
            adjustedState=0b10;
            break;
        case 2:
            itrMonitor.iterateForward();
            itrMonitor.remove();
            adjustedState=0b10;
        default:
        }
        itrMonitor.illegalMod(preMod);
        final Class<? extends Throwable> expectedException=adjustedState == 0b00?null
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
            int state){
        final var setMonitor=new BooleanSetImplMonitor(checkedType,state);
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
            IllegalModification preMod,CheckedType checkedType,int state){
        final var setMonitor=new BooleanSetImplMonitor(checkedType,state);
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
            FunctionCallType functionCallType,long randSeed,CheckedType checkedType,int state){
        final var monitor=new BooleanSetImplMonitor(checkedType,state);
        var filter=filterGen.getMonitoredRemoveIfPredicate(monitor,0.5,randSeed);
        if(filterGen.expectedException == null || state == 0b00){
            final boolean result=monitor.verifyRemoveIf(filter,functionCallType);
            if(state == 0b00){
                Assertions.assertFalse(result);
            }else{
                switch(filterGen){
                case RemoveAll:
                    Assertions.assertTrue(monitor.set.isEmpty());
                    Assertions.assertTrue(result);
                    break;
                case RemoveFalse:
                    Assertions.assertFalse(monitor.set.contains(false));
                    Assertions.assertEquals((state & 0b01) != 0,result);
                    break;
                case RemoveNone:
                    Assertions.assertFalse(result);
                    Assertions.assertFalse(monitor.set.isEmpty());
                    break;
                case RemoveTrue:
                    Assertions.assertFalse(monitor.set.contains(true));
                    Assertions.assertEquals((state & 0b10) != 0,result);
                    break;
                default:
                    throw new UnsupportedOperationException("Unknown filterGen " + filterGen);
                }
            }
        }else{
            Assertions.assertThrows(filterGen.expectedException,
                    ()->monitor.verifyRemoveIf(filter,functionCallType));
            monitor.verifyCollectionState();
        }
    }
    @org.junit.jupiter.api.Test
    public void testadd_val(){
        for(final var inputType:DataType.BOOLEAN.mayBeAddedTo()){
            for(final var checkedType:CheckedType.values()){
                for(final var state:STATES){
                    for(final var inputVal:POSSIBLE_VALS){
                        for(final var functionCallType:FunctionCallType.values()){
                            TestExecutorService.submitTest(
                                    ()->Assertions.assertEquals(new BooleanSetImplMonitor(checkedType,state).verifyAdd(
                                            inputVal,inputType,functionCallType),(state & (inputVal?2:1)) == 0));
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("BooleanSetImplTest.testadd_val");
    }
    @org.junit.jupiter.api.Test
    public void testclear_void(){
        final BasicTest test=(checkedType,state)->new BooleanSetImplMonitor(checkedType,state).verifyClear();
        test.runAllTests("BooleanSetImplTest.testclear_void");
    }
    @org.junit.jupiter.api.Test
    public void testclone_void(){
        final BasicTest test=(checkedType,state)->new BooleanSetImplMonitor(checkedType,state).verifyClone();
        test.runAllTests("BooleanSetImplTest.testclone_void");
    }
    @org.junit.jupiter.api.Test
    public void testConstructor_int(){
        BasicTest test=(checkedType,state)->new BooleanSetImplMonitor(checkedType,state).verifyCollectionState();
        test.runAllTests("BooleanSetImplTest.testConstructor_int");
    }
    @org.junit.jupiter.api.Test
    public void testConstructor_void(){
        for(final var checkedType:CheckedType.values()){
            TestExecutorService.submitTest(()->new BooleanSetImplMonitor(checkedType).verifyCollectionState());
        }
        TestExecutorService.completeAllTests("BooleanSetImplTest.testConstructor_void");
    }
    @org.junit.jupiter.api.Test
    public void testcontains_val(){
        final QueryTest test=(monitor,queryVal,inputType,castType,modification)->monitor.verifyContains(queryVal,
                inputType,castType,modification);
        test.runAllTests("BooleanSetImplTest.testcontains_val");
    }
    @org.junit.jupiter.api.Test
    public void testforEach_Consumer(){
        for(final var functionGen:StructType.BooleanSetImpl.validMonitoredFunctionGens){
            for(final var state:STATES){
                for(final var functionCallType:FunctionCallType.values()){
                    final long randSeedBound=functionGen.randomized && state == 0b11 && !functionCallType.boxed?100:0;
                    for(final var checkedType:CheckedType.values()){
                        if(checkedType.checked || functionGen.expectedException == null || state == 0b00){
                            LongStream.rangeClosed(0,randSeedBound).forEach(
                                    randSeed->TestExecutorService.submitTest(()->testforEach_ConsumerHelper(functionGen,
                                            functionCallType,randSeed,checkedType,state)));
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("BooleanSetImplTest.testforEach_Consumer");
    }
    @org.junit.jupiter.api.Test
    public void testhashCode_void(){
        final BasicTest test=(checkedType,state)->new BooleanSetImplMonitor(checkedType,state).verifyHashCode();
        test.runAllTests("BooleanSetImplTest.testhashCode_void");
    }
    @org.junit.jupiter.api.Test
    public void testisEmpty_void(){
        final BasicTest test=(checkedType,state)->new BooleanSetImplMonitor(checkedType,state).verifyIsEmpty();
        test.runAllTests("BooleanSetImplTest.testisEmpty_void");
    }
    @org.junit.jupiter.api.Test
    public void testiterator_void(){
        final BasicTest test=(checkedType,state)->new BooleanSetImplMonitor(checkedType,state).getMonitoredIterator()
                .verifyIteratorState();
        test.runAllTests("BooleanSetImplTest.testiterator_void");
    }
    @org.junit.jupiter.api.Test
    public void testItrclone_void(){
        final BasicTest test=(checkedType,state)->new BooleanSetImplMonitor(checkedType,state).getMonitoredIterator()
                .verifyClone();
        test.runAllTests("BooleanSetImplTest.testItrclone_void");
    }
    @org.junit.jupiter.api.Test
    public void testItrforEachRemaining_Consumer(){
        for(final var checkedType:CheckedType.values()){
            for(final var state:STATES){
                for(final var functionGen:IteratorType.AscendingItr.validMonitoredFunctionGens){
                    if(checkedType.checked || functionGen.expectedException == null || state == 0b00){
                        for(final var preMod:IteratorType.AscendingItr.validPreMods){
                            if(checkedType.checked || preMod.expectedException == null || state == 0b00){
                                int itrScenarioMax=0;
                                if(state == 0b11){
                                    itrScenarioMax=2;
                                }
                                IntStream.rangeClosed(0,itrScenarioMax).forEach(itrScenario->{
                                    LongStream
                                    .rangeClosed(0,
                                            preMod.expectedException == null && functionGen.randomized
                                            && state == 0b11 && itrScenario == 0?100:0)
                                    .forEach(randSeed->{
                                        for(final var functionCallType:FunctionCallType.values()){
                                            TestExecutorService.submitTest(
                                                    ()->testItrforEachRemaining_ConsumerHelper(itrScenario,
                                                            preMod,functionGen,functionCallType,randSeed,
                                                            checkedType,state));
                                        }
                                    });
                                });
                            }
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("BooleanSetImplTest.testItrforEachRemaining_Consmer");
    }
    @org.junit.jupiter.api.Test
    public void testItrhasNext_void(){
        BasicTest test=(checkedType,state)->{
            final var setMonitor=new BooleanSetImplMonitor(checkedType,state);
            final var itrMonitor=setMonitor.getMonitoredIterator();
            final var setSize=Integer.bitCount(state);
            for(int i=0;i < setSize;++i){
                Assertions.assertTrue(itrMonitor.verifyHasNext());
                itrMonitor.iterateForward();
            }
            Assertions.assertFalse(itrMonitor.verifyHasNext());
        };
        test.runAllTests("BooleanSetImplTest.testItrhasNext_void");
    }
    @org.junit.jupiter.api.Test
    public void testItrnext_void(){
        for(final var outputType:DataType.BOOLEAN.validOutputTypes()){
            for(final var checkedType:CheckedType.values()){
                for(final var preMod:IteratorType.AscendingItr.validPreMods){
                    if(checkedType.checked || preMod.expectedException == null){
                        for(final var state:STATES){
                            TestExecutorService
                            .submitTest(()->testItrnext_voidHelper(preMod,outputType,checkedType,state));
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("BooleanSetImplTest.testItrnext_void");
    }
    @org.junit.jupiter.api.Test
    public void testItrremove_void(){
        for(final var checkedType:CheckedType.values()){
            for(final var state:STATES){
                final int setSize=Integer.bitCount(state);
                for(final var itrRemoveScenario:IteratorType.AscendingItr.validItrRemoveScenarios){
                    if((state != 0b00 || itrRemoveScenario == IteratorRemoveScenario.PostInit)
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
                                IntStream.rangeClosed(itrOffset,itrBound).forEach(
                                        itrCount->TestExecutorService.submitTest(()->testItrremove_voidHelper(itrCount,
                                                itrRemoveScenario,preMod,checkedType,state)));
                            }
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("BooleanSetImplTest.testItrremove_void");
    }
    @org.junit.jupiter.api.Test
    public void testReadAndWrite(){
        final MonitoredFunctionGenTest test=(functionGen,checkedType,
                state)->new BooleanSetImplMonitor(checkedType,state).verifyReadAndWrite(functionGen);
        test.runAllTests("BooleanSetImplTest.testReadAndWrite");
    }
    @org.junit.jupiter.api.Test
    public void testremoveIf_Predicate(){
        for(final var filterGen:StructType.BooleanSetImpl.validMonitoredRemoveIfPredicateGens){
            for(final var state:STATES){
                for(final var functionCallType:FunctionCallType.values()){
                    final long randSeedBound=filterGen.randomized && state == 0b11 && !functionCallType.boxed?100:0;
                    for(final var checkedType:CheckedType.values()){
                        if(checkedType.checked || filterGen.expectedException == null || state == 0b00){
                            LongStream.rangeClosed(0,randSeedBound).forEach(
                                    randSeed->TestExecutorService.submitTest(()->testremoveIf_PredicateHelper(filterGen,
                                            functionCallType,randSeed,checkedType,state)));
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("BooleanSetImplTest.testremoveIf_Predicate");
    }
    @org.junit.jupiter.api.Test
    public void testremoveVal_val(){
        final QueryTest test=(monitor,queryVal,inputType,castType,modification)->monitor.verifyRemoveVal(queryVal,
                inputType,castType,modification);
        test.runAllTests("BooleanSetImplTest.testremoveVal_val");
    }
    @org.junit.jupiter.api.Test
    public void testsize_void(){
        final BasicTest test=(checkedType,state)->new BooleanSetImplMonitor(checkedType,state).verifySize();
        test.runAllTests("BooleanSetImplTest.testsize_void");
    }
    @org.junit.jupiter.api.Test
    public void testtoArray_IntFunction(){
        final MonitoredFunctionGenTest test=(functionGen,checkedType,state)->{
            final var monitor=new BooleanSetImplMonitor(checkedType,state);
            if(functionGen.expectedException == null){
                monitor.verifyToArray(functionGen);
            }else{
                Assertions.assertThrows(functionGen.expectedException,()->monitor.verifyToArray(functionGen));
                monitor.verifyCollectionState();
            }
        };
        test.runAllTests("BooleanSetImplTest.testtoArray_IntFunction");
    }
    @org.junit.jupiter.api.Test
    public void testtoArray_ObjectArray(){
        for(final var checkedType:CheckedType.values()){
            for(final var state:STATES){
                for(int arrSize=0,bound=Integer.bitCount(state) + 2;arrSize <= bound;++arrSize){
                    final Object[] paramArr=new Object[arrSize];
                    TestExecutorService
                    .submitTest(()->new BooleanSetImplMonitor(checkedType,state).verifyToArray(paramArr));
                }
            }
        }
        TestExecutorService.completeAllTests("BooleanSetImplTest.testtoArray_ObjectArray");
    }
    @org.junit.jupiter.api.Test
    public void testtoArray_void(){
        for(final var outputType:DataType.BOOLEAN.validOutputTypes()){
            for(final var checkedType:CheckedType.values()){
                for(final var state:STATES){
                    TestExecutorService
                    .submitTest(()->outputType.verifyToArray(new BooleanSetImplMonitor(checkedType,state)));
                }
            }

        }
        TestExecutorService.completeAllTests("BooleanSetImplTest.testtoArray_void");
    }
    @org.junit.jupiter.api.Test
    public void testtoString_void(){
        final BasicTest test=(checkedType,state)->new BooleanSetImplMonitor(checkedType,state).verifyToString();
        test.runAllTests("BooleanSetImplTest.testtoString_void");
    }
    @org.junit.jupiter.api.AfterEach
    public void verifyAllExecuted(){
        int numTestsRemaining;
        if((numTestsRemaining=TestExecutorService.getNumRemainingTasks()) != 0){
            System.err.println("Warning: there were " + numTestsRemaining + " tests that were not completed");
        }
        TestExecutorService.reset();
    }
    private static interface BasicTest{
        default void runAllTests(String testName){
            for(final var checkedType:CheckedType.values()){
                for(final var state:STATES){
                    TestExecutorService.submitTest(()->runTest(checkedType,state));
                }
            }
            TestExecutorService.completeAllTests(testName);
        }
        void runTest(CheckedType checkedType,int state);
    }
    private static interface MonitoredFunctionGenTest{
        default void runAllTests(String testName){
            for(final var functionGen:StructType.BooleanSetImpl.validMonitoredFunctionGens){
                for(final var checkedType:CheckedType.values()){
                    if(checkedType.checked || functionGen.expectedException == null){
                        for(final var state:STATES){
                            TestExecutorService.submitTest(()->runTest(functionGen,checkedType,state));
                        }
                    }
                }
            }
            TestExecutorService.completeAllTests(testName);
        }
        void runTest(MonitoredFunctionGen functionGen,CheckedType checkedType,int state);
    }
    private static interface QueryTest{
        boolean callMethod(BooleanSetImplMonitor monitor,QueryVal queryVal,DataType inputType,QueryCastType castType,
                QueryVal.QueryValModification modification);
        default void runAllTests(String testName){
            for(final var queryVal:QueryVal.values()){
                queryVal.validQueryCombos.forEach((modification,castTypesToInputTypes)->{
                    castTypesToInputTypes.forEach((castType,inputTypes)->{
                        inputTypes.forEach(inputType->{
                            final boolean queryCanReturnTrue=queryVal.queryCanReturnTrue(modification,castType,
                                    inputType,DataType.BOOLEAN);
                            for(final var checkedType:CheckedType.values()){
                                for(final var state:STATES){
                                    TestExecutorService.submitTest(()->runTest(queryCanReturnTrue,queryVal,modification,
                                            inputType,castType,checkedType,state));
                                }
                            }
                        });
                    });
                });
            }
            TestExecutorService.completeAllTests(testName);
        }
        default void runTest(boolean queryCanReturnTrue,QueryVal queryVal,QueryVal.QueryValModification modification,
                DataType inputType,QueryCastType castType,CheckedType checkedType,int state){
            boolean expectedResult;
            if(queryCanReturnTrue){
                final boolean booleanInputVal=queryVal.getBooleanVal(modification);
                switch(state){
                case 0b11:
                    expectedResult=true;
                    break;
                case 0b01:
                    expectedResult=!booleanInputVal;
                    break;
                case 0b10:
                    expectedResult=booleanInputVal;
                    break;
                default:
                    expectedResult=false;
                }
            }else{
                expectedResult=false;
            }
            final boolean actualResult=callMethod(new BooleanSetImplMonitor(checkedType,state),queryVal,inputType,
                    castType,modification);
            Assertions.assertEquals(expectedResult,actualResult);
        }
    }
}
