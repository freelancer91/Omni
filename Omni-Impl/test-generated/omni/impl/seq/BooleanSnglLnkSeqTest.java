package omni.impl.seq;
import omni.function.BooleanConsumer;
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
import omni.impl.seq.AbstractBooleanSeqMonitor.CheckedType;
import omni.impl.seq.AbstractBooleanSeqMonitor.PreModScenario;
import omni.impl.seq.AbstractBooleanSeqMonitor.SequenceLocation;
import omni.impl.seq.AbstractBooleanSeqMonitor.IterationScenario;
import omni.impl.seq.AbstractBooleanSeqMonitor.ItrRemoveScenario;
import omni.impl.seq.AbstractBooleanSeqMonitor.MonitoredFunctionGen;
import omni.impl.seq.AbstractBooleanSeqMonitor.MonitoredRemoveIfPredicateGen;
import omni.impl.seq.AbstractBooleanSeqMonitor.QueryTester;
import java.nio.file.Files;
import omni.impl.seq.AbstractBooleanSeqMonitor.SequenceVerificationItr;
import omni.api.OmniCollection;
import java.util.ArrayList;
@SuppressWarnings({"rawtypes","unchecked"})
@Tag("SnglLnkSeqTest")
@Execution(ExecutionMode.CONCURRENT)
public class BooleanSnglLnkSeqTest{
  static Stream<Arguments> getConstructor_voidArgs(){
    return ArgBuilder.buildSeqArgs((streamBuilder,nestedType,checkedType)->{
      streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType)));
    });
  }
  @org.junit.jupiter.api.Test
  public void testConstructor_void(){
    getConstructor_voidArgs().parallel().map(Arguments::get).forEach(args->{
        testConstructor_voidHelper((SeqMonitor)args[0]);
    });
  }
  private static void testConstructor_voidHelper
  (SeqMonitor seqMonitor){
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
          for(int seqSize:AbstractBooleanSeqMonitor.FIB_SEQ){
            if(seqSize!=0 || itrScenario.validWithEmptySeq){
              if(itrScenario.preModScenario.appliesToRootItr){
                for(var outputType:BooleanOutputTestArgType.values()){
                  streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),itrScenario,seqSize,outputType));
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
        testItrnext_voidHelper((SeqMonitor)args[0],(IterationScenario)args[1],(int)args[2],(BooleanOutputTestArgType)args[3]);
    });
  }
  private static void testItrnext_voidHelper
  (SeqMonitor seqMonitor,IterationScenario itrScenario,int numToAdd,BooleanOutputTestArgType outputType){
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
          for(int seqSize:AbstractBooleanSeqMonitor.FIB_SEQ){
            if(seqSize!=0 || removeScenario.validWithEmptySeq){
              for(var preModScenario:PreModScenario.values()){
                if(preModScenario.appliesToRootItr && (checkedType.checked || preModScenario.expectedException==null)){
                  for(var seqLocation:SequenceLocation.values()){
                    if(seqLocation.expectedException==null &&
                      (seqSize!=0 || seqLocation==SequenceLocation.BEGINNING) &&
                      (seqLocation!=SequenceLocation.END || removeScenario!=ItrRemoveScenario.PostInit)&&
                      (removeScenario!=ItrRemoveScenario.PostInit || seqLocation==SequenceLocation.BEGINNING)
                    ){
                      streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),removeScenario,preModScenario,seqSize,seqLocation));
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
        testItrremove_voidHelper((SeqMonitor)args[0],(ItrRemoveScenario)args[1],(PreModScenario)args[2],(int)args[3],(SequenceLocation)args[4]);
    });
  }
  private static void testItrremove_voidHelper
  (SeqMonitor seqMonitor,ItrRemoveScenario removeScenario,PreModScenario preModScenario,int numToAdd,SequenceLocation seqLocation){
    for(int i=0;i<numToAdd;++i){
      seqMonitor.add(i);
    }
    var itrMonitor=seqMonitor.getItrMonitor();
    int numIterated=0;
    switch(seqLocation){
      case BEGINNING:
        break;
      case NEARBEGINNING:
        for(int i=0,bound=numToAdd/4;i<bound;++i){
          ++numIterated;
          itrMonitor.iterateForward();
        }
        break;
      case MIDDLE:
        for(int i=0,bound=numToAdd/2;i<bound;++i){
          ++numIterated;
          itrMonitor.iterateForward();
        }
        break;
      case NEAREND:
        for(int i=0,bound=(numToAdd/4)*3;i<bound;++i){
          ++numIterated;
          itrMonitor.iterateForward();
        }
        break;
      case END:
        for(int i=0;i<numToAdd;++i){
          ++numIterated;
          itrMonitor.iterateForward();
        }
        break;
      default:
        throw new Error("Unknown seqLocation "+seqLocation);
    }
    switch(removeScenario){
      case PostNext:
        if(numIterated==0){
          ++numIterated;
          itrMonitor.iterateForward();
        }
        break;
      case PostRemove:
        if(numIterated==0){
          ++numIterated;
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
        switch(seqLocation){
          case BEGINNING:
            Assertions.assertTrue(seqMonitor.isEmpty());
          case NEARBEGINNING:
          case MIDDLE:
          case NEAREND:
          case END:
            Assertions.assertEquals(numIterated-1,seqMonitor.seq.size());
            break;
          default:
            throw new Error("Unknown seqLocation "+seqLocation);
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
            verifyItr.verifyAscending(numIterated-1).verifyAscending(numIterated,numToAdd-numIterated);
          }else{
            verifyItr.verifyDescending(numToAdd,numIterated-1).verifyDescending(numToAdd-numIterated);
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
              for(int seqSize:AbstractBooleanSeqMonitor.FIB_SEQ){
                streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredFunctionGen,seqSize,FunctionCallType.Unboxed));
                streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),preModScenario,monitoredFunctionGen,seqSize,FunctionCallType.Boxed));
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
        testItrforEachRemaining_ConsumerHelper((SeqMonitor)args[0],(PreModScenario)args[1],(MonitoredFunctionGen)args[2],(int)args[3],(FunctionCallType)args[4]);
    });
  }
  private static void testItrforEachRemaining_ConsumerHelper
  (SeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredFunctionGen monitoredFunctionGen,int numToAdd,FunctionCallType functionCallType){
    for(int i=0;i<numToAdd;++i){
      seqMonitor.add(i);
    }
    var itrMonitor=seqMonitor.getItrMonitor();
    seqMonitor.illegalAdd(preModScenario);
    var monitoredConsumer=monitoredFunctionGen.getMonitoredConsumer(itrMonitor);
    int numExpectedIteratedValues;
    if(preModScenario.expectedException==null || numToAdd==0){
      if(monitoredFunctionGen.expectedException==null || numToAdd==0){
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
          for(int seqSize:AbstractBooleanSeqMonitor.FIB_SEQ){
            streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),monitoredFunctionGen,seqSize,FunctionCallType.Unboxed));
            streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),monitoredFunctionGen,seqSize,FunctionCallType.Boxed));
          }
        }
      }
    });
  }
  @org.junit.jupiter.api.Test
  public void testforEach_Consumer(){
    getforEach_ConsumerArgs().parallel().map(Arguments::get).forEach(args->{
        testforEach_ConsumerHelper((SeqMonitor)args[0],(MonitoredFunctionGen)args[1],(int)args[2],(FunctionCallType)args[3]);
    });
  }
  private static void testforEach_ConsumerHelper
  (SeqMonitor seqMonitor,MonitoredFunctionGen monitoredFunctionGen,int numToAdd,FunctionCallType functionCallType){
    for(int i=0;i<numToAdd;++i){
      seqMonitor.add(i);
    }
    var monitoredConsumer=monitoredFunctionGen.getMonitoredConsumer(seqMonitor);
    int numExpectedIteratedValues;
    if(monitoredFunctionGen.expectedException==null || numToAdd==0){
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
                    streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),monitoredRemoveIfPredicateGen,0.5,randSeed,functionCallType,seqSize,initVal,period));
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
      testremoveIf_PredicateHelper((SeqMonitor)args[0],(MonitoredRemoveIfPredicateGen)args[1],(double)args[2],(long)args[3],(FunctionCallType)args[4],(int)args[5]
      ,(int)args[6],(int)args[7]
      );
    });
  }
  private static void testremoveIf_PredicateHelper
  (SeqMonitor seqMonitor,MonitoredRemoveIfPredicateGen monitoredRemoveIfPredicateGen,double threshold,long randSeed,final FunctionCallType functionCallType,int seqSize
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
    seqMonitor.verifyStructuralIntegrity();
  }
  @org.junit.jupiter.api.Test
  public void testclone_void(){
    getBasicCollectionTestArgs().parallel().map(Arguments::get).forEach(args->{
        testclone_voidHelper((SeqMonitor)args[0],(int)args[1]);
    });
  }
  private static void testclone_voidHelper
  (SeqMonitor seqMonitor,int numToAdd){
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
        testsize_voidHelper((SeqMonitor)args[0],(int)args[1]);
    });
  }
  private static void testsize_voidHelper
  (SeqMonitor seqMonitor,int numToAdd){
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
        testisEmpty_voidHelper((SeqMonitor)args[0],(int)args[1]);
    });
  }
  private static void testisEmpty_voidHelper
  (SeqMonitor seqMonitor,int numToAdd){
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
        streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),inputArgType));
      }
    });
  }
  @org.junit.jupiter.api.Test
  public void testadd_val(){
    getadd_valArgs().parallel().map(Arguments::get).forEach(args->{
        testadd_valHelper((SeqMonitor)args[0],(BooleanInputTestArgType)args[1]);
    });
  }
  private static void testadd_valHelper
  (SeqMonitor seqMonitor,BooleanInputTestArgType inputArgType){
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
        builder.accept(Arguments.of(new SeqMonitor(NestedType.STACK,checkedType),inputArgType));
      }
    }
    return builder.build();
  }
  @org.junit.jupiter.api.Test
  public void testStackpush_val(){
    getStackpush_valArgs().parallel().map(Arguments::get).forEach(args->{
        testStackpush_valHelper((SeqMonitor)args[0],(BooleanInputTestArgType)args[1]);
    });
  }
  private static void testStackpush_valHelper
  (SeqMonitor seqMonitor,BooleanInputTestArgType inputArgType){
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
        builder.accept(Arguments.of(new SeqMonitor(NestedType.QUEUE,checkedType),inputArgType));
      }
    }
    return builder.build();
  }
  @org.junit.jupiter.api.Test
  public void testQueueoffer_val(){
    getQueueoffer_valArgs().parallel().map(Arguments::get).forEach(args->{
        testQueueoffer_valHelper((SeqMonitor)args[0],(BooleanInputTestArgType)args[1]);
    });
  }
  private static void testQueueoffer_valHelper
  (SeqMonitor seqMonitor,BooleanInputTestArgType inputArgType){
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seqMonitor.offer(i,inputArgType));
      seqMonitor.verifyStructuralIntegrity();
    }
    seqMonitor.verifyPreAlloc().verifyNaturalAscending(inputArgType,100).verifyPostAlloc();
  }
  @org.junit.jupiter.api.Test
  public void testcontains_val(){
    getQueryCollectionArguments().parallel().map(Arguments::get).forEach(args->{
        testcontains_valHelper((SeqMonitor)args[0],(QueryTester)args[1],(QueryCastType)args[2],(SequenceLocation)args[3],(int)args[4]
        );
    });
  }
  private static void testcontains_valHelper
  (SeqMonitor seqMonitor,QueryTester argType,QueryCastType queryCastType,SequenceLocation seqLocation,int seqSize
  ){
    if(seqSize>0){
      {
        switch(seqLocation){
          case BEGINNING:
            argType.initContainsBeginning(seqMonitor,seqSize,seqMonitor.nestedType.forwardIteration);
            break;
          case NEARBEGINNING:
            argType.initContainsNearBeginning(seqMonitor,seqSize,seqMonitor.nestedType.forwardIteration);
            break;
          case MIDDLE:
            argType.initContainsMiddle(seqMonitor,seqSize,seqMonitor.nestedType.forwardIteration);
            break;
          case NEAREND:
            argType.initContainsNearEnd(seqMonitor,seqSize,seqMonitor.nestedType.forwardIteration);
            break;
          case END:
            argType.initContainsEnd(seqMonitor,seqSize,seqMonitor.nestedType.forwardIteration);
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
        testsearch_valHelper((SeqMonitor)args[0],(QueryTester)args[1],(QueryCastType)args[2],(SequenceLocation)args[3],(int)args[4]
        );
    });
  }
  private static void testsearch_valHelper
  (SeqMonitor seqMonitor,QueryTester argType,QueryCastType queryCastType,SequenceLocation seqLocation,int seqSize
  ){
    int expectedIndex;
    if(seqSize>0){
      {
        switch(seqLocation){
          case BEGINNING:
            expectedIndex=argType.initContainsBeginning(seqMonitor,seqSize,seqMonitor.nestedType.forwardIteration);
            break;
          case NEARBEGINNING:
            expectedIndex=argType.initContainsNearBeginning(seqMonitor,seqSize,seqMonitor.nestedType.forwardIteration);
            break;
          case MIDDLE:
            expectedIndex=argType.initContainsMiddle(seqMonitor,seqSize,seqMonitor.nestedType.forwardIteration);
            break;
          case NEAREND:
            expectedIndex=argType.initContainsNearEnd(seqMonitor,seqSize,seqMonitor.nestedType.forwardIteration);
            break;
          case END:
            expectedIndex=argType.initContainsEnd(seqMonitor,seqSize,seqMonitor.nestedType.forwardIteration);
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
        testremoveVal_valHelper((SeqMonitor)args[0],(QueryTester)args[1],(QueryCastType)args[2],(SequenceLocation)args[3],(int)args[4]
        );
    });
  }
  private static void testremoveVal_valHelper
  (SeqMonitor seqMonitor,QueryTester argType,QueryCastType queryCastType,SequenceLocation seqLocation,int seqSize
  ){
    if(seqSize>0){
      {
        switch(seqLocation){
          case BEGINNING:
            argType.initContainsBeginning(seqMonitor,seqSize,seqMonitor.nestedType.forwardIteration);
            break;
          case NEARBEGINNING:
            argType.initContainsNearBeginning(seqMonitor,seqSize,seqMonitor.nestedType.forwardIteration);
            break;
          case MIDDLE:
            argType.initContainsMiddle(seqMonitor,seqSize,seqMonitor.nestedType.forwardIteration);
            break;
          case NEAREND:
            argType.initContainsNearEnd(seqMonitor,seqSize,seqMonitor.nestedType.forwardIteration);
            break;
          case END:
            argType.initContainsEnd(seqMonitor,seqSize,seqMonitor.nestedType.forwardIteration);
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
        testpeek_voidHelper((SeqMonitor)args[0],(BooleanOutputTestArgType)args[1]);
    });
  }
  private static void testpeek_voidHelper
  (SeqMonitor seqMonitor,BooleanOutputTestArgType outputArgType){
    if(seqMonitor.nestedType==NestedType.STACK)
    {
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
    else
    {
      if(seqMonitor.nestedType.forwardIteration){
        for(int i=0;i<100;++i){
          seqMonitor.add(i);
        }
        for(int i=0;i<100;++i){
          outputArgType.verifyPeek(seqMonitor.seq,100-i,i);
          seqMonitor.verifyStructuralIntegrity();
          seqMonitor.queueRemove(i,outputArgType);
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
  }
  @org.junit.jupiter.api.Test
  public void testpoll_void(){
    getPeekPollAndPopArgs().parallel().map(Arguments::get).forEach(args->{
        testpoll_voidHelper((SeqMonitor)args[0],(BooleanOutputTestArgType)args[1]);
    });
  }
  private static void testpoll_voidHelper
  (SeqMonitor seqMonitor,BooleanOutputTestArgType outputArgType){
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
        testpop_voidHelper((SeqMonitor)args[0],(BooleanOutputTestArgType)args[1]);
    });
  }
  private static void testpop_voidHelper
  (SeqMonitor seqMonitor,BooleanOutputTestArgType outputArgType){
    for(int i=0;i<100;++i){
      seqMonitor.add(i);
    }
    if(seqMonitor.nestedType==NestedType.STACK)
    {
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
    else
    {
      if(seqMonitor.nestedType.forwardIteration){
        for(int i=0;i<100;++i){
          seqMonitor.queueRemove(i,outputArgType);
          seqMonitor.verifyStructuralIntegrity();
        }
      }else{
        for(int i=100;--i>=0;){
          seqMonitor.queueRemove(i,outputArgType);
          seqMonitor.verifyStructuralIntegrity();
        }
      }
      if(seqMonitor.checkedType.checked){
        Assertions.assertThrows(NoSuchElementException.class,()->seqMonitor.queueRemove(0,outputArgType));
        seqMonitor.verifyStructuralIntegrity();
      }
    }
  }
  static Stream<Arguments> getreadAndwriteObjectArgs(){
    return ArgBuilder.buildSeqArgs((streamBuilder,nestedType,checkedType)->{
      for(var monitoredFunctionGen:MonitoredFunctionGen.values()){
        if((checkedType.checked || monitoredFunctionGen.expectedException==null)&&(monitoredFunctionGen.appliesToRoot)){
          for(int seqSize:AbstractBooleanSeqMonitor.FIB_SEQ){
            streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),monitoredFunctionGen,seqSize
              ,0
            ));
            if(seqSize!=0){
              streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),monitoredFunctionGen,seqSize,1));
            }
          }
        }
      }
    });
  }
  @org.junit.jupiter.api.Test
  public void testreadAndwriteObject(){
    getreadAndwriteObjectArgs().parallel().map(Arguments::get).forEach(args->{
        testreadAndwriteObjectHelper((SeqMonitor)args[0],(MonitoredFunctionGen)args[1],(int)args[2]
        ,(int)args[3]
        );
    });
  }
  private static void testreadAndwriteObjectHelper
  (SeqMonitor seqMonitor,MonitoredFunctionGen monitoredFunctionGen,int numToAdd
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
      for(int seqSize:AbstractBooleanSeqMonitor.FIB_SEQ){
        for(var outputArgType:BooleanOutputTestArgType.values()){
          streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),seqSize,outputArgType));
        }
      }
    });
  }
  @org.junit.jupiter.api.Test
  public void testtoArray_void(){
    gettoArray_voidArgs().parallel().map(Arguments::get).forEach(args->{
        testtoArray_voidHelper((SeqMonitor)args[0],(int)args[1],(BooleanOutputTestArgType)args[2]);
    });
  }
  private static void testtoArray_voidHelper
  (SeqMonitor seqMonitor,int numToAdd,BooleanOutputTestArgType outputArgType){
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
        testclear_voidHelper((SeqMonitor)args[0],(int)args[1]);
    });
  }
  private static void testclear_voidHelper
  (SeqMonitor seqMonitor,int numToAdd){
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
        builder.accept(Arguments.of(new SeqMonitor(NestedType.QUEUE,checkedType),outputType));
      }
    }
    return builder.build();
  }
  @org.junit.jupiter.api.Test
  public void testQueueelement_void(){
    getQueueelement_voidArgs().parallel().map(Arguments::get).forEach(args->{
        testQueueelement_voidHelper((SeqMonitor)args[0],(BooleanOutputTestArgType)args[1]);
    });
  }
  private static void testQueueelement_voidHelper
  (SeqMonitor seqMonitor,BooleanOutputTestArgType outputArgType){
    for(int i=0;i<100;++i){
      seqMonitor.add(i);
    }
    for(int i=0;i<100;++i){
      outputArgType.verifyQueueElement(seqMonitor.seq,i);
      seqMonitor.verifyStructuralIntegrity();
      seqMonitor.queueRemove(i,outputArgType);
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
          for(int seqSize:AbstractBooleanSeqMonitor.FIB_SEQ){
            streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),monitoredFunctionGen,seqSize));
          }
        }
      }   
    });
  }
  @org.junit.jupiter.api.Test
  public void testtoArray_IntFunction(){
    gettoArray_IntFunctionArgs().parallel().map(Arguments::get).forEach(args->{
        testtoArray_IntFunctionHelper((SeqMonitor)args[0],(MonitoredFunctionGen)args[1],(int)args[2]);
    });
  }
  private static void testtoArray_IntFunctionHelper
  (SeqMonitor seqMonitor,MonitoredFunctionGen monitoredFunctionGen,int numToAdd){
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
          streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),seqSize,arrSize));
        }
      }
    });
  }
  @org.junit.jupiter.api.Test
  public void testtoArray_ObjectArray(){
    gettoArray_ObjectArrayArgs().parallel().map(Arguments::get).forEach(args->{
        testtoArray_ObjectArrayHelper((SeqMonitor)args[0],(int)args[1],(int)args[2]);
    });
  }
  private static void testtoArray_ObjectArrayHelper
  (SeqMonitor seqMonitor,int seqSize,int arrSize){
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
  @Tag("MASSIVEtoString")
  @org.junit.jupiter.api.Test
  public void testMASSIVEtoString_void(){
    final int seqLength=(OmniArray.MAX_ARR_SIZE/(MAX_TOSTRING_LENGTH+2))+1;
    var head=new BooleanSnglLnkNode(TypeConversionUtil.convertToboolean(1));
    var tail=head;
    int numThreads=Runtime.getRuntime().availableProcessors();
    int threadSpan=seqLength/numThreads;
    var wayPointNodes=new BooleanSnglLnkNode[numThreads];
    wayPointNodes[0]=head;
    int nextWayPointIndex=threadSpan;
    int threadIndex=0;
    int threadBound=numThreads-1;
    for(int i=1;i<seqLength;++i)
    {
      tail=tail.next=new BooleanSnglLnkNode(TypeConversionUtil.convertToboolean(1));
      if(i==nextWayPointIndex){
        wayPointNodes[++threadIndex]=tail;
        if(threadIndex==threadBound){
          nextWayPointIndex=Integer.MIN_VALUE;
        }else{
          nextWayPointIndex+=threadSpan;
        }
      }
    }
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        var seqMonitor=new SeqMonitor(nestedType,checkedType,head,seqLength,tail);
        String string=seqMonitor.seq.toString();
        Assertions.assertEquals('[',string.charAt(0));
        Assertions.assertEquals(']',string.charAt(string.length()-1));
        seqMonitor.verifyStructuralIntegrity();
        var threads=new Thread[threadBound];
        for(threadIndex=0,nextWayPointIndex=0;threadIndex<threadBound;){
          var verifyItr=new SeqMonitor.SnglLnkSeqSequenceVerificationItr(seqMonitor,wayPointNodes[threadIndex]);
          final int finalWayPointBound=nextWayPointIndex+threadSpan;
          final int finalWayPointIndex=nextWayPointIndex;
          threads[threadIndex++]=new Thread(()->AbstractBooleanSeqMonitor.verifyLargeStr(string,finalWayPointIndex,finalWayPointBound,verifyItr));
          nextWayPointIndex=finalWayPointBound;
        }
        var verifyItr=new SeqMonitor.SnglLnkSeqSequenceVerificationItr(seqMonitor,wayPointNodes[threadIndex]);
        AbstractBooleanSeqMonitor.verifyLargeStr(string,nextWayPointIndex,seqLength,verifyItr);
        try{
          for(threadIndex=0;threadIndex<threadBound;++threadIndex){
            threads[threadIndex].join();
          }
        }catch(InterruptedException e){
          Assertions.fail(e);
        }
        verifyItr.verifyPostAlloc(1);
      }
    }
  }
  @org.junit.jupiter.api.Test
  public void testtoString_void(){
    gettoStringAndhashCode_voidArgs().parallel().map(Arguments::get).forEach(args->{
      testtoString_voidHelper((SeqMonitor)args[0],(int)args[1]
      );
    });
  }
  private static void testtoString_voidHelper
  (SeqMonitor seqMonitor,int numToAdd
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
      testhashCode_voidHelper((SeqMonitor)args[0],(int)args[1]
      );
    });
  }
  private static void testhashCode_voidHelper
  (SeqMonitor seqMonitor,int numToAdd
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
        streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),outputType));
      }
    });
  }
  static Stream<Arguments> gettoStringAndhashCode_voidArgs(){
   return ArgBuilder.buildSeqArgs((streamBuilder,nestedType,checkedType)->{
     for(int seqSize:AbstractBooleanSeqMonitor.FIB_SEQ){
       streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),seqSize));
     }
   });
  }
  private static class SeqMonitor extends AbstractBooleanSeqMonitor<BooleanSnglLnkSeq>{
    NestedType nestedType;
    SeqMonitor(NestedType nestedType,CheckedType checkedType,BooleanSnglLnkNode head,int seqSize,BooleanSnglLnkNode tail){
      super(checkedType);
      this.nestedType=nestedType;
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
    SeqMonitor(NestedType nestedType,CheckedType checkedType){
      super(checkedType);
      this.nestedType=nestedType;
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
    void illegalAdd(PreModScenario preModScenario){
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
    void verifyAddition()
    {
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
    void verifyBatchRemove(int numRemoved){
       expectedSeqSize-=numRemoved;
    }
    void writeObject(ObjectOutputStream oos) throws IOException{
      switch(nestedType){
        case QUEUE:
          if(checkedType.checked){
            FieldAndMethodAccessor.BooleanSnglLnkSeq.CheckedQueue.writeObject(seq,oos);
          }else{
            FieldAndMethodAccessor.BooleanSnglLnkSeq.UncheckedQueue.writeObject(seq,oos);
          }
          break;
        case STACK:
          if(checkedType.checked){
            FieldAndMethodAccessor.BooleanSnglLnkSeq.CheckedStack.writeObject(seq,oos);
          }else{
            FieldAndMethodAccessor.BooleanSnglLnkSeq.UncheckedStack.writeObject(seq,oos);
          }
          break;
        default:
          throw new Error("unknown nested type "+nestedType);
      }
    }
    void verifyRemoval(){
      ++expectedSeqModCount;
      --expectedSeqSize;
    }
    void verifyStructuralIntegrity(){
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
    class UncheckedSnglLnkSeqItrMonitor extends AbstractBooleanSeqMonitor.AbstractItrMonitor{
      BooleanSnglLnkNode expectedPrev;
      BooleanSnglLnkNode expectedCurr;
      BooleanSnglLnkNode expectedNext;
      UncheckedSnglLnkSeqItrMonitor(){
        super(ItrType.Itr,seq.iterator(),expectedSeqModCount);
        this.expectedNext=seq.head;
      }
      void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
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
      SeqMonitor getSeqMonitor(){
        return SeqMonitor.this;
      }
      void verifyNext(int expectedVal,BooleanOutputTestArgType outputType){
        outputType.verifyItrNext(itr,expectedVal);
        final BooleanSnglLnkNode expectedNext;
        this.expectedNext=(expectedNext=this.expectedNext).next;
        this.expectedPrev=this.expectedCurr;
        this.expectedCurr=expectedNext;
      }
      void verifyIteratorState(){
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
      void iterateForward(){
        itr.next();
        final BooleanSnglLnkNode expectedNext;
        this.expectedNext=(expectedNext=this.expectedNext).next;
        this.expectedPrev=this.expectedCurr;
        this.expectedCurr=expectedNext;
      }
      void remove(){
        itr.remove();
        verifyRemoval();
        this.expectedCurr=this.expectedPrev;
      }
    }
    class CheckedSnglLnkSeqItrMonitor extends UncheckedSnglLnkSeqItrMonitor
    {
      private CheckedSnglLnkSeqItrMonitor(){
        super();
      }
      void verifyIteratorState(){
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
      @Override void remove(){
        super.remove();
        ++expectedItrModCount;
      }
    }
    private static class SnglLnkSeqSequenceVerificationItr extends SequenceVerificationItr{
      BooleanSnglLnkNode curr;
      final SeqMonitor seqMonitor;
      private SnglLnkSeqSequenceVerificationItr(SeqMonitor seqMonitor,BooleanSnglLnkNode curr){
        this.seqMonitor=seqMonitor;
        this.curr=curr;
      }
      @Override SequenceVerificationItr verifyPostAlloc(int expectedVal){
        Assertions.assertNull(curr);
        return this;
      }
      @Override void verifyLiteralIndexAndIterate(boolean val){
        Assertions.assertEquals(val,curr.val);
        curr=curr.next;
      }
      @Override void verifyIndexAndIterate(BooleanInputTestArgType inputArgType,int val){
        inputArgType.verifyVal(val,curr.val);
        curr=curr.next;
      }
      @Override SequenceVerificationItr getOffset(int i){
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
      @Override SequenceVerificationItr skip(int i){
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
      @Override SequenceVerificationItr verifyRootPostAlloc(){
        Assertions.assertNull(curr);
        return this;
      }
      @Override SequenceVerificationItr verifyParentPostAlloc(){
        Assertions.assertNull(curr);
        return this;
      }
      @Override SequenceVerificationItr verifyPostAlloc(){
        Assertions.assertNull(curr);
        return this;
      }
      @Override SequenceVerificationItr verifyPostAlloc(PreModScenario preModScenario){
        if(seqMonitor.nestedType.forwardIteration && preModScenario==PreModScenario.ModSeq){
          verifyIllegalAdd();
        }
        Assertions.assertNull(curr);
        return this;
      }
      SequenceVerificationItr verifyNaturalAscending(int v,BooleanInputTestArgType inputArgType,int length)
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
    UncheckedSnglLnkSeqItrMonitor getItrMonitor(){
      return checkedType.checked
        ?new CheckedSnglLnkSeqItrMonitor()
        :new UncheckedSnglLnkSeqItrMonitor();
    }
    SequenceVerificationItr verifyPreAlloc(PreModScenario preModScenario){
      var verifyItr=new SnglLnkSeqSequenceVerificationItr(this,seq.head);
      if(!nestedType.forwardIteration && preModScenario==PreModScenario.ModSeq){
        verifyItr.verifyIllegalAdd();
      }
      return verifyItr;
    }
    SequenceVerificationItr verifyPreAlloc(){
      return new SnglLnkSeqSequenceVerificationItr(this,seq.head);
    }
    SequenceVerificationItr verifyPreAlloc(int expectedVal){
      return new SnglLnkSeqSequenceVerificationItr(this,seq.head);
    }
    void verifyFunctionalModification(){
      ++expectedSeqModCount;
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
  static void buildQueryArguments(Stream.Builder<Arguments> builder,NestedType nestedType){
    for(var checkedType:CheckedType.values()){
      for(var seqLocation:SequenceLocation.values()){
        if(seqLocation!=SequenceLocation.IOBLO){
          for(int seqSize:AbstractBooleanSeqMonitor.FIB_SEQ){
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
                  builder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),argType,queryCastType,seqLocation,seqSize));
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
      for(int seqSize:AbstractBooleanSeqMonitor.FIB_SEQ){
        streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),seqSize));
      }
    });
  }
}
