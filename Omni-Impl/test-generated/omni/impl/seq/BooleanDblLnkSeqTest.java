package omni.impl.seq;
import omni.function.BooleanConsumer;
import java.util.Arrays;
import java.util.function.Consumer;
import java.io.IOException;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import omni.impl.BooleanInputTestArgType;
import omni.impl.BooleanOutputTestArgType;
import java.util.NoSuchElementException;
import omni.util.OmniArray;
import omni.impl.BooleanDblLnkNode;
import omni.api.OmniIterator;
import omni.impl.FunctionCallType;
import omni.impl.QueryCastType;
import java.io.File;
import org.junit.jupiter.api.Tag;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import omni.impl.seq.AbstractBooleanSeqMonitor.MonitoredFunctionGen;
import omni.impl.seq.AbstractBooleanSeqMonitor.MonitoredRemoveIfPredicateGen;
import omni.impl.seq.AbstractBooleanSeqMonitor.QueryTester;
import omni.impl.seq.AbstractBooleanSeqMonitor.MonitoredComparatorGen;
import java.nio.file.Files;
import omni.impl.seq.AbstractBooleanSeqMonitor.SequenceVerificationItr;
import omni.api.OmniCollection;
import omni.api.OmniListIterator;
import java.util.ArrayList;
import omni.api.OmniDeque;
import omni.api.OmniList;
import java.util.Comparator;
import omni.util.TestExecutorService;
import omni.impl.CheckedType;
import omni.function.BooleanComparator;
@SuppressWarnings({"rawtypes","unchecked"})
@Tag("DblLnkSeqTest")
public class BooleanDblLnkSeqTest{
    @org.junit.jupiter.api.AfterEach
    public void verifyAllExecuted(){
      int numTestsRemaining;
      if((numTestsRemaining=TestExecutorService.getNumRemainingTasks())!=0)
      {
        System.err.println("Warning: there were "+numTestsRemaining+" tests that were not completed");
      }
      TestExecutorService.reset();
    }
      private static final int MAX_TOSTRING_LENGTH=5;
    @Tag("MASSIVEtoString")
    @org.junit.jupiter.api.Test
    public void testMASSIVEtoString_void(){
      final int seqLength=(OmniArray.MAX_ARR_SIZE/(MAX_TOSTRING_LENGTH+2))+1;
      var head=new BooleanDblLnkNode(TypeConversionUtil.convertToboolean(1));
      var tail=head;
      int numBatches=100;
      int batchSpan=seqLength/numBatches;
      var wayPointNodes=new BooleanDblLnkNode[numBatches];
      wayPointNodes[0]=head;
      int nextWayPointIndex=batchSpan;
      int batchIndex=0;
      int batchBound=numBatches-1;
      for(int i=1;i<seqLength;++i)
      {
        tail=tail.next=new BooleanDblLnkNode(tail,TypeConversionUtil.convertToboolean(1));
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
          var seqMonitor=new SeqMonitor(checkedType,nestedType,head,seqLength,tail);
          String string=seqMonitor.seq.toString();
          Assertions.assertEquals('[',string.charAt(0));
          Assertions.assertEquals(']',string.charAt(string.length()-1));
          seqMonitor.verifyStructuralIntegrity();
          for(batchIndex=0,nextWayPointIndex=0;batchIndex<batchBound;++batchIndex){
            var verifyItr=new SeqMonitor.DblLnkSeqVerificationItr(nextWayPointIndex,wayPointNodes[batchIndex],seqMonitor);
            final int finalWayPointBound=nextWayPointIndex+batchSpan;
            final int finalWayPointIndex=nextWayPointIndex;
            TestExecutorService.submitTest(()->AbstractBooleanSeqMonitor.verifyLargeStr(string,finalWayPointIndex,finalWayPointBound,verifyItr));
            nextWayPointIndex=finalWayPointBound;
          }
          var verifyItr=new SeqMonitor.DblLnkSeqVerificationItr(nextWayPointIndex,wayPointNodes[batchIndex],seqMonitor);
          final int finalWayPointIndex=nextWayPointIndex;
          TestExecutorService.submitTest(()->AbstractBooleanSeqMonitor.verifyLargeStr(string,finalWayPointIndex,seqLength,verifyItr));
          TestExecutorService.completeAllTests("BooleanDblLnkSeqTest.testMASSIVEtoString_void checkedType="+checkedType+"; nestedType="+nestedType);
          verifyItr.verifyPostAlloc(1);
        }
      }
    }
  @org.junit.jupiter.api.Test
  public void testListItradd_val(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var preModScenario:PreModScenario.values()){
          if((preModScenario.appliesToRootItr || !nestedType.rootType) && (checkedType.checked || preModScenario.expectedException==null)){
            for(int seqSize=0;seqSize<=10;++seqSize){
              final int finalSeqSize=seqSize;
              for(var seqLocation:SequenceLocation.values()){
                if(seqLocation.expectedException==null){
                  for(var inputArgType:BooleanInputTestArgType.values()){
                    switch(nestedType){
                      case SUBLIST:
                        if(inputArgType==BooleanInputTestArgType.ARRAY_TYPE){
                          for(int parentsLength=1;parentsLength<=3;++parentsLength){
                            if(parentsLength!=1 || preModScenario!=PreModScenario.ModParent){
                              runSubListTests(false,checkedType,parentsLength,seqMonitor->testListItradd_valHelper(seqMonitor,preModScenario,finalSeqSize,seqLocation,inputArgType));
                            }
                          }
                          break;
                        }
                      case LISTDEQUE:
                        TestExecutorService.submitTest(()->testListItradd_valHelper(new SeqMonitor(nestedType,checkedType),preModScenario,finalSeqSize,seqLocation,inputArgType));
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
  private static void testListItradd_valHelper(SeqMonitor seqMonitor,PreModScenario preModScenario,int numToAdd,SequenceLocation seqLocation,BooleanInputTestArgType inputArgType){
    if(preModScenario.expectedException!=null || seqLocation.expectedException!=null){
      for(int i=0;i<numToAdd;++i){
        seqMonitor.add(i);
      }
    }
    var itrMonitor=seqMonitor.getListItrMonitor(seqLocation);
    seqMonitor.illegalAdd(preModScenario);
    SequenceVerificationItr verifyItr;
    if(preModScenario.expectedException==null){
      switch(seqLocation){
        case BEGINNING:
          for(int i=0;i<numToAdd;++i){
            itrMonitor.add(i,inputArgType);
            itrMonitor.verifyIteratorState();
            itrMonitor.iterateReverse();
            seqMonitor.verifyStructuralIntegrity();
          }
          break;
        case NEARBEGINNING:
          int currIndex=0;
          for(int i=0;i<numToAdd;++i){
            itrMonitor.add(i,inputArgType);
            ++currIndex;
            itrMonitor.verifyIteratorState();
            for(int bound=seqMonitor.expectedSeqSize/4;currIndex>bound;--currIndex)
            {
              itrMonitor.iterateReverse();
            }
            seqMonitor.verifyStructuralIntegrity();
          }
          break;
        case MIDDLE:
          currIndex=0;
          for(int i=0;i<numToAdd;++i){
            itrMonitor.add(i,inputArgType);
            ++currIndex;
            itrMonitor.verifyIteratorState();
            for(int bound=seqMonitor.expectedSeqSize/2;currIndex>bound;--currIndex)
            {
              itrMonitor.iterateReverse();
            }
            seqMonitor.verifyStructuralIntegrity();
          }
          break;
        case NEAREND:
          currIndex=0;
          for(int i=0;i<numToAdd;++i){
            itrMonitor.add(i,inputArgType);
            ++currIndex;
            itrMonitor.verifyIteratorState();
            for(int bound=seqMonitor.expectedSeqSize-(seqMonitor.expectedSeqSize/4);currIndex>bound;--currIndex)
            {
              itrMonitor.iterateReverse();
            }
            seqMonitor.verifyStructuralIntegrity();
          }
          break;
        case END:
          for(int i=0;i<numToAdd;++i){
            itrMonitor.add(i,inputArgType);
            itrMonitor.verifyIteratorState();
            seqMonitor.verifyStructuralIntegrity();
          }
          break;
        default:
          throw new Error("Unknown sequence location scenario "+seqLocation);
      }
      verifyItr=seqMonitor.verifyPreAlloc();
      switch(seqLocation){
        case BEGINNING:
          verifyItr.verifyDescending(inputArgType,numToAdd);
          break;
        case NEARBEGINNING:
          verifyItr.verifyNearBeginningInsertion(inputArgType,numToAdd);
          break;
        case MIDDLE:
          verifyItr.verifyMidPointInsertion(inputArgType,numToAdd);
          break;
        case NEAREND:
          verifyItr.verifyNearEndInsertion(inputArgType,numToAdd);
          break;
        case END:
          verifyItr.verifyAscending(inputArgType,numToAdd);
          break;
        default:
          throw new Error("Unknown sequence location scenario "+seqLocation);
      }
    }else{
      Assertions.assertThrows(preModScenario.expectedException,()->itrMonitor.add(0,inputArgType));
      itrMonitor.verifyIteratorState();
      seqMonitor.verifyStructuralIntegrity();
      verifyItr=seqMonitor.verifyPreAlloc();
      verifyItr.verifyAscending(numToAdd);
    }
    verifyItr.verifyPostAlloc(preModScenario);
  }
  @org.junit.jupiter.api.Test
  public void testclear_void(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var preModScenario:PreModScenario.values()){
          if(preModScenario!=PreModScenario.ModSeq && (preModScenario.expectedException==null || (!nestedType.rootType && checkedType.checked))){
            for(int seqSize:AbstractBooleanSeqMonitor.FIB_SEQ){
              switch(nestedType){
                case SUBLIST:
                  if(preModScenario.expectedException==null){
                    for(int parentsLength=1;parentsLength<=3;++parentsLength){
                      runSubListTests(true,checkedType,parentsLength,seqMonitor->testclear_voidHelper(seqMonitor,preModScenario,seqSize));
                    }
                    break;
                  }
                case LISTDEQUE:
                  TestExecutorService.submitTest(()->testclear_voidHelper(new SeqMonitor(nestedType,checkedType),preModScenario,seqSize));
                  break;
                default:
                  throw new Error("Unknown nested type "+nestedType);
              }
            }
          }
        }
      }
    }
    TestExecutorService.completeAllTests();
  }
  private static void testclear_voidHelper(SeqMonitor seqMonitor,PreModScenario preModScenario,int numToAdd){
    for(int i=0;i<numToAdd;++i){
      seqMonitor.add(i);
    }
    seqMonitor.illegalAdd(preModScenario);
    if(preModScenario.expectedException==null){
      seqMonitor.clear();
      Assertions.assertTrue(seqMonitor.seq.isEmpty());
      numToAdd=0;
    }else{
      Assertions.assertThrows(preModScenario.expectedException,()->seqMonitor.seq.isEmpty());
    }
    seqMonitor.verifyStructuralIntegrity();
    seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyPostAlloc(preModScenario);
  }
  @org.junit.jupiter.api.Test
  public void testListremoveAt_int(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var preModScenario:PreModScenario.values()){
          if(preModScenario!=PreModScenario.ModSeq && (preModScenario.expectedException==null || (!nestedType.rootType && checkedType.checked))){
            for(var seqLocation:SequenceLocation.values()){
              if(checkedType.checked || seqLocation.expectedException==null){
                for(int seqSize:AbstractBooleanSeqMonitor.FIB_SEQ){
                  if(seqSize!=0|| (seqLocation==SequenceLocation.IOBHI)){
                    for(var outputArgType:BooleanOutputTestArgType.values()){
                      switch(nestedType){
                        case SUBLIST:
                          if(preModScenario.expectedException==null){
                            for(int parentsLength=1;parentsLength<=3;++parentsLength){
                              runSubListTests(true,checkedType,parentsLength,seqMonitor->testListremoveAt_intHelper(seqMonitor,preModScenario,seqLocation,seqSize,outputArgType));
                            }
                            break;
                          }
                        case LISTDEQUE:
                          TestExecutorService.submitTest(()->testListremoveAt_intHelper(new SeqMonitor(nestedType,checkedType),preModScenario,seqLocation,seqSize,outputArgType));
                          break;
                        default:
                          throw new Error("Unknown nested type "+nestedType);
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
  private static void testListremoveAt_intHelper(SeqMonitor seqMonitor,PreModScenario preModScenario,SequenceLocation seqLocation,int numToAdd,BooleanOutputTestArgType outputArgType){
    for(int i=0;i<numToAdd;++i){
      seqMonitor.add(i);
    }
    int expectedSize=numToAdd;
    seqMonitor.illegalAdd(preModScenario);
    Class<? extends Throwable> expectedException=preModScenario.expectedException==null?seqLocation.expectedException:preModScenario.expectedException;
    switch(seqLocation){
      case IOBLO:
        Assertions.assertThrows(expectedException,()->seqMonitor.removeAt(0,outputArgType,-1));
        seqMonitor.verifyStructuralIntegrity();
        break;
      case IOBHI:
        Assertions.assertThrows(expectedException,()->seqMonitor.removeAt(numToAdd,outputArgType,-1));
        seqMonitor.verifyStructuralIntegrity();
        break;
      case BEGINNING:
        if(expectedException==null){
          seqMonitor.verifyBeginningRemoveAt(outputArgType);
          expectedSize=0;
        }else{
          Assertions.assertThrows(expectedException,()->seqMonitor.removeAt(0,outputArgType,0));
          seqMonitor.verifyStructuralIntegrity();
        }
        break;
      case NEARBEGINNING:
        if(expectedException==null){
          seqMonitor.verifyNearBeginningRemoveAt(outputArgType);
          expectedSize=0;
        }else{
          Assertions.assertThrows(expectedException,()->seqMonitor.removeAt(numToAdd/4,outputArgType,numToAdd/4));
          seqMonitor.verifyStructuralIntegrity();
        }
        break;
      case MIDDLE:
        if(expectedException==null){
          seqMonitor.verifyMiddleRemoveAt(outputArgType);
          expectedSize=0;
        }else{
          Assertions.assertThrows(expectedException,()->seqMonitor.removeAt(numToAdd/2,outputArgType,numToAdd/2));
          seqMonitor.verifyStructuralIntegrity();
        }
        break;
      case NEAREND:
        if(expectedException==null){
          seqMonitor.verifyNearEndRemoveAt(outputArgType);
          expectedSize=0;
        }else{
          Assertions.assertThrows(expectedException,()->seqMonitor.removeAt((numToAdd/4)*3,outputArgType,(numToAdd/4)*3));
          seqMonitor.verifyStructuralIntegrity();
        }
        break;
      case END:
        if(expectedException==null){
          seqMonitor.verifyEndRemoveAt(outputArgType);
          expectedSize=0;
        }else{
          Assertions.assertThrows(expectedException,()->seqMonitor.removeAt(numToAdd-1,outputArgType,numToAdd-1));
          seqMonitor.verifyStructuralIntegrity();
        }
        break;
      default:
        throw new Error("Unknown seqLocation "+seqLocation);
    }
    seqMonitor.verifyPreAlloc().verifyAscending(expectedSize).verifyPostAlloc(preModScenario);
  }
  @Tag("RemoveIf")
  @org.junit.jupiter.api.Test
  public void testremoveIf_Predicate(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var functionCallType:FunctionCallType.values()){
          for(var preModScenario:PreModScenario.values()){
            if(preModScenario==PreModScenario.ModSeq || (preModScenario.expectedException!=null && !checkedType.checked) || (nestedType.rootType && preModScenario!=PreModScenario.NoMod)){
              continue;
            }
            for(var monitoredRemoveIfPredicateGen:MonitoredRemoveIfPredicateGen.values()){
              if(monitoredRemoveIfPredicateGen.expectedException!=null && (!checkedType.checked || (nestedType.rootType && !monitoredRemoveIfPredicateGen.appliesToRoot))){
                continue;
              }
              for(int seqSize=0;seqSize<=10;++seqSize){
                final int finalSeqSize;
                final int inc=Math.max(1,(finalSeqSize=seqSize)/10);
                final long randSeedBound=seqSize==0 || !monitoredRemoveIfPredicateGen.isRandomized?0:100;
                for(long tmpRandSeed=0;tmpRandSeed<=randSeedBound;++tmpRandSeed){
                  final long randSeed=tmpRandSeed;
                  for(int tmpPeriod=1;;tmpPeriod+=inc){
                    final int period=tmpPeriod;
                    for(int initVal=0;initVal<=1;++initVal){
                      final int finalInitVal=initVal;
                      switch(nestedType){
                        case SUBLIST:
                          if(functionCallType==FunctionCallType.Unboxed && monitoredRemoveIfPredicateGen.expectedException==null && preModScenario.expectedException==null && !monitoredRemoveIfPredicateGen.isRandomized){
                            for(int parentsLength=1;parentsLength<=3;++parentsLength){
                              runSubListTests(true,checkedType,parentsLength,seqMonitor->testremoveIf_PredicateHelper(seqMonitor,preModScenario,monitoredRemoveIfPredicateGen,0.5,randSeed,functionCallType,finalSeqSize,finalInitVal,period));
                            }
                            break;
                          }
                        case LISTDEQUE:
                          TestExecutorService.submitTest(()->testremoveIf_PredicateHelper(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredRemoveIfPredicateGen,0.5,randSeed,functionCallType,finalSeqSize,finalInitVal,period));
                      }
                    }
                    if(period>=finalSeqSize){
                      break;
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
  private static void testremoveIf_PredicateHelper(SeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredRemoveIfPredicateGen monitoredRemoveIfPredicateGen,double threshold,long randSeed,final FunctionCallType functionCallType,int seqSize
  ,int initVal,int period
  ){
  int trueCount=0;
  for(int i=0;i<seqSize;){
    seqMonitor.add(initVal);
    if((initVal&1)!=0){
      ++trueCount;
    }
    if((++i)%period==0){
      ++initVal;
    }
  }
    final var clone=(OmniCollection.OfBoolean)seqMonitor.seq.clone();
    final int numExpectedCalls=seqMonitor.seq.contains(true)?seqMonitor.seq.contains(false)?2:1:seqMonitor.seq.contains(false)?1:0;
    final int numExpectedRemoved;
    switch(monitoredRemoveIfPredicateGen){
      case RemoveTrue:
        numExpectedRemoved=trueCount;
        break;
      case RemoveFalse:
        numExpectedRemoved=seqSize-trueCount;
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
      case ModParent:
      case ModRoot:
      case ThrowModSeq:
      case ThrowModParent:
      case ThrowModRoot:
        numExpectedRemoved=0;
        break;
      default:
        throw new Error("Unknown monitoredRemoveIfPredicateGen "+monitoredRemoveIfPredicateGen);
    }
    seqMonitor.illegalAdd(preModScenario);
    final var monitoredRemoveIfPredicate=monitoredRemoveIfPredicateGen.getMonitoredRemoveIfPredicate(seqMonitor,randSeed,numExpectedCalls,threshold);
    if(preModScenario.expectedException==null){
      if(monitoredRemoveIfPredicateGen.expectedException==null || seqSize==0){
        seqMonitor.verifyRemoveIf(monitoredRemoveIfPredicate,functionCallType,numExpectedRemoved,clone);
        seqMonitor.verifyStructuralIntegrity();
        seqMonitor.verifyPreAlloc().skip(seqMonitor.expectedSeqSize).verifyPostAlloc();
        Assertions.assertEquals(numExpectedCalls,monitoredRemoveIfPredicate.callCounter);
        return;
      }else{
        Assertions.assertThrows(monitoredRemoveIfPredicateGen.expectedException,()->seqMonitor.verifyRemoveIf(monitoredRemoveIfPredicate,functionCallType,numExpectedRemoved,clone));
      }
    }else{
      Assertions.assertThrows(preModScenario.expectedException,()->seqMonitor.verifyRemoveIf(monitoredRemoveIfPredicate,functionCallType,numExpectedRemoved,clone));
    }
    seqMonitor.verifyStructuralIntegrity();
    var verifyItr=seqMonitor.verifyPreAlloc();
    var cloneItr=clone.iterator();
    while(cloneItr.hasNext()){
      verifyItr.verifyLiteralIndexAndIterate(cloneItr.nextBoolean());
    }
    switch(monitoredRemoveIfPredicateGen){
      case ModRoot:
      case ThrowModRoot:
        //The nature of concurrent modification makes verifying the contents of the array tricky due to array reallocations
        //skip it in this scenario
      case Random:
      case RemoveAll:
      case RemoveNone:
      case RemoveTrue:
      case RemoveFalse:
      case Throw:
        verifyItr.verifyPostAlloc(preModScenario);
        break;
      case ModParent:
      case ThrowModParent:
        verifyItr.verifyParentPostAlloc();
        if(preModScenario==PreModScenario.ModRoot){
          verifyItr.verifyRootPostAlloc();
          verifyItr.verifyIllegalAdd();
        }
      case ModSeq:
      case ThrowModSeq:
        //The nature of concurrent modification makes verifying the contents of the array tricky due to array reallocations
        //skip it in this scenario
        break;
      default:
        throw new Error("Unknown monitoredRemoveIfPredicateGen "+monitoredRemoveIfPredicateGen);
    }
  }
  @org.junit.jupiter.api.Test
  public void testListadd_int_val(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var seqLocation:SequenceLocation.values()){
          if(checkedType.checked || seqLocation.expectedException==null){
            for(var preModScenario:PreModScenario.values()){
              if(preModScenario!=PreModScenario.ModSeq && (preModScenario.expectedException==null || checkedType.checked) && (!nestedType.rootType || preModScenario==PreModScenario.NoMod)){
                for(int seqSize=0;seqSize<=10;++seqSize){
                  if(seqSize!=0 || seqLocation.validForEmpty){
                    final int finalSeqSize=seqSize;
                    for(var inputArgType:BooleanInputTestArgType.values()){
                      switch(nestedType){
                        case SUBLIST:
                          if(inputArgType==BooleanInputTestArgType.ARRAY_TYPE){
                            for(int parentsLength=1;parentsLength<=3;++parentsLength){
                              if(parentsLength!=1 || preModScenario!=PreModScenario.ModParent){
                                runSubListTests(false,checkedType,parentsLength,seqMonitor->testListadd_int_valHelper(seqMonitor,inputArgType,seqLocation,preModScenario,finalSeqSize));
                              }
                            }
                            break;
                          }
                        case LISTDEQUE:
                          TestExecutorService.submitTest(()->testListadd_int_valHelper(new SeqMonitor(nestedType,checkedType),inputArgType,seqLocation,preModScenario,finalSeqSize));
                          break;
                        default:
                          throw new Error("Unknown nested type "+nestedType);
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
  private static void testListadd_int_valHelper(SeqMonitor seqMonitor,BooleanInputTestArgType inputArgType,SequenceLocation seqLocation,PreModScenario preModScenario,int numToAdd){
    if(preModScenario.expectedException!=null || seqLocation.expectedException!=null){
      for(int i=0;i<numToAdd;++i){
        seqMonitor.add(i);
      }
    }
    seqMonitor.illegalAdd(preModScenario);
    SequenceVerificationItr verifyItr;
    if(preModScenario.expectedException==null){
      switch(seqLocation){
        case IOBLO:
          Assertions.assertThrows(seqLocation.expectedException,()->seqMonitor.add(-1,0,inputArgType));
          seqMonitor.verifyStructuralIntegrity();
          verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(numToAdd);
          break;
        case IOBHI:
          Assertions.assertThrows(seqLocation.expectedException,()->seqMonitor.add(numToAdd+1,0,inputArgType));
          seqMonitor.verifyStructuralIntegrity();
          verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(numToAdd);
          break;
        case BEGINNING:
          for(int i=0;i<numToAdd;++i){
            seqMonitor.add(0,i,inputArgType);
            seqMonitor.verifyStructuralIntegrity();
          }
          verifyItr=seqMonitor.verifyPreAlloc().verifyDescending(inputArgType,numToAdd);
          break;
        case NEARBEGINNING:
          for(int i=0;i<numToAdd;++i){
            seqMonitor.add(seqMonitor.expectedSeqSize>>2,i,inputArgType);
            seqMonitor.verifyStructuralIntegrity();
          }
          verifyItr=seqMonitor.verifyPreAlloc().verifyNearBeginningInsertion(inputArgType,numToAdd);
          break;
        case MIDDLE:
          for(int i=0;i<numToAdd;++i){
            seqMonitor.add(seqMonitor.expectedSeqSize>>1,i,inputArgType);
            seqMonitor.verifyStructuralIntegrity();
          }
          verifyItr=seqMonitor.verifyPreAlloc().verifyMidPointInsertion(inputArgType,numToAdd);
          break;
        case NEAREND:
          for(int i=0;i<numToAdd;++i){
            seqMonitor.add(seqMonitor.expectedSeqSize-(seqMonitor.expectedSeqSize>>2),i,inputArgType);
            seqMonitor.verifyStructuralIntegrity();
          }
          verifyItr=seqMonitor.verifyPreAlloc().verifyNearEndInsertion(inputArgType,numToAdd);
          break;
        case END:
          for(int i=0;i<numToAdd;++i){
            seqMonitor.add(i,i,inputArgType);
            seqMonitor.verifyStructuralIntegrity();
          }
          verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(inputArgType,numToAdd);
          break;
        default:
          throw new Error("Unknown seqLocation "+seqLocation);
      }
    }else{
      final int insertionIndex;
      switch(seqLocation){
        case IOBLO:
          insertionIndex=-1;
          break;
        case IOBHI:
           insertionIndex=seqMonitor.expectedSeqSize+1;
          break;
        case BEGINNING:
          insertionIndex=0;
          break;
        case NEARBEGINNING:
          insertionIndex=seqMonitor.expectedSeqSize>>2;;
          break;
        case MIDDLE:
          insertionIndex=seqMonitor.expectedSeqSize>>1;
          break;
        case NEAREND:
          insertionIndex=seqMonitor.expectedSeqSize-(seqMonitor.expectedSeqSize>>2);
          break;
        case END:
          insertionIndex=seqMonitor.expectedSeqSize;
          break;
        default:
          throw new Error("Unknown seqLocation "+seqLocation);
      }
      Assertions.assertThrows(preModScenario.expectedException,()->seqMonitor.add(insertionIndex,0,inputArgType));
      seqMonitor.verifyStructuralIntegrity();
      verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(numToAdd);
    }
    verifyItr.verifyPostAlloc(preModScenario);
  }
  @org.junit.jupiter.api.Test
  public void testcontains_val(){
    for(var nestedType:NestedType.values()){
      runQueryTests(nestedType,(checkedType,argType,queryCastType,seqLocation,numToAdd,preModScenario
      )->{
        TestExecutorService.submitTest(()->{
          var seqMonitor=new SeqMonitor(nestedType,checkedType);
          if(numToAdd!=0){
            {
              switch(seqLocation){
                case BEGINNING:
                  argType.initContainsBeginning(seqMonitor,numToAdd,true);
                  break;
                case NEARBEGINNING:
                  argType.initContainsNearBeginning(seqMonitor,numToAdd,true);
                  break;
                case MIDDLE:
                  argType.initContainsMiddle(seqMonitor,numToAdd,true);
                  break;
                case NEAREND:
                  argType.initContainsNearEnd(seqMonitor,numToAdd,true);
                  break;
                case END:
                  argType.initContainsEnd(seqMonitor,numToAdd,true);
                  break;
                case IOBHI:
                  argType.initDoesNotContain(seqMonitor,numToAdd);
                  break;
                default:
                  throw new Error("Unknown seqLocation "+seqLocation);
              }
            }
          }
          int seqSize=seqMonitor.expectedSeqSize;
          seqMonitor.illegalAdd(preModScenario);
          if(preModScenario.expectedException==null){
            Assertions.assertEquals(seqLocation!=SequenceLocation.IOBHI,argType.invokecontains(seqMonitor,queryCastType));
          }else{
            switch(argType)
            {
              case Booleannull:
              case Bytenull:
              case Characternull:
              case Shortnull:
              case Integernull:
              case Longnull:
              case Floatnull:
              case Doublenull:
                Assertions.assertFalse(argType.invokecontains(seqMonitor,queryCastType));
                break;
              default:
                Assertions.assertThrows(preModScenario.expectedException,()->argType.invokecontains(seqMonitor,queryCastType));
            }
          }
          seqMonitor.verifyStructuralIntegrity();
          seqMonitor.verifyPreAlloc().skip(seqSize).verifyPostAlloc(preModScenario);
        });
      });
    }
  }
  @org.junit.jupiter.api.Test
  public void testremoveFirstOccurrence_val(){
      runQueryTests(NestedType.LISTDEQUE,(checkedType,argType,queryCastType,seqLocation,numToAdd,preModScenario
      )->{
        TestExecutorService.submitTest(()->{
          var seqMonitor=new SeqMonitor(NestedType.LISTDEQUE,checkedType);
          if(numToAdd!=0){
            {
              switch(seqLocation){
                case BEGINNING:
                  argType.initContainsBeginning(seqMonitor,numToAdd,true);
                  break;
                case NEARBEGINNING:
                  argType.initContainsNearBeginning(seqMonitor,numToAdd,true);
                  break;
                case MIDDLE:
                  argType.initContainsMiddle(seqMonitor,numToAdd,true);
                  break;
                case NEAREND:
                  argType.initContainsNearEnd(seqMonitor,numToAdd,true);
                  break;
                case END:
                  argType.initContainsEnd(seqMonitor,numToAdd,true);
                  break;
                case IOBHI:
                  argType.initDoesNotContain(seqMonitor,numToAdd);
                  break;
                default:
                  throw new Error("Unknown seqLocation "+seqLocation);
              }
            }
          }
          int seqSize=seqMonitor.expectedSeqSize;
          boolean expectedResult;
          Assertions.assertEquals(expectedResult=seqLocation!=SequenceLocation.IOBHI,argType.invokeremoveFirstOccurrence(seqMonitor,queryCastType));
          if(expectedResult){
            --seqSize;
          }
          seqMonitor.verifyStructuralIntegrity();
          seqMonitor.verifyPreAlloc().skip(seqSize).verifyPostAlloc();
        });
      });
  }
  @org.junit.jupiter.api.Test
  public void testremoveLastOccurrence_val(){
      runQueryTests(NestedType.LISTDEQUE,(checkedType,argType,queryCastType,seqLocation,numToAdd,preModScenario
      )->{
        TestExecutorService.submitTest(()->{
          var seqMonitor=new SeqMonitor(NestedType.LISTDEQUE,checkedType);
          if(numToAdd!=0){
            {
              switch(seqLocation){
                case BEGINNING:
                  argType.initContainsBeginning(seqMonitor,numToAdd,true);
                  break;
                case NEARBEGINNING:
                  argType.initContainsNearBeginning(seqMonitor,numToAdd,true);
                  break;
                case MIDDLE:
                  argType.initContainsMiddle(seqMonitor,numToAdd,true);
                  break;
                case NEAREND:
                  argType.initContainsNearEnd(seqMonitor,numToAdd,true);
                  break;
                case END:
                  argType.initContainsEnd(seqMonitor,numToAdd,true);
                  break;
                case IOBHI:
                  argType.initDoesNotContain(seqMonitor,numToAdd);
                  break;
                default:
                  throw new Error("Unknown seqLocation "+seqLocation);
              }
            }
          }
          int seqSize=seqMonitor.expectedSeqSize;
          boolean expectedResult;
          Assertions.assertEquals(expectedResult=seqLocation!=SequenceLocation.IOBHI,argType.invokeremoveLastOccurrence(seqMonitor,queryCastType));
          if(expectedResult){
            --seqSize;
          }
          seqMonitor.verifyStructuralIntegrity();
          seqMonitor.verifyPreAlloc().skip(seqSize).verifyPostAlloc();
        });
      });
  }
  @org.junit.jupiter.api.Test
  public void testindexOf_val(){
    for(var nestedType:NestedType.values()){
      runQueryTests(nestedType,(checkedType,argType,queryCastType,seqLocation,numToAdd,preModScenario
      )->{
        TestExecutorService.submitTest(()->{
          var seqMonitor=new SeqMonitor(nestedType,checkedType);
          int expectedIndex;
          if(numToAdd!=0){
            {
              switch(seqLocation){
                case BEGINNING:
                  expectedIndex=argType.initContainsBeginning(seqMonitor,numToAdd,true)-1;
                  break;
                case NEARBEGINNING:
                  expectedIndex=argType.initContainsNearBeginning(seqMonitor,numToAdd,true)-1;
                  break;
                case MIDDLE:
                  expectedIndex=argType.initContainsMiddle(seqMonitor,numToAdd,true)-1;
                  break;
                case NEAREND:
                  expectedIndex=argType.initContainsNearEnd(seqMonitor,numToAdd,true)-1;
                  break;
                case END:
                  expectedIndex=argType.initContainsEnd(seqMonitor,numToAdd,true)-1;
                  break;
                case IOBHI:
                  argType.initDoesNotContain(seqMonitor,numToAdd);
                  expectedIndex=-1;
                  break;
                default:
                  throw new Error("Unknown seqLocation "+seqLocation);
              }
            }
          }else{
            expectedIndex=-1;
          }
          int seqSize=seqMonitor.expectedSeqSize;
          seqMonitor.illegalAdd(preModScenario);
          if(preModScenario.expectedException==null){
            Assertions.assertEquals(expectedIndex,argType.invokeindexOf(seqMonitor,queryCastType));
          }else{
            switch(argType)
            {
              case Booleannull:
              case Bytenull:
              case Characternull:
              case Shortnull:
              case Integernull:
              case Longnull:
              case Floatnull:
              case Doublenull:
                Assertions.assertEquals(-1,argType.invokeindexOf(seqMonitor,queryCastType));
                break;
              default:
                Assertions.assertThrows(preModScenario.expectedException,()->argType.invokeindexOf(seqMonitor,queryCastType));
            }
          }
          seqMonitor.verifyStructuralIntegrity();
          seqMonitor.verifyPreAlloc().skip(seqSize).verifyPostAlloc(preModScenario);
        });
      });
    }
  }
  @org.junit.jupiter.api.Test
  public void testlastIndexOf_val(){
    for(var nestedType:NestedType.values()){
      runQueryTests(nestedType,(checkedType,argType,queryCastType,seqLocation,numToAdd,preModScenario
      )->{
        TestExecutorService.submitTest(()->{
          var seqMonitor=new SeqMonitor(nestedType,checkedType);
          int expectedIndex;
          if(numToAdd!=0){
            {
              switch(seqLocation){
                case BEGINNING:
                  expectedIndex=argType.initContainsBeginning(seqMonitor,numToAdd,true)-1;
                  break;
                case NEARBEGINNING:
                  expectedIndex=argType.initContainsNearBeginning(seqMonitor,numToAdd,true)-1;
                  break;
                case MIDDLE:
                  expectedIndex=argType.initContainsMiddle(seqMonitor,numToAdd,true)-1;
                  break;
                case NEAREND:
                  expectedIndex=argType.initContainsNearEnd(seqMonitor,numToAdd,true)-1;
                  break;
                case END:
                  expectedIndex=argType.initContainsEnd(seqMonitor,numToAdd,true)-1;
                  break;
                case IOBHI:
                  argType.initDoesNotContain(seqMonitor,numToAdd);
                  expectedIndex=-1;
                  break;
                default:
                  throw new Error("Unknown seqLocation "+seqLocation);
              }
            }
          }else{
            expectedIndex=-1;
          }
          int seqSize=seqMonitor.expectedSeqSize;
          seqMonitor.illegalAdd(preModScenario);
          if(preModScenario.expectedException==null){
            Assertions.assertEquals(expectedIndex,argType.invokelastIndexOf(seqMonitor,queryCastType));
          }else{
            switch(argType)
            {
              case Booleannull:
              case Bytenull:
              case Characternull:
              case Shortnull:
              case Integernull:
              case Longnull:
              case Floatnull:
              case Doublenull:
                Assertions.assertEquals(-1,argType.invokelastIndexOf(seqMonitor,queryCastType));
                break;
              default:
                Assertions.assertThrows(preModScenario.expectedException,()->argType.invokelastIndexOf(seqMonitor,queryCastType));
            }
          }
          seqMonitor.verifyStructuralIntegrity();
          seqMonitor.verifyPreAlloc().skip(seqSize).verifyPostAlloc(preModScenario);
        });
      });
    }
  }
  @org.junit.jupiter.api.Test
  public void testsearch_val(){
    runQueryTests(NestedType.LISTDEQUE,(checkedType,argType,queryCastType,seqLocation,numToAdd,preModScenario
    )->{
      TestExecutorService.submitTest(()->{
        var seqMonitor=new SeqMonitor(NestedType.LISTDEQUE,checkedType);
        int expectedIndex;
        if(numToAdd!=0){
          {
            switch(seqLocation){
              case BEGINNING:
                expectedIndex=argType.initContainsBeginning(seqMonitor,numToAdd,true);
                break;
              case NEARBEGINNING:
                expectedIndex=argType.initContainsNearBeginning(seqMonitor,numToAdd,true);
                break;
              case MIDDLE:
                expectedIndex=argType.initContainsMiddle(seqMonitor,numToAdd,true);
                break;
              case NEAREND:
                expectedIndex=argType.initContainsNearEnd(seqMonitor,numToAdd,true);
                break;
              case END:
                expectedIndex=argType.initContainsEnd(seqMonitor,numToAdd,true);
                break;
              case IOBHI:
                argType.initDoesNotContain(seqMonitor,numToAdd);
                expectedIndex=-1;
                break;
              default:
                throw new Error("Unknown seqLocation "+seqLocation);
            }
          }
        }else{
          expectedIndex=-1;
        }
        int seqSize=seqMonitor.expectedSeqSize;
        Assertions.assertEquals(expectedIndex,argType.invokesearch(seqMonitor,queryCastType));
        seqMonitor.verifyStructuralIntegrity();
        seqMonitor.verifyPreAlloc().skip(seqSize).verifyPostAlloc(preModScenario);
      });
    });
  }
  @org.junit.jupiter.api.Test
  public void testremoveVal_val(){
    for(var nestedType:NestedType.values()){
      runQueryTests(nestedType,(checkedType,argType,queryCastType,seqLocation,seqSize,preModScenario
      )->{
        switch(nestedType){
          case SUBLIST:
            if(preModScenario.expectedException==null && (seqLocation==SequenceLocation.BEGINNING||seqLocation==SequenceLocation.END)
            ){
              for(int parentsLength=1;parentsLength<=3;++parentsLength){
                runSubListTests(true,checkedType,parentsLength,seqMonitor->testremoveVal_valHelper(seqMonitor,argType,queryCastType,seqLocation,seqSize,preModScenario
                ));
              }
              break;
            }
          case LISTDEQUE:
            TestExecutorService.submitTest(()->testremoveVal_valHelper(new SeqMonitor(nestedType,checkedType),argType,queryCastType,seqLocation,seqSize,preModScenario
            ));
            break;
          default:
            throw new Error("Unknown nested type "+nestedType);
        }
      });
    }
  }
  private static void testremoveVal_valHelper(SeqMonitor seqMonitor,QueryTester argType,QueryCastType queryCastType,SequenceLocation seqLocation,int numToAdd,PreModScenario preModScenario 
  ){
    if(numToAdd!=0){
      {
        switch(seqLocation){
          case BEGINNING:
            argType.initContainsBeginning(seqMonitor,numToAdd,true);
            break;
          case NEARBEGINNING:
            argType.initContainsNearBeginning(seqMonitor,numToAdd,true);
            break;
          case MIDDLE:
            argType.initContainsMiddle(seqMonitor,numToAdd,true);
            break;
          case NEAREND:
            argType.initContainsNearEnd(seqMonitor,numToAdd,true);
            break;
          case END:
            argType.initContainsEnd(seqMonitor,numToAdd,true);
            break;
          case IOBHI:
            argType.initDoesNotContain(seqMonitor,numToAdd);
            break;
          default:
            throw new Error("Unknown seqLocation "+seqLocation);
        }
      }
    }
    int seqSize=seqMonitor.expectedSeqSize;
    seqMonitor.illegalAdd(preModScenario);
    if(preModScenario.expectedException==null){
      boolean expectedResult;
      Assertions.assertEquals(expectedResult=seqLocation!=SequenceLocation.IOBHI,argType.invokeremoveVal(seqMonitor,queryCastType));
      if(expectedResult){
        --seqSize;
      }
    }else{
      switch(argType)
      {
        case Booleannull:
        case Bytenull:
        case Characternull:
        case Shortnull:
        case Integernull:
        case Longnull:
        case Floatnull:
        case Doublenull:
          Assertions.assertFalse(argType.invokeremoveVal(seqMonitor,queryCastType));
          break;
        default:
          Assertions.assertThrows(preModScenario.expectedException,()->argType.invokeremoveVal(seqMonitor,queryCastType));
      }
    }
    seqMonitor.verifyStructuralIntegrity();
    seqMonitor.verifyPreAlloc().skip(seqSize).verifyPostAlloc(preModScenario);
  }
  @org.junit.jupiter.api.Test
  public void testadd_val(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var preModScenario:PreModScenario.values()){
          if(preModScenario.expectedException==null || (checkedType.checked && preModScenario!=PreModScenario.ModSeq && !nestedType.rootType)){
            for(int seqSize=0;seqSize<=10;++seqSize){
              final int finalSeqSize=seqSize;
              for(var inputArgType:BooleanInputTestArgType.values()){
                switch(nestedType){
                  case SUBLIST:
                    if(inputArgType==BooleanInputTestArgType.ARRAY_TYPE){
                      for(int parentsLength=1;parentsLength<=3;++parentsLength){
                        if(parentsLength!=1 || preModScenario!=PreModScenario.ModParent){
                          runSubListTests(false,checkedType,parentsLength,seqMonitor->testadd_valHelper(seqMonitor,inputArgType,preModScenario,finalSeqSize));
                        }
                      }
                      break;
                    }
                  case LISTDEQUE:
                    TestExecutorService.submitTest(()->testadd_valHelper(new SeqMonitor(nestedType,checkedType),inputArgType,preModScenario,finalSeqSize));
                    break;
                  default:
                    throw new Error("Unknown nested type "+nestedType);
                } 
              }
            }
          }
        }
      }
    }
    TestExecutorService.completeAllTests();
  }
  private static void testadd_valHelper(SeqMonitor seqMonitor,BooleanInputTestArgType inputArgType,PreModScenario preModScenario,int numToAdd){
    for(int i=0;i<numToAdd;++i){
      seqMonitor.add(i);
    }
    seqMonitor.illegalAdd(preModScenario);
    SequenceVerificationItr verifyItr;
    if(preModScenario.expectedException==null){
      for(int i=0;i<10;++i){
        Assertions.assertTrue(seqMonitor.add(i,inputArgType));
        seqMonitor.verifyStructuralIntegrity();
      }
      verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyAscending(inputArgType,10);
    }else{
      Assertions.assertThrows(preModScenario.expectedException,()->seqMonitor.add(0,inputArgType));
      seqMonitor.verifyStructuralIntegrity();
      verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(numToAdd);
    }
    verifyItr.verifyPostAlloc(preModScenario);
  }
  @org.junit.jupiter.api.Test
  public void testConstructor_void(){
    TestExecutorService.submitTest(()->{
      var seq=new BooleanDblLnkSeq.CheckedList();
      Assertions.assertNull(seq.head);
      Assertions.assertNull(seq.tail);
      Assertions.assertEquals(0,seq.size);
      Assertions.assertEquals(0,seq.modCount);
    });
    TestExecutorService.submitTest(()->{
      var seq=new BooleanDblLnkSeq.UncheckedList();
      Assertions.assertNull(seq.head);
      Assertions.assertNull(seq.tail);
      Assertions.assertEquals(0,seq.size);
    });
    TestExecutorService.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testConstructor_Node_int_Node(){
    final var head=new BooleanDblLnkNode(TypeConversionUtil.convertToboolean(1));
    final var tail=new BooleanDblLnkNode(TypeConversionUtil.convertToboolean(2));
    head.next=tail;
    tail.prev=head;
    final int seqSize=2;
    TestExecutorService.submitTest(()->{
      var seq=new BooleanDblLnkSeq.CheckedList(head,seqSize,tail);
      Assertions.assertSame(head,seq.head);
      Assertions.assertSame(tail,seq.tail);
      Assertions.assertEquals(seqSize,seq.size);
      Assertions.assertNull(seq.head.prev);
      Assertions.assertNull(seq.tail.next);
      Assertions.assertSame(seq.head.next,seq.tail);
      Assertions.assertSame(seq.tail.prev,seq.head);
      Assertions.assertEquals(0,seq.modCount);
    });
    TestExecutorService.submitTest(()->{
      var seq=new BooleanDblLnkSeq.UncheckedList(head,seqSize,tail);
      Assertions.assertSame(head,seq.head);
      Assertions.assertSame(tail,seq.tail);
      Assertions.assertEquals(seqSize,seq.size);
      Assertions.assertNull(seq.head.prev);
      Assertions.assertNull(seq.tail.next);
      Assertions.assertSame(seq.head.next,seq.tail);
      Assertions.assertSame(seq.tail.prev,seq.head);
    });
    TestExecutorService.completeAllTests();
  }
  interface BasicCollectionTest{
    void runTest(SeqMonitor seqMonitor,PreModScenario preModScenario,int numToAdd);
    private void runTests(){
      for(var nestedType:NestedType.values()){
        for(var checkedType:CheckedType.values()){
          for(var preModScenario:PreModScenario.values()){
            if(preModScenario.expectedException==null || (checkedType.checked && preModScenario!=PreModScenario.ModSeq && !nestedType.rootType)){
              for(int seqSize:AbstractBooleanSeqMonitor.FIB_SEQ){
                TestExecutorService.submitTest(()->runTest(new SeqMonitor(nestedType,checkedType),preModScenario,seqSize));
              }
            }
          }
        }
      }
      TestExecutorService.completeAllTests();
    }
  }
  @org.junit.jupiter.api.Test
  public void testsize_void(){
    BasicCollectionTest test=(seqMonitor,preModScenario,numToAdd)->{
      for(int i=0;i<numToAdd;++i){
        Assertions.assertEquals(i,seqMonitor.seq.size());
        seqMonitor.verifyStructuralIntegrity();
        seqMonitor.add(i);
      }
      seqMonitor.illegalAdd(preModScenario);
      if(preModScenario.expectedException==null){
        var itrMonitor=seqMonitor.getItrMonitor();
        while(numToAdd>0){
          Assertions.assertEquals(numToAdd--,seqMonitor.seq.size());
          seqMonitor.verifyStructuralIntegrity();
          itrMonitor.iterateForward();
          itrMonitor.remove();
        }
        Assertions.assertEquals(numToAdd,seqMonitor.seq.size());
      }else{
        Assertions.assertThrows(preModScenario.expectedException,()->seqMonitor.seq.size());
      }
      seqMonitor.verifyStructuralIntegrity();
      seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyPostAlloc(preModScenario);
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testisEmpty_void(){
    BasicCollectionTest test=(seqMonitor,preModScenario,numToAdd)->{
      for(int i=0;i<numToAdd;++i){
        Assertions.assertEquals(i==0,seqMonitor.seq.isEmpty());
        seqMonitor.verifyStructuralIntegrity();
        seqMonitor.add(i);
      }
      seqMonitor.illegalAdd(preModScenario);
      if(preModScenario.expectedException==null){
        var itrMonitor=seqMonitor.getItrMonitor();
        while(numToAdd>0){
          Assertions.assertEquals((numToAdd--)==0,seqMonitor.seq.isEmpty());
          seqMonitor.verifyStructuralIntegrity();
          itrMonitor.iterateForward();
          itrMonitor.remove();
        }
        Assertions.assertTrue(seqMonitor.seq.isEmpty());
      }else{
        Assertions.assertThrows(preModScenario.expectedException,()->seqMonitor.seq.isEmpty());
      }
      seqMonitor.verifyStructuralIntegrity();
      seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyPostAlloc(preModScenario);
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testclone_void(){
    BasicCollectionTest test=(seqMonitor,preModScenario,numToAdd)->{
      for(int i=0;i<numToAdd;++i){
        seqMonitor.add(i);
      }
      seqMonitor.illegalAdd(preModScenario);
      if(preModScenario.expectedException==null){
        var clone=(OmniCollection.OfBoolean)seqMonitor.seq.clone();
        Assertions.assertNotSame(clone,seqMonitor.seq);
        if(seqMonitor.checkedType.checked){
          Assertions.assertTrue(clone instanceof BooleanDblLnkSeq.CheckedList);
          Assertions.assertEquals(0,((BooleanDblLnkSeq.CheckedList)clone).modCount);
        }else{
          Assertions.assertTrue(clone instanceof BooleanDblLnkSeq.UncheckedList);
        }
        var dblLnkSeqClone=(BooleanDblLnkSeq)clone;
        var cloneHead=dblLnkSeqClone.head;
        var cloneTail=dblLnkSeqClone.tail;
        Assertions.assertEquals(numToAdd,dblLnkSeqClone.size);
        if(numToAdd==0){
          Assertions.assertNull(cloneHead);
          Assertions.assertNull(cloneTail);
        }else{
          Assertions.assertNotNull(cloneHead);
          Assertions.assertNotNull(cloneTail);
          var originalHead=seqMonitor.seq.head;
          var originalTail=seqMonitor.seq.tail;
          Assertions.assertNotSame(cloneHead,originalHead);
          Assertions.assertNotSame(cloneTail,originalTail);
          Assertions.assertNull(cloneHead.prev);
          for(int i=0;;){
            Assertions.assertEquals(originalHead.val,cloneHead.val);
            if(++i==numToAdd){
              Assertions.assertSame(cloneHead,cloneTail);
              Assertions.assertNull(cloneHead.next);
              break;
            }
            Assertions.assertSame(cloneHead,(cloneHead=cloneHead.next).prev);
            originalHead=originalHead.next;
          }
        }
      }else{
        Assertions.assertThrows(preModScenario.expectedException,()->seqMonitor.seq.clone());
      }
      seqMonitor.verifyStructuralIntegrity();
      seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyPostAlloc(preModScenario);
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testItrforEachRemaining_Consumer(){
    for(var checkedType:CheckedType.values()){
      for(var nestedType:NestedType.values()){
        for(var preModScenario:PreModScenario.values()){
          if(checkedType.checked || preModScenario.expectedException==null){
            for(var monitoredFunctionGen:MonitoredFunctionGen.values()){
              if((monitoredFunctionGen.expectedException==null || checkedType.checked) && (!nestedType.rootType || (preModScenario.appliesToRootItr&&monitoredFunctionGen.appliesToRootItr))){
                for(var itrType:ItrType.values()){
                  if((itrType!=ItrType.DescendingItr||nestedType.rootType)){
                    for(int seqSize:AbstractBooleanSeqMonitor.FIB_SEQ){
                      TestExecutorService.submitTest(()->testItrforEachRemaining_ConsumerHelper(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredFunctionGen,itrType,seqSize,FunctionCallType.Unboxed));
                      TestExecutorService.submitTest(()->testItrforEachRemaining_ConsumerHelper(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredFunctionGen,itrType,seqSize,FunctionCallType.Boxed));
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
  private static void testItrforEachRemaining_ConsumerHelper(SeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredFunctionGen monitoredFunctionGen,ItrType itrType,int numToAdd,FunctionCallType functionCallType){
    for(int i=0;i<numToAdd;++i){
      seqMonitor.add(i);
    }
    var itrMonitor=seqMonitor.getItrMonitor(itrType);
    seqMonitor.illegalAdd(preModScenario);
    var monitoredConsumer=monitoredFunctionGen.getMonitoredConsumer(itrMonitor);
    int numExpectedIteratedValues;
    if(preModScenario.expectedException==null || (numToAdd==0 && (itrType==ItrType.DescendingItr || preModScenario!=PreModScenario.ModSeq))){
      if(monitoredFunctionGen.expectedException==null || (numToAdd==0)){
        itrMonitor.forEachRemaining(monitoredConsumer,functionCallType);
        itrMonitor.verifyIteratorState();
        seqMonitor.verifyStructuralIntegrity();
        seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyPostAlloc(preModScenario);
        numExpectedIteratedValues=numToAdd;
      }else{
        Assertions.assertThrows(monitoredFunctionGen.expectedException,()->itrMonitor.forEachRemaining(monitoredConsumer,functionCallType));
        seqMonitor.verifyStructuralIntegrity();
        itrMonitor.verifyIteratorState();
        switch(monitoredFunctionGen){
          case ThrowModItr:
            numExpectedIteratedValues=1;
            if(itrType!=ItrType.DescendingItr){
              seqMonitor.verifyPreAlloc().verifyAscending(1,numToAdd-1).verifyPostAlloc();
            }else{
              seqMonitor.verifyPreAlloc().verifyAscending(numToAdd-1).verifyPostAlloc();
            }
            break;
          case ModItr:
            numExpectedIteratedValues=numToAdd;
            seqMonitor.verifyPreAlloc().verifyPostAlloc();
            break;
          case Throw:
            numExpectedIteratedValues=1;
            seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyPostAlloc();
            break;
          case ModSeq:
            numExpectedIteratedValues=numToAdd;
            var verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(numToAdd);
            for(int i=0;i<numToAdd;++i){
              verifyItr.verifyIllegalAdd();
            }
            verifyItr.verifyPostAlloc();
            break;
          case ModParent:
            numExpectedIteratedValues=numToAdd;
            verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyParentPostAlloc();
            for(int i=0;i<numToAdd;++i){
              verifyItr.verifyIllegalAdd();
            }
            verifyItr.verifyRootPostAlloc();
            break;
          case ModRoot:
            numExpectedIteratedValues=numToAdd;
            verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyPostAlloc();
            for(int i=0;i<numToAdd;++i){
              verifyItr.verifyIllegalAdd();
            }
            break;
          case ThrowModSeq:
            numExpectedIteratedValues=1;
            seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyPostAlloc(PreModScenario.ModSeq);
            break;
          case ThrowModParent:
            numExpectedIteratedValues=1;
            seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyPostAlloc(PreModScenario.ModParent);
            break;
          case ThrowModRoot:
            numExpectedIteratedValues=1;
            seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyPostAlloc(PreModScenario.ModRoot);
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
          if(itrType!=ItrType.DescendingItr && numToAdd==0 && preModScenario==PreModScenario.ModSeq){
            numExpectedIteratedValues=0;
          }else{
            numExpectedIteratedValues=1;
          }
          seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyPostAlloc(preModScenario);
          break;
        case ModItr:
          if(itrType!=ItrType.DescendingItr && numToAdd==0 && preModScenario==PreModScenario.ModSeq){
            numExpectedIteratedValues=0;
          }else{
            numExpectedIteratedValues=1;
          }
          //verification in tis situation is tricky. Just skip it
          break;
        case NoThrow:
          numExpectedIteratedValues=numToAdd;
          if(preModScenario==PreModScenario.ModSeq && numToAdd!=0 && itrType!=ItrType.DescendingItr)
          {
            ++numExpectedIteratedValues;
          }
          seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyPostAlloc(preModScenario);
          break;
        case Throw:
          if(itrType!=ItrType.DescendingItr && numToAdd==0 && preModScenario==PreModScenario.ModSeq){
            numExpectedIteratedValues=0;
          }else{
            numExpectedIteratedValues=1;
          }
          seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyPostAlloc(preModScenario);
          break;
        case ModSeq:
          var verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(numToAdd);
          if(preModScenario==PreModScenario.ModSeq){
            numExpectedIteratedValues=numToAdd;
            if(itrType!=ItrType.DescendingItr && numToAdd!=0){
              ++numExpectedIteratedValues;
            }
            for(int i=0;i<numExpectedIteratedValues;++i){
              verifyItr.verifyIllegalAdd();
            }
          }else{
            numExpectedIteratedValues=1;
          }
          verifyItr.verifyPostAlloc(preModScenario);
          break;
        case ModParent:
          switch(preModScenario){
            case ModRoot:
              numExpectedIteratedValues=1;
              seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyPostAlloc(preModScenario);
              break;
            case ModParent:
              numExpectedIteratedValues=numToAdd;
              verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyParentPostAlloc();
              for(int i=0;i<numExpectedIteratedValues+1;++i){
                verifyItr.verifyIllegalAdd();
              }
              verifyItr.verifyRootPostAlloc();
              break;
            case ModSeq:
              numExpectedIteratedValues=numToAdd+1;
              verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyIllegalAdd().verifyParentPostAlloc();
              if(numToAdd!=0){
                for(int i=0;i<numExpectedIteratedValues;++i){
                  verifyItr.verifyIllegalAdd();
                }
              }else{
                numExpectedIteratedValues=0;
              }
              verifyItr.verifyRootPostAlloc();
              break;
            default:
              throw new Error("Unknown preModScenario "+preModScenario);
          }
          break;
        case ModRoot:
          numExpectedIteratedValues=numToAdd;
          if(preModScenario==PreModScenario.ModSeq){
            ++numExpectedIteratedValues;
          }
          verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyPostAlloc(preModScenario);
          if(numToAdd!=0)
          {
            if(preModScenario!=PreModScenario.ModSeq){
               for(int i=0;i<numExpectedIteratedValues;++i){
                verifyItr.verifyIllegalAdd();
              }
            }
          }else{
            if(preModScenario==PreModScenario.ModSeq){
              numExpectedIteratedValues=0;
            }
          }
          break;
        case ThrowModSeq:
          numExpectedIteratedValues=1;
          verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(numToAdd);
          if(preModScenario==PreModScenario.ModSeq){
            if(numToAdd==0)
            {
              numExpectedIteratedValues=0;
            }
            else if(itrType!=ItrType.DescendingItr)
            {
              verifyItr.verifyIllegalAdd();
            }
          }
          verifyItr.verifyPostAlloc(preModScenario);
          break;
        case ThrowModParent:
          verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(numToAdd);
          numExpectedIteratedValues=1;
          switch(preModScenario){
            case ModSeq:
              verifyItr.verifyIllegalAdd();
              if(numToAdd!=0)
              {
                verifyItr.verifyPostAlloc(PreModScenario.ModParent);
              }
              else
              {
                numExpectedIteratedValues=0;
                verifyItr.verifyPostAlloc();
              }
              break;
            case ModParent:
              verifyItr.verifyParentPostAlloc().verifyIllegalAdd().verifyIllegalAdd().verifyRootPostAlloc();
              break;
            case ModRoot:
              verifyItr.verifyPostAlloc(preModScenario);
              break;
            default:
              throw new Error("Unknown preModScenario "+preModScenario);
          }
          break;
        case ThrowModRoot:
          verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyPostAlloc(preModScenario);
          if(preModScenario!=PreModScenario.ModSeq || numToAdd!=0)
          {
            verifyItr.verifyIllegalAdd();
            numExpectedIteratedValues=1;
          }else{
            numExpectedIteratedValues=0;
          }
          break;
        default:
          throw new Error("Unknown monitored function gen "+monitoredFunctionGen);
      }
    }
    Assertions.assertEquals(numExpectedIteratedValues,monitoredConsumer.encounteredValues.size());
    switch(monitoredFunctionGen){
      case ModItr:
      case ThrowModItr:{
        if(preModScenario==PreModScenario.NoMod && numToAdd!=0){
          break;
        }
        //those scenarios are kinda tricky.
        //just skip this step of verification
      }
      default:{
        if(itrType!=ItrType.DescendingItr){
          var currNode=seqMonitor.seq.head;
          for(var encounteredValue:monitoredConsumer.encounteredValues){
            Assertions.assertEquals(encounteredValue,(Object)currNode.val);
            currNode=currNode.next;
          }
        }else{
          var currNode=seqMonitor.seq.tail;
          if(preModScenario==PreModScenario.ModSeq && numToAdd!=0){
            currNode=currNode.prev;
          }
          if(monitoredFunctionGen==MonitoredFunctionGen.ModSeq){
            for(int i=0;i<numToAdd;++i){
              currNode=currNode.prev;
            }
          }
          else if(monitoredFunctionGen==MonitoredFunctionGen.ThrowModSeq && numToAdd!=0){
            currNode=currNode.prev;
          }
          for(var encounteredValue:monitoredConsumer.encounteredValues){
            Assertions.assertEquals(encounteredValue,(Object)currNode.val);
            currNode=currNode.prev;
          }
        }
      }
    }
  }
  @org.junit.jupiter.api.Test
  public void testItrremove_void(){
    for(var checkedType:CheckedType.values()){
      for(var removeScenario:ItrRemoveScenario.values()){
        if(checkedType.checked || removeScenario.expectedException==null){
          for(int numToAdd:AbstractBooleanSeqMonitor.FIB_SEQ){
            if(numToAdd!=0 || removeScenario.validWithEmptySeq){
              for(var preModScenario:PreModScenario.values()){
                if(checkedType.checked || preModScenario.expectedException==null){
                  for(var itrType:ItrType.values()){
                    if(itrType==ItrType.ListItr || (removeScenario!=ItrRemoveScenario.PostAdd && removeScenario!=ItrRemoveScenario.PostPrevious)){
                      for(var nestedType:NestedType.values()){
                        if((!nestedType.rootType || (preModScenario!=PreModScenario.ModParent && preModScenario!=PreModScenario.ModRoot)) && (nestedType.rootType || itrType!=ItrType.DescendingItr)){
                          for(var seqLocation:SequenceLocation.values()){
                            if(seqLocation.expectedException==null && (itrType==ItrType.ListItr || (removeScenario!=ItrRemoveScenario.PostInit || seqLocation==SequenceLocation.BEGINNING))){
                              TestExecutorService.submitTest(()->{
                                var seqMonitor=new SeqMonitor(nestedType,checkedType);
                                for(int i=0;i<numToAdd;++i){
                                  seqMonitor.add(i);
                                }
                                var itrMonitor=seqMonitor.getItrMonitor(seqLocation,itrType);
                                int numBefore;
                                switch(seqLocation){
                                  case BEGINNING:
                                    numBefore=0;
                                    break;
                                  case NEARBEGINNING:
                                    numBefore=numToAdd>>2;
                                    break;
                                  case MIDDLE:
                                    numBefore=numToAdd>>1;
                                    break;
                                  case NEAREND:
                                    numBefore=(numToAdd>>2)*3;
                                    break;
                                  case END:
                                    numBefore=numToAdd;
                                    break;
                                  default:
                                    throw new Error("Unknown seqLocation "+seqLocation);
                                }
                                boolean hasLastRet;
                                switch(itrType){
                                  case Itr:
                                    hasLastRet=numBefore>0;
                                    break;
                                  case ListItr:
                                    hasLastRet=false;
                                    break;
                                  case DescendingItr:
                                    numBefore=numToAdd-numBefore;
                                    hasLastRet=numBefore<numToAdd;
                                    break;
                                  default:
                                    throw new Error("Unknown itrType "+itrType);
                                }
                                int numAfter=numToAdd-numBefore;
                                switch(removeScenario){
                                  case PostNext:
                                    if(!hasLastRet){
                                      if(itrMonitor.hasNext()){
                                        itrMonitor.iterateForward();
                                        if(itrType==ItrType.DescendingItr){
                                          --numBefore;
                                          --numAfter;
                                        }
                                        hasLastRet=true;
                                        break;
                                      }
                                    }else{
                                      if(itrType==ItrType.Itr){
                                        --numBefore;
                                      }else{
                                        --numAfter;
                                      }
                                      break;
                                    }
                                  case PostPrevious:
                                    if(itrType!=ItrType.ListItr){
                                      throw new Error("Invalid test{removeScenario="+removeScenario+", numToAdd="+numToAdd+", itrType="+itrType+"} must be ListItr");
                                    }
                                    if(!itrMonitor.hasPrevious()){
                                      if(!itrMonitor.hasNext()){
                                        throw new Error("Invalid test{removeScenario="+removeScenario+", numToAdd="+numToAdd+", itrType="+itrType+"} hasPrevious returned false");
                                      }
                                      itrMonitor.iterateForward();
                                    }
                                    itrMonitor.iterateReverse();
                                    --numBefore;
                                    --numAfter;
                                    hasLastRet=true;
                                    break;
                                  case PostAdd:
                                    if(itrType!=ItrType.ListItr){
                                      throw new Error("Invalid test{removeScenario="+removeScenario+", numToAdd="+numToAdd+", itrType="+itrType+"} must be ListItr");
                                    }
                                    itrMonitor.add(0);
                                    hasLastRet=false;
                                    break;
                                  case PostRemove:
                                    if(!hasLastRet){
                                      if(itrMonitor.hasNext()){
                                        itrMonitor.iterateForward();
                                        if(itrType==ItrType.DescendingItr){
                                          --numBefore;
                                        }else{
                                          --numAfter;
                                        }
                                      }else{
                                        if(itrType!=ItrType.ListItr){
                                          throw new Error("Invalid test{removeScenario="+removeScenario+", numToAdd="+numToAdd+", itrType="+itrType+"} must be ListItr");
                                        }
                                        if(!itrMonitor.hasPrevious()){
                                          throw new Error("Invalid test{removeScenario="+removeScenario+", numToAdd="+numToAdd+", itrType="+itrType+"} hasPrevious returned false");
                                        }
                                        itrMonitor.iterateReverse();
                                        itrMonitor.iterateForward();
                                        --numBefore;
                                      }
                                    }else if(itrType==ItrType.Itr){
                                      --numBefore;
                                    }else if(itrType==ItrType.DescendingItr){
                                      --numAfter;
                                    }
                                    itrMonitor.remove();
                                    hasLastRet=false;
                                    break;
                                  case PostInit:
                                    if(itrType!=ItrType.ListItr && seqLocation!=SequenceLocation.BEGINNING){
                                      throw new Error("Invalid test{removeScenario="+removeScenario+", numToAdd="+numToAdd+", itrType="+itrType+"} must be ListItr");
                                    }
                                    break;
                                  default:
                                    throw new Error("Unknown removeScenario "+removeScenario);
                                }
                                seqMonitor.illegalAdd(preModScenario);
                                SequenceVerificationItr verifyItr;
                                if(removeScenario.expectedException==null){
                                  if(preModScenario.expectedException==null){
                                    itrMonitor.remove();
                                    itrMonitor.verifyIteratorState();
                                    seqMonitor.verifyStructuralIntegrity();
                                    if(itrType==ItrType.ListItr){
                                      switch(removeScenario){
                                        case PostNext:
                                          switch(seqLocation){
                                            case BEGINNING:
                                              while(itrMonitor.hasNext()){
                                                itrMonitor.iterateForward();
                                                itrMonitor.remove();
                                                itrMonitor.verifyIteratorState();
                                                seqMonitor.verifyStructuralIntegrity();
                                              }
                                              break;
                                            case NEARBEGINNING:
                                              for(;;){
                                                if(numBefore*3<numAfter){
                                                  if(itrMonitor.hasNext()){
                                                    itrMonitor.iterateForward();
                                                    --numAfter;
                                                  }else if(itrMonitor.hasPrevious()){
                                                    itrMonitor.iterateReverse();
                                                    itrMonitor.iterateForward();
                                                    --numBefore;
                                                  }else{
                                                    break;
                                                  }
                                                }else{
                                                  if(itrMonitor.hasPrevious()){
                                                    itrMonitor.iterateReverse();
                                                    itrMonitor.iterateForward();
                                                    --numBefore;
                                                  }else if(itrMonitor.hasNext()){
                                                    itrMonitor.iterateForward();
                                                    --numAfter;
                                                  }
                                                }
                                                itrMonitor.remove();
                                                itrMonitor.verifyIteratorState();
                                                seqMonitor.verifyStructuralIntegrity();
                                              }
                                              break;
                                            case MIDDLE:
                                              for(;;){
                                                if(numBefore<numAfter){
                                                  if(itrMonitor.hasNext()){
                                                    itrMonitor.iterateForward();
                                                    --numAfter;
                                                  }else if(itrMonitor.hasPrevious()){
                                                    itrMonitor.iterateReverse();
                                                    itrMonitor.iterateForward();
                                                    --numBefore;
                                                  }else{
                                                    break;
                                                  }
                                                }else{
                                                  if(itrMonitor.hasPrevious()){
                                                    itrMonitor.iterateReverse();
                                                    itrMonitor.iterateForward();
                                                    --numBefore;
                                                  }else if(itrMonitor.hasNext()){
                                                    itrMonitor.iterateForward();
                                                    --numAfter;
                                                  }
                                                }
                                                itrMonitor.remove();
                                                itrMonitor.verifyIteratorState();
                                                seqMonitor.verifyStructuralIntegrity();
                                              }
                                              break;
                                            case NEAREND:
                                              for(;;){
                                                if(numBefore<numAfter*3){
                                                  if(itrMonitor.hasNext()){
                                                    itrMonitor.iterateForward();
                                                    --numAfter;
                                                  }else if(itrMonitor.hasPrevious()){
                                                    itrMonitor.iterateReverse();
                                                    itrMonitor.iterateForward();
                                                    --numBefore;
                                                  }else{
                                                    break;
                                                  }
                                                }else{
                                                  if(itrMonitor.hasPrevious()){
                                                    itrMonitor.iterateReverse();
                                                    itrMonitor.iterateForward();
                                                    --numBefore;
                                                  }else if(itrMonitor.hasNext()){
                                                    itrMonitor.iterateForward();
                                                    --numAfter;
                                                  }
                                                }
                                                itrMonitor.remove();
                                                itrMonitor.verifyIteratorState();
                                                seqMonitor.verifyStructuralIntegrity();
                                              }
                                              break;
                                            case END:
                                              while(itrMonitor.hasPrevious()){
                                                itrMonitor.iterateReverse();
                                                itrMonitor.iterateForward();
                                                itrMonitor.remove();
                                                itrMonitor.verifyIteratorState();
                                                seqMonitor.verifyStructuralIntegrity();
                                              }
                                              break;
                                            default:
                                              throw new Error("Unknown seqLocation "+seqLocation);
                                          }
                                          break;
                                        case PostPrevious:
                                          switch(seqLocation){
                                            case BEGINNING:
                                              while(itrMonitor.hasNext()){
                                                itrMonitor.iterateForward();
                                                itrMonitor.iterateReverse();
                                                itrMonitor.remove();
                                                itrMonitor.verifyIteratorState();
                                                seqMonitor.verifyStructuralIntegrity();
                                              }
                                              break;
                                            case NEARBEGINNING:
                                              for(;;){
                                                if(numBefore*3<numAfter){
                                                  if(itrMonitor.hasNext()){
                                                    itrMonitor.iterateForward();
                                                    itrMonitor.iterateReverse();
                                                    --numAfter;
                                                  }else if(itrMonitor.hasPrevious()){
                                                    itrMonitor.iterateReverse();
                                                    --numBefore;
                                                  }else{
                                                    break;
                                                  }
                                                }else{
                                                  if(itrMonitor.hasPrevious()){
                                                    itrMonitor.iterateReverse();
                                                    --numBefore;
                                                  }else if(itrMonitor.hasNext()){
                                                    itrMonitor.iterateForward();
                                                    itrMonitor.iterateReverse();
                                                    --numAfter;
                                                  }else{
                                                    break;
                                                  }
                                                }
                                                itrMonitor.remove();
                                                itrMonitor.verifyIteratorState();
                                                seqMonitor.verifyStructuralIntegrity();
                                              }
                                              break;
                                            case MIDDLE:
                                              for(;;){
                                                if(numBefore<numAfter){
                                                  if(itrMonitor.hasNext()){
                                                    itrMonitor.iterateForward();
                                                    itrMonitor.iterateReverse();
                                                    --numAfter;
                                                  }else if(itrMonitor.hasPrevious()){
                                                    itrMonitor.iterateReverse();
                                                    --numBefore;
                                                  }else{
                                                    break;
                                                  }
                                                }else{
                                                  if(itrMonitor.hasPrevious()){
                                                    itrMonitor.iterateReverse();
                                                    --numBefore;
                                                  }else if(itrMonitor.hasNext()){
                                                    itrMonitor.iterateForward();
                                                    itrMonitor.iterateReverse();
                                                    --numAfter;
                                                  }else{
                                                    break;
                                                  }
                                                }
                                                itrMonitor.remove();
                                                itrMonitor.verifyIteratorState();
                                                seqMonitor.verifyStructuralIntegrity();
                                              }
                                              break;
                                            case NEAREND:
                                              for(;;){
                                                if(numBefore<numAfter*3){
                                                  if(itrMonitor.hasNext()){
                                                    itrMonitor.iterateForward();
                                                    itrMonitor.iterateReverse();
                                                    --numAfter;
                                                  }else if(itrMonitor.hasPrevious()){
                                                    itrMonitor.iterateReverse();
                                                    --numBefore;
                                                  }else{
                                                    break;
                                                  }
                                                }else{
                                                  if(itrMonitor.hasPrevious()){
                                                    itrMonitor.iterateReverse();
                                                    --numBefore;
                                                  }else if(itrMonitor.hasNext()){
                                                    itrMonitor.iterateForward();
                                                    itrMonitor.iterateReverse();
                                                    --numAfter;
                                                  }else{
                                                    break;
                                                  }
                                                }
                                                itrMonitor.remove();
                                                itrMonitor.verifyIteratorState();
                                                seqMonitor.verifyStructuralIntegrity();
                                              }
                                              break;
                                            case END:
                                              while(itrMonitor.hasPrevious()){
                                                itrMonitor.iterateReverse();
                                                itrMonitor.remove();
                                                itrMonitor.verifyIteratorState();
                                                seqMonitor.verifyStructuralIntegrity();
                                              }
                                              break;
                                            default:
                                              throw new Error("Unknown seqLocation "+seqLocation);
                                          }
                                          break;
                                        default:
                                          throw new Error("Unknown removeScenario "+removeScenario);
                                      }
                                      Assertions.assertTrue(seqMonitor.isEmpty());
                                    }else{
                                      while(itrMonitor.hasNext()){
                                        itrMonitor.iterateForward();
                                        itrMonitor.remove();
                                        itrMonitor.verifyIteratorState();
                                        seqMonitor.verifyStructuralIntegrity();
                                      }
                                      if(itrType==ItrType.Itr){
                                        Assertions.assertTrue(numBefore!=0 || seqMonitor.isEmpty());
                                      }else{
                                        Assertions.assertTrue(numAfter!=0 || seqMonitor.isEmpty());
                                      }
                                    }
                                    verifyItr=seqMonitor.verifyPreAlloc();
                                    switch(itrType){
                                      case ListItr:
                                        break;
                                      case Itr:
                                        verifyItr.verifyAscending(numBefore);
                                        break;
                                      case DescendingItr:
                                        verifyItr.verifyAscending(numBefore+1,numAfter);
                                        break;
                                      default:
                                        throw new Error("Unknown itrType "+itrType);
                                    }
                                  }else{
                                    Assertions.assertThrows(preModScenario.expectedException,()->itrMonitor.remove());
                                    itrMonitor.verifyIteratorState();
                                    seqMonitor.verifyStructuralIntegrity();
                                    verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(numToAdd);
                                  }
                                }else{
                                  Assertions.assertThrows(removeScenario.expectedException,()->itrMonitor.remove());
                                  itrMonitor.verifyIteratorState();
                                  seqMonitor.verifyStructuralIntegrity();
                                  verifyItr=seqMonitor.verifyPreAlloc();
                                  switch(removeScenario){
                                    case PostInit:
                                      verifyItr.verifyAscending(numToAdd);
                                      break;
                                    case PostAdd:
                                      verifyItr.verifyAscending(numBefore);
                                      verifyItr.verifyIllegalAdd();
                                      verifyItr.verifyAscending(numBefore,numAfter);
                                      break;
                                    case PostRemove:
                                      verifyItr.verifyAscending(numBefore);
                                      verifyItr.verifyAscending(numBefore+1,numAfter);
                                      break;
                                    default:
                                      throw new Error("Unknown removeScenario "+removeScenario);
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
        }
      }
    }
    TestExecutorService.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testListget_int(){
    for(var nestedType:NestedType.values()){
      if(nestedType.forwardIteration){
        for(var checkedType:CheckedType.values()){
          for(var preModScenario:PreModScenario.values()){
            if(preModScenario.expectedException==null || (checkedType.checked && preModScenario!=PreModScenario.ModSeq && !nestedType.rootType)){
              for(int numToAdd:AbstractBooleanSeqMonitor.FIB_SEQ){
                for(var seqLocation:SequenceLocation.values()){
                  if((seqLocation==SequenceLocation.BEGINNING && numToAdd!=0) || (checkedType.checked && seqLocation.expectedException!=null)){
                    for(var outputArgType:BooleanOutputTestArgType.values()){
                      TestExecutorService.submitTest(()->{
                        var seqMonitor=new SeqMonitor(nestedType,checkedType);
                        for(int i=0;i<numToAdd;++i){
                          seqMonitor.add(i);
                        }
                        seqMonitor.illegalAdd(preModScenario);
                        if(preModScenario.expectedException==null){
                          switch(seqLocation){
                            case IOBLO:
                              Assertions.assertThrows(seqLocation.expectedException,()->outputArgType.verifyListGet(seqMonitor.seq,-1,0));
                              break;
                            case IOBHI:
                              Assertions.assertThrows(seqLocation.expectedException,()->outputArgType.verifyListGet(seqMonitor.seq,numToAdd,0));
                              break;
                            case BEGINNING:
                              for(int i=0;i<numToAdd;++i){
                                outputArgType.verifyListGet(seqMonitor.seq,i,i);
                                seqMonitor.verifyStructuralIntegrity();
                              }
                              break;
                            default:
                              throw new Error("Unknown seqLocation "+seqLocation);
                          }
                        }else{
                          final int getIndex;
                          switch(seqLocation){
                            case IOBLO:
                              getIndex=-1;
                              break;
                            case IOBHI:
                              getIndex=numToAdd;
                              break;
                            case BEGINNING:
                              getIndex=0;
                              break;
                            default:
                              throw new Error("Unknown seqLocation "+seqLocation);
                          }
                          Assertions.assertThrows(preModScenario.expectedException,()->outputArgType.verifyListGet(seqMonitor.seq,getIndex,0));
                        }
                        seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyPostAlloc(preModScenario);
                        seqMonitor.verifyStructuralIntegrity();
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
    TestExecutorService.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testListset_int_val(){
    for(var nestedType:NestedType.values()){
      if(nestedType.forwardIteration){
        for(var checkedType:CheckedType.values()){
          for(var preModScenario:PreModScenario.values()){
            if(preModScenario.expectedException==null || (checkedType.checked && preModScenario!=PreModScenario.ModSeq && !nestedType.rootType)){
              for(int seqSize:AbstractBooleanSeqMonitor.FIB_SEQ){
                for(var seqLocation:SequenceLocation.values()){
                  if((seqLocation==SequenceLocation.BEGINNING && seqSize!=0) || (checkedType.checked && seqLocation.expectedException!=null)){
                    TestExecutorService.submitTest(()->testListset_int_valHelper(new SeqMonitor(nestedType,checkedType),preModScenario,seqSize,seqLocation,FunctionCallType.Unboxed));
                    TestExecutorService.submitTest(()->testListset_int_valHelper(new SeqMonitor(nestedType,checkedType),preModScenario,seqSize,seqLocation,FunctionCallType.Boxed));
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
  private static void testListset_int_valHelper(SeqMonitor seqMonitor,PreModScenario preModScenario,int numToAdd,SequenceLocation seqLocation,FunctionCallType functionCallType){
    for(int i=0;i<numToAdd;++i){
      seqMonitor.add(i);
    }
    seqMonitor.illegalAdd(preModScenario);
    if(preModScenario.expectedException==null){
      switch(seqLocation){
        case IOBLO:
          Assertions.assertThrows(seqLocation.expectedException,()->seqMonitor.verifySet(-1,-1,0,functionCallType));
          seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyPostAlloc();
          break;
        case IOBHI:
          Assertions.assertThrows(seqLocation.expectedException,()->seqMonitor.verifySet(numToAdd,numToAdd+1,numToAdd,functionCallType));
          seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyPostAlloc();
          break;
        case BEGINNING:
          for(int i=0;i<numToAdd;++i){
            seqMonitor.verifySet(i,i+numToAdd,i,functionCallType);
            seqMonitor.verifyStructuralIntegrity();
          }
          seqMonitor.verifyPreAlloc().verifyAscending(numToAdd,numToAdd).verifyPostAlloc();
          break;
        default:
          throw new Error("Unknown seqLocation "+seqLocation);
      }
    }else{
      final int setIndex;
      switch(seqLocation){
        case IOBLO:
          setIndex=-1;
          break;
        case IOBHI:
          setIndex=numToAdd;
          break;
        case BEGINNING:
          setIndex=0;
          break;
        default:
          throw new Error("Unknown seqLocation "+seqLocation);
      }
      Assertions.assertThrows(preModScenario.expectedException,()->seqMonitor.verifySet(setIndex,numToAdd+1,setIndex,functionCallType));
      seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyPostAlloc(preModScenario);
    }
    seqMonitor.verifyStructuralIntegrity();
  }
  static void runQueryTests(NestedType nestedType,QueryTest queryTest){
    for(var checkedType:CheckedType.values()){
      for(var preModScenario:PreModScenario.values()){
        if(preModScenario!=PreModScenario.ModSeq && (preModScenario.expectedException==null || (checkedType.checked && !nestedType.rootType))){
          for(var seqLocation:SequenceLocation.values()){
            if(seqLocation==SequenceLocation.IOBLO){
              continue;
            }
            for(int seqSize:AbstractBooleanSeqMonitor.FIB_SEQ){
              if(seqLocation==SequenceLocation.IOBHI || seqSize!=0){
                for(var queryCastType:QueryCastType.values()){
                  for(var argType:QueryTester.values()){
                    switch(argType){
                      case Booleannull:
                      case Bytenull:
                      case Characternull:
                      case Shortnull:
                      case Integernull:
                      case Longnull:
                      case Floatnull:
                      case Doublenull:
                        if(queryCastType!=QueryCastType.ToBoxed || (seqSize!=0 && seqLocation.expectedException==null)){
                          continue;
                        }
                        break;
                      case Objectnull:
                        if(queryCastType!=QueryCastType.ToObject || (seqSize!=0 && seqLocation.expectedException==null)){
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
                      //these input values cannot potentially return true
                      break;
                      default:
                      if(seqSize!=0 && seqLocation.expectedException==null){
                        continue;
                      }
                      //these values must necessarily return false
                    }
                    queryTest.runTest(checkedType,argType,queryCastType,seqLocation,seqSize,preModScenario);
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
  interface QueryTest
  {
    void runTest(CheckedType checkedType,QueryTester argType,QueryCastType queryCastType,SequenceLocation seqLocation,int seqSize,PreModScenario preModScenario
    );
  }
  private static void runSubListTests(boolean quickTest,CheckedType checkedType,int parentLength,Consumer<? super SeqMonitor> testMethod){
    if(quickTest){
      for(int preAllocBits=0,preAllocBound=1<<parentLength;preAllocBits<preAllocBound;++preAllocBits){
        int[] preAllocs=new int[parentLength];
        for(int index=0,marker=0b1;index<parentLength;marker<<=1,++index){
          preAllocs[index]=(marker&preAllocBits)!=0?5:0;
        }
        for(int postAllocBits=0;postAllocBits<preAllocBound;++postAllocBits){
          int[] postAllocs=new int[parentLength];
          for(int index=0,marker=0b1;index<parentLength;marker<<=1,++index){
            postAllocs[index]=(marker&postAllocBits)!=0?5:0;
          }
          TestExecutorService.submitTest(()->testMethod.accept(new SeqMonitor(checkedType,preAllocs,postAllocs)));
        }
      }
    }else{
      for(int preAllocBits=0,preAllocBound=1<<(parentLength<<1);preAllocBits<preAllocBound;++preAllocBits){
        int[] preAllocs=new int[parentLength];
        for(int index=0,marker=0b11;index<parentLength;marker<<=2,++index){
          int preAlloc;
          switch(marker&preAllocBits){
            case 0b00:
              preAlloc=0;
              break;
            case 0b01:
              preAlloc=2;
              break;
            case 0b10:
              preAlloc=4;
              break;
            default:
              preAlloc=6;
          }
          preAllocs[index]=preAlloc;
        }
        for(int postAllocBits=0;postAllocBits<preAllocBound;++postAllocBits){
          int[] postAllocs=new int[parentLength];
          for(int index=0,marker=0b11;index<parentLength;marker<<=2,++index){
            int postAlloc;
            switch(marker&postAllocBits){
              case 0b00:
                postAlloc=0;
                break;
              case 0b01:
                postAlloc=2;
                break;
              case 0b10:
                postAlloc=4;
                break;
              default:
                postAlloc=6;
            }
            postAllocs[index]=postAlloc;
          }
          TestExecutorService.submitTest(()->testMethod.accept(new SeqMonitor(checkedType,preAllocs,postAllocs)));
        }
      }
    }
  }
  interface ListDequeOutputTest{
    void runTest(SeqMonitor seqMonitor,BooleanOutputTestArgType outputArgType);
    private void runTests(){
      for(var checkedType:CheckedType.values()){
        for(var outputType:BooleanOutputTestArgType.values()){
          TestExecutorService.submitTest(()->runTest(new SeqMonitor(NestedType.LISTDEQUE,checkedType),outputType));
        }
      }
      TestExecutorService.completeAllTests();
    }
  }
  @org.junit.jupiter.api.Test
  public void testgetLast_void(){
    ListDequeOutputTest test=(seqMonitor,outputArgType)->{
      if(seqMonitor.checkedType.checked){
        Assertions.assertThrows(NoSuchElementException.class,()->outputArgType.verifyDequeGetLast(seqMonitor.seq,0));
        seqMonitor.verifyStructuralIntegrity();
      }
      for(int i=0;i<100;++i){
        seqMonitor.addLast(i);
        outputArgType.verifyDequeGetLast(seqMonitor.seq,i);
        seqMonitor.verifyStructuralIntegrity();
      }
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testgetFirst_void(){
    ListDequeOutputTest test=(seqMonitor,outputArgType)->{
      if(seqMonitor.checkedType.checked){
        Assertions.assertThrows(NoSuchElementException.class,()->outputArgType.verifyDequeGetFirst(seqMonitor.seq,0));
        seqMonitor.verifyStructuralIntegrity();
      }
      for(int i=0;i<100;++i){
        seqMonitor.addFirst(i);
        outputArgType.verifyDequeGetFirst(seqMonitor.seq,i);
        seqMonitor.verifyStructuralIntegrity();
      }
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testQueueelement_void(){
    ListDequeOutputTest test=(seqMonitor,outputArgType)->{
      if(seqMonitor.checkedType.checked){
        Assertions.assertThrows(NoSuchElementException.class,()->outputArgType.verifyQueueElement(seqMonitor.seq,0));
        seqMonitor.verifyStructuralIntegrity();
      }
      for(int i=0;i<100;++i){
        seqMonitor.addFirst(i);
        outputArgType.verifyQueueElement(seqMonitor.seq,i);
        seqMonitor.verifyStructuralIntegrity();
      }
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testpollFirst_void(){
    ListDequeOutputTest test=(seqMonitor,outputArgType)->{
      for(int i=0;i<100;++i){
        seqMonitor.addLast(i);
      }
      for(int i=0;i<100;++i){
        seqMonitor.pollFirst(i,outputArgType);
        seqMonitor.verifyStructuralIntegrity();
      }
      seqMonitor.pollFirst(0,outputArgType);
      seqMonitor.verifyStructuralIntegrity();
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testpollLast_void(){
    ListDequeOutputTest test=(seqMonitor,outputArgType)->{
      for(int i=0;i<100;++i){
        seqMonitor.addLast(i);
      }
      for(int i=100;--i>=0;){
        seqMonitor.pollLast(i,outputArgType);
        seqMonitor.verifyStructuralIntegrity();
      }
      seqMonitor.pollLast(0,outputArgType);
      seqMonitor.verifyStructuralIntegrity();
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testpoll_void(){
    ListDequeOutputTest test=(seqMonitor,outputArgType)->{
      for(int i=0;i<100;++i){
        seqMonitor.addLast(i);
      }
      for(int i=0;i<100;++i){
        seqMonitor.poll(i,outputArgType);
        seqMonitor.verifyStructuralIntegrity();
      }
      seqMonitor.poll(0,outputArgType);
      seqMonitor.verifyStructuralIntegrity();
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testpeekFirst_void(){
    ListDequeOutputTest test=(seqMonitor,outputArgType)->{
      for(int i=0;i<100;){
        outputArgType.verifyDequePeekFirst(seqMonitor.seq,i,i);
        seqMonitor.verifyStructuralIntegrity();
        seqMonitor.addFirst(++i);
      }
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testpeekLast_void(){
    ListDequeOutputTest test=(seqMonitor,outputArgType)->{
      for(int i=0;i<100;){
        outputArgType.verifyDequePeekLast(seqMonitor.seq,i,i);
        seqMonitor.verifyStructuralIntegrity();
        seqMonitor.addLast(++i);
      }
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testpeek_void(){
    ListDequeOutputTest test=(seqMonitor,outputArgType)->{
      for(int i=0;i<100;){
        outputArgType.verifyPeek(seqMonitor.seq,i,i);
        seqMonitor.verifyStructuralIntegrity();
        seqMonitor.addFirst(++i);
      }
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testQueueremove_void(){
    ListDequeOutputTest test=(seqMonitor,outputArgType)->{
      for(int i=0;i<100;++i){
        seqMonitor.addLast(i);
      }
      for(int i=0;i<100;++i){
        seqMonitor.queueRemove(i,outputArgType);
        seqMonitor.verifyStructuralIntegrity();
      }
      if(seqMonitor.checkedType.checked){
        Assertions.assertThrows(NoSuchElementException.class,()->seqMonitor.queueRemove(0,outputArgType));
        seqMonitor.verifyStructuralIntegrity();
      }
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testpop_void(){
    ListDequeOutputTest test=(seqMonitor,outputArgType)->{
      for(int i=0;i<100;++i){
        seqMonitor.addLast(i);
      }
      for(int i=0;i<100;++i){
        seqMonitor.pop(i,outputArgType);
        seqMonitor.verifyStructuralIntegrity();
      }
      if(seqMonitor.checkedType.checked){
        Assertions.assertThrows(NoSuchElementException.class,()->seqMonitor.pop(0,outputArgType));
        seqMonitor.verifyStructuralIntegrity();
      }
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testremoveFirst_void(){
    ListDequeOutputTest test=(seqMonitor,outputArgType)->{
      for(int i=0;i<100;++i){
        seqMonitor.addLast(i);
      }
      for(int i=0;i<100;++i){
        seqMonitor.removeFirst(i,outputArgType);
        seqMonitor.verifyStructuralIntegrity();
      }
      if(seqMonitor.checkedType.checked){
        Assertions.assertThrows(NoSuchElementException.class,()->seqMonitor.removeFirst(0,outputArgType));
        seqMonitor.verifyStructuralIntegrity();
      }
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testremoveLast_void(){
    ListDequeOutputTest test=(seqMonitor,outputArgType)->{
      for(int i=0;i<100;++i){
        seqMonitor.addFirst(i);
      }
      for(int i=0;i<100;++i){
        seqMonitor.removeLast(i,outputArgType);
        seqMonitor.verifyStructuralIntegrity();
      }
      if(seqMonitor.checkedType.checked){
        Assertions.assertThrows(NoSuchElementException.class,()->seqMonitor.removeLast(0,outputArgType));
        seqMonitor.verifyStructuralIntegrity();
      }
    };
    test.runTests();
  }
  interface ListDequeInputTest{
    void runTest(SeqMonitor seqMonitor,BooleanInputTestArgType inputArgType);
    private void runTests(){
      for(var checkedType:CheckedType.values()){
        for(var inputArgType:BooleanInputTestArgType.values()){
          TestExecutorService.submitTest(()->runTest(new SeqMonitor(NestedType.LISTDEQUE,checkedType),inputArgType));
        }
      }
      TestExecutorService.completeAllTests();
    }
  }
  @org.junit.jupiter.api.Test
  public void testofferFirst_val(){
    ListDequeInputTest test=(seqMonitor,inputArgType)->{
      for(int i=0;i<100;++i){
        Assertions.assertTrue(seqMonitor.offerFirst(i,inputArgType));
        seqMonitor.verifyStructuralIntegrity();
      }
      seqMonitor.verifyPreAlloc().verifyDescending(inputArgType,100).verifyPostAlloc();
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testofferLast_val(){
    ListDequeInputTest test=(seqMonitor,inputArgType)->{
      for(int i=0;i<100;++i){
        Assertions.assertTrue(seqMonitor.offerLast(i,inputArgType));
        seqMonitor.verifyStructuralIntegrity();
      }
      seqMonitor.verifyPreAlloc().verifyAscending(inputArgType,100).verifyPostAlloc();
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testQueueoffer_val(){
    ListDequeInputTest test=(seqMonitor,inputArgType)->{
      for(int i=0;i<100;++i){
        Assertions.assertTrue(seqMonitor.offer(i,inputArgType));
        seqMonitor.verifyStructuralIntegrity();
      }
      seqMonitor.verifyPreAlloc().verifyAscending(inputArgType,100).verifyPostAlloc();
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testaddLast_val(){
    ListDequeInputTest test=(seqMonitor,inputArgType)->{
      for(int i=0;i<100;++i){
        seqMonitor.addLast(i,inputArgType);
        seqMonitor.verifyStructuralIntegrity();
      }
      seqMonitor.verifyPreAlloc().verifyAscending(inputArgType,100).verifyPostAlloc();
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testaddFirst_val(){
    ListDequeInputTest test=(seqMonitor,inputArgType)->{
      for(int i=0;i<100;++i){
        seqMonitor.addFirst(i,inputArgType);
        seqMonitor.verifyStructuralIntegrity();
      }
      seqMonitor.verifyPreAlloc().verifyDescending(inputArgType,100).verifyPostAlloc();
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testStackpush_val(){
    ListDequeInputTest test=(seqMonitor,inputArgType)->{
      for(int i=0;i<100;++i){
        seqMonitor.push(i,inputArgType);
        seqMonitor.verifyStructuralIntegrity();
      }
      seqMonitor.verifyPreAlloc().verifyDescending(inputArgType,100).verifyPostAlloc();
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testreadAndWriteObject(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var preModScenario:PreModScenario.values()){
          if(preModScenario.expectedException==null || (checkedType.checked && preModScenario!=PreModScenario.ModSeq && !nestedType.rootType)){
            for(var monitoredFunctionGen:MonitoredFunctionGen.values()){
              if((checkedType.checked || monitoredFunctionGen.expectedException==null)&&(!nestedType.rootType || monitoredFunctionGen.appliesToRoot) &&(nestedType.rootType || monitoredFunctionGen.appliesToSubList)){
                for(int numToAdd:AbstractBooleanSeqMonitor.FIB_SEQ){
                  for(int tmpInitVal=0;tmpInitVal<=1;++tmpInitVal){
                    final int initVal=tmpInitVal;
                    TestExecutorService.submitTest(()->{
                      var seqMonitor=new SeqMonitor(nestedType,checkedType);
                      for(int i=0;i<numToAdd;++i){
                        seqMonitor.add(i+initVal);
                      }
                      seqMonitor.illegalAdd(preModScenario);
                      final File file;
                      try{
                        file=Files.createTempFile(null,null).toFile();
                      }catch(Exception e){
                        Assertions.fail(e);
                        return;
                      }
                      if(preModScenario.expectedException==null){
                        if(monitoredFunctionGen.expectedException==null){
                          try(var oos=new ObjectOutputStream(new FileOutputStream(file));){
                            oos.writeObject(seqMonitor.seq);
                          }catch(Exception e){
                            Assertions.fail(e);
                          }
                          seqMonitor.verifyPreAlloc().verifyAscending(initVal,numToAdd).verifyPostAlloc(preModScenario);
                          OmniCollection.OfBoolean readCol=null;
                          try(var ois=new ObjectInputStream(new FileInputStream(file));){
                            readCol=(OmniCollection.OfBoolean)ois.readObject();
                          }catch(Exception e){
                            Assertions.fail(e);
                            return;
                          }
                          var itr=readCol.iterator();
                          if(seqMonitor.nestedType.forwardIteration){
                            for(int i=0,v=initVal;i<numToAdd;++i,++v){
                              Assertions.assertEquals(TypeConversionUtil.convertToboolean(v),itr.nextBoolean());
                            }
                          }else{
                            for(int i=0,v=initVal;i<numToAdd;++i,++v){
                              Assertions.assertEquals(TypeConversionUtil.convertToboolean(numToAdd+initVal-v-1),itr.nextBoolean());
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
                      }else{
                        Assertions.assertThrows(preModScenario.expectedException,()->{
                          try(var moos=monitoredFunctionGen.getMonitoredObjectOutputStream(file,seqMonitor);){
                            seqMonitor.writeObject(moos);
                          }
                        });
                      }
                      seqMonitor.verifyStructuralIntegrity();
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
  public void testListput_int_val(){
    for(var nestedType:NestedType.values()){
      if(nestedType.forwardIteration){
        for(var checkedType:CheckedType.values()){
          for(var seqLocation:SequenceLocation.values()){
            if((checkedType.checked || seqLocation.expectedException==null) && seqLocation.validForEmpty){
              for(var preModScenario:PreModScenario.values()){
                if(preModScenario!=PreModScenario.ModSeq && (preModScenario.expectedException==null || checkedType.checked) && (!nestedType.rootType || preModScenario==PreModScenario.NoMod)){
                  for(int numToAdd:AbstractBooleanSeqMonitor.FIB_SEQ){
                    if(numToAdd!=0 || seqLocation.expectedException!=null){
                      for(var inputArgType:BooleanInputTestArgType.values()){
                        TestExecutorService.submitTest(()->{
                          var seqMonitor=new SeqMonitor(nestedType,checkedType);
                          for(int i=0;i<numToAdd;++i){
                            seqMonitor.add(i);
                          }
                          seqMonitor.illegalAdd(preModScenario);
                          SequenceVerificationItr verifyItr;
                          if(preModScenario.expectedException==null){
                            switch(seqLocation){
                              case IOBLO:
                                Assertions.assertThrows(seqLocation.expectedException,()->seqMonitor.put(-1,0,inputArgType));
                                seqMonitor.verifyStructuralIntegrity();
                                verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(numToAdd);
                                break;
                              case IOBHI:
                                Assertions.assertThrows(seqLocation.expectedException,()->seqMonitor.put(numToAdd,0,inputArgType));
                                seqMonitor.verifyStructuralIntegrity();
                                verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(numToAdd);
                                break;
                              case BEGINNING:
                                for(int i=0;i<numToAdd;++i){
                                  seqMonitor.put(i,numToAdd-i-1,inputArgType);
                                  seqMonitor.verifyStructuralIntegrity();
                                }
                                seqMonitor.verifyPreAlloc().verifyDescending(inputArgType,numToAdd).verifyPostAlloc();
                                for(int i=0;i<numToAdd;++i){
                                  seqMonitor.put(i,i,inputArgType);
                                  seqMonitor.verifyStructuralIntegrity();
                                }
                                verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(inputArgType,numToAdd);
                                break;
                              default:
                                throw new Error("Unknown seqLocation "+seqLocation);
                            }
                          }else{
                            final int insertionIndex;
                            switch(seqLocation){
                              case IOBLO:
                                insertionIndex=-1;
                                break;
                              case IOBHI:
                                 insertionIndex=numToAdd;
                                break;
                              case BEGINNING:
                                insertionIndex=0;
                                break;
                              default:
                                throw new Error("Unknown seqLocation "+seqLocation);
                            }
                            Assertions.assertThrows(preModScenario.expectedException,()->seqMonitor.put(insertionIndex,0,inputArgType));
                            seqMonitor.verifyStructuralIntegrity();
                            verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(numToAdd);
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
  public void testtoArray_void(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var preModScenario:PreModScenario.values()){
          if(preModScenario.expectedException==null || (checkedType.checked && preModScenario!=PreModScenario.ModSeq && !nestedType.rootType)){
            for(int numToAdd:AbstractBooleanSeqMonitor.FIB_SEQ){
              for(var outputArgType:BooleanOutputTestArgType.values()){
                TestExecutorService.submitTest(()->{
                  var seqMonitor=new SeqMonitor(nestedType,checkedType);
                  for(int i=0;i<numToAdd;++i){
                    seqMonitor.add(i);
                  }
                  seqMonitor.illegalAdd(preModScenario);
                  if(preModScenario.expectedException==null){
                    outputArgType.verifyToArray(seqMonitor.seq,numToAdd);
                  }else{
                    Assertions.assertThrows(preModScenario.expectedException,()->outputArgType.verifyToArray(seqMonitor.seq,numToAdd));
                  }
                  seqMonitor.verifyStructuralIntegrity();
                  seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyPostAlloc(preModScenario);
                });
              }
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
        for(var preModScenario:PreModScenario.values()){
          if(preModScenario.expectedException==null || (checkedType.checked && preModScenario!=PreModScenario.ModSeq && !nestedType.rootType)){
            for(int tmpSeqSize=0;tmpSeqSize<=15;tmpSeqSize+=5){
              final int seqSize=tmpSeqSize;
              for(int tmpArrSize=0,tmpArrSizeBound=seqSize+5;tmpArrSize<=tmpArrSizeBound;tmpArrSize+=5){
                final int arrSize=tmpArrSize;
                TestExecutorService.submitTest(()->{
                  var seqMonitor=new SeqMonitor(nestedType,checkedType);
                  for(int i=0;i<seqSize;++i){
                    seqMonitor.add(i);
                  }
                  seqMonitor.illegalAdd(preModScenario);
                  Boolean[] paramArr=new Boolean[arrSize];
                  for(int i=seqSize,bound=seqSize+arrSize;i<bound;++i){
                    paramArr[i-seqSize]=TypeConversionUtil.convertToBoolean(i);
                  }
                  if(preModScenario.expectedException==null){
                    var resultArr=seqMonitor.seq.toArray(paramArr);
                    if(arrSize<seqSize){
                      Assertions.assertNotSame(paramArr,resultArr);
                      Assertions.assertEquals(seqSize,resultArr.length);
                    }else if(arrSize>seqSize){
                      Assertions.assertSame(paramArr,resultArr);
                      Assertions.assertNull(resultArr[seqSize]);
                      for(int i=seqSize+1;i<arrSize;++i){
                        Assertions.assertEquals(TypeConversionUtil.convertToBoolean(i+seqSize),resultArr[i]);
                      }
                    }else{
                      Assertions.assertSame(paramArr,resultArr);
                    }
                    var itr=seqMonitor.seq.iterator();
                    for(int i=0;i<seqSize;++i){
                      Assertions.assertEquals((Object)itr.nextBoolean(),resultArr[i]);
                    }
                  }else{
                    Assertions.assertThrows(preModScenario.expectedException,()->seqMonitor.seq.toArray(paramArr));
                  }
                  seqMonitor.verifyStructuralIntegrity();
                  seqMonitor.verifyPreAlloc().verifyAscending(seqSize).verifyPostAlloc(preModScenario);
                });
              }
            }
          }
        }
      }
    }
    TestExecutorService.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testtoArray_IntFunction(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var preModScenario:PreModScenario.values()){
          if(preModScenario.expectedException==null || (checkedType.checked && preModScenario!=PreModScenario.ModSeq && !nestedType.rootType)){
            for(var monitoredFunctionGen:MonitoredFunctionGen.values()){
              if((checkedType.checked || monitoredFunctionGen.expectedException==null)&&(!nestedType.rootType || monitoredFunctionGen.appliesToRoot) &&(nestedType.rootType || monitoredFunctionGen.appliesToSubList)){
                for(int numToAdd:AbstractBooleanSeqMonitor.FIB_SEQ){
                  TestExecutorService.submitTest(()->{
                    var seqMonitor=new SeqMonitor(nestedType,checkedType);
                    for(int i=0;i<numToAdd;++i){
                      seqMonitor.add(i);
                    }
                    seqMonitor.illegalAdd(preModScenario);
                    var arrConstructor=monitoredFunctionGen.getMonitoredArrayConstructor(seqMonitor);
                    if(preModScenario.expectedException==null){
                      if(monitoredFunctionGen.expectedException==null){
                        var resultArr=seqMonitor.seq.toArray(arrConstructor);
                        Assertions.assertEquals(numToAdd,resultArr.length);
                        var itr=seqMonitor.seq.iterator();
                        for(int i=0;i<numToAdd;++i){
                          Assertions.assertEquals(resultArr[i],(Object)itr.nextBoolean());
                        }
                      }else{
                         Assertions.assertThrows(monitoredFunctionGen.expectedException,()->seqMonitor.seq.toArray(arrConstructor));
                      }
                    }else{
                      Assertions.assertThrows(preModScenario.expectedException,()->seqMonitor.seq.toArray(arrConstructor));
                    }
                    var verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(numToAdd);
                    switch(monitoredFunctionGen){
                      case NoThrow:
                      case Throw:
                        verifyItr.verifyPostAlloc(preModScenario);
                        break;
                      case ModSeq:
                      case ThrowModSeq:
                        if(preModScenario==PreModScenario.NoMod){
                           verifyItr.verifyIllegalAdd().verifyPostAlloc();
                        }else{
                          verifyItr.verifyPostAlloc(preModScenario);
                        }
                        break;
                      case ModParent:
                      case ThrowModParent:
                        if(preModScenario==PreModScenario.ModSeq){
                          verifyItr.verifyIllegalAdd();
                        }
                        verifyItr.verifyParentPostAlloc();
                        if(preModScenario!=PreModScenario.ModRoot){
                          verifyItr.verifyIllegalAdd();
                          if(preModScenario!=PreModScenario.NoMod){
                            verifyItr.verifyIllegalAdd();
                          }
                        }
                        verifyItr.verifyRootPostAlloc();
                        if(preModScenario==PreModScenario.ModRoot){
                          verifyItr.verifyIllegalAdd();
                        }
                        break;
                      case ModRoot:
                      case ThrowModRoot:
                        verifyItr.verifyPostAlloc(preModScenario).verifyIllegalAdd();
                        break;
                      default:
                        throw new Error("Unknown monitoredFunctionGen "+monitoredFunctionGen);
                    }
                    seqMonitor.verifyStructuralIntegrity();
                  });
                }
              }
            }   
          }
        }
      }
    }
    TestExecutorService.completeAllTests();
  }
  interface NonComparatorSortTest{
    void runTest(SeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredComparatorGen monitoredComparatorGen,int seqSize);
    private void runTests(){
      for(var nestedType:NestedType.values()){
        for(var checkedType:CheckedType.values()){
          for(var preModScenario:PreModScenario.values()){
            if((checkedType.checked || preModScenario.expectedException==null) && ((nestedType.rootType && preModScenario.expectedException==null)||(!nestedType.rootType && preModScenario.appliesToSubList))){
              for(var monitoredComparatorGen:MonitoredComparatorGen.values()){
                if(monitoredComparatorGen.nullComparator && ((!nestedType.rootType || monitoredComparatorGen.appliesToRoot) && (checkedType.checked || monitoredComparatorGen.expectedException==null))){
                  for(int seqSize:AbstractBooleanSeqMonitor.FIB_SEQ){
                    TestExecutorService.submitTest(()->runTest(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredComparatorGen,seqSize));
                  }
                }
              }
            }
          }
        }
      }
      TestExecutorService.completeAllTests();
    }
  }
  @org.junit.jupiter.api.Test
  public void testListstableDescendingSort_void(){
    NonComparatorSortTest test=(seqMonitor,preModScenario,monitoredComparatorGen,numToAdd)->{
      monitoredComparatorGen.initReverse(seqMonitor,numToAdd,preModScenario);
      if(preModScenario.expectedException==null){
        if(monitoredComparatorGen.expectedException==null || numToAdd<2){
          seqMonitor.stableDescendingSort();
          monitoredComparatorGen.assertReverseSorted(seqMonitor,numToAdd,preModScenario);
        }else{
          Assertions.assertThrows(monitoredComparatorGen.expectedException,()->seqMonitor.stableDescendingSort());
          monitoredComparatorGen.assertReverseUnmodified(seqMonitor,numToAdd,preModScenario);
        }
      }else{
        Assertions.assertThrows(preModScenario.expectedException,()->seqMonitor.stableDescendingSort());
        monitoredComparatorGen.assertReverseUnmodified(seqMonitor,numToAdd,preModScenario);
      }
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testListstableAscendingSort_void(){
    NonComparatorSortTest test=(seqMonitor,preModScenario,monitoredComparatorGen,numToAdd)->{
      monitoredComparatorGen.init(seqMonitor,numToAdd,preModScenario);
      if(preModScenario.expectedException==null){
        if(monitoredComparatorGen.expectedException==null || numToAdd<2){
          seqMonitor.stableAscendingSort();
          monitoredComparatorGen.assertSorted(seqMonitor,numToAdd,preModScenario);
        }else{
          Assertions.assertThrows(monitoredComparatorGen.expectedException,()->seqMonitor.stableAscendingSort());
          monitoredComparatorGen.assertUnmodified(seqMonitor,numToAdd,preModScenario);
        }
      }else{
        Assertions.assertThrows(preModScenario.expectedException,()->seqMonitor.stableAscendingSort());
        monitoredComparatorGen.assertUnmodified(seqMonitor,numToAdd,preModScenario);
      }
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testListsort_Comparator(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var preModScenario:PreModScenario.values()){
          if((checkedType.checked || preModScenario.expectedException==null) && ((nestedType.rootType && preModScenario.expectedException==null)||(!nestedType.rootType && preModScenario.appliesToSubList))){
            for(var monitoredComparatorGen:MonitoredComparatorGen.values()){
              if((!nestedType.rootType || monitoredComparatorGen.appliesToRoot) && (checkedType.checked || monitoredComparatorGen.expectedException==null)){
                for(int numToAdd:new int[]{0,2}){
                  for(var functionCallType:FunctionCallType.values()){
                    TestExecutorService.submitTest(()->{
                      var seqMonitor=new SeqMonitor(nestedType,checkedType);
                      monitoredComparatorGen.init(seqMonitor,numToAdd,preModScenario);
                      var sorter=monitoredComparatorGen.getMonitoredComparator(seqMonitor);
                      if(preModScenario.expectedException==null){
                        if(monitoredComparatorGen.expectedException==null || numToAdd<2){
                          seqMonitor.sort(sorter,functionCallType);
                          monitoredComparatorGen.assertSorted(seqMonitor,numToAdd,preModScenario);
                        }else{
                          Assertions.assertThrows(monitoredComparatorGen.expectedException,()->seqMonitor.sort(sorter,functionCallType));
                          monitoredComparatorGen.assertUnmodified(seqMonitor,numToAdd,preModScenario);
                        }
                      }else{
                        Assertions.assertThrows(preModScenario.expectedException,()->seqMonitor.sort(sorter,functionCallType));
                        monitoredComparatorGen.assertUnmodified(seqMonitor,numToAdd,preModScenario);
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
    TestExecutorService.completeAllTests();
  }
  interface ToStringAndHashCodeTest
  {
    void runTest(SeqMonitor seqMonitor,PreModScenario preModScenario,int numToAdd
    );
    private void runTests(){
      for(var nestedType:NestedType.values()){
        for(var checkedType:CheckedType.values()){
          for(var preModScenario:PreModScenario.values()){
            if((checkedType.checked || preModScenario.expectedException==null) && ((nestedType.rootType && preModScenario.expectedException==null)||(!nestedType.rootType && preModScenario.appliesToSubList))){
              for(int seqSize:AbstractBooleanSeqMonitor.FIB_SEQ){
                TestExecutorService.submitTest(()->runTest(new SeqMonitor(nestedType,checkedType),preModScenario,seqSize));
              }
            }
          }
        }
      }
      TestExecutorService.completeAllTests();
    }
  }
  @org.junit.jupiter.api.Test
  public void testtoString_void(){
    ToStringAndHashCodeTest test=(seqMonitor,preModScenario,numToAdd
    )->{
      {
        for(int i=0;i<numToAdd;++i){
          seqMonitor.add(i);
        }
      }
      seqMonitor.illegalAdd(preModScenario);
      if(preModScenario.expectedException==null){
        {
          var resultStr=seqMonitor.seq.toString();
          seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyPostAlloc();
          var itr=seqMonitor.seq.iterator();
          var arrList=new ArrayList<Object>();
          for(int i=0;i<numToAdd;++i){
            arrList.add(itr.next());
          }
          Assertions.assertEquals(arrList.toString(),resultStr);
        }
      }else{
        Assertions.assertThrows(preModScenario.expectedException,()->seqMonitor.seq.toString());
        seqMonitor.verifyThrowCondition(numToAdd,preModScenario
        );
      }
      seqMonitor.verifyStructuralIntegrity();
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testhashCode_void(){
    ToStringAndHashCodeTest test=(seqMonitor,preModScenario,numToAdd
    )->{
      {
        for(int i=0;i<numToAdd;++i){
          seqMonitor.add(i);
        }
      }
      seqMonitor.illegalAdd(preModScenario);
      if(preModScenario.expectedException==null){
        {
          int resultHash=seqMonitor.seq.hashCode();
          seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyPostAlloc();
          var itr=seqMonitor.seq.iterator();
          int expectedHash=1;
          for(int i=0;i<numToAdd;++i){
            expectedHash=(expectedHash*31)+itr.next().hashCode();
          }
          Assertions.assertEquals(expectedHash,resultHash);
        }
      }else{
        Assertions.assertThrows(preModScenario.expectedException,()->seqMonitor.seq.hashCode());
        seqMonitor.verifyThrowCondition(numToAdd,preModScenario
        );
      }
      seqMonitor.verifyStructuralIntegrity();
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testListsubList_int_int(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var preModScenario:PreModScenario.values()){
          if(preModScenario!=PreModScenario.ModSeq && (preModScenario.expectedException==null || (checkedType.checked && !nestedType.rootType))){
            for(int tmpSeqSize=0;tmpSeqSize<=5;++tmpSeqSize){
              final int seqSize=tmpSeqSize;
              for(int tmpFromIndex=-2;tmpFromIndex<=seqSize+1;++tmpFromIndex){
                if(checkedType.checked || tmpFromIndex>=0){
                  final int fromIndex=tmpFromIndex;
                  for(int tmpToIndex=-1;tmpToIndex<=seqSize+2;++tmpToIndex){
                    if(checkedType.checked || (tmpToIndex>=fromIndex && tmpToIndex<=seqSize)){
                      final int toIndex=tmpToIndex;
                      TestExecutorService.submitTest(()->{
                        var seqMonitor=new SeqMonitor(nestedType,checkedType);
                        for(int i=0;i<seqSize;++i){
                          seqMonitor.add(i);
                        }
                        seqMonitor.illegalAdd(preModScenario);
                        if(preModScenario.expectedException==null){
                          if(fromIndex>=0 && fromIndex<=toIndex && toIndex<=seqSize){
                            var subList=(BooleanDblLnkSeq)seqMonitor.seq.subList(fromIndex,toIndex);
                            if(seqMonitor.checkedType.checked){
                              Assertions.assertEquals(seqMonitor.expectedSeqModCount,FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.modCount(subList));
                              if(seqMonitor.nestedType.rootType){
                                Assertions.assertNull(FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.parent(subList));
                                Assertions.assertSame(seqMonitor.seq,FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.root(subList));
                              }else{
                                Assertions.assertSame(seqMonitor.seq,FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.parent(subList));
                                Assertions.assertSame(seqMonitor.parents[seqMonitor.parents.length-1],FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.root(subList));
                              }
                              Assertions.assertEquals(fromIndex,FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.parentOffset(subList));
                            }else{
                              if(seqMonitor.nestedType.rootType){
                                Assertions.assertNull(FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedSubList.parent(subList));
                                Assertions.assertSame(seqMonitor.seq,FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedSubList.root(subList));
                              }else{
                                Assertions.assertSame(seqMonitor.seq,FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedSubList.parent(subList));
                                Assertions.assertSame(seqMonitor.parents[seqMonitor.parents.length-1],FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedSubList.root(subList));
                              }
                              Assertions.assertEquals(fromIndex,FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedSubList.parentOffset(subList));
                            }
                            Assertions.assertSame(toIndex-fromIndex,subList.size);
                            var subListHead=subList.head;
                            var subListTail=subList.tail;
                            if(toIndex-fromIndex==0){
                              Assertions.assertNull(subListHead);
                              Assertions.assertNull(subListTail);
                            }else{
                              Assertions.assertSame(BooleanDblLnkNode.iterateAscending(seqMonitor.seq.head,fromIndex),subListHead);
                              Assertions.assertSame(BooleanDblLnkNode.iterateDescending(seqMonitor.seq.tail,seqMonitor.seq.size-toIndex),subListTail);
                            }
                          }else{
                            Assertions.assertThrows(IndexOutOfBoundsException.class,()->seqMonitor.seq.subList(fromIndex,toIndex));
                          }
                        }else{
                          Assertions.assertThrows(preModScenario.expectedException,()->seqMonitor.seq.subList(fromIndex,toIndex));
                        }
                        seqMonitor.verifyStructuralIntegrity();
                        seqMonitor.verifyPreAlloc().verifyAscending(seqSize).verifyPostAlloc(preModScenario);
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
    TestExecutorService.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testListlistIterator_int(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var preModScenario:PreModScenario.values()){
          if(preModScenario!=PreModScenario.ModSeq && (preModScenario.expectedException==null || (checkedType.checked && !nestedType.rootType))){
            for(var seqLocation:SequenceLocation.values()){
              if(seqLocation.expectedException!=null && checkedType.checked){
                TestExecutorService.submitTest(()->{
                  var seqMonitor=new SeqMonitor(nestedType,checkedType);
                  for(int i=0;i<100;++i){
                    seqMonitor.add(i);
                  }
                  seqMonitor.illegalAdd(preModScenario);
                  final int itrIndex;
                  switch(seqLocation){
                    case IOBLO:
                      itrIndex=-1;
                      break;
                    case IOBHI:
                      itrIndex=101;
                      break;
                    case BEGINNING:
                      itrIndex=0;
                      break;
                    case MIDDLE:
                      itrIndex=50;
                      break;
                    case END:
                      itrIndex=100;
                      break;
                    default:
                      throw new Error("Unknown seqLocation "+seqLocation);
                  }
                  Class<? extends Throwable> expectedException=(preModScenario.expectedException==null)?(seqLocation.expectedException):(preModScenario.expectedException);
                  if(expectedException==null){
                    var itrMonitor=seqMonitor.getListItrMonitor(itrIndex);
                    itrMonitor.verifyIteratorState();
                    Assertions.assertEquals(itrIndex,itrMonitor.nextIndex());
                  }else{
                    Assertions.assertThrows(expectedException,()->seqMonitor.getListItrMonitor(itrIndex));
                  }
                  seqMonitor.verifyStructuralIntegrity();
                  seqMonitor.verifyPreAlloc().verifyAscending(100).verifyPostAlloc(preModScenario);
                });
              }
            }
          }
        }
      }
    }
    TestExecutorService.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testListlistIterator_void(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var preModScenario:PreModScenario.values()){
          if(preModScenario!=PreModScenario.ModSeq && (preModScenario.expectedException==null || (checkedType.checked && !nestedType.rootType))){
            TestExecutorService.submitTest(()->{
              var seqMonitor=new SeqMonitor(nestedType,checkedType);
              for(int i=0;i<100;++i){
                seqMonitor.add(i);
              }
              seqMonitor.illegalAdd(preModScenario);
              if(preModScenario.expectedException==null){
                var itrMonitor=seqMonitor.getListItrMonitor();
                itrMonitor.verifyIteratorState();
                Assertions.assertEquals(0,itrMonitor.nextIndex());
              }else{
                Assertions.assertThrows(preModScenario.expectedException,()->seqMonitor.getListItrMonitor());
              }
              seqMonitor.verifyStructuralIntegrity();
              seqMonitor.verifyPreAlloc().verifyAscending(100).verifyPostAlloc(preModScenario);
            });
          }
        }
      }
    }
    TestExecutorService.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testiterator_void(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var preModScenario:PreModScenario.values()){
          if(preModScenario!=PreModScenario.ModSeq && (preModScenario.expectedException==null || (checkedType.checked && !nestedType.rootType))){
            TestExecutorService.submitTest(()->{
              var seqMonitor=new SeqMonitor(nestedType,checkedType);
              for(int i=0;i<100;++i){
                seqMonitor.add(i);
              }
              seqMonitor.illegalAdd(preModScenario);
              if(preModScenario.expectedException==null){
                var itrMonitor=seqMonitor.getItrMonitor();
                itrMonitor.verifyIteratorState();
              }else{
                Assertions.assertThrows(preModScenario.expectedException,()->seqMonitor.getItrMonitor());
              }
              seqMonitor.verifyStructuralIntegrity();
              seqMonitor.verifyPreAlloc().verifyAscending(100).verifyPostAlloc(preModScenario);
            });
          }
        }
      }
    }
    TestExecutorService.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testdescendingIterator_void(){
    for(var checkedType:CheckedType.values()){
      TestExecutorService.submitTest(()->{
        var seqMonitor=new SeqMonitor(NestedType.LISTDEQUE,checkedType);
        for(int i=0;i<100;++i){
          seqMonitor.add(i);
        }
        var itrMonitor=seqMonitor.getDescendingItrMonitor();
        itrMonitor.verifyIteratorState();
        seqMonitor.verifyStructuralIntegrity();
        seqMonitor.verifyPreAlloc().verifyAscending(100).verifyPostAlloc();
      });
    }
    TestExecutorService.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testListreplaceAll_UnaryOperator(){
    for(var checkedType:CheckedType.values()){
      for(var nestedType:NestedType.values()){
        for(var preModScenario:PreModScenario.values()){
          if(preModScenario!=PreModScenario.ModSeq&&(preModScenario.expectedException==null||(!nestedType.rootType&&checkedType.checked))){
            for(var monitoredFunctionGen:MonitoredFunctionGen.values()){
              if((checkedType.checked || monitoredFunctionGen.expectedException==null)&&(!nestedType.rootType || monitoredFunctionGen.appliesToRoot) &&(nestedType.rootType || monitoredFunctionGen.appliesToSubList)){
                for(int seqSize:AbstractBooleanSeqMonitor.FIB_SEQ){
                  TestExecutorService.submitTest(()->testListreplaceAll_UnaryOperatorHelper(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredFunctionGen,seqSize,FunctionCallType.Unboxed));
                  TestExecutorService.submitTest(()->testListreplaceAll_UnaryOperatorHelper(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredFunctionGen,seqSize,FunctionCallType.Boxed));
                }
              }
            }   
          }
        }
      }
    }
    TestExecutorService.completeAllTests();
  }
  private static void testListreplaceAll_UnaryOperatorHelper(SeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredFunctionGen monitoredFunctionGen,int numToAdd,FunctionCallType functionCallType){
    for(int i=0;i<numToAdd;++i){
      seqMonitor.add(i);
    }
    seqMonitor.illegalAdd(preModScenario);
    var monitoredUnaryOperator=monitoredFunctionGen.getMonitoredUnaryOperator(seqMonitor);
    int numExpectedIteratedValues;
    if(preModScenario.expectedException==null && (monitoredFunctionGen.expectedException==null||numToAdd==0)){
        seqMonitor.replaceAll(monitoredUnaryOperator,functionCallType);
        seqMonitor.verifyStructuralIntegrity();
        seqMonitor.verifyPreAlloc().verifyAscending(1,numToAdd).verifyPostAlloc(preModScenario);
        numExpectedIteratedValues=numToAdd;
    }else{
      Class<? extends Throwable> expectedException=preModScenario.expectedException==null?monitoredFunctionGen.expectedException:preModScenario.expectedException;
      Assertions.assertThrows(expectedException,()->seqMonitor.replaceAll(monitoredUnaryOperator,functionCallType));
      seqMonitor.verifyStructuralIntegrity();
      var verifyItr=seqMonitor.verifyPreAlloc();
      switch(monitoredFunctionGen){
        case NoThrow:
          numExpectedIteratedValues=numToAdd;
          verifyItr.verifyAscending(1,numToAdd);
          verifyItr.verifyPostAlloc(preModScenario);
          break;
        case Throw:
          numExpectedIteratedValues=Math.min(numToAdd,1);
          verifyItr.verifyAscending(numToAdd);
          verifyItr.verifyPostAlloc(preModScenario);
          break;
        case ModSeq:
          numExpectedIteratedValues=numToAdd;
          switch(preModScenario){
            case ModParent:
              verifyItr.verifyAscending(numToAdd);
              numExpectedIteratedValues=Math.min(numToAdd,1);
              verifyItr.verifyPostAlloc(preModScenario);
              break;
            case ModRoot:
              verifyItr.verifyAscending(numToAdd);
              numExpectedIteratedValues=Math.min(numToAdd,1);
              verifyItr.verifyPostAlloc(preModScenario);
              break;
            case NoMod:
            case ModSeq:
              verifyItr.verifyAscending(1,numToAdd);
              for(int i=0;i<numToAdd;++i){
                verifyItr.verifyIllegalAdd();
              }
              verifyItr.verifyPostAlloc(preModScenario);
              break;
            default:
              throw new Error("Unknown preModScenario "+preModScenario);
          }
          break;
        case ModParent:
          numExpectedIteratedValues=numToAdd;
          switch(preModScenario){
            case ModRoot:
              numExpectedIteratedValues=Math.min(numToAdd,1);
              verifyItr.verifyAscending(numToAdd);
              if(preModScenario==PreModScenario.ModSeq){
                verifyItr.verifyIllegalAdd();
              }
              verifyItr.verifyParentPostAlloc();
              if(preModScenario==PreModScenario.ModParent){
                verifyItr.verifyIllegalAdd();
              }
              verifyItr.verifyRootPostAlloc();
              if(preModScenario==PreModScenario.ModRoot){
                verifyItr.verifyIllegalAdd();
              }
              break;
            case ModParent:
            case ModSeq:
            case NoMod:
              verifyItr.verifyAscending(1,numToAdd);
              if(preModScenario==PreModScenario.ModSeq){
                verifyItr.verifyIllegalAdd();
              }
              verifyItr.verifyParentPostAlloc();
              for(int i=0;i<numToAdd;++i){
                verifyItr.verifyIllegalAdd();
              }
              if(preModScenario==PreModScenario.ModParent){
                verifyItr.verifyIllegalAdd();
              }
              verifyItr.verifyRootPostAlloc();
              if(preModScenario==PreModScenario.ModRoot){
                verifyItr.verifyIllegalAdd();
              }
              break;
            default:
              throw new Error("Unknown preModScenario "+preModScenario);
          }
          break;
        case ModRoot:
          numExpectedIteratedValues=numToAdd;
          switch(preModScenario){
            case ModSeq:
            case ModParent:
            case ModRoot:
            case NoMod:
              verifyItr.verifyAscending(1,numToAdd);
              verifyItr.verifyPostAlloc(preModScenario);
              for(int i=0;i<numToAdd;++i){
                verifyItr.verifyIllegalAdd();
              } 
              break;
            default:
              throw new Error("Unknown preModScenario "+preModScenario);
          }
          break;
        case ThrowModSeq:
          numExpectedIteratedValues=1;
          verifyItr.verifyAscending(numToAdd);
          switch(preModScenario){
            case ModParent:
            case ModRoot:
              numExpectedIteratedValues=Math.min(numToAdd,1);
              verifyItr.verifyPostAlloc(preModScenario);
              break;
            case NoMod:
            case ModSeq:
              verifyItr.verifyIllegalAdd();
              verifyItr.verifyPostAlloc(preModScenario);
              break;
            default:
              throw new Error("Unknown preModScenario "+preModScenario);
          }
          break;
        case ThrowModParent:
          numExpectedIteratedValues=1;
          verifyItr.verifyAscending(numToAdd);
          switch(preModScenario){
            case ModParent:
              verifyItr.verifyParentPostAlloc();
              verifyItr.verifyIllegalAdd();
              numExpectedIteratedValues=Math.min(numToAdd,1);
              for(int i=0;i<numExpectedIteratedValues;++i)
              {
                verifyItr.verifyIllegalAdd();
              }
              verifyItr.verifyRootPostAlloc();
              break;
            case ModRoot:
              numExpectedIteratedValues=Math.min(numToAdd,1);
              if(preModScenario==PreModScenario.ModSeq){
                verifyItr.verifyIllegalAdd();
              }
              verifyItr.verifyParentPostAlloc();
              if(preModScenario==PreModScenario.ModParent){
                verifyItr.verifyIllegalAdd();
              }
              verifyItr.verifyRootPostAlloc();
              if(preModScenario==PreModScenario.ModRoot){
                verifyItr.verifyIllegalAdd();
              }
              break;
            case NoMod:
            case ModSeq:
              if(preModScenario==PreModScenario.ModSeq){
                verifyItr.verifyIllegalAdd();
              }
              verifyItr.verifyParentPostAlloc();
              verifyItr.verifyIllegalAdd();
              if(preModScenario==PreModScenario.ModParent){
                verifyItr.verifyIllegalAdd();
              }
              verifyItr.verifyRootPostAlloc();
              if(preModScenario==PreModScenario.ModRoot){
                verifyItr.verifyIllegalAdd();
              }
              break;
            default:
              throw new Error("Unknown preModScenario "+preModScenario);
          }
          break;
        case ThrowModRoot:
          numExpectedIteratedValues=1;
          verifyItr.verifyAscending(numToAdd);
          switch(preModScenario){
            case ModParent:
            case ModRoot:
              verifyItr.verifyPostAlloc(preModScenario);
              numExpectedIteratedValues=Math.min(numToAdd,1);
              break;
            case NoMod:
            case ModSeq:
              verifyItr.verifyPostAlloc(preModScenario);
              verifyItr.verifyIllegalAdd();
              break;
            default:
              throw new Error("Unknown preModScenario "+preModScenario);
          }
          break;
        default:
          throw new Error("Unknown monitoredFunctionGen "+monitoredFunctionGen);
      }
    }
    Assertions.assertEquals(numExpectedIteratedValues,monitoredUnaryOperator.encounteredValues.size());
    BooleanDblLnkNode currNode=seqMonitor.seq.head;
    boolean encounteredValuesFlag=false;
    outer: switch(monitoredFunctionGen)
    {
      case ModSeq:
        switch(preModScenario){
          case ModParent:
          case ModRoot:  
            break outer;
          case ModSeq:
          case NoMod:
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
      case ModParent:
        switch(preModScenario){
          case ModRoot:
            break outer;
          case ModParent:
          case ModSeq:
          case NoMod:
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
      case ModRoot:
      case NoThrow:
        encounteredValuesFlag=true;
        break;
      case Throw:
      case ThrowModSeq:
      case ThrowModParent:
      case ThrowModRoot:
        break;
      default:
        throw new Error("Unknown monitoredFunctionGen "+monitoredFunctionGen);
    }
    if(encounteredValuesFlag)
    {
      for(var encounteredValue:monitoredUnaryOperator.encounteredValues)
      {
        var expectedVal=(boolean)!((boolean)encounteredValue);
        Assertions.assertEquals(expectedVal,currNode.val);
        currNode=currNode.next;
      }
    }
    else
    {
      for(var encounteredValue:monitoredUnaryOperator.encounteredValues){
        Assertions.assertEquals(encounteredValue,(Object)currNode.val);
        currNode=currNode.next;
      }
    }
  }
  @org.junit.jupiter.api.Test
  public void testListItrset_val(){
    for(var checkedType:CheckedType.values()){
      for(var listItrSetScenario:ListItrSetScenario.values()){
        if(checkedType.checked || listItrSetScenario.expectedException==null){
          for(var inputArgType:BooleanInputTestArgType.values()){
            if(listItrSetScenario.preModScenario.appliesToRootItr){
              TestExecutorService.submitTest(()->testListItrset_valHelper(new SeqMonitor(NestedType.LISTDEQUE,checkedType),listItrSetScenario,inputArgType));
            }
            TestExecutorService.submitTest(()->testListItrset_valHelper(new SeqMonitor(NestedType.SUBLIST,checkedType),listItrSetScenario,inputArgType));
          }
        }
      }
    }
    TestExecutorService.completeAllTests();
  }
  private static void testListItrset_valHelper(SeqMonitor seqMonitor,ListItrSetScenario listItrSetScenario,BooleanInputTestArgType inputArgType){
    int numToAdd=100;
    for(int i=0;i<numToAdd;++i){
      seqMonitor.add(i);
    }
    var itrMonitor=seqMonitor.getListItrMonitor();
    switch(listItrSetScenario){
      case PostAddThrowISE:
      case PostAddThrowISESupercedesModRootCME:
      case PostAddThrowISESupercedesModParentCME:
      case PostAddThrowISESupercedesModSeqCME:
        itrMonitor.add(0);
        break;
      case PostRemoveThrowISE:
      case PostRemoveThrowISESupercedesModRootCME:
      case PostRemoveThrowISESupercedesModParentCME:
      case PostRemoveThrowISESupercedesModSeqCME:
        itrMonitor.iterateForward();
        itrMonitor.remove();
        break;
      default:
        itrMonitor.iterateForward();
      case ThrowISE:
      case ThrowISESupercedesModRootCME:
      case ThrowISESupercedesModParentCME:
      case ThrowISESupercedesModSeqCME:
    }
    seqMonitor.illegalAdd(listItrSetScenario.preModScenario);
    SequenceVerificationItr verifyItr;
    if(listItrSetScenario.expectedException==null){
      for(int j=0;j<numToAdd;++j){
        itrMonitor.set(numToAdd-j-1,inputArgType);
        itrMonitor.verifyIteratorState();
        seqMonitor.verifyStructuralIntegrity();
        if(!itrMonitor.hasNext()){
          break;
        }
        itrMonitor.iterateForward();
      }
      seqMonitor.verifyPreAlloc().verifyDescending(inputArgType,numToAdd).verifyPostAlloc();
      for(int j=0;j<numToAdd;++j){
        itrMonitor.iterateReverse();
        itrMonitor.set(numToAdd-j-1,inputArgType);
        itrMonitor.verifyIteratorState();
        seqMonitor.verifyStructuralIntegrity();
      }
      verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(inputArgType,numToAdd);
    }else{
      Assertions.assertThrows(listItrSetScenario.expectedException,()->itrMonitor.set(0));
      itrMonitor.verifyIteratorState();
      seqMonitor.verifyStructuralIntegrity();
      verifyItr=seqMonitor.verifyPreAlloc();
      switch(listItrSetScenario){
        case PostRemoveThrowISE:
        case PostRemoveThrowISESupercedesModRootCME:
        case PostRemoveThrowISESupercedesModParentCME:
        case PostRemoveThrowISESupercedesModSeqCME:
          verifyItr.verifyAscending(1,numToAdd-1);
          break;
        case PostAddThrowISE:
        case PostAddThrowISESupercedesModRootCME:
        case PostAddThrowISESupercedesModParentCME:
        case PostAddThrowISESupercedesModSeqCME:
          verifyItr.verifyIllegalAdd();
        default:
          verifyItr.verifyAscending(numToAdd);
      }
    }
    verifyItr.verifyPostAlloc(listItrSetScenario.preModScenario);
  }
  @org.junit.jupiter.api.Test
  public void testItrnext_void(){
    for(var checkedType:CheckedType.values()){
      for(var itrScenario:IterationScenario.values()){
        if(checkedType.checked || itrScenario==IterationScenario.NoMod){
          for(int numToAdd:AbstractBooleanSeqMonitor.FIB_SEQ){
            if(numToAdd!=0 || itrScenario.validWithEmptySeq){
              for(var itrType:ItrType.values()){
                for(var nestedType:NestedType.values()){
                  if((itrType!=ItrType.DescendingItr || nestedType.rootType) && (itrScenario.preModScenario.appliesToRootItr || !nestedType.rootType)){
                    for(var outputType:BooleanOutputTestArgType.values()){
                      TestExecutorService.submitTest(()->{
                        var seqMonitor=new SeqMonitor(nestedType,checkedType);
                        for(int i=0;i<numToAdd;++i){
                          seqMonitor.add(i);
                        }
                        var itrMonitor=seqMonitor.getItrMonitor(itrType);
                        switch(itrScenario){
                          case NoMod:
                          case ModSeqSupercedesThrowNSEE:
                          case ModParentSupercedesThrowNSEE:
                          case ModRootSupercedesThrowNSEE:
                            for(int i=0;;++i){
                              if(itrType==ItrType.ListItr){
                                Assertions.assertEquals(i,itrMonitor.nextIndex());
                                Assertions.assertEquals(i-1,itrMonitor.previousIndex());
                              }
                              if(i==numToAdd){
                                Assertions.assertFalse(itrMonitor.hasNext());
                                break;
                              }
                              Assertions.assertTrue(itrMonitor.hasNext());
                              itrMonitor.verifyNext(itrType==ItrType.DescendingItr?numToAdd-i-1:i,outputType);
                              itrMonitor.verifyIteratorState();
                              seqMonitor.verifyStructuralIntegrity();
                            }
                          case ModSeq:
                          case ModParent:
                          case ModRoot:  
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
                        seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyPostAlloc(itrScenario.preModScenario);
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
    TestExecutorService.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testListItrprevious_void(){
    for(var checkedType:CheckedType.values()){
      for(var itrScenario:IterationScenario.values()){
        if(checkedType.checked || itrScenario==IterationScenario.NoMod){
          for(int seqSize:AbstractBooleanSeqMonitor.FIB_SEQ){
            if(seqSize!=0 || itrScenario.validWithEmptySeq){
              for(var outputType:BooleanOutputTestArgType.values()){
                if(itrScenario.preModScenario.appliesToRootItr){
                  TestExecutorService.submitTest(()->testListItrprevious_voidHelper(new SeqMonitor(NestedType.LISTDEQUE,checkedType),itrScenario,seqSize,outputType));
                }
                TestExecutorService.submitTest(()->testListItrprevious_voidHelper(new SeqMonitor(NestedType.SUBLIST,checkedType),itrScenario,seqSize,outputType));
              }
            }
          }
        }
      }
    }
    TestExecutorService.completeAllTests();
  }
  private static void testListItrprevious_voidHelper(SeqMonitor seqMonitor,IterationScenario itrScenario,int numToAdd,BooleanOutputTestArgType outputType){
    for(int i=0;i<numToAdd;++i){
      seqMonitor.add(i);
    }
    var itrMonitor=seqMonitor.getListItrMonitor(numToAdd);
    switch(itrScenario){
      case NoMod:
      case ModSeqSupercedesThrowNSEE:
      case ModParentSupercedesThrowNSEE:
      case ModRootSupercedesThrowNSEE:
        for(int i=0;;++i){
          Assertions.assertEquals(numToAdd-i,itrMonitor.nextIndex());
          Assertions.assertEquals(numToAdd-i-1,itrMonitor.previousIndex());
          if(i==numToAdd){
            Assertions.assertFalse(itrMonitor.hasPrevious());
            break;
          }
          Assertions.assertTrue(itrMonitor.hasPrevious());
          itrMonitor.verifyPrevious(numToAdd-i-1,outputType);
          itrMonitor.verifyIteratorState();
          seqMonitor.verifyStructuralIntegrity();
        }
      case ModSeq:
      case ModParent:
      case ModRoot:  
        break;
      default:
        throw new Error("unknown itr scenario "+itrScenario);
    }
    if(seqMonitor.checkedType.checked){
      seqMonitor.illegalAdd(itrScenario.preModScenario);
      Assertions.assertThrows(itrScenario.expectedException,()->itrMonitor.iterateReverse());
    }
    itrMonitor.verifyIteratorState();
    seqMonitor.verifyStructuralIntegrity();
    seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyPostAlloc(itrScenario.preModScenario);
  }
  @org.junit.jupiter.api.Test
  public void testListItrpreviousIndex_void_And_ListItrnextIndex_void(){
    for(var checkedType:CheckedType.values()){
      for(var nestedType:NestedType.values()){
        TestExecutorService.submitTest(()->{
          var seqMonitor=new SeqMonitor(nestedType,checkedType);
          int numToAdd=100;
          for(int i=0;i<numToAdd;++i){
            seqMonitor.add(i);
          }
          var itrMonitor=seqMonitor.getListItrMonitor();
          for(int i=0;i<numToAdd;++i){
            Assertions.assertEquals(i,itrMonitor.nextIndex());
            Assertions.assertEquals(i-1,itrMonitor.previousIndex());
            itrMonitor.verifyIteratorState();
            seqMonitor.verifyStructuralIntegrity();
            itrMonitor.iterateForward();
          }
          for(int i=numToAdd;i>0;){
            Assertions.assertEquals(i,itrMonitor.nextIndex());
            Assertions.assertEquals(--i,itrMonitor.previousIndex());
            itrMonitor.verifyIteratorState();
            seqMonitor.verifyStructuralIntegrity();
            itrMonitor.iterateReverse();
          }
          seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyPostAlloc();
        });
      }  
    }
    TestExecutorService.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testforEach_Consumer(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var preModScenario:PreModScenario.values()){
          if(preModScenario!=PreModScenario.ModSeq && (preModScenario.expectedException==null || (checkedType.checked && !nestedType.rootType))){
            for(var monitoredFunctionGen:MonitoredFunctionGen.values()){
              if((checkedType.checked || monitoredFunctionGen.expectedException==null)&&(!nestedType.rootType || monitoredFunctionGen.appliesToRoot) &&(nestedType.rootType || monitoredFunctionGen.appliesToSubList)){
                for(int seqSize:AbstractBooleanSeqMonitor.FIB_SEQ){
                  TestExecutorService.submitTest(()->testforEach_ConsumerHelper(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredFunctionGen,seqSize,FunctionCallType.Unboxed));
                  TestExecutorService.submitTest(()->testforEach_ConsumerHelper(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredFunctionGen,seqSize,FunctionCallType.Boxed));
                }
              }
            }
          }
        }
      }
    }
    TestExecutorService.completeAllTests();
  }
  private static void testforEach_ConsumerHelper(SeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredFunctionGen monitoredFunctionGen,int numToAdd,FunctionCallType functionCallType){
    for(int i=0;i<numToAdd;++i){
      seqMonitor.add(i);
    }
    seqMonitor.illegalAdd(preModScenario);
    var monitoredConsumer=monitoredFunctionGen.getMonitoredConsumer(seqMonitor);
    int numExpectedIteratedValues;
    if(preModScenario.expectedException==null){
      if(monitoredFunctionGen.expectedException==null || numToAdd==0){
        seqMonitor.forEach(monitoredConsumer,functionCallType);
        seqMonitor.verifyStructuralIntegrity();
        seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyPostAlloc(preModScenario);
        numExpectedIteratedValues=numToAdd;
      }else{
        Assertions.assertThrows(monitoredFunctionGen.expectedException,()->seqMonitor.forEach(monitoredConsumer,functionCallType));
        seqMonitor.verifyStructuralIntegrity();
        var verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(numToAdd);
        switch(monitoredFunctionGen){
          case Throw:
            numExpectedIteratedValues=1;
            verifyItr.verifyPostAlloc();
            break;
          case ModSeq:
            numExpectedIteratedValues=numToAdd;
            for(int i=0;i<numToAdd;++i){
              verifyItr.verifyIllegalAdd();
            }
            verifyItr.verifyPostAlloc();
            break;
          case ModParent:
            numExpectedIteratedValues=numToAdd;
            verifyItr.verifyParentPostAlloc();
            for(int i=0;i<numToAdd;++i){
              verifyItr.verifyIllegalAdd();
            }
            verifyItr.verifyRootPostAlloc();
            break;
          case ModRoot:
            numExpectedIteratedValues=numToAdd;
            verifyItr.verifyPostAlloc();
            for(int i=0;i<numToAdd;++i){
              verifyItr.verifyIllegalAdd();
            }
            break;
          case ThrowModSeq:
            numExpectedIteratedValues=1;
            verifyItr.verifyPostAlloc(PreModScenario.ModSeq);
            break;
          case ThrowModParent:
            numExpectedIteratedValues=1;
            verifyItr.verifyPostAlloc(PreModScenario.ModParent);
            break;
          case ThrowModRoot:
            numExpectedIteratedValues=1;
            verifyItr.verifyPostAlloc(PreModScenario.ModRoot);
            break;
          default:
            throw new Error("Unknown monitored function gen "+monitoredFunctionGen);
        }
      }
    }else{
      Assertions.assertThrows(preModScenario.expectedException,()->seqMonitor.forEach(monitoredConsumer,functionCallType));
      seqMonitor.verifyStructuralIntegrity();
      var verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(numToAdd);
      switch(monitoredFunctionGen){
        case NoThrow:
          numExpectedIteratedValues=numToAdd;
          verifyItr.verifyPostAlloc(preModScenario);
          break;
        case Throw:
        case ModSeq:
          numExpectedIteratedValues=numToAdd==0?0:1;
          verifyItr.verifyPostAlloc(preModScenario);
          break;
        case ModParent:
          switch(preModScenario){
            case ModRoot:
              numExpectedIteratedValues=numToAdd==0?0:1;
              verifyItr.verifyPostAlloc(preModScenario);
              break;
            case ModParent:
              numExpectedIteratedValues=numToAdd;
              verifyItr.verifyParentPostAlloc();
              for(int i=0;i<numExpectedIteratedValues+1;++i){
                verifyItr.verifyIllegalAdd();
              }
              verifyItr.verifyRootPostAlloc();
              break;
            default:
              throw new Error("Unknown preModScenario "+preModScenario);
          }
          break;
        case ModRoot:
          numExpectedIteratedValues=numToAdd;
          verifyItr.verifyPostAlloc(preModScenario);
          for(int i=0;i<numExpectedIteratedValues;++i){
            verifyItr.verifyIllegalAdd();
          }
          break;
        case ThrowModSeq:
          verifyItr.verifyPostAlloc(preModScenario);
          numExpectedIteratedValues=numToAdd==0?0:1;
          break;
        case ThrowModParent:
          numExpectedIteratedValues=numToAdd==0?0:1;
          switch(preModScenario){
            case ModParent:
              verifyItr.verifyParentPostAlloc();
              for(int i=0;i<numExpectedIteratedValues;++i){
                verifyItr.verifyIllegalAdd();
              }
              verifyItr.verifyIllegalAdd().verifyRootPostAlloc();
              break;
            case ModRoot:
              verifyItr.verifyPostAlloc(preModScenario);
              break;
            default:
              throw new Error("Unknown preModScenario "+preModScenario);
          }
          break;
        case ThrowModRoot:
          numExpectedIteratedValues=numToAdd==0?0:1;
          verifyItr.verifyPostAlloc(preModScenario);
          for(int i=0;i<numExpectedIteratedValues;++i){
            verifyItr.verifyIllegalAdd();
          }
          break;
        default:
          throw new Error("Unknown monitored function gen "+monitoredFunctionGen);
      }
    }
    Assertions.assertEquals(numExpectedIteratedValues,monitoredConsumer.encounteredValues.size());
    var currNode=seqMonitor.seq.head;
    for(var encounteredValue:monitoredConsumer.encounteredValues){
      Assertions.assertEquals(encounteredValue,(Object)currNode.val);
      currNode=currNode.next;
    }
  }
  static enum NestedType{
    LISTDEQUE(true),
    SUBLIST(false);
    final boolean rootType;
    final boolean forwardIteration=true;
    NestedType(boolean rootType){
      this.rootType=rootType;
    }
  }
  private static class SeqMonitor extends AbstractBooleanSeqMonitor<BooleanDblLnkSeq>{
    private static final BooleanDblLnkSeq[] EMPTY_PARENTS=new BooleanDblLnkSeq[0];
    final NestedType nestedType;
    final BooleanDblLnkSeq[] parents;
    final int[] parentOffsets;
    final int[] expectedParentModCounts;
    final int[] expectedParentSizes;
    final int parentPreAlloc;
    final int parentPostAlloc;
    final int rootPostAlloc;
    SeqMonitor(CheckedType checkedType,NestedType nestedType,BooleanDblLnkNode head,int seqSize,BooleanDblLnkNode tail){
      super(checkedType);
      this.nestedType=nestedType;
      this.parentPreAlloc=0;
      this.parentPostAlloc=0;
      this.rootPostAlloc=0;
      this.expectedSeqSize=seqSize;
      switch(nestedType){
        case LISTDEQUE:
          this.seq=checkedType.checked?new BooleanDblLnkSeq.CheckedList(head,seqSize,tail):new BooleanDblLnkSeq.UncheckedList(head,seqSize,tail);
          this.parents=EMPTY_PARENTS;
          this.parentOffsets=OmniArray.OfInt.DEFAULT_ARR;
          this.expectedParentModCounts=OmniArray.OfInt.DEFAULT_ARR;
          this.expectedParentSizes=OmniArray.OfInt.DEFAULT_ARR;
          break;
        case SUBLIST:
          this.parents=new BooleanDblLnkSeq[]{checkedType.checked?new BooleanDblLnkSeq.CheckedList(head,seqSize,tail):new BooleanDblLnkSeq.UncheckedList(head,seqSize,tail)};
          this.parentOffsets=new int[1];
          this.expectedParentModCounts=new int[1];
          this.expectedParentSizes=new int[]{seqSize};
          this.seq=(BooleanDblLnkSeq)this.parents[0].subList(0,seqSize);
          break;
        default:
          throw new Error("Unknown nestedType "+nestedType);
      }
    }
    SeqMonitor(NestedType nestedType,CheckedType checkedType){
      super(checkedType);
      this.nestedType=nestedType;
      switch(nestedType){
        case LISTDEQUE:
          this.seq=checkedType.checked?new BooleanDblLnkSeq.CheckedList():new BooleanDblLnkSeq.UncheckedList();
          this.parentPreAlloc=0;
          this.parentPostAlloc=0;
          this.rootPostAlloc=0;
          this.parents=EMPTY_PARENTS;
          this.parentOffsets=OmniArray.OfInt.DEFAULT_ARR;
          this.expectedParentModCounts=OmniArray.OfInt.DEFAULT_ARR;
          this.expectedParentSizes=OmniArray.OfInt.DEFAULT_ARR;
          break;
        case SUBLIST:
          var rootHead=new BooleanDblLnkNode(TypeConversionUtil.convertToboolean(Integer.MIN_VALUE));
          var currHead=rootHead;
          for(int i=1;i<10;++i){
            currHead=currHead.next=new BooleanDblLnkNode(currHead,TypeConversionUtil.convertToboolean(Integer.MIN_VALUE+i));
          }
          var rootTail=new BooleanDblLnkNode(TypeConversionUtil.convertToboolean(Integer.MAX_VALUE));
          var currTail=rootTail;
          for(int i=1;i<10;++i){
            currTail=currTail.prev=new BooleanDblLnkNode(TypeConversionUtil.convertToboolean(Integer.MAX_VALUE-i),currTail);
          }
          currHead.next=currTail;
          currTail.prev=currHead;
          BooleanDblLnkSeq root;
          root=checkedType.checked?new BooleanDblLnkSeq.CheckedList(rootHead,20,rootTail):new BooleanDblLnkSeq.UncheckedList(rootHead,20,rootTail);
          this.parents=new BooleanDblLnkSeq[2];
          this.parents[1]=root;
          this.parents[0]=(BooleanDblLnkSeq)root.subList(5,15);
          this.seq=(BooleanDblLnkSeq)parents[0].subList(5,5);
          this.parentOffsets=new int[]{5,5};
          this.expectedParentSizes=new int[]{10,20};
          this.expectedParentModCounts=new int[]{0,0};
          this.parentPreAlloc=5;
          this.parentPostAlloc=5;
          this.rootPostAlloc=5;
          break;
        default:
          throw new Error("Unknown nested type "+nestedType);
      }
    }
    SeqMonitor(CheckedType checkedType,int[] parentPreAllocs,int[] parentPostAllocs){
      super(checkedType);
      Assertions.assertEquals(parentPreAllocs.length,parentPostAllocs.length);
      if(parentPreAllocs.length==0){
        this.parentPreAlloc=0;
        this.parentPostAlloc=0;
        this.rootPostAlloc=0;
        this.nestedType=NestedType.LISTDEQUE;
        this.seq=checkedType.checked?new BooleanDblLnkSeq.CheckedList():new BooleanDblLnkSeq.UncheckedList();
        this.parents=EMPTY_PARENTS;
        this.parentOffsets=OmniArray.OfInt.DEFAULT_ARR;
        this.expectedParentModCounts=OmniArray.OfInt.DEFAULT_ARR;
        this.expectedParentSizes=OmniArray.OfInt.DEFAULT_ARR;
      }else{
        this.nestedType=NestedType.SUBLIST;
        int totalPreAlloc=0;
        int totalPostAlloc=0;
        for(int i=0;i<parentPreAllocs.length;++i){
          totalPreAlloc+=parentPreAllocs[i];
          totalPostAlloc+=parentPostAllocs[i];
        }
        this.rootPostAlloc=parentPostAllocs[parentPostAllocs.length-1];
        this.parentPreAlloc=totalPreAlloc-parentPreAllocs[parentPreAllocs.length-1];
        this.parentPostAlloc=totalPostAlloc-rootPostAlloc;
        BooleanDblLnkNode rootHead;
        BooleanDblLnkNode rootTail;
        if(totalPreAlloc==0){
          if(totalPostAlloc==0){
            rootHead=null;
            rootTail=null;
          }else{
            rootTail=new BooleanDblLnkNode(TypeConversionUtil.convertToboolean(Integer.MAX_VALUE));
            rootHead=rootTail;
            for(int i=1;i<totalPostAlloc;++i){
              rootHead=rootHead.prev=new BooleanDblLnkNode(TypeConversionUtil.convertToboolean(Integer.MAX_VALUE-i),rootHead);
            }
          }
        }else{
          rootHead=new BooleanDblLnkNode(TypeConversionUtil.convertToboolean(Integer.MIN_VALUE));
          rootTail=rootHead;
          for(int i=1;i<totalPreAlloc;++i){
            rootTail=rootTail.next=new BooleanDblLnkNode(rootTail,TypeConversionUtil.convertToboolean(Integer.MIN_VALUE+i));
          }
          for(int i=totalPostAlloc;--i>=0;){
            rootTail=rootTail.next=new BooleanDblLnkNode(rootTail,TypeConversionUtil.convertToboolean(Integer.MAX_VALUE-i));
          }
        }
        BooleanDblLnkSeq root;
        int rootSize=totalPreAlloc+totalPostAlloc;
        root=checkedType.checked?new BooleanDblLnkSeq.CheckedList(rootHead,rootSize,rootTail):new BooleanDblLnkSeq.UncheckedList(rootHead,rootSize,rootTail);
        this.parents=new BooleanDblLnkSeq[parentPreAllocs.length];
        this.parentOffsets=new int[parentPreAllocs.length];
        this.expectedParentModCounts=new int[parentPreAllocs.length];
        this.expectedParentSizes=new int[parentPreAllocs.length];
        this.expectedParentSizes[parentPreAllocs.length-1]=rootSize;
        this.parents[parentPreAllocs.length-1]=root;
        for(int i=parentPreAllocs.length;--i>=1;){
          int fromIndex=parentPreAllocs[i];
          int toIndex=expectedParentSizes[i]-parentPostAllocs[i];
          parents[i-1]=(BooleanDblLnkSeq)parents[i].subList(fromIndex,toIndex);
          parentOffsets[i]=fromIndex;
          expectedParentSizes[i-1]=toIndex-fromIndex;
        }
        int fromIndex=parentPreAllocs[0];
        parentOffsets[0]=fromIndex;
        int toIndex=expectedParentSizes[0]-parentPostAllocs[0];
        this.seq=(BooleanDblLnkSeq)parents[0].subList(fromIndex,toIndex);
        this.expectedSeqSize=toIndex-fromIndex;
        Assertions.assertEquals(0,this.expectedSeqSize);
      }
    }
    void stableAscendingSort(){
      int seqSize=expectedSeqSize;
      ((OmniList.OfBoolean)seq).stableAscendingSort();
      if(seqSize>1){
       verifyFunctionalModification();
      }
    }
    void stableDescendingSort(){
      int seqSize=expectedSeqSize;
      ((OmniList.OfBoolean)seq).stableDescendingSort();
      if(seqSize>1){
       verifyFunctionalModification();
      }
    }
    void sort(MonitoredComparator sorter,FunctionCallType functionCallType){
      int seqSize=expectedSeqSize;
      if(functionCallType==FunctionCallType.Boxed){
        ((OmniList.OfBoolean)seq).sort((Comparator)sorter);
      }else
      {
        ((OmniList.OfBoolean)seq).sort((BooleanComparator)sorter);
      }
      if(seqSize>1){
       verifyFunctionalModification();
      }
    }
    AbstractItrMonitor getItrMonitor(){
      switch(nestedType){
        case LISTDEQUE:
          return checkedType.checked?new CheckedAscendingItrMonitor():new UncheckedAscendingItrMonitor();
        case SUBLIST:
          return checkedType.checked?new CheckedSubAscendingItrMonitor():new UncheckedSubAscendingItrMonitor();
        default:
          throw new Error("Unknown nested type "+nestedType);
      }
    }
    AbstractBooleanSeqMonitor.AbstractItrMonitor getDescendingItrMonitor(){
      return checkedType.checked?new CheckedDescendingItrMonitor():new UncheckedDescendingItrMonitor();
    }
    AbstractItrMonitor getListItrMonitor(){
      switch(nestedType){
        case LISTDEQUE:
          return checkedType.checked?new CheckedBidirectionalItrMonitor():new UncheckedBidirectionalItrMonitor();
        case SUBLIST:
          return checkedType.checked?new CheckedBidirectionalSubItrMonitor():new UncheckedBidirectionalSubItrMonitor();
        default:
          throw new Error("Unknown nested type "+nestedType);
      }
    }
    AbstractItrMonitor getListItrMonitor(int index){
      switch(nestedType){
        case LISTDEQUE:
          return checkedType.checked?new CheckedBidirectionalItrMonitor(index):new UncheckedBidirectionalItrMonitor(index);
        case SUBLIST:
          return checkedType.checked?new CheckedBidirectionalSubItrMonitor(index):new UncheckedBidirectionalSubItrMonitor(index);
        default:
          throw new Error("Unknown nested type "+nestedType);
      }
    }
    SequenceVerificationItr verifyPreAlloc(int expectedVal){
      int rootPreAlloc;
      BooleanDblLnkNode curr;
      switch(nestedType){
        case LISTDEQUE:
          curr=seq.head;
          rootPreAlloc=0;
          break;
        case SUBLIST:
          curr=parents[parents.length-1].head;
          rootPreAlloc=parentOffsets[parentOffsets.length-1];
          break;
        default:
          throw new Error("Unknown nested type "+nestedType);
      }
      int offset=0;
      for(int bound=offset+rootPreAlloc+parentPreAlloc;offset<bound;++offset,curr=curr.next){
         BooleanInputTestArgType.ARRAY_TYPE.verifyVal(expectedVal,curr.val);
      }
      return new DblLnkSeqVerificationItr(offset,curr,this);
    }
    SequenceVerificationItr verifyPreAlloc(){
      int rootPreAlloc;
      BooleanDblLnkNode curr;
      switch(nestedType){
        case LISTDEQUE:
          curr=seq.head;
          rootPreAlloc=0;
          break;
        case SUBLIST:
          curr=parents[parents.length-1].head;
          rootPreAlloc=parentOffsets[parentOffsets.length-1];
          break;
        default:
          throw new Error("Unknown nested type "+nestedType);
      }
      int offset=0;
      for(int bound=offset+rootPreAlloc+parentPreAlloc,v=Integer.MIN_VALUE;offset<bound;++offset,++v,curr=curr.next){
        BooleanInputTestArgType.ARRAY_TYPE.verifyVal(v,curr.val);
      }
      return new DblLnkSeqVerificationItr(offset,curr,this);
    }
    void illegalAdd(PreModScenario preModScenario){
      switch(preModScenario)
      {
        case ModSeq:
          BooleanInputTestArgType.ARRAY_TYPE.callCollectionAdd(seq,0);
          verifyAddition();
          break;
        case ModParent:
          int index;
          BooleanInputTestArgType.ARRAY_TYPE.callCollectionAdd(parents[index=parents.length-2],0);
          ++expectedParentSizes[index];
          ++expectedParentModCounts[index];
          ++expectedParentSizes[++index];
          ++expectedParentModCounts[index];
          break;
        case ModRoot:
          BooleanInputTestArgType.ARRAY_TYPE.callCollectionAdd(parents[index=parents.length-1],0);
          ++expectedParentSizes[index];
          ++expectedParentModCounts[index];
        case NoMod:
          break;
        default:
          throw new Error("Unknown preModScenario "+preModScenario);
      }
    }
    void verifyAddition(){
      ++expectedSeqSize;
      ++expectedSeqModCount;
      for(int i=0,bound=expectedParentModCounts.length;i<bound;++i){
        ++expectedParentSizes[i];
        ++expectedParentModCounts[i];
      }
    }
    public String toString(){
      var builder=new StringBuilder("BooleanDblLnkSeq").append(checkedType.checked?"Checked":"Unchecked");
      switch(nestedType){
        case LISTDEQUE:
          builder.append("List{").append(expectedSeqSize);
          break;
        case SUBLIST:
          builder.append("SubList{").append(expectedSeqSize).append(',').append(Arrays.toString(parentOffsets)).append(',').append(Arrays.toString(expectedParentSizes));
          break;
        default:
          throw new Error("Unknown nestedType "+nestedType);
      }
      return builder.append('}').toString();
    }
    private void verifyListDeque(){
      if(checkedType.checked){
        Assertions.assertEquals(expectedSeqModCount,((BooleanDblLnkSeq.CheckedList)seq).modCount);
      }
      if(expectedSeqSize==0){
        Assertions.assertNull(seq.head);
        Assertions.assertNull(seq.tail);
      }else{
        var head=seq.head;
        var tail=seq.tail;
        Assertions.assertNull(head.prev);
        Assertions.assertNull(tail.next);
        var curr=head;
        for(int count=expectedSeqSize;--count>=1;){
          var next=curr.next;
          Assertions.assertSame(next.prev,curr);
          curr=next;
        }
        Assertions.assertSame(curr,tail);
      }
    }
    private void verifyCheckedSubList(){
      BooleanDblLnkSeq currList,currParent;
      int currSize;
      Assertions.assertEquals(currSize=this.expectedSeqSize,(currList=this.seq).size);
      Assertions.assertEquals(expectedSeqModCount,FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.modCount(currList));
      BooleanDblLnkSeq[] parents;
      var root=(parents=this.parents)[parents.length-1];
      int parentSize;
      BooleanDblLnkNode currHead;
      for(int parentIndex=0,parentBound=parents.length;;){
        parentSize=expectedParentSizes[parentIndex];
        currParent=parents[parentIndex];
        if(parentIndex==parentBound-1){
          Assertions.assertNull(FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.parent(currList));
          Assertions.assertEquals(expectedParentModCounts[parentIndex],FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.modCount(currParent));
        }else{
          Assertions.assertSame(currParent,FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.parent(currList));
          Assertions.assertEquals(expectedParentModCounts[parentIndex],FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.modCount(currParent));
        }
        Assertions.assertSame(root,FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.root(currList));
        int preAlloc=parentOffsets[parentIndex];
        Assertions.assertEquals(preAlloc,FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.parentOffset(currList));
        if(currSize==0){
          Assertions.assertNull(currList.head);
          Assertions.assertNull(currList.tail);
        }else{
          currHead=currList.head;
          Assertions.assertNotNull(currHead);
          var currTail=currHead;
          for(int i=currSize;--i>0;){
            Assertions.assertSame(currTail,(currTail=currTail.next).prev);
          }
          Assertions.assertSame(currTail,currList.tail);
          for(;;){
            Assertions.assertEquals(parentSize,currParent.size);
            int postAlloc=parentSize-(currSize+preAlloc);
            for(int i=0;i<postAlloc;++i){
              Assertions.assertSame(currTail,(currTail=currTail.next).prev);
            }
            Assertions.assertSame(currTail,currParent.tail);
            for(int i=0;i<preAlloc;++i){
              Assertions.assertSame(currHead,(currHead=currHead.prev).next);
            }
            Assertions.assertSame(currHead,currParent.head);
            if(parentIndex==parentBound-1){
              Assertions.assertNull(currHead.prev);
              Assertions.assertNull(currTail.next);
              return;
            }else{
              Assertions.assertEquals(preAlloc=parentOffsets[++parentIndex],FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.parentOffset(currList=currParent));
              if(parentIndex==parentBound-1){
                Assertions.assertNull(FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.parent(currParent));
                currParent=parents[parentIndex];
                Assertions.assertEquals(expectedParentModCounts[parentIndex],FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.modCount(currParent));
              }else{
                Assertions.assertSame(root,FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.root(currList));
                Assertions.assertSame(currParent=parents[parentIndex],FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.parent(currList));
                Assertions.assertEquals(expectedParentModCounts[parentIndex],FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.modCount(currParent));
              }
            }
            currSize=parentSize;
            parentSize=expectedParentSizes[parentIndex];
          }
        }
        Assertions.assertEquals(currSize=parentSize,(currList=currParent).size);
        if(++parentIndex==parentBound){
          break;
        }
      }
    }
    private void verifyUncheckedSubList(){
      BooleanDblLnkSeq currList,currParent;
      int currSize;
      Assertions.assertEquals(currSize=this.expectedSeqSize,(currList=this.seq).size);
      BooleanDblLnkSeq[] parents;
      var root=(parents=this.parents)[parents.length-1];
      int parentSize;
      BooleanDblLnkNode currHead;
      for(int parentIndex=0,parentBound=parents.length;;){
        parentSize=expectedParentSizes[parentIndex];
        currParent=parents[parentIndex];
        if(parentIndex==parentBound-1){
          Assertions.assertNull(FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedSubList.parent(currList));
        }else{
          Assertions.assertSame(currParent,FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedSubList.parent(currList));
        }
        Assertions.assertSame(root,FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedSubList.root(currList));
        int preAlloc=parentOffsets[parentIndex];
        Assertions.assertEquals(preAlloc,FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedSubList.parentOffset(currList));
        if(currSize==0){
          Assertions.assertNull(currList.head);
          Assertions.assertNull(currList.tail);
        }else{
          currHead=currList.head;
          Assertions.assertNotNull(currHead);
          var currTail=currHead;
          for(int i=currSize;--i>0;){
            Assertions.assertSame(currTail,(currTail=currTail.next).prev);
          }
          Assertions.assertSame(currTail,currList.tail);
          for(;;){
            Assertions.assertEquals(parentSize,currParent.size);
            int postAlloc=parentSize-(currSize+preAlloc);
            for(int i=0;i<postAlloc;++i){
              Assertions.assertSame(currTail,(currTail=currTail.next).prev);
            }
            Assertions.assertSame(currTail,currParent.tail);
            for(int i=0;i<preAlloc;++i){
              Assertions.assertSame(currHead,(currHead=currHead.prev).next);
            }
            Assertions.assertSame(currHead,currParent.head);
            if(parentIndex==parentBound-1){
              Assertions.assertNull(currHead.prev);
              Assertions.assertNull(currTail.next);
              return;
            }else{
              Assertions.assertEquals(preAlloc=parentOffsets[++parentIndex],FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedSubList.parentOffset(currList=currParent));
              if(parentIndex==parentBound-1){
                Assertions.assertNull(FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedSubList.parent(currParent));
                currParent=parents[parentIndex];
              }else{
                Assertions.assertSame(root,FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedSubList.root(currList));
                Assertions.assertSame(currParent=parents[parentIndex],FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedSubList.parent(currList));
              }
            }
            currSize=parentSize;
            parentSize=expectedParentSizes[parentIndex];
          }
        }
        Assertions.assertEquals(currSize=parentSize,(currList=currParent).size);
        if(++parentIndex==parentBound){
          break;
        }
      }
    }
    void verifyStructuralIntegrity(){
      Assertions.assertEquals(expectedSeqSize,seq.size);
      switch(nestedType){
        case LISTDEQUE:
          verifyListDeque();
          break;
        case SUBLIST:
          if(checkedType.checked){
            verifyCheckedSubList();
          }else{
            verifyUncheckedSubList();
          }
          break;
        default:
          throw new Error("Unknown nestedType "+nestedType);
      }
    }
    void verifyFunctionalModification(){
      ++expectedSeqModCount;
      for(int i=0,bound=expectedParentModCounts.length;i<bound;++i){
        ++expectedParentModCounts[i];
      }
    }
    void verifyBatchRemove(int numRemoved){
      expectedSeqSize-=numRemoved;
      for(int i=0,bound=parents.length;i<bound;++i){
        expectedParentSizes[i]-=numRemoved;
      }
    }
    void writeObject(ObjectOutputStream oos) throws IOException{
      switch(nestedType){
        case LISTDEQUE:
          if(checkedType.checked){
            FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.writeObject(seq,oos);
          }else{
            FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedList.writeObject(seq,oos);
          }
          break;
        case SUBLIST:
          if(checkedType.checked){
            FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.writeObject(seq,oos);
          }else{
            FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedSubList.writeObject(seq,oos);
          }
          break;
        default:
          throw new Error("unknown nested type "+nestedType);
      }
    }
    void verifyRemoval(){
      --expectedSeqSize;
      ++expectedSeqModCount;
      for(int i=0,bound=expectedParentModCounts.length;i<bound;++i){
        --expectedParentSizes[i];
        ++expectedParentModCounts[i];
      }
    }
    private static class DblLnkSeqVerificationItr extends SequenceVerificationItr{
      BooleanDblLnkNode curr;
      int index;
      final SeqMonitor seqMonitor;
      private DblLnkSeqVerificationItr(int index,BooleanDblLnkNode curr,SeqMonitor seqMonitor){
        this.index=index;
        this.seqMonitor=seqMonitor;
        this.curr=curr;
      }
      SequenceVerificationItr verifyNaturalAscending(int v,BooleanInputTestArgType inputArgType,int length){
        return verifyAscending(v,inputArgType,length);
      }
      @Override SequenceVerificationItr verifyPostAlloc(int expectedVal){
        for(int i=0,bound=seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc;i<bound;++i){
          verifyIndexAndIterate(BooleanInputTestArgType.ARRAY_TYPE,expectedVal);
        }
        Assertions.assertNull(curr);
        Assertions.assertEquals(seqMonitor.expectedParentSizes.length==0?seqMonitor.expectedSeqSize:seqMonitor.expectedParentSizes[seqMonitor.parents.length-1],index);
        return this;
      }
      @Override void verifyLiteralIndexAndIterate(boolean val){
        Assertions.assertEquals(val,curr.val);
        curr=curr.next;
        ++index;
      }
      private BooleanDblLnkNode getReverseNode(){
        BooleanDblLnkNode curr;
        return (curr=this.curr)==null?seqMonitor.parents.length==0?seqMonitor.seq.tail:seqMonitor.parents[seqMonitor.parents.length-1].tail:curr.prev;
      }
      @Override void reverseAndVerifyIndex(BooleanInputTestArgType inputArgType,int val){
        BooleanDblLnkNode curr;
        inputArgType.verifyVal(val,(curr=getReverseNode()).val);
        this.curr=curr;
        --index;
      }
      @Override void verifyIndexAndIterate(BooleanInputTestArgType inputArgType,int val){ 
        inputArgType.verifyVal(val,curr.val);
        curr=curr.next;
        ++index;
      }
      private BooleanDblLnkNode getNode(int i){
        if(i<0){
          if(this.curr==null){
            return BooleanDblLnkNode.iterateDescending(seqMonitor.parents.length==0?seqMonitor.seq.tail:seqMonitor.parents[seqMonitor.parents.length-1].tail,(-i)-1);
          }
          return BooleanDblLnkNode.uncheckedIterateDescending(this.curr,-i);
        }
        return BooleanDblLnkNode.iterateAscending(curr,i);
      }
      @Override SequenceVerificationItr getOffset(int i){
        return new DblLnkSeqVerificationItr(i+index,getNode(i),seqMonitor);
      }
      @Override SequenceVerificationItr skip(int i){
        this.curr=getNode(i);
        this.index+=i;
        return this;
      }
      @Override public boolean equals(Object val){
        return val==this || (val instanceof DblLnkSeqVerificationItr && ((DblLnkSeqVerificationItr)val).curr==this.curr);
      }
      @Override SequenceVerificationItr verifyRootPostAlloc(){
        for(int i=0,rootPostAlloc=seqMonitor.rootPostAlloc,v=Integer.MAX_VALUE-(rootPostAlloc-1);i<rootPostAlloc;++i,++v){
          verifyIndexAndIterate(BooleanInputTestArgType.ARRAY_TYPE,v);
        }
        return this;
      }
      @Override SequenceVerificationItr verifyParentPostAlloc(){
        for(int i=0,rootPostAlloc=seqMonitor.rootPostAlloc,v=Integer.MAX_VALUE-(rootPostAlloc+seqMonitor.parentPostAlloc-1);i<seqMonitor.parentPostAlloc;++i,++v){
          verifyIndexAndIterate(BooleanInputTestArgType.ARRAY_TYPE,v);
        }
        return this;
      }
    }
    class UncheckedSubAscendingItrMonitor extends UncheckedAscendingItrMonitor{
      UncheckedSubAscendingItrMonitor(){
        super();
      }
      BooleanDblLnkNode getNewCurr(){
        if(expectedCurr!=null && expectedCurr!=seq.tail){
          return expectedCurr.next;
        }
        return null;
      }
      void verifyIteratorState(){
        Assertions.assertSame(FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedSubList.AscendingItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedSubList.AscendingItr.parent(itr),seq);
      }
    }
    class UncheckedAscendingItrMonitor extends AbstractBooleanSeqMonitor.AbstractItrMonitor{
      BooleanDblLnkNode expectedCurr;
      UncheckedAscendingItrMonitor(){
        this(ItrType.Itr,seq.iterator(),seq.head);
      }
      private UncheckedAscendingItrMonitor(ItrType itrType,OmniIterator.OfBoolean itr,BooleanDblLnkNode expectedCurr){
        super(itrType,itr,expectedSeqModCount);
        this.expectedCurr=expectedCurr;
      }
      void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
        if(functionCallType==FunctionCallType.Boxed){
          itr.forEachRemaining((Consumer)action);
        }else
        {
          itr.forEachRemaining((BooleanConsumer)action);
        }
        this.expectedCurr=null;
      }
      SeqMonitor getSeqMonitor(){
        return SeqMonitor.this;
      }
      BooleanDblLnkNode getNewCurr(){
        return expectedCurr!=null?expectedCurr.next:null;
      }
      void verifyNext(int expectedVal,BooleanOutputTestArgType outputType){
        var newCurr=getNewCurr();
        outputType.verifyItrNext(itr,expectedVal);
        expectedCurr=newCurr;
      }
      void iterateForward(){
        var newCurr=getNewCurr();
        itr.nextBoolean();
        expectedCurr=newCurr;
      }
      void remove(){
        itr.remove();
        verifyRemoval();
      }
      void verifyIteratorState(){
        Assertions.assertSame(FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedList.AscendingItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedList.AscendingItr.parent(itr),seq);
      }
    }
    class UncheckedDescendingItrMonitor extends UncheckedAscendingItrMonitor{
      UncheckedDescendingItrMonitor(){
        super(ItrType.DescendingItr,((OmniDeque.OfBoolean)seq).descendingIterator(),seq.tail);
      }
      BooleanDblLnkNode getNewCurr(){
        return expectedCurr!=null?expectedCurr.prev:null;
      }
      void verifyIteratorState(){
        Assertions.assertSame(FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedList.DescendingItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedList.DescendingItr.parent(itr),seq);
      }
    }
    class CheckedDescendingItrMonitor extends UncheckedDescendingItrMonitor{
      BooleanDblLnkNode expectedLastRet;
      int expectedCurrIndex;
      CheckedDescendingItrMonitor(){
        super();
        this.expectedCurrIndex=expectedSeqSize;
      }
      void verifyNext(int expectedVal,BooleanOutputTestArgType outputType){
        var newCurr=getNewCurr();
        outputType.verifyItrNext(itr,expectedVal);
        expectedLastRet=expectedCurr;
        expectedCurr=newCurr;
        --expectedCurrIndex;
      }
      void iterateForward(){
        var newCurr=getNewCurr();
        itr.nextBoolean();
        expectedLastRet=expectedCurr;
        expectedCurr=newCurr;
        --expectedCurrIndex;
      }
      void remove(){
        super.remove();
        this.expectedLastRet=null;
        ++expectedItrModCount;
      }
      void verifyIteratorState(){
        Assertions.assertEquals(FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.DescendingItr.modCount(itr),expectedItrModCount);
        Assertions.assertEquals(FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.DescendingItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertSame(FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.DescendingItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.DescendingItr.lastRet(itr),expectedLastRet);
        Assertions.assertSame(FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.DescendingItr.parent(itr),seq);
      }
      void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
        if(functionCallType==FunctionCallType.Boxed){
          itr.forEachRemaining((Consumer)action);
        }else
        {
          itr.forEachRemaining((BooleanConsumer)action);
        }
        if(expectedCurrIndex>0){
          this.expectedLastRet=seq.head;
          this.expectedCurrIndex=0;
          this.expectedCurr=null;
        }
      }
    }
    class UncheckedBidirectionalSubItrMonitor extends UncheckedBidirectionalItrMonitor{
      UncheckedBidirectionalSubItrMonitor(){
        super();
      }
      UncheckedBidirectionalSubItrMonitor(int index){
        super(index);
      }
      void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
        int parentSize=expectedSeqSize;
        BooleanDblLnkNode newLastRet=seq.tail;
        if(functionCallType==FunctionCallType.Boxed){
          itr.forEachRemaining((Consumer)action);
        }else
        {
          itr.forEachRemaining((BooleanConsumer)action);
        }
        if(expectedCurrIndex<parentSize){
          this.expectedLastRet=newLastRet;
          this.expectedCurrIndex=parentSize;
          this.expectedCurr=null;
        }
      }
      void verifyIteratorState(){
        Assertions.assertEquals(FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedSubList.BidirectionalItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertSame(FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedSubList.BidirectionalItr.parent(itr),seq);
        Assertions.assertSame(FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedSubList.BidirectionalItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedSubList.BidirectionalItr.lastRet(itr),expectedLastRet);
      }
    }
    class UncheckedBidirectionalItrMonitor extends AbstractBooleanSeqMonitor.AbstractItrMonitor{
      BooleanDblLnkNode expectedCurr;
      int expectedCurrIndex;
      BooleanDblLnkNode expectedLastRet;
      UncheckedBidirectionalItrMonitor(){
        super(ItrType.ListItr,seq.listIterator(),expectedSeqModCount);
        this.expectedCurr=seq.head;
        this.expectedCurrIndex=0;
      }
      UncheckedBidirectionalItrMonitor(ItrType itrType,OmniIterator.OfBoolean itr,int currIndex){
        super(itrType,itr,expectedSeqModCount);
        this.expectedCurrIndex=currIndex;
        int parentSize=expectedSeqSize;
        if((parentSize-=currIndex)<=currIndex){
          switch(parentSize){
          case 0:
            this.expectedCurr=null;
            break;
          case 1:
            this.expectedCurr=seq.tail;
            break;
          default:
            this.expectedCurr=BooleanDblLnkNode.uncheckedIterateDescending(seq.tail,parentSize-1);
          }
        }else{
          this.expectedCurr=BooleanDblLnkNode.iterateAscending(seq.head,currIndex);
        }
      }
      UncheckedBidirectionalItrMonitor(int index){
        this(ItrType.ListItr,seq.listIterator(index),index);
      }
      BooleanDblLnkNode getNewNextNode(){
        return expectedCurr==null?null:expectedCurr.next;
      }
      BooleanDblLnkNode getNewPrevNode(){
        return expectedCurr==null?seq.tail:expectedCurr.prev;
      }
      void iterateReverse(){
        BooleanDblLnkNode newLastRet=getNewPrevNode();
        ((OmniListIterator.OfBoolean)itr).previousBoolean();
        this.expectedLastRet=newLastRet;
        this.expectedCurr=newLastRet;
        --this.expectedCurrIndex;
      }
      void verifyPrevious(int expectedVal,BooleanOutputTestArgType outputType){
        BooleanDblLnkNode newLastRet=getNewPrevNode();
        outputType.verifyItrPrevious(((OmniListIterator.OfBoolean)itr),expectedVal);
         this.expectedLastRet=newLastRet;
        this.expectedCurr=newLastRet;
        --this.expectedCurrIndex;
      }
      void add(int v,BooleanInputTestArgType inputArgType){
        inputArgType.callListItrAdd(((OmniListIterator.OfBoolean)itr),v);
        ++this.expectedCurrIndex;
        this.expectedLastRet=null;
        verifyAddition();
      }
      void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
        if(functionCallType==FunctionCallType.Boxed){
          itr.forEachRemaining((Consumer)action);
        }else
        {
          itr.forEachRemaining((BooleanConsumer)action);
        }
        if(expectedCurr!=null){
          this.expectedLastRet=seq.tail;
          this.expectedCurrIndex=expectedSeqSize;
          this.expectedCurr=null;
        }
      }
      SeqMonitor getSeqMonitor(){
        return SeqMonitor.this;
      }
      void verifyNext(int expectedVal,BooleanOutputTestArgType outputType){
        var newCurr=getNewNextNode();
        outputType.verifyItrNext(itr,expectedVal);
        this.expectedLastRet=expectedCurr;
        this.expectedCurr=newCurr;
        ++this.expectedCurrIndex;
      }
      void iterateForward(){
        var newCurr=getNewNextNode();
        itr.nextBoolean();
        this.expectedLastRet=expectedCurr;
        this.expectedCurr=newCurr;
        ++this.expectedCurrIndex;
      }
      void remove(){
        int newCurrIndex=this.expectedCurrIndex;
        var newCurr=expectedCurr;
        if(expectedLastRet!=null){
          if(expectedLastRet.next==newCurr){
            --newCurrIndex;
          }else{
            newCurr=expectedLastRet.next;
          }
        }
        itr.remove();
        verifyRemoval();
        this.expectedCurrIndex=newCurrIndex;
        this.expectedLastRet=null;
        this.expectedCurr=newCurr;
      }
      void verifyIteratorState(){
        Assertions.assertEquals(FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedList.BidirectionalItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertSame(FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedList.BidirectionalItr.parent(itr),seq);
        Assertions.assertSame(FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedList.BidirectionalItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedList.BidirectionalItr.lastRet(itr),expectedLastRet);
      }
    }
    class CheckedBidirectionalSubItrMonitor extends CheckedSubAscendingItrMonitor{
      CheckedBidirectionalSubItrMonitor(){
        super();
      }
      CheckedBidirectionalSubItrMonitor(int index){
        super(index);
      }
      void verifyIteratorState(){
        Assertions.assertEquals(FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.BidirectionalItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertEquals(FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.BidirectionalItr.modCount(itr),expectedItrModCount);
        Assertions.assertSame(FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.BidirectionalItr.parent(itr),seq);
        Assertions.assertSame(FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.BidirectionalItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.BidirectionalItr.lastRet(itr),expectedLastRet);
      }
    }
    class CheckedSubAscendingItrMonitor extends CheckedBidirectionalItrMonitor{
      CheckedSubAscendingItrMonitor(){
        super();
      }
      CheckedSubAscendingItrMonitor(int index){
        super(index);
      }
      BooleanDblLnkNode getNewNextNode(){
        return expectedCurr!=null &&expectedCurrIndex<expectedSeqSize?expectedCurr.next:null;
      }
      void verifyIteratorState(){
        Assertions.assertEquals(FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.AscendingItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertEquals(FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.AscendingItr.modCount(itr),expectedItrModCount);
        Assertions.assertSame(FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.AscendingItr.parent(itr),seq);
        Assertions.assertSame(FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.AscendingItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.AscendingItr.lastRet(itr),expectedLastRet);
      }
    }
    class CheckedBidirectionalItrMonitor extends CheckedAscendingItrMonitor{
      CheckedBidirectionalItrMonitor(int index){
        super(ItrType.ListItr,seq.listIterator(index),index);
      }
      CheckedBidirectionalItrMonitor(){
        super(ItrType.ListItr,seq.listIterator(),0);
      }
      void add(int v,BooleanInputTestArgType inputArgType){
        super.add(v,inputArgType);
        ++expectedItrModCount;
      }
      BooleanDblLnkNode getNewPrevNode(){
        return expectedCurrIndex!=0?expectedCurr==null?seq.tail:expectedCurr.prev:null;
      }
      void verifyIteratorState(){
        Assertions.assertEquals(FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.BidirectionalItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertEquals(FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.BidirectionalItr.modCount(itr),expectedItrModCount);
        Assertions.assertSame(FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.BidirectionalItr.parent(itr),seq);
        Assertions.assertSame(FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.BidirectionalItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.BidirectionalItr.lastRet(itr),expectedLastRet);
      }
    }
    class CheckedAscendingItrMonitor extends UncheckedBidirectionalItrMonitor{
      CheckedAscendingItrMonitor(){
        super(ItrType.Itr,seq.iterator(),0);
      }
      CheckedAscendingItrMonitor(ItrType itrType,OmniIterator.OfBoolean itr,int index){
        super(itrType,itr,index);
      }
      void verifyIteratorState(){
        Assertions.assertEquals(FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.AscendingItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertEquals(FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.AscendingItr.modCount(itr),expectedItrModCount);
        Assertions.assertSame(FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.AscendingItr.parent(itr),seq);
        Assertions.assertSame(FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.AscendingItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.AscendingItr.lastRet(itr),expectedLastRet);
      }
      void remove(){
        super.remove();
        ++expectedItrModCount;
      }
      void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
        int parentSize=expectedSeqSize;
        int currIndex=this.expectedCurrIndex;
        if(functionCallType==FunctionCallType.Boxed){
          itr.forEachRemaining((Consumer)action);
        }else
        {
          itr.forEachRemaining((BooleanConsumer)action);
        }
        if(currIndex<parentSize){
          this.expectedLastRet=seq.tail;
          this.expectedCurrIndex=parentSize;
          this.expectedCurr=null;
        }
      }
    }
  }
}
