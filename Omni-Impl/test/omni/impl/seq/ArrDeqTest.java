package omni.impl.seq;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import omni.api.OmniCollection;
import omni.api.OmniDeque;
import omni.api.OmniIterator;
import omni.impl.CheckedType;
import omni.impl.DataType;
import omni.impl.FunctionCallType;
import omni.impl.IteratorType;
import omni.impl.MonitoredCollection;
import omni.impl.MonitoredFunction;
import omni.impl.MonitoredFunctionGen;
import omni.impl.MonitoredObjectGen;
import omni.impl.MonitoredRemoveIfPredicate;
import omni.impl.MonitoredRemoveIfPredicateGen;
import omni.impl.QueryCastType;
import omni.impl.QueryVal;
import omni.impl.StructType;
import omni.util.OmniArray;
import omni.util.TestExecutorService;
@TestMethodOrder(OrderAnnotation.class) @Tag("NewTest") public class ArrDeqTest{
  private static interface AddTest{
    private void runAllTests(String testName){
      for(final var checkedType:CheckedType.values()){
        for(final var collectionType:DataType.values()){
          for(final var inputType:collectionType.mayBeAddedTo()){
            for(final var functionCallType:inputType.validFunctionCalls){
              for(int tmpInitCap=0;tmpInitCap <= 15;tmpInitCap+=5){
                final int initCap=tmpInitCap;
                for(int tmpNumToRotate=0;tmpNumToRotate <= 10;++tmpNumToRotate){
                  final int numToRotate=tmpNumToRotate;
                  TestExecutorService.submitTest(()->{
                    final var monitor=new ArrDeqMonitor<>(checkedType,collectionType,initCap);
                    if(numToRotate != 0){
                      monitor.add(0);
                      monitor.rotate(numToRotate);
                    }
                    for(int i=0;i < 100;++i){
                      callMethod(monitor,inputType.convertValUnchecked(i),inputType,functionCallType);
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
    void callMethod(ArrDeqMonitor<?> monitor,Object inputVal,DataType inputType,FunctionCallType functionCallType);
  }
  private static class ArrDeqMonitor<E> extends AbstractArrDeqMonitor<OmniDeque<E>,E>{
    private abstract class AbstractItrMonitor extends AbstractArrDeqMonitor<OmniDeque<E>,E>.AbstractItrMonitor{
      AbstractItrMonitor(OmniIterator<?> itr,int expectedCursor,int numLeft){
        super(itr,expectedCursor,numLeft);
      }
      IntConsumer getForEachRemainingVerifier(MonitoredFunction function){
        final var functionItr=function.iterator();
        switch(dataType){
        case BOOLEAN:{
          final var expectedArr=(boolean[])ArrDeqMonitor.this.expectedArr;
          return index->Assertions.assertEquals((boolean)functionItr.next(),expectedArr[index]);
        }
        case BYTE:{
          final var expectedArr=(byte[])ArrDeqMonitor.this.expectedArr;
          return index->Assertions.assertEquals((byte)functionItr.next(),expectedArr[index]);
        }
        case CHAR:{
          final var expectedArr=(char[])ArrDeqMonitor.this.expectedArr;
          return index->Assertions.assertEquals((char)functionItr.next(),expectedArr[index]);
        }
        case SHORT:{
          final var expectedArr=(short[])ArrDeqMonitor.this.expectedArr;
          return index->Assertions.assertEquals((short)functionItr.next(),expectedArr[index]);
        }
        case INT:{
          final var expectedArr=(int[])ArrDeqMonitor.this.expectedArr;
          return index->Assertions.assertEquals((int)functionItr.next(),expectedArr[index]);
        }
        case LONG:{
          final var expectedArr=(long[])ArrDeqMonitor.this.expectedArr;
          return index->Assertions.assertEquals((long)functionItr.next(),expectedArr[index]);
        }
        case FLOAT:{
          final var expectedArr=(float[])ArrDeqMonitor.this.expectedArr;
          return index->Assertions.assertEquals((float)functionItr.next(),expectedArr[index]);
        }
        case DOUBLE:{
          final var expectedArr=(double[])ArrDeqMonitor.this.expectedArr;
          return index->Assertions.assertEquals((double)functionItr.next(),expectedArr[index]);
        }
        case REF:{
          final var expectedArr=(Object[])ArrDeqMonitor.this.expectedArr;
          return index->Assertions.assertSame(functionItr.next(),expectedArr[index]);
        }
        default:
          throw dataType.invalid();
        }
      }
    }
    private class AscendingItrMonitor extends AbstractItrMonitor{
      AscendingItrMonitor(){
        super(seq.iterator(),expectedTail != -1?expectedHead:-1,expectedSize);
      }
      @Override public IteratorType getIteratorType(){
        return IteratorType.AscendingItr;
      }
      @Override public void updateItrNextState(){
        expectedLastRet=expectedCursor;
        --numLeft;
        if(expectedCursor == expectedTail){
          expectedCursor=-1;
        }else if(++expectedCursor == expectedCapacity){
          expectedCursor=0;
        }
      }
      @Override public void updateItrRemoveState(){
        ++expectedItrModCount;
        ++expectedModCount;
        if(checkedType.checked){
          final int tail=expectedTail;
          switch(Integer.signum(tail - expectedHead)){
          case -1:
            checkedFragmentedRemove();
            break;
          case 0:
            expectedTail=-1;
            if(dataType == DataType.REF){
              ((Object[])expectedArr)[tail]=null;
            }
            break;
          default:
            nonfragmentedRemove();
          }
        }else{
          final int cursor=expectedCursor;
          final int tail=expectedTail;
          switch(cursor){
          case -1:
            switch(Integer.signum(tail - expectedHead)){
            case -1:
              expectedTail=tail == 0?expectedCapacity - 1:tail - 1;
              break;
            case 0:
              expectedTail=-1;
              break;
            default:
              expectedTail=tail - 1;
            }
            if(dataType == DataType.REF){
              ((Object[])expectedArr)[tail]=null;
            }
            break;
          case 0:
            final int arrBound=expectedCapacity - 1;
            int head=expectedHead;
            final int headDist=arrBound - head;
            final var arr=expectedArr;
            if(tail < headDist){
              System.arraycopy(arr,0,arr,arrBound,1);
              System.arraycopy(arr,1,arr,0,tail);
              if(dataType == DataType.REF){
                ((Object[])expectedArr)[tail]=null;
              }
              expectedTail=tail == 0?arrBound:tail - 1;
              expectedCursor=arrBound;
            }else{
              int tmp;
              System.arraycopy(arr,tmp=head,arr,++head,headDist);
              if(dataType == DataType.REF){
                ((Object[])expectedArr)[tmp]=null;
              }
              expectedHead=headDist == 0?0:head;
            }
            break;
          default:
            if(tail < expectedHead){
              uncheckedFragmentedRemove();
            }else{
              nonfragmentedRemove();
            }
          }
        }
        expectedLastRet=-1;
        --expectedSize;
      }
      @Override public void verifyCloneHelper(Object clone){
        final var checked=checkedType.checked;
        switch(dataType){
        case BOOLEAN:
          Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.BooleanArrDeq.AbstractDeqItr.cursor(itr));
          if(checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanArrDeq.Checked.AscendingItr.root(itr));
            Assertions.assertEquals(expectedItrModCount,
                FieldAndMethodAccessor.BooleanArrDeq.Checked.AscendingItr.modCount(itr));
            Assertions.assertEquals(expectedLastRet,
                FieldAndMethodAccessor.BooleanArrDeq.Checked.AscendingItr.lastRet(itr));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanArrDeq.AscendingItr.root(itr));
          }
          break;
        case BYTE:
          Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.ByteArrDeq.AbstractDeqItr.cursor(itr));
          if(checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.ByteArrDeq.Checked.AscendingItr.root(itr));
            Assertions.assertEquals(expectedItrModCount,
                FieldAndMethodAccessor.ByteArrDeq.Checked.AscendingItr.modCount(itr));
            Assertions.assertEquals(expectedLastRet,
                FieldAndMethodAccessor.ByteArrDeq.Checked.AscendingItr.lastRet(itr));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.ByteArrDeq.AscendingItr.root(itr));
          }
          break;
        case CHAR:
          Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.CharArrDeq.AbstractDeqItr.cursor(itr));
          if(checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.CharArrDeq.Checked.AscendingItr.root(itr));
            Assertions.assertEquals(expectedItrModCount,
                FieldAndMethodAccessor.CharArrDeq.Checked.AscendingItr.modCount(itr));
            Assertions.assertEquals(expectedLastRet,
                FieldAndMethodAccessor.CharArrDeq.Checked.AscendingItr.lastRet(itr));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.CharArrDeq.AscendingItr.root(itr));
          }
          break;
        case SHORT:
          Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.ShortArrDeq.AbstractDeqItr.cursor(itr));
          if(checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.ShortArrDeq.Checked.AscendingItr.root(itr));
            Assertions.assertEquals(expectedItrModCount,
                FieldAndMethodAccessor.ShortArrDeq.Checked.AscendingItr.modCount(itr));
            Assertions.assertEquals(expectedLastRet,
                FieldAndMethodAccessor.ShortArrDeq.Checked.AscendingItr.lastRet(itr));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.ShortArrDeq.AscendingItr.root(itr));
          }
          break;
        case INT:
          Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.IntArrDeq.AbstractDeqItr.cursor(itr));
          if(checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.IntArrDeq.Checked.AscendingItr.root(itr));
            Assertions.assertEquals(expectedItrModCount,
                FieldAndMethodAccessor.IntArrDeq.Checked.AscendingItr.modCount(itr));
            Assertions.assertEquals(expectedLastRet,FieldAndMethodAccessor.IntArrDeq.Checked.AscendingItr.lastRet(itr));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.IntArrDeq.AscendingItr.root(itr));
          }
          break;
        case LONG:
          Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.LongArrDeq.AbstractDeqItr.cursor(itr));
          if(checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.LongArrDeq.Checked.AscendingItr.root(itr));
            Assertions.assertEquals(expectedItrModCount,
                FieldAndMethodAccessor.LongArrDeq.Checked.AscendingItr.modCount(itr));
            Assertions.assertEquals(expectedLastRet,
                FieldAndMethodAccessor.LongArrDeq.Checked.AscendingItr.lastRet(itr));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.LongArrDeq.AscendingItr.root(itr));
          }
          break;
        case FLOAT:
          Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.FloatArrDeq.AbstractDeqItr.cursor(itr));
          if(checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.FloatArrDeq.Checked.AscendingItr.root(itr));
            Assertions.assertEquals(expectedItrModCount,
                FieldAndMethodAccessor.FloatArrDeq.Checked.AscendingItr.modCount(itr));
            Assertions.assertEquals(expectedLastRet,
                FieldAndMethodAccessor.FloatArrDeq.Checked.AscendingItr.lastRet(itr));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.FloatArrDeq.AscendingItr.root(itr));
          }
          break;
        case DOUBLE:
          Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.DoubleArrDeq.AbstractDeqItr.cursor(itr));
          if(checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleArrDeq.Checked.AscendingItr.root(itr));
            Assertions.assertEquals(expectedItrModCount,
                FieldAndMethodAccessor.DoubleArrDeq.Checked.AscendingItr.modCount(itr));
            Assertions.assertEquals(expectedLastRet,
                FieldAndMethodAccessor.DoubleArrDeq.Checked.AscendingItr.lastRet(itr));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleArrDeq.AscendingItr.root(itr));
          }
          break;
        case REF:
          Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.RefArrDeq.AbstractDeqItr.cursor(itr));
          if(checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.RefArrDeq.Checked.AscendingItr.root(itr));
            Assertions.assertEquals(expectedItrModCount,
                FieldAndMethodAccessor.RefArrDeq.Checked.AscendingItr.modCount(itr));
            Assertions.assertEquals(expectedLastRet,FieldAndMethodAccessor.RefArrDeq.Checked.AscendingItr.lastRet(itr));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.RefArrDeq.AscendingItr.root(itr));
          }
          break;
        default:
          throw dataType.invalid();
        }
      }
      @Override public void verifyForEachRemaining(MonitoredFunction function){
        if(expectedCursor != -1){
          final IntConsumer verifier=super.getForEachRemainingVerifier(function);
          int i=expectedCursor;
          if(i > expectedTail){
            for(;i < expectedCapacity;++i){
              verifier.accept(i);
            }
            i=0;
          }
          for(;i <= expectedTail;++i){
            verifier.accept(i);
          }
          expectedLastRet=expectedTail;
          expectedCursor=-1;
          Assertions.assertEquals(function.size(),numLeft);
          numLeft=0;
        }else{
          Assertions.assertTrue(function.isEmpty());
        }
      }
      private void checkedFragmentedRemove(){
        int head=expectedHead;
        int tail=expectedTail;
        final int lastRet=expectedLastRet;
        final int arrBound=expectedCapacity - 1;
        final var arr=expectedArr;
        int headDist=expectedLastRet - head;
        if(headDist >= 0){
          final int tailDist=arrBound - lastRet;
          if(headDist <= tailDist + tail + 1){
            if(headDist == 0){
              if(tailDist == 0){
                expectedHead=0;
              }else{
                expectedHead=head + 1;
              }
              if(dataType == DataType.REF){
                ((Object[])arr)[head]=null;
              }
            }else{
              System.arraycopy(expectedArr,tail=head,arr,++head,headDist);
              if(dataType == DataType.REF){
                ((Object[])arr)[tail]=null;
              }
              expectedHead=head;
            }
          }else{
            System.arraycopy(arr,lastRet + 1,arr,lastRet,tailDist);
            System.arraycopy(arr,0,arr,arrBound,1);
            System.arraycopy(arr,1,arr,0,tail);
            if(dataType == DataType.REF){
              ((Object[])arr)[tail]=null;
            }
            if(tail == 0){
              expectedTail=arrBound;
            }else{
              expectedTail=tail - 1;
            }
            expectedCursor=lastRet;
          }
        }else{
          final int tailDist=tail - lastRet;
          headDist=arrBound - head;
          if(tailDist <= headDist + lastRet + 1){
            if(tailDist == 0){
              if(lastRet == 0){
                expectedTail=arrBound;
              }else{
                expectedTail=tail - 1;
              }
            }else{
              System.arraycopy(arr,lastRet + 1,arr,lastRet,tailDist);
              expectedTail=tail - 1;
              expectedCursor=lastRet;
            }
            if(dataType == DataType.REF){
              ((Object[])arr)[tail]=null;
            }
          }else{
            System.arraycopy(arr,0,arr,1,lastRet);
            System.arraycopy(arr,arrBound,arr,0,1);
            int tmp;
            System.arraycopy(arr,tmp=head,arr,++head,headDist);
            if(dataType == DataType.REF){
              ((Object[])arr)[tmp]=null;
            }
            expectedHead=headDist == 0?0:head;
          }
        }
      }
      private void nonfragmentedRemove(){
        int head=expectedHead;
        final int tail=expectedTail;
        final int lastRet=expectedLastRet;
        final int headDist=lastRet - head;
        final int tailDist=tail - lastRet;
        final var arr=expectedArr;
        if(headDist <= tailDist){
          int tmp;
          System.arraycopy(arr,tmp=head,arr,++head,headDist);
          if(dataType == DataType.REF){
            ((Object[])arr)[tmp]=null;
          }
          expectedHead=head;
        }else{
          System.arraycopy(arr,lastRet + 1,arr,lastRet,tailDist);
          if(dataType == DataType.REF){
            ((Object[])arr)[tail]=null;
          }
          if(expectedCursor != -1){
            expectedCursor=lastRet;
          }
          expectedTail=tail - 1;
        }
      }
      private void uncheckedFragmentedRemove(){
        int head=expectedHead;
        final int tail=expectedTail;
        final int lastRet=expectedLastRet;
        final int arrBound=expectedCapacity - 1;
        final var arr=expectedArr;
        int headDist=expectedLastRet - head;
        if(headDist >= 0){
          final int tailDist=arrBound - lastRet;
          if(headDist <= tailDist + tail + 1){
            int tmp;
            System.arraycopy(arr,tmp=head,arr,++head,headDist);
            if(dataType == DataType.REF){
              ((Object[])arr)[tmp]=null;
            }
            expectedHead=head;
          }else{
            System.arraycopy(arr,lastRet + 1,arr,lastRet,tailDist);
            System.arraycopy(arr,0,arr,arrBound,1);
            System.arraycopy(arr,1,arr,0,tail);
            if(dataType == DataType.REF){
              ((Object[])arr)[tail]=null;
            }
            expectedTail=tail == 0?arrBound:tail - 1;
            expectedCursor=lastRet;
          }
        }else{
          final int tailDist=tail - lastRet;
          headDist=arrBound - head;
          if(tailDist <= headDist + lastRet + 1){
            System.arraycopy(arr,lastRet + 1,arr,lastRet,tailDist);
            if(dataType == DataType.REF){
              ((Object[])arr)[tail]=null;
            }
            expectedTail=tail - 1;
            expectedCursor=lastRet;
          }else{
            System.arraycopy(arr,0,arr,1,lastRet);
            System.arraycopy(arr,arrBound,arr,0,1);
            int tmp;
            System.arraycopy(arr,tmp=head,arr,++head,headDist);
            if(dataType == DataType.REF){
              ((Object[])arr)[tmp]=null;
            }
            expectedHead=headDist == 0?0:head;
          }
        }
      }
    }
    private static interface CloneVerifier{
      void verifyIndices(int thisIndex,int cloneIndex);
    }
    private class DescendingItrMonitor extends AbstractItrMonitor{
      DescendingItrMonitor(){
        super(seq.descendingIterator(),expectedTail,expectedSize);
      }
      @Override public IteratorType getIteratorType(){
        return IteratorType.DescendingItr;
      }
      @Override public void updateItrNextState(){
        expectedLastRet=expectedCursor;
        --numLeft;
        if(expectedCursor == expectedHead){
          expectedCursor=-1;
        }else if(--expectedCursor == -1){
          expectedCursor=expectedCapacity - 1;
        }
      }
      @Override public void updateItrRemoveState(){
        ++expectedItrModCount;
        ++expectedModCount;
        if(checkedType.checked){
          final int tail=expectedTail;
          switch(Integer.signum(tail - expectedHead)){
          case -1:
            checkedFragmentedRemove();
            break;
          case 0:
            expectedTail=-1;
            if(dataType == DataType.REF){
              ((Object[])expectedArr)[tail]=null;
            }
            break;
          default:
            nonfragmentedRemove();
          }
        }else{
          if(expectedCursor == -1){
            int head;
            switch(Integer.signum(expectedTail - (head=expectedHead))){
            case -1:
              expectedHead=head == expectedCapacity - 1?0:head + 1;
              break;
            case 0:
              expectedTail=-1;
              break;
            default:
              expectedHead=head + 1;
            }
            if(dataType == DataType.REF){
              ((Object[])expectedArr)[head]=null;
            }
          }else{
            if(expectedTail < expectedHead){
              uncheckedFragmentedRemove();
            }else{
              nonfragmentedRemove();
            }
          }
        }
        expectedLastRet=-1;
        --expectedSize;
      }
      @Override public void verifyCloneHelper(Object clone){
        final var checked=checkedType.checked;
        switch(dataType){
        case BOOLEAN:
          Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.BooleanArrDeq.AbstractDeqItr.cursor(itr));
          if(checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanArrDeq.Checked.DescendingItr.root(itr));
            Assertions.assertEquals(expectedItrModCount,
                FieldAndMethodAccessor.BooleanArrDeq.Checked.DescendingItr.modCount(itr));
            Assertions.assertEquals(expectedLastRet,
                FieldAndMethodAccessor.BooleanArrDeq.Checked.DescendingItr.lastRet(itr));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanArrDeq.DescendingItr.root(itr));
          }
          break;
        case BYTE:
          Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.ByteArrDeq.AbstractDeqItr.cursor(itr));
          if(checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.ByteArrDeq.Checked.DescendingItr.root(itr));
            Assertions.assertEquals(expectedItrModCount,
                FieldAndMethodAccessor.ByteArrDeq.Checked.DescendingItr.modCount(itr));
            Assertions.assertEquals(expectedLastRet,
                FieldAndMethodAccessor.ByteArrDeq.Checked.DescendingItr.lastRet(itr));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.ByteArrDeq.DescendingItr.root(itr));
          }
          break;
        case CHAR:
          Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.CharArrDeq.AbstractDeqItr.cursor(itr));
          if(checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.CharArrDeq.Checked.DescendingItr.root(itr));
            Assertions.assertEquals(expectedItrModCount,
                FieldAndMethodAccessor.CharArrDeq.Checked.DescendingItr.modCount(itr));
            Assertions.assertEquals(expectedLastRet,
                FieldAndMethodAccessor.CharArrDeq.Checked.DescendingItr.lastRet(itr));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.CharArrDeq.DescendingItr.root(itr));
          }
          break;
        case SHORT:
          Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.ShortArrDeq.AbstractDeqItr.cursor(itr));
          if(checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.ShortArrDeq.Checked.DescendingItr.root(itr));
            Assertions.assertEquals(expectedItrModCount,
                FieldAndMethodAccessor.ShortArrDeq.Checked.DescendingItr.modCount(itr));
            Assertions.assertEquals(expectedLastRet,
                FieldAndMethodAccessor.ShortArrDeq.Checked.DescendingItr.lastRet(itr));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.ShortArrDeq.DescendingItr.root(itr));
          }
          break;
        case INT:
          Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.IntArrDeq.AbstractDeqItr.cursor(itr));
          if(checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.IntArrDeq.Checked.DescendingItr.root(itr));
            Assertions.assertEquals(expectedItrModCount,
                FieldAndMethodAccessor.IntArrDeq.Checked.DescendingItr.modCount(itr));
            Assertions.assertEquals(expectedLastRet,
                FieldAndMethodAccessor.IntArrDeq.Checked.DescendingItr.lastRet(itr));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.IntArrDeq.DescendingItr.root(itr));
          }
          break;
        case LONG:
          Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.LongArrDeq.AbstractDeqItr.cursor(itr));
          if(checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.LongArrDeq.Checked.DescendingItr.root(itr));
            Assertions.assertEquals(expectedItrModCount,
                FieldAndMethodAccessor.LongArrDeq.Checked.DescendingItr.modCount(itr));
            Assertions.assertEquals(expectedLastRet,
                FieldAndMethodAccessor.LongArrDeq.Checked.DescendingItr.lastRet(itr));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.LongArrDeq.DescendingItr.root(itr));
          }
          break;
        case FLOAT:
          Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.FloatArrDeq.AbstractDeqItr.cursor(itr));
          if(checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.FloatArrDeq.Checked.DescendingItr.root(itr));
            Assertions.assertEquals(expectedItrModCount,
                FieldAndMethodAccessor.FloatArrDeq.Checked.DescendingItr.modCount(itr));
            Assertions.assertEquals(expectedLastRet,
                FieldAndMethodAccessor.FloatArrDeq.Checked.DescendingItr.lastRet(itr));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.FloatArrDeq.DescendingItr.root(itr));
          }
          break;
        case DOUBLE:
          Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.DoubleArrDeq.AbstractDeqItr.cursor(itr));
          if(checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleArrDeq.Checked.DescendingItr.root(itr));
            Assertions.assertEquals(expectedItrModCount,
                FieldAndMethodAccessor.DoubleArrDeq.Checked.DescendingItr.modCount(itr));
            Assertions.assertEquals(expectedLastRet,
                FieldAndMethodAccessor.DoubleArrDeq.Checked.DescendingItr.lastRet(itr));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleArrDeq.DescendingItr.root(itr));
          }
          break;
        case REF:
          Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.RefArrDeq.AbstractDeqItr.cursor(itr));
          if(checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.RefArrDeq.Checked.DescendingItr.root(itr));
            Assertions.assertEquals(expectedItrModCount,
                FieldAndMethodAccessor.RefArrDeq.Checked.DescendingItr.modCount(itr));
            Assertions.assertEquals(expectedLastRet,
                FieldAndMethodAccessor.RefArrDeq.Checked.DescendingItr.lastRet(itr));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.RefArrDeq.DescendingItr.root(itr));
          }
          break;
        default:
          throw dataType.invalid();
        }
      }
      @Override public void verifyForEachRemaining(MonitoredFunction function){
        if(expectedCursor != -1){
          final IntConsumer verifier=super.getForEachRemainingVerifier(function);
          int i=expectedCursor;
          if(i < expectedHead){
            for(;i >= 0;--i){
              verifier.accept(i);
            }
            i=expectedCapacity - 1;
          }
          for(;i >= expectedHead;--i){
            verifier.accept(i);
          }
          expectedLastRet=expectedHead;
          expectedCursor=-1;
          Assertions.assertEquals(function.size(),numLeft);
          numLeft=0;
        }else{
          Assertions.assertTrue(function.isEmpty());
        }
      }
      private void checkedFragmentedRemove(){
        int head=expectedHead;
        int tail=expectedTail;
        final int arrBound=expectedCapacity - 1;
        final var arr=expectedArr;
        final int lastRet=expectedLastRet;
        int headDist=expectedLastRet - head;
        if(headDist >= 0){
          final int tailDist=arrBound - lastRet;
          if(headDist <= tailDist + tail + 1){
            if(headDist == 0){
              if(tailDist == 0){
                expectedHead=0;
              }else{
                expectedHead=head + 1;
              }
              if(dataType == DataType.REF){
                ((Object[])arr)[head]=null;
              }
            }else{
              System.arraycopy(arr,tail=head,arr,++head,headDist);
              if(dataType == DataType.REF){
                ((Object[])arr)[tail]=null;
              }
              expectedHead=head;
              expectedCursor=lastRet;
            }
          }else{
            System.arraycopy(arr,lastRet + 1,arr,lastRet,tailDist);
            System.arraycopy(arr,0,arr,arrBound,1);
            System.arraycopy(arr,1,arr,0,tail);
            if(dataType == DataType.REF){
              ((Object[])arr)[tail]=null;
            }
            expectedTail=tail == 0?arrBound:tail - 1;
          }
        }else{
          final int tailDist=tail - lastRet;
          headDist=arrBound - head;
          if(tailDist <= headDist + lastRet + 1){
            if(tailDist == 0){
              if(lastRet == 0){
                expectedTail=arrBound;
              }else{
                expectedTail=tail - 1;
              }
            }else{
              System.arraycopy(arr,lastRet + 1,arr,lastRet,tailDist);
              expectedTail=tail - 1;
            }
            if(dataType == DataType.REF){
              ((Object[])arr)[tail]=null;
            }
          }else{
            System.arraycopy(arr,0,arr,1,lastRet);
            System.arraycopy(arr,arrBound,arr,0,1);
            int tmp;
            System.arraycopy(arr,tmp=head,arr,++head,headDist);
            if(dataType == DataType.REF){
              ((Object[])arr)[tmp]=null;
            }
            expectedHead=headDist == 0?0:head;
            expectedCursor=lastRet;
          }
        }
      }
      private void nonfragmentedRemove(){
        final int tail=expectedTail;
        int head=expectedHead;
        final var arr=expectedArr;
        final int lastRet=expectedLastRet;
        final int headDist=lastRet - head;
        final int tailDist=tail - lastRet;
        if(tailDist <= headDist){
          System.arraycopy(arr,lastRet + 1,arr,lastRet,tailDist);
          if(dataType == DataType.REF){
            ((Object[])arr)[tail]=null;
          }
          expectedTail=tail - 1;
        }else{
          int tmp;
          System.arraycopy(arr,tmp=head,arr,++head,headDist);
          if(dataType == DataType.REF){
            ((Object[])arr)[tmp]=null;
          }
          expectedHead=head;
          if(expectedCursor != -1){
            expectedCursor=lastRet;
          }
        }
      }
      private void uncheckedFragmentedRemove(){
        int head=expectedHead;
        int tail=expectedTail;
        final int arrBound=expectedCapacity - 1;
        final var arr=expectedArr;
        int cursor=expectedCursor;
        if(arrBound == cursor){
          if(tail <= (cursor=arrBound - head) + 1){
            System.arraycopy(arr,1,arr,0,tail);
            if(dataType == DataType.REF){
              ((Object[])arr)[tail]=null;
            }
            expectedTail=tail == 0?arrBound:tail - 1;
          }else{
            System.arraycopy(arr,arrBound,arr,0,1);
            int tmp;
            System.arraycopy(arr,tmp=head,arr,++head,cursor);
            if(dataType == DataType.REF){
              ((Object[])arr)[tmp]=null;
            }
            expectedHead=cursor == 0?0:head;
            expectedCursor=0;
          }
        }else{
          int headDist=++cursor - head;
          if(headDist > 0){
            final int tailDist=arrBound - cursor;
            if(headDist <= tailDist + tail + 1){
              System.arraycopy(arr,tail=head,arr,++head,headDist);
              if(dataType == DataType.REF){
                ((Object[])arr)[tail]=null;
              }
              expectedHead=head;
              expectedCursor=cursor;
            }else{
              System.arraycopy(arr,cursor + 1,arr,cursor,tailDist);
              System.arraycopy(arr,0,arr,arrBound,1);
              System.arraycopy(arr,1,arr,0,tail);
              if(dataType == DataType.REF){
                ((Object[])arr)[tail]=null;
              }
              expectedTail=tail == 0?arrBound:tail - 1;
            }
          }else{
            final int tailDist=tail - cursor;
            headDist=arrBound - head;
            if(tailDist <= headDist + cursor + 1){
              System.arraycopy(arr,cursor + 1,arr,cursor,tailDist);
              if(dataType == DataType.REF){
                ((Object[])arr)[tail]=null;
              }
              expectedTail=tail - 1;
            }else{
              System.arraycopy(arr,0,arr,1,cursor);
              System.arraycopy(arr,arrBound,arr,0,1);
              int tmp;
              System.arraycopy(arr,tmp=head,arr,++head,headDist);
              if(dataType == DataType.REF){
                ((Object[])arr)[tmp]=null;
              }
              expectedHead=headDist == 0?0:head;
              expectedCursor=cursor;
            }
          }
        }
      }
    }
    ArrDeqMonitor(CheckedType checkedType,DataType dataType){
      super(checkedType,dataType);
      updateCollectionState();
    }
    ArrDeqMonitor(CheckedType checkedType,DataType dataType,int initCap){
      super(checkedType,dataType,initCap);
      updateCollectionState();
    }
    @Override public Object get(int iterationIndex,DataType outputType){
      iterationIndex+=expectedHead;
      if(iterationIndex >= expectedCapacity){
        iterationIndex-=expectedCapacity;
      }
      return outputType.convertVal(dataType.getFromArray(iterationIndex,expectedArr));
    }
    @Override public MonitoredIterator<?,OmniDeque<E>> getMonitoredDescendingIterator(){
      return new DescendingItrMonitor();
    }
    @Override public MonitoredIterator<? extends OmniIterator<?>,OmniDeque<E>> getMonitoredIterator(){
      return new AscendingItrMonitor();
    }
    @Override public StructType getStructType(){
      return StructType.ArrDeq;
    }
    @Override public void modCollection(){
      switch(dataType){
      case BOOLEAN:
        ++((BooleanArrDeq.Checked)seq).modCount;
        break;
      case BYTE:
        ++((ByteArrDeq.Checked)seq).modCount;
        break;
      case CHAR:
        ++((CharArrDeq.Checked)seq).modCount;
        break;
      case SHORT:
        ++((ShortArrDeq.Checked)seq).modCount;
        break;
      case INT:
        ++((IntArrDeq.Checked)seq).modCount;
        break;
      case LONG:
        ++((LongArrDeq.Checked)seq).modCount;
        break;
      case FLOAT:
        ++((FloatArrDeq.Checked)seq).modCount;
        break;
      case DOUBLE:
        ++((DoubleArrDeq.Checked)seq).modCount;
        break;
      case REF:
        ++((RefArrDeq.Checked<?>)seq).modCount;
        break;
      default:
        throw dataType.invalid();
      }
      ++expectedModCount;
    }
    @Override public void rotate(int numToRotate){
      if(expectedSize <= 0){ return; }
      assert expectedHead == 0;
      assert expectedTail == expectedSize - 1;
      numToRotate%=expectedCapacity;
      if(numToRotate <= 0){ return; }
      Object actualArr;
      Object tmp=null;
      final int newHead=-numToRotate + expectedCapacity;
      int newTail=newHead + expectedSize - 1;
      if(newTail >= expectedCapacity){
        newTail-=expectedCapacity;
      }
      final int overflow=Math.min(numToRotate - (expectedCapacity - expectedSize),expectedSize - numToRotate);
      switch(dataType){
      case BOOLEAN:{
        final var seq=(BooleanArrDeq)this.seq;
        actualArr=seq.arr;
        seq.head=newHead;
        seq.tail=newTail;
        if(overflow > 0){
          tmp=new boolean[overflow];
        }
        break;
      }
      case BYTE:{
        final var seq=(ByteArrDeq)this.seq;
        actualArr=seq.arr;
        seq.head=newHead;
        seq.tail=newTail;
        if(overflow > 0){
          tmp=new byte[overflow];
        }
        break;
      }
      case CHAR:{
        final var seq=(CharArrDeq)this.seq;
        actualArr=seq.arr;
        seq.head=newHead;
        seq.tail=newTail;
        if(overflow > 0){
          tmp=new char[overflow];
        }
        break;
      }
      case DOUBLE:{
        final var seq=(DoubleArrDeq)this.seq;
        actualArr=seq.arr;
        seq.head=newHead;
        seq.tail=newTail;
        if(overflow > 0){
          tmp=new double[overflow];
        }
        break;
      }
      case FLOAT:{
        final var seq=(FloatArrDeq)this.seq;
        actualArr=seq.arr;
        seq.head=newHead;
        seq.tail=newTail;
        if(overflow > 0){
          tmp=new float[overflow];
        }
        break;
      }
      case INT:{
        final var seq=(IntArrDeq)this.seq;
        actualArr=seq.arr;
        seq.head=newHead;
        seq.tail=newTail;
        if(overflow > 0){
          tmp=new int[overflow];
        }
        break;
      }
      case LONG:{
        final var seq=(LongArrDeq)this.seq;
        actualArr=seq.arr;
        seq.head=newHead;
        seq.tail=newTail;
        if(overflow > 0){
          tmp=new long[overflow];
        }
        break;
      }
      case REF:{
        final var seq=(RefArrDeq<?>)this.seq;
        actualArr=seq.arr;
        seq.head=newHead;
        seq.tail=newTail;
        if(overflow > 0){
          tmp=new Object[overflow];
        }
        break;
      }
      case SHORT:{
        final var seq=(ShortArrDeq)this.seq;
        actualArr=seq.arr;
        seq.head=newHead;
        seq.tail=newTail;
        if(overflow > 0){
          tmp=new short[overflow];
        }
        break;
      }
      default:
        throw dataType.invalid();
      }
      if(tmp != null){
        if(expectedSize - numToRotate > overflow){
          System.arraycopy(actualArr,expectedSize - overflow,tmp,0,overflow);
          System.arraycopy(actualArr,0,actualArr,expectedCapacity - numToRotate,numToRotate);
          int tmpLength;
          System.arraycopy(actualArr,numToRotate,actualArr,0,tmpLength=expectedSize - numToRotate - overflow);
          System.arraycopy(tmp,0,actualArr,tmpLength,overflow);
        }else{
          System.arraycopy(actualArr,expectedSize - overflow,tmp,0,overflow);
          System.arraycopy(actualArr,0,actualArr,expectedCapacity - numToRotate,numToRotate);
          System.arraycopy(tmp,0,actualArr,0,overflow);
        }
      }else{
        if(numToRotate >= expectedSize){
          System.arraycopy(actualArr,0,actualArr,newHead,expectedSize);
        }else{
          System.arraycopy(actualArr,0,actualArr,expectedCapacity - numToRotate,numToRotate);
          System.arraycopy(actualArr,numToRotate,actualArr,0,expectedSize - numToRotate);
        }
      }
      expectedHead=newHead;
      expectedTail=newTail;
      if(dataType == DataType.REF){
        final Object[] castActual=(Object[])actualArr;
        if(newTail < newHead){
          for(int i=newTail + 1;i < newHead;++i){
            castActual[i]=null;
          }
        }else{
          for(int i=0;i < newHead;++i){
            castActual[i]=null;
          }
          for(int i=expectedCapacity - 1;i > newTail;--i){
            castActual[i]=null;
          }
        }
      }
      System.arraycopy(actualArr,0,expectedArr,0,expectedCapacity);
    }
    @Override public void updateAddFirstState(Object inputVal,DataType inputType){
      if(expectedSize == 0){
        if(expectedCapacity == 0){
          if(expectedArr == null){
            expectedArr=dataType.newArray(expectedCapacity=1);
          }else{
            expectedArr=dataType.newArray(expectedCapacity=OmniArray.DEFAULT_ARR_SEQ_CAP);
          }
        }
        expectedHead=expectedCapacity - 1;
        expectedTail=expectedCapacity - 1;
        dataType.convertValAndStoreInArray(expectedCapacity - 1,expectedArr,inputVal,inputType);
      }else{
        int head;
        int tail;
        if((tail=expectedTail) == (head=expectedHead - 1)){
          Object newArr;
          int newCap,size;
          expectedTail=(newCap=OmniArray.growBy50Pct(head + (size=expectedCapacity))) - 1;
          expectedCapacity=newCap;
          System.arraycopy(expectedArr,0,newArr=dataType.newArray(newCap),newCap-=++tail,tail);
          System.arraycopy(expectedArr,head + 1,newArr,head=newCap - (size-=tail),size);
          expectedArr=newArr;
          --head;
        }else if(head == -1 && tail == (head=expectedCapacity - 1)){
          int newCap;
          expectedTail=(newCap=OmniArray.growBy50Pct(++tail)) - 1;
          expectedCapacity=newCap;
          System.arraycopy(expectedArr,0,expectedArr=dataType.newArray(newCap),head=newCap - tail,tail);
          --head;
        }
        dataType.convertValAndStoreInArray(head,expectedArr,inputVal,inputType);
        expectedHead=head;
      }
      ++expectedModCount;
      ++expectedSize;
    }
    @Override public void updateAddState(Object inputVal,DataType inputType){
      if(expectedSize == 0){
        if(expectedCapacity == 0){
          if(expectedArr == null){
            expectedArr=dataType.newArray(expectedCapacity=1);
          }else{
            expectedArr=dataType.newArray(expectedCapacity=OmniArray.DEFAULT_ARR_SEQ_CAP);
          }
        }
        expectedHead=0;
        expectedTail=0;
        dataType.convertValAndStoreInArray(0,expectedArr,inputVal,inputType);
      }else{
        int head;
        int tail;
        if((tail=expectedTail + 1) == (head=expectedHead)){
          expectedHead=0;
          tail=expectedCapacity;
          final Object newArr=dataType.newArray(expectedCapacity=OmniArray.growBy50Pct(tail));
          dataType.convertValAndStoreInArray(tail,newArr,inputVal,inputType);
          expectedTail=tail;
          System.arraycopy(expectedArr,head,newArr,0,tail-=head);
          System.arraycopy(expectedArr,0,newArr,tail,head);
          expectedArr=newArr;
        }else{
          if(tail == expectedCapacity){
            if(head == 0){
              System.arraycopy(expectedArr,0,
                  expectedArr=dataType.newArray(expectedCapacity=OmniArray.growBy50Pct(tail)),0,tail);
            }else{
              tail=0;
            }
          }
          dataType.convertValAndStoreInArray(tail,expectedArr,inputVal,inputType);
          expectedTail=tail;
        }
      }
      ++expectedModCount;
      ++expectedSize;
    }
    @Override public void updateCollectionState(){
      Object actualArr;
      int actualCapacity;
      final var checked=checkedType.checked;
      switch(dataType){
      case BOOLEAN:{
        final var cast=(BooleanArrDeq)seq;
        expectedHead=cast.head;
        expectedTail=cast.tail;
        actualArr=cast.arr;
        actualCapacity=cast.arr == null?0:cast.arr.length;
        if(checked){
          expectedModCount=((BooleanArrDeq.Checked)cast).modCount;
        }
        break;
      }
      case BYTE:{
        final var cast=(ByteArrDeq)seq;
        expectedHead=cast.head;
        expectedTail=cast.tail;
        actualArr=cast.arr;
        actualCapacity=cast.arr == null?0:cast.arr.length;
        if(checked){
          expectedModCount=((ByteArrDeq.Checked)cast).modCount;
        }
        break;
      }
      case CHAR:{
        final var cast=(CharArrDeq)seq;
        expectedHead=cast.head;
        expectedTail=cast.tail;
        actualArr=cast.arr;
        actualCapacity=cast.arr == null?0:cast.arr.length;
        if(checked){
          expectedModCount=((CharArrDeq.Checked)cast).modCount;
        }
        break;
      }
      case SHORT:{
        final var cast=(ShortArrDeq)seq;
        expectedHead=cast.head;
        expectedTail=cast.tail;
        actualArr=cast.arr;
        actualCapacity=cast.arr == null?0:cast.arr.length;
        if(checked){
          expectedModCount=((ShortArrDeq.Checked)cast).modCount;
        }
        break;
      }
      case INT:{
        final var cast=(IntArrDeq)seq;
        expectedHead=cast.head;
        expectedTail=cast.tail;
        actualArr=cast.arr;
        actualCapacity=cast.arr == null?0:cast.arr.length;
        if(checked){
          expectedModCount=((IntArrDeq.Checked)cast).modCount;
        }
        break;
      }
      case LONG:{
        final var cast=(LongArrDeq)seq;
        expectedHead=cast.head;
        expectedTail=cast.tail;
        actualArr=cast.arr;
        actualCapacity=cast.arr == null?0:cast.arr.length;
        if(checked){
          expectedModCount=((LongArrDeq.Checked)cast).modCount;
        }
        break;
      }
      case FLOAT:{
        final var cast=(FloatArrDeq)seq;
        expectedHead=cast.head;
        expectedTail=cast.tail;
        actualArr=cast.arr;
        actualCapacity=cast.arr == null?0:cast.arr.length;
        if(checked){
          expectedModCount=((FloatArrDeq.Checked)cast).modCount;
        }
        break;
      }
      case DOUBLE:{
        final var cast=(DoubleArrDeq)seq;
        expectedHead=cast.head;
        expectedTail=cast.tail;
        actualArr=cast.arr;
        actualCapacity=cast.arr == null?0:cast.arr.length;
        if(checked){
          expectedModCount=((DoubleArrDeq.Checked)cast).modCount;
        }
        break;
      }
      case REF:{
        final var cast=(RefArrDeq<?>)seq;
        expectedHead=cast.head;
        expectedTail=cast.tail;
        actualArr=cast.arr;
        actualCapacity=cast.arr == null?0:cast.arr.length;
        if(checked){
          expectedModCount=((RefArrDeq.Checked<?>)cast).modCount;
        }
        break;
      }
      default:
        throw dataType.invalid();
      }
      if(actualArr == null || actualArr == dataType.defaultArr){
        expectedArr=actualArr;
        expectedCapacity=0;
        expectedSize=0;
      }else if(expectedCapacity != actualCapacity){
        expectedCapacity=actualCapacity;
        expectedArr=dataType.newArray(actualCapacity);
        if(expectedTail == -1){
          expectedSize=0;
        }else if(expectedTail < expectedHead){
          System.arraycopy(actualArr,expectedHead,expectedArr,expectedHead,actualCapacity - expectedHead);
          System.arraycopy(actualArr,0,expectedArr,0,expectedTail + 1);
          expectedSize=actualCapacity - expectedHead + expectedTail + 1;
        }else{
          System.arraycopy(actualArr,expectedHead,expectedArr,expectedHead,
              expectedSize=expectedTail + 1 - expectedHead);
        }
      }else if(dataType == DataType.REF){
        System.arraycopy(actualArr,0,expectedArr,0,actualCapacity);
        if(expectedTail == -1){
          expectedSize=0;
        }else{
          expectedSize=expectedTail + 1 - expectedHead;
          if(expectedSize <= 0){
            expectedSize+=actualCapacity;
          }
        }
      }else if(expectedTail == -1){
        expectedSize=0;
      }else if(expectedTail < expectedHead){
        System.arraycopy(actualArr,expectedHead,expectedArr,expectedHead,actualCapacity - expectedHead);
        System.arraycopy(actualArr,0,expectedArr,0,expectedTail + 1);
        expectedSize=actualCapacity - expectedHead + expectedTail + 1;
      }else{
        System.arraycopy(actualArr,expectedHead,expectedArr,expectedHead,expectedSize=expectedTail + 1 - expectedHead);
      }
    }
    @Override public void updateRemoveLastOccurrenceState(Object inputVal,DataType inputType){
      final int head=expectedHead;
      int tail=expectedTail;
      final IntPredicate indexSearcher=dataType.getArrayIndexSearcher(expectedArr,inputVal,inputType);
      ++expectedModCount;
      --expectedSize;
      if(tail < head){
        int index,bound;
        for(index=tail,bound=expectedCapacity - 1;index >= 0;--index){
          if(indexSearcher.test(index)){
            int headDist,tailDist;
            if((headDist=bound - head) + index + 1 < (tailDist=tail - index)){
              System.arraycopy(expectedArr,0,expectedArr,1,index);
              System.arraycopy(expectedArr,bound,expectedArr,0,1);
              System.arraycopy(expectedArr,head,expectedArr,tail=head + 1,headDist);
              if(dataType == DataType.REF){
                ((Object[])expectedArr)[head]=null;
              }
              expectedHead=tail > bound?0:tail;
            }else{
              System.arraycopy(expectedArr,index + 1,expectedArr,index,tailDist);
              if(dataType == DataType.REF){
                ((Object[])expectedArr)[tail]=null;
              }
              expectedTail=--tail == -1?bound:tail;
            }
            return;
          }
        }
        for(index=bound;;--index){
          if(indexSearcher.test(index)){
            int headDist,tailDist;
            if((headDist=index - head) <= (tailDist=bound - index) + tail + 1){
              System.arraycopy(expectedArr,head,expectedArr,tail=head + 1,headDist);
              if(dataType == DataType.REF){
                ((Object[])expectedArr)[head]=null;
              }
              expectedHead=tail > bound?0:tail;
            }else{
              System.arraycopy(expectedArr,index + 1,expectedArr,index,tailDist);
              System.arraycopy(expectedArr,0,expectedArr,bound,1);
              System.arraycopy(expectedArr,1,expectedArr,0,tail);
              if(dataType == DataType.REF){
                ((Object[])expectedArr)[tail]=null;
              }
              expectedTail=--tail == -1?bound:tail;
            }
            return;
          }
        }
      }else{
        int index;
        for(index=tail;;--index){
          if(indexSearcher.test(index)){
            int headDist,tailDist;
            if((tailDist=tail - index) <= (headDist=index - head)){
              if(headDist == 0){
                expectedTail=-1;
              }else{
                System.arraycopy(expectedArr,index + 1,expectedArr,index,tailDist);
                expectedTail=tail - 1;
              }
              if(dataType == DataType.REF){
                ((Object[])expectedArr)[tail]=null;
              }
            }else{
              System.arraycopy(expectedArr,head,expectedArr,tail=head + 1,headDist);
              if(dataType == DataType.REF){
                ((Object[])expectedArr)[head]=null;
              }
              expectedHead=tail;
            }
            return;
          }
        }
      }
    }
    @Override public void updateRemoveValState(Object inputVal,DataType inputType){
      final int head=expectedHead;
      int tail=expectedTail;
      final IntPredicate indexSearcher=dataType.getArrayIndexSearcher(expectedArr,inputVal,inputType);
      ++expectedModCount;
      --expectedSize;
      if(tail < head){
        int index,bound;
        for(index=head,bound=expectedCapacity - 1;index <= bound;++index){
          if(indexSearcher.test(index)){
            int headDist,tailDist;
            if((headDist=index - head) <= (tailDist=bound - index) + tail){
              System.arraycopy(expectedArr,head,expectedArr,tail=head + 1,headDist);
              if(dataType == DataType.REF){
                ((Object[])expectedArr)[head]=null;
              }
              expectedHead=tail > bound?0:tail;
            }else{
              System.arraycopy(expectedArr,index + 1,expectedArr,index,tailDist);
              System.arraycopy(expectedArr,0,expectedArr,bound,1);
              System.arraycopy(expectedArr,1,expectedArr,0,tail);
              if(dataType == DataType.REF){
                ((Object[])expectedArr)[tail]=null;
              }
              expectedTail=--tail == -1?bound:tail;
            }
            return;
          }
        }
        for(index=0;;++index){
          if(indexSearcher.test(index)){
            int headDist,tailDist;
            if((headDist=bound - head) + index + 1 < (tailDist=tail - index)){
              System.arraycopy(expectedArr,0,expectedArr,1,index);
              System.arraycopy(expectedArr,bound,expectedArr,0,1);
              System.arraycopy(expectedArr,head,expectedArr,tail=head + 1,headDist);
              if(dataType == DataType.REF){
                ((Object[])expectedArr)[head]=null;
              }
              expectedHead=tail > bound?0:tail;
            }else{
              System.arraycopy(expectedArr,index + 1,expectedArr,index,tailDist);
              if(dataType == DataType.REF){
                ((Object[])expectedArr)[tail]=null;
              }
              expectedTail=--tail == -1?bound:tail;
            }
            return;
          }
        }
      }else{
        int index;
        for(index=head;;++index){
          if(indexSearcher.test(index)){
            int headDist,tailDist;
            if((tailDist=tail - index) <= (headDist=index - head)){
              if(headDist == 0){
                expectedTail=-1;
              }else{
                System.arraycopy(expectedArr,index + 1,expectedArr,index,tailDist);
                expectedTail=tail - 1;
              }
              if(dataType == DataType.REF){
                ((Object[])expectedArr)[tail]=null;
              }
            }else{
              System.arraycopy(expectedArr,head,expectedArr,tail=head + 1,headDist);
              if(dataType == DataType.REF){
                ((Object[])expectedArr)[head]=null;
              }
              expectedHead=tail;
            }
            return;
          }
        }
      }
    }
    @Override public void verifyArrayIsCopy(Object arr,boolean emptyArrayMayBeSame){
      Object expectedArr;
      switch(dataType){
      case BOOLEAN:
        expectedArr=((BooleanArrDeq)seq).arr;
        if(arr instanceof boolean[]){
          break;
        }
        return;
      case BYTE:
        expectedArr=((ByteArrDeq)seq).arr;
        if(arr instanceof byte[]){
          break;
        }
        return;
      case CHAR:
        expectedArr=((CharArrDeq)seq).arr;
        if(arr instanceof char[]){
          break;
        }
        return;
      case SHORT:
        expectedArr=((ShortArrDeq)seq).arr;
        if(arr instanceof short[]){
          break;
        }
        return;
      case INT:
        expectedArr=((IntArrDeq)seq).arr;
        if(arr instanceof int[]){
          break;
        }
        return;
      case LONG:
        expectedArr=((LongArrDeq)seq).arr;
        if(arr instanceof long[]){
          break;
        }
        return;
      case FLOAT:
        expectedArr=((FloatArrDeq)seq).arr;
        if(arr instanceof float[]){
          break;
        }
        return;
      case DOUBLE:
        expectedArr=((DoubleArrDeq)seq).arr;
        if(arr instanceof double[]){
          break;
        }
        return;
      case REF:
        expectedArr=((RefArrDeq<?>)seq).arr;
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
      final var checked=checkedType.checked;
      int cloneHead;
      int cloneTail;
      int cloneCapacity;
      int thisHead;
      int thisTail;
      Object cloneArr;
      CloneVerifier cloneVerifier;
      switch(dataType){
      case BOOLEAN:{
        final var cloneCast=(BooleanArrDeq)clone;
        final var thisCast=(BooleanArrDeq)seq;
        thisHead=thisCast.head;
        thisTail=thisCast.tail;
        cloneHead=cloneCast.head;
        cloneTail=cloneCast.tail;
        final var cloneCastArr=cloneCast.arr;
        cloneCapacity=cloneCastArr == null?0:cloneCastArr.length;
        cloneArr=cloneCastArr;
        Assertions.assertEquals(checked,cloneCast instanceof BooleanArrDeq.Checked);
        if(checked){
          Assertions.assertEquals(0,((BooleanArrDeq.Checked)cloneCast).modCount);
        }
        final var thisArr=(boolean[])expectedArr;
        cloneVerifier=(thisIndex,cloneIndex)->Assertions.assertEquals(thisArr[thisIndex],cloneCastArr[cloneIndex]);
        break;
      }
      case BYTE:{
        final var cloneCast=(ByteArrDeq)clone;
        final var thisCast=(ByteArrDeq)seq;
        thisHead=thisCast.head;
        thisTail=thisCast.tail;
        cloneHead=cloneCast.head;
        cloneTail=cloneCast.tail;
        final var cloneCastArr=cloneCast.arr;
        cloneCapacity=cloneCastArr == null?0:cloneCastArr.length;
        cloneArr=cloneCastArr;
        Assertions.assertEquals(checked,cloneCast instanceof ByteArrDeq.Checked);
        if(checked){
          Assertions.assertEquals(0,((ByteArrDeq.Checked)cloneCast).modCount);
        }
        final var thisArr=(byte[])expectedArr;
        cloneVerifier=(thisIndex,cloneIndex)->Assertions.assertEquals(thisArr[thisIndex],cloneCastArr[cloneIndex]);
        break;
      }
      case CHAR:{
        final var cloneCast=(CharArrDeq)clone;
        final var thisCast=(CharArrDeq)seq;
        thisHead=thisCast.head;
        thisTail=thisCast.tail;
        cloneHead=cloneCast.head;
        cloneTail=cloneCast.tail;
        final var cloneCastArr=cloneCast.arr;
        cloneCapacity=cloneCastArr == null?0:cloneCastArr.length;
        cloneArr=cloneCastArr;
        Assertions.assertEquals(checked,cloneCast instanceof CharArrDeq.Checked);
        if(checked){
          Assertions.assertEquals(0,((CharArrDeq.Checked)cloneCast).modCount);
        }
        final var thisArr=(char[])expectedArr;
        cloneVerifier=(thisIndex,cloneIndex)->Assertions.assertEquals(thisArr[thisIndex],cloneCastArr[cloneIndex]);
        break;
      }
      case SHORT:{
        final var cloneCast=(ShortArrDeq)clone;
        final var thisCast=(ShortArrDeq)seq;
        thisHead=thisCast.head;
        thisTail=thisCast.tail;
        cloneHead=cloneCast.head;
        cloneTail=cloneCast.tail;
        final var cloneCastArr=cloneCast.arr;
        cloneCapacity=cloneCastArr == null?0:cloneCastArr.length;
        cloneArr=cloneCastArr;
        Assertions.assertEquals(checked,cloneCast instanceof ShortArrDeq.Checked);
        if(checked){
          Assertions.assertEquals(0,((ShortArrDeq.Checked)cloneCast).modCount);
        }
        final var thisArr=(short[])expectedArr;
        cloneVerifier=(thisIndex,cloneIndex)->Assertions.assertEquals(thisArr[thisIndex],cloneCastArr[cloneIndex]);
        break;
      }
      case INT:{
        final var cloneCast=(IntArrDeq)clone;
        final var thisCast=(IntArrDeq)seq;
        thisHead=thisCast.head;
        thisTail=thisCast.tail;
        cloneHead=cloneCast.head;
        cloneTail=cloneCast.tail;
        final var cloneCastArr=cloneCast.arr;
        cloneCapacity=cloneCastArr == null?0:cloneCastArr.length;
        cloneArr=cloneCastArr;
        Assertions.assertEquals(checked,cloneCast instanceof IntArrDeq.Checked);
        if(checked){
          Assertions.assertEquals(0,((IntArrDeq.Checked)cloneCast).modCount);
        }
        final var thisArr=(int[])expectedArr;
        cloneVerifier=(thisIndex,cloneIndex)->Assertions.assertEquals(thisArr[thisIndex],cloneCastArr[cloneIndex]);
        break;
      }
      case LONG:{
        final var cloneCast=(LongArrDeq)clone;
        final var thisCast=(LongArrDeq)seq;
        thisHead=thisCast.head;
        thisTail=thisCast.tail;
        cloneHead=cloneCast.head;
        cloneTail=cloneCast.tail;
        final var cloneCastArr=cloneCast.arr;
        cloneCapacity=cloneCastArr == null?0:cloneCastArr.length;
        cloneArr=cloneCastArr;
        Assertions.assertEquals(checked,cloneCast instanceof LongArrDeq.Checked);
        if(checked){
          Assertions.assertEquals(0,((LongArrDeq.Checked)cloneCast).modCount);
        }
        final var thisArr=(long[])expectedArr;
        cloneVerifier=(thisIndex,cloneIndex)->Assertions.assertEquals(thisArr[thisIndex],cloneCastArr[cloneIndex]);
        break;
      }
      case FLOAT:{
        final var cloneCast=(FloatArrDeq)clone;
        final var thisCast=(FloatArrDeq)seq;
        thisHead=thisCast.head;
        thisTail=thisCast.tail;
        cloneHead=cloneCast.head;
        cloneTail=cloneCast.tail;
        final var cloneCastArr=cloneCast.arr;
        cloneCapacity=cloneCastArr == null?0:cloneCastArr.length;
        cloneArr=cloneCastArr;
        Assertions.assertEquals(checked,cloneCast instanceof FloatArrDeq.Checked);
        if(checked){
          Assertions.assertEquals(0,((FloatArrDeq.Checked)cloneCast).modCount);
        }
        final var thisArr=(float[])expectedArr;
        cloneVerifier=(thisIndex,cloneIndex)->Assertions.assertEquals(thisArr[thisIndex],cloneCastArr[cloneIndex]);
        break;
      }
      case DOUBLE:{
        final var cloneCast=(DoubleArrDeq)clone;
        final var thisCast=(DoubleArrDeq)seq;
        thisHead=thisCast.head;
        thisTail=thisCast.tail;
        cloneHead=cloneCast.head;
        cloneTail=cloneCast.tail;
        final var cloneCastArr=cloneCast.arr;
        cloneCapacity=cloneCastArr == null?0:cloneCastArr.length;
        cloneArr=cloneCastArr;
        Assertions.assertEquals(checked,cloneCast instanceof DoubleArrDeq.Checked);
        if(checked){
          Assertions.assertEquals(0,((DoubleArrDeq.Checked)cloneCast).modCount);
        }
        final var thisArr=(double[])expectedArr;
        cloneVerifier=(thisIndex,cloneIndex)->Assertions.assertEquals(thisArr[thisIndex],cloneCastArr[cloneIndex]);
        break;
      }
      case REF:{
        final var cloneCast=(RefArrDeq<?>)clone;
        final var thisCast=(RefArrDeq<?>)seq;
        thisHead=thisCast.head;
        thisTail=thisCast.tail;
        cloneHead=cloneCast.head;
        cloneTail=cloneCast.tail;
        final var cloneCastArr=cloneCast.arr;
        cloneCapacity=cloneCastArr == null?0:cloneCastArr.length;
        cloneArr=cloneCastArr;
        Assertions.assertEquals(checked,cloneCast instanceof RefArrDeq.Checked);
        if(checked){
          Assertions.assertEquals(0,((RefArrDeq.Checked<?>)cloneCast).modCount);
        }
        final var thisArr=(Object[])expectedArr;
        cloneVerifier=(thisIndex,cloneIndex)->Assertions.assertEquals(thisArr[thisIndex],cloneCastArr[cloneIndex]);
        break;
      }
      default:
        throw dataType.invalid();
      }
      Assertions.assertNotSame(clone,seq);
      if(expectedSize == 0){
        Assertions.assertEquals(-1,cloneTail);
        Assertions.assertEquals(0,cloneHead);
        Assertions.assertSame(dataType.defaultArr,cloneArr);
      }else{
        Assertions.assertNotSame(cloneArr,expectedArr);
        Assertions.assertEquals(expectedSize,cloneTail + 1 - cloneHead);
        Assertions.assertEquals(expectedSize,cloneCapacity);
        int cloneIndex=0;
        if(thisTail < thisHead){
          final int bound=expectedCapacity;
          for(int thisIndex=thisHead;thisIndex < bound;++thisIndex,++cloneIndex){
            cloneVerifier.verifyIndices(thisIndex,cloneIndex);
          }
          for(int thisIndex=0;thisIndex <= thisTail;++thisIndex,++cloneIndex){
            cloneVerifier.verifyIndices(thisIndex,cloneIndex);
          }
        }else{
          for(int thisIndex=thisHead;thisIndex <= thisTail;++thisIndex,++cloneIndex){
            cloneVerifier.verifyIndices(thisIndex,cloneIndex);
          }
        }
      }
    }
    @Override public void verifyCollectionState(){
      int actualHead;
      int actualTail;
      int actualCapacity;
      Object actualArr;
      final var checked=checkedType.checked;
      IntConsumer indexVerifier;
      switch(dataType){
      case BOOLEAN:{
        final var castSeq=(BooleanArrDeq)seq;
        actualHead=castSeq.head;
        actualTail=castSeq.tail;
        final var castActualArr=castSeq.arr;
        actualArr=castActualArr;
        actualCapacity=castActualArr == null?0:castActualArr.length;
        if(checked){
          Assertions.assertEquals(expectedModCount,((BooleanArrDeq.Checked)castSeq).modCount);
        }
        final var expectedArr=(boolean[])this.expectedArr;
        indexVerifier=index->Assertions.assertEquals(expectedArr[index],castActualArr[index]);
        break;
      }
      case BYTE:{
        final var castSeq=(ByteArrDeq)seq;
        actualHead=castSeq.head;
        actualTail=castSeq.tail;
        final var castActualArr=castSeq.arr;
        actualArr=castActualArr;
        actualCapacity=castActualArr == null?0:castActualArr.length;
        if(checked){
          Assertions.assertEquals(expectedModCount,((ByteArrDeq.Checked)castSeq).modCount);
        }
        final var expectedArr=(byte[])this.expectedArr;
        indexVerifier=index->Assertions.assertEquals(expectedArr[index],castActualArr[index]);
        break;
      }
      case CHAR:{
        final var castSeq=(CharArrDeq)seq;
        actualHead=castSeq.head;
        actualTail=castSeq.tail;
        final var castActualArr=castSeq.arr;
        actualArr=castActualArr;
        actualCapacity=castActualArr == null?0:castActualArr.length;
        if(checked){
          Assertions.assertEquals(expectedModCount,((CharArrDeq.Checked)castSeq).modCount);
        }
        final var expectedArr=(char[])this.expectedArr;
        indexVerifier=index->Assertions.assertEquals(expectedArr[index],castActualArr[index]);
        break;
      }
      case SHORT:{
        final var castSeq=(ShortArrDeq)seq;
        actualHead=castSeq.head;
        actualTail=castSeq.tail;
        final var castActualArr=castSeq.arr;
        actualArr=castActualArr;
        actualCapacity=castActualArr == null?0:castActualArr.length;
        if(checked){
          Assertions.assertEquals(expectedModCount,((ShortArrDeq.Checked)castSeq).modCount);
        }
        final var expectedArr=(short[])this.expectedArr;
        indexVerifier=index->Assertions.assertEquals(expectedArr[index],castActualArr[index]);
        break;
      }
      case INT:{
        final var castSeq=(IntArrDeq)seq;
        actualHead=castSeq.head;
        actualTail=castSeq.tail;
        final var castActualArr=castSeq.arr;
        actualArr=castActualArr;
        actualCapacity=castActualArr == null?0:castActualArr.length;
        if(checked){
          Assertions.assertEquals(expectedModCount,((IntArrDeq.Checked)castSeq).modCount);
        }
        final var expectedArr=(int[])this.expectedArr;
        indexVerifier=index->Assertions.assertEquals(expectedArr[index],castActualArr[index]);
        break;
      }
      case LONG:{
        final var castSeq=(LongArrDeq)seq;
        actualHead=castSeq.head;
        actualTail=castSeq.tail;
        final var castActualArr=castSeq.arr;
        actualArr=castActualArr;
        actualCapacity=castActualArr == null?0:castActualArr.length;
        if(checked){
          Assertions.assertEquals(expectedModCount,((LongArrDeq.Checked)castSeq).modCount);
        }
        final var expectedArr=(long[])this.expectedArr;
        indexVerifier=index->Assertions.assertEquals(expectedArr[index],castActualArr[index]);
        break;
      }
      case FLOAT:{
        final var castSeq=(FloatArrDeq)seq;
        actualHead=castSeq.head;
        actualTail=castSeq.tail;
        final var castActualArr=castSeq.arr;
        actualArr=castActualArr;
        actualCapacity=castActualArr == null?0:castActualArr.length;
        if(checked){
          Assertions.assertEquals(expectedModCount,((FloatArrDeq.Checked)castSeq).modCount);
        }
        final var expectedArr=(float[])this.expectedArr;
        indexVerifier=index->Assertions.assertEquals(expectedArr[index],castActualArr[index]);
        break;
      }
      case DOUBLE:{
        final var castSeq=(DoubleArrDeq)seq;
        actualHead=castSeq.head;
        actualTail=castSeq.tail;
        final var castActualArr=castSeq.arr;
        actualArr=castActualArr;
        actualCapacity=castActualArr == null?0:castActualArr.length;
        if(checked){
          Assertions.assertEquals(expectedModCount,((DoubleArrDeq.Checked)castSeq).modCount);
        }
        final var expectedArr=(double[])this.expectedArr;
        indexVerifier=index->Assertions.assertEquals(expectedArr[index],castActualArr[index]);
        break;
      }
      case REF:{
        final var castSeq=(RefArrDeq<?>)seq;
        actualHead=castSeq.head;
        actualTail=castSeq.tail;
        final var castActualArr=castSeq.arr;
        actualArr=castActualArr;
        actualCapacity=castActualArr == null?0:castActualArr.length;
        if(checked){
          Assertions.assertEquals(expectedModCount,((RefArrDeq.Checked<?>)castSeq).modCount);
        }
        final var expectedArr=(Object[])this.expectedArr;
        indexVerifier=index->Assertions.assertSame(expectedArr[index],castActualArr[index]);
        if(actualTail == -1){
          if(actualCapacity != 0){
            for(int i=actualCapacity;--i >= 0;){
              Assertions.assertNull(castActualArr[i]);
            }
          }
        }else{
          if(actualTail < actualHead){
            for(int i=actualTail + 1;i < actualHead;++i){
              Assertions.assertNull(castActualArr[i]);
            }
          }else{
            for(int i=0;i < actualHead;++i){
              Assertions.assertNull(castActualArr[i]);
            }
            for(int i=actualTail;++i < actualCapacity;){
              Assertions.assertNull(castActualArr[i]);
            }
          }
        }
        break;
      }
      default:
        throw dataType.invalid();
      }
      Assertions.assertEquals(expectedHead,actualHead);
      Assertions.assertEquals(expectedTail,actualTail);
      Assertions.assertEquals(expectedCapacity,actualCapacity);
      Assertions.assertTrue(actualCapacity > 0 || actualArr == null || actualArr == dataType.defaultArr);
      if(actualTail == -1){
        Assertions.assertEquals(0,expectedSize);
      }else{
        if(actualTail < actualHead){
          Assertions.assertEquals(expectedSize,actualTail + 1 + actualCapacity - actualHead);
          for(int i=actualHead;i < actualCapacity;++i){
            indexVerifier.accept(i);
          }
          for(int i=0;i <= actualTail;++i){
            indexVerifier.accept(i);
          }
        }else{
          Assertions.assertEquals(expectedSize,actualTail + 1 - actualHead);
          for(int i=actualHead;i <= actualTail;++i){
            indexVerifier.accept(i);
          }
        }
      }
    }
    @Override public void verifyGetResult(int expectedCursor,Object output,DataType outputType){
      switch(outputType){
      case BOOLEAN:
        Assertions.assertEquals(((boolean[])expectedArr)[expectedCursor],(boolean)output);
        break;
      case BYTE:{
        final var v=(byte)output;
        switch(dataType){
        case BOOLEAN:
          Assertions.assertEquals(((boolean[])expectedArr)[expectedCursor]?(byte)1:(byte)0,v);
          break;
        case BYTE:
          Assertions.assertEquals(((byte[])expectedArr)[expectedCursor],v);
          break;
        default:
          throw dataType.invalid();
        }
        break;
      }
      case CHAR:{
        final var v=(char)output;
        switch(dataType){
        case BOOLEAN:
          Assertions.assertEquals(((boolean[])expectedArr)[expectedCursor]?(char)1:(char)0,v);
          break;
        case CHAR:
          Assertions.assertEquals(((char[])expectedArr)[expectedCursor],v);
          break;
        default:
          throw dataType.invalid();
        }
        break;
      }
      case SHORT:{
        final var v=(short)output;
        switch(dataType){
        case BOOLEAN:
          Assertions.assertEquals(((boolean[])expectedArr)[expectedCursor]?(short)1:(short)0,v);
          break;
        case BYTE:
          Assertions.assertEquals(((byte[])expectedArr)[expectedCursor],v);
          break;
        case SHORT:
          Assertions.assertEquals(((short[])expectedArr)[expectedCursor],v);
          break;
        default:
          throw dataType.invalid();
        }
        break;
      }
      case INT:{
        final var v=(int)output;
        switch(dataType){
        case BOOLEAN:
          Assertions.assertEquals(((boolean[])expectedArr)[expectedCursor]?1:0,v);
          break;
        case BYTE:
          Assertions.assertEquals(((byte[])expectedArr)[expectedCursor],v);
          break;
        case CHAR:
          Assertions.assertEquals(((char[])expectedArr)[expectedCursor],v);
          break;
        case SHORT:
          Assertions.assertEquals(((short[])expectedArr)[expectedCursor],v);
          break;
        case INT:
          Assertions.assertEquals(((int[])expectedArr)[expectedCursor],v);
          break;
        default:
          throw dataType.invalid();
        }
        break;
      }
      case LONG:{
        final var v=(long)output;
        switch(dataType){
        case BOOLEAN:
          Assertions.assertEquals(((boolean[])expectedArr)[expectedCursor]?1L:0L,v);
          break;
        case BYTE:
          Assertions.assertEquals(((byte[])expectedArr)[expectedCursor],v);
          break;
        case CHAR:
          Assertions.assertEquals(((char[])expectedArr)[expectedCursor],v);
          break;
        case SHORT:
          Assertions.assertEquals(((short[])expectedArr)[expectedCursor],v);
          break;
        case INT:
          Assertions.assertEquals(((int[])expectedArr)[expectedCursor],v);
          break;
        case LONG:
          Assertions.assertEquals(((long[])expectedArr)[expectedCursor],v);
          break;
        default:
          throw dataType.invalid();
        }
        break;
      }
      case FLOAT:{
        final var v=(float)output;
        switch(dataType){
        case BOOLEAN:
          Assertions.assertEquals(((boolean[])expectedArr)[expectedCursor]?1F:0F,v);
          break;
        case BYTE:
          Assertions.assertEquals(((byte[])expectedArr)[expectedCursor],v);
          break;
        case CHAR:
          Assertions.assertEquals(((char[])expectedArr)[expectedCursor],v);
          break;
        case SHORT:
          Assertions.assertEquals(((short[])expectedArr)[expectedCursor],v);
          break;
        case INT:
          Assertions.assertEquals(((int[])expectedArr)[expectedCursor],v);
          break;
        case LONG:
          Assertions.assertEquals(((long[])expectedArr)[expectedCursor],v);
          break;
        case FLOAT:
          Assertions.assertEquals(((float[])expectedArr)[expectedCursor],v);
          break;
        default:
          throw dataType.invalid();
        }
        break;
      }
      case DOUBLE:{
        final var v=(double)output;
        switch(dataType){
        case BOOLEAN:
          Assertions.assertEquals(((boolean[])expectedArr)[expectedCursor]?1D:0D,v);
          break;
        case BYTE:
          Assertions.assertEquals(((byte[])expectedArr)[expectedCursor],v);
          break;
        case CHAR:
          Assertions.assertEquals(((char[])expectedArr)[expectedCursor],v);
          break;
        case SHORT:
          Assertions.assertEquals(((short[])expectedArr)[expectedCursor],v);
          break;
        case INT:
          Assertions.assertEquals(((int[])expectedArr)[expectedCursor],v);
          break;
        case LONG:
          Assertions.assertEquals(((long[])expectedArr)[expectedCursor],v);
          break;
        case FLOAT:
          Assertions.assertEquals(((float[])expectedArr)[expectedCursor],v);
          break;
        case DOUBLE:
          Assertions.assertEquals(((double[])expectedArr)[expectedCursor],v);
          break;
        default:
          throw dataType.invalid();
        }
        break;
      }
      case REF:{
        switch(dataType){
        case BOOLEAN:
          Assertions.assertEquals(((boolean[])expectedArr)[expectedCursor],output);
          break;
        case BYTE:
          Assertions.assertEquals(((byte[])expectedArr)[expectedCursor],output);
          break;
        case CHAR:
          Assertions.assertEquals(((char[])expectedArr)[expectedCursor],output);
          break;
        case SHORT:
          Assertions.assertEquals(((short[])expectedArr)[expectedCursor],output);
          break;
        case INT:
          Assertions.assertEquals(((int[])expectedArr)[expectedCursor],output);
          break;
        case LONG:
          Assertions.assertEquals(((long[])expectedArr)[expectedCursor],output);
          break;
        case FLOAT:
          Assertions.assertEquals(((float[])expectedArr)[expectedCursor],output);
          break;
        case DOUBLE:
          Assertions.assertEquals(((double[])expectedArr)[expectedCursor],output);
          break;
        case REF:
          Assertions.assertSame(((Object[])expectedArr)[expectedCursor],output);
          break;
        default:
          throw dataType.invalid();
        }
        break;
      }
      }
    }
    @Override public void verifyRemoveIf(boolean result,MonitoredRemoveIfPredicate filter){
      Assertions.assertNotEquals(result,filter.removedVals.isEmpty());
      Assertions.assertNotEquals(result,filter.numRemoved == 0);
      if(expectedTail == -1){
        Assertions.assertFalse(result);
        Assertions.assertTrue(filter.retainedVals.isEmpty());
        Assertions.assertEquals(0,filter.numRetained);
        Assertions.assertEquals(0,filter.numCalls);
      }else{
        if(dataType == DataType.BOOLEAN){
          if(expectedTail < expectedHead){
            verifyBooleanFragmentedRemoveIf(result,filter);
          }else{
            verifyBooleanNonfragmentedRemoveIf(result,filter);
          }
        }else{
          verifyNonBooleanRemoveIf(result,filter);
        }
      }
    }
    private void verifyBooleanFragmentedRemoveIf(boolean result,MonitoredRemoveIfPredicate filter){
      final int head=expectedHead;
      final int tail=expectedTail;
      final int expectedCapacity=this.expectedCapacity;
      final var expectedArr=(boolean[])this.expectedArr;
      int trueCount=0;
      int size;
      for(int i=tail;;--i){
        if(expectedArr[i]){
          ++trueCount;
        }
        if(i == 0){
          for(i=(size=expectedCapacity) - 1;;--i){
            if(expectedArr[i]){
              ++trueCount;
            }
            if(i == head){
              break;
            }
          }
          size+=tail + 1 - head;
          break;
        }
      }
      verifyBooleanRemoveIfHelper(result,filter,expectedArr,trueCount,size);
    }
    private void verifyBooleanNonfragmentedRemoveIf(boolean result,MonitoredRemoveIfPredicate filter){
      final int head=expectedHead;
      final int tail=expectedTail;
      final var expectedArr=(boolean[])this.expectedArr;
      int trueCount=0;
      for(int i=head;;++i){
        if(expectedArr[i]){
          ++trueCount;
        }
        if(i == tail){
          break;
        }
      }
      verifyBooleanRemoveIfHelper(result,filter,expectedArr,trueCount,tail - head + 1);
    }
    private void verifyBooleanRemoveIfHelper(boolean result,MonitoredRemoveIfPredicate filter,
        final boolean[] expectedArr,int trueCount,int size){
      if(trueCount == size){
        Assertions.assertFalse(filter.removedVals.contains(Boolean.FALSE));
        Assertions.assertFalse(filter.retainedVals.contains(Boolean.FALSE));
        Assertions.assertEquals(1,filter.numCalls);
        if(filter.removedVals.contains(Boolean.TRUE)){
          Assertions.assertTrue(result);
          expectedTail=-1;
          expectedSize=0;
          ++expectedModCount;
        }else{
          Assertions.assertFalse(result);
        }
      }else if(trueCount == 0){
        Assertions.assertFalse(filter.removedVals.contains(Boolean.TRUE));
        Assertions.assertFalse(filter.retainedVals.contains(Boolean.TRUE));
        Assertions.assertEquals(1,filter.numCalls);
        if(filter.removedVals.contains(Boolean.FALSE)){
          Assertions.assertTrue(result);
          expectedTail=-1;
          expectedSize=0;
          ++expectedModCount;
        }else{
          Assertions.assertFalse(result);
        }
      }else{
        Assertions.assertEquals(2,filter.numCalls);
        if(filter.removedVals.contains(Boolean.TRUE)){
          Assertions.assertTrue(result);
          ++expectedModCount;
          if(filter.removedVals.contains(Boolean.FALSE)){
            expectedTail=-1;
            expectedSize=0;
            Assertions.assertTrue(filter.retainedVals.isEmpty());
            Assertions.assertEquals(0,filter.numRetained);
            Assertions.assertEquals(2,filter.numRemoved);
          }else{
            for(int i=size-=trueCount + 1;;--i){
              expectedArr[i]=false;
              if(i == 0){
                break;
              }
            }
            expectedHead=0;
            expectedTail=size;
            expectedSize=size + 1;
            Assertions.assertTrue(filter.retainedVals.contains(Boolean.FALSE));
            Assertions.assertFalse(filter.retainedVals.contains(Boolean.TRUE));
            Assertions.assertEquals(1,filter.numRetained);
            Assertions.assertEquals(1,filter.numRemoved);
          }
        }else{
          if(filter.removedVals.contains(Boolean.FALSE)){
            Assertions.assertTrue(result);
            ++expectedModCount;
            for(int i=--trueCount;;--i){
              expectedArr[i]=true;
              if(i == 0){
                break;
              }
            }
            expectedHead=0;
            expectedTail=trueCount;
            expectedSize=trueCount + 1;
            Assertions.assertTrue(filter.retainedVals.contains(Boolean.TRUE));
            Assertions.assertFalse(filter.retainedVals.contains(Boolean.FALSE));
            Assertions.assertEquals(1,filter.numRetained);
            Assertions.assertEquals(1,filter.numRemoved);
          }else{
            Assertions.assertFalse(result);
            Assertions.assertEquals(2,filter.numRetained);
            Assertions.assertEquals(0,filter.numRemoved);
            Assertions.assertTrue(filter.removedVals.isEmpty());
            Assertions.assertTrue(filter.retainedVals.contains(Boolean.TRUE));
            Assertions.assertTrue(filter.retainedVals.contains(Boolean.FALSE));
          }
        }
      }
    }
    private void verifyNonBooleanRemoveIf(boolean result,MonitoredRemoveIfPredicate filter){
      if(result){
        for(final var removedVal:filter.removedVals){
          Assertions.assertFalse(seq.contains(removedVal));
        }
      }else{
        Assertions.assertEquals(expectedSize,filter.numRetained);
      }
      final var itr=seq.iterator();
      while(itr.hasNext()){
        Assertions.assertTrue(filter.retainedVals.contains(itr.next()));
      }
      updateCollectionState();
    }
    @SuppressWarnings("unchecked") @Override OmniDeque<E> initDeq(){
      final var checked=checkedType.checked;
      switch(dataType){
      case BOOLEAN:
        if(checked){
          return (OmniDeque<E>)new BooleanArrDeq.Checked();
        }else{
          return (OmniDeque<E>)new BooleanArrDeq();
        }
      case BYTE:
        if(checked){
          return (OmniDeque<E>)new ByteArrDeq.Checked();
        }else{
          return (OmniDeque<E>)new ByteArrDeq();
        }
      case CHAR:
        if(checked){
          return (OmniDeque<E>)new CharArrDeq.Checked();
        }else{
          return (OmniDeque<E>)new CharArrDeq();
        }
      case DOUBLE:
        if(checked){
          return (OmniDeque<E>)new DoubleArrDeq.Checked();
        }else{
          return (OmniDeque<E>)new DoubleArrDeq();
        }
      case FLOAT:
        if(checked){
          return (OmniDeque<E>)new FloatArrDeq.Checked();
        }else{
          return (OmniDeque<E>)new FloatArrDeq();
        }
      case INT:
        if(checked){
          return (OmniDeque<E>)new IntArrDeq.Checked();
        }else{
          return (OmniDeque<E>)new IntArrDeq();
        }
      case LONG:
        if(checked){
          return (OmniDeque<E>)new LongArrDeq.Checked();
        }else{
          return (OmniDeque<E>)new LongArrDeq();
        }
      case REF:
        if(checked){
          return new RefArrDeq.Checked<>();
        }else{
          return new RefArrDeq<>();
        }
      case SHORT:
        if(checked){
          return (OmniDeque<E>)new ShortArrDeq.Checked();
        }else{
          return (OmniDeque<E>)new ShortArrDeq();
        }
      default:
        throw dataType.invalid();
      }
    }
    @SuppressWarnings("unchecked") @Override OmniDeque<E> initDeq(int initCap){
      final var checked=checkedType.checked;
      switch(dataType){
      case BOOLEAN:
        if(checked){
          return (OmniDeque<E>)new BooleanArrDeq.Checked(initCap);
        }else{
          return (OmniDeque<E>)new BooleanArrDeq(initCap);
        }
      case BYTE:
        if(checked){
          return (OmniDeque<E>)new ByteArrDeq.Checked(initCap);
        }else{
          return (OmniDeque<E>)new ByteArrDeq(initCap);
        }
      case CHAR:
        if(checked){
          return (OmniDeque<E>)new CharArrDeq.Checked(initCap);
        }else{
          return (OmniDeque<E>)new CharArrDeq(initCap);
        }
      case DOUBLE:
        if(checked){
          return (OmniDeque<E>)new DoubleArrDeq.Checked(initCap);
        }else{
          return (OmniDeque<E>)new DoubleArrDeq(initCap);
        }
      case FLOAT:
        if(checked){
          return (OmniDeque<E>)new FloatArrDeq.Checked(initCap);
        }else{
          return (OmniDeque<E>)new FloatArrDeq(initCap);
        }
      case INT:
        if(checked){
          return (OmniDeque<E>)new IntArrDeq.Checked(initCap);
        }else{
          return (OmniDeque<E>)new IntArrDeq(initCap);
        }
      case LONG:
        if(checked){
          return (OmniDeque<E>)new LongArrDeq.Checked(initCap);
        }else{
          return (OmniDeque<E>)new LongArrDeq(initCap);
        }
      case REF:
        if(checked){
          return new RefArrDeq.Checked<>(initCap);
        }else{
          return new RefArrDeq<>(initCap);
        }
      case SHORT:
        if(checked){
          return (OmniDeque<E>)new ShortArrDeq.Checked(initCap);
        }else{
          return (OmniDeque<E>)new ShortArrDeq(initCap);
        }
      default:
        throw dataType.invalid();
      }
    }
  }
  private static interface BasicTest{
    private void runAllTests(String testName){
      for(final var checkedType:CheckedType.values()){
        for(final var collectionType:DataType.values()){
          for(int tmpSize=0;tmpSize <= 100;tmpSize+=5){
            final int size=tmpSize;
            final int interval=Math.max(1,size / 10);
            final int rotateBound=size / 2 + interval;
            for(int tmpInitCap=0,initCapBound=size + interval;tmpInitCap <= initCapBound;tmpInitCap+=interval){
              final int initCap=tmpInitCap;
              for(int tmpNumToRotate=0;tmpNumToRotate <= rotateBound;tmpNumToRotate+=interval){
                final int numToRotate=tmpNumToRotate;
                TestExecutorService.submitTest(()->{
                  final var monitor=SequenceInitialization.Ascending
                      .initialize(new ArrDeqMonitor<>(checkedType,collectionType,initCap),size,0);
                  monitor.rotate(numToRotate);
                  runTest(monitor);
                });
              }
            }
          }
        }
      }
      TestExecutorService.completeAllTests(testName);
    }
    void runTest(ArrDeqMonitor<?> monitor);
  }
  private static interface GetTest{
    private void runAllTests(String testName,boolean throwsOnEmpty){
      for(final var collectionType:DataType.values()){
        for(final var outputType:collectionType.validOutputTypes()){
          for(final var checkedType:CheckedType.values()){
            for(int tmpInitCap=0;tmpInitCap <= 105;tmpInitCap+=5){
              final int initCap=tmpInitCap;
              for(int tmpNumToRotate=0;tmpNumToRotate <= 50;tmpNumToRotate+=5){
                final int numToRotate=tmpNumToRotate;
                TestExecutorService.submitTest(()->{
                  final var monitor=SequenceInitialization.Ascending
                      .initialize(new ArrDeqMonitor<>(checkedType,collectionType,initCap),100,0);
                  if(numToRotate > 0){
                    monitor.rotate(numToRotate);
                  }
                  for(int i=0;i < 100;++i){
                    processNext(monitor,outputType);
                  }
                  if(throwsOnEmpty){
                    if(checkedType.checked){
                      Assertions.assertThrows(NoSuchElementException.class,()->processNext(monitor,outputType));
                    }
                  }else{
                    processNext(monitor,outputType);
                  }
                });
              }
            }
          }
        }
      }
      TestExecutorService.completeAllTests(testName);
    }
    void processNext(ArrDeqMonitor<?> monitor,DataType outputType);
  }
  private static interface MonitoredFunctionTest{
    private void runAllTests(String testName,long maxRand){
      for(final var checkedType:CheckedType.values()){
        for(final var collectionType:DataType.values()){
          for(final var size:SIZES){
            final int interval=Math.max(1,size / 10);
            final int rotateBound=size / 2 + interval;
            final int initValBound=collectionType == DataType.BOOLEAN && size != 0?1:0;
            for(int tmpInitCap=0,initCapBound=size + interval;tmpInitCap <= initCapBound;tmpInitCap+=interval){
              final int initCap=tmpInitCap;
              for(int tmpNumToRotate=0;tmpNumToRotate <= rotateBound;tmpNumToRotate+=interval){
                final int numToRotate=tmpNumToRotate;
                for(final var functionGen:StructType.ArrDeq.validMonitoredFunctionGens){
                  if(functionGen.expectedException == null || checkedType.checked){
                    for(final var functionCallType:collectionType.validFunctionCalls){
                      final long randSeedBound=size > 1 && functionGen.randomized && !functionCallType.boxed?maxRand:0;
                      for(long tmpRandSeed=0;tmpRandSeed <= randSeedBound;++tmpRandSeed){
                        final long randSeed=tmpRandSeed;
                        for(int tmpInitVal=0;tmpInitVal <= initValBound;++tmpInitVal){
                          final int initVal=tmpInitVal;
                          TestExecutorService.submitTest(()->{
                            final var monitor=SequenceInitialization.Ascending
                                .initialize(new ArrDeqMonitor<>(checkedType,collectionType,initCap),size,initVal);
                            if(size > 0 && numToRotate > 0){
                              monitor.rotate(numToRotate);
                            }
                            runTest(monitor,functionGen,functionCallType,randSeed);
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
    void runTest(ArrDeqMonitor<?> monitor,MonitoredFunctionGen functionGen,FunctionCallType functionCallType,
        long randSeed);
  }
  private static interface QueryTest{
    private void runAllTests(String testName){
      for(final var collectionType:DataType.values()){
        for(final var queryVal:QueryVal.values()){
          if(collectionType.isValidQueryVal(queryVal)){
            queryVal.validQueryCombos.forEach((modification,castTypesToInputTypes)->{
              castTypesToInputTypes.forEach((castType,inputTypes)->{
                for(final var inputType:inputTypes){
                  final boolean queryCanReturnTrue
                      =queryVal.queryCanReturnTrue(modification,castType,inputType,collectionType);
                  for(final var checkedType:CheckedType.values()){
                    for(final var size:SHORT_SIZES){
                      final int interval=1;
                      for(int tmpInitCap=size,initCapBound=size + interval;tmpInitCap <= initCapBound;
                          tmpInitCap+=interval){
                        final int initCap=tmpInitCap;
                        for(int tmpNumToRotate=0;tmpNumToRotate <= initCapBound;tmpNumToRotate+=interval){
                          final int numToRotate=tmpNumToRotate;
                          if(queryVal == QueryVal.NonNull){
                            for(final var monitoredObjectGen:StructType.ArrDeq.validMonitoredObjectGens){
                              if(size != 0 && monitoredObjectGen.expectedException != null && checkedType.checked){
                                TestExecutorService.submitTest(()->{
                                  Assertions.assertThrows(monitoredObjectGen.expectedException,
                                      ()->runTest(new ArrDeqMonitor<>(checkedType,collectionType,initCap),queryVal,
                                          modification,castType,inputType,size,monitoredObjectGen,size == 0?-1:0,
                                          numToRotate));
                                });
                              }
                            }
                          }else{
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
                              TestExecutorService.submitTest(()->{
                                runTest(new ArrDeqMonitor<>(checkedType,collectionType,initCap),queryVal,modification,
                                    castType,inputType,size,null,position,numToRotate);
                              });
                            }
                          }
                        }
                      }
                    }
                  }
                }
              });
            });
          }
        }
      }
      TestExecutorService.completeAllTests(testName);
    }
    @SuppressWarnings("unchecked") private void runTest(ArrDeqMonitor<?> monitor,QueryVal queryVal,
        QueryVal.QueryValModification modification,QueryCastType castType,DataType inputType,int size,
        MonitoredObjectGen monitoredObjectGen,double position,int numToRotate){
      if(position < 0){
        switch(monitor.dataType){
        case BOOLEAN:
          queryVal.initDoesNotContain((OmniCollection.OfBoolean)monitor.getCollection(),size,0,modification);
          break;
        case BYTE:
          queryVal.initDoesNotContain((OmniCollection.OfByte)monitor.getCollection(),size,0,modification);
          break;
        case CHAR:
          queryVal.initDoesNotContain((OmniCollection.OfChar)monitor.getCollection(),size,0,modification);
          break;
        case DOUBLE:
          queryVal.initDoesNotContain((OmniCollection.OfDouble)monitor.getCollection(),size,0,modification);
          break;
        case FLOAT:
          queryVal.initDoesNotContain((OmniCollection.OfFloat)monitor.getCollection(),size,0,modification);
          break;
        case INT:
          queryVal.initDoesNotContain((OmniCollection.OfInt)monitor.getCollection(),size,0,modification);
          break;
        case LONG:
          queryVal.initDoesNotContain((OmniCollection.OfLong)monitor.getCollection(),size,0,modification);
          break;
        case REF:
          queryVal.initDoesNotContain((OmniCollection.OfRef<Object>)monitor.getCollection(),size,0,modification);
          break;
        case SHORT:
          queryVal.initDoesNotContain((OmniCollection.OfShort)monitor.getCollection(),size,0,modification);
          break;
        default:
          throw monitor.dataType.invalid();
        }
      }else{
        switch(monitor.dataType){
        case BOOLEAN:
          queryVal.initContains((OmniCollection.OfBoolean)monitor.getCollection(),size,0,position,modification);
          break;
        case BYTE:
          queryVal.initContains((OmniCollection.OfByte)monitor.getCollection(),size,0,position,modification);
          break;
        case CHAR:
          queryVal.initContains((OmniCollection.OfChar)monitor.getCollection(),size,0,position,modification);
          break;
        case DOUBLE:
          queryVal.initContains((OmniCollection.OfDouble)monitor.getCollection(),size,0,position,modification);
          break;
        case FLOAT:
          queryVal.initContains((OmniCollection.OfFloat)monitor.getCollection(),size,0,position,modification);
          break;
        case INT:
          queryVal.initContains((OmniCollection.OfInt)monitor.getCollection(),size,0,position,modification);
          break;
        case LONG:
          queryVal.initContains((OmniCollection.OfLong)monitor.getCollection(),size,0,position,modification);
          break;
        case REF:
          queryVal.initContains((MonitoredCollection<? extends OmniCollection.OfRef<Object>>)monitor,size,0,position,
              modification,inputType,monitoredObjectGen);
          break;
        case SHORT:
          queryVal.initContains((OmniCollection.OfShort)monitor.getCollection(),size,0,position,modification);
          break;
        default:
          throw monitor.dataType.invalid();
        }
      }
      monitor.updateCollectionState();
      monitor.rotate(numToRotate);
      callAndVerifyResult(monitor,queryVal,modification,castType,inputType,size,monitoredObjectGen,position);
    }
    void callAndVerifyResult(ArrDeqMonitor<?> monitor,QueryVal queryVal,QueryVal.QueryValModification modification,
        QueryCastType castType,DataType inputType,int size,MonitoredObjectGen monitoredObjectGen,double position);
  }
  private static interface ToStringAndHashCodeTest{
    private void runAllTests(String testName){
      for(final var size:SIZES){
        final int interval=Math.max(1,size / 10);
        final int rotateBound=size / 2 + interval;
        for(int tmpInitCap=0,initCapBound=size + interval;tmpInitCap <= initCapBound;tmpInitCap+=interval){
          final int initCap=tmpInitCap;
          for(int tmpNumToRotate=0;tmpNumToRotate <= rotateBound;tmpNumToRotate+=interval){
            final int numToRotate=tmpNumToRotate;
            for(final var collectionType:DataType.values()){
              final int initValBound=collectionType == DataType.BOOLEAN && size > 0?1:0;
              for(final var checkedType:CheckedType.values()){
                if(collectionType == DataType.REF){
                  for(final var objGen:StructType.ArrDeq.validMonitoredObjectGens){
                    if(checkedType.checked || size == 0 || objGen.expectedException == null){
                      TestExecutorService.submitTest(()->{
                        if(size == 0 || objGen.expectedException == null){
                          final var monitor=SequenceInitialization.Ascending
                              .initialize(new ArrDeqMonitor<>(checkedType,collectionType,initCap),size,0);
                          if(size > 0 && numToRotate > 0){
                            monitor.rotate(numToRotate);
                          }
                          callVerify(monitor);
                        }else{
                          final var throwSwitch=new MonitoredObjectGen.ThrowSwitch();
                          final var monitor=SequenceInitialization.Ascending.initializeWithMonitoredObj(
                              new ArrDeqMonitor<>(checkedType,collectionType,initCap),size,0,objGen,throwSwitch);
                          if(size > 0 && numToRotate > 0){
                            monitor.rotate(numToRotate);
                          }
                          Assertions.assertThrows(objGen.expectedException,()->{
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
                }else{
                  for(int tmpInitVal=0;tmpInitVal <= initValBound;++tmpInitVal){
                    final int initVal=tmpInitVal;
                    TestExecutorService.submitTest(()->{
                      final var monitor=SequenceInitialization.Ascending
                          .initialize(new ArrDeqMonitor<>(checkedType,collectionType,initCap),size,initVal);
                      if(size > 0 && numToRotate > 0){
                        monitor.rotate(numToRotate);
                      }
                      callVerify(monitor);
                    });
                  }
                }
              }
            }
          }
        }
      }
      TestExecutorService.completeAllTests(testName);
    }
    void callRaw(OmniDeque<?> seq);
    void callVerify(ArrDeqMonitor<?> monitor);
  }
  private static final int[] REMOVE_IF_SIZES=new int[66 + 8 * 5 + 2];
  private static final int[] SHORT_SIZES=new int[]{0,1,2,3,4,5,6,7,8,9,10};
  private static final double[] RANDOM_THRESHOLDS=new double[]{0.01,0.10,0.90};
  private static final double[] NON_RANDOM_THRESHOLD=new double[]{0.5};
  private static final double[] POSITIONS=new double[]{-1,0,0.25,0.5,0.75,1.0};
  private static final int[] SIZES=new int[]{0,1,2,3,4,5,6,7,8,9,10,15,20,30,40,50,60,70,80,90,100};
  static{
    for(int i=0;i < 67;++i){
      REMOVE_IF_SIZES[i]=i;
    }
    int index=67;
    for(int j=2;j < 10;++j){
      for(int i=-2;i <= 2;++i){
        REMOVE_IF_SIZES[index++]=j * 64 + i;
      }
    }
    REMOVE_IF_SIZES[REMOVE_IF_SIZES.length - 1]=16384;
  }
  @Order(6072) @Test public void testadd_val(){
    final AddTest test=(monitor,inputVal,inputType,functionCallType)->Assertions
        .assertTrue(monitor.verifyAdd(inputVal,inputType,functionCallType));
    test.runAllTests("ArrDeqTest.testadd_val");
  }
  @Order(6072) @Test public void testaddFirst_val(){
    final AddTest test=ArrDeqMonitor::verifyAddFirst;
    test.runAllTests("ArrDeqTest.testaddFirst_val");
  }
  @Order(6072) @Test public void testaddLast_val(){
    final AddTest test=ArrDeqMonitor::verifyAddLast;
    test.runAllTests("ArrDeqTest.testaddLast_val");
  }
  @Order(31428) @Test public void testclear_void(){
    final BasicTest test=ArrDeqMonitor::verifyClear;
    test.runAllTests("ArrDeqTest.testclear_void");
  }
  @Order(31428) @Test public void testclone_void(){
    final BasicTest test=ArrDeqMonitor::verifyClone;
    test.runAllTests("ArrDeqTest.testclone_void");
  }
  @Order(72) @Test public void testConstructor_int(){
    for(final var checkedType:CheckedType.values()){
      for(final var collectionType:DataType.values()){
        for(int tmpInitCap=0;tmpInitCap <= 15;tmpInitCap+=5){
          final int initCap=tmpInitCap;
          TestExecutorService.submitTest(()->{
            new ArrDeqMonitor<>(checkedType,collectionType,initCap).verifyCollectionState();
          });
        }
      }
    }
    TestExecutorService.completeAllTests("ArrDeqTest.testConstructor_int");
  }
  @Order(18) @Test public void testConstructor_void(){
    for(final var checkedType:CheckedType.values()){
      for(final var collectionType:DataType.values()){
        TestExecutorService.submitTest(()->{
          new ArrDeqMonitor<>(checkedType,collectionType).verifyCollectionState();
        });
      }
    }
    TestExecutorService.completeAllTests("ArrDeqTest.testConstructor_void");
  }
  @Order(5012622) @Test public void testcontains_val(){
    final QueryTest test=(monitor,queryVal,modification,castType,inputType,size,monitoredObjectGen,position)->{
      if(monitoredObjectGen == null){
        Assertions.assertEquals(position >= 0,monitor.verifyContains(queryVal,inputType,castType,modification));
      }else{
        monitor.verifyThrowingContains(monitoredObjectGen);
      }
    };
    test.runAllTests("ArrDeqTest.testcontains_val");
  }
  @Order(31428) @Test public void testdescendingIterator_void(){
    final BasicTest test=monitor->{
      monitor.getMonitoredDescendingIterator().verifyIteratorState();
      monitor.verifyCollectionState();
    };
    test.runAllTests("ArrDeqTest.testdescendingIterator_void");
  }
  @Order(20812) @Test public void testelement_void(){
    final GetTest test=(monitor,outputType)->{
      monitor.verifyElement(outputType);
      if(!monitor.isEmpty()){
        monitor.removeFirst();
      }
    };
    test.runAllTests("ArrDeqTest.testelement_void",true);
  }
  @Order(31428) @Test public void testequals_Object(){
      final BasicTest test=(monitor)->Assertions.assertFalse(monitor.getCollection().equals(null));
      test.runAllTests("ArrDeqTest.testequals_Object");
  }
  @Order(3971795) @Test public void testforEach_Consumer(){
    final MonitoredFunctionTest test=(monitor,functionGen,functionCallType,randSeed)->{
      if(functionGen.expectedException == null || monitor.isEmpty()){
        monitor.verifyForEach(functionGen,functionCallType,randSeed);
      }else{
        Assertions.assertThrows(functionGen.expectedException,
            ()->monitor.verifyForEach(functionGen,functionCallType,randSeed));
      }
    };
    test.runAllTests("ArrDeqTest.testforEach_Consumer",100);
  }
  @Order(20812) @Test public void testgetFirst_void(){
    final GetTest test=(monitor,outputType)->{
      monitor.verifyGetFirst(outputType);
      if(!monitor.isEmpty()){
        monitor.removeFirst();
      }
    };
    test.runAllTests("ArrDeqTest.testgetFirst_void",true);
  }
  @Order(20812) @Test public void testgetLast_void(){
    final GetTest test=(monitor,outputType)->{
      monitor.verifyGetLast(outputType);
      if(!monitor.isEmpty()){
        monitor.removeLast();
      }
    };
    test.runAllTests("ArrDeqTest.testgetLast_void",true);
  }
  @Order(29743) @Test public void testhashCode_void(){
    final ToStringAndHashCodeTest test=new ToStringAndHashCodeTest(){
      @Override public void callRaw(OmniDeque<?> seq){
        seq.hashCode();
      }
      @Override public void callVerify(ArrDeqMonitor<?> monitor){
        monitor.verifyHashCode();
      }
    };
    test.runAllTests("ArrDeqTst.testhashCode_void");
  }
  @Order(31428) @Test public void testisEmpty_void(){
    final BasicTest test=ArrDeqMonitor::verifyIsEmpty;
    test.runAllTests("ArrDeqTest.testisEmpty_void");
  }
  @Order(31428) @Test public void testiterator_void(){
    final BasicTest test=monitor->{
      monitor.getMonitoredIterator().verifyIteratorState();
      monitor.verifyCollectionState();
    };
    test.runAllTests("ArrDeqTest.testiterator_void");
  }
  @Order(31428) @Test public void testItrclone_void(){
    final BasicTest test=monitor->{
      final int size=monitor.size();
      for(final var itrType:StructType.ArrDeq.validItrTypes){
        for(final var position:POSITIONS){
          if(position >= 0){
            monitor.getMonitoredIterator((int)(position * size),itrType).verifyClone();
          }
        }
      }
    };
    test.runAllTests("ArrDeqTest.testItrclone_void");
  }
  @Order(13471054) @Test public void testItrforEachRemaining_Consumer(){
    for(final var size:SHORT_SIZES){
      final int interval=Math.max(1,size / 10);
      final int rotateBound=size / 2 + interval;
      final int initCapBound=size + interval;
      int prevNumToIterate=-1;
      for(final var position:POSITIONS){
        int numToIterate;
        if(position >= 0 && (numToIterate=(int)(position * size)) != prevNumToIterate){
          prevNumToIterate=numToIterate;
          final int numLeft=size - numToIterate;
          for(final var collectionType:DataType.values()){
            for(final var functionCallType:collectionType.validFunctionCalls){
              for(final var itrType:StructType.ArrDeq.validItrTypes){
                for(final var illegalMod:itrType.validPreMods){
                  for(final var checkedType:CheckedType.values()){
                    if(illegalMod.expectedException == null || checkedType.checked){
                      for(final var functionGen:itrType.validMonitoredFunctionGens){
                        if(checkedType.checked || size == 0 || functionGen.expectedException == null){
                          final long randSeedBound=!functionCallType.boxed && numLeft > 1 && functionGen.randomized
                              && illegalMod.expectedException == null?100:0;
                          for(var tmpRandSeed=0;tmpRandSeed <= randSeedBound;++tmpRandSeed){
                            final long randSeed=tmpRandSeed;
                            for(var tmpNumToRotate=0;tmpNumToRotate <= rotateBound;tmpNumToRotate+=interval){
                              final int numToRotate=tmpNumToRotate;
                              for(int tmpInitCap=0;tmpInitCap <= initCapBound;tmpInitCap+=interval){
                                final int initCap=tmpInitCap;
                                TestExecutorService.submitTest(()->{
                                  final var seqMonitor=SequenceInitialization.Ascending
                                      .initialize(new ArrDeqMonitor<>(checkedType,collectionType,initCap),size,0);
                                  seqMonitor.rotate(numToRotate);
                                  final var itrMonitor=seqMonitor.getMonitoredIterator(numToIterate,itrType);
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
      }
    }
    TestExecutorService.completeAllTests("ArrDeqTest.testItrforEachRemaining_Consumer");
  }
  @Order(31428) @Test public void testItrhasNext_void(){
    final BasicTest test=monitor->{
      for(final var itrType:StructType.ArrDeq.validItrTypes){
        final var itrMonitor=monitor.getMonitoredIterator(itrType);
        while(itrMonitor.verifyHasNext()){
          itrMonitor.iterateForward();
        }
      }
    };
    test.runAllTests("ArrDeqTest.testItrhasNext_void");
  }
  @Order(778042) @Test public void testItrnext_void(){
    for(final var size:SIZES){
      final int interval=Math.max(1,size / 10);
      final int rotateBound=size / 2 + interval;
      final int initCapBound=size + interval;
      for(var tmpNumToRotate=0;tmpNumToRotate <= rotateBound;tmpNumToRotate+=interval){
        final int numToRotate=tmpNumToRotate;
        for(var tmpInitCap=0;tmpInitCap <= initCapBound;tmpInitCap+=interval){
          final int initCap=tmpInitCap;
          for(final var itrType:StructType.ArrDeq.validItrTypes){
            for(final var illegalMod:itrType.validPreMods){
              for(final var checkedType:CheckedType.values()){
                if(checkedType.checked || size > 0 && illegalMod.expectedException == null){
                  for(final var collectionType:DataType.values()){
                    for(final var outputType:collectionType.validOutputTypes()){
                      if(illegalMod.expectedException == null){
                        TestExecutorService.submitTest(()->{
                          final var seqMonitor=SequenceInitialization.Ascending
                              .initialize(new ArrDeqMonitor<>(checkedType,collectionType,initCap),size,0);
                          seqMonitor.rotate(numToRotate);
                          final var itrMonitor=seqMonitor.getMonitoredIterator(itrType);
                          while(itrMonitor.hasNext()){
                            itrMonitor.verifyNext(outputType);
                          }
                          if(checkedType.checked){
                            Assertions.assertThrows(NoSuchElementException.class,()->itrMonitor.verifyNext(outputType));
                          }
                        });
                      }else{
                        for(final var position:POSITIONS){
                          if(position >= 0){
                            TestExecutorService.submitTest(()->{
                              final var seqMonitor=SequenceInitialization.Ascending
                                  .initialize(new ArrDeqMonitor<>(checkedType,collectionType,initCap),size,0);
                              if(size > 0 && numToRotate > 0){
                                seqMonitor.rotate(numToRotate);
                              }
                              final int index=(int)(size * position);
                              final var itrMonitor=seqMonitor.getMonitoredIterator(index,itrType);
                              itrMonitor.illegalMod(illegalMod);
                              Assertions.assertThrows(illegalMod.expectedException,
                                  ()->itrMonitor.verifyNext(outputType));
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
    TestExecutorService.completeAllTests("ArrDeqTest.testItrnext_void");
  }
  @Order(551862) @Test public void testItrremove_void(){
    for(final var size:SIZES){
      final int interval=Math.max(1,size / 10);
      final int rotateBound=size / 2 + interval;
      final int initCapBound=size + interval;
      int prevNumToIterate=-1;
      for(final var position:POSITIONS){
        final int numToIterate;
        if(position >= 0 && (numToIterate=(int)(size * position)) != prevNumToIterate){
          prevNumToIterate=numToIterate;
          for(var tmpInitCap=0;tmpInitCap <= initCapBound;tmpInitCap+=interval){
            final int initCap=tmpInitCap;
            for(var tmpNumToRotate=0;tmpNumToRotate <= rotateBound;tmpNumToRotate+=interval){
              final int numToRotate=tmpNumToRotate;
              for(final var checkedType:CheckedType.values()){
                for(final var collectionType:DataType.values()){
                  for(final var itrType:StructType.ArrDeq.validItrTypes){
                    for(final var illegalMod:itrType.validPreMods){
                      if(illegalMod.expectedException == null || checkedType.checked){
                        for(final var removeScenario:itrType.validItrRemoveScenarios){
                          if(removeScenario.expectedException == null || checkedType.checked){
                            switch(removeScenario){
                            case PostInit:
                              if(numToIterate != 0){
                                continue;
                              }
                              break;
                            case PostNext:
                              if(size == 0 || numToIterate == size){
                                continue;
                              }
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
                                  .initialize(new ArrDeqMonitor<>(checkedType,collectionType,initCap),size,0);
                              seqMonitor.rotate(numToRotate);
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
                                    Assertions.assertEquals(numToIterate < 2,seqMonitor.isEmpty());
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
        }
      }
    }
    TestExecutorService.completeAllTests("ArrDeqTest.testItrremove_void");
  }
  @Order(Integer.MAX_VALUE) @Test public void testMASSIVEtoString(){
    for(final var collectionType:DataType.values()){
      int seqSize;
      if((seqSize=collectionType.massiveToStringThreshold + 1) == 0){
        continue;
      }
      OmniDeque<?> checkedNonFragmented;
      OmniDeque<?> uncheckedNonFragmented;
      OmniDeque<?> checkedFragmented;
      OmniDeque<?> uncheckedFragmented;
      switch(collectionType){
      case BOOLEAN:{
        boolean[] arr;
        checkedNonFragmented=new BooleanArrDeq.Checked(0,arr=new boolean[seqSize],seqSize - 1);
        uncheckedNonFragmented=new BooleanArrDeq(0,arr,seqSize - 1);
        checkedFragmented=new BooleanArrDeq.Checked((seqSize >> 1) + 1,arr,seqSize >> 1);
        uncheckedFragmented=new BooleanArrDeq((seqSize >> 1) + 1,arr,seqSize >> 1);
        for(int i=0;i < seqSize;++i){
          arr[i]=true;
        }
        break;
      }
      case BYTE:{
        byte[] arr;
        checkedNonFragmented=new ByteArrDeq.Checked(0,arr=new byte[seqSize],seqSize - 1);
        uncheckedNonFragmented=new ByteArrDeq(0,arr,seqSize - 1);
        checkedFragmented=new ByteArrDeq.Checked((seqSize >> 1) + 1,arr,seqSize >> 1);
        uncheckedFragmented=new ByteArrDeq((seqSize >> 1) + 1,arr,seqSize >> 1);
        break;
      }
      case SHORT:{
        short[] arr;
        checkedNonFragmented=new ShortArrDeq.Checked(0,arr=new short[seqSize],seqSize - 1);
        uncheckedNonFragmented=new ShortArrDeq(0,arr,seqSize - 1);
        checkedFragmented=new ShortArrDeq.Checked((seqSize >> 1) + 1,arr,seqSize >> 1);
        uncheckedFragmented=new ShortArrDeq((seqSize >> 1) + 1,arr,seqSize >> 1);
        break;
      }
      case INT:{
        int[] arr;
        checkedNonFragmented=new IntArrDeq.Checked(0,arr=new int[seqSize],seqSize - 1);
        uncheckedNonFragmented=new IntArrDeq(0,arr,seqSize - 1);
        checkedFragmented=new IntArrDeq.Checked((seqSize >> 1) + 1,arr,seqSize >> 1);
        uncheckedFragmented=new IntArrDeq((seqSize >> 1) + 1,arr,seqSize >> 1);
        break;
      }
      case LONG:{
        long[] arr;
        checkedNonFragmented=new LongArrDeq.Checked(0,arr=new long[seqSize],seqSize - 1);
        uncheckedNonFragmented=new LongArrDeq(0,arr,seqSize - 1);
        checkedFragmented=new LongArrDeq.Checked((seqSize >> 1) + 1,arr,seqSize >> 1);
        uncheckedFragmented=new LongArrDeq((seqSize >> 1) + 1,arr,seqSize >> 1);
        break;
      }
      case FLOAT:{
        float[] arr;
        checkedNonFragmented=new FloatArrDeq.Checked(0,arr=new float[seqSize],seqSize - 1);
        uncheckedNonFragmented=new FloatArrDeq(0,arr,seqSize - 1);
        checkedFragmented=new FloatArrDeq.Checked((seqSize >> 1) + 1,arr,seqSize >> 1);
        uncheckedFragmented=new FloatArrDeq((seqSize >> 1) + 1,arr,seqSize >> 1);
        break;
      }
      default:
        throw collectionType.invalid();
      }
      collectionType.verifyMASSIVEToString(checkedNonFragmented.toString(),seqSize,
          collectionType.classPrefix + "ArrDeqTest.Checked.NonFragmented.testMassiveToString");
      collectionType.verifyMASSIVEToString(uncheckedNonFragmented.toString(),seqSize,
          collectionType.classPrefix + "ArrDeqTest.NonFragmented.testMassiveToString");
      collectionType.verifyMASSIVEToString(checkedFragmented.toString(),seqSize,
          collectionType.classPrefix + "ArrDeqTest.Checked.Fragmented.testMassiveToString");
      collectionType.verifyMASSIVEToString(uncheckedFragmented.toString(),seqSize,
          collectionType.classPrefix + "ArrDeqTest.Fragmented.testMassiveToString");
    }
  }
  @Order(6072) @Test public void testoffer_val(){
    final AddTest test=(monitor,inputVal,inputType,functionCallType)->Assertions
        .assertTrue(monitor.verifyOffer(inputVal,inputType,functionCallType));
    test.runAllTests("ArrDeqTest.testoffer_val");
  }
  @Order(6072) @Test public void testofferFirst_val(){
    final AddTest test=(monitor,inputVal,inputType,functionCallType)->Assertions
        .assertTrue(monitor.verifyOfferFirst(inputVal,inputType,functionCallType));
    test.runAllTests("ArrDeqTest.testofferFirst_val");
  }
  @Order(6072) @Test public void testofferLast_val(){
    final AddTest test=(monitor,inputVal,inputType,functionCallType)->Assertions
        .assertTrue(monitor.verifyOfferLast(inputVal,inputType,functionCallType));
    test.runAllTests("ArrDeqTest.testofferLast_val");
  }
  @Order(20812) @Test public void testpeek_void(){
    final GetTest test=(monitor,outputType)->{
      monitor.verifyPeek(outputType);
      if(!monitor.isEmpty()){
        monitor.removeFirst();
      }
    };
    test.runAllTests("ArrDeqTest.testpeek_void",false);
  }
  @Order(20812) @Test public void testpeekFirst_void(){
    final GetTest test=(monitor,outputType)->{
      monitor.verifyPeekFirst(outputType);
      if(!monitor.isEmpty()){
        monitor.removeFirst();
      }
    };
    test.runAllTests("ArrDeqTest.testpeekFirst_void",false);
  }
  @Order(20812) @Test public void testpeekLast_void(){
    final GetTest test=(monitor,outputType)->{
      monitor.verifyPeekLast(outputType);
      if(!monitor.isEmpty()){
        monitor.removeLast();
      }
    };
    test.runAllTests("ArrDeqTest.testpeekLast_void",false);
  }
  @Order(20812) @Test public void testpoll_void(){
    final GetTest test=ArrDeqMonitor::verifyPoll;
    test.runAllTests("ArrDeqTest.testpoll_void",false);
  }
  @Order(20812) @Test public void testpollFirst_void(){
    final GetTest test=ArrDeqMonitor::verifyPollFirst;
    test.runAllTests("ArrDeqTest.testpollFirst_void",false);
  }
  @Order(20812) @Test public void testpollLast_void(){
    final GetTest test=ArrDeqMonitor::verifyPollLast;
    test.runAllTests("ArrDeqTest.testpollLast_void",false);
  }
  @Order(20812) @Test public void testpop_void(){
    final GetTest test=ArrDeqMonitor::verifyPop;
    test.runAllTests("ArrDeqTest.testpop_void",true);
  }
  @Order(6072) @Test public void testpush_val(){
    final AddTest test=ArrDeqMonitor::verifyPush;
    test.runAllTests("ArrDeqTest.testpush_val");
  }
  @Order(122795) @Test public void testReadAndWrite(){
    final MonitoredFunctionTest test=(monitor,functionGen,functionCallType,randSeed)->{
      if(functionGen.expectedException == null){
        Assertions.assertDoesNotThrow(()->monitor.verifyReadAndWrite(functionGen));
      }else{
        Assertions.assertThrows(functionGen.expectedException,()->monitor.verifyReadAndWrite(functionGen));
      }
    };
    test.runAllTests("ArrDeqTest.testReadAndWrite",0);
  }
  @Order(20812) @Test public void testremove_void(){
    final GetTest test=ArrDeqMonitor::verifyRemove;
    test.runAllTests("ArrDeqTest.testremove_void",true);
  }
  @Order(20812) @Test public void testremoveFirst_void(){
    final GetTest test=ArrDeqMonitor::verifyRemoveFirst;
    test.runAllTests("ArrDeqTest.testremoveFirst_void",true);
  }
  @Order(5012622) @Test public void testremoveFirstOccurrence_val(){
    final QueryTest test=(monitor,queryVal,modification,castType,inputType,size,monitoredObjectGen,position)->{
      if(monitoredObjectGen == null){
        Assertions.assertEquals(position >= 0,
            monitor.verifyRemoveFirstOccurrence(queryVal,inputType,castType,modification));
      }else{
        monitor.verifyThrowingRemoveFirstOccurrence(monitoredObjectGen);
      }
    };
    test.runAllTests("ArrDeqTest.testremoveFirstOccurrence_val");
  }
  @Order(12353670) @Test public void testremoveIf_Predicate(){
    for(final var collectionType:DataType.values()){
      for(final var checkedType:CheckedType.values()){
        for(final var filterGen:StructType.ArrDeq.validMonitoredRemoveIfPredicateGens){
          if(filterGen.expectedException == null || checkedType.checked){
            for(final var functionCallType:collectionType.validFunctionCalls){
              int[] sizes;
              if(collectionType == DataType.BOOLEAN){
                sizes=SHORT_SIZES;
              }else{
                sizes=REMOVE_IF_SIZES;
              }
              for(final var size:sizes){
                int periodBound;
                int initValBound;
                int periodInc;
                int periodOffset;
                if(collectionType == DataType.BOOLEAN && size > 0){
                  periodOffset=0;
                  periodBound=size;
                  initValBound=1;
                  periodInc=Math.max(1,size / 10);
                }else{
                  periodOffset=0;
                  periodBound=Math.max(1,size / 128);
                  initValBound=0;
                  periodInc=Math.max(1,size / 256);
                }
                final int rotateInc=Math.max(1,size / 4);
                if(functionCallType == FunctionCallType.Boxed && size > 2){
                  continue;
                }
                if((filterGen.expectedException != null
                    || filterGen.predicateGenCallType != MonitoredRemoveIfPredicateGen.PredicateGenCallType.Randomized
                        && !checkedType.checked)
                    && size > 126){
                  continue;
                }
                double[] thresholdArr;
                long randSeedBound;
                if(size == 0
                    || filterGen.predicateGenCallType != MonitoredRemoveIfPredicateGen.PredicateGenCallType.Randomized
                    || functionCallType == FunctionCallType.Boxed){
                  thresholdArr=NON_RANDOM_THRESHOLD;
                  randSeedBound=0;
                }else{
                  thresholdArr=RANDOM_THRESHOLDS;
                  randSeedBound=100;
                }
                for(long tmpRandSeed=0;tmpRandSeed <= randSeedBound;++tmpRandSeed){
                  if(functionCallType == FunctionCallType.Boxed && tmpRandSeed > 0){
                    break;
                  }
                  final long randSeed=tmpRandSeed;
                  for(final double threshold:thresholdArr){
                    for(int tmpNumToRotate=0;tmpNumToRotate <= size;tmpNumToRotate+=rotateInc){
                      if(functionCallType == FunctionCallType.Boxed && tmpNumToRotate > 1){
                        break;
                      }
                      final int numToRotate=tmpNumToRotate;
                      for(var tmpInitVal=0;tmpInitVal <= initValBound;++tmpInitVal){
                        final int initVal=tmpInitVal;
                        for(var tmpPeriod=periodOffset;tmpPeriod <= periodBound;tmpPeriod+=periodInc){
                          final int period=tmpPeriod;
                          TestExecutorService.submitTest(()->{
                            final var monitor=SequenceInitialization.Ascending
                                .initialize(new ArrDeqMonitor<>(checkedType,collectionType,size),size,initVal,period);
                            monitor.rotate(numToRotate);
                            final var filter
                                =filterGen.getMonitoredRemoveIfPredicate(monitor,threshold,new Random(randSeed));
                            if(filterGen.expectedException == null || size == 0){
                              monitor.verifyRemoveIf(filter,functionCallType);
                            }else{
                              Assertions.assertThrows(filterGen.expectedException,
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
    TestExecutorService.completeAllTests("ArrDeqTest.testremoveIf_Predicate");
  }
  @Order(20812) @Test public void testremoveLast_void(){
    final GetTest test=ArrDeqMonitor::verifyRemoveLast;
    test.runAllTests("ArrDeqTest.testremoveLast_void",true);
  }
  @Order(5012622) @Test public void testremoveLastOccurrence_val(){
    final QueryTest test=(monitor,queryVal,modification,castType,inputType,size,monitoredObjectGen,position)->{
      if(monitoredObjectGen == null){
        Assertions.assertEquals(position >= 0,
            monitor.verifyRemoveLastOccurrence(queryVal,inputType,castType,modification));
      }else{
        monitor.verifyThrowingRemoveLastOccurrence(monitoredObjectGen);
      }
    };
    test.runAllTests("ArrDeqTest.testremoveLastOccurrence_val");
  }
  @Order(5012622) @Test public void testremoveVal_val(){
    final QueryTest test=(monitor,queryVal,modification,castType,inputType,size,monitoredObjectGen,position)->{
      if(monitoredObjectGen == null){
        Assertions.assertEquals(position >= 0,monitor.verifyRemoveVal(queryVal,inputType,castType,modification));
      }else{
        monitor.verifyThrowingRemoveVal(monitoredObjectGen);
      }
    };
    test.runAllTests("ArrDeqTest.testremoveVal_val");
  }
  @Order(5012622) @Test public void testsearch_val(){
    final QueryTest test=(monitor,queryVal,modification,castType,inputType,size,monitoredObjectGen,position)->{
      if(monitoredObjectGen == null){
        int expectedIndex;
        if(position >= 0){
          expectedIndex=1 + (int)Math.round(position * size);
        }else{
          expectedIndex=-1;
        }
        Assertions.assertEquals(expectedIndex,monitor.verifySearch(queryVal,inputType,castType,modification));
      }else{
        monitor.verifyThrowingSearch(monitoredObjectGen);
      }
    };
    test.runAllTests("ArrDeqTest.testsearch_val");
  }
  @Order(31428) @Test public void testsize_void(){
    final BasicTest test=ArrDeqMonitor::verifySize;
    test.runAllTests("ArrDeqTest.testsize_void");
  }
  @Order(122795) @Test public void testtoArray_IntFunction(){
    final MonitoredFunctionTest test=(monitor,functionGen,functionCallType,randSeed)->{
      if(functionGen.expectedException == null){
        monitor.verifyToArray(functionGen);
      }else{
        Assertions.assertThrows(functionGen.expectedException,()->monitor.verifyToArray(functionGen));
      }
    };
    test.runAllTests("ArrDeqTest.testtoArray_IntFunction",0);
  }
  @Order(31428) @Test public void testtoArray_ObjectArray(){
    final BasicTest test=(monitor)->{
      final int size=monitor.size();
      final int inc=Math.max(1,size / 10);
      for(int paramArrLength=0,bound=size + inc;paramArrLength <= bound;paramArrLength+=inc){
        monitor.verifyToArray(new Object[paramArrLength]);
      }
    };
    test.runAllTests("ArrDeqTest.testtoArray_ObjectArray");
  }
  @Order(31428) @Test public void testtoArray_void(){
    final BasicTest test=(monitor)->{
      for(final var outputType:monitor.dataType.validOutputTypes()){
        outputType.verifyToArray(monitor);
      }
    };
    test.runAllTests("ArrDeqTst.testtoArray_void");
  }
  @Order(29743) @Test public void testtoString_void(){
    final ToStringAndHashCodeTest test=new ToStringAndHashCodeTest(){
      @Override public void callRaw(OmniDeque<?> seq){
        seq.toString();
      }
      @Override public void callVerify(ArrDeqMonitor<?> monitor){
        monitor.verifyToString();
      }
    };
    test.runAllTests("ArrDeqTst.testtoString_void");
  }
  @org.junit.jupiter.api.AfterEach public void verifyAllExecuted(){
    int numTestsRemaining;
    if((numTestsRemaining=TestExecutorService.getNumRemainingTasks()) != 0){
      System.err.println("Warning: there were " + numTestsRemaining + " tests that were not completed");
    }
    TestExecutorService.reset();
  }
}
