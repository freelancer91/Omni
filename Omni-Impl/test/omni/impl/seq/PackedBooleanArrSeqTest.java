package omni.impl.seq;
import java.io.Externalizable;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.api.OmniCollection;
import omni.api.OmniIterator;
import omni.api.OmniList;
import omni.api.OmniListIterator;
import omni.api.OmniStack;
import omni.impl.AbstractOmniCollection;
import omni.impl.CheckedType;
import omni.impl.DataType;
import omni.impl.FunctionCallType;
import omni.impl.IllegalModification;
import omni.impl.IteratorType;
import omni.impl.MonitoredCollection;
import omni.impl.MonitoredComparatorGen;
import omni.impl.MonitoredFunction;
import omni.impl.MonitoredFunctionGen;
import omni.impl.MonitoredList;
import omni.impl.MonitoredObjectOutputStream;
import omni.impl.MonitoredRemoveIfPredicate;
import omni.impl.MonitoredSequence;
import omni.impl.MonitoredStack;
import omni.impl.QueryCastType;
import omni.impl.QueryVal;
import omni.impl.QueryVal.QueryValModification;
import omni.impl.StructType;
import omni.impl.seq.PackedBooleanArrSeq.UncheckedList;
import omni.impl.seq.PackedBooleanArrSeq.UncheckedStack;
import omni.util.ArrCopy;
import omni.util.BitSetUtil;
import omni.util.OmniArray;
import omni.util.TestExecutorService;
public class PackedBooleanArrSeqTest{
    /**
     * TODO add more indices as needed
     */
    private static final int[] NON_THROWING_REMOVE_AT_POSITIONS=new int[]{-1,0,1,2,3};
    private static final int[] THROWING_REMOVE_AT_POSITIONS=new int[]{-1};
    private static final double[] POSITIONS;
    private static final int[] INIT_CAPACITIES=new int[]{0,64,128,192,256,320,384};
    private static final int[] SHORT_SIZES;
    public static final int[] LONG_SIZES;
    private static final SequenceInitParams[] ALL_STRUCT_INIT_PARAMS;
    private static final SequenceInitParams[] LIST_STRUCT_INIT_PARAMS;
    private static final SequenceInitParams[] STACK_STRUCT_INIT_PARAMS;
    private static final SequenceInitParams[] ROOT_STRUCT_INIT_PARAMS;
    static{
        {
            final IntStream.Builder builder=IntStream.builder();
            for(int baseSize=0;baseSize <= 6400;baseSize+=64){
                for(int mod=-2;mod <= 2;++mod){
                    final int size=baseSize + mod;
                    if(size >= 0){
                        builder.accept(size);
                    }
                }
            }
            LONG_SIZES=builder.build().toArray();
        }
        {
            final IntStream.Builder builder=IntStream.builder();
            for(int baseSize=0;baseSize <= 192;baseSize+=64){
                for(int mod=-1;mod <= 1;++mod){
                    final int size=baseSize + mod;
                    if(size >= 0){
                        builder.accept(size);
                    }
                }
            }
            SHORT_SIZES=builder.build().toArray();
        }
        POSITIONS=new double[]{-1,0.0,0.25,0.5,0.75,1.0};
        final Stream.Builder<SequenceInitParams> allStructBuilder=Stream.builder();
        final Stream.Builder<SequenceInitParams> listStructBuilder=Stream.builder();
        final Stream.Builder<SequenceInitParams> stackStructBuilder=Stream.builder();
        final Stream.Builder<SequenceInitParams> rootStructBuilder=Stream.builder();
        for(final var checkedType:CheckedType.values()){
            final var arrListParams=new SequenceInitParams(StructType.PackedBooleanArrList,DataType.BOOLEAN,checkedType,
                    OmniArray.OfInt.DEFAULT_ARR,OmniArray.OfInt.DEFAULT_ARR);
            final var arrStackParams=new SequenceInitParams(StructType.PackedBooleanArrStack,DataType.BOOLEAN,
                    checkedType,OmniArray.OfInt.DEFAULT_ARR,OmniArray.OfInt.DEFAULT_ARR);
            allStructBuilder.accept(arrListParams);
            allStructBuilder.accept(arrStackParams);
            stackStructBuilder.accept(arrStackParams);
            listStructBuilder.accept(arrListParams);
            rootStructBuilder.accept(arrListParams);
            rootStructBuilder.accept(arrStackParams);
        }
        final int[] buffers=new int[]{0,1,2,3,4,60,61,62,63,64,65,66,67,68,124,125,126,127,128,129,130,131,132};
        for(final var pre0:buffers){
            for(final var pre1:buffers){
                if(pre1 > 0){
                    continue;
                }
                final var preAllocs=new int[]{pre0,pre1};
                for(final var post0:buffers){
                    for(final var post1:buffers){
                        if(post1 > 0){
                            continue;
                        }
                        final var postAllocs=new int[]{post0,post1};
                        for(final var checkedType:CheckedType.values()){
                            final var subListParams=new SequenceInitParams(StructType.PackedBooleanArrSubList,
                                    DataType.BOOLEAN,checkedType,preAllocs,postAllocs);
                            listStructBuilder.accept(subListParams);
                            allStructBuilder.accept(subListParams);
                        }
                    }
                }
            }
        }
        ALL_STRUCT_INIT_PARAMS=allStructBuilder.build().toArray(SequenceInitParams[]::new);
        LIST_STRUCT_INIT_PARAMS=listStructBuilder.build().toArray(SequenceInitParams[]::new);
        STACK_STRUCT_INIT_PARAMS=stackStructBuilder.build().toArray(SequenceInitParams[]::new);
        ROOT_STRUCT_INIT_PARAMS=rootStructBuilder.build().toArray(SequenceInitParams[]::new);
    }
    private static int getInitCapacity(int initCapacity,int[] preAllocs,int[] postAllocs){
        for(int i=preAllocs.length;--i >= 0;){
            initCapacity+=preAllocs[i];
        }
        for(int i=postAllocs.length;--i >= 0;){
            initCapacity+=postAllocs[i];
        }
        return initCapacity;
    }
    static MonitoredList<? extends OmniList.OfBoolean> getMonitoredList(SequenceInitParams initParams,
            int initCapacity){
        final var rootMonitor=new PackedBooleanArrListMonitor(initParams,
                getInitCapacity(initCapacity,initParams.preAllocs,initParams.postAllocs));
        if(initParams.structType != StructType.PackedBooleanArrSubList){
            return rootMonitor;
        }
        final var preAllocs=initParams.preAllocs;
        final var postAllocs=initParams.postAllocs;
        int totalPreAlloc=preAllocs[0];
        int totalPostAlloc=postAllocs[0];
        SequenceInitialization.Ascending.initialize(rootMonitor,totalPreAlloc,Integer.MIN_VALUE);
        SequenceInitialization.Ascending.initialize(rootMonitor,totalPostAlloc,Integer.MAX_VALUE - totalPostAlloc);
        var subListMonitor=new PackedBooleanArrListMonitor.PackedBooleanArrSubListMonitor<>(rootMonitor,totalPreAlloc,
                totalPreAlloc);
        for(int i=1;i < preAllocs.length;++i){
            int postAlloc;
            int preAlloc;
            totalPostAlloc+=postAlloc=postAllocs[i];
            SequenceInitialization.Ascending.initialize(subListMonitor,preAlloc=preAllocs[i],
                    Integer.MIN_VALUE + totalPreAlloc);
            SequenceInitialization.Ascending.initialize(subListMonitor,postAlloc,Integer.MAX_VALUE - totalPostAlloc);
            totalPreAlloc+=preAlloc;
            subListMonitor=new PackedBooleanArrListMonitor.PackedBooleanArrSubListMonitor<>(subListMonitor,preAlloc,
                    preAlloc);
        }
        return subListMonitor;
    }
    private static MonitoredSequence<? extends OmniCollection.OfBoolean> getMonitoredSequence(
            SequenceInitParams initParams,int initCapacity){
        if(initParams.structType == StructType.PackedBooleanArrStack){
            return new PackedBooleanArrStackMonitor(initParams,initCapacity);
        }else{
            return getMonitoredList(initParams,initCapacity);
        }
    }
    private static MonitoredStack<? extends OmniStack.OfBoolean> getMonitoredStack(SequenceInitParams initParams,
            int initCapacity){
        return new PackedBooleanArrStackMonitor(initParams,initCapacity);
    }
    private static void verifyMASSIVEToStringHelper(String result,int seqSize,String testName,boolean forward){
        int offset;
        Assertions.assertEquals('[',result.charAt(offset=0));
        if(forward){
            Assertions.assertEquals('f',result.charAt(++offset));
            Assertions.assertEquals('a',result.charAt(++offset));
            Assertions.assertEquals('l',result.charAt(++offset));
            Assertions.assertEquals('s',result.charAt(++offset));
            Assertions.assertEquals('e',result.charAt(++offset));
            ++offset;
            final int numBatches=TestExecutorService.getNumWorkers();
            final int batchSize=(seqSize - 2) / numBatches * 13;
            for(int batchCount=0;batchCount < numBatches;++batchCount){
                final int batchOffset=offset;
                final int batchBound=batchCount == numBatches?result.length() - 1:offset + batchSize;
                TestExecutorService.submitTest(()->{
                    for(int i=batchOffset;;++i){
                        if(i >= batchBound){
                            break;
                        }
                        Assertions.assertEquals(',',result.charAt(i));
                        Assertions.assertEquals(' ',result.charAt(++i));
                        Assertions.assertEquals('t',result.charAt(++i));
                        Assertions.assertEquals('r',result.charAt(++i));
                        Assertions.assertEquals('u',result.charAt(++i));
                        Assertions.assertEquals('e',result.charAt(++i));
                        if(++i >= batchBound){
                            break;
                        }
                        Assertions.assertEquals(',',result.charAt(i));
                        Assertions.assertEquals(' ',result.charAt(++i));
                        Assertions.assertEquals('f',result.charAt(++i));
                        Assertions.assertEquals('a',result.charAt(++i));
                        Assertions.assertEquals('l',result.charAt(++i));
                        Assertions.assertEquals('s',result.charAt(++i));
                        Assertions.assertEquals('e',result.charAt(++i));
                    }
                });
            }
            Assertions.assertEquals(',',result.charAt(offset=result.length() - 7));
            Assertions.assertEquals(' ',result.charAt(++offset));
            Assertions.assertEquals('t',result.charAt(++offset));
            Assertions.assertEquals('r',result.charAt(++offset));
            Assertions.assertEquals('u',result.charAt(++offset));
        }else{
            Assertions.assertEquals('t',result.charAt(++offset));
            Assertions.assertEquals('r',result.charAt(++offset));
            Assertions.assertEquals('u',result.charAt(++offset));
            Assertions.assertEquals('e',result.charAt(++offset));
            ++offset;
            final int numBatches=TestExecutorService.getNumWorkers();
            final int batchSize=(seqSize - 2) / numBatches * 13;
            for(int batchCount=0;batchCount < numBatches;++batchCount){
                final int batchOffset=offset;
                final int batchBound=batchCount == numBatches?result.length() - 1:offset + batchSize;
                TestExecutorService.submitTest(()->{
                    for(int i=batchOffset;;++i){
                        if(i >= batchBound){
                            break;
                        }
                        Assertions.assertEquals(',',result.charAt(i));
                        Assertions.assertEquals(' ',result.charAt(++i));
                        Assertions.assertEquals('f',result.charAt(++i));
                        Assertions.assertEquals('a',result.charAt(++i));
                        Assertions.assertEquals('l',result.charAt(++i));
                        Assertions.assertEquals('s',result.charAt(++i));
                        Assertions.assertEquals('e',result.charAt(++i));
                        if(++i >= batchBound){
                            break;
                        }
                        Assertions.assertEquals(',',result.charAt(i));
                        Assertions.assertEquals(' ',result.charAt(++i));
                        Assertions.assertEquals('t',result.charAt(++i));
                        Assertions.assertEquals('r',result.charAt(++i));
                        Assertions.assertEquals('u',result.charAt(++i));
                        Assertions.assertEquals('e',result.charAt(++i));
                    }
                });
            }
            Assertions.assertEquals(',',result.charAt(offset=result.length() - 8));
            Assertions.assertEquals(' ',result.charAt(++offset));
            Assertions.assertEquals('f',result.charAt(++offset));
            Assertions.assertEquals('a',result.charAt(++offset));
            Assertions.assertEquals('l',result.charAt(++offset));
            Assertions.assertEquals('s',result.charAt(++offset));
        }
        Assertions.assertEquals('e',result.charAt(++offset));
        Assertions.assertEquals(']',result.charAt(++offset));
        TestExecutorService.completeAllTests(testName);
    }
    @Test
    public void testadd_intval(){
        for(final var initParams:LIST_STRUCT_INIT_PARAMS){
            for(final var illegalMod:initParams.structType.validPreMods){
                if(illegalMod.minDepth <= initParams.preAllocs.length
                        && (initParams.checkedType.checked || illegalMod.expectedException == null)){
                    for(final var inputType:DataType.BOOLEAN.mayBeAddedTo()){
                        for(final var functionCallType:inputType.validFunctionCalls){
                            for(final var initCap:INIT_CAPACITIES){
                                if(illegalMod.expectedException == null){
                                    int prevMax=-1;
                                    for(final var position:POSITIONS){
                                        if(position > 0 && (inputType != DataType.BOOLEAN
                                                || functionCallType != FunctionCallType.Unboxed)){
                                            break;
                                        }
                                        if(position >= 0 || initParams.checkedType.checked){
                                            if(position < 0){
                                                TestExecutorService.submitTest(()->{
                                                    final var monitor=getMonitoredList(initParams,initCap);
                                                    for(int i=0;i < 194;++i){
                                                        final Object inputVal=inputType.convertValUnchecked(i);
                                                        final int finalI=i;
                                                        Assertions.assertThrows(IndexOutOfBoundsException.class,
                                                                ()->monitor.verifyAdd(-1,inputVal,inputType,
                                                                        functionCallType));
                                                        Assertions.assertThrows(IndexOutOfBoundsException.class,
                                                                ()->monitor.verifyAdd(finalI + 1,inputVal,inputType,
                                                                        functionCallType));
                                                        monitor.add(i);
                                                    }
                                                });
                                            }else{
                                                final int maxIndex=(int)(position * 194);
                                                if(maxIndex == prevMax){
                                                    continue;
                                                }
                                                prevMax=maxIndex;
                                                TestExecutorService.submitTest(()->{
                                                    final var monitor=getMonitoredList(initParams,initCap);
                                                    for(int i=0;i < 194;++i){
                                                        monitor.verifyAdd((int)(i * position),
                                                                inputType.convertValUnchecked(i),inputType,
                                                                functionCallType);
                                                    }
                                                });
                                            }
                                        }
                                    }
                                }else{
                                    TestExecutorService.submitTest(()->{
                                        final var monitor=SequenceInitialization.Ascending
                                                .initialize(getMonitoredList(initParams,initCap),10,0);
                                        monitor.illegalMod(illegalMod);
                                        final Object inputVal=inputType.convertValUnchecked(0);
                                        Assertions.assertThrows(illegalMod.expectedException,
                                                ()->monitor.verifyAdd(-1,inputVal,inputType,functionCallType));
                                        Assertions.assertThrows(illegalMod.expectedException,
                                                ()->monitor.verifyAdd(1,inputVal,inputType,functionCallType));
                                        Assertions.assertThrows(illegalMod.expectedException,
                                                ()->monitor.verifyAdd(0,inputVal,inputType,functionCallType));
                                    });
                                    TestExecutorService.submitTest(()->{
                                        final var monitor=getMonitoredList(initParams,initCap);
                                        monitor.illegalMod(illegalMod);
                                        final Object inputVal=inputType.convertValUnchecked(0);
                                        Assertions.assertThrows(illegalMod.expectedException,
                                                ()->monitor.verifyAdd(-1,inputVal,inputType,functionCallType));
                                        Assertions.assertThrows(illegalMod.expectedException,
                                                ()->monitor.verifyAdd(1,inputVal,inputType,functionCallType));
                                        Assertions.assertThrows(illegalMod.expectedException,
                                                ()->monitor.verifyAdd(0,inputVal,inputType,functionCallType));
                                    });
                                }
                            }
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("PackedBooleanArrSeqTest.testadd_intval");
    }
    @Test
    public void testadd_val(){
        for(final var initParams:ALL_STRUCT_INIT_PARAMS){
            for(final var illegalMod:initParams.structType.validPreMods){
                if(illegalMod.minDepth <= initParams.preAllocs.length
                        && (initParams.checkedType.checked || illegalMod.expectedException == null)){
                    for(final var inputType:DataType.BOOLEAN.mayBeAddedTo()){
                        for(final var functionCallType:inputType.validFunctionCalls){
                            for(final var initCap:INIT_CAPACITIES){
                                if(illegalMod.expectedException == null){
                                    TestExecutorService.submitTest(()->{
                                        final var monitor=getMonitoredSequence(initParams,initCap);
                                        for(int i=0;i < 258;++i){
                                            monitor.verifyAdd(inputType.convertValUnchecked(i),inputType,
                                                    functionCallType);
                                        }
                                    });
                                }else{
                                    TestExecutorService.submitTest(()->{
                                        final var monitor=getMonitoredSequence(initParams,initCap);
                                        monitor.illegalMod(illegalMod);
                                        Assertions.assertThrows(illegalMod.expectedException,
                                                ()->monitor.verifyAdd(inputType.convertValUnchecked(0),inputType,
                                                        functionCallType));
                                    });
                                    TestExecutorService.submitTest(()->{
                                        final var monitor=SequenceInitialization.Ascending
                                                .initialize(getMonitoredSequence(initParams,initCap),10,0);
                                        monitor.illegalMod(illegalMod);
                                        Assertions.assertThrows(illegalMod.expectedException,
                                                ()->monitor.verifyAdd(inputType.convertValUnchecked(0),inputType,
                                                        functionCallType));
                                    });
                                }
                            }
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("PackedBooleanArrSeqTest.testadd_val");
    }
    @Test
    public void testclear_void(){
        final BasicTest test=new BasicTest(){
            @Override
            public int getMaxPostAlloc(){
                return Integer.MAX_VALUE;
            }
            @Override
            public void runTest(MonitoredSequence<?> monitor){
                monitor.verifyClear();
            }
        };
        test.runAllTests("PackedBooleanArrSeqTest.testclear_void",SHORT_SIZES);
    }
    @Test
    public void testclone_void(){
        final BasicTest test=MonitoredSequence::verifyClone;
        test.runAllTests("PackedBooleanArrSeqTest.testclone_void",SHORT_SIZES);
    }
    @SuppressWarnings("unchecked")
    @Test
    public void testConstructor_Collection() {
          for(var checkedType:CheckedType.values()) {
              for(var collectionClass:DataType.BOOLEAN.validCollectionConstructorClasses) {
                  TestExecutorService.submitTest(()->{
                      Collection<?> collectionParam=MonitoredCollection.getConstructorCollectionParam(DataType.BOOLEAN,(Class<? extends Collection<?>>)collectionClass);
                      new PackedBooleanArrListMonitor(checkedType,collectionParam,(Class<? extends Collection<?>>)collectionClass).verifyCollectionState();
                      new PackedBooleanArrStackMonitor(checkedType,collectionParam,(Class<? extends Collection<?>>)collectionClass).verifyCollectionState();
                  });
              }
          }
      TestExecutorService.completeAllTests("PackedBooleanArrSeqTest.testConstructor_Collection");
    }
    @Test
    public void testConstructor_int(){
        final int[] initCapacities=new int[]{0,5,10,15,63,64,65,66,127,128,129,255,256,257,319,320,321};
        for(final var initParams:ROOT_STRUCT_INIT_PARAMS){
            for(final var initCap:initCapacities){
                TestExecutorService.submitTest(()->{
                    AbstractPackedBooleanArrSeqMonitor<?> monitor;
                    switch(initParams.structType){
                    case PackedBooleanArrList:{
                        monitor=new PackedBooleanArrListMonitor(initParams,initCap);
                        break;
                    }
                    case PackedBooleanArrStack:{
                        monitor=new PackedBooleanArrStackMonitor(initParams,initCap);
                        break;
                    }
                    default:
                        throw initParams.structType.invalid();
                    }
                    if(initCap <= 64){
                        Assertions.assertNull(monitor.expectedWords);
                    }else{
                        Assertions.assertEquals((initCap - 1 >> 6) + 1,monitor.expectedWords.length);
                    }
                    monitor.verifyCollectionState();
                });
            }
        }
        TestExecutorService.completeAllTests("PackedBooleanArrSeqTest.testConstructor_int");
    }
    @Test
    public void testConstructor_void(){
        for(final var initParams:ROOT_STRUCT_INIT_PARAMS){
            TestExecutorService.submitTest(()->{
                MonitoredSequence<?> monitor;
                switch(initParams.structType){
                case PackedBooleanArrList:
                    monitor=new PackedBooleanArrListMonitor(initParams);
                    break;
                case PackedBooleanArrStack:
                    monitor=new PackedBooleanArrStackMonitor(initParams);
                    break;
                default:
                    throw initParams.structType.invalid();
                }
                monitor.verifyCollectionState();
            });
        }
        TestExecutorService.completeAllTests("PackedBooleanArrSeqTest.testConstructor_void");
    }
    @Test
    public void testcontains_val(){
        final QueryTest<?> test=(monitor,queryVal,inputType,castType,modification,position,seqSize)->{
            Assertions.assertEquals(position >= 0,monitor.verifyContains(queryVal,inputType,castType,modification));
        };
        test.runAllTests("PackedBooleanArrSeqTest.testcontains_val",2);
    }
    @Test
    public void testforEach_Consumer(){
        final MonitoredFunctionTest<?> test=(monitor,functionGen,functionCallType,illegalMod)->{
            if(illegalMod.expectedException == null){
                if(functionGen.expectedException == null || monitor.isEmpty()){
                    monitor.verifyForEach(functionGen,functionCallType,0);
                }else{
                    Assertions.assertThrows(functionGen.expectedException,
                            ()->monitor.verifyForEach(functionGen,functionCallType,0));
                }
            }else{
                monitor.illegalMod(illegalMod);
                Assertions.assertThrows(illegalMod.expectedException,
                        ()->monitor.verifyForEach(functionGen,functionCallType,0));
            }
        };
        test.runAllTests("PackedBooleanArrSeqTest.testforEach_Consumer",true);
    }
    @Test
    public void testget_int(){
        for(final var initParams:LIST_STRUCT_INIT_PARAMS){
            if(initParams.totalPostAlloc > 0){
                continue;
            }
            for(final var illegalMod:initParams.structType.validPreMods){
                if(illegalMod.minDepth <= initParams.preAllocs.length
                        && (initParams.checkedType.checked || illegalMod.expectedException == null)){
                    for(final var size:SHORT_SIZES){
                        TestExecutorService.submitTest(()->{
                            final var monitor=SequenceInitialization.Ascending
                                    .initialize(getMonitoredList(initParams,size),size,0);
                            monitor.illegalMod(illegalMod);
                            if(illegalMod.expectedException == null){
                                for(final var outputType:DataType.BOOLEAN.validOutputTypes()){
                                    if(initParams.checkedType.checked){
                                        Assertions.assertThrows(IndexOutOfBoundsException.class,
                                                ()->monitor.verifyGet(-1,outputType));
                                        Assertions.assertThrows(IndexOutOfBoundsException.class,
                                                ()->monitor.verifyGet(size,outputType));
                                    }
                                    for(int index=0;index < size;++index){
                                        monitor.verifyGet(index,outputType);
                                    }
                                }
                            }else{
                                for(final var outputType:DataType.BOOLEAN.validOutputTypes()){
                                    for(int tmpIndex=-1;tmpIndex <= size;++tmpIndex){
                                        final int index=tmpIndex;
                                        Assertions.assertThrows(illegalMod.expectedException,
                                                ()->monitor.verifyGet(index,outputType));
                                    }
                                }
                            }
                        });
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("PackedBooleanArrSeqTest.testget_int");
    }
    @Test
    public void testhashCode_void(){
        final ToStringAndHashCodeTest test=new ToStringAndHashCodeTest(){
            @Override
            public void callRaw(OmniCollection.OfBoolean seq){
                seq.hashCode();
            }
            @Override
            public void callVerify(MonitoredSequence<? extends OmniCollection.OfBoolean> monitor){
                monitor.verifyHashCode();
            }
        };
        test.runAllTests("PackedBooleanArrSeqTest.testhashCode_void",LIST_STRUCT_INIT_PARAMS);
    }
    @Test
    public void testindexOf_val(){
        final QueryTest<MonitoredList<OmniList.OfBoolean>> test=(monitor,queryVal,inputType,castType,modification,
                position,seqSize)->{
            int expectedIndex;
            if(position >= 0){
                expectedIndex=(int)Math.round(position * seqSize);
            }else{
                expectedIndex=-1;
            }
            Assertions.assertEquals(expectedIndex,monitor.verifyIndexOf(queryVal,inputType,castType,modification));
        };
        test.runAllTests("PackedBooleanArrSeqTest.testindexOf_val",0);
    }
    @Test
    public void testisEmpty_void(){
        final BasicTest test=MonitoredSequence::verifyIsEmpty;
        test.runAllTests("PackedBooleanArrSeqTest.testisEmpty_void",SHORT_SIZES);
    }
    @Test
    public void testiterator_void(){
        for(final var initParams:ALL_STRUCT_INIT_PARAMS){
            if(initParams.totalPostAlloc > 0){
                continue;
            }
            for(final var illegalMod:initParams.structType.validPreMods){
                if(illegalMod.minDepth <= initParams.preAllocs.length
                        && (initParams.checkedType.checked || illegalMod.expectedException == null)){
                    for(final var size:SHORT_SIZES){
                        TestExecutorService.submitTest(()->{
                            final var monitor=SequenceInitialization.Ascending
                                    .initialize(getMonitoredSequence(initParams,size),size,0);
                            try{
                                if(illegalMod.expectedException == null){
                                    monitor.getMonitoredIterator().verifyIteratorState();
                                }else{
                                    monitor.illegalMod(illegalMod);
                                    Assertions.assertThrows(illegalMod.expectedException,monitor::getMonitoredIterator);
                                }
                            }finally{
                                monitor.verifyCollectionState();
                            }
                        });
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("PackedBooleanArrSeqTest.testiterator_void");
    }
    @Test
    public void testItrclone_void(){
        for(final var size:SHORT_SIZES){
            int prevIndex=-1;
            for(final var position:POSITIONS){
                int index;
                if(position >= 0 && (index=(int)(position * size)) != prevIndex){
                    prevIndex=index;
                    for(final var initParams:ALL_STRUCT_INIT_PARAMS){
                        if(initParams.totalPostAlloc > 0){
                            continue;
                        }
                        for(final var itrType:initParams.structType.validItrTypes){
                            TestExecutorService.submitTest(()->{
                                final var seqMonitor=SequenceInitialization.Ascending
                                        .initialize(getMonitoredSequence(initParams,size),size,0);
                                final var itrMonitor=seqMonitor.getMonitoredIterator(index,itrType);
                                itrMonitor.verifyClone();
                            });
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("PackedBooleanArrSeqTest.testItrclone_void");
    }
    @Test
    public void testItrforEachRemaining_Consumer(){
        for(final int size:SHORT_SIZES){
            int prevNumToIterate=-1;
            for(final var position:POSITIONS){
                int numToIterate;
                if(position >= 0 && (numToIterate=(int)(position * size)) != prevNumToIterate){
                    prevNumToIterate=numToIterate;
                    final int numLeft=size - numToIterate;
                    for(final var initParams:ALL_STRUCT_INIT_PARAMS){
                        if(initParams.totalPostAlloc > 0){
                            continue;
                        }
                        for(final var functionCallType:DataType.BOOLEAN.validFunctionCalls){
                            for(final var itrType:initParams.structType.validItrTypes){
                                for(final var illegalMod:itrType.validPreMods){
                                    if(illegalMod.expectedException == null || initParams.checkedType.checked){
                                        for(final var functionGen:itrType.validMonitoredFunctionGens){
                                            if(initParams.checkedType.checked || size == 0
                                                    || functionGen.expectedException == null){
                                                TestExecutorService.submitTest(()->{
                                                    final var seqMonitor=SequenceInitialization.Ascending
                                                            .initialize(getMonitoredSequence(initParams,size),size,0);
                                                    final var itrMonitor=seqMonitor.getMonitoredIterator(numToIterate,
                                                            itrType);
                                                    itrMonitor.illegalMod(illegalMod);
                                                    if(illegalMod.expectedException == null || numLeft == 0){
                                                        if(functionGen.expectedException == null || numLeft == 0){
                                                            itrMonitor.verifyForEachRemaining(functionGen,
                                                                    functionCallType,0);
                                                        }else{
                                                            Assertions.assertThrows(functionGen.expectedException,
                                                                    ()->itrMonitor.verifyForEachRemaining(functionGen,
                                                                            functionCallType,0));
                                                        }
                                                    }else{
                                                        Assertions.assertThrows(illegalMod.expectedException,
                                                                ()->itrMonitor.verifyForEachRemaining(functionGen,
                                                                        functionCallType,0));
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
        TestExecutorService.completeAllTests("PackedBooleanArrSeqTest.testItrforEachRemaining_Consumer");
    }
    @Test
    public void testItrhasNext_void(){
        for(final var initParams:ALL_STRUCT_INIT_PARAMS){
            if(initParams.totalPostAlloc > 0){
                continue;
            }
            for(final var itrType:initParams.structType.validItrTypes){
                for(final int size:SHORT_SIZES){
                    TestExecutorService.submitTest(()->{
                        final var seqMonitor=SequenceInitialization.Ascending
                                .initialize(getMonitoredSequence(initParams,size),size,0);
                        final var itrMonitor=seqMonitor.getMonitoredIterator(itrType);
                        while(itrMonitor.verifyHasNext()){
                            itrMonitor.iterateForward();
                        }
                    });
                }
            }
        }
        TestExecutorService.completeAllTests("PackedBooleanArrSeqTest.testItrhasNext_void");
    }
    @Test
    public void testItrnext_void(){
        for(final var initParams:ALL_STRUCT_INIT_PARAMS){
            if(initParams.totalPostAlloc > 0){
                continue;
            }
            for(final var itrType:initParams.structType.validItrTypes){
                for(final var illegalMod:itrType.validPreMods){
                    if(illegalMod.expectedException == null || initParams.checkedType.checked){
                        for(final var outputType:DataType.BOOLEAN.validOutputTypes()){
                            for(final var size:SHORT_SIZES){
                                if(size > 0 || initParams.checkedType.checked){
                                    if(illegalMod.expectedException == null){
                                        TestExecutorService.submitTest(()->{
                                            final var seqMonitor=SequenceInitialization.Ascending
                                                    .initialize(getMonitoredSequence(initParams,size),size,0);
                                            final var itrMonitor=seqMonitor.getMonitoredIterator(itrType);
                                            while(itrMonitor.hasNext()){
                                                itrMonitor.verifyNext(outputType);
                                            }
                                            if(initParams.checkedType.checked){
                                                Assertions.assertThrows(NoSuchElementException.class,
                                                        ()->itrMonitor.verifyNext(outputType));
                                            }
                                        });
                                    }else{
                                        for(final var position:POSITIONS){
                                            if(position >= 0){
                                                TestExecutorService.submitTest(()->{
                                                    final var seqMonitor=SequenceInitialization.Ascending
                                                            .initialize(getMonitoredSequence(initParams,size),size,0);
                                                    final int index=(int)(size * position);
                                                    final var itrMonitor=seqMonitor.getMonitoredIterator(index,itrType);
                                                    itrMonitor.illegalMod(illegalMod);
                                                    Assertions.assertThrows(illegalMod.expectedException,
                                                            ()->itrMonitor.verifyNext(outputType));
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
        TestExecutorService.completeAllTests("PackedBooleanArrSeqTest.testItrnext_void");
    }
    @Test
    public void testItrremove_void(){
        for(final var initParams:ALL_STRUCT_INIT_PARAMS){
            for(final var itrType:initParams.structType.validItrTypes){
                for(final var illegalMod:itrType.validPreMods){
                    if(illegalMod.expectedException == null || initParams.checkedType.checked){
                        for(final var removeScenario:itrType.validItrRemoveScenarios){
                            if(removeScenario.expectedException == null || initParams.checkedType.checked){
                                for(final var size:SHORT_SIZES){
                                    int prevNumToIterate=-1;
                                    positionLoop:for(final var position:POSITIONS){
                                        final int numToIterate;
                                        if(position >= 0 && (numToIterate=(int)(size * position)) != prevNumToIterate){
                                            prevNumToIterate=numToIterate;
                                            switch(removeScenario){
                                            case PostInit:
                                                if(numToIterate != 0){
                                                    break positionLoop;
                                                }
                                                break;
                                            case PostNext:
                                                if(itrType.iteratorInterface != OmniListIterator.class
                                                        && (size == 0 || numToIterate == size)){
                                                    continue;
                                                }
                                            case PostAdd:
                                            case PostPrev:
                                                break;
                                            case PostRemove:
                                                if(size == 0 && itrType.iteratorInterface != OmniListIterator.class){
                                                    continue;
                                                }
                                                break;
                                            default:
                                                throw removeScenario.invalid();
                                            }
                                            TestExecutorService.submitTest(()->{
                                                final var seqMonitor=SequenceInitialization.Ascending
                                                        .initialize(getMonitoredSequence(initParams,size),size,0);
                                                final var itrMonitor=seqMonitor.getMonitoredIterator(numToIterate,
                                                        itrType);
                                                removeScenario.initialize(itrMonitor);
                                                itrMonitor.illegalMod(illegalMod);
                                                if(removeScenario.expectedException == null){
                                                    if(illegalMod.expectedException == null){
                                                        itrMonitor.verifyRemove();
                                                        switch(removeScenario){
                                                        case PostNext:{
                                                            while(itrMonitor.hasNext()){
                                                                itrMonitor.iterateForward();
                                                                itrMonitor.verifyRemove();
                                                            }
                                                            if(!(itrMonitor instanceof MonitoredList.MonitoredListIterator<?,?>)){
                                                                Assertions.assertEquals(numToIterate < 2,
                                                                        seqMonitor.isEmpty());
                                                                break;
                                                            }
                                                        }
                                                        case PostPrev:{
                                                            final var cast=(MonitoredList.MonitoredListIterator<?,?>)itrMonitor;
                                                            while(cast.hasPrevious()){
                                                                cast.iterateReverse();
                                                                cast.verifyRemove();
                                                            }
                                                            while(cast.hasNext()){
                                                                cast.iterateForward();
                                                                cast.verifyRemove();
                                                            }
                                                            Assertions.assertTrue(seqMonitor.isEmpty());
                                                            break;
                                                        }
                                                        default:
                                                            throw removeScenario.invalid();
                                                        }
                                                    }else{
                                                        Assertions.assertThrows(illegalMod.expectedException,
                                                                ()->itrMonitor.verifyRemove());
                                                    }
                                                }else{
                                                    Assertions.assertThrows(removeScenario.expectedException,
                                                            ()->itrMonitor.verifyRemove());
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
        TestExecutorService.completeAllTests("PackedBooleanArrSeqTest.testItrremove_void");
    }
    @Test
    public void testlastIndexOf_val(){
        final QueryTest<MonitoredList<? extends OmniList.OfBoolean>> test=(monitor,queryVal,inputType,castType,
                modification,position,seqSize)->{
            int expectedIndex;
            if(position >= 0){
                expectedIndex=(int)Math.round(position * seqSize);
            }else{
                expectedIndex=-1;
            }
            Assertions.assertEquals(expectedIndex,monitor.verifyLastIndexOf(queryVal,inputType,castType,modification));
        };
        test.runAllTests("PackedBooleanArrSeqTest.testlastIndexOf_val",0);
    }
    @Test
    public void testlistIterator_int(){
        for(final var initParams:LIST_STRUCT_INIT_PARAMS){
            if(initParams.totalPostAlloc > 0){
                continue;
            }
            for(final var size:SHORT_SIZES){
                final int inc=Math.max(1,size / 10);
                if(initParams.checkedType.checked || size > 0){
                    for(final var illegalMod:initParams.structType.validPreMods){
                        if(illegalMod.minDepth <= initParams.preAllocs.length
                                && (initParams.checkedType.checked || illegalMod.expectedException == null)){
                            TestExecutorService.submitTest(()->{
                                final var monitor=SequenceInitialization.Ascending
                                        .initialize(getMonitoredList(initParams,size),size,0);
                                if(illegalMod.expectedException == null){
                                    for(int index=-inc,bound=size + inc;index <= bound;index+=inc){
                                        if(index < 0 || index > size){
                                            if(initParams.checkedType.checked){
                                                final int finalIndex=index;
                                                Assertions.assertThrows(IndexOutOfBoundsException.class,()->{
                                                    try{
                                                        monitor.getMonitoredListIterator(finalIndex);
                                                    }finally{
                                                        monitor.verifyCollectionState();
                                                    }
                                                });
                                            }
                                        }else{
                                            final var itrMonitor=monitor.getMonitoredListIterator(index);
                                            monitor.verifyCollectionState();
                                            itrMonitor.verifyIteratorState();
                                        }
                                    }
                                }
                            });
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("PackedBooleanArrSeqTest.testlistIterator_int");
    }
    @Test
    public void testlistIterator_void(){
        for(final var initParams:LIST_STRUCT_INIT_PARAMS){
            if(initParams.totalPostAlloc > 0){
                continue;
            }
            for(final var illegalMod:initParams.structType.validPreMods){
                if(illegalMod.minDepth <= initParams.preAllocs.length
                        && (initParams.checkedType.checked || illegalMod.expectedException == null)){
                    for(final var size:SHORT_SIZES){
                        TestExecutorService.submitTest(()->{
                            final var monitor=SequenceInitialization.Ascending
                                    .initialize(getMonitoredList(initParams,size),size,0);
                            try{
                                if(illegalMod.expectedException == null){
                                    monitor.getMonitoredListIterator().verifyIteratorState();
                                }else{
                                    monitor.illegalMod(illegalMod);
                                    Assertions.assertThrows(illegalMod.expectedException,
                                            monitor::getMonitoredListIterator);
                                }
                            }finally{
                                monitor.verifyCollectionState();
                            }
                        });
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("PackedBooleanArrSeqTest.testlistIterator_void");
    }
    @Test
    public void testListItradd_val(){
        for(final var position:POSITIONS){
            if(position >= 0){
                for(final var initParams:LIST_STRUCT_INIT_PARAMS){
                    final var itrType=initParams.structType == StructType.PackedBooleanArrList
                            ?IteratorType.BidirectionalItr
                            :IteratorType.SubBidirectionalItr;
                    for(final var illegalMod:itrType.validPreMods){
                        if(illegalMod.expectedException == null || initParams.checkedType.checked){
                            for(final var inputType:DataType.BOOLEAN.mayBeAddedTo()){
                                for(final var functionCallType:inputType.validFunctionCalls){
                                    for(final var initCapacity:INIT_CAPACITIES){
                                        TestExecutorService.submitTest(()->{
                                            final var seqMonitor=getMonitoredList(initParams,initCapacity);
                                            final var itrMonitor=seqMonitor.getMonitoredListIterator();
                                            for(int i=0;;){
                                                itrMonitor.verifyAdd(inputType.convertValUnchecked(i),inputType,
                                                        functionCallType);
                                                if(++i == 258){
                                                    break;
                                                }
                                                final double dI=i;
                                                double currPosition;
                                                while((currPosition=itrMonitor.nextIndex() / dI) < position
                                                        && itrMonitor.hasNext()){
                                                    itrMonitor.iterateForward();
                                                }
                                                while(currPosition > position && itrMonitor.hasPrevious()){
                                                    itrMonitor.iterateReverse();
                                                    currPosition=itrMonitor.nextIndex() / dI;
                                                }
                                            }
                                            if(illegalMod.expectedException != null){
                                                itrMonitor.illegalMod(illegalMod);
                                                Assertions.assertThrows(illegalMod.expectedException,
                                                        ()->itrMonitor.verifyAdd(inputType.convertValUnchecked(258),
                                                                inputType,functionCallType));
                                                // for good measure, do it again with an
                                                // empty list
                                                final var itrMonitor2=getMonitoredList(initParams,initCapacity)
                                                        .getMonitoredListIterator();
                                                itrMonitor2.illegalMod(illegalMod);
                                                Assertions.assertThrows(illegalMod.expectedException,
                                                        ()->itrMonitor2.verifyAdd(inputType.convertValUnchecked(258),
                                                                inputType,functionCallType));
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
        TestExecutorService.completeAllTests("PackedBooleanArrSeqTest.testListItradd_val");
    }
    @Test
    public void testListItrhasPrevious_void(){
        for(final var initParams:LIST_STRUCT_INIT_PARAMS){
            if(initParams.totalPostAlloc > 0){
                continue;
            }
            for(final var size:SHORT_SIZES){
                TestExecutorService.submitTest(()->{
                    final var seqMonitor=SequenceInitialization.Ascending.initialize(getMonitoredList(initParams,size),
                            size,0);
                    final var itrMonitor=seqMonitor.getMonitoredListIterator(size);
                    while(itrMonitor.verifyHasPrevious()){
                        itrMonitor.iterateReverse();
                    }
                });
            }
        }
        TestExecutorService.completeAllTests("PackedBooleanArrSeqTest.testListItrhasPrevious_void");
    }
    @Test
    public void testListItrnextIndex_void(){
        for(final var initParams:LIST_STRUCT_INIT_PARAMS){
            if(initParams.totalPostAlloc > 0){
                continue;
            }
            for(final var size:SHORT_SIZES){
                TestExecutorService.submitTest(()->{
                    final var seqMonitor=SequenceInitialization.Ascending.initialize(getMonitoredList(initParams,size),
                            size,0);
                    final var itrMonitor=seqMonitor.getMonitoredListIterator();
                    while(itrMonitor.verifyNextIndex() < size){
                        itrMonitor.iterateForward();
                    }
                });
            }
        }
        TestExecutorService.completeAllTests("PackedBooleanArrSeqTest.testListItrnextIndex_void");
    }
    @Test
    public void testListItrprevious_void(){
        for(final var initParams:LIST_STRUCT_INIT_PARAMS){
            if(initParams.totalPostAlloc > 0){
                continue;
            }
            for(final var size:SHORT_SIZES){
                if(size > 0 || initParams.checkedType.checked){
                    final var itrType=initParams.structType == StructType.PackedBooleanArrList
                            ?IteratorType.BidirectionalItr
                            :IteratorType.SubBidirectionalItr;
                    for(final var illegalMod:itrType.validPreMods){
                        if(illegalMod.expectedException == null || initParams.checkedType.checked){
                            for(final var outputType:DataType.BOOLEAN.validOutputTypes()){
                                if(illegalMod.expectedException == null){
                                    final var seqMonitor=SequenceInitialization.Ascending
                                            .initialize(getMonitoredList(initParams,size),size,0);
                                    final var itrMonitor=seqMonitor.getMonitoredListIterator(size);
                                    itrMonitor.illegalMod(illegalMod);
                                    while(itrMonitor.hasPrevious()){
                                        itrMonitor.verifyPrevious(outputType);
                                    }
                                    if(initParams.checkedType.checked){
                                        Assertions.assertThrows(NoSuchElementException.class,
                                                ()->itrMonitor.verifyPrevious(outputType));
                                    }
                                }else{
                                    for(final var position:POSITIONS){
                                        if(position >= 0){
                                            TestExecutorService.submitTest(()->{
                                                final var seqMonitor=SequenceInitialization.Ascending
                                                        .initialize(getMonitoredList(initParams,size),size,0);
                                                final int index=(int)(size * position);
                                                final var itrMonitor=seqMonitor.getMonitoredListIterator(index);
                                                itrMonitor.illegalMod(illegalMod);
                                                Assertions.assertThrows(illegalMod.expectedException,
                                                        ()->itrMonitor.verifyPrevious(outputType));
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
        TestExecutorService.completeAllTests("PackedBooleanArrSeqTest.testListItrprevious_void");
    }
    @Test
    public void testListItrpreviousIndex_void(){
        for(final var initParams:LIST_STRUCT_INIT_PARAMS){
            if(initParams.totalPostAlloc > 0){
                continue;
            }
            for(final var size:SHORT_SIZES){
                TestExecutorService.submitTest(()->{
                    final var seqMonitor=SequenceInitialization.Ascending.initialize(getMonitoredList(initParams,size),
                            size,0);
                    final var itrMonitor=seqMonitor.getMonitoredListIterator(size);
                    while(itrMonitor.verifyPreviousIndex() > 0){
                        itrMonitor.iterateReverse();
                    }
                });
            }
        }
        TestExecutorService.completeAllTests("PackedBooleanArrSeqTest.testListItrpreviousIndex_void");
    }
    @Test
    public void testListItrset_val(){
        for(final var initParams:LIST_STRUCT_INIT_PARAMS){
            if(initParams.totalPostAlloc > 0){
                continue;
            }
            final var itrType=initParams.structType == StructType.PackedBooleanArrList?IteratorType.BidirectionalItr
                    :IteratorType.SubBidirectionalItr;
            for(final var illegalMod:itrType.validPreMods){
                if(illegalMod.expectedException == null || initParams.checkedType.checked){
                    for(final var removeScenario:itrType.validItrRemoveScenarios){
                        if(removeScenario.expectedException == null || initParams.checkedType.checked){
                            for(final var inputType:DataType.BOOLEAN.mayBeAddedTo()){
                                for(final var functionCallType:inputType.validFunctionCalls){
                                    for(final int size:SHORT_SIZES){
                                        if(removeScenario.expectedException == null
                                                && illegalMod.expectedException == null){
                                            TestExecutorService.submitTest(()->{
                                                final var seqMonitor=SequenceInitialization.Ascending
                                                        .initialize(getMonitoredList(initParams,size),size,0);
                                                final var itrMonitor=seqMonitor.getMonitoredListIterator();
                                                removeScenario.initialize(itrMonitor);
                                                int i=1;
                                                itrMonitor.verifySet(inputType.convertValUnchecked(i),inputType,
                                                        functionCallType);
                                                while(itrMonitor.hasNext()){
                                                    itrMonitor.iterateForward();
                                                    itrMonitor.verifySet(inputType.convertValUnchecked(++i),inputType,
                                                            functionCallType);
                                                }
                                            });
                                        }else{
                                            for(final var position:POSITIONS){
                                                if(position >= 0){
                                                    TestExecutorService.submitTest(()->{
                                                        final var seqMonitor=SequenceInitialization.Ascending
                                                                .initialize(getMonitoredList(initParams,size),size,0);
                                                        final int index=(int)(size * position);
                                                        final var itrMonitor=seqMonitor.getMonitoredListIterator(index);
                                                        removeScenario.initialize(itrMonitor);
                                                        itrMonitor.illegalMod(illegalMod);
                                                        if(removeScenario.expectedException == null){
                                                            Assertions.assertThrows(illegalMod.expectedException,
                                                                    ()->itrMonitor.verifySet(
                                                                            inputType.convertValUnchecked(1),inputType,
                                                                            functionCallType));
                                                        }else{
                                                            Assertions.assertThrows(removeScenario.expectedException,
                                                                    ()->itrMonitor.verifySet(
                                                                            inputType.convertValUnchecked(1),inputType,
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
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("PackedBooleanArrSeqTest.testListItrset_val");
    }
    @Test
    public void testMASSIVEtoString(){
        int seqSize;
        long[] words;
        for(int i=(words=new long[((seqSize=DataType.BOOLEAN.massiveToStringThreshold + 1) - 1 >> 6)
                + 1]).length;--i >= 0;){
            words[i]=0xAAAAAAAAAAAAAAAAL;
        }
        final var uncheckedStack=new PackedBooleanArrSeq.UncheckedStack(seqSize,words);
        final var checkedStack=new PackedBooleanArrSeq.CheckedStack(seqSize,words);
        final var uncheckedList=new PackedBooleanArrSeq.UncheckedList(seqSize,words);
        final var checkedList=new PackedBooleanArrSeq.CheckedList(seqSize,words);
        verifyMASSIVEToStringHelper(uncheckedStack.toString(),seqSize,
                "PackedBooleanArrSeqTest.UncheckedStack.testMASSIVEtoString",false);
        verifyMASSIVEToStringHelper(checkedStack.toString(),seqSize,
                "PackedBooleanArrSeqTest.CheckedStack.testMASSIVEtoString",false);
        verifyMASSIVEToStringHelper(uncheckedList.toString(),seqSize,
                "PackedBooleanArrSeqTest.UncheckedList.testMASSIVEtoString",true);
        verifyMASSIVEToStringHelper(checkedList.toString(),seqSize,
                "PackedBooleanArrSeqTest.CheckedList.testMASSIVEtoString",true);
        verifyMASSIVEToStringHelper(uncheckedList.subList(0,seqSize).toString(),seqSize,
                "PackedBooleanArrSeqTest.UncheckedSubList.testMASSIVEtoString",true);
        verifyMASSIVEToStringHelper(checkedList.subList(0,seqSize).toString(),seqSize,
                "PackedBooleanArrSeqTest.CheckedSubList.testMASSIVEtoString",true);
    }
    @Test
    public void testpeek_void(){
        for(final var initParams:STACK_STRUCT_INIT_PARAMS){
            TestExecutorService.submitTest(()->{
                final var monitor=getMonitoredStack(initParams,258);
                for(int i=0;;++i){
                    for(final var outputType:DataType.BOOLEAN.validOutputTypes()){
                        monitor.verifyPeek(outputType);
                    }
                    if(i == 258){
                        break;
                    }
                    monitor.add(i);
                }
            });
        }
        TestExecutorService.completeAllTests("PackedBooleanArrSeqTest.testpeek_void");
    }
    @Test
    public void testpoll_void(){
        for(final var initParams:STACK_STRUCT_INIT_PARAMS){
            for(final var outputType:DataType.BOOLEAN.validOutputTypes()){
                TestExecutorService.submitTest(()->{
                    final var monitor=SequenceInitialization.Ascending.initialize(getMonitoredStack(initParams,258),258,
                            0);
                    for(int i=0;;++i){
                        monitor.verifyPoll(outputType);
                        if(i > 258){
                            break;
                        }
                    }
                });
            }
        }
        TestExecutorService.completeAllTests("PackedBooleanArrSeqTest.testpoll_void");
    }
    @Test
    public void testpop_void(){
        for(final var initParams:STACK_STRUCT_INIT_PARAMS){
            for(final var size:SHORT_SIZES){
                if(size > 0 || initParams.checkedType.checked){
                    for(final var outputType:DataType.BOOLEAN.validOutputTypes()){
                        TestExecutorService.submitTest(()->{
                            final var monitor=SequenceInitialization.Ascending
                                    .initialize(getMonitoredStack(initParams,size),size,0);
                            for(int i=0;i < size;++i){
                                monitor.verifyPop(outputType);
                            }
                            if(initParams.checkedType.checked){
                                Assertions.assertThrows(NoSuchElementException.class,()->monitor.verifyPop(outputType));
                            }
                        });
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("PackedBooleanArrSeqTest.testpop_void");
    }
    @Test
    public void testpush_val(){
        for(final var initParams:STACK_STRUCT_INIT_PARAMS){
            for(final var inputType:DataType.BOOLEAN.mayBeAddedTo()){
                for(final var functionCallType:inputType.validFunctionCalls){
                    for(final var initCap:INIT_CAPACITIES){
                        TestExecutorService.submitTest(()->{
                            final var monitor=getMonitoredStack(initParams,initCap);
                            for(int i=0;i < 258;++i){
                                monitor.verifyPush(inputType.convertValUnchecked(i),inputType,functionCallType);
                            }
                        });
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("PackedBooleanArrSeqTest.testpush_val");
    }
    @Test
    public void testput_intval(){
        for(final var initParams:LIST_STRUCT_INIT_PARAMS){
            if(initParams.totalPostAlloc > 0){
                continue;
            }
            for(final var illegalMod:initParams.structType.validPreMods){
                if(illegalMod.minDepth <= initParams.preAllocs.length
                        && (initParams.checkedType.checked || illegalMod.expectedException == null)){
                    for(final var size:SHORT_SIZES){
                        TestExecutorService.submitTest(()->{
                            final var monitor=SequenceInitialization.Ascending
                                    .initialize(getMonitoredList(initParams,size),size,0);
                            if(illegalMod.expectedException == null){
                                for(final var inputType:DataType.BOOLEAN.mayBeAddedTo()){
                                    for(final var functionCallType:inputType.validFunctionCalls){
                                        if(initParams.checkedType.checked){
                                            Assertions.assertThrows(IndexOutOfBoundsException.class,
                                                    ()->monitor.verifyPut(-1,inputType.convertValUnchecked(0),inputType,
                                                            functionCallType));
                                            Assertions.assertThrows(IndexOutOfBoundsException.class,
                                                    ()->monitor.verifyPut(size,inputType.convertValUnchecked(size + 1),
                                                            inputType,functionCallType));
                                        }
                                        for(int index=0;index < size;++index){
                                            monitor.verifyPut(index,inputType.convertValUnchecked(index + 1),inputType,
                                                    functionCallType);
                                        }
                                    }
                                }
                            }else{
                                monitor.illegalMod(illegalMod);
                                for(final var inputType:DataType.BOOLEAN.mayBeAddedTo()){
                                    for(final var functionCallType:inputType.validFunctionCalls){
                                        for(int tmpIndex=-1;tmpIndex <= size;++tmpIndex){
                                            final int index=tmpIndex;
                                            Assertions.assertThrows(illegalMod.expectedException,
                                                    ()->monitor.verifyPut(index,
                                                            inputType.convertValUnchecked(index + 1),inputType,
                                                            functionCallType));
                                        }
                                    }
                                }
                            }
                        });
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("PackedBooleanArrSeqTest.testput_intval");
    }
    @Test
    public void testReadAndWrite(){
        TestExecutorService.setNumWorkers(1);
        final MonitoredFunctionTest<?> test=(monitor,functionGen,functionCallType,illegalMod)->{
            if(illegalMod.expectedException == null){
                if(functionGen.expectedException == null){
                    Assertions.assertDoesNotThrow(()->monitor.verifyReadAndWrite(functionGen));
                }else{
                    Assertions.assertThrows(functionGen.expectedException,()->monitor.verifyReadAndWrite(functionGen));
                }
            }else{
                monitor.illegalMod(illegalMod);
                Assertions.assertThrows(illegalMod.expectedException,()->monitor.verifyReadAndWrite(functionGen));
            }
        };
        test.runAllTests("PackedBooleanArrSeqTest.testReadAndWrite",true);
    }
    @Test
    public void testremoveAt_int(){
        for(final var initParams:LIST_STRUCT_INIT_PARAMS){
            for(final var illegalMod:initParams.structType.validPreMods){
                if(illegalMod.minDepth <= initParams.preAllocs.length
                        && (initParams.checkedType.checked || illegalMod.expectedException == null)){
                    for(final var position:illegalMod.expectedException == null?NON_THROWING_REMOVE_AT_POSITIONS
                            :THROWING_REMOVE_AT_POSITIONS){
                        if(initParams.checkedType.checked || position >= 0 && position <= 2){
                            for(final int size:SHORT_SIZES){
                                switch(position){
                                case 0:
                                    if(size < 1){
                                        continue;
                                    }
                                    break;
                                case 1:
                                    if(size < 3){
                                        continue;
                                    }
                                    break;
                                case 2:
                                    if(size < 2){
                                        continue;
                                    }
                                default:
                                }
                                for(final var outputType:DataType.BOOLEAN.validOutputTypes()){
                                    if(outputType != DataType.BOOLEAN){
                                        if(size > 1 || position > 0 || initParams.totalPreAlloc > 0
                                                || initParams.totalPostAlloc > 0){
                                            break;
                                        }
                                    }
                                    TestExecutorService.submitTest(()->{
                                        final var monitor=SequenceInitialization.Ascending
                                                .initialize(getMonitoredList(initParams,size),size,0);
                                        if(illegalMod.expectedException == null){
                                            switch(position){
                                            case -1:
                                                Assertions.assertThrows(IndexOutOfBoundsException.class,
                                                        ()->monitor.verifyRemoveAt(-1,outputType));
                                                break;
                                            case 3:
                                                Assertions.assertThrows(IndexOutOfBoundsException.class,
                                                        ()->monitor.verifyRemoveAt(size,outputType));
                                                break;
                                            case 0:
                                                for(int i=0;i < size;++i){
                                                    monitor.verifyRemoveAt(0,outputType);
                                                }
                                                break;
                                            case 1:{
                                                for(int i=0;i < size;++i){
                                                    monitor.verifyRemoveAt((size - i) / 2,outputType);
                                                }
                                                break;
                                            }
                                            case 2:
                                                for(int i=0;i < size;++i){
                                                    monitor.verifyRemoveAt(size - i - 1,outputType);
                                                }
                                                break;
                                            default:
                                                throw new UnsupportedOperationException("Unknown position " + position);
                                            }
                                        }else{
                                            monitor.illegalMod(illegalMod);
                                            for(int tmpIndex=-1;tmpIndex <= size;++tmpIndex){
                                                final int index=tmpIndex;
                                                Assertions.assertThrows(illegalMod.expectedException,
                                                        ()->monitor.verifyRemoveAt(index,outputType));
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("PackedBooleanArrSeqTest.testremoveAt_int");
    }
    @Test
    public void testremoveIf_Predicate(){
        for(final var initParams:ALL_STRUCT_INIT_PARAMS){
            for(final var filterGen:initParams.structType.validMonitoredRemoveIfPredicateGens){
                for(final var size:SHORT_SIZES){
                    if((size > 65 || initParams.totalPreAlloc > 0 || initParams.totalPostAlloc > 0)
                            && filterGen.expectedException != null){
                        break;
                    }
                    if(size == 0 || initParams.checkedType.checked || filterGen.expectedException == null){
                        final int initValBound;
                        final int periodBound;
                        initValBound=size == 0?0:1;
                        periodBound=Math.min(Math.max(0,size - 1),65);
                        for(final var functionCallType:DataType.BOOLEAN.validFunctionCalls){
                            if((size > 65 || initParams.totalPreAlloc > 0 || initParams.totalPostAlloc > 0)
                                    && functionCallType.boxed){
                                continue;
                            }
                            for(final var illegalMod:initParams.structType.validPreMods){
                                if(illegalMod.expectedException != null && size > 65){
                                    continue;
                                }
                                if(illegalMod.minDepth <= initParams.preAllocs.length
                                        && (initParams.checkedType.checked || illegalMod.expectedException == null)){
                                    for(int tmpInitVal=0;tmpInitVal <= initValBound;++tmpInitVal){
                                        final int initVal=tmpInitVal;
                                        for(int tmpPeriod=0;tmpPeriod <= periodBound;++tmpPeriod){
                                            final int period=tmpPeriod;
                                            TestExecutorService.submitTest(()->{
                                                final var monitor=SequenceInitialization.Ascending.initialize(
                                                        getMonitoredSequence(initParams,size),size,initVal,period);
                                                final var filter=filterGen.getMonitoredRemoveIfPredicate(monitor);
                                                if(illegalMod.expectedException == null){
                                                    if(filterGen.expectedException == null || size == 0){
                                                        monitor.verifyRemoveIf(filter,functionCallType);
                                                    }else{
                                                        Assertions.assertThrows(filterGen.expectedException,
                                                                ()->monitor.verifyRemoveIf(filter,functionCallType));
                                                    }
                                                }else{
                                                    monitor.illegalMod(illegalMod);
                                                    Assertions.assertThrows(illegalMod.expectedException,
                                                            ()->monitor.verifyRemoveIf(filter,functionCallType));
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
        TestExecutorService.completeAllTests("PackedBooleanArrSeqTest.testremoveIf_Predicate");
    }
    @Test
    public void testremoveVal_val(){
        final QueryTest<?> test=new QueryTest<MonitoredSequence<OmniCollection.OfBoolean>>(){
            @Override
            public void callAndVerifyResult(MonitoredSequence<OmniCollection.OfBoolean> monitor,QueryVal queryVal,
                    DataType inputType,QueryCastType castType,QueryValModification modification,double position,
                    int seqSize){
                Assertions.assertEquals(position >= 0,
                        monitor.verifyRemoveVal(queryVal,inputType,castType,modification));
            }
            @Override
            public int getMaxPostAlloc(){
                return Integer.MAX_VALUE;
            }
        };
        test.runAllTests("PackedBooleanArrSeqTest.testremoveVal_val",2);
    }
    @Test
    public void testreplaceAll_UnaryOperator(){
        final MonitoredFunctionTest<MonitoredList<? extends OmniList.OfBoolean>> test=(monitor,functionGen,
                functionCallType,illegalMod)->{
            if(illegalMod.expectedException == null){
                if(functionGen.expectedException == null || monitor.isEmpty()){
                    monitor.verifyReplaceAll(functionGen,functionCallType,0);
                }else{
                    Assertions.assertThrows(functionGen.expectedException,
                            ()->monitor.verifyReplaceAll(functionGen,functionCallType,0));
                }
            }else{
                monitor.illegalMod(illegalMod);
                Assertions.assertThrows(illegalMod.expectedException,
                        ()->monitor.verifyReplaceAll(functionGen,functionCallType,0));
            }
        };
        test.runAllTests("PackedBooleanArrSeqTest.testreplaceAll_UnaryOperator",false);
    }
    @Test
    public void testsearch_val(){
        final QueryTest<PackedBooleanArrStackMonitor> test=(monitor,queryVal,inputType,castType,modification,position,
                seqSize)->{
            int expectedIndex;
            if(position >= 0){
                int size;
                expectedIndex=(size=monitor.size())
                        - monitor.findRemoveValIndex(queryVal.getInputVal(inputType,modification),inputType,0,size);
            }else{
                expectedIndex=-1;
            }
            Assertions.assertEquals(expectedIndex,monitor.verifySearch(queryVal,inputType,castType,modification));
        };
        test.runAllTests("PackedBooleanArrSeqTest.testsearch",1);
    }
    @Test
    public void testset_intval(){
        for(final var initParams:LIST_STRUCT_INIT_PARAMS){
            if(initParams.totalPostAlloc > 0){
                continue;
            }
            for(final var illegalMod:initParams.structType.validPreMods){
                if(illegalMod.minDepth <= initParams.preAllocs.length
                        && (initParams.checkedType.checked || illegalMod.expectedException == null)){
                    for(final int size:SHORT_SIZES){
                        TestExecutorService.submitTest(()->{
                            final var monitor=SequenceInitialization.Ascending
                                    .initialize(getMonitoredList(initParams,size),size,0);
                            if(illegalMod.expectedException == null){
                                for(final var functionCallType:DataType.BOOLEAN.validFunctionCalls){
                                    if(initParams.checkedType.checked){
                                        Assertions.assertThrows(IndexOutOfBoundsException.class,
                                                ()->monitor.verifySet(-1,DataType.BOOLEAN.convertValUnchecked(0),
                                                        functionCallType));
                                        Assertions.assertThrows(IndexOutOfBoundsException.class,
                                                ()->monitor.verifySet(size,
                                                        DataType.BOOLEAN.convertValUnchecked(size + 1),
                                                        functionCallType));
                                    }
                                    for(int index=0;index < size;++index){
                                        monitor.verifySet(index,DataType.BOOLEAN.convertValUnchecked(index + 1),
                                                functionCallType);
                                    }
                                }
                            }else{
                                monitor.illegalMod(illegalMod);
                                for(final var functionCallType:DataType.BOOLEAN.validFunctionCalls){
                                    for(int tmpIndex=-1;tmpIndex <= size;++tmpIndex){
                                        final int index=tmpIndex;
                                        Assertions.assertThrows(illegalMod.expectedException,
                                                ()->monitor.verifySet(index,
                                                        DataType.BOOLEAN.convertValUnchecked(index + 1),
                                                        functionCallType));
                                    }
                                }
                            }
                        });
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("PackedBooleanArrSeqTest.testset_intval");
    }
    @Test
    public void testsize_void(){
        final BasicTest test=MonitoredSequence::verifySize;
        test.runAllTests("PackedBooleanArrSeqTest.testsize_void",SHORT_SIZES);
    }
    @Test
    public void testsort_Comparator(){
        final SortTest test=(monitor,comparatorGen,functionCallType)->{
            DataType.BOOLEAN.callStableSort(comparatorGen.getMonitoredComparator(monitor),monitor.getCollection(),
                    functionCallType);
            comparatorGen.assertSorted(monitor);
            monitor.verifyCollectionState();
        };
        test.runAllTests("PackedBooleanArrSeqTest.testsort_Comparator",true);
    }
    @Test
    public void teststableAscendingSort_void(){
        final SortTest test=(monitor,comparatorGen,functionCallType)->{
            monitor.getCollection().stableAscendingSort();
            comparatorGen.assertSorted(monitor);
            monitor.verifyCollectionState();
        };
        test.runAllTests("PackedBooleanArrSeqTest.teststableAscendingSort_void",false);
    }
    @Test
    public void teststableDescendingSort_void(){
        final SortTest test=(monitor,comparatorGen,functionCallType)->{
            monitor.getCollection().stableDescendingSort();
            comparatorGen.assertReverseSorted(monitor);
            monitor.verifyCollectionState();
        };
        test.runAllTests("PackedBooleanArrSeqTest.teststableDescendingSort_void",false);
    }
    @Test
    public void testsubList_intint(){
        for(final var initParams:LIST_STRUCT_INIT_PARAMS){
            if(initParams.totalPostAlloc > 0){
                continue;
            }
            for(final int size:SHORT_SIZES){
                final int inc=Math.max(1,size / 10);
                for(int tmpFromIndex=-inc,fromBound=size + 2 * inc;tmpFromIndex <= fromBound;tmpFromIndex+=inc){
                    if(initParams.checkedType.checked || tmpFromIndex >= 0 && tmpFromIndex <= size){
                        final int fromIndex=tmpFromIndex;
                        for(int tmpToIndex=-(2 * inc),toBound=size + inc;tmpToIndex < toBound;tmpToIndex+=inc){
                            if(initParams.checkedType.checked || tmpToIndex >= fromIndex && tmpToIndex <= size){
                                final int toIndex=tmpToIndex;
                                for(final var illegalMod:initParams.structType.validPreMods){
                                    if(illegalMod.minDepth <= initParams.preAllocs.length
                                            && (initParams.checkedType.checked
                                                    || illegalMod.expectedException == null)){
                                        TestExecutorService.submitTest(()->{
                                            final var rootMonitor=SequenceInitialization.Ascending
                                                    .initialize(getMonitoredList(initParams,size),size,0);
                                            if(illegalMod.expectedException == null){
                                                if(fromIndex >= 0 && toIndex >= fromIndex && toIndex <= size){
                                                    rootMonitor.getMonitoredSubList(fromIndex,toIndex)
                                                            .verifyCollectionState();
                                                }else{
                                                    Assertions.assertThrows(IndexOutOfBoundsException.class,()->{
                                                        try{
                                                            rootMonitor.getMonitoredSubList(fromIndex,toIndex);
                                                        }finally{
                                                            rootMonitor.verifyCollectionState();
                                                        }
                                                    });
                                                }
                                            }else{
                                                rootMonitor.illegalMod(illegalMod);
                                                Assertions.assertThrows(illegalMod.expectedException,()->{
                                                    try{
                                                        rootMonitor.getMonitoredSubList(fromIndex,toIndex);
                                                    }finally{
                                                        rootMonitor.verifyCollectionState();
                                                    }
                                                });
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
        TestExecutorService.completeAllTests("PackedBooleanArrSeqTest.testsubList_intint");
    }
    @Test
    public void testtoArray_IntFunction(){
        final MonitoredFunctionTest<?> test=(monitor,functionGen,functionCallType,illegalMod)->{
            if(illegalMod.expectedException == null){
                if(functionGen.expectedException == null){
                    monitor.verifyToArray(functionGen);
                }else{
                    Assertions.assertThrows(functionGen.expectedException,()->monitor.verifyToArray(functionGen));
                }
            }else{
                monitor.illegalMod(illegalMod);
                Assertions.assertThrows(illegalMod.expectedException,()->monitor.verifyToArray(functionGen));
            }
        };
        test.runAllTests("PackedBooleanArrSeqTest.testtoArray_IntFunction",true);
    }
    @Test
    public void testtoArray_ObjectArray(){
        for(final var initParams:ALL_STRUCT_INIT_PARAMS){
            if(initParams.totalPostAlloc > 0){
                continue;
            }
            for(final var illegalMod:initParams.structType.validPreMods){
                if(illegalMod.minDepth <= initParams.preAllocs.length
                        && (initParams.checkedType.checked || illegalMod.expectedException == null)){
                    for(final int size:SHORT_SIZES){
                        for(int tmpArrSize=0,tmpArrSizeBound=size + 5;tmpArrSize <= tmpArrSizeBound;++tmpArrSize){
                            final int arrSize=tmpArrSize;
                            TestExecutorService.submitTest(()->{
                                final var monitor=SequenceInitialization.Ascending
                                        .initialize(getMonitoredSequence(initParams,size),size,0);
                                if(illegalMod.expectedException == null){
                                    monitor.verifyToArray(new Object[arrSize]);
                                }else{
                                    monitor.illegalMod(illegalMod);
                                    Assertions.assertThrows(illegalMod.expectedException,
                                            ()->monitor.verifyToArray(new Object[arrSize]));
                                }
                            });
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("PackedBooleanArrSeqTest.testtoArray_ObjectArray");
    }
    @Test
    public void testtoArray_void(){
        for(final var initParams:ALL_STRUCT_INIT_PARAMS){
            if(initParams.totalPostAlloc > 0){
                continue;
            }
            for(final var illegalMod:initParams.structType.validPreMods){
                if(illegalMod.minDepth <= initParams.preAllocs.length
                        && (initParams.checkedType.checked || illegalMod.expectedException == null)){
                    for(final int size:SHORT_SIZES){
                        TestExecutorService.submitTest(()->{
                            final var monitor=SequenceInitialization.Ascending
                                    .initialize(getMonitoredSequence(initParams,size),size,0);
                            if(illegalMod.expectedException == null){
                                for(final var outputType:DataType.BOOLEAN.validOutputTypes()){
                                    outputType.verifyToArray(monitor);
                                }
                            }else{
                                monitor.illegalMod(illegalMod);
                                for(final var outputType:DataType.BOOLEAN.validOutputTypes()){
                                    Assertions.assertThrows(illegalMod.expectedException,
                                            ()->outputType.verifyToArray(monitor));
                                }
                            }
                        });
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("PackedBooleanArrSeqTest.testtoArray_void");
    }
    @Test
    public void testtoString_void(){
        final ToStringAndHashCodeTest test=new ToStringAndHashCodeTest(){
            @Override
            public void callRaw(OmniCollection.OfBoolean seq){
                seq.toString();
            }
            @Override
            public void callVerify(MonitoredSequence<? extends OmniCollection.OfBoolean> monitor){
                monitor.verifyToString();
            }
        };
        test.runAllTests("PackedBooleanArrSeqTest.testtoString_void",ALL_STRUCT_INIT_PARAMS);
    }
    private static class PackedBooleanArrListMonitor
            extends
            AbstractPackedBooleanArrSeqMonitor<PackedBooleanArrSeq.UncheckedList>
            implements
            MonitoredList<PackedBooleanArrSeq.UncheckedList>{
        PackedBooleanArrListMonitor(CheckedType checkedType){
            super(checkedType);
        }
        PackedBooleanArrListMonitor(CheckedType checkedType,Collection<?> collection,Class<? extends Collection<?>> collectionClass){
            super(checkedType,collection,collectionClass);
        }
        PackedBooleanArrListMonitor(CheckedType checkedType,int initCapacity){
            super(checkedType,initCapacity);
        }
        PackedBooleanArrListMonitor(SequenceInitParams initParams){
            super(initParams.checkedType);
        }
        PackedBooleanArrListMonitor(SequenceInitParams initParams,int initCap){
            super(initParams.checkedType,initCap);
        }
        @Override
        public MonitoredIterator<? extends OmniIterator<?>,UncheckedList> getMonitoredIterator(){
            return new ItrMonitor();
        }
        @Override
        public MonitoredIterator<? extends OmniIterator<?>,UncheckedList> getMonitoredIterator(int index,
                IteratorType itrType){
            switch(itrType){
            case AscendingItr:
                return getMonitoredIterator(index);
            case BidirectionalItr:
                return getMonitoredListIterator(index);
            default:
                throw itrType.invalid();
            }
        }
        @Override
        public MonitoredIterator<? extends OmniIterator<?>,UncheckedList> getMonitoredIterator(IteratorType itrType){
            switch(itrType){
            case AscendingItr:
                return getMonitoredIterator();
            case BidirectionalItr:
                return getMonitoredListIterator();
            default:
                throw itrType.invalid();
            }
        }
        @Override
        public MonitoredListIterator<? extends OmniListIterator<?>,UncheckedList> getMonitoredListIterator(){
            return new ListItrMonitor();
        }
        @Override
        public MonitoredListIterator<? extends OmniListIterator<?>,UncheckedList> getMonitoredListIterator(int index){
            return new ListItrMonitor(index);
        }
        @Override
        public MonitoredList<?> getMonitoredSubList(int fromIndex,int toIndex){
            return new PackedBooleanArrSubListMonitor<>(this,fromIndex,toIndex);
        }
        @Override
        public StructType getStructType(){
            return StructType.PackedBooleanArrList;
        }
        @Override
        public void modCollection(){
            ++((PackedBooleanArrSeq.CheckedList)seq).modCount;
            ++expectedModCount;
        }
        @Override
        public Object removeFirst(){
            final Object result=seq.removeBooleanAt(0);
            super.updateRemoveIndexState(0);
            return result;
        }
        @Override
        int findRemoveValIndex(Object inputVal,DataType inputType,int fromIndex,int toIndex){
            boolean inputCast;
            switch(inputType){
            case BOOLEAN:
                inputCast=(boolean)inputVal;
                break;
            case BYTE:
                inputCast=(byte)inputVal == 1;
                break;
            case CHAR:
                inputCast=(char)inputVal == 1;
                break;
            case SHORT:
                inputCast=(short)inputVal == 1;
                break;
            case INT:
                inputCast=(int)inputVal == 1;
                break;
            case LONG:
                inputCast=(long)inputVal == 1L;
                break;
            case FLOAT:
                inputCast=(float)inputVal == 1F;
                break;
            case DOUBLE:
                inputCast=(double)inputVal == 1D;
                break;
            default:
                throw inputType.invalid();
            }
            final var words=expectedWords;
            var word=words[fromIndex >> 6];
            if(inputCast){
                for(;;){
                    if((word & 1L << fromIndex) != 0){
                        return fromIndex;
                    }
                    if((++fromIndex & 63) == 0){
                        word=words[fromIndex >> 6];
                    }
                }
            }else{
                for(;;){
                    if((word & 1L << fromIndex) == 0){
                        return fromIndex;
                    }
                    if((++fromIndex & 63) == 0){
                        word=words[fromIndex >> 6];
                    }
                }
            }
        }
        @Override
        UncheckedList initSeq(){
            if(checkedType.checked){
                return new PackedBooleanArrSeq.CheckedList();
            }
            return new PackedBooleanArrSeq.UncheckedList();
        }
        @Override
        UncheckedList initSeq(int initCapacity){
            if(checkedType.checked){
                return new PackedBooleanArrSeq.CheckedList(initCapacity);
            }
            return new PackedBooleanArrSeq.UncheckedList(initCapacity);
        }
        @Override
        UncheckedList initSeq(Collection<?> collection,Class<? extends Collection<?>> collectionClass) {
            Class<? extends UncheckedList> clazz;
            if(checkedType.checked) {
                clazz=PackedBooleanArrSeq.CheckedList.class;
            }else {
                clazz=PackedBooleanArrSeq.UncheckedList.class;
            }
            try{
                return clazz.getDeclaredConstructor(collectionClass).newInstance(collection);
            }catch(InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException | SecurityException e){
                throw new Error(e);
            }
            
            
        }
        @Override
        void updateModCount(){
            expectedModCount=((PackedBooleanArrSeq.CheckedList)seq).modCount;
        }
        @Override
        void verifyCloneTypeAndModCount(Object clone){
            Assertions.assertEquals(checkedType.checked,clone instanceof PackedBooleanArrSeq.CheckedList);
            if(checkedType.checked){
                Assertions.assertEquals(0,((PackedBooleanArrSeq.CheckedList)clone).modCount);
            }else{
                Assertions.assertTrue(clone instanceof PackedBooleanArrSeq.UncheckedList);
            }
        }
        @Override
        void verifyModCount(){
            Assertions.assertEquals(expectedModCount,((PackedBooleanArrSeq.CheckedList)seq).modCount);
        }
        private static class PackedBooleanArrSubListMonitor<SUBLIST extends AbstractOmniCollection<Boolean>&OmniList.OfBoolean,SEQ extends AbstractOmniCollection<Boolean>&OmniList.OfBoolean&Externalizable>
                implements
                MonitoredList<SUBLIST>{
            final PackedBooleanArrListMonitor expectedRoot;
            final PackedBooleanArrSubListMonitor<SUBLIST,SEQ> expectedParent;
            final SUBLIST seq;
            int expectedRootOffset;
            int expectedSize;
            int expectedModCount;
            int trueCount;
            @SuppressWarnings("unchecked")
            PackedBooleanArrSubListMonitor(PackedBooleanArrListMonitor expectedRoot,int fromIndex,int toIndex){
                this.expectedRoot=expectedRoot;
                expectedParent=null;
                expectedModCount=expectedRoot.expectedModCount;
                expectedSize=toIndex - fromIndex;
                expectedRootOffset=fromIndex;
                seq=(SUBLIST)expectedRoot.seq.subList(fromIndex,toIndex);
                trueCount=countTrue();
            }
            @SuppressWarnings("unchecked")
            PackedBooleanArrSubListMonitor(PackedBooleanArrSubListMonitor<SUBLIST,SEQ> expectedParent,int fromIndex,
                    int toIndex){
                expectedRoot=expectedParent.expectedRoot;
                this.expectedParent=expectedParent;
                expectedModCount=expectedParent.expectedModCount;
                expectedSize=toIndex - fromIndex;
                expectedRootOffset=expectedParent.expectedRootOffset + fromIndex;
                seq=(SUBLIST)expectedParent.seq.subList(fromIndex,toIndex);
                trueCount=countTrue();
            }
            @Override
            public void copyListContents(){
                expectedRoot.copyListContents();
                trueCount=countTrue();
            }
            @Override
            public Object get(int iterationIndex,DataType outputType){
                return expectedRoot.get(iterationIndex + expectedRootOffset,outputType);
            }
            @Override
            public CheckedType getCheckedType(){
                return expectedRoot.checkedType;
            }
            @Override
            public SUBLIST getCollection(){
                return seq;
            }
            @Override
            public DataType getDataType(){
                return DataType.BOOLEAN;
            }
            @Override
            public MonitoredIterator<? extends OmniIterator<?>,SUBLIST> getMonitoredIterator(){
                return new ItrMonitor();
            }
            @Override
            public MonitoredIterator<? extends OmniIterator<?>,SUBLIST> getMonitoredIterator(int index,
                    IteratorType itrType){
                switch(itrType){
                case SubAscendingItr:{
                    final var monitor=new ItrMonitor();
                    while(--index >= 0){
                        monitor.iterateForward();
                    }
                    return monitor;
                }
                case SubBidirectionalItr:
                    return new ListItrMonitor(index);
                default:
                    throw itrType.invalid();
                }
            }
            @Override
            public MonitoredIterator<? extends OmniIterator<?>,SUBLIST> getMonitoredIterator(IteratorType itrType){
                switch(itrType){
                case SubAscendingItr:
                    return new ItrMonitor();
                case SubBidirectionalItr:
                    return new ListItrMonitor();
                default:
                    throw itrType.invalid();
                }
            }
            @Override
            public MonitoredListIterator<? extends OmniListIterator<?>,SUBLIST> getMonitoredListIterator(){
                return new ListItrMonitor();
            }
            @Override
            public MonitoredListIterator<? extends OmniListIterator<?>,SUBLIST> getMonitoredListIterator(int index){
                return new ListItrMonitor(index);
            }
            @Override
            public PackedBooleanArrSubListMonitor<SUBLIST,SEQ> getMonitoredSubList(int fromIndex,int toIndex){
                return new PackedBooleanArrSubListMonitor<>(this,fromIndex,toIndex);
            }
            @Override
            public StructType getStructType(){
                return StructType.PackedBooleanArrSubList;
            }
            @Override
            public void incrementModCount(){
                var curr=this;
                do{
                    ++curr.expectedModCount;
                }while((curr=curr.expectedParent) != null);
                ++expectedRoot.expectedModCount;
            }
            @Override
            public void modCollection(){
                var curr=this;
                do{
                    curr.expectedModCount=FieldAndMethodAccessor.PackedBooleanArrSeq.CheckedSubList
                            .incrementModCount(curr.seq);
                }while((curr=curr.expectedParent) != null);
                expectedRoot.modCollection();
            }
            @Override
            public void modParent(){
                var curr=this;
                while((curr=curr.expectedParent) != null){
                    curr.expectedModCount=FieldAndMethodAccessor.PackedBooleanArrSeq.CheckedSubList
                            .incrementModCount(curr.seq);
                }
                expectedRoot.modCollection();
            }
            @Override
            public void modRoot(){
                expectedRoot.modCollection();
            }
            @Override
            public Object removeFirst(){
                final var removed=seq.remove(0);
                updateRemoveIndexState(0);
                return removed;
            }
            @Override
            public void repairModCount(){
                if(expectedRoot.checkedType.checked){
                    final int rootModCount=expectedRoot.expectedModCount;
                    var curr=this;
                    do{
                        FieldAndMethodAccessor.setIntValue(
                                FieldAndMethodAccessor.PackedBooleanArrSeq.CheckedSubList.modCountField,curr.seq,
                                rootModCount);
                        curr.expectedModCount=rootModCount;
                    }while((curr=curr.expectedParent) != null);
                }
            }
            @Override
            public int size(){
                return expectedSize;
            }
            @Override
            public void updateAddState(int index,Object inputVal,DataType inputType){
                expectedRoot.updateAddState(index + expectedRootOffset,inputVal,inputType);
                if((boolean)inputVal){
                    var curr=this;
                    do{
                        ++curr.trueCount;
                    }while((curr=curr.expectedParent) != null);
                }
                bubbleUpModifySize(1);
            }
            @Override
            public void updateClearState(){
                int expectedSize;
                if((expectedSize=this.expectedSize) != 0){
                    final int trueCount=this.trueCount;
                    var curr=this;
                    do{
                        curr.trueCount-=trueCount;
                    }while((curr=curr.expectedParent) != null);
                    bubbleUpModifySize(-expectedSize);
                    final var root=this.expectedRoot;
                    ++root.expectedModCount;
                    final int offset,tailLength,rootBound;
                    root.expectedSize=(rootBound=root.expectedSize) - expectedSize;
                    if((tailLength=rootBound - (expectedSize=(offset=this.expectedRootOffset) + expectedSize)) != 0){
                        final var words=root.expectedWords;
                        if((offset & 63) == 0){
                            if((expectedSize & 63) == 0){
                                ArrCopy.uncheckedSelfCopy(words,offset >> 6,expectedSize >> 6,
                                        (tailLength - 1 >> 6) + 1);
                            }else{
                                BitSetUtil.srcUnalignedPullDown(words,offset >> 6,expectedSize,rootBound - 1 >> 6);
                            }
                        }else{
                            BitSetUtil.dstUnalignedPullDown(words,offset,expectedSize,rootBound - 1 >> 6);
                        }
                    }
                    ((AbstractPackedBooleanArrSeqMonitor<?>)root).updateTrueCount();
                }
            }
            @Override
            public void updateCollectionState(){
                expectedRoot.updateCollectionState();
                Consumer<PackedBooleanArrSubListMonitor<SUBLIST,SEQ>> subListUpdater=subListMonitor->{
                    subListMonitor.expectedSize=((AbstractOmniCollection<?>)subListMonitor.seq).size;
                    subListMonitor.trueCount=subListMonitor.countTrue();
                };
                if(expectedRoot.checkedType.checked){
                    subListUpdater=subListUpdater.andThen(
                            subListMonitor->subListMonitor.expectedModCount=FieldAndMethodAccessor.PackedBooleanArrSeq.CheckedSubList
                                    .modCount(subListMonitor.seq));
                }
                var curr=this;
                do{
                    subListUpdater.accept(curr);
                }while((curr=curr.expectedParent) != null);
            }
            @Override
            public void updateRemoveIndexState(int index){
                final int rootTrueCount=expectedRoot.trueCount;
                expectedRoot.updateRemoveIndexState(index + expectedRootOffset);
                if(rootTrueCount != expectedRoot.trueCount){
                    var curr=this;
                    do{
                        --curr.trueCount;
                    }while((curr=curr.expectedParent) != null);
                }
                bubbleUpModifySize(-1);
            }
            @Override
            public void updateRemoveValState(Object inputVal,DataType inputType){
                final int rootTrueCount=expectedRoot.trueCount;
                final int index=expectedRoot.findRemoveValIndex(inputVal,inputType,expectedRootOffset,
                        expectedRootOffset + expectedSize);
                expectedRoot.updateRemoveIndexState(index);
                if(rootTrueCount != expectedRoot.trueCount){
                    var curr=this;
                    do{
                        --curr.trueCount;
                    }while((curr=curr.expectedParent) != null);
                }
                bubbleUpModifySize(-1);
            }
            @Override
            public void updateReplaceAllState(MonitoredFunction function){
                if(function.getMonitoredFunctionGen().expectedException != ConcurrentModificationException.class
                        && !function.isEmpty()){
                    var curr=this;
                    do{
                        ++curr.expectedModCount;
                    }while((curr=curr.expectedParent) != null);
                }
                expectedRoot.updateReplaceAllState(function,expectedRootOffset);
                var curr=this;
                do{
                    curr.trueCount=curr.countTrue();
                }while((curr=curr.expectedParent) != null);
            }
            @Override
            public void verifyArrayIsCopy(Object arr,boolean emptyArrayMayBeSame){
                expectedRoot.verifyArrayIsCopy(arr,emptyArrayMayBeSame);
            }
            @Override
            public void verifyClone(Object clone){
                expectedRoot.verifyCloneTypeAndModCount(clone);
                Assertions.assertNotSame(clone,expectedRoot.seq);
                int size;
                Assertions.assertEquals(size=((AbstractOmniCollection<?>)seq).size,((AbstractOmniCollection<?>)clone).size);
                final var origArr=((PackedBooleanArrSeq)expectedRoot.seq).words;
                final var cloneArr=((PackedBooleanArrSeq)clone).words;
                if(origArr == null){
                    Assertions.assertNull(cloneArr);
                }else{
                    Assertions.assertNotSame(origArr,cloneArr);
                    if(size == 0){
                        Assertions.assertNull(cloneArr);
                    }else{
                        final int copyLength=(expectedSize - 1 >> 6) + 1;
                        Assertions.assertEquals(copyLength,cloneArr.length);
                        final var cloneItr=((PackedBooleanArrSeq)clone).iterator();
                        final var origItr=seq.iterator();
                        for(int i=0;i < expectedSize;++i){
                            Assertions.assertEquals(origItr.nextBoolean(),cloneItr.nextBoolean());
                        }
                        Assertions.assertFalse(cloneItr.hasNext());
                    }
                }
            }
            @Override
            public void verifyCollectionState(boolean refIsSame){
                Consumer<PackedBooleanArrSubListMonitor<SUBLIST,SEQ>> subListVerifier=subListMonitor->Assertions
                        .assertEquals(subListMonitor.expectedSize,((AbstractOmniCollection<?>)subListMonitor.seq).size);
                if(expectedRoot.checkedType.checked){
                    subListVerifier=subListVerifier.andThen(subListMonitor->{
                        Assertions.assertSame(subListMonitor.getParentSeq(),
                                FieldAndMethodAccessor.PackedBooleanArrSeq.CheckedSubList.parent(subListMonitor.seq));
                        Assertions.assertSame(expectedRoot.seq,
                                FieldAndMethodAccessor.PackedBooleanArrSeq.CheckedSubList.root(subListMonitor.seq));
                        Assertions.assertEquals(subListMonitor.expectedRootOffset,
                                FieldAndMethodAccessor.PackedBooleanArrSeq.CheckedSubList
                                        .rootOffset(subListMonitor.seq));
                        Assertions.assertEquals(subListMonitor.expectedModCount,
                                FieldAndMethodAccessor.PackedBooleanArrSeq.CheckedSubList.modCount(subListMonitor.seq));
                    });
                }else{
                    subListVerifier=subListVerifier.andThen(subListMonitor->{
                        Assertions.assertSame(subListMonitor.getParentSeq(),
                                FieldAndMethodAccessor.PackedBooleanArrSeq.UncheckedSubList.parent(subListMonitor.seq));
                        Assertions.assertSame(expectedRoot.seq,
                                FieldAndMethodAccessor.PackedBooleanArrSeq.UncheckedSubList.root(subListMonitor.seq));
                        Assertions.assertEquals(subListMonitor.expectedRootOffset,
                                FieldAndMethodAccessor.PackedBooleanArrSeq.UncheckedSubList
                                        .rootOffset(subListMonitor.seq));
                    });
                }
                var curr=this;
                do{
                    subListVerifier.accept(curr);
                }while((curr=curr.expectedParent) != null);
                expectedRoot.verifyCollectionState(refIsSame);
            }
            @Override
            public void verifyGetResult(int index,Object result,DataType outputType){
                expectedRoot.verifyGetResult(index + expectedRootOffset,result,outputType);
            }
            @Override
            public void verifyPutResult(int index,Object input,DataType inputType){
                final int trueCount=expectedRoot.trueCount;
                expectedRoot.verifyPutResult(index + expectedRootOffset,input,inputType);
                if((boolean)input){
                    if(expectedRoot.trueCount > trueCount){
                        var curr=this;
                        do{
                            ++curr.trueCount;
                        }while((curr=curr.expectedParent) != null);
                    }
                }else{
                    if(expectedRoot.trueCount < trueCount){
                        var curr=this;
                        do{
                            --curr.trueCount;
                        }while((curr=curr.expectedParent) != null);
                    }
                }
            }
            @Override
            public void verifyRemoveIf(boolean result,MonitoredRemoveIfPredicate filter){
                final int expectedSize=this.expectedSize;
                if(expectedSize == 0){
                    Assertions.assertFalse(result);
                    Assertions.assertTrue(filter.removedVals.isEmpty());
                }else{
                    Assertions.assertNotEquals(result,filter.removedVals.isEmpty());
                }
                Assertions.assertNotEquals(result,filter.numRemoved == 0);
                if(result){
                    int numRemoved;
                    int numTrueRemoved;
                    final int trueCount=this.trueCount;
                    if(trueCount == 0){
                        Assertions.assertFalse(filter.retainedVals.contains(Boolean.TRUE));
                        Assertions.assertFalse(filter.removedVals.contains(Boolean.TRUE));
                        Assertions.assertFalse(filter.retainedVals.contains(Boolean.FALSE));
                        Assertions.assertTrue(filter.removedVals.contains(Boolean.FALSE));
                        Assertions.assertEquals(1,filter.numCalls);
                        Assertions.assertEquals(1,filter.numRemoved);
                        Assertions.assertEquals(0,filter.numRetained);
                        numRemoved=expectedSize;
                        numTrueRemoved=0;
                    }else if(expectedSize == trueCount){
                        Assertions.assertFalse(filter.retainedVals.contains(Boolean.FALSE));
                        Assertions.assertFalse(filter.removedVals.contains(Boolean.FALSE));
                        Assertions.assertFalse(filter.retainedVals.contains(Boolean.TRUE));
                        Assertions.assertTrue(filter.removedVals.contains(Boolean.TRUE));
                        Assertions.assertEquals(1,filter.numCalls);
                        Assertions.assertEquals(1,filter.numRemoved);
                        Assertions.assertEquals(0,filter.numRetained);
                        numRemoved=expectedSize;
                        numTrueRemoved=trueCount;
                    }else{
                        Assertions.assertEquals(2,filter.numCalls);
                        if(filter.removedVals.contains(Boolean.TRUE)){
                            Assertions.assertFalse(filter.retainedVals.contains(Boolean.TRUE));
                            if(filter.removedVals.contains(Boolean.FALSE)){
                                Assertions.assertFalse(filter.retainedVals.contains(Boolean.FALSE));
                                Assertions.assertEquals(2,filter.numRemoved);
                                Assertions.assertEquals(0,filter.numRetained);
                                numRemoved=expectedSize;
                                numTrueRemoved=trueCount;
                            }else{
                                Assertions.assertTrue(filter.retainedVals.contains(Boolean.FALSE));
                                Assertions.assertEquals(1,filter.numRemoved);
                                Assertions.assertEquals(1,filter.numRetained);
                                numRemoved=trueCount;
                                numTrueRemoved=trueCount;
                            }
                        }else{
                            Assertions.assertTrue(filter.retainedVals.contains(Boolean.TRUE));
                            Assertions.assertFalse(filter.retainedVals.contains(Boolean.FALSE));
                            Assertions.assertTrue(filter.removedVals.contains(Boolean.FALSE));
                            Assertions.assertEquals(1,filter.numRemoved);
                            Assertions.assertEquals(1,filter.numRetained);
                            numRemoved=expectedSize - trueCount;
                            numTrueRemoved=0;
                        }
                    }
                    ++expectedRoot.expectedModCount;
                    expectedRoot.expectedSize-=numRemoved;
                    expectedRoot.copyListContents();
                    var curr=this;
                    do{
                        ++curr.expectedModCount;
                        curr.expectedSize-=numRemoved;
                        curr.trueCount-=numTrueRemoved;
                    }while((curr=curr.expectedParent) != null);
                }else{
                    Assertions.assertEquals(0,filter.numRemoved);
                    Assertions.assertEquals(filter.numCalls,filter.numRetained);
                    if(expectedSize != 0){
                        if(trueCount == 0){
                            Assertions.assertFalse(filter.retainedVals.contains(Boolean.TRUE));
                            Assertions.assertTrue(filter.retainedVals.contains(Boolean.FALSE));
                            Assertions.assertEquals(1,filter.numRetained);
                        }else if(trueCount == expectedSize){
                            Assertions.assertTrue(filter.retainedVals.contains(Boolean.TRUE));
                            Assertions.assertFalse(filter.retainedVals.contains(Boolean.FALSE));
                            Assertions.assertEquals(1,filter.numRetained);
                        }else{
                            Assertions.assertTrue(filter.retainedVals.contains(Boolean.TRUE));
                            Assertions.assertTrue(filter.retainedVals.contains(Boolean.FALSE));
                            Assertions.assertEquals(2,filter.numRetained);
                        }
                    }else{
                        Assertions.assertEquals(0,filter.numRetained);
                    }
                }
            }
            @Override
            public void writeObjectImpl(MonitoredObjectOutputStream oos) throws IOException{
                if(expectedRoot.checkedType.checked){
                    FieldAndMethodAccessor.PackedBooleanArrSeq.CheckedSubList.writeObject(seq,oos);
                }else{
                    FieldAndMethodAccessor.PackedBooleanArrSeq.UncheckedSubList.writeObject(seq,oos);
                }
            }
            private void bubbleUpModifySize(int sizeDelta){
                var curr=this;
                do{
                    ++curr.expectedModCount;
                    curr.expectedSize+=sizeDelta;
                }while((curr=curr.expectedParent) != null);
            }
            private int countTrue(){
                if(expectedSize == 0){
                    return 0;
                }
                final var words=expectedRoot.expectedWords;
                final int offset=this.expectedRootOffset;
                final int bound=offset + this.expectedSize;
                int wordOffset=offset >> 6;
                final int wordBound=bound - 1 >> 6;
                if(wordOffset == wordBound){
                    return Long.bitCount(words[wordBound] & -1L >>> -expectedSize << offset);
                }
                int bitCount=Long.bitCount(words[wordOffset] & -1L << offset);
                while(++wordOffset < wordBound){
                    bitCount+=Long.bitCount(words[wordOffset]);
                }
                return bitCount + Long.bitCount(words[wordOffset] & -1L >>> -bound);
            }
            private OmniList<?> getParentSeq(){
                if(expectedParent == null){
                    return null;
                }
                return expectedParent.seq;
            }
            private abstract class AbstractItrMonitor<ITR extends OmniIterator.OfBoolean>
                    implements
                    MonitoredIterator<ITR,SUBLIST>{
                final ITR itr;
                int expectedCursor;
                int expectedItrModCount;
                int expectedLastRet;
                int lastRetState;
                AbstractItrMonitor(ITR itr,int expectedCursor){
                    this.itr=itr;
                    this.expectedCursor=expectedCursor;
                    this.expectedItrModCount=expectedModCount;
                    if(expectedRoot.checkedType.checked){
                        this.expectedLastRet=-1;
                    }
                    this.lastRetState=-1;
                }
                @Override
                public ITR getIterator(){
                    return itr;
                }
                @Override
                public MonitoredCollection<SUBLIST> getMonitoredCollection(){
                    return PackedBooleanArrSubListMonitor.this;
                }
                @Override
                public int getNumLeft(){
                    return expectedRootOffset + expectedSize - expectedCursor;
                }
                @Override
                public boolean hasNext(){
                    return expectedCursor < expectedRootOffset + expectedSize;
                }
                @Override
                public boolean nextWasJustCalled(){
                    return lastRetState == 0;
                }
                @Override
                public void updateItrNextState(){
                    this.expectedLastRet=expectedCursor++;
                    lastRetState=0;
                }
                @Override
                public void updateItrRemoveState(){
                    expectedRoot.updateRemoveIndexState(expectedCursor=expectedLastRet);
                    ++expectedItrModCount;
                    bubbleUpModifySize(-1);
                    if(expectedRoot.checkedType.checked){
                        expectedLastRet=-1;
                    }
                    lastRetState=-1;
                }
                @Override
                public void verifyForEachRemaining(MonitoredFunction function){
                    final var functionItr=function.iterator();
                    IntConsumer functionVerifier;
                    final var expectedArr=expectedRoot.expectedWords;
                    functionVerifier=cursor->Assertions.assertEquals((expectedArr[cursor >> 6] & 1L << cursor) != 0,
                            (boolean)functionItr.next());
                    int expectedLastRet=this.expectedLastRet;
                    int expectedCursor=this.expectedCursor;
                    int lastRetState=this.lastRetState;
                    final int expectedBound=expectedSize + expectedRootOffset;
                    while(expectedCursor < expectedBound){
                        functionVerifier.accept(expectedCursor);
                        expectedLastRet=expectedCursor++;
                        lastRetState=0;
                    }
                    this.expectedCursor=expectedCursor;
                    this.expectedLastRet=expectedLastRet;
                    this.lastRetState=lastRetState;
                }
                @Override
                public void verifyNextResult(DataType outputType,Object result){
                    expectedRoot.verifyGetResult(expectedCursor,result,outputType);
                }
            }
            private class ItrMonitor extends AbstractItrMonitor<OmniIterator.OfBoolean>{
                ItrMonitor(){
                    super(seq.iterator(),expectedRootOffset);
                }
                @Override
                public IteratorType getIteratorType(){
                    return IteratorType.SubAscendingItr;
                }
                @Override
                public void verifyCloneHelper(Object clone){
                    final var checked=expectedRoot.checkedType.checked;
                    if(checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.PackedBooleanArrSeq.CheckedSubList.Itr.parent(clone));
                        Assertions.assertEquals(expectedCursor,
                                FieldAndMethodAccessor.PackedBooleanArrSeq.CheckedSubList.Itr.cursor(clone));
                        Assertions.assertEquals(expectedLastRet,
                                FieldAndMethodAccessor.PackedBooleanArrSeq.CheckedSubList.Itr.lastRet(clone));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.PackedBooleanArrSeq.CheckedSubList.Itr.modCount(clone));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.PackedBooleanArrSeq.UncheckedSubList.Itr.parent(clone));
                        Assertions.assertEquals(expectedCursor,
                                FieldAndMethodAccessor.PackedBooleanArrSeq.UncheckedSubList.Itr.cursor(clone));
                    }
                }
            }
            private class ListItrMonitor extends AbstractItrMonitor<OmniListIterator.OfBoolean>
                    implements
                    MonitoredListIterator<OmniListIterator.OfBoolean,SUBLIST>{
                ListItrMonitor(){
                    super(seq.listIterator(),expectedRootOffset);
                }
                ListItrMonitor(int index){
                    super(seq.listIterator(index),index + expectedRootOffset);
                }
                @Override
                public IteratorType getIteratorType(){
                    return IteratorType.SubBidirectionalItr;
                }
                @Override
                public boolean hasPrevious(){
                    return expectedCursor > expectedRootOffset;
                }
                @Override
                public int nextIndex(){
                    return expectedCursor - expectedRootOffset;
                }
                @Override
                public int previousIndex(){
                    return expectedCursor - expectedRootOffset - 1;
                }
                @Override
                public boolean previousWasJustCalled(){
                    return lastRetState == 1;
                }
                @Override
                public void updateItrAddState(Object input,DataType inputType){
                    expectedRoot.updateAddState(expectedCursor++,input,inputType);
                    bubbleUpModifySize(1);
                    ++expectedItrModCount;
                    if(expectedRoot.checkedType.checked){
                        expectedLastRet=-1;
                    }
                    lastRetState=-1;
                }
                @Override
                public void updateItrPreviousState(){
                    expectedLastRet=--expectedCursor;
                    lastRetState=1;
                }
                @Override
                public void updateItrSetState(Object input,DataType inputType){
                    expectedRoot.verifyPutResult(expectedLastRet,input,inputType);
                }
                @Override
                public void verifyCloneHelper(Object clone){
                    if(expectedRoot.checkedType.checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.PackedBooleanArrSeq.CheckedSubList.ListItr.parent(clone));
                        Assertions.assertEquals(expectedCursor,
                                FieldAndMethodAccessor.PackedBooleanArrSeq.CheckedSubList.ListItr.cursor(clone));
                        Assertions.assertEquals(expectedLastRet,
                                FieldAndMethodAccessor.PackedBooleanArrSeq.CheckedSubList.ListItr.lastRet(clone));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.PackedBooleanArrSeq.CheckedSubList.ListItr.modCount(clone));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.PackedBooleanArrSeq.UncheckedSubList.ListItr.parent(clone));
                        Assertions.assertEquals(expectedCursor,
                                FieldAndMethodAccessor.PackedBooleanArrSeq.UncheckedSubList.ListItr.cursor(clone));
                        Assertions.assertEquals(expectedLastRet,
                                FieldAndMethodAccessor.PackedBooleanArrSeq.UncheckedSubList.ListItr.lastRet(clone));
                    }
                }
                @Override
                public void verifyPreviousResult(DataType outputType,Object result){
                    expectedRoot.verifyGetResult(expectedLastRet,result,outputType);
                }
            }
        }
        private abstract class AbstractItrMonitor<ITR extends OmniIterator.OfBoolean>
                implements
                MonitoredIterator<ITR,PackedBooleanArrSeq.UncheckedList>{
            final ITR itr;
            int expectedCursor;
            int expectedItrModCount;
            int expectedLastRet;
            int lastRetState;
            AbstractItrMonitor(ITR itr,int expectedCursor){
                this.itr=itr;
                this.expectedCursor=expectedCursor;
                this.expectedItrModCount=expectedModCount;
                if(checkedType.checked){
                    this.expectedLastRet=-1;
                }
                this.lastRetState=-1;
            }
            @Override
            public ITR getIterator(){
                return this.itr;
            }
            @Override
            public MonitoredCollection<PackedBooleanArrSeq.UncheckedList> getMonitoredCollection(){
                return PackedBooleanArrListMonitor.this;
            }
            @Override
            public int getNumLeft(){
                return expectedSize - expectedCursor;
            }
            @Override
            public boolean hasNext(){
                return expectedCursor < expectedSize;
            }
            @Override
            public boolean nextWasJustCalled(){
                return this.lastRetState == 0;
            }
            @Override
            public void updateItrNextState(){
                expectedLastRet=expectedCursor++;
                lastRetState=0;
            }
            @Override
            public void updateItrRemoveState(){
                updateRemoveIndexState(expectedCursor=expectedLastRet);
                ++expectedItrModCount;
                if(checkedType.checked){
                    expectedLastRet=-1;
                }
                lastRetState=-1;
            }
            @Override
            public void verifyForEachRemaining(MonitoredFunction function){
                final var functionItr=function.iterator();
                IntConsumer functionVerifier;
                final var expectedArr=PackedBooleanArrListMonitor.this.expectedWords;
                functionVerifier=cursor->Assertions.assertEquals((expectedArr[cursor >> 6] & 1L << cursor) != 0,
                        (boolean)functionItr.next());
                int expectedLastRet=this.expectedLastRet;
                int lastRetState=this.lastRetState;
                int expectedCursor=this.expectedCursor;
                final int expectedBound=expectedSize;
                while(expectedCursor < expectedBound){
                    functionVerifier.accept(expectedCursor);
                    expectedLastRet=expectedCursor++;
                    lastRetState=0;
                }
                this.expectedCursor=expectedCursor;
                this.expectedLastRet=expectedLastRet;
                this.lastRetState=lastRetState;
            }
            @Override
            public void verifyNextResult(DataType outputType,Object result){
                verifyGetResult(expectedCursor,result,outputType);
            }
        }
        private class ItrMonitor extends AbstractItrMonitor<OmniIterator.OfBoolean>{
            ItrMonitor(){
                super(seq.iterator(),0);
            }
            @Override
            public IteratorType getIteratorType(){
                return IteratorType.AscendingItr;
            }
            @Override
            public void verifyCloneHelper(Object clone){
                if(checkedType.checked){
                    Assertions.assertSame(seq,FieldAndMethodAccessor.PackedBooleanArrSeq.CheckedList.Itr.parent(clone));
                    Assertions.assertEquals(expectedCursor,
                            FieldAndMethodAccessor.PackedBooleanArrSeq.CheckedList.Itr.cursor(clone));
                    Assertions.assertEquals(expectedItrModCount,
                            FieldAndMethodAccessor.PackedBooleanArrSeq.CheckedList.Itr.modCount(clone));
                    Assertions.assertEquals(expectedLastRet,
                            FieldAndMethodAccessor.PackedBooleanArrSeq.CheckedList.Itr.lastRet(clone));
                }else{
                    Assertions.assertSame(seq,
                            FieldAndMethodAccessor.PackedBooleanArrSeq.UncheckedList.Itr.parent(clone));
                    Assertions.assertEquals(expectedCursor,
                            FieldAndMethodAccessor.PackedBooleanArrSeq.UncheckedList.Itr.cursor(clone));
                }
            }
        }
        private class ListItrMonitor extends AbstractItrMonitor<OmniListIterator.OfBoolean>
                implements
                MonitoredListIterator<OmniListIterator.OfBoolean,PackedBooleanArrSeq.UncheckedList>{
            ListItrMonitor(){
                super(seq.listIterator(),0);
            }
            ListItrMonitor(int cursor){
                super(seq.listIterator(cursor),cursor);
            }
            @Override
            public IteratorType getIteratorType(){
                return IteratorType.BidirectionalItr;
            }
            @Override
            public boolean hasPrevious(){
                return expectedCursor > 0;
            }
            @Override
            public int nextIndex(){
                return expectedCursor;
            }
            @Override
            public int previousIndex(){
                return expectedCursor - 1;
            }
            @Override
            public boolean previousWasJustCalled(){
                return lastRetState == 1;
            }
            @Override
            public void updateItrAddState(Object input,DataType inputType){
                PackedBooleanArrListMonitor.this.updateAddState(expectedCursor++,input,inputType);
                ++expectedItrModCount;
                lastRetState=-1;
                if(checkedType.checked){
                    expectedLastRet=-1;
                }
            }
            @Override
            public void updateItrPreviousState(){
                expectedLastRet=--expectedCursor;
                lastRetState=1;
            }
            @Override
            public void updateItrSetState(Object input,DataType inputType){
                verifyPutResult(expectedLastRet,input,inputType);
            }
            @Override
            public void verifyCloneHelper(Object clone){
                if(checkedType.checked){
                    Assertions.assertSame(seq,
                            FieldAndMethodAccessor.PackedBooleanArrSeq.CheckedList.ListItr.parent(clone));
                    Assertions.assertEquals(expectedCursor,
                            FieldAndMethodAccessor.PackedBooleanArrSeq.CheckedList.ListItr.cursor(clone));
                    Assertions.assertEquals(expectedItrModCount,
                            FieldAndMethodAccessor.PackedBooleanArrSeq.CheckedList.ListItr.modCount(clone));
                    Assertions.assertEquals(expectedLastRet,
                            FieldAndMethodAccessor.PackedBooleanArrSeq.CheckedList.ListItr.lastRet(clone));
                }else{
                    Assertions.assertSame(seq,
                            FieldAndMethodAccessor.PackedBooleanArrSeq.UncheckedList.ListItr.parent(clone));
                    Assertions.assertEquals(expectedCursor,
                            FieldAndMethodAccessor.PackedBooleanArrSeq.UncheckedList.ListItr.cursor(clone));
                    Assertions.assertEquals(expectedLastRet,
                            FieldAndMethodAccessor.PackedBooleanArrSeq.UncheckedList.ListItr.lastRet(clone));
                }
            }
            @Override
            public void verifyPreviousResult(DataType outputType,Object result){
                verifyGetResult(expectedLastRet,result,outputType);
            }
        }
    }
    private abstract static class AbstractPackedBooleanArrSeqMonitor<SEQ extends PackedBooleanArrSeq>
            implements
            MonitoredSequence<SEQ>{
        private static long partialWordShiftDown(long word,int shift){
            long mask;
            return word & (mask=(1L << shift) - 1) | word >>> 1 & ~mask;
        }
        final CheckedType checkedType;
        final SEQ seq;
        int expectedSize;
        long[] expectedWords;
        int expectedModCount;
        int trueCount;
        AbstractPackedBooleanArrSeqMonitor(CheckedType checkedType){
            this.checkedType=checkedType;
            this.seq=initSeq();
            updateCollectionState();
        }
        AbstractPackedBooleanArrSeqMonitor(CheckedType checkedType,Collection<?> collection,Class<? extends Collection<?>> collectionClass){
            this.checkedType=checkedType;
            this.seq=initSeq(collection,collectionClass);
            updateCollectionState();
        }
        AbstractPackedBooleanArrSeqMonitor(CheckedType checkedType,int initCapacity){
            this.checkedType=checkedType;
            this.seq=initSeq(initCapacity);
            updateCollectionState();
        }
        public void copyListContents(){
            final var actualWords=seq.words;
            if(actualWords == null){
                this.expectedWords=null;
                this.trueCount=0;
            }else{
                var expectedWords=this.expectedWords;
                if(expectedWords == null || expectedWords.length != actualWords.length){
                    this.expectedWords=expectedWords=new long[actualWords.length];
                }
                System.arraycopy(actualWords,0,expectedWords,0,actualWords.length);
                final int bound=seq.size;
                if(bound != 0){
                    int wordOffset;
                    int bitCount=Long.bitCount(actualWords[wordOffset=bound - 1 >> 6] << -bound);
                    while(--wordOffset >= 0){
                        bitCount+=Long.bitCount(actualWords[wordOffset]);
                    }
                    this.trueCount=bitCount;
                }else{
                    this.trueCount=0;
                }
            }
        }
        @Override
        public Object get(int iterationIndex,DataType outputType){
            return outputType.convertVal((expectedWords[iterationIndex >> 6] & 1L << iterationIndex) != 0);
        }
        @Override
        public CheckedType getCheckedType(){
            return checkedType;
        }
        @Override
        public SEQ getCollection(){
            return seq;
        }
        @Override
        public DataType getDataType(){
            return DataType.BOOLEAN;
        }
        public void incrementModCount(){
            ++expectedModCount;
        }
        @Override
        public void modParent(){
            throw new UnsupportedOperationException();
        }
        @Override
        public void modRoot(){
            throw new UnsupportedOperationException();
        }
        @Override
        public void repairModCount(){
            // nothing to do
        }
        @Override
        public int size(){
            return this.expectedSize;
        }
        public void updateAddState(int index,Object inputVal,DataType inputType){
            final var v=(boolean)inputVal;
            var words=this.expectedWords;
            int expectedSize=this.expectedSize;
            this.expectedSize=expectedSize + 1;
            ++expectedModCount;
            if(v){
                ++trueCount;
            }
            if(expectedSize != 0){
                int wordIndex=index >> 6;
                if(expectedSize == index){
                    if(words.length == wordIndex){
                        ArrCopy.uncheckedCopy(words,0,words=new long[OmniArray.growBy50Pct(wordIndex)],0,wordIndex);
                        this.expectedWords=words;
                    }
                    if(v){
                        words[wordIndex]|=1L << expectedSize;
                    }else{
                        words[wordIndex]&=~(1L << expectedSize);
                    }
                }else{
                    var word=words[wordIndex];
                    var mask=1L << index;
                    if(words.length == (expectedSize>>=6)){
                        final long[] tmp;
                        ArrCopy.semicheckedCopy(words,0,tmp=new long[OmniArray.growBy50Pct(expectedSize)],0,wordIndex);
                        this.expectedWords=tmp;
                        if(v){
                            tmp[wordIndex]=mask | --mask & word | ~mask & word << 1;
                        }else{
                            tmp[wordIndex]=~mask & (--mask & word | ~mask & word << 1);
                        }
                        for(final int wordBound=expectedSize - 1;wordIndex < wordBound;){
                            tmp[++wordIndex]=word >>> 63 | (word=words[wordIndex]) << 1;
                        }
                        tmp[wordIndex + 1]=word >>> 63;
                    }else{
                        if(v){
                            words[wordIndex]=mask | --mask & word | ~mask & word << 1;
                        }else{
                            words[wordIndex]=~mask & (--mask & word | ~mask & word << 1);
                        }
                        while(wordIndex < expectedSize){
                            words[++wordIndex]=word >>> 63 | (word=words[wordIndex]) << 1;
                        }
                    }
                }
            }else{
                if(words == null){
                    this.expectedWords=new long[]{v?1L:0L};
                }else{
                    words[0]=v?1L:0L;
                }
            }
        }
        @Override
        public void updateAddState(Object inputVal,DataType inputType){
            updateAddState(expectedSize,inputVal,inputType);
        }
        @Override
        public void updateClearState(){
            ++expectedModCount;
            trueCount=0;
            expectedSize=0;
        }
        @Override
        public void updateCollectionState(){
            if(checkedType.checked){
                updateModCount();
            }
            copyListContents();
            this.expectedSize=((AbstractOmniCollection<?>)seq).size;
        }
        @Override
        public void updateRemoveIndexState(final int index){
            final int bound=expectedSize - 1;
            ++expectedModCount;
            final long[] words;
            int wordOffset;
            final int wordBound;
            final long retWord;
            var word=(words=this.expectedWords)[wordOffset=index >> 6]=partialWordShiftDown(retWord=words[wordOffset],
                    index);
            if(wordOffset == (wordBound=bound >> 6)){
                words[wordOffset]=word;
            }else{
                words[wordOffset]=word | (word=words[++wordOffset]) << 63;
                while(wordOffset != wordBound){
                    words[wordOffset]=word >>> 1 | (word=words[++wordOffset]) << 63;
                }
                words[wordBound]=word >>> 1;
            }
            expectedSize=bound;
            if((retWord & 1L << index) != 0){
                --trueCount;
            }
        }
        @Override
        public void updateRemoveValState(Object inputVal,DataType inputType){
            updateRemoveIndexState(findRemoveValIndex(inputVal,inputType,0,expectedSize));
        }
        public void updateReplaceAllState(MonitoredFunction function){
            updateReplaceAllState(function,0);
        }
        public void updateReplaceAllState(MonitoredFunction function,int index){
            if(function.getMonitoredFunctionGen().expectedException != ConcurrentModificationException.class
                    && !function.isEmpty()){
                ++expectedModCount;
            }
            final var itr=function.iterator();
            if(itr.hasNext()){
                final var words=this.expectedWords;
                int wordIndex;
                long word=words[wordIndex=index >> 6];
                int trueCount=this.trueCount;
                for(;;){
                    final var oldVal=itr.next();
                    if((boolean)oldVal){
                        --trueCount;
                        word=word & ~(1L << index);
                    }else{
                        ++trueCount;
                        word=word | 1L << index;
                    }
                    if(!itr.hasNext()){
                        words[wordIndex]=word;
                        break;
                    }
                    if((++index & 63) == 0){
                        words[wordIndex]=word;
                        word=words[++wordIndex];
                    }
                }
                this.trueCount=trueCount;
            }
        }
        @Override
        public void verifyArrayIsCopy(Object arr,boolean emptyArrayMayBeSame){
            if(arr instanceof long[]){
                Assertions.assertNotSame(seq.words,arr);
            }else if(arr == null){
                Assertions.assertNull(seq.words);
            }
        }
        @Override
        public void verifyClone(Object clone){
            verifyCloneTypeAndModCount(clone);
            Assertions.assertNotSame(clone,seq);
            int size;
            Assertions.assertEquals(size=((AbstractOmniCollection<?>)seq).size,((AbstractOmniCollection<?>)clone).size);
            final var origArr=((PackedBooleanArrSeq)seq).words;
            final var cloneArr=((PackedBooleanArrSeq)clone).words;
            if(origArr == null){
                Assertions.assertNull(cloneArr);
            }else{
                Assertions.assertNotSame(origArr,cloneArr);
                if(size != 0){
                    int wordIndex;
                    Assertions.assertEquals(origArr[wordIndex=size - 1 >> 6] << -size,cloneArr[wordIndex] << -size);
                    while(--wordIndex >= 0){
                        Assertions.assertEquals(origArr[wordIndex],cloneArr[wordIndex]);
                    }
                }
            }
        }
        @Override
        public void verifyCollectionState(){
            verifyCollectionState(false);
        }
        public void verifyCollectionState(boolean refIsSame){
            int expectedSize;
            Assertions.assertEquals(expectedSize=this.expectedSize,((AbstractOmniCollection<?>)seq).size);
            if(checkedType.checked){
                verifyModCount();
            }
            final var actualArr=((PackedBooleanArrSeq)seq).words;
            final var expectedArr=expectedWords;
            int bitCount=0;
            if(expectedSize == 0){
                if(expectedArr == null){
                    Assertions.assertNull(actualArr);
                }else{
                    Assertions.assertEquals(expectedArr.length,actualArr.length);
                }
            }else{
                Assertions.assertEquals(expectedArr.length,actualArr.length);
                int wordIndex;
                long word;
                Assertions.assertEquals(expectedArr[wordIndex=expectedSize - 1 >> 6] << -expectedSize,
                        word=actualArr[wordIndex] << -expectedSize);
                for(;;){
                    bitCount+=Long.bitCount(word);
                    if(--wordIndex < 0){
                        break;
                    }
                    Assertions.assertEquals(expectedArr[wordIndex],word=actualArr[wordIndex]);
                }
            }
            Assertions.assertEquals(trueCount,bitCount);
        }
        @Override
        public void verifyGetResult(int expectedCursor,Object output,DataType outputType){
            final boolean rawActualGetVal=(expectedWords[expectedCursor >> 6] & 1L << expectedCursor) != 0;
            switch(outputType){
            case BOOLEAN:
                Assertions.assertEquals(rawActualGetVal,(boolean)output);
                break;
            case BYTE:
                Assertions.assertEquals(rawActualGetVal?(byte)1:(byte)0,(byte)output);
                break;
            case CHAR:
                Assertions.assertEquals(rawActualGetVal?(char)1:(char)0,(char)output);
                break;
            case SHORT:
                Assertions.assertEquals(rawActualGetVal?(short)1:(short)0,(short)output);
                break;
            case INT:
                Assertions.assertEquals(rawActualGetVal?(int)1:(int)0,(int)output);
                break;
            case LONG:
                Assertions.assertEquals(rawActualGetVal?(long)1:(long)0,(long)output);
                break;
            case FLOAT:
                Assertions.assertEquals(rawActualGetVal?(float)1:(float)0,(float)output);
                break;
            case DOUBLE:
                Assertions.assertEquals(rawActualGetVal?(double)1:(double)0,(double)output);
                break;
            case REF:
                Assertions.assertEquals(rawActualGetVal,output);
                break;
            default:
                throw outputType.invalid();
            }
        }
        public void verifyPutResult(int index,Object input,DataType inputType){
            final Object expectedVal=DataType.BOOLEAN.convertValUnchecked(inputType,input);
            Assertions.assertEquals((boolean)expectedVal,((OmniList.OfBoolean)seq).getBoolean(index));
            int wordIndex;
            final long word=expectedWords[wordIndex=index >> 6];
            final boolean oldVal=(word & 1L << index) != 0;
            final var newVal=(boolean)expectedVal;
            if(oldVal){
                if(!newVal){
                    expectedWords[wordIndex]=word & ~(1L << index);
                    --trueCount;
                }
            }else{
                if(newVal){
                    expectedWords[wordIndex]=word | 1L << index;
                    ++trueCount;
                }
            }
        }
        @Override
        public void verifyRemoveIf(boolean result,MonitoredRemoveIfPredicate filter){
            int expectedSize=this.expectedSize;
            if(expectedSize == 0){
                Assertions.assertFalse(result);
                Assertions.assertTrue(filter.removedVals.isEmpty());
            }else{
                Assertions.assertNotEquals(result,filter.removedVals.isEmpty());
            }
            Assertions.assertNotEquals(result,filter.numRemoved == 0);
            if(result){
                final var words=this.expectedWords;
                if(trueCount == 0){
                    Assertions.assertFalse(filter.retainedVals.contains(Boolean.TRUE));
                    Assertions.assertFalse(filter.removedVals.contains(Boolean.TRUE));
                    Assertions.assertFalse(filter.retainedVals.contains(Boolean.FALSE));
                    Assertions.assertTrue(filter.removedVals.contains(Boolean.FALSE));
                    Assertions.assertEquals(1,filter.numCalls);
                    Assertions.assertEquals(1,filter.numRemoved);
                    Assertions.assertEquals(0,filter.numRetained);
                    this.expectedSize=0;
                }else if(expectedSize == trueCount){
                    Assertions.assertFalse(filter.retainedVals.contains(Boolean.FALSE));
                    Assertions.assertFalse(filter.removedVals.contains(Boolean.FALSE));
                    Assertions.assertFalse(filter.retainedVals.contains(Boolean.TRUE));
                    Assertions.assertTrue(filter.removedVals.contains(Boolean.TRUE));
                    Assertions.assertEquals(1,filter.numCalls);
                    Assertions.assertEquals(1,filter.numRemoved);
                    Assertions.assertEquals(0,filter.numRetained);
                    this.expectedSize=0;
                    this.trueCount=0;
                }else{
                    Assertions.assertEquals(2,filter.numCalls);
                    if(filter.removedVals.contains(Boolean.TRUE)){
                        Assertions.assertFalse(filter.retainedVals.contains(Boolean.TRUE));
                        if(filter.removedVals.contains(Boolean.FALSE)){
                            Assertions.assertFalse(filter.retainedVals.contains(Boolean.FALSE));
                            Assertions.assertEquals(2,filter.numRemoved);
                            Assertions.assertEquals(0,filter.numRetained);
                            this.expectedSize=0;
                            this.trueCount=0;
                        }else{
                            Assertions.assertTrue(filter.retainedVals.contains(Boolean.FALSE));
                            Assertions.assertEquals(1,filter.numRemoved);
                            Assertions.assertEquals(1,filter.numRetained);
                            this.expectedSize=expectedSize-=trueCount;
                            this.trueCount=0;
                            for(int i=0,bound=expectedSize - 1 >> 6;;++i){
                                words[i]=0;
                                if(i == bound){
                                    break;
                                }
                            }
                        }
                    }else{
                        Assertions.assertTrue(filter.retainedVals.contains(Boolean.TRUE));
                        Assertions.assertFalse(filter.retainedVals.contains(Boolean.FALSE));
                        Assertions.assertTrue(filter.removedVals.contains(Boolean.FALSE));
                        Assertions.assertEquals(1,filter.numRemoved);
                        Assertions.assertEquals(1,filter.numRetained);
                        this.expectedSize=expectedSize=trueCount;
                        for(int i=0,bound=expectedSize - 1 >> 6;;++i){
                            words[i]=-1L;
                            if(i == bound){
                                break;
                            }
                        }
                    }
                }
                ++expectedModCount;
            }else{
                Assertions.assertEquals(0,filter.numRemoved);
                Assertions.assertEquals(filter.numCalls,filter.numRetained);
                if(expectedSize != 0){
                    if(trueCount == 0){
                        Assertions.assertFalse(filter.retainedVals.contains(Boolean.TRUE));
                        Assertions.assertTrue(filter.retainedVals.contains(Boolean.FALSE));
                        Assertions.assertEquals(1,filter.numRetained);
                    }else if(trueCount == expectedSize){
                        Assertions.assertTrue(filter.retainedVals.contains(Boolean.TRUE));
                        Assertions.assertFalse(filter.retainedVals.contains(Boolean.FALSE));
                        Assertions.assertEquals(1,filter.numRetained);
                    }else{
                        Assertions.assertTrue(filter.retainedVals.contains(Boolean.TRUE));
                        Assertions.assertTrue(filter.retainedVals.contains(Boolean.FALSE));
                        Assertions.assertEquals(2,filter.numRetained);
                    }
                }else{
                    Assertions.assertEquals(0,filter.numRetained);
                }
            }
        }
        @Override
        public void writeObjectImpl(MonitoredObjectOutputStream oos) throws IOException{
            seq.writeExternal(oos);
        }
        abstract int findRemoveValIndex(Object inputVal,DataType inputType,int fromIndex,int toIndex);
        abstract SEQ initSeq();
        abstract SEQ initSeq(int initCapacity);
        abstract SEQ initSeq(Collection<?> collection,Class<? extends Collection<?>> collectionClass);
        abstract void updateModCount();
        abstract void verifyCloneTypeAndModCount(Object clone);
        abstract void verifyModCount();
        private void updateTrueCount(){
            int bitCount=0;
            if(expectedSize != 0){
                int wordIndex;
                final var words=this.expectedWords;
                long word=words[wordIndex=expectedSize - 1 >> 6] << -expectedSize;
                for(;;){
                    bitCount+=Long.bitCount(word);
                    if(--wordIndex < 0){
                        break;
                    }
                    word=words[wordIndex];
                }
            }
            this.trueCount=bitCount;
        }
    }
    private static interface BasicTest{
        default int getMaxPostAlloc(){
            return 0;
        }
        void runTest(MonitoredSequence<?> monitor);
        private void runAllTests(String testName,int[] sizes){
            final int maxPostAlloc=getMaxPostAlloc();
            for(final var initParams:ALL_STRUCT_INIT_PARAMS){
                if(initParams.totalPostAlloc > maxPostAlloc){
                    continue;
                }
                for(final var illegalMod:initParams.structType.validPreMods){
                    if(illegalMod.minDepth <= initParams.preAllocs.length
                            && (initParams.checkedType.checked || illegalMod.expectedException == null)){
                        for(final int size:sizes){
                            for(final int initCap:INIT_CAPACITIES){
                                TestExecutorService.submitTest(()->{
                                    final var monitor=SequenceInitialization.Ascending
                                            .initialize(getMonitoredSequence(initParams,initCap),size,0);
                                    if(illegalMod.expectedException == null){
                                        runTest(monitor);
                                    }else{
                                        monitor.illegalMod(illegalMod);
                                        Assertions.assertThrows(illegalMod.expectedException,()->runTest(monitor));
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
    private static interface MonitoredFunctionTest<MONITOR extends MonitoredSequence<? extends OmniCollection.OfBoolean>>{
        void runTest(MONITOR monitor,MonitoredFunctionGen functionGen,FunctionCallType functionCallType,
                IllegalModification illegalMod);
        @SuppressWarnings("unchecked")
        private void runAllTests(String testName,boolean useAllStructs){
            for(final var initParams:useAllStructs?ALL_STRUCT_INIT_PARAMS:LIST_STRUCT_INIT_PARAMS){
                if(initParams.totalPostAlloc > 0){
                    continue;
                }
                for(final var functionGen:initParams.structType.validMonitoredFunctionGens){
                    if(initParams.checkedType.checked || functionGen.expectedException == null){
                        for(final var size:SHORT_SIZES){
                            final int initValBound=size != 0?1:0;
                            for(final var functionCallType:DataType.BOOLEAN.validFunctionCalls){
                                for(final var illegalMod:initParams.structType.validPreMods){
                                    if(illegalMod.minDepth <= initParams.preAllocs.length
                                            && (initParams.checkedType.checked
                                                    || illegalMod.expectedException == null)){
                                        for(int tmpInitVal=0;tmpInitVal <= initValBound;++tmpInitVal){
                                            final int initVal=tmpInitVal;
                                            for(final var initCap:INIT_CAPACITIES){
                                                TestExecutorService.submitTest(()->{
                                                    runTest((MONITOR)SequenceInitialization.Ascending.initialize(
                                                            getMonitoredSequence(initParams,initCap),size,initVal),
                                                            functionGen,functionCallType,illegalMod);
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
            TestExecutorService.completeAllTests(testName);
        }
    }
    private static class PackedBooleanArrStackMonitor
            extends
            AbstractPackedBooleanArrSeqMonitor<PackedBooleanArrSeq.UncheckedStack>
            implements
            MonitoredStack<PackedBooleanArrSeq.UncheckedStack>{
        public PackedBooleanArrStackMonitor(SequenceInitParams initParams){
            super(initParams.checkedType);
        }
        public PackedBooleanArrStackMonitor(SequenceInitParams initParams,int initCap){
            super(initParams.checkedType,initCap);
        }
        PackedBooleanArrStackMonitor(CheckedType checkedType){
            super(checkedType);
        }
        PackedBooleanArrStackMonitor(CheckedType checkedType,Collection<?> collection,Class<? extends Collection<?>> collectionClass){
            super(checkedType,collection,collectionClass);
        }
        PackedBooleanArrStackMonitor(CheckedType checkedType,int initCapacity){
            super(checkedType,initCapacity);
        }
        @Override
        public Object get(int iterationIndex,DataType outputType){
            return super.get(expectedSize - iterationIndex - 1,outputType);
        }
        @Override
        public MonitoredIterator<? extends OmniIterator<?>,UncheckedStack> getMonitoredIterator(){
            if(checkedType.checked){
                return new CheckedItrMonitor();
            }
            return new UncheckedItrMonitor();
        }
        @Override
        public MonitoredIterator<? extends OmniIterator<?>,UncheckedStack> getMonitoredIterator(int index,
                IteratorType itrType){
            if(itrType != IteratorType.AscendingItr){
                throw itrType.invalid();
            }
            return getMonitoredIterator(index);
        }
        @Override
        public MonitoredIterator<? extends OmniIterator<?>,UncheckedStack> getMonitoredIterator(IteratorType itrType){
            if(itrType != IteratorType.AscendingItr){
                throw itrType.invalid();
            }
            return getMonitoredIterator();
        }
        @Override
        public StructType getStructType(){
            return StructType.PackedBooleanArrStack;
        }
        @Override
        public void modCollection(){
            ++((PackedBooleanArrSeq.CheckedStack)seq).modCount;
            ++expectedModCount;
        }
        @Override
        public Object removeFirst(){
            final var removed=seq.popBoolean();
            super.updateRemoveIndexState(expectedSize - 1);
            return removed;
        }
        @Override
        public Object verifyPeek(DataType outputType){
            Object result;
            final int size=expectedSize;
            try{
                result=outputType.callPeek(seq);
            }finally{
                verifyCollectionState(true);
            }
            if(size == 0){
                Assertions.assertEquals(outputType.defaultVal,result);
            }else{
                verifyGetResult(size - 1,result,outputType);
            }
            return result;
        }
        @Override
        public Object verifyPoll(DataType outputType){
            Object result;
            final Object expected=outputType.callPeek(seq);
            final int size=expectedSize;
            try{
                result=outputType.callPoll(seq);
                if(size > 0){
                    updateRemoveIndexState(size - 1);
                }
            }finally{
                verifyCollectionState(true);
            }
            Assertions.assertEquals(expected,result);
            return result;
        }
        @Override
        public Object verifyPop(DataType outputType){
            Object result;
            Object expected=null;
            int size;
            if(0 < (size=expectedSize)){
                expected=outputType.callPeek(seq);
            }
            try{
                result=outputType.callPop(seq);
                updateRemoveIndexState(size - 1);
            }finally{
                verifyCollectionState(true);
            }
            Assertions.assertEquals(expected,result);
            return result;
        }
        @Override
        public void verifyPush(Object inputVal,DataType inputType,FunctionCallType functionCallType){
            inputType.callPush(inputVal,seq,functionCallType);
            updateAddState(expectedSize,inputVal,inputType);
            verifyCollectionState(true);
        }
        @Override
        int findRemoveValIndex(Object inputVal,DataType inputType,int fromIndex,int toIndex){
            boolean inputCast;
            switch(inputType){
            case BOOLEAN:
                inputCast=(boolean)inputVal;
                break;
            case BYTE:
                inputCast=(byte)inputVal == 1;
                break;
            case CHAR:
                inputCast=(char)inputVal == 1;
                break;
            case SHORT:
                inputCast=(short)inputVal == 1;
                break;
            case INT:
                inputCast=(int)inputVal == 1;
                break;
            case LONG:
                inputCast=(long)inputVal == 1L;
                break;
            case FLOAT:
                inputCast=(float)inputVal == 1F;
                break;
            case DOUBLE:
                inputCast=(double)inputVal == 1D;
                break;
            default:
                throw inputType.invalid();
            }
            final var words=expectedWords;
            var word=words[--toIndex >> 6];
            if(inputCast){
                for(;;){
                    if((word & 1L << toIndex) != 0){
                        return toIndex;
                    }
                    if((--toIndex & 63) == 63){
                        word=words[toIndex >> 6];
                    }
                }
            }else{
                for(;;){
                    if((word & 1L << toIndex) == 0){
                        return toIndex;
                    }
                    if((--toIndex & 63) == 63){
                        word=words[toIndex >> 6];
                    }
                }
            }
        }
        @Override
        UncheckedStack initSeq(){
            if(checkedType.checked){
                return new PackedBooleanArrSeq.CheckedStack();
            }
            return new PackedBooleanArrSeq.UncheckedStack();
        }
        @Override
        UncheckedStack initSeq(int initCapacity){
            if(checkedType.checked){
                return new PackedBooleanArrSeq.CheckedStack(initCapacity);
            }
            return new PackedBooleanArrSeq.UncheckedStack(initCapacity);
        }
        @Override
        UncheckedStack initSeq(Collection<?> collection,Class<? extends Collection<?>> collectionClass) {
            Class<? extends UncheckedStack> clazz;
            if(checkedType.checked) {
                clazz=PackedBooleanArrSeq.CheckedStack.class;
            }else {
                clazz=PackedBooleanArrSeq.UncheckedStack.class;
            }
            try{
                return clazz.getDeclaredConstructor(collectionClass).newInstance(collection);
            }catch(InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException | SecurityException e){
                throw new Error(e);
            }
            
            
        }
        @Override
        void updateModCount(){
            expectedModCount=((PackedBooleanArrSeq.CheckedStack)seq).modCount;
        }
        @Override
        void verifyCloneTypeAndModCount(Object clone){
            Assertions.assertEquals(checkedType.checked,clone instanceof PackedBooleanArrSeq.CheckedStack);
            if(checkedType.checked){
                Assertions.assertEquals(0,((PackedBooleanArrSeq.CheckedStack)clone).modCount);
            }else{
                Assertions.assertTrue(clone instanceof PackedBooleanArrSeq.UncheckedStack);
            }
        }
        @Override
        void verifyModCount(){
            Assertions.assertEquals(expectedModCount,((PackedBooleanArrSeq.CheckedStack)seq).modCount);
        }
        private class CheckedItrMonitor extends UncheckedItrMonitor{
            @Override
            public void verifyCloneHelper(Object clone){
                Assertions.assertSame(seq,FieldAndMethodAccessor.PackedBooleanArrSeq.CheckedStack.Itr.parent(clone));
                Assertions.assertEquals(expectedCursor,
                        FieldAndMethodAccessor.PackedBooleanArrSeq.CheckedStack.Itr.cursor(clone));
                Assertions.assertEquals(expectedItrModCount,
                        FieldAndMethodAccessor.PackedBooleanArrSeq.CheckedStack.Itr.modCount(clone));
                Assertions.assertEquals(expectedLastRet,
                        FieldAndMethodAccessor.PackedBooleanArrSeq.CheckedStack.Itr.lastRet(clone));
            }
        }
        private class UncheckedItrMonitor
                implements
                MonitoredCollection.MonitoredIterator<OmniIterator.OfBoolean,PackedBooleanArrSeq.UncheckedStack>{
            final OmniIterator.OfBoolean itr=seq.iterator();
            int expectedCursor=expectedSize;
            int expectedLastRet=-1;
            int expectedItrModCount=expectedModCount;
            @Override
            public OmniIterator.OfBoolean getIterator(){
                return itr;
            }
            @Override
            public IteratorType getIteratorType(){
                return IteratorType.AscendingItr;
            }
            @Override
            public MonitoredCollection<PackedBooleanArrSeq.UncheckedStack> getMonitoredCollection(){
                return PackedBooleanArrStackMonitor.this;
            }
            @Override
            public int getNumLeft(){
                return expectedCursor;
            }
            @Override
            public boolean hasNext(){
                return expectedCursor > 0;
            }
            @Override
            public boolean nextWasJustCalled(){
                return expectedLastRet != -1;
            }
            @Override
            public void updateItrNextState(){
                expectedLastRet=--expectedCursor;
            }
            @Override
            public void updateItrRemoveState(){
                updateRemoveIndexState(expectedCursor);
                expectedLastRet=-1;
                ++expectedItrModCount;
            }
            @Override
            public void verifyCloneHelper(Object clone){
                Assertions.assertSame(seq,FieldAndMethodAccessor.PackedBooleanArrSeq.UncheckedStack.Itr.parent(clone));
                Assertions.assertEquals(expectedCursor,
                        FieldAndMethodAccessor.PackedBooleanArrSeq.UncheckedStack.Itr.cursor(clone));
            }
            @Override
            public void verifyForEachRemaining(MonitoredFunction function){
                int expectedCursor=this.expectedCursor;
                final var functionItr=function.iterator();
                IntConsumer functionVerifier;
                final var expectedArr=PackedBooleanArrStackMonitor.this.expectedWords;
                functionVerifier=cursor->Assertions.assertEquals((expectedArr[cursor >> 6] & 1L << cursor) != 0,
                        (boolean)functionItr.next());
                while(functionItr.hasNext()){
                    functionVerifier.accept(--expectedCursor);
                }
                if(expectedCursor == 0 && !function.isEmpty()){
                    this.expectedCursor=0;
                    expectedLastRet=0;
                }
            }
            @Override
            public void verifyNextResult(DataType outputType,Object result){
                verifyGetResult(expectedCursor - 1,result,outputType);
            }
        }
    }
    @FunctionalInterface
    private static interface QueryTest<MONITOR extends MonitoredSequence<? extends OmniCollection.OfBoolean>>{
        void callAndVerifyResult(MONITOR monitor,QueryVal queryVal,DataType inputType,QueryCastType castType,
                QueryVal.QueryValModification modification,double position,int seqSize);
        default boolean cmeFilter(IllegalModification illegalMod,DataType inputType,QueryVal queryVal,
                QueryVal.QueryValModification modification,QueryCastType castType){
            if(illegalMod.expectedException != null){
                return queryVal == QueryVal.Null && castType != QueryCastType.ToObject;
            }
            return true;
        }
        default int getMaxPostAlloc(){
            return 63;
        }
        private void runAllTests(String testName,int structSwitch){
            final int maxPostAlloc=getMaxPostAlloc();
            SequenceInitParams[] initParamArray;
            switch(structSwitch){
            case 0:
                initParamArray=LIST_STRUCT_INIT_PARAMS;
                break;
            case 1:
                initParamArray=STACK_STRUCT_INIT_PARAMS;
                break;
            default:
                initParamArray=ALL_STRUCT_INIT_PARAMS;
            }
            for(final var initParams:initParamArray){
                for(final var queryVal:QueryVal.values()){
                    if(DataType.BOOLEAN.isValidQueryVal(queryVal)){
                        queryVal.validQueryCombos.forEach((modification,castTypesToInputTypes)->{
                            castTypesToInputTypes.forEach((castType,inputTypes)->{
                                for(final var inputType:inputTypes){
                                    final boolean queryCanReturnTrue=queryVal.queryCanReturnTrue(modification,castType,
                                            inputType,DataType.BOOLEAN);
                                    for(final var size:SHORT_SIZES){
                                        if(size > 1 && (!queryCanReturnTrue || inputType != DataType.BOOLEAN
                                                || castType != QueryCastType.Unboxed)){
                                            break;
                                        }
                                        int prevIndex=-1;
                                        for(final var position:POSITIONS){
                                            if(position >= 0){
                                                if(initParams.totalPostAlloc > maxPostAlloc || !queryCanReturnTrue){
                                                    break;
                                                }
                                                int index;
                                                if((index=(int)(size * position)) == prevIndex){
                                                    continue;
                                                }
                                                prevIndex=index;
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
                                                    if(position == 0.5d){
                                                        continue;
                                                    }
                                                default:
                                                }
                                            }else{
                                                if(initParams.totalPostAlloc > maxPostAlloc){
                                                    continue;
                                                }
                                            }
                                            for(final var illegalMod:initParams.structType.validPreMods){
                                                if(illegalMod.minDepth <= initParams.preAllocs.length
                                                        && (initParams.checkedType.checked
                                                                || illegalMod.expectedException == null)){
                                                    if(illegalMod.expectedException != null && size > 0){
                                                        continue;
                                                    }
                                                    TestExecutorService.submitTest(()->{
                                                        if(cmeFilter(illegalMod,inputType,queryVal,modification,
                                                                castType)){
                                                            runTest(initParams,illegalMod,queryVal,modification,
                                                                    castType,inputType,size,position);
                                                        }else{
                                                            Assertions.assertThrows(illegalMod.expectedException,
                                                                    ()->runTest(initParams,illegalMod,queryVal,
                                                                            modification,castType,inputType,size,
                                                                            position));
                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    }
                                }
                            });
                        });
                    }
                }
            }
            TestExecutorService.completeAllTests(testName);
        }
        @SuppressWarnings("unchecked")
        private void runTest(SequenceInitParams initParams,IllegalModification illegalMod,QueryVal queryVal,
                QueryVal.QueryValModification modification,QueryCastType castType,DataType inputType,int seqSize,
                double position){
            final var monitor=getMonitoredSequence(initParams,seqSize + 1);
            if(position < 0){
                queryVal.initDoesNotContain(monitor.getCollection(),seqSize,0,modification);
            }else{
                queryVal.initContains(monitor.getCollection(),seqSize,0,position,modification);
            }
            monitor.updateCollectionState();
            monitor.illegalMod(illegalMod);
            callAndVerifyResult((MONITOR)monitor,queryVal,inputType,castType,modification,position,seqSize);
        }
    }
    private static interface SortTest{
        void runTest(MonitoredList<?> monitor,MonitoredComparatorGen comparatorGen,FunctionCallType functionCallType);
        private void runAllTests(String testName,boolean usesComparator){
            for(final var initParams:LIST_STRUCT_INIT_PARAMS){
                if(initParams.totalPostAlloc > 0){
                    continue;
                }
                for(final var comparatorGen:initParams.structType.validComparatorGens){
                    if(usesComparator || comparatorGen.validWithNoComparator){
                        for(final var size:SHORT_SIZES){
                            final int periodInc=Math.max(1,size / 10);
                            final int periodBound=size + periodInc;
                            if(size < 2 || comparatorGen.expectedException == null || initParams.checkedType.checked){
                                for(final var functionCallType:DataType.BOOLEAN.validFunctionCalls){
                                    for(final var illegalMod:initParams.structType.validPreMods){
                                        if(initParams.checkedType.checked || illegalMod.expectedException == null){
                                            for(int tmpInitVal=0;tmpInitVal <= 1;++tmpInitVal){
                                                final int initVal=tmpInitVal;
                                                for(int tmpPeriod=0;tmpPeriod <= periodBound;tmpPeriod+=periodInc){
                                                    final int period=tmpPeriod;
                                                    TestExecutorService.submitTest(()->{
                                                        final var monitor=SequenceInitialization.Ascending.initialize(
                                                                getMonitoredList(initParams,size),size,initVal,period);
                                                        if(illegalMod.expectedException == null){
                                                            if(size < 2 || comparatorGen.expectedException == null
                                                                    || period + 1 >= size){
                                                                runTest(monitor,comparatorGen,functionCallType);
                                                            }else{
                                                                Assertions.assertThrows(comparatorGen.expectedException,
                                                                        ()->runTest(monitor,comparatorGen,
                                                                                functionCallType));
                                                            }
                                                        }else{
                                                            monitor.illegalMod(illegalMod);
                                                            Assertions.assertThrows(illegalMod.expectedException,
                                                                    ()->runTest(monitor,comparatorGen,
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
                    }
                }
            }
            TestExecutorService.completeAllTests(testName);
        }
    }
    private static interface ToStringAndHashCodeTest{
        void callRaw(OmniCollection.OfBoolean seq);
        void callVerify(MonitoredSequence<? extends OmniCollection.OfBoolean> monitor);
        private void runAllTests(String testName,SequenceInitParams[] initParamsArr){
            for(final var initParams:initParamsArr){
                if(initParams.totalPostAlloc > 0){
                    continue;
                }
                for(int tmpInitVal=0;tmpInitVal <= 1;++tmpInitVal){
                    final int initVal=tmpInitVal;
                    for(final var size:SHORT_SIZES){
                        for(final var illegalMod:initParams.structType.validPreMods){
                            if(illegalMod.minDepth <= initParams.preAllocs.length
                                    && (initParams.checkedType.checked || illegalMod.expectedException == null)){
                                TestExecutorService.submitTest(()->{
                                    final var monitor=SequenceInitialization.Ascending
                                            .initialize(getMonitoredSequence(initParams,size),size,initVal);
                                    if(illegalMod.expectedException == null){
                                        callVerify(monitor);
                                    }else{
                                        monitor.illegalMod(illegalMod);
                                        Assertions.assertThrows(illegalMod.expectedException,()->callVerify(monitor));
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
}
