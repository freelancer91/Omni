package omni.impl.seq;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import java.util.function.IntFunction;
import java.util.function.Consumer;
import omni.impl.CharInputTestArgType;
import omni.impl.CharOutputTestArgType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.api.Test;
import java.util.stream.Stream;
import java.util.Objects;
import omni.function.CharConsumer;
import java.util.ConcurrentModificationException;
import omni.util.OmniArray;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.api.OmniCollection;
import omni.api.OmniListIterator;
import omni.api.OmniIterator;
import omni.impl.FunctionCallType;
import omni.impl.QueryCastType;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import omni.impl.seq.CharSeqMonitor.NestedType;
import omni.impl.seq.CharSeqMonitor.StructType;
import omni.impl.seq.CharSeqMonitor.CheckedType;
import omni.impl.seq.CharSeqMonitor.PreModScenario;
import omni.impl.seq.CharSeqMonitor.SequenceLocation;
import omni.impl.seq.CharSeqMonitor.SequenceContentsScenario;
import omni.impl.seq.CharSeqMonitor.ListItrSetScenario;
import omni.impl.seq.CharSeqMonitor.ItrType;
import omni.impl.seq.CharSeqMonitor.IterationScenario;
import omni.impl.seq.CharSeqMonitor.ItrRemoveScenario;
import omni.impl.seq.CharSeqMonitor.MonitoredConsumerGen;
import omni.impl.seq.CharSeqMonitor.SequenceVerificationItr;
import omni.impl.seq.CharSeqMonitor.QueryTester;
//TODO replace this with a custom collection
import java.util.ArrayList;
@SuppressWarnings({"rawtypes","unchecked"})
@Execution(ExecutionMode.CONCURRENT)
public class CharArrSeqTest{
  static Stream<Arguments> getConstructor_intArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var nestedType:NestedType.values()){
      if(nestedType==NestedType.SUBLIST){
        continue;
      }
      for(var checkedType:CheckedType.values()){
        for(int initialCapacity=0;initialCapacity<=15;initialCapacity+=5){
          builder.accept(Arguments.of(nestedType,checkedType,initialCapacity));
        }
      }
    }
    return builder.build().parallel();
  }
  @ParameterizedTest
  @MethodSource("getConstructor_intArgs")
  public void testConstructor_int(NestedType nestedType,CheckedType checkedType,int initialCapacity){
    CharArrSeq seq;
    if(checkedType.checked){
      Assertions.assertEquals(0,nestedType==NestedType.LIST?FieldAccessor.CharArrSeq.CheckedList.modCount(seq=new CharArrSeq.CheckedList(initialCapacity)):FieldAccessor.CharArrSeq.CheckedStack.modCount(seq=new CharArrSeq.CheckedStack(initialCapacity)));
    }else{
      seq=nestedType==NestedType.LIST?new CharArrSeq.UncheckedList(initialCapacity):new CharArrSeq.UncheckedStack(initialCapacity);
    }
    Assertions.assertEquals(0,seq.size);
    switch(initialCapacity){
      case 0:
        Assertions.assertNull(seq.arr);
        break;
      case OmniArray.DEFAULT_ARR_SEQ_CAP:
        Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,seq.arr);
        break;
      default:
        Assertions.assertEquals(initialCapacity,seq.arr.length);
    }
  }
  static Stream<Arguments> getListItradd_valArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var checkedType:CheckedType.values()){
      for(var preModScenario:PreModScenario.values()){
        if(!checkedType.checked && preModScenario.expectedException!=null){
          continue;
        }
        for(var seqContentsScenario:SequenceContentsScenario.values()){
          for(var seqLocation:SequenceLocation.values()){
            if(seqLocation.expectedException!=null){
              continue;
            }
            for(var inputArgType:CharInputTestArgType.values()){
              if(preModScenario.appliesToRootItr){
                for(int initialCapacity=0;initialCapacity<=15;initialCapacity+=5){
                    builder.accept(Arguments.of(new CharSeqMonitor(StructType.ARRSEQ,NestedType.LIST,checkedType,initialCapacity),preModScenario,seqContentsScenario,seqLocation,inputArgType));
                }
              }
              for(int rootPreAlloc=0;rootPreAlloc<=5;rootPreAlloc+=5){
                for(int parentPreAlloc=0;parentPreAlloc<=5;parentPreAlloc+=5){
                  for(int parentPostAlloc=0;parentPostAlloc<=5;parentPostAlloc+=5){
                    for(int rootPostAlloc=0;rootPostAlloc<=5;rootPostAlloc+=5){
                      builder.accept(Arguments.of(new CharSeqMonitor(StructType.ARRSEQ,NestedType.SUBLIST,checkedType,OmniArray.DEFAULT_ARR_SEQ_CAP,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),preModScenario,seqContentsScenario,seqLocation,inputArgType));
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    return builder.build().parallel();
  }
  @ParameterizedTest
  @MethodSource("getListItradd_valArgs")
  public void testListItradd_val(CharSeqMonitor seqMonitor,PreModScenario preModScenario,SequenceContentsScenario seqContentsScenario,SequenceLocation seqLocation,CharInputTestArgType inputArgType){
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
  static Stream<Arguments> getListItrset_valArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var checkedType:CheckedType.values()){
      for(var listItrSetScenario:ListItrSetScenario.values()){
        if(!checkedType.checked && listItrSetScenario.expectedException!=null){
          continue;
        }
        for(var inputArgType:CharInputTestArgType.values()){
          if(listItrSetScenario.preModScenario.appliesToRootItr){
            builder.accept(Arguments.of(new CharSeqMonitor(StructType.ARRSEQ,NestedType.LIST,checkedType),listItrSetScenario,inputArgType));
          }
          builder.accept(Arguments.of(new CharSeqMonitor(StructType.ARRSEQ,NestedType.SUBLIST,checkedType),listItrSetScenario,inputArgType));
        }
      }
    }
    return builder.build().parallel();
  }
  @ParameterizedTest
  @MethodSource("getListItrset_valArgs")
  public void testListItrset_val(CharSeqMonitor seqMonitor,ListItrSetScenario listItrSetScenario,CharInputTestArgType inputArgType){
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
        if(!checkedType.checked && itrScenario.expectedException!=null){
          continue;
        }
        for(var seqContentsScenario:SequenceContentsScenario.values()){
          if(!seqContentsScenario.nonEmpty && !itrScenario.validWithEmptySeq){
            continue;
          }
          for(var itrType:ItrType.values()){
            for(var nestedType:NestedType.values()){
              if((nestedType==NestedType.STACK && itrType==ItrType.ListItr) || (!itrScenario.preModScenario.appliesToRootItr && nestedType.rootType)){
                continue;
              }
              for(var outputType:CharOutputTestArgType.values()){
                builder.accept(Arguments.of(new CharSeqMonitor(StructType.ARRSEQ,nestedType,checkedType),itrScenario,seqContentsScenario,itrType,outputType));
              }
            }
          }
        }
      }
    }
    return builder.build().parallel();
  }
  @ParameterizedTest
  @MethodSource("getItrnext_voidArgs")
  public void testItrnext_void(CharSeqMonitor seqMonitor,IterationScenario itrScenario,SequenceContentsScenario seqContentsScenario,ItrType itrType,CharOutputTestArgType outputType){
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
  static Stream<Arguments> getListItrprevious_voidArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var checkedType:CheckedType.values()){
      for(var itrScenario:IterationScenario.values()){
        if(!checkedType.checked && itrScenario.expectedException!=null){
          continue;
        }
        for(var seqContentsScenario:SequenceContentsScenario.values()){
          if(!seqContentsScenario.nonEmpty && !itrScenario.validWithEmptySeq){
            continue;
          }
          for(var outputType:CharOutputTestArgType.values()){
            if(itrScenario.preModScenario.appliesToRootItr){
              builder.accept(Arguments.of(new CharSeqMonitor(StructType.ARRSEQ,NestedType.LIST,checkedType),itrScenario,seqContentsScenario,outputType));
            }
            builder.accept(Arguments.of(new CharSeqMonitor(StructType.ARRSEQ,NestedType.SUBLIST,checkedType),itrScenario,seqContentsScenario,outputType));
          }
        }
      }
    }
    return builder.build().parallel();
  }
  @ParameterizedTest
  @MethodSource("getListItrprevious_voidArgs")
  public void testListItrprevious_void(CharSeqMonitor seqMonitor,IterationScenario itrScenario,SequenceContentsScenario seqContentsScenario,CharOutputTestArgType outputType){
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
  static Stream<Arguments> getItrremove_voidArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var checkedType:CheckedType.values()){
      for(var removeScenario:ItrRemoveScenario.values()){
        if(!checkedType.checked && removeScenario.expectedException!=null){
          continue;
        }
        for(var sequenceContentsScenario:SequenceContentsScenario.values()){
            if(!sequenceContentsScenario.nonEmpty && !removeScenario.validWithEmptySeq){
              continue;
            }
          for(var preModScenario:PreModScenario.values()){
            if(!checkedType.checked && preModScenario.expectedException!=null){
              continue;
            }
            for(var itrType:ItrType.values()){
              if(itrType==ItrType.Itr && !removeScenario.validWithForwardItr){
                continue;
              }
              for(var nestedType:NestedType.values()){
                if((itrType==ItrType.ListItr && nestedType==NestedType.STACK) || (nestedType.rootType && !preModScenario.appliesToRootItr)){
                  continue;
                }
                for(var sequenceLocation:SequenceLocation.values()){
                  if(sequenceLocation.expectedException!=null || (sequenceLocation!=SequenceLocation.BEGINNING && (!sequenceContentsScenario.nonEmpty || nestedType==NestedType.STACK))){
                    continue;
                  }
                  builder.accept(Arguments.of(new CharSeqMonitor(StructType.ARRSEQ,nestedType,checkedType),removeScenario,preModScenario,sequenceContentsScenario,itrType,sequenceLocation));
                }
              }
            }
          }
        }
      }
    }
    return builder.build().parallel();
  }
  @ParameterizedTest
  @MethodSource("getItrremove_voidArgs")
  public void testItrremove_void(CharSeqMonitor seqMonitor,ItrRemoveScenario removeScenario,PreModScenario preModScenario,SequenceContentsScenario seqContentsScenario,ItrType itrType,SequenceLocation seqLocation){
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
  static Stream<Arguments> getListItrpreviousIndex_void_And_ListItrnextIndex_voidArgs()
  {
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var checkedType:CheckedType.values()){
      for(var nestedType:NestedType.values()){
        if(nestedType==NestedType.STACK){
          continue;
        }
        builder.accept(Arguments.of(new CharSeqMonitor(StructType.ARRSEQ,nestedType,checkedType)));
      }  
    }
    return builder.build().parallel();
  }
  @ParameterizedTest
  @MethodSource("getListItrpreviousIndex_void_And_ListItrnextIndex_voidArgs")
  public void testListItrpreviousIndex_void_And_ListItrnextIndex_void(CharSeqMonitor seqMonitor){
    int numToAdd=100;
    for(int i=0;i<numToAdd;++i)
    {
      seqMonitor.add(i);
    }
    var itrMonitor=seqMonitor.getListItrMonitor();
    for(int i=0;i<numToAdd;++i)
    {
      Assertions.assertEquals(i,itrMonitor.nextIndex());
      Assertions.assertEquals(i-1,itrMonitor.previousIndex());
      itrMonitor.verifyIteratorState();
      seqMonitor.verifyStructuralIntegrity();
      itrMonitor.iterateForward();
    }
    for(int i=numToAdd;i>0;)
    {
      Assertions.assertEquals(i,itrMonitor.nextIndex());
      Assertions.assertEquals(--i,itrMonitor.previousIndex());
      itrMonitor.verifyIteratorState();
      seqMonitor.verifyStructuralIntegrity();
      itrMonitor.iterateReverse();
    }
    seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyPostAlloc();
  }
  static Stream<Arguments> getItrforEachRemaining_ConsumerArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var checkedType:CheckedType.values()){
      for(var nestedType:NestedType.values()){
        for(var preModScenario:PreModScenario.values()){
          if((!checkedType.checked && preModScenario.expectedException!=null)){
            continue;
          }
          for(var monitoredConsumerGen:MonitoredConsumerGen.values()){
            if((monitoredConsumerGen.expectedException!=null && !checkedType.checked) || (nestedType.rootType && (!preModScenario.appliesToRootItr||!monitoredConsumerGen.appliesToRootItr))){
              continue;
            }
            for(var itrType:ItrType.values()){
              if(itrType==ItrType.ListItr && !nestedType.forwardIteration){
                continue;
              }
              for(var seqContentsScenario:SequenceContentsScenario.values()){
                builder.accept(Arguments.of(new CharSeqMonitor(StructType.ARRSEQ,nestedType,checkedType),preModScenario,monitoredConsumerGen,itrType,seqContentsScenario,FunctionCallType.Unboxed));
                builder.accept(Arguments.of(new CharSeqMonitor(StructType.ARRSEQ,nestedType,checkedType),preModScenario,monitoredConsumerGen,itrType,seqContentsScenario,FunctionCallType.Boxed));
              }
            }
          }  
        }
      }
    }
    return builder.build().parallel();
  }
  @ParameterizedTest
  @MethodSource("getItrforEachRemaining_ConsumerArgs")
  public void testItrforEachRemaining_Consumer(CharSeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredConsumerGen monitoredConsumerGen,ItrType itrType,SequenceContentsScenario seqContentsScenario,FunctionCallType functionCallType){
    int numToAdd=seqContentsScenario.nonEmpty?100:0;
    for(int i=0;i<numToAdd;++i){
      seqMonitor.add(i);
    }
    var itrMonitor=seqMonitor.getItrMonitor(itrType);
    seqMonitor.illegalAdd(preModScenario);
    var monitoredConsumer=monitoredConsumerGen.getMonitoredConsumer(itrMonitor);
    int numExpectedIteratedValues;
    if(preModScenario.expectedException==null || (!seqContentsScenario.nonEmpty && (preModScenario!=PreModScenario.ModSeq || !seqMonitor.nestedType.forwardIteration))){
      if(monitoredConsumerGen.expectedException==null || (!seqContentsScenario.nonEmpty && (preModScenario!=PreModScenario.ModSeq || !seqMonitor.nestedType.forwardIteration))){
        itrMonitor.forEachRemaining(monitoredConsumer,functionCallType);
        itrMonitor.verifyIteratorState();
        seqMonitor.verifyStructuralIntegrity();
        seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyPostAlloc(preModScenario);
        numExpectedIteratedValues=numToAdd;
      }else{
        Assertions.assertThrows(monitoredConsumerGen.expectedException,()->itrMonitor.forEachRemaining(monitoredConsumer,functionCallType));
        seqMonitor.verifyStructuralIntegrity();
        //if(monitoredConsumerGen==MonitoredConsumerGen.ModItr || monitoredConsumerGen==MonitoredConsumerGen.ThrowModItr)
        //{
        //  //TODO figured out these special cases
        //  System.out.println("testItrforEachRemaining_Consumer("+seqMonitor+","+monitoredConsumerGen+","+seqContentsScenario+","+preModScenario+","+itrType+","+functionCallType+")");
        //  return;
        //}
        itrMonitor.verifyIteratorState();
        var verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(numToAdd);
        switch(monitoredConsumerGen){
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
            throw new Error("Unknown monitored consumer gen "+monitoredConsumerGen);
        }
      }
    }else{
      Assertions.assertThrows(preModScenario.expectedException,()->itrMonitor.forEachRemaining(monitoredConsumer,functionCallType));
      seqMonitor.verifyStructuralIntegrity();
      //if(monitoredConsumerGen==MonitoredConsumerGen.ModItr || monitoredConsumerGen==MonitoredConsumerGen.ThrowModItr)
      //{
      //  //TODO figured out these special cases
      //  System.out.println("testItrforEachRemaining_Consumer("+seqMonitor+","+monitoredConsumerGen+","+seqContentsScenario+","+preModScenario+","+itrType+","+functionCallType+")");
      //  return;
      //}
      itrMonitor.verifyIteratorState();
      var verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(numToAdd);
      switch(monitoredConsumerGen){
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
            throw new Error("Unknown monitored consumer gen "+monitoredConsumerGen);
      }
    }
    Assertions.assertEquals(numExpectedIteratedValues,monitoredConsumer.encounteredValues.size());
    var arr=((CharArrSeq)seqMonitor.root).arr;
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
  static Stream<Arguments> getforEach_ConsumerArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var checkedType:CheckedType.values()){
      for(var nestedType:NestedType.values()){
        for(var preModScenario:PreModScenario.values()){
          if(preModScenario==PreModScenario.ModSeq||(preModScenario.expectedException!=null&&(nestedType.rootType||!checkedType.checked))){
            continue;
          }
          for(var monitoredConsumerGen:MonitoredConsumerGen.values()){
            if((!checkedType.checked && monitoredConsumerGen.expectedException!=null)||(nestedType.rootType && !monitoredConsumerGen.appliesToRoot) ||(!nestedType.rootType && !monitoredConsumerGen.appliesToSubList)){
              continue;
            }
            for(var seqContentsScenario:SequenceContentsScenario.values()){
              builder.accept(Arguments.of(new CharSeqMonitor(StructType.ARRSEQ,nestedType,checkedType),preModScenario,monitoredConsumerGen,seqContentsScenario,FunctionCallType.Unboxed));
              builder.accept(Arguments.of(new CharSeqMonitor(StructType.ARRSEQ,nestedType,checkedType),preModScenario,monitoredConsumerGen,seqContentsScenario,FunctionCallType.Boxed));
            }
          }  
        }
      }
    }
    return builder.build().parallel();
  }
  @ParameterizedTest
  @MethodSource("getforEach_ConsumerArgs")
  public void testforEach_ConsumerArgs(CharSeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredConsumerGen monitoredConsumerGen,SequenceContentsScenario seqContentsScenario,FunctionCallType functionCallType){
    int numToAdd=seqContentsScenario.nonEmpty?100:0;
    for(int i=0;i<numToAdd;++i){
      seqMonitor.add(i);
    }
    seqMonitor.illegalAdd(preModScenario);
    var monitoredConsumer=monitoredConsumerGen.getMonitoredConsumer(seqMonitor);
    int numExpectedIteratedValues;
    if(preModScenario.expectedException==null){
      if(monitoredConsumerGen.expectedException==null || !seqContentsScenario.nonEmpty){
        seqMonitor.forEach(monitoredConsumer,functionCallType);
        seqMonitor.verifyStructuralIntegrity();
        seqMonitor.verifyPreAlloc().verifyAscending(numToAdd).verifyPostAlloc(preModScenario);
        numExpectedIteratedValues=numToAdd;
      }else{
        Assertions.assertThrows(monitoredConsumerGen.expectedException,()->seqMonitor.forEach(monitoredConsumer,functionCallType));
        seqMonitor.verifyStructuralIntegrity();
        var verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(numToAdd);
        switch(monitoredConsumerGen){
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
            throw new Error("Unknown monitored consumer gen "+monitoredConsumerGen);
        }
      }
    }else{
      Assertions.assertThrows(preModScenario.expectedException,()->seqMonitor.forEach(monitoredConsumer,functionCallType));
      seqMonitor.verifyStructuralIntegrity();
      var verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(numToAdd);
      switch(monitoredConsumerGen){
          case NoThrow:
            numExpectedIteratedValues=numToAdd;
            verifyItr.verifyPostAlloc(preModScenario);
            break;
          case Throw:
            numExpectedIteratedValues=numToAdd==0?0:1;
            verifyItr.verifyPostAlloc(preModScenario);
            break;
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
            throw new Error("Unknown monitored consumer gen "+monitoredConsumerGen);
      }
    }
    Assertions.assertEquals(numExpectedIteratedValues,monitoredConsumer.encounteredValues.size());
    var arr=((CharArrSeq)seqMonitor.root).arr;
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
  static Stream<Arguments> getListadd_int_valArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var nestedType:NestedType.values()){
      if(!nestedType.forwardIteration){
        continue;
      }
      for(var checkedType:CheckedType.values()){
        for(var seqLocation:SequenceLocation.values()){
          if(!checkedType.checked && seqLocation.expectedException!=null){
            continue;
          }
          for(var preModScenario:PreModScenario.values()){
            if(preModScenario==PreModScenario.ModSeq || (preModScenario.expectedException!=null && !checkedType.checked) || (nestedType.rootType && preModScenario!=PreModScenario.NoMod)){
              continue;
            }
            for(var seqContentsScenario:SequenceContentsScenario.values()){
              if(!seqContentsScenario.nonEmpty && !seqLocation.validForEmpty){
                continue;
              }
              for(var inputArgType:CharInputTestArgType.values()){
                for(int initialCapacity=0;initialCapacity<=15;initialCapacity+=5){
                  switch(nestedType){
                    case LIST:
                      builder.accept(Arguments.of(new CharSeqMonitor(StructType.ARRSEQ,nestedType,checkedType,initialCapacity),inputArgType,seqLocation,preModScenario,seqContentsScenario));
                      break;
                    case SUBLIST:
                      for(int rootPreAlloc=0;rootPreAlloc<=5;rootPreAlloc+=5){
                        for(int parentPreAlloc=0;parentPreAlloc<=5;parentPreAlloc+=5){
                          for(int parentPostAlloc=0;parentPostAlloc<=5;parentPostAlloc+=5){
                            for(int rootPostAlloc=0;rootPostAlloc<=5;rootPostAlloc+=5){
                              builder.accept(Arguments.of(new CharSeqMonitor(StructType.ARRSEQ,nestedType,checkedType,initialCapacity,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),inputArgType,seqLocation,preModScenario,seqContentsScenario));
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
    return builder.build().parallel();
  }
  @ParameterizedTest
  @MethodSource("getListadd_int_valArgs")
  public void testListadd_int_val(CharSeqMonitor seqMonitor,CharInputTestArgType inputArgType,SequenceLocation seqLocation,PreModScenario preModScenario,SequenceContentsScenario seqContentsScenario){
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
  static Stream<Arguments> getListput_int_valArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var nestedType:NestedType.values()){
      if(!nestedType.forwardIteration){
        continue;
      }
      for(var checkedType:CheckedType.values()){
        for(var seqLocation:SequenceLocation.values()){
          if((!checkedType.checked && seqLocation.expectedException!=null) || !seqLocation.validForEmpty){
            continue;
          }
          for(var preModScenario:PreModScenario.values()){
            if(preModScenario==PreModScenario.ModSeq || (preModScenario.expectedException!=null && !checkedType.checked) || (nestedType.rootType && preModScenario!=PreModScenario.NoMod)){
              continue;
            }
            for(var seqContentsScenario:SequenceContentsScenario.values()){
              if(!seqContentsScenario.nonEmpty && seqLocation.expectedException==null){
                continue;
              }
              for(var inputArgType:CharInputTestArgType.values()){
                builder.accept(Arguments.of(new CharSeqMonitor(StructType.ARRSEQ,nestedType,checkedType),inputArgType,seqLocation,preModScenario,seqContentsScenario));
              }
            }
          }
        }
      }
    }
    return builder.build().parallel();
  }
  @ParameterizedTest
  @MethodSource("getListput_int_valArgs")
  public void testListput_int_val(CharSeqMonitor seqMonitor,CharInputTestArgType inputArgType,SequenceLocation seqLocation,PreModScenario preModScenario,SequenceContentsScenario seqContentsScenario){
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
  static Stream<Arguments> getadd_valArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        for(var preModScenario:PreModScenario.values()){
          if(preModScenario==PreModScenario.ModSeq || (preModScenario.expectedException!=null && !checkedType.checked) || (nestedType.rootType && preModScenario!=PreModScenario.NoMod)){
            continue;
          }
          for(var seqContentsScenario:SequenceContentsScenario.values()){
            for(var inputArgType:CharInputTestArgType.values()){
              for(int initialCapacity=0;initialCapacity<=15;initialCapacity+=5){
                switch(nestedType){
                  case LIST:
                  case STACK:
                    builder.accept(Arguments.of(new CharSeqMonitor(StructType.ARRSEQ,nestedType,checkedType,initialCapacity),inputArgType,preModScenario,seqContentsScenario));
                    break;
                  case SUBLIST:
                    for(int rootPreAlloc=0;rootPreAlloc<=5;rootPreAlloc+=5){
                      for(int parentPreAlloc=0;parentPreAlloc<=5;parentPreAlloc+=5){
                        for(int parentPostAlloc=0;parentPostAlloc<=5;parentPostAlloc+=5){
                          for(int rootPostAlloc=0;rootPostAlloc<=5;rootPostAlloc+=5){
                            builder.accept(Arguments.of(new CharSeqMonitor(StructType.ARRSEQ,nestedType,checkedType,initialCapacity,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),inputArgType,preModScenario,seqContentsScenario));
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
    return builder.build().parallel();
  }
  @ParameterizedTest
  @MethodSource("getadd_valArgs")
  public void testadd_val(CharSeqMonitor seqMonitor,CharInputTestArgType inputArgType,PreModScenario preModScenario,SequenceContentsScenario sequenceContentsScenario){
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
  static Stream<Arguments> getStackpush_valArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var checkedType:CheckedType.values()){
      for(var inputArgType:CharInputTestArgType.values()){
        for(int initialCapacity=0;initialCapacity<=15;initialCapacity+=5){
          builder.accept(Arguments.of(new CharSeqMonitor(StructType.ARRSEQ,NestedType.STACK,checkedType,initialCapacity),inputArgType));
        }
      }
    }
    return builder.build().parallel();
  }
  @ParameterizedTest
  @MethodSource("getStackpush_valArgs")
  public void testStackpush_val(CharSeqMonitor seqMonitor,CharInputTestArgType inputArgType){
    for(int i=0;i<100;++i){
      seqMonitor.push(i,inputArgType);
      seqMonitor.verifyStructuralIntegrity();
    }
    seqMonitor.verifyPreAlloc().verifyAscending(inputArgType,100);
  }
  static void buildQueryArguments(Stream.Builder<Arguments> builder,NestedType nestedType){
    for(var checkedType:CheckedType.values()){
      for(var preModScenario:PreModScenario.values()){
        if(preModScenario==PreModScenario.ModSeq || (preModScenario.expectedException!=null && (!checkedType.checked || nestedType.rootType))){
          continue;
        }
        for(var seqLocation:SequenceLocation.values()){
          if(seqLocation==SequenceLocation.IOBLO){
            continue;
          }
          for(var seqContentsScenario:SequenceContentsScenario.values()){
            if(seqLocation!=SequenceLocation.IOBHI && !seqContentsScenario.nonEmpty){
              continue;
            }
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
                  if(seqContentsScenario.nonEmpty && seqLocation.expectedException==null){
                    continue;
                  }
                  //these values must necessarily return false
                }
                builder.accept(Arguments.of(new CharSeqMonitor(StructType.ARRSEQ,nestedType,checkedType),argType,queryCastType,seqLocation,seqContentsScenario,preModScenario));
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
    return builder.build().parallel();
  }
  static Stream<Arguments> getQueryListArguments(){
    Stream.Builder<Arguments> builder=Stream.builder();
    buildQueryArguments(builder,NestedType.LIST);
    buildQueryArguments(builder,NestedType.SUBLIST);
    return builder.build().parallel();
  }
  static Stream<Arguments> getQueryCollectionArguments(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var nestedType:NestedType.values()){
      buildQueryArguments(builder,nestedType);
    }
    return builder.build().parallel();
  }
  @ParameterizedTest
  @MethodSource("getQueryCollectionArguments")
  public void testcontains_val(CharSeqMonitor seqMonitor,QueryTester argType,QueryCastType queryCastType,SequenceLocation seqLocation,SequenceContentsScenario seqContentsScenario,PreModScenario preModScenario 
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
  /*
  static enum QueryCast{
    AsIs,
    ToBoxed,
    ToObject;
  }
  static void buildQueryArguments(Stream.Builder<Arguments> builder,StructType structType){
    for(QueryTestInputType argType:QueryTestInputType.values()){
        for(QueryCast queryCast:QueryCast.values()){
          if(structType==StructType.CHECKEDSUBLIST){
            for(CMEScenario modScenario:CMEScenario.values()){
              builder.accept(Arguments.of(modScenario,queryCast,QueryTestScenario.EMPTY,argType,structType));
              builder.accept(Arguments.of(modScenario,queryCast,QueryTestScenario.DOESNOTCONTAIN,argType,structType));
            }
          }else{
            builder.accept(Arguments.of(CMEScenario.NoMod,queryCast,QueryTestScenario.EMPTY,argType,structType));
            builder.accept(Arguments.of(CMEScenario.NoMod,queryCast,QueryTestScenario.DOESNOTCONTAIN,argType,structType));
          }
          switch(argType){
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
              if(structType==StructType.CHECKEDSUBLIST){
                for(CMEScenario modScenario:CMEScenario.values()){
                  builder.accept(Arguments.of(modScenario,queryCast,QueryTestScenario.CONTAINSBEGINNING,argType,structType));
                  builder.accept(Arguments.of(modScenario,queryCast,QueryTestScenario.CONTAINSMIDDLE,argType,structType));
                  builder.accept(Arguments.of(modScenario,queryCast,QueryTestScenario.CONTAINSEND,argType,structType));
                }
              }else{
                builder.accept(Arguments.of(CMEScenario.NoMod,queryCast,QueryTestScenario.CONTAINSBEGINNING,argType,structType));
                builder.accept(Arguments.of(CMEScenario.NoMod,queryCast,QueryTestScenario.CONTAINSMIDDLE,argType,structType));
                builder.accept(Arguments.of(CMEScenario.NoMod,queryCast,QueryTestScenario.CONTAINSEND,argType,structType));
              }
            default:
              //all other enumerated values MUST return false because they are either out of range or are too precise
          }
        }
      }
  }
  static Stream<Arguments> getQueryStackArguments(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(StructType structType:StructType.values()){
      switch(structType){
        default:
          continue;
        case CHECKEDSTACK:
        case UNCHECKEDSTACK:
      }
      buildQueryArguments(builder,structType);
    }
    return builder.build().parallel();
  }
  static Stream<Arguments> getQueryListArguments(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(StructType structType:StructType.values()){
      switch(structType){
        case CHECKEDSTACK:
        case UNCHECKEDSTACK:
          continue;
        default:
      }
      buildQueryArguments(builder,structType);
    }
    return builder.build().parallel();
  }
  static Stream<Arguments> getQueryCollectionArguments(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(StructType structType:StructType.values()){
      buildQueryArguments(builder,structType);
    }
    return builder.build().parallel();
  }
  private static boolean illegallyModForQuery(QueryCast queryCast,CMEScenario modScenario,QueryTestInputType inputArgType,QueryTestScenario testScenario,ConstructionArguments constructionArgs){
    boolean expectThrow=false;
    switch(modScenario){
      case ModParent:
      case ModRoot:
          switch(inputArgType){
            default:
              expectThrow=true;
              break;
            case Booleannull:
            case Bytenull:
            case Characternull:
            case Shortnull:
            case Integernull:
            case Longnull:
            case Floatnull:
            case Doublenull:
              expectThrow=(queryCast==QueryCast.ToObject);
          }
      default:
    }
    switch(modScenario){
      case ModParent:
        CharInputTestArgType.ARRAY_TYPE.callCollectionAdd(constructionArgs.parent,0);
        break;
      case ModRoot:
        CharInputTestArgType.ARRAY_TYPE.callCollectionAdd(constructionArgs.root,0);
        break;
      default:
    }
    return expectThrow;
  }
  private static ConstructionArguments initializeSeqForQuery(QueryTestScenario testScenario,QueryTestInputType inputArgType,StructType structType){
    ConstructionArguments constructionArgs=new ConstructionArguments(structType);
    switch(testScenario){
      case CONTAINSBEGINNING:
        Assertions.assertTrue(inputArgType.attemptAdd(constructionArgs.seq));
        for(int i=1;i<100;++i){
          inputArgType.addNotEqualsVal(constructionArgs.seq);
        }
        break;
      case CONTAINSMIDDLE:
        for(int i=0;i<49;++i){
          inputArgType.addNotEqualsVal(constructionArgs.seq);
        }
        Assertions.assertTrue(inputArgType.attemptAdd(constructionArgs.seq));
        for(int i=50;i<100;++i){
          inputArgType.addNotEqualsVal(constructionArgs.seq);
        }
        break;
      case CONTAINSEND:
        for(int i=0;i<99;++i){
          inputArgType.addNotEqualsVal(constructionArgs.seq);
        }
        Assertions.assertTrue(inputArgType.attemptAdd(constructionArgs.seq));
        break;
      case DOESNOTCONTAIN:
        for(int i=0;i<100;++i){
          inputArgType.addNotEqualsVal(constructionArgs.seq);
        }
      default:
        break;
    }
    return constructionArgs;
  }
  private static void verifyQueryDidNotModify(CMEScenario modScenario,ConstructionArguments constructionArgs,QueryTestScenario testScenario){
    int offset=constructionArgs.verifyPreAlloc();
    if(testScenario==QueryTestScenario.EMPTY){
      switch(modScenario){
        case ModParent:
          constructionArgs.verifyStructuralIntegrity(0,0,1,1);
          offset=constructionArgs.verifyParentPostAlloc(offset);
          offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
          constructionArgs.verifyRootPostAlloc(offset);
          break;
        case ModRoot:
          constructionArgs.verifyStructuralIntegrity(0,0,0,0,1,1);
          offset=constructionArgs.verifyParentPostAlloc(offset);
          offset=constructionArgs.verifyRootPostAlloc(offset);
          constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
          break;
        default:
          constructionArgs.verifyStructuralIntegrity(0,0);
          offset=constructionArgs.verifyParentPostAlloc(offset);
          constructionArgs.verifyRootPostAlloc(offset);
      }
    }else{
      switch(modScenario){
        case ModParent:
          constructionArgs.verifyStructuralIntegrity(100,100,101,101);
          offset=constructionArgs.verifyParentPostAlloc(offset+100);
          offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
          constructionArgs.verifyRootPostAlloc(offset);
          break;
        case ModRoot:
          constructionArgs.verifyStructuralIntegrity(100,100,100,100,101,101);
          offset=constructionArgs.verifyParentPostAlloc(offset+100);
          offset=constructionArgs.verifyRootPostAlloc(offset);
          constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
          break;
        default:
          constructionArgs.verifyStructuralIntegrity(100,100);
          offset=constructionArgs.verifyParentPostAlloc(offset+100);
          constructionArgs.verifyRootPostAlloc(offset);
      }
    }
  }
  static enum ObjModScenario{
    ModSeq,
    ModParent,
    ModRoot,
    ModSeqThrow,
    ModParentThrow,
    ModRootThrow,
    NoMod,
    Throw;
  }
  private static class ModCheckTestData{
    final ModCheckTestObject modCheckTestObject;
    final ConstructionArguments constructionArgs;
    ModCheckTestData(ModCheckTestObject modCheckTestObject,ConstructionArguments constructionArgs){
      this.modCheckTestObject=modCheckTestObject;
      this.constructionArgs=constructionArgs;
    }
  }
  @ParameterizedTest
  @MethodSource("getQueryListArguments")
  public void testindexOf_val(CMEScenario modScenario,QueryCast queryCast,QueryTestScenario testScenario,QueryTestInputType inputArgType,StructType structType){
    ConstructionArguments constructionArgs=initializeSeqForQuery(testScenario,inputArgType,structType);
    boolean expectThrow=illegallyModForQuery(queryCast,modScenario,inputArgType,testScenario,constructionArgs);
    int expectedRet;
    switch(testScenario){
      case CONTAINSBEGINNING:
        expectedRet=0;
        break;
      case CONTAINSMIDDLE:
        expectedRet=49;
        break;
      case CONTAINSEND:
        expectedRet=99;
        break;
      default:
        expectedRet=-1;
    }
    switch(queryCast){
      case ToBoxed:
        if(expectThrow){
          Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.invokeBoxedindexOf(constructionArgs.seq));
        }else{
          Assertions.assertEquals(expectedRet,inputArgType.invokeBoxedindexOf(constructionArgs.seq));
        }
        break;
      case ToObject:
        if(expectThrow){
          Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.invokeObjectindexOf(constructionArgs.seq));
        }else{
          Assertions.assertEquals(expectedRet,inputArgType.invokeObjectindexOf(constructionArgs.seq));
        }
        break;
      case AsIs:
        if(expectThrow){
          Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.invokeindexOf(constructionArgs.seq));
        }else{
          Assertions.assertEquals(expectedRet,inputArgType.invokeindexOf(constructionArgs.seq));
        }
    }
    verifyQueryDidNotModify(modScenario,constructionArgs,testScenario);
  }
  @ParameterizedTest
  @MethodSource("getQueryListArguments")
  public void testlastIndexOf_val(CMEScenario modScenario,QueryCast queryCast,QueryTestScenario testScenario,QueryTestInputType inputArgType,StructType structType){
    ConstructionArguments constructionArgs=initializeSeqForQuery(testScenario,inputArgType,structType);
    boolean expectThrow=illegallyModForQuery(queryCast,modScenario,inputArgType,testScenario,constructionArgs);
    int expectedRet;
    switch(testScenario){
      case CONTAINSBEGINNING:
        expectedRet=0;
        break;
      case CONTAINSMIDDLE:
        expectedRet=49;
        break;
      case CONTAINSEND:
        expectedRet=99;
        break;
      default:
        expectedRet=-1;
    }
    switch(queryCast){
      case ToBoxed:
        if(expectThrow){
          Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.invokeBoxedlastIndexOf(constructionArgs.seq));
        }else{
          Assertions.assertEquals(expectedRet,inputArgType.invokeBoxedlastIndexOf(constructionArgs.seq));
        }
        break;
      case ToObject:
        if(expectThrow){
          Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.invokeObjectlastIndexOf(constructionArgs.seq));
        }else{
          Assertions.assertEquals(expectedRet,inputArgType.invokeObjectlastIndexOf(constructionArgs.seq));
        }
        break;
      case AsIs:
        if(expectThrow){
          Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.invokelastIndexOf(constructionArgs.seq));
        }else{
          Assertions.assertEquals(expectedRet,inputArgType.invokelastIndexOf(constructionArgs.seq));
        }
    }
    verifyQueryDidNotModify(modScenario,constructionArgs,testScenario);
  }
  @ParameterizedTest
  @MethodSource("getQueryStackArguments")
  public void testsearch_val(CMEScenario modScenario,QueryCast queryCast,QueryTestScenario testScenario,QueryTestInputType inputArgType,StructType structType){
    ConstructionArguments constructionArgs=initializeSeqForQuery(testScenario,inputArgType,structType);
    int expectedRet;
    switch(testScenario){
      case CONTAINSBEGINNING:
        expectedRet=100;
        break;
      case CONTAINSMIDDLE:
        expectedRet=51;
        break;
      case CONTAINSEND:
        expectedRet=1;
        break;
      default:
        expectedRet=-1;
    }
    switch(queryCast){
    case ToBoxed:
      Assertions.assertEquals(expectedRet,inputArgType.invokeBoxedsearch(constructionArgs.seq));
      break;
    case ToObject:
      Assertions.assertEquals(expectedRet,inputArgType.invokeObjectsearch(constructionArgs.seq));
      break;
    case AsIs:
      Assertions.assertEquals(expectedRet,inputArgType.invokesearch(constructionArgs.seq));
    }
    verifyQueryDidNotModify(modScenario,constructionArgs,testScenario);
  }
  @ParameterizedTest
  @MethodSource("getQueryCollectionArguments")
  public void testcontains_val(CMEScenario modScenario,QueryCast queryCast,QueryTestScenario testScenario,QueryTestInputType inputArgType,StructType structType){
    ConstructionArguments constructionArgs=initializeSeqForQuery(testScenario,inputArgType,structType);
    boolean expectThrow=illegallyModForQuery(queryCast,modScenario,inputArgType,testScenario,constructionArgs);
    boolean expectedRet;
    switch(testScenario){
      case CONTAINSBEGINNING:
      case CONTAINSMIDDLE:
      case CONTAINSEND:
        expectedRet=true;
        break;
      default:
        expectedRet=false;
    }
    switch(queryCast){
      case ToBoxed:
        if(expectThrow){
          Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.invokeBoxedcontains(constructionArgs.seq));
        }else{
          Assertions.assertEquals(expectedRet,inputArgType.invokeBoxedcontains(constructionArgs.seq));
        }
        break;
      case ToObject:
        if(expectThrow){
          Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.invokeObjectcontains(constructionArgs.seq));
        }else{
          Assertions.assertEquals(expectedRet,inputArgType.invokeObjectcontains(constructionArgs.seq));
        }
        break;
      case AsIs:
        if(expectThrow){
          Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.invokecontains(constructionArgs.seq));
        }else{
          Assertions.assertEquals(expectedRet,inputArgType.invokecontains(constructionArgs.seq));
        }
    }
    verifyQueryDidNotModify(modScenario,constructionArgs,testScenario);
  }
  @ParameterizedTest
  @MethodSource("getQueryCollectionArguments")
  public void testremoveVal_val(CMEScenario modScenario,QueryCast queryCast,QueryTestScenario testScenario,QueryTestInputType inputArgType,StructType structType){
    ConstructionArguments constructionArgs=initializeSeqForQuery(testScenario,inputArgType,structType);
    boolean expectThrow=illegallyModForQuery(queryCast,modScenario,inputArgType,testScenario,constructionArgs);
    boolean expectedRet;
    switch(testScenario){
      case CONTAINSBEGINNING:
      case CONTAINSMIDDLE:
      case CONTAINSEND:
        expectedRet=true;
        break;
      default:
        expectedRet=false;
    }
    switch(queryCast){
      case ToBoxed:
        if(expectThrow){
          Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.invokeBoxedremoveVal(constructionArgs.seq));
        }else{
          Assertions.assertEquals(expectedRet,inputArgType.invokeBoxedremoveVal(constructionArgs.seq));
          Assertions.assertFalse(inputArgType.invokeBoxedcontains(constructionArgs.seq));
        }
        break;
      case ToObject:
       if(expectThrow){
          Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.invokeObjectremoveVal(constructionArgs.seq));
        }else{
          Assertions.assertEquals(expectedRet,inputArgType.invokeObjectremoveVal(constructionArgs.seq));
          Assertions.assertFalse(inputArgType.invokeObjectcontains(constructionArgs.seq));
        }
        break;
      case AsIs:
        if(expectThrow){
          Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.invokeremoveVal(constructionArgs.seq));
        }else{
          Assertions.assertEquals(expectedRet,inputArgType.invokeremoveVal(constructionArgs.seq));
          Assertions.assertFalse(inputArgType.invokecontains(constructionArgs.seq));
        }
    }
    int offset=constructionArgs.verifyPreAlloc();
    switch(modScenario){
      case ModParent:
        if(testScenario==QueryTestScenario.EMPTY){
          constructionArgs.verifyStructuralIntegrity(0,0,1,1);
        }else{
          constructionArgs.verifyStructuralIntegrity(100,100,101,101);
          offset+=100;
        }
        offset=constructionArgs.verifyParentPostAlloc(offset);
        offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
        constructionArgs.verifyRootPostAlloc(offset);
        break;
      case ModRoot:
        if(testScenario==QueryTestScenario.EMPTY){
          constructionArgs.verifyStructuralIntegrity(0,0,0,0,1,1);
        }else{
          constructionArgs.verifyStructuralIntegrity(100,100,100,100,101,101);
          offset+=100;
        }
        offset=constructionArgs.verifyParentPostAlloc(offset);
        offset=constructionArgs.verifyRootPostAlloc(offset);
        constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
        break;
      case NoMod:
        switch(testScenario){
          case DOESNOTCONTAIN:
            constructionArgs.verifyStructuralIntegrity(100,100);
            offset=constructionArgs.verifyParentPostAlloc(offset+100);
            constructionArgs.verifyRootPostAlloc(offset);
            break;
          case EMPTY:
            constructionArgs.verifyStructuralIntegrity(0,0);
            offset=constructionArgs.verifyParentPostAlloc(offset);
            constructionArgs.verifyRootPostAlloc(offset);
            break;
          case CONTAINSBEGINNING:
          case CONTAINSMIDDLE:
          case CONTAINSEND:
            constructionArgs.verifyStructuralIntegrity(99,101);
            offset=constructionArgs.verifyParentPostAlloc(offset+99);
            offset=constructionArgs.verifyRootPostAlloc(offset);
        }
    }
  }
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
    return builder.build().parallel();
  }
  static void illegallyMod(CMEScenario modScenario,ConstructionArguments constructionArgs){
    switch(modScenario){
      case ModSeq:
        CharInputTestArgType.ARRAY_TYPE.callCollectionAdd(constructionArgs.seq,0);
        break;
      case ModParent:
        CharInputTestArgType.ARRAY_TYPE.callCollectionAdd(constructionArgs.parent,0);
        break;
      case ModRoot:
        CharInputTestArgType.ARRAY_TYPE.callCollectionAdd(constructionArgs.root,0);
        break;
      default:
    }
  }
  @ParameterizedTest
  @MethodSource("getBasicCollectionTestArguments")
  public void testclear_void(StructType structType,int numToAdd,CMEScenario modScenario){
    ConstructionArguments constructionArgs=new ConstructionArguments(structType);
    for(int i=0;i<numToAdd;++i){
      CharInputTestArgType.ARRAY_TYPE.callCollectionAdd(constructionArgs.seq,i);
    }
    illegallyMod(modScenario,constructionArgs);
    if(modScenario==CMEScenario.NoMod){
      Assertions.assertDoesNotThrow(()->constructionArgs.seq.clear());
      if(numToAdd==0){
        constructionArgs.verifyStructuralIntegrity(0,0);
      }else{
        constructionArgs.verifyStructuralIntegrity(0,numToAdd+1);
      }
      int offset=constructionArgs.verifyPreAlloc();
      offset=constructionArgs.verifyParentPostAlloc(offset);
      offset=constructionArgs.verifyRootPostAlloc(offset);
    }else{
      Assertions.assertThrows(ConcurrentModificationException.class,()->constructionArgs.seq.clear());
      if(modScenario==CMEScenario.ModParent){
        constructionArgs.verifyStructuralIntegrity(numToAdd,numToAdd,numToAdd+1,numToAdd+1);
      }else{
        constructionArgs.verifyStructuralIntegrity(numToAdd,numToAdd,numToAdd,numToAdd,numToAdd+1,numToAdd+1);
      }
      int offset=constructionArgs.verifyPreAlloc();
      offset=constructionArgs.verifyAscending(offset,CharInputTestArgType.ARRAY_TYPE,numToAdd);
      offset=constructionArgs.verifyParentPostAlloc(offset);
      if(modScenario==CMEScenario.ModParent){offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);}
      offset=constructionArgs.verifyRootPostAlloc(offset);
      if(modScenario==CMEScenario.ModRoot){constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);}
    }
  }
  @ParameterizedTest
  @MethodSource("getBasicCollectionTestArguments")
  public void testclone_void(StructType structType,int numToAdd,CMEScenario modScenario){
    ConstructionArguments constructionArgs=new ConstructionArguments(structType);
    for(int i=0;i<numToAdd;++i){
      CharInputTestArgType.ARRAY_TYPE.callCollectionAdd(constructionArgs.seq,i);
    }
    illegallyMod(modScenario,constructionArgs);
    if(modScenario==CMEScenario.NoMod){
      var clone=constructionArgs.seq.clone();
      constructionArgs.verifyStructuralIntegrity(numToAdd,numToAdd);
      Assertions.assertNotSame(constructionArgs.seq,clone);
      Assertions.assertNotSame(constructionArgs.parent,clone);
      Assertions.assertNotSame(constructionArgs.root,clone);
      switch(structType)
      {
        case CHECKEDLIST:
        case CHECKEDSUBLIST:
          Assertions.assertTrue(clone instanceof CharArrSeq.CheckedList);
          Assertions.assertEquals(0,((CharArrSeq.CheckedList)clone).modCount);
          break;
        case UNCHECKEDLIST:
        case UNCHECKEDSUBLIST:
          Assertions.assertTrue(clone instanceof CharArrSeq.UncheckedList);
          break;
        case CHECKEDSTACK:
          Assertions.assertTrue(clone instanceof CharArrSeq.CheckedStack);
          Assertions.assertEquals(0,((CharArrSeq.CheckedStack)clone).modCount);
          break;
        case UNCHECKEDSTACK:
          Assertions.assertTrue(clone instanceof CharArrSeq.UncheckedStack);
      }
      var cloneArr=((CharArrSeq)clone).arr;
      var cloneSize=((CharArrSeq)clone).size;
      Assertions.assertEquals(numToAdd,cloneSize);
      if(numToAdd==0)
      {
        Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,cloneArr);
      }
      else
      {
        Assertions.assertNotSame(constructionArgs.root.arr,cloneArr);
        Assertions.assertEquals(numToAdd,cloneArr.length);
        for(int i=0;i<numToAdd;++i)
        {
          Assertions.assertEquals(constructionArgs.root.arr[i+constructionArgs.rootPreAlloc+constructionArgs.parentPreAlloc],cloneArr[i]);
        }
      }
    }
    else
    {
      Assertions.assertThrows(ConcurrentModificationException.class,()->constructionArgs.seq.clone());
      if(modScenario==CMEScenario.ModParent)
      {
        constructionArgs.verifyStructuralIntegrity(numToAdd,numToAdd,numToAdd+1,numToAdd+1);
      }
      else
      {
        constructionArgs.verifyStructuralIntegrity(numToAdd,numToAdd,numToAdd,numToAdd,numToAdd+1,numToAdd+1);
      }
    }
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,CharInputTestArgType.ARRAY_TYPE,numToAdd);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    if(modScenario==CMEScenario.ModParent){offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);}
    offset=constructionArgs.verifyRootPostAlloc(offset);
    if(modScenario==CMEScenario.ModRoot){constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);}
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
    return builder.build().parallel();
  }
  @ParameterizedTest
  @MethodSource("getToArrayArrayArguments")
  public void testtoArray_ObjectArray(int seqSize,int arrayLength,StructType structType,CMEScenario preModScenario)
  {
    ConstructionArguments constructionArgs=new ConstructionArguments(structType);
    for(int i=0;i<seqSize;++i)
    {
      CharInputTestArgType.ARRAY_TYPE.callCollectionAdd(constructionArgs.seq,i);
    }
    illegallyMod(preModScenario,constructionArgs);
    final Character[] paramArr=new Character[arrayLength];
    for(int i=0;i<arrayLength;++i)
    {
      paramArr[i]=TypeConversionUtil.convertToCharacter(100);
    }
    switch(preModScenario)
    {
      case ModParent:
      {
        Assertions.assertThrows(ConcurrentModificationException.class,()->constructionArgs.seq.toArray(paramArr));
        constructionArgs.verifyStructuralIntegrity(seqSize,seqSize,seqSize+1,seqSize+1);
        for(int i=0;i<arrayLength;++i)
        {
          Assertions.assertEquals(TypeConversionUtil.convertToCharacter(100),paramArr[i]);
        }
        break;
      }
      case ModRoot:
      {
        Assertions.assertThrows(ConcurrentModificationException.class,()->constructionArgs.seq.toArray(paramArr));
        constructionArgs.verifyStructuralIntegrity(seqSize,seqSize,seqSize,seqSize,seqSize+1,seqSize+1);
        for(int i=0;i<arrayLength;++i)
        {
          Assertions.assertEquals(TypeConversionUtil.convertToCharacter(100),paramArr[i]);
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
            Assertions.assertEquals(TypeConversionUtil.convertToCharacter(100),outputArr[i]);
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
            Assertions.assertEquals(TypeConversionUtil.convertToCharacter(100),paramArr[i]);
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
    offset=constructionArgs.verifyAscending(offset,CharInputTestArgType.ARRAY_TYPE,seqSize);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    if(preModScenario==CMEScenario.ModParent){offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);}
    offset=constructionArgs.verifyRootPostAlloc(offset);
    if(preModScenario==CMEScenario.ModRoot){offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);}
  }
  static Stream<Arguments> getSizeAndIsEmptyTestArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var structType:StructType.values()){
      for(var preModScenario:CMEScenario.values()){
        if(preModScenario==CMEScenario.ModSeq || (preModScenario!=CMEScenario.NoMod && structType!=StructType.CHECKEDSUBLIST)){
          continue;
        }
        builder.accept(Arguments.of(0,structType,preModScenario));
        builder.accept(Arguments.of(100,structType,preModScenario));
      }
    }
    return builder.build().parallel();
  }
  @ParameterizedTest
  @MethodSource("getSizeAndIsEmptyTestArgs")
  public void testsize_void(int numToAdd,StructType structType,CMEScenario preModScenario)
  {
    ConstructionArguments constructionArgs=new ConstructionArguments(structType);
    for(int i=0;i<numToAdd;++i)
    {
      CharInputTestArgType.ARRAY_TYPE.callCollectionAdd(constructionArgs.seq,i);
    }
    illegallyMod(preModScenario,constructionArgs);
    switch(preModScenario)
    {
      case ModParent:
      {
        Assertions.assertThrows(ConcurrentModificationException.class,()->constructionArgs.seq.size());
        constructionArgs.verifyStructuralIntegrity(numToAdd,numToAdd,numToAdd+1,numToAdd+1);
        break;
      }
      case ModRoot:
      {
        Assertions.assertThrows(ConcurrentModificationException.class,()->constructionArgs.seq.size());
        constructionArgs.verifyStructuralIntegrity(numToAdd,numToAdd,numToAdd,numToAdd,numToAdd+1,numToAdd+1);
        break;
      }
      default:
      {
        for(int i=0;i<numToAdd;++i)
        {
          Assertions.assertEquals(numToAdd-i,constructionArgs.seq.size());
          constructionArgs.verifyStructuralIntegrity(numToAdd-i,i+numToAdd);
          switch(structType)
          {
            case CHECKEDSTACK:
            case UNCHECKEDSTACK:
              ((OmniStack.OfChar)constructionArgs.seq).popChar();
              break;
            default:
              ((OmniList.OfChar)constructionArgs.seq).removeCharAt(numToAdd-i-1);
          }
        }
        Assertions.assertEquals(0,constructionArgs.seq.size());
        constructionArgs.verifyStructuralIntegrity(0,numToAdd*2);
      }
    }
    int offset=constructionArgs.verifyPreAlloc();
    if(preModScenario!=CMEScenario.NoMod){offset=constructionArgs.verifyAscending(offset,CharInputTestArgType.ARRAY_TYPE,numToAdd);}
    offset=constructionArgs.verifyParentPostAlloc(offset);
    if(preModScenario==CMEScenario.ModParent){offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);}
    offset=constructionArgs.verifyRootPostAlloc(offset);
    if(preModScenario==CMEScenario.ModRoot){offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);}
  }
  @ParameterizedTest
  @MethodSource("getSizeAndIsEmptyTestArgs")
  public void testisEmpty_void(int numToAdd,StructType structType,CMEScenario preModScenario)
  {
    ConstructionArguments constructionArgs=new ConstructionArguments(structType);
    for(int i=0;i<numToAdd;++i)
    {
      CharInputTestArgType.ARRAY_TYPE.callCollectionAdd(constructionArgs.seq,i);
    }
    illegallyMod(preModScenario,constructionArgs);
    switch(preModScenario)
    {
      case ModParent:
      {
        Assertions.assertThrows(ConcurrentModificationException.class,()->constructionArgs.seq.isEmpty());
        constructionArgs.verifyStructuralIntegrity(numToAdd,numToAdd,numToAdd+1,numToAdd+1);
        break;
      }
      case ModRoot:
      {
        Assertions.assertThrows(ConcurrentModificationException.class,()->constructionArgs.seq.isEmpty());
        constructionArgs.verifyStructuralIntegrity(numToAdd,numToAdd,numToAdd,numToAdd,numToAdd+1,numToAdd+1);
        break;
      }
      default:
      {
        for(int i=0;i<numToAdd;++i)
        {
          Assertions.assertEquals(numToAdd-i==0,constructionArgs.seq.isEmpty());
          constructionArgs.verifyStructuralIntegrity(numToAdd-i,i+numToAdd);
          switch(structType)
          {
            case CHECKEDSTACK:
            case UNCHECKEDSTACK:
              ((OmniStack.OfChar)constructionArgs.seq).popChar();
              break;
            default:
              ((OmniList.OfChar)constructionArgs.seq).removeCharAt(numToAdd-i-1);
          }
        }
        Assertions.assertTrue(constructionArgs.seq.isEmpty());
        constructionArgs.verifyStructuralIntegrity(0,numToAdd*2);
      }
    }
    int offset=constructionArgs.verifyPreAlloc();
    if(preModScenario!=CMEScenario.NoMod){offset=constructionArgs.verifyAscending(offset,CharInputTestArgType.ARRAY_TYPE,numToAdd);}
    offset=constructionArgs.verifyParentPostAlloc(offset);
    if(preModScenario==CMEScenario.ModParent){offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);}
    offset=constructionArgs.verifyRootPostAlloc(offset);
    if(preModScenario==CMEScenario.ModRoot){offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);}
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
    return builder.build().parallel();
  }
  private static ModCheckTestData initializeForBasicModCheckableTest(StructType structType,int numToAdd,ObjModScenario objModScenario,CMEScenario preModScenario){
    ConstructionArguments constructionArgs=new ConstructionArguments(structType);
    ModCheckTestObject modCheckTestObject=null;
    if(objModScenario==ObjModScenario.NoMod){
       for(int i=0;i<numToAdd;++i){
          CharInputTestArgType.ARRAY_TYPE.callCollectionAdd(constructionArgs.seq,i);
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
  @ParameterizedTest
  @MethodSource("getHashCodeTestArgs")
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
              expectedHash=(expectedHash*31)+Objects.hashCode(TypeConversionUtil.convertTochar(i));
            }
            break;
          default:
            for(int i=0;i<numToAdd;++i)
            {
              expectedHash=(expectedHash*31)+Objects.hashCode(TypeConversionUtil.convertTochar(i));
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
      offset=constructionArgs.verifyAscending(offset,CharInputTestArgType.ARRAY_TYPE,numToAdd);
      offset=constructionArgs.verifyParentPostAlloc(offset);
      if(preModScenario==CMEScenario.ModParent){offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);}
      offset=constructionArgs.verifyRootPostAlloc(offset);
      if(preModScenario==CMEScenario.ModRoot){constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);}
  }
  static Stream<Arguments> getToArrayIntFunctionArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(StructType structType:StructType.values()){
      for(CMEScenario preModScenario:CMEScenario.values()){
        if(preModScenario==CMEScenario.ModSeq){
          continue;
        }
        if(preModScenario!=CMEScenario.NoMod && structType!=StructType.CHECKEDSUBLIST){
          continue;
        }
        for(int seqSize=0;seqSize<=5;seqSize+=5){
          switch(structType){
            case CHECKEDSUBLIST:
              builder.accept(Arguments.of(structType,seqSize,ObjModScenario.ModParentThrow,preModScenario));
              builder.accept(Arguments.of(structType,seqSize,ObjModScenario.ModRootThrow,preModScenario));
              builder.accept(Arguments.of(structType,seqSize,ObjModScenario.ModParent,preModScenario));
              builder.accept(Arguments.of(structType,seqSize,ObjModScenario.ModRoot,preModScenario));
            case CHECKEDLIST:
            case CHECKEDSTACK:
              builder.accept(Arguments.of(structType,seqSize,ObjModScenario.ModSeqThrow,preModScenario));
              builder.accept(Arguments.of(structType,seqSize,ObjModScenario.ModSeq,preModScenario));
              builder.accept(Arguments.of(structType,seqSize,ObjModScenario.Throw,preModScenario));
            default:
              builder.accept(Arguments.of(structType,seqSize,ObjModScenario.NoMod,preModScenario));
          }
        }
      }
    }
    return builder.build().parallel();
  }
  @ParameterizedTest
  @MethodSource("getToArrayIntFunctionArgs")
  public void testtoArray_IntFunction(StructType structType,int seqSize,ObjModScenario arrConstructorModScenario,CMEScenario preModScenario){
    ConstructionArguments constructionArgs=new ConstructionArguments(structType);
    for(int i=0;i<seqSize;++i){
      CharInputTestArgType.ARRAY_TYPE.callCollectionAdd(constructionArgs.seq,i);
    }
    illegallyMod(preModScenario,constructionArgs);
    MonitoredArrayConstructor monitoredArrConstructor;
    Class<Character> componentClass=Character.class;
    switch(arrConstructorModScenario){
      case Throw:
        monitoredArrConstructor=new MonitoredArrayConstructor.Throwing<>(componentClass);
        break;
      case ModSeqThrow:
        monitoredArrConstructor=new MonitoredArrayConstructor.ModdingAndThrowing<>(componentClass,constructionArgs.seq);
        break;
      case ModParentThrow:
        monitoredArrConstructor=new MonitoredArrayConstructor.ModdingAndThrowing<>(componentClass,constructionArgs.parent);
        break;
      case ModRootThrow:
        monitoredArrConstructor=new MonitoredArrayConstructor.ModdingAndThrowing<>(componentClass,constructionArgs.root);
        break;
      case ModSeq:
        monitoredArrConstructor=new MonitoredArrayConstructor.Modding<>(componentClass,constructionArgs.seq);
        break;
      case ModParent:
        monitoredArrConstructor=new MonitoredArrayConstructor.Modding<>(componentClass,constructionArgs.parent);
        break;
      case ModRoot:
        monitoredArrConstructor=new MonitoredArrayConstructor.Modding<>(componentClass,constructionArgs.root);
        break;
      default:
        monitoredArrConstructor=new MonitoredArrayConstructor<>(componentClass);
    }
    Class<? extends Throwable> expectedException=preModScenario==CMEScenario.NoMod?monitoredArrConstructor.expectedException:ConcurrentModificationException.class;
    if(expectedException==null){
      var outputArray=constructionArgs.seq.toArray(monitoredArrConstructor);
      constructionArgs.verifyStructuralIntegrity(seqSize,seqSize);
      Assertions.assertNotSame(outputArray,constructionArgs.root.arr);
      Assertions.assertEquals(seqSize,outputArray.length);
      var itr=constructionArgs.seq.iterator();
      for(int i=0;i<seqSize;++i)
      {
        Assertions.assertEquals(itr.next(),outputArray[i]);
      }
    }else{
      Assertions.assertThrows(expectedException,()->constructionArgs.seq.toArray(monitoredArrConstructor));
    }
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,CharInputTestArgType.ARRAY_TYPE,seqSize);
    if(arrConstructorModScenario==ObjModScenario.ModSeq||arrConstructorModScenario==ObjModScenario.ModSeqThrow){
      switch(preModScenario)
      {
        case ModRoot:
          constructionArgs.verifyStructuralIntegrity(seqSize,seqSize,seqSize,seqSize,seqSize+1,seqSize+1);
          break;
        case ModParent:
          constructionArgs.verifyStructuralIntegrity(seqSize,seqSize,seqSize+1,seqSize+1);
          break;
        default:
          constructionArgs.verifyStructuralIntegrity(seqSize+1,seqSize+1);
          offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
      }
    }
    offset=constructionArgs.verifyParentPostAlloc(offset);
    if(arrConstructorModScenario==ObjModScenario.ModParent||arrConstructorModScenario==ObjModScenario.ModParentThrow){
      switch(preModScenario){
        case ModRoot:
          constructionArgs.verifyStructuralIntegrity(seqSize,seqSize,seqSize,seqSize,seqSize+1,seqSize+1);
          break;
        case ModParent:
          constructionArgs.verifyStructuralIntegrity(seqSize,seqSize,seqSize+2,seqSize+2);
          offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
          break;
        default:
          constructionArgs.verifyStructuralIntegrity(seqSize,seqSize,seqSize+1,seqSize+1);
          offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
      }
    }
    if(preModScenario==CMEScenario.ModParent){
      offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    }
    offset=constructionArgs.verifyRootPostAlloc(offset);
    if(arrConstructorModScenario==ObjModScenario.ModRoot||arrConstructorModScenario==ObjModScenario.ModRootThrow){
      switch(preModScenario){
        case ModRoot:
          constructionArgs.verifyStructuralIntegrity(seqSize,seqSize,seqSize,seqSize,seqSize+2,seqSize+2);
          break;
        case ModParent:
          constructionArgs.verifyStructuralIntegrity(seqSize,seqSize,seqSize+1,seqSize+1,seqSize+2,seqSize+2);
          break;
        default:
          constructionArgs.verifyStructuralIntegrity(seqSize,seqSize,seqSize,seqSize,seqSize+1,seqSize+1);
      }
      offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    }
    if(preModScenario==CMEScenario.ModRoot){
      constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    }
    Assertions.assertEquals(1,monitoredArrConstructor.numCalls);
  }
  static enum FunctionCallType
  {
    BoxedType,
    AsIs;
  }
  static enum ItrType
  {
    ListItr,
    Itr;
  }
  static enum ItrForEachModScenario
  {
    ModItr,
    ModItrThrow,
    ModSeq,
    ModSeqThrow,
    ModParent,
    ModParentThrow,
    ModRoot,
    ModRootThrow,
    Throw,
    NoMod;
  }
  static Stream<Arguments> getItrForEachArguments()
  {
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var structType:StructType.values())
    {
      for(var itrType:ItrType.values())
      {
        if(itrType==ItrType.ListItr && (structType==StructType.CHECKEDSTACK || structType==StructType.UNCHECKEDSTACK))
        {
          continue;
        }
        for(var preModScenario:CMEScenario.values())
        {
          if((!structType.checked && preModScenario!=CMEScenario.NoMod) || (structType!=StructType.CHECKEDSUBLIST && (preModScenario==CMEScenario.ModParent||preModScenario==CMEScenario.ModRoot)))
          {
            continue;
          }
          for(var itrModScenario:ItrForEachModScenario.values())
          {
            if((!structType.checked && itrModScenario!=ItrForEachModScenario.NoMod) || (structType!=StructType.CHECKEDSUBLIST && (itrModScenario==ItrForEachModScenario.ModRoot || itrModScenario==ItrForEachModScenario.ModParent || itrModScenario==ItrForEachModScenario.ModRootThrow || itrModScenario==ItrForEachModScenario.ModParentThrow)))
            {
              continue;
            }
            for(var functionCallType:FunctionCallType.values())
            {
              for(int seqSize=0;seqSize<=5;seqSize+=5)
              {
                builder.accept(Arguments.of(structType,seqSize,preModScenario,itrModScenario,itrType,functionCallType));
              }
            }
          }
        }
      }
    }
    return builder.build().parallel();
  }
  @ParameterizedTest
  @MethodSource("getItrForEachArguments")
  public void testItrforEachRemaining(StructType structType,int seqSize,CMEScenario preModScenario,ItrForEachModScenario itrForEachModScenario,ItrType itrType,FunctionCallType functionCallType)
  {
    ConstructionArguments constructionArgs=new ConstructionArguments(structType);
    for(int i=0;i<seqSize;++i){
      CharInputTestArgType.ARRAY_TYPE.callCollectionAdd(constructionArgs.seq,i);
    }
    OmniIterator.OfChar itr;
    if(itrType==ItrType.ListItr)
    {
      itr=((OmniList.OfChar)constructionArgs.seq).listIterator();
    }
    else
    {
      itr=constructionArgs.seq.iterator();
    }
    illegallyMod(preModScenario,constructionArgs);
    ArrayList consumerMonitor=new ArrayList();
    final CharConsumer consumer;
    Class<? extends Throwable> expectedException;
    switch(itrForEachModScenario)
    {
      case ModItr:
        consumer=(v)->
        {
          itr.nextChar();
          itr.remove();
          consumerMonitor.add(v);
        };
        expectedException=seqSize==0?null:ConcurrentModificationException.class;
        break;
      case ModItrThrow:
        consumer=(v)->
        {
          itr.nextChar();
          itr.remove();
          consumerMonitor.add(v);
          throw new IndexOutOfBoundsException();
        };
        expectedException=seqSize==0?null:ConcurrentModificationException.class;
        break;
      case ModSeq:
        consumer=(v)->
        {
          constructionArgs.seq.remove(v);
          consumerMonitor.add(v);
        };
        expectedException=seqSize==0?null:ConcurrentModificationException.class;
        break;
      case ModSeqThrow:
        consumer=(v)->
        {
          constructionArgs.seq.remove(v);
          consumerMonitor.add(v);
          throw new IndexOutOfBoundsException();
        };
        expectedException=seqSize==0?null:ConcurrentModificationException.class;
        break;
      case ModParent:
        consumer=(v)->
        {
          constructionArgs.parent.remove(v);
          consumerMonitor.add(v);
        };
        expectedException=seqSize==0?null:ConcurrentModificationException.class;
        break;
      case ModParentThrow:
        consumer=(v)->
        {
          constructionArgs.parent.remove(v);
          consumerMonitor.add(v);
          throw new IndexOutOfBoundsException();
        };
        expectedException=seqSize==0?null:ConcurrentModificationException.class;
        break;
      case ModRoot:
        consumer=(v)->
        {
          constructionArgs.root.remove(v);
          consumerMonitor.add(v);
        };
        expectedException=seqSize==0?null:ConcurrentModificationException.class;
        break;
      case ModRootThrow:
        consumer=(v)->
        {
          constructionArgs.root.remove(v);
          consumerMonitor.add(v);
          throw new IndexOutOfBoundsException();
        };
        expectedException=seqSize==0?null:ConcurrentModificationException.class;
        break;
      case Throw:
        consumer=(v)->
        {
          consumerMonitor.add(v);
          throw new IndexOutOfBoundsException();
        };
        expectedException=seqSize==0?null:IndexOutOfBoundsException.class;
        break;
      default:
        consumer=(v)->
        {
          consumerMonitor.add(v);
        };
        expectedException=null;
    }
    if(expectedException==null)
    {
      if(functionCallType==FunctionCallType.AsIs)
      {
        itr.forEachRemaining(consumer);
      }
      else
      {
        itr.forEachRemaining((Consumer<? super Character>)consumer::accept);
      }
    }
    else
    {
      if(functionCallType==FunctionCallType.AsIs)
      {
        Assertions.assertThrows(expectedException,()->itr.forEachRemaining(consumer));
      }
      else
      {
        Assertions.assertThrows(expectedException,()->itr.forEachRemaining((Consumer<? super Character>)consumer::accept));
      }
    }
    verifyIteratorState(constructionArgs,itr,seqSize,preModScenario,itrForEachModScenario);
    verifyStructuralIntegrity(seqSize,constructionArgs,preModScenario,itrForEachModScenario);
    verifyConsumerMonitor(consumerMonitor,seqSize,constructionArgs,preModScenario,itrForEachModScenario); 
  }
  private static void verifyIteratorState(ConstructionArguments constructionArgs,Object iterator,int seqSize,CMEScenario preModScenario,ItrForEachModScenario itrForEachModScenario)
  {
    switch(itrForEachModScenario)
    {
      case ModItr:
        switch(preModScenario)
        {
          case ModSeq:
            switch(constructionArgs.structType)
            {
              case CHECKEDSTACK:
              case UNCHECKEDSTACK:
              default:
            }
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case ModItrThrow:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case ModSeq:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case ModSeqThrow:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case ModParent:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case ModParentThrow:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case ModRoot:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case ModRootThrow:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case Throw:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      default:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
    }
  }
  private static void verifyStructuralIntegrity(int seqSize,ConstructionArguments constructionArgs,CMEScenario preModScenario,ItrForEachModScenario itrForEachModScenario)
  {
    switch(itrForEachModScenario)
    {
      case ModItr:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case ModItrThrow:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case ModSeq:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case ModSeqThrow:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case ModParent:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case ModParentThrow:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case ModRoot:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case ModRootThrow:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case Throw:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      default:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
    }
  }
  private static void verifyConsumerMonitor(ArrayList consumerMonitor,int seqSize,ConstructionArguments constructionArgs,CMEScenario preModScenario,ItrForEachModScenario itrForEachModScenario)
  {
    switch(itrForEachModScenario)
    {
      case ModItr:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case ModItrThrow:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case ModSeq:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case ModSeqThrow:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case ModParent:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case ModParentThrow:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case ModRoot:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case ModRootThrow:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case Throw:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      default:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
    }
  }
  */
}
