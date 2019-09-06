package omni.impl.seq;

import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.impl.CheckedType;
import omni.impl.DataType;
import omni.impl.FunctionCallType;
import omni.impl.MonitoredFunctionGen;
import omni.impl.MonitoredRemoveIfPredicateGen;
import omni.impl.QueryCastType;
import omni.impl.QueryVal;
import omni.impl.StructType;
import omni.impl.seq.SequenceMonitors.PackedBooleanArrDeqMonitor;
import omni.util.TestExecutorService;
public class PackedBooleanArrDeqTest{
  private static final int[] SHORT_SIZES;
  private static final int[] SIZES;
  static {
      {
          IntStream.Builder builder=IntStream.builder();
          for(int wordLength:new int[] {0,1,4}) {
              int baseSize=wordLength<<6;
              for(int sizeModifier=-3;sizeModifier<=3;++sizeModifier) {
                  final int size;
                  if((size=baseSize+sizeModifier)>=0)
                  {
                      builder.accept(size);
                  }
              }
          }
          SHORT_SIZES=builder.build().toArray();
        
        SIZES=IntStream.rangeClosed(0,259).toArray();
        }
  }
  
  private static final double[] POSITIONS=new double[]{-1,0,0.25,0.5,0.75,1.0};
  private static final double[] NON_RANDOM_THRESHOLD=new double[]{0.5};
  private static final double[] RANDOM_THRESHOLDS=new double[]{0.01,0.10,0.90};


  
  private static int getExpectedFinalCapacity(int initCap,int numAdded) {
      if(numAdded==0) {
          return 0;
      }
      initCap=initCap+64&-64;
      for(int i=0;;) {
          if(++i>initCap) {
              initCap>>=6;
              initCap+=initCap>>1;
          }
          if(i==numAdded) {
              return initCap;
          }
      }
  }
  
  private static interface AddTest{
    private void runAllTests(String testName){
      for(final var checkedType:CheckedType.values()){
        for(final var functionCallType:DataType.BOOLEAN.validFunctionCalls){
          for(int tmpInitCap=0;tmpInitCap <= 512;tmpInitCap+=64){
            final int initCap=tmpInitCap;
            final int rotateBound=getExpectedFinalCapacity(initCap,1)-1;
            for(int tmpNumToRotate=0;tmpNumToRotate <= rotateBound;++tmpNumToRotate){
              final int numToRotate=tmpNumToRotate;
              for(int tmpInitVal=0;tmpInitVal<=1;++tmpInitVal) {
                final int initVal=tmpInitVal;
                TestExecutorService.submitTest(()->{
                  final var monitor=new PackedBooleanArrDeqMonitor(checkedType,initCap);
                  int val=initVal;
                  if(numToRotate != 0){
                    monitor.add(val++);
                    monitor.rotate(numToRotate);
                  }
                  for(int i=0;i < 512;++i){
                    callMethod(monitor,DataType.BOOLEAN.convertValUnchecked(val++),DataType.BOOLEAN,functionCallType);
                  }
                });
              }
             
            }
          }
        }
      }
      TestExecutorService.completeAllTests(testName);
    }
    void callMethod(PackedBooleanArrDeqMonitor monitor,Object inputVal,DataType inputType,FunctionCallType functionCallType);
  }
  
  private static interface BasicTest{
      void runTest(PackedBooleanArrDeqMonitor monitor);
      private void runAllTests(String testName,int[] sizes) {
          for(var size:sizes) {
              final int initCapBound=size+64&-64;
              final int initValBound=size==0?0:1;
              for(int tmpInitCap=0;tmpInitCap<=initCapBound;tmpInitCap+=64) {
                  final int initCap=tmpInitCap;
                  final int rotateBound=getExpectedFinalCapacity(initCap,size);
                  for(int tmpInitVal=0;tmpInitVal<=initValBound;++tmpInitVal) {
                      final int initVal=tmpInitVal;
                      for(var checkedType:CheckedType.values()) {
                          TestExecutorService.submitTest(()->{
                              final var monitor=SequenceInitialization.Ascending.initialize(new PackedBooleanArrDeqMonitor(checkedType,initCap),size,initVal);
                              for(int i=0;i<=rotateBound;++i) {
                                  monitor.rotate(1);
                                  runTest(monitor);
                              }
                          });
                      }
                  }
              }
          }
          TestExecutorService.completeAllTests(testName);
      }
      
  }

  
  
