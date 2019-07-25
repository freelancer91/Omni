package omni.impl.set;
import java.io.IOException;
import java.util.EnumSet;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import omni.api.OmniIterator;
import omni.impl.CheckedType;
import omni.impl.DataType;
import omni.impl.FunctionCallType;
import omni.impl.IteratorRemoveScenario;
import omni.impl.IteratorType;
import omni.impl.MonitoredCollection;
import omni.impl.MonitoredFunction;
import omni.impl.MonitoredFunctionGen;
import omni.impl.MonitoredObjectOutputStream;
import omni.impl.MonitoredRemoveIfPredicate;
import omni.impl.MonitoredRemoveIfPredicateGen.PredicateGenCallType;
import omni.impl.MonitoredSet;
import omni.impl.QueryCastType;
import omni.impl.QueryVal;
import omni.impl.QueryVal.QueryValModification;
import omni.impl.StructType;
import omni.util.TestExecutorService;
@Tag(value="NewTest")
@TestMethodOrder(OrderAnnotation.class)
public class BooleanSetImplTest{
    private static class BooleanSetImplMonitor implements MonitoredSet<BooleanSetImpl>{
        final CheckedType checkedType;
        final BooleanSetImpl set;
        int expectedState;

        BooleanSetImplMonitor(CheckedType checkedType){
            this.checkedType=checkedType;
            this.expectedState=0;
            if(checkedType.checked){
                this.set=new BooleanSetImpl.Checked();
            }else{
                this.set=new BooleanSetImpl();
            }
        }
        BooleanSetImplMonitor(CheckedType checkedType,int expectedState){
            this.checkedType=checkedType;
            this.expectedState=expectedState;
            if(checkedType.checked) {
                this.set=new BooleanSetImpl.Checked(expectedState);
            }else {
                this.set=new BooleanSetImpl(expectedState);
            }
        }

        @Override
        public Object get(int iterationIndex,DataType outputType) {
            var itr=set.iterator();
            while(iterationIndex>0) {
                itr.nextBoolean();
            }
            return outputType.convertVal(itr.nextBoolean());
        }
        
        
        @Override
        public void updateAddState(Object inputVal,DataType inputType,boolean result){
            boolean v=(boolean)inputVal;
            expectedState|=v?2:1;
        }

        @Override public CheckedType getCheckedType(){
            return this.checkedType;
        }

        @Override public BooleanSetImpl getCollection(){
            return set;
        }

        @Override public DataType getDataType(){
            return DataType.BOOLEAN;
        }

        @Override public MonitoredIterator<OmniIterator.OfBoolean,BooleanSetImpl> getMonitoredIterator(){
            if(checkedType.checked) {
                return new CheckedMonitoredItr();
            }
            return new UncheckedMonitoredItr();
        }
        @Override public MonitoredIterator<? extends OmniIterator<?>,BooleanSetImpl> getMonitoredIterator(IteratorType itrType){
            if(itrType!=IteratorType.AscendingItr) {
                throw itrType.invalid();
            }
            return getMonitoredIterator();
        }
        @Override public MonitoredIterator<? extends OmniIterator<?>,BooleanSetImpl> getMonitoredIterator(int index,IteratorType itrType){
            var itrMonitor=getMonitoredIterator(itrType);
            while(--index>=0 && itrMonitor.hasNext()) {
                itrMonitor.iterateForward();
            }
            return itrMonitor;
        }
        @Override public StructType getStructType(){
            return StructType.BooleanSetImpl;
        }

        @Override public boolean isEmpty(){
            return expectedState==0;
        }

        @Override public void modCollection(){
            set.state=expectedState=expectedState+1&0b11;
        }

        @Override public int size(){
            return Integer.bitCount(expectedState);
        }

        @Override public void updateCollectionState(){
            expectedState=set.state;
        }

        @Override public void verifyCollectionState(){
            Assertions.assertEquals(expectedState,set.state);
        }

        @Override public void verifyClone(Object clone){
            var cast=(BooleanSetImpl)clone;
            Assertions.assertEquals(checkedType.checked,cast instanceof BooleanSetImpl.Checked);
            Assertions.assertEquals(set.state,cast.state);
        }

        @Override
        public void removeFromExpectedState(DataType inputType,QueryVal queryVal,QueryValModification modification){
            switch(expectedState) {
            case 0b01:
            case 0b10:
                expectedState=0b00;
                break;
            default:
                boolean v=(boolean)queryVal.getInputVal(DataType.BOOLEAN,modification);
                if(v){
                    expectedState=0b01;
                }else {
                    expectedState=0b10;
                }
            }
        }



