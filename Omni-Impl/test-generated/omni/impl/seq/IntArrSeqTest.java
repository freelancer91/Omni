package omni.impl.seq;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import omni.impl.IntInputTestArgType;
import omni.impl.IntOutputTestArgType;
import java.util.NoSuchElementException;
import omni.util.OmniArray;
import omni.impl.FunctionCallType;
import omni.impl.QueryCastType;
import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import omni.impl.seq.AbstractIntSeqMonitor.MonitoredFunctionGen;
import omni.impl.seq.AbstractIntSeqMonitor.MonitoredComparatorGen;
import omni.impl.seq.AbstractIntSeqMonitor.MonitoredRemoveIfPredicateGen;
import omni.impl.seq.AbstractIntSeqMonitor.QueryTester;
import java.nio.file.Files;
import omni.impl.CheckedType;
import org.junit.jupiter.api.Tag;
import omni.impl.seq.AbstractIntSeqMonitor.SequenceVerificationItr;
import omni.api.OmniCollection;
import omni.api.OmniList;
import java.util.ArrayList;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import omni.util.TestExecutorService;
@SuppressWarnings({"rawtypes","unchecked"})
@Tag("ArrSeqTest")
public class IntArrSeqTest{
  private static TestExecutorService EXECUTORSERVICE;
  @org.junit.jupiter.api.BeforeAll
  public static void init(){
    EXECUTORSERVICE=new TestExecutorService(0);
  }
  @org.junit.jupiter.api.AfterEach
  public void verifyAllExecuted(){
    int numTestsRemaining;
    if((numTestsRemaining=EXECUTORSERVICE.getNumRemainingTasks())!=0)
    {
      System.err.println("Warning: there were "+numTestsRemaining+" tests that were not completed");
    }
    EXECUTORSERVICE.reset();
  }
  @org.junit.jupiter.api.AfterAll
  public static void cleanUp(){
    EXECUTORSERVICE=null;
  }
    private static final int MAX_TOSTRING_LENGTH=11;
  @Tag("MASSIVEtoString")
  @org.junit.jupiter.api.Test
  public void testMASSIVEtoString_void(){
    int seqLength=(OmniArray.MAX_ARR_SIZE/(MAX_TOSTRING_LENGTH+2))+1;
    int[] arr=new int[seqLength];
    int numBatches=100;
    int batchSpan=seqLength/numBatches;
    int batchBound=numBatches-1;
    for(int i=0;i<seqLength;++i){
      arr[i]=TypeConversionUtil.convertToint(1);
    }
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        var seqMonitor=new SeqMonitor(nestedType,checkedType,seqLength,arr);
        String string=seqMonitor.seq.toString();
        Assertions.assertEquals('[',string.charAt(0));
        Assertions.assertEquals(']',string.charAt(string.length()-1));
        seqMonitor.verifyStructuralIntegrity();
        int nextWayPointIndex=0;
        for(int batchIndex=0;batchIndex<batchBound;++batchIndex){
          var verifyItr=new SeqMonitor.ArrSeqSequenceVerificationItr(seqMonitor,nextWayPointIndex,arr);
          final int finalWayPointBound=nextWayPointIndex+batchSpan;
          final int finalWayPointIndex=nextWayPointIndex;
          EXECUTORSERVICE.submitTest(()->AbstractIntSeqMonitor.verifyLargeStr(string,finalWayPointIndex,finalWayPointBound,verifyItr));
          nextWayPointIndex=finalWayPointBound;
        }
        var verifyItr=new SeqMonitor.ArrSeqSequenceVerificationItr(seqMonitor,nextWayPointIndex,arr);
        final int finalWayPointIndex=nextWayPointIndex;
        EXECUTORSERVICE.submitTest(()->AbstractIntSeqMonitor.verifyLargeStr(string,finalWayPointIndex,seqLength,verifyItr));
        EXECUTORSERVICE.completeAllTests("IntArrSeqTest.testMASSIVEtoString_void checkedType="+checkedType+"; nestedType="+nestedType);
        verifyItr.verifyPostAlloc(1);
      }
    }
  }
  @org.junit.jupiter.api.Test
  public void testConstructor_int(){
    for(var nestedType:NestedType.values()){
      if(nestedType!=NestedType.SUBLIST){
        for(var checkedType:CheckedType.values()){
          for(int tmpInitialCapacity=0;tmpInitialCapacity<=15;tmpInitialCapacity+=5){
            final int initialCapacity=tmpInitialCapacity;
            EXECUTORSERVICE.submitTest(()->{
              IntArrSeq seq;
              if(checkedType.checked){
                Assertions.assertEquals(0,nestedType==NestedType.LIST?FieldAndMethodAccessor.IntArrSeq.CheckedList.modCount(seq=new IntArrSeq.CheckedList(initialCapacity)):FieldAndMethodAccessor.IntArrSeq.CheckedStack.modCount(seq=new IntArrSeq.CheckedStack(initialCapacity)));
              }else{
                seq=nestedType==NestedType.LIST?new IntArrSeq.UncheckedList(initialCapacity):new IntArrSeq.UncheckedStack(initialCapacity);
              }
              Assertions.assertEquals(0,seq.size);
              switch(initialCapacity){
                case 0:
                  Assertions.assertNull(seq.arr);
                  break;
                case OmniArray.DEFAULT_ARR_SEQ_CAP:
                  Assertions.assertSame(OmniArray.OfInt.DEFAULT_ARR,seq.arr);
                  break;
                default:
                  Assertions.assertEquals(initialCapacity,seq.arr.length);
              }
            });
          }
        }
      }
    }
    EXECUTORSERVICE.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testListItradd_val(){
    for(var checkedType:CheckedType.values()){
      for(var preModScenario:PreModScenario.values()){
        if(checkedType.checked || preModScenario.expectedException==null){
          for(int seqSize:AbstractIntSeqMonitor.FIB_SEQ){
            for(var seqLocation:SequenceLocation.values()){
              if(seqLocation.expectedException==null){
                for(var inputArgType:IntInputTestArgType.values()){
                  if(preModScenario.appliesToRootItr){
                    for(int tmpInitialCapacity=0;tmpInitialCapacity<=15;tmpInitialCapacity+=5){
                      final int initialCapacity=tmpInitialCapacity;
                      EXECUTORSERVICE.submitTest(()->testListItradd_valHelper(new SeqMonitor(NestedType.LIST,checkedType,initialCapacity),preModScenario,seqSize,seqLocation,inputArgType));
                    }
                  }
                  for(int tmpRootPreAlloc=0;tmpRootPreAlloc<=5;tmpRootPreAlloc+=5){
                    final int rootPreAlloc=tmpRootPreAlloc;
                    for(int tmpParentPreAlloc=0;tmpParentPreAlloc<=5;tmpParentPreAlloc+=5){
                      final int parentPreAlloc=tmpParentPreAlloc;
                      for(int tmpParentPostAlloc=0;tmpParentPostAlloc<=5;tmpParentPostAlloc+=5){
                        final int parentPostAlloc=tmpParentPostAlloc;
                        for(int tmpRootPostAlloc=0;tmpRootPostAlloc<=5;tmpRootPostAlloc+=5){
                          final int rootPostAlloc=tmpRootPostAlloc;
                          EXECUTORSERVICE.submitTest(()->testListItradd_valHelper(new SeqMonitor(NestedType.SUBLIST,checkedType,OmniArray.DEFAULT_ARR_SEQ_CAP,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),preModScenario,seqSize,seqLocation,inputArgType));
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
    EXECUTORSERVICE.completeAllTests();
  }
  private static void testListItradd_valHelper(SeqMonitor seqMonitor,PreModScenario preModScenario,int numToAdd,SequenceLocation seqLocation,IntInputTestArgType inputArgType){
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
  public void testListItrset_val(){
    for(var checkedType:CheckedType.values()){
      for(var listItrSetScenario:ListItrSetScenario.values()){
        if(checkedType.checked || listItrSetScenario.expectedException==null){
          for(var inputArgType:IntInputTestArgType.values()){
            if(listItrSetScenario.preModScenario.appliesToRootItr){
              EXECUTORSERVICE.submitTest(()->testListItrset_valHelper(new SeqMonitor(NestedType.LIST,checkedType),listItrSetScenario,inputArgType));
            }
            EXECUTORSERVICE.submitTest(()->testListItrset_valHelper(new SeqMonitor(NestedType.SUBLIST,checkedType),listItrSetScenario,inputArgType));
          }
        }
      }
    }
    EXECUTORSERVICE.completeAllTests();
  }
  private static void testListItrset_valHelper(SeqMonitor seqMonitor,ListItrSetScenario listItrSetScenario,IntInputTestArgType inputArgType){
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
          for(int numToAdd:AbstractIntSeqMonitor.FIB_SEQ){
            if(numToAdd!=0 || itrScenario.validWithEmptySeq){
              for(var itrType:ItrType.values()){
                if(itrType!=ItrType.DescendingItr){
                  for(var nestedType:NestedType.values()){
                    if((nestedType!=NestedType.STACK || itrType!=ItrType.ListItr) && (itrScenario.preModScenario.appliesToRootItr || !nestedType.rootType)){
                      for(var outputType:IntOutputTestArgType.values()){
                        EXECUTORSERVICE.submitTest(()->{
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
                                itrMonitor.verifyNext(seqMonitor.nestedType==NestedType.STACK?numToAdd-i-1:i,outputType);
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
    }
    EXECUTORSERVICE.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testListItrprevious_void(){
    for(var checkedType:CheckedType.values()){
      for(var itrScenario:IterationScenario.values()){
        if(checkedType.checked || itrScenario==IterationScenario.NoMod){
          for(int seqSize:AbstractIntSeqMonitor.FIB_SEQ){
            if(seqSize!=0 || itrScenario.validWithEmptySeq){
              for(var outputType:IntOutputTestArgType.values()){
                if(itrScenario.preModScenario.appliesToRootItr){
                  EXECUTORSERVICE.submitTest(()->testListItrprevious_voidHelper(new SeqMonitor(NestedType.LIST,checkedType),itrScenario,seqSize,outputType));
                }
                EXECUTORSERVICE.submitTest(()->testListItrprevious_voidHelper(new SeqMonitor(NestedType.SUBLIST,checkedType),itrScenario,seqSize,outputType));
              }
            }
          }
        }
      }
    }
    EXECUTORSERVICE.completeAllTests();
  }
  private static void testListItrprevious_voidHelper(SeqMonitor seqMonitor,IterationScenario itrScenario,int numToAdd,IntOutputTestArgType outputType){
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
  public void testItrremove_void(){
    for(var checkedType:CheckedType.values()){
      for(var removeScenario:ItrRemoveScenario.values()){
        if(checkedType.checked || removeScenario.expectedException==null){
          for(int tmpSeqSize=0;tmpSeqSize<=100;++tmpSeqSize){
            if(tmpSeqSize!=0 || removeScenario.validWithEmptySeq){
              final int numToAdd=tmpSeqSize;
              for(var preModScenario:PreModScenario.values()){
                if(checkedType.checked || preModScenario.expectedException==null){
                  for(var itrType:ItrType.values()){
                    if(itrType!=ItrType.DescendingItr && (itrType==ItrType.ListItr || removeScenario.validWithForwardItr)){
                      for(var nestedType:NestedType.values()){
                        if((itrType==ItrType.Itr || nestedType!=NestedType.STACK) && (!nestedType.rootType || preModScenario.appliesToRootItr)){
                          for(var seqLocation:SequenceLocation.values()){
                            if(seqLocation.expectedException==null && (seqLocation==SequenceLocation.BEGINNING || (numToAdd!=0 && itrType!=ItrType.Itr))){
                              EXECUTORSERVICE.submitTest(()->{
                                var seqMonitor=new SeqMonitor(nestedType,checkedType);
                                for(int i=0;i<numToAdd;++i){
                                  seqMonitor.add(i);
                                }
                                var itrMonitor=seqMonitor.getItrMonitor(seqLocation,itrType);
                                int numIterated;
                                boolean hasLastRet=false;
                                switch(seqLocation){
                                  case BEGINNING:
                                    if(seqMonitor.nestedType.forwardIteration){
                                      numIterated=0;
                                    }else{
                                      numIterated=numToAdd;
                                    }
                                    break;
                                  case NEARBEGINNING:
                                    numIterated=seqMonitor.expectedSeqSize/4;
                                    break;
                                  case MIDDLE:
                                    numIterated=seqMonitor.expectedSeqSize/2;
                                    break;
                                  case NEAREND:
                                    numIterated=(seqMonitor.expectedSeqSize/4)*3;
                                    break;
                                  case END:
                                    numIterated=seqMonitor.expectedSeqSize;
                                    break;
                                  default:
                                    throw new Error("Unknown seq location "+seqLocation);
                                }
                                switch(removeScenario){
                                  case PostNext:
                                    if(seqLocation==SequenceLocation.END && itrMonitor.hasPrevious()){
                                      hasLastRet=true;
                                      --numIterated;
                                      itrMonitor.iterateReverse();
                                    }
                                    if(itrMonitor.hasNext()){
                                      hasLastRet=true;
                                      if(seqMonitor.nestedType.forwardIteration){
                                        ++numIterated;
                                      }else{
                                        --numIterated;
                                      }
                                      itrMonitor.iterateForward();
                                    }
                                    break;
                                  case PostPrevious:
                                    if(seqLocation==SequenceLocation.BEGINNING && itrMonitor.hasNext()){
                                       hasLastRet=true;
                                       ++numIterated;
                                       itrMonitor.iterateForward();
                                    }
                                    if(itrMonitor.hasPrevious()){
                                      hasLastRet=true;
                                      --numIterated;
                                      itrMonitor.iterateReverse();
                                    }
                                    break;
                                  case PostAdd:
                                    itrMonitor.add(0);
                                    hasLastRet=false;
                                    break;
                                  case PostRemove:
                                    if(seqLocation==SequenceLocation.END && itrMonitor.hasPrevious()){
                                      --numIterated;
                                      itrMonitor.iterateReverse();
                                      hasLastRet=true;
                                    }else if(itrMonitor.hasNext()){
                                      if(!seqMonitor.nestedType.forwardIteration){
                                        --numIterated;
                                      }
                                      itrMonitor.iterateForward();
                                      hasLastRet=true;
                                    }
                                    itrMonitor.remove();
                                    hasLastRet=false;
                                  case PostInit:
                                    break;
                                  default:
                                     throw new Error("unknown remove scenario "+removeScenario);
                                }
                                if(removeScenario.expectedException==null && preModScenario.expectedException!=null && !hasLastRet){
                                  if(itrMonitor.hasNext()){
                                    ++numIterated;
                                    itrMonitor.iterateForward();
                                  }else{
                                    --numIterated;
                                    itrMonitor.iterateReverse();
                                  }
                                  hasLastRet=true;
                                }
                                seqMonitor.illegalAdd(preModScenario);
                                SequenceVerificationItr verifyItr;
                                if(removeScenario.expectedException==null){
                                  if(preModScenario.expectedException==null){
                                    if(!hasLastRet){
                                      if(itrMonitor.hasNext()){
                                        if(seqMonitor.nestedType.forwardIteration){
                                          ++numIterated;
                                        }else{
                                          --numIterated;
                                        }
                                        itrMonitor.iterateForward();
                                      }else{
                                        --numIterated;
                                        itrMonitor.iterateReverse();
                                      }
                                      hasLastRet=true;
                                    }
                                    itrMonitor.remove();
                                    itrMonitor.verifyIteratorState();
                                    seqMonitor.verifyStructuralIntegrity();
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
                                             for(int j=1;;++j){
                                              if(itrMonitor.hasPrevious()){
                                                itrMonitor.iterateReverse();
                                              }
                                              if((j&3)!=0){
                                                if(itrMonitor.hasPrevious()){
                                                  itrMonitor.iterateReverse();
                                                }
                                              }
                                              if(itrMonitor.hasNext()){
                                                itrMonitor.iterateForward();
                                              }else{
                                                if(!itrMonitor.hasPrevious()){
                                                  break;
                                                }
                                                itrMonitor.iterateReverse();
                                              }
                                              itrMonitor.remove();
                                              itrMonitor.verifyIteratorState();
                                              seqMonitor.verifyStructuralIntegrity();
                                            }
                                            break;
                                          case MIDDLE:
                                            for(int j=1;;++j){
                                              if(itrMonitor.hasPrevious()){
                                                itrMonitor.iterateReverse();
                                              }
                                              if((j&1)!=0){
                                                if(itrMonitor.hasPrevious()){
                                                  itrMonitor.iterateReverse();
                                                }
                                              }
                                              if(itrMonitor.hasNext()){
                                                itrMonitor.iterateForward();
                                              }else{
                                                if(!itrMonitor.hasPrevious()){
                                                  break;
                                                }
                                                itrMonitor.iterateReverse();
                                              }
                                              itrMonitor.remove();
                                              itrMonitor.verifyIteratorState();
                                              seqMonitor.verifyStructuralIntegrity();
                                            }
                                            break;
                                          case NEAREND:
                                            for(int j=1;;++j){
                                              if(itrMonitor.hasPrevious()){
                                                itrMonitor.iterateReverse();
                                              }
                                              if((j&3)==0){
                                                if(itrMonitor.hasPrevious()){
                                                  itrMonitor.iterateReverse();
                                                }
                                              }
                                              if(itrMonitor.hasNext()){
                                                itrMonitor.iterateForward();
                                              }else{
                                                if(!itrMonitor.hasPrevious()){
                                                  break;
                                                }
                                                itrMonitor.iterateReverse();
                                              }
                                              itrMonitor.remove();
                                              itrMonitor.verifyIteratorState();
                                              seqMonitor.verifyStructuralIntegrity();
                                            }
                                            break;
                                          case END:
                                            for(;;){
                                              if(!itrMonitor.hasPrevious()){
                                                break;
                                              }
                                              itrMonitor.iterateReverse();
                                              itrMonitor.iterateForward();
                                              itrMonitor.remove();
                                              itrMonitor.verifyIteratorState();
                                              seqMonitor.verifyStructuralIntegrity();
                                            }
                                            break;
                                          default:
                                            throw new Error("Unknown sequence location "+seqLocation);
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
                                            for(int j=1;;++j){
                                              if((j&3)==0 && itrMonitor.hasNext()){
                                                itrMonitor.iterateForward();
                                              }
                                              if(itrMonitor.hasPrevious()){
                                                itrMonitor.iterateReverse();
                                              }else{
                                                if(!itrMonitor.hasNext()){
                                                  break;
                                                }
                                                itrMonitor.iterateForward();
                                              }            
                                              itrMonitor.remove();
                                              itrMonitor.verifyIteratorState();
                                              seqMonitor.verifyStructuralIntegrity();
                                            }
                                            break;
                                          case MIDDLE:
                                            for(int j=1;;++j){
                                              if((j&1)!=0 && itrMonitor.hasNext()){
                                                itrMonitor.iterateForward();
                                              }
                                              if(itrMonitor.hasPrevious()){
                                                itrMonitor.iterateReverse();
                                              }else{
                                                if(!itrMonitor.hasNext()){
                                                  break;
                                                }
                                                itrMonitor.iterateForward();
                                              }            
                                              itrMonitor.remove();
                                              itrMonitor.verifyIteratorState();
                                              seqMonitor.verifyStructuralIntegrity();
                                            }
                                            break;
                                          case NEAREND:
                                            for(int j=1;;++j){
                                              if((j&3)!=0 && itrMonitor.hasNext()){
                                                itrMonitor.iterateForward();
                                              }
                                              if(itrMonitor.hasPrevious()){
                                                itrMonitor.iterateReverse();
                                              }else{
                                                if(!itrMonitor.hasNext()){
                                                  break;
                                                }
                                                itrMonitor.iterateForward();
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
                                            throw new Error("Unknown sequence location "+seqLocation);
                                        }
                                        break;
                                      default:
                                        throw new Error("unknown remove scenario "+removeScenario);
                                    }
                                    Assertions.assertFalse(itrMonitor.hasNext());
                                    Assertions.assertTrue(seqMonitor.isEmpty());
                                    verifyItr=seqMonitor.verifyPreAlloc();
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
                                      verifyItr.verifyAscending(numIterated).verifyIllegalAdd().verifyAscending(numIterated,numToAdd-(numIterated));
                                      break;
                                    case PostRemove:
                                      verifyItr.verifyAscending(numIterated).verifyAscending(numIterated+1,numToAdd-(numIterated+1));
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
        }
      }
    }
    EXECUTORSERVICE.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testListItrpreviousIndex_void_And_ListItrnextIndex_void(){
    for(var checkedType:CheckedType.values()){
      for(var nestedType:NestedType.values()){
        if(nestedType!=NestedType.STACK){
          EXECUTORSERVICE.submitTest(()->{
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
    }
    EXECUTORSERVICE.completeAllTests();
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
                  if(itrType!=ItrType.DescendingItr && (itrType==ItrType.Itr || nestedType.forwardIteration)){
                    for(int seqSize:AbstractIntSeqMonitor.FIB_SEQ){
                      EXECUTORSERVICE.submitTest(()->testItrforEachRemaining_ConsumerHelper(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredFunctionGen,itrType,seqSize,FunctionCallType.Unboxed));
                      EXECUTORSERVICE.submitTest(()->testItrforEachRemaining_ConsumerHelper(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredFunctionGen,itrType,seqSize,FunctionCallType.Boxed));
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    EXECUTORSERVICE.completeAllTests();
  }
  private static void testItrforEachRemaining_ConsumerHelper(SeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredFunctionGen monitoredFunctionGen,ItrType itrType,int numToAdd,FunctionCallType functionCallType){
    for(int i=0;i<numToAdd;++i){
      seqMonitor.add(i);
    }
    var itrMonitor=seqMonitor.getItrMonitor(itrType);
    seqMonitor.illegalAdd(preModScenario);
    var monitoredConsumer=monitoredFunctionGen.getMonitoredConsumer(itrMonitor);
    int numExpectedIteratedValues;
    if(preModScenario.expectedException==null || (numToAdd==0 && (preModScenario!=PreModScenario.ModSeq || !seqMonitor.nestedType.forwardIteration))){
      if(monitoredFunctionGen.expectedException==null || (numToAdd==0 && (preModScenario!=PreModScenario.ModSeq || !seqMonitor.nestedType.forwardIteration))){
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
            if(seqMonitor.nestedType.forwardIteration){
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
          numExpectedIteratedValues=1;
          seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyPostAlloc(preModScenario);
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
          seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyPostAlloc(preModScenario);
          break;
        case Throw:
          numExpectedIteratedValues=1;
          seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyPostAlloc(preModScenario);
          break;
        case ModSeq:
          var verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(numToAdd);
          if(preModScenario==PreModScenario.ModSeq){
            numExpectedIteratedValues=numToAdd;
            if(seqMonitor.nestedType.forwardIteration){
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
              for(int i=0;i<numExpectedIteratedValues;++i){
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
          if(preModScenario==PreModScenario.ModSeq){
            ++numExpectedIteratedValues;
          }
          verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyPostAlloc(preModScenario);
          for(int i=0;i<numExpectedIteratedValues;++i){
            verifyItr.verifyIllegalAdd();
          }
          break;
        case ThrowModSeq:
          verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(numToAdd);
          if(preModScenario==PreModScenario.ModSeq){
            verifyItr.verifyIllegalAdd();
          }
          verifyItr.verifyPostAlloc(preModScenario);
          numExpectedIteratedValues=1;
          break;
        case ThrowModParent:
          switch(preModScenario){
            case ModSeq:
              seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyIllegalAdd().verifyPostAlloc(PreModScenario.ModParent);
              break;
            case ModParent:
              seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyParentPostAlloc().verifyIllegalAdd().verifyIllegalAdd().verifyRootPostAlloc();
              break;
            case ModRoot:
              seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyPostAlloc(preModScenario);
              break;
            default:
              throw new Error("Unknown preModScenario "+preModScenario);
          }
          numExpectedIteratedValues=1;
          break;
        case ThrowModRoot:
          seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyPostAlloc(preModScenario).verifyIllegalAdd();
          numExpectedIteratedValues=1;
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
        var arr=((IntArrSeq)seqMonitor.root).arr;
        if(seqMonitor.nestedType.forwardIteration){
          int i=seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc;
          for(var encounteredValue:monitoredConsumer.encounteredValues){
            Assertions.assertEquals(encounteredValue,(Object)arr[i++]);
          }
        }else{
          int i=seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+numToAdd;
          for(var encounteredValue:monitoredConsumer.encounteredValues){
            Assertions.assertEquals(encounteredValue,(Object)arr[--i]);
          }
        }
      }
    }
  }
  @org.junit.jupiter.api.Test
  public void testforEach_Consumer(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var preModScenario:PreModScenario.values()){
          if(preModScenario.expectedException==null || (checkedType.checked && preModScenario!=PreModScenario.ModSeq && !nestedType.rootType)){
            for(var monitoredFunctionGen:MonitoredFunctionGen.values()){
              if((checkedType.checked || monitoredFunctionGen.expectedException==null)&&(!nestedType.rootType || monitoredFunctionGen.appliesToRoot) &&(nestedType.rootType || monitoredFunctionGen.appliesToSubList)){
                for(int seqSize:AbstractIntSeqMonitor.FIB_SEQ){
                  EXECUTORSERVICE.submitTest(()->testforEach_ConsumerHelper(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredFunctionGen,seqSize,FunctionCallType.Unboxed));
                  EXECUTORSERVICE.submitTest(()->testforEach_ConsumerHelper(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredFunctionGen,seqSize,FunctionCallType.Boxed));
                }
              }
            }
          }
        }
      }
    }
    EXECUTORSERVICE.completeAllTests();
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
    var arr=((IntArrSeq)seqMonitor.root).arr;
    if(seqMonitor.nestedType.forwardIteration){
      int i=seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc;
      for(var encounteredValue:monitoredConsumer.encounteredValues){
        Assertions.assertEquals(encounteredValue,(Object)arr[i++]);
      }
    }else{
      int i=seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+numToAdd;
      for(var encounteredValue:monitoredConsumer.encounteredValues){
        Assertions.assertEquals(encounteredValue,(Object)arr[--i]);
      }
    }
  }
  @org.junit.jupiter.api.Test
  public void testListadd_int_val(){
    for(var nestedType:NestedType.values()){
      if(nestedType.forwardIteration){
        for(var checkedType:CheckedType.values()){
          for(var seqLocation:SequenceLocation.values()){
            if(checkedType.checked || seqLocation.expectedException==null){
              for(var preModScenario:PreModScenario.values()){
                if(preModScenario!=PreModScenario.ModSeq && (preModScenario.expectedException==null || checkedType.checked) && (!nestedType.rootType || preModScenario==PreModScenario.NoMod)){
                  for(int seqSize:AbstractIntSeqMonitor.FIB_SEQ){
                    if(seqSize!=0 || seqLocation.validForEmpty){
                      for(var inputArgType:IntInputTestArgType.values()){
                        for(int tmpInitialCapacity=0;tmpInitialCapacity<=15;tmpInitialCapacity+=5){
                          final int initialCapacity=tmpInitialCapacity;
                          switch(nestedType){
                            case LIST:
                              EXECUTORSERVICE.submitTest(()->testListadd_int_valHelper(new SeqMonitor(nestedType,checkedType,initialCapacity),inputArgType,seqLocation,preModScenario,seqSize));
                              break;
                            case SUBLIST:
                              for(int tmpRootPreAlloc=0;tmpRootPreAlloc<=5;tmpRootPreAlloc+=5){
                                final int rootPreAlloc=tmpRootPreAlloc;
                                for(int tmpParentPreAlloc=0;tmpParentPreAlloc<=5;tmpParentPreAlloc+=5){
                                  final int parentPreAlloc=tmpParentPreAlloc;
                                  for(int tmpParentPostAlloc=0;tmpParentPostAlloc<=5;tmpParentPostAlloc+=5){
                                    final int parentPostAlloc=tmpParentPostAlloc;
                                    for(int tmpRootPostAlloc=0;tmpRootPostAlloc<=5;tmpRootPostAlloc+=5){
                                      final int rootPostAlloc=tmpRootPostAlloc;
                                      EXECUTORSERVICE.submitTest(()->testListadd_int_valHelper(new SeqMonitor(nestedType,checkedType,initialCapacity,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),inputArgType,seqLocation,preModScenario,seqSize));
                                    }
                                  }
                                }
                              }
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
      }
    }
    EXECUTORSERVICE.completeAllTests();
  }
  private static void testListadd_int_valHelper(SeqMonitor seqMonitor,IntInputTestArgType inputArgType,SequenceLocation seqLocation,PreModScenario preModScenario,int numToAdd){
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
  public void testListput_int_val(){
    for(var nestedType:NestedType.values()){
      if(nestedType.forwardIteration){
        for(var checkedType:CheckedType.values()){
          for(var seqLocation:SequenceLocation.values()){
            if((checkedType.checked || seqLocation.expectedException==null) && seqLocation.validForEmpty){
              for(var preModScenario:PreModScenario.values()){
                if(preModScenario!=PreModScenario.ModSeq && (preModScenario.expectedException==null || checkedType.checked) && (!nestedType.rootType || preModScenario==PreModScenario.NoMod)){
                  for(int numToAdd:AbstractIntSeqMonitor.FIB_SEQ){
                    if(numToAdd!=0 || seqLocation.expectedException!=null){
                      for(var inputArgType:IntInputTestArgType.values()){
                        EXECUTORSERVICE.submitTest(()->{
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
    EXECUTORSERVICE.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testadd_val(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var preModScenario:PreModScenario.values()){
          if(preModScenario.expectedException==null || (checkedType.checked && preModScenario!=PreModScenario.ModSeq && !nestedType.rootType)){
            for(int seqSize:AbstractIntSeqMonitor.FIB_SEQ){
              for(var inputArgType:IntInputTestArgType.values()){
                for(int tmpInitialCapacity=0;tmpInitialCapacity<=15;tmpInitialCapacity+=5){
                  final int initialCapacity=tmpInitialCapacity;
                  switch(nestedType){
                    case LIST:
                    case STACK:
                      EXECUTORSERVICE.submitTest(()->testadd_valHelper(new SeqMonitor(nestedType,checkedType,initialCapacity),inputArgType,preModScenario,seqSize));
                      break;
                    case SUBLIST:
                      for(int tmpRootPreAlloc=0;tmpRootPreAlloc<=5;tmpRootPreAlloc+=5){
                        final int rootPreAlloc=tmpRootPreAlloc;
                        for(int tmpParentPreAlloc=0;tmpParentPreAlloc<=5;tmpParentPreAlloc+=5){
                          final int parentPreAlloc=tmpParentPreAlloc;
                          for(int tmpParentPostAlloc=0;tmpParentPostAlloc<=5;tmpParentPostAlloc+=5){
                            final int parentPostAlloc=tmpParentPostAlloc;
                            for(int tmpRootPostAlloc=0;tmpRootPostAlloc<=5;tmpRootPostAlloc+=5){
                              final int rootPostAlloc=tmpRootPostAlloc;
                              EXECUTORSERVICE.submitTest(()->testadd_valHelper(new SeqMonitor(nestedType,checkedType,initialCapacity,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),inputArgType,preModScenario,seqSize));
                            }
                          }
                        }
                      }
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
    EXECUTORSERVICE.completeAllTests();
  }
  private static void testadd_valHelper(SeqMonitor seqMonitor,IntInputTestArgType inputArgType,PreModScenario preModScenario,int numToAdd){
    for(int i=0;i<numToAdd;++i){
      seqMonitor.add(i);
    }
    seqMonitor.illegalAdd(preModScenario);
    SequenceVerificationItr verifyItr;
    if(preModScenario.expectedException==null){
      for(int i=0;i<100;++i){
        Assertions.assertTrue(seqMonitor.add(i,inputArgType));
        seqMonitor.verifyStructuralIntegrity();
      }
      verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyAscending(inputArgType,100);
    }else{
      Assertions.assertThrows(preModScenario.expectedException,()->seqMonitor.add(0,inputArgType));
      seqMonitor.verifyStructuralIntegrity();
      verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(numToAdd);
    }
    verifyItr.verifyPostAlloc(preModScenario);
  }
  @org.junit.jupiter.api.Test
  public void testStackpush_val(){
    for(var checkedType:CheckedType.values()){
      for(var inputArgType:IntInputTestArgType.values()){
        for(int tmpInitialCapacity=0;tmpInitialCapacity<=15;tmpInitialCapacity+=5){
          final int initialCapacity=tmpInitialCapacity;
          EXECUTORSERVICE.submitTest(()->{
            var seqMonitor=new SeqMonitor(NestedType.STACK,checkedType,initialCapacity);
            for(int i=0;i<100;++i){
              seqMonitor.push(i,inputArgType);
              seqMonitor.verifyStructuralIntegrity();
            }
            seqMonitor.verifyPreAlloc().verifyAscending(inputArgType,100);
          });
        }
      }
    }
    EXECUTORSERVICE.completeAllTests();
  }
  interface QueryTest
  {
    void runTest(SeqMonitor seqMonitor,QueryTester argType,QueryCastType queryCastType,SequenceLocation seqLocation,int seqSize,PreModScenario preModScenario
    );
    private void runCollectionTests(){
      for(var nestedType:NestedType.values()){
        runTests(nestedType);
      }
      EXECUTORSERVICE.completeAllTests();
    }
    private void runStackTests(){
      runTests(NestedType.STACK);
      EXECUTORSERVICE.completeAllTests();
    }
    private void runListTests(){
      runTests(NestedType.LIST);
      runTests(NestedType.SUBLIST);
      EXECUTORSERVICE.completeAllTests();
    }
    private void runTests(NestedType nestedType){
      for(var checkedType:CheckedType.values()){
        for(var preModScenario:PreModScenario.values()){
          if(preModScenario!=PreModScenario.ModSeq && (preModScenario.expectedException==null || (checkedType.checked && !nestedType.rootType))){
            for(var seqLocation:SequenceLocation.values()){
              if(seqLocation!=SequenceLocation.IOBLO){
                for(int seqSize:AbstractIntSeqMonitor.FIB_SEQ){
                  if(seqLocation==SequenceLocation.IOBHI || seqSize!=0){
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
                          //positive values beyond MAX_SAFE_INT that are beyond the precision of float
                          case IntegerMAX_SAFE_INT_PLUS1:
                          case LongMAX_SAFE_INT_PLUS1:
                          case DoubleMAX_SAFE_INT_PLUS1:
                          //these input values cannot potentially return true
                          break;
                          default:
                          if(seqSize!=0 && seqLocation.expectedException==null){
                            continue;
                          }
                          //these values must necessarily return false
                        }
                        EXECUTORSERVICE.submitTest(()->runTest(new SeqMonitor(nestedType,checkedType),argType,queryCastType,seqLocation,seqSize,preModScenario));
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
  @org.junit.jupiter.api.Test
  public void testcontains_val(){
    QueryTest test=(seqMonitor,argType,queryCastType,seqLocation,numToAdd,preModScenario
    )->{
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
    };
    test.runCollectionTests();
  }
  @org.junit.jupiter.api.Test
  public void testindexOf_val(){
    QueryTest test=(seqMonitor,argType,queryCastType,seqLocation,numToAdd,preModScenario
    )->{
      int expectedIndex;
      if(numToAdd!=0){
        {
          switch(seqLocation){
            case BEGINNING:
              expectedIndex=argType.initContainsBeginning(seqMonitor,numToAdd,seqMonitor.nestedType.forwardIteration)-1;
              break;
            case NEARBEGINNING:
              expectedIndex=argType.initContainsNearBeginning(seqMonitor,numToAdd,seqMonitor.nestedType.forwardIteration)-1;
              break;
            case MIDDLE:
              expectedIndex=argType.initContainsMiddle(seqMonitor,numToAdd,seqMonitor.nestedType.forwardIteration)-1;
              break;
            case NEAREND:
              expectedIndex=argType.initContainsNearEnd(seqMonitor,numToAdd,seqMonitor.nestedType.forwardIteration)-1;
              break;
            case END:
              expectedIndex=argType.initContainsEnd(seqMonitor,numToAdd,seqMonitor.nestedType.forwardIteration)-1;
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
    };
    test.runListTests();
  }
  @org.junit.jupiter.api.Test
  public void testlastIndexOf_val(){
    QueryTest test=(seqMonitor,argType,queryCastType,seqLocation,numToAdd,preModScenario
    )->{
      int expectedIndex;
      if(numToAdd!=0){
        {
          switch(seqLocation){
            case BEGINNING:
              expectedIndex=argType.initContainsBeginning(seqMonitor,numToAdd,seqMonitor.nestedType.forwardIteration)-1;
              break;
            case NEARBEGINNING:
              expectedIndex=argType.initContainsNearBeginning(seqMonitor,numToAdd,seqMonitor.nestedType.forwardIteration)-1;
              break;
            case MIDDLE:
              expectedIndex=argType.initContainsMiddle(seqMonitor,numToAdd,seqMonitor.nestedType.forwardIteration)-1;
              break;
            case NEAREND:
              expectedIndex=argType.initContainsNearEnd(seqMonitor,numToAdd,seqMonitor.nestedType.forwardIteration)-1;
              break;
            case END:
              expectedIndex=argType.initContainsEnd(seqMonitor,numToAdd,seqMonitor.nestedType.forwardIteration)-1;
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
    };
    test.runListTests();
  }
  @org.junit.jupiter.api.Test
  public void testsearch_val(){
    QueryTest test=(seqMonitor,argType,queryCastType,seqLocation,numToAdd,preModScenario
    )->{
      int expectedIndex;
      if(numToAdd!=0){
        {
          switch(seqLocation){
            case BEGINNING:
              expectedIndex=argType.initContainsBeginning(seqMonitor,numToAdd,seqMonitor.nestedType.forwardIteration);
              break;
            case NEARBEGINNING:
              expectedIndex=argType.initContainsNearBeginning(seqMonitor,numToAdd,seqMonitor.nestedType.forwardIteration);
              break;
            case MIDDLE:
              expectedIndex=argType.initContainsMiddle(seqMonitor,numToAdd,seqMonitor.nestedType.forwardIteration);
              break;
            case NEAREND:
              expectedIndex=argType.initContainsNearEnd(seqMonitor,numToAdd,seqMonitor.nestedType.forwardIteration);
              break;
            case END:
              expectedIndex=argType.initContainsEnd(seqMonitor,numToAdd,seqMonitor.nestedType.forwardIteration);
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
    };
    test.runStackTests();
  }
  @org.junit.jupiter.api.Test
  public void testremoveVal_val(){
    QueryTest test=(seqMonitor,argType,queryCastType,seqLocation,numToAdd,preModScenario
    )->{
      if(numToAdd!=0){
        {
          switch(seqLocation){
            case BEGINNING:
              argType.initContainsBeginning(seqMonitor,numToAdd,seqMonitor.nestedType.forwardIteration);
              break;
            case NEARBEGINNING:
              argType.initContainsNearBeginning(seqMonitor,numToAdd,seqMonitor.nestedType.forwardIteration);
              break;
            case MIDDLE:
              argType.initContainsMiddle(seqMonitor,numToAdd,seqMonitor.nestedType.forwardIteration);
              break;
            case NEAREND:
              argType.initContainsNearEnd(seqMonitor,numToAdd,seqMonitor.nestedType.forwardIteration);
              break;
            case END:
              argType.initContainsEnd(seqMonitor,numToAdd,seqMonitor.nestedType.forwardIteration);
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
    };
    test.runCollectionTests();
  }
  @org.junit.jupiter.api.Test
  public void testremoveIf_Predicate(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var preModScenario:PreModScenario.values()){
          if(preModScenario.expectedException==null || (checkedType.checked && preModScenario!=PreModScenario.ModSeq && !nestedType.rootType)){
            for(var monitoredRemoveIfPredicateGen:MonitoredRemoveIfPredicateGen.values()){
              if(monitoredRemoveIfPredicateGen.expectedException==null || (checkedType.checked && (!nestedType.rootType || monitoredRemoveIfPredicateGen.appliesToRoot))){
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
                        EXECUTORSERVICE.submitTest(()->testremoveIf_PredicateHelper(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredRemoveIfPredicateGen,threshold,randSeed,functionCallType,seqSize));
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
    EXECUTORSERVICE.completeAllTests();
  }
  private static void testremoveIf_PredicateHelper(SeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredRemoveIfPredicateGen monitoredRemoveIfPredicateGen,double threshold,long randSeed,final FunctionCallType functionCallType,int seqSize
  ){
    for(int i=0;i<seqSize;++i){
      seqMonitor.add(i);
    }
    final var clone=(OmniCollection.OfInt)seqMonitor.seq.clone();
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
        seqMonitor.verifyRemoveIf(monitoredRemoveIfPredicate,functionCallType,numExpectedRemoved,clone,seqMonitor.nestedType!=NestedType.STACK);
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
    if(seqMonitor.nestedType.forwardIteration){
      var cloneItr=clone.iterator();
      while(cloneItr.hasNext()){
        verifyItr.verifyLiteralIndexAndIterate(cloneItr.nextInt());
      }
    }else{
      var arr=clone.toIntArray();
      for(int i=arr.length;--i>=0;){
         verifyItr.verifyLiteralIndexAndIterate(arr[i]);
      }
    }
    switch(monitoredRemoveIfPredicateGen){
      case ModRoot:
      case ThrowModRoot:
        //The nature of concurrent modification makes verifying the contents of the array tricky due to array reallocations
        //skip it in this scenario
      case Random:
      case RemoveAll:
      case RemoveNone:
      case RemoveFirst:
      case RemoveLast:
      case RemoveFirstAndLast:
      case RemoveAllButFirst:
      case RemoveAllButLast:
      case RemoveAllButFirstAndLast:
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
  public void testListsort_Comparator(){
    for(var nestedType:NestedType.values()){
      if(nestedType.forwardIteration){
        for(var checkedType:CheckedType.values()){
          for(var preModScenario:PreModScenario.values()){
            if((checkedType.checked || preModScenario.expectedException==null) && ((nestedType.rootType && preModScenario.expectedException==null)||(!nestedType.rootType && preModScenario.appliesToSubList))){
              for(var monitoredComparatorGen:MonitoredComparatorGen.values()){
                if((!nestedType.rootType || monitoredComparatorGen.appliesToRoot) && (checkedType.checked || monitoredComparatorGen.expectedException==null)){
                  for(int numToAdd:new int[]{0,2}){
                    for(var functionCallType:FunctionCallType.values()){
                      EXECUTORSERVICE.submitTest(()->{
                        var seqMonitor=new SeqMonitor(nestedType,checkedType);
                        monitoredComparatorGen.init(seqMonitor,numToAdd,preModScenario);
                        var sorter=monitoredComparatorGen.getMonitoredComparator(seqMonitor);
                        if(preModScenario.expectedException==null){
                          if(monitoredComparatorGen.expectedException==null || numToAdd<2){
                            seqMonitor.sort(sorter,functionCallType);
                          }else{
                            Assertions.assertThrows(monitoredComparatorGen.expectedException,()->seqMonitor.sort(sorter,functionCallType));
                          }
                        }else{
                          Assertions.assertThrows(preModScenario.expectedException,()->seqMonitor.sort(sorter,functionCallType));
                        }
                        if(monitoredComparatorGen==MonitoredComparatorGen.NullComparator && seqMonitor.checkedType.checked && preModScenario.expectedException!=null)
                        {
                          monitoredComparatorGen.assertReverseSorted(seqMonitor,numToAdd,preModScenario);
                        }
                        else
                        {
                          monitoredComparatorGen.assertSorted(seqMonitor,numToAdd,preModScenario);
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
    EXECUTORSERVICE.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testListunstableSort_Comparator(){
    for(var nestedType:NestedType.values()){
      if(nestedType.forwardIteration){
        for(var checkedType:CheckedType.values()){
          for(var preModScenario:PreModScenario.values()){
            if((checkedType.checked || preModScenario.expectedException==null) && ((nestedType.rootType && preModScenario.expectedException==null)||(!nestedType.rootType && preModScenario.appliesToSubList))){
              for(var monitoredComparatorGen:MonitoredComparatorGen.values()){
                if((!nestedType.rootType || monitoredComparatorGen.appliesToRoot) && (checkedType.checked || monitoredComparatorGen.expectedException==null)){
                  for(int numToAdd:new int[]{0,2}){
                    EXECUTORSERVICE.submitTest(()->{
                      var seqMonitor=new SeqMonitor(nestedType,checkedType);
                      monitoredComparatorGen.init(seqMonitor,numToAdd,preModScenario);
                      var sorter=monitoredComparatorGen.getMonitoredComparator(seqMonitor);
                      if(preModScenario.expectedException==null){
                        if(monitoredComparatorGen.expectedException==null || numToAdd<2){
                          seqMonitor.unstableSort(sorter);
                        }else{
                          Assertions.assertThrows(monitoredComparatorGen.expectedException,()->seqMonitor.unstableSort(sorter));
                        }
                      }else{
                        Assertions.assertThrows(preModScenario.expectedException,()->seqMonitor.unstableSort(sorter));
                      }
                      if(monitoredComparatorGen==MonitoredComparatorGen.NullComparator && seqMonitor.checkedType.checked && preModScenario.expectedException!=null)
                      {
                        monitoredComparatorGen.assertReverseSorted(seqMonitor,numToAdd,preModScenario);
                      }
                      else
                      {
                        monitoredComparatorGen.assertSorted(seqMonitor,numToAdd,preModScenario);
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
    EXECUTORSERVICE.completeAllTests();
  }
  interface NonComparatorSortTest{
    void runTest(SeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredComparatorGen monitoredComparatorGen,int seqSize);
    private void runTests(){
      for(var nestedType:NestedType.values()){
        if(nestedType.forwardIteration){
          for(var checkedType:CheckedType.values()){
            for(var preModScenario:PreModScenario.values()){
              if((checkedType.checked || preModScenario.expectedException==null) && ((nestedType.rootType && preModScenario.expectedException==null)||(!nestedType.rootType && preModScenario.appliesToSubList))){
                for(var monitoredComparatorGen:MonitoredComparatorGen.values()){
                  if(monitoredComparatorGen.nullComparator && ((!nestedType.rootType || monitoredComparatorGen.appliesToRoot) && (checkedType.checked || monitoredComparatorGen.expectedException==null))){
                    for(int seqSize:AbstractIntSeqMonitor.FIB_SEQ){
                      EXECUTORSERVICE.submitTest(()->runTest(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredComparatorGen,seqSize));
                    }
                  }
                }
              }
            }
          }
        }
      }
      EXECUTORSERVICE.completeAllTests();
    }
  }
  @org.junit.jupiter.api.Test
  public void testListstableAscendingSort_void(){
    NonComparatorSortTest test=(seqMonitor,preModScenario,monitoredComparatorGen,numToAdd)->{
      monitoredComparatorGen.init(seqMonitor,numToAdd,preModScenario);
      if(preModScenario.expectedException==null){
        if(monitoredComparatorGen.expectedException==null || numToAdd<2){
          seqMonitor.stableAscendingSort();
        }else{
          Assertions.assertThrows(monitoredComparatorGen.expectedException,()->seqMonitor.stableAscendingSort());
        }
      }else{
        Assertions.assertThrows(preModScenario.expectedException,()->seqMonitor.stableAscendingSort());
      }
      monitoredComparatorGen.assertSorted(seqMonitor,numToAdd,preModScenario);
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testListstableDescendingSort_void(){
    NonComparatorSortTest test=(seqMonitor,preModScenario,monitoredComparatorGen,numToAdd)->{
      monitoredComparatorGen.initReverse(seqMonitor,numToAdd,preModScenario);
      if(preModScenario.expectedException==null){
        if(monitoredComparatorGen.expectedException==null || numToAdd<2){
          seqMonitor.stableDescendingSort();
        }else{
          Assertions.assertThrows(monitoredComparatorGen.expectedException,()->seqMonitor.stableDescendingSort());
        }
      }else{
        Assertions.assertThrows(preModScenario.expectedException,()->seqMonitor.stableDescendingSort());
      }
      monitoredComparatorGen.assertReverseSorted(seqMonitor,numToAdd,preModScenario);
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testListremoveAt_int(){
    for(var nestedType:NestedType.values()){
      if(nestedType.forwardIteration){
        for(var checkedType:CheckedType.values()){
          for(var preModScenario:PreModScenario.values()){
            if(preModScenario!=PreModScenario.ModSeq && (preModScenario.expectedException==null || (!nestedType.rootType && checkedType.checked))){
              for(var seqLocation:SequenceLocation.values()){
                if(checkedType.checked || seqLocation.expectedException==null){
                  for(int numToAdd:AbstractIntSeqMonitor.FIB_SEQ){
                    if(numToAdd!=0|| (seqLocation==SequenceLocation.IOBHI)){
                      for(var outputArgType:IntOutputTestArgType.values()){
                        EXECUTORSERVICE.submitTest(()->{
                          var seqMonitor=new SeqMonitor(nestedType,checkedType);
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
    EXECUTORSERVICE.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testtoArray_void(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var preModScenario:PreModScenario.values()){
          if(preModScenario.expectedException==null || (checkedType.checked && preModScenario!=PreModScenario.ModSeq && !nestedType.rootType)){
            for(int numToAdd:AbstractIntSeqMonitor.FIB_SEQ){
              for(var outputArgType:IntOutputTestArgType.values()){
                EXECUTORSERVICE.submitTest(()->{
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
    EXECUTORSERVICE.completeAllTests();
  }
  interface BasicCollectionTest{
    void runTest(SeqMonitor seqMonitor,PreModScenario preModScenario,int seqSize);
    private void runTests(){
      for(var nestedType:NestedType.values()){
        for(var checkedType:CheckedType.values()){
          for(var preModScenario:PreModScenario.values()){
            if(preModScenario.expectedException==null || (checkedType.checked && preModScenario!=PreModScenario.ModSeq && !nestedType.rootType)){
              for(int seqSize:AbstractIntSeqMonitor.FIB_SEQ){
                EXECUTORSERVICE.submitTest(()->runTest(new SeqMonitor(nestedType,checkedType),preModScenario,seqSize));
              }
            }
          }
        }
      }
      EXECUTORSERVICE.completeAllTests();
    }
  }
  @org.junit.jupiter.api.Test
  public void testclone_void(){
    BasicCollectionTest test=(seqMonitor,preModScenario,numToAdd)->{
      for(int i=0;i<numToAdd;++i){
        seqMonitor.add(i);
      }
      seqMonitor.illegalAdd(preModScenario);
      if(preModScenario.expectedException==null){
        var clone=(OmniCollection.OfInt)seqMonitor.seq.clone();
        Assertions.assertNotSame(clone,seqMonitor.seq);
        switch(seqMonitor.nestedType){
          case STACK:
            if(seqMonitor.checkedType.checked){
              Assertions.assertTrue(clone instanceof IntArrSeq.CheckedStack);
              Assertions.assertEquals(0,((IntArrSeq.CheckedStack)clone).modCount);
            }else{
              Assertions.assertTrue(clone instanceof IntArrSeq.UncheckedStack);
            }
            break;
          case LIST:
          case SUBLIST:
            if(seqMonitor.checkedType.checked){
              Assertions.assertTrue(clone instanceof IntArrSeq.CheckedList);
              Assertions.assertEquals(0,((IntArrSeq.CheckedList)clone).modCount);
            }else{
              Assertions.assertTrue(clone instanceof IntArrSeq.UncheckedList);
            }
            break;
          default:
            throw new Error("Unknown nested type "+seqMonitor.nestedType);
        }
        var arrSeqClone=(IntArrSeq)clone;
        var originalArr=((IntArrSeq)seqMonitor.root).arr;
        var cloneArr=arrSeqClone.arr;
        Assertions.assertEquals(numToAdd,arrSeqClone.size);
        if(arrSeqClone.size==0){
          Assertions.assertSame(cloneArr,OmniArray.OfInt.DEFAULT_ARR);
        }else{
          Assertions.assertNotSame(originalArr,cloneArr);
          for(int i=0,origOffset=seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc;i<numToAdd;++i,++origOffset){
            Assertions.assertEquals(originalArr[origOffset],cloneArr[i]);
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
  public void testclear_void(){
    BasicCollectionTest test=(seqMonitor,preModScenario,numToAdd)->{
      for(int i=0;i<numToAdd;++i){
        seqMonitor.add(i);
      }
      seqMonitor.illegalAdd(preModScenario);
      if(preModScenario.expectedException==null){
        seqMonitor.clear();
        Assertions.assertTrue(seqMonitor.seq.isEmpty());
        numToAdd=0;
      }else{
        Assertions.assertThrows(preModScenario.expectedException,()->seqMonitor.clear());
      }
      seqMonitor.verifyStructuralIntegrity();
      seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyPostAlloc(preModScenario);
    };
    test.runTests();
  }
  interface StackOutputTest
  {
    void runTest(SeqMonitor seqMonitor,IntOutputTestArgType outputArgType);
    private void runTests(){
      for(var checkedType:CheckedType.values()){
        for(var outputArgType:IntOutputTestArgType.values()){
          EXECUTORSERVICE.submitTest(()->runTest(new SeqMonitor(NestedType.STACK,checkedType),outputArgType));
        }
      }
      EXECUTORSERVICE.completeAllTests();
    }
  }
  @org.junit.jupiter.api.Test
  public void testStackpeek_void(){
    StackOutputTest test=(seqMonitor,outputArgType)->{
      for(int i=0;i<100;){
        outputArgType.verifyPeek(seqMonitor.seq,i,i);
        seqMonitor.verifyStructuralIntegrity();
        seqMonitor.add(++i);
      }
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testStackpop_void(){
    StackOutputTest test=(seqMonitor,outputArgType)->{
      for(int i=0;i<100;++i){
        seqMonitor.add(i);
      }
      for(int i=100;--i>=0;){
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
  public void testStackpoll_void(){
    StackOutputTest test=(seqMonitor,outputArgType)->{
      for(int i=0;i<100;++i){
        seqMonitor.add(i);
      }
      for(int i=100;--i>=0;){
        seqMonitor.poll(i,outputArgType);
        seqMonitor.verifyStructuralIntegrity();
      }
      seqMonitor.poll(0,outputArgType);
      seqMonitor.verifyStructuralIntegrity();
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testiterator_void(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var preModScenario:PreModScenario.values()){
          if(preModScenario.expectedException==null || (checkedType.checked && preModScenario!=PreModScenario.ModSeq && !nestedType.rootType)){
            EXECUTORSERVICE.submitTest(()->{
              var seqMonitor=new SeqMonitor(nestedType,checkedType);
              for(int i=0;i<100;++i){
                seqMonitor.add(i);
              }
              seqMonitor.illegalAdd(preModScenario);
              if(preModScenario.expectedException==null){
                var itrMonitor=seqMonitor.getItrMonitor();
                itrMonitor.verifyIteratorState();
                if(seqMonitor.nestedType.forwardIteration){
                  Assertions.assertEquals(0,itrMonitor.expectedCursor);
                }else{
                  Assertions.assertEquals(100,itrMonitor.expectedCursor);
                }
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
    EXECUTORSERVICE.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testListlistIterator_void(){
    for(var nestedType:NestedType.values()){
      if(nestedType.forwardIteration){
        for(var checkedType:CheckedType.values()){
          for(var preModScenario:PreModScenario.values()){
            if(preModScenario!=PreModScenario.ModSeq && (preModScenario.expectedException==null || (checkedType.checked && !nestedType.rootType))){
              EXECUTORSERVICE.submitTest(()->{
                var seqMonitor=new SeqMonitor(nestedType,checkedType);
                for(int i=0;i<100;++i){
                  seqMonitor.add(i);
                }
                seqMonitor.illegalAdd(preModScenario);
                if(preModScenario.expectedException==null){
                  var itrMonitor=seqMonitor.getListItrMonitor();
                  itrMonitor.verifyIteratorState();
                  Assertions.assertEquals(0,itrMonitor.expectedCursor);
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
    }
    EXECUTORSERVICE.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testListlistIterator_int(){
    for(var nestedType:NestedType.values()){
      if(nestedType.forwardIteration){
        for(var checkedType:CheckedType.values()){
          for(var preModScenario:PreModScenario.values()){
            if(preModScenario!=PreModScenario.ModSeq && (preModScenario.expectedException==null || (checkedType.checked && !nestedType.rootType))){
              for(var seqLocation:SequenceLocation.values()){
                if(seqLocation.expectedException!=null && checkedType.checked){
                  EXECUTORSERVICE.submitTest(()->{
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
                      Assertions.assertEquals(itrIndex,itrMonitor.expectedCursor);
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
    }
    EXECUTORSERVICE.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testListreplaceAll_UnaryOperator(){
    for(var checkedType:CheckedType.values()){
      for(var nestedType:NestedType.values()){
        if(nestedType.forwardIteration){
          for(var preModScenario:PreModScenario.values()){
            if(preModScenario!=PreModScenario.ModSeq&&(preModScenario.expectedException==null||(!nestedType.rootType&&checkedType.checked))){
              for(var monitoredFunctionGen:MonitoredFunctionGen.values()){
                if((checkedType.checked || monitoredFunctionGen.expectedException==null)&&(!nestedType.rootType || monitoredFunctionGen.appliesToRoot) &&(nestedType.rootType || monitoredFunctionGen.appliesToSubList)){
                  for(int seqSize:AbstractIntSeqMonitor.FIB_SEQ){
                    EXECUTORSERVICE.submitTest(()->testListreplaceAll_UnaryOperatorHelper(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredFunctionGen,seqSize,FunctionCallType.Unboxed));
                    EXECUTORSERVICE.submitTest(()->testListreplaceAll_UnaryOperatorHelper(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredFunctionGen,seqSize,FunctionCallType.Boxed));
                  }
                }
              }   
            }
          }
        }
      }
    }
    EXECUTORSERVICE.completeAllTests();
  }
  private static void testListreplaceAll_UnaryOperatorHelper(SeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredFunctionGen monitoredFunctionGen,int numToAdd,FunctionCallType functionCallType){
    for(int i=0;i<numToAdd;++i){
      seqMonitor.add(i);
    }
    seqMonitor.illegalAdd(preModScenario);
    var monitoredUnaryOperator=monitoredFunctionGen.getMonitoredUnaryOperator(seqMonitor);
    int numExpectedIteratedValues;
    if(preModScenario.expectedException==null){
      if(monitoredFunctionGen.expectedException==null || numToAdd==0){
        seqMonitor.replaceAll(monitoredUnaryOperator,functionCallType);
        seqMonitor.verifyStructuralIntegrity();
        seqMonitor.verifyPreAlloc().verifyAscending(1,numToAdd).verifyPostAlloc(preModScenario);
        numExpectedIteratedValues=numToAdd;
      }else{
        Assertions.assertThrows(monitoredFunctionGen.expectedException,()->seqMonitor.replaceAll(monitoredUnaryOperator,functionCallType));
        seqMonitor.verifyStructuralIntegrity();
        var verifyItr=seqMonitor.verifyPreAlloc();
        switch(monitoredFunctionGen){
          case Throw:
            numExpectedIteratedValues=1;
            verifyItr.verifyAscending(numToAdd).verifyPostAlloc();
            break;
          case ModSeq:
            numExpectedIteratedValues=numToAdd;
            //don't bother verifying the array values
            //If the function adds to the array and triggers a resize, it can cause unexpected results
            verifyItr.skip(numToAdd);
            //verifyItr.verifyAscending(1,numToAdd);
            for(int i=0;i<numToAdd;++i){
              verifyItr.verifyIllegalAdd();
            }
            verifyItr.verifyPostAlloc();
            break;
          case ModParent:
            numExpectedIteratedValues=numToAdd;
            //don't bother verifying the array values
            //If the function adds to the array and triggers a resize, it can cause unexpected results
            //verifyItr.verifyAscending(1,numToAdd).verifyParentPostAlloc();
            verifyItr.skip(numToAdd).verifyParentPostAlloc();
            for(int i=0;i<numToAdd;++i){
              verifyItr.verifyIllegalAdd();
            }
            verifyItr.verifyRootPostAlloc();
            break;
          case ModRoot:
            numExpectedIteratedValues=numToAdd;
            //don't bother verifying the array values
            //If the function adds to the array and triggers a resize, it can cause unexpected results
            //verifyItr.verifyAscending(1,numToAdd)
            verifyItr.skip(numToAdd).verifyPostAlloc();
            for(int i=0;i<numToAdd;++i){
              verifyItr.verifyIllegalAdd();
            }
            break;
          case ThrowModSeq:
            numExpectedIteratedValues=1;
            verifyItr.verifyAscending(numToAdd).verifyPostAlloc(PreModScenario.ModSeq);
            break;
          case ThrowModParent:
            numExpectedIteratedValues=1;
            verifyItr.verifyAscending(numToAdd).verifyPostAlloc(PreModScenario.ModParent);
            break;
          case ThrowModRoot:
            numExpectedIteratedValues=1;
            verifyItr.verifyAscending(numToAdd).verifyPostAlloc(PreModScenario.ModRoot);
            break;
          default:
            throw new Error("Unknown monitored function gen "+monitoredFunctionGen);
        }
      }
    }else{
      Assertions.assertThrows(preModScenario.expectedException,()->seqMonitor.replaceAll(monitoredUnaryOperator,functionCallType));
      seqMonitor.verifyStructuralIntegrity();
      var verifyItr=seqMonitor.verifyPreAlloc();
      switch(monitoredFunctionGen){
        case NoThrow:
          numExpectedIteratedValues=numToAdd;
          verifyItr.verifyAscending(1,numToAdd).verifyPostAlloc(preModScenario);
          break;
        case Throw:
          numExpectedIteratedValues=numToAdd==0?0:1;
          verifyItr.verifyAscending(numToAdd).verifyPostAlloc(preModScenario);
          break;
        case ModSeq:
          numExpectedIteratedValues=numToAdd==0?0:1;
          verifyItr.verifyAscending(numToAdd).verifyPostAlloc(preModScenario);
          break;
        case ModParent:
          switch(preModScenario){
            case ModRoot:
              numExpectedIteratedValues=numToAdd==0?0:1;
              verifyItr.verifyAscending(numToAdd).verifyPostAlloc(preModScenario);
              break;
            case ModParent:
              numExpectedIteratedValues=numToAdd;
              //don't bother verifying the array values
              //If the function adds to the array and triggers a resize, it can cause unexpected results
              //verifyItr.verifyAscending(1,numToAdd)
              verifyItr.skip(numToAdd);
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
          //don't bother verifying the array values
          //If the function adds to the array and triggers a resize, it can cause unexpected results
          //verifyItr.verifyAscending(1,numToAdd);
          verifyItr.skip(numToAdd);
          verifyItr.verifyPostAlloc(preModScenario);
          for(int i=0;i<numExpectedIteratedValues;++i){
            verifyItr.verifyIllegalAdd();
          }
          break;
        case ThrowModSeq:
          verifyItr.verifyAscending(numToAdd).verifyPostAlloc(preModScenario);
          numExpectedIteratedValues=numToAdd==0?0:1;
          break;
        case ThrowModParent:
          numExpectedIteratedValues=numToAdd==0?0:1;
          switch(preModScenario){
            case ModParent:
              verifyItr.verifyAscending(numToAdd).verifyParentPostAlloc();
              verifyItr.verifyIllegalAdd();
              for(int i=0;i<numExpectedIteratedValues;++i){
                 verifyItr.verifyIllegalAdd();
              }
              verifyItr.verifyRootPostAlloc();
              break;
            case ModRoot:
              verifyItr.verifyAscending(numToAdd).verifyPostAlloc(preModScenario);
              break;
            default:
              throw new Error("Unknown preModScenario "+preModScenario);
          }
          break;
        case ThrowModRoot:
          numExpectedIteratedValues=numToAdd==0?0:1;
          verifyItr.verifyAscending(numToAdd).verifyPostAlloc(preModScenario);
          for(int i=0;i<numExpectedIteratedValues;++i){
            verifyItr.verifyIllegalAdd();
          }
          break;
        default:
          throw new Error("Unknown monitored function gen "+monitoredFunctionGen);
      }
    }
    Assertions.assertEquals(numExpectedIteratedValues,monitoredUnaryOperator.encounteredValues.size());
    var arr=((IntArrSeq)seqMonitor.root).arr;
    int i=seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc;
    if(numExpectedIteratedValues==numToAdd){
      if(monitoredFunctionGen.expectedException==null){
        for(var encounteredValue:monitoredUnaryOperator.encounteredValues){
          var expectedVal=(int)(((int)encounteredValue)+1);
          Assertions.assertEquals(expectedVal,arr[i++]);
        }
      }
    }else{
      for(var encounteredValue:monitoredUnaryOperator.encounteredValues){
        Assertions.assertEquals(encounteredValue,(Object)arr[i++]);
      }
    }
  }
  @org.junit.jupiter.api.Test
  public void testListsubList_int_int(){
    for(var nestedType:NestedType.values()){
      if(nestedType.forwardIteration){
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
                        EXECUTORSERVICE.submitTest(()->{
                          var seqMonitor=new SeqMonitor(nestedType,checkedType);
                          for(int i=0;i<seqSize;++i){
                            seqMonitor.add(i);
                          }
                          seqMonitor.illegalAdd(preModScenario);
                          if(preModScenario.expectedException==null){
                            if(fromIndex>=0 && fromIndex<=toIndex && toIndex<=seqSize){
                              var subList=((OmniList.OfInt)seqMonitor.seq).subList(fromIndex,toIndex);
                              if(seqMonitor.checkedType.checked){
                                Assertions.assertEquals(seqMonitor.expectedRootModCount,FieldAndMethodAccessor.IntArrSeq.CheckedSubList.modCount(subList));
                                if(seqMonitor.nestedType.rootType){
                                  Assertions.assertNull(FieldAndMethodAccessor.IntArrSeq.CheckedSubList.parent(subList));
                                }else{
                                  Assertions.assertSame(seqMonitor.seq,FieldAndMethodAccessor.IntArrSeq.CheckedSubList.parent(subList));
                                }
                                Assertions.assertSame(seqMonitor.root,FieldAndMethodAccessor.IntArrSeq.CheckedSubList.root(subList));
                                Assertions.assertEquals(toIndex-fromIndex,FieldAndMethodAccessor.IntArrSeq.CheckedSubList.size(subList));
                                Assertions.assertEquals(seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+fromIndex,FieldAndMethodAccessor.IntArrSeq.CheckedSubList.rootOffset(subList));
                              }else{
                                if(seqMonitor.nestedType.rootType){
                                  Assertions.assertNull(FieldAndMethodAccessor.IntArrSeq.UncheckedSubList.parent(subList));
                                }else{
                                  Assertions.assertSame(seqMonitor.seq,FieldAndMethodAccessor.IntArrSeq.UncheckedSubList.parent(subList));
                                }
                                Assertions.assertSame(seqMonitor.root,FieldAndMethodAccessor.IntArrSeq.UncheckedSubList.root(subList));
                                Assertions.assertEquals(toIndex-fromIndex,FieldAndMethodAccessor.IntArrSeq.UncheckedSubList.size(subList));
                                Assertions.assertEquals(seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+fromIndex,FieldAndMethodAccessor.IntArrSeq.UncheckedSubList.rootOffset(subList));
                              }
                            }else{
                              Assertions.assertThrows(IndexOutOfBoundsException.class,()->((OmniList.OfInt)seqMonitor.seq).subList(fromIndex,toIndex));
                            }
                          }else{
                            Assertions.assertThrows(preModScenario.expectedException,()->((OmniList.OfInt)seqMonitor.seq).subList(fromIndex,toIndex));
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
    }
    EXECUTORSERVICE.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testtoArray_IntFunction(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var preModScenario:PreModScenario.values()){
          if(preModScenario!=PreModScenario.ModSeq && (preModScenario.expectedException==null || (checkedType.checked && !nestedType.rootType))){
            for(var monitoredFunctionGen:MonitoredFunctionGen.values()){
              if((checkedType.checked || monitoredFunctionGen.expectedException==null)&&(!nestedType.rootType || monitoredFunctionGen.appliesToRoot) &&(nestedType.rootType || monitoredFunctionGen.appliesToSubList)){
                for(int numToAdd:AbstractIntSeqMonitor.FIB_SEQ){
                  EXECUTORSERVICE.submitTest(()->{
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
                          Assertions.assertEquals(resultArr[i],(Object)itr.nextInt());
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
    EXECUTORSERVICE.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testtoArray_ObjectArray(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var preModScenario:PreModScenario.values()){
          if(preModScenario!=PreModScenario.ModSeq && (preModScenario.expectedException==null || (checkedType.checked && !nestedType.rootType))){
            for(int tmpSeqSize=0;tmpSeqSize<=15;tmpSeqSize+=5){
              final int seqSize=tmpSeqSize;
              for(int tmpArrSize=0;tmpArrSize<=seqSize+5;tmpArrSize+=5){
                final int arrSize=tmpArrSize;
                EXECUTORSERVICE.submitTest(()->{
                  var seqMonitor=new SeqMonitor(nestedType,checkedType);
                  for(int i=0;i<seqSize;++i){
                    seqMonitor.add(i);
                  }
                  seqMonitor.illegalAdd(preModScenario);
                  Integer[] paramArr=new Integer[arrSize];
                  for(int i=seqSize,bound=seqSize+arrSize;i<bound;++i){
                    paramArr[i-seqSize]=TypeConversionUtil.convertToInteger(i);
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
                        Assertions.assertEquals(TypeConversionUtil.convertToInteger(i+seqSize),resultArr[i]);
                      }
                    }else{
                      Assertions.assertSame(paramArr,resultArr);
                    }
                    var itr=seqMonitor.seq.iterator();
                    for(int i=0;i<seqSize;++i){
                      Assertions.assertEquals((Object)itr.nextInt(),resultArr[i]);
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
    EXECUTORSERVICE.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testListset_int_val(){
    for(var nestedType:NestedType.values()){
      if(nestedType.forwardIteration){
        for(var checkedType:CheckedType.values()){
          for(var preModScenario:PreModScenario.values()){
            if(preModScenario.expectedException==null || (checkedType.checked && preModScenario!=PreModScenario.ModSeq && !nestedType.rootType)){
              for(int seqSize:AbstractIntSeqMonitor.FIB_SEQ){
                for(var seqLocation:SequenceLocation.values()){
                  if((seqLocation==SequenceLocation.BEGINNING && seqSize!=0) || (checkedType.checked && seqLocation.expectedException!=null)){
                    EXECUTORSERVICE.submitTest(()->testListset_int_valHelper(new SeqMonitor(nestedType,checkedType),preModScenario,seqSize,seqLocation,FunctionCallType.Unboxed));
                    EXECUTORSERVICE.submitTest(()->testListset_int_valHelper(new SeqMonitor(nestedType,checkedType),preModScenario,seqSize,seqLocation,FunctionCallType.Boxed));
                  }
                }
              }
            }
          }
        }
      }
    }
    EXECUTORSERVICE.completeAllTests();
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
  @org.junit.jupiter.api.Test
  public void testListget_int(){
    for(var nestedType:NestedType.values()){
      if(nestedType.forwardIteration){
        for(var checkedType:CheckedType.values()){
          for(var preModScenario:PreModScenario.values()){
            if(preModScenario.expectedException==null || (checkedType.checked && preModScenario!=PreModScenario.ModSeq && !nestedType.rootType)){
              for(int numToAdd:AbstractIntSeqMonitor.FIB_SEQ){
                for(var seqLocation:SequenceLocation.values()){
                  if((seqLocation==SequenceLocation.BEGINNING && numToAdd!=0) || (checkedType.checked && seqLocation.expectedException!=null)){
                    for(var outputArgType:IntOutputTestArgType.values()){
                      EXECUTORSERVICE.submitTest(()->{
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
    EXECUTORSERVICE.completeAllTests();
  }
  interface ToStringAndHashCodeTest{
    void runTest(SeqMonitor seqMonitor,PreModScenario preModScenario,int seqSize
    );
    private void runTests(){
      for(var nestedType:NestedType.values()){
        for(var checkedType:CheckedType.values()){
          for(var preModScenario:PreModScenario.values()){
            if(preModScenario.expectedException==null || (checkedType.checked && preModScenario!=PreModScenario.ModSeq && !nestedType.rootType)){
              for(int seqSize:AbstractIntSeqMonitor.FIB_SEQ){
                EXECUTORSERVICE.submitTest(()->runTest(new SeqMonitor(nestedType,checkedType),preModScenario,seqSize));
              }
            }
          }
        }
      }
      EXECUTORSERVICE.completeAllTests();
    }
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
  public void testreadAndwriteObject(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var preModScenario:PreModScenario.values()){
          if(preModScenario.expectedException==null || (checkedType.checked && preModScenario!=PreModScenario.ModSeq && !nestedType.rootType)){
            for(var monitoredFunctionGen:MonitoredFunctionGen.values()){
              if((checkedType.checked || monitoredFunctionGen.expectedException==null)&&(!nestedType.rootType || monitoredFunctionGen.appliesToRoot) &&(nestedType.rootType || monitoredFunctionGen.appliesToSubList)){
                for(int numToAdd:AbstractIntSeqMonitor.FIB_SEQ){
                  EXECUTORSERVICE.submitTest(()->{
                    var seqMonitor=new SeqMonitor(nestedType,checkedType);
                    for(int i=0;i<numToAdd;++i){
                      seqMonitor.add(i);
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
                        seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyPostAlloc(preModScenario);
                        OmniCollection.OfInt readCol=null;
                        try(var ois=new ObjectInputStream(new FileInputStream(file));){
                          readCol=(OmniCollection.OfInt)ois.readObject();
                        }catch(Exception e){
                          Assertions.fail(e);
                          return;
                        }
                        var itr=readCol.iterator();
                        if(seqMonitor.nestedType.forwardIteration){
                          for(int i=0;i<numToAdd;++i){
                            Assertions.assertEquals(TypeConversionUtil.convertToint(i),itr.nextInt());
                          }
                        }else{
                          for(int i=0;i<numToAdd;++i){
                            Assertions.assertEquals(TypeConversionUtil.convertToint(numToAdd-i-1),itr.nextInt());
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
    EXECUTORSERVICE.completeAllTests();
  }
  private static class SeqMonitor extends AbstractIntSeqMonitor{
    static final int DEFAULT_PRE_AND_POST_ALLOC=5;
    private static void initArray(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc,int[] arr){
      for(int i=0,v=Integer.MIN_VALUE,bound=rootPreAlloc;i<bound;++i,++v){
        arr[i]=TypeConversionUtil.convertToint(v);
      }
      for(int i=rootPreAlloc,v=Integer.MIN_VALUE+rootPreAlloc,bound=i+parentPreAlloc;i<bound;++i,++v){
        arr[i]=TypeConversionUtil.convertToint(v);
      }
      for(int i=rootPreAlloc+parentPreAlloc,v=Integer.MAX_VALUE-rootPostAlloc-parentPostAlloc,bound=i+parentPostAlloc;i<bound;++i,++v){
        arr[i]=TypeConversionUtil.convertToint(v);
      }
      for(int i=rootPreAlloc+parentPreAlloc+parentPostAlloc,v=Integer.MAX_VALUE-rootPostAlloc,bound=i+rootPostAlloc;i<bound;++i,++v){
        arr[i]=TypeConversionUtil.convertToint(v);
      }
    }
    final NestedType nestedType;
    final int initialCapacity;
    final int rootPreAlloc;
    final int parentPreAlloc;
    final int parentPostAlloc;
    final int rootPostAlloc;
    final IntArrSeq root;
    final OmniCollection.OfInt parent;
    int expectedRootSize;
    int expectedParentSize;
    int expectedRootModCount;
    int expectedParentModCount;
    SeqMonitor(NestedType nestedType,CheckedType checkedType,int seqLength,int[] arr){
      super(checkedType);
      this.nestedType=nestedType;
      this.expectedRootSize=seqLength;
      this.expectedParentSize=seqLength;
      this.expectedSeqSize=seqLength;
      this.initialCapacity=(arr==null)?0:(arr==OmniArray.OfInt.DEFAULT_ARR?OmniArray.DEFAULT_ARR_SEQ_CAP:arr.length);
      switch(nestedType){
        case SUBLIST:
        case LIST:
          this.root=checkedType.checked?new IntArrSeq.CheckedList(seqLength,arr):new IntArrSeq.UncheckedList(seqLength,arr);
          break;
        case STACK:
          this.root=checkedType.checked?new IntArrSeq.CheckedStack(seqLength,arr):new IntArrSeq.UncheckedStack(seqLength,arr);
          break;
        default:
          throw new Error("Unknown nestedType "+nestedType);
      }
      this.rootPreAlloc=0;
      this.parentPreAlloc=0;
      this.parentPostAlloc=0;
      this.rootPostAlloc=0;
      if(nestedType.rootType){
        this.parent=root;
        this.seq=root;
      }else{
        Assertions.assertTrue(arr!=null && seqLength<=arr.length);
        this.parent=((OmniList.OfInt)root).subList(rootPreAlloc,seqLength-rootPostAlloc);
        this.seq=((OmniList.OfInt)parent).subList(parentPreAlloc,seqLength-rootPreAlloc-parentPostAlloc-rootPostAlloc);
      }
    }
    SeqMonitor(final NestedType nestedType,final CheckedType checkedType){
      super(checkedType);
      this.nestedType=nestedType;
      this.initialCapacity=OmniArray.DEFAULT_ARR_SEQ_CAP;
      if(nestedType==NestedType.SUBLIST){
        this.rootPreAlloc=DEFAULT_PRE_AND_POST_ALLOC;
        this.parentPreAlloc=DEFAULT_PRE_AND_POST_ALLOC;
        this.parentPostAlloc=DEFAULT_PRE_AND_POST_ALLOC;
        this.rootPostAlloc=DEFAULT_PRE_AND_POST_ALLOC;
        int rootSize;
        int[] arr=new int[rootSize=rootPreAlloc+parentPreAlloc+parentPostAlloc+rootPostAlloc];
        initArray(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,arr);
        this.root=checkedType.checked
          ?new IntArrSeq.CheckedList(rootSize,arr)
          :new IntArrSeq.UncheckedList(rootSize,arr);
        this.parent=((OmniList.OfInt)root).subList(rootPreAlloc,rootSize-rootPostAlloc);
        this.seq=((OmniList.OfInt)parent).subList(parentPreAlloc,parentPreAlloc);
      }else{
        this.rootPreAlloc=0;
        this.parentPreAlloc=0;
        this.parentPostAlloc=0;
        this.rootPostAlloc=0;
        switch(nestedType){
          default:
            throw new Error("Unknown nestedType "+nestedType);
          case STACK:
            this.root=checkedType.checked
              ?new IntArrSeq.CheckedStack()
              :new IntArrSeq.UncheckedStack();
            break;
          case LIST:
            this.root=checkedType.checked
              ?new IntArrSeq.CheckedList()
              :new IntArrSeq.UncheckedList();
        }
        this.parent=root;
        this.seq=root;
      }
    }
    SeqMonitor(final NestedType nestedType,final CheckedType checkedType,final int initialCapacity){
      this(nestedType,checkedType,initialCapacity,0,0,0,0);
    }
    SeqMonitor(final NestedType nestedType,CheckedType checkedType,final int initialCapacity,final int rootPreAlloc,final int parentPreAlloc,final int parentPostAlloc,final int rootPostAlloc){
      super(checkedType);
      this.nestedType=nestedType;
      this.initialCapacity=initialCapacity;
      this.rootPreAlloc=rootPreAlloc;
      this.parentPreAlloc=parentPreAlloc;
      this.parentPostAlloc=parentPostAlloc;
      this.rootPostAlloc=rootPostAlloc;
      int rootSize=rootPreAlloc+parentPreAlloc+parentPostAlloc+rootPostAlloc;
      int[] arr;
      if(rootSize==0){
        switch(initialCapacity){
          case 0:
            arr=null;
            break;
          case OmniArray.DEFAULT_ARR_SEQ_CAP:
            arr=OmniArray.OfInt.DEFAULT_ARR;
            break;
          default:
            arr=new int[initialCapacity];
        }
      }else{
        arr=new int[Math.max(initialCapacity,rootSize)];
      }
      initArray(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,arr);
      this.root=nestedType==NestedType.STACK
        ?checkedType.checked
          ?new IntArrSeq.CheckedStack(rootSize,arr)
          :new IntArrSeq.UncheckedStack(rootSize,arr)
        :checkedType.checked
          ?new IntArrSeq.CheckedList(rootSize,arr)
          :new IntArrSeq.UncheckedList(rootSize,arr);
      switch(nestedType){
        case SUBLIST:
          this.parent=((OmniList.OfInt)root).subList(rootPreAlloc,rootPreAlloc+parentPreAlloc+parentPostAlloc);
          this.seq=((OmniList.OfInt)parent).subList(parentPreAlloc,parentPreAlloc);
          break;
        case LIST:
        case STACK:
          this.parent=root;
          this.seq=root;
          break;
        default:
          throw new Error("Unknown nestedType "+nestedType);
      }
    }
    class UncheckedArrSeqItrMonitor extends AbstractIntSeqMonitor.AbstractItrMonitor{
      int expectedCursor;
      int expectedLastRet;
      private UncheckedArrSeqItrMonitor(ItrType itrType,OmniIterator.OfInt itr,int expectedCursor){
        super(itrType,itr,expectedSeqModCount);
        this.expectedCursor=expectedCursor;
        this.expectedLastRet=-1;
      }
      void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
        int expectedBound=nestedType.forwardIteration?expectedSeqSize:0;
        if(functionCallType==FunctionCallType.Boxed){
          itr.forEachRemaining((Consumer)action);
        }else
        {
          itr.forEachRemaining((IntConsumer)action);
        }
        if(nestedType.forwardIteration){
          if(expectedCursor<expectedBound){
            expectedCursor=expectedBound;
            expectedLastRet=expectedCursor-1;
          }
        }else{
          if(expectedCursor>expectedBound){
            expectedCursor=expectedBound;
            expectedLastRet=expectedCursor;
          }
        }
      }
      void iterateReverse(){
        ((OmniListIterator.OfInt)itr).previousInt();
        expectedLastRet=--expectedCursor;
      }
      SeqMonitor getSeqMonitor(){
        return SeqMonitor.this;
      }
      @Override void verifyIteratorState(){
        int actualCursor;
        Object actualParent;
        switch(nestedType){
          case LIST:
            actualCursor=FieldAndMethodAccessor.IntArrSeq.UncheckedList.Itr.cursor(itr);
            actualParent=FieldAndMethodAccessor.IntArrSeq.UncheckedList.Itr.parent(itr);
            break;
          case STACK:
            actualCursor=FieldAndMethodAccessor.IntArrSeq.UncheckedStack.Itr.cursor(itr);
            actualParent=FieldAndMethodAccessor.IntArrSeq.UncheckedStack.Itr.parent(itr);
            break;
          case SUBLIST:
            actualCursor=FieldAndMethodAccessor.IntArrSeq.UncheckedSubList.Itr.cursor(itr);
            actualParent=FieldAndMethodAccessor.IntArrSeq.UncheckedSubList.Itr.parent(itr);
            break;
          default:
            throw new Error("Unknown nestedType "+nestedType);
        }
        Assertions.assertEquals(expectedCursor+(rootPreAlloc+parentPreAlloc),actualCursor);
        Assertions.assertSame(seq,actualParent);
      }
      @Override void add(int v,IntInputTestArgType inputArgType){
        inputArgType.callListItrAdd((OmniListIterator.OfInt)itr,v);
        ++expectedCursor;
        verifyAddition();
        expectedLastRet=-1;
      }
      @Override void iterateForward(){
        itr.nextInt();
        expectedLastRet=nestedType==NestedType.STACK
          ?--expectedCursor
          :expectedCursor++;
      }
      @Override void verifyNext(int expectedVal,IntOutputTestArgType outputType){
        outputType.verifyItrNext(itr,expectedVal);
        expectedLastRet=nestedType==NestedType.STACK
          ?--expectedCursor
          :expectedCursor++;
      }
      @Override void verifyPrevious(int expectedVal,IntOutputTestArgType outputType){
        outputType.verifyItrPrevious(itr,expectedVal);
        expectedLastRet=--expectedCursor;
      }
      @Override void remove(){
        itr.remove();
        verifyRemoval();
        expectedCursor=expectedLastRet;
        expectedLastRet=-1;
      }
    }
    class CheckedArrSeqItrMonitor extends UncheckedArrSeqItrMonitor{
      private CheckedArrSeqItrMonitor(ItrType itrType,OmniIterator.OfInt itr,int expectedCursor){
        super(itrType,itr,expectedCursor);
      }
      @Override void verifyIteratorState(){
        int actualCursor;
        Object actualParent;
        switch(nestedType){
          case LIST:
            actualCursor=FieldAndMethodAccessor.IntArrSeq.CheckedList.Itr.cursor(itr);
            actualParent=FieldAndMethodAccessor.IntArrSeq.CheckedList.Itr.parent(itr);
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.IntArrSeq.CheckedList.Itr.modCount(itr));
            Assertions.assertEquals(expectedLastRet<0?expectedLastRet:expectedLastRet+(rootPreAlloc+parentPreAlloc),FieldAndMethodAccessor.IntArrSeq.CheckedList.Itr.lastRet(itr));
            break;
          case STACK:
            actualCursor=FieldAndMethodAccessor.IntArrSeq.CheckedStack.Itr.cursor(itr);
            actualParent=FieldAndMethodAccessor.IntArrSeq.CheckedStack.Itr.parent(itr);
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.IntArrSeq.CheckedStack.Itr.modCount(itr));
            Assertions.assertEquals(expectedLastRet<0?expectedLastRet:expectedLastRet+(rootPreAlloc+parentPreAlloc),FieldAndMethodAccessor.IntArrSeq.CheckedStack.Itr.lastRet(itr));
            break;
          case SUBLIST:
            actualCursor=FieldAndMethodAccessor.IntArrSeq.CheckedSubList.Itr.cursor(itr);
            actualParent=FieldAndMethodAccessor.IntArrSeq.CheckedSubList.Itr.parent(itr);
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.IntArrSeq.CheckedSubList.Itr.modCount(itr));
            Assertions.assertEquals(expectedLastRet<0?expectedLastRet:expectedLastRet+(rootPreAlloc+parentPreAlloc),FieldAndMethodAccessor.IntArrSeq.CheckedSubList.Itr.lastRet(itr));
            break;
          default:
            throw new Error("Unknown nestedType "+nestedType);
        }
        Assertions.assertEquals(expectedCursor+(rootPreAlloc+parentPreAlloc),actualCursor);
        Assertions.assertSame(seq,actualParent);
      }
      @Override void add(int v,IntInputTestArgType inputArgType){
        super.add(v,inputArgType);
        ++expectedItrModCount;
      }
      @Override void remove(){
        super.remove();
        ++expectedItrModCount;
      }
    }
    UncheckedArrSeqItrMonitor getItrMonitor(){
      var itr=seq.iterator();
      int expectedCursor=nestedType==NestedType.STACK?root.size:0;
      return checkedType.checked
        ?new CheckedArrSeqItrMonitor(ItrType.Itr,itr,expectedCursor)
        :new UncheckedArrSeqItrMonitor(ItrType.Itr,itr,expectedCursor);
    }
    UncheckedArrSeqItrMonitor getListItrMonitor(){
      var itr=((OmniList.OfInt)seq).listIterator();
      return checkedType.checked
        ?new CheckedArrSeqItrMonitor(ItrType.ListItr,itr,0)
        :new UncheckedArrSeqItrMonitor(ItrType.ListItr,itr,0);
    }
    UncheckedArrSeqItrMonitor getListItrMonitor(int index){
      var itr=((OmniList.OfInt)seq).listIterator(index);
      return checkedType.checked
        ?new CheckedArrSeqItrMonitor(ItrType.ListItr,itr,index)
        :new UncheckedArrSeqItrMonitor(ItrType.ListItr,itr,index);
    }
    private static class ArrSeqSequenceVerificationItr extends SequenceVerificationItr{
      final int[] arr;
      int offset;
      final SeqMonitor seqMonitor;
      private ArrSeqSequenceVerificationItr(SeqMonitor seqMonitor,int offset,int[] arr){
        this.seqMonitor=seqMonitor;
        this.arr=arr;
        this.offset=offset;
      }
      SequenceVerificationItr verifyNaturalAscending(int v,IntInputTestArgType inputArgType,int length){
        return verifyAscending(v,inputArgType,length);
      }
      @Override SequenceVerificationItr verifyPostAlloc(int expectedVal){
        for(int i=0,bound=seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc;i<bound;++i){
          verifyIndexAndIterate(IntInputTestArgType.ARRAY_TYPE,expectedVal);
        }
        return this;
      }
      @Override void verifyLiteralIndexAndIterate(int val){
        Assertions.assertEquals(val,arr[offset++]);
      }
      @Override void reverseAndVerifyIndex(IntInputTestArgType inputArgType,int val){
        inputArgType.verifyVal(val,arr[--offset]);
      }
      @Override void verifyIndexAndIterate(IntInputTestArgType inputArgType,int val){
        inputArgType.verifyVal(val,arr[offset++]);
      }
      @Override SequenceVerificationItr getOffset(int i){
        return new ArrSeqSequenceVerificationItr(seqMonitor,i+offset,arr);
      }
      @Override SequenceVerificationItr skip(int i){
        this.offset+=i;
        return this;
      }
      @Override public boolean equals(Object val){
        final ArrSeqSequenceVerificationItr that;
        return val==this || (val instanceof ArrSeqSequenceVerificationItr && (that=(ArrSeqSequenceVerificationItr)val).arr==this.arr && that.offset==this.offset);
      }
      @Override SequenceVerificationItr verifyRootPostAlloc(){
        for(int i=0,v=Integer.MAX_VALUE-seqMonitor.rootPostAlloc;i<seqMonitor.rootPostAlloc;++i,++v){
          verifyIndexAndIterate(IntInputTestArgType.ARRAY_TYPE,v);
        }
        return this;
      }
      @Override SequenceVerificationItr verifyParentPostAlloc(){
        for(int i=0,v=Integer.MAX_VALUE-seqMonitor.rootPostAlloc-seqMonitor.parentPostAlloc;i<seqMonitor.parentPostAlloc;++i,++v){
          verifyIndexAndIterate(IntInputTestArgType.ARRAY_TYPE,v);
        }
        return this;
      }
    }
    SequenceVerificationItr verifyPreAlloc(int expectedVal){
      var arr=root.arr;
      int offset=0;
      for(int bound=offset+rootPreAlloc+parentPreAlloc;offset<bound;++offset){
        IntInputTestArgType.ARRAY_TYPE.verifyVal(expectedVal,arr[offset]);
      }
      return new ArrSeqSequenceVerificationItr(this,offset,arr);
    }
    SequenceVerificationItr verifyPreAlloc(){
      var arr=root.arr;
      int offset=0;
      for(int bound=offset+rootPreAlloc+parentPreAlloc,v=Integer.MIN_VALUE;offset<bound;++offset,++v){
        IntInputTestArgType.ARRAY_TYPE.verifyVal(v,arr[offset]);
      }
      return new ArrSeqSequenceVerificationItr(this,offset,arr);
    }
    void illegalAdd(PreModScenario preModScenario){
      switch(preModScenario){
        case ModSeq:
          IntInputTestArgType.ARRAY_TYPE.callCollectionAdd(seq,0);
          ++expectedRootModCount;
          ++expectedRootSize;
          ++expectedParentModCount;
          ++expectedParentSize;
          ++expectedSeqModCount;
          ++expectedSeqSize;
          break;
        case ModParent:
          IntInputTestArgType.ARRAY_TYPE.callCollectionAdd(parent,0);
          ++expectedRootModCount;
          ++expectedRootSize;
          ++expectedParentModCount;
          ++expectedParentSize;
          break;
        case ModRoot:
          IntInputTestArgType.ARRAY_TYPE.callCollectionAdd(root,0);
          ++expectedRootModCount;
          ++expectedRootSize;
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
      ++expectedParentSize;
      ++expectedRootSize;
      ++expectedSeqModCount;
      ++expectedParentModCount;
      ++expectedRootModCount;
    }
    public String toString(){
      var builder=new StringBuilder("IntArrSeq.").append(checkedType.checked?"Checked":"Unchecked");
      switch(nestedType){
        case STACK:
          builder.append("Stack{").append(initialCapacity);
          break;
        case LIST:
          builder.append("List{").append(initialCapacity);
          break;
        case SUBLIST:
          builder.append("SubList{").append(rootPreAlloc).append(',').append(parentPreAlloc).append(',').append(parentPostAlloc).append(',').append(rootPostAlloc);
          break;
        default:
          throw new Error("Unknown nestedType "+nestedType);
      }
      return builder.append('}').toString();
    }
    void verifyStructuralIntegrity(){
        switch(nestedType){
          case STACK:
            if(checkedType.checked){
              Assertions.assertEquals(expectedRootModCount,FieldAndMethodAccessor.IntArrSeq.CheckedStack.modCount(root));
            }
            break;
          case LIST:
            if(checkedType.checked){
              Assertions.assertEquals(expectedRootModCount,FieldAndMethodAccessor.IntArrSeq.CheckedList.modCount(root));
            }
            break;
          case SUBLIST:
            OmniList.OfInt actualSeqParent;
            IntArrSeq actualSeqRoot;
            int actualSeqSize;
            OmniList.OfInt actualParentParent;
            IntArrSeq  actualParentRoot;
            int actualParentSize;
            if(checkedType.checked){
              actualSeqParent=(OmniList.OfInt)FieldAndMethodAccessor.IntArrSeq.CheckedSubList.parent(seq);
              actualSeqRoot=(IntArrSeq)FieldAndMethodAccessor.IntArrSeq.CheckedSubList.root(seq);
              actualSeqSize=FieldAndMethodAccessor.IntArrSeq.CheckedSubList.size(seq);
              actualParentParent=(OmniList.OfInt)FieldAndMethodAccessor.IntArrSeq.CheckedSubList.parent(parent);
              actualParentRoot=(IntArrSeq)FieldAndMethodAccessor.IntArrSeq.CheckedSubList.root(parent);
              actualParentSize=FieldAndMethodAccessor.IntArrSeq.CheckedSubList.size(parent);
              Assertions.assertEquals(expectedSeqModCount,FieldAndMethodAccessor.IntArrSeq.CheckedSubList.modCount(seq));
              Assertions.assertEquals(expectedParentModCount,FieldAndMethodAccessor.IntArrSeq.CheckedSubList.modCount(parent));
              Assertions.assertEquals(expectedRootModCount,FieldAndMethodAccessor.IntArrSeq.CheckedList.modCount(root));
            }else{
              actualSeqParent=(OmniList.OfInt)FieldAndMethodAccessor.IntArrSeq.UncheckedSubList.parent(seq);
              actualSeqRoot=(IntArrSeq)FieldAndMethodAccessor.IntArrSeq.UncheckedSubList.root(seq);
              actualSeqSize=FieldAndMethodAccessor.IntArrSeq.UncheckedSubList.size(seq);
              actualParentParent=(OmniList.OfInt)FieldAndMethodAccessor.IntArrSeq.UncheckedSubList.parent(parent);
              actualParentRoot=(IntArrSeq)FieldAndMethodAccessor.IntArrSeq.UncheckedSubList.root(parent);
              actualParentSize=FieldAndMethodAccessor.IntArrSeq.UncheckedSubList.size(parent);
            }
            Assertions.assertSame(root,actualSeqRoot);
            Assertions.assertSame(root,actualParentRoot);
            Assertions.assertSame(parent,actualSeqParent);
            Assertions.assertNull(actualParentParent);
            Assertions.assertEquals(expectedSeqSize,actualSeqSize);
            Assertions.assertEquals(expectedParentSize+parentPreAlloc+parentPostAlloc,actualParentSize);
            break;
          default:
            throw new Error("Unknown nestedType "+nestedType);
        }
        Assertions.assertEquals(expectedRootSize+parentPreAlloc+parentPostAlloc+rootPreAlloc+rootPostAlloc,FieldAndMethodAccessor.IntArrSeq.size(root));
    }
    void verifyFunctionalModification(){
       ++expectedSeqModCount;
       ++expectedParentModCount;
       ++expectedRootModCount;
    }
    void verifyBatchRemove(int numRemoved)
    {
      expectedSeqSize-=numRemoved;
      expectedParentSize-=numRemoved;
      expectedRootSize-=numRemoved;
    }
    void writeObject(ObjectOutputStream oos) throws IOException{
      switch(nestedType){
        case LIST:
          if(checkedType.checked){
            FieldAndMethodAccessor.IntArrSeq.CheckedList.writeObject(seq,oos);
          }else{
            FieldAndMethodAccessor.IntArrSeq.UncheckedList.writeObject(seq,oos);
          }
          break;
        case STACK:
          if(checkedType.checked){
            FieldAndMethodAccessor.IntArrSeq.CheckedStack.writeObject(seq,oos);
          }else{
            FieldAndMethodAccessor.IntArrSeq.UncheckedStack.writeObject(seq,oos);
          }
          break;
        case SUBLIST:
          if(checkedType.checked){
            FieldAndMethodAccessor.IntArrSeq.CheckedSubList.writeObject(seq,oos);
          }else{
            FieldAndMethodAccessor.IntArrSeq.UncheckedSubList.writeObject(seq,oos);
          }
          break;
        default:
          throw new Error("unknown nested type "+nestedType);
      }
    }
    void verifyRemoval(){
        ++expectedSeqModCount;
        ++expectedParentModCount;
        ++expectedRootModCount;
        --expectedSeqSize;
        --expectedParentSize;
        --expectedRootSize;
    }
  }
  static enum NestedType{
    LIST(true,true),
    STACK(true,false),
    SUBLIST(false,true);
    final boolean rootType;
    final boolean forwardIteration;
    NestedType(boolean rootType,boolean forwardIteration){
      this.rootType=rootType;
      this.forwardIteration=forwardIteration;
    }
  }
}
