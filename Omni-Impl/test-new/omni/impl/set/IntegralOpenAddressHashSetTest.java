package omni.impl.set;
import java.io.IOException;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.LongStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import omni.api.OmniIterator;
import omni.api.OmniSet;
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
import omni.util.OmniArray;
import omni.util.TestExecutorService;
@Tag(value="NewTest")
public class IntegralOpenAddressHashSetTest{
    private static final double[] RANDOM_THRESHOLDS=new double[]{0.01,0.05,0.10,0.25,0.50,0.75,0.90,0.95,0.99};
    private static final double[] NON_RANDOM_THRESHOLD=new double[]{0.5};
    private static final EnumSet<SetInitialization> VALID_INIT_SEQS=EnumSet.of(SetInitialization.Empty,
            SetInitialization.AddTrue,SetInitialization.AddFalse,
            SetInitialization.AddTrueAndFalse,SetInitialization.AddPrime,
            SetInitialization.AddFibSeq,SetInitialization.AddMinByte,
            SetInitialization.FillWord0,SetInitialization.FillWord1,SetInitialization.FillWord2,
            SetInitialization.FillWord3,SetInitialization.Add200RemoveThenAdd100More);
    private static final int[] CONSTRUCTOR_INITIAL_CAPACITIES=new int[5 + 29 * 3 + 2];
    private static final float[] LOAD_FACTORS=new float[]{0.1f,0.25f,0.5f,0.75f,0.9f,0.95f,1.0f,1.1f,2.0f,0.0f,-1f,
            -.75f,-.5f,Float.NaN};
    private static final int[] GENERAL_PURPOSE_INITIAL_CAPACITIES=new int[]{0,1,2,4,8,16,32,64,128,256,512,1024,2048,
            4096,8192};
    static{
        CONSTRUCTOR_INITIAL_CAPACITIES[0]=0;
        CONSTRUCTOR_INITIAL_CAPACITIES[1]=1;
        CONSTRUCTOR_INITIAL_CAPACITIES[2]=2;
        int i=2;
        for(int pow=2;pow <= 30;++pow){
            CONSTRUCTOR_INITIAL_CAPACITIES[++i]=(1 << pow) - 1;
            CONSTRUCTOR_INITIAL_CAPACITIES[++i]=1 << pow;
            CONSTRUCTOR_INITIAL_CAPACITIES[++i]=(1 << pow) + 1;
        }
        CONSTRUCTOR_INITIAL_CAPACITIES[++i]=OmniArray.MAX_ARR_SIZE;
        CONSTRUCTOR_INITIAL_CAPACITIES[++i]=Integer.MAX_VALUE;
        CONSTRUCTOR_INITIAL_CAPACITIES[++i]=-1;
        CONSTRUCTOR_INITIAL_CAPACITIES[++i]=Integer.MIN_VALUE;
    }
    @Disabled
    @org.junit.jupiter.api.Test
    public void testadd_val(){
        for(final var collectionType:StructType.IntegralOpenAddressHashSet.validDataTypes){
            for(final var inputType:collectionType.mayBeAddedTo()){
                for(final var checkedType:CheckedType.values()){
                    for(final float loadFactor:LOAD_FACTORS){
                        if(loadFactor > 0.f && loadFactor <= 1.0f && loadFactor == loadFactor){
                            for(final int initialCapacity:GENERAL_PURPOSE_INITIAL_CAPACITIES){
                                for(final var functionCallType:FunctionCallType.values()){
                                    TestExecutorService.submitTest(()->{
                                        final var monitor=new IntegralOpenAddressHashSetMonitor(collectionType,
                                                checkedType,initialCapacity,loadFactor);
                                        if(collectionType != DataType.CHAR){
                                            for(int curr=-128;curr < 128;++curr){
                                                final Object inputVal=inputType.convertValUnchecked(curr);
                                                monitor.verifyAdd(inputType,functionCallType,inputVal);
                                            }
                                        }
                                        long pPrev=0;
                                        long prev=1;
                                        for(int i=0;i < 1000;++i){
                                            final long curr=pPrev + prev;
                                            final Object inputVal=inputType.convertValUnchecked(curr);
                                            monitor.verifyAdd(inputType,functionCallType,inputVal);
                                            pPrev=prev;
                                            prev=curr;
                                        }
                                    });
                                }
                            }
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("IntegralOpenAddressHashSetTest.testadd_val");
    }
    @org.junit.jupiter.api.Test
    public void testclear_void(){
        final BasicTest test=(loadFactor,initCapacity,collectionType,checkedType,initSet)->{
            initSet.initialize(
                    new IntegralOpenAddressHashSetMonitor(collectionType,checkedType,initCapacity,loadFactor))
            .verifyClear();
        };
        test.runAllTests("IntegralOpenAddressHashSetTest.testclear_void");
    }
    @org.junit.jupiter.api.Test
    public void testclone_void(){
        final BasicTest test=(loadFactor,initCapacity,collectionType,checkedType,initSet)->{
            initSet.initialize(
                    new IntegralOpenAddressHashSetMonitor(collectionType,checkedType,initCapacity,loadFactor))
            .verifyClone();
        };
        test.runAllTests("IntegralOpenAddressHashSetTest.testclone_void");
    }
    @org.junit.jupiter.api.Test
    public void testConstructor_float(){
        for(final var collectionType:StructType.IntegralOpenAddressHashSet.validDataTypes){
            for(final var checkedType:CheckedType.values()){
                for(final float loadFactor:LOAD_FACTORS){
                    if(checkedType.checked || loadFactor == loadFactor && loadFactor <= 1.0f && loadFactor > 0){
                        TestExecutorService.submitTest(()->{
                            if(loadFactor == loadFactor && loadFactor <= 1.0f && loadFactor > 0){
                                new IntegralOpenAddressHashSetMonitor(collectionType,checkedType,loadFactor)
                                .verifyCollectionState();
                            }else{
                                Assertions.assertThrows(IllegalArgumentException.class,
                                        ()->new IntegralOpenAddressHashSetMonitor(collectionType,checkedType,
                                                loadFactor));
                            }
                        });
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("IntegralOpenAddressHashSetTest.testConstructor_float");
    }
    @org.junit.jupiter.api.Test
    public void testConstructor_int(){
        for(final var collectionType:StructType.IntegralOpenAddressHashSet.validDataTypes){
            for(final var checkedType:CheckedType.values()){
                for(final int initialCapacity:CONSTRUCTOR_INITIAL_CAPACITIES){
                    if(checkedType.checked || initialCapacity >= 0){
                        TestExecutorService.submitTest(()->{
                            if(initialCapacity >= 0){
                                new IntegralOpenAddressHashSetMonitor(collectionType,checkedType,initialCapacity)
                                .verifyCollectionState();
                            }else{
                                Assertions.assertThrows(IllegalArgumentException.class,
                                        ()->new IntegralOpenAddressHashSetMonitor(collectionType,checkedType,
                                                initialCapacity));
                            }
                        });
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("IntegralOpenAddressHashSetTest.testConstructor_int");
    }
    @org.junit.jupiter.api.Test
    public void testConstructor_intfloat(){
        for(final var collectionType:StructType.IntegralOpenAddressHashSet.validDataTypes){
            for(final var checkedType:CheckedType.values()){
                for(final int initialCapacity:CONSTRUCTOR_INITIAL_CAPACITIES){
                    if(checkedType.checked || initialCapacity >= 0){
                        for(final float loadFactor:LOAD_FACTORS){
                            if(checkedType.checked || loadFactor == loadFactor && loadFactor <= 1.0f && loadFactor > 0){
                                TestExecutorService.submitTest(()->{
                                    if(initialCapacity >= 0 && loadFactor == loadFactor && loadFactor <= 1.0f
                                            && loadFactor > 0){
                                        new IntegralOpenAddressHashSetMonitor(collectionType,checkedType,
                                                initialCapacity,loadFactor).verifyCollectionState();
                                    }else{
                                        Assertions.assertThrows(IllegalArgumentException.class,
                                                ()->new IntegralOpenAddressHashSetMonitor(collectionType,checkedType,
                                                        initialCapacity,loadFactor));
                                    }
                                });
                            }
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("IntegralOpenAddressHashSetTest.testConstructor_intfloat");
    }
    @org.junit.jupiter.api.Test
    public void testConstructor_void(){
        for(final var collectionType:StructType.IntegralOpenAddressHashSet.validDataTypes){
            for(final var checkedType:CheckedType.values()){
                TestExecutorService.submitTest(
                        ()->new IntegralOpenAddressHashSetMonitor(collectionType,checkedType).verifyCollectionState());
            }
        }
        TestExecutorService.completeAllTests("IntegralOpenAddressHashSetTest.testConstructor_void");
    }
    @Disabled
    @org.junit.jupiter.api.Test
    public void testcontains_val(){
        final QueryTest test=(monitor,queryVal,inputType,castType,modification)->monitor.verifyContains(queryVal,
                inputType,castType,modification);
        test.runAllTests("IntegralOpenAddressHashSetTest.testcontains_val");
    }
    @org.junit.jupiter.api.Test
    public void testforEach_Consumer(){
        for(final var collectionType:StructType.IntegralOpenAddressHashSet.validDataTypes){
            for(final var functionGen:StructType.IntegralOpenAddressHashSet.validMonitoredFunctionGens){
                for(final var initSet:VALID_INIT_SEQS){
                    final int size=initSet
                            .initialize(new IntegralOpenAddressHashSetMonitor(collectionType,CheckedType.UNCHECKED))
                            .size();
                    for(final var functionCallType:FunctionCallType.values()){
                        final long randSeedBound=functionGen.randomized && size > 1 && !functionCallType.boxed?100:0;
                        for(final var checkedType:CheckedType.values()){
                            if(checkedType.checked || functionGen.expectedException == null || size == 0){
                                LongStream.rangeClosed(0,randSeedBound)
                                .forEach(randSeed->TestExecutorService
                                        .submitTest(()->{
                                            final var monitor=initSet.initialize(
                                                    new IntegralOpenAddressHashSetMonitor(collectionType,
                                                            checkedType));
                                            if(functionGen.expectedException == null || monitor.size() == 0){
                                                monitor.verifyForEach(functionGen,functionCallType,randSeed);
                                            }else{
                                                Assertions.assertThrows(functionGen.expectedException,
                                                        ()->monitor.verifyForEach(functionGen,functionCallType,
                                                                randSeed));
                                                monitor.verifyCollectionState();
                                            }
                                        }));
                            }
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("IntegralOpenAddressHashSetTest.testforEach_Consumer");
    }
    @org.junit.jupiter.api.Test
    public void testhashCode_void(){
        final BasicTest test=(loadFactor,initCapacity,collectionType,checkedType,initSet)->{
            initSet.initialize(
                    new IntegralOpenAddressHashSetMonitor(collectionType,checkedType,initCapacity,loadFactor))
            .verifyHashCode();
        };
        test.runAllTests("IntegralOpenAddressHashSetTest.testhashCode_void");
    }
    @org.junit.jupiter.api.Test
    public void testisEmpty_void(){
        final BasicTest test=(loadFactor,initCapacity,collectionType,checkedType,initSet)->{
            initSet.initialize(
                    new IntegralOpenAddressHashSetMonitor(collectionType,checkedType,initCapacity,loadFactor))
            .verifyIsEmpty();
        };
        test.runAllTests("IntegralOpenAddressHashSetTest.testisEmpty_void");
    }
    @org.junit.jupiter.api.Test
    public void testiterator_void(){
        final BasicTest test=(loadFactor,initCapacity,collectionType,checkedType,initSet)->{
            initSet.initialize(
                    new IntegralOpenAddressHashSetMonitor(collectionType,checkedType,initCapacity,loadFactor))
            .getMonitoredIterator().verifyIteratorState();
        };
        test.runAllTests("IntegralOpenAddressHashSetTest.testiterator_void");
    }
    @org.junit.jupiter.api.Test
    public void testItrclone_void(){
        final BasicTest test=(loadFactor,initCapacity,collectionType,checkedType,initSet)->initSet
                .initialize(new IntegralOpenAddressHashSetMonitor(collectionType,checkedType,initCapacity,loadFactor))
                .getMonitoredIterator().verifyClone();
        test.runAllTests("IntegralOpenAddressHashSetTest.testItrclone_void");
    }
    @Disabled
    @org.junit.jupiter.api.Test
    public void testItrforEachRemaining_Consumer(){
        for(final var collectionType:StructType.IntegralOpenAddressHashSet.validDataTypes){
            for(final var checkedType:CheckedType.values()){
                for(final var initSet:VALID_INIT_SEQS){
                    int sizeScenario;
                    switch(initSet){
                    case Empty:
                        sizeScenario=0;
                        break;
                    case AddPrime:
                    case AddTrue:
                    case AddFalse:
                    case AddMinByte:
                        sizeScenario=1;
                        break;
                    case AddTrueAndFalse:
                    case FillWord0:
                    case FillWord1:
                    case FillWord2:
                    case FillWord3:
                        sizeScenario=2;
                        break;
                    case AddFibSeq:
                    case Add200RemoveThenAdd100More:
                        sizeScenario=3;
                        break;
                    default:
                        throw initSet.invalid();
                    }
                    for(final var functionGen:IteratorType.AscendingItr.validMonitoredFunctionGens){
                        if(checkedType.checked || functionGen.expectedException == null || sizeScenario == 0){
                            for(final var preMod:IteratorType.AscendingItr.validPreMods){
                                if(checkedType.checked || preMod.expectedException == null || sizeScenario == 0){
                                    int itrScenarioMax=0;
                                    if(sizeScenario > 1){
                                        if(sizeScenario > 2){
                                            itrScenarioMax=3;
                                        }else{
                                            itrScenarioMax=2;
                                        }
                                    }
                                    for(int tmpItrScenario=0;tmpItrScenario<=itrScenarioMax;++tmpItrScenario) {
                                      final int itrScenario=tmpItrScenario;
                                      for(long tmpRandSeed=0,randBound=preMod.expectedException == null && functionGen.randomized
                                                && sizeScenario > 1 && itrScenario == 0?100:0;tmpRandSeed<=randBound;++tmpRandSeed) {
                                        final long randSeed=tmpRandSeed;
                                            for(final var functionCallType:FunctionCallType.values()){
                                                for(final var loadFactor:LOAD_FACTORS){
                                                    if(loadFactor > 0.f && loadFactor <= 1.0f
                                                            && loadFactor == loadFactor){
                                                        for(final var initCapacity:GENERAL_PURPOSE_INITIAL_CAPACITIES){
                                                            TestExecutorService.submitTest(()->{
                                                                final var setMonitor=initSet.initialize(
                                                                        new IntegralOpenAddressHashSetMonitor(
                                                                                collectionType,checkedType,
                                                                                initCapacity,loadFactor));
                                                                final var itrMonitor=setMonitor
                                                                        .getMonitoredIterator();
                                                                switch(itrScenario){
                                                                case 1:
                                                                    itrMonitor.iterateForward();
                                                                    break;
                                                                case 2:
                                                                    itrMonitor.iterateForward();
                                                                    itrMonitor.remove();
                                                                    break;
                                                                case 3:
                                                                    for(int i=0;i < 13
                                                                            && itrMonitor.hasNext();++i){
                                                                        itrMonitor.iterateForward();
                                                                    }
                                                                    break;
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
                                                                    itrMonitor.verifyForEachRemaining(
                                                                            functionGen,functionCallType,
                                                                            randSeed);
                                                                }else{
                                                                    Assertions.assertThrows(expectedException,
                                                                            ()->itrMonitor
                                                                            .verifyForEachRemaining(
                                                                                    functionGen,
                                                                                    functionCallType,
                                                                                    randSeed));
                                                                    itrMonitor.verifyIteratorState();
                                                                    setMonitor.verifyCollectionState();
                                                                }
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
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("IntegralOpenAddressHashSetTest.testItrforEachRemaining_Consumer");
    }
    @org.junit.jupiter.api.Test
    public void testItrhasNext_void(){
        final BasicTest test=(loadFactor,initCapacity,collectionType,checkedType,initSet)->{
            final var setMonitor=initSet.initialize(
                    new IntegralOpenAddressHashSetMonitor(collectionType,checkedType,initCapacity,loadFactor));
            final var itrMonitor=setMonitor.getMonitoredIterator();
            final var setSize=setMonitor.size();
            for(int i=0;i < setSize;++i){
                Assertions.assertTrue(itrMonitor.verifyHasNext());
                itrMonitor.iterateForward();
            }
            Assertions.assertFalse(itrMonitor.verifyHasNext());
        };
        test.runAllTests("IntegralOpenAddressHashSetTest.testItrhasNext_void");
    }
    @Disabled
    @org.junit.jupiter.api.Test
    public void testItrnext_void(){
        for(final var collectionType:StructType.IntegralOpenAddressHashSet.validDataTypes){
            for(final var outputType:collectionType.validOutputTypes()){
                for(final var checkedType:CheckedType.values()){
                    for(final var preMod:IteratorType.AscendingItr.validPreMods){
                        if(checkedType.checked || preMod.expectedException == null){
                            for(final var loadFactor:LOAD_FACTORS){
                                if(loadFactor > 0.f && loadFactor <= 1.0f && loadFactor == loadFactor){
                                    for(final var initialCapacity:GENERAL_PURPOSE_INITIAL_CAPACITIES){
                                        for(final var initSet:VALID_INIT_SEQS){
                                            TestExecutorService.submitTest(()->{
                                                final var setMonitor=initSet.initialize(
                                                        new IntegralOpenAddressHashSetMonitor(collectionType,
                                                                checkedType,initialCapacity,loadFactor));
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
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("IntegralOpenAddressHashSetTest.testItrnext_void");
    }
    @Disabled
    @org.junit.jupiter.api.Test
    public void testItrremove_void(){
        for(final var collectionType:StructType.IntegralOpenAddressHashSet.validDataTypes){
            for(final var checkedType:CheckedType.values()){
                for(final var initSet:VALID_INIT_SEQS){
                    for(final var itrRemoveScenario:IteratorType.AscendingItr.validItrRemoveScenarios){
                        if((!initSet.isEmpty || itrRemoveScenario == IteratorRemoveScenario.PostInit)
                                && (checkedType.checked || itrRemoveScenario.expectedException == null)){
                            for(final var preMod:IteratorType.AscendingItr.validPreMods){
                                if(checkedType.checked || preMod.expectedException == null){
                                    final var monitor=initSet.initialize(
                                            new IntegralOpenAddressHashSetMonitor(collectionType,checkedType));
                                    final int setSize=monitor.size();
                                    int itrOffset,itrBound;
                                    if(itrRemoveScenario == IteratorRemoveScenario.PostInit){
                                        itrOffset=itrBound=0;
                                    }else{
                                        itrOffset=1;
                                        itrBound=setSize;
                                    }
                                    for(int tmpItrCount=itrOffset;tmpItrCount<=itrBound;++tmpItrCount) {
                                      final int itrCount=tmpItrCount;
                                        for(final float loadFactor:LOAD_FACTORS){
                                            if(loadFactor > 0.f && loadFactor <= 1.0f && loadFactor == loadFactor){
                                                for(final int initCapacity:GENERAL_PURPOSE_INITIAL_CAPACITIES){
                                                    TestExecutorService.submitTest(()->{
                                                        var setMonitor=initSet.initialize(new IntegralOpenAddressHashSetMonitor(
                                                                collectionType,checkedType,initCapacity,loadFactor));
                                                        final var itrMonitor=setMonitor.getMonitoredIterator();
                                                        for(int i=0;i < itrCount && itrMonitor.hasNext();++i){
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
            }
        }
        TestExecutorService.completeAllTests("IntegralOpenAddressHashSetTest.testItrremove_void");
    }
    @Disabled
    @org.junit.jupiter.api.Test
    public void testMASSIVEtoString(){
        for(final var collectionType:new DataType[]{DataType.INT,DataType.LONG}){
            final int numToAdd=collectionType.massiveToStringThreshold + 1;
            OmniSet.IntInput<?> set;
            if(collectionType == DataType.INT){
                set=new IntOpenAddressHashSet(numToAdd + 1);
            }else{
                set=new LongOpenAddressHashSet(numToAdd + 1);
            }
            set.add(0);
            int pPrev;
            int prev;
            set.add(pPrev=1);
            set.add(prev=2);
            int numAdded=3;
            for(;;){
                final int curr=pPrev + prev;
                if(curr >= 128){
                    break;
                }
                set.add(curr);
                ++numAdded;
                pPrev=prev;
                prev=curr;
            }
            int curr=128;
            for(;;){
                set.add(curr);
                if(++numAdded == numToAdd){
                    break;
                }
                ++curr;
            }
            collectionType.verifyToString(set.toString(),set,
                    "IntegralOpenAddressHashSetTest." + collectionType + ".testMASSIVEtoString");
        }
    }
    @org.junit.jupiter.api.Test
    public void testReadAndWrite(){
        final MonitoredFunctionGenTest test=(collectionType,functionGen,checkedType,initSet)->{
            var monitor=initSet.initialize(new IntegralOpenAddressHashSetMonitor(collectionType,checkedType));
            if(functionGen.expectedException==null) {
                Assertions.assertDoesNotThrow(()->monitor.verifyReadAndWrite(functionGen));
            }else {
                Assertions.assertThrows(functionGen.expectedException,()->monitor.verifyReadAndWrite(functionGen));
            }   
        };
        test.runAllTests("IntegralOpenAddressHashSetTest.testReadAndWrite");
    }
    @Disabled
    @org.junit.jupiter.api.Test
    public void testremoveIf_Predicate(){
        for(final var collectionType:StructType.IntegralOpenAddressHashSet.validDataTypes){
            for(final var initSet:VALID_INIT_SEQS){
                final int setSize=initSet
                        .initialize(new IntegralOpenAddressHashSetMonitor(collectionType,CheckedType.UNCHECKED)).size();
                for(final var filterGen:StructType.IntegralOpenAddressHashSet.validMonitoredRemoveIfPredicateGens){
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
                                LongStream.rangeClosed(0,randSeedBound).forEach(randSeed->DoubleStream.of(thresholdArr)
                                        .forEach(threshold->TestExecutorService
                                                .submitTest(()->{
                                                    final var monitor=initSet.initialize(
                                                            new IntegralOpenAddressHashSetMonitor(collectionType,
                                                                    checkedType));
                                                    final long[] expectedWords=Arrays.copyOf(monitor.expectedWords,4);
                                                    final var filter=filterGen.getMonitoredRemoveIfPredicate(monitor,
                                                            threshold,new Random(randSeed));
                                                    final int sizeBefore=monitor.size();
                                                    if(filterGen.expectedException == null || sizeBefore == 0){
                                                        final boolean result=monitor.verifyRemoveIf(filter,
                                                                functionCallType);
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
                                                                Assertions.assertEquals(
                                                                        (expectedWords[collectionType == DataType.CHAR?0
                                                                                :2] & 0b01) != 0,
                                                                                result);
                                                                break;
                                                            case RemoveNone:
                                                                Assertions.assertFalse(result);
                                                                Assertions.assertFalse(monitor.set.isEmpty());
                                                                break;
                                                            case RemoveTrue:
                                                                Assertions.assertFalse(monitor.set.contains(true));
                                                                Assertions.assertEquals(
                                                                        (expectedWords[collectionType == DataType.CHAR?0
                                                                                :2] & 0b10) != 0,
                                                                                result);
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
                                                })));
                            }
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("IntegralOpenAddressHashSetTest.testremoveIf_Predicate");
    }
    @org.junit.jupiter.api.Test
    public void testremoveVal_val(){
        final QueryTest test=(monitor,queryVal,inputType,castType,modification)->monitor.verifyRemoveVal(queryVal,
                inputType,castType,modification);
        test.runAllTests("IntegralOpenAddressHashSetTest.testremoveVal_val");
    }
    @org.junit.jupiter.api.Test
    public void testsetLoadFactor_float(){
        for(final var collectionType:StructType.IntegralOpenAddressHashSet.validDataTypes){
            for(final var checkedType:CheckedType.values()){
                for(final var initSet:VALID_INIT_SEQS){
                    for(final float loadFactor:LOAD_FACTORS){
                        if(checkedType.checked || loadFactor == loadFactor && loadFactor <= 1.0f && loadFactor > 0){
                            TestExecutorService.submitTest(()->initSet
                                    .initialize(new IntegralOpenAddressHashSetMonitor(collectionType,checkedType))
                                    .verifySetLoadFactor(loadFactor));
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("IntegralOpenAddressHashSetTest.testsetLoadFactor_float");
    }
    @org.junit.jupiter.api.Test
    public void testsize_void(){
        final BasicTest test=(loadFactor,initCapacity,collectionType,checkedType,initSet)->{
            initSet.initialize(
                    new IntegralOpenAddressHashSetMonitor(collectionType,checkedType,initCapacity,loadFactor))
            .verifySize();
        };
        test.runAllTests("IntegralOpenAddressHashSetTest.testsize_void");
    }
    @org.junit.jupiter.api.Test
    public void testtoArray_IntFunction(){
        final MonitoredFunctionGenTest test=(collectionType,functionGen,checkedType,initSet)->{
            final var monitor=initSet.initialize(new IntegralOpenAddressHashSetMonitor(collectionType,checkedType));
            if(functionGen.expectedException == null){
                monitor.verifyToArray(functionGen);
            }else{
                Assertions.assertThrows(functionGen.expectedException,()->monitor.verifyToArray(functionGen));
                monitor.verifyCollectionState();
            }
        };
        test.runAllTests("IntegralOpenAddressHashSetTest.testToArray_IntFunction");
    }
    @org.junit.jupiter.api.Test
    public void testtoArray_ObjectArray(){
        for(final var collectionType:StructType.IntegralOpenAddressHashSet.validDataTypes){
            for(final var initSet:VALID_INIT_SEQS){
                final int size=initSet
                        .initialize(new IntegralOpenAddressHashSetMonitor(collectionType,CheckedType.UNCHECKED)).size();
                for(final var checkedType:CheckedType.values()){
                    for(int arrSize=0,increment=Math.max(1,size / 10),bound=size + increment
                            + 2;arrSize <= bound;arrSize+=increment){
                        final Object[] paramArr=new Object[arrSize];
                        TestExecutorService.submitTest(()->initSet
                                .initialize(new IntegralOpenAddressHashSetMonitor(collectionType,checkedType))
                                .verifyToArray(paramArr));
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("IntegralOpenAddressHashSetTest.testtoArray_ObjectArray");
    }
    @org.junit.jupiter.api.Test
    public void testtoArray_void(){
        for(final var collectionType:StructType.IntegralOpenAddressHashSet.validDataTypes){
            for(final var outputType:collectionType.validOutputTypes()){
                for(final var checkedType:CheckedType.values()){
                    for(final var initSet:VALID_INIT_SEQS){
                        TestExecutorService.submitTest(()->{
                            outputType.verifyToArray(initSet
                                    .initialize(new IntegralOpenAddressHashSetMonitor(collectionType,checkedType)));
                        });
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("IntegralOpenAddressHashSetTest.testtoArray_void");
    }
    @org.junit.jupiter.api.Test
    public void testtoString_void(){
        final BasicTest test=(loadFactor,initCapacity,collectionType,checkedType,initSet)->{
            initSet.initialize(
                    new IntegralOpenAddressHashSetMonitor(collectionType,checkedType,initCapacity,loadFactor))
            .verifyToString();
        };
        test.runAllTests("IntegralOpenAddressHashSetTest.testtoString_void");
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
        void runTest(float loadFactor,int initCapacity,DataType collectionType,CheckedType checkedType,
                SetInitialization initSet);
        private void runAllTests(String testName){
            for(final var collectionType:StructType.IntegralOpenAddressHashSet.validDataTypes){
                for(final var checkedType:CheckedType.values()){
                    for(final var initSet:VALID_INIT_SEQS){
                        for(final var loadFactor:LOAD_FACTORS){
                            if(loadFactor > 0.f && loadFactor <= 1.0f && loadFactor == loadFactor){
                                for(final var initCapacity:GENERAL_PURPOSE_INITIAL_CAPACITIES){
                                    TestExecutorService.submitTest(
                                            ()->runTest(loadFactor,initCapacity,collectionType,checkedType,initSet));
                                }
                            }
                        }
                    }
                }
            }
            TestExecutorService.completeAllTests(testName);
        }
    }
    private static class IntegralOpenAddressHashSetMonitor
    implements
    MonitoredSet<AbstractIntegralTypeOpenAddressHashSet<?>>{
        @Override
        public Object get(int iterationIndex,DataType outputType) {
            switch(dataType) {
            case CHAR:{
                var itr=(OmniIterator.OfChar)set.iterator();
                while(iterationIndex>0) {
                    itr.nextChar();
                }
                return outputType.convertVal(itr.nextChar());
            }
            case SHORT:{
                var itr=(OmniIterator.OfShort)set.iterator();
                while(iterationIndex>0) {
                    itr.nextShort();
                }
                return outputType.convertVal(itr.nextShort());
            }
            case INT:{
                var itr=(OmniIterator.OfInt)set.iterator();
                while(iterationIndex>0) {
                    itr.nextInt();
                }
                return outputType.convertVal(itr.nextInt());
            }
            case LONG:{
                var itr=(OmniIterator.OfLong)set.iterator();
                while(iterationIndex>0) {
                    itr.nextLong();
                }
                return outputType.convertVal(itr.nextLong());
            }
            default:
                throw dataType.invalid();
            }
            
        }
        private static void quickInsert(char[] table,char val){
            int tableLength;
            int hash;
            for(hash=val & (tableLength=table.length - 1);;){
                if(table[hash] == 0){
                    table[hash]=val;
                    return;
                }
                hash=hash + 1 & tableLength;
            }
        }
        private static void quickInsert(int[] table,int val){
            int tableLength;
            int hash;
            for(hash=val & (tableLength=table.length - 1);;){
                if(table[hash] == 0){
                    table[hash]=val;
                    return;
                }
                hash=hash + 1 & tableLength;
            }
        }
        private static void quickInsert(long[] table,long val){
            int tableLength;
            int hash;
            for(hash=(int)(val ^ val >>> 32) & (tableLength=table.length - 1);;){
                if(table[hash] == 0){
                    table[hash]=val;
                    return;
                }
                hash=hash + 1 & tableLength;
            }
        }
        private static void quickInsert(short[] table,short val){
            int tableLength;
            int hash;
            for(hash=val & (tableLength=table.length - 1);;){
                if(table[hash] == 0){
                    table[hash]=val;
                    return;
                }
                hash=hash + 1 & tableLength;
            }
        }
        final AbstractIntegralTypeOpenAddressHashSet<?> set;
        final CheckedType checkedType;
        final DataType dataType;
        final long[] expectedWords;
        int expectedSize;
        int expectedMaxTableSize;
        float expectedLoadFactor;
        int expectedTableSize;
        int expectedModCount;
        int expectedTableLength;
        Object expectedTable;
        IntegralOpenAddressHashSetMonitor(DataType dataType,CheckedType checkedType){
            switch(dataType){
            case CHAR:
                if(checkedType.checked){
                    set=new CharOpenAddressHashSet.Checked();
                }else{
                    set=new CharOpenAddressHashSet();
                }
                break;
            case SHORT:
                if(checkedType.checked){
                    set=new ShortOpenAddressHashSet.Checked();
                }else{
                    set=new ShortOpenAddressHashSet();
                }
                break;
            case INT:
                if(checkedType.checked){
                    set=new IntOpenAddressHashSet.Checked();
                }else{
                    set=new IntOpenAddressHashSet();
                }
                break;
            case LONG:
                if(checkedType.checked){
                    set=new LongOpenAddressHashSet.Checked();
                }else{
                    set=new LongOpenAddressHashSet();
                }
                break;
            default:
                throw dataType.invalid();
            }
            this.checkedType=checkedType;
            this.dataType=dataType;
            expectedWords=new long[4];
            updateCollectionState();
        }
        IntegralOpenAddressHashSetMonitor(DataType dataType,CheckedType checkedType,float loadFactor){
            switch(dataType){
            case CHAR:
                if(checkedType.checked){
                    set=new CharOpenAddressHashSet.Checked(loadFactor);
                }else{
                    set=new CharOpenAddressHashSet(loadFactor);
                }
                break;
            case SHORT:
                if(checkedType.checked){
                    set=new ShortOpenAddressHashSet.Checked(loadFactor);
                }else{
                    set=new ShortOpenAddressHashSet(loadFactor);
                }
                break;
            case INT:
                if(checkedType.checked){
                    set=new IntOpenAddressHashSet.Checked(loadFactor);
                }else{
                    set=new IntOpenAddressHashSet(loadFactor);
                }
                break;
            case LONG:
                if(checkedType.checked){
                    set=new LongOpenAddressHashSet.Checked(loadFactor);
                }else{
                    set=new LongOpenAddressHashSet(loadFactor);
                }
                break;
            default:
                throw dataType.invalid();
            }
            this.checkedType=checkedType;
            this.dataType=dataType;
            expectedWords=new long[4];
            updateCollectionState();
        }
        IntegralOpenAddressHashSetMonitor(DataType dataType,CheckedType checkedType,int initialCapacity){
            switch(dataType){
            case CHAR:
                if(checkedType.checked){
                    set=new CharOpenAddressHashSet.Checked(initialCapacity);
                }else{
                    set=new CharOpenAddressHashSet(initialCapacity);
                }
                break;
            case SHORT:
                if(checkedType.checked){
                    set=new ShortOpenAddressHashSet.Checked(initialCapacity);
                }else{
                    set=new ShortOpenAddressHashSet(initialCapacity);
                }
                break;
            case INT:
                if(checkedType.checked){
                    set=new IntOpenAddressHashSet.Checked(initialCapacity);
                }else{
                    set=new IntOpenAddressHashSet(initialCapacity);
                }
                break;
            case LONG:
                if(checkedType.checked){
                    set=new LongOpenAddressHashSet.Checked(initialCapacity);
                }else{
                    set=new LongOpenAddressHashSet(initialCapacity);
                }
                break;
            default:
                throw dataType.invalid();
            }
            this.checkedType=checkedType;
            this.dataType=dataType;
            expectedWords=new long[4];
            updateCollectionState();
        }
        IntegralOpenAddressHashSetMonitor(DataType dataType,CheckedType checkedType,int initialCapacity,
                float loadFactor){
            switch(dataType){
            case CHAR:
                if(checkedType.checked){
                    set=new CharOpenAddressHashSet.Checked(initialCapacity,loadFactor);
                }else{
                    set=new CharOpenAddressHashSet(initialCapacity,loadFactor);
                }
                break;
            case SHORT:
                if(checkedType.checked){
                    set=new ShortOpenAddressHashSet.Checked(initialCapacity,loadFactor);
                }else{
                    set=new ShortOpenAddressHashSet(initialCapacity,loadFactor);
                }
                break;
            case INT:
                if(checkedType.checked){
                    set=new IntOpenAddressHashSet.Checked(initialCapacity,loadFactor);
                }else{
                    set=new IntOpenAddressHashSet(initialCapacity,loadFactor);
                }
                break;
            case LONG:
                if(checkedType.checked){
                    set=new LongOpenAddressHashSet.Checked(initialCapacity,loadFactor);
                }else{
                    set=new LongOpenAddressHashSet(initialCapacity,loadFactor);
                }
                break;
            default:
                throw dataType.invalid();
            }
            this.checkedType=checkedType;
            this.dataType=dataType;
            expectedWords=new long[4];
            updateCollectionState();
        }
        @Override
        public CheckedType getCheckedType(){
            return checkedType;
        }
        @Override
        public AbstractIntegralTypeOpenAddressHashSet<?> getCollection(){
            return set;
        }
        @Override
        public DataType getDataType(){
            return dataType;
        }
        @Override
        public MonitoredIterator<? extends OmniIterator<?>,AbstractIntegralTypeOpenAddressHashSet<?>> getMonitoredIterator(){
            int expectedOffset;
            int numLeft;
            if((numLeft=expectedSize) != 0){
                outer:for(;;){
                    switch(dataType){
                    case CHAR:{
                        for(expectedOffset=0;expectedOffset < 256;++expectedOffset){
                            if((expectedWords[expectedOffset >> 6] & 1L << expectedOffset) != 0){
                                break outer;
                            }
                        }
                        final char[] table=(char[])expectedTable;
                        for(expectedOffset=0;;++expectedOffset){
                            if(table[expectedOffset] > 1){
                                expectedOffset+=256;
                                break outer;
                            }
                        }
                    }
                    case SHORT:{
                        for(expectedOffset=-128;expectedOffset < 128;++expectedOffset){
                            if((expectedWords[(expectedOffset >> 6) + 2] & 1L << expectedOffset) != 0){
                                expectedOffset+=128;
                                break outer;
                            }
                        }
                        final short[] table=(short[])expectedTable;
                        for(expectedOffset=0;;++expectedOffset){
                            if((table[expectedOffset] & -2) != 0){
                                expectedOffset+=256;
                                break outer;
                            }
                        }
                    }
                    case INT:{
                        for(expectedOffset=-128;expectedOffset < 128;++expectedOffset){
                            if((expectedWords[(expectedOffset >> 6) + 2] & 1L << expectedOffset) != 0){
                                expectedOffset+=128;
                                break outer;
                            }
                        }
                        final int[] table=(int[])expectedTable;
                        for(expectedOffset=0;;++expectedOffset){
                            if((table[expectedOffset] & -2) != 0){
                                expectedOffset+=256;
                                break outer;
                            }
                        }
                    }
                    case LONG:{
                        for(expectedOffset=-128;expectedOffset < 128;++expectedOffset){
                            if((expectedWords[(expectedOffset >> 6) + 2] & 1L << expectedOffset) != 0){
                                expectedOffset+=128;
                                break outer;
                            }
                        }
                        final long[] table=(long[])expectedTable;
                        for(expectedOffset=0;;++expectedOffset){
                            if((table[expectedOffset] & -2) != 0){
                                expectedOffset+=256;
                                break outer;
                            }
                        }
                    }
                    default:
                        throw dataType.invalid();
                    }
                }
            }else{
                expectedOffset=-1;
            }
            final var itr=set.iterator();
            if(checkedType.checked){
                return new CheckedItrMonitor(itr,expectedOffset,numLeft,expectedModCount);
            }
            return new UncheckedItrMonitor(itr,expectedOffset,numLeft);
        }
        @Override
        public StructType getStructType(){
            return StructType.IntegralOpenAddressHashSet;
        }
        @Override
        public void modCollection(){
            outer:for(;;){
                switch(dataType){
                case CHAR:{
                    final var cast=(CharOpenAddressHashSet)set;
                    for(int i=0;i <= Character.MAX_VALUE;++i){
                        if(cast.add((char)i)){
                            break outer;
                        }
                    }
                    break;
                }
                case SHORT:{
                    final var cast=(ShortOpenAddressHashSet)set;
                    for(int i=-128;i <= Short.MAX_VALUE;++i){
                        if(cast.add((short)i)){
                            break outer;
                        }
                    }
                    for(int i=Short.MIN_VALUE;i != -128;++i){
                        if(cast.add((short)i)){
                            break outer;
                        }
                    }
                    break;
                }
                case INT:
                case LONG:{
                    final var cast=(OmniSet.IntInput<?>)set;
                    for(int i=-128;;++i){
                        if(cast.add(i)){
                            break outer;
                        }
                    }
                }
                default:
                    throw dataType.invalid();
                }
                set.clear();
                break;
            }
        updateCollectionState();
        }
        @Override
        public void removeFromExpectedState(DataType inputType,QueryVal queryVal,QueryValModification modification){
            final Object inputVal=queryVal.getInputVal(dataType,modification);
            switch(dataType){
            case CHAR:{
                removeVal((char)inputVal);
                break;
            }
            case INT:{
                removeVal((int)inputVal);
                break;
            }
            case LONG:{
                removeVal((long)inputVal);
                break;
            }
            case SHORT:{
                removeVal((short)inputVal);
                break;
            }
            default:
                throw dataType.invalid();
            }
            ++expectedModCount;
        }
        @Override
        public int size(){
            return expectedSize;
        }
        @Override
        public void updateAddState(Object inputVal,DataType inputType,boolean result){
            if(result){
                switch(dataType){
                case CHAR:{
                    char v;
                    switch(inputType){
                    case BOOLEAN:
                        v=(boolean)inputVal?(char)1:(char)0;
                        break;
                    case CHAR:
                        v=(char)inputVal;
                        break;
                    default:
                        throw inputType.invalid();
                    }
                    if(v < 256){
                        expectedWords[v >> 6]|=1L << v;
                    }else{
                        insertInTable(v);
                    }
                    break;
                }
                case SHORT:{
                    short v;
                    switch(inputType){
                    case BOOLEAN:
                        v=(boolean)inputVal?(short)1:(short)0;
                        break;
                    case BYTE:
                        v=(byte)inputVal;
                        break;
                    case SHORT:
                        v=(short)inputVal;
                        break;
                    default:
                        throw inputType.invalid();
                    }
                    if(v <= Byte.MAX_VALUE && v >= Byte.MIN_VALUE){
                        expectedWords[(v >> 6) + 2]|=1L << v;
                    }else{
                        insertInTable(v);
                    }
                    break;
                }
                case INT:{
                    int v;
                    switch(inputType){
                    case BOOLEAN:
                        v=(boolean)inputVal?1:0;
                        break;
                    case BYTE:
                        v=(byte)inputVal;
                        break;
                    case SHORT:
                        v=(short)inputVal;
                        break;
                    case CHAR:
                        v=(char)inputVal;
                        break;
                    case INT:
                        v=(int)inputVal;
                        break;
                    default:
                        throw inputType.invalid();
                    }
                    if(v <= Byte.MAX_VALUE && v >= Byte.MIN_VALUE){
                        expectedWords[(v >> 6) + 2]|=1L << v;
                    }else{
                        insertInTable(v);
                    }
                    break;
                }
                case LONG:{
                    long v;
                    switch(inputType){
                    case BOOLEAN:
                        v=(boolean)inputVal?1L:0L;
                        break;
                    case BYTE:
                        v=(byte)inputVal;
                        break;
                    case SHORT:
                        v=(short)inputVal;
                        break;
                    case CHAR:
                        v=(char)inputVal;
                        break;
                    case INT:
                        v=(int)inputVal;
                        break;
                    case LONG:
                        v=(long)inputVal;
                        break;
                    default:
                        throw inputType.invalid();
                    }
                    if(v <= Byte.MAX_VALUE && v >= Byte.MIN_VALUE){
                        int i;
                        expectedWords[((i=(int)v) >> 6) + 2]|=1L << i;
                    }else{
                        insertInTable(v);
                    }
                    break;
                }
                default:
                    throw dataType.invalid();
                }
                ++expectedModCount;
                ++expectedSize;
            }
        }
        @Override public void updateClearState() {

            if(expectedTableSize != 0){
                switch(dataType){
                case CHAR:{
                    final var table=(char[])expectedTable;
                    for(int i=table.length;--i >= 0;){
                        table[i]=0;
                    }
                    break;
                }
                case SHORT:{
                    final var table=(short[])expectedTable;
                    for(int i=table.length;--i >= 0;){
                        table[i]=0;
                    }
                    break;
                }
                case INT:{
                    final var table=(int[])expectedTable;
                    for(int i=table.length;--i >= 0;){
                        table[i]=0;
                    }
                    break;
                }
                case LONG:{
                    final var table=(long[])expectedTable;
                    for(int i=table.length;--i >= 0;){
                        table[i]=0;
                    }
                    break;
                }
                default:
                    throw dataType.invalid();
                }
                expectedTableSize=0;
            }
            for(int i=0;i < 4;++i){
                expectedWords[i]=0;
            }
            expectedSize=0;
            ++expectedModCount;

        }
        @Override
        public void updateCollectionState(){
            AbstractIntegralTypeOpenAddressHashSet<?> set;
            expectedSize=(set=this.set).size;
            expectedLoadFactor=set.loadFactor;
            expectedMaxTableSize=set.maxTableSize;
            expectedTableSize=set.tableSize;
            long[] expectedWords;
            (expectedWords=this.expectedWords)[0]=set.word0;
            expectedWords[1]=set.word1;
            expectedWords[2]=set.word2;
            expectedWords[3]=set.word3;
            switch(dataType){
            case CHAR:{
                final var castSet=(CharOpenAddressHashSet)set;
                if(checkedType.checked){
                    expectedModCount=((CharOpenAddressHashSet.Checked)castSet).modCount;
                }
                char[] table;
                if((table=castSet.table) == null){
                    expectedTable=null;
                    expectedTableLength=0;
                }else{
                    int expectedTableLength,tableLength;
                    if((expectedTableLength=this.expectedTableLength) != (tableLength=table.length)){
                        Object newExpectedTable;
                        System.arraycopy(table,0,newExpectedTable=new char[tableLength],0,tableLength);
                        expectedTable=newExpectedTable;
                        this.expectedTableLength=tableLength;
                    }else{
                        System.arraycopy(table,0,expectedTable,0,expectedTableLength);
                    }
                }
                break;
            }
            case INT:{
                final var castSet=(IntOpenAddressHashSet)set;
                if(checkedType.checked){
                    expectedModCount=((IntOpenAddressHashSet.Checked)castSet).modCount;
                }
                int[] table;
                if((table=castSet.table) == null){
                    expectedTable=null;
                    expectedTableLength=0;
                }else{
                    int expectedTableLength,tableLength;
                    if((expectedTableLength=this.expectedTableLength) != (tableLength=table.length)){
                        Object newExpectedTable;
                        System.arraycopy(table,0,newExpectedTable=new int[tableLength],0,tableLength);
                        expectedTable=newExpectedTable;
                        this.expectedTableLength=tableLength;
                    }else{
                        System.arraycopy(table,0,expectedTable,0,expectedTableLength);
                    }
                }
                break;
            }
            case LONG:{
                final var castSet=(LongOpenAddressHashSet)set;
                if(checkedType.checked){
                    expectedModCount=((LongOpenAddressHashSet.Checked)castSet).modCount;
                }
                long[] table;
                if((table=castSet.table) == null){
                    expectedTable=null;
                    expectedTableLength=0;
                }else{
                    int expectedTableLength,tableLength;
                    if((expectedTableLength=this.expectedTableLength) != (tableLength=table.length)){
                        Object newExpectedTable;
                        System.arraycopy(table,0,newExpectedTable=new long[tableLength],0,tableLength);
                        expectedTable=newExpectedTable;
                        this.expectedTableLength=tableLength;
                    }else{
                        System.arraycopy(table,0,expectedTable,0,expectedTableLength);
                    }
                }
                break;
            }
            case SHORT:{
                final var castSet=(ShortOpenAddressHashSet)set;
                if(checkedType.checked){
                    expectedModCount=((ShortOpenAddressHashSet.Checked)castSet).modCount;
                }
                short[] table;
                if((table=castSet.table) == null){
                    expectedTable=null;
                    expectedTableLength=0;
                }else{
                    int expectedTableLength,tableLength;
                    if((expectedTableLength=this.expectedTableLength) != (tableLength=table.length)){
                        Object newExpectedTable;
                        System.arraycopy(table,0,newExpectedTable=new short[tableLength],0,tableLength);
                        expectedTable=newExpectedTable;
                        this.expectedTableLength=tableLength;
                    }else{
                        System.arraycopy(table,0,expectedTable,0,expectedTableLength);
                    }
                }
                break;
            }
            default:
                throw dataType.invalid();
            }
        }
        @Override
        public void verifyArrayIsCopy(Object arr,boolean emptyArrayMayBeSame){
            switch(dataType){
            case CHAR:
                Assertions.assertNotSame(((CharOpenAddressHashSet)set).table,arr);
                break;
            case SHORT:
                Assertions.assertNotSame(((ShortOpenAddressHashSet)set).table,arr);
                break;
            case INT:
                Assertions.assertNotSame(((IntOpenAddressHashSet)set).table,arr);
                break;
            case LONG:
                Assertions.assertNotSame(((LongOpenAddressHashSet)set).table,arr);
                break;
            default:
                throw dataType.invalid();
            }
        }
        @Override
        public void verifyClone(Object clone){
            AbstractIntegralTypeOpenAddressHashSet<?> cloneSet;
            Assertions.assertEquals(expectedSize,(cloneSet=(AbstractIntegralTypeOpenAddressHashSet<?>)clone).size);
            Assertions.assertEquals(expectedTableSize,cloneSet.tableSize);
            Assertions.assertEquals(expectedLoadFactor,cloneSet.loadFactor);
            Assertions.assertEquals(expectedWords[0],cloneSet.word0);
            Assertions.assertEquals(expectedWords[1],cloneSet.word1);
            Assertions.assertEquals(expectedWords[2],cloneSet.word2);
            Assertions.assertEquals(expectedWords[3],cloneSet.word3);
            int expectedTableLength=this.expectedTableLength;
            switch(dataType){
            case CHAR:
                if(checkedType.checked){
                    Assertions.assertEquals(0,((CharOpenAddressHashSet.Checked)cloneSet).modCount);
                }
                if(expectedTableLength == 0){
                    Assertions.assertNull(((CharOpenAddressHashSet)cloneSet).table);
                }else{
                    final var expectedTable=(char[])this.expectedTable;
                    while(--expectedTableLength >= 0){
                        char v;
                        switch(v=expectedTable[expectedTableLength]){
                        default:
                            Assertions.assertTrue(cloneSet.contains(v));
                        case 0:
                        case 1:
                        }
                    }
                }
                break;
            case INT:
                if(checkedType.checked){
                    Assertions.assertEquals(0,((IntOpenAddressHashSet.Checked)cloneSet).modCount);
                }
                if(expectedTableLength == 0){
                    Assertions.assertNull(((IntOpenAddressHashSet)cloneSet).table);
                }else{
                    final var expectedTable=(int[])this.expectedTable;
                    while(--expectedTableLength >= 0){
                        int v;
                        switch(v=expectedTable[expectedTableLength]){
                        default:
                            Assertions.assertTrue(cloneSet.contains(v));
                        case 0:
                        case 1:
                        }
                    }
                }
                break;
            case LONG:
                if(checkedType.checked){
                    Assertions.assertEquals(0,((LongOpenAddressHashSet.Checked)cloneSet).modCount);
                }
                if(expectedTableLength == 0){
                    Assertions.assertNull(((LongOpenAddressHashSet)cloneSet).table);
                }else{
                    final var expectedTable=(long[])this.expectedTable;
                    while(--expectedTableLength >= 0){
                        long v;
                        if((v=expectedTable[expectedTableLength]) != 0 && v != 1){
                            Assertions.assertTrue(cloneSet.contains(v));
                        }
                    }
                }
                break;
            case SHORT:
                if(checkedType.checked){
                    Assertions.assertEquals(0,((ShortOpenAddressHashSet.Checked)cloneSet).modCount);
                }
                if(expectedTableLength == 0){
                    Assertions.assertNull(((ShortOpenAddressHashSet)cloneSet).table);
                }else{
                    final var expectedTable=(short[])this.expectedTable;
                    while(--expectedTableLength >= 0){
                        short v;
                        switch(v=expectedTable[expectedTableLength]){
                        default:
                            Assertions.assertTrue(cloneSet.contains(v));
                        case 0:
                        case 1:
                        }
                    }
                }
                break;
            default:
                throw dataType.invalid();
            }
            verifyStructuralIntegrity(cloneSet);
        }
        @Override
        public void verifyCollectionState(){
            AbstractIntegralTypeOpenAddressHashSet<?> set;
            Assertions.assertEquals(expectedSize,(set=this.set).size);
            Assertions.assertEquals(expectedMaxTableSize,set.maxTableSize);
            Assertions.assertEquals(expectedLoadFactor,set.loadFactor);
            Assertions.assertEquals(expectedTableSize,set.tableSize);
            long[] expectedWords;
            Assertions.assertEquals((expectedWords=this.expectedWords)[0],set.word0);
            Assertions.assertEquals(expectedWords[1],set.word1);
            Assertions.assertEquals(expectedWords[2],set.word2);
            Assertions.assertEquals(expectedWords[3],set.word3);
            switch(dataType){
            case CHAR:
                if(checkedType.checked){
                    Assertions.assertEquals(expectedModCount,((CharOpenAddressHashSet.Checked)set).modCount);
                }
                if(expectedTableLength == 0){
                    Assertions.assertNull(((CharOpenAddressHashSet)set).table);
                }else{
                    Assertions.assertArrayEquals((char[])expectedTable,((CharOpenAddressHashSet)set).table);
                }
                break;
            case SHORT:
                if(checkedType.checked){
                    Assertions.assertEquals(expectedModCount,((ShortOpenAddressHashSet.Checked)set).modCount);
                }
                if(expectedTableLength == 0){
                    Assertions.assertNull(((ShortOpenAddressHashSet)set).table);
                }else{
                    Assertions.assertArrayEquals((short[])expectedTable,((ShortOpenAddressHashSet)set).table);
                }
                break;
            case INT:
                if(checkedType.checked){
                    Assertions.assertEquals(expectedModCount,((IntOpenAddressHashSet.Checked)set).modCount);
                }
                if(expectedTableLength == 0){
                    Assertions.assertNull(((IntOpenAddressHashSet)set).table);
                }else{
                    Assertions.assertArrayEquals((int[])expectedTable,((IntOpenAddressHashSet)set).table);
                }
                break;
            case LONG:
                if(checkedType.checked){
                    Assertions.assertEquals(expectedModCount,((LongOpenAddressHashSet.Checked)set).modCount);
                }
                if(expectedTableLength == 0){
                    Assertions.assertNull(((LongOpenAddressHashSet)set).table);
                }else{
                    Assertions.assertArrayEquals((long[])expectedTable,((LongOpenAddressHashSet)set).table);
                }
                break;
            default:
                throw dataType.invalid();
            }
            verifyStructuralIntegrity(set);
        }
        @Override
        public void verifyRemoveIf(boolean result,MonitoredRemoveIfPredicate filter){
            Assertions.assertEquals(expectedSize,filter.numCalls);
            Assertions.assertEquals(filter.numRemoved,filter.removedVals.size());
            Assertions.assertEquals(filter.numRetained,filter.retainedVals.size());
            if(result){
                ++expectedModCount;
                final var removedValsItr=filter.removedVals.iterator();
                Assertions.assertTrue(removedValsItr.hasNext());
                final var retainedValsItr=filter.retainedVals.iterator();
                switch(dataType){
                case CHAR:{
                    do{
                        char v;
                        Assertions.assertFalse(set.contains(v=(char)removedValsItr.next()));
                        removeVal(v);
                    }while(removedValsItr.hasNext());
                    while(retainedValsItr.hasNext()){
                        Assertions.assertTrue(set.contains((char)retainedValsItr.next()));
                    }
                    break;
                }
                case SHORT:{
                    do{
                        short v;
                        Assertions.assertFalse(set.contains(v=(short)removedValsItr.next()));
                        removeVal(v);
                    }while(removedValsItr.hasNext());
                    while(retainedValsItr.hasNext()){
                        Assertions.assertTrue(set.contains((short)retainedValsItr.next()));
                    }
                    break;
                }
                case INT:{
                    do{
                        int v;
                        Assertions.assertFalse(set.contains(v=(int)removedValsItr.next()));
                        removeVal(v);
                    }while(removedValsItr.hasNext());
                    while(retainedValsItr.hasNext()){
                        Assertions.assertTrue(set.contains((int)retainedValsItr.next()));
                    }
                    break;
                }
                case LONG:{
                    do{
                        long v;
                        Assertions.assertFalse(set.contains(v=(long)removedValsItr.next()));
                        removeVal(v);
                    }while(removedValsItr.hasNext());
                    while(retainedValsItr.hasNext()){
                        Assertions.assertTrue(set.contains((long)retainedValsItr.next()));
                    }
                    break;
                }
                default:
                    throw dataType.invalid();
                }
            }
        }
        public void verifySetLoadFactor(float newLoadFactor){
            if(newLoadFactor <= 0.0f || newLoadFactor > 1.0f || newLoadFactor != newLoadFactor){
                Assertions.assertThrows(IllegalArgumentException.class,()->set.setLoadFactor(newLoadFactor));
            }else{
                set.setLoadFactor(newLoadFactor);
                expectedLoadFactor=newLoadFactor;
                if(expectedTable != null){
                    int tableLength;
                    switch(dataType){
                    case CHAR:
                        tableLength=((char[])expectedTable).length;
                        break;
                    case SHORT:
                        tableLength=((short[])expectedTable).length;
                        break;
                    case INT:
                        tableLength=((int[])expectedTable).length;
                        break;
                    case LONG:
                        tableLength=((long[])expectedTable).length;
                        break;
                    default:
                        throw dataType.invalid();
                    }
                    expectedMaxTableSize=(int)(tableLength * newLoadFactor);
                }
            }
            verifyCollectionState();
        }
        @Override
        public void writeObjectImpl(MonitoredObjectOutputStream oos) throws IOException{
            set.writeExternal(oos);
        }
        private void insert(char[] table,int hash,char inputVal){
            int tableSize;
            if((tableSize=++expectedTableSize) >= expectedMaxTableSize){
                expectedMaxTableSize=(int)((hash=table.length << 1) * expectedLoadFactor);
                char[] newTable;
                expectedTable=newTable=new char[expectedTableLength=hash];
                if(tableSize != 1){
                    for(int i=0;;++i){
                        char tableVal;
                        if((tableVal=table[i]) > 1){
                            quickInsert(newTable,tableVal);
                            if(--tableSize == 1){
                                break;
                            }
                        }
                    }
                }
                quickInsert(newTable,inputVal);
            }else{
                table[hash]=inputVal;
            }
        }
        private void insert(int[] table,int hash,int inputVal){
            int tableSize;
            if((tableSize=++expectedTableSize) >= expectedMaxTableSize){
                expectedMaxTableSize=(int)((hash=table.length << 1) * expectedLoadFactor);
                int[] newTable;
                expectedTable=newTable=new int[expectedTableLength=hash];
                if(tableSize != 1){
                    for(int i=0;;++i){
                        int tableVal;
                        if(((tableVal=table[i]) & -2) != 0){
                            quickInsert(newTable,tableVal);
                            if(--tableSize == 1){
                                break;
                            }
                        }
                    }
                }
                quickInsert(newTable,inputVal);
            }else{
                table[hash]=inputVal;
            }
        }
        private void insert(long[] table,int hash,long inputVal){
            int tableSize;
            if((tableSize=++expectedTableSize) >= expectedMaxTableSize){
                expectedMaxTableSize=(int)((hash=table.length << 1) * expectedLoadFactor);
                long[] newTable;
                expectedTable=newTable=new long[expectedTableLength=hash];
                if(tableSize != 1){
                    for(int i=0;;++i){
                        long tableVal;
                        if(((tableVal=table[i]) & -2) != 0){
                            quickInsert(newTable,tableVal);
                            if(--tableSize == 1){
                                break;
                            }
                        }
                    }
                }
                quickInsert(newTable,inputVal);
            }else{
                table[hash]=inputVal;
            }
        }
        private void insert(short[] table,int hash,short inputVal){
            int tableSize;
            if((tableSize=++expectedTableSize) >= expectedMaxTableSize){
                expectedMaxTableSize=(int)((hash=table.length << 1) * expectedLoadFactor);
                short[] newTable;
                expectedTable=newTable=new short[expectedTableLength=hash];
                if(tableSize != 1){
                    for(int i=0;;++i){
                        short tableVal;
                        if(((tableVal=table[i]) & -2) != 0){
                            quickInsert(newTable,tableVal);
                            if(--tableSize == 1){
                                break;
                            }
                        }
                    }
                }
                quickInsert(newTable,inputVal);
            }else{
                table[hash]=inputVal;
            }
        }
        private void insertInTable(char v){
            char[] t;
            if((t=(char[])expectedTable) == null){
                int expectedTableLength;
                expectedTable=t=new char[this.expectedTableLength=expectedTableLength=expectedMaxTableSize];
                t[v & expectedTableLength - 1]=v;
                expectedMaxTableSize=(int)(expectedTableLength * expectedLoadFactor);
                expectedTableSize=1;
            }else{
                int hash;
                int insertHere=-1;
                int expectedTableLength;
                insertInTable:for(final int initialHash=hash=v & (expectedTableLength=this.expectedTableLength - 1);;){
                    switch(t[hash]){
                    case 0:
                        if(insertHere == -1){
                            insertHere=hash;
                        }
                        break insertInTable;
                    case 1:
                        insertHere=hash;
                    default:
                        if((hash=hash + 1 & expectedTableLength) == initialHash){
                            break insertInTable;
                        }
                    }
                }
                insert(t,insertHere,v);
            }
        }
        private void insertInTable(int v){
            int[] t;
            if((t=(int[])expectedTable) == null){
                int expectedTableLength;
                expectedTable=t=new int[this.expectedTableLength=expectedTableLength=expectedMaxTableSize];
                t[v & expectedTableLength - 1]=v;
                expectedMaxTableSize=(int)(expectedTableLength * expectedLoadFactor);
                expectedTableSize=1;
            }else{
                int hash;
                int insertHere=-1;
                int expectedTableLength;
                insertInTable:for(final int initialHash=hash=v & (expectedTableLength=this.expectedTableLength - 1);;){
                    switch(t[hash]){
                    case 0:
                        if(insertHere == -1){
                            insertHere=hash;
                        }
                        break insertInTable;
                    case 1:
                        insertHere=hash;
                    default:
                        if((hash=hash + 1 & expectedTableLength) == initialHash){
                            break insertInTable;
                        }
                    }
                }
                insert(t,insertHere,v);
            }
        }
        private void insertInTable(long v){
            long[] t;
            if((t=(long[])expectedTable) == null){
                int expectedTableLength;
                expectedTable=t=new long[this.expectedTableLength=expectedTableLength=expectedMaxTableSize];
                t[(int)(v ^ v >>> 32) & expectedTableLength - 1]=v;
                expectedMaxTableSize=(int)(expectedTableLength * expectedLoadFactor);
                expectedTableSize=1;
            }else{
                int hash;
                int insertHere=-1;
                int expectedTableLength;
                insertInTable:for(final int initialHash=hash=(int)(v ^ v >>> 32)
                        & (expectedTableLength=this.expectedTableLength - 1);;){
                    long tableVal;
                    if((tableVal=t[hash]) == 0L){
                        if(insertHere == -1){
                            insertHere=hash;
                        }
                        break insertInTable;
                    }else if(tableVal == 1L){
                        insertHere=hash;
                    }
                    if((hash=hash + 1 & expectedTableLength) == initialHash){
                        break insertInTable;
                    }
                }
                insert(t,insertHere,v);
            }
        }
        private void insertInTable(short v){
            short[] t;
            if((t=(short[])expectedTable) == null){
                int expectedTableLength;
                expectedTable=t=new short[this.expectedTableLength=expectedTableLength=expectedMaxTableSize];
                t[v & expectedTableLength - 1]=v;
                expectedMaxTableSize=(int)(expectedTableLength * expectedLoadFactor);
                expectedTableSize=1;
            }else{
                int hash;
                int insertHere=-1;
                int expectedTableLength;
                insertInTable:for(final int initialHash=hash=v & (expectedTableLength=this.expectedTableLength - 1);;){
                    switch(t[hash]){
                    case 0:
                        if(insertHere == -1){
                            insertHere=hash;
                        }
                        break insertInTable;
                    case 1:
                        insertHere=hash;
                    default:
                        if((hash=hash + 1 & expectedTableLength) == initialHash){
                            break insertInTable;
                        }
                    }
                }
                insert(t,insertHere,v);
            }
        }
        private void removeVal(char v){
            if(v < 256){
                expectedWords[v >> 6]&=~(1L << v);
            }else{
                final char[] expectedTable=(char[])this.expectedTable;
                int expectedTableLength;
                int hash=v & (expectedTableLength=this.expectedTableLength - 1);
                for(;;){
                    if(expectedTable[hash] == v){
                        expectedTable[hash]=1;
                        break;
                    }
                    hash=hash + 1 & expectedTableLength;
                }
                --expectedTableSize;
            }
            --expectedSize;
        }
        private void removeVal(int v){
            if(v < 128 && v >= -128){
                expectedWords[(v >> 6) + 2]&=~(1L << v);
            }else{
                final int[] expectedTable=(int[])this.expectedTable;
                int expectedTableLength;
                int hash=v & (expectedTableLength=this.expectedTableLength - 1);
                for(;;){
                    if(expectedTable[hash] == v){
                        expectedTable[hash]=1;
                        break;
                    }
                    hash=hash + 1 & expectedTableLength;
                }
                --expectedTableSize;
            }
            --expectedSize;
        }
        private void removeVal(long v){
            if(v < 128 && v >= -128){
                int i;
                expectedWords[((i=(int)v) >> 6) + 2]&=~(1L << i);
            }else{
                final long[] expectedTable=(long[])this.expectedTable;
                int expectedTableLength;
                int hash=(int)(v ^ v >>> 32) & (expectedTableLength=this.expectedTableLength - 1);
                for(;;){
                    if(expectedTable[hash] == v){
                        expectedTable[hash]=1;
                        break;
                    }
                    hash=hash + 1 & expectedTableLength;
                }
                --expectedTableSize;
            }
            --expectedSize;
        }
        private void removeVal(short v){
            if(v < 128 && v >= -128){
                expectedWords[(v >> 6) + 2]&=~(1L << v);
            }else{
                final short[] expectedTable=(short[])this.expectedTable;
                int expectedTableLength;
                int hash=v & (expectedTableLength=this.expectedTableLength - 1);
                for(;;){
                    if(expectedTable[hash] == v){
                        expectedTable[hash]=1;
                        break;
                    }
                    hash=hash + 1 & expectedTableLength;
                }
                --expectedTableSize;
            }
            --expectedSize;
        }
        private void verifyStructuralIntegrity(AbstractIntegralTypeOpenAddressHashSet<?> set){
            Assertions.assertEquals(Long.bitCount(set.word0) + Long.bitCount(set.word1) + Long.bitCount(set.word2)
            + Long.bitCount(set.word3) + set.tableSize,set.size);
            final HashSet<Object> encounteredVals=new HashSet<>(set.tableSize);
            switch(dataType){
            case CHAR:{
                char[] table;
                if((table=((CharOpenAddressHashSet)set).table) != null){
                    int i=table.length;
                    // check that it is a power of 2
                    Assertions.assertEquals(1,i >>> Integer.numberOfTrailingZeros(i));
                    Assertions.assertEquals((int)(i * set.loadFactor),set.maxTableSize);
                    while(--i >= 0){
                        char v;
                        switch(v=table[i]){
                        case 0:
                        case 1:
                            break;
                        default:
                            Assertions.assertTrue(encounteredVals.add(v));
                            Assertions.assertTrue(v >= 256);
                        }
                    }
                }
                break;
            }
            case SHORT:{
                short[] table;
                if((table=((ShortOpenAddressHashSet)set).table) != null){
                    int i=table.length;
                    // check that it is a power of 2
                    Assertions.assertEquals(1,i >>> Integer.numberOfTrailingZeros(i));
                    Assertions.assertEquals((int)(i * set.loadFactor),set.maxTableSize);
                    while(--i >= 0){
                        short v;
                        switch(v=table[i]){
                        case 0:
                        case 1:
                            break;
                        default:
                            Assertions.assertTrue(encounteredVals.add(v));
                            Assertions.assertTrue(v < -128 || v > 127);
                        }
                    }
                }
                break;
            }
            case INT:{
                int[] table;
                if((table=((IntOpenAddressHashSet)set).table) != null){
                    int i=table.length;
                    // check that it is a power of 2
                    Assertions.assertEquals(1,i >>> Integer.numberOfTrailingZeros(i));
                    Assertions.assertEquals((int)(i * set.loadFactor),set.maxTableSize);
                    while(--i >= 0){
                        int v;
                        switch(v=table[i]){
                        case 0:
                        case 1:
                            break;
                        default:
                            Assertions.assertTrue(encounteredVals.add(v));
                            Assertions.assertTrue(v < -128 || v > 127);
                        }
                    }
                }
                break;
            }
            case LONG:{
                long[] table;
                if((table=((LongOpenAddressHashSet)set).table) != null){
                    int i=table.length;
                    // check that it is a power of 2
                    Assertions.assertEquals(1,i >>> Integer.numberOfTrailingZeros(i));
                    Assertions.assertEquals((int)(i * set.loadFactor),set.maxTableSize);
                    while(--i >= 0){
                        long v;
                        if((v=table[i]) != 0 && v != 1){
                            Assertions.assertTrue(encounteredVals.add(v));
                            Assertions.assertTrue(v < -128 || v > 127);
                        }
                    }
                }
                break;
            }
            default:
                throw dataType.invalid();
            }
            Assertions.assertEquals(encounteredVals.size(),set.tableSize);
        }
        abstract class AbstractItrMonitor
        implements
        MonitoredSet.MonitoredSetIterator<OmniIterator<?>,AbstractIntegralTypeOpenAddressHashSet<?>>{
            final OmniIterator<?> itr;
            int expectedOffset;
            int expectedNumLeft;
            AbstractItrMonitor(OmniIterator<?> itr,int expectedOffset,int expectedNumLeft){
                this.itr=itr;
                this.expectedOffset=expectedOffset;
                this.expectedNumLeft=expectedNumLeft;
            }
            @Override
            public OmniIterator<?> getIterator(){
                return itr;
            }
            @Override
            public MonitoredCollection<AbstractIntegralTypeOpenAddressHashSet<?>> getMonitoredCollection(){
                return IntegralOpenAddressHashSetMonitor.this;
            }
            @Override
            public int getNumLeft(){
                return expectedNumLeft;
            }
            @Override
            public boolean hasNext(){
                return expectedOffset != -1;
            }
            @Override
            public void updateItrNextState(){
                int expectedOffset;
                if((expectedOffset=this.expectedOffset) < 256){
                    updateItrNextFromWords(expectedOffset);
                }else{
                    updateItrNextFromTable(expectedOffset - 256);
                }
                --expectedNumLeft;
            }
            @Override
            public void verifyNextResult(DataType outputType,Object result){
                Object expectedResult;
                if(expectedOffset < 256){
                    switch(dataType){
                    case CHAR:
                        expectedResult=outputType.convertVal((char)expectedOffset);
                        break;
                    case SHORT:
                        expectedResult=outputType.convertVal((short)(expectedOffset - 128));
                        break;
                    case INT:
                        expectedResult=outputType.convertVal(expectedOffset - 128);
                        break;
                    case LONG:
                        expectedResult=outputType.convertVal((long)(expectedOffset - 128));
                        break;
                    default:
                        throw dataType.invalid();
                    }
                }else{
                    switch(dataType){
                    case CHAR:
                        expectedResult=outputType.convertVal(((char[])expectedTable)[expectedOffset - 256]);
                        break;
                    case SHORT:
                        expectedResult=outputType.convertVal(((short[])expectedTable)[expectedOffset - 256]);
                        break;
                    case INT:
                        expectedResult=outputType.convertValUnchecked(((int[])expectedTable)[expectedOffset - 256]);
                        break;
                    case LONG:
                        expectedResult=outputType.convertValUnchecked(((long[])expectedTable)[expectedOffset - 256]);
                        break;
                    default:
                        throw dataType.invalid();
                    }
                }
                Assertions.assertEquals(expectedResult,result);
            }
            void updateItrNextFromTable(int expectedOffset){
                switch(dataType){
                case CHAR:{
                    char[] table;
                    for(final int tableLength=(table=(char[])expectedTable).length;;){
                        if(++expectedOffset == tableLength){
                            this.expectedOffset=-1;
                            break;
                        }
                        if((table[expectedOffset] & -2) != 0){
                            this.expectedOffset=expectedOffset + 256;
                            break;
                        }
                    }
                    break;
                }
                case SHORT:{
                    short[] table;
                    for(final int tableLength=(table=(short[])expectedTable).length;;){
                        if(++expectedOffset == tableLength){
                            this.expectedOffset=-1;
                            break;
                        }
                        if((table[expectedOffset] & -2) != 0){
                            this.expectedOffset=expectedOffset + 256;
                            break;
                        }
                    }
                    break;
                }
                case INT:{
                    int[] table;
                    for(final int tableLength=(table=(int[])expectedTable).length;;){
                        if(++expectedOffset == tableLength){
                            this.expectedOffset=-1;
                            break;
                        }
                        if((table[expectedOffset] & -2) != 0){
                            this.expectedOffset=expectedOffset + 256;
                            break;
                        }
                    }
                    break;
                }
                case LONG:{
                    long[] table;
                    for(final int tableLength=(table=(long[])expectedTable).length;;){
                        if(++expectedOffset == tableLength){
                            this.expectedOffset=-1;
                            break;
                        }
                        if((table[expectedOffset] & -2) != 0){
                            this.expectedOffset=expectedOffset + 256;
                            break;
                        }
                    }
                    break;
                }
                default:
                    throw dataType.invalid();
                }
            }
            void updateItrNextFromWords(int expectedOffset){
                while(++expectedOffset < 256){
                    if((expectedWords[expectedOffset >> 6] & 1L << expectedOffset) != 0){
                        this.expectedOffset=expectedOffset;
                        return;
                    }
                }
                expectedOffset-=256;
                if(expectedTableSize != 0){
                    switch(dataType){
                    case CHAR:{
                        final char[] table=(char[])expectedTable;
                        for(;;++expectedOffset){
                            if((table[expectedOffset] & -2) != 0){
                                break;
                            }
                        }
                        break;
                    }
                    case SHORT:{
                        final short[] table=(short[])expectedTable;
                        for(;;++expectedOffset){
                            if((table[expectedOffset] & -2) != 0){
                                break;
                            }
                        }
                        break;
                    }
                    case INT:{
                        final int[] table=(int[])expectedTable;
                        for(;;++expectedOffset){
                            if((table[expectedOffset] & -2) != 0){
                                break;
                            }
                        }
                        break;
                    }
                    case LONG:{
                        final long[] table=(long[])expectedTable;
                        for(;;++expectedOffset){
                            if((table[expectedOffset] & -2L) != 0){
                                break;
                            }
                        }
                        break;
                    }
                    default:
                        throw dataType.invalid();
                    }
                    this.expectedOffset=expectedOffset + 256;
                }else{
                    this.expectedOffset=-1;
                }
            }
            private int verifyForEachRemainingHelper(MonitoredFunction function,int expectedLastRet){
                final var monitoredFunctionItr=function.iterator();
                int expectedOffset;
                if((expectedOffset=this.expectedOffset) != -1){
                    switch(dataType){
                    case CHAR:{
                        if(expectedOffset < 256){
                            do{
                                if((expectedWords[expectedOffset >> 6] & 1L << expectedOffset) != 0){
                                    final var v=(Character)monitoredFunctionItr.next();
                                    Assertions.assertEquals(v.charValue(),expectedOffset);
                                    expectedLastRet=expectedOffset;
                                }
                            }while(++expectedOffset != 256);
                            expectedOffset=0;
                        }else{
                            expectedOffset-=256;
                        }
                        int expectedTableSize;
                        if((expectedTableSize=IntegralOpenAddressHashSetMonitor.this.expectedTableSize) != 0){
                            final char[] table=(char[])expectedTable;
                            for(final int tableLength=table.length;expectedOffset < tableLength;++expectedOffset){
                                char tableVal;
                                if(((tableVal=table[expectedOffset]) & -2) != 0){
                                    final var v=(Character)monitoredFunctionItr.next();
                                    Assertions.assertEquals(v.charValue(),tableVal);
                                    expectedLastRet=expectedOffset + 256;
                                    if(--expectedTableSize == 0){
                                        break;
                                    }
                                }
                            }
                        }
                        break;
                    }
                    case SHORT:{
                        if(expectedOffset < 256){
                            do{
                                if((expectedWords[expectedOffset >> 6] & 1L << expectedOffset) != 0){
                                    final var v=(Short)monitoredFunctionItr.next();
                                    Assertions.assertEquals(v.shortValue(),expectedOffset - 128);
                                    expectedLastRet=expectedOffset;
                                }
                            }while(++expectedOffset != 256);
                            expectedOffset=0;
                        }else{
                            expectedOffset-=256;
                        }
                        int expectedTableSize;
                        if((expectedTableSize=IntegralOpenAddressHashSetMonitor.this.expectedTableSize) != 0){
                            final short[] table=(short[])expectedTable;
                            for(final int tableLength=table.length;expectedOffset < tableLength;++expectedOffset){
                                short tableVal;
                                if(((tableVal=table[expectedOffset]) & -2) != 0){
                                    final var v=(Short)monitoredFunctionItr.next();
                                    Assertions.assertEquals(v.shortValue(),tableVal);
                                    expectedLastRet=expectedOffset + 256;
                                    if(--expectedTableSize == 0){
                                        break;
                                    }
                                }
                            }
                        }
                        break;
                    }
                    case INT:{
                        if(expectedOffset < 256){
                            do{
                                if((expectedWords[expectedOffset >> 6] & 1L << expectedOffset) != 0){
                                    final var v=(Integer)monitoredFunctionItr.next();
                                    Assertions.assertEquals(v.intValue(),expectedOffset - 128);
                                    expectedLastRet=expectedOffset;
                                }
                            }while(++expectedOffset != 256);
                            expectedOffset=0;
                        }else{
                            expectedOffset-=256;
                        }
                        int expectedTableSize;
                        if((expectedTableSize=IntegralOpenAddressHashSetMonitor.this.expectedTableSize) != 0){
                            final int[] table=(int[])expectedTable;
                            for(final int tableLength=table.length;expectedOffset < tableLength;++expectedOffset){
                                int tableVal;
                                if(((tableVal=table[expectedOffset]) & -2) != 0){
                                    final var v=(Integer)monitoredFunctionItr.next();
                                    Assertions.assertEquals(v.intValue(),tableVal);
                                    expectedLastRet=expectedOffset + 256;
                                    if(--expectedTableSize == 0){
                                        break;
                                    }
                                }
                            }
                        }
                        break;
                    }
                    case LONG:{
                        if(expectedOffset < 256){
                            do{
                                if((expectedWords[expectedOffset >> 6] & 1L << expectedOffset) != 0){
                                    final var v=(Long)monitoredFunctionItr.next();
                                    Assertions.assertEquals(v.longValue(),expectedOffset - 128);
                                    expectedLastRet=expectedOffset;
                                }
                            }while(++expectedOffset != 256);
                            expectedOffset=0;
                        }else{
                            expectedOffset-=256;
                        }
                        int expectedTableSize;
                        if((expectedTableSize=IntegralOpenAddressHashSetMonitor.this.expectedTableSize) != 0){
                            final long[] table=(long[])expectedTable;
                            for(final int tableLength=table.length;expectedOffset < tableLength;++expectedOffset){
                                long tableVal;
                                if(((tableVal=table[expectedOffset]) & -2) != 0){
                                    final var v=(Long)monitoredFunctionItr.next();
                                    Assertions.assertEquals(v.longValue(),tableVal);
                                    expectedLastRet=expectedOffset + 256;
                                    if(--expectedTableSize == 0){
                                        break;
                                    }
                                }
                            }
                        }
                        break;
                    }
                    default:
                        throw dataType.invalid();
                    }
                }
                Assertions.assertFalse(monitoredFunctionItr.hasNext());
                this.expectedOffset=-1;
                expectedNumLeft=0;
                return expectedLastRet;
            }
        }
        class CheckedItrMonitor extends AbstractItrMonitor{
            int expectedItrModCount;
            int expectedItrLastRet;
            CheckedItrMonitor(OmniIterator<?> itr,int expectedOffset,int expectedNumLeft,int expectedItrModCount){
                super(itr,expectedOffset,expectedNumLeft);
                this.expectedItrModCount=expectedItrModCount;
                expectedItrLastRet=-1;
            }
            @Override
            public void updateItrRemoveState(){
                ++expectedModCount;
                ++expectedItrModCount;
                --expectedSize;
                if(expectedItrLastRet < 256){
                    expectedWords[expectedItrLastRet >> 6]&=~(1L << expectedItrLastRet);
                }else{
                    switch(dataType){
                    case CHAR:
                        ((char[])expectedTable)[expectedItrLastRet - 256]=1;
                        break;
                    case SHORT:
                        ((short[])expectedTable)[expectedItrLastRet - 256]=1;
                        break;
                    case INT:
                        ((int[])expectedTable)[expectedItrLastRet - 256]=1;
                        break;
                    case LONG:
                        ((long[])expectedTable)[expectedItrLastRet - 256]=1;
                        break;
                    default:
                        throw dataType.invalid();
                    }
                    --expectedTableSize;
                }
                expectedItrLastRet=-1;
            }
            @Override
            public void verifyForEachRemaining(MonitoredFunction function){
                expectedItrLastRet=super.verifyForEachRemainingHelper(function,expectedItrLastRet);
            }
            @Override
            public void verifyCloneHelper(Object clone){
                switch(dataType){
                case CHAR:
                    Assertions.assertSame(set,FieldAndMethodAccessor.CharOpenAddressHashSet.Checked.Itr.root(clone));
                    Assertions.assertEquals(expectedOffset,
                            FieldAndMethodAccessor.CharOpenAddressHashSet.Checked.Itr.offset(clone));
                    Assertions.assertEquals(expectedItrModCount,
                            FieldAndMethodAccessor.CharOpenAddressHashSet.Checked.Itr.modCount(clone));
                    Assertions.assertEquals(expectedItrLastRet,
                            FieldAndMethodAccessor.CharOpenAddressHashSet.Checked.Itr.lastRet(clone));
                    break;
                case SHORT:
                    Assertions.assertSame(set,FieldAndMethodAccessor.ShortOpenAddressHashSet.Checked.Itr.root(clone));
                    Assertions.assertEquals(expectedOffset,
                            FieldAndMethodAccessor.ShortOpenAddressHashSet.Checked.Itr.offset(clone));
                    Assertions.assertEquals(expectedItrModCount,
                            FieldAndMethodAccessor.ShortOpenAddressHashSet.Checked.Itr.modCount(clone));
                    Assertions.assertEquals(expectedItrLastRet,
                            FieldAndMethodAccessor.ShortOpenAddressHashSet.Checked.Itr.lastRet(clone));
                    break;
                case INT:
                    Assertions.assertSame(set,FieldAndMethodAccessor.IntOpenAddressHashSet.Checked.Itr.root(clone));
                    Assertions.assertEquals(expectedOffset,
                            FieldAndMethodAccessor.IntOpenAddressHashSet.Checked.Itr.offset(clone));
                    Assertions.assertEquals(expectedItrModCount,
                            FieldAndMethodAccessor.IntOpenAddressHashSet.Checked.Itr.modCount(clone));
                    Assertions.assertEquals(expectedItrLastRet,
                            FieldAndMethodAccessor.IntOpenAddressHashSet.Checked.Itr.lastRet(clone));
                    break;
                case LONG:
                    Assertions.assertSame(set,FieldAndMethodAccessor.LongOpenAddressHashSet.Checked.Itr.root(clone));
                    Assertions.assertEquals(expectedOffset,
                            FieldAndMethodAccessor.LongOpenAddressHashSet.Checked.Itr.offset(clone));
                    Assertions.assertEquals(expectedItrModCount,
                            FieldAndMethodAccessor.LongOpenAddressHashSet.Checked.Itr.modCount(clone));
                    Assertions.assertEquals(expectedItrLastRet,
                            FieldAndMethodAccessor.LongOpenAddressHashSet.Checked.Itr.lastRet(clone));
                    break;
                default:
                    throw dataType.invalid();
                }
            }
            @Override
            void updateItrNextFromTable(int expectedOffset){
                expectedItrLastRet=this.expectedOffset;
                super.updateItrNextFromTable(expectedOffset);
            }
            @Override
            void updateItrNextFromWords(int expectedOffset){
                expectedItrLastRet=this.expectedOffset;
                super.updateItrNextFromWords(expectedOffset);
            }
            @Override
            public boolean nextWasJustCalled(){
                return expectedItrLastRet != -1;
            }
        }
        class UncheckedItrMonitor extends AbstractItrMonitor{
            UncheckedItrMonitor(OmniIterator<?> itr,int expectedOffset,int expectedNumLeft){
                super(itr,expectedOffset,expectedNumLeft);
            }
            @Override
            public void updateItrRemoveState(){
                --expectedSize;
                int expectedOffset;
                int bitIndex;
                switch(bitIndex=(expectedOffset=this.expectedOffset) - 1 >> 6){
                default:
                    if(expectedTableLength != 0){
                        if(expectedOffset == -1){
                            expectedOffset=expectedTableLength;
                        }else{
                            expectedOffset-=256;
                        }
                        switch(dataType){
                        case CHAR:{
                            final char[] table=(char[])expectedTable;
                            for(;;){
                                if((table[--expectedOffset] & -2) != 0){
                                    table[expectedOffset]=1;
                                    --expectedTableSize;
                                    return;
                                }
                                if(expectedOffset == 0){
                                    break;
                                }
                            }
                            break;
                        }
                        case SHORT:{
                            final short[] table=(short[])expectedTable;
                            for(;;){
                                if((table[--expectedOffset] & -2) != 0){
                                    table[expectedOffset]=1;
                                    --expectedTableSize;
                                    return;
                                }
                                if(expectedOffset == 0){
                                    break;
                                }
                            }
                            break;
                        }
                        case INT:{
                            final int[] table=(int[])expectedTable;
                            for(;;){
                                if((table[--expectedOffset] & -2) != 0){
                                    table[expectedOffset]=1;
                                    --expectedTableSize;
                                    return;
                                }
                                if(expectedOffset == 0){
                                    break;
                                }
                            }
                            break;
                        }
                        case LONG:{
                            final long[] table=(long[])expectedTable;
                            for(;;){
                                if((table[--expectedOffset] & -2) != 0){
                                    table[expectedOffset]=1;
                                    --expectedTableSize;
                                    return;
                                }
                                if(expectedOffset == 0){
                                    break;
                                }
                            }
                            break;
                        }
                        default:
                            throw dataType.invalid();
                        }
                    }else{
                        expectedOffset=0;
                    }
                    bitIndex=3;
                case 3:
                case 2:
                case 1:
                case 0:
                    for(;;){
                        long word;
                        if((expectedOffset=Long
                                .numberOfLeadingZeros((word=expectedWords[bitIndex]) & -1L >>> -expectedOffset)) != 64){
                            expectedWords[bitIndex]=word & ~(Long.MIN_VALUE >>> expectedOffset);
                            return;
                        }
                        expectedOffset=0;
                        --bitIndex;
                    }
                }
            }
            @Override
            public void verifyForEachRemaining(MonitoredFunction function){
                super.verifyForEachRemainingHelper(function,-1);
            }
            @Override
            public void verifyCloneHelper(Object clone){
                switch(dataType){
                case CHAR:
                    Assertions.assertSame(set,FieldAndMethodAccessor.CharOpenAddressHashSet.Itr.root(clone));
                    Assertions.assertEquals(expectedOffset,
                            FieldAndMethodAccessor.CharOpenAddressHashSet.Itr.offset(clone));
                    break;
                case SHORT:
                    Assertions.assertSame(set,FieldAndMethodAccessor.ShortOpenAddressHashSet.Itr.root(clone));
                    Assertions.assertEquals(expectedOffset,
                            FieldAndMethodAccessor.ShortOpenAddressHashSet.Itr.offset(clone));
                    break;
                case INT:
                    Assertions.assertSame(set,FieldAndMethodAccessor.IntOpenAddressHashSet.Itr.root(clone));
                    Assertions.assertEquals(expectedOffset,
                            FieldAndMethodAccessor.IntOpenAddressHashSet.Itr.offset(clone));
                    break;
                case LONG:
                    Assertions.assertSame(set,FieldAndMethodAccessor.LongOpenAddressHashSet.Itr.root(clone));
                    Assertions.assertEquals(expectedOffset,
                            FieldAndMethodAccessor.LongOpenAddressHashSet.Itr.offset(clone));
                    break;
                default:
                    throw dataType.invalid();
                }
            }
            @Override
            public boolean nextWasJustCalled(){
                int expectedOffset;
                int bitIndex;
                switch(bitIndex=(expectedOffset=this.expectedOffset) - 1 >> 6){
                default:
                    if(expectedTableLength != 0){
                        if(expectedOffset == -1){
                            expectedOffset=expectedTableLength;
                        }else{
                            expectedOffset-=256;
                        }
                        switch(dataType){
                        case CHAR:{
                            final char[] table=(char[])expectedTable;
                            for(;;){
                                if((table[--expectedOffset] & -2) != 0){
                                    return true;
                                }
                                if(expectedOffset == 0){
                                    break;
                                }
                            }
                            break;
                        }
                        case SHORT:{
                            final short[] table=(short[])expectedTable;
                            for(;;){
                                if((table[--expectedOffset] & -2) != 0){
                                    return true;
                                }
                                if(expectedOffset == 0){
                                    break;
                                }
                            }
                            break;
                        }
                        case INT:{
                            final int[] table=(int[])expectedTable;
                            for(;;){
                                if((table[--expectedOffset] & -2) != 0){
                                    return true;
                                }
                                if(expectedOffset == 0){
                                    break;
                                }
                            }
                            break;
                        }
                        case LONG:{
                            final long[] table=(long[])expectedTable;
                            for(;;){
                                if((table[--expectedOffset] & -2) != 0){
                                    return true;
                                }
                                if(expectedOffset == 0){
                                    break;
                                }
                            }
                            break;
                        }
                        default:
                            throw dataType.invalid();
                        }
                    }else{
                        expectedOffset=0;
                    }
                    bitIndex=3;
                case 3:
                case 2:
                case 1:
                case 0:
                    for(;;){
                        if((expectedOffset=Long
                                .numberOfLeadingZeros(expectedWords[bitIndex] & -1L >>> -expectedOffset)) != 64){
                            return true;
                        }
                        expectedOffset=0;
                        if(--bitIndex < 0){
                            return false;
                        }
                    }
                }
            }
        }
    }
    private interface MonitoredFunctionGenTest{
        void runTest(DataType collectionType,MonitoredFunctionGen functionGen,CheckedType checkedType,
                SetInitialization initSet);
        private void runAllTests(String testName){
            for(final var collectionType:StructType.IntegralOpenAddressHashSet.validDataTypes){
                for(final var functionGen:StructType.IntegralOpenAddressHashSet.validMonitoredFunctionGens){
                    for(final var checkedType:CheckedType.values()){
                        if(checkedType.checked || functionGen.expectedException == null){
                            for(final var initSet:VALID_INIT_SEQS){
                                TestExecutorService
                                .submitTest(()->runTest(collectionType,functionGen,checkedType,initSet));
                            }
                        }
                    }
                }
            }
            TestExecutorService.completeAllTests(testName);
        }
    }
    private interface QueryTest{
        static final int[] sizes=new int[]{0,1,2,3,4,5,8,13,16,21,32,34,55,64,89,128,129,144,233,256,257,377,512,610,
                987};
        static final double[] positions=new double[]{-1,0,0.25,0.5,0.75,1};
        boolean callMethod(IntegralOpenAddressHashSetMonitor monitor,QueryVal queryVal,DataType inputType,
                QueryCastType castType,QueryVal.QueryValModification modification);
        private void runAllTests(String testName){
            for(final var loadFactor:LOAD_FACTORS){
                if(loadFactor > 0.f && loadFactor <= 1.0f && loadFactor == loadFactor){
                    for(final var collectionType:StructType.IntegralOpenAddressHashSet.validDataTypes){
                        for(final var queryVal:QueryVal.values()){
                            if(collectionType.isValidQueryVal(queryVal)){
                                queryVal.validQueryCombos.forEach((modification,castTypesToInputTypes)->{
                                    castTypesToInputTypes.forEach((castType,inputTypes)->{
                                        inputTypes.forEach(inputType->{
                                            final boolean queryCanReturnTrue=queryVal.queryCanReturnTrue(modification,
                                                    castType,inputType,collectionType);
                                            for(final var checkedType:CheckedType.values()){
                                                for(final var size:sizes){
                                                    for(final var position:positions){
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
                                                        TestExecutorService.submitTest(()->runTest(collectionType,
                                                                queryVal,modification,inputType,castType,checkedType,
                                                                size,loadFactor,position));
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
        private void runTest(DataType collectionType,QueryVal queryVal,QueryVal.QueryValModification modification,
                DataType inputType,QueryCastType castType,CheckedType checkedType,int setSize,float loadFactor,
                double position){
            final var monitor=new IntegralOpenAddressHashSetMonitor(collectionType,checkedType,0,loadFactor);
            boolean expectedResult;
            if(position < 0){
                expectedResult=false;
                switch(collectionType){
                case CHAR:
                    queryVal.initDoesNotContain((OmniSet.OfChar)monitor.set,setSize,0,modification);
                    break;
                case SHORT:
                    queryVal.initDoesNotContain((OmniSet.OfShort)monitor.set,setSize,0,modification);
                    break;
                case INT:
                    queryVal.initDoesNotContain((OmniSet.OfInt)monitor.set,setSize,0,modification);
                    break;
                case LONG:
                    queryVal.initDoesNotContain((OmniSet.OfLong)monitor.set,setSize,0,modification);
                    break;
                default:
                    throw collectionType.invalid();
                }
            }else{
                expectedResult=true;
                switch(collectionType){
                case CHAR:
                    queryVal.initContains((OmniSet.OfChar)monitor.set,setSize,0,position,modification);
                    break;
                case SHORT:
                    queryVal.initContains((OmniSet.OfShort)monitor.set,setSize,0,position,modification);
                    break;
                case INT:
                    queryVal.initContains((OmniSet.OfInt)monitor.set,setSize,0,position,modification);
                    break;
                case LONG:
                    queryVal.initContains((OmniSet.OfLong)monitor.set,setSize,0,position,modification);
                    break;
                default:
                    throw collectionType.invalid();
                }
            }
            monitor.updateCollectionState();
            final boolean actualResult=callMethod(monitor,queryVal,inputType,castType,modification);
            Assertions.assertEquals(expectedResult,actualResult);
        }
    }
}
