package omni.impl.seq;
import omni.function.FloatConsumer;
import omni.function.FloatPredicate;
import java.util.Arrays;
import java.util.function.Consumer;
import java.io.IOException;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import omni.impl.FloatInputTestArgType;
import omni.impl.FloatOutputTestArgType;
import org.junit.jupiter.params.provider.Arguments;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import omni.util.OmniArray;
import omni.impl.FloatDblLnkNode;
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
import omni.impl.seq.AbstractFloatSeqMonitor.CheckedType;
import omni.impl.seq.AbstractFloatSeqMonitor.PreModScenario;
import omni.impl.seq.AbstractFloatSeqMonitor.SequenceLocation;
import omni.impl.seq.AbstractFloatSeqMonitor.IterationScenario;
import omni.impl.seq.AbstractFloatSeqMonitor.ItrRemoveScenario;
import omni.impl.seq.AbstractFloatSeqMonitor.MonitoredFunctionGen;
import omni.impl.seq.AbstractFloatSeqMonitor.MonitoredRemoveIfPredicateGen;
import omni.impl.seq.AbstractFloatSeqMonitor.QueryTester;
import java.nio.file.Files;
import omni.impl.seq.AbstractFloatSeqMonitor.SequenceVerificationItr;
import omni.api.OmniCollection;
import omni.api.OmniListIterator;
import java.util.ArrayList;
import omni.api.OmniDeque;
@SuppressWarnings({"rawtypes","unchecked"})
@Tag("DblLnkSeqTest")
@Execution(ExecutionMode.CONCURRENT)
public class FloatDblLnkSeqTest{
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
              for(int seqSize:AbstractFloatSeqMonitor.FIB_SEQ){
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
                        //negative values beyond the range of int
                        case FloatMIN_INT_MINUS1:
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
      for(int seqSize:AbstractFloatSeqMonitor.FIB_SEQ){
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
  @org.junit.jupiter.params.ParameterizedTest
  @org.junit.jupiter.params.provider.EnumSource(CheckedType.class)
  public void testConstructor_void(CheckedType checkedType){
    switch(checkedType){
      case CHECKED:{
        var seq=new FloatDblLnkSeq.CheckedList();
        Assertions.assertNull(seq.head);
        Assertions.assertNull(seq.tail);
        Assertions.assertEquals(0,seq.size);
        Assertions.assertEquals(0,seq.modCount);
        break;
      }
      case UNCHECKED:{
        var seq=new FloatDblLnkSeq.UncheckedList();
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
    var head=new FloatDblLnkNode(TypeConversionUtil.convertTofloat(1));
    var tail=new FloatDblLnkNode(TypeConversionUtil.convertTofloat(2));
    head.next=tail;
    tail.prev=head;
    int seqSize=2;
    switch(checkedType){
      case CHECKED:{
        var seq=new FloatDblLnkSeq.CheckedList(head,seqSize,tail);
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
        var seq=new FloatDblLnkSeq.UncheckedList(head,seqSize,tail);
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
  @org.junit.jupiter.params.ParameterizedTest
  @org.junit.jupiter.params.provider.MethodSource("getBasicCollectionTestArgs")
  public void testsize_void
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
      for(int seqSize:AbstractFloatSeqMonitor.FIB_SEQ){
        for(var inputArgType:FloatInputTestArgType.values()){
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
  @org.junit.jupiter.params.ParameterizedTest
  @org.junit.jupiter.params.provider.MethodSource("getadd_valArgs")
  public void testadd_val
  (SeqMonitor seqMonitor,FloatInputTestArgType inputArgType,PreModScenario preModScenario,int numToAdd){
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
  static Stream<Arguments> getListadd_int_valArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var nestedType:NestedType.values()){
      //if(nestedType.forwardIteration){
        for(var checkedType:CheckedType.values()){
          for(var seqLocation:SequenceLocation.values()){
            if(checkedType.checked || seqLocation.expectedException==null){
              for(var preModScenario:PreModScenario.values()){
                if(preModScenario!=PreModScenario.ModSeq && (preModScenario.expectedException==null || checkedType.checked) && (!nestedType.rootType || preModScenario==PreModScenario.NoMod)){
                  for(int seqSize:AbstractFloatSeqMonitor.FIB_SEQ){
                    if(seqSize!=0 || seqLocation.validForEmpty){
                      for(var inputArgType:FloatInputTestArgType.values()){
                        //for(int initialCapacity=0;initialCapacity<=15;initialCapacity+=5){
                          switch(nestedType){
                            case LISTDEQUE:
                              builder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),inputArgType,seqLocation,preModScenario,seqSize));
                              break;
                            case SUBLIST:
                              //for(int rootPreAlloc=0;rootPreAlloc<=5;rootPreAlloc+=5){
                              //  for(int parentPreAlloc=0;parentPreAlloc<=5;parentPreAlloc+=5){
                              //    for(int parentPostAlloc=0;parentPostAlloc<=5;parentPostAlloc+=5){
                              //      for(int rootPostAlloc=0;rootPostAlloc<=5;rootPostAlloc+=5){
                              //        builder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType,initialCapacity,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),inputArgType,seqLocation,preModScenario,seqSize));
                              //      }
                              //    }
                              //  }
                              //}
                              break;
                            default:
                              throw new Error("Unknown nested type "+nestedType);
                          }
                        //}
                      }
                    }
                  }
                }
              }
            }
          }
        }
      //}
    }
    return builder.build();
  }
  @org.junit.jupiter.api.Test
  public void testListadd_int_val(){
    getListadd_int_valArgs().parallel().map(Arguments::get).forEach(args->{
        testListadd_int_valHelper((SeqMonitor)args[0],(FloatInputTestArgType)args[1],(SequenceLocation)args[2],(PreModScenario)args[3],(int)args[4]);
    });
  }
  private static void testListadd_int_valHelper
  (SeqMonitor seqMonitor,FloatInputTestArgType inputArgType,SequenceLocation seqLocation,PreModScenario preModScenario,int numToAdd){
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
  static enum NestedType{
    LISTDEQUE(true),
    SUBLIST(false);
    final boolean rootType;
    NestedType(boolean rootType){
      this.rootType=rootType;
    }
  }
  private static class SeqMonitor extends AbstractFloatSeqMonitor<FloatDblLnkSeq>{
    private static final FloatDblLnkSeq[] EMPTY_PARENTS=new FloatDblLnkSeq[0];
    final NestedType nestedType;
    final FloatDblLnkSeq[] parents;
    final int[] parentOffsets;
    final int[] expectedParentModCounts;
    final int[] expectedParentSizes;
    final int parentPreAlloc;
    final int parentPostAlloc;
    SeqMonitor(CheckedType checkedType,NestedType nestedType,FloatDblLnkNode head,int seqSize,FloatDblLnkNode tail){
      super(checkedType);
      this.nestedType=nestedType;
      this.parentPreAlloc=0;
      this.parentPostAlloc=0;
      this.expectedSeqSize=seqSize;
      switch(nestedType){
        case LISTDEQUE:
          this.seq=checkedType.checked?new FloatDblLnkSeq.CheckedList(head,seqSize,tail):new FloatDblLnkSeq.UncheckedList(head,seqSize,tail);
          this.parents=EMPTY_PARENTS;
          this.parentOffsets=OmniArray.OfInt.DEFAULT_ARR;
          this.expectedParentModCounts=OmniArray.OfInt.DEFAULT_ARR;
          this.expectedParentSizes=OmniArray.OfInt.DEFAULT_ARR;
          break;
        case SUBLIST:
          this.parents=new FloatDblLnkSeq[]{checkedType.checked?new FloatDblLnkSeq.CheckedList(head,seqSize,tail):new FloatDblLnkSeq.UncheckedList(head,seqSize,tail)};
          this.parentOffsets=new int[]{0};
          this.expectedParentModCounts=new int[0];
          this.expectedParentSizes=new int[]{seqSize};
          this.seq=(FloatDblLnkSeq)this.parents[0].subList(0,seqSize);
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
          this.seq=checkedType.checked?new FloatDblLnkSeq.CheckedList():new FloatDblLnkSeq.UncheckedList();
          this.parentPreAlloc=0;
          this.parentPostAlloc=0;
          this.parents=EMPTY_PARENTS;
          this.parentOffsets=OmniArray.OfInt.DEFAULT_ARR;
          this.expectedParentModCounts=OmniArray.OfInt.DEFAULT_ARR;
          this.expectedParentSizes=OmniArray.OfInt.DEFAULT_ARR;
          break;
        case SUBLIST:
          var rootHead=new FloatDblLnkNode(TypeConversionUtil.convertTofloat(Integer.MIN_VALUE));
          var currHead=rootHead;
          for(int i=1;i<10;++i){
            currHead=currHead.next=new FloatDblLnkNode(currHead,TypeConversionUtil.convertTofloat(Integer.MIN_VALUE+i));
          }
          var rootTail=new FloatDblLnkNode(TypeConversionUtil.convertTofloat(Integer.MAX_VALUE));
          var currTail=rootTail;
          for(int i=1;i<10;++i){
            currTail=currTail.prev=new FloatDblLnkNode(TypeConversionUtil.convertTofloat(Integer.MAX_VALUE-i),currTail);
          }
          currHead.next=currTail;
          currTail.prev=currHead;
          FloatDblLnkSeq root;
          root=checkedType.checked?new FloatDblLnkSeq.CheckedList(rootHead,20,rootTail):new FloatDblLnkSeq.UncheckedList(rootHead,20,rootTail);
          this.parents=new FloatDblLnkSeq[2];
          this.parents[1]=root;
          this.parents[0]=(FloatDblLnkSeq)root.subList(5,15);
          this.seq=(FloatDblLnkSeq)parents[0].subList(5,5);
          this.parentOffsets=new int[]{5,5};
          this.expectedParentSizes=new int[]{10,20};
          this.expectedParentModCounts=new int[]{0,0};
          this.parentPreAlloc=5;
          this.parentPostAlloc=5;
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
        this.nestedType=NestedType.LISTDEQUE;
        this.seq=checkedType.checked?new FloatDblLnkSeq.CheckedList():new FloatDblLnkSeq.UncheckedList();
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
        this.parentPreAlloc=totalPreAlloc-parentPreAllocs[parentPreAllocs.length-1];
        this.parentPostAlloc=totalPostAlloc-parentPostAllocs[parentPostAllocs.length-1];
        FloatDblLnkNode rootHead;
        FloatDblLnkNode rootTail;
        if(totalPreAlloc==0)
        {
          if(totalPostAlloc==0)
          {
            rootHead=null;
            rootTail=null;
          }
          else
          {
            rootTail=new FloatDblLnkNode(TypeConversionUtil.convertTofloat(Integer.MAX_VALUE));
            rootHead=rootTail;
            for(int i=1;i<totalPostAlloc;++i)
            {
              rootHead=rootHead.prev=new FloatDblLnkNode(TypeConversionUtil.convertTofloat(Integer.MAX_VALUE-i),rootHead);
            }
          }
        }
        else
        {
          rootHead=new FloatDblLnkNode(TypeConversionUtil.convertTofloat(Integer.MIN_VALUE));
          rootTail=rootHead;
          for(int i=1;i<totalPreAlloc;++i)
          {
            rootTail=rootTail.next=new FloatDblLnkNode(rootTail,TypeConversionUtil.convertTofloat(Integer.MIN_VALUE+i));
          }
          for(int i=totalPostAlloc;--i>=0;)
          {
            rootTail=rootTail.next=new FloatDblLnkNode(rootTail,TypeConversionUtil.convertTofloat(Integer.MAX_VALUE-i));
          }
        }
        FloatDblLnkSeq root;
        int rootSize=totalPreAlloc+totalPostAlloc;
        root=checkedType.checked?new FloatDblLnkSeq.CheckedList(rootHead,rootSize,rootTail):new FloatDblLnkSeq.UncheckedList(rootHead,rootSize,rootTail);
        this.parents=new FloatDblLnkSeq[parentPreAllocs.length];
        this.parentOffsets=new int[parentPreAllocs.length];
        this.expectedParentModCounts=new int[parentPreAllocs.length];
        this.expectedParentSizes=new int[parentPreAllocs.length];
        this.expectedParentSizes[parentPreAllocs.length-1]=rootSize;
        this.parents[parentPreAllocs.length-1]=root;
        for(int i=parentPreAllocs.length-1;--i>=0;){
          int fromIndex=parentPreAllocs[i+1];
          int toIndex=expectedParentSizes[i+1]-parentPostAllocs[i+1];
          parents[i]=(FloatDblLnkSeq)parents[i+1].subList(fromIndex,toIndex);
          parentOffsets[i]=fromIndex;
          expectedParentSizes[i]=toIndex-fromIndex;
        }
        int fromIndex=parentPreAllocs[0];
        int toIndex=expectedParentSizes[0]-parentPostAllocs[0];
        this.seq=(FloatDblLnkSeq)parents[0].subList(fromIndex,toIndex);
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
    AbstractFloatSeqMonitor.AbstractItrMonitor getDescendingItrMonitor(){
      return checkedType.checked?new CheckedDescendingItrMonitor():new UncheckedDescendingItrMonitor();
    }
    AbstractFloatSeqMonitor.AbstractItrMonitor getListItrMonitor(){
      switch(nestedType){
        case LISTDEQUE:
          return checkedType.checked?new CheckedBidirectionalItrMonitor():new UncheckedBidirectionalItrMonitor();
        case SUBLIST:
          return checkedType.checked?new CheckedBidirectionalSubItrMonitor():new UncheckedBidirectionalSubItrMonitor();
        default:
          throw new Error("Unknown nested type "+nestedType);
      }
    }
    AbstractFloatSeqMonitor.AbstractItrMonitor getListItrMonitor(int index){
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
      FloatDblLnkNode curr;
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
         FloatInputTestArgType.ARRAY_TYPE.verifyVal(expectedVal,curr.val);
      }
      return new DblLnkSeqVerificationItr(offset,curr,this);
    }
    SequenceVerificationItr verifyPreAlloc(){
      int rootPreAlloc;
      FloatDblLnkNode curr;
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
        FloatInputTestArgType.ARRAY_TYPE.verifyVal(v,curr.val);
      }
      return new DblLnkSeqVerificationItr(offset,curr,this);
    }
    void illegalAdd(PreModScenario preModScenario){
      switch(preModScenario)
      {
        case ModSeq:
          FloatInputTestArgType.ARRAY_TYPE.callCollectionAdd(seq,0);
          verifyAddition();
          break;
        case ModParent:
          int index;
          FloatInputTestArgType.ARRAY_TYPE.callCollectionAdd(parents[index=parents.length-2],0);
          ++expectedParentSizes[index];
          ++expectedParentModCounts[index];
          ++expectedParentSizes[++index];
          ++expectedParentModCounts[index];
          break;
        case ModRoot:
          FloatInputTestArgType.ARRAY_TYPE.callCollectionAdd(parents[index=parents.length-1],0);
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
      var builder=new StringBuilder("FloatDblLnkSeq").append(checkedType.checked?"Checked":"Unchecked");
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
        Assertions.assertEquals(expectedSeqModCount,((FloatDblLnkSeq.CheckedList)seq).modCount);
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
    private void verifyEmptyUncheckedSubList(int parentIndex){
      FloatDblLnkSeq[] parents;
      for(FloatDblLnkSeq currList=(parents=this.parents)[parentIndex],root=parents[parents.length-1];;){
        Assertions.assertNull(currList.head);
        Assertions.assertNull(currList.tail);
        Assertions.assertEquals(0,currList.size);
        if(parentIndex==0){
          Assertions.assertSame(currList,FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedSubList.parent(currList=this.seq));
          Assertions.assertSame(root,FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedSubList.root(currList));
          Assertions.assertEquals(0,FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedSubList.parentOffset(currList));
          Assertions.assertNull(currList.head);
          Assertions.assertNull(currList.tail);
          Assertions.assertEquals(0,currList.size);
          return;
        }
        Assertions.assertSame(currList,FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedSubList.parent(currList=parents[--parentIndex]));
        Assertions.assertSame(root,FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedSubList.root(currList));
        Assertions.assertEquals(0,FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedSubList.parentOffset(currList));
      }
    }
    private void verifyEmptyCheckedSubList(int parentIndex){
      FloatDblLnkSeq[] parents;
      for(FloatDblLnkSeq currList=(parents=this.parents)[parentIndex],root=parents[parents.length-1];;){
        if(root!=currList){
          Assertions.assertEquals(expectedParentModCounts[parentIndex],FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.modCount(currList));
        }
        Assertions.assertNull(currList.head);
        Assertions.assertNull(currList.tail);
        Assertions.assertEquals(0,currList.size);
        if(parentIndex==0){
          Assertions.assertSame(currList,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.parent(currList=this.seq));
          Assertions.assertSame(root,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.root(currList));
          Assertions.assertEquals(0,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.parentOffset(currList));
          Assertions.assertNull(currList.head);
          Assertions.assertNull(currList.tail);
          Assertions.assertEquals(0,currList.size);
          return;
        }
        Assertions.assertSame(currList,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.parent(currList=parents[--parentIndex]));
        Assertions.assertSame(root,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.root(currList));
        Assertions.assertEquals(0,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.parentOffset(currList));
      }
    }
    private void verifyCheckedSubList(){
    /*
      Assertions.assertEquals(expectedParentModCounts[parents.length-1],FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.modCount(parents[parents.length-1]));
      int[] expectedParentSizes;
      int currSize,parentIndex;
      if((currSize=(expectedParentSizes=this.expectedParentSizes)[parentIndex=expectedParentSizes.length-1])!=0){
        int[] parentOffsets=this.parentOffsets;
        FloatDblLnkSeq[] parents;
        var root=(parents=this.parents)[parentIndex];
        var currList=root;
        var head=currList.head;
        var tail=currList.tail;
        Assertions.assertNull(head.prev);
        Assertions.assertNull(tail.next);
        for(;;){
          Assertions.assertSame(currSize,currList.size);
          int preAlloc=parentOffsets[parentIndex];
          for(int i=0;i<preAlloc;++i){
            FloatDblLnkNode nextNode;
            if((nextNode=head.next)==null){
              break;
            }
            Assertions.assertSame(head,nextNode.prev);
            head=nextNode;
          }
          int postAlloc=currSize-1-preAlloc;
          for(int i=0;i<postAlloc;++i){
            FloatDblLnkNode prevNode;
            if((prevNode=tail.prev)==null){
              break;
            }
            Assertions.assertSame(tail,prevNode.next);
            tail=prevNode;
          }
          if(parentIndex==0){
            Assertions.assertSame(currList,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.parent(currList=seq));
            Assertions.assertSame(root,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.root(currList));
            Assertions.assertEquals(preAlloc,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.parentOffset(currList));
            Assertions.assertEquals(expectedSeqSize,currList.size);
            if(expectedSeqSize==0){
              if(head!=tail){
                Assertions.assertSame(head.next,tail);
                Assertions.assertSame(tail.prev,head);
              }
            }else{
              if(head!=currList.head){
                Assertions.assertSame(head.next,head=currList.head);
              }
              if(tail!=currList.tail){
                Assertions.assertSame(tail.prev,tail=currList.tail);
              }
              for(int i=expectedSeqSize;--i>=0;){
                Assertions.assertSame(head,(head=head.next).prev);
              }
              Assertions.assertSame(head,tail);
            }
            return;
          }
          Assertions.assertSame(parentIndex==parents.length-1?null:currList,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.parent(currList=parents[--parentIndex]));
          Assertions.assertSame(root,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.root(currList));
          Assertions.assertEquals(preAlloc,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.parentOffset(currList));
          Assertions.assertEquals(expectedParentModCounts[parentIndex],FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.modCount(currList));
          if((currSize=expectedParentSizes[parentIndex])==0){
            if(head!=tail){
              Assertions.assertSame(head.next,tail);
              Assertions.assertSame(tail.prev,head);
            }
            break;
          }
        }
      }
      verifyEmptyCheckedSubList(parentIndex);
      */
    }
    private void verifyUncheckedSubList(){
    /*
      int[] expectedParentSizes;
      int currSize,parentIndex;
      if((currSize=(expectedParentSizes=this.expectedParentSizes)[parentIndex=expectedParentSizes.length-1])!=0){
        int[] parentOffsets=this.parentOffsets;
        FloatDblLnkSeq[] parents;
        var root=(parents=this.parents)[parentIndex];
        var currList=root;
        var head=currList.head;
        var tail=currList.tail;
        Assertions.assertNull(head.prev);
        Assertions.assertNull(tail.next);
        for(;;){
          Assertions.assertSame(currSize,currList.size);
          int preAlloc=parentOffsets[parentIndex];
          for(int i=0;i<preAlloc;++i){
            FloatDblLnkNode nextNode;
            if((nextNode=head.next)==null){
              break;
            }
            Assertions.assertSame(head,nextNode.prev);
            head=nextNode;
          }
          int postAlloc=currSize-1-preAlloc;
          for(int i=0;i<postAlloc;++i){
            FloatDblLnkNode prevNode;
            if((prevNode=tail.prev)==null){
              break;
            }
            Assertions.assertSame(tail,prevNode.next);
            tail=prevNode;
          }
          if(parentIndex==0){
            Assertions.assertSame(currList,FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedSubList.parent(currList=seq));
            Assertions.assertSame(root,FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedSubList.root(currList));
            Assertions.assertEquals(preAlloc,FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedSubList.parentOffset(currList));
            Assertions.assertEquals(expectedSeqSize,currList.size);
            if(expectedSeqSize==0){
              if(head!=tail){
                Assertions.assertSame(head.next,tail);
                Assertions.assertSame(tail.prev,head);
              }
            }else{
              if(head!=currList.head){
                Assertions.assertSame(head.next,head=currList.head);
              }
              if(tail!=currList.tail){
                Assertions.assertSame(tail.prev,tail=currList.tail);
              }
              for(int i=expectedSeqSize;--i>=0;){
                Assertions.assertSame(head,(head=head.next).prev);
              }
              Assertions.assertSame(head,tail);
            }
            return;
          }
          Assertions.assertSame(parentIndex==parents.length-1?null:currList,FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedSubList.parent(currList=parents[--parentIndex]));
          Assertions.assertSame(root,FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedSubList.root(currList));
          Assertions.assertEquals(preAlloc,FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedSubList.parentOffset(currList));
          if((currSize=expectedParentSizes[parentIndex])==0){
            if(head!=tail){
              Assertions.assertSame(head.next,tail);
              Assertions.assertSame(tail.prev,head);
            }
            break;
          }
        }
      }
      verifyEmptyUncheckedSubList(parentIndex);
      */
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
            FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.writeObject(seq,oos);
          }else{
            FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedList.writeObject(seq,oos);
          }
          break;
        case SUBLIST:
          if(checkedType.checked){
            FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.writeObject(seq,oos);
          }else{
            FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedSubList.writeObject(seq,oos);
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
    private int getRootPostAlloc(){
      var expectedParentSizes=this.expectedParentSizes;
      switch(expectedParentSizes.length){
        default:
          return expectedParentSizes[expectedParentSizes.length-1]-expectedParentSizes[expectedParentSizes.length-2]-parentOffsets[parentOffsets.length-1];
        case 1:
          return expectedParentSizes[0]-expectedSeqSize-parentOffsets[0];
        case 0:
         return 0;
      }
    }
    private static class DblLnkSeqVerificationItr extends SequenceVerificationItr{
      FloatDblLnkNode curr;
      int index;
      final SeqMonitor seqMonitor;
      private DblLnkSeqVerificationItr(int index,FloatDblLnkNode curr,SeqMonitor seqMonitor){
        this.index=index;
        this.seqMonitor=seqMonitor;
        this.curr=curr;
      }
      SequenceVerificationItr verifyNaturalAscending(int v,FloatInputTestArgType inputArgType,int length){
        return verifyAscending(v,inputArgType,length);
      }
      @Override SequenceVerificationItr verifyPostAlloc(int expectedVal){
        for(int i=0,bound=seqMonitor.parentPostAlloc+seqMonitor.getRootPostAlloc();i<bound;++i){
          verifyIndexAndIterate(FloatInputTestArgType.ARRAY_TYPE,expectedVal);
        }
        return this;
      }
      @Override void verifyLiteralIndexAndIterate(float val){
        Assertions.assertEquals(val,curr.val);
        curr=curr.next;
        ++index;
      }
      private FloatDblLnkNode getReverseNode(){
        FloatDblLnkNode curr;
        return (curr=this.curr)==null?seqMonitor.parents.length==0?seqMonitor.seq.tail:seqMonitor.parents[seqMonitor.parents.length-1].tail:curr.prev;
      }
      @Override void reverseAndVerifyIndex(FloatInputTestArgType inputArgType,int val){
        FloatDblLnkNode curr;
        inputArgType.verifyVal(val,(curr=getReverseNode()).val);
        this.curr=curr;
        --index;
      }
      @Override void verifyIndexAndIterate(FloatInputTestArgType inputArgType,int val){ 
        inputArgType.verifyVal(val,curr.val);
        curr=curr.next;
        ++index;
      }
      private FloatDblLnkNode getNode(int i){
        if(i<0){
          if(this.curr==null){
            return FloatDblLnkNode.iterateDescending(seqMonitor.parents.length==0?seqMonitor.seq.tail:seqMonitor.parents[seqMonitor.parents.length-1].tail,(-i)-1);
          }
          return FloatDblLnkNode.uncheckedIterateDescending(this.curr,-i);
        }
        return FloatDblLnkNode.iterateAscending(curr,i);
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
        for(int i=0,rootPostAlloc=seqMonitor.getRootPostAlloc(),v=Integer.MAX_VALUE-(rootPostAlloc-1);i<rootPostAlloc;++i,++v){
          verifyIndexAndIterate(FloatInputTestArgType.ARRAY_TYPE,v);
        }
        return this;
      }
      @Override SequenceVerificationItr verifyParentPostAlloc(){
        for(int i=0,rootPostAlloc=seqMonitor.getRootPostAlloc(),v=Integer.MAX_VALUE-(rootPostAlloc+seqMonitor.parentPostAlloc-1);i<seqMonitor.parentPostAlloc;++i,++v){
          verifyIndexAndIterate(FloatInputTestArgType.ARRAY_TYPE,v);
        }
        return this;
      }
    }
    class UncheckedSubAscendingItrMonitor extends UncheckedAscendingItrMonitor{
      UncheckedSubAscendingItrMonitor(){
        super();
      }
      FloatDblLnkNode getNewCurr(){
        if(expectedCurr!=null && expectedCurr!=seq.tail){
          return expectedCurr.next;
        }
        return null;
      }
      void verifyIteratorState(){
        Assertions.assertSame(FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedSubList.AscendingItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedSubList.AscendingItr.parent(itr),seq);
      }
    }
    class UncheckedAscendingItrMonitor extends AbstractFloatSeqMonitor.AbstractItrMonitor{
      FloatDblLnkNode expectedCurr;
      UncheckedAscendingItrMonitor(){
        this(ItrType.Itr,seq.iterator(),seq.head);
      }
      private UncheckedAscendingItrMonitor(ItrType itrType,OmniIterator.OfFloat itr,FloatDblLnkNode expectedCurr){
        super(itrType,itr,expectedSeqModCount);
        this.expectedCurr=expectedCurr;
      }
      void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
        if(functionCallType==FunctionCallType.Boxed){
          itr.forEachRemaining((Consumer)action);
        }else
        {
          itr.forEachRemaining((FloatConsumer)action);
        }
        this.expectedCurr=null;
      }
      SeqMonitor getSeqMonitor(){
        return SeqMonitor.this;
      }
      FloatDblLnkNode getNewCurr(){
        return expectedCurr!=null?expectedCurr.next:null;
      }
      void verifyNext(int expectedVal,FloatOutputTestArgType outputType){
        var newCurr=getNewCurr();
        outputType.verifyItrNext(itr,expectedVal);
        expectedCurr=newCurr;
      }
      void iterateForward(){
        var newCurr=getNewCurr();
        itr.nextFloat();
        expectedCurr=newCurr;
      }
      void remove(){
        itr.remove();
        verifyRemoval();
      }
      void verifyIteratorState(){
        Assertions.assertSame(FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedList.AscendingItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedList.AscendingItr.parent(itr),seq);
      }
    }
    class UncheckedDescendingItrMonitor extends UncheckedAscendingItrMonitor{
      UncheckedDescendingItrMonitor(){
        super(ItrType.DescendingItr,((OmniDeque.OfFloat)seq).descendingIterator(),seq.tail);
      }
      FloatDblLnkNode getNewCurr(){
        return expectedCurr!=null?expectedCurr.prev:null;
      }
      void verifyIteratorState(){
        Assertions.assertSame(FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedList.DescendingItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedList.DescendingItr.parent(itr),seq);
      }
    }
    class CheckedDescendingItrMonitor extends UncheckedDescendingItrMonitor{
      FloatDblLnkNode expectedLastRet;
      int expectedCurrIndex;
      CheckedDescendingItrMonitor(){
        super();
        this.expectedCurrIndex=expectedSeqSize;
      }
      void verifyNext(int expectedVal,FloatOutputTestArgType outputType){
        var newCurr=getNewCurr();
        outputType.verifyItrNext(itr,expectedVal);
        expectedLastRet=expectedCurr;
        expectedCurr=newCurr;
        --expectedCurrIndex;
      }
      void iterateForward(){
        var newCurr=getNewCurr();
        itr.nextFloat();
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
        Assertions.assertEquals(FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.DescendingItr.modCount(itr),expectedItrModCount);
        Assertions.assertEquals(FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.DescendingItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertSame(FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.DescendingItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.DescendingItr.lastRet(itr),expectedLastRet);
        Assertions.assertSame(FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.DescendingItr.parent(itr),seq);
      }
      void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
        if(functionCallType==FunctionCallType.Boxed){
          itr.forEachRemaining((Consumer)action);
        }else
        {
          itr.forEachRemaining((FloatConsumer)action);
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
        FloatDblLnkNode newLastRet=seq.tail;
        if(functionCallType==FunctionCallType.Boxed){
          itr.forEachRemaining((Consumer)action);
        }else
        {
          itr.forEachRemaining((FloatConsumer)action);
        }
        if(expectedCurrIndex<parentSize){
          this.expectedLastRet=newLastRet;
          this.expectedCurrIndex=parentSize;
          this.expectedCurr=newLastRet.next;
        }
      }
      void verifyIteratorState(){
        Assertions.assertEquals(FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedSubList.BidirectionalItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertSame(FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedSubList.BidirectionalItr.parent(itr),seq);
        Assertions.assertSame(FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedSubList.BidirectionalItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedSubList.BidirectionalItr.lastRet(itr),expectedLastRet);
      }
    }
    class UncheckedBidirectionalItrMonitor extends AbstractFloatSeqMonitor.AbstractItrMonitor{
      FloatDblLnkNode expectedCurr;
      int expectedCurrIndex;
      FloatDblLnkNode expectedLastRet;
      UncheckedBidirectionalItrMonitor(){
        super(ItrType.ListItr,seq.listIterator(),expectedSeqModCount);
        this.expectedCurr=seq.head;
        this.expectedCurrIndex=0;
      }
      UncheckedBidirectionalItrMonitor(ItrType itrType,OmniIterator.OfFloat itr,int currIndex){
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
            this.expectedCurr=FloatDblLnkNode.uncheckedIterateDescending(seq.tail,parentSize-1);
          }
        }else{
          this.expectedCurr=FloatDblLnkNode.iterateAscending(seq.head,currIndex);
        }
      }
      UncheckedBidirectionalItrMonitor(int index){
        this(ItrType.ListItr,seq.listIterator(index),index);
      }
      FloatDblLnkNode getNewNextNode(){
        return expectedCurr==null?null:expectedCurr.next;
      }
      FloatDblLnkNode getNewPrevNode(){
        return expectedCurr==null?seq.tail:expectedCurr.prev;
      }
      void iterateReverse(){
        FloatDblLnkNode newLastRet=getNewPrevNode();
        ((OmniListIterator.OfFloat)itr).previousFloat();
        this.expectedLastRet=newLastRet;
        this.expectedCurr=newLastRet;
        --this.expectedCurrIndex;
      }
      void verifyPrevious(int expectedVal,FloatOutputTestArgType outputType){
        FloatDblLnkNode newLastRet=getNewPrevNode();
        outputType.verifyItrPrevious(((OmniListIterator.OfFloat)itr),expectedVal);
         this.expectedLastRet=newLastRet;
        this.expectedCurr=newLastRet;
        --this.expectedCurrIndex;
      }
      void add(int v,FloatInputTestArgType inputArgType){
        inputArgType.callListItrAdd(((OmniListIterator.OfFloat)itr),v);
        ++this.expectedCurrIndex;
        this.expectedLastRet=null;
        verifyAddition();
      }
      void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
        if(functionCallType==FunctionCallType.Boxed){
          itr.forEachRemaining((Consumer)action);
        }else
        {
          itr.forEachRemaining((FloatConsumer)action);
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
      void verifyNext(int expectedVal,FloatOutputTestArgType outputType){
        var newCurr=getNewNextNode();
        outputType.verifyItrNext(itr,expectedVal);
        this.expectedLastRet=expectedCurr;
        this.expectedCurr=newCurr;
        ++this.expectedCurrIndex;
      }
      void iterateForward(){
        var newCurr=getNewNextNode();
        itr.nextFloat();
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
        Assertions.assertEquals(FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedList.BidirectionalItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertSame(FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedList.BidirectionalItr.parent(itr),seq);
        Assertions.assertSame(FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedList.BidirectionalItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedList.BidirectionalItr.lastRet(itr),expectedLastRet);
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
        Assertions.assertEquals(FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.BidirectionalItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertEquals(FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.BidirectionalItr.modCount(itr),expectedItrModCount);
        Assertions.assertSame(FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.BidirectionalItr.parent(itr),seq);
        Assertions.assertSame(FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.BidirectionalItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.BidirectionalItr.lastRet(itr),expectedLastRet);
      }
    }
    class CheckedSubAscendingItrMonitor extends CheckedBidirectionalItrMonitor{
      CheckedSubAscendingItrMonitor(){
        super();
      }
      CheckedSubAscendingItrMonitor(int index){
        super(index);
      }
      FloatDblLnkNode getNewNextNode(){
        return expectedCurr!=null &&expectedCurrIndex<expectedSeqSize?expectedCurr.next:null;
      }
      void verifyIteratorState(){
        Assertions.assertEquals(FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.AscendingItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertEquals(FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.AscendingItr.modCount(itr),expectedItrModCount);
        Assertions.assertSame(FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.AscendingItr.parent(itr),seq);
        Assertions.assertSame(FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.AscendingItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.AscendingItr.lastRet(itr),expectedLastRet);
      }
    }
    class CheckedBidirectionalItrMonitor extends CheckedAscendingItrMonitor{
      CheckedBidirectionalItrMonitor(int index){
        super(ItrType.ListItr,seq.listIterator(index),index);
      }
      CheckedBidirectionalItrMonitor(){
        super(ItrType.ListItr,seq.listIterator(),0);
      }
      void add(int v,FloatInputTestArgType inputArgType){
        super.add(v,inputArgType);
        ++expectedItrModCount;
      }
      FloatDblLnkNode getNewPrevNode(){
        return expectedCurrIndex!=0?expectedCurr==null?seq.tail:expectedCurr.prev:null;
      }
      void verifyIteratorState(){
        Assertions.assertEquals(FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.BidirectionalItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertEquals(FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.BidirectionalItr.modCount(itr),expectedItrModCount);
        Assertions.assertSame(FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.BidirectionalItr.parent(itr),seq);
        Assertions.assertSame(FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.BidirectionalItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.BidirectionalItr.lastRet(itr),expectedLastRet);
      }
    }
    class CheckedAscendingItrMonitor extends UncheckedBidirectionalItrMonitor{
      CheckedAscendingItrMonitor(){
        super(ItrType.Itr,seq.iterator(),0);
      }
      CheckedAscendingItrMonitor(ItrType itrType,OmniIterator.OfFloat itr,int index){
        super(itrType,itr,index);
      }
      void verifyIteratorState(){
        Assertions.assertEquals(FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.AscendingItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertEquals(FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.AscendingItr.modCount(itr),expectedItrModCount);
        Assertions.assertSame(FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.AscendingItr.parent(itr),seq);
        Assertions.assertSame(FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.AscendingItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.AscendingItr.lastRet(itr),expectedLastRet);
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
          itr.forEachRemaining((FloatConsumer)action);
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
