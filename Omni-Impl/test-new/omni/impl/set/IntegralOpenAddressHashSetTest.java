package omni.impl.set;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import omni.impl.CheckedType;
import omni.impl.DataType;
import omni.impl.FunctionCallType;
import omni.impl.IllegalModification;
import omni.impl.IteratorType;
import omni.impl.StructType;
import omni.util.OmniArray;
import omni.util.TestExecutorService;
public class IntegralOpenAddressHashSetTest{
    // private static final double[] RANDOM_THRESHOLDS=new
    // double[]{0.01,0.05,0.10,0.25,0.50,0.75,0.90,0.95,0.99};
    // private static final double[] NON_RANDOM_THRESHOLD=new double[]{0.5};
    private static final long[] WORDSTATES=new long[]{0x0000000000000000L,0x0000000000000001L,0x0000000000000002L,
            0x0000000000000004L,0x7fffffffffffffffL,0x8000000000000000L,0xfffffffffffffffeL,0xffffffffffffffffL,};
    private static final float[] LOAD_FACTORS=new float[7];
    private static final float DEFAULT_LOAD_FACTOR=0.75f;
    private static final int DEFAULT_INITIAL_CAPACITY=16;
    private static final int[] INITIAL_CAPACITIES=new int[5 + 29 * 3];
    private static final int[] ILLEGAL_INITIAL_CAPACITIES=new int[INITIAL_CAPACITIES.length + 2];
    private static final float[] ILLEGAL_LOAD_FACTORS=new float[LOAD_FACTORS.length + 7];
    static{
        INITIAL_CAPACITIES[0]=0;
        INITIAL_CAPACITIES[1]=1;
        INITIAL_CAPACITIES[2]=2;
        int i=2;
        for(int pow=2;pow <= 30;++pow){
            INITIAL_CAPACITIES[++i]=(1 << pow) - 1;
            INITIAL_CAPACITIES[++i]=1 << pow;
            INITIAL_CAPACITIES[++i]=(1 << pow) + 1;
        }
        INITIAL_CAPACITIES[++i]=OmniArray.MAX_ARR_SIZE;
        INITIAL_CAPACITIES[++i]=Integer.MAX_VALUE;
        System.arraycopy(INITIAL_CAPACITIES,0,ILLEGAL_INITIAL_CAPACITIES,0,INITIAL_CAPACITIES.length);
        ILLEGAL_INITIAL_CAPACITIES[++i]=-1;
        ILLEGAL_INITIAL_CAPACITIES[++i]=Integer.MIN_VALUE;
        LOAD_FACTORS[0]=0.1f;
        LOAD_FACTORS[1]=0.25f;
        LOAD_FACTORS[2]=0.5f;
        LOAD_FACTORS[3]=0.75f;
        LOAD_FACTORS[4]=0.9f;
        LOAD_FACTORS[5]=0.95f;
        LOAD_FACTORS[6]=1.0f;
        System.arraycopy(LOAD_FACTORS,0,ILLEGAL_LOAD_FACTORS,0,LOAD_FACTORS.length);
        ILLEGAL_LOAD_FACTORS[i=LOAD_FACTORS.length]=1.1f;
        ILLEGAL_LOAD_FACTORS[++i]=2.0f;
        ILLEGAL_LOAD_FACTORS[++i]=0.0f;
        ILLEGAL_LOAD_FACTORS[++i]=-1f;
        ILLEGAL_LOAD_FACTORS[++i]=-.75f;
        ILLEGAL_LOAD_FACTORS[++i]=-.5f;
        ILLEGAL_LOAD_FACTORS[++i]=Float.NaN;
    }
    @org.junit.jupiter.api.Test
    public void testConstructor_void(){
        for(var collectionType:StructType.IntegralOpenAddressHashSet.validDataTypes){
            for(var checkedType:CheckedType.values()){
                TestExecutorService.submitTest(
                        ()->new IntegralOpenAddressHashSetMonitor(collectionType,checkedType).verifyCollectionState());
            }
        }
        TestExecutorService.completeAllTests("IntegralOpenAddressHashSetTest.testConstructor_void");
    }
    private static void testConstructor_intHelper(int initialCapacity,DataType collectionType,CheckedType checkedType){
        if(initialCapacity >= 0){
            new IntegralOpenAddressHashSetMonitor(collectionType,checkedType,initialCapacity).verifyCollectionState();
        }else{
            Assertions.assertThrows(IllegalArgumentException.class,
                    ()->new IntegralOpenAddressHashSetMonitor(collectionType,checkedType,initialCapacity));
        }
    }
    @org.junit.jupiter.api.Test
    public void testConstructor_int(){
        for(var collectionType:StructType.IntegralOpenAddressHashSet.validDataTypes){
            for(var checkedType:CheckedType.values()){
                for(int initialCapacity:ILLEGAL_INITIAL_CAPACITIES){
                    if(checkedType.checked || initialCapacity >= 0){
                        TestExecutorService
                        .submitTest(()->testConstructor_intHelper(initialCapacity,collectionType,checkedType));
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("IntegralOpenAddressHashSetTest.testConstructor_int");
    }
    private static void testConstructor_floatHelper(float loadFactor,DataType collectionType,CheckedType checkedType){
        if(loadFactor == loadFactor && loadFactor <= 1.0f && loadFactor > 0){
            new IntegralOpenAddressHashSetMonitor(collectionType,checkedType,loadFactor).verifyCollectionState();
        }else{
            Assertions.assertThrows(IllegalArgumentException.class,
                    ()->new IntegralOpenAddressHashSetMonitor(collectionType,checkedType,loadFactor));
        }
    }
    @org.junit.jupiter.api.Test
    public void testConstructor_float(){
        for(var collectionType:StructType.IntegralOpenAddressHashSet.validDataTypes){
            for(var checkedType:CheckedType.values()){
                for(float loadFactor:ILLEGAL_LOAD_FACTORS){
                    if(checkedType.checked || loadFactor == loadFactor && loadFactor <= 1.0f && loadFactor > 0){
                        TestExecutorService
                        .submitTest(()->testConstructor_floatHelper(loadFactor,collectionType,checkedType));
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("IntegralOpenAddressHashSetTest.testConstructor_float");
    }
    private static void testConstructor_intfloatHelper(int initialCapacity,float loadFactor,DataType collectionType,
            CheckedType checkedType){
        if(initialCapacity >= 0 && loadFactor == loadFactor && loadFactor <= 1.0f && loadFactor > 0){
            new IntegralOpenAddressHashSetMonitor(collectionType,checkedType,initialCapacity,loadFactor)
            .verifyCollectionState();
        }else{
            Assertions.assertThrows(IllegalArgumentException.class,
                    ()->new IntegralOpenAddressHashSetMonitor(collectionType,checkedType,initialCapacity,loadFactor));
        }
    }
    @org.junit.jupiter.api.Test
    public void testConstructor_intfloat(){
        for(var collectionType:StructType.IntegralOpenAddressHashSet.validDataTypes){
            for(var checkedType:CheckedType.values()){
                for(int initialCapacity:ILLEGAL_INITIAL_CAPACITIES){
                    if(checkedType.checked || initialCapacity >= 0){
                        for(float loadFactor:ILLEGAL_LOAD_FACTORS){
                            if(checkedType.checked || loadFactor == loadFactor && loadFactor <= 1.0f && loadFactor > 0){
                                TestExecutorService.submitTest(()->testConstructor_intfloatHelper(initialCapacity,
                                        loadFactor,collectionType,checkedType));
                            }
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("IntegralOpenAddressHashSetTest.testConstructor_intfloat");
    }
    private static enum TableInitializationSequence{
        Empty{
            @Override
            IntegralOpenAddressHashSetMonitor initialize(IntegralOpenAddressHashSetMonitor monitor){
                // nothing to do
                return monitor;
            }
        },
        AddPrime{
            @Override
            IntegralOpenAddressHashSetMonitor initialize(IntegralOpenAddressHashSetMonitor monitor){
                monitor.add(1231);
                return monitor;
            }
        },
        AddFibSeq{
            @Override
            IntegralOpenAddressHashSetMonitor initialize(IntegralOpenAddressHashSetMonitor monitor){
                DataType dataType=monitor.getDataType();
                switch(dataType){
                case CHAR:{
                    var set=(CharOpenAddressHashSet)monitor.set;
                    set.add((char)0);
                    set.add((char)1);
                    set.add((char)2);
                    long pPrev=1;
                    long prev=2;
                    for(int i=3;i < 100;++i){
                        long curr=pPrev + prev;
                        set.add((char)curr);
                        pPrev=prev;
                        prev=curr;
                    }
                    break;
                }
                case SHORT:{
                    var set=(ShortOpenAddressHashSet)monitor.set;
                    set.add((short)0);
                    set.add((short)1);
                    set.add((short)2);
                    long pPrev=1;
                    long prev=2;
                    for(int i=3;i < 100;++i){
                        long curr=pPrev + prev;
                        set.add((short)curr);
                        pPrev=prev;
                        prev=curr;
                    }
                    break;
                }
                case INT:{
                    var set=(IntOpenAddressHashSet)monitor.set;
                    set.add(0);
                    set.add(1);
                    set.add(2);
                    long pPrev=1;
                    long prev=2;
                    for(int i=3;i < 100;++i){
                        long curr=pPrev + prev;
                        set.add((int)curr);
                        pPrev=prev;
                        prev=curr;
                    }
                    break;
                }
                case LONG:{
                    var set=(LongOpenAddressHashSet)monitor.set;
                    set.add((long)0);
                    set.add((long)1);
                    set.add((long)2);
                    long pPrev=1;
                    long prev=2;
                    for(int i=3;i < 100;++i){
                        long curr=pPrev + prev;
                        set.add(curr);
                        pPrev=prev;
                        prev=curr;
                    }
                    break;
                }
                default:
                    throw DataType.invalidDataType(dataType);
                }
                monitor.updateCollectionState();
                return monitor;
            }
        };
        abstract IntegralOpenAddressHashSetMonitor initialize(IntegralOpenAddressHashSetMonitor monitor);
    }
    @org.junit.jupiter.api.Test
    public void testiterator_void(){
        for(var collectionType:StructType.IntegralOpenAddressHashSet.validDataTypes){
            for(var checkedType:CheckedType.values()){
                for(var word0:WORDSTATES){
                    for(var word1:WORDSTATES){
                        for(var word2:WORDSTATES){
                            for(var word3:WORDSTATES){
                                for(var tableInitSeq:TableInitializationSequence.values()){
                                    TestExecutorService.submitTest(()->
                                    tableInitSeq
                                    .initialize(new IntegralOpenAddressHashSetMonitor(collectionType,
                                            checkedType,DEFAULT_INITIAL_CAPACITY,DEFAULT_LOAD_FACTOR,word0,
                                            word1,word2,word3))
                                    .getMonitoredIterator().verifyIteratorState()
                                            )
                                    ;
                                }
                            }
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("IntegralOpenAddressHashSetTest.testiterator_void");
    }
    private static void testItrnext_voidHelper(IllegalModification preMod,DataType outputType,DataType collectionType,
            CheckedType checkedType,TableInitializationSequence tableInitSeq,long...expectedWords){
        var setMonitor=tableInitSeq.initialize(new IntegralOpenAddressHashSetMonitor(collectionType,checkedType,
                DEFAULT_INITIAL_CAPACITY,DEFAULT_LOAD_FACTOR,expectedWords));
        var itrMonitor=setMonitor.getMonitoredIterator();
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
    @org.junit.jupiter.api.Test
    public void testItrnext_void(){
        for(var collectionType:StructType.IntegralOpenAddressHashSet.validDataTypes){
            for(var outputType:collectionType.validOutputTypes()){
                for(var checkedType:CheckedType.values()){
                    for(final var preMod:IteratorType.AscendingItr.validPreMods){
                        if(checkedType.checked || preMod.expectedException == null){
                            for(var word0:WORDSTATES){
                                for(var word1:WORDSTATES){
                                    for(var word2:WORDSTATES){
                                        for(var word3:WORDSTATES){
                                            for(var tableInitSeq:TableInitializationSequence.values()){
                                                TestExecutorService.submitTest(()->
                                                testItrnext_voidHelper(preMod,outputType,collectionType,checkedType,
                                                        tableInitSeq,word0,word1,word2,word3)
                                                        )
                                                ;
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
        TestExecutorService.completeAllTests("IntegralOpenAddressHashSetTest.testItrnext_void");
    }
    //    private static int getSize(long...words){
    //        int size=0;
    //        for(final var word:words){
    //            size+=Long.bitCount(word);
    //        }
    //        return size;
    //    }
    //    private static void testItrforEachRemaining_ConsumerHelper(int itrScenario,IllegalModification preMod,
    //            MonitoredFunctionGen functionGen,FunctionCallType functionCallType,long randSeed,DataType collectionType,
    //            CheckedType checkedType,TableInitializationSequence tableInitSeq,long...expectedWords){
    //        var setMonitor=tableInitSeq.initialize(new IntegralOpenAddressHashSetMonitor(collectionType,checkedType,
    //                DEFAULT_INITIAL_CAPACITY,DEFAULT_LOAD_FACTOR,expectedWords));
    //        final var itrMonitor=setMonitor.getMonitoredIterator();
    //        switch(itrScenario){
    //        case 1:
    //            itrMonitor.iterateForward();
    //            break;
    //        case 2:
    //            itrMonitor.iterateForward();
    //            itrMonitor.remove();
    //        default:
    //        }
    //        final int numLeft=itrMonitor.getNumLeft();
    //        itrMonitor.illegalMod(preMod);
    //        final Class<? extends Throwable> expectedException=numLeft == 0?null
    //                :preMod.expectedException == null?functionGen.expectedException:preMod.expectedException;
    //        if(expectedException == null){
    //            itrMonitor.verifyForEachRemaining(functionGen,functionCallType,randSeed);
    //        }else{
    //            Assertions.assertThrows(expectedException,
    //                    ()->itrMonitor.verifyForEachRemaining(functionGen,functionCallType,randSeed));
    //            itrMonitor.verifyIteratorState();
    //            setMonitor.verifyCollectionState();
    //        }
    //    }
    @org.junit.jupiter.api.Test
    public void testadd_val(){
        final float[] loadFactors=new float[]{0.25f,0.5f,0.75f,0.9f,1.0f};
        final int[] initialCapacities=new int[]{0,1,2,4,8,16,32,64,128,256,512,1024,2048,4096,8192};
        for(var collectionType:StructType.IntegralOpenAddressHashSet.validDataTypes){
            for(final var inputType:collectionType.mayBeAddedTo()){
                for(final var checkedType:CheckedType.values()){
                    for(float loadFactor:loadFactors){
                        for(int initialCapacity:initialCapacities){
                            for(final long word0:WORDSTATES){
                                for(final long word1:WORDSTATES){
                                    for(final long word2:WORDSTATES){
                                        for(final long word3:WORDSTATES){
                                            for(final var tableInitSeq:TableInitializationSequence.values()){
                                                for(final var functionCallType:FunctionCallType.values()){
                                                    TestExecutorService.submitTest(()->testadd_valHelper(collectionType,
                                                            inputType,checkedType,loadFactor,initialCapacity,
                                                            tableInitSeq,functionCallType,word0,word1,word2,word3));
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
        TestExecutorService.completeAllTests("IntegralOpenAddressHashSetTest.testadd_val");
    }
    //    @org.junit.jupiter.api.Test
    //    public void testItrforEachRemaining_Consumer(){
    //        for(var collectionType:StructType.IntegralOpenAddressHashSet.validDataTypes){
    //            for(final var checkedType:CheckedType.values()){
    //                for(var word0:WORDSTATES){
    //                    for(var word1:WORDSTATES){
    //                        for(var word2:WORDSTATES){
    //                            for(var word3:WORDSTATES){
    //                                for(var tableInitSeq:TableInitializationSequence.values()){
    //                                    int wordSize=getSize(word0,word1,word2,word3);
    //                                    int sizeScenario;
    //                                    outer:for(;;){
    //                                        switch(tableInitSeq){
    //                                        case Empty:
    //                                            sizeScenario=Math.min(2,wordSize);
    //                                            break outer;
    //                                        case AddPrime:
    //                                            sizeScenario=wordSize == 0?1:2;
    //                                            break outer;
    //                                        case AddFibSeq:
    //                                            sizeScenario=2;
    //                                            break outer;
    //                                        }
    //                                        throw new UnsupportedOperationException("Unknown tableInitSeq " + tableInitSeq);
    //                                    }
    //                                    for(final var functionGen:IteratorType.AscendingItr.validMonitoredFunctionGens){
    //                                        if(checkedType.checked || functionGen.expectedException == null
    //                                                || sizeScenario == 0){
    //                                            for(final var preMod:IteratorType.AscendingItr.validPreMods){
    //                                                if(checkedType.checked || preMod.expectedException == null
    //                                                        || sizeScenario == 0){
    //                                                    int itrScenarioMax=0;
    //                                                    if(sizeScenario > 1){
    //                                                        itrScenarioMax=2;
    //                                                    }
    //                                                    IntStream.rangeClosed(0,itrScenarioMax).forEach(itrScenario->{
    //                                                        LongStream.rangeClosed(0,
    //                                                                preMod.expectedException == null
    //                                                                && functionGen.randomized && sizeScenario > 1
    //                                                                && itrScenario == 0?100:0)
    //                                                        .forEach(randSeed->{
    //                                                            for(final var functionCallType:FunctionCallType
    //                                                                    .values()){
    //                                                                        TestExecutorService.submitTest(()->
    //                                                                testItrforEachRemaining_ConsumerHelper(
    //                                                                        itrScenario,preMod,functionGen,
    //                                                                        functionCallType,randSeed,
    //                                                                        collectionType,checkedType,
    //                                                                        tableInitSeq,word0,word1,word2,
    //                                                                        word3)
    //                                                                )
    //                                                                ;
    //                                                            }
    //                                                        });
    //                                                    });
    //                                                }
    //                                            }
    //                                        }
    //                                    }
    //                                }
    //                            }
    //                        }
    //                    }
    //                }
    //            }
    //        }
    //        TestExecutorService.completeAllTests("IntegralOpenAddressHashSetTest.testItrforEachRemaining_Consumer");
    //    }
    private static void testadd_valHelper(DataType collectionType,DataType inputType,CheckedType checkedType,
            float loadFactor,int initialCapacity,TableInitializationSequence tableInitSeq,
            FunctionCallType functionCallType,long...expectedWords){
        var monitor=tableInitSeq.initialize(new IntegralOpenAddressHashSetMonitor(collectionType,checkedType,
                initialCapacity,loadFactor,expectedWords));
        long pPrev=0;
        long prev=1;
        for(int i=0;i < 100;++i){
            long curr=pPrev + prev;
            boolean alreadyContains;
            switch(collectionType){
            case CHAR:
                alreadyContains=((CharOpenAddressHashSet)monitor.set).contains((char)curr);
                break;
            case SHORT:
                alreadyContains=((CharOpenAddressHashSet)monitor.set).contains((short)curr);
                break;
            case INT:
                alreadyContains=((CharOpenAddressHashSet)monitor.set).contains((int)curr);
                break;
            case LONG:
                alreadyContains=((CharOpenAddressHashSet)monitor.set).contains(curr);
                break;
            default:
                throw DataType.invalidDataType(collectionType);
            }
            Assertions.assertNotEquals(alreadyContains,
                    monitor.verifyAdd(inputType.convertValUnchecked(curr),inputType,functionCallType));
            pPrev=prev;
            prev=curr;
        }
    }
}
