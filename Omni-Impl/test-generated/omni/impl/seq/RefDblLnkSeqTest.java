package omni.impl.seq;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Arrays;
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
import java.util.stream.DoubleStream;
import omni.util.OmniArray;
import omni.impl.RefDblLnkNode;
import omni.api.OmniIterator;
import java.util.function.Predicate;
import omni.impl.FunctionCallType;
import omni.impl.QueryCastType;
import java.io.File;
import omni.api.OmniStack;
import omni.util.TypeUtil;
import org.junit.jupiter.api.Tag;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.Externalizable;
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
@Tag("DblLnkSeqTest")
@Execution(ExecutionMode.CONCURRENT)
public class RefDblLnkSeqTest{
  @org.junit.jupiter.params.ParameterizedTest
  @org.junit.jupiter.params.provider.EnumSource(CheckedType.class)
  public void testConstructor_void(CheckedType checkedType){
    switch(checkedType){
      case CHECKED:{
        var seq=new RefDblLnkSeq.CheckedList();
        Assertions.assertNull(seq.head);
        Assertions.assertNull(seq.tail);
        Assertions.assertEquals(0,seq.size);
        Assertions.assertEquals(0,seq.modCount);
        break;
      }
      case UNCHECKED:{
        var seq=new RefDblLnkSeq.UncheckedList();
        Assertions.assertNull(seq.head);
        Assertions.assertNull(seq.tail);
        Assertions.assertEquals(0,seq.size);
        break;
      }
      default:
        throw new Error("Unknown checked type "+checkedType);
    }
  }
  @org.junit.jupiter.params.ParameterizedTest
  @org.junit.jupiter.params.provider.EnumSource(CheckedType.class)
  public void testConstructor_Node_int_Node(CheckedType checkedType){
    var head=new RefDblLnkNode(TypeConversionUtil.convertToObject(1));
    var tail=new RefDblLnkNode(TypeConversionUtil.convertToObject(2));
    head.next=tail;
    tail.prev=head;
    int seqSize=2;
    switch(checkedType){
      case CHECKED:{
        var seq=new RefDblLnkSeq.CheckedList(head,seqSize,tail);
        Assertions.assertSame(head,seq.head);
        Assertions.assertSame(tail,seq.tail);
        Assertions.assertEquals(seqSize,seq.size);
        Assertions.assertNull(seq.head.prev);
        Assertions.assertNull(seq.tail.next);
        Assertions.assertSame(seq.head.next,seq.tail);
        Assertions.assertSame(seq.tail.prev,seq.head);
        Assertions.assertEquals(0,seq.modCount);
        break;
      }
      case UNCHECKED:{
        var seq=new RefDblLnkSeq.UncheckedList(head,seqSize,tail);
        Assertions.assertSame(head,seq.head);
        Assertions.assertSame(tail,seq.tail);
        Assertions.assertEquals(seqSize,seq.size);
        Assertions.assertNull(seq.head.prev);
        Assertions.assertNull(seq.tail.next);
        Assertions.assertSame(seq.head.next,seq.tail);
        Assertions.assertSame(seq.tail.prev,seq.head);
        break;
      }
      default:
        throw new Error("Unknown checked type "+checkedType);
    }
  }
  @org.junit.jupiter.api.Test
  public void testsize_void(){
    getBasicCollectionTestArgs().parallel().map(Arguments::get).forEach(args->{
        testsize_voidHelper((SeqMonitor)args[0],(PreModScenario)args[1],(int)args[2]);
    });
  }
  private static void testsize_voidHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,int numToAdd){
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
  }
  @org.junit.jupiter.api.Test
  public void testisEmpty_void(){
    getBasicCollectionTestArgs().parallel().map(Arguments::get).forEach(args->{
        testisEmpty_voidHelper((SeqMonitor)args[0],(PreModScenario)args[1],(int)args[2]);
    });
  }
  private static void testisEmpty_voidHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,int numToAdd){
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
  }
  @org.junit.jupiter.api.Test
  public void testadd_val(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var preModScenario:PreModScenario.values()){
          if(preModScenario.expectedException==null || (checkedType.checked && preModScenario!=PreModScenario.ModSeq && !nestedType.rootType)){
            for(int seqSize=0;seqSize<=10;++seqSize){
              final int finalSeqSize=seqSize;
              Stream.of(RefInputTestArgType.values())
              .parallel()
              .forEach(inputArgType->{
                switch(nestedType){
                  case SUBLIST:
                    if(inputArgType==RefInputTestArgType.ARRAY_TYPE){
                      IntStream.rangeClosed(1,3)
                      .parallel()
                      .forEach(parentsLength->{
                        if(parentsLength!=1 || preModScenario!=PreModScenario.ModParent){
                          runSubListTests(false,checkedType,true,parentsLength,seqMonitor->testadd_valHelper(seqMonitor,inputArgType,preModScenario,finalSeqSize));
                        }
                      });
                      break;
                    }
                  case LISTDEQUE:
                    testadd_valHelper(new SeqMonitor(nestedType,checkedType),inputArgType,preModScenario,finalSeqSize);
                    break;
                  default:
                    throw new Error("Unknown nested type "+nestedType);
                } 
              });
            }
          }
        }
      }
    }
  }
  private static void testadd_valHelper
  (SeqMonitor seqMonitor,RefInputTestArgType inputArgType,PreModScenario preModScenario,int numToAdd){
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
  public void testcontains_val(){
    for(var nestedType:NestedType.values()){
      runQueryTests(true,nestedType,(checkedType,argType,queryCastType,seqLocation,seqSize,preModScenario
        ,monitoredObjectGen
      )->{
        testcontains_valHelper(new SeqMonitor(nestedType,checkedType),argType,queryCastType,seqLocation,seqSize,preModScenario
        ,monitoredObjectGen
        );
      });
    }
  }
  private static void testcontains_valHelper
  (SeqMonitor seqMonitor,QueryTester argType,QueryCastType queryCastType,SequenceLocation seqLocation,int numToAdd,PreModScenario preModScenario 
    ,MonitoredObjectGen monitoredObjectGen
  ){
    if(numToAdd!=0){
      if(monitoredObjectGen!=null){
        int numExpectedCalls;
        final var monitoredObject=monitoredObjectGen.getMonitoredObject(seqMonitor);
        switch(seqLocation){
          case BEGINNING:
            numExpectedCalls=argType.initContainsBeginning(seqMonitor,monitoredObject,numToAdd,true);
            break;
          case NEARBEGINNING:
            numExpectedCalls=argType.initContainsNearBeginning(seqMonitor,monitoredObject,numToAdd,true);
            break;
          case MIDDLE:
            numExpectedCalls=argType.initContainsMiddle(seqMonitor,monitoredObject,numToAdd,true);
            break;
          case NEAREND:
            numExpectedCalls=argType.initContainsNearEnd(seqMonitor,monitoredObject,numToAdd,true);
            break;
          case END:
            numExpectedCalls=argType.initContainsEnd(seqMonitor,monitoredObject,numToAdd,true);
            break;
          case IOBHI:
            argType.initDoesNotContain(seqMonitor,numToAdd);
            numExpectedCalls=seqMonitor.expectedSeqSize;
            break;
          default:
            throw new Error("Unknown seqLocation "+seqLocation);
        }
        int seqSize=seqMonitor.expectedSeqSize;
        seqMonitor.illegalAdd(preModScenario);
        Class<? extends Throwable> expectedException=preModScenario.expectedException==null?monitoredObjectGen.expectedException:preModScenario.expectedException;
        Assertions.assertThrows(expectedException,()->argType.invokecontainsMonitored(seqMonitor,monitoredObject));
        seqMonitor.verifyStructuralIntegrity();
        var verifyItr=seqMonitor.verifyPreAlloc().skip(seqSize);
        switch(monitoredObjectGen){
          case ModSeq:
            if(preModScenario==PreModScenario.NoMod){
              for(int i=0;i<numExpectedCalls;++i){
                verifyItr.verifyIllegalAdd();
              }
            }else{
              numExpectedCalls=1;
            }
            verifyItr.verifyPostAlloc(preModScenario);
            break;
          case ModParent:
            verifyItr.verifyParentPostAlloc();
            if(preModScenario==PreModScenario.ModParent){
              verifyItr.verifyIllegalAdd();
            }
            if(preModScenario==PreModScenario.ModRoot){
              numExpectedCalls=1;
            }
            else{
              for(int i=0;i<numExpectedCalls;++i){
                verifyItr.verifyIllegalAdd();
              }
            }
            verifyItr.verifyRootPostAlloc();
            if(preModScenario==PreModScenario.ModRoot){
              verifyItr.verifyIllegalAdd();
            }
            break;
          case ModRoot:
            verifyItr.verifyPostAlloc(preModScenario);
            for(int i=0;i<numExpectedCalls;++i){
              verifyItr.verifyIllegalAdd();
            }
            break;
          case Throw:
            numExpectedCalls=1;
            verifyItr.verifyPostAlloc(preModScenario);
            break;
          case ThrowModSeq:
            numExpectedCalls=1;
            if(preModScenario==PreModScenario.NoMod){
              verifyItr.verifyIllegalAdd();
            }
            verifyItr.verifyPostAlloc(preModScenario);
            break;
          case ThrowModParent:
            numExpectedCalls=1;
            verifyItr.verifyParentPostAlloc();
            switch(preModScenario){
              case NoMod:
                verifyItr.verifyIllegalAdd().verifyRootPostAlloc();
                break;
              case ModParent:
                verifyItr.verifyIllegalAdd().verifyIllegalAdd().verifyRootPostAlloc();
                break;
              case ModRoot:
                verifyItr.verifyRootPostAlloc().verifyIllegalAdd();
                break;
              default:
                throw new Error("Unknown preModScenario "+preModScenario);
            }
            break;
          case ThrowModRoot:
            numExpectedCalls=1;
            verifyItr.verifyPostAlloc(preModScenario).verifyIllegalAdd();
            break;
          default:
            throw new Error("Unknown monitoredObjectGen "+monitoredObjectGen);
        }
        Assertions.assertEquals(numExpectedCalls,monitoredObject.numEqualsCalls);
        return;
      }else
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
      {
          Assertions.assertThrows(preModScenario.expectedException,()->argType.invokecontains(seqMonitor,queryCastType));
      }
    }
    seqMonitor.verifyStructuralIntegrity();
    seqMonitor.verifyPreAlloc().skip(seqSize).verifyPostAlloc(preModScenario);
  }
  @org.junit.jupiter.api.Test
  public void testindexOf_val(){
    for(var nestedType:NestedType.values()){
      runQueryTests(true,nestedType,(checkedType,argType,queryCastType,seqLocation,seqSize,preModScenario
        ,monitoredObjectGen
      )->{
        testindexOf_valHelper(new SeqMonitor(nestedType,checkedType),argType,queryCastType,seqLocation,seqSize,preModScenario
        ,monitoredObjectGen
        );
      });
    }
  }
  private static void testindexOf_valHelper
  (SeqMonitor seqMonitor,QueryTester argType,QueryCastType queryCastType,SequenceLocation seqLocation,int numToAdd,PreModScenario preModScenario 
    ,MonitoredObjectGen monitoredObjectGen
  ){
    int expectedIndex;
    if(numToAdd!=0){
      if(monitoredObjectGen!=null){
        int numExpectedCalls;
        final var monitoredObject=monitoredObjectGen.getMonitoredObject(seqMonitor);
        switch(seqLocation){
          case BEGINNING:
            numExpectedCalls=argType.initContainsBeginning(seqMonitor,monitoredObject,numToAdd,true);
            break;
          case NEARBEGINNING:
            numExpectedCalls=argType.initContainsNearBeginning(seqMonitor,monitoredObject,numToAdd,true);
            break;
          case MIDDLE:
            numExpectedCalls=argType.initContainsMiddle(seqMonitor,monitoredObject,numToAdd,true);
            break;
          case NEAREND:
            numExpectedCalls=argType.initContainsNearEnd(seqMonitor,monitoredObject,numToAdd,true);
            break;
          case END:
            numExpectedCalls=argType.initContainsEnd(seqMonitor,monitoredObject,numToAdd,true);
            break;
          case IOBHI:
            argType.initDoesNotContain(seqMonitor,numToAdd);
            numExpectedCalls=seqMonitor.expectedSeqSize;
            break;
          default:
            throw new Error("Unknown seqLocation "+seqLocation);
        }
        int seqSize=seqMonitor.expectedSeqSize;
        seqMonitor.illegalAdd(preModScenario);
        Class<? extends Throwable> expectedException=preModScenario.expectedException==null?monitoredObjectGen.expectedException:preModScenario.expectedException;
        Assertions.assertThrows(expectedException,()->argType.invokeindexOfMonitored(seqMonitor,monitoredObject));
        seqMonitor.verifyStructuralIntegrity();
        var verifyItr=seqMonitor.verifyPreAlloc().skip(seqSize);
        switch(monitoredObjectGen){
          case ModSeq:
            if(preModScenario==PreModScenario.NoMod){
              for(int i=0;i<numExpectedCalls;++i){
                verifyItr.verifyIllegalAdd();
              }
            }else{
              numExpectedCalls=1;
            }
            verifyItr.verifyPostAlloc(preModScenario);
            break;
          case ModParent:
            verifyItr.verifyParentPostAlloc();
            if(preModScenario==PreModScenario.ModParent){
              verifyItr.verifyIllegalAdd();
            }
            if(preModScenario==PreModScenario.ModRoot){
              numExpectedCalls=1;
            }else{
              for(int i=0;i<numExpectedCalls;++i){
                verifyItr.verifyIllegalAdd();
              }
            }
            verifyItr.verifyRootPostAlloc();
            if(preModScenario==PreModScenario.ModRoot){
              verifyItr.verifyIllegalAdd();
            }
            break;
          case ModRoot:
            verifyItr.verifyPostAlloc(preModScenario);
            for(int i=0;i<numExpectedCalls;++i){
              verifyItr.verifyIllegalAdd();
            }
            break;
          case Throw:
            numExpectedCalls=1;
            verifyItr.verifyPostAlloc(preModScenario);
            break;
          case ThrowModSeq:
            numExpectedCalls=1;
            if(preModScenario==PreModScenario.NoMod){
              verifyItr.verifyIllegalAdd();
            }
            verifyItr.verifyPostAlloc(preModScenario);
            break;
          case ThrowModParent:
            numExpectedCalls=1;
            verifyItr.verifyParentPostAlloc();
            switch(preModScenario){
              case NoMod:
                verifyItr.verifyIllegalAdd().verifyRootPostAlloc();
                break;
              case ModParent:
                verifyItr.verifyIllegalAdd().verifyIllegalAdd().verifyRootPostAlloc();
                break;
              case ModRoot:
                verifyItr.verifyRootPostAlloc().verifyIllegalAdd();
                break;
              default:
                throw new Error("Unknown preModScenario "+preModScenario);
            }
            break;
          case ThrowModRoot:
            numExpectedCalls=1;
            verifyItr.verifyPostAlloc(preModScenario).verifyIllegalAdd();
            break;
          default:
            throw new Error("Unknown monitoredObjectGen "+monitoredObjectGen);
        }
        Assertions.assertEquals(numExpectedCalls,monitoredObject.numEqualsCalls);
        return;
      }else
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
      {
          Assertions.assertThrows(preModScenario.expectedException,()->argType.invokeindexOf(seqMonitor,queryCastType));
      }
    }
    seqMonitor.verifyStructuralIntegrity();
    seqMonitor.verifyPreAlloc().skip(seqSize).verifyPostAlloc(preModScenario);
  }
  @org.junit.jupiter.api.Test
  public void testlastIndexOf_val(){
    for(var nestedType:NestedType.values()){
      runQueryTests(true,nestedType,(checkedType,argType,queryCastType,seqLocation,seqSize,preModScenario
        ,monitoredObjectGen
      )->{
        testlastIndexOf_valHelper(new SeqMonitor(nestedType,checkedType),argType,queryCastType,seqLocation,seqSize,preModScenario
        ,monitoredObjectGen
        );
      });
    }
  }
  private static void testlastIndexOf_valHelper
  (SeqMonitor seqMonitor,QueryTester argType,QueryCastType queryCastType,SequenceLocation seqLocation,int numToAdd,PreModScenario preModScenario 
    ,MonitoredObjectGen monitoredObjectGen
  ){
    int expectedIndex;
    if(numToAdd!=0){
      if(monitoredObjectGen!=null){
        int numExpectedCalls;
        final var monitoredObject=monitoredObjectGen.getMonitoredObject(seqMonitor);
        switch(seqLocation){
          case BEGINNING:
            numExpectedCalls=argType.initContainsBeginning(seqMonitor,monitoredObject,numToAdd,false);
            break;
          case NEARBEGINNING:
            numExpectedCalls=argType.initContainsNearBeginning(seqMonitor,monitoredObject,numToAdd,false);
            break;
          case MIDDLE:
            numExpectedCalls=argType.initContainsMiddle(seqMonitor,monitoredObject,numToAdd,false);
            break;
          case NEAREND:
            numExpectedCalls=argType.initContainsNearEnd(seqMonitor,monitoredObject,numToAdd,false);
            break;
          case END:
            numExpectedCalls=argType.initContainsEnd(seqMonitor,monitoredObject,numToAdd,false);
            break;
          case IOBHI:
            argType.initDoesNotContain(seqMonitor,numToAdd);
            numExpectedCalls=seqMonitor.expectedSeqSize;
            break;
          default:
            throw new Error("Unknown seqLocation "+seqLocation);
        }
        int seqSize=seqMonitor.expectedSeqSize;
        seqMonitor.illegalAdd(preModScenario);
        Class<? extends Throwable> expectedException=preModScenario.expectedException==null?monitoredObjectGen.expectedException:preModScenario.expectedException;
        Assertions.assertThrows(expectedException,()->argType.invokelastIndexOfMonitored(seqMonitor,monitoredObject));
        seqMonitor.verifyStructuralIntegrity();
        var verifyItr=seqMonitor.verifyPreAlloc().skip(seqSize);
        switch(monitoredObjectGen){
          case ModSeq:
            if(preModScenario==PreModScenario.NoMod){
              for(int i=0;i<numExpectedCalls;++i){
                verifyItr.verifyIllegalAdd();
              }
            }else{
              numExpectedCalls=1;
            }
            verifyItr.verifyPostAlloc(preModScenario);
            break;
          case ModParent:
            verifyItr.verifyParentPostAlloc();
            if(preModScenario==PreModScenario.ModParent){
              verifyItr.verifyIllegalAdd();
            }
            if(preModScenario==PreModScenario.ModRoot){
              numExpectedCalls=1;
            }else{
              for(int i=0;i<numExpectedCalls;++i){
                verifyItr.verifyIllegalAdd();
              }
            }
            verifyItr.verifyRootPostAlloc();
            if(preModScenario==PreModScenario.ModRoot){
              verifyItr.verifyIllegalAdd();
            }
            break;
          case ModRoot:
            verifyItr.verifyPostAlloc(preModScenario);
            for(int i=0;i<numExpectedCalls;++i){
              verifyItr.verifyIllegalAdd();
            }
            break;
          case Throw:
            numExpectedCalls=1;
            verifyItr.verifyPostAlloc(preModScenario);
            break;
          case ThrowModSeq:
            numExpectedCalls=1;
            if(preModScenario==PreModScenario.NoMod){
              verifyItr.verifyIllegalAdd();
            }
            verifyItr.verifyPostAlloc(preModScenario);
            break;
          case ThrowModParent:
            numExpectedCalls=1;
            verifyItr.verifyParentPostAlloc();
            switch(preModScenario){
              case NoMod:
                verifyItr.verifyIllegalAdd().verifyRootPostAlloc();
                break;
              case ModParent:
                verifyItr.verifyIllegalAdd().verifyIllegalAdd().verifyRootPostAlloc();
                break;
              case ModRoot:
                verifyItr.verifyRootPostAlloc().verifyIllegalAdd();
                break;
              default:
                throw new Error("Unknown preModScenario "+preModScenario);
            }
            break;
          case ThrowModRoot:
            numExpectedCalls=1;
            verifyItr.verifyPostAlloc(preModScenario).verifyIllegalAdd();
            break;
          default:
            throw new Error("Unknown monitoredObjectGen "+monitoredObjectGen);
        }
        Assertions.assertEquals(numExpectedCalls,monitoredObject.numEqualsCalls);
        return;
      }else
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
      {
          Assertions.assertThrows(preModScenario.expectedException,()->argType.invokelastIndexOf(seqMonitor,queryCastType));
      }
    }
    seqMonitor.verifyStructuralIntegrity();
    seqMonitor.verifyPreAlloc().skip(seqSize).verifyPostAlloc(preModScenario);
  }
  @org.junit.jupiter.api.Test
  public void testsearch_val(){
    runQueryTests(true,NestedType.LISTDEQUE,(checkedType,argType,queryCastType,seqLocation,seqSize,preModScenario
      ,monitoredObjectGen
    )->{
      testsearch_valHelper(new SeqMonitor(NestedType.LISTDEQUE,checkedType),argType,queryCastType,seqLocation,seqSize,preModScenario
      ,monitoredObjectGen
      );
    });
  }
  private static void testsearch_valHelper
  (SeqMonitor seqMonitor,QueryTester argType,QueryCastType queryCastType,SequenceLocation seqLocation,int numToAdd,PreModScenario preModScenario 
    ,MonitoredObjectGen monitoredObjectGen
  ){
    int expectedIndex;
    if(numToAdd!=0){
      if(monitoredObjectGen!=null){
        int numExpectedCalls;
        final var monitoredObject=monitoredObjectGen.getMonitoredObject(seqMonitor);
        switch(seqLocation){
          case BEGINNING:
            numExpectedCalls=argType.initContainsBeginning(seqMonitor,monitoredObject,numToAdd,true);
            break;
          case NEARBEGINNING:
            numExpectedCalls=argType.initContainsNearBeginning(seqMonitor,monitoredObject,numToAdd,true);
            break;
          case MIDDLE:
            numExpectedCalls=argType.initContainsMiddle(seqMonitor,monitoredObject,numToAdd,true);
            break;
          case NEAREND:
            numExpectedCalls=argType.initContainsNearEnd(seqMonitor,monitoredObject,numToAdd,true);
            break;
          case END:
            numExpectedCalls=argType.initContainsEnd(seqMonitor,monitoredObject,numToAdd,true);
            break;
          case IOBHI:
            argType.initDoesNotContain(seqMonitor,numToAdd);
            numExpectedCalls=seqMonitor.expectedSeqSize;
            break;
          default:
            throw new Error("Unknown seqLocation "+seqLocation);
        }
        int seqSize=seqMonitor.expectedSeqSize;
        seqMonitor.illegalAdd(preModScenario);
        Assertions.assertThrows(monitoredObjectGen.expectedException,()->argType.invokesearchMonitored(seqMonitor,monitoredObject));
        seqMonitor.verifyStructuralIntegrity();
        var verifyItr=seqMonitor.verifyPreAlloc().skip(seqSize);
        switch(monitoredObjectGen){
          case ModSeq:
            for(int i=0;i<numExpectedCalls;++i){
              verifyItr.verifyIllegalAdd();
            }
            break;
          case ThrowModSeq:
            verifyItr.verifyIllegalAdd();
          case Throw:
            numExpectedCalls=1;
            break;
          default:
            throw new Error("Unknown monitoredObjectGen "+monitoredObjectGen);
        }
        verifyItr.verifyPostAlloc();
        Assertions.assertEquals(numExpectedCalls,monitoredObject.numEqualsCalls);
        return;
      }else
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
  }
  @org.junit.jupiter.api.Test
  public void testremoveVal_val(){
    for(var nestedType:NestedType.values()){
      runQueryTests(true,nestedType,(checkedType,argType,queryCastType,seqLocation,seqSize,preModScenario
      ,monitoredObjectGen
      )->{
        switch(nestedType){
          case SUBLIST:
            if(preModScenario.expectedException==null && (seqLocation==SequenceLocation.BEGINNING||seqLocation==SequenceLocation.END)
              && (monitoredObjectGen==null || monitoredObjectGen.expectedException==null)
            ){
              for(int parentsLength=1;parentsLength<=3;++parentsLength){
                if(parentsLength!=1 || (preModScenario!=PreModScenario.ModParent
                && (monitoredObjectGen==null|| (monitoredObjectGen!=MonitoredObjectGen.ModParent && monitoredObjectGen!=MonitoredObjectGen.ThrowModParent))
                 )){
                  runSubListTests(true,checkedType,true,parentsLength,seqMonitor->testremoveVal_valHelper(seqMonitor,argType,queryCastType,seqLocation,seqSize,preModScenario
                    ,monitoredObjectGen
                  ));
                }
              }
              break;
            }
          case LISTDEQUE:
            testremoveVal_valHelper(new SeqMonitor(nestedType,checkedType),argType,queryCastType,seqLocation,seqSize,preModScenario
              ,monitoredObjectGen
            );
            break;
          default:
            throw new Error("Unknown nested type "+nestedType);
        }
      });
    }
  }
  private static void testremoveVal_valHelper
  (SeqMonitor seqMonitor,QueryTester argType,QueryCastType queryCastType,SequenceLocation seqLocation,int numToAdd,PreModScenario preModScenario 
    ,MonitoredObjectGen monitoredObjectGen
  ){
    if(numToAdd!=0){
      if(monitoredObjectGen!=null){
        int numExpectedCalls;
        final var monitoredObject=monitoredObjectGen.getMonitoredObject(seqMonitor);
        switch(seqLocation){
          case BEGINNING:
            numExpectedCalls=argType.initContainsBeginning(seqMonitor,monitoredObject,numToAdd,true);
            break;
          case NEARBEGINNING:
            numExpectedCalls=argType.initContainsNearBeginning(seqMonitor,monitoredObject,numToAdd,true);
            break;
          case MIDDLE:
            numExpectedCalls=argType.initContainsMiddle(seqMonitor,monitoredObject,numToAdd,true);
            break;
          case NEAREND:
            numExpectedCalls=argType.initContainsNearEnd(seqMonitor,monitoredObject,numToAdd,true);
            break;
          case END:
            numExpectedCalls=argType.initContainsEnd(seqMonitor,monitoredObject,numToAdd,true);
            break;
          case IOBHI:
            argType.initDoesNotContain(seqMonitor,numToAdd);
            numExpectedCalls=seqMonitor.expectedSeqSize;
            break;
          default:
            throw new Error("Unknown seqLocation "+seqLocation);
        }
        int seqSize=seqMonitor.expectedSeqSize;
        seqMonitor.illegalAdd(preModScenario);
        Class<? extends Throwable> expectedException=preModScenario.expectedException==null?monitoredObjectGen.expectedException:preModScenario.expectedException;
        Assertions.assertThrows(expectedException,()->argType.invokeremoveValMonitored(seqMonitor,monitoredObject));
        seqMonitor.verifyStructuralIntegrity();
        var verifyItr=seqMonitor.verifyPreAlloc().skip(seqSize);
        switch(monitoredObjectGen){
          case ModSeq:
            if(preModScenario==PreModScenario.NoMod){
              if(seqLocation==SequenceLocation.IOBHI){
                ++numExpectedCalls;
              }
              for(int i=0;i<numExpectedCalls;++i){
                verifyItr.verifyIllegalAdd();
              }
            }else{
              numExpectedCalls=1;
            }
            verifyItr.verifyPostAlloc(preModScenario);
            break;
          case ModParent:
            verifyItr.verifyParentPostAlloc();
            if(preModScenario==PreModScenario.ModParent){
              verifyItr.verifyIllegalAdd();
            }
            if(preModScenario==PreModScenario.ModRoot){
              numExpectedCalls=1;
            }else{
              for(int i=0;i<numExpectedCalls;++i){
                verifyItr.verifyIllegalAdd();
              }
            }
            verifyItr.verifyRootPostAlloc();
            if(preModScenario==PreModScenario.ModRoot){
              verifyItr.verifyIllegalAdd();
            }
            break;
          case ModRoot:
            verifyItr.verifyPostAlloc(preModScenario);
            for(int i=0;i<numExpectedCalls;++i){
              verifyItr.verifyIllegalAdd();
            }
            break;
          case Throw:
            numExpectedCalls=1;
            verifyItr.verifyPostAlloc(preModScenario);
            break;
          case ThrowModSeq:
            numExpectedCalls=1;
            if(preModScenario==PreModScenario.NoMod){
              verifyItr.verifyIllegalAdd();
            }
            verifyItr.verifyPostAlloc(preModScenario);
            break;
          case ThrowModParent:
            numExpectedCalls=1;
            verifyItr.verifyParentPostAlloc();
            switch(preModScenario){
              case NoMod:
                verifyItr.verifyIllegalAdd().verifyRootPostAlloc();
                break;
              case ModParent:
                verifyItr.verifyIllegalAdd().verifyIllegalAdd().verifyRootPostAlloc();
                break;
              case ModRoot:
                verifyItr.verifyRootPostAlloc().verifyIllegalAdd();
                break;
              default:
                throw new Error("Unknown preModScenario "+preModScenario);
            }
            break;
          case ThrowModRoot:
            numExpectedCalls=1;
            verifyItr.verifyPostAlloc(preModScenario).verifyIllegalAdd();
            break;
          default:
            throw new Error("Unknown monitoredObjectGen "+monitoredObjectGen);
        }
        Assertions.assertEquals(numExpectedCalls,monitoredObject.numEqualsCalls);
        return;
      }else
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
      {
          Assertions.assertThrows(preModScenario.expectedException,()->argType.invokeremoveVal(seqMonitor,queryCastType));
      }
    }
    seqMonitor.verifyStructuralIntegrity();
    seqMonitor.verifyPreAlloc().skip(seqSize).verifyPostAlloc(preModScenario);
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
                    Stream.of(RefInputTestArgType.values())
                    .parallel()
                    .forEach(inputArgType->{
                      switch(nestedType){
                        case SUBLIST:
                          if(inputArgType==RefInputTestArgType.ARRAY_TYPE){
                            for(int parentsLength=1;parentsLength<=3;++parentsLength){
                              if(parentsLength!=1 || preModScenario!=PreModScenario.ModParent){
                                runSubListTests(false,checkedType,true,parentsLength,seqMonitor->testListadd_int_valHelper(seqMonitor,inputArgType,seqLocation,preModScenario,finalSeqSize));
                              }
                            }
                            break;
                          }
                        case LISTDEQUE:
                          testListadd_int_valHelper(new SeqMonitor(nestedType,checkedType),inputArgType,seqLocation,preModScenario,finalSeqSize);
                          break;
                        default:
                          throw new Error("Unknown nested type "+nestedType);
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
  private static void testListadd_int_valHelper
  (SeqMonitor seqMonitor,RefInputTestArgType inputArgType,SequenceLocation seqLocation,PreModScenario preModScenario,int numToAdd){
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
  public void testListItradd_val(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var preModScenario:PreModScenario.values()){
          if((preModScenario.appliesToRootItr || !nestedType.rootType) && (checkedType.checked || preModScenario.expectedException==null)){
            for(int seqSize=0;seqSize<=10;++seqSize){
              final int finalSeqSize=seqSize;
              for(var seqLocation:SequenceLocation.values()){
                if(seqLocation.expectedException==null){
                  Stream.of(RefInputTestArgType.values())
                  .parallel()
                  .forEach(inputArgType->{
                    switch(nestedType){
                      case SUBLIST:
                        if(inputArgType==RefInputTestArgType.ARRAY_TYPE){
                          for(int parentsLength=1;parentsLength<=3;++parentsLength){
                            if(parentsLength!=1 || preModScenario!=PreModScenario.ModParent){
                              runSubListTests(false,checkedType,true,parentsLength,seqMonitor->testListItradd_valHelper(seqMonitor,preModScenario,finalSeqSize,seqLocation,inputArgType));
                            }
                          }
                          break;
                        }
                      case LISTDEQUE:
                        testListItradd_valHelper(new SeqMonitor(nestedType,checkedType),preModScenario,finalSeqSize,seqLocation,inputArgType);
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
  private static void testListItradd_valHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,int numToAdd,SequenceLocation seqLocation,RefInputTestArgType inputArgType){
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
  public void testremoveIf_Predicate(){
    final int[] seqSizes=new int[]{0,1,2,3,4,5,10,20,30,40,50,60,70,80,90,100};
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var functionCallType:FunctionCallType.values()){
          if(functionCallType==FunctionCallType.Boxed){
            continue;
          }
          for(var preModScenario:PreModScenario.values()){
            if(preModScenario==PreModScenario.ModSeq || (preModScenario.expectedException!=null && !checkedType.checked) || (nestedType.rootType && preModScenario!=PreModScenario.NoMod)){
              continue;
            }
            for(var monitoredRemoveIfPredicateGen:MonitoredRemoveIfPredicateGen.values()){
              if(monitoredRemoveIfPredicateGen.expectedException!=null && (!checkedType.checked || (nestedType.rootType && !monitoredRemoveIfPredicateGen.appliesToRoot))){
                continue;
              }
              for(int seqSize:seqSizes){
                System.out.println("RefDblLnkSeq.testremoveIf_Predicate<"+nestedType+","+checkedType+","+functionCallType+","+preModScenario+","+monitoredRemoveIfPredicateGen+","+seqSize+">");
                double[] thresholdArr;
                long randSeedBound;
                if(seqSize==0 || !monitoredRemoveIfPredicateGen.isRandomized){
                  thresholdArr=new double[]{0.5};
                  randSeedBound=0;
                }else{
                  thresholdArr=new double[]{0.01,0.05,0.10,0.25,0.50,0.75,0.90,0.95,0.99};
                  randSeedBound=100;
                }
                var randStream=LongStream.rangeClosed(0,randSeedBound);
                if(randSeedBound!=0){
                  randStream=randStream.parallel();
                }
                randStream.forEach(randSeed->{
                  for(var threshold:thresholdArr){
                    switch(nestedType){
                      case SUBLIST:
                        if(functionCallType==FunctionCallType.Unboxed && monitoredRemoveIfPredicateGen.expectedException==null && preModScenario.expectedException==null){
                          for(int parentsLength=1;parentsLength<=3;++parentsLength){
                            if(parentsLength!=1 || (preModScenario!=PreModScenario.ModParent && monitoredRemoveIfPredicateGen!=MonitoredRemoveIfPredicateGen.ModParent && monitoredRemoveIfPredicateGen!=MonitoredRemoveIfPredicateGen.ThrowModParent)){
                              runSubListTests(true,checkedType,randSeedBound==0?true:false,parentsLength,seqMonitor->testremoveIf_PredicateHelper(seqMonitor,preModScenario,monitoredRemoveIfPredicateGen,threshold,randSeed,functionCallType,seqSize));
                            }
                          }
                          break;
                        }
                      case LISTDEQUE:
                        testremoveIf_PredicateHelper(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredRemoveIfPredicateGen,threshold,randSeed,functionCallType,seqSize);
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
  private static void testremoveIf_PredicateHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredRemoveIfPredicateGen monitoredRemoveIfPredicateGen,double threshold,long randSeed,final FunctionCallType functionCallType,int seqSize
  ){
    for(int i=0;i<seqSize;++i){
      seqMonitor.add(i);
    }
    final var clone=(OmniCollection.OfRef)seqMonitor.seq.clone();
    final int numExpectedCalls=seqSize;
    final int numExpectedRemoved;
    switch(monitoredRemoveIfPredicateGen){
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
      verifyItr.verifyLiteralIndexAndIterate(cloneItr.next());
    }
    switch(monitoredRemoveIfPredicateGen){
      case ModRoot:
      case ThrowModRoot:
        //The nature of concurrent modification makes verifying the contents of the array tricky due to array reallocations
        //skip it in this scenario
      case Random:
      case RemoveAll:
      case RemoveNone:
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
  public void testListremoveAt_int(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var preModScenario:PreModScenario.values()){
          if(preModScenario!=PreModScenario.ModSeq && (preModScenario.expectedException==null || (!nestedType.rootType && checkedType.checked))){
            for(var seqLocation:SequenceLocation.values()){
              if(checkedType.checked || seqLocation.expectedException==null){
                for(int seqSize:AbstractRefSeqMonitor.FIB_SEQ){
                  if(seqSize!=0|| (seqLocation==SequenceLocation.IOBHI)){
                    for(var outputArgType:RefOutputTestArgType.values()){
                      switch(nestedType){
                        case SUBLIST:
                          if(preModScenario.expectedException==null && seqLocation==SequenceLocation.BEGINNING || seqLocation==SequenceLocation.END){
                            for(int parentsLength=1;parentsLength<=3;++parentsLength){
                              if(parentsLength!=1 || (preModScenario!=PreModScenario.ModParent)){
                                runSubListTests(true,checkedType,true,parentsLength,seqMonitor->testListremoveAt_intHelper(seqMonitor,preModScenario,seqLocation,seqSize,outputArgType));
                              }
                            }
                            break;
                          }
                        case LISTDEQUE:
                          testListremoveAt_intHelper(new SeqMonitor(nestedType,checkedType),preModScenario,seqLocation,seqSize,outputArgType);
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
  private static void testListremoveAt_intHelper(SeqMonitor seqMonitor,PreModScenario preModScenario,SequenceLocation seqLocation,int numToAdd,RefOutputTestArgType outputArgType){
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
  static Stream<Arguments> getItrforEachRemaining_ConsumerArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var checkedType:CheckedType.values()){
      for(var nestedType:NestedType.values()){
        for(var preModScenario:PreModScenario.values()){
          if(checkedType.checked || preModScenario.expectedException==null){
            for(var monitoredFunctionGen:MonitoredFunctionGen.values()){
              if((monitoredFunctionGen.expectedException==null || checkedType.checked) && (!nestedType.rootType || (preModScenario.appliesToRootItr&&monitoredFunctionGen.appliesToRootItr))){
                for(var itrType:ItrType.values()){
                  if((itrType!=ItrType.DescendingItr||nestedType.rootType)){
                    for(int seqSize:AbstractRefSeqMonitor.FIB_SEQ){
                      builder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredFunctionGen,itrType,seqSize,FunctionCallType.Unboxed));
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    return builder.build();
  }
  @org.junit.jupiter.api.Test
  public void testItrforEachRemaining_Consumer(){
    getItrforEachRemaining_ConsumerArgs().parallel().map(Arguments::get).forEach(args->{
        testItrforEachRemaining_ConsumerHelper((SeqMonitor)args[0],(PreModScenario)args[1],(MonitoredFunctionGen)args[2],(ItrType)args[3],(int)args[4],(FunctionCallType)args[5]);
    });
  }
  private static void testItrforEachRemaining_ConsumerHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredFunctionGen monitoredFunctionGen,ItrType itrType,int numToAdd,FunctionCallType functionCallType){
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
            Assertions.assertSame(encounteredValue,currNode.val);
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
            Assertions.assertSame(encounteredValue,currNode.val);
            currNode=currNode.prev;
          }
        }
      }
    }
  }
  static Stream<Arguments> getItrremove_voidArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var checkedType:CheckedType.values()){
      for(var removeScenario:ItrRemoveScenario.values()){
        if(checkedType.checked || removeScenario.expectedException==null){
          for(int seqSize:AbstractRefSeqMonitor.FIB_SEQ){
            if(seqSize!=0 || removeScenario.validWithEmptySeq){
              for(var preModScenario:PreModScenario.values()){
                if(checkedType.checked || preModScenario.expectedException==null){
                  for(var itrType:ItrType.values()){
                    if(itrType==ItrType.ListItr || (removeScenario!=ItrRemoveScenario.PostAdd && removeScenario!=ItrRemoveScenario.PostPrevious))
                    {
                      for(var nestedType:NestedType.values()){
                        if((!nestedType.rootType || (preModScenario!=PreModScenario.ModParent && preModScenario!=PreModScenario.ModRoot)) && (nestedType.rootType || itrType!=ItrType.DescendingItr))
                        {
                          for(var seqLocation:SequenceLocation.values()){
                            if(seqLocation.expectedException==null && (itrType==ItrType.ListItr || (removeScenario!=ItrRemoveScenario.PostInit || seqLocation==SequenceLocation.BEGINNING))
                            )
                            {
                              builder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),removeScenario,preModScenario,seqSize,itrType,seqLocation));
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
    return builder.build();
  }
  @org.junit.jupiter.api.Test
  public void testItrremove_void(){
    getItrremove_voidArgs().parallel().map(Arguments::get).forEach(args->{
        testItrremove_voidHelper((SeqMonitor)args[0],(ItrRemoveScenario)args[1],(PreModScenario)args[2],(int)args[3],(ItrType)args[4],(SequenceLocation)args[5]);
    });
  }
  private static void testItrremove_voidHelper
  (SeqMonitor seqMonitor,ItrRemoveScenario removeScenario,PreModScenario preModScenario,int numToAdd,ItrType itrType,SequenceLocation seqLocation){
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
          throw new Error("Invalid test{removeScenario="+removeScenario+", numToAdd="+numToAdd+", itrType="+itrType+"} hasPrevious returned false");
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
  }
  static Stream<Arguments> getListItrpreviousIndex_void_And_ListItrnextIndex_voidArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var checkedType:CheckedType.values()){
      for(var nestedType:NestedType.values()){
        builder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType)));
      }  
    }
    return builder.build();
  }
  @org.junit.jupiter.api.Test
  public void testListItrpreviousIndex_void_And_ListItrnextIndex_void(){
    getListItrpreviousIndex_void_And_ListItrnextIndex_voidArgs().parallel().map(Arguments::get).forEach(args->{
        testListItrpreviousIndex_void_And_ListItrnextIndex_voidHelper((SeqMonitor)args[0]);
    });
  }
  private static void testListItrpreviousIndex_void_And_ListItrnextIndex_voidHelper
  (SeqMonitor seqMonitor){
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
  }
  static Stream<Arguments> getListItrset_valArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var checkedType:CheckedType.values()){
      for(var listItrSetScenario:ListItrSetScenario.values()){
        if(checkedType.checked || listItrSetScenario.expectedException==null){
          for(var inputArgType:RefInputTestArgType.values()){
            if(listItrSetScenario.preModScenario.appliesToRootItr){
              builder.accept(Arguments.of(new SeqMonitor(NestedType.LISTDEQUE,checkedType),listItrSetScenario,inputArgType));
            }
            builder.accept(Arguments.of(new SeqMonitor(NestedType.SUBLIST,checkedType),listItrSetScenario,inputArgType));
          }
        }
      }
    }
    return builder.build();
  }
  @org.junit.jupiter.api.Test
  public void testListItrset_val(){
    getListItrset_valArgs().parallel().map(Arguments::get).forEach(args->{
        testListItrset_valHelper((SeqMonitor)args[0],(ListItrSetScenario)args[1],(RefInputTestArgType)args[2]);
    });
  }
  private static void testListItrset_valHelper
  (SeqMonitor seqMonitor,ListItrSetScenario listItrSetScenario,RefInputTestArgType inputArgType){
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
  static Stream<Arguments> getItrnext_voidArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var checkedType:CheckedType.values()){
      for(var itrScenario:IterationScenario.values()){
        if(checkedType.checked || itrScenario==IterationScenario.NoMod){
          for(int seqSize:AbstractRefSeqMonitor.FIB_SEQ){
            if(seqSize!=0 || itrScenario.validWithEmptySeq){
              for(var itrType:ItrType.values()){
                for(var nestedType:NestedType.values()){
                  if((itrType!=ItrType.DescendingItr || nestedType.rootType) && (itrScenario.preModScenario.appliesToRootItr || !nestedType.rootType)){
                    for(var outputType:RefOutputTestArgType.values()){
                      builder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),itrScenario,seqSize,itrType,outputType));
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    return builder.build();
  }
  @org.junit.jupiter.api.Test
  public void testItrnext_void(){
    getItrnext_voidArgs().parallel().map(Arguments::get).forEach(args->{
        testItrnext_voidHelper((SeqMonitor)args[0],(IterationScenario)args[1],(int)args[2],(ItrType)args[3],(RefOutputTestArgType)args[4]);
    });
  }
  private static void testItrnext_voidHelper
  (SeqMonitor seqMonitor,IterationScenario itrScenario,int numToAdd,ItrType itrType,RefOutputTestArgType outputType){
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
  }
  static Stream<Arguments> getListItrprevious_voidArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var checkedType:CheckedType.values()){
      for(var itrScenario:IterationScenario.values()){
        if(checkedType.checked || itrScenario==IterationScenario.NoMod){
          for(int seqSize:AbstractRefSeqMonitor.FIB_SEQ){
            if(seqSize!=0 || itrScenario.validWithEmptySeq){
              for(var outputType:RefOutputTestArgType.values()){
                if(itrScenario.preModScenario.appliesToRootItr){
                  builder.accept(Arguments.of(new SeqMonitor(NestedType.LISTDEQUE,checkedType),itrScenario,seqSize,outputType));
                }
                builder.accept(Arguments.of(new SeqMonitor(NestedType.SUBLIST,checkedType),itrScenario,seqSize,outputType));
              }
            }
          }
        }
      }
    }
    return builder.build();
  }
  @org.junit.jupiter.api.Test
  public void testListItrprevious_void(){
    getListItrprevious_voidArgs().parallel().map(Arguments::get).forEach(args->{
        testListItrprevious_voidHelper((SeqMonitor)args[0],(IterationScenario)args[1],(int)args[2],(RefOutputTestArgType)args[3]);
    });
  }
  private static void testListItrprevious_voidHelper
  (SeqMonitor seqMonitor,IterationScenario itrScenario,int numToAdd,RefOutputTestArgType outputType){
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
  static Stream<Arguments> getforEach_ConsumerArgs(){
    return ArgBuilder.buildSeqArgs((streamBuilder,nestedType,checkedType,preModScenario)->{
      for(var monitoredFunctionGen:MonitoredFunctionGen.values()){
        if((checkedType.checked || monitoredFunctionGen.expectedException==null)&&(!nestedType.rootType || monitoredFunctionGen.appliesToRoot) &&(nestedType.rootType || monitoredFunctionGen.appliesToSubList)){
          for(int seqSize:AbstractRefSeqMonitor.FIB_SEQ){
            streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredFunctionGen,seqSize,FunctionCallType.Unboxed));
          }
        }
      }
    });
  }
  @org.junit.jupiter.api.Test
  public void testforEach_Consumer(){
    getforEach_ConsumerArgs().parallel().map(Arguments::get).forEach(args->{
        testforEach_ConsumerHelper((SeqMonitor)args[0],(PreModScenario)args[1],(MonitoredFunctionGen)args[2],(int)args[3],(FunctionCallType)args[4]);
    });
  }
  private static void testforEach_ConsumerHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredFunctionGen monitoredFunctionGen,int numToAdd,FunctionCallType functionCallType){
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
      Assertions.assertSame(encounteredValue,currNode.val);
      currNode=currNode.next;
    }
  }
  static Stream<Arguments> getListreplaceAll_UnaryOperatorArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var checkedType:CheckedType.values()){
      for(var nestedType:NestedType.values()){
        if(nestedType.forwardIteration){
          for(var preModScenario:PreModScenario.values()){
            if(preModScenario!=PreModScenario.ModSeq&&(preModScenario.expectedException==null||(!nestedType.rootType&&checkedType.checked))){
              for(var monitoredFunctionGen:MonitoredFunctionGen.values()){
                if((checkedType.checked || monitoredFunctionGen.expectedException==null)&&(!nestedType.rootType || monitoredFunctionGen.appliesToRoot) &&(nestedType.rootType || monitoredFunctionGen.appliesToSubList)){
                  for(int seqSize:AbstractRefSeqMonitor.FIB_SEQ){
                    builder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredFunctionGen,seqSize,FunctionCallType.Unboxed));
                  }
                }
              }   
            }
          }
        }
      }
    }
    return builder.build();
  }
  @org.junit.jupiter.api.Test
  public void testListreplaceAll_UnaryOperator(){
    getListreplaceAll_UnaryOperatorArgs().parallel().map(Arguments::get).forEach(args->{
        testListreplaceAll_UnaryOperatorHelper((SeqMonitor)args[0],(PreModScenario)args[1],(MonitoredFunctionGen)args[2],(int)args[3],(FunctionCallType)args[4]);
    });
  }
  private static void testListreplaceAll_UnaryOperatorHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredFunctionGen monitoredFunctionGen,int numToAdd,FunctionCallType functionCallType){
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
    RefDblLnkNode currNode=seqMonitor.seq.head;
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
        var expectedVal=(Object)(Integer.valueOf((int)encounteredValue)+1);
        Assertions.assertEquals(expectedVal,currNode.val);
        currNode=currNode.next;
      }
    }
    else
    {
      for(var encounteredValue:monitoredUnaryOperator.encounteredValues){
        Assertions.assertSame(encounteredValue,currNode.val);
        currNode=currNode.next;
      }
    }
  }
  @org.junit.jupiter.api.Test
  public void testclone_void(){
    getBasicCollectionTestArgs().parallel().map(Arguments::get).forEach(args->{
        testclone_voidHelper((SeqMonitor)args[0],(PreModScenario)args[1],(int)args[2]);
    });
  }
  private static void testclone_voidHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,int numToAdd){
    for(int i=0;i<numToAdd;++i){
      seqMonitor.add(i);
    }
    seqMonitor.illegalAdd(preModScenario);
    if(preModScenario.expectedException==null){
      var clone=(OmniCollection.OfRef)seqMonitor.seq.clone();
      Assertions.assertNotSame(clone,seqMonitor.seq);
      if(seqMonitor.checkedType.checked){
        Assertions.assertTrue(clone instanceof RefDblLnkSeq.CheckedList);
        Assertions.assertEquals(0,((RefDblLnkSeq.CheckedList)clone).modCount);
      }else{
        Assertions.assertTrue(clone instanceof RefDblLnkSeq.UncheckedList);
      }
      var dblLnkSeqClone=(RefDblLnkSeq)clone;
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
          Assertions.assertSame(originalHead.val,cloneHead.val);
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
  }
  static Stream<Arguments> getListsubList_int_intArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var nestedType:NestedType.values()){
      if(nestedType.forwardIteration){
        for(var checkedType:CheckedType.values()){
          for(var preModScenario:PreModScenario.values()){
            if(preModScenario!=PreModScenario.ModSeq && (preModScenario.expectedException==null || (checkedType.checked && !nestedType.rootType))){
              for(int seqSize=0;seqSize<=5;++seqSize){
                for(int fromIndex=-2;fromIndex<=seqSize+1;++fromIndex){
                  if(checkedType.checked || fromIndex>=0){
                    for(int toIndex=-1;toIndex<=seqSize+2;++toIndex){
                      if(checkedType.checked || (toIndex>=fromIndex && toIndex<=seqSize)){
                        builder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario,seqSize,fromIndex,toIndex));
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
    return builder.build();
  }
  @org.junit.jupiter.api.Test
  public void testListsubList_int_int(){
    getListsubList_int_intArgs().parallel().map(Arguments::get).forEach(args->{
        testListsubList_int_intHelper((SeqMonitor)args[0],(PreModScenario)args[1],(int)args[2],(int)args[3],(int)args[4]);
    });
  }
  private static void testListsubList_int_intHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,int seqSize,int fromIndex,int toIndex){
    for(int i=0;i<seqSize;++i){
      seqMonitor.add(i);
    }
    seqMonitor.illegalAdd(preModScenario);
    if(preModScenario.expectedException==null){
      if(fromIndex>=0 && fromIndex<=toIndex && toIndex<=seqSize){
        var subList=(RefDblLnkSeq)seqMonitor.seq.subList(fromIndex,toIndex);
        if(seqMonitor.checkedType.checked){
          Assertions.assertEquals(seqMonitor.expectedSeqModCount,FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.modCount(subList));
          if(seqMonitor.nestedType.rootType){
            Assertions.assertNull(FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.parent(subList));
            Assertions.assertSame(seqMonitor.seq,FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.root(subList));
          }else{
            Assertions.assertSame(seqMonitor.seq,FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.parent(subList));
            Assertions.assertSame(seqMonitor.parents[seqMonitor.parents.length-1],FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.root(subList));
          }
          Assertions.assertEquals(fromIndex,FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.parentOffset(subList));
        }else{
          if(seqMonitor.nestedType.rootType){
            Assertions.assertNull(FieldAndMethodAccessor.RefDblLnkSeq.UncheckedSubList.parent(subList));
            Assertions.assertSame(seqMonitor.seq,FieldAndMethodAccessor.RefDblLnkSeq.UncheckedSubList.root(subList));
          }else{
            Assertions.assertSame(seqMonitor.seq,FieldAndMethodAccessor.RefDblLnkSeq.UncheckedSubList.parent(subList));
            Assertions.assertSame(seqMonitor.parents[seqMonitor.parents.length-1],FieldAndMethodAccessor.RefDblLnkSeq.UncheckedSubList.root(subList));
          }
          Assertions.assertEquals(fromIndex,FieldAndMethodAccessor.RefDblLnkSeq.UncheckedSubList.parentOffset(subList));
        }
        Assertions.assertSame(toIndex-fromIndex,subList.size);
        var subListHead=subList.head;
        var subListTail=subList.tail;
        if(toIndex-fromIndex==0){
          Assertions.assertNull(subListHead);
          Assertions.assertNull(subListTail);
        }else{
          Assertions.assertSame(RefDblLnkNode.iterateAscending(seqMonitor.seq.head,fromIndex),subListHead);
          Assertions.assertSame(RefDblLnkNode.iterateDescending(seqMonitor.seq.tail,seqMonitor.seq.size-toIndex),subListTail);
        }
      }else{
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->seqMonitor.seq.subList(fromIndex,toIndex));
      }
    }else{
      Assertions.assertThrows(preModScenario.expectedException,()->seqMonitor.seq.subList(fromIndex,toIndex));
    }
    seqMonitor.verifyStructuralIntegrity();
    seqMonitor.verifyPreAlloc().verifyAscending(seqSize).verifyPostAlloc(preModScenario);
  }
  static Stream<Arguments> getListlistIterator_intArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var nestedType:NestedType.values()){
      if(nestedType.forwardIteration){
        for(var checkedType:CheckedType.values()){
          for(var preModScenario:PreModScenario.values()){
            if(preModScenario!=PreModScenario.ModSeq && (preModScenario.expectedException==null || (checkedType.checked && !nestedType.rootType))){
              for(var seqLocation:SequenceLocation.values()){
                if(seqLocation.expectedException!=null && checkedType.checked){
                  builder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario,seqLocation));
                }
              }
            }
          }
        }
      }
    }
    return builder.build();
  }
  @org.junit.jupiter.api.Test
  public void testListlistIterator_int(){
    getListlistIterator_intArgs().parallel().map(Arguments::get).forEach(args->{
        testListlistIterator_intHelper((SeqMonitor)args[0],(PreModScenario)args[1],(SequenceLocation)args[2]);
    });
  }
  private static void testListlistIterator_intHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,SequenceLocation seqLocation){
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
  }
  static Stream<Arguments> getListlistIterator_voidArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var nestedType:NestedType.values()){
      if(nestedType.forwardIteration){
        for(var checkedType:CheckedType.values()){
          for(var preModScenario:PreModScenario.values()){
            if(preModScenario!=PreModScenario.ModSeq && (preModScenario.expectedException==null || (checkedType.checked && !nestedType.rootType))){
              builder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario));
            }
          }
        }
      }
    }
    return builder.build();
  }
  @org.junit.jupiter.api.Test
  public void testListlistIterator_void(){
    getListlistIterator_intArgs().parallel().map(Arguments::get).forEach(args->{
        testListlistIterator_voidHelper((SeqMonitor)args[0],(PreModScenario)args[1]);
    });
  }
  private static void testListlistIterator_voidHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario){
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
  }
  static Stream<Arguments> getiterator_voidArgs(){
    return ArgBuilder.buildSeqArgs((streamBuilder,nestedType,checkedType,preModScenario)->{
      streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario));
    });
  }
  @org.junit.jupiter.api.Test
  public void testiterator_void(){
    getiterator_voidArgs().parallel().map(Arguments::get).forEach(args->{
        testiterator_voidHelper((SeqMonitor)args[0],(PreModScenario)args[1]);
    });
  }
  private static void testiterator_voidHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario){
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
  }
  static Stream<Arguments> getdescendingIterator_voidArgs(){
    return ArgBuilder.buildSeqArgs((streamBuilder,nestedType,checkedType,preModScenario)->{
      streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario));
    });
  }
  @org.junit.jupiter.api.Test
  public void testdescendingIterator_void(){
    getdescendingIterator_voidArgs().parallel().map(Arguments::get).forEach(args->{
        testiterator_voidHelper((SeqMonitor)args[0],(PreModScenario)args[1]);
    });
  }
  private static void testdescendingIterator_voidHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario){
    for(int i=0;i<100;++i){
      seqMonitor.add(i);
    }
    seqMonitor.illegalAdd(preModScenario);
    if(preModScenario.expectedException==null){
      var itrMonitor=seqMonitor.getDescendingItrMonitor();
      itrMonitor.verifyIteratorState();
    }else{
      Assertions.assertThrows(preModScenario.expectedException,()->seqMonitor.getItrMonitor());
    }
    seqMonitor.verifyStructuralIntegrity();
    seqMonitor.verifyPreAlloc().verifyAscending(100).verifyPostAlloc(preModScenario);
  }
  @org.junit.jupiter.api.Test
  public void testhashCode_void(){
    gettoStringAndhashCode_voidArgs().parallel().map(Arguments::get).forEach(args->{
      testhashCode_voidHelper((SeqMonitor)args[0],(PreModScenario)args[1],(int)args[2]
      ,(MonitoredObjectGen)args[3]
      );
    });
  }
  private static void testhashCode_voidHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,int numToAdd
  ,MonitoredObjectGen monitoredObjectGen
  ){
    MonitoredObject monitoredObject=null;
    if(numToAdd!=0 && monitoredObjectGen.expectedException!=null){
      monitoredObject=monitoredObjectGen.getMonitoredObject(seqMonitor);
      for(int i=0;i<numToAdd;++i){
        seqMonitor.addVal(monitoredObject);
      }
    }else
    {
      for(int i=0;i<numToAdd;++i){
        seqMonitor.add(i);
      }
    }
    seqMonitor.illegalAdd(preModScenario);
    if(preModScenario.expectedException==null){
      if(monitoredObject!=null){
        Assertions.assertThrows(monitoredObjectGen.expectedException,()->seqMonitor.seq.hashCode());
        Assertions.assertEquals(seqMonitor.verifyThrowCondition(numToAdd,monitoredObject,monitoredObjectGen),monitoredObject.numHashCodeCalls);
      }else
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
        ,monitoredObject,monitoredObjectGen
      );
    }
    seqMonitor.verifyStructuralIntegrity();
  }
  @org.junit.jupiter.api.Test
  public void testtoString_void(){
    gettoStringAndhashCode_voidArgs().parallel().map(Arguments::get).forEach(args->{
      testtoString_voidHelper((SeqMonitor)args[0],(PreModScenario)args[1],(int)args[2]
      ,(MonitoredObjectGen)args[3]
      );
    });
  }
  private static void testtoString_voidHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,int numToAdd
  ,MonitoredObjectGen monitoredObjectGen
  ){
    MonitoredObject monitoredObject=null;
    if(numToAdd!=0 && monitoredObjectGen.expectedException!=null){
      monitoredObject=monitoredObjectGen.getMonitoredObject(seqMonitor);
      for(int i=0;i<numToAdd;++i){
        seqMonitor.addVal(monitoredObject);
      }
    }else
    {
      for(int i=0;i<numToAdd;++i){
        seqMonitor.add(i);
      }
    }
    seqMonitor.illegalAdd(preModScenario);
    if(preModScenario.expectedException==null){
      if(monitoredObject!=null){
        Assertions.assertThrows(monitoredObjectGen.expectedException,()->seqMonitor.seq.toString());
        Assertions.assertEquals(seqMonitor.verifyThrowCondition(numToAdd,monitoredObject,monitoredObjectGen),monitoredObject.numToStringCalls);
      }
      else
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
        ,monitoredObject,monitoredObjectGen
      );
    }
    seqMonitor.verifyStructuralIntegrity();
  }
  @org.junit.jupiter.api.Test
  public void testclear_void(){
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var preModScenario:PreModScenario.values()){
          if(preModScenario!=PreModScenario.ModSeq && (preModScenario.expectedException==null || (!nestedType.rootType && checkedType.checked))){
            for(int seqSize:AbstractRefSeqMonitor.FIB_SEQ){
              switch(nestedType){
                case SUBLIST:
                  if(preModScenario.expectedException==null){
                    for(int parentsLength=1;parentsLength<=3;++parentsLength){
                      if(parentsLength!=1 || (preModScenario!=PreModScenario.ModParent)){
                        runSubListTests(true,checkedType,true,parentsLength,seqMonitor->testclear_voidHelper(seqMonitor,preModScenario,seqSize));
                      }
                    }
                    break;
                  }
                case LISTDEQUE:
                  testclear_voidHelper(new SeqMonitor(nestedType,checkedType),preModScenario,seqSize);
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
  public void testListunstableAscendingSort_void(){
    getNonComparatorSortArgs().parallel().map(Arguments::get).forEach(args->{
        testListunstableAscendingSort_voidHelper((SeqMonitor)args[0],(PreModScenario)args[1],(MonitoredComparatorGen)args[2],(int)args[3]);
    });
  }
  private static void testListunstableAscendingSort_voidHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredComparatorGen monitoredComparatorGen,int numToAdd){
    monitoredComparatorGen.init(seqMonitor,numToAdd,preModScenario);
    if(preModScenario.expectedException==null){
      if(monitoredComparatorGen.expectedException==null || numToAdd<2){
        seqMonitor.unstableAscendingSort();
        monitoredComparatorGen.assertSorted(seqMonitor,numToAdd,preModScenario);
      }else{
        Assertions.assertThrows(monitoredComparatorGen.expectedException,()->seqMonitor.unstableAscendingSort());
        monitoredComparatorGen.assertUnmodified(seqMonitor,numToAdd,preModScenario);
      }
    }else{
      Assertions.assertThrows(preModScenario.expectedException,()->seqMonitor.unstableAscendingSort());
      monitoredComparatorGen.assertUnmodified(seqMonitor,numToAdd,preModScenario);
    }
  }
  @org.junit.jupiter.api.Test
  public void testListunstableDescendingSort_void(){
    getNonComparatorSortArgs().parallel().map(Arguments::get).forEach(args->{
        testListunstableDescendingSort_voidHelper((SeqMonitor)args[0],(PreModScenario)args[1],(MonitoredComparatorGen)args[2],(int)args[3]);
    });
  }
  private static void testListunstableDescendingSort_voidHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredComparatorGen monitoredComparatorGen,int numToAdd){
    monitoredComparatorGen.initReverse(seqMonitor,numToAdd,preModScenario);
    if(preModScenario.expectedException==null){
      if(monitoredComparatorGen.expectedException==null || numToAdd<2){
        seqMonitor.unstableDescendingSort();
        monitoredComparatorGen.assertReverseSorted(seqMonitor,numToAdd,preModScenario);
      }else{
        Assertions.assertThrows(monitoredComparatorGen.expectedException,()->seqMonitor.unstableDescendingSort());
        monitoredComparatorGen.assertReverseUnmodified(seqMonitor,numToAdd,preModScenario);
      }
    }else{
      Assertions.assertThrows(preModScenario.expectedException,()->seqMonitor.unstableDescendingSort());
      monitoredComparatorGen.assertReverseUnmodified(seqMonitor,numToAdd,preModScenario);
    }
  }
  @org.junit.jupiter.api.Test
  public void testListstableDescendingSort_void(){
    getNonComparatorSortArgs().parallel().map(Arguments::get).forEach(args->{
        testListstableDescendingSort_voidHelper((SeqMonitor)args[0],(PreModScenario)args[1],(MonitoredComparatorGen)args[2],(int)args[3]);
    });
  }
  private static void testListstableDescendingSort_voidHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredComparatorGen monitoredComparatorGen,int numToAdd){
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
  }
  @org.junit.jupiter.api.Test
  public void testListstableAscendingSort_void(){
    getNonComparatorSortArgs().parallel().map(Arguments::get).forEach(args->{
        testListstableAscendingSort_voidHelper((SeqMonitor)args[0],(PreModScenario)args[1],(MonitoredComparatorGen)args[2],(int)args[3]);
    });
  }
  private static void testListstableAscendingSort_voidHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredComparatorGen monitoredComparatorGen,int numToAdd){
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
  }
  static Stream<Arguments> getListunstableSort_ComparatorArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var nestedType:NestedType.values()){
      if(nestedType.forwardIteration){
        for(var checkedType:CheckedType.values()){
          for(var preModScenario:PreModScenario.values()){
            if((checkedType.checked || preModScenario.expectedException==null) && ((nestedType.rootType && preModScenario.expectedException==null)||(!nestedType.rootType && preModScenario.appliesToSubList))){
              for(var monitoredComparatorGen:MonitoredComparatorGen.values()){
                if((!nestedType.rootType || monitoredComparatorGen.appliesToRoot) && (checkedType.checked || monitoredComparatorGen.expectedException==null)){
                  for(int seqSize:new int[]{0,2}){
                    builder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredComparatorGen,seqSize));
                  }
                }
              }
            }
          }
        }
      }
    }
    return builder.build();
  }
  @org.junit.jupiter.api.Test
  public void testListunstableSort_Comparator(){
    getListunstableSort_ComparatorArgs().parallel().map(Arguments::get).forEach(args->{
        testListunstableSort_ComparatorHelper((SeqMonitor)args[0],(PreModScenario)args[1],(MonitoredComparatorGen)args[2],(int)args[3]);
    });
  }
  private static void testListunstableSort_ComparatorHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredComparatorGen monitoredComparatorGen,int numToAdd){
    monitoredComparatorGen.init(seqMonitor,numToAdd,preModScenario);
    var sorter=monitoredComparatorGen.getMonitoredComparator(seqMonitor);
    if(preModScenario.expectedException==null){
      if(monitoredComparatorGen.expectedException==null || numToAdd<2){
        seqMonitor.unstableSort(sorter);
        monitoredComparatorGen.assertSorted(seqMonitor,numToAdd,preModScenario);
      }else{
        Assertions.assertThrows(monitoredComparatorGen.expectedException,()->seqMonitor.unstableSort(sorter));
        monitoredComparatorGen.assertUnmodified(seqMonitor,numToAdd,preModScenario);
      }
    }else{
      Assertions.assertThrows(preModScenario.expectedException,()->seqMonitor.unstableSort(sorter));
      monitoredComparatorGen.assertUnmodified(seqMonitor,numToAdd,preModScenario);
    }
  }
  static Stream<Arguments> getListsort_ComparatorArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var nestedType:NestedType.values()){
      if(nestedType.forwardIteration){
        for(var checkedType:CheckedType.values()){
          for(var preModScenario:PreModScenario.values()){
            if((checkedType.checked || preModScenario.expectedException==null) && ((nestedType.rootType && preModScenario.expectedException==null)||(!nestedType.rootType && preModScenario.appliesToSubList))){
              for(var monitoredComparatorGen:MonitoredComparatorGen.values()){
                if((!nestedType.rootType || monitoredComparatorGen.appliesToRoot) && (checkedType.checked || monitoredComparatorGen.expectedException==null)){
                  for(int seqSize:new int[]{0,2}){
                    for(var functionCallType:FunctionCallType.values()){
                      builder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredComparatorGen,seqSize,functionCallType));
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    return builder.build();
  }
  @org.junit.jupiter.api.Test
  public void testListsort_Comparator(){
    getListsort_ComparatorArgs().parallel().map(Arguments::get).forEach(args->{
        testListsort_ComparatorHelper((SeqMonitor)args[0],(PreModScenario)args[1],(MonitoredComparatorGen)args[2],(int)args[3],(FunctionCallType)args[4]);
    });
  }
  private static void testListsort_ComparatorHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredComparatorGen monitoredComparatorGen,int numToAdd,FunctionCallType functionCallType){
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
  }
  static Stream<Arguments> gettoArray_IntFunctionArgs(){
    return ArgBuilder.buildSeqArgs((streamBuilder,nestedType,checkedType,preModScenario)->{
      for(var monitoredFunctionGen:MonitoredFunctionGen.values()){
        if((checkedType.checked || monitoredFunctionGen.expectedException==null)&&(!nestedType.rootType || monitoredFunctionGen.appliesToRoot) &&(nestedType.rootType || monitoredFunctionGen.appliesToSubList)){
          for(int seqSize:AbstractRefSeqMonitor.FIB_SEQ){
            streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredFunctionGen,seqSize));
          }
        }
      }   
    });
  }
  @org.junit.jupiter.api.Test
  public void testtoArray_IntFunction(){
    gettoArray_IntFunctionArgs().parallel().map(Arguments::get).forEach(args->{
        testtoArray_IntFunctionHelper((SeqMonitor)args[0],(PreModScenario)args[1],(MonitoredFunctionGen)args[2],(int)args[3]);
    });
  }
  private static void testtoArray_IntFunctionHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredFunctionGen monitoredFunctionGen,int numToAdd){
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
          Assertions.assertSame(resultArr[i],itr.next());
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
  }
  static Stream<Arguments> gettoArray_ObjectArrayArgs(){
    return ArgBuilder.buildSeqArgs((streamBuilder,nestedType,checkedType,preModScenario)->{
      for(int seqSize=0;seqSize<=15;seqSize+=5){
        for(int arrSize=0;arrSize<=20;arrSize+=5){
          streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario,seqSize,arrSize));
        }
      }
    });
  }
  @org.junit.jupiter.api.Test
  public void testtoArray_ObjectArray(){
    gettoArray_ObjectArrayArgs().parallel().map(Arguments::get).forEach(args->{
        testtoArray_ObjectArrayHelper((SeqMonitor)args[0],(PreModScenario)args[1],(int)args[2],(int)args[3]);
    });
  }
  private static void testtoArray_ObjectArrayHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,int seqSize,int arrSize){
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
        Assertions.assertSame(itr.next(),resultArr[i]);
      }
    }else{
      Assertions.assertThrows(preModScenario.expectedException,()->seqMonitor.seq.toArray(paramArr));
    }
    seqMonitor.verifyStructuralIntegrity();
    seqMonitor.verifyPreAlloc().verifyAscending(seqSize).verifyPostAlloc(preModScenario);
  }
  static Stream<Arguments> gettoArray_voidArgs(){
    return ArgBuilder.buildSeqArgs((streamBuilder,nestedType,checkedType,preModScenario)->{
      for(int seqSize:AbstractRefSeqMonitor.FIB_SEQ){
        for(var outputArgType:RefOutputTestArgType.values()){
          streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario,seqSize,outputArgType));
        }
      }
    });
  }
  @org.junit.jupiter.api.Test
  public void testtoArray_void(){
    gettoArray_voidArgs().parallel().map(Arguments::get).forEach(args->{
        testtoArray_voidHelper((SeqMonitor)args[0],(PreModScenario)args[1],(int)args[2],(RefOutputTestArgType)args[3]);
    });
  }
  private static void testtoArray_voidHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,int numToAdd,RefOutputTestArgType outputArgType){
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
  }
  static Stream<Arguments> getListget_intArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var nestedType:NestedType.values()){
      if(nestedType.forwardIteration){
        for(var checkedType:CheckedType.values()){
          for(var preModScenario:PreModScenario.values()){
            if(preModScenario.expectedException==null || (checkedType.checked && preModScenario!=PreModScenario.ModSeq && !nestedType.rootType)){
              for(int seqSize:AbstractRefSeqMonitor.FIB_SEQ){
                for(var seqLocation:SequenceLocation.values()){
                  if((seqLocation==SequenceLocation.BEGINNING && seqSize!=0) || (checkedType.checked && seqLocation.expectedException!=null)){
                    for(var outputArgType:RefOutputTestArgType.values()){
                      builder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario,seqSize,seqLocation,outputArgType));
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    return builder.build();
  }
  @org.junit.jupiter.api.Test
  public void testListget_int(){
    getListget_intArgs().parallel().map(Arguments::get).forEach(args->{
        testListget_intHelper((SeqMonitor)args[0],(PreModScenario)args[1],(int)args[2],(SequenceLocation)args[3],(RefOutputTestArgType)args[4]);
    });
  }
  private static void testListget_intHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,int numToAdd,SequenceLocation seqLocation,RefOutputTestArgType outputArgType){
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
  }
  static Stream<Arguments> getListset_int_valArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var nestedType:NestedType.values()){
      if(nestedType.forwardIteration){
        for(var checkedType:CheckedType.values()){
          for(var preModScenario:PreModScenario.values()){
            if(preModScenario.expectedException==null || (checkedType.checked && preModScenario!=PreModScenario.ModSeq && !nestedType.rootType)){
              for(int seqSize:AbstractRefSeqMonitor.FIB_SEQ){
                for(var seqLocation:SequenceLocation.values()){
                  if((seqLocation==SequenceLocation.BEGINNING && seqSize!=0) || (checkedType.checked && seqLocation.expectedException!=null)){
                    builder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario,seqSize,seqLocation,FunctionCallType.Unboxed));
                  }
                }
              }
            }
          }
        }
      }
    }
    return builder.build();
  }
  @org.junit.jupiter.api.Test
  public void testListset_int_val(){
    getListset_int_valArgs().parallel().map(Arguments::get).forEach(args->{
        testListset_int_valHelper((SeqMonitor)args[0],(PreModScenario)args[1],(int)args[2],(SequenceLocation)args[3],(FunctionCallType)args[4]);
    });
  }
  private static void testListset_int_valHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,int numToAdd,SequenceLocation seqLocation,FunctionCallType functionCallType){
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
  static Stream<Arguments> getListput_int_valArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var nestedType:NestedType.values()){
      if(nestedType.forwardIteration){
        for(var checkedType:CheckedType.values()){
          for(var seqLocation:SequenceLocation.values()){
            if((checkedType.checked || seqLocation.expectedException==null) && seqLocation.validForEmpty){
              for(var preModScenario:PreModScenario.values()){
                if(preModScenario!=PreModScenario.ModSeq && (preModScenario.expectedException==null || checkedType.checked) && (!nestedType.rootType || preModScenario==PreModScenario.NoMod)){
                  for(int seqSize:AbstractRefSeqMonitor.FIB_SEQ){
                    if(seqSize!=0 || seqLocation.expectedException!=null){
                      for(var inputArgType:RefInputTestArgType.values()){
                        builder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),inputArgType,seqLocation,preModScenario,seqSize));
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
    return builder.build();
  }
  @org.junit.jupiter.api.Test
  public void testListput_int_val(){
    getListput_int_valArgs().parallel().map(Arguments::get).forEach(args->{
        testListput_int_valHelper((SeqMonitor)args[0],(RefInputTestArgType)args[1],(SequenceLocation)args[2],(PreModScenario)args[3],(int)args[4]);
    });
  }
  private static void testListput_int_valHelper
  (SeqMonitor seqMonitor,RefInputTestArgType inputArgType,SequenceLocation seqLocation,PreModScenario preModScenario,int numToAdd){
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
  }
  static Stream<Arguments> getreadAndwriteObjectArgs(){
    return ArgBuilder.buildSeqArgs((streamBuilder,nestedType,checkedType,preModScenario)->{
      for(var monitoredFunctionGen:MonitoredFunctionGen.values()){
        if((checkedType.checked || monitoredFunctionGen.expectedException==null)&&(!nestedType.rootType || monitoredFunctionGen.appliesToRoot) &&(nestedType.rootType || monitoredFunctionGen.appliesToSubList)){
          for(int seqSize:AbstractRefSeqMonitor.FIB_SEQ){
            for(int initVal=0;initVal<=1;++initVal){
              streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredFunctionGen,seqSize,initVal));
            }
          }
        }
      }
    });
  }
  @org.junit.jupiter.api.Test
  public void testreadAndwriteObject(){
    getreadAndwriteObjectArgs().parallel().map(Arguments::get).forEach(args->{
        testreadAndwriteObjectHelper((SeqMonitor)args[0],(PreModScenario)args[1],(MonitoredFunctionGen)args[2],(int)args[3],(int)args[4]);
    });
  }
  private static void testreadAndwriteObjectHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredFunctionGen monitoredFunctionGen,int numToAdd,int initVal)
  {
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
        seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyPostAlloc(preModScenario);
        OmniCollection.OfRef readCol=null;
        try(var ois=new ObjectInputStream(new FileInputStream(file));){
          readCol=(OmniCollection.OfRef)ois.readObject();
        }catch(Exception e){
          Assertions.fail(e);
          return;
        }
        var itr=readCol.iterator();
        if(seqMonitor.nestedType.forwardIteration){
          for(int i=0;i<numToAdd;++i){
            Assertions.assertEquals(TypeConversionUtil.convertToObject(i),itr.next());
          }
        }else{
          for(int i=0;i<numToAdd;++i){
            Assertions.assertEquals(TypeConversionUtil.convertToObject(numToAdd-i-1),itr.next());
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
  }
  static Stream<Arguments> getQueueelement_voidArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var checkedType:CheckedType.values()){
      for(var outputType:RefOutputTestArgType.values()){
        builder.accept(Arguments.of(new SeqMonitor(NestedType.LISTDEQUE,checkedType),outputType));
      }
    }
    return builder.build();
  }
  @org.junit.jupiter.api.Test
  public void testQueueelement_void(){
    getQueueelement_voidArgs().parallel().map(Arguments::get).forEach(args->{
        testQueueelement_voidHelper((SeqMonitor)args[0],(RefOutputTestArgType)args[1]);
    });
  }
  private static void testQueueelement_voidHelper
  (SeqMonitor seqMonitor,RefOutputTestArgType outputArgType){
    for(int i=0;i<100;++i){
      seqMonitor.add(i);
    }
    for(int i=0;i<100;++i){
      outputArgType.verifyQueueElement(seqMonitor.seq,i);
      seqMonitor.verifyStructuralIntegrity();
      seqMonitor.pop(i,outputArgType);
    }
    if(seqMonitor.checkedType.checked){
      Assertions.assertThrows(NoSuchElementException.class,()->outputArgType.verifyQueueElement(seqMonitor.seq,0));
      seqMonitor.verifyStructuralIntegrity();
    }
  }
  static Stream<Arguments> getStackpush_valArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var checkedType:CheckedType.values()){
      for(var inputArgType:RefInputTestArgType.values()){
        builder.accept(Arguments.of(new SeqMonitor(NestedType.LISTDEQUE,checkedType),inputArgType));
      }
    }
    return builder.build();
  }
  @org.junit.jupiter.api.Test
  public void testStackpush_val(){
    getStackpush_valArgs().parallel().map(Arguments::get).forEach(args->{
        testStackpush_valHelper((SeqMonitor)args[0],(RefInputTestArgType)args[1]);
    });
  }
  private static void testStackpush_valHelper
  (SeqMonitor seqMonitor,RefInputTestArgType inputArgType){
    for(int i=0;i<100;++i){
      seqMonitor.push(i,inputArgType);
      seqMonitor.verifyStructuralIntegrity();
    }
    seqMonitor.verifyPreAlloc().verifyDescending(inputArgType,100).verifyPostAlloc();
  }
  static Stream<Arguments> getQueueoffer_valArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var checkedType:CheckedType.values()){
      for(var inputArgType:RefInputTestArgType.values()){
        builder.accept(Arguments.of(new SeqMonitor(NestedType.LISTDEQUE,checkedType),inputArgType));
      }
    }
    return builder.build();
  }
  @org.junit.jupiter.api.Test
  public void testQueueoffer_val(){
    getQueueoffer_valArgs().parallel().map(Arguments::get).forEach(args->{
        testQueueoffer_valHelper((SeqMonitor)args[0],(RefInputTestArgType)args[1]);
    });
  }
  private static void testQueueoffer_valHelper
  (SeqMonitor seqMonitor,RefInputTestArgType inputArgType){
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seqMonitor.offer(i,inputArgType));
      seqMonitor.verifyStructuralIntegrity();
    }
    seqMonitor.verifyPreAlloc().verifyNaturalAscending(inputArgType,100).verifyPostAlloc();
  }
  @org.junit.jupiter.api.Test
  public void testpeek_void(){
    getPeekPollAndPopArgs().parallel().map(Arguments::get).forEach(args->{
        testpeek_voidHelper((SeqMonitor)args[0],(RefOutputTestArgType)args[1]);
    });
  }
  private static void testpeek_voidHelper
  (SeqMonitor seqMonitor,RefOutputTestArgType outputArgType){
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
  @org.junit.jupiter.api.Test
  public void testpoll_void(){
    getPeekPollAndPopArgs().parallel().map(Arguments::get).forEach(args->{
        testpoll_voidHelper((SeqMonitor)args[0],(RefOutputTestArgType)args[1]);
    });
  }
  private static void testpoll_voidHelper
  (SeqMonitor seqMonitor,RefOutputTestArgType outputArgType){
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
  }
  @org.junit.jupiter.api.Test
  public void testpop_void(){
    getPeekPollAndPopArgs().parallel().map(Arguments::get).forEach(args->{
        testpop_voidHelper((SeqMonitor)args[0],(RefOutputTestArgType)args[1]);
    });
  }
  private static void testpop_voidHelper
  (SeqMonitor seqMonitor,RefOutputTestArgType outputArgType){
    for(int i=0;i<100;++i){
      seqMonitor.add(i);
    }
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
  @org.junit.jupiter.api.Test
  public void testQueueremove_void(){
    getPeekPollAndPopArgs().parallel().map(Arguments::get).forEach(args->{
        testQueueremove_voidHelper((SeqMonitor)args[0],(RefOutputTestArgType)args[1]);
    });
  }
  private static void testQueueremove_voidHelper
  (SeqMonitor seqMonitor,RefOutputTestArgType outputArgType){
    for(int i=0;i<100;++i){
      seqMonitor.add(i);
    }
    for(int i=0;i<100;++i){
      seqMonitor.queueRemove(i,outputArgType);
      seqMonitor.verifyStructuralIntegrity();
    }
    if(seqMonitor.checkedType.checked){
      Assertions.assertThrows(NoSuchElementException.class,()->seqMonitor.queueRemove(0,outputArgType));
      seqMonitor.verifyStructuralIntegrity();
    }
  }
  //TODO test removeLastOccurrence
  //TODO test pollLast/peekLast
  //TODO test pollFirst/peekFirst
  //TODO test offerFirst/offerLast
  //TODO test addFirst/addLast
  //TODO test getFirst/getLast
  //TODO test removeFirst/removeLast
  static enum NestedType{
    LISTDEQUE(true),
    SUBLIST(false);
    final boolean rootType;
    final boolean forwardIteration=true;
    NestedType(boolean rootType){
      this.rootType=rootType;
    }
  }
  private static class SeqMonitor extends AbstractRefSeqMonitor<RefDblLnkSeq>{
    private static final RefDblLnkSeq[] EMPTY_PARENTS=new RefDblLnkSeq[0];
    final NestedType nestedType;
    final RefDblLnkSeq[] parents;
    final int[] parentOffsets;
    final int[] expectedParentModCounts;
    final int[] expectedParentSizes;
    final int parentPreAlloc;
    final int parentPostAlloc;
    final int rootPostAlloc;
    SeqMonitor(NestedType nestedType,CheckedType checkedType){
      super(checkedType);
      this.nestedType=nestedType;
      switch(nestedType){
        case LISTDEQUE:
          this.seq=checkedType.checked?new RefDblLnkSeq.CheckedList():new RefDblLnkSeq.UncheckedList();
          this.parentPreAlloc=0;
          this.parentPostAlloc=0;
          this.rootPostAlloc=0;
          this.parents=EMPTY_PARENTS;
          this.parentOffsets=OmniArray.OfInt.DEFAULT_ARR;
          this.expectedParentModCounts=OmniArray.OfInt.DEFAULT_ARR;
          this.expectedParentSizes=OmniArray.OfInt.DEFAULT_ARR;
          break;
        case SUBLIST:
          var rootHead=new RefDblLnkNode(TypeConversionUtil.convertToObject(Integer.MIN_VALUE));
          var currHead=rootHead;
          for(int i=1;i<10;++i){
            currHead=currHead.next=new RefDblLnkNode(currHead,TypeConversionUtil.convertToObject(Integer.MIN_VALUE+i));
          }
          var rootTail=new RefDblLnkNode(TypeConversionUtil.convertToObject(Integer.MAX_VALUE));
          var currTail=rootTail;
          for(int i=1;i<10;++i){
            currTail=currTail.prev=new RefDblLnkNode(TypeConversionUtil.convertToObject(Integer.MAX_VALUE-i),currTail);
          }
          currHead.next=currTail;
          currTail.prev=currHead;
          RefDblLnkSeq root;
          root=checkedType.checked?new RefDblLnkSeq.CheckedList(rootHead,20,rootTail):new RefDblLnkSeq.UncheckedList(rootHead,20,rootTail);
          this.parents=new RefDblLnkSeq[2];
          this.parents[1]=root;
          this.parents[0]=(RefDblLnkSeq)root.subList(5,15);
          this.seq=(RefDblLnkSeq)parents[0].subList(5,5);
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
        this.seq=checkedType.checked?new RefDblLnkSeq.CheckedList():new RefDblLnkSeq.UncheckedList();
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
        RefDblLnkNode rootHead;
        RefDblLnkNode rootTail;
        if(totalPreAlloc==0){
          if(totalPostAlloc==0){
            rootHead=null;
            rootTail=null;
          }else{
            rootTail=new RefDblLnkNode(TypeConversionUtil.convertToObject(Integer.MAX_VALUE));
            rootHead=rootTail;
            for(int i=1;i<totalPostAlloc;++i){
              rootHead=rootHead.prev=new RefDblLnkNode(TypeConversionUtil.convertToObject(Integer.MAX_VALUE-i),rootHead);
            }
          }
        }else{
          rootHead=new RefDblLnkNode(TypeConversionUtil.convertToObject(Integer.MIN_VALUE));
          rootTail=rootHead;
          for(int i=1;i<totalPreAlloc;++i){
            rootTail=rootTail.next=new RefDblLnkNode(rootTail,TypeConversionUtil.convertToObject(Integer.MIN_VALUE+i));
          }
          for(int i=totalPostAlloc;--i>=0;){
            rootTail=rootTail.next=new RefDblLnkNode(rootTail,TypeConversionUtil.convertToObject(Integer.MAX_VALUE-i));
          }
        }
        RefDblLnkSeq root;
        int rootSize=totalPreAlloc+totalPostAlloc;
        root=checkedType.checked?new RefDblLnkSeq.CheckedList(rootHead,rootSize,rootTail):new RefDblLnkSeq.UncheckedList(rootHead,rootSize,rootTail);
        this.parents=new RefDblLnkSeq[parentPreAllocs.length];
        this.parentOffsets=new int[parentPreAllocs.length];
        this.expectedParentModCounts=new int[parentPreAllocs.length];
        this.expectedParentSizes=new int[parentPreAllocs.length];
        this.expectedParentSizes[parentPreAllocs.length-1]=rootSize;
        this.parents[parentPreAllocs.length-1]=root;
        for(int i=parentPreAllocs.length;--i>=1;){
          int fromIndex=parentPreAllocs[i];
          int toIndex=expectedParentSizes[i]-parentPostAllocs[i];
          parents[i-1]=(RefDblLnkSeq)parents[i].subList(fromIndex,toIndex);
          parentOffsets[i]=fromIndex;
          expectedParentSizes[i-1]=toIndex-fromIndex;
        }
        int fromIndex=parentPreAllocs[0];
        parentOffsets[0]=fromIndex;
        int toIndex=expectedParentSizes[0]-parentPostAllocs[0];
        this.seq=(RefDblLnkSeq)parents[0].subList(fromIndex,toIndex);
        this.expectedSeqSize=toIndex-fromIndex;
        Assertions.assertEquals(0,this.expectedSeqSize);
      }
    }
    void stableAscendingSort(){
      int seqSize=expectedSeqSize;
      ((OmniList.OfRef)seq).stableAscendingSort();
      if(seqSize>1){
       verifyFunctionalModification();
      }
    }
    void stableDescendingSort(){
      int seqSize=expectedSeqSize;
      ((OmniList.OfRef)seq).stableDescendingSort();
      if(seqSize>1){
       verifyFunctionalModification();
      }
    }
    void unstableAscendingSort(){
      int seqSize=expectedSeqSize;
      ((OmniList.OfRef)seq).unstableAscendingSort();
      if(seqSize>1){
       verifyFunctionalModification();
      }
    }
    void unstableDescendingSort(){
      int seqSize=expectedSeqSize;
      ((OmniList.OfRef)seq).unstableDescendingSort();
      if(seqSize>1){
       verifyFunctionalModification();
      }
    }
    void unstableSort(MonitoredComparator sorter){
      int seqSize=expectedSeqSize;
      ((OmniList.OfRef)seq).unstableSort((Comparator)sorter);
      if(seqSize>1){
       verifyFunctionalModification();
      }
    }
    void sort(MonitoredComparator sorter,FunctionCallType functionCallType){
      int seqSize=expectedSeqSize;
      {
        ((OmniList.OfRef)seq).sort((Comparator)sorter);
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
    AbstractRefSeqMonitor.AbstractItrMonitor getDescendingItrMonitor(){
      return checkedType.checked?new CheckedDescendingItrMonitor():new UncheckedDescendingItrMonitor();
    }
    AbstractRefSeqMonitor.AbstractItrMonitor getListItrMonitor(){
      switch(nestedType){
        case LISTDEQUE:
          return checkedType.checked?new CheckedBidirectionalItrMonitor():new UncheckedBidirectionalItrMonitor();
        case SUBLIST:
          return checkedType.checked?new CheckedBidirectionalSubItrMonitor():new UncheckedBidirectionalSubItrMonitor();
        default:
          throw new Error("Unknown nested type "+nestedType);
      }
    }
    AbstractRefSeqMonitor.AbstractItrMonitor getListItrMonitor(int index){
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
      RefDblLnkNode curr;
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
         RefInputTestArgType.ARRAY_TYPE.verifyVal(expectedVal,curr.val);
      }
      return new DblLnkSeqVerificationItr(offset,curr,this);
    }
    SequenceVerificationItr verifyPreAlloc(){
      int rootPreAlloc;
      RefDblLnkNode curr;
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
        RefInputTestArgType.ARRAY_TYPE.verifyVal(v,curr.val);
      }
      return new DblLnkSeqVerificationItr(offset,curr,this);
    }
    void illegalAdd(PreModScenario preModScenario){
      switch(preModScenario)
      {
        case ModSeq:
          RefInputTestArgType.ARRAY_TYPE.callCollectionAdd(seq,0);
          verifyAddition();
          break;
        case ModParent:
          int index;
          RefInputTestArgType.ARRAY_TYPE.callCollectionAdd(parents[index=parents.length-2],0);
          ++expectedParentSizes[index];
          ++expectedParentModCounts[index];
          ++expectedParentSizes[++index];
          ++expectedParentModCounts[index];
          break;
        case ModRoot:
          RefInputTestArgType.ARRAY_TYPE.callCollectionAdd(parents[index=parents.length-1],0);
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
      var builder=new StringBuilder("RefDblLnkSeq").append(checkedType.checked?"Checked":"Unchecked");
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
        Assertions.assertEquals(expectedSeqModCount,((RefDblLnkSeq.CheckedList)seq).modCount);
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
      RefDblLnkSeq currList,currParent;
      int currSize;
      Assertions.assertEquals(currSize=this.expectedSeqSize,(currList=this.seq).size);
      Assertions.assertEquals(expectedSeqModCount,FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.modCount(currList));
      RefDblLnkSeq[] parents;
      var root=(parents=this.parents)[parents.length-1];
      int parentSize;
      RefDblLnkNode currHead;
      for(int parentIndex=0,parentBound=parents.length;;){
        parentSize=expectedParentSizes[parentIndex];
        currParent=parents[parentIndex];
        if(parentIndex==parentBound-1){
          Assertions.assertNull(FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.parent(currList));
          Assertions.assertEquals(expectedParentModCounts[parentIndex],FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.modCount(currParent));
        }else{
          Assertions.assertSame(currParent,FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.parent(currList));
          Assertions.assertEquals(expectedParentModCounts[parentIndex],FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.modCount(currParent));
        }
        Assertions.assertSame(root,FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.root(currList));
        int preAlloc=parentOffsets[parentIndex];
        Assertions.assertEquals(preAlloc,FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.parentOffset(currList));
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
              Assertions.assertEquals(preAlloc=parentOffsets[++parentIndex],FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.parentOffset(currList=currParent));
              if(parentIndex==parentBound-1){
                Assertions.assertNull(FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.parent(currParent));
                currParent=parents[parentIndex];
                Assertions.assertEquals(expectedParentModCounts[parentIndex],FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.modCount(currParent));
              }else{
                Assertions.assertSame(root,FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.root(currList));
                Assertions.assertSame(currParent=parents[parentIndex],FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.parent(currList));
                Assertions.assertEquals(expectedParentModCounts[parentIndex],FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.modCount(currParent));
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
      RefDblLnkSeq currList,currParent;
      int currSize;
      Assertions.assertEquals(currSize=this.expectedSeqSize,(currList=this.seq).size);
      RefDblLnkSeq[] parents;
      var root=(parents=this.parents)[parents.length-1];
      int parentSize;
      RefDblLnkNode currHead;
      for(int parentIndex=0,parentBound=parents.length;;){
        parentSize=expectedParentSizes[parentIndex];
        currParent=parents[parentIndex];
        if(parentIndex==parentBound-1){
          Assertions.assertNull(FieldAndMethodAccessor.RefDblLnkSeq.UncheckedSubList.parent(currList));
        }else{
          Assertions.assertSame(currParent,FieldAndMethodAccessor.RefDblLnkSeq.UncheckedSubList.parent(currList));
        }
        Assertions.assertSame(root,FieldAndMethodAccessor.RefDblLnkSeq.UncheckedSubList.root(currList));
        int preAlloc=parentOffsets[parentIndex];
        Assertions.assertEquals(preAlloc,FieldAndMethodAccessor.RefDblLnkSeq.UncheckedSubList.parentOffset(currList));
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
              Assertions.assertEquals(preAlloc=parentOffsets[++parentIndex],FieldAndMethodAccessor.RefDblLnkSeq.UncheckedSubList.parentOffset(currList=currParent));
              if(parentIndex==parentBound-1){
                Assertions.assertNull(FieldAndMethodAccessor.RefDblLnkSeq.UncheckedSubList.parent(currParent));
                currParent=parents[parentIndex];
              }else{
                Assertions.assertSame(root,FieldAndMethodAccessor.RefDblLnkSeq.UncheckedSubList.root(currList));
                Assertions.assertSame(currParent=parents[parentIndex],FieldAndMethodAccessor.RefDblLnkSeq.UncheckedSubList.parent(currList));
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
            FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.writeObject(seq,oos);
          }else{
            FieldAndMethodAccessor.RefDblLnkSeq.UncheckedList.writeObject(seq,oos);
          }
          break;
        case SUBLIST:
          if(checkedType.checked){
            FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.writeObject(seq,oos);
          }else{
            FieldAndMethodAccessor.RefDblLnkSeq.UncheckedSubList.writeObject(seq,oos);
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
      RefDblLnkNode curr;
      int index;
      final SeqMonitor seqMonitor;
      private DblLnkSeqVerificationItr(int index,RefDblLnkNode curr,SeqMonitor seqMonitor){
        this.index=index;
        this.seqMonitor=seqMonitor;
        this.curr=curr;
      }
      SequenceVerificationItr verifyNaturalAscending(int v,RefInputTestArgType inputArgType,int length){
        return verifyAscending(v,inputArgType,length);
      }
      @Override SequenceVerificationItr verifyPostAlloc(int expectedVal){
        for(int i=0,bound=seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc;i<bound;++i){
          verifyIndexAndIterate(RefInputTestArgType.ARRAY_TYPE,expectedVal);
        }
        Assertions.assertNull(curr);
        Assertions.assertEquals(seqMonitor.expectedParentSizes.length==0?seqMonitor.expectedSeqSize:seqMonitor.expectedParentSizes[seqMonitor.parents.length-1],index);
        return this;
      }
      @Override void verifyLiteralIndexAndIterate(Object val){
        Assertions.assertSame(val,curr.val);
        curr=curr.next;
        ++index;
      }
      @Override void verifyIndexAndIterate(MonitoredObject monitoredObject){
        Object v;
        Assertions.assertEquals(monitoredObject.compareVal,(v=curr.val) instanceof MonitoredObject?((MonitoredObject)v).compareVal:(Object)v);
        curr=curr.next;
        ++index;
      }
      @Override void reverseAndVerifyIndex(MonitoredObject monitoredObject){
        RefDblLnkNode curr;
        Object v;
        Assertions.assertEquals(monitoredObject.compareVal,(v=(curr=getReverseNode()).val) instanceof MonitoredObject?((MonitoredObject)v).compareVal:(Object)v);
        this.curr=curr;
        --index;
      }
      private RefDblLnkNode getReverseNode(){
        RefDblLnkNode curr;
        return (curr=this.curr)==null?seqMonitor.parents.length==0?seqMonitor.seq.tail:seqMonitor.parents[seqMonitor.parents.length-1].tail:curr.prev;
      }
      @Override void reverseAndVerifyIndex(RefInputTestArgType inputArgType,int val){
        RefDblLnkNode curr;
        inputArgType.verifyVal(val,(curr=getReverseNode()).val);
        this.curr=curr;
        --index;
      }
      @Override void verifyIndexAndIterate(RefInputTestArgType inputArgType,int val){ 
        inputArgType.verifyVal(val,curr.val);
        curr=curr.next;
        ++index;
      }
      private RefDblLnkNode getNode(int i){
        if(i<0){
          if(this.curr==null){
            return RefDblLnkNode.iterateDescending(seqMonitor.parents.length==0?seqMonitor.seq.tail:seqMonitor.parents[seqMonitor.parents.length-1].tail,(-i)-1);
          }
          return RefDblLnkNode.uncheckedIterateDescending(this.curr,-i);
        }
        return RefDblLnkNode.iterateAscending(curr,i);
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
          verifyIndexAndIterate(RefInputTestArgType.ARRAY_TYPE,v);
        }
        return this;
      }
      @Override SequenceVerificationItr verifyParentPostAlloc(){
        for(int i=0,rootPostAlloc=seqMonitor.rootPostAlloc,v=Integer.MAX_VALUE-(rootPostAlloc+seqMonitor.parentPostAlloc-1);i<seqMonitor.parentPostAlloc;++i,++v){
          verifyIndexAndIterate(RefInputTestArgType.ARRAY_TYPE,v);
        }
        return this;
      }
    }
    class UncheckedSubAscendingItrMonitor extends UncheckedAscendingItrMonitor{
      UncheckedSubAscendingItrMonitor(){
        super();
      }
      RefDblLnkNode getNewCurr(){
        if(expectedCurr!=null && expectedCurr!=seq.tail){
          return expectedCurr.next;
        }
        return null;
      }
      void verifyIteratorState(){
        Assertions.assertSame(FieldAndMethodAccessor.RefDblLnkSeq.UncheckedSubList.AscendingItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.RefDblLnkSeq.UncheckedSubList.AscendingItr.parent(itr),seq);
      }
    }
    class UncheckedAscendingItrMonitor extends AbstractRefSeqMonitor.AbstractItrMonitor{
      RefDblLnkNode expectedCurr;
      UncheckedAscendingItrMonitor(){
        this(ItrType.Itr,seq.iterator(),seq.head);
      }
      private UncheckedAscendingItrMonitor(ItrType itrType,OmniIterator.OfRef itr,RefDblLnkNode expectedCurr){
        super(itrType,itr,expectedSeqModCount);
        this.expectedCurr=expectedCurr;
      }
      void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
        {
          itr.forEachRemaining((Consumer)action);
        }
        this.expectedCurr=null;
      }
      SeqMonitor getSeqMonitor(){
        return SeqMonitor.this;
      }
      RefDblLnkNode getNewCurr(){
        return expectedCurr!=null?expectedCurr.next:null;
      }
      void verifyNext(int expectedVal,RefOutputTestArgType outputType){
        var newCurr=getNewCurr();
        outputType.verifyItrNext(itr,expectedVal);
        expectedCurr=newCurr;
      }
      void iterateForward(){
        var newCurr=getNewCurr();
        itr.next();
        expectedCurr=newCurr;
      }
      void remove(){
        itr.remove();
        verifyRemoval();
      }
      void verifyIteratorState(){
        Assertions.assertSame(FieldAndMethodAccessor.RefDblLnkSeq.UncheckedList.AscendingItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.RefDblLnkSeq.UncheckedList.AscendingItr.parent(itr),seq);
      }
    }
    class UncheckedDescendingItrMonitor extends UncheckedAscendingItrMonitor{
      UncheckedDescendingItrMonitor(){
        super(ItrType.DescendingItr,((OmniDeque.OfRef)seq).descendingIterator(),seq.tail);
      }
      RefDblLnkNode getNewCurr(){
        return expectedCurr!=null?expectedCurr.prev:null;
      }
      void verifyIteratorState(){
        Assertions.assertSame(FieldAndMethodAccessor.RefDblLnkSeq.UncheckedList.DescendingItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.RefDblLnkSeq.UncheckedList.DescendingItr.parent(itr),seq);
      }
    }
    class CheckedDescendingItrMonitor extends UncheckedDescendingItrMonitor{
      RefDblLnkNode expectedLastRet;
      int expectedCurrIndex;
      CheckedDescendingItrMonitor(){
        super();
        this.expectedCurrIndex=expectedSeqSize;
      }
      void verifyNext(int expectedVal,RefOutputTestArgType outputType){
        var newCurr=getNewCurr();
        outputType.verifyItrNext(itr,expectedVal);
        expectedLastRet=expectedCurr;
        expectedCurr=newCurr;
        --expectedCurrIndex;
      }
      void iterateForward(){
        var newCurr=getNewCurr();
        itr.next();
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
        Assertions.assertEquals(FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.DescendingItr.modCount(itr),expectedItrModCount);
        Assertions.assertEquals(FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.DescendingItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertSame(FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.DescendingItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.DescendingItr.lastRet(itr),expectedLastRet);
        Assertions.assertSame(FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.DescendingItr.parent(itr),seq);
      }
      void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
        {
          itr.forEachRemaining((Consumer)action);
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
        RefDblLnkNode newLastRet=seq.tail;
        {
          itr.forEachRemaining((Consumer)action);
        }
        if(expectedCurrIndex<parentSize){
          this.expectedLastRet=newLastRet;
          this.expectedCurrIndex=parentSize;
          this.expectedCurr=null;
        }
      }
      void verifyIteratorState(){
        Assertions.assertEquals(FieldAndMethodAccessor.RefDblLnkSeq.UncheckedSubList.BidirectionalItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertSame(FieldAndMethodAccessor.RefDblLnkSeq.UncheckedSubList.BidirectionalItr.parent(itr),seq);
        Assertions.assertSame(FieldAndMethodAccessor.RefDblLnkSeq.UncheckedSubList.BidirectionalItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.RefDblLnkSeq.UncheckedSubList.BidirectionalItr.lastRet(itr),expectedLastRet);
      }
    }
    class UncheckedBidirectionalItrMonitor extends AbstractRefSeqMonitor.AbstractItrMonitor{
      RefDblLnkNode expectedCurr;
      int expectedCurrIndex;
      RefDblLnkNode expectedLastRet;
      UncheckedBidirectionalItrMonitor(){
        super(ItrType.ListItr,seq.listIterator(),expectedSeqModCount);
        this.expectedCurr=seq.head;
        this.expectedCurrIndex=0;
      }
      UncheckedBidirectionalItrMonitor(ItrType itrType,OmniIterator.OfRef itr,int currIndex){
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
            this.expectedCurr=RefDblLnkNode.uncheckedIterateDescending(seq.tail,parentSize-1);
          }
        }else{
          this.expectedCurr=RefDblLnkNode.iterateAscending(seq.head,currIndex);
        }
      }
      UncheckedBidirectionalItrMonitor(int index){
        this(ItrType.ListItr,seq.listIterator(index),index);
      }
      RefDblLnkNode getNewNextNode(){
        return expectedCurr==null?null:expectedCurr.next;
      }
      RefDblLnkNode getNewPrevNode(){
        return expectedCurr==null?seq.tail:expectedCurr.prev;
      }
      void iterateReverse(){
        RefDblLnkNode newLastRet=getNewPrevNode();
        ((OmniListIterator.OfRef)itr).previous();
        this.expectedLastRet=newLastRet;
        this.expectedCurr=newLastRet;
        --this.expectedCurrIndex;
      }
      void verifyPrevious(int expectedVal,RefOutputTestArgType outputType){
        RefDblLnkNode newLastRet=getNewPrevNode();
        outputType.verifyItrPrevious(((OmniListIterator.OfRef)itr),expectedVal);
         this.expectedLastRet=newLastRet;
        this.expectedCurr=newLastRet;
        --this.expectedCurrIndex;
      }
      void add(int v,RefInputTestArgType inputArgType){
        inputArgType.callListItrAdd(((OmniListIterator.OfRef)itr),v);
        ++this.expectedCurrIndex;
        this.expectedLastRet=null;
        verifyAddition();
      }
      void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
        {
          itr.forEachRemaining((Consumer)action);
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
      void verifyNext(int expectedVal,RefOutputTestArgType outputType){
        var newCurr=getNewNextNode();
        outputType.verifyItrNext(itr,expectedVal);
        this.expectedLastRet=expectedCurr;
        this.expectedCurr=newCurr;
        ++this.expectedCurrIndex;
      }
      void iterateForward(){
        var newCurr=getNewNextNode();
        itr.next();
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
        Assertions.assertEquals(FieldAndMethodAccessor.RefDblLnkSeq.UncheckedList.BidirectionalItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertSame(FieldAndMethodAccessor.RefDblLnkSeq.UncheckedList.BidirectionalItr.parent(itr),seq);
        Assertions.assertSame(FieldAndMethodAccessor.RefDblLnkSeq.UncheckedList.BidirectionalItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.RefDblLnkSeq.UncheckedList.BidirectionalItr.lastRet(itr),expectedLastRet);
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
        Assertions.assertEquals(FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.BidirectionalItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertEquals(FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.BidirectionalItr.modCount(itr),expectedItrModCount);
        Assertions.assertSame(FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.BidirectionalItr.parent(itr),seq);
        Assertions.assertSame(FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.BidirectionalItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.BidirectionalItr.lastRet(itr),expectedLastRet);
      }
    }
    class CheckedSubAscendingItrMonitor extends CheckedBidirectionalItrMonitor{
      CheckedSubAscendingItrMonitor(){
        super();
      }
      CheckedSubAscendingItrMonitor(int index){
        super(index);
      }
      RefDblLnkNode getNewNextNode(){
        return expectedCurr!=null &&expectedCurrIndex<expectedSeqSize?expectedCurr.next:null;
      }
      void verifyIteratorState(){
        Assertions.assertEquals(FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.AscendingItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertEquals(FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.AscendingItr.modCount(itr),expectedItrModCount);
        Assertions.assertSame(FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.AscendingItr.parent(itr),seq);
        Assertions.assertSame(FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.AscendingItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.AscendingItr.lastRet(itr),expectedLastRet);
      }
    }
    class CheckedBidirectionalItrMonitor extends CheckedAscendingItrMonitor{
      CheckedBidirectionalItrMonitor(int index){
        super(ItrType.ListItr,seq.listIterator(index),index);
      }
      CheckedBidirectionalItrMonitor(){
        super(ItrType.ListItr,seq.listIterator(),0);
      }
      void add(int v,RefInputTestArgType inputArgType){
        super.add(v,inputArgType);
        ++expectedItrModCount;
      }
      RefDblLnkNode getNewPrevNode(){
        return expectedCurrIndex!=0?expectedCurr==null?seq.tail:expectedCurr.prev:null;
      }
      void verifyIteratorState(){
        Assertions.assertEquals(FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.BidirectionalItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertEquals(FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.BidirectionalItr.modCount(itr),expectedItrModCount);
        Assertions.assertSame(FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.BidirectionalItr.parent(itr),seq);
        Assertions.assertSame(FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.BidirectionalItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.BidirectionalItr.lastRet(itr),expectedLastRet);
      }
    }
    class CheckedAscendingItrMonitor extends UncheckedBidirectionalItrMonitor{
      CheckedAscendingItrMonitor(){
        super(ItrType.Itr,seq.iterator(),0);
      }
      CheckedAscendingItrMonitor(ItrType itrType,OmniIterator.OfRef itr,int index){
        super(itrType,itr,index);
      }
      void verifyIteratorState(){
        Assertions.assertEquals(FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.AscendingItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertEquals(FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.AscendingItr.modCount(itr),expectedItrModCount);
        Assertions.assertSame(FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.AscendingItr.parent(itr),seq);
        Assertions.assertSame(FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.AscendingItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.AscendingItr.lastRet(itr),expectedLastRet);
      }
      void remove(){
        super.remove();
        ++expectedItrModCount;
      }
      void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
        int parentSize=expectedSeqSize;
        int currIndex=this.expectedCurrIndex;
        {
          itr.forEachRemaining((Consumer)action);
        }
        if(currIndex<parentSize){
          this.expectedLastRet=seq.tail;
          this.expectedCurrIndex=parentSize;
          this.expectedCurr=null;
        }
      }
    }
  }
  @FunctionalInterface
  interface ArgBuilder{
    void buildArgs(Stream.Builder<Arguments> streamBuilder,NestedType nestedType,CheckedType checkedType,PreModScenario preModScenario);
    static Stream<Arguments> buildSeqArgs(ArgBuilder argBuilder){
      Stream.Builder<Arguments> streamBuilder=Stream.builder();
      for(var nestedType:NestedType.values()){
        for(var checkedType:CheckedType.values()){
          for(var preModScenario:PreModScenario.values()){
            if(preModScenario.expectedException==null || (checkedType.checked && preModScenario!=PreModScenario.ModSeq && !nestedType.rootType)){
              argBuilder.buildArgs(streamBuilder,nestedType,checkedType,preModScenario);
            }
          }
        }
      }
      return streamBuilder.build();
    }
  }
  static void runQueryTests(boolean parallel,NestedType nestedType,QueryTest queryTest){
    for(var checkedType:CheckedType.values()){
      for(var preModScenario:PreModScenario.values()){
        if(preModScenario!=PreModScenario.ModSeq && (preModScenario.expectedException==null || (checkedType.checked && !nestedType.rootType))){
          var seqLocationStream=Stream.of(SequenceLocation.values()).filter(seqLocation->seqLocation!=SequenceLocation.IOBLO);
          if(parallel){
            seqLocationStream=seqLocationStream.parallel();
          }
          seqLocationStream.forEach(seqLocation->{
            for(int seqSize:AbstractRefSeqMonitor.FIB_SEQ){
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
                      var monitoredObjectGenStream=Stream.of(MonitoredObjectGen.values()).filter(monitoredObjectGen->monitoredObjectGen.expectedException!=null&&(!nestedType.rootType || monitoredObjectGen.appliesToRoot));
                      if(parallel){
                        monitoredObjectGenStream=monitoredObjectGenStream.parallel();
                      }
                      monitoredObjectGenStream.forEach(monitoredObjectGen->{
                        queryTest.runTest(checkedType,argType,queryCastType,seqLocation,seqSize,preModScenario,monitoredObjectGen);
                      });
                    }else{
                      queryTest.runTest(checkedType,argType,queryCastType,seqLocation,seqSize,preModScenario,null);
                    }
                  });
                }
              }
            }
          });
        }
      }
    }
  }
  interface QueryTest
  {
    void runTest(CheckedType checkedType,QueryTester argType,QueryCastType queryCastType,SequenceLocation seqLocation,int seqSize,PreModScenario preModScenario
      ,MonitoredObjectGen monitoredObjectGen
    );
  }
  static Stream<Arguments> getNonComparatorSortArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var nestedType:NestedType.values()){
      if(nestedType.forwardIteration){
        for(var checkedType:CheckedType.values()){
          for(var preModScenario:PreModScenario.values()){
            if((checkedType.checked || preModScenario.expectedException==null) && ((nestedType.rootType && preModScenario.expectedException==null)||(!nestedType.rootType && preModScenario.appliesToSubList))){
              for(var monitoredComparatorGen:MonitoredComparatorGen.values()){
                if(monitoredComparatorGen.nullComparator && ((!nestedType.rootType || monitoredComparatorGen.appliesToRoot) && (checkedType.checked || monitoredComparatorGen.expectedException==null))){
                  for(int seqSize:AbstractRefSeqMonitor.FIB_SEQ){
                    builder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredComparatorGen,seqSize));
                  }
                }
              }
            }
          }
        }
      }
    }
    return builder.build();
  }
  static Stream<Arguments> getPeekPollAndPopArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var checkedType:CheckedType.values()){
      for(var outputType:RefOutputTestArgType.values()){
        builder.accept(Arguments.of(new SeqMonitor(NestedType.LISTDEQUE,checkedType),outputType));
      }
    }
    return builder.build();
  }
  static Stream<Arguments> getBasicCollectionTestArgs(){
    return ArgBuilder.buildSeqArgs((streamBuilder,nestedType,checkedType,preModScenario)->{
      for(int seqSize:AbstractRefSeqMonitor.FIB_SEQ){
        streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario,seqSize));
      }
    });
  }
  static Stream<Arguments> gettoStringAndhashCode_voidArgs(){
   return ArgBuilder.buildSeqArgs((streamBuilder,nestedType,checkedType,preModScenario)->{
     for(int seqSize:AbstractRefSeqMonitor.FIB_SEQ){
       for(var monitoredObjectGen:MonitoredObjectGen.values()){
         if(monitoredObjectGen.expectedException==null || (checkedType.checked && (!nestedType.rootType || monitoredObjectGen.appliesToRoot))){
           streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario,seqSize,monitoredObjectGen));
         }
       }
     }
   });
  }
  private static void runSubListTests(boolean quickTest,CheckedType checkedType,boolean parallel,int parentLength,Consumer<? super SeqMonitor> testMethod){
    if(quickTest){
      var preAllocStream=IntStream.range(0,1<<(parentLength));
      if(parallel){
        preAllocStream=preAllocStream.parallel();
      }
      preAllocStream.forEach(preAllocBits->{
        int[] preAllocs=new int[parentLength];
        for(int index=0,marker=0b1;index<parentLength;marker<<=1,++index){
          preAllocs[index]=(marker&preAllocBits)!=0?5:0;
        }
        var postAllocStream=IntStream.range(0,1<<(parentLength<<1));
        if(parallel){
          postAllocStream=postAllocStream.parallel();
        }
        postAllocStream.forEach(postAllocBits->{
          int[] postAllocs=new int[parentLength];
          for(int index=0,marker=0b1;index<parentLength;marker<<=1,++index){
            postAllocs[index]=(marker&postAllocBits)!=0?5:0;
          }
          //System.out.println(Arrays.toString(preAllocs)+","+Arrays.toString(postAllocs));
          testMethod.accept(new SeqMonitor(checkedType,preAllocs,postAllocs));
        });
      });
    }else{
      var preAllocStream=IntStream.range(0,1<<(parentLength<<1));
      if(parallel){
        preAllocStream=preAllocStream.parallel();
      }
      preAllocStream.forEach(preAllocBits->{
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
        var postAllocStream=IntStream.range(0,1<<(parentLength<<1));
        if(parallel){
          postAllocStream=postAllocStream.parallel();
        }
        postAllocStream.forEach(postAllocBits->{
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
          testMethod.accept(new SeqMonitor(checkedType,preAllocs,postAllocs));
        });
      });
    }
  }
}