        @Override public void verifyRemoveIf(boolean result,MonitoredRemoveIfPredicate filter){
            switch(expectedState) {
            case 0b00:
                Assertions.assertEquals(0,filter.numCalls);
                Assertions.assertEquals(0,filter.numRemoved);
                Assertions.assertEquals(0,filter.numRetained);
                Assertions.assertTrue(filter.removedVals.isEmpty());
                Assertions.assertTrue(filter.retainedVals.isEmpty());
                Assertions.assertFalse(result);
                Assertions.assertEquals(0b00,set.state);
                break;
            case 0b01:
                Assertions.assertEquals(1,filter.numCalls);
                if(result) {
                    Assertions.assertEquals(1,filter.numRemoved);
                    Assertions.assertEquals(0,filter.numRetained);
                    Assertions.assertTrue(filter.removedVals.contains(Boolean.FALSE));
                    Assertions.assertFalse(filter.removedVals.contains(Boolean.TRUE));
                    Assertions.assertFalse(filter.retainedVals.contains(Boolean.FALSE));
                    Assertions.assertFalse(filter.retainedVals.contains(Boolean.TRUE));
                    Assertions.assertNotEquals(expectedState,set.state);
                    Assertions.assertEquals(0b00,set.state);
                }else {
                    Assertions.assertEquals(0,filter.numRemoved);
                    Assertions.assertEquals(1,filter.numRetained);
                    Assertions.assertFalse(filter.removedVals.contains(Boolean.FALSE));
                    Assertions.assertFalse(filter.removedVals.contains(Boolean.TRUE));
                    Assertions.assertTrue(filter.retainedVals.contains(Boolean.FALSE));
                    Assertions.assertFalse(filter.retainedVals.contains(Boolean.TRUE));
                    Assertions.assertEquals(0b01,set.state);
                }
                break;
            case 0b10:
                Assertions.assertEquals(1,filter.numCalls);
                if(result) {
                    Assertions.assertEquals(1,filter.numRemoved);
                    Assertions.assertEquals(0,filter.numRetained);
                    Assertions.assertTrue(filter.removedVals.contains(Boolean.TRUE));
                    Assertions.assertFalse(filter.removedVals.contains(Boolean.FALSE));
                    Assertions.assertFalse(filter.retainedVals.contains(Boolean.TRUE));
                    Assertions.assertFalse(filter.retainedVals.contains(Boolean.FALSE));
                    Assertions.assertEquals(0b00,set.state);
                }else {
                    Assertions.assertEquals(0,filter.numRemoved);
                    Assertions.assertEquals(1,filter.numRetained);
                    Assertions.assertFalse(filter.removedVals.contains(Boolean.TRUE));
                    Assertions.assertFalse(filter.removedVals.contains(Boolean.FALSE));
                    Assertions.assertTrue(filter.retainedVals.contains(Boolean.TRUE));
                    Assertions.assertFalse(filter.retainedVals.contains(Boolean.FALSE));
                    Assertions.assertEquals(0b10,set.state);
                }
                break;
            default:
                Assertions.assertEquals(2,filter.numCalls);
                if(result) {
                    if(filter.removedVals.contains(Boolean.TRUE)) {
                        if(filter.removedVals.contains(Boolean.FALSE)) {
                            Assertions.assertEquals(0b00,set.state);
                            Assertions.assertTrue(filter.retainedVals.isEmpty());
                            Assertions.assertEquals(2,filter.removedVals.size());
                        }else{
                            Assertions.assertEquals(0b01,set.state);
                            Assertions.assertEquals(1,filter.removedVals.size());
                            Assertions.assertEquals(1,filter.retainedVals.size());
                            Assertions.assertTrue(filter.retainedVals.contains(Boolean.FALSE));
                            Assertions.assertFalse(filter.retainedVals.contains(Boolean.TRUE));
                        }
                    }else {
                        Assertions.assertEquals(0b10,set.state);
                        Assertions.assertEquals(1,filter.removedVals.size());
                        Assertions.assertEquals(1,filter.retainedVals.size());
                        Assertions.assertTrue(filter.retainedVals.contains(Boolean.TRUE));
                        Assertions.assertFalse(filter.retainedVals.contains(Boolean.FALSE));
                    }
                }else {
                    Assertions.assertEquals(2,filter.numRetained);
                    Assertions.assertEquals(0,filter.numRemoved);
                    Assertions.assertTrue(filter.removedVals.isEmpty());
                    Assertions.assertTrue(filter.retainedVals.contains(Boolean.TRUE));
                    Assertions.assertTrue(filter.retainedVals.contains(Boolean.FALSE));
                    Assertions.assertEquals(2,filter.retainedVals.size());
                }
            }
            expectedState=set.state;
        }

