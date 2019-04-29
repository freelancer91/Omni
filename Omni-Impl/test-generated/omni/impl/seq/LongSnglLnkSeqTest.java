package omni.impl.seq;
import java.util.function.LongConsumer;
import java.util.function.Consumer;
import java.io.IOException;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import omni.impl.LongInputTestArgType;
import omni.impl.LongOutputTestArgType;
import org.junit.jupiter.params.provider.Arguments;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import omni.util.OmniArray;
import omni.impl.LongSnglLnkNode;
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
import omni.impl.seq.AbstractLongSeqMonitor.CheckedType;
import omni.impl.seq.AbstractLongSeqMonitor.PreModScenario;
import omni.impl.seq.AbstractLongSeqMonitor.SequenceLocation;
import omni.impl.seq.AbstractLongSeqMonitor.IterationScenario;
import omni.impl.seq.AbstractLongSeqMonitor.ItrRemoveScenario;
import omni.impl.seq.AbstractLongSeqMonitor.MonitoredFunctionGen;
import omni.impl.seq.AbstractLongSeqMonitor.MonitoredRemoveIfPredicateGen;
import omni.impl.seq.AbstractLongSeqMonitor.QueryTester;
import java.nio.file.Files;
import omni.impl.seq.AbstractLongSeqMonitor.SequenceVerificationItr;
import omni.api.OmniCollection;
import java.util.ArrayList;
@SuppressWarnings({"rawtypes","unchecked"})
@Tag("SnglLnkSeqTest")
@Execution(ExecutionMode.CONCURRENT)
public class LongSnglLnkSeqTest{
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
      Assertions.assertEquals(0,seqMonitor.nestedType==NestedType.QUEUE?FieldAndMethodAccessor.LongSnglLnkSeq.CheckedQueue.modCount(seqMonitor.seq):FieldAndMethodAccessor.LongSnglLnkSeq.CheckedStack.modCount(seqMonitor.seq));
    }
    Assertions.assertEquals(0,seqMonitor.seq.size);
    Assertions.assertNull(seqMonitor.seq.head);
  }
  static Stream<Arguments> getItrnext_voidArgs(){
    return ArgBuilder.buildSeqArgs((streamBuilder,nestedType,checkedType)->{
      for(var itrScenario:IterationScenario.values()){
        if(checkedType.checked || itrScenario==IterationScenario.NoMod){
          for(int seqSize:AbstractLongSeqMonitor.FIB_SEQ){
            if(seqSize!=0 || itrScenario.validWithEmptySeq){
              if(itrScenario.preModScenario.appliesToRootItr){
                for(var outputType:LongOutputTestArgType.values()){
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
        testItrnext_voidHelper((SeqMonitor)args[0],(IterationScenario)args[1],(int)args[2],(LongOutputTestArgType)args[3]);
    });
  }
  private static void testItrnext_voidHelper
  (SeqMonitor seqMonitor,IterationScenario itrScenario,int numToAdd,LongOutputTestArgType outputType){
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
          for(int seqSize:AbstractLongSeqMonitor.FIB_SEQ){
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
              for(int seqSize:AbstractLongSeqMonitor.FIB_SEQ){
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
          for(int seqSize:AbstractLongSeqMonitor.FIB_SEQ){
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
                for(double threshold:thresholdArr)
                {
                  streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),monitoredRemoveIfPredicateGen,threshold,randSeed,functionCallType,seqSize));
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
      );
    });
  }
  private static void testremoveIf_PredicateHelper
  (SeqMonitor seqMonitor,MonitoredRemoveIfPredicateGen monitoredRemoveIfPredicateGen,double threshold,long randSeed,final FunctionCallType functionCallType,int seqSize
  ){
    for(int i=0;i<seqSize;++i){
      seqMonitor.add(i);
    }
    final var clone=(OmniCollection.OfLong)seqMonitor.seq.clone();
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
    var clone=(OmniCollection.OfLong)seqMonitor.seq.clone();
    seqMonitor.verifyStructuralIntegrity();
    seqMonitor.verifyPreAlloc().verifyNaturalAscending(numToAdd).verifyPostAlloc();
    Assertions.assertNotSame(clone,seqMonitor.seq);
    switch(seqMonitor.nestedType){
      case STACK:
        if(seqMonitor.checkedType.checked){
          Assertions.assertTrue(clone instanceof LongSnglLnkSeq.CheckedStack);
          Assertions.assertEquals(0,((LongSnglLnkSeq.CheckedStack)clone).modCount);
        }else{
          Assertions.assertTrue(clone instanceof LongSnglLnkSeq.UncheckedStack);
        }
        break;
      case QUEUE:
        if(seqMonitor.checkedType.checked){
          Assertions.assertTrue(clone instanceof LongSnglLnkSeq.CheckedQueue);
          Assertions.assertEquals(0,((LongSnglLnkSeq.CheckedQueue)clone).modCount);
        }else{
          Assertions.assertTrue(clone instanceof LongSnglLnkSeq.UncheckedQueue);
        }
        break;
      default:
        throw new Error("Unknown nested type "+seqMonitor.nestedType);
    }
    var snglLnkSeqClone=(LongSnglLnkSeq)clone;
    var originalHead=((LongSnglLnkSeq)seqMonitor.seq).head;
    var cloneHead=snglLnkSeqClone.head;
    Assertions.assertEquals(numToAdd,snglLnkSeqClone.size);
    if(snglLnkSeqClone.size==0){
      Assertions.assertNull(cloneHead);
      if(seqMonitor.nestedType==NestedType.QUEUE){
        if(seqMonitor.checkedType.checked){
          Assertions.assertNull(((LongSnglLnkSeq.CheckedQueue)snglLnkSeqClone).tail);
        }else{
          Assertions.assertNull(((LongSnglLnkSeq.UncheckedQueue)snglLnkSeqClone).tail);
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
              Assertions.assertSame(cloneHead,((LongSnglLnkSeq.CheckedQueue)snglLnkSeqClone).tail);
            }else{
              Assertions.assertSame(cloneHead,((LongSnglLnkSeq.UncheckedQueue)snglLnkSeqClone).tail);
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
      for(var inputArgType:LongInputTestArgType.values()){
        streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),inputArgType));
      }
    });
  }
  @org.junit.jupiter.api.Test
  public void testadd_val(){
    getadd_valArgs().parallel().map(Arguments::get).forEach(args->{
        testadd_valHelper((SeqMonitor)args[0],(LongInputTestArgType)args[1]);
    });
  }
  private static void testadd_valHelper
  (SeqMonitor seqMonitor,LongInputTestArgType inputArgType){
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seqMonitor.add(i,inputArgType));
      seqMonitor.verifyStructuralIntegrity();
    }
    seqMonitor.verifyPreAlloc().verifyNaturalAscending(inputArgType,100).verifyPostAlloc();
  }
  static Stream<Arguments> getStackpush_valArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var checkedType:CheckedType.values()){
      for(var inputArgType:LongInputTestArgType.values()){
        builder.accept(Arguments.of(new SeqMonitor(NestedType.STACK,checkedType),inputArgType));
      }
    }
    return builder.build();
  }
  @org.junit.jupiter.api.Test
  public void testStackpush_val(){
    getStackpush_valArgs().parallel().map(Arguments::get).forEach(args->{
        testStackpush_valHelper((SeqMonitor)args[0],(LongInputTestArgType)args[1]);
    });
  }
  private static void testStackpush_valHelper
  (SeqMonitor seqMonitor,LongInputTestArgType inputArgType){
    for(int i=0;i<100;++i){
      seqMonitor.push(i,inputArgType);
      seqMonitor.verifyStructuralIntegrity();
    }
    seqMonitor.verifyPreAlloc().verifyNaturalAscending(inputArgType,100).verifyPostAlloc();
  }
  static Stream<Arguments> getQueueoffer_valArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var checkedType:CheckedType.values()){
      for(var inputArgType:LongInputTestArgType.values()){
        builder.accept(Arguments.of(new SeqMonitor(NestedType.QUEUE,checkedType),inputArgType));
      }
    }
    return builder.build();
  }
  @org.junit.jupiter.api.Test
  public void testQueueoffer_val(){
    getQueueoffer_valArgs().parallel().map(Arguments::get).forEach(args->{
        testQueueoffer_valHelper((SeqMonitor)args[0],(LongInputTestArgType)args[1]);
    });
  }
  private static void testQueueoffer_valHelper
  (SeqMonitor seqMonitor,LongInputTestArgType inputArgType){
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
        testpeek_voidHelper((SeqMonitor)args[0],(LongOutputTestArgType)args[1]);
    });
  }
  private static void testpeek_voidHelper
  (SeqMonitor seqMonitor,LongOutputTestArgType outputArgType){
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
        testpoll_voidHelper((SeqMonitor)args[0],(LongOutputTestArgType)args[1]);
    });
  }
  private static void testpoll_voidHelper
  (SeqMonitor seqMonitor,LongOutputTestArgType outputArgType){
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
        testpop_voidHelper((SeqMonitor)args[0],(LongOutputTestArgType)args[1]);
    });
  }
  private static void testpop_voidHelper
  (SeqMonitor seqMonitor,LongOutputTestArgType outputArgType){
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
          for(int seqSize:AbstractLongSeqMonitor.FIB_SEQ){
            streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),monitoredFunctionGen,seqSize
            ));
          }
        }
      }
    });
  }
  @org.junit.jupiter.api.Test
  public void testreadAndwriteObject(){
    getreadAndwriteObjectArgs().parallel().map(Arguments::get).forEach(args->{
        testreadAndwriteObjectHelper((SeqMonitor)args[0],(MonitoredFunctionGen)args[1],(int)args[2]
        );
    });
  }
  private static void testreadAndwriteObjectHelper
  (SeqMonitor seqMonitor,MonitoredFunctionGen monitoredFunctionGen,int numToAdd
  )
  {
    for(int i=0;i<numToAdd;++i){
      seqMonitor.add(i);
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
      seqMonitor.verifyPreAlloc().verifyNaturalAscending(numToAdd).verifyPostAlloc();
      OmniCollection.OfLong readCol=null;
      try(var ois=new ObjectInputStream(new FileInputStream(file));){
        readCol=(OmniCollection.OfLong)ois.readObject();
      }catch(Exception e){
        Assertions.fail(e);
        return;
      }
      var itr=readCol.iterator();
      if(seqMonitor.nestedType.forwardIteration){
        for(int i=0;i<numToAdd;++i){
          Assertions.assertEquals(TypeConversionUtil.convertTolong(i),itr.nextLong());
        }
      }else{
        for(int i=0;i<numToAdd;++i){
          Assertions.assertEquals(TypeConversionUtil.convertTolong(numToAdd-i-1),itr.nextLong());
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
      for(int seqSize:AbstractLongSeqMonitor.FIB_SEQ){
        for(var outputArgType:LongOutputTestArgType.values()){
          streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),seqSize,outputArgType));
        }
      }
    });
  }
  @org.junit.jupiter.api.Test
  public void testtoArray_void(){
    gettoArray_voidArgs().parallel().map(Arguments::get).forEach(args->{
        testtoArray_voidHelper((SeqMonitor)args[0],(int)args[1],(LongOutputTestArgType)args[2]);
    });
  }
  private static void testtoArray_voidHelper
  (SeqMonitor seqMonitor,int numToAdd,LongOutputTestArgType outputArgType){
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
      for(var outputType:LongOutputTestArgType.values()){
        builder.accept(Arguments.of(new SeqMonitor(NestedType.QUEUE,checkedType),outputType));
      }
    }
    return builder.build();
  }
  @org.junit.jupiter.api.Test
  public void testQueueelement_void(){
    getQueueelement_voidArgs().parallel().map(Arguments::get).forEach(args->{
        testQueueelement_voidHelper((SeqMonitor)args[0],(LongOutputTestArgType)args[1]);
    });
  }
  private static void testQueueelement_voidHelper
  (SeqMonitor seqMonitor,LongOutputTestArgType outputArgType){
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
          for(int seqSize:AbstractLongSeqMonitor.FIB_SEQ){
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
        Assertions.assertEquals(resultArr[i],(Object)itr.nextLong());
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
    Long[] paramArr=new Long[arrSize];
    for(int i=seqSize,bound=seqSize+arrSize;i<bound;++i){
      paramArr[i-seqSize]=TypeConversionUtil.convertToLong(i);
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
        Assertions.assertEquals(TypeConversionUtil.convertToLong(i+seqSize),resultArr[i]);
      }
    }else{
      Assertions.assertSame(paramArr,resultArr);
    }
    var itr=seqMonitor.seq.iterator();
    for(int i=0;i<seqSize;++i){
      Assertions.assertEquals((Object)itr.nextLong(),resultArr[i]);
    }
    seqMonitor.verifyPreAlloc().verifyNaturalAscending(seqSize).verifyPostAlloc();
  }
    private static final int MAX_TOSTRING_LENGTH=20;
  static Stream<Arguments> getMASSIVEtoString_voidArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    final int seqLength=(OmniArray.MAX_ARR_SIZE/(MAX_TOSTRING_LENGTH+2))+1;
    LongSnglLnkNode head=new LongSnglLnkNode(TypeConversionUtil.convertTolong(1));
    LongSnglLnkNode tail=head;
    for(int i=1;i<seqLength;++i){
      tail=tail.next=new LongSnglLnkNode(TypeConversionUtil.convertTolong(1));
    }
    for(var nestedType:NestedType.values()){
      for(var checkedType:CheckedType.values()){
        builder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType,head,seqLength,tail)));
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
      for(var outputType:LongOutputTestArgType.values()){
        streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),outputType));
      }
    });
  }
  static Stream<Arguments> gettoStringAndhashCode_voidArgs(){
   return ArgBuilder.buildSeqArgs((streamBuilder,nestedType,checkedType)->{
     for(int seqSize:AbstractLongSeqMonitor.FIB_SEQ){
       streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),seqSize));
     }
   });
  }
  private static class SeqMonitor extends AbstractLongSeqMonitor<LongSnglLnkSeq>{
    NestedType nestedType;
    SeqMonitor(NestedType nestedType,CheckedType checkedType,LongSnglLnkNode head,int seqSize,LongSnglLnkNode tail){
      super(checkedType);
      this.nestedType=nestedType;
      this.expectedSeqSize=seqSize;
      switch(nestedType){
        case QUEUE:
          this.seq=checkedType.checked?new LongSnglLnkSeq.CheckedQueue(head,seqSize,tail):new LongSnglLnkSeq.UncheckedQueue(head,seqSize,tail);
          break;
        case STACK:
          this.seq=checkedType.checked?new LongSnglLnkSeq.CheckedStack(head,seqSize):new LongSnglLnkSeq.UncheckedStack(head,seqSize);
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
          this.seq=checkedType.checked?new LongSnglLnkSeq.CheckedQueue():new LongSnglLnkSeq.UncheckedQueue();
          break;
        case STACK:
          this.seq=checkedType.checked?new LongSnglLnkSeq.CheckedStack():new LongSnglLnkSeq.UncheckedStack();
          break;
        default:
          throw new Error("unknown nested type "+nestedType);
      }
    }
    void illegalAdd(PreModScenario preModScenario){
      switch(preModScenario){
        case ModSeq:
          LongInputTestArgType.ARRAY_TYPE.callCollectionAdd(seq,0);
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
      builder.append("LongSnglLnkSeq.").append(checkedType.checked?"Checked":"Unchecked");
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
            FieldAndMethodAccessor.LongSnglLnkSeq.CheckedQueue.writeObject(seq,oos);
          }else{
            FieldAndMethodAccessor.LongSnglLnkSeq.UncheckedQueue.writeObject(seq,oos);
          }
          break;
        case STACK:
          if(checkedType.checked){
            FieldAndMethodAccessor.LongSnglLnkSeq.CheckedStack.writeObject(seq,oos);
          }else{
            FieldAndMethodAccessor.LongSnglLnkSeq.UncheckedStack.writeObject(seq,oos);
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
            Assertions.assertEquals(expectedSeqModCount,((LongSnglLnkSeq.CheckedQueue)seq).modCount);
            break;
          case STACK:
            Assertions.assertEquals(expectedSeqModCount,((LongSnglLnkSeq.CheckedStack)seq).modCount);
            break;
          default:
            throw new Error("Unknown nested type "+nestedType);
        }
      }
      if(expectedSeqSize==0){
        Assertions.assertNull(seq.head);
      }else{
        LongSnglLnkNode node;
        Assertions.assertNotNull(node=seq.head);
        int i=expectedSeqSize;
        while(--i!=0){
          Assertions.assertNotNull(node=node.next);
        }
        Assertions.assertNull(node.next);
      }
    }
    class UncheckedSnglLnkSeqItrMonitor extends AbstractLongSeqMonitor.AbstractItrMonitor{
      LongSnglLnkNode expectedPrev;
      LongSnglLnkNode expectedCurr;
      LongSnglLnkNode expectedNext;
      UncheckedSnglLnkSeqItrMonitor(){
        super(ItrType.Itr,seq.iterator(),expectedSeqModCount);
        this.expectedNext=seq.head;
      }
      void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
        if(functionCallType==FunctionCallType.Boxed){
          itr.forEachRemaining((Consumer)action);
        }else
        {
          itr.forEachRemaining((LongConsumer)action);
        }
        LongSnglLnkNode expectedNext;
        if((expectedNext=this.expectedNext)!=null){
          LongSnglLnkNode expectedPrev,expectedCurr=this.expectedCurr;
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
      void verifyNext(int expectedVal,LongOutputTestArgType outputType){
        outputType.verifyItrNext(itr,expectedVal);
        final LongSnglLnkNode expectedNext;
        this.expectedNext=(expectedNext=this.expectedNext).next;
        this.expectedPrev=this.expectedCurr;
        this.expectedCurr=expectedNext;
      }
      void verifyIteratorState(){
        Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.LongSnglLnkSeq.AbstractItr.prev(itr));
        Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.LongSnglLnkSeq.AbstractItr.curr(itr));
        Assertions.assertSame(expectedNext,FieldAndMethodAccessor.LongSnglLnkSeq.AbstractItr.next(itr));
        switch(nestedType)
        {
          case STACK:
            if(checkedType.checked)
            {
              Assertions.assertSame(seq,FieldAndMethodAccessor.LongSnglLnkSeq.CheckedStack.Itr.parent(itr));
            }
            else
            {
              Assertions.assertSame(seq,FieldAndMethodAccessor.LongSnglLnkSeq.UncheckedStack.Itr.parent(itr));
            }
            break;
          case QUEUE:
            if(checkedType.checked)
            {
              Assertions.assertSame(seq,FieldAndMethodAccessor.LongSnglLnkSeq.CheckedQueue.Itr.parent(itr));
            }
            else
            {
              Assertions.assertSame(seq,FieldAndMethodAccessor.LongSnglLnkSeq.UncheckedQueue.Itr.parent(itr));
            }
            break;
          default:
            throw new Error("Unknown nested type "+nestedType);
        }
      }
      void iterateForward(){
        itr.next();
        final LongSnglLnkNode expectedNext;
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
        Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.LongSnglLnkSeq.AbstractItr.prev(itr));
        Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.LongSnglLnkSeq.AbstractItr.curr(itr));
        Assertions.assertSame(expectedNext,FieldAndMethodAccessor.LongSnglLnkSeq.AbstractItr.next(itr));
        switch(nestedType)
        {
          case STACK:
            if(checkedType.checked)
            {
              Assertions.assertSame(seq,FieldAndMethodAccessor.LongSnglLnkSeq.CheckedStack.Itr.parent(itr));
              Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.LongSnglLnkSeq.CheckedStack.Itr.modCount(itr));
            }
            else
            {
              Assertions.assertSame(seq,FieldAndMethodAccessor.LongSnglLnkSeq.UncheckedStack.Itr.parent(itr));
            }
            break;
          case QUEUE:
            if(checkedType.checked)
            {
              Assertions.assertSame(seq,FieldAndMethodAccessor.LongSnglLnkSeq.CheckedQueue.Itr.parent(itr));
              Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.LongSnglLnkSeq.CheckedQueue.Itr.modCount(itr));
            }
            else
            {
              Assertions.assertSame(seq,FieldAndMethodAccessor.LongSnglLnkSeq.UncheckedQueue.Itr.parent(itr));
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
      LongSnglLnkNode curr;
      final SeqMonitor seqMonitor;
      private SnglLnkSeqSequenceVerificationItr(SeqMonitor seqMonitor,LongSnglLnkNode curr){
        this.seqMonitor=seqMonitor;
        this.curr=curr;
      }
      @Override SequenceVerificationItr verifyPostAlloc(int expectedVal){
        Assertions.assertNull(curr);
        return this;
      }
      @Override void verifyLiteralIndexAndIterate(long val){
        Assertions.assertEquals(val,curr.val);
        curr=curr.next;
      }
      @Override void verifyIndexAndIterate(LongInputTestArgType inputArgType,int val){
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
      SequenceVerificationItr verifyNaturalAscending(int v,LongInputTestArgType inputArgType,int length)
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
          for(int seqSize:AbstractLongSeqMonitor.FIB_SEQ){
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
      for(int seqSize:AbstractLongSeqMonitor.FIB_SEQ){
        streamBuilder.accept(Arguments.of(new SeqMonitor(nestedType,checkedType),seqSize));
      }
    });
  }
}
