package omni.impl.set;
import java.io.IOException;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
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
@TestMethodOrder(OrderAnnotation.class)
@Tag("NewTest")
public class ByteSetImplTest{
    private static final EnumSet<SetInitialization> VALID_INIT_SEQS=EnumSet.of(SetInitialization.Empty,
            SetInitialization.AddTrue,SetInitialization.AddFalse,
            SetInitialization.AddTrueAndFalse,SetInitialization.AddPrime,
            SetInitialization.AddFibSeq,SetInitialization.AddMinByte,
            SetInitialization.FillWord0,SetInitialization.FillWord1,SetInitialization.FillWord2,
            SetInitialization.FillWord3);
    private static final double[] RANDOM_THRESHOLDS=new double[]{0.01,0.05,0.10,0.25,0.50,0.75,0.90,0.95,0.99};
    private static final double[] NON_RANDOM_THRESHOLD=new double[]{0.5};
    private static int getSize(long...words){
        int size=0;
        for(final var word:words){
            size+=Long.bitCount(word);
        }
        return size;
    }
    @Order(88)
    @Test
    public void testadd_val(){
        for(final var inputType:DataType.BYTE.mayBeAddedTo()){
            final int min=inputType.getMinInt();
            final int max=inputType.getMaxInt();
            for(final var checkedType:CheckedType.values()){
                for(final var initSet:VALID_INIT_SEQS){
                    for(final var functionCallType:FunctionCallType.values()){
                        TestExecutorService.submitTest(()->{
                            final var monitor=initSet.initialize(new ByteSetImplMonitor(checkedType,0,0,0,0));
                            for(int i=min;i <= max;++i){
                                Assertions.assertEquals(!monitor.set.contains((byte)DataType.BYTE.convertVal(i)),
                                        monitor.verifyAdd(inputType.convertVal(i),inputType,functionCallType));
                            }
                        });
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("ByteSetImplTest.testadd_val");
    }
    @Order(22)
    @Test
    public void testclear_void(){
        final BasicTest test=(checkedType,initSet)->initSet.initialize(new ByteSetImplMonitor(checkedType))
                .verifyClear();
        test.runAllTests("ByteSetImplTest.testclear_void");
    }
    @Order(22)
    @Test
    public void testclone_void(){
        final BasicTest test=(checkedType,initSet)->initSet.initialize(new ByteSetImplMonitor(checkedType))
                .verifyClone();
        test.runAllTests("ByteSetImplTest.testclone_void");
    }
    @Order(162)
    @Test
    public void testConstructor_longlonglonglong(){
        final long[] wordStates=new long[]{0,1,-1};
        for(final var checkedType:CheckedType.values()){
            for(final var word0:wordStates){
                for(final var word1:wordStates){
                    for(final var word2:wordStates){
                        for(final var word3:wordStates){
                            TestExecutorService
                            .submitTest(()->new ByteSetImplMonitor(checkedType,word0,word1,word2,word3)
                                    .verifyCollectionState());
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("ByteSetImplTest.testConstructor_longlonglonglong");
    }
    @Order(81)
    @Test
    public void testConstructor_longlonglonglongint(){
        final long[] wordStates=new long[]{0,1,-1};
        for(final var word0:wordStates){
            for(final var word1:wordStates){
                for(final var word2:wordStates){
                    for(final var word3:wordStates){
                        TestExecutorService.submitTest(
                                ()->new ByteSetImplMonitor(word0,word1,word2,word3,getSize(word0,word1,word2,word3))
                                .verifyCollectionState());
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("ByteSetImplTest.testConstructor_longlonglonglongint");
    }
    @Order(2)
    @Test
    public void testConstructor_void(){
        for(final var checkedType:CheckedType.values()){
            TestExecutorService.submitTest(()->new ByteSetImplMonitor(checkedType).verifyCollectionState());
        }
        TestExecutorService.completeAllTests("ByteSetImplTest.testConstructor_void");
    }
    @Order(11352)
    @Test
    public void testcontains_val(){
        final QueryTest test=(monitor,queryVal,inputType,castType,modification)->monitor.verifyContains(queryVal,
                inputType,castType,modification);
        test.runAllTests("ByteSetImplTest.testcontains_val");
    }
    @Order(1916)
    @Test
    public void testforEach_Consumer(){
        for(final var functionGen:StructType.ByteSetImpl.validMonitoredFunctionGens){
            for(final var initSet:VALID_INIT_SEQS){
                final int size=initSet.initialize(new ByteSetImplMonitor(CheckedType.UNCHECKED)).size();
                for(final var functionCallType:FunctionCallType.values()){
                    final long randSeedBound=functionGen.randomized && size > 1 && !functionCallType.boxed?100:0;
                    for(final var checkedType:CheckedType.values()){
                        if(checkedType.checked || functionGen.expectedException == null || size == 0){
                            LongStream.rangeClosed(0,randSeedBound).forEach(
                                    randSeed->TestExecutorService.submitTest(()->{
                                        final var monitor=initSet.initialize(new ByteSetImplMonitor(checkedType));
                                        if(functionGen.expectedException == null || monitor.size() == 0){
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
        TestExecutorService.completeAllTests("ByteSetImplTest.forEach_Consumer");
    }
    @Order(22)
    @Test
    public void testhashCode_void(){
        final BasicTest test=(checkedType,initSet)->initSet.initialize(new ByteSetImplMonitor(checkedType))
                .verifyHashCode();
        test.runAllTests("ByteSetImplTest.testhashCode_void");
    }
    @Order(22)
    @Test
    public void testequals_Object(){
        final BasicTest test=(checkedType,initSet)->Assertions.assertFalse(initSet.initialize(new ByteSetImplMonitor(checkedType))
                .getCollection().equals(null));
        test.runAllTests("ByteSetImplTest.testequals_Object");
    }
    @Order(22)
    @Test
    public void testisEmpty_void(){
        final BasicTest test=(checkedType,initSet)->initSet.initialize(new ByteSetImplMonitor(checkedType))
                .verifyIsEmpty();
        test.runAllTests("ByteSetImplTest.testisEmpty_void");
    }
    @Order(22)
    @Test
    public void testiterator_void(){
        final BasicTest test=(checkedType,initSet)->initSet.initialize(new ByteSetImplMonitor(checkedType))
                .getMonitoredIterator().verifyIteratorState();
        test.runAllTests("ByteSetImplTest.testiterator_void");
    }
    @Order(22)
    @Test
    public void testItrclone_void(){
        final BasicTest test=(checkedType,initSet)->initSet.initialize(new ByteSetImplMonitor(checkedType))
                .getMonitoredIterator().verifyClone();
        test.runAllTests("ByteSetImplTest.testItrclone_void");
    }
    @Order(6620)
    @Test
    public void testItrforEachRemaining_Consumer(){
        for(final var initSet:VALID_INIT_SEQS){
            final int size=initSet.initialize(new ByteSetImplMonitor(CheckedType.UNCHECKED)).size();
            for(final var checkedType:CheckedType.values()){
                for(final var functionGen:IteratorType.AscendingItr.validMonitoredFunctionGens){
                    if(checkedType.checked || functionGen.expectedException == null || size == 0){
                        for(final var preMod:IteratorType.AscendingItr.validPreMods){
                            if(checkedType.checked || preMod.expectedException == null || size == 0){
                                int itrScenarioMax=0;
                                if(size > 1){
                                    itrScenarioMax=2;
                                }
                                IntStream.rangeClosed(0,itrScenarioMax).forEach(itrScenario->{
                                    LongStream.rangeClosed(0,preMod.expectedException == null && functionGen.randomized
                                            && size > 1 && itrScenario == 0?100:0).forEach(randSeed->{
                                                for(final var functionCallType:FunctionCallType.values()){
                                                    TestExecutorService.submitTest(()->{
                                                        final var setMonitor=initSet
                                                                .initialize(new ByteSetImplMonitor(checkedType));
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
                                                        final Class<? extends Throwable> expectedException=numLeft == 0
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
        TestExecutorService.completeAllTests("ByteSetImplTest.testItrforEachRemaining_Consumer");
    }
    @Order(22)
    @Test
    public void testItrhasNext_void(){
        final BasicTest test=(checkedType,initSet)->{
            final var setMonitor=initSet.initialize(new ByteSetImplMonitor(checkedType));
            final var itrMonitor=setMonitor.getMonitoredIterator();
            final var setSize=setMonitor.size();
            for(int i=0;i < setSize;++i){
                Assertions.assertTrue(itrMonitor.verifyHasNext());
                itrMonitor.iterateForward();
            }
            Assertions.assertFalse(itrMonitor.verifyHasNext());
        };
        test.runAllTests("ByteSetImplTest.testItrhasNext_void");
    }
    @Order(231)
    @Test
    public void testItrnext_void(){
        for(final var outputType:DataType.BYTE.validOutputTypes()){
            for(final var checkedType:CheckedType.values()){
                for(final var preMod:IteratorType.AscendingItr.validPreMods){
                    if(checkedType.checked || preMod.expectedException == null){
                        for(final var initSet:VALID_INIT_SEQS){
                            TestExecutorService
                            .submitTest(()->{
                                final var setMonitor=initSet.initialize(new ByteSetImplMonitor(checkedType));
                                final var itrMonitor=setMonitor.getMonitoredIterator();
                                itrMonitor.illegalMod(preMod);
                                if(preMod.expectedException == null){
                                    while(itrMonitor.hasNext()){
                                        itrMonitor.verifyNext(outputType);
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
        TestExecutorService.completeAllTests("ByteSetImplTest.testItrnext_void");
    }
    @Order(1747)
    @Test
    public void testItrremove_void(){
        for(final var initSet:VALID_INIT_SEQS){
            final int setSize=initSet.initialize(new ByteSetImplMonitor(CheckedType.UNCHECKED)).size();
            for(final var checkedType:CheckedType.values()){
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
                                                    .initialize(new ByteSetImplMonitor(checkedType));
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
        TestExecutorService.completeAllTests("ByteSetImplTest.testItrremove_void");
    }
    @Order(55)
    @Test
    public void testReadAndWrite(){
        final MonitoredFunctionGenTest test=(functionGen,checkedType,initSet)->{
            var monitor=initSet.initialize(new ByteSetImplMonitor(checkedType));
            if(functionGen.expectedException==null) {
                Assertions.assertDoesNotThrow(()->monitor.verifyReadAndWrite(functionGen));
            }else {
                Assertions.assertThrows(functionGen.expectedException,()->monitor.verifyReadAndWrite(functionGen));
            }
        };
        test.runAllTests("ByteSetImplTest.testReadAndWrite");
    }
    @Order(27532)
    @Test
    public void testremoveIf_Predicate(){
        for(final var initSet:VALID_INIT_SEQS){
            final int setSize=initSet.initialize(new ByteSetImplMonitor(CheckedType.UNCHECKED)).size();
            for(final var filterGen:StructType.ByteSetImpl.validMonitoredRemoveIfPredicateGens){
                for(final var functionCallType:FunctionCallType.values()){
                    final long randSeedBound;
                    final double[] thresholdArr;
                    if(filterGen.predicateGenCallType==PredicateGenCallType.Randomized && setSize > 1 && !functionCallType.boxed){
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
                                            .submitTest(()->{
                                                final var monitor=initSet
                                                        .initialize(new ByteSetImplMonitor(checkedType));
                                                final long[] expectedWords=Arrays.copyOf(monitor.expectedWords,
                                                        4);
                                                final var filter=filterGen.getMonitoredRemoveIfPredicate(
                                                        monitor,threshold,new Random(randSeed));
                                                final int sizeBefore=monitor.size();
                                                if(filterGen.expectedException == null || sizeBefore == 0){
                                                    final boolean result=monitor.verifyRemoveIf(filter,
                                                            functionCallType);
                                                    if(sizeBefore == 0b00){
                                                        Assertions.assertFalse(result);
                                                    }else{
                                                        switch(filterGen){
                                                        case Random:
                                                            Assertions.assertEquals(filter.numRemoved != 0,
                                                            result);
                                                            break;
                                                        case RemoveAll:
                                                            Assertions.assertTrue(monitor.set.isEmpty());
                                                            Assertions.assertTrue(result);
                                                            break;
                                                        case RemoveFalse:
                                                            Assertions.assertFalse(monitor.set.contains(false));
                                                            Assertions.assertEquals(
                                                                    (expectedWords[2] & 0b01) != 0,result);
                                                            break;
                                                        case RemoveNone:
                                                            Assertions.assertFalse(result);
                                                            Assertions.assertFalse(monitor.set.isEmpty());
                                                            break;
                                                        case RemoveTrue:
                                                            Assertions.assertFalse(monitor.set.contains(true));
                                                            Assertions.assertEquals(
                                                                    (expectedWords[2] & 0b10) != 0,result);
                                                            break;
                                                        default:
                                                            throw filterGen.invalid();
                                                        }
                                                    }
                                                }else{
                                                    Assertions.assertThrows(filterGen.expectedException,
                                                            ()->monitor.verifyRemoveIf(filter,
                                                                    functionCallType));
                                                    monitor.verifyCollectionState();
                                                }
                                            })));
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("ByteSetImplTest.testremoveIf_Predicate");
    }
    @Order(11352)
    @Test
    public void testremoveVal_val(){
        final QueryTest test=(monitor,queryVal,inputType,castType,modification)->monitor.verifyRemoveVal(queryVal,
                inputType,castType,modification);
        test.runAllTests("ByteSetImplTest.testremoveVal_val");
    }
    @Order(22)
    @Test
    public void testsize_void(){
        final BasicTest test=(checkedType,initSet)->initSet.initialize(new ByteSetImplMonitor(checkedType))
                .verifySize();
        test.runAllTests("ByteSetImplTest.testsize_void");
    }
    @Order(55)
    @Test
    public void testtoArray_IntFunction(){
        final MonitoredFunctionGenTest test=(functionGen,checkedType,initSet)->{
            final var monitor=initSet.initialize(new ByteSetImplMonitor(checkedType));
            if(functionGen.expectedException == null){
                monitor.verifyToArray(functionGen);
            }else{
                Assertions.assertThrows(functionGen.expectedException,()->monitor.verifyToArray(functionGen));
                monitor.verifyCollectionState();
            }
        };
        test.runAllTests("ByteSetImplTest.testToArray_IntFunction");
    }
    @Order(188)
    @Test
    public void testtoArray_ObjectArray(){
        for(final var initSet:VALID_INIT_SEQS){
            final int size=initSet.initialize(new ByteSetImplMonitor(CheckedType.UNCHECKED)).size();
            for(final var checkedType:CheckedType.values()){
                for(int arrSize=0,increment=Math.max(1,size / 10),bound=size + increment
                        + 2;arrSize <= bound;arrSize+=increment){
                    final Object[] paramArr=new Object[arrSize];
                    TestExecutorService.submitTest(
                            ()->initSet.initialize(new ByteSetImplMonitor(checkedType)).verifyToArray(paramArr));
                }
            }
        }
        TestExecutorService.completeAllTests("ByteSetImplTest.testtoArray_ObjectArray");
    }
    @Order(154)
    @Test
    public void testtoArray_void(){
        for(final var outputType:DataType.BYTE.validOutputTypes()){
            for(final var checkedType:CheckedType.values()){
                for(final var initSet:VALID_INIT_SEQS){
                    TestExecutorService.submitTest(
                            ()->outputType.verifyToArray(initSet.initialize(new ByteSetImplMonitor(checkedType))));
                }
            }
        }
        TestExecutorService.completeAllTests("ByteSetImplTest.testtoArray_void");
    }
    @Order(22)
    @Test
    public void testtoString_void(){
        final BasicTest test=(checkedType,initSet)->initSet.initialize(new ByteSetImplMonitor(checkedType))
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
    private static class ByteSetImplMonitor implements MonitoredSet<ByteSetImpl>{
        final CheckedType checkedType;
        final ByteSetImpl set;
        final long[] expectedWords;
        int expectedSize;
        int expectedModCount;
        ByteSetImplMonitor(CheckedType checkedType){
            this.checkedType=checkedType;
            if(checkedType.checked){
                set=new ByteSetImpl.Checked();
            }else{
                set=new ByteSetImpl();
            }
            expectedWords=new long[4];
        }
        @Override public MonitoredIterator<? extends OmniIterator<?>,ByteSetImpl> getMonitoredIterator(IteratorType itrType){
            if(itrType!=IteratorType.AscendingItr) {
                throw itrType.invalid();
            }
            return getMonitoredIterator();
        }
        @Override public MonitoredIterator<? extends OmniIterator<?>,ByteSetImpl> getMonitoredIterator(int index,IteratorType itrType){
            var itrMonitor=getMonitoredIterator(itrType);
            while(--index>=0 && itrMonitor.hasNext()) {
                itrMonitor.iterateForward();
            }
            return itrMonitor;
        }
        @Override
        public Object get(int iterationIndex,DataType outputType) {
            var itr=set.iterator();
            while(iterationIndex>0) {
                itr.nextByte();
            }
            return outputType.convertVal(itr.nextByte());
        }
        ByteSetImplMonitor(CheckedType checkedType,long word0,long word1,long word2,long word3){
            this.checkedType=checkedType;
            expectedWords=new long[4];
            expectedWords[0]=word0;
            expectedWords[1]=word1;
            expectedWords[2]=word2;
            expectedWords[3]=word3;
            if(checkedType.checked){
                set=new ByteSetImpl.Checked(word0,word1,word2,word3);
            }else{
                set=new ByteSetImpl(word0,word1,word2,word3);
            }
            expectedSize=getExpectedSize();
        }
        ByteSetImplMonitor(long word0,long word1,long word2,long word3,int expectedSize){
            checkedType=CheckedType.CHECKED;
            expectedWords=new long[4];
            expectedWords[0]=word0;
            expectedWords[1]=word1;
            expectedWords[2]=word2;
            expectedWords[3]=word3;
            set=new ByteSetImpl.Checked(word0,word1,word2,word3,expectedSize);
            this.expectedSize=getExpectedSize();
        }
        @Override
        public CheckedType getCheckedType(){
            return checkedType;
        }
        @Override
        public ByteSetImpl getCollection(){
            return set;
        }
        @Override
        public DataType getDataType(){
            return DataType.BYTE;
        }
        @Override
        public MonitoredIterator<? extends OmniIterator<?>,ByteSetImpl> getMonitoredIterator(){
            int expectedValOffset;
            for(expectedValOffset=-128;expectedValOffset < 128;++expectedValOffset){
                if((expectedWords[(expectedValOffset >> 6) + 2] & 1L << expectedValOffset) != 0){
                    break;
                }
            }
            final var itr=set.iterator();
            if(checkedType.checked){
                return new CheckedMonitoredItr(itr,expectedValOffset,expectedModCount);
            }
            return new UncheckedMonitoredItr(itr,expectedValOffset);
        }
        @Override
        public StructType getStructType(){
            return StructType.ByteSetImpl;
        }
        @Override
        public void modCollection(){
            for(int i=Byte.MIN_VALUE;i <= Byte.MAX_VALUE;++i){
                if(set.add((byte)i)){
                    expectedWords[(i >> 6) + 2]|=1L << i;
                    ++expectedSize;
                    ++expectedModCount;
                    return;
                }
            }
            set.clear();
            for(int i=0;i < 4;++i){
                expectedWords[i]=0;
            }
            expectedSize=0;
            ++expectedModCount;
        }
        @Override
        public void removeFromExpectedState(DataType inputType,QueryVal queryVal,QueryValModification modification){
            final byte v=(byte)queryVal.getInputVal(DataType.BYTE,modification);
            expectedWords[(v >> 6) + 2]&=~(1L << v);
            --expectedSize;
            ++expectedModCount;
        }
        @Override
        public int size(){
            return expectedSize;
        }
        @Override
        public void updateAddState(Object inputVal,DataType inputType,boolean result){
            byte v;
            switch(inputType){
            case BOOLEAN:
                v=(boolean)inputVal?(byte)1:(byte)0;
                break;
            case BYTE:
                v=(byte)inputVal;
                break;
            default:
                throw inputType.invalid();
            }
            expectedWords[(v >> 6) + 2]|=1L << v;
            if(result){
                ++expectedSize;
                ++expectedModCount;
            }
        }
        @Override
        public void updateCollectionState(){
            if(checkedType.checked){
                expectedModCount=((ByteSetImpl.Checked)set).modCount;
            }
            expectedWords[0]=set.word0;
            expectedWords[1]=set.word1;
            expectedWords[2]=set.word2;
            expectedWords[3]=set.word3;
            expectedSize=getExpectedSize();
        }
        @Override
        public void verifyArrayIsCopy(Object arr,boolean emptyArrayMayBeSame){
            // nothing to do
        }
        @Override public void updateClearState() {
            for(int i=0;i < 4;++i){
                expectedWords[i]=0;
            }
            expectedSize=0;
            ++expectedModCount;
        }

        @Override
        public void verifyClone(Object clone){
            ByteSetImpl cast;
            Assertions.assertEquals(expectedWords[0],(cast=(ByteSetImpl)clone).word0);
            Assertions.assertEquals(expectedWords[1],cast.word1);
            Assertions.assertEquals(expectedWords[2],cast.word2);
            Assertions.assertEquals(expectedWords[3],cast.word3);
            if(checkedType.checked){
                ByteSetImpl.Checked checked;
                Assertions.assertEquals(0,(checked=(ByteSetImpl.Checked)cast).modCount);
                Assertions.assertEquals(expectedSize,checked.size);
            }
        }
        @Override
        public void verifyCollectionState(){
            if(checkedType.checked){
                ByteSetImpl.Checked cast;
                Assertions.assertEquals(expectedModCount,(cast=(ByteSetImpl.Checked)set).modCount);
                Assertions.assertEquals(expectedSize,cast.size);
            }
            Assertions.assertEquals(expectedWords[0],set.word0);
            Assertions.assertEquals(expectedWords[1],set.word1);
            Assertions.assertEquals(expectedWords[2],set.word2);
            Assertions.assertEquals(expectedWords[3],set.word3);
        }
        @Override
        public void verifyRemoveIf(boolean result,MonitoredRemoveIfPredicate filter){
            Assertions.assertEquals(expectedSize,filter.numCalls);
            if(result){
                ++expectedModCount;
            }
            Assertions.assertEquals(!result,filter.removedVals.isEmpty());
            for(final var removedVal:filter.removedVals){
                final byte v=(byte)removedVal;
                expectedWords[(v >> 6) + 2]&=~(1L << v);
                --expectedSize;
            }
            Assertions.assertEquals(expectedSize,filter.retainedVals.size());
        }
        @Override
        public void writeObjectImpl(MonitoredObjectOutputStream oos) throws IOException{
            set.writeExternal(oos);
        }
        private int getExpectedSize(){
            int size=0;
            for(final var word:expectedWords){
                size+=Long.bitCount(word);
            }
            return size;
        }
        abstract class AbstractMonitoredItr
        implements
        MonitoredSet.MonitoredSetIterator<OmniIterator.OfByte,ByteSetImpl>{
            final OmniIterator.OfByte itr;
            int expectedValOffset;
            AbstractMonitoredItr(OmniIterator.OfByte itr,int expectedValOffset){
                this.itr=itr;
                this.expectedValOffset=expectedValOffset;
            }
            @Override
            public OmniIterator.OfByte getIterator(){
                return itr;
            }
            @Override
            public MonitoredCollection<ByteSetImpl> getMonitoredCollection(){
                return ByteSetImplMonitor.this;
            }
            @Override
            public int getNumLeft(){
                int numLeft=0;
                int expectedValOffset=this.expectedValOffset;
                for(int i=(expectedValOffset >> 6) + 2;i < 4;++i){
                    numLeft+=Long.bitCount(expectedWords[i] >>> expectedValOffset);
                    expectedValOffset=0;
                }
                return numLeft;
            }
            @Override
            public boolean hasNext(){
                return expectedValOffset != 128;
            }
            @Override
            public void updateItrNextState(){
                int expectedValOffset;
                switch((expectedValOffset=this.expectedValOffset + 1) >> 6){
                case -2:
                    int tail0s;
                    if((tail0s=Long.numberOfTrailingZeros(expectedWords[0] >>> expectedValOffset)) != 64){
                        this.expectedValOffset=expectedValOffset + tail0s;
                        break;
                    }
                    expectedValOffset=-64;
                case -1:
                    if((tail0s=Long.numberOfTrailingZeros(expectedWords[1] >>> expectedValOffset)) != 64){
                        this.expectedValOffset=expectedValOffset + tail0s;
                        break;
                    }
                    expectedValOffset=0;
                case 0:
                    if((tail0s=Long.numberOfTrailingZeros(expectedWords[2] >>> expectedValOffset)) != 64){
                        this.expectedValOffset=expectedValOffset + tail0s;
                        break;
                    }
                    expectedValOffset=64;
                case 1:
                    if((tail0s=Long.numberOfTrailingZeros(expectedWords[3] >>> expectedValOffset)) != 64){
                        this.expectedValOffset=expectedValOffset + tail0s;
                        break;
                    }
                default:
                    this.expectedValOffset=128;
                }
            }
            @Override
            public void verifyNextResult(DataType outputType,Object result){
                Assertions.assertEquals(outputType.convertVal((byte)expectedValOffset),result);
            }
        }
        class CheckedMonitoredItr extends AbstractMonitoredItr{
            int expectedItrModCount;
            int expectedItrLastRet;

            CheckedMonitoredItr(OmniIterator.OfByte itr,int expectedValOffset,int expectedItrModCount){
                super(itr,expectedValOffset);
                this.expectedItrModCount=expectedItrModCount;
                expectedItrLastRet=-129;
            }
            @Override
            public void updateItrNextState(){
                expectedItrLastRet=expectedValOffset;
                super.updateItrNextState();
            }
            @Override
            public void updateItrRemoveState(){
                expectedWords[(expectedItrLastRet >> 6) + 2]&=~(1L << expectedItrLastRet);
                --expectedSize;
                ++expectedModCount;
                ++expectedItrModCount;
                expectedItrLastRet=-129;
            }
            @Override
            public void verifyForEachRemaining(MonitoredFunction function){
                final var monitoredFunctionItr=function.iterator();
                int expectedValOffset;
                if((expectedValOffset=this.expectedValOffset) < 128){
                    var expectedWords=ByteSetImplMonitor.this.expectedWords;
                    int expectedLastRet;
                    outer:for(;;){
                        final var v=(Byte)monitoredFunctionItr.next();
                        Assertions.assertEquals(v.byteValue(),expectedValOffset);
                        expectedLastRet=expectedValOffset;
                        for(;++expectedValOffset != 128;){
                            if((expectedWords[(expectedValOffset >> 6) + 2] & 1L << expectedValOffset) != 0){
                                continue outer;
                            }
                        }
                        break;
                    }
                    this.expectedValOffset=128;
                    expectedItrLastRet=expectedLastRet;
                }
                switch(function.getMonitoredFunctionGen()){
                default:
                case ThrowIOBModItr:
                    Assertions.assertFalse(monitoredFunctionItr.hasNext());
                case ModItr:
                }
            }
            @Override
            public void verifyCloneHelper(Object clone){
                Assertions.assertEquals(expectedValOffset,
                        FieldAndMethodAccessor.ByteSetImpl.Checked.Itr.valOffset(clone));
                Assertions.assertEquals(expectedItrLastRet,
                        FieldAndMethodAccessor.ByteSetImpl.Checked.Itr.lastRet(clone));
                Assertions.assertEquals(expectedItrModCount,
                        FieldAndMethodAccessor.ByteSetImpl.Checked.Itr.modCount(clone));
                Assertions.assertSame(set,FieldAndMethodAccessor.ByteSetImpl.Checked.Itr.root(clone));
            }
            @Override
            public boolean nextWasJustCalled(){
                return expectedItrLastRet != -129;
            }
        }
        class UncheckedMonitoredItr extends AbstractMonitoredItr{
            UncheckedMonitoredItr(OmniIterator.OfByte itr,int expectedValOffset){
                super(itr,expectedValOffset);
            }
            @Override
            public void updateItrRemoveState(){
                for(int valOffset=this.expectedValOffset,wordOffset=(valOffset - 1 >> 6) + 2;;--wordOffset,valOffset=0){
                    long word;
                    if((valOffset=Long
                            .numberOfLeadingZeros((word=expectedWords[wordOffset]) & -1L >>> -valOffset)) != 64){
                        expectedWords[wordOffset]=word & ~(Long.MIN_VALUE >>> valOffset);
                        return;
                    }
                }
            }
            @Override
            public boolean nextWasJustCalled(){
                var expectedWords=ByteSetImplMonitor.this.expectedWords;
                for(int valOffset=this.expectedValOffset,wordOffset=(valOffset - 1 >> 6)
                        + 2;wordOffset >= 0;--wordOffset,valOffset=0){
                    if((valOffset=Long.numberOfLeadingZeros(expectedWords[wordOffset] & -1L >>> -valOffset)) != 64){
                        return true;
                    }
                }
                return false;
            }
            @Override
            public void verifyForEachRemaining(MonitoredFunction function){
                final var monitoredFunctionItr=function.iterator();
                int expectedValOffset;
                if((expectedValOffset=this.expectedValOffset) < 128){
                    var expectedWords=ByteSetImplMonitor.this.expectedWords;
                    outer:for(;;){
                        final var v=(Byte)monitoredFunctionItr.next();
                        Assertions.assertEquals(v.byteValue(),expectedValOffset);
                        for(;++expectedValOffset != 128;){
                            if((expectedWords[(expectedValOffset >> 6) + 2] & 1L << expectedValOffset) != 0){
                                continue outer;
                            }
                        }
                        break;
                    }
                    this.expectedValOffset=128;
                }
                Assertions.assertFalse(monitoredFunctionItr.hasNext());
            }
            @Override
            public void verifyCloneHelper(Object clone){
                Assertions.assertEquals(expectedValOffset,FieldAndMethodAccessor.ByteSetImpl.Itr.valOffset(clone));
                Assertions.assertSame(set,FieldAndMethodAccessor.ByteSetImpl.Itr.root(clone));
            }
        }
    }
    private static interface MonitoredFunctionGenTest{
        private void runAllTests(String testName){
            for(final var functionGen:StructType.ByteSetImpl.validMonitoredFunctionGens){
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
        boolean callMethod(ByteSetImplMonitor monitor,QueryVal queryVal,DataType inputType,QueryCastType castType,
                QueryVal.QueryValModification modification);
        private void runAllTests(String testName){
            for(final var queryVal:QueryVal.values()){
                if(DataType.BYTE.isValidQueryVal(queryVal)){
                    queryVal.validQueryCombos.forEach((modification,castTypesToInputTypes)->{
                        castTypesToInputTypes.forEach((castType,inputTypes)->{
                            inputTypes.forEach(inputType->{
                                final boolean queryCanReturnTrue=queryVal.queryCanReturnTrue(modification,castType,
                                        inputType,DataType.BYTE);
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
            final var monitor=initSet.initialize(new ByteSetImplMonitor(checkedType));
            if(queryCanReturnTrue){
                final byte byteInputVal=queryVal.getByteVal(modification);
                expectedResult=(monitor.expectedWords[(byteInputVal >> 6) + 2] & 1L << byteInputVal) != 0;
            }else{
                expectedResult=false;
            }
            final boolean actualResult=callMethod(monitor,queryVal,inputType,castType,modification);
            Assertions.assertEquals(expectedResult,actualResult);
        }
    }
}