  private static interface GetTest{
    private void runAllTests(String testName,boolean throwsOnEmpty){
        for(final var outputType:DataType.BOOLEAN.validOutputTypes()){
          for(final var checkedType:CheckedType.values()){
            for(int tmpInitCap=0;tmpInitCap <=512;tmpInitCap+=64){
              final int initCap=tmpInitCap;
              final int rotateBound=getExpectedFinalCapacity(initCap,100)-1;
              
              for(int tmpNumToRotate=0;tmpNumToRotate <= rotateBound;++tmpNumToRotate){
                final int numToRotate=tmpNumToRotate;
                for(var tmpInitVal=0;tmpInitVal<=1;++tmpInitVal) {
                  final var initVal=tmpInitVal;
                  TestExecutorService.submitTest(()->{
                    final var monitor=SequenceInitialization.Ascending
                        .initialize(new PackedBooleanArrDeqMonitor(checkedType,initCap),100,initVal);
                    if(numToRotate > 0){
                      monitor.rotate(numToRotate);
                    }
                    for(int i=0;i < 100;++i){
                      processNext(monitor,outputType);
                    }
                    if(throwsOnEmpty){
                      if(checkedType.checked){
                        Assertions.assertThrows(NoSuchElementException.class,()->processNext(monitor,outputType));
                      }
                    }else{
                      processNext(monitor,outputType);
                    }
                  });
                }
                
              }
            }
          }
        }
      
      TestExecutorService.completeAllTests(testName);
    }
    void processNext(PackedBooleanArrDeqMonitor monitor,DataType outputType);
  }
 
