package omni.impl.seq;
import omni.function.ByteConsumer;
import java.util.function.Consumer;
import java.io.IOException;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import omni.impl.ByteInputTestArgType;
import omni.impl.ByteOutputTestArgType;
import java.util.NoSuchElementException;
import omni.util.OmniArray;
import omni.impl.ByteSnglLnkNode;
import omni.impl.FunctionCallType;
import omni.impl.QueryCastType;
import java.io.File;
import org.junit.jupiter.api.Tag;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import omni.impl.seq.AbstractByteSeqMonitor.MonitoredFunctionGen;
import omni.impl.seq.AbstractByteSeqMonitor.MonitoredRemoveIfPredicateGen;
import omni.impl.seq.AbstractByteSeqMonitor.QueryTester;
import java.nio.file.Files;
import omni.impl.seq.AbstractByteSeqMonitor.SequenceVerificationItr;
import omni.api.OmniCollection;
import java.util.ArrayList;
import omni.util.TestExecutorService;
import omni.impl.CheckedType;
@SuppressWarnings({"rawtypes","unchecked"})
@Tag("SnglLnkSeqTest")
public class ByteSnglLnkSeqTest{
  @org.junit.jupiter.api.AfterEach
  public void verifyAllExecuted(){
    int numTestsRemaining;
    if((numTestsRemaining=TestExecutorService.getNumRemainingTasks())!=0)
    {
      System.err.println("Warning: there were "+numTestsRemaining+" tests that were not completed");
    }
    TestExecutorService.reset();
  }
    private static final int MAX_TOSTRING_LENGTH=4;
  @Tag("MASSIVEtoString")
  @org.junit.jupiter.api.Test
  public void testMASSIVEtoString_void(){
    final int seqLength=(OmniArray.MAX_ARR_SIZE/(MAX_TOSTRING_LENGTH+2))+1;
    var head=new ByteSnglLnkNode(TypeConversionUtil.convertTobyte(1));
    var tail=head;
    int numBatches=100;
    int batchSpan=seqLength/numBatches;
    var wayPointNodes=new ByteSnglLnkNode[numBatches];
    wayPointNodes[0]=head;
    int nextWayPointIndex=batchSpan;
    int batchIndex=0;
    int batchBound=numBatches-1;
    for(int i=1;i<seqLength;++i)
    {
      tail=tail.next=new ByteSnglLnkNode(TypeConversionUtil.convertTobyte(1));
      if(i==nextWayPointIndex){
        wayPointNodes[++batchIndex]=tail;
        if(batchIndex==batchBound){
          nextWayPointIndex=Integer.MIN_VALUE;
        }else{
          nextWayPointIndex+=batchSpan;
        }
      }
    }
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        var seqMonitor=new SeqMonitor(nestedType,checkedType,head,seqLength,tail);
        String string=seqMonitor.seq.toString();
        Assertions.assertEquals('[',string.charAt(0));
        Assertions.assertEquals(']',string.charAt(string.length()-1));
        seqMonitor.verifyStructuralIntegrity();
        for(batchIndex=0,nextWayPointIndex=0;batchIndex<batchBound;++batchIndex){
          var verifyItr=new SeqMonitor.SnglLnkSeqSequenceVerificationItr(seqMonitor,wayPointNodes[batchIndex]);
          final int finalWayPointBound=nextWayPointIndex+batchSpan;
          final int finalWayPointIndex=nextWayPointIndex;
          TestExecutorService.submitTest(()->AbstractByteSeqMonitor.verifyLargeStr(string,finalWayPointIndex,finalWayPointBound,verifyItr));
          nextWayPointIndex=finalWayPointBound;
        }
        var verifyItr=new SeqMonitor.SnglLnkSeqSequenceVerificationItr(seqMonitor,wayPointNodes[batchIndex]);
        final int finalWayPointIndex=nextWayPointIndex;
        TestExecutorService.submitTest(()->AbstractByteSeqMonitor.verifyLargeStr(string,finalWayPointIndex,seqLength,verifyItr));
        TestExecutorService.completeAllTests("ByteSnglLnkSeqTest.testMASSIVEtoString_void checkedType="+checkedType+"; nestedType="+nestedType);
        verifyItr.verifyPostAlloc(1);
      }
    }
  }
  interface BasicCollectionTest{
    void runTest(SeqMonitor seqMonitor,int seqSize);
    private void runTests(){
      for(var nestedType:NestedType.values()){
        for(var checkedType:CheckedType.values()){
          for(int seqSize:AbstractByteSeqMonitor.FIB_SEQ){
            TestExecutorService.submitTest(()->runTest(new SeqMonitor(nestedType,checkedType),seqSize));
          }
        }
      }
      TestExecutorService.completeAllTests();
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
      var clone=(OmniCollection.OfByte)seqMonitor.seq.clone();
      seqMonitor.verifyStructuralIntegrity();
      seqMonitor.verifyPreAlloc().verifyNaturalAscending(numToAdd).verifyPostAlloc();
      Assertions.assertNotSame(clone,seqMonitor.seq);
      switch(seqMonitor.nestedType){
        case STACK:
          if(seqMonitor.checkedType.checked){
            Assertions.assertTrue(clone instanceof ByteSnglLnkSeq.CheckedStack);
            Assertions.assertEquals(0,((ByteSnglLnkSeq.CheckedStack)clone).modCount);
          }else{
            Assertions.assertTrue(clone instanceof ByteSnglLnkSeq.UncheckedStack);
          }
          break;
        case QUEUE:
          if(seqMonitor.checkedType.checked){
            Assertions.assertTrue(clone instanceof ByteSnglLnkSeq.CheckedQueue);
            Assertions.assertEquals(0,((ByteSnglLnkSeq.CheckedQueue)clone).modCount);
          }else{
            Assertions.assertTrue(clone instanceof ByteSnglLnkSeq.UncheckedQueue);
          }
          break;
        default:
          throw new Error("Unknown nested type "+seqMonitor.nestedType);
      }
      var snglLnkSeqClone=(ByteSnglLnkSeq)clone;
      var originalHead=((ByteSnglLnkSeq)seqMonitor.seq).head;
      var cloneHead=snglLnkSeqClone.head;
      Assertions.assertEquals(numToAdd,snglLnkSeqClone.size);
      if(snglLnkSeqClone.size==0){
        Assertions.assertNull(cloneHead);
        if(seqMonitor.nestedType==NestedType.QUEUE){
          if(seqMonitor.checkedType.checked){
            Assertions.assertNull(((ByteSnglLnkSeq.CheckedQueue)snglLnkSeqClone).tail);
          }else{
            Assertions.assertNull(((ByteSnglLnkSeq.UncheckedQueue)snglLnkSeqClone).tail);
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
                Assertions.assertSame(cloneHead,((ByteSnglLnkSeq.CheckedQueue)snglLnkSeqClone).tail);
              }else{
                Assertions.assertSame(cloneHead,((ByteSnglLnkSeq.UncheckedQueue)snglLnkSeqClone).tail);
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
          for(int seqSize:AbstractByteSeqMonitor.FIB_SEQ){
            TestExecutorService.submitTest(()->runTest(new SeqMonitor(nestedType,checkedType),seqSize));
          }
        }
      }
      TestExecutorService.completeAllTests();
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
                    TestExecutorService.submitTest(()->testremoveIf_PredicateHelper(new SeqMonitor(nestedType,checkedType),monitoredRemoveIfPredicateGen,threshold,randSeed,functionCallType,seqSize));
                  }
                }
              }
            }
          }
        }
      }
    }
    TestExecutorService.completeAllTests();
  }
  private static void testremoveIf_PredicateHelper(SeqMonitor seqMonitor,MonitoredRemoveIfPredicateGen monitoredRemoveIfPredicateGen,double threshold,long randSeed,final FunctionCallType functionCallType,int seqSize
  ){
    for(int i=0;i<seqSize;++i){
      seqMonitor.add(i);
    }
    final var clone=(OmniCollection.OfByte)seqMonitor.seq.clone();
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
      seqMonitor.verifyRemoveIf(monitoredRemoveIfPredicate,functionCallType,numExpectedRemoved,clone,seqMonitor.nestedType==NestedType.QUEUE);
      seqMonitor.verifyPreAlloc().skip(seqMonitor.expectedSeqSize).verifyPostAlloc();
      Assertions.assertEquals(numExpectedCalls,monitoredRemoveIfPredicate.callCounter);
      return;
    }else{
      Assertions.assertThrows(monitoredRemoveIfPredicateGen.expectedException,()->seqMonitor.verifyRemoveIf(monitoredRemoveIfPredicate,functionCallType,numExpectedRemoved,clone,seqMonitor.nestedType==NestedType.QUEUE));
      //TODO verify contents of sequence in throw cases 
    }
    seqMonitor.verifyStructuralIntegrity();
  }
  interface QueryTest{
    void runTest(SeqMonitor seqMonitor,QueryTester tester,QueryCastType queryCastType,SequenceLocation seqLocation,int seqSize
    );
    private void runStackTests(){
      runTests(NestedType.STACK);
      TestExecutorService.completeAllTests();
    }
    private void runCollectionTests(){
      runTests(NestedType.STACK);
      runTests(NestedType.QUEUE);
      TestExecutorService.completeAllTests();
    }
    private void runTests(NestedType nestedType){
      for(var checkedType:CheckedType.values()){
        for(var seqLocation:SequenceLocation.values()){
          if(seqLocation!=SequenceLocation.IOBLO){
            for(int seqSize:AbstractByteSeqMonitor.FIB_SEQ){
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
                      //these input values cannot potentially return true
                      break;
                      default:
                      if(seqSize>0 && seqLocation.expectedException==null){
                        continue;
                      }
                      //these values must necessarily return false
                    }
                    TestExecutorService.submitTest(()->runTest(new SeqMonitor(nestedType,checkedType),argType,queryCastType,seqLocation,seqSize));
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
            for(int numToAdd:AbstractByteSeqMonitor.FIB_SEQ){
              TestExecutorService.submitTest(()->{
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
                    Assertions.assertEquals(resultArr[i],(Object)itr.nextByte());
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
    TestExecutorService.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testtoArray_ObjectArray(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(int tmpSeqSize=0;tmpSeqSize<=15;tmpSeqSize+=5){
          final int seqSize=tmpSeqSize;
          for(int tmpArrSize=0;tmpArrSize<=seqSize+5;tmpArrSize+=5){
            final int arrSize=tmpArrSize;
            TestExecutorService.submitTest(()->{
              var seqMonitor=new SeqMonitor(nestedType,checkedType);
              for(int i=0;i<seqSize;++i){
                seqMonitor.add(i);
              }
              Byte[] paramArr=new Byte[arrSize];
              for(int i=seqSize,bound=seqSize+arrSize;i<bound;++i){
                paramArr[i-seqSize]=TypeConversionUtil.convertToByte(i);
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
                  Assertions.assertEquals(TypeConversionUtil.convertToByte(i+seqSize),resultArr[i]);
                }
              }else{
                Assertions.assertSame(paramArr,resultArr);
              }
              var itr=seqMonitor.seq.iterator();
              for(int i=0;i<seqSize;++i){
                Assertions.assertEquals((Object)itr.nextByte(),resultArr[i]);
              }
              seqMonitor.verifyPreAlloc().verifyNaturalAscending(seqSize).verifyPostAlloc();
            });
          }
        }
      }
    }
    TestExecutorService.completeAllTests();
  }
  interface OutputTest{
    void runTest(SeqMonitor seqMonitor,ByteOutputTestArgType outputArgType);
    private void runTests(){
      for(var nestedType:NestedType.values()){
        for(var checkedType:CheckedType.values()){
          for(var outputArgType:ByteOutputTestArgType.values()){
            TestExecutorService.submitTest(()->runTest(new SeqMonitor(nestedType,checkedType),outputArgType));
          }
        }
      }
      TestExecutorService.completeAllTests();
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
      for(var outputArgType:ByteOutputTestArgType.values()){
        TestExecutorService.submitTest(()->{
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
    TestExecutorService.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testtoArray_void(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(int numToAdd:AbstractByteSeqMonitor.FIB_SEQ){
          for(var outputArgType:ByteOutputTestArgType.values()){
            TestExecutorService.submitTest(()->{
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
    TestExecutorService.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testreadAndwriteObject(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var monitoredFunctionGen:MonitoredFunctionGen.values()){
          if((checkedType.checked || monitoredFunctionGen.expectedException==null)&&(monitoredFunctionGen.appliesToRoot)){
            for(int seqSize:AbstractByteSeqMonitor.FIB_SEQ){
              TestExecutorService.submitTest(()->testreadAndwriteObjectHelper(new SeqMonitor(nestedType,checkedType),monitoredFunctionGen,seqSize
              ));
            }
          }
        }
      }
    }
    TestExecutorService.completeAllTests();
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
      OmniCollection.OfByte readCol=null;
      try(var ois=new ObjectInputStream(new FileInputStream(file));){
        readCol=(OmniCollection.OfByte)ois.readObject();
      }catch(Exception e){
        Assertions.fail(e);
        return;
      }
      var itr=readCol.iterator();
      if(seqMonitor.nestedType.forwardIteration){
        for(int i=0;i<numToAdd;++i){
          Assertions.assertEquals(TypeConversionUtil.convertTobyte(i),itr.nextByte());
        }
      }else{
        for(int i=0;i<numToAdd;++i){
          Assertions.assertEquals(TypeConversionUtil.convertTobyte(numToAdd-i-1),itr.nextByte());
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
      for(var inputArgType:ByteInputTestArgType.values()){
        TestExecutorService.submitTest(()->{
          var seqMonitor=new SeqMonitor(NestedType.STACK,checkedType);
          for(int i=0;i<100;++i){
            seqMonitor.push(i,inputArgType);
            seqMonitor.verifyStructuralIntegrity();
          }
          seqMonitor.verifyPreAlloc().verifyNaturalAscending(inputArgType,100).verifyPostAlloc();
        });
      }
    }
    TestExecutorService.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testQueueoffer_val(){
    for(var checkedType:CheckedType.values()){
      for(var inputArgType:ByteInputTestArgType.values()){
        TestExecutorService.submitTest(()->{
          var seqMonitor=new SeqMonitor(NestedType.QUEUE,checkedType);
          for(int i=0;i<100;++i){
            Assertions.assertTrue(seqMonitor.offer(i,inputArgType));
            seqMonitor.verifyStructuralIntegrity();
          }
          seqMonitor.verifyPreAlloc().verifyNaturalAscending(inputArgType,100).verifyPostAlloc();
        });
      }
    }
    TestExecutorService.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testadd_val(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var inputArgType:ByteInputTestArgType.values()){
          TestExecutorService.submitTest(()->{
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
    TestExecutorService.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testforEach_Consumer(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var monitoredFunctionGen:MonitoredFunctionGen.values()){
          if(monitoredFunctionGen.appliesToRoot&&(checkedType.checked || monitoredFunctionGen.expectedException==null)){
            for(int seqSize:AbstractByteSeqMonitor.FIB_SEQ){
              TestExecutorService.submitTest(()->testforEach_ConsumerHelper(new SeqMonitor(nestedType,checkedType),monitoredFunctionGen,seqSize,FunctionCallType.Unboxed));
              TestExecutorService.submitTest(()->testforEach_ConsumerHelper(new SeqMonitor(nestedType,checkedType),monitoredFunctionGen,seqSize,FunctionCallType.Boxed));
            }
          }
        }
      }
    }
    TestExecutorService.completeAllTests();
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
                for(int seqSize:AbstractByteSeqMonitor.FIB_SEQ){
                  TestExecutorService.submitTest(()->testItrforEachRemaining_ConsumerHelper(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredFunctionGen,seqSize,FunctionCallType.Unboxed));
                  TestExecutorService.submitTest(()->testItrforEachRemaining_ConsumerHelper(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredFunctionGen,seqSize,FunctionCallType.Boxed));
                }
              }
            }
          }
        }
      }
    }
    TestExecutorService.completeAllTests();
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
            for(int numToAdd:AbstractByteSeqMonitor.FIB_SEQ){
              if(numToAdd!=0 || removeScenario.validWithEmptySeq){
                for(var preModScenario:PreModScenario.values()){
                  if(preModScenario.appliesToRootItr && (checkedType.checked || preModScenario.expectedException==null)){
                    for(var seqLocation:SequenceLocation.values()){
                      if(seqLocation.expectedException==null &&
                        (numToAdd!=0 || seqLocation==SequenceLocation.BEGINNING) &&
                        (seqLocation!=SequenceLocation.END || removeScenario!=ItrRemoveScenario.PostInit)&&
                        (removeScenario!=ItrRemoveScenario.PostInit || seqLocation==SequenceLocation.BEGINNING)
                      ){
                        TestExecutorService.submitTest(()->{
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
    TestExecutorService.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testItrnext_void(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var itrScenario:IterationScenario.values()){
          if(checkedType.checked || itrScenario==IterationScenario.NoMod){
            for(int numToAdd:AbstractByteSeqMonitor.FIB_SEQ){
              if(numToAdd!=0 || itrScenario.validWithEmptySeq){
                if(itrScenario.preModScenario.appliesToRootItr){
                  for(var outputType:ByteOutputTestArgType.values()){
                    TestExecutorService.submitTest(()->{
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
    TestExecutorService.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testConstructor_void(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        TestExecutorService.submitTest(()->{
          var seqMonitor=new SeqMonitor(nestedType,checkedType);
          if(seqMonitor.checkedType.checked){
            Assertions.assertEquals(0,seqMonitor.nestedType==NestedType.QUEUE?FieldAndMethodAccessor.ByteSnglLnkSeq.CheckedQueue.modCount(seqMonitor.seq):FieldAndMethodAccessor.ByteSnglLnkSeq.CheckedStack.modCount(seqMonitor.seq));
          }
          Assertions.assertEquals(0,seqMonitor.seq.size);
          Assertions.assertNull(seqMonitor.seq.head);
        });
      }
    }
    TestExecutorService.completeAllTests();
  }
  private static class SeqMonitor extends AbstractByteSeqMonitor<ByteSnglLnkSeq>{
    NestedType nestedType;
    SeqMonitor(NestedType nestedType,CheckedType checkedType,ByteSnglLnkNode head,int seqSize,ByteSnglLnkNode tail){
      super(checkedType);
      this.nestedType=nestedType;
      this.expectedSeqSize=seqSize;
      switch(nestedType){
        case QUEUE:
          this.seq=checkedType.checked?new ByteSnglLnkSeq.CheckedQueue(head,seqSize,tail):new ByteSnglLnkSeq.UncheckedQueue(head,seqSize,tail);
          break;
        case STACK:
          this.seq=checkedType.checked?new ByteSnglLnkSeq.CheckedStack(head,seqSize):new ByteSnglLnkSeq.UncheckedStack(head,seqSize);
          break;
        default:
          throw new Error("unknown nested type "+nestedType);
      }
    }
    SeqMonitor(NestedType nestedType,CheckedType checkedType){
      super(checkedType);
      this.nestedType=nestedType;
      switch(nestedType){
        case QUEUE:
          this.seq=checkedType.checked?new ByteSnglLnkSeq.CheckedQueue():new ByteSnglLnkSeq.UncheckedQueue();
          break;
        case STACK:
          this.seq=checkedType.checked?new ByteSnglLnkSeq.CheckedStack():new ByteSnglLnkSeq.UncheckedStack();
          break;
        default:
          throw new Error("unknown nested type "+nestedType);
      }
    }
    void illegalAdd(PreModScenario preModScenario){
      switch(preModScenario){
        case ModSeq:
          ByteInputTestArgType.ARRAY_TYPE.callCollectionAdd(seq,0);
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
      builder.append("ByteSnglLnkSeq.").append(checkedType.checked?"Checked":"Unchecked");
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
            FieldAndMethodAccessor.ByteSnglLnkSeq.CheckedQueue.writeObject(seq,oos);
          }else{
            FieldAndMethodAccessor.ByteSnglLnkSeq.UncheckedQueue.writeObject(seq,oos);
          }
          break;
        case STACK:
          if(checkedType.checked){
            FieldAndMethodAccessor.ByteSnglLnkSeq.CheckedStack.writeObject(seq,oos);
          }else{
            FieldAndMethodAccessor.ByteSnglLnkSeq.UncheckedStack.writeObject(seq,oos);
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
            Assertions.assertEquals(expectedSeqModCount,((ByteSnglLnkSeq.CheckedQueue)seq).modCount);
            break;
          case STACK:
            Assertions.assertEquals(expectedSeqModCount,((ByteSnglLnkSeq.CheckedStack)seq).modCount);
            break;
          default:
            throw new Error("Unknown nested type "+nestedType);
        }
      }
      if(expectedSeqSize==0){
        Assertions.assertNull(seq.head);
      }else{
        ByteSnglLnkNode node;
        Assertions.assertNotNull(node=seq.head);
        int i=expectedSeqSize;
        while(--i!=0){
          Assertions.assertNotNull(node=node.next);
        }
        Assertions.assertNull(node.next);
      }
    }
    class UncheckedSnglLnkSeqItrMonitor extends AbstractByteSeqMonitor.AbstractItrMonitor{
      ByteSnglLnkNode expectedPrev;
      ByteSnglLnkNode expectedCurr;
      ByteSnglLnkNode expectedNext;
      UncheckedSnglLnkSeqItrMonitor(){
        super(ItrType.Itr,seq.iterator(),expectedSeqModCount);
        this.expectedNext=seq.head;
      }
      void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
        if(functionCallType==FunctionCallType.Boxed){
          itr.forEachRemaining((Consumer)action);
        }else
        {
          itr.forEachRemaining((ByteConsumer)action);
        }
        ByteSnglLnkNode expectedNext;
        if((expectedNext=this.expectedNext)!=null){
          ByteSnglLnkNode expectedPrev,expectedCurr=this.expectedCurr;
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
      void verifyNext(int expectedVal,ByteOutputTestArgType outputType){
        outputType.verifyItrNext(itr,expectedVal);
        final ByteSnglLnkNode expectedNext;
        this.expectedNext=(expectedNext=this.expectedNext).next;
        this.expectedPrev=this.expectedCurr;
        this.expectedCurr=expectedNext;
      }
      void verifyIteratorState(){
        Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.ByteSnglLnkSeq.AbstractItr.prev(itr));
        Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.ByteSnglLnkSeq.AbstractItr.curr(itr));
        Assertions.assertSame(expectedNext,FieldAndMethodAccessor.ByteSnglLnkSeq.AbstractItr.next(itr));
        switch(nestedType)
        {
          case STACK:
            if(checkedType.checked)
            {
              Assertions.assertSame(seq,FieldAndMethodAccessor.ByteSnglLnkSeq.CheckedStack.Itr.parent(itr));
            }
            else
            {
              Assertions.assertSame(seq,FieldAndMethodAccessor.ByteSnglLnkSeq.UncheckedStack.Itr.parent(itr));
            }
            break;
          case QUEUE:
            if(checkedType.checked)
            {
              Assertions.assertSame(seq,FieldAndMethodAccessor.ByteSnglLnkSeq.CheckedQueue.Itr.parent(itr));
            }
            else
            {
              Assertions.assertSame(seq,FieldAndMethodAccessor.ByteSnglLnkSeq.UncheckedQueue.Itr.parent(itr));
            }
            break;
          default:
            throw new Error("Unknown nested type "+nestedType);
        }
      }
      void iterateForward(){
        itr.next();
        final ByteSnglLnkNode expectedNext;
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
        Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.ByteSnglLnkSeq.AbstractItr.prev(itr));
        Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.ByteSnglLnkSeq.AbstractItr.curr(itr));
        Assertions.assertSame(expectedNext,FieldAndMethodAccessor.ByteSnglLnkSeq.AbstractItr.next(itr));
        switch(nestedType)
        {
          case STACK:
            if(checkedType.checked)
            {
              Assertions.assertSame(seq,FieldAndMethodAccessor.ByteSnglLnkSeq.CheckedStack.Itr.parent(itr));
              Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.ByteSnglLnkSeq.CheckedStack.Itr.modCount(itr));
            }
            else
            {
              Assertions.assertSame(seq,FieldAndMethodAccessor.ByteSnglLnkSeq.UncheckedStack.Itr.parent(itr));
            }
            break;
          case QUEUE:
            if(checkedType.checked)
            {
              Assertions.assertSame(seq,FieldAndMethodAccessor.ByteSnglLnkSeq.CheckedQueue.Itr.parent(itr));
              Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.ByteSnglLnkSeq.CheckedQueue.Itr.modCount(itr));
            }
            else
            {
              Assertions.assertSame(seq,FieldAndMethodAccessor.ByteSnglLnkSeq.UncheckedQueue.Itr.parent(itr));
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
      ByteSnglLnkNode curr;
      final SeqMonitor seqMonitor;
      private SnglLnkSeqSequenceVerificationItr(SeqMonitor seqMonitor,ByteSnglLnkNode curr){
        this.seqMonitor=seqMonitor;
        this.curr=curr;
      }
      @Override SequenceVerificationItr verifyPostAlloc(int expectedVal){
        Assertions.assertNull(curr);
        return this;
      }
      @Override void verifyLiteralIndexAndIterate(byte val){
        Assertions.assertEquals(val,curr.val);
        curr=curr.next;
      }
      @Override void verifyIndexAndIterate(ByteInputTestArgType inputArgType,int val){
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
      SequenceVerificationItr verifyNaturalAscending(int v,ByteInputTestArgType inputArgType,int length)
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