        @Override
        public void verifyArrayIsCopy(Object arr,boolean emptyArrayMayBeSame){
            //nothing to do
        }

        abstract class AbstractMonitoredItr implements MonitoredSet.MonitoredSetIterator<OmniIterator.OfBoolean,BooleanSetImpl>{

            final OmniIterator.OfBoolean itr;
            int expectedItrState;
            AbstractMonitoredItr(){
                itr=set.iterator();
                expectedItrState=expectedState;
            }

            @Override public OmniIterator.OfBoolean getIterator(){
                return itr;
            }

            @Override public MonitoredCollection<BooleanSetImpl> getMonitoredCollection(){
                return BooleanSetImplMonitor.this;
            }
        }

        class UncheckedMonitoredItr extends AbstractMonitoredItr{
            @Override
            public boolean nextWasJustCalled(){
                switch(expectedItrState){
                case 0b00:
                    return expectedState != 0b00;
                case 0b10:
                    return expectedState == 0b11;
                default:
                    return false;
                }
            }

            @Override public boolean hasNext(){
                return expectedItrState!=0b00;
            }

            @Override public int getNumLeft(){
                return Integer.bitCount(expectedItrState);
            }

            @Override public void verifyForEachRemaining(MonitoredFunction function){
                switch(expectedItrState) {
                case 0b00:
                    Assertions.assertTrue(function.isEmpty());
                    break;
                case 0b01:
                    Assertions.assertEquals(1,function.size());
                    Assertions.assertEquals(Boolean.FALSE,function.get(0));
                    break;
                case 0b10:
                    Assertions.assertEquals(1,function.size());
                    Assertions.assertEquals(Boolean.TRUE,function.get(0));
                    break;
                default:
                    Assertions.assertEquals(2,function.size());
                    Assertions.assertEquals(Boolean.FALSE,function.get(0));
                    Assertions.assertEquals(Boolean.TRUE,function.get(1));
                }
                expectedItrState=0b00;
            }


            @Override
            public void updateItrRemoveState(){
                if(expectedItrState == 0b00){
                    if(expectedState == 0b11){
                        expectedState=0b01;
                    }else{
                        expectedState=0b00;
                    }
                }else{
                    expectedState=0b10;
                }
            }

            @Override
            public void verifyCloneHelper(Object clone){
                Assertions.assertSame(set,FieldAndMethodAccessor.BooleanSetImpl.Itr.root(clone));
                Assertions.assertEquals(expectedItrState,FieldAndMethodAccessor.BooleanSetImpl.Itr.itrState(clone));
            }


            @Override
            public void updateItrNextState(){
                if(expectedItrState == 0b11){
                    expectedItrState=0b10;
                }else{
                    expectedItrState=0b00;
                }
            }

            @Override
            public void verifyNextResult(DataType outputType,Object result){
                Assertions.assertEquals(outputType.convertVal(expectedItrState == 0b10),result);
            }



        }

        class CheckedMonitoredItr extends AbstractMonitoredItr{


            @Override public boolean hasNext(){
                switch(expectedItrState){
                case 0b0001:
                case 0b0010:
                case 0b0011:
                case 0b0110:
                case 0b0111:
                    return true;
                default:
                    return false;
                }
            }

