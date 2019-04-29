package omni.impl.seq;
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
import omni.impl.seq.AbstractRefSeqMonitor.MonitoredFunctionGen;
import omni.impl.seq.AbstractRefSeqMonitor.MonitoredRemoveIfPredicateGen;
import omni.impl.seq.AbstractRefSeqMonitor.QueryTester;
import java.nio.file.Files;
import omni.impl.seq.AbstractRefSeqMonitor.MonitoredObjectGen;
import omni.impl.seq.AbstractRefSeqMonitor.MonitoredObject;
import omni.impl.seq.AbstractRefSeqMonitor.SequenceVerificationItr;
import omni.api.OmniCollection;
import omni.api.OmniListIterator;
import java.util.ArrayList;
import omni.api.OmniDeque;
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
  static Stream<Arguments> getadd_valArgs(){
    return ArgBuilder.buildSeqArgs((streamBuilder,nestedType,checkedType,preModScenario)->{
      for(int seqSize:AbstractRefSeqMonitor.FIB_SEQ){
        for(var inputArgType:RefInputTestArgType.values()){
          switch(nestedType){
            case LISTDEQUE:
              streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),inputArgType,preModScenario,seqSize));
              break;
            case SUBLIST:
              int[] preAllocs=new int[4];
              int[] postAllocs=new int[4];
              for(int preAllocBits=0;preAllocBits<(1<<4);++preAllocBits)
              {
                for(int marker=1,index=0;marker<(1<<4);marker<<=1,++index)
                {
                  preAllocs[index]=(preAllocBits&marker)!=0?5:0;
                }
                for(int postAllocBits=0;postAllocBits<(1<<4);++postAllocBits)
                {
                  for(int marker=1,index=0;marker<(1<<4);marker<<=1,++index)
                  {
                    postAllocs[index]=(postAllocBits&marker)!=0?5:0;
                  }
                  streamBuilder.accept(Arguments.of(new SeqMonitor(checkedType,preAllocs,postAllocs),inputArgType,preModScenario,seqSize));
                }
              }
              break;
            default:
              throw new Error("Unknown nested type "+nestedType);
          } 
        }
      }
    });
  }
  @org.junit.jupiter.api.Test
  public void testadd_val(){
    getadd_valArgs().parallel().map(Arguments::get).forEach(args->{
        testadd_valHelper((SeqMonitor)args[0],(RefInputTestArgType)args[1],(PreModScenario)args[2],(int)args[3]);
    });
  }
  private static void testadd_valHelper
  (SeqMonitor seqMonitor,RefInputTestArgType inputArgType,PreModScenario preModScenario,int numToAdd){
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
  public void testcontains_val(){
    getQueryCollectionArguments().parallel().map(Arguments::get).forEach(args->{
        testcontains_valHelper((SeqMonitor)args[0],(QueryTester)args[1],(QueryCastType)args[2],(SequenceLocation)args[3],(int)args[4],(PreModScenario)args[5]
          ,(MonitoredObjectGen)args[6]
        );
    });
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
    getQueryListArguments().parallel().map(Arguments::get).forEach(args->{
        testindexOf_valHelper((SeqMonitor)args[0],(QueryTester)args[1],(QueryCastType)args[2],(SequenceLocation)args[3],(int)args[4],(PreModScenario)args[5]
          ,(MonitoredObjectGen)args[6]
        );
    });
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
    getQueryListArguments().parallel().map(Arguments::get).forEach(args->{
        testlastIndexOf_valHelper((SeqMonitor)args[0],(QueryTester)args[1],(QueryCastType)args[2],(SequenceLocation)args[3],(int)args[4],(PreModScenario)args[5]
          ,(MonitoredObjectGen)args[6]
        );
    });
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
    getQueryCollectionArguments().parallel().map(Arguments::get).forEach(args->{
        testsearch_valHelper((SeqMonitor)args[0],(QueryTester)args[1],(QueryCastType)args[2],(SequenceLocation)args[3],(int)args[4],(PreModScenario)args[5]
          ,(MonitoredObjectGen)args[6]
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
    getQueryCollectionArguments().parallel().map(Arguments::get).forEach(args->{
        testremoveVal_valHelper((SeqMonitor)args[0],(QueryTester)args[1],(QueryCastType)args[2],(SequenceLocation)args[3],(int)args[4],(PreModScenario)args[5]
          ,(MonitoredObjectGen)args[6]
        );
    });
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
              for(int i=0;i<numExpectedCalls;++i){
                verifyItr.verifyIllegalAdd();
              }
              if(seqLocation==SequenceLocation.IOBHI){
                ++numExpectedCalls;
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
  //#MACRO testListadd_int_val<true>()
  static enum NestedType{
    LISTDEQUE(true),
    SUBLIST(false);
    final boolean rootType;
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
          this.expectedCurr=newLastRet.next;
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
        if(expectedLastRet!=null && expectedLastRet.next==expectedCurr){
          --newCurrIndex;
        }
        itr.remove();
        verifyRemoval();
        this.expectedCurrIndex=newCurrIndex;
        this.expectedLastRet=null;
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
        //if(nestedType==NestedType.SUBLIST){
        //  continue;
        //}
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
  static void buildQueryArguments(Stream.Builder<Arguments> builder,NestedType nestedType){
    for(var checkedType:CheckedType.values()){
      for(var preModScenario:PreModScenario.values()){
        if(preModScenario!=PreModScenario.ModSeq && (preModScenario.expectedException==null || (checkedType.checked && !nestedType.rootType))){
          for(var seqLocation:SequenceLocation.values()){
            if(seqLocation!=SequenceLocation.IOBLO){
              for(int seqSize:AbstractRefSeqMonitor.FIB_SEQ){
                if(seqLocation==SequenceLocation.IOBHI || seqSize!=0){
                  for(var argType:QueryTester.values()){
                    for(var queryCastType:QueryCastType.values()){
                      switch(argType){
                        case ObjectNonNull:
                        case Objectnull:
                          if(queryCastType!=QueryCastType.ToObject){
                            continue;
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
                            continue;
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
                          continue;
                        }
                        //these values must necessarily return false
                      }
                      if(argType==QueryTester.ObjectNonNull){
                        if(seqSize==0|| !checkedType.checked){
                          continue;
                        }
                        for(var monitoredObjectGen:MonitoredObjectGen.values()){
                          if(nestedType.rootType && !monitoredObjectGen.appliesToRoot || monitoredObjectGen.expectedException==null){
                            continue;
                          }
                          builder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),argType,queryCastType,seqLocation,seqSize,preModScenario,monitoredObjectGen));
                        }
                      }else{
                        builder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),argType,queryCastType,seqLocation,seqSize,preModScenario,null));
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
  static Stream<Arguments> getBasicCollectionTestArgs(){
    return ArgBuilder.buildSeqArgs((streamBuilder,nestedType,checkedType,preModScenario)->{
      for(int seqSize:AbstractRefSeqMonitor.FIB_SEQ){
        streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario,seqSize));
      }
    });
  }
  static Stream<Arguments> getQueryListArguments(){
    Stream.Builder<Arguments> builder=Stream.builder();
    buildQueryArguments(builder,NestedType.LISTDEQUE);
    //buildQueryArguments(builder,NestedType.SUBLIST);
    return builder.build();
  }
  static Stream<Arguments> getQueryCollectionArguments(){
    Stream.Builder<Arguments> builder=Stream.builder();
    buildQueryArguments(builder,NestedType.LISTDEQUE);
    return builder.build();
  }
}
