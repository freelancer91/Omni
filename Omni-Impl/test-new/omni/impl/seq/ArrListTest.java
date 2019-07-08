package omni.impl.seq;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.api.OmniCollection;
import omni.api.OmniIterator;
import omni.api.OmniList;
import omni.impl.CheckedType;
import omni.impl.DataType;
import omni.impl.FunctionCallType;
import omni.impl.IteratorType;
import omni.impl.MonitoredList;
import omni.impl.MonitoredObjectGen;
import omni.impl.QueryCastType;
import omni.impl.QueryVal;
import omni.impl.StructType;
import omni.util.OmniArray;
import omni.util.TestExecutorService;
public class ArrListTest{
    private static final int[] INIT_CAPACITIES=new int[]{0,5,10,15};
    private static final double[] POSITIONS=new double[]{-1,0,0.5,1.0};
    private static final double[] RANDOM_THRESHOLDS=new double[]{0.01,0.05,0.10,0.25,0.50,0.75,0.90,0.95,0.99};
    private static final double[] NON_RANDOM_THRESHOLD=new double[]{0.5};
    private static final int[] FIB_SEQ=new int[]{0,1,2,3,5,8,13,21,34,55,89,144,233,377,610,987,1597,2584,4181,6765,10946,
            17711,28657,46368,75025,121393,196418,317811,514229,832040,1346269,2178309,3524578,5702887,9227465,14930352,
            24157817,39088169,63245986,102334155,165580141,267914296,433494437,701408733,1134903170,1836311903};
    @Test
    public void testadd_intval(){
        for(var collectionType:DataType.values()){
            for(var inputType:collectionType.mayBeAddedTo()){
                for(var functionCallType:FunctionCallType.values()){
                    if(inputType != DataType.REF || functionCallType != FunctionCallType.Boxed){
                        for(var initCap:INIT_CAPACITIES){
                            for(var checkedType:CheckedType.values()){
                                for(var position:POSITIONS){
                                    if(position >= 0 || checkedType.checked){
                                        TestExecutorService.submitTest(()->{
                                            var monitor=new ArrListMonitor(checkedType,collectionType,initCap);
                                            if(position < 0){
                                                for(int i=0;i < 1000;++i){
                                                    Object inputVal=inputType.convertValUnchecked(i);
                                                    final int finalI=i;
                                                    Assertions.assertThrows(IndexOutOfBoundsException.class,()->monitor
                                                            .verifyAdd(-1,inputVal,inputType,functionCallType));
                                                    Assertions.assertThrows(IndexOutOfBoundsException.class,()->monitor
                                                            .verifyAdd(finalI + 1,inputVal,inputType,functionCallType));
                                                    monitor.add(i);
                                                }
                                            }else{
                                                for(int i=0;i < 1000;++i){
                                                    monitor.verifyAdd((int)(i * position),
                                                            inputType.convertValUnchecked(i),inputType,
                                                            functionCallType);
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
        TestExecutorService.completeAllTests("ArrListTest.testadd_intval");
    }
    @Test public void testadd_val() {
        for(var collectionType:DataType.values()) {
            for(var inputType:collectionType.mayBeAddedTo()) {
                for(var functionCallType:FunctionCallType.values()) {
                    if(inputType!=DataType.REF || functionCallType!=FunctionCallType.Boxed) {
                        for(var initCap:INIT_CAPACITIES) {
                            for(var checkedType:CheckedType.values()) {
                                TestExecutorService.submitTest(()->{
                                    var monitor=new ArrListMonitor(checkedType,collectionType,initCap);
                                    for(int i=0;i<1000;++i) {
                                        monitor.verifyAdd(inputType.convertValUnchecked(i),inputType,functionCallType);
                                    }
                                });
                            }
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("ArrListTest.testadd_val");
    }
    @Test public void testclear_void(){
        BasicTest test=(monitor)->monitor.verifyClear();
        test.runAllTests("ArrListTest.testclear_void");
    }
    @Test public void testclone_void(){
        BasicTest test=(monitor)->monitor.verifyClone();
        test.runAllTests("ArrListTest.testclone_void");
    }
    @Test public void testConstructor_int(){
        for(final var dataType:DataType.values()){
            for(final var checkedType:CheckedType.values()){
                for(final var initCap:INIT_CAPACITIES){
                    TestExecutorService.submitTest(()->{
                        final var monitor=new ArrListMonitor(checkedType,dataType,initCap);
                        switch(initCap){
                        case 0:
                            Assertions.assertNull(monitor.expectedArr);
                            break;
                        case OmniArray.DEFAULT_ARR_SEQ_CAP:
                            Object expectedArr;
                            switch(dataType){
                            case BOOLEAN:
                                expectedArr=OmniArray.OfBoolean.DEFAULT_ARR;
                                break;
                            case BYTE:
                                expectedArr=OmniArray.OfByte.DEFAULT_ARR;
                                break;
                            case CHAR:
                                expectedArr=OmniArray.OfChar.DEFAULT_ARR;
                                break;
                            case SHORT:
                                expectedArr=OmniArray.OfShort.DEFAULT_ARR;
                                break;
                            case INT:
                                expectedArr=OmniArray.OfInt.DEFAULT_ARR;
                                break;
                            case LONG:
                                expectedArr=OmniArray.OfLong.DEFAULT_ARR;
                                break;
                            case FLOAT:
                                expectedArr=OmniArray.OfFloat.DEFAULT_ARR;
                                break;
                            case DOUBLE:
                                expectedArr=OmniArray.OfDouble.DEFAULT_ARR;
                                break;
                            case REF:
                                expectedArr=OmniArray.OfRef.DEFAULT_ARR;
                                break;
                            default:
                                throw dataType.invalid();
                            }
                            Assertions.assertSame(expectedArr,monitor.expectedArr);
                            break;
                        default:
                            Assertions.assertEquals(initCap,monitor.expectedCapacity);
                        }
                        monitor.verifyCollectionState();
                    });
                }
            }
        }
        TestExecutorService.completeAllTests("ArrListTest.testConstructor_int");
    }
    @Test public void testConstructor_void(){
        for(final var dataType:DataType.values()){
            for(final var checkedType:CheckedType.values()){
                TestExecutorService.submitTest(()->new ArrListMonitor(checkedType,dataType).verifyCollectionState());
            }
        }
        TestExecutorService.completeAllTests("ArrListTest.testConstructor_void");
    }
    @Test
    public void testcontains_val(){
        QueryTest test=(monitor,queryVal,inputType,castType,modification,monitoredObjectGen,position,seqSize)->{
            if(monitoredObjectGen == null){
                Assertions.assertEquals(position >= 0,monitor.verifyContains(queryVal,inputType,castType,modification));
            }else{
                monitor.verifyThrowingContains(monitoredObjectGen);
            }
        };
        test.runAllTests("ArrListTest.testcontains_val");
    }
    @Test
    public void testforEach_Consumer(){
        for(var functionGen:StructType.ArrList.validMonitoredFunctionGens){
            for(var checkedType:CheckedType.values()){
                for(var size:FIB_SEQ){
                    if(size > 1000){
                        break;
                    }
                    if(size == 0 || checkedType.checked || functionGen.expectedException == null){
                        for(var functionCallType:FunctionCallType.values()){
                            long randSeedBound=functionGen.randomized && !functionCallType.boxed && size > 0?100:0;
                            LongStream.rangeClosed(0,randSeedBound).forEach(randSeed->{
                                for(var collectionType:DataType.values()){
                                    if(collectionType != DataType.REF || !functionCallType.boxed){
                                        TestExecutorService.submitTest(()->{
                                            var monitor=SequenceInitialization.Ascending
                                                    .initialize(new ArrListMonitor(checkedType,collectionType),size,0);
                                            if(functionGen.expectedException == null || size == 0){
                                                monitor.verifyForEach(functionGen,functionCallType,randSeed);
                                            }else{
                                                Assertions.assertThrows(functionGen.expectedException,()->monitor
                                                        .verifyForEach(functionGen,functionCallType,randSeed));
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("ArrListTest.testforEach_Consumer");
    }
    @Test
    public void testget_int(){
        for(var collectionType:DataType.values()){
            for(var checkedType:CheckedType.values()){
                for(int size:FIB_SEQ){
                    if(size > 100){
                        break;
                    }
                    TestExecutorService.submitTest(()->{
                        var monitor=SequenceInitialization.Ascending
                                .initialize(new ArrListMonitor(checkedType,collectionType),size,0);
                        for(var outputType:collectionType.validOutputTypes()){
                            if(checkedType.checked){
                                Assertions.assertThrows(IndexOutOfBoundsException.class,
                                        ()->monitor.verifyGet(-1,outputType));
                                Assertions.assertThrows(IndexOutOfBoundsException.class,
                                        ()->monitor.verifyGet(size,outputType));
                            }
                            for(int index=0;index < size;++index){
                                monitor.verifyGet(index,outputType);
                            }
                        }
                    });
                }
            }
        }
        TestExecutorService.completeAllTests("ArrListTest.testget_int");
    }
    @Test
    public void testindexOf_val(){
        QueryTest test=(monitor,queryVal,inputType,castType,modification,monitoredObjectGen,position,seqSize)->{
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
        test.runAllTests("ArrListTest.testindexOf_val");
    }
    @Test
    public void testhashCode_void(){
        ToStringAndHashCodeTest test=(size,collectionType,checkedType,initVal,objGen)->{
            if(collectionType == DataType.REF){
                var throwSwitch=new MonitoredObjectGen.ThrowSwitch();
                var monitor=SequenceInitialization.Ascending.initializeWithMonitoredObj(
                        new ArrListMonitor(checkedType,collectionType),size,initVal,objGen,throwSwitch);
                if(objGen.expectedException == null || size == 0){
                    monitor.verifyHashCode();
                }else{
                    Assertions.assertThrows(objGen.expectedException,()->{
                        try{
                            monitor.seq.hashCode();
                        }finally{
                            throwSwitch.doThrow=false;
                            monitor.verifyCollectionState();
                        }
                    });
                }
            }else{
                SequenceInitialization.Ascending.initialize(new ArrListMonitor(checkedType,collectionType),size,initVal)
                .verifyHashCode();
            }
        };
        test.runAllTests("ArrListTest.testtoString_void");
    }
    @Test public void testisEmpty_void(){
        BasicTest test=(monitor)->monitor.verifyIsEmpty();
        test.runAllTests("ArrListTest.testisEmpty_void");
    }
    @Test
    public void testiterator_void(){
        // TODO
    }
    @Test
    public void testItrclone_void(){
        // TODO
    }
    @Test
    public void testItrforEachRemaining_Consumer(){
        // TODO
    }
    @Test
    public void testItrhasNext_void(){
        // TODO
    }
    @Test
    public void testItrnext_void(){
        // TODO
    }
    @Test
    public void testItrremove_void(){
        // TODO
    }
    @Test
    public void testlastIndexOf_val(){
        QueryTest test=(monitor,queryVal,inputType,castType,modification,monitoredObjectGen,position,seqSize)->{
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
        test.runAllTests("ArrListTest.testlastIndexOf_val");
    }
    @Test
    public void testMASSIVEtoString(){
        for(var collectionType:DataType.values()){
            int seqSize;
            if((seqSize=collectionType.massiveToStringThreshold + 1) == 0){
                continue;
            }
            OmniList<?> seq;
            switch(collectionType){
            case BOOLEAN:{
                boolean[] arr;
                seq=new BooleanArrSeq.UncheckedList(seqSize,arr=new boolean[seqSize]);
                for(int i=0;i < seqSize;++i){
                    arr[i]=true;
                }
                break;
            }
            case BYTE:{
                seq=new ByteArrSeq.UncheckedList(seqSize,new byte[seqSize]);
                break;
            }
            case SHORT:{
                seq=new ShortArrSeq.UncheckedList(seqSize,new short[seqSize]);
                break;
            }
            case INT:{
                seq=new IntArrSeq.UncheckedList(seqSize,new int[seqSize]);
                break;
            }
            case LONG:{
                seq=new LongArrSeq.UncheckedList(seqSize,new long[seqSize]);
                break;
            }
            case FLOAT:{
                seq=new FloatArrSeq.UncheckedList(seqSize,new float[seqSize]);
                break;
            }
            default:
                throw collectionType.invalid();
            }
            collectionType.verifyMASSIVEToString(seq.toString(),seqSize,
                    "ArrListTest." + collectionType + ".testMASSIVEtoString");
        }
    }
    @Test
    public void testput_intval(){
        for(var collectionType:DataType.values()){
            for(var checkedType:CheckedType.values()){
                for(int size:FIB_SEQ){
                    if(size > 100){
                        break;
                    }
                    for(var inputType:collectionType.mayBeAddedTo()){
                        for(var functionCallType:FunctionCallType.values()){
                            if(inputType != DataType.REF || !functionCallType.boxed){
                                TestExecutorService.submitTest(()->{
                                    var monitor=SequenceInitialization.Ascending
                                            .initialize(new ArrListMonitor(checkedType,collectionType),size,0);
                                    if(checkedType.checked){
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
                                });
                            }
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("ArrListTest.testput_intval");
    }
    @Test
    public void testReadAndWrite(){
        // TODO
    }
    @Test
    public void testremoveIf_Predicate(){
        for(var filterGen:StructType.ArrList.validMonitoredRemoveIfPredicateGens){
            for(int size:FIB_SEQ){
                if(size > 1000){
                    break;
                }
                for(var checkedType:CheckedType.values()){
                    if(size == 0 || checkedType.checked || filterGen.expectedException == null){
                        for(var collectionType:DataType.values()){
                            int initValBound=collectionType == DataType.BOOLEAN?1:0;
                            for(var functionCallType:FunctionCallType.values()){
                                if(collectionType != DataType.REF || !functionCallType.boxed){
                                    long randSeedBound;
                                    double[] thresholdArr;
                                    if(filterGen.randomized && size > 0 && !functionCallType.boxed){
                                        randSeedBound=100;
                                        thresholdArr=RANDOM_THRESHOLDS;
                                    }else{
                                        randSeedBound=0;
                                        thresholdArr=NON_RANDOM_THRESHOLD;
                                    }
                                    for(long tmpRandSeed=0;tmpRandSeed <= randSeedBound;++tmpRandSeed){
                                        final long randSeed=tmpRandSeed;
                                        for(int tmpInitVal=0;tmpInitVal <= initValBound;++tmpInitVal){
                                            final int initVal=tmpInitVal;
                                            for(var threshold:thresholdArr){
                                                TestExecutorService.submitTest(()->{
                                                    var monitor=SequenceInitialization.Ascending.initialize(
                                                            new ArrListMonitor(checkedType,collectionType),size,
                                                            initVal);
                                                    var filter=filterGen.getMonitoredRemoveIfPredicate(monitor,
                                                            threshold,randSeed);
                                                    if(filterGen.expectedException == null || size == 0){
                                                        monitor.verifyRemoveIf(filter,functionCallType);
                                                    }else{
                                                        if(size == 377 && collectionType == DataType.BYTE
                                                                && functionCallType == FunctionCallType.Boxed
                                                                && randSeed == 0 && threshold == 0.5
                                                                && filterGen == omni.impl.MonitoredRemoveIfPredicateGen.Throw){
                                                            System.out.println("here");
                                                        }
                                                        Assertions.assertThrows(filterGen.expectedException,
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
        }
        TestExecutorService.completeAllTests("ArrListTest.testremoveIf_Predicate");
    }
    @Test
    public void testremoveVal_val(){
        QueryTest test=(monitor,queryVal,inputType,castType,modification,monitoredObjectGen,position,seqSize)->{
            if(monitoredObjectGen == null){
                Assertions.assertEquals(position >= 0,
                        monitor.verifyRemoveVal(queryVal,inputType,castType,modification));
            }else{
                monitor.verifyThrowingRemoveVal(monitoredObjectGen);
            }
        };
        test.runAllTests("ArrListTest.testremoveVal_val");
    }
    @Test
    public void testset_intval(){
        for(var collectionType:DataType.values()){
            for(var checkedType:CheckedType.values()){
                for(int size:FIB_SEQ){
                    if(size > 100){
                        break;
                    }
                    for(var functionCallType:FunctionCallType.values()){
                        if(collectionType != DataType.REF || !functionCallType.boxed){
                            TestExecutorService.submitTest(()->{
                                var monitor=SequenceInitialization.Ascending
                                        .initialize(new ArrListMonitor(checkedType,collectionType),size,0);
                                if(checkedType.checked){
                                    Assertions.assertThrows(IndexOutOfBoundsException.class,()->monitor.verifySet(-1,
                                            collectionType.convertValUnchecked(0),functionCallType));
                                    Assertions.assertThrows(IndexOutOfBoundsException.class,()->monitor.verifySet(size,
                                            collectionType.convertValUnchecked(size + 1),functionCallType));
                                }
                                for(int index=0;index < size;++index){
                                    monitor.verifySet(index,collectionType.convertValUnchecked(index + 1),
                                            functionCallType);
                                }
                            });
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("ArrListTest.testset_intval");
    }
    @Test public void testsize_void() {
        BasicTest test=(monitor)->monitor.verifySize();
        test.runAllTests("ArrListTest.testsize_void");
    }
    @Test
    public void testtoArray_IntFunction(){
        // TODO
    }
    @Test
    public void testtoArray_ObjectArray(){
        // TODO
    }
    @Test
    public void testtoArray_void(){
        for(var collectionType:DataType.values()){
            for(var checkedType:CheckedType.values()){
                for(int size:FIB_SEQ){
                    if(size > 100){
                        break;
                    }
                    TestExecutorService.submitTest(()->{
                        var monitor=SequenceInitialization.Ascending
                                .initialize(new ArrListMonitor(checkedType,collectionType),size,0);
                        for(var outputType:collectionType.validOutputTypes()){
                            outputType.verifyToArray(monitor);
                        }
                    });
                }
            }
        }
        TestExecutorService.completeAllTests("ArrListTest.testget_int");
    }
    @Test
    public void testtoString_void(){
        ToStringAndHashCodeTest test=(size,collectionType,checkedType,initVal,objGen)->{
            if(collectionType == DataType.REF && objGen.expectedException != null && size != 0){
                var throwSwitch=new MonitoredObjectGen.ThrowSwitch();
                var monitor=SequenceInitialization.Ascending.initializeWithMonitoredObj(
                        new ArrListMonitor(checkedType,collectionType),size,initVal,objGen,throwSwitch);
                Assertions.assertThrows(objGen.expectedException,()->{
                    try{
                        monitor.seq.toString();
                    }finally{
                        throwSwitch.doThrow=false;
                        monitor.verifyCollectionState();
                    }
                });
            }else{
                SequenceInitialization.Ascending.initialize(new ArrListMonitor(checkedType,collectionType),size,initVal)
                .verifyToString();
            }
        };
        test.runAllTests("ArrListTest.testtoString_void");
    }
    @org.junit.jupiter.api.AfterEach
    public void verifyAllExecuted(){
        int numTestsRemaining;
        if((numTestsRemaining=TestExecutorService.getNumRemainingTasks()) != 0){
            System.err.println("Warning: there were " + numTestsRemaining + " tests that were not completed");
        }
        TestExecutorService.reset();
    }
    private static class ArrListMonitor extends AbstractArrSeqMonitor<OmniList<?>>
    implements MonitoredList<OmniIterator<?>,OmniList<?>>{
        public ArrListMonitor(CheckedType checkedType,DataType dataType){
            super(checkedType,dataType);
        }
        public ArrListMonitor(CheckedType checkedType,DataType dataType,int initCap){
            super(checkedType,dataType,initCap);
        }
        @Override public MonitoredIterator<? extends OmniIterator<?>,OmniList<?>> getMonitoredIterator(){
            // TODO Auto-generated method stub
            return null;
        }
        @Override public MonitoredIterator<? extends OmniIterator<?>,OmniList<?>>
        getMonitoredIterator(IteratorType itrType){
            // TODO Auto-generated method stub
            return null;
        }
        @Override public StructType getStructType(){
            return StructType.ArrList;
        }
        @Override public void modCollection(){
            switch(dataType){
            case BOOLEAN:
                ++((BooleanArrSeq.CheckedList)seq).modCount;
                break;
            case BYTE:
                ++((ByteArrSeq.CheckedList)seq).modCount;
                break;
            case CHAR:
                ++((CharArrSeq.CheckedList)seq).modCount;
                break;
            case DOUBLE:
                ++((DoubleArrSeq.CheckedList)seq).modCount;
                break;
            case FLOAT:
                ++((FloatArrSeq.CheckedList)seq).modCount;
                break;
            case INT:
                ++((IntArrSeq.CheckedList)seq).modCount;
                break;
            case LONG:
                ++((LongArrSeq.CheckedList)seq).modCount;
                break;
            case REF:
                ++((RefArrSeq.CheckedList<?>)seq).modCount;
                break;
            case SHORT:
                ++((ShortArrSeq.CheckedList)seq).modCount;
                break;
            default:
                throw dataType.invalid();
            }
            ++expectedModCount;
        }
        @Override public void updateRemoveValState(Object inputVal,DataType inputType){
            final int expectedSize=this.expectedSize;
            switch(dataType){
            case BOOLEAN:{
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
                final var expectedArr=(boolean[])this.expectedArr;
                for(int i=0;;++i){
                    if(expectedArr[i] == inputCast){
                        System.arraycopy(expectedArr,i + 1,expectedArr,i,expectedSize - i - 1);
                        break;
                    }
                }
                break;
            }
            case BYTE:{
                byte inputCast;
                switch(inputType){
                case BOOLEAN:
                    inputCast=(boolean)inputVal?(byte)1:(byte)0;
                    break;
                case BYTE:
                    inputCast=(byte)inputVal;
                    break;
                case CHAR:
                    inputCast=(byte)(char)inputVal;
                    break;
                case SHORT:
                    inputCast=(byte)(short)inputVal;
                    break;
                case INT:
                    inputCast=(byte)(int)inputVal;
                    break;
                case LONG:
                    inputCast=(byte)(long)inputVal;
                    break;
                case FLOAT:
                    inputCast=(byte)(float)inputVal;
                    break;
                case DOUBLE:
                    inputCast=(byte)(double)inputVal;
                    break;
                default:
                    throw inputType.invalid();
                }
                final var expectedArr=(byte[])this.expectedArr;
                for(int i=0;;++i){
                    if(expectedArr[i] == inputCast){
                        System.arraycopy(expectedArr,i + 1,expectedArr,i,expectedSize - i - 1);
                        break;
                    }
                }
                break;
            }
            case CHAR:{
                char inputCast;
                switch(inputType){
                case BOOLEAN:
                    inputCast=(boolean)inputVal?(char)1:(char)0;
                    break;
                case BYTE:
                    inputCast=(char)(byte)inputVal;
                    break;
                case CHAR:
                    inputCast=(char)inputVal;
                    break;
                case SHORT:
                    inputCast=(char)(short)inputVal;
                    break;
                case INT:
                    inputCast=(char)(int)inputVal;
                    break;
                case LONG:
                    inputCast=(char)(long)inputVal;
                    break;
                case FLOAT:
                    inputCast=(char)(float)inputVal;
                    break;
                case DOUBLE:
                    inputCast=(char)(double)inputVal;
                    break;
                default:
                    throw inputType.invalid();
                }
                final var expectedArr=(char[])this.expectedArr;
                for(int i=0;;++i){
                    if(expectedArr[i] == inputCast){
                        System.arraycopy(expectedArr,i + 1,expectedArr,i,expectedSize - i - 1);
                        break;
                    }
                }
                break;
            }
            case SHORT:{
                short inputCast;
                switch(inputType){
                case BOOLEAN:
                    inputCast=(boolean)inputVal?(short)1:(short)0;
                    break;
                case BYTE:
                    inputCast=(byte)inputVal;
                    break;
                case CHAR:
                    inputCast=(short)(char)inputVal;
                    break;
                case SHORT:
                    inputCast=(short)inputVal;
                    break;
                case INT:
                    inputCast=(short)(int)inputVal;
                    break;
                case LONG:
                    inputCast=(short)(long)inputVal;
                    break;
                case FLOAT:
                    inputCast=(short)(float)inputVal;
                    break;
                case DOUBLE:
                    inputCast=(short)(double)inputVal;
                    break;
                default:
                    throw inputType.invalid();
                }
                final var expectedArr=(short[])this.expectedArr;
                for(int i=0;;++i){
                    if(expectedArr[i] == inputCast){
                        System.arraycopy(expectedArr,i + 1,expectedArr,i,expectedSize - i - 1);
                        break;
                    }
                }
                break;
            }
            case INT:{
                int inputCast;
                switch(inputType){
                case BOOLEAN:
                    inputCast=(boolean)inputVal?1:0;
                    break;
                case BYTE:
                    inputCast=(byte)inputVal;
                    break;
                case CHAR:
                    inputCast=(char)inputVal;
                    break;
                case SHORT:
                    inputCast=(short)inputVal;
                    break;
                case INT:
                    inputCast=(int)inputVal;
                    break;
                case LONG:
                    inputCast=(int)(long)inputVal;
                    break;
                case FLOAT:
                    inputCast=(int)(float)inputVal;
                    break;
                case DOUBLE:
                    inputCast=(int)(double)inputVal;
                    break;
                default:
                    throw inputType.invalid();
                }
                final var expectedArr=(int[])this.expectedArr;
                for(int i=0;;++i){
                    if(expectedArr[i] == inputCast){
                        System.arraycopy(expectedArr,i + 1,expectedArr,i,expectedSize - i - 1);
                        break;
                    }
                }
                break;
            }
            case LONG:{
                long inputCast;
                switch(inputType){
                case BOOLEAN:
                    inputCast=(boolean)inputVal?1L:0L;
                    break;
                case BYTE:
                    inputCast=(byte)inputVal;
                    break;
                case CHAR:
                    inputCast=(char)inputVal;
                    break;
                case SHORT:
                    inputCast=(short)inputVal;
                    break;
                case INT:
                    inputCast=(int)inputVal;
                    break;
                case LONG:
                    inputCast=(long)inputVal;
                    break;
                case FLOAT:
                    inputCast=(long)(float)inputVal;
                    break;
                case DOUBLE:
                    inputCast=(long)(double)inputVal;
                    break;
                default:
                    throw inputType.invalid();
                }
                final var expectedArr=(long[])this.expectedArr;
                for(int i=0;;++i){
                    if(expectedArr[i] == inputCast){
                        System.arraycopy(expectedArr,i + 1,expectedArr,i,expectedSize - i - 1);
                        break;
                    }
                }
                break;
            }
            case FLOAT:{
                float inputCast;
                switch(inputType){
                case BOOLEAN:
                    inputCast=(boolean)inputVal?1.0f:0.0f;
                    break;
                case BYTE:
                    inputCast=(byte)inputVal;
                    break;
                case CHAR:
                    inputCast=(char)inputVal;
                    break;
                case SHORT:
                    inputCast=(short)inputVal;
                    break;
                case INT:
                    inputCast=(int)inputVal;
                    break;
                case LONG:
                    inputCast=(long)inputVal;
                    break;
                case FLOAT:
                    inputCast=(float)inputVal;
                    break;
                case DOUBLE:
                    inputCast=(float)(double)inputVal;
                    break;
                default:
                    throw inputType.invalid();
                }
                final var expectedArr=(float[])this.expectedArr;
                if(inputCast == inputCast){
                    for(int i=0;;++i){
                        if(expectedArr[i] == inputCast){
                            System.arraycopy(expectedArr,i + 1,expectedArr,i,expectedSize - i - 1);
                            break;
                        }
                    }
                }else{
                    for(int i=0;;++i){
                        float v;
                        if((v=expectedArr[i]) != v){
                            System.arraycopy(expectedArr,i + 1,expectedArr,i,expectedSize - i - 1);
                            break;
                        }
                    }
                }
                break;
            }
            case DOUBLE:{
                double inputCast;
                switch(inputType){
                case BOOLEAN:
                    inputCast=(boolean)inputVal?1.0:0.0;
                    break;
                case BYTE:
                    inputCast=(byte)inputVal;
                    break;
                case CHAR:
                    inputCast=(char)inputVal;
                    break;
                case SHORT:
                    inputCast=(short)inputVal;
                    break;
                case INT:
                    inputCast=(int)inputVal;
                    break;
                case LONG:
                    inputCast=(long)inputVal;
                    break;
                case FLOAT:
                    inputCast=(float)inputVal;
                    break;
                case DOUBLE:
                    inputCast=(double)inputVal;
                    break;
                default:
                    throw inputType.invalid();
                }
                final var expectedArr=(double[])this.expectedArr;
                if(inputCast == inputCast){
                    for(int i=0;;++i){
                        if(expectedArr[i] == inputCast){
                            System.arraycopy(expectedArr,i + 1,expectedArr,i,expectedSize - i - 1);
                            break;
                        }
                    }
                }else{
                    for(int i=0;;++i){
                        double v;
                        if((v=expectedArr[i]) != v){
                            System.arraycopy(expectedArr,i + 1,expectedArr,i,expectedSize - i - 1);
                            break;
                        }
                    }
                }
                break;
            }
            case REF:{
                final var expectedArr=(Object[])this.expectedArr;
                if(inputVal == null){
                    for(int i=0;;++i){
                        if(expectedArr[i] == null){
                            System.arraycopy(expectedArr,i + 1,expectedArr,i,expectedSize - i - 1);
                            break;
                        }
                    }
                }else{
                    for(int i=0;;++i){
                        if(inputVal.equals(expectedArr[i])){
                            System.arraycopy(expectedArr,i + 1,expectedArr,i,expectedSize - i - 1);
                            break;
                        }
                    }
                }
                expectedArr[expectedSize]=null;
                break;
            }
            default:
                throw dataType.invalid();
            }
            --this.expectedSize;
            ++expectedModCount;
        }
        @Override
        public void verifyGetResult(int index,Object result,DataType outputType){
            switch(outputType){
            case BOOLEAN:
                Assertions.assertEquals(((boolean[])expectedArr)[index],(boolean)result);
                break;
            case BYTE:
                switch(dataType){
                case BOOLEAN:
                    Assertions.assertEquals(((boolean[])expectedArr)[index]?(byte)1:(byte)0,(byte)result);
                    break;
                case BYTE:
                    Assertions.assertEquals(((byte[])expectedArr)[index],(byte)result);
                    break;
                default:
                    throw dataType.invalid();
                }
                break;
            case CHAR:
                switch(dataType){
                case BOOLEAN:
                    Assertions.assertEquals(((boolean[])expectedArr)[index]?(char)1:(char)0,(char)result);
                    break;
                case CHAR:
                    Assertions.assertEquals(((char[])expectedArr)[index],(char)result);
                    break;
                default:
                    throw dataType.invalid();
                }
                break;
            case SHORT:
                switch(dataType){
                case BOOLEAN:
                    Assertions.assertEquals(((boolean[])expectedArr)[index]?(short)1:(short)0,(short)result);
                    break;
                case BYTE:
                    Assertions.assertEquals(((byte[])expectedArr)[index],(short)result);
                    break;
                case SHORT:
                    Assertions.assertEquals(((short[])expectedArr)[index],(short)result);
                    break;
                default:
                    throw dataType.invalid();
                }
                break;
            case INT:
                switch(dataType){
                case BOOLEAN:
                    Assertions.assertEquals(((boolean[])expectedArr)[index]?(int)1:(int)0,(int)result);
                    break;
                case BYTE:
                    Assertions.assertEquals(((byte[])expectedArr)[index],(int)result);
                    break;
                case CHAR:
                    Assertions.assertEquals(((char[])expectedArr)[index],(int)result);
                    break;
                case SHORT:
                    Assertions.assertEquals(((short[])expectedArr)[index],(int)result);
                    break;
                case INT:
                    Assertions.assertEquals(((int[])expectedArr)[index],(int)result);
                    break;
                default:
                    throw dataType.invalid();
                }
                break;
            case LONG:
                switch(dataType){
                case BOOLEAN:
                    Assertions.assertEquals(((boolean[])expectedArr)[index]?(long)1:(long)0,(long)result);
                    break;
                case BYTE:
                    Assertions.assertEquals(((byte[])expectedArr)[index],(long)result);
                    break;
                case CHAR:
                    Assertions.assertEquals(((char[])expectedArr)[index],(long)result);
                    break;
                case SHORT:
                    Assertions.assertEquals(((short[])expectedArr)[index],(long)result);
                    break;
                case INT:
                    Assertions.assertEquals(((int[])expectedArr)[index],(long)result);
                    break;
                case LONG:
                    Assertions.assertEquals(((long[])expectedArr)[index],(long)result);
                    break;
                default:
                    throw dataType.invalid();
                }
                break;
            case FLOAT:
                switch(dataType){
                case BOOLEAN:
                    Assertions.assertEquals(((boolean[])expectedArr)[index]?(float)1:(float)0,(float)result);
                    break;
                case BYTE:
                    Assertions.assertEquals(((byte[])expectedArr)[index],(float)result);
                    break;
                case CHAR:
                    Assertions.assertEquals(((char[])expectedArr)[index],(float)result);
                    break;
                case SHORT:
                    Assertions.assertEquals(((short[])expectedArr)[index],(float)result);
                    break;
                case INT:
                    Assertions.assertEquals(((int[])expectedArr)[index],(float)result);
                    break;
                case LONG:
                    Assertions.assertEquals(((long[])expectedArr)[index],(float)result);
                    break;
                case FLOAT:
                    Assertions.assertEquals(((float[])expectedArr)[index],(float)result);
                    break;
                default:
                    throw dataType.invalid();
                }
                break;
            case DOUBLE:
                switch(dataType){
                case BOOLEAN:
                    Assertions.assertEquals(((boolean[])expectedArr)[index]?(double)1:(double)0,(double)result);
                    break;
                case BYTE:
                    Assertions.assertEquals(((byte[])expectedArr)[index],(double)result);
                    break;
                case CHAR:
                    Assertions.assertEquals(((char[])expectedArr)[index],(double)result);
                    break;
                case SHORT:
                    Assertions.assertEquals(((short[])expectedArr)[index],(double)result);
                    break;
                case INT:
                    Assertions.assertEquals(((int[])expectedArr)[index],(double)result);
                    break;
                case LONG:
                    Assertions.assertEquals(((long[])expectedArr)[index],(double)result);
                    break;
                case FLOAT:
                    Assertions.assertEquals(((float[])expectedArr)[index],(double)result);
                    break;
                case DOUBLE:
                    Assertions.assertEquals(((double[])expectedArr)[index],(double)result);
                    break;
                default:
                    throw dataType.invalid();
                }
                break;
            case REF:
                switch(dataType){
                case BOOLEAN:
                    Assertions.assertEquals(((boolean[])expectedArr)[index],result);
                    break;
                case BYTE:
                    Assertions.assertEquals(((byte[])expectedArr)[index],result);
                    break;
                case CHAR:
                    Assertions.assertEquals(((char[])expectedArr)[index],result);
                    break;
                case SHORT:
                    Assertions.assertEquals(((short[])expectedArr)[index],result);
                    break;
                case INT:
                    Assertions.assertEquals(((int[])expectedArr)[index],result);
                    break;
                case LONG:
                    Assertions.assertEquals(((long[])expectedArr)[index],result);
                    break;
                case FLOAT:
                    Assertions.assertEquals(((float[])expectedArr)[index],result);
                    break;
                case DOUBLE:
                    Assertions.assertEquals(((double[])expectedArr)[index],result);
                    break;
                case REF:
                    Assertions.assertSame(((Object[])expectedArr)[index],result);
                    break;
                default:
                    throw dataType.invalid();
                }
                break;
            }
        }
        @Override
        public void verifyPutResult(int index,Object input,DataType inputType){
            Object expectedVal=dataType.convertValUnchecked(inputType,input);
            switch(dataType){
            case BOOLEAN:
                Assertions.assertEquals((boolean)expectedVal,((OmniList.OfBoolean)seq).getBoolean(index));
                ((boolean[])expectedArr)[index]=(boolean)expectedVal;
                break;
            case BYTE:
                Assertions.assertEquals((byte)expectedVal,((OmniList.OfByte)seq).getByte(index));
                ((byte[])expectedArr)[index]=(byte)expectedVal;
                break;
            case CHAR:
                Assertions.assertEquals((char)expectedVal,((OmniList.OfChar)seq).getChar(index));
                ((char[])expectedArr)[index]=(char)expectedVal;
                break;
            case SHORT:
                Assertions.assertEquals((short)expectedVal,((OmniList.OfShort)seq).getShort(index));
                ((short[])expectedArr)[index]=(short)expectedVal;
                break;
            case INT:
                Assertions.assertEquals((int)expectedVal,((OmniList.OfInt)seq).getInt(index));
                ((int[])expectedArr)[index]=(int)expectedVal;
                break;
            case LONG:
                Assertions.assertEquals((long)expectedVal,((OmniList.OfLong)seq).getLong(index));
                ((long[])expectedArr)[index]=(long)expectedVal;
                break;
            case FLOAT:
                Assertions.assertEquals((float)expectedVal,((OmniList.OfFloat)seq).getFloat(index));
                ((float[])expectedArr)[index]=(float)expectedVal;
                break;
            case DOUBLE:
                Assertions.assertEquals((double)expectedVal,((OmniList.OfDouble)seq).getDouble(index));
                ((double[])expectedArr)[index]=(double)expectedVal;
                break;
            case REF:
                Assertions.assertEquals(expectedVal,((OmniList.OfRef<?>)seq).get(index));
                ((Object[])expectedArr)[index]=expectedVal;
                break;
            default:
                throw dataType.invalid();
            }
        }
        @Override OmniList<?> initSeq(){
            switch(dataType){
            case BOOLEAN:
                if(checkedType.checked){
                    return new BooleanArrSeq.CheckedList();
                }else{
                    return new BooleanArrSeq.UncheckedList();
                }
            case BYTE:
                if(checkedType.checked){
                    return new ByteArrSeq.CheckedList();
                }else{
                    return new ByteArrSeq.UncheckedList();
                }
            case CHAR:
                if(checkedType.checked){
                    return new CharArrSeq.CheckedList();
                }else{
                    return new CharArrSeq.UncheckedList();
                }
            case DOUBLE:
                if(checkedType.checked){
                    return new DoubleArrSeq.CheckedList();
                }else{
                    return new DoubleArrSeq.UncheckedList();
                }
            case FLOAT:
                if(checkedType.checked){
                    return new FloatArrSeq.CheckedList();
                }else{
                    return new FloatArrSeq.UncheckedList();
                }
            case INT:
                if(checkedType.checked){
                    return new IntArrSeq.CheckedList();
                }else{
                    return new IntArrSeq.UncheckedList();
                }
            case LONG:
                if(checkedType.checked){
                    return new LongArrSeq.CheckedList();
                }else{
                    return new LongArrSeq.UncheckedList();
                }
            case REF:
                if(checkedType.checked){
                    return new RefArrSeq.CheckedList<>();
                }else{
                    return new RefArrSeq.UncheckedList<>();
                }
            case SHORT:
                if(checkedType.checked){
                    return new ShortArrSeq.CheckedList();
                }else{
                    return new ShortArrSeq.UncheckedList();
                }
            default:
                throw dataType.invalid();
            }
        }
        @Override OmniList<?> initSeq(int initCap){
            switch(dataType){
            case BOOLEAN:
                if(checkedType.checked){
                    return new BooleanArrSeq.CheckedList(initCap);
                }else{
                    return new BooleanArrSeq.UncheckedList(initCap);
                }
            case BYTE:
                if(checkedType.checked){
                    return new ByteArrSeq.CheckedList(initCap);
                }else{
                    return new ByteArrSeq.UncheckedList(initCap);
                }
            case CHAR:
                if(checkedType.checked){
                    return new CharArrSeq.CheckedList(initCap);
                }else{
                    return new CharArrSeq.UncheckedList(initCap);
                }
            case DOUBLE:
                if(checkedType.checked){
                    return new DoubleArrSeq.CheckedList(initCap);
                }else{
                    return new DoubleArrSeq.UncheckedList(initCap);
                }
            case FLOAT:
                if(checkedType.checked){
                    return new FloatArrSeq.CheckedList(initCap);
                }else{
                    return new FloatArrSeq.UncheckedList(initCap);
                }
            case INT:
                if(checkedType.checked){
                    return new IntArrSeq.CheckedList(initCap);
                }else{
                    return new IntArrSeq.UncheckedList(initCap);
                }
            case LONG:
                if(checkedType.checked){
                    return new LongArrSeq.CheckedList(initCap);
                }else{
                    return new LongArrSeq.UncheckedList(initCap);
                }
            case REF:
                if(checkedType.checked){
                    return new RefArrSeq.CheckedList<>(initCap);
                }else{
                    return new RefArrSeq.UncheckedList<>(initCap);
                }
            case SHORT:
                if(checkedType.checked){
                    return new ShortArrSeq.CheckedList(initCap);
                }else{
                    return new ShortArrSeq.UncheckedList(initCap);
                }
            default:
                throw dataType.invalid();
            }
        }
        @Override void updateModCount(){
            switch(dataType){
            case BOOLEAN:
                expectedModCount=((BooleanArrSeq.CheckedList)seq).modCount;
                break;
            case BYTE:
                expectedModCount=((ByteArrSeq.CheckedList)seq).modCount;
                break;
            case CHAR:
                expectedModCount=((CharArrSeq.CheckedList)seq).modCount;
                break;
            case DOUBLE:
                expectedModCount=((DoubleArrSeq.CheckedList)seq).modCount;
                break;
            case FLOAT:
                expectedModCount=((FloatArrSeq.CheckedList)seq).modCount;
                break;
            case INT:
                expectedModCount=((IntArrSeq.CheckedList)seq).modCount;
                break;
            case LONG:
                expectedModCount=((LongArrSeq.CheckedList)seq).modCount;
                break;
            case REF:
                expectedModCount=((RefArrSeq.CheckedList<?>)seq).modCount;
                break;
            case SHORT:
                expectedModCount=((ShortArrSeq.CheckedList)seq).modCount;
                break;
            default:
                throw dataType.invalid();
            }
        }
        @Override void verifyCloneTypeAndModCount(Object clone){
            switch(dataType){
            case BOOLEAN:{
                if(checkedType.checked){
                    Assertions.assertEquals(0,((BooleanArrSeq.CheckedList)clone).modCount);
                }else{
                    Assertions.assertTrue(clone instanceof BooleanArrSeq.UncheckedList);
                    Assertions.assertFalse(clone instanceof BooleanArrSeq.CheckedList);
                }
                break;
            }
            case BYTE:{
                if(checkedType.checked){
                    Assertions.assertEquals(0,((ByteArrSeq.CheckedList)clone).modCount);
                }else{
                    Assertions.assertTrue(clone instanceof ByteArrSeq.UncheckedList);
                    Assertions.assertFalse(clone instanceof ByteArrSeq.CheckedList);
                }
                break;
            }
            case CHAR:{
                if(checkedType.checked){
                    Assertions.assertEquals(0,((CharArrSeq.CheckedList)clone).modCount);
                }else{
                    Assertions.assertTrue(clone instanceof CharArrSeq.UncheckedList);
                    Assertions.assertFalse(clone instanceof CharArrSeq.CheckedList);
                }
                break;
            }
            case DOUBLE:{
                if(checkedType.checked){
                    Assertions.assertEquals(0,((DoubleArrSeq.CheckedList)clone).modCount);
                }else{
                    Assertions.assertTrue(clone instanceof DoubleArrSeq.UncheckedList);
                    Assertions.assertFalse(clone instanceof DoubleArrSeq.CheckedList);
                }
                break;
            }
            case FLOAT:{
                if(checkedType.checked){
                    Assertions.assertEquals(0,((FloatArrSeq.CheckedList)clone).modCount);
                }else{
                    Assertions.assertTrue(clone instanceof FloatArrSeq.UncheckedList);
                    Assertions.assertFalse(clone instanceof FloatArrSeq.CheckedList);
                }
                break;
            }
            case INT:{
                if(checkedType.checked){
                    Assertions.assertEquals(0,((IntArrSeq.CheckedList)clone).modCount);
                }else{
                    Assertions.assertTrue(clone instanceof IntArrSeq.UncheckedList);
                    Assertions.assertFalse(clone instanceof IntArrSeq.CheckedList);
                }
                break;
            }
            case LONG:{
                if(checkedType.checked){
                    Assertions.assertEquals(0,((LongArrSeq.CheckedList)clone).modCount);
                }else{
                    Assertions.assertTrue(clone instanceof LongArrSeq.UncheckedList);
                    Assertions.assertFalse(clone instanceof LongArrSeq.CheckedList);
                }
                break;
            }
            case REF:{
                if(checkedType.checked){
                    Assertions.assertEquals(0,((RefArrSeq.CheckedList<?>)clone).modCount);
                }else{
                    Assertions.assertTrue(clone instanceof RefArrSeq.UncheckedList);
                    Assertions.assertFalse(clone instanceof RefArrSeq.CheckedList);
                }
                break;
            }
            case SHORT:{
                if(checkedType.checked){
                    Assertions.assertEquals(0,((ShortArrSeq.CheckedList)clone).modCount);
                }else{
                    Assertions.assertTrue(clone instanceof ShortArrSeq.UncheckedList);
                    Assertions.assertFalse(clone instanceof ShortArrSeq.CheckedList);
                }
                break;
            }
            default:
                throw dataType.invalid();
            }
        }
        @Override void verifyModCount(){
            int actualModCount;
            switch(dataType){
            case BOOLEAN:
                actualModCount=((BooleanArrSeq.CheckedList)seq).modCount;
                break;
            case BYTE:
                actualModCount=((ByteArrSeq.CheckedList)seq).modCount;
                break;
            case CHAR:
                actualModCount=((CharArrSeq.CheckedList)seq).modCount;
                break;
            case DOUBLE:
                actualModCount=((DoubleArrSeq.CheckedList)seq).modCount;
                break;
            case FLOAT:
                actualModCount=((FloatArrSeq.CheckedList)seq).modCount;
                break;
            case INT:
                actualModCount=((IntArrSeq.CheckedList)seq).modCount;
                break;
            case LONG:
                actualModCount=((LongArrSeq.CheckedList)seq).modCount;
                break;
            case REF:
                actualModCount=((RefArrSeq.CheckedList<?>)seq).modCount;
                break;
            case SHORT:
                actualModCount=((ShortArrSeq.CheckedList)seq).modCount;
                break;
            default:
                throw dataType.invalid();
            }
            Assertions.assertEquals(expectedModCount,actualModCount);
        }
    }
    private static interface ToStringAndHashCodeTest{
        void callMethod(int size,DataType collectionType,CheckedType checkedType,int initVal,MonitoredObjectGen objGen);
        private void runAllTests(String testName){
            for(int size:FIB_SEQ){
                if(size > 100){
                    break;
                }
                for(var collectionType:DataType.values()){
                    final int initValBound;
                    if(collectionType == DataType.BOOLEAN){
                        initValBound=1;
                    }else{
                        initValBound=0;
                    }
                    for(var checkedType:CheckedType.values()){
                        if(collectionType == DataType.REF || !checkedType.checked){
                            IntStream.rangeClosed(0,initValBound).forEach(initVal->{
                                for(var objGen:StructType.ArrList.validMonitoredObjectGens){
                                    if(checkedType.checked || objGen.expectedException == null){
                                        TestExecutorService.submitTest(
                                                ()->callMethod(size,collectionType,checkedType,initVal,objGen));
                                    }
                                    if(collectionType != DataType.REF){
                                        break;
                                    }
                                }
                            });
                        }
                    }
                }
            }
            TestExecutorService.completeAllTests(testName);
        }
    }
    private static interface BasicTest{
        default void runAllTests(String testName) {
            for(int seqSize:FIB_SEQ) {
                if(seqSize>100) {
                    break;
                }
                for(var dataType:DataType.values()) {
                    for(var checkedType:CheckedType.values()) {
                        for(var initCap:INIT_CAPACITIES) {
                            TestExecutorService.submitTest(()->runTest(SequenceInitialization.Ascending.initialize(new ArrListMonitor(checkedType,dataType,initCap),seqSize,0)));
                        }
                    }
                }
            }
            TestExecutorService.completeAllTests("ArrListTest.testsize_void");
        }

        void runTest(ArrListMonitor monitor);
    }
    private static interface QueryTest{
        void callAndVerifyResult(ArrListMonitor monitor,QueryVal queryVal,DataType inputType,QueryCastType castType,
                QueryVal.QueryValModification modification,MonitoredObjectGen monitoredObjectGen,double position,
                int seqSize);
        private void runAllTests(String testName){
            for(var collectionType:DataType.values()){
                for(var queryVal:QueryVal.values()){
                    if(collectionType.isValidQueryVal(queryVal)){
                        queryVal.validQueryCombos.forEach((modification,castTypesToInputTypes)->{
                            castTypesToInputTypes.forEach((castType,inputTypes)->{
                                inputTypes.forEach(inputType->{
                                    if(queryVal == QueryVal.NonNull){
                                        for(var monitoredObjectGen:StructType.ArrList.validMonitoredObjectGens){
                                            if(monitoredObjectGen.expectedException != null){
                                                for(var size:FIB_SEQ){
                                                    if(size > 1000){
                                                        break;
                                                    }
                                                    if(size > 0){
                                                        TestExecutorService.submitTest(()->Assertions.assertThrows(
                                                                monitoredObjectGen.expectedException,
                                                                ()->runTest(collectionType,queryVal,modification,
                                                                        inputType,castType,CheckedType.CHECKED,size,-1,
                                                                        monitoredObjectGen)));
                                                    }
                                                }
                                            }
                                        }
                                    }else{
                                        final boolean queryCanReturnTrue=queryVal.queryCanReturnTrue(modification,
                                                castType,inputType,collectionType);
                                        for(var size:FIB_SEQ){
                                            if(size > 1000){
                                                break;
                                            }
                                            for(var position:POSITIONS){
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
                                                for(var checkedType:CheckedType.values()){
                                                    TestExecutorService.submitTest(
                                                            ()->runTest(collectionType,queryVal,modification,inputType,
                                                                    castType,checkedType,size,position,null));
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
            TestExecutorService.completeAllTests(testName);
        }
        @SuppressWarnings("unchecked")
        private void runTest(DataType collectionType,QueryVal queryVal,QueryVal.QueryValModification modification,
                DataType inputType,QueryCastType castType,CheckedType checkedType,int seqSize,double position,
                MonitoredObjectGen monitoredObjectGen){
            var monitor=new ArrListMonitor(checkedType,collectionType);
            if(position < 0){
                switch(collectionType){
                case BOOLEAN:
                    queryVal.initDoesNotContain((OmniCollection.OfBoolean)monitor.seq,seqSize,0,modification);
                    break;
                case BYTE:
                    queryVal.initDoesNotContain((OmniCollection.OfByte)monitor.seq,seqSize,0,modification);
                    break;
                case CHAR:
                    queryVal.initDoesNotContain((OmniCollection.OfChar)monitor.seq,seqSize,0,modification);
                    break;
                case DOUBLE:
                    queryVal.initDoesNotContain((OmniCollection.OfDouble)monitor.seq,seqSize,0,modification);
                    break;
                case FLOAT:
                    queryVal.initDoesNotContain((OmniCollection.OfFloat)monitor.seq,seqSize,0,modification);
                    break;
                case INT:
                    queryVal.initDoesNotContain((OmniCollection.OfInt)monitor.seq,seqSize,0,modification);
                    break;
                case LONG:
                    queryVal.initDoesNotContain((OmniCollection.OfLong)monitor.seq,seqSize,0,modification);
                    break;
                case REF:
                    queryVal.initDoesNotContain((OmniCollection.OfRef<Object>)monitor.seq,seqSize,0,modification);
                    break;
                case SHORT:
                    queryVal.initDoesNotContain((OmniCollection.OfShort)monitor.seq,seqSize,0,modification);
                    break;
                default:
                    throw collectionType.invalid();
                }
            }else{
                switch(collectionType){
                case BOOLEAN:
                    queryVal.initContains((OmniCollection.OfBoolean)monitor.seq,seqSize,0,position,modification);
                    break;
                case BYTE:
                    queryVal.initContains((OmniCollection.OfByte)monitor.seq,seqSize,0,position,modification);
                    break;
                case CHAR:
                    queryVal.initContains((OmniCollection.OfChar)monitor.seq,seqSize,0,position,modification);
                    break;
                case DOUBLE:
                    queryVal.initContains((OmniCollection.OfDouble)monitor.seq,seqSize,0,position,modification);
                    break;
                case FLOAT:
                    queryVal.initContains((OmniCollection.OfFloat)monitor.seq,seqSize,0,position,modification);
                    break;
                case INT:
                    queryVal.initContains((OmniCollection.OfInt)monitor.seq,seqSize,0,position,modification);
                    break;
                case LONG:
                    queryVal.initContains((OmniCollection.OfLong)monitor.seq,seqSize,0,position,modification);
                    break;
                case REF:
                    queryVal.initContains((OmniCollection.OfRef<Object>)monitor.seq,seqSize,0,position,modification,
                            inputType);
                    break;
                case SHORT:
                    queryVal.initContains((OmniCollection.OfShort)monitor.seq,seqSize,0,position,modification);
                    break;
                default:
                    throw collectionType.invalid();
                }
            }
            monitor.updateCollectionState();
            callAndVerifyResult(monitor,queryVal,inputType,castType,modification,monitoredObjectGen,position,seqSize);
        }
    }
}
