package omni.impl.seq;
import java.util.concurrent.atomic.AtomicInteger;
import omni.function.CharConsumer;
import omni.function.CharPredicate;
import java.util.Arrays;
import java.util.function.Consumer;
import java.io.IOException;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import omni.impl.CharInputTestArgType;
import omni.impl.CharOutputTestArgType;
import org.junit.jupiter.params.provider.Arguments;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.DoubleStream;
import omni.util.OmniArray;
import omni.impl.CharDblLnkNode;
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
import omni.impl.seq.AbstractCharSeqMonitor.CheckedType;
import omni.impl.seq.AbstractCharSeqMonitor.PreModScenario;
import omni.impl.seq.AbstractCharSeqMonitor.SequenceLocation;
import omni.impl.seq.AbstractCharSeqMonitor.IterationScenario;
import omni.impl.seq.AbstractCharSeqMonitor.ItrRemoveScenario;
import omni.impl.seq.AbstractCharSeqMonitor.MonitoredFunctionGen;
import omni.impl.seq.AbstractCharSeqMonitor.MonitoredRemoveIfPredicateGen;
import omni.impl.seq.AbstractCharSeqMonitor.QueryTester;
import java.nio.file.Files;
import omni.impl.seq.AbstractCharSeqMonitor.SequenceVerificationItr;
import omni.api.OmniCollection;
import omni.api.OmniListIterator;
import java.util.ArrayList;
import omni.api.OmniDeque;
@SuppressWarnings({"rawtypes","unchecked"})
@Tag("DblLnkSeqTest")
@Execution(ExecutionMode.CONCURRENT)
public class CharDblLnkSeqTest{
  @org.junit.jupiter.params.ParameterizedTest
  @org.junit.jupiter.params.provider.EnumSource(CheckedType.class)
  public void testConstructor_void(CheckedType checkedType){
    switch(checkedType){
      case CHECKED:{
        var seq=new CharDblLnkSeq.CheckedList();
        Assertions.assertNull(seq.head);
        Assertions.assertNull(seq.tail);
        Assertions.assertEquals(0,seq.size);
        Assertions.assertEquals(0,seq.modCount);
        break;
      }
      case UNCHECKED:{
        var seq=new CharDblLnkSeq.UncheckedList();
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
    var head=new CharDblLnkNode(TypeConversionUtil.convertTochar(1));
    var tail=new CharDblLnkNode(TypeConversionUtil.convertTochar(2));
    head.next=tail;
    tail.prev=head;
    int seqSize=2;
    switch(checkedType){
      case CHECKED:{
        var seq=new CharDblLnkSeq.CheckedList(head,seqSize,tail);
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
        var seq=new CharDblLnkSeq.UncheckedList(head,seqSize,tail);
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
              //System.out.println("CharDblLnkSeqTest.testadd_val<"+nestedType+","+checkedType+","+preModScenario+","+seqSize+">");
              Stream.of(CharInputTestArgType.values())
              .parallel()
              .forEach(inputArgType->{
                switch(nestedType){
                  case SUBLIST:
                    if(inputArgType==CharInputTestArgType.ARRAY_TYPE){
                      IntStream.rangeClosed(1,3)
                      .parallel()
                      .forEach(parentsLength->{
                        if(parentsLength!=1 || preModScenario!=PreModScenario.ModParent){
                          runSubListTests(checkedType,true,parentsLength,seqMonitor->testadd_valHelper(seqMonitor,inputArgType,preModScenario,finalSeqSize));
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
  (SeqMonitor seqMonitor,CharInputTestArgType inputArgType,PreModScenario preModScenario,int numToAdd){
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
    getQueryCollectionArguments().parallel().map(Arguments::get).forEach(args->{
        testcontains_valHelper((SeqMonitor)args[0],(QueryTester)args[1],(QueryCastType)args[2],(SequenceLocation)args[3],(int)args[4],(PreModScenario)args[5]
        );
    });
  }
  private static void testcontains_valHelper
  (SeqMonitor seqMonitor,QueryTester argType,QueryCastType queryCastType,SequenceLocation seqLocation,int numToAdd,PreModScenario preModScenario 
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
      Assertions.assertEquals(seqLocation!=SequenceLocation.IOBHI,argType.invokecontains(seqMonitor,queryCastType));
    }else{
      switch(argType)
      {
        case Byteneg1:
        case Shortneg1:
        case Integerneg1:
        case ShortMIN_BYTE_MINUS1:
        case IntegerMIN_BYTE_MINUS1:
        case IntegerMIN_SHORT_MINUS1:
        case IntegerMAX_CHAR_PLUS1:
        case IntegerMAX_SAFE_INT_PLUS1:
        case IntegerMIN_SAFE_INT_MINUS1:
        if(preModScenario.expectedException!=null && queryCastType==QueryCastType.ToObject){
          Assertions.assertThrows(preModScenario.expectedException,()->argType.invokecontains(seqMonitor,queryCastType));
          break;
        }
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
  }
  @org.junit.jupiter.api.Test
  public void testindexOf_val(){
    getQueryListArguments().parallel().map(Arguments::get).forEach(args->{
        testindexOf_valHelper((SeqMonitor)args[0],(QueryTester)args[1],(QueryCastType)args[2],(SequenceLocation)args[3],(int)args[4],(PreModScenario)args[5]
        );
    });
  }
  private static void testindexOf_valHelper
  (SeqMonitor seqMonitor,QueryTester argType,QueryCastType queryCastType,SequenceLocation seqLocation,int numToAdd,PreModScenario preModScenario 
  ){
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
        case Byteneg1:
        case Shortneg1:
        case Integerneg1:
        case ShortMIN_BYTE_MINUS1:
        case IntegerMIN_BYTE_MINUS1:
        case IntegerMIN_SHORT_MINUS1:
        case IntegerMAX_CHAR_PLUS1:
        case IntegerMAX_SAFE_INT_PLUS1:
        case IntegerMIN_SAFE_INT_MINUS1:
        if(preModScenario.expectedException!=null && queryCastType==QueryCastType.ToObject){
          Assertions.assertThrows(preModScenario.expectedException,()->argType.invokecontains(seqMonitor,queryCastType));
          break;
        }
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
  }
  @org.junit.jupiter.api.Test
  public void testlastIndexOf_val(){
    getQueryListArguments().parallel().map(Arguments::get).forEach(args->{
        testlastIndexOf_valHelper((SeqMonitor)args[0],(QueryTester)args[1],(QueryCastType)args[2],(SequenceLocation)args[3],(int)args[4],(PreModScenario)args[5]
        );
    });
  }
  private static void testlastIndexOf_valHelper
  (SeqMonitor seqMonitor,QueryTester argType,QueryCastType queryCastType,SequenceLocation seqLocation,int numToAdd,PreModScenario preModScenario 
  ){
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
        case Byteneg1:
        case Shortneg1:
        case Integerneg1:
        case ShortMIN_BYTE_MINUS1:
        case IntegerMIN_BYTE_MINUS1:
        case IntegerMIN_SHORT_MINUS1:
        case IntegerMAX_CHAR_PLUS1:
        case IntegerMAX_SAFE_INT_PLUS1:
        case IntegerMIN_SAFE_INT_MINUS1:
        if(preModScenario.expectedException!=null && queryCastType==QueryCastType.ToObject){
          Assertions.assertThrows(preModScenario.expectedException,()->argType.invokecontains(seqMonitor,queryCastType));
          break;
        }
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
  }
  @org.junit.jupiter.api.Test
  public void testsearch_val(){
    getQueryCollectionArguments().parallel().map(Arguments::get).forEach(args->{
        testsearch_valHelper((SeqMonitor)args[0],(QueryTester)args[1],(QueryCastType)args[2],(SequenceLocation)args[3],(int)args[4],(PreModScenario)args[5]
        );
    });
  }
  private static void testsearch_valHelper
  (SeqMonitor seqMonitor,QueryTester argType,QueryCastType queryCastType,SequenceLocation seqLocation,int numToAdd,PreModScenario preModScenario 
  ){
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
  }
  @org.junit.jupiter.api.Test
  public void testremoveVal_val(){
    getQueryCollectionArguments().parallel().map(Arguments::get).forEach(args->{
        testremoveVal_valHelper((SeqMonitor)args[0],(QueryTester)args[1],(QueryCastType)args[2],(SequenceLocation)args[3],(int)args[4],(PreModScenario)args[5]
        );
    });
  }
  private static void testremoveVal_valHelper
  (SeqMonitor seqMonitor,QueryTester argType,QueryCastType queryCastType,SequenceLocation seqLocation,int numToAdd,PreModScenario preModScenario 
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
                    //System.out.println("CharDblLnkSeqTest.testListadd_int_val<"+nestedType+","+checkedType+","+preModScenario+","+seqSize+","+seqLocation+">");
                    Stream.of(CharInputTestArgType.values())
                    .parallel()
                    .forEach(inputArgType->{
                      switch(nestedType){
                        case SUBLIST:
                          if(inputArgType==CharInputTestArgType.ARRAY_TYPE){
                            IntStream.rangeClosed(1,3)
                            .parallel()
                            .forEach(parentsLength->{
                              if(parentsLength!=1 || preModScenario!=PreModScenario.ModParent){
                                runSubListTests(checkedType,true,parentsLength,seqMonitor->testListadd_int_valHelper(seqMonitor,inputArgType,seqLocation,preModScenario,finalSeqSize));
                              }
                            });
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
  (SeqMonitor seqMonitor,CharInputTestArgType inputArgType,SequenceLocation seqLocation,PreModScenario preModScenario,int numToAdd){
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
                  //System.out.println("CharDblLnkSeqTest.testListItradd_val<"+nestedType+","+checkedType+","+preModScenario+","+seqSize+","+seqLocation+">");
                  Stream.of(CharInputTestArgType.values())
                  .parallel()
                  .forEach(inputArgType->{
                    switch(nestedType){
                      case SUBLIST:
                        if(inputArgType==CharInputTestArgType.ARRAY_TYPE)
                        {
                          IntStream.rangeClosed(1,3)
                          .parallel()
                          .forEach(parentsLength->{
                            if(parentsLength!=1 || preModScenario!=PreModScenario.ModParent){
                              runSubListTests(checkedType,true,parentsLength,seqMonitor->testListItradd_valHelper(seqMonitor,preModScenario,finalSeqSize,seqLocation,inputArgType));
                            }
                          });
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
  (SeqMonitor seqMonitor,PreModScenario preModScenario,int numToAdd,SequenceLocation seqLocation,CharInputTestArgType inputArgType){
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
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var functionCallType:FunctionCallType.values()){
          Stream.of(PreModScenario.values()).filter(preModScenario->preModScenario!=PreModScenario.ModSeq && (preModScenario.expectedException==null || checkedType.checked) && (!nestedType.rootType || preModScenario==PreModScenario.NoMod))
          //.parallel()
          .forEach(preModScenario->{
            Stream.of(MonitoredRemoveIfPredicateGen.values()).filter(monitoredRemoveIfPredicateGen->(monitoredRemoveIfPredicateGen.expectedException==null || (checkedType.checked && (!nestedType.rootType || monitoredRemoveIfPredicateGen.appliesToRoot))))
            //.parallel()
            .forEach(monitoredRemoveIfPredicateGen->{
              IntStream.iterate(0,seqSize->seqSize+10).limit(11)
              //.parallel()
              .forEach(seqSize->{
                System.out.println("CharDblLnkSeq.testremoveIf_Predicate<"+nestedType+","+checkedType+","+functionCallType+","+preModScenario+","+monitoredRemoveIfPredicateGen+","+seqSize+">");
                double[] thresholdArr;
                long randSeedBound;
                if(seqSize==0 || !monitoredRemoveIfPredicateGen.isRandomized){
                  thresholdArr=new double[]{0.5};
                  randSeedBound=0;
                }else{
                  thresholdArr=new double[]{0.01,0.05,0.10,0.25,0.50,0.75,0.90,0.95,0.99};
                  randSeedBound=100;
                }
                LongStream.rangeClosed(0,randSeedBound)
                  //.parallel()
                  .forEach(randSeed->{
                    DoubleStream.of(thresholdArr)
                    //.parallel()
                    .forEach(threshold->{
                    switch(nestedType){
                      case SUBLIST:
                        if(functionCallType==FunctionCallType.Unboxed && monitoredRemoveIfPredicateGen.expectedException==null && preModScenario.expectedException==null){
                          IntStream.rangeClosed(1,3)
                          //.parallel()
                          .forEach(parentsLength->{
                            if(parentsLength!=1 || (preModScenario!=PreModScenario.ModParent && monitoredRemoveIfPredicateGen!=MonitoredRemoveIfPredicateGen.ModParent && monitoredRemoveIfPredicateGen!=MonitoredRemoveIfPredicateGen.ThrowModParent)){
                              runSubListTests(checkedType,true,parentsLength,seqMonitor->testremoveIf_PredicateHelper(seqMonitor,preModScenario,monitoredRemoveIfPredicateGen,threshold,randSeed,functionCallType,seqSize));
                            }
                          });
                          break;
                        }
                      case LISTDEQUE:
                        testremoveIf_PredicateHelper(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredRemoveIfPredicateGen,threshold,randSeed,functionCallType,seqSize);
                    }
                  });
                });
              });
            });
          });
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
    final var clone=(OmniCollection.OfChar)seqMonitor.seq.clone();
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
      verifyItr.verifyLiteralIndexAndIterate(cloneItr.nextChar());
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
  private static void runSubListTests(CheckedType checkedType,boolean parallel,int parentLength,Consumer<? super SeqMonitor> testMethod){
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
  /*
    var preAllocStream=IntStream.range(0,1<<parentLength);
    if(parallel){
      preAllocStream=preAllocStream.parallel();
    }
    preAllocStream.forEach(preAllocBits->{
      int[] preAllocs=new int[parentLength];
      for(int index=0,marker=1;index<parentLength;marker<<=1,++index){
        preAllocs[index]=(preAllocBits&marker)!=0?5:0;
      }
      var postAllocStream=IntStream.range(0,1<<parentLength);
      if(parallel){
        postAllocStream=postAllocStream.parallel();
      }
      postAllocStream.forEach(postAllocBits->{
        int[] postAllocs=new int[parentLength];
        for(int index=0,marker=1;index<parentLength;marker<<=1,++index){
          postAllocs[index]=(postAllocBits&marker)!=0?5:0;
        }
        testMethod.accept(new SeqMonitor(checkedType,preAllocs,postAllocs));
      });
    });
    */
  }
  static enum NestedType{
    LISTDEQUE(true),
    SUBLIST(false);
    final boolean rootType;
    NestedType(boolean rootType){
      this.rootType=rootType;
    }
  }
  private static class SeqMonitor extends AbstractCharSeqMonitor<CharDblLnkSeq>{
    private static final CharDblLnkSeq[] EMPTY_PARENTS=new CharDblLnkSeq[0];
    final NestedType nestedType;
    final CharDblLnkSeq[] parents;
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
          this.seq=checkedType.checked?new CharDblLnkSeq.CheckedList():new CharDblLnkSeq.UncheckedList();
          this.parentPreAlloc=0;
          this.parentPostAlloc=0;
          this.rootPostAlloc=0;
          this.parents=EMPTY_PARENTS;
          this.parentOffsets=OmniArray.OfInt.DEFAULT_ARR;
          this.expectedParentModCounts=OmniArray.OfInt.DEFAULT_ARR;
          this.expectedParentSizes=OmniArray.OfInt.DEFAULT_ARR;
          break;
        case SUBLIST:
          var rootHead=new CharDblLnkNode(TypeConversionUtil.convertTochar(Integer.MIN_VALUE));
          var currHead=rootHead;
          for(int i=1;i<10;++i){
            currHead=currHead.next=new CharDblLnkNode(currHead,TypeConversionUtil.convertTochar(Integer.MIN_VALUE+i));
          }
          var rootTail=new CharDblLnkNode(TypeConversionUtil.convertTochar(Integer.MAX_VALUE));
          var currTail=rootTail;
          for(int i=1;i<10;++i){
            currTail=currTail.prev=new CharDblLnkNode(TypeConversionUtil.convertTochar(Integer.MAX_VALUE-i),currTail);
          }
          currHead.next=currTail;
          currTail.prev=currHead;
          CharDblLnkSeq root;
          root=checkedType.checked?new CharDblLnkSeq.CheckedList(rootHead,20,rootTail):new CharDblLnkSeq.UncheckedList(rootHead,20,rootTail);
          this.parents=new CharDblLnkSeq[2];
          this.parents[1]=root;
          this.parents[0]=(CharDblLnkSeq)root.subList(5,15);
          this.seq=(CharDblLnkSeq)parents[0].subList(5,5);
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
        this.seq=checkedType.checked?new CharDblLnkSeq.CheckedList():new CharDblLnkSeq.UncheckedList();
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
        CharDblLnkNode rootHead;
        CharDblLnkNode rootTail;
        if(totalPreAlloc==0){
          if(totalPostAlloc==0){
            rootHead=null;
            rootTail=null;
          }else{
            rootTail=new CharDblLnkNode(TypeConversionUtil.convertTochar(Integer.MAX_VALUE));
            rootHead=rootTail;
            for(int i=1;i<totalPostAlloc;++i){
              rootHead=rootHead.prev=new CharDblLnkNode(TypeConversionUtil.convertTochar(Integer.MAX_VALUE-i),rootHead);
            }
          }
        }else{
          rootHead=new CharDblLnkNode(TypeConversionUtil.convertTochar(Integer.MIN_VALUE));
          rootTail=rootHead;
          for(int i=1;i<totalPreAlloc;++i){
            rootTail=rootTail.next=new CharDblLnkNode(rootTail,TypeConversionUtil.convertTochar(Integer.MIN_VALUE+i));
          }
          for(int i=totalPostAlloc;--i>=0;){
            rootTail=rootTail.next=new CharDblLnkNode(rootTail,TypeConversionUtil.convertTochar(Integer.MAX_VALUE-i));
          }
        }
        CharDblLnkSeq root;
        int rootSize=totalPreAlloc+totalPostAlloc;
        root=checkedType.checked?new CharDblLnkSeq.CheckedList(rootHead,rootSize,rootTail):new CharDblLnkSeq.UncheckedList(rootHead,rootSize,rootTail);
        this.parents=new CharDblLnkSeq[parentPreAllocs.length];
        this.parentOffsets=new int[parentPreAllocs.length];
        this.expectedParentModCounts=new int[parentPreAllocs.length];
        this.expectedParentSizes=new int[parentPreAllocs.length];
        this.expectedParentSizes[parentPreAllocs.length-1]=rootSize;
        this.parents[parentPreAllocs.length-1]=root;
        for(int i=parentPreAllocs.length;--i>=1;){
          int fromIndex=parentPreAllocs[i];
          int toIndex=expectedParentSizes[i]-parentPostAllocs[i];
          parents[i-1]=(CharDblLnkSeq)parents[i].subList(fromIndex,toIndex);
          parentOffsets[i]=fromIndex;
          expectedParentSizes[i-1]=toIndex-fromIndex;
        }
        int fromIndex=parentPreAllocs[0];
        parentOffsets[0]=fromIndex;
        int toIndex=expectedParentSizes[0]-parentPostAllocs[0];
        this.seq=(CharDblLnkSeq)parents[0].subList(fromIndex,toIndex);
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
    AbstractCharSeqMonitor.AbstractItrMonitor getDescendingItrMonitor(){
      return checkedType.checked?new CheckedDescendingItrMonitor():new UncheckedDescendingItrMonitor();
    }
    AbstractCharSeqMonitor.AbstractItrMonitor getListItrMonitor(){
      switch(nestedType){
        case LISTDEQUE:
          return checkedType.checked?new CheckedBidirectionalItrMonitor():new UncheckedBidirectionalItrMonitor();
        case SUBLIST:
          return checkedType.checked?new CheckedBidirectionalSubItrMonitor():new UncheckedBidirectionalSubItrMonitor();
        default:
          throw new Error("Unknown nested type "+nestedType);
      }
    }
    AbstractCharSeqMonitor.AbstractItrMonitor getListItrMonitor(int index){
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
      CharDblLnkNode curr;
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
         CharInputTestArgType.ARRAY_TYPE.verifyVal(expectedVal,curr.val);
      }
      return new DblLnkSeqVerificationItr(offset,curr,this);
    }
    SequenceVerificationItr verifyPreAlloc(){
      int rootPreAlloc;
      CharDblLnkNode curr;
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
        CharInputTestArgType.ARRAY_TYPE.verifyVal(v,curr.val);
      }
      return new DblLnkSeqVerificationItr(offset,curr,this);
    }
    void illegalAdd(PreModScenario preModScenario){
      switch(preModScenario)
      {
        case ModSeq:
          CharInputTestArgType.ARRAY_TYPE.callCollectionAdd(seq,0);
          verifyAddition();
          break;
        case ModParent:
          int index;
          CharInputTestArgType.ARRAY_TYPE.callCollectionAdd(parents[index=parents.length-2],0);
          ++expectedParentSizes[index];
          ++expectedParentModCounts[index];
          ++expectedParentSizes[++index];
          ++expectedParentModCounts[index];
          break;
        case ModRoot:
          CharInputTestArgType.ARRAY_TYPE.callCollectionAdd(parents[index=parents.length-1],0);
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
      var builder=new StringBuilder("CharDblLnkSeq").append(checkedType.checked?"Checked":"Unchecked");
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
        Assertions.assertEquals(expectedSeqModCount,((CharDblLnkSeq.CheckedList)seq).modCount);
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
      CharDblLnkSeq currList,currParent;
      int currSize;
      Assertions.assertEquals(currSize=this.expectedSeqSize,(currList=this.seq).size);
      Assertions.assertEquals(expectedSeqModCount,FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.modCount(currList));
      CharDblLnkSeq[] parents;
      var root=(parents=this.parents)[parents.length-1];
      int parentSize;
      CharDblLnkNode currHead;
      for(int parentIndex=0,parentBound=parents.length;;){
        parentSize=expectedParentSizes[parentIndex];
        currParent=parents[parentIndex];
        if(parentIndex==parentBound-1){
          Assertions.assertNull(FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.parent(currList));
          Assertions.assertEquals(expectedParentModCounts[parentIndex],FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.modCount(currParent));
        }else{
          Assertions.assertSame(currParent,FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.parent(currList));
          Assertions.assertEquals(expectedParentModCounts[parentIndex],FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.modCount(currParent));
        }
        Assertions.assertSame(root,FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.root(currList));
        int preAlloc=parentOffsets[parentIndex];
        Assertions.assertEquals(preAlloc,FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.parentOffset(currList));
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
              Assertions.assertEquals(preAlloc=parentOffsets[++parentIndex],FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.parentOffset(currList=currParent));
              if(parentIndex==parentBound-1){
                Assertions.assertNull(FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.parent(currParent));
                currParent=parents[parentIndex];
                Assertions.assertEquals(expectedParentModCounts[parentIndex],FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.modCount(currParent));
              }else{
                Assertions.assertSame(root,FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.root(currList));
                Assertions.assertSame(currParent=parents[parentIndex],FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.parent(currList));
                Assertions.assertEquals(expectedParentModCounts[parentIndex],FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.modCount(currParent));
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
      CharDblLnkSeq currList,currParent;
      int currSize;
      Assertions.assertEquals(currSize=this.expectedSeqSize,(currList=this.seq).size);
      CharDblLnkSeq[] parents;
      var root=(parents=this.parents)[parents.length-1];
      int parentSize;
      CharDblLnkNode currHead;
      for(int parentIndex=0,parentBound=parents.length;;){
        parentSize=expectedParentSizes[parentIndex];
        currParent=parents[parentIndex];
        if(parentIndex==parentBound-1){
          Assertions.assertNull(FieldAndMethodAccessor.CharDblLnkSeq.UncheckedSubList.parent(currList));
        }else{
          Assertions.assertSame(currParent,FieldAndMethodAccessor.CharDblLnkSeq.UncheckedSubList.parent(currList));
        }
        Assertions.assertSame(root,FieldAndMethodAccessor.CharDblLnkSeq.UncheckedSubList.root(currList));
        int preAlloc=parentOffsets[parentIndex];
        Assertions.assertEquals(preAlloc,FieldAndMethodAccessor.CharDblLnkSeq.UncheckedSubList.parentOffset(currList));
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
              Assertions.assertEquals(preAlloc=parentOffsets[++parentIndex],FieldAndMethodAccessor.CharDblLnkSeq.UncheckedSubList.parentOffset(currList=currParent));
              if(parentIndex==parentBound-1){
                Assertions.assertNull(FieldAndMethodAccessor.CharDblLnkSeq.UncheckedSubList.parent(currParent));
                currParent=parents[parentIndex];
              }else{
                Assertions.assertSame(root,FieldAndMethodAccessor.CharDblLnkSeq.UncheckedSubList.root(currList));
                Assertions.assertSame(currParent=parents[parentIndex],FieldAndMethodAccessor.CharDblLnkSeq.UncheckedSubList.parent(currList));
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
            FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.writeObject(seq,oos);
          }else{
            FieldAndMethodAccessor.CharDblLnkSeq.UncheckedList.writeObject(seq,oos);
          }
          break;
        case SUBLIST:
          if(checkedType.checked){
            FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.writeObject(seq,oos);
          }else{
            FieldAndMethodAccessor.CharDblLnkSeq.UncheckedSubList.writeObject(seq,oos);
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
      CharDblLnkNode curr;
      int index;
      final SeqMonitor seqMonitor;
      private DblLnkSeqVerificationItr(int index,CharDblLnkNode curr,SeqMonitor seqMonitor){
        this.index=index;
        this.seqMonitor=seqMonitor;
        this.curr=curr;
      }
      SequenceVerificationItr verifyNaturalAscending(int v,CharInputTestArgType inputArgType,int length){
        return verifyAscending(v,inputArgType,length);
      }
      @Override SequenceVerificationItr verifyPostAlloc(int expectedVal){
        for(int i=0,bound=seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc;i<bound;++i){
          verifyIndexAndIterate(CharInputTestArgType.ARRAY_TYPE,expectedVal);
        }
        Assertions.assertNull(curr);
        Assertions.assertEquals(seqMonitor.expectedParentSizes.length==0?seqMonitor.expectedSeqSize:seqMonitor.expectedParentSizes[seqMonitor.parents.length-1],index);
        return this;
      }
      @Override void verifyLiteralIndexAndIterate(char val){
        Assertions.assertEquals(val,curr.val);
        curr=curr.next;
        ++index;
      }
      private CharDblLnkNode getReverseNode(){
        CharDblLnkNode curr;
        return (curr=this.curr)==null?seqMonitor.parents.length==0?seqMonitor.seq.tail:seqMonitor.parents[seqMonitor.parents.length-1].tail:curr.prev;
      }
      @Override void reverseAndVerifyIndex(CharInputTestArgType inputArgType,int val){
        CharDblLnkNode curr;
        inputArgType.verifyVal(val,(curr=getReverseNode()).val);
        this.curr=curr;
        --index;
      }
      @Override void verifyIndexAndIterate(CharInputTestArgType inputArgType,int val){ 
        inputArgType.verifyVal(val,curr.val);
        curr=curr.next;
        ++index;
      }
      private CharDblLnkNode getNode(int i){
        if(i<0){
          if(this.curr==null){
            return CharDblLnkNode.iterateDescending(seqMonitor.parents.length==0?seqMonitor.seq.tail:seqMonitor.parents[seqMonitor.parents.length-1].tail,(-i)-1);
          }
          return CharDblLnkNode.uncheckedIterateDescending(this.curr,-i);
        }
        return CharDblLnkNode.iterateAscending(curr,i);
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
          verifyIndexAndIterate(CharInputTestArgType.ARRAY_TYPE,v);
        }
        return this;
      }
      @Override SequenceVerificationItr verifyParentPostAlloc(){
        for(int i=0,rootPostAlloc=seqMonitor.rootPostAlloc,v=Integer.MAX_VALUE-(rootPostAlloc+seqMonitor.parentPostAlloc-1);i<seqMonitor.parentPostAlloc;++i,++v){
          verifyIndexAndIterate(CharInputTestArgType.ARRAY_TYPE,v);
        }
        return this;
      }
    }
    class UncheckedSubAscendingItrMonitor extends UncheckedAscendingItrMonitor{
      UncheckedSubAscendingItrMonitor(){
        super();
      }
      CharDblLnkNode getNewCurr(){
        if(expectedCurr!=null && expectedCurr!=seq.tail){
          return expectedCurr.next;
        }
        return null;
      }
      void verifyIteratorState(){
        Assertions.assertSame(FieldAndMethodAccessor.CharDblLnkSeq.UncheckedSubList.AscendingItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.CharDblLnkSeq.UncheckedSubList.AscendingItr.parent(itr),seq);
      }
    }
    class UncheckedAscendingItrMonitor extends AbstractCharSeqMonitor.AbstractItrMonitor{
      CharDblLnkNode expectedCurr;
      UncheckedAscendingItrMonitor(){
        this(ItrType.Itr,seq.iterator(),seq.head);
      }
      private UncheckedAscendingItrMonitor(ItrType itrType,OmniIterator.OfChar itr,CharDblLnkNode expectedCurr){
        super(itrType,itr,expectedSeqModCount);
        this.expectedCurr=expectedCurr;
      }
      void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
        if(functionCallType==FunctionCallType.Boxed){
          itr.forEachRemaining((Consumer)action);
        }else
        {
          itr.forEachRemaining((CharConsumer)action);
        }
        this.expectedCurr=null;
      }
      SeqMonitor getSeqMonitor(){
        return SeqMonitor.this;
      }
      CharDblLnkNode getNewCurr(){
        return expectedCurr!=null?expectedCurr.next:null;
      }
      void verifyNext(int expectedVal,CharOutputTestArgType outputType){
        var newCurr=getNewCurr();
        outputType.verifyItrNext(itr,expectedVal);
        expectedCurr=newCurr;
      }
      void iterateForward(){
        var newCurr=getNewCurr();
        itr.nextChar();
        expectedCurr=newCurr;
      }
      void remove(){
        itr.remove();
        verifyRemoval();
      }
      void verifyIteratorState(){
        Assertions.assertSame(FieldAndMethodAccessor.CharDblLnkSeq.UncheckedList.AscendingItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.CharDblLnkSeq.UncheckedList.AscendingItr.parent(itr),seq);
      }
    }
    class UncheckedDescendingItrMonitor extends UncheckedAscendingItrMonitor{
      UncheckedDescendingItrMonitor(){
        super(ItrType.DescendingItr,((OmniDeque.OfChar)seq).descendingIterator(),seq.tail);
      }
      CharDblLnkNode getNewCurr(){
        return expectedCurr!=null?expectedCurr.prev:null;
      }
      void verifyIteratorState(){
        Assertions.assertSame(FieldAndMethodAccessor.CharDblLnkSeq.UncheckedList.DescendingItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.CharDblLnkSeq.UncheckedList.DescendingItr.parent(itr),seq);
      }
    }
    class CheckedDescendingItrMonitor extends UncheckedDescendingItrMonitor{
      CharDblLnkNode expectedLastRet;
      int expectedCurrIndex;
      CheckedDescendingItrMonitor(){
        super();
        this.expectedCurrIndex=expectedSeqSize;
      }
      void verifyNext(int expectedVal,CharOutputTestArgType outputType){
        var newCurr=getNewCurr();
        outputType.verifyItrNext(itr,expectedVal);
        expectedLastRet=expectedCurr;
        expectedCurr=newCurr;
        --expectedCurrIndex;
      }
      void iterateForward(){
        var newCurr=getNewCurr();
        itr.nextChar();
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
        Assertions.assertEquals(FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.DescendingItr.modCount(itr),expectedItrModCount);
        Assertions.assertEquals(FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.DescendingItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertSame(FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.DescendingItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.DescendingItr.lastRet(itr),expectedLastRet);
        Assertions.assertSame(FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.DescendingItr.parent(itr),seq);
      }
      void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
        if(functionCallType==FunctionCallType.Boxed){
          itr.forEachRemaining((Consumer)action);
        }else
        {
          itr.forEachRemaining((CharConsumer)action);
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
        CharDblLnkNode newLastRet=seq.tail;
        if(functionCallType==FunctionCallType.Boxed){
          itr.forEachRemaining((Consumer)action);
        }else
        {
          itr.forEachRemaining((CharConsumer)action);
        }
        if(expectedCurrIndex<parentSize){
          this.expectedLastRet=newLastRet;
          this.expectedCurrIndex=parentSize;
          this.expectedCurr=newLastRet.next;
        }
      }
      void verifyIteratorState(){
        Assertions.assertEquals(FieldAndMethodAccessor.CharDblLnkSeq.UncheckedSubList.BidirectionalItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertSame(FieldAndMethodAccessor.CharDblLnkSeq.UncheckedSubList.BidirectionalItr.parent(itr),seq);
        Assertions.assertSame(FieldAndMethodAccessor.CharDblLnkSeq.UncheckedSubList.BidirectionalItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.CharDblLnkSeq.UncheckedSubList.BidirectionalItr.lastRet(itr),expectedLastRet);
      }
    }
    class UncheckedBidirectionalItrMonitor extends AbstractCharSeqMonitor.AbstractItrMonitor{
      CharDblLnkNode expectedCurr;
      int expectedCurrIndex;
      CharDblLnkNode expectedLastRet;
      UncheckedBidirectionalItrMonitor(){
        super(ItrType.ListItr,seq.listIterator(),expectedSeqModCount);
        this.expectedCurr=seq.head;
        this.expectedCurrIndex=0;
      }
      UncheckedBidirectionalItrMonitor(ItrType itrType,OmniIterator.OfChar itr,int currIndex){
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
            this.expectedCurr=CharDblLnkNode.uncheckedIterateDescending(seq.tail,parentSize-1);
          }
        }else{
          this.expectedCurr=CharDblLnkNode.iterateAscending(seq.head,currIndex);
        }
      }
      UncheckedBidirectionalItrMonitor(int index){
        this(ItrType.ListItr,seq.listIterator(index),index);
      }
      CharDblLnkNode getNewNextNode(){
        return expectedCurr==null?null:expectedCurr.next;
      }
      CharDblLnkNode getNewPrevNode(){
        return expectedCurr==null?seq.tail:expectedCurr.prev;
      }
      void iterateReverse(){
        CharDblLnkNode newLastRet=getNewPrevNode();
        ((OmniListIterator.OfChar)itr).previousChar();
        this.expectedLastRet=newLastRet;
        this.expectedCurr=newLastRet;
        --this.expectedCurrIndex;
      }
      void verifyPrevious(int expectedVal,CharOutputTestArgType outputType){
        CharDblLnkNode newLastRet=getNewPrevNode();
        outputType.verifyItrPrevious(((OmniListIterator.OfChar)itr),expectedVal);
         this.expectedLastRet=newLastRet;
        this.expectedCurr=newLastRet;
        --this.expectedCurrIndex;
      }
      void add(int v,CharInputTestArgType inputArgType){
        inputArgType.callListItrAdd(((OmniListIterator.OfChar)itr),v);
        ++this.expectedCurrIndex;
        this.expectedLastRet=null;
        verifyAddition();
      }
      void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
        if(functionCallType==FunctionCallType.Boxed){
          itr.forEachRemaining((Consumer)action);
        }else
        {
          itr.forEachRemaining((CharConsumer)action);
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
      void verifyNext(int expectedVal,CharOutputTestArgType outputType){
        var newCurr=getNewNextNode();
        outputType.verifyItrNext(itr,expectedVal);
        this.expectedLastRet=expectedCurr;
        this.expectedCurr=newCurr;
        ++this.expectedCurrIndex;
      }
      void iterateForward(){
        var newCurr=getNewNextNode();
        itr.nextChar();
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
        Assertions.assertEquals(FieldAndMethodAccessor.CharDblLnkSeq.UncheckedList.BidirectionalItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertSame(FieldAndMethodAccessor.CharDblLnkSeq.UncheckedList.BidirectionalItr.parent(itr),seq);
        Assertions.assertSame(FieldAndMethodAccessor.CharDblLnkSeq.UncheckedList.BidirectionalItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.CharDblLnkSeq.UncheckedList.BidirectionalItr.lastRet(itr),expectedLastRet);
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
        Assertions.assertEquals(FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.BidirectionalItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertEquals(FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.BidirectionalItr.modCount(itr),expectedItrModCount);
        Assertions.assertSame(FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.BidirectionalItr.parent(itr),seq);
        Assertions.assertSame(FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.BidirectionalItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.BidirectionalItr.lastRet(itr),expectedLastRet);
      }
    }
    class CheckedSubAscendingItrMonitor extends CheckedBidirectionalItrMonitor{
      CheckedSubAscendingItrMonitor(){
        super();
      }
      CheckedSubAscendingItrMonitor(int index){
        super(index);
      }
      CharDblLnkNode getNewNextNode(){
        return expectedCurr!=null &&expectedCurrIndex<expectedSeqSize?expectedCurr.next:null;
      }
      void verifyIteratorState(){
        Assertions.assertEquals(FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.AscendingItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertEquals(FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.AscendingItr.modCount(itr),expectedItrModCount);
        Assertions.assertSame(FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.AscendingItr.parent(itr),seq);
        Assertions.assertSame(FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.AscendingItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.AscendingItr.lastRet(itr),expectedLastRet);
      }
    }
    class CheckedBidirectionalItrMonitor extends CheckedAscendingItrMonitor{
      CheckedBidirectionalItrMonitor(int index){
        super(ItrType.ListItr,seq.listIterator(index),index);
      }
      CheckedBidirectionalItrMonitor(){
        super(ItrType.ListItr,seq.listIterator(),0);
      }
      void add(int v,CharInputTestArgType inputArgType){
        super.add(v,inputArgType);
        ++expectedItrModCount;
      }
      CharDblLnkNode getNewPrevNode(){
        return expectedCurrIndex!=0?expectedCurr==null?seq.tail:expectedCurr.prev:null;
      }
      void verifyIteratorState(){
        Assertions.assertEquals(FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.BidirectionalItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertEquals(FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.BidirectionalItr.modCount(itr),expectedItrModCount);
        Assertions.assertSame(FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.BidirectionalItr.parent(itr),seq);
        Assertions.assertSame(FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.BidirectionalItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.BidirectionalItr.lastRet(itr),expectedLastRet);
      }
    }
    class CheckedAscendingItrMonitor extends UncheckedBidirectionalItrMonitor{
      CheckedAscendingItrMonitor(){
        super(ItrType.Itr,seq.iterator(),0);
      }
      CheckedAscendingItrMonitor(ItrType itrType,OmniIterator.OfChar itr,int index){
        super(itrType,itr,index);
      }
      void verifyIteratorState(){
        Assertions.assertEquals(FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.AscendingItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertEquals(FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.AscendingItr.modCount(itr),expectedItrModCount);
        Assertions.assertSame(FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.AscendingItr.parent(itr),seq);
        Assertions.assertSame(FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.AscendingItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.AscendingItr.lastRet(itr),expectedLastRet);
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
          itr.forEachRemaining((CharConsumer)action);
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
  static void buildQueryArguments(Stream.Builder<Arguments> builder,NestedType nestedType){
    for(var checkedType:CheckedType.values()){
      for(var preModScenario:PreModScenario.values()){
        if(preModScenario!=PreModScenario.ModSeq && (preModScenario.expectedException==null || (checkedType.checked && !nestedType.rootType))){
          for(var seqLocation:SequenceLocation.values()){
            if(seqLocation!=SequenceLocation.IOBLO){
              for(int seqSize:AbstractCharSeqMonitor.FIB_SEQ){
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
                        //these input values cannot potentially return true
                        break;
                        default:
                        if(seqSize!=0 && seqLocation.expectedException==null){
                          continue;
                        }
                        //these values must necessarily return false
                      }
                      builder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),argType,queryCastType,seqLocation,seqSize,preModScenario));
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
      for(int seqSize:AbstractCharSeqMonitor.FIB_SEQ){
        streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario,seqSize));
      }
    });
  }
  static Stream<Arguments> getQueryListArguments(){
    Stream.Builder<Arguments> builder=Stream.builder();
    buildQueryArguments(builder,NestedType.LISTDEQUE);
    buildQueryArguments(builder,NestedType.SUBLIST);
    return builder.build();
  }
  static Stream<Arguments> getQueryCollectionArguments(){
    Stream.Builder<Arguments> builder=Stream.builder();
    buildQueryArguments(builder,NestedType.LISTDEQUE);
    return builder.build();
  }
}
