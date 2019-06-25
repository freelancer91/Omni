package omni.impl.set;

import java.util.NoSuchElementException;
import java.util.function.LongPredicate;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import org.junit.jupiter.api.Assertions;
import omni.impl.CheckedType;
import omni.impl.DataType;
import omni.impl.FunctionCallType;
import omni.impl.IteratorRemoveScenario;
import omni.impl.IteratorType;
import omni.impl.StructType;
import omni.util.TestExecutorService;

public class BooleanSetImplTestNew{
    private static final int[] STATES=new int[]{0b00, // empty
            0b01, // false
            0b10, // true
            0b11, // false, true
    };
    private static final boolean[] POSSIBLE_VALS=new boolean[]{false,true};


    @org.junit.jupiter.api.AfterEach
    public void verifyAllExecuted(){
        int numTestsRemaining;
        if((numTestsRemaining=TestExecutorService.getNumRemainingTasks()) != 0){
            System.err.println("Warning: there were " + numTestsRemaining + " tests that were not completed");
        }
        TestExecutorService.reset();
    }
    @org.junit.jupiter.api.Test
    public void testConstructor_void(){
        for(var checkedType:CheckedType.values()){
            TestExecutorService.submitTest(()->new BooleanSetImplMonitor(checkedType).verifyCollectionState());
        }
        TestExecutorService.completeAllTests();
    }
    @org.junit.jupiter.api.Test
    public void testConstructor_int(){
        for(var checkedType:CheckedType.values()){
            for(var tmpState=0b00;tmpState <= 0b11;++tmpState){
                final var state=tmpState;
                TestExecutorService
                .submitTest(()->new BooleanSetImplMonitor(checkedType,state).verifyCollectionState());
            }
        }
        TestExecutorService.completeAllTests();
    }
    @org.junit.jupiter.api.Test
    public void testReadAndWrite(){
        for(var functionGen:StructType.BooleanSetImpl.validMonitoredFunctionGens){
            for(var checkedType:CheckedType.values()){
                if(checkedType.checked || functionGen.expectedException == null){
                    for(var state:STATES){
                        TestExecutorService.submitTest(()->{
                            var monitor=new BooleanSetImplMonitor(checkedType,state);
                            if(functionGen.expectedException == null){
                                monitor.verifyReadAndWrite(functionGen);
                            }else{
                                Assertions.assertThrows(functionGen.expectedException,
                                        ()->monitor.verifyReadAndWrite(functionGen));
                                monitor.verifyCollectionState();
                            }
                        });
                    }
                }
            }
        }
        TestExecutorService.completeAllTests();
    }
    @org.junit.jupiter.api.Test
    public void testclear_void() {
        for(var checkedType:CheckedType.values()) {
            for(var state:STATES) {
                TestExecutorService.submitTest(()->new BooleanSetImplMonitor(checkedType,state).verifyClear());
            }
        }
        TestExecutorService.completeAllTests();
    }
    @org.junit.jupiter.api.Test
    public void testclone_void(){
        for(var checkedType:CheckedType.values()){
            for(var state:STATES){
                TestExecutorService.submitTest(()->new BooleanSetImplMonitor(checkedType,state).verifyClone());
            }
        }
        TestExecutorService.completeAllTests();
    }
    @org.junit.jupiter.api.Test
    public void testtoString_void(){
        for(var checkedType:CheckedType.values()){
            for(var state:STATES){
                TestExecutorService.submitTest(()->new BooleanSetImplMonitor(checkedType,state).verifyToString());
            }
        }
        TestExecutorService.completeAllTests();
    }
    @org.junit.jupiter.api.Test
    public void testhashCode_void(){
        for(var checkedType:CheckedType.values()){
            for(var state:STATES){
                TestExecutorService.submitTest(()->new BooleanSetImplMonitor(checkedType,state).verifyHashCode());
            }
        }
        TestExecutorService.completeAllTests();
    }
    @org.junit.jupiter.api.Test
    public void testisEmpty_void(){
        for(var checkedType:CheckedType.values()){
            for(var state:STATES){
                TestExecutorService.submitTest(()->new BooleanSetImplMonitor(checkedType,state).verifyIsEmpty());
            }
        }
        TestExecutorService.completeAllTests();
    }
    @org.junit.jupiter.api.Test
    public void testsize_void(){
        for(var checkedType:CheckedType.values()){
            for(var state:STATES){
                TestExecutorService.submitTest(()->new BooleanSetImplMonitor(checkedType,state).verifySize());
            }
        }
        TestExecutorService.completeAllTests();
    }
    @org.junit.jupiter.api.Test
    public void testtoArray_IntFunction(){
        for(var functionGen:StructType.BooleanSetImpl.validMonitoredFunctionGens){
            for(var checkedType:CheckedType.values()){
                if(checkedType.checked || functionGen.expectedException == null){
                    for(var state:STATES){
                        TestExecutorService.submitTest(()->{
                            var monitor=new BooleanSetImplMonitor(checkedType,state);
                            if(functionGen.expectedException == null){
                                monitor.verifyToArray(functionGen);
                            }else{
                                Assertions.assertThrows(functionGen.expectedException,
                                        ()->monitor.verifyToArray(functionGen));
                                monitor.verifyCollectionState();
                            }
                        });
                    }
                }
            }
        }
        TestExecutorService.completeAllTests();
    }
    @org.junit.jupiter.api.Test
    public void testtoArray_ObjectArray(){
        for(var checkedType:CheckedType.values()){
            for(var state:STATES){
                for(int arrSize=0,bound=Integer.bitCount(state) + 2;arrSize <= bound;++arrSize){
                    Object[] paramArr=new Object[arrSize];
                    TestExecutorService
                    .submitTest(()->new BooleanSetImplMonitor(checkedType,state).verifyToArray(paramArr));
                }
            }
        }
        TestExecutorService.completeAllTests();
    }
    @org.junit.jupiter.api.Test
    public void testadd_val() {
        for(var checkedType:CheckedType.values()){
            for(var state:STATES){
                for(var inputVal:POSSIBLE_VALS){
                    for(var functionCallType:FunctionCallType.values()){
                        for(var inputType:DataType.BOOLEAN.mayBeAddedTo){
                            TestExecutorService.submitTest(
                                    ()->Assertions.assertEquals(new BooleanSetImplMonitor(checkedType,state).verifyAdd(
                                            inputVal,inputType,functionCallType),(state & (inputVal?2:1)) == 0));
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests();
    }
    @org.junit.jupiter.api.Test
    public void testforEach_Consumer(){
        for(var functionGen:StructType.BooleanSetImpl.validMonitoredFunctionGens){
            for(var state:STATES){
                for(var functionCallType:FunctionCallType.values()){
                    LongPredicate hasNextRand=functionGen.randomized && state == 0b11 && !functionCallType.boxed
                            ?v->v < 100
                                    :v->v == 0;
                                    for(var checkedType:CheckedType.values()){
                                        if(checkedType.checked || functionGen.expectedException == null || state == 0b00){
                                            LongStream.iterate(0,hasNextRand,v->v + 1).forEach(randSeed->{
                                                TestExecutorService.submitTest(()->{
                                                    var monitor=new BooleanSetImplMonitor(checkedType,state);
                                                    if(functionGen.expectedException == null || state == 0b00){
                                                        monitor.verifyForEach(functionGen,functionCallType,randSeed);
                                                    }else{
                                                        Assertions.assertThrows(functionGen.expectedException,
                                                                ()->monitor.verifyForEach(functionGen,functionCallType,randSeed));
                                        monitor.verifyCollectionState();
                                                    }
                                                });
                                            });
                                        }
                                    }
                }
            }
        }
        TestExecutorService.completeAllTests();
    }
    @org.junit.jupiter.api.Test
    public void testremoveIf_Predicate(){
        for(var filterGen:StructType.BooleanSetImpl.validMonitoredRemoveIfPredicateGens){
            for(var state:STATES){
                for(var functionCallType:FunctionCallType.values()){
                    LongPredicate hasNextRand=filterGen.randomized && state == 0b11 && !functionCallType.boxed
                            ?v->v < 100
                                    :v->v == 0;
                                    for(var checkedType:CheckedType.values()){
                                        if(checkedType.checked || filterGen.expectedException == null || state == 0b00){
                                            LongStream.iterate(0,hasNextRand,v->v + 1).forEach(randSeed->{
                                                TestExecutorService.submitTest(()->{
                                                    var monitor=new BooleanSetImplMonitor(checkedType,state);
                                                    if(filterGen.expectedException == null || state == 0b00){
                                                        boolean result=monitor.verifyRemoveIf(filterGen,functionCallType,0.5,randSeed);
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
                                                                throw new UnsupportedOperationException(
                                                                        "Unknown filterGen " + filterGen);
                                                            }
                                                        }
                                                    }else{
                                                        Assertions.assertThrows(filterGen.expectedException,
                                                                ()->monitor.verifyRemoveIf(filterGen,functionCallType,0.5,randSeed));
                                        monitor.verifyCollectionState();
                                                    }
                                                });
                                            });
                                        }
                                    }
                }
            }
        }
        TestExecutorService.completeAllTests();
    }
    @org.junit.jupiter.api.Test
    public void testtoArray_void(){
        for(var outputType:DataType.BOOLEAN.validOutputTypes){
            for(var checkedType:CheckedType.values()){
                for(var state:STATES){
                    TestExecutorService
                    .submitTest(()->outputType.verifyToArray(new BooleanSetImplMonitor(checkedType,state)));
                }
            }
            TestExecutorService.completeAllTests();
        }
    }
    @org.junit.jupiter.api.Test
    public void testiterator_void(){
        for(var checkedType:CheckedType.values()){
            for(var state:STATES){
                TestExecutorService.submitTest(
                        ()->new BooleanSetImplMonitor(checkedType,state).getMonitoredIterator().verifyIteratorState());
            }
        }
        TestExecutorService.completeAllTests();
    }
    @org.junit.jupiter.api.Test
    public void testItrremove_void(){
        for(var checkedType:CheckedType.values()){
            for(var state:STATES){
                int setSize=Integer.bitCount(state);
                for(var itrRemoveScenario:IteratorType.AscendingItr.validItrRemoveScenarios){
                    if((state != 0b00 || itrRemoveScenario == IteratorRemoveScenario.PostInit)
                            && (checkedType.checked || itrRemoveScenario.expectedException == null)){
                        for(var preMod:IteratorType.AscendingItr.validPreMods){
                            if(checkedType.checked || preMod.expectedException == null){
                                int itrOffset,itrBound;
                                if(itrRemoveScenario == IteratorRemoveScenario.PostInit){
                                    itrOffset=itrBound=0;
                                }else{
                                    itrOffset=1;
                                    itrBound=setSize;
                                }
                                IntStream.rangeClosed(itrOffset,itrBound).forEach(itrCount->{
                                    TestExecutorService.submitTest(()->{
                                        var setMonitor=new BooleanSetImplMonitor(checkedType,state);
                                        var itrMonitor=setMonitor.getMonitoredIterator();
                                        for(int i=0;i < itrCount;++i){
                                            itrMonitor.iterateForward();
                                        }
                                        if(itrRemoveScenario == IteratorRemoveScenario.PostRemove){
                                            itrMonitor.remove();
                                        }
                                        itrMonitor.illegalMod(preMod);
                                        Class<? extends Throwable> expectedException=itrRemoveScenario.expectedException == null
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
                                    });
                                });
                            }
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests();
    }
    @org.junit.jupiter.api.Test
    public void testItrforEachRemaining_Consumer(){
        for(var checkedType:CheckedType.values()){
            for(var state:STATES){
                for(var functionGen:IteratorType.AscendingItr.validMonitoredFunctionGens){
                    if(checkedType.checked || functionGen.expectedException == null || state == 0b00){
                        for(var preMod:IteratorType.AscendingItr.validPreMods){
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
                                        for(var functionCallType:FunctionCallType.values()){
                                            TestExecutorService.submitTest(()->{
                                                var setMonitor=new BooleanSetImplMonitor(checkedType,state);
                                                var itrMonitor=setMonitor.getMonitoredIterator();
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
                                                Class<? extends Throwable> expectedException=adjustedState == 0b00
                                                        ?null
                                                                :preMod.expectedException == null
                                                                ?functionGen.expectedException
                                                                        :preMod.expectedException;
                                                if(expectedException == null){
                                                    itrMonitor.verifyForEachRemaining(functionGen,
                                                            functionCallType,randSeed);
                                                }else{
                                                    Assertions.assertThrows(expectedException,
                                                            ()->itrMonitor.verifyForEachRemaining(functionGen,
                                                                    functionCallType,randSeed));
                                                    itrMonitor.verifyIteratorState();
                                                    setMonitor.verifyCollectionState();
                                                }
                                            });
                                        }
                                    });
                                });
                            }
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests();
    }
    @org.junit.jupiter.api.Test
    public void testItrnext_void(){
        for(var outputType:DataType.BOOLEAN.validOutputTypes){
            for(var checkedType:CheckedType.values()){
                for(var preMod:IteratorType.AscendingItr.validPreMods){
                    if(checkedType.checked || preMod.expectedException == null){
                        for(var state:STATES){
                            TestExecutorService.submitTest(()->{
                                var setMonitor=new BooleanSetImplMonitor(checkedType,state);
                                var itrMonitor=setMonitor.getMonitoredIterator();
                                itrMonitor.illegalMod(preMod);
                                if(preMod.expectedException == null){
                                    switch(state){
                                    case 0b01:
                                        Assertions.assertEquals(Boolean.FALSE,itrMonitor.verifyNext(outputType));
                                        break;
                                    default:
                                        Assertions.assertEquals(Boolean.FALSE,itrMonitor.verifyNext(outputType));
                                    case 0b10:
                                        Assertions.assertEquals(Boolean.TRUE,itrMonitor.verifyNext(outputType));
                                        break;
                                    case 0b00:
                                    }
                                    Assertions.assertFalse(itrMonitor.getIterator().hasNext());
                                    if(checkedType.checked){
                                        Assertions.assertThrows(NoSuchElementException.class,
                                                ()->itrMonitor.verifyNext(outputType));
                                    }
                                }else{
                                    Assertions.assertThrows(preMod.expectedException,
                                            ()->itrMonitor.verifyNext(outputType));
                                }
                                itrMonitor.verifyIteratorState();
                                setMonitor.verifyCollectionState();
                            });
                        }
                    }
                }
            }
        }
    }
    @org.junit.jupiter.api.Test
    public void testItrhasNext_void(){
        for(var checkedType:CheckedType.values()){
            for(var state:STATES){
                TestExecutorService.submitTest(()->{
                    var setMonitor=new BooleanSetImplMonitor(checkedType,state);
                    var itrMonitor=setMonitor.getMonitoredIterator();
                    var setSize=Integer.bitCount(state);
                    for(int i=0;i < setSize;++i){
                        Assertions.assertTrue(itrMonitor.verifyHasNext());
                        itrMonitor.iterateForward();
                    }
                    Assertions.assertFalse(itrMonitor.verifyHasNext());
                });
            }
        }
        TestExecutorService.completeAllTests();
    }
    @org.junit.jupiter.api.Test
    public void testcontains_val(){
        // TODO
    }
    @org.junit.jupiter.api.Test
    public void testremoveVal_val(){
        // TODO
    }
}
