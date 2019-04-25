package omni.impl.seq;
import omni.function.BooleanConsumer;
import omni.function.BooleanPredicate;
import java.util.function.Consumer;
import java.io.IOException;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import omni.impl.BooleanInputTestArgType;
import omni.impl.BooleanOutputTestArgType;
import org.junit.jupiter.params.provider.Arguments;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import omni.util.OmniArray;
import omni.impl.BooleanSnglLnkNode;
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
import omni.impl.seq.BooleanSeqMonitor.CheckedType;
import omni.impl.seq.BooleanSeqMonitor.PreModScenario;
import omni.impl.seq.BooleanSeqMonitor.SequenceLocation;
import omni.impl.seq.BooleanSeqMonitor.SequenceContentsScenario;
import omni.impl.seq.BooleanSeqMonitor.IterationScenario;
import omni.impl.seq.BooleanSeqMonitor.ItrRemoveScenario;
import omni.impl.seq.BooleanSeqMonitor.MonitoredFunctionGen;
import omni.impl.seq.BooleanSeqMonitor.MonitoredRemoveIfPredicateGen;
import java.nio.file.Files;
import omni.impl.seq.BooleanSeqMonitor.SequenceVerificationItr;
import omni.api.OmniCollection;
import java.util.ArrayList;
@SuppressWarnings({"rawtypes","unchecked"})
@Tag("SnglLnkSeq")
@Execution(ExecutionMode.CONCURRENT)
public class BooleanSnglLnkSeqTest{
  static Stream<Arguments> getConstructor_voidArgs(){
    return ArgBuilder.buildSeqArgs((streamBuilder,nestedType,checkedType)->{
      streamBuilder.accept(Arguments.of(new BooleanSnglLnkSeqMonitor(nestedType,checkedType)));
    });
  }
  @org.junit.jupiter.api.Test
  public void testConstructor_void(){
    getConstructor_voidArgs().parallel().map(Arguments::get).forEach(args->{
        testConstructor_voidHelper((BooleanSnglLnkSeqMonitor)args[0]);
    });
  }
  private static void testConstructor_voidHelper
  (BooleanSnglLnkSeqMonitor seqMonitor){
    if(seqMonitor.checkedType.checked){
      Assertions.assertEquals(0,seqMonitor.nestedType==NestedType.QUEUE?FieldAndMethodAccessor.BooleanSnglLnkSeq.CheckedQueue.modCount(seqMonitor.seq):FieldAndMethodAccessor.BooleanSnglLnkSeq.CheckedStack.modCount(seqMonitor.seq));
    }
    Assertions.assertEquals(0,seqMonitor.seq.size);
    Assertions.assertNull(seqMonitor.seq.head);
  }
  static Stream<Arguments> getItrnext_voidArgs(){
    return ArgBuilder.buildSeqArgs((streamBuilder,nestedType,checkedType)->{
      for(var itrScenario:IterationScenario.values()){
        if(checkedType.checked || itrScenario==IterationScenario.NoMod){
          for(var seqContentsScenario:SequenceContentsScenario.values()){
            if(seqContentsScenario.nonEmpty || itrScenario.validWithEmptySeq){
              if(itrScenario.preModScenario.appliesToRootItr){
                for(var outputType:BooleanOutputTestArgType.values()){
                  streamBuilder.accept(Arguments.of(new BooleanSnglLnkSeqMonitor(nestedType,checkedType),itrScenario,seqContentsScenario,outputType));
                }
              }
            }
          }
        }
      }
    });
  }
  @org.junit.jupiter.api.Test
  public void testItrnext_void(){
    getItrnext_voidArgs().parallel().map(Arguments::get).forEach(args->{
        testItrnext_voidHelper((BooleanSnglLnkSeqMonitor)args[0],(IterationScenario)args[1],(SequenceContentsScenario)args[2],(BooleanOutputTestArgType)args[3]);
    });
  }
  private static void testItrnext_voidHelper
  (BooleanSnglLnkSeqMonitor seqMonitor,IterationScenario itrScenario,SequenceContentsScenario seqContentsScenario,BooleanOutputTestArgType outputType){
    int numToAdd=seqContentsScenario.nonEmpty?100:0;
    for(int i=0;i<numToAdd;++i){
      seqMonitor.add(i);
    }
    var itrMonitor=seqMonitor.getItrMonitor();
    switch(itrScenario){
      case NoMod:
      case ModSeqSupercedesThrowNSEE:
        for(int i=0;;++i){
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
    seqMonitor.verifyPreAlloc(itrScenario.preModScenario).verifyNaturalAscending(numToAdd).verifyPostAlloc(itrScenario.preModScenario);
  }
  static Stream<Arguments> getItrremove_voidArgs(){
    return ArgBuilder.buildSeqArgs((streamBuilder,nestedType,checkedType)->{
      for(var removeScenario:ItrRemoveScenario.values()){
        if(removeScenario.validWithForwardItr && (checkedType.checked || removeScenario.expectedException==null)){
          for(var sequenceContentsScenario:SequenceContentsScenario.values()){
            if(sequenceContentsScenario.nonEmpty || removeScenario.validWithEmptySeq){
              for(var preModScenario:PreModScenario.values()){
                if(preModScenario.appliesToRootItr && (checkedType.checked || preModScenario.expectedException==null)){
                  for(var seqLocation:SequenceLocation.values()){
                    if(seqLocation.expectedException==null && (sequenceContentsScenario.nonEmpty || seqLocation==SequenceLocation.BEGINNING) && (seqLocation!=SequenceLocation.END || removeScenario!=ItrRemoveScenario.PostInit) && (removeScenario!=ItrRemoveScenario.PostInit || seqLocation==SequenceLocation.BEGINNING)){
                      streamBuilder.accept(Arguments.of(new BooleanSnglLnkSeqMonitor(nestedType,checkedType),removeScenario,preModScenario,sequenceContentsScenario,seqLocation));
                    }
                  }
                }
              }
            }
          }
        }
      }
    });
  }
  @org.junit.jupiter.api.Test
  public void testItrremove_void(){
    getItrremove_voidArgs().parallel().map(Arguments::get).forEach(args->{
        testItrremove_voidHelper((BooleanSnglLnkSeqMonitor)args[0],(ItrRemoveScenario)args[1],(PreModScenario)args[2],(SequenceContentsScenario)args[3],(SequenceLocation)args[4]);
    });
  }
  private static void testItrremove_voidHelper
  (BooleanSnglLnkSeqMonitor seqMonitor,ItrRemoveScenario removeScenario,PreModScenario preModScenario,SequenceContentsScenario seqContentsScenario,SequenceLocation seqLocation){
    int numToAdd=seqContentsScenario.nonEmpty?100:0;
    for(int i=0;i<numToAdd;++i){
      seqMonitor.add(i);
    }
    var itrMonitor=seqMonitor.getItrMonitor();
    switch(seqLocation){
      case BEGINNING:
        break;
      case MIDDLE:
        for(int i=0,bound=numToAdd/2;i<bound;++i){
          itrMonitor.iterateForward();
        }
        break;
      case END:
        for(int i=0;i<numToAdd;++i){
          itrMonitor.iterateForward();
        }
        break;
      default:
        throw new Error("Unknown seqLocation "+seqLocation);
    }
    switch(removeScenario){
      case PostNext:
        if(seqLocation==SequenceLocation.BEGINNING){
          itrMonitor.iterateForward();
        }
        break;
      case PostRemove:
        if(seqLocation==SequenceLocation.BEGINNING){
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
        while(itrMonitor.hasNext()){
          itrMonitor.iterateForward();
          itrMonitor.remove();
          itrMonitor.verifyIteratorState();
          seqMonitor.verifyStructuralIntegrity();
        }
        Assertions.assertFalse(itrMonitor.hasNext());
        if(seqLocation==SequenceLocation.BEGINNING){
          Assertions.assertTrue(seqMonitor.isEmpty());
        }
        return;
      }else{
        Assertions.assertThrows(preModScenario.expectedException,()->itrMonitor.remove());
        itrMonitor.verifyIteratorState();
        seqMonitor.verifyStructuralIntegrity();
        verifyItr=seqMonitor.verifyPreAlloc(preModScenario).verifyNaturalAscending(numToAdd);
      }
    }else{
      Assertions.assertThrows(removeScenario.expectedException,()->itrMonitor.remove());
      itrMonitor.verifyIteratorState();
      seqMonitor.verifyStructuralIntegrity();
      verifyItr=seqMonitor.verifyPreAlloc(preModScenario);
      switch(removeScenario){
        case PostInit:
          verifyItr.verifyNaturalAscending(numToAdd);
          break;
        case PostRemove:
          if(seqMonitor.nestedType.forwardIteration){
            switch(seqLocation){
              case BEGINNING:
                verifyItr.verifyAscending(1,numToAdd-1);
                break;
              case MIDDLE:
                verifyItr.verifyAscending((numToAdd/2)-1).verifyAscending(numToAdd/2,numToAdd/2);
                break;
              case END:
                verifyItr.verifyAscending(numToAdd-1);
                break;
              default:
               throw new Error("Unknown seqLocation "+seqLocation);
            }
          }else{
            switch(seqLocation){
              case BEGINNING:
                verifyItr.verifyDescending(numToAdd-1);
                break;
              case MIDDLE:
                verifyItr.verifyDescending(numToAdd,(numToAdd/2)-1).verifyDescending(numToAdd/2);
                break;
              case END:
                verifyItr.verifyDescending(numToAdd,numToAdd-1);
                break;
              default:
                 throw new Error("Unknown seqLocation "+seqLocation);
            }
          }
          break;
        default:
          throw new Error("unknown remove scenario "+removeScenario);
      }
    }
    verifyItr.verifyPostAlloc(preModScenario);
  }
  static Stream<Arguments> getItrforEachRemaining_ConsumerArgs(){
    return ArgBuilder.buildSeqArgs((streamBuilder,nestedType,checkedType)->{
      for(var preModScenario:PreModScenario.values()){
        if(checkedType.checked || preModScenario.expectedException==null){
          for(var monitoredFunctionGen:MonitoredFunctionGen.values()){
            if((monitoredFunctionGen.expectedException==null || checkedType.checked) && (preModScenario.appliesToRootItr&&monitoredFunctionGen.appliesToRootItr)){
              for(var seqContentsScenario:SequenceContentsScenario.values()){
                streamBuilder.accept(Arguments.of(new BooleanSnglLnkSeqMonitor(nestedType,checkedType),preModScenario,monitoredFunctionGen,seqContentsScenario,FunctionCallType.Unboxed));
                streamBuilder.accept(Arguments.of(new BooleanSnglLnkSeqMonitor(nestedType,checkedType),preModScenario,monitoredFunctionGen,seqContentsScenario,FunctionCallType.Boxed));
              }
            }
          }
        }
      }
    });
  }
  @org.junit.jupiter.api.Test
  public void testItrforEachRemaining_Consumer(){
    getItrforEachRemaining_ConsumerArgs().parallel().map(Arguments::get).forEach(args->{
        testItrforEachRemaining_ConsumerHelper((BooleanSnglLnkSeqMonitor)args[0],(PreModScenario)args[1],(MonitoredFunctionGen)args[2],(SequenceContentsScenario)args[3],(FunctionCallType)args[4]);
    });
  }
  private static void testItrforEachRemaining_ConsumerHelper
  (BooleanSnglLnkSeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredFunctionGen monitoredFunctionGen,SequenceContentsScenario seqContentsScenario,FunctionCallType functionCallType){
    int numToAdd=seqContentsScenario.nonEmpty?100:0;
    for(int i=0;i<numToAdd;++i){
      seqMonitor.add(i);
    }
    var itrMonitor=seqMonitor.getItrMonitor();
    seqMonitor.illegalAdd(preModScenario);
    var monitoredConsumer=monitoredFunctionGen.getMonitoredConsumer(itrMonitor);
    int numExpectedIteratedValues;
    if(preModScenario.expectedException==null || !seqContentsScenario.nonEmpty){
      if(monitoredFunctionGen.expectedException==null || !seqContentsScenario.nonEmpty){
        itrMonitor.forEachRemaining(monitoredConsumer,functionCallType);
        itrMonitor.verifyIteratorState();
        seqMonitor.verifyStructuralIntegrity();
        seqMonitor.verifyPreAlloc(preModScenario).verifyNaturalAscending(numToAdd).verifyPostAlloc(preModScenario);
        numExpectedIteratedValues=numToAdd;
      }else{
        Assertions.assertThrows(monitoredFunctionGen.expectedException,()->itrMonitor.forEachRemaining(monitoredConsumer,functionCallType));
        seqMonitor.verifyStructuralIntegrity();
        itrMonitor.verifyIteratorState();
        switch(monitoredFunctionGen){
          case ThrowModItr:
            numExpectedIteratedValues=1;
            var verifyItr=seqMonitor.verifyPreAlloc(preModScenario);
            if(seqMonitor.nestedType.forwardIteration){
              verifyItr.verifyAscending(1,numToAdd-1);
            }else{
              verifyItr.verifyDescending(numToAdd-1);
            }
            verifyItr.verifyPostAlloc(preModScenario);
            break;
          case ModItr:
            numExpectedIteratedValues=numToAdd;
            seqMonitor.verifyPreAlloc(preModScenario).verifyPostAlloc(preModScenario);
            break;
          case Throw:
            numExpectedIteratedValues=1;
            seqMonitor.verifyPreAlloc(preModScenario).verifyNaturalAscending(numToAdd).verifyPostAlloc(preModScenario);
            break;
          case ModSeq:
            numExpectedIteratedValues=numToAdd;
            verifyItr=seqMonitor.verifyPreAlloc(preModScenario);
            if(seqMonitor.nestedType.forwardIteration){
              verifyItr.verifyAscending(numToAdd);
              for(int i=0;i<numToAdd;++i){
                verifyItr.verifyIllegalAdd();
              }
            }else{
              for(int i=0;i<numToAdd;++i){
                verifyItr.verifyIllegalAdd();
              }
              verifyItr.verifyDescending(numToAdd);
            }
            verifyItr.verifyPostAlloc(preModScenario);
            break;
          case ThrowModSeq:
            numExpectedIteratedValues=1;
            seqMonitor.verifyPreAlloc(PreModScenario.ModSeq).verifyNaturalAscending(numToAdd).verifyPostAlloc(PreModScenario.ModSeq);
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
          seqMonitor.verifyPreAlloc(preModScenario).verifyNaturalAscending(numToAdd).verifyPostAlloc(preModScenario);
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
          seqMonitor.verifyPreAlloc(preModScenario).verifyNaturalAscending(numToAdd).verifyPostAlloc(preModScenario);
          break;
        case Throw:
          numExpectedIteratedValues=1;
          seqMonitor.verifyPreAlloc(preModScenario).verifyNaturalAscending(numToAdd).verifyPostAlloc(preModScenario);
          break;
        case ModSeq:
          var verifyItr=seqMonitor.verifyPreAlloc(preModScenario);
          if(seqMonitor.nestedType.forwardIteration){
            verifyItr.verifyAscending(numToAdd);
            if(preModScenario==PreModScenario.ModSeq){
              numExpectedIteratedValues=numToAdd+1;
            }else{
              numExpectedIteratedValues=1;
            }
            for(int i=0;i<numExpectedIteratedValues;++i){
              verifyItr.verifyIllegalAdd();
            }
          }else{
            if(preModScenario==PreModScenario.ModSeq){
              numExpectedIteratedValues=numToAdd;
            }else{
              numExpectedIteratedValues=1;
            }
            for(int i=0;i<numExpectedIteratedValues;++i){
              verifyItr.verifyIllegalAdd();
            }
            verifyItr.verifyDescending(numToAdd);
          }
          verifyItr.verifyPostAlloc(preModScenario);
          break;
        case ThrowModSeq:
          verifyItr=seqMonitor.verifyPreAlloc(preModScenario);
          if(seqMonitor.nestedType.forwardIteration){
            verifyItr.verifyAscending(numToAdd).verifyIllegalAdd();
          }else{
            verifyItr.verifyIllegalAdd().verifyDescending(numToAdd);
          }
          verifyItr.verifyPostAlloc(preModScenario);
          numExpectedIteratedValues=1;
          break;
        default:
          throw new Error("Unknown monitored function gen "+monitoredFunctionGen);
      }
    }
    Assertions.assertEquals(numExpectedIteratedValues,monitoredConsumer.encounteredValues.size());
    //TODO verify the iterated values
  }
  static Stream<Arguments> getforEach_ConsumerArgs(){
    return ArgBuilder.buildSeqArgs((streamBuilder,nestedType,checkedType)->{
      for(var monitoredFunctionGen:MonitoredFunctionGen.values()){
        if(monitoredFunctionGen.appliesToRoot&&(checkedType.checked || monitoredFunctionGen.expectedException==null)){
          for(var seqContentsScenario:SequenceContentsScenario.values()){
            streamBuilder.accept(Arguments.of(new BooleanSnglLnkSeqMonitor(nestedType,checkedType),monitoredFunctionGen,seqContentsScenario,FunctionCallType.Unboxed));
            streamBuilder.accept(Arguments.of(new BooleanSnglLnkSeqMonitor(nestedType,checkedType),monitoredFunctionGen,seqContentsScenario,FunctionCallType.Boxed));
          }
        }
      }
    });
  }
  @org.junit.jupiter.api.Test
  public void testforEach_Consumer(){
    getforEach_ConsumerArgs().parallel().map(Arguments::get).forEach(args->{
        testforEach_ConsumerHelper((BooleanSnglLnkSeqMonitor)args[0],(MonitoredFunctionGen)args[1],(SequenceContentsScenario)args[2],(FunctionCallType)args[3]);
    });
  }
  private static void testforEach_ConsumerHelper
  (BooleanSnglLnkSeqMonitor seqMonitor,MonitoredFunctionGen monitoredFunctionGen,SequenceContentsScenario seqContentsScenario,FunctionCallType functionCallType){
    int numToAdd=seqContentsScenario.nonEmpty?100:0;
    for(int i=0;i<numToAdd;++i){
      seqMonitor.add(i);
    }
    var monitoredConsumer=monitoredFunctionGen.getMonitoredConsumer(seqMonitor);
    int numExpectedIteratedValues;
    if(monitoredFunctionGen.expectedException==null || !seqContentsScenario.nonEmpty){
      seqMonitor.forEach(monitoredConsumer,functionCallType);
      seqMonitor.verifyStructuralIntegrity();
      seqMonitor.verifyPreAlloc().verifyNaturalAscending(numToAdd).verifyPostAlloc();
      numExpectedIteratedValues=numToAdd;
    }else{
      Assertions.assertThrows(monitoredFunctionGen.expectedException,()->seqMonitor.forEach(monitoredConsumer,functionCallType));
      seqMonitor.verifyStructuralIntegrity();
      var verifyItr=seqMonitor.verifyPreAlloc();
      switch(monitoredFunctionGen){
        case Throw:
          numExpectedIteratedValues=1;
          verifyItr.verifyNaturalAscending(numToAdd);
          break;
        case ModSeq:
          numExpectedIteratedValues=numToAdd;
          if(seqMonitor.nestedType.forwardIteration){
            verifyItr.verifyAscending(numToAdd);
            for(int i=0;i<numToAdd;++i){
              verifyItr.verifyIllegalAdd();
            }
          }else{
            for(int i=0;i<numToAdd;++i){
              verifyItr.verifyIllegalAdd();
            }
            verifyItr.verifyDescending(numToAdd);
          }
          break;
        case ThrowModSeq:
          numExpectedIteratedValues=1;
          if(seqMonitor.nestedType.forwardIteration){
            verifyItr.verifyAscending(numToAdd).verifyIllegalAdd();
          }else{
            verifyItr.verifyIllegalAdd().verifyDescending(numToAdd);
          }
          break;
        default:
          throw new Error("Unknown monitored function gen "+monitoredFunctionGen);
      }
      verifyItr.verifyPostAlloc();
    }
    Assertions.assertEquals(numExpectedIteratedValues,monitoredConsumer.encounteredValues.size());
    //TODO verify iterated values
  }
  static Stream<Arguments> getremoveIf_PredicateArgs(){
    return ArgBuilder.buildSeqArgs((streamBuilder,nestedType,checkedType)->{
      for(var monitoredRemoveIfPredicateGen:MonitoredRemoveIfPredicateGen.values()){
        if(monitoredRemoveIfPredicateGen.expectedException==null || (checkedType.checked && monitoredRemoveIfPredicateGen.appliesToRoot)){
          for(var functionCallType:FunctionCallType.values()){
            for(int seqSize=0;seqSize<=10;++seqSize){
              long randSeedBound;
              if(seqSize==0 || !monitoredRemoveIfPredicateGen.isRandomized){
                randSeedBound=0;
              }else{
                randSeedBound=100;
              }
              for(long randSeed=0;randSeed<=randSeedBound;++randSeed){
                for(int period=1,inc=Math.max(1,seqSize/10);;period+=inc){
                  for(int initVal=0;initVal<=1;++initVal){
                    streamBuilder.accept(Arguments.of(new BooleanSnglLnkSeqMonitor(nestedType,checkedType),monitoredRemoveIfPredicateGen,0.5,randSeed,functionCallType,seqSize,initVal,period));
                  }
                  if(period>=seqSize){
                    break;
                  }
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
      testremoveIf_PredicateHelper((BooleanSnglLnkSeqMonitor)args[0],(MonitoredRemoveIfPredicateGen)args[1],(double)args[2],(long)args[3],(FunctionCallType)args[4],(int)args[5]
      ,(int)args[6],(int)args[7]
      );
    });
  }
  private static void testremoveIf_PredicateHelper
  (BooleanSnglLnkSeqMonitor seqMonitor,MonitoredRemoveIfPredicateGen monitoredRemoveIfPredicateGen,double threshold,long randSeed,final FunctionCallType functionCallType,int seqSize
  ,int initVal,int period
  ){
  int trueCount=0;
  for(int i=0;i<seqSize;){
    seqMonitor.add(initVal);
    if((initVal&1)!=0){
      ++trueCount;
    }
    if((++i)%period==0){
      ++initVal;
    }
  }
    final var clone=(OmniCollection.OfBoolean)seqMonitor.seq.clone();
    final int numExpectedCalls=seqMonitor.seq.contains(true)?seqMonitor.seq.contains(false)?2:1:seqMonitor.seq.contains(false)?1:0;
    final int numExpectedRemoved;
    switch(monitoredRemoveIfPredicateGen){
      case RemoveTrue:
        numExpectedRemoved=trueCount;
        break;
      case RemoveFalse:
        numExpectedRemoved=seqSize-trueCount;
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
      case ThrowModSeq:
        numExpectedRemoved=0;
        break;
      default:
        throw new Error("Unknown monitoredRemoveIfPredicateGen "+monitoredRemoveIfPredicateGen);
    }
    final var monitoredRemoveIfPredicate=monitoredRemoveIfPredicateGen.getMonitoredRemoveIfPredicate(seqMonitor,randSeed,numExpectedCalls,threshold);
    if(monitoredRemoveIfPredicateGen.expectedException==null || seqSize==0){
      seqMonitor.verifyRemoveIf(monitoredRemoveIfPredicate,functionCallType,numExpectedRemoved,clone);
      seqMonitor.verifyPreAlloc().skip(seqMonitor.expectedSeqSize).verifyPostAlloc();
      return;
    }else{
      Assertions.assertThrows(monitoredRemoveIfPredicateGen.expectedException,()->seqMonitor.verifyRemoveIf(monitoredRemoveIfPredicate,functionCallType,numExpectedRemoved,clone));
      //TODO verify contents of sequence in throw cases 
    }
  }
  @org.junit.jupiter.api.Test
  public void testclone_void(){
    getBasicCollectionTestArgs().parallel().map(Arguments::get).forEach(args->{
        testclone_voidHelper((BooleanSnglLnkSeqMonitor)args[0],(int)args[1]);
    });
  }
  private static void testclone_voidHelper
  (BooleanSnglLnkSeqMonitor seqMonitor,int numToAdd){
    for(int i=0;i<numToAdd;++i){
      seqMonitor.add(i);
    }
    var clone=(OmniCollection.OfBoolean)seqMonitor.seq.clone();
    seqMonitor.verifyStructuralIntegrity();
    seqMonitor.verifyPreAlloc().verifyNaturalAscending(numToAdd).verifyPostAlloc();
    Assertions.assertNotSame(clone,seqMonitor.seq);
    switch(seqMonitor.nestedType){
      case STACK:
        if(seqMonitor.checkedType.checked){
          Assertions.assertTrue(clone instanceof BooleanSnglLnkSeq.CheckedStack);
          Assertions.assertEquals(0,((BooleanSnglLnkSeq.CheckedStack)clone).modCount);
        }else{
          Assertions.assertTrue(clone instanceof BooleanSnglLnkSeq.UncheckedStack);
        }
        break;
      case QUEUE:
        if(seqMonitor.checkedType.checked){
          Assertions.assertTrue(clone instanceof BooleanSnglLnkSeq.CheckedQueue);
          Assertions.assertEquals(0,((BooleanSnglLnkSeq.CheckedQueue)clone).modCount);
        }else{
          Assertions.assertTrue(clone instanceof BooleanSnglLnkSeq.UncheckedQueue);
        }
        break;
      default:
        throw new Error("Unknown nested type "+seqMonitor.nestedType);
    }
    var snglLnkSeqClone=(BooleanSnglLnkSeq)clone;
    var originalHead=((BooleanSnglLnkSeq)seqMonitor.seq).head;
    var cloneHead=snglLnkSeqClone.head;
    Assertions.assertEquals(numToAdd,snglLnkSeqClone.size);
    if(snglLnkSeqClone.size==0){
      Assertions.assertNull(cloneHead);
      if(seqMonitor.nestedType==NestedType.QUEUE){
        if(seqMonitor.checkedType.checked){
          Assertions.assertNull(((BooleanSnglLnkSeq.CheckedQueue)snglLnkSeqClone).tail);
        }else{
          Assertions.assertNull(((BooleanSnglLnkSeq.UncheckedQueue)snglLnkSeqClone).tail);
        }
      }
    }else{
      for(int i=snglLnkSeqClone.size;;cloneHead=cloneHead.next,originalHead=originalHead.next){
        Assertions.assertNotNull(cloneHead);
        Assertions.assertNotSame(cloneHead,originalHead);
        Assertions.assertEquals(cloneHead.val,originalHead.val);
        if(--i==0){
          if(seqMonitor.nestedType==NestedType.QUEUE){
            if(seqMonitor.checkedType.checked){
              Assertions.assertSame(cloneHead,((BooleanSnglLnkSeq.CheckedQueue)snglLnkSeqClone).tail);
            }else{
              Assertions.assertSame(cloneHead,((BooleanSnglLnkSeq.UncheckedQueue)snglLnkSeqClone).tail);
            }
          }
          Assertions.assertNull(cloneHead.next);
          Assertions.assertNull(originalHead.next);
          break;
        }
      }
    }
  }
  @org.junit.jupiter.api.Test
  public void testsize_void(){
    getBasicCollectionTestArgs().parallel().map(Arguments::get).forEach(args->{
        testsize_voidHelper((BooleanSnglLnkSeqMonitor)args[0],(int)args[1]);
    });
  }
  private static void testsize_voidHelper
  (BooleanSnglLnkSeqMonitor seqMonitor,int numToAdd){
    for(int i=0;i<numToAdd;++i){
      Assertions.assertEquals(i,seqMonitor.seq.size());
      seqMonitor.verifyStructuralIntegrity();
      seqMonitor.add(i);
    }
    var itrMonitor=seqMonitor.getItrMonitor();
    while(numToAdd>0){
      Assertions.assertEquals(numToAdd--,seqMonitor.seq.size());
      seqMonitor.verifyStructuralIntegrity();
      itrMonitor.iterateForward();
      itrMonitor.remove();
    }
    Assertions.assertEquals(numToAdd,seqMonitor.seq.size());
    seqMonitor.verifyStructuralIntegrity();
    seqMonitor.verifyPreAlloc().verifyNaturalAscending(numToAdd).verifyPostAlloc();
  }
  @org.junit.jupiter.api.Test
  public void testisEmpty_void(){
    getBasicCollectionTestArgs().parallel().map(Arguments::get).forEach(args->{
        testisEmpty_voidHelper((BooleanSnglLnkSeqMonitor)args[0],(int)args[1]);
    });
  }
  private static void testisEmpty_voidHelper
  (BooleanSnglLnkSeqMonitor seqMonitor,int numToAdd){
    for(int i=0;i<numToAdd;++i){
      Assertions.assertEquals(i==0,seqMonitor.seq.isEmpty());
      seqMonitor.verifyStructuralIntegrity();
      seqMonitor.add(i);
    }
    var itrMonitor=seqMonitor.getItrMonitor();
    while(numToAdd>0){
      Assertions.assertEquals((numToAdd--)==0,seqMonitor.seq.isEmpty());
      seqMonitor.verifyStructuralIntegrity();
      itrMonitor.iterateForward();
      itrMonitor.remove();
    }
    Assertions.assertTrue(seqMonitor.seq.isEmpty());
    seqMonitor.verifyStructuralIntegrity();
    seqMonitor.verifyPreAlloc().verifyNaturalAscending(numToAdd).verifyPostAlloc();
  }
  static Stream<Arguments> getadd_valArgs(){
    return ArgBuilder.buildSeqArgs((streamBuilder,nestedType,checkedType)->{
      for(var inputArgType:BooleanInputTestArgType.values()){
        streamBuilder.accept(Arguments.of(new BooleanSnglLnkSeqMonitor(nestedType,checkedType),inputArgType));
      }
    });
  }
  @org.junit.jupiter.api.Test
  public void testadd_val(){
    getadd_valArgs().parallel().map(Arguments::get).forEach(args->{
        testadd_valHelper((BooleanSnglLnkSeqMonitor)args[0],(BooleanInputTestArgType)args[1]);
    });
  }
  private static void testadd_valHelper
  (BooleanSnglLnkSeqMonitor seqMonitor,BooleanInputTestArgType inputArgType){
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seqMonitor.add(i,inputArgType));
      seqMonitor.verifyStructuralIntegrity();
    }
    seqMonitor.verifyPreAlloc().verifyNaturalAscending(inputArgType,100).verifyPostAlloc();
  }
  static Stream<Arguments> getStackpush_valArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var checkedType:CheckedType.values()){
      for(var inputArgType:BooleanInputTestArgType.values()){
        builder.accept(Arguments.of(new BooleanSnglLnkSeqMonitor(NestedType.STACK,checkedType),inputArgType));
      }
    }
    return builder.build();
  }
  @org.junit.jupiter.api.Test
  public void testStackpush_val(){
    getStackpush_valArgs().parallel().map(Arguments::get).forEach(args->{
        testStackpush_valHelper((BooleanSnglLnkSeqMonitor)args[0],(BooleanInputTestArgType)args[1]);
    });
  }
  private static void testStackpush_valHelper
  (BooleanSnglLnkSeqMonitor seqMonitor,BooleanInputTestArgType inputArgType){
    for(int i=0;i<100;++i){
      seqMonitor.push(i,inputArgType);
      seqMonitor.verifyStructuralIntegrity();
    }
    seqMonitor.verifyPreAlloc().verifyNaturalAscending(inputArgType,100).verifyPostAlloc();
  }
  static Stream<Arguments> getQueueoffer_valArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var checkedType:CheckedType.values()){
      for(var inputArgType:BooleanInputTestArgType.values()){
        builder.accept(Arguments.of(new BooleanSnglLnkSeqMonitor(NestedType.QUEUE,checkedType),inputArgType));
      }
    }
    return builder.build();
  }
  @org.junit.jupiter.api.Test
  public void testQueueoffer_val(){
    getQueueoffer_valArgs().parallel().map(Arguments::get).forEach(args->{
        testQueueoffer_valHelper((BooleanSnglLnkSeqMonitor)args[0],(BooleanInputTestArgType)args[1]);
    });
  }
  private static void testQueueoffer_valHelper
  (BooleanSnglLnkSeqMonitor seqMonitor,BooleanInputTestArgType inputArgType){
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seqMonitor.offer(i,inputArgType));
      seqMonitor.verifyStructuralIntegrity();
    }
    seqMonitor.verifyPreAlloc().verifyNaturalAscending(inputArgType,100).verifyPostAlloc();
  }
  @org.junit.jupiter.api.Test
  public void testcontains_val(){
    getQueryCollectionArguments().parallel().map(Arguments::get).forEach(args->{
        testcontains_valHelper((BooleanSnglLnkSeqMonitor)args[0],(QueryTester)args[1],(QueryCastType)args[2],(SequenceLocation)args[3],(int)args[4]
        );
    });
  }
  private static void testcontains_valHelper
  (BooleanSnglLnkSeqMonitor seqMonitor,QueryTester argType,QueryCastType queryCastType,SequenceLocation seqLocation,int seqSize
  ){
    if(seqSize>0){
      {
        switch(seqLocation){
          case BEGINNING:
            argType.initContainsBeginning(seqMonitor,seqSize);
            break;
          case MIDDLE:
            argType.initContainsMiddle(seqMonitor,seqSize);
            break;
          case END:
            argType.initContainsEnd(seqMonitor,seqSize);
            break;
          case IOBHI:
            argType.initDoesNotContain(seqMonitor,seqSize);
            break;
          default:
            throw new Error("Unknown seqLocation "+seqLocation);
        }
      }
    }
    Assertions.assertEquals(seqLocation!=SequenceLocation.IOBHI,argType.invokecontains(seqMonitor,queryCastType));
    seqMonitor.verifyStructuralIntegrity();
    seqMonitor.verifyPreAlloc().skip(seqSize).verifyPostAlloc();
  }
  @org.junit.jupiter.api.Test
  public void testsearch_val(){
    getQueryStackArguments().parallel().map(Arguments::get).forEach(args->{
        testsearch_valHelper((BooleanSnglLnkSeqMonitor)args[0],(QueryTester)args[1],(QueryCastType)args[2],(SequenceLocation)args[3],(int)args[4]
        );
    });
  }
  private static void testsearch_valHelper
  (BooleanSnglLnkSeqMonitor seqMonitor,QueryTester argType,QueryCastType queryCastType,SequenceLocation seqLocation,int seqSize
  ){
    int expectedIndex;
    if(seqSize>0){
      {
        switch(seqLocation){
          case BEGINNING:
            expectedIndex=argType.initContainsBeginning(seqMonitor,seqSize);
            expectedIndex=seqMonitor.expectedSeqSize-expectedIndex;
            break;
          case MIDDLE:
            expectedIndex=argType.initContainsMiddle(seqMonitor,seqSize);
            expectedIndex=seqMonitor.expectedSeqSize-expectedIndex;
            break;
          case END:
            expectedIndex=argType.initContainsEnd(seqMonitor,seqSize);
            expectedIndex=seqMonitor.expectedSeqSize-expectedIndex;
            break;
          case IOBHI:
            argType.initDoesNotContain(seqMonitor,seqSize);
            expectedIndex=-1;
            break;
          default:
            throw new Error("Unknown seqLocation "+seqLocation);
        }
      }
    }else{
      expectedIndex=-1;
    }
    Assertions.assertEquals(expectedIndex,argType.invokesearch(seqMonitor,queryCastType));
    seqMonitor.verifyStructuralIntegrity();
    seqMonitor.verifyPreAlloc().skip(seqSize).verifyPostAlloc();
  }
  @org.junit.jupiter.api.Test
  public void testremoveVal_val(){
    getQueryCollectionArguments().parallel().map(Arguments::get).forEach(args->{
        testremoveVal_valHelper((BooleanSnglLnkSeqMonitor)args[0],(QueryTester)args[1],(QueryCastType)args[2],(SequenceLocation)args[3],(int)args[4]
        );
    });
  }
  private static void testremoveVal_valHelper
  (BooleanSnglLnkSeqMonitor seqMonitor,QueryTester argType,QueryCastType queryCastType,SequenceLocation seqLocation,int seqSize
  ){
    if(seqSize>0){
      {
        switch(seqLocation){
          case BEGINNING:
            argType.initContainsBeginning(seqMonitor,seqSize);
            break;
          case MIDDLE:
            argType.initContainsMiddle(seqMonitor,seqSize);
            break;
          case END:
            argType.initContainsEnd(seqMonitor,seqSize);
            break;
          case IOBHI:
            argType.initDoesNotContain(seqMonitor,seqSize);
            break;
          default:
            throw new Error("Unknown seqLocation "+seqLocation);
        }
      }
    }
    boolean expectedResult;
    Assertions.assertEquals(expectedResult=seqLocation!=SequenceLocation.IOBHI,argType.invokeremoveVal(seqMonitor,queryCastType));
    if(expectedResult){
      --seqSize;
    }
    seqMonitor.verifyStructuralIntegrity();
    seqMonitor.verifyPreAlloc().skip(seqSize).verifyPostAlloc();
  }
  @org.junit.jupiter.api.Test
  public void testpeek_void(){
    getPeekPollAndPopArgs().parallel().map(Arguments::get).forEach(args->{
        testpeek_voidHelper((BooleanSnglLnkSeqMonitor)args[0],(BooleanOutputTestArgType)args[1]);
    });
  }
  private static void testpeek_voidHelper
  (BooleanSnglLnkSeqMonitor seqMonitor,BooleanOutputTestArgType outputArgType){
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
        testpoll_voidHelper((BooleanSnglLnkSeqMonitor)args[0],(BooleanOutputTestArgType)args[1]);
    });
  }
  private static void testpoll_voidHelper
  (BooleanSnglLnkSeqMonitor seqMonitor,BooleanOutputTestArgType outputArgType){
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
        testpop_voidHelper((BooleanSnglLnkSeqMonitor)args[0],(BooleanOutputTestArgType)args[1]);
    });
  }
  private static void testpop_voidHelper
  (BooleanSnglLnkSeqMonitor seqMonitor,BooleanOutputTestArgType outputArgType){
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
  static Stream<Arguments> getreadAndwriteObjectArgs(){
    return ArgBuilder.buildSeqArgs((streamBuilder,nestedType,checkedType)->{
      for(var monitoredFunctionGen:MonitoredFunctionGen.values()){
        if((checkedType.checked || monitoredFunctionGen.expectedException==null)&&(monitoredFunctionGen.appliesToRoot)){
          for(int seqSize:new int[]{0,1,100}){
            streamBuilder.accept(Arguments.of(new BooleanSnglLnkSeqMonitor(nestedType,checkedType),monitoredFunctionGen,seqSize
              ,0
            ));
            if(seqSize!=0){
              streamBuilder.accept(Arguments.of(new BooleanSnglLnkSeqMonitor(nestedType,checkedType),monitoredFunctionGen,seqSize,1));
            }
          }
        }
      }
    });
  }
  @org.junit.jupiter.api.Test
  public void testreadAndwriteObject(){
    getreadAndwriteObjectArgs().parallel().map(Arguments::get).forEach(args->{
        testreadAndwriteObjectHelper((BooleanSnglLnkSeqMonitor)args[0],(MonitoredFunctionGen)args[1],(int)args[2]
        ,(int)args[3]
        );
    });
  }
  private static void testreadAndwriteObjectHelper
  (BooleanSnglLnkSeqMonitor seqMonitor,MonitoredFunctionGen monitoredFunctionGen,int numToAdd
  ,int initVal
  )
  {
    for(int i=0;i<numToAdd;++i){
      seqMonitor.add(initVal+i); 
    }
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
      seqMonitor.verifyPreAlloc().verifyNaturalAscending(initVal,numToAdd).verifyPostAlloc();
      OmniCollection.OfBoolean readCol=null;
      try(var ois=new ObjectInputStream(new FileInputStream(file));){
        readCol=(OmniCollection.OfBoolean)ois.readObject();
      }catch(Exception e){
        Assertions.fail(e);
        return;
      }
      var itr=readCol.iterator();
      if(seqMonitor.nestedType.forwardIteration){
        for(int i=0;i<numToAdd;++i){
          Assertions.assertEquals(TypeConversionUtil.convertToboolean(initVal+i),itr.nextBoolean());
        }
      }else{
        for(int i=0;i<numToAdd;++i){
          Assertions.assertEquals(TypeConversionUtil.convertToboolean(numToAdd-i-1+initVal),itr.nextBoolean());
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
    seqMonitor.verifyStructuralIntegrity();
  }
  static Stream<Arguments> gettoArray_voidArgs(){
    return ArgBuilder.buildSeqArgs((streamBuilder,nestedType,checkedType)->{
      for(int seqSize:new int[]{0,1,100}){
        for(var outputArgType:BooleanOutputTestArgType.values()){
          streamBuilder.accept(Arguments.of(new BooleanSnglLnkSeqMonitor(nestedType,checkedType),seqSize,outputArgType));
        }
      }
    });
  }
  @org.junit.jupiter.api.Test
  public void testtoArray_void(){
    gettoArray_voidArgs().parallel().map(Arguments::get).forEach(args->{
        testtoArray_voidHelper((BooleanSnglLnkSeqMonitor)args[0],(int)args[1],(BooleanOutputTestArgType)args[2]);
    });
  }
  private static void testtoArray_voidHelper
  (BooleanSnglLnkSeqMonitor seqMonitor,int numToAdd,BooleanOutputTestArgType outputArgType){
    for(int i=0;i<numToAdd;++i){
      seqMonitor.add(i);
    }
    outputArgType.verifyToArray(seqMonitor.seq,numToAdd);
    seqMonitor.verifyStructuralIntegrity();
    seqMonitor.verifyPreAlloc().verifyNaturalAscending(numToAdd).verifyPostAlloc();
  }
  @org.junit.jupiter.api.Test
  public void testclear_void(){
    getBasicCollectionTestArgs().parallel().map(Arguments::get).forEach(args->{
        testclear_voidHelper((BooleanSnglLnkSeqMonitor)args[0],(int)args[1]);
    });
  }
  private static void testclear_voidHelper
  (BooleanSnglLnkSeqMonitor seqMonitor,int numToAdd){
    for(int i=0;i<numToAdd;++i){
      seqMonitor.add(i);
    }
    seqMonitor.clear();
    seqMonitor.verifyStructuralIntegrity();
    Assertions.assertTrue(seqMonitor.seq.isEmpty());
    seqMonitor.verifyPreAlloc().verifyPostAlloc();
  }
  static Stream<Arguments> getQueueelement_voidArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var checkedType:CheckedType.values()){
      for(var outputType:BooleanOutputTestArgType.values()){
        builder.accept(Arguments.of(new BooleanSnglLnkSeqMonitor(NestedType.QUEUE,checkedType),outputType));
      }
    }
    return builder.build();
  }
  @org.junit.jupiter.api.Test
  public void testQueueelement_void(){
    getQueueelement_voidArgs().parallel().map(Arguments::get).forEach(args->{
        testQueueelement_voidHelper((BooleanSnglLnkSeqMonitor)args[0],(BooleanOutputTestArgType)args[1]);
    });
  }
  private static void testQueueelement_voidHelper
  (BooleanSnglLnkSeqMonitor seqMonitor,BooleanOutputTestArgType outputArgType){
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
  static Stream<Arguments> gettoArray_IntFunctionArgs(){
    return ArgBuilder.buildSeqArgs((streamBuilder,nestedType,checkedType)->{
      for(var monitoredFunctionGen:MonitoredFunctionGen.values()){
        if((checkedType.checked || monitoredFunctionGen.expectedException==null)&&monitoredFunctionGen.appliesToRoot){
          for(int seqSize:new int[]{0,1,100}){
            streamBuilder.accept(Arguments.of(new BooleanSnglLnkSeqMonitor(nestedType,checkedType),monitoredFunctionGen,seqSize));
          }
        }
      }   
    });
  }
  @org.junit.jupiter.api.Test
  public void testtoArray_IntFunction(){
    gettoArray_IntFunctionArgs().parallel().map(Arguments::get).forEach(args->{
        testtoArray_IntFunctionHelper((BooleanSnglLnkSeqMonitor)args[0],(MonitoredFunctionGen)args[1],(int)args[2]);
    });
  }
  private static void testtoArray_IntFunctionHelper
  (BooleanSnglLnkSeqMonitor seqMonitor,MonitoredFunctionGen monitoredFunctionGen,int numToAdd){
    for(int i=0;i<numToAdd;++i){
      seqMonitor.add(i);
    }
    var arrConstructor=monitoredFunctionGen.getMonitoredArrayConstructor(seqMonitor);
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
    seqMonitor.verifyStructuralIntegrity();
    var verifyItr=seqMonitor.verifyPreAlloc();
    switch(monitoredFunctionGen){
      case NoThrow:
      case Throw:
        verifyItr.verifyNaturalAscending(numToAdd);
        break;
      case ModSeq:
      case ThrowModSeq:
        if(seqMonitor.nestedType.forwardIteration){
          verifyItr.verifyAscending(numToAdd).verifyIllegalAdd();
        }else{
          verifyItr.verifyIllegalAdd().verifyDescending(numToAdd);
        }
        break;
      default:
        throw new Error("Unknown monitoredFunctionGen "+monitoredFunctionGen);
    }
    verifyItr.verifyPostAlloc();
  }
  static Stream<Arguments> gettoArray_ObjectArrayArgs(){
    return ArgBuilder.buildSeqArgs((streamBuilder,nestedType,checkedType)->{
      for(int seqSize=0;seqSize<=15;seqSize+=5){
        for(int arrSize=0;arrSize<=20;arrSize+=5){
          streamBuilder.accept(Arguments.of(new BooleanSnglLnkSeqMonitor(nestedType,checkedType),seqSize,arrSize));
        }
      }
    });
  }
  @org.junit.jupiter.api.Test
  public void testtoArray_ObjectArray(){
    gettoArray_ObjectArrayArgs().parallel().map(Arguments::get).forEach(args->{
        testtoArray_ObjectArrayHelper((BooleanSnglLnkSeqMonitor)args[0],(int)args[1],(int)args[2]);
    });
  }
  private static void testtoArray_ObjectArrayHelper
  (BooleanSnglLnkSeqMonitor seqMonitor,int seqSize,int arrSize){
    for(int i=0;i<seqSize;++i){
      seqMonitor.add(i);
    }
    Boolean[] paramArr=new Boolean[arrSize];
    for(int i=seqSize,bound=seqSize+arrSize;i<bound;++i){
      paramArr[i-seqSize]=TypeConversionUtil.convertToBoolean(i);
    }
    var resultArr=seqMonitor.seq.toArray(paramArr);
    seqMonitor.verifyStructuralIntegrity();
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
    seqMonitor.verifyPreAlloc().verifyNaturalAscending(seqSize).verifyPostAlloc();
  }
    private static final int MAX_TOSTRING_LENGTH=5;
  static Stream<Arguments> getMASSIVEtoString_voidArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    final int seqLength=(OmniArray.MAX_ARR_SIZE/(MAX_TOSTRING_LENGTH+2))+1;
    BooleanSnglLnkNode head=new BooleanSnglLnkNode(TypeConversionUtil.convertToboolean(1));
    BooleanSnglLnkNode tail=head;
    for(int i=1;i<seqLength;++i){
      tail=tail.next=new BooleanSnglLnkNode(TypeConversionUtil.convertToboolean(1));
    }
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        builder.accept(Arguments.of(new BooleanSnglLnkSeqMonitor(nestedType,checkedType,head,seqLength,tail)));
      }
    }
    return builder.build();
  }
  @Tag("MASSIVEtoString")
  @org.junit.jupiter.params.ParameterizedTest
  @org.junit.jupiter.params.provider.MethodSource("getMASSIVEtoString_voidArgs")
  public void testMASSIVEtoString_void
  (BooleanSnglLnkSeqMonitor seqMonitor){
    seqMonitor.verifyMASSIVEString();
  }
  @org.junit.jupiter.api.Test
  public void testtoString_void(){
    gettoStringAndhashCode_voidArgs().parallel().map(Arguments::get).forEach(args->{
      testtoString_voidHelper((BooleanSnglLnkSeqMonitor)args[0],(int)args[1]
      );
    });
  }
  private static void testtoString_voidHelper
  (BooleanSnglLnkSeqMonitor seqMonitor,int numToAdd
  ){
    {
      for(int i=0;i<numToAdd;++i){
        seqMonitor.add(i);
      }
    }
    {
      var resultStr=seqMonitor.seq.toString();
      seqMonitor.verifyPreAlloc().verifyNaturalAscending(numToAdd).verifyPostAlloc();
      var itr=seqMonitor.seq.iterator();
      var arrList=new ArrayList<Object>();
      for(int i=0;i<numToAdd;++i){
        arrList.add(itr.next());
      }
      Assertions.assertEquals(arrList.toString(),resultStr);
    }
    seqMonitor.verifyStructuralIntegrity();
  }
  @org.junit.jupiter.api.Test
  public void testhashCode_void(){
    gettoStringAndhashCode_voidArgs().parallel().map(Arguments::get).forEach(args->{
      testhashCode_voidHelper((BooleanSnglLnkSeqMonitor)args[0],(int)args[1]
      );
    });
  }
  private static void testhashCode_voidHelper
  (BooleanSnglLnkSeqMonitor seqMonitor,int numToAdd
  ){
    {
      for(int i=0;i<numToAdd;++i){
        seqMonitor.add(i);
      }
    }
    {
      int resultHash=seqMonitor.seq.hashCode();
      seqMonitor.verifyPreAlloc().verifyNaturalAscending(numToAdd).verifyPostAlloc();
      var itr=seqMonitor.seq.iterator();
      int expectedHash=1;
      for(int i=0;i<numToAdd;++i){
        expectedHash=(expectedHash*31)+itr.next().hashCode();
      }
      Assertions.assertEquals(expectedHash,resultHash);
    }
    seqMonitor.verifyStructuralIntegrity();
  }
  @FunctionalInterface
  interface ArgBuilder{
    void buildArgs(Stream.Builder<Arguments> streamBuilder,NestedType nestedType,CheckedType checkedType);
    static Stream<Arguments> buildSeqArgs(ArgBuilder argBuilder){
      Stream.Builder<Arguments> streamBuilder=Stream.builder();
      for(var nestedType:NestedType.values()){
        for(var checkedType:CheckedType.values()){
          argBuilder.buildArgs(streamBuilder,nestedType,checkedType);
        }
      }
      return streamBuilder.build();
    }
  }
  static Stream<Arguments> getPeekPollAndPopArgs(){
    return ArgBuilder.buildSeqArgs((streamBuilder,nestedType,checkedType)->{
      for(var outputType:BooleanOutputTestArgType.values()){
        streamBuilder.accept(Arguments.of(new BooleanSnglLnkSeqMonitor(nestedType,checkedType),outputType));
      }
    });
  }
  static Stream<Arguments> gettoStringAndhashCode_voidArgs(){
   return ArgBuilder.buildSeqArgs((streamBuilder,nestedType,checkedType)->{
     for(int seqSize:new int[]{0,1,100}){
       streamBuilder.accept(Arguments.of(new BooleanSnglLnkSeqMonitor(nestedType,checkedType),seqSize));
     }
   });
  }
  private static class BooleanSnglLnkSeqMonitor implements BooleanSeqMonitor{
    NestedType nestedType;
    CheckedType checkedType;
    final BooleanSnglLnkSeq seq;
    int expectedSeqSize;
    int expectedSeqModCount;
    BooleanSnglLnkSeqMonitor(NestedType nestedType,CheckedType checkedType,BooleanSnglLnkNode head,int seqSize,BooleanSnglLnkNode tail){
      this.nestedType=nestedType;
      this.checkedType=checkedType;
      this.expectedSeqSize=seqSize;
      switch(nestedType){
        case QUEUE:
          this.seq=checkedType.checked?new BooleanSnglLnkSeq.CheckedQueue(head,seqSize,tail):new BooleanSnglLnkSeq.UncheckedQueue(head,seqSize,tail);
          break;
        case STACK:
          this.seq=checkedType.checked?new BooleanSnglLnkSeq.CheckedStack(head,seqSize):new BooleanSnglLnkSeq.UncheckedStack(head,seqSize);
          break;
        default:
          throw new Error("unknown nested type "+nestedType);
      }
    }
    BooleanSnglLnkSeqMonitor(NestedType nestedType,CheckedType checkedType){
      this.nestedType=nestedType;
      this.checkedType=checkedType;
      switch(nestedType){
        case QUEUE:
          this.seq=checkedType.checked?new BooleanSnglLnkSeq.CheckedQueue():new BooleanSnglLnkSeq.UncheckedQueue();
          break;
        case STACK:
          this.seq=checkedType.checked?new BooleanSnglLnkSeq.CheckedStack():new BooleanSnglLnkSeq.UncheckedStack();
          break;
        default:
          throw new Error("unknown nested type "+nestedType);
      }
    }
    public int getExpectedSeqSize(){
      return this.expectedSeqSize;
    }
    public void illegalAdd(PreModScenario preModScenario){
      switch(preModScenario){
        case ModSeq:
          BooleanInputTestArgType.ARRAY_TYPE.callCollectionAdd(seq,0);
          ++expectedSeqModCount;
          ++expectedSeqSize;
          break;
        case NoMod:
          break;
        default:
          throw new Error("Unknown preModScenario "+preModScenario);
      }
    }
    public boolean add(int val,BooleanInputTestArgType inputArgType){
      boolean ret=inputArgType.callCollectionAdd(seq,val);
      if(ret){
        ++expectedSeqSize;
        ++expectedSeqModCount;
      }
      return ret;
    }
    public boolean offer(int val,BooleanInputTestArgType inputArgType){
      boolean ret=inputArgType.callQueueOffer(seq,val);
      if(ret){
        ++expectedSeqSize;
        ++expectedSeqModCount;
      }
      return ret;
    }
    public void push(int val,BooleanInputTestArgType inputArgType){
      inputArgType.callStackPush(seq,val);
      ++expectedSeqSize;
      ++expectedSeqModCount;
    }
    public String toString(){
      StringBuilder builder=new StringBuilder();
      builder.append("BooleanSnglLnkSeq.").append(checkedType.checked?"Checked":"Unchecked");
      switch(nestedType){
        case STACK:
          builder.append("Stack");
          break;
        case QUEUE:
          builder.append("Queue");
          break;
        default:
          throw new Error("Unknown nestedType "+nestedType);
      }
      return builder.toString();
    }
    public boolean isEmpty(){
      return seq.isEmpty();
    }
    public void forEach(MonitoredConsumer action,FunctionCallType functionCallType){
      if(functionCallType==FunctionCallType.Boxed){
        seq.forEach((Consumer)action);
      }else
      {
        seq.forEach((BooleanConsumer)action);
      }
    }
    public void clear(){
      int seqSize=expectedSeqSize;
      seq.clear();
      if(seqSize!=0){
        expectedSeqSize=0;
        ++expectedSeqModCount;
      }
    }
    public void pop(int expectedVal,BooleanOutputTestArgType outputType){
      switch(nestedType)
      {
        case QUEUE:
          outputType.verifyQueueRemove(seq,expectedVal);
          break;
        case STACK:
          outputType.verifyStackPop(seq,expectedVal);
          break; 
        default:
          throw new Error("Unknown nested type "+nestedType);
      }
      --expectedSeqSize;
      ++expectedSeqModCount;
    }
    public void poll(int expectedVal,BooleanOutputTestArgType outputType){
      outputType.verifyPoll(seq,expectedSeqSize,expectedVal);
      if(expectedSeqSize!=0){
        --expectedSeqSize;
        ++expectedSeqModCount;
      }
    }
    public void verifyRemoveIf(MonitoredRemoveIfPredicate pred,FunctionCallType functionCallType,int expectedNumRemoved,OmniCollection.OfBoolean clone){
      int seqSize=expectedSeqSize;
      boolean retVal;
      if(functionCallType==FunctionCallType.Boxed){
        retVal=seq.removeIf((Predicate)pred);
      }
      else
      {
        retVal=seq.removeIf((BooleanPredicate)pred);
      }
      if(retVal){
        ++expectedSeqModCount;
        int numRemoved;
        int numTrue=0,numFalse=0;
        var cloneItr=clone.iterator();
        while(cloneItr.hasNext()){
          if(cloneItr.nextBoolean()){
            ++numTrue;
          }else{
            ++numFalse;
          }
        }
        if(pred.removedVals.contains(true)){
          if(pred.removedVals.contains(false)){
            numRemoved=seqSize;
            Assertions.assertTrue(seq.isEmpty());
          }else{
            numRemoved=numTrue;
            Assertions.assertFalse(seq.contains(true));
          }
        }else{
          numRemoved=numFalse;
          Assertions.assertFalse(seq.contains(false));
        }
        expectedSeqSize-=numRemoved;
        if(expectedNumRemoved!=-1){
          Assertions.assertEquals(expectedNumRemoved,numRemoved);
        }
      }else{
        Assertions.assertEquals(expectedSeqSize,clone.size());
        var seqItr=seq.iterator();
        var cloneItr=clone.iterator();
        for(int i=0;i<expectedSeqSize;++i){
          Assertions.assertEquals(seqItr.nextBoolean(),cloneItr.nextBoolean());
        }
      }
      verifyStructuralIntegrity();
    }
    public void writeObject(ObjectOutputStream oos) throws IOException{
      ((Externalizable)seq).writeExternal(oos);
    }
    public Object readObject(ObjectInputStream ois) throws IOException,ClassNotFoundException{
      return ois.readObject();
    }
    public void verifyStructuralIntegrity(){
      Assertions.assertEquals(expectedSeqSize,seq.size);
      if(checkedType.checked){
        switch(nestedType){
          case QUEUE:
            Assertions.assertEquals(expectedSeqModCount,((BooleanSnglLnkSeq.CheckedQueue)seq).modCount);
            break;
          case STACK:
            Assertions.assertEquals(expectedSeqModCount,((BooleanSnglLnkSeq.CheckedStack)seq).modCount);
            break;
          default:
            throw new Error("Unknown nested type "+nestedType);
        }
      }
      if(expectedSeqSize==0){
        Assertions.assertNull(seq.head);
      }else{
        BooleanSnglLnkNode node;
        Assertions.assertNotNull(node=seq.head);
        int i=expectedSeqSize;
        while(--i!=0){
          Assertions.assertNotNull(node=node.next);
        }
        Assertions.assertNull(node.next);
      }
    }
    class UncheckedSnglLnkSeqItrMonitor implements ItrMonitor{
      final OmniIterator.OfBoolean itr;
      BooleanSnglLnkNode expectedPrev;
      BooleanSnglLnkNode expectedCurr;
      BooleanSnglLnkNode expectedNext;
      UncheckedSnglLnkSeqItrMonitor(){
        this.expectedNext=seq.head;
        this.itr=seq.iterator();
      }
      public void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
        if(functionCallType==FunctionCallType.Boxed){
          itr.forEachRemaining((Consumer)action);
        }else
        {
          itr.forEachRemaining((BooleanConsumer)action);
        }
        BooleanSnglLnkNode expectedNext;
        if((expectedNext=this.expectedNext)!=null){
          BooleanSnglLnkNode expectedPrev,expectedCurr=this.expectedCurr;
          do{
            expectedPrev=expectedCurr;
          }while((expectedNext=(expectedCurr=expectedNext).next)!=null);
          this.expectedPrev=expectedPrev;
          this.expectedCurr=expectedCurr;
          this.expectedNext=null;
        }
      }
      public BooleanSeqMonitor getSeqMonitor(){
        return BooleanSnglLnkSeqMonitor.this;
      }
      public void verifyNext(int expectedVal,BooleanOutputTestArgType outputType){
        outputType.verifyItrNext(itr,expectedVal);
        final BooleanSnglLnkNode expectedNext;
        this.expectedNext=(expectedNext=this.expectedNext).next;
        this.expectedPrev=this.expectedCurr;
        this.expectedCurr=expectedNext;
      }
      public void verifyIteratorState(){
        Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.BooleanSnglLnkSeq.AbstractItr.prev(itr));
        Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.BooleanSnglLnkSeq.AbstractItr.curr(itr));
        Assertions.assertSame(expectedNext,FieldAndMethodAccessor.BooleanSnglLnkSeq.AbstractItr.next(itr));
        switch(nestedType)
        {
          case STACK:
            if(checkedType.checked)
            {
              Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanSnglLnkSeq.CheckedStack.Itr.parent(itr));
            }
            else
            {
              Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanSnglLnkSeq.UncheckedStack.Itr.parent(itr));
            }
            break;
          case QUEUE:
            if(checkedType.checked)
            {
              Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanSnglLnkSeq.CheckedQueue.Itr.parent(itr));
            }
            else
            {
              Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanSnglLnkSeq.UncheckedQueue.Itr.parent(itr));
            }
            break;
          default:
            throw new Error("Unknown nested type "+nestedType);
        }
      }
      public void iterateForward(){
        itr.next();
        final BooleanSnglLnkNode expectedNext;
        this.expectedNext=(expectedNext=this.expectedNext).next;
        this.expectedPrev=this.expectedCurr;
        this.expectedCurr=expectedNext;
      }
      public void remove(){
        itr.remove();
        --expectedSeqSize;
        ++expectedSeqModCount;
        this.expectedCurr=this.expectedPrev;
      }
      public boolean hasNext(){
        return itr.hasNext();
      }
    }
    class CheckedSnglLnkSeqItrMonitor extends UncheckedSnglLnkSeqItrMonitor
    {
      int expectedItrModCount;
      private CheckedSnglLnkSeqItrMonitor(){
        super();
        this.expectedItrModCount=expectedSeqModCount;
      }
      public void verifyIteratorState(){
        Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.BooleanSnglLnkSeq.AbstractItr.prev(itr));
        Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.BooleanSnglLnkSeq.AbstractItr.curr(itr));
        Assertions.assertSame(expectedNext,FieldAndMethodAccessor.BooleanSnglLnkSeq.AbstractItr.next(itr));
        switch(nestedType)
        {
          case STACK:
            if(checkedType.checked)
            {
              Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanSnglLnkSeq.CheckedStack.Itr.parent(itr));
              Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.BooleanSnglLnkSeq.CheckedStack.Itr.modCount(itr));
            }
            else
            {
              Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanSnglLnkSeq.UncheckedStack.Itr.parent(itr));
            }
            break;
          case QUEUE:
            if(checkedType.checked)
            {
              Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanSnglLnkSeq.CheckedQueue.Itr.parent(itr));
              Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.BooleanSnglLnkSeq.CheckedQueue.Itr.modCount(itr));
            }
            else
            {
              Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanSnglLnkSeq.UncheckedQueue.Itr.parent(itr));
            }
            break;
          default:
            throw new Error("Unknown nested type "+nestedType);
        }
      }
      @Override public void remove(){
        super.remove();
        ++expectedItrModCount;
      }
    }
    private static class SnglLnkSeqSequenceVerificationItr extends SequenceVerificationItr{
      BooleanSnglLnkNode curr;
      final BooleanSnglLnkSeqMonitor seqMonitor;
      private SnglLnkSeqSequenceVerificationItr(BooleanSnglLnkSeqMonitor seqMonitor,BooleanSnglLnkNode curr){
        this.seqMonitor=seqMonitor;
        this.curr=curr;
      }
      @Override public SequenceVerificationItr verifyPostAlloc(int expectedVal){
        Assertions.assertNull(curr);
        return this;
      }
      @Override public void verifyLiteralIndexAndIterate(boolean val){
        Assertions.assertEquals(val,curr.val);
        curr=curr.next;
      }
      @Override public void verifyIndexAndIterate(BooleanInputTestArgType inputArgType,int val){
        inputArgType.verifyVal(val,curr.val);
        curr=curr.next;
      }
      @Override public SequenceVerificationItr getPositiveOffset(int i){
        if(i<0){
          throw new Error("offset cannot be negative: "+i);
        }
        var currCopy=curr;
        while(i>0)
        {
          --i;
          currCopy=currCopy.next;
        }
        return new SnglLnkSeqSequenceVerificationItr(seqMonitor,currCopy);
      }
      @Override public SequenceVerificationItr skip(int i){
        if(i<0){
          throw new Error("offset cannot be negative: "+i);
        }
        while(i>0)
        {
          --i;
          curr=curr.next;
        }
        return this;
      }
      @Override public boolean equals(Object val){
        final SnglLnkSeqSequenceVerificationItr that;
        return val==this || (val instanceof SnglLnkSeqSequenceVerificationItr && (that=(SnglLnkSeqSequenceVerificationItr)val).seqMonitor.seq==this.seqMonitor.seq && that.curr==this.curr);
      }
      @Override public SequenceVerificationItr verifyRootPostAlloc(){
        Assertions.assertNull(curr);
        return this;
      }
      @Override public SequenceVerificationItr verifyParentPostAlloc(){
        Assertions.assertNull(curr);
        return this;
      }
      @Override public SequenceVerificationItr verifyPostAlloc(){
        Assertions.assertNull(curr);
        return this;
      }
      @Override public SequenceVerificationItr verifyPostAlloc(PreModScenario preModScenario){
        if(seqMonitor.nestedType.forwardIteration && preModScenario==PreModScenario.ModSeq){
          verifyIllegalAdd();
        }
        Assertions.assertNull(curr);
        return this;
      }
      public SequenceVerificationItr verifyNaturalAscending(int v,BooleanInputTestArgType inputArgType,int length)
      {
        if(seqMonitor.nestedType.forwardIteration)
        {
          return verifyAscending(v,inputArgType,length);
        }
        else
        {
          return verifyDescending(v+length,inputArgType,length);
        }
      }
    }
    public UncheckedSnglLnkSeqItrMonitor getItrMonitor(){
      return checkedType.checked
        ?new CheckedSnglLnkSeqItrMonitor()
        :new UncheckedSnglLnkSeqItrMonitor();
    }
    public SequenceVerificationItr verifyPreAlloc(PreModScenario preModScenario){
      var verifyItr=new SnglLnkSeqSequenceVerificationItr(this,seq.head);
      if(!nestedType.forwardIteration && preModScenario==PreModScenario.ModSeq){
        verifyItr.verifyIllegalAdd();
      }
      return verifyItr;
    }
    public SequenceVerificationItr verifyPreAlloc(){
      return new SnglLnkSeqSequenceVerificationItr(this,seq.head);
    }
    public SequenceVerificationItr verifyPreAlloc(int expectedVal){
      return new SnglLnkSeqSequenceVerificationItr(this,seq.head);
    }
    public String callToString(){
      return seq.toString();
    }
  }
  static enum NestedType{
    QUEUE(true),
    STACK(false);
    final boolean forwardIteration;
    NestedType(boolean forwardIteration){
      this.forwardIteration=forwardIteration;
    }
  }
  static enum QueryTester
  {
  Booleannull(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Boolean)(null));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(Boolean)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(Boolean)(null));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Boolean)(Boolean)(null));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Boolean)(Boolean)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Boolean)(Boolean)(null));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Boolean)(null));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Boolean)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Boolean)(null));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Bytenull(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Byte)(null));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(Byte)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(Byte)(null));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Byte)(Byte)(null));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Byte)(Byte)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Byte)(Byte)(null));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Byte)(null));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Byte)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Byte)(null));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Characternull(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Character)(null));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(Character)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(Character)(null));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(Character)(null));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Character)(Character)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Character)(Character)(null));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(null));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Character)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Character)(null));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Shortnull(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Short)(null));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(Short)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(Short)(null));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(Short)(null));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Short)(Short)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Short)(Short)(null));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(null));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Short)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Short)(null));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Integernull(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Integer)(null));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(Integer)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(Integer)(null));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(Integer)(null));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(Integer)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Integer)(Integer)(null));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(null));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Integer)(null));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Longnull(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Long)(null));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(Long)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(Long)(null));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(Long)(null));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(Long)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Long)(Long)(null));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(null));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Long)(null));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Floatnull(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Float)(null));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(Float)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(Float)(null));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(Float)(null));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(Float)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Float)(Float)(null));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(null));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Float)(null));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Doublenull(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Double)(null));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(Double)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(Double)(null));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(Double)(null));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(Double)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Double)(Double)(null));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(null));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Double)(null));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Objectnull(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Object)(null));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(Object)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(Object)(null));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Object)(null));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(Object)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(Object)(null));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(null));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(null));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Booleanfalse(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(boolean)(false));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(boolean)(false));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(boolean)(false));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Boolean)(boolean)(false));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Boolean)(boolean)(false));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Boolean)(boolean)(false));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((boolean)(false));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((boolean)(false));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((boolean)(false));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)(false));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)true);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Booleantrue(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(boolean)(true));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(boolean)(true));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(boolean)(true));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Boolean)(boolean)(true));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Boolean)(boolean)(true));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Boolean)(boolean)(true));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((boolean)(true));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((boolean)(true));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((boolean)(true));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)(true));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Byte0(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(byte)(0));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(byte)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(byte)(0));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Byte)(byte)(0));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Byte)(byte)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Byte)(byte)(0));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((byte)(0));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((byte)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((byte)(0));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((0)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)true);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Bytepos1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(byte)(1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(byte)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(byte)(1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Byte)(byte)(1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Byte)(byte)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Byte)(byte)(1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((byte)(1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((byte)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((byte)(1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)(true));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Bytepos2(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(byte)(2));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(byte)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(byte)(2));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Byte)(byte)(2));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Byte)(byte)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Byte)(byte)(2));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((byte)(2));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((byte)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((byte)(2));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((2)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Byteneg1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(byte)(-1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(byte)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(byte)(-1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Byte)(byte)(-1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Byte)(byte)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Byte)(byte)(-1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((byte)(-1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((byte)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((byte)(-1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((-1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Character0(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(char)(0));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(char)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(char)(0));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(char)(0));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Character)(char)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Character)(char)(0));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((char)(0));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((char)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((char)(0));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((0)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)true);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Characterpos1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(char)(1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(char)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(char)(1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(char)(1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Character)(char)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Character)(char)(1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((char)(1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((char)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((char)(1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)(true));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Characterpos2(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(char)(2));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(char)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(char)(2));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(char)(2));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Character)(char)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Character)(char)(2));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((char)(2));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((char)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((char)(2));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((2)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  CharacterMAX_BYTE_PLUS1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(char)(((char)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(char)(((char)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(char)(((char)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(char)(((char)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Character)(char)(((char)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Character)(char)(((char)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((char)(((char)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((char)(((char)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((char)(((char)Byte.MAX_VALUE)+1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((((char)Byte.MAX_VALUE)+1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  CharacterMAX_SHORT_PLUS1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(char)(((char)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(char)(((char)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(char)(((char)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(char)(((char)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Character)(char)(((char)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Character)(char)(((char)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((char)(((char)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((char)(((char)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((char)(((char)Short.MAX_VALUE)+1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((((char)Short.MAX_VALUE)+1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Short0(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(short)(0));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(short)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(short)(0));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(short)(0));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Short)(short)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Short)(short)(0));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((short)(0));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((short)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((short)(0));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((0)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)true);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Shortpos1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(short)(1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(short)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(short)(1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(short)(1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Short)(short)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Short)(short)(1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((short)(1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((short)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((short)(1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)(true));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Shortpos2(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(short)(2));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(short)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(short)(2));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(short)(2));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Short)(short)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Short)(short)(2));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((short)(2));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((short)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((short)(2));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((2)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Shortneg1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(short)(-1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(short)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(short)(-1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(short)(-1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Short)(short)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Short)(short)(-1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((short)(-1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((short)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((short)(-1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((-1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  ShortMAX_BYTE_PLUS1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(short)(((short)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(short)(((short)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(short)(((short)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(short)(((short)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Short)(short)(((short)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Short)(short)(((short)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((short)(((short)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((short)(((short)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((short)(((short)Byte.MAX_VALUE)+1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((((short)Byte.MAX_VALUE)+1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  ShortMIN_BYTE_MINUS1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(short)(((short)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(short)(((short)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(short)(((short)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(short)(((short)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Short)(short)(((short)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Short)(short)(((short)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((short)(((short)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((short)(((short)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((short)(((short)Byte.MIN_VALUE)-1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((((short)Byte.MIN_VALUE)-1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Integer0(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(0));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(int)(0));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(0));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Integer)(int)(0));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(0));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((int)(0));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((0)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)true);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Integerpos1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(int)(1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Integer)(int)(1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((int)(1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)(true));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Integerpos2(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(2));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(int)(2));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(2));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Integer)(int)(2));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(2));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((int)(2));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((2)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Integerneg1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(-1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(int)(-1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(-1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Integer)(int)(-1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(-1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((int)(-1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((-1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  IntegerMAX_BYTE_PLUS1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(((int)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(((int)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(int)(((int)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(((int)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(((int)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Integer)(int)(((int)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(((int)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(((int)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((int)(((int)Byte.MAX_VALUE)+1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((((int)Byte.MAX_VALUE)+1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  IntegerMIN_BYTE_MINUS1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(((int)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(((int)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(int)(((int)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(((int)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(((int)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Integer)(int)(((int)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(((int)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(((int)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((int)(((int)Byte.MIN_VALUE)-1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((((int)Byte.MIN_VALUE)-1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  IntegerMAX_SHORT_PLUS1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(((int)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(((int)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(int)(((int)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(((int)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(((int)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Integer)(int)(((int)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(((int)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(((int)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((int)(((int)Short.MAX_VALUE)+1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((((int)Short.MAX_VALUE)+1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  IntegerMIN_SHORT_MINUS1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(((int)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(((int)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(int)(((int)Short.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(((int)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(((int)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Integer)(int)(((int)Short.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(((int)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(((int)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((int)(((int)Short.MIN_VALUE)-1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((((int)Short.MIN_VALUE)-1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  IntegerMAX_CHAR_PLUS1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(((int)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(((int)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(int)(((int)Character.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(((int)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(((int)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Integer)(int)(((int)Character.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(((int)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(((int)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((int)(((int)Character.MAX_VALUE)+1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((((int)Character.MAX_VALUE)+1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  IntegerMAX_SAFE_INT_PLUS1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(TypeUtil.MAX_SAFE_INT+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(TypeUtil.MAX_SAFE_INT+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Integer)(int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(TypeUtil.MAX_SAFE_INT+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((int)(TypeUtil.MAX_SAFE_INT+1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((TypeUtil.MAX_SAFE_INT+1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  IntegerMIN_SAFE_INT_MINUS1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(TypeUtil.MIN_SAFE_INT-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(TypeUtil.MIN_SAFE_INT-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Integer)(int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(TypeUtil.MIN_SAFE_INT-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((int)(TypeUtil.MIN_SAFE_INT-1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((TypeUtil.MIN_SAFE_INT-1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Long0(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(0));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(long)(0));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(0));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Long)(long)(0));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(0));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((long)(0));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((0)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)true);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Longpos1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(long)(1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Long)(long)(1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((long)(1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)(true));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Longpos2(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(2));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(long)(2));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(2));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Long)(long)(2));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(2));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((long)(2));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((2)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Longneg1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(-1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(long)(-1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(-1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Long)(long)(-1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(-1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((long)(-1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((-1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  LongMAX_BYTE_PLUS1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(long)(((long)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Long)(long)(((long)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((long)(((long)Byte.MAX_VALUE)+1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((((long)Byte.MAX_VALUE)+1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  LongMIN_BYTE_MINUS1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(long)(((long)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Long)(long)(((long)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((long)(((long)Byte.MIN_VALUE)-1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((((long)Byte.MIN_VALUE)-1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  LongMAX_SHORT_PLUS1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(long)(((long)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Long)(long)(((long)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((long)(((long)Short.MAX_VALUE)+1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((((long)Short.MAX_VALUE)+1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  LongMIN_SHORT_MINUS1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(long)(((long)Short.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Long)(long)(((long)Short.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((long)(((long)Short.MIN_VALUE)-1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((((long)Short.MIN_VALUE)-1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  LongMAX_CHAR_PLUS1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(long)(((long)Character.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Long)(long)(((long)Character.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((long)(((long)Character.MAX_VALUE)+1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((((long)Character.MAX_VALUE)+1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  LongMAX_SAFE_INT_PLUS1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Long)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)TypeUtil.MAX_SAFE_INT)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((((long)TypeUtil.MAX_SAFE_INT)+1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  LongMIN_SAFE_INT_MINUS1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Long)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)TypeUtil.MIN_SAFE_INT)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((((long)TypeUtil.MIN_SAFE_INT)-1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  LongMAX_INT_PLUS1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)Integer.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(long)(((long)Integer.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)Integer.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Long)(long)(((long)Integer.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)Integer.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((long)(((long)Integer.MAX_VALUE)+1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((((long)Integer.MAX_VALUE)+1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  LongMIN_INT_MINUS1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)Integer.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(long)(((long)Integer.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)Integer.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Long)(long)(((long)Integer.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)Integer.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((long)(((long)Integer.MIN_VALUE)-1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((((long)Integer.MIN_VALUE)-1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  LongMAX_SAFE_LONG_PLUS1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Long)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)TypeUtil.MAX_SAFE_LONG)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((((long)TypeUtil.MAX_SAFE_LONG)+1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  LongMIN_SAFE_LONG_MINUS1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Long)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)TypeUtil.MIN_SAFE_LONG)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((((long)TypeUtil.MIN_SAFE_LONG)-1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Floatpos0(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(0.0F));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(0.0F));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(float)(0.0F));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(0.0F));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(0.0F));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Float)(float)(0.0F));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(0.0F));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(0.0F));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((float)(0.0F));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((0.0F)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)true);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Floatneg0(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(-0.0F));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(-0.0F));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(float)(-0.0F));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(-0.0F));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(-0.0F));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Float)(float)(-0.0F));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(-0.0F));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(-0.0F));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((float)(-0.0F));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((-0.0F)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)true);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Floatpos1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(float)(1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Float)(float)(1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((float)(1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)(true));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Floatpos2(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(2));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(float)(2));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(2));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Float)(float)(2));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(2));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((float)(2));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((2)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Floatneg1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(-1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(float)(-1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(-1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Float)(float)(-1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(-1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((float)(-1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((-1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  FloatMAX_BYTE_PLUS1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(((float)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(float)(((float)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(((float)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Float)(float)(((float)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(((float)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((float)(((float)Byte.MAX_VALUE)+1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((((float)Byte.MAX_VALUE)+1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  FloatMIN_BYTE_MINUS1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(((float)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(float)(((float)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(((float)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Float)(float)(((float)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(((float)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((float)(((float)Byte.MIN_VALUE)-1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((((float)Byte.MIN_VALUE)-1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  FloatMAX_SHORT_PLUS1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(((float)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(float)(((float)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(((float)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Float)(float)(((float)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(((float)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((float)(((float)Short.MAX_VALUE)+1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((((float)Short.MAX_VALUE)+1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  FloatMIN_SHORT_MINUS1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(((float)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(float)(((float)Short.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(((float)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Float)(float)(((float)Short.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(((float)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((float)(((float)Short.MIN_VALUE)-1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((((float)Short.MIN_VALUE)-1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  FloatMAX_CHAR_PLUS1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(((float)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(float)(((float)Character.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(((float)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Float)(float)(((float)Character.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(((float)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((float)(((float)Character.MAX_VALUE)+1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((((float)Character.MAX_VALUE)+1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  FloatMAX_INT_PLUS1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(((float)Integer.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(float)(((float)Integer.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(((float)Integer.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Float)(float)(((float)Integer.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(((float)Integer.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((float)(((float)Integer.MAX_VALUE)+1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((((float)Integer.MAX_VALUE)+1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  FloatMIN_INT_MINUS1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(((float)Integer.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(float)(((float)Integer.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(((float)Integer.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Float)(float)(((float)Integer.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(((float)Integer.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((float)(((float)Integer.MIN_VALUE)-1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((((float)Integer.MIN_VALUE)-1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  FloatMAX_LONG_PLUS1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Long.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(((float)Long.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(float)(((float)Long.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Long.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(((float)Long.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Float)(float)(((float)Long.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Long.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(((float)Long.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((float)(((float)Long.MAX_VALUE)+1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((((float)Long.MAX_VALUE)+1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  FloatMIN_LONG_MINUS1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Long.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(((float)Long.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(float)(((float)Long.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Long.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(((float)Long.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Float)(float)(((float)Long.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Long.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(((float)Long.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((float)(((float)Long.MIN_VALUE)-1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((((float)Long.MIN_VALUE)-1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  FloatMIN_FLOAT_VALUE(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(Float.MIN_VALUE));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(Float.MIN_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(float)(Float.MIN_VALUE));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(Float.MIN_VALUE));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(Float.MIN_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Float)(float)(Float.MIN_VALUE));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(Float.MIN_VALUE));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(Float.MIN_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((float)(Float.MIN_VALUE));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((Float.MIN_VALUE)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  FloatMAX_FLOAT_VALUE(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(Float.MAX_VALUE));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(Float.MAX_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(float)(Float.MAX_VALUE));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(Float.MAX_VALUE));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(Float.MAX_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Float)(float)(Float.MAX_VALUE));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(Float.MAX_VALUE));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(Float.MAX_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((float)(Float.MAX_VALUE));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((Float.MAX_VALUE)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  FloatNaN(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(Float.NaN));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(Float.NaN));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(float)(Float.NaN));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(Float.NaN));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(Float.NaN));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Float)(float)(Float.NaN));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(Float.NaN));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(Float.NaN));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((float)(Float.NaN));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((Float.NaN)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Doublepos0(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(0.0D));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(0.0D));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(double)(0.0D));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(0.0D));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(0.0D));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Double)(double)(0.0D));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(0.0D));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(0.0D));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((double)(0.0D));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((0.0D)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)true);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Doubleneg0(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(-0.0D));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(-0.0D));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(double)(-0.0D));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(-0.0D));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(-0.0D));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Double)(double)(-0.0D));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(-0.0D));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(-0.0D));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((double)(-0.0D));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((-0.0D)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)true);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Doublepos1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(double)(1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Double)(double)(1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((double)(1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)(true));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Doublepos2(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(2));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(double)(2));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(2));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Double)(double)(2));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(2));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((double)(2));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((2)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Doubleneg1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(-1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(double)(-1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(-1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Double)(double)(-1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(-1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((double)(-1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((-1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  DoubleMAX_BYTE_PLUS1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(double)(((double)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Double)(double)(((double)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((double)(((double)Byte.MAX_VALUE)+1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((((double)Byte.MAX_VALUE)+1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  DoubleMIN_BYTE_MINUS1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(double)(((double)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Double)(double)(((double)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((double)(((double)Byte.MIN_VALUE)-1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((((double)Byte.MIN_VALUE)-1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  DoubleMAX_SHORT_PLUS1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(double)(((double)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Double)(double)(((double)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((double)(((double)Short.MAX_VALUE)+1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((((double)Short.MAX_VALUE)+1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  DoubleMIN_SHORT_MINUS1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(double)(((double)Short.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Double)(double)(((double)Short.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((double)(((double)Short.MIN_VALUE)-1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((((double)Short.MIN_VALUE)-1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  DoubleMAX_CHAR_PLUS1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(double)(((double)Character.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Double)(double)(((double)Character.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((double)(((double)Character.MAX_VALUE)+1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((((double)Character.MAX_VALUE)+1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  DoubleMAX_SAFE_INT_PLUS1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Double)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)TypeUtil.MAX_SAFE_INT)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((((double)TypeUtil.MAX_SAFE_INT)+1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  DoubleMIN_SAFE_INT_MINUS1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Double)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)TypeUtil.MIN_SAFE_INT)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((((double)TypeUtil.MIN_SAFE_INT)-1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  DoubleMAX_INT_PLUS1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)Integer.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(double)(((double)Integer.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)Integer.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Double)(double)(((double)Integer.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)Integer.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((double)(((double)Integer.MAX_VALUE)+1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((((double)Integer.MAX_VALUE)+1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  DoubleMIN_INT_MINUS1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)Integer.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(double)(((double)Integer.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)Integer.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Double)(double)(((double)Integer.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)Integer.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((double)(((double)Integer.MIN_VALUE)-1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((((double)Integer.MIN_VALUE)-1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  DoubleMAX_LONG_PLUS1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Long.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)Long.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(double)(((double)Long.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Long.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)Long.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Double)(double)(((double)Long.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Long.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)Long.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((double)(((double)Long.MAX_VALUE)+1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((((double)Long.MAX_VALUE)+1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  DoubleMIN_LONG_MINUS1(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Long.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)Long.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(double)(((double)Long.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Long.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)Long.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Double)(double)(((double)Long.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Long.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)Long.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((double)(((double)Long.MIN_VALUE)-1));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((((double)Long.MIN_VALUE)-1)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  DoubleMIN_FLOAT_VALUE(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(Float.MIN_VALUE));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(Float.MIN_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(double)(Float.MIN_VALUE));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(Float.MIN_VALUE));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(Float.MIN_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Double)(double)(Float.MIN_VALUE));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(Float.MIN_VALUE));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(Float.MIN_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((double)(Float.MIN_VALUE));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((Float.MIN_VALUE)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  DoubleMAX_FLOAT_VALUE(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(Float.MAX_VALUE));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(Float.MAX_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(double)(Float.MAX_VALUE));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(Float.MAX_VALUE));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(Float.MAX_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Double)(double)(Float.MAX_VALUE));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(Float.MAX_VALUE));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(Float.MAX_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((double)(Float.MAX_VALUE));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((Float.MAX_VALUE)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  DoubleMIN_DOUBLE_VALUE(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(Double.MIN_VALUE));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(Double.MIN_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(double)(Double.MIN_VALUE));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(Double.MIN_VALUE));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(Double.MIN_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Double)(double)(Double.MIN_VALUE));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(Double.MIN_VALUE));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(Double.MIN_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((double)(Double.MIN_VALUE));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((Double.MIN_VALUE)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  DoubleMAX_DOUBLE_VALUE(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(Double.MAX_VALUE));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(Double.MAX_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(double)(Double.MAX_VALUE));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(Double.MAX_VALUE));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(Double.MAX_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Double)(double)(Double.MAX_VALUE));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(Double.MAX_VALUE));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(Double.MAX_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((double)(Double.MAX_VALUE));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((Double.MAX_VALUE)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  DoubleNaN(false){
    @Override boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(Double.NaN));}
    @Override boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(Double.NaN));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Object)(double)(Double.NaN));}
    @Override boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(Double.NaN));}
    @Override boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(Double.NaN));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((Double)(double)(Double.NaN));}
    @Override boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(Double.NaN));}
    @Override boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(Double.NaN));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor){return ((OmniStack.OfBoolean)seqMonitor.seq).search((double)(Double.NaN));}
    void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)((Double.NaN)==1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  ;
    final boolean isObjectNonNull;
    QueryTester(boolean isObjectNonNull){
      this.isObjectNonNull=isObjectNonNull;
    }
    boolean invokecontains(BooleanSnglLnkSeqMonitor seqMonitor,QueryCastType queryCastType){
      switch(queryCastType){
        case Unboxed:
          return invokecontainsUnboxed(seqMonitor);
        case ToBoxed:
          return invokecontainsBoxed(seqMonitor);
        case ToObject:
          return invokecontainsObject(seqMonitor);
        default:
          throw new Error("Unknown queryCastType "+queryCastType);
      }
    }
    boolean invokeremoveVal(BooleanSnglLnkSeqMonitor seqMonitor,QueryCastType queryCastType){
      switch(queryCastType){
        case Unboxed:
          return invokeremoveValUnboxed(seqMonitor);
        case ToBoxed:
          return invokeremoveValBoxed(seqMonitor);
        case ToObject:
          return invokeremoveValObject(seqMonitor);
        default:
          throw new Error("Unknown queryCastType "+queryCastType);
      }
    }
    int invokesearch(BooleanSnglLnkSeqMonitor seqMonitor,QueryCastType queryCastType){
      switch(queryCastType){
        case Unboxed:
          return invokesearchUnboxed(seqMonitor);
        case ToBoxed:
          return invokesearchBoxed(seqMonitor);
        case ToObject:
          return invokesearchObject(seqMonitor);
        default:
          throw new Error("Unknown queryCastType "+queryCastType);
      }
    }
    abstract boolean invokecontainsObject(BooleanSnglLnkSeqMonitor seqMonitor);
    abstract boolean invokecontainsBoxed(BooleanSnglLnkSeqMonitor seqMonitor);
    abstract boolean invokecontainsUnboxed(BooleanSnglLnkSeqMonitor seqMonitor);
    abstract boolean invokeremoveValObject(BooleanSnglLnkSeqMonitor seqMonitor);
    abstract boolean invokeremoveValBoxed(BooleanSnglLnkSeqMonitor seqMonitor);
    abstract boolean invokeremoveValUnboxed(BooleanSnglLnkSeqMonitor seqMonitor);
    abstract int invokesearchObject(BooleanSnglLnkSeqMonitor seqMonitor);
    abstract int invokesearchBoxed(BooleanSnglLnkSeqMonitor seqMonitor);
    abstract int invokesearchUnboxed(BooleanSnglLnkSeqMonitor seqMonitor);
    abstract void addEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor);
    abstract void addNotEqualsVal(BooleanSnglLnkSeqMonitor seqMonitor);
    void initDoesNotContain(BooleanSnglLnkSeqMonitor seqMonitor,int seqSize){
      for(int i=0;i<seqSize;++i){
        addNotEqualsVal(seqMonitor);
      }
    }
    int initContainsEnd(BooleanSnglLnkSeqMonitor seqMonitor,int seqSize){
      Assertions.assertEquals(0,seqMonitor.expectedSeqSize);
      for(int i=0;i<seqSize-1;++i){
        addNotEqualsVal(seqMonitor);
      }
      addEqualsVal(seqMonitor);
      return seqMonitor.expectedSeqSize-1;
    }
    int initContainsMiddle(BooleanSnglLnkSeqMonitor seqMonitor,int seqSize){
      Assertions.assertEquals(0,seqMonitor.expectedSeqSize);
      for(int i=0,bound=seqSize/2;i<bound;++i){
        addNotEqualsVal(seqMonitor);
      }
      addEqualsVal(seqMonitor);
      for(int i=(seqSize/2)+1;i<seqSize;++i){
        addNotEqualsVal(seqMonitor);
      }
      return seqMonitor.expectedSeqSize/2;
    }
    int initContainsBeginning(BooleanSnglLnkSeqMonitor seqMonitor,int seqSize){
      addEqualsVal(seqMonitor);
      for(int i=1;i<seqSize;++i){
        addNotEqualsVal(seqMonitor);
      }
      return 0;
    }
  };
  static void buildQueryArguments(Stream.Builder<Arguments> builder,NestedType nestedType){
    for(var checkedType:CheckedType.values()){
      for(var seqLocation:SequenceLocation.values()){
        if(seqLocation!=SequenceLocation.IOBLO){
          for(int seqSize:new int[]{0,1,100}){
            if(seqLocation==SequenceLocation.IOBHI || (seqSize>1 || (seqLocation==SequenceLocation.BEGINNING && seqSize>0))){
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
                      if(queryCastType!=QueryCastType.ToBoxed || (seqSize>0 && seqLocation.expectedException==null)){
                        continue;
                      }
                      break;
                    case Objectnull:
                      if(queryCastType!=QueryCastType.ToObject || (seqSize>0 && seqLocation.expectedException==null)){
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
                    if(seqSize>0 && seqLocation.expectedException==null){
                      continue;
                    }
                    //these values must necessarily return false
                  }
                  builder.accept(Arguments.of(new BooleanSnglLnkSeqMonitor(nestedType,checkedType),argType,queryCastType,seqLocation,seqSize));
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
  static Stream<Arguments> getQueryCollectionArguments(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var nestedType:NestedType.values()){
      buildQueryArguments(builder,nestedType);
    }
    return builder.build();
  }
  static Stream<Arguments> getBasicCollectionTestArgs(){
    return ArgBuilder.buildSeqArgs((streamBuilder,nestedType,checkedType)->{
      for(int seqSize:new int[]{0,1,100}){
        streamBuilder.accept(Arguments.of(new BooleanSnglLnkSeqMonitor(nestedType,checkedType),seqSize));
      }
    });
  }
}
