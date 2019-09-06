package omni.impl.seq;
import java.io.Externalizable;
import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.api.OmniCollection;
import omni.api.OmniIterator;
import omni.api.OmniList;
import omni.api.OmniListIterator;
import omni.api.OmniStack;
import omni.impl.CheckedType;
import omni.impl.DataType;
import omni.impl.FunctionCallType;
import omni.impl.IllegalModification;
import omni.impl.IteratorType;
import omni.impl.MonitoredCollection;
import omni.impl.MonitoredFunction;
import omni.impl.MonitoredFunctionGen;
import omni.impl.MonitoredList;
import omni.impl.MonitoredObjectGen;
import omni.impl.MonitoredObjectOutputStream;
import omni.impl.MonitoredRemoveIfPredicate;
import omni.impl.MonitoredRemoveIfPredicateGen.PredicateGenCallType;
import omni.impl.MonitoredSequence;
import omni.impl.MonitoredStack;
import omni.impl.QueryCastType;
import omni.impl.QueryVal;
import omni.impl.QueryVal.QueryValModification;
import omni.impl.StructType;
import omni.util.NotYetImplementedException;
import omni.util.OmniArray;
import omni.util.TestExecutorService;
public class ArrSeqTest{
  private static abstract class AbstractArrSeqMonitor<SEQ extends AbstractSeq<?>&Externalizable>
      extends AbstractSequenceMonitor<SEQ>{
    AbstractArrSeqMonitor(CheckedType checkedType,DataType dataType){
      super(checkedType,dataType);
    }
    AbstractArrSeqMonitor(CheckedType checkedType,DataType dataType,int initCap){
      super(checkedType,dataType,initCap);
    }
    @Override public void copyListContents(){
      final int expectedSize=seq.size;
      switch(dataType){
      case BOOLEAN:{
        final var castSeq=(BooleanArrSeq)seq;
        final var actualArr=castSeq.arr;
        var expectedArr=(boolean[])this.expectedArr;
        if(actualArr == null || actualArr == OmniArray.OfBoolean.DEFAULT_ARR){
          this.expectedArr=actualArr;
          expectedCapacity=0;
        }else{
          if(expectedArr == null){
            System.arraycopy(actualArr,0,this.expectedArr=new boolean[actualArr.length],0,expectedSize);
          }else{
            if(expectedArr.length != actualArr.length){
              this.expectedArr=expectedArr=new boolean[actualArr.length];
            }
            System.arraycopy(actualArr,0,expectedArr,0,expectedSize);
          }
          expectedCapacity=actualArr.length;
        }
        break;
      }
      case BYTE:{
        final var castSeq=(ByteArrSeq)seq;
        final var actualArr=castSeq.arr;
        var expectedArr=(byte[])this.expectedArr;
        if(actualArr == null || actualArr == OmniArray.OfByte.DEFAULT_ARR){
          this.expectedArr=actualArr;
          expectedCapacity=0;
        }else{
          if(expectedArr == null){
            System.arraycopy(actualArr,0,this.expectedArr=new byte[actualArr.length],0,expectedSize);
          }else{
            if(expectedArr.length != actualArr.length){
              this.expectedArr=expectedArr=new byte[actualArr.length];
            }
            System.arraycopy(actualArr,0,expectedArr,0,expectedSize);
          }
          expectedCapacity=actualArr.length;
        }
        break;
      }
      case CHAR:{
        final var castSeq=(CharArrSeq)seq;
        final var actualArr=castSeq.arr;
        var expectedArr=(char[])this.expectedArr;
        if(actualArr == null || actualArr == OmniArray.OfChar.DEFAULT_ARR){
          this.expectedArr=actualArr;
          expectedCapacity=0;
        }else{
          if(expectedArr == null){
            System.arraycopy(actualArr,0,this.expectedArr=new char[actualArr.length],0,expectedSize);
          }else{
            if(expectedArr.length != actualArr.length){
              this.expectedArr=expectedArr=new char[actualArr.length];
            }
            System.arraycopy(actualArr,0,expectedArr,0,expectedSize);
          }
          expectedCapacity=actualArr.length;
        }
        break;
      }
      case SHORT:{
        final var castSeq=(ShortArrSeq)seq;
        final var actualArr=castSeq.arr;
        var expectedArr=(short[])this.expectedArr;
        if(actualArr == null || actualArr == OmniArray.OfShort.DEFAULT_ARR){
          this.expectedArr=actualArr;
          expectedCapacity=0;
        }else{
          if(expectedArr == null){
            System.arraycopy(actualArr,0,this.expectedArr=new short[actualArr.length],0,expectedSize);
          }else{
            if(expectedArr.length != actualArr.length){
              this.expectedArr=expectedArr=new short[actualArr.length];
            }
            System.arraycopy(actualArr,0,expectedArr,0,expectedSize);
          }
          expectedCapacity=actualArr.length;
        }
        break;
      }
      case INT:{
        final var castSeq=(IntArrSeq)seq;
        final var actualArr=castSeq.arr;
        var expectedArr=(int[])this.expectedArr;
        if(actualArr == null || actualArr == OmniArray.OfInt.DEFAULT_ARR){
          this.expectedArr=actualArr;
          expectedCapacity=0;
        }else{
          if(expectedArr == null){
            System.arraycopy(actualArr,0,this.expectedArr=new int[actualArr.length],0,expectedSize);
          }else{
            if(expectedArr.length != actualArr.length){
              this.expectedArr=expectedArr=new int[actualArr.length];
            }
            System.arraycopy(actualArr,0,expectedArr,0,expectedSize);
          }
          expectedCapacity=actualArr.length;
        }
        break;
      }
      case LONG:{
        final var castSeq=(LongArrSeq)seq;
        final var actualArr=castSeq.arr;
        var expectedArr=(long[])this.expectedArr;
        if(actualArr == null || actualArr == OmniArray.OfLong.DEFAULT_ARR){
          this.expectedArr=actualArr;
          expectedCapacity=0;
        }else{
          if(expectedArr == null){
            System.arraycopy(actualArr,0,this.expectedArr=new long[actualArr.length],0,expectedSize);
          }else{
            if(expectedArr.length != actualArr.length){
              this.expectedArr=expectedArr=new long[actualArr.length];
            }
            System.arraycopy(actualArr,0,expectedArr,0,expectedSize);
          }
          expectedCapacity=actualArr.length;
        }
        break;
      }
      case FLOAT:{
        final var castSeq=(FloatArrSeq)seq;
        final var actualArr=castSeq.arr;
        var expectedArr=(float[])this.expectedArr;
        if(actualArr == null || actualArr == OmniArray.OfFloat.DEFAULT_ARR){
          this.expectedArr=actualArr;
          expectedCapacity=0;
        }else{
          if(expectedArr == null){
            System.arraycopy(actualArr,0,this.expectedArr=new float[actualArr.length],0,expectedSize);
          }else{
            if(expectedArr.length != actualArr.length){
              this.expectedArr=expectedArr=new float[actualArr.length];
            }
            System.arraycopy(actualArr,0,expectedArr,0,expectedSize);
          }
          expectedCapacity=actualArr.length;
        }
        break;
      }
      case DOUBLE:{
        final var castSeq=(DoubleArrSeq)seq;
        final var actualArr=castSeq.arr;
        var expectedArr=(double[])this.expectedArr;
        if(actualArr == null || actualArr == OmniArray.OfDouble.DEFAULT_ARR){
          this.expectedArr=actualArr;
          expectedCapacity=0;
        }else{
          if(expectedArr == null){
            System.arraycopy(actualArr,0,this.expectedArr=new double[actualArr.length],0,expectedSize);
          }else{
            if(expectedArr.length != actualArr.length){
              this.expectedArr=expectedArr=new double[actualArr.length];
            }
            System.arraycopy(actualArr,0,expectedArr,0,expectedSize);
          }
          expectedCapacity=actualArr.length;
        }
        break;
      }
      case REF:{
        final var castSeq=(RefArrSeq<?>)seq;
        final var actualArr=castSeq.arr;
        var expectedArr=(Object[])this.expectedArr;
        if(actualArr == null || actualArr == OmniArray.OfRef.DEFAULT_ARR){
          this.expectedArr=actualArr;
          expectedCapacity=0;
        }else{
          if(expectedArr == null){
            System.arraycopy(actualArr,0,this.expectedArr=new Object[actualArr.length],0,expectedSize);
          }else{
            if(expectedArr.length != actualArr.length){
              this.expectedArr=expectedArr=new Object[actualArr.length];
            }
            System.arraycopy(actualArr,0,expectedArr,0,expectedSize);
          }
          expectedCapacity=actualArr.length;
        }
        break;
      }
      default:
        throw dataType.invalid();
      }
    }
    @Override public void verifyArrayIsCopy(Object arr,boolean emptyArrayMayBeSame){
      Object expectedArr;
      switch(dataType){
      case BOOLEAN:
        expectedArr=((BooleanArrSeq)seq).arr;
        if(arr instanceof boolean[]){
          break;
        }
        return;
      case BYTE:
        expectedArr=((ByteArrSeq)seq).arr;
        if(arr instanceof byte[]){
          break;
        }
        return;
      case CHAR:
        expectedArr=((CharArrSeq)seq).arr;
        if(arr instanceof char[]){
          break;
        }
        return;
      case SHORT:
        expectedArr=((ShortArrSeq)seq).arr;
        if(arr instanceof short[]){
          break;
        }
        return;
      case INT:
        expectedArr=((IntArrSeq)seq).arr;
        if(arr instanceof int[]){
          break;
        }
        return;
      case LONG:
        expectedArr=((LongArrSeq)seq).arr;
        if(arr instanceof long[]){
          break;
        }
        return;
      case FLOAT:
        expectedArr=((FloatArrSeq)seq).arr;
        if(arr instanceof float[]){
          break;
        }
        return;
      case DOUBLE:
        expectedArr=((DoubleArrSeq)seq).arr;
        if(arr instanceof double[]){
          break;
        }
        return;
      case REF:
        expectedArr=((RefArrSeq<?>)seq).arr;
        if(arr instanceof Object[]){
          break;
        }
        return;
      default:
        throw dataType.invalid();
      }
      if(expectedCapacity == 0 && emptyArrayMayBeSame && expectedArr != null){
        Assertions.assertSame(expectedArr,arr);
      }else{
        Assertions.assertNotSame(expectedArr,arr);
      }
    }
    @Override public void verifyClone(Object clone){
      verifyCloneTypeAndModCount(clone);
      Assertions.assertNotSame(clone,seq);
      int size;
      Assertions.assertEquals(size=((AbstractSeq<?>)seq).size,((AbstractSeq<?>)clone).size);
      switch(dataType){
      case BOOLEAN:{
        final var origArr=((BooleanArrSeq)seq).arr;
        final var cloneArr=((BooleanArrSeq)clone).arr;
        if(origArr == OmniArray.OfBoolean.DEFAULT_ARR){
          Assertions.assertSame(origArr,cloneArr);
        }else{
          Assertions.assertNotSame(origArr,cloneArr);
          while(size != 0){
            Assertions.assertEquals(origArr[--size],cloneArr[size]);
          }
        }
        break;
      }
      case BYTE:{
        final var origArr=((ByteArrSeq)seq).arr;
        final var cloneArr=((ByteArrSeq)clone).arr;
        if(origArr == OmniArray.OfByte.DEFAULT_ARR){
          Assertions.assertSame(origArr,cloneArr);
        }else{
          Assertions.assertNotSame(origArr,cloneArr);
          while(size != 0){
            Assertions.assertEquals(origArr[--size],cloneArr[size]);
          }
        }
        break;
      }
      case CHAR:{
        final var origArr=((CharArrSeq)seq).arr;
        final var cloneArr=((CharArrSeq)clone).arr;
        if(origArr == OmniArray.OfChar.DEFAULT_ARR){
          Assertions.assertSame(origArr,cloneArr);
        }else{
          Assertions.assertNotSame(origArr,cloneArr);
          while(size != 0){
            Assertions.assertEquals(origArr[--size],cloneArr[size]);
          }
        }
        break;
      }
      case SHORT:{
        final var origArr=((ShortArrSeq)seq).arr;
        final var cloneArr=((ShortArrSeq)clone).arr;
        if(origArr == OmniArray.OfShort.DEFAULT_ARR){
          Assertions.assertSame(origArr,cloneArr);
        }else{
          Assertions.assertNotSame(origArr,cloneArr);
          while(size != 0){
            Assertions.assertEquals(origArr[--size],cloneArr[size]);
          }
        }
        break;
      }
      case INT:{
        final var origArr=((IntArrSeq)seq).arr;
        final var cloneArr=((IntArrSeq)clone).arr;
        if(origArr == OmniArray.OfInt.DEFAULT_ARR){
          Assertions.assertSame(origArr,cloneArr);
        }else{
          Assertions.assertNotSame(origArr,cloneArr);
          while(size != 0){
            Assertions.assertEquals(origArr[--size],cloneArr[size]);
          }
        }
        break;
      }
      case LONG:{
        final var origArr=((LongArrSeq)seq).arr;
        final var cloneArr=((LongArrSeq)clone).arr;
        if(origArr == OmniArray.OfLong.DEFAULT_ARR){
          Assertions.assertSame(origArr,cloneArr);
        }else{
          Assertions.assertNotSame(origArr,cloneArr);
          while(size != 0){
            Assertions.assertEquals(origArr[--size],cloneArr[size]);
          }
        }
        break;
      }
      case FLOAT:{
        final var origArr=((FloatArrSeq)seq).arr;
        final var cloneArr=((FloatArrSeq)clone).arr;
        if(origArr == OmniArray.OfFloat.DEFAULT_ARR){
          Assertions.assertSame(origArr,cloneArr);
        }else{
          Assertions.assertNotSame(origArr,cloneArr);
          while(size != 0){
            Assertions.assertEquals(origArr[--size],cloneArr[size]);
          }
        }
        break;
      }
      case DOUBLE:{
        final var origArr=((DoubleArrSeq)seq).arr;
        final var cloneArr=((DoubleArrSeq)clone).arr;
        if(origArr == OmniArray.OfDouble.DEFAULT_ARR){
          Assertions.assertSame(origArr,cloneArr);
        }else{
          Assertions.assertNotSame(origArr,cloneArr);
          while(size != 0){
            Assertions.assertEquals(origArr[--size],cloneArr[size]);
          }
        }
        break;
      }
      case REF:{
        final var origArr=((RefArrSeq<?>)seq).arr;
        final var cloneArr=((RefArrSeq<?>)clone).arr;
        if(origArr == OmniArray.OfRef.DEFAULT_ARR){
          Assertions.assertSame(origArr,cloneArr);
        }else{
          Assertions.assertNotSame(origArr,cloneArr);
          while(size != 0){
            Assertions.assertSame(origArr[--size],cloneArr[size]);
          }
        }
        break;
      }
      default:
        throw dataType.invalid();
      }
    }
    @Override public void verifyCollectionState(){
      verifyCollectionState(true);
    }
    public void verifyCollectionState(boolean refIsSame){
      int expectedSize;
      Assertions.assertEquals(expectedSize=this.expectedSize,((AbstractSeq<?>)seq).size);
      if(checkedType.checked){
        verifyModCount();
      }
      switch(dataType){
      case BOOLEAN:{
        final var actualArr=((BooleanArrSeq)seq).arr;
        int expectedCapacity;
        if((expectedCapacity=this.expectedCapacity) == 0){
          Assertions.assertSame(expectedArr,actualArr);
        }else{
          Assertions.assertEquals(expectedCapacity,actualArr.length);
          final var expectedArr=(boolean[])this.expectedArr;
          while(expectedSize != 0){
            Assertions.assertEquals(expectedArr[--expectedSize],actualArr[expectedSize]);
          }
        }
        break;
      }
      case BYTE:{
        final var actualArr=((ByteArrSeq)seq).arr;
        int expectedCapacity;
        if((expectedCapacity=this.expectedCapacity) == 0){
          Assertions.assertSame(expectedArr,actualArr);
        }else{
          Assertions.assertEquals(expectedCapacity,actualArr.length);
          final var expectedArr=(byte[])this.expectedArr;
          while(expectedSize != 0){
            Assertions.assertEquals(expectedArr[--expectedSize],actualArr[expectedSize]);
          }
        }
        break;
      }
      case CHAR:{
        final var actualArr=((CharArrSeq)seq).arr;
        int expectedCapacity;
        if((expectedCapacity=this.expectedCapacity) == 0){
          Assertions.assertSame(expectedArr,actualArr);
        }else{
          Assertions.assertEquals(expectedCapacity,actualArr.length);
          final var expectedArr=(char[])this.expectedArr;
          while(expectedSize != 0){
            Assertions.assertEquals(expectedArr[--expectedSize],actualArr[expectedSize]);
          }
        }
        break;
      }
      case DOUBLE:{
        final var actualArr=((DoubleArrSeq)seq).arr;
        int expectedCapacity;
        if((expectedCapacity=this.expectedCapacity) == 0){
          Assertions.assertSame(expectedArr,actualArr);
        }else{
          Assertions.assertEquals(expectedCapacity,actualArr.length);
          final var expectedArr=(double[])this.expectedArr;
          while(expectedSize != 0){
            Assertions.assertEquals(expectedArr[--expectedSize],actualArr[expectedSize]);
          }
        }
        break;
      }
      case FLOAT:{
        final var actualArr=((FloatArrSeq)seq).arr;
        int expectedCapacity;
        if((expectedCapacity=this.expectedCapacity) == 0){
          Assertions.assertSame(expectedArr,actualArr);
        }else{
          Assertions.assertEquals(expectedCapacity,actualArr.length);
          final var expectedArr=(float[])this.expectedArr;
          while(expectedSize != 0){
            Assertions.assertEquals(expectedArr[--expectedSize],actualArr[expectedSize]);
          }
        }
        break;
      }
      case INT:{
        final var actualArr=((IntArrSeq)seq).arr;
        int expectedCapacity;
        if((expectedCapacity=this.expectedCapacity) == 0){
          Assertions.assertSame(expectedArr,actualArr);
        }else{
          Assertions.assertEquals(expectedCapacity,actualArr.length);
          final var expectedArr=(int[])this.expectedArr;
          while(expectedSize != 0){
            Assertions.assertEquals(expectedArr[--expectedSize],actualArr[expectedSize]);
          }
        }
        break;
      }
      case LONG:{
        final var actualArr=((LongArrSeq)seq).arr;
        int expectedCapacity;
        if((expectedCapacity=this.expectedCapacity) == 0){
          Assertions.assertSame(expectedArr,actualArr);
        }else{
          Assertions.assertEquals(expectedCapacity,actualArr.length);
          final var expectedArr=(long[])this.expectedArr;
          while(expectedSize != 0){
            Assertions.assertEquals(expectedArr[--expectedSize],actualArr[expectedSize]);
          }
        }
        break;
      }
      case REF:{
        final var actualArr=((RefArrSeq<?>)seq).arr;
        int expectedCapacity;
        if((expectedCapacity=this.expectedCapacity) == 0){
          Assertions.assertSame(expectedArr,actualArr);
        }else{
          final var expectedArr=(Object[])this.expectedArr;
          int i=0;
          if(refIsSame){
            while(i != expectedSize){
              Assertions.assertSame(expectedArr[i],actualArr[i++]);
            }
          }else{
            while(i != expectedSize){
              Assertions.assertEquals(expectedArr[i],actualArr[i++]);
            }
          }
          while(i != expectedSize){
            Assertions.assertSame(expectedArr[i],actualArr[i++]);
          }
          while(i != expectedCapacity){
            Assertions.assertNull(actualArr[i++]);
          }
        }
        break;
      }
      case SHORT:{
        final var actualArr=((ShortArrSeq)seq).arr;
        int expectedCapacity;
        if((expectedCapacity=this.expectedCapacity) == 0){
          Assertions.assertSame(expectedArr,actualArr);
        }else{
          Assertions.assertEquals(expectedCapacity,actualArr.length);
          final var expectedArr=(short[])this.expectedArr;
          while(expectedSize != 0){
            Assertions.assertEquals(expectedArr[--expectedSize],actualArr[expectedSize]);
          }
        }
        break;
      }
      default:
        throw dataType.invalid();
      }
    }
    @Override public void verifyReadAndWriteClone(SEQ readCol){
      verifyCloneTypeAndModCount(readCol);
      Assertions.assertNotSame(readCol,seq);
      int size;
      Assertions.assertEquals(size=((AbstractSeq<?>)seq).size,((AbstractSeq<?>)readCol).size);
      final var origArr=((RefArrSeq<?>)seq).arr;
      final var cloneArr=((RefArrSeq<?>)readCol).arr;
      if(origArr == OmniArray.OfRef.DEFAULT_ARR){
        Assertions.assertSame(origArr,cloneArr);
      }else{
        Assertions.assertNotSame(origArr,cloneArr);
        while(size != 0){
          Assertions.assertEquals(origArr[--size],cloneArr[size]);
        }
      }
    }
    @Override abstract SEQ initSeq();
    @Override abstract SEQ initSeq(int initCap);
    abstract void verifyCloneTypeAndModCount(Object clone);
    abstract void verifyModCount();
  }
  static class ArrListMonitor<SEQ extends AbstractSeq<E>&OmniList<E>&Externalizable,E>
      extends AbstractArrSeqMonitor<SEQ> implements MonitoredList<SEQ>{
    public static class ArrSubListMonitor<SUBLIST extends AbstractSeq<E>&OmniList<E>,
        SEQ extends AbstractSeq<E>&OmniList<E>&Externalizable,E> implements MonitoredList<SUBLIST>{
        
      
        
      private abstract class AbstractItrMonitor<ITR extends OmniIterator<?>> implements MonitoredIterator<ITR,SUBLIST>{
        final ITR itr;
        int expectedCursor;
        int expectedItrModCount;
        int expectedLastRet;
        int lastRetState;
        AbstractItrMonitor(ITR itr,int expectedCursor){
          this.itr=itr;
          this.expectedCursor=expectedCursor;
          this.expectedItrModCount=expectedModCount;
          if(expectedRoot.checkedType.checked){
            this.expectedLastRet=-1;
          }
          this.lastRetState=-1;
        }
        @Override public ITR getIterator(){
          return itr;
        }
        @Override public MonitoredCollection<SUBLIST> getMonitoredCollection(){
          return ArrSubListMonitor.this;
        }
        @Override public int getNumLeft(){
          return expectedRootOffset + expectedSize - expectedCursor;
        }
        @Override public boolean hasNext(){
          return expectedCursor < expectedRootOffset + expectedSize;
        }
        @Override public boolean nextWasJustCalled(){
          return lastRetState == 0;
        }
        @Override public void updateItrNextState(){
          this.expectedLastRet=expectedCursor++;
          lastRetState=0;
        }
        @Override public void updateItrRemoveState(){
          expectedRoot.updateRemoveIndexState(expectedCursor=expectedLastRet);
          ++expectedItrModCount;
          bubbleUpModifySize(-1);
          if(expectedRoot.checkedType.checked){
            expectedLastRet=-1;
          }
          lastRetState=-1;
        }
        @Override public void verifyForEachRemaining(MonitoredFunction function){
          final var functionItr=function.iterator();
          IntConsumer functionVerifier;
          final var dataType=expectedRoot.dataType;
          switch(dataType){
          case BOOLEAN:{
            final var expectedArr=(boolean[])expectedRoot.expectedArr;
            functionVerifier=cursor->Assertions.assertEquals(expectedArr[cursor],(boolean)functionItr.next());
            break;
          }
          case BYTE:{
            final var expectedArr=(byte[])expectedRoot.expectedArr;
            functionVerifier=cursor->Assertions.assertEquals(expectedArr[cursor],(byte)functionItr.next());
            break;
          }
          case CHAR:{
            final var expectedArr=(char[])expectedRoot.expectedArr;
            functionVerifier=cursor->Assertions.assertEquals(expectedArr[cursor],(char)functionItr.next());
            break;
          }
          case SHORT:{
            final var expectedArr=(short[])expectedRoot.expectedArr;
            functionVerifier=cursor->Assertions.assertEquals(expectedArr[cursor],(short)functionItr.next());
            break;
          }
          case INT:{
            final var expectedArr=(int[])expectedRoot.expectedArr;
            functionVerifier=cursor->Assertions.assertEquals(expectedArr[cursor],(int)functionItr.next());
            break;
          }
          case LONG:{
            final var expectedArr=(long[])expectedRoot.expectedArr;
            functionVerifier=cursor->Assertions.assertEquals(expectedArr[cursor],(long)functionItr.next());
            break;
          }
          case FLOAT:{
            final var expectedArr=(float[])expectedRoot.expectedArr;
            functionVerifier=cursor->Assertions.assertEquals(expectedArr[cursor],(float)functionItr.next());
            break;
          }
          case DOUBLE:{
            final var expectedArr=(double[])expectedRoot.expectedArr;
            functionVerifier=cursor->Assertions.assertEquals(expectedArr[cursor],(double)functionItr.next());
            break;
          }
          case REF:{
            final var expectedArr=(Object[])expectedRoot.expectedArr;
            functionVerifier=cursor->Assertions.assertSame(expectedArr[cursor],functionItr.next());
            break;
          }
          default:
            throw dataType.invalid();
          }
          int expectedLastRet=this.expectedLastRet;
          int expectedCursor=this.expectedCursor;
          int lastRetState=this.lastRetState;
          final int expectedBound=expectedSize + expectedRootOffset;
          while(expectedCursor < expectedBound){
            functionVerifier.accept(expectedCursor);
            expectedLastRet=expectedCursor++;
            lastRetState=0;
          }
          this.expectedCursor=expectedCursor;
          this.expectedLastRet=expectedLastRet;
          this.lastRetState=lastRetState;
        }
        @Override public void verifyNextResult(DataType outputType,Object result){
          expectedRoot.verifyGetResult(expectedCursor,result,outputType);
        }
      }
      private class ItrMonitor extends AbstractItrMonitor<OmniIterator<?>>{
        ItrMonitor(){
          super(seq.iterator(),expectedRootOffset);
        }
        @Override public IteratorType getIteratorType(){
          return IteratorType.SubAscendingItr;
        }
        @Override public void verifyCloneHelper(Object clone){
          final var checked=expectedRoot.checkedType.checked;
          switch(expectedRoot.dataType){
          case BOOLEAN:
            if(checked){
              Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanArrSeq.CheckedSubList.Itr.parent(clone));
              Assertions.assertEquals(expectedCursor,
                  FieldAndMethodAccessor.BooleanArrSeq.CheckedSubList.Itr.cursor(clone));
              Assertions.assertEquals(expectedLastRet,
                  FieldAndMethodAccessor.BooleanArrSeq.CheckedSubList.Itr.lastRet(clone));
              Assertions.assertEquals(expectedItrModCount,
                  FieldAndMethodAccessor.BooleanArrSeq.CheckedSubList.Itr.modCount(clone));
            }else{
              Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanArrSeq.UncheckedSubList.Itr.parent(clone));
              Assertions.assertEquals(expectedCursor,
                  FieldAndMethodAccessor.BooleanArrSeq.UncheckedSubList.Itr.cursor(clone));
            }
            break;
          case BYTE:
            if(checked){
              Assertions.assertSame(seq,FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.Itr.parent(clone));
              Assertions.assertEquals(expectedCursor,
                  FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.Itr.cursor(clone));
              Assertions.assertEquals(expectedLastRet,
                  FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.Itr.lastRet(clone));
              Assertions.assertEquals(expectedItrModCount,
                  FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.Itr.modCount(clone));
            }else{
              Assertions.assertSame(seq,FieldAndMethodAccessor.ByteArrSeq.UncheckedSubList.Itr.parent(clone));
              Assertions.assertEquals(expectedCursor,
                  FieldAndMethodAccessor.ByteArrSeq.UncheckedSubList.Itr.cursor(clone));
            }
            break;
          case CHAR:
            if(checked){
              Assertions.assertSame(seq,FieldAndMethodAccessor.CharArrSeq.CheckedSubList.Itr.parent(clone));
              Assertions.assertEquals(expectedCursor,
                  FieldAndMethodAccessor.CharArrSeq.CheckedSubList.Itr.cursor(clone));
              Assertions.assertEquals(expectedLastRet,
                  FieldAndMethodAccessor.CharArrSeq.CheckedSubList.Itr.lastRet(clone));
              Assertions.assertEquals(expectedItrModCount,
                  FieldAndMethodAccessor.CharArrSeq.CheckedSubList.Itr.modCount(clone));
            }else{
              Assertions.assertSame(seq,FieldAndMethodAccessor.CharArrSeq.UncheckedSubList.Itr.parent(clone));
              Assertions.assertEquals(expectedCursor,
                  FieldAndMethodAccessor.CharArrSeq.UncheckedSubList.Itr.cursor(clone));
            }
            break;
          case DOUBLE:
            if(checked){
              Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleArrSeq.CheckedSubList.Itr.parent(clone));
              Assertions.assertEquals(expectedCursor,
                  FieldAndMethodAccessor.DoubleArrSeq.CheckedSubList.Itr.cursor(clone));
              Assertions.assertEquals(expectedLastRet,
                  FieldAndMethodAccessor.DoubleArrSeq.CheckedSubList.Itr.lastRet(clone));
              Assertions.assertEquals(expectedItrModCount,
                  FieldAndMethodAccessor.DoubleArrSeq.CheckedSubList.Itr.modCount(clone));
            }else{
              Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleArrSeq.UncheckedSubList.Itr.parent(clone));
              Assertions.assertEquals(expectedCursor,
                  FieldAndMethodAccessor.DoubleArrSeq.UncheckedSubList.Itr.cursor(clone));
            }
            break;
          case FLOAT:
            if(checked){
              Assertions.assertSame(seq,FieldAndMethodAccessor.FloatArrSeq.CheckedSubList.Itr.parent(clone));
              Assertions.assertEquals(expectedCursor,
                  FieldAndMethodAccessor.FloatArrSeq.CheckedSubList.Itr.cursor(clone));
              Assertions.assertEquals(expectedLastRet,
                  FieldAndMethodAccessor.FloatArrSeq.CheckedSubList.Itr.lastRet(clone));
              Assertions.assertEquals(expectedItrModCount,
                  FieldAndMethodAccessor.FloatArrSeq.CheckedSubList.Itr.modCount(clone));
            }else{
              Assertions.assertSame(seq,FieldAndMethodAccessor.FloatArrSeq.UncheckedSubList.Itr.parent(clone));
              Assertions.assertEquals(expectedCursor,
                  FieldAndMethodAccessor.FloatArrSeq.UncheckedSubList.Itr.cursor(clone));
            }
            break;
          case INT:
            if(checked){
              Assertions.assertSame(seq,FieldAndMethodAccessor.IntArrSeq.CheckedSubList.Itr.parent(clone));
              Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.IntArrSeq.CheckedSubList.Itr.cursor(clone));
              Assertions.assertEquals(expectedLastRet,
                  FieldAndMethodAccessor.IntArrSeq.CheckedSubList.Itr.lastRet(clone));
              Assertions.assertEquals(expectedItrModCount,
                  FieldAndMethodAccessor.IntArrSeq.CheckedSubList.Itr.modCount(clone));
            }else{
              Assertions.assertSame(seq,FieldAndMethodAccessor.IntArrSeq.UncheckedSubList.Itr.parent(clone));
              Assertions.assertEquals(expectedCursor,
                  FieldAndMethodAccessor.IntArrSeq.UncheckedSubList.Itr.cursor(clone));
            }
            break;
          case LONG:
            if(checked){
              Assertions.assertSame(seq,FieldAndMethodAccessor.LongArrSeq.CheckedSubList.Itr.parent(clone));
              Assertions.assertEquals(expectedCursor,
                  FieldAndMethodAccessor.LongArrSeq.CheckedSubList.Itr.cursor(clone));
              Assertions.assertEquals(expectedLastRet,
                  FieldAndMethodAccessor.LongArrSeq.CheckedSubList.Itr.lastRet(clone));
              Assertions.assertEquals(expectedItrModCount,
                  FieldAndMethodAccessor.LongArrSeq.CheckedSubList.Itr.modCount(clone));
            }else{
              Assertions.assertSame(seq,FieldAndMethodAccessor.LongArrSeq.UncheckedSubList.Itr.parent(clone));
              Assertions.assertEquals(expectedCursor,
                  FieldAndMethodAccessor.LongArrSeq.UncheckedSubList.Itr.cursor(clone));
            }
            break;
          case REF:
            if(checked){
              Assertions.assertSame(seq,FieldAndMethodAccessor.RefArrSeq.CheckedSubList.Itr.parent(clone));
              Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.RefArrSeq.CheckedSubList.Itr.cursor(clone));
              Assertions.assertEquals(expectedLastRet,
                  FieldAndMethodAccessor.RefArrSeq.CheckedSubList.Itr.lastRet(clone));
              Assertions.assertEquals(expectedItrModCount,
                  FieldAndMethodAccessor.RefArrSeq.CheckedSubList.Itr.modCount(clone));
            }else{
              Assertions.assertSame(seq,FieldAndMethodAccessor.RefArrSeq.UncheckedSubList.Itr.parent(clone));
              Assertions.assertEquals(expectedCursor,
                  FieldAndMethodAccessor.RefArrSeq.UncheckedSubList.Itr.cursor(clone));
            }
            break;
          case SHORT:
            if(checked){
              Assertions.assertSame(seq,FieldAndMethodAccessor.ShortArrSeq.CheckedSubList.Itr.parent(clone));
              Assertions.assertEquals(expectedCursor,
                  FieldAndMethodAccessor.ShortArrSeq.CheckedSubList.Itr.cursor(clone));
              Assertions.assertEquals(expectedLastRet,
                  FieldAndMethodAccessor.ShortArrSeq.CheckedSubList.Itr.lastRet(clone));
              Assertions.assertEquals(expectedItrModCount,
                  FieldAndMethodAccessor.ShortArrSeq.CheckedSubList.Itr.modCount(clone));
            }else{
              Assertions.assertSame(seq,FieldAndMethodAccessor.ShortArrSeq.UncheckedSubList.Itr.parent(clone));
              Assertions.assertEquals(expectedCursor,
                  FieldAndMethodAccessor.ShortArrSeq.UncheckedSubList.Itr.cursor(clone));
            }
            break;
          default:
            throw expectedRoot.dataType.invalid();
          }
        }
      }
      private class ListItrMonitor extends AbstractItrMonitor<OmniListIterator<?>>
          implements MonitoredListIterator<OmniListIterator<?>,SUBLIST>{
        ListItrMonitor(){
          super(seq.listIterator(),expectedRootOffset);
        }
        ListItrMonitor(int index){
          super(seq.listIterator(index),index + expectedRootOffset);
        }
        @Override public IteratorType getIteratorType(){
          return IteratorType.SubBidirectionalItr;
        }
        @Override public boolean hasPrevious(){
          return expectedCursor > expectedRootOffset;
        }
        @Override public int nextIndex(){
          return expectedCursor - expectedRootOffset;
        }
        @Override public int previousIndex(){
          return expectedCursor - expectedRootOffset - 1;
        }
        @Override public boolean previousWasJustCalled(){
          return lastRetState == 1;
        }
        @Override public void updateItrAddState(Object input,DataType inputType){
          expectedRoot.updateAddState(expectedCursor++,input,inputType);
          bubbleUpModifySize(1);
          ++expectedItrModCount;
          if(expectedRoot.checkedType.checked){
            expectedLastRet=-1;
          }
          lastRetState=-1;
        }
        @Override public void updateItrPreviousState(){
          expectedLastRet=--expectedCursor;
          lastRetState=1;
        }
        @Override public void updateItrSetState(Object input,DataType inputType){
          expectedRoot.verifyPutResult(expectedLastRet,input,inputType);
        }
        @Override public void verifyCloneHelper(Object clone){
          final var checked=expectedRoot.checkedType.checked;
          switch(expectedRoot.dataType){
          case BOOLEAN:
            if(checked){
              Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanArrSeq.CheckedSubList.ListItr.parent(clone));
              Assertions.assertEquals(expectedCursor,
                  FieldAndMethodAccessor.BooleanArrSeq.CheckedSubList.ListItr.cursor(clone));
              Assertions.assertEquals(expectedLastRet,
                  FieldAndMethodAccessor.BooleanArrSeq.CheckedSubList.ListItr.lastRet(clone));
              Assertions.assertEquals(expectedItrModCount,
                  FieldAndMethodAccessor.BooleanArrSeq.CheckedSubList.ListItr.modCount(clone));
            }else{
              Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanArrSeq.UncheckedSubList.ListItr.parent(clone));
              Assertions.assertEquals(expectedCursor,
                  FieldAndMethodAccessor.BooleanArrSeq.UncheckedSubList.ListItr.cursor(clone));
              Assertions.assertEquals(expectedLastRet,
                  FieldAndMethodAccessor.BooleanArrSeq.UncheckedSubList.ListItr.lastRet(clone));
            }
            break;
          case BYTE:
            if(checked){
              Assertions.assertSame(seq,FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.ListItr.parent(clone));
              Assertions.assertEquals(expectedCursor,
                  FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.ListItr.cursor(clone));
              Assertions.assertEquals(expectedLastRet,
                  FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.ListItr.lastRet(clone));
              Assertions.assertEquals(expectedItrModCount,
                  FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.ListItr.modCount(clone));
            }else{
              Assertions.assertSame(seq,FieldAndMethodAccessor.ByteArrSeq.UncheckedSubList.ListItr.parent(clone));
              Assertions.assertEquals(expectedCursor,
                  FieldAndMethodAccessor.ByteArrSeq.UncheckedSubList.ListItr.cursor(clone));
              Assertions.assertEquals(expectedLastRet,
                  FieldAndMethodAccessor.ByteArrSeq.UncheckedSubList.ListItr.lastRet(clone));
            }
            break;
          case CHAR:
            if(checked){
              Assertions.assertSame(seq,FieldAndMethodAccessor.CharArrSeq.CheckedSubList.ListItr.parent(clone));
              Assertions.assertEquals(expectedCursor,
                  FieldAndMethodAccessor.CharArrSeq.CheckedSubList.ListItr.cursor(clone));
              Assertions.assertEquals(expectedLastRet,
                  FieldAndMethodAccessor.CharArrSeq.CheckedSubList.ListItr.lastRet(clone));
              Assertions.assertEquals(expectedItrModCount,
                  FieldAndMethodAccessor.CharArrSeq.CheckedSubList.ListItr.modCount(clone));
            }else{
              Assertions.assertSame(seq,FieldAndMethodAccessor.CharArrSeq.UncheckedSubList.ListItr.parent(clone));
              Assertions.assertEquals(expectedCursor,
                  FieldAndMethodAccessor.CharArrSeq.UncheckedSubList.ListItr.cursor(clone));
              Assertions.assertEquals(expectedLastRet,
                  FieldAndMethodAccessor.CharArrSeq.UncheckedSubList.ListItr.lastRet(clone));
            }
            break;
          case DOUBLE:
            if(checked){
              Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleArrSeq.CheckedSubList.ListItr.parent(clone));
              Assertions.assertEquals(expectedCursor,
                  FieldAndMethodAccessor.DoubleArrSeq.CheckedSubList.ListItr.cursor(clone));
              Assertions.assertEquals(expectedLastRet,
                  FieldAndMethodAccessor.DoubleArrSeq.CheckedSubList.ListItr.lastRet(clone));
              Assertions.assertEquals(expectedItrModCount,
                  FieldAndMethodAccessor.DoubleArrSeq.CheckedSubList.ListItr.modCount(clone));
            }else{
              Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleArrSeq.UncheckedSubList.ListItr.parent(clone));
              Assertions.assertEquals(expectedCursor,
                  FieldAndMethodAccessor.DoubleArrSeq.UncheckedSubList.ListItr.cursor(clone));
              Assertions.assertEquals(expectedLastRet,
                  FieldAndMethodAccessor.DoubleArrSeq.UncheckedSubList.ListItr.lastRet(clone));
            }
            break;
          case FLOAT:
            if(checked){
              Assertions.assertSame(seq,FieldAndMethodAccessor.FloatArrSeq.CheckedSubList.ListItr.parent(clone));
              Assertions.assertEquals(expectedCursor,
                  FieldAndMethodAccessor.FloatArrSeq.CheckedSubList.ListItr.cursor(clone));
              Assertions.assertEquals(expectedLastRet,
                  FieldAndMethodAccessor.FloatArrSeq.CheckedSubList.ListItr.lastRet(clone));
              Assertions.assertEquals(expectedItrModCount,
                  FieldAndMethodAccessor.FloatArrSeq.CheckedSubList.ListItr.modCount(clone));
            }else{
              Assertions.assertSame(seq,FieldAndMethodAccessor.FloatArrSeq.UncheckedSubList.ListItr.parent(clone));
              Assertions.assertEquals(expectedCursor,
                  FieldAndMethodAccessor.FloatArrSeq.UncheckedSubList.ListItr.cursor(clone));
              Assertions.assertEquals(expectedLastRet,
                  FieldAndMethodAccessor.FloatArrSeq.UncheckedSubList.ListItr.lastRet(clone));
            }
            break;
          case INT:
            if(checked){
              Assertions.assertSame(seq,FieldAndMethodAccessor.IntArrSeq.CheckedSubList.ListItr.parent(clone));
              Assertions.assertEquals(expectedCursor,
                  FieldAndMethodAccessor.IntArrSeq.CheckedSubList.ListItr.cursor(clone));
              Assertions.assertEquals(expectedLastRet,
                  FieldAndMethodAccessor.IntArrSeq.CheckedSubList.ListItr.lastRet(clone));
              Assertions.assertEquals(expectedItrModCount,
                  FieldAndMethodAccessor.IntArrSeq.CheckedSubList.ListItr.modCount(clone));
            }else{
              Assertions.assertSame(seq,FieldAndMethodAccessor.IntArrSeq.UncheckedSubList.ListItr.parent(clone));
              Assertions.assertEquals(expectedCursor,
                  FieldAndMethodAccessor.IntArrSeq.UncheckedSubList.ListItr.cursor(clone));
              Assertions.assertEquals(expectedLastRet,
                  FieldAndMethodAccessor.IntArrSeq.UncheckedSubList.ListItr.lastRet(clone));
            }
            break;
          case LONG:
            if(checked){
              Assertions.assertSame(seq,FieldAndMethodAccessor.LongArrSeq.CheckedSubList.ListItr.parent(clone));
              Assertions.assertEquals(expectedCursor,
                  FieldAndMethodAccessor.LongArrSeq.CheckedSubList.ListItr.cursor(clone));
              Assertions.assertEquals(expectedLastRet,
                  FieldAndMethodAccessor.LongArrSeq.CheckedSubList.ListItr.lastRet(clone));
              Assertions.assertEquals(expectedItrModCount,
                  FieldAndMethodAccessor.LongArrSeq.CheckedSubList.ListItr.modCount(clone));
            }else{
              Assertions.assertSame(seq,FieldAndMethodAccessor.LongArrSeq.UncheckedSubList.ListItr.parent(clone));
              Assertions.assertEquals(expectedCursor,
                  FieldAndMethodAccessor.LongArrSeq.UncheckedSubList.ListItr.cursor(clone));
              Assertions.assertEquals(expectedLastRet,
                  FieldAndMethodAccessor.LongArrSeq.UncheckedSubList.ListItr.lastRet(clone));
            }
            break;
          case REF:
            if(checked){
              Assertions.assertSame(seq,FieldAndMethodAccessor.RefArrSeq.CheckedSubList.ListItr.parent(clone));
              Assertions.assertEquals(expectedCursor,
                  FieldAndMethodAccessor.RefArrSeq.CheckedSubList.ListItr.cursor(clone));
              Assertions.assertEquals(expectedLastRet,
                  FieldAndMethodAccessor.RefArrSeq.CheckedSubList.ListItr.lastRet(clone));
              Assertions.assertEquals(expectedItrModCount,
                  FieldAndMethodAccessor.RefArrSeq.CheckedSubList.ListItr.modCount(clone));
            }else{
              Assertions.assertSame(seq,FieldAndMethodAccessor.RefArrSeq.UncheckedSubList.ListItr.parent(clone));
              Assertions.assertEquals(expectedCursor,
                  FieldAndMethodAccessor.RefArrSeq.UncheckedSubList.ListItr.cursor(clone));
              Assertions.assertEquals(expectedLastRet,
                  FieldAndMethodAccessor.RefArrSeq.UncheckedSubList.ListItr.lastRet(clone));
            }
            break;
          case SHORT:
            if(checked){
              Assertions.assertSame(seq,FieldAndMethodAccessor.ShortArrSeq.CheckedSubList.ListItr.parent(clone));
              Assertions.assertEquals(expectedCursor,
                  FieldAndMethodAccessor.ShortArrSeq.CheckedSubList.ListItr.cursor(clone));
              Assertions.assertEquals(expectedLastRet,
                  FieldAndMethodAccessor.ShortArrSeq.CheckedSubList.ListItr.lastRet(clone));
              Assertions.assertEquals(expectedItrModCount,
                  FieldAndMethodAccessor.ShortArrSeq.CheckedSubList.ListItr.modCount(clone));
            }else{
              Assertions.assertSame(seq,FieldAndMethodAccessor.ShortArrSeq.UncheckedSubList.ListItr.parent(clone));
              Assertions.assertEquals(expectedCursor,
                  FieldAndMethodAccessor.ShortArrSeq.UncheckedSubList.ListItr.cursor(clone));
              Assertions.assertEquals(expectedLastRet,
                  FieldAndMethodAccessor.ShortArrSeq.UncheckedSubList.ListItr.lastRet(clone));
            }
            break;
          default:
            throw expectedRoot.dataType.invalid();
          }
        }
        @Override public void verifyPreviousResult(DataType outputType,Object result){
          expectedRoot.verifyGetResult(expectedLastRet,result,outputType);
        }
      }
      final ArrListMonitor<SEQ,E> expectedRoot;
      final ArrSubListMonitor<SUBLIST,SEQ,E> expectedParent;
      final SUBLIST seq;
      int expectedRootOffset;
      int expectedSize;
      int expectedModCount;
      @SuppressWarnings("unchecked") ArrSubListMonitor(ArrListMonitor<SEQ,E> expectedRoot,int fromIndex,int toIndex){
        this.expectedRoot=expectedRoot;
        expectedParent=null;
        expectedModCount=expectedRoot.expectedModCount;
        expectedSize=toIndex - fromIndex;
        expectedRootOffset=fromIndex;
        seq=(SUBLIST)expectedRoot.seq.subList(fromIndex,toIndex);
      }
      @SuppressWarnings("unchecked") ArrSubListMonitor(ArrSubListMonitor<SUBLIST,SEQ,E> expectedParent,int fromIndex,
          int toIndex){
        expectedRoot=expectedParent.expectedRoot;
        this.expectedParent=expectedParent;
        expectedModCount=expectedParent.expectedModCount;
        expectedSize=toIndex - fromIndex;
        expectedRootOffset=expectedParent.expectedRootOffset + fromIndex;
        seq=(SUBLIST)expectedParent.seq.subList(fromIndex,toIndex);
      }
      @Override public void copyListContents(){
        expectedRoot.copyListContents();
      }
      @Override public Object get(int iterationIndex,DataType outputType){
        return expectedRoot.get(iterationIndex + expectedRootOffset,outputType);
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
        return new ItrMonitor();
      }
      @Override public MonitoredIterator<? extends OmniIterator<?>,SUBLIST> getMonitoredIterator(int index,
          IteratorType itrType){
        switch(itrType){
        case SubAscendingItr:{
          final var monitor=new ItrMonitor();
          while(--index >= 0){
            monitor.iterateForward();
          }
          return monitor;
        }
        case SubBidirectionalItr:
          return new ListItrMonitor(index);
        default:
          throw itrType.invalid();
        }
      }
      @Override public MonitoredIterator<? extends OmniIterator<?>,SUBLIST> getMonitoredIterator(IteratorType itrType){
        switch(itrType){
        case SubAscendingItr:
          return new ItrMonitor();
        case SubBidirectionalItr:
          return new ListItrMonitor();
        default:
          throw itrType.invalid();
        }
      }
      @Override public MonitoredListIterator<? extends OmniListIterator<?>,SUBLIST> getMonitoredListIterator(){
        return new ListItrMonitor();
      }
      @Override public MonitoredListIterator<? extends OmniListIterator<?>,SUBLIST> getMonitoredListIterator(int index){
        return new ListItrMonitor(index);
      }
      @Override public ArrSubListMonitor<SUBLIST,SEQ,E> getMonitoredSubList(int fromIndex,int toIndex){
        return new ArrSubListMonitor<>(this,fromIndex,toIndex);
      }
      @Override public StructType getStructType(){
        return StructType.ArrSubList;
      }
      @Override public void incrementModCount(){
        var curr=this;
        do{
          ++curr.expectedModCount;
        }while((curr=curr.expectedParent) != null);
        ++expectedRoot.expectedModCount;
      }
      @Override public void modCollection(){
        final var modifier=getModCountModifier();
        var curr=this;
        do{
          curr.expectedModCount=modifier.applyAsInt(curr.seq);
        }while((curr=curr.expectedParent) != null);
        expectedRoot.modCollection();
      }
      @Override
    public void repairModCount() {
          if(expectedRoot.checkedType.checked) {
              int rootModCount=expectedRoot.expectedModCount;
              Consumer<ArrSubListMonitor<SUBLIST,SEQ,E>> modCountRepairer;
              switch(expectedRoot.dataType) {
              case BOOLEAN:{
                  modCountRepairer=monitor->{
                    FieldAndMethodAccessor.setIntValue(FieldAndMethodAccessor.BooleanArrSeq.CheckedSubList.modCountField,monitor.seq,rootModCount);
                    monitor.expectedModCount=rootModCount;
                  };
                  break;
              }
              case BYTE:{
                  modCountRepairer=monitor->{
                      FieldAndMethodAccessor.setIntValue(FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.modCountField,monitor.seq,rootModCount);
                      monitor.expectedModCount=rootModCount;
                    };
                    break;
                }
              case CHAR:{
                  modCountRepairer=monitor->{
                      FieldAndMethodAccessor.setIntValue(FieldAndMethodAccessor.CharArrSeq.CheckedSubList.modCountField,monitor.seq,rootModCount);
                      monitor.expectedModCount=rootModCount;
                    };
                    break;
                }
              case SHORT:{
                  modCountRepairer=monitor->{
                      FieldAndMethodAccessor.setIntValue(FieldAndMethodAccessor.ShortArrSeq.CheckedSubList.modCountField,monitor.seq,rootModCount);
                      monitor.expectedModCount=rootModCount;
                    };
                    break;
                }
              case INT:{
                  modCountRepairer=monitor->{
                      FieldAndMethodAccessor.setIntValue(FieldAndMethodAccessor.IntArrSeq.CheckedSubList.modCountField,monitor.seq,rootModCount);
                      monitor.expectedModCount=rootModCount;
                    };
                    break;
                }
              case LONG:{
                  modCountRepairer=monitor->{
                      FieldAndMethodAccessor.setIntValue(FieldAndMethodAccessor.LongArrSeq.CheckedSubList.modCountField,monitor.seq,rootModCount);
                      monitor.expectedModCount=rootModCount;
                    };
                    break;
                }
              case FLOAT:{
                  modCountRepairer=monitor->{
                      FieldAndMethodAccessor.setIntValue(FieldAndMethodAccessor.FloatArrSeq.CheckedSubList.modCountField,monitor.seq,rootModCount);
                      monitor.expectedModCount=rootModCount;
                    };
                    break;
                }
              case DOUBLE:{
                  modCountRepairer=monitor->{
                      FieldAndMethodAccessor.setIntValue(FieldAndMethodAccessor.DoubleArrSeq.CheckedSubList.modCountField,monitor.seq,rootModCount);
                      monitor.expectedModCount=rootModCount;
                    };
                    break;
                }
              case REF:{
                  modCountRepairer=monitor->{
                      FieldAndMethodAccessor.setIntValue(FieldAndMethodAccessor.RefArrSeq.CheckedSubList.modCountField,monitor.seq,rootModCount);
                      monitor.expectedModCount=rootModCount;
                    };
                    break;
                }
              default:
                  throw expectedRoot.dataType.invalid();
              }
              var curr=this;
              do {
                  modCountRepairer.accept(curr);
              }while((curr=curr.expectedParent)!=null);
          }
          
          
      }
      
      
      @Override public void modParent(){
        final var modifier=getModCountModifier();
        var curr=this;
        while((curr=curr.expectedParent) != null){
          curr.expectedModCount=modifier.applyAsInt(curr.seq);
        }
        expectedRoot.modCollection();
      }
      @Override public void modRoot(){
        expectedRoot.modCollection();
      }
      
      
      @Override public Object removeFirst(){
        final var removed=seq.remove(0);
        updateRemoveIndexState(0);
        return removed;
      }
      @Override public int size(){
        return expectedSize;
      }
      @Override public void updateAddState(int index,Object inputVal,DataType inputType){
        expectedRoot.updateAddState(index + expectedRootOffset,inputVal,inputType);
        bubbleUpModifySize(1);
      }
      @Override public void updateClearState(){
        int expectedSize;
        if((expectedSize=this.expectedSize) != 0){
          bubbleUpModifySize(-expectedSize);
          final int bound=expectedRootOffset + expectedSize;
          final int expectedRootSize=expectedRoot.expectedSize;
          System.arraycopy(expectedRoot.expectedArr,bound,expectedRoot.expectedArr,expectedRootOffset,
              expectedRootSize - bound);
          final int newExpectedRootSize=expectedRootSize - expectedSize;
          if(expectedRoot.dataType == DataType.REF){
            final var castArr=(Object[])expectedRoot.expectedArr;
            for(int i=newExpectedRootSize;i < expectedRootSize;++i){
              castArr[i]=null;
            }
          }
          expectedRoot.expectedSize=newExpectedRootSize;
          ++expectedRoot.expectedModCount;
        }
      }
      @Override public void updateCollectionState(){
        Consumer<ArrSubListMonitor<SUBLIST,SEQ,E>> subListUpdater
            =subListMonitor->subListMonitor.expectedSize=((AbstractSeq<?>)subListMonitor.seq).size;
        if(expectedRoot.checkedType.checked){
          final var dataType=expectedRoot.dataType;
          switch(dataType){
          case BOOLEAN:
            subListUpdater=subListUpdater.andThen(subListMonitor->subListMonitor.expectedModCount
                =FieldAndMethodAccessor.BooleanArrSeq.CheckedSubList.modCount(subListMonitor.seq));
            break;
          case BYTE:
            subListUpdater=subListUpdater.andThen(subListMonitor->subListMonitor.expectedModCount
                =FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.modCount(subListMonitor.seq));
            break;
          case CHAR:
            subListUpdater=subListUpdater.andThen(subListMonitor->subListMonitor.expectedModCount
                =FieldAndMethodAccessor.CharArrSeq.CheckedSubList.modCount(subListMonitor.seq));
            break;
          case DOUBLE:
            subListUpdater=subListUpdater.andThen(subListMonitor->subListMonitor.expectedModCount
                =FieldAndMethodAccessor.DoubleArrSeq.CheckedSubList.modCount(subListMonitor.seq));
            break;
          case FLOAT:
            subListUpdater=subListUpdater.andThen(subListMonitor->subListMonitor.expectedModCount
                =FieldAndMethodAccessor.FloatArrSeq.CheckedSubList.modCount(subListMonitor.seq));
            break;
          case INT:
            subListUpdater=subListUpdater.andThen(subListMonitor->subListMonitor.expectedModCount
                =FieldAndMethodAccessor.IntArrSeq.CheckedSubList.modCount(subListMonitor.seq));
            break;
          case LONG:
            subListUpdater=subListUpdater.andThen(subListMonitor->subListMonitor.expectedModCount
                =FieldAndMethodAccessor.LongArrSeq.CheckedSubList.modCount(subListMonitor.seq));
            break;
          case REF:
            subListUpdater=subListUpdater.andThen(subListMonitor->subListMonitor.expectedModCount
                =FieldAndMethodAccessor.RefArrSeq.CheckedSubList.modCount(subListMonitor.seq));
            break;
          case SHORT:
            subListUpdater=subListUpdater.andThen(subListMonitor->subListMonitor.expectedModCount
                =FieldAndMethodAccessor.ShortArrSeq.CheckedSubList.modCount(subListMonitor.seq));
            break;
          default:
            throw dataType.invalid();
          }
        }
        var curr=this;
        do{
          subListUpdater.accept(curr);
        }while((curr=curr.expectedParent) != null);
        expectedRoot.updateCollectionState();
      }
      @Override public void updateRemoveIndexState(int index){
        expectedRoot.updateRemoveIndexState(index + expectedRootOffset);
        bubbleUpModifySize(-1);
      }
      @Override public void updateRemoveValState(Object inputVal,DataType inputType){
        final int index
            =expectedRoot.findRemoveValIndex(inputVal,inputType,expectedRootOffset,expectedRootOffset + expectedSize);
        expectedRoot.updateRemoveIndexState(index);
        bubbleUpModifySize(-1);
      }
      @Override public void updateReplaceAllState(MonitoredFunction function){
        if(function.getMonitoredFunctionGen().expectedException != ConcurrentModificationException.class
            && !function.isEmpty()){
          var curr=this;
          do{
            ++curr.expectedModCount;
          }while((curr=curr.expectedParent) != null);
        }
        expectedRoot.updateReplaceAllState(function,expectedRootOffset);
      }
      @Override public void verifyArrayIsCopy(Object arr,boolean emptyArrayMayBeSame){
        expectedRoot.verifyArrayIsCopy(arr,emptyArrayMayBeSame);
      }
      @Override public void verifyClone(Object clone){
        expectedRoot.verifyCloneTypeAndModCount(clone);
        Assertions.assertNotSame(clone,expectedRoot.seq);
        int size;
        Assertions.assertEquals(size=((AbstractSeq<?>)seq).size,((AbstractSeq<?>)clone).size);
        final var dataType=expectedRoot.dataType;
        switch(dataType){
        case BOOLEAN:{
          final var origArr=((BooleanArrSeq)expectedRoot.seq).arr;
          final var cloneArr=((BooleanArrSeq)clone).arr;
          if(origArr == OmniArray.OfBoolean.DEFAULT_ARR){
            Assertions.assertSame(origArr,cloneArr);
          }else{
            Assertions.assertNotSame(origArr,cloneArr);
            if(size == 0){
              Assertions.assertSame(cloneArr,OmniArray.OfBoolean.DEFAULT_ARR);
            }else{
              do{
                Assertions.assertEquals(origArr[expectedRootOffset + --size],cloneArr[size]);
              }while(size != 0);
            }
          }
          break;
        }
        case BYTE:{
          final var origArr=((ByteArrSeq)expectedRoot.seq).arr;
          final var cloneArr=((ByteArrSeq)clone).arr;
          if(origArr == OmniArray.OfByte.DEFAULT_ARR){
            Assertions.assertSame(origArr,cloneArr);
          }else{
            Assertions.assertNotSame(origArr,cloneArr);
            if(size == 0){
              Assertions.assertSame(cloneArr,OmniArray.OfByte.DEFAULT_ARR);
            }else{
              do{
                Assertions.assertEquals(origArr[expectedRootOffset + --size],cloneArr[size]);
              }while(size != 0);
            }
          }
          break;
        }
        case CHAR:{
          final var origArr=((CharArrSeq)expectedRoot.seq).arr;
          final var cloneArr=((CharArrSeq)clone).arr;
          if(origArr == OmniArray.OfChar.DEFAULT_ARR){
            Assertions.assertSame(origArr,cloneArr);
          }else{
            Assertions.assertNotSame(origArr,cloneArr);
            if(size == 0){
              Assertions.assertSame(cloneArr,OmniArray.OfChar.DEFAULT_ARR);
            }else{
              do{
                Assertions.assertEquals(origArr[expectedRootOffset + --size],cloneArr[size]);
              }while(size != 0);
            }
          }
          break;
        }
        case DOUBLE:{
          final var origArr=((DoubleArrSeq)expectedRoot.seq).arr;
          final var cloneArr=((DoubleArrSeq)clone).arr;
          if(origArr == OmniArray.OfDouble.DEFAULT_ARR){
            Assertions.assertSame(origArr,cloneArr);
          }else{
            Assertions.assertNotSame(origArr,cloneArr);
            if(size == 0){
              Assertions.assertSame(cloneArr,OmniArray.OfDouble.DEFAULT_ARR);
            }else{
              do{
                Assertions.assertEquals(origArr[expectedRootOffset + --size],cloneArr[size]);
              }while(size != 0);
            }
          }
          break;
        }
        case FLOAT:{
          final var origArr=((FloatArrSeq)expectedRoot.seq).arr;
          final var cloneArr=((FloatArrSeq)clone).arr;
          if(origArr == OmniArray.OfFloat.DEFAULT_ARR){
            Assertions.assertSame(origArr,cloneArr);
          }else{
            Assertions.assertNotSame(origArr,cloneArr);
            if(size == 0){
              Assertions.assertSame(cloneArr,OmniArray.OfFloat.DEFAULT_ARR);
            }else{
              do{
                Assertions.assertEquals(origArr[expectedRootOffset + --size],cloneArr[size]);
              }while(size != 0);
            }
          }
          break;
        }
        case INT:{
          final var origArr=((IntArrSeq)expectedRoot.seq).arr;
          final var cloneArr=((IntArrSeq)clone).arr;
          if(origArr == OmniArray.OfInt.DEFAULT_ARR){
            Assertions.assertSame(origArr,cloneArr);
          }else{
            Assertions.assertNotSame(origArr,cloneArr);
            if(size == 0){
              Assertions.assertSame(cloneArr,OmniArray.OfInt.DEFAULT_ARR);
            }else{
              do{
                Assertions.assertEquals(origArr[expectedRootOffset + --size],cloneArr[size]);
              }while(size != 0);
            }
          }
          break;
        }
        case LONG:{
          final var origArr=((LongArrSeq)expectedRoot.seq).arr;
          final var cloneArr=((LongArrSeq)clone).arr;
          if(origArr == OmniArray.OfLong.DEFAULT_ARR){
            Assertions.assertSame(origArr,cloneArr);
          }else{
            Assertions.assertNotSame(origArr,cloneArr);
            if(size == 0){
              Assertions.assertSame(cloneArr,OmniArray.OfLong.DEFAULT_ARR);
            }else{
              do{
                Assertions.assertEquals(origArr[expectedRootOffset + --size],cloneArr[size]);
              }while(size != 0);
            }
          }
          break;
        }
        case REF:{
          final var origArr=((RefArrSeq<?>)expectedRoot.seq).arr;
          final var cloneArr=((RefArrSeq<?>)clone).arr;
          if(origArr == OmniArray.OfRef.DEFAULT_ARR){
            Assertions.assertSame(origArr,cloneArr);
          }else{
            Assertions.assertNotSame(origArr,cloneArr);
            if(size == 0){
              Assertions.assertSame(cloneArr,OmniArray.OfRef.DEFAULT_ARR);
            }else{
              do{
                Assertions.assertSame(origArr[expectedRootOffset + --size],cloneArr[size]);
              }while(size != 0);
            }
          }
          break;
        }
        case SHORT:{
          final var origArr=((ShortArrSeq)expectedRoot.seq).arr;
          final var cloneArr=((ShortArrSeq)clone).arr;
          if(origArr == OmniArray.OfShort.DEFAULT_ARR){
            Assertions.assertSame(origArr,cloneArr);
          }else{
            Assertions.assertNotSame(origArr,cloneArr);
            if(size == 0){
              Assertions.assertSame(cloneArr,OmniArray.OfShort.DEFAULT_ARR);
            }else{
              do{
                Assertions.assertEquals(origArr[expectedRootOffset + --size],cloneArr[size]);
              }while(size != 0);
            }
          }
          break;
        }
        default:
          throw dataType.invalid();
        }
      }
      @Override public void verifyCollectionState(boolean refIsSame){
        Consumer<ArrSubListMonitor<SUBLIST,SEQ,E>> subListVerifier=subListMonitor->Assertions
            .assertEquals(subListMonitor.expectedSize,((AbstractSeq<?>)subListMonitor.seq).size);
        final var dataType=expectedRoot.dataType;
        final var checked=expectedRoot.checkedType.checked;
        switch(dataType){
        case BOOLEAN:
          if(checked){
            subListVerifier=subListVerifier.andThen(subListMonitor->{
              Assertions.assertSame(subListMonitor.getParentSeq(),
                  FieldAndMethodAccessor.BooleanArrSeq.CheckedSubList.parent(subListMonitor.seq));
              Assertions.assertSame(expectedRoot.seq,
                  FieldAndMethodAccessor.BooleanArrSeq.CheckedSubList.root(subListMonitor.seq));
              Assertions.assertEquals(subListMonitor.expectedRootOffset,
                  FieldAndMethodAccessor.BooleanArrSeq.CheckedSubList.rootOffset(subListMonitor.seq));
              Assertions.assertEquals(subListMonitor.expectedModCount,
                  FieldAndMethodAccessor.BooleanArrSeq.CheckedSubList.modCount(subListMonitor.seq));
            });
          }else{
            subListVerifier=subListVerifier.andThen(subListMonitor->{
              Assertions.assertSame(subListMonitor.getParentSeq(),
                  FieldAndMethodAccessor.BooleanArrSeq.UncheckedSubList.parent(subListMonitor.seq));
              Assertions.assertSame(expectedRoot.seq,
                  FieldAndMethodAccessor.BooleanArrSeq.UncheckedSubList.root(subListMonitor.seq));
              Assertions.assertEquals(subListMonitor.expectedRootOffset,
                  FieldAndMethodAccessor.BooleanArrSeq.UncheckedSubList.rootOffset(subListMonitor.seq));
            });
          }
          break;
        case BYTE:
          if(checked){
            subListVerifier=subListVerifier.andThen(subListMonitor->{
              Assertions.assertSame(subListMonitor.getParentSeq(),
                  FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.parent(subListMonitor.seq));
              Assertions.assertSame(expectedRoot.seq,
                  FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.root(subListMonitor.seq));
              Assertions.assertEquals(subListMonitor.expectedRootOffset,
                  FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.rootOffset(subListMonitor.seq));
              Assertions.assertEquals(subListMonitor.expectedModCount,
                  FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.modCount(subListMonitor.seq));
            });
          }else{
            subListVerifier=subListVerifier.andThen(subListMonitor->{
              Assertions.assertSame(subListMonitor.getParentSeq(),
                  FieldAndMethodAccessor.ByteArrSeq.UncheckedSubList.parent(subListMonitor.seq));
              Assertions.assertSame(expectedRoot.seq,
                  FieldAndMethodAccessor.ByteArrSeq.UncheckedSubList.root(subListMonitor.seq));
              Assertions.assertEquals(subListMonitor.expectedRootOffset,
                  FieldAndMethodAccessor.ByteArrSeq.UncheckedSubList.rootOffset(subListMonitor.seq));
            });
          }
          break;
        case CHAR:
          if(checked){
            subListVerifier=subListVerifier.andThen(subListMonitor->{
              Assertions.assertSame(subListMonitor.getParentSeq(),
                  FieldAndMethodAccessor.CharArrSeq.CheckedSubList.parent(subListMonitor.seq));
              Assertions.assertSame(expectedRoot.seq,
                  FieldAndMethodAccessor.CharArrSeq.CheckedSubList.root(subListMonitor.seq));
              Assertions.assertEquals(subListMonitor.expectedRootOffset,
                  FieldAndMethodAccessor.CharArrSeq.CheckedSubList.rootOffset(subListMonitor.seq));
              Assertions.assertEquals(subListMonitor.expectedModCount,
                  FieldAndMethodAccessor.CharArrSeq.CheckedSubList.modCount(subListMonitor.seq));
            });
          }else{
            subListVerifier=subListVerifier.andThen(subListMonitor->{
              Assertions.assertSame(subListMonitor.getParentSeq(),
                  FieldAndMethodAccessor.CharArrSeq.UncheckedSubList.parent(subListMonitor.seq));
              Assertions.assertSame(expectedRoot.seq,
                  FieldAndMethodAccessor.CharArrSeq.UncheckedSubList.root(subListMonitor.seq));
              Assertions.assertEquals(subListMonitor.expectedRootOffset,
                  FieldAndMethodAccessor.CharArrSeq.UncheckedSubList.rootOffset(subListMonitor.seq));
            });
          }
          break;
        case DOUBLE:
          if(checked){
            subListVerifier=subListVerifier.andThen(subListMonitor->{
              Assertions.assertSame(subListMonitor.getParentSeq(),
                  FieldAndMethodAccessor.DoubleArrSeq.CheckedSubList.parent(subListMonitor.seq));
              Assertions.assertSame(expectedRoot.seq,
                  FieldAndMethodAccessor.DoubleArrSeq.CheckedSubList.root(subListMonitor.seq));
              Assertions.assertEquals(subListMonitor.expectedRootOffset,
                  FieldAndMethodAccessor.DoubleArrSeq.CheckedSubList.rootOffset(subListMonitor.seq));
              Assertions.assertEquals(subListMonitor.expectedModCount,
                  FieldAndMethodAccessor.DoubleArrSeq.CheckedSubList.modCount(subListMonitor.seq));
            });
          }else{
            subListVerifier=subListVerifier.andThen(subListMonitor->{
              Assertions.assertSame(subListMonitor.getParentSeq(),
                  FieldAndMethodAccessor.DoubleArrSeq.UncheckedSubList.parent(subListMonitor.seq));
              Assertions.assertSame(expectedRoot.seq,
                  FieldAndMethodAccessor.DoubleArrSeq.UncheckedSubList.root(subListMonitor.seq));
              Assertions.assertEquals(subListMonitor.expectedRootOffset,
                  FieldAndMethodAccessor.DoubleArrSeq.UncheckedSubList.rootOffset(subListMonitor.seq));
            });
          }
          break;
        case FLOAT:
          if(checked){
            subListVerifier=subListVerifier.andThen(subListMonitor->{
              Assertions.assertSame(subListMonitor.getParentSeq(),
                  FieldAndMethodAccessor.FloatArrSeq.CheckedSubList.parent(subListMonitor.seq));
              Assertions.assertSame(expectedRoot.seq,
                  FieldAndMethodAccessor.FloatArrSeq.CheckedSubList.root(subListMonitor.seq));
              Assertions.assertEquals(subListMonitor.expectedRootOffset,
                  FieldAndMethodAccessor.FloatArrSeq.CheckedSubList.rootOffset(subListMonitor.seq));
              Assertions.assertEquals(subListMonitor.expectedModCount,
                  FieldAndMethodAccessor.FloatArrSeq.CheckedSubList.modCount(subListMonitor.seq));
            });
          }else{
            subListVerifier=subListVerifier.andThen(subListMonitor->{
              Assertions.assertSame(subListMonitor.getParentSeq(),
                  FieldAndMethodAccessor.FloatArrSeq.UncheckedSubList.parent(subListMonitor.seq));
              Assertions.assertSame(expectedRoot.seq,
                  FieldAndMethodAccessor.FloatArrSeq.UncheckedSubList.root(subListMonitor.seq));
              Assertions.assertEquals(subListMonitor.expectedRootOffset,
                  FieldAndMethodAccessor.FloatArrSeq.UncheckedSubList.rootOffset(subListMonitor.seq));
            });
          }
          break;
        case INT:
          if(checked){
            subListVerifier=subListVerifier.andThen(subListMonitor->{
              Assertions.assertSame(subListMonitor.getParentSeq(),
                  FieldAndMethodAccessor.IntArrSeq.CheckedSubList.parent(subListMonitor.seq));
              Assertions.assertSame(expectedRoot.seq,
                  FieldAndMethodAccessor.IntArrSeq.CheckedSubList.root(subListMonitor.seq));
              Assertions.assertEquals(subListMonitor.expectedRootOffset,
                  FieldAndMethodAccessor.IntArrSeq.CheckedSubList.rootOffset(subListMonitor.seq));
              Assertions.assertEquals(subListMonitor.expectedModCount,
                  FieldAndMethodAccessor.IntArrSeq.CheckedSubList.modCount(subListMonitor.seq));
            });
          }else{
            subListVerifier=subListVerifier.andThen(subListMonitor->{
              Assertions.assertSame(subListMonitor.getParentSeq(),
                  FieldAndMethodAccessor.IntArrSeq.UncheckedSubList.parent(subListMonitor.seq));
              Assertions.assertSame(expectedRoot.seq,
                  FieldAndMethodAccessor.IntArrSeq.UncheckedSubList.root(subListMonitor.seq));
              Assertions.assertEquals(subListMonitor.expectedRootOffset,
                  FieldAndMethodAccessor.IntArrSeq.UncheckedSubList.rootOffset(subListMonitor.seq));
            });
          }
          break;
        case LONG:
          if(checked){
            subListVerifier=subListVerifier.andThen(subListMonitor->{
              Assertions.assertSame(subListMonitor.getParentSeq(),
                  FieldAndMethodAccessor.LongArrSeq.CheckedSubList.parent(subListMonitor.seq));
              Assertions.assertSame(expectedRoot.seq,
                  FieldAndMethodAccessor.LongArrSeq.CheckedSubList.root(subListMonitor.seq));
              Assertions.assertEquals(subListMonitor.expectedRootOffset,
                  FieldAndMethodAccessor.LongArrSeq.CheckedSubList.rootOffset(subListMonitor.seq));
              Assertions.assertEquals(subListMonitor.expectedModCount,
                  FieldAndMethodAccessor.LongArrSeq.CheckedSubList.modCount(subListMonitor.seq));
            });
          }else{
            subListVerifier=subListVerifier.andThen(subListMonitor->{
              Assertions.assertSame(subListMonitor.getParentSeq(),
                  FieldAndMethodAccessor.LongArrSeq.UncheckedSubList.parent(subListMonitor.seq));
              Assertions.assertSame(expectedRoot.seq,
                  FieldAndMethodAccessor.LongArrSeq.UncheckedSubList.root(subListMonitor.seq));
              Assertions.assertEquals(subListMonitor.expectedRootOffset,
                  FieldAndMethodAccessor.LongArrSeq.UncheckedSubList.rootOffset(subListMonitor.seq));
            });
          }
          break;
        case REF:
          if(checked){
            subListVerifier=subListVerifier.andThen(subListMonitor->{
              Assertions.assertSame(subListMonitor.getParentSeq(),
                  FieldAndMethodAccessor.RefArrSeq.CheckedSubList.parent(subListMonitor.seq));
              Assertions.assertSame(expectedRoot.seq,
                  FieldAndMethodAccessor.RefArrSeq.CheckedSubList.root(subListMonitor.seq));
              Assertions.assertEquals(subListMonitor.expectedRootOffset,
                  FieldAndMethodAccessor.RefArrSeq.CheckedSubList.rootOffset(subListMonitor.seq));
              Assertions.assertEquals(subListMonitor.expectedModCount,
                  FieldAndMethodAccessor.RefArrSeq.CheckedSubList.modCount(subListMonitor.seq));
            });
          }else{
            subListVerifier=subListVerifier.andThen(subListMonitor->{
              Assertions.assertSame(subListMonitor.getParentSeq(),
                  FieldAndMethodAccessor.RefArrSeq.UncheckedSubList.parent(subListMonitor.seq));
              Assertions.assertSame(expectedRoot.seq,
                  FieldAndMethodAccessor.RefArrSeq.UncheckedSubList.root(subListMonitor.seq));
              Assertions.assertEquals(subListMonitor.expectedRootOffset,
                  FieldAndMethodAccessor.RefArrSeq.UncheckedSubList.rootOffset(subListMonitor.seq));
            });
          }
          break;
        case SHORT:
          if(checked){
            subListVerifier=subListVerifier.andThen(subListMonitor->{
              Assertions.assertSame(subListMonitor.getParentSeq(),
                  FieldAndMethodAccessor.ShortArrSeq.CheckedSubList.parent(subListMonitor.seq));
              Assertions.assertSame(expectedRoot.seq,
                  FieldAndMethodAccessor.ShortArrSeq.CheckedSubList.root(subListMonitor.seq));
              Assertions.assertEquals(subListMonitor.expectedRootOffset,
                  FieldAndMethodAccessor.ShortArrSeq.CheckedSubList.rootOffset(subListMonitor.seq));
              Assertions.assertEquals(subListMonitor.expectedModCount,
                  FieldAndMethodAccessor.ShortArrSeq.CheckedSubList.modCount(subListMonitor.seq));
            });
          }else{
            subListVerifier=subListVerifier.andThen(subListMonitor->{
              Assertions.assertSame(subListMonitor.getParentSeq(),
                  FieldAndMethodAccessor.ShortArrSeq.UncheckedSubList.parent(subListMonitor.seq));
              Assertions.assertSame(expectedRoot.seq,
                  FieldAndMethodAccessor.ShortArrSeq.UncheckedSubList.root(subListMonitor.seq));
              Assertions.assertEquals(subListMonitor.expectedRootOffset,
                  FieldAndMethodAccessor.ShortArrSeq.UncheckedSubList.rootOffset(subListMonitor.seq));
            });
          }
          break;
        default:
          throw dataType.invalid();
        }
        var curr=this;
        do{
          subListVerifier.accept(curr);
        }while((curr=curr.expectedParent) != null);
        expectedRoot.verifyCollectionState(refIsSame);
      }
      @Override public void verifyGetResult(int index,Object result,DataType outputType){
        expectedRoot.verifyGetResult(index + expectedRootOffset,result,outputType);
      }
      @Override public void verifyPutResult(int index,Object input,DataType inputType){
        expectedRoot.verifyPutResult(index + expectedRootOffset,input,inputType);
      }
      @Override public void verifyReadAndWriteClone(SUBLIST clone){
        expectedRoot.verifyCloneTypeAndModCount(clone);
        Assertions.assertNotSame(clone,expectedRoot.seq);
        int size;
        Assertions.assertEquals(size=((AbstractSeq<?>)seq).size,((AbstractSeq<?>)clone).size);
        final var origArr=((RefArrSeq<?>)expectedRoot.seq).arr;
        final var cloneArr=((RefArrSeq<?>)clone).arr;
        if(origArr == OmniArray.OfRef.DEFAULT_ARR){
          Assertions.assertSame(origArr,cloneArr);
        }else{
          Assertions.assertNotSame(origArr,cloneArr);
          if(size == 0){
            Assertions.assertSame(cloneArr,OmniArray.OfRef.DEFAULT_ARR);
          }else{
            do{
              Assertions.assertEquals(origArr[expectedRootOffset + --size],cloneArr[size]);
            }while(size != 0);
          }
        }
      }
      @Override public void verifyRemoveIf(boolean result,MonitoredRemoveIfPredicate filter){
        Assertions.assertNotEquals(result,filter.removedVals.isEmpty());
        Assertions.assertNotEquals(result,filter.numRemoved == 0);
        final int expectedSize=this.expectedSize;
        final int offset=expectedRootOffset;
        final int bound=offset + expectedSize;
        final var dataType=expectedRoot.dataType;
        if(result){
          int numRemoved;
          if(dataType == DataType.BOOLEAN){
            final var expectedArr=(boolean[])expectedRoot.expectedArr;
            if(filter.removedVals.contains(Boolean.TRUE)){
              if(filter.removedVals.contains(Boolean.FALSE)){
                Assertions.assertTrue(filter.retainedVals.isEmpty());
                Assertions.assertEquals(0,filter.numRetained);
                int i=bound - 1;
                final boolean firstVal=expectedArr[i];
                while(expectedArr[--i] == firstVal){
                  // we are expecting this condition to be met before i<offset. Otherwise,
                  // something is wrong.
                }
                numRemoved=expectedSize;
              }else{
                numRemoved=1;
                int i=offset;
                for(;;++i){
                  if(expectedArr[i]){
                    for(int j=i + 1;j < bound;++j){
                      if(!expectedArr[j]){
                        expectedArr[i++]=false;
                      }else{
                        ++numRemoved;
                      }
                    }
                    break;
                  }
                }
              }
            }else{
              numRemoved=1;
              int i=offset;
              for(;;++i){
                if(!expectedArr[i]){
                  for(int j=i + 1;j < bound;++j){
                    if(expectedArr[j]){
                      expectedArr[i++]=true;
                    }else{
                      ++numRemoved;
                    }
                  }
                  break;
                }
              }
            }
          }else{
            Assertions.assertEquals(expectedSize,filter.numCalls);
            IntBinaryOperator remover;
            switch(dataType){
            case BYTE:{
              final var castArr=(byte[])expectedRoot.expectedArr;
              remover=(srcIndex,dstIndex)->{
                final var val=castArr[srcIndex];
                final boolean removedContains=filter.removedVals.contains(val);
                final boolean retainedContains=filter.retainedVals.contains(val);
                Assertions.assertNotEquals(removedContains,retainedContains);
                if(retainedContains){
                  castArr[dstIndex++]=val;
                }
                return dstIndex;
              };
              break;
            }
            case CHAR:{
              final var castArr=(char[])expectedRoot.expectedArr;
              remover=(srcIndex,dstIndex)->{
                final var val=castArr[srcIndex];
                final boolean removedContains=filter.removedVals.contains(val);
                final boolean retainedContains=filter.retainedVals.contains(val);
                Assertions.assertNotEquals(removedContains,retainedContains);
                if(retainedContains){
                  castArr[dstIndex++]=val;
                }
                return dstIndex;
              };
              break;
            }
            case SHORT:{
              final var castArr=(short[])expectedRoot.expectedArr;
              remover=(srcIndex,dstIndex)->{
                final var val=castArr[srcIndex];
                final boolean removedContains=filter.removedVals.contains(val);
                final boolean retainedContains=filter.retainedVals.contains(val);
                Assertions.assertNotEquals(removedContains,retainedContains);
                if(retainedContains){
                  castArr[dstIndex++]=val;
                }
                return dstIndex;
              };
              break;
            }
            case INT:{
              final var castArr=(int[])expectedRoot.expectedArr;
              remover=(srcIndex,dstIndex)->{
                final var val=castArr[srcIndex];
                final boolean removedContains=filter.removedVals.contains(val);
                final boolean retainedContains=filter.retainedVals.contains(val);
                Assertions.assertNotEquals(removedContains,retainedContains);
                if(retainedContains){
                  castArr[dstIndex++]=val;
                }
                return dstIndex;
              };
              break;
            }
            case LONG:{
              final var castArr=(long[])expectedRoot.expectedArr;
              remover=(srcIndex,dstIndex)->{
                final var val=castArr[srcIndex];
                final boolean removedContains=filter.removedVals.contains(val);
                final boolean retainedContains=filter.retainedVals.contains(val);
                Assertions.assertNotEquals(removedContains,retainedContains);
                if(retainedContains){
                  castArr[dstIndex++]=val;
                }
                return dstIndex;
              };
              break;
            }
            case FLOAT:{
              final var castArr=(float[])expectedRoot.expectedArr;
              remover=(srcIndex,dstIndex)->{
                final var val=castArr[srcIndex];
                final boolean removedContains=filter.removedVals.contains(val);
                final boolean retainedContains=filter.retainedVals.contains(val);
                Assertions.assertNotEquals(removedContains,retainedContains);
                if(retainedContains){
                  castArr[dstIndex++]=val;
                }
                return dstIndex;
              };
              break;
            }
            case DOUBLE:{
              final var castArr=(double[])expectedRoot.expectedArr;
              remover=(srcIndex,dstIndex)->{
                final var val=castArr[srcIndex];
                final boolean removedContains=filter.removedVals.contains(val);
                final boolean retainedContains=filter.retainedVals.contains(val);
                Assertions.assertNotEquals(removedContains,retainedContains);
                if(retainedContains){
                  castArr[dstIndex++]=val;
                }
                return dstIndex;
              };
              break;
            }
            case REF:{
              final var castArr=(Object[])expectedRoot.expectedArr;
              remover=(srcIndex,dstIndex)->{
                final var val=castArr[srcIndex];
                final boolean removedContains=filter.removedVals.contains(val);
                final boolean retainedContains=filter.retainedVals.contains(val);
                Assertions.assertNotEquals(removedContains,retainedContains);
                if(retainedContains){
                  castArr[dstIndex++]=val;
                }
                return dstIndex;
              };
              break;
            }
            default:
              throw dataType.invalid();
            }
            int dstOffset=offset;
            for(int srcOffset=offset;srcOffset < bound;++srcOffset){
              dstOffset=remover.applyAsInt(srcOffset,dstOffset);
            }
            numRemoved=bound - dstOffset;
            Assertions.assertEquals(filter.numRemoved,numRemoved);
            Assertions.assertEquals(filter.numRetained,dstOffset - offset);
          }
          System.arraycopy(expectedRoot.expectedArr,bound,expectedRoot.expectedArr,bound - numRemoved,
              expectedRoot.expectedSize - bound);
          if(dataType == DataType.REF){
            final var expectedArr=(Object[])expectedRoot.expectedArr;
            final int rootBound=expectedRoot.expectedSize;
            int newRootBound=rootBound - numRemoved;
            while(newRootBound != rootBound){
              expectedArr[newRootBound]=null;
              ++newRootBound;
            }
          }
          bubbleUpModifySize(-numRemoved);
          expectedRoot.expectedSize-=numRemoved;
          ++expectedRoot.expectedModCount;
        }else{
          if(dataType == DataType.BOOLEAN){
            final var expectedArr=((BooleanArrSeq)expectedRoot.seq).arr;
            if(filter.retainedVals.contains(Boolean.TRUE)){
              if(filter.retainedVals.contains(Boolean.FALSE)){
                int i=expectedRootOffset + expectedSize - 1;
                final boolean firstVal=expectedArr[i];
                while(expectedArr[--i] == firstVal){
                  // we are expecting this condition to be met before expectedSize==-1. Otherwise,
                  // something is wrong.
                }
              }else{
                for(int i=expectedRootOffset + expectedSize;--i >= expectedRootOffset;){
                  Assertions.assertTrue(expectedArr[i]);
                }
              }
            }else{
              if(filter.retainedVals.contains(Boolean.FALSE)){
                for(int i=expectedRootOffset + expectedSize;--i >= expectedRootOffset;){
                  Assertions.assertFalse(expectedArr[i]);
                }
              }else{
                Assertions.assertEquals(0,expectedSize);
              }
            }
          }else{
            Assertions.assertEquals(expectedSize,filter.numCalls);
            Assertions.assertEquals(expectedSize,filter.numRetained);
            final var itr=seq.iterator();
            while(itr.hasNext()){
              Assertions.assertTrue(filter.retainedVals.contains(itr.next()));
            }
          }
        }
      }
      @Override public void writeObjectImpl(MonitoredObjectOutputStream oos) throws IOException{
        final var dataType=expectedRoot.getDataType();
        final var checked=expectedRoot.checkedType.checked;
        switch(dataType){
        case BOOLEAN:
          if(checked){
            FieldAndMethodAccessor.BooleanArrSeq.CheckedSubList.writeObject(seq,oos);
          }else{
            FieldAndMethodAccessor.BooleanArrSeq.UncheckedSubList.writeObject(seq,oos);
          }
          break;
        case BYTE:
          if(checked){
            FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.writeObject(seq,oos);
          }else{
            FieldAndMethodAccessor.ByteArrSeq.UncheckedSubList.writeObject(seq,oos);
          }
          break;
        case CHAR:
          if(checked){
            FieldAndMethodAccessor.CharArrSeq.CheckedSubList.writeObject(seq,oos);
          }else{
            FieldAndMethodAccessor.CharArrSeq.UncheckedSubList.writeObject(seq,oos);
          }
          break;
        case DOUBLE:
          if(checked){
            FieldAndMethodAccessor.DoubleArrSeq.CheckedSubList.writeObject(seq,oos);
          }else{
            FieldAndMethodAccessor.DoubleArrSeq.UncheckedSubList.writeObject(seq,oos);
          }
          break;
        case FLOAT:
          if(checked){
            FieldAndMethodAccessor.FloatArrSeq.CheckedSubList.writeObject(seq,oos);
          }else{
            FieldAndMethodAccessor.FloatArrSeq.UncheckedSubList.writeObject(seq,oos);
          }
          break;
        case INT:
          if(checked){
            FieldAndMethodAccessor.IntArrSeq.CheckedSubList.writeObject(seq,oos);
          }else{
            FieldAndMethodAccessor.IntArrSeq.UncheckedSubList.writeObject(seq,oos);
          }
          break;
        case LONG:
          if(checked){
            FieldAndMethodAccessor.LongArrSeq.CheckedSubList.writeObject(seq,oos);
          }else{
            FieldAndMethodAccessor.LongArrSeq.UncheckedSubList.writeObject(seq,oos);
          }
          break;
        case REF:
          if(checked){
            FieldAndMethodAccessor.RefArrSeq.CheckedSubList.writeObject(seq,oos);
          }else{
            FieldAndMethodAccessor.RefArrSeq.UncheckedSubList.writeObject(seq,oos);
          }
          break;
        case SHORT:
          if(checked){
            FieldAndMethodAccessor.ShortArrSeq.CheckedSubList.writeObject(seq,oos);
          }else{
            FieldAndMethodAccessor.ShortArrSeq.UncheckedSubList.writeObject(seq,oos);
          }
          break;
        default:
          throw dataType.invalid();
        }
      }
      private void bubbleUpModifySize(int sizeDelta){
        var curr=this;
        do{
          ++curr.expectedModCount;
          curr.expectedSize+=sizeDelta;
        }while((curr=curr.expectedParent) != null);
      }
      private ToIntFunction<OmniList<?>> getModCountModifier(){
        final var dataType=expectedRoot.dataType;
        switch(dataType){
        case BOOLEAN:
          return FieldAndMethodAccessor.BooleanArrSeq.CheckedSubList::incrementModCount;
        case BYTE:
          return FieldAndMethodAccessor.ByteArrSeq.CheckedSubList::incrementModCount;
        case CHAR:
          return FieldAndMethodAccessor.CharArrSeq.CheckedSubList::incrementModCount;
        case DOUBLE:
          return FieldAndMethodAccessor.DoubleArrSeq.CheckedSubList::incrementModCount;
        case FLOAT:
          return FieldAndMethodAccessor.FloatArrSeq.CheckedSubList::incrementModCount;
        case INT:
          return FieldAndMethodAccessor.IntArrSeq.CheckedSubList::incrementModCount;
        case LONG:
          return FieldAndMethodAccessor.LongArrSeq.CheckedSubList::incrementModCount;
        case REF:
          return FieldAndMethodAccessor.RefArrSeq.CheckedSubList::incrementModCount;
        case SHORT:
          return FieldAndMethodAccessor.ShortArrSeq.CheckedSubList::incrementModCount;
        default:
          throw dataType.invalid();
        }
      }
      private OmniList<?> getParentSeq(){
        if(expectedParent == null){ return null; }
        return expectedParent.seq;
      }
    }
    private abstract class AbstractItrMonitor<ITR extends OmniIterator<?>> implements MonitoredIterator<ITR,SEQ>{
      final ITR itr;
      int expectedCursor;
      int expectedItrModCount;
      int expectedLastRet;
      int lastRetState;
      AbstractItrMonitor(ITR itr,int expectedCursor){
        this.itr=itr;
        this.expectedCursor=expectedCursor;
        this.expectedItrModCount=expectedModCount;
        if(checkedType.checked){
          this.expectedLastRet=-1;
        }
        this.lastRetState=-1;
      }
      @Override public ITR getIterator(){
        return this.itr;
      }
      @Override public MonitoredCollection<SEQ> getMonitoredCollection(){
        return ArrListMonitor.this;
      }
      @Override public int getNumLeft(){
        return expectedSize - expectedCursor;
      }
      @Override public boolean hasNext(){
        return expectedCursor < expectedSize;
      }
      @Override public boolean nextWasJustCalled(){
        return this.lastRetState == 0;
      }
      @Override public void updateItrNextState(){
        expectedLastRet=expectedCursor++;
        lastRetState=0;
      }
      @Override public void updateItrRemoveState(){
        updateRemoveIndexState(expectedCursor=expectedLastRet);
        ++expectedItrModCount;
        if(checkedType.checked){
          expectedLastRet=-1;
        }
        lastRetState=-1;
      }
      @Override public void verifyForEachRemaining(MonitoredFunction function){
        final var functionItr=function.iterator();
        IntConsumer functionVerifier;
        switch(dataType){
        case BOOLEAN:{
          final var expectedArr=(boolean[])ArrListMonitor.this.expectedArr;
          functionVerifier=cursor->Assertions.assertEquals(expectedArr[cursor],(boolean)functionItr.next());
          break;
        }
        case BYTE:{
          final var expectedArr=(byte[])ArrListMonitor.this.expectedArr;
          functionVerifier=cursor->Assertions.assertEquals(expectedArr[cursor],(byte)functionItr.next());
          break;
        }
        case CHAR:{
          final var expectedArr=(char[])ArrListMonitor.this.expectedArr;
          functionVerifier=cursor->Assertions.assertEquals(expectedArr[cursor],(char)functionItr.next());
          break;
        }
        case SHORT:{
          final var expectedArr=(short[])ArrListMonitor.this.expectedArr;
          functionVerifier=cursor->Assertions.assertEquals(expectedArr[cursor],(short)functionItr.next());
          break;
        }
        case INT:{
          final var expectedArr=(int[])ArrListMonitor.this.expectedArr;
          functionVerifier=cursor->Assertions.assertEquals(expectedArr[cursor],(int)functionItr.next());
          break;
        }
        case LONG:{
          final var expectedArr=(long[])ArrListMonitor.this.expectedArr;
          functionVerifier=cursor->Assertions.assertEquals(expectedArr[cursor],(long)functionItr.next());
          break;
        }
        case FLOAT:{
          final var expectedArr=(float[])ArrListMonitor.this.expectedArr;
          functionVerifier=cursor->Assertions.assertEquals(expectedArr[cursor],(float)functionItr.next());
          break;
        }
        case DOUBLE:{
          final var expectedArr=(double[])ArrListMonitor.this.expectedArr;
          functionVerifier=cursor->Assertions.assertEquals(expectedArr[cursor],(double)functionItr.next());
          break;
        }
        case REF:{
          final var expectedArr=(Object[])ArrListMonitor.this.expectedArr;
          functionVerifier=cursor->Assertions.assertSame(expectedArr[cursor],functionItr.next());
          break;
        }
        default:
          throw dataType.invalid();
        }
        int expectedLastRet=this.expectedLastRet;
        int lastRetState=this.lastRetState;
        int expectedCursor=this.expectedCursor;
        final int expectedBound=expectedSize;
        while(expectedCursor < expectedBound){
          functionVerifier.accept(expectedCursor);
          expectedLastRet=expectedCursor++;
          lastRetState=0;
        }
        this.expectedCursor=expectedCursor;
        this.expectedLastRet=expectedLastRet;
        this.lastRetState=lastRetState;
      }
      @Override public void verifyNextResult(DataType outputType,Object result){
        verifyGetResult(expectedCursor,result,outputType);
      }
    }
    private class ItrMonitor extends AbstractItrMonitor<OmniIterator<?>>{
      ItrMonitor(){
        super(seq.iterator(),0);
      }
      @Override public IteratorType getIteratorType(){
        return IteratorType.AscendingItr;
      }
      @Override public void verifyCloneHelper(Object clone){
        final var checked=checkedType.checked;
        switch(dataType){
        case BOOLEAN:
          if(checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanArrSeq.CheckedList.Itr.parent(clone));
            Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.BooleanArrSeq.CheckedList.Itr.cursor(clone));
            Assertions.assertEquals(expectedItrModCount,
                FieldAndMethodAccessor.BooleanArrSeq.CheckedList.Itr.modCount(clone));
            Assertions.assertEquals(expectedLastRet,
                FieldAndMethodAccessor.BooleanArrSeq.CheckedList.Itr.lastRet(clone));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanArrSeq.UncheckedList.Itr.parent(clone));
            Assertions.assertEquals(expectedCursor,
                FieldAndMethodAccessor.BooleanArrSeq.UncheckedList.Itr.cursor(clone));
          }
          break;
        case BYTE:
          if(checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.ByteArrSeq.CheckedList.Itr.parent(clone));
            Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.ByteArrSeq.CheckedList.Itr.cursor(clone));
            Assertions.assertEquals(expectedItrModCount,
                FieldAndMethodAccessor.ByteArrSeq.CheckedList.Itr.modCount(clone));
            Assertions.assertEquals(expectedLastRet,FieldAndMethodAccessor.ByteArrSeq.CheckedList.Itr.lastRet(clone));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.ByteArrSeq.UncheckedList.Itr.parent(clone));
            Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.ByteArrSeq.UncheckedList.Itr.cursor(clone));
          }
          break;
        case CHAR:
          if(checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.CharArrSeq.CheckedList.Itr.parent(clone));
            Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.CharArrSeq.CheckedList.Itr.cursor(clone));
            Assertions.assertEquals(expectedItrModCount,
                FieldAndMethodAccessor.CharArrSeq.CheckedList.Itr.modCount(clone));
            Assertions.assertEquals(expectedLastRet,FieldAndMethodAccessor.CharArrSeq.CheckedList.Itr.lastRet(clone));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.CharArrSeq.UncheckedList.Itr.parent(clone));
            Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.CharArrSeq.UncheckedList.Itr.cursor(clone));
          }
          break;
        case SHORT:
          if(checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.ShortArrSeq.CheckedList.Itr.parent(clone));
            Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.ShortArrSeq.CheckedList.Itr.cursor(clone));
            Assertions.assertEquals(expectedItrModCount,
                FieldAndMethodAccessor.ShortArrSeq.CheckedList.Itr.modCount(clone));
            Assertions.assertEquals(expectedLastRet,FieldAndMethodAccessor.ShortArrSeq.CheckedList.Itr.lastRet(clone));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.ShortArrSeq.UncheckedList.Itr.parent(clone));
            Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.ShortArrSeq.UncheckedList.Itr.cursor(clone));
          }
          break;
        case INT:
          if(checkedType.checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.IntArrSeq.CheckedList.Itr.parent(clone));
            Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.IntArrSeq.CheckedList.Itr.cursor(clone));
            Assertions.assertEquals(expectedItrModCount,
                FieldAndMethodAccessor.IntArrSeq.CheckedList.Itr.modCount(clone));
            Assertions.assertEquals(expectedLastRet,FieldAndMethodAccessor.IntArrSeq.CheckedList.Itr.lastRet(clone));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.IntArrSeq.UncheckedList.Itr.parent(clone));
            Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.IntArrSeq.UncheckedList.Itr.cursor(clone));
          }
          break;
        case LONG:
          if(checkedType.checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.LongArrSeq.CheckedList.Itr.parent(clone));
            Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.LongArrSeq.CheckedList.Itr.cursor(clone));
            Assertions.assertEquals(expectedItrModCount,
                FieldAndMethodAccessor.LongArrSeq.CheckedList.Itr.modCount(clone));
            Assertions.assertEquals(expectedLastRet,FieldAndMethodAccessor.LongArrSeq.CheckedList.Itr.lastRet(clone));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.LongArrSeq.UncheckedList.Itr.parent(clone));
            Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.LongArrSeq.UncheckedList.Itr.cursor(clone));
          }
          break;
        case FLOAT:
          if(checkedType.checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.FloatArrSeq.CheckedList.Itr.parent(clone));
            Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.FloatArrSeq.CheckedList.Itr.cursor(clone));
            Assertions.assertEquals(expectedItrModCount,
                FieldAndMethodAccessor.FloatArrSeq.CheckedList.Itr.modCount(clone));
            Assertions.assertEquals(expectedLastRet,FieldAndMethodAccessor.FloatArrSeq.CheckedList.Itr.lastRet(clone));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.FloatArrSeq.UncheckedList.Itr.parent(clone));
            Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.FloatArrSeq.UncheckedList.Itr.cursor(clone));
          }
          break;
        case DOUBLE:
          if(checkedType.checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleArrSeq.CheckedList.Itr.parent(clone));
            Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.DoubleArrSeq.CheckedList.Itr.cursor(clone));
            Assertions.assertEquals(expectedItrModCount,
                FieldAndMethodAccessor.DoubleArrSeq.CheckedList.Itr.modCount(clone));
            Assertions.assertEquals(expectedLastRet,FieldAndMethodAccessor.DoubleArrSeq.CheckedList.Itr.lastRet(clone));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleArrSeq.UncheckedList.Itr.parent(clone));
            Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.DoubleArrSeq.UncheckedList.Itr.cursor(clone));
          }
          break;
        case REF:
          if(checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.RefArrSeq.CheckedList.Itr.parent(clone));
            Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.RefArrSeq.CheckedList.Itr.cursor(clone));
            Assertions.assertEquals(expectedItrModCount,
                FieldAndMethodAccessor.RefArrSeq.CheckedList.Itr.modCount(clone));
            Assertions.assertEquals(expectedLastRet,FieldAndMethodAccessor.RefArrSeq.CheckedList.Itr.lastRet(clone));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.RefArrSeq.UncheckedList.Itr.parent(clone));
            Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.RefArrSeq.UncheckedList.Itr.cursor(clone));
          }
          break;
        default:
          throw dataType.invalid();
        }
      }
    }
    private class ListItrMonitor extends AbstractItrMonitor<OmniListIterator<?>>
        implements MonitoredListIterator<OmniListIterator<?>,SEQ>{
      ListItrMonitor(){
        super(seq.listIterator(),0);
      }
      ListItrMonitor(int cursor){
        super(seq.listIterator(cursor),cursor);
      }
      @Override public IteratorType getIteratorType(){
        return IteratorType.BidirectionalItr;
      }
      @Override public boolean hasPrevious(){
        return expectedCursor > 0;
      }
      @Override public int nextIndex(){
        return expectedCursor;
      }
      @Override public int previousIndex(){
        return expectedCursor - 1;
      }
      @Override public boolean previousWasJustCalled(){
        return lastRetState == 1;
      }
      @Override public void updateItrAddState(Object input,DataType inputType){
        ArrListMonitor.this.updateAddState(expectedCursor++,input,inputType);
        ++expectedItrModCount;
        lastRetState=-1;
        if(checkedType.checked){
          expectedLastRet=-1;
        }
      }
      @Override public void updateItrPreviousState(){
        expectedLastRet=--expectedCursor;
        lastRetState=1;
      }
      @Override public void updateItrSetState(Object input,DataType inputType){
        verifyPutResult(expectedLastRet,input,inputType);
      }
      @Override public void verifyCloneHelper(Object clone){
        switch(dataType){
        case BOOLEAN:
          if(checkedType.checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanArrSeq.CheckedList.ListItr.parent(clone));
            Assertions.assertEquals(expectedCursor,
                FieldAndMethodAccessor.BooleanArrSeq.CheckedList.ListItr.cursor(clone));
            Assertions.assertEquals(expectedItrModCount,
                FieldAndMethodAccessor.BooleanArrSeq.CheckedList.ListItr.modCount(clone));
            Assertions.assertEquals(expectedLastRet,
                FieldAndMethodAccessor.BooleanArrSeq.CheckedList.ListItr.lastRet(clone));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanArrSeq.UncheckedList.ListItr.parent(clone));
            Assertions.assertEquals(expectedCursor,
                FieldAndMethodAccessor.BooleanArrSeq.UncheckedList.ListItr.cursor(clone));
            Assertions.assertEquals(expectedLastRet,
                FieldAndMethodAccessor.BooleanArrSeq.UncheckedList.ListItr.lastRet(clone));
          }
          break;
        case BYTE:
          if(checkedType.checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.ByteArrSeq.CheckedList.ListItr.parent(clone));
            Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.ByteArrSeq.CheckedList.ListItr.cursor(clone));
            Assertions.assertEquals(expectedItrModCount,
                FieldAndMethodAccessor.ByteArrSeq.CheckedList.ListItr.modCount(clone));
            Assertions.assertEquals(expectedLastRet,
                FieldAndMethodAccessor.ByteArrSeq.CheckedList.ListItr.lastRet(clone));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.ByteArrSeq.UncheckedList.ListItr.parent(clone));
            Assertions.assertEquals(expectedCursor,
                FieldAndMethodAccessor.ByteArrSeq.UncheckedList.ListItr.cursor(clone));
            Assertions.assertEquals(expectedLastRet,
                FieldAndMethodAccessor.ByteArrSeq.UncheckedList.ListItr.lastRet(clone));
          }
          break;
        case CHAR:
          if(checkedType.checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.CharArrSeq.CheckedList.ListItr.parent(clone));
            Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.CharArrSeq.CheckedList.ListItr.cursor(clone));
            Assertions.assertEquals(expectedItrModCount,
                FieldAndMethodAccessor.CharArrSeq.CheckedList.ListItr.modCount(clone));
            Assertions.assertEquals(expectedLastRet,
                FieldAndMethodAccessor.CharArrSeq.CheckedList.ListItr.lastRet(clone));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.CharArrSeq.UncheckedList.ListItr.parent(clone));
            Assertions.assertEquals(expectedCursor,
                FieldAndMethodAccessor.CharArrSeq.UncheckedList.ListItr.cursor(clone));
            Assertions.assertEquals(expectedLastRet,
                FieldAndMethodAccessor.CharArrSeq.UncheckedList.ListItr.lastRet(clone));
          }
          break;
        case SHORT:
          if(checkedType.checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.ShortArrSeq.CheckedList.ListItr.parent(clone));
            Assertions.assertEquals(expectedCursor,
                FieldAndMethodAccessor.ShortArrSeq.CheckedList.ListItr.cursor(clone));
            Assertions.assertEquals(expectedItrModCount,
                FieldAndMethodAccessor.ShortArrSeq.CheckedList.ListItr.modCount(clone));
            Assertions.assertEquals(expectedLastRet,
                FieldAndMethodAccessor.ShortArrSeq.CheckedList.ListItr.lastRet(clone));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.ShortArrSeq.UncheckedList.ListItr.parent(clone));
            Assertions.assertEquals(expectedCursor,
                FieldAndMethodAccessor.ShortArrSeq.UncheckedList.ListItr.cursor(clone));
            Assertions.assertEquals(expectedLastRet,
                FieldAndMethodAccessor.ShortArrSeq.UncheckedList.ListItr.lastRet(clone));
          }
          break;
        case INT:
          if(checkedType.checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.IntArrSeq.CheckedList.ListItr.parent(clone));
            Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.IntArrSeq.CheckedList.ListItr.cursor(clone));
            Assertions.assertEquals(expectedItrModCount,
                FieldAndMethodAccessor.IntArrSeq.CheckedList.ListItr.modCount(clone));
            Assertions.assertEquals(expectedLastRet,
                FieldAndMethodAccessor.IntArrSeq.CheckedList.ListItr.lastRet(clone));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.IntArrSeq.UncheckedList.ListItr.parent(clone));
            Assertions.assertEquals(expectedCursor,
                FieldAndMethodAccessor.IntArrSeq.UncheckedList.ListItr.cursor(clone));
            Assertions.assertEquals(expectedLastRet,
                FieldAndMethodAccessor.IntArrSeq.UncheckedList.ListItr.lastRet(clone));
          }
          break;
        case LONG:
          if(checkedType.checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.LongArrSeq.CheckedList.ListItr.parent(clone));
            Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.LongArrSeq.CheckedList.ListItr.cursor(clone));
            Assertions.assertEquals(expectedItrModCount,
                FieldAndMethodAccessor.LongArrSeq.CheckedList.ListItr.modCount(clone));
            Assertions.assertEquals(expectedLastRet,
                FieldAndMethodAccessor.LongArrSeq.CheckedList.ListItr.lastRet(clone));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.LongArrSeq.UncheckedList.ListItr.parent(clone));
            Assertions.assertEquals(expectedCursor,
                FieldAndMethodAccessor.LongArrSeq.UncheckedList.ListItr.cursor(clone));
            Assertions.assertEquals(expectedLastRet,
                FieldAndMethodAccessor.LongArrSeq.UncheckedList.ListItr.lastRet(clone));
          }
          break;
        case FLOAT:
          if(checkedType.checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.FloatArrSeq.CheckedList.ListItr.parent(clone));
            Assertions.assertEquals(expectedCursor,
                FieldAndMethodAccessor.FloatArrSeq.CheckedList.ListItr.cursor(clone));
            Assertions.assertEquals(expectedItrModCount,
                FieldAndMethodAccessor.FloatArrSeq.CheckedList.ListItr.modCount(clone));
            Assertions.assertEquals(expectedLastRet,
                FieldAndMethodAccessor.FloatArrSeq.CheckedList.ListItr.lastRet(clone));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.FloatArrSeq.UncheckedList.ListItr.parent(clone));
            Assertions.assertEquals(expectedCursor,
                FieldAndMethodAccessor.FloatArrSeq.UncheckedList.ListItr.cursor(clone));
            Assertions.assertEquals(expectedLastRet,
                FieldAndMethodAccessor.FloatArrSeq.UncheckedList.ListItr.lastRet(clone));
          }
          break;
        case DOUBLE:
          if(checkedType.checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleArrSeq.CheckedList.ListItr.parent(clone));
            Assertions.assertEquals(expectedCursor,
                FieldAndMethodAccessor.DoubleArrSeq.CheckedList.ListItr.cursor(clone));
            Assertions.assertEquals(expectedItrModCount,
                FieldAndMethodAccessor.DoubleArrSeq.CheckedList.ListItr.modCount(clone));
            Assertions.assertEquals(expectedLastRet,
                FieldAndMethodAccessor.DoubleArrSeq.CheckedList.ListItr.lastRet(clone));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleArrSeq.UncheckedList.ListItr.parent(clone));
            Assertions.assertEquals(expectedCursor,
                FieldAndMethodAccessor.DoubleArrSeq.UncheckedList.ListItr.cursor(clone));
            Assertions.assertEquals(expectedLastRet,
                FieldAndMethodAccessor.DoubleArrSeq.UncheckedList.ListItr.lastRet(clone));
          }
          break;
        case REF:
          if(checkedType.checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.RefArrSeq.CheckedList.ListItr.parent(clone));
            Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.RefArrSeq.CheckedList.ListItr.cursor(clone));
            Assertions.assertEquals(expectedItrModCount,
                FieldAndMethodAccessor.RefArrSeq.CheckedList.ListItr.modCount(clone));
            Assertions.assertEquals(expectedLastRet,
                FieldAndMethodAccessor.RefArrSeq.CheckedList.ListItr.lastRet(clone));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.RefArrSeq.UncheckedList.ListItr.parent(clone));
            Assertions.assertEquals(expectedCursor,
                FieldAndMethodAccessor.RefArrSeq.UncheckedList.ListItr.cursor(clone));
            Assertions.assertEquals(expectedLastRet,
                FieldAndMethodAccessor.RefArrSeq.UncheckedList.ListItr.lastRet(clone));
          }
          break;
        default:
          throw dataType.invalid();
        }
      }
      @Override public void verifyPreviousResult(DataType outputType,Object result){
        verifyGetResult(expectedLastRet,result,outputType);
      }
    }
    public ArrListMonitor(CheckedType checkedType,DataType dataType){
      super(checkedType,dataType);
    }
    public ArrListMonitor(CheckedType checkedType,DataType dataType,int initCap){
      super(checkedType,dataType,initCap);
    }
    public ArrListMonitor(SequenceInitParams initParams){
      super(initParams.checkedType,initParams.collectionType);
    }
    public ArrListMonitor(SequenceInitParams initParams,int initCap){
      super(initParams.checkedType,initParams.collectionType,initCap);
    }
    @Override public MonitoredIterator<? extends OmniIterator<?>,SEQ> getMonitoredIterator(){
      return new ItrMonitor();
    }
    @Override public MonitoredIterator<? extends OmniIterator<?>,SEQ> getMonitoredIterator(int index,
        IteratorType itrType){
      switch(itrType){
      case AscendingItr:
        return getMonitoredIterator(index);
      case BidirectionalItr:{
        return getMonitoredListIterator(index);
      }
      default:
        throw itrType.invalid();
      }
    }
    @Override public MonitoredIterator<? extends OmniIterator<?>,SEQ> getMonitoredIterator(IteratorType itrType){
      switch(itrType){
      case AscendingItr:
        return getMonitoredIterator();
      case BidirectionalItr:{
        return getMonitoredListIterator();
      }
      default:
        throw itrType.invalid();
      }
    }
    @Override public MonitoredListIterator<? extends OmniListIterator<?>,SEQ> getMonitoredListIterator(){
      return new ListItrMonitor();
    }
    @Override public MonitoredListIterator<? extends OmniListIterator<?>,SEQ> getMonitoredListIterator(int index){
      return new ListItrMonitor(index);
    }
    @Override public ArrSubListMonitor<?,SEQ,E> getMonitoredSubList(int fromIndex,int toIndex){
      return new ArrSubListMonitor<>(this,fromIndex,toIndex);
    }
    @Override public StructType getStructType(){
      return StructType.ArrList;
    }
    @Override public void modCollection(){
      switch(dataType){
      case BOOLEAN:
        ++((BooleanArrSeq.CheckedList)seq).modCount;
        break;
      case BYTE:
        ++((ByteArrSeq.CheckedList)seq).modCount;
        break;
      case CHAR:
        ++((CharArrSeq.CheckedList)seq).modCount;
        break;
      case DOUBLE:
        ++((DoubleArrSeq.CheckedList)seq).modCount;
        break;
      case FLOAT:
        ++((FloatArrSeq.CheckedList)seq).modCount;
        break;
      case INT:
        ++((IntArrSeq.CheckedList)seq).modCount;
        break;
      case LONG:
        ++((LongArrSeq.CheckedList)seq).modCount;
        break;
      case REF:
        ++((RefArrSeq.CheckedList<?>)seq).modCount;
        break;
      case SHORT:
        ++((ShortArrSeq.CheckedList)seq).modCount;
        break;
      default:
        throw dataType.invalid();
      }
      ++expectedModCount;
    }
    @Override public Object removeFirst(){
      final var removed=seq.remove(0);
      super.updateRemoveIndexState(0);
      return removed;
    }
    @SuppressWarnings("unchecked") @Override SEQ initSeq(){
      switch(dataType){
      case BOOLEAN:
        if(checkedType.checked){
          return (SEQ)new BooleanArrSeq.CheckedList();
        }else{
          return (SEQ)new BooleanArrSeq.UncheckedList();
        }
      case BYTE:
        if(checkedType.checked){
          return (SEQ)new ByteArrSeq.CheckedList();
        }else{
          return (SEQ)new ByteArrSeq.UncheckedList();
        }
      case CHAR:
        if(checkedType.checked){
          return (SEQ)new CharArrSeq.CheckedList();
        }else{
          return (SEQ)new CharArrSeq.UncheckedList();
        }
      case DOUBLE:
        if(checkedType.checked){
          return (SEQ)new DoubleArrSeq.CheckedList();
        }else{
          return (SEQ)new DoubleArrSeq.UncheckedList();
        }
      case FLOAT:
        if(checkedType.checked){
          return (SEQ)new FloatArrSeq.CheckedList();
        }else{
          return (SEQ)new FloatArrSeq.UncheckedList();
        }
      case INT:
        if(checkedType.checked){
          return (SEQ)new IntArrSeq.CheckedList();
        }else{
          return (SEQ)new IntArrSeq.UncheckedList();
        }
      case LONG:
        if(checkedType.checked){
          return (SEQ)new LongArrSeq.CheckedList();
        }else{
          return (SEQ)new LongArrSeq.UncheckedList();
        }
      case REF:
        if(checkedType.checked){
          return (SEQ)new RefArrSeq.CheckedList<>();
        }else{
          return (SEQ)new RefArrSeq.UncheckedList<>();
        }
      case SHORT:
        if(checkedType.checked){
          return (SEQ)new ShortArrSeq.CheckedList();
        }else{
          return (SEQ)new ShortArrSeq.UncheckedList();
        }
      default:
        throw dataType.invalid();
      }
    }
    @SuppressWarnings("unchecked") @Override SEQ initSeq(int initCap){
      switch(dataType){
      case BOOLEAN:
        if(checkedType.checked){
          return (SEQ)new BooleanArrSeq.CheckedList(initCap);
        }else{
          return (SEQ)new BooleanArrSeq.UncheckedList(initCap);
        }
      case BYTE:
        if(checkedType.checked){
          return (SEQ)new ByteArrSeq.CheckedList(initCap);
        }else{
          return (SEQ)new ByteArrSeq.UncheckedList(initCap);
        }
      case CHAR:
        if(checkedType.checked){
          return (SEQ)new CharArrSeq.CheckedList(initCap);
        }else{
          return (SEQ)new CharArrSeq.UncheckedList(initCap);
        }
      case DOUBLE:
        if(checkedType.checked){
          return (SEQ)new DoubleArrSeq.CheckedList(initCap);
        }else{
          return (SEQ)new DoubleArrSeq.UncheckedList(initCap);
        }
      case FLOAT:
        if(checkedType.checked){
          return (SEQ)new FloatArrSeq.CheckedList(initCap);
        }else{
          return (SEQ)new FloatArrSeq.UncheckedList(initCap);
        }
      case INT:
        if(checkedType.checked){
          return (SEQ)new IntArrSeq.CheckedList(initCap);
        }else{
          return (SEQ)new IntArrSeq.UncheckedList(initCap);
        }
      case LONG:
        if(checkedType.checked){
          return (SEQ)new LongArrSeq.CheckedList(initCap);
        }else{
          return (SEQ)new LongArrSeq.UncheckedList(initCap);
        }
      case REF:
        if(checkedType.checked){
          return (SEQ)new RefArrSeq.CheckedList<>(initCap);
        }else{
          return (SEQ)new RefArrSeq.UncheckedList<>(initCap);
        }
      case SHORT:
        if(checkedType.checked){
          return (SEQ)new ShortArrSeq.CheckedList(initCap);
        }else{
          return (SEQ)new ShortArrSeq.UncheckedList(initCap);
        }
      default:
        throw dataType.invalid();
      }
    }
    @Override void updateModCount(){
      switch(dataType){
      case BOOLEAN:
        expectedModCount=((BooleanArrSeq.CheckedList)seq).modCount;
        break;
      case BYTE:
        expectedModCount=((ByteArrSeq.CheckedList)seq).modCount;
        break;
      case CHAR:
        expectedModCount=((CharArrSeq.CheckedList)seq).modCount;
        break;
      case DOUBLE:
        expectedModCount=((DoubleArrSeq.CheckedList)seq).modCount;
        break;
      case FLOAT:
        expectedModCount=((FloatArrSeq.CheckedList)seq).modCount;
        break;
      case INT:
        expectedModCount=((IntArrSeq.CheckedList)seq).modCount;
        break;
      case LONG:
        expectedModCount=((LongArrSeq.CheckedList)seq).modCount;
        break;
      case REF:
        expectedModCount=((RefArrSeq.CheckedList<?>)seq).modCount;
        break;
      case SHORT:
        expectedModCount=((ShortArrSeq.CheckedList)seq).modCount;
        break;
      default:
        throw dataType.invalid();
      }
    }
    @Override void verifyCloneTypeAndModCount(Object clone){
      switch(dataType){
      case BOOLEAN:{
        Assertions.assertEquals(checkedType.checked,clone instanceof BooleanArrSeq.CheckedList);
        if(checkedType.checked){
          Assertions.assertEquals(0,((BooleanArrSeq.CheckedList)clone).modCount);
        }else{
          Assertions.assertTrue(clone instanceof BooleanArrSeq.UncheckedList);
        }
        break;
      }
      case BYTE:{
        Assertions.assertEquals(checkedType.checked,clone instanceof ByteArrSeq.CheckedList);
        if(checkedType.checked){
          Assertions.assertEquals(0,((ByteArrSeq.CheckedList)clone).modCount);
        }else{
          Assertions.assertTrue(clone instanceof ByteArrSeq.UncheckedList);
        }
        break;
      }
      case CHAR:{
        Assertions.assertEquals(checkedType.checked,clone instanceof CharArrSeq.CheckedList);
        if(checkedType.checked){
          Assertions.assertEquals(0,((CharArrSeq.CheckedList)clone).modCount);
        }else{
          Assertions.assertTrue(clone instanceof CharArrSeq.UncheckedList);
        }
        break;
      }
      case DOUBLE:{
        Assertions.assertEquals(checkedType.checked,clone instanceof DoubleArrSeq.CheckedList);
        if(checkedType.checked){
          Assertions.assertEquals(0,((DoubleArrSeq.CheckedList)clone).modCount);
        }else{
          Assertions.assertTrue(clone instanceof DoubleArrSeq.UncheckedList);
        }
        break;
      }
      case FLOAT:{
        Assertions.assertEquals(checkedType.checked,clone instanceof FloatArrSeq.CheckedList);
        if(checkedType.checked){
          Assertions.assertEquals(0,((FloatArrSeq.CheckedList)clone).modCount);
        }else{
          Assertions.assertTrue(clone instanceof FloatArrSeq.UncheckedList);
        }
        break;
      }
      case INT:{
        Assertions.assertEquals(checkedType.checked,clone instanceof IntArrSeq.CheckedList);
        if(checkedType.checked){
          Assertions.assertEquals(0,((IntArrSeq.CheckedList)clone).modCount);
        }else{
          Assertions.assertTrue(clone instanceof IntArrSeq.UncheckedList);
        }
        break;
      }
      case LONG:{
        Assertions.assertEquals(checkedType.checked,clone instanceof LongArrSeq.CheckedList);
        if(checkedType.checked){
          Assertions.assertEquals(0,((LongArrSeq.CheckedList)clone).modCount);
        }else{
          Assertions.assertTrue(clone instanceof LongArrSeq.UncheckedList);
        }
        break;
      }
      case REF:{
        Assertions.assertEquals(checkedType.checked,clone instanceof RefArrSeq.CheckedList);
        if(checkedType.checked){
          Assertions.assertEquals(0,((RefArrSeq.CheckedList<?>)clone).modCount);
        }else{
          Assertions.assertTrue(clone instanceof RefArrSeq.UncheckedList);
        }
        break;
      }
      case SHORT:{
        Assertions.assertEquals(checkedType.checked,clone instanceof ShortArrSeq.CheckedList);
        if(checkedType.checked){
          Assertions.assertEquals(0,((ShortArrSeq.CheckedList)clone).modCount);
        }else{
          Assertions.assertTrue(clone instanceof ShortArrSeq.UncheckedList);
        }
        break;
      }
      default:
        throw dataType.invalid();
      }
    }
    @Override void verifyModCount(){
      int actualModCount;
      switch(dataType){
      case BOOLEAN:
        actualModCount=((BooleanArrSeq.CheckedList)seq).modCount;
        break;
      case BYTE:
        actualModCount=((ByteArrSeq.CheckedList)seq).modCount;
        break;
      case CHAR:
        actualModCount=((CharArrSeq.CheckedList)seq).modCount;
        break;
      case DOUBLE:
        actualModCount=((DoubleArrSeq.CheckedList)seq).modCount;
        break;
      case FLOAT:
        actualModCount=((FloatArrSeq.CheckedList)seq).modCount;
        break;
      case INT:
        actualModCount=((IntArrSeq.CheckedList)seq).modCount;
        break;
      case LONG:
        actualModCount=((LongArrSeq.CheckedList)seq).modCount;
        break;
      case REF:
        actualModCount=((RefArrSeq.CheckedList<?>)seq).modCount;
        break;
      case SHORT:
        actualModCount=((ShortArrSeq.CheckedList)seq).modCount;
        break;
      default:
        throw dataType.invalid();
      }
      Assertions.assertEquals(expectedModCount,actualModCount);
    }
  }
  private static class ArrStackMonitor<SEQ extends AbstractSeq<E>&OmniStack<E>&Externalizable,E>
      extends AbstractArrSeqMonitor<SEQ> implements MonitoredStack<SEQ>{
    private class CheckedItrMonitor extends UncheckedItrMonitor{
      @Override public void verifyCloneHelper(Object clone){
        switch(dataType){
        case BOOLEAN:
          Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanArrSeq.CheckedStack.Itr.parent(clone));
          Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.BooleanArrSeq.CheckedStack.Itr.cursor(clone));
          Assertions.assertEquals(expectedItrModCount,
              FieldAndMethodAccessor.BooleanArrSeq.CheckedStack.Itr.modCount(clone));
          Assertions.assertEquals(expectedLastRet,FieldAndMethodAccessor.BooleanArrSeq.CheckedStack.Itr.lastRet(clone));
          break;
        case BYTE:
          Assertions.assertSame(seq,FieldAndMethodAccessor.ByteArrSeq.CheckedStack.Itr.parent(clone));
          Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.ByteArrSeq.CheckedStack.Itr.cursor(clone));
          Assertions.assertEquals(expectedItrModCount,
              FieldAndMethodAccessor.ByteArrSeq.CheckedStack.Itr.modCount(clone));
          Assertions.assertEquals(expectedLastRet,FieldAndMethodAccessor.ByteArrSeq.CheckedStack.Itr.lastRet(clone));
          break;
        case CHAR:
          Assertions.assertSame(seq,FieldAndMethodAccessor.CharArrSeq.CheckedStack.Itr.parent(clone));
          Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.CharArrSeq.CheckedStack.Itr.cursor(clone));
          Assertions.assertEquals(expectedItrModCount,
              FieldAndMethodAccessor.CharArrSeq.CheckedStack.Itr.modCount(clone));
          Assertions.assertEquals(expectedLastRet,FieldAndMethodAccessor.CharArrSeq.CheckedStack.Itr.lastRet(clone));
          break;
        case DOUBLE:
          Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleArrSeq.CheckedStack.Itr.parent(clone));
          Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.DoubleArrSeq.CheckedStack.Itr.cursor(clone));
          Assertions.assertEquals(expectedItrModCount,
              FieldAndMethodAccessor.DoubleArrSeq.CheckedStack.Itr.modCount(clone));
          Assertions.assertEquals(expectedLastRet,FieldAndMethodAccessor.DoubleArrSeq.CheckedStack.Itr.lastRet(clone));
          break;
        case FLOAT:
          Assertions.assertSame(seq,FieldAndMethodAccessor.FloatArrSeq.CheckedStack.Itr.parent(clone));
          Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.FloatArrSeq.CheckedStack.Itr.cursor(clone));
          Assertions.assertEquals(expectedItrModCount,
              FieldAndMethodAccessor.FloatArrSeq.CheckedStack.Itr.modCount(clone));
          Assertions.assertEquals(expectedLastRet,FieldAndMethodAccessor.FloatArrSeq.CheckedStack.Itr.lastRet(clone));
          break;
        case INT:
          Assertions.assertSame(seq,FieldAndMethodAccessor.IntArrSeq.CheckedStack.Itr.parent(clone));
          Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.IntArrSeq.CheckedStack.Itr.cursor(clone));
          Assertions.assertEquals(expectedItrModCount,
              FieldAndMethodAccessor.IntArrSeq.CheckedStack.Itr.modCount(clone));
          Assertions.assertEquals(expectedLastRet,FieldAndMethodAccessor.IntArrSeq.CheckedStack.Itr.lastRet(clone));
          break;
        case LONG:
          Assertions.assertSame(seq,FieldAndMethodAccessor.LongArrSeq.CheckedStack.Itr.parent(clone));
          Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.LongArrSeq.CheckedStack.Itr.cursor(clone));
          Assertions.assertEquals(expectedItrModCount,
              FieldAndMethodAccessor.LongArrSeq.CheckedStack.Itr.modCount(clone));
          Assertions.assertEquals(expectedLastRet,FieldAndMethodAccessor.LongArrSeq.CheckedStack.Itr.lastRet(clone));
          break;
        case REF:
          Assertions.assertSame(seq,FieldAndMethodAccessor.RefArrSeq.CheckedStack.Itr.parent(clone));
          Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.RefArrSeq.CheckedStack.Itr.cursor(clone));
          Assertions.assertEquals(expectedItrModCount,
              FieldAndMethodAccessor.RefArrSeq.CheckedStack.Itr.modCount(clone));
          Assertions.assertEquals(expectedLastRet,FieldAndMethodAccessor.RefArrSeq.CheckedStack.Itr.lastRet(clone));
          break;
        case SHORT:
          Assertions.assertSame(seq,FieldAndMethodAccessor.ShortArrSeq.CheckedStack.Itr.parent(clone));
          Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.ShortArrSeq.CheckedStack.Itr.cursor(clone));
          Assertions.assertEquals(expectedItrModCount,
              FieldAndMethodAccessor.ShortArrSeq.CheckedStack.Itr.modCount(clone));
          Assertions.assertEquals(expectedLastRet,FieldAndMethodAccessor.ShortArrSeq.CheckedStack.Itr.lastRet(clone));
          break;
        default:
          throw dataType.invalid();
        }
      }
    }
    private class UncheckedItrMonitor implements MonitoredCollection.MonitoredIterator<OmniIterator<?>,SEQ>{
      final OmniIterator<?> itr=seq.iterator();
      int expectedCursor=expectedSize;
      int expectedLastRet=-1;
      int expectedItrModCount=expectedModCount;
      @Override public OmniIterator<?> getIterator(){
        return itr;
      }
      @Override public IteratorType getIteratorType(){
        return IteratorType.AscendingItr;
      }
      @Override public MonitoredCollection<SEQ> getMonitoredCollection(){
        return ArrStackMonitor.this;
      }
      @Override public int getNumLeft(){
        return expectedCursor;
      }
      @Override public boolean hasNext(){
        return expectedCursor > 0;
      }
      @Override public boolean nextWasJustCalled(){
        return expectedLastRet != -1;
      }
      @Override public void updateItrNextState(){
        expectedLastRet=--expectedCursor;
      }
      @Override public void updateItrRemoveState(){
        updateRemoveIndexState(expectedCursor);
        expectedLastRet=-1;
        ++expectedItrModCount;
      }
      @Override public void verifyCloneHelper(Object clone){
        switch(dataType){
        case BOOLEAN:
          Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanArrSeq.UncheckedStack.Itr.parent(clone));
          Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.BooleanArrSeq.UncheckedStack.Itr.cursor(clone));
          break;
        case BYTE:
          Assertions.assertSame(seq,FieldAndMethodAccessor.ByteArrSeq.UncheckedStack.Itr.parent(clone));
          Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.ByteArrSeq.UncheckedStack.Itr.cursor(clone));
          break;
        case CHAR:
          Assertions.assertSame(seq,FieldAndMethodAccessor.CharArrSeq.UncheckedStack.Itr.parent(clone));
          Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.CharArrSeq.UncheckedStack.Itr.cursor(clone));
          break;
        case DOUBLE:
          Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleArrSeq.UncheckedStack.Itr.parent(clone));
          Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.DoubleArrSeq.UncheckedStack.Itr.cursor(clone));
          break;
        case FLOAT:
          Assertions.assertSame(seq,FieldAndMethodAccessor.FloatArrSeq.UncheckedStack.Itr.parent(clone));
          Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.FloatArrSeq.UncheckedStack.Itr.cursor(clone));
          break;
        case INT:
          Assertions.assertSame(seq,FieldAndMethodAccessor.IntArrSeq.UncheckedStack.Itr.parent(clone));
          Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.IntArrSeq.UncheckedStack.Itr.cursor(clone));
          break;
        case LONG:
          Assertions.assertSame(seq,FieldAndMethodAccessor.LongArrSeq.UncheckedStack.Itr.parent(clone));
          Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.LongArrSeq.UncheckedStack.Itr.cursor(clone));
          break;
        case REF:
          Assertions.assertSame(seq,FieldAndMethodAccessor.RefArrSeq.UncheckedStack.Itr.parent(clone));
          Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.RefArrSeq.UncheckedStack.Itr.cursor(clone));
          break;
        case SHORT:
          Assertions.assertSame(seq,FieldAndMethodAccessor.ShortArrSeq.UncheckedStack.Itr.parent(clone));
          Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.ShortArrSeq.UncheckedStack.Itr.cursor(clone));
          break;
        default:
          throw dataType.invalid();
        }
      }
      @Override public void verifyForEachRemaining(MonitoredFunction function){
        int expectedCursor=this.expectedCursor;
        final var functionItr=function.iterator();
        IntConsumer functionVerifier;
        switch(dataType){
        case BOOLEAN:{
          final var expectedArr=(boolean[])ArrStackMonitor.this.expectedArr;
          functionVerifier=cursor->Assertions.assertEquals(expectedArr[cursor],(boolean)functionItr.next());
          break;
        }
        case BYTE:{
          final var expectedArr=(byte[])ArrStackMonitor.this.expectedArr;
          functionVerifier=cursor->Assertions.assertEquals(expectedArr[cursor],(byte)functionItr.next());
          break;
        }
        case CHAR:{
          final var expectedArr=(char[])ArrStackMonitor.this.expectedArr;
          functionVerifier=cursor->Assertions.assertEquals(expectedArr[cursor],(char)functionItr.next());
          break;
        }
        case SHORT:{
          final var expectedArr=(short[])ArrStackMonitor.this.expectedArr;
          functionVerifier=cursor->Assertions.assertEquals(expectedArr[cursor],(short)functionItr.next());
          break;
        }
        case INT:{
          final var expectedArr=(int[])ArrStackMonitor.this.expectedArr;
          functionVerifier=cursor->Assertions.assertEquals(expectedArr[cursor],(int)functionItr.next());
          break;
        }
        case LONG:{
          final var expectedArr=(long[])ArrStackMonitor.this.expectedArr;
          functionVerifier=cursor->Assertions.assertEquals(expectedArr[cursor],(long)functionItr.next());
          break;
        }
        case FLOAT:{
          final var expectedArr=(float[])ArrStackMonitor.this.expectedArr;
          functionVerifier=cursor->Assertions.assertEquals(expectedArr[cursor],(float)functionItr.next());
          break;
        }
        case DOUBLE:{
          final var expectedArr=(double[])ArrStackMonitor.this.expectedArr;
          functionVerifier=cursor->Assertions.assertEquals(expectedArr[cursor],(double)functionItr.next());
          break;
        }
        case REF:{
          final var expectedArr=(Object[])ArrStackMonitor.this.expectedArr;
          functionVerifier=cursor->Assertions.assertSame(expectedArr[cursor],functionItr.next());
          break;
        }
        default:
          throw dataType.invalid();
        }
        while(functionItr.hasNext()){
          functionVerifier.accept(--expectedCursor);
        }
        if(expectedCursor == 0 && !function.isEmpty()){
          this.expectedCursor=0;
          expectedLastRet=0;
        }
      }
      @Override public void verifyNextResult(DataType outputType,Object result){
        verifyGetResult(expectedCursor - 1,result,outputType);
      }
    }
    public ArrStackMonitor(CheckedType checkedType,DataType dataType){
      super(checkedType,dataType);
    }
    public ArrStackMonitor(CheckedType checkedType,DataType dataType,int initCap){
      super(checkedType,dataType,initCap);
    }
    public ArrStackMonitor(SequenceInitParams initParams){
      super(initParams.checkedType,initParams.collectionType);
    }
    public ArrStackMonitor(SequenceInitParams initParams,int initCap){
      super(initParams.checkedType,initParams.collectionType,initCap);
    }
    @Override public int findRemoveValIndex(Object inputVal,DataType inputType,int fromIndex,int toIndex){
      switch(dataType){
      case BOOLEAN:{
        boolean inputCast;
        switch(inputType){
        case BOOLEAN:
          inputCast=(boolean)inputVal;
          break;
        case BYTE:
          inputCast=(byte)inputVal == 1;
          break;
        case CHAR:
          inputCast=(char)inputVal == 1;
          break;
        case SHORT:
          inputCast=(short)inputVal == 1;
          break;
        case INT:
          inputCast=(int)inputVal == 1;
          break;
        case LONG:
          inputCast=(long)inputVal == 1L;
          break;
        case FLOAT:
          inputCast=(float)inputVal == 1F;
          break;
        case DOUBLE:
          inputCast=(double)inputVal == 1D;
          break;
        default:
          throw inputType.invalid();
        }
        final var expectedArr=(boolean[])this.expectedArr;
        for(int i=toIndex - 1;;--i){
          if(expectedArr[i] == inputCast){ return i; }
        }
      }
      case BYTE:{
        byte inputCast;
        switch(inputType){
        case BOOLEAN:
          inputCast=(boolean)inputVal?(byte)1:(byte)0;
          break;
        case BYTE:
          inputCast=(byte)inputVal;
          break;
        case CHAR:
          inputCast=(byte)(char)inputVal;
          break;
        case SHORT:
          inputCast=(byte)(short)inputVal;
          break;
        case INT:
          inputCast=(byte)(int)inputVal;
          break;
        case LONG:
          inputCast=(byte)(long)inputVal;
          break;
        case FLOAT:
          inputCast=(byte)(float)inputVal;
          break;
        case DOUBLE:
          inputCast=(byte)(double)inputVal;
          break;
        default:
          throw inputType.invalid();
        }
        final var expectedArr=(byte[])this.expectedArr;
        for(int i=toIndex - 1;;--i){
          if(expectedArr[i] == inputCast){ return i; }
        }
      }
      case CHAR:{
        char inputCast;
        switch(inputType){
        case BOOLEAN:
          inputCast=(boolean)inputVal?(char)1:(char)0;
          break;
        case BYTE:
          inputCast=(char)(byte)inputVal;
          break;
        case CHAR:
          inputCast=(char)inputVal;
          break;
        case SHORT:
          inputCast=(char)(short)inputVal;
          break;
        case INT:
          inputCast=(char)(int)inputVal;
          break;
        case LONG:
          inputCast=(char)(long)inputVal;
          break;
        case FLOAT:
          inputCast=(char)(float)inputVal;
          break;
        case DOUBLE:
          inputCast=(char)(double)inputVal;
          break;
        default:
          throw inputType.invalid();
        }
        final var expectedArr=(char[])this.expectedArr;
        for(int i=toIndex - 1;;--i){
          if(expectedArr[i] == inputCast){ return i; }
        }
      }
      case SHORT:{
        short inputCast;
        switch(inputType){
        case BOOLEAN:
          inputCast=(boolean)inputVal?(short)1:(short)0;
          break;
        case BYTE:
          inputCast=(byte)inputVal;
          break;
        case CHAR:
          inputCast=(short)(char)inputVal;
          break;
        case SHORT:
          inputCast=(short)inputVal;
          break;
        case INT:
          inputCast=(short)(int)inputVal;
          break;
        case LONG:
          inputCast=(short)(long)inputVal;
          break;
        case FLOAT:
          inputCast=(short)(float)inputVal;
          break;
        case DOUBLE:
          inputCast=(short)(double)inputVal;
          break;
        default:
          throw inputType.invalid();
        }
        final var expectedArr=(short[])this.expectedArr;
        for(int i=toIndex - 1;;--i){
          if(expectedArr[i] == inputCast){ return i; }
        }
      }
      case INT:{
        int inputCast;
        switch(inputType){
        case BOOLEAN:
          inputCast=(boolean)inputVal?1:0;
          break;
        case BYTE:
          inputCast=(byte)inputVal;
          break;
        case CHAR:
          inputCast=(char)inputVal;
          break;
        case SHORT:
          inputCast=(short)inputVal;
          break;
        case INT:
          inputCast=(int)inputVal;
          break;
        case LONG:
          inputCast=(int)(long)inputVal;
          break;
        case FLOAT:
          inputCast=(int)(float)inputVal;
          break;
        case DOUBLE:
          inputCast=(int)(double)inputVal;
          break;
        default:
          throw inputType.invalid();
        }
        final var expectedArr=(int[])this.expectedArr;
        for(int i=toIndex - 1;;--i){
          if(expectedArr[i] == inputCast){ return i; }
        }
      }
      case LONG:{
        long inputCast;
        switch(inputType){
        case BOOLEAN:
          inputCast=(boolean)inputVal?1L:0L;
          break;
        case BYTE:
          inputCast=(byte)inputVal;
          break;
        case CHAR:
          inputCast=(char)inputVal;
          break;
        case SHORT:
          inputCast=(short)inputVal;
          break;
        case INT:
          inputCast=(int)inputVal;
          break;
        case LONG:
          inputCast=(long)inputVal;
          break;
        case FLOAT:
          inputCast=(long)(float)inputVal;
          break;
        case DOUBLE:
          inputCast=(long)(double)inputVal;
          break;
        default:
          throw inputType.invalid();
        }
        final var expectedArr=(long[])this.expectedArr;
        for(int i=toIndex - 1;;--i){
          if(expectedArr[i] == inputCast){ return i; }
        }
      }
      case FLOAT:{
        float inputCast;
        switch(inputType){
        case BOOLEAN:
          inputCast=(boolean)inputVal?1.0f:0.0f;
          break;
        case BYTE:
          inputCast=(byte)inputVal;
          break;
        case CHAR:
          inputCast=(char)inputVal;
          break;
        case SHORT:
          inputCast=(short)inputVal;
          break;
        case INT:
          inputCast=(int)inputVal;
          break;
        case LONG:
          inputCast=(long)inputVal;
          break;
        case FLOAT:
          inputCast=(float)inputVal;
          break;
        case DOUBLE:
          inputCast=(float)(double)inputVal;
          break;
        default:
          throw inputType.invalid();
        }
        final var expectedArr=(float[])this.expectedArr;
        if(inputCast == inputCast){
          for(int i=toIndex - 1;;--i){
            if(expectedArr[i] == inputCast){ return i; }
          }
        }else{
          for(int i=toIndex - 1;;--i){
            float v;
            if((v=expectedArr[i]) != v){ return i; }
          }
        }
      }
      case DOUBLE:{
        double inputCast;
        switch(inputType){
        case BOOLEAN:
          inputCast=(boolean)inputVal?1.0:0.0;
          break;
        case BYTE:
          inputCast=(byte)inputVal;
          break;
        case CHAR:
          inputCast=(char)inputVal;
          break;
        case SHORT:
          inputCast=(short)inputVal;
          break;
        case INT:
          inputCast=(int)inputVal;
          break;
        case LONG:
          inputCast=(long)inputVal;
          break;
        case FLOAT:
          inputCast=(float)inputVal;
          break;
        case DOUBLE:
          inputCast=(double)inputVal;
          break;
        default:
          throw inputType.invalid();
        }
        final var expectedArr=(double[])this.expectedArr;
        if(inputCast == inputCast){
          for(int i=toIndex - 1;;--i){
            if(expectedArr[i] == inputCast){ return i; }
          }
        }else{
          for(int i=toIndex - 1;;--i){
            double v;
            if((v=expectedArr[i]) != v){ return i; }
          }
        }
      }
      case REF:{
        final var expectedArr=(Object[])this.expectedArr;
        if(inputVal == null){
          for(int i=toIndex - 1;;--i){
            if(expectedArr[i] == null){ return i; }
          }
        }else{
          for(int i=toIndex - 1;;--i){
            if(inputVal.equals(expectedArr[i])){ return i; }
          }
        }
      }
      default:
        throw dataType.invalid();
      }
    }
    @Override public Object get(int iterationIndex,DataType outputType){
      return super.get(expectedSize - iterationIndex - 1,outputType);
    }
    @Override public MonitoredIterator<? extends OmniIterator<?>,SEQ> getMonitoredIterator(){
      if(checkedType.checked){ return new CheckedItrMonitor(); }
      return new UncheckedItrMonitor();
    }
    @Override public MonitoredIterator<? extends OmniIterator<?>,SEQ> getMonitoredIterator(int index,
        IteratorType itrType){
      if(itrType != IteratorType.AscendingItr){ throw itrType.invalid(); }
      return getMonitoredIterator(index);
    }
    @Override public MonitoredIterator<? extends OmniIterator<?>,SEQ> getMonitoredIterator(IteratorType itrType){
      if(itrType != IteratorType.AscendingItr){ throw itrType.invalid(); }
      return getMonitoredIterator();
    }
    @Override public StructType getStructType(){
      return StructType.ArrStack;
    }
    @Override public void modCollection(){
      switch(dataType){
      case BOOLEAN:
        ++((BooleanArrSeq.CheckedStack)seq).modCount;
        break;
      case BYTE:
        ++((ByteArrSeq.CheckedStack)seq).modCount;
        break;
      case CHAR:
        ++((CharArrSeq.CheckedStack)seq).modCount;
        break;
      case DOUBLE:
        ++((DoubleArrSeq.CheckedStack)seq).modCount;
        break;
      case FLOAT:
        ++((FloatArrSeq.CheckedStack)seq).modCount;
        break;
      case INT:
        ++((IntArrSeq.CheckedStack)seq).modCount;
        break;
      case LONG:
        ++((LongArrSeq.CheckedStack)seq).modCount;
        break;
      case REF:
        ++((RefArrSeq.CheckedStack<?>)seq).modCount;
        break;
      case SHORT:
        ++((ShortArrSeq.CheckedStack)seq).modCount;
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
      final int size=expectedSize;
      try{
        result=outputType.callPeek(seq);
      }finally{
        verifyCollectionState(true);
      }
      if(size == 0){
        Assertions.assertEquals(outputType.defaultVal,result);
      }else{
        verifyGetResult(size - 1,result,outputType);
      }
      return result;
    }
    @Override public Object verifyPoll(DataType outputType){
      Object result;
      final Object expected=outputType.callPeek(seq);
      final int size=expectedSize;
      try{
        result=outputType.callPoll(seq);
        if(size > 0){
          updateRemoveIndexState(size - 1);
        }
      }finally{
        verifyCollectionState(true);
      }
      Assertions.assertEquals(expected,result);
      return result;
    }
    @Override public Object verifyPop(DataType outputType){
      Object result;
      Object expected=null;
      int size;
      if(0 < (size=expectedSize)){
        expected=outputType.callPeek(seq);
      }
      try{
        result=outputType.callPop(seq);
        updateRemoveIndexState(size - 1);
      }finally{
        verifyCollectionState(true);
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
      verifyCollectionState(true);
    }
    @SuppressWarnings("unchecked") @Override SEQ initSeq(){
      switch(dataType){
      case BOOLEAN:
        if(checkedType.checked){
          return (SEQ)new BooleanArrSeq.CheckedStack();
        }else{
          return (SEQ)new BooleanArrSeq.UncheckedStack();
        }
      case BYTE:
        if(checkedType.checked){
          return (SEQ)new ByteArrSeq.CheckedStack();
        }else{
          return (SEQ)new ByteArrSeq.UncheckedStack();
        }
      case CHAR:
        if(checkedType.checked){
          return (SEQ)new CharArrSeq.CheckedStack();
        }else{
          return (SEQ)new CharArrSeq.UncheckedStack();
        }
      case DOUBLE:
        if(checkedType.checked){
          return (SEQ)new DoubleArrSeq.CheckedStack();
        }else{
          return (SEQ)new DoubleArrSeq.UncheckedStack();
        }
      case FLOAT:
        if(checkedType.checked){
          return (SEQ)new FloatArrSeq.CheckedStack();
        }else{
          return (SEQ)new FloatArrSeq.UncheckedStack();
        }
      case INT:
        if(checkedType.checked){
          return (SEQ)new IntArrSeq.CheckedStack();
        }else{
          return (SEQ)new IntArrSeq.UncheckedStack();
        }
      case LONG:
        if(checkedType.checked){
          return (SEQ)new LongArrSeq.CheckedStack();
        }else{
          return (SEQ)new LongArrSeq.UncheckedStack();
        }
      case REF:
        if(checkedType.checked){
          return (SEQ)new RefArrSeq.CheckedStack<>();
        }else{
          return (SEQ)new RefArrSeq.UncheckedStack<>();
        }
      case SHORT:
        if(checkedType.checked){
          return (SEQ)new ShortArrSeq.CheckedStack();
        }else{
          return (SEQ)new ShortArrSeq.UncheckedStack();
        }
      default:
        throw dataType.invalid();
      }
    }
    @SuppressWarnings("unchecked") @Override SEQ initSeq(int initCap){
      switch(dataType){
      case BOOLEAN:
        if(checkedType.checked){
          return (SEQ)new BooleanArrSeq.CheckedStack(initCap);
        }else{
          return (SEQ)new BooleanArrSeq.UncheckedStack(initCap);
        }
      case BYTE:
        if(checkedType.checked){
          return (SEQ)new ByteArrSeq.CheckedStack(initCap);
        }else{
          return (SEQ)new ByteArrSeq.UncheckedStack(initCap);
        }
      case CHAR:
        if(checkedType.checked){
          return (SEQ)new CharArrSeq.CheckedStack(initCap);
        }else{
          return (SEQ)new CharArrSeq.UncheckedStack(initCap);
        }
      case DOUBLE:
        if(checkedType.checked){
          return (SEQ)new DoubleArrSeq.CheckedStack(initCap);
        }else{
          return (SEQ)new DoubleArrSeq.UncheckedStack(initCap);
        }
      case FLOAT:
        if(checkedType.checked){
          return (SEQ)new FloatArrSeq.CheckedStack(initCap);
        }else{
          return (SEQ)new FloatArrSeq.UncheckedStack(initCap);
        }
      case INT:
        if(checkedType.checked){
          return (SEQ)new IntArrSeq.CheckedStack(initCap);
        }else{
          return (SEQ)new IntArrSeq.UncheckedStack(initCap);
        }
      case LONG:
        if(checkedType.checked){
          return (SEQ)new LongArrSeq.CheckedStack(initCap);
        }else{
          return (SEQ)new LongArrSeq.UncheckedStack(initCap);
        }
      case REF:
        if(checkedType.checked){
          return (SEQ)new RefArrSeq.CheckedStack<>(initCap);
        }else{
          return (SEQ)new RefArrSeq.UncheckedStack<>(initCap);
        }
      case SHORT:
        if(checkedType.checked){
          return (SEQ)new ShortArrSeq.CheckedStack(initCap);
        }else{
          return (SEQ)new ShortArrSeq.UncheckedStack(initCap);
        }
      default:
        throw dataType.invalid();
      }
    }
    @Override void updateModCount(){
      switch(dataType){
      case BOOLEAN:
        expectedModCount=((BooleanArrSeq.CheckedStack)seq).modCount;
        break;
      case BYTE:
        expectedModCount=((ByteArrSeq.CheckedStack)seq).modCount;
        break;
      case CHAR:
        expectedModCount=((CharArrSeq.CheckedStack)seq).modCount;
        break;
      case DOUBLE:
        expectedModCount=((DoubleArrSeq.CheckedStack)seq).modCount;
        break;
      case FLOAT:
        expectedModCount=((FloatArrSeq.CheckedStack)seq).modCount;
        break;
      case INT:
        expectedModCount=((IntArrSeq.CheckedStack)seq).modCount;
        break;
      case LONG:
        expectedModCount=((LongArrSeq.CheckedStack)seq).modCount;
        break;
      case REF:
        expectedModCount=((RefArrSeq.CheckedStack<?>)seq).modCount;
        break;
      case SHORT:
        expectedModCount=((ShortArrSeq.CheckedStack)seq).modCount;
        break;
      default:
        throw dataType.invalid();
      }
    }
    @Override void verifyCloneTypeAndModCount(Object clone){
      switch(dataType){
      case BOOLEAN:{
        Assertions.assertEquals(checkedType.checked,clone instanceof BooleanArrSeq.CheckedStack);
        if(checkedType.checked){
          Assertions.assertEquals(0,((BooleanArrSeq.CheckedStack)clone).modCount);
        }else{
          Assertions.assertTrue(clone instanceof BooleanArrSeq.UncheckedStack);
        }
        break;
      }
      case BYTE:{
        Assertions.assertEquals(checkedType.checked,clone instanceof ByteArrSeq.CheckedStack);
        if(checkedType.checked){
          Assertions.assertEquals(0,((ByteArrSeq.CheckedStack)clone).modCount);
        }else{
          Assertions.assertTrue(clone instanceof ByteArrSeq.UncheckedStack);
        }
        break;
      }
      case CHAR:{
        Assertions.assertEquals(checkedType.checked,clone instanceof CharArrSeq.CheckedStack);
        if(checkedType.checked){
          Assertions.assertEquals(0,((CharArrSeq.CheckedStack)clone).modCount);
        }else{
          Assertions.assertTrue(clone instanceof CharArrSeq.UncheckedStack);
        }
        break;
      }
      case DOUBLE:{
        Assertions.assertEquals(checkedType.checked,clone instanceof DoubleArrSeq.CheckedStack);
        if(checkedType.checked){
          Assertions.assertEquals(0,((DoubleArrSeq.CheckedStack)clone).modCount);
        }else{
          Assertions.assertTrue(clone instanceof DoubleArrSeq.UncheckedStack);
        }
        break;
      }
      case FLOAT:{
        Assertions.assertEquals(checkedType.checked,clone instanceof FloatArrSeq.CheckedStack);
        if(checkedType.checked){
          Assertions.assertEquals(0,((FloatArrSeq.CheckedStack)clone).modCount);
        }else{
          Assertions.assertTrue(clone instanceof FloatArrSeq.UncheckedStack);
        }
        break;
      }
      case INT:{
        Assertions.assertEquals(checkedType.checked,clone instanceof IntArrSeq.CheckedStack);
        if(checkedType.checked){
          Assertions.assertEquals(0,((IntArrSeq.CheckedStack)clone).modCount);
        }else{
          Assertions.assertTrue(clone instanceof IntArrSeq.UncheckedStack);
        }
        break;
      }
      case LONG:{
        Assertions.assertEquals(checkedType.checked,clone instanceof LongArrSeq.CheckedStack);
        if(checkedType.checked){
          Assertions.assertEquals(0,((LongArrSeq.CheckedStack)clone).modCount);
        }else{
          Assertions.assertTrue(clone instanceof LongArrSeq.UncheckedStack);
        }
        break;
      }
      case REF:{
        Assertions.assertEquals(checkedType.checked,clone instanceof RefArrSeq.CheckedStack);
        if(checkedType.checked){
          Assertions.assertEquals(0,((RefArrSeq.CheckedStack<?>)clone).modCount);
        }else{
          Assertions.assertTrue(clone instanceof RefArrSeq.UncheckedStack);
        }
        break;
      }
      case SHORT:{
        Assertions.assertEquals(checkedType.checked,clone instanceof ShortArrSeq.CheckedStack);
        if(checkedType.checked){
          Assertions.assertEquals(0,((ShortArrSeq.CheckedStack)clone).modCount);
        }else{
          Assertions.assertTrue(clone instanceof ShortArrSeq.UncheckedStack);
        }
        break;
      }
      default:
        throw dataType.invalid();
      }
    }
    @Override void verifyModCount(){
      int actualModCount;
      switch(dataType){
      case BOOLEAN:
        actualModCount=((BooleanArrSeq.CheckedStack)seq).modCount;
        break;
      case BYTE:
        actualModCount=((ByteArrSeq.CheckedStack)seq).modCount;
        break;
      case CHAR:
        actualModCount=((CharArrSeq.CheckedStack)seq).modCount;
        break;
      case DOUBLE:
        actualModCount=((DoubleArrSeq.CheckedStack)seq).modCount;
        break;
      case FLOAT:
        actualModCount=((FloatArrSeq.CheckedStack)seq).modCount;
        break;
      case INT:
        actualModCount=((IntArrSeq.CheckedStack)seq).modCount;
        break;
      case LONG:
        actualModCount=((LongArrSeq.CheckedStack)seq).modCount;
        break;
      case REF:
        actualModCount=((RefArrSeq.CheckedStack<?>)seq).modCount;
        break;
      case SHORT:
        actualModCount=((ShortArrSeq.CheckedStack)seq).modCount;
        break;
      default:
        throw dataType.invalid();
      }
      Assertions.assertEquals(expectedModCount,actualModCount);
    }
  }
  private static interface BasicTest{
    private void runAllTests(String testName){
      for(final var initParams:ALL_STRUCT_INIT_PARAMS){
        for(final var illegalMod:initParams.structType.validPreMods){
          if(illegalMod.minDepth <= initParams.preAllocs.length
              && (initParams.checkedType.checked || illegalMod.expectedException == null)){
            for(final int size:SIZES){
              for(final int initCap:INIT_CAPACITIES){
                TestExecutorService.submitTest(()->{
                  final var monitor
                      =SequenceInitialization.Ascending.initialize(getMonitoredSequence(initParams,initCap),size,0);
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
        }
      }
      TestExecutorService.completeAllTests(testName);
    }
    void runTest(MonitoredSequence<? extends AbstractSeq<?>> monitor);
  }
  private static interface MonitoredFunctionTest<MONITOR extends MonitoredSequence<? extends AbstractSeq<?>>>{
    @SuppressWarnings("unchecked") private void runAllTests(String testName,long maxRand,boolean useAllStructs){
      for(final var initParams:useAllStructs?ALL_STRUCT_INIT_PARAMS:LIST_STRUCT_INIT_PARAMS){
        for(final var functionGen:initParams.structType.validMonitoredFunctionGens){
          if(initParams.checkedType.checked || functionGen.expectedException == null){
            for(final var size:SIZES){
              final int initValBound=initParams.collectionType == DataType.BOOLEAN && size != 0?1:0;
              for(final var functionCallType:initParams.collectionType.validFunctionCalls){
                for(final var illegalMod:initParams.structType.validPreMods){
                  if(illegalMod.minDepth <= initParams.preAllocs.length
                      && (initParams.checkedType.checked || illegalMod.expectedException == null)){
                    final long randSeedBound=size > 1 && functionGen.randomized && !functionCallType.boxed
                        && illegalMod.expectedException == null?maxRand:0;
                    for(long tmpRandSeed=0;tmpRandSeed <= randSeedBound;++tmpRandSeed){
                      final long randSeed=tmpRandSeed;
                      for(int tmpInitVal=0;tmpInitVal <= initValBound;++tmpInitVal){
                        final int initVal=tmpInitVal;
                        for(final var initCap:INIT_CAPACITIES){
                          TestExecutorService.submitTest(()->{
                            runTest(
                                (MONITOR)SequenceInitialization.Ascending
                                    .initialize(getMonitoredSequence(initParams,initCap),size,initVal),
                                functionGen,functionCallType,illegalMod,randSeed);
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
      TestExecutorService.completeAllTests(testName);
    }
    void runTest(MONITOR monitor,MonitoredFunctionGen functionGen,FunctionCallType functionCallType,
        IllegalModification illegalMod,long randSeed);
  }
  
  private static interface QueryTest<MONITOR extends MonitoredSequence<? extends AbstractSeq<?>>>{
    private void runAllTests(String testName,int structSwitch){
      SequenceInitParams[] initParamArray;
      switch(structSwitch){
      case 0:
        initParamArray=LIST_STRUCT_INIT_PARAMS;
        break;
      case 1:
        initParamArray=STACK_STRUCT_INIT_PARAMS;
        break;
      default:
        initParamArray=ALL_STRUCT_INIT_PARAMS;
      }
      for(final var initParams:initParamArray){
        for(final var queryVal:QueryVal.values()){
          if(initParams.collectionType.isValidQueryVal(queryVal)){
            queryVal.validQueryCombos.forEach((modification,castTypesToInputTypes)->{
              castTypesToInputTypes.forEach((castType,inputTypes)->{
                inputTypes.forEach(inputType->{
                  if(queryVal == QueryVal.NonNull){
                    for(final var monitoredObjectGen:initParams.structType.validMonitoredObjectGens){
                      if(monitoredObjectGen.expectedException != null && initParams.checkedType.checked){
                        for(final var size:SIZES){
                          if(size > 0){
                            for(final var illegalMod:initParams.structType.validPreMods){
                              if(illegalMod.minDepth <= initParams.preAllocs.length
                                  && (initParams.checkedType.checked || illegalMod.expectedException == null)){
                                final Class<? extends Throwable> expectedException=illegalMod.expectedException == null
                                    ?monitoredObjectGen.expectedException:illegalMod.expectedException;
                                TestExecutorService.submitTest(()->{
                                  Assertions.assertThrows(expectedException,()->runTest(initParams,illegalMod,queryVal,
                                      modification,castType,inputType,monitoredObjectGen,size,-1));
                                });
                              }
                            }
                          }
                        }
                      }
                    }
                  }else{
                    final boolean queryCanReturnTrue
                        =queryVal.queryCanReturnTrue(modification,castType,inputType,initParams.collectionType);
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
                            if(position == 0.5d){
                              continue;
                            }
                          default:
                          }
                        }
                        for(final var illegalMod:initParams.structType.validPreMods){
                          if(illegalMod.minDepth <= initParams.preAllocs.length
                              && (initParams.checkedType.checked || illegalMod.expectedException == null)){
                            TestExecutorService.submitTest(()->{
                              if(cmeFilter(illegalMod,inputType,initParams.collectionType,queryVal,modification,
                                  castType)){
                                runTest(initParams,illegalMod,queryVal,modification,castType,inputType,null,size,
                                    position);
                              }else{
                                Assertions.assertThrows(illegalMod.expectedException,()->runTest(initParams,illegalMod,
                                    queryVal,modification,castType,inputType,null,size,position));
                              }
                            });
                          }
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
    @SuppressWarnings("unchecked") private void runTest(SequenceInitParams initParams,IllegalModification illegalMod,
        QueryVal queryVal,QueryVal.QueryValModification modification,QueryCastType castType,DataType inputType,
        MonitoredObjectGen monitoredObjectGen,int seqSize,double position){
      final var monitor=getMonitoredSequence(initParams,seqSize);
      if(position < 0){
        switch(initParams.collectionType){
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
          throw initParams.collectionType.invalid();
        }
      }else{
        switch(initParams.collectionType){
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
          throw initParams.collectionType.invalid();
        }
      }
      monitor.updateCollectionState();
      monitor.illegalMod(illegalMod);
      callAndVerifyResult((MONITOR)monitor,queryVal,inputType,castType,modification,monitoredObjectGen,position,
          seqSize);
    }
    void callAndVerifyResult(MONITOR monitor,QueryVal queryVal,DataType inputType,QueryCastType castType,
        QueryVal.QueryValModification modification,MonitoredObjectGen monitoredObjectGen,double position,int seqSize);
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
  }
  private static interface ToStringAndHashCodeTest{
    private void runAllTests(String testName,SequenceInitParams[] initParamsArr){
      for(final var initParams:initParamsArr){
        if(initParams.collectionType == DataType.REF){
          for(final var objGen:initParams.structType.validMonitoredObjectGens){
            if(objGen.expectedException == null || initParams.checkedType.checked){
              for(final var size:SIZES){
                for(final var illegalMod:initParams.structType.validPreMods){
                  if(illegalMod.minDepth <= initParams.preAllocs.length
                      && (initParams.checkedType.checked || illegalMod.expectedException == null)){
                    TestExecutorService.submitTest(()->{
                      if(size == 0 || objGen.expectedException == null){
                        final var monitor
                            =SequenceInitialization.Ascending.initialize(getMonitoredSequence(initParams,size),size,0);
                        if(illegalMod.expectedException == null){
                          callVerify(monitor);
                        }else{
                          monitor.illegalMod(illegalMod);
                          Assertions.assertThrows(illegalMod.expectedException,()->callVerify(monitor));
                        }
                      }else{
                        final var throwSwitch=new MonitoredObjectGen.ThrowSwitch();
                        final var monitor=SequenceInitialization.Ascending.initializeWithMonitoredObj(
                            getMonitoredSequence(initParams,size),size,0,objGen,throwSwitch);
                        monitor.illegalMod(illegalMod);
                        final Class<? extends Throwable> expectedException
                            =illegalMod.expectedException == null?objGen.expectedException:illegalMod.expectedException;
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
          }
        }else{
          final int initValBound=initParams.collectionType == DataType.BOOLEAN?1:0;
          for(int tmpInitVal=0;tmpInitVal <= initValBound;++tmpInitVal){
            final int initVal=tmpInitVal;
            for(final var size:SIZES){
              for(final var illegalMod:initParams.structType.validPreMods){
                if(illegalMod.minDepth <= initParams.preAllocs.length
                    && (initParams.checkedType.checked || illegalMod.expectedException == null)){
                  TestExecutorService.submitTest(()->{
                    final var monitor=SequenceInitialization.Ascending.initialize(getMonitoredSequence(initParams,size),
                        size,initVal);
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
      }
      TestExecutorService.completeAllTests(testName);
    }
    void callRaw(OmniCollection<?> seq);
    void callVerify(MonitoredSequence<?> monitor);
  }
  private static final int[] INIT_CAPACITIES=new int[]{0,5,10,15};
  private static final double[] POSITIONS=new double[]{-1,0,0.5,1.0};
  private static final double[] RANDOM_THRESHOLDS=new double[]{0.01,0.05,0.10,0.25,0.50,0.75,0.90,0.95,0.99};
  private static final double[] NON_RANDOM_THRESHOLD=new double[]{0.5};
  private static final int[] SIZES=new int[]{0,1,2,3,4,5,10,20,30,40,50,60,70,80,90,100};
  private static final int[] SHORT_SIZES=new int[]{0,100};
  private static final int[] NON_THROWING_REMOVE_AT_POSITIONS=new int[]{-1,0,1,2,3};
  private static final int[] THROWING_REMOVE_AT_POSITIONS=new int[]{-1};
  private static final SequenceInitParams[] ALL_STRUCT_INIT_PARAMS;
  private static final SequenceInitParams[] LIST_STRUCT_INIT_PARAMS;
  private static final SequenceInitParams[] STACK_STRUCT_INIT_PARAMS;
  private static final SequenceInitParams[] ROOT_STRUCT_INIT_PARAMS;
  static{
    final Stream.Builder<SequenceInitParams> allStructBuilder=Stream.builder();
    final Stream.Builder<SequenceInitParams> listStructBuilder=Stream.builder();
    final Stream.Builder<SequenceInitParams> stackStructBuilder=Stream.builder();
    final Stream.Builder<SequenceInitParams> rootStructBuilder=Stream.builder();
    for(final var checkedType:CheckedType.values()){
      for(final var collectionType:DataType.values()){
        final var arrListParams=new SequenceInitParams(StructType.ArrList,collectionType,checkedType,
            OmniArray.OfInt.DEFAULT_ARR,OmniArray.OfInt.DEFAULT_ARR);
        final var arrStackParams=new SequenceInitParams(StructType.ArrStack,collectionType,checkedType,
            OmniArray.OfInt.DEFAULT_ARR,OmniArray.OfInt.DEFAULT_ARR);
        allStructBuilder.accept(arrListParams);
        allStructBuilder.accept(arrStackParams);
        stackStructBuilder.accept(arrStackParams);
        listStructBuilder.accept(arrListParams);
        rootStructBuilder.accept(arrListParams);
        rootStructBuilder.accept(arrStackParams);
      }
    }
    for(int pre0=0;pre0 <= 5;pre0+=5){
      for(int pre1=0;pre1 <= 5;pre1+=5){
        final var preAllocs=new int[]{pre0,pre1};
        for(int post0=0;post0 <= 5;post0+=5){
          for(int post1=0;post1 <= 5;post1+=5){
            final var postAllocs=new int[]{post0,post1};
            for(final var checkedType:CheckedType.values()){
              for(final var collectionType:DataType.values()){
                final var subListParams
                    =new SequenceInitParams(StructType.ArrSubList,collectionType,checkedType,preAllocs,postAllocs);
                listStructBuilder.accept(subListParams);
                allStructBuilder.accept(subListParams);
              }
            }
          }
        }
      }
    }
    ALL_STRUCT_INIT_PARAMS=allStructBuilder.build().toArray(SequenceInitParams[]::new);
    LIST_STRUCT_INIT_PARAMS=listStructBuilder.build().toArray(SequenceInitParams[]::new);
    STACK_STRUCT_INIT_PARAMS=stackStructBuilder.build().toArray(SequenceInitParams[]::new);
    ROOT_STRUCT_INIT_PARAMS=rootStructBuilder.build().toArray(SequenceInitParams[]::new);
  }
  private static int getInitCapacity(int initCapacity,int[] preAllocs,int[] postAllocs){
    for(int i=preAllocs.length;--i >= 0;){
      initCapacity+=preAllocs[i];
    }
    for(int i=postAllocs.length;--i >= 0;){
      initCapacity+=postAllocs[i];
    }
    return initCapacity;
  }
  private static MonitoredList<? extends AbstractSeq<?>> getMonitoredList(SequenceInitParams initParams,
      int initCapacity){
    switch(initParams.structType){
    case ArrList:
    case ArrSubList:{
      final var rootMonitor
          =new ArrListMonitor<>(initParams,getInitCapacity(initCapacity,initParams.preAllocs,initParams.postAllocs));
      if(initParams.structType != StructType.ArrSubList){ return rootMonitor; }
      final var preAllocs=initParams.preAllocs;
      final var postAllocs=initParams.postAllocs;
      int totalPreAlloc=preAllocs[0];
      int totalPostAlloc=postAllocs[0];
      SequenceInitialization.Ascending.initialize(rootMonitor,totalPreAlloc,Integer.MIN_VALUE);
      SequenceInitialization.Ascending.initialize(rootMonitor,totalPostAlloc,Integer.MAX_VALUE - totalPostAlloc);
      var subListMonitor=new ArrListMonitor.ArrSubListMonitor<>(rootMonitor,totalPreAlloc,totalPreAlloc);
      for(int i=1;i < preAllocs.length;++i){
        int postAlloc;
        int preAlloc;
        totalPostAlloc+=postAlloc=postAllocs[i];
        SequenceInitialization.Ascending.initialize(subListMonitor,preAlloc=preAllocs[i],
            Integer.MIN_VALUE + totalPreAlloc);
        SequenceInitialization.Ascending.initialize(subListMonitor,postAlloc,Integer.MAX_VALUE - totalPostAlloc);
        totalPreAlloc+=preAlloc;
        subListMonitor=new ArrListMonitor.ArrSubListMonitor<>(subListMonitor,preAlloc,preAlloc);
      }
      return subListMonitor;
    }
    default:
      throw initParams.structType.invalid();
    }
  }
  private static MonitoredSequence<? extends AbstractSeq<?>> getMonitoredSequence(SequenceInitParams initParams,
      int initCapacity){
    if(initParams.structType == StructType.ArrStack){
      return new ArrStackMonitor<>(initParams,initCapacity);
    }else{
      return getMonitoredList(initParams,initCapacity);
    }
  }
  private static ArrStackMonitor<?,?> getMonitoredStack(SequenceInitParams initParams,int initCapacity){
    return new ArrStackMonitor<>(initParams,initCapacity);
  }
   @Test public void testadd_intval(){
    for(final var initParams:LIST_STRUCT_INIT_PARAMS){
      for(final var illegalMod:initParams.structType.validPreMods){
        if(illegalMod.minDepth <= initParams.preAllocs.length
            && (initParams.checkedType.checked || illegalMod.expectedException == null)){
          for(final var inputType:initParams.collectionType.mayBeAddedTo()){
            for(final var functionCallType:inputType.validFunctionCalls){
              for(final var initCap:INIT_CAPACITIES){
                TestExecutorService.submitTest(()->{
                  if(illegalMod.expectedException == null){
                    for(final var position:POSITIONS){
                      if(position >= 0 || initParams.checkedType.checked){
                        final var monitor=getMonitoredList(initParams,initCap);
                        if(position < 0){
                          for(int i=0;i < 258;++i){
                            final Object inputVal=inputType.convertValUnchecked(i);
                            final int finalI=i;
                            Assertions.assertThrows(IndexOutOfBoundsException.class,
                                ()->monitor.verifyAdd(-1,inputVal,inputType,functionCallType));
                            Assertions.assertThrows(IndexOutOfBoundsException.class,
                                ()->monitor.verifyAdd(finalI + 1,inputVal,inputType,functionCallType));
                            monitor.add(i);
                          }
                        }else{
                          for(int i=0;i < 258;++i){
                            monitor.verifyAdd((int)(i * position),inputType.convertValUnchecked(i),inputType,
                                functionCallType);
                          }
                        }
                      }
                    }
                  }else{
                    {
                      final var monitor
                          =SequenceInitialization.Ascending.initialize(getMonitoredList(initParams,initCap),10,0);
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
                      final var monitor=getMonitoredList(initParams,initCap);
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
    TestExecutorService.completeAllTests("ArrSeqTest.testadd_intval");
  }
   @Test public void testadd_val(){
    for(final var initParams:ALL_STRUCT_INIT_PARAMS){
      for(final var illegalMod:initParams.structType.validPreMods){
        if(illegalMod.minDepth <= initParams.preAllocs.length
            && (initParams.checkedType.checked || illegalMod.expectedException == null)){
          for(final var inputType:initParams.collectionType.mayBeAddedTo()){
            for(final var functionCallType:inputType.validFunctionCalls){
              for(final var initCap:INIT_CAPACITIES){
                TestExecutorService.submitTest(()->{
                  if(illegalMod.expectedException == null){
                    final var monitor=getMonitoredSequence(initParams,initCap);
                    for(int i=0;i < 100;++i){
                      monitor.verifyAdd(inputType.convertValUnchecked(i),inputType,functionCallType);
                    }
                  }else{
                    {
                      final var monitor=getMonitoredSequence(initParams,initCap);
                      monitor.illegalMod(illegalMod);
                      Assertions.assertThrows(illegalMod.expectedException,
                          ()->monitor.verifyAdd(inputType.convertValUnchecked(0),inputType,functionCallType));
                    }
                    {
                      final var monitor
                          =SequenceInitialization.Ascending.initialize(getMonitoredSequence(initParams,initCap),10,0);
                      monitor.illegalMod(illegalMod);
                      Assertions.assertThrows(illegalMod.expectedException,
                          ()->monitor.verifyAdd(inputType.convertValUnchecked(0),inputType,functionCallType));
                    }
                  }
                });
              }
            }
          }
        }
      }
    }
    TestExecutorService.completeAllTests("ArrSeqTest.testadd_val");
  }
   @Test public void testclear_void(){
    final BasicTest test=MonitoredSequence::verifyClear;
    test.runAllTests("ArrSeqTest.testclear_void");
  }
   @Test public void testclone_void(){
    final BasicTest test=MonitoredSequence::verifyClone;
    test.runAllTests("ArrSeqTest.testclone_void");
  }
   @Test public void testConstructor_int(){
    for(final var initParams:ROOT_STRUCT_INIT_PARAMS){
      for(final var initCap:INIT_CAPACITIES){
        TestExecutorService.submitTest(()->{
          AbstractArrSeqMonitor<?> monitor;
          switch(initParams.structType){
          case ArrList:
            monitor=new ArrListMonitor<>(initParams,initCap);
            break;
          case ArrStack:
            monitor=new ArrStackMonitor<>(initParams,initCap);
            break;
          default:
            throw initParams.structType.invalid();
          }
          switch(initCap){
          case 0:
            Assertions.assertNull(monitor.expectedArr);
            break;
          case OmniArray.DEFAULT_ARR_SEQ_CAP:
            Object expectedArr;
            switch(initParams.collectionType){
            case BOOLEAN:
              expectedArr=OmniArray.OfBoolean.DEFAULT_ARR;
              break;
            case BYTE:
              expectedArr=OmniArray.OfByte.DEFAULT_ARR;
              break;
            case CHAR:
              expectedArr=OmniArray.OfChar.DEFAULT_ARR;
              break;
            case SHORT:
              expectedArr=OmniArray.OfShort.DEFAULT_ARR;
              break;
            case INT:
              expectedArr=OmniArray.OfInt.DEFAULT_ARR;
              break;
            case LONG:
              expectedArr=OmniArray.OfLong.DEFAULT_ARR;
              break;
            case FLOAT:
              expectedArr=OmniArray.OfFloat.DEFAULT_ARR;
              break;
            case DOUBLE:
              expectedArr=OmniArray.OfDouble.DEFAULT_ARR;
              break;
            case REF:
              expectedArr=OmniArray.OfRef.DEFAULT_ARR;
              break;
            default:
              throw initParams.collectionType.invalid();
            }
            Assertions.assertSame(expectedArr,monitor.expectedArr);
            break;
          default:
            Assertions.assertEquals(initCap,monitor.expectedCapacity);
          }
          monitor.verifyCollectionState();
        });
      }
    }
    TestExecutorService.completeAllTests("ArrSeqTest.testConstructor_int");
  }
   @Test public void testConstructor_void(){
    for(final var initParams:ROOT_STRUCT_INIT_PARAMS){
      TestExecutorService.submitTest(()->{
        MonitoredSequence<?> monitor;
        switch(initParams.structType){
        case ArrList:
          monitor=new ArrListMonitor<>(initParams);
          break;
        case ArrStack:
          monitor=new ArrStackMonitor<>(initParams);
          break;
        default:
          throw initParams.structType.invalid();
        }
        monitor.verifyCollectionState();
      });
    }
    TestExecutorService.completeAllTests("ArrSeqTest.testConstructor_void");
  }
   @Test public void testcontains_val(){
    final QueryTest<?> test=(monitor,queryVal,inputType,castType,modification,monitoredObjectGen,position,seqSize)->{
      if(monitoredObjectGen == null){
        Assertions.assertEquals(position >= 0,monitor.verifyContains(queryVal,inputType,castType,modification));
      }else{
        monitor.verifyThrowingContains(monitoredObjectGen);
      }
    };
    test.runAllTests("ArrSeqTest.testcontains_val",2);
  }
   @Test public void testequals_Object(){
    for(final var initParams:LIST_STRUCT_INIT_PARAMS){
      TestExecutorService
          .submitTest(()->{
              try {
                Assertions.assertFalse(getMonitoredSequence(initParams,0).getCollection().equals(null)); 
              }catch(NotYetImplementedException e) {
                  //do nothing
              }
          });
    }
    TestExecutorService.completeAllTests("ArrSeqTest.testequals_Object");
  }
   @Test public void testforEach_Consumer(){
      for(var size:SIZES) {
          for(final var initParams:ALL_STRUCT_INIT_PARAMS){
              final int initValBound=initParams.collectionType == DataType.BOOLEAN && size != 0?1:0;
              for(int tmpInitVal=0;tmpInitVal<=initValBound;++tmpInitVal) {
                  final int initVal=tmpInitVal;
                  TestExecutorService.submitTest(()->{
                      final var seqMonitor=SequenceInitialization.Ascending.initialize(getMonitoredSequence(initParams,size),size,initVal) ;
                      for(final var functionGen:initParams.structType.validMonitoredFunctionGens){
                          if((functionGen.expectedException==null || size>0) && (initParams.checkedType.checked || functionGen.expectedException == null)){
                              for(final var functionCallType:initParams.collectionType.validFunctionCalls){
                                  for(final var illegalMod:initParams.structType.validPreMods){
                                      if(illegalMod.minDepth <= initParams.preAllocs.length&& (initParams.checkedType.checked || illegalMod.expectedException == null)){
                                          final long randSeedBound=size > 1 && functionGen.randomized && !functionCallType.boxed&& illegalMod.expectedException == null?100:0;
                                          for(long randSeed=0;randSeed <= randSeedBound;++randSeed){
                                              if(illegalMod.expectedException==null) {
                                                  if(functionGen.expectedException==null) {
                                                      seqMonitor.verifyForEach(functionGen,functionCallType,randSeed);
                                                  }else {
                                                      final long finalRandSeed=randSeed;
                                                      Assertions.assertThrows(functionGen.expectedException, ()->seqMonitor.verifyForEach(functionGen,functionCallType,finalRandSeed));
                                                      seqMonitor.repairModCount();
                                                  }
                                              }else {
                                                  seqMonitor.illegalMod(illegalMod);
                                                  final long finalRandSeed=randSeed;
                                                  Assertions.assertThrows(illegalMod.expectedException, ()->seqMonitor.verifyForEach(functionGen,functionCallType,finalRandSeed));
                                                  seqMonitor.repairModCount();
                                              }
                                          }
                                      }
                                  }
                              }
                          }
                      }
                  });
              }
          }
      }
      TestExecutorService.completeAllTests("ArrSeqTest.testforEach_Consumer");
  }
   @Test public void testget_int(){
    for(final var initParams:LIST_STRUCT_INIT_PARAMS){
      for(final var illegalMod:initParams.structType.validPreMods){
        if(illegalMod.minDepth <= initParams.preAllocs.length
            && (initParams.checkedType.checked || illegalMod.expectedException == null)){
          for(final var size:SHORT_SIZES){
            TestExecutorService.submitTest(()->{
              final var monitor=SequenceInitialization.Ascending.initialize(getMonitoredList(initParams,size),size,0);
              monitor.illegalMod(illegalMod);
              if(illegalMod.expectedException == null){
                for(final var outputType:initParams.collectionType.validOutputTypes()){
                  if(initParams.checkedType.checked){
                    Assertions.assertThrows(IndexOutOfBoundsException.class,()->monitor.verifyGet(-1,outputType));
                    Assertions.assertThrows(IndexOutOfBoundsException.class,()->monitor.verifyGet(size,outputType));
                  }
                  for(int index=0;index < size;++index){
                    monitor.verifyGet(index,outputType);
                  }
                }
              }else{
                for(final var outputType:initParams.collectionType.validOutputTypes()){
                  for(int tmpIndex=-1;tmpIndex <= size;++tmpIndex){
                    final int index=tmpIndex;
                    Assertions.assertThrows(illegalMod.expectedException,()->monitor.verifyGet(index,outputType));
                  }
                }
              }
            });
          }
        }
      }
    }
    TestExecutorService.completeAllTests("ArrSeqTest.testget_int");
  }
   @Test public void testhashCode_void(){
    final ToStringAndHashCodeTest test=new ToStringAndHashCodeTest(){
      @Override public void callRaw(OmniCollection<?> seq){
        seq.hashCode();
      }
      @Override public void callVerify(MonitoredSequence<?> monitor){
        monitor.verifyHashCode();
      }
    };
    test.runAllTests("ArrSeqTest.testhashCode_void",LIST_STRUCT_INIT_PARAMS);
  }
   @Test public void testindexOf_val(){
    final QueryTest<MonitoredList<? extends AbstractSeq<?>>> test
        =(monitor,queryVal,inputType,castType,modification,monitoredObjectGen,position,seqSize)->{
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
    test.runAllTests("ArrSeqTest.testindexOf_val",0);
  }
   @Test public void testisEmpty_void(){
    final BasicTest test=MonitoredSequence::verifyIsEmpty;
    test.runAllTests("ArrSeqTest.testisEmpty_void");
  }
   @Test public void testiterator_void(){
    for(final var initParams:ALL_STRUCT_INIT_PARAMS){
      for(final var illegalMod:initParams.structType.validPreMods){
        if(illegalMod.minDepth <= initParams.preAllocs.length
            && (initParams.checkedType.checked || illegalMod.expectedException == null)){
          for(final var size:SIZES){
            TestExecutorService.submitTest(()->{
              final var monitor
                  =SequenceInitialization.Ascending.initialize(getMonitoredSequence(initParams,size),size,0);
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
    }
    TestExecutorService.completeAllTests("ArrSeqTest.testiterator_void");
  }
   @Test public void testItrclone_void(){
    for(final var size:SIZES){
      int prevIndex=-1;
      for(final var position:POSITIONS){
        int index;
        if(position >= 0 && (index=(int)(position * size)) != prevIndex){
          prevIndex=index;
          for(final var initParams:ALL_STRUCT_INIT_PARAMS){
            for(final var itrType:initParams.structType.validItrTypes){
              TestExecutorService.submitTest(()->{
                final var seqMonitor
                    =SequenceInitialization.Ascending.initialize(getMonitoredSequence(initParams,size),size,0);
                final var itrMonitor=seqMonitor.getMonitoredIterator(index,itrType);
                itrMonitor.verifyClone();
              });
            }
          }
        }
      }
    }
    TestExecutorService.completeAllTests("ArrSeqTest.testItrclone_void");
  }
   @Test public void testItrforEachRemaining_Consumer(){
      for(final int size:SIZES) {
          for(var initParams:ALL_STRUCT_INIT_PARAMS) {
              TestExecutorService.submitTest(()->{
                  final var seqMonitor=SequenceInitialization.Ascending.initialize(getMonitoredSequence(initParams,size),size,0);
                  int prevNumToIterate=-1;
                  for(var position:POSITIONS) {
                      int numToIterate;
                      if(position >= 0 && (numToIterate=(int)(position * size)) != prevNumToIterate){
                          prevNumToIterate=numToIterate;
                          final int numLeft=size - numToIterate;
                          for(final var functionCallType:initParams.collectionType.validFunctionCalls){
                              for(final var itrType:initParams.structType.validItrTypes){
                                  for(final var illegalMod:itrType.validPreMods){
                                      if((illegalMod.expectedException==null || numLeft>0) && (illegalMod.expectedException == null || initParams.checkedType.checked)){
                                          for(final var functionGen:itrType.validMonitoredFunctionGens){
                                              if((functionGen.expectedException==null || numLeft>0) && (initParams.checkedType.checked || size == 0 || functionGen.expectedException == null)){
                                                  final long randSeedBound=!functionCallType.boxed && numLeft > 1 && functionGen.randomized&& illegalMod.expectedException == null?100:0;
                                                  for(long randSeed=0;randSeed <= randSeedBound;++randSeed){
                                                      final var itrMonitor=seqMonitor.getMonitoredIterator(numToIterate,itrType);
                                                      if(illegalMod.expectedException==null ) {
                                                          if(functionGen.expectedException==null) {
                                                              itrMonitor.verifyForEachRemaining(functionGen,functionCallType,randSeed);
                                                          }else {
                                                              final long finalRandSeed=randSeed;
                                                              Assertions.assertThrows(functionGen.expectedException,()->itrMonitor.verifyForEachRemaining(functionGen,functionCallType,finalRandSeed));
                                                              seqMonitor.repairModCount();
                                                          }
                                                      }else {
                                                          itrMonitor.illegalMod(illegalMod);
                                                          final long finalRandSeed=randSeed;
                                                          Assertions.assertThrows(illegalMod.expectedException,()->itrMonitor.verifyForEachRemaining(functionGen,functionCallType,finalRandSeed));
                                                          seqMonitor.repairModCount();
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
              });
          }
      }
    TestExecutorService.completeAllTests("ArrSeqTest.testItrforEachRemaining_Consumer");
  }
   @Test public void testItrhasNext_void(){
    for(final var initParams:ALL_STRUCT_INIT_PARAMS){
        for(final int size:SHORT_SIZES){
            TestExecutorService.submitTest(()->{
                final var seqMonitor=SequenceInitialization.Ascending.initialize(getMonitoredSequence(initParams,size),size,0);
                for(final var itrType:initParams.structType.validItrTypes){
                    final var itrMonitor=seqMonitor.getMonitoredIterator(itrType);
                    while(itrMonitor.verifyHasNext()){
                      itrMonitor.iterateForward();
                    }
                }
            });
        }
    }
    TestExecutorService.completeAllTests("ArrSeqTest.testItrhasNext_void");
  }
   @Test public void testItrnext_void(){
    for(final var initParams:ALL_STRUCT_INIT_PARAMS){
      for(final var itrType:initParams.structType.validItrTypes){
        for(final var illegalMod:itrType.validPreMods){
          if(illegalMod.expectedException == null || initParams.checkedType.checked){
            for(final var outputType:initParams.collectionType.validOutputTypes()){
              for(final var size:SHORT_SIZES){
                if(size > 0 || initParams.checkedType.checked){
                  if(illegalMod.expectedException == null){
                    TestExecutorService.submitTest(()->{
                      final var seqMonitor
                          =SequenceInitialization.Ascending.initialize(getMonitoredSequence(initParams,size),size,0);
                      final var itrMonitor=seqMonitor.getMonitoredIterator(itrType);
                      while(itrMonitor.hasNext()){
                        itrMonitor.verifyNext(outputType);
                      }
                      if(initParams.checkedType.checked){
                        Assertions.assertThrows(NoSuchElementException.class,()->itrMonitor.verifyNext(outputType));
                      }
                    });
                  }else{
                    for(final var position:POSITIONS){
                      if(position >= 0){
                        TestExecutorService.submitTest(()->{
                          final var seqMonitor=SequenceInitialization.Ascending
                              .initialize(getMonitoredSequence(initParams,size),size,0);
                          final int index=(int)(size * position);
                          final var itrMonitor=seqMonitor.getMonitoredIterator(index,itrType);
                          itrMonitor.illegalMod(illegalMod);
                          Assertions.assertThrows(illegalMod.expectedException,()->itrMonitor.verifyNext(outputType));
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
    TestExecutorService.completeAllTests("ArrSeqTest.testItrnext_void");
  }
   @Test public void testItrremove_void(){
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
                        if(itrType.iteratorInterface != OmniListIterator.class && (size == 0 || numToIterate == size)){
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
                        final var seqMonitor
                            =SequenceInitialization.Ascending.initialize(getMonitoredSequence(initParams,size),size,0);
                        final var itrMonitor=seqMonitor.getMonitoredIterator(numToIterate,itrType);
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
                                Assertions.assertEquals(numToIterate < 2,seqMonitor.isEmpty());
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
                            Assertions.assertThrows(illegalMod.expectedException,()->itrMonitor.verifyRemove());
                          }
                        }else{
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
    TestExecutorService.completeAllTests("ArrSeqTest.testItrremove_void");
  }
   @Test public void testlastIndexOf_val(){
    final QueryTest<MonitoredList<? extends AbstractSeq<?>>> test
        =(monitor,queryVal,inputType,castType,modification,monitoredObjectGen,position,seqSize)->{
          if(monitoredObjectGen == null){
            int expectedIndex;
            if(position >= 0){
              expectedIndex=(int)Math.round(position * seqSize);
            }else{
              expectedIndex=-1;
            }
            Assertions.assertEquals(expectedIndex,monitor.verifyLastIndexOf(queryVal,inputType,castType,modification));
          }else{
            monitor.verifyThrowingLastIndexOf(monitoredObjectGen);
          }
        };
    test.runAllTests("ArrSeqTest.testlastIndexOf_val",0);
  }
   @Test public void testlistIterator_int(){
    for(final var initParams:LIST_STRUCT_INIT_PARAMS){
      for(final var size:SIZES){
        final int inc=Math.max(1,size / 10);
        if(initParams.checkedType.checked || size > 0){
          for(final var illegalMod:initParams.structType.validPreMods){
            if(illegalMod.minDepth <= initParams.preAllocs.length
                && (initParams.checkedType.checked || illegalMod.expectedException == null)){
              TestExecutorService.submitTest(()->{
                final var monitor=SequenceInitialization.Ascending.initialize(getMonitoredList(initParams,size),size,0);
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
    }
    TestExecutorService.completeAllTests("ArrSeqTest.testlistIterator_int");
  }
   @Test public void testlistIterator_void(){
    for(final var initParams:LIST_STRUCT_INIT_PARAMS){
      for(final var illegalMod:initParams.structType.validPreMods){
        if(illegalMod.minDepth <= initParams.preAllocs.length
            && (initParams.checkedType.checked || illegalMod.expectedException == null)){
          for(final var size:SHORT_SIZES){
            TestExecutorService.submitTest(()->{
              final var monitor=SequenceInitialization.Ascending.initialize(getMonitoredList(initParams,size),size,0);
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
    }
    TestExecutorService.completeAllTests("ArrSeqTest.testlistIterator_void");
  }
   @Test public void testListItradd_val(){
    for(final var position:POSITIONS){
      if(position >= 0){
        for(final var initParams:LIST_STRUCT_INIT_PARAMS){
          final var itrType=initParams.structType == StructType.ArrList?IteratorType.BidirectionalItr
              :IteratorType.SubBidirectionalItr;
          for(final var illegalMod:itrType.validPreMods){
            if(illegalMod.expectedException == null || initParams.checkedType.checked){
              for(final var inputType:initParams.collectionType.mayBeAddedTo()){
                for(final var functionCallType:inputType.validFunctionCalls){
                  for(final var initCapacity:INIT_CAPACITIES){
                    TestExecutorService.submitTest(()->{
                      final var seqMonitor=getMonitoredList(initParams,initCapacity);
                      final var itrMonitor=seqMonitor.getMonitoredListIterator();
                      for(int i=0;;){
                        itrMonitor.verifyAdd(inputType.convertValUnchecked(i),inputType,functionCallType);
                        if(++i == 100){
                          break;
                        }
                        final double dI=i;
                        double currPosition;
                        while((currPosition=itrMonitor.nextIndex() / dI) < position && itrMonitor.hasNext()){
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
                            ()->itrMonitor.verifyAdd(inputType.convertValUnchecked(100),inputType,functionCallType));
                        // for good measure, do it again with an
                        // empty list
                        final var itrMonitor2=getMonitoredList(initParams,initCapacity).getMonitoredListIterator();
                        itrMonitor2.illegalMod(illegalMod);
                        Assertions.assertThrows(illegalMod.expectedException,
                            ()->itrMonitor2.verifyAdd(inputType.convertValUnchecked(100),inputType,functionCallType));
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
    TestExecutorService.completeAllTests("ArrSeqTest.testListItradd_val");
  }
   @Test public void testListItrhasPrevious_void(){
    for(final var initParams:LIST_STRUCT_INIT_PARAMS){
      for(final var size:SHORT_SIZES){
        TestExecutorService.submitTest(()->{
          final var seqMonitor=SequenceInitialization.Ascending.initialize(getMonitoredList(initParams,size),size,0);
          final var itrMonitor=seqMonitor.getMonitoredListIterator(size);
          while(itrMonitor.verifyHasPrevious()){
            itrMonitor.iterateReverse();
          }
        });
      }
    }
    TestExecutorService.completeAllTests("ArrSeqTest.testListItrhasPrevious_void");
  }
   @Test public void testListItrnextIndex_void(){
    for(final var initParams:LIST_STRUCT_INIT_PARAMS){
      for(final var size:SHORT_SIZES){
        TestExecutorService.submitTest(()->{
          final var seqMonitor=SequenceInitialization.Ascending.initialize(getMonitoredList(initParams,size),size,0);
          final var itrMonitor=seqMonitor.getMonitoredListIterator();
          while(itrMonitor.verifyNextIndex() < size){
            itrMonitor.iterateForward();
          }
        });
      }
    }
    TestExecutorService.completeAllTests("ArrSeqTest.testListItrnextIndex_void");
  }
   @Test public void testListItrprevious_void(){
    for(final var initParams:LIST_STRUCT_INIT_PARAMS){
      for(final var size:SHORT_SIZES){
        if(size > 0 || initParams.checkedType.checked){
          final var itrType=initParams.structType == StructType.ArrList?IteratorType.BidirectionalItr
              :IteratorType.SubBidirectionalItr;
          for(final var illegalMod:itrType.validPreMods){
            if(illegalMod.expectedException == null || initParams.checkedType.checked){
              for(final var outputType:initParams.collectionType.validOutputTypes()){
                if(illegalMod.expectedException == null){
                  final var seqMonitor
                      =SequenceInitialization.Ascending.initialize(getMonitoredList(initParams,size),size,0);
                  final var itrMonitor=seqMonitor.getMonitoredListIterator(size);
                  itrMonitor.illegalMod(illegalMod);
                  while(itrMonitor.hasPrevious()){
                    itrMonitor.verifyPrevious(outputType);
                  }
                  if(initParams.checkedType.checked){
                    Assertions.assertThrows(NoSuchElementException.class,()->itrMonitor.verifyPrevious(outputType));
                  }
                }else{
                  for(final var position:POSITIONS){
                    if(position >= 0){
                      TestExecutorService.submitTest(()->{
                        final var seqMonitor
                            =SequenceInitialization.Ascending.initialize(getMonitoredList(initParams,size),size,0);
                        final int index=(int)(size * position);
                        final var itrMonitor=seqMonitor.getMonitoredListIterator(index);
                        itrMonitor.illegalMod(illegalMod);
                        Assertions.assertThrows(illegalMod.expectedException,()->itrMonitor.verifyPrevious(outputType));
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
    TestExecutorService.completeAllTests("ArrSeqTest.testListItrprevious_void");
  }
   @Test public void testListItrpreviousIndex_void(){
    for(final var initParams:LIST_STRUCT_INIT_PARAMS){
      for(final var size:SHORT_SIZES){
        TestExecutorService.submitTest(()->{
          final var seqMonitor=SequenceInitialization.Ascending.initialize(getMonitoredList(initParams,size),size,0);
          final var itrMonitor=seqMonitor.getMonitoredListIterator(size);
          while(itrMonitor.verifyPreviousIndex() > 0){
            itrMonitor.iterateReverse();
          }
        });
      }
    }
    TestExecutorService.completeAllTests("ArrSeqTest.testListItrhasPrevious_void");
  }
   @Test public void testListItrset_val(){
    for(final var initParams:LIST_STRUCT_INIT_PARAMS){
      final var itrType
          =initParams.structType == StructType.ArrList?IteratorType.BidirectionalItr:IteratorType.SubBidirectionalItr;
      for(final var illegalMod:itrType.validPreMods){
        if(illegalMod.expectedException == null || initParams.checkedType.checked){
          for(final var removeScenario:itrType.validItrRemoveScenarios){
            if(removeScenario.expectedException == null || initParams.checkedType.checked){
              for(final var inputType:initParams.collectionType.mayBeAddedTo()){
                for(final var functionCallType:inputType.validFunctionCalls){
                  for(final int size:SHORT_SIZES){
                    if(removeScenario.expectedException == null && illegalMod.expectedException == null){
                      TestExecutorService.submitTest(()->{
                        final var seqMonitor
                            =SequenceInitialization.Ascending.initialize(getMonitoredList(initParams,size),size,0);
                        final var itrMonitor=seqMonitor.getMonitoredListIterator();
                        removeScenario.initialize(itrMonitor);
                        int i=1;
                        itrMonitor.verifySet(inputType.convertValUnchecked(i),inputType,functionCallType);
                        while(itrMonitor.hasNext()){
                          itrMonitor.iterateForward();
                          itrMonitor.verifySet(inputType.convertValUnchecked(++i),inputType,functionCallType);
                        }
                      });
                    }else{
                      for(final var position:POSITIONS){
                        if(position >= 0){
                          TestExecutorService.submitTest(()->{
                            final var seqMonitor
                                =SequenceInitialization.Ascending.initialize(getMonitoredList(initParams,size),size,0);
                            final int index=(int)(size * position);
                            final var itrMonitor=seqMonitor.getMonitoredListIterator(index);
                            removeScenario.initialize(itrMonitor);
                            itrMonitor.illegalMod(illegalMod);
                            if(removeScenario.expectedException == null){
                              Assertions.assertThrows(illegalMod.expectedException,()->itrMonitor
                                  .verifySet(inputType.convertValUnchecked(1),inputType,functionCallType));
                            }else{
                              Assertions.assertThrows(removeScenario.expectedException,()->itrMonitor
                                  .verifySet(inputType.convertValUnchecked(1),inputType,functionCallType));
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
    TestExecutorService.completeAllTests("ArrSeqTest.testListItrset_val");
  }
   @Test public void testMASSIVEtoString(){
    for(final var collectionType:DataType.values()){
      int seqSize;
      if((seqSize=collectionType.massiveToStringThreshold + 1) == 0){
        continue;
      }
      OmniList<?> uncheckedList;
      OmniStack<?> uncheckedStack;
      OmniList<?> checkedList;
      OmniStack<?> checkedStack;
      switch(collectionType){
      case BOOLEAN:{
        boolean[] arr;
        uncheckedList=new BooleanArrSeq.UncheckedList(seqSize,arr=new boolean[seqSize]);
        uncheckedStack=new BooleanArrSeq.UncheckedStack(seqSize,arr);
        checkedList=new BooleanArrSeq.CheckedList(seqSize,arr);
        checkedStack=new BooleanArrSeq.CheckedStack(seqSize,arr);
        for(int i=0;i < seqSize;++i){
          arr[i]=true;
        }
        break;
      }
      case BYTE:{
        byte[] arr;
        uncheckedList=new ByteArrSeq.UncheckedList(seqSize,arr=new byte[seqSize]);
        uncheckedStack=new ByteArrSeq.UncheckedStack(seqSize,arr);
        checkedList=new ByteArrSeq.CheckedList(seqSize,arr);
        checkedStack=new ByteArrSeq.CheckedStack(seqSize,arr);
        break;
      }
      case SHORT:{
        short[] arr;
        uncheckedList=new ShortArrSeq.UncheckedList(seqSize,arr=new short[seqSize]);
        uncheckedStack=new ShortArrSeq.UncheckedStack(seqSize,arr);
        checkedList=new ShortArrSeq.CheckedList(seqSize,arr);
        checkedStack=new ShortArrSeq.CheckedStack(seqSize,arr);
        break;
      }
      case INT:{
        int[] arr;
        uncheckedList=new IntArrSeq.UncheckedList(seqSize,arr=new int[seqSize]);
        uncheckedStack=new IntArrSeq.UncheckedStack(seqSize,arr);
        checkedList=new IntArrSeq.CheckedList(seqSize,arr);
        checkedStack=new IntArrSeq.CheckedStack(seqSize,arr);
        break;
      }
      case LONG:{
        long[] arr;
        uncheckedList=new LongArrSeq.UncheckedList(seqSize,arr=new long[seqSize]);
        uncheckedStack=new LongArrSeq.UncheckedStack(seqSize,arr);
        checkedList=new LongArrSeq.CheckedList(seqSize,arr);
        checkedStack=new LongArrSeq.CheckedStack(seqSize,arr);
        break;
      }
      case FLOAT:{
        float[] arr;
        uncheckedList=new FloatArrSeq.UncheckedList(seqSize,arr=new float[seqSize]);
        uncheckedStack=new FloatArrSeq.UncheckedStack(seqSize,arr);
        checkedList=new FloatArrSeq.CheckedList(seqSize,arr);
        checkedStack=new FloatArrSeq.CheckedStack(seqSize,arr);
        break;
      }
      default:
        throw collectionType.invalid();
      }
      collectionType.verifyMASSIVEToString(uncheckedList.toString(),seqSize,
          collectionType.classPrefix + "ArrSeqTest.UncheckedList.testMASSIVEtoString");
      collectionType.verifyMASSIVEToString(uncheckedList.subList(0,seqSize).toString(),seqSize,
          collectionType.classPrefix + "ArrSeqTest.UncheckedSubList.testMASSIVEtoString");
      collectionType.verifyMASSIVEToString(checkedList.toString(),seqSize,
          collectionType.classPrefix + "ArrSeqTest.CheckedList.testMASSIVEtoString");
      collectionType.verifyMASSIVEToString(checkedList.subList(0,seqSize).toString(),seqSize,
          collectionType.classPrefix + "ArrSeqTest.CheckedSubList.testMASSIVEtoString");
      collectionType.verifyMASSIVEToString(uncheckedStack.toString(),seqSize,
          collectionType.classPrefix + "ArrSeqTest.UncheckedStack.testMASSIVEtoString");
      collectionType.verifyMASSIVEToString(checkedStack.toString(),seqSize,
          collectionType.classPrefix + "ArrSeqTest.CheckedStack.testMASSIVEtoString");
    }
  }
   @Test public void testpeek_void(){
    for(final var initParams:STACK_STRUCT_INIT_PARAMS){
      TestExecutorService.submitTest(()->{
        final var monitor=getMonitoredStack(initParams,100);
        for(int i=0;;++i){
          for(final var outputType:initParams.collectionType.validOutputTypes()){
            monitor.verifyPeek(outputType);
          }
          if(i == 100){
            break;
          }
          monitor.add(i);
        }
      });
    }
    TestExecutorService.completeAllTests("ArrSeqTest.testpeek_void");
  }
   @Test public void testpoll_void(){
    for(final var initParams:STACK_STRUCT_INIT_PARAMS){
      for(final var outputType:initParams.collectionType.validOutputTypes()){
        TestExecutorService.submitTest(()->{
          final var monitor=SequenceInitialization.Ascending.initialize(getMonitoredStack(initParams,100),100,0);
          for(int i=0;;++i){
            monitor.verifyPoll(outputType);
            if(i > 100){
              break;
            }
          }
        });
      }
    }
    TestExecutorService.completeAllTests("ArrSeqTest.testpoll_void");
  }
   @Test public void testpop_void(){
    for(final var initParams:STACK_STRUCT_INIT_PARAMS){
      for(final var size:SHORT_SIZES){
        if(size > 0 || initParams.checkedType.checked){
          for(final var outputType:initParams.collectionType.validOutputTypes()){
            TestExecutorService.submitTest(()->{
              final var monitor=SequenceInitialization.Ascending.initialize(getMonitoredStack(initParams,size),size,0);
              for(int i=0;i < size;++i){
                monitor.verifyPop(outputType);
              }
              if(initParams.checkedType.checked){
                Assertions.assertThrows(NoSuchElementException.class,()->monitor.verifyPop(outputType));
              }
            });
          }
        }
      }
    }
    TestExecutorService.completeAllTests("ArrSeqTest.testpop_void");
  }
   @Test public void testpush_val(){
    for(final var initParams:STACK_STRUCT_INIT_PARAMS){
      for(final var inputType:initParams.collectionType.mayBeAddedTo()){
        for(final var functionCallType:inputType.validFunctionCalls){
          for(final var initCap:INIT_CAPACITIES){
            TestExecutorService.submitTest(()->{
              final var monitor=getMonitoredStack(initParams,initCap);
              for(int i=0;i < 100;++i){
                monitor.verifyPush(inputType.convertValUnchecked(i),inputType,functionCallType);
              }
            });
          }
        }
      }
    }
    TestExecutorService.completeAllTests("ArrSeqTest.testpush_val");
  }
   @Test public void testput_intval(){
    for(final var initParams:LIST_STRUCT_INIT_PARAMS){
      for(final var illegalMod:initParams.structType.validPreMods){
        if(illegalMod.minDepth <= initParams.preAllocs.length
            && (initParams.checkedType.checked || illegalMod.expectedException == null)){
          for(final var size:SHORT_SIZES){
            TestExecutorService.submitTest(()->{
              final var monitor=SequenceInitialization.Ascending.initialize(getMonitoredList(initParams,size),size,0);
              if(illegalMod.expectedException == null){
                for(final var inputType:initParams.collectionType.mayBeAddedTo()){
                  for(final var functionCallType:inputType.validFunctionCalls){
                    if(initParams.checkedType.checked){
                      Assertions.assertThrows(IndexOutOfBoundsException.class,
                          ()->monitor.verifyPut(-1,inputType.convertValUnchecked(0),inputType,functionCallType));
                      Assertions.assertThrows(IndexOutOfBoundsException.class,()->monitor.verifyPut(size,
                          inputType.convertValUnchecked(size + 1),inputType,functionCallType));
                    }
                    for(int index=0;index < size;++index){
                      monitor.verifyPut(index,inputType.convertValUnchecked(index + 1),inputType,functionCallType);
                    }
                  }
                }
              }else{
                monitor.illegalMod(illegalMod);
                for(final var inputType:initParams.collectionType.mayBeAddedTo()){
                  for(final var functionCallType:inputType.validFunctionCalls){
                    for(int tmpIndex=-1;tmpIndex <= size;++tmpIndex){
                      final int index=tmpIndex;
                      Assertions.assertThrows(illegalMod.expectedException,()->monitor.verifyPut(index,
                          inputType.convertValUnchecked(index + 1),inputType,functionCallType));
                    }
                  }
                }
              }
            });
          }
        }
      }
    }
    TestExecutorService.completeAllTests("ArrSeqTest.testput_intval");
  }
   @Test public void testReadAndWrite(){
    final MonitoredFunctionTest<?> test=(monitor,functionGen,functionCallType,illegalMod,randSeed)->{
      if(illegalMod.expectedException == null){
        if(functionGen.expectedException == null){
          Assertions.assertDoesNotThrow(()->monitor.verifyReadAndWrite(functionGen));
        }else{
          Assertions.assertThrows(functionGen.expectedException,()->monitor.verifyReadAndWrite(functionGen));
        }
      }else{
        monitor.illegalMod(illegalMod);
        Assertions.assertThrows(illegalMod.expectedException,()->monitor.verifyReadAndWrite(functionGen));
      }
    };
    test.runAllTests("ArrSeqTest.testReadAndWrite",0,true);
  }
   @Test public void testremoveAt_int(){
    for(final var initParams:LIST_STRUCT_INIT_PARAMS){
      for(final var illegalMod:initParams.structType.validPreMods){
        if(illegalMod.minDepth <= initParams.preAllocs.length
            && (initParams.checkedType.checked || illegalMod.expectedException == null)){
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
                    final var monitor
                        =SequenceInitialization.Ascending.initialize(getMonitoredList(initParams,size),size,0);
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
    }
    TestExecutorService.completeAllTests("ArrSeqTest.testremoveAt_int");
  }
   @Test public void testremoveIf_Predicate(){
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
              for(final var illegalMod:initParams.structType.validPreMods){
                if(illegalMod.minDepth <= initParams.preAllocs.length
                    && (initParams.checkedType.checked || illegalMod.expectedException == null)){
                  long randSeedBound;
                  double[] thresholdArr;
                  if(filterGen.predicateGenCallType == PredicateGenCallType.Randomized && size > 0
                      && !functionCallType.boxed && illegalMod.expectedException == null){
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
                            final var monitor=SequenceInitialization.Ascending
                                .initialize(getMonitoredSequence(initParams,size),size,initVal,period);
                            final var filter
                                =filterGen.getMonitoredRemoveIfPredicate(monitor,threshold,new Random(randSeed));
                            if(illegalMod.expectedException == null){
                              if(filterGen.expectedException == null || size == 0){
                                monitor.verifyRemoveIf(filter,functionCallType);
                              }else{
                                Assertions.assertThrows(filterGen.expectedException,
                                    ()->monitor.verifyRemoveIf(filter,functionCallType));
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
    }
    TestExecutorService.completeAllTests("ArrSeqTest.testremoveIf_Predicate");
  }
   @Test public void testremoveVal_val(){
    final QueryTest<?> test=new QueryTest<>(){
      @Override public void callAndVerifyResult(MonitoredSequence<? extends AbstractSeq<?>> monitor,QueryVal queryVal,
          DataType inputType,QueryCastType castType,QueryValModification modification,
          MonitoredObjectGen monitoredObjectGen,double position,int seqSize){
        if(monitoredObjectGen == null){
          Assertions.assertEquals(position >= 0,monitor.verifyRemoveVal(queryVal,inputType,castType,modification));
        }else{
          monitor.verifyThrowingRemoveVal(monitoredObjectGen);
        }
      }
      @Override public boolean cmeFilter(IllegalModification illegalMod,DataType inputType,DataType collectionType,
          QueryVal queryVal,QueryVal.QueryValModification modification,QueryCastType castType){
        return illegalMod.expectedException == null
            || collectionType != DataType.REF && queryVal == QueryVal.Null && castType != QueryCastType.ToObject;
      }
    };
    test.runAllTests("ArrSeqTest.testremoveVal_val",2);
  }
   @Test public void testreplaceAll_UnaryOperator(){
    final MonitoredFunctionTest<MonitoredList<? extends AbstractSeq<?>>> test
        =(monitor,functionGen,functionCallType,illegalMod,randSeed)->{
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
    test.runAllTests("ArrSeqTest.testreplaceAll_UnaryOperator",100,false);
  }
   @Test public void testsearch_val(){
    final QueryTest<ArrStackMonitor<?,?>> test
        =(monitor,queryVal,inputType,castType,modification,monitoredObjectGen,position,seqSize)->{
          if(monitoredObjectGen == null){
            int expectedIndex;
            if(position >= 0){
              int size;
              expectedIndex=(size=monitor.size())
                  - monitor.findRemoveValIndex(queryVal.getInputVal(inputType,modification),inputType,0,size);
            }else{
              expectedIndex=-1;
            }
            Assertions.assertEquals(expectedIndex,monitor.verifySearch(queryVal,inputType,castType,modification));
          }else{
            monitor.verifyThrowingSearch(monitoredObjectGen);
          }
        };
    test.runAllTests("ArrSeqTest.testsearch",1);
  }
   @Test public void testset_intval(){
    for(final var initParams:LIST_STRUCT_INIT_PARAMS){
      for(final var illegalMod:initParams.structType.validPreMods){
        if(illegalMod.minDepth <= initParams.preAllocs.length
            && (initParams.checkedType.checked || illegalMod.expectedException == null)){
          for(final int size:SHORT_SIZES){
            TestExecutorService.submitTest(()->{
              final var monitor=SequenceInitialization.Ascending.initialize(getMonitoredList(initParams,size),size,0);
              if(illegalMod.expectedException == null){
                for(final var functionCallType:initParams.collectionType.validFunctionCalls){
                  if(initParams.checkedType.checked){
                    Assertions.assertThrows(IndexOutOfBoundsException.class,
                        ()->monitor.verifySet(-1,initParams.collectionType.convertValUnchecked(0),functionCallType));
                    Assertions.assertThrows(IndexOutOfBoundsException.class,()->monitor.verifySet(size,
                        initParams.collectionType.convertValUnchecked(size + 1),functionCallType));
                  }
                  for(int index=0;index < size;++index){
                    monitor.verifySet(index,initParams.collectionType.convertValUnchecked(index + 1),functionCallType);
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
    }
    TestExecutorService.completeAllTests("ArrSeqTest.testset_intval");
  }
   @Test public void testsize_void(){
    final BasicTest test=MonitoredSequence::verifySize;
    test.runAllTests("ArrSeqTest.testsize_void");
  }
   @Test public void testsort_Comparator(){
    for(final var initParams:LIST_STRUCT_INIT_PARAMS){
      for(final var comparatorGen:initParams.structType.validComparatorGens){
          if(comparatorGen.throwsContractViolationException && initParams.collectionType==DataType.BOOLEAN) {
              continue;
          }
        if(initParams.collectionType == DataType.REF || comparatorGen.validWithPrimitive){
          for(final var size:SIZES){
            if(size < 2 || comparatorGen.expectedException == null || initParams.checkedType.checked){
              for(final var functionCallType:initParams.collectionType.validFunctionCalls){
                for(final var illegalMod:initParams.structType.validPreMods){
                  if(illegalMod.minDepth <= initParams.preAllocs.length
                      && (initParams.checkedType.checked || illegalMod.expectedException == null)){
                    TestExecutorService.submitTest(()->{
                      final var monitor=getMonitoredList(initParams,size);
                      if(illegalMod.expectedException == null){
                        if(size < 2 || comparatorGen.expectedException == null){
                          monitor.verifyStableSort(size,comparatorGen,functionCallType);
                        }else{
                          Assertions.assertThrows(comparatorGen.expectedException,
                              ()->monitor.verifyStableSort(size,comparatorGen,functionCallType));
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
    }
    TestExecutorService.completeAllTests("ArrSeqTest.testsort_Comparator");
  }
   @Test public void teststableAscendingSort_void(){
    for(final var initParams:LIST_STRUCT_INIT_PARAMS){
      for(final var comparatorGen:initParams.structType.validComparatorGens){
        if(comparatorGen.validWithNoComparator
            && (comparatorGen.validWithPrimitive || initParams.collectionType == DataType.REF)){
          for(final var size:SIZES){
            if(size < 2 || comparatorGen.expectedException == null || initParams.checkedType.checked){
              for(final var illegalMod:initParams.structType.validPreMods){
                if(illegalMod.minDepth <= initParams.preAllocs.length
                    && (initParams.checkedType.checked || illegalMod.expectedException == null)){
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
    }
    TestExecutorService.completeAllTests("ArrSeqTest.teststableAscendingSort_void");
  }
   @Test public void teststableDescendingSort_void(){
    for(final var initParams:LIST_STRUCT_INIT_PARAMS){
      for(final var comparatorGen:initParams.structType.validComparatorGens){
        if(comparatorGen.validWithNoComparator
            && (comparatorGen.validWithPrimitive || initParams.collectionType == DataType.REF)){
          for(final var size:SIZES){
            if(size < 2 || comparatorGen.expectedException == null || initParams.checkedType.checked){
              for(final var illegalMod:initParams.structType.validPreMods){
                if(illegalMod.minDepth <= initParams.preAllocs.length
                    && (initParams.checkedType.checked || illegalMod.expectedException == null)){
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
    }
    TestExecutorService.completeAllTests("ArrSeqTest.teststableDescendingSort_void");
  }
   @Test public void testsubList_intint(){
    for(final var initParams:LIST_STRUCT_INIT_PARAMS){
      for(final int size:SIZES){
        final int inc=Math.max(1,size / 10);
        for(int tmpFromIndex=-inc,fromBound=size + 2 * inc;tmpFromIndex <= fromBound;tmpFromIndex+=inc){
          if(initParams.checkedType.checked || tmpFromIndex >= 0 && tmpFromIndex <= size){
            final int fromIndex=tmpFromIndex;
            for(int tmpToIndex=-(2 * inc),toBound=size + inc;tmpToIndex < toBound;tmpToIndex+=inc){
              if(initParams.checkedType.checked || tmpToIndex >= fromIndex && tmpToIndex <= size){
                final int toIndex=tmpToIndex;
                for(final var illegalMod:initParams.structType.validPreMods){
                  if(illegalMod.minDepth <= initParams.preAllocs.length
                      && (initParams.checkedType.checked || illegalMod.expectedException == null)){
                    TestExecutorService.submitTest(()->{
                      final var rootMonitor
                          =SequenceInitialization.Ascending.initialize(getMonitoredList(initParams,size),size,0);
                      if(illegalMod.expectedException == null){
                        if(fromIndex >= 0 && toIndex >= fromIndex && toIndex <= size){
                          rootMonitor.getMonitoredSubList(fromIndex,toIndex).verifyCollectionState();
                        }else{
                          Assertions.assertThrows(IndexOutOfBoundsException.class,()->{
                            try{
                              rootMonitor.getMonitoredSubList(fromIndex,toIndex);
                            }finally{
                              rootMonitor.verifyCollectionState();
                            }
                          });
                        }
                      }else{
                        rootMonitor.illegalMod(illegalMod);
                        Assertions.assertThrows(illegalMod.expectedException,()->{
                          try{
                            rootMonitor.getMonitoredSubList(fromIndex,toIndex);
                          }finally{
                            rootMonitor.verifyCollectionState();
                          }
                        });
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
    TestExecutorService.completeAllTests("ArrSeqTest.testsubList_intint");
  }
   @Test public void testtoArray_IntFunction(){
    final MonitoredFunctionTest<?> test=(monitor,functionGen,functionCallType,illegalMod,randSeed)->{
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
    test.runAllTests("ArrSeqTest.testtoArray_IntFunction",0,true);
  }
   @Test public void testtoArray_ObjectArray(){
    for(final var initParams:ALL_STRUCT_INIT_PARAMS){
      for(final var illegalMod:initParams.structType.validPreMods){
        if(illegalMod.minDepth <= initParams.preAllocs.length
            && (initParams.checkedType.checked || illegalMod.expectedException == null)){
          for(final int size:SIZES){
            for(int tmpArrSize=0,tmpArrSizeBound=size + 5;tmpArrSize <= tmpArrSizeBound;++tmpArrSize){
              final int arrSize=tmpArrSize;
              TestExecutorService.submitTest(()->{
                final var monitor
                    =SequenceInitialization.Ascending.initialize(getMonitoredSequence(initParams,size),size,0);
                if(illegalMod.expectedException == null){
                  monitor.verifyToArray(new Object[arrSize]);
                }else{
                  monitor.illegalMod(illegalMod);
                  Assertions.assertThrows(illegalMod.expectedException,()->monitor.verifyToArray(new Object[arrSize]));
                }
              });
            }
          }
        }
      }
    }
    TestExecutorService.completeAllTests("ArrSeqTest.testtoArray_ObjectArray");
  }
   @Test public void testtoArray_void(){
    for(final var initParams:ALL_STRUCT_INIT_PARAMS){
      for(final var illegalMod:initParams.structType.validPreMods){
        if(illegalMod.minDepth <= initParams.preAllocs.length
            && (initParams.checkedType.checked || illegalMod.expectedException == null)){
          for(final int size:SIZES){
            TestExecutorService.submitTest(()->{
              final var monitor
                  =SequenceInitialization.Ascending.initialize(getMonitoredSequence(initParams,size),size,0);
              if(illegalMod.expectedException == null){
                for(final var outputType:initParams.collectionType.validOutputTypes()){
                  outputType.verifyToArray(monitor);
                }
              }else{
                monitor.illegalMod(illegalMod);
                for(final var outputType:initParams.collectionType.validOutputTypes()){
                  Assertions.assertThrows(illegalMod.expectedException,()->outputType.verifyToArray(monitor));
                }
              }
            });
          }
        }
      }
    }
    TestExecutorService.completeAllTests("ArrSeqTest.testtoArray_void");
  }
   @Test public void testtoString_void(){
    final ToStringAndHashCodeTest test=new ToStringAndHashCodeTest(){
      @Override public void callRaw(OmniCollection<?> seq){
        seq.toString();
      }
      @Override public void callVerify(MonitoredSequence<?> monitor){
        monitor.verifyToString();
      }
    };
    test.runAllTests("ArrSeqTest.testtoString_void",ALL_STRUCT_INIT_PARAMS);
  }
   @Test public void testunstableAscendingSort_void(){
    for(final var initParams:LIST_STRUCT_INIT_PARAMS){
      if(initParams.collectionType == DataType.REF){
        for(final var comparatorGen:initParams.structType.validComparatorGens){
          if(comparatorGen.validWithNoComparator){
            for(final var size:SIZES){
              if(size < 2 || initParams.checkedType.checked || comparatorGen.expectedException == null){
                for(final var illegalMod:initParams.structType.validPreMods){
                  if(illegalMod.minDepth <= initParams.preAllocs.length
                      && (initParams.checkedType.checked || illegalMod.expectedException == null)){
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
    }
    TestExecutorService.completeAllTests("ArrSeqTest.testunstableAscendingSort_void");
  }
   @Test public void testunstableDescendingSort_void(){
    for(final var initParams:LIST_STRUCT_INIT_PARAMS){
      if(initParams.collectionType == DataType.REF){
        for(final var comparatorGen:initParams.structType.validComparatorGens){
          if(comparatorGen.validWithNoComparator){
            for(final var size:SIZES){
              if(size < 2 || initParams.checkedType.checked || comparatorGen.expectedException == null){
                for(final var illegalMod:initParams.structType.validPreMods){
                  if(illegalMod.minDepth <= initParams.preAllocs.length
                      && (initParams.checkedType.checked || illegalMod.expectedException == null)){
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
    }
    TestExecutorService.completeAllTests("ArrSeqTest.testunstableDescendingSort_void");
  }
   @Test public void testunstableSort_Comparator(){
    for(final var initParams:LIST_STRUCT_INIT_PARAMS){
      if(initParams.collectionType != DataType.BOOLEAN){
        for(final var comparatorGen:initParams.structType.validComparatorGens){
          if(initParams.collectionType == DataType.REF || comparatorGen.validWithPrimitive){
            for(final var size:SIZES){
              if(size < 2 || initParams.checkedType.checked || comparatorGen.expectedException == null){
                for(final var illegalMod:initParams.structType.validPreMods){
                  if(illegalMod.minDepth <= initParams.preAllocs.length
                      && (initParams.checkedType.checked || illegalMod.expectedException == null)){
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
    }
    TestExecutorService.completeAllTests("ArrSeqTest.testunstableSort_Comparator");
  }
}
