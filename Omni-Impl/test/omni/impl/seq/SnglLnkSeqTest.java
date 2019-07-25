package omni.impl.seq;
import java.io.Externalizable;
import java.util.NoSuchElementException;
import java.util.Random;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import omni.api.OmniCollection;
import omni.api.OmniIterator;
import omni.api.OmniQueue;
import omni.api.OmniStack;
import omni.impl.BooleanSnglLnkNode;
import omni.impl.ByteSnglLnkNode;
import omni.impl.CharSnglLnkNode;
import omni.impl.CheckedType;
import omni.impl.DataType;
import omni.impl.DoubleSnglLnkNode;
import omni.impl.FloatSnglLnkNode;
import omni.impl.FunctionCallType;
import omni.impl.IntSnglLnkNode;
import omni.impl.IteratorType;
import omni.impl.LongSnglLnkNode;
import omni.impl.MonitoredCollection;
import omni.impl.MonitoredFunction;
import omni.impl.MonitoredFunctionGen;
import omni.impl.MonitoredObjectGen;
import omni.impl.MonitoredQueue;
import omni.impl.MonitoredSequence;
import omni.impl.MonitoredStack;
import omni.impl.QueryCastType;
import omni.impl.QueryVal;
import omni.impl.RefSnglLnkNode;
import omni.impl.ShortSnglLnkNode;
import omni.impl.StructType;
import omni.util.OmniArray;
import omni.util.TestExecutorService;
@Tag("NewTest")
@TestMethodOrder(OrderAnnotation.class)
public class SnglLnkSeqTest{
  private static abstract class AbstractSnglLnkSeqMonitor<SEQ extends AbstractSeq<?>&Externalizable>
      extends AbstractSequenceMonitor<SEQ>{
    abstract class AbstractItrMonitor implements MonitoredCollection.MonitoredIterator<OmniIterator<?>,SEQ>{
      final OmniIterator<?> itr;
      Object expectedPrev;
      Object expectedCurr;
      Object expectedNext;
      int expectedItrModCount;
      int expectedCurrIndex;
      int expectedLastRetIndex;
      AbstractItrMonitor(int expectedCurrIndex){
        this.itr=seq.iterator();
        this.expectedNext=getHead();
        this.expectedItrModCount=expectedModCount;
        this.expectedCurrIndex=expectedCurrIndex;
        this.expectedLastRetIndex=-1;
      }
      @Override public OmniIterator<?> getIterator(){
        return itr;
      }
      @Override public IteratorType getIteratorType(){
        return IteratorType.AscendingItr;
      }
      @Override public MonitoredCollection<SEQ> getMonitoredCollection(){
        return AbstractSnglLnkSeqMonitor.this;
      }
      @Override public boolean hasNext(){
        return expectedNext != null;
      }
      @Override public boolean nextWasJustCalled(){
        return this.expectedLastRetIndex != -1;
      }
      @Override public void updateItrNextState(){
        Object newCurr;
        this.expectedNext=getNext(newCurr=this.expectedNext);
        this.expectedPrev=this.expectedCurr;
        this.expectedCurr=newCurr;
        updateItrNextIndex();
      }
      @Override public void updateItrRemoveState(){
        updateRemoveIndexState(expectedCurrIndex=expectedLastRetIndex);
        this.expectedCurr=this.expectedPrev;
        ++expectedItrModCount;
        this.expectedLastRetIndex=-1;
      }
      
      private void verifyForEachRemainingHelper(MonitoredFunction function){
        final var itr=function.iterator();
        Object curr=this.expectedCurr;
        Object prev;
        switch(dataType){
        case BOOLEAN:{
          var next=(BooleanSnglLnkNode)expectedNext;
          do{
            Assertions.assertEquals(next.val,(boolean)itr.next());
            prev=curr;
            curr=next;
          }while((next=next.next) != null);
          break;
        }
        case BYTE:{
          var next=(ByteSnglLnkNode)expectedNext;
          do{
            Assertions.assertEquals(next.val,(byte)itr.next());
            prev=curr;
            curr=next;
          }while((next=next.next) != null);
          break;
        }
        case CHAR:{
          var next=(CharSnglLnkNode)expectedNext;
          do{
            Assertions.assertEquals(next.val,(char)itr.next());
            prev=curr;
            curr=next;
          }while((next=next.next) != null);
          break;
        }
        case DOUBLE:{
          var next=(DoubleSnglLnkNode)expectedNext;
          do{
            Assertions.assertEquals(next.val,(double)itr.next());
            prev=curr;
            curr=next;
          }while((next=next.next) != null);
          break;
        }
        case FLOAT:{
          var next=(FloatSnglLnkNode)expectedNext;
          do{
            Assertions.assertEquals(next.val,(float)itr.next());
            prev=curr;
            curr=next;
          }while((next=next.next) != null);
          break;
        }
        case INT:{
          var next=(IntSnglLnkNode)expectedNext;
          do{
            Assertions.assertEquals(next.val,(int)itr.next());
            prev=curr;
            curr=next;
          }while((next=next.next) != null);
          break;
        }
        case LONG:{
          var next=(LongSnglLnkNode)expectedNext;
          do{
            Assertions.assertEquals(next.val,(long)itr.next());
            prev=curr;
            curr=next;
          }while((next=next.next) != null);
          break;
        }
        case REF:{
          var next=(RefSnglLnkNode<?>)expectedNext;
          do{
            Assertions.assertSame(next.val,itr.next());
            prev=curr;
            curr=next;
          }while((next=next.next) != null);
          break;
        }
        case SHORT:{
          var next=(ShortSnglLnkNode)expectedNext;
          do{
            Assertions.assertEquals(next.val,(short)itr.next());
            prev=curr;
            curr=next;
          }while((next=next.next) != null);
          break;
        }
        default:
          throw dataType.invalid();
        }
        this.expectedNext=null;
        this.expectedCurr=curr;
        this.expectedPrev=prev;
      }
      abstract void updateItrNextIndex();
    }
    AbstractSnglLnkSeqMonitor(CheckedType checkedType,DataType dataType){
      super(checkedType,dataType);
    }
    AbstractSnglLnkSeqMonitor(CheckedType checkedType,DataType dataType,int initCap){
      super(checkedType,dataType,initCap);
    }
    @Override public void copyListContents(){
      switch(dataType){
      case BOOLEAN:{
        copyBooleanListContents();
        break;
      }
      case BYTE:{
        copyByteListContents();
        break;
      }
      case CHAR:{
        copyCharListContents();
        break;
      }
      case DOUBLE:{
        copyDoubleListContents();
        break;
      }
      case FLOAT:{
        copyFloatListContents();
        break;
      }
      case INT:{
        copyIntListContents();
        break;
      }
      case LONG:{
        copyLongListContents();
        break;
      }
      case REF:{
        copyRefListContents();
        break;
      }
      case SHORT:{
        copyShortListContents();
        break;
      }
      default:
        throw dataType.invalid();
      }
    }
    @Override public MonitoredIterator<? extends OmniIterator<?>,SEQ> getMonitoredIterator(int index,
        IteratorType itrType){
      final var itrMonitor=getMonitoredIterator(itrType);
      while(--index >= 0 && itrMonitor.hasNext()){
        itrMonitor.iterateForward();
      }
      return itrMonitor;
    }
    @Override public MonitoredIterator<? extends OmniIterator<?>,SEQ> getMonitoredIterator(IteratorType itrType){
      if(itrType != IteratorType.AscendingItr){ throw itrType.invalid(); }
      return getMonitoredIterator();
    }
    @Override public void verifyArrayIsCopy(Object arr,boolean emptyArrayMayBeSame){
      // nothing to do
    }
    @Override public void verifyClone(Object clone){
      switch(dataType){
      case BOOLEAN:{
        verifyBooleanClone(clone);
        break;
      }
      case BYTE:{
        verifyByteClone(clone);
        break;
      }
      case CHAR:{
        verifyCharClone(clone);
        break;
      }
      case DOUBLE:{
        verifyDoubleClone(clone);
        break;
      }
      case FLOAT:{
        verifyFloatClone(clone);
        break;
      }
      case INT:{
        verifyIntClone(clone);
        break;
      }
      case LONG:{
        verifyLongClone(clone);
        break;
      }
      case REF:{
        verifyRefClone(clone,true);
        break;
      }
      case SHORT:{
        verifyShortClone(clone);
        break;
      }
      default:{
        throw dataType.invalid();
      }
      }
    }
    @Override public void verifyCollectionState(){
      switch(dataType){
      case BOOLEAN:{
        verifyBooleanIntegrity();
        break;
      }
      case BYTE:{
        verifyByteIntegrity();
        break;
      }
      case CHAR:{
        verifyCharIntegrity();
        break;
      }
      case DOUBLE:{
        verifyDoubleIntegrity();
        break;
      }
      case FLOAT:{
        verifyFloatIntegrity();
        break;
      }
      case INT:{
        verifyIntIntegrity();
        break;
      }
      case LONG:{
        verifyLongIntegrity();
        break;
      }
      case REF:{
        verifyRefIntegrity();
        break;
      }
      case SHORT:{
        verifyShortIntegrity();
        break;
      }
      default:
        throw dataType.invalid();
      }
    }
    @Override public void verifyReadAndWriteClone(SEQ readCol){
      verifyRefClone(readCol,false);
    }
    private Object getHead(){
      switch(dataType){
      case BOOLEAN:
        return ((BooleanSnglLnkSeq)seq).head;
      case BYTE:
        return ((ByteSnglLnkSeq)seq).head;
      case CHAR:
        return ((CharSnglLnkSeq)seq).head;
      case DOUBLE:
        return ((DoubleSnglLnkSeq)seq).head;
      case FLOAT:
        return ((FloatSnglLnkSeq)seq).head;
      case INT:
        return ((IntSnglLnkSeq)seq).head;
      case LONG:
        return ((LongSnglLnkSeq)seq).head;
      case REF:
        return ((RefSnglLnkSeq<?>)seq).head;
      case SHORT:
        return ((ShortSnglLnkSeq)seq).head;
      default:
        throw dataType.invalid();
      }
    }
    private Object getNext(Object node){
      switch(dataType){
      case BOOLEAN:
        return ((BooleanSnglLnkNode)node).next;
      case BYTE:
        return ((ByteSnglLnkNode)node).next;
      case CHAR:
        return ((CharSnglLnkNode)node).next;
      case DOUBLE:
        return ((DoubleSnglLnkNode)node).next;
      case FLOAT:
        return ((FloatSnglLnkNode)node).next;
      case INT:
        return ((IntSnglLnkNode)node).next;
      case LONG:
        return ((LongSnglLnkNode)node).next;
      case REF:
        return ((RefSnglLnkNode<?>)node).next;
      case SHORT:
        return ((ShortSnglLnkNode)node).next;
      default:
        throw dataType.invalid();
      }
    }
    abstract void copyBooleanListContents();
    abstract void copyByteListContents();
    abstract void copyCharListContents();
    abstract void copyDoubleListContents();
    abstract void copyFloatListContents();
    abstract void copyIntListContents();
    abstract void copyLongListContents();
    abstract void copyRefListContents();
    abstract void copyShortListContents();
    @Override SEQ initSeq(int initCap){
      return initSeq();
    }
    abstract void verifyBooleanClone(Object clone);
    abstract void verifyBooleanIntegrity();
    abstract void verifyByteClone(Object clone);
    abstract void verifyByteIntegrity();
    abstract void verifyCharClone(Object clone);
    abstract void verifyCharIntegrity();
    abstract void verifyDoubleClone(Object clone);
    abstract void verifyDoubleIntegrity();
    abstract void verifyFloatClone(Object clone);
    abstract void verifyFloatIntegrity();
    abstract void verifyIntClone(Object clone);
    abstract void verifyIntIntegrity();
    abstract void verifyLongClone(Object clone);
    abstract void verifyLongIntegrity();
    abstract void verifyRefClone(Object clone,boolean refIsSame);
    abstract void verifyRefIntegrity();
    abstract void verifyShortClone(Object clone);
    abstract void verifyShortIntegrity();
  }
  private static interface AddTest<MONITOR extends MonitoredCollection<?>>{
    @SuppressWarnings("unchecked") private void runAllTests(String testName,StructType...structTypes){
      for(final var collectionType:DataType.values()){
        for(final var inputType:collectionType.mayBeAddedTo()){
          for(final var functionCallType:inputType.validFunctionCalls){
            for(final var checkedType:CheckedType.values()){
              for(final var structType:structTypes){
                TestExecutorService.submitTest(()->{
                  final var monitor=getMonitoredSequence(structType,checkedType,collectionType,10);
                  for(int i=0;i < 10;++i){
                    runTest((MONITOR)monitor,inputType.convertValUnchecked(i),inputType,functionCallType);
                  }
                });
              }
            }
          }
        }
      }
      TestExecutorService.completeAllTests(testName);
    }
    void runTest(MONITOR monitor,Object inputval,DataType inputType,FunctionCallType functionCallType);
  }
  private static interface BasicTest{
    private void runAllTests(String testName){
      for(final var structType:ALL_STRUCTS){
        for(final var checkedType:CheckedType.values()){
          for(final var collectionType:DataType.values()){
            for(final int size:SIZES){
              TestExecutorService.submitTest(()->runTest(SequenceInitialization.Ascending
                  .initialize(getMonitoredSequence(structType,checkedType,collectionType),size,0)));
            }
          }
        }
      }
      TestExecutorService.completeAllTests(testName);
    }
    void runTest(MonitoredSequence<?> monitor);
  }
  private static interface MonitoredFunctionTest<MONITOR extends MonitoredSequence<?>>{
    @SuppressWarnings("unchecked") private void runAllTests(String testName,long maxRand){
      for(final var collectionType:DataType.values()){
        for(final var size:MEDIUM_SIZES){
          final int initValBound=collectionType == DataType.BOOLEAN && size != 0?1:0;
          for(final var checkedType:CheckedType.values()){
            for(final var structType:ALL_STRUCTS){
              for(final var functionGen:structType.validMonitoredFunctionGens){
                if(functionGen.expectedException == null || checkedType.checked){
                  for(final var functionCallType:collectionType.validFunctionCalls){
                    final long randSeedBound=size > 1 && functionGen.randomized && !functionCallType.boxed?maxRand:0;
                    for(long tmpRandSeed=0;tmpRandSeed <= randSeedBound;++tmpRandSeed){
                      final long randSeed=tmpRandSeed;
                      for(int tmpInitVal=0;tmpInitVal <= initValBound;++tmpInitVal){
                        final int initVal=tmpInitVal;
                        TestExecutorService.submitTest(()->runTest(
                            (MONITOR)SequenceInitialization.Ascending.initialize(
                                getMonitoredSequence(structType,checkedType,collectionType,size),size,initVal),
                            functionGen,functionCallType,randSeed));
                      }
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
    void runTest(MONITOR monitor,MonitoredFunctionGen functionGen,FunctionCallType functionCallType,long randSeed);
  }
  private static interface PopTest<MONITOR extends MonitoredSequence<?>>{
    private void runAllTests(String testName,boolean throwOnEmpty,StructType...structTypes){
      for(final var structType:structTypes){
        for(final var checkedType:CheckedType.values()){
          for(final var collectionType:DataType.values()){
            for(final var outputType:collectionType.validOutputTypes()){
              TestExecutorService.submitTest(()->{
                @SuppressWarnings("unchecked") final var monitor=(MONITOR)SequenceInitialization.Ascending
                    .initialize(getMonitoredSequence(structType,checkedType,collectionType,10),10,0);
                for(int i=0;i < 10;++i){
                  processNextElement(monitor,outputType);
                }
                if(throwOnEmpty){
                  if(checkedType.checked){
                    Assertions.assertThrows(NoSuchElementException.class,()->processNextElement(monitor,outputType));
                  }
                }else{
                  processNextElement(monitor,outputType);
                }
              });
            }
          }
        }
      }
      TestExecutorService.completeAllTests(testName);
    }
    void processNextElement(MONITOR monitor,DataType outputType);
  }
  private static interface QueryTest<MONITOR extends MonitoredSequence<?>>{
    private void runAllTests(String testName,boolean useAllStructs){
      for(final var structType:ALL_STRUCTS){
        if(useAllStructs || structType == StructType.SnglLnkStack){
          for(final var collectionType:DataType.values()){
            for(final var queryVal:QueryVal.values()){
              if(collectionType.isValidQueryVal(queryVal)){
                queryVal.validQueryCombos.forEach((modification,castTypesToInputTypes)->{
                  castTypesToInputTypes.forEach((castType,inputTypes)->{
                    inputTypes.forEach(inputType->{
                      final boolean queryCanReturnTrue
                          =queryVal.queryCanReturnTrue(modification,castType,inputType,collectionType);
                      for(final var checkedType:CheckedType.values()){
                        if(queryVal == QueryVal.NonNull){
                          for(final var objGen:structType.validMonitoredObjectGens){
                            for(final var size:MEDIUM_SIZES){
                              if(size != 0 && objGen.expectedException != null && checkedType.checked){
                                TestExecutorService.submitTest(()->Assertions.assertThrows(objGen.expectedException,
                                    ()->runTest(structType,checkedType,collectionType,queryVal,modification,castType,
                                        inputType,objGen,size,size == 0?-1:0)));
                              }
                            }
                          }
                        }else{
                          for(final var size:MEDIUM_SIZES){
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
                                  if(position == 0.5d){
                                    continue;
                                  }
                                default:
                                }
                              }
                              TestExecutorService.submitTest(()->runTest(structType,checkedType,collectionType,queryVal,
                                  modification,castType,inputType,null,size,position));
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
        }
      }
      TestExecutorService.completeAllTests(testName);
    }
    @SuppressWarnings("unchecked") private void runTest(StructType structType,CheckedType checkedType,
        DataType collectionType,QueryVal queryVal,QueryVal.QueryValModification modification,QueryCastType castType,
        DataType inputType,MonitoredObjectGen monitoredObjectGen,int seqSize,double position){
      final var monitor=getMonitoredSequence(structType,checkedType,collectionType,seqSize);
      if(position < 0){
        switch(collectionType){
        case BOOLEAN:
          queryVal.initDoesNotContain((OmniCollection.OfBoolean)monitor.getCollection(),seqSize,0,modification);
          break;
        case BYTE:
          queryVal.initDoesNotContain((OmniCollection.OfByte)monitor.getCollection(),seqSize,0,modification);
          break;
        case CHAR:
          queryVal.initDoesNotContain((OmniCollection.OfChar)monitor.getCollection(),seqSize,0,modification);
          break;
        case DOUBLE:
          queryVal.initDoesNotContain((OmniCollection.OfDouble)monitor.getCollection(),seqSize,0,modification);
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
          queryVal.initDoesNotContain((OmniCollection.OfRef<Object>)monitor.getCollection(),seqSize,0,modification);
          break;
        case SHORT:
          queryVal.initDoesNotContain((OmniCollection.OfShort)monitor.getCollection(),seqSize,0,modification);
          break;
        default:
          throw collectionType.invalid();
        }
      }else{
        switch(collectionType){
        case BOOLEAN:
          queryVal.initContains((OmniCollection.OfBoolean)monitor.getCollection(),seqSize,0,position,modification);
          break;
        case BYTE:
          queryVal.initContains((OmniCollection.OfByte)monitor.getCollection(),seqSize,0,position,modification);
          break;
        case CHAR:
          queryVal.initContains((OmniCollection.OfChar)monitor.getCollection(),seqSize,0,position,modification);
          break;
        case DOUBLE:
          queryVal.initContains((OmniCollection.OfDouble)monitor.getCollection(),seqSize,0,position,modification);
          break;
        case FLOAT:
          queryVal.initContains((OmniCollection.OfFloat)monitor.getCollection(),seqSize,0,position,modification);
          break;
        case INT:
          queryVal.initContains((OmniCollection.OfInt)monitor.getCollection(),seqSize,0,position,modification);
          break;
        case LONG:
          queryVal.initContains((OmniCollection.OfLong)monitor.getCollection(),seqSize,0,position,modification);
          break;
        case REF:
          queryVal.initContains((MonitoredCollection<? extends OmniCollection.OfRef<Object>>)monitor,seqSize,0,position,
              modification,inputType,monitoredObjectGen);
          break;
        case SHORT:
          queryVal.initContains((OmniCollection.OfShort)monitor.getCollection(),seqSize,0,position,modification);
          break;
        default:
          throw collectionType.invalid();
        }
      }
      monitor.updateCollectionState();
      callAndVerifyResult((MONITOR)monitor,queryVal,inputType,castType,modification,monitoredObjectGen,position,
          seqSize);
    }
    void callAndVerifyResult(MONITOR monitor,QueryVal queryVal,DataType inputType,QueryCastType castType,
        QueryVal.QueryValModification modification,MonitoredObjectGen monitoredObjectGen,double position,int seqSize);
  }
  private static class SnglLnkQueueMonitor<SEQ extends AbstractSeq<E>&Externalizable&OmniQueue<E>,E>
      extends AbstractSnglLnkSeqMonitor<SEQ> implements MonitoredQueue<SEQ>{
    class ItrMonitor extends AbstractSnglLnkSeqMonitor<SEQ>.AbstractItrMonitor{
      ItrMonitor(){
        super(0);
      }
      @Override public int getNumLeft(){
        return expectedSize - expectedCurrIndex;
      }
      @Override public void verifyCloneHelper(Object clone){
        final var itr=this.itr;
        final var checked=checkedType.checked;
        switch(dataType){
        case BOOLEAN:
          Assertions.assertSame(expectedNext,FieldAndMethodAccessor.BooleanSnglLnkSeq.AbstractItr.next(itr));
          Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.BooleanSnglLnkSeq.AbstractItr.curr(itr));
          Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.BooleanSnglLnkSeq.AbstractItr.prev(itr));
          if(checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanSnglLnkSeq.CheckedQueue.Itr.parent(itr));
            Assertions.assertSame(expectedItrModCount,
                FieldAndMethodAccessor.BooleanSnglLnkSeq.CheckedQueue.Itr.modCount(itr));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanSnglLnkSeq.UncheckedQueue.Itr.parent(itr));
          }
          break;
        case BYTE:
          Assertions.assertSame(expectedNext,FieldAndMethodAccessor.ByteSnglLnkSeq.AbstractItr.next(itr));
          Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.ByteSnglLnkSeq.AbstractItr.curr(itr));
          Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.ByteSnglLnkSeq.AbstractItr.prev(itr));
          if(checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.ByteSnglLnkSeq.CheckedQueue.Itr.parent(itr));
            Assertions.assertSame(expectedItrModCount,
                FieldAndMethodAccessor.ByteSnglLnkSeq.CheckedQueue.Itr.modCount(itr));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.ByteSnglLnkSeq.UncheckedQueue.Itr.parent(itr));
          }
          break;
        case CHAR:
          Assertions.assertSame(expectedNext,FieldAndMethodAccessor.CharSnglLnkSeq.AbstractItr.next(itr));
          Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.CharSnglLnkSeq.AbstractItr.curr(itr));
          Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.CharSnglLnkSeq.AbstractItr.prev(itr));
          if(checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.CharSnglLnkSeq.CheckedQueue.Itr.parent(itr));
            Assertions.assertSame(expectedItrModCount,
                FieldAndMethodAccessor.CharSnglLnkSeq.CheckedQueue.Itr.modCount(itr));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.CharSnglLnkSeq.UncheckedQueue.Itr.parent(itr));
          }
          break;
        case DOUBLE:
          Assertions.assertSame(expectedNext,FieldAndMethodAccessor.DoubleSnglLnkSeq.AbstractItr.next(itr));
          Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.DoubleSnglLnkSeq.AbstractItr.curr(itr));
          Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.DoubleSnglLnkSeq.AbstractItr.prev(itr));
          if(checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleSnglLnkSeq.CheckedQueue.Itr.parent(itr));
            Assertions.assertSame(expectedItrModCount,
                FieldAndMethodAccessor.DoubleSnglLnkSeq.CheckedQueue.Itr.modCount(itr));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleSnglLnkSeq.UncheckedQueue.Itr.parent(itr));
          }
          break;
        case FLOAT:
          Assertions.assertSame(expectedNext,FieldAndMethodAccessor.FloatSnglLnkSeq.AbstractItr.next(itr));
          Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.FloatSnglLnkSeq.AbstractItr.curr(itr));
          Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.FloatSnglLnkSeq.AbstractItr.prev(itr));
          if(checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.FloatSnglLnkSeq.CheckedQueue.Itr.parent(itr));
            Assertions.assertSame(expectedItrModCount,
                FieldAndMethodAccessor.FloatSnglLnkSeq.CheckedQueue.Itr.modCount(itr));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.FloatSnglLnkSeq.UncheckedQueue.Itr.parent(itr));
          }
          break;
        case INT:
          Assertions.assertSame(expectedNext,FieldAndMethodAccessor.IntSnglLnkSeq.AbstractItr.next(itr));
          Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.IntSnglLnkSeq.AbstractItr.curr(itr));
          Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.IntSnglLnkSeq.AbstractItr.prev(itr));
          if(checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.IntSnglLnkSeq.CheckedQueue.Itr.parent(itr));
            Assertions.assertSame(expectedItrModCount,
                FieldAndMethodAccessor.IntSnglLnkSeq.CheckedQueue.Itr.modCount(itr));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.IntSnglLnkSeq.UncheckedQueue.Itr.parent(itr));
          }
          break;
        case LONG:
          Assertions.assertSame(expectedNext,FieldAndMethodAccessor.LongSnglLnkSeq.AbstractItr.next(itr));
          Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.LongSnglLnkSeq.AbstractItr.curr(itr));
          Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.LongSnglLnkSeq.AbstractItr.prev(itr));
          if(checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.LongSnglLnkSeq.CheckedQueue.Itr.parent(itr));
            Assertions.assertSame(expectedItrModCount,
                FieldAndMethodAccessor.LongSnglLnkSeq.CheckedQueue.Itr.modCount(itr));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.LongSnglLnkSeq.UncheckedQueue.Itr.parent(itr));
          }
          break;
        case REF:
          Assertions.assertSame(expectedNext,FieldAndMethodAccessor.RefSnglLnkSeq.AbstractItr.next(itr));
          Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.RefSnglLnkSeq.AbstractItr.curr(itr));
          Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.RefSnglLnkSeq.AbstractItr.prev(itr));
          if(checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.RefSnglLnkSeq.CheckedQueue.Itr.parent(itr));
            Assertions.assertSame(expectedItrModCount,
                FieldAndMethodAccessor.RefSnglLnkSeq.CheckedQueue.Itr.modCount(itr));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.RefSnglLnkSeq.UncheckedQueue.Itr.parent(itr));
          }
          break;
        case SHORT:
          Assertions.assertSame(expectedNext,FieldAndMethodAccessor.ShortSnglLnkSeq.AbstractItr.next(itr));
          Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.ShortSnglLnkSeq.AbstractItr.curr(itr));
          Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.ShortSnglLnkSeq.AbstractItr.prev(itr));
          if(checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.ShortSnglLnkSeq.CheckedQueue.Itr.parent(itr));
            Assertions.assertSame(expectedItrModCount,
                FieldAndMethodAccessor.ShortSnglLnkSeq.CheckedQueue.Itr.modCount(itr));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.ShortSnglLnkSeq.UncheckedQueue.Itr.parent(itr));
          }
          break;
        default:
          throw dataType.invalid();
        }
      }
      @Override public void verifyForEachRemaining(MonitoredFunction function){
        final int expectedCurrIndex=this.expectedCurrIndex;
        final int expectedSize=SnglLnkQueueMonitor.this.expectedSize;
        if(expectedCurrIndex < expectedSize){
          super.verifyForEachRemainingHelper(function);
          expectedLastRetIndex=expectedSize - 1;
          this.expectedCurrIndex=expectedSize;
        }
      }
      @Override public void verifyNextResult(DataType outputType,Object result){
          verifyGetResult(expectedCurrIndex,result,outputType);
      }
      @Override void updateItrNextIndex(){
        expectedLastRetIndex=expectedCurrIndex++;
      }
    }
    SnglLnkQueueMonitor(CheckedType checkedType,DataType dataType){
      super(checkedType,dataType);
    }
    SnglLnkQueueMonitor(CheckedType checkedType,DataType dataType,int initCap){
      super(checkedType,dataType,initCap);
    }
    @Override public MonitoredIterator<? extends OmniIterator<?>,SEQ> getMonitoredIterator(){
      return new ItrMonitor();
    }
    
    @Override public StructType getStructType(){
      return StructType.SnglLnkQueue;
    }
    @Override public void modCollection(){
      switch(dataType){
      case BOOLEAN:
        ++((BooleanSnglLnkSeq.CheckedQueue)seq).modCount;
        break;
      case BYTE:
        ++((ByteSnglLnkSeq.CheckedQueue)seq).modCount;
        break;
      case CHAR:
        ++((CharSnglLnkSeq.CheckedQueue)seq).modCount;
        break;
      case DOUBLE:
        ++((DoubleSnglLnkSeq.CheckedQueue)seq).modCount;
        break;
      case FLOAT:
        ++((FloatSnglLnkSeq.CheckedQueue)seq).modCount;
        break;
      case INT:
        ++((IntSnglLnkSeq.CheckedQueue)seq).modCount;
        break;
      case LONG:
        ++((LongSnglLnkSeq.CheckedQueue)seq).modCount;
        break;
      case REF:
        ++((RefSnglLnkSeq.CheckedQueue<?>)seq).modCount;
        break;
      case SHORT:
        ++((ShortSnglLnkSeq.CheckedQueue)seq).modCount;
        break;
      default:
        throw dataType.invalid();
      }
      ++expectedModCount;
    }
    @Override public Object removeFirst(){
      final var removed=seq.remove();
      super.updateRemoveIndexState(0);
      return removed;
    }
    @Override public Object verifyElement(DataType outputType){
      Object result;
      Object expected=null;
      if(expectedSize != 0){
        expected=super.get(0,outputType);
      }
      try{
        result=outputType.callElement(seq);
      }finally{
        verifyCollectionState();
      }
      if(outputType == DataType.REF && dataType == DataType.REF){
        Assertions.assertSame(expected,result);
      }else{
        Assertions.assertEquals(expected,result);
      }
      return result;
    }
    @Override public boolean verifyOffer(Object inputVal,DataType inputType,FunctionCallType functionCallType){
      final boolean result=inputType.callOffer(inputVal,seq,functionCallType);
      if(result){
        super.updateAddState(expectedSize,inputVal,inputType);
      }
      verifyCollectionState();
      return result;
    }
    @Override public Object verifyPeek(DataType outputType){
      Object result;
      final boolean isEmpty=expectedSize == 0;
      try{
        result=outputType.callPeek(seq);
      }finally{
        verifyCollectionState();
      }
      if(isEmpty){
        Assertions.assertEquals(outputType.defaultVal,result);
      }else{
        super.verifyGetResult(0,result,outputType);
      }
      return result;
    }
    @Override public Object verifyPoll(DataType outputType){
      Object result;
      final Object expected=outputType.callPeek(seq);
      final boolean isEmpty=expectedSize == 0;
      try{
        result=outputType.callPoll(seq);
        if(!isEmpty){
          super.updateRemoveIndexState(0);
        }
      }finally{
        verifyCollectionState();
      }
      Assertions.assertEquals(expected,result);
      return result;
    }
    @Override public Object verifyRemove(DataType outputType){
      Object result;
      Object expected=null;
      if(expectedSize != 0){
        expected=super.get(0,outputType);
      }
      try{
        result=outputType.callRemove(seq);
        super.updateRemoveIndexState(0);
      }finally{
        verifyCollectionState();
      }
      if(outputType == DataType.REF && dataType == DataType.REF){
        Assertions.assertSame(expected,result);
      }else{
        Assertions.assertEquals(expected,result);
      }
      return result;
    }
    @Override void copyBooleanListContents(){
      final int expectedSize=seq.size;
      final var cast=(BooleanSnglLnkSeq)seq;
      var expectedArr=(boolean[])this.expectedArr;
      int expectedCapacity;
      if((expectedCapacity=this.expectedCapacity) < expectedSize){
        this.expectedArr=expectedArr=new boolean[this.expectedCapacity
            =expectedArr == OmniArray.OfBoolean.DEFAULT_ARR && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP
                ?OmniArray.DEFAULT_ARR_SEQ_CAP:OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
      }
      var currNode=cast.head;
      int i=0;
      while(currNode != null){
        expectedArr[i]=currNode.val;
        currNode=currNode.next;
        ++i;
      }
    }
    @Override void copyByteListContents(){
      final int expectedSize=seq.size;
      final var cast=(ByteSnglLnkSeq)seq;
      var expectedArr=(byte[])this.expectedArr;
      int expectedCapacity;
      if((expectedCapacity=this.expectedCapacity) < expectedSize){
        this.expectedArr=expectedArr=new byte[this.expectedCapacity
            =expectedArr == OmniArray.OfByte.DEFAULT_ARR && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP
                ?OmniArray.DEFAULT_ARR_SEQ_CAP:OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
      }
      var currNode=cast.head;
      int i=0;
      while(currNode != null){
        expectedArr[i]=currNode.val;
        currNode=currNode.next;
        ++i;
      }
    }
    @Override void copyCharListContents(){
      final int expectedSize=seq.size;
      final var cast=(CharSnglLnkSeq)seq;
      var expectedArr=(char[])this.expectedArr;
      int expectedCapacity;
      if((expectedCapacity=this.expectedCapacity) < expectedSize){
        this.expectedArr=expectedArr=new char[this.expectedCapacity
            =expectedArr == OmniArray.OfChar.DEFAULT_ARR && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP
                ?OmniArray.DEFAULT_ARR_SEQ_CAP:OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
      }
      var currNode=cast.head;
      int i=0;
      while(currNode != null){
        expectedArr[i]=currNode.val;
        currNode=currNode.next;
        ++i;
      }
    }
    @Override void copyDoubleListContents(){
      final int expectedSize=seq.size;
      final var cast=(DoubleSnglLnkSeq)seq;
      var expectedArr=(double[])this.expectedArr;
      int expectedCapacity;
      if((expectedCapacity=this.expectedCapacity) < expectedSize){
        this.expectedArr=expectedArr=new double[this.expectedCapacity
            =expectedArr == OmniArray.OfDouble.DEFAULT_ARR && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP
                ?OmniArray.DEFAULT_ARR_SEQ_CAP:OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
      }
      var currNode=cast.head;
      int i=0;
      while(currNode != null){
        expectedArr[i]=currNode.val;
        currNode=currNode.next;
        ++i;
      }
    }
    @Override void copyFloatListContents(){
      final int expectedSize=seq.size;
      final var cast=(FloatSnglLnkSeq)seq;
      var expectedArr=(float[])this.expectedArr;
      int expectedCapacity;
      if((expectedCapacity=this.expectedCapacity) < expectedSize){
        this.expectedArr=expectedArr=new float[this.expectedCapacity
            =expectedArr == OmniArray.OfFloat.DEFAULT_ARR && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP
                ?OmniArray.DEFAULT_ARR_SEQ_CAP:OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
      }
      var currNode=cast.head;
      int i=0;
      while(currNode != null){
        expectedArr[i]=currNode.val;
        currNode=currNode.next;
        ++i;
      }
    }
    @Override void copyIntListContents(){
      final int expectedSize=seq.size;
      final var cast=(IntSnglLnkSeq)seq;
      var expectedArr=(int[])this.expectedArr;
      int expectedCapacity;
      if((expectedCapacity=this.expectedCapacity) < expectedSize){
        this.expectedArr=expectedArr=new int[this.expectedCapacity
            =expectedArr == OmniArray.OfInt.DEFAULT_ARR && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP
                ?OmniArray.DEFAULT_ARR_SEQ_CAP:OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
      }
      var currNode=cast.head;
      int i=0;
      while(currNode != null){
        expectedArr[i]=currNode.val;
        currNode=currNode.next;
        ++i;
      }
    }
    @Override void copyLongListContents(){
      final int expectedSize=seq.size;
      final var cast=(LongSnglLnkSeq)seq;
      var expectedArr=(long[])this.expectedArr;
      int expectedCapacity;
      if((expectedCapacity=this.expectedCapacity) < expectedSize){
        this.expectedArr=expectedArr=new long[this.expectedCapacity
            =expectedArr == OmniArray.OfLong.DEFAULT_ARR && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP
                ?OmniArray.DEFAULT_ARR_SEQ_CAP:OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
      }
      var currNode=cast.head;
      int i=0;
      while(currNode != null){
        expectedArr[i]=currNode.val;
        currNode=currNode.next;
        ++i;
      }
    }
    @Override void copyRefListContents(){
      final int expectedSize=seq.size;
      final var cast=(RefSnglLnkSeq<?>)seq;
      var expectedArr=(Object[])this.expectedArr;
      int expectedCapacity;
      if((expectedCapacity=this.expectedCapacity) < expectedSize){
        this.expectedArr=expectedArr=new Object[this.expectedCapacity
            =expectedArr == OmniArray.OfRef.DEFAULT_ARR && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP
                ?OmniArray.DEFAULT_ARR_SEQ_CAP:OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
      }
      var currNode=cast.head;
      int i=0;
      while(currNode != null){
        expectedArr[i]=currNode.val;
        currNode=currNode.next;
        ++i;
      }
    }
    @Override void copyShortListContents(){
      final int expectedSize=seq.size;
      final var cast=(ShortSnglLnkSeq)seq;
      var expectedArr=(short[])this.expectedArr;
      int expectedCapacity;
      if((expectedCapacity=this.expectedCapacity) < expectedSize){
        this.expectedArr=expectedArr=new short[this.expectedCapacity
            =expectedArr == OmniArray.OfShort.DEFAULT_ARR && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP
                ?OmniArray.DEFAULT_ARR_SEQ_CAP:OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
      }
      var currNode=cast.head;
      int i=0;
      while(currNode != null){
        expectedArr[i]=currNode.val;
        currNode=currNode.next;
        ++i;
      }
    }
    @SuppressWarnings("unchecked") @Override SEQ initSeq(){
      final var checked=checkedType.checked;
      switch(dataType){
      case BOOLEAN:
        if(checked){ return (SEQ)new BooleanSnglLnkSeq.CheckedQueue(); }
        return (SEQ)new BooleanSnglLnkSeq.UncheckedQueue();
      case BYTE:
        if(checked){ return (SEQ)new ByteSnglLnkSeq.CheckedQueue(); }
        return (SEQ)new ByteSnglLnkSeq.UncheckedQueue();
      case CHAR:
        if(checked){ return (SEQ)new CharSnglLnkSeq.CheckedQueue(); }
        return (SEQ)new CharSnglLnkSeq.UncheckedQueue();
      case DOUBLE:
        if(checked){ return (SEQ)new DoubleSnglLnkSeq.CheckedQueue(); }
        return (SEQ)new DoubleSnglLnkSeq.UncheckedQueue();
      case FLOAT:
        if(checked){ return (SEQ)new FloatSnglLnkSeq.CheckedQueue(); }
        return (SEQ)new FloatSnglLnkSeq.UncheckedQueue();
      case INT:
        if(checked){ return (SEQ)new IntSnglLnkSeq.CheckedQueue(); }
        return (SEQ)new IntSnglLnkSeq.UncheckedQueue();
      case LONG:
        if(checked){ return (SEQ)new LongSnglLnkSeq.CheckedQueue(); }
        return (SEQ)new LongSnglLnkSeq.UncheckedQueue();
      case REF:
        if(checked){ return (SEQ)new RefSnglLnkSeq.CheckedQueue<>(); }
        return (SEQ)new RefSnglLnkSeq.UncheckedQueue<>();
      case SHORT:
        if(checked){ return (SEQ)new ShortSnglLnkSeq.CheckedQueue(); }
        return (SEQ)new ShortSnglLnkSeq.UncheckedQueue();
      default:
        throw dataType.invalid();
      }
    }
    @Override void updateModCount(){
      switch(dataType){
      case BOOLEAN:
        expectedModCount=((BooleanSnglLnkSeq.CheckedQueue)seq).modCount;
        break;
      case BYTE:
        expectedModCount=((ByteSnglLnkSeq.CheckedQueue)seq).modCount;
        break;
      case CHAR:
        expectedModCount=((CharSnglLnkSeq.CheckedQueue)seq).modCount;
        break;
      case DOUBLE:
        expectedModCount=((DoubleSnglLnkSeq.CheckedQueue)seq).modCount;
        break;
      case FLOAT:
        expectedModCount=((FloatSnglLnkSeq.CheckedQueue)seq).modCount;
        break;
      case INT:
        expectedModCount=((IntSnglLnkSeq.CheckedQueue)seq).modCount;
        break;
      case LONG:
        expectedModCount=((LongSnglLnkSeq.CheckedQueue)seq).modCount;
        break;
      case REF:
        expectedModCount=((RefSnglLnkSeq.CheckedQueue<?>)seq).modCount;
        break;
      case SHORT:
        expectedModCount=((ShortSnglLnkSeq.CheckedQueue)seq).modCount;
        break;
      default:
        throw dataType.invalid();
      }
    }
    @Override void verifyBooleanClone(Object clone){
      Assertions.assertNotSame(clone,seq);
      final var cloneCast=(BooleanSnglLnkSeq.UncheckedQueue)clone;
      int size;
      Assertions.assertEquals(size=seq.size,cloneCast.size);
      Assertions.assertEquals(checkedType.checked,cloneCast instanceof BooleanSnglLnkSeq.CheckedQueue);
      if(checkedType.checked){
        Assertions.assertEquals(0,((BooleanSnglLnkSeq.CheckedQueue)cloneCast).modCount);
      }else{
        Assertions.assertTrue(cloneCast instanceof BooleanSnglLnkSeq.UncheckedQueue);
      }
      var cloneHead=cloneCast.head;
      var thisHead=((BooleanSnglLnkSeq)seq).head;
      if(size > 0){
        for(;;){
          Assertions.assertNotSame(cloneHead,thisHead);
          Assertions.assertEquals(cloneHead.val,thisHead.val);
          if(--size == 0){
            break;
          }
          cloneHead=cloneHead.next;
          thisHead=thisHead.next;
        }
        Assertions.assertSame(cloneHead,cloneCast.tail);
        cloneHead=cloneHead.next;
        thisHead=thisHead.next;
      }
      Assertions.assertNull(cloneHead);
      Assertions.assertNull(thisHead);
    }
    @Override void verifyBooleanIntegrity(){
      int expectedSize;
      Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
      final var cast=(BooleanSnglLnkSeq.UncheckedQueue)seq;
      final var expectedArr=(boolean[])this.expectedArr;
      if(checkedType.checked){
        Assertions.assertEquals(expectedModCount,((BooleanSnglLnkSeq.CheckedQueue)cast).modCount);
      }
      var headNode=cast.head;
      if(expectedSize > 0){
        for(int i=0,bound=expectedSize - 1;;++i){
          Assertions.assertEquals(expectedArr[i],headNode.val);
          if(i == bound){
            break;
          }
          headNode=headNode.next;
        }
        Assertions.assertSame(headNode,cast.tail);
        headNode=headNode.next;
      }
      Assertions.assertNull(headNode);
    }
    @Override void verifyByteClone(Object clone){
      Assertions.assertNotSame(clone,seq);
      final var cloneCast=(ByteSnglLnkSeq.UncheckedQueue)clone;
      int size;
      Assertions.assertEquals(size=seq.size,cloneCast.size);
      Assertions.assertEquals(checkedType.checked,cloneCast instanceof ByteSnglLnkSeq.CheckedQueue);
      if(checkedType.checked){
        Assertions.assertEquals(0,((ByteSnglLnkSeq.CheckedQueue)cloneCast).modCount);
      }else{
        Assertions.assertTrue(cloneCast instanceof ByteSnglLnkSeq.UncheckedQueue);
      }
      var cloneHead=cloneCast.head;
      var thisHead=((ByteSnglLnkSeq)seq).head;
      if(size > 0){
        for(;;){
          Assertions.assertNotSame(cloneHead,thisHead);
          Assertions.assertEquals(cloneHead.val,thisHead.val);
          if(--size == 0){
            break;
          }
          cloneHead=cloneHead.next;
          thisHead=thisHead.next;
        }
        Assertions.assertSame(cloneHead,cloneCast.tail);
        cloneHead=cloneHead.next;
        thisHead=thisHead.next;
      }
      Assertions.assertNull(cloneHead);
      Assertions.assertNull(thisHead);
    }
    @Override void verifyByteIntegrity(){
      int expectedSize;
      Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
      final var cast=(ByteSnglLnkSeq.UncheckedQueue)seq;
      final var expectedArr=(byte[])this.expectedArr;
      if(checkedType.checked){
        Assertions.assertEquals(expectedModCount,((ByteSnglLnkSeq.CheckedQueue)cast).modCount);
      }
      var headNode=cast.head;
      if(expectedSize > 0){
        for(int i=0,bound=expectedSize - 1;;++i){
          Assertions.assertEquals(expectedArr[i],headNode.val);
          if(i == bound){
            break;
          }
          headNode=headNode.next;
        }
        Assertions.assertSame(headNode,cast.tail);
        headNode=headNode.next;
      }
      Assertions.assertNull(headNode);
    }
    @Override void verifyCharClone(Object clone){
      Assertions.assertNotSame(clone,seq);
      final var cloneCast=(CharSnglLnkSeq.UncheckedQueue)clone;
      int size;
      Assertions.assertEquals(size=seq.size,cloneCast.size);
      Assertions.assertEquals(checkedType.checked,cloneCast instanceof CharSnglLnkSeq.CheckedQueue);
      if(checkedType.checked){
        Assertions.assertEquals(0,((CharSnglLnkSeq.CheckedQueue)cloneCast).modCount);
      }else{
        Assertions.assertTrue(cloneCast instanceof CharSnglLnkSeq.UncheckedQueue);
      }
      var cloneHead=cloneCast.head;
      var thisHead=((CharSnglLnkSeq)seq).head;
      if(size > 0){
        for(;;){
          Assertions.assertNotSame(cloneHead,thisHead);
          Assertions.assertEquals(cloneHead.val,thisHead.val);
          if(--size == 0){
            break;
          }
          cloneHead=cloneHead.next;
          thisHead=thisHead.next;
        }
        Assertions.assertSame(cloneHead,cloneCast.tail);
        cloneHead=cloneHead.next;
        thisHead=thisHead.next;
      }
      Assertions.assertNull(cloneHead);
      Assertions.assertNull(thisHead);
    }
    @Override void verifyCharIntegrity(){
      int expectedSize;
      Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
      final var cast=(CharSnglLnkSeq.UncheckedQueue)seq;
      final var expectedArr=(char[])this.expectedArr;
      if(checkedType.checked){
        Assertions.assertEquals(expectedModCount,((CharSnglLnkSeq.CheckedQueue)cast).modCount);
      }
      var headNode=cast.head;
      if(expectedSize > 0){
        for(int i=0,bound=expectedSize - 1;;++i){
          Assertions.assertEquals(expectedArr[i],headNode.val);
          if(i == bound){
            break;
          }
          headNode=headNode.next;
        }
        Assertions.assertSame(headNode,cast.tail);
        headNode=headNode.next;
      }
      Assertions.assertNull(headNode);
    }
    @Override void verifyDoubleClone(Object clone){
      Assertions.assertNotSame(clone,seq);
      final var cloneCast=(DoubleSnglLnkSeq.UncheckedQueue)clone;
      int size;
      Assertions.assertEquals(size=seq.size,cloneCast.size);
      Assertions.assertEquals(checkedType.checked,cloneCast instanceof DoubleSnglLnkSeq.CheckedQueue);
      if(checkedType.checked){
        Assertions.assertEquals(0,((DoubleSnglLnkSeq.CheckedQueue)cloneCast).modCount);
      }else{
        Assertions.assertTrue(cloneCast instanceof DoubleSnglLnkSeq.UncheckedQueue);
      }
      var cloneHead=cloneCast.head;
      var thisHead=((DoubleSnglLnkSeq)seq).head;
      if(size > 0){
        for(;;){
          Assertions.assertNotSame(cloneHead,thisHead);
          Assertions.assertEquals(cloneHead.val,thisHead.val);
          if(--size == 0){
            break;
          }
          cloneHead=cloneHead.next;
          thisHead=thisHead.next;
        }
        Assertions.assertSame(cloneHead,cloneCast.tail);
        cloneHead=cloneHead.next;
        thisHead=thisHead.next;
      }
      Assertions.assertNull(cloneHead);
      Assertions.assertNull(thisHead);
    }
    @Override void verifyDoubleIntegrity(){
      int expectedSize;
      Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
      final var cast=(DoubleSnglLnkSeq.UncheckedQueue)seq;
      final var expectedArr=(double[])this.expectedArr;
      if(checkedType.checked){
        Assertions.assertEquals(expectedModCount,((DoubleSnglLnkSeq.CheckedQueue)cast).modCount);
      }
      var headNode=cast.head;
      if(expectedSize > 0){
        for(int i=0,bound=expectedSize - 1;;++i){
          Assertions.assertEquals(expectedArr[i],headNode.val);
          if(i == bound){
            break;
          }
          headNode=headNode.next;
        }
        Assertions.assertSame(headNode,cast.tail);
        headNode=headNode.next;
      }
      Assertions.assertNull(headNode);
    }
    @Override void verifyFloatClone(Object clone){
      Assertions.assertNotSame(clone,seq);
      final var cloneCast=(FloatSnglLnkSeq.UncheckedQueue)clone;
      int size;
      Assertions.assertEquals(size=seq.size,cloneCast.size);
      Assertions.assertEquals(checkedType.checked,cloneCast instanceof FloatSnglLnkSeq.CheckedQueue);
      if(checkedType.checked){
        Assertions.assertEquals(0,((FloatSnglLnkSeq.CheckedQueue)cloneCast).modCount);
      }else{
        Assertions.assertTrue(cloneCast instanceof FloatSnglLnkSeq.UncheckedQueue);
      }
      var cloneHead=cloneCast.head;
      var thisHead=((FloatSnglLnkSeq)seq).head;
      if(size > 0){
        for(;;){
          Assertions.assertNotSame(cloneHead,thisHead);
          Assertions.assertEquals(cloneHead.val,thisHead.val);
          if(--size == 0){
            break;
          }
          cloneHead=cloneHead.next;
          thisHead=thisHead.next;
        }
        Assertions.assertSame(cloneHead,cloneCast.tail);
        cloneHead=cloneHead.next;
        thisHead=thisHead.next;
      }
      Assertions.assertNull(cloneHead);
      Assertions.assertNull(thisHead);
    }
    @Override void verifyFloatIntegrity(){
      int expectedSize;
      Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
      final var cast=(FloatSnglLnkSeq.UncheckedQueue)seq;
      final var expectedArr=(float[])this.expectedArr;
      if(checkedType.checked){
        Assertions.assertEquals(expectedModCount,((FloatSnglLnkSeq.CheckedQueue)cast).modCount);
      }
      var headNode=cast.head;
      if(expectedSize > 0){
        for(int i=0,bound=expectedSize - 1;;++i){
          Assertions.assertEquals(expectedArr[i],headNode.val);
          if(i == bound){
            break;
          }
          headNode=headNode.next;
        }
        Assertions.assertSame(headNode,cast.tail);
        headNode=headNode.next;
      }
      Assertions.assertNull(headNode);
    }
    @Override void verifyIntClone(Object clone){
      Assertions.assertNotSame(clone,seq);
      final var cloneCast=(IntSnglLnkSeq.UncheckedQueue)clone;
      int size;
      Assertions.assertEquals(size=seq.size,cloneCast.size);
      Assertions.assertEquals(checkedType.checked,cloneCast instanceof IntSnglLnkSeq.CheckedQueue);
      if(checkedType.checked){
        Assertions.assertEquals(0,((IntSnglLnkSeq.CheckedQueue)cloneCast).modCount);
      }else{
        Assertions.assertTrue(cloneCast instanceof IntSnglLnkSeq.UncheckedQueue);
      }
      var cloneHead=cloneCast.head;
      var thisHead=((IntSnglLnkSeq)seq).head;
      if(size > 0){
        for(;;){
          Assertions.assertNotSame(cloneHead,thisHead);
          Assertions.assertEquals(cloneHead.val,thisHead.val);
          if(--size == 0){
            break;
          }
          cloneHead=cloneHead.next;
          thisHead=thisHead.next;
        }
        Assertions.assertSame(cloneHead,cloneCast.tail);
        cloneHead=cloneHead.next;
        thisHead=thisHead.next;
      }
      Assertions.assertNull(cloneHead);
      Assertions.assertNull(thisHead);
    }
    @Override void verifyIntIntegrity(){
      int expectedSize;
      Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
      final var cast=(IntSnglLnkSeq.UncheckedQueue)seq;
      final var expectedArr=(int[])this.expectedArr;
      if(checkedType.checked){
        Assertions.assertEquals(expectedModCount,((IntSnglLnkSeq.CheckedQueue)cast).modCount);
      }
      var headNode=cast.head;
      if(expectedSize > 0){
        for(int i=0,bound=expectedSize - 1;;++i){
          Assertions.assertEquals(expectedArr[i],headNode.val);
          if(i == bound){
            break;
          }
          headNode=headNode.next;
        }
        Assertions.assertSame(headNode,cast.tail);
        headNode=headNode.next;
      }
      Assertions.assertNull(headNode);
    }
    @Override void verifyLongClone(Object clone){
      Assertions.assertNotSame(clone,seq);
      final var cloneCast=(LongSnglLnkSeq.UncheckedQueue)clone;
      int size;
      Assertions.assertEquals(size=seq.size,cloneCast.size);
      Assertions.assertEquals(checkedType.checked,cloneCast instanceof LongSnglLnkSeq.CheckedQueue);
      if(checkedType.checked){
        Assertions.assertEquals(0,((LongSnglLnkSeq.CheckedQueue)cloneCast).modCount);
      }else{
        Assertions.assertTrue(cloneCast instanceof LongSnglLnkSeq.UncheckedQueue);
      }
      var cloneHead=cloneCast.head;
      var thisHead=((LongSnglLnkSeq)seq).head;
      if(size > 0){
        for(;;){
          Assertions.assertNotSame(cloneHead,thisHead);
          Assertions.assertEquals(cloneHead.val,thisHead.val);
          if(--size == 0){
            break;
          }
          cloneHead=cloneHead.next;
          thisHead=thisHead.next;
        }
        Assertions.assertSame(cloneHead,cloneCast.tail);
        cloneHead=cloneHead.next;
        thisHead=thisHead.next;
      }
      Assertions.assertNull(cloneHead);
      Assertions.assertNull(thisHead);
    }
    @Override void verifyLongIntegrity(){
      int expectedSize;
      Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
      final var cast=(LongSnglLnkSeq.UncheckedQueue)seq;
      final var expectedArr=(long[])this.expectedArr;
      if(checkedType.checked){
        Assertions.assertEquals(expectedModCount,((LongSnglLnkSeq.CheckedQueue)cast).modCount);
      }
      var headNode=cast.head;
      if(expectedSize > 0){
        for(int i=0,bound=expectedSize - 1;;++i){
          Assertions.assertEquals(expectedArr[i],headNode.val);
          if(i == bound){
            break;
          }
          headNode=headNode.next;
        }
        Assertions.assertSame(headNode,cast.tail);
        headNode=headNode.next;
      }
      Assertions.assertNull(headNode);
    }
    @Override void verifyRefClone(Object clone,boolean refIsSame){
      Assertions.assertNotSame(clone,seq);
      final var cloneCast=(RefSnglLnkSeq.UncheckedQueue<?>)clone;
      int size;
      Assertions.assertEquals(size=seq.size,cloneCast.size);
      Assertions.assertEquals(checkedType.checked,cloneCast instanceof RefSnglLnkSeq.CheckedQueue);
      if(checkedType.checked){
        Assertions.assertEquals(0,((RefSnglLnkSeq.CheckedQueue<?>)cloneCast).modCount);
      }else{
        Assertions.assertTrue(cloneCast instanceof RefSnglLnkSeq.UncheckedQueue);
      }
      var cloneHead=cloneCast.head;
      var thisHead=((RefSnglLnkSeq<?>)seq).head;
      if(size > 0){
        if(refIsSame){
          for(;;){
            Assertions.assertNotSame(cloneHead,thisHead);
            Assertions.assertSame(cloneHead.val,thisHead.val);
            if(--size == 0){
              break;
            }
            cloneHead=cloneHead.next;
            thisHead=thisHead.next;
          }
        }else{
          for(;;){
            Assertions.assertNotSame(cloneHead,thisHead);
            Assertions.assertEquals(cloneHead.val,thisHead.val);
            if(--size == 0){
              break;
            }
            cloneHead=cloneHead.next;
            thisHead=thisHead.next;
          }
        }
        Assertions.assertSame(cloneHead,cloneCast.tail);
        cloneHead=cloneHead.next;
        thisHead=thisHead.next;
      }
      Assertions.assertNull(cloneHead);
      Assertions.assertNull(thisHead);
    }
    @Override void verifyRefIntegrity(){
      int expectedSize;
      Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
      final var cast=(RefSnglLnkSeq.UncheckedQueue<?>)seq;
      final var expectedArr=(Object[])this.expectedArr;
      if(checkedType.checked){
        Assertions.assertEquals(expectedModCount,((RefSnglLnkSeq.CheckedQueue<?>)cast).modCount);
      }
      var headNode=cast.head;
      if(expectedSize > 0){
        for(int i=0,bound=expectedSize - 1;;++i){
          Assertions.assertSame(expectedArr[i],headNode.val);
          if(i == bound){
            break;
          }
          headNode=headNode.next;
        }
        Assertions.assertSame(headNode,cast.tail);
        headNode=headNode.next;
      }
      Assertions.assertNull(headNode);
    }
    @Override void verifyShortClone(Object clone){
      Assertions.assertNotSame(clone,seq);
      final var cloneCast=(ShortSnglLnkSeq.UncheckedQueue)clone;
      int size;
      Assertions.assertEquals(size=seq.size,cloneCast.size);
      Assertions.assertEquals(checkedType.checked,cloneCast instanceof ShortSnglLnkSeq.CheckedQueue);
      if(checkedType.checked){
        Assertions.assertEquals(0,((ShortSnglLnkSeq.CheckedQueue)cloneCast).modCount);
      }else{
        Assertions.assertTrue(cloneCast instanceof ShortSnglLnkSeq.UncheckedQueue);
      }
      var cloneHead=cloneCast.head;
      var thisHead=((ShortSnglLnkSeq)seq).head;
      if(size > 0){
        for(;;){
          Assertions.assertNotSame(cloneHead,thisHead);
          Assertions.assertEquals(cloneHead.val,thisHead.val);
          if(--size == 0){
            break;
          }
          cloneHead=cloneHead.next;
          thisHead=thisHead.next;
        }
        Assertions.assertSame(cloneHead,cloneCast.tail);
        cloneHead=cloneHead.next;
        thisHead=thisHead.next;
      }
      Assertions.assertNull(cloneHead);
      Assertions.assertNull(thisHead);
    }
    @Override void verifyShortIntegrity(){
      int expectedSize;
      Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
      final var cast=(ShortSnglLnkSeq.UncheckedQueue)seq;
      final var expectedArr=(short[])this.expectedArr;
      if(checkedType.checked){
        Assertions.assertEquals(expectedModCount,((ShortSnglLnkSeq.CheckedQueue)cast).modCount);
      }
      var headNode=cast.head;
      if(expectedSize > 0){
        for(int i=0,bound=expectedSize - 1;;++i){
          Assertions.assertEquals(expectedArr[i],headNode.val);
          if(i == bound){
            break;
          }
          headNode=headNode.next;
        }
        Assertions.assertSame(headNode,cast.tail);
        headNode=headNode.next;
      }
      Assertions.assertNull(headNode);
    }
  }
  private static class SnglLnkStackMonitor<SEQ extends AbstractSeq<E>&Externalizable&OmniStack<E>,E>
      extends AbstractSnglLnkSeqMonitor<SEQ> implements MonitoredStack<SEQ>{
    class ItrMonitor extends AbstractSnglLnkSeqMonitor<SEQ>.AbstractItrMonitor{
      ItrMonitor(){
        super(expectedSize);
      }
      @Override public int getNumLeft(){
        return expectedCurrIndex;
      }
      @Override public void verifyCloneHelper(Object clone){
        final var checked=checkedType.checked;
        final var itr=this.itr;
        switch(dataType){
        case BOOLEAN:
          Assertions.assertSame(expectedNext,FieldAndMethodAccessor.BooleanSnglLnkSeq.AbstractItr.next(itr));
          Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.BooleanSnglLnkSeq.AbstractItr.curr(itr));
          Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.BooleanSnglLnkSeq.AbstractItr.prev(itr));
          if(checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanSnglLnkSeq.CheckedStack.Itr.parent(itr));
            Assertions.assertSame(expectedItrModCount,
                FieldAndMethodAccessor.BooleanSnglLnkSeq.CheckedStack.Itr.modCount(itr));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanSnglLnkSeq.UncheckedStack.Itr.parent(itr));
          }
          break;
        case BYTE:
          Assertions.assertSame(expectedNext,FieldAndMethodAccessor.ByteSnglLnkSeq.AbstractItr.next(itr));
          Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.ByteSnglLnkSeq.AbstractItr.curr(itr));
          Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.ByteSnglLnkSeq.AbstractItr.prev(itr));
          if(checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.ByteSnglLnkSeq.CheckedStack.Itr.parent(itr));
            Assertions.assertSame(expectedItrModCount,
                FieldAndMethodAccessor.ByteSnglLnkSeq.CheckedStack.Itr.modCount(itr));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.ByteSnglLnkSeq.UncheckedStack.Itr.parent(itr));
          }
          break;
        case CHAR:
          Assertions.assertSame(expectedNext,FieldAndMethodAccessor.CharSnglLnkSeq.AbstractItr.next(itr));
          Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.CharSnglLnkSeq.AbstractItr.curr(itr));
          Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.CharSnglLnkSeq.AbstractItr.prev(itr));
          if(checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.CharSnglLnkSeq.CheckedStack.Itr.parent(itr));
            Assertions.assertSame(expectedItrModCount,
                FieldAndMethodAccessor.CharSnglLnkSeq.CheckedStack.Itr.modCount(itr));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.CharSnglLnkSeq.UncheckedStack.Itr.parent(itr));
          }
          break;
        case DOUBLE:
          Assertions.assertSame(expectedNext,FieldAndMethodAccessor.DoubleSnglLnkSeq.AbstractItr.next(itr));
          Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.DoubleSnglLnkSeq.AbstractItr.curr(itr));
          Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.DoubleSnglLnkSeq.AbstractItr.prev(itr));
          if(checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleSnglLnkSeq.CheckedStack.Itr.parent(itr));
            Assertions.assertSame(expectedItrModCount,
                FieldAndMethodAccessor.DoubleSnglLnkSeq.CheckedStack.Itr.modCount(itr));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleSnglLnkSeq.UncheckedStack.Itr.parent(itr));
          }
          break;
        case FLOAT:
          Assertions.assertSame(expectedNext,FieldAndMethodAccessor.FloatSnglLnkSeq.AbstractItr.next(itr));
          Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.FloatSnglLnkSeq.AbstractItr.curr(itr));
          Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.FloatSnglLnkSeq.AbstractItr.prev(itr));
          if(checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.FloatSnglLnkSeq.CheckedStack.Itr.parent(itr));
            Assertions.assertSame(expectedItrModCount,
                FieldAndMethodAccessor.FloatSnglLnkSeq.CheckedStack.Itr.modCount(itr));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.FloatSnglLnkSeq.UncheckedStack.Itr.parent(itr));
          }
          break;
        case INT:
          Assertions.assertSame(expectedNext,FieldAndMethodAccessor.IntSnglLnkSeq.AbstractItr.next(itr));
          Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.IntSnglLnkSeq.AbstractItr.curr(itr));
          Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.IntSnglLnkSeq.AbstractItr.prev(itr));
          if(checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.IntSnglLnkSeq.CheckedStack.Itr.parent(itr));
            Assertions.assertSame(expectedItrModCount,
                FieldAndMethodAccessor.IntSnglLnkSeq.CheckedStack.Itr.modCount(itr));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.IntSnglLnkSeq.UncheckedStack.Itr.parent(itr));
          }
          break;
        case LONG:
          Assertions.assertSame(expectedNext,FieldAndMethodAccessor.LongSnglLnkSeq.AbstractItr.next(itr));
          Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.LongSnglLnkSeq.AbstractItr.curr(itr));
          Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.LongSnglLnkSeq.AbstractItr.prev(itr));
          if(checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.LongSnglLnkSeq.CheckedStack.Itr.parent(itr));
            Assertions.assertSame(expectedItrModCount,
                FieldAndMethodAccessor.LongSnglLnkSeq.CheckedStack.Itr.modCount(itr));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.LongSnglLnkSeq.UncheckedStack.Itr.parent(itr));
          }
          break;
        case REF:
          Assertions.assertSame(expectedNext,FieldAndMethodAccessor.RefSnglLnkSeq.AbstractItr.next(itr));
          Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.RefSnglLnkSeq.AbstractItr.curr(itr));
          Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.RefSnglLnkSeq.AbstractItr.prev(itr));
          if(checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.RefSnglLnkSeq.CheckedStack.Itr.parent(itr));
            Assertions.assertSame(expectedItrModCount,
                FieldAndMethodAccessor.RefSnglLnkSeq.CheckedStack.Itr.modCount(itr));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.RefSnglLnkSeq.UncheckedStack.Itr.parent(itr));
          }
          break;
        case SHORT:
          Assertions.assertSame(expectedNext,FieldAndMethodAccessor.ShortSnglLnkSeq.AbstractItr.next(itr));
          Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.ShortSnglLnkSeq.AbstractItr.curr(itr));
          Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.ShortSnglLnkSeq.AbstractItr.prev(itr));
          if(checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.ShortSnglLnkSeq.CheckedStack.Itr.parent(itr));
            Assertions.assertSame(expectedItrModCount,
                FieldAndMethodAccessor.ShortSnglLnkSeq.CheckedStack.Itr.modCount(itr));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.ShortSnglLnkSeq.UncheckedStack.Itr.parent(itr));
          }
          break;
        default:
          throw dataType.invalid();
        }
      }
      @Override public void verifyForEachRemaining(MonitoredFunction function){
        final int expectedCurrIndex=this.expectedCurrIndex;
        if(expectedCurrIndex > 0){
          super.verifyForEachRemainingHelper(function);
          expectedLastRetIndex=expectedSize - 1;
          this.expectedCurrIndex=expectedSize;
        }
      }
      @Override public void verifyNextResult(DataType outputType,Object result){
          verifyGetResult(expectedCurrIndex-1,result,outputType);
      }
      @Override void updateItrNextIndex(){
        expectedLastRetIndex=--expectedCurrIndex;
      }
    }
    SnglLnkStackMonitor(CheckedType checkedType,DataType dataType){
      super(checkedType,dataType);
    }
    SnglLnkStackMonitor(CheckedType checkedType,DataType dataType,int initCap){
      super(checkedType,dataType,initCap);
    }
    @Override public MonitoredIterator<? extends OmniIterator<?>,SEQ> getMonitoredIterator(){
      return new ItrMonitor();
    }
    @Override public StructType getStructType(){
      return StructType.SnglLnkStack;
    }
    @Override public void modCollection(){
      switch(dataType){
      case BOOLEAN:
        ++((BooleanSnglLnkSeq.CheckedStack)seq).modCount;
        break;
      case BYTE:
        ++((ByteSnglLnkSeq.CheckedStack)seq).modCount;
        break;
      case CHAR:
        ++((CharSnglLnkSeq.CheckedStack)seq).modCount;
        break;
      case DOUBLE:
        ++((DoubleSnglLnkSeq.CheckedStack)seq).modCount;
        break;
      case FLOAT:
        ++((FloatSnglLnkSeq.CheckedStack)seq).modCount;
        break;
      case INT:
        ++((IntSnglLnkSeq.CheckedStack)seq).modCount;
        break;
      case LONG:
        ++((LongSnglLnkSeq.CheckedStack)seq).modCount;
        break;
      case REF:
        ++((RefSnglLnkSeq.CheckedStack<?>)seq).modCount;
        break;
      case SHORT:
        ++((ShortSnglLnkSeq.CheckedStack)seq).modCount;
        break;
      default:
        throw dataType.invalid();
      }
      ++expectedModCount;
    }
    @Override public Object removeFirst(){
      final var removed=seq.pop();
      super.updateRemoveIndexState(expectedSize - 1);
      return removed;
    }
    @Override public Object verifyPeek(DataType outputType){
      Object result;
      final boolean isEmpty=expectedSize == 0;
      try{
        result=outputType.callPeek(seq);
      }finally{
        verifyCollectionState();
      }
      if(isEmpty){
        Assertions.assertEquals(outputType.defaultVal,result);
      }else{
        super.verifyGetResult(expectedSize - 1,result,outputType);
      }
      return result;
    }
    @Override public Object verifyPoll(DataType outputType){
      Object result;
      final boolean isEmpty=expectedSize == 0;
      final Object expected=isEmpty?outputType.defaultVal:super.get(expectedSize - 1,outputType);
      try{
        result=outputType.callPoll(seq);
        if(!isEmpty){
          super.updateRemoveIndexState(expectedSize - 1);
        }
      }finally{
        verifyCollectionState();
      }
      Assertions.assertEquals(expected,result);
      return result;
    }
    @Override public Object verifyPop(DataType outputType){
      Object result;
      Object expected=null;
      if(expectedSize != 0){
        expected=super.get(expectedSize - 1,outputType);
      }
      try{
        result=outputType.callPop(seq);
        super.updateRemoveIndexState(expectedSize - 1);
      }finally{
        verifyCollectionState();
      }
      if(outputType == DataType.REF && dataType == DataType.REF){
        Assertions.assertSame(expected,result);
      }else{
        Assertions.assertEquals(expected,result);
      }
      return result;
    }
    @Override public void verifyPush(Object inputVal,DataType inputType,FunctionCallType functionCallType){
      inputType.callPush(inputVal,seq,functionCallType);
      updateAddState(expectedSize,inputVal,inputType);
      verifyCollectionState();
    }
    @Override void copyBooleanListContents(){
      int expectedSize=seq.size;
      final var cast=(BooleanSnglLnkSeq)seq;
      var expectedArr=(boolean[])this.expectedArr;
      int expectedCapacity;
      if((expectedCapacity=this.expectedCapacity) < expectedSize){
        this.expectedArr=expectedArr=new boolean[this.expectedCapacity
            =expectedArr == OmniArray.OfBoolean.DEFAULT_ARR && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP
                ?OmniArray.DEFAULT_ARR_SEQ_CAP:OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
      }
      var currNode=cast.head;
      while(currNode != null){
        expectedArr[--expectedSize]=currNode.val;
        currNode=currNode.next;
      }
    }
    @Override void copyByteListContents(){
      int expectedSize=seq.size;
      final var cast=(ByteSnglLnkSeq)seq;
      var expectedArr=(byte[])this.expectedArr;
      int expectedCapacity;
      if((expectedCapacity=this.expectedCapacity) < expectedSize){
        this.expectedArr=expectedArr=new byte[this.expectedCapacity
            =expectedArr == OmniArray.OfByte.DEFAULT_ARR && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP
                ?OmniArray.DEFAULT_ARR_SEQ_CAP:OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
      }
      var currNode=cast.head;
      while(currNode != null){
        expectedArr[--expectedSize]=currNode.val;
        currNode=currNode.next;
      }
    }
    @Override void copyCharListContents(){
      int expectedSize=seq.size;
      final var cast=(CharSnglLnkSeq)seq;
      var expectedArr=(char[])this.expectedArr;
      int expectedCapacity;
      if((expectedCapacity=this.expectedCapacity) < expectedSize){
        this.expectedArr=expectedArr=new char[this.expectedCapacity
            =expectedArr == OmniArray.OfChar.DEFAULT_ARR && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP
                ?OmniArray.DEFAULT_ARR_SEQ_CAP:OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
      }
      var currNode=cast.head;
      while(currNode != null){
        expectedArr[--expectedSize]=currNode.val;
        currNode=currNode.next;
      }
    }
    @Override void copyDoubleListContents(){
      int expectedSize=seq.size;
      final var cast=(DoubleSnglLnkSeq)seq;
      var expectedArr=(double[])this.expectedArr;
      int expectedCapacity;
      if((expectedCapacity=this.expectedCapacity) < expectedSize){
        this.expectedArr=expectedArr=new double[this.expectedCapacity
            =expectedArr == OmniArray.OfDouble.DEFAULT_ARR && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP
                ?OmniArray.DEFAULT_ARR_SEQ_CAP:OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
      }
      var currNode=cast.head;
      while(currNode != null){
        expectedArr[--expectedSize]=currNode.val;
        currNode=currNode.next;
      }
    }
    @Override void copyFloatListContents(){
      int expectedSize=seq.size;
      final var cast=(FloatSnglLnkSeq)seq;
      var expectedArr=(float[])this.expectedArr;
      int expectedCapacity;
      if((expectedCapacity=this.expectedCapacity) < expectedSize){
        this.expectedArr=expectedArr=new float[this.expectedCapacity
            =expectedArr == OmniArray.OfFloat.DEFAULT_ARR && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP
                ?OmniArray.DEFAULT_ARR_SEQ_CAP:OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
      }
      var currNode=cast.head;
      while(currNode != null){
        expectedArr[--expectedSize]=currNode.val;
        currNode=currNode.next;
      }
    }
    @Override void copyIntListContents(){
      int expectedSize=seq.size;
      final var cast=(IntSnglLnkSeq)seq;
      var expectedArr=(int[])this.expectedArr;
      int expectedCapacity;
      if((expectedCapacity=this.expectedCapacity) < expectedSize){
        this.expectedArr=expectedArr=new int[this.expectedCapacity
            =expectedArr == OmniArray.OfInt.DEFAULT_ARR && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP
                ?OmniArray.DEFAULT_ARR_SEQ_CAP:OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
      }
      var currNode=cast.head;
      while(currNode != null){
        expectedArr[--expectedSize]=currNode.val;
        currNode=currNode.next;
      }
    }
    @Override void copyLongListContents(){
      int expectedSize=seq.size;
      final var cast=(LongSnglLnkSeq)seq;
      var expectedArr=(long[])this.expectedArr;
      int expectedCapacity;
      if((expectedCapacity=this.expectedCapacity) < expectedSize){
        this.expectedArr=expectedArr=new long[this.expectedCapacity
            =expectedArr == OmniArray.OfLong.DEFAULT_ARR && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP
                ?OmniArray.DEFAULT_ARR_SEQ_CAP:OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
      }
      var currNode=cast.head;
      while(currNode != null){
        expectedArr[--expectedSize]=currNode.val;
        currNode=currNode.next;
      }
    }
    @Override void copyRefListContents(){
      int expectedSize=seq.size;
      final var cast=(RefSnglLnkSeq<?>)seq;
      var expectedArr=(Object[])this.expectedArr;
      int expectedCapacity;
      if((expectedCapacity=this.expectedCapacity) < expectedSize){
        this.expectedArr=expectedArr=new Object[this.expectedCapacity
            =expectedArr == OmniArray.OfRef.DEFAULT_ARR && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP
                ?OmniArray.DEFAULT_ARR_SEQ_CAP:OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
      }
      var currNode=cast.head;
      while(currNode != null){
        expectedArr[--expectedSize]=currNode.val;
        currNode=currNode.next;
      }
    }
    @Override void copyShortListContents(){
      int expectedSize=seq.size;
      final var cast=(ShortSnglLnkSeq)seq;
      var expectedArr=(short[])this.expectedArr;
      int expectedCapacity;
      if((expectedCapacity=this.expectedCapacity) < expectedSize){
        this.expectedArr=expectedArr=new short[this.expectedCapacity
            =expectedArr == OmniArray.OfShort.DEFAULT_ARR && expectedSize <= OmniArray.DEFAULT_ARR_SEQ_CAP
                ?OmniArray.DEFAULT_ARR_SEQ_CAP:OmniArray.growBy50Pct(expectedCapacity,expectedSize)];
      }
      var currNode=cast.head;
      while(currNode != null){
        expectedArr[--expectedSize]=currNode.val;
        currNode=currNode.next;
      }
    }
    @SuppressWarnings("unchecked") @Override SEQ initSeq(){
      final var checked=checkedType.checked;
      switch(dataType){
      case BOOLEAN:
        if(checked){ return (SEQ)new BooleanSnglLnkSeq.CheckedStack(); }
        return (SEQ)new BooleanSnglLnkSeq.UncheckedStack();
      case BYTE:
        if(checked){ return (SEQ)new ByteSnglLnkSeq.CheckedStack(); }
        return (SEQ)new ByteSnglLnkSeq.UncheckedStack();
      case CHAR:
        if(checked){ return (SEQ)new CharSnglLnkSeq.CheckedStack(); }
        return (SEQ)new CharSnglLnkSeq.UncheckedStack();
      case DOUBLE:
        if(checked){ return (SEQ)new DoubleSnglLnkSeq.CheckedStack(); }
        return (SEQ)new DoubleSnglLnkSeq.UncheckedStack();
      case FLOAT:
        if(checked){ return (SEQ)new FloatSnglLnkSeq.CheckedStack(); }
        return (SEQ)new FloatSnglLnkSeq.UncheckedStack();
      case INT:
        if(checked){ return (SEQ)new IntSnglLnkSeq.CheckedStack(); }
        return (SEQ)new IntSnglLnkSeq.UncheckedStack();
      case LONG:
        if(checked){ return (SEQ)new LongSnglLnkSeq.CheckedStack(); }
        return (SEQ)new LongSnglLnkSeq.UncheckedStack();
      case REF:
        if(checked){ return (SEQ)new RefSnglLnkSeq.CheckedStack<>(); }
        return (SEQ)new RefSnglLnkSeq.UncheckedStack<>();
      case SHORT:
        if(checked){ return (SEQ)new ShortSnglLnkSeq.CheckedStack(); }
        return (SEQ)new ShortSnglLnkSeq.UncheckedStack();
      default:
        throw dataType.invalid();
      }
    }
    @Override void updateModCount(){
      switch(dataType){
      case BOOLEAN:
        expectedModCount=((BooleanSnglLnkSeq.CheckedStack)seq).modCount;
        break;
      case BYTE:
        expectedModCount=((ByteSnglLnkSeq.CheckedStack)seq).modCount;
        break;
      case CHAR:
        expectedModCount=((CharSnglLnkSeq.CheckedStack)seq).modCount;
        break;
      case DOUBLE:
        expectedModCount=((DoubleSnglLnkSeq.CheckedStack)seq).modCount;
        break;
      case FLOAT:
        expectedModCount=((FloatSnglLnkSeq.CheckedStack)seq).modCount;
        break;
      case INT:
        expectedModCount=((IntSnglLnkSeq.CheckedStack)seq).modCount;
        break;
      case LONG:
        expectedModCount=((LongSnglLnkSeq.CheckedStack)seq).modCount;
        break;
      case REF:
        expectedModCount=((RefSnglLnkSeq.CheckedStack<?>)seq).modCount;
        break;
      case SHORT:
        expectedModCount=((ShortSnglLnkSeq.CheckedStack)seq).modCount;
        break;
      default:
        throw dataType.invalid();
      }
    }
    @Override void verifyBooleanClone(Object clone){
      Assertions.assertNotSame(clone,seq);
      final var cloneCast=(BooleanSnglLnkSeq)clone;
      int size;
      Assertions.assertEquals(size=seq.size,cloneCast.size);
      Assertions.assertEquals(checkedType.checked,cloneCast instanceof BooleanSnglLnkSeq.CheckedStack);
      if(checkedType.checked){
        Assertions.assertEquals(0,((BooleanSnglLnkSeq.CheckedStack)cloneCast).modCount);
      }else{
        Assertions.assertTrue(cloneCast instanceof BooleanSnglLnkSeq.UncheckedStack);
      }
      var cloneHead=cloneCast.head;
      var thisHead=((BooleanSnglLnkSeq)seq).head;
      while(--size >= 0){
        Assertions.assertNotSame(cloneHead,thisHead);
        Assertions.assertEquals(cloneHead.val,thisHead.val);
        cloneHead=cloneHead.next;
        thisHead=thisHead.next;
      }
      Assertions.assertNull(cloneHead);
      Assertions.assertNull(thisHead);
    }
    @Override void verifyBooleanIntegrity(){
      final int expectedSize;
      Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
      final var cast=(BooleanSnglLnkSeq)seq;
      final var expectedArr=(boolean[])this.expectedArr;
      if(checkedType.checked){
        Assertions.assertEquals(expectedModCount,((BooleanSnglLnkSeq.CheckedStack)cast).modCount);
      }
      var headNode=cast.head;
      if(expectedSize != 0){
        for(int i=expectedSize;--i >= 0;){
          Assertions.assertEquals(expectedArr[i],headNode.val);
          headNode=headNode.next;
        }
      }
      Assertions.assertNull(headNode);
    }
    @Override void verifyByteClone(Object clone){
      Assertions.assertNotSame(clone,seq);
      final var cloneCast=(ByteSnglLnkSeq)clone;
      int size;
      Assertions.assertEquals(size=seq.size,cloneCast.size);
      Assertions.assertEquals(checkedType.checked,cloneCast instanceof ByteSnglLnkSeq.CheckedStack);
      if(checkedType.checked){
        Assertions.assertEquals(0,((ByteSnglLnkSeq.CheckedStack)cloneCast).modCount);
      }else{
        Assertions.assertTrue(cloneCast instanceof ByteSnglLnkSeq.UncheckedStack);
      }
      var cloneHead=cloneCast.head;
      var thisHead=((ByteSnglLnkSeq)seq).head;
      while(--size >= 0){
        Assertions.assertNotSame(cloneHead,thisHead);
        Assertions.assertEquals(cloneHead.val,thisHead.val);
        cloneHead=cloneHead.next;
        thisHead=thisHead.next;
      }
      Assertions.assertNull(cloneHead);
      Assertions.assertNull(thisHead);
    }
    @Override void verifyByteIntegrity(){
      final int expectedSize;
      Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
      final var cast=(ByteSnglLnkSeq)seq;
      final var expectedArr=(byte[])this.expectedArr;
      if(checkedType.checked){
        Assertions.assertEquals(expectedModCount,((ByteSnglLnkSeq.CheckedStack)cast).modCount);
      }
      var headNode=cast.head;
      if(expectedSize != 0){
        for(int i=expectedSize;--i >= 0;){
          Assertions.assertEquals(expectedArr[i],headNode.val);
          headNode=headNode.next;
        }
      }
      Assertions.assertNull(headNode);
    }
    @Override void verifyCharClone(Object clone){
      Assertions.assertNotSame(clone,seq);
      final var cloneCast=(CharSnglLnkSeq)clone;
      int size;
      Assertions.assertEquals(size=seq.size,cloneCast.size);
      Assertions.assertEquals(checkedType.checked,cloneCast instanceof CharSnglLnkSeq.CheckedStack);
      if(checkedType.checked){
        Assertions.assertEquals(0,((CharSnglLnkSeq.CheckedStack)cloneCast).modCount);
      }else{
        Assertions.assertTrue(cloneCast instanceof CharSnglLnkSeq.UncheckedStack);
      }
      var cloneHead=cloneCast.head;
      var thisHead=((CharSnglLnkSeq)seq).head;
      while(--size >= 0){
        Assertions.assertNotSame(cloneHead,thisHead);
        Assertions.assertEquals(cloneHead.val,thisHead.val);
        cloneHead=cloneHead.next;
        thisHead=thisHead.next;
      }
      Assertions.assertNull(cloneHead);
      Assertions.assertNull(thisHead);
    }
    @Override void verifyCharIntegrity(){
      final int expectedSize;
      Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
      final var cast=(CharSnglLnkSeq)seq;
      final var expectedArr=(char[])this.expectedArr;
      if(checkedType.checked){
        Assertions.assertEquals(expectedModCount,((CharSnglLnkSeq.CheckedStack)cast).modCount);
      }
      var headNode=cast.head;
      if(expectedSize != 0){
        for(int i=expectedSize;--i >= 0;){
          Assertions.assertEquals(expectedArr[i],headNode.val);
          headNode=headNode.next;
        }
      }
      Assertions.assertNull(headNode);
    }
    @Override void verifyDoubleClone(Object clone){
      Assertions.assertNotSame(clone,seq);
      final var cloneCast=(DoubleSnglLnkSeq)clone;
      int size;
      Assertions.assertEquals(size=seq.size,cloneCast.size);
      Assertions.assertEquals(checkedType.checked,cloneCast instanceof DoubleSnglLnkSeq.CheckedStack);
      if(checkedType.checked){
        Assertions.assertEquals(0,((DoubleSnglLnkSeq.CheckedStack)cloneCast).modCount);
      }else{
        Assertions.assertTrue(cloneCast instanceof DoubleSnglLnkSeq.UncheckedStack);
      }
      var cloneHead=cloneCast.head;
      var thisHead=((DoubleSnglLnkSeq)seq).head;
      while(--size >= 0){
        Assertions.assertNotSame(cloneHead,thisHead);
        Assertions.assertEquals(cloneHead.val,thisHead.val);
        cloneHead=cloneHead.next;
        thisHead=thisHead.next;
      }
      Assertions.assertNull(cloneHead);
      Assertions.assertNull(thisHead);
    }
    @Override void verifyDoubleIntegrity(){
      final int expectedSize;
      Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
      final var cast=(DoubleSnglLnkSeq)seq;
      final var expectedArr=(double[])this.expectedArr;
      if(checkedType.checked){
        Assertions.assertEquals(expectedModCount,((DoubleSnglLnkSeq.CheckedStack)cast).modCount);
      }
      var headNode=cast.head;
      if(expectedSize != 0){
        for(int i=expectedSize;--i >= 0;){
          Assertions.assertEquals(expectedArr[i],headNode.val);
          headNode=headNode.next;
        }
      }
      Assertions.assertNull(headNode);
    }
    @Override void verifyFloatClone(Object clone){
      Assertions.assertNotSame(clone,seq);
      final var cloneCast=(FloatSnglLnkSeq)clone;
      int size;
      Assertions.assertEquals(size=seq.size,cloneCast.size);
      Assertions.assertEquals(checkedType.checked,cloneCast instanceof FloatSnglLnkSeq.CheckedStack);
      if(checkedType.checked){
        Assertions.assertEquals(0,((FloatSnglLnkSeq.CheckedStack)cloneCast).modCount);
      }else{
        Assertions.assertTrue(cloneCast instanceof FloatSnglLnkSeq.UncheckedStack);
      }
      var cloneHead=cloneCast.head;
      var thisHead=((FloatSnglLnkSeq)seq).head;
      while(--size >= 0){
        Assertions.assertNotSame(cloneHead,thisHead);
        Assertions.assertEquals(cloneHead.val,thisHead.val);
        cloneHead=cloneHead.next;
        thisHead=thisHead.next;
      }
      Assertions.assertNull(cloneHead);
      Assertions.assertNull(thisHead);
    }
    @Override void verifyFloatIntegrity(){
      final int expectedSize;
      Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
      final var cast=(FloatSnglLnkSeq)seq;
      final var expectedArr=(float[])this.expectedArr;
      if(checkedType.checked){
        Assertions.assertEquals(expectedModCount,((FloatSnglLnkSeq.CheckedStack)cast).modCount);
      }
      var headNode=cast.head;
      if(expectedSize != 0){
        for(int i=expectedSize;--i >= 0;){
          Assertions.assertEquals(expectedArr[i],headNode.val);
          headNode=headNode.next;
        }
      }
      Assertions.assertNull(headNode);
    }
    @Override void verifyIntClone(Object clone){
      Assertions.assertNotSame(clone,seq);
      final var cloneCast=(IntSnglLnkSeq)clone;
      int size;
      Assertions.assertEquals(size=seq.size,cloneCast.size);
      Assertions.assertEquals(checkedType.checked,cloneCast instanceof IntSnglLnkSeq.CheckedStack);
      if(checkedType.checked){
        Assertions.assertEquals(0,((IntSnglLnkSeq.CheckedStack)cloneCast).modCount);
      }else{
        Assertions.assertTrue(cloneCast instanceof IntSnglLnkSeq.UncheckedStack);
      }
      var cloneHead=cloneCast.head;
      var thisHead=((IntSnglLnkSeq)seq).head;
      while(--size >= 0){
        Assertions.assertNotSame(cloneHead,thisHead);
        Assertions.assertEquals(cloneHead.val,thisHead.val);
        cloneHead=cloneHead.next;
        thisHead=thisHead.next;
      }
      Assertions.assertNull(cloneHead);
      Assertions.assertNull(thisHead);
    }
    @Override void verifyIntIntegrity(){
      final int expectedSize;
      Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
      final var cast=(IntSnglLnkSeq)seq;
      final var expectedArr=(int[])this.expectedArr;
      if(checkedType.checked){
        Assertions.assertEquals(expectedModCount,((IntSnglLnkSeq.CheckedStack)cast).modCount);
      }
      var headNode=cast.head;
      if(expectedSize != 0){
        for(int i=expectedSize;--i >= 0;){
          Assertions.assertEquals(expectedArr[i],headNode.val);
          headNode=headNode.next;
        }
      }
      Assertions.assertNull(headNode);
    }
    @Override void verifyLongClone(Object clone){
      Assertions.assertNotSame(clone,seq);
      final var cloneCast=(LongSnglLnkSeq)clone;
      int size;
      Assertions.assertEquals(size=seq.size,cloneCast.size);
      Assertions.assertEquals(checkedType.checked,cloneCast instanceof LongSnglLnkSeq.CheckedStack);
      if(checkedType.checked){
        Assertions.assertEquals(0,((LongSnglLnkSeq.CheckedStack)cloneCast).modCount);
      }else{
        Assertions.assertTrue(cloneCast instanceof LongSnglLnkSeq.UncheckedStack);
      }
      var cloneHead=cloneCast.head;
      var thisHead=((LongSnglLnkSeq)seq).head;
      while(--size >= 0){
        Assertions.assertNotSame(cloneHead,thisHead);
        Assertions.assertEquals(cloneHead.val,thisHead.val);
        cloneHead=cloneHead.next;
        thisHead=thisHead.next;
      }
      Assertions.assertNull(cloneHead);
      Assertions.assertNull(thisHead);
    }
    @Override void verifyLongIntegrity(){
      final int expectedSize;
      Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
      final var cast=(LongSnglLnkSeq)seq;
      final var expectedArr=(long[])this.expectedArr;
      if(checkedType.checked){
        Assertions.assertEquals(expectedModCount,((LongSnglLnkSeq.CheckedStack)cast).modCount);
      }
      var headNode=cast.head;
      if(expectedSize != 0){
        for(int i=expectedSize;--i >= 0;){
          Assertions.assertEquals(expectedArr[i],headNode.val);
          headNode=headNode.next;
        }
      }
      Assertions.assertNull(headNode);
    }
    @Override void verifyRefClone(Object clone,boolean refIsSame){
      Assertions.assertNotSame(clone,seq);
      final var cloneCast=(RefSnglLnkSeq<?>)clone;
      int size;
      Assertions.assertEquals(size=seq.size,cloneCast.size);
      Assertions.assertEquals(checkedType.checked,cloneCast instanceof RefSnglLnkSeq.CheckedStack);
      if(checkedType.checked){
        Assertions.assertEquals(0,((RefSnglLnkSeq.CheckedStack<?>)cloneCast).modCount);
      }else{
        Assertions.assertTrue(cloneCast instanceof RefSnglLnkSeq.UncheckedStack);
      }
      var cloneHead=cloneCast.head;
      var thisHead=((RefSnglLnkSeq<?>)seq).head;
      if(refIsSame){
        while(--size >= 0){
          Assertions.assertNotSame(cloneHead,thisHead);
          Assertions.assertSame(cloneHead.val,thisHead.val);
          cloneHead=cloneHead.next;
          thisHead=thisHead.next;
        }
      }else{
        while(--size >= 0){
          Assertions.assertNotSame(cloneHead,thisHead);
          Assertions.assertEquals(cloneHead.val,thisHead.val);
          cloneHead=cloneHead.next;
          thisHead=thisHead.next;
        }
      }
      Assertions.assertNull(cloneHead);
      Assertions.assertNull(thisHead);
    }
    @Override void verifyRefIntegrity(){
      final int expectedSize;
      Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
      final var cast=(RefSnglLnkSeq<?>)seq;
      final var expectedArr=(Object[])this.expectedArr;
      if(checkedType.checked){
        Assertions.assertEquals(expectedModCount,((RefSnglLnkSeq.CheckedStack<?>)cast).modCount);
      }
      var headNode=cast.head;
      if(expectedSize != 0){
        for(int i=expectedSize;--i >= 0;){
          Assertions.assertSame(expectedArr[i],headNode.val);
          headNode=headNode.next;
        }
      }
      Assertions.assertNull(headNode);
    }
    @Override void verifyShortClone(Object clone){
      Assertions.assertNotSame(clone,seq);
      final var cloneCast=(ShortSnglLnkSeq)clone;
      int size;
      Assertions.assertEquals(size=seq.size,cloneCast.size);
      Assertions.assertEquals(checkedType.checked,cloneCast instanceof ShortSnglLnkSeq.CheckedStack);
      if(checkedType.checked){
        Assertions.assertEquals(0,((ShortSnglLnkSeq.CheckedStack)cloneCast).modCount);
      }else{
        Assertions.assertTrue(cloneCast instanceof ShortSnglLnkSeq.UncheckedStack);
      }
      var cloneHead=cloneCast.head;
      var thisHead=((ShortSnglLnkSeq)seq).head;
      while(--size >= 0){
        Assertions.assertNotSame(cloneHead,thisHead);
        Assertions.assertEquals(cloneHead.val,thisHead.val);
        cloneHead=cloneHead.next;
        thisHead=thisHead.next;
      }
      Assertions.assertNull(cloneHead);
      Assertions.assertNull(thisHead);
    }
    @Override void verifyShortIntegrity(){
      final int expectedSize;
      Assertions.assertEquals(expectedSize=this.expectedSize,seq.size);
      final var cast=(ShortSnglLnkSeq)seq;
      final var expectedArr=(short[])this.expectedArr;
      if(checkedType.checked){
        Assertions.assertEquals(expectedModCount,((ShortSnglLnkSeq.CheckedStack)cast).modCount);
      }
      var headNode=cast.head;
      if(expectedSize != 0){
        for(int i=expectedSize;--i >= 0;){
          Assertions.assertEquals(expectedArr[i],headNode.val);
          headNode=headNode.next;
        }
      }
      Assertions.assertNull(headNode);
    }
  }
  private static interface ToStringAndHashCodeTest{
    private void runAllTests(String testName){
      for(final var structType:ALL_STRUCTS){
        for(final var checkedType:CheckedType.values()){
          for(final var collectionType:DataType.values()){
            for(final var size:SIZES){
              if(collectionType == DataType.REF){
                for(final var objGen:structType.validMonitoredObjectGens){
                  if(size == 0 || objGen.expectedException == null){
                    TestExecutorService.submitTest(()->callVerify(SequenceInitialization.Ascending
                        .initialize(getMonitoredSequence(structType,checkedType,collectionType,size),size,0)));
                  }else if(checkedType.checked){
                    TestExecutorService.submitTest(()->{
                      final var throwSwitch=new MonitoredObjectGen.ThrowSwitch();
                      final var monitor=SequenceInitialization.Ascending.initializeWithMonitoredObj(
                          getMonitoredSequence(structType,checkedType,collectionType,size),size,0,objGen,throwSwitch);
                      Assertions.assertThrows(objGen.expectedException,()->{
                        try{
                          callRaw(monitor.getCollection());
                        }finally{
                          throwSwitch.doThrow=false;
                          monitor.verifyCollectionState();
                        }
                      });
                    });
                  }
                }
              }else{
                final int initValBound=size > 0 && collectionType == DataType.BOOLEAN?1:0;
                for(int tmpInitVal=0;tmpInitVal <= initValBound;++tmpInitVal){
                  final int initVal=tmpInitVal;
                  TestExecutorService.submitTest(()->callVerify(SequenceInitialization.Ascending
                      .initialize(getMonitoredSequence(structType,checkedType,collectionType,size),size,initVal)));
                }
              }
            }
          }
        }
      }
      TestExecutorService.completeAllTests(testName);
    }
    void callRaw(OmniCollection<?> collection);
    void callVerify(MonitoredSequence<?> monitor);
  }
  private static final double[] RANDOM_THRESHOLDS=new double[]{0.01,0.05,0.10,0.25,0.50,0.75,0.90,0.95,0.99};
  private static final double[] POSITIONS=new double[]{-1,0,0.25,0.5,0.75,1.0};
  private static final int[] SHORT_SIZES=new int[]{0,10};
  private static final int[] MEDIUM_SIZES=new int[]{0,1,2,3,4,5,10};
  private static final int[] SIZES=new int[]{0,1,2,3,4,5,10,20,30,40,50,60,70,80,90,100};
  private static final int[] REMOVE_IF_SIZES=new int[]{0,1,2,3,4,5,6,7,8,9,10,20,30,40,50,60,70,90,80,100};
  private static final StructType[] ALL_STRUCTS=new StructType[]{StructType.SnglLnkQueue,StructType.SnglLnkStack};
  private static AbstractSnglLnkSeqMonitor<?> getMonitoredSequence(StructType structType,CheckedType checkedType,
      DataType collectionType){
    switch(structType){
    case SnglLnkQueue:
      return new SnglLnkQueueMonitor<>(checkedType,collectionType);
    case SnglLnkStack:
      return new SnglLnkStackMonitor<>(checkedType,collectionType);
    default:
      throw structType.invalid();
    }
  }
  private static MonitoredSequence<?> getMonitoredSequence(StructType structType,CheckedType checkedType,
      DataType collectionType,int initCapacity){
    switch(structType){
    case SnglLnkQueue:
      return new SnglLnkQueueMonitor<>(checkedType,collectionType,initCapacity);
    case SnglLnkStack:
      return new SnglLnkStackMonitor<>(checkedType,collectionType,initCapacity);
    default:
      throw structType.invalid();
    }
  }
  @Order(276)
  @Test public void testadd_val(){
    final AddTest<?> test=(monitor,inputVal,inputType,functionCallType)->Assertions
        .assertTrue(monitor.verifyAdd(inputVal,inputType,functionCallType));
    test.runAllTests("SnglLnkSeqTest.testadd_val",ALL_STRUCTS);
  }
  @Order(576)
  @Test public void testclear_void(){
    final BasicTest test=MonitoredSequence::verifyClear;
    test.runAllTests("SnglLnkSeqTest.testclear_void");
  }
  @Order(576)
  @Test public void testclone_void(){
    final BasicTest test=MonitoredSequence::verifyClone;
    test.runAllTests("SnglLnkSeqTest.testclone_void");
  }
  @Order(36)
  @Test public void testConstructor_void(){
    for(final var checkedType:CheckedType.values()){
      for(final var collectionType:DataType.values()){
        TestExecutorService
            .submitTest(()->new SnglLnkStackMonitor<>(checkedType,collectionType).verifyCollectionState());
        TestExecutorService
            .submitTest(()->new SnglLnkQueueMonitor<>(checkedType,collectionType).verifyCollectionState());
      }
    }
    TestExecutorService.completeAllTests("SnglLnkSeqTest.testConstructor_void");
  }
  @Order(236988)
  @Test public void testcontains_val(){
    final QueryTest<MonitoredSequence<?>> test
        =(monitor,queryVal,inputType,castType,modification,monitoredObjectGen,position,seqSize)->{
          if(monitoredObjectGen == null){
            Assertions.assertEquals(position >= 0,monitor.verifyContains(queryVal,inputType,castType,modification));
          }else{
            monitor.verifyThrowingContains(monitoredObjectGen);
          }
        };
    test.runAllTests("SnglLnkSeqTest.testcontains_val",true);
  }
  @Order(86)
  @Test public void testelement_void(){
    final PopTest<SnglLnkQueueMonitor<?,?>> test=(monitor,outputType)->{
      monitor.verifyElement(outputType);
      if(!monitor.isEmpty()){
        monitor.removeFirst();
      }
    };
    test.runAllTests("SnglLnkSeqTest.testelement_void",true,StructType.SnglLnkQueue);
  }
  @Order(31310)
  @Test public void testforEach_Consumer(){
    final MonitoredFunctionTest<MonitoredSequence<?>> test=(monitor,functionGen,functionCallType,randSeed)->{
      if(functionGen.expectedException == null || monitor.isEmpty()){
        monitor.verifyForEach(functionGen,functionCallType,randSeed);
      }else{
        Assertions.assertThrows(functionGen.expectedException,
            ()->monitor.verifyForEach(functionGen,functionCallType,randSeed));
      }
    };
    test.runAllTests("SnglLnkSeqTest.testforEach_Consumer",100);
  }
  @Order(738)
  @Test public void testhashCode_void(){
    final ToStringAndHashCodeTest test=new ToStringAndHashCodeTest(){
      @Override public void callRaw(OmniCollection<?> seq){
        seq.hashCode();
      }
      @Override public void callVerify(MonitoredSequence<?> monitor){
        monitor.verifyHashCode();
      }
    };
    test.runAllTests("SnglLnkSeqTest.testhashCode_void");
  }
  @Order(576)
  @Test
  public void testequals_Object(){
      final BasicTest test=(monitor)->Assertions.assertFalse(monitor.getCollection().equals(null));
      test.runAllTests("SnglLnkSeqTest.testequals_Object");
  }
  @Order(576)
  @Test public void testisEmpty_void(){
    final BasicTest test=MonitoredSequence::verifyIsEmpty;
    test.runAllTests("SnglLnkSeqTest.testisEmpty_void");
  }
  @Order(576)
  @Test public void testiterator_void(){
    final BasicTest test=(monitor)->{
      monitor.getMonitoredIterator().verifyIteratorState();
      monitor.verifyCollectionState();
    };
    test.runAllTests("SnglLnkSeqTest.testiterator_void");
  }
  @Order(2520)
  @Test public void testItrclone_void(){
    for(final var size:SIZES){
      int prevIndex=-1;
      for(final var position:POSITIONS){
        int index;
        if(position >= 0 && (index=(int)(position * size)) != prevIndex){
          prevIndex=index;
          for(final var collectionType:DataType.values()){
            for(final var checkedType:CheckedType.values()){
              for(final var structType:ALL_STRUCTS){
                TestExecutorService.submitTest(()->{
                  final var seqMonitor=SequenceInitialization.Ascending
                      .initialize(getMonitoredSequence(structType,checkedType,collectionType,size),size,0);
                  final var itrMonitor=seqMonitor.getMonitoredIterator(index,IteratorType.AscendingItr);
                  itrMonitor.verifyClone();
                });
              }
            }
          }
        }
      }
    }
    TestExecutorService.completeAllTests("SnglLnkSeqTest.testItrclone_void");
  }
  //@org.junit.jupiter.api.Disabled
  @Order(137220)
  @Test public void testItrforEachRemaining_Consumer(){
    for(final int size:MEDIUM_SIZES){
      int prevNumToIterate=-1;
      for(final var position:POSITIONS){
        int numToIterate;
        if(position >= 0 && (numToIterate=(int)(position * size)) != prevNumToIterate){
          prevNumToIterate=numToIterate;
          final int numLeft=size - numToIterate;
          for(final var collectionType:DataType.values()){
            for(final var checkedType:CheckedType.values()){
              for(final var structType:ALL_STRUCTS){
                for(final var illegalMod:IteratorType.AscendingItr.validPreMods){
                  if(illegalMod.expectedException == null || checkedType.checked){
                    for(final var functionGen:IteratorType.AscendingItr.validMonitoredFunctionGens){
                      if(size == 0 || functionGen.expectedException == null || checkedType.checked){
                        for(final var functionCallType:collectionType.validFunctionCalls){
                          final long randSeedBound=!functionCallType.boxed && numLeft > 1 && functionGen.randomized
                              && illegalMod.expectedException == null?100:0;
                          for(long tmpRandSeed=0;tmpRandSeed <= randSeedBound;++tmpRandSeed){
                            final long randSeed=tmpRandSeed;
                            TestExecutorService.submitTest(()->{
                              final var seqMonitor=SequenceInitialization.Ascending
                                  .initialize(getMonitoredSequence(structType,checkedType,collectionType,size),size,0);
                              final var itrMonitor
                                  =seqMonitor.getMonitoredIterator(numToIterate,IteratorType.AscendingItr);
                              itrMonitor.illegalMod(illegalMod);
                              if(illegalMod.expectedException == null || numLeft == 0){
                                if(functionGen.expectedException == null || numLeft == 0){
                                  itrMonitor.verifyForEachRemaining(functionGen,functionCallType,randSeed);
                                }else{
                                  Assertions.assertThrows(functionGen.expectedException,
                                      ()->itrMonitor.verifyForEachRemaining(functionGen,functionCallType,randSeed));
                                }
                              }else{
                                Assertions.assertThrows(illegalMod.expectedException,
                                    ()->itrMonitor.verifyForEachRemaining(functionGen,functionCallType,randSeed));
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
    }
    TestExecutorService.completeAllTests("SnglLnkSeqTest.testItrforEachRemaining_Consumer");
  }
  @Order(72)
  @Test public void testItrhasNext_void(){
    for(final var collectionType:DataType.values()){
      for(final var checkedType:CheckedType.values()){
        for(final var structType:ALL_STRUCTS){
          for(final var size:SHORT_SIZES){
            TestExecutorService.submitTest(()->{
              final var seqMonitor=SequenceInitialization.Ascending
                  .initialize(getMonitoredSequence(structType,checkedType,collectionType,size),size,0);
              final var itrMonitor=seqMonitor.getMonitoredIterator();
              while(itrMonitor.verifyHasNext()){
                itrMonitor.iterateForward();
              }
            });
          }
        }
      }
    }
    TestExecutorService.completeAllTests("SnglLnkSeqTest.testItrhasNext_void");
  }
  @Order(90)
  @Test public void testItrnext_void(){
    for(final var collectionType:DataType.values()){
      final var outputTypes=collectionType.validOutputTypes();
      for(final var checkedType:CheckedType.values()){
        for(final var size:SHORT_SIZES){
          if(size > 0 || checkedType.checked){
            for(final var structType:ALL_STRUCTS){
              for(final var illegalMod:IteratorType.AscendingItr.validPreMods){
                if(illegalMod.expectedException == null || checkedType.checked){
                  TestExecutorService.submitTest(()->{
                    final var seqMonitor=SequenceInitialization.Ascending
                        .initialize(getMonitoredSequence(structType,checkedType,collectionType,size),size,0);
                    if(illegalMod.expectedException == null){
                      for(final var outputType:outputTypes){
                        final var itrMonitor=seqMonitor.getMonitoredIterator();
                        while(itrMonitor.hasNext()){
                          itrMonitor.verifyNext(outputType);
                        }
                        if(checkedType.checked){
                          Assertions.assertThrows(NoSuchElementException.class,()->itrMonitor.verifyNext(outputType));
                        }
                      }
                    }else{
                      for(final var outputType:outputTypes){
                        int prevIndex=-1;
                        for(final var position:POSITIONS){
                          if(position >= 0){
                            final int index=(int)(size * position);
                            if(index != prevIndex){
                              prevIndex=index;
                              final var itrMonitor=seqMonitor.getMonitoredIterator(index,IteratorType.AscendingItr);
                              itrMonitor.illegalMod(illegalMod);
                              Assertions.assertThrows(illegalMod.expectedException,
                                  ()->itrMonitor.verifyNext(outputType));
                            }
                          }
                        }
                      }
                    }
                  });
                }
              }
            }
          }
        }
      }
    }
    TestExecutorService.completeAllTests("SnglLnkSeqTest.testItrnext_void");
  }
  @Order(468)
  @Test public void testItrremove_void(){
    for(final var collectionType:DataType.values()){
      for(final var checkedType:CheckedType.values()){
        for(final var structType:ALL_STRUCTS){
          for(final var removeScenario:IteratorType.AscendingItr.validItrRemoveScenarios){
            if(removeScenario.expectedException == null || checkedType.checked){
              for(final var illegalMod:IteratorType.AscendingItr.validPreMods){
                if(illegalMod.expectedException == null || checkedType.checked){
                  for(final var size:SHORT_SIZES){
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
                          if(size == 0 || numToIterate == size){
                            continue;
                          }
                        case PostAdd:
                        case PostPrev:
                          break;
                        case PostRemove:
                          if(size == 0){
                            continue;
                          }
                          break;
                        default:
                          throw removeScenario.invalid();
                        }
                        TestExecutorService.submitTest(()->{
                          final var seqMonitor=SequenceInitialization.Ascending
                              .initialize(getMonitoredSequence(structType,checkedType,collectionType,size),size,0);
                          final var itrMonitor=seqMonitor.getMonitoredIterator(numToIterate,IteratorType.AscendingItr);
                          removeScenario.initialize(itrMonitor);
                          if(removeScenario.expectedException == null){
                            if(illegalMod.expectedException == null){
                              itrMonitor.verifyRemove();
                              while(itrMonitor.hasNext()){
                                itrMonitor.iterateForward();
                                itrMonitor.verifyRemove();
                              }
                              Assertions.assertEquals(numToIterate < 2,seqMonitor.isEmpty());
                            }else{
                              itrMonitor.illegalMod(illegalMod);
                              Assertions.assertThrows(illegalMod.expectedException,()->itrMonitor.verifyRemove());
                            }
                          }else{
                            itrMonitor.illegalMod(illegalMod);
                            Assertions.assertThrows(removeScenario.expectedException,()->itrMonitor.verifyRemove());
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
    TestExecutorService.completeAllTests("DblLnkSeqTest.testItrremove_void");
  }
  @Test public void testMASSIVEtoString(){
    final int numWorkers=TestExecutorService.getNumWorkers();
    for(final var collectionType:DataType.values()){
      int seqSize;
      if((seqSize=collectionType.massiveToStringThreshold + 1) == 0){
        continue;
      }
      OmniStack<?> checkedStack;
      OmniStack<?> uncheckedStack;
      OmniQueue<?> checkedQueue;
      OmniQueue<?> uncheckedQueue;
      final int threadSpan=(int)Math.ceil((double)seqSize / (double)numWorkers) - 1;
      switch(collectionType){
      case BOOLEAN:{
        final var wayPoints=new BooleanSnglLnkNode[numWorkers + 1];
        for(int i=0;i < wayPoints.length;++i){
          wayPoints[i]=new BooleanSnglLnkNode(true);
        }
        for(int i=0;i < numWorkers;){
          final var threadHead=wayPoints[i];
          final var threadTail=wayPoints[++i];
          TestExecutorService.submitTest(()->{
            var curr=threadTail;
            for(int j=threadSpan;--j >= 0;){
              curr=new BooleanSnglLnkNode(true,curr);
            }
            threadHead.next=curr;
          });
        }
        TestExecutorService.completeAllTests();
        final var head=wayPoints[0];
        final var tail=wayPoints[wayPoints.length - 1];
        checkedQueue=new BooleanSnglLnkSeq.CheckedQueue(head,seqSize,tail);
        uncheckedQueue=new BooleanSnglLnkSeq.UncheckedQueue(head,seqSize,tail);
        checkedStack=new BooleanSnglLnkSeq.CheckedStack(head,seqSize);
        uncheckedStack=new BooleanSnglLnkSeq.UncheckedStack(head,seqSize);
        break;
      }
      case BYTE:{
        final var wayPoints=new ByteSnglLnkNode[numWorkers + 1];
        for(int i=0;i < wayPoints.length;++i){
          wayPoints[i]=new ByteSnglLnkNode((byte)0);
        }
        for(int i=0;i < numWorkers;){
          final var threadHead=wayPoints[i];
          final var threadTail=wayPoints[++i];
          TestExecutorService.submitTest(()->{
            var curr=threadTail;
            for(int j=threadSpan;--j >= 0;){
              curr=new ByteSnglLnkNode((byte)0,curr);
            }
            threadHead.next=curr;
          });
        }
        TestExecutorService.completeAllTests();
        final var head=wayPoints[0];
        final var tail=wayPoints[wayPoints.length - 1];
        checkedQueue=new ByteSnglLnkSeq.CheckedQueue(head,seqSize,tail);
        uncheckedQueue=new ByteSnglLnkSeq.UncheckedQueue(head,seqSize,tail);
        checkedStack=new ByteSnglLnkSeq.CheckedStack(head,seqSize);
        uncheckedStack=new ByteSnglLnkSeq.UncheckedStack(head,seqSize);
        break;
      }
      case SHORT:{
        final var wayPoints=new ShortSnglLnkNode[numWorkers + 1];
        for(int i=0;i < wayPoints.length;++i){
          wayPoints[i]=new ShortSnglLnkNode((short)0);
        }
        for(int i=0;i < numWorkers;){
          final var threadHead=wayPoints[i];
          final var threadTail=wayPoints[++i];
          TestExecutorService.submitTest(()->{
            var curr=threadTail;
            for(int j=threadSpan;--j >= 0;){
              curr=new ShortSnglLnkNode((short)0,curr);
            }
            threadHead.next=curr;
          });
        }
        TestExecutorService.completeAllTests();
        final var head=wayPoints[0];
        final var tail=wayPoints[wayPoints.length - 1];
        checkedQueue=new ShortSnglLnkSeq.CheckedQueue(head,seqSize,tail);
        uncheckedQueue=new ShortSnglLnkSeq.UncheckedQueue(head,seqSize,tail);
        checkedStack=new ShortSnglLnkSeq.CheckedStack(head,seqSize);
        uncheckedStack=new ShortSnglLnkSeq.UncheckedStack(head,seqSize);
        break;
      }
      case INT:{
        final var wayPoints=new IntSnglLnkNode[numWorkers + 1];
        for(int i=0;i < wayPoints.length;++i){
          wayPoints[i]=new IntSnglLnkNode(0);
        }
        for(int i=0;i < numWorkers;){
          final var threadHead=wayPoints[i];
          final var threadTail=wayPoints[++i];
          TestExecutorService.submitTest(()->{
            var curr=threadTail;
            for(int j=threadSpan;--j >= 0;){
              curr=new IntSnglLnkNode(0,curr);
            }
            threadHead.next=curr;
          });
        }
        TestExecutorService.completeAllTests();
        final var head=wayPoints[0];
        final var tail=wayPoints[wayPoints.length - 1];
        checkedQueue=new IntSnglLnkSeq.CheckedQueue(head,seqSize,tail);
        uncheckedQueue=new IntSnglLnkSeq.UncheckedQueue(head,seqSize,tail);
        checkedStack=new IntSnglLnkSeq.CheckedStack(head,seqSize);
        uncheckedStack=new IntSnglLnkSeq.UncheckedStack(head,seqSize);
        break;
      }
      case LONG:{
        final var wayPoints=new LongSnglLnkNode[numWorkers + 1];
        for(int i=0;i < wayPoints.length;++i){
          wayPoints[i]=new LongSnglLnkNode(0L);
        }
        for(int i=0;i < numWorkers;){
          final var threadHead=wayPoints[i];
          final var threadTail=wayPoints[++i];
          TestExecutorService.submitTest(()->{
            var curr=threadTail;
            for(int j=threadSpan;--j >= 0;){
              curr=new LongSnglLnkNode(0L,curr);
            }
            threadHead.next=curr;
          });
        }
        TestExecutorService.completeAllTests();
        final var head=wayPoints[0];
        final var tail=wayPoints[wayPoints.length - 1];
        checkedQueue=new LongSnglLnkSeq.CheckedQueue(head,seqSize,tail);
        uncheckedQueue=new LongSnglLnkSeq.UncheckedQueue(head,seqSize,tail);
        checkedStack=new LongSnglLnkSeq.CheckedStack(head,seqSize);
        uncheckedStack=new LongSnglLnkSeq.UncheckedStack(head,seqSize);
        break;
      }
      case FLOAT:{
        final var wayPoints=new FloatSnglLnkNode[numWorkers + 1];
        for(int i=0;i < wayPoints.length;++i){
          wayPoints[i]=new FloatSnglLnkNode(0F);
        }
        for(int i=0;i < numWorkers;){
          final var threadHead=wayPoints[i];
          final var threadTail=wayPoints[++i];
          TestExecutorService.submitTest(()->{
            var curr=threadTail;
            for(int j=threadSpan;--j >= 0;){
              curr=new FloatSnglLnkNode(0F,curr);
            }
            threadHead.next=curr;
          });
        }
        TestExecutorService.completeAllTests();
        final var head=wayPoints[0];
        final var tail=wayPoints[wayPoints.length - 1];
        checkedQueue=new FloatSnglLnkSeq.CheckedQueue(head,seqSize,tail);
        uncheckedQueue=new FloatSnglLnkSeq.UncheckedQueue(head,seqSize,tail);
        checkedStack=new FloatSnglLnkSeq.CheckedStack(head,seqSize);
        uncheckedStack=new FloatSnglLnkSeq.UncheckedStack(head,seqSize);
        break;
      }
      default:
        throw collectionType.invalid();
      }
      collectionType.verifyMASSIVEToString(uncheckedQueue.toString(),seqSize,
          collectionType.classPrefix + "SnglLnkSeq.UncheckedQueue.testMASSIVEtoString");
      collectionType.verifyMASSIVEToString(checkedQueue.toString(),seqSize,
          collectionType.classPrefix + "SnglLnkSeq.CheckedQueue.testMASSIVEtoString");
      collectionType.verifyMASSIVEToString(uncheckedStack.toString(),seqSize,
          collectionType.classPrefix + "SnglLnkSeq.UncheckedStack.testMASSIVEtoString");
      collectionType.verifyMASSIVEToString(checkedStack.toString(),seqSize,
          collectionType.classPrefix + "SnglLnkSeq.CheckedStack.testMASSIVEtoString");
    }
  }
  @Order(138)
  @Test public void testoffer_val(){
    final AddTest<MonitoredQueue<?>> test=(monitor,inputVal,inputType,functionCallType)->Assertions
        .assertTrue(monitor.verifyOffer(inputVal,inputType,functionCallType));
    test.runAllTests("SnglLnkSeqTest.testoffer_val",StructType.SnglLnkQueue);
  }
  @Order(172)
  @Test public void testpeek_void(){
    final PopTest<?> test=(monitor,outputType)->{
      monitor.verifyPeek(outputType);
      if(!monitor.isEmpty()){
        monitor.removeFirst();
      }
    };
    test.runAllTests("SnglLnkSeqTest.testpeek_void",false,ALL_STRUCTS);
  }
  @Order(172)
  @Test public void testpoll_void(){
    final PopTest<?> test=MonitoredSequence::verifyPoll;
    test.runAllTests("SnglLnkSeqTest.testpoll_void",false,ALL_STRUCTS);
  }
  @Order(86)
  @Test public void testpop_void(){
    final PopTest<SnglLnkStackMonitor<?,?>> test=MonitoredStack::verifyPop;
    test.runAllTests("SnglLnkSeqTest.testpop_void",true,StructType.SnglLnkStack);
  }
  @Order(138)
  @Test public void testpush_val(){
    final AddTest<MonitoredStack<?>> test=MonitoredStack::verifyPush;
    test.runAllTests("SnglLnkSeqTest.testpush_val",StructType.SnglLnkStack);
  }
  @Order(1310)
  @Test public void testReadAndWrite(){
    final MonitoredFunctionTest<MonitoredSequence<?>> test=(monitor,functionGen,functionCallType,randSeed)->{
      if(functionGen.expectedException == null){
        Assertions.assertDoesNotThrow(()->monitor.verifyReadAndWrite(functionGen));
      }else{
        Assertions.assertThrows(functionGen.expectedException,()->monitor.verifyReadAndWrite(functionGen));
      }
    };
    test.runAllTests("SnglLnkSeqTest.testReadAndWrite",0);
  }
  @Order(86)
  @Test public void testremove_void(){
    final PopTest<SnglLnkQueueMonitor<?,?>> test=MonitoredQueue::verifyRemove;
    test.runAllTests("SnglLnkSeqTest.testremove_void",true,StructType.SnglLnkQueue);
  }
  @Order(508680)
  @Test public void testremoveIf_Predicate(){
    for(final var collectionType:DataType.values()){
      for(final var checkedType:CheckedType.values()){
        for(final var structType:ALL_STRUCTS){
          for(final var functionCallType:collectionType.validFunctionCalls){
            for(final var filterGen:structType.validMonitoredRemoveIfPredicateGens){
              if(collectionType == DataType.BOOLEAN){
                for(int tmpSize=0;tmpSize <= 10;++tmpSize){
                  if((tmpSize == 0 || filterGen.expectedException == null || checkedType.checked)
                      && (tmpSize < 2 || !functionCallType.boxed)){
                    final int size=tmpSize;
                    final int initValBound=size > 0?1:0;
                    for(int tmpInitVal=0;tmpInitVal <= initValBound;++tmpInitVal){
                      final int initVal=tmpInitVal;
                      for(int tmpPeriod=0;tmpPeriod <= size;++tmpPeriod){
                        final int period=tmpPeriod;
                        switch(filterGen.predicateGenCallType){
                        case Randomized:
                          if(!functionCallType.boxed && size > 0 && filterGen.expectedException == null){
                            for(long tmpRandSeed=0;tmpRandSeed <= 100;++tmpRandSeed){
                              final long randSeed=tmpRandSeed;
                              for(final var threshold:RANDOM_THRESHOLDS){
                                TestExecutorService.submitTest(()->{
                                  final var monitor=SequenceInitialization.Ascending.initialize(
                                      getMonitoredSequence(structType,checkedType,collectionType,size),size,initVal,
                                      period);
                                  final var filter
                                      =filterGen.getMonitoredRemoveIfPredicate(monitor,threshold,new Random(randSeed));
                                  monitor.verifyRemoveIf(filter,functionCallType);
                                });
                              }
                            }
                            break;
                          }
                        case NonRandomized:
                          TestExecutorService.submitTest(()->{
                            final var monitor=SequenceInitialization.Ascending.initialize(
                                getMonitoredSequence(structType,checkedType,collectionType,size),size,initVal,period);
                            final var filter=filterGen.getMonitoredRemoveIfPredicate(monitor);
                            if(size == 0 || filterGen.expectedException == null){
                              monitor.verifyRemoveIf(filter,functionCallType);
                            }else{
                              Assertions.assertThrows(filterGen.expectedException,
                                  ()->monitor.verifyRemoveIf(filter,functionCallType));
                            }
                          });
                          break;
                        default:
                          throw filterGen.invalid();
                        }
                      }
                    }
                  }
                }
              }else{
                if(filterGen.expectedException == null || checkedType.checked){
                  switch(filterGen.predicateGenCallType){
                  case Randomized:
                    if(!functionCallType.boxed && filterGen.expectedException == null){
                      for(long tmpRandSeed=0;tmpRandSeed <= 100;++tmpRandSeed){
                        final long randSeed=tmpRandSeed;
                        for(final var threshold:RANDOM_THRESHOLDS){
                          TestExecutorService.submitTest(()->{
                            final var monitor=SequenceInitialization.Ascending
                                .initialize(getMonitoredSequence(structType,checkedType,collectionType,100),100,0);
                            final var filter
                                =filterGen.getMonitoredRemoveIfPredicate(monitor,threshold,new Random(randSeed));
                            for(;;){
                              monitor.verifyRemoveIf(filter,functionCallType);
                              if(monitor.isEmpty()){
                                break;
                              }
                              filter.reset(monitor);
                            }
                          });
                        }
                      }
                      break;
                    }
                  case NonRandomized:
                    for(final int size:REMOVE_IF_SIZES){
                      if((size == 0 || checkedType.checked || filterGen.expectedException == null)
                          && (size < 2 || !functionCallType.boxed)){
                        TestExecutorService.submitTest(()->{
                          final var monitor=SequenceInitialization.Ascending
                              .initialize(getMonitoredSequence(structType,checkedType,collectionType,size),size,0);
                          final var filter=filterGen.getMonitoredRemoveIfPredicate(monitor);
                          if(size == 0 || filterGen.expectedException == null){
                            monitor.verifyRemoveIf(filter,functionCallType);
                          }else{
                            Assertions.assertThrows(filterGen.expectedException,
                                ()->monitor.verifyRemoveIf(filter,functionCallType));
                          }
                        });
                      }
                    }
                    break;
                  default:
                    throw filterGen.invalid();
                  }
                }
              }
            }
          }
        }
      }
    }
    TestExecutorService.completeAllTests("SnglLnkSeqTest.testremoveIf_Predicate");
  }
  @Order(236988)
  @Test public void testremoveVal_val(){
    final QueryTest<MonitoredSequence<?>> test
        =(monitor,queryVal,inputType,castType,modification,monitoredObjectGen,position,seqSize)->{
          if(monitoredObjectGen == null){
            Assertions.assertEquals(position >= 0,monitor.verifyRemoveVal(queryVal,inputType,castType,modification));
          }else{
            monitor.verifyThrowingRemoveVal(monitoredObjectGen);
          }
        };
    test.runAllTests("SnglLnkSeqTest.testremoveVal_val",true);
  }
  @Order(118494)
  @Test public void testsearch_val(){
    final QueryTest<SnglLnkStackMonitor<?,?>> test
        =(monitor,queryVal,inputType,castType,modification,monitoredObjectGen,position,seqSize)->{
          if(monitoredObjectGen == null){
            int expectedIndex;
            if(position >= 0){
              int size;
              expectedIndex=(size=monitor.size()) - monitor
                  .findRemoveValIndex(queryVal.getInputVal(inputType,modification),inputType,0,size);
            }else{
              expectedIndex=-1;
            }
            Assertions.assertEquals(expectedIndex,monitor.verifySearch(queryVal,inputType,castType,modification));
          }else{
            monitor.verifyThrowingSearch(monitoredObjectGen);
          }
        };
    test.runAllTests("SnglLnkSeqTest.testsearch_val",false);
  }
  @Order(576)
  @Test public void testsize_void(){
    final BasicTest test=MonitoredSequence::verifySize;
    test.runAllTests("SnglLnkSeqTest.testsize_void");
  }
  @Order(1310)
  @Test public void testtoArray_IntFunction(){
    final MonitoredFunctionTest<MonitoredSequence<?>> test=(monitor,functionGen,functionCallType,randSeed)->{
      if(functionGen.expectedException == null){
        monitor.verifyToArray(functionGen);
      }else{
        Assertions.assertThrows(functionGen.expectedException,()->monitor.verifyToArray(functionGen));
      }
    };
    test.runAllTests("SnglLnkSeqTest.testtoArray_IntFunction",0);
  }
  @Order(23796)
  @Test public void testtoArray_ObjectArray(){
    for(final var collectionType:DataType.values()){
      for(final var checkedType:CheckedType.values()){
        for(final var structType:ALL_STRUCTS){
          for(final int size:SIZES){
            for(int tmpArrSize=0,tmpArrSizeBound=size + 5;tmpArrSize <= tmpArrSizeBound;++tmpArrSize){
              final int arrSize=tmpArrSize;
              TestExecutorService.submitTest(()->{
                final var monitor=SequenceInitialization.Ascending
                    .initialize(getMonitoredSequence(structType,checkedType,collectionType,size),size,0);
                monitor.verifyToArray(new Object[arrSize]);
              });
            }
          }
        }
      }
    }
    TestExecutorService.completeAllTests("DblLnkSeqTest.testtoArray_ObjectArray");
  }
  @Order(576)
  @Test public void testtoArray_void(){
    final BasicTest test=(monitor)->{
      for(final var outputType:monitor.getDataType().validOutputTypes()){
        outputType.verifyToArray(monitor);
      }
    };
    test.runAllTests("SnglLnkSeqTest.testtoArray_void");
  }
  @Order(738)
  @Test public void testtoString_void(){
    final ToStringAndHashCodeTest test=new ToStringAndHashCodeTest(){
      @Override public void callRaw(OmniCollection<?> seq){
        seq.toString();
      }
      @Override public void callVerify(MonitoredSequence<?> monitor){
        monitor.verifyToString();
      }
    };
    test.runAllTests("SnglLnkSeqTest.testtoString_void");
  }
  @org.junit.jupiter.api.AfterEach public void verifyAllExecuted(){
    int numTestsRemaining;
    if((numTestsRemaining=TestExecutorService.getNumRemainingTasks()) != 0){
      System.err.println("Warning: there were " + numTestsRemaining + " tests that were not completed");
    }
    TestExecutorService.reset();
  }
}
