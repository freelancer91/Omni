package omni.impl.seq;
import java.io.Externalizable;
import java.io.IOException;
import java.util.EnumSet;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import omni.api.OmniCollection;
import omni.api.OmniDeque;
import omni.api.OmniIterator;
import omni.api.OmniList;
import omni.api.OmniListIterator;
import omni.impl.BooleanDblLnkNode;
import omni.impl.ByteDblLnkNode;
import omni.impl.CharDblLnkNode;
import omni.impl.CheckedType;
import omni.impl.DataType;
import omni.impl.DoubleDblLnkNode;
import omni.impl.FloatDblLnkNode;
import omni.impl.FunctionCallType;
import omni.impl.IllegalModification;
import omni.impl.IntDblLnkNode;
import omni.impl.IteratorType;
import omni.impl.LongDblLnkNode;
import omni.impl.MonitoredCollection;
import omni.impl.MonitoredDeque;
import omni.impl.MonitoredFunction;
import omni.impl.MonitoredFunctionGen;
import omni.impl.MonitoredList;
import omni.impl.MonitoredObjectGen;
import omni.impl.MonitoredObjectOutputStream;
import omni.impl.MonitoredRemoveIfPredicate;
import omni.impl.MonitoredSequence;
import omni.impl.MonitoredStack;
import omni.impl.QueryCastType;
import omni.impl.QueryVal;
import omni.impl.QueryVal.QueryValModification;
import omni.impl.RefDblLnkNode;
import omni.impl.ShortDblLnkNode;
import omni.impl.StructType;
import omni.util.OmniArray;
import omni.util.TestExecutorService;
public class DblLnkSeqTest{
    private static interface BasicTest{
        void runTest(MonitoredSequence<?> monitor);
        private void runAllTests(String testName){
            for(final var initParams:ALL_STRUCT_INIT_PARAMS){
                for(final var illegalMod:initParams.validPreMods){
                    for(final int size:SIZES){
                            TestExecutorService.submitTest(()->{
                                final var monitor=SequenceInitialization.Ascending
                                        .initialize(getMonitoredList(initParams,size),size,0);
                                if(illegalMod.expectedException == null){
                                    runTest(monitor);
                                }else{
                                    monitor.illegalMod(illegalMod);
                                    Assertions.assertThrows(illegalMod.expectedException,()->runTest(monitor));
                                }
                            });
                        
                    }
                }
            }
            TestExecutorService.completeAllTests(testName);
        }
    }
    private static interface MonitoredFunctionTest<MONITOR extends MonitoredSequence<?>>{
        void runTest(MONITOR monitor,MonitoredFunctionGen functionGen,FunctionCallType functionCallType,
                IllegalModification illegalMod,long randSeed);
        @SuppressWarnings("unchecked")
        private void runAllTests(String testName,long maxRand,EnumSet<FunctionCallType> functionCallTypes){
            for(final var initParams:ALL_STRUCT_INIT_PARAMS) {
                for(final var functionGen:initParams.structType.validMonitoredFunctionGens){
                    if(initParams.checkedType.checked || functionGen.expectedException == null){
                        for(final var size:SIZES){
                            final int initValBound=initParams.collectionType == DataType.BOOLEAN && size != 0?1:0;
                            for(final var functionCallType:initParams.collectionType.validFunctionCalls){
                                for(final var illegalMod:initParams.validPreMods){
                                    final long randSeedBound=size > 1 && functionGen.randomized
                                            && !functionCallType.boxed && illegalMod.expectedException == null?maxRand
                                                    :0;
                                    for(long tmpRandSeed=0;tmpRandSeed <= randSeedBound;++tmpRandSeed){
                                        final long randSeed=tmpRandSeed;
                                        for(int tmpInitVal=0;tmpInitVal <= initValBound;++tmpInitVal){
                                            final int initVal=tmpInitVal;
                                                TestExecutorService.submitTest(()->runTest(
                                                        (MONITOR)SequenceInitialization.Ascending.initialize(
                                                                getMonitoredList(initParams,size),size,initVal),
                                                        functionGen,functionCallType,illegalMod,randSeed));
                                            
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            TestExecutorService.completeAllTests(testName);
        }
    }
    private static interface QueryTest<MONITOR extends MonitoredSequence<?>>{
        void callAndVerifyResult(MONITOR monitor,QueryVal queryVal,DataType inputType,QueryCastType castType,
                QueryVal.QueryValModification modification,MonitoredObjectGen monitoredObjectGen,double position,
                int seqSize);
        default boolean cmeFilter(IllegalModification illegalMod,DataType inputType,DataType collectionType,
                QueryVal queryVal,QueryVal.QueryValModification modification,QueryCastType castType){
            if(illegalMod.expectedException != null){
                switch(collectionType){
                case BOOLEAN:
                    return queryVal == QueryVal.Null && castType != QueryCastType.ToObject;
                case BYTE:
                    switch(queryVal){
                    default:
                        if(castType != QueryCastType.ToObject){
                            switch(inputType){
                            case CHAR:
                            case SHORT:
                            case INT:
                                return true;
                            default:
                            }
                        }
                        break;
                    case Null:
                        return castType != QueryCastType.ToObject;
                    case MaxByte:
                    case MinByte:
                        switch(modification){
                        case Plus1:
                            if(castType != QueryCastType.ToObject){
                                switch(inputType){
                                case CHAR:
                                case SHORT:
                                case INT:
                                    return true;
                                default:
                                }
                            }
                        default:
                        }
                    case MaxBoolean:
                    case Neg0:
                    case Pos0:
                    }
                    return false;
                case CHAR:
                    switch(queryVal){
                    default:
                        if(castType != QueryCastType.ToObject){
                            switch(inputType){
                            case BYTE:
                            case SHORT:
                            case INT:
                                return true;
                            default:
                            }
                        }
                        break;
                    case Null:
                        return castType != QueryCastType.ToObject;
                    case MaxChar:
                    case Pos0:
                        switch(modification){
                        case Plus1:
                            if(castType != QueryCastType.ToObject){
                                switch(inputType){
                                case BYTE:
                                case SHORT:
                                case INT:
                                    return true;
                                default:
                                }
                            }
                        default:
                        }
                    case MaxBoolean:
                    case Neg0:
                    case MaxByte:
                    case TwoHundred:
                    case MaxShort:
                    }
                    return false;
                case SHORT:
                    switch(queryVal){
                    default:
                        if(castType != QueryCastType.ToObject){
                            switch(inputType){
                            case CHAR:
                            case INT:
                                return true;
                            default:
                            }
                        }
                        break;
                    case Null:
                        return castType != QueryCastType.ToObject;
                    case MaxShort:
                    case MinShort:
                        switch(modification){
                        case Plus1:
                            if(castType != QueryCastType.ToObject){
                                switch(inputType){
                                case CHAR:
                                case INT:
                                    return true;
                                default:
                                }
                            }
                        default:
                        }
                    case MaxBoolean:
                    case Neg0:
                    case Pos0:
                    case MaxByte:
                    case MinByte:
                    case TwoHundred:
                    }
                case REF:
                    return false;
                case INT:
                case LONG:
                case FLOAT:
                case DOUBLE:
                    return queryVal == QueryVal.Null && castType != QueryCastType.ToObject;
                default:
                    throw collectionType.invalid();
                }
            }
            return true;
        }
        private void runAllTests(String testName,int structSwitch){
            SequenceInitParams[] initParamArray;
           // switch(structSwitch){
           // case 0:
           //     initParamArray=LIST_STRUCT_INIT_PARAMS;
           //     break;
           // case 1:
           //     initParamArray=STACK_STRUCT_INIT_PARAMS;
           //     break;
            //default:
                initParamArray=ALL_STRUCT_INIT_PARAMS;
           // }
            for(final var initParams:initParamArray){
                for(final var queryVal:QueryVal.values()){
                    if(initParams.collectionType.isValidQueryVal(queryVal)){
                        queryVal.validQueryCombos.forEach((modification,castTypesToInputTypes)->{
                            castTypesToInputTypes.forEach((castType,inputTypes)->{
                                inputTypes.forEach(inputType->{
                                    if(queryVal == QueryVal.NonNull){
                                        for(final var monitoredObjectGen:initParams.structType.validMonitoredObjectGens){
                                            if(monitoredObjectGen.expectedException != null
                                                    && initParams.checkedType.checked){
                                                for(final var size:SIZES){
                                                    if(size > 0){
                                                        for(final var illegalMod:initParams.validPreMods){
                                                            final Class<? extends Throwable> expectedException=illegalMod.expectedException == null
                                                                    ?monitoredObjectGen.expectedException
                                                                    :illegalMod.expectedException;
                                                            TestExecutorService.submitTest(
                                                                    ()->Assertions.assertThrows(expectedException,
                                                                            ()->runTest(initParams,illegalMod,queryVal,
                                                                                    modification,castType,inputType,
                                                                                    monitoredObjectGen,size,-1)));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }else{
                                        final boolean queryCanReturnTrue=queryVal.queryCanReturnTrue(modification,
                                                castType,inputType,initParams.collectionType);
                                        for(final var size:SIZES){
                                            for(final var position:POSITIONS){
                                                if(position >= 0){
                                                    if(!queryCanReturnTrue){
                                                        continue;
                                                    }
                                                    switch(size){
                                                    case 3:
                                                        if(position == 0.5d){
                                                            break;
                                                        }
                                                    case 2:
                                                        if(position == 1.0d){
                                                            break;
                                                        }
                                                    case 1:
                                                        if(position == 0.0d){
                                                            break;
                                                        }
                                                    case 0:
                                                        continue;
                                                    case 4:
                                                        if(position != 0.5d){
                                                            break;
                                                        }
                                                    default:
                                                        continue;
                                                    }
                                                }
                                                for(final var illegalMod:initParams.validPreMods){
                                                    TestExecutorService.submitTest(()->{
                                                        if(cmeFilter(illegalMod,inputType,initParams.collectionType,
                                                                queryVal,modification,castType)){
                                                            runTest(initParams,illegalMod,queryVal,modification,
                                                                    castType,inputType,null,size,position);
                                                        }else{
                                                            Assertions.assertThrows(illegalMod.expectedException,
                                                                    ()->runTest(initParams,illegalMod,queryVal,
                                                                            modification,castType,inputType,null,size,
                                                                            position));
                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    }
                                });
                            });
                        });
                    }
                }
            }
            TestExecutorService.completeAllTests(testName);
        }
        @SuppressWarnings("unchecked")
        private void runTest(SequenceInitParams initParams,IllegalModification illegalMod,QueryVal queryVal,
                QueryVal.QueryValModification modification,QueryCastType castType,DataType inputType,
                MonitoredObjectGen monitoredObjectGen,int seqSize,double position){
            final var monitor=getMonitoredList(initParams,seqSize);
            if(position < 0){
                switch(initParams.collectionType){
                case BOOLEAN:
                    queryVal.initDoesNotContain((OmniCollection.OfBoolean)monitor.getCollection(),seqSize,0,
                            modification);
                    break;
                case BYTE:
                    queryVal.initDoesNotContain((OmniCollection.OfByte)monitor.getCollection(),seqSize,0,modification);
                    break;
                case CHAR:
                    queryVal.initDoesNotContain((OmniCollection.OfChar)monitor.getCollection(),seqSize,0,modification);
                    break;
                case DOUBLE:
                    queryVal.initDoesNotContain((OmniCollection.OfDouble)monitor.getCollection(),seqSize,0,
                            modification);
                    break;
                case FLOAT:
                    queryVal.initDoesNotContain((OmniCollection.OfFloat)monitor.getCollection(),seqSize,0,modification);
                    break;
                case INT:
                    queryVal.initDoesNotContain((OmniCollection.OfInt)monitor.getCollection(),seqSize,0,modification);
                    break;
                case LONG:
                    queryVal.initDoesNotContain((OmniCollection.OfLong)monitor.getCollection(),seqSize,0,modification);
                    break;
                case REF:
                    queryVal.initDoesNotContain((OmniCollection.OfRef<Object>)monitor.getCollection(),seqSize,0,
                            modification);
                    break;
                case SHORT:
                    queryVal.initDoesNotContain((OmniCollection.OfShort)monitor.getCollection(),seqSize,0,modification);
                    break;
                default:
                    throw initParams.collectionType.invalid();
                }
            }else{
                switch(initParams.collectionType){
                case BOOLEAN:
                    queryVal.initContains((OmniCollection.OfBoolean)monitor.getCollection(),seqSize,0,position,
                            modification);
                    break;
                case BYTE:
                    queryVal.initContains((OmniCollection.OfByte)monitor.getCollection(),seqSize,0,position,
                            modification);
                    break;
                case CHAR:
                    queryVal.initContains((OmniCollection.OfChar)monitor.getCollection(),seqSize,0,position,
                            modification);
                    break;
                case DOUBLE:
                    queryVal.initContains((OmniCollection.OfDouble)monitor.getCollection(),seqSize,0,position,
                            modification);
                    break;
                case FLOAT:
                    queryVal.initContains((OmniCollection.OfFloat)monitor.getCollection(),seqSize,0,position,
                            modification);
                    break;
                case INT:
                    queryVal.initContains((OmniCollection.OfInt)monitor.getCollection(),seqSize,0,position,
                            modification);
                    break;
                case LONG:
                    queryVal.initContains((OmniCollection.OfLong)monitor.getCollection(),seqSize,0,position,
                            modification);
                    break;
                case REF:
                    queryVal.initContains((OmniCollection.OfRef<Object>)monitor.getCollection(),seqSize,0,position,
                            modification,inputType);
                    break;
                case SHORT:
                    queryVal.initContains((OmniCollection.OfShort)monitor.getCollection(),seqSize,0,position,
                            modification);
                    break;
                default:
                    throw initParams.collectionType.invalid();
                }
            }
            monitor.updateCollectionState();
            monitor.illegalMod(illegalMod);
            callAndVerifyResult((MONITOR)monitor,queryVal,inputType,castType,modification,monitoredObjectGen,position,
                    seqSize);
        }
    }
    private static interface ToStringAndHashCodeTest{
        void callRaw(OmniCollection<?> seq);
        void callVerify(MonitoredSequence<?> monitor);
        private void runAllTests(String testName){
            for(final var initParams:ALL_STRUCT_INIT_PARAMS){
                if(initParams.collectionType == DataType.REF){
                    for(final var objGen:initParams.structType.validMonitoredObjectGens){
                        if(objGen.expectedException == null || initParams.checkedType.checked){
                            for(final var size:SIZES){
                                for(final var illegalMod:initParams.validPreMods){
                                    TestExecutorService.submitTest(()->{
                                        if(size == 0 || objGen.expectedException == null){
                                            final var monitor=SequenceInitialization.Ascending
                                                    .initialize(getMonitoredList(initParams,size),size,0);
                                            if(illegalMod.expectedException == null){
                                                callVerify(monitor);
                                            }else{
                                                monitor.illegalMod(illegalMod);
                                                Assertions.assertThrows(illegalMod.expectedException,
                                                        ()->callVerify(monitor));
                                            }
                                        }else{
                                            final var throwSwitch=new MonitoredObjectGen.ThrowSwitch();
                                            final var monitor=SequenceInitialization.Ascending
                                                    .initializeWithMonitoredObj(getMonitoredList(initParams,size),
                                                            size,0,objGen,throwSwitch);
                                            monitor.illegalMod(illegalMod);
                                            final Class<? extends Throwable> expectedException=illegalMod.expectedException == null
                                                    ?objGen.expectedException
                                                    :illegalMod.expectedException;
                                            Assertions.assertThrows(expectedException,()->{
                                                try{
                                                    callRaw(monitor.getCollection());
                                                }finally{
                                                    throwSwitch.doThrow=false;
                                                    monitor.verifyCollectionState();
                                                }
                                            });
                                        }
                                    });
                                }
                            }
                        }
                    }
                }else{
                    final int initValBound=initParams.collectionType == DataType.BOOLEAN?1:0;
                    for(int tmpInitVal=0;tmpInitVal <= initValBound;++tmpInitVal){
                        final int initVal=tmpInitVal;
                        for(final var size:SIZES){
                            for(final var illegalMod:initParams.validPreMods){
                                TestExecutorService.submitTest(()->{
                                    final var monitor=SequenceInitialization.Ascending
                                            .initialize(getMonitoredList(initParams,size),size,initVal);
                                    if(illegalMod.expectedException == null){
                                        callVerify(monitor);
                                    }else{
                                        monitor.illegalMod(illegalMod);
                                        Assertions.assertThrows(illegalMod.expectedException,()->callVerify(monitor));
                                    }
                                });
                            }
                        }
                    }
                }
            }
            TestExecutorService.completeAllTests(testName);
        }
    }
  
  private static class DblLnkSeqMonitor<LSTDEQ extends AbstractSeq<E>&OmniDeque<E>&OmniList<E>&Externalizable,E>
      extends AbstractSequenceMonitor<LSTDEQ> implements MonitoredDeque<LSTDEQ>,MonitoredList<LSTDEQ>{
    private static class SubListMonitor<SUBLIST extends AbstractSeq<E>&OmniList<E>,LSTDEQ extends AbstractSeq<E>&OmniDeque<E>&OmniList<E>&Externalizable,E> implements MonitoredList<SUBLIST>{
      final DblLnkSeqMonitor<LSTDEQ,E> expectedRoot;
      final SubListMonitor<SUBLIST,LSTDEQ,E> expectedParent;
      final SUBLIST seq;
      final int expectedParentOffset;
      final int expectedRootOffset;
      Object expectedHead;
      Object expectedTail;
      
      int expectedSize;
      int expectedModCount;
      
      
      //TODO add the other fields
      
      
      @SuppressWarnings("unchecked")
    SubListMonitor(DblLnkSeqMonitor<LSTDEQ,E> expectedRoot,int fromIndex,int toIndex){
          this.expectedRoot=expectedRoot;
          this.expectedParent=null;
          this.seq=(SUBLIST)expectedRoot.seq.subList(fromIndex,toIndex);
          this.expectedSize=toIndex-fromIndex;
          this.expectedModCount=expectedRoot.expectedModCount;
          this.expectedRootOffset=fromIndex;
          this.expectedParentOffset=fromIndex;
          
          if(expectedSize!=0) {
             expectedTail=expectedRoot.getNodeIterateDown(expectedRoot.seq,expectedRoot.expectedSize-toIndex);
             expectedHead=expectedRoot.getNodeIterateUp(fromIndex,expectedRoot.seq);
          }
      }
      @SuppressWarnings("unchecked")
    SubListMonitor(SubListMonitor<SUBLIST,LSTDEQ,E> expectedParent,int fromIndex,int toIndex){
          this.expectedRoot=expectedParent.expectedRoot;
          this.expectedParent=expectedParent;
          this.seq=(SUBLIST)expectedParent.seq.subList(fromIndex,toIndex);
          this.expectedSize=toIndex-fromIndex;
          this.expectedModCount=expectedParent.expectedModCount;
          this.expectedRootOffset=expectedParent.expectedRootOffset+fromIndex;
          this.expectedParentOffset=fromIndex;
          if(expectedSize!=0) {
              expectedTail=expectedRoot.getNodeIterateDown(expectedParent.seq,expectedParent.expectedSize-toIndex);
              expectedHead=expectedRoot.getNodeIterateUp(fromIndex,expectedParent.seq);
           }
      }
      
      @Override public void updateRemoveIndexState(int index){
        //TODO
        throw new UnsupportedOperationException();
      }

      @Override public MonitoredIterator<? extends OmniIterator<?>,SUBLIST> getMonitoredIterator(int index,
          IteratorType itrType){
        //TODO
        throw new UnsupportedOperationException();
      }

      @Override public void verifyGetResult(int index,Object result,DataType outputType){
        //TODO
        throw new UnsupportedOperationException();
      }

      @Override public void updateRemoveValState(Object inputVal,DataType inputType){
        //TODO
        throw new UnsupportedOperationException();
      }

      @Override public MonitoredIterator<? extends OmniIterator<?>,SUBLIST> getMonitoredIterator(IteratorType itrType){
        //TODO
        throw new UnsupportedOperationException();
      }

      @Override public CheckedType getCheckedType(){
        return expectedRoot.checkedType;
      }

      @Override public SUBLIST getCollection(){
        return seq;
      }

      @Override public DataType getDataType(){
        return expectedRoot.dataType;
      }

      @Override public MonitoredIterator<? extends OmniIterator<?>,SUBLIST> getMonitoredIterator(){
        // TODO Auto-generated method stub
        return null;
      }

      @Override public StructType getStructType(){
        return StructType.DblLnkSubList;
      }

      @Override public void modCollection(){
        Consumer<SubListMonitor<SUBLIST,LSTDEQ,E>> modifier;
        var dataType=expectedRoot.dataType;
        switch(dataType) {
        case BOOLEAN:
          modifier=subList->subList.expectedModCount=FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.incrementModCount(subList.seq);
          break;
        case BYTE:
          modifier=subList->subList.expectedModCount=FieldAndMethodAccessor.ByteDblLnkSeq.CheckedSubList.incrementModCount(subList.seq);
          break;
        case CHAR:
          modifier=subList->subList.expectedModCount=FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.incrementModCount(subList.seq);
          break;
        case DOUBLE:
          modifier=subList->subList.expectedModCount=FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedSubList.incrementModCount(subList.seq);
          break;
        case FLOAT:
          modifier=subList->subList.expectedModCount=FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.incrementModCount(subList.seq);
          break;
        case INT:
          modifier=subList->subList.expectedModCount=FieldAndMethodAccessor.IntDblLnkSeq.CheckedSubList.incrementModCount(subList.seq);
          break;
        case LONG:
          modifier=subList->subList.expectedModCount=FieldAndMethodAccessor.LongDblLnkSeq.CheckedSubList.incrementModCount(subList.seq);
          break;
        case REF:
          modifier=subList->subList.expectedModCount=FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.incrementModCount(subList.seq);
          break;
        case SHORT:
          modifier=subList->subList.expectedModCount=FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList.incrementModCount(subList.seq);
          break;
        default:
          throw dataType.invalid();
        }
        var curr=this;
        do {
          modifier.accept(curr);
        }while((curr=curr.expectedParent)!=null);
        expectedRoot.modCollection();
      }

      @Override public void modParent(){
        expectedParent.modCollection();
      }

      @Override public void modRoot(){
        expectedRoot.modCollection();
      }

      @Override public int size(){
        return expectedSize;
      }

      @Override public void updateClearState(){
        // TODO Auto-generated method stub
        
      }

      @SuppressWarnings("unchecked")
    @Override public void updateCollectionState(){
        Consumer<SubListMonitor<SUBLIST,LSTDEQ,E>> updater=subListMonitor->subListMonitor.expectedSize=((AbstractSeq<E>)subListMonitor.seq).size;
        if(expectedRoot.checkedType.checked) {
            var dataType=expectedRoot.dataType;
            switch(dataType) {
            case BOOLEAN:
                updater=updater.andThen(subListMonitor->{
                    subListMonitor.expectedModCount=FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.modCount(subListMonitor.seq);
                    subListMonitor.expectedHead=((BooleanDblLnkSeq)subListMonitor.seq).head;
                    subListMonitor.expectedTail=((BooleanDblLnkSeq)subListMonitor.seq).tail;
                });
                break;
            case BYTE:
                updater=updater.andThen(subListMonitor->{
                    subListMonitor.expectedModCount=FieldAndMethodAccessor.ByteDblLnkSeq.CheckedSubList.modCount(subListMonitor.seq);
                    subListMonitor.expectedHead=((ByteDblLnkSeq)subListMonitor.seq).head;
                    subListMonitor.expectedTail=((ByteDblLnkSeq)subListMonitor.seq).tail;
                });
                break;
            case CHAR:
                updater=updater.andThen(subListMonitor->{
                    subListMonitor.expectedModCount=FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.modCount(subListMonitor.seq);
                    subListMonitor.expectedHead=((CharDblLnkSeq)subListMonitor.seq).head;
                    subListMonitor.expectedTail=((CharDblLnkSeq)subListMonitor.seq).tail;
                });
                break;
            case DOUBLE:
                updater=updater.andThen(subListMonitor->{
                    subListMonitor.expectedModCount=FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedSubList.modCount(subListMonitor.seq);
                    subListMonitor.expectedHead=((DoubleDblLnkSeq)subListMonitor.seq).head;
                    subListMonitor.expectedTail=((DoubleDblLnkSeq)subListMonitor.seq).tail;
                });
                break;
            case FLOAT:
                updater=updater.andThen(subListMonitor->{
                    subListMonitor.expectedModCount=FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.modCount(subListMonitor.seq);
                    subListMonitor.expectedHead=((FloatDblLnkSeq)subListMonitor.seq).head;
                    subListMonitor.expectedTail=((FloatDblLnkSeq)subListMonitor.seq).tail;
                });
                break;
            case INT:
                updater=updater.andThen(subListMonitor->{
                    subListMonitor.expectedModCount=FieldAndMethodAccessor.IntDblLnkSeq.CheckedSubList.modCount(subListMonitor.seq);
                    subListMonitor.expectedHead=((IntDblLnkSeq)subListMonitor.seq).head;
                    subListMonitor.expectedTail=((IntDblLnkSeq)subListMonitor.seq).tail;
                });
                break;
            case LONG:
                updater=updater.andThen(subListMonitor->{
                    subListMonitor.expectedModCount=FieldAndMethodAccessor.LongDblLnkSeq.CheckedSubList.modCount(subListMonitor.seq);
                    subListMonitor.expectedHead=((LongDblLnkSeq)subListMonitor.seq).head;
                    subListMonitor.expectedTail=((LongDblLnkSeq)subListMonitor.seq).tail;
                });
                break;
            case REF:
                updater=updater.andThen(subListMonitor->{
                    subListMonitor.expectedModCount=FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.modCount(subListMonitor.seq);
                    subListMonitor.expectedHead=((RefDblLnkSeq<E>)subListMonitor.seq).head;
                    subListMonitor.expectedTail=((RefDblLnkSeq<E>)subListMonitor.seq).tail;
                });
                break;
            case SHORT:
                updater=updater.andThen(subListMonitor->{
                    subListMonitor.expectedModCount=FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList.modCount(subListMonitor.seq);
                    subListMonitor.expectedHead=((ShortDblLnkSeq)subListMonitor.seq).head;
                    subListMonitor.expectedTail=((ShortDblLnkSeq)subListMonitor.seq).tail;
                });
                break;
            default:
                throw dataType.invalid();
            }
        }
        var curr=this;
        do {
            updater.accept(curr);
        }while((curr=curr.expectedParent)!=null);
        expectedRoot.updateCollectionState();
      }

      @Override public void verifyClone(Object clone){
        // TODO Auto-generated method stub
        
      }

      @Override public void verifyRemoveIf(boolean result,MonitoredRemoveIfPredicate filter){
        // TODO Auto-generated method stub
        
      }

      @Override public void verifyArrayIsCopy(Object arr,boolean emptyArrayMayBeSame){
        // TODO Auto-generated method stub
        
      }

      @Override public void writeObjectImpl(MonitoredObjectOutputStream oos) throws IOException{
        // TODO Auto-generated method stub
        
      }

      @Override public void updateAddState(int index,Object inputVal,DataType inputType){
        // TODO Auto-generated method stub
        
      }

      @Override public void verifyPutResult(int index,Object input,DataType inputType){
        // TODO Auto-generated method stub
        
      }

      @Override public MonitoredListIterator<? extends OmniListIterator<?>,SUBLIST> getMonitoredListIterator(){
        // TODO Auto-generated method stub
        return null;
      }

      @Override public MonitoredListIterator<? extends OmniListIterator<?>,SUBLIST> getMonitoredListIterator(int index){
        // TODO Auto-generated method stub
        return null;
      }

      @Override public void updateReplaceAllState(MonitoredFunction function){
        // TODO Auto-generated method stub
        
      }

      @Override public void verifyCollectionState(boolean refIsSame){
        var dataType=expectedRoot.dataType;
        int rootOffset=this.expectedRootOffset;
        int rootBound=rootOffset+this.expectedSize;
        boolean checked=expectedRoot.checkedType.checked;
        switch(dataType) {
        case BOOLEAN:{
            var root=(BooleanDblLnkSeq)expectedRoot.seq;
            var curr=(BooleanDblLnkSeq)seq;
            var expectedArr=(boolean[])expectedRoot.expectedArr;
            //TODO
            break;
        }
        case BYTE:{
            var root=(ByteDblLnkSeq)expectedRoot.seq;
            var curr=(ByteDblLnkSeq)seq;
            var expectedArr=(byte[])expectedRoot.expectedArr;
            //TODO
            break;
        }
        case CHAR:{
            var root=(CharDblLnkSeq)expectedRoot.seq;
            var curr=(CharDblLnkSeq)seq;
            var expectedArr=(char[])expectedRoot.expectedArr;
            //TODO
            break;
        }
        case DOUBLE:{
            var root=(DoubleDblLnkSeq)expectedRoot.seq;
            var curr=(DoubleDblLnkSeq)seq;
            var expectedArr=(double[])expectedRoot.expectedArr;
            //TODO
            break;
        }
        case FLOAT:{
            var root=(FloatDblLnkSeq)expectedRoot.seq;
            var curr=(FloatDblLnkSeq)seq;
            var expectedArr=(float[])expectedRoot.expectedArr;
            //TODO
            break;
        }
        case INT:{
            var root=(IntDblLnkSeq)expectedRoot.seq;
            var curr=(IntDblLnkSeq)seq;
            var expectedArr=(int[])expectedRoot.expectedArr;
            //TODO
            break;
        }
        case LONG:{
            var root=(LongDblLnkSeq)expectedRoot.seq;
            var curr=(LongDblLnkSeq)seq;
            var expectedArr=(long[])expectedRoot.expectedArr;
            //TODO
            break;
        }
        case REF:{
            var root=(RefDblLnkSeq<E>)expectedRoot.seq;
            var curr=(RefDblLnkSeq<E>)seq;
            var expectedArr=(Object[])expectedRoot.expectedArr;
            //TODO
            break;
        }
        case SHORT:{
            var root=(ShortDblLnkSeq)expectedRoot.seq;
            var curr=(ShortDblLnkSeq)seq;
            var expectedArr=(short[])expectedRoot.expectedArr;
            //TODO
            break;
        }
        default:
            throw dataType.invalid();
        }
      }

      @Override public void copyListContents(){
        // TODO Auto-generated method stub
        
      }

      @Override public void incrementModCount(){
        // TODO Auto-generated method stub
        
      }

      @Override public MonitoredList<?> getMonitoredSubList(int fromIndex,int toIndex){
        return new SubListMonitor<>(this,fromIndex,toIndex);
      }
      //TODO
    }
   
    @SuppressWarnings("unchecked") private Object next(Object node) {
      switch(dataType) {
      case BOOLEAN:
        return ((BooleanDblLnkNode)node).next;
      case BYTE:
        return ((ByteDblLnkNode)node).next;
      case CHAR:
        return ((CharDblLnkNode)node).next;
      case DOUBLE:
        return ((DoubleDblLnkNode)node).next;
      case FLOAT:
        return ((FloatDblLnkNode)node).next;
      case INT:
        return ((IntDblLnkNode)node).next;
      case LONG:
        return ((LongDblLnkNode)node).next;
      case REF:
        return ((RefDblLnkNode<E>)node).next;
      case SHORT:
        return ((ShortDblLnkNode)node).next;
      default:
        throw dataType.invalid();
      }
    }
    @SuppressWarnings("unchecked") private Object prev(Object node) {
      switch(dataType) {
      case BOOLEAN:
        return ((BooleanDblLnkNode)node).prev;
      case BYTE:
        return ((ByteDblLnkNode)node).prev;
      case CHAR:
        return ((CharDblLnkNode)node).prev;
      case DOUBLE:
        return ((DoubleDblLnkNode)node).prev;
      case FLOAT:
        return ((FloatDblLnkNode)node).prev;
      case INT:
        return ((IntDblLnkNode)node).prev;
      case LONG:
        return ((LongDblLnkNode)node).prev;
      case REF:
        return ((RefDblLnkNode<E>)node).prev;
      case SHORT:
        return ((ShortDblLnkNode)node).prev;
      default:
        throw dataType.invalid();
      }
    }

    private Object head(AbstractSeq<E> seq) {
      switch(dataType) {
      case BOOLEAN:
        return ((BooleanDblLnkSeq)seq).head;
      case BYTE:
        return ((ByteDblLnkSeq)seq).head;
      case CHAR:
        return ((CharDblLnkSeq)seq).head;
      case DOUBLE:
        return ((DoubleDblLnkSeq)seq).head;
      case FLOAT:
        return ((FloatDblLnkSeq)seq).head;
      case INT:
        return ((IntDblLnkSeq)seq).head;
      case LONG:
        return ((LongDblLnkSeq)seq).head;
      case REF:
        return ((RefDblLnkSeq<E>)seq).head;
      case SHORT:
        return ((ShortDblLnkSeq)seq).head;
      default:
        throw dataType.invalid();
      }
    }
    private Object tail(AbstractSeq<E> seq) {
      switch(dataType) {
      case BOOLEAN:
        return ((BooleanDblLnkSeq)seq).tail;
      case BYTE:
        return ((ByteDblLnkSeq)seq).tail;
      case CHAR:
        return ((CharDblLnkSeq)seq).tail;
      case DOUBLE:
        return ((DoubleDblLnkSeq)seq).tail;
      case FLOAT:
        return ((FloatDblLnkSeq)seq).tail;
      case INT:
        return ((IntDblLnkSeq)seq).tail;
      case LONG:
        return ((LongDblLnkSeq)seq).tail;
      case REF:
        return ((RefDblLnkSeq<E>)seq).tail;
      case SHORT:
        return ((ShortDblLnkSeq)seq).tail;
      default:
        throw dataType.invalid();
      }
    }
    private Object getNode(int index,AbstractSeq<E> seq,int expectedSize) {
      if((expectedSize-=index)<=index) {
        return getNodeIterateDown(seq,expectedSize);
      }else {
        return getNodeIterateUp(index,seq);
      }
    }
    private Object getItrNode(int index,AbstractSeq<E> seq,int expectedSize) {
        if((expectedSize-=index)<=index){
            //the node is closer to the tail
            switch(expectedSize){
            case 0:
              return null;
            case 1:
              return tail(seq);
            default:
              return getNodeIterateDown(seq,expectedSize);
            }
          }else{
            //the node is closer to the head
            return getNodeIterateUp(index,seq);
          }
        }
    private Object getNodeIterateUp(int index,AbstractSeq<E> seq){
      switch(dataType) {
      case BOOLEAN:{
        var curr=((BooleanDblLnkSeq)seq).head;
        while(index>0) {
          curr=curr.next;
          --index;
        }
        return curr;
      }
      case BYTE:{
        var curr=((ByteDblLnkSeq)seq).head;
        while(index>0) {
          curr=curr.next;
          --index;
        }
        return curr;
      }
      case CHAR:{
        var curr=((CharDblLnkSeq)seq).head;
        while(index>0) {
          curr=curr.next;
          --index;
        }
        return curr;
      }
      case DOUBLE:{
        var curr=((DoubleDblLnkSeq)seq).head;
        while(index>0) {
          curr=curr.next;
          --index;
        }
        return curr;
      }
      case FLOAT:{
        var curr=((FloatDblLnkSeq)seq).head;
        while(index>0) {
          curr=curr.next;
          --index;
        }
        return curr;
      }
      case INT:{
        var curr=((IntDblLnkSeq)seq).head;
        while(index>0) {
          curr=curr.next;
          --index;
        }
        return curr;
      }
      case LONG:{
        var curr=((LongDblLnkSeq)seq).head;
        while(index>0) {
          curr=curr.next;
          --index;
        }
        return curr;
      }
      case REF:{
        var curr=((RefDblLnkSeq<E>)seq).head;
        while(index>0) {
          curr=curr.next;
          --index;
        }
        return curr;
      }
      case SHORT:{
        var curr=((ShortDblLnkSeq)seq).head;
        while(index>0) {
          curr=curr.next;
          --index;
        }
        return curr;
      }
      default:
        throw dataType.invalid();
      }
    }
    private Object getNodeIterateDown(AbstractSeq<E> seq,int tailDist){
      switch(dataType) {
      case BOOLEAN:{
        var curr=((BooleanDblLnkSeq)seq).tail;
        while(--tailDist>0) {
          curr=curr.prev;
        }
        return curr;
      }
      case BYTE:{
        var curr=((ByteDblLnkSeq)seq).tail;
        while(--tailDist>0) {
          curr=curr.prev;
        }
        return curr;
      }
      case CHAR:{
        var curr=((CharDblLnkSeq)seq).tail;
        while(--tailDist>0) {
          curr=curr.prev;
        }
        return curr;
      }
      case DOUBLE:{
        var curr=((DoubleDblLnkSeq)seq).tail;
        while(--tailDist>0) {
          curr=curr.prev;
        }
        return curr;
      }
      case FLOAT:{
        var curr=((FloatDblLnkSeq)seq).tail;
        while(--tailDist>0) {
          curr=curr.prev;
        }
        return curr;
      }
      case INT:{
        var curr=((IntDblLnkSeq)seq).tail;
        while(--tailDist>0) {
          curr=curr.prev;
        }
        return curr;
      }
      case LONG:{
        var curr=((LongDblLnkSeq)seq).tail;
        while(--tailDist>0) {
          curr=curr.prev;
        }
        return curr;
      }
      case REF:{
        var curr=((RefDblLnkSeq<E>)seq).tail;
        while(--tailDist>0) {
          curr=curr.prev;
        }
        return curr;
      }
      case SHORT:{
        var curr=((ShortDblLnkSeq)seq).tail;
        while(--tailDist>0) {
          curr=curr.prev;
        }
        return curr;
      }
      default:
        throw dataType.invalid();
      }
    }
  /*
    @SuppressWarnings("unchecked") private Object val(Object node) {
  switch(dataType) {
  case BOOLEAN:
    return ((BooleanDblLnkNode)node).val;
  case BYTE:
    return ((ByteDblLnkNode)node).val;
  case CHAR:
    return ((CharDblLnkNode)node).val;
  case DOUBLE:
    return ((DoubleDblLnkNode)node).val;
  case FLOAT:
    return ((FloatDblLnkNode)node).val;
  case INT:
    return ((IntDblLnkNode)node).val;
  case LONG:
    return ((LongDblLnkNode)node).val;
  case REF:
    return ((RefDblLnkNode<E>)node).val;
  case SHORT:
    return ((ShortDblLnkNode)node).val;
  default:
    throw dataType.invalid();
  }
}
    private Object iterateUp(Object node,int length) {
      switch(dataType) {
      case BOOLEAN:{
        var cast=(BooleanDblLnkNode)node;
        while(length>0) {
          --length;
          cast=cast.next;
        }
        return cast;
      }  
      case BYTE:{
        var cast=(ByteDblLnkNode)node;
        while(length>0) {
          --length;
          cast=cast.next;
        }
        return cast;
      }  
      case CHAR:{
        var cast=(CharDblLnkNode)node;
        while(length>0) {
          --length;
          cast=cast.next;
        }
        return cast;
      }  
      case DOUBLE:{
        var cast=(DoubleDblLnkNode)node;
        while(length>0) {
          --length;
          cast=cast.next;
        }
        return cast;
      }  
      case FLOAT:{
        var cast=(FloatDblLnkNode)node;
        while(length>0) {
          --length;
          cast=cast.next;
        }
        return cast;
      }  
      case INT:{
        var cast=(IntDblLnkNode)node;
        while(length>0) {
          --length;
          cast=cast.next;
        }
        return cast;
      }  
      case LONG:{
        var cast=(LongDblLnkNode)node;
        while(length>0) {
          --length;
          cast=cast.next;
        }
        return cast;
      }  
      case REF:{
        @SuppressWarnings("unchecked") var cast=(RefDblLnkNode<E>)node;
        while(length>0) {
          --length;
          cast=cast.next;
        }
        return cast;
      }  
      case SHORT:{
        var cast=(ShortDblLnkNode)node;
        while(length>0) {
          --length;
          cast=cast.next;
        }
        return cast;
      }  
      default:
        throw dataType.invalid();
      }
    }
    private Object iterateDown(Object node,int length) {
      switch(dataType) {
      case BOOLEAN:{
        var cast=(BooleanDblLnkNode)node;
        while(length>0) {
          --length;
          cast=cast.prev;
        }
        return cast;
      }  
      case BYTE:{
        var cast=(ByteDblLnkNode)node;
        while(length>0) {
          --length;
          cast=cast.prev;
        }
        return cast;
      }  
      case CHAR:{
        var cast=(CharDblLnkNode)node;
        while(length>0) {
          --length;
          cast=cast.prev;
        }
        return cast;
      }  
      case DOUBLE:{
        var cast=(DoubleDblLnkNode)node;
        while(length>0) {
          --length;
          cast=cast.prev;
        }
        return cast;
      }  
      case FLOAT:{
        var cast=(FloatDblLnkNode)node;
        while(length>0) {
          --length;
          cast=cast.prev;
        }
        return cast;
      }  
      case INT:{
        var cast=(IntDblLnkNode)node;
        while(length>0) {
          --length;
          cast=cast.prev;
        }
        return cast;
      }  
      case LONG:{
        var cast=(LongDblLnkNode)node;
        while(length>0) {
          --length;
          cast=cast.prev;
        }
        return cast;
      }  
      case REF:{
        @SuppressWarnings("unchecked") var cast=(RefDblLnkNode<E>)node;
        while(length>0) {
          --length;
          cast=cast.prev;
        }
        return cast;
      }  
      case SHORT:{
        var cast=(ShortDblLnkNode)node;
        while(length>0) {
          --length;
          cast=cast.prev;
        }
        return cast;
      }  
      default:
        throw dataType.invalid();
      }
    }
    */
    private abstract class AbstractItrMonitor<ITR extends OmniIterator<E>> implements MonitoredIterator<ITR,LSTDEQ>{
      final ITR itr;
      Object expectedCurr;
      Object expectedLastRet;
      int expectedCurrIndex;
      int expectedItrModCount;
      int lastRetIndex;
      AbstractItrMonitor(ITR itr,int expectedCurrIndex,Object expectedCurr){
        this.itr=itr;
        this.expectedCurrIndex=expectedCurrIndex;
        this.expectedCurr=expectedCurr;
        this.expectedItrModCount=expectedModCount;
        this.lastRetIndex=-1;
      }
      @Override public boolean nextWasJustCalled(){
        return lastRetIndex!=-1 && lastRetIndex<expectedCurrIndex;
      }
      @Override public void updateItrNextState(){
        expectedLastRet=expectedCurr;
        expectedCurr=next(expectedCurr);
        lastRetIndex=expectedCurrIndex++;
      }
      @Override public void verifyNextResult(DataType outputType,Object result){
        verifyGetResult(lastRetIndex,result,outputType);
      }
      @Override public void updateItrRemoveState(){
        int expectedCurrIndex=this.expectedCurrIndex;
        int lastRetIndex;
        updateRemoveIndexState(lastRetIndex=this.lastRetIndex);
        ++expectedItrModCount;
        if(expectedCurrIndex==lastRetIndex) {
          this.expectedCurr=next(expectedLastRet);
        }else {
          this.expectedCurrIndex=expectedCurrIndex-1;
        }
        this.lastRetIndex=-1;
        this.expectedLastRet=null;
      }
      @Override public ITR getIterator(){
        return itr;
      }
      @Override public MonitoredCollection<LSTDEQ> getMonitoredCollection(){
        return DblLnkSeqMonitor.this;
      }
      @Override public boolean hasNext(){
        return this.expectedCurrIndex<expectedSize;
      }
      @Override public int getNumLeft(){
        return expectedSize-expectedCurrIndex;
      }
      @Override public void verifyForEachRemaining(MonitoredFunction function){
        int lastRetIndex=this.lastRetIndex;
        int expectedCurrIndex=this.expectedCurrIndex;
        int expectedBound=expectedSize;
        final var functionItr=function.iterator();
        switch(dataType) {
        case BOOLEAN:{
          final var expectedArr=(boolean[])DblLnkSeqMonitor.this.expectedArr;
          var expectedCurr=(BooleanDblLnkNode)this.expectedCurr;
          var expectedLastRet=(BooleanDblLnkNode)this.expectedLastRet;
          while(expectedCurrIndex<expectedBound) {
            Assertions.assertEquals(expectedArr[expectedCurrIndex],(boolean)functionItr.next());
            lastRetIndex=expectedCurrIndex++;
            expectedLastRet=expectedCurr;
            expectedCurr=expectedCurr.next;
          }
          this.expectedCurr=expectedCurr;
          this.expectedLastRet=expectedLastRet;
          break;
        }
        case BYTE:{
          final var expectedArr=(byte[])DblLnkSeqMonitor.this.expectedArr;
          var expectedCurr=(ByteDblLnkNode)this.expectedCurr;
          var expectedLastRet=(ByteDblLnkNode)this.expectedLastRet;
          while(expectedCurrIndex<expectedBound) {
            Assertions.assertEquals(expectedArr[expectedCurrIndex],(byte)functionItr.next());
            lastRetIndex=expectedCurrIndex++;
            expectedLastRet=expectedCurr;
            expectedCurr=expectedCurr.next;
          }
          this.expectedCurr=expectedCurr;
          this.expectedLastRet=expectedLastRet;
          break;
        }
        case CHAR:{
          final var expectedArr=(char[])DblLnkSeqMonitor.this.expectedArr;
          var expectedCurr=(CharDblLnkNode)this.expectedCurr;
          var expectedLastRet=(CharDblLnkNode)this.expectedLastRet;
          while(expectedCurrIndex<expectedBound) {
            Assertions.assertEquals(expectedArr[expectedCurrIndex],(char)functionItr.next());
            lastRetIndex=expectedCurrIndex++;
            expectedLastRet=expectedCurr;
            expectedCurr=expectedCurr.next;
          }
          this.expectedCurr=expectedCurr;
          this.expectedLastRet=expectedLastRet;
          break;
        }
        case DOUBLE:{
          final var expectedArr=(double[])DblLnkSeqMonitor.this.expectedArr;
          var expectedCurr=(DoubleDblLnkNode)this.expectedCurr;
          var expectedLastRet=(DoubleDblLnkNode)this.expectedLastRet;
          while(expectedCurrIndex<expectedBound) {
            Assertions.assertEquals(expectedArr[expectedCurrIndex],(double)functionItr.next());
            lastRetIndex=expectedCurrIndex++;
            expectedLastRet=expectedCurr;
            expectedCurr=expectedCurr.next;
          }
          this.expectedCurr=expectedCurr;
          this.expectedLastRet=expectedLastRet;
          break;
        }
        case FLOAT:{
          final var expectedArr=(float[])DblLnkSeqMonitor.this.expectedArr;
          var expectedCurr=(FloatDblLnkNode)this.expectedCurr;
          var expectedLastRet=(FloatDblLnkNode)this.expectedLastRet;
          while(expectedCurrIndex<expectedBound) {
            Assertions.assertEquals(expectedArr[expectedCurrIndex],(float)functionItr.next());
            lastRetIndex=expectedCurrIndex++;
            expectedLastRet=expectedCurr;
            expectedCurr=expectedCurr.next;
          }
          this.expectedCurr=expectedCurr;
          this.expectedLastRet=expectedLastRet;
          break;
        }
        case INT:{
          final var expectedArr=(int[])DblLnkSeqMonitor.this.expectedArr;
          var expectedCurr=(IntDblLnkNode)this.expectedCurr;
          var expectedLastRet=(IntDblLnkNode)this.expectedLastRet;
          while(expectedCurrIndex<expectedBound) {
            Assertions.assertEquals(expectedArr[expectedCurrIndex],(int)functionItr.next());
            lastRetIndex=expectedCurrIndex++;
            expectedLastRet=expectedCurr;
            expectedCurr=expectedCurr.next;
          }
          this.expectedCurr=expectedCurr;
          this.expectedLastRet=expectedLastRet;
          break;
        }
        case LONG:{
          final var expectedArr=(long[])DblLnkSeqMonitor.this.expectedArr;
          var expectedCurr=(LongDblLnkNode)this.expectedCurr;
          var expectedLastRet=(LongDblLnkNode)this.expectedLastRet;
          while(expectedCurrIndex<expectedBound) {
            Assertions.assertEquals(expectedArr[expectedCurrIndex],(long)functionItr.next());
            lastRetIndex=expectedCurrIndex++;
            expectedLastRet=expectedCurr;
            expectedCurr=expectedCurr.next;
          }
          this.expectedCurr=expectedCurr;
          this.expectedLastRet=expectedLastRet;
          break;
        }
        case REF:{
          final var expectedArr=(Object[])DblLnkSeqMonitor.this.expectedArr;
          @SuppressWarnings("unchecked") var expectedCurr=(RefDblLnkNode<E>)this.expectedCurr;
          @SuppressWarnings("unchecked") var expectedLastRet=(RefDblLnkNode<E>)this.expectedLastRet;
          while(expectedCurrIndex<expectedBound) {
            Assertions.assertSame(expectedArr[expectedCurrIndex],functionItr.next());
            lastRetIndex=expectedCurrIndex++;
            expectedLastRet=expectedCurr;
            expectedCurr=expectedCurr.next;
          }
          this.expectedCurr=expectedCurr;
          this.expectedLastRet=expectedLastRet;
          break;
        }
        case SHORT:{
          final var expectedArr=(short[])DblLnkSeqMonitor.this.expectedArr;
          var expectedCurr=(ShortDblLnkNode)this.expectedCurr;
          var expectedLastRet=(ShortDblLnkNode)this.expectedLastRet;
          while(expectedCurrIndex<expectedBound) {
            Assertions.assertEquals(expectedArr[expectedCurrIndex],(short)functionItr.next());
            lastRetIndex=expectedCurrIndex++;
            expectedLastRet=expectedCurr;
            expectedCurr=expectedCurr.next;
          }
          this.expectedCurr=expectedCurr;
          this.expectedLastRet=expectedLastRet;
          break;
        }
        default:
          throw dataType.invalid();
        }
        this.expectedCurrIndex=expectedCurrIndex;
        this.lastRetIndex=lastRetIndex;
      }
    }
    private class ItrMonitor extends AbstractItrMonitor<OmniIterator<E>>{
      ItrMonitor(){
        super(seq.iterator(),0,head(seq));
      }
      @Override public IteratorType getIteratorType(){
        return IteratorType.AscendingItr;
      }
      @Override public void verifyCloneHelper(Object clone){
        var checked=checkedType.checked;
        switch(dataType) {
        case BOOLEAN:{
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.AscendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.AscendingItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.AscendingItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.AscendingItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.AscendingItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedList.AscendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedList.AscendingItr.curr(itr));
          }
          break;
        }
        case BYTE:{
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.AscendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.AscendingItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.AscendingItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.AscendingItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.AscendingItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedList.AscendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedList.AscendingItr.curr(itr));
          }
          break;
        }
        case CHAR:{
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.AscendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.AscendingItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.AscendingItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.AscendingItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.AscendingItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.CharDblLnkSeq.UncheckedList.AscendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.CharDblLnkSeq.UncheckedList.AscendingItr.curr(itr));
          }
          break;
        }
        case DOUBLE:{
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.AscendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.AscendingItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.AscendingItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.AscendingItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.AscendingItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleDblLnkSeq.UncheckedList.AscendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.DoubleDblLnkSeq.UncheckedList.AscendingItr.curr(itr));
          }
          break;
        }
        case FLOAT:{
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.AscendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.AscendingItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.AscendingItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.AscendingItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.AscendingItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedList.AscendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedList.AscendingItr.curr(itr));
          }
          break;
        }
        case INT:{
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.AscendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.AscendingItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.AscendingItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.AscendingItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.AscendingItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.IntDblLnkSeq.UncheckedList.AscendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.IntDblLnkSeq.UncheckedList.AscendingItr.curr(itr));
          }
          break;
        }
        case LONG:{
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.AscendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.AscendingItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.AscendingItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.AscendingItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.AscendingItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.LongDblLnkSeq.UncheckedList.AscendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.LongDblLnkSeq.UncheckedList.AscendingItr.curr(itr));
          }
          break;
        }
        case REF:{
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.AscendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.AscendingItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.AscendingItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.AscendingItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.AscendingItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.RefDblLnkSeq.UncheckedList.AscendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.RefDblLnkSeq.UncheckedList.AscendingItr.curr(itr));
          }
          break;
        }
        case SHORT:{
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.AscendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.AscendingItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.AscendingItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.AscendingItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.AscendingItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedList.AscendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedList.AscendingItr.curr(itr));
          }
          break;
        }
        default:
          throw dataType.invalid();
        }
      }
    }
    private class ListItrMonitor extends AbstractItrMonitor<OmniListIterator<E>> implements MonitoredListIterator<OmniListIterator<E>,LSTDEQ>{
      ListItrMonitor(){
        super(seq.listIterator(),0,head(seq));
      }
      ListItrMonitor(int index){
        super(seq.listIterator(index),index,getItrNode(index,seq,expectedSize));
      }
      @Override public IteratorType getIteratorType(){
        return IteratorType.BidirectionalItr;
      }
      @Override public void verifyCloneHelper(Object clone){
        var checked=checkedType.checked;
        switch(dataType) {
        case BOOLEAN:{
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.BidirectionalItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.BidirectionalItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.BidirectionalItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.BidirectionalItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.BidirectionalItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedList.BidirectionalItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedList.BidirectionalItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedList.BidirectionalItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedList.BidirectionalItr.currIndex(itr));
          }
          break;
        }
        case BYTE:{
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.BidirectionalItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.BidirectionalItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.BidirectionalItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.BidirectionalItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.BidirectionalItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedList.BidirectionalItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedList.BidirectionalItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedList.BidirectionalItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedList.BidirectionalItr.currIndex(itr));
          }
          break;
        }
        case CHAR:{
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.BidirectionalItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.BidirectionalItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.BidirectionalItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.BidirectionalItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.BidirectionalItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.CharDblLnkSeq.UncheckedList.BidirectionalItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.CharDblLnkSeq.UncheckedList.BidirectionalItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.CharDblLnkSeq.UncheckedList.BidirectionalItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.CharDblLnkSeq.UncheckedList.BidirectionalItr.currIndex(itr));
          }
          break;
        }
        case DOUBLE:{
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.BidirectionalItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.BidirectionalItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.BidirectionalItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.BidirectionalItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.BidirectionalItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleDblLnkSeq.UncheckedList.BidirectionalItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.DoubleDblLnkSeq.UncheckedList.BidirectionalItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.DoubleDblLnkSeq.UncheckedList.BidirectionalItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.DoubleDblLnkSeq.UncheckedList.BidirectionalItr.currIndex(itr));
          }
          break;
        }
        case FLOAT:{
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.BidirectionalItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.BidirectionalItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.BidirectionalItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.BidirectionalItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.BidirectionalItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedList.BidirectionalItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedList.BidirectionalItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedList.BidirectionalItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedList.BidirectionalItr.currIndex(itr));
          }
          break;
        }
        case INT:{
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.BidirectionalItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.BidirectionalItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.BidirectionalItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.BidirectionalItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.BidirectionalItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.IntDblLnkSeq.UncheckedList.BidirectionalItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.IntDblLnkSeq.UncheckedList.BidirectionalItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.IntDblLnkSeq.UncheckedList.BidirectionalItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.IntDblLnkSeq.UncheckedList.BidirectionalItr.currIndex(itr));
          }
          break;
        }
        case LONG:{
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.BidirectionalItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.BidirectionalItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.BidirectionalItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.BidirectionalItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.BidirectionalItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.LongDblLnkSeq.UncheckedList.BidirectionalItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.LongDblLnkSeq.UncheckedList.BidirectionalItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.LongDblLnkSeq.UncheckedList.BidirectionalItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.LongDblLnkSeq.UncheckedList.BidirectionalItr.currIndex(itr));
          }
          break;
        }
        case REF:{
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.BidirectionalItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.BidirectionalItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.BidirectionalItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.BidirectionalItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.BidirectionalItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.RefDblLnkSeq.UncheckedList.BidirectionalItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.RefDblLnkSeq.UncheckedList.BidirectionalItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.RefDblLnkSeq.UncheckedList.BidirectionalItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.RefDblLnkSeq.UncheckedList.BidirectionalItr.currIndex(itr));
          }
          break;
        }
        case SHORT:{
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.BidirectionalItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.BidirectionalItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.BidirectionalItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.BidirectionalItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.BidirectionalItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedList.BidirectionalItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedList.BidirectionalItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedList.BidirectionalItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedList.BidirectionalItr.currIndex(itr));
          }
          break;
        }
        default:
          throw dataType.invalid();
        }
      }
      @Override public boolean previousWasJustCalled(){
        return this.lastRetIndex==this.expectedCurrIndex;
      }
      @Override public void updateItrPreviousState(){
        if(expectedCurr==null) {
          expectedCurr=tail(seq);
        }else {
          expectedCurr=prev(expectedCurr);
        }
        this.expectedLastRet=expectedCurr;
        this.lastRetIndex=--expectedCurrIndex;
      }
      @Override public void verifyPreviousResult(DataType outputType,Object result){
        verifyGetResult(lastRetIndex,result,outputType);
      }
      @Override public boolean hasPrevious(){
        return expectedCurrIndex>0;
      }
      @Override public int nextIndex(){
        return expectedCurrIndex;
      }
      @Override public int previousIndex(){
        return expectedCurrIndex-1;
      }
      @Override public void updateItrSetState(Object input,DataType inputType){
        verifyPutResult(lastRetIndex,input,inputType);
      }
      @Override public void updateItrAddState(Object input,DataType inputType){
        DblLnkSeqMonitor.this.updateAddState(expectedCurrIndex++,input,inputType);
        ++expectedItrModCount;
        this.lastRetIndex=-1;
        this.expectedLastRet=null;
      }
    }
    private class DescendingItrMonitor implements MonitoredIterator<OmniIterator<E>,LSTDEQ>{
      final OmniIterator<E> itr=seq.iterator();
      Object expectedCurr=tail(seq);
      Object expectedLastRet;
      int expectedCurrIndex=expectedSize;
      int expectedItrModCount=expectedModCount;
      int lastRetIndex=-1;
      @Override public boolean nextWasJustCalled(){
        return this.lastRetIndex!=-1;
      }
      @Override public void updateItrNextState(){
        this.expectedCurr=prev(this.expectedLastRet=this.expectedCurr);
        this.lastRetIndex=--expectedCurrIndex;
      }
      @Override public void verifyNextResult(DataType outputType,Object result){
        verifyGetResult(lastRetIndex,result,outputType);
      }
      @Override public void updateItrRemoveState(){
        updateRemoveIndexState(lastRetIndex);
        this.expectedLastRet=null;
        this.lastRetIndex=-1;
        ++expectedItrModCount;
      }
      @Override public OmniIterator<E> getIterator(){
        return itr;
      }
      @Override public IteratorType getIteratorType(){
        return IteratorType.DescendingItr;
      }
      @Override public MonitoredCollection<LSTDEQ> getMonitoredCollection(){
        return DblLnkSeqMonitor.this;
      }
      @Override public boolean hasNext(){
        return expectedCurrIndex>0;
      }
      @Override public int getNumLeft(){
        return expectedCurrIndex;
      }
      @Override public void verifyForEachRemaining(MonitoredFunction function){
        var itr=function.iterator();
        int expectedCurrIndex=this.expectedCurrIndex;
        int lastRetIndex=this.lastRetIndex;
        Object expectedLastRet=this.expectedLastRet;
        switch(dataType) {
        case BOOLEAN:{
          var arr=(boolean[])expectedArr;
          var castCurr=(BooleanDblLnkNode)expectedCurr;
          while(expectedCurrIndex>0) {
            Assertions.assertEquals(arr[lastRetIndex=--expectedCurrIndex],(boolean)itr.next());
            expectedLastRet=castCurr=castCurr.prev;
          }
          break;
        }
        case BYTE:{
          var arr=(byte[])expectedArr;
          var castCurr=(ByteDblLnkNode)expectedCurr;
          while(expectedCurrIndex>0) {
            Assertions.assertEquals(arr[lastRetIndex=--expectedCurrIndex],(byte)itr.next());
            expectedLastRet=castCurr=castCurr.prev;
          }
          break;
        }
        case CHAR:{
          var arr=(char[])expectedArr;
          var castCurr=(CharDblLnkNode)expectedCurr;
          while(expectedCurrIndex>0) {
            Assertions.assertEquals(arr[lastRetIndex=--expectedCurrIndex],(char)itr.next());
            expectedLastRet=castCurr=castCurr.prev;
          }
          break;
        }
        case DOUBLE:{
          var arr=(double[])expectedArr;
          var castCurr=(DoubleDblLnkNode)expectedCurr;
          while(expectedCurrIndex>0) {
            Assertions.assertEquals(arr[lastRetIndex=--expectedCurrIndex],(double)itr.next());
            expectedLastRet=castCurr=castCurr.prev;
          }
          break;
        }
        case FLOAT:{
          var arr=(float[])expectedArr;
          var castCurr=(FloatDblLnkNode)expectedCurr;
          while(expectedCurrIndex>0) {
            Assertions.assertEquals(arr[lastRetIndex=--expectedCurrIndex],(float)itr.next());
            expectedLastRet=castCurr=castCurr.prev;
          }
          break;
        }
        case INT:{
          var arr=(int[])expectedArr;
          var castCurr=(IntDblLnkNode)expectedCurr;
          while(expectedCurrIndex>0) {
            Assertions.assertEquals(arr[lastRetIndex=--expectedCurrIndex],(int)itr.next());
            expectedLastRet=castCurr=castCurr.prev;
          }
          break;
        }
        case LONG:{
          var arr=(long[])expectedArr;
          var castCurr=(LongDblLnkNode)expectedCurr;
          while(expectedCurrIndex>0) {
            Assertions.assertEquals(arr[lastRetIndex=--expectedCurrIndex],(long)itr.next());
            expectedLastRet=castCurr=castCurr.prev;
          }
          break;
        }
        case REF:{
          var arr=(Object[])expectedArr;
          @SuppressWarnings("unchecked") var castCurr=(RefDblLnkNode<E>)expectedCurr;
          while(expectedCurrIndex>0) {
            Assertions.assertSame(arr[lastRetIndex=--expectedCurrIndex],itr.next());
            expectedLastRet=castCurr=castCurr.prev;
          }
          break;
        }
        case SHORT:{
          var arr=(short[])expectedArr;
          var castCurr=(ShortDblLnkNode)expectedCurr;
          while(expectedCurrIndex>0) {
            Assertions.assertEquals(arr[lastRetIndex=--expectedCurrIndex],(short)itr.next());
            expectedLastRet=castCurr=castCurr.prev;
          }
          break;
        }
        default:
          throw dataType.invalid();
        }
        this.expectedLastRet=expectedLastRet;
        this.expectedCurr=null;
        this.lastRetIndex=lastRetIndex;
        this.expectedCurrIndex=expectedCurrIndex;
        
      }
      @Override public void verifyCloneHelper(Object clone){
        var checked=checkedType.checked;
        switch(dataType) {
        case BOOLEAN:
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.DescendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.DescendingItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.DescendingItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.DescendingItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.DescendingItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedList.DescendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedList.DescendingItr.curr(itr));
          }
          break;
        case BYTE:
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.DescendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.DescendingItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.DescendingItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.DescendingItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.DescendingItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedList.DescendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedList.DescendingItr.curr(itr));
          }
          break;
        case CHAR:
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.DescendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.DescendingItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.DescendingItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.DescendingItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.DescendingItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.CharDblLnkSeq.UncheckedList.DescendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.CharDblLnkSeq.UncheckedList.DescendingItr.curr(itr));
          }
          break;
        case DOUBLE:
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.DescendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.DescendingItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.DescendingItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.DescendingItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.DescendingItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleDblLnkSeq.UncheckedList.DescendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.DoubleDblLnkSeq.UncheckedList.DescendingItr.curr(itr));
          }
          break;
        case FLOAT:
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.DescendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.DescendingItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.DescendingItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.DescendingItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.DescendingItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedList.DescendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedList.DescendingItr.curr(itr));
          }
          break;
        case INT:
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.DescendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.DescendingItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.DescendingItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.DescendingItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.DescendingItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.IntDblLnkSeq.UncheckedList.DescendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.IntDblLnkSeq.UncheckedList.DescendingItr.curr(itr));
          }
          break;
        case LONG:
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.DescendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.DescendingItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.DescendingItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.DescendingItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.DescendingItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.LongDblLnkSeq.UncheckedList.DescendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.LongDblLnkSeq.UncheckedList.DescendingItr.curr(itr));
          }
          break;
        case REF:
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.DescendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.DescendingItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.DescendingItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.DescendingItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.DescendingItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.RefDblLnkSeq.UncheckedList.DescendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.RefDblLnkSeq.UncheckedList.DescendingItr.curr(itr));
          }
          break;
        case SHORT:
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.DescendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.DescendingItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.DescendingItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.DescendingItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.DescendingItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedList.DescendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedList.DescendingItr.curr(itr));
          }
          break;
        default:
          throw dataType.invalid();
        }
      }
    }
    
    
    DblLnkSeqMonitor(SequenceInitParams initParams,int initCapacity){
      super(initParams.checkedType,initParams.collectionType,initCapacity);
      updateCollectionState();
    }
    DblLnkSeqMonitor(CheckedType checkedType,DataType dataType){
      super(checkedType,dataType);
      updateCollectionState();
    }
    DblLnkSeqMonitor(CheckedType checkedType,DataType dataType,int capacity){
      super(checkedType,dataType,capacity);
      updateCollectionState();
    }
    
    @Override public MonitoredList<?> getMonitoredSubList(int fromIndex,int toIndex){
      return new SubListMonitor<>(this,fromIndex,toIndex);
    }
    @Override public MonitoredIterator<?,LSTDEQ> getMonitoredDescendingIterator(){
      return new DescendingItrMonitor();
    }
    @Override public MonitoredIterator<? extends OmniIterator<?>,LSTDEQ> getMonitoredIterator(){
      return new ItrMonitor();
    }
    @Override public MonitoredListIterator<? extends OmniListIterator<?>,LSTDEQ> getMonitoredListIterator(){
      return new ListItrMonitor();
    }
    @Override public MonitoredListIterator<? extends OmniListIterator<?>,LSTDEQ> getMonitoredListIterator(int index){
      return new ListItrMonitor(index);
    }
    @Override public void copyListContents(){
      final int expectedSize=seq.size;
      int oldExpectedSize=this.expectedSize;
      switch(dataType){
      case BOOLEAN:{
        final var cast=(BooleanDblLnkSeq)seq;
        var expectedArr=(boolean[])this.expectedArr;
        int expectedCapacity;
        if((expectedCapacity=this.expectedCapacity)<expectedSize) {
            this.expectedArr=expectedArr=new boolean[this.expectedCapacity=expectedArr==OmniArray.OfBoolean.DEFAULT_ARR && expectedSize<=OmniArray.DEFAULT_ARR_SEQ_CAP?OmniArray.DEFAULT_ARR_SEQ_CAP:OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
        }
        var currNode=cast.tail;
        for(int i=expectedSize;;){
          if(currNode == null){
            break;
          }
          expectedArr[--i]=currNode.val;
          currNode=currNode.prev;
        }
        break;
      }
      case BYTE:{
        final var cast=(ByteDblLnkSeq)seq;
        var expectedArr=(byte[])this.expectedArr;
        int expectedCapacity;
        if((expectedCapacity=this.expectedCapacity)<expectedSize) {
            this.expectedArr=expectedArr=new byte[this.expectedCapacity=expectedArr==OmniArray.OfByte.DEFAULT_ARR && expectedSize<=OmniArray.DEFAULT_ARR_SEQ_CAP?OmniArray.DEFAULT_ARR_SEQ_CAP:OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
        }
        var currNode=cast.tail;
        for(int i=expectedSize;;){
          if(currNode == null){
            break;
          }
          expectedArr[--i]=currNode.val;
          currNode=currNode.prev;
        }
        break;
      }
      case CHAR:{
        final var cast=(CharDblLnkSeq)seq;
        var expectedArr=(char[])this.expectedArr;
        int expectedCapacity;
        if((expectedCapacity=this.expectedCapacity)<expectedSize) {
            this.expectedArr=expectedArr=new char[this.expectedCapacity=expectedArr==OmniArray.OfChar.DEFAULT_ARR && expectedSize<=OmniArray.DEFAULT_ARR_SEQ_CAP?OmniArray.DEFAULT_ARR_SEQ_CAP:OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
        }
        var currNode=cast.tail;
        for(int i=expectedSize;;){
          if(currNode == null){
            break;
          }
          expectedArr[--i]=currNode.val;
          currNode=currNode.prev;
        }
        break;
      }
      case DOUBLE:{
        final var cast=(DoubleDblLnkSeq)seq;
        var expectedArr=(double[])this.expectedArr;
        int expectedCapacity;
        if((expectedCapacity=this.expectedCapacity)<expectedSize) {
            this.expectedArr=expectedArr=new double[this.expectedCapacity=expectedArr==OmniArray.OfDouble.DEFAULT_ARR && expectedSize<=OmniArray.DEFAULT_ARR_SEQ_CAP?OmniArray.DEFAULT_ARR_SEQ_CAP:OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
        }
        var currNode=cast.tail;
        for(int i=expectedSize;;){
          if(currNode == null){
            break;
          }
          expectedArr[--i]=currNode.val;
          currNode=currNode.prev;
        }
        break;
      }
      case FLOAT:{
        final var cast=(FloatDblLnkSeq)seq;
        var expectedArr=(float[])this.expectedArr;
        int expectedCapacity;
        if((expectedCapacity=this.expectedCapacity)<expectedSize) {
            this.expectedArr=expectedArr=new float[this.expectedCapacity=expectedArr==OmniArray.OfFloat.DEFAULT_ARR && expectedSize<=OmniArray.DEFAULT_ARR_SEQ_CAP?OmniArray.DEFAULT_ARR_SEQ_CAP:OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
        }
        var currNode=cast.tail;
        for(int i=expectedSize;;){
          if(currNode == null){
            break;
          }
          expectedArr[--i]=currNode.val;
          currNode=currNode.prev;
        }
        break;
      }
      case INT:{
        final var cast=(IntDblLnkSeq)seq;
        var expectedArr=(int[])this.expectedArr;
        int expectedCapacity;
        if((expectedCapacity=this.expectedCapacity)<expectedSize) {
            this.expectedArr=expectedArr=new int[this.expectedCapacity=expectedArr==OmniArray.OfInt.DEFAULT_ARR && expectedSize<=OmniArray.DEFAULT_ARR_SEQ_CAP?OmniArray.DEFAULT_ARR_SEQ_CAP:OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
        }
        var currNode=cast.tail;
        for(int i=expectedSize;;){
          if(currNode == null){
            break;
          }
          expectedArr[--i]=currNode.val;
          currNode=currNode.prev;
        }
        break;
      }
      case LONG:{
        final var cast=(LongDblLnkSeq)seq;
        var expectedArr=(long[])this.expectedArr;
        int expectedCapacity;
        if((expectedCapacity=this.expectedCapacity)<expectedSize) {
            this.expectedArr=expectedArr=new long[this.expectedCapacity=expectedArr==OmniArray.OfLong.DEFAULT_ARR && expectedSize<=OmniArray.DEFAULT_ARR_SEQ_CAP?OmniArray.DEFAULT_ARR_SEQ_CAP:OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
        }
        var currNode=cast.tail;
        for(int i=expectedSize;;){
          if(currNode == null){
            break;
          }
          expectedArr[--i]=currNode.val;
          currNode=currNode.prev;
        }
        break;
      }
      case REF:{
        @SuppressWarnings("unchecked") final var cast=(RefDblLnkSeq<E>)seq;
        var expectedArr=(Object[])this.expectedArr;
        int expectedCapacity;
        if((expectedCapacity=this.expectedCapacity)<expectedSize) {
            this.expectedArr=expectedArr=new Object[this.expectedCapacity=expectedArr==OmniArray.OfRef.DEFAULT_ARR && expectedSize<=OmniArray.DEFAULT_ARR_SEQ_CAP?OmniArray.DEFAULT_ARR_SEQ_CAP:OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
        }
        var currNode=cast.tail;
        for(int i=expectedSize;;){
          if(currNode == null){
            break;
          }
          expectedArr[--i]=currNode.val;
          currNode=currNode.prev;
        }
        while(oldExpectedSize > expectedSize){
          expectedArr[--oldExpectedSize]=null;
        }
        break;
      }
      case SHORT:{
        final var cast=(ShortDblLnkSeq)seq;
        var expectedArr=(short[])this.expectedArr;
        int expectedCapacity;
        if((expectedCapacity=this.expectedCapacity)<expectedSize) {
            this.expectedArr=expectedArr=new short[this.expectedCapacity=expectedArr==OmniArray.OfShort.DEFAULT_ARR && expectedSize<=OmniArray.DEFAULT_ARR_SEQ_CAP?OmniArray.DEFAULT_ARR_SEQ_CAP:OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
        }
        var currNode=cast.tail;
        for(int i=expectedSize;;){
          if(currNode == null){
            break;
          }
          expectedArr[--i]=currNode.val;
          currNode=currNode.prev;
        }
        break;
      }
      default:
        throw dataType.invalid();
      }
    }
    @Override public MonitoredIterator<? extends OmniIterator<?>,LSTDEQ> getMonitoredIterator(int index,
        IteratorType itrType){
      MonitoredIterator<? extends OmniIterator<?>,LSTDEQ>  itr;
      switch(itrType) {
      case BidirectionalItr:
        return getMonitoredListIterator(index);
      default:
        throw itrType.invalid();
      case AscendingItr:
        itr=getMonitoredIterator();
        break;
      case DescendingItr:
        itr=getMonitoredDescendingIterator();
      }
      while(--index>=0 && itr.hasNext()) {
        itr.iterateForward();
      }
      return itr;
    }
    @Override public MonitoredIterator<? extends OmniIterator<?>,LSTDEQ> getMonitoredIterator(IteratorType itrType){
      switch(itrType) {
      case AscendingItr:
        return getMonitoredIterator();
      case BidirectionalItr:
        return getMonitoredListIterator();
      case DescendingItr:
        return getMonitoredDescendingIterator();
      default:
        throw itrType.invalid();
      }
    }
    @Override public StructType getStructType(){
      return StructType.DblLnkList;
    }
    @Override public void modCollection(){
      switch(dataType){
      case BOOLEAN:
        ++((BooleanDblLnkSeq.CheckedList)seq).modCount;
        break;
      case BYTE:
        ++((ByteDblLnkSeq.CheckedList)seq).modCount;
        break;
      case CHAR:
        ++((CharDblLnkSeq.CheckedList)seq).modCount;
        break;
      case DOUBLE:
        ++((DoubleDblLnkSeq.CheckedList)seq).modCount;
        break;
      case FLOAT:
        ++((FloatDblLnkSeq.CheckedList)seq).modCount;
        break;
      case INT:
        ++((IntDblLnkSeq.CheckedList)seq).modCount;
        break;
      case LONG:
        ++((LongDblLnkSeq.CheckedList)seq).modCount;
        break;
      case REF:
        ++((RefDblLnkSeq.CheckedList<?>)seq).modCount;
        break;
      case SHORT:
        ++((ShortDblLnkSeq.CheckedList)seq).modCount;
        break;
      default:
        throw dataType.invalid();
      }
      ++expectedModCount;
    }
    @Override public void verifyArrayIsCopy(Object arr,boolean emptyArrayMayBeSame){
      // nothing to do
    }
    @Override public void verifyClone(Object clone){
      Assertions.assertNotSame(clone,seq);
      int size;
      Assertions.assertEquals(size=seq.size,((AbstractSeq<?>)clone).size);
      switch(dataType){
      case BOOLEAN:{
        var cloneCast=(BooleanDblLnkSeq)clone;
        Assertions.assertEquals(checkedType.checked,cloneCast instanceof BooleanDblLnkSeq.CheckedList);
        if(checkedType.checked) {
          Assertions.assertEquals(0,((BooleanDblLnkSeq.CheckedList)cloneCast).modCount);
        }else {
          Assertions.assertTrue(cloneCast instanceof BooleanDblLnkSeq.UncheckedList);
        }
        var cloneHead=cloneCast.head;
        var cloneTail=cloneCast.tail;
        if(size==0) {
          Assertions.assertNull(cloneHead);
          Assertions.assertNull(cloneTail);
        }else {
          var thisCast=(BooleanDblLnkSeq)seq;
          var thisHead=thisCast.head;
          var thisTail=thisCast.tail;
          var clonePrev=cloneHead.prev;
          Assertions.assertNull(clonePrev);
          for(int i=0;i < size;++i){
            Assertions.assertNotSame(cloneHead,thisHead);
            Assertions.assertEquals(cloneHead.val,thisHead.val);
            Assertions.assertSame(clonePrev,cloneHead.prev);
            clonePrev=cloneHead;
            cloneHead=cloneHead.next;
            thisHead=thisHead.next;
          }
          Assertions.assertSame(cloneTail,clonePrev);
          Assertions.assertNotSame(cloneTail,thisTail);
          Assertions.assertNull(cloneHead);
        }
        break;
      }
      case BYTE:{
        var cloneCast=(ByteDblLnkSeq)clone;
        Assertions.assertEquals(checkedType.checked,cloneCast instanceof ByteDblLnkSeq.CheckedList);
        if(checkedType.checked) {
          Assertions.assertEquals(0,((ByteDblLnkSeq.CheckedList)cloneCast).modCount);
        }else {
          Assertions.assertTrue(cloneCast instanceof ByteDblLnkSeq.UncheckedList);
        }
        var cloneHead=cloneCast.head;
        var cloneTail=cloneCast.tail;
        if(size==0) {
          Assertions.assertNull(cloneHead);
          Assertions.assertNull(cloneTail);
        }else {
          var thisCast=(ByteDblLnkSeq)seq;
          var thisHead=thisCast.head;
          var thisTail=thisCast.tail;
          var clonePrev=cloneHead.prev;
          Assertions.assertNull(clonePrev);
          for(int i=0;i < size;++i){
            Assertions.assertNotSame(cloneHead,thisHead);
            Assertions.assertEquals(cloneHead.val,thisHead.val);
            Assertions.assertSame(clonePrev,cloneHead.prev);
            clonePrev=cloneHead;
            cloneHead=cloneHead.next;
            thisHead=thisHead.next;
          }
          Assertions.assertSame(cloneTail,clonePrev);
          Assertions.assertNotSame(cloneTail,thisTail);
          Assertions.assertNull(cloneHead);
        }
        break;
      }
      case CHAR:{
        var cloneCast=(CharDblLnkSeq)clone;
        Assertions.assertEquals(checkedType.checked,cloneCast instanceof CharDblLnkSeq.CheckedList);
        if(checkedType.checked) {
          Assertions.assertEquals(0,((CharDblLnkSeq.CheckedList)cloneCast).modCount);
        }else {
          Assertions.assertTrue(cloneCast instanceof CharDblLnkSeq.UncheckedList);
        }
        var cloneHead=cloneCast.head;
        var cloneTail=cloneCast.tail;
        if(size==0) {
          Assertions.assertNull(cloneHead);
          Assertions.assertNull(cloneTail);
        }else {
          var thisCast=(CharDblLnkSeq)seq;
          var thisHead=thisCast.head;
          var thisTail=thisCast.tail;
          var clonePrev=cloneHead.prev;
          Assertions.assertNull(clonePrev);
          for(int i=0;i < size;++i){
            Assertions.assertNotSame(cloneHead,thisHead);
            Assertions.assertEquals(cloneHead.val,thisHead.val);
            Assertions.assertSame(clonePrev,cloneHead.prev);
            clonePrev=cloneHead;
            cloneHead=cloneHead.next;
            thisHead=thisHead.next;
          }
          Assertions.assertSame(cloneTail,clonePrev);
          Assertions.assertNotSame(cloneTail,thisTail);
          Assertions.assertNull(cloneHead);
        }
        break;
      }
      case DOUBLE:{
        var cloneCast=(DoubleDblLnkSeq)clone;
        Assertions.assertEquals(checkedType.checked,cloneCast instanceof DoubleDblLnkSeq.CheckedList);
        if(checkedType.checked) {
          Assertions.assertEquals(0,((DoubleDblLnkSeq.CheckedList)cloneCast).modCount);
        }else {
          Assertions.assertTrue(cloneCast instanceof DoubleDblLnkSeq.UncheckedList);
        }
        var cloneHead=cloneCast.head;
        var cloneTail=cloneCast.tail;
        if(size==0) {
          Assertions.assertNull(cloneHead);
          Assertions.assertNull(cloneTail);
        }else {
          var thisCast=(DoubleDblLnkSeq)seq;
          var thisHead=thisCast.head;
          var thisTail=thisCast.tail;
          var clonePrev=cloneHead.prev;
          Assertions.assertNull(clonePrev);
          for(int i=0;i < size;++i){
            Assertions.assertNotSame(cloneHead,thisHead);
            Assertions.assertEquals(cloneHead.val,thisHead.val);
            Assertions.assertSame(clonePrev,cloneHead.prev);
            clonePrev=cloneHead;
            cloneHead=cloneHead.next;
            thisHead=thisHead.next;
          }
          Assertions.assertSame(cloneTail,clonePrev);
          Assertions.assertNotSame(cloneTail,thisTail);
          Assertions.assertNull(cloneHead);
        }
        break;
      }
      case FLOAT:{
        var cloneCast=(FloatDblLnkSeq)clone;
        Assertions.assertEquals(checkedType.checked,cloneCast instanceof FloatDblLnkSeq.CheckedList);
        if(checkedType.checked) {
          Assertions.assertEquals(0,((FloatDblLnkSeq.CheckedList)cloneCast).modCount);
        }else {
          Assertions.assertTrue(cloneCast instanceof FloatDblLnkSeq.UncheckedList);
        }
        var cloneHead=cloneCast.head;
        var cloneTail=cloneCast.tail;
        if(size==0) {
          Assertions.assertNull(cloneHead);
          Assertions.assertNull(cloneTail);
        }else {
          var thisCast=(FloatDblLnkSeq)seq;
          var thisHead=thisCast.head;
          var thisTail=thisCast.tail;
          var clonePrev=cloneHead.prev;
          Assertions.assertNull(clonePrev);
          for(int i=0;i < size;++i){
            Assertions.assertNotSame(cloneHead,thisHead);
            Assertions.assertEquals(cloneHead.val,thisHead.val);
            Assertions.assertSame(clonePrev,cloneHead.prev);
            clonePrev=cloneHead;
            cloneHead=cloneHead.next;
            thisHead=thisHead.next;
          }
          Assertions.assertSame(cloneTail,clonePrev);
          Assertions.assertNotSame(cloneTail,thisTail);
          Assertions.assertNull(cloneHead);
        }
        break;
      }
      case INT:{
        var cloneCast=(IntDblLnkSeq)clone;
        Assertions.assertEquals(checkedType.checked,cloneCast instanceof IntDblLnkSeq.CheckedList);
        if(checkedType.checked) {
          Assertions.assertEquals(0,((IntDblLnkSeq.CheckedList)cloneCast).modCount);
        }else {
          Assertions.assertTrue(cloneCast instanceof IntDblLnkSeq.UncheckedList);
        }
        var cloneHead=cloneCast.head;
        var cloneTail=cloneCast.tail;
        if(size==0) {
          Assertions.assertNull(cloneHead);
          Assertions.assertNull(cloneTail);
        }else {
          var thisCast=(IntDblLnkSeq)seq;
          var thisHead=thisCast.head;
          var thisTail=thisCast.tail;
          var clonePrev=cloneHead.prev;
          Assertions.assertNull(clonePrev);
          for(int i=0;i < size;++i){
            Assertions.assertNotSame(cloneHead,thisHead);
            Assertions.assertEquals(cloneHead.val,thisHead.val);
            Assertions.assertSame(clonePrev,cloneHead.prev);
            clonePrev=cloneHead;
            cloneHead=cloneHead.next;
            thisHead=thisHead.next;
          }
          Assertions.assertSame(cloneTail,clonePrev);
          Assertions.assertNotSame(cloneTail,thisTail);
          Assertions.assertNull(cloneHead);
        }
        break;
      }
      case LONG:{
        var cloneCast=(LongDblLnkSeq)clone;
        Assertions.assertEquals(checkedType.checked,cloneCast instanceof LongDblLnkSeq.CheckedList);
        if(checkedType.checked) {
          Assertions.assertEquals(0,((LongDblLnkSeq.CheckedList)cloneCast).modCount);
        }else {
          Assertions.assertTrue(cloneCast instanceof LongDblLnkSeq.UncheckedList);
        }
        var cloneHead=cloneCast.head;
        var cloneTail=cloneCast.tail;
        if(size==0) {
          Assertions.assertNull(cloneHead);
          Assertions.assertNull(cloneTail);
        }else {
          var thisCast=(LongDblLnkSeq)seq;
          var thisHead=thisCast.head;
          var thisTail=thisCast.tail;
          var clonePrev=cloneHead.prev;
          Assertions.assertNull(clonePrev);
          for(int i=0;i < size;++i){
            Assertions.assertNotSame(cloneHead,thisHead);
            Assertions.assertEquals(cloneHead.val,thisHead.val);
            Assertions.assertSame(clonePrev,cloneHead.prev);
            clonePrev=cloneHead;
            cloneHead=cloneHead.next;
            thisHead=thisHead.next;
          }
          Assertions.assertSame(cloneTail,clonePrev);
          Assertions.assertNotSame(cloneTail,thisTail);
          Assertions.assertNull(cloneHead);
        }
        break;
      }
      case REF:{
        @SuppressWarnings("unchecked") var cloneCast=(RefDblLnkSeq<E>)clone;
        Assertions.assertEquals(checkedType.checked,cloneCast instanceof RefDblLnkSeq.CheckedList);
        if(checkedType.checked) {
          Assertions.assertEquals(0,((RefDblLnkSeq.CheckedList<E>)cloneCast).modCount);
        }else {
          Assertions.assertTrue(cloneCast instanceof RefDblLnkSeq.UncheckedList);
        }
        var cloneHead=cloneCast.head;
        var cloneTail=cloneCast.tail;
        if(size==0) {
          Assertions.assertNull(cloneHead);
          Assertions.assertNull(cloneTail);
        }else {
          @SuppressWarnings("unchecked") var thisCast=(RefDblLnkSeq<E>)seq;
          var thisHead=thisCast.head;
          var thisTail=thisCast.tail;
          var clonePrev=cloneHead.prev;
          Assertions.assertNull(clonePrev);
          for(int i=0;i < size;++i){
            Assertions.assertNotSame(cloneHead,thisHead);
            Assertions.assertSame(cloneHead.val,thisHead.val);
            Assertions.assertSame(clonePrev,cloneHead.prev);
            clonePrev=cloneHead;
            cloneHead=cloneHead.next;
            thisHead=thisHead.next;
          }
          Assertions.assertSame(cloneTail,clonePrev);
          Assertions.assertNotSame(cloneTail,thisTail);
          Assertions.assertNull(cloneHead);
        }
        break;
      }
      case SHORT:{
        var cloneCast=(ShortDblLnkSeq)clone;
        Assertions.assertEquals(checkedType.checked,cloneCast instanceof ShortDblLnkSeq.CheckedList);
        if(checkedType.checked) {
          Assertions.assertEquals(0,((ShortDblLnkSeq.CheckedList)cloneCast).modCount);
        }else {
          Assertions.assertTrue(cloneCast instanceof ShortDblLnkSeq.UncheckedList);
        }
        var cloneHead=cloneCast.head;
        var cloneTail=cloneCast.tail;
        if(size==0) {
          Assertions.assertNull(cloneHead);
          Assertions.assertNull(cloneTail);
        }else {
          var thisCast=(ShortDblLnkSeq)seq;
          var thisHead=thisCast.head;
          var thisTail=thisCast.tail;
          var clonePrev=cloneHead.prev;
          Assertions.assertNull(clonePrev);
          for(int i=0;i < size;++i){
            Assertions.assertNotSame(cloneHead,thisHead);
            Assertions.assertEquals(cloneHead.val,thisHead.val);
            Assertions.assertSame(clonePrev,cloneHead.prev);
            clonePrev=cloneHead;
            cloneHead=cloneHead.next;
            thisHead=thisHead.next;
          }
          Assertions.assertSame(cloneTail,clonePrev);
          Assertions.assertNotSame(cloneTail,thisTail);
          Assertions.assertNull(cloneHead);
        }
        break;
      }
      default:{
        throw dataType.invalid();
      }
      }
     
    }
    @Override public void verifyCollectionState(boolean refIsSame){
      final int expectedSize;
      Assertions.assertEquals(expectedSize=this.expectedSize,((AbstractSeq<?>)seq).size);
      switch(dataType){
      case BOOLEAN:{
        final var cast=(BooleanDblLnkSeq)seq;
        final var expectedArr=(boolean[])this.expectedArr;
        if(checkedType.checked){
          Assertions.assertEquals(expectedModCount,((BooleanDblLnkSeq.CheckedList)cast).modCount);
        }
        var headNode=cast.head;
        final var tailNode=cast.tail;
        if(expectedSize == 0){
          Assertions.assertNull(headNode);
          Assertions.assertNull(tailNode);
        }else{
          var prevNode=headNode.prev;
          Assertions.assertNull(prevNode);
          for(int i=0;i < expectedSize;++i){
            Assertions.assertEquals(headNode.val,expectedArr[i]);
            Assertions.assertSame(prevNode,headNode.prev);
            prevNode=headNode;
            headNode=headNode.next;
          }
          Assertions.assertSame(tailNode,prevNode);
          Assertions.assertNull(headNode);
        }
        break;
      }
      case BYTE:{
        final var cast=(ByteDblLnkSeq)seq;
        final var expectedArr=(byte[])this.expectedArr;
        if(checkedType.checked){
          Assertions.assertEquals(expectedModCount,((ByteDblLnkSeq.CheckedList)cast).modCount);
        }
        var headNode=cast.head;
        final var tailNode=cast.tail;
        if(expectedSize == 0){
          Assertions.assertNull(headNode);
          Assertions.assertNull(tailNode);
        }else{
          var prevNode=headNode.prev;
          Assertions.assertNull(prevNode);
          for(int i=0;i < expectedSize;++i){
            Assertions.assertEquals(headNode.val,expectedArr[i]);
            Assertions.assertSame(prevNode,headNode.prev);
            prevNode=headNode;
            headNode=headNode.next;
          }
          Assertions.assertSame(tailNode,prevNode);
          Assertions.assertNull(headNode);
        }
        break;
      }
      case CHAR:{
        final var cast=(CharDblLnkSeq)seq;
        final var expectedArr=(char[])this.expectedArr;
        if(checkedType.checked){
          Assertions.assertEquals(expectedModCount,((CharDblLnkSeq.CheckedList)cast).modCount);
        }
        var headNode=cast.head;
        final var tailNode=cast.tail;
        if(expectedSize == 0){
          Assertions.assertNull(headNode);
          Assertions.assertNull(tailNode);
        }else{
          var prevNode=headNode.prev;
          Assertions.assertNull(prevNode);
          for(int i=0;i < expectedSize;++i){
            Assertions.assertEquals(headNode.val,expectedArr[i]);
            Assertions.assertSame(prevNode,headNode.prev);
            prevNode=headNode;
            headNode=headNode.next;
          }
          Assertions.assertSame(tailNode,prevNode);
          Assertions.assertNull(headNode);
        }
        break;
      }
      case DOUBLE:{
        final var cast=(DoubleDblLnkSeq)seq;
        final var expectedArr=(double[])this.expectedArr;
        if(checkedType.checked){
          Assertions.assertEquals(expectedModCount,((DoubleDblLnkSeq.CheckedList)cast).modCount);
        }
        var headNode=cast.head;
        final var tailNode=cast.tail;
        if(expectedSize == 0){
          Assertions.assertNull(headNode);
          Assertions.assertNull(tailNode);
        }else{
          var prevNode=headNode.prev;
          Assertions.assertNull(prevNode);
          for(int i=0;i < expectedSize;++i){
            Assertions.assertEquals(headNode.val,expectedArr[i]);
            Assertions.assertSame(prevNode,headNode.prev);
            prevNode=headNode;
            headNode=headNode.next;
          }
          Assertions.assertSame(tailNode,prevNode);
          Assertions.assertNull(headNode);
        }
        break;
      }
      case FLOAT:{
        final var cast=(FloatDblLnkSeq)seq;
        final var expectedArr=(float[])this.expectedArr;
        if(checkedType.checked){
          Assertions.assertEquals(expectedModCount,((FloatDblLnkSeq.CheckedList)cast).modCount);
        }
        var headNode=cast.head;
        final var tailNode=cast.tail;
        if(expectedSize == 0){
          Assertions.assertNull(headNode);
          Assertions.assertNull(tailNode);
        }else{
          var prevNode=headNode.prev;
          Assertions.assertNull(prevNode);
          for(int i=0;i < expectedSize;++i){
            Assertions.assertEquals(headNode.val,expectedArr[i]);
            Assertions.assertSame(prevNode,headNode.prev);
            prevNode=headNode;
            headNode=headNode.next;
          }
          Assertions.assertSame(tailNode,prevNode);
          Assertions.assertNull(headNode);
        }
        break;
      }
      case INT:{
        final var cast=(IntDblLnkSeq)seq;
        final var expectedArr=(int[])this.expectedArr;
        if(checkedType.checked){
          Assertions.assertEquals(expectedModCount,((IntDblLnkSeq.CheckedList)cast).modCount);
        }
        var headNode=cast.head;
        final var tailNode=cast.tail;
        if(expectedSize == 0){
          Assertions.assertNull(headNode);
          Assertions.assertNull(tailNode);
        }else{
          var prevNode=headNode.prev;
          Assertions.assertNull(prevNode);
          for(int i=0;i < expectedSize;++i){
            Assertions.assertEquals(headNode.val,expectedArr[i]);
            Assertions.assertSame(prevNode,headNode.prev);
            prevNode=headNode;
            headNode=headNode.next;
          }
          Assertions.assertSame(tailNode,prevNode);
          Assertions.assertNull(headNode);
        }
        break;
      }
      case LONG:{
        final var cast=(LongDblLnkSeq)seq;
        final var expectedArr=(long[])this.expectedArr;
        if(checkedType.checked){
          Assertions.assertEquals(expectedModCount,((LongDblLnkSeq.CheckedList)cast).modCount);
        }
        var headNode=cast.head;
        final var tailNode=cast.tail;
        if(expectedSize == 0){
          Assertions.assertNull(headNode);
          Assertions.assertNull(tailNode);
        }else{
          var prevNode=headNode.prev;
          Assertions.assertNull(prevNode);
          for(int i=0;i < expectedSize;++i){
            Assertions.assertEquals(headNode.val,expectedArr[i]);
            Assertions.assertSame(prevNode,headNode.prev);
            prevNode=headNode;
            headNode=headNode.next;
          }
          Assertions.assertSame(tailNode,prevNode);
          Assertions.assertNull(headNode);
        }
        break;
      }
      case REF:{
        @SuppressWarnings("unchecked") final var cast=(RefDblLnkSeq<E>)seq;
        final var expectedArr=(Object[])this.expectedArr;
        if(checkedType.checked){
          Assertions.assertEquals(expectedModCount,((RefDblLnkSeq.CheckedList<E>)cast).modCount);
        }
        var headNode=cast.head;
        final var tailNode=cast.tail;
        if(expectedSize == 0){
          Assertions.assertNull(headNode);
          Assertions.assertNull(tailNode);
        }else{
          var prevNode=headNode.prev;
          Assertions.assertNull(prevNode);
          if(refIsSame){
            for(int i=0;i < expectedSize;++i){
              Assertions.assertSame(headNode.val,expectedArr[i]);
              Assertions.assertSame(prevNode,headNode.prev);
              prevNode=headNode;
              headNode=headNode.next;
            }
          }else{
            for(int i=0;i < expectedSize;++i){
              Assertions.assertEquals(headNode.val,expectedArr[i]);
              Assertions.assertSame(prevNode,headNode.prev);
              prevNode=headNode;
              headNode=headNode.next;
            }
          }
          Assertions.assertSame(tailNode,prevNode);
          Assertions.assertNull(headNode);
        }
        break;
      }
      case SHORT:{
        final var cast=(ShortDblLnkSeq)seq;
        final var expectedArr=(short[])this.expectedArr;
        if(checkedType.checked){
          Assertions.assertEquals(expectedModCount,((ShortDblLnkSeq.CheckedList)cast).modCount);
        }
        var headNode=cast.head;
        final var tailNode=cast.tail;
        if(expectedSize == 0){
          Assertions.assertNull(headNode);
          Assertions.assertNull(tailNode);
        }else{
          var prevNode=headNode.prev;
          Assertions.assertNull(prevNode);
          for(int i=0;i < expectedSize;++i){
            Assertions.assertEquals(headNode.val,expectedArr[i]);
            Assertions.assertSame(prevNode,headNode.prev);
            prevNode=headNode;
            headNode=headNode.next;
          }
          Assertions.assertSame(tailNode,prevNode);
          Assertions.assertNull(headNode);
        }
        break;
      }
      default:
        throw dataType.invalid();
      }
    }
    @SuppressWarnings("unchecked") @Override LSTDEQ initSeq(){
      switch(dataType){
      case BOOLEAN:
        if(checkedType.checked){
          return (LSTDEQ)new BooleanDblLnkSeq.CheckedList();
        }else{
          return (LSTDEQ)new BooleanDblLnkSeq.UncheckedList();
        }
      case BYTE:
        if(checkedType.checked){
          return (LSTDEQ)new ByteDblLnkSeq.CheckedList();
        }else{
          return (LSTDEQ)new ByteDblLnkSeq.UncheckedList();
        }
      case CHAR:
        if(checkedType.checked){
          return (LSTDEQ)new CharDblLnkSeq.CheckedList();
        }else{
          return (LSTDEQ)new CharDblLnkSeq.UncheckedList();
        }
      case SHORT:
        if(checkedType.checked){
          return (LSTDEQ)new ShortDblLnkSeq.CheckedList();
        }else{
          return (LSTDEQ)new ShortDblLnkSeq.UncheckedList();
        }
      case INT:
        if(checkedType.checked){
          return (LSTDEQ)new IntDblLnkSeq.CheckedList();
        }else{
          return (LSTDEQ)new IntDblLnkSeq.UncheckedList();
        }
      case LONG:
        if(checkedType.checked){
          return (LSTDEQ)new LongDblLnkSeq.CheckedList();
        }else{
          return (LSTDEQ)new LongDblLnkSeq.UncheckedList();
        }
      case FLOAT:
        if(checkedType.checked){
          return (LSTDEQ)new FloatDblLnkSeq.CheckedList();
        }else{
          return (LSTDEQ)new FloatDblLnkSeq.UncheckedList();
        }
      case DOUBLE:
        if(checkedType.checked){
          return (LSTDEQ)new DoubleDblLnkSeq.CheckedList();
        }else{
          return (LSTDEQ)new DoubleDblLnkSeq.UncheckedList();
        }
      case REF:
        if(checkedType.checked){
          return (LSTDEQ)new RefDblLnkSeq.CheckedList<>();
        }else{
          return (LSTDEQ)new RefDblLnkSeq.UncheckedList<>();
        }
      default:
        throw dataType.invalid();
      }
    }
    @Override LSTDEQ initSeq(int initCap){
      return initSeq();
    }
    @Override void updateModCount(){
      switch(dataType){
      case BOOLEAN:
        expectedModCount=((BooleanDblLnkSeq.CheckedList)seq).modCount;
        break;
      case BYTE:
        expectedModCount=((ByteDblLnkSeq.CheckedList)seq).modCount;
        break;
      case CHAR:
        expectedModCount=((CharDblLnkSeq.CheckedList)seq).modCount;
        break;
      case DOUBLE:
        expectedModCount=((DoubleDblLnkSeq.CheckedList)seq).modCount;
        break;
      case FLOAT:
        expectedModCount=((FloatDblLnkSeq.CheckedList)seq).modCount;
        break;
      case INT:
        expectedModCount=((IntDblLnkSeq.CheckedList)seq).modCount;
        break;
      case LONG:
        expectedModCount=((LongDblLnkSeq.CheckedList)seq).modCount;
        break;
      case REF:
        expectedModCount=((RefDblLnkSeq.CheckedList<?>)seq).modCount;
        break;
      case SHORT:
        expectedModCount=((ShortDblLnkSeq.CheckedList)seq).modCount;
        break;
      default:
        throw dataType.invalid();
      }
    }
  }
  private static final double[] RANDOM_THRESHOLDS=new double[]{0.01,0.05,0.10,0.25,0.50,0.75,0.90,0.95,0.99};
  private static final double[] NON_RANDOM_THRESHOLD=new double[]{0.5};
  private static final int[] NON_THROWING_REMOVE_AT_POSITIONS=new int[]{-1,0,1,2,3};
  private static final int[] THROWING_REMOVE_AT_POSITIONS=new int[]{-1};
  private static final double[] POSITIONS=new double[]{-1,0,0.5,1.0};
  private static final SequenceInitParams[] ALL_STRUCT_INIT_PARAMS;
  private static final SequenceInitParams[] ROOT_STRUCT_INIT_PARAMS;
  private static final int[] SHORT_SIZES=new int[]{0,100};
  private static final int[] SIZES=new int[]{0,1,2,3,4,5,10,20,30,40,50,60,70,80,90,100};
  static {
    Stream.Builder<SequenceInitParams> allStructBuilder=Stream.builder();
    Stream.Builder<SequenceInitParams> rootStructBuilder=Stream.builder();
    for(var checkedType:CheckedType.values()) {
      for(var collectionType:DataType.values()) {
          var rootParams=new SequenceInitParams(StructType.DblLnkList,collectionType,checkedType,OmniArray.OfInt.DEFAULT_ARR,OmniArray.OfInt.DEFAULT_ARR);
          allStructBuilder.accept(rootParams);
          rootStructBuilder.accept(rootParams);
      }
    }
    ROOT_STRUCT_INIT_PARAMS=rootStructBuilder.build().toArray(SequenceInitParams[]::new);
//    for(int pre0=0;pre0<=4;pre0+=2) {
//      for(int pre1=0;pre1<=4;pre1+=2) {
//        for(int pre2=0;pre2<=4;pre2+=2) {
//          final var preAllocs=new int[] {pre0,pre1,pre2};
//          for(int post0=0;post0<=4;post0+=2) {
//            for(int post1=0;post1<=4;post1+=2) {
//              for(int post2=0;post2<=4;post2+=2) {
//                final var postAllocs=new int[] {post0,post1,post2};
//                for(var checkedType:CheckedType.values()) {
//                  for(var collectionType:DataType.values()) {
//                    allStructBuilder.accept(new SequenceInitParams(StructType.DblLnkSubList,collectionType,checkedType,preAllocs,postAllocs));
//                  }
//                }
//              }
//            }
//          }
//        }
//      }
//    }
    ALL_STRUCT_INIT_PARAMS=allStructBuilder.build().toArray(SequenceInitParams[]::new);
  }
  
  private static MonitoredList<?> getMonitoredList(SequenceInitParams initParams,int initialCapacity){
    var rootMonitor=new DblLnkSeqMonitor<>(initParams,getInitCapacity(initialCapacity,initParams.preAllocs,initParams.postAllocs));
    if(initParams.structType==StructType.DblLnkSubList) {
      //TODO
      throw new UnsupportedOperationException("Still need to implement sublist monitor");
    }
    return rootMonitor;
  }
  private static int getInitCapacity(int initCapacity,int[] preAllocs,int[] postAllocs) {
    for(int i=preAllocs.length;--i>=0;) {
      initCapacity+=preAllocs[i];
    }
    for(int i=postAllocs.length;--i>=0;) {
      initCapacity+=postAllocs[i];
    }
    return initCapacity;
  }
  @org.junit.jupiter.api.AfterEach
  public void verifyAllExecuted(){
      int numTestsRemaining;
      if((numTestsRemaining=TestExecutorService.getNumRemainingTasks()) != 0){
          System.err.println("Warning: there were " + numTestsRemaining + " tests that were not completed");
      }
      TestExecutorService.reset();
  }
  @Test
  public void testcontains_val(){
      final QueryTest<MonitoredSequence<?>> test=(monitor,queryVal,inputType,castType,modification,monitoredObjectGen,
              position,seqSize)->{
          if(monitoredObjectGen == null){
              Assertions.assertEquals(position >= 0,monitor.verifyContains(queryVal,inputType,castType,modification));
          }else{
              monitor.verifyThrowingContains(monitoredObjectGen);
          }
      };
      test.runAllTests("DblLnkSeqTest.testcontains_val",2);
  }
  @Test
  public void testforEach_Consumer(){
      final MonitoredFunctionTest<MonitoredSequence<?>> test=(monitor,functionGen,functionCallType,illegalMod,
              randSeed)->{
          if(illegalMod.expectedException == null){
              if(functionGen.expectedException == null || monitor.isEmpty()){
                  monitor.verifyForEach(functionGen,functionCallType,randSeed);
              }else{
                  Assertions.assertThrows(functionGen.expectedException,
                          ()->monitor.verifyForEach(functionGen,functionCallType,randSeed));
              }
          }else{
              monitor.illegalMod(illegalMod);
              Assertions.assertThrows(illegalMod.expectedException,
                      ()->monitor.verifyForEach(functionGen,functionCallType,randSeed));
          }
      };
      test.runAllTests("DblLnkSeqTest.testforEach_Consumer",100,EnumSet.allOf(FunctionCallType.class));
  }
  @Test
  public void testlastIndexOf_val(){
      final QueryTest<MonitoredList<?>> test=(monitor,queryVal,inputType,castType,modification,monitoredObjectGen,
              position,seqSize)->{
          if(monitoredObjectGen == null){
              int expectedIndex;
              if(position >= 0){
                  expectedIndex=(int)Math.round(position * seqSize);
              }else{
                  expectedIndex=-1;
              }
              Assertions.assertEquals(expectedIndex,
                      monitor.verifyLastIndexOf(queryVal,inputType,castType,modification));
          }else{
              monitor.verifyThrowingLastIndexOf(monitoredObjectGen);
          }
      };
      test.runAllTests("DblLnkSeqTest.testlastIndexOf_val",0);
  }
  @Test
  public void testremoveVal_val(){
      final QueryTest<MonitoredSequence<?>> test=new QueryTest<>(){
          @Override
          public void callAndVerifyResult(MonitoredSequence<?> monitor,QueryVal queryVal,DataType inputType,
                  QueryCastType castType,QueryValModification modification,MonitoredObjectGen monitoredObjectGen,
                  double position,int seqSize){
              if(monitoredObjectGen == null){
                  Assertions.assertEquals(position >= 0,
                          monitor.verifyRemoveVal(queryVal,inputType,castType,modification));
              }else{
                  monitor.verifyThrowingRemoveVal(monitoredObjectGen);
              }
          }
          @Override
          public boolean cmeFilter(IllegalModification illegalMod,DataType inputType,DataType collectionType,
                  QueryVal queryVal,QueryVal.QueryValModification modification,QueryCastType castType){
              return illegalMod.expectedException == null || collectionType != DataType.REF
                      && queryVal == QueryVal.Null && castType != QueryCastType.ToObject;
          }
      };
      test.runAllTests("DblLnkSeqTest.testremoveVal_val",2);
  }
  @Test
  public void testreplaceAll_UnaryOperator(){
      final MonitoredFunctionTest<MonitoredList<?>> test=(monitor,functionGen,functionCallType,illegalMod,randSeed)->{
          if(illegalMod.expectedException == null){
              if(functionGen.expectedException == null || monitor.isEmpty()){
                  monitor.verifyReplaceAll(functionGen,functionCallType,randSeed);
              }else{
                  Assertions.assertThrows(functionGen.expectedException,
                          ()->monitor.verifyReplaceAll(functionGen,functionCallType,randSeed));
              }
          }else{
              monitor.illegalMod(illegalMod);
              Assertions.assertThrows(illegalMod.expectedException,
                      ()->monitor.verifyReplaceAll(functionGen,functionCallType,randSeed));
          }
      };
      test.runAllTests("DblLnkSeqTest.testreplaceAll_UnaryOperator",100,EnumSet.allOf(FunctionCallType.class));
  }
  @Tag("Search")
  @Test
  public void testsearch_val(){
      final QueryTest<MonitoredStack<?>> test=(monitor,queryVal,inputType,castType,modification,monitoredObjectGen,
              position,seqSize)->{
          if(monitoredObjectGen == null){
              int expectedIndex;
              if(position >= 0){
                  expectedIndex=1+(int)Math.round(position * seqSize);
              }else{
                  expectedIndex=-1;
              }
              Assertions.assertEquals(expectedIndex,monitor.verifySearch(queryVal,inputType,castType,modification));
          }else{
              monitor.verifyThrowingSearch(monitoredObjectGen);
          }
      };
      test.runAllTests("DblLnkSeqTest.testsearch",1);
  }
  @Test
  public void testindexOf_val(){
      final QueryTest<MonitoredList<?>> test=(monitor,queryVal,inputType,castType,modification,monitoredObjectGen,
              position,seqSize)->{
          if(monitoredObjectGen == null){
              int expectedIndex;
              if(position >= 0){
                  expectedIndex=(int)Math.round(position * seqSize);
              }else{
                  expectedIndex=-1;
              }
              Assertions.assertEquals(expectedIndex,monitor.verifyIndexOf(queryVal,inputType,castType,modification));
          }else{
              monitor.verifyThrowingIndexOf(monitoredObjectGen);
          }
      };
      test.runAllTests("DblLnkSeqTest.testindexOf_val",0);
  }
  @Test
  public void testtoArray_IntFunction(){
      final MonitoredFunctionTest<MonitoredSequence<?>> test=(monitor,functionGen,functionCallType,illegalMod,
              randSeed)->{
          if(illegalMod.expectedException == null){
              if(functionGen.expectedException == null){
                  monitor.verifyToArray(functionGen);
              }else{
                  Assertions.assertThrows(functionGen.expectedException,()->monitor.verifyToArray(functionGen));
              }
          }else{
              monitor.illegalMod(illegalMod);
              Assertions.assertThrows(illegalMod.expectedException,()->monitor.verifyToArray(functionGen));
          }
      };
      test.runAllTests("DblLnkSeqTest.testtoArray_IntFunction",0,EnumSet.of(FunctionCallType.Unboxed));
  }
  @Test
  public void testisEmpty_void(){
      final BasicTest test=MonitoredSequence::verifyIsEmpty;
      test.runAllTests("DblLnkSeqTest.testisEmpty_void");
  }
  @Test
  public void testtoString_void(){
      final ToStringAndHashCodeTest test=new ToStringAndHashCodeTest(){
          @Override
          public void callRaw(OmniCollection<?> seq){
              seq.toString();
          }
          @Override
          public void callVerify(MonitoredSequence<?> monitor){
              monitor.verifyToString();
          }
      };
      test.runAllTests("DblLnkSeqTest.testtoString_void");
  }
  @Test
  public void testclear_void(){
      final BasicTest test=MonitoredSequence::verifyClear;
      test.runAllTests("DblLnkSeqTest.testclear_void");
  }
  @Test
  public void testclone_void(){
      final BasicTest test=MonitoredSequence::verifyClone;
      test.runAllTests("DblLnkSeqTest.testclone_void");
  }
  @Test
  public void testhashCode_void(){
      final ToStringAndHashCodeTest test=new ToStringAndHashCodeTest(){
          @Override
          public void callRaw(OmniCollection<?> seq){
              seq.hashCode();
          }
          @Override
          public void callVerify(MonitoredSequence<?> monitor){
              monitor.verifyHashCode();
          }
      };
      test.runAllTests("DblLnkSeqTest.testhashCode_void");
  }
  @Test
  public void testConstructor_void() {
    for(var checkedType:CheckedType.values()) {
      for(var collectionType:DataType.values()) {
        TestExecutorService.submitTest(()->new DblLnkSeqMonitor<>(checkedType,collectionType).verifyCollectionState());
      }
    }
    TestExecutorService.completeAllTests("DblLnkSeqTest.testConstructor_void");
  }
  @Test
  public void testadd_val() {
    for(var initParams:ALL_STRUCT_INIT_PARAMS) {
      for(var illegalMod:initParams.validPreMods) {
          for(var inputType:initParams.collectionType.mayBeAddedTo()) {
              for(var functionCallType:inputType.validFunctionCalls) {
                  if(illegalMod.expectedException==null) {
                      TestExecutorService.submitTest(()->{
                        var monitor=getMonitoredList(initParams,100);
                        for(int i=0;i<100;++i) {
                          monitor.verifyAdd(inputType.convertValUnchecked(i),inputType,functionCallType);
                        }
                      });
                    }else {
                      for(int tmpInitSize=0;tmpInitSize<100;++tmpInitSize) {
                        final int initSize=tmpInitSize;
                        TestExecutorService.submitTest(()->{
                            var monitor=SequenceInitialization.Ascending.initialize(getMonitoredList(initParams,initSize),initSize,0,0);
                            monitor.illegalMod(illegalMod);
                            Assertions.assertThrows(illegalMod.expectedException,()->monitor.verifyAdd(inputType.convertValUnchecked(initSize),inputType,functionCallType));
                        });
                      }
                    }
                  }
          }
        
      }
    }
    TestExecutorService.completeAllTests("DblLnkSeqTest.testadd_val");
  }
  @Test
  public void testadd_intval() {
      for(var initParams:ALL_STRUCT_INIT_PARAMS) {
          for(final var position:POSITIONS){
              if(position >= 0 || initParams.checkedType.checked){
                  for(final var illegalMod:initParams.validPreMods){
                      for(final var inputType:initParams.collectionType.mayBeAddedTo()){
                          for(final var functionCallType:inputType.validFunctionCalls){
                              
                                  TestExecutorService.submitTest(()->{
                                      if(illegalMod.expectedException == null){
                                          final var monitor=getMonitoredList(initParams,100);
                                          if(position < 0){
                                              for(int i=0;i < 100;++i){
                                                  final Object inputVal=inputType.convertValUnchecked(i);
                                                  final int finalI=i;
                                                  Assertions.assertThrows(IndexOutOfBoundsException.class,()->monitor
                                                          .verifyAdd(-1,inputVal,inputType,functionCallType));
                                                  Assertions.assertThrows(IndexOutOfBoundsException.class,()->monitor
                                                          .verifyAdd(finalI + 1,inputVal,inputType,functionCallType));
                                                  monitor.add(i);
                                              }
                                          }else{
                                              for(int i=0;i < 100;++i){
                                                  monitor.verifyAdd((int)(i * position),
                                                          inputType.convertValUnchecked(i),inputType,
                                                          functionCallType);
                                              }
                                          }
                                      }else{
                                          {
                                              final var monitor=SequenceInitialization.Ascending
                                                      .initialize(getMonitoredList(initParams,10),10,0);
                                              monitor.illegalMod(illegalMod);
                                              final Object inputVal=inputType.convertValUnchecked(0);
                                              Assertions.assertThrows(illegalMod.expectedException,
                                                      ()->monitor.verifyAdd(-1,inputVal,inputType,functionCallType));
                                              Assertions.assertThrows(illegalMod.expectedException,
                                                      ()->monitor.verifyAdd(1,inputVal,inputType,functionCallType));
                                              Assertions.assertThrows(illegalMod.expectedException,
                                                      ()->monitor.verifyAdd(0,inputVal,inputType,functionCallType));
                                          }
                                          {
                                              final var monitor=getMonitoredList(initParams,0);
                                              monitor.illegalMod(illegalMod);
                                              final Object inputVal=inputType.convertValUnchecked(0);
                                              Assertions.assertThrows(illegalMod.expectedException,
                                                      ()->monitor.verifyAdd(-1,inputVal,inputType,functionCallType));
                                              Assertions.assertThrows(illegalMod.expectedException,
                                                      ()->monitor.verifyAdd(1,inputVal,inputType,functionCallType));
                                              Assertions.assertThrows(illegalMod.expectedException,
                                                      ()->monitor.verifyAdd(0,inputVal,inputType,functionCallType));
                                          }
                                      }
                                  });
                              
                          }
                      }
                  }
              }
          }
      }
      TestExecutorService.completeAllTests("DblLnkSeqTest.testadd_intval");
  }
  @Test
  public void testget_int(){
      for(final var initParams:ALL_STRUCT_INIT_PARAMS){
          for(final var illegalMod:initParams.validPreMods){
              for(final var size:SHORT_SIZES){
                  TestExecutorService.submitTest(()->{
                      final var monitor=SequenceInitialization.Ascending.initialize(getMonitoredList(initParams,size),
                              size,0);
                      monitor.illegalMod(illegalMod);
                      if(illegalMod.expectedException == null){
                          for(final var outputType:initParams.collectionType.validOutputTypes()){
                              if(initParams.checkedType.checked){
                                  Assertions.assertThrows(IndexOutOfBoundsException.class,
                                          ()->monitor.verifyGet(-1,outputType));
                                  Assertions.assertThrows(IndexOutOfBoundsException.class,
                                          ()->monitor.verifyGet(size,outputType));
                              }
                              for(int index=0;index < size;++index){
                                  monitor.verifyGet(index,outputType);
                              }
                          }
                      }else{
                          for(final var outputType:initParams.collectionType.validOutputTypes()){
                              for(int tmpIndex=-1;tmpIndex <= size;++tmpIndex){
                                  final int index=tmpIndex;
                                  Assertions.assertThrows(illegalMod.expectedException,
                                          ()->monitor.verifyGet(index,outputType));
                              }
                          }
                      }
                  });
              }
          }
      }
      TestExecutorService.completeAllTests("DblLnkSeqTest.testget_int");
  }
  @Test
  public void testiterator_void(){
      for(final var initParams:ALL_STRUCT_INIT_PARAMS){
          for(final var illegalMod:initParams.validPreMods){
              for(final var size:SIZES){
                  TestExecutorService.submitTest(()->{
                      final var monitor=SequenceInitialization.Ascending
                              .initialize(getMonitoredList(initParams,size),size,0);
                      try{
                          if(illegalMod.expectedException == null){
                              monitor.getMonitoredIterator().verifyIteratorState();
                          }else{
                              monitor.illegalMod(illegalMod);
                              Assertions.assertThrows(illegalMod.expectedException,monitor::getMonitoredIterator);
                          }
                      }finally{
                          monitor.verifyCollectionState();
                      }
                  });
              }
          }
      }
      TestExecutorService.completeAllTests("DblLnkSeqTest.testiterator_void");
  }
  @Tag("ForEachRemaining")
  @Test
  public void testItrforEachRemaining_Consumer(){
      for(final int size:SIZES){
          int prevNumToIterate=-1;
          for(final var position:POSITIONS){
              int numToIterate;
              if(position >= 0 && (numToIterate=(int)(position * size)) != prevNumToIterate){
                  prevNumToIterate=numToIterate;
                  final int numLeft=size - numToIterate;
                  for(final var initParams:ALL_STRUCT_INIT_PARAMS){
                      for(final var functionCallType:initParams.collectionType.validFunctionCalls){
                          for(final var itrType:initParams.structType.validItrTypes){
                              for(final var illegalMod:itrType.validPreMods){
                                  if(illegalMod.expectedException == null || initParams.checkedType.checked){
                                      for(final var functionGen:itrType.validMonitoredFunctionGens){
                                          if(initParams.checkedType.checked || size == 0
                                                  || functionGen.expectedException == null){
                                              final long randSeedBound=!functionCallType.boxed && numLeft > 1
                                                      && functionGen.randomized&&illegalMod.expectedException==null?100:0;
                                              for(long tmpRandSeed=0;tmpRandSeed <= randSeedBound;++tmpRandSeed){
                                                  final long randSeed=tmpRandSeed;
                                                  TestExecutorService.submitTest(()->{
                                                      final var seqMonitor=SequenceInitialization.Ascending
                                                              .initialize(getMonitoredList(initParams,size),size,
                                                                      0);
                                                      final var itrMonitor=seqMonitor
                                                              .getMonitoredIterator(numToIterate,itrType);
                                                      itrMonitor.illegalMod(illegalMod);
                                                      if(illegalMod.expectedException == null || numLeft == 0){
                                                          if(functionGen.expectedException == null || numLeft == 0){
                                                              itrMonitor.verifyForEachRemaining(functionGen,
                                                                      functionCallType,randSeed);
                                                          }else{
                                                              Assertions
                                                                      .assertThrows(functionGen.expectedException,
                                                                              ()->itrMonitor.verifyForEachRemaining(
                                                                                      functionGen,functionCallType,
                                                                                      randSeed));
                                                          }
                                                      }else{
                                                          Assertions.assertThrows(illegalMod.expectedException,
                                                                  ()->itrMonitor.verifyForEachRemaining(functionGen,
                                                                          functionCallType,randSeed));
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
          }
      }
      TestExecutorService.completeAllTests("DblLnkSeqTest.testItrforEachRemaining_Consumer");
  }
  @Test
  public void testItrhasNext_void(){
      for(final var initParams:ALL_STRUCT_INIT_PARAMS){
          for(final var itrType:initParams.structType.validItrTypes){
              for(final int size:SHORT_SIZES){
                  TestExecutorService.submitTest(()->{
                      final var seqMonitor=SequenceInitialization.Ascending
                              .initialize(getMonitoredList(initParams,size),size,0);
                      final var itrMonitor=seqMonitor.getMonitoredIterator(itrType);
                      while(itrMonitor.verifyHasNext()){
                          itrMonitor.iterateForward();
                      }
                  });
              }
          }
      }
      TestExecutorService.completeAllTests("DblLnkSeqTest.testItrhasNext_void");
  }
  @Test
  public void testItrnext_void(){
      for(final var initParams:ALL_STRUCT_INIT_PARAMS){
          for(final var itrType:initParams.structType.validItrTypes){
              for(final var illegalMod:itrType.validPreMods){
                  if(illegalMod.expectedException == null || initParams.checkedType.checked){
                      for(final var outputType:initParams.collectionType.validOutputTypes()){
                          for(final var size:SHORT_SIZES){
                              if(size > 0 || initParams.checkedType.checked){
                                  TestExecutorService.submitTest(()->{
                                      final var seqMonitor=SequenceInitialization.Ascending
                                              .initialize(getMonitoredList(initParams,size),size,0);
                                      final var itrMonitor=seqMonitor.getMonitoredIterator(itrType);
                                      if(illegalMod.expectedException == null){
                                          while(itrMonitor.hasNext()){
                                              itrMonitor.verifyNext(outputType);
                                          }
                                          if(initParams.checkedType.checked){
                                              Assertions.assertThrows(NoSuchElementException.class,
                                                      ()->itrMonitor.verifyNext(outputType));
                                          }
                                      }else{
                                          itrMonitor.illegalMod(illegalMod);
                                          Assertions.assertThrows(illegalMod.expectedException,
                                                  ()->itrMonitor.verifyNext(outputType));
                                      }
                                  });
                              }
                          }
                      }
                  }
              }
          }
      }
      TestExecutorService.completeAllTests("DblLnkSeqTest.testItrnext_void");
  }
  @Test
  public void testlistIterator_int(){
      for(final var initParams:ALL_STRUCT_INIT_PARAMS){
          for(final var size:SIZES){
              final int inc=Math.max(1,size / 10);
              if(initParams.checkedType.checked || size > 0){
                  for(final var illegalMod:initParams.validPreMods){
                      TestExecutorService.submitTest(()->{
                          final var monitor=SequenceInitialization.Ascending
                                  .initialize(getMonitoredList(initParams,size),size,0);
                          if(illegalMod.expectedException == null){
                              for(int index=-inc,bound=size + inc;index <= bound;index+=inc){
                                  if(index < 0 || index > size){
                                      if(initParams.checkedType.checked){
                                          final int finalIndex=index;
                                          Assertions.assertThrows(IndexOutOfBoundsException.class,()->{
                                              try{
                                                  monitor.getMonitoredListIterator(finalIndex);
                                              }finally{
                                                  monitor.verifyCollectionState();
                                              }
                                          });
                                      }
                                  }else{
                                      final var itrMonitor=monitor.getMonitoredListIterator(index);
                                      monitor.verifyCollectionState();
                                      itrMonitor.verifyIteratorState();
                                  }
                              }
                          }
                      });
                  }
              }
          }
      }
      TestExecutorService.completeAllTests("DblLnkSeqTest.testlistIterator_int");
  }
  @Test
  public void testlistIterator_void(){
      for(final var initParams:ALL_STRUCT_INIT_PARAMS){
          for(final var illegalMod:initParams.validPreMods){
              for(final var size:SHORT_SIZES){
                  TestExecutorService.submitTest(()->{
                      final var monitor=SequenceInitialization.Ascending.initialize(getMonitoredList(initParams,size),
                              size,0);
                      try{
                          if(illegalMod.expectedException == null){
                              monitor.getMonitoredListIterator().verifyIteratorState();
                          }else{
                              monitor.illegalMod(illegalMod);
                              Assertions.assertThrows(illegalMod.expectedException,monitor::getMonitoredListIterator);
                          }
                      }finally{
                          monitor.verifyCollectionState();
                      }
                  });
              }
          }
      }
      TestExecutorService.completeAllTests("DblLnkSeqTest.testlistIterator_void");
  }
  @Test
  public void testListItrhasPrevious_void(){
      for(final var initParams:ALL_STRUCT_INIT_PARAMS){
          for(final var size:SHORT_SIZES){
              TestExecutorService.submitTest(()->{
                  final var seqMonitor=SequenceInitialization.Ascending.initialize(getMonitoredList(initParams,size),
                          size,0);
                  final var itrMonitor=seqMonitor.getMonitoredListIterator(size);
                  while(itrMonitor.verifyHasPrevious()){
                      itrMonitor.iterateReverse();
                  }
              });
          }
      }
      TestExecutorService.completeAllTests("DblLnkSeqTest.testListItrhasPrevious_void");
  }
  @Test
  public void testListItrnextIndex_void(){
      for(final var initParams:ALL_STRUCT_INIT_PARAMS){
          for(final var size:SHORT_SIZES){
              TestExecutorService.submitTest(()->{
                  final var seqMonitor=SequenceInitialization.Ascending.initialize(getMonitoredList(initParams,size),
                          size,0);
                  final var itrMonitor=seqMonitor.getMonitoredListIterator();
                  while(itrMonitor.verifyNextIndex() < size){
                      itrMonitor.iterateForward();
                  }
              });
          }
      }
      TestExecutorService.completeAllTests("DblLnkSeqTest.testListItrnextIndex_void");
  }
  @Test
  public void testListItrprevious_void(){
      for(final var initParams:ALL_STRUCT_INIT_PARAMS){
          for(final var size:SHORT_SIZES){
              if(size > 0 || initParams.checkedType.checked){
                  final var itrType=initParams.structType == StructType.DblLnkList?IteratorType.BidirectionalItr
                          :IteratorType.SubBidirectionalItr;
                  for(final var illegalMod:itrType.validPreMods){
                      if(illegalMod.expectedException == null || initParams.checkedType.checked){
                          for(final var outputType:initParams.collectionType.validOutputTypes()){
                              TestExecutorService.submitTest(()->{
                                  final var seqMonitor=SequenceInitialization.Ascending
                                          .initialize(getMonitoredList(initParams,size),size,0);
                                  final var itrMonitor=seqMonitor.getMonitoredListIterator(size);
                                  itrMonitor.illegalMod(illegalMod);
                                  if(illegalMod.expectedException == null){
                                      while(itrMonitor.hasPrevious()){
                                          itrMonitor.verifyPrevious(outputType);
                                      }
                                      if(initParams.checkedType.checked){
                                          Assertions.assertThrows(NoSuchElementException.class,
                                                  ()->itrMonitor.verifyPrevious(outputType));
                                      }
                                  }else{
                                      Assertions.assertThrows(illegalMod.expectedException,
                                              ()->itrMonitor.verifyPrevious(outputType));
                                  }
                              });
                          }
                      }
                  }
              }
          }
      }
      TestExecutorService.completeAllTests("DblLnkSeqTest.testListItrprevious_void");
  }
  @Test
  public void testListItrpreviousIndex_void(){
      for(final var initParams:ALL_STRUCT_INIT_PARAMS){
          for(final var size:SHORT_SIZES){
              TestExecutorService.submitTest(()->{
                  final var seqMonitor=SequenceInitialization.Ascending.initialize(getMonitoredList(initParams,size),
                          size,0);
                  final var itrMonitor=seqMonitor.getMonitoredListIterator(size);
                  while(itrMonitor.verifyPreviousIndex() > 0){
                      itrMonitor.iterateReverse();
                  }
              });
          }
      }
      TestExecutorService.completeAllTests("DblLnkSeqTest.testListItrhasPrevious_void");
  }
  @Test
  public void testListItrset_val(){
      for(final var initParams:ALL_STRUCT_INIT_PARAMS){
          final var itrType=initParams.structType == StructType.DblLnkList?IteratorType.BidirectionalItr
                  :IteratorType.SubBidirectionalItr;
          for(final var illegalMod:itrType.validPreMods){
              if(illegalMod.expectedException == null || initParams.checkedType.checked){
                  for(final var removeScenario:itrType.validItrRemoveScenarios){
                      if(removeScenario.expectedException == null || initParams.checkedType.checked){
                          for(final var inputType:initParams.collectionType.mayBeAddedTo()){
                              for(final var functionCallType:inputType.validFunctionCalls){
                                  for(final int size:SHORT_SIZES){
                                      TestExecutorService.submitTest(()->{
                                          final var seqMonitor=SequenceInitialization.Ascending
                                                  .initialize(getMonitoredList(initParams,size),size,0);
                                          final var itrMonitor=seqMonitor.getMonitoredListIterator();
                                          removeScenario.initialize(itrMonitor);
                                          itrMonitor.illegalMod(illegalMod);
                                          if(removeScenario.expectedException == null){
                                              if(illegalMod.expectedException == null){
                                                  int i=1;
                                                  {
                                                      final int finalI=i;
                                                  itrMonitor.verifySet(inputType.convertValUnchecked(finalI),inputType,
                                                          functionCallType);
                                                  }
                                                  while(itrMonitor.hasNext()){
                                                      itrMonitor.iterateForward();
                                                      final int finalI=++i;
                                                      itrMonitor.verifySet(inputType.convertValUnchecked(finalI),
                                                              inputType,functionCallType);
                                                  }
                                              }else{
                                                  Assertions.assertThrows(illegalMod.expectedException,
                                                          ()->itrMonitor.verifySet(inputType.convertValUnchecked(1),
                                                                  inputType,functionCallType));
                                              }
                                          }else{
                                              Assertions.assertThrows(removeScenario.expectedException,
                                                      ()->itrMonitor.verifySet(inputType.convertValUnchecked(1),
                                                              inputType,functionCallType));
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
      TestExecutorService.completeAllTests("DblLnkSeqTest.testListItrset_val");
  }
  @Test
  public void testListItradd_val(){
      for(final var position:POSITIONS){
          if(position >= 0){
              for(final var initParams:ALL_STRUCT_INIT_PARAMS){
                  final var itrType=initParams.structType == StructType.DblLnkList?IteratorType.BidirectionalItr
                          :IteratorType.SubBidirectionalItr;
                  for(final var illegalMod:itrType.validPreMods){
                      if(illegalMod.expectedException == null || initParams.checkedType.checked){
                          for(final var inputType:initParams.collectionType.mayBeAddedTo()){
                              for(final var functionCallType:inputType.validFunctionCalls){
                                      TestExecutorService.submitTest(()->{
                                          final var seqMonitor=getMonitoredList(initParams,100);
                                          final var itrMonitor=seqMonitor.getMonitoredListIterator();
                                          for(int i=0;;){
                                              itrMonitor.verifyAdd(inputType.convertValUnchecked(i),inputType,
                                                      functionCallType);
                                              if(++i == 100){
                                                  break;
                                              }
                                              final double dI=i;
                                              double currPosition;
                                              while((currPosition=itrMonitor.nextIndex() / dI) < position
                                                      && itrMonitor.hasNext()){
                                                  itrMonitor.iterateForward();
                                              }
                                              while(currPosition > position && itrMonitor.hasPrevious()){
                                                  itrMonitor.iterateReverse();
                                                  currPosition=itrMonitor.nextIndex() / dI;
                                              }
                                          }
                                          if(illegalMod.expectedException != null){
                                              itrMonitor.illegalMod(illegalMod);
                                              Assertions.assertThrows(illegalMod.expectedException,
                                                      ()->itrMonitor.verifyAdd(inputType.convertValUnchecked(100),
                                                              inputType,functionCallType));
                                              // for good measure, do it again with an
                                              // empty list
                                              final var itrMonitor2=getMonitoredList(initParams,100)
                                                      .getMonitoredListIterator();
                                              itrMonitor2.illegalMod(illegalMod);
                                              Assertions.assertThrows(illegalMod.expectedException,
                                                      ()->itrMonitor2.verifyAdd(inputType.convertValUnchecked(100),
                                                              inputType,functionCallType));
                                          }
                                      });
                                  
                              }
                          }
                      }
                  }
              }
          }
      }
      TestExecutorService.completeAllTests("DblLnkSeqTest.testListItradd_val");
  }
  @Test
  public void testItrremove_void(){
      for(final var initParams:ALL_STRUCT_INIT_PARAMS){
          for(final var itrType:initParams.structType.validItrTypes){
              for(final var illegalMod:itrType.validPreMods){
                  if(illegalMod.expectedException == null || initParams.checkedType.checked){
                      for(final var removeScenario:itrType.validItrRemoveScenarios){
                          if(removeScenario.expectedException == null || initParams.checkedType.checked){
                              for(final var size:SIZES){
                                  int prevNumToIterate=-1;
                                  positionLoop:for(final var position:POSITIONS){
                                      final int numToIterate;
                                      if(position >= 0 && (numToIterate=(int)(size * position)) != prevNumToIterate){
                                          prevNumToIterate=numToIterate;
                                          switch(removeScenario){
                                          case PostInit:
                                              if(numToIterate != 0){
                                                  break positionLoop;
                                              }
                                              break;
                                          case PostNext:
                                              if(itrType.iteratorInterface != OmniListIterator.class
                                                      && (size == 0 || numToIterate == size)){
                                                  continue;
                                              }
                                          case PostAdd:
                                          case PostPrev:
                                              break;
                                          case PostRemove:
                                              if(size == 0 && itrType.iteratorInterface != OmniListIterator.class){
                                                  continue;
                                              }
                                              break;
                                          default:
                                              throw removeScenario.invalid();
                                          }
                                          TestExecutorService.submitTest(()->{
                                              final var seqMonitor=SequenceInitialization.Ascending
                                                      .initialize(getMonitoredList(initParams,size),size,0);
                                              final var itrMonitor=seqMonitor.getMonitoredIterator(numToIterate,
                                                      itrType);
                                              removeScenario.initialize(itrMonitor);
                                              itrMonitor.illegalMod(illegalMod);
                                              if(removeScenario.expectedException == null){
                                                  if(illegalMod.expectedException == null){
                                                      itrMonitor.verifyRemove();
                                                      switch(removeScenario){
                                                      case PostNext:{
                                                          while(itrMonitor.hasNext()){
                                                              itrMonitor.iterateForward();
                                                              itrMonitor.verifyRemove();
                                                          }
                                                          if(!(itrMonitor instanceof MonitoredList.MonitoredListIterator<?,?>)){
                                                              Assertions.assertEquals(numToIterate < 2,
                                                                      seqMonitor.isEmpty());
                                                              break;
                                                          }
                                                      }
                                                      case PostPrev:{
                                                          final var cast=(MonitoredList.MonitoredListIterator<?,?>)itrMonitor;
                                                          while(cast.hasPrevious()){
                                                              cast.iterateReverse();
                                                              cast.verifyRemove();
                                                          }
                                                          while(cast.hasNext()){
                                                              cast.iterateForward();
                                                              cast.verifyRemove();
                                                          }
                                                          Assertions.assertTrue(seqMonitor.isEmpty());
                                                          break;
                                                      }
                                                      default:
                                                          throw removeScenario.invalid();
                                                      }
                                                  }else{
                                                      Assertions.assertThrows(illegalMod.expectedException,
                                                              ()->itrMonitor.verifyRemove());
                                                  }
                                              }else{
                                                  Assertions.assertThrows(removeScenario.expectedException,
                                                          ()->itrMonitor.verifyRemove());
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
      TestExecutorService.completeAllTests("DblLnkSeqTest.testItrremove_void");
  }
  @Test
  public void testput_intval(){
      for(final var initParams:ALL_STRUCT_INIT_PARAMS){
          for(final var illegalMod:initParams.validPreMods){
              for(final var size:SHORT_SIZES){
                  TestExecutorService.submitTest(()->{
                      final var monitor=SequenceInitialization.Ascending.initialize(getMonitoredList(initParams,size),
                              size,0);
                      if(illegalMod.expectedException == null){
                          for(final var inputType:initParams.collectionType.mayBeAddedTo()){
                              for(final var functionCallType:inputType.validFunctionCalls){
                                  if(initParams.checkedType.checked){
                                      Assertions.assertThrows(IndexOutOfBoundsException.class,
                                              ()->monitor.verifyPut(-1,inputType.convertValUnchecked(0),inputType,
                                                      functionCallType));
                                      Assertions.assertThrows(IndexOutOfBoundsException.class,
                                              ()->monitor.verifyPut(size,inputType.convertValUnchecked(size + 1),
                                                      inputType,functionCallType));
                                  }
                                  for(int index=0;index < size;++index){
                                      monitor.verifyPut(index,inputType.convertValUnchecked(index + 1),inputType,
                                              functionCallType);
                                  }
                              }
                          }
                      }else{
                          monitor.illegalMod(illegalMod);
                          for(final var inputType:initParams.collectionType.mayBeAddedTo()){
                              for(final var functionCallType:inputType.validFunctionCalls){
                                  for(int tmpIndex=-1;tmpIndex <= size;++tmpIndex){
                                      final int index=tmpIndex;
                                      Assertions.assertThrows(illegalMod.expectedException,
                                              ()->monitor.verifyPut(index,inputType.convertValUnchecked(index + 1),
                                                      inputType,functionCallType));
                                  }
                              }
                          }
                      }
                  });
              }
          }
      }
      TestExecutorService.completeAllTests("DblLnkSeqTest.testput_intval");
  }
  @Test
  public void testremoveIf_Predicate(){
      for(final var initParams:ALL_STRUCT_INIT_PARAMS){
          for(final var filterGen:initParams.structType.validMonitoredRemoveIfPredicateGens){
              final int sizeBound=initParams.collectionType == DataType.BOOLEAN?10:100;
              final int sizeInc=Math.max(1,sizeBound / 10);
              for(int tmpSize=0;tmpSize <= sizeBound;tmpSize+=sizeInc){
                  final int size;
                  if((size=tmpSize) == 0 || initParams.checkedType.checked || filterGen.expectedException == null){
                      final int initValBound;
                      final int periodBound;
                      if(initParams.collectionType == DataType.BOOLEAN){
                          initValBound=size == 0?0:1;
                          periodBound=Math.max(0,size - 1);
                      }else{
                          initValBound=0;
                          periodBound=0;
                      }
                      for(final var functionCallType:initParams.collectionType.validFunctionCalls){
                          for(final var illegalMod:initParams.validPreMods){
                              long randSeedBound;
                              double[] thresholdArr;
                              if(filterGen.randomized && size > 0 && !functionCallType.boxed && illegalMod.expectedException==null){
                                  randSeedBound=100;
                                  thresholdArr=RANDOM_THRESHOLDS;
                              }else{
                                  randSeedBound=0;
                                  thresholdArr=NON_RANDOM_THRESHOLD;
                              }
                          
                              for(long tmpRandSeed=0;tmpRandSeed <= randSeedBound;++tmpRandSeed){
                                  final long randSeed=tmpRandSeed;
                                  for(int tmpInitVal=0;tmpInitVal <= initValBound;++tmpInitVal){
                                      final int initVal=tmpInitVal;
                                      for(int tmpPeriod=0;tmpPeriod <= periodBound;++tmpPeriod){
                                          final int period=tmpPeriod;
                                          for(final var threshold:thresholdArr){
                                              TestExecutorService.submitTest(()->{
                                                  final var monitor=SequenceInitialization.Ascending.initialize(
                                                          getMonitoredList(initParams,size),size,initVal,period);
                                                  final var filter=filterGen.getMonitoredRemoveIfPredicate(monitor,
                                                          threshold,randSeed);
                                                  if(illegalMod.expectedException == null){
                                                      if(filterGen.expectedException == null || size == 0){
                                                          monitor.verifyRemoveIf(filter,functionCallType);
                                                      }else{
                                                          Assertions.assertThrows(filterGen.expectedException,
                                                                  ()->monitor.verifyRemoveIf(filter,
                                                                          functionCallType));
                                                      }
                                                  }else{
                                                      monitor.illegalMod(illegalMod);
                                                      Assertions.assertThrows(illegalMod.expectedException,
                                                              ()->monitor.verifyRemoveIf(filter,functionCallType));
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
      }
      TestExecutorService.completeAllTests("DblLnkSeqTest.testremoveIf_Predicate");
  }
  @Test
  public void testset_intval(){
      for(final var initParams:ALL_STRUCT_INIT_PARAMS){
          for(final var illegalMod:initParams.validPreMods){
              for(final int size:SHORT_SIZES){
                  TestExecutorService.submitTest(()->{
                      final var monitor=SequenceInitialization.Ascending.initialize(getMonitoredList(initParams,size),
                              size,0);
                      if(illegalMod.expectedException == null){
                          for(final var functionCallType:initParams.collectionType.validFunctionCalls){
                              if(initParams.checkedType.checked){
                                  Assertions.assertThrows(IndexOutOfBoundsException.class,()->monitor.verifySet(-1,
                                          initParams.collectionType.convertValUnchecked(0),functionCallType));
                                  Assertions.assertThrows(IndexOutOfBoundsException.class,()->monitor.verifySet(size,
                                          initParams.collectionType.convertValUnchecked(size + 1),functionCallType));
                              }
                              for(int index=0;index < size;++index){
                                  monitor.verifySet(index,initParams.collectionType.convertValUnchecked(index + 1),
                                          functionCallType);
                              }
                          }
                      }else{
                          monitor.illegalMod(illegalMod);
                          for(final var functionCallType:initParams.collectionType.validFunctionCalls){
                              for(int tmpIndex=-1;tmpIndex <= size;++tmpIndex){
                                  final int index=tmpIndex;
                                  Assertions.assertThrows(illegalMod.expectedException,()->monitor.verifySet(index,
                                          initParams.collectionType.convertValUnchecked(index + 1),functionCallType));
                              }
                          }
                      }
                  });
              }
          }
      }
      TestExecutorService.completeAllTests("DblLnkSeqTest.testset_intval");
  }
  @Test
  public void testsort_Comparator(){
      for(final var initParams:ALL_STRUCT_INIT_PARAMS){
          for(final var comparatorGen:initParams.structType.validComparatorGens){
              if(initParams.collectionType == DataType.REF || comparatorGen.validWithPrimitive){
                  for(final var size:SIZES){
                      if(size < 2 || comparatorGen.expectedException == null || initParams.checkedType.checked){
                          for(final var functionCallType:initParams.collectionType.validFunctionCalls){
                              for(final var illegalMod:initParams.validPreMods){
                                  TestExecutorService.submitTest(()->{
                                      final var monitor=getMonitoredList(initParams,size);
                                      if(illegalMod.expectedException == null){
                                          if(size < 2 || comparatorGen.expectedException == null){
                                              monitor.verifyStableSort(size,comparatorGen,functionCallType);
                                          }else{
                                              Assertions.assertThrows(comparatorGen.expectedException,()->monitor
                                                      .verifyStableSort(size,comparatorGen,functionCallType));
                                          }
                                      }else{
                                          monitor.illegalMod(illegalMod);
                                          Assertions.assertThrows(illegalMod.expectedException,
                                                  ()->monitor.verifyStableSort(size,comparatorGen,functionCallType));
                                      }
                                  });
                              }
                          }
                      }
                  }
              }
          }
      }
      TestExecutorService.completeAllTests("DblLnkSeqTest.testsort_Comparator");
  }
  @Test
  public void teststableAscendingSort_void(){
      for(final var initParams:ALL_STRUCT_INIT_PARAMS){
          for(final var comparatorGen:initParams.structType.validComparatorGens){
              if(comparatorGen.validWithNoComparator
                      && (comparatorGen.validWithPrimitive || initParams.collectionType == DataType.REF)){
                  for(final var size:SIZES){
                      if(size < 2 || comparatorGen.expectedException == null || initParams.checkedType.checked){
                          for(final var illegalMod:initParams.validPreMods){
                              TestExecutorService.submitTest(()->{
                                  final var monitor=getMonitoredList(initParams,size);
                                  if(illegalMod.expectedException == null){
                                      if(size < 2 || comparatorGen.expectedException == null){
                                          monitor.verifyAscendingStableSort(size,comparatorGen);
                                      }else{
                                          Assertions.assertThrows(comparatorGen.expectedException,
                                                  ()->monitor.verifyAscendingStableSort(size,comparatorGen));
                                      }
                                  }else{
                                      monitor.illegalMod(illegalMod);
                                      Assertions.assertThrows(illegalMod.expectedException,
                                              ()->monitor.verifyAscendingStableSort(size,comparatorGen));
                                  }
                              });
                          }
                      }
                  }
              }
          }
      }
      TestExecutorService.completeAllTests("DblLnkSeqTest.teststableAscendingSort_void");
  }
  @Test
  public void teststableDescendingSort_void(){
      for(final var initParams:ALL_STRUCT_INIT_PARAMS){
          for(final var comparatorGen:initParams.structType.validComparatorGens){
              if(comparatorGen.validWithNoComparator
                      && (comparatorGen.validWithPrimitive || initParams.collectionType == DataType.REF)){
                  for(final var size:SIZES){
                      if(size < 2 || comparatorGen.expectedException == null || initParams.checkedType.checked){
                          for(final var illegalMod:initParams.validPreMods){
                              TestExecutorService.submitTest(()->{
                                  final var monitor=getMonitoredList(initParams,size);
                                  if(illegalMod.expectedException == null){
                                      if(size < 2 || comparatorGen.expectedException == null){
                                          monitor.verifyDescendingStableSort(size,comparatorGen);
                                      }else{
                                          Assertions.assertThrows(comparatorGen.expectedException,
                                                  ()->monitor.verifyDescendingStableSort(size,comparatorGen));
                                      }
                                  }else{
                                      monitor.illegalMod(illegalMod);
                                      Assertions.assertThrows(illegalMod.expectedException,
                                              ()->monitor.verifyDescendingStableSort(size,comparatorGen));
                                  }
                              });
                          }
                      }
                  }
              }
          }
      }
      TestExecutorService.completeAllTests("DblLnkSeqTest.teststableDescendingSort_void");
  }
  @Test
  public void testtoArray_ObjectArray(){
      for(final var initParams:ALL_STRUCT_INIT_PARAMS){
          for(final var illegalMod:initParams.validPreMods){
              for(final int size:SIZES){
                  for(int tmpArrSize=0,tmpArrSizeBound=size + 5;tmpArrSize <= tmpArrSizeBound;++tmpArrSize){
                      final int arrSize=tmpArrSize;
                      TestExecutorService.submitTest(()->{
                          final var monitor=SequenceInitialization.Ascending
                                  .initialize(getMonitoredList(initParams,size),size,0);
                          if(illegalMod.expectedException == null){
                              monitor.verifyToArray(new Object[arrSize]);
                          }else{
                              monitor.illegalMod(illegalMod);
                              Assertions.assertThrows(illegalMod.expectedException,
                                      ()->monitor.verifyToArray(new Object[arrSize]));
                          }
                      });
                  }
              }
          }
      }
      TestExecutorService.completeAllTests("DblLnkSeqTest.testtoArray_ObjectArray");
  }
  @Test
  public void testtoArray_void(){
      for(final var initParams:ALL_STRUCT_INIT_PARAMS){
          for(final var illegalMod:initParams.validPreMods){
              for(final int size:SIZES){
                  TestExecutorService.submitTest(()->{
                      final var monitor=SequenceInitialization.Ascending
                              .initialize(getMonitoredList(initParams,size),size,0);
                      if(illegalMod.expectedException == null){
                          for(final var outputType:initParams.collectionType.validOutputTypes()){
                              outputType.verifyToArray(monitor);
                          }
                      }else{
                          monitor.illegalMod(illegalMod);
                          for(final var outputType:initParams.collectionType.validOutputTypes()){
                              Assertions.assertThrows(illegalMod.expectedException,
                                      ()->outputType.verifyToArray(monitor));
                          }
                      }
                  });
              }
          }
      }
      TestExecutorService.completeAllTests("DblLnkSeqTest.testget_int");
  }
  @Test
  public void testunstableAscendingSort_void(){
      for(final var initParams:ALL_STRUCT_INIT_PARAMS){
          if(initParams.collectionType == DataType.REF){
              for(final var comparatorGen:initParams.structType.validComparatorGens){
                  if(comparatorGen.validWithNoComparator){
                      for(final var size:SIZES){
                          if(size < 2 || initParams.checkedType.checked || comparatorGen.expectedException == null){
                              for(final var illegalMod:initParams.validPreMods){
                                  TestExecutorService.submitTest(()->{
                                      final var monitor=getMonitoredList(initParams,size);
                                      if(illegalMod.expectedException == null){
                                          if(size < 2 || comparatorGen.expectedException == null){
                                              monitor.verifyAscendingUnstableSort(size,comparatorGen);
                                          }else{
                                              Assertions.assertThrows(comparatorGen.expectedException,
                                                      ()->monitor.verifyAscendingUnstableSort(size,comparatorGen));
                                          }
                                      }else{
                                          monitor.illegalMod(illegalMod);
                                          Assertions.assertThrows(illegalMod.expectedException,
                                                  ()->monitor.verifyAscendingUnstableSort(size,comparatorGen));
                                      }
                                  });
                              }
                          }
                      }
                  }
              }
          }
      }
      TestExecutorService.completeAllTests("DblLnkSeqTest.testunstableAscendingSort_void");
  }
  @Test
  public void testunstableDescendingSort_void(){
      for(final var initParams:ALL_STRUCT_INIT_PARAMS){
          if(initParams.collectionType == DataType.REF){
              for(final var comparatorGen:initParams.structType.validComparatorGens){
                  if(comparatorGen.validWithNoComparator){
                      for(final var size:SIZES){
                          if(size < 2 || initParams.checkedType.checked || comparatorGen.expectedException == null){
                              for(final var illegalMod:initParams.validPreMods){
                                  TestExecutorService.submitTest(()->{
                                      final var monitor=getMonitoredList(initParams,size);
                                      if(illegalMod.expectedException == null){
                                          if(size < 2 || comparatorGen.expectedException == null){
                                              monitor.verifyDescendingUnstableSort(size,comparatorGen);
                                          }else{
                                              Assertions.assertThrows(comparatorGen.expectedException,
                                                      ()->monitor.verifyDescendingUnstableSort(size,comparatorGen));
                                          }
                                      }else{
                                          monitor.illegalMod(illegalMod);
                                          Assertions.assertThrows(illegalMod.expectedException,
                                                  ()->monitor.verifyDescendingUnstableSort(size,comparatorGen));
                                      }
                                  });
                              }
                          }
                      }
                  }
              }
          }
      }
      TestExecutorService.completeAllTests("DblLnkSeqTest.testunstableDescendingSort_void");
  }
  @Test
  public void testunstableSort_Comparator(){
      for(final var initParams:ALL_STRUCT_INIT_PARAMS){
          if(initParams.collectionType != DataType.BOOLEAN){
              for(final var comparatorGen:initParams.structType.validComparatorGens){
                  if(initParams.collectionType == DataType.REF || comparatorGen.validWithPrimitive){
                      for(final var size:SIZES){
                          if(size < 2 || initParams.checkedType.checked || comparatorGen.expectedException == null){
                              for(final var illegalMod:initParams.validPreMods){
                                  TestExecutorService.submitTest(()->{
                                      final var monitor=getMonitoredList(initParams,size);
                                      if(illegalMod.expectedException == null){
                                          if(size < 2 || comparatorGen.expectedException == null){
                                              monitor.verifyUnstableSort(size,comparatorGen);
                                          }else{
                                              Assertions.assertThrows(comparatorGen.expectedException,
                                                      ()->monitor.verifyUnstableSort(size,comparatorGen));
                                          }
                                      }else{
                                          monitor.illegalMod(illegalMod);
                                          Assertions.assertThrows(illegalMod.expectedException,
                                                  ()->monitor.verifyUnstableSort(size,comparatorGen));
                                      }
                                  });
                              }
                          }
                      }
                  }
              }
          }
      }
      TestExecutorService.completeAllTests("DblLnkSeqTest.testunstableSort_Comparator");
  }
  @Test
  public void testremoveAt_int(){
      for(final var initParams:ALL_STRUCT_INIT_PARAMS){
          for(final var illegalMod:initParams.validPreMods){
              for(final var position:illegalMod.expectedException == null?NON_THROWING_REMOVE_AT_POSITIONS
                      :THROWING_REMOVE_AT_POSITIONS){
                  if(initParams.checkedType.checked || position >= 0 && position <= 2){
                      for(final int size:SIZES){
                          switch(position){
                          case 0:
                              if(size < 1){
                                  continue;
                              }
                              break;
                          case 1:
                              if(size < 3){
                                  continue;
                              }
                              break;
                          case 2:
                              if(size < 2){
                                  continue;
                              }
                          default:
                          }
                          for(final var outputType:initParams.collectionType.validOutputTypes()){
                              TestExecutorService.submitTest(()->{
                                  final var monitor=SequenceInitialization.Ascending
                                          .initialize(getMonitoredList(initParams,size),size,0);
                                  if(illegalMod.expectedException == null){
                                      switch(position){
                                      case -1:
                                          Assertions.assertThrows(IndexOutOfBoundsException.class,
                                                  ()->monitor.verifyRemoveAt(-1,outputType));
                                          break;
                                      case 3:
                                          Assertions.assertThrows(IndexOutOfBoundsException.class,
                                                  ()->monitor.verifyRemoveAt(size,outputType));
                                          break;
                                      case 0:
                                          for(int i=0;i < size;++i){
                                              monitor.verifyRemoveAt(0,outputType);
                                          }
                                          break;
                                      case 1:{
                                          for(int i=0;i < size;++i){
                                              monitor.verifyRemoveAt((size - i) / 2,outputType);
                                          }
                                          break;
                                      }
                                      case 2:
                                          for(int i=0;i < size;++i){
                                              monitor.verifyRemoveAt(size - i - 1,outputType);
                                          }
                                          break;
                                      default:
                                          throw new UnsupportedOperationException("Unknown position " + position);
                                      }
                                  }else{
                                      monitor.illegalMod(illegalMod);
                                      for(int tmpIndex=-1;tmpIndex <= size;++tmpIndex){
                                          final int index=tmpIndex;
                                          Assertions.assertThrows(illegalMod.expectedException,
                                                  ()->monitor.verifyRemoveAt(index,outputType));
                                      }
                                  }
                              });
                          }
                      }
                  }
              }
          }
      }
      TestExecutorService.completeAllTests("DblLnkSeqTest.testremoveAt_int");
  }
}
