package omni.impl.seq;
import java.util.Arrays;
import omni.util.ArrCopy;
import java.util.function.Consumer;
import java.io.IOException;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import omni.impl.RefInputTestArgType;
import omni.impl.RefOutputTestArgType;
import org.junit.jupiter.params.provider.Arguments;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import omni.util.OmniArray;
import omni.impl.RefDblLnkNode;
import omni.api.OmniIterator;
import omni.impl.FunctionCallType;
import omni.impl.QueryCastType;
import java.io.File;
import org.junit.jupiter.api.Tag;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import omni.impl.seq.AbstractRefSeqMonitor.CheckedType;
import omni.impl.seq.AbstractRefSeqMonitor.PreModScenario;
import omni.impl.seq.AbstractRefSeqMonitor.SequenceLocation;
import omni.impl.seq.AbstractRefSeqMonitor.IterationScenario;
import omni.impl.seq.AbstractRefSeqMonitor.ItrRemoveScenario;
import omni.impl.seq.AbstractRefSeqMonitor.ListItrSetScenario;
import omni.impl.seq.AbstractRefSeqMonitor.MonitoredFunctionGen;
import omni.impl.seq.AbstractRefSeqMonitor.MonitoredRemoveIfPredicateGen;
import omni.impl.seq.AbstractRefSeqMonitor.QueryTester;
import omni.impl.seq.AbstractRefSeqMonitor.ItrType;
import omni.impl.seq.AbstractRefSeqMonitor.MonitoredComparatorGen;
import java.nio.file.Files;
import java.util.Objects;
import omni.impl.seq.AbstractRefSeqMonitor.MonitoredObjectGen;
import omni.impl.seq.AbstractRefSeqMonitor.MonitoredObject;
import omni.impl.seq.AbstractRefSeqMonitor.SequenceVerificationItr;
import omni.api.OmniCollection;
import omni.api.OmniListIterator;
import java.util.ArrayList;
import omni.api.OmniDeque;
import omni.api.OmniList;
import java.util.Comparator;
@SuppressWarnings({"rawtypes","unchecked"})
@Tag("ArrDeqTest")
@Execution(ExecutionMode.CONCURRENT)
public class RefArrDeqTest{
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
                      testItrremove_voidHelper(itrType,checkedType,preModScenario,removeScenario,seqLocation,0,0);
                    }
                    for(int tmpSeqSize=1;tmpSeqSize<=10;++tmpSeqSize){
                      int seqSize=tmpSeqSize;
                      for(int tmpHead=0;tmpHead<tmpSeqSize;++tmpHead){
                        int head=tmpHead;
                        testItrremove_voidHelper(itrType,checkedType,preModScenario,removeScenario,seqLocation,seqSize,head);
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
            Stream.of(RefOutputTestArgType.values())
            .forEach(outputArgType->{
              IntStream.rangeClosed(0,10)
              .forEach(seqSize->{
                if(seqSize==0){
                  testItrnext_voidHelper(checkedType,preModScenario,outputArgType,itrType,seqSize,0);
                }else{
                  IntStream.range(0,seqSize)
                  .forEach(head->testItrnext_voidHelper(checkedType,preModScenario,outputArgType,itrType,seqSize,head));
                }
              });
            });
          }
        }
      }
    }
  }
  private static void testItrnext_voidHelper(CheckedType checkedType,PreModScenario preModScenario,RefOutputTestArgType outputArgType,ItrType itrType,int seqSize,int head){
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
  public void testtoArray_void(){
    runOutputTests(false,true,RefArrDeqTest::testtoArray_voidHelper);
  }
  private static void testtoArray_voidHelper(CheckedType checkedType,int head,int initialSize,RefOutputTestArgType outputArgType)
  {
    SeqMonitor seqMonitor=new SeqMonitor(checkedType,initialSize,head,initialSize);
    initializeAscending(seqMonitor.seq.arr,head,initialSize);
    outputArgType.verifyToArray(seqMonitor.seq,initialSize);
    seqMonitor.verifyStructuralIntegrity();
    seqMonitor.verifyPreAlloc().verifyAscending(initialSize);
  }
  @org.junit.jupiter.api.Test
  public void testclear_void(){
    for(var checkedType:CheckedType.values()){
      testclear_voidHelper(checkedType,0,0);
      for(int seqSize=1;seqSize<=10;++seqSize){
        for(int head=0;head<seqSize;++head){
          testclear_voidHelper(checkedType,seqSize,head);
        }
      }
    }
  }
  private static void testclear_voidHelper(CheckedType checkedType,int seqSize,int head){
    SeqMonitor seqMonitor=new SeqMonitor(checkedType,seqSize,head,seqSize);
    initializeAscending(seqMonitor.seq.arr,head,seqSize);
    seqMonitor.clear();
    seqMonitor.verifyStructuralIntegrity();
    Assertions.assertEquals(-1,seqMonitor.seq.tail);
    if(seqSize!=0)
    {
      for(int i=0;i<seqSize;++i)
      {
        Assertions.assertNull(seqMonitor.seq.arr[i]);
      }
    }
  }
  @org.junit.jupiter.api.Test
  public void testtoArray_ObjectArray(){
    for(var checkedType:CheckedType.values()){
      IntStream.iterate(0,seqSize->seqSize+=5).limit(3)
      .forEach(seqSize->{
        IntStream.iterate(0,paramArrSize->paramArrSize+=5).limit((seqSize/5)+1)
        .forEach(paramArrSize->{
          if(seqSize==0)
          {
            testtoArray_ObjectArrayHelper(checkedType,seqSize,0,paramArrSize);
          }
          else
          {
            IntStream.range(0,seqSize)
            .forEach(head->testtoArray_ObjectArrayHelper(checkedType,seqSize,head,paramArrSize));
          }
        });
      });
    }
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
      Object[] arr;
      for(int i=0,j=seqMonitor.seq.head,bound=(arr=seqMonitor.seq.arr).length;i<seqSize;++i){
        Assertions.assertSame(resultArr[i],arr[j]);
        if(++j==bound){
          j=0;
        }
      }
    }
  }
  @org.junit.jupiter.api.Test
  public void testcontains_val(){
    runQueryTests(false,(checkedType,argType,queryCastType,seqLocation,seqSize
    ,monitoredObjectGen
    )->{
      if(seqSize==0)
      {
        testcontains_valHelper(checkedType,seqSize,0,seqSize,seqLocation,argType,queryCastType
        ,monitoredObjectGen
        );
      }
      else
      {
        IntStream.range(0,seqSize)
        .forEach(head->{
          testcontains_valHelper(checkedType,seqSize,head,seqSize,seqLocation,argType,queryCastType
          ,monitoredObjectGen
          );
        });
      }
    });
  }
  private static void testcontains_valHelper(CheckedType checkedType,int capacity,int head,int numToAdd,SequenceLocation seqLocation,QueryTester argType,QueryCastType queryCastType
    ,MonitoredObjectGen monitoredObjectGen
  ){
    SeqMonitor seqMonitor=new SeqMonitor(checkedType,capacity,head,numToAdd);
    if(numToAdd!=0){
      if(monitoredObjectGen!=null){
        final var monitoredObject=monitoredObjectGen.getMonitoredObject(seqMonitor);
        int numExpectedCalls=initializeArrayForQuery(true,seqMonitor.seq.arr,head,numToAdd,argType,seqLocation,monitoredObject);
        if(seqLocation!=SequenceLocation.IOBHI && seqMonitor.seq.tail<seqMonitor.seq.head){
          if(seqMonitor.seq.head+numExpectedCalls>seqMonitor.seq.arr.length){
            numExpectedCalls-=(seqMonitor.seq.arr.length-seqMonitor.seq.head);
          }else{
            numExpectedCalls+=(seqMonitor.seq.tail+1);
          }
        }
        Assertions.assertThrows(monitoredObjectGen.expectedException,()->argType.invokecontainsMonitored(seqMonitor,monitoredObject));
        seqMonitor.verifyStructuralIntegrity();
        var verifyItr=seqMonitor.verifyPreAlloc().skip(numToAdd);
        switch(monitoredObjectGen){
          case ModSeq:
          case ModParent:
          case ModRoot:
            for(int i=0;i<numExpectedCalls;++i){
              verifyItr.verifyIllegalAdd();
            }
            break;
          case ThrowModSeq:
          case ThrowModParent:
          case ThrowModRoot:
            verifyItr.verifyIllegalAdd();
          case Throw:
            numExpectedCalls=1;
            break;
          default:
            throw new Error("Unknown monitoredObjectGen "+monitoredObjectGen);
        }
        Assertions.assertEquals(numExpectedCalls,monitoredObject.numEqualsCalls);
        return;
      }else{
        initializeArrayForQuery(true,seqMonitor.seq.arr,head,numToAdd,argType,seqLocation,null);
      }
    }
    Assertions.assertEquals(seqLocation!=SequenceLocation.IOBHI,argType.invokecontains(seqMonitor,queryCastType));
    seqMonitor.verifyStructuralIntegrity();
  }
  @org.junit.jupiter.api.Test
  public void testsearch_val(){
    runQueryTests(false,(checkedType,argType,queryCastType,seqLocation,seqSize
    ,monitoredObjectGen
    )->{
      if(seqSize==0)
      {
        testsearch_valHelper(checkedType,seqSize,0,seqSize,seqLocation,argType,queryCastType
        ,monitoredObjectGen
        );
      }
      else
      {
        IntStream.range(0,seqSize)
        .forEach(head->{
          testsearch_valHelper(checkedType,seqSize,head,seqSize,seqLocation,argType,queryCastType
          ,monitoredObjectGen
          );
        });
      }
    });
  }
  private static void testsearch_valHelper(CheckedType checkedType,int capacity,int head,int numToAdd,SequenceLocation seqLocation,QueryTester argType,QueryCastType queryCastType
    ,MonitoredObjectGen monitoredObjectGen
  ){
    SeqMonitor seqMonitor=new SeqMonitor(checkedType,capacity,head,numToAdd);
    int expectedIndex;
    if(numToAdd!=0){
      if(monitoredObjectGen!=null){
        final var monitoredObject=monitoredObjectGen.getMonitoredObject(seqMonitor);
        int numExpectedCalls=initializeArrayForQuery(true,seqMonitor.seq.arr,head,numToAdd,argType,seqLocation,monitoredObject);
        Assertions.assertThrows(monitoredObjectGen.expectedException,()->argType.invokesearchMonitored(seqMonitor,monitoredObject));
        seqMonitor.verifyStructuralIntegrity();
        var verifyItr=seqMonitor.verifyPreAlloc().skip(numToAdd);
        switch(monitoredObjectGen){
          case ModSeq:
          case ModParent:
          case ModRoot:
            for(int i=0;i<numExpectedCalls;++i){
              verifyItr.verifyIllegalAdd();
            }
            break;
          case ThrowModSeq:
          case ThrowModParent:
          case ThrowModRoot:
            verifyItr.verifyIllegalAdd();
          case Throw:
            numExpectedCalls=1;
            break;
          default:
            throw new Error("Unknown monitoredObjectGen "+monitoredObjectGen);
        }
        Assertions.assertEquals(numExpectedCalls,monitoredObject.numEqualsCalls);
        return;
      }else{
        expectedIndex=initializeArrayForQuery(true,seqMonitor.seq.arr,head,numToAdd,argType,seqLocation,null);
      }
    }else{
      expectedIndex=-1;
    }
    if(seqLocation==SequenceLocation.IOBHI){
      expectedIndex=-1;
    }
    Assertions.assertEquals(expectedIndex,argType.invokesearch(seqMonitor,queryCastType));
    seqMonitor.verifyStructuralIntegrity();
  }
  @org.junit.jupiter.api.Test
  public void testhashCode_void(){
    runToStringOrHashCodeTests(false,RefArrDeqTest::testhashCode_voidHelper);
  }
  private static void testhashCode_voidHelper(CheckedType checkedType,int size,int head
  ,MonitoredObjectGen monitoredObjectGen
  )
  {
    SeqMonitor seqMonitor=new SeqMonitor(checkedType,size,head,size);
    MonitoredObject monitoredObject=null;
    if(size!=0 && monitoredObjectGen!=null && monitoredObjectGen.expectedException!=null)
    {
      monitoredObject=monitoredObjectGen.getMonitoredObject(seqMonitor);
      for(int i=0;i<size;++i)
      {
        seqMonitor.seq.arr[i]=monitoredObject;
      }
    }
    else
    {
      initializeAscending(seqMonitor.seq.arr,head,size);
    }
    if(monitoredObject!=null)
    {
      Assertions.assertThrows(monitoredObjectGen.expectedException,()->seqMonitor.seq.hashCode());
      Assertions.assertEquals(seqMonitor.verifyThrowCondition(size,monitoredObject,monitoredObjectGen),monitoredObject.numHashCodeCalls);
    }
    else
    {
      int resultHash=seqMonitor.seq.hashCode();
      seqMonitor.verifyPreAlloc().verifyAscending(size);
      int expectedHash=1;
      if(size!=0)
      {
        Object[] arr;
        for(int i=0,j=seqMonitor.seq.head,bound=(arr=seqMonitor.seq.arr).length;i<size;++i){
          expectedHash=(expectedHash*31)+Objects.hashCode(arr[j]);
          if(++j==bound){
            j=0;
          }
        }
      }
      Assertions.assertEquals(expectedHash,resultHash);
    }
    seqMonitor.verifyStructuralIntegrity();
  }
  @org.junit.jupiter.api.Test
  public void testtoString_void(){
    runToStringOrHashCodeTests(false,RefArrDeqTest::testtoString_voidHelper);
  }
  private static void testtoString_voidHelper(CheckedType checkedType,int size,int head
  ,MonitoredObjectGen monitoredObjectGen
  )
  {
    SeqMonitor seqMonitor=new SeqMonitor(checkedType,size,head,size);
    MonitoredObject monitoredObject=null;
    if(size!=0 && monitoredObjectGen!=null && monitoredObjectGen.expectedException!=null)
    {
      monitoredObject=monitoredObjectGen.getMonitoredObject(seqMonitor);
      for(int i=0;i<size;++i)
      {
        seqMonitor.seq.arr[i]=monitoredObject;
      }
    }
    else
    {
      initializeAscending(seqMonitor.seq.arr,head,size);
    }
    if(monitoredObject!=null)
    {
      Assertions.assertThrows(monitoredObjectGen.expectedException,()->seqMonitor.seq.toString());
      Assertions.assertEquals(seqMonitor.verifyThrowCondition(size,monitoredObject,monitoredObjectGen),monitoredObject.numToStringCalls);
    }
    else
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
  }
  @org.junit.jupiter.api.Test
  public void testelement_void(){
    runOutputTests(false,true,RefArrDeqTest::testelement_voidHelper);
  }
  private static void testelement_voidHelper(CheckedType checkedType,int head,int initialSize,RefOutputTestArgType outputArgType)
  {
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
  }
  @org.junit.jupiter.api.Test
  public void testgetFirst_void(){
    runOutputTests(false,true,RefArrDeqTest::testgetFirst_voidHelper);
  }
  private static void testgetFirst_voidHelper(CheckedType checkedType,int head,int initialSize,RefOutputTestArgType outputArgType)
  {
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
  }
  @org.junit.jupiter.api.Test
  public void testgetLast_void(){
    runOutputTests(false,true,RefArrDeqTest::testgetLast_voidHelper);
  }
  private static void testgetLast_voidHelper(CheckedType checkedType,int head,int initialSize,RefOutputTestArgType outputArgType)
  {
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
  }
  @org.junit.jupiter.api.Test
  public void testremoveLast_void(){
    runOutputTests(false,true,RefArrDeqTest::testremoveLast_voidHelper);
  }
  private static void testremoveLast_voidHelper(CheckedType checkedType,int head,int initialSize,RefOutputTestArgType outputArgType)
  {
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
  }
  @org.junit.jupiter.api.Test
  public void testremoveFirst_void(){
    runOutputTests(false,true,RefArrDeqTest::testremoveFirst_voidHelper);
  }
  private static void testremoveFirst_voidHelper(CheckedType checkedType,int head,int initialSize,RefOutputTestArgType outputArgType)
  {
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
  }
  @org.junit.jupiter.api.Test
  public void testremove_void(){
    runOutputTests(false,true,RefArrDeqTest::testremove_voidHelper);
  }
  private static void testremove_voidHelper(CheckedType checkedType,int head,int initialSize,RefOutputTestArgType outputArgType)
  {
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
  }
  @org.junit.jupiter.api.Test
  public void testpop_void(){
    runOutputTests(false,true,RefArrDeqTest::testpop_voidHelper);
  }
  private static void testpop_voidHelper(CheckedType checkedType,int head,int initialSize,RefOutputTestArgType outputArgType)
  {
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
  }
  @org.junit.jupiter.api.Test
  public void testclone_void(){
    for(var checkedType:CheckedType.values()){
      IntStream.range(0,10)
      .forEach(seqSize->{
        if(seqSize==0)
        {
          testclone_voidHelper(new SeqMonitor(checkedType,0,0,0));
        }
        else
        {
          IntStream.range(0,seqSize)
          .forEach(head->{
            var seqMonitor=new SeqMonitor(checkedType,seqSize,head,seqSize);
            initializeAscending(seqMonitor.seq.arr,head,seqSize);
            testclone_voidHelper(seqMonitor);
          });
        }
      });
    }
  }
  private static void testclone_voidHelper(SeqMonitor seqMonitor){
    var clone=(RefArrDeq)seqMonitor.seq.clone();
    if(seqMonitor.checkedType.checked){
      Assertions.assertEquals(0,((RefArrDeq.Checked)clone).modCount);
    }
    int seqSize;
    if((seqSize=seqMonitor.expectedSeqSize)==0)
    {
      Assertions.assertEquals(-1,clone.tail);
      Assertions.assertEquals(0,clone.head);
      Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,clone.arr);
    }
    else
    {
      Assertions.assertEquals(0,clone.head);
      Assertions.assertEquals(seqSize-1,clone.tail);
      Object[] origArr,cloneArr=clone.arr;
      for(int i=0,j=seqMonitor.seq.head,arrLength=(origArr=seqMonitor.seq.arr).length;i<seqSize;++i)
      {
        Assertions.assertSame(origArr[j],cloneArr[i]);
        if(++j==arrLength)
        {
          j=0;
        }
      }
    }
  }
  @org.junit.jupiter.api.Test
  public void testpoll_void(){
    runOutputTests(false,true,RefArrDeqTest::testpoll_voidHelper);
  }
  private static void testpoll_voidHelper(CheckedType checkedType,int head,int initialSize,RefOutputTestArgType outputArgType)
  {
    SeqMonitor seqMonitor=new SeqMonitor(checkedType,initialSize,head,initialSize);
    initializeAscending(seqMonitor.seq.arr,head,initialSize);
    for(int i=0;i<initialSize;++i)
    {
      seqMonitor.poll(i,outputArgType);
      seqMonitor.verifyStructuralIntegrity();
    }
    seqMonitor.poll(0,outputArgType);
    seqMonitor.verifyStructuralIntegrity();
  }
  @org.junit.jupiter.api.Test
  public void testpollFirst_void(){
    runOutputTests(false,true,RefArrDeqTest::testpollFirst_voidHelper);
  }
  private static void testpollFirst_voidHelper(CheckedType checkedType,int head,int initialSize,RefOutputTestArgType outputArgType)
  {
    SeqMonitor seqMonitor=new SeqMonitor(checkedType,initialSize,head,initialSize);
    initializeAscending(seqMonitor.seq.arr,head,initialSize);
    for(int i=0;i<initialSize;++i)
    {
      seqMonitor.pollFirst(i,outputArgType);
      seqMonitor.verifyStructuralIntegrity();
    }
    seqMonitor.pollFirst(0,outputArgType);
    seqMonitor.verifyStructuralIntegrity();
  }
  @org.junit.jupiter.api.Test
  public void testpollLast_void(){
    runOutputTests(false,true,RefArrDeqTest::testpollLast_voidHelper);
  }
  private static void testpollLast_voidHelper(CheckedType checkedType,int head,int initialSize,RefOutputTestArgType outputArgType)
  {
    SeqMonitor seqMonitor=new SeqMonitor(checkedType,initialSize,head,initialSize);
    initializeAscending(seqMonitor.seq.arr,head,initialSize);
    for(int i=initialSize;--i>=0;)
    {
      seqMonitor.pollLast(i,outputArgType);
      seqMonitor.verifyStructuralIntegrity();
    }
    seqMonitor.pollLast(0,outputArgType);
    seqMonitor.verifyStructuralIntegrity();
  }
  @org.junit.jupiter.api.Test
  public void testremoveVal_val(){
    runQueryTests(false,(checkedType,argType,queryCastType,seqLocation,seqSize
    ,monitoredObjectGen
    )->{
      if(seqSize==0)
      {
        testremoveVal_valHelper(checkedType,seqSize,0,seqSize,seqLocation,argType,queryCastType
        ,monitoredObjectGen
        );
      }
      else
      {
        IntStream.range(0,seqSize)
        .forEach(head->{
          testremoveVal_valHelper(checkedType,seqSize,head,seqSize,seqLocation,argType,queryCastType
          ,monitoredObjectGen
          );
        });
      }
    });
  }
  private static void testremoveVal_valHelper(CheckedType checkedType,int capacity,int head,int numToAdd,SequenceLocation seqLocation,QueryTester argType,QueryCastType queryCastType
    ,MonitoredObjectGen monitoredObjectGen
  ){
    SeqMonitor seqMonitor=new SeqMonitor(checkedType,capacity,head,numToAdd);
    if(numToAdd!=0){
      if(monitoredObjectGen!=null){
        final var monitoredObject=monitoredObjectGen.getMonitoredObject(seqMonitor);
        int numExpectedCalls=initializeArrayForQuery(true,seqMonitor.seq.arr,head,numToAdd,argType,seqLocation,monitoredObject);
        Assertions.assertThrows(monitoredObjectGen.expectedException,()->argType.invokeremoveValMonitored(seqMonitor,monitoredObject));
        seqMonitor.verifyStructuralIntegrity();
        var verifyItr=seqMonitor.verifyPreAlloc().skip(numToAdd);
        switch(monitoredObjectGen){
          case ModSeq:
          case ModParent:
          case ModRoot:
            for(int i=0;i<numExpectedCalls;++i){
              verifyItr.verifyIllegalAdd();
            }
            break;
          case ThrowModSeq:
          case ThrowModParent:
          case ThrowModRoot:
            verifyItr.verifyIllegalAdd();
          case Throw:
            numExpectedCalls=1;
            break;
          default:
            throw new Error("Unknown monitoredObjectGen "+monitoredObjectGen);
        }
        Assertions.assertEquals(numExpectedCalls,monitoredObject.numEqualsCalls);
        return;
      }else{
        initializeArrayForQuery(true,seqMonitor.seq.arr,head,numToAdd,argType,seqLocation,null);
      }
    }
    Assertions.assertEquals(seqLocation!=SequenceLocation.IOBHI,argType.invokeremoveVal(seqMonitor,queryCastType));
    seqMonitor.verifyStructuralIntegrity();
  }
  @org.junit.jupiter.api.Test
  public void testremoveLastOccurrence_val(){
    runQueryTests(false,(checkedType,argType,queryCastType,seqLocation,seqSize
    ,monitoredObjectGen
    )->{
      if(seqSize==0)
      {
        testremoveLastOccurrence_valHelper(checkedType,seqSize,0,seqSize,seqLocation,argType,queryCastType
        ,monitoredObjectGen
        );
      }
      else
      {
        IntStream.range(0,seqSize)
        .forEach(head->{
          testremoveLastOccurrence_valHelper(checkedType,seqSize,head,seqSize,seqLocation,argType,queryCastType
          ,monitoredObjectGen
          );
        });
      }
    });
  }
  private static void testremoveLastOccurrence_valHelper(CheckedType checkedType,int capacity,int head,int numToAdd,SequenceLocation seqLocation,QueryTester argType,QueryCastType queryCastType
    ,MonitoredObjectGen monitoredObjectGen
  ){
    SeqMonitor seqMonitor=new SeqMonitor(checkedType,capacity,head,numToAdd);
    if(numToAdd!=0){
      if(monitoredObjectGen!=null){
        final var monitoredObject=monitoredObjectGen.getMonitoredObject(seqMonitor);
        int numExpectedCalls=initializeArrayForQuery(false,seqMonitor.seq.arr,head,numToAdd,argType,seqLocation,monitoredObject);
        Assertions.assertThrows(monitoredObjectGen.expectedException,()->argType.invokeremoveLastOccurrenceMonitored(seqMonitor,monitoredObject));
        seqMonitor.verifyStructuralIntegrity();
        var verifyItr=seqMonitor.verifyPreAlloc().skip(numToAdd);
        switch(monitoredObjectGen){
          case ModSeq:
          case ModParent:
          case ModRoot:
            for(int i=0;i<numExpectedCalls;++i){
              verifyItr.verifyIllegalAdd();
            }
            if(head>0 && seqLocation==SequenceLocation.IOBHI)
            {
              ++numExpectedCalls;
            }
            break;
          case ThrowModSeq:
          case ThrowModParent:
          case ThrowModRoot:
            verifyItr.verifyIllegalAdd();
          case Throw:
            numExpectedCalls=1;
            break;
          default:
            throw new Error("Unknown monitoredObjectGen "+monitoredObjectGen);
        }
        Assertions.assertEquals(numExpectedCalls,monitoredObject.numEqualsCalls);
        return;
      }else{
        initializeArrayForQuery(false,seqMonitor.seq.arr,head,numToAdd,argType,seqLocation,null);
      }
    }
    Assertions.assertEquals(seqLocation!=SequenceLocation.IOBHI,argType.invokeremoveLastOccurrence(seqMonitor,queryCastType));
    seqMonitor.verifyStructuralIntegrity();
  }
  @org.junit.jupiter.api.Test
  public void testadd_val(){
    runInputTests(false,RefArrDeqTest::testadd_valHelper);
  }
  private static void testadd_valHelper(CheckedType checkedType,int initialCapacity,int head,int initialSize,RefInputTestArgType inputArgType)
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
  }
  @org.junit.jupiter.api.Test
  public void testaddLast_val(){
    runInputTests(false,RefArrDeqTest::testaddLast_valHelper);
  }
  private static void testaddLast_valHelper(CheckedType checkedType,int initialCapacity,int head,int initialSize,RefInputTestArgType inputArgType)
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
  }
  @org.junit.jupiter.api.Test
  public void testpush_val(){
    runInputTests(false,RefArrDeqTest::testpush_valHelper);
  }
  private static void testpush_valHelper(CheckedType checkedType,int initialCapacity,int head,int initialSize,RefInputTestArgType inputArgType)
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
  }
  @org.junit.jupiter.api.Test
  public void testaddFirst_val(){
    runInputTests(false,RefArrDeqTest::testaddFirst_valHelper);
  }
  private static void testaddFirst_valHelper(CheckedType checkedType,int initialCapacity,int head,int initialSize,RefInputTestArgType inputArgType)
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
  }
  @org.junit.jupiter.api.Test
  public void testofferFirst_val(){
    runInputTests(false,RefArrDeqTest::testofferFirst_valHelper);
  }
  private static void testofferFirst_valHelper(CheckedType checkedType,int initialCapacity,int head,int initialSize,RefInputTestArgType inputArgType)
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
  }
  @org.junit.jupiter.api.Test
  public void testoffer_val(){
    runInputTests(false,RefArrDeqTest::testoffer_valHelper);
  }
  private static void testoffer_valHelper(CheckedType checkedType,int initialCapacity,int head,int initialSize,RefInputTestArgType inputArgType)
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
  }
  @org.junit.jupiter.api.Test
  public void testofferLast_val(){
    runInputTests(false,RefArrDeqTest::testofferLast_valHelper);
  }
  private static void testofferLast_valHelper(CheckedType checkedType,int initialCapacity,int head,int initialSize,RefInputTestArgType inputArgType)
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
  }
  @org.junit.jupiter.api.Test
  public void testConstructor_void(){
    {
      var deq=new RefArrDeq.Checked();
      Assertions.assertEquals(0,deq.modCount);
      Assertions.assertEquals(-1,deq.tail);
      Assertions.assertEquals(0,deq.head);
      Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,deq.arr);
    }
    {
      var deq=new RefArrDeq();
      Assertions.assertEquals(-1,deq.tail);
      Assertions.assertEquals(0,deq.head);
      Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,deq.arr);
    }
  }
  @org.junit.jupiter.api.Test
  public void testConstructor_int(){
    for(int initialCapacity=0;initialCapacity<=15;initialCapacity+=5)
    {
      {
        var deq=new RefArrDeq.Checked(initialCapacity);
        Assertions.assertEquals(0,deq.modCount);
        Assertions.assertEquals(-1,deq.tail);
        Assertions.assertEquals(0,deq.head);
        switch(initialCapacity){
          case 0:
            Assertions.assertNull(deq.arr);
            break;
          case OmniArray.DEFAULT_ARR_SEQ_CAP:
            Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,deq.arr);
            break;
          default:
            Assertions.assertEquals(initialCapacity,deq.arr.length);
            for(int i=0;i<initialCapacity;++i)
            {
              Assertions.assertNull(deq.arr[i]);
            }
        }
      }
      {
        var deq=new RefArrDeq.Checked(initialCapacity);
        Assertions.assertEquals(-1,deq.tail);
        Assertions.assertEquals(0,deq.head);
        switch(initialCapacity){
          case 0:
            Assertions.assertNull(deq.arr);
            break;
          case OmniArray.DEFAULT_ARR_SEQ_CAP:
            Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,deq.arr);
            break;
          default:
            Assertions.assertEquals(initialCapacity,deq.arr.length);
            for(int i=0;i<initialCapacity;++i)
            {
              Assertions.assertNull(deq.arr[i]);
            }
        }
      }
    }
  }
  interface ToStringAndHashCodeTest{
    void runTest(CheckedType checkedType,int size,int head
    ,MonitoredObjectGen monitoredObjectGen
    );
  }
  static void runToStringOrHashCodeTests(boolean parallel,ToStringAndHashCodeTest tester){
    for(var checkedType:CheckedType.values()){
      var seqSizeStream=IntStream.rangeClosed(0,10);
      if(parallel){
        seqSizeStream=seqSizeStream.parallel();
      }
      seqSizeStream.forEach(seqSize->{
        if(seqSize==0)
        {
          tester.runTest(checkedType,seqSize,0,null);
        }
        else
        {
          var headStream=IntStream.range(0,seqSize);
          if(parallel){
            headStream=headStream.parallel();
          }
          headStream.forEach(head->{
            var monitoredObjectGenStream=Stream.of(MonitoredObjectGen.values()).filter(monitoredObjectGen->monitoredObjectGen.expectedException==null||(checkedType.checked && monitoredObjectGen.appliesToRoot));
            if(parallel){
              monitoredObjectGenStream=monitoredObjectGenStream.parallel();
            }
            monitoredObjectGenStream.forEach(monitoredObjectGen->tester.runTest(checkedType,seqSize,head,monitoredObjectGen));
          });
        }
      });
    }
  }
  interface OutputTester{
    void runTest(CheckedType checkedType,int head,int initialSize,RefOutputTestArgType inputType);
  }
  static void runOutputTests(boolean parallel,boolean allowEmpty,OutputTester outputTest){
    for(final var checkedType:CheckedType.values()){
      var seqSizeStream=IntStream.rangeClosed(0,10).filter(seqSize->allowEmpty||seqSize!=0||checkedType.checked);
      if(parallel){
        seqSizeStream=seqSizeStream.parallel();
      }
      seqSizeStream.forEach(seqSize->{
        var outputArgTypeStream=Stream.of(RefOutputTestArgType.values());
        if(parallel){
          outputArgTypeStream=outputArgTypeStream.parallel();
        }
        outputArgTypeStream.forEach(outputType->{
          if(seqSize==0){
            outputTest.runTest(checkedType,0,seqSize,outputType);
          }else{
            var headStream=IntStream.range(0,seqSize);
            if(parallel){
              headStream=headStream.parallel();
            }
            headStream.forEach(head->outputTest.runTest(checkedType,head,seqSize,outputType));
          }
        });
      });
    }
  }
  interface InputTester{
    void runTest(CheckedType checkedType,int initialCapacity,int head,int initialSize,RefInputTestArgType inputType);
  }
  static void runInputTests(boolean parallel,InputTester inputTester)
  {
    for(var checkedType:CheckedType.values()){
      var capacityStream=IntStream.rangeClosed(0,15);
      if(parallel){
        capacityStream=capacityStream.parallel();
      }
      capacityStream.forEach(capacity->{
        var seqSizeStream=IntStream.rangeClosed(0,capacity);
        if(parallel)
        {
          seqSizeStream=seqSizeStream.parallel();
        }
        seqSizeStream.forEach(seqSize->{
          var inputArgTypeStream=Stream.of(RefInputTestArgType.values());
          if(parallel)
          {
            inputArgTypeStream=inputArgTypeStream.parallel();
          }
          inputArgTypeStream.forEach(inputType->{
             if(seqSize==0)
             {
               inputTester.runTest(checkedType,capacity,0,seqSize,inputType);
             }
             else
             {
               var headStream=IntStream.range(0,capacity);
               if(parallel){
                 headStream=headStream.parallel();
               }
               headStream.forEach(head->{
                 inputTester.runTest(checkedType,capacity,head,seqSize,inputType);
               });
             }
          });
        });
      });
    }
  }
  interface QueryTest
  {
    void runTest(CheckedType checkedType,QueryTester argType,QueryCastType queryCastType,SequenceLocation seqLocation,int seqSize
      ,MonitoredObjectGen monitoredObjectGen
    );
  }
  static void runQueryTests(boolean parallel,QueryTest queryTest){
    for(var checkedType:CheckedType.values()){
      var seqLocationStream=Stream.of(SequenceLocation.values()).filter(seqLocation->seqLocation!=SequenceLocation.IOBLO);
      if(parallel){
        seqLocationStream=seqLocationStream.parallel();
      }
      seqLocationStream.forEach(seqLocation->{
        for(int tmpSeqSize=0;tmpSeqSize<=10;++tmpSeqSize){
          final int seqSize=tmpSeqSize;
          if(seqLocation==SequenceLocation.IOBHI || seqSize!=0){
            for(var queryCastType:QueryCastType.values()){
              var argTypeStream=Stream.of(QueryTester.values());
              if(parallel){
                argTypeStream=argTypeStream.parallel();
              }
              argTypeStream.forEach(argType->{
                switch(argType){
                  case ObjectNonNull:
                  case Objectnull:
                    if(queryCastType!=QueryCastType.ToObject){
                      return;
                    }
                    break;
                  case Booleannull:
                  case Bytenull:
                  case Characternull:
                  case Shortnull:
                  case Integernull:
                  case Longnull:
                  case Floatnull:
                  case Doublenull:
                    if(queryCastType!=QueryCastType.ToBoxed){
                      return;
                    }
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
                  //negative values beyond the range of int that are beyond the precision of float and double
                  case LongMIN_SAFE_LONG_MINUS1:
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
                  //positive values beyond the range of int that are beyond the precision of float and double
                  case LongMAX_SAFE_LONG_PLUS1:
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
                  if(seqSize!=0 && seqLocation.expectedException==null){
                    return;
                  }
                  //these values must necessarily return false
                }
                if(argType==QueryTester.ObjectNonNull){
                  if(seqSize==0|| !checkedType.checked){
                    return;
                  }
                  var monitoredObjectGenStream=Stream.of(MonitoredObjectGen.values()).filter(monitoredObjectGen->monitoredObjectGen.expectedException!=null&&monitoredObjectGen.appliesToRoot);
                  if(parallel){
                    monitoredObjectGenStream=monitoredObjectGenStream.parallel();
                  }
                  monitoredObjectGenStream.forEach(monitoredObjectGen->{
                    queryTest.runTest(checkedType,argType,queryCastType,seqLocation,seqSize,monitoredObjectGen);
                  });
                }else{
                  queryTest.runTest(checkedType,argType,queryCastType,seqLocation,seqSize,null);
                }
              });
            }
          }
        }
      });
    }
  }
  private static void initializeAscending(Object[] arr,int head,int size)
  {
    if(size!=0)
    {
      for(int i=0,arrLength=arr.length;i<size;++i)
      {
        arr[head]=TypeConversionUtil.convertToObject(i);
        if(++head==arrLength)
        {
          head=0;
        }
      }
    }
  }
  private static int initializeArrayForQuery(boolean forwardIteration,Object[] arr,int head,int size,QueryTester argType,SequenceLocation seqLocation
   ,MonitoredObject monitoredObject
  ){
    int capacity=arr.length;
    switch(seqLocation){
      case IOBHI:
        if(monitoredObject!=null)
        {
          for(int i=0,dst=head;i<size;++i)
          {
            arr[dst]=new Object();
            if(++dst==capacity)
            {
              dst=0;
            }
          }
        }
        else
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
        if(monitoredObject!=null)
        {
          int dst;
          arr[dst=head]=monitoredObject;
          for(int i=1;i<size;++i)
          {
            if(++dst==capacity){
              dst=0;
            }
            arr[dst]=new Object();
          }
        }
        else
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
        if(monitoredObject!=null)
        {
          int i,dst,bound;
          for(i=0,dst=head,bound=size>>2;i<bound;++i)
          {
            arr[dst]=new Object();
            if(++dst==capacity)
            {
              dst=0;
            }
          }
          arr[dst]=monitoredObject;
          for(;++i<size;)
          {
            if(++dst==capacity)
            {
              dst=0;
            }
            arr[dst]=new Object();
          }
        }
        else
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
        if(monitoredObject!=null)
        {
          int i,dst,bound;
          for(i=0,dst=head,bound=size>>1;i<bound;++i)
          {
            arr[dst]=new Object();
            if(++dst==capacity)
            {
              dst=0;
            }
          }
          arr[dst]=monitoredObject;
          for(;++i<size;)
          {
            if(++dst==capacity)
            {
              dst=0;
            }
            arr[dst]=new Object();
          }
        }
        else
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
        if(monitoredObject!=null)
        {
          int i,dst,bound;
          for(i=0,dst=head,bound=(size>>2)*3;i<bound;++i)
          {
            arr[dst]=new Object();
            if(++dst==capacity)
            {
              dst=0;
            }
          }
          arr[dst]=monitoredObject;
          for(;++i<size;)
          {
            if(++dst==capacity)
            {
              dst=0;
            }
            arr[dst]=new Object();
          }
        }
        else
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
        if(monitoredObject!=null)
        {
          int i,dst,bound;
          for(i=0,dst=head,bound=size-1;i<bound;++i)
          {
            arr[dst]=new Object();
            if(++dst==capacity){
              dst=0;
            }
          }
          arr[dst]=monitoredObject;
        }
        else
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
  private static class SeqMonitor extends AbstractRefSeqMonitor<RefArrDeq>{
    //private int expectedTail;
    //private int expectedHead;
    SeqMonitor(CheckedType checkedType,int capacity,int head,int size)
    {
      super(checkedType);
      if(size==0)
      {
        this.seq=checkedType.checked?new RefArrDeq.Checked(capacity):new RefArrDeq(capacity);
      }
      else
      {
        Assertions.assertTrue(size<=capacity,"The expression (size="+size+")<=(capacity="+capacity+") evaluates to false");
        int tail=head+size-1;
        Object[] arr=new Object[capacity];
        if(tail>=capacity)
        {
          tail-=capacity;
        }
        this.seq=checkedType.checked?new RefArrDeq.Checked(head,arr,tail):new RefArrDeq(head,arr,tail);
        this.expectedSeqSize=size;
      }
    }
    SeqMonitor(CheckedType checkedType,int initialCapacity){
      super(checkedType);
      this.seq=checkedType.checked?new RefArrDeq.Checked(initialCapacity):new RefArrDeq(initialCapacity);
      //this.expectedTail=-1;
    }
    SeqMonitor(CheckedType checkedType){
      super(checkedType);
      this.seq=checkedType.checked?new RefArrDeq.Checked():new RefArrDeq();
      //this.expectedTail=-1;
    }
    AbstractItrMonitor getItrMonitor(){
      if(checkedType.checked){
        return new CheckedAscendingItrMonitor();
      }
      return new UncheckedAscendingItrMonitor();
    }
    class UncheckedAscendingItrMonitor extends AbstractRefSeqMonitor.AbstractItrMonitor{
      int expectedCursor;
      UncheckedAscendingItrMonitor(ItrType itrType,OmniIterator.OfRef itr,int cursor){
        super(itrType,itr,expectedSeqModCount);
        this.expectedCursor=cursor;
      }
      UncheckedAscendingItrMonitor(){
        super(ItrType.Itr,seq.iterator(),expectedSeqModCount);
        this.expectedCursor=seq.tail==-1?-1:seq.head;
      }
      void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
        int tail=seq.tail;
        {
          itr.forEachRemaining((Consumer)action);
        }
        if(tail!=-1){
          this.expectedCursor=-1;
        }
      }
      SeqMonitor getSeqMonitor(){
        return SeqMonitor.this;
      }
      int getNewCursor(){
        return expectedCursor==seq.tail?-1:expectedCursor+1==seq.arr.length?0:expectedCursor+1;
      }
      void verifyNext(int expectedVal,RefOutputTestArgType outputType){
        int newCursor=getNewCursor();
        outputType.verifyItrNext(itr,expectedVal);
        expectedCursor=newCursor;
      }
      void iterateForward(){
        int newCursor=getNewCursor();
        itr.next();
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
        Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.RefArrDeq.AscendingItr.cursor(itr));
        Assertions.assertSame(seq,FieldAndMethodAccessor.RefArrDeq.AscendingItr.root(itr));
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
              if((headDist=(tmp=newCursor+1)-head)>=0){
                if(headDist<=arrBound-tmp){
                  newCursor=tmp;
                }
              }else if(tail-tmp>arrBound-head+tmp){
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
      CheckedAscendingItrMonitor(ItrType itrType,OmniIterator.OfRef itr,int cursor){
        super(itrType,itr,cursor);
        this.expectedLastRet=-1;
      }
      CheckedAscendingItrMonitor(){
        super(ItrType.Itr,seq.iterator(),seq.tail==-1?-1:seq.head);
        this.expectedLastRet=-1;
      }
      void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
        int tail=seq.tail;
        {
          itr.forEachRemaining((Consumer)action);
        }
        if(tail!=-1){
          this.expectedLastRet=tail;
          this.expectedCursor=-1;
        }
      }
      void verifyNext(int expectedVal,RefOutputTestArgType outputType){
        int newCursor=getNewCursor();
        outputType.verifyItrNext(itr,expectedVal);
        expectedLastRet=expectedCursor;
        expectedCursor=newCursor;
      }
      void iterateForward(){
        int newCursor=getNewCursor();
        itr.next();
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
        Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.RefArrDeq.Checked.AscendingItr.modCount(itr));
        Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.RefArrDeq.Checked.AscendingItr.cursor(itr));
        Assertions.assertEquals(expectedLastRet,FieldAndMethodAccessor.RefArrDeq.Checked.AscendingItr.lastRet(itr));
        Assertions.assertSame(seq,FieldAndMethodAccessor.RefArrDeq.Checked.AscendingItr.root(itr));
      }
    }
    class CheckedDescendingItrMonitor extends CheckedAscendingItrMonitor{
      CheckedDescendingItrMonitor(){
        super(ItrType.DescendingItr,seq.descendingIterator(),expectedSeqModCount);
        this.expectedCursor=seq.tail;
        this.expectedLastRet=-1;
      }
      void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
        int tail=seq.tail;
        int head=seq.head;
        {
          itr.forEachRemaining((Consumer)action);
        }
        if(tail!=-1){
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
      SequenceVerificationItr verifyNaturalAscending(int v,RefInputTestArgType inputArgType,int length){
        return verifyAscending(v,inputArgType,length);
      }
      @Override SequenceVerificationItr verifyPostAlloc(int expectedVal){
        return this;
      }
      @Override void verifyLiteralIndexAndIterate(Object val){
        Assertions.assertSame(val,seqMonitor.seq.arr[currIndex]);
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
      @Override void verifyIndexAndIterate(MonitoredObject monitoredObject){
        Object v;
        Assertions.assertEquals(monitoredObject.compareVal,(v=seqMonitor.seq.arr[currIndex]) instanceof MonitoredObject?((MonitoredObject)v).compareVal:(Object)v);
        if(++currIndex==seqMonitor.seq.arr.length){
          currIndex=0;
        }
      }
      @Override void reverseAndVerifyIndex(MonitoredObject monitoredObject){
        Object v;
        int currIndex;
        Assertions.assertEquals(monitoredObject.compareVal,(v=(seqMonitor.seq.arr[currIndex=getReverseIndex()])) instanceof MonitoredObject?((MonitoredObject)v).compareVal:(Object)v);
        this.currIndex=currIndex;
      }
      @Override void reverseAndVerifyIndex(RefInputTestArgType inputArgType,int val){
        int currIndex;
        inputArgType.verifyVal(val,seqMonitor.seq.arr[(currIndex=getReverseIndex())]);
        this.currIndex=currIndex;
      }
      @Override void verifyIndexAndIterate(RefInputTestArgType inputArgType,int val){ 
        inputArgType.verifyVal(val,seqMonitor.seq.arr[currIndex]);
        if(++currIndex==seqMonitor.seq.arr.length){
          currIndex=0;
        }
      }
      @Override SequenceVerificationItr getOffset(int i){
        int index=currIndex+1;
        int arrLength;
        if(index>=(arrLength=seqMonitor.seq.arr.length)){
          index-=arrLength;
        }else if(index<0){
          index+=arrLength;
        }
        return new ArrDeqVerificationItr(index,seqMonitor);
      }
      @Override SequenceVerificationItr skip(int i){
        currIndex+=i;
        int arrLength;
        if(currIndex>=(arrLength=seqMonitor.seq.arr.length)){
          currIndex-=arrLength;
        }else if(currIndex<0){
          currIndex+=arrLength;
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
          RefInputTestArgType.ARRAY_TYPE.callCollectionAdd(seq,0);
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
      return new StringBuilder("RefArrDeq").append(checkedType.checked?".Checked{":"{").append(seq.head).append(',').append(expectedSeqSize).append(',').append(seq.tail).append('}').toString();
    }
    void verifyStructuralIntegrity(){
      if(checkedType.checked){
        Assertions.assertEquals(expectedSeqModCount,((RefArrDeq.Checked)seq).modCount);
      }
      if(expectedSeqSize==0)
      {
        Assertions.assertEquals(-1,seq.tail);
        Object[] arr;
        if((arr=seq.arr)!=null)
        {
          for(int i=arr.length;--i>=0;){
            Assertions.assertNull(arr[i]);
          }
        }
      }
      else
      {
        int size,head,tail;
        if((size=(tail=seq.tail)-(head=seq.head)+1)<=0){
          var arr=seq.arr;
          while(++tail!=head){
            Assertions.assertNull(arr[tail]);
          }
          size+=arr.length;
        }else{
          Object[] arr;
          int arrLength=(arr=seq.arr).length;
          while(++tail!=arrLength){
            Assertions.assertNull(arr[tail]);
          }
          while(--head!=-1){
            Assertions.assertNull(arr[head]);
          }
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
        FieldAndMethodAccessor.RefArrDeq.Checked.writeObject(seq,oos);
      }else{
        FieldAndMethodAccessor.RefArrDeq.writeObject(seq,oos);
      }
    }
    void verifyRemoval(){
      --expectedSeqSize;
      ++expectedSeqModCount;
    }
  }
}
