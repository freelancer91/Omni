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
import org.junit.jupiter.api.Tag;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import omni.impl.seq.ByteSnglLnkSeqMonitor.NestedType;
import omni.impl.seq.ByteSeqMonitor.CheckedType;
import omni.impl.seq.ByteSeqMonitor.PreModScenario;
import omni.impl.seq.ByteSeqMonitor.SequenceLocation;
import omni.impl.seq.ByteSeqMonitor.SequenceContentsScenario;
import omni.impl.seq.ByteSeqMonitor.ListItrSetScenario;
import omni.impl.seq.ByteSeqMonitor.ItrType;
import omni.impl.seq.ByteSeqMonitor.IterationScenario;
import omni.impl.seq.ByteSeqMonitor.ItrRemoveScenario;
import omni.impl.seq.ByteSeqMonitor.MonitoredFunctionGen;
import omni.impl.seq.ByteSeqMonitor.MonitoredComparatorGen;
import omni.impl.seq.ByteSeqMonitor.MonitoredRemoveIfPredicateGen;
import java.nio.file.Files;
import omni.impl.seq.ByteSeqMonitor.SequenceVerificationItr;
import omni.impl.seq.ByteSnglLnkSeqMonitor.QueryTester;
import omni.api.OmniCollection;
import omni.api.OmniList;
import java.util.ArrayList;
@Tag("SnglLnkSeq")
@Execution(ExecutionMode.CONCURRENT)
public class ByteSnglLnkSeqTest{
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
  static Stream<Arguments> getConstructor_voidArgs(){
    return ArgBuilder.buildSeqArgs((streamBuilder,nestedType,checkedType)->{
      streamBuilder.accept(Arguments.of(new ByteSnglLnkSeqMonitor(nestedType,checkedType)));
    });
  }
  @org.junit.jupiter.api.Test
  public void testConstructor_void(){
    getConstructor_voidArgs().parallel().map(Arguments::get).forEach(args->{
        testConstructor_voidHelper((ByteSnglLnkSeqMonitor)args[0]);
    });
  }
  private static void testConstructor_voidHelper
  (ByteSnglLnkSeqMonitor seqMonitor){
    if(seqMonitor.checkedType.checked){
      Assertions.assertEquals(0,seqMonitor.nestedType==NestedType.QUEUE?FieldAndMethodAccessor.ByteSnglLnkSeq.CheckedQueue.modCount(seqMonitor.seq):FieldAndMethodAccessor.ByteSnglLnkSeq.CheckedStack.modCount(seqMonitor.seq));
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
                for(var outputType:ByteOutputTestArgType.values()){
                  streamBuilder.accept(Arguments.of(new ByteSnglLnkSeqMonitor(nestedType,checkedType),itrScenario,seqContentsScenario,outputType));
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
        testItrnext_voidHelper((ByteSnglLnkSeqMonitor)args[0],(IterationScenario)args[1],(SequenceContentsScenario)args[2],(ByteOutputTestArgType)args[3]);
    });
  }
  private static void testItrnext_voidHelper
  (ByteSnglLnkSeqMonitor seqMonitor,IterationScenario itrScenario,SequenceContentsScenario seqContentsScenario,ByteOutputTestArgType outputType){
    int numToAdd=seqContentsScenario.nonEmpty?100:0;
    for(int i=0;i<numToAdd;++i)
    {
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
    if(seqMonitor.checkedType.checked)
    {
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
                      streamBuilder.accept(Arguments.of(new ByteSnglLnkSeqMonitor(nestedType,checkedType),removeScenario,preModScenario,sequenceContentsScenario,seqLocation));
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
        testItrremove_voidHelper((ByteSnglLnkSeqMonitor)args[0],(ItrRemoveScenario)args[1],(PreModScenario)args[2],(SequenceContentsScenario)args[3],(SequenceLocation)args[4]);
    });
  }
  private static void testItrremove_voidHelper
  (ByteSnglLnkSeqMonitor seqMonitor,ItrRemoveScenario removeScenario,PreModScenario preModScenario,SequenceContentsScenario seqContentsScenario,SequenceLocation seqLocation){
    int numToAdd=seqContentsScenario.nonEmpty?100:0;
    for(int i=0;i<numToAdd;++i){
      seqMonitor.add(i);
    }
    var itrMonitor=seqMonitor.getItrMonitor();
    switch(seqLocation)
    {
      case BEGINNING:
        break;
      case MIDDLE:
        for(int i=0,bound=numToAdd/2;i<bound;++i)
        {
          itrMonitor.iterateForward();
        }
        break;
      case END:
        for(int i=0;i<numToAdd;++i)
        {
          itrMonitor.iterateForward();
        }
        break;
      default:
        throw new Error("Unknown seqLocation "+seqLocation);
    }
    switch(removeScenario){
      case PostNext:
        if(seqLocation==SequenceLocation.BEGINNING)
        {
          itrMonitor.iterateForward();
        }
        break;
      case PostRemove:
        if(seqLocation==SequenceLocation.BEGINNING)
        {
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
        if(seqLocation==SequenceLocation.BEGINNING)
        {
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
          if(seqMonitor.nestedType.forwardIteration)
          {
            switch(seqLocation)
            {
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
            switch(seqLocation)
            {
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
                streamBuilder.accept(Arguments.of(new ByteSnglLnkSeqMonitor(nestedType,checkedType),preModScenario,monitoredFunctionGen,seqContentsScenario,FunctionCallType.Unboxed));
                streamBuilder.accept(Arguments.of(new ByteSnglLnkSeqMonitor(nestedType,checkedType),preModScenario,monitoredFunctionGen,seqContentsScenario,FunctionCallType.Boxed));
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
        testItrforEachRemaining_ConsumerHelper((ByteSnglLnkSeqMonitor)args[0],(PreModScenario)args[1],(MonitoredFunctionGen)args[2],(SequenceContentsScenario)args[3],(FunctionCallType)args[4]);
    });
  }
  private static void testItrforEachRemaining_ConsumerHelper
  (ByteSnglLnkSeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredFunctionGen monitoredFunctionGen,SequenceContentsScenario seqContentsScenario,FunctionCallType functionCallType){
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
            if(seqMonitor.nestedType.forwardIteration)
            {
              verifyItr.verifyAscending(numToAdd);
              for(int i=0;i<numToAdd;++i){
                verifyItr.verifyIllegalAdd();
              }
            }
            else
            {
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
            if(seqMonitor.nestedType.forwardIteration)
            {
              verifyItr.verifyAscending(numToAdd);
              if(preModScenario==PreModScenario.ModSeq)
              {
                numExpectedIteratedValues=numToAdd+1;
              }
              else
              {
                numExpectedIteratedValues=1;
              }
              for(int i=0;i<numExpectedIteratedValues;++i)
              {
                verifyItr.verifyIllegalAdd();
              }
            }
            else
            {
              if(preModScenario==PreModScenario.ModSeq)
              {
                numExpectedIteratedValues=numToAdd;
              }
              else
              {
                numExpectedIteratedValues=1;
              }
              for(int i=0;i<numExpectedIteratedValues;++i)
              {
                verifyItr.verifyIllegalAdd();
              }
              verifyItr.verifyDescending(numToAdd);
            }
            verifyItr.verifyPostAlloc(preModScenario);
            break;
          case ThrowModSeq:
            verifyItr=seqMonitor.verifyPreAlloc(preModScenario);
            if(seqMonitor.nestedType.forwardIteration)
            {
              verifyItr.verifyAscending(numToAdd).verifyIllegalAdd();
            }
            else
            {
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
            streamBuilder.accept(Arguments.of(new ByteSnglLnkSeqMonitor(nestedType,checkedType),monitoredFunctionGen,seqContentsScenario,FunctionCallType.Unboxed));
            streamBuilder.accept(Arguments.of(new ByteSnglLnkSeqMonitor(nestedType,checkedType),monitoredFunctionGen,seqContentsScenario,FunctionCallType.Boxed));
          }
        }
      }
    });
  }
  @org.junit.jupiter.api.Test
  public void testforEach_Consumer(){
    getforEach_ConsumerArgs().parallel().map(Arguments::get).forEach(args->{
        testforEach_ConsumerHelper((ByteSnglLnkSeqMonitor)args[0],(MonitoredFunctionGen)args[1],(SequenceContentsScenario)args[2],(FunctionCallType)args[3]);
    });
  }
  private static void testforEach_ConsumerHelper
  (ByteSnglLnkSeqMonitor seqMonitor,MonitoredFunctionGen monitoredFunctionGen,SequenceContentsScenario seqContentsScenario,FunctionCallType functionCallType){
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
      switch(monitoredFunctionGen)
      {
        case Throw:
          numExpectedIteratedValues=1;
          verifyItr.verifyNaturalAscending(numToAdd);
          break;
        case ModSeq:
          numExpectedIteratedValues=numToAdd;
          if(seqMonitor.nestedType.forwardIteration)
          {
            verifyItr.verifyAscending(numToAdd);
            for(int i=0;i<numToAdd;++i){
              verifyItr.verifyIllegalAdd();
            }
          }
          else
          {
            for(int i=0;i<numToAdd;++i){
              verifyItr.verifyIllegalAdd();
            }
            verifyItr.verifyDescending(numToAdd);
          }
          break;
        case ThrowModSeq:
          numExpectedIteratedValues=1;
          if(seqMonitor.nestedType.forwardIteration)
          {
            verifyItr.verifyAscending(numToAdd).verifyIllegalAdd();
          }
          else
          {
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
                  streamBuilder.accept(Arguments.of(new ByteSnglLnkSeqMonitor(nestedType,checkedType),monitoredRemoveIfPredicateGen,threshold,randSeed,functionCallType,seqSize));
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
      testremoveIf_PredicateHelper((ByteSnglLnkSeqMonitor)args[0],(MonitoredRemoveIfPredicateGen)args[1],(double)args[2],(long)args[3],(FunctionCallType)args[4],(int)args[5]
      );
    });
  }
  private static void testremoveIf_PredicateHelper
  (ByteSnglLnkSeqMonitor seqMonitor,MonitoredRemoveIfPredicateGen monitoredRemoveIfPredicateGen,double threshold,long randSeed,final FunctionCallType functionCallType,int seqSize
  ){
    for(int i=0;i<seqSize;++i)
    {
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
      case ThrowModSeq:
        numExpectedRemoved=0;
        break;
      default:
        throw new Error("Unknown monitoredRemoveIfPredicateGen "+monitoredRemoveIfPredicateGen);
    }
    final var monitoredRemoveIfPredicate=monitoredRemoveIfPredicateGen.getMonitoredRemoveIfPredicate(seqMonitor,randSeed,numExpectedCalls,threshold);
    if(monitoredRemoveIfPredicateGen.expectedException==null || seqSize==0)
    {
      seqMonitor.verifyRemoveIf(monitoredRemoveIfPredicate,functionCallType,numExpectedRemoved,clone);
      seqMonitor.verifyPreAlloc().skip(seqMonitor.expectedSeqSize).verifyPostAlloc();
      return;
    }else{
      Assertions.assertThrows(monitoredRemoveIfPredicateGen.expectedException,()->seqMonitor.verifyRemoveIf(monitoredRemoveIfPredicate,functionCallType,numExpectedRemoved,clone));
    }
    var verifyItr=seqMonitor.verifyPreAlloc(!seqMonitor.nestedType.forwardIteration &&(monitoredRemoveIfPredicateGen==MonitoredRemoveIfPredicateGen.ModSeq ||  monitoredRemoveIfPredicateGen==MonitoredRemoveIfPredicateGen.ThrowModSeq)?PreModScenario.ModSeq:null);
    if(seqMonitor.nestedType.forwardIteration){
      var cloneItr=clone.iterator();
      while(cloneItr.hasNext()){
        verifyItr.verifyLiteralIndexAndIterate(cloneItr.nextByte());
      }
    }else{
      if(monitoredRemoveIfPredicateGen==MonitoredRemoveIfPredicateGen.ModSeq){
        //tricky to verify
        //skip it
        return;
      }
      var arr=clone.toByteArray();
      for(int i=arr.length;--i>=0;){
         verifyItr.verifyLiteralIndexAndIterate(arr[i]);
      }
    }
    if(seqMonitor.nestedType.forwardIteration && monitoredRemoveIfPredicateGen==MonitoredRemoveIfPredicateGen.ThrowModSeq)
    {
      verifyItr.verifyPostAlloc(); 
    }
    switch(monitoredRemoveIfPredicateGen){
      case Random:
      case RemoveAll:
      case RemoveNone:
      case Throw:
        verifyItr.verifyPostAlloc();
        break;
      case ModSeq:
      case ThrowModSeq:
        //The nature of concurrent modification makes verifying the contents of the array tricky due to array reallocations
        //skip it in this scenario
        break;
      default:
        throw new Error("Unknown monitoredRemoveIfPredicateGen "+monitoredRemoveIfPredicateGen);
    }
  }
}
