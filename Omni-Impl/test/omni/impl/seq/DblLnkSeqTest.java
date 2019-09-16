package omni.impl.seq;
import java.io.Externalizable;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.EnumMap;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.api.OmniCollection;
import omni.api.OmniDeque;
import omni.api.OmniIterator;
import omni.api.OmniList;
import omni.api.OmniListIterator;
import omni.impl.AbstractOmniCollection;
import omni.impl.CheckedType;
import omni.impl.DataType;
import omni.impl.FunctionCallType;
import omni.impl.IllegalModification;
import omni.impl.IteratorType;
import omni.impl.MonitoredCollection;
import omni.impl.MonitoredComparatorGen;
import omni.impl.MonitoredDeque;
import omni.impl.MonitoredFunction;
import omni.impl.MonitoredFunctionGen;
import omni.impl.MonitoredList;
import omni.impl.MonitoredObjectGen;
import omni.impl.MonitoredObjectOutputStream;
import omni.impl.MonitoredRemoveIfPredicate;
import omni.impl.MonitoredSequence;
import omni.impl.QueryCastType;
import omni.impl.QueryVal;
import omni.impl.QueryVal.QueryValModification;
import omni.impl.StructType;
import omni.util.NotYetImplementedException;
import omni.util.OmniArray;
import omni.util.TestExecutorService;
public class DblLnkSeqTest{
    private static final double[] RANDOM_THRESHOLDS=new double[]{0.01,0.05,0.10,0.25,0.50,0.75,0.90,0.95,0.99};
    private static final double[] POSITIONS=new double[]{-1,0,0.25,0.5,0.75,1.0};
    private static final EnumMap<DataType,EnumMap<CheckedType,SequenceInitParams[]>> QUICK_INIT_PARAMS=new EnumMap<>(
            DataType.class);
    private static final EnumMap<DataType,EnumMap<CheckedType,SequenceInitParams[]>> ALL_INIT_PARAMS=new EnumMap<>(
            DataType.class);
    private static final int[] SHORT_SIZES=new int[]{0,10};
    private static final int[] MEDIUM_SIZES=new int[]{0,1,2,3,4,5,10};
    private static final int[] SIZES=new int[]{0,1,2,3,4,5,10,20,30,40,50,60,70,80,90,100};
    private static final int[] REMOVE_IF_SIZES=new int[]{0,1,2,3,4,5,6,7,8,9,10,20,30,40,50,60,70,90,80,100};
    static{
        for(final var collectionType:DataType.values()){
            final var allCheckedTypeToSeqInit=ALL_INIT_PARAMS.computeIfAbsent(collectionType,
                    dt->new EnumMap<>(CheckedType.class));
            final var quickCheckedTypeToSeqInit=QUICK_INIT_PARAMS.computeIfAbsent(collectionType,
                    dt->new EnumMap<>(CheckedType.class));
            for(final var checkedType:CheckedType.values()){
                final var rootParams=new SequenceInitParams(StructType.DblLnkList,collectionType,checkedType,
                        OmniArray.OfInt.DEFAULT_ARR,OmniArray.OfInt.DEFAULT_ARR);
                final var quickParamArray=new SequenceInitParams[1 + 2 * 2 + 2 * 2 * 2 * 2 + 2 * 2 * 2 * 2 * 2 * 2];
                final var allParamArray=new SequenceInitParams[1 + 3 * 3 + 3 * 3 * 3 * 3 + 3 * 3 * 3 * 3 * 3 * 3];
                quickParamArray[0]=rootParams;
                allParamArray[0]=rootParams;
                allCheckedTypeToSeqInit.put(checkedType,allParamArray);
                quickCheckedTypeToSeqInit.put(checkedType,quickParamArray);
            }
        }
        int index=0;
        for(int pre0=0;pre0 <= 4;pre0+=4){
            final int[] preAllocs=new int[]{pre0};
            for(int post0=0;post0 <= 4;post0+=4){
                final int[] postAllocs=new int[]{post0};
                ++index;
                for(final var collectionType:DataType.values()){
                    final var tmp=QUICK_INIT_PARAMS.get(collectionType);
                    for(final var checkedType:CheckedType.values()){
                        tmp.get(checkedType)[index]=new SequenceInitParams(StructType.DblLnkSubList,collectionType,
                                checkedType,preAllocs,postAllocs);
                    }
                }
            }
        }
        for(int pre0=0;pre0 <= 4;pre0+=4){
            for(int pre1=0;pre1 <= 4;pre1+=4){
                final int[] preAllocs=new int[]{pre0,pre1};
                for(int post0=0;post0 <= 4;post0+=4){
                    for(int post1=0;post1 <= 4;post1+=4){
                        final int[] postAllocs=new int[]{post0,post1};
                        ++index;
                        for(final var collectionType:DataType.values()){
                            final var tmp=QUICK_INIT_PARAMS.get(collectionType);
                            for(final var checkedType:CheckedType.values()){
                                tmp.get(checkedType)[index]=new SequenceInitParams(StructType.DblLnkSubList,
                                        collectionType,checkedType,preAllocs,postAllocs);
                            }
                        }
                    }
                }
            }
        }
        for(int pre0=0;pre0 <= 4;pre0+=4){
            for(int pre1=0;pre1 <= 4;pre1+=4){
                for(int pre2=0;pre2 <= 4;pre2+=4){
                    final int[] preAllocs=new int[]{pre0,pre1,pre2};
                    for(int post0=0;post0 <= 4;post0+=4){
                        for(int post1=0;post1 <= 4;post1+=4){
                            for(int post2=0;post2 <= 4;post2+=4){
                                final int[] postAllocs=new int[]{post0,post1,post2};
                                ++index;
                                for(final var collectionType:DataType.values()){
                                    final var tmp=QUICK_INIT_PARAMS.get(collectionType);
                                    for(final var checkedType:CheckedType.values()){
                                        tmp.get(checkedType)[index]=new SequenceInitParams(StructType.DblLnkSubList,
                                                collectionType,checkedType,preAllocs,postAllocs);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        index=0;
        for(int pre0=0;pre0 <= 4;pre0+=2){
            final int[] preAllocs=new int[]{pre0};
            for(int post0=0;post0 <= 4;post0+=2){
                final int[] postAllocs=new int[]{post0};
                ++index;
                for(final var collectionType:DataType.values()){
                    final var tmp=ALL_INIT_PARAMS.get(collectionType);
                    for(final var checkedType:CheckedType.values()){
                        tmp.get(checkedType)[index]=new SequenceInitParams(StructType.DblLnkSubList,collectionType,
                                checkedType,preAllocs,postAllocs);
                    }
                }
            }
        }
        for(int pre0=0;pre0 <= 4;pre0+=2){
            for(int pre1=0;pre1 <= 4;pre1+=2){
                final int[] preAllocs=new int[]{pre0,pre1};
                for(int post0=0;post0 <= 4;post0+=2){
                    for(int post1=0;post1 <= 4;post1+=2){
                        final int[] postAllocs=new int[]{post0,post1};
                        ++index;
                        for(final var collectionType:DataType.values()){
                            final var tmp=ALL_INIT_PARAMS.get(collectionType);
                            for(final var checkedType:CheckedType.values()){
                                tmp.get(checkedType)[index]=new SequenceInitParams(StructType.DblLnkSubList,
                                        collectionType,checkedType,preAllocs,postAllocs);
                            }
                        }
                    }
                }
            }
        }
        for(int pre0=0;pre0 <= 4;pre0+=2){
            for(int pre1=0;pre1 <= 4;pre1+=2){
                for(int pre2=0;pre2 <= 4;pre2+=2){
                    final int[] preAllocs=new int[]{pre0,pre1,pre2};
                    for(int post0=0;post0 <= 4;post0+=2){
                        for(int post1=0;post1 <= 4;post1+=2){
                            for(int post2=0;post2 <= 4;post2+=2){
                                final int[] postAllocs=new int[]{post0,post1,post2};
                                ++index;
                                for(final var collectionType:DataType.values()){
                                    final var tmp=ALL_INIT_PARAMS.get(collectionType);
                                    for(final var checkedType:CheckedType.values()){
                                        tmp.get(checkedType)[index]=new SequenceInitParams(StructType.DblLnkSubList,
                                                collectionType,checkedType,preAllocs,postAllocs);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
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
    static MonitoredList<?> getMonitoredList(SequenceInitParams initParams,int initialCapacity){
        final var rootMonitor=new DblLnkSeqMonitor<>(initParams,
                getInitCapacity(initialCapacity,initParams.preAllocs,initParams.postAllocs));
        if(initParams.structType != StructType.DblLnkSubList){
            return rootMonitor;
        }
        final var preAllocs=initParams.preAllocs;
        final var postAllocs=initParams.postAllocs;
        int totalPreAlloc=preAllocs[0];
        int totalPostAlloc=postAllocs[0];
        SequenceInitialization.Ascending.initialize(rootMonitor,totalPreAlloc,Integer.MIN_VALUE);
        SequenceInitialization.Ascending.initialize(rootMonitor,totalPostAlloc,Integer.MAX_VALUE - totalPostAlloc);
        var subListMonitor=new DblLnkSeqMonitor.SubListMonitor<>(rootMonitor,totalPreAlloc,totalPreAlloc);
        for(int i=1;i < preAllocs.length;++i){
            int postAlloc;
            int preAlloc;
            totalPostAlloc+=postAlloc=postAllocs[i];
            SequenceInitialization.Ascending.initialize(subListMonitor,preAlloc=preAllocs[i],
                    Integer.MIN_VALUE + totalPreAlloc);
            SequenceInitialization.Ascending.initialize(subListMonitor,postAlloc,Integer.MAX_VALUE - totalPostAlloc);
            totalPreAlloc+=preAlloc;
            subListMonitor=new DblLnkSeqMonitor.SubListMonitor<>(subListMonitor,preAlloc,preAlloc);
        }
        return subListMonitor;
    }
    @Test
    public void testadd_intval(){
        ALL_INIT_PARAMS.forEach((collectionType,checkedTypeToInitParams)->{
            checkedTypeToInitParams.forEach((checkedType,initParamArray)->{
                for(final var position:POSITIONS){
                    if(position >= 0 || checkedType.checked){
                        for(final var initParams:initParamArray){
                            if(position != 0 && position != 1 && initParams.preAllocs.length > 0
                                    && initParams.totalPreAlloc != 0 && initParams.totalPostAlloc != 0){
                                continue;
                            }
                            for(final var inputType:collectionType.mayBeAddedTo()){
                                for(final var functionCallType:inputType.validFunctionCalls){
                                    for(final var illegalMod:initParams.structType.validPreMods){
                                        if(illegalMod.minDepth <= initParams.preAllocs.length
                                                && (initParams.checkedType.checked
                                                        || illegalMod.expectedException == null)){
                                            TestExecutorService.submitTest(()->{
                                                if(illegalMod.expectedException == null){
                                                    final var monitor=getMonitoredList(initParams,10);
                                                    if(position < 0){
                                                        for(int i=0;i < 10;++i){
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
                                                    }else{
                                                        for(int i=0;i < 10;++i){
                                                            monitor.verifyAdd((int)(i * position),
                                                                    inputType.convertValUnchecked(i),inputType,
                                                                    functionCallType);
                                                        }
                                                    }
                                                }else{
                                                    {
                                                        final var monitor=SequenceInitialization.Ascending
                                                                .initialize(getMonitoredList(initParams,10),10,0);
                                                        monitor.illegalMod(illegalMod);
                                                        final Object inputVal=inputType.convertValUnchecked(0);
                                                        Assertions.assertThrows(illegalMod.expectedException,()->monitor
                                                                .verifyAdd(-1,inputVal,inputType,functionCallType));
                                                        Assertions.assertThrows(illegalMod.expectedException,()->monitor
                                                                .verifyAdd(1,inputVal,inputType,functionCallType));
                                                        Assertions.assertThrows(illegalMod.expectedException,()->monitor
                                                                .verifyAdd(0,inputVal,inputType,functionCallType));
                                                    }
                                                    {
                                                        final var monitor=getMonitoredList(initParams,0);
                                                        monitor.illegalMod(illegalMod);
                                                        final Object inputVal=inputType.convertValUnchecked(0);
                                                        Assertions.assertThrows(illegalMod.expectedException,()->monitor
                                                                .verifyAdd(-1,inputVal,inputType,functionCallType));
                                                        Assertions.assertThrows(illegalMod.expectedException,()->monitor
                                                                .verifyAdd(1,inputVal,inputType,functionCallType));
                                                        Assertions.assertThrows(illegalMod.expectedException,()->monitor
                                                                .verifyAdd(0,inputVal,inputType,functionCallType));
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
            });
        });
        TestExecutorService.completeAllTests("DblLnkSeqTest.testadd_intval");
    }
    @Test
    public void testadd_val(){
        QUICK_INIT_PARAMS.forEach((collectionType,checkedTypeToInitParams)->{
            for(final var inputType:collectionType.mayBeAddedTo()){
                for(final var functionCallType:inputType.validFunctionCalls){
                    checkedTypeToInitParams.forEach((checkedType,initParamArray)->{
                        for(final var initParams:initParamArray){
                            for(final var illegalMod:initParams.structType.validPreMods){
                                if(illegalMod.minDepth <= initParams.preAllocs.length
                                        && (initParams.checkedType.checked || illegalMod.expectedException == null)){
                                    if(illegalMod.expectedException == null){
                                        TestExecutorService.submitTest(()->{
                                            final var monitor=getMonitoredList(initParams,10);
                                            for(int i=0;i < 10;++i){
                                                monitor.verifyAdd(inputType.convertValUnchecked(i),inputType,
                                                        functionCallType);
                                            }
                                        });
                                    }else{
                                        for(int tmpInitSize=0;tmpInitSize < 10;++tmpInitSize){
                                            final int initSize=tmpInitSize;
                                            TestExecutorService.submitTest(()->{
                                                final var monitor=SequenceInitialization.Ascending
                                                        .initialize(getMonitoredList(initParams,initSize),initSize,0,0);
                                                monitor.illegalMod(illegalMod);
                                                Assertions.assertThrows(illegalMod.expectedException,
                                                        ()->monitor.verifyAdd(inputType.convertValUnchecked(initSize),
                                                                inputType,functionCallType));
                                            });
                                        }
                                    }
                                }
                            }
                        }
                    });
                }
            }
        });
        TestExecutorService.completeAllTests("DblLnkSeqTest.testadd_val");
    }
    @Test
    public void testaddFirst_val(){
        final DequeAddTest test=DblLnkSeqMonitor::verifyAddFirst;
        test.runAllTests("DblLnkSeqTest.testaddFirst_val");
    }
    @Test
    public void testaddLast_val(){
        final DequeAddTest test=DblLnkSeqMonitor::verifyAddLast;
        test.runAllTests("DblLnkSeqTest.testaddLast_val");
    }
    @Test
    public void testclear_void(){
        final BasicTest test=(monitor,illegalMod)->{
            if(illegalMod.expectedException == null){
                monitor.verifyClear();
            }else{
                monitor.illegalMod(illegalMod);
                Assertions.assertThrows(illegalMod.expectedException,()->monitor.verifyClear());
            }
        };
        test.runAllTests("DblLnkSeqTest.testclear_void");
    }
    @Test
    public void testclone_void(){
        final BasicTest test=(monitor,illegalMod)->{
            if(illegalMod.expectedException == null){
                monitor.verifyClone();
            }else{
                monitor.illegalMod(illegalMod);
                Assertions.assertThrows(illegalMod.expectedException,()->monitor.verifyClone());
            }
        };
        test.runAllTests("DblLnkSeqTest.testclone_void");
    }
    @Test
    public void testConstructor_void(){
        for(final var checkedType:CheckedType.values()){
            for(final var collectionType:DataType.values()){
                TestExecutorService
                        .submitTest(()->new DblLnkSeqMonitor<>(checkedType,collectionType).verifyCollectionState());
            }
        }
        TestExecutorService.completeAllTests("DblLnkSeqTest.testConstructor_void");
    }
    @SuppressWarnings("unchecked")
    @Test
    public void testConstructor_Collection() {
      for(var dataType:DataType.values()) {
          for(var checkedType:CheckedType.values()) {
              for(var collectionClass:dataType.validCollectionConstructorClasses) {
                  TestExecutorService.submitTest(()->{
                      Collection<?> collectionParam=MonitoredCollection.getConstructorCollectionParam(dataType,(Class<? extends Collection<?>>)collectionClass);
                      new DblLnkSeqMonitor<>(checkedType,dataType,collectionParam,(Class<? extends Collection<?>>)collectionClass).verifyCollectionState();
                  });
              }
          }
      }
      TestExecutorService.completeAllTests("DblLnkSeqTest.testConstructor_Collection");
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
        test.runAllTests("DblLnkSeqTest.testcontains_val",true);
    }
    @Test
    public void testdescendingIterator_void(){
        QUICK_INIT_PARAMS.forEach((collectionType,checkedTypeToInitParams)->{
            checkedTypeToInitParams.forEach((checkedType,initParamArray)->{
                final var initParams=initParamArray[0];
                for(final var size:SHORT_SIZES){
                    TestExecutorService.submitTest(()->{
                        final var monitor=SequenceInitialization.Ascending
                                .initialize(new DblLnkSeqMonitor<>(initParams,size),size,0);
                        try{
                            monitor.getMonitoredDescendingIterator().verifyIteratorState();
                        }finally{
                            monitor.verifyCollectionState();
                        }
                    });
                }
            });
        });
        TestExecutorService.completeAllTests("DblLnkSeqTest.testdescendingIterator_void");
    }
    @Test
    public void testelement_void(){
        final DeqGetTest test=DblLnkSeqMonitor::verifyElement;
        test.runAllGetFirstTests("DblLnkSeqTest.testelement_void",true);
    }
    @Test
    public void testequals_Object(){
        final BasicTest test=(monitor,illegalMod)->{
            try{
                Assertions.assertFalse(monitor.getCollection().equals(null));
            }catch(final NotYetImplementedException e){
                // do nothing
            }
        };
        test.runAllTests("DblLnkSeqTest.testequals_Object");
    }
    @Test
    public void testforEach_Consumer(){
        final MonitoredFunctionTest<MonitoredSequence<?>> test=(monitor,functionGen,functionCallType,illegalMod,
                randSeed)->{
            if(illegalMod.expectedException == null){
                if(functionGen.expectedException == null || monitor.isEmpty()){
                    monitor.verifyForEach(functionGen,functionCallType,randSeed);
                }else{
                    Assertions.assertThrows(functionGen.expectedException,
                            ()->monitor.verifyForEach(functionGen,functionCallType,randSeed));
                }
            }else{
                monitor.illegalMod(illegalMod);
                Assertions.assertThrows(illegalMod.expectedException,
                        ()->monitor.verifyForEach(functionGen,functionCallType,randSeed));
            }
        };
        test.runAllTests("DblLnkSeqTest.testforEach_Consumer",100);
    }
    @Test
    public void testget_int(){
        final BasicTest test=(monitor,illegalMod)->{
            final int size=monitor.size();
            final var outputTypes=monitor.getDataType().validOutputTypes();
            if(illegalMod.expectedException == null){
                final var checked=monitor.getCheckedType().checked;
                for(final var outputType:outputTypes){
                    if(checked){
                        Assertions.assertThrows(IndexOutOfBoundsException.class,()->monitor.verifyGet(-1,outputType));
                        Assertions.assertThrows(IndexOutOfBoundsException.class,()->monitor.verifyGet(size,outputType));
                    }
                    for(int index=0;index < size;++index){
                        monitor.verifyGet(index,outputType);
                    }
                }
            }else{
                monitor.illegalMod(illegalMod);
                for(final var outputType:outputTypes){
                    for(int tmpIndex=-1;tmpIndex <= size;++tmpIndex){
                        final int index=tmpIndex;
                        Assertions.assertThrows(illegalMod.expectedException,()->monitor.verifyGet(index,outputType));
                    }
                }
            }
        };
        test.runAllTests("DblLnkSeqTest.testget_int");
    }
    @Test
    public void testgetFirst_void(){
        final DeqGetTest test=DblLnkSeqMonitor::verifyGetFirst;
        test.runAllGetFirstTests("DblLnkSeqTest.testgetFirst_void",true);
    }
    @Test
    public void testgetLast_void(){
        final DeqGetTest test=DblLnkSeqMonitor::verifyGetLast;
        test.runAllGetLastTests("DblLnkSeqTest.testgetLast_void",true);
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
        test.runAllTests("DblLnkSeqTest.testhashCode_void");
    }
    @Test
    public void testindexOf_val(){
        final QueryTest<MonitoredList<?>> test=(monitor,queryVal,inputType,castType,modification,monitoredObjectGen,
                position,seqSize)->{
            if(monitoredObjectGen == null){
                int expectedIndex;
                if(position >= 0){
                    expectedIndex=(int)Math.round(position * seqSize);
                }else{
                    expectedIndex=-1;
                }
                Assertions.assertEquals(expectedIndex,monitor.verifyIndexOf(queryVal,inputType,castType,modification));
            }else{
                monitor.verifyThrowingIndexOf(monitoredObjectGen);
            }
        };
        test.runAllTests("DblLnkSeqTest.testindexOf_val",true);
    }
    @Test
    public void testisEmpty_void(){
        final BasicTest test=(monitor,illegalMod)->{
            if(illegalMod.expectedException == null){
                monitor.verifyIsEmpty();
            }else{
                monitor.illegalMod(illegalMod);
                Assertions.assertThrows(illegalMod.expectedException,()->monitor.verifyIsEmpty());
            }
        };
        test.runAllTests("DblLnkSeqTest.testisEmpty_void");
    }
    @Test
    public void testiterator_void(){
        final BasicTest test=(monitor,illegalMod)->{
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
        };
        test.runAllTests("DblLnkSeqTest.testiterator_void");
    }
    @Test
    public void testItrclone_void(){
        for(final var size:SIZES){
            int prevIndex=-1;
            for(final var position:POSITIONS){
                int index;
                if(position >= 0 && (index=(int)(position * size)) != prevIndex){
                    prevIndex=index;
                    QUICK_INIT_PARAMS.forEach((collectionType,checkedTypeToInitParams)->{
                        checkedTypeToInitParams.forEach((checkedType,initParamArray)->{
                            for(final var initParams:initParamArray){
                                if(initParams.totalPreAlloc <= 2 && initParams.totalPostAlloc <= 2){
                                    for(final var itrType:initParams.structType.validItrTypes){
                                        TestExecutorService.submitTest(()->{
                                            final var seqMonitor=SequenceInitialization.Ascending
                                                    .initialize(getMonitoredList(initParams,size),size,0);
                                            final var itrMonitor=seqMonitor.getMonitoredIterator(index,itrType);
                                            itrMonitor.verifyClone();
                                        });
                                    }
                                }
                            }
                        });
                    });
                }
            }
        }
        TestExecutorService.completeAllTests("DblLnkSeqTest.testItrclone_void");
    }
    @Test
    public void testItrforEachRemaining_Consumer(){
        for(final int size:MEDIUM_SIZES){
            int prevNumToIterate=-1;
            for(final var position:POSITIONS){
                int numToIterate;
                if(position >= 0 && (numToIterate=(int)(position * size)) != prevNumToIterate){
                    prevNumToIterate=numToIterate;
                    final int numLeft=size - numToIterate;
                    QUICK_INIT_PARAMS.forEach((collectionType,checkedTypeToInitParams)->{
                        checkedTypeToInitParams.forEach((checkedType,initParamArray)->{
                            for(final var initParams:initParamArray){
                                if(initParams.totalPreAlloc == 0 && initParams.totalPostAlloc == 0){
                                    for(final var itrType:initParams.structType.validItrTypes){
                                        for(final var illegalMod:itrType.validPreMods){
                                            if(illegalMod.expectedException == null || checkedType.checked
                                                    && illegalMod.minDepth <= initParams.preAllocs.length){
                                                for(final var functionGen:itrType.validMonitoredFunctionGens){
                                                    if(size == 0 || functionGen.expectedException == null
                                                            || checkedType.checked
                                                                    && functionGen.minDepth <= initParams.preAllocs.length){
                                                        for(final var functionCallType:collectionType.validFunctionCalls){
                                                            final long randSeedBound=!functionCallType.boxed
                                                                    && numLeft > 1 && functionGen.randomized
                                                                    && illegalMod.expectedException == null?100:0;
                                                            for(long tmpRandSeed=0;tmpRandSeed <= randSeedBound;++tmpRandSeed){
                                                                final long randSeed=tmpRandSeed;
                                                                TestExecutorService.submitTest(()->{
                                                                    final var seqMonitor=SequenceInitialization.Ascending
                                                                            .initialize(
                                                                                    getMonitoredList(initParams,size),
                                                                                    size,0);
                                                                    final var itrMonitor=seqMonitor
                                                                            .getMonitoredIterator(numToIterate,itrType);
                                                                    itrMonitor.illegalMod(illegalMod);
                                                                    if(illegalMod.expectedException == null
                                                                            || numLeft == 0){
                                                                        if(functionGen.expectedException == null
                                                                                || numLeft == 0){
                                                                            itrMonitor.verifyForEachRemaining(
                                                                                    functionGen,functionCallType,
                                                                                    randSeed);
                                                                        }else{
                                                                            Assertions.assertThrows(
                                                                                    functionGen.expectedException,
                                                                                    ()->itrMonitor
                                                                                            .verifyForEachRemaining(
                                                                                                    functionGen,
                                                                                                    functionCallType,
                                                                                                    randSeed));
                                                                        }
                                                                    }else{
                                                                        Assertions.assertThrows(
                                                                                illegalMod.expectedException,
                                                                                ()->itrMonitor.verifyForEachRemaining(
                                                                                        functionGen,functionCallType,
                                                                                        randSeed));
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
                        });
                    });
                }
            }
        }
        TestExecutorService.completeAllTests("DblLnkSeqTest.testItrforEachRemaining_Consumer");
    }
    @Test
    public void testItrhasNext_void(){
        QUICK_INIT_PARAMS.forEach((collectionType,checkedTypeToInitParams)->{
            checkedTypeToInitParams.forEach((checkedType,initParamArray)->{
                for(final var initParams:initParamArray){
                    for(final var itrType:initParams.structType.validItrTypes){
                        for(final int size:SHORT_SIZES){
                            TestExecutorService.submitTest(()->{
                                final var seqMonitor=SequenceInitialization.Ascending
                                        .initialize(getMonitoredList(initParams,size),size,0);
                                final var itrMonitor=seqMonitor.getMonitoredIterator(itrType);
                                while(itrMonitor.verifyHasNext()){
                                    itrMonitor.iterateForward();
                                }
                            });
                        }
                    }
                }
            });
        });
        TestExecutorService.completeAllTests("DblLnkSeqTest.testItrhasNext_void");
    }
    @Test
    public void testItrnext_void(){
        QUICK_INIT_PARAMS.forEach((collectionType,checkedTypeToInitParams)->{
            checkedTypeToInitParams.forEach((checkedType,initParamArray)->{
                for(final var size:SHORT_SIZES){
                    if(size > 0 || checkedType.checked){
                        for(final var initParams:initParamArray){
                            for(final var itrType:initParams.structType.validItrTypes){
                                for(final var illegalMod:itrType.validPreMods){
                                    if(illegalMod.expectedException == null || checkedType.checked
                                            && illegalMod.minDepth <= initParams.preAllocs.length){
                                        for(final var outputType:collectionType.validOutputTypes()){
                                            TestExecutorService.submitTest(()->{
                                                final var seqMonitor=SequenceInitialization.Ascending
                                                        .initialize(getMonitoredList(initParams,size),size,0);
                                                final var itrMonitor=seqMonitor.getMonitoredIterator(itrType);
                                                if(illegalMod.expectedException == null){
                                                    while(itrMonitor.hasNext()){
                                                        itrMonitor.verifyNext(outputType);
                                                    }
                                                    if(checkedType.checked){
                                                        Assertions.assertThrows(NoSuchElementException.class,
                                                                ()->itrMonitor.verifyNext(outputType));
                                                    }
                                                }else{
                                                    itrMonitor.illegalMod(illegalMod);
                                                    Assertions.assertThrows(illegalMod.expectedException,
                                                            ()->itrMonitor.verifyNext(outputType));
                                                }
                                            });
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            });
        });
        TestExecutorService.completeAllTests("DblLnkSeqTest.testItrnext_void");
    }
    @Test
    public void testItrremove_void(){
        QUICK_INIT_PARAMS.forEach((collectionType,checkedTypeToInitParams)->{
            checkedTypeToInitParams.forEach((checkedType,initParamArray)->{
                for(final var initParams:initParamArray){
                    for(final var itrType:initParams.structType.validItrTypes){
                        for(final var illegalMod:itrType.validPreMods){
                            if(illegalMod.expectedException == null
                                    || checkedType.checked && illegalMod.minDepth <= initParams.preAllocs.length){
                                for(final var removeScenario:itrType.validItrRemoveScenarios){
                                    if(removeScenario.expectedException == null || checkedType.checked){
                                        for(final var size:SHORT_SIZES){
                                            int prevNumToIterate=-1;
                                            positionLoop:for(final var position:POSITIONS){
                                                if(position != 0 && position != 1 && initParams.preAllocs.length > 0
                                                        && initParams.totalPreAlloc != 0
                                                        && initParams.totalPostAlloc != 0){
                                                    continue;
                                                }
                                                final int numToIterate;
                                                if(position >= 0
                                                        && (numToIterate=(int)(size * position)) != prevNumToIterate){
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
                                                        if(size == 0
                                                                && itrType.iteratorInterface != OmniListIterator.class){
                                                            continue;
                                                        }
                                                        break;
                                                    default:
                                                        throw removeScenario.invalid();
                                                    }
                                                    TestExecutorService.submitTest(()->{
                                                        final var seqMonitor=SequenceInitialization.Ascending
                                                                .initialize(getMonitoredList(initParams,size),size,0);
                                                        final var itrMonitor=seqMonitor
                                                                .getMonitoredIterator(numToIterate,itrType);
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
            });
        });
        TestExecutorService.completeAllTests("DblLnkSeqTest.testItrremove_void");
    }
    @Test
    public void testlastIndexOf_val(){
        final QueryTest<MonitoredList<?>> test=(monitor,queryVal,inputType,castType,modification,monitoredObjectGen,
                position,seqSize)->{
            if(monitoredObjectGen == null){
                int expectedIndex;
                if(position >= 0){
                    expectedIndex=(int)Math.round(position * seqSize);
                }else{
                    expectedIndex=-1;
                }
                Assertions.assertEquals(expectedIndex,
                        monitor.verifyLastIndexOf(queryVal,inputType,castType,modification));
            }else{
                monitor.verifyThrowingLastIndexOf(monitoredObjectGen);
            }
        };
        test.runAllTests("DblLnkSeqTest.testlastIndexOf_val",true);
    }
    @Test
    public void testlistIterator_int(){
        QUICK_INIT_PARAMS.forEach((collectionType,checkedTypeToInitParams)->{
            checkedTypeToInitParams.forEach((checkedType,initParamArray)->{
                for(final var size:SIZES){
                    final int inc=Math.max(1,size / 10);
                    if(checkedType.checked || size > 0){
                        for(final var initParams:initParamArray){
                            if(initParams.totalPreAlloc <= 2 && initParams.totalPostAlloc <= 2){
                                for(final var illegalMod:initParams.structType.validPreMods){
                                    if(illegalMod.minDepth <= initParams.preAllocs.length
                                            && (initParams.checkedType.checked
                                                    || illegalMod.expectedException == null)){
                                        TestExecutorService.submitTest(()->{
                                            final var monitor=SequenceInitialization.Ascending
                                                    .initialize(getMonitoredList(initParams,size),size,0);
                                            if(illegalMod.expectedException == null){
                                                for(int index=-inc,bound=size + inc;index <= bound;index+=inc){
                                                    if(index < 0 || index > size){
                                                        if(initParams.checkedType.checked){
                                                            final int finalIndex=index;
                                                            Assertions.assertThrows(IndexOutOfBoundsException.class,
                                                                    ()->{
                                                                        try{
                                                                            monitor.getMonitoredListIterator(
                                                                                    finalIndex);
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
                }
            });
        });
        TestExecutorService.completeAllTests("DblLnkSeqTest.testlistIterator_int");
    }
    @Test
    public void testlistIterator_void(){
        final BasicTest test=(monitor,illegalMod)->{
            try{
                if(illegalMod.expectedException == null){
                    monitor.getMonitoredListIterator().verifyIteratorState();
                }else{
                    monitor.illegalMod(illegalMod);
                    Assertions.assertThrows(illegalMod.expectedException,monitor::getMonitoredListIterator);
                }
            }finally{
                monitor.verifyCollectionState();
            }
        };
        test.runAllTests("DblLnkSeqTest.testlistIterator_void");
    }
    @Test
    public void testListItradd_val(){
        for(final var position:POSITIONS){
            if(position >= 0){
                QUICK_INIT_PARAMS.forEach((collectionType,checkedTypeToInitParams)->{
                    checkedTypeToInitParams.forEach((checkedType,initParamArray)->{
                        for(final var initParams:initParamArray){
                            if(position != 0 && position != 1 && initParams.preAllocs.length > 0
                                    && initParams.totalPreAlloc != 0 && initParams.totalPostAlloc != 0){
                                continue;
                            }
                            final var itrType=initParams.structType == StructType.DblLnkList
                                    ?IteratorType.BidirectionalItr
                                    :IteratorType.SubBidirectionalItr;
                            for(final var illegalMod:itrType.validPreMods){
                                if(illegalMod.expectedException == null
                                        || checkedType.checked && illegalMod.minDepth <= initParams.preAllocs.length){
                                    for(final var inputType:collectionType.mayBeAddedTo()){
                                        for(final var functionCallType:inputType.validFunctionCalls){
                                            TestExecutorService.submitTest(()->{
                                                final var seqMonitor=getMonitoredList(initParams,10);
                                                final var itrMonitor=seqMonitor.getMonitoredListIterator();
                                                for(int i=0;;){
                                                    itrMonitor.verifyAdd(inputType.convertValUnchecked(i),inputType,
                                                            functionCallType);
                                                    if(++i == 10){
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
                                                            ()->itrMonitor.verifyAdd(inputType.convertValUnchecked(10),
                                                                    inputType,functionCallType));
                                                    // for good measure, do it again with an
                                                    // empty list
                                                    final var itrMonitor2=getMonitoredList(initParams,10)
                                                            .getMonitoredListIterator();
                                                    itrMonitor2.illegalMod(illegalMod);
                                                    Assertions.assertThrows(illegalMod.expectedException,
                                                            ()->itrMonitor2.verifyAdd(inputType.convertValUnchecked(10),
                                                                    inputType,functionCallType));
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
        TestExecutorService.completeAllTests("DblLnkSeqTest.testListItradd_val");
    }
    @Test
    public void testListItrhasPrevious_void(){
        final ListItrPositionalQueryTest test=(initParams,size)->{
            final var seqMonitor=SequenceInitialization.Ascending.initialize(getMonitoredList(initParams,size),size,0);
            final var itrMonitor=seqMonitor.getMonitoredListIterator(size);
            while(itrMonitor.verifyHasPrevious()){
                itrMonitor.iterateReverse();
            }
        };
        test.runAllTests("DblLnkSeqTest.testListItrhasPrevious_void");
    }
    @Test
    public void testListItrnextIndex_void(){
        final ListItrPositionalQueryTest test=(initParams,size)->{
            final var seqMonitor=SequenceInitialization.Ascending.initialize(getMonitoredList(initParams,size),size,0);
            final var itrMonitor=seqMonitor.getMonitoredListIterator();
            while(itrMonitor.verifyNextIndex() < size){
                itrMonitor.iterateForward();
            }
        };
        test.runAllTests("DblLnkSeqTest.testListItrnextIndex_void");
    }
    @Test
    public void testListItrprevious_void(){
        QUICK_INIT_PARAMS.forEach((collectionType,checkedTypeToInitParams)->{
            checkedTypeToInitParams.forEach((checkedType,initParamArray)->{
                for(final var size:SHORT_SIZES){
                    if(size > 0 || checkedType.checked){
                        for(final var initParams:initParamArray){
                            final var itrType=initParams.structType == StructType.DblLnkList
                                    ?IteratorType.BidirectionalItr
                                    :IteratorType.SubBidirectionalItr;
                            for(final var illegalMod:itrType.validPreMods){
                                if(illegalMod.expectedException == null
                                        || checkedType.checked && illegalMod.minDepth <= initParams.preAllocs.length){
                                    for(final var outputType:collectionType.validOutputTypes()){
                                        TestExecutorService.submitTest(()->{
                                            final var seqMonitor=SequenceInitialization.Ascending
                                                    .initialize(getMonitoredList(initParams,size),size,0);
                                            final var itrMonitor=seqMonitor.getMonitoredListIterator(size);
                                            itrMonitor.illegalMod(illegalMod);
                                            if(illegalMod.expectedException == null){
                                                while(itrMonitor.hasPrevious()){
                                                    itrMonitor.verifyPrevious(outputType);
                                                }
                                                if(initParams.checkedType.checked){
                                                    Assertions.assertThrows(NoSuchElementException.class,
                                                            ()->itrMonitor.verifyPrevious(outputType));
                                                }
                                            }else{
                                                Assertions.assertThrows(illegalMod.expectedException,
                                                        ()->itrMonitor.verifyPrevious(outputType));
                                            }
                                        });
                                    }
                                }
                            }
                        }
                    }
                }
            });
        });
        TestExecutorService.completeAllTests("DblLnkSeqTest.testListItrprevious_void");
    }
    @Test
    public void testListItrpreviousIndex_void(){
        final ListItrPositionalQueryTest test=(initParams,size)->{
            final var seqMonitor=SequenceInitialization.Ascending.initialize(getMonitoredList(initParams,size),size,0);
            final var itrMonitor=seqMonitor.getMonitoredListIterator(size);
            while(itrMonitor.verifyPreviousIndex() > 0){
                itrMonitor.iterateReverse();
            }
        };
        test.runAllTests("DblLnkSeqTest.testListItrpreviousIndex_void");
    }
    @Test
    public void testListItrset_val(){
        QUICK_INIT_PARAMS.forEach((collectionType,checkedTypeToInitParams)->{
            checkedTypeToInitParams.forEach((checkedType,initParamArray)->{
                for(final var initParams:initParamArray){
                    final var itrType=initParams.structType == StructType.DblLnkList?IteratorType.BidirectionalItr
                            :IteratorType.SubBidirectionalItr;
                    for(final var illegalMod:itrType.validPreMods){
                        if(illegalMod.expectedException == null
                                || checkedType.checked && illegalMod.minDepth <= initParams.preAllocs.length){
                            for(final var removeScenario:itrType.validItrRemoveScenarios){
                                if(removeScenario.expectedException == null || checkedType.checked){
                                    for(final var inputType:initParams.collectionType.mayBeAddedTo()){
                                        for(final var functionCallType:inputType.validFunctionCalls){
                                            for(final int size:SHORT_SIZES){
                                                TestExecutorService.submitTest(()->{
                                                    final var seqMonitor=SequenceInitialization.Ascending
                                                            .initialize(getMonitoredList(initParams,size),size,0);
                                                    final var itrMonitor=seqMonitor.getMonitoredListIterator();
                                                    removeScenario.initialize(itrMonitor);
                                                    itrMonitor.illegalMod(illegalMod);
                                                    if(removeScenario.expectedException == null){
                                                        if(illegalMod.expectedException == null){
                                                            int i=1;
                                                            {
                                                                final int finalI=i;
                                                                itrMonitor.verifySet(
                                                                        inputType.convertValUnchecked(finalI),inputType,
                                                                        functionCallType);
                                                            }
                                                            while(itrMonitor.hasNext()){
                                                                itrMonitor.iterateForward();
                                                                final int finalI=++i;
                                                                itrMonitor.verifySet(
                                                                        inputType.convertValUnchecked(finalI),inputType,
                                                                        functionCallType);
                                                            }
                                                        }else{
                                                            Assertions.assertThrows(illegalMod.expectedException,
                                                                    ()->itrMonitor.verifySet(
                                                                            inputType.convertValUnchecked(1),inputType,
                                                                            functionCallType));
                                                        }
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
            });
        });
        TestExecutorService.completeAllTests("DblLnkSeqTest.testListItrset_val");
    }
    @Test
    public void testMASSIVEtoString(){
        final int numWorkers=TestExecutorService.getNumWorkers();
        for(final var collectionType:DataType.values()){
            int seqSize;
            if((seqSize=collectionType.massiveToStringThreshold + 1) == 0){
                continue;
            }
            OmniList<?> checkedRoot;
            OmniList<?> uncheckedRoot;
            final int threadSpan=(int)Math.ceil((double)seqSize / (double)numWorkers) - 1;
            switch(collectionType){
            case BOOLEAN:{
                final var wayPoints=new BooleanDblLnkSeq.Node[numWorkers + 1];
                for(int i=0;i < wayPoints.length;++i){
                    wayPoints[i]=new BooleanDblLnkSeq.Node(true);
                }
                for(int i=0;i < numWorkers;){
                    final var threadHead=wayPoints[i];
                    final var threadTail=wayPoints[++i];
                    TestExecutorService.submitTest(()->{
                        var curr=threadHead;
                        for(int j=threadSpan;--j >= 0;){
                            curr=curr.next=new BooleanDblLnkSeq.Node(curr,true);
                        }
                        threadTail.prev=curr;
                        curr.next=threadTail;
                    });
                }
                TestExecutorService.completeAllTests();
                final var head=wayPoints[0];
                final var tail=wayPoints[wayPoints.length - 1];
                checkedRoot=new BooleanDblLnkSeq.CheckedList(head,seqSize,tail);
                uncheckedRoot=new BooleanDblLnkSeq.UncheckedList(head,seqSize,tail);
                break;
            }
            case BYTE:{
                final var wayPoints=new ByteDblLnkSeq.Node[numWorkers + 1];
                for(int i=0;i < wayPoints.length;++i){
                    wayPoints[i]=new ByteDblLnkSeq.Node((byte)0);
                }
                for(int i=0;i < numWorkers;){
                    final var threadHead=wayPoints[i];
                    final var threadTail=wayPoints[++i];
                    TestExecutorService.submitTest(()->{
                        var curr=threadHead;
                        for(int j=threadSpan;--j >= 0;){
                            curr=curr.next=new ByteDblLnkSeq.Node(curr,(byte)0);
                        }
                        threadTail.prev=curr;
                        curr.next=threadTail;
                    });
                }
                TestExecutorService.completeAllTests();
                final var head=wayPoints[0];
                final var tail=wayPoints[wayPoints.length - 1];
                checkedRoot=new ByteDblLnkSeq.CheckedList(head,seqSize,tail);
                uncheckedRoot=new ByteDblLnkSeq.UncheckedList(head,seqSize,tail);
                break;
            }
            case SHORT:{
                final var wayPoints=new ShortDblLnkSeq.Node[numWorkers + 1];
                for(int i=0;i < wayPoints.length;++i){
                    wayPoints[i]=new ShortDblLnkSeq.Node((short)0);
                }
                for(int i=0;i < numWorkers;){
                    final var threadHead=wayPoints[i];
                    final var threadTail=wayPoints[++i];
                    TestExecutorService.submitTest(()->{
                        var curr=threadHead;
                        for(int j=threadSpan;--j >= 0;){
                            curr=curr.next=new ShortDblLnkSeq.Node(curr,(short)0);
                        }
                        threadTail.prev=curr;
                        curr.next=threadTail;
                    });
                }
                TestExecutorService.completeAllTests();
                final var head=wayPoints[0];
                final var tail=wayPoints[wayPoints.length - 1];
                checkedRoot=new ShortDblLnkSeq.CheckedList(head,seqSize,tail);
                uncheckedRoot=new ShortDblLnkSeq.UncheckedList(head,seqSize,tail);
                break;
            }
            case INT:{
                final var wayPoints=new IntDblLnkSeq.Node[numWorkers + 1];
                for(int i=0;i < wayPoints.length;++i){
                    wayPoints[i]=new IntDblLnkSeq.Node(0);
                }
                for(int i=0;i < numWorkers;){
                    final var threadHead=wayPoints[i];
                    final var threadTail=wayPoints[++i];
                    TestExecutorService.submitTest(()->{
                        var curr=threadHead;
                        for(int j=threadSpan;--j >= 0;){
                            curr=curr.next=new IntDblLnkSeq.Node(curr,0);
                        }
                        threadTail.prev=curr;
                        curr.next=threadTail;
                    });
                }
                TestExecutorService.completeAllTests();
                final var head=wayPoints[0];
                final var tail=wayPoints[wayPoints.length - 1];
                checkedRoot=new IntDblLnkSeq.CheckedList(head,seqSize,tail);
                uncheckedRoot=new IntDblLnkSeq.UncheckedList(head,seqSize,tail);
                break;
            }
            case LONG:{
                final var wayPoints=new LongDblLnkSeq.Node[numWorkers + 1];
                for(int i=0;i < wayPoints.length;++i){
                    wayPoints[i]=new LongDblLnkSeq.Node(0L);
                }
                for(int i=0;i < numWorkers;){
                    final var threadHead=wayPoints[i];
                    final var threadTail=wayPoints[++i];
                    TestExecutorService.submitTest(()->{
                        var curr=threadHead;
                        for(int j=threadSpan;--j >= 0;){
                            curr=curr.next=new LongDblLnkSeq.Node(curr,0L);
                        }
                        threadTail.prev=curr;
                        curr.next=threadTail;
                    });
                }
                TestExecutorService.completeAllTests();
                final var head=wayPoints[0];
                final var tail=wayPoints[wayPoints.length - 1];
                checkedRoot=new LongDblLnkSeq.CheckedList(head,seqSize,tail);
                uncheckedRoot=new LongDblLnkSeq.UncheckedList(head,seqSize,tail);
                break;
            }
            case FLOAT:{
                final var wayPoints=new FloatDblLnkSeq.Node[numWorkers + 1];
                for(int i=0;i < wayPoints.length;++i){
                    wayPoints[i]=new FloatDblLnkSeq.Node(0F);
                }
                for(int i=0;i < numWorkers;){
                    final var threadHead=wayPoints[i];
                    final var threadTail=wayPoints[++i];
                    TestExecutorService.submitTest(()->{
                        var curr=threadHead;
                        for(int j=threadSpan;--j >= 0;){
                            curr=curr.next=new FloatDblLnkSeq.Node(curr,0F);
                        }
                        threadTail.prev=curr;
                        curr.next=threadTail;
                    });
                }
                TestExecutorService.completeAllTests();
                final var head=wayPoints[0];
                final var tail=wayPoints[wayPoints.length - 1];
                checkedRoot=new FloatDblLnkSeq.CheckedList(head,seqSize,tail);
                uncheckedRoot=new FloatDblLnkSeq.UncheckedList(head,seqSize,tail);
                break;
            }
            default:
                throw collectionType.invalid();
            }
            collectionType.verifyMASSIVEToString(uncheckedRoot.toString(),seqSize,
                    collectionType.classPrefix + "DblLnkSeq.UncheckedList.testMASSIVEtoString");
            collectionType.verifyMASSIVEToString(uncheckedRoot.subList(0,seqSize).toString(),seqSize,
                    collectionType.classPrefix + "DblLnkSeq.UncheckedSubList.testMASSIVEtoString");
            collectionType.verifyMASSIVEToString(checkedRoot.toString(),seqSize,
                    collectionType.classPrefix + "DblLnkSeq.CheckedList.testMASSIVEtoString");
            collectionType.verifyMASSIVEToString(checkedRoot.subList(0,seqSize).toString(),seqSize,
                    collectionType.classPrefix + "DblLnkSeq.CheckedSubList.testMASSIVEtoString");
        }
    }
    @Test
    public void testoffer_val(){
        final DequeAddTest test=(monitor,inputVal,inputType,functionCallType)->Assertions
                .assertTrue(monitor.verifyOffer(inputVal,inputType,functionCallType));
        test.runAllTests("DblLnkSeqTest.testoffer_val");
    }
    @Test
    public void testofferFirst_val(){
        final DequeAddTest test=(monitor,inputVal,inputType,functionCallType)->Assertions
                .assertTrue(monitor.verifyOfferFirst(inputVal,inputType,functionCallType));
        test.runAllTests("DblLnkSeqTest.testofferFirst_val");
    }
    @Test
    public void testofferLast_val(){
        final DequeAddTest test=(monitor,inputVal,inputType,functionCallType)->Assertions
                .assertTrue(monitor.verifyOfferLast(inputVal,inputType,functionCallType));
        test.runAllTests("DblLnkSeqTest.testofferLast_val");
    }
    @Test
    public void testpeek_void(){
        final DeqGetTest test=DblLnkSeqMonitor::verifyPeek;
        test.runAllGetFirstTests("DblLnkSeqTest.testpeek_void",false);
    }
    @Test
    public void testpeekFirst_void(){
        final DeqGetTest test=DblLnkSeqMonitor::verifyPeekFirst;
        test.runAllGetFirstTests("DblLnkSeqTest.testpeekFirst_void",false);
    }
    @Test
    public void testpeekLast_void(){
        final DeqGetTest test=DblLnkSeqMonitor::verifyPeekLast;
        test.runAllGetLastTests("DblLnkSeqTest.testpeekLast_void",false);
    }
    @Test
    public void testpoll_void(){
        final DeqPopTest test=DblLnkSeqMonitor::verifyPoll;
        test.runAllTests("DblLnkSeqTest.testpoll_void",false);
    }
    @Test
    public void testpollFirst_void(){
        final DeqPopTest test=DblLnkSeqMonitor::verifyPollFirst;
        test.runAllTests("DblLnkSeqTest.testpollFirst_void",false);
    }
    @Test
    public void testpollLast_void(){
        final DeqPopTest test=DblLnkSeqMonitor::verifyPollLast;
        test.runAllTests("DblLnkSeqTest.testpollLast_void",false);
    }
    @Test
    public void testpop_void(){
        final DeqPopTest test=DblLnkSeqMonitor::verifyPop;
        test.runAllTests("DblLnkSeqTest.testpop_void",true);
    }
    @Test
    public void testpush_val(){
        final DequeAddTest test=DblLnkSeqMonitor::verifyPush;
        test.runAllTests("DblLnkSeqTest.testpush_val");
    }
    @Test
    public void testput_intval(){
        final BasicTest test=(monitor,illegalMod)->{
            final int size=monitor.size();
            final var inputTypes=monitor.getDataType().mayBeAddedTo();
            if(illegalMod.expectedException == null){
                final var checked=monitor.getCheckedType().checked;
                for(final var inputType:inputTypes){
                    for(final var functionCallType:inputType.validFunctionCalls){
                        if(checked){
                            Assertions.assertThrows(IndexOutOfBoundsException.class,()->monitor.verifyPut(-1,
                                    inputType.convertValUnchecked(0),inputType,functionCallType));
                            Assertions.assertThrows(IndexOutOfBoundsException.class,()->monitor.verifyPut(size,
                                    inputType.convertValUnchecked(size + 1),inputType,functionCallType));
                        }
                        for(int index=0;index < size;++index){
                            monitor.verifyPut(index,inputType.convertValUnchecked(index + 1),inputType,
                                    functionCallType);
                        }
                    }
                }
            }else{
                monitor.illegalMod(illegalMod);
                for(final var inputType:inputTypes){
                    for(final var functionCallType:inputType.validFunctionCalls){
                        for(int tmpIndex=-1;tmpIndex <= size;++tmpIndex){
                            final int index=tmpIndex;
                            Assertions.assertThrows(illegalMod.expectedException,()->monitor.verifyPut(index,
                                    inputType.convertValUnchecked(index + 1),inputType,functionCallType));
                        }
                    }
                }
            }
        };
        test.runAllTests("DblLnkSeqTest.testput_intval");
    }
    @Test
    public void testReadAndWrite(){
        final MonitoredFunctionTest<MonitoredSequence<?>> test=(monitor,functionGen,functionCallType,illegalMod,
                randSeed)->{
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
        test.runAllTests("DblLnkSeqTest.testReadAndWrite",0);
    }
    @Test
    public void testremove_void(){
        final DeqPopTest test=DblLnkSeqMonitor::verifyRemove;
        test.runAllTests("DblLnkSeqTest.testremove_void",true);
    }
    @Test
    public void testremoveAt_int(){
        QUICK_INIT_PARAMS.forEach((collectionType,checkedTypeToInitParams)->{
            checkedTypeToInitParams.forEach((checkedType,initParamArray)->{
                for(final var position:POSITIONS){
                    if(checkedType.checked || position >= 0){
                        for(final int size:MEDIUM_SIZES){
                            if(size > 0 || position < 0){
                                for(final var initParams:initParamArray){
                                    for(final var illegalMod:initParams.structType.validPreMods){
                                        if(illegalMod.minDepth <= initParams.preAllocs.length
                                                && (initParams.checkedType.checked
                                                        || illegalMod.expectedException == null)){
                                            if(illegalMod.expectedException == null || position < 0){
                                                for(final var outputType:collectionType.validOutputTypes()){
                                                    TestExecutorService.submitTest(()->{
                                                        final var monitor=SequenceInitialization.Ascending
                                                                .initialize(getMonitoredList(initParams,size),size,0);
                                                        if(illegalMod.expectedException == null){
                                                            if(position < 0){
                                                                Assertions.assertThrows(IndexOutOfBoundsException.class,
                                                                        ()->monitor.verifyRemoveAt(-1,outputType));
                                                                Assertions.assertThrows(IndexOutOfBoundsException.class,
                                                                        ()->monitor.verifyRemoveAt(size,outputType));
                                                            }else{
                                                                for(int i=0;i < size;++i){
                                                                    monitor.verifyRemoveAt(
                                                                            Math.min(size - i - 1,Math.max(0,
                                                                                    (int)Math.round(
                                                                                            position * (size - i)))),
                                                                            outputType);
                                                                }
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
                    }
                }
            });
        });
        TestExecutorService.completeAllTests("DblLnkSeqTest.testremoveAt_int");
    }
    @Test
    public void testremoveFirst_void(){
        final DeqPopTest test=DblLnkSeqMonitor::verifyRemoveFirst;
        test.runAllTests("DblLnkSeqTest.testremoveFirst_void",true);
    }
    @Test
    public void testremoveFirstOccurrence_val(){
        final QueryTest<DblLnkSeqMonitor<?,?>> test=new QueryTest<>(){
            @Override
            public void callAndVerifyResult(DblLnkSeqMonitor<?,?> monitor,QueryVal queryVal,DataType inputType,
                    QueryCastType castType,QueryValModification modification,MonitoredObjectGen monitoredObjectGen,
                    double position,int seqSize){
                if(monitoredObjectGen == null){
                    Assertions.assertEquals(position >= 0,
                            monitor.verifyRemoveFirstOccurrence(queryVal,inputType,castType,modification));
                }else{
                    monitor.verifyThrowingRemoveFirstOccurrence(monitoredObjectGen);
                }
            }
            public boolean skipParams(SequenceInitParams params,int size,double position){
                return false;
            }
        };
        test.runAllTests("DblLnkSeqTest.removeFirstOccurrence_val",false);
    }
    @Test
    public void testremoveIf_Predicate(){
        QUICK_INIT_PARAMS.forEach((collectionType,checkedTypeToInitParams)->{
            if(collectionType == DataType.BOOLEAN){
                checkedTypeToInitParams.forEach((checkedType,initParamArray)->{
                    for(final var initParams:initParamArray){
                        for(final var functionCallType:collectionType.validFunctionCalls){
                            for(final var filterGen:initParams.structType.validMonitoredRemoveIfPredicateGens){
                                for(int tmpSize=0;tmpSize <= 10;++tmpSize){
                                    if((tmpSize == 0 || filterGen.expectedException == null
                                            || checkedType.checked && filterGen.minDepth <= initParams.preAllocs.length)
                                            && (tmpSize < 2 || !functionCallType.boxed)){
                                        final int size=tmpSize;
                                        final int initValBound=size > 0?1:0;
                                        for(int tmpInitVal=0;tmpInitVal <= initValBound;++tmpInitVal){
                                            final int initVal=tmpInitVal;
                                            for(int tmpPeriod=0;tmpPeriod <= size;++tmpPeriod){
                                                final int period=tmpPeriod;
                                                for(final var illegalMod:initParams.structType.validPreMods){
                                                    if(illegalMod.expectedException == null || checkedType.checked
                                                            && illegalMod.minDepth <= initParams.preAllocs.length){
                                                        switch(filterGen.predicateGenCallType){
                                                        case Randomized:
                                                            if(!functionCallType.boxed && size > 0
                                                                    && illegalMod.expectedException == null
                                                                    && filterGen.expectedException == null
                                                                    && initParams.totalPreAlloc == 0
                                                                    && initParams.totalPostAlloc == 0){
                                                                for(long tmpRandSeed=0;tmpRandSeed <= 100;++tmpRandSeed){
                                                                    final long randSeed=tmpRandSeed;
                                                                    for(final var threshold:RANDOM_THRESHOLDS){
                                                                        TestExecutorService.submitTest(()->{
                                                                            final var monitor=SequenceInitialization.Ascending
                                                                                    .initialize(
                                                                                            getMonitoredList(initParams,
                                                                                                    size),
                                                                                            size,initVal,period);
                                                                            final var filter=filterGen
                                                                                    .getMonitoredRemoveIfPredicate(
                                                                                            monitor,threshold,
                                                                                            new Random(randSeed));
                                                                            monitor.verifyRemoveIf(filter,
                                                                                    functionCallType);
                                                                        });
                                                                    }
                                                                }
                                                                break;
                                                            }
                                                        case NonRandomized:
                                                            TestExecutorService.submitTest(()->{
                                                                final var monitor=SequenceInitialization.Ascending
                                                                        .initialize(getMonitoredList(initParams,size),
                                                                                size,initVal,period);
                                                                final var filter=filterGen
                                                                        .getMonitoredRemoveIfPredicate(monitor);
                                                                if(illegalMod.expectedException == null){
                                                                    if(size == 0
                                                                            || filterGen.expectedException == null){
                                                                        monitor.verifyRemoveIf(filter,functionCallType);
                                                                    }else{
                                                                        Assertions.assertThrows(
                                                                                filterGen.expectedException,
                                                                                ()->monitor.verifyRemoveIf(filter,
                                                                                        functionCallType));
                                                                    }
                                                                }else{
                                                                    monitor.illegalMod(illegalMod);
                                                                    Assertions.assertThrows(
                                                                            illegalMod.expectedException,
                                                                            ()->monitor.verifyRemoveIf(filter,
                                                                                    functionCallType));
                                                                }
                                                            });
                                                            break;
                                                        default:
                                                            throw filterGen.invalid();
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
                });
            }else{
                checkedTypeToInitParams.forEach((checkedType,initParamArray)->{
                    for(final var initParams:initParamArray){
                        for(final var functionCallType:collectionType.validFunctionCalls){
                            for(final var filterGen:initParams.structType.validMonitoredRemoveIfPredicateGens){
                                if(filterGen.expectedException == null
                                        || checkedType.checked && filterGen.minDepth <= initParams.preAllocs.length){
                                    switch(filterGen.predicateGenCallType){
                                    case Randomized:
                                        if(!functionCallType.boxed && filterGen.expectedException == null
                                                && initParams.totalPreAlloc == 0 && initParams.totalPostAlloc == 0){
                                            for(long tmpRandSeed=0;tmpRandSeed <= 100;++tmpRandSeed){
                                                final long randSeed=tmpRandSeed;
                                                for(final var threshold:RANDOM_THRESHOLDS){
                                                    TestExecutorService.submitTest(()->{
                                                        final var monitor=SequenceInitialization.Ascending
                                                                .initialize(getMonitoredList(initParams,100),100,0);
                                                        final var filter=filterGen.getMonitoredRemoveIfPredicate(
                                                                monitor,threshold,new Random(randSeed));
                                                        for(;;){
                                                            monitor.verifyRemoveIf(filter,functionCallType);
                                                            if(monitor.isEmpty()){
                                                                break;
                                                            }
                                                            filter.reset(monitor);
                                                        }
                                                    });
                                                }
                                            }
                                            break;
                                        }
                                    case NonRandomized:
                                        for(final var illegalMod:initParams.structType.validPreMods){
                                            if(illegalMod.expectedException == null || checkedType.checked
                                                    && illegalMod.minDepth <= initParams.preAllocs.length){
                                                for(final int size:REMOVE_IF_SIZES){
                                                    if((size == 0 || checkedType.checked
                                                            || filterGen.expectedException == null)
                                                            && (size < 2 || !functionCallType.boxed)){
                                                        TestExecutorService.submitTest(()->{
                                                            final var monitor=SequenceInitialization.Ascending
                                                                    .initialize(getMonitoredList(initParams,size),size,
                                                                            0,0);
                                                            final var filter=filterGen
                                                                    .getMonitoredRemoveIfPredicate(monitor);
                                                            if(illegalMod.expectedException == null){
                                                                if(size == 0 || filterGen.expectedException == null){
                                                                    monitor.verifyRemoveIf(filter,functionCallType);
                                                                }else{
                                                                    Assertions.assertThrows(filterGen.expectedException,
                                                                            ()->monitor.verifyRemoveIf(filter,
                                                                                    functionCallType));
                                                                }
                                                            }else{
                                                                monitor.illegalMod(illegalMod);
                                                                Assertions.assertThrows(illegalMod.expectedException,
                                                                        ()->monitor.verifyRemoveIf(filter,
                                                                                functionCallType));
                                                            }
                                                        });
                                                    }
                                                }
                                            }
                                        }
                                        break;
                                    default:
                                        throw filterGen.invalid();
                                    }
                                }
                            }
                        }
                    }
                });
            }
        });
        TestExecutorService.completeAllTests("DblLnkSeqTest.testremoveIf_Predicate");
    }
    @Test
    public void testremoveLast_void(){
        final DeqPopTest test=DblLnkSeqMonitor::verifyRemoveLast;
        test.runAllTests("DblLnkSeqTest.testremoveLast_void",true);
    }
    @Test
    public void testremoveLastOccurrence_val(){
        final QueryTest<DblLnkSeqMonitor<?,?>> test=new QueryTest<>(){
            @Override
            public void callAndVerifyResult(DblLnkSeqMonitor<?,?> monitor,QueryVal queryVal,DataType inputType,
                    QueryCastType castType,QueryValModification modification,MonitoredObjectGen monitoredObjectGen,
                    double position,int seqSize){
                if(monitoredObjectGen == null){
                    Assertions.assertEquals(position >= 0,
                            monitor.verifyRemoveLastOccurrence(queryVal,inputType,castType,modification));
                }else{
                    monitor.verifyThrowingRemoveLastOccurrence(monitoredObjectGen);
                }
            }
            public boolean skipParams(SequenceInitParams params,int size,double position){
                return false;
            }
        };
        test.runAllTests("DblLnkSeqTest.removeLastOccurrence)val",false);
    }
    @Test
    public void testremoveVal_val(){
        final QueryTest<MonitoredSequence<?>> test=new QueryTest<>(){
            @Override
            public void callAndVerifyResult(MonitoredSequence<?> monitor,QueryVal queryVal,DataType inputType,
                    QueryCastType castType,QueryValModification modification,MonitoredObjectGen monitoredObjectGen,
                    double position,int seqSize){
                if(monitoredObjectGen == null){
                    Assertions.assertEquals(position >= 0,
                            monitor.verifyRemoveVal(queryVal,inputType,castType,modification));
                }else{
                    monitor.verifyThrowingRemoveVal(monitoredObjectGen);
                }
            }
            @Override
            public boolean cmeFilter(IllegalModification illegalMod,DataType inputType,DataType collectionType,
                    QueryVal queryVal,QueryVal.QueryValModification modification,QueryCastType castType){
                return illegalMod.expectedException == null || collectionType != DataType.REF
                        && queryVal == QueryVal.Null && castType != QueryCastType.ToObject;
            }
            public boolean skipParams(SequenceInitParams params,int size,double position){
                return false;
            }
        };
        test.runAllTests("DblLnkSeqTest.testremoveVal_val",true);
    }
    @Test
    public void testreplaceAll_UnaryOperator(){
        final MonitoredFunctionTest<MonitoredList<?>> test=(monitor,functionGen,functionCallType,illegalMod,randSeed)->{
            if(illegalMod.expectedException == null){
                if(functionGen.expectedException == null || monitor.isEmpty()){
                    monitor.verifyReplaceAll(functionGen,functionCallType,randSeed);
                }else{
                    Assertions.assertThrows(functionGen.expectedException,
                            ()->monitor.verifyReplaceAll(functionGen,functionCallType,randSeed));
                }
            }else{
                monitor.illegalMod(illegalMod);
                Assertions.assertThrows(illegalMod.expectedException,
                        ()->monitor.verifyReplaceAll(functionGen,functionCallType,randSeed));
            }
        };
        test.runAllTests("DblLnkSeqTest.testreplaceAll_UnaryOperator",100);
    }
    @Test
    public void testsearch_val(){
        final QueryTest<DblLnkSeqMonitor<?,?>> test=(monitor,queryVal,inputType,castType,modification,
                monitoredObjectGen,position,seqSize)->{
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
        test.runAllTests("DblLnkSeqTest.testsearch_val",false);
    }
    @Test
    public void testset_intval(){
        final BasicTest test=(monitor,illegalMod)->{
            final int size=monitor.size();
            final var collectionType=monitor.getDataType();
            if(illegalMod.expectedException == null){
                final var checked=monitor.getCheckedType().checked;
                for(final var functionCallType:collectionType.validFunctionCalls){
                    if(checked){
                        Assertions.assertThrows(IndexOutOfBoundsException.class,
                                ()->monitor.verifySet(-1,collectionType.convertValUnchecked(0),functionCallType));
                        Assertions.assertThrows(IndexOutOfBoundsException.class,()->monitor.verifySet(size,
                                collectionType.convertValUnchecked(size + 1),functionCallType));
                    }
                    for(int index=0;index < size;++index){
                        monitor.verifySet(index,collectionType.convertValUnchecked(index + 1),functionCallType);
                    }
                }
            }else{
                monitor.illegalMod(illegalMod);
                for(final var functionCallType:collectionType.validFunctionCalls){
                    for(int tmpIndex=-1;tmpIndex <= size;++tmpIndex){
                        final int index=tmpIndex;
                        Assertions.assertThrows(illegalMod.expectedException,()->monitor.verifySet(index,
                                collectionType.convertValUnchecked(index + 1),functionCallType));
                    }
                }
            }
        };
        test.runAllTests("DblLnkSeqTest.testset_intval");
    }
    @Test
    public void testsize_void(){
        final BasicTest test=(monitor,illegalMod)->{
            if(illegalMod.expectedException == null){
                monitor.verifySize();
            }else{
                monitor.illegalMod(illegalMod);
                Assertions.assertThrows(illegalMod.expectedException,()->monitor.verifySize());
            }
        };
        test.runAllTests("DblLnkSeqTest.testsize_void");
    }
    @Test
    public void testsort_Comparator(){
        QUICK_INIT_PARAMS.forEach((collectionType,checkedTypeToInitParams)->{
            checkedTypeToInitParams.forEach((checkedType,initParamArray)->{
                for(final var initParams:initParamArray){
                    if(initParams.totalPreAlloc <= 2 && initParams.totalPostAlloc <= 2){
                        for(final var comparatorGen:initParams.structType.validComparatorGens){
                            if(comparatorGen.throwsContractViolationException
                                    && initParams.collectionType == DataType.BOOLEAN){
                                continue;
                            }
                            if(collectionType == DataType.REF || comparatorGen.validWithPrimitive){
                                for(final var size:SIZES){
                                    if(size < 2 || comparatorGen.expectedException == null || checkedType.checked
                                            && comparatorGen.minDepth <= initParams.preAllocs.length){
                                        for(final var functionCallType:collectionType.validFunctionCalls){
                                            for(final var illegalMod:initParams.structType.validPreMods){
                                                if(illegalMod.minDepth <= initParams.preAllocs.length
                                                        && (initParams.checkedType.checked
                                                                || illegalMod.expectedException == null)){
                                                    TestExecutorService.submitTest(()->{
                                                        final var monitor=getMonitoredList(initParams,size);
                                                        if(illegalMod.expectedException == null){
                                                            if(size < 2 || comparatorGen.expectedException == null){
                                                                monitor.verifyStableSort(size,comparatorGen,
                                                                        functionCallType);
                                                            }else{
                                                                Assertions.assertThrows(comparatorGen.expectedException,
                                                                        ()->monitor.verifyStableSort(size,comparatorGen,
                                                                                functionCallType));
                                                            }
                                                        }else{
                                                            monitor.illegalMod(illegalMod);
                                                            Assertions.assertThrows(illegalMod.expectedException,
                                                                    ()->monitor.verifyStableSort(size,comparatorGen,
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
            });
        });
        TestExecutorService.completeAllTests("DblLnkSeqTest.testsort_Comparator");
    }
    @Test
    public void teststableAscendingSort_void(){
        final NonComparatorSortTest test=MonitoredList::verifyAscendingStableSort;
        test.runStableTests("DblLnkSeqTest.teststableAscendingSort_void");
    }
    @Test
    public void teststableDescendingSort_void(){
        final NonComparatorSortTest test=MonitoredList::verifyDescendingStableSort;
        test.runStableTests("DblLnkSeqTest.teststableDescendingSort_void");
    }
    @Test
    public void testsubList_intint(){
        for(final var size:MEDIUM_SIZES){
            final int inc=Math.max(1,size / 10);
            QUICK_INIT_PARAMS.forEach((collectionType,checkedTypeToInitParams)->{
                checkedTypeToInitParams.forEach((checkedType,initParamArray)->{
                    for(final var initParams:initParamArray){
                        for(final var illegalMod:initParams.structType.validPreMods){
                            if(illegalMod.minDepth <= initParams.preAllocs.length
                                    && (initParams.checkedType.checked || illegalMod.expectedException == null)){
                                TestExecutorService.submitTest(()->{
                                    final var rootMonitor=SequenceInitialization.Ascending
                                            .initialize(getMonitoredList(initParams,size),size,0);
                                    if(illegalMod.expectedException == null){
                                        int tmpFromIndex;
                                        final int fromBound;
                                        final int toBound;
                                        if(checkedType.checked){
                                            tmpFromIndex=-(2 * inc);
                                            fromBound=size + inc;
                                            toBound=size + 2 * inc;
                                        }else{
                                            tmpFromIndex=0;
                                            fromBound=size;
                                            toBound=size;
                                        }
                                        for(;tmpFromIndex <= fromBound;tmpFromIndex+=inc){
                                            final int fromIndex=tmpFromIndex;
                                            for(int tmpToIndex=checkedType.checked?fromIndex - inc
                                                    :fromIndex;tmpToIndex <= toBound;tmpToIndex+=inc){
                                                final int toIndex=tmpToIndex;
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
                                            }
                                        }
                                    }else{
                                        rootMonitor.illegalMod(illegalMod);
                                        for(int tmpFromIndex=-(2 * inc),fromBound=size
                                                + inc;tmpFromIndex <= fromBound;tmpFromIndex+=inc){
                                            final int fromIndex=tmpFromIndex;
                                            for(int tmpToIndex=fromIndex - inc,toBound=size
                                                    + 2 * inc;tmpToIndex <= toBound;tmpToIndex+=inc){
                                                final int toIndex=tmpToIndex;
                                                Assertions.assertThrows(illegalMod.expectedException,()->{
                                                    try{
                                                        rootMonitor.getMonitoredSubList(fromIndex,toIndex);
                                                    }finally{
                                                        rootMonitor.verifyCollectionState();
                                                    }
                                                });
                                            }
                                        }
                                    }
                                });
                            }
                        }
                    }
                });
            });
        }
        TestExecutorService.completeAllTests("DblLnkSeqTest.testsubList_intint");
    }
    @Test
    public void testtoArray_IntFunction(){
        final MonitoredFunctionTest<MonitoredSequence<?>> test=(monitor,functionGen,functionCallType,illegalMod,
                randSeed)->{
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
        test.runAllTests("DblLnkSeqTest.testtoArray_IntFunction",0);
    }
    @Test
    public void testtoArray_ObjectArray(){
        QUICK_INIT_PARAMS.forEach((collectionType,checkedTypeToInitParams)->{
            checkedTypeToInitParams.forEach((checkedType,initParamArray)->{
                for(final var initParams:initParamArray){
                    if(initParams.totalPreAlloc == 0 && initParams.totalPostAlloc == 0){
                        for(final var illegalMod:initParams.structType.validPreMods){
                            if(illegalMod.minDepth <= initParams.preAllocs.length
                                    && (initParams.checkedType.checked || illegalMod.expectedException == null)){
                                for(final int size:SIZES){
                                    for(int tmpArrSize=0,tmpArrSizeBound=size
                                            + 5;tmpArrSize <= tmpArrSizeBound;++tmpArrSize){
                                        final int arrSize=tmpArrSize;
                                        TestExecutorService.submitTest(()->{
                                            final var monitor=SequenceInitialization.Ascending
                                                    .initialize(getMonitoredList(initParams,size),size,0);
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
                }
            });
        });
        TestExecutorService.completeAllTests("DblLnkSeqTest.testtoArray_ObjectArray");
    }
    @Test
    public void testtoArray_void(){
        final BasicTest test=(monitor,illegalMod)->{
            final var outputTypes=monitor.getDataType().validOutputTypes();
            if(illegalMod.expectedException == null){
                for(final var outputType:outputTypes){
                    outputType.verifyToArray(monitor);
                }
            }else{
                monitor.illegalMod(illegalMod);
                for(final var outputType:outputTypes){
                    Assertions.assertThrows(illegalMod.expectedException,()->outputType.verifyToArray(monitor));
                }
            }
        };
        test.runAllTests("DblLnkSeqTest.testtoArray_void");
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
        test.runAllTests("DblLnkSeqTest.testtoString_void");
    }
    @Test
    public void testunstableAscendingSort_void(){
        final NonComparatorSortTest test=MonitoredList::verifyAscendingUnstableSort;
        test.runUnstableTests("DblLnkSeqTest.testunstableAscendingSort_void");
    }
    @Test
    public void testunstableDescendingSort_void(){
        final NonComparatorSortTest test=MonitoredList::verifyDescendingUnstableSort;
        test.runUnstableTests("DblLnkSeqTest.testunstableDescendingSort_void");
    }
    @Test
    public void testunstableSort_Comparator(){
        QUICK_INIT_PARAMS.forEach((collectionType,checkedTypeToInitParams)->{
            if(collectionType != DataType.BOOLEAN){
                checkedTypeToInitParams.forEach((checkedType,initParamArray)->{
                    for(final var initParams:initParamArray){
                        for(final var comparatorGen:initParams.structType.validComparatorGens){
                            if(collectionType == DataType.REF || comparatorGen.validWithPrimitive){
                                for(final var size:MEDIUM_SIZES){
                                    if(size < 2 || comparatorGen.expectedException == null || checkedType.checked
                                            && comparatorGen.minDepth <= initParams.preAllocs.length){
                                        for(final var illegalMod:initParams.structType.validPreMods){
                                            if(illegalMod.minDepth <= initParams.preAllocs.length
                                                    && (initParams.checkedType.checked
                                                            || illegalMod.expectedException == null)){
                                                TestExecutorService.submitTest(()->{
                                                    final var monitor=getMonitoredList(initParams,size);
                                                    if(illegalMod.expectedException == null){
                                                        if(size < 2 || comparatorGen.expectedException == null){
                                                            monitor.verifyUnstableSort(size,comparatorGen);
                                                        }else{
                                                            Assertions.assertThrows(comparatorGen.expectedException,
                                                                    ()->monitor.verifyUnstableSort(size,comparatorGen));
                                                        }
                                                    }else{
                                                        monitor.illegalMod(illegalMod);
                                                        Assertions.assertThrows(illegalMod.expectedException,
                                                                ()->monitor.verifyUnstableSort(size,comparatorGen));
                                                    }
                                                });
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
            }
        });
        TestExecutorService.completeAllTests("DblLnkSeqTest.testunstableSort_Comparator");
    }
    private static class DblLnkSeqMonitor<LSTDEQ extends AbstractOmniCollection<E>&OmniDeque<E>&OmniList<E>&Externalizable,E>
            extends
            AbstractSequenceMonitor<LSTDEQ>
            implements
            MonitoredDeque<LSTDEQ>,
            MonitoredList<LSTDEQ>{
        DblLnkSeqMonitor(CheckedType checkedType,DataType dataType,Collection<?> collection,Class<? extends Collection<?>> collectionClass){
            super(checkedType,dataType,collection,collectionClass);
            updateCollectionState();
        }
        DblLnkSeqMonitor(CheckedType checkedType,DataType dataType){
            super(checkedType,dataType);
            updateCollectionState();
        }
        DblLnkSeqMonitor(CheckedType checkedType,DataType dataType,int capacity){
            super(checkedType,dataType,capacity);
            updateCollectionState();
        }
        DblLnkSeqMonitor(SequenceInitParams initParams,int initCapacity){
            super(initParams.checkedType,initParams.collectionType,initCapacity);
            updateCollectionState();
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
        @Override
        public MonitoredIterator<?,LSTDEQ> getMonitoredDescendingIterator(){
            return new DescendingItrMonitor();
        }
        @Override
        public MonitoredIterator<? extends OmniIterator<?>,LSTDEQ> getMonitoredIterator(){
            return new ItrMonitor();
        }
        @Override
        public MonitoredIterator<? extends OmniIterator<?>,LSTDEQ> getMonitoredIterator(int index,IteratorType itrType){
            switch(itrType){
            case BidirectionalItr:
                return getMonitoredListIterator(index);
            case DescendingItr:
                return getMonitoredDescendingIterator(index);
            case AscendingItr:
                return getMonitoredIterator(index);
            default:
                throw itrType.invalid();
            }
        }
        @Override
        public MonitoredIterator<? extends OmniIterator<?>,LSTDEQ> getMonitoredIterator(IteratorType itrType){
            switch(itrType){
            case AscendingItr:
                return getMonitoredIterator();
            case BidirectionalItr:
                return getMonitoredListIterator();
            case DescendingItr:
                return getMonitoredDescendingIterator();
            default:
                throw itrType.invalid();
            }
        }
        @Override
        public MonitoredListIterator<? extends OmniListIterator<?>,LSTDEQ> getMonitoredListIterator(){
            return new ListItrMonitor();
        }
        @Override
        public MonitoredListIterator<? extends OmniListIterator<?>,LSTDEQ> getMonitoredListIterator(int index){
            return new ListItrMonitor(index);
        }
        @Override
        public MonitoredList<?> getMonitoredSubList(int fromIndex,int toIndex){
            return new SubListMonitor<>(this,fromIndex,toIndex);
        }
        @Override
        public StructType getStructType(){
            return StructType.DblLnkList;
        }
        @Override
        public void modCollection(){
            switch(dataType){
            case BOOLEAN:
                ++((BooleanDblLnkSeq.CheckedList)seq).modCount;
                break;
            case BYTE:
                ++((ByteDblLnkSeq.CheckedList)seq).modCount;
                break;
            case CHAR:
                ++((CharDblLnkSeq.CheckedList)seq).modCount;
                break;
            case DOUBLE:
                ++((DoubleDblLnkSeq.CheckedList)seq).modCount;
                break;
            case FLOAT:
                ++((FloatDblLnkSeq.CheckedList)seq).modCount;
                break;
            case INT:
                ++((IntDblLnkSeq.CheckedList)seq).modCount;
                break;
            case LONG:
                ++((LongDblLnkSeq.CheckedList)seq).modCount;
                break;
            case REF:
                ++((RefDblLnkSeq.CheckedList<?>)seq).modCount;
                break;
            case SHORT:
                ++((ShortDblLnkSeq.CheckedList)seq).modCount;
                break;
            default:
                throw dataType.invalid();
            }
            ++expectedModCount;
        }
        @Override
        public Object removeFirst(){
            final var removed=seq.removeFirst();
            super.updateRemoveIndexState(0);
            return removed;
        }
        @Override
        public void updateRemoveFirstState(){
            updateRemoveIndexState(0);
        }
        @Override
        public void updateRemoveLastState(){
            updateRemoveIndexState(expectedSize - 1);
        }
        @Override
        public void verifyArrayIsCopy(Object arr,boolean emptyArrayMayBeSame){
            // nothing to do
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
        public void verifyCollectionState(boolean refIsSame){
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
                verifyRefIntegrity(refIsSame);
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
        public void verifyGetFirstResult(Object result,DataType outputType){
            verifyGetResult(0,result,outputType);
        }
        @Override
        public void verifyGetLastResult(Object result,DataType outputType){
            verifyGetResult(expectedSize - 1,result,outputType);
        }
        @Override
        public Object verifyPeek(DataType outputType){
            return MonitoredDeque.super.verifyPeek(outputType);
        }
        @Override
        public Object verifyPoll(DataType outputType){
            return MonitoredDeque.super.verifyPoll(outputType);
        }
        @Override
        public void verifyReadAndWriteClone(LSTDEQ readCol){
            verifyRefClone(readCol,false);
        }
        @SuppressWarnings("unchecked")
        @Override
        LSTDEQ initSeq(){
            switch(dataType){
            case BOOLEAN:
                if(checkedType.checked){
                    return (LSTDEQ)new BooleanDblLnkSeq.CheckedList();
                }else{
                    return (LSTDEQ)new BooleanDblLnkSeq.UncheckedList();
                }
            case BYTE:
                if(checkedType.checked){
                    return (LSTDEQ)new ByteDblLnkSeq.CheckedList();
                }else{
                    return (LSTDEQ)new ByteDblLnkSeq.UncheckedList();
                }
            case CHAR:
                if(checkedType.checked){
                    return (LSTDEQ)new CharDblLnkSeq.CheckedList();
                }else{
                    return (LSTDEQ)new CharDblLnkSeq.UncheckedList();
                }
            case SHORT:
                if(checkedType.checked){
                    return (LSTDEQ)new ShortDblLnkSeq.CheckedList();
                }else{
                    return (LSTDEQ)new ShortDblLnkSeq.UncheckedList();
                }
            case INT:
                if(checkedType.checked){
                    return (LSTDEQ)new IntDblLnkSeq.CheckedList();
                }else{
                    return (LSTDEQ)new IntDblLnkSeq.UncheckedList();
                }
            case LONG:
                if(checkedType.checked){
                    return (LSTDEQ)new LongDblLnkSeq.CheckedList();
                }else{
                    return (LSTDEQ)new LongDblLnkSeq.UncheckedList();
                }
            case FLOAT:
                if(checkedType.checked){
                    return (LSTDEQ)new FloatDblLnkSeq.CheckedList();
                }else{
                    return (LSTDEQ)new FloatDblLnkSeq.UncheckedList();
                }
            case DOUBLE:
                if(checkedType.checked){
                    return (LSTDEQ)new DoubleDblLnkSeq.CheckedList();
                }else{
                    return (LSTDEQ)new DoubleDblLnkSeq.UncheckedList();
                }
            case REF:
                if(checkedType.checked){
                    return (LSTDEQ)new RefDblLnkSeq.CheckedList<>();
                }else{
                    return (LSTDEQ)new RefDblLnkSeq.UncheckedList<>();
                }
            default:
                throw dataType.invalid();
            }
        }
        @Override
        LSTDEQ initSeq(int initCap){
            return initSeq();
        }
        @SuppressWarnings("unchecked")
        @Override
        LSTDEQ initSeq(Collection<?> collection,Class<? extends Collection<?>> collectionClass){
            Class<?> clazz;
            switch(this.dataType) {
            case BOOLEAN:
                if(this.checkedType.checked) {
                    clazz=BooleanDblLnkSeq.CheckedList.class;
                }else {
                    clazz=BooleanDblLnkSeq.UncheckedList.class;
                }
                break;
            case BYTE:
                if(this.checkedType.checked) {
                    clazz=ByteDblLnkSeq.CheckedList.class;
                }else {
                    clazz=ByteDblLnkSeq.UncheckedList.class;
                }
                break;
            case CHAR:
                if(this.checkedType.checked) {
                    clazz=CharDblLnkSeq.CheckedList.class;
                }else {
                    clazz=CharDblLnkSeq.UncheckedList.class;
                }
                break;
            case SHORT:
                if(this.checkedType.checked) {
                    clazz=ShortDblLnkSeq.CheckedList.class;
                }else {
                    clazz=ShortDblLnkSeq.UncheckedList.class;
                }
                break;
            case INT:
                if(this.checkedType.checked) {
                    clazz=IntDblLnkSeq.CheckedList.class;
                }else {
                    clazz=IntDblLnkSeq.UncheckedList.class;
                }
                break;
            case LONG:
                if(this.checkedType.checked) {
                    clazz=LongDblLnkSeq.CheckedList.class;
                }else {
                    clazz=LongDblLnkSeq.UncheckedList.class;
                }
                break;
            case FLOAT:
                if(this.checkedType.checked) {
                    clazz=FloatDblLnkSeq.CheckedList.class;
                }else {
                    clazz=FloatDblLnkSeq.UncheckedList.class;
                }
                break;
            case DOUBLE:
                if(this.checkedType.checked) {
                    clazz=DoubleDblLnkSeq.CheckedList.class;
                }else {
                    clazz=DoubleDblLnkSeq.UncheckedList.class;
                }
                break;
            case REF:
                if(this.checkedType.checked) {
                    clazz=RefDblLnkSeq.CheckedList.class;
                }else {
                    clazz=RefDblLnkSeq.UncheckedList.class;
                }
                break;
            default:
                throw this.dataType.invalid();
            }
            try{
                return (LSTDEQ)clazz.getDeclaredConstructor(collectionClass).newInstance(collection);
            }catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                    | NoSuchMethodException | SecurityException e){
                throw new Error(e);
            }
        }
        @Override
        void updateModCount(){
            switch(dataType){
            case BOOLEAN:
                expectedModCount=((BooleanDblLnkSeq.CheckedList)seq).modCount;
                break;
            case BYTE:
                expectedModCount=((ByteDblLnkSeq.CheckedList)seq).modCount;
                break;
            case CHAR:
                expectedModCount=((CharDblLnkSeq.CheckedList)seq).modCount;
                break;
            case DOUBLE:
                expectedModCount=((DoubleDblLnkSeq.CheckedList)seq).modCount;
                break;
            case FLOAT:
                expectedModCount=((FloatDblLnkSeq.CheckedList)seq).modCount;
                break;
            case INT:
                expectedModCount=((IntDblLnkSeq.CheckedList)seq).modCount;
                break;
            case LONG:
                expectedModCount=((LongDblLnkSeq.CheckedList)seq).modCount;
                break;
            case REF:
                expectedModCount=((RefDblLnkSeq.CheckedList<?>)seq).modCount;
                break;
            case SHORT:
                expectedModCount=((ShortDblLnkSeq.CheckedList)seq).modCount;
                break;
            default:
                throw dataType.invalid();
            }
        }
        private void copyBooleanListContents(){
            final int expectedSize=seq.size;
            final var cast=(BooleanDblLnkSeq)seq;
            var expectedArr=(boolean[])this.expectedArr;
            int expectedCapacity;
            if((expectedCapacity=this.expectedCapacity) < expectedSize){
                this.expectedArr=expectedArr=new boolean[this.expectedCapacity=expectedArr == OmniArray.OfBoolean.DEFAULT_ARR
                        && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP?OmniArray.DEFAULT_ARR_SEQ_CAP
                                :OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
            }
            var currNode=cast.tail;
            for(int i=expectedSize;;){
                if(currNode == null){
                    break;
                }
                expectedArr[--i]=currNode.val;
                currNode=currNode.prev;
            }
        }
        private void copyByteListContents(){
            final int expectedSize=seq.size;
            final var cast=(ByteDblLnkSeq)seq;
            var expectedArr=(byte[])this.expectedArr;
            int expectedCapacity;
            if((expectedCapacity=this.expectedCapacity) < expectedSize){
                this.expectedArr=expectedArr=new byte[this.expectedCapacity=expectedArr == OmniArray.OfByte.DEFAULT_ARR
                        && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP?OmniArray.DEFAULT_ARR_SEQ_CAP
                                :OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
            }
            var currNode=cast.tail;
            for(int i=expectedSize;;){
                if(currNode == null){
                    break;
                }
                expectedArr[--i]=currNode.val;
                currNode=currNode.prev;
            }
        }
        private void copyCharListContents(){
            final int expectedSize=seq.size;
            final var cast=(CharDblLnkSeq)seq;
            var expectedArr=(char[])this.expectedArr;
            int expectedCapacity;
            if((expectedCapacity=this.expectedCapacity) < expectedSize){
                this.expectedArr=expectedArr=new char[this.expectedCapacity=expectedArr == OmniArray.OfChar.DEFAULT_ARR
                        && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP?OmniArray.DEFAULT_ARR_SEQ_CAP
                                :OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
            }
            var currNode=cast.tail;
            for(int i=expectedSize;;){
                if(currNode == null){
                    break;
                }
                expectedArr[--i]=currNode.val;
                currNode=currNode.prev;
            }
        }
        private void copyDoubleListContents(){
            final int expectedSize=seq.size;
            final var cast=(DoubleDblLnkSeq)seq;
            var expectedArr=(double[])this.expectedArr;
            int expectedCapacity;
            if((expectedCapacity=this.expectedCapacity) < expectedSize){
                this.expectedArr=expectedArr=new double[this.expectedCapacity=expectedArr == OmniArray.OfDouble.DEFAULT_ARR
                        && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP?OmniArray.DEFAULT_ARR_SEQ_CAP
                                :OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
            }
            var currNode=cast.tail;
            for(int i=expectedSize;;){
                if(currNode == null){
                    break;
                }
                expectedArr[--i]=currNode.val;
                currNode=currNode.prev;
            }
        }
        private void copyFloatListContents(){
            final int expectedSize=seq.size;
            final var cast=(FloatDblLnkSeq)seq;
            var expectedArr=(float[])this.expectedArr;
            int expectedCapacity;
            if((expectedCapacity=this.expectedCapacity) < expectedSize){
                this.expectedArr=expectedArr=new float[this.expectedCapacity=expectedArr == OmniArray.OfFloat.DEFAULT_ARR
                        && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP?OmniArray.DEFAULT_ARR_SEQ_CAP
                                :OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
            }
            var currNode=cast.tail;
            for(int i=expectedSize;;){
                if(currNode == null){
                    break;
                }
                expectedArr[--i]=currNode.val;
                currNode=currNode.prev;
            }
        }
        private void copyIntListContents(){
            final int expectedSize=seq.size;
            final var cast=(IntDblLnkSeq)seq;
            var expectedArr=(int[])this.expectedArr;
            int expectedCapacity;
            if((expectedCapacity=this.expectedCapacity) < expectedSize){
                this.expectedArr=expectedArr=new int[this.expectedCapacity=expectedArr == OmniArray.OfInt.DEFAULT_ARR
                        && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP?OmniArray.DEFAULT_ARR_SEQ_CAP
                                :OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
            }
            var currNode=cast.tail;
            for(int i=expectedSize;;){
                if(currNode == null){
                    break;
                }
                expectedArr[--i]=currNode.val;
                currNode=currNode.prev;
            }
        }
        private void copyLongListContents(){
            final int expectedSize=seq.size;
            final var cast=(LongDblLnkSeq)seq;
            var expectedArr=(long[])this.expectedArr;
            int expectedCapacity;
            if((expectedCapacity=this.expectedCapacity) < expectedSize){
                this.expectedArr=expectedArr=new long[this.expectedCapacity=expectedArr == OmniArray.OfLong.DEFAULT_ARR
                        && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP?OmniArray.DEFAULT_ARR_SEQ_CAP
                                :OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
            }
            var currNode=cast.tail;
            for(int i=expectedSize;;){
                if(currNode == null){
                    break;
                }
                expectedArr[--i]=currNode.val;
                currNode=currNode.prev;
            }
        }
        private void copyRefListContents(){
            final int expectedSize=seq.size;
            int oldExpectedSize=this.expectedSize;
            @SuppressWarnings("unchecked")
            final var cast=(RefDblLnkSeq<E>)seq;
            var expectedArr=(Object[])this.expectedArr;
            int expectedCapacity;
            if((expectedCapacity=this.expectedCapacity) < expectedSize){
                this.expectedArr=expectedArr=new Object[this.expectedCapacity=expectedArr == OmniArray.OfRef.DEFAULT_ARR
                        && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP?OmniArray.DEFAULT_ARR_SEQ_CAP
                                :OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
            }
            var currNode=cast.tail;
            for(int i=expectedSize;;){
                if(currNode == null){
                    break;
                }
                expectedArr[--i]=currNode.val;
                currNode=currNode.prev;
            }
            while(oldExpectedSize > expectedSize){
                expectedArr[--oldExpectedSize]=null;
            }
        }
        private void copyShortListContents(){
            final int expectedSize=seq.size;
            final var cast=(ShortDblLnkSeq)seq;
            var expectedArr=(short[])this.expectedArr;
            int expectedCapacity;
            if((expectedCapacity=this.expectedCapacity) < expectedSize){
                this.expectedArr=expectedArr=new short[this.expectedCapacity=expectedArr == OmniArray.OfShort.DEFAULT_ARR
                        && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP?OmniArray.DEFAULT_ARR_SEQ_CAP
                                :OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
            }
            var currNode=cast.tail;
            for(int i=expectedSize;;){
                if(currNode == null){
                    break;
                }
                expectedArr[--i]=currNode.val;
                currNode=currNode.prev;
            }
        }
        private Object getNode(int index,AbstractOmniCollection<E> seq,int expectedSize){
            if((expectedSize-=index) <= index){
                // the node is closer to the tail
                if(expectedSize == 0){
                    return null;
                }
                return iterateDown(tail(seq),expectedSize - 1);
            }else{
                // the node is closer to the head
                return iterateUp(head(seq),index);
            }
        }
        private Object head(AbstractOmniCollection<E> seq){
            switch(dataType){
            case BOOLEAN:
                return ((BooleanDblLnkSeq)seq).head;
            case BYTE:
                return ((ByteDblLnkSeq)seq).head;
            case CHAR:
                return ((CharDblLnkSeq)seq).head;
            case DOUBLE:
                return ((DoubleDblLnkSeq)seq).head;
            case FLOAT:
                return ((FloatDblLnkSeq)seq).head;
            case INT:
                return ((IntDblLnkSeq)seq).head;
            case LONG:
                return ((LongDblLnkSeq)seq).head;
            case REF:
                return ((RefDblLnkSeq<E>)seq).head;
            case SHORT:
                return ((ShortDblLnkSeq)seq).head;
            default:
                throw dataType.invalid();
            }
        }
        private Object iterateDown(Object node,int dist){
            if(dist != 0){
                switch(dataType){
                case BOOLEAN:{
                    var cast=(BooleanDblLnkSeq.Node)node;
                    do{
                        cast=cast.prev;
                    }while(--dist > 0);
                    node=cast;
                    break;
                }
                case BYTE:{
                    var cast=(ByteDblLnkSeq.Node)node;
                    do{
                        cast=cast.prev;
                    }while(--dist > 0);
                    node=cast;
                    break;
                }
                case CHAR:{
                    var cast=(CharDblLnkSeq.Node)node;
                    do{
                        cast=cast.prev;
                    }while(--dist > 0);
                    node=cast;
                    break;
                }
                case SHORT:{
                    var cast=(ShortDblLnkSeq.Node)node;
                    do{
                        cast=cast.prev;
                    }while(--dist > 0);
                    node=cast;
                    break;
                }
                case INT:{
                    var cast=(IntDblLnkSeq.Node)node;
                    do{
                        cast=cast.prev;
                    }while(--dist > 0);
                    node=cast;
                    break;
                }
                case LONG:{
                    var cast=(LongDblLnkSeq.Node)node;
                    do{
                        cast=cast.prev;
                    }while(--dist > 0);
                    node=cast;
                    break;
                }
                case FLOAT:{
                    var cast=(FloatDblLnkSeq.Node)node;
                    do{
                        cast=cast.prev;
                    }while(--dist > 0);
                    node=cast;
                    break;
                }
                case DOUBLE:{
                    var cast=(DoubleDblLnkSeq.Node)node;
                    do{
                        cast=cast.prev;
                    }while(--dist > 0);
                    node=cast;
                    break;
                }
                case REF:{
                    @SuppressWarnings("unchecked")
                    var cast=(RefDblLnkSeq.Node<E>)node;
                    do{
                        cast=cast.prev;
                    }while(--dist > 0);
                    node=cast;
                    break;
                }
                default:
                    throw dataType.invalid();
                }
            }
            return node;
        }
        private Object iterateUp(Object node,int dist){
            if(dist != 0){
                switch(dataType){
                case BOOLEAN:{
                    var cast=(BooleanDblLnkSeq.Node)node;
                    do{
                        cast=cast.next;
                    }while(--dist > 0);
                    node=cast;
                    break;
                }
                case BYTE:{
                    var cast=(ByteDblLnkSeq.Node)node;
                    do{
                        cast=cast.next;
                    }while(--dist > 0);
                    node=cast;
                    break;
                }
                case CHAR:{
                    var cast=(CharDblLnkSeq.Node)node;
                    do{
                        cast=cast.next;
                    }while(--dist > 0);
                    node=cast;
                    break;
                }
                case SHORT:{
                    var cast=(ShortDblLnkSeq.Node)node;
                    do{
                        cast=cast.next;
                    }while(--dist > 0);
                    node=cast;
                    break;
                }
                case INT:{
                    var cast=(IntDblLnkSeq.Node)node;
                    do{
                        cast=cast.next;
                    }while(--dist > 0);
                    node=cast;
                    break;
                }
                case LONG:{
                    var cast=(LongDblLnkSeq.Node)node;
                    do{
                        cast=cast.next;
                    }while(--dist > 0);
                    node=cast;
                    break;
                }
                case FLOAT:{
                    var cast=(FloatDblLnkSeq.Node)node;
                    do{
                        cast=cast.next;
                    }while(--dist > 0);
                    node=cast;
                    break;
                }
                case DOUBLE:{
                    var cast=(DoubleDblLnkSeq.Node)node;
                    do{
                        cast=cast.next;
                    }while(--dist > 0);
                    node=cast;
                    break;
                }
                case REF:{
                    @SuppressWarnings("unchecked")
                    var cast=(RefDblLnkSeq.Node<E>)node;
                    do{
                        cast=cast.next;
                    }while(--dist > 0);
                    node=cast;
                    break;
                }
                default:
                    throw dataType.invalid();
                }
            }
            return node;
        }
        @SuppressWarnings("unchecked")
        private Object next(Object node){
            switch(dataType){
            case BOOLEAN:
                return ((BooleanDblLnkSeq.Node)node).next;
            case BYTE:
                return ((ByteDblLnkSeq.Node)node).next;
            case CHAR:
                return ((CharDblLnkSeq.Node)node).next;
            case DOUBLE:
                return ((DoubleDblLnkSeq.Node)node).next;
            case FLOAT:
                return ((FloatDblLnkSeq.Node)node).next;
            case INT:
                return ((IntDblLnkSeq.Node)node).next;
            case LONG:
                return ((LongDblLnkSeq.Node)node).next;
            case REF:
                return ((RefDblLnkSeq.Node<E>)node).next;
            case SHORT:
                return ((ShortDblLnkSeq.Node)node).next;
            default:
                throw dataType.invalid();
            }
        }
        @SuppressWarnings("unchecked")
        private Object prev(Object node){
            switch(dataType){
            case BOOLEAN:
                return ((BooleanDblLnkSeq.Node)node).prev;
            case BYTE:
                return ((ByteDblLnkSeq.Node)node).prev;
            case CHAR:
                return ((CharDblLnkSeq.Node)node).prev;
            case DOUBLE:
                return ((DoubleDblLnkSeq.Node)node).prev;
            case FLOAT:
                return ((FloatDblLnkSeq.Node)node).prev;
            case INT:
                return ((IntDblLnkSeq.Node)node).prev;
            case LONG:
                return ((LongDblLnkSeq.Node)node).prev;
            case REF:
                return ((RefDblLnkSeq.Node<E>)node).prev;
            case SHORT:
                return ((ShortDblLnkSeq.Node)node).prev;
            default:
                throw dataType.invalid();
            }
        }
        private Object tail(AbstractOmniCollection<E> seq){
            switch(dataType){
            case BOOLEAN:
                return ((BooleanDblLnkSeq)seq).tail;
            case BYTE:
                return ((ByteDblLnkSeq)seq).tail;
            case CHAR:
                return ((CharDblLnkSeq)seq).tail;
            case DOUBLE:
                return ((DoubleDblLnkSeq)seq).tail;
            case FLOAT:
                return ((FloatDblLnkSeq)seq).tail;
            case INT:
                return ((IntDblLnkSeq)seq).tail;
            case LONG:
                return ((LongDblLnkSeq)seq).tail;
            case REF:
                return ((RefDblLnkSeq<E>)seq).tail;
            case SHORT:
                return ((ShortDblLnkSeq)seq).tail;
            default:
                throw dataType.invalid();
            }
        }
        private void verifyBooleanClone(Object clone){
            Assertions.assertNotSame(clone,seq);
            final var cloneCast=(BooleanDblLnkSeq)clone;
            int size;
            Assertions.assertEquals(size=seq.size,cloneCast.size);
            Assertions.assertEquals(checkedType.checked,cloneCast instanceof BooleanDblLnkSeq.CheckedList);
            if(checkedType.checked){
                Assertions.assertEquals(0,((BooleanDblLnkSeq.CheckedList)cloneCast).modCount);
            }else{
                Assertions.assertTrue(cloneCast instanceof BooleanDblLnkSeq.UncheckedList);
            }
            var cloneHead=cloneCast.head;
            final var cloneTail=cloneCast.tail;
            if(size == 0){
                Assertions.assertNull(cloneHead);
                Assertions.assertNull(cloneTail);
            }else{
                final var thisCast=(BooleanDblLnkSeq)seq;
                var thisHead=thisCast.head;
                final var thisTail=thisCast.tail;
                var clonePrev=cloneHead.prev;
                Assertions.assertNull(clonePrev);
                for(int i=0;i < size;++i){
                    Assertions.assertNotSame(cloneHead,thisHead);
                    Assertions.assertEquals(cloneHead.val,thisHead.val);
                    Assertions.assertSame(clonePrev,cloneHead.prev);
                    clonePrev=cloneHead;
                    cloneHead=cloneHead.next;
                    thisHead=thisHead.next;
                }
                Assertions.assertSame(cloneTail,clonePrev);
                Assertions.assertNotSame(cloneTail,thisTail);
                Assertions.assertNull(cloneHead);
            }
        }
        private void verifyBooleanIntegrity(){
            final int expectedSize;
            Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
            final var cast=(BooleanDblLnkSeq)seq;
            final var expectedArr=(boolean[])this.expectedArr;
            if(checkedType.checked){
                Assertions.assertEquals(expectedModCount,((BooleanDblLnkSeq.CheckedList)cast).modCount);
            }
            var headNode=cast.head;
            final var tailNode=cast.tail;
            if(expectedSize == 0){
                Assertions.assertNull(headNode);
                Assertions.assertNull(tailNode);
            }else{
                var prevNode=headNode.prev;
                Assertions.assertNull(prevNode);
                for(int i=0;i < expectedSize;++i){
                    Assertions.assertEquals(headNode.val,expectedArr[i]);
                    Assertions.assertSame(prevNode,headNode.prev);
                    prevNode=headNode;
                    headNode=headNode.next;
                }
                Assertions.assertSame(tailNode,prevNode);
                Assertions.assertNull(headNode);
            }
        }
        private void verifyByteClone(Object clone){
            Assertions.assertNotSame(clone,seq);
            final var cloneCast=(ByteDblLnkSeq)clone;
            int size;
            Assertions.assertEquals(size=seq.size,cloneCast.size);
            Assertions.assertEquals(checkedType.checked,cloneCast instanceof ByteDblLnkSeq.CheckedList);
            if(checkedType.checked){
                Assertions.assertEquals(0,((ByteDblLnkSeq.CheckedList)cloneCast).modCount);
            }else{
                Assertions.assertTrue(cloneCast instanceof ByteDblLnkSeq.UncheckedList);
            }
            var cloneHead=cloneCast.head;
            final var cloneTail=cloneCast.tail;
            if(size == 0){
                Assertions.assertNull(cloneHead);
                Assertions.assertNull(cloneTail);
            }else{
                final var thisCast=(ByteDblLnkSeq)seq;
                var thisHead=thisCast.head;
                final var thisTail=thisCast.tail;
                var clonePrev=cloneHead.prev;
                Assertions.assertNull(clonePrev);
                for(int i=0;i < size;++i){
                    Assertions.assertNotSame(cloneHead,thisHead);
                    Assertions.assertEquals(cloneHead.val,thisHead.val);
                    Assertions.assertSame(clonePrev,cloneHead.prev);
                    clonePrev=cloneHead;
                    cloneHead=cloneHead.next;
                    thisHead=thisHead.next;
                }
                Assertions.assertSame(cloneTail,clonePrev);
                Assertions.assertNotSame(cloneTail,thisTail);
                Assertions.assertNull(cloneHead);
            }
        }
        private void verifyByteIntegrity(){
            final int expectedSize;
            Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
            final var cast=(ByteDblLnkSeq)seq;
            final var expectedArr=(byte[])this.expectedArr;
            if(checkedType.checked){
                Assertions.assertEquals(expectedModCount,((ByteDblLnkSeq.CheckedList)cast).modCount);
            }
            var headNode=cast.head;
            final var tailNode=cast.tail;
            if(expectedSize == 0){
                Assertions.assertNull(headNode);
                Assertions.assertNull(tailNode);
            }else{
                var prevNode=headNode.prev;
                Assertions.assertNull(prevNode);
                for(int i=0;i < expectedSize;++i){
                    Assertions.assertEquals(headNode.val,expectedArr[i]);
                    Assertions.assertSame(prevNode,headNode.prev);
                    prevNode=headNode;
                    headNode=headNode.next;
                }
                Assertions.assertSame(tailNode,prevNode);
                Assertions.assertNull(headNode);
            }
        }
        private void verifyCharClone(Object clone){
            Assertions.assertNotSame(clone,seq);
            final var cloneCast=(CharDblLnkSeq)clone;
            int size;
            Assertions.assertEquals(size=seq.size,cloneCast.size);
            Assertions.assertEquals(checkedType.checked,cloneCast instanceof CharDblLnkSeq.CheckedList);
            if(checkedType.checked){
                Assertions.assertEquals(0,((CharDblLnkSeq.CheckedList)cloneCast).modCount);
            }else{
                Assertions.assertTrue(cloneCast instanceof CharDblLnkSeq.UncheckedList);
            }
            var cloneHead=cloneCast.head;
            final var cloneTail=cloneCast.tail;
            if(size == 0){
                Assertions.assertNull(cloneHead);
                Assertions.assertNull(cloneTail);
            }else{
                final var thisCast=(CharDblLnkSeq)seq;
                var thisHead=thisCast.head;
                final var thisTail=thisCast.tail;
                var clonePrev=cloneHead.prev;
                Assertions.assertNull(clonePrev);
                for(int i=0;i < size;++i){
                    Assertions.assertNotSame(cloneHead,thisHead);
                    Assertions.assertEquals(cloneHead.val,thisHead.val);
                    Assertions.assertSame(clonePrev,cloneHead.prev);
                    clonePrev=cloneHead;
                    cloneHead=cloneHead.next;
                    thisHead=thisHead.next;
                }
                Assertions.assertSame(cloneTail,clonePrev);
                Assertions.assertNotSame(cloneTail,thisTail);
                Assertions.assertNull(cloneHead);
            }
        }
        private void verifyCharIntegrity(){
            final int expectedSize;
            Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
            final var cast=(CharDblLnkSeq)seq;
            final var expectedArr=(char[])this.expectedArr;
            if(checkedType.checked){
                Assertions.assertEquals(expectedModCount,((CharDblLnkSeq.CheckedList)cast).modCount);
            }
            var headNode=cast.head;
            final var tailNode=cast.tail;
            if(expectedSize == 0){
                Assertions.assertNull(headNode);
                Assertions.assertNull(tailNode);
            }else{
                var prevNode=headNode.prev;
                Assertions.assertNull(prevNode);
                for(int i=0;i < expectedSize;++i){
                    Assertions.assertEquals(headNode.val,expectedArr[i]);
                    Assertions.assertSame(prevNode,headNode.prev);
                    prevNode=headNode;
                    headNode=headNode.next;
                }
                Assertions.assertSame(tailNode,prevNode);
                Assertions.assertNull(headNode);
            }
        }
        private void verifyDoubleClone(Object clone){
            Assertions.assertNotSame(clone,seq);
            final var cloneCast=(DoubleDblLnkSeq)clone;
            int size;
            Assertions.assertEquals(size=seq.size,cloneCast.size);
            Assertions.assertEquals(checkedType.checked,cloneCast instanceof DoubleDblLnkSeq.CheckedList);
            if(checkedType.checked){
                Assertions.assertEquals(0,((DoubleDblLnkSeq.CheckedList)cloneCast).modCount);
            }else{
                Assertions.assertTrue(cloneCast instanceof DoubleDblLnkSeq.UncheckedList);
            }
            var cloneHead=cloneCast.head;
            final var cloneTail=cloneCast.tail;
            if(size == 0){
                Assertions.assertNull(cloneHead);
                Assertions.assertNull(cloneTail);
            }else{
                final var thisCast=(DoubleDblLnkSeq)seq;
                var thisHead=thisCast.head;
                final var thisTail=thisCast.tail;
                var clonePrev=cloneHead.prev;
                Assertions.assertNull(clonePrev);
                for(int i=0;i < size;++i){
                    Assertions.assertNotSame(cloneHead,thisHead);
                    Assertions.assertEquals(cloneHead.val,thisHead.val);
                    Assertions.assertSame(clonePrev,cloneHead.prev);
                    clonePrev=cloneHead;
                    cloneHead=cloneHead.next;
                    thisHead=thisHead.next;
                }
                Assertions.assertSame(cloneTail,clonePrev);
                Assertions.assertNotSame(cloneTail,thisTail);
                Assertions.assertNull(cloneHead);
            }
        }
        private void verifyDoubleIntegrity(){
            final int expectedSize;
            Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
            final var cast=(DoubleDblLnkSeq)seq;
            final var expectedArr=(double[])this.expectedArr;
            if(checkedType.checked){
                Assertions.assertEquals(expectedModCount,((DoubleDblLnkSeq.CheckedList)cast).modCount);
            }
            var headNode=cast.head;
            final var tailNode=cast.tail;
            if(expectedSize == 0){
                Assertions.assertNull(headNode);
                Assertions.assertNull(tailNode);
            }else{
                var prevNode=headNode.prev;
                Assertions.assertNull(prevNode);
                for(int i=0;i < expectedSize;++i){
                    Assertions.assertEquals(headNode.val,expectedArr[i]);
                    Assertions.assertSame(prevNode,headNode.prev);
                    prevNode=headNode;
                    headNode=headNode.next;
                }
                Assertions.assertSame(tailNode,prevNode);
                Assertions.assertNull(headNode);
            }
        }
        private void verifyFloatClone(Object clone){
            Assertions.assertNotSame(clone,seq);
            final var cloneCast=(FloatDblLnkSeq)clone;
            int size;
            Assertions.assertEquals(size=seq.size,cloneCast.size);
            Assertions.assertEquals(checkedType.checked,cloneCast instanceof FloatDblLnkSeq.CheckedList);
            if(checkedType.checked){
                Assertions.assertEquals(0,((FloatDblLnkSeq.CheckedList)cloneCast).modCount);
            }else{
                Assertions.assertTrue(cloneCast instanceof FloatDblLnkSeq.UncheckedList);
            }
            var cloneHead=cloneCast.head;
            final var cloneTail=cloneCast.tail;
            if(size == 0){
                Assertions.assertNull(cloneHead);
                Assertions.assertNull(cloneTail);
            }else{
                final var thisCast=(FloatDblLnkSeq)seq;
                var thisHead=thisCast.head;
                final var thisTail=thisCast.tail;
                var clonePrev=cloneHead.prev;
                Assertions.assertNull(clonePrev);
                for(int i=0;i < size;++i){
                    Assertions.assertNotSame(cloneHead,thisHead);
                    Assertions.assertEquals(cloneHead.val,thisHead.val);
                    Assertions.assertSame(clonePrev,cloneHead.prev);
                    clonePrev=cloneHead;
                    cloneHead=cloneHead.next;
                    thisHead=thisHead.next;
                }
                Assertions.assertSame(cloneTail,clonePrev);
                Assertions.assertNotSame(cloneTail,thisTail);
                Assertions.assertNull(cloneHead);
            }
        }
        private void verifyFloatIntegrity(){
            final int expectedSize;
            Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
            final var cast=(FloatDblLnkSeq)seq;
            final var expectedArr=(float[])this.expectedArr;
            if(checkedType.checked){
                Assertions.assertEquals(expectedModCount,((FloatDblLnkSeq.CheckedList)cast).modCount);
            }
            var headNode=cast.head;
            final var tailNode=cast.tail;
            if(expectedSize == 0){
                Assertions.assertNull(headNode);
                Assertions.assertNull(tailNode);
            }else{
                var prevNode=headNode.prev;
                Assertions.assertNull(prevNode);
                for(int i=0;i < expectedSize;++i){
                    Assertions.assertEquals(headNode.val,expectedArr[i]);
                    Assertions.assertSame(prevNode,headNode.prev);
                    prevNode=headNode;
                    headNode=headNode.next;
                }
                Assertions.assertSame(tailNode,prevNode);
                Assertions.assertNull(headNode);
            }
        }
        private void verifyIntClone(Object clone){
            Assertions.assertNotSame(clone,seq);
            final var cloneCast=(IntDblLnkSeq)clone;
            int size;
            Assertions.assertEquals(size=seq.size,cloneCast.size);
            Assertions.assertEquals(checkedType.checked,cloneCast instanceof IntDblLnkSeq.CheckedList);
            if(checkedType.checked){
                Assertions.assertEquals(0,((IntDblLnkSeq.CheckedList)cloneCast).modCount);
            }else{
                Assertions.assertTrue(cloneCast instanceof IntDblLnkSeq.UncheckedList);
            }
            var cloneHead=cloneCast.head;
            final var cloneTail=cloneCast.tail;
            if(size == 0){
                Assertions.assertNull(cloneHead);
                Assertions.assertNull(cloneTail);
            }else{
                final var thisCast=(IntDblLnkSeq)seq;
                var thisHead=thisCast.head;
                final var thisTail=thisCast.tail;
                var clonePrev=cloneHead.prev;
                Assertions.assertNull(clonePrev);
                for(int i=0;i < size;++i){
                    Assertions.assertNotSame(cloneHead,thisHead);
                    Assertions.assertEquals(cloneHead.val,thisHead.val);
                    Assertions.assertSame(clonePrev,cloneHead.prev);
                    clonePrev=cloneHead;
                    cloneHead=cloneHead.next;
                    thisHead=thisHead.next;
                }
                Assertions.assertSame(cloneTail,clonePrev);
                Assertions.assertNotSame(cloneTail,thisTail);
                Assertions.assertNull(cloneHead);
            }
        }
        private void verifyIntIntegrity(){
            final int expectedSize;
            Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
            final var cast=(IntDblLnkSeq)seq;
            final var expectedArr=(int[])this.expectedArr;
            if(checkedType.checked){
                Assertions.assertEquals(expectedModCount,((IntDblLnkSeq.CheckedList)cast).modCount);
            }
            var headNode=cast.head;
            final var tailNode=cast.tail;
            if(expectedSize == 0){
                Assertions.assertNull(headNode);
                Assertions.assertNull(tailNode);
            }else{
                var prevNode=headNode.prev;
                Assertions.assertNull(prevNode);
                for(int i=0;i < expectedSize;++i){
                    Assertions.assertEquals(headNode.val,expectedArr[i]);
                    Assertions.assertSame(prevNode,headNode.prev);
                    prevNode=headNode;
                    headNode=headNode.next;
                }
                Assertions.assertSame(tailNode,prevNode);
                Assertions.assertNull(headNode);
            }
        }
        private void verifyLongClone(Object clone){
            Assertions.assertNotSame(clone,seq);
            final var cloneCast=(LongDblLnkSeq)clone;
            int size;
            Assertions.assertEquals(size=seq.size,cloneCast.size);
            Assertions.assertEquals(checkedType.checked,cloneCast instanceof LongDblLnkSeq.CheckedList);
            if(checkedType.checked){
                Assertions.assertEquals(0,((LongDblLnkSeq.CheckedList)cloneCast).modCount);
            }else{
                Assertions.assertTrue(cloneCast instanceof LongDblLnkSeq.UncheckedList);
            }
            var cloneHead=cloneCast.head;
            final var cloneTail=cloneCast.tail;
            if(size == 0){
                Assertions.assertNull(cloneHead);
                Assertions.assertNull(cloneTail);
            }else{
                final var thisCast=(LongDblLnkSeq)seq;
                var thisHead=thisCast.head;
                final var thisTail=thisCast.tail;
                var clonePrev=cloneHead.prev;
                Assertions.assertNull(clonePrev);
                for(int i=0;i < size;++i){
                    Assertions.assertNotSame(cloneHead,thisHead);
                    Assertions.assertEquals(cloneHead.val,thisHead.val);
                    Assertions.assertSame(clonePrev,cloneHead.prev);
                    clonePrev=cloneHead;
                    cloneHead=cloneHead.next;
                    thisHead=thisHead.next;
                }
                Assertions.assertSame(cloneTail,clonePrev);
                Assertions.assertNotSame(cloneTail,thisTail);
                Assertions.assertNull(cloneHead);
            }
        }
        private void verifyLongIntegrity(){
            final int expectedSize;
            Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
            final var cast=(LongDblLnkSeq)seq;
            final var expectedArr=(long[])this.expectedArr;
            if(checkedType.checked){
                Assertions.assertEquals(expectedModCount,((LongDblLnkSeq.CheckedList)cast).modCount);
            }
            var headNode=cast.head;
            final var tailNode=cast.tail;
            if(expectedSize == 0){
                Assertions.assertNull(headNode);
                Assertions.assertNull(tailNode);
            }else{
                var prevNode=headNode.prev;
                Assertions.assertNull(prevNode);
                for(int i=0;i < expectedSize;++i){
                    Assertions.assertEquals(headNode.val,expectedArr[i]);
                    Assertions.assertSame(prevNode,headNode.prev);
                    prevNode=headNode;
                    headNode=headNode.next;
                }
                Assertions.assertSame(tailNode,prevNode);
                Assertions.assertNull(headNode);
            }
        }
        private void verifyRefClone(Object clone,boolean refIsSame){
            Assertions.assertNotSame(clone,seq);
            @SuppressWarnings("unchecked")
            final var cloneCast=(RefDblLnkSeq<E>)clone;
            int size;
            Assertions.assertEquals(size=seq.size,cloneCast.size);
            Assertions.assertEquals(checkedType.checked,cloneCast instanceof RefDblLnkSeq.CheckedList);
            if(checkedType.checked){
                Assertions.assertEquals(0,((RefDblLnkSeq.CheckedList<E>)cloneCast).modCount);
            }else{
                Assertions.assertTrue(cloneCast instanceof RefDblLnkSeq.UncheckedList);
            }
            var cloneHead=cloneCast.head;
            final var cloneTail=cloneCast.tail;
            if(size == 0){
                Assertions.assertNull(cloneHead);
                Assertions.assertNull(cloneTail);
            }else{
                @SuppressWarnings("unchecked")
                final var thisCast=(RefDblLnkSeq<E>)seq;
                var thisHead=thisCast.head;
                final var thisTail=thisCast.tail;
                var clonePrev=cloneHead.prev;
                Assertions.assertNull(clonePrev);
                if(refIsSame){
                    for(int i=0;i < size;++i){
                        Assertions.assertNotSame(cloneHead,thisHead);
                        Assertions.assertSame(cloneHead.val,thisHead.val);
                        Assertions.assertSame(clonePrev,cloneHead.prev);
                        clonePrev=cloneHead;
                        cloneHead=cloneHead.next;
                        thisHead=thisHead.next;
                    }
                }else{
                    for(int i=0;i < size;++i){
                        Assertions.assertNotSame(cloneHead,thisHead);
                        Assertions.assertEquals(cloneHead.val,thisHead.val);
                        Assertions.assertSame(clonePrev,cloneHead.prev);
                        clonePrev=cloneHead;
                        cloneHead=cloneHead.next;
                        thisHead=thisHead.next;
                    }
                }
                Assertions.assertSame(cloneTail,clonePrev);
                Assertions.assertNotSame(cloneTail,thisTail);
                Assertions.assertNull(cloneHead);
            }
        }
        private void verifyRefIntegrity(boolean refIsSame){
            final int expectedSize;
            Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
            @SuppressWarnings("unchecked")
            final var cast=(RefDblLnkSeq<E>)seq;
            final var expectedArr=(Object[])this.expectedArr;
            if(checkedType.checked){
                Assertions.assertEquals(expectedModCount,((RefDblLnkSeq.CheckedList<E>)cast).modCount);
            }
            var headNode=cast.head;
            final var tailNode=cast.tail;
            if(expectedSize == 0){
                Assertions.assertNull(headNode);
                Assertions.assertNull(tailNode);
            }else{
                var prevNode=headNode.prev;
                Assertions.assertNull(prevNode);
                if(refIsSame){
                    for(int i=0;i < expectedSize;++i){
                        Assertions.assertSame(headNode.val,expectedArr[i]);
                        Assertions.assertSame(prevNode,headNode.prev);
                        prevNode=headNode;
                        headNode=headNode.next;
                    }
                }else{
                    for(int i=0;i < expectedSize;++i){
                        Assertions.assertEquals(headNode.val,expectedArr[i]);
                        Assertions.assertSame(prevNode,headNode.prev);
                        prevNode=headNode;
                        headNode=headNode.next;
                    }
                }
                Assertions.assertSame(tailNode,prevNode);
                Assertions.assertNull(headNode);
            }
        }
        private void verifyShortClone(Object clone){
            Assertions.assertNotSame(clone,seq);
            final var cloneCast=(ShortDblLnkSeq)clone;
            int size;
            Assertions.assertEquals(size=seq.size,cloneCast.size);
            Assertions.assertEquals(checkedType.checked,cloneCast instanceof ShortDblLnkSeq.CheckedList);
            if(checkedType.checked){
                Assertions.assertEquals(0,((ShortDblLnkSeq.CheckedList)cloneCast).modCount);
            }else{
                Assertions.assertTrue(cloneCast instanceof ShortDblLnkSeq.UncheckedList);
            }
            var cloneHead=cloneCast.head;
            final var cloneTail=cloneCast.tail;
            if(size == 0){
                Assertions.assertNull(cloneHead);
                Assertions.assertNull(cloneTail);
            }else{
                final var thisCast=(ShortDblLnkSeq)seq;
                var thisHead=thisCast.head;
                final var thisTail=thisCast.tail;
                var clonePrev=cloneHead.prev;
                Assertions.assertNull(clonePrev);
                for(int i=0;i < size;++i){
                    Assertions.assertNotSame(cloneHead,thisHead);
                    Assertions.assertEquals(cloneHead.val,thisHead.val);
                    Assertions.assertSame(clonePrev,cloneHead.prev);
                    clonePrev=cloneHead;
                    cloneHead=cloneHead.next;
                    thisHead=thisHead.next;
                }
                Assertions.assertSame(cloneTail,clonePrev);
                Assertions.assertNotSame(cloneTail,thisTail);
                Assertions.assertNull(cloneHead);
            }
        }
        private void verifyShortIntegrity(){
            final int expectedSize;
            Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
            final var cast=(ShortDblLnkSeq)seq;
            final var expectedArr=(short[])this.expectedArr;
            if(checkedType.checked){
                Assertions.assertEquals(expectedModCount,((ShortDblLnkSeq.CheckedList)cast).modCount);
            }
            var headNode=cast.head;
            final var tailNode=cast.tail;
            if(expectedSize == 0){
                Assertions.assertNull(headNode);
                Assertions.assertNull(tailNode);
            }else{
                var prevNode=headNode.prev;
                Assertions.assertNull(prevNode);
                for(int i=0;i < expectedSize;++i){
                    Assertions.assertEquals(headNode.val,expectedArr[i]);
                    Assertions.assertSame(prevNode,headNode.prev);
                    prevNode=headNode;
                    headNode=headNode.next;
                }
                Assertions.assertSame(tailNode,prevNode);
                Assertions.assertNull(headNode);
            }
        }
        private abstract class AbstractItrMonitor<ITR extends OmniIterator<E>> implements MonitoredIterator<ITR,LSTDEQ>{
            final ITR itr;
            Object expectedCurr;
            Object expectedLastRet;
            int expectedCurrIndex;
            int expectedItrModCount;
            int lastRetIndex;
            AbstractItrMonitor(ITR itr,int expectedCurrIndex,Object expectedCurr){
                this.itr=itr;
                this.expectedCurrIndex=expectedCurrIndex;
                this.expectedCurr=expectedCurr;
                this.expectedItrModCount=expectedModCount;
                this.lastRetIndex=-1;
            }
            @Override
            public ITR getIterator(){
                return itr;
            }
            @Override
            public MonitoredCollection<LSTDEQ> getMonitoredCollection(){
                return DblLnkSeqMonitor.this;
            }
            @Override
            public int getNumLeft(){
                return expectedSize - expectedCurrIndex;
            }
            @Override
            public boolean hasNext(){
                return this.expectedCurrIndex < expectedSize;
            }
            @Override
            public boolean nextWasJustCalled(){
                return lastRetIndex != -1 && lastRetIndex < expectedCurrIndex;
            }
            @Override
            public void updateItrNextState(){
                expectedLastRet=expectedCurr;
                expectedCurr=next(expectedCurr);
                lastRetIndex=expectedCurrIndex++;
            }
            @Override
            public void updateItrRemoveState(){
                final int expectedCurrIndex=this.expectedCurrIndex;
                int lastRetIndex;
                updateRemoveIndexState(lastRetIndex=this.lastRetIndex);
                ++expectedItrModCount;
                if(expectedCurrIndex == lastRetIndex){
                    this.expectedCurr=next(expectedLastRet);
                }else{
                    this.expectedCurrIndex=expectedCurrIndex - 1;
                }
                this.lastRetIndex=-1;
                this.expectedLastRet=null;
            }
            @Override
            public void verifyForEachRemaining(MonitoredFunction function){
                int lastRetIndex=this.lastRetIndex;
                int expectedCurrIndex=this.expectedCurrIndex;
                final int expectedBound=expectedSize;
                final var functionItr=function.iterator();
                switch(dataType){
                case BOOLEAN:{
                    final var expectedArr=(boolean[])DblLnkSeqMonitor.this.expectedArr;
                    var expectedCurr=(BooleanDblLnkSeq.Node)this.expectedCurr;
                    var expectedLastRet=(BooleanDblLnkSeq.Node)this.expectedLastRet;
                    while(expectedCurrIndex < expectedBound){
                        Assertions.assertEquals(expectedArr[expectedCurrIndex],(boolean)functionItr.next());
                        lastRetIndex=expectedCurrIndex++;
                        expectedLastRet=expectedCurr;
                        expectedCurr=expectedCurr.next;
                    }
                    this.expectedCurr=expectedCurr;
                    this.expectedLastRet=expectedLastRet;
                    break;
                }
                case BYTE:{
                    final var expectedArr=(byte[])DblLnkSeqMonitor.this.expectedArr;
                    var expectedCurr=(ByteDblLnkSeq.Node)this.expectedCurr;
                    var expectedLastRet=(ByteDblLnkSeq.Node)this.expectedLastRet;
                    while(expectedCurrIndex < expectedBound){
                        Assertions.assertEquals(expectedArr[expectedCurrIndex],(byte)functionItr.next());
                        lastRetIndex=expectedCurrIndex++;
                        expectedLastRet=expectedCurr;
                        expectedCurr=expectedCurr.next;
                    }
                    this.expectedCurr=expectedCurr;
                    this.expectedLastRet=expectedLastRet;
                    break;
                }
                case CHAR:{
                    final var expectedArr=(char[])DblLnkSeqMonitor.this.expectedArr;
                    var expectedCurr=(CharDblLnkSeq.Node)this.expectedCurr;
                    var expectedLastRet=(CharDblLnkSeq.Node)this.expectedLastRet;
                    while(expectedCurrIndex < expectedBound){
                        Assertions.assertEquals(expectedArr[expectedCurrIndex],(char)functionItr.next());
                        lastRetIndex=expectedCurrIndex++;
                        expectedLastRet=expectedCurr;
                        expectedCurr=expectedCurr.next;
                    }
                    this.expectedCurr=expectedCurr;
                    this.expectedLastRet=expectedLastRet;
                    break;
                }
                case DOUBLE:{
                    final var expectedArr=(double[])DblLnkSeqMonitor.this.expectedArr;
                    var expectedCurr=(DoubleDblLnkSeq.Node)this.expectedCurr;
                    var expectedLastRet=(DoubleDblLnkSeq.Node)this.expectedLastRet;
                    while(expectedCurrIndex < expectedBound){
                        Assertions.assertEquals(expectedArr[expectedCurrIndex],(double)functionItr.next());
                        lastRetIndex=expectedCurrIndex++;
                        expectedLastRet=expectedCurr;
                        expectedCurr=expectedCurr.next;
                    }
                    this.expectedCurr=expectedCurr;
                    this.expectedLastRet=expectedLastRet;
                    break;
                }
                case FLOAT:{
                    final var expectedArr=(float[])DblLnkSeqMonitor.this.expectedArr;
                    var expectedCurr=(FloatDblLnkSeq.Node)this.expectedCurr;
                    var expectedLastRet=(FloatDblLnkSeq.Node)this.expectedLastRet;
                    while(expectedCurrIndex < expectedBound){
                        Assertions.assertEquals(expectedArr[expectedCurrIndex],(float)functionItr.next());
                        lastRetIndex=expectedCurrIndex++;
                        expectedLastRet=expectedCurr;
                        expectedCurr=expectedCurr.next;
                    }
                    this.expectedCurr=expectedCurr;
                    this.expectedLastRet=expectedLastRet;
                    break;
                }
                case INT:{
                    final var expectedArr=(int[])DblLnkSeqMonitor.this.expectedArr;
                    var expectedCurr=(IntDblLnkSeq.Node)this.expectedCurr;
                    var expectedLastRet=(IntDblLnkSeq.Node)this.expectedLastRet;
                    while(expectedCurrIndex < expectedBound){
                        Assertions.assertEquals(expectedArr[expectedCurrIndex],(int)functionItr.next());
                        lastRetIndex=expectedCurrIndex++;
                        expectedLastRet=expectedCurr;
                        expectedCurr=expectedCurr.next;
                    }
                    this.expectedCurr=expectedCurr;
                    this.expectedLastRet=expectedLastRet;
                    break;
                }
                case LONG:{
                    final var expectedArr=(long[])DblLnkSeqMonitor.this.expectedArr;
                    var expectedCurr=(LongDblLnkSeq.Node)this.expectedCurr;
                    var expectedLastRet=(LongDblLnkSeq.Node)this.expectedLastRet;
                    while(expectedCurrIndex < expectedBound){
                        Assertions.assertEquals(expectedArr[expectedCurrIndex],(long)functionItr.next());
                        lastRetIndex=expectedCurrIndex++;
                        expectedLastRet=expectedCurr;
                        expectedCurr=expectedCurr.next;
                    }
                    this.expectedCurr=expectedCurr;
                    this.expectedLastRet=expectedLastRet;
                    break;
                }
                case REF:{
                    final var expectedArr=(Object[])DblLnkSeqMonitor.this.expectedArr;
                    @SuppressWarnings("unchecked")
                    var expectedCurr=(RefDblLnkSeq.Node<E>)this.expectedCurr;
                    @SuppressWarnings("unchecked")
                    var expectedLastRet=(RefDblLnkSeq.Node<E>)this.expectedLastRet;
                    while(expectedCurrIndex < expectedBound){
                        Assertions.assertSame(expectedArr[expectedCurrIndex],functionItr.next());
                        lastRetIndex=expectedCurrIndex++;
                        expectedLastRet=expectedCurr;
                        expectedCurr=expectedCurr.next;
                    }
                    this.expectedCurr=expectedCurr;
                    this.expectedLastRet=expectedLastRet;
                    break;
                }
                case SHORT:{
                    final var expectedArr=(short[])DblLnkSeqMonitor.this.expectedArr;
                    var expectedCurr=(ShortDblLnkSeq.Node)this.expectedCurr;
                    var expectedLastRet=(ShortDblLnkSeq.Node)this.expectedLastRet;
                    while(expectedCurrIndex < expectedBound){
                        Assertions.assertEquals(expectedArr[expectedCurrIndex],(short)functionItr.next());
                        lastRetIndex=expectedCurrIndex++;
                        expectedLastRet=expectedCurr;
                        expectedCurr=expectedCurr.next;
                    }
                    this.expectedCurr=expectedCurr;
                    this.expectedLastRet=expectedLastRet;
                    break;
                }
                default:
                    throw dataType.invalid();
                }
                this.expectedCurrIndex=expectedCurrIndex;
                this.lastRetIndex=lastRetIndex;
            }
            @Override
            public void verifyNextResult(DataType outputType,Object result){
                verifyGetResult(expectedCurrIndex,result,outputType);
            }
        }
        private class DescendingItrMonitor implements MonitoredIterator<OmniIterator<E>,LSTDEQ>{
            final OmniIterator<E> itr=seq.descendingIterator();
            Object expectedCurr=tail(seq);
            Object expectedLastRet;
            int expectedCurrIndex=expectedSize;
            int expectedItrModCount=expectedModCount;
            int lastRetIndex=-1;
            @Override
            public OmniIterator<E> getIterator(){
                return itr;
            }
            @Override
            public IteratorType getIteratorType(){
                return IteratorType.DescendingItr;
            }
            @Override
            public MonitoredCollection<LSTDEQ> getMonitoredCollection(){
                return DblLnkSeqMonitor.this;
            }
            @Override
            public int getNumLeft(){
                return expectedCurrIndex;
            }
            @Override
            public boolean hasNext(){
                return expectedCurrIndex > 0;
            }
            @Override
            public boolean nextWasJustCalled(){
                return this.lastRetIndex != -1;
            }
            @Override
            public void updateItrNextState(){
                this.expectedCurr=prev(this.expectedLastRet=this.expectedCurr);
                this.lastRetIndex=--expectedCurrIndex;
            }
            @Override
            public void updateItrRemoveState(){
                updateRemoveIndexState(lastRetIndex);
                this.expectedLastRet=null;
                this.lastRetIndex=-1;
                ++expectedItrModCount;
            }
            @Override
            public void verifyCloneHelper(Object clone){
                final var checked=checkedType.checked;
                switch(dataType){
                case BOOLEAN:
                    if(checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.DescendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.DescendingItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.DescendingItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.DescendingItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.DescendingItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedList.DescendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedList.DescendingItr.curr(itr));
                    }
                    break;
                case BYTE:
                    if(checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.DescendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.DescendingItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.DescendingItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.DescendingItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.DescendingItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedList.DescendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedList.DescendingItr.curr(itr));
                    }
                    break;
                case CHAR:
                    if(checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.DescendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.DescendingItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.DescendingItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.DescendingItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.DescendingItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.CharDblLnkSeq.UncheckedList.DescendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.CharDblLnkSeq.UncheckedList.DescendingItr.curr(itr));
                    }
                    break;
                case DOUBLE:
                    if(checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.DescendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.DescendingItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.DescendingItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.DescendingItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.DescendingItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.UncheckedList.DescendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.UncheckedList.DescendingItr.curr(itr));
                    }
                    break;
                case FLOAT:
                    if(checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.DescendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.DescendingItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.DescendingItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.DescendingItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.DescendingItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedList.DescendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedList.DescendingItr.curr(itr));
                    }
                    break;
                case INT:
                    if(checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.DescendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.DescendingItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.DescendingItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.DescendingItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.DescendingItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.IntDblLnkSeq.UncheckedList.DescendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.IntDblLnkSeq.UncheckedList.DescendingItr.curr(itr));
                    }
                    break;
                case LONG:
                    if(checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.DescendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.DescendingItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.DescendingItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.DescendingItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.DescendingItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.LongDblLnkSeq.UncheckedList.DescendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.LongDblLnkSeq.UncheckedList.DescendingItr.curr(itr));
                    }
                    break;
                case REF:
                    if(checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.DescendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.DescendingItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.DescendingItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.DescendingItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.DescendingItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.RefDblLnkSeq.UncheckedList.DescendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.RefDblLnkSeq.UncheckedList.DescendingItr.curr(itr));
                    }
                    break;
                case SHORT:
                    if(checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.DescendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.DescendingItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.DescendingItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.DescendingItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.DescendingItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedList.DescendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedList.DescendingItr.curr(itr));
                    }
                    break;
                default:
                    throw dataType.invalid();
                }
            }
            @Override
            public void verifyForEachRemaining(MonitoredFunction function){
                final var itr=function.iterator();
                int expectedCurrIndex=this.expectedCurrIndex;
                int lastRetIndex=this.lastRetIndex;
                if(expectedCurrIndex > 0){
                    switch(dataType){
                    case BOOLEAN:{
                        final var arr=(boolean[])expectedArr;
                        while(expectedCurrIndex > 0){
                            Assertions.assertEquals(arr[lastRetIndex=--expectedCurrIndex],(boolean)itr.next());
                        }
                        break;
                    }
                    case BYTE:{
                        final var arr=(byte[])expectedArr;
                        while(expectedCurrIndex > 0){
                            Assertions.assertEquals(arr[lastRetIndex=--expectedCurrIndex],(byte)itr.next());
                        }
                        break;
                    }
                    case CHAR:{
                        final var arr=(char[])expectedArr;
                        while(expectedCurrIndex > 0){
                            Assertions.assertEquals(arr[lastRetIndex=--expectedCurrIndex],(char)itr.next());
                        }
                        break;
                    }
                    case DOUBLE:{
                        final var arr=(double[])expectedArr;
                        while(expectedCurrIndex > 0){
                            Assertions.assertEquals(arr[lastRetIndex=--expectedCurrIndex],(double)itr.next());
                        }
                        break;
                    }
                    case FLOAT:{
                        final var arr=(float[])expectedArr;
                        while(expectedCurrIndex > 0){
                            Assertions.assertEquals(arr[lastRetIndex=--expectedCurrIndex],(float)itr.next());
                        }
                        break;
                    }
                    case INT:{
                        final var arr=(int[])expectedArr;
                        while(expectedCurrIndex > 0){
                            Assertions.assertEquals(arr[lastRetIndex=--expectedCurrIndex],(int)itr.next());
                        }
                        break;
                    }
                    case LONG:{
                        final var arr=(long[])expectedArr;
                        while(expectedCurrIndex > 0){
                            Assertions.assertEquals(arr[lastRetIndex=--expectedCurrIndex],(long)itr.next());
                        }
                        break;
                    }
                    case REF:{
                        final var arr=(Object[])expectedArr;
                        while(expectedCurrIndex > 0){
                            Assertions.assertSame(arr[lastRetIndex=--expectedCurrIndex],itr.next());
                        }
                        break;
                    }
                    case SHORT:{
                        final var arr=(short[])expectedArr;
                        while(expectedCurrIndex > 0){
                            Assertions.assertEquals(arr[lastRetIndex=--expectedCurrIndex],(short)itr.next());
                        }
                        break;
                    }
                    default:
                        throw dataType.invalid();
                    }
                    this.expectedLastRet=head(seq);
                    this.expectedCurr=null;
                    this.lastRetIndex=lastRetIndex;
                    this.expectedCurrIndex=expectedCurrIndex;
                }
            }
            @Override
            public void verifyNextResult(DataType outputType,Object result){
                verifyGetResult(expectedCurrIndex - 1,result,outputType);
            }
        }
        private class ItrMonitor extends AbstractItrMonitor<OmniIterator<E>>{
            ItrMonitor(){
                super(seq.iterator(),0,head(seq));
            }
            @Override
            public IteratorType getIteratorType(){
                return IteratorType.AscendingItr;
            }
            @Override
            public void verifyCloneHelper(Object clone){
                final var checked=checkedType.checked;
                switch(dataType){
                case BOOLEAN:{
                    if(checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.AscendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.AscendingItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.AscendingItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.AscendingItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.AscendingItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedList.AscendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedList.AscendingItr.curr(itr));
                    }
                    break;
                }
                case BYTE:{
                    if(checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.AscendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.AscendingItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.AscendingItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.AscendingItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.AscendingItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedList.AscendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedList.AscendingItr.curr(itr));
                    }
                    break;
                }
                case CHAR:{
                    if(checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.AscendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.AscendingItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.AscendingItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.AscendingItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.AscendingItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.CharDblLnkSeq.UncheckedList.AscendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.CharDblLnkSeq.UncheckedList.AscendingItr.curr(itr));
                    }
                    break;
                }
                case DOUBLE:{
                    if(checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.AscendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.AscendingItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.AscendingItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.AscendingItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.AscendingItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.UncheckedList.AscendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.UncheckedList.AscendingItr.curr(itr));
                    }
                    break;
                }
                case FLOAT:{
                    if(checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.AscendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.AscendingItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.AscendingItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.AscendingItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.AscendingItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedList.AscendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedList.AscendingItr.curr(itr));
                    }
                    break;
                }
                case INT:{
                    if(checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.AscendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.AscendingItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.AscendingItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.AscendingItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.AscendingItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.IntDblLnkSeq.UncheckedList.AscendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.IntDblLnkSeq.UncheckedList.AscendingItr.curr(itr));
                    }
                    break;
                }
                case LONG:{
                    if(checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.AscendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.AscendingItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.AscendingItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.AscendingItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.AscendingItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.LongDblLnkSeq.UncheckedList.AscendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.LongDblLnkSeq.UncheckedList.AscendingItr.curr(itr));
                    }
                    break;
                }
                case REF:{
                    if(checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.AscendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.AscendingItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.AscendingItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.AscendingItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.AscendingItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.RefDblLnkSeq.UncheckedList.AscendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.RefDblLnkSeq.UncheckedList.AscendingItr.curr(itr));
                    }
                    break;
                }
                case SHORT:{
                    if(checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.AscendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.AscendingItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.AscendingItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.AscendingItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.AscendingItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedList.AscendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedList.AscendingItr.curr(itr));
                    }
                    break;
                }
                default:
                    throw dataType.invalid();
                }
            }
        }
        private class ListItrMonitor extends AbstractItrMonitor<OmniListIterator<E>>
                implements
                MonitoredListIterator<OmniListIterator<E>,LSTDEQ>{
            ListItrMonitor(){
                super(seq.listIterator(),0,head(seq));
            }
            ListItrMonitor(int index){
                super(seq.listIterator(index),index,getNode(index,seq,expectedSize));
            }
            @Override
            public IteratorType getIteratorType(){
                return IteratorType.BidirectionalItr;
            }
            @Override
            public boolean hasPrevious(){
                return expectedCurrIndex > 0;
            }
            @Override
            public int nextIndex(){
                return expectedCurrIndex;
            }
            @Override
            public int previousIndex(){
                return expectedCurrIndex - 1;
            }
            @Override
            public boolean previousWasJustCalled(){
                return lastRetIndex == expectedCurrIndex;
            }
            @Override
            public void updateItrAddState(Object input,DataType inputType){
                DblLnkSeqMonitor.this.updateAddState(expectedCurrIndex++,input,inputType);
                ++expectedItrModCount;
                lastRetIndex=-1;
                expectedLastRet=null;
            }
            @Override
            public void updateItrPreviousState(){
                if(expectedCurr == null){
                    expectedCurr=tail(seq);
                }else{
                    expectedCurr=prev(expectedCurr);
                }
                expectedLastRet=expectedCurr;
                lastRetIndex=--expectedCurrIndex;
            }
            @Override
            public void updateItrSetState(Object input,DataType inputType){
                verifyPutResult(lastRetIndex,input,inputType);
            }
            @Override
            public void verifyCloneHelper(Object clone){
                final var checked=checkedType.checked;
                switch(dataType){
                case BOOLEAN:{
                    if(checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.BidirectionalItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.BidirectionalItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.BidirectionalItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.BidirectionalItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.BidirectionalItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedList.BidirectionalItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedList.BidirectionalItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedList.BidirectionalItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedList.BidirectionalItr.currIndex(itr));
                    }
                    break;
                }
                case BYTE:{
                    if(checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.BidirectionalItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.BidirectionalItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.BidirectionalItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.BidirectionalItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.BidirectionalItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedList.BidirectionalItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedList.BidirectionalItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedList.BidirectionalItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedList.BidirectionalItr.currIndex(itr));
                    }
                    break;
                }
                case CHAR:{
                    if(checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.BidirectionalItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.BidirectionalItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.BidirectionalItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.BidirectionalItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.BidirectionalItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.CharDblLnkSeq.UncheckedList.BidirectionalItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.CharDblLnkSeq.UncheckedList.BidirectionalItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.CharDblLnkSeq.UncheckedList.BidirectionalItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.CharDblLnkSeq.UncheckedList.BidirectionalItr.currIndex(itr));
                    }
                    break;
                }
                case DOUBLE:{
                    if(checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.BidirectionalItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.BidirectionalItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.BidirectionalItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.BidirectionalItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.BidirectionalItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.UncheckedList.BidirectionalItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.UncheckedList.BidirectionalItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.UncheckedList.BidirectionalItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.UncheckedList.BidirectionalItr.currIndex(itr));
                    }
                    break;
                }
                case FLOAT:{
                    if(checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.BidirectionalItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.BidirectionalItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.BidirectionalItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.BidirectionalItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.BidirectionalItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedList.BidirectionalItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedList.BidirectionalItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedList.BidirectionalItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedList.BidirectionalItr.currIndex(itr));
                    }
                    break;
                }
                case INT:{
                    if(checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.BidirectionalItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.BidirectionalItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.BidirectionalItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.BidirectionalItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.BidirectionalItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.IntDblLnkSeq.UncheckedList.BidirectionalItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.IntDblLnkSeq.UncheckedList.BidirectionalItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.IntDblLnkSeq.UncheckedList.BidirectionalItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.IntDblLnkSeq.UncheckedList.BidirectionalItr.currIndex(itr));
                    }
                    break;
                }
                case LONG:{
                    if(checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.BidirectionalItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.BidirectionalItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.BidirectionalItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.BidirectionalItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.BidirectionalItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.LongDblLnkSeq.UncheckedList.BidirectionalItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.LongDblLnkSeq.UncheckedList.BidirectionalItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.LongDblLnkSeq.UncheckedList.BidirectionalItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.LongDblLnkSeq.UncheckedList.BidirectionalItr.currIndex(itr));
                    }
                    break;
                }
                case REF:{
                    if(checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.BidirectionalItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.BidirectionalItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.BidirectionalItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.BidirectionalItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.BidirectionalItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.RefDblLnkSeq.UncheckedList.BidirectionalItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.RefDblLnkSeq.UncheckedList.BidirectionalItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.RefDblLnkSeq.UncheckedList.BidirectionalItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.RefDblLnkSeq.UncheckedList.BidirectionalItr.currIndex(itr));
                    }
                    break;
                }
                case SHORT:{
                    if(checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.BidirectionalItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.BidirectionalItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.BidirectionalItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.BidirectionalItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.BidirectionalItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedList.BidirectionalItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedList.BidirectionalItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedList.BidirectionalItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedList.BidirectionalItr.currIndex(itr));
                    }
                    break;
                }
                default:
                    throw dataType.invalid();
                }
            }
            @Override
            public void verifyPreviousResult(DataType outputType,Object result){
                verifyGetResult(lastRetIndex,result,outputType);
            }
        }
        private static class SubListMonitor<SUBLIST extends AbstractOmniCollection<E>&OmniList<E>,LSTDEQ extends AbstractOmniCollection<E>&OmniDeque<E>&OmniList<E>&Externalizable,E>
                implements
                MonitoredList<SUBLIST>{
            final DblLnkSeqMonitor<LSTDEQ,E> expectedRoot;
            final SubListMonitor<SUBLIST,LSTDEQ,E> expectedParent;
            final SUBLIST seq;
            final int expectedParentOffset;
            final int expectedRootOffset;
            Object expectedHead;
            Object expectedTail;
            int expectedSize;
            int expectedModCount;
            @SuppressWarnings("unchecked")
            SubListMonitor(DblLnkSeqMonitor<LSTDEQ,E> expectedRoot,int fromIndex,int toIndex){
                this.expectedRoot=expectedRoot;
                this.expectedParent=null;
                this.seq=(SUBLIST)expectedRoot.seq.subList(fromIndex,toIndex);
                this.expectedSize=toIndex - fromIndex;
                this.expectedModCount=expectedRoot.expectedModCount;
                this.expectedRootOffset=fromIndex;
                this.expectedParentOffset=fromIndex;
                if(expectedSize != 0){
                    final int tailDist=expectedRoot.expectedSize - toIndex;
                    if(tailDist <= fromIndex){
                        expectedTail=expectedRoot.iterateDown(expectedRoot.tail(expectedRoot.seq),tailDist);
                        expectedHead=expectedSize <= fromIndex?expectedRoot.iterateDown(expectedTail,expectedSize - 1)
                                :expectedRoot.iterateUp(expectedRoot.head(expectedRoot.seq),fromIndex);
                    }else{
                        expectedHead=expectedRoot.iterateUp(expectedRoot.head(expectedRoot.seq),fromIndex);
                        expectedTail=expectedSize <= tailDist?expectedRoot.iterateUp(expectedHead,expectedSize - 1)
                                :expectedRoot.iterateDown(expectedRoot.tail(expectedRoot.seq),tailDist);
                    }
                }
            }
            @SuppressWarnings("unchecked")
            SubListMonitor(SubListMonitor<SUBLIST,LSTDEQ,E> expectedParent,int fromIndex,int toIndex){
                this.expectedRoot=expectedParent.expectedRoot;
                this.expectedParent=expectedParent;
                this.seq=(SUBLIST)expectedParent.seq.subList(fromIndex,toIndex);
                this.expectedSize=toIndex - fromIndex;
                this.expectedModCount=expectedParent.expectedModCount;
                this.expectedRootOffset=expectedParent.expectedRootOffset + fromIndex;
                this.expectedParentOffset=fromIndex;
                if(expectedSize != 0){
                    final int tailDist=expectedParent.expectedSize - toIndex;
                    if(tailDist <= fromIndex){
                        expectedTail=expectedRoot.iterateDown(expectedParent.expectedTail,tailDist);
                        expectedHead=expectedSize <= fromIndex?expectedRoot.iterateDown(expectedTail,expectedSize - 1)
                                :expectedRoot.iterateUp(expectedParent.expectedHead,fromIndex);
                    }else{
                        expectedHead=expectedRoot.iterateUp(expectedParent.expectedHead,fromIndex);
                        expectedTail=expectedSize <= tailDist?expectedRoot.iterateUp(expectedHead,expectedSize - 1)
                                :expectedRoot.iterateDown(expectedParent.expectedTail,tailDist);
                    }
                }
            }
            @Override
            public void copyListContents(){
                expectedRoot.updateCollectionState();
                updateSubLists();
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
                return expectedRoot.dataType;
            }
            @Override
            public MonitoredIterator<? extends OmniIterator<?>,SUBLIST> getMonitoredIterator(){
                return new ItrMonitor<>(seq.iterator(),getNode(0,expectedSize),0);
            }
            @Override
            public MonitoredIterator<? extends OmniIterator<?>,SUBLIST> getMonitoredIterator(int index,
                    IteratorType itrType){
                switch(itrType){
                case SubAscendingItr:
                    return getMonitoredIterator(index);
                case SubBidirectionalItr:
                    return getMonitoredListIterator(index);
                default:
                    throw itrType.invalid();
                }
            }
            @Override
            public MonitoredIterator<? extends OmniIterator<?>,SUBLIST> getMonitoredIterator(IteratorType itrType){
                switch(itrType){
                case SubAscendingItr:
                    return getMonitoredIterator();
                case SubBidirectionalItr:
                    return getMonitoredListIterator();
                default:
                    throw itrType.invalid();
                }
            }
            @Override
            public MonitoredListIterator<? extends OmniListIterator<?>,SUBLIST> getMonitoredListIterator(){
                return new ListItrMonitor(seq.listIterator(),getNode(0,expectedSize),0);
            }
            @Override
            public MonitoredListIterator<? extends OmniListIterator<?>,SUBLIST> getMonitoredListIterator(int index){
                return new ListItrMonitor(seq.listIterator(index),getNode(index,expectedSize),index);
            }
            @Override
            public MonitoredList<?> getMonitoredSubList(int fromIndex,int toIndex){
                return new SubListMonitor<>(this,fromIndex,toIndex);
            }
            @Override
            public StructType getStructType(){
                return StructType.DblLnkSubList;
            }
            @Override
            public void incrementModCount(){
                var curr=this;
                do{
                    ++curr.expectedModCount;
                }while((curr=curr.expectedParent) != null);
                expectedRoot.incrementModCount();
            }
            @Override
            public void modCollection(){
                Consumer<SubListMonitor<SUBLIST,LSTDEQ,E>> modifier;
                final var dataType=expectedRoot.dataType;
                switch(dataType){
                case BOOLEAN:
                    modifier=subList->subList.expectedModCount=FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList
                            .incrementModCount(subList.seq);
                    break;
                case BYTE:
                    modifier=subList->subList.expectedModCount=FieldAndMethodAccessor.ByteDblLnkSeq.CheckedSubList
                            .incrementModCount(subList.seq);
                    break;
                case CHAR:
                    modifier=subList->subList.expectedModCount=FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList
                            .incrementModCount(subList.seq);
                    break;
                case DOUBLE:
                    modifier=subList->subList.expectedModCount=FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedSubList
                            .incrementModCount(subList.seq);
                    break;
                case FLOAT:
                    modifier=subList->subList.expectedModCount=FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList
                            .incrementModCount(subList.seq);
                    break;
                case INT:
                    modifier=subList->subList.expectedModCount=FieldAndMethodAccessor.IntDblLnkSeq.CheckedSubList
                            .incrementModCount(subList.seq);
                    break;
                case LONG:
                    modifier=subList->subList.expectedModCount=FieldAndMethodAccessor.LongDblLnkSeq.CheckedSubList
                            .incrementModCount(subList.seq);
                    break;
                case REF:
                    modifier=subList->subList.expectedModCount=FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList
                            .incrementModCount(subList.seq);
                    break;
                case SHORT:
                    modifier=subList->subList.expectedModCount=FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList
                            .incrementModCount(subList.seq);
                    break;
                default:
                    throw dataType.invalid();
                }
                var curr=this;
                do{
                    modifier.accept(curr);
                }while((curr=curr.expectedParent) != null);
                expectedRoot.modCollection();
            }
            @Override
            public void modParent(){
                expectedParent.modCollection();
            }
            @Override
            public void modRoot(){
                expectedRoot.modCollection();
            }
            @Override
            public Object removeFirst(){
                final var removed=seq.remove(0);
                updateRemoveIndexState(expectedRootOffset);
                return removed;
            }
            @Override
            public void repairModCount(){
                if(expectedRoot.checkedType.checked){
                    final int rootModCount=expectedRoot.expectedModCount;
                    Consumer<SubListMonitor<SUBLIST,LSTDEQ,E>> modCountRepairer;
                    switch(expectedRoot.dataType){
                    case BOOLEAN:{
                        modCountRepairer=monitor->{
                            FieldAndMethodAccessor.setIntValue(
                                    FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.modCountField,monitor.seq,
                                    rootModCount);
                            monitor.expectedModCount=rootModCount;
                        };
                        break;
                    }
                    case BYTE:{
                        modCountRepairer=monitor->{
                            FieldAndMethodAccessor.setIntValue(
                                    FieldAndMethodAccessor.ByteDblLnkSeq.CheckedSubList.modCountField,monitor.seq,
                                    rootModCount);
                            monitor.expectedModCount=rootModCount;
                        };
                        break;
                    }
                    case CHAR:{
                        modCountRepairer=monitor->{
                            FieldAndMethodAccessor.setIntValue(
                                    FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.modCountField,monitor.seq,
                                    rootModCount);
                            monitor.expectedModCount=rootModCount;
                        };
                        break;
                    }
                    case SHORT:{
                        modCountRepairer=monitor->{
                            FieldAndMethodAccessor.setIntValue(
                                    FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList.modCountField,monitor.seq,
                                    rootModCount);
                            monitor.expectedModCount=rootModCount;
                        };
                        break;
                    }
                    case INT:{
                        modCountRepairer=monitor->{
                            FieldAndMethodAccessor.setIntValue(
                                    FieldAndMethodAccessor.IntDblLnkSeq.CheckedSubList.modCountField,monitor.seq,
                                    rootModCount);
                            monitor.expectedModCount=rootModCount;
                        };
                        break;
                    }
                    case LONG:{
                        modCountRepairer=monitor->{
                            FieldAndMethodAccessor.setIntValue(
                                    FieldAndMethodAccessor.LongDblLnkSeq.CheckedSubList.modCountField,monitor.seq,
                                    rootModCount);
                            monitor.expectedModCount=rootModCount;
                        };
                        break;
                    }
                    case FLOAT:{
                        modCountRepairer=monitor->{
                            FieldAndMethodAccessor.setIntValue(
                                    FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.modCountField,monitor.seq,
                                    rootModCount);
                            monitor.expectedModCount=rootModCount;
                        };
                        break;
                    }
                    case DOUBLE:{
                        modCountRepairer=monitor->{
                            FieldAndMethodAccessor.setIntValue(
                                    FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedSubList.modCountField,monitor.seq,
                                    rootModCount);
                            monitor.expectedModCount=rootModCount;
                        };
                        break;
                    }
                    case REF:{
                        modCountRepairer=monitor->{
                            FieldAndMethodAccessor.setIntValue(
                                    FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.modCountField,monitor.seq,
                                    rootModCount);
                            monitor.expectedModCount=rootModCount;
                        };
                        break;
                    }
                    default:
                        throw expectedRoot.dataType.invalid();
                    }
                    var curr=this;
                    do{
                        modCountRepairer.accept(curr);
                    }while((curr=curr.expectedParent) != null);
                }
            }
            @Override
            public int size(){
                return expectedSize;
            }
            @Override
            public void updateAddState(int index,Object inputVal,DataType inputType){
                expectedRoot.updateAddState(index + this.expectedRootOffset,inputVal,inputType);
                updateSubLists();
            }
            @Override
            public void updateClearState(){
                updateCollectionState();
            }
            @Override
            public void updateCollectionState(){
                expectedRoot.updateCollectionState();
                updateSubLists();
            }
            @Override
            public void updateRemoveIndexState(int index){
                expectedRoot.updateRemoveIndexState(index + this.expectedRootOffset);
                updateSubLists();
            }
            @Override
            public void updateRemoveValState(Object inputVal,DataType inputType){
                final int index=expectedRoot.findRemoveValIndex(inputVal,inputType,expectedRootOffset,
                        expectedRootOffset + expectedSize);
                expectedRoot.updateRemoveIndexState(index);
                updateSubLists();
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
            }
            @Override
            public void verifyArrayIsCopy(Object arr,boolean emptyArrayMayBeSame){
                // nothing to do
            }
            @Override
            public void verifyClone(Object clone){
                final var dataType=expectedRoot.dataType;
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
            public void verifyCollectionState(boolean refIsSame){
                final var dataType=expectedRoot.dataType;
                switch(dataType){
                case BOOLEAN:
                    verifyBooleanIntegrity();
                    break;
                case BYTE:
                    verifyByteIntegrity();
                    break;
                case CHAR:
                    verifyCharIntegrity();
                    break;
                case DOUBLE:
                    verifyDoubleIntegrity();
                    break;
                case FLOAT:
                    verifyFloatIntegrity();
                    break;
                case INT:
                    verifyIntIntegrity();
                    break;
                case LONG:
                    verifyLongIntegrity();
                    break;
                case REF:
                    verifyRefIntegrity(refIsSame);
                    break;
                case SHORT:
                    verifyShortIntegrity();
                    break;
                default:
                    throw dataType.invalid();
                }
            }
            @Override
            public void verifyGetResult(int index,Object result,DataType outputType){
                expectedRoot.verifyGetResult(index + this.expectedRootOffset,result,outputType);
            }
            @Override
            public void verifyPutResult(int index,Object input,DataType inputType){
                expectedRoot.verifyPutResult(index + this.expectedRootOffset,input,inputType);
            }
            @Override
            public void verifyReadAndWriteClone(SUBLIST readCol){
                verifyRefClone(readCol,false);
            }
            @Override
            public void verifyRemoveIf(boolean result,MonitoredRemoveIfPredicate filter){
                Assertions.assertNotEquals(result,filter.removedVals.isEmpty());
                Assertions.assertNotEquals(result,filter.numRemoved == 0);
                final int expectedSize=this.expectedSize;
                final int offset=expectedRootOffset;
                final int bound=offset + expectedSize;
                final var dataType=expectedRoot.dataType;
                if(result){
                    int numRemoved;
                    if(dataType == DataType.BOOLEAN){
                        final var expectedArr=(boolean[])expectedRoot.expectedArr;
                        if(filter.removedVals.contains(Boolean.TRUE)){
                            if(filter.removedVals.contains(Boolean.FALSE)){
                                Assertions.assertTrue(filter.retainedVals.isEmpty());
                                Assertions.assertEquals(0,filter.numRetained);
                                int i=bound - 1;
                                final boolean firstVal=expectedArr[i];
                                while(expectedArr[--i] == firstVal){
                                    // we are expecting this condition to be met before i<offset. Otherwise,
                                    // something is wrong.
                                }
                                numRemoved=expectedSize;
                            }else{
                                numRemoved=1;
                                int i=offset;
                                for(;;++i){
                                    if(expectedArr[i]){
                                        for(int j=i + 1;j < bound;++j){
                                            if(!expectedArr[j]){
                                                expectedArr[i++]=false;
                                            }else{
                                                ++numRemoved;
                                            }
                                        }
                                        break;
                                    }
                                }
                            }
                        }else{
                            numRemoved=1;
                            int i=offset;
                            for(;;++i){
                                if(!expectedArr[i]){
                                    for(int j=i + 1;j < bound;++j){
                                        if(expectedArr[j]){
                                            expectedArr[i++]=true;
                                        }else{
                                            ++numRemoved;
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    }else{
                        Assertions.assertEquals(expectedSize,filter.numCalls);
                        IntBinaryOperator remover;
                        switch(dataType){
                        case BYTE:{
                            final var castArr=(byte[])expectedRoot.expectedArr;
                            remover=(srcIndex,dstIndex)->{
                                final var val=castArr[srcIndex];
                                final boolean removedContains=filter.removedVals.contains(val);
                                final boolean retainedContains=filter.retainedVals.contains(val);
                                Assertions.assertNotEquals(removedContains,retainedContains);
                                if(retainedContains){
                                    castArr[dstIndex++]=val;
                                }
                                return dstIndex;
                            };
                            break;
                        }
                        case CHAR:{
                            final var castArr=(char[])expectedRoot.expectedArr;
                            remover=(srcIndex,dstIndex)->{
                                final var val=castArr[srcIndex];
                                final boolean removedContains=filter.removedVals.contains(val);
                                final boolean retainedContains=filter.retainedVals.contains(val);
                                Assertions.assertNotEquals(removedContains,retainedContains);
                                if(retainedContains){
                                    castArr[dstIndex++]=val;
                                }
                                return dstIndex;
                            };
                            break;
                        }
                        case SHORT:{
                            final var castArr=(short[])expectedRoot.expectedArr;
                            remover=(srcIndex,dstIndex)->{
                                final var val=castArr[srcIndex];
                                final boolean removedContains=filter.removedVals.contains(val);
                                final boolean retainedContains=filter.retainedVals.contains(val);
                                Assertions.assertNotEquals(removedContains,retainedContains);
                                if(retainedContains){
                                    castArr[dstIndex++]=val;
                                }
                                return dstIndex;
                            };
                            break;
                        }
                        case INT:{
                            final var castArr=(int[])expectedRoot.expectedArr;
                            remover=(srcIndex,dstIndex)->{
                                final var val=castArr[srcIndex];
                                final boolean removedContains=filter.removedVals.contains(val);
                                final boolean retainedContains=filter.retainedVals.contains(val);
                                Assertions.assertNotEquals(removedContains,retainedContains);
                                if(retainedContains){
                                    castArr[dstIndex++]=val;
                                }
                                return dstIndex;
                            };
                            break;
                        }
                        case LONG:{
                            final var castArr=(long[])expectedRoot.expectedArr;
                            remover=(srcIndex,dstIndex)->{
                                final var val=castArr[srcIndex];
                                final boolean removedContains=filter.removedVals.contains(val);
                                final boolean retainedContains=filter.retainedVals.contains(val);
                                Assertions.assertNotEquals(removedContains,retainedContains);
                                if(retainedContains){
                                    castArr[dstIndex++]=val;
                                }
                                return dstIndex;
                            };
                            break;
                        }
                        case FLOAT:{
                            final var castArr=(float[])expectedRoot.expectedArr;
                            remover=(srcIndex,dstIndex)->{
                                final var val=castArr[srcIndex];
                                final boolean removedContains=filter.removedVals.contains(val);
                                final boolean retainedContains=filter.retainedVals.contains(val);
                                Assertions.assertNotEquals(removedContains,retainedContains);
                                if(retainedContains){
                                    castArr[dstIndex++]=val;
                                }
                                return dstIndex;
                            };
                            break;
                        }
                        case DOUBLE:{
                            final var castArr=(double[])expectedRoot.expectedArr;
                            remover=(srcIndex,dstIndex)->{
                                final var val=castArr[srcIndex];
                                final boolean removedContains=filter.removedVals.contains(val);
                                final boolean retainedContains=filter.retainedVals.contains(val);
                                Assertions.assertNotEquals(removedContains,retainedContains);
                                if(retainedContains){
                                    castArr[dstIndex++]=val;
                                }
                                return dstIndex;
                            };
                            break;
                        }
                        case REF:{
                            final var castArr=(Object[])expectedRoot.expectedArr;
                            remover=(srcIndex,dstIndex)->{
                                final var val=castArr[srcIndex];
                                final boolean removedContains=filter.removedVals.contains(val);
                                final boolean retainedContains=filter.retainedVals.contains(val);
                                Assertions.assertNotEquals(removedContains,retainedContains);
                                if(retainedContains){
                                    castArr[dstIndex++]=val;
                                }
                                return dstIndex;
                            };
                            break;
                        }
                        default:
                            throw dataType.invalid();
                        }
                        int dstOffset=offset;
                        for(int srcOffset=offset;srcOffset < bound;++srcOffset){
                            dstOffset=remover.applyAsInt(srcOffset,dstOffset);
                        }
                        numRemoved=bound - dstOffset;
                        Assertions.assertEquals(filter.numRemoved,numRemoved);
                        Assertions.assertEquals(filter.numRetained,dstOffset - offset);
                    }
                    System.arraycopy(expectedRoot.expectedArr,bound,expectedRoot.expectedArr,bound - numRemoved,
                            expectedRoot.expectedSize - bound);
                    if(dataType == DataType.REF){
                        final var expectedArr=(Object[])expectedRoot.expectedArr;
                        final int rootBound=expectedRoot.expectedSize;
                        int newRootBound=rootBound - numRemoved;
                        while(newRootBound != rootBound){
                            expectedArr[newRootBound]=null;
                            ++newRootBound;
                        }
                    }
                    updateSubLists();
                    expectedRoot.expectedSize-=numRemoved;
                    ++expectedRoot.expectedModCount;
                }else{
                    if(dataType == DataType.BOOLEAN){
                        final var expectedArr=(boolean[])expectedRoot.expectedArr;
                        if(filter.retainedVals.contains(Boolean.TRUE)){
                            if(filter.retainedVals.contains(Boolean.FALSE)){
                                int i=expectedRootOffset + expectedSize - 1;
                                final boolean firstVal=expectedArr[i];
                                while(expectedArr[--i] == firstVal){
                                    // we are expecting this condition to be met before expectedSize==-1. Otherwise,
                                    // something is wrong.
                                }
                            }else{
                                for(int i=expectedRootOffset + expectedSize;--i >= expectedRootOffset;){
                                    Assertions.assertTrue(expectedArr[i]);
                                }
                            }
                        }else{
                            if(filter.retainedVals.contains(Boolean.FALSE)){
                                for(int i=expectedRootOffset + expectedSize;--i >= expectedRootOffset;){
                                    Assertions.assertFalse(expectedArr[i]);
                                }
                            }else{
                                Assertions.assertEquals(0,expectedSize);
                            }
                        }
                    }else{
                        Assertions.assertEquals(expectedSize,filter.numCalls);
                        Assertions.assertEquals(expectedSize,filter.numRetained);
                        final var itr=seq.iterator();
                        while(itr.hasNext()){
                            Assertions.assertTrue(filter.retainedVals.contains(itr.next()));
                        }
                    }
                }
            }
            @Override
            public void writeObjectImpl(MonitoredObjectOutputStream oos) throws IOException{
                final var dataType=expectedRoot.getDataType();
                final var checked=expectedRoot.checkedType.checked;
                switch(dataType){
                case BOOLEAN:
                    if(checked){
                        FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.writeObject(seq,oos);
                    }else{
                        FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedSubList.writeObject(seq,oos);
                    }
                    break;
                case BYTE:
                    if(checked){
                        FieldAndMethodAccessor.ByteDblLnkSeq.CheckedSubList.writeObject(seq,oos);
                    }else{
                        FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedSubList.writeObject(seq,oos);
                    }
                    break;
                case CHAR:
                    if(checked){
                        FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.writeObject(seq,oos);
                    }else{
                        FieldAndMethodAccessor.CharDblLnkSeq.UncheckedSubList.writeObject(seq,oos);
                    }
                    break;
                case DOUBLE:
                    if(checked){
                        FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedSubList.writeObject(seq,oos);
                    }else{
                        FieldAndMethodAccessor.DoubleDblLnkSeq.UncheckedSubList.writeObject(seq,oos);
                    }
                    break;
                case FLOAT:
                    if(checked){
                        FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.writeObject(seq,oos);
                    }else{
                        FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedSubList.writeObject(seq,oos);
                    }
                    break;
                case INT:
                    if(checked){
                        FieldAndMethodAccessor.IntDblLnkSeq.CheckedSubList.writeObject(seq,oos);
                    }else{
                        FieldAndMethodAccessor.IntDblLnkSeq.UncheckedSubList.writeObject(seq,oos);
                    }
                    break;
                case LONG:
                    if(checked){
                        FieldAndMethodAccessor.LongDblLnkSeq.CheckedSubList.writeObject(seq,oos);
                    }else{
                        FieldAndMethodAccessor.LongDblLnkSeq.UncheckedSubList.writeObject(seq,oos);
                    }
                    break;
                case REF:
                    if(checked){
                        FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.writeObject(seq,oos);
                    }else{
                        FieldAndMethodAccessor.RefDblLnkSeq.UncheckedSubList.writeObject(seq,oos);
                    }
                    break;
                case SHORT:
                    if(checked){
                        FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList.writeObject(seq,oos);
                    }else{
                        FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedSubList.writeObject(seq,oos);
                    }
                    break;
                default:
                    throw dataType.invalid();
                }
            }
            private Object getNode(int index,int size){
                if((size-=index) <= index){
                    // the node is closer to the tail
                    switch(size){
                    case 0:
                        return null;
                    case 1:
                        return expectedTail;
                    default:
                        return getNodeIterateDescending(size - 1);
                    }
                }else{
                    // the node is closer to the head
                    return getNodeIterateAscending(index);
                }
            }
            private Object getNodeIterateAscending(int headDist){
                final var dataType=expectedRoot.dataType;
                switch(dataType){
                case BOOLEAN:{
                    var head=((BooleanDblLnkSeq)seq).head;
                    while(headDist > 0){
                        head=head.next;
                        --headDist;
                    }
                    return head;
                }
                case BYTE:{
                    var head=((ByteDblLnkSeq)seq).head;
                    while(headDist > 0){
                        head=head.next;
                        --headDist;
                    }
                    return head;
                }
                case CHAR:{
                    var head=((CharDblLnkSeq)seq).head;
                    while(headDist > 0){
                        head=head.next;
                        --headDist;
                    }
                    return head;
                }
                case DOUBLE:{
                    var head=((DoubleDblLnkSeq)seq).head;
                    while(headDist > 0){
                        head=head.next;
                        --headDist;
                    }
                    return head;
                }
                case FLOAT:{
                    var head=((FloatDblLnkSeq)seq).head;
                    while(headDist > 0){
                        head=head.next;
                        --headDist;
                    }
                    return head;
                }
                case INT:{
                    var head=((IntDblLnkSeq)seq).head;
                    while(headDist > 0){
                        head=head.next;
                        --headDist;
                    }
                    return head;
                }
                case LONG:{
                    var head=((LongDblLnkSeq)seq).head;
                    while(headDist > 0){
                        head=head.next;
                        --headDist;
                    }
                    return head;
                }
                case REF:{
                    @SuppressWarnings("unchecked")
                    var head=((RefDblLnkSeq<E>)seq).head;
                    while(headDist > 0){
                        head=head.next;
                        --headDist;
                    }
                    return head;
                }
                case SHORT:{
                    var head=((ShortDblLnkSeq)seq).head;
                    while(headDist > 0){
                        head=head.next;
                        --headDist;
                    }
                    return head;
                }
                default:
                    throw dataType.invalid();
                }
            }
            private Object getNodeIterateDescending(int tailDist){
                final var dataType=expectedRoot.dataType;
                switch(dataType){
                case BOOLEAN:{
                    var tail=((BooleanDblLnkSeq)seq).tail;
                    do{
                        tail=tail.prev;
                    }while(--tailDist > 0);
                    return tail;
                }
                case BYTE:{
                    var tail=((ByteDblLnkSeq)seq).tail;
                    do{
                        tail=tail.prev;
                    }while(--tailDist > 0);
                    return tail;
                }
                case CHAR:{
                    var tail=((CharDblLnkSeq)seq).tail;
                    do{
                        tail=tail.prev;
                    }while(--tailDist > 0);
                    return tail;
                }
                case DOUBLE:{
                    var tail=((DoubleDblLnkSeq)seq).tail;
                    do{
                        tail=tail.prev;
                    }while(--tailDist > 0);
                    return tail;
                }
                case FLOAT:{
                    var tail=((FloatDblLnkSeq)seq).tail;
                    do{
                        tail=tail.prev;
                    }while(--tailDist > 0);
                    return tail;
                }
                case INT:{
                    var tail=((IntDblLnkSeq)seq).tail;
                    do{
                        tail=tail.prev;
                    }while(--tailDist > 0);
                    return tail;
                }
                case LONG:{
                    var tail=((LongDblLnkSeq)seq).tail;
                    do{
                        tail=tail.prev;
                    }while(--tailDist > 0);
                    return tail;
                }
                case REF:{
                    @SuppressWarnings("unchecked")
                    var tail=((RefDblLnkSeq<E>)seq).tail;
                    do{
                        tail=tail.prev;
                    }while(--tailDist > 0);
                    return tail;
                }
                case SHORT:{
                    var tail=((ShortDblLnkSeq)seq).tail;
                    do{
                        tail=tail.prev;
                    }while(--tailDist > 0);
                    return tail;
                }
                default:
                    throw dataType.invalid();
                }
            }
            @SuppressWarnings("unchecked")
            private void updateSubLists(){
                Consumer<SubListMonitor<SUBLIST,LSTDEQ,E>> updater=subListMonitor->subListMonitor.expectedSize=((AbstractOmniCollection<E>)subListMonitor.seq).size;
                final var checked=expectedRoot.checkedType.checked;
                final var dataType=expectedRoot.dataType;
                switch(dataType){
                case BOOLEAN:
                    updater=updater.andThen(subListMonitor->{
                        subListMonitor.expectedHead=((BooleanDblLnkSeq)subListMonitor.seq).head;
                        subListMonitor.expectedTail=((BooleanDblLnkSeq)subListMonitor.seq).tail;
                    });
                    if(checked){
                        updater=updater.andThen(
                                subListMonitor->subListMonitor.expectedModCount=FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList
                                        .modCount(subListMonitor.seq));
                    }
                    break;
                case BYTE:
                    updater=updater.andThen(subListMonitor->{
                        subListMonitor.expectedHead=((ByteDblLnkSeq)subListMonitor.seq).head;
                        subListMonitor.expectedTail=((ByteDblLnkSeq)subListMonitor.seq).tail;
                    });
                    if(checked){
                        updater=updater.andThen(
                                subListMonitor->subListMonitor.expectedModCount=FieldAndMethodAccessor.ByteDblLnkSeq.CheckedSubList
                                        .modCount(subListMonitor.seq));
                    }
                    break;
                case CHAR:
                    updater=updater.andThen(subListMonitor->{
                        subListMonitor.expectedHead=((CharDblLnkSeq)subListMonitor.seq).head;
                        subListMonitor.expectedTail=((CharDblLnkSeq)subListMonitor.seq).tail;
                    });
                    if(checked){
                        updater=updater.andThen(
                                subListMonitor->subListMonitor.expectedModCount=FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList
                                        .modCount(subListMonitor.seq));
                    }
                    break;
                case DOUBLE:
                    updater=updater.andThen(subListMonitor->{
                        subListMonitor.expectedHead=((DoubleDblLnkSeq)subListMonitor.seq).head;
                        subListMonitor.expectedTail=((DoubleDblLnkSeq)subListMonitor.seq).tail;
                    });
                    if(checked){
                        updater=updater.andThen(
                                subListMonitor->subListMonitor.expectedModCount=FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedSubList
                                        .modCount(subListMonitor.seq));
                    }
                    break;
                case FLOAT:
                    updater=updater.andThen(subListMonitor->{
                        subListMonitor.expectedHead=((FloatDblLnkSeq)subListMonitor.seq).head;
                        subListMonitor.expectedTail=((FloatDblLnkSeq)subListMonitor.seq).tail;
                    });
                    if(checked){
                        updater=updater.andThen(
                                subListMonitor->subListMonitor.expectedModCount=FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList
                                        .modCount(subListMonitor.seq));
                    }
                    break;
                case INT:
                    updater=updater.andThen(subListMonitor->{
                        subListMonitor.expectedHead=((IntDblLnkSeq)subListMonitor.seq).head;
                        subListMonitor.expectedTail=((IntDblLnkSeq)subListMonitor.seq).tail;
                    });
                    if(checked){
                        updater=updater.andThen(
                                subListMonitor->subListMonitor.expectedModCount=FieldAndMethodAccessor.IntDblLnkSeq.CheckedSubList
                                        .modCount(subListMonitor.seq));
                    }
                    break;
                case LONG:
                    updater=updater.andThen(subListMonitor->{
                        subListMonitor.expectedHead=((LongDblLnkSeq)subListMonitor.seq).head;
                        subListMonitor.expectedTail=((LongDblLnkSeq)subListMonitor.seq).tail;
                    });
                    if(checked){
                        updater=updater.andThen(
                                subListMonitor->subListMonitor.expectedModCount=FieldAndMethodAccessor.LongDblLnkSeq.CheckedSubList
                                        .modCount(subListMonitor.seq));
                    }
                    break;
                case REF:
                    updater=updater.andThen(subListMonitor->{
                        subListMonitor.expectedHead=((RefDblLnkSeq<E>)subListMonitor.seq).head;
                        subListMonitor.expectedTail=((RefDblLnkSeq<E>)subListMonitor.seq).tail;
                    });
                    if(checked){
                        updater=updater.andThen(
                                subListMonitor->subListMonitor.expectedModCount=FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList
                                        .modCount(subListMonitor.seq));
                    }
                    break;
                case SHORT:
                    updater=updater.andThen(subListMonitor->{
                        subListMonitor.expectedHead=((ShortDblLnkSeq)subListMonitor.seq).head;
                        subListMonitor.expectedTail=((ShortDblLnkSeq)subListMonitor.seq).tail;
                    });
                    if(checked){
                        updater=updater.andThen(
                                subListMonitor->subListMonitor.expectedModCount=FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList
                                        .modCount(subListMonitor.seq));
                    }
                    break;
                default:
                    throw dataType.invalid();
                }
                var curr=this;
                do{
                    updater.accept(curr);
                }while((curr=curr.expectedParent) != null);
            }
            private void verifyBooleanClone(Object clone){
                Assertions.assertNotSame(clone,seq);
                int size;
                Assertions.assertEquals(size=seq.size,((AbstractOmniCollection<?>)clone).size);
                final var checked=expectedRoot.checkedType.checked;
                final var cloneCast=(BooleanDblLnkSeq)clone;
                Assertions.assertEquals(checked,cloneCast instanceof BooleanDblLnkSeq.CheckedList);
                if(checked){
                    Assertions.assertEquals(0,((BooleanDblLnkSeq.CheckedList)cloneCast).modCount);
                }else{
                    Assertions.assertTrue(cloneCast instanceof BooleanDblLnkSeq.UncheckedList);
                }
                var cloneHead=cloneCast.head;
                final var cloneTail=cloneCast.tail;
                if(size == 0){
                    Assertions.assertNull(cloneHead);
                    Assertions.assertNull(cloneTail);
                }else{
                    final var thisCast=(BooleanDblLnkSeq)seq;
                    var thisHead=thisCast.head;
                    final var thisTail=thisCast.tail;
                    var clonePrev=cloneHead.prev;
                    Assertions.assertNull(clonePrev);
                    for(int i=0;i < size;++i){
                        Assertions.assertNotSame(cloneHead,thisHead);
                        Assertions.assertEquals(cloneHead.val,thisHead.val);
                        Assertions.assertSame(clonePrev,cloneHead.prev);
                        clonePrev=cloneHead;
                        cloneHead=cloneHead.next;
                        thisHead=thisHead.next;
                    }
                    Assertions.assertSame(cloneTail,clonePrev);
                    Assertions.assertNotSame(cloneTail,thisTail);
                    Assertions.assertNull(cloneHead);
                }
            }
            private void verifyBooleanIntegrity(){
                var seq=(BooleanDblLnkSeq)this.seq;
                final var root=expectedRoot.seq;
                final var checked=expectedRoot.checkedType.checked;
                var curr=this;
                while(curr.expectedSize == 0){
                    Assertions.assertNull(seq.head);
                    Assertions.assertNull(seq.tail);
                    if(checked){
                        Assertions.assertSame(root,FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.root(seq));
                        Assertions.assertEquals(curr.expectedParentOffset,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.parentOffset(seq));
                        Assertions.assertEquals(curr.expectedModCount,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.modCount(seq));
                    }else{
                        Assertions.assertSame(root,FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedSubList.root(seq));
                        Assertions.assertEquals(curr.expectedParentOffset,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedSubList.parentOffset(seq));
                    }
                    final var expectedParent=curr.expectedParent;
                    if(expectedParent == null){
                        expectedRoot.verifyBooleanIntegrity();
                        return;
                    }else{
                        curr=expectedParent;
                        seq=(BooleanDblLnkSeq)curr.seq;
                    }
                }
                var head=seq.head;
                var tail=seq.tail;
                var currNode=head;
                var nextNode=currNode.next;
                var rootOffset=curr.expectedRootOffset;
                var rootBound=rootOffset + curr.expectedSize - 1;
                final var expectedArr=(boolean[])expectedRoot.expectedArr;
                for(int i=rootOffset;;++i){
                    Assertions.assertEquals(expectedArr[i],currNode.val);
                    if(i >= rootBound){
                        Assertions.assertSame(currNode,tail);
                        break;
                    }
                    Assertions.assertSame(nextNode.prev,currNode);
                    currNode=nextNode;
                    nextNode=currNode.next;
                }
                for(;;){
                    seq=(BooleanDblLnkSeq)curr.seq;
                    Assertions.assertSame(curr.expectedHead,head);
                    Assertions.assertSame(curr.expectedTail,tail);
                    Assertions.assertEquals(curr.expectedSize,seq.size);
                    if(checked){
                        Assertions.assertSame(root,FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.root(seq));
                        Assertions.assertEquals(curr.expectedParentOffset,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.parentOffset(seq));
                        Assertions.assertEquals(curr.expectedModCount,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.modCount(seq));
                    }else{
                        Assertions.assertSame(root,FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedSubList.root(seq));
                        Assertions.assertEquals(curr.expectedParentOffset,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedSubList.parentOffset(seq));
                    }
                    for(final int nextRootOffset=rootOffset - curr.expectedParentOffset;rootOffset != nextRootOffset;){
                        nextNode=head;
                        head=head.prev;
                        Assertions.assertSame(head.next,nextNode);
                        Assertions.assertEquals(expectedArr[--rootOffset],head.val);
                    }
                    final var expectedParent=curr.expectedParent;
                    if(expectedParent == null){
                        if(checked){
                            Assertions.assertNull(FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.parent(seq));
                        }else{
                            Assertions.assertNull(FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedSubList.parent(seq));
                        }
                        break;
                    }else{
                        if(checked){
                            Assertions.assertSame(expectedParent.seq,
                                    FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.parent(seq));
                        }else{
                            Assertions.assertSame(expectedParent.seq,
                                    FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedSubList.parent(seq));
                        }
                        for(final var nextRootBound=expectedParent.expectedRootOffset + expectedParent.expectedSize
                                - 1;rootBound != nextRootBound;){
                            nextNode=tail;
                            tail=tail.next;
                            Assertions.assertSame(tail.prev,nextNode);
                            Assertions.assertEquals(expectedArr[++rootBound],tail.val);
                        }
                        curr=expectedParent;
                    }
                }
                for(final var nextRootBound=expectedRoot.expectedSize - 1;rootBound != nextRootBound;){
                    nextNode=tail;
                    tail=tail.next;
                    Assertions.assertSame(tail.prev,nextNode);
                    Assertions.assertEquals(expectedArr[++rootBound],tail.val);
                }
                Assertions.assertNull(head.prev);
                Assertions.assertNull(tail.next);
                seq=(BooleanDblLnkSeq)expectedRoot.seq;
                Assertions.assertSame(head,seq.head);
                Assertions.assertSame(tail,seq.tail);
                Assertions.assertEquals(expectedRoot.expectedSize,seq.size);
                if(checked){
                    Assertions.assertEquals(expectedRoot.expectedModCount,((BooleanDblLnkSeq.CheckedList)seq).modCount);
                }
            }
            private void verifyByteClone(Object clone){
                Assertions.assertNotSame(clone,seq);
                int size;
                Assertions.assertEquals(size=seq.size,((AbstractOmniCollection<?>)clone).size);
                final var checked=expectedRoot.checkedType.checked;
                final var cloneCast=(ByteDblLnkSeq)clone;
                Assertions.assertEquals(checked,cloneCast instanceof ByteDblLnkSeq.CheckedList);
                if(checked){
                    Assertions.assertEquals(0,((ByteDblLnkSeq.CheckedList)cloneCast).modCount);
                }else{
                    Assertions.assertTrue(cloneCast instanceof ByteDblLnkSeq.UncheckedList);
                }
                var cloneHead=cloneCast.head;
                final var cloneTail=cloneCast.tail;
                if(size == 0){
                    Assertions.assertNull(cloneHead);
                    Assertions.assertNull(cloneTail);
                }else{
                    final var thisCast=(ByteDblLnkSeq)seq;
                    var thisHead=thisCast.head;
                    final var thisTail=thisCast.tail;
                    var clonePrev=cloneHead.prev;
                    Assertions.assertNull(clonePrev);
                    for(int i=0;i < size;++i){
                        Assertions.assertNotSame(cloneHead,thisHead);
                        Assertions.assertEquals(cloneHead.val,thisHead.val);
                        Assertions.assertSame(clonePrev,cloneHead.prev);
                        clonePrev=cloneHead;
                        cloneHead=cloneHead.next;
                        thisHead=thisHead.next;
                    }
                    Assertions.assertSame(cloneTail,clonePrev);
                    Assertions.assertNotSame(cloneTail,thisTail);
                    Assertions.assertNull(cloneHead);
                }
            }
            private void verifyByteIntegrity(){
                var seq=(ByteDblLnkSeq)this.seq;
                final var root=expectedRoot.seq;
                final var checked=expectedRoot.checkedType.checked;
                var curr=this;
                while(curr.expectedSize == 0){
                    Assertions.assertNull(seq.head);
                    Assertions.assertNull(seq.tail);
                    if(checked){
                        Assertions.assertSame(root,FieldAndMethodAccessor.ByteDblLnkSeq.CheckedSubList.root(seq));
                        Assertions.assertEquals(curr.expectedParentOffset,
                                FieldAndMethodAccessor.ByteDblLnkSeq.CheckedSubList.parentOffset(seq));
                        Assertions.assertEquals(curr.expectedModCount,
                                FieldAndMethodAccessor.ByteDblLnkSeq.CheckedSubList.modCount(seq));
                    }else{
                        Assertions.assertSame(root,FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedSubList.root(seq));
                        Assertions.assertEquals(curr.expectedParentOffset,
                                FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedSubList.parentOffset(seq));
                    }
                    final var expectedParent=curr.expectedParent;
                    if(expectedParent == null){
                        expectedRoot.verifyByteIntegrity();
                        return;
                    }else{
                        curr=expectedParent;
                        seq=(ByteDblLnkSeq)curr.seq;
                    }
                }
                var head=seq.head;
                var tail=seq.tail;
                var currNode=head;
                var nextNode=currNode.next;
                var rootOffset=curr.expectedRootOffset;
                var rootBound=rootOffset + curr.expectedSize - 1;
                final var expectedArr=(byte[])expectedRoot.expectedArr;
                for(int i=rootOffset;;++i){
                    Assertions.assertEquals(expectedArr[i],currNode.val);
                    if(i >= rootBound){
                        Assertions.assertSame(currNode,tail);
                        break;
                    }
                    Assertions.assertSame(nextNode.prev,currNode);
                    currNode=nextNode;
                    nextNode=currNode.next;
                }
                for(;;){
                    seq=(ByteDblLnkSeq)curr.seq;
                    Assertions.assertSame(curr.expectedHead,head);
                    Assertions.assertSame(curr.expectedTail,tail);
                    Assertions.assertEquals(curr.expectedSize,seq.size);
                    if(checked){
                        Assertions.assertSame(root,FieldAndMethodAccessor.ByteDblLnkSeq.CheckedSubList.root(seq));
                        Assertions.assertEquals(curr.expectedParentOffset,
                                FieldAndMethodAccessor.ByteDblLnkSeq.CheckedSubList.parentOffset(seq));
                        Assertions.assertEquals(curr.expectedModCount,
                                FieldAndMethodAccessor.ByteDblLnkSeq.CheckedSubList.modCount(seq));
                    }else{
                        Assertions.assertSame(root,FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedSubList.root(seq));
                        Assertions.assertEquals(curr.expectedParentOffset,
                                FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedSubList.parentOffset(seq));
                    }
                    for(final int nextRootOffset=rootOffset - curr.expectedParentOffset;rootOffset != nextRootOffset;){
                        nextNode=head;
                        head=head.prev;
                        Assertions.assertSame(head.next,nextNode);
                        Assertions.assertEquals(expectedArr[--rootOffset],head.val);
                    }
                    final var expectedParent=curr.expectedParent;
                    if(expectedParent == null){
                        if(checked){
                            Assertions.assertNull(FieldAndMethodAccessor.ByteDblLnkSeq.CheckedSubList.parent(seq));
                        }else{
                            Assertions.assertNull(FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedSubList.parent(seq));
                        }
                        break;
                    }else{
                        if(checked){
                            Assertions.assertSame(expectedParent.seq,
                                    FieldAndMethodAccessor.ByteDblLnkSeq.CheckedSubList.parent(seq));
                        }else{
                            Assertions.assertSame(expectedParent.seq,
                                    FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedSubList.parent(seq));
                        }
                        for(final var nextRootBound=expectedParent.expectedRootOffset + expectedParent.expectedSize
                                - 1;rootBound != nextRootBound;){
                            nextNode=tail;
                            tail=tail.next;
                            Assertions.assertSame(tail.prev,nextNode);
                            Assertions.assertEquals(expectedArr[++rootBound],tail.val);
                        }
                        curr=expectedParent;
                    }
                }
                for(final var nextRootBound=expectedRoot.expectedSize - 1;rootBound != nextRootBound;){
                    nextNode=tail;
                    tail=tail.next;
                    Assertions.assertSame(tail.prev,nextNode);
                    Assertions.assertEquals(expectedArr[++rootBound],tail.val);
                }
                Assertions.assertNull(head.prev);
                Assertions.assertNull(tail.next);
                seq=(ByteDblLnkSeq)expectedRoot.seq;
                Assertions.assertSame(head,seq.head);
                Assertions.assertSame(tail,seq.tail);
                Assertions.assertEquals(expectedRoot.expectedSize,seq.size);
                if(checked){
                    Assertions.assertEquals(expectedRoot.expectedModCount,((ByteDblLnkSeq.CheckedList)seq).modCount);
                }
            }
            private void verifyCharClone(Object clone){
                Assertions.assertNotSame(clone,seq);
                int size;
                Assertions.assertEquals(size=seq.size,((AbstractOmniCollection<?>)clone).size);
                final var checked=expectedRoot.checkedType.checked;
                final var cloneCast=(CharDblLnkSeq)clone;
                Assertions.assertEquals(checked,cloneCast instanceof CharDblLnkSeq.CheckedList);
                if(checked){
                    Assertions.assertEquals(0,((CharDblLnkSeq.CheckedList)cloneCast).modCount);
                }else{
                    Assertions.assertTrue(cloneCast instanceof CharDblLnkSeq.UncheckedList);
                }
                var cloneHead=cloneCast.head;
                final var cloneTail=cloneCast.tail;
                if(size == 0){
                    Assertions.assertNull(cloneHead);
                    Assertions.assertNull(cloneTail);
                }else{
                    final var thisCast=(CharDblLnkSeq)seq;
                    var thisHead=thisCast.head;
                    final var thisTail=thisCast.tail;
                    var clonePrev=cloneHead.prev;
                    Assertions.assertNull(clonePrev);
                    for(int i=0;i < size;++i){
                        Assertions.assertNotSame(cloneHead,thisHead);
                        Assertions.assertEquals(cloneHead.val,thisHead.val);
                        Assertions.assertSame(clonePrev,cloneHead.prev);
                        clonePrev=cloneHead;
                        cloneHead=cloneHead.next;
                        thisHead=thisHead.next;
                    }
                    Assertions.assertSame(cloneTail,clonePrev);
                    Assertions.assertNotSame(cloneTail,thisTail);
                    Assertions.assertNull(cloneHead);
                }
            }
            private void verifyCharIntegrity(){
                var seq=(CharDblLnkSeq)this.seq;
                final var root=expectedRoot.seq;
                final var checked=expectedRoot.checkedType.checked;
                var curr=this;
                while(curr.expectedSize == 0){
                    Assertions.assertNull(seq.head);
                    Assertions.assertNull(seq.tail);
                    if(checked){
                        Assertions.assertSame(root,FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.root(seq));
                        Assertions.assertEquals(curr.expectedParentOffset,
                                FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.parentOffset(seq));
                        Assertions.assertEquals(curr.expectedModCount,
                                FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.modCount(seq));
                    }else{
                        Assertions.assertSame(root,FieldAndMethodAccessor.CharDblLnkSeq.UncheckedSubList.root(seq));
                        Assertions.assertEquals(curr.expectedParentOffset,
                                FieldAndMethodAccessor.CharDblLnkSeq.UncheckedSubList.parentOffset(seq));
                    }
                    final var expectedParent=curr.expectedParent;
                    if(expectedParent == null){
                        expectedRoot.verifyCharIntegrity();
                        return;
                    }else{
                        curr=expectedParent;
                        seq=(CharDblLnkSeq)curr.seq;
                    }
                }
                var head=seq.head;
                var tail=seq.tail;
                var currNode=head;
                var nextNode=currNode.next;
                var rootOffset=curr.expectedRootOffset;
                var rootBound=rootOffset + curr.expectedSize - 1;
                final var expectedArr=(char[])expectedRoot.expectedArr;
                for(int i=rootOffset;;++i){
                    Assertions.assertEquals(expectedArr[i],currNode.val);
                    if(i >= rootBound){
                        Assertions.assertSame(currNode,tail);
                        break;
                    }
                    Assertions.assertSame(nextNode.prev,currNode);
                    currNode=nextNode;
                    nextNode=currNode.next;
                }
                for(;;){
                    seq=(CharDblLnkSeq)curr.seq;
                    Assertions.assertSame(curr.expectedHead,head);
                    Assertions.assertSame(curr.expectedTail,tail);
                    Assertions.assertEquals(curr.expectedSize,seq.size);
                    if(checked){
                        Assertions.assertSame(root,FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.root(seq));
                        Assertions.assertEquals(curr.expectedParentOffset,
                                FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.parentOffset(seq));
                        Assertions.assertEquals(curr.expectedModCount,
                                FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.modCount(seq));
                    }else{
                        Assertions.assertSame(root,FieldAndMethodAccessor.CharDblLnkSeq.UncheckedSubList.root(seq));
                        Assertions.assertEquals(curr.expectedParentOffset,
                                FieldAndMethodAccessor.CharDblLnkSeq.UncheckedSubList.parentOffset(seq));
                    }
                    for(final int nextRootOffset=rootOffset - curr.expectedParentOffset;rootOffset != nextRootOffset;){
                        nextNode=head;
                        head=head.prev;
                        Assertions.assertSame(head.next,nextNode);
                        Assertions.assertEquals(expectedArr[--rootOffset],head.val);
                    }
                    final var expectedParent=curr.expectedParent;
                    if(expectedParent == null){
                        if(checked){
                            Assertions.assertNull(FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.parent(seq));
                        }else{
                            Assertions.assertNull(FieldAndMethodAccessor.CharDblLnkSeq.UncheckedSubList.parent(seq));
                        }
                        break;
                    }else{
                        if(checked){
                            Assertions.assertSame(expectedParent.seq,
                                    FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.parent(seq));
                        }else{
                            Assertions.assertSame(expectedParent.seq,
                                    FieldAndMethodAccessor.CharDblLnkSeq.UncheckedSubList.parent(seq));
                        }
                        for(final var nextRootBound=expectedParent.expectedRootOffset + expectedParent.expectedSize
                                - 1;rootBound != nextRootBound;){
                            nextNode=tail;
                            tail=tail.next;
                            Assertions.assertSame(tail.prev,nextNode);
                            Assertions.assertEquals(expectedArr[++rootBound],tail.val);
                        }
                        curr=expectedParent;
                    }
                }
                for(final var nextRootBound=expectedRoot.expectedSize - 1;rootBound != nextRootBound;){
                    nextNode=tail;
                    tail=tail.next;
                    Assertions.assertSame(tail.prev,nextNode);
                    Assertions.assertEquals(expectedArr[++rootBound],tail.val);
                }
                Assertions.assertNull(head.prev);
                Assertions.assertNull(tail.next);
                seq=(CharDblLnkSeq)expectedRoot.seq;
                Assertions.assertSame(head,seq.head);
                Assertions.assertSame(tail,seq.tail);
                Assertions.assertEquals(expectedRoot.expectedSize,seq.size);
                if(checked){
                    Assertions.assertEquals(expectedRoot.expectedModCount,((CharDblLnkSeq.CheckedList)seq).modCount);
                }
            }
            private void verifyDoubleClone(Object clone){
                Assertions.assertNotSame(clone,seq);
                int size;
                Assertions.assertEquals(size=seq.size,((AbstractOmniCollection<?>)clone).size);
                final var checked=expectedRoot.checkedType.checked;
                final var cloneCast=(DoubleDblLnkSeq)clone;
                Assertions.assertEquals(checked,cloneCast instanceof DoubleDblLnkSeq.CheckedList);
                if(checked){
                    Assertions.assertEquals(0,((DoubleDblLnkSeq.CheckedList)cloneCast).modCount);
                }else{
                    Assertions.assertTrue(cloneCast instanceof DoubleDblLnkSeq.UncheckedList);
                }
                var cloneHead=cloneCast.head;
                final var cloneTail=cloneCast.tail;
                if(size == 0){
                    Assertions.assertNull(cloneHead);
                    Assertions.assertNull(cloneTail);
                }else{
                    final var thisCast=(DoubleDblLnkSeq)seq;
                    var thisHead=thisCast.head;
                    final var thisTail=thisCast.tail;
                    var clonePrev=cloneHead.prev;
                    Assertions.assertNull(clonePrev);
                    for(int i=0;i < size;++i){
                        Assertions.assertNotSame(cloneHead,thisHead);
                        Assertions.assertEquals(cloneHead.val,thisHead.val);
                        Assertions.assertSame(clonePrev,cloneHead.prev);
                        clonePrev=cloneHead;
                        cloneHead=cloneHead.next;
                        thisHead=thisHead.next;
                    }
                    Assertions.assertSame(cloneTail,clonePrev);
                    Assertions.assertNotSame(cloneTail,thisTail);
                    Assertions.assertNull(cloneHead);
                }
            }
            private void verifyDoubleIntegrity(){
                var seq=(DoubleDblLnkSeq)this.seq;
                final var root=expectedRoot.seq;
                final var checked=expectedRoot.checkedType.checked;
                var curr=this;
                while(curr.expectedSize == 0){
                    Assertions.assertNull(seq.head);
                    Assertions.assertNull(seq.tail);
                    if(checked){
                        Assertions.assertSame(root,FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedSubList.root(seq));
                        Assertions.assertEquals(curr.expectedParentOffset,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedSubList.parentOffset(seq));
                        Assertions.assertEquals(curr.expectedModCount,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedSubList.modCount(seq));
                    }else{
                        Assertions.assertSame(root,FieldAndMethodAccessor.DoubleDblLnkSeq.UncheckedSubList.root(seq));
                        Assertions.assertEquals(curr.expectedParentOffset,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.UncheckedSubList.parentOffset(seq));
                    }
                    final var expectedParent=curr.expectedParent;
                    if(expectedParent == null){
                        expectedRoot.verifyDoubleIntegrity();
                        return;
                    }else{
                        curr=expectedParent;
                        seq=(DoubleDblLnkSeq)curr.seq;
                    }
                }
                var head=seq.head;
                var tail=seq.tail;
                var currNode=head;
                var nextNode=currNode.next;
                var rootOffset=curr.expectedRootOffset;
                var rootBound=rootOffset + curr.expectedSize - 1;
                final var expectedArr=(double[])expectedRoot.expectedArr;
                for(int i=rootOffset;;++i){
                    Assertions.assertEquals(expectedArr[i],currNode.val);
                    if(i >= rootBound){
                        Assertions.assertSame(currNode,tail);
                        break;
                    }
                    Assertions.assertSame(nextNode.prev,currNode);
                    currNode=nextNode;
                    nextNode=currNode.next;
                }
                for(;;){
                    seq=(DoubleDblLnkSeq)curr.seq;
                    Assertions.assertSame(curr.expectedHead,head);
                    Assertions.assertSame(curr.expectedTail,tail);
                    Assertions.assertEquals(curr.expectedSize,seq.size);
                    if(checked){
                        Assertions.assertSame(root,FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedSubList.root(seq));
                        Assertions.assertEquals(curr.expectedParentOffset,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedSubList.parentOffset(seq));
                        Assertions.assertEquals(curr.expectedModCount,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedSubList.modCount(seq));
                    }else{
                        Assertions.assertSame(root,FieldAndMethodAccessor.DoubleDblLnkSeq.UncheckedSubList.root(seq));
                        Assertions.assertEquals(curr.expectedParentOffset,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.UncheckedSubList.parentOffset(seq));
                    }
                    for(final int nextRootOffset=rootOffset - curr.expectedParentOffset;rootOffset != nextRootOffset;){
                        nextNode=head;
                        head=head.prev;
                        Assertions.assertSame(head.next,nextNode);
                        Assertions.assertEquals(expectedArr[--rootOffset],head.val);
                    }
                    final var expectedParent=curr.expectedParent;
                    if(expectedParent == null){
                        if(checked){
                            Assertions.assertNull(FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedSubList.parent(seq));
                        }else{
                            Assertions.assertNull(FieldAndMethodAccessor.DoubleDblLnkSeq.UncheckedSubList.parent(seq));
                        }
                        break;
                    }else{
                        if(checked){
                            Assertions.assertSame(expectedParent.seq,
                                    FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedSubList.parent(seq));
                        }else{
                            Assertions.assertSame(expectedParent.seq,
                                    FieldAndMethodAccessor.DoubleDblLnkSeq.UncheckedSubList.parent(seq));
                        }
                        for(final var nextRootBound=expectedParent.expectedRootOffset + expectedParent.expectedSize
                                - 1;rootBound != nextRootBound;){
                            nextNode=tail;
                            tail=tail.next;
                            Assertions.assertSame(tail.prev,nextNode);
                            Assertions.assertEquals(expectedArr[++rootBound],tail.val);
                        }
                        curr=expectedParent;
                    }
                }
                for(final var nextRootBound=expectedRoot.expectedSize - 1;rootBound != nextRootBound;){
                    nextNode=tail;
                    tail=tail.next;
                    Assertions.assertSame(tail.prev,nextNode);
                    Assertions.assertEquals(expectedArr[++rootBound],tail.val);
                }
                Assertions.assertNull(head.prev);
                Assertions.assertNull(tail.next);
                seq=(DoubleDblLnkSeq)expectedRoot.seq;
                Assertions.assertSame(head,seq.head);
                Assertions.assertSame(tail,seq.tail);
                Assertions.assertEquals(expectedRoot.expectedSize,seq.size);
                if(checked){
                    Assertions.assertEquals(expectedRoot.expectedModCount,((DoubleDblLnkSeq.CheckedList)seq).modCount);
                }
            }
            private void verifyFloatClone(Object clone){
                Assertions.assertNotSame(clone,seq);
                int size;
                Assertions.assertEquals(size=seq.size,((AbstractOmniCollection<?>)clone).size);
                final var checked=expectedRoot.checkedType.checked;
                final var cloneCast=(FloatDblLnkSeq)clone;
                Assertions.assertEquals(checked,cloneCast instanceof FloatDblLnkSeq.CheckedList);
                if(checked){
                    Assertions.assertEquals(0,((FloatDblLnkSeq.CheckedList)cloneCast).modCount);
                }else{
                    Assertions.assertTrue(cloneCast instanceof FloatDblLnkSeq.UncheckedList);
                }
                var cloneHead=cloneCast.head;
                final var cloneTail=cloneCast.tail;
                if(size == 0){
                    Assertions.assertNull(cloneHead);
                    Assertions.assertNull(cloneTail);
                }else{
                    final var thisCast=(FloatDblLnkSeq)seq;
                    var thisHead=thisCast.head;
                    final var thisTail=thisCast.tail;
                    var clonePrev=cloneHead.prev;
                    Assertions.assertNull(clonePrev);
                    for(int i=0;i < size;++i){
                        Assertions.assertNotSame(cloneHead,thisHead);
                        Assertions.assertEquals(cloneHead.val,thisHead.val);
                        Assertions.assertSame(clonePrev,cloneHead.prev);
                        clonePrev=cloneHead;
                        cloneHead=cloneHead.next;
                        thisHead=thisHead.next;
                    }
                    Assertions.assertSame(cloneTail,clonePrev);
                    Assertions.assertNotSame(cloneTail,thisTail);
                    Assertions.assertNull(cloneHead);
                }
            }
            private void verifyFloatIntegrity(){
                var seq=(FloatDblLnkSeq)this.seq;
                final var root=expectedRoot.seq;
                final var checked=expectedRoot.checkedType.checked;
                var curr=this;
                while(curr.expectedSize == 0){
                    Assertions.assertNull(seq.head);
                    Assertions.assertNull(seq.tail);
                    if(checked){
                        Assertions.assertSame(root,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.root(seq));
                        Assertions.assertEquals(curr.expectedParentOffset,
                                FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.parentOffset(seq));
                        Assertions.assertEquals(curr.expectedModCount,
                                FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.modCount(seq));
                    }else{
                        Assertions.assertSame(root,FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedSubList.root(seq));
                        Assertions.assertEquals(curr.expectedParentOffset,
                                FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedSubList.parentOffset(seq));
                    }
                    final var expectedParent=curr.expectedParent;
                    if(expectedParent == null){
                        expectedRoot.verifyFloatIntegrity();
                        return;
                    }else{
                        curr=expectedParent;
                        seq=(FloatDblLnkSeq)curr.seq;
                    }
                }
                var head=seq.head;
                var tail=seq.tail;
                var currNode=head;
                var nextNode=currNode.next;
                var rootOffset=curr.expectedRootOffset;
                var rootBound=rootOffset + curr.expectedSize - 1;
                final var expectedArr=(float[])expectedRoot.expectedArr;
                for(int i=rootOffset;;++i){
                    Assertions.assertEquals(expectedArr[i],currNode.val);
                    if(i >= rootBound){
                        Assertions.assertSame(currNode,tail);
                        break;
                    }
                    Assertions.assertSame(nextNode.prev,currNode);
                    currNode=nextNode;
                    nextNode=currNode.next;
                }
                for(;;){
                    seq=(FloatDblLnkSeq)curr.seq;
                    Assertions.assertSame(curr.expectedHead,head);
                    Assertions.assertSame(curr.expectedTail,tail);
                    Assertions.assertEquals(curr.expectedSize,seq.size);
                    if(checked){
                        Assertions.assertSame(root,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.root(seq));
                        Assertions.assertEquals(curr.expectedParentOffset,
                                FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.parentOffset(seq));
                        Assertions.assertEquals(curr.expectedModCount,
                                FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.modCount(seq));
                    }else{
                        Assertions.assertSame(root,FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedSubList.root(seq));
                        Assertions.assertEquals(curr.expectedParentOffset,
                                FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedSubList.parentOffset(seq));
                    }
                    for(final int nextRootOffset=rootOffset - curr.expectedParentOffset;rootOffset != nextRootOffset;){
                        nextNode=head;
                        head=head.prev;
                        Assertions.assertSame(head.next,nextNode);
                        Assertions.assertEquals(expectedArr[--rootOffset],head.val);
                    }
                    final var expectedParent=curr.expectedParent;
                    if(expectedParent == null){
                        if(checked){
                            Assertions.assertNull(FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.parent(seq));
                        }else{
                            Assertions.assertNull(FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedSubList.parent(seq));
                        }
                        break;
                    }else{
                        if(checked){
                            Assertions.assertSame(expectedParent.seq,
                                    FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.parent(seq));
                        }else{
                            Assertions.assertSame(expectedParent.seq,
                                    FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedSubList.parent(seq));
                        }
                        for(final var nextRootBound=expectedParent.expectedRootOffset + expectedParent.expectedSize
                                - 1;rootBound != nextRootBound;){
                            nextNode=tail;
                            tail=tail.next;
                            Assertions.assertSame(tail.prev,nextNode);
                            Assertions.assertEquals(expectedArr[++rootBound],tail.val);
                        }
                        curr=expectedParent;
                    }
                }
                for(final var nextRootBound=expectedRoot.expectedSize - 1;rootBound != nextRootBound;){
                    nextNode=tail;
                    tail=tail.next;
                    Assertions.assertSame(tail.prev,nextNode);
                    Assertions.assertEquals(expectedArr[++rootBound],tail.val);
                }
                Assertions.assertNull(head.prev);
                Assertions.assertNull(tail.next);
                seq=(FloatDblLnkSeq)expectedRoot.seq;
                Assertions.assertSame(head,seq.head);
                Assertions.assertSame(tail,seq.tail);
                Assertions.assertEquals(expectedRoot.expectedSize,seq.size);
                if(checked){
                    Assertions.assertEquals(expectedRoot.expectedModCount,((FloatDblLnkSeq.CheckedList)seq).modCount);
                }
            }
            private void verifyIntClone(Object clone){
                Assertions.assertNotSame(clone,seq);
                int size;
                Assertions.assertEquals(size=seq.size,((AbstractOmniCollection<?>)clone).size);
                final var checked=expectedRoot.checkedType.checked;
                final var cloneCast=(IntDblLnkSeq)clone;
                Assertions.assertEquals(checked,cloneCast instanceof IntDblLnkSeq.CheckedList);
                if(checked){
                    Assertions.assertEquals(0,((IntDblLnkSeq.CheckedList)cloneCast).modCount);
                }else{
                    Assertions.assertTrue(cloneCast instanceof IntDblLnkSeq.UncheckedList);
                }
                var cloneHead=cloneCast.head;
                final var cloneTail=cloneCast.tail;
                if(size == 0){
                    Assertions.assertNull(cloneHead);
                    Assertions.assertNull(cloneTail);
                }else{
                    final var thisCast=(IntDblLnkSeq)seq;
                    var thisHead=thisCast.head;
                    final var thisTail=thisCast.tail;
                    var clonePrev=cloneHead.prev;
                    Assertions.assertNull(clonePrev);
                    for(int i=0;i < size;++i){
                        Assertions.assertNotSame(cloneHead,thisHead);
                        Assertions.assertEquals(cloneHead.val,thisHead.val);
                        Assertions.assertSame(clonePrev,cloneHead.prev);
                        clonePrev=cloneHead;
                        cloneHead=cloneHead.next;
                        thisHead=thisHead.next;
                    }
                    Assertions.assertSame(cloneTail,clonePrev);
                    Assertions.assertNotSame(cloneTail,thisTail);
                    Assertions.assertNull(cloneHead);
                }
            }
            private void verifyIntIntegrity(){
                var seq=(IntDblLnkSeq)this.seq;
                final var root=expectedRoot.seq;
                final var checked=expectedRoot.checkedType.checked;
                var curr=this;
                while(curr.expectedSize == 0){
                    Assertions.assertNull(seq.head);
                    Assertions.assertNull(seq.tail);
                    if(checked){
                        Assertions.assertSame(root,FieldAndMethodAccessor.IntDblLnkSeq.CheckedSubList.root(seq));
                        Assertions.assertEquals(curr.expectedParentOffset,
                                FieldAndMethodAccessor.IntDblLnkSeq.CheckedSubList.parentOffset(seq));
                        Assertions.assertEquals(curr.expectedModCount,
                                FieldAndMethodAccessor.IntDblLnkSeq.CheckedSubList.modCount(seq));
                    }else{
                        Assertions.assertSame(root,FieldAndMethodAccessor.IntDblLnkSeq.UncheckedSubList.root(seq));
                        Assertions.assertEquals(curr.expectedParentOffset,
                                FieldAndMethodAccessor.IntDblLnkSeq.UncheckedSubList.parentOffset(seq));
                    }
                    final var expectedParent=curr.expectedParent;
                    if(expectedParent == null){
                        expectedRoot.verifyIntIntegrity();
                        return;
                    }else{
                        curr=expectedParent;
                        seq=(IntDblLnkSeq)curr.seq;
                    }
                }
                var head=seq.head;
                var tail=seq.tail;
                var currNode=head;
                var nextNode=currNode.next;
                var rootOffset=curr.expectedRootOffset;
                var rootBound=rootOffset + curr.expectedSize - 1;
                final var expectedArr=(int[])expectedRoot.expectedArr;
                for(int i=rootOffset;;++i){
                    Assertions.assertEquals(expectedArr[i],currNode.val);
                    if(i >= rootBound){
                        Assertions.assertSame(currNode,tail);
                        break;
                    }
                    Assertions.assertSame(nextNode.prev,currNode);
                    currNode=nextNode;
                    nextNode=currNode.next;
                }
                for(;;){
                    seq=(IntDblLnkSeq)curr.seq;
                    Assertions.assertSame(curr.expectedHead,head);
                    Assertions.assertSame(curr.expectedTail,tail);
                    Assertions.assertEquals(curr.expectedSize,seq.size);
                    if(checked){
                        Assertions.assertSame(root,FieldAndMethodAccessor.IntDblLnkSeq.CheckedSubList.root(seq));
                        Assertions.assertEquals(curr.expectedParentOffset,
                                FieldAndMethodAccessor.IntDblLnkSeq.CheckedSubList.parentOffset(seq));
                        Assertions.assertEquals(curr.expectedModCount,
                                FieldAndMethodAccessor.IntDblLnkSeq.CheckedSubList.modCount(seq));
                    }else{
                        Assertions.assertSame(root,FieldAndMethodAccessor.IntDblLnkSeq.UncheckedSubList.root(seq));
                        Assertions.assertEquals(curr.expectedParentOffset,
                                FieldAndMethodAccessor.IntDblLnkSeq.UncheckedSubList.parentOffset(seq));
                    }
                    for(final int nextRootOffset=rootOffset - curr.expectedParentOffset;rootOffset != nextRootOffset;){
                        nextNode=head;
                        head=head.prev;
                        Assertions.assertSame(head.next,nextNode);
                        Assertions.assertEquals(expectedArr[--rootOffset],head.val);
                    }
                    final var expectedParent=curr.expectedParent;
                    if(expectedParent == null){
                        if(checked){
                            Assertions.assertNull(FieldAndMethodAccessor.IntDblLnkSeq.CheckedSubList.parent(seq));
                        }else{
                            Assertions.assertNull(FieldAndMethodAccessor.IntDblLnkSeq.UncheckedSubList.parent(seq));
                        }
                        break;
                    }else{
                        if(checked){
                            Assertions.assertSame(expectedParent.seq,
                                    FieldAndMethodAccessor.IntDblLnkSeq.CheckedSubList.parent(seq));
                        }else{
                            Assertions.assertSame(expectedParent.seq,
                                    FieldAndMethodAccessor.IntDblLnkSeq.UncheckedSubList.parent(seq));
                        }
                        for(final var nextRootBound=expectedParent.expectedRootOffset + expectedParent.expectedSize
                                - 1;rootBound != nextRootBound;){
                            nextNode=tail;
                            tail=tail.next;
                            Assertions.assertSame(tail.prev,nextNode);
                            Assertions.assertEquals(expectedArr[++rootBound],tail.val);
                        }
                        curr=expectedParent;
                    }
                }
                for(final var nextRootBound=expectedRoot.expectedSize - 1;rootBound != nextRootBound;){
                    nextNode=tail;
                    tail=tail.next;
                    Assertions.assertSame(tail.prev,nextNode);
                    Assertions.assertEquals(expectedArr[++rootBound],tail.val);
                }
                Assertions.assertNull(head.prev);
                Assertions.assertNull(tail.next);
                seq=(IntDblLnkSeq)expectedRoot.seq;
                Assertions.assertSame(head,seq.head);
                Assertions.assertSame(tail,seq.tail);
                Assertions.assertEquals(expectedRoot.expectedSize,seq.size);
                if(checked){
                    Assertions.assertEquals(expectedRoot.expectedModCount,((IntDblLnkSeq.CheckedList)seq).modCount);
                }
            }
            private void verifyLongClone(Object clone){
                Assertions.assertNotSame(clone,seq);
                int size;
                Assertions.assertEquals(size=seq.size,((AbstractOmniCollection<?>)clone).size);
                final var checked=expectedRoot.checkedType.checked;
                final var cloneCast=(LongDblLnkSeq)clone;
                Assertions.assertEquals(checked,cloneCast instanceof LongDblLnkSeq.CheckedList);
                if(checked){
                    Assertions.assertEquals(0,((LongDblLnkSeq.CheckedList)cloneCast).modCount);
                }else{
                    Assertions.assertTrue(cloneCast instanceof LongDblLnkSeq.UncheckedList);
                }
                var cloneHead=cloneCast.head;
                final var cloneTail=cloneCast.tail;
                if(size == 0){
                    Assertions.assertNull(cloneHead);
                    Assertions.assertNull(cloneTail);
                }else{
                    final var thisCast=(LongDblLnkSeq)seq;
                    var thisHead=thisCast.head;
                    final var thisTail=thisCast.tail;
                    var clonePrev=cloneHead.prev;
                    Assertions.assertNull(clonePrev);
                    for(int i=0;i < size;++i){
                        Assertions.assertNotSame(cloneHead,thisHead);
                        Assertions.assertEquals(cloneHead.val,thisHead.val);
                        Assertions.assertSame(clonePrev,cloneHead.prev);
                        clonePrev=cloneHead;
                        cloneHead=cloneHead.next;
                        thisHead=thisHead.next;
                    }
                    Assertions.assertSame(cloneTail,clonePrev);
                    Assertions.assertNotSame(cloneTail,thisTail);
                    Assertions.assertNull(cloneHead);
                }
            }
            private void verifyLongIntegrity(){
                var seq=(LongDblLnkSeq)this.seq;
                final var root=expectedRoot.seq;
                final var checked=expectedRoot.checkedType.checked;
                var curr=this;
                while(curr.expectedSize == 0){
                    Assertions.assertNull(seq.head);
                    Assertions.assertNull(seq.tail);
                    if(checked){
                        Assertions.assertSame(root,FieldAndMethodAccessor.LongDblLnkSeq.CheckedSubList.root(seq));
                        Assertions.assertEquals(curr.expectedParentOffset,
                                FieldAndMethodAccessor.LongDblLnkSeq.CheckedSubList.parentOffset(seq));
                        Assertions.assertEquals(curr.expectedModCount,
                                FieldAndMethodAccessor.LongDblLnkSeq.CheckedSubList.modCount(seq));
                    }else{
                        Assertions.assertSame(root,FieldAndMethodAccessor.LongDblLnkSeq.UncheckedSubList.root(seq));
                        Assertions.assertEquals(curr.expectedParentOffset,
                                FieldAndMethodAccessor.LongDblLnkSeq.UncheckedSubList.parentOffset(seq));
                    }
                    final var expectedParent=curr.expectedParent;
                    if(expectedParent == null){
                        expectedRoot.verifyLongIntegrity();
                        return;
                    }else{
                        curr=expectedParent;
                        seq=(LongDblLnkSeq)curr.seq;
                    }
                }
                var head=seq.head;
                var tail=seq.tail;
                var currNode=head;
                var nextNode=currNode.next;
                var rootOffset=curr.expectedRootOffset;
                var rootBound=rootOffset + curr.expectedSize - 1;
                final var expectedArr=(long[])expectedRoot.expectedArr;
                for(int i=rootOffset;;++i){
                    Assertions.assertEquals(expectedArr[i],currNode.val);
                    if(i >= rootBound){
                        Assertions.assertSame(currNode,tail);
                        break;
                    }
                    Assertions.assertSame(nextNode.prev,currNode);
                    currNode=nextNode;
                    nextNode=currNode.next;
                }
                for(;;){
                    seq=(LongDblLnkSeq)curr.seq;
                    Assertions.assertSame(curr.expectedHead,head);
                    Assertions.assertSame(curr.expectedTail,tail);
                    Assertions.assertEquals(curr.expectedSize,seq.size);
                    if(checked){
                        Assertions.assertSame(root,FieldAndMethodAccessor.LongDblLnkSeq.CheckedSubList.root(seq));
                        Assertions.assertEquals(curr.expectedParentOffset,
                                FieldAndMethodAccessor.LongDblLnkSeq.CheckedSubList.parentOffset(seq));
                        Assertions.assertEquals(curr.expectedModCount,
                                FieldAndMethodAccessor.LongDblLnkSeq.CheckedSubList.modCount(seq));
                    }else{
                        Assertions.assertSame(root,FieldAndMethodAccessor.LongDblLnkSeq.UncheckedSubList.root(seq));
                        Assertions.assertEquals(curr.expectedParentOffset,
                                FieldAndMethodAccessor.LongDblLnkSeq.UncheckedSubList.parentOffset(seq));
                    }
                    for(final int nextRootOffset=rootOffset - curr.expectedParentOffset;rootOffset != nextRootOffset;){
                        nextNode=head;
                        head=head.prev;
                        Assertions.assertSame(head.next,nextNode);
                        Assertions.assertEquals(expectedArr[--rootOffset],head.val);
                    }
                    final var expectedParent=curr.expectedParent;
                    if(expectedParent == null){
                        if(checked){
                            Assertions.assertNull(FieldAndMethodAccessor.LongDblLnkSeq.CheckedSubList.parent(seq));
                        }else{
                            Assertions.assertNull(FieldAndMethodAccessor.LongDblLnkSeq.UncheckedSubList.parent(seq));
                        }
                        break;
                    }else{
                        if(checked){
                            Assertions.assertSame(expectedParent.seq,
                                    FieldAndMethodAccessor.LongDblLnkSeq.CheckedSubList.parent(seq));
                        }else{
                            Assertions.assertSame(expectedParent.seq,
                                    FieldAndMethodAccessor.LongDblLnkSeq.UncheckedSubList.parent(seq));
                        }
                        for(final var nextRootBound=expectedParent.expectedRootOffset + expectedParent.expectedSize
                                - 1;rootBound != nextRootBound;){
                            nextNode=tail;
                            tail=tail.next;
                            Assertions.assertSame(tail.prev,nextNode);
                            Assertions.assertEquals(expectedArr[++rootBound],tail.val);
                        }
                        curr=expectedParent;
                    }
                }
                for(final var nextRootBound=expectedRoot.expectedSize - 1;rootBound != nextRootBound;){
                    nextNode=tail;
                    tail=tail.next;
                    Assertions.assertSame(tail.prev,nextNode);
                    Assertions.assertEquals(expectedArr[++rootBound],tail.val);
                }
                Assertions.assertNull(head.prev);
                Assertions.assertNull(tail.next);
                seq=(LongDblLnkSeq)expectedRoot.seq;
                Assertions.assertSame(head,seq.head);
                Assertions.assertSame(tail,seq.tail);
                Assertions.assertEquals(expectedRoot.expectedSize,seq.size);
                if(checked){
                    Assertions.assertEquals(expectedRoot.expectedModCount,((LongDblLnkSeq.CheckedList)seq).modCount);
                }
            }
            private void verifyRefClone(Object clone,boolean refIsSame){
                Assertions.assertNotSame(clone,seq);
                int size;
                Assertions.assertEquals(size=seq.size,((AbstractOmniCollection<?>)clone).size);
                final var checked=expectedRoot.checkedType.checked;
                @SuppressWarnings("unchecked")
                final var cloneCast=(RefDblLnkSeq<E>)clone;
                Assertions.assertEquals(checked,cloneCast instanceof RefDblLnkSeq.CheckedList);
                if(checked){
                    Assertions.assertEquals(0,((RefDblLnkSeq.CheckedList<E>)cloneCast).modCount);
                }else{
                    Assertions.assertTrue(cloneCast instanceof RefDblLnkSeq.UncheckedList);
                }
                var cloneHead=cloneCast.head;
                final var cloneTail=cloneCast.tail;
                if(size == 0){
                    Assertions.assertNull(cloneHead);
                    Assertions.assertNull(cloneTail);
                }else{
                    @SuppressWarnings("unchecked")
                    final var thisCast=(RefDblLnkSeq<E>)seq;
                    var thisHead=thisCast.head;
                    final var thisTail=thisCast.tail;
                    var clonePrev=cloneHead.prev;
                    Assertions.assertNull(clonePrev);
                    if(refIsSame){
                        for(int i=0;i < size;++i){
                            Assertions.assertNotSame(cloneHead,thisHead);
                            Assertions.assertSame(cloneHead.val,thisHead.val);
                            Assertions.assertSame(clonePrev,cloneHead.prev);
                            clonePrev=cloneHead;
                            cloneHead=cloneHead.next;
                            thisHead=thisHead.next;
                        }
                    }else{
                        for(int i=0;i < size;++i){
                            Assertions.assertNotSame(cloneHead,thisHead);
                            Assertions.assertEquals(cloneHead.val,thisHead.val);
                            Assertions.assertSame(clonePrev,cloneHead.prev);
                            clonePrev=cloneHead;
                            cloneHead=cloneHead.next;
                            thisHead=thisHead.next;
                        }
                    }
                    Assertions.assertSame(cloneTail,clonePrev);
                    Assertions.assertNotSame(cloneTail,thisTail);
                    Assertions.assertNull(cloneHead);
                }
            }
            @SuppressWarnings("unchecked")
            private void verifyRefIntegrity(boolean refIsSame){
                var seq=(RefDblLnkSeq<E>)this.seq;
                final var root=expectedRoot.seq;
                final var checked=expectedRoot.checkedType.checked;
                var curr=this;
                while(curr.expectedSize == 0){
                    Assertions.assertNull(seq.head);
                    Assertions.assertNull(seq.tail);
                    if(checked){
                        Assertions.assertSame(root,FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.root(seq));
                        Assertions.assertEquals(curr.expectedParentOffset,
                                FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.parentOffset(seq));
                        Assertions.assertEquals(curr.expectedModCount,
                                FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.modCount(seq));
                    }else{
                        Assertions.assertSame(root,FieldAndMethodAccessor.RefDblLnkSeq.UncheckedSubList.root(seq));
                        Assertions.assertEquals(curr.expectedParentOffset,
                                FieldAndMethodAccessor.RefDblLnkSeq.UncheckedSubList.parentOffset(seq));
                    }
                    final var expectedParent=curr.expectedParent;
                    if(expectedParent == null){
                        expectedRoot.verifyRefIntegrity(refIsSame);
                        return;
                    }else{
                        curr=expectedParent;
                        seq=(RefDblLnkSeq<E>)curr.seq;
                    }
                }
                var head=seq.head;
                var tail=seq.tail;
                var currNode=head;
                var nextNode=currNode.next;
                var rootOffset=curr.expectedRootOffset;
                var rootBound=rootOffset + curr.expectedSize - 1;
                final var expectedArr=(Object[])expectedRoot.expectedArr;
                if(refIsSame){
                    for(int i=rootOffset;;++i){
                        Assertions.assertSame(expectedArr[i],currNode.val);
                        if(i >= rootBound){
                            Assertions.assertSame(currNode,tail);
                            break;
                        }
                        Assertions.assertSame(nextNode.prev,currNode);
                        currNode=nextNode;
                        nextNode=currNode.next;
                    }
                }else{
                    for(int i=rootOffset;;++i){
                        Assertions.assertEquals(expectedArr[i],currNode.val);
                        if(i == rootBound){
                            Assertions.assertSame(currNode,tail);
                            break;
                        }
                        Assertions.assertSame(nextNode.prev,currNode);
                        currNode=nextNode;
                        nextNode=currNode.next;
                    }
                }
                for(;;){
                    seq=(RefDblLnkSeq<E>)curr.seq;
                    Assertions.assertSame(curr.expectedHead,head);
                    Assertions.assertSame(curr.expectedTail,tail);
                    Assertions.assertEquals(curr.expectedSize,seq.size);
                    if(checked){
                        Assertions.assertSame(root,FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.root(seq));
                        Assertions.assertEquals(curr.expectedParentOffset,
                                FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.parentOffset(seq));
                        Assertions.assertEquals(curr.expectedModCount,
                                FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.modCount(seq));
                    }else{
                        Assertions.assertSame(root,FieldAndMethodAccessor.RefDblLnkSeq.UncheckedSubList.root(seq));
                        Assertions.assertEquals(curr.expectedParentOffset,
                                FieldAndMethodAccessor.RefDblLnkSeq.UncheckedSubList.parentOffset(seq));
                    }
                    if(refIsSame){
                        for(final int nextRootOffset=rootOffset
                                - curr.expectedParentOffset;rootOffset != nextRootOffset;){
                            nextNode=head;
                            head=head.prev;
                            Assertions.assertSame(head.next,nextNode);
                            Assertions.assertSame(expectedArr[--rootOffset],head.val);
                        }
                    }else{
                        for(final int nextRootOffset=rootOffset
                                - curr.expectedParentOffset;rootOffset != nextRootOffset;){
                            nextNode=head;
                            head=head.prev;
                            Assertions.assertSame(head.next,nextNode);
                            Assertions.assertEquals(expectedArr[--rootOffset],head.val);
                        }
                    }
                    final var expectedParent=curr.expectedParent;
                    if(expectedParent == null){
                        if(checked){
                            Assertions.assertNull(FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.parent(seq));
                        }else{
                            Assertions.assertNull(FieldAndMethodAccessor.RefDblLnkSeq.UncheckedSubList.parent(seq));
                        }
                        break;
                    }else{
                        if(checked){
                            Assertions.assertSame(expectedParent.seq,
                                    FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.parent(seq));
                        }else{
                            Assertions.assertSame(expectedParent.seq,
                                    FieldAndMethodAccessor.RefDblLnkSeq.UncheckedSubList.parent(seq));
                        }
                        if(refIsSame){
                            for(final var nextRootBound=expectedParent.expectedRootOffset + expectedParent.expectedSize
                                    - 1;rootBound != nextRootBound;){
                                nextNode=tail;
                                tail=tail.next;
                                Assertions.assertSame(tail.prev,nextNode);
                                Assertions.assertSame(expectedArr[++rootBound],tail.val);
                            }
                        }else{
                            for(final var nextRootBound=expectedParent.expectedRootOffset + expectedParent.expectedSize
                                    - 1;rootBound != nextRootBound;){
                                nextNode=tail;
                                tail=tail.next;
                                Assertions.assertSame(tail.prev,nextNode);
                                Assertions.assertEquals(expectedArr[++rootBound],tail.val);
                            }
                        }
                        curr=expectedParent;
                    }
                }
                for(final var nextRootBound=expectedRoot.expectedSize - 1;rootBound != nextRootBound;){
                    nextNode=tail;
                    tail=tail.next;
                    Assertions.assertSame(tail.prev,nextNode);
                    Assertions.assertEquals(expectedArr[++rootBound],tail.val);
                }
                Assertions.assertNull(head.prev);
                Assertions.assertNull(tail.next);
                seq=(RefDblLnkSeq<E>)expectedRoot.seq;
                Assertions.assertSame(head,seq.head);
                Assertions.assertSame(tail,seq.tail);
                Assertions.assertEquals(expectedRoot.expectedSize,seq.size);
                if(checked){
                    Assertions.assertEquals(expectedRoot.expectedModCount,((RefDblLnkSeq.CheckedList<E>)seq).modCount);
                }
            }
            private void verifyShortClone(Object clone){
                Assertions.assertNotSame(clone,seq);
                int size;
                Assertions.assertEquals(size=seq.size,((AbstractOmniCollection<?>)clone).size);
                final var checked=expectedRoot.checkedType.checked;
                final var cloneCast=(ShortDblLnkSeq)clone;
                Assertions.assertEquals(checked,cloneCast instanceof ShortDblLnkSeq.CheckedList);
                if(checked){
                    Assertions.assertEquals(0,((ShortDblLnkSeq.CheckedList)cloneCast).modCount);
                }else{
                    Assertions.assertTrue(cloneCast instanceof ShortDblLnkSeq.UncheckedList);
                }
                var cloneHead=cloneCast.head;
                final var cloneTail=cloneCast.tail;
                if(size == 0){
                    Assertions.assertNull(cloneHead);
                    Assertions.assertNull(cloneTail);
                }else{
                    final var thisCast=(ShortDblLnkSeq)seq;
                    var thisHead=thisCast.head;
                    final var thisTail=thisCast.tail;
                    var clonePrev=cloneHead.prev;
                    Assertions.assertNull(clonePrev);
                    for(int i=0;i < size;++i){
                        Assertions.assertNotSame(cloneHead,thisHead);
                        Assertions.assertEquals(cloneHead.val,thisHead.val);
                        Assertions.assertSame(clonePrev,cloneHead.prev);
                        clonePrev=cloneHead;
                        cloneHead=cloneHead.next;
                        thisHead=thisHead.next;
                    }
                    Assertions.assertSame(cloneTail,clonePrev);
                    Assertions.assertNotSame(cloneTail,thisTail);
                    Assertions.assertNull(cloneHead);
                }
            }
            private void verifyShortIntegrity(){
                var seq=(ShortDblLnkSeq)this.seq;
                final var root=expectedRoot.seq;
                final var checked=expectedRoot.checkedType.checked;
                var curr=this;
                while(curr.expectedSize == 0){
                    Assertions.assertNull(seq.head);
                    Assertions.assertNull(seq.tail);
                    if(checked){
                        Assertions.assertSame(root,FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList.root(seq));
                        Assertions.assertEquals(curr.expectedParentOffset,
                                FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList.parentOffset(seq));
                        Assertions.assertEquals(curr.expectedModCount,
                                FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList.modCount(seq));
                    }else{
                        Assertions.assertSame(root,FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedSubList.root(seq));
                        Assertions.assertEquals(curr.expectedParentOffset,
                                FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedSubList.parentOffset(seq));
                    }
                    final var expectedParent=curr.expectedParent;
                    if(expectedParent == null){
                        expectedRoot.verifyShortIntegrity();
                        return;
                    }else{
                        curr=expectedParent;
                        seq=(ShortDblLnkSeq)curr.seq;
                    }
                }
                var head=seq.head;
                var tail=seq.tail;
                var currNode=head;
                var nextNode=currNode.next;
                var rootOffset=curr.expectedRootOffset;
                var rootBound=rootOffset + curr.expectedSize - 1;
                final var expectedArr=(short[])expectedRoot.expectedArr;
                for(int i=rootOffset;;++i){
                    Assertions.assertEquals(expectedArr[i],currNode.val);
                    if(i >= rootBound){
                        Assertions.assertSame(currNode,tail);
                        break;
                    }
                    Assertions.assertSame(nextNode.prev,currNode);
                    currNode=nextNode;
                    nextNode=currNode.next;
                }
                for(;;){
                    seq=(ShortDblLnkSeq)curr.seq;
                    Assertions.assertSame(curr.expectedHead,head);
                    Assertions.assertSame(curr.expectedTail,tail);
                    Assertions.assertEquals(curr.expectedSize,seq.size);
                    if(checked){
                        Assertions.assertSame(root,FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList.root(seq));
                        Assertions.assertEquals(curr.expectedParentOffset,
                                FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList.parentOffset(seq));
                        Assertions.assertEquals(curr.expectedModCount,
                                FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList.modCount(seq));
                    }else{
                        Assertions.assertSame(root,FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedSubList.root(seq));
                        Assertions.assertEquals(curr.expectedParentOffset,
                                FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedSubList.parentOffset(seq));
                    }
                    for(final int nextRootOffset=rootOffset - curr.expectedParentOffset;rootOffset != nextRootOffset;){
                        nextNode=head;
                        head=head.prev;
                        Assertions.assertSame(head.next,nextNode);
                        Assertions.assertEquals(expectedArr[--rootOffset],head.val);
                    }
                    final var expectedParent=curr.expectedParent;
                    if(expectedParent == null){
                        if(checked){
                            Assertions.assertNull(FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList.parent(seq));
                        }else{
                            Assertions.assertNull(FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedSubList.parent(seq));
                        }
                        break;
                    }else{
                        if(checked){
                            Assertions.assertSame(expectedParent.seq,
                                    FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList.parent(seq));
                        }else{
                            Assertions.assertSame(expectedParent.seq,
                                    FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedSubList.parent(seq));
                        }
                        for(final var nextRootBound=expectedParent.expectedRootOffset + expectedParent.expectedSize
                                - 1;rootBound != nextRootBound;){
                            nextNode=tail;
                            tail=tail.next;
                            Assertions.assertSame(tail.prev,nextNode);
                            Assertions.assertEquals(expectedArr[++rootBound],tail.val);
                        }
                        curr=expectedParent;
                    }
                }
                for(final var nextRootBound=expectedRoot.expectedSize - 1;rootBound != nextRootBound;){
                    nextNode=tail;
                    tail=tail.next;
                    Assertions.assertSame(tail.prev,nextNode);
                    Assertions.assertEquals(expectedArr[++rootBound],tail.val);
                }
                Assertions.assertNull(head.prev);
                Assertions.assertNull(tail.next);
                seq=(ShortDblLnkSeq)expectedRoot.seq;
                Assertions.assertSame(head,seq.head);
                Assertions.assertSame(tail,seq.tail);
                Assertions.assertEquals(expectedRoot.expectedSize,seq.size);
                if(checked){
                    Assertions.assertEquals(expectedRoot.expectedModCount,((ShortDblLnkSeq.CheckedList)seq).modCount);
                }
            }
            private class ItrMonitor<ITR extends OmniIterator<E>> implements MonitoredIterator<ITR,SUBLIST>{
                final ITR itr;
                Object expectedCurr;
                Object expectedLastRet;
                int expectedCurrIndex;
                int expectedItrModCount;
                int lastRetIndex;
                ItrMonitor(ITR itr,Object expectedCurr,int expectedCurrIndex){
                    this.itr=itr;
                    this.expectedCurr=expectedCurr;
                    this.expectedCurrIndex=expectedCurrIndex;
                    this.expectedItrModCount=expectedModCount;
                    this.lastRetIndex=-1;
                }
                @Override
                public ITR getIterator(){
                    return itr;
                }
                @Override
                public IteratorType getIteratorType(){
                    return IteratorType.SubAscendingItr;
                }
                @Override
                public MonitoredCollection<SUBLIST> getMonitoredCollection(){
                    return SubListMonitor.this;
                }
                @Override
                public int getNumLeft(){
                    return expectedSize - expectedCurrIndex;
                }
                @Override
                public boolean hasNext(){
                    return this.expectedCurrIndex < expectedSize;
                }
                @Override
                public boolean nextWasJustCalled(){
                    return this.lastRetIndex != -1 && lastRetIndex < this.expectedCurrIndex;
                }
                @Override
                public void updateItrNextState(){
                    this.expectedLastRet=expectedCurr;
                    this.lastRetIndex=this.expectedCurrIndex++;
                    if(expectedRoot.checkedType.checked){
                        this.expectedCurr=expectedRoot.next(this.expectedCurr);
                    }else{
                        if(expectedCurr == expectedTail){
                            expectedCurr=null;
                        }else{
                            expectedCurr=expectedRoot.next(expectedCurr);
                        }
                    }
                }
                @Override
                public void updateItrRemoveState(){
                    updateRemoveIndexState(lastRetIndex);
                    if(lastRetIndex < expectedCurrIndex){
                        --expectedCurrIndex;
                    }else{
                        this.expectedCurr=expectedRoot.next(expectedLastRet);
                    }
                    ++expectedItrModCount;
                    this.expectedLastRet=null;
                    this.lastRetIndex=-1;
                }
                @Override
                public void verifyCloneHelper(Object clone){
                    final var dataType=expectedRoot.dataType;
                    switch(dataType){
                    case BOOLEAN:{
                        verifyBooleanClone();
                        break;
                    }
                    case BYTE:{
                        verifyByteClone();
                        break;
                    }
                    case CHAR:{
                        verifyCharClone();
                        break;
                    }
                    case DOUBLE:{
                        verifyDoubleClone();
                        break;
                    }
                    case FLOAT:{
                        verifyFloatClone();
                        break;
                    }
                    case INT:{
                        verifyIntClone();
                        break;
                    }
                    case LONG:{
                        verifyLongClone();
                        break;
                    }
                    case REF:{
                        verifyRefClone();
                        break;
                    }
                    case SHORT:{
                        verifyShortClone();
                        break;
                    }
                    default:
                        throw dataType.invalid();
                    }
                }
                @Override
                public void verifyForEachRemaining(MonitoredFunction function){
                    int expectedCurrIndex=this.expectedCurrIndex;
                    final int expectedBound=expectedSize;
                    if(expectedCurrIndex < expectedBound){
                        final var dataType=expectedRoot.dataType;
                        final var functionItr=function.iterator();
                        IntConsumer verifier;
                        switch(dataType){
                        case BOOLEAN:{
                            final var expectedArr=(boolean[])expectedRoot.expectedArr;
                            verifier=index->Assertions.assertEquals(expectedArr[index + expectedRootOffset],
                                    (boolean)functionItr.next());
                            break;
                        }
                        case BYTE:{
                            final var expectedArr=(byte[])expectedRoot.expectedArr;
                            verifier=index->Assertions.assertEquals(expectedArr[index + expectedRootOffset],
                                    (byte)functionItr.next());
                            break;
                        }
                        case CHAR:{
                            final var expectedArr=(char[])expectedRoot.expectedArr;
                            verifier=index->Assertions.assertEquals(expectedArr[index + expectedRootOffset],
                                    (char)functionItr.next());
                            break;
                        }
                        case DOUBLE:{
                            final var expectedArr=(double[])expectedRoot.expectedArr;
                            verifier=index->Assertions.assertEquals(expectedArr[index + expectedRootOffset],
                                    (double)functionItr.next());
                            break;
                        }
                        case FLOAT:{
                            final var expectedArr=(float[])expectedRoot.expectedArr;
                            verifier=index->Assertions.assertEquals(expectedArr[index + expectedRootOffset],
                                    (float)functionItr.next());
                            break;
                        }
                        case INT:{
                            final var expectedArr=(int[])expectedRoot.expectedArr;
                            verifier=index->Assertions.assertEquals(expectedArr[index + expectedRootOffset],
                                    (int)functionItr.next());
                            break;
                        }
                        case LONG:{
                            final var expectedArr=(long[])expectedRoot.expectedArr;
                            verifier=index->Assertions.assertEquals(expectedArr[index + expectedRootOffset],
                                    (long)functionItr.next());
                            break;
                        }
                        case REF:{
                            final var expectedArr=(Object[])expectedRoot.expectedArr;
                            verifier=index->Assertions.assertSame(expectedArr[index + expectedRootOffset],
                                    functionItr.next());
                            break;
                        }
                        case SHORT:{
                            final var expectedArr=(short[])expectedRoot.expectedArr;
                            verifier=index->Assertions.assertEquals(expectedArr[index + expectedRootOffset],
                                    (short)functionItr.next());
                            break;
                        }
                        default:
                            throw dataType.invalid();
                        }
                        do{
                            verifier.accept(expectedCurrIndex);
                        }while(++expectedCurrIndex < expectedBound);
                        this.expectedCurr=null;
                        this.expectedLastRet=expectedTail;
                        this.expectedCurrIndex=expectedSize;
                        this.lastRetIndex=expectedSize - 1;
                    }
                }
                @Override
                public void verifyNextResult(DataType outputType,Object result){
                    expectedRoot.verifyGetResult(expectedRootOffset + expectedCurrIndex,result,outputType);
                }
                void verifyBooleanClone(){
                    if(expectedRoot.checkedType.checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.AscendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.AscendingItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.AscendingItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.AscendingItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.AscendingItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedSubList.AscendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedSubList.AscendingItr.curr(itr));
                    }
                }
                void verifyByteClone(){
                    if(expectedRoot.checkedType.checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.ByteDblLnkSeq.CheckedSubList.AscendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.ByteDblLnkSeq.CheckedSubList.AscendingItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.ByteDblLnkSeq.CheckedSubList.AscendingItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.ByteDblLnkSeq.CheckedSubList.AscendingItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.ByteDblLnkSeq.CheckedSubList.AscendingItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedSubList.AscendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedSubList.AscendingItr.curr(itr));
                    }
                }
                void verifyCharClone(){
                    if(expectedRoot.checkedType.checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.AscendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.AscendingItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.AscendingItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.AscendingItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.AscendingItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.CharDblLnkSeq.UncheckedSubList.AscendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.CharDblLnkSeq.UncheckedSubList.AscendingItr.curr(itr));
                    }
                }
                void verifyDoubleClone(){
                    if(expectedRoot.checkedType.checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedSubList.AscendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedSubList.AscendingItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedSubList.AscendingItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedSubList.AscendingItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedSubList.AscendingItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.UncheckedSubList.AscendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.UncheckedSubList.AscendingItr.curr(itr));
                    }
                }
                void verifyFloatClone(){
                    if(expectedRoot.checkedType.checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.AscendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.AscendingItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.AscendingItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.AscendingItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.AscendingItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedSubList.AscendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedSubList.AscendingItr.curr(itr));
                    }
                }
                void verifyIntClone(){
                    if(expectedRoot.checkedType.checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.IntDblLnkSeq.CheckedSubList.AscendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.IntDblLnkSeq.CheckedSubList.AscendingItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.IntDblLnkSeq.CheckedSubList.AscendingItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.IntDblLnkSeq.CheckedSubList.AscendingItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.IntDblLnkSeq.CheckedSubList.AscendingItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.IntDblLnkSeq.UncheckedSubList.AscendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.IntDblLnkSeq.UncheckedSubList.AscendingItr.curr(itr));
                    }
                }
                void verifyLongClone(){
                    if(expectedRoot.checkedType.checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.LongDblLnkSeq.CheckedSubList.AscendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.LongDblLnkSeq.CheckedSubList.AscendingItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.LongDblLnkSeq.CheckedSubList.AscendingItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.LongDblLnkSeq.CheckedSubList.AscendingItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.LongDblLnkSeq.CheckedSubList.AscendingItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.LongDblLnkSeq.UncheckedSubList.AscendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.LongDblLnkSeq.UncheckedSubList.AscendingItr.curr(itr));
                    }
                }
                void verifyRefClone(){
                    if(expectedRoot.checkedType.checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.AscendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.AscendingItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.AscendingItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.AscendingItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.AscendingItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.RefDblLnkSeq.UncheckedSubList.AscendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.RefDblLnkSeq.UncheckedSubList.AscendingItr.curr(itr));
                    }
                }
                void verifyShortClone(){
                    if(expectedRoot.checkedType.checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList.AscendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList.AscendingItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList.AscendingItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList.AscendingItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList.AscendingItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedSubList.AscendingItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedSubList.AscendingItr.curr(itr));
                    }
                }
            }
            private class ListItrMonitor extends ItrMonitor<OmniListIterator<E>>
                    implements
                    MonitoredListIterator<OmniListIterator<E>,SUBLIST>{
                ListItrMonitor(OmniListIterator<E> itr,Object expectedCurr,int expectedCurrIndex){
                    super(itr,expectedCurr,expectedCurrIndex);
                }
                @Override
                public IteratorType getIteratorType(){
                    return IteratorType.SubBidirectionalItr;
                }
                @Override
                public boolean hasPrevious(){
                    return expectedCurrIndex > 0;
                }
                @Override
                public int nextIndex(){
                    return expectedCurrIndex;
                }
                @Override
                public int previousIndex(){
                    return expectedCurrIndex - 1;
                }
                @Override
                public boolean previousWasJustCalled(){
                    return lastRetIndex == expectedCurrIndex;
                }
                @Override
                public void updateItrAddState(Object input,DataType inputType){
                    updateAddState(expectedCurrIndex++,input,inputType);
                    ++expectedItrModCount;
                    lastRetIndex=-1;
                    expectedLastRet=null;
                }
                @Override
                public void updateItrNextState(){
                    lastRetIndex=expectedCurrIndex++;
                    expectedCurr=expectedRoot.next(expectedLastRet=expectedCurr);
                }
                @Override
                public void updateItrPreviousState(){
                    if(expectedCurr == null){
                        expectedLastRet=expectedTail;
                    }else{
                        expectedLastRet=expectedRoot.prev(expectedCurr);
                    }
                    expectedCurr=expectedLastRet;
                    lastRetIndex=--expectedCurrIndex;
                }
                @Override
                public void updateItrSetState(Object input,DataType inputType){
                    expectedRoot.verifyPutResult(expectedRootOffset + lastRetIndex,input,inputType);
                }
                @Override
                public void verifyPreviousResult(DataType outputType,Object result){
                    expectedRoot.verifyGetResult(expectedRootOffset + lastRetIndex,result,outputType);
                }
                @Override
                void verifyBooleanClone(){
                    if(expectedRoot.checkedType.checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.BidirectionalItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.BidirectionalItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.BidirectionalItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.BidirectionalItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.BidirectionalItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedSubList.BidirectionalItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedSubList.BidirectionalItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedSubList.BidirectionalItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedSubList.BidirectionalItr
                                        .currIndex(itr));
                    }
                }
                @Override
                void verifyByteClone(){
                    if(expectedRoot.checkedType.checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.ByteDblLnkSeq.CheckedSubList.BidirectionalItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.ByteDblLnkSeq.CheckedSubList.BidirectionalItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.ByteDblLnkSeq.CheckedSubList.BidirectionalItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.ByteDblLnkSeq.CheckedSubList.BidirectionalItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.ByteDblLnkSeq.CheckedSubList.BidirectionalItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedSubList.BidirectionalItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedSubList.BidirectionalItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedSubList.BidirectionalItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedSubList.BidirectionalItr.currIndex(itr));
                    }
                }
                @Override
                void verifyCharClone(){
                    if(expectedRoot.checkedType.checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.BidirectionalItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.BidirectionalItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.BidirectionalItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.BidirectionalItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.BidirectionalItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.CharDblLnkSeq.UncheckedSubList.BidirectionalItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.CharDblLnkSeq.UncheckedSubList.BidirectionalItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.CharDblLnkSeq.UncheckedSubList.BidirectionalItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.CharDblLnkSeq.UncheckedSubList.BidirectionalItr.currIndex(itr));
                    }
                }
                @Override
                void verifyDoubleClone(){
                    if(expectedRoot.checkedType.checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedSubList.BidirectionalItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedSubList.BidirectionalItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedSubList.BidirectionalItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedSubList.BidirectionalItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedSubList.BidirectionalItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.UncheckedSubList.BidirectionalItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.UncheckedSubList.BidirectionalItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.UncheckedSubList.BidirectionalItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.DoubleDblLnkSeq.UncheckedSubList.BidirectionalItr
                                        .currIndex(itr));
                    }
                }
                @Override
                void verifyFloatClone(){
                    if(expectedRoot.checkedType.checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.BidirectionalItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.BidirectionalItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.BidirectionalItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.BidirectionalItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.BidirectionalItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedSubList.BidirectionalItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedSubList.BidirectionalItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedSubList.BidirectionalItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedSubList.BidirectionalItr.currIndex(itr));
                    }
                }
                @Override
                void verifyIntClone(){
                    if(expectedRoot.checkedType.checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.IntDblLnkSeq.CheckedSubList.BidirectionalItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.IntDblLnkSeq.CheckedSubList.BidirectionalItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.IntDblLnkSeq.CheckedSubList.BidirectionalItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.IntDblLnkSeq.CheckedSubList.BidirectionalItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.IntDblLnkSeq.CheckedSubList.BidirectionalItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.IntDblLnkSeq.UncheckedSubList.BidirectionalItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.IntDblLnkSeq.UncheckedSubList.BidirectionalItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.IntDblLnkSeq.UncheckedSubList.BidirectionalItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.IntDblLnkSeq.UncheckedSubList.BidirectionalItr.currIndex(itr));
                    }
                }
                @Override
                void verifyLongClone(){
                    if(expectedRoot.checkedType.checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.LongDblLnkSeq.CheckedSubList.BidirectionalItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.LongDblLnkSeq.CheckedSubList.BidirectionalItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.LongDblLnkSeq.CheckedSubList.BidirectionalItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.LongDblLnkSeq.CheckedSubList.BidirectionalItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.LongDblLnkSeq.CheckedSubList.BidirectionalItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.LongDblLnkSeq.UncheckedSubList.BidirectionalItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.LongDblLnkSeq.UncheckedSubList.BidirectionalItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.LongDblLnkSeq.UncheckedSubList.BidirectionalItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.LongDblLnkSeq.UncheckedSubList.BidirectionalItr.currIndex(itr));
                    }
                }
                @Override
                void verifyRefClone(){
                    if(expectedRoot.checkedType.checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.BidirectionalItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.BidirectionalItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.BidirectionalItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.BidirectionalItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.BidirectionalItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.RefDblLnkSeq.UncheckedSubList.BidirectionalItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.RefDblLnkSeq.UncheckedSubList.BidirectionalItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.RefDblLnkSeq.UncheckedSubList.BidirectionalItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.RefDblLnkSeq.UncheckedSubList.BidirectionalItr.currIndex(itr));
                    }
                }
                @Override
                void verifyShortClone(){
                    if(expectedRoot.checkedType.checked){
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList.BidirectionalItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList.BidirectionalItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList.BidirectionalItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList.BidirectionalItr.currIndex(itr));
                        Assertions.assertEquals(expectedItrModCount,
                                FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList.BidirectionalItr.modCount(itr));
                    }else{
                        Assertions.assertSame(seq,
                                FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedSubList.BidirectionalItr.parent(itr));
                        Assertions.assertSame(expectedCurr,
                                FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedSubList.BidirectionalItr.curr(itr));
                        Assertions.assertSame(expectedLastRet,
                                FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedSubList.BidirectionalItr.lastRet(itr));
                        Assertions.assertEquals(expectedCurrIndex,
                                FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedSubList.BidirectionalItr.currIndex(itr));
                    }
                }
            }
        }
    }
    private static interface BasicTest{
        void runTest(MonitoredList<?> monitor,IllegalModification illegalMod);
        private void runAllTests(String testName){
            QUICK_INIT_PARAMS.forEach((collectionType,checkedTypeToInitParams)->{
                checkedTypeToInitParams.forEach((checkedType,initParamArray)->{
                    for(final var initParams:initParamArray){
                        if(initParams.totalPreAlloc <= 2 && initParams.totalPostAlloc <= 2){
                            for(final var illegalMod:initParams.structType.validPreMods){
                                if(illegalMod.minDepth <= initParams.preAllocs.length
                                        && (initParams.checkedType.checked || illegalMod.expectedException == null)){
                                    for(final var size:SIZES){
                                        TestExecutorService.submitTest(()->runTest(SequenceInitialization.Ascending
                                                .initialize(getMonitoredList(initParams,size),size,0),illegalMod));
                                    }
                                }
                            }
                        }
                    }
                });
            });
            TestExecutorService.completeAllTests(testName);
        }
    }
    private static interface DeqGetTest{
        void runTest(DblLnkSeqMonitor<?,?> monitor,DataType outputType);
        private void runAllGetFirstTests(String testName,boolean throwsOnEmpty){
            QUICK_INIT_PARAMS.forEach((collectionType,checkedTypeToInitParams)->{
                checkedTypeToInitParams.forEach((checkedType,initParamArray)->{
                    TestExecutorService.submitTest(()->{
                        final var monitor=new DblLnkSeqMonitor<>(initParamArray[0],100);
                        if(throwsOnEmpty){
                            if(checkedType.checked){
                                for(final var outputType:collectionType.validOutputTypes()){
                                    Assertions.assertThrows(NoSuchElementException.class,
                                            ()->runTest(monitor,outputType));
                                }
                            }
                        }else{
                            for(final var outputType:collectionType.validOutputTypes()){
                                runTest(monitor,outputType);
                            }
                        }
                        for(int i=0;;){
                            monitor.addFirst(i);
                            for(final var outputType:collectionType.validOutputTypes()){
                                runTest(monitor,outputType);
                            }
                            if(++i == 100){
                                break;
                            }
                        }
                    });
                });
            });
            TestExecutorService.completeAllTests(testName);
        }
        private void runAllGetLastTests(String testName,boolean throwsOnEmpty){
            QUICK_INIT_PARAMS.forEach((collectionType,checkedTypeToInitParams)->{
                checkedTypeToInitParams.forEach((checkedType,initParamArray)->{
                    TestExecutorService.submitTest(()->{
                        final var monitor=new DblLnkSeqMonitor<>(initParamArray[0],100);
                        if(throwsOnEmpty){
                            if(checkedType.checked){
                                for(final var outputType:collectionType.validOutputTypes()){
                                    Assertions.assertThrows(NoSuchElementException.class,
                                            ()->runTest(monitor,outputType));
                                }
                            }
                        }else{
                            for(final var outputType:collectionType.validOutputTypes()){
                                runTest(monitor,outputType);
                            }
                        }
                        for(int i=0;;){
                            monitor.add(i);
                            for(final var outputType:collectionType.validOutputTypes()){
                                runTest(monitor,outputType);
                            }
                            if(++i == 100){
                                break;
                            }
                        }
                    });
                });
            });
            TestExecutorService.completeAllTests(testName);
        }
    }
    private static interface DeqPopTest{
        void runTest(DblLnkSeqMonitor<?,?> monitor,DataType outputType);
        private void runAllTests(String testName,boolean throwOnEmpty){
            QUICK_INIT_PARAMS.forEach((collectionType,checkedTypeToInitParams)->{
                checkedTypeToInitParams.forEach((checkedType,initParamArray)->{
                    for(final var size:SHORT_SIZES){
                        if(size > 0 || checkedType.checked){
                            final var initParams=initParamArray[0];
                            for(final var outputType:collectionType.validOutputTypes()){
                                TestExecutorService.submitTest(()->{
                                    final var monitor=SequenceInitialization.Ascending
                                            .initialize(new DblLnkSeqMonitor<>(initParams,size),size,0);
                                    for(int i=0;i < size;++i){
                                        runTest(monitor,outputType);
                                    }
                                    if(throwOnEmpty){
                                        if(checkedType.checked){
                                            Assertions.assertThrows(NoSuchElementException.class,
                                                    ()->runTest(monitor,outputType));
                                        }
                                    }else{
                                        runTest(monitor,outputType);
                                    }
                                });
                            }
                        }
                    }
                });
            });
            TestExecutorService.completeAllTests(testName);
        }
    }
    private static interface DequeAddTest{
        void verifyMethod(DblLnkSeqMonitor<?,?> monitor,Object inputVal,DataType inputType,
                FunctionCallType functionCallType);
        private void runAllTests(String testName){
            QUICK_INIT_PARAMS.forEach((collectionType,checkedTypeToInitParams)->{
                for(final var inputType:collectionType.mayBeAddedTo()){
                    for(final var functionCallType:inputType.validFunctionCalls){
                        checkedTypeToInitParams.forEach((checkedType,initParamArray)->{
                            final var initParams=initParamArray[0];
                            TestExecutorService.submitTest(()->{
                                final var monitor=new DblLnkSeqMonitor<>(initParams,10);
                                for(int i=0;i < 10;++i){
                                    verifyMethod(monitor,inputType.convertValUnchecked(i),inputType,functionCallType);
                                }
                            });
                        });
                    }
                }
            });
            TestExecutorService.completeAllTests(testName);
        }
    }
    private static interface ListItrPositionalQueryTest{
        void runTest(SequenceInitParams initParams,int size);
        private void runAllTests(String testName){
            QUICK_INIT_PARAMS.forEach((collectionType,checkedTypeToInitParams)->{
                checkedTypeToInitParams.forEach((checkedType,initParamArray)->{
                    for(final var initParams:initParamArray){
                        if(initParams.totalPreAlloc <= 2 && initParams.totalPostAlloc <= 2){
                            for(final var size:SHORT_SIZES){
                                TestExecutorService.submitTest(()->runTest(initParams,size));
                            }
                        }
                    }
                });
            });
            TestExecutorService.completeAllTests(testName);
        }
    }
    private static interface MonitoredFunctionTest<MONITOR extends MonitoredSequence<?>>{
        void runTest(MONITOR monitor,MonitoredFunctionGen functionGen,FunctionCallType functionCallType,
                IllegalModification illegalMod,long randSeed);
        @SuppressWarnings("unchecked")
        private void runAllTests(String testName,long maxRand){
            QUICK_INIT_PARAMS.forEach((collectionType,checkedTypeToInitParams)->{
                for(final var size:MEDIUM_SIZES){
                    final int initValBound=collectionType == DataType.BOOLEAN && size != 0?1:0;
                    checkedTypeToInitParams.forEach((checkedType,initParamArray)->{
                        for(final var initParams:initParamArray){
                            if(initParams.totalPreAlloc <= 2 && initParams.totalPostAlloc <= 2){
                                for(final var functionGen:initParams.structType.validMonitoredFunctionGens){
                                    if(functionGen.expectedException == null || checkedType.checked
                                            && functionGen.minDepth <= initParams.preAllocs.length){
                                        for(final var functionCallType:collectionType.validFunctionCalls){
                                            for(final var illegalMod:initParams.structType.validPreMods){
                                                if(illegalMod.minDepth <= initParams.preAllocs.length
                                                        && (initParams.checkedType.checked
                                                                || illegalMod.expectedException == null)){
                                                    final long randSeedBound=size > 1 && functionGen.randomized
                                                            && !functionCallType.boxed
                                                            && illegalMod.expectedException == null?maxRand:0;
                                                    for(long tmpRandSeed=0;tmpRandSeed <= randSeedBound;++tmpRandSeed){
                                                        final long randSeed=tmpRandSeed;
                                                        for(int tmpInitVal=0;tmpInitVal <= initValBound;++tmpInitVal){
                                                            final int initVal=tmpInitVal;
                                                            TestExecutorService.submitTest(()->runTest(
                                                                    (MONITOR)SequenceInitialization.Ascending
                                                                            .initialize(
                                                                                    getMonitoredList(initParams,size),
                                                                                    size,initVal),
                                                                    functionGen,functionCallType,illegalMod,randSeed));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    });
                }
            });
            TestExecutorService.completeAllTests(testName);
        }
    }
    private static interface NonComparatorSortTest{
        void runTest(MonitoredList<?> monitor,int size,MonitoredComparatorGen comparatorGen);
        private void runStableTests(String testName){
            QUICK_INIT_PARAMS.forEach((collectionType,checkedTypeToInitParams)->{
                checkedTypeToInitParams.forEach((checkedType,initParamArray)->{
                    for(final var initParams:initParamArray){
                        for(final var comparatorGen:initParams.structType.validComparatorGens){
                            if(comparatorGen.validWithNoComparator
                                    && (comparatorGen.validWithPrimitive || collectionType == DataType.REF)){
                                for(final var size:SIZES){
                                    if(size < 2 || comparatorGen.expectedException == null || checkedType.checked
                                            && comparatorGen.minDepth <= initParams.preAllocs.length){
                                        for(final var illegalMod:initParams.structType.validPreMods){
                                            if(illegalMod.minDepth <= initParams.preAllocs.length
                                                    && (initParams.checkedType.checked
                                                            || illegalMod.expectedException == null)){
                                                TestExecutorService.submitTest(()->{
                                                    final var monitor=getMonitoredList(initParams,size);
                                                    if(illegalMod.expectedException == null){
                                                        if(size < 2 || comparatorGen.expectedException == null){
                                                            runTest(monitor,size,comparatorGen);
                                                        }else{
                                                            Assertions.assertThrows(comparatorGen.expectedException,
                                                                    ()->runTest(monitor,size,comparatorGen));
                                                        }
                                                    }else{
                                                        monitor.illegalMod(illegalMod);
                                                        Assertions.assertThrows(illegalMod.expectedException,
                                                                ()->runTest(monitor,size,comparatorGen));
                                                    }
                                                });
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
            });
            TestExecutorService.completeAllTests(testName);
        }
        private void runUnstableTests(String testName){
            QUICK_INIT_PARAMS.get(DataType.REF).forEach((checkedType,initParamArray)->{
                for(final var initParams:initParamArray){
                    for(final var comparatorGen:initParams.structType.validComparatorGens){
                        if(comparatorGen.validWithNoComparator){
                            for(final var size:SIZES){
                                if(size < 2 || comparatorGen.expectedException == null || checkedType.checked
                                        && comparatorGen.minDepth <= initParams.preAllocs.length){
                                    for(final var illegalMod:initParams.structType.validPreMods){
                                        if(illegalMod.minDepth <= initParams.preAllocs.length
                                                && (initParams.checkedType.checked
                                                        || illegalMod.expectedException == null)){
                                            TestExecutorService.submitTest(()->{
                                                final var monitor=getMonitoredList(initParams,size);
                                                if(illegalMod.expectedException == null){
                                                    if(size < 2 || comparatorGen.expectedException == null){
                                                        runTest(monitor,size,comparatorGen);
                                                    }else{
                                                        Assertions.assertThrows(comparatorGen.expectedException,
                                                                ()->runTest(monitor,size,comparatorGen));
                                                    }
                                                }else{
                                                    monitor.illegalMod(illegalMod);
                                                    Assertions.assertThrows(illegalMod.expectedException,
                                                            ()->runTest(monitor,size,comparatorGen));
                                                }
                                            });
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            });
            TestExecutorService.completeAllTests(testName);
        }
    }
    private static interface QueryTest<MONITOR extends MonitoredSequence<?>>{
        void callAndVerifyResult(MONITOR monitor,QueryVal queryVal,DataType inputType,QueryCastType castType,
                QueryVal.QueryValModification modification,MonitoredObjectGen monitoredObjectGen,double position,
                int seqSize);
        default boolean cmeFilter(IllegalModification illegalMod,DataType inputType,DataType collectionType,
                QueryVal queryVal,QueryVal.QueryValModification modification,QueryCastType castType){
            if(illegalMod.expectedException != null){
                switch(collectionType){
                case BOOLEAN:
                    return queryVal == QueryVal.Null && castType != QueryCastType.ToObject;
                case BYTE:
                    switch(queryVal){
                    default:
                        if(castType != QueryCastType.ToObject){
                            switch(inputType){
                            case CHAR:
                            case SHORT:
                            case INT:
                                return true;
                            default:
                            }
                        }
                        break;
                    case Null:
                        return castType != QueryCastType.ToObject;
                    case MaxByte:
                    case MinByte:
                        switch(modification){
                        case Plus1:
                            if(castType != QueryCastType.ToObject){
                                switch(inputType){
                                case CHAR:
                                case SHORT:
                                case INT:
                                    return true;
                                default:
                                }
                            }
                        default:
                        }
                    case MaxBoolean:
                    case Neg0:
                    case Pos0:
                    }
                    return false;
                case CHAR:
                    switch(queryVal){
                    default:
                        if(castType != QueryCastType.ToObject){
                            switch(inputType){
                            case BYTE:
                            case SHORT:
                            case INT:
                                return true;
                            default:
                            }
                        }
                        break;
                    case Null:
                        return castType != QueryCastType.ToObject;
                    case MaxChar:
                    case Pos0:
                        switch(modification){
                        case Plus1:
                            if(castType != QueryCastType.ToObject){
                                switch(inputType){
                                case BYTE:
                                case SHORT:
                                case INT:
                                    return true;
                                default:
                                }
                            }
                        default:
                        }
                    case MaxBoolean:
                    case Neg0:
                    case MaxByte:
                    case TwoHundred:
                    case MaxShort:
                    }
                    return false;
                case SHORT:
                    switch(queryVal){
                    default:
                        if(castType != QueryCastType.ToObject){
                            switch(inputType){
                            case CHAR:
                            case INT:
                                return true;
                            default:
                            }
                        }
                        break;
                    case Null:
                        return castType != QueryCastType.ToObject;
                    case MaxShort:
                    case MinShort:
                        switch(modification){
                        case Plus1:
                            if(castType != QueryCastType.ToObject){
                                switch(inputType){
                                case CHAR:
                                case INT:
                                    return true;
                                default:
                                }
                            }
                        default:
                        }
                    case MaxBoolean:
                    case Neg0:
                    case Pos0:
                    case MaxByte:
                    case MinByte:
                    case TwoHundred:
                    }
                case REF:
                    return false;
                case INT:
                case LONG:
                case FLOAT:
                case DOUBLE:
                    return queryVal == QueryVal.Null && castType != QueryCastType.ToObject;
                default:
                    throw collectionType.invalid();
                }
            }
            return true;
        }
        default boolean skipParams(SequenceInitParams params,int size,double position){
            return params.totalPreAlloc != 0 || params.totalPostAlloc != 0;
        }
        private void runAllTests(String testName,boolean useAllStructs){
            QUICK_INIT_PARAMS.forEach((collectionType,checkedTypeToInitParams)->{
                for(final var queryVal:QueryVal.values()){
                    if(collectionType.isValidQueryVal(queryVal)){
                        queryVal.validQueryCombos.forEach((modification,castTypesToInputTypes)->{
                            castTypesToInputTypes.forEach((castType,inputTypes)->{
                                inputTypes.forEach(inputType->{
                                    final boolean queryCanReturnTrue=queryVal.queryCanReturnTrue(modification,castType,
                                            inputType,collectionType);
                                    checkedTypeToInitParams.forEach((checkedType,initParamArray)->{
                                        for(final var initParams:initParamArray){
                                            if(queryVal == QueryVal.NonNull){
                                                if(initParams.totalPreAlloc == 0 && initParams.totalPostAlloc == 0){
                                                    for(final var monitoredObjectGen:initParams.structType.validMonitoredObjectGens){
                                                        for(final var size:MEDIUM_SIZES){
                                                            if(size == 0
                                                                    || monitoredObjectGen.minDepth <= initParams.preAllocs.length){
                                                                for(final var illegalMod:initParams.structType.validPreMods){
                                                                    if(illegalMod.minDepth <= initParams.preAllocs.length){
                                                                        if(illegalMod.expectedException == null){
                                                                            if(size != 0
                                                                                    && monitoredObjectGen.expectedException != null
                                                                                    && checkedType.checked){
                                                                                TestExecutorService.submitTest(()->{
                                                                                    Assertions.assertThrows(
                                                                                            monitoredObjectGen.expectedException,
                                                                                            ()->runTest(initParams,
                                                                                                    illegalMod,queryVal,
                                                                                                    modification,
                                                                                                    castType,inputType,
                                                                                                    monitoredObjectGen,
                                                                                                    size,
                                                                                                    size == 0?-1:0));
                                                                                });
                                                                            }
                                                                        }else if(checkedType.checked){
                                                                            TestExecutorService.submitTest(()->{
                                                                                Assertions.assertThrows(
                                                                                        illegalMod.expectedException,
                                                                                        ()->runTest(initParams,
                                                                                                illegalMod,queryVal,
                                                                                                modification,castType,
                                                                                                inputType,
                                                                                                monitoredObjectGen,size,
                                                                                                size == 0?-1:0));
                                                                            });
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }else{
                                                for(final var size:MEDIUM_SIZES){
                                                    for(final var position:POSITIONS){
                                                        if(skipParams(initParams,size,position)){
                                                            continue;
                                                        }
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
                                                                if(position == 0.5d){
                                                                    continue;
                                                                }
                                                            default:
                                                            }
                                                        }else if(initParams.totalPreAlloc != 0
                                                                || initParams.totalPostAlloc != 0){
                                                            continue;
                                                        }
                                                        for(final var illegalMod:initParams.structType.validPreMods){
                                                            if(illegalMod.expectedException == null
                                                                    || checkedType.checked
                                                                            && illegalMod.minDepth <= initParams.preAllocs.length){
                                                                TestExecutorService.submitTest(()->{
                                                                    if(cmeFilter(illegalMod,inputType,collectionType,
                                                                            queryVal,modification,castType)){
                                                                        runTest(initParams,illegalMod,queryVal,
                                                                                modification,castType,inputType,null,
                                                                                size,position);
                                                                    }else{
                                                                        Assertions.assertThrows(
                                                                                illegalMod.expectedException,
                                                                                ()->runTest(initParams,illegalMod,
                                                                                        queryVal,modification,castType,
                                                                                        inputType,null,size,position));
                                                                    }
                                                                });
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            if(!useAllStructs){
                                                break;
                                            }
                                        }
                                    });
                                });
                            });
                        });
                    }
                }
            });
            TestExecutorService.completeAllTests(testName);
        }
        @SuppressWarnings("unchecked")
        private void runTest(SequenceInitParams initParams,IllegalModification illegalMod,QueryVal queryVal,
                QueryVal.QueryValModification modification,QueryCastType castType,DataType inputType,
                MonitoredObjectGen monitoredObjectGen,int seqSize,double position){
            final var monitor=getMonitoredList(initParams,seqSize);
            if(position < 0){
                switch(initParams.collectionType){
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
                    throw initParams.collectionType.invalid();
                }
            }else{
                switch(initParams.collectionType){
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
                    throw initParams.collectionType.invalid();
                }
            }
            monitor.updateCollectionState();
            monitor.illegalMod(illegalMod);
            callAndVerifyResult((MONITOR)monitor,queryVal,inputType,castType,modification,monitoredObjectGen,position,
                    seqSize);
        }
    }
    private static interface ToStringAndHashCodeTest{
        void callRaw(OmniCollection<?> seq);
        void callVerify(MonitoredSequence<?> monitor);
        private void runAllTests(String testName){
            QUICK_INIT_PARAMS.forEach((collectionType,checkedTypeToInitParams)->{
                if(collectionType == DataType.REF){
                    checkedTypeToInitParams.forEach((checkedType,initParamArray)->{
                        for(final var initParams:initParamArray){
                            if(initParams.totalPreAlloc == 0 && initParams.totalPostAlloc == 0){
                                for(final var objGen:initParams.structType.validMonitoredObjectGens){
                                    if(objGen.expectedException == null
                                            || checkedType.checked && objGen.minDepth <= initParams.preAllocs.length){
                                        for(final var size:SIZES){
                                            for(final var illegalMod:initParams.structType.validPreMods){
                                                if(illegalMod.minDepth <= initParams.preAllocs.length
                                                        && (initParams.checkedType.checked
                                                                || illegalMod.expectedException == null)){
                                                    TestExecutorService.submitTest(()->{
                                                        if(size == 0 || objGen.expectedException == null){
                                                            final var monitor=SequenceInitialization.Ascending
                                                                    .initialize(getMonitoredList(initParams,size),size,
                                                                            0);
                                                            if(illegalMod.expectedException == null){
                                                                callVerify(monitor);
                                                            }else{
                                                                monitor.illegalMod(illegalMod);
                                                                Assertions.assertThrows(illegalMod.expectedException,
                                                                        ()->callVerify(monitor));
                                                            }
                                                        }else{
                                                            final var throwSwitch=new MonitoredObjectGen.ThrowSwitch();
                                                            final var monitor=SequenceInitialization.Ascending
                                                                    .initializeWithMonitoredObj(
                                                                            getMonitoredList(initParams,size),size,0,
                                                                            objGen,throwSwitch);
                                                            monitor.illegalMod(illegalMod);
                                                            final Class<? extends Throwable> expectedException=illegalMod.expectedException == null
                                                                    ?objGen.expectedException
                                                                    :illegalMod.expectedException;
                                                            Assertions.assertThrows(expectedException,()->{
                                                                try{
                                                                    callRaw(monitor.getCollection());
                                                                }finally{
                                                                    throwSwitch.doThrow=false;
                                                                    monitor.verifyCollectionState();
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
                    });
                }else{
                    final int initValBound=collectionType == DataType.BOOLEAN?1:0;
                    checkedTypeToInitParams.forEach((checkedType,initParamArray)->{
                        for(final var initParams:initParamArray){
                            if(initParams.totalPreAlloc == 0 && initParams.totalPostAlloc == 0){
                                for(int tmpInitVal=0;tmpInitVal <= initValBound;++tmpInitVal){
                                    final int initVal=tmpInitVal;
                                    for(final var size:SIZES){
                                        for(final var illegalMod:initParams.structType.validPreMods){
                                            if(illegalMod.minDepth <= initParams.preAllocs.length
                                                    && (initParams.checkedType.checked
                                                            || illegalMod.expectedException == null)){
                                                TestExecutorService.submitTest(()->{
                                                    final var monitor=SequenceInitialization.Ascending
                                                            .initialize(getMonitoredList(initParams,size),size,initVal);
                                                    if(illegalMod.expectedException == null){
                                                        callVerify(monitor);
                                                    }else{
                                                        monitor.illegalMod(illegalMod);
                                                        Assertions.assertThrows(illegalMod.expectedException,
                                                                ()->callVerify(monitor));
                                                    }
                                                });
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    });
                }
            });
            TestExecutorService.completeAllTests(testName);
        }
    }
}
