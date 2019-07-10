package omni.impl.seq;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.api.OmniCollection;
import omni.api.OmniIterator;
import omni.api.OmniList;
import omni.api.OmniListIterator;
import omni.impl.CheckedType;
import omni.impl.DataType;
import omni.impl.FunctionCallType;
import omni.impl.IllegalModification;
import omni.impl.IteratorType;
import omni.impl.MonitoredCollection;
import omni.impl.MonitoredFunction;
import omni.impl.MonitoredFunctionGen;
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
    private static final int[] FIB_SEQ=new int[]{0,1,2,3,5,8,13,21,34,55,89};
    private static final int[] EXTENDED_FIB_SEQ=new int[]{0,1,2,3,5,8,13,21,34,55,89,144,233,377,610,987,1597,2584,4181,
            6765,10946};
    @Test
    public void testadd_intval(){
        for(final var collectionType:DataType.values()){
            for(final var inputType:collectionType.mayBeAddedTo()){
                for(final var functionCallType:FunctionCallType.values()){
                    if(inputType != DataType.REF || functionCallType != FunctionCallType.Boxed){
                        for(final var initCap:INIT_CAPACITIES){
                            for(final var checkedType:CheckedType.values()){
                                for(final var position:POSITIONS){
                                    if(position >= 0 || checkedType.checked){
                                        TestExecutorService.submitTest(()->{
                                            final var monitor=new ArrListMonitor(checkedType,collectionType,initCap);
                                            if(position < 0){
                                                for(int i=0;i < 1000;++i){
                                                    final Object inputVal=inputType.convertValUnchecked(i);
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
    @Test
    public void testadd_val(){
        for(final var collectionType:DataType.values()){
            for(final var inputType:collectionType.mayBeAddedTo()){
                for(final var functionCallType:FunctionCallType.values()){
                    if(inputType != DataType.REF || functionCallType != FunctionCallType.Boxed){
                        for(final var initCap:INIT_CAPACITIES){
                            for(final var checkedType:CheckedType.values()){
                                TestExecutorService.submitTest(()->{
                                    final var monitor=new ArrListMonitor(checkedType,collectionType,initCap);
                                    for(int i=0;i < 1000;++i){
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
    @Test
    public void testclear_void(){
        final BasicTest test=(monitor)->monitor.verifyClear();
        test.runAllTests("ArrListTest.testclear_void");
    }
    @Test
    public void testclone_void(){
        final BasicTest test=(monitor)->monitor.verifyClone();
        test.runAllTests("ArrListTest.testclone_void");
    }
    @Test
    public void testConstructor_int(){
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
    @Test
    public void testConstructor_void(){
        for(final var dataType:DataType.values()){
            for(final var checkedType:CheckedType.values()){
                TestExecutorService.submitTest(()->new ArrListMonitor(checkedType,dataType).verifyCollectionState());
            }
        }
        TestExecutorService.completeAllTests("ArrListTest.testConstructor_void");
    }
    @Test
    public void testcontains_val(){
        final QueryTest test=(monitor,queryVal,inputType,castType,modification,monitoredObjectGen,position,seqSize)->{
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
        for(final var functionGen:StructType.ArrList.validMonitoredFunctionGens){
            for(final var checkedType:CheckedType.values()){
                for(final var size:FIB_SEQ){
                    if(size == 0 || checkedType.checked || functionGen.expectedException == null){
                        for(final var functionCallType:FunctionCallType.values()){
                            final long randSeedBound=functionGen.randomized && !functionCallType.boxed && size > 0?100
                                    :0;
                            LongStream.rangeClosed(0,randSeedBound).forEach(randSeed->{
                                for(final var collectionType:DataType.values()){
                                    if(collectionType != DataType.REF || !functionCallType.boxed){
                                        TestExecutorService.submitTest(()->{
                                            final var monitor=SequenceInitialization.Ascending
                                                    .initialize(new ArrListMonitor(checkedType,collectionType,size),
                                                            size,0);
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
        for(final var collectionType:DataType.values()){
            for(final var checkedType:CheckedType.values()){
                for(final int size:FIB_SEQ){
                    TestExecutorService.submitTest(()->{
                        final var monitor=SequenceInitialization.Ascending
                                .initialize(new ArrListMonitor(checkedType,collectionType,size),size,0);
                        for(final var outputType:collectionType.validOutputTypes()){
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
    public void testhashCode_void(){
        final ToStringAndHashCodeTest test=(size,collectionType,checkedType,initVal,objGen)->{
            if(collectionType == DataType.REF){
                final var throwSwitch=new MonitoredObjectGen.ThrowSwitch();
                final var monitor=SequenceInitialization.Ascending.initializeWithMonitoredObj(
                        new ArrListMonitor(checkedType,collectionType,size),size,initVal,objGen,throwSwitch);
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
    @Test
    public void testindexOf_val(){
        final QueryTest test=(monitor,queryVal,inputType,castType,modification,monitoredObjectGen,position,seqSize)->{
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
    public void testisEmpty_void(){
        final BasicTest test=(monitor)->monitor.verifyIsEmpty();
        test.runAllTests("ArrListTest.testisEmpty_void");
    }
    @Test
    public void testiterator_void(){
        for(var collectionType:DataType.values()){
            for(var itrType:StructType.ArrList.validItrTypes){
                for(var checkedType:CheckedType.values()){
                    for(var size:FIB_SEQ){
                        TestExecutorService.submitTest(()->SequenceInitialization.Ascending
                                .initialize(new ArrListMonitor(checkedType,collectionType,size),size,0)
                                .getMonitoredIterator(itrType).verifyIteratorState());
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("ArrListTest.testiterator_void");
    }
    @Test
    public void testItrclone_void(){
        for(var collectionType:DataType.values()){
            for(var itrType:StructType.ArrList.validItrTypes){
                for(var checkedType:CheckedType.values()){
                    for(var size:FIB_SEQ){
                        int prevIndex=-1;
                        for(var position:POSITIONS){
                            int index;
                            if(position >= 0 && (index=(int)(position * size)) != prevIndex){
                                prevIndex=index;
                                TestExecutorService.submitTest(()->{
                                    var seqMonitor=SequenceInitialization.Ascending
                                            .initialize(new ArrListMonitor(checkedType,collectionType,size),size,0);
                                    var itrMonitor=seqMonitor.getMonitoredIterator(index,itrType);
                                    itrMonitor.verifyClone();
                                });
                            }
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("ArrListTest.testItrclone_void");
    }
    @Test
    public void testItrforEachRemaining_Consumer(){
        for(var collectionType:DataType.values()){
            for(var functionCallType:FunctionCallType.values()){
                if(collectionType != DataType.REF || !functionCallType.boxed){
                    for(var size:FIB_SEQ){
                        int prevNumToIterate=-1;
                        for(var position:POSITIONS){
                            int numToIterate;
                            if(position >= 0 && (numToIterate=(int)(position * size)) != prevNumToIterate){
                                prevNumToIterate=numToIterate;
                                int numLeft=size - numToIterate;
                                for(var itrType:StructType.ArrList.validItrTypes){
                                    for(var functionGen:itrType.validMonitoredFunctionGens){
                                        for(var checkedType:CheckedType.values()){
                                            for(var illegalMod:itrType.validPreMods){
                                                if(checkedType.checked || illegalMod.expectedException == null
                                                        && (size == 0 || functionGen.expectedException == null)){
                                                    long randSeedBound=!functionCallType.boxed && numLeft > 1
                                                            && functionGen.randomized?100:0;
                                                    for(long tmpRandSeed=0;tmpRandSeed <= randSeedBound;++tmpRandSeed){
                                                        final long randSeed=tmpRandSeed;
                                                        TestExecutorService.submitTest(()->{
                                                            var seqMonitor=SequenceInitialization.Ascending.initialize(
                                                                    new ArrListMonitor(checkedType,collectionType,size),
                                                                    size,0);
                                                            var itrMonitor=seqMonitor.getMonitoredIterator(numToIterate,
                                                                    itrType);
                                                            itrMonitor.illegalMod(illegalMod);
                                                            if(illegalMod.expectedException == null || numLeft == 0){
                                                                if(functionGen.expectedException == null
                                                                        || numLeft == 0){
                                                                    itrMonitor.verifyForEachRemaining(functionGen,
                                                                            functionCallType,randSeed);
                                                                }else{
                                                                    Assertions.assertThrows(
                                                                            functionGen.expectedException,
                                                                            ()->itrMonitor.verifyForEachRemaining(
                                                                                    functionGen,functionCallType,
                                                                                    randSeed));
                                                                }
                                                            }else{
                                                                Assertions
                                                                .assertThrows(illegalMod.expectedException,
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
                }
            }
        }
        TestExecutorService.completeAllTests("ArrListTest.testItrforEachRemaining_Consumer");
    }
    @Test
    public void testItrhasNext_void(){
        for(var collectionType:DataType.values()){
            for(var itrType:StructType.ArrList.validItrTypes){
                for(var checkedType:CheckedType.values()){
                    for(var size:FIB_SEQ){
                        
                          
                              TestExecutorService.submitTest(()->{
                                  var seqMonitor=SequenceInitialization.Ascending.initialize(
                                          new ArrListMonitor(checkedType,collectionType,size),size,0);
                                  var itrMonitor=seqMonitor.getMonitoredIterator(itrType);
                                  while(itrMonitor.verifyHasNext()){
                                      itrMonitor.iterateForward();
                                  }
                              });
                          
                        
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("ArrListTest.testItrhasNext_void");
    }
    @Test
    public void testListItrhasPrevious_void(){
        for(var collectionType:DataType.values()){
            
          for(var checkedType:CheckedType.values()){
              for(var size:FIB_SEQ){
                  
                    
                TestExecutorService.submitTest(()->{
                    var seqMonitor=SequenceInitialization.Ascending.initialize(
                            new ArrListMonitor(checkedType,collectionType,size),size,0);
                    var itrMonitor=seqMonitor.getMonitoredListIterator(size);
                    while(itrMonitor.verifyHasPrevious()){
                        itrMonitor.iterateReverse();
                    }
                });
                    
                  
              }
          }
            
        }
        TestExecutorService.completeAllTests("ArrListTest.testListItrhasPrevious_void");
    }
    @Test
    public void testListItrpreviousIndex_void(){
        for(var collectionType:DataType.values()){
          
        for(var checkedType:CheckedType.values()){
            for(var size:FIB_SEQ){
                
                  
              TestExecutorService.submitTest(()->{
                  var seqMonitor=SequenceInitialization.Ascending.initialize(
                          new ArrListMonitor(checkedType,collectionType,size),size,0);
                  var itrMonitor=seqMonitor.getMonitoredListIterator(size);
                  while(itrMonitor.verifyPreviousIndex()>0){
                      itrMonitor.iterateReverse();
                  }
              });
                  
                
            }
        }
          
      }
        TestExecutorService.completeAllTests("ArrListTest.testListItrhasPrevious_void");
    }
    @Test
    public void testListItrnextIndex_void(){
        for(var collectionType:DataType.values()){
            
          for(var checkedType:CheckedType.values()){
              for(var size:FIB_SEQ){
                  
                    
                TestExecutorService.submitTest(()->{
                    var seqMonitor=SequenceInitialization.Ascending.initialize(
                            new ArrListMonitor(checkedType,collectionType,size),size,0);
                    var itrMonitor=seqMonitor.getMonitoredListIterator();
                    while(itrMonitor.verifyNextIndex()<size){
                        itrMonitor.iterateForward();
                    }
                });
                    
                  
              }
          }
            
        }
        TestExecutorService.completeAllTests("ArrListTest.testListItrnextIndex_void");
    }
    @Test
    public void testItrnext_void(){
        for(var collectionType:DataType.values()){
            for(var outputType:collectionType.validOutputTypes()){
                for(var itrType:StructType.ArrList.validItrTypes){
                    for(var illegalMod:itrType.validPreMods){
                        for(var checkedType:CheckedType.values()){
                            if(illegalMod.expectedException == null || checkedType.checked){
                                for(var size:FIB_SEQ){
                                    if(checkedType.checked || size > 0){
                                        
                                      TestExecutorService.submitTest(()->{
                                          var seqMonitor=SequenceInitialization.Ascending.initialize(
                                                  new ArrListMonitor(checkedType,collectionType,size),
                                                  size,0);
                                          var itrMonitor=seqMonitor.getMonitoredIterator(itrType);
                                          itrMonitor.illegalMod(illegalMod);
                                          if(illegalMod.expectedException == null){
                                              while(itrMonitor.hasNext()){
                                                  itrMonitor.verifyNext(outputType);
                                              }
                                              if(checkedType.checked){
                                                  Assertions.assertThrows(NoSuchElementException.class,
                                                          ()->itrMonitor.verifyNext(outputType));
                                              }
                                          }else{
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
        }
        TestExecutorService.completeAllTests("ArrListTest.testItrnext_void");
    }
    @Test
    public void testListItrprevious_void(){
        for(var collectionType:DataType.values()){
            for(var outputType:collectionType.validOutputTypes()){
                    for(var illegalMod:IteratorType.BidirectionalItr.validPreMods){
                        for(var checkedType:CheckedType.values()){
                            if(illegalMod.expectedException == null || checkedType.checked){
                                for(var size:FIB_SEQ){
                                    if(checkedType.checked || size > 0){
                                      TestExecutorService.submitTest(()->{
                                          var seqMonitor=SequenceInitialization.Ascending.initialize(
                                                  new ArrListMonitor(checkedType,collectionType,size),
                                                  size,0);
                                          var itrMonitor=seqMonitor.getMonitoredListIterator(size);
                                          itrMonitor.illegalMod(illegalMod);
                                          if(illegalMod.expectedException == null){
                                              while(itrMonitor.hasPrevious()){
                                                  itrMonitor.verifyPrevious(outputType);
                                              }
                                              if(checkedType.checked){
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
        }
        TestExecutorService.completeAllTests("ArrListTest.testListItrprevious_void");
    }
    
    @Test
    public void testListItrset_val() {
      //TODO
    }
    @Test
    public void testlistIterator_void() {
      //TODO
    }
    @Test
    public void testlistIterator_int() {
      //TODO
    }
    @Test
    public void testremoveAt_int() {
      //TODO
    }
    @Test
    public void testreplaceAll_UnaryOperator() {
      //TODO
    }
    @Test
    public void testsort_Comparator() {
      //TODO
    }
    @Test
    public void teststableAscendingSort_void() {
      //TODO
    }
    @Test
    public void teststableDescendingSort_void() {
      //TODO
    }
    @Test
    public void testunstableAscendingSort_void() {
      //TODO
    }
    @Test
    public void testunstableDescendingSort_void() {
      //TODO
    }
    @Test
    public void testunstableSort_Comparator() {
      //TODO
    }
    @Test
    public void testsubList_intint() {
      //TODO
    }
    
    
    @Test
    public void testItrremove_void(){
        for(var itrType:StructType.ArrList.validItrTypes){
            for(var illegalMod:itrType.validPreMods){
                for(var checkedType:CheckedType.values()){
                    if(illegalMod.expectedException == null || checkedType.checked){
                        for(var removeScenario:itrType.validItrRemoveScenarios){
                            if(checkedType.checked || removeScenario.expectedException == null){
                                for(var collectionType:DataType.values()){
                                    for(var size:FIB_SEQ){
                                        int prevNumToIterate=-1;
                                        positionLoop:for(var position:POSITIONS){
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
                                                var seqMonitor=SequenceInitialization.Ascending.initialize(
                                                        new ArrListMonitor(checkedType,collectionType,size),size,0);
                                                var itrMonitor=seqMonitor.getMonitoredIterator(numToIterate,
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
                                                            var cast=(MonitoredList.MonitoredListIterator<?,?>)itrMonitor;
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
        }
        TestExecutorService.completeAllTests("ArrListTest.testItrremove_void");
    }
    @Test
    public void testListItradd_val() {

            for(var checkedType:CheckedType.values()){
                  for(var collectionType:DataType.values()){
                    for(var inputType:collectionType.mayBeAddedTo()) {
                      for(var functionCallType:FunctionCallType.values()) {
                        if(inputType!=DataType.REF || !functionCallType.boxed) {
                          for(var initCapacity:INIT_CAPACITIES) {
                            for(var position:POSITIONS){
                              if(position>=0) {
                                TestExecutorService.submitTest(()->{
                                  var seqMonitor=new ArrListMonitor(checkedType,collectionType,initCapacity);
                                  var itrMonitor=seqMonitor.getMonitoredListIterator();
                                  for(int i=0;;) {
                                    itrMonitor.verifyAdd(inputType.convertValUnchecked(i),inputType,functionCallType);
                                    if(++i==1000) {
                                      break;
                                    }
                                    double dI=(double)i;
                                    double currPosition;
                                    while((currPosition=(double)itrMonitor.nextIndex()/dI)<position && itrMonitor.hasNext()) {
                                      itrMonitor.iterateForward();
                                    }
                                    while(currPosition>position && itrMonitor.hasPrevious()) {
                                      itrMonitor.iterateReverse();
                                      currPosition=(double)itrMonitor.nextIndex()/dI;
                                    }
                                  }
                                  if(checkedType.checked) {
                                    itrMonitor.illegalMod(IllegalModification.ModCollection);
                                    Assertions.assertThrows(IllegalModification.ModCollection.expectedException,()->itrMonitor.verifyAdd(inputType.convertValUnchecked(1000),inputType,functionCallType));
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
            
        
      
      TestExecutorService.completeAllTests("ArrListTest.testListItradd_val");
  
    }
    @Test
    public void testlastIndexOf_val(){
        final QueryTest test=(monitor,queryVal,inputType,castType,modification,monitoredObjectGen,position,seqSize)->{
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
        for(final var collectionType:DataType.values()){
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
        for(final var collectionType:DataType.values()){
            for(final var checkedType:CheckedType.values()){
                for(final int size:FIB_SEQ){
                    for(final var inputType:collectionType.mayBeAddedTo()){
                        for(final var functionCallType:FunctionCallType.values()){
                            if(inputType != DataType.REF || !functionCallType.boxed){
                                TestExecutorService.submitTest(()->{
                                    final var monitor=SequenceInitialization.Ascending
                                            .initialize(new ArrListMonitor(checkedType,collectionType,size),size,0);
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
        final MonitoredFunctionGenTest test=(monitor,functionGen)->monitor.verifyReadAndWrite(functionGen);
        test.runAllTests("ArrListTest.testReadAndWrite");
    }
    @Test
    public void testremoveIf_Predicate(){
        for(final var filterGen:StructType.ArrList.validMonitoredRemoveIfPredicateGens){
            for(final int size:EXTENDED_FIB_SEQ){
                for(final var checkedType:CheckedType.values()){
                    if(size == 0 || checkedType.checked || filterGen.expectedException == null){
                        for(final var collectionType:DataType.values()){
                            final int initValBound=collectionType == DataType.BOOLEAN?1:0;
                            for(final var functionCallType:FunctionCallType.values()){
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
                                            for(final var threshold:thresholdArr){
                                                TestExecutorService.submitTest(()->{
                                                    final var monitor=SequenceInitialization.Ascending.initialize(
                                                            new ArrListMonitor(checkedType,collectionType,size),size,
                                                            initVal);
                                                    final var filter=filterGen.getMonitoredRemoveIfPredicate(monitor,
                                                            threshold,randSeed);
                                                    if(filterGen.expectedException == null || size == 0){
                                                        monitor.verifyRemoveIf(filter,functionCallType);
                                                    }else{
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
        final QueryTest test=(monitor,queryVal,inputType,castType,modification,monitoredObjectGen,position,seqSize)->{
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
        for(final var collectionType:DataType.values()){
            for(final var checkedType:CheckedType.values()){
                for(final int size:FIB_SEQ){
                    for(final var functionCallType:FunctionCallType.values()){
                        if(collectionType != DataType.REF || !functionCallType.boxed){
                            TestExecutorService.submitTest(()->{
                                final var monitor=SequenceInitialization.Ascending
                                        .initialize(new ArrListMonitor(checkedType,collectionType,size),size,0);
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
    @Test
    public void testsize_void(){
        final BasicTest test=(monitor)->monitor.verifySize();
        test.runAllTests("ArrListTest.testsize_void");
    }
    @Test
    public void testtoArray_IntFunction(){
        final MonitoredFunctionGenTest test=(monitor,functionGen)->{
            if(functionGen.expectedException == null){
                monitor.verifyToArray(functionGen);
            }else{
                Assertions.assertThrows(functionGen.expectedException,()->monitor.verifyToArray(functionGen));
            }
        };
        test.runAllTests("ArrListTest.testtoArray_IntFunction");
    }
    @Test
    public void testtoArray_ObjectArray(){
        for(int tmpSize=0;tmpSize <= 15;tmpSize+=5){
            final int size=tmpSize;
            for(int tmpArrSize=0,tmpArrSizeBound=tmpSize + 5;tmpArrSize <= tmpArrSizeBound;++tmpArrSize){
                final int arrSize=tmpArrSize;
                for(final var collectionType:DataType.values()){
                    for(final var checkedType:CheckedType.values()){
                        TestExecutorService.submitTest(()->SequenceInitialization.Ascending
                                .initialize(new ArrListMonitor(checkedType,collectionType,size),size,0)
                                .verifyToArray(new Object[arrSize]));
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("ArrListTest.testtoArray_ObjectArray");
    }
    @Test
    public void testtoArray_void(){
        for(final var collectionType:DataType.values()){
            for(final var checkedType:CheckedType.values()){
                for(final int size:FIB_SEQ){
                    TestExecutorService.submitTest(()->{
                        final var monitor=SequenceInitialization.Ascending
                                .initialize(new ArrListMonitor(checkedType,collectionType,size),size,0);
                        for(final var outputType:collectionType.validOutputTypes()){
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
        final ToStringAndHashCodeTest test=(size,collectionType,checkedType,initVal,objGen)->{
            if(collectionType == DataType.REF && objGen.expectedException != null && size != 0){
                final var throwSwitch=new MonitoredObjectGen.ThrowSwitch();
                final var monitor=SequenceInitialization.Ascending.initializeWithMonitoredObj(
                        new ArrListMonitor(checkedType,collectionType,size),size,initVal,objGen,throwSwitch);
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
    implements
    MonitoredList<OmniIterator<?>,OmniListIterator<?>,OmniList<?>>{
        public ArrListMonitor(CheckedType checkedType,DataType dataType){
            super(checkedType,dataType);
        }
        public ArrListMonitor(CheckedType checkedType,DataType dataType,int initCap){
            super(checkedType,dataType,initCap);
        }
        @Override
        public int findRemoveValIndex(Object inputVal,DataType inputType){
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
                        return i;
                    }
                }
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
                        return i;
                    }
                }
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
                        return i;
                    }
                }
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
                        return i;
                    }
                }
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
                        return i;
                    }
                }
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
                        return i;
                    }
                }
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
                            return i;
                        }
                    }
                }else{
                    for(int i=0;;++i){
                        float v;
                        if((v=expectedArr[i]) != v){
                            return i;
                        }
                    }
                }
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
                            return i;
                        }
                    }
                }else{
                    for(int i=0;;++i){
                        double v;
                        if((v=expectedArr[i]) != v){
                            return i;
                        }
                    }
                }
            }
            case REF:{
                final var expectedArr=(Object[])this.expectedArr;
                if(inputVal == null){
                    for(int i=0;;++i){
                        if(expectedArr[i] == null){
                            return i;
                        }
                    }
                }else{
                    for(int i=0;;++i){
                        if(inputVal.equals(expectedArr[i])){
                            return i;
                        }
                    }
                }
            }
            default:
                throw dataType.invalid();
            }
        }
        @Override
        public MonitoredIterator<? extends OmniIterator<?>,OmniList<?>> getMonitoredIterator(){
            var itr=seq.iterator();
            if(checkedType.checked){
                return new CheckedItrMonitor(itr,0,expectedModCount);
            }
            return new UncheckedItrMonitor(itr,0);
        }
        @Override
        public MonitoredIterator<? extends OmniIterator<?>,OmniList<?>> getMonitoredIterator(int index,
                IteratorType itrType){
            switch(itrType){
            case AscendingItr:
                var itr=getMonitoredIterator();
                while(--index >= 0 && itr.hasNext()){
                    itr.iterateForward();
                }
                return itr;
            case BidirectionalItr:{
                return getMonitoredListIterator(index);
            }
            default:
                throw itrType.invalid();
            }
        }
        @Override
        public MonitoredIterator<? extends OmniIterator<?>,OmniList<?>> getMonitoredIterator(IteratorType itrType){
            switch(itrType){
            case AscendingItr:
                return getMonitoredIterator();
            case BidirectionalItr:{
                return getMonitoredListIterator();
            }
            default:
                throw itrType.invalid();
            }
        }
        @Override
        public MonitoredListIterator<? extends OmniListIterator<?>,OmniList<?>> getMonitoredListIterator(){
            var itr=seq.listIterator();
            if(checkedType.checked){
                return new CheckedListItrMonitor(itr,0,expectedModCount);
            }
            return new UncheckedListItrMonitor(itr,0);
        }
        @Override
        public MonitoredListIterator<? extends OmniListIterator<?>,OmniList<?>> getMonitoredListIterator(int index){
            var itr=seq.listIterator(index);
            if(checkedType.checked){
                return new CheckedListItrMonitor(itr,index,expectedModCount);
            }
            return new UncheckedListItrMonitor(itr,index);
        }
        @Override
        public StructType getStructType(){
            return StructType.ArrList;
        }
        @Override
        public void modCollection(){
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
        @Override
        public void verifyPutResult(int index,Object input,DataType inputType){
            final Object expectedVal=dataType.convertValUnchecked(inputType,input);
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
        @Override
        OmniList<?> initSeq(){
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
        @Override
        OmniList<?> initSeq(int initCap){
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
        @Override
        void updateModCount(){
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
        @Override
        void verifyCloneTypeAndModCount(Object clone){
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
        @Override
        void verifyModCount(){
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
        private abstract class AbstractItrMonitor<ITR extends OmniIterator<?>>
        implements
        MonitoredIterator<ITR,OmniList<?>>{
            final ITR itr;
            int expectedCursor;
            AbstractItrMonitor(ITR itr,int expectedCursor){
                this.itr=itr;
                this.expectedCursor=expectedCursor;
            }
            @Override
            public ITR getIterator(){
                return this.itr;
            }
            @Override
            public IteratorType getIteratorType(){
                return IteratorType.AscendingItr;
            }
            @Override
            public MonitoredCollection<OmniList<?>> getMonitoredCollection(){
                return ArrListMonitor.this;
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
            public void verifyNextResult(DataType outputType,Object result){
                verifyGetResult(expectedCursor - 1,result,outputType);
            }
            int verifyForEachRemainingHelper(MonitoredFunction function,int expectedLastRet){
                int expectedCursor=this.expectedCursor;
                final int expectedSize=ArrListMonitor.this.expectedSize;
                final var functionItr=function.iterator();
                switch(dataType){
                case BOOLEAN:{
                    final var expectedArr=(boolean[])ArrListMonitor.this.expectedArr;
                    while(expectedCursor < expectedSize){
                        Assertions.assertEquals(expectedArr[expectedCursor],(boolean)functionItr.next());
                        expectedLastRet=expectedCursor++;
                    }
                    break;
                }
                case BYTE:{
                    final var expectedArr=(byte[])ArrListMonitor.this.expectedArr;
                    while(expectedCursor < expectedSize){
                        Assertions.assertEquals(expectedArr[expectedCursor],(byte)functionItr.next());
                        expectedLastRet=expectedCursor++;
                    }
                    break;
                }
                case CHAR:{
                    final var expectedArr=(char[])ArrListMonitor.this.expectedArr;
                    while(expectedCursor < expectedSize){
                        Assertions.assertEquals(expectedArr[expectedCursor],(char)functionItr.next());
                        expectedLastRet=expectedCursor++;
                    }
                    break;
                }
                case SHORT:{
                    final var expectedArr=(short[])ArrListMonitor.this.expectedArr;
                    while(expectedCursor < expectedSize){
                        Assertions.assertEquals(expectedArr[expectedCursor],(short)functionItr.next());
                        expectedLastRet=expectedCursor++;
                    }
                    break;
                }
                case INT:{
                    final var expectedArr=(int[])ArrListMonitor.this.expectedArr;
                    while(expectedCursor < expectedSize){
                        Assertions.assertEquals(expectedArr[expectedCursor],(int)functionItr.next());
                        expectedLastRet=expectedCursor++;
                    }
                    break;
                }
                case LONG:{
                    final var expectedArr=(long[])ArrListMonitor.this.expectedArr;
                    while(expectedCursor < expectedSize){
                        Assertions.assertEquals(expectedArr[expectedCursor],(long)functionItr.next());
                        expectedLastRet=expectedCursor++;
                    }
                    break;
                }
                case FLOAT:{
                    final var expectedArr=(float[])ArrListMonitor.this.expectedArr;
                    while(expectedCursor < expectedSize){
                        Assertions.assertEquals(expectedArr[expectedCursor],(float)functionItr.next());
                        expectedLastRet=expectedCursor++;
                    }
                    break;
                }
                case DOUBLE:{
                    final var expectedArr=(double[])ArrListMonitor.this.expectedArr;
                    while(expectedCursor < expectedSize){
                        Assertions.assertEquals(expectedArr[expectedCursor],(double)functionItr.next());
                        expectedLastRet=expectedCursor++;
                    }
                    break;
                }
                case REF:{
                    final var expectedArr=(Object[])ArrListMonitor.this.expectedArr;
                    while(expectedCursor < expectedSize){
                        Assertions.assertSame(expectedArr[expectedCursor],functionItr.next());
                        expectedLastRet=expectedCursor++;
                    }
                    break;
                }
                default:
                    throw dataType.invalid();
                }
                this.expectedCursor=expectedCursor;
                return expectedLastRet;
            }
        }
        private abstract class AbstractListItrMonitor extends AbstractItrMonitor<OmniListIterator<?>>
        implements
        MonitoredListIterator<OmniListIterator<?>,OmniList<?>>{
            int expectedLastRet;
            AbstractListItrMonitor(OmniListIterator<?> itr,int expectedCursor){
                super(itr,expectedCursor);
                expectedLastRet=-1;
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
            public void updateItrNextState(){
                expectedLastRet=expectedCursor++;
            }
            @Override
            public void updateItrPreviousState(){
                expectedLastRet=--expectedCursor;
            }
            @Override
            public void verifyForEachRemaining(MonitoredFunction function){
                expectedLastRet=super.verifyForEachRemainingHelper(function,expectedLastRet);
            }
            @Override
            public void verifyPreviousResult(DataType outputType,Object result){
                verifyGetResult(expectedLastRet,result,outputType);
            }
            @Override
            public void updateItrSetState(Object input,DataType inputType){
              ArrListMonitor.this.verifyPutResult(expectedLastRet,input,inputType);
            }
        }
        private class CheckedItrMonitor extends AbstractItrMonitor<OmniIterator<?>>{
            int expectedItrModCount;
            int expectedLastRet;
            CheckedItrMonitor(OmniIterator<?> itr,int expectedCursor,int expectedItrModCount){
                super(itr,expectedCursor);
                expectedLastRet=-1;
                this.expectedItrModCount=expectedItrModCount;
            }
            @Override
            public void updateItrNextState(){
                expectedLastRet=expectedCursor++;
            }
            @Override
            public void updateItrRemoveState(){
                updateRemoveIndexState(expectedCursor=expectedLastRet);
                ++expectedItrModCount;
                expectedLastRet=-1;
            }
            @Override
            public void verifyCloneHelper(Object clone){
                switch(dataType){
                case BOOLEAN:
                    Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanArrSeq.CheckedList.Itr.parent(clone));
                    Assertions.assertEquals(expectedCursor,
                            FieldAndMethodAccessor.BooleanArrSeq.CheckedList.Itr.cursor(clone));
                    Assertions.assertEquals(expectedItrModCount,
                            FieldAndMethodAccessor.BooleanArrSeq.CheckedList.Itr.modCount(clone));
                    Assertions.assertEquals(expectedLastRet,
                            FieldAndMethodAccessor.BooleanArrSeq.CheckedList.Itr.lastRet(clone));
                    break;
                case BYTE:
                    Assertions.assertSame(seq,FieldAndMethodAccessor.ByteArrSeq.CheckedList.Itr.parent(clone));
                    Assertions.assertEquals(expectedCursor,
                            FieldAndMethodAccessor.ByteArrSeq.CheckedList.Itr.cursor(clone));
                    Assertions.assertEquals(expectedItrModCount,
                            FieldAndMethodAccessor.ByteArrSeq.CheckedList.Itr.modCount(clone));
                    Assertions.assertEquals(expectedLastRet,
                            FieldAndMethodAccessor.ByteArrSeq.CheckedList.Itr.lastRet(clone));
                    break;
                case CHAR:
                    Assertions.assertSame(seq,FieldAndMethodAccessor.CharArrSeq.CheckedList.Itr.parent(clone));
                    Assertions.assertEquals(expectedCursor,
                            FieldAndMethodAccessor.CharArrSeq.CheckedList.Itr.cursor(clone));
                    Assertions.assertEquals(expectedItrModCount,
                            FieldAndMethodAccessor.CharArrSeq.CheckedList.Itr.modCount(clone));
                    Assertions.assertEquals(expectedLastRet,
                            FieldAndMethodAccessor.CharArrSeq.CheckedList.Itr.lastRet(clone));
                    break;
                case DOUBLE:
                    Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleArrSeq.CheckedList.Itr.parent(clone));
                    Assertions.assertEquals(expectedCursor,
                            FieldAndMethodAccessor.DoubleArrSeq.CheckedList.Itr.cursor(clone));
                    Assertions.assertEquals(expectedItrModCount,
                            FieldAndMethodAccessor.DoubleArrSeq.CheckedList.Itr.modCount(clone));
                    Assertions.assertEquals(expectedLastRet,
                            FieldAndMethodAccessor.DoubleArrSeq.CheckedList.Itr.lastRet(clone));
                    break;
                case FLOAT:
                    Assertions.assertSame(seq,FieldAndMethodAccessor.FloatArrSeq.CheckedList.Itr.parent(clone));
                    Assertions.assertEquals(expectedCursor,
                            FieldAndMethodAccessor.FloatArrSeq.CheckedList.Itr.cursor(clone));
                    Assertions.assertEquals(expectedItrModCount,
                            FieldAndMethodAccessor.FloatArrSeq.CheckedList.Itr.modCount(clone));
                    Assertions.assertEquals(expectedLastRet,
                            FieldAndMethodAccessor.FloatArrSeq.CheckedList.Itr.lastRet(clone));
                    break;
                case INT:
                    Assertions.assertSame(seq,FieldAndMethodAccessor.IntArrSeq.CheckedList.Itr.parent(clone));
                    Assertions.assertEquals(expectedCursor,
                            FieldAndMethodAccessor.IntArrSeq.CheckedList.Itr.cursor(clone));
                    Assertions.assertEquals(expectedItrModCount,
                            FieldAndMethodAccessor.IntArrSeq.CheckedList.Itr.modCount(clone));
                    Assertions.assertEquals(expectedLastRet,
                            FieldAndMethodAccessor.IntArrSeq.CheckedList.Itr.lastRet(clone));
                    break;
                case LONG:
                    Assertions.assertSame(seq,FieldAndMethodAccessor.LongArrSeq.CheckedList.Itr.parent(clone));
                    Assertions.assertEquals(expectedCursor,
                            FieldAndMethodAccessor.LongArrSeq.CheckedList.Itr.cursor(clone));
                    Assertions.assertEquals(expectedItrModCount,
                            FieldAndMethodAccessor.LongArrSeq.CheckedList.Itr.modCount(clone));
                    Assertions.assertEquals(expectedLastRet,
                            FieldAndMethodAccessor.LongArrSeq.CheckedList.Itr.lastRet(clone));
                    break;
                case REF:
                    Assertions.assertSame(seq,FieldAndMethodAccessor.RefArrSeq.CheckedList.Itr.parent(clone));
                    Assertions.assertEquals(expectedCursor,
                            FieldAndMethodAccessor.RefArrSeq.CheckedList.Itr.cursor(clone));
                    Assertions.assertEquals(expectedItrModCount,
                            FieldAndMethodAccessor.RefArrSeq.CheckedList.Itr.modCount(clone));
                    Assertions.assertEquals(expectedLastRet,
                            FieldAndMethodAccessor.RefArrSeq.CheckedList.Itr.lastRet(clone));
                    break;
                case SHORT:
                    Assertions.assertSame(seq,FieldAndMethodAccessor.ShortArrSeq.CheckedList.Itr.parent(clone));
                    Assertions.assertEquals(expectedCursor,
                            FieldAndMethodAccessor.ShortArrSeq.CheckedList.Itr.cursor(clone));
                    Assertions.assertEquals(expectedItrModCount,
                            FieldAndMethodAccessor.ShortArrSeq.CheckedList.Itr.modCount(clone));
                    Assertions.assertEquals(expectedLastRet,
                            FieldAndMethodAccessor.ShortArrSeq.CheckedList.Itr.lastRet(clone));
                    break;
                default:
                    throw dataType.invalid();
                }
            }
            @Override
            public void verifyForEachRemaining(MonitoredFunction function){
                expectedLastRet=super.verifyForEachRemainingHelper(function,expectedLastRet);
            }
            @Override
            public boolean nextWasJustCalled(){
                return expectedLastRet != -1;
            }
        }
        private class CheckedListItrMonitor extends AbstractListItrMonitor{
            int expectedItrModCount;
            CheckedListItrMonitor(OmniListIterator<?> itr,int expectedCursor,int expectedItrModCount){
                super(itr,expectedCursor);
                this.expectedItrModCount=expectedItrModCount;
            }
            @Override
            public void updateItrAddState(Object input,DataType inputType){
                ArrListMonitor.this.updateAddState(expectedCursor++,input,inputType);
                ++expectedItrModCount;
                expectedLastRet=-1;
            }
            @Override
            public void updateItrRemoveState(){
                updateRemoveIndexState(expectedCursor=expectedLastRet);
                ++expectedItrModCount;
                expectedLastRet=-1;
            }
            @Override
            public void verifyCloneHelper(Object clone){
                switch(dataType){
                case BOOLEAN:
                    Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanArrSeq.CheckedList.ListItr.parent(clone));
                    Assertions.assertEquals(expectedCursor,
                            FieldAndMethodAccessor.BooleanArrSeq.CheckedList.ListItr.cursor(clone));
                    Assertions.assertEquals(expectedItrModCount,
                            FieldAndMethodAccessor.BooleanArrSeq.CheckedList.ListItr.modCount(clone));
                    Assertions.assertEquals(expectedLastRet,
                            FieldAndMethodAccessor.BooleanArrSeq.CheckedList.ListItr.lastRet(clone));
                    break;
                case BYTE:
                    Assertions.assertSame(seq,FieldAndMethodAccessor.ByteArrSeq.CheckedList.ListItr.parent(clone));
                    Assertions.assertEquals(expectedCursor,
                            FieldAndMethodAccessor.ByteArrSeq.CheckedList.ListItr.cursor(clone));
                    Assertions.assertEquals(expectedItrModCount,
                            FieldAndMethodAccessor.ByteArrSeq.CheckedList.ListItr.modCount(clone));
                    Assertions.assertEquals(expectedLastRet,
                            FieldAndMethodAccessor.ByteArrSeq.CheckedList.ListItr.lastRet(clone));
                    break;
                case CHAR:
                    Assertions.assertSame(seq,FieldAndMethodAccessor.CharArrSeq.CheckedList.ListItr.parent(clone));
                    Assertions.assertEquals(expectedCursor,
                            FieldAndMethodAccessor.CharArrSeq.CheckedList.ListItr.cursor(clone));
                    Assertions.assertEquals(expectedItrModCount,
                            FieldAndMethodAccessor.CharArrSeq.CheckedList.ListItr.modCount(clone));
                    Assertions.assertEquals(expectedLastRet,
                            FieldAndMethodAccessor.CharArrSeq.CheckedList.ListItr.lastRet(clone));
                    break;
                case DOUBLE:
                    Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleArrSeq.CheckedList.ListItr.parent(clone));
                    Assertions.assertEquals(expectedCursor,
                            FieldAndMethodAccessor.DoubleArrSeq.CheckedList.ListItr.cursor(clone));
                    Assertions.assertEquals(expectedItrModCount,
                            FieldAndMethodAccessor.DoubleArrSeq.CheckedList.ListItr.modCount(clone));
                    Assertions.assertEquals(expectedLastRet,
                            FieldAndMethodAccessor.DoubleArrSeq.CheckedList.ListItr.lastRet(clone));
                    break;
                case FLOAT:
                    Assertions.assertSame(seq,FieldAndMethodAccessor.FloatArrSeq.CheckedList.ListItr.parent(clone));
                    Assertions.assertEquals(expectedCursor,
                            FieldAndMethodAccessor.FloatArrSeq.CheckedList.ListItr.cursor(clone));
                    Assertions.assertEquals(expectedItrModCount,
                            FieldAndMethodAccessor.FloatArrSeq.CheckedList.ListItr.modCount(clone));
                    Assertions.assertEquals(expectedLastRet,
                            FieldAndMethodAccessor.FloatArrSeq.CheckedList.ListItr.lastRet(clone));
                    break;
                case INT:
                    Assertions.assertSame(seq,FieldAndMethodAccessor.IntArrSeq.CheckedList.ListItr.parent(clone));
                    Assertions.assertEquals(expectedCursor,
                            FieldAndMethodAccessor.IntArrSeq.CheckedList.ListItr.cursor(clone));
                    Assertions.assertEquals(expectedItrModCount,
                            FieldAndMethodAccessor.IntArrSeq.CheckedList.ListItr.modCount(clone));
                    Assertions.assertEquals(expectedLastRet,
                            FieldAndMethodAccessor.IntArrSeq.CheckedList.ListItr.lastRet(clone));
                    break;
                case LONG:
                    Assertions.assertSame(seq,FieldAndMethodAccessor.LongArrSeq.CheckedList.ListItr.parent(clone));
                    Assertions.assertEquals(expectedCursor,
                            FieldAndMethodAccessor.LongArrSeq.CheckedList.ListItr.cursor(clone));
                    Assertions.assertEquals(expectedItrModCount,
                            FieldAndMethodAccessor.LongArrSeq.CheckedList.ListItr.modCount(clone));
                    Assertions.assertEquals(expectedLastRet,
                            FieldAndMethodAccessor.LongArrSeq.CheckedList.ListItr.lastRet(clone));
                    break;
                case REF:
                    Assertions.assertSame(seq,FieldAndMethodAccessor.RefArrSeq.CheckedList.ListItr.parent(clone));
                    Assertions.assertEquals(expectedCursor,
                            FieldAndMethodAccessor.RefArrSeq.CheckedList.ListItr.cursor(clone));
                    Assertions.assertEquals(expectedItrModCount,
                            FieldAndMethodAccessor.RefArrSeq.CheckedList.ListItr.modCount(clone));
                    Assertions.assertEquals(expectedLastRet,
                            FieldAndMethodAccessor.RefArrSeq.CheckedList.ListItr.lastRet(clone));
                    break;
                case SHORT:
                    Assertions.assertSame(seq,FieldAndMethodAccessor.ShortArrSeq.CheckedList.ListItr.parent(clone));
                    Assertions.assertEquals(expectedCursor,
                            FieldAndMethodAccessor.ShortArrSeq.CheckedList.ListItr.cursor(clone));
                    Assertions.assertEquals(expectedItrModCount,
                            FieldAndMethodAccessor.ShortArrSeq.CheckedList.ListItr.modCount(clone));
                    Assertions.assertEquals(expectedLastRet,
                            FieldAndMethodAccessor.ShortArrSeq.CheckedList.ListItr.lastRet(clone));
                    break;
                default:
                    throw dataType.invalid();
                }
            }
            @Override
            public boolean previousWasJustCalled(){
                return expectedLastRet == expectedCursor;
            }
            @Override
            public boolean nextWasJustCalled(){
                return expectedLastRet != -1 && expectedLastRet == expectedCursor - 1;
            }
        }
        private class UncheckedItrMonitor extends AbstractItrMonitor<OmniIterator<?>>{
            int lastRetState=-1;
            UncheckedItrMonitor(OmniIterator<?> itr,int expectedCursor){
                super(itr,expectedCursor);
            }
            @Override
            public void updateItrNextState(){
                lastRetState=expectedCursor++;
            }
            @Override
            public void updateItrRemoveState(){
                updateRemoveIndexState(--expectedCursor);
                lastRetState=-1;
            }
            @Override
            public void verifyCloneHelper(Object clone){
                switch(dataType){
                case BOOLEAN:
                    Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanArrSeq.UncheckedList.Itr.parent(clone));
                    Assertions.assertEquals(expectedCursor,
                            FieldAndMethodAccessor.BooleanArrSeq.UncheckedList.Itr.cursor(clone));
                    break;
                case BYTE:
                    Assertions.assertSame(seq,FieldAndMethodAccessor.ByteArrSeq.UncheckedList.Itr.parent(clone));
                    Assertions.assertEquals(expectedCursor,
                            FieldAndMethodAccessor.ByteArrSeq.UncheckedList.Itr.cursor(clone));
                    break;
                case CHAR:
                    Assertions.assertSame(seq,FieldAndMethodAccessor.CharArrSeq.UncheckedList.Itr.parent(clone));
                    Assertions.assertEquals(expectedCursor,
                            FieldAndMethodAccessor.CharArrSeq.UncheckedList.Itr.cursor(clone));
                    break;
                case DOUBLE:
                    Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleArrSeq.UncheckedList.Itr.parent(clone));
                    Assertions.assertEquals(expectedCursor,
                            FieldAndMethodAccessor.DoubleArrSeq.UncheckedList.Itr.cursor(clone));
                    break;
                case FLOAT:
                    Assertions.assertSame(seq,FieldAndMethodAccessor.FloatArrSeq.UncheckedList.Itr.parent(clone));
                    Assertions.assertEquals(expectedCursor,
                            FieldAndMethodAccessor.FloatArrSeq.UncheckedList.Itr.cursor(clone));
                    break;
                case INT:
                    Assertions.assertSame(seq,FieldAndMethodAccessor.IntArrSeq.UncheckedList.Itr.parent(clone));
                    Assertions.assertEquals(expectedCursor,
                            FieldAndMethodAccessor.IntArrSeq.UncheckedList.Itr.cursor(clone));
                    break;
                case LONG:
                    Assertions.assertSame(seq,FieldAndMethodAccessor.LongArrSeq.UncheckedList.Itr.parent(clone));
                    Assertions.assertEquals(expectedCursor,
                            FieldAndMethodAccessor.LongArrSeq.UncheckedList.Itr.cursor(clone));
                    break;
                case REF:
                    Assertions.assertSame(seq,FieldAndMethodAccessor.RefArrSeq.UncheckedList.Itr.parent(clone));
                    Assertions.assertEquals(expectedCursor,
                            FieldAndMethodAccessor.RefArrSeq.UncheckedList.Itr.cursor(clone));
                    break;
                case SHORT:
                    Assertions.assertSame(seq,FieldAndMethodAccessor.ShortArrSeq.UncheckedList.Itr.parent(clone));
                    Assertions.assertEquals(expectedCursor,
                            FieldAndMethodAccessor.ShortArrSeq.UncheckedList.Itr.cursor(clone));
                    break;
                default:
                    throw dataType.invalid();
                }
            }
            @Override
            public void verifyForEachRemaining(MonitoredFunction function){
                lastRetState=super.verifyForEachRemainingHelper(function,lastRetState);
            }
            @Override
            public boolean nextWasJustCalled(){
                return lastRetState != -1;
            }
        }
        private class UncheckedListItrMonitor extends AbstractListItrMonitor{
            int lastRetState=-1;
            UncheckedListItrMonitor(OmniListIterator<?> itr,int expectedCursor){
                super(itr,expectedCursor);
            }
            @Override
            public void updateItrAddState(Object input,DataType inputType){
                ArrListMonitor.this.updateAddState(expectedCursor++,input,inputType);
                lastRetState=-1;
            }
            @Override
            public void updateItrRemoveState(){
                updateRemoveIndexState(expectedCursor=expectedLastRet);
                lastRetState=-1;
            }
            @Override
            public void verifyCloneHelper(Object clone){
                switch(dataType){
                case BOOLEAN:
                    Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanArrSeq.UncheckedList.ListItr.parent(clone));
                    Assertions.assertEquals(expectedCursor,
                            FieldAndMethodAccessor.BooleanArrSeq.UncheckedList.ListItr.cursor(clone));
                    Assertions.assertEquals(expectedLastRet,
                            FieldAndMethodAccessor.BooleanArrSeq.UncheckedList.ListItr.lastRet(clone));
                    break;
                case BYTE:
                    Assertions.assertSame(seq,FieldAndMethodAccessor.ByteArrSeq.UncheckedList.ListItr.parent(clone));
                    Assertions.assertEquals(expectedCursor,
                            FieldAndMethodAccessor.ByteArrSeq.UncheckedList.ListItr.cursor(clone));
                    Assertions.assertEquals(expectedLastRet,
                            FieldAndMethodAccessor.ByteArrSeq.UncheckedList.ListItr.lastRet(clone));
                    break;
                case CHAR:
                    Assertions.assertSame(seq,FieldAndMethodAccessor.CharArrSeq.UncheckedList.ListItr.parent(clone));
                    Assertions.assertEquals(expectedCursor,
                            FieldAndMethodAccessor.CharArrSeq.UncheckedList.ListItr.cursor(clone));
                    Assertions.assertEquals(expectedLastRet,
                            FieldAndMethodAccessor.CharArrSeq.UncheckedList.ListItr.lastRet(clone));
                    break;
                case DOUBLE:
                    Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleArrSeq.UncheckedList.ListItr.parent(clone));
                    Assertions.assertEquals(expectedCursor,
                            FieldAndMethodAccessor.DoubleArrSeq.UncheckedList.ListItr.cursor(clone));
                    Assertions.assertEquals(expectedLastRet,
                            FieldAndMethodAccessor.DoubleArrSeq.UncheckedList.ListItr.lastRet(clone));
                    break;
                case FLOAT:
                    Assertions.assertSame(seq,FieldAndMethodAccessor.FloatArrSeq.UncheckedList.ListItr.parent(clone));
                    Assertions.assertEquals(expectedCursor,
                            FieldAndMethodAccessor.FloatArrSeq.UncheckedList.ListItr.cursor(clone));
                    Assertions.assertEquals(expectedLastRet,
                            FieldAndMethodAccessor.FloatArrSeq.UncheckedList.ListItr.lastRet(clone));
                    break;
                case INT:
                    Assertions.assertSame(seq,FieldAndMethodAccessor.IntArrSeq.UncheckedList.ListItr.parent(clone));
                    Assertions.assertEquals(expectedCursor,
                            FieldAndMethodAccessor.IntArrSeq.UncheckedList.ListItr.cursor(clone));
                    Assertions.assertEquals(expectedLastRet,
                            FieldAndMethodAccessor.IntArrSeq.UncheckedList.ListItr.lastRet(clone));
                    break;
                case LONG:
                    Assertions.assertSame(seq,FieldAndMethodAccessor.LongArrSeq.UncheckedList.ListItr.parent(clone));
                    Assertions.assertEquals(expectedCursor,
                            FieldAndMethodAccessor.LongArrSeq.UncheckedList.ListItr.cursor(clone));
                    Assertions.assertEquals(expectedLastRet,
                            FieldAndMethodAccessor.LongArrSeq.UncheckedList.ListItr.lastRet(clone));
                    break;
                case REF:
                    Assertions.assertSame(seq,FieldAndMethodAccessor.RefArrSeq.UncheckedList.ListItr.parent(clone));
                    Assertions.assertEquals(expectedCursor,
                            FieldAndMethodAccessor.RefArrSeq.UncheckedList.ListItr.cursor(clone));
                    Assertions.assertEquals(expectedLastRet,
                            FieldAndMethodAccessor.RefArrSeq.UncheckedList.ListItr.lastRet(clone));
                    break;
                case SHORT:
                    Assertions.assertSame(seq,FieldAndMethodAccessor.ShortArrSeq.UncheckedList.ListItr.parent(clone));
                    Assertions.assertEquals(expectedCursor,
                            FieldAndMethodAccessor.ShortArrSeq.UncheckedList.ListItr.cursor(clone));
                    Assertions.assertEquals(expectedLastRet,
                            FieldAndMethodAccessor.ShortArrSeq.UncheckedList.ListItr.lastRet(clone));
                    break;
                default:
                    throw dataType.invalid();
                }
            }
            @Override
            public void updateItrNextState(){
                expectedLastRet=expectedCursor++;
                lastRetState=0;
            }
            @Override
            public void updateItrPreviousState(){
                expectedLastRet=--expectedCursor;
                lastRetState=1;
            }
            @Override
            public void verifyForEachRemaining(MonitoredFunction function){
                int expectedLastRet=this.expectedLastRet;
                int newLastRet=super.verifyForEachRemainingHelper(function,expectedLastRet);
                if(expectedLastRet != newLastRet){
                    this.lastRetState=0;
                    this.expectedLastRet=newLastRet;
                }

            }
            @Override
            public boolean previousWasJustCalled(){
                return lastRetState == 1;
            }
            @Override
            public boolean nextWasJustCalled(){
                return lastRetState == 0;
            }
        }
    }
    private static interface BasicTest{
        default void runAllTests(String testName){
            for(final int seqSize:FIB_SEQ){
                for(final var dataType:DataType.values()){
                    for(final var checkedType:CheckedType.values()){
                        for(final var initCap:INIT_CAPACITIES){
                            TestExecutorService.submitTest(()->runTest(SequenceInitialization.Ascending
                                    .initialize(new ArrListMonitor(checkedType,dataType,initCap),seqSize,0)));
                        }
                    }
                }
            }
            TestExecutorService.completeAllTests("ArrListTest.testsize_void");
        }
        void runTest(ArrListMonitor monitor);
    }
    private static interface MonitoredFunctionGenTest{
        void runTest(ArrListMonitor monitor,MonitoredFunctionGen functionGen);
        private void runAllTests(String testName){
            for(final var size:FIB_SEQ){
                for(final var functionGen:StructType.ArrList.validMonitoredFunctionGens){
                    for(final var checkedType:CheckedType.values()){
                        if(checkedType.checked || functionGen.expectedException == null){
                            for(final var collectionType:DataType.values()){
                                final int initValCap=collectionType == DataType.BOOLEAN && size != 0?1:0;
                                IntStream.rangeClosed(0,initValCap).forEach(initVal->{
                                    for(final var initCap:INIT_CAPACITIES){
                                        TestExecutorService.submitTest(()->runTest(SequenceInitialization.Ascending
                                                .initialize(new ArrListMonitor(checkedType,collectionType,initCap),size,
                                                        initVal),
                                                functionGen));
                                    }
                                });
                            }
                        }
                    }
                }
            }
            TestExecutorService.completeAllTests("ArrListTest.testReadAndWrite");
        }
    }
    private static interface QueryTest{
        void callAndVerifyResult(ArrListMonitor monitor,QueryVal queryVal,DataType inputType,QueryCastType castType,
                QueryVal.QueryValModification modification,MonitoredObjectGen monitoredObjectGen,double position,
                int seqSize);
        private void runAllTests(String testName){
            for(final var collectionType:DataType.values()){
                for(final var queryVal:QueryVal.values()){
                    if(collectionType.isValidQueryVal(queryVal)){
                        queryVal.validQueryCombos.forEach((modification,castTypesToInputTypes)->{
                            castTypesToInputTypes.forEach((castType,inputTypes)->{
                                inputTypes.forEach(inputType->{
                                    if(queryVal == QueryVal.NonNull){
                                        for(final var monitoredObjectGen:StructType.ArrList.validMonitoredObjectGens){
                                            if(monitoredObjectGen.expectedException != null){
                                                for(final var size:FIB_SEQ){
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
                                        for(final var size:FIB_SEQ){
                                            for(final var position:POSITIONS){
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
                                                for(final var checkedType:CheckedType.values()){
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
            final var monitor=new ArrListMonitor(checkedType,collectionType,seqSize);
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
    private static interface ToStringAndHashCodeTest{
        void callMethod(int size,DataType collectionType,CheckedType checkedType,int initVal,MonitoredObjectGen objGen);
        private void runAllTests(String testName){
            for(final int size:FIB_SEQ){
                for(final var collectionType:DataType.values()){
                    final int initValBound;
                    if(collectionType == DataType.BOOLEAN){
                        initValBound=1;
                    }else{
                        initValBound=0;
                    }
                    for(final var checkedType:CheckedType.values()){
                        if(collectionType == DataType.REF || !checkedType.checked){
                            IntStream.rangeClosed(0,initValBound).forEach(initVal->{
                                for(final var objGen:StructType.ArrList.validMonitoredObjectGens){
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
}
