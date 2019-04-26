package omni.impl.seq;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import omni.impl.ByteInputTestArgType;
import omni.impl.ByteOutputTestArgType;
import org.junit.jupiter.params.provider.Arguments;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import omni.util.OmniArray;
import omni.impl.FunctionCallType;
import omni.impl.QueryCastType;
import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import omni.impl.seq.AbstractByteSeqMonitor.CheckedType;
import omni.impl.seq.AbstractByteSeqMonitor.PreModScenario;
import omni.impl.seq.AbstractByteSeqMonitor.SequenceLocation;
import omni.impl.seq.AbstractByteSeqMonitor.SequenceContentsScenario;
import omni.impl.seq.AbstractByteSeqMonitor.ListItrSetScenario;
import omni.impl.seq.AbstractByteSeqMonitor.ItrType;
import omni.impl.seq.AbstractByteSeqMonitor.IterationScenario;
import omni.impl.seq.AbstractByteSeqMonitor.ItrRemoveScenario;
import omni.impl.seq.AbstractByteSeqMonitor.MonitoredFunctionGen;
import omni.impl.seq.AbstractByteSeqMonitor.MonitoredComparatorGen;
import omni.impl.seq.AbstractByteSeqMonitor.MonitoredRemoveIfPredicateGen;
import omni.impl.seq.AbstractByteSeqMonitor.QueryTester;
import java.nio.file.Files;
import org.junit.jupiter.api.Tag;
import omni.impl.seq.AbstractByteSeqMonitor.SequenceVerificationItr;
import omni.api.OmniCollection;
import omni.api.OmniList;
import java.util.ArrayList;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import omni.function.ByteConsumer;
import omni.function.BytePredicate;
@SuppressWarnings({"rawtypes","unchecked"})
@Execution(ExecutionMode.CONCURRENT)
@Tag("ArrSeqTest")
public class ByteArrSeqTest{
  static Stream<Arguments> getremoveIf_PredicateArgs(){
    return ArgBuilder.buildSeqArgs((streamBuilder,nestedType,checkedType,preModScenario)->{
      for(var monitoredRemoveIfPredicateGen:MonitoredRemoveIfPredicateGen.values()){
        if(monitoredRemoveIfPredicateGen.expectedException==null || (checkedType.checked && (!nestedType.rootType || monitoredRemoveIfPredicateGen.appliesToRoot))){
          for(var functionCallType:FunctionCallType.values()){
            for(int seqSize=0;seqSize<=100;seqSize+=10){
              double[] thresholdArr;
              long randSeedBound;
              if(seqSize==0 || !monitoredRemoveIfPredicateGen.isRandomized){
                thresholdArr=new double[]{0.5};
                randSeedBound=0;
              }else{
                thresholdArr=new double[]{0.01,0.05,0.10,0.25,0.50,0.75,0.90,0.95,0.99};
                randSeedBound=100;
              }
              for(long randSeed=0;randSeed<=randSeedBound;++randSeed){
                for(double threshold:thresholdArr){
                  streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredRemoveIfPredicateGen,threshold,randSeed,functionCallType,seqSize));
                }
              }
            }
          }
        }
      }
   });
  }
  @org.junit.jupiter.api.Test
  public void testremoveIf_Predicate(){
    getremoveIf_PredicateArgs().parallel().map(Arguments::get).forEach(args->{
      testremoveIf_PredicateHelper((SeqMonitor)args[0],(PreModScenario)args[1],(MonitoredRemoveIfPredicateGen)args[2],(double)args[3],(long)args[4],(FunctionCallType)args[5],(int)args[6]
      );
    });
  }
  private static void testremoveIf_PredicateHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredRemoveIfPredicateGen monitoredRemoveIfPredicateGen,double threshold,long randSeed,final FunctionCallType functionCallType,int seqSize
  ){
    for(int i=0;i<seqSize;++i){
      seqMonitor.add(i);
    }
    final var clone=(OmniCollection.OfByte)seqMonitor.seq.clone();
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
        seqMonitor.verifyPreAlloc().skip(seqMonitor.expectedSeqSize).verifyPostAlloc();
        return;
      }else{
        Assertions.assertThrows(monitoredRemoveIfPredicateGen.expectedException,()->seqMonitor.verifyRemoveIf(monitoredRemoveIfPredicate,functionCallType,numExpectedRemoved,clone));
      }
    }else{
      Assertions.assertThrows(preModScenario.expectedException,()->seqMonitor.verifyRemoveIf(monitoredRemoveIfPredicate,functionCallType,numExpectedRemoved,clone));
    }
    var verifyItr=seqMonitor.verifyPreAlloc();
    if(seqMonitor.nestedType.forwardIteration){
      var cloneItr=clone.iterator();
      while(cloneItr.hasNext()){
        verifyItr.verifyLiteralIndexAndIterate(cloneItr.nextByte());
      }
    }else{
      var arr=clone.toByteArray();
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
  public void testhashCode_void(){
    gettoStringAndhashCode_voidArgs().parallel().map(Arguments::get).forEach(args->{
      testhashCode_voidHelper((SeqMonitor)args[0],(PreModScenario)args[1],(SequenceContentsScenario)args[2]
      );
    });
  }
  private static void testhashCode_voidHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,SequenceContentsScenario seqContentsScenario
  ){
    int numToAdd=seqContentsScenario.nonEmpty?100:0;
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
  }
  static Stream<Arguments> getListset_int_valArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var nestedType:NestedType.values()){
      if(nestedType.forwardIteration){
        for(var checkedType:CheckedType.values()){
          for(var preModScenario:PreModScenario.values()){
            if(preModScenario.expectedException==null || (checkedType.checked && preModScenario!=PreModScenario.ModSeq && !nestedType.rootType)){
              for(var seqContentsScenario:SequenceContentsScenario.values()){
                for(var seqLocation:SequenceLocation.values()){
                  if((seqLocation==SequenceLocation.BEGINNING && seqContentsScenario.nonEmpty) || (checkedType.checked && seqLocation.expectedException!=null)){
                    builder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario,seqContentsScenario,seqLocation,FunctionCallType.Unboxed));
                    builder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario,seqContentsScenario,seqLocation,FunctionCallType.Boxed));
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
        testListset_int_valHelper((SeqMonitor)args[0],(PreModScenario)args[1],(SequenceContentsScenario)args[2],(SequenceLocation)args[3],(FunctionCallType)args[4]);
    });
  }
  private static void testListset_int_valHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,SequenceContentsScenario seqContentsScenario,SequenceLocation seqLocation,FunctionCallType functionCallType){
    int numToAdd=seqContentsScenario.nonEmpty?100:0;
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
  static Stream<Arguments> getListget_intArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var nestedType:NestedType.values()){
      if(nestedType.forwardIteration){
        for(var checkedType:CheckedType.values()){
          for(var preModScenario:PreModScenario.values()){
            if(preModScenario.expectedException==null || (checkedType.checked && preModScenario!=PreModScenario.ModSeq && !nestedType.rootType)){
              for(var seqContentsScenario:SequenceContentsScenario.values()){
                for(var seqLocation:SequenceLocation.values()){
                  if((seqLocation==SequenceLocation.BEGINNING && seqContentsScenario.nonEmpty) || (checkedType.checked && seqLocation.expectedException!=null)){
                    for(var outputArgType:ByteOutputTestArgType.values()){
                      builder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario,seqContentsScenario,seqLocation,outputArgType));
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
        testListget_intHelper((SeqMonitor)args[0],(PreModScenario)args[1],(SequenceContentsScenario)args[2],(SequenceLocation)args[3],(ByteOutputTestArgType)args[4]);
    });
  }
  private static void testListget_intHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,SequenceContentsScenario seqContentsScenario,SequenceLocation seqLocation,ByteOutputTestArgType outputArgType){
    int numToAdd=seqContentsScenario.nonEmpty?100:0;
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
    Byte[] paramArr=new Byte[arrSize];
    for(int i=seqSize,bound=seqSize+arrSize;i<bound;++i){
      paramArr[i-seqSize]=TypeConversionUtil.convertToByte(i);
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
          Assertions.assertEquals(TypeConversionUtil.convertToByte(i+seqSize),resultArr[i]);
        }
      }else{
        Assertions.assertSame(paramArr,resultArr);
      }
      var itr=seqMonitor.seq.iterator();
      for(int i=0;i<seqSize;++i){
        Assertions.assertEquals((Object)itr.nextByte(),resultArr[i]);
      }
    }else{
      Assertions.assertThrows(preModScenario.expectedException,()->seqMonitor.seq.toArray(paramArr));
    }
    seqMonitor.verifyStructuralIntegrity();
    seqMonitor.verifyPreAlloc().verifyAscending(seqSize).verifyPostAlloc(preModScenario);
  }
  static Stream<Arguments> gettoArray_IntFunctionArgs(){
    return ArgBuilder.buildSeqArgs((streamBuilder,nestedType,checkedType,preModScenario)->{
      for(var monitoredFunctionGen:MonitoredFunctionGen.values()){
        if((checkedType.checked || monitoredFunctionGen.expectedException==null)&&(!nestedType.rootType || monitoredFunctionGen.appliesToRoot) &&(nestedType.rootType || monitoredFunctionGen.appliesToSubList)){
          for(var seqContentsScenario:SequenceContentsScenario.values()){
            streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredFunctionGen,seqContentsScenario));
          }
        }
      }   
    });
  }
  @org.junit.jupiter.api.Test
  public void testtoArray_IntFunction(){
    gettoArray_IntFunctionArgs().parallel().map(Arguments::get).forEach(args->{
        testtoArray_IntFunctionHelper((SeqMonitor)args[0],(PreModScenario)args[1],(MonitoredFunctionGen)args[2],(SequenceContentsScenario)args[3]);
    });
  }
  private static void testtoArray_IntFunctionHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredFunctionGen monitoredFunctionGen,SequenceContentsScenario seqContentsScenario){
    int numToAdd=seqContentsScenario.nonEmpty?100:0;
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
          Assertions.assertEquals(resultArr[i],(Object)itr.nextByte());
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
        var subList=((OmniList.OfByte)seqMonitor.seq).subList(fromIndex,toIndex);
        if(seqMonitor.checkedType.checked){
          Assertions.assertEquals(seqMonitor.expectedRootModCount,FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.modCount(subList));
          if(seqMonitor.nestedType.rootType){
            Assertions.assertNull(FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.parent(subList));
          }else{
            Assertions.assertSame(seqMonitor.seq,FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.parent(subList));
          }
          Assertions.assertSame(seqMonitor.root,FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.root(subList));
          Assertions.assertEquals(toIndex-fromIndex,FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.size(subList));
          Assertions.assertEquals(seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+fromIndex,FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.rootOffset(subList));
        }else{
          if(seqMonitor.nestedType.rootType){
            Assertions.assertNull(FieldAndMethodAccessor.ByteArrSeq.UncheckedSubList.parent(subList));
          }else{
            Assertions.assertSame(seqMonitor.seq,FieldAndMethodAccessor.ByteArrSeq.UncheckedSubList.parent(subList));
          }
          Assertions.assertSame(seqMonitor.root,FieldAndMethodAccessor.ByteArrSeq.UncheckedSubList.root(subList));
          Assertions.assertEquals(toIndex-fromIndex,FieldAndMethodAccessor.ByteArrSeq.UncheckedSubList.size(subList));
          Assertions.assertEquals(seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+fromIndex,FieldAndMethodAccessor.ByteArrSeq.UncheckedSubList.rootOffset(subList));
        }
      }else{
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->((OmniList.OfByte)seqMonitor.seq).subList(fromIndex,toIndex));
      }
    }else{
      Assertions.assertThrows(preModScenario.expectedException,()->((OmniList.OfByte)seqMonitor.seq).subList(fromIndex,toIndex));
    }
    seqMonitor.verifyStructuralIntegrity();
    seqMonitor.verifyPreAlloc().verifyAscending(seqSize).verifyPostAlloc(preModScenario);
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
                  for(var seqContentsScenario:SequenceContentsScenario.values()){
                    builder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredFunctionGen,seqContentsScenario,FunctionCallType.Unboxed));
                    builder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredFunctionGen,seqContentsScenario,FunctionCallType.Boxed));
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
        testListreplaceAll_UnaryOperatorHelper((SeqMonitor)args[0],(PreModScenario)args[1],(MonitoredFunctionGen)args[2],(SequenceContentsScenario)args[3],(FunctionCallType)args[4]);
    });
  }
  private static void testListreplaceAll_UnaryOperatorHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredFunctionGen monitoredFunctionGen,SequenceContentsScenario seqContentsScenario,FunctionCallType functionCallType){
    int numToAdd=seqContentsScenario.nonEmpty?100:0;
    for(int i=0;i<numToAdd;++i){
      seqMonitor.add(i);
    }
    seqMonitor.illegalAdd(preModScenario);
    var monitoredUnaryOperator=monitoredFunctionGen.getMonitoredUnaryOperator(seqMonitor);
    int numExpectedIteratedValues;
    if(preModScenario.expectedException==null){
      if(monitoredFunctionGen.expectedException==null || !seqContentsScenario.nonEmpty){
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
    var arr=((ByteArrSeq)seqMonitor.root).arr;
    int i=seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc;
    if(numExpectedIteratedValues==numToAdd){
      if(monitoredFunctionGen.expectedException==null){
        for(var encounteredValue:monitoredUnaryOperator.encounteredValues){
          var expectedVal=(byte)(((byte)encounteredValue)+1);
          Assertions.assertEquals(expectedVal,arr[i++]);
        }
      }
    }else{
      for(var encounteredValue:monitoredUnaryOperator.encounteredValues){
        Assertions.assertEquals(encounteredValue,(Object)arr[i++]);
      }
    }
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
      Assertions.assertEquals(0,itrMonitor.expectedCursor);
    }else{
      Assertions.assertThrows(preModScenario.expectedException,()->seqMonitor.getListItrMonitor());
    }
    seqMonitor.verifyStructuralIntegrity();
    seqMonitor.verifyPreAlloc().verifyAscending(100).verifyPostAlloc(preModScenario);
  }
  static Stream<Arguments> getListlistIterator_intArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var nestedType:NestedType.values()){
      if(nestedType.forwardIteration){
        for(var checkedType:CheckedType.values()){
          for(var preModScenario:PreModScenario.values()){
            if(preModScenario!=PreModScenario.ModSeq && (preModScenario.expectedException==null || (checkedType.checked && !nestedType.rootType))){
              for(var seqLocation:SequenceLocation.values()){
                if(seqLocation.expectedException!=null && checkedType.checked)
                  builder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario,seqLocation));
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
      Assertions.assertEquals(itrIndex,itrMonitor.expectedCursor);
    }else{
      Assertions.assertThrows(expectedException,()->seqMonitor.getListItrMonitor(itrIndex));
    }
    seqMonitor.verifyStructuralIntegrity();
    seqMonitor.verifyPreAlloc().verifyAscending(100).verifyPostAlloc(preModScenario);
  }
  static Stream<Arguments> getConstructor_intArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var nestedType:NestedType.values()){
      if(nestedType!=NestedType.SUBLIST){
        for(var checkedType:CheckedType.values()){
          for(int initialCapacity=0;initialCapacity<=15;initialCapacity+=5){
            builder.accept(Arguments.of(nestedType,checkedType,initialCapacity));
          }
        }
      }
    }
    return builder.build();
  }
  @org.junit.jupiter.api.Test
  public void testConstructor_int(){
    getConstructor_intArgs().parallel().map(Arguments::get).forEach(args->{
        testConstructor_intHelper((NestedType)args[0],(CheckedType)args[1],(int)args[2]);
    });
  }
  private static void testConstructor_intHelper
  (NestedType nestedType,CheckedType checkedType,int initialCapacity){
    ByteArrSeq seq;
    if(checkedType.checked){
      Assertions.assertEquals(0,nestedType==NestedType.LIST?FieldAndMethodAccessor.ByteArrSeq.CheckedList.modCount(seq=new ByteArrSeq.CheckedList(initialCapacity)):FieldAndMethodAccessor.ByteArrSeq.CheckedStack.modCount(seq=new ByteArrSeq.CheckedStack(initialCapacity)));
    }else{
      seq=nestedType==NestedType.LIST?new ByteArrSeq.UncheckedList(initialCapacity):new ByteArrSeq.UncheckedStack(initialCapacity);
    }
    Assertions.assertEquals(0,seq.size);
    switch(initialCapacity){
      case 0:
        Assertions.assertNull(seq.arr);
        break;
      case OmniArray.DEFAULT_ARR_SEQ_CAP:
        Assertions.assertSame(OmniArray.OfByte.DEFAULT_ARR,seq.arr);
        break;
      default:
        Assertions.assertEquals(initialCapacity,seq.arr.length);
    }
  }
  static Stream<Arguments> gettoArray_voidArgs(){
    return ArgBuilder.buildSeqArgs((streamBuilder,nestedType,checkedType,preModScenario)->{
      for(var seqContentsScenario:SequenceContentsScenario.values()){
        for(var outputArgType:ByteOutputTestArgType.values()){
          streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario,seqContentsScenario,outputArgType));
        }
      }
    });
  }
  @org.junit.jupiter.api.Test
  public void testtoArray_void(){
    gettoArray_voidArgs().parallel().map(Arguments::get).forEach(args->{
        testtoArray_voidHelper((SeqMonitor)args[0],(PreModScenario)args[1],(SequenceContentsScenario)args[2],(ByteOutputTestArgType)args[3]);
    });
  }
  private static void testtoArray_voidHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,SequenceContentsScenario seqContentsScenario,ByteOutputTestArgType outputArgType){
    int numToAdd=seqContentsScenario.nonEmpty?100:0;
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
  static Stream<Arguments> getListremoveAt_intArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var nestedType:NestedType.values()){
      if(nestedType.forwardIteration){
        for(var checkedType:CheckedType.values()){
          for(var preModScenario:PreModScenario.values()){
            if(preModScenario!=PreModScenario.ModSeq && (preModScenario.expectedException==null || (!nestedType.rootType && checkedType.checked))){
              for(var seqLocation:SequenceLocation.values()){
                if(checkedType.checked || seqLocation.expectedException==null){
                  for(var seqContentsScenario:SequenceContentsScenario.values()){
                    if(seqContentsScenario.nonEmpty || (seqLocation==SequenceLocation.IOBHI)){
                      for(var outputArgType:ByteOutputTestArgType.values()){
                        builder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario,seqLocation,seqContentsScenario,outputArgType));
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
  public void testListremoveAt_int(){
    getListremoveAt_intArgs().parallel().map(Arguments::get).forEach(args->{
        testListremoveAt_intHelper((SeqMonitor)args[0],(PreModScenario)args[1],(SequenceLocation)args[2],(SequenceContentsScenario)args[3],(ByteOutputTestArgType)args[4]);
    });
  }
  private static void testListremoveAt_intHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,SequenceLocation seqLocation,SequenceContentsScenario seqContentsScenario,ByteOutputTestArgType outputArgType){
    int numToAdd=seqContentsScenario.nonEmpty?120:0;
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
          for(int i=0;i<numToAdd;++i){
            seqMonitor.removeAt(i,outputArgType,0);
            seqMonitor.verifyStructuralIntegrity();
          }
          expectedSize=0;
        }else{
          Assertions.assertThrows(expectedException,()->seqMonitor.removeAt(0,outputArgType,0));
          seqMonitor.verifyStructuralIntegrity();
        }
        break;
      case NEARBEGINNING:
        if(expectedException==null){
          for(int i=0,expectedLo=numToAdd/4,expectedHi=numToAdd/4;i<numToAdd;++i){
            if((i&3)==1)
            {
              seqMonitor.removeAt(--expectedLo,outputArgType,(numToAdd-i)/4);
            }
            else
            {
              seqMonitor.removeAt(expectedHi++,outputArgType,(numToAdd-i)/4);
            }
            seqMonitor.verifyStructuralIntegrity();
          }
          expectedSize=0;
        }else{
          Assertions.assertThrows(expectedException,()->seqMonitor.removeAt(numToAdd/4,outputArgType,numToAdd/4));
          seqMonitor.verifyStructuralIntegrity();
        }
        break;
      case MIDDLE:
        if(expectedException==null){
          for(int i=0,expectedLo=numToAdd/2,expectedHi=numToAdd/2;i<numToAdd;++i){
            if((i&1)==0){
              seqMonitor.removeAt(expectedHi++,outputArgType,(numToAdd-i)/2);
            }else{
              seqMonitor.removeAt(--expectedLo,outputArgType,(numToAdd-i)/2);
            }
            seqMonitor.verifyStructuralIntegrity();
          }
          expectedSize=0;
        }else{
          Assertions.assertThrows(expectedException,()->seqMonitor.removeAt(numToAdd/2,outputArgType,numToAdd/2));
          seqMonitor.verifyStructuralIntegrity();
        }
        break;
      case NEAREND:
        if(expectedException==null){
          for(int i=0,expectedLo=((numToAdd/4)*3)+2,expectedHi=(numToAdd/4)*3;i<numToAdd;++i){
            switch((i%4))
            {
              case 0:
                seqMonitor.removeAt(expectedHi++,outputArgType,((numToAdd-i)/4)*3);
                break;
              case 1:
                seqMonitor.removeAt(expectedLo-=5,outputArgType,((numToAdd-i)/4)*3);
                break;
              case 2:
                seqMonitor.removeAt(++expectedLo,outputArgType,((numToAdd-i)/4)*3);
                break;
              case 3:
                seqMonitor.removeAt(++expectedLo,outputArgType,((numToAdd-i)/4)*3);
                break;
            }
            seqMonitor.verifyStructuralIntegrity();
          }
          expectedSize=0;
        }else{
          Assertions.assertThrows(expectedException,()->seqMonitor.removeAt((numToAdd/4)*3,outputArgType,(numToAdd/4)*3));
          seqMonitor.verifyStructuralIntegrity();
        }
        break;
      case END:
        if(expectedException==null){
          for(int i=0;i<numToAdd;++i){
            seqMonitor.removeAt(numToAdd-i-1,outputArgType,numToAdd-i-1);
            seqMonitor.verifyStructuralIntegrity();
          }
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
  @org.junit.jupiter.api.Test
  public void testListstableDescendingSort_void(){
    getNonComparatorSortArgs().parallel().map(Arguments::get).forEach(args->{
        testListstableDescendingSort_voidHelper((SeqMonitor)args[0],(PreModScenario)args[1],(MonitoredComparatorGen)args[2],(SequenceContentsScenario)args[3]);
    });
  }
  private static void testListstableDescendingSort_voidHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredComparatorGen monitoredComparatorGen,SequenceContentsScenario seqContentsScenario){
    monitoredComparatorGen.initReverse(seqMonitor,seqContentsScenario,preModScenario);
    if(preModScenario.expectedException==null){
      if(monitoredComparatorGen.expectedException==null || !seqContentsScenario.nonEmpty){
        seqMonitor.stableDescendingSort();
      }else{
        Assertions.assertThrows(monitoredComparatorGen.expectedException,()->seqMonitor.stableDescendingSort());
      }
    }else{
      Assertions.assertThrows(preModScenario.expectedException,()->seqMonitor.stableDescendingSort());
    }
    monitoredComparatorGen.assertReverseSorted(seqMonitor,seqContentsScenario,preModScenario);
  }
  @org.junit.jupiter.api.Test
  public void testclone_void(){
    getBasicCollectionTestArgs().parallel().map(Arguments::get).forEach(args->{
        testclone_voidHelper((SeqMonitor)args[0],(PreModScenario)args[1],(SequenceContentsScenario)args[2]);
    });
  }
  private static void testclone_voidHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,SequenceContentsScenario seqContentsScenario){
    int numToAdd=seqContentsScenario.nonEmpty?100:0;
    for(int i=0;i<numToAdd;++i){
      seqMonitor.add(i);
    }
    seqMonitor.illegalAdd(preModScenario);
    if(preModScenario.expectedException==null){
      var clone=(OmniCollection.OfByte)seqMonitor.seq.clone();
      Assertions.assertNotSame(clone,seqMonitor.seq);
      switch(seqMonitor.nestedType){
        case STACK:
          if(seqMonitor.checkedType.checked){
            Assertions.assertTrue(clone instanceof ByteArrSeq.CheckedStack);
            Assertions.assertEquals(0,((ByteArrSeq.CheckedStack)clone).modCount);
          }else{
            Assertions.assertTrue(clone instanceof ByteArrSeq.UncheckedStack);
          }
          break;
        case LIST:
        case SUBLIST:
          if(seqMonitor.checkedType.checked){
            Assertions.assertTrue(clone instanceof ByteArrSeq.CheckedList);
            Assertions.assertEquals(0,((ByteArrSeq.CheckedList)clone).modCount);
          }else{
            Assertions.assertTrue(clone instanceof ByteArrSeq.UncheckedList);
          }
          break;
        default:
          throw new Error("Unknown nested type "+seqMonitor.nestedType);
      }
      var arrSeqClone=(ByteArrSeq)clone;
      var originalArr=((ByteArrSeq)seqMonitor.root).arr;
      var cloneArr=arrSeqClone.arr;
      Assertions.assertEquals(numToAdd,arrSeqClone.size);
      if(arrSeqClone.size==0){
        Assertions.assertSame(cloneArr,OmniArray.OfByte.DEFAULT_ARR);
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
  }
  @org.junit.jupiter.api.Test
  public void testsize_void(){
    getBasicCollectionTestArgs().parallel().map(Arguments::get).forEach(args->{
        testsize_voidHelper((SeqMonitor)args[0],(PreModScenario)args[1],(SequenceContentsScenario)args[2]);
    });
  }
  private static void testsize_voidHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,SequenceContentsScenario seqContentsScenario){
    int numToAdd=seqContentsScenario.nonEmpty?100:0;
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
        testisEmpty_voidHelper((SeqMonitor)args[0],(PreModScenario)args[1],(SequenceContentsScenario)args[2]);
    });
  }
  private static void testisEmpty_voidHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,SequenceContentsScenario seqContentsScenario){
    int numToAdd=seqContentsScenario.nonEmpty?100:0;
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
  public void testclear_void(){
    getBasicCollectionTestArgs().parallel().map(Arguments::get).forEach(args->{
        testclear_voidHelper((SeqMonitor)args[0],(PreModScenario)args[1],(SequenceContentsScenario)args[2]);
    });
  }
  private static void testclear_voidHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,SequenceContentsScenario seqContentsScenario){
    int numToAdd=seqContentsScenario.nonEmpty?100:0;
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
  public void testStackpeek_void(){
    getStackpollpeekAndpop_voidArgs().parallel().map(Arguments::get).forEach(args->{
        testStackpeek_voidHelper((SeqMonitor)args[0],(ByteOutputTestArgType)args[1]);
    });
  }
  private static void testStackpeek_voidHelper
  (SeqMonitor seqMonitor,ByteOutputTestArgType outputArgType){
    for(int i=0;i<100;){
      outputArgType.verifyPeek(seqMonitor.seq,i,i);
      seqMonitor.verifyStructuralIntegrity();
      seqMonitor.add(++i);
    }
  }
  @org.junit.jupiter.api.Test
  public void testStackpop_void(){
    getStackpollpeekAndpop_voidArgs().parallel().map(Arguments::get).forEach(args->{
        testStackpop_voidHelper((SeqMonitor)args[0],(ByteOutputTestArgType)args[1]);
    });
  }
  private static void testStackpop_voidHelper
  (SeqMonitor seqMonitor,ByteOutputTestArgType outputArgType){
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
  }
  @org.junit.jupiter.api.Test
  public void testStackpoll_void(){
    getStackpollpeekAndpop_voidArgs().parallel().map(Arguments::get).forEach(args->{
        testStackpoll_voidHelper((SeqMonitor)args[0],(ByteOutputTestArgType)args[1]);
    });
  }
  private static void testStackpoll_voidHelper
  (SeqMonitor seqMonitor,ByteOutputTestArgType outputArgType){
    for(int i=0;i<100;++i){
      seqMonitor.add(i);
    }
    for(int i=100;--i>=0;){
      seqMonitor.poll(i,outputArgType);
      seqMonitor.verifyStructuralIntegrity();
    }
    seqMonitor.poll(0,outputArgType);
    seqMonitor.verifyStructuralIntegrity();
  }
  @org.junit.jupiter.api.Test
  public void testListstableAscendingSort_void(){
    getNonComparatorSortArgs().parallel().map(Arguments::get).forEach(args->{
        testListstableAscendingSort_voidHelper((SeqMonitor)args[0],(PreModScenario)args[1],(MonitoredComparatorGen)args[2],(SequenceContentsScenario)args[3]);
    });
  }
  private static void testListstableAscendingSort_voidHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredComparatorGen monitoredComparatorGen,SequenceContentsScenario seqContentsScenario){
    monitoredComparatorGen.init(seqMonitor,seqContentsScenario,preModScenario);
    if(preModScenario.expectedException==null){
      if(monitoredComparatorGen.expectedException==null || !seqContentsScenario.nonEmpty){
        seqMonitor.stableAscendingSort();
      }else{
        Assertions.assertThrows(monitoredComparatorGen.expectedException,()->seqMonitor.stableAscendingSort());
      }
    }else{
      Assertions.assertThrows(preModScenario.expectedException,()->seqMonitor.stableAscendingSort());
    }
    monitoredComparatorGen.assertSorted(seqMonitor,seqContentsScenario,preModScenario);
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
                  for(var sequenceContentsScenario:SequenceContentsScenario.values()){
                    builder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredComparatorGen,sequenceContentsScenario));
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
        testListunstableSort_ComparatorHelper((SeqMonitor)args[0],(PreModScenario)args[1],(MonitoredComparatorGen)args[2],(SequenceContentsScenario)args[3]);
    });
  }
  private static void testListunstableSort_ComparatorHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredComparatorGen monitoredComparatorGen,SequenceContentsScenario seqContentsScenario){
    monitoredComparatorGen.init(seqMonitor,seqContentsScenario,preModScenario);
    var sorter=monitoredComparatorGen.getMonitoredComparator(seqMonitor);
    if(preModScenario.expectedException==null){
      if(monitoredComparatorGen.expectedException==null || !seqContentsScenario.nonEmpty){
        seqMonitor.unstableSort(sorter);
      }else{
        Assertions.assertThrows(monitoredComparatorGen.expectedException,()->seqMonitor.unstableSort(sorter));
      }
    }else{
      Assertions.assertThrows(preModScenario.expectedException,()->seqMonitor.unstableSort(sorter));
    }
    monitoredComparatorGen.assertSorted(seqMonitor,seqContentsScenario,preModScenario);
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
                  for(var sequenceContentsScenario:SequenceContentsScenario.values()){
                    for(var functionCallType:FunctionCallType.values()){
                      builder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredComparatorGen,sequenceContentsScenario,functionCallType));
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
        testListsort_ComparatorHelper((SeqMonitor)args[0],(PreModScenario)args[1],(MonitoredComparatorGen)args[2],(SequenceContentsScenario)args[3],(FunctionCallType)args[4]);
    });
  }
  private static void testListsort_ComparatorHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredComparatorGen monitoredComparatorGen,SequenceContentsScenario seqContentsScenario,FunctionCallType functionCallType){
    monitoredComparatorGen.init(seqMonitor,seqContentsScenario,preModScenario);
    var sorter=monitoredComparatorGen.getMonitoredComparator(seqMonitor);
    if(preModScenario.expectedException==null){
      if(monitoredComparatorGen.expectedException==null || !seqContentsScenario.nonEmpty){
        seqMonitor.sort(sorter,functionCallType);
      }else{
        Assertions.assertThrows(monitoredComparatorGen.expectedException,()->seqMonitor.sort(sorter,functionCallType));
      }
    }else{
      Assertions.assertThrows(preModScenario.expectedException,()->seqMonitor.sort(sorter,functionCallType));
    }
    monitoredComparatorGen.assertSorted(seqMonitor,seqContentsScenario,preModScenario);
  }
  @org.junit.jupiter.api.Test
  public void testremoveVal_val(){
    getQueryCollectionArguments().parallel().map(Arguments::get).forEach(args->{
        testremoveVal_valHelper((SeqMonitor)args[0],(QueryTester)args[1],(QueryCastType)args[2],(SequenceLocation)args[3],(SequenceContentsScenario)args[4],(PreModScenario)args[5]
        );
    });
  }
  private static void testremoveVal_valHelper
  (SeqMonitor seqMonitor,QueryTester argType,QueryCastType queryCastType,SequenceLocation seqLocation,SequenceContentsScenario seqContentsScenario,PreModScenario preModScenario 
  ){
    if(seqContentsScenario.nonEmpty){
      {
        switch(seqLocation){
          case BEGINNING:
            argType.initContainsBeginning(seqMonitor,100,seqMonitor.nestedType.forwardIteration);
            break;
          case NEARBEGINNING:
            argType.initContainsNearBeginning(seqMonitor,100,seqMonitor.nestedType.forwardIteration);
            break;
          case MIDDLE:
            argType.initContainsMiddle(seqMonitor,100,seqMonitor.nestedType.forwardIteration);
            break;
          case NEAREND:
            argType.initContainsNearEnd(seqMonitor,100,seqMonitor.nestedType.forwardIteration);
            break;
          case END:
            argType.initContainsEnd(seqMonitor,100,seqMonitor.nestedType.forwardIteration);
            break;
          case IOBHI:
            argType.initDoesNotContain(seqMonitor,100);
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
  public void testsearch_val(){
    getQueryStackArguments().parallel().map(Arguments::get).forEach(args->{
        testsearch_valHelper((SeqMonitor)args[0],(QueryTester)args[1],(QueryCastType)args[2],(SequenceLocation)args[3],(SequenceContentsScenario)args[4],(PreModScenario)args[5]
        );
    });
  }
  private static void testsearch_valHelper
  (SeqMonitor seqMonitor,QueryTester argType,QueryCastType queryCastType,SequenceLocation seqLocation,SequenceContentsScenario seqContentsScenario,PreModScenario preModScenario 
  ){
    int expectedIndex;
    if(seqContentsScenario.nonEmpty){
      {
        switch(seqLocation){
          case BEGINNING:
            expectedIndex=argType.initContainsBeginning(seqMonitor,100,seqMonitor.nestedType.forwardIteration);
            break;
          case NEARBEGINNING:
            expectedIndex=argType.initContainsNearBeginning(seqMonitor,100,seqMonitor.nestedType.forwardIteration);
            break;
          case MIDDLE:
            expectedIndex=argType.initContainsMiddle(seqMonitor,100,seqMonitor.nestedType.forwardIteration);
            break;
          case NEAREND:
            expectedIndex=argType.initContainsNearEnd(seqMonitor,100,seqMonitor.nestedType.forwardIteration);
            break;
          case END:
            expectedIndex=argType.initContainsEnd(seqMonitor,100,seqMonitor.nestedType.forwardIteration);
            break;
          case IOBHI:
            argType.initDoesNotContain(seqMonitor,100);
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
  public void testlastIndexOf_val(){
    getQueryListArguments().parallel().map(Arguments::get).forEach(args->{
        testlastIndexOf_valHelper((SeqMonitor)args[0],(QueryTester)args[1],(QueryCastType)args[2],(SequenceLocation)args[3],(SequenceContentsScenario)args[4],(PreModScenario)args[5]
        );
    });
  }
  private static void testlastIndexOf_valHelper
  (SeqMonitor seqMonitor,QueryTester argType,QueryCastType queryCastType,SequenceLocation seqLocation,SequenceContentsScenario seqContentsScenario,PreModScenario preModScenario 
  ){
    int expectedIndex;
    if(seqContentsScenario.nonEmpty){
      {
        switch(seqLocation){
          case BEGINNING:
            expectedIndex=argType.initContainsBeginning(seqMonitor,100,seqMonitor.nestedType.forwardIteration)-1;
            break;
          case NEARBEGINNING:
            expectedIndex=argType.initContainsNearBeginning(seqMonitor,100,seqMonitor.nestedType.forwardIteration)-1;
            break;
          case MIDDLE:
            expectedIndex=argType.initContainsMiddle(seqMonitor,100,seqMonitor.nestedType.forwardIteration)-1;
            break;
          case NEAREND:
            expectedIndex=argType.initContainsNearEnd(seqMonitor,100,seqMonitor.nestedType.forwardIteration)-1;
            break;
          case END:
            expectedIndex=argType.initContainsEnd(seqMonitor,100,seqMonitor.nestedType.forwardIteration)-1;
            break;
          case IOBHI:
            argType.initDoesNotContain(seqMonitor,100);
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
        case CharacterMAX_BYTE_PLUS1:
        case CharacterMAX_SHORT_PLUS1:
        case ShortMAX_BYTE_PLUS1:
        case ShortMIN_BYTE_MINUS1:
        case IntegerMAX_BYTE_PLUS1:
        case IntegerMIN_BYTE_MINUS1:
        case IntegerMAX_SHORT_PLUS1:
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
  public void testindexOf_val(){
    getQueryListArguments().parallel().map(Arguments::get).forEach(args->{
        testindexOf_valHelper((SeqMonitor)args[0],(QueryTester)args[1],(QueryCastType)args[2],(SequenceLocation)args[3],(SequenceContentsScenario)args[4],(PreModScenario)args[5]
        );
    });
  }
  private static void testindexOf_valHelper
  (SeqMonitor seqMonitor,QueryTester argType,QueryCastType queryCastType,SequenceLocation seqLocation,SequenceContentsScenario seqContentsScenario,PreModScenario preModScenario 
  ){
    int expectedIndex;
    if(seqContentsScenario.nonEmpty){
      {
        switch(seqLocation){
          case BEGINNING:
            expectedIndex=argType.initContainsBeginning(seqMonitor,100,seqMonitor.nestedType.forwardIteration)-1;
            break;
          case NEARBEGINNING:
            expectedIndex=argType.initContainsNearBeginning(seqMonitor,100,seqMonitor.nestedType.forwardIteration)-1;
            break;
          case MIDDLE:
            expectedIndex=argType.initContainsMiddle(seqMonitor,100,seqMonitor.nestedType.forwardIteration)-1;
            break;
          case NEAREND:
            expectedIndex=argType.initContainsNearEnd(seqMonitor,100,seqMonitor.nestedType.forwardIteration)-1;
            break;
          case END:
            expectedIndex=argType.initContainsEnd(seqMonitor,100,seqMonitor.nestedType.forwardIteration)-1;
            break;
          case IOBHI:
            argType.initDoesNotContain(seqMonitor,100);
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
        case CharacterMAX_BYTE_PLUS1:
        case CharacterMAX_SHORT_PLUS1:
        case ShortMAX_BYTE_PLUS1:
        case ShortMIN_BYTE_MINUS1:
        case IntegerMAX_BYTE_PLUS1:
        case IntegerMIN_BYTE_MINUS1:
        case IntegerMAX_SHORT_PLUS1:
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
  public void testcontains_val(){
    getQueryCollectionArguments().parallel().map(Arguments::get).forEach(args->{
        testcontains_valHelper((SeqMonitor)args[0],(QueryTester)args[1],(QueryCastType)args[2],(SequenceLocation)args[3],(SequenceContentsScenario)args[4],(PreModScenario)args[5]
        );
    });
  }
  private static void testcontains_valHelper
  (SeqMonitor seqMonitor,QueryTester argType,QueryCastType queryCastType,SequenceLocation seqLocation,SequenceContentsScenario seqContentsScenario,PreModScenario preModScenario 
  ){
    if(seqContentsScenario.nonEmpty){
      {
        switch(seqLocation){
          case BEGINNING:
            argType.initContainsBeginning(seqMonitor,100,true);
            break;
          case NEARBEGINNING:
            argType.initContainsNearBeginning(seqMonitor,100,true);
            break;
          case MIDDLE:
            argType.initContainsMiddle(seqMonitor,100,true);
            break;
          case NEAREND:
            argType.initContainsNearEnd(seqMonitor,100,true);
            break;
          case END:
            argType.initContainsEnd(seqMonitor,100,true);
            break;
          case IOBHI:
            argType.initDoesNotContain(seqMonitor,100);
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
        case CharacterMAX_BYTE_PLUS1:
        case CharacterMAX_SHORT_PLUS1:
        case ShortMAX_BYTE_PLUS1:
        case ShortMIN_BYTE_MINUS1:
        case IntegerMAX_BYTE_PLUS1:
        case IntegerMIN_BYTE_MINUS1:
        case IntegerMAX_SHORT_PLUS1:
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
  static Stream<Arguments> getStackpush_valArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var checkedType:CheckedType.values()){
      for(var inputArgType:ByteInputTestArgType.values()){
        for(int initialCapacity=0;initialCapacity<=15;initialCapacity+=5){
          builder.accept(Arguments.of(new SeqMonitor(NestedType.STACK,checkedType,initialCapacity),inputArgType));
        }
      }
    }
    return builder.build();
  }
  @org.junit.jupiter.api.Test
  public void testStackpush_val(){
    getStackpush_valArgs().parallel().map(Arguments::get).forEach(args->{
        testStackpush_valHelper((SeqMonitor)args[0],(ByteInputTestArgType)args[1]);
    });
  }
  private static void testStackpush_valHelper
  (SeqMonitor seqMonitor,ByteInputTestArgType inputArgType){
    for(int i=0;i<100;++i){
      seqMonitor.push(i,inputArgType);
      seqMonitor.verifyStructuralIntegrity();
    }
    seqMonitor.verifyPreAlloc().verifyAscending(inputArgType,100);
  }
  static Stream<Arguments> getadd_valArgs(){
    return ArgBuilder.buildSeqArgs((streamBuilder,nestedType,checkedType,preModScenario)->{
      for(var seqContentsScenario:SequenceContentsScenario.values()){
        for(var inputArgType:ByteInputTestArgType.values()){
          for(int initialCapacity=0;initialCapacity<=15;initialCapacity+=5){
            switch(nestedType){
              case LIST:
              case STACK:
                streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType,initialCapacity),inputArgType,preModScenario,seqContentsScenario));
                break;
              case SUBLIST:
                for(int rootPreAlloc=0;rootPreAlloc<=5;rootPreAlloc+=5){
                  for(int parentPreAlloc=0;parentPreAlloc<=5;parentPreAlloc+=5){
                    for(int parentPostAlloc=0;parentPostAlloc<=5;parentPostAlloc+=5){
                      for(int rootPostAlloc=0;rootPostAlloc<=5;rootPostAlloc+=5){
                        streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType,initialCapacity,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),inputArgType,preModScenario,seqContentsScenario));
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
    });
  }
  @org.junit.jupiter.api.Test
  public void testadd_val(){
    getadd_valArgs().parallel().map(Arguments::get).forEach(args->{
        testadd_valHelper((SeqMonitor)args[0],(ByteInputTestArgType)args[1],(PreModScenario)args[2],(SequenceContentsScenario)args[3]);
    });
  }
  private static void testadd_valHelper
  (SeqMonitor seqMonitor,ByteInputTestArgType inputArgType,PreModScenario preModScenario,SequenceContentsScenario sequenceContentsScenario){
    int numToAdd=sequenceContentsScenario.nonEmpty?100:0;
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
  static Stream<Arguments> getListput_int_valArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var nestedType:NestedType.values()){
      if(nestedType.forwardIteration){
        for(var checkedType:CheckedType.values()){
          for(var seqLocation:SequenceLocation.values()){
            if((checkedType.checked || seqLocation.expectedException==null) && seqLocation.validForEmpty){
              for(var preModScenario:PreModScenario.values()){
                if(preModScenario!=PreModScenario.ModSeq && (preModScenario.expectedException==null || checkedType.checked) && (!nestedType.rootType || preModScenario==PreModScenario.NoMod)){
                  for(var seqContentsScenario:SequenceContentsScenario.values()){
                    if(seqContentsScenario.nonEmpty || seqLocation.expectedException!=null){
                      for(var inputArgType:ByteInputTestArgType.values()){
                        builder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),inputArgType,seqLocation,preModScenario,seqContentsScenario));
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
        testListput_int_valHelper((SeqMonitor)args[0],(ByteInputTestArgType)args[1],(SequenceLocation)args[2],(PreModScenario)args[3],(SequenceContentsScenario)args[4]);
    });
  }
  private static void testListput_int_valHelper
  (SeqMonitor seqMonitor,ByteInputTestArgType inputArgType,SequenceLocation seqLocation,PreModScenario preModScenario,SequenceContentsScenario seqContentsScenario){
    int numToAdd=seqContentsScenario.nonEmpty?100:0;
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
  static Stream<Arguments> getListadd_int_valArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var nestedType:NestedType.values()){
      if(nestedType.forwardIteration){
        for(var checkedType:CheckedType.values()){
          for(var seqLocation:SequenceLocation.values()){
            if(checkedType.checked || seqLocation.expectedException==null){
              for(var preModScenario:PreModScenario.values()){
                if(preModScenario!=PreModScenario.ModSeq && (preModScenario.expectedException==null || checkedType.checked) && (!nestedType.rootType || preModScenario==PreModScenario.NoMod)){
                  for(var seqContentsScenario:SequenceContentsScenario.values()){
                    if(seqContentsScenario.nonEmpty || seqLocation.validForEmpty){
                      for(var inputArgType:ByteInputTestArgType.values()){
                        for(int initialCapacity=0;initialCapacity<=15;initialCapacity+=5){
                          switch(nestedType){
                            case LIST:
                              builder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType,initialCapacity),inputArgType,seqLocation,preModScenario,seqContentsScenario));
                              break;
                            case SUBLIST:
                              for(int rootPreAlloc=0;rootPreAlloc<=5;rootPreAlloc+=5){
                                for(int parentPreAlloc=0;parentPreAlloc<=5;parentPreAlloc+=5){
                                  for(int parentPostAlloc=0;parentPostAlloc<=5;parentPostAlloc+=5){
                                    for(int rootPostAlloc=0;rootPostAlloc<=5;rootPostAlloc+=5){
                                      builder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType,initialCapacity,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),inputArgType,seqLocation,preModScenario,seqContentsScenario));
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
    return builder.build();
  }
  @org.junit.jupiter.api.Test
  public void testListadd_int_val(){
    getListadd_int_valArgs().parallel().map(Arguments::get).forEach(args->{
        testListadd_int_valHelper((SeqMonitor)args[0],(ByteInputTestArgType)args[1],(SequenceLocation)args[2],(PreModScenario)args[3],(SequenceContentsScenario)args[4]);
    });
  }
  private static void testListadd_int_valHelper
  (SeqMonitor seqMonitor,ByteInputTestArgType inputArgType,SequenceLocation seqLocation,PreModScenario preModScenario,SequenceContentsScenario seqContentsScenario){
    int numToAdd=seqContentsScenario.nonEmpty?100:0;
    for(int i=0;i<numToAdd;++i){
      seqMonitor.add(i);
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
          for(int i=0;i<100;++i){
            seqMonitor.add(0,i,inputArgType);
            seqMonitor.verifyStructuralIntegrity();
          }
          verifyItr=seqMonitor.verifyPreAlloc().verifyDescending(inputArgType,100).verifyAscending(numToAdd);
          break;
        case NEARBEGINNING:
          for(int i=0;i<100;++i){
            seqMonitor.add((i+numToAdd)/4,i,inputArgType);
            seqMonitor.verifyStructuralIntegrity();
          }
          verifyItr=seqMonitor.verifyPreAlloc().verifyAscending((numToAdd/4)).verifyNearBeginningInsertion(inputArgType,100).verifyAscending((numToAdd/4),numToAdd-((numToAdd/4)));
          break;
        case MIDDLE:
          for(int i=0;i<100;++i){
            seqMonitor.add((i+numToAdd)/2,i,inputArgType);
            seqMonitor.verifyStructuralIntegrity();
          }
          verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(numToAdd/2).verifyMidPointInsertion(inputArgType,100).verifyAscending(numToAdd/2,numToAdd-(numToAdd/2));
          break;
        case NEAREND:
          int insertionIndex=(numToAdd/4)*3;
          for(int i=0;i<100;){
            seqMonitor.add(insertionIndex,i,inputArgType);
            ++i;
            ++insertionIndex;
            if((i&3)==0)
            {
              --insertionIndex;
            }
            seqMonitor.verifyStructuralIntegrity();
          }
          verifyItr=seqMonitor.verifyPreAlloc().verifyAscending((numToAdd/4)*3).verifyNearEndInsertion(inputArgType,100).verifyAscending((numToAdd/4)*3,numToAdd-((numToAdd/4)*3));
          break;
        case END:
          for(int i=0;i<100;++i){
            seqMonitor.add(i+numToAdd,i,inputArgType);
            seqMonitor.verifyStructuralIntegrity();
          }
          verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyAscending(inputArgType,100);
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
           insertionIndex=numToAdd+1;
          break;
        case BEGINNING:
          insertionIndex=0;
          break;
        case NEARBEGINNING:
          insertionIndex=numToAdd/4;
          break;
        case MIDDLE:
          insertionIndex=numToAdd/2;
          break;
        case NEAREND:
          insertionIndex=(numToAdd/4)*3;
          break;
        case END:
          insertionIndex=numToAdd;
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
  static Stream<Arguments> getforEach_ConsumerArgs(){
    return ArgBuilder.buildSeqArgs((streamBuilder,nestedType,checkedType,preModScenario)->{
      for(var monitoredFunctionGen:MonitoredFunctionGen.values()){
        if((checkedType.checked || monitoredFunctionGen.expectedException==null)&&(!nestedType.rootType || monitoredFunctionGen.appliesToRoot) &&(nestedType.rootType || monitoredFunctionGen.appliesToSubList)){
          for(var seqContentsScenario:SequenceContentsScenario.values()){
            streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredFunctionGen,seqContentsScenario,FunctionCallType.Unboxed));
            streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredFunctionGen,seqContentsScenario,FunctionCallType.Boxed));
          }
        }
      }
    });
  }
  @org.junit.jupiter.api.Test
  public void testforEach_Consumer(){
    getforEach_ConsumerArgs().parallel().map(Arguments::get).forEach(args->{
        testforEach_ConsumerHelper((SeqMonitor)args[0],(PreModScenario)args[1],(MonitoredFunctionGen)args[2],(SequenceContentsScenario)args[3],(FunctionCallType)args[4]);
    });
  }
  private static void testforEach_ConsumerHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredFunctionGen monitoredFunctionGen,SequenceContentsScenario seqContentsScenario,FunctionCallType functionCallType){
    int numToAdd=seqContentsScenario.nonEmpty?100:0;
    for(int i=0;i<numToAdd;++i){
      seqMonitor.add(i);
    }
    seqMonitor.illegalAdd(preModScenario);
    var monitoredConsumer=monitoredFunctionGen.getMonitoredConsumer(seqMonitor);
    int numExpectedIteratedValues;
    if(preModScenario.expectedException==null){
      if(monitoredFunctionGen.expectedException==null || !seqContentsScenario.nonEmpty){
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
    var arr=((ByteArrSeq)seqMonitor.root).arr;
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
  static Stream<Arguments> getItrforEachRemaining_ConsumerArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var checkedType:CheckedType.values()){
      for(var nestedType:NestedType.values()){
        for(var preModScenario:PreModScenario.values()){
          if(checkedType.checked || preModScenario.expectedException==null){
            for(var monitoredFunctionGen:MonitoredFunctionGen.values()){
              if((monitoredFunctionGen.expectedException==null || checkedType.checked) && (!nestedType.rootType || (preModScenario.appliesToRootItr&&monitoredFunctionGen.appliesToRootItr))){
                for(var itrType:ItrType.values()){
                  if(itrType!=ItrType.DescendingItr && (itrType==ItrType.Itr || nestedType.forwardIteration)){
                    for(var seqContentsScenario:SequenceContentsScenario.values()){
                      builder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredFunctionGen,itrType,seqContentsScenario,FunctionCallType.Unboxed));
                      builder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredFunctionGen,itrType,seqContentsScenario,FunctionCallType.Boxed));
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
        testItrforEachRemaining_ConsumerHelper((SeqMonitor)args[0],(PreModScenario)args[1],(MonitoredFunctionGen)args[2],(ItrType)args[3],(SequenceContentsScenario)args[4],(FunctionCallType)args[5]);
    });
  }
  private static void testItrforEachRemaining_ConsumerHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredFunctionGen monitoredFunctionGen,ItrType itrType,SequenceContentsScenario seqContentsScenario,FunctionCallType functionCallType){
    int numToAdd=seqContentsScenario.nonEmpty?100:0;
    for(int i=0;i<numToAdd;++i){
      seqMonitor.add(i);
    }
    var itrMonitor=seqMonitor.getItrMonitor(itrType);
    seqMonitor.illegalAdd(preModScenario);
    var monitoredConsumer=monitoredFunctionGen.getMonitoredConsumer(itrMonitor);
    int numExpectedIteratedValues;
    if(preModScenario.expectedException==null || (!seqContentsScenario.nonEmpty && (preModScenario!=PreModScenario.ModSeq || !seqMonitor.nestedType.forwardIteration))){
      if(monitoredFunctionGen.expectedException==null || (!seqContentsScenario.nonEmpty && (preModScenario!=PreModScenario.ModSeq || !seqMonitor.nestedType.forwardIteration))){
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
        var arr=((ByteArrSeq)seqMonitor.root).arr;
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
  static Stream<Arguments> getListItrpreviousIndex_void_And_ListItrnextIndex_voidArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var checkedType:CheckedType.values()){
      for(var nestedType:NestedType.values()){
        if(nestedType!=NestedType.STACK){
          builder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType)));
        }
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
  static Stream<Arguments> getItrremove_voidArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var checkedType:CheckedType.values()){
      for(var removeScenario:ItrRemoveScenario.values()){
        if(checkedType.checked || removeScenario.expectedException==null){
          for(var sequenceContentsScenario:SequenceContentsScenario.values()){
            if(sequenceContentsScenario.nonEmpty || removeScenario.validWithEmptySeq){
              for(var preModScenario:PreModScenario.values()){
                if(checkedType.checked || preModScenario.expectedException==null){
                  for(var itrType:ItrType.values()){
                    if(itrType!=ItrType.DescendingItr && (itrType==ItrType.ListItr || removeScenario.validWithForwardItr)){
                      for(var nestedType:NestedType.values()){
                        if((itrType==ItrType.Itr || nestedType!=NestedType.STACK) && (!nestedType.rootType || preModScenario.appliesToRootItr)){
                          for(var sequenceLocation:SequenceLocation.values()){
                            if(sequenceLocation.expectedException==null && (sequenceLocation==SequenceLocation.BEGINNING || (sequenceContentsScenario.nonEmpty && nestedType!=NestedType.STACK))){
                              builder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),removeScenario,preModScenario,sequenceContentsScenario,itrType,sequenceLocation));
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
        testItrremove_voidHelper((SeqMonitor)args[0],(ItrRemoveScenario)args[1],(PreModScenario)args[2],(SequenceContentsScenario)args[3],(ItrType)args[4],(SequenceLocation)args[5]);
    });
  }
  private static void testItrremove_voidHelper
  (SeqMonitor seqMonitor,ItrRemoveScenario removeScenario,PreModScenario preModScenario,SequenceContentsScenario seqContentsScenario,ItrType itrType,SequenceLocation seqLocation){
    int numToAdd=seqContentsScenario.nonEmpty?100:0;
    for(int i=0;i<numToAdd;++i){
      seqMonitor.add(i);
    }
    var itrMonitor=seqMonitor.getItrMonitor(seqLocation,itrType);
    switch(removeScenario){
      case PostNext:
        if(seqLocation==SequenceLocation.END){
          itrMonitor.iterateReverse();
        }
        itrMonitor.iterateForward();
        break;
      case PostPrevious:
        if(seqLocation==SequenceLocation.BEGINNING){
           itrMonitor.iterateForward();
        }
        itrMonitor.iterateReverse();
        break;
      case PostAdd:
        itrMonitor.add(0);
        break;
      case PostRemove:
        if(seqLocation==SequenceLocation.END){
          itrMonitor.iterateReverse();
        }else{
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
          switch(seqLocation){
            case BEGINNING:
              verifyItr.verifyIllegalAdd().verifyAscending(numToAdd);
              break;
            case NEARBEGINNING:
              verifyItr.verifyAscending(numToAdd/4).verifyIllegalAdd().verifyAscending(numToAdd/4,numToAdd-(numToAdd/4));
              break;
            case MIDDLE:
              verifyItr.verifyAscending(numToAdd/2).verifyIllegalAdd().verifyAscending(numToAdd/2,numToAdd-(numToAdd/2));
              break;
            case NEAREND:
              verifyItr.verifyAscending((numToAdd/4)*3).verifyIllegalAdd().verifyAscending((numToAdd/4)*3,numToAdd-((numToAdd/4)*3));
              break;
            case END:
              verifyItr.verifyAscending(numToAdd).verifyIllegalAdd();
              break;
            default:
              throw new Error("Unknown sequence location "+seqLocation);
          }
          break;
        case PostRemove:
          switch(seqLocation){
            case BEGINNING:
              if(seqMonitor.nestedType.forwardIteration){
                verifyItr.verifyAscending(1,numToAdd-1);
              }else{
                verifyItr.verifyAscending(numToAdd-1);
              }
              break;
            case NEARBEGINNING:
              verifyItr.verifyAscending(numToAdd/4).verifyAscending((numToAdd/4)+1,numToAdd-(numToAdd/4)-1);
              break;
            case MIDDLE:
              verifyItr.verifyAscending(numToAdd/2).verifyAscending((numToAdd/2)+1,numToAdd-(numToAdd/2)-1);
              break;
            case NEAREND:
              verifyItr.verifyAscending((numToAdd/4)*3).verifyAscending(((numToAdd/4)*3)+1,numToAdd-((numToAdd/4)*3)-1);
              break;
            case END:
              verifyItr.verifyAscending(numToAdd-1);
              break;
            default:
              throw new Error("Unknown sequence location "+seqLocation);
          }
          break;
        default:
          throw new Error("unknown remove scenario "+removeScenario);
      }
    }
    verifyItr.verifyPostAlloc(preModScenario);
  }
  static Stream<Arguments> getListItrprevious_voidArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var checkedType:CheckedType.values()){
      for(var itrScenario:IterationScenario.values()){
        if(checkedType.checked || itrScenario==IterationScenario.NoMod){
          for(var seqContentsScenario:SequenceContentsScenario.values()){
            if(seqContentsScenario.nonEmpty || itrScenario.validWithEmptySeq){
              for(var outputType:ByteOutputTestArgType.values()){
                if(itrScenario.preModScenario.appliesToRootItr){
                  builder.accept(Arguments.of(new SeqMonitor(NestedType.LIST,checkedType),itrScenario,seqContentsScenario,outputType));
                }
                builder.accept(Arguments.of(new SeqMonitor(NestedType.SUBLIST,checkedType),itrScenario,seqContentsScenario,outputType));
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
        testListItrprevious_voidHelper((SeqMonitor)args[0],(IterationScenario)args[1],(SequenceContentsScenario)args[2],(ByteOutputTestArgType)args[3]);
    });
  }
  private static void testListItrprevious_voidHelper
  (SeqMonitor seqMonitor,IterationScenario itrScenario,SequenceContentsScenario seqContentsScenario,ByteOutputTestArgType outputType){
    int numToAdd=seqContentsScenario.nonEmpty?100:0;
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
  static Stream<Arguments> getItrnext_voidArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var checkedType:CheckedType.values()){
      for(var itrScenario:IterationScenario.values()){
        if(checkedType.checked || itrScenario==IterationScenario.NoMod){
          for(var seqContentsScenario:SequenceContentsScenario.values()){
            if(seqContentsScenario.nonEmpty || itrScenario.validWithEmptySeq){
              for(var itrType:ItrType.values()){
                if(itrType!=ItrType.DescendingItr){
                  for(var nestedType:NestedType.values()){
                    if((nestedType!=NestedType.STACK || itrType!=ItrType.ListItr) && (itrScenario.preModScenario.appliesToRootItr || !nestedType.rootType)){
                      for(var outputType:ByteOutputTestArgType.values()){
                        builder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),itrScenario,seqContentsScenario,itrType,outputType));
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
  public void testItrnext_void(){
    getItrnext_voidArgs().parallel().map(Arguments::get).forEach(args->{
        testItrnext_voidHelper((SeqMonitor)args[0],(IterationScenario)args[1],(SequenceContentsScenario)args[2],(ItrType)args[3],(ByteOutputTestArgType)args[4]);
    });
  }
  private static void testItrnext_voidHelper
  (SeqMonitor seqMonitor,IterationScenario itrScenario,SequenceContentsScenario seqContentsScenario,ItrType itrType,ByteOutputTestArgType outputType){
    int numToAdd=seqContentsScenario.nonEmpty?100:0;
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
  }
  static Stream<Arguments> getListItrset_valArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var checkedType:CheckedType.values()){
      for(var listItrSetScenario:ListItrSetScenario.values()){
        if(checkedType.checked || listItrSetScenario.expectedException==null){
          for(var inputArgType:ByteInputTestArgType.values()){
            if(listItrSetScenario.preModScenario.appliesToRootItr){
              builder.accept(Arguments.of(new SeqMonitor(NestedType.LIST,checkedType),listItrSetScenario,inputArgType));
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
        testListItrset_valHelper((SeqMonitor)args[0],(ListItrSetScenario)args[1],(ByteInputTestArgType)args[2]);
    });
  }
  private static void testListItrset_valHelper
  (SeqMonitor seqMonitor,ListItrSetScenario listItrSetScenario,ByteInputTestArgType inputArgType){
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
  static Stream<Arguments> getListItradd_valArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var checkedType:CheckedType.values()){
      for(var preModScenario:PreModScenario.values()){
        if(checkedType.checked || preModScenario.expectedException==null){
          for(var seqContentsScenario:SequenceContentsScenario.values()){
            for(var seqLocation:SequenceLocation.values()){
              if(seqLocation.expectedException==null){
                for(var inputArgType:ByteInputTestArgType.values()){
                  if(preModScenario.appliesToRootItr){
                    for(int initialCapacity=0;initialCapacity<=15;initialCapacity+=5){
                        builder.accept(Arguments.of(new SeqMonitor(NestedType.LIST,checkedType,initialCapacity),preModScenario,seqContentsScenario,seqLocation,inputArgType));
                    }
                  }
                  for(int rootPreAlloc=0;rootPreAlloc<=5;rootPreAlloc+=5){
                    for(int parentPreAlloc=0;parentPreAlloc<=5;parentPreAlloc+=5){
                      for(int parentPostAlloc=0;parentPostAlloc<=5;parentPostAlloc+=5){
                        for(int rootPostAlloc=0;rootPostAlloc<=5;rootPostAlloc+=5){
                          builder.accept(Arguments.of(new SeqMonitor(NestedType.SUBLIST,checkedType,OmniArray.DEFAULT_ARR_SEQ_CAP,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),preModScenario,seqContentsScenario,seqLocation,inputArgType));
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
  public void testListItradd_val(){
    getListItradd_valArgs().parallel().map(Arguments::get).forEach(args->{
        testListItradd_valHelper((SeqMonitor)args[0],(PreModScenario)args[1],(SequenceContentsScenario)args[2],(SequenceLocation)args[3],(ByteInputTestArgType)args[4]);
    });
  }
  private static void testListItradd_valHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,SequenceContentsScenario seqContentsScenario,SequenceLocation seqLocation,ByteInputTestArgType inputArgType){
    int numToAdd=seqContentsScenario.nonEmpty?100:0;
    for(int i=0;i<numToAdd;++i){
      seqMonitor.add(i);
    }
    var itrMonitor=seqMonitor.getListItrMonitor(seqLocation);
    seqMonitor.illegalAdd(preModScenario);
    SequenceVerificationItr verifyItr;
    if(preModScenario.expectedException==null){
      switch(seqLocation){
        case BEGINNING:
          for(int i=0;i<100;++i){
            itrMonitor.add(i,inputArgType);
            itrMonitor.verifyIteratorState();
            itrMonitor.iterateReverse();
            seqMonitor.verifyStructuralIntegrity();
          }
          break;
        case NEARBEGINNING:
          for(int i=0;i<100;){
            itrMonitor.add(i,inputArgType);
            itrMonitor.verifyIteratorState();
            ++i;
            if((i&3)!=0){
              itrMonitor.iterateReverse();
            }
            seqMonitor.verifyStructuralIntegrity();
          }
          break;
        case MIDDLE:
          for(int i=0;i<100;++i){
            itrMonitor.add(i,inputArgType);
            itrMonitor.verifyIteratorState();
            if((i&1)==0){
              itrMonitor.iterateReverse();
            }
            seqMonitor.verifyStructuralIntegrity();
          }
          break;
        case NEAREND:
          for(int i=0;i<100;){
            itrMonitor.add(i,inputArgType);
            itrMonitor.verifyIteratorState();
            ++i;
            if((i&3)==0){
              itrMonitor.iterateReverse();
            }
            seqMonitor.verifyStructuralIntegrity();
          }
          break;
        case END:
          for(int i=0;i<100;++i){
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
          verifyItr.verifyDescending(inputArgType,100).verifyAscending(numToAdd);
          break;
        case NEARBEGINNING:
          verifyItr.verifyAscending(numToAdd/4).verifyNearBeginningInsertion(inputArgType,100).verifyAscending(numToAdd/4,(numToAdd-(numToAdd/4)));
          break;
        case MIDDLE:
          verifyItr.verifyAscending(numToAdd/2).verifyMidPointInsertion(inputArgType,100).verifyAscending(numToAdd/2,numToAdd-(numToAdd/2));
          break;
        case NEAREND:
          verifyItr.verifyAscending((numToAdd/4)*3).verifyNearEndInsertion(inputArgType,100).verifyAscending((numToAdd/4)*3,(numToAdd-((numToAdd/4)*3)));
          break;
        case END:
          verifyItr.verifyAscending(numToAdd).verifyAscending(inputArgType,100);
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
  public void testtoString_void(){
    gettoStringAndhashCode_voidArgs().parallel().map(Arguments::get).forEach(args->{
      testtoString_voidHelper((SeqMonitor)args[0],(PreModScenario)args[1],(SequenceContentsScenario)args[2]
      );
    });
  }
  private static void testtoString_voidHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,SequenceContentsScenario seqContentsScenario
  ){
    int numToAdd=seqContentsScenario.nonEmpty?100:0;
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
  }
    private static final int MAX_TOSTRING_LENGTH=4;
  static Stream<Arguments> getMASSIVEtoString_voidArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    final int seqLength=(OmniArray.MAX_ARR_SIZE/(MAX_TOSTRING_LENGTH+2))+1;
    final int arrLength=seqLength+20;
    byte[] arr=new byte[arrLength];
    for(int i=0;i<arrLength;++i){
      arr[i]=TypeConversionUtil.convertTobyte(1);
    }
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        builder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType,seqLength,arr)));
      }
    }
    return builder.build();
  }
  @Tag("MASSIVEtoString")
  @org.junit.jupiter.params.ParameterizedTest
  @org.junit.jupiter.params.provider.MethodSource("getMASSIVEtoString_voidArgs")
  public void testMASSIVEtoString_void
  (SeqMonitor seqMonitor){
    seqMonitor.verifyMASSIVEString();
  }
  static Stream<Arguments> getreadAndwriteObjectArgs(){
    return ArgBuilder.buildSeqArgs((streamBuilder,nestedType,checkedType,preModScenario)->{
      for(var monitoredFunctionGen:MonitoredFunctionGen.values()){
        if((checkedType.checked || monitoredFunctionGen.expectedException==null)&&(!nestedType.rootType || monitoredFunctionGen.appliesToRoot) &&(nestedType.rootType || monitoredFunctionGen.appliesToSubList)){
          for(var seqContentsScenario:SequenceContentsScenario.values()){
            streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredFunctionGen,seqContentsScenario));
          }
        }
      }
    });
  }
  @org.junit.jupiter.api.Test
  public void testreadAndwriteObject(){
    getreadAndwriteObjectArgs().parallel().map(Arguments::get).forEach(args->{
        testreadAndwriteObjectHelper((SeqMonitor)args[0],(PreModScenario)args[1],(MonitoredFunctionGen)args[2],(SequenceContentsScenario)args[3]);
    });
  }
  private static void testreadAndwriteObjectHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredFunctionGen monitoredFunctionGen,SequenceContentsScenario seqContentsScenario)
  {
    int numToAdd=seqContentsScenario.nonEmpty?100:0;
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
    }else{
      Assertions.assertThrows(preModScenario.expectedException,()->{
        try(var moos=monitoredFunctionGen.getMonitoredObjectOutputStream(file,seqMonitor);){
          seqMonitor.writeObject(moos);
        }
      });
    }
    seqMonitor.verifyStructuralIntegrity();
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
  private static class SeqMonitor extends AbstractByteSeqMonitor{
    static final int DEFAULT_PRE_AND_POST_ALLOC=5;
    private static void initArray(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc,byte[] arr){
      for(int i=0,v=Integer.MIN_VALUE,bound=rootPreAlloc;i<bound;++i,++v){
        arr[i]=TypeConversionUtil.convertTobyte(v);
      }
      for(int i=rootPreAlloc,v=Integer.MIN_VALUE+rootPreAlloc,bound=i+parentPreAlloc;i<bound;++i,++v){
        arr[i]=TypeConversionUtil.convertTobyte(v);
      }
      for(int i=rootPreAlloc+parentPreAlloc,v=Integer.MAX_VALUE-rootPostAlloc-parentPostAlloc,bound=i+parentPostAlloc;i<bound;++i,++v){
        arr[i]=TypeConversionUtil.convertTobyte(v);
      }
      for(int i=rootPreAlloc+parentPreAlloc+parentPostAlloc,v=Integer.MAX_VALUE-rootPostAlloc,bound=i+rootPostAlloc;i<bound;++i,++v){
        arr[i]=TypeConversionUtil.convertTobyte(v);
      }
    }
    final NestedType nestedType;
    final int initialCapacity;
    final int rootPreAlloc;
    final int parentPreAlloc;
    final int parentPostAlloc;
    final int rootPostAlloc;
    final ByteArrSeq root;
    final OmniCollection.OfByte parent;
    int expectedRootSize;
    int expectedParentSize;
    int expectedRootModCount;
    int expectedParentModCount;
    SeqMonitor(NestedType nestedType,CheckedType checkedType,int seqLength,byte[] arr){
      super(checkedType);
      this.nestedType=nestedType;
      this.expectedRootSize=seqLength;
      this.expectedParentSize=seqLength;
      this.expectedSeqSize=seqLength;
      this.initialCapacity=(arr==null)?0:(arr==OmniArray.OfByte.DEFAULT_ARR?OmniArray.DEFAULT_ARR_SEQ_CAP:arr.length);
      switch(nestedType){
        case SUBLIST:
          seqLength+=(4*DEFAULT_PRE_AND_POST_ALLOC);
        case LIST:
          this.root=checkedType.checked?new ByteArrSeq.CheckedList(seqLength,arr):new ByteArrSeq.UncheckedList(seqLength,arr);
          break;
        case STACK:
          this.root=checkedType.checked?new ByteArrSeq.CheckedStack(seqLength,arr):new ByteArrSeq.UncheckedStack(seqLength,arr);
          break;
        default:
          throw new Error("Unknown nestedType "+nestedType);
      }
      if(nestedType.rootType){
        this.rootPreAlloc=0;
        this.parentPreAlloc=0;
        this.parentPostAlloc=0;
        this.rootPostAlloc=0;
        this.parent=root;
        this.seq=root;
      }else{
        Assertions.assertTrue(arr!=null && seqLength<=arr.length);
        this.rootPreAlloc=DEFAULT_PRE_AND_POST_ALLOC;
        this.parentPreAlloc=DEFAULT_PRE_AND_POST_ALLOC;
        this.parentPostAlloc=DEFAULT_PRE_AND_POST_ALLOC;
        this.rootPostAlloc=DEFAULT_PRE_AND_POST_ALLOC;
        this.parent=((OmniList.OfByte)root).subList(rootPreAlloc,seqLength-rootPostAlloc);
        this.seq=((OmniList.OfByte)parent).subList(parentPreAlloc,seqLength-rootPreAlloc-parentPostAlloc-rootPostAlloc);
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
        byte[] arr=new byte[rootSize=rootPreAlloc+parentPreAlloc+parentPostAlloc+rootPostAlloc];
        initArray(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,arr);
        this.root=checkedType.checked
          ?new ByteArrSeq.CheckedList(rootSize,arr)
          :new ByteArrSeq.UncheckedList(rootSize,arr);
        this.parent=((OmniList.OfByte)root).subList(rootPreAlloc,rootSize-rootPostAlloc);
        this.seq=((OmniList.OfByte)parent).subList(parentPreAlloc,parentPreAlloc);
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
              ?new ByteArrSeq.CheckedStack()
              :new ByteArrSeq.UncheckedStack();
            break;
          case LIST:
            this.root=checkedType.checked
              ?new ByteArrSeq.CheckedList()
              :new ByteArrSeq.UncheckedList();
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
      byte[] arr;
      if(rootSize==0){
        switch(initialCapacity){
          case 0:
            arr=null;
            break;
          case OmniArray.DEFAULT_ARR_SEQ_CAP:
            arr=OmniArray.OfByte.DEFAULT_ARR;
            break;
          default:
            arr=new byte[initialCapacity];
        }
      }else{
        arr=new byte[Math.max(initialCapacity,rootSize)];
      }
      initArray(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,arr);
      this.root=nestedType==NestedType.STACK
        ?checkedType.checked
          ?new ByteArrSeq.CheckedStack(rootSize,arr)
          :new ByteArrSeq.UncheckedStack(rootSize,arr)
        :checkedType.checked
          ?new ByteArrSeq.CheckedList(rootSize,arr)
          :new ByteArrSeq.UncheckedList(rootSize,arr);
      switch(nestedType){
        case SUBLIST:
          this.parent=((OmniList.OfByte)root).subList(rootPreAlloc,rootPreAlloc+parentPreAlloc+parentPostAlloc);
          this.seq=((OmniList.OfByte)parent).subList(parentPreAlloc,parentPreAlloc);
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
    class UncheckedArrSeqItrMonitor extends AbstractByteSeqMonitor.AbstractItrMonitor{
      int expectedCursor;
      int expectedLastRet;
      private UncheckedArrSeqItrMonitor(ItrType itrType,OmniIterator.OfByte itr,int expectedCursor){
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
          itr.forEachRemaining((ByteConsumer)action);
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
        ((OmniListIterator.OfByte)itr).previousByte();
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
            actualCursor=FieldAndMethodAccessor.ByteArrSeq.UncheckedList.Itr.cursor(itr);
            actualParent=FieldAndMethodAccessor.ByteArrSeq.UncheckedList.Itr.parent(itr);
            break;
          case STACK:
            actualCursor=FieldAndMethodAccessor.ByteArrSeq.UncheckedStack.Itr.cursor(itr);
            actualParent=FieldAndMethodAccessor.ByteArrSeq.UncheckedStack.Itr.parent(itr);
            break;
          case SUBLIST:
            actualCursor=FieldAndMethodAccessor.ByteArrSeq.UncheckedSubList.Itr.cursor(itr);
            actualParent=FieldAndMethodAccessor.ByteArrSeq.UncheckedSubList.Itr.parent(itr);
            break;
          default:
            throw new Error("Unknown nestedType "+nestedType);
        }
        Assertions.assertEquals(expectedCursor+(rootPreAlloc+parentPreAlloc),actualCursor);
        Assertions.assertSame(seq,actualParent);
      }
      @Override void add(int v,ByteInputTestArgType inputArgType){
        inputArgType.callListItrAdd((OmniListIterator.OfByte)itr,v);
        ++expectedCursor;
        verifyAddition();
        expectedLastRet=-1;
      }
      @Override void iterateForward(){
        itr.nextByte();
        expectedLastRet=nestedType==NestedType.STACK
          ?--expectedCursor
          :expectedCursor++;
      }
      @Override void verifyNext(int expectedVal,ByteOutputTestArgType outputType){
        outputType.verifyItrNext(itr,expectedVal);
        expectedLastRet=nestedType==NestedType.STACK
          ?--expectedCursor
          :expectedCursor++;
      }
      @Override void verifyPrevious(int expectedVal,ByteOutputTestArgType outputType){
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
      private CheckedArrSeqItrMonitor(ItrType itrType,OmniIterator.OfByte itr,int expectedCursor){
        super(itrType,itr,expectedCursor);
      }
      @Override void verifyIteratorState(){
        int actualCursor;
        Object actualParent;
        switch(nestedType){
          case LIST:
            actualCursor=FieldAndMethodAccessor.ByteArrSeq.CheckedList.Itr.cursor(itr);
            actualParent=FieldAndMethodAccessor.ByteArrSeq.CheckedList.Itr.parent(itr);
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.ByteArrSeq.CheckedList.Itr.modCount(itr));
            Assertions.assertEquals(expectedLastRet<0?expectedLastRet:expectedLastRet+(rootPreAlloc+parentPreAlloc),FieldAndMethodAccessor.ByteArrSeq.CheckedList.Itr.lastRet(itr));
            break;
          case STACK:
            actualCursor=FieldAndMethodAccessor.ByteArrSeq.CheckedStack.Itr.cursor(itr);
            actualParent=FieldAndMethodAccessor.ByteArrSeq.CheckedStack.Itr.parent(itr);
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.ByteArrSeq.CheckedStack.Itr.modCount(itr));
            Assertions.assertEquals(expectedLastRet<0?expectedLastRet:expectedLastRet+(rootPreAlloc+parentPreAlloc),FieldAndMethodAccessor.ByteArrSeq.CheckedStack.Itr.lastRet(itr));
            break;
          case SUBLIST:
            actualCursor=FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.Itr.cursor(itr);
            actualParent=FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.Itr.parent(itr);
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.Itr.modCount(itr));
            Assertions.assertEquals(expectedLastRet<0?expectedLastRet:expectedLastRet+(rootPreAlloc+parentPreAlloc),FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.Itr.lastRet(itr));
            break;
          default:
            throw new Error("Unknown nestedType "+nestedType);
        }
        Assertions.assertEquals(expectedCursor+(rootPreAlloc+parentPreAlloc),actualCursor);
        Assertions.assertSame(seq,actualParent);
      }
      @Override void add(int v,ByteInputTestArgType inputArgType){
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
      var itr=((OmniList.OfByte)seq).listIterator();
      return checkedType.checked
        ?new CheckedArrSeqItrMonitor(ItrType.ListItr,itr,0)
        :new UncheckedArrSeqItrMonitor(ItrType.ListItr,itr,0);
    }
    UncheckedArrSeqItrMonitor getListItrMonitor(int index){
      var itr=((OmniList.OfByte)seq).listIterator(index);
      return checkedType.checked
        ?new CheckedArrSeqItrMonitor(ItrType.ListItr,itr,index)
        :new UncheckedArrSeqItrMonitor(ItrType.ListItr,itr,index);
    }
    private static class ArrSeqSequenceVerificationItr extends SequenceVerificationItr{
      final byte[] arr;
      int offset;
      final SeqMonitor seqMonitor;
      private ArrSeqSequenceVerificationItr(SeqMonitor seqMonitor,int offset,byte[] arr){
        this.seqMonitor=seqMonitor;
        this.arr=arr;
        this.offset=offset;
      }
      SequenceVerificationItr verifyNaturalAscending(int v,ByteInputTestArgType inputArgType,int length){
        return verifyAscending(v,inputArgType,length);
      }
      @Override SequenceVerificationItr verifyPostAlloc(int expectedVal){
        for(int i=0,bound=seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc;i<bound;++i){
          verifyIndexAndIterate(ByteInputTestArgType.ARRAY_TYPE,expectedVal);
        }
        return this;
      }
      @Override void verifyLiteralIndexAndIterate(byte val){
        Assertions.assertEquals(val,arr[offset++]);
      }
      @Override void verifyIndexAndIterate(ByteInputTestArgType inputArgType,int val){
        inputArgType.verifyVal(val,arr[offset++]);
      }
      @Override SequenceVerificationItr getPositiveOffset(int i){
        if(i<0){
          throw new Error("offset cannot be negative: "+i);
        }
        return new ArrSeqSequenceVerificationItr(seqMonitor,i+offset,arr);
      }
      @Override SequenceVerificationItr skip(int i){
        if(i<0){
          throw new Error("offset cannot be negative: "+i);
        }
        this.offset+=i;
        return this;
      }
      @Override public boolean equals(Object val){
        final ArrSeqSequenceVerificationItr that;
        return val==this || (val instanceof ArrSeqSequenceVerificationItr && (that=(ArrSeqSequenceVerificationItr)val).arr==this.arr && that.offset==this.offset);
      }
      @Override SequenceVerificationItr verifyRootPostAlloc(){
        for(int i=0,v=Integer.MAX_VALUE-seqMonitor.rootPostAlloc;i<seqMonitor.rootPostAlloc;++i,++v){
          verifyIndexAndIterate(ByteInputTestArgType.ARRAY_TYPE,v);
        }
        return this;
      }
      @Override SequenceVerificationItr verifyParentPostAlloc(){
        for(int i=0,v=Integer.MAX_VALUE-seqMonitor.rootPostAlloc-seqMonitor.parentPostAlloc;i<seqMonitor.parentPostAlloc;++i,++v){
          verifyIndexAndIterate(ByteInputTestArgType.ARRAY_TYPE,v);
        }
        return this;
      }
    }
    SequenceVerificationItr verifyPreAlloc(int expectedVal){
      var arr=root.arr;
      int offset=0;
      for(int bound=offset+rootPreAlloc+parentPreAlloc;offset<bound;++offset){
        ByteInputTestArgType.ARRAY_TYPE.verifyVal(expectedVal,arr[offset]);
      }
      return new ArrSeqSequenceVerificationItr(this,offset,arr);
    }
    SequenceVerificationItr verifyPreAlloc(){
      var arr=root.arr;
      int offset=0;
      for(int bound=offset+rootPreAlloc+parentPreAlloc,v=Integer.MIN_VALUE;offset<bound;++offset,++v){
        ByteInputTestArgType.ARRAY_TYPE.verifyVal(v,arr[offset]);
      }
      return new ArrSeqSequenceVerificationItr(this,offset,arr);
    }
    void illegalAdd(PreModScenario preModScenario){
      switch(preModScenario){
        case ModSeq:
          ByteInputTestArgType.ARRAY_TYPE.callCollectionAdd(seq,0);
          ++expectedRootModCount;
          ++expectedRootSize;
          ++expectedParentModCount;
          ++expectedParentSize;
          ++expectedSeqModCount;
          ++expectedSeqSize;
          break;
        case ModParent:
          ByteInputTestArgType.ARRAY_TYPE.callCollectionAdd(parent,0);
          ++expectedRootModCount;
          ++expectedRootSize;
          ++expectedParentModCount;
          ++expectedParentSize;
          break;
        case ModRoot:
          ByteInputTestArgType.ARRAY_TYPE.callCollectionAdd(root,0);
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
      var builder=new StringBuilder("ByteArrSeq.").append(checkedType.checked?"Checked":"Unchecked");
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
              Assertions.assertEquals(expectedRootModCount,FieldAndMethodAccessor.ByteArrSeq.CheckedStack.modCount(root));
            }
            break;
          case LIST:
            if(checkedType.checked){
              Assertions.assertEquals(expectedRootModCount,FieldAndMethodAccessor.ByteArrSeq.CheckedList.modCount(root));
            }
            break;
          case SUBLIST:
            OmniList.OfByte actualSeqParent;
            ByteArrSeq actualSeqRoot;
            int actualSeqSize;
            OmniList.OfByte actualParentParent;
            ByteArrSeq  actualParentRoot;
            int actualParentSize;
            if(checkedType.checked){
              actualSeqParent=(OmniList.OfByte)FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.parent(seq);
              actualSeqRoot=(ByteArrSeq)FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.root(seq);
              actualSeqSize=FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.size(seq);
              actualParentParent=(OmniList.OfByte)FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.parent(parent);
              actualParentRoot=(ByteArrSeq)FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.root(parent);
              actualParentSize=FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.size(parent);
              Assertions.assertEquals(expectedSeqModCount,FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.modCount(seq));
              Assertions.assertEquals(expectedParentModCount,FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.modCount(parent));
              Assertions.assertEquals(expectedRootModCount,FieldAndMethodAccessor.ByteArrSeq.CheckedList.modCount(root));
            }else{
              actualSeqParent=(OmniList.OfByte)FieldAndMethodAccessor.ByteArrSeq.UncheckedSubList.parent(seq);
              actualSeqRoot=(ByteArrSeq)FieldAndMethodAccessor.ByteArrSeq.UncheckedSubList.root(seq);
              actualSeqSize=FieldAndMethodAccessor.ByteArrSeq.UncheckedSubList.size(seq);
              actualParentParent=(OmniList.OfByte)FieldAndMethodAccessor.ByteArrSeq.UncheckedSubList.parent(parent);
              actualParentRoot=(ByteArrSeq)FieldAndMethodAccessor.ByteArrSeq.UncheckedSubList.root(parent);
              actualParentSize=FieldAndMethodAccessor.ByteArrSeq.UncheckedSubList.size(parent);
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
        Assertions.assertEquals(expectedRootSize+parentPreAlloc+parentPostAlloc+rootPreAlloc+rootPostAlloc,FieldAndMethodAccessor.ByteArrSeq.size(root));
    }
    void verifyFunctionalModification(){
       ++expectedSeqModCount;
       ++expectedParentModCount;
       ++expectedRootModCount;
    }
    void clear(){
      int seqSize=expectedSeqSize;
      seq.clear();
      if(seqSize!=0){
        expectedSeqSize=0;
        expectedParentSize=0;
        expectedRootSize=0;
        ++expectedSeqModCount;
        ++expectedParentModCount;
        ++expectedRootModCount;
      }
    }
    void verifyRemoveIf(MonitoredRemoveIfPredicate pred,FunctionCallType functionCallType,int expectedNumRemoved,OmniCollection.OfByte clone){
      boolean retVal;
      if(functionCallType==FunctionCallType.Boxed){
        retVal=seq.removeIf((Predicate)pred);
      }
      else
      {
        retVal=seq.removeIf((BytePredicate)pred);
      }
      if(retVal){
        ++expectedSeqModCount;
        ++expectedParentModCount;
        ++expectedRootModCount;
        int numRemoved;
        numRemoved=pred.numRemoved;
        for(var removedVal:pred.removedVals){
          Assertions.assertFalse(seq.contains(removedVal));
        }
        expectedSeqSize-=numRemoved;
        expectedParentSize-=numRemoved;
        expectedRootSize-=numRemoved;
        if(expectedNumRemoved!=-1){
          Assertions.assertEquals(expectedNumRemoved,numRemoved);
        }
      }else{
        Assertions.assertEquals(expectedSeqSize,clone.size());
        var seqItr=seq.iterator();
        var cloneItr=clone.iterator();
        for(int i=0;i<expectedSeqSize;++i){
          Assertions.assertEquals(seqItr.nextByte(),cloneItr.nextByte());
        }
      }
      verifyStructuralIntegrity();
    }
    void writeObject(ObjectOutputStream oos) throws IOException{
      switch(nestedType){
        case LIST:
          if(checkedType.checked){
            FieldAndMethodAccessor.ByteArrSeq.CheckedList.writeObject(seq,oos);
          }else{
            FieldAndMethodAccessor.ByteArrSeq.UncheckedList.writeObject(seq,oos);
          }
          break;
        case STACK:
          if(checkedType.checked){
            FieldAndMethodAccessor.ByteArrSeq.CheckedStack.writeObject(seq,oos);
          }else{
            FieldAndMethodAccessor.ByteArrSeq.UncheckedStack.writeObject(seq,oos);
          }
          break;
        case SUBLIST:
          if(checkedType.checked){
            FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.writeObject(seq,oos);
          }else{
            FieldAndMethodAccessor.ByteArrSeq.UncheckedSubList.writeObject(seq,oos);
          }
          break;
        default:
          throw new Error("unknown nested type "+nestedType);
      }
    }
    Object readObject(ObjectInputStream ois) throws IOException,ClassNotFoundException{
      switch(nestedType){
        case LIST:
          if(checkedType.checked){
            return FieldAndMethodAccessor.ByteArrSeq.CheckedList.readObject(seq,ois);
          }else{
            return FieldAndMethodAccessor.ByteArrSeq.UncheckedList.readObject(seq,ois);
          }
        case STACK:
          if(checkedType.checked){
            return FieldAndMethodAccessor.ByteArrSeq.CheckedStack.readObject(seq,ois);
          }else{
            return FieldAndMethodAccessor.ByteArrSeq.UncheckedStack.readObject(seq,ois);
          }
        case SUBLIST:
          if(checkedType.checked){
            return FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.readObject(seq,ois);
          }else{
            return FieldAndMethodAccessor.ByteArrSeq.UncheckedSubList.readObject(seq,ois);
          }
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
  static void buildQueryArguments(Stream.Builder<Arguments> builder,NestedType nestedType){
    for(var checkedType:CheckedType.values()){
      for(var preModScenario:PreModScenario.values()){
        if(preModScenario!=PreModScenario.ModSeq && (preModScenario.expectedException==null || (checkedType.checked && !nestedType.rootType))){
          for(var seqLocation:SequenceLocation.values()){
            if(seqLocation!=SequenceLocation.IOBLO){
              for(var seqContentsScenario:SequenceContentsScenario.values()){
                if(seqLocation==SequenceLocation.IOBHI || seqContentsScenario.nonEmpty){
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
                          if(queryCastType!=QueryCastType.ToBoxed || (seqContentsScenario.nonEmpty && seqLocation.expectedException==null)){
                            continue;
                          }
                          break;
                        case Objectnull:
                          if(queryCastType!=QueryCastType.ToObject || (seqContentsScenario.nonEmpty && seqLocation.expectedException==null)){
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
                        if(seqContentsScenario.nonEmpty && seqLocation.expectedException==null){
                          continue;
                        }
                        //these values must necessarily return false
                      }
                      builder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),argType,queryCastType,seqLocation,seqContentsScenario,preModScenario));
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
  static Stream<Arguments> getQueryStackArguments(){
    Stream.Builder<Arguments> builder=Stream.builder();
    buildQueryArguments(builder,NestedType.STACK);
    return builder.build();
  }
  static Stream<Arguments> getQueryListArguments(){
    Stream.Builder<Arguments> builder=Stream.builder();
    buildQueryArguments(builder,NestedType.LIST);
    buildQueryArguments(builder,NestedType.SUBLIST);
    return builder.build();
  }
  static Stream<Arguments> getQueryCollectionArguments(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var nestedType:NestedType.values()){
      buildQueryArguments(builder,nestedType);
    }
    return builder.build();
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
                  for(var sequenceContentsScenario:SequenceContentsScenario.values()){
                    builder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredComparatorGen,sequenceContentsScenario));
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
  static Stream<Arguments> getBasicCollectionTestArgs(){
    return ArgBuilder.buildSeqArgs((streamBuilder,nestedType,checkedType,preModScenario)->{
      for(var seqContentsScenario:SequenceContentsScenario.values()){
        streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario,seqContentsScenario));
      }
    });
  }
  static Stream<Arguments> getStackpollpeekAndpop_voidArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var checkedType:CheckedType.values()){
      for(var outputArgType:ByteOutputTestArgType.values()){
        builder.accept(Arguments.of(new SeqMonitor(NestedType.STACK,checkedType),outputArgType));
      }
    }
    return builder.build();
  }
  static Stream<Arguments> gettoStringAndhashCode_voidArgs(){
   return ArgBuilder.buildSeqArgs((streamBuilder,nestedType,checkedType,preModScenario)->{
     for(var seqContentsScenario:SequenceContentsScenario.values()){
       streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario,seqContentsScenario));
     }
   });
  }
}