            @Override public int getNumLeft(){
                switch(expectedItrState) {
                case 0b0011:
                    return 2;
                case 0b0001:
                case 0b0010:
                case 0b0110:
                case 0b0111:
                    return 1;
                default:
                    return 0;
                }
            }
            @Override
            public void verifyNextResult(DataType outputType,Object result){
                boolean expected;
                switch(expectedItrState){
                case 0b0010:
                case 0b0110:
                case 0b0111:
                    expected=true;
                    break;
                default:
                    expected=false;
                }
                Assertions.assertEquals(outputType.convertVal(expected),result);
            }
            @Override public void verifyForEachRemaining(MonitoredFunction function){
                switch(expectedItrState){
                case 0b0001:
                    Assertions.assertEquals(1,function.size());
                    Assertions.assertEquals(Boolean.FALSE,function.get(0));
                    expectedItrState=0b0101;
                    break;
                case 0b0010:
                    Assertions.assertEquals(1,function.size());
                    Assertions.assertEquals(Boolean.TRUE,function.get(0));
                    expectedItrState=0b1010;
                    break;
                case 0b0011:
                    Assertions.assertEquals(2,function.size());
                    Assertions.assertEquals(Boolean.FALSE,function.get(0));
                    Assertions.assertEquals(Boolean.TRUE,function.get(1));
                    expectedItrState=0b1111;
                    break;
                case 0b0110:
                    Assertions.assertEquals(1,function.size());
                    Assertions.assertEquals(Boolean.TRUE,function.get(0));
                    expectedItrState=0b1110;
                    break;
                case 0b0111:
                    Assertions.assertEquals(1,function.size());
                    Assertions.assertEquals(Boolean.TRUE,function.get(0));
                    expectedItrState=0b1111;
                    break;
                default:
                    Assertions.assertTrue(function.isEmpty());
                }
            }


            @Override
            public boolean nextWasJustCalled(){
                switch(expectedItrState){
                case 0b0101:
                case 0b0111:
                case 0b1010:
                case 0b1110:
                case 0b1111:
                    return true;
                default:
                    return false;
                }
            }
            @Override
            public void updateItrRemoveState(){
                switch(expectedItrState){
                default:
                    expectedState=0b00;
                    expectedItrState=0b0100;
                    break;
                case 0b0111:
                    expectedState=0b10;
                    expectedItrState=0b0110;
                    break;
                case 0b1010:
                    expectedState=0b00;
                    expectedItrState=0b1000;
                    break;
                case 0b1110:
                    expectedState=0b00;
                    expectedItrState=0b1100;
                    break;
                case 0b1111:
                    expectedState=0b01;
                    expectedItrState=0b1101;
                }
            }

            @Override
            public void verifyCloneHelper(Object clone){
                Assertions.assertSame(set,FieldAndMethodAccessor.BooleanSetImpl.Checked.Itr.root(clone));
                Assertions.assertEquals(expectedItrState,
                        FieldAndMethodAccessor.BooleanSetImpl.Checked.Itr.itrState(clone));
            }


            @Override
            public void updateItrNextState(){
                switch(expectedItrState){
                default:
                    this.expectedItrState=0b0101;
                    break;
                case 0b0010:
                    this.expectedItrState=0b1010;
                    break;
                case 0b0011:
                    this.expectedItrState=0b0111;
                    break;
                case 0b0110:
                    this.expectedItrState=0b1110;
                    break;
                case 0b0111:
                    this.expectedItrState=0b1111;
                    break;
                }
            }



        }

