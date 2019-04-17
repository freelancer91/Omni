package omni.impl.seq;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import omni.impl.IntInputTestArgType;
import omni.impl.IntOutputTestArgType;
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
import omni.impl.seq.IntSnglLnkSeqMonitor.NestedType;
import omni.impl.seq.IntSeqMonitor.CheckedType;
import omni.impl.seq.IntSeqMonitor.PreModScenario;
import omni.impl.seq.IntSeqMonitor.SequenceLocation;
import omni.impl.seq.IntSeqMonitor.SequenceContentsScenario;
import omni.impl.seq.IntSeqMonitor.ListItrSetScenario;
import omni.impl.seq.IntSeqMonitor.ItrType;
import omni.impl.seq.IntSeqMonitor.IterationScenario;
import omni.impl.seq.IntSeqMonitor.ItrRemoveScenario;
import omni.impl.seq.IntSeqMonitor.MonitoredFunctionGen;
import omni.impl.seq.IntSeqMonitor.MonitoredComparatorGen;
import omni.impl.seq.IntSeqMonitor.MonitoredRemoveIfPredicateGen;
import java.nio.file.Files;
import omni.impl.seq.IntSeqMonitor.SequenceVerificationItr;
import omni.impl.seq.IntSnglLnkSeqMonitor.QueryTester;
import omni.api.OmniCollection;
import omni.api.OmniList;
import java.util.ArrayList;
@Tag("SnglLnkSeq")
@Execution(ExecutionMode.CONCURRENT)
public class IntSnglLnkSeqTest{
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
      streamBuilder.accept(Arguments.of(new IntSnglLnkSeqMonitor(nestedType,checkedType)));
    });
  }
  @org.junit.jupiter.api.Test
  public void testConstructor_void(){
    getConstructor_voidArgs().parallel().map(Arguments::get).forEach(args->{
        testConstructor_voidHelper((IntSnglLnkSeqMonitor)args[0]);
    });
  }
  private static void testConstructor_voidHelper
  (IntSnglLnkSeqMonitor seqMonitor){
    if(seqMonitor.checkedType.checked){
      Assertions.assertEquals(0,seqMonitor.nestedType==NestedType.QUEUE?FieldAndMethodAccessor.IntSnglLnkSeq.CheckedQueue.modCount(seqMonitor.seq):FieldAndMethodAccessor.IntSnglLnkSeq.CheckedStack.modCount(seqMonitor.seq));
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
                for(var outputType:IntOutputTestArgType.values()){
                  streamBuilder.accept(Arguments.of(new IntSnglLnkSeqMonitor(nestedType,checkedType),itrScenario,seqContentsScenario,outputType));
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
        testItrnext_voidHelper((IntSnglLnkSeqMonitor)args[0],(IterationScenario)args[1],(SequenceContentsScenario)args[2],(IntOutputTestArgType)args[3]);
    });
  }
  private static void testItrnext_voidHelper
  (IntSnglLnkSeqMonitor seqMonitor,IterationScenario itrScenario,SequenceContentsScenario seqContentsScenario,IntOutputTestArgType outputType){
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
                      streamBuilder.accept(Arguments.of(new IntSnglLnkSeqMonitor(nestedType,checkedType),removeScenario,preModScenario,sequenceContentsScenario,seqLocation));
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
        testItrremove_voidHelper((IntSnglLnkSeqMonitor)args[0],(ItrRemoveScenario)args[1],(PreModScenario)args[2],(SequenceContentsScenario)args[3],(SequenceLocation)args[4]);
    });
  }
  private static void testItrremove_voidHelper
  (IntSnglLnkSeqMonitor seqMonitor,ItrRemoveScenario removeScenario,PreModScenario preModScenario,SequenceContentsScenario seqContentsScenario,SequenceLocation seqLocation){
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
                streamBuilder.accept(Arguments.of(new IntSnglLnkSeqMonitor(nestedType,checkedType),preModScenario,monitoredFunctionGen,seqContentsScenario,FunctionCallType.Unboxed));
                streamBuilder.accept(Arguments.of(new IntSnglLnkSeqMonitor(nestedType,checkedType),preModScenario,monitoredFunctionGen,seqContentsScenario,FunctionCallType.Boxed));
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
        testItrforEachRemaining_ConsumerHelper((IntSnglLnkSeqMonitor)args[0],(PreModScenario)args[1],(MonitoredFunctionGen)args[2],(SequenceContentsScenario)args[3],(FunctionCallType)args[4]);
    });
  }
  private static void testItrforEachRemaining_ConsumerHelper
  (IntSnglLnkSeqMonitor seqMonitor,PreModScenario preModScenario,MonitoredFunctionGen monitoredFunctionGen,SequenceContentsScenario seqContentsScenario,FunctionCallType functionCallType){
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
            streamBuilder.accept(Arguments.of(new IntSnglLnkSeqMonitor(nestedType,checkedType),monitoredFunctionGen,seqContentsScenario,FunctionCallType.Unboxed));
            streamBuilder.accept(Arguments.of(new IntSnglLnkSeqMonitor(nestedType,checkedType),monitoredFunctionGen,seqContentsScenario,FunctionCallType.Boxed));
          }
        }
      }
    });
  }
  @org.junit.jupiter.api.Test
  public void testforEach_Consumer(){
    getforEach_ConsumerArgs().parallel().map(Arguments::get).forEach(args->{
        testforEach_ConsumerHelper((IntSnglLnkSeqMonitor)args[0],(MonitoredFunctionGen)args[1],(SequenceContentsScenario)args[2],(FunctionCallType)args[3]);
    });
  }
  private static void testforEach_ConsumerHelper
  (IntSnglLnkSeqMonitor seqMonitor,MonitoredFunctionGen monitoredFunctionGen,SequenceContentsScenario seqContentsScenario,FunctionCallType functionCallType){
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
                  streamBuilder.accept(Arguments.of(new IntSnglLnkSeqMonitor(nestedType,checkedType),monitoredRemoveIfPredicateGen,threshold,randSeed,functionCallType,seqSize));
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
      testremoveIf_PredicateHelper((IntSnglLnkSeqMonitor)args[0],(MonitoredRemoveIfPredicateGen)args[1],(double)args[2],(long)args[3],(FunctionCallType)args[4],(int)args[5]
      );
    });
  }
  private static void testremoveIf_PredicateHelper
  (IntSnglLnkSeqMonitor seqMonitor,MonitoredRemoveIfPredicateGen monitoredRemoveIfPredicateGen,double threshold,long randSeed,final FunctionCallType functionCallType,int seqSize
  ){
    for(int i=0;i<seqSize;++i)
    {
      seqMonitor.add(i);
    }
    final var clone=(OmniCollection.OfInt)seqMonitor.seq.clone();
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
      //TODO verify contents of sequence in throw cases 
    }
  }
  @org.junit.jupiter.api.Test
  public void testclone_void(){
    getBasicCollectionTestArgs().parallel().map(Arguments::get).forEach(args->{
        testclone_voidHelper((IntSnglLnkSeqMonitor)args[0],(int)args[1]);
    });
  }
  private static void testclone_voidHelper
  (IntSnglLnkSeqMonitor seqMonitor,int numToAdd){
    for(int i=0;i<numToAdd;++i){
      seqMonitor.add(i);
    }
    var clone=(OmniCollection.OfInt)seqMonitor.seq.clone();
    seqMonitor.verifyStructuralIntegrity();
    seqMonitor.verifyPreAlloc().verifyNaturalAscending(numToAdd).verifyPostAlloc();
    Assertions.assertNotSame(clone,seqMonitor.seq);
    switch(seqMonitor.nestedType){
      case STACK:
        if(seqMonitor.checkedType.checked){
          Assertions.assertTrue(clone instanceof IntSnglLnkSeq.CheckedStack);
          Assertions.assertEquals(0,((IntSnglLnkSeq.CheckedStack)clone).modCount);
        }else{
          Assertions.assertTrue(clone instanceof IntSnglLnkSeq.UncheckedStack);
        }
        break;
      case QUEUE:
        if(seqMonitor.checkedType.checked){
          Assertions.assertTrue(clone instanceof IntSnglLnkSeq.CheckedQueue);
          Assertions.assertEquals(0,((IntSnglLnkSeq.CheckedQueue)clone).modCount);
        }else{
          Assertions.assertTrue(clone instanceof IntSnglLnkSeq.UncheckedQueue);
        }
        break;
      default:
        throw new Error("Unknown nested type "+seqMonitor.nestedType);
    }
    var snglLnkSeqClone=(IntSnglLnkSeq)clone;
    var originalHead=((IntSnglLnkSeq)seqMonitor.seq).head;
    var cloneHead=snglLnkSeqClone.head;
    Assertions.assertEquals(numToAdd,snglLnkSeqClone.size);
    if(snglLnkSeqClone.size==0)
    {
      Assertions.assertNull(cloneHead);
      if(seqMonitor.nestedType==NestedType.QUEUE)
      {
        if(seqMonitor.checkedType.checked)
        {
          Assertions.assertNull(((IntSnglLnkSeq.CheckedQueue)snglLnkSeqClone).tail);
        }
        else
        {
          Assertions.assertNull(((IntSnglLnkSeq.UncheckedQueue)snglLnkSeqClone).tail);
        }
      }
    }
    else
    {
      for(int i=snglLnkSeqClone.size;;cloneHead=cloneHead.next,originalHead=originalHead.next)
      {
        Assertions.assertNotNull(cloneHead);
        Assertions.assertNotSame(cloneHead,originalHead);
        Assertions.assertEquals(cloneHead.val,originalHead.val);
        if(--i==0)
        {
          if(seqMonitor.nestedType==NestedType.QUEUE)
          {
            if(seqMonitor.checkedType.checked)
            {
              Assertions.assertSame(cloneHead,((IntSnglLnkSeq.CheckedQueue)snglLnkSeqClone).tail);
            }
            else
            {
              Assertions.assertSame(cloneHead,((IntSnglLnkSeq.UncheckedQueue)snglLnkSeqClone).tail);
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
        testsize_voidHelper((IntSnglLnkSeqMonitor)args[0],(int)args[1]);
    });
  }
  private static void testsize_voidHelper
  (IntSnglLnkSeqMonitor seqMonitor,int numToAdd){
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
        testisEmpty_voidHelper((IntSnglLnkSeqMonitor)args[0],(int)args[1]);
    });
  }
  private static void testisEmpty_voidHelper
  (IntSnglLnkSeqMonitor seqMonitor,int numToAdd){
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
      for(var inputArgType:IntInputTestArgType.values()){
        streamBuilder.accept(Arguments.of(new IntSnglLnkSeqMonitor(nestedType,checkedType),inputArgType));
      }
    });
  }
  @org.junit.jupiter.api.Test
  public void testadd_val(){
    getadd_valArgs().parallel().map(Arguments::get).forEach(args->{
        testadd_valHelper((IntSnglLnkSeqMonitor)args[0],(IntInputTestArgType)args[1]);
    });
  }
  private static void testadd_valHelper
  (IntSnglLnkSeqMonitor seqMonitor,IntInputTestArgType inputArgType){
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seqMonitor.add(i,inputArgType));
      seqMonitor.verifyStructuralIntegrity();
    }
    seqMonitor.verifyPreAlloc().verifyNaturalAscending(inputArgType,100).verifyPostAlloc();
  }
  static Stream<Arguments> getStackpush_valArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var checkedType:CheckedType.values()){
      for(var inputArgType:IntInputTestArgType.values()){
        builder.accept(Arguments.of(new IntSnglLnkSeqMonitor(NestedType.STACK,checkedType),inputArgType));
      }
    }
    return builder.build();
  }
  @org.junit.jupiter.api.Test
  public void testStackpush_val(){
    getStackpush_valArgs().parallel().map(Arguments::get).forEach(args->{
        testStackpush_valHelper((IntSnglLnkSeqMonitor)args[0],(IntInputTestArgType)args[1]);
    });
  }
  private static void testStackpush_valHelper
  (IntSnglLnkSeqMonitor seqMonitor,IntInputTestArgType inputArgType){
    for(int i=0;i<100;++i){
      seqMonitor.push(i,inputArgType);
      seqMonitor.verifyStructuralIntegrity();
    }
    seqMonitor.verifyPreAlloc().verifyNaturalAscending(inputArgType,100).verifyPostAlloc();
  }
  static Stream<Arguments> getQueueoffer_valArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var checkedType:CheckedType.values()){
      for(var inputArgType:IntInputTestArgType.values()){
        builder.accept(Arguments.of(new IntSnglLnkSeqMonitor(NestedType.QUEUE,checkedType),inputArgType));
      }
    }
    return builder.build();
  }
  @org.junit.jupiter.api.Test
  public void testQueueoffer_val(){
    getQueueoffer_valArgs().parallel().map(Arguments::get).forEach(args->{
        testQueueoffer_valHelper((IntSnglLnkSeqMonitor)args[0],(IntInputTestArgType)args[1]);
    });
  }
  private static void testQueueoffer_valHelper
  (IntSnglLnkSeqMonitor seqMonitor,IntInputTestArgType inputArgType){
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seqMonitor.offer(i,inputArgType));
      seqMonitor.verifyStructuralIntegrity();
    }
    seqMonitor.verifyPreAlloc().verifyNaturalAscending(inputArgType,100).verifyPostAlloc();
  }
  @org.junit.jupiter.api.Test
  public void testcontains_val(){
    getQueryCollectionArguments().parallel().map(Arguments::get).forEach(args->{
        testcontains_valHelper((IntSnglLnkSeqMonitor)args[0],(QueryTester)args[1],(QueryCastType)args[2],(SequenceLocation)args[3],(int)args[4]
        );
    });
  }
  private static void testcontains_valHelper
  (IntSnglLnkSeqMonitor seqMonitor,QueryTester argType,QueryCastType queryCastType,SequenceLocation seqLocation,int seqSize
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
        testsearch_valHelper((IntSnglLnkSeqMonitor)args[0],(QueryTester)args[1],(QueryCastType)args[2],(SequenceLocation)args[3],(int)args[4]
        );
    });
  }
  private static void testsearch_valHelper
  (IntSnglLnkSeqMonitor seqMonitor,QueryTester argType,QueryCastType queryCastType,SequenceLocation seqLocation,int seqSize
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
        testremoveVal_valHelper((IntSnglLnkSeqMonitor)args[0],(QueryTester)args[1],(QueryCastType)args[2],(SequenceLocation)args[3],(int)args[4]
        );
    });
  }
  private static void testremoveVal_valHelper
  (IntSnglLnkSeqMonitor seqMonitor,QueryTester argType,QueryCastType queryCastType,SequenceLocation seqLocation,int seqSize
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
        testpeek_voidHelper((IntSnglLnkSeqMonitor)args[0],(IntOutputTestArgType)args[1]);
    });
  }
  private static void testpeek_voidHelper
  (IntSnglLnkSeqMonitor seqMonitor,IntOutputTestArgType outputArgType){
    if(seqMonitor.nestedType.forwardIteration)
    {
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
    }
    else
    {
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
        testpoll_voidHelper((IntSnglLnkSeqMonitor)args[0],(IntOutputTestArgType)args[1]);
    });
  }
  private static void testpoll_voidHelper
  (IntSnglLnkSeqMonitor seqMonitor,IntOutputTestArgType outputArgType){
    for(int i=0;i<100;++i){
      seqMonitor.add(i);
    }
    if(seqMonitor.nestedType.forwardIteration)
    {
      for(int i=0;i<100;++i){
        seqMonitor.poll(i,outputArgType);
        seqMonitor.verifyStructuralIntegrity();
      }
    }
    else
    {
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
        testpop_voidHelper((IntSnglLnkSeqMonitor)args[0],(IntOutputTestArgType)args[1]);
    });
  }
  private static void testpop_voidHelper
  (IntSnglLnkSeqMonitor seqMonitor,IntOutputTestArgType outputArgType){
    for(int i=0;i<100;++i){
      seqMonitor.add(i);
    }
    if(seqMonitor.nestedType.forwardIteration)
    {
      for(int i=0;i<100;++i){
        seqMonitor.pop(i,outputArgType);
        seqMonitor.verifyStructuralIntegrity();
      }
    }
    else
    {
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
            streamBuilder.accept(Arguments.of(new IntSnglLnkSeqMonitor(nestedType,checkedType),monitoredFunctionGen,seqSize
            ));
          }
        }
      }
    });
  }
  @org.junit.jupiter.api.Test
  public void testreadAndwriteObject(){
    getreadAndwriteObjectArgs().parallel().map(Arguments::get).forEach(args->{
        testreadAndwriteObjectHelper((IntSnglLnkSeqMonitor)args[0],(MonitoredFunctionGen)args[1],(int)args[2]
        );
    });
  }
  private static void testreadAndwriteObjectHelper
  (IntSnglLnkSeqMonitor seqMonitor,MonitoredFunctionGen monitoredFunctionGen,int numToAdd
  )
  {
    for(int i=0;i<numToAdd;++i){
      seqMonitor.add(i);
    }
    final File file;
    try
    {
      file=Files.createTempFile(null,null).toFile();
    } 
    catch(Exception e)
    {
      Assertions.fail(e);
      return;
    }
    if(monitoredFunctionGen.expectedException==null){
      try(var oos=new ObjectOutputStream(new FileOutputStream(file));)
      {
        oos.writeObject(seqMonitor.seq);
      }
      catch(Exception e)
      {
        Assertions.fail(e);
      }
      seqMonitor.verifyPreAlloc().verifyNaturalAscending(numToAdd).verifyPostAlloc();
      OmniCollection.OfInt readCol=null;
      try(var ois=new ObjectInputStream(new FileInputStream(file));)
      {
        readCol=(OmniCollection.OfInt)ois.readObject();
      }
      catch(Exception e)
      {
        Assertions.fail(e);
        return;
      }
      var itr=readCol.iterator();
      if(seqMonitor.nestedType.forwardIteration)
      {
        for(int i=0;i<numToAdd;++i)
        {
          Assertions.assertEquals(TypeConversionUtil.convertToint(i),itr.nextInt());
        }
      }
      else
      {
        for(int i=0;i<numToAdd;++i)
        {
          Assertions.assertEquals(TypeConversionUtil.convertToint(numToAdd-i-1),itr.nextInt());
        }
      }
      Assertions.assertFalse(itr.hasNext());
    }else{
      Assertions.assertThrows(monitoredFunctionGen.expectedException,()->{
        try(var moos=monitoredFunctionGen.getMonitoredObjectOutputStream(file,seqMonitor);)
        {
          seqMonitor.writeObject(moos);
        }
      });
    }
    seqMonitor.verifyStructuralIntegrity();
  }
  static Stream<Arguments> gettoArray_voidArgs(){
    return ArgBuilder.buildSeqArgs((streamBuilder,nestedType,checkedType)->{
      for(int seqSize:new int[]{0,1,100}){
        for(var outputArgType:IntOutputTestArgType.values()){
          streamBuilder.accept(Arguments.of(new IntSnglLnkSeqMonitor(nestedType,checkedType),seqSize,outputArgType));
        }
      }
    });
  }
  @org.junit.jupiter.api.Test
  public void testtoArray_void(){
    gettoArray_voidArgs().parallel().map(Arguments::get).forEach(args->{
        testtoArray_voidHelper((IntSnglLnkSeqMonitor)args[0],(int)args[1],(IntOutputTestArgType)args[2]);
    });
  }
  private static void testtoArray_voidHelper
  (IntSnglLnkSeqMonitor seqMonitor,int numToAdd,IntOutputTestArgType outputArgType){
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
        testclear_voidHelper((IntSnglLnkSeqMonitor)args[0],(int)args[1]);
    });
  }
  private static void testclear_voidHelper
  (IntSnglLnkSeqMonitor seqMonitor,int numToAdd){
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
      for(var outputType:IntOutputTestArgType.values()){
        builder.accept(Arguments.of(new IntSnglLnkSeqMonitor(NestedType.QUEUE,checkedType),outputType));
      }
    }
    return builder.build();
  }
  @org.junit.jupiter.api.Test
  public void testQueueelement_void(){
    getQueueelement_voidArgs().parallel().map(Arguments::get).forEach(args->{
        testQueueelement_voidHelper((IntSnglLnkSeqMonitor)args[0],(IntOutputTestArgType)args[1]);
    });
  }
  private static void testQueueelement_voidHelper
  (IntSnglLnkSeqMonitor seqMonitor,IntOutputTestArgType outputArgType){
    for(int i=0;i<100;++i)
    {
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
            streamBuilder.accept(Arguments.of(new IntSnglLnkSeqMonitor(nestedType,checkedType),monitoredFunctionGen,seqSize));
          }
        }
      }   
    });
  }
  @org.junit.jupiter.api.Test
  public void testtoArray_IntFunction(){
    gettoArray_IntFunctionArgs().parallel().map(Arguments::get).forEach(args->{
        testtoArray_IntFunctionHelper((IntSnglLnkSeqMonitor)args[0],(MonitoredFunctionGen)args[1],(int)args[2]);
    });
  }
  private static void testtoArray_IntFunctionHelper
  (IntSnglLnkSeqMonitor seqMonitor,MonitoredFunctionGen monitoredFunctionGen,int numToAdd){
    for(int i=0;i<numToAdd;++i){
      seqMonitor.add(i);
    }
    var arrConstructor=monitoredFunctionGen.getMonitoredArrayConstructor(seqMonitor);
    if(monitoredFunctionGen.expectedException==null){
      var resultArr=seqMonitor.seq.toArray(arrConstructor);
      Assertions.assertEquals(numToAdd,resultArr.length);
      var itr=seqMonitor.seq.iterator();
      for(int i=0;i<numToAdd;++i){
        Assertions.assertEquals(resultArr[i],(Object)itr.nextInt());
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
          streamBuilder.accept(Arguments.of(new IntSnglLnkSeqMonitor(nestedType,checkedType),seqSize,arrSize));
        }
      }
    });
  }
  @org.junit.jupiter.api.Test
  public void testtoArray_ObjectArray(){
    gettoArray_ObjectArrayArgs().parallel().map(Arguments::get).forEach(args->{
        testtoArray_ObjectArrayHelper((IntSnglLnkSeqMonitor)args[0],(int)args[1],(int)args[2]);
    });
  }
  private static void testtoArray_ObjectArrayHelper
  (IntSnglLnkSeqMonitor seqMonitor,int seqSize,int arrSize){
    for(int i=0;i<seqSize;++i){
      seqMonitor.add(i);
    }
    Integer[] paramArr=new Integer[arrSize];
    for(int i=seqSize,bound=seqSize+arrSize;i<bound;++i){
      paramArr[i-seqSize]=TypeConversionUtil.convertToInteger(i);
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
        Assertions.assertEquals(TypeConversionUtil.convertToInteger(i+seqSize),resultArr[i]);
      }
    }else{
      Assertions.assertSame(paramArr,resultArr);
    }
    var itr=seqMonitor.seq.iterator();
    for(int i=0;i<seqSize;++i){
      Assertions.assertEquals((Object)itr.nextInt(),resultArr[i]);
    }
    seqMonitor.verifyPreAlloc().verifyNaturalAscending(seqSize).verifyPostAlloc();
  }
  static Stream<Arguments> getPeekPollAndPopArgs(){
    return ArgBuilder.buildSeqArgs((streamBuilder,nestedType,checkedType)->{
      for(var outputType:IntOutputTestArgType.values()){
        streamBuilder.accept(Arguments.of(new IntSnglLnkSeqMonitor(nestedType,checkedType),outputType));
      }
    });
  }
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
                    //negative values beyond MIN_SAFE_INT that are beyond the precision of float
                    case IntegerMIN_SAFE_INT_MINUS1:
                    case LongMIN_SAFE_INT_MINUS1:
                    case DoubleMIN_SAFE_INT_MINUS1:
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
                    //positive values beyond MAX_SAFE_INT that are beyond the precision of float
                    case IntegerMAX_SAFE_INT_PLUS1:
                    case LongMAX_SAFE_INT_PLUS1:
                    case DoubleMAX_SAFE_INT_PLUS1:
                    //these input values cannot potentially return true
                    break;
                    default:
                    if(seqSize>0 && seqLocation.expectedException==null){
                      continue;
                    }
                    //these values must necessarily return false
                  }
                  builder.accept(Arguments.of(new IntSnglLnkSeqMonitor(nestedType,checkedType),argType,queryCastType,seqLocation,seqSize));
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
      streamBuilder.accept(Arguments.of(new IntSnglLnkSeqMonitor(nestedType,checkedType),seqSize));
    }
  });
}
}
