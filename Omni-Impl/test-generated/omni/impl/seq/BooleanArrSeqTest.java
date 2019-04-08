package omni.impl.seq;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import omni.impl.BooleanInputTestArgType;
import omni.impl.BooleanOutputTestArgType;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import omni.util.OmniArray;
import omni.impl.FunctionCallType;
import omni.impl.QueryCastType;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import omni.impl.seq.BooleanSeqMonitor.NestedType;
import omni.impl.seq.BooleanSeqMonitor.StructType;
import omni.impl.seq.BooleanSeqMonitor.CheckedType;
import omni.impl.seq.BooleanSeqMonitor.PreModScenario;
import omni.impl.seq.BooleanSeqMonitor.SequenceLocation;
import omni.impl.seq.BooleanSeqMonitor.SequenceContentsScenario;
import omni.impl.seq.BooleanSeqMonitor.ListItrSetScenario;
import omni.impl.seq.BooleanSeqMonitor.ItrType;
import omni.impl.seq.BooleanSeqMonitor.IterationScenario;
import omni.impl.seq.BooleanSeqMonitor.ItrRemoveScenario;
import omni.impl.seq.BooleanSeqMonitor.MonitoredFunctionGen;
import omni.impl.seq.BooleanSeqMonitor.MonitoredComparatorGen;
import omni.impl.seq.BooleanSeqMonitor.SequenceVerificationItr;
import omni.impl.seq.BooleanSeqMonitor.QueryTester;
import omni.api.OmniCollection;
import omni.api.OmniList;
@Execution(ExecutionMode.CONCURRENT)
public class BooleanArrSeqTest{
    //TODO refactor RemoveIf
    static Stream<Arguments> gettoArray_ObjectArrayArgs(){
      Stream.Builder<Arguments> builder=Stream.builder();
      for(var nestedType:NestedType.values()){
        for(var checkedType:CheckedType.values()){
          for(var preModScenario:PreModScenario.values()){
            if(preModScenario!=PreModScenario.ModSeq&&(preModScenario.expectedException==null||(!nestedType.rootType&&checkedType.checked))){
              for(int seqSize=0;seqSize<=15;seqSize+=5){
                for(int arrSize=0;arrSize<=20;arrSize+=5){
                  builder.accept(Arguments.of(new BooleanSeqMonitor(StructType.ARRSEQ,nestedType,checkedType),preModScenario,seqSize,arrSize));
                }
              }
            }
          }
        }
      }
      return builder.build();
    }
    @org.junit.jupiter.params.ParameterizedTest
    @org.junit.jupiter.params.provider.MethodSource("gettoArray_ObjectArrayArgs")
    public void testtoArray_ObjectArray
    (BooleanSeqMonitor seqMonitor,PreModScenario preModScenario,int seqSize,int arrSize){
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
        }
        else if(arrSize>seqSize){
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
    }
    static Stream<Arguments> gettoArray_IntFunctionArgs(){
      Stream.Builder<Arguments> builder=Stream.builder();
      for(var checkedType:CheckedType.values()){
        for(var nestedType:NestedType.values()){
          for(var preModScenario:PreModScenario.values()){
            if(preModScenario!=PreModScenario.ModSeq&&(preModScenario.expectedException==null||(!nestedType.rootType&&checkedType.checked))){
              for(var monitoredFunctionGen:MonitoredFunctionGen.values()){
                if((checkedType.checked || monitoredFunctionGen.expectedException==null)&&(!nestedType.rootType || monitoredFunctionGen.appliesToRoot) &&(nestedType.rootType || monitoredFunctionGen.appliesToSubList)){
                  for(var seqContentsScenario:SequenceContentsScenario.values()){
                    builder.accept(Arguments.of(new BooleanSeqMonitor(StructType.ARRSEQ,nestedType,checkedType),preModScenario,monitoredFunctionGen,seqContentsScenario));
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
    public void testtoArray_IntFunction(){
      gettoArray_IntFunctionArgs().parallel().map(Arguments::get).forEach(args->{
          testtoArray_IntFunctionHelper((BooleanSeqMonitor)args[0],(PreModScenario)args[1],(MonitoredFunctionGen)args[2],(SequenceContentsScenario)args[3]);
      });
    }
    private static void testtoArray_IntFunctionHelper
    (BooleanSeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredFunctionGen monitoredFunctionGen,SequenceContentsScenario seqContentsScenario){
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
                          builder.accept(Arguments.of(new BooleanSeqMonitor(StructType.ARRSEQ,nestedType,checkedType),preModScenario,seqSize,fromIndex,toIndex));
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
          testListsubList_int_intHelper((BooleanSeqMonitor)args[0],(PreModScenario)args[1],(int)args[2],(int)args[3],(int)args[4]);
      });
    }
    private static void testListsubList_int_intHelper
    (BooleanSeqMonitor seqMonitor,PreModScenario preModScenario,int seqSize,int fromIndex,int toIndex){
      for(int i=0;i<seqSize;++i){
        seqMonitor.add(i);
      }
      seqMonitor.illegalAdd(preModScenario);
      if(preModScenario.expectedException==null){
        if(fromIndex>=0 && fromIndex<=toIndex && toIndex<=seqSize){
          var subList=((OmniList.OfBoolean)seqMonitor.seq).subList(fromIndex,toIndex);
          if(seqMonitor.checkedType.checked){
            Assertions.assertEquals(seqMonitor.expectedRootModCount,FieldAccessor.BooleanArrSeq.CheckedSubList.modCount(subList));
            if(seqMonitor.nestedType.rootType){
              Assertions.assertNull(FieldAccessor.BooleanArrSeq.CheckedSubList.parent(subList));
            }else{
              Assertions.assertSame(seqMonitor.seq,FieldAccessor.BooleanArrSeq.CheckedSubList.parent(subList));
            }
            Assertions.assertSame(seqMonitor.root,FieldAccessor.BooleanArrSeq.CheckedSubList.root(subList));
            Assertions.assertEquals(toIndex-fromIndex,FieldAccessor.BooleanArrSeq.CheckedSubList.size(subList));
            Assertions.assertEquals(seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+fromIndex,FieldAccessor.BooleanArrSeq.CheckedSubList.rootOffset(subList));
          }else{
            if(seqMonitor.nestedType.rootType){
              Assertions.assertNull(FieldAccessor.BooleanArrSeq.UncheckedSubList.parent(subList));
            }else{
              Assertions.assertSame(seqMonitor.seq,FieldAccessor.BooleanArrSeq.UncheckedSubList.parent(subList));
            }
            Assertions.assertSame(seqMonitor.root,FieldAccessor.BooleanArrSeq.UncheckedSubList.root(subList));
            Assertions.assertEquals(toIndex-fromIndex,FieldAccessor.BooleanArrSeq.UncheckedSubList.size(subList));
            Assertions.assertEquals(seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+fromIndex,FieldAccessor.BooleanArrSeq.UncheckedSubList.rootOffset(subList));
          }
        }else{
          Assertions.assertThrows(IndexOutOfBoundsException.class,()->((OmniList.OfBoolean)seqMonitor.seq).subList(fromIndex,toIndex));
        }
      }else{
        Assertions.assertThrows(preModScenario.expectedException,()->((OmniList.OfBoolean)seqMonitor.seq).subList(fromIndex,toIndex));
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
                      builder.accept(Arguments.of(new BooleanSeqMonitor(StructType.ARRSEQ,nestedType,checkedType),preModScenario,monitoredFunctionGen,seqContentsScenario,FunctionCallType.Unboxed));
                      builder.accept(Arguments.of(new BooleanSeqMonitor(StructType.ARRSEQ,nestedType,checkedType),preModScenario,monitoredFunctionGen,seqContentsScenario,FunctionCallType.Boxed));
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
          testListreplaceAll_UnaryOperatorHelper((BooleanSeqMonitor)args[0],(PreModScenario)args[1],(MonitoredFunctionGen)args[2],(SequenceContentsScenario)args[3],(FunctionCallType)args[4]);
      });
    }
    private static void testListreplaceAll_UnaryOperatorHelper
    (BooleanSeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredFunctionGen monitoredFunctionGen,SequenceContentsScenario seqContentsScenario,FunctionCallType functionCallType){
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
      var arr=((BooleanArrSeq)seqMonitor.root).arr;
      int i=seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc;
      if(numExpectedIteratedValues==numToAdd){
        if(monitoredFunctionGen.expectedException==null){
          for(var encounteredValue:monitoredUnaryOperator.encounteredValues){
            var expectedVal=(boolean)!((boolean)encounteredValue);
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
      Stream.Builder<Arguments> builder=Stream.builder();
      for(var nestedType:NestedType.values()){
        for(var checkedType:CheckedType.values()){
          for(var preModScenario:PreModScenario.values()){
            if(preModScenario!=PreModScenario.ModSeq && (preModScenario.expectedException==null || (checkedType.checked && !nestedType.rootType))){
              builder.accept(Arguments.of(new BooleanSeqMonitor(StructType.ARRSEQ,nestedType,checkedType),preModScenario));
            }
          }
        }
      }
      return builder.build();
    }
    @org.junit.jupiter.api.Test
    public void testiterator_void(){
      getiterator_voidArgs().parallel().map(Arguments::get).forEach(args->{
          testiterator_voidHelper((BooleanSeqMonitor)args[0],(PreModScenario)args[1]);
      });
    }
    private static void testiterator_voidHelper
    (BooleanSeqMonitor seqMonitor,PreModScenario preModScenario){
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
                builder.accept(Arguments.of(new BooleanSeqMonitor(StructType.ARRSEQ,nestedType,checkedType),preModScenario));
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
          testListlistIterator_voidHelper((BooleanSeqMonitor)args[0],(PreModScenario)args[1]);
      });
    }
    private static void testListlistIterator_voidHelper
    (BooleanSeqMonitor seqMonitor,PreModScenario preModScenario){
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
                    builder.accept(Arguments.of(new BooleanSeqMonitor(StructType.ARRSEQ,nestedType,checkedType),preModScenario,seqLocation));
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
          testListlistIterator_intHelper((BooleanSeqMonitor)args[0],(PreModScenario)args[1],(SequenceLocation)args[2]);
      });
    }
    private static void testListlistIterator_intHelper
    (BooleanSeqMonitor seqMonitor,PreModScenario preModScenario,SequenceLocation seqLocation){
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
      BooleanArrSeq seq;
      if(checkedType.checked){
        Assertions.assertEquals(0,nestedType==NestedType.LIST?FieldAccessor.BooleanArrSeq.CheckedList.modCount(seq=new BooleanArrSeq.CheckedList(initialCapacity)):FieldAccessor.BooleanArrSeq.CheckedStack.modCount(seq=new BooleanArrSeq.CheckedStack(initialCapacity)));
      }else{
        seq=nestedType==NestedType.LIST?new BooleanArrSeq.UncheckedList(initialCapacity):new BooleanArrSeq.UncheckedStack(initialCapacity);
      }
      Assertions.assertEquals(0,seq.size);
      switch(initialCapacity){
        case 0:
          Assertions.assertNull(seq.arr);
          break;
        case OmniArray.DEFAULT_ARR_SEQ_CAP:
          Assertions.assertSame(OmniArray.OfBoolean.DEFAULT_ARR,seq.arr);
          break;
        default:
          Assertions.assertEquals(initialCapacity,seq.arr.length);
      }
    }
    static Stream<Arguments> gettoArray_voidArgs(){
      Stream.Builder<Arguments> builder=Stream.builder();
      for(var nestedType:NestedType.values()){
        for(var checkedType:CheckedType.values()){
          for(var preModScenario:PreModScenario.values()){
            if(preModScenario.expectedException==null || (checkedType.checked && !nestedType.rootType && preModScenario!=PreModScenario.ModSeq)){
              for(var seqContentsScenario:SequenceContentsScenario.values()){
                for(var outputArgType:BooleanOutputTestArgType.values()){
                  builder.accept(Arguments.of(new BooleanSeqMonitor(StructType.ARRSEQ,nestedType,checkedType),preModScenario,seqContentsScenario,outputArgType));
                }
              }
            }
          }
        }
      }
      return builder.build();
    }
    @org.junit.jupiter.api.Test
    public void testtoArray_void(){
      gettoArray_voidArgs().parallel().map(Arguments::get).forEach(args->{
          testtoArray_voidHelper((BooleanSeqMonitor)args[0],(PreModScenario)args[1],(SequenceContentsScenario)args[2],(BooleanOutputTestArgType)args[3]);
      });
    }
    private static void testtoArray_voidHelper
    (BooleanSeqMonitor seqMonitor,PreModScenario preModScenario,SequenceContentsScenario seqContentsScenario,BooleanOutputTestArgType outputArgType){
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
                        for(var outputArgType:BooleanOutputTestArgType.values()){
                          builder.accept(Arguments.of(new BooleanSeqMonitor(StructType.ARRSEQ,nestedType,checkedType),preModScenario,seqLocation,seqContentsScenario,outputArgType));
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
          testListremoveAt_intHelper((BooleanSeqMonitor)args[0],(PreModScenario)args[1],(SequenceLocation)args[2],(SequenceContentsScenario)args[3],(BooleanOutputTestArgType)args[4]);
      });
    }
    private static void testListremoveAt_intHelper
    (BooleanSeqMonitor seqMonitor,PreModScenario preModScenario,SequenceLocation seqLocation,SequenceContentsScenario seqContentsScenario,BooleanOutputTestArgType outputArgType){
      int numToAdd=seqContentsScenario.nonEmpty?100:0;
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
          testListstableDescendingSort_voidHelper((BooleanSeqMonitor)args[0],(PreModScenario)args[1],(MonitoredComparatorGen)args[2],(SequenceContentsScenario)args[3]);
      });
    }
    private static void testListstableDescendingSort_voidHelper
    (BooleanSeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredComparatorGen monitoredComparatorGen,SequenceContentsScenario seqContentsScenario){
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
          testclone_voidHelper((BooleanSeqMonitor)args[0],(PreModScenario)args[1],(SequenceContentsScenario)args[2]);
      });
    }
    private static void testclone_voidHelper
    (BooleanSeqMonitor seqMonitor,PreModScenario preModScenario,SequenceContentsScenario seqContentsScenario){
      int numToAdd=seqContentsScenario.nonEmpty?100:0;
      for(int i=0;i<numToAdd;++i){
        seqMonitor.add(i);
      }
      seqMonitor.illegalAdd(preModScenario);
      if(preModScenario.expectedException==null){
        var clone=(OmniCollection.OfBoolean)seqMonitor.seq.clone();
        Assertions.assertNotSame(clone,seqMonitor.seq);
        switch(seqMonitor.nestedType){
          case STACK:
            if(seqMonitor.checkedType.checked){
              Assertions.assertTrue(clone instanceof BooleanArrSeq.CheckedStack);
              Assertions.assertEquals(0,((BooleanArrSeq.CheckedStack)clone).modCount);
            }else{
              Assertions.assertTrue(clone instanceof BooleanArrSeq.UncheckedStack);
            }
            break;
          case LIST:
          case SUBLIST:
            if(seqMonitor.checkedType.checked){
              Assertions.assertTrue(clone instanceof BooleanArrSeq.CheckedList);
              Assertions.assertEquals(0,((BooleanArrSeq.CheckedList)clone).modCount);
            }else{
              Assertions.assertTrue(clone instanceof BooleanArrSeq.UncheckedList);
            }
            break;
          default:
            throw new Error("Unknown nested type "+seqMonitor.nestedType);
        }
        var arrSeqClone=(BooleanArrSeq)clone;
        var originalArr=((BooleanArrSeq)seqMonitor.root).arr;
        var cloneArr=arrSeqClone.arr;
        Assertions.assertEquals(numToAdd,arrSeqClone.size);
        if(arrSeqClone.size==0){
          Assertions.assertSame(cloneArr,OmniArray.OfBoolean.DEFAULT_ARR);
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
          testsize_voidHelper((BooleanSeqMonitor)args[0],(PreModScenario)args[1],(SequenceContentsScenario)args[2]);
      });
    }
    private static void testsize_voidHelper
    (BooleanSeqMonitor seqMonitor,PreModScenario preModScenario,SequenceContentsScenario seqContentsScenario){
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
          testisEmpty_voidHelper((BooleanSeqMonitor)args[0],(PreModScenario)args[1],(SequenceContentsScenario)args[2]);
      });
    }
    private static void testisEmpty_voidHelper
    (BooleanSeqMonitor seqMonitor,PreModScenario preModScenario,SequenceContentsScenario seqContentsScenario){
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
          testclear_voidHelper((BooleanSeqMonitor)args[0],(PreModScenario)args[1],(SequenceContentsScenario)args[2]);
      });
    }
    private static void testclear_voidHelper
    (BooleanSeqMonitor seqMonitor,PreModScenario preModScenario,SequenceContentsScenario seqContentsScenario){
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
          testStackpeek_voidHelper((BooleanSeqMonitor)args[0],(BooleanOutputTestArgType)args[1]);
      });
    }
    private static void testStackpeek_voidHelper
    (BooleanSeqMonitor seqMonitor,BooleanOutputTestArgType outputArgType){
      for(int i=0;i<100;){
        outputArgType.verifyStackPeek(seqMonitor.seq,i,i);
        seqMonitor.verifyStructuralIntegrity();
        seqMonitor.add(++i);
      }
    }
    @org.junit.jupiter.api.Test
    public void testStackpop_void(){
      getStackpollpeekAndpop_voidArgs().parallel().map(Arguments::get).forEach(args->{
          testStackpop_voidHelper((BooleanSeqMonitor)args[0],(BooleanOutputTestArgType)args[1]);
      });
    }
    private static void testStackpop_voidHelper
    (BooleanSeqMonitor seqMonitor,BooleanOutputTestArgType outputArgType){
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
          testStackpoll_voidHelper((BooleanSeqMonitor)args[0],(BooleanOutputTestArgType)args[1]);
      });
    }
    private static void testStackpoll_voidHelper
    (BooleanSeqMonitor seqMonitor,BooleanOutputTestArgType outputArgType){
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
          testListstableAscendingSort_voidHelper((BooleanSeqMonitor)args[0],(PreModScenario)args[1],(MonitoredComparatorGen)args[2],(SequenceContentsScenario)args[3]);
      });
    }
    private static void testListstableAscendingSort_voidHelper
    (BooleanSeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredComparatorGen monitoredComparatorGen,SequenceContentsScenario seqContentsScenario){
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
                        builder.accept(Arguments.of(new BooleanSeqMonitor(StructType.ARRSEQ,nestedType,checkedType),preModScenario,monitoredComparatorGen,sequenceContentsScenario,functionCallType));
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
          testListsort_ComparatorHelper((BooleanSeqMonitor)args[0],(PreModScenario)args[1],(MonitoredComparatorGen)args[2],(SequenceContentsScenario)args[3],(FunctionCallType)args[4]);
      });
    }
    private static void testListsort_ComparatorHelper
    (BooleanSeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredComparatorGen monitoredComparatorGen,SequenceContentsScenario seqContentsScenario,FunctionCallType functionCallType){
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
          testremoveVal_valHelper((BooleanSeqMonitor)args[0],(QueryTester)args[1],(QueryCastType)args[2],(SequenceLocation)args[3],(SequenceContentsScenario)args[4],(PreModScenario)args[5]
          );
      });
    }
    private static void testremoveVal_valHelper
    (BooleanSeqMonitor seqMonitor,QueryTester argType,QueryCastType queryCastType,SequenceLocation seqLocation,SequenceContentsScenario seqContentsScenario,PreModScenario preModScenario 
    ){
      if(seqContentsScenario.nonEmpty){
        {
          switch(seqLocation){
            case BEGINNING:
              argType.initContainsBeginning(seqMonitor);
              break;
            case MIDDLE:
              argType.initContainsMiddle(seqMonitor);
              break;
            case END:
              argType.initContainsEnd(seqMonitor);
              break;
            case IOBHI:
              argType.initDoesNotContain(seqMonitor);
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
          testsearch_valHelper((BooleanSeqMonitor)args[0],(QueryTester)args[1],(QueryCastType)args[2],(SequenceLocation)args[3],(SequenceContentsScenario)args[4],(PreModScenario)args[5]
          );
      });
    }
    private static void testsearch_valHelper
    (BooleanSeqMonitor seqMonitor,QueryTester argType,QueryCastType queryCastType,SequenceLocation seqLocation,SequenceContentsScenario seqContentsScenario,PreModScenario preModScenario 
    ){
      int expectedIndex;
      if(seqContentsScenario.nonEmpty){
        {
          switch(seqLocation){
            case BEGINNING:
              expectedIndex=argType.initContainsBeginning(seqMonitor);
              expectedIndex=seqMonitor.expectedSeqSize-expectedIndex;
              break;
            case MIDDLE:
              expectedIndex=argType.initContainsMiddle(seqMonitor);
              expectedIndex=seqMonitor.expectedSeqSize-expectedIndex;
              break;
            case END:
              expectedIndex=argType.initContainsEnd(seqMonitor);
              expectedIndex=seqMonitor.expectedSeqSize-expectedIndex;
              break;
            case IOBHI:
              argType.initDoesNotContain(seqMonitor);
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
          testlastIndexOf_valHelper((BooleanSeqMonitor)args[0],(QueryTester)args[1],(QueryCastType)args[2],(SequenceLocation)args[3],(SequenceContentsScenario)args[4],(PreModScenario)args[5]
          );
      });
    }
    private static void testlastIndexOf_valHelper
    (BooleanSeqMonitor seqMonitor,QueryTester argType,QueryCastType queryCastType,SequenceLocation seqLocation,SequenceContentsScenario seqContentsScenario,PreModScenario preModScenario 
    ){
      int expectedIndex;
      if(seqContentsScenario.nonEmpty){
        {
          switch(seqLocation){
            case BEGINNING:
              expectedIndex=argType.initContainsBeginning(seqMonitor);
              break;
            case MIDDLE:
              expectedIndex=argType.initContainsMiddle(seqMonitor);
              break;
            case END:
              expectedIndex=argType.initContainsEnd(seqMonitor);
              break;
            case IOBHI:
              argType.initDoesNotContain(seqMonitor);
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
    public void testindexOf_val(){
      getQueryListArguments().parallel().map(Arguments::get).forEach(args->{
          testindexOf_valHelper((BooleanSeqMonitor)args[0],(QueryTester)args[1],(QueryCastType)args[2],(SequenceLocation)args[3],(SequenceContentsScenario)args[4],(PreModScenario)args[5]
          );
      });
    }
    private static void testindexOf_valHelper
    (BooleanSeqMonitor seqMonitor,QueryTester argType,QueryCastType queryCastType,SequenceLocation seqLocation,SequenceContentsScenario seqContentsScenario,PreModScenario preModScenario 
    ){
      int expectedIndex;
      if(seqContentsScenario.nonEmpty){
        {
          switch(seqLocation){
            case BEGINNING:
              expectedIndex=argType.initContainsBeginning(seqMonitor);
              break;
            case MIDDLE:
              expectedIndex=argType.initContainsMiddle(seqMonitor);
              break;
            case END:
              expectedIndex=argType.initContainsEnd(seqMonitor);
              break;
            case IOBHI:
              argType.initDoesNotContain(seqMonitor);
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
    public void testcontains_val(){
      getQueryCollectionArguments().parallel().map(Arguments::get).forEach(args->{
          testcontains_valHelper((BooleanSeqMonitor)args[0],(QueryTester)args[1],(QueryCastType)args[2],(SequenceLocation)args[3],(SequenceContentsScenario)args[4],(PreModScenario)args[5]
          );
      });
    }
    private static void testcontains_valHelper
    (BooleanSeqMonitor seqMonitor,QueryTester argType,QueryCastType queryCastType,SequenceLocation seqLocation,SequenceContentsScenario seqContentsScenario,PreModScenario preModScenario 
    ){
      if(seqContentsScenario.nonEmpty){
        {
          switch(seqLocation){
            case BEGINNING:
              argType.initContainsBeginning(seqMonitor);
              break;
            case MIDDLE:
              argType.initContainsMiddle(seqMonitor);
              break;
            case END:
              argType.initContainsEnd(seqMonitor);
              break;
            case IOBHI:
              argType.initDoesNotContain(seqMonitor);
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
    static Stream<Arguments> getStackpush_valArgs(){
      Stream.Builder<Arguments> builder=Stream.builder();
      for(var checkedType:CheckedType.values()){
        for(var inputArgType:BooleanInputTestArgType.values()){
          for(int initialCapacity=0;initialCapacity<=15;initialCapacity+=5){
            builder.accept(Arguments.of(new BooleanSeqMonitor(StructType.ARRSEQ,NestedType.STACK,checkedType,initialCapacity),inputArgType));
          }
        }
      }
      return builder.build();
    }
    @org.junit.jupiter.api.Test
    public void testStackpush_val(){
      getStackpush_valArgs().parallel().map(Arguments::get).forEach(args->{
          testStackpush_valHelper((BooleanSeqMonitor)args[0],(BooleanInputTestArgType)args[1]);
      });
    }
    private static void testStackpush_valHelper
    (BooleanSeqMonitor seqMonitor,BooleanInputTestArgType inputArgType){
      for(int i=0;i<100;++i){
        seqMonitor.push(i,inputArgType);
        seqMonitor.verifyStructuralIntegrity();
      }
      seqMonitor.verifyPreAlloc().verifyAscending(inputArgType,100);
    }
    static Stream<Arguments> getadd_valArgs(){
      Stream.Builder<Arguments> builder=Stream.builder();
      for(var nestedType:NestedType.values()){
        for(var checkedType:CheckedType.values()){
          for(var preModScenario:PreModScenario.values()){
            if(preModScenario!=PreModScenario.ModSeq && (preModScenario.expectedException==null || checkedType.checked) && (!nestedType.rootType || preModScenario==PreModScenario.NoMod)){
              for(var seqContentsScenario:SequenceContentsScenario.values()){
                for(var inputArgType:BooleanInputTestArgType.values()){
                  for(int initialCapacity=0;initialCapacity<=15;initialCapacity+=5){
                    switch(nestedType){
                      case LIST:
                      case STACK:
                        builder.accept(Arguments.of(new BooleanSeqMonitor(StructType.ARRSEQ,nestedType,checkedType,initialCapacity),inputArgType,preModScenario,seqContentsScenario));
                        break;
                      case SUBLIST:
                        for(int rootPreAlloc=0;rootPreAlloc<=5;rootPreAlloc+=5){
                          for(int parentPreAlloc=0;parentPreAlloc<=5;parentPreAlloc+=5){
                            for(int parentPostAlloc=0;parentPostAlloc<=5;parentPostAlloc+=5){
                              for(int rootPostAlloc=0;rootPostAlloc<=5;rootPostAlloc+=5){
                                builder.accept(Arguments.of(new BooleanSeqMonitor(StructType.ARRSEQ,nestedType,checkedType,initialCapacity,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),inputArgType,preModScenario,seqContentsScenario));
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
      return builder.build();
    }
    @org.junit.jupiter.api.Test
    public void testadd_val(){
      getadd_valArgs().parallel().map(Arguments::get).forEach(args->{
          testadd_valHelper((BooleanSeqMonitor)args[0],(BooleanInputTestArgType)args[1],(PreModScenario)args[2],(SequenceContentsScenario)args[3]);
      });
    }
    private static void testadd_valHelper
    (BooleanSeqMonitor seqMonitor,BooleanInputTestArgType inputArgType,PreModScenario preModScenario,SequenceContentsScenario sequenceContentsScenario){
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
                        for(var inputArgType:BooleanInputTestArgType.values()){
                          builder.accept(Arguments.of(new BooleanSeqMonitor(StructType.ARRSEQ,nestedType,checkedType),inputArgType,seqLocation,preModScenario,seqContentsScenario));
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
          testListput_int_valHelper((BooleanSeqMonitor)args[0],(BooleanInputTestArgType)args[1],(SequenceLocation)args[2],(PreModScenario)args[3],(SequenceContentsScenario)args[4]);
      });
    }
    private static void testListput_int_valHelper
    (BooleanSeqMonitor seqMonitor,BooleanInputTestArgType inputArgType,SequenceLocation seqLocation,PreModScenario preModScenario,SequenceContentsScenario seqContentsScenario){
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
                        for(var inputArgType:BooleanInputTestArgType.values()){
                          for(int initialCapacity=0;initialCapacity<=15;initialCapacity+=5){
                            switch(nestedType){
                              case LIST:
                                builder.accept(Arguments.of(new BooleanSeqMonitor(StructType.ARRSEQ,nestedType,checkedType,initialCapacity),inputArgType,seqLocation,preModScenario,seqContentsScenario));
                                break;
                              case SUBLIST:
                                for(int rootPreAlloc=0;rootPreAlloc<=5;rootPreAlloc+=5){
                                  for(int parentPreAlloc=0;parentPreAlloc<=5;parentPreAlloc+=5){
                                    for(int parentPostAlloc=0;parentPostAlloc<=5;parentPostAlloc+=5){
                                      for(int rootPostAlloc=0;rootPostAlloc<=5;rootPostAlloc+=5){
                                        builder.accept(Arguments.of(new BooleanSeqMonitor(StructType.ARRSEQ,nestedType,checkedType,initialCapacity,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),inputArgType,seqLocation,preModScenario,seqContentsScenario));
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
          testListadd_int_valHelper((BooleanSeqMonitor)args[0],(BooleanInputTestArgType)args[1],(SequenceLocation)args[2],(PreModScenario)args[3],(SequenceContentsScenario)args[4]);
      });
    }
    private static void testListadd_int_valHelper
    (BooleanSeqMonitor seqMonitor,BooleanInputTestArgType inputArgType,SequenceLocation seqLocation,PreModScenario preModScenario,SequenceContentsScenario seqContentsScenario){
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
          case MIDDLE:
            for(int i=0;i<100;++i){
              seqMonitor.add((i+numToAdd)/2,i,inputArgType);
              seqMonitor.verifyStructuralIntegrity();
            }
            verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(numToAdd/2).verifyMidPointInsertion(inputArgType,100).verifyAscending(numToAdd/2,numToAdd-(numToAdd/2));
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
          case MIDDLE:
            insertionIndex=numToAdd/2;
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
      Stream.Builder<Arguments> builder=Stream.builder();
      for(var checkedType:CheckedType.values()){
        for(var nestedType:NestedType.values()){
          for(var preModScenario:PreModScenario.values()){
            if(preModScenario!=PreModScenario.ModSeq&&(preModScenario.expectedException==null||(!nestedType.rootType&&checkedType.checked))){
              for(var monitoredFunctionGen:MonitoredFunctionGen.values()){
                if((checkedType.checked || monitoredFunctionGen.expectedException==null)&&(!nestedType.rootType || monitoredFunctionGen.appliesToRoot) &&(nestedType.rootType || monitoredFunctionGen.appliesToSubList)){
                  for(var seqContentsScenario:SequenceContentsScenario.values()){
                    builder.accept(Arguments.of(new BooleanSeqMonitor(StructType.ARRSEQ,nestedType,checkedType),preModScenario,monitoredFunctionGen,seqContentsScenario,FunctionCallType.Unboxed));
                    builder.accept(Arguments.of(new BooleanSeqMonitor(StructType.ARRSEQ,nestedType,checkedType),preModScenario,monitoredFunctionGen,seqContentsScenario,FunctionCallType.Boxed));
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
    public void testforEach_Consumer(){
      getforEach_ConsumerArgs().parallel().map(Arguments::get).forEach(args->{
          testforEach_ConsumerHelper((BooleanSeqMonitor)args[0],(PreModScenario)args[1],(MonitoredFunctionGen)args[2],(SequenceContentsScenario)args[3],(FunctionCallType)args[4]);
      });
    }
    private static void testforEach_ConsumerHelper
    (BooleanSeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredFunctionGen monitoredFunctionGen,SequenceContentsScenario seqContentsScenario,FunctionCallType functionCallType){
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
      var arr=((BooleanArrSeq)seqMonitor.root).arr;
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
                    if(itrType==ItrType.Itr || nestedType.forwardIteration){
                      for(var seqContentsScenario:SequenceContentsScenario.values()){
                        builder.accept(Arguments.of(new BooleanSeqMonitor(StructType.ARRSEQ,nestedType,checkedType),preModScenario,monitoredFunctionGen,itrType,seqContentsScenario,FunctionCallType.Unboxed));
                        builder.accept(Arguments.of(new BooleanSeqMonitor(StructType.ARRSEQ,nestedType,checkedType),preModScenario,monitoredFunctionGen,itrType,seqContentsScenario,FunctionCallType.Boxed));
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
          testItrforEachRemaining_ConsumerHelper((BooleanSeqMonitor)args[0],(PreModScenario)args[1],(MonitoredFunctionGen)args[2],(ItrType)args[3],(SequenceContentsScenario)args[4],(FunctionCallType)args[5]);
      });
    }
    private static void testItrforEachRemaining_ConsumerHelper
    (BooleanSeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredFunctionGen monitoredFunctionGen,ItrType itrType,SequenceContentsScenario seqContentsScenario,FunctionCallType functionCallType){
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
          //if(monitoredFunctionGen==MonitoredFunctionGen.ModItr || monitoredFunctionGen==MonitoredFunctionGen.ThrowModItr)
          //{
          //  //TODO figured out these special cases
          //  System.out.println("testItrforEachRemaining_Consumer("+seqMonitor+","+monitoredFunctionGen+","+seqContentsScenario+","+preModScenario+","+itrType+","+functionCallType+")");
          //  return;
          //}
          itrMonitor.verifyIteratorState();
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
        Assertions.assertThrows(preModScenario.expectedException,()->itrMonitor.forEachRemaining(monitoredConsumer,functionCallType));
        seqMonitor.verifyStructuralIntegrity();
        //if(monitoredFunctionGen==MonitoredFunctionGen.ModItr || monitoredFunctionGen==MonitoredFunctionGen.ThrowModItr)
        //{
        //  //TODO figured out these special cases
        //  System.out.println("testItrforEachRemaining_Consumer("+seqMonitor+","+monitoredFunctionGen+","+seqContentsScenario+","+preModScenario+","+itrType+","+functionCallType+")");
        //  return;
        //}
        itrMonitor.verifyIteratorState();
        var verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(numToAdd);
        switch(monitoredFunctionGen){
            case NoThrow:
              numExpectedIteratedValues=numToAdd;
              if(preModScenario==PreModScenario.ModSeq && seqMonitor.nestedType.forwardIteration){
                ++numExpectedIteratedValues;
              }
              verifyItr.verifyPostAlloc(preModScenario);
              break;
            case Throw:
              numExpectedIteratedValues=1;
              verifyItr.verifyPostAlloc(preModScenario);
              break;
            case ModSeq:
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
                case ModSeq:
                  numExpectedIteratedValues=numToAdd+1;
                  verifyItr.verifyIllegalAdd().verifyParentPostAlloc();
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
              verifyItr.verifyPostAlloc(preModScenario);
              for(int i=0;i<numExpectedIteratedValues;++i){
                verifyItr.verifyIllegalAdd();
              }
              break;
            case ThrowModSeq:
              if(preModScenario==PreModScenario.ModSeq){
                verifyItr.verifyIllegalAdd();
              }
              verifyItr.verifyPostAlloc(preModScenario);
              numExpectedIteratedValues=1;
              break;
            case ThrowModParent:
              switch(preModScenario){
                case ModSeq:
                  verifyItr.verifyIllegalAdd().verifyPostAlloc(PreModScenario.ModParent);
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
              numExpectedIteratedValues=1;
              break;
            case ThrowModRoot:
              verifyItr.verifyPostAlloc(preModScenario);
              verifyItr.verifyIllegalAdd();
              numExpectedIteratedValues=1;
              break;
            default:
              throw new Error("Unknown monitored function gen "+monitoredFunctionGen);
        }
      }
      Assertions.assertEquals(numExpectedIteratedValues,monitoredConsumer.encounteredValues.size());
      var arr=((BooleanArrSeq)seqMonitor.root).arr;
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
    static Stream<Arguments> getListItrpreviousIndex_void_And_ListItrnextIndex_voidArgs(){
      Stream.Builder<Arguments> builder=Stream.builder();
      for(var checkedType:CheckedType.values()){
        for(var nestedType:NestedType.values()){
          if(nestedType!=NestedType.STACK){
            builder.accept(Arguments.of(new BooleanSeqMonitor(StructType.ARRSEQ,nestedType,checkedType)));
          }
        }  
      }
      return builder.build();
    }
    @org.junit.jupiter.api.Test
    public void testListItrpreviousIndex_void_And_ListItrnextIndex_void(){
      getListItrpreviousIndex_void_And_ListItrnextIndex_voidArgs().parallel().map(Arguments::get).forEach(args->{
          testListItrpreviousIndex_void_And_ListItrnextIndex_voidHelper((BooleanSeqMonitor)args[0]);
      });
    }
    private static void testListItrpreviousIndex_void_And_ListItrnextIndex_voidHelper
    (BooleanSeqMonitor seqMonitor){
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
                      if(itrType==ItrType.ListItr || removeScenario.validWithForwardItr){
                        for(var nestedType:NestedType.values()){
                          if((itrType==ItrType.Itr || nestedType!=NestedType.STACK) && (!nestedType.rootType || preModScenario.appliesToRootItr)){
                            for(var sequenceLocation:SequenceLocation.values()){
                              if(sequenceLocation.expectedException==null && (sequenceLocation==SequenceLocation.BEGINNING || (sequenceContentsScenario.nonEmpty && nestedType!=NestedType.STACK))){
                                builder.accept(Arguments.of(new BooleanSeqMonitor(StructType.ARRSEQ,nestedType,checkedType),removeScenario,preModScenario,sequenceContentsScenario,itrType,sequenceLocation));
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
          testItrremove_voidHelper((BooleanSeqMonitor)args[0],(ItrRemoveScenario)args[1],(PreModScenario)args[2],(SequenceContentsScenario)args[3],(ItrType)args[4],(SequenceLocation)args[5]);
      });
    }
    private static void testItrremove_voidHelper
    (BooleanSeqMonitor seqMonitor,ItrRemoveScenario removeScenario,PreModScenario preModScenario,SequenceContentsScenario seqContentsScenario,ItrType itrType,SequenceLocation seqLocation){
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
                case MIDDLE:
                  for(int j=1;;++j){
                    if((j&1)!=0){
                      if(itrMonitor.hasNext()){
                        itrMonitor.iterateForward();
                      }
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
              case MIDDLE:
                verifyItr.verifyAscending(numToAdd/2).verifyIllegalAdd().verifyAscending(numToAdd/2,numToAdd-(numToAdd/2));
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
              case MIDDLE:
                verifyItr.verifyAscending(numToAdd/2).verifyAscending((numToAdd/2)+1,numToAdd-(numToAdd/2)-1);
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
          if(checkedType.checked || itrScenario.expectedException==null){
            for(var seqContentsScenario:SequenceContentsScenario.values()){
              if(seqContentsScenario.nonEmpty || itrScenario.validWithEmptySeq){
                for(var outputType:BooleanOutputTestArgType.values()){
                  if(itrScenario.preModScenario.appliesToRootItr){
                    builder.accept(Arguments.of(new BooleanSeqMonitor(StructType.ARRSEQ,NestedType.LIST,checkedType),itrScenario,seqContentsScenario,outputType));
                  }
                  builder.accept(Arguments.of(new BooleanSeqMonitor(StructType.ARRSEQ,NestedType.SUBLIST,checkedType),itrScenario,seqContentsScenario,outputType));
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
          testListItrprevious_voidHelper((BooleanSeqMonitor)args[0],(IterationScenario)args[1],(SequenceContentsScenario)args[2],(BooleanOutputTestArgType)args[3]);
      });
    }
    private static void testListItrprevious_voidHelper
    (BooleanSeqMonitor seqMonitor,IterationScenario itrScenario,SequenceContentsScenario seqContentsScenario,BooleanOutputTestArgType outputType){
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
      seqMonitor.illegalAdd(itrScenario.preModScenario);
      Assertions.assertThrows(itrScenario.expectedException,()->itrMonitor.iterateReverse());
      itrMonitor.verifyIteratorState();
      seqMonitor.verifyStructuralIntegrity();
      seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyPostAlloc(itrScenario.preModScenario);
    }
    static Stream<Arguments> getItrnext_voidArgs(){
      Stream.Builder<Arguments> builder=Stream.builder();
      for(var checkedType:CheckedType.values()){
        for(var itrScenario:IterationScenario.values()){
          if(checkedType.checked || itrScenario.expectedException==null){
            for(var seqContentsScenario:SequenceContentsScenario.values()){
              if(seqContentsScenario.nonEmpty || itrScenario.validWithEmptySeq){
                for(var itrType:ItrType.values()){
                  for(var nestedType:NestedType.values()){
                    if((nestedType!=NestedType.STACK || itrType!=ItrType.ListItr) && (itrScenario.preModScenario.appliesToRootItr || !nestedType.rootType)){
                      for(var outputType:BooleanOutputTestArgType.values()){
                        builder.accept(Arguments.of(new BooleanSeqMonitor(StructType.ARRSEQ,nestedType,checkedType),itrScenario,seqContentsScenario,itrType,outputType));
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
          testItrnext_voidHelper((BooleanSeqMonitor)args[0],(IterationScenario)args[1],(SequenceContentsScenario)args[2],(ItrType)args[3],(BooleanOutputTestArgType)args[4]);
      });
    }
    private static void testItrnext_voidHelper
    (BooleanSeqMonitor seqMonitor,IterationScenario itrScenario,SequenceContentsScenario seqContentsScenario,ItrType itrType,BooleanOutputTestArgType outputType){
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
      seqMonitor.illegalAdd(itrScenario.preModScenario);
      Assertions.assertThrows(itrScenario.expectedException,()->itrMonitor.iterateForward());
      itrMonitor.verifyIteratorState();
      seqMonitor.verifyStructuralIntegrity();
      seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyPostAlloc(itrScenario.preModScenario);
    }
    static Stream<Arguments> getListItrset_valArgs(){
      Stream.Builder<Arguments> builder=Stream.builder();
      for(var checkedType:CheckedType.values()){
        for(var listItrSetScenario:ListItrSetScenario.values()){
          if(checkedType.checked || listItrSetScenario.expectedException==null){
            for(var inputArgType:BooleanInputTestArgType.values()){
              if(listItrSetScenario.preModScenario.appliesToRootItr){
                builder.accept(Arguments.of(new BooleanSeqMonitor(StructType.ARRSEQ,NestedType.LIST,checkedType),listItrSetScenario,inputArgType));
              }
              builder.accept(Arguments.of(new BooleanSeqMonitor(StructType.ARRSEQ,NestedType.SUBLIST,checkedType),listItrSetScenario,inputArgType));
            }
          }
        }
      }
      return builder.build();
    }
    @org.junit.jupiter.api.Test
    public void testListItrset_val(){
      getListItrset_valArgs().parallel().map(Arguments::get).forEach(args->{
          testListItrset_valHelper((BooleanSeqMonitor)args[0],(ListItrSetScenario)args[1],(BooleanInputTestArgType)args[2]);
      });
    }
    private static void testListItrset_valHelper
    (BooleanSeqMonitor seqMonitor,ListItrSetScenario listItrSetScenario,BooleanInputTestArgType inputArgType){
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
                  for(var inputArgType:BooleanInputTestArgType.values()){
                    if(preModScenario.appliesToRootItr){
                      for(int initialCapacity=0;initialCapacity<=15;initialCapacity+=5){
                          builder.accept(Arguments.of(new BooleanSeqMonitor(StructType.ARRSEQ,NestedType.LIST,checkedType,initialCapacity),preModScenario,seqContentsScenario,seqLocation,inputArgType));
                      }
                    }
                    for(int rootPreAlloc=0;rootPreAlloc<=5;rootPreAlloc+=5){
                      for(int parentPreAlloc=0;parentPreAlloc<=5;parentPreAlloc+=5){
                        for(int parentPostAlloc=0;parentPostAlloc<=5;parentPostAlloc+=5){
                          for(int rootPostAlloc=0;rootPostAlloc<=5;rootPostAlloc+=5){
                            builder.accept(Arguments.of(new BooleanSeqMonitor(StructType.ARRSEQ,NestedType.SUBLIST,checkedType,OmniArray.DEFAULT_ARR_SEQ_CAP,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),preModScenario,seqContentsScenario,seqLocation,inputArgType));
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
          testListItradd_valHelper((BooleanSeqMonitor)args[0],(PreModScenario)args[1],(SequenceContentsScenario)args[2],(SequenceLocation)args[3],(BooleanInputTestArgType)args[4]);
      });
    }
    private static void testListItradd_valHelper
    (BooleanSeqMonitor seqMonitor,PreModScenario preModScenario,SequenceContentsScenario seqContentsScenario,SequenceLocation seqLocation,BooleanInputTestArgType inputArgType){
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
          case END:
            for(int i=0;i<100;++i){
              itrMonitor.add(i,inputArgType);
              itrMonitor.verifyIteratorState();
              seqMonitor.verifyStructuralIntegrity();
            }
            break;
          default:
            throw new Error("Unknown sequence locatio scenario "+seqLocation);
        }
        verifyItr=seqMonitor.verifyPreAlloc();
        switch(seqLocation){
          case BEGINNING:
            verifyItr.verifyDescending(inputArgType,100).verifyAscending(numToAdd);
            break;
          case MIDDLE:
            verifyItr.verifyAscending(numToAdd/2).verifyMidPointInsertion(inputArgType,100).verifyAscending(numToAdd/2,numToAdd-(numToAdd/2));
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
                        //these input values cannot potentially return true
                        break;
                        default:
                        if(seqContentsScenario.nonEmpty && seqLocation.expectedException==null){
                          continue;
                        }
                        //these values must necessarily return false
                      }
                      builder.accept(Arguments.of(new BooleanSeqMonitor(StructType.ARRSEQ,nestedType,checkedType),argType,queryCastType,seqLocation,seqContentsScenario,preModScenario));
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
                  builder.accept(Arguments.of(new BooleanSeqMonitor(StructType.ARRSEQ,nestedType,checkedType),preModScenario,monitoredComparatorGen,sequenceContentsScenario));
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
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var preModScenario:PreModScenario.values()){
          if(preModScenario.expectedException==null || (checkedType.checked && !nestedType.rootType && preModScenario!=PreModScenario.ModSeq)){
            for(var seqContentsScenario:SequenceContentsScenario.values()){
              builder.accept(Arguments.of(new BooleanSeqMonitor(StructType.ARRSEQ,nestedType,checkedType),preModScenario,seqContentsScenario));
            }
          }
        }
      }
    }
    return builder.build();
  }
  static Stream<Arguments> getStackpollpeekAndpop_voidArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var checkedType:CheckedType.values()){
      for(var outputArgType:BooleanOutputTestArgType.values()){
        builder.accept(Arguments.of(new BooleanSeqMonitor(StructType.ARRSEQ,NestedType.STACK,checkedType),outputArgType));
      }
    }
    return builder.build();
  }
  /*
  static Stream<Arguments> getBasicCollectionTestArguments(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(StructType structType:StructType.values()){
      builder.accept(Arguments.of(structType,0,CMEScenario.NoMod));
      builder.accept(Arguments.of(structType,100,CMEScenario.NoMod));
      if(structType==StructType.CHECKEDSUBLIST){
        builder.accept(Arguments.of(structType,0,CMEScenario.ModParent));
        builder.accept(Arguments.of(structType,100,CMEScenario.ModParent));
        builder.accept(Arguments.of(structType,0,CMEScenario.ModRoot));
        builder.accept(Arguments.of(structType,100,CMEScenario.ModRoot));
      }
    }
    return builder.build();
  }
  static void illegallyMod(CMEScenario modScenario,ConstructionArguments constructionArgs){
    switch(modScenario){
      case ModSeq:
        BooleanInputTestArgType.ARRAY_TYPE.callCollectionAdd(constructionArgs.seq,0);
        break;
      case ModParent:
        BooleanInputTestArgType.ARRAY_TYPE.callCollectionAdd(constructionArgs.parent,0);
        break;
      case ModRoot:
        BooleanInputTestArgType.ARRAY_TYPE.callCollectionAdd(constructionArgs.root,0);
        break;
      default:
    }
  }
  static Stream<Arguments> getToArrayArrayArguments()
  {
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var structType:StructType.values()){
      for(var preModScenario:CMEScenario.values()){
        if(preModScenario==CMEScenario.ModSeq || (preModScenario!=CMEScenario.NoMod && structType!=StructType.CHECKEDSUBLIST)){
          continue;
        }
        builder.accept(Arguments.of(0,0,structType,preModScenario));
        builder.accept(Arguments.of(0,5,structType,preModScenario));
        builder.accept(Arguments.of(3,5,structType,preModScenario));
        builder.accept(Arguments.of(5,5,structType,preModScenario));
        builder.accept(Arguments.of(8,5,structType,preModScenario));
      }
    }
    return builder.build();
  }
  @org.junit.jupiter.params.ParameterizedTest
  @org.junit.jupiter.params.provider.MethodSource("getToArrayArrayArguments")
  public void testtoArray_ObjectArray(int seqSize,int arrayLength,StructType structType,CMEScenario preModScenario)
  {
    ConstructionArguments constructionArgs=new ConstructionArguments(structType);
    for(int i=0;i<seqSize;++i)
    {
      BooleanInputTestArgType.ARRAY_TYPE.callCollectionAdd(constructionArgs.seq,i);
    }
    illegallyMod(preModScenario,constructionArgs);
    final Boolean[] paramArr=new Boolean[arrayLength];
    for(int i=0;i<arrayLength;++i)
    {
      paramArr[i]=TypeConversionUtil.convertToBoolean(100);
    }
    switch(preModScenario)
    {
      case ModParent:
      {
        Assertions.assertThrows(ConcurrentModificationException.class,()->constructionArgs.seq.toArray(paramArr));
        constructionArgs.verifyStructuralIntegrity(seqSize,seqSize,seqSize+1,seqSize+1);
        for(int i=0;i<arrayLength;++i)
        {
          Assertions.assertEquals(TypeConversionUtil.convertToBoolean(100),paramArr[i]);
        }
        break;
      }
      case ModRoot:
      {
        Assertions.assertThrows(ConcurrentModificationException.class,()->constructionArgs.seq.toArray(paramArr));
        constructionArgs.verifyStructuralIntegrity(seqSize,seqSize,seqSize,seqSize,seqSize+1,seqSize+1);
        for(int i=0;i<arrayLength;++i)
        {
          Assertions.assertEquals(TypeConversionUtil.convertToBoolean(100),paramArr[i]);
        }
        break;
      }
      default:
      {
        Object[] outputArr=constructionArgs.seq.toArray(paramArr);
        constructionArgs.verifyStructuralIntegrity(seqSize,seqSize);
        if(seqSize<arrayLength)
        {
          Assertions.assertSame(outputArr,paramArr);
          Assertions.assertNull(outputArr[seqSize]);
          for(int i=seqSize+1;i<arrayLength;++i)
          {
            Assertions.assertEquals(TypeConversionUtil.convertToBoolean(100),outputArr[i]);
          }
        }
        else if(seqSize==arrayLength)
        {
          Assertions.assertSame(outputArr,paramArr);
        }
        else
        {
          Assertions.assertNotSame(outputArr,paramArr);
          for(int i=0;i<arrayLength;++i)
          {
            Assertions.assertEquals(TypeConversionUtil.convertToBoolean(100),paramArr[i]);
          }
          Assertions.assertEquals(seqSize,outputArr.length);
        }
        var itr=constructionArgs.seq.iterator();
        for(int i=0;i<seqSize;++i)
        {
          Assertions.assertEquals(itr.next(),outputArr[i]);
        }
      }
    }
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,BooleanInputTestArgType.ARRAY_TYPE,seqSize);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    if(preModScenario==CMEScenario.ModParent){offset=constructionArgs.verifyIndex(offset,BooleanInputTestArgType.ARRAY_TYPE,0);}
    offset=constructionArgs.verifyRootPostAlloc(offset);
    if(preModScenario==CMEScenario.ModRoot){offset=constructionArgs.verifyIndex(offset,BooleanInputTestArgType.ARRAY_TYPE,0);}
  }
  static Stream<Arguments> getHashCodeTestArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(StructType structType:StructType.values()){
      switch(structType)
      {
        case CHECKEDSUBLIST:
          builder.accept(Arguments.of(structType,0,ObjModScenario.NoMod,CMEScenario.ModParent));
          builder.accept(Arguments.of(structType,100,ObjModScenario.NoMod,CMEScenario.ModParent));
          builder.accept(Arguments.of(structType,0,ObjModScenario.NoMod,CMEScenario.ModRoot));
          builder.accept(Arguments.of(structType,100,ObjModScenario.NoMod,CMEScenario.ModRoot));
        case CHECKEDSTACK:
        case CHECKEDLIST:
        default:
          builder.accept(Arguments.of(structType,0,ObjModScenario.NoMod,CMEScenario.NoMod));
          builder.accept(Arguments.of(structType,100,ObjModScenario.NoMod,CMEScenario.NoMod));
      }
    }
    return builder.build();
  }
  private static ModCheckTestData initializeForBasicModCheckableTest(StructType structType,int numToAdd,ObjModScenario objModScenario,CMEScenario preModScenario){
    ConstructionArguments constructionArgs=new ConstructionArguments(structType);
    ModCheckTestObject modCheckTestObject=null;
    if(objModScenario==ObjModScenario.NoMod){
       for(int i=0;i<numToAdd;++i){
          BooleanInputTestArgType.ARRAY_TYPE.callCollectionAdd(constructionArgs.seq,i);
        }
    }else{
      switch(objModScenario){
        case ModSeq:
          modCheckTestObject=new ModCheckTestObject.Modding((OmniCollection.OfRef<?>)constructionArgs.seq);
          break;
        case ModSeqThrow:
          modCheckTestObject=new ModCheckTestObject.ModdingAndThrowing((OmniCollection.OfRef<?>)constructionArgs.seq);
          break;
        case ModParent:
          modCheckTestObject=new ModCheckTestObject.Modding((OmniCollection.OfRef<?>)constructionArgs.parent);
          break;
        case ModParentThrow:
          modCheckTestObject=new ModCheckTestObject.ModdingAndThrowing((OmniCollection.OfRef<?>)constructionArgs.parent);
          break;
        case ModRoot:
          modCheckTestObject=new ModCheckTestObject.Modding((OmniCollection.OfRef<?>)constructionArgs.root);
          break;
        case ModRootThrow:
          modCheckTestObject=new ModCheckTestObject.ModdingAndThrowing((OmniCollection.OfRef<?>)constructionArgs.root);
          break;
        default:
          modCheckTestObject=new ModCheckTestObject();
      }
      for(int i=0;i<numToAdd;++i){
        ((OmniCollection.OfRef)constructionArgs.seq).add(modCheckTestObject);
      }
    }
    illegallyMod(preModScenario,constructionArgs);
    return new ModCheckTestData(modCheckTestObject,constructionArgs);
  }
  @org.junit.jupiter.params.ParameterizedTest
  @org.junit.jupiter.params.provider.MethodSource("getHashCodeTestArgs")
  public void testhashCode_void(StructType structType,int numToAdd,ObjModScenario objModScenario,CMEScenario preModScenario){
    ModCheckTestData modCheckTestData=initializeForBasicModCheckableTest(structType,numToAdd,objModScenario,preModScenario);
    ConstructionArguments constructionArgs=modCheckTestData.constructionArgs;
      if(preModScenario==CMEScenario.NoMod){
        int expectedHash=1;
        switch(structType){
          case CHECKEDSTACK:
          case UNCHECKEDSTACK:
            for(int i=numToAdd;--i>=0;)
            {
              expectedHash=(expectedHash*31)+Objects.hashCode(TypeConversionUtil.convertToboolean(i));
            }
            break;
          default:
            for(int i=0;i<numToAdd;++i)
            {
              expectedHash=(expectedHash*31)+Objects.hashCode(TypeConversionUtil.convertToboolean(i));
            }
        }
        Assertions.assertEquals(expectedHash,constructionArgs.seq.hashCode());
        constructionArgs.verifyStructuralIntegrity(numToAdd,numToAdd);
      }
      else
      {
        Assertions.assertThrows(ConcurrentModificationException.class,()->constructionArgs.seq.hashCode());
        if(preModScenario==CMEScenario.ModParent)
        {
          constructionArgs.verifyStructuralIntegrity(numToAdd,numToAdd,numToAdd+1,numToAdd+1);
        }
        else
        {
          constructionArgs.verifyStructuralIntegrity(numToAdd,numToAdd,numToAdd,numToAdd,numToAdd+1,numToAdd+1);
        }
      }
      int offset=constructionArgs.verifyPreAlloc();
      offset=constructionArgs.verifyAscending(offset,BooleanInputTestArgType.ARRAY_TYPE,numToAdd);
      offset=constructionArgs.verifyParentPostAlloc(offset);
      if(preModScenario==CMEScenario.ModParent){offset=constructionArgs.verifyIndex(offset,BooleanInputTestArgType.ARRAY_TYPE,0);}
      offset=constructionArgs.verifyRootPostAlloc(offset);
      if(preModScenario==CMEScenario.ModRoot){constructionArgs.verifyIndex(offset,BooleanInputTestArgType.ARRAY_TYPE,0);}
  }
  */
}