        @Override public void updateClearState() {
            expectedState=0;
        }
        @Override
        public void writeObjectImpl(MonitoredObjectOutputStream oos) throws IOException{
            set.writeExternal(oos);
        }
    }
    private static final EnumSet<SetInitialization> VALID_INIT_SEQS=EnumSet.of(SetInitialization.Empty,
            SetInitialization.AddTrue,SetInitialization.AddFalse,
            SetInitialization.AddTrueAndFalse);
    private static final boolean[] POSSIBLE_VALS=new boolean[]{false,true};
    @Order(32)
    @Test
    public void testadd_val(){
        final var mayBeAddedTo=DataType.BOOLEAN.mayBeAddedTo();
        for(final var initSet:VALID_INIT_SEQS){
            for(final var inputType:mayBeAddedTo){
                for(final var checkedType:CheckedType.values()){
                    for(final var inputVal:POSSIBLE_VALS){
                        for(final var functionCallType:FunctionCallType.values()){
                            TestExecutorService.submitTest(()->{
                                var monitor=initSet.initialize(new BooleanSetImplMonitor(checkedType));
                                Assertions.assertEquals(!monitor.set.contains(inputVal),
                                        monitor.verifyAdd(inputVal,inputType,functionCallType));
                            });
                        }
                    }

                }
            }
        }
        TestExecutorService.completeAllTests("BooleanSetImplTest.testadd_val");
    }
    @Order(8)
    @Test
    public void testclear_void(){
        final BasicTest test=(checkedType,initSet)->initSet.initialize(new BooleanSetImplMonitor(checkedType))
                .verifyClear();
        test.runAllTests("BooleanSetImplTest.testclear_void");
    }
    @Order(8)
    @Test
    public void testclone_void(){
        final BasicTest test=(checkedType,initSet)->initSet.initialize(new BooleanSetImplMonitor(checkedType))
                .verifyClone();
        test.runAllTests("BooleanSetImplTest.testclone_void");
    }
    @Order(8)
    @Test
    public void testConstructor_int(){
        BasicTest test=(checkedType,initSet)->{
            int state;
            switch(initSet){
            case Empty:
                state=0b00;
                break;
            case AddFalse:
                state=0b01;
                break;
            case AddTrue:
                state=0b10;
                break;
            case AddTrueAndFalse:
                state=0b11;
                break;
            default:
                throw initSet.invalid();
            }
            initSet.initialize(new BooleanSetImplMonitor(checkedType,state)).verifyCollectionState();
        };
        test.runAllTests("BooleanSetImplTest.testConstructor_int");
    }
    @Order(2)
    @Test
    public void testConstructor_void(){
        for(final var checkedType:CheckedType.values()){
            TestExecutorService.submitTest(()->new BooleanSetImplMonitor(checkedType).verifyCollectionState());
        }
        TestExecutorService.completeAllTests("BooleanSetImplTest.testConstructor_void");
    }
    @Order(4128)
    @Test
    public void testcontains_val(){
        final QueryTest test=(monitor,queryVal,inputType,castType,modification)->monitor.verifyContains(queryVal,
                inputType,castType,modification);
        test.runAllTests("BooleanSetImplTest.testcontains_val");
    }
    @Order(346)
    @Test
    public void testforEach_Consumer(){
        for(final var initSet:VALID_INIT_SEQS){
            for(final var functionGen:StructType.BooleanSetImpl.validMonitoredFunctionGens){
                for(final var functionCallType:FunctionCallType.values()){
                    final long randSeedBound=functionGen.randomized
                            && initSet == SetInitialization.AddTrueAndFalse && !functionCallType.boxed?100:0;
                    for(final var checkedType:CheckedType.values()){
                        if(checkedType.checked || functionGen.expectedException == null || initSet.isEmpty){
                            LongStream.rangeClosed(0,randSeedBound).forEach(
                                    randSeed->TestExecutorService.submitTest(()->{
                                        final var monitor=initSet.initialize(new BooleanSetImplMonitor(checkedType));
                                        if(functionGen.expectedException == null || initSet.isEmpty){
                                            monitor.verifyForEach(functionGen,functionCallType,randSeed);
                                        }else{
                                            Assertions.assertThrows(functionGen.expectedException,
                                                    ()->monitor.verifyForEach(functionGen,functionCallType,randSeed));
                                            monitor.verifyCollectionState();
                                        }
                                    }));
                        }
                    }
                }
            }
        }

        TestExecutorService.completeAllTests("BooleanSetImplTest.testforEach_Consumer");
    }
    @Order(8)
    @Test
    public void testhashCode_void(){
        final BasicTest test=(checkedType,initSet)->initSet.initialize(new BooleanSetImplMonitor(checkedType))
                .verifyHashCode();
        test.runAllTests("BooleanSetImplTest.testhashCode_void");
    }
    @Order(8)
    @Test
    public void testisEmpty_void(){
        final BasicTest test=(checkedType,initSet)->initSet.initialize(new BooleanSetImplMonitor(checkedType))
                .verifyIsEmpty();
        test.runAllTests("BooleanSetImplTest.testisEmpty_void");
    }
    @Order(8)
    @Test
    public void testequals_Object(){
        final BasicTest test=(checkedType,initSet)->Assertions.assertFalse(initSet.initialize(new BooleanSetImplMonitor(checkedType))
                .getCollection().equals(null));
        test.runAllTests("BooleanSetImplTest.testequals_Object");
    }
    @Order(8)
    @Test
    public void testiterator_void(){
        final BasicTest test=(checkedType,initSet)->initSet.initialize(new BooleanSetImplMonitor(checkedType))
                .getMonitoredIterator()
                .verifyIteratorState();
        test.runAllTests("BooleanSetImplTest.testiterator_void");
    }
    @Order(8)
    @Test
    public void testItrclone_void(){
        final BasicTest test=(checkedType,initSet)->initSet.initialize(new BooleanSetImplMonitor(checkedType))
                .getMonitoredIterator()
                .verifyClone();
        test.runAllTests("BooleanSetImplTest.testItrclone_void");
    }
    @Order(1178)
    @Test
    public void testItrforEachRemaining_Consumer(){
        for(final var checkedType:CheckedType.values()){
            for(var initSet:VALID_INIT_SEQS){
                for(final var functionGen:IteratorType.AscendingItr.validMonitoredFunctionGens){
                    if(checkedType.checked || functionGen.expectedException == null || initSet.isEmpty){
                        for(final var preMod:IteratorType.AscendingItr.validPreMods){
                            if(checkedType.checked || preMod.expectedException == null || initSet.isEmpty){
                                int itrScenarioMax=0;
                                if(initSet == SetInitialization.AddTrueAndFalse){
                                    itrScenarioMax=2;
                                }
                                IntStream.rangeClosed(0,itrScenarioMax).forEach(itrScenario->{
                                    LongStream
                                    .rangeClosed(0,
                                            preMod.expectedException == null && functionGen.randomized
                                            && initSet == SetInitialization.AddTrueAndFalse
                                            && itrScenario == 0?100:0)
                                    .forEach(randSeed->{
                                        for(final var functionCallType:FunctionCallType.values()){
                                            TestExecutorService.submitTest(()->{
                                                final var setMonitor=initSet
                                                        .initialize(new BooleanSetImplMonitor(checkedType));
                                                final var itrMonitor=setMonitor.getMonitoredIterator();
                                                int adjustedState;
                                                switch(itrScenario){
                                                case 1:
                                                    itrMonitor.iterateForward();
                                                    adjustedState=0b10;
                                                    break;
                                                case 2:
                                                    itrMonitor.iterateForward();
                                                    itrMonitor.remove();
                                                    adjustedState=0b10;
                                                    break;
                                                default:
                                                    switch(initSet){
                                                    case AddFalse:
                                                        adjustedState=0b01;
                                                        break;
                                                    case AddTrue:
                                                        adjustedState=0b10;
                                                        break;
                                                    case AddTrueAndFalse:
                                                        adjustedState=0b11;
                                                        break;
                                                    case Empty:
                                                        adjustedState=0b00;
                                                        break;
                                                    default:
                                                        throw initSet.invalid();
                                                    }
                                                }
                                                itrMonitor.illegalMod(preMod);
                                                final Class<? extends Throwable> expectedException=adjustedState == 0b00
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
        TestExecutorService.completeAllTests("BooleanSetImplTest.testItrforEachRemaining_Consmer");
    }
    @Order(8)
    @Test
    public void testItrhasNext_void(){
        BasicTest test=(checkedType,initSet)->{
            final var setMonitor=initSet.initialize(new BooleanSetImplMonitor(checkedType));
            final var itrMonitor=setMonitor.getMonitoredIterator();
            final var setSize=setMonitor.size();
            for(int i=0;i < setSize;++i){
                Assertions.assertTrue(itrMonitor.verifyHasNext());
                itrMonitor.iterateForward();
            }
            Assertions.assertFalse(itrMonitor.verifyHasNext());
        };
        test.runAllTests("BooleanSetImplTest.testItrhasNext_void");
    }
    @Order(108)
    @Test
    public void testItrnext_void(){
        var outputTypes=DataType.BOOLEAN.validOutputTypes();
        for(var checkedType:CheckedType.values()) {
            for(var preMod:IteratorType.AscendingItr.validPreMods) {
                if(checkedType.checked || preMod.expectedException==null) {
                    for(var initSet:VALID_INIT_SEQS) {
                        if(preMod.expectedException==null) {
                            TestExecutorService.submitTest(()->{
                                final var setMonitor=initSet.initialize(new BooleanSetImplMonitor(checkedType));
                                for(var outputType:outputTypes) {
                                    var itrMonitor=setMonitor.getMonitoredIterator();
                                    while(itrMonitor.hasNext()) {
                                        itrMonitor.verifyNext(outputType);
                                    }
                                    Assertions.assertFalse(itrMonitor.getIterator().hasNext());
                                    if(checkedType.checked) {
                                        Assertions.assertThrows(NoSuchElementException.class,()->itrMonitor.verifyNext(outputType));
                                    }
                                }
                            });
                        }else {
                            for(var outputType:outputTypes) {
                                TestExecutorService.submitTest(()->{
                                    final var setMonitor=initSet.initialize(new BooleanSetImplMonitor(checkedType));
                                    var itrMonitor=setMonitor.getMonitoredIterator();
                                    itrMonitor.illegalMod(preMod);
                                    Assertions.assertThrows(preMod.expectedException,()->itrMonitor.verifyNext(outputType));
                                });
                            }
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("BooleanSetImplTest.testItrnext_void");
    }
    @Order(28)
    @Test
    public void testItrremove_void(){
        for(final var checkedType:CheckedType.values()){
            for(var initSet:VALID_INIT_SEQS){
                final int setSize;
                switch(initSet){
                case Empty:
                    setSize=0;
                    break;
                case AddTrue:
                case AddFalse:
                    setSize=1;
                    break;
                case AddTrueAndFalse:
                    setSize=2;
                    break;
                default:
                    throw initSet.invalid();
                }
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
                                IntStream.rangeClosed(itrOffset,itrBound).forEach(
                                        itrCount->TestExecutorService.submitTest(()->{
                                            final var setMonitor=initSet
                                                    .initialize(new BooleanSetImplMonitor(checkedType));
                                            final var itrMonitor=setMonitor.getMonitoredIterator();
                                            for(int i=0;i < itrCount;++i){
                                                itrMonitor.iterateForward();
                                            }
                                            itrRemoveScenario.initialize(itrMonitor);
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
                                                Assertions.assertThrows(expectedException,
                                                        ()->itrMonitor.verifyRemove());
                                                itrMonitor.verifyIteratorState();
                                                setMonitor.verifyCollectionState();
                                            }
                                        }));
                            }
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("BooleanSetImplTest.testItrremove_void");
    }
    @Order(20)
    @Test
    public void testReadAndWrite(){
        MonitoredFunctionGenTest test=(functionGen,checkedType,initSet)->{
            var monitor=initSet.initialize(new BooleanSetImplMonitor(checkedType));
            if(functionGen.expectedException==null) {
                Assertions.assertDoesNotThrow(()->monitor.verifyReadAndWrite(functionGen));
            }else {
                Assertions.assertThrows(functionGen.expectedException,()->monitor.verifyReadAndWrite(functionGen));
            }
        };
        test.runAllTests("BooleanSetImplTest.testReadAndWrite");
    }
    @Order(394)
    @Test
    public void testremoveIf_Predicate(){
        for(final var filterGen:StructType.BooleanSetImpl.validMonitoredRemoveIfPredicateGens){
            for(final var initSet:VALID_INIT_SEQS){
                for(final var functionCallType:FunctionCallType.values()){
                    final long randSeedBound=filterGen.predicateGenCallType==PredicateGenCallType.Randomized
                            && initSet == SetInitialization.AddTrueAndFalse && !functionCallType.boxed?100:0;
                    for(final var checkedType:CheckedType.values()){
                        if(checkedType.checked || filterGen.expectedException == null || initSet.isEmpty){
                            LongStream.rangeClosed(0,randSeedBound).forEach(
                                    randSeed->TestExecutorService.submitTest(()->{
                                        final var monitor=initSet.initialize(new BooleanSetImplMonitor(checkedType));
                                        int state=monitor.expectedState;
                                        var filter=filterGen.getMonitoredRemoveIfPredicate(monitor,0.5,new Random(randSeed));
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
                                                    throw filterGen.invalid();
                                                }
                                            }
                                        }else{
                                            Assertions.assertThrows(filterGen.expectedException,
                                                    ()->monitor.verifyRemoveIf(filter,functionCallType));
                                            monitor.verifyCollectionState();
                                        }
                                    }));
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("BooleanSetImplTest.testremoveIf_Predicate");
    }
    @Order(4128)
    @Test
    public void testremoveVal_val(){
        final QueryTest test=(monitor,queryVal,inputType,castType,modification)->monitor.verifyRemoveVal(queryVal,
                inputType,castType,modification);
        test.runAllTests("BooleanSetImplTest.testremoveVal_val");
    }
    @Order(8)
    @Test
    public void testsize_void(){
        final BasicTest test=(checkedType,initSet)->initSet.initialize(new BooleanSetImplMonitor(checkedType))
                .verifySize();
        test.runAllTests("BooleanSetImplTest.testsize_void");
    }
    @Order(20)
    @Test
    public void testtoArray_IntFunction(){
        final MonitoredFunctionGenTest test=(functionGen,checkedType,initSet)->{
            final var monitor=initSet.initialize(new BooleanSetImplMonitor(checkedType));
            if(functionGen.expectedException == null){
                monitor.verifyToArray(functionGen);
            }else{
                Assertions.assertThrows(functionGen.expectedException,()->monitor.verifyToArray(functionGen));
                monitor.verifyCollectionState();
            }
        };
        test.runAllTests("BooleanSetImplTest.testtoArray_IntFunction");
    }
    @Order(8)
    @Test
    public void testtoArray_ObjectArray(){
        final BasicTest test=(checkedType,initSet)->{
            int bound;
            switch(initSet){
            case Empty:
                bound=2;
                break;
            case AddTrue:
            case AddFalse:
                bound=3;
                break;
            case AddTrueAndFalse:
                bound=4;
                break;
            default:
                throw initSet.invalid();
            }
            var monitor=initSet.initialize(new BooleanSetImplMonitor(checkedType));
            for(int arrSize=0;arrSize <= bound;++arrSize){
                monitor.verifyToArray(new Object[arrSize]);
            }
        };
        test.runAllTests("BooleanSetImplTest.testtoArray_ObjectArray");
    }
    @Order(8)
    @Test
    public void testtoArray_void(){
        final BasicTest test=(checkedType,initSet)->{
            var monitor=initSet.initialize(new BooleanSetImplMonitor(checkedType));
            for(var outputType:DataType.BOOLEAN.validOutputTypes()) {
                outputType.verifyToArray(monitor);
            }
        };
        test.runAllTests("BooleanSetImplTest.testtoArray_void");
    }
    @Order(8)
    @Test
    public void testtoString_void(){
        final BasicTest test=(checkedType,initSet)->initSet.initialize(new BooleanSetImplMonitor(checkedType))
                .verifyToString();
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
        private void runAllTests(String testName){
            for(final var checkedType:CheckedType.values()){
                for(final var initSet:VALID_INIT_SEQS){
                    TestExecutorService.submitTest(()->runTest(checkedType,initSet));
                }
            }
            TestExecutorService.completeAllTests(testName);
        }
        void runTest(CheckedType checkedType,SetInitialization initSet);
    }
    private static interface MonitoredFunctionGenTest{
        private void runAllTests(String testName){
            for(final var functionGen:StructType.BooleanSetImpl.validMonitoredFunctionGens){
                for(final var checkedType:CheckedType.values()){
                    if(checkedType.checked || functionGen.expectedException == null){
                        for(final var initSet:VALID_INIT_SEQS){
                            TestExecutorService.submitTest(()->runTest(functionGen,checkedType,initSet));
                        }
                    }
                }
            }
            TestExecutorService.completeAllTests(testName);
        }
        void runTest(MonitoredFunctionGen functionGen,CheckedType checkedType,SetInitialization initSet);
    }
    private static interface QueryTest{
        boolean callMethod(BooleanSetImplMonitor monitor,QueryVal queryVal,DataType inputType,QueryCastType castType,
                QueryVal.QueryValModification modification);
        private void runAllTests(String testName){
            for(final var queryVal:QueryVal.values()){
                if(DataType.BOOLEAN.isValidQueryVal(queryVal)){
                    queryVal.validQueryCombos.forEach((modification,castTypesToInputTypes)->{
                        castTypesToInputTypes.forEach((castType,inputTypes)->{
                            inputTypes.forEach(inputType->{
                                final boolean queryCanReturnTrue=queryVal.queryCanReturnTrue(modification,castType,
                                        inputType,DataType.BOOLEAN);
                                for(final var checkedType:CheckedType.values()){
                                    for(final var initSet:VALID_INIT_SEQS){
                                        TestExecutorService.submitTest(()->runTest(queryCanReturnTrue,queryVal,
                                                modification,inputType,castType,checkedType,initSet));
                                    }
                                }
                            });
                        });
                    });
                }
            }
            TestExecutorService.completeAllTests(testName);
        }
        private void runTest(boolean queryCanReturnTrue,QueryVal queryVal,QueryVal.QueryValModification modification,
                DataType inputType,QueryCastType castType,CheckedType checkedType,SetInitialization initSet){
            boolean expectedResult;
            if(queryCanReturnTrue){
                final boolean booleanInputVal=queryVal.getBooleanVal(modification);
                switch(initSet){
                case Empty:
                    expectedResult=false;
                    break;
                case AddTrue:
                    expectedResult=booleanInputVal;
                    break;
                case AddFalse:
                    expectedResult=!booleanInputVal;
                    break;
                case AddTrueAndFalse:
                    expectedResult=true;
                    break;
                default:
                    throw initSet.invalid();
                }
            }else{
                expectedResult=false;
            }
            final boolean actualResult=callMethod(initSet.initialize(new BooleanSetImplMonitor(checkedType)),queryVal,
                    inputType,
                    castType,modification);
            Assertions.assertEquals(expectedResult,actualResult);
        }
    }
}
