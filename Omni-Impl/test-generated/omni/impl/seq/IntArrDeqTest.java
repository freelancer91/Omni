package omni.impl.seq;
import java.util.function.IntConsumer;
import java.util.Arrays;
import omni.util.ArrCopy;
import java.util.function.Consumer;
import java.io.IOException;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import omni.impl.IntInputTestArgType;
import omni.impl.IntOutputTestArgType;
import java.util.NoSuchElementException;
import omni.util.OmniArray;
import omni.api.OmniIterator;
import omni.impl.FunctionCallType;
import omni.impl.QueryCastType;
import java.io.File;
import org.junit.jupiter.api.Tag;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import omni.impl.seq.AbstractIntSeqMonitor.CheckedType;
import omni.impl.seq.AbstractIntSeqMonitor.PreModScenario;
import omni.impl.seq.AbstractIntSeqMonitor.SequenceLocation;
import omni.impl.seq.AbstractIntSeqMonitor.ItrRemoveScenario;
import omni.impl.seq.AbstractIntSeqMonitor.MonitoredFunctionGen;
import omni.impl.seq.AbstractIntSeqMonitor.MonitoredRemoveIfPredicateGen;
import omni.impl.seq.AbstractIntSeqMonitor.QueryTester;
import omni.impl.seq.AbstractIntSeqMonitor.ItrType;
import java.nio.file.Files;
import omni.impl.seq.AbstractIntSeqMonitor.SequenceVerificationItr;
import omni.api.OmniCollection;
import java.util.ArrayList;
import omni.api.OmniDeque;
@SuppressWarnings({"rawtypes","unchecked"})
@Tag("ArrDeqTest")
public class IntArrDeqTest{
  private static final java.util.concurrent.ExecutorService EXECUTORSERVICE=
  java.util.concurrent.Executors.newSingleThreadExecutor();
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
    private static final int MAX_TOSTRING_LENGTH=11;
  @Tag("MASSIVEtoString")
  @org.junit.jupiter.api.Test
  public void testMASSIVEtoString_void(){
    int seqLength=(OmniArray.MAX_ARR_SIZE/(MAX_TOSTRING_LENGTH+2))+1;
    int[] arr=new int[seqLength];
    for(int i=0;i<seqLength;++i){
      arr[i]=TypeConversionUtil.convertToint(1);
    }
    for(int tail=seqLength-1;tail>=(seqLength>>2);tail-=(seqLength>>1)){
      for(var checkedType:CheckedType.values()){
        int head=tail-(seqLength-1);
        if(head<0)
        {
          head+=seqLength;
        }
        var seqMonitor=new SeqMonitor(checkedType,head,arr,tail);
        String string=seqMonitor.seq.toString();
        Assertions.assertEquals('[',string.charAt(0));
        Assertions.assertEquals(']',string.charAt(string.length()-1));
        seqMonitor.verifyStructuralIntegrity();
        var verifyItr=seqMonitor.verifyPreAlloc();
        AbstractIntSeqMonitor.verifyLargeStr(string,0,seqLength,verifyItr);
      }
    }
  }
  @org.junit.jupiter.api.Test
  public void testremoveIf_Predicate(){
    for(var checkedType:CheckedType.values()){
//#IFNOT OfBoolean
//      if(checkedType.checked){
//        continue; //TODO remove
//      }
//#ENDIF
      for(var monitoredRemoveIfPredicateGen:MonitoredRemoveIfPredicateGen.values()){
        if(monitoredRemoveIfPredicateGen.expectedException==null || (checkedType.checked && monitoredRemoveIfPredicateGen.appliesToRoot)){
          for(var functionCallType:FunctionCallType.values()){
            submitTest(()->testremoveIf_PredicateHelper(checkedType,monitoredRemoveIfPredicateGen,0,0,functionCallType,0,0));
            for(int tmpSeqSize=1;tmpSeqSize<=101;tmpSeqSize+=10){
              final int seqSize=tmpSeqSize;
              final int inc=Math.max(1,seqSize/10);
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
                  for(int tmpHead=0;tmpHead<seqSize;tmpHead+=inc){
                    if(tmpHead!=0 && checkedType.checked) //TODO remove
                    {
                      continue;
                    }
                    final int head=tmpHead;
                    submitTest(()->testremoveIf_PredicateHelper(checkedType,monitoredRemoveIfPredicateGen,threshold,randSeed,functionCallType,seqSize,head));
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
  private static void testremoveIf_PredicateHelper(CheckedType checkedType,MonitoredRemoveIfPredicateGen monitoredRemoveIfPredicateGen,double threshold,long randSeed,final FunctionCallType functionCallType,int seqSize,int head
  ){
    var seqMonitor=new SeqMonitor(checkedType,seqSize,head,seqSize);
    initializeAscending(seqMonitor.seq.arr,head,seqSize);
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
    final var monitoredRemoveIfPredicate=monitoredRemoveIfPredicateGen.getMonitoredRemoveIfPredicate(seqMonitor,randSeed,numExpectedCalls,threshold);
    if(monitoredRemoveIfPredicateGen.expectedException==null || seqSize==0){
      seqMonitor.verifyRemoveIf(monitoredRemoveIfPredicate,functionCallType,numExpectedRemoved,clone);
      seqMonitor.verifyStructuralIntegrity();
      seqMonitor.verifyPreAlloc().skip(seqMonitor.expectedSeqSize);
      Assertions.assertEquals(numExpectedCalls,monitoredRemoveIfPredicate.callCounter);
      return;
    }else{
      Assertions.assertThrows(monitoredRemoveIfPredicateGen.expectedException,()->seqMonitor.verifyRemoveIf(monitoredRemoveIfPredicate,functionCallType,numExpectedRemoved,clone));
    }
    seqMonitor.verifyStructuralIntegrity();
    var verifyItr=seqMonitor.verifyPreAlloc();
    var cloneItr=clone.iterator();
    while(cloneItr.hasNext()){
      verifyItr.verifyLiteralIndexAndIterate(cloneItr.nextInt());
    }
  }
  @org.junit.jupiter.api.Test
  public void testreadandwriteObject(){
    for(var checkedType:CheckedType.values()){
      for(var monitoredFunctionGen:MonitoredFunctionGen.values()){
        if(monitoredFunctionGen.expectedException==null || (checkedType.checked && monitoredFunctionGen.appliesToRoot)){
          submitTest(()->testreadandwriteObjectHelper(checkedType,monitoredFunctionGen,0,0));
          for(int tmpSeqSize=1;tmpSeqSize<=10;++tmpSeqSize){
            int seqSize=tmpSeqSize;
            for(int tmpHead=0;tmpHead<seqSize;++tmpHead){
              int head=tmpHead;
              submitTest(()->testreadandwriteObjectHelper(checkedType,monitoredFunctionGen,seqSize,head));
            }
          }
        }
      }
    }
    completeAllTests();
  }
  private static void testreadandwriteObjectHelper(CheckedType checkedType,MonitoredFunctionGen monitoredFunctionGen,int seqSize,int head
  ){
    SeqMonitor seqMonitor=new SeqMonitor(checkedType,seqSize,head,seqSize);
    initializeAscending(seqMonitor.seq.arr,head,seqSize);
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
      seqMonitor.verifyPreAlloc().verifyAscending(0,seqSize);
      IntArrDeq readCol=null;
      try(var ois=new ObjectInputStream(new FileInputStream(file));){
        readCol=(IntArrDeq)ois.readObject();
      }catch(Exception e){
        Assertions.fail(e);
        return;
      }
      if(checkedType.checked){
        Assertions.assertEquals(0,((IntArrDeq.Checked)readCol).modCount);
      }
      if(seqSize==0){
        Assertions.assertEquals(-1,readCol.tail);
        Assertions.assertEquals(0,readCol.head);
        Assertions.assertSame(OmniArray.OfInt.DEFAULT_ARR,readCol.arr);
      }else{
        Assertions.assertEquals(0,readCol.head);
        Assertions.assertEquals(seqSize-1,readCol.tail);
        var arr=readCol.arr;
        for(int i=0;i<seqSize;++i){
          Assertions.assertEquals(TypeConversionUtil.convertToint(i),arr[i]);
        }
      }
    }else{
      Assertions.assertThrows(monitoredFunctionGen.expectedException,()->{
        try(var moos=monitoredFunctionGen.getMonitoredObjectOutputStream(file,seqMonitor);){
          seqMonitor.writeObject(moos);
        }
      });
    }
  }
  @org.junit.jupiter.api.Test
  public void testtoArray_IntFunction(){
    for(var checkedType:CheckedType.values()){
      for(var monitoredFunctionGen:MonitoredFunctionGen.values()){
        if(monitoredFunctionGen.expectedException==null || (checkedType.checked && monitoredFunctionGen.appliesToRoot)){
          submitTest(()->testtoArray_IntFunctionHelper(checkedType,monitoredFunctionGen,0,0));
          for(int tmpSeqSize=1;tmpSeqSize<=10;++tmpSeqSize){
            int seqSize=tmpSeqSize;
            for(int tmpHead=0;tmpHead<seqSize;++tmpHead){
              int head=tmpHead;
              submitTest(()->testtoArray_IntFunctionHelper(checkedType,monitoredFunctionGen,seqSize,head));
            }
          }
        }
      }
    }
    completeAllTests();
  }
  private static void testtoArray_IntFunctionHelper(CheckedType checkedType,MonitoredFunctionGen monitoredFunctionGen,int seqSize,int head){
    SeqMonitor seqMonitor=new SeqMonitor(checkedType,seqSize,head,seqSize);
    initializeAscending(seqMonitor.seq.arr,head,seqSize);
    var arrConstructor=monitoredFunctionGen.getMonitoredArrayConstructor(seqMonitor);
    if(monitoredFunctionGen.expectedException==null){
      var resultArr=seqMonitor.seq.toArray(arrConstructor);
      Assertions.assertEquals(seqSize,resultArr.length);
      Assertions.assertNotSame(resultArr,seqMonitor.seq.arr);
      if(seqSize!=0){
        var arr=seqMonitor.seq.arr;
        int index=seqMonitor.seq.head;
        int bound=arr.length;
        for(int i=0;i<seqSize;++i){
          Assertions.assertEquals((Object)resultArr[i],(Object)arr[index]);
          if(++index==bound){
            index=0;
          }
        }
      }
    }else{
      Assertions.assertThrows(monitoredFunctionGen.expectedException,()->seqMonitor.seq.toArray(arrConstructor));
    }
    seqMonitor.verifyStructuralIntegrity();
    var verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(seqSize);
    switch(monitoredFunctionGen){
      case ModSeq:
      case ThrowModSeq:
        verifyItr.verifyIllegalAdd();
      case NoThrow:
      case Throw:
        break;
      default:
        throw new Error("Unknown monitoredFunctionGen "+monitoredFunctionGen);
    }
  }
  @org.junit.jupiter.api.Test
  public void testforEach_Consumer(){
    for(var checkedType:CheckedType.values()){
      for(var monitoredFunctionGen:MonitoredFunctionGen.values()){
        if(monitoredFunctionGen.expectedException==null || (checkedType.checked && monitoredFunctionGen.appliesToRoot)){
          for(var functionCallType:FunctionCallType.values()){ 
            submitTest(()->testforEach_ConsumerHelper(checkedType,monitoredFunctionGen,functionCallType,0,0));
            for(int tmpSeqSize=1;tmpSeqSize<=10;++tmpSeqSize){
              int seqSize=tmpSeqSize;
              for(int tmpHead=0;tmpHead<seqSize;++tmpHead){
                int head=tmpHead;
                submitTest(()->testforEach_ConsumerHelper(checkedType,monitoredFunctionGen,functionCallType,seqSize,head));
              }
            }
          }
        }
      }
    }
    completeAllTests();
  }
  private static void testforEach_ConsumerHelper(CheckedType checkedType,MonitoredFunctionGen monitoredFunctionGen,FunctionCallType functionCallType,int seqSize,int head){
    SeqMonitor seqMonitor=new SeqMonitor(checkedType,seqSize,head,seqSize);
    initializeAscending(seqMonitor.seq.arr,head,seqSize);
    var monitoredConsumer=monitoredFunctionGen.getMonitoredConsumer(seqMonitor);
    if(monitoredFunctionGen.expectedException==null || seqSize==0){
      seqMonitor.forEach(monitoredConsumer,functionCallType);
      seqMonitor.verifyStructuralIntegrity();
      seqMonitor.verifyPreAlloc().verifyAscending(seqSize);
      if(seqSize!=0){
        var arr=seqMonitor.seq.arr;
        int index=seqMonitor.seq.head;
        int bound=arr.length;
        for(var encounteredValue:monitoredConsumer.encounteredValues){
          Assertions.assertEquals((Object)encounteredValue,(Object)arr[index]);
          if(++index==bound){
            index=0;
          }
        }
      }
    }else{
      Assertions.assertThrows(monitoredFunctionGen.expectedException,()->seqMonitor.forEach(monitoredConsumer,functionCallType));
      seqMonitor.verifyStructuralIntegrity();
    }
  }
  @org.junit.jupiter.api.Test
  public void testItrforEachRemaining_Consumer(){
    for(var itrType:new ItrType[]{ItrType.Itr,ItrType.DescendingItr}){
      for(var checkedType:CheckedType.values()){
        for(var preModScenario:PreModScenario.values()){
          if(preModScenario.expectedException==null || (checkedType.checked && preModScenario.appliesToRootItr)){
            for(var functionCallType:FunctionCallType.values()){
              for(var monitoredFunctionGen:MonitoredFunctionGen.values()){
                if(monitoredFunctionGen.expectedException==null || (checkedType.checked && monitoredFunctionGen.appliesToRootItr)){
                  submitTest(()->testItrforEachRemaining_ConsumerHelper(itrType,checkedType,preModScenario,functionCallType,monitoredFunctionGen,0,0));
                  for(int tmpSeqSize=1;tmpSeqSize<=10;++tmpSeqSize){
                    int seqSize=tmpSeqSize;
                    for(int tmpHead=0;tmpHead<seqSize;++tmpHead){
                      int head=tmpHead;
                      submitTest(()->testItrforEachRemaining_ConsumerHelper(itrType,checkedType,preModScenario,functionCallType,monitoredFunctionGen,seqSize,head));
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
  private static void testItrforEachRemaining_ConsumerHelper(ItrType itrType,CheckedType checkedType,PreModScenario preModScenario,FunctionCallType functionCallType,MonitoredFunctionGen monitoredFunctionGen,int seqSize,int head){
    SeqMonitor seqMonitor=new SeqMonitor(checkedType,seqSize,head,seqSize);
    initializeAscending(seqMonitor.seq.arr,head,seqSize);
    var itrMonitor=seqMonitor.getItrMonitor(itrType);
    seqMonitor.illegalAdd(preModScenario);
    var monitoredConsumer=monitoredFunctionGen.getMonitoredConsumer(itrMonitor);
    if(preModScenario.expectedException==null || seqSize==0){
      if(monitoredFunctionGen.expectedException==null || seqSize==0){
        itrMonitor.forEachRemaining(monitoredConsumer,functionCallType);
        itrMonitor.verifyIteratorState();
        seqMonitor.verifyStructuralIntegrity();
        seqMonitor.verifyPreAlloc().verifyAscending(seqSize);
        if(seqSize!=0){
          var arr=seqMonitor.seq.arr;
          if(itrType==ItrType.Itr){
            int index=seqMonitor.seq.head,bound=arr.length;
            for(var encounteredValue:monitoredConsumer.encounteredValues){
              Assertions.assertEquals((Object)encounteredValue,(Object)arr[index]);
              if(++index==bound){
                index=0;
              }
            }
          }else{
            int index=seqMonitor.seq.tail;
            for(var encounteredValue:monitoredConsumer.encounteredValues){
              Assertions.assertEquals((Object)encounteredValue,(Object)arr[index]);
              if(--index==-1){
                index=arr.length-1;
              }
            }
          }
        }
      }else{
        Assertions.assertThrows(monitoredFunctionGen.expectedException,()->itrMonitor.forEachRemaining(monitoredConsumer,functionCallType));
        itrMonitor.verifyIteratorState();
        seqMonitor.verifyStructuralIntegrity();
        //todo maybe verify the contents of the sequence and the iterated values?
      }
    }else{
      Assertions.assertThrows(preModScenario.expectedException,()->itrMonitor.forEachRemaining(monitoredConsumer,functionCallType));
      itrMonitor.verifyIteratorState();
      seqMonitor.verifyStructuralIntegrity();
      //todo maybe verify the contents of the sequence and the iterated values?
    }
  }
  @org.junit.jupiter.api.Test
  public void testsize_void(){
    for(var checkedType:CheckedType.values()){
      submitTest(()->testsize_voidHelper(checkedType,0,0));
      for(int tmpSeqSize=1;tmpSeqSize<=10;++tmpSeqSize){
        int seqSize=tmpSeqSize;
        for(int tmpHead=0;tmpHead<seqSize;++tmpHead){
          int head=tmpHead;
          submitTest(()->testsize_voidHelper(checkedType,seqSize,head));
        }
      }
    }
    completeAllTests();
  }
  private static void testsize_voidHelper(CheckedType checkedType,int seqSize,int head){
    var seqMonitor=new SeqMonitor(checkedType,seqSize,head,seqSize);
    initializeAscending(seqMonitor.seq.arr,head,seqSize);
    Assertions.assertEquals(seqSize,seqMonitor.seq.size());
    seqMonitor.verifyStructuralIntegrity();
    seqMonitor.verifyPreAlloc().verifyAscending(seqSize);
  }
  @org.junit.jupiter.api.Test
  public void testisEmpty_void(){
    for(var checkedType:CheckedType.values()){
      submitTest(()->testisEmpty_voidHelper(checkedType,0,0));
      for(int tmpSeqSize=1;tmpSeqSize<=10;++tmpSeqSize){
        int seqSize=tmpSeqSize;
        for(int tmpHead=0;tmpHead<seqSize;++tmpHead){
          int head=tmpHead;
          submitTest(()->testisEmpty_voidHelper(checkedType,seqSize,head));
        }
      }
    }
    completeAllTests();
  }
  private static void testisEmpty_voidHelper(CheckedType checkedType,int seqSize,int head){
    var seqMonitor=new SeqMonitor(checkedType,seqSize,head,seqSize);
    initializeAscending(seqMonitor.seq.arr,head,seqSize);
    Assertions.assertEquals(seqSize==0,seqMonitor.seq.isEmpty());
    seqMonitor.verifyStructuralIntegrity();
    seqMonitor.verifyPreAlloc().verifyAscending(seqSize);
  }
  @org.junit.jupiter.api.Test
  public void testItrremove_void(){
    for(var itrType:new ItrType[]{ItrType.Itr,ItrType.DescendingItr}){
      for(var checkedType:CheckedType.values()){
        for(var preModScenario:PreModScenario.values()){
          if(preModScenario.expectedException==null || (checkedType.checked && preModScenario.appliesToRootItr)){
            for(var removeScenario:ItrRemoveScenario.values()){
              if((checkedType.checked || removeScenario.expectedException==null)&&removeScenario!=ItrRemoveScenario.PostAdd && removeScenario!=ItrRemoveScenario.PostPrevious){
                for(var seqLocation:SequenceLocation.values()){
                  if(seqLocation.expectedException==null && (removeScenario!=ItrRemoveScenario.PostInit || seqLocation==SequenceLocation.BEGINNING)){
                    if(removeScenario.validWithEmptySeq){
                      submitTest(()->testItrremove_voidHelper(itrType,checkedType,preModScenario,removeScenario,seqLocation,0,0));
                    }
                    for(int tmpSeqSize=1;tmpSeqSize<=10;++tmpSeqSize){
                      int seqSize=tmpSeqSize;
                      for(int tmpHead=0;tmpHead<tmpSeqSize;++tmpHead){
                        int head=tmpHead;
                        submitTest(()->testItrremove_voidHelper(itrType,checkedType,preModScenario,removeScenario,seqLocation,seqSize,head));
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
  private static void testItrremove_voidHelper(ItrType itrType,CheckedType checkedType,PreModScenario preModScenario,ItrRemoveScenario removeScenario,SequenceLocation seqLocation,int seqSize,int head){
    SeqMonitor seqMonitor=new SeqMonitor(checkedType,seqSize,head,seqSize);
    initializeAscending(seqMonitor.seq.arr,head,seqSize);
    var itrMonitor=seqMonitor.getItrMonitor(seqLocation,itrType);
    int numBefore;
    switch(seqLocation){
      case BEGINNING:
        numBefore=0;
        break;
      case NEARBEGINNING:
        numBefore=seqSize>>2;
        break;
      case MIDDLE:
        numBefore=seqSize>>1;
        break;
      case NEAREND:
        numBefore=(seqSize>>2)*3;
        break;
      case END:
        numBefore=seqSize;
        break;
      default:
        throw new Error("Unknown seqLocation "+seqLocation);
    }
    boolean hasLastRet;
    switch(itrType){
      case Itr:
        hasLastRet=numBefore>0;
        break;
      case DescendingItr:
        numBefore=seqSize-numBefore;
        hasLastRet=numBefore<seqSize;
        break;
      default:
        throw new Error("Unknown itrType "+itrType);
    }
    int numAfter=seqSize-numBefore;
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
          }
        }else{
          if(itrType==ItrType.Itr){
            --numBefore;
          }else{
            --numAfter;
          }
        }
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
            throw new Error("Invalid test{removeScenario="+removeScenario+", seqSize="+seqSize+", itrType="+itrType+"} must be ListItr");
          }
        }else if(itrType==ItrType.Itr){
          --numBefore;
        }else{
          --numAfter;
        }
        itrMonitor.remove();
        hasLastRet=false;
        break;
      case PostInit:
        if(seqLocation!=SequenceLocation.BEGINNING){
          throw new Error("Invalid test{removeScenario="+removeScenario+", seqSize="+seqSize+", itrType="+itrType+"} must be ListItr");
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
        verifyItr=seqMonitor.verifyPreAlloc();
        switch(itrType){
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
        verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(seqSize);
      }
    }else{
      Assertions.assertThrows(removeScenario.expectedException,()->itrMonitor.remove());
      itrMonitor.verifyIteratorState();
      seqMonitor.verifyStructuralIntegrity();
      verifyItr=seqMonitor.verifyPreAlloc();
      switch(removeScenario){
        case PostInit:
          verifyItr.verifyAscending(seqSize);
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
  }
  @org.junit.jupiter.api.Test
  public void testItrnext_void(){
    for(var checkedType:CheckedType.values()){
      for(var preModScenario:PreModScenario.values()){
        if(preModScenario.expectedException==null || (preModScenario==PreModScenario.ModSeq && checkedType.checked)){
          for(var itrType:new ItrType[]{ItrType.Itr,ItrType.DescendingItr}){
            for(var outputArgType:IntOutputTestArgType.values()){
              submitTest(()->testItrnext_voidHelper(checkedType,preModScenario,outputArgType,itrType,0,0));
              for(int tmpSeqSize=1;tmpSeqSize<=10;++tmpSeqSize){
                final int seqSize=tmpSeqSize;
                for(int tmpHead=0;tmpHead<seqSize;++tmpHead){
                  final int head=tmpHead;
                  submitTest(()->testItrnext_voidHelper(checkedType,preModScenario,outputArgType,itrType,seqSize,head));
                }
              }
            }
          }
        }
      }
    }
    completeAllTests();
  }
  private static void testItrnext_voidHelper(CheckedType checkedType,PreModScenario preModScenario,IntOutputTestArgType outputArgType,ItrType itrType,int seqSize,int head){
    SeqMonitor seqMonitor=new SeqMonitor(checkedType,seqSize,head,seqSize);
    initializeAscending(seqMonitor.seq.arr,head,seqSize);
    var itrMonitor=seqMonitor.getItrMonitor(itrType);
    seqMonitor.illegalAdd(preModScenario);
    if(preModScenario.expectedException==null){
      if(itrType==ItrType.Itr){
        for(int i=0;itrMonitor.itr.hasNext();++i){
          itrMonitor.verifyNext(i,outputArgType);
          itrMonitor.verifyIteratorState();
          seqMonitor.verifyStructuralIntegrity();
        }
      }else{
        for(int i=seqSize;itrMonitor.itr.hasNext();){
          itrMonitor.verifyNext(--i,outputArgType);
          itrMonitor.verifyIteratorState();
          seqMonitor.verifyStructuralIntegrity();
        }
      }
      if(checkedType.checked){
        Assertions.assertThrows(NoSuchElementException.class,()->itrMonitor.verifyNext(0,outputArgType));
        itrMonitor.verifyIteratorState();
        seqMonitor.verifyStructuralIntegrity();
      }
    }else{
      Assertions.assertThrows(preModScenario.expectedException,()->itrMonitor.verifyNext(0,outputArgType));
      itrMonitor.verifyIteratorState();
      seqMonitor.verifyStructuralIntegrity();
    }
    var verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(seqSize);
    if(preModScenario==PreModScenario.ModSeq){
      verifyItr.verifyIllegalAdd();
    }
  }
  @org.junit.jupiter.api.Test
  public void testclear_void(){
    for(var checkedType:CheckedType.values()){
      submitTest(()->testclear_voidHelper(checkedType,0,0));
      for(int tmpSeqSize=1;tmpSeqSize<=10;++tmpSeqSize){
        final int seqSize=tmpSeqSize;
        for(int tmpHead=0;tmpHead<seqSize;++tmpHead){
          final int head=tmpHead;
          submitTest(()->testclear_voidHelper(checkedType,seqSize,head));
        }
      }
    }
    completeAllTests();
  }
  private static void testclear_voidHelper(CheckedType checkedType,int seqSize,int head){
    SeqMonitor seqMonitor=new SeqMonitor(checkedType,seqSize,head,seqSize);
    initializeAscending(seqMonitor.seq.arr,head,seqSize);
    seqMonitor.clear();
    seqMonitor.verifyStructuralIntegrity();
    Assertions.assertEquals(-1,seqMonitor.seq.tail);
  }
  @org.junit.jupiter.api.Test
  public void testtoArray_ObjectArray(){
    for(var checkedType:CheckedType.values()){
      for(int tmpSeqSize=0;tmpSeqSize<=15;tmpSeqSize+=5){
        final int seqSize=tmpSeqSize;
        for(int tmpParamArrSize=0;tmpParamArrSize<=seqSize+5;tmpParamArrSize+=5){
          final int paramArrSize=tmpParamArrSize;
          if(seqSize==0){
            submitTest(()->testtoArray_ObjectArrayHelper(checkedType,seqSize,0,paramArrSize));
          }else{
            for(int tmpHead=0;tmpHead<seqSize;++tmpHead){
              final int head=tmpHead;
              submitTest(()->testtoArray_ObjectArrayHelper(checkedType,seqSize,head,paramArrSize));
            }
          }
        }
      }
    }
    completeAllTests();
  }
  private static void testtoArray_ObjectArrayHelper(CheckedType checkedType,int seqSize,int head,int paramArrSize){
    SeqMonitor seqMonitor=new SeqMonitor(checkedType,seqSize,head,seqSize);
    initializeAscending(seqMonitor.seq.arr,head,seqSize);
    var paramArr=new Object[paramArrSize];
    for(int i=0;i<paramArrSize;++i)
    {
      paramArr[i]=Integer.valueOf(paramArrSize);
    }
    var resultArr=seqMonitor.seq.toArray(paramArr);
    seqMonitor.verifyStructuralIntegrity();
    seqMonitor.verifyPreAlloc().verifyAscending(seqSize);
    Assertions.assertNotSame(resultArr,seqMonitor.seq.arr);
    if(paramArrSize<seqSize){
      Assertions.assertNotSame(resultArr,paramArr);
    }else{
      Assertions.assertSame(resultArr,paramArr);
      if(paramArrSize>seqSize){
        Assertions.assertNull(resultArr[seqSize]);
        for(int i=seqSize+1;i<paramArrSize;++i){
          Assertions.assertEquals(Integer.valueOf(paramArrSize),resultArr[i]);
        }
      }
    }
    if(seqSize!=0){
      int[] arr;
      for(int i=0,j=seqMonitor.seq.head,bound=(arr=seqMonitor.seq.arr).length;i<seqSize;++i){
        Assertions.assertEquals((Object)resultArr[i],(Object)arr[j]);
        if(++j==bound){
          j=0;
        }
      }
    }
  }
  @org.junit.jupiter.api.Test
  public void testtoString_void(){
    ToStringAndHashCodeTest test=(checkedType,size,head
    )->{
      SeqMonitor seqMonitor=new SeqMonitor(checkedType,size,head,size);
      {
        initializeAscending(seqMonitor.seq.arr,head,size);
      }
      {
        var resultStr=seqMonitor.seq.toString();
        seqMonitor.verifyPreAlloc().verifyAscending(size);
        var arrList=new ArrayList<Object>();
        if(size!=0){
          for(int i=0,j=seqMonitor.seq.head,bound=seqMonitor.seq.arr.length;i<size;++i)
          {
            arrList.add(seqMonitor.seq.arr[j]);
            if(++j==bound)
            {
              j=0;
            }
          }
        }
        Assertions.assertEquals(arrList.toString(),resultStr);
      }
      seqMonitor.verifyStructuralIntegrity();
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testhashCode_void(){
    ToStringAndHashCodeTest test=(checkedType,size,head
    )->{
      SeqMonitor seqMonitor=new SeqMonitor(checkedType,size,head,size);
      {
        initializeAscending(seqMonitor.seq.arr,head,size);
      }
      {
        int resultHash=seqMonitor.seq.hashCode();
        seqMonitor.verifyPreAlloc().verifyAscending(size);
        int expectedHash=1;
        if(size!=0)
        {
          int[] arr;
          for(int i=0,j=seqMonitor.seq.head,bound=(arr=seqMonitor.seq.arr).length;i<size;++i){
            expectedHash=(expectedHash*31)+(arr[j]);
            if(++j==bound){
              j=0;
            }
          }
        }
        Assertions.assertEquals(expectedHash,resultHash);
      }
      seqMonitor.verifyStructuralIntegrity();
    };
    test.runTests();
  }
  interface ToStringAndHashCodeTest{
    void runTest(CheckedType checkedType,int size,int head
    );
    private void runTests(){
      for(var checkedType:CheckedType.values()){
        submitTest(()->runTest(checkedType,0,0));
        for(int tmpSeqSize=1;tmpSeqSize<=10;++tmpSeqSize){
          final int seqSize=tmpSeqSize;
          for(int tmpHead=0;tmpHead<seqSize;++tmpHead){
            final int head=tmpHead;
            submitTest(()->runTest(checkedType,seqSize,head));
          }
        }
      }
      completeAllTests();
    }
  }
  @org.junit.jupiter.api.Test
  public void testclone_void(){
    for(var checkedType:CheckedType.values()){
      submitTest(()->testclone_voidHelper(checkedType,0,0));
      for(int tmpSeqSize=1;tmpSeqSize<=10;++tmpSeqSize){
        final int seqSize=tmpSeqSize;
        for(int tmpHead=0;tmpHead<seqSize;++tmpHead){
          final int head=tmpHead;
          submitTest(()->testclone_voidHelper(checkedType,seqSize,head));
        }
      }
    }
    completeAllTests();
  }
  private static void testclone_voidHelper(CheckedType checkedType,int seqSize,int head){
    var seqMonitor=new SeqMonitor(checkedType,seqSize,head,seqSize);
    initializeAscending(seqMonitor.seq.arr,head,seqSize);
    var clone=(IntArrDeq)seqMonitor.seq.clone();
    if(seqMonitor.checkedType.checked){
      Assertions.assertEquals(0,((IntArrDeq.Checked)clone).modCount);
    }
    if((seqSize=seqMonitor.expectedSeqSize)==0)
    {
      Assertions.assertEquals(-1,clone.tail);
      Assertions.assertEquals(0,clone.head);
      Assertions.assertSame(OmniArray.OfInt.DEFAULT_ARR,clone.arr);
    }
    else
    {
      Assertions.assertEquals(0,clone.head);
      Assertions.assertEquals(seqSize-1,clone.tail);
      int[] origArr,cloneArr=clone.arr;
      for(int i=0,j=seqMonitor.seq.head,arrLength=(origArr=seqMonitor.seq.arr).length;i<seqSize;++i)
      {
        Assertions.assertEquals(origArr[j],cloneArr[i]);
        if(++j==arrLength)
        {
          j=0;
        }
      }
    }
  }
  @org.junit.jupiter.api.Test
  public void testtoArray_void(){
    OutputTest test=(checkedType,head,initialSize,outputArgType)->{
      SeqMonitor seqMonitor=new SeqMonitor(checkedType,initialSize,head,initialSize);
      initializeAscending(seqMonitor.seq.arr,head,initialSize);
      outputArgType.verifyToArray(seqMonitor.seq,initialSize);
      seqMonitor.verifyStructuralIntegrity();
      seqMonitor.verifyPreAlloc().verifyAscending(initialSize);
    };
    test.runTests(true);
  }
  @org.junit.jupiter.api.Test
  public void testelement_void(){
    OutputTest test=(checkedType,head,initialSize,outputArgType)->{
      SeqMonitor seqMonitor=new SeqMonitor(checkedType,initialSize,head,initialSize);
      initializeAscending(seqMonitor.seq.arr,head,initialSize);
      for(int i=0;i<initialSize;++i){
        outputArgType.verifyQueueElement(seqMonitor.seq,i);
        seqMonitor.verifyStructuralIntegrity();
        seqMonitor.removeFirst(i,outputArgType);
      }
      if(seqMonitor.checkedType.checked){
        Assertions.assertThrows(NoSuchElementException.class,()->outputArgType.verifyQueueElement(seqMonitor.seq,0));
        seqMonitor.verifyStructuralIntegrity();
      }
    };
    test.runTests(false);
  }
  @org.junit.jupiter.api.Test
  public void testgetFirst_void(){
    OutputTest test=(checkedType,head,initialSize,outputArgType)->{
      SeqMonitor seqMonitor=new SeqMonitor(checkedType,initialSize,head,initialSize);
      initializeAscending(seqMonitor.seq.arr,head,initialSize);
      for(int i=0;i<initialSize;++i){
        outputArgType.verifyDequeGetFirst(seqMonitor.seq,i);
        seqMonitor.verifyStructuralIntegrity();
        seqMonitor.removeFirst(i,outputArgType);
      }
      if(seqMonitor.checkedType.checked){
        Assertions.assertThrows(NoSuchElementException.class,()->outputArgType.verifyDequeGetFirst(seqMonitor.seq,0));
        seqMonitor.verifyStructuralIntegrity();
      }
    };
    test.runTests(false);
  }
  @org.junit.jupiter.api.Test
  public void testgetLast_void(){
    OutputTest test=(checkedType,head,initialSize,outputArgType)->{
      SeqMonitor seqMonitor=new SeqMonitor(checkedType,initialSize,head,initialSize);
      initializeAscending(seqMonitor.seq.arr,head,initialSize);
      for(int i=initialSize;--i>=0;){
        outputArgType.verifyDequeGetLast(seqMonitor.seq,i);
        seqMonitor.verifyStructuralIntegrity();
        seqMonitor.removeLast(i,outputArgType);
      }
      if(seqMonitor.checkedType.checked){
        Assertions.assertThrows(NoSuchElementException.class,()->outputArgType.verifyDequeGetLast(seqMonitor.seq,0));
        seqMonitor.verifyStructuralIntegrity();
      }
    };
    test.runTests(false);
  }
  @org.junit.jupiter.api.Test
  public void testpoll_void(){
    OutputTest test=(checkedType,head,initialSize,outputArgType)->{
      SeqMonitor seqMonitor=new SeqMonitor(checkedType,initialSize,head,initialSize);
      initializeAscending(seqMonitor.seq.arr,head,initialSize);
      for(int i=0;i<initialSize;++i)
      {
        seqMonitor.poll(i,outputArgType);
        seqMonitor.verifyStructuralIntegrity();
      }
      seqMonitor.poll(0,outputArgType);
      seqMonitor.verifyStructuralIntegrity();
    };
    test.runTests(true);
  }
  @org.junit.jupiter.api.Test
  public void testpeek_void(){
    OutputTest test=(checkedType,head,initialSize,outputArgType)->{
      SeqMonitor seqMonitor=new SeqMonitor(checkedType,initialSize,head,initialSize);
      initializeAscending(seqMonitor.seq.arr,head,initialSize);
      for(int i=0;i<initialSize;++i)
      {
        outputArgType.verifyPeek(seqMonitor.seq,initialSize-i,i);
        seqMonitor.verifyStructuralIntegrity();
        seqMonitor.removeFirst(i,outputArgType);
      }
      outputArgType.verifyPeek(seqMonitor.seq,0,0);
      seqMonitor.verifyStructuralIntegrity();
    };
    test.runTests(true);
  }
  @org.junit.jupiter.api.Test
  public void testpeekFirst_void(){
    OutputTest test=(checkedType,head,initialSize,outputArgType)->{
      SeqMonitor seqMonitor=new SeqMonitor(checkedType,initialSize,head,initialSize);
      initializeAscending(seqMonitor.seq.arr,head,initialSize);
      for(int i=0;i<initialSize;++i)
      {
        outputArgType.verifyDequePeekFirst(seqMonitor.seq,initialSize-i,i);
        seqMonitor.verifyStructuralIntegrity();
        seqMonitor.removeFirst(i,outputArgType);
      }
      outputArgType.verifyDequePeekFirst(seqMonitor.seq,0,0);
      seqMonitor.verifyStructuralIntegrity();
    };
    test.runTests(true);
  }
  @org.junit.jupiter.api.Test
  public void testpeekLast_void(){
    OutputTest test=(checkedType,head,initialSize,outputArgType)->{
      SeqMonitor seqMonitor=new SeqMonitor(checkedType,initialSize,head,initialSize);
      initializeAscending(seqMonitor.seq.arr,head,initialSize);
      for(int i=0;i<initialSize;++i)
      {
        outputArgType.verifyDequePeekLast(seqMonitor.seq,initialSize-i,initialSize-i-1);
        seqMonitor.verifyStructuralIntegrity();
        seqMonitor.removeLast(initialSize-i-1,outputArgType);
      }
      outputArgType.verifyDequePeekLast(seqMonitor.seq,0,0);
      seqMonitor.verifyStructuralIntegrity();
    };
    test.runTests(true);
  }
  @org.junit.jupiter.api.Test
  public void testremoveFirst_void(){
    OutputTest test=(checkedType,head,initialSize,outputArgType)->{
      SeqMonitor seqMonitor=new SeqMonitor(checkedType,initialSize,head,initialSize);
      initializeAscending(seqMonitor.seq.arr,head,initialSize);
      for(int i=0;i<initialSize;++i)
      {
        seqMonitor.removeFirst(i,outputArgType);
        seqMonitor.verifyStructuralIntegrity();
      }
      if(checkedType.checked)
      {
        Assertions.assertThrows(NoSuchElementException.class,()->seqMonitor.removeFirst(0,outputArgType));
        seqMonitor.verifyStructuralIntegrity();
      }
    };
    test.runTests(false);
  }
  @org.junit.jupiter.api.Test
  public void testremove_void(){
    OutputTest test=(checkedType,head,initialSize,outputArgType)->{
      SeqMonitor seqMonitor=new SeqMonitor(checkedType,initialSize,head,initialSize);
      initializeAscending(seqMonitor.seq.arr,head,initialSize);
      for(int i=0;i<initialSize;++i)
      {
        seqMonitor.queueRemove(i,outputArgType);
        seqMonitor.verifyStructuralIntegrity();
      }
      if(checkedType.checked)
      {
        Assertions.assertThrows(NoSuchElementException.class,()->seqMonitor.queueRemove(0,outputArgType));
        seqMonitor.verifyStructuralIntegrity();
      }
    };
    test.runTests(false);
  }
  @org.junit.jupiter.api.Test
  public void testpop_void(){
    OutputTest test=(checkedType,head,initialSize,outputArgType)->{
      SeqMonitor seqMonitor=new SeqMonitor(checkedType,initialSize,head,initialSize);
      initializeAscending(seqMonitor.seq.arr,head,initialSize);
      for(int i=0;i<initialSize;++i)
      {
        seqMonitor.pop(i,outputArgType);
        seqMonitor.verifyStructuralIntegrity();
      }
      if(checkedType.checked)
      {
        Assertions.assertThrows(NoSuchElementException.class,()->seqMonitor.pop(0,outputArgType));
        seqMonitor.verifyStructuralIntegrity();
      }
    };
    test.runTests(false);
  }
  @org.junit.jupiter.api.Test
  public void testremoveLast_void(){
    OutputTest test=(checkedType,head,initialSize,outputArgType)->{
      SeqMonitor seqMonitor=new SeqMonitor(checkedType,initialSize,head,initialSize);
      initializeAscending(seqMonitor.seq.arr,head,initialSize);
      for(int i=initialSize;--i>=0;)
      {
        seqMonitor.removeLast(i,outputArgType);
        seqMonitor.verifyStructuralIntegrity();
      }
      if(checkedType.checked)
      {
        Assertions.assertThrows(NoSuchElementException.class,()->seqMonitor.removeLast(0,outputArgType));
        seqMonitor.verifyStructuralIntegrity();
      }
    };
    test.runTests(false);
  }
  @org.junit.jupiter.api.Test
  public void testpollFirst_void(){
    OutputTest test=(checkedType,head,initialSize,outputArgType)->{
      SeqMonitor seqMonitor=new SeqMonitor(checkedType,initialSize,head,initialSize);
      initializeAscending(seqMonitor.seq.arr,head,initialSize);
      for(int i=0;i<initialSize;++i)
      {
        seqMonitor.pollFirst(i,outputArgType);
        seqMonitor.verifyStructuralIntegrity();
      }
      seqMonitor.pollFirst(0,outputArgType);
      seqMonitor.verifyStructuralIntegrity();
    };
    test.runTests(true);
  }
  @org.junit.jupiter.api.Test
  public void testpollLast_void(){
    OutputTest test=(checkedType,head,initialSize,outputArgType)->{
      SeqMonitor seqMonitor=new SeqMonitor(checkedType,initialSize,head,initialSize);
      initializeAscending(seqMonitor.seq.arr,head,initialSize);
      for(int i=initialSize;--i>=0;)
      {
        seqMonitor.pollLast(i,outputArgType);
        seqMonitor.verifyStructuralIntegrity();
      }
      seqMonitor.pollLast(0,outputArgType);
      seqMonitor.verifyStructuralIntegrity();
    };
    test.runTests(true);
  }
  @org.junit.jupiter.api.Test
  public void testConstructor_void(){
    for(var checkedType:CheckedType.values()){
      submitTest(()->{
        IntArrDeq deq;
        if(checkedType.checked){
          Assertions.assertEquals(0,((IntArrDeq.Checked)(deq=new IntArrDeq.Checked())).modCount);
        }else{
          deq=new IntArrDeq();
        }
        Assertions.assertEquals(-1,deq.tail);
        Assertions.assertEquals(0,deq.head);
        Assertions.assertSame(OmniArray.OfInt.DEFAULT_ARR,deq.arr);
      });
    }
    completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testConstructor_int(){
    for(var checkedType:CheckedType.values()){
      for(int tmpInitialCapacity=0;tmpInitialCapacity<=15;tmpInitialCapacity+=5){
        final int initialCapacity=tmpInitialCapacity;
        submitTest(()->{
          IntArrDeq deq;
          if(checkedType.checked){
            Assertions.assertEquals(0,((IntArrDeq.Checked)(deq=new IntArrDeq.Checked(initialCapacity))).modCount);
          }else{
            deq=new IntArrDeq(initialCapacity);
          }
          Assertions.assertEquals(-1,deq.tail);
          Assertions.assertEquals(0,deq.head);
          switch(initialCapacity){
            case 0:
              Assertions.assertNull(deq.arr);
              break;
            case OmniArray.DEFAULT_ARR_SEQ_CAP:
              Assertions.assertSame(OmniArray.OfInt.DEFAULT_ARR,deq.arr);
              break;
            default:
              Assertions.assertEquals(initialCapacity,deq.arr.length);
          }
        });
      }
    }
    completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testadd_val(){
    InputTest test=(checkedType,initialCapacity,head,initialSize,inputArgType)->
    {
      SeqMonitor seqMonitor=new SeqMonitor(checkedType,initialCapacity,head,initialSize);
      initializeAscending(seqMonitor.seq.arr,head,initialSize);
      for(int i=0;i<100;++i)
      {
        Assertions.assertTrue(seqMonitor.add(i,inputArgType));
        seqMonitor.verifyStructuralIntegrity();
      }
      var verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(initialSize);
      verifyItr.verifyAscending(inputArgType,100);
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testoffer_val(){
    InputTest test=(checkedType,initialCapacity,head,initialSize,inputArgType)->
    {
      SeqMonitor seqMonitor=new SeqMonitor(checkedType,initialCapacity,head,initialSize);
      initializeAscending(seqMonitor.seq.arr,head,initialSize);
      for(int i=0;i<100;++i)
      {
        Assertions.assertTrue(seqMonitor.offer(i,inputArgType));
        seqMonitor.verifyStructuralIntegrity();
      }
      var verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(initialSize);
      verifyItr.verifyAscending(inputArgType,100);
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testofferLast_val(){
    InputTest test=(checkedType,initialCapacity,head,initialSize,inputArgType)->
    {
      SeqMonitor seqMonitor=new SeqMonitor(checkedType,initialCapacity,head,initialSize);
      initializeAscending(seqMonitor.seq.arr,head,initialSize);
      for(int i=0;i<100;++i)
      {
        Assertions.assertTrue(seqMonitor.offerLast(i,inputArgType));
        seqMonitor.verifyStructuralIntegrity();
      }
      var verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(initialSize);
      verifyItr.verifyAscending(inputArgType,100);
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testofferFirst_val(){
    InputTest test=(checkedType,initialCapacity,head,initialSize,inputArgType)->
    {
      SeqMonitor seqMonitor=new SeqMonitor(checkedType,initialCapacity,head,initialSize);
      initializeAscending(seqMonitor.seq.arr,head,initialSize);
      for(int i=0;i<100;++i)
      {
        Assertions.assertTrue(seqMonitor.offerFirst(i,inputArgType));
        seqMonitor.verifyStructuralIntegrity();
      }
      var verifyItr=seqMonitor.verifyPreAlloc();
      verifyItr.verifyDescending(inputArgType,100);
      verifyItr.verifyAscending(initialSize);
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testpush_val(){
    InputTest test=(checkedType,initialCapacity,head,initialSize,inputArgType)->
    {
      SeqMonitor seqMonitor=new SeqMonitor(checkedType,initialCapacity,head,initialSize);
      initializeAscending(seqMonitor.seq.arr,head,initialSize);
      for(int i=0;i<100;++i)
      {
        seqMonitor.push(i,inputArgType);
        seqMonitor.verifyStructuralIntegrity();
        var verifyItr=seqMonitor.verifyPreAlloc();
        verifyItr.verifyDescending(inputArgType,i+1);
        verifyItr.verifyAscending(initialSize);
      }
      var verifyItr=seqMonitor.verifyPreAlloc();
      verifyItr.verifyDescending(inputArgType,100);
      verifyItr.verifyAscending(initialSize);
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testaddFirst_val(){
    InputTest test=(checkedType,initialCapacity,head,initialSize,inputArgType)->
    {
      SeqMonitor seqMonitor=new SeqMonitor(checkedType,initialCapacity,head,initialSize);
      initializeAscending(seqMonitor.seq.arr,head,initialSize);
      for(int i=0;i<100;++i)
      {
        seqMonitor.addFirst(i,inputArgType);
        seqMonitor.verifyStructuralIntegrity();
      }
      var verifyItr=seqMonitor.verifyPreAlloc();
      verifyItr.verifyDescending(inputArgType,100);
      verifyItr.verifyAscending(initialSize);
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testaddLast_val(){
    InputTest test=(checkedType,initialCapacity,head,initialSize,inputArgType)->
    {
      SeqMonitor seqMonitor=new SeqMonitor(checkedType,initialCapacity,head,initialSize);
      initializeAscending(seqMonitor.seq.arr,head,initialSize);
      for(int i=0;i<100;++i)
      {
        seqMonitor.addLast(i,inputArgType);
        seqMonitor.verifyStructuralIntegrity();
      }
      var verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(initialSize);
      verifyItr.verifyAscending(inputArgType,100);
    };
    test.runTests();
  }
  interface OutputTest{
    void runTest(CheckedType checkedType,int head,int initialSize,IntOutputTestArgType outputArgType);
    private void runTests(boolean allowEmpty){
      for(var checkedType:CheckedType.values()){
        for(var outputArgType:IntOutputTestArgType.values()){
          if(allowEmpty || checkedType.checked){
            submitTest(()->runTest(checkedType,0,0,outputArgType));
          }
          for(int tmpSeqSize=1;tmpSeqSize<=10;++tmpSeqSize){
            final int seqSize=tmpSeqSize;
            for(int tmpHead=0;tmpHead<seqSize;++tmpHead){
              final int head=tmpHead;
              submitTest(()->runTest(checkedType,head,seqSize,outputArgType));
            }
          }
        }
      }
      completeAllTests();
    }
  }
  interface InputTest{
    void runTest(CheckedType checkedType,int initialCapacity,int head,int initialSize,IntInputTestArgType inputType);
    private void runTests(){
      for(var checkedType:CheckedType.values()){
        for(var inputArgType:IntInputTestArgType.values()){
          submitTest(()->runTest(checkedType,0,0,0,inputArgType));
          for(int tmpCapacity=1;tmpCapacity<=15;++tmpCapacity){
            final int capacity=tmpCapacity;
            submitTest(()->runTest(checkedType,capacity,0,0,inputArgType));
            for(int tmpSeqSize=1;tmpSeqSize<=capacity;++tmpSeqSize){
              final int seqSize=tmpSeqSize;
              for(int tmpHead=0;tmpHead<seqSize;++tmpHead){
                final int head=tmpHead;
                submitTest(()->runTest(checkedType,capacity,head,seqSize,inputArgType));
              }
            }
          }
        }
      }
      completeAllTests();
    }
  }
  @org.junit.jupiter.api.Test
  public void testremoveLastOccurrence_val(){
    QueryTest test=(checkedType,argType,queryCastType,seqLocation,seqSize,head
    )->{
      SeqMonitor seqMonitor=new SeqMonitor(checkedType,seqSize,head,seqSize);
      if(seqSize!=0){
        initializeArrayForQuery(false,seqMonitor.seq.arr,head,seqSize,argType,seqLocation);
      }
      Assertions.assertEquals(seqLocation!=SequenceLocation.IOBHI,argType.invokeremoveLastOccurrence(seqMonitor,queryCastType));
      seqMonitor.verifyStructuralIntegrity();
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testremoveVal_val(){
    QueryTest test=(checkedType,argType,queryCastType,seqLocation,seqSize,head
    )->{
      SeqMonitor seqMonitor=new SeqMonitor(checkedType,seqSize,head,seqSize);
      if(seqSize!=0){
        initializeArrayForQuery(true,seqMonitor.seq.arr,head,seqSize,argType,seqLocation);
      }
      Assertions.assertEquals(seqLocation!=SequenceLocation.IOBHI,argType.invokeremoveVal(seqMonitor,queryCastType));
      seqMonitor.verifyStructuralIntegrity();
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testremoveFirstOccurrence_val(){
    QueryTest test=(checkedType,argType,queryCastType,seqLocation,seqSize,head
    )->{
      SeqMonitor seqMonitor=new SeqMonitor(checkedType,seqSize,head,seqSize);
      if(seqSize!=0){
        initializeArrayForQuery(true,seqMonitor.seq.arr,head,seqSize,argType,seqLocation);
      }
      Assertions.assertEquals(seqLocation!=SequenceLocation.IOBHI,argType.invokeremoveFirstOccurrence(seqMonitor,queryCastType));
      seqMonitor.verifyStructuralIntegrity();
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testsearch_val(){
    QueryTest test=(checkedType,argType,queryCastType,seqLocation,seqSize,head
    )->{
      SeqMonitor seqMonitor=new SeqMonitor(checkedType,seqSize,head,seqSize);
      int expectedIndex;
      if(seqSize!=0){
        expectedIndex=initializeArrayForQuery(true,seqMonitor.seq.arr,head,seqSize,argType,seqLocation);
      }else{
        expectedIndex=-1;
      }
      if(seqLocation==SequenceLocation.IOBHI){
        expectedIndex=-1;
      }
      Assertions.assertEquals(expectedIndex,argType.invokesearch(seqMonitor,queryCastType));
      seqMonitor.verifyStructuralIntegrity();
    };
    test.runTests();
  }
  @org.junit.jupiter.api.Test
  public void testcontains_val(){
    QueryTest test=(checkedType,argType,queryCastType,seqLocation,seqSize,head
    )->{
      SeqMonitor seqMonitor=new SeqMonitor(checkedType,seqSize,head,seqSize);
      if(seqSize!=0){
        initializeArrayForQuery(true,seqMonitor.seq.arr,head,seqSize,argType,seqLocation);
      }
      Assertions.assertEquals(seqLocation!=SequenceLocation.IOBHI,argType.invokecontains(seqMonitor,queryCastType));
      seqMonitor.verifyStructuralIntegrity();
    };
    test.runTests();
  }
  interface QueryTest
  {
    void runTest(CheckedType checkedType,QueryTester argType,QueryCastType queryCastType,SequenceLocation seqLocation,int seqSize,int head
    );
    private void runTests(CheckedType checkedType,QueryTester argType,QueryCastType queryCastType,SequenceLocation seqLocation,int seqSize
    ){
      if(seqSize==0)
      {
        submitTest(()->runTest(checkedType,argType,queryCastType,seqLocation,seqSize,0
        ));
      }
      else
      {
        for(int tmpHead=0;tmpHead<seqSize;++tmpHead){
          final int head=tmpHead;
          submitTest(()->runTest(checkedType,argType,queryCastType,seqLocation,seqSize,head
          ));
        }
      }
    }
    private void runTests(){
      for(var checkedType:CheckedType.values()){
        for(var seqLocation:SequenceLocation.values()){
          if(seqLocation==SequenceLocation.IOBLO){
            continue;
          }
          for(int tmpSeqSize=0;tmpSeqSize<=10;++tmpSeqSize){
            final int seqSize=tmpSeqSize;
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
                  runTests(checkedType,argType,queryCastType,seqLocation,seqSize);
                }
              }
            }
          }
        }
      }
      completeAllTests();
    }
  }
  private static void initializeAscending(int[] arr,int head,int size)
  {
    if(size!=0)
    {
      for(int i=0,arrLength=arr.length;i<size;++i)
      {
        arr[head]=TypeConversionUtil.convertToint(i);
        if(++head==arrLength)
        {
          head=0;
        }
      }
    }
  }
  private static int initializeArrayForQuery(boolean forwardIteration,int[] arr,int head,int size,QueryTester argType,SequenceLocation seqLocation
  ){
    int capacity=arr.length;
    switch(seqLocation){
      case IOBHI:
        {
          for(int i=0,dst=head;i<size;++i)
          {
            argType.setNotEqualsVal(arr,dst);
            if(++dst==capacity)
            {
              dst=0;
            }
          }
        }
        return size;
      case BEGINNING:
        {
          int dst;
          argType.setEqualsVal(arr,dst=head);
          for(int i=1;i<size;++i)
          {
            if(++dst==capacity){
              dst=0;
            }
            argType.setNotEqualsVal(arr,dst);
          }
        }
        if(forwardIteration)
        {
          return 1;
        }
        else
        {
          return size;
        }
      case NEARBEGINNING:
        {
          int i,dst,bound;
          for(i=0,dst=head,bound=size>>2;i<bound;++i)
          {
            argType.setNotEqualsVal(arr,dst);
            if(++dst==capacity)
            {
              dst=0;
            }
          }
          argType.setEqualsVal(arr,dst);
          for(;++i<size;)
          {
            if(++dst==capacity)
            {
              dst=0;
            }
            argType.setNotEqualsVal(arr,dst);
          }
        }
        if(forwardIteration)
        {
          return (size>>2)+1;
        }
        else
        {
          return size-((size>>2));
        }
      case MIDDLE:
        {
          int i,dst,bound;
          for(i=0,dst=head,bound=size>>1;i<bound;++i)
          {
            argType.setNotEqualsVal(arr,dst);
            if(++dst==capacity)
            {
              dst=0;
            }
          }
          argType.setEqualsVal(arr,dst);
          for(;++i<size;)
          {
            if(++dst==capacity)
            {
              dst=0;
            }
            argType.setNotEqualsVal(arr,dst);
          }
        }
        if(forwardIteration)
        {
          return (size>>1)+1;
        }
        else
        {
          return size-(size>>1);
        }
      case NEAREND:
        {
          int i,dst,bound;
          for(i=0,dst=head,bound=(size>>2)*3;i<bound;++i)
          {
            argType.setNotEqualsVal(arr,dst);
            if(++dst==capacity)
            {
              dst=0;
            }
          }
          argType.setEqualsVal(arr,dst);
          for(;++i<size;)
          {
            if(++dst==capacity)
            {
              dst=0;
            }
            argType.setNotEqualsVal(arr,dst);
          }
        }
        if(forwardIteration)
        {
          return ((size>>2)*3)+1;
        }
        else
        {
          return (size)-(size>>2)*3;
        }
      case END:
        {
          int i,dst,bound;
          for(i=0,dst=head,bound=size-1;i<bound;++i)
          {
            argType.setNotEqualsVal(arr,dst);
            if(++dst==capacity){
              dst=0;
            }
          }
          argType.setEqualsVal(arr,dst);
        }
        if(forwardIteration)
        {
          return size;
        }
        else
        {
          return 1;
        }
      default:
        throw new Error("Unknown seqLocation "+seqLocation);
    }
  }
  private static class SeqMonitor extends AbstractIntSeqMonitor<IntArrDeq>{
    //private int expectedTail;
    //private int expectedHead;
    SeqMonitor(CheckedType checkedType,int head,int[] arr,int tail){
      super(checkedType);
      this.seq=checkedType.checked?new IntArrDeq.Checked(head,arr,tail):new IntArrDeq(head,arr,tail);
      //this.expectedHead=head;
      //this.expectedTail=tail;
      if(tail==-1){
        this.expectedSeqSize=0;
      }else{
        this.expectedSeqSize=tail-head+1;
        if(expectedSeqSize<=0){
          this.expectedSeqSize+=arr.length;
        }
      }
    }
    SeqMonitor(CheckedType checkedType,int capacity,int head,int size)
    {
      super(checkedType);
      if(size==0)
      {
        this.seq=checkedType.checked?new IntArrDeq.Checked(capacity):new IntArrDeq(capacity);
      }
      else
      {
        Assertions.assertTrue(size<=capacity,"The expression (size="+size+")<=(capacity="+capacity+") evaluates to false");
        int tail=head+size-1;
        int[] arr=new int[capacity];
        if(tail>=capacity)
        {
          tail-=capacity;
        }
        this.seq=checkedType.checked?new IntArrDeq.Checked(head,arr,tail):new IntArrDeq(head,arr,tail);
        this.expectedSeqSize=size;
      }
    }
    SeqMonitor(CheckedType checkedType,int initialCapacity){
      super(checkedType);
      this.seq=checkedType.checked?new IntArrDeq.Checked(initialCapacity):new IntArrDeq(initialCapacity);
      //this.expectedTail=-1;
    }
    SeqMonitor(CheckedType checkedType){
      super(checkedType);
      this.seq=checkedType.checked?new IntArrDeq.Checked():new IntArrDeq();
      //this.expectedTail=-1;
    }
    AbstractItrMonitor getItrMonitor(){
      if(checkedType.checked){
        return new CheckedAscendingItrMonitor();
      }
      return new UncheckedAscendingItrMonitor();
    }
    class UncheckedAscendingItrMonitor extends AbstractIntSeqMonitor.AbstractItrMonitor{
      int expectedCursor;
      UncheckedAscendingItrMonitor(ItrType itrType,OmniIterator.OfInt itr,int cursor){
        super(itrType,itr,expectedSeqModCount);
        this.expectedCursor=cursor;
      }
      UncheckedAscendingItrMonitor(){
        super(ItrType.Itr,seq.iterator(),expectedSeqModCount);
        this.expectedCursor=seq.tail==-1?-1:seq.head;
      }
      void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
        int expectedCursor=this.expectedCursor;
        if(functionCallType==FunctionCallType.Boxed){
          itr.forEachRemaining((Consumer)action);
        }else
        {
          itr.forEachRemaining((IntConsumer)action);
        }
        if(expectedCursor!=-1){
          this.expectedCursor=-1;
        }
      }
      SeqMonitor getSeqMonitor(){
        return SeqMonitor.this;
      }
      int getNewCursor(){
        return expectedCursor==seq.tail?-1:expectedCursor+1==seq.arr.length?0:expectedCursor+1;
      }
      void verifyNext(int expectedVal,IntOutputTestArgType outputType){
        int newCursor=getNewCursor();
        outputType.verifyItrNext(itr,expectedVal);
        expectedCursor=newCursor;
      }
      void iterateForward(){
        int newCursor=getNewCursor();
        itr.nextInt();
        expectedCursor=newCursor;
      }
      int getRemoveCursor(){
        int newCursor;
        switch(newCursor=expectedCursor){
          case 0:
            int arrBound;
            if(seq.tail<(arrBound=seq.arr.length-1)-seq.head){
              newCursor=arrBound;
            }
            break;
          default:
            int tail,head;
            arrBound=newCursor-1;
            if((tail=seq.tail)<(head=seq.head)){
              int headDist;
              if((headDist=arrBound-head)>=0){
                if(headDist>seq.arr.length-arrBound+tail){
                  newCursor=arrBound;
                }
              }else if(tail-arrBound<=seq.arr.length-head+arrBound){
                newCursor=arrBound;
              }
            }else if(arrBound-head>tail-arrBound){
              newCursor=arrBound;
            }
          case -1:
        }
        return newCursor;
      }
      void remove(){
        int newCursor=getRemoveCursor();
        itr.remove();
        verifyRemoval();
        this.expectedCursor=newCursor;
      }
      void verifyIteratorState(){
        Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.IntArrDeq.AscendingItr.cursor(itr));
        Assertions.assertSame(seq,FieldAndMethodAccessor.IntArrDeq.AscendingItr.root(itr));
      }
    }
    class UncheckedDescendingItrMonitor extends UncheckedAscendingItrMonitor{
      UncheckedDescendingItrMonitor(){
        super(ItrType.DescendingItr,seq.descendingIterator(),seq.tail);
      }
      int getNewCursor(){
        return expectedCursor==seq.head?-1:expectedCursor==0?seq.arr.length-1:expectedCursor-1;
      }
      int getRemoveCursor(){
        int newCursor;
        if((newCursor=expectedCursor)!=-1){
          int head,tail;
          if((tail=seq.tail)<(head=seq.head)){
            int arrBound;
            if(expectedCursor==(arrBound=seq.arr.length-1)){
              if(tail>arrBound-head){
                newCursor=0;
              }
            }else{
              int headDist,tmp;
              if((headDist=(tmp=newCursor+1)-head)>0){
                if(headDist<=arrBound-tmp+tail+1){
                  newCursor=tmp;
                }
              }else if(tail-tmp>arrBound-head+tmp+1){
                newCursor=tmp;
              }
            }
          }else{
            int tmp;
            if(tail-(tmp=newCursor+1)>tmp-head){
              newCursor=tmp;
            }
          }
        }
        return newCursor;
      }
    }
    class CheckedAscendingItrMonitor extends UncheckedAscendingItrMonitor{
      int expectedLastRet;
      CheckedAscendingItrMonitor(ItrType itrType,OmniIterator.OfInt itr,int cursor){
        super(itrType,itr,cursor);
        this.expectedLastRet=-1;
      }
      CheckedAscendingItrMonitor(){
        super(ItrType.Itr,seq.iterator(),seq.tail==-1?-1:seq.head);
        this.expectedLastRet=-1;
      }
      void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
        int tail=seq.tail;
        int expectedCursor=this.expectedCursor;
        if(functionCallType==FunctionCallType.Boxed){
          itr.forEachRemaining((Consumer)action);
        }else
        {
          itr.forEachRemaining((IntConsumer)action);
        }
        if(expectedCursor!=-1){
          this.expectedLastRet=tail;
          this.expectedCursor=-1;
        }
      }
      void verifyNext(int expectedVal,IntOutputTestArgType outputType){
        int newCursor=getNewCursor();
        outputType.verifyItrNext(itr,expectedVal);
        expectedLastRet=expectedCursor;
        expectedCursor=newCursor;
      }
      void iterateForward(){
        int newCursor=getNewCursor();
        itr.nextInt();
        expectedLastRet=expectedCursor;
        expectedCursor=newCursor;
      }
      int getRemoveCursor(){
        int newCursor=expectedCursor;
        if(newCursor==-1){
          return -1;
        }
        int head,tail;
        switch(Integer.signum((tail=seq.tail)-(head=seq.head)))
        {
          case -1:
          {
            int headDist;
            if((headDist=expectedLastRet-head)>=0){
              if(headDist>seq.arr.length-expectedLastRet+tail){
                newCursor=expectedLastRet;
              }
            }else if(tail-expectedLastRet<=seq.arr.length-head+expectedLastRet){
              newCursor=expectedLastRet;
            }
            break;
          }
          default:
          {
            if(expectedLastRet-head>tail-expectedLastRet){
              newCursor=expectedLastRet;
            }
          }
          case 0:
        }
        return newCursor;
      }
      void remove(){
        int newCursor=getRemoveCursor();
        itr.remove();
        verifyRemoval();
        this.expectedCursor=newCursor;
        this.expectedLastRet=-1;
        ++this.expectedItrModCount;
      }
      void verifyIteratorState(){
        Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.IntArrDeq.Checked.AscendingItr.modCount(itr));
        Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.IntArrDeq.Checked.AscendingItr.cursor(itr));
        Assertions.assertEquals(expectedLastRet,FieldAndMethodAccessor.IntArrDeq.Checked.AscendingItr.lastRet(itr));
        Assertions.assertSame(seq,FieldAndMethodAccessor.IntArrDeq.Checked.AscendingItr.root(itr));
      }
    }
    class CheckedDescendingItrMonitor extends CheckedAscendingItrMonitor{
      CheckedDescendingItrMonitor(){
        super(ItrType.DescendingItr,seq.descendingIterator(),expectedSeqModCount);
        this.expectedCursor=seq.tail;
        this.expectedLastRet=-1;
      }
      void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
        int expectedCursor=this.expectedCursor;
        int head=seq.head;
        if(functionCallType==FunctionCallType.Boxed){
          itr.forEachRemaining((Consumer)action);
        }else
        {
          itr.forEachRemaining((IntConsumer)action);
        }
        if(expectedCursor!=-1){
          this.expectedLastRet=head;
          this.expectedCursor=-1;
        }
      }
      int getNewCursor(){
        return expectedCursor==seq.head?-1:expectedCursor==0?seq.arr.length-1:expectedCursor-1;
      }
      int getRemoveCursor(){
        int newCursor=expectedCursor;
        if(newCursor==-1){
          return -1;
        }
        int head,tail;
        switch(Integer.signum((tail=seq.tail)-(head=seq.head)))
        {
          case -1:
          {
            int headDist;
            if((headDist=expectedLastRet-head)>=0){
              if(headDist<=seq.arr.length-expectedLastRet+tail){
                newCursor=expectedLastRet;
              }
            }else if(tail-expectedLastRet>seq.arr.length-head+expectedLastRet){
              newCursor=expectedLastRet;
            }
            break;
          }
          default:
          {
            if(expectedLastRet-head<tail-expectedLastRet){
              newCursor=expectedLastRet;
            }
          }
          case 0:
        }
        return newCursor;
      }
    }
    AbstractItrMonitor getDescendingItrMonitor(){
      if(checkedType.checked){
        return new CheckedDescendingItrMonitor();
      }
      return new UncheckedDescendingItrMonitor();
    }
    SequenceVerificationItr verifyPreAlloc(int expectedVal){
      return verifyPreAlloc();
    }
    SequenceVerificationItr verifyPreAlloc(){
      return new ArrDeqVerificationItr(seq.head,this);
    }
    private static class ArrDeqVerificationItr extends SequenceVerificationItr{
      int currIndex;
      final SeqMonitor seqMonitor;
      ArrDeqVerificationItr(int currIndex,SeqMonitor seqMonitor){
        this.currIndex=currIndex;
        this.seqMonitor=seqMonitor;
      }
      SequenceVerificationItr verifyNaturalAscending(int v,IntInputTestArgType inputArgType,int length){
        return verifyAscending(v,inputArgType,length);
      }
      @Override SequenceVerificationItr verifyPostAlloc(int expectedVal){
        return this;
      }
      @Override void verifyLiteralIndexAndIterate(int val){
        Assertions.assertEquals(val,seqMonitor.seq.arr[currIndex]);
        if(++currIndex==seqMonitor.seq.arr.length){
          currIndex=0;
        }
      }
      private int getReverseIndex(){
        if(currIndex==0){
          return seqMonitor.seq.arr.length-1;
        }
        return currIndex-1;
      }
      @Override void reverseAndVerifyIndex(IntInputTestArgType inputArgType,int val){
        int currIndex;
        inputArgType.verifyVal(val,seqMonitor.seq.arr[(currIndex=getReverseIndex())]);
        this.currIndex=currIndex;
      }
      @Override void verifyIndexAndIterate(IntInputTestArgType inputArgType,int val){ 
        inputArgType.verifyVal(val,seqMonitor.seq.arr[currIndex]);
        if(++currIndex==seqMonitor.seq.arr.length){
          currIndex=0;
        }
      }
      @Override SequenceVerificationItr getOffset(int i){
        int index=currIndex+i;
        if(i!=0){
          int arrLength;
          if(index>=(arrLength=seqMonitor.seq.arr.length)){
            index-=arrLength;
          }else if(index<0){
            index+=arrLength;
          }
        }
        return new ArrDeqVerificationItr(index,seqMonitor);
      }
      @Override SequenceVerificationItr skip(int i){
        if(i!=0){
          currIndex+=i;
          int arrLength;
          if(currIndex>=(arrLength=seqMonitor.seq.arr.length)){
            currIndex-=arrLength;
          }else if(currIndex<0){
            currIndex+=arrLength;
          }
        }
        return this;
      }
      @Override public boolean equals(Object val){
        ArrDeqVerificationItr thatItr;
        return val==this || (val instanceof ArrDeqVerificationItr && (thatItr=(ArrDeqVerificationItr)val).currIndex==this.currIndex && thatItr.seqMonitor.seq==seqMonitor.seq);
      }
      @Override SequenceVerificationItr verifyRootPostAlloc(){
        return this;
      }
      @Override SequenceVerificationItr verifyParentPostAlloc(){
        return this;
      }
    }
    void illegalAdd(PreModScenario preModScenario){
      switch(preModScenario){
        case ModSeq:
          IntInputTestArgType.ARRAY_TYPE.callCollectionAdd(seq,0);
          verifyAddition();
          break;
        case NoMod:
          break;
        default:
          throw new Error("Unknown preModScenario "+preModScenario);
      }
    }
    void verifyAddition(){
      ++expectedSeqSize;
      ++expectedSeqModCount;
    }
    public String toString(){
      return new StringBuilder("IntArrDeq").append(checkedType.checked?".Checked{":"{").append(seq.head).append(',').append(expectedSeqSize).append(',').append(seq.tail).append('}').toString();
    }
    void verifyStructuralIntegrity(){
      if(checkedType.checked){
        Assertions.assertEquals(expectedSeqModCount,((IntArrDeq.Checked)seq).modCount);
      }
      if(expectedSeqSize==0)
      {
        Assertions.assertEquals(-1,seq.tail);
      }
      else
      {
        int size;
        if((size=seq.tail-seq.head+1)<=0){
          size+=seq.arr.length;
        }else{
        }
        Assertions.assertEquals(expectedSeqSize,size);
      }
    }
    void verifyFunctionalModification(){
      ++expectedSeqModCount;
    }
    void verifyBatchRemove(int numRemoved){
      expectedSeqSize-=numRemoved;
    }
    void writeObject(ObjectOutputStream oos) throws IOException{
      if(checkedType.checked){
        FieldAndMethodAccessor.IntArrDeq.Checked.writeObject(seq,oos);
      }else{
        FieldAndMethodAccessor.IntArrDeq.writeObject(seq,oos);
      }
    }
    void verifyRemoval(){
      --expectedSeqSize;
      ++expectedSeqModCount;
    }
  }
}