  private static interface MonitoredFunctionTest{
      
      
      void runTest(PackedBooleanArrDeqMonitor monitor,MonitoredFunctionGen functionGen,FunctionCallType functionCallType,long randSeed);
      private void runAllTests(String testName,long randMax,int[] sizes) {
          for(var size:sizes) {
              final int initCapBound=size+64&-64;
              final int initValBound=size==0?0:1;
              for(int tmpInitCap=0;tmpInitCap<=initCapBound;tmpInitCap+=64) {
                  final int initCap=tmpInitCap;
                  final int rotateBound=getExpectedFinalCapacity(initCap,size)+64;
                  for(int tmpInitVal=0;tmpInitVal<=initValBound;++tmpInitVal) {
                      final int initVal=tmpInitVal;
                      for(var checkedType:CheckedType.values()) {
                          TestExecutorService.submitTest(()->{
                              final var monitor=SequenceInitialization.Ascending.initialize(new PackedBooleanArrDeqMonitor(checkedType,initCap),size,initVal);
                              for(int i=0;i<=rotateBound;++i) {
                                  monitor.rotate(1);
                                  for(final var functionGen:StructType.ArrDeq.validMonitoredFunctionGens){
                                      if(functionGen.expectedException == null || monitor.checkedType.checked){
                                          for(final var functionCallType:DataType.BOOLEAN.validFunctionCalls){
                                              final long randSeedBound=monitor.expectedSize > 1 && functionGen.randomized && !functionCallType.boxed?randMax:0;
                                              for(long randSeed=0;randSeed<=randSeedBound;++randSeed) {
                                                runTest(monitor,functionGen,functionCallType,randSeed);
                                              }
                                          }
                                      }
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
  

  private static interface QueryTest{
      void callAndVerifyResult(PackedBooleanArrDeqMonitor monitor,QueryVal queryVal,QueryVal.QueryValModification modification,
              QueryCastType castType,DataType inputType,int size,double position);
      private void runAllTests(String testName) {
          for(final var queryVal:QueryVal.values()){
              if(DataType.BOOLEAN.isValidQueryVal(queryVal)){
                queryVal.validQueryCombos.forEach((modification,castTypesToInputTypes)->{
                  castTypesToInputTypes.forEach((castType,inputTypes)->{
                    for(final var inputType:inputTypes){
                      final boolean queryCanReturnTrue
                          =queryVal.queryCanReturnTrue(modification,castType,inputType,DataType.BOOLEAN);
                      for(final var checkedType:CheckedType.values()){
                        for(final var size:SHORT_SIZES){
                          for(int tmpInitCap=0,initCapBound=size+64&-64;tmpInitCap <= initCapBound;tmpInitCap+=64){
                            final int initCap=tmpInitCap;
                            final int rotateBound=getExpectedFinalCapacity(initCap,size+1)-1;                              
                              for(final var position:POSITIONS){
                                if(position >= 0){
                                  if(!queryCanReturnTrue){
                                    break;
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
                                }
                                if(position>0 && (inputType!=DataType.BOOLEAN || castType!=QueryCastType.Unboxed)) {
                                  continue;
                                }
                                TestExecutorService.submitTest(()->{
                                  var monitor=  new PackedBooleanArrDeqMonitor(checkedType,initCap);
                                  if(position<0) {
                                      queryVal.initDoesNotContain(monitor.getCollection(),size,0,modification);
                                  }else {
                                      queryVal.initContains(monitor.getCollection(),size,0,position,modification);
                                  }
                                  monitor.updateCollectionState();
                                  for(int numToRotate=0;;) {
                                      callAndVerifyResult(monitor,queryVal,modification,castType,inputType,size,position);
                                      if(numToRotate>=rotateBound) {
                                          break;
                                      }
                                      monitor.rotate(1);
                                      ++numToRotate; 
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
          
          TestExecutorService.completeAllTests(testName);
        }
  }
  
  private static interface RemoveValTest{
    private void runAllTests(String testName){
        for(final var queryVal:QueryVal.values()){
          if(DataType.BOOLEAN.isValidQueryVal(queryVal)){
            queryVal.validQueryCombos.forEach((modification,castTypesToInputTypes)->{
              castTypesToInputTypes.forEach((castType,inputTypes)->{
                for(final var inputType:inputTypes){
                  final boolean queryCanReturnTrue
                      =queryVal.queryCanReturnTrue(modification,castType,inputType,DataType.BOOLEAN);
                  for(final var checkedType:CheckedType.values()){
                    for(final var size:SHORT_SIZES){
                      for(int tmpInitCap=0,initCapBound=size+64&-64;tmpInitCap <= initCapBound;tmpInitCap+=64){

                        final int initCap=tmpInitCap;
                        final int rotateBound=getExpectedFinalCapacity(initCap,size+1)-1;
                        for(int tmpNumToRotate=0;tmpNumToRotate <= rotateBound;++tmpNumToRotate){
                          final int numToRotate=tmpNumToRotate;
                          
                          for(final var position:POSITIONS){
                            if(position >= 0){
                              if(!queryCanReturnTrue){
                                break;
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
                            }
                            if(position>0 && (inputType!=DataType.BOOLEAN || castType!=QueryCastType.Unboxed)) {
                              continue;
                            }
                            TestExecutorService.submitTest(()->{
                              runTest(new PackedBooleanArrDeqMonitor(checkedType,initCap),queryVal,modification,
                                  castType,inputType,size,position,numToRotate);
                            });
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
      
      TestExecutorService.completeAllTests(testName);
    }
    private void runTest(PackedBooleanArrDeqMonitor monitor,QueryVal queryVal,
        QueryVal.QueryValModification modification,QueryCastType castType,DataType inputType,int size,double position,int numToRotate){
      if(position < 0){

          queryVal.initDoesNotContain(monitor.getCollection(),size,0,modification);
       
      }else{
          queryVal.initContains(monitor.getCollection(),size,0,position,modification);
        
      }
      monitor.updateCollectionState();
      monitor.rotate(numToRotate);
      callAndVerifyResult(monitor,queryVal,modification,castType,inputType,size,position);
    }
    void callAndVerifyResult(PackedBooleanArrDeqMonitor monitor,QueryVal queryVal,QueryVal.QueryValModification modification,
        QueryCastType castType,DataType inputType,int size,double position);
  }

  
  @Test public void testadd_val(){
    final AddTest test=(monitor,inputVal,inputType,functionCallType)->Assertions
        .assertTrue(monitor.verifyAdd(inputVal,inputType,functionCallType));
    test.runAllTests("PackedBooleanArrDeqTest.testadd_val");
  }
  
  @Test public void testaddFirst_val(){
    final AddTest test=PackedBooleanArrDeqMonitor::verifyAddFirst;
    test.runAllTests("PackedBooleanArrDeqTest.testaddFirst_val");
  }
  
  @Test public void testaddLast_val(){
    final AddTest test=PackedBooleanArrDeqMonitor::verifyAddLast;
    test.runAllTests("PackedBooleanArrDeqTest.testaddLast_val");
  }
  
  @Test public void testclear_void(){
    final BasicTest test=PackedBooleanArrDeqMonitor::verifyClear;
    test.runAllTests("PackedBooleanArrDeqTest.testclear_void",SHORT_SIZES);
  }
  
  @Test public void testclone_void(){
    final BasicTest test=PackedBooleanArrDeqMonitor::verifyClone;
    test.runAllTests("PackedBooleanArrDeqTest.testclone_void",SHORT_SIZES);
  }
   @Test public void testConstructor_int(){
    for(final var checkedType:CheckedType.values()){
        for(int tmpInitCap=0;tmpInitCap <= 512;tmpInitCap+=8){
          final int initCap=tmpInitCap;
          TestExecutorService.submitTest(()->{
            new PackedBooleanArrDeqMonitor(checkedType,initCap).verifyCollectionState();
          });
        }
      
    }
    TestExecutorService.completeAllTests("PackedBooleanArrDeqTest.testConstructor_int");
  }
  
  @Test public void testConstructor_void(){
    for(final var checkedType:CheckedType.values()){
      TestExecutorService.submitTest(()->{
        new PackedBooleanArrDeqMonitor(checkedType).verifyCollectionState();
      });
    }
    TestExecutorService.completeAllTests("PackedBooleanArrDeqTest.testConstructor_void");
  }  
  @Test public void testcontains_val(){
    final QueryTest test=(monitor,queryVal,modification,castType,inputType,size,position)->{
        Assertions.assertEquals(position >= 0,monitor.verifyContains(queryVal,inputType,castType,modification));
    };
    test.runAllTests("PackedBooleanArrDeqTest.testcontains_val");
  }
  
  @Test public void testdescendingIterator_void(){
    final BasicTest test=monitor->{
      monitor.getMonitoredDescendingIterator().verifyIteratorState();
      monitor.verifyCollectionState();
    };
    test.runAllTests("PackedBooleanArrDeqTest.testdescendingIterator_void",SHORT_SIZES);
  }
  
  @Test public void testelement_void(){
    final GetTest test=(monitor,outputType)->{
      monitor.verifyElement(outputType);
      if(!monitor.isEmpty()){
        monitor.removeFirst();
      }
    };
    test.runAllTests("PackedBooleanArrDeqTest.testelement_void",true);
  }
  
  
  

  @Test public void testforEach_Consumer(){
    final MonitoredFunctionTest test=(monitor,functionGen,functionCallType,randSeed)->{
      if(functionGen.expectedException == null || monitor.isEmpty()){
        monitor.verifyForEach(functionGen,functionCallType,randSeed);
      }else{
        Assertions.assertThrows(functionGen.expectedException,
            ()->monitor.verifyForEach(functionGen,functionCallType,randSeed));
      }
    };
    test.runAllTests("PackedBooleanArrDeqTest.testforEach_Consumer",100,SHORT_SIZES);
  }
  
  @Test public void testgetFirst_void(){
    final GetTest test=(monitor,outputType)->{
      monitor.verifyGetFirst(outputType);
      if(!monitor.isEmpty()){
        monitor.removeFirst();
      }
    };
    test.runAllTests("PackedBooleanArrDeqTest.testgetFirst_void",true);
  }
  
  @Test public void testgetLast_void(){
    final GetTest test=(monitor,outputType)->{
      monitor.verifyGetLast(outputType);
      if(!monitor.isEmpty()){
        monitor.removeLast();
      }
    };
    test.runAllTests("PackedBooleanArrDeqTest.testgetLast_void",true);
  }
  

  @Test public void testisEmpty_void(){
    final BasicTest test=PackedBooleanArrDeqMonitor::verifyIsEmpty;
    test.runAllTests("PackedBooleanArrDeqTest.testisEmpty_void",SHORT_SIZES);
  }
  
  @Test public void testiterator_void(){
    final BasicTest test=monitor->{
      monitor.getMonitoredIterator().verifyIteratorState();
      monitor.verifyCollectionState();
    };
    test.runAllTests("PackedBooleanArrDeqTest.testiterator_void",SHORT_SIZES);
  }
   @Test public void testItrclone_void(){
    final BasicTest test=monitor->{
      final int size=monitor.size();
      for(final var itrType:StructType.PackedBooleanArrDeq.validItrTypes){
        for(final var position:POSITIONS){
          if(position >= 0){
            monitor.getMonitoredIterator((int)(position * size),itrType).verifyClone();
          }
        }
      }
    };
    test.runAllTests("PackedBooleanArrDeqTest.testItrclone_void",SHORT_SIZES);
  }
  
 
  
  @Test public void testItrforEachRemaining_Consumer(){
    BasicTest test=(monitor)->{
        int prevNumToIterate=-1;
        for(final var position:POSITIONS){
            int numToIterate;
            if(position >= 0 && (numToIterate=(int)(position * monitor.expectedSize)) != prevNumToIterate){
                prevNumToIterate=numToIterate;
                final int numLeft=monitor.expectedSize - numToIterate;
                for(final var functionCallType:DataType.BOOLEAN.validFunctionCalls){
                  if(position>0 && functionCallType.boxed) {
                    continue;
                  }
                    for(final var itrType:StructType.PackedBooleanArrDeq.validItrTypes){
                        for(final var illegalMod:itrType.validPreMods){
                          if(position>0 && illegalMod.expectedException!=null) {
                            continue;
                          }
                            if(illegalMod.expectedException == null || monitor.checkedType.checked){
                                for(final var functionGen:itrType.validMonitoredFunctionGens){
                                  if(position>0 && functionGen.expectedException!=null) {
                                    continue;
                                  }
                                    if(monitor.checkedType.checked || monitor.expectedSize == 0 || functionGen.expectedException == null){
                                        final long randSeedBound=!functionCallType.boxed && numLeft > 1 && functionGen.randomized && illegalMod.expectedException == null?10:0;
                                        for(var randSeed=0;randSeed <= randSeedBound;++randSeed){
                                            final var itrMonitor=monitor.getMonitoredIterator(numToIterate,itrType);
                                            itrMonitor.illegalMod(illegalMod);
                                            if(illegalMod.expectedException == null || numLeft == 0){
                                                if(functionGen.expectedException == null || numLeft == 0){
                                                    itrMonitor.verifyForEachRemaining(functionGen,functionCallType,randSeed);
                                                }else{
                                                    final long finalRand=randSeed;
                                                    Assertions.assertThrows(functionGen.expectedException,()->itrMonitor.verifyForEachRemaining(functionGen,functionCallType,finalRand));
                                                }
                                            }else{
                                                final long finalRand=randSeed;
                                                Assertions.assertThrows(illegalMod.expectedException,()->itrMonitor.verifyForEachRemaining(functionGen,functionCallType,finalRand));
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
    };
    test.runAllTests("PackedBooleanArrDeqTest.testItrforEachRemaining_Consumer",SHORT_SIZES);
  }
  
  @Test public void testItrhasNext_void(){
    final BasicTest test=monitor->{
      for(final var itrType:StructType.PackedBooleanArrDeq.validItrTypes){
        final var itrMonitor=monitor.getMonitoredIterator(itrType);
        while(itrMonitor.verifyHasNext()){
          itrMonitor.iterateForward();
        }
      }
    };
    test.runAllTests("PackedBooleanArrDeqTest.testItrhasNext_void",SHORT_SIZES);
  }
  
  @Test public void testItrnext_void(){
      BasicTest test=monitor->{
          for(var itrType:StructType.PackedBooleanArrDeq.validItrTypes) {
              for(var illegalMod:itrType.validPreMods) {
                  if(monitor.checkedType.checked || illegalMod.expectedException==null) {
                      for(var outputType:DataType.BOOLEAN.validOutputTypes()) {
                          if(illegalMod.expectedException==null) {
                              final var itrMonitor=monitor.getMonitoredIterator(itrType);
                              while(itrMonitor.hasNext()){
                                itrMonitor.verifyNext(outputType);
                              }
                              if(monitor.checkedType.checked){
                                Assertions.assertThrows(NoSuchElementException.class,()->itrMonitor.verifyNext(outputType));
                              }
                          }else {
                              for(final var position:POSITIONS){
                                  if(position >= 0){
                                      final int index=(int)(monitor.expectedSize * position);
                                      final var itrMonitor=monitor.getMonitoredIterator(index,itrType);
                                      itrMonitor.illegalMod(illegalMod);
                                      Assertions.assertThrows(illegalMod.expectedException,()->itrMonitor.verifyNext(outputType));
                                  }
                              }
                          }
                      }
                  }
              }
          }
      };
      test.runAllTests("PackedBooleanArrDeqTest.testItrnext_void",SHORT_SIZES);
  }  
  @Test public void testItrremove_void(){
    for(var size:SHORT_SIZES){
      final int initCapBound=size+64&-64;
      int prevNumToIterate=-1;
      for(final var position:POSITIONS){
        final int numToIterate;
        if(position >= 0 && (numToIterate=(int)(size * position)) != prevNumToIterate){
          prevNumToIterate=numToIterate;
          for(int tmpInitCap=0;tmpInitCap<=initCapBound;tmpInitCap+=64) {
            final int initCap=tmpInitCap;
            final int rotateBound=getExpectedFinalCapacity(initCap,size);
            for(var tmpNumToRotate=0;tmpNumToRotate <= rotateBound;++tmpNumToRotate){
              final int numToRotate=tmpNumToRotate;
              for(final var checkedType:CheckedType.values()){
                  for(final var itrType:StructType.PackedBooleanArrDeq.validItrTypes){
                    for(final var illegalMod:itrType.validPreMods){
                      if(illegalMod.expectedException == null || checkedType.checked){
                        for(final var removeScenario:itrType.validItrRemoveScenarios){
                          if(removeScenario.expectedException == null || checkedType.checked){
                            switch(removeScenario){
                            case PostInit:
                              if(numToIterate != 0){
                                continue;
                              }
                              break;
                            case PostNext:
                              if(size == 0 || numToIterate == size){
                                continue;
                              }
                              break;
                            case PostRemove:
                              if(size == 0){
                                continue;
                              }
                              break;
                            default:
                              throw removeScenario.invalid();
                            }
                            TestExecutorService.submitTest(()->{
                              final var seqMonitor=SequenceInitialization.Ascending
                                  .initialize(new PackedBooleanArrDeqMonitor(checkedType,initCap),size,0);
                              seqMonitor.rotate(numToRotate);
                              final var itrMonitor=seqMonitor.getMonitoredIterator(numToIterate,itrType);
                              
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
                                    Assertions.assertEquals(numToIterate < 2,seqMonitor.isEmpty());
                                    break;
                                  }
                                  default:
                                    throw removeScenario.invalid();
                                  }
                                }else{
                                  Assertions.assertThrows(illegalMod.expectedException,()->itrMonitor.verifyRemove());
                                }
                              }else{
                               
                                Assertions.assertThrows(removeScenario.expectedException,()->itrMonitor.verifyRemove());
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
    TestExecutorService.completeAllTests("PackedBooleanArrDeqTest.testItrremove_void");
  }
  private static void verifyMASSIVEToStringHelper(PackedBooleanArrDeq deq,String testName) {
    int size=deq.size();
    String str=deq.toString();
    boolean firstVal=deq.booleanElement();
    int offset;
    Assertions.assertEquals('[',str.charAt(offset=0));
    int numBatches=1;
    int batchSize=(size-1)/2/numBatches * 13;
    if(firstVal)
    {
      Assertions.assertEquals('t',str.charAt(++offset));
      Assertions.assertEquals('r',str.charAt(++offset));
      Assertions.assertEquals('u',str.charAt(++offset));
      Assertions.assertEquals('e',str.charAt(++offset));
      ++offset;
      for(int batchCount=0;batchCount<numBatches;) {
        final int batchOffset=offset;
        final int batchBound=++batchCount == numBatches?str.length() - 1:offset + batchSize;
        TestExecutorService.submitTest(()->{
          for(int i=batchOffset;;++i) {
            if(i>=batchBound) {
                break;
            }
            Assertions.assertEquals(',',str.charAt(i));
            Assertions.assertEquals(' ',str.charAt(++i));
            Assertions.assertEquals('f',str.charAt(++i));
            Assertions.assertEquals('a',str.charAt(++i));
            Assertions.assertEquals('l',str.charAt(++i));
            Assertions.assertEquals('s',str.charAt(++i));
            Assertions.assertEquals('e',str.charAt(++i));
            if(++i>=batchBound) {
              break;
            }
            Assertions.assertEquals(',',str.charAt(i));
            Assertions.assertEquals(' ',str.charAt(++i));
            Assertions.assertEquals('t',str.charAt(++i));
            Assertions.assertEquals('r',str.charAt(++i));
            Assertions.assertEquals('u',str.charAt(++i));
            Assertions.assertEquals('e',str.charAt(++i));
          }
        });
        offset=batchBound;
      }
    }
    else
    {
      Assertions.assertEquals('f',str.charAt(++offset));
      Assertions.assertEquals('a',str.charAt(++offset));
      Assertions.assertEquals('l',str.charAt(++offset));
      Assertions.assertEquals('s',str.charAt(++offset));
      Assertions.assertEquals('e',str.charAt(++offset));
      ++offset;
      for(int batchCount=0;batchCount<numBatches;) {
        final int batchOffset=offset;
        final int batchBound=++batchCount == numBatches?str.length() - 1:offset + batchSize;
        TestExecutorService.submitTest(()->{

          for(int i=batchOffset;;++i) {
            if(i>=batchBound) {
                break;
            }
            Assertions.assertEquals(',',str.charAt(i));
            Assertions.assertEquals(' ',str.charAt(++i));
            Assertions.assertEquals('t',str.charAt(++i));
            Assertions.assertEquals('r',str.charAt(++i));
            Assertions.assertEquals('u',str.charAt(++i));
            Assertions.assertEquals('e',str.charAt(++i));
            if(++i>=batchBound) {
              break;
            }
            Assertions.assertEquals(',',str.charAt(i));
            Assertions.assertEquals(' ',str.charAt(++i));
            Assertions.assertEquals('f',str.charAt(++i));
            Assertions.assertEquals('a',str.charAt(++i));
            Assertions.assertEquals('l',str.charAt(++i));
            Assertions.assertEquals('s',str.charAt(++i));
            Assertions.assertEquals('e',str.charAt(++i));
          }
        });
        offset=batchBound;
      }
      
    }
    Assertions.assertEquals(']',str.charAt(str.length()-1));
    TestExecutorService.completeAllTests(testName);
  }
  @Test public void testMASSIVEtoString(){
      int seqSize;
      long[] words;
      for(int i=(words=new long[((seqSize=DataType.BOOLEAN.massiveToStringThreshold+1)-1>>6)+1]).length;--i>=0;) {
          words[i]=0xAAAAAAAAAAAAAAAAL;
      }
      int capacity=words.length<<6;
      {
        PackedBooleanArrDeq deq=new PackedBooleanArrDeq(0,words,seqSize-1);
        verifyMASSIVEToStringHelper(deq,"PackedBooleanArrDeqTest.testMASSIVEtoString Unchecked nonfragmented first bit false");
        ++deq.head;
        ++deq.tail;
        verifyMASSIVEToStringHelper(deq,"PackedBooleanArrDeqTest.testMASSIVEtoString Unchecked nonfragmented first bit true");
        deq.head=capacity/2;
        deq.tail=deq.head+seqSize-capacity;
        verifyMASSIVEToStringHelper(deq,"PackedBooleanArrDeqTest.testMASSIVEtoString Unchecked fragmented first bit false");
        ++deq.head;
        ++deq.tail;
        verifyMASSIVEToStringHelper(deq,"PackedBooleanArrDeqTest.testMASSIVEtoString Unchecked fragmented first bit true");
      }
      {
        PackedBooleanArrDeq deq=new PackedBooleanArrDeq.Checked(0,words,seqSize-1);
        verifyMASSIVEToStringHelper(deq,"PackedBooleanArrDeqTest.testMASSIVEtoString Checked nonfragmented first bit false");
        ++deq.head;
        ++deq.tail;
        verifyMASSIVEToStringHelper(deq,"PackedBooleanArrDeqTest.testMASSIVEtoString Unchecked nonfragmented first bit true");
        deq.head=capacity/2;
        deq.tail=deq.head+seqSize-capacity;
        verifyMASSIVEToStringHelper(deq,"PackedBooleanArrDeqTest.testMASSIVEtoString Unchecked fragmented first bit false");
        ++deq.head;
        ++deq.tail;
        verifyMASSIVEToStringHelper(deq,"PackedBooleanArrDeqTest.testMASSIVEtoString Unchecked fragmented first bit true");        
      }
  }
  
  @Test public void testoffer_val(){
    final AddTest test=(monitor,inputVal,inputType,functionCallType)->Assertions
        .assertTrue(monitor.verifyOffer(inputVal,inputType,functionCallType));
    test.runAllTests("PackedBooleanArrDeqTest.testoffer_val");
  }
  
  @Test public void testofferFirst_val(){
    final AddTest test=(monitor,inputVal,inputType,functionCallType)->Assertions
        .assertTrue(monitor.verifyOfferFirst(inputVal,inputType,functionCallType));
    test.runAllTests("PackedBooleanArrDeqTest.testofferFirst_val");
  }
  
  @Test public void testofferLast_val(){
    final AddTest test=(monitor,inputVal,inputType,functionCallType)->Assertions
        .assertTrue(monitor.verifyOfferLast(inputVal,inputType,functionCallType));
    test.runAllTests("PackedBooleanArrDeqTest.testofferLast_val");
  }
  
  @Test public void testpeek_void(){
    final GetTest test=(monitor,outputType)->{
      monitor.verifyPeek(outputType);
      if(!monitor.isEmpty()){
        monitor.removeFirst();
      }
    };
    test.runAllTests("PackedBooleanArrDeqTest.testpeek_void",false);
  }
   @Test public void testpeekFirst_void(){
    final GetTest test=(monitor,outputType)->{
      monitor.verifyPeekFirst(outputType);
      if(!monitor.isEmpty()){
        monitor.removeFirst();
      }
    };
    test.runAllTests("PackedBooleanArrDeqTest.testpeekFirst_void",false);
  }
  
  @Test public void testpeekLast_void(){
    final GetTest test=(monitor,outputType)->{
      monitor.verifyPeekLast(outputType);
      if(!monitor.isEmpty()){
        monitor.removeLast();
      }
    };
    test.runAllTests("PackedBooleanArrDeqTest.testpeekLast_void",false);
  }
  
  @Test public void testpoll_void(){
    final GetTest test=PackedBooleanArrDeqMonitor::verifyPoll;
    test.runAllTests("PackedBooleanArrDeqTest.testpoll_void",false);
  }
  
  @Test public void testpollFirst_void(){
    final GetTest test=PackedBooleanArrDeqMonitor::verifyPollFirst;
    test.runAllTests("PackedBooleanArrDeqTest.testpollFirst_void",false);
  }
  
  @Test public void testpollLast_void(){
    final GetTest test=PackedBooleanArrDeqMonitor::verifyPollLast;
    test.runAllTests("PackedBooleanArrDeqTest.testpollLast_void",false);
  }
  
  @Test public void testpop_void(){
    final GetTest test=PackedBooleanArrDeqMonitor::verifyPop;
    test.runAllTests("PackedBooleanArrDeqTest.testpop_void",true);
  }
   @Test public void testpush_val(){
    final AddTest test=PackedBooleanArrDeqMonitor::verifyPush;
    test.runAllTests("PackedBooleanArrDeqTest.testpush_val");
  }  
  @Test public void testReadAndWrite(){
    final MonitoredFunctionTest test=(monitor,functionGen,functionCallType,randSeed)->{
      if(functionGen.expectedException == null){
        Assertions.assertDoesNotThrow(()->monitor.verifyReadAndWrite(functionGen));
      }else{
        Assertions.assertThrows(functionGen.expectedException,()->monitor.verifyReadAndWrite(functionGen));
      }
    };
    
    test.runAllTests("PackedBooleanArrDeqTest.testReadAndWrite",0,SIZES);
  }
  
  @Test public void testremove_void(){
    final GetTest test=PackedBooleanArrDeqMonitor::verifyRemove;
    test.runAllTests("PackedBooleanArrDeqTest.testremove_void",true);
  }
  
  @Test public void testremoveFirst_void(){
    final GetTest test=PackedBooleanArrDeqMonitor::verifyRemoveFirst;
    test.runAllTests("PackedBooleanArrDeqTest.testremoveFirst_void",true);
  }
  
  @Test public void testremoveFirstOccurrence_val(){
    final RemoveValTest test=(monitor,queryVal,modification,castType,inputType,size,position)->{
        Assertions.assertEquals(position >= 0,
            monitor.verifyRemoveFirstOccurrence(queryVal,inputType,castType,modification));

    };
    test.runAllTests("PackedBooleanArrDeqTest.testremoveFirstOccurrence_val");
  }  
  @Test public void testremoveIf_Predicate(){
      for(final var checkedType:CheckedType.values()){
        for(final var filterGen:StructType.PackedBooleanArrDeq.validMonitoredRemoveIfPredicateGens){
          if(filterGen.expectedException == null || checkedType.checked){
            for(final var functionCallType:DataType.BOOLEAN.validFunctionCalls){
              for(final var size:SHORT_SIZES){
                int periodBound;
                int initValBound;
                int periodInc;
                int periodOffset;
                if(size > 0){
                  periodOffset=0;
                  periodBound=size;
                  initValBound=1;
                  periodInc=1;
                }else{
                  periodOffset=0;
                  periodBound=size;
                  initValBound=0;
                  periodInc=1;
                }
                final int rotateInc=Math.max(1,size / 4);
                if(functionCallType == FunctionCallType.Boxed && size > 2){
                  continue;
                }
                if(filterGen.expectedException!=null && size>126) {
                    continue;
                }
                double[] thresholdArr;
                long randSeedBound;
                if(size == 0
                    || filterGen.predicateGenCallType != MonitoredRemoveIfPredicateGen.PredicateGenCallType.Randomized
                    || functionCallType == FunctionCallType.Boxed){
                  thresholdArr=NON_RANDOM_THRESHOLD;
                  randSeedBound=0;
                }else{
                  thresholdArr=RANDOM_THRESHOLDS;
                  randSeedBound=100;
                }
                for(long tmpRandSeed=0;tmpRandSeed <= randSeedBound;++tmpRandSeed){
                  if(functionCallType == FunctionCallType.Boxed && tmpRandSeed > 0){
                    break;
                  }
                  final long randSeed=tmpRandSeed;
                  for(final double threshold:thresholdArr){
                    for(int tmpNumToRotate=0;tmpNumToRotate <= size;tmpNumToRotate+=rotateInc){
                      if(functionCallType == FunctionCallType.Boxed && tmpNumToRotate > 1){
                        break;
                      }
                      final int numToRotate=tmpNumToRotate;
                      for(var tmpInitVal=0;tmpInitVal <= initValBound;++tmpInitVal){
                        final int initVal=tmpInitVal;
                        for(var tmpPeriod=periodOffset;tmpPeriod <= periodBound;tmpPeriod+=periodInc){
                          final int period=tmpPeriod;
                          TestExecutorService.submitTest(()->{
                           
                              
                            final var monitor=SequenceInitialization.Ascending
                                .initialize(new PackedBooleanArrDeqMonitor(checkedType,size),size,initVal,period);
                            monitor.rotate(numToRotate);
                            final var filter
                                =filterGen.getMonitoredRemoveIfPredicate(monitor,threshold,new Random(randSeed));
      
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
    TestExecutorService.completeAllTests("PackedBooleanArrDeqTest.testremoveIf_Predicate");
  }
  
  @Test public void testremoveLast_void(){
    final GetTest test=PackedBooleanArrDeqMonitor::verifyRemoveLast;
    test.runAllTests("PackedBooleanArrDeqTest.testremoveLast_void",true);
  }  
  @Test public void testremoveLastOccurrence_val(){
    final RemoveValTest test=(monitor,queryVal,modification,castType,inputType,size,position)->{
        Assertions.assertEquals(position >= 0,
            monitor.verifyRemoveLastOccurrence(queryVal,inputType,castType,modification));

    };
    test.runAllTests("PackedBooleanArrDeqTest.testremoveLastOccurrence_val");
  }  
  @Test public void testremoveVal_val(){
    final RemoveValTest test=(monitor,queryVal,modification,castType,inputType,size,position)->{
        Assertions.assertEquals(position >= 0,monitor.verifyRemoveVal(queryVal,inputType,castType,modification));
    };
    test.runAllTests("PackedBooleanArrDeqTest.testremoveVal_val");
  }  
  @Test public void testsearch_val(){
    final QueryTest test=(monitor,queryVal,modification,castType,inputType,size,position)->{
        int expectedIndex;
        if(position >= 0){
          expectedIndex=1 + (int)Math.round(position * size);
        }else{
          expectedIndex=-1;
        }
        Assertions.assertEquals(expectedIndex,monitor.verifySearch(queryVal,inputType,castType,modification));
    };
    test.runAllTests("PackedBooleanArrDeqTest.testsearch_val");
  }
  
  @Test public void testsize_void(){
    final BasicTest test=PackedBooleanArrDeqMonitor::verifySize;
    test.runAllTests("PackedBooleanArrDeqTest.testsize_void",SHORT_SIZES);
  }
  
  @Test public void testtoArray_IntFunction(){
    final MonitoredFunctionTest test=(monitor,functionGen,functionCallType,randSeed)->{
      if(functionGen.expectedException == null){
        monitor.verifyToArray(functionGen);
      }else{
        Assertions.assertThrows(functionGen.expectedException,()->monitor.verifyToArray(functionGen));
      }
    };
    test.runAllTests("PackedBooleanArrDeqTest.testtoArray_IntFunction",0,SHORT_SIZES);
  }
  
  @Test public void testtoArray_ObjectArray(){
    final BasicTest test=(monitor)->{
      final int size=monitor.size();
      final int inc=Math.max(1,size / 10);
      for(int paramArrLength=0,bound=size + inc;paramArrLength <= bound;paramArrLength+=inc){
        monitor.verifyToArray(new Object[paramArrLength]);
      }
    };
    test.runAllTests("PackedBooleanArrDeqTest.testtoArray_ObjectArray",SHORT_SIZES);
  }
  
  @Test public void testtoArray_void(){
    final BasicTest test=(monitor)->{
      for(final var outputType:monitor.dataType.validOutputTypes()){
        outputType.verifyToArray(monitor);
      }
    };
    test.runAllTests("PackedBooleanArrDeqTest.testtoArray_void",SHORT_SIZES);
  }
  
  @Test public void testtoString_void(){
      BasicTest test=PackedBooleanArrDeqMonitor::verifyToString;
      test.runAllTests("PackedBooleanArrDeqTest.testtoString_void",SHORT_SIZES);
  }
}
