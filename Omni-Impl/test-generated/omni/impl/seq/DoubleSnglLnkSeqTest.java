package omni.impl.seq;
import java.util.function.DoubleConsumer;
import java.util.function.Consumer;
import java.io.IOException;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import omni.impl.DoubleInputTestArgType;
import omni.impl.DoubleOutputTestArgType;
import java.util.NoSuchElementException;
import omni.impl.DoubleSnglLnkNode;
import omni.impl.FunctionCallType;
import omni.impl.QueryCastType;
import java.io.File;
import org.junit.jupiter.api.Tag;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import omni.impl.seq.AbstractDoubleSeqMonitor.CheckedType;
import omni.impl.seq.AbstractDoubleSeqMonitor.PreModScenario;
import omni.impl.seq.AbstractDoubleSeqMonitor.SequenceLocation;
import omni.impl.seq.AbstractDoubleSeqMonitor.IterationScenario;
import omni.impl.seq.AbstractDoubleSeqMonitor.ItrRemoveScenario;
import omni.impl.seq.AbstractDoubleSeqMonitor.MonitoredFunctionGen;
import omni.impl.seq.AbstractDoubleSeqMonitor.MonitoredRemoveIfPredicateGen;
import omni.impl.seq.AbstractDoubleSeqMonitor.QueryTester;
import java.nio.file.Files;
import omni.impl.seq.AbstractDoubleSeqMonitor.SequenceVerificationItr;
import omni.api.OmniCollection;
import java.util.ArrayList;
@SuppressWarnings({"rawtypes","unchecked"})
@Tag("SnglLnkSeqTest")
public class DoubleSnglLnkSeqTest{
  private static final java.util.concurrent.ExecutorService EXECUTORSERVICE=
  java.util.concurrent.Executors.newWorkStealingPool();
  private static final java.util.ArrayList<java.util.concurrent.Future<Object>> TESTQUEUE=new java.util.ArrayList<>();
  private static void submitTest(Runnable test){
    TESTQUEUE.add(EXECUTORSERVICE.submit(java.util.concurrent.Executors.callable(test)));
  }
  private static void completeAllTests(){
    try{
      TESTQUEUE.forEach(test->{
        try{
          test.get();
        }catch(InterruptedException|java.util.concurrent.ExecutionException e){
          var cause=e.getCause();
          if(cause instanceof RuntimeException){
            throw (RuntimeException)cause;
          }
          if(cause instanceof Error){
            throw (Error)cause;
          }
          throw new Error(cause);
        }
      });
    }finally{
      TESTQUEUE.clear();
    }
  }
  @org.junit.jupiter.api.AfterEach
  public void verifyAllExecuted(){
    if(!TESTQUEUE.isEmpty()){
      System.err.println("Warning: there were "+TESTQUEUE.size()+" tests that were not completed");
    }
  }
  @org.junit.jupiter.api.AfterAll
  public static void cleanUp(){
    java.util.List<Runnable> stillRunning=EXECUTORSERVICE.shutdownNow();
    if(!TESTQUEUE.isEmpty() || !stillRunning.isEmpty()){
      throw new Error("There were unfinished tests in the queue");
    }
  }
  interface BasicCollectionTest{
    void runTest(SeqMonitor seqMonitor,int seqSize);
    private void runTests(){
      for(var nestedType:NestedType.values()){
        for(var checkedType:CheckedType.values()){
          for(int seqSize:AbstractDoubleSeqMonitor.FIB_SEQ){
            submitTest(()->runTest(new SeqMonitor(nestedType,checkedType),seqSize));
          }
        }
      }
      completeAllTests();
    }
  }
  @org.junit.jupiter.api.Test
  public void testclear_void(){
    BasicCollectionTest test=(seqMonitor,numToAdd)->{
      for(int i=0;i<numToAdd;++i){
        seqMonitor.add(i);
      }
      seqMonitor.clear();
      seqMonitor.verifyStructuralIntegrity();
      Assertions.assertTrue(seqMonitor.seq.isEmpty());
      seqMonitor.verifyPreAlloc().verifyPostAlloc();
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testisEmpty_void(){
    BasicCollectionTest test=(seqMonitor,numToAdd)->{
      for(int i=0;i<numToAdd;++i){
        Assertions.assertEquals(i==0,seqMonitor.seq.isEmpty());
        seqMonitor.verifyStructuralIntegrity();
        seqMonitor.add(i);
      }
      var itrMonitor=seqMonitor.getItrMonitor();
      while(numToAdd>0){
        Assertions.assertEquals((numToAdd--)==0,seqMonitor.seq.isEmpty());
        seqMonitor.verifyStructuralIntegrity();
        itrMonitor.iterateForward();
        itrMonitor.remove();
      }
      Assertions.assertTrue(seqMonitor.seq.isEmpty());
      seqMonitor.verifyStructuralIntegrity();
      seqMonitor.verifyPreAlloc().verifyNaturalAscending(numToAdd).verifyPostAlloc();
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testsize_void(){
    BasicCollectionTest test=(seqMonitor,numToAdd)->{
      for(int i=0;i<numToAdd;++i){
        Assertions.assertEquals(i,seqMonitor.seq.size());
        seqMonitor.verifyStructuralIntegrity();
        seqMonitor.add(i);
      }
      var itrMonitor=seqMonitor.getItrMonitor();
      while(numToAdd>0){
        Assertions.assertEquals(numToAdd--,seqMonitor.seq.size());
        seqMonitor.verifyStructuralIntegrity();
        itrMonitor.iterateForward();
        itrMonitor.remove();
      }
      Assertions.assertEquals(numToAdd,seqMonitor.seq.size());
      seqMonitor.verifyStructuralIntegrity();
      seqMonitor.verifyPreAlloc().verifyNaturalAscending(numToAdd).verifyPostAlloc();
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testclone_void(){
    BasicCollectionTest test=(seqMonitor,numToAdd)->{
      for(int i=0;i<numToAdd;++i){
        seqMonitor.add(i);
      }
      var clone=(OmniCollection.OfDouble)seqMonitor.seq.clone();
      seqMonitor.verifyStructuralIntegrity();
      seqMonitor.verifyPreAlloc().verifyNaturalAscending(numToAdd).verifyPostAlloc();
      Assertions.assertNotSame(clone,seqMonitor.seq);
      switch(seqMonitor.nestedType){
        case STACK:
          if(seqMonitor.checkedType.checked){
            Assertions.assertTrue(clone instanceof DoubleSnglLnkSeq.CheckedStack);
            Assertions.assertEquals(0,((DoubleSnglLnkSeq.CheckedStack)clone).modCount);
          }else{
            Assertions.assertTrue(clone instanceof DoubleSnglLnkSeq.UncheckedStack);
          }
          break;
        case QUEUE:
          if(seqMonitor.checkedType.checked){
            Assertions.assertTrue(clone instanceof DoubleSnglLnkSeq.CheckedQueue);
            Assertions.assertEquals(0,((DoubleSnglLnkSeq.CheckedQueue)clone).modCount);
          }else{
            Assertions.assertTrue(clone instanceof DoubleSnglLnkSeq.UncheckedQueue);
          }
          break;
        default:
          throw new Error("Unknown nested type "+seqMonitor.nestedType);
      }
      var snglLnkSeqClone=(DoubleSnglLnkSeq)clone;
      var originalHead=((DoubleSnglLnkSeq)seqMonitor.seq).head;
      var cloneHead=snglLnkSeqClone.head;
      Assertions.assertEquals(numToAdd,snglLnkSeqClone.size);
      if(snglLnkSeqClone.size==0){
        Assertions.assertNull(cloneHead);
        if(seqMonitor.nestedType==NestedType.QUEUE){
          if(seqMonitor.checkedType.checked){
            Assertions.assertNull(((DoubleSnglLnkSeq.CheckedQueue)snglLnkSeqClone).tail);
          }else{
            Assertions.assertNull(((DoubleSnglLnkSeq.UncheckedQueue)snglLnkSeqClone).tail);
          }
        }
      }else{
        for(int i=snglLnkSeqClone.size;;cloneHead=cloneHead.next,originalHead=originalHead.next){
          Assertions.assertNotNull(cloneHead);
          Assertions.assertNotSame(cloneHead,originalHead);
          Assertions.assertEquals(cloneHead.val,originalHead.val);
          if(--i==0){
            if(seqMonitor.nestedType==NestedType.QUEUE){
              if(seqMonitor.checkedType.checked){
                Assertions.assertSame(cloneHead,((DoubleSnglLnkSeq.CheckedQueue)snglLnkSeqClone).tail);
              }else{
                Assertions.assertSame(cloneHead,((DoubleSnglLnkSeq.UncheckedQueue)snglLnkSeqClone).tail);
              }
            }
            Assertions.assertNull(cloneHead.next);
            Assertions.assertNull(originalHead.next);
            break;
          }
        }
      }
    };
    test.runTests();
  }
  interface ToStringAndHashCodeTest{
    void runTest(SeqMonitor seqMonitor,int seqSize
    );
    private void runTests(){
      for(var nestedType:NestedType.values()){
        for(var checkedType:CheckedType.values()){
          for(int seqSize:AbstractDoubleSeqMonitor.FIB_SEQ){
            submitTest(()->runTest(new SeqMonitor(nestedType,checkedType),seqSize));
          }
        }
      }
      completeAllTests();
    }
  }
  @org.junit.jupiter.api.Test
  public void testhashCode_void(){
    ToStringAndHashCodeTest test=(seqMonitor,numToAdd
    )->{
      {
        for(int i=0;i<numToAdd;++i){
          seqMonitor.add(i);
        }
      }
      {
        int resultHash=seqMonitor.seq.hashCode();
        seqMonitor.verifyPreAlloc().verifyNaturalAscending(numToAdd).verifyPostAlloc();
        var itr=seqMonitor.seq.iterator();
        int expectedHash=1;
        for(int i=0;i<numToAdd;++i){
          expectedHash=(expectedHash*31)+itr.next().hashCode();
        }
        Assertions.assertEquals(expectedHash,resultHash);
      }
      seqMonitor.verifyStructuralIntegrity();
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testtoString_void(){
    ToStringAndHashCodeTest test=(seqMonitor,numToAdd
    )->{
      {
        for(int i=0;i<numToAdd;++i){
          seqMonitor.add(i);
        }
      }
      {
        var resultStr=seqMonitor.seq.toString();
        seqMonitor.verifyPreAlloc().verifyNaturalAscending(numToAdd).verifyPostAlloc();
        var itr=seqMonitor.seq.iterator();
        var arrList=new ArrayList<Object>();
        for(int i=0;i<numToAdd;++i){
          arrList.add(itr.next());
        }
        Assertions.assertEquals(arrList.toString(),resultStr);
      }
      seqMonitor.verifyStructuralIntegrity();
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testremoveIf_Predicate(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var monitoredRemoveIfPredicateGen:MonitoredRemoveIfPredicateGen.values()){
          if(monitoredRemoveIfPredicateGen.expectedException==null || (checkedType.checked && monitoredRemoveIfPredicateGen.appliesToRoot)){
            for(var functionCallType:FunctionCallType.values()){
              for(int tmpSeqSize=0;tmpSeqSize<=100;tmpSeqSize+=10){
                final int seqSize=tmpSeqSize;
                double[] thresholdArr;
                long randSeedBound;
                if(seqSize==0 || !monitoredRemoveIfPredicateGen.isRandomized){
                  thresholdArr=new double[]{0.5};
                  randSeedBound=0;
                }else{
                  thresholdArr=new double[]{0.01,0.05,0.10,0.25,0.50,0.75,0.90,0.95,0.99};
                  randSeedBound=100;
                }
                for(long tmpRandSeed=0;tmpRandSeed<=randSeedBound;++tmpRandSeed){
                  final long randSeed=tmpRandSeed;
                  for(double threshold:thresholdArr){
                    submitTest(()->testremoveIf_PredicateHelper(new SeqMonitor(nestedType,checkedType),monitoredRemoveIfPredicateGen,threshold,randSeed,functionCallType,seqSize));
                  }
                }
              }
            }
          }
        }
      }
    }
    completeAllTests();
  }
  private static void testremoveIf_PredicateHelper(SeqMonitor seqMonitor,MonitoredRemoveIfPredicateGen monitoredRemoveIfPredicateGen,double threshold,long randSeed,final FunctionCallType functionCallType,int seqSize
  ){
    for(int i=0;i<seqSize;++i){
      seqMonitor.add(i);
    }
    final var clone=(OmniCollection.OfDouble)seqMonitor.seq.clone();
    final int numExpectedCalls=seqSize;
    final int numExpectedRemoved;
    switch(monitoredRemoveIfPredicateGen){
      case RemoveFirst:
      case RemoveLast:
        numExpectedRemoved=Math.min(1,seqSize);
        break;
      case RemoveFirstAndLast:
        numExpectedRemoved=Math.min(2,seqSize);
        break;
      case RemoveAllButFirst:
      case RemoveAllButLast:
        numExpectedRemoved=seqSize-Math.min(1,seqSize);
        break;
      case RemoveAllButFirstAndLast:
        numExpectedRemoved=seqSize-Math.min(2,seqSize);
        break;
      case RemoveAll:
        numExpectedRemoved=seqSize;
        break;
      case Random:
        numExpectedRemoved=-1;
        break;
      case RemoveNone:
      case Throw:
      case ModSeq:
      case ThrowModSeq:
        numExpectedRemoved=0;
        break;
      default:
        throw new Error("Unknown monitoredRemoveIfPredicateGen "+monitoredRemoveIfPredicateGen);
    }
    final var monitoredRemoveIfPredicate=monitoredRemoveIfPredicateGen.getMonitoredRemoveIfPredicate(seqMonitor,randSeed,numExpectedCalls,threshold);
    if(monitoredRemoveIfPredicateGen.expectedException==null || seqSize==0){
      seqMonitor.verifyRemoveIf(monitoredRemoveIfPredicate,functionCallType,numExpectedRemoved,clone);
      seqMonitor.verifyPreAlloc().skip(seqMonitor.expectedSeqSize).verifyPostAlloc();
      return;
    }else{
      Assertions.assertThrows(monitoredRemoveIfPredicateGen.expectedException,()->seqMonitor.verifyRemoveIf(monitoredRemoveIfPredicate,functionCallType,numExpectedRemoved,clone));
      //TODO verify contents of sequence in throw cases 
    }
    seqMonitor.verifyStructuralIntegrity();
  }
  interface QueryTest{
    void runTest(SeqMonitor seqMonitor,QueryTester tester,QueryCastType queryCastType,SequenceLocation seqLocation,int seqSize
    );
    private void runStackTests(){
      runTests(NestedType.STACK);
      completeAllTests();
    }
    private void runCollectionTests(){
      runTests(NestedType.STACK);
      runTests(NestedType.QUEUE);
      completeAllTests();
    }
    private void runTests(NestedType nestedType){
      for(var checkedType:CheckedType.values()){
        for(var seqLocation:SequenceLocation.values()){
          if(seqLocation!=SequenceLocation.IOBLO){
            for(int seqSize:AbstractDoubleSeqMonitor.FIB_SEQ){
              if(seqLocation==SequenceLocation.IOBHI || (seqSize>1 || (seqLocation==SequenceLocation.BEGINNING && seqSize>0))){
                for(var argType:QueryTester.values()){
                  for(var queryCastType:QueryCastType.values()){
                    switch(argType){
                      case Booleannull:
                      case Bytenull:
                      case Characternull:
                      case Shortnull:
                      case Integernull:
                      case Longnull:
                      case Floatnull:
                      case Doublenull:
                        if(queryCastType!=QueryCastType.ToBoxed || (seqSize>0 && seqLocation.expectedException==null)){
                          continue;
                        }
                        break;
                      case Objectnull:
                        if(queryCastType!=QueryCastType.ToObject || (seqSize>0 && seqLocation.expectedException==null)){
                          continue;
                        }
                        break;
                      case Booleanfalse:
                      case Byte0:
                      case Character0:
                      case Short0:
                      case Integer0:
                      case Long0:
                      case Floatpos0:
                      case Floatneg0:
                      case Doublepos0:
                      case Doubleneg0:
                      case Booleantrue:
                      case Bytepos1:
                      case Characterpos1:
                      case Shortpos1:
                      case Integerpos1:
                      case Longpos1:
                      case Floatpos1:
                      case Doublepos1:
                      //values beyond the range of boolean
                      case Bytepos2:
                      case Characterpos2:
                      case Shortpos2:
                      case Integerpos2:
                      case Longpos2:
                      case Floatpos2:
                      case Doublepos2:
                      //negative values beyond the range of char
                      case Byteneg1:
                      case Shortneg1:
                      case Integerneg1:
                      case Longneg1:
                      case Floatneg1:
                      case Doubleneg1:
                      //negative values beyond the range of byte
                      case ShortMIN_BYTE_MINUS1:
                      case IntegerMIN_BYTE_MINUS1:
                      case LongMIN_BYTE_MINUS1:
                      case FloatMIN_BYTE_MINUS1:
                      case DoubleMIN_BYTE_MINUS1:
                      //negative values beyond the range of short
                      case IntegerMIN_SHORT_MINUS1:
                      case LongMIN_SHORT_MINUS1:
                      case FloatMIN_SHORT_MINUS1:
                      case DoubleMIN_SHORT_MINUS1:
                      //negative values beyond the range of int
                      case FloatMIN_INT_MINUS1:
                      //negative values beyond the range of int and beyond the precision of float
                      case LongMIN_INT_MINUS1:
                      case DoubleMIN_INT_MINUS1:
                      //negative values beyond MIN_SAFE_INT that are beyond the precision of float
                      case IntegerMIN_SAFE_INT_MINUS1:
                      case LongMIN_SAFE_INT_MINUS1:
                      case DoubleMIN_SAFE_INT_MINUS1:
                      //positive values out of the range of byte
                      case CharacterMAX_BYTE_PLUS1:
                      case ShortMAX_BYTE_PLUS1:
                      case IntegerMAX_BYTE_PLUS1:
                      case LongMAX_BYTE_PLUS1:
                      case FloatMAX_BYTE_PLUS1:
                      case DoubleMAX_BYTE_PLUS1:
                      //positive values out of the range of short
                      case CharacterMAX_SHORT_PLUS1:
                      case IntegerMAX_SHORT_PLUS1:
                      case LongMAX_SHORT_PLUS1:
                      case FloatMAX_SHORT_PLUS1:
                      case DoubleMAX_SHORT_PLUS1:
                      //positive values out of the range of char
                      case IntegerMAX_CHAR_PLUS1:
                      case LongMAX_CHAR_PLUS1:
                      case FloatMAX_CHAR_PLUS1:
                      case DoubleMAX_CHAR_PLUS1:
                      //positive values out of the range of int
                      case LongMAX_INT_PLUS1:
                      case FloatMAX_INT_PLUS1:
                      case DoubleMAX_INT_PLUS1:
                      //positive values beyond MAX_SAFE_INT that are beyond the precision of float
                      case IntegerMAX_SAFE_INT_PLUS1:
                      case LongMAX_SAFE_INT_PLUS1:
                      case DoubleMAX_SAFE_INT_PLUS1:
                        //floating-point values beyond the range any integral type
                      case FloatMAX_LONG_PLUS1:
                      case FloatMIN_LONG_MINUS1:
                      case FloatMAX_FLOAT_VALUE:
                      case DoubleMAX_FLOAT_VALUE:
                      //fractional floating point values that cannot be matched with any integral type
                      case FloatMIN_FLOAT_VALUE:
                      case DoubleMIN_FLOAT_VALUE:
                      //NaN values that can only be matches with NaN
                      case FloatNaN:
                      case DoubleNaN:
                      //double-precision floating-point values beyond the range of any integral type and beyond the precision of float
                      case DoubleMAX_LONG_PLUS1:
                      case DoubleMIN_LONG_MINUS1:
                      case DoubleMAX_DOUBLE_VALUE:
                      //fractional floating point values that cannot be matched with any integral type and which are beyond the precision of float
                      case DoubleMIN_DOUBLE_VALUE:
                      //these input values cannot potentially return true
                      break;
                      default:
                      if(seqSize>0 && seqLocation.expectedException==null){
                        continue;
                      }
                      //these values must necessarily return false
                    }
                    submitTest(()->runTest(new SeqMonitor(nestedType,checkedType),argType,queryCastType,seqLocation,seqSize));
                  }
                }
              }
            }
          }
        }
      }
    }
  }
  @org.junit.jupiter.api.Test
  public void testremoveVal_val(){
    QueryTest test=(seqMonitor,argType,queryCastType,seqLocation,seqSize
    )->{
      if(seqSize>0){
        {
          switch(seqLocation){
            case BEGINNING:
              argType.initContainsBeginning(seqMonitor,seqSize,seqMonitor.nestedType.forwardIteration);
              break;
            case NEARBEGINNING:
              argType.initContainsNearBeginning(seqMonitor,seqSize,seqMonitor.nestedType.forwardIteration);
              break;
            case MIDDLE:
              argType.initContainsMiddle(seqMonitor,seqSize,seqMonitor.nestedType.forwardIteration);
              break;
            case NEAREND:
              argType.initContainsNearEnd(seqMonitor,seqSize,seqMonitor.nestedType.forwardIteration);
              break;
            case END:
              argType.initContainsEnd(seqMonitor,seqSize,seqMonitor.nestedType.forwardIteration);
              break;
            case IOBHI:
              argType.initDoesNotContain(seqMonitor,seqSize);
              break;
            default:
              throw new Error("Unknown seqLocation "+seqLocation);
          }
        }
      }
      boolean expectedResult;
      Assertions.assertEquals(expectedResult=seqLocation!=SequenceLocation.IOBHI,argType.invokeremoveVal(seqMonitor,queryCastType));
      if(expectedResult){
        --seqSize;
      }
      seqMonitor.verifyStructuralIntegrity();
      seqMonitor.verifyPreAlloc().skip(seqSize).verifyPostAlloc();
    };
    test.runCollectionTests();
  }
  @org.junit.jupiter.api.Test
  public void testsearch_val(){
    QueryTest test=(seqMonitor,argType,queryCastType,seqLocation,seqSize
    )->{
      int expectedIndex;
      if(seqSize>0){
        {
          switch(seqLocation){
            case BEGINNING:
              expectedIndex=argType.initContainsBeginning(seqMonitor,seqSize,seqMonitor.nestedType.forwardIteration);
              break;
            case NEARBEGINNING:
              expectedIndex=argType.initContainsNearBeginning(seqMonitor,seqSize,seqMonitor.nestedType.forwardIteration);
              break;
            case MIDDLE:
              expectedIndex=argType.initContainsMiddle(seqMonitor,seqSize,seqMonitor.nestedType.forwardIteration);
              break;
            case NEAREND:
              expectedIndex=argType.initContainsNearEnd(seqMonitor,seqSize,seqMonitor.nestedType.forwardIteration);
              break;
            case END:
              expectedIndex=argType.initContainsEnd(seqMonitor,seqSize,seqMonitor.nestedType.forwardIteration);
              break;
            case IOBHI:
              argType.initDoesNotContain(seqMonitor,seqSize);
              expectedIndex=-1;
              break;
            default:
              throw new Error("Unknown seqLocation "+seqLocation);
          }
        }
      }else{
        expectedIndex=-1;
      }
      Assertions.assertEquals(expectedIndex,argType.invokesearch(seqMonitor,queryCastType));
      seqMonitor.verifyStructuralIntegrity();
      seqMonitor.verifyPreAlloc().skip(seqSize).verifyPostAlloc();
    };
    test.runStackTests();
  }
  @org.junit.jupiter.api.Test
  public void testcontains_val(){
    QueryTest test=(seqMonitor,argType,queryCastType,seqLocation,seqSize
    )->{
      if(seqSize>0){
        {
          switch(seqLocation){
            case BEGINNING:
              argType.initContainsBeginning(seqMonitor,seqSize,seqMonitor.nestedType.forwardIteration);
              break;
            case NEARBEGINNING:
              argType.initContainsNearBeginning(seqMonitor,seqSize,seqMonitor.nestedType.forwardIteration);
              break;
            case MIDDLE:
              argType.initContainsMiddle(seqMonitor,seqSize,seqMonitor.nestedType.forwardIteration);
              break;
            case NEAREND:
              argType.initContainsNearEnd(seqMonitor,seqSize,seqMonitor.nestedType.forwardIteration);
              break;
            case END:
              argType.initContainsEnd(seqMonitor,seqSize,seqMonitor.nestedType.forwardIteration);
              break;
            case IOBHI:
              argType.initDoesNotContain(seqMonitor,seqSize);
              break;
            default:
              throw new Error("Unknown seqLocation "+seqLocation);
          }
        }
      }
      Assertions.assertEquals(seqLocation!=SequenceLocation.IOBHI,argType.invokecontains(seqMonitor,queryCastType));
      seqMonitor.verifyStructuralIntegrity();
      seqMonitor.verifyPreAlloc().skip(seqSize).verifyPostAlloc();
    };
    test.runCollectionTests();
  }
  @org.junit.jupiter.api.Test
  public void testtoArray_IntFunction(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var monitoredFunctionGen:MonitoredFunctionGen.values()){
          if((checkedType.checked || monitoredFunctionGen.expectedException==null)&&monitoredFunctionGen.appliesToRoot){
            for(int numToAdd:AbstractDoubleSeqMonitor.FIB_SEQ){
              submitTest(()->{
                var seqMonitor=new SeqMonitor(nestedType,checkedType);
                for(int i=0;i<numToAdd;++i){
                  seqMonitor.add(i);
                }
                var arrConstructor=monitoredFunctionGen.getMonitoredArrayConstructor(seqMonitor);
                if(monitoredFunctionGen.expectedException==null){
                  var resultArr=seqMonitor.seq.toArray(arrConstructor);
                  Assertions.assertEquals(numToAdd,resultArr.length);
                  var itr=seqMonitor.seq.iterator();
                  for(int i=0;i<numToAdd;++i){
                    Assertions.assertEquals(resultArr[i],(Object)itr.nextDouble());
                  }
                }else{
                   Assertions.assertThrows(monitoredFunctionGen.expectedException,()->seqMonitor.seq.toArray(arrConstructor));
                }
                seqMonitor.verifyStructuralIntegrity();
                var verifyItr=seqMonitor.verifyPreAlloc();
                switch(monitoredFunctionGen){
                  case NoThrow:
                  case Throw:
                    verifyItr.verifyNaturalAscending(numToAdd);
                    break;
                  case ModSeq:
                  case ThrowModSeq:
                    if(seqMonitor.nestedType.forwardIteration){
                      verifyItr.verifyAscending(numToAdd).verifyIllegalAdd();
                    }else{
                      verifyItr.verifyIllegalAdd().verifyDescending(numToAdd);
                    }
                    break;
                  default:
                    throw new Error("Unknown monitoredFunctionGen "+monitoredFunctionGen);
                }
                verifyItr.verifyPostAlloc();
              });
            }
          }
        }   
      }
    }
    completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testtoArray_ObjectArray(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(int tmpSeqSize=0;tmpSeqSize<=15;tmpSeqSize+=5){
          final int seqSize=tmpSeqSize;
          for(int tmpArrSize=0;tmpArrSize<=seqSize+5;tmpArrSize+=5){
            final int arrSize=tmpArrSize;
            submitTest(()->{
              var seqMonitor=new SeqMonitor(nestedType,checkedType);
              for(int i=0;i<seqSize;++i){
                seqMonitor.add(i);
              }
              Double[] paramArr=new Double[arrSize];
              for(int i=seqSize,bound=seqSize+arrSize;i<bound;++i){
                paramArr[i-seqSize]=TypeConversionUtil.convertToDouble(i);
              }
              var resultArr=seqMonitor.seq.toArray(paramArr);
              seqMonitor.verifyStructuralIntegrity();
              if(arrSize<seqSize){
                Assertions.assertNotSame(paramArr,resultArr);
                Assertions.assertEquals(seqSize,resultArr.length);
              }
              else if(arrSize>seqSize){
                Assertions.assertSame(paramArr,resultArr);
                Assertions.assertNull(resultArr[seqSize]);
                for(int i=seqSize+1;i<arrSize;++i){
                  Assertions.assertEquals(TypeConversionUtil.convertToDouble(i+seqSize),resultArr[i]);
                }
              }else{
                Assertions.assertSame(paramArr,resultArr);
              }
              var itr=seqMonitor.seq.iterator();
              for(int i=0;i<seqSize;++i){
                Assertions.assertEquals((Object)itr.nextDouble(),resultArr[i]);
              }
              seqMonitor.verifyPreAlloc().verifyNaturalAscending(seqSize).verifyPostAlloc();
            });
          }
        }
      }
    }
    completeAllTests();
  }
  interface OutputTest{
    void runTest(SeqMonitor seqMonitor,DoubleOutputTestArgType outputArgType);
    private void runTests(){
      for(var nestedType:NestedType.values()){
        for(var checkedType:CheckedType.values()){
          for(var outputArgType:DoubleOutputTestArgType.values()){
            submitTest(()->runTest(new SeqMonitor(nestedType,checkedType),outputArgType));
          }
        }
      }
      completeAllTests();
    }
  }
  @org.junit.jupiter.api.Test
  public void testpop_void(){
    OutputTest test=(seqMonitor,outputArgType)->{
      for(int i=0;i<100;++i){
        seqMonitor.add(i);
      }
      if(seqMonitor.nestedType==NestedType.STACK)
      {
        if(seqMonitor.nestedType.forwardIteration){
          for(int i=0;i<100;++i){
            seqMonitor.pop(i,outputArgType);
            seqMonitor.verifyStructuralIntegrity();
          }
        }else{
          for(int i=100;--i>=0;){
            seqMonitor.pop(i,outputArgType);
            seqMonitor.verifyStructuralIntegrity();
          }
        }
        if(seqMonitor.checkedType.checked){
          Assertions.assertThrows(NoSuchElementException.class,()->seqMonitor.pop(0,outputArgType));
          seqMonitor.verifyStructuralIntegrity();
        }
      }
      else
      {
        if(seqMonitor.nestedType.forwardIteration){
          for(int i=0;i<100;++i){
            seqMonitor.queueRemove(i,outputArgType);
            seqMonitor.verifyStructuralIntegrity();
          }
        }else{
          for(int i=100;--i>=0;){
            seqMonitor.queueRemove(i,outputArgType);
            seqMonitor.verifyStructuralIntegrity();
          }
        }
        if(seqMonitor.checkedType.checked){
          Assertions.assertThrows(NoSuchElementException.class,()->seqMonitor.queueRemove(0,outputArgType));
          seqMonitor.verifyStructuralIntegrity();
        }
      }
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testpeek_void(){
    OutputTest test=(seqMonitor,outputArgType)->{
      if(seqMonitor.nestedType==NestedType.STACK)
      {
        if(seqMonitor.nestedType.forwardIteration){
          for(int i=0;i<100;++i){
            seqMonitor.add(i);
          }
          for(int i=0;i<100;++i){
            outputArgType.verifyPeek(seqMonitor.seq,100-i,i);
            seqMonitor.verifyStructuralIntegrity();
            seqMonitor.pop(i,outputArgType);
          }
          outputArgType.verifyPeek(seqMonitor.seq,0,0);
          seqMonitor.verifyStructuralIntegrity();
        }else{
          for(int i=0;i<100;){
            outputArgType.verifyPeek(seqMonitor.seq,i,i);
            seqMonitor.verifyStructuralIntegrity();
            seqMonitor.add(++i);
          }
        }
      }
      else
      {
        if(seqMonitor.nestedType.forwardIteration){
          for(int i=0;i<100;++i){
            seqMonitor.add(i);
          }
          for(int i=0;i<100;++i){
            outputArgType.verifyPeek(seqMonitor.seq,100-i,i);
            seqMonitor.verifyStructuralIntegrity();
            seqMonitor.queueRemove(i,outputArgType);
          }
          outputArgType.verifyPeek(seqMonitor.seq,0,0);
          seqMonitor.verifyStructuralIntegrity();
        }else{
          for(int i=0;i<100;){
            outputArgType.verifyPeek(seqMonitor.seq,i,i);
            seqMonitor.verifyStructuralIntegrity();
            seqMonitor.add(++i);
          }
        }
      }
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testpoll_void(){
    OutputTest test=(seqMonitor,outputArgType)->{
      for(int i=0;i<100;++i){
        seqMonitor.add(i);
      }
      if(seqMonitor.nestedType.forwardIteration){
        for(int i=0;i<100;++i){
          seqMonitor.poll(i,outputArgType);
          seqMonitor.verifyStructuralIntegrity();
        }
      }else{
        for(int i=100;--i>=0;){
          seqMonitor.poll(i,outputArgType);
          seqMonitor.verifyStructuralIntegrity();
        }
      }
      seqMonitor.poll(0,outputArgType);
      seqMonitor.verifyStructuralIntegrity();
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testQueueelement_void(){
    for(var checkedType:CheckedType.values()){
      for(var outputArgType:DoubleOutputTestArgType.values()){
        submitTest(()->{
          var seqMonitor=new SeqMonitor(NestedType.QUEUE,checkedType);
          for(int i=0;i<100;++i){
            seqMonitor.add(i);
          }
          for(int i=0;i<100;++i){
            outputArgType.verifyQueueElement(seqMonitor.seq,i);
            seqMonitor.verifyStructuralIntegrity();
            seqMonitor.queueRemove(i,outputArgType);
          }
          if(seqMonitor.checkedType.checked){
            Assertions.assertThrows(NoSuchElementException.class,()->outputArgType.verifyQueueElement(seqMonitor.seq,0));
            seqMonitor.verifyStructuralIntegrity();
          }
        });
      }
    }
    completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testtoArray_void(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(int numToAdd:AbstractDoubleSeqMonitor.FIB_SEQ){
          for(var outputArgType:DoubleOutputTestArgType.values()){
            submitTest(()->{
              var seqMonitor=new SeqMonitor(nestedType,checkedType);
              for(int i=0;i<numToAdd;++i){
                seqMonitor.add(i);
              }
              outputArgType.verifyToArray(seqMonitor.seq,numToAdd);
              seqMonitor.verifyStructuralIntegrity();
              seqMonitor.verifyPreAlloc().verifyNaturalAscending(numToAdd).verifyPostAlloc();
            });
          }
        }
      }
    }
    completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testreadAndwriteObject(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var monitoredFunctionGen:MonitoredFunctionGen.values()){
          if((checkedType.checked || monitoredFunctionGen.expectedException==null)&&(monitoredFunctionGen.appliesToRoot)){
            for(int seqSize:AbstractDoubleSeqMonitor.FIB_SEQ){
              submitTest(()->testreadAndwriteObjectHelper(new SeqMonitor(nestedType,checkedType),monitoredFunctionGen,seqSize
              ));
            }
          }
        }
      }
    }
    completeAllTests();
  }
  private static void testreadAndwriteObjectHelper(SeqMonitor seqMonitor,MonitoredFunctionGen monitoredFunctionGen,int numToAdd
  )
  {
    for(int i=0;i<numToAdd;++i){
      seqMonitor.add(i);
    }
    final File file;
    try{
      file=Files.createTempFile(null,null).toFile();
    }catch(Exception e){
      Assertions.fail(e);
      return;
    }
    if(monitoredFunctionGen.expectedException==null){
      try(var oos=new ObjectOutputStream(new FileOutputStream(file));){
        oos.writeObject(seqMonitor.seq);
      }catch(Exception e){
        Assertions.fail(e);
      }
      seqMonitor.verifyPreAlloc().verifyNaturalAscending(numToAdd).verifyPostAlloc();
      OmniCollection.OfDouble readCol=null;
      try(var ois=new ObjectInputStream(new FileInputStream(file));){
        readCol=(OmniCollection.OfDouble)ois.readObject();
      }catch(Exception e){
        Assertions.fail(e);
        return;
      }
      var itr=readCol.iterator();
      if(seqMonitor.nestedType.forwardIteration){
        for(int i=0;i<numToAdd;++i){
          Assertions.assertEquals(TypeConversionUtil.convertTodouble(i),itr.nextDouble());
        }
      }else{
        for(int i=0;i<numToAdd;++i){
          Assertions.assertEquals(TypeConversionUtil.convertTodouble(numToAdd-i-1),itr.nextDouble());
        }
      }
      Assertions.assertFalse(itr.hasNext());
    }else{
      Assertions.assertThrows(monitoredFunctionGen.expectedException,()->{
        try(var moos=monitoredFunctionGen.getMonitoredObjectOutputStream(file,seqMonitor);){
          seqMonitor.writeObject(moos);
        }
      });
    }
    seqMonitor.verifyStructuralIntegrity();
  }
  @org.junit.jupiter.api.Test
  public void testStackpush_val(){
    for(var checkedType:CheckedType.values()){
      for(var inputArgType:DoubleInputTestArgType.values()){
        submitTest(()->{
          var seqMonitor=new SeqMonitor(NestedType.STACK,checkedType);
          for(int i=0;i<100;++i){
            seqMonitor.push(i,inputArgType);
            seqMonitor.verifyStructuralIntegrity();
          }
          seqMonitor.verifyPreAlloc().verifyNaturalAscending(inputArgType,100).verifyPostAlloc();
        });
      }
    }
    completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testQueueoffer_val(){
    for(var checkedType:CheckedType.values()){
      for(var inputArgType:DoubleInputTestArgType.values()){
        submitTest(()->{
          var seqMonitor=new SeqMonitor(NestedType.QUEUE,checkedType);
          for(int i=0;i<100;++i){
            Assertions.assertTrue(seqMonitor.offer(i,inputArgType));
            seqMonitor.verifyStructuralIntegrity();
          }
          seqMonitor.verifyPreAlloc().verifyNaturalAscending(inputArgType,100).verifyPostAlloc();
        });
      }
    }
    completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testadd_val(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var inputArgType:DoubleInputTestArgType.values()){
          submitTest(()->{
            var seqMonitor=new SeqMonitor(nestedType,checkedType);
            for(int i=0;i<100;++i){
              Assertions.assertTrue(seqMonitor.add(i,inputArgType));
              seqMonitor.verifyStructuralIntegrity();
            }
            seqMonitor.verifyPreAlloc().verifyNaturalAscending(inputArgType,100).verifyPostAlloc();
          });
        }
      }
    }
    completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testforEach_Consumer(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var monitoredFunctionGen:MonitoredFunctionGen.values()){
          if(monitoredFunctionGen.appliesToRoot&&(checkedType.checked || monitoredFunctionGen.expectedException==null)){
            for(int seqSize:AbstractDoubleSeqMonitor.FIB_SEQ){
              submitTest(()->testforEach_ConsumerHelper(new SeqMonitor(nestedType,checkedType),monitoredFunctionGen,seqSize,FunctionCallType.Unboxed));
              submitTest(()->testforEach_ConsumerHelper(new SeqMonitor(nestedType,checkedType),monitoredFunctionGen,seqSize,FunctionCallType.Boxed));
            }
          }
        }
      }
    }
    completeAllTests();
  }
  private static void testforEach_ConsumerHelper(SeqMonitor seqMonitor,MonitoredFunctionGen monitoredFunctionGen,int numToAdd,FunctionCallType functionCallType){
    for(int i=0;i<numToAdd;++i){
      seqMonitor.add(i);
    }
    var monitoredConsumer=monitoredFunctionGen.getMonitoredConsumer(seqMonitor);
    int numExpectedIteratedValues;
    if(monitoredFunctionGen.expectedException==null || numToAdd==0){
      seqMonitor.forEach(monitoredConsumer,functionCallType);
      seqMonitor.verifyStructuralIntegrity();
      seqMonitor.verifyPreAlloc().verifyNaturalAscending(numToAdd).verifyPostAlloc();
      numExpectedIteratedValues=numToAdd;
    }else{
      Assertions.assertThrows(monitoredFunctionGen.expectedException,()->seqMonitor.forEach(monitoredConsumer,functionCallType));
      seqMonitor.verifyStructuralIntegrity();
      var verifyItr=seqMonitor.verifyPreAlloc();
      switch(monitoredFunctionGen){
        case Throw:
          numExpectedIteratedValues=1;
          verifyItr.verifyNaturalAscending(numToAdd);
          break;
        case ModSeq:
          numExpectedIteratedValues=numToAdd;
          if(seqMonitor.nestedType.forwardIteration){
            verifyItr.verifyAscending(numToAdd);
            for(int i=0;i<numToAdd;++i){
              verifyItr.verifyIllegalAdd();
            }
          }else{
            for(int i=0;i<numToAdd;++i){
              verifyItr.verifyIllegalAdd();
            }
            verifyItr.verifyDescending(numToAdd);
          }
          break;
        case ThrowModSeq:
          numExpectedIteratedValues=1;
          if(seqMonitor.nestedType.forwardIteration){
            verifyItr.verifyAscending(numToAdd).verifyIllegalAdd();
          }else{
            verifyItr.verifyIllegalAdd().verifyDescending(numToAdd);
          }
          break;
        default:
          throw new Error("Unknown monitored function gen "+monitoredFunctionGen);
      }
      verifyItr.verifyPostAlloc();
    }
    Assertions.assertEquals(numExpectedIteratedValues,monitoredConsumer.encounteredValues.size());
    //TODO verify iterated values
  }
  @org.junit.jupiter.api.Test
  public void testItrforEachRemaining_Consumer(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var preModScenario:PreModScenario.values()){
          if(checkedType.checked || preModScenario.expectedException==null){
            for(var monitoredFunctionGen:MonitoredFunctionGen.values()){
              if((monitoredFunctionGen.expectedException==null || checkedType.checked) && (preModScenario.appliesToRootItr&&monitoredFunctionGen.appliesToRootItr)){
                for(int seqSize:AbstractDoubleSeqMonitor.FIB_SEQ){
                  submitTest(()->testItrforEachRemaining_ConsumerHelper(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredFunctionGen,seqSize,FunctionCallType.Unboxed));
                  submitTest(()->testItrforEachRemaining_ConsumerHelper(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredFunctionGen,seqSize,FunctionCallType.Boxed));
                }
              }
            }
          }
        }
      }
    }
    completeAllTests();
  }
  private static void testItrforEachRemaining_ConsumerHelper(SeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredFunctionGen monitoredFunctionGen,int numToAdd,FunctionCallType functionCallType){
    for(int i=0;i<numToAdd;++i){
      seqMonitor.add(i);
    }
    var itrMonitor=seqMonitor.getItrMonitor();
    seqMonitor.illegalAdd(preModScenario);
    var monitoredConsumer=monitoredFunctionGen.getMonitoredConsumer(itrMonitor);
    int numExpectedIteratedValues;
    if(preModScenario.expectedException==null || numToAdd==0){
      if(monitoredFunctionGen.expectedException==null || numToAdd==0){
        itrMonitor.forEachRemaining(monitoredConsumer,functionCallType);
        itrMonitor.verifyIteratorState();
        seqMonitor.verifyStructuralIntegrity();
        seqMonitor.verifyPreAlloc(preModScenario).verifyNaturalAscending(numToAdd).verifyPostAlloc(preModScenario);
        numExpectedIteratedValues=numToAdd;
      }else{
        Assertions.assertThrows(monitoredFunctionGen.expectedException,()->itrMonitor.forEachRemaining(monitoredConsumer,functionCallType));
        seqMonitor.verifyStructuralIntegrity();
        itrMonitor.verifyIteratorState();
        switch(monitoredFunctionGen){
          case ThrowModItr:
            numExpectedIteratedValues=1;
            var verifyItr=seqMonitor.verifyPreAlloc(preModScenario);
            if(seqMonitor.nestedType.forwardIteration){
              verifyItr.verifyAscending(1,numToAdd-1);
            }else{
              verifyItr.verifyDescending(numToAdd-1);
            }
            verifyItr.verifyPostAlloc(preModScenario);
            break;
          case ModItr:
            numExpectedIteratedValues=numToAdd;
            seqMonitor.verifyPreAlloc(preModScenario).verifyPostAlloc(preModScenario);
            break;
          case Throw:
            numExpectedIteratedValues=1;
            seqMonitor.verifyPreAlloc(preModScenario).verifyNaturalAscending(numToAdd).verifyPostAlloc(preModScenario);
            break;
          case ModSeq:
            numExpectedIteratedValues=numToAdd;
            verifyItr=seqMonitor.verifyPreAlloc(preModScenario);
            if(seqMonitor.nestedType.forwardIteration){
              verifyItr.verifyAscending(numToAdd);
              for(int i=0;i<numToAdd;++i){
                verifyItr.verifyIllegalAdd();
              }
            }else{
              for(int i=0;i<numToAdd;++i){
                verifyItr.verifyIllegalAdd();
              }
              verifyItr.verifyDescending(numToAdd);
            }
            verifyItr.verifyPostAlloc(preModScenario);
            break;
          case ThrowModSeq:
            numExpectedIteratedValues=1;
            seqMonitor.verifyPreAlloc(PreModScenario.ModSeq).verifyNaturalAscending(numToAdd).verifyPostAlloc(PreModScenario.ModSeq);
            break;
          default:
            throw new Error("Unknown monitored function gen "+monitoredFunctionGen);
        }
      }
    }else{
      Assertions.assertThrows(preModScenario.expectedException,()->itrMonitor.forEachRemaining(monitoredConsumer,functionCallType));
      seqMonitor.verifyStructuralIntegrity();
      itrMonitor.verifyIteratorState();
      switch(monitoredFunctionGen){
        case ThrowModItr:
          numExpectedIteratedValues=1;
          seqMonitor.verifyPreAlloc(preModScenario).verifyNaturalAscending(numToAdd).verifyPostAlloc(preModScenario);
          break;
        case ModItr:
          numExpectedIteratedValues=1;
          //verification in tis situation is tricky. Just skip it
          break;
        case NoThrow:
          numExpectedIteratedValues=numToAdd;
          if(preModScenario==PreModScenario.ModSeq && seqMonitor.nestedType.forwardIteration){
            ++numExpectedIteratedValues;
          }
          seqMonitor.verifyPreAlloc(preModScenario).verifyNaturalAscending(numToAdd).verifyPostAlloc(preModScenario);
          break;
        case Throw:
          numExpectedIteratedValues=1;
          seqMonitor.verifyPreAlloc(preModScenario).verifyNaturalAscending(numToAdd).verifyPostAlloc(preModScenario);
          break;
        case ModSeq:
          var verifyItr=seqMonitor.verifyPreAlloc(preModScenario);
          if(seqMonitor.nestedType.forwardIteration){
            verifyItr.verifyAscending(numToAdd);
            if(preModScenario==PreModScenario.ModSeq){
              numExpectedIteratedValues=numToAdd+1;
            }else{
              numExpectedIteratedValues=1;
            }
            for(int i=0;i<numExpectedIteratedValues;++i){
              verifyItr.verifyIllegalAdd();
            }
          }else{
            if(preModScenario==PreModScenario.ModSeq){
              numExpectedIteratedValues=numToAdd;
            }else{
              numExpectedIteratedValues=1;
            }
            for(int i=0;i<numExpectedIteratedValues;++i){
              verifyItr.verifyIllegalAdd();
            }
            verifyItr.verifyDescending(numToAdd);
          }
          verifyItr.verifyPostAlloc(preModScenario);
          break;
        case ThrowModSeq:
          verifyItr=seqMonitor.verifyPreAlloc(preModScenario);
          if(seqMonitor.nestedType.forwardIteration){
            verifyItr.verifyAscending(numToAdd).verifyIllegalAdd();
          }else{
            verifyItr.verifyIllegalAdd().verifyDescending(numToAdd);
          }
          verifyItr.verifyPostAlloc(preModScenario);
          numExpectedIteratedValues=1;
          break;
        default:
          throw new Error("Unknown monitored function gen "+monitoredFunctionGen);
      }
    }
    Assertions.assertEquals(numExpectedIteratedValues,monitoredConsumer.encounteredValues.size());
    //TODO verify the iterated values
  }
  @org.junit.jupiter.api.Test
  public void testItrremove_void(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var removeScenario:ItrRemoveScenario.values()){
          if(removeScenario.validWithForwardItr && (checkedType.checked || removeScenario.expectedException==null)){
            for(int numToAdd:AbstractDoubleSeqMonitor.FIB_SEQ){
              if(numToAdd!=0 || removeScenario.validWithEmptySeq){
                for(var preModScenario:PreModScenario.values()){
                  if(preModScenario.appliesToRootItr && (checkedType.checked || preModScenario.expectedException==null)){
                    for(var seqLocation:SequenceLocation.values()){
                      if(seqLocation.expectedException==null &&
                        (numToAdd!=0 || seqLocation==SequenceLocation.BEGINNING) &&
                        (seqLocation!=SequenceLocation.END || removeScenario!=ItrRemoveScenario.PostInit)&&
                        (removeScenario!=ItrRemoveScenario.PostInit || seqLocation==SequenceLocation.BEGINNING)
                      ){
                        submitTest(()->{
                          var seqMonitor=new SeqMonitor(nestedType,checkedType);
                          for(int i=0;i<numToAdd;++i){
                            seqMonitor.add(i);
                          }
                          var itrMonitor=seqMonitor.getItrMonitor();
                          int numIterated=0;
                          switch(seqLocation){
                            case BEGINNING:
                              break;
                            case NEARBEGINNING:
                              for(int i=0,bound=numToAdd/4;i<bound;++i){
                                ++numIterated;
                                itrMonitor.iterateForward();
                              }
                              break;
                            case MIDDLE:
                              for(int i=0,bound=numToAdd/2;i<bound;++i){
                                ++numIterated;
                                itrMonitor.iterateForward();
                              }
                              break;
                            case NEAREND:
                              for(int i=0,bound=(numToAdd/4)*3;i<bound;++i){
                                ++numIterated;
                                itrMonitor.iterateForward();
                              }
                              break;
                            case END:
                              for(int i=0;i<numToAdd;++i){
                                ++numIterated;
                                itrMonitor.iterateForward();
                              }
                              break;
                            default:
                              throw new Error("Unknown seqLocation "+seqLocation);
                          }
                          switch(removeScenario){
                            case PostNext:
                              if(numIterated==0){
                                ++numIterated;
                                itrMonitor.iterateForward();
                              }
                              break;
                            case PostRemove:
                              if(numIterated==0){
                                ++numIterated;
                                itrMonitor.iterateForward();
                              }
                              itrMonitor.remove();
                            case PostInit:
                              break;
                            default:
                               throw new Error("unknown remove scenario "+removeScenario);
                          }
                          seqMonitor.illegalAdd(preModScenario);
                          SequenceVerificationItr verifyItr;
                          if(removeScenario.expectedException==null){
                            if(preModScenario.expectedException==null){
                              itrMonitor.remove();
                              itrMonitor.verifyIteratorState();
                              seqMonitor.verifyStructuralIntegrity();
                              while(itrMonitor.hasNext()){
                                itrMonitor.iterateForward();
                                itrMonitor.remove();
                                itrMonitor.verifyIteratorState();
                                seqMonitor.verifyStructuralIntegrity();
                              }
                              Assertions.assertFalse(itrMonitor.hasNext());
                              switch(seqLocation){
                                case BEGINNING:
                                  Assertions.assertTrue(seqMonitor.isEmpty());
                                case NEARBEGINNING:
                                case MIDDLE:
                                case NEAREND:
                                case END:
                                  Assertions.assertEquals(numIterated-1,seqMonitor.seq.size());
                                  break;
                                default:
                                  throw new Error("Unknown seqLocation "+seqLocation);
                              }
                              return;
                            }else{
                              Assertions.assertThrows(preModScenario.expectedException,()->itrMonitor.remove());
                              itrMonitor.verifyIteratorState();
                              seqMonitor.verifyStructuralIntegrity();
                              verifyItr=seqMonitor.verifyPreAlloc(preModScenario).verifyNaturalAscending(numToAdd);
                            }
                          }else{
                            Assertions.assertThrows(removeScenario.expectedException,()->itrMonitor.remove());
                            itrMonitor.verifyIteratorState();
                            seqMonitor.verifyStructuralIntegrity();
                            verifyItr=seqMonitor.verifyPreAlloc(preModScenario);
                            switch(removeScenario){
                              case PostInit:
                                verifyItr.verifyNaturalAscending(numToAdd);
                                break;
                              case PostRemove:
                                if(seqMonitor.nestedType.forwardIteration){
                                  verifyItr.verifyAscending(numIterated-1).verifyAscending(numIterated,numToAdd-numIterated);
                                }else{
                                  verifyItr.verifyDescending(numToAdd,numIterated-1).verifyDescending(numToAdd-numIterated);
                                }
                                break;
                              default:
                                throw new Error("unknown remove scenario "+removeScenario);
                            }
                          }
                          verifyItr.verifyPostAlloc(preModScenario);
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
    completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testItrnext_void(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var itrScenario:IterationScenario.values()){
          if(checkedType.checked || itrScenario==IterationScenario.NoMod){
            for(int numToAdd:AbstractDoubleSeqMonitor.FIB_SEQ){
              if(numToAdd!=0 || itrScenario.validWithEmptySeq){
                if(itrScenario.preModScenario.appliesToRootItr){
                  for(var outputType:DoubleOutputTestArgType.values()){
                    submitTest(()->{
                      var seqMonitor=new SeqMonitor(nestedType,checkedType);
                      for(int i=0;i<numToAdd;++i){
                        seqMonitor.add(i);
                      }
                      var itrMonitor=seqMonitor.getItrMonitor();
                      switch(itrScenario){
                        case NoMod:
                        case ModSeqSupercedesThrowNSEE:
                          for(int i=0;;++i){
                            if(i==numToAdd){
                              Assertions.assertFalse(itrMonitor.hasNext());
                              break;
                            }
                            Assertions.assertTrue(itrMonitor.hasNext());
                            itrMonitor.verifyNext(seqMonitor.nestedType==NestedType.STACK?numToAdd-i-1:i,outputType);
                            itrMonitor.verifyIteratorState();
                            seqMonitor.verifyStructuralIntegrity();
                          }
                        case ModSeq:
                          break;
                        default:
                          throw new Error("unknown itr scenario "+itrScenario);
                      }
                      if(seqMonitor.checkedType.checked){
                        seqMonitor.illegalAdd(itrScenario.preModScenario);
                        Assertions.assertThrows(itrScenario.expectedException,()->itrMonitor.iterateForward());
                      }
                      itrMonitor.verifyIteratorState();
                      seqMonitor.verifyStructuralIntegrity();
                      seqMonitor.verifyPreAlloc(itrScenario.preModScenario).verifyNaturalAscending(numToAdd).verifyPostAlloc(itrScenario.preModScenario);
                    });
                  }
                }
              }
            }
          }
        }
      }
    }
    completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testConstructor_void(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        submitTest(()->{
          var seqMonitor=new SeqMonitor(nestedType,checkedType);
          if(seqMonitor.checkedType.checked){
            Assertions.assertEquals(0,seqMonitor.nestedType==NestedType.QUEUE?FieldAndMethodAccessor.DoubleSnglLnkSeq.CheckedQueue.modCount(seqMonitor.seq):FieldAndMethodAccessor.DoubleSnglLnkSeq.CheckedStack.modCount(seqMonitor.seq));
          }
          Assertions.assertEquals(0,seqMonitor.seq.size);
          Assertions.assertNull(seqMonitor.seq.head);
        });
      }
    }
    completeAllTests();
  }
  private static class SeqMonitor extends AbstractDoubleSeqMonitor<DoubleSnglLnkSeq>{
    NestedType nestedType;
    SeqMonitor(NestedType nestedType,CheckedType checkedType){
      super(checkedType);
      this.nestedType=nestedType;
      switch(nestedType){
        case QUEUE:
          this.seq=checkedType.checked?new DoubleSnglLnkSeq.CheckedQueue():new DoubleSnglLnkSeq.UncheckedQueue();
          break;
        case STACK:
          this.seq=checkedType.checked?new DoubleSnglLnkSeq.CheckedStack():new DoubleSnglLnkSeq.UncheckedStack();
          break;
        default:
          throw new Error("unknown nested type "+nestedType);
      }
    }
    void illegalAdd(PreModScenario preModScenario){
      switch(preModScenario){
        case ModSeq:
          DoubleInputTestArgType.ARRAY_TYPE.callCollectionAdd(seq,0);
          ++expectedSeqModCount;
          ++expectedSeqSize;
          break;
        case NoMod:
          break;
        default:
          throw new Error("Unknown preModScenario "+preModScenario);
      }
    }
    void verifyAddition()
    {
      ++expectedSeqSize;
      ++expectedSeqModCount;
    }
    public String toString(){
      StringBuilder builder=new StringBuilder();
      builder.append("DoubleSnglLnkSeq.").append(checkedType.checked?"Checked":"Unchecked");
      switch(nestedType){
        case STACK:
          builder.append("Stack");
          break;
        case QUEUE:
          builder.append("Queue");
          break;
        default:
          throw new Error("Unknown nestedType "+nestedType);
      }
      return builder.toString();
    }
    void verifyBatchRemove(int numRemoved){
       expectedSeqSize-=numRemoved;
    }
    void writeObject(ObjectOutputStream oos) throws IOException{
      switch(nestedType){
        case QUEUE:
          if(checkedType.checked){
            FieldAndMethodAccessor.DoubleSnglLnkSeq.CheckedQueue.writeObject(seq,oos);
          }else{
            FieldAndMethodAccessor.DoubleSnglLnkSeq.UncheckedQueue.writeObject(seq,oos);
          }
          break;
        case STACK:
          if(checkedType.checked){
            FieldAndMethodAccessor.DoubleSnglLnkSeq.CheckedStack.writeObject(seq,oos);
          }else{
            FieldAndMethodAccessor.DoubleSnglLnkSeq.UncheckedStack.writeObject(seq,oos);
          }
          break;
        default:
          throw new Error("unknown nested type "+nestedType);
      }
    }
    void verifyRemoval(){
      ++expectedSeqModCount;
      --expectedSeqSize;
    }
    void verifyStructuralIntegrity(){
      Assertions.assertEquals(expectedSeqSize,seq.size);
      if(checkedType.checked){
        switch(nestedType){
          case QUEUE:
            Assertions.assertEquals(expectedSeqModCount,((DoubleSnglLnkSeq.CheckedQueue)seq).modCount);
            break;
          case STACK:
            Assertions.assertEquals(expectedSeqModCount,((DoubleSnglLnkSeq.CheckedStack)seq).modCount);
            break;
          default:
            throw new Error("Unknown nested type "+nestedType);
        }
      }
      if(expectedSeqSize==0){
        Assertions.assertNull(seq.head);
      }else{
        DoubleSnglLnkNode node;
        Assertions.assertNotNull(node=seq.head);
        int i=expectedSeqSize;
        while(--i!=0){
          Assertions.assertNotNull(node=node.next);
        }
        Assertions.assertNull(node.next);
      }
    }
    class UncheckedSnglLnkSeqItrMonitor extends AbstractDoubleSeqMonitor.AbstractItrMonitor{
      DoubleSnglLnkNode expectedPrev;
      DoubleSnglLnkNode expectedCurr;
      DoubleSnglLnkNode expectedNext;
      UncheckedSnglLnkSeqItrMonitor(){
        super(ItrType.Itr,seq.iterator(),expectedSeqModCount);
        this.expectedNext=seq.head;
      }
      void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
        if(functionCallType==FunctionCallType.Boxed){
          itr.forEachRemaining((Consumer)action);
        }else
        {
          itr.forEachRemaining((DoubleConsumer)action);
        }
        DoubleSnglLnkNode expectedNext;
        if((expectedNext=this.expectedNext)!=null){
          DoubleSnglLnkNode expectedPrev,expectedCurr=this.expectedCurr;
          do{
            expectedPrev=expectedCurr;
          }while((expectedNext=(expectedCurr=expectedNext).next)!=null);
          this.expectedPrev=expectedPrev;
          this.expectedCurr=expectedCurr;
          this.expectedNext=null;
        }
      }
      SeqMonitor getSeqMonitor(){
        return SeqMonitor.this;
      }
      void verifyNext(int expectedVal,DoubleOutputTestArgType outputType){
        outputType.verifyItrNext(itr,expectedVal);
        final DoubleSnglLnkNode expectedNext;
        this.expectedNext=(expectedNext=this.expectedNext).next;
        this.expectedPrev=this.expectedCurr;
        this.expectedCurr=expectedNext;
      }
      void verifyIteratorState(){
        Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.DoubleSnglLnkSeq.AbstractItr.prev(itr));
        Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.DoubleSnglLnkSeq.AbstractItr.curr(itr));
        Assertions.assertSame(expectedNext,FieldAndMethodAccessor.DoubleSnglLnkSeq.AbstractItr.next(itr));
        switch(nestedType)
        {
          case STACK:
            if(checkedType.checked)
            {
              Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleSnglLnkSeq.CheckedStack.Itr.parent(itr));
            }
            else
            {
              Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleSnglLnkSeq.UncheckedStack.Itr.parent(itr));
            }
            break;
          case QUEUE:
            if(checkedType.checked)
            {
              Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleSnglLnkSeq.CheckedQueue.Itr.parent(itr));
            }
            else
            {
              Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleSnglLnkSeq.UncheckedQueue.Itr.parent(itr));
            }
            break;
          default:
            throw new Error("Unknown nested type "+nestedType);
        }
      }
      void iterateForward(){
        itr.next();
        final DoubleSnglLnkNode expectedNext;
        this.expectedNext=(expectedNext=this.expectedNext).next;
        this.expectedPrev=this.expectedCurr;
        this.expectedCurr=expectedNext;
      }
      void remove(){
        itr.remove();
        verifyRemoval();
        this.expectedCurr=this.expectedPrev;
      }
    }
    class CheckedSnglLnkSeqItrMonitor extends UncheckedSnglLnkSeqItrMonitor
    {
      private CheckedSnglLnkSeqItrMonitor(){
        super();
      }
      void verifyIteratorState(){
        Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.DoubleSnglLnkSeq.AbstractItr.prev(itr));
        Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.DoubleSnglLnkSeq.AbstractItr.curr(itr));
        Assertions.assertSame(expectedNext,FieldAndMethodAccessor.DoubleSnglLnkSeq.AbstractItr.next(itr));
        switch(nestedType)
        {
          case STACK:
            if(checkedType.checked)
            {
              Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleSnglLnkSeq.CheckedStack.Itr.parent(itr));
              Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.DoubleSnglLnkSeq.CheckedStack.Itr.modCount(itr));
            }
            else
            {
              Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleSnglLnkSeq.UncheckedStack.Itr.parent(itr));
            }
            break;
          case QUEUE:
            if(checkedType.checked)
            {
              Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleSnglLnkSeq.CheckedQueue.Itr.parent(itr));
              Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.DoubleSnglLnkSeq.CheckedQueue.Itr.modCount(itr));
            }
            else
            {
              Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleSnglLnkSeq.UncheckedQueue.Itr.parent(itr));
            }
            break;
          default:
            throw new Error("Unknown nested type "+nestedType);
        }
      }
      @Override void remove(){
        super.remove();
        ++expectedItrModCount;
      }
    }
    private static class SnglLnkSeqSequenceVerificationItr extends SequenceVerificationItr{
      DoubleSnglLnkNode curr;
      final SeqMonitor seqMonitor;
      private SnglLnkSeqSequenceVerificationItr(SeqMonitor seqMonitor,DoubleSnglLnkNode curr){
        this.seqMonitor=seqMonitor;
        this.curr=curr;
      }
      @Override SequenceVerificationItr verifyPostAlloc(int expectedVal){
        Assertions.assertNull(curr);
        return this;
      }
      @Override void verifyLiteralIndexAndIterate(double val){
        Assertions.assertEquals(val,curr.val);
        curr=curr.next;
      }
      @Override void verifyIndexAndIterate(DoubleInputTestArgType inputArgType,int val){
        inputArgType.verifyVal(val,curr.val);
        curr=curr.next;
      }
      @Override SequenceVerificationItr getOffset(int i){
        if(i<0){
          throw new Error("offset cannot be negative: "+i);
        }
        var currCopy=curr;
        while(i>0)
        {
          --i;
          currCopy=currCopy.next;
        }
        return new SnglLnkSeqSequenceVerificationItr(seqMonitor,currCopy);
      }
      @Override SequenceVerificationItr skip(int i){
        if(i<0){
          throw new Error("offset cannot be negative: "+i);
        }
        while(i>0)
        {
          --i;
          curr=curr.next;
        }
        return this;
      }
      @Override public boolean equals(Object val){
        final SnglLnkSeqSequenceVerificationItr that;
        return val==this || (val instanceof SnglLnkSeqSequenceVerificationItr && (that=(SnglLnkSeqSequenceVerificationItr)val).seqMonitor.seq==this.seqMonitor.seq && that.curr==this.curr);
      }
      @Override SequenceVerificationItr verifyRootPostAlloc(){
        Assertions.assertNull(curr);
        return this;
      }
      @Override SequenceVerificationItr verifyParentPostAlloc(){
        Assertions.assertNull(curr);
        return this;
      }
      @Override SequenceVerificationItr verifyPostAlloc(){
        Assertions.assertNull(curr);
        return this;
      }
      @Override SequenceVerificationItr verifyPostAlloc(PreModScenario preModScenario){
        if(seqMonitor.nestedType.forwardIteration && preModScenario==PreModScenario.ModSeq){
          verifyIllegalAdd();
        }
        Assertions.assertNull(curr);
        return this;
      }
      SequenceVerificationItr verifyNaturalAscending(int v,DoubleInputTestArgType inputArgType,int length)
      {
        if(seqMonitor.nestedType.forwardIteration)
        {
          return verifyAscending(v,inputArgType,length);
        }
        else
        {
          return verifyDescending(v+length,inputArgType,length);
        }
      }
    }
    UncheckedSnglLnkSeqItrMonitor getItrMonitor(){
      return checkedType.checked
        ?new CheckedSnglLnkSeqItrMonitor()
        :new UncheckedSnglLnkSeqItrMonitor();
    }
    SequenceVerificationItr verifyPreAlloc(PreModScenario preModScenario){
      var verifyItr=new SnglLnkSeqSequenceVerificationItr(this,seq.head);
      if(!nestedType.forwardIteration && preModScenario==PreModScenario.ModSeq){
        verifyItr.verifyIllegalAdd();
      }
      return verifyItr;
    }
    SequenceVerificationItr verifyPreAlloc(){
      return new SnglLnkSeqSequenceVerificationItr(this,seq.head);
    }
    SequenceVerificationItr verifyPreAlloc(int expectedVal){
      return new SnglLnkSeqSequenceVerificationItr(this,seq.head);
    }
    void verifyFunctionalModification(){
      ++expectedSeqModCount;
    }
  }
  static enum NestedType{
    QUEUE(true),
    STACK(false);
    final boolean forwardIteration;
    NestedType(boolean forwardIteration){
      this.forwardIteration=forwardIteration;
    }
  }
}
