package omni.impl.seq;

import java.util.NoSuchElementException;
import java.util.Random;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.api.OmniCollection;
import omni.api.OmniIterator;
import omni.impl.CheckedType;
import omni.impl.DataType;
import omni.impl.FunctionCallType;
import omni.impl.IteratorType;
import omni.impl.MonitoredFunction;
import omni.impl.MonitoredFunctionGen;
import omni.impl.MonitoredRemoveIfPredicate;
import omni.impl.MonitoredRemoveIfPredicateGen;
import omni.impl.QueryCastType;
import omni.impl.QueryVal;
import omni.impl.StructType;
import omni.util.BitSetUtil;
import omni.util.OmniArray;
import omni.util.TestExecutorService;

public class PackedBooleanArrDeqTest{
  private static final int[] SIZES=new int[]{0,1,2,3,4,5,6,7,8,9,10,15,20,30,40,50,60,70,80,90,100};
  private static final int[] SHORT_SIZES=new int[]{0,1,2,3,4,5,6,7,8,9,10};
  private static final double[] POSITIONS=new double[]{-1,0,0.25,0.5,0.75,1.0};
  private static final double[] NON_RANDOM_THRESHOLD=new double[]{0.5};
  private static final double[] RANDOM_THRESHOLDS=new double[]{0.01,0.10,0.90};


  private static class PackedBooleanArrDeqMonitor extends AbstractArrDeqMonitor<PackedBooleanArrDeq,Boolean>{
    private abstract class AbstractItrMonitor extends AbstractArrDeqMonitor<PackedBooleanArrDeq,Boolean>.AbstractItrMonitor{
      AbstractItrMonitor(OmniIterator<?> itr,int expectedCursor,int numLeft){
        super(itr,expectedCursor,numLeft);
      }
      @Override
    IntConsumer getForEachRemainingVerifier(MonitoredFunction function){
        final var functionItr=function.iterator();

        final var expectedArr=(long[])PackedBooleanArrDeqMonitor.this.expectedArr;
        return index->Assertions.assertEquals((boolean)functionItr.next(),BitSetUtil.getFromPackedArr(expectedArr,index));
       
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
            break;
          case 0:
            final int arrBound=expectedCapacity - 1;
            int head=expectedHead;
            final int headDist=arrBound - head;
            final var arr=(long[])expectedArr;
            if(tail < headDist){
              BitSetUtil.uncheckedCopy(arr,0,arr,arrBound,1);
              BitSetUtil.semicheckedCopy(arr,1,arr,0,tail);
              BitSetUtil.semicheckedCopy(arr,1,arr,0,tail);
              expectedTail=tail == 0?arrBound:tail - 1;
              expectedCursor=arrBound;
            }else{
              BitSetUtil.semicheckedCopy(arr,head,arr,++head,headDist);
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
          Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.PackedBooleanArrDeq.AbstractDeqItr.cursor(itr));
          if(checkedType.checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.PackedBooleanArrDeq.Checked.AscendingItr.root(itr));
            Assertions.assertEquals(expectedItrModCount,
                FieldAndMethodAccessor.PackedBooleanArrDeq.Checked.AscendingItr.modCount(itr));
            Assertions.assertEquals(expectedLastRet,
                FieldAndMethodAccessor.PackedBooleanArrDeq.Checked.AscendingItr.lastRet(itr));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.PackedBooleanArrDeq.AscendingItr.root(itr));
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
        final var arr=(long[])expectedArr;
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
            }else{
              BitSetUtil.uncheckedCopy(arr,tail=head,arr,++head,headDist);
              expectedHead=head;
            }
          }else{
            BitSetUtil.semicheckedCopy(arr,lastRet + 1,arr,lastRet,tailDist);
            BitSetUtil.uncheckedCopy(arr,0,arr,arrBound,1);
            BitSetUtil.semicheckedCopy(arr,1,arr,0,tail);
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
              BitSetUtil.uncheckedCopy(arr,lastRet + 1,arr,lastRet,tailDist);
              expectedTail=tail - 1;
              expectedCursor=lastRet;
            }
          }else{
            BitSetUtil.semicheckedCopy(arr,0,arr,1,lastRet);
            BitSetUtil.uncheckedCopy(arr,arrBound,arr,0,1);
            BitSetUtil.semicheckedCopy(arr,head,arr,++head,headDist);
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
        final var arr=(long[])expectedArr;
        if(headDist <= tailDist){
          BitSetUtil.semicheckedCopy(arr,head,arr,++head,headDist);
          expectedHead=head;
        }else{
          BitSetUtil.semicheckedCopy(arr,lastRet + 1,arr,lastRet,tailDist);
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
        final var arr=(long[])expectedArr;
        int headDist=expectedLastRet - head;
        if(headDist >= 0){
          final int tailDist=arrBound - lastRet;
          if(headDist <= tailDist + tail + 1){
            BitSetUtil.semicheckedCopy(arr,head,arr,++head,headDist);
            expectedHead=head;
          }else{
            BitSetUtil.semicheckedCopy(arr,lastRet + 1,arr,lastRet,tailDist);
            BitSetUtil.uncheckedCopy(arr,0,arr,arrBound,1);
            BitSetUtil.semicheckedCopy(arr,1,arr,0,tail);
            expectedTail=tail == 0?arrBound:tail - 1;
            expectedCursor=lastRet;
          }
        }else{
          final int tailDist=tail - lastRet;
          headDist=arrBound - head;
          if(tailDist <= headDist + lastRet + 1){
            BitSetUtil.semicheckedCopy(arr,lastRet + 1,arr,lastRet,tailDist);
            expectedTail=tail - 1;
            expectedCursor=lastRet;
          }else{
            BitSetUtil.semicheckedCopy(arr,0,arr,1,lastRet);
            BitSetUtil.uncheckedCopy(arr,arrBound,arr,0,1);
            BitSetUtil.semicheckedCopy(arr,head,arr,++head,headDist);
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
          Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.PackedBooleanArrDeq.AbstractDeqItr.cursor(itr));
          if(checkedType.checked){
            Assertions.assertSame(seq,FieldAndMethodAccessor.PackedBooleanArrDeq.Checked.DescendingItr.root(itr));
            Assertions.assertEquals(expectedItrModCount,
                FieldAndMethodAccessor.PackedBooleanArrDeq.Checked.DescendingItr.modCount(itr));
            Assertions.assertEquals(expectedLastRet,
                FieldAndMethodAccessor.PackedBooleanArrDeq.Checked.DescendingItr.lastRet(itr));
          }else{
            Assertions.assertSame(seq,FieldAndMethodAccessor.PackedBooleanArrDeq.DescendingItr.root(itr));
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
        final var arr=(long[])expectedArr;
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
            }else{
              BitSetUtil.uncheckedCopy(arr,tail=head,arr,++head,headDist);
              expectedHead=head;
              expectedCursor=lastRet;
            }
          }else{
            BitSetUtil.semicheckedCopy(arr,lastRet + 1,arr,lastRet,tailDist);
            BitSetUtil.uncheckedCopy(arr,0,arr,arrBound,1);
            BitSetUtil.semicheckedCopy(arr,1,arr,0,tail);
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
              BitSetUtil.uncheckedCopy(arr,lastRet + 1,arr,lastRet,tailDist);
              expectedTail=tail - 1;
            }
          }else{
            BitSetUtil.semicheckedCopy(arr,0,arr,1,lastRet);
            BitSetUtil.uncheckedCopy(arr,arrBound,arr,0,1);
            BitSetUtil.semicheckedCopy(arr,head,arr,++head,headDist);
            expectedHead=headDist == 0?0:head;
            expectedCursor=lastRet;
          }
        }
      }
      private void nonfragmentedRemove(){
        final int tail=expectedTail;
        int head=expectedHead;
        final var arr=(long[])expectedArr;
        final int lastRet=expectedLastRet;
        final int headDist=lastRet - head;
        final int tailDist=tail - lastRet;
        if(tailDist <= headDist){
          BitSetUtil.semicheckedCopy(arr,lastRet + 1,arr,lastRet,tailDist);
          expectedTail=tail - 1;
        }else{
          BitSetUtil.semicheckedCopy(arr,head,arr,++head,headDist);
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
        final var arr=(long[])expectedArr;
        int cursor=expectedCursor;
        if(arrBound == cursor){
          if(tail <= (cursor=arrBound - head) + 1){
            BitSetUtil.semicheckedCopy(arr,1,arr,0,tail);
            expectedTail=tail == 0?arrBound:tail - 1;
          }else{
            BitSetUtil.uncheckedCopy(arr,arrBound,arr,0,1);
            BitSetUtil.semicheckedCopy(arr,head,arr,++head,cursor);
            expectedHead=cursor == 0?0:head;
            expectedCursor=0;
          }
        }else{
          int headDist=++cursor - head;
          if(headDist > 0){
            final int tailDist=arrBound - cursor;
            if(headDist <= tailDist + tail + 1){
              BitSetUtil.uncheckedCopy(arr,tail=head,arr,++head,headDist);
              expectedHead=head;
              expectedCursor=cursor;
            }else{
              BitSetUtil.semicheckedCopy(arr,cursor + 1,arr,cursor,tailDist);
              BitSetUtil.uncheckedCopy(arr,0,arr,arrBound,1);
              BitSetUtil.semicheckedCopy(arr,1,arr,0,tail);
              expectedTail=tail == 0?arrBound:tail - 1;
            }
          }else{
            final int tailDist=tail - cursor;
            headDist=arrBound - head;
            if(tailDist <= headDist + cursor + 1){
              BitSetUtil.semicheckedCopy(arr,cursor + 1,arr,cursor,tailDist);
              expectedTail=tail - 1;
            }else{
              BitSetUtil.semicheckedCopy(arr,0,arr,1,cursor);
              BitSetUtil.uncheckedCopy(arr,arrBound,arr,0,1);
              BitSetUtil.semicheckedCopy(arr,head,arr,++head,headDist);
              expectedHead=headDist == 0?0:head;
              expectedCursor=cursor;
            }
          }
        }
      }
    }
    PackedBooleanArrDeqMonitor(CheckedType checkedType){
      super(checkedType,DataType.BOOLEAN);
    }

    PackedBooleanArrDeqMonitor(CheckedType checkedType,int capacity){
      super(checkedType,DataType.BOOLEAN,capacity);
    }

    @Override public Object get(int iterationIndex,DataType outputType){
      iterationIndex+=expectedHead;
      if(iterationIndex >= expectedCapacity){
        iterationIndex-=expectedCapacity;
      }
      return outputType.convertVal(BitSetUtil.getFromPackedArr((long[])expectedArr,iterationIndex));
    }
    
    @Override public MonitoredIterator<?,PackedBooleanArrDeq> getMonitoredDescendingIterator(){
      return new DescendingItrMonitor();
    }

    @Override public MonitoredIterator<? extends OmniIterator<?>,PackedBooleanArrDeq> getMonitoredIterator(){
      return new AscendingItrMonitor();
    }

    @Override public StructType getStructType(){
      return StructType.PackedBooleanArrDeq;
    }

    @Override public void modCollection(){
      ++((PackedBooleanArrDeq.Checked)seq).modCount;
      ++expectedModCount;
    }
    
    @Override public void updateAddFirstState(Object inputVal,DataType inputType){
        if(expectedSize == 0){
            if(expectedCapacity == 0){
              expectedArr=new long[BitSetUtil.getPackedCapacity(expectedCapacity=64)];
            }
            expectedHead=expectedCapacity - 1;
            expectedTail=expectedCapacity - 1;
            BitSetUtil.storeInPackedArr((long[])expectedArr,expectedCapacity - 1,(boolean)inputVal);
          }else{
            int head;
            int tail;
            if((tail=expectedTail) == (head=expectedHead - 1)){
              long[] newArr;
              int newCap,size;
              expectedTail=(newCap=OmniArray.growBy50Pct(head + (size=expectedCapacity))) - 1;
              expectedCapacity=newCap;
              newArr=new long[BitSetUtil.getPackedCapacity(newCap)];
              BitSetUtil.semicheckedCopy((long[])expectedArr,0,newArr,newCap-=++tail,tail);
              BitSetUtil.semicheckedCopy((long[])expectedArr,head + 1,newArr,head=newCap - (size-=tail),size);
              expectedArr=newArr;
              --head;
            }else if(head == -1 && tail == (head=expectedCapacity - 1)){
              int newCap;
              expectedTail=(newCap=OmniArray.growBy50Pct(++tail)) - 1;
              expectedCapacity=newCap;
              BitSetUtil.semicheckedCopy((long[])expectedArr,0,(long[])(expectedArr=new long[BitSetUtil.getPackedCapacity(newCap)]),head=newCap - tail,tail);
              --head;
            }
            BitSetUtil.storeInPackedArr((long[])expectedArr,head,(boolean)inputVal);
            expectedHead=head;
          }
          ++expectedModCount;
          ++expectedSize;
        }

    @Override public void updateAddState(Object inputVal,DataType inputType){
        if(expectedSize == 0){
            if(expectedCapacity == 0){

                expectedArr=new long[BitSetUtil.getPackedCapacity(expectedCapacity=64)];
            }
            expectedHead=0;
            expectedTail=0;
            BitSetUtil.storeInPackedArr((long[])expectedArr,0,(boolean)inputVal);
          }else{
            int head;
            int tail;
            if((tail=expectedTail + 1) == (head=expectedHead)){
              expectedHead=0;
              tail=expectedCapacity;
              final long[] newArr=new long[BitSetUtil.getPackedCapacity(expectedCapacity=OmniArray.growBy50Pct(tail))];
              BitSetUtil.storeInPackedArr(newArr,tail,(boolean)inputVal);
              expectedTail=tail;
              BitSetUtil.semicheckedCopy((long[])expectedArr,head,newArr,0,tail-=head);
              BitSetUtil.semicheckedCopy((long[])expectedArr,0,newArr,tail,head);
              expectedArr=newArr;
            }else{
              if(tail == expectedCapacity){
                if(head == 0){
                 BitSetUtil.semicheckedCopy((long[])expectedArr,0,(long[])(expectedArr=new long[BitSetUtil.getPackedCapacity(expectedCapacity=OmniArray.growBy50Pct(tail))]),0,tail);
                }else{
                  tail=0;
                }
              }
              BitSetUtil.storeInPackedArr((long[])expectedArr,tail,(boolean)inputVal);
              expectedTail=tail;
            }
          }
          ++expectedModCount;
          ++expectedSize;
        }

    @Override public void updateCollectionState(){
        long[] actualArr;
        int actualCapacity;
        final var checked=checkedType.checked;
        final var cast=seq;
        expectedHead=cast.head;
        expectedTail=cast.tail;
        actualArr=cast.words;
        actualCapacity=cast.words == null?0:cast.words.length<<6;
        if(checked){
          expectedModCount=((PackedBooleanArrDeq.Checked)cast).modCount;
        }
        if(actualArr == null){
            expectedArr=actualArr;
            expectedCapacity=0;
            expectedSize=0;
          }else if(expectedCapacity != actualCapacity){
            expectedCapacity=actualCapacity;
            expectedArr=new long[BitSetUtil.getPackedCapacity(actualCapacity)];
            if(expectedTail == -1){
              expectedSize=0;
            }else if(expectedTail < expectedHead){
              BitSetUtil.uncheckedCopy(actualArr,expectedHead,(long[])expectedArr,expectedHead,actualCapacity - expectedHead);
              BitSetUtil.uncheckedCopy(actualArr,0,(long[])expectedArr,0,expectedTail + 1);
              expectedSize=actualCapacity - expectedHead + expectedTail + 1;
            }else{
              BitSetUtil.semicheckedCopy(actualArr,expectedHead,(long[])expectedArr,expectedHead,
                  expectedSize=expectedTail + 1 - expectedHead);
             
            }
          }else if(expectedTail == -1){
            expectedSize=0;
          }else if(expectedTail < expectedHead){
            BitSetUtil.uncheckedCopy(actualArr,expectedHead,(long[])expectedArr,expectedHead,actualCapacity - expectedHead);
            BitSetUtil.uncheckedCopy(actualArr,0,(long[])expectedArr,0,expectedTail + 1);
            expectedSize=actualCapacity - expectedHead + expectedTail + 1;
          }else{
              BitSetUtil.semicheckedCopy(actualArr,expectedHead,(long[])expectedArr,expectedHead,expectedSize=expectedTail + 1 - expectedHead);
          }
    }
    private static IntPredicate getArrayIndexSearcher(long[] arr,Object inputVal,DataType inputType) {
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
        return index->(arr[index>>6]>>>index&1)==inputCast;
    }
    @Override public void updateRemoveLastOccurrenceState(Object inputVal,DataType inputType){
        final int head=expectedHead;
        int tail=expectedTail;
        final IntPredicate indexSearcher=getArrayIndexSearcher((long[])expectedArr,inputVal,inputType);
        ++expectedModCount;
        --expectedSize;
        if(tail < head){
          int index,bound;
          for(index=tail,bound=expectedCapacity - 1;index >= 0;--index){
            if(indexSearcher.test(index)){
              int headDist,tailDist;
              if((headDist=bound - head) + index + 1 < (tailDist=tail - index)){
                BitSetUtil.semicheckedCopy((long[])expectedArr,0,(long[])expectedArr,1,index);
                BitSetUtil.uncheckedCopy((long[])expectedArr,bound,(long[])expectedArr,0,1);
                BitSetUtil.semicheckedCopy((long[])expectedArr,head,(long[])expectedArr,tail=head + 1,headDist);
     
                expectedHead=tail > bound?0:tail;
              }else{
                  BitSetUtil.semicheckedCopy((long[])expectedArr,index + 1,(long[])expectedArr,index,tailDist);
 
                expectedTail=--tail == -1?bound:tail;
              }
              return;
            }
          }
          for(index=bound;;--index){
            if(indexSearcher.test(index)){
              int headDist,tailDist;
              if((headDist=index - head) <= (tailDist=bound - index) + tail + 1){
                  BitSetUtil.semicheckedCopy((long[])expectedArr,head,(long[])expectedArr,tail=head + 1,headDist);

                expectedHead=tail > bound?0:tail;
              }else{
                  BitSetUtil.semicheckedCopy((long[])expectedArr,index + 1,(long[])expectedArr,index,tailDist);
                  BitSetUtil.uncheckedCopy((long[])expectedArr,0,(long[])expectedArr,bound,1);
                BitSetUtil.semicheckedCopy((long[])expectedArr,1,(long[])expectedArr,0,tail);
   
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
                    BitSetUtil.semicheckedCopy((long[])expectedArr,index + 1,(long[])expectedArr,index,tailDist);
                  expectedTail=tail - 1;
                }
              }else{
                  BitSetUtil.semicheckedCopy((long[])expectedArr,head,(long[])expectedArr,tail=head + 1,headDist);
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
        final IntPredicate indexSearcher=getArrayIndexSearcher((long[])expectedArr,inputVal,inputType);
        ++expectedModCount;
        --expectedSize;
        if(tail < head){
          int index,bound;
          for(index=head,bound=expectedCapacity - 1;index <= bound;++index){
            if(indexSearcher.test(index)){
              int headDist,tailDist;
              if((headDist=index - head) <= (tailDist=bound - index) + tail){
                BitSetUtil.semicheckedCopy((long[])expectedArr,head,(long[])expectedArr,tail=head + 1,headDist);
                expectedHead=tail > bound?0:tail;
              }else{
                  BitSetUtil.semicheckedCopy((long[])expectedArr,index + 1,(long[])expectedArr,index,tailDist);
                  BitSetUtil.uncheckedCopy((long[])expectedArr,0,(long[])expectedArr,bound,1);
                BitSetUtil.semicheckedCopy((long[])expectedArr,1,(long[])expectedArr,0,tail);
                expectedTail=--tail == -1?bound:tail;
              }
              return;
            }
          }
          for(index=0;;++index){
            if(indexSearcher.test(index)){
              int headDist,tailDist;
              if((headDist=bound - head) + index + 1 < (tailDist=tail - index)){
                  BitSetUtil.semicheckedCopy((long[])expectedArr,0,(long[])expectedArr,1,index);
                  BitSetUtil.uncheckedCopy((long[])expectedArr,bound,(long[])expectedArr,0,1);
                BitSetUtil.semicheckedCopy((long[])expectedArr,head,(long[])expectedArr,tail=head + 1,headDist);
                expectedHead=tail > bound?0:tail;
              }else{
                  BitSetUtil.semicheckedCopy((long[])expectedArr,index + 1,(long[])expectedArr,index,tailDist);
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
                    BitSetUtil.semicheckedCopy((long[])expectedArr,index + 1,(long[])expectedArr,index,tailDist);
                  expectedTail=tail - 1;
                }
              }else{
                  BitSetUtil.semicheckedCopy((long[])expectedArr,head,(long[])expectedArr,tail=head + 1,headDist);
                expectedHead=tail;
              }
              return;
            }
          }
        }
      }

    @Override public void verifyArrayIsCopy(Object arr,boolean emptyArrayMayBeSame){
      
      if(!(arr instanceof long[])) {
          return;
      }
      long[] expectedArr=seq.words;
      Assertions.assertNotSame(expectedArr,arr);
    }

    @Override public void verifyClone(Object clone){
        final var checked=checkedType.checked;
        int cloneHead;
        int cloneTail;
        int cloneCapacity;
        int thisHead;
        int thisTail;
        long[] cloneArr;
        CloneVerifier cloneVerifier;

          final var cloneCast=(PackedBooleanArrDeq)clone;
          final var thisCast=seq;
          thisHead=thisCast.head;
          thisTail=thisCast.tail;
          cloneHead=cloneCast.head;
          cloneTail=cloneCast.tail;
          final var cloneCastArr=cloneCast.words;
          cloneCapacity=cloneCastArr == null?0:cloneCastArr.length<<6;
          cloneArr=cloneCastArr;
          Assertions.assertEquals(checked,cloneCast instanceof PackedBooleanArrDeq.Checked);
          if(checked){
            Assertions.assertEquals(0,((PackedBooleanArrDeq.Checked)cloneCast).modCount);
          }
          final var thisArr=(long[])expectedArr;
          cloneVerifier=(thisIndex,cloneIndex)->Assertions.assertEquals(BitSetUtil.getFromPackedArr(thisArr,thisIndex),BitSetUtil.getFromPackedArr(cloneArr,cloneIndex));
       
        Assertions.assertNotSame(clone,seq);
        if(expectedSize == 0){
          Assertions.assertEquals(-1,cloneTail);
          Assertions.assertEquals(0,cloneHead);
          Assertions.assertNull(cloneArr);
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

          final var castSeq=seq;
          actualHead=castSeq.head;
          actualTail=castSeq.tail;
          final var castActualArr=castSeq.words;
          actualArr=castActualArr;
          actualCapacity=castActualArr == null?0:castActualArr.length<<6;
          if(checked){
            Assertions.assertEquals(expectedModCount,((PackedBooleanArrDeq.Checked)castSeq).modCount);
          }
          final var expectedArr=(long[])this.expectedArr;
          indexVerifier=index->Assertions.assertEquals(BitSetUtil.getFromPackedArr(expectedArr,index),BitSetUtil.getFromPackedArr(castActualArr,index));
        
        Assertions.assertEquals(expectedHead,actualHead);
        Assertions.assertEquals(expectedTail,actualTail);
        Assertions.assertEquals(expectedCapacity,actualCapacity);
        Assertions.assertTrue(actualCapacity > 0 || actualArr == null);
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
        Assertions.assertEquals(BitSetUtil.getFromPackedArr((long[])expectedArr,expectedCursor),(boolean)output);
        break;
      case BYTE:{

          Assertions.assertEquals(BitSetUtil.getFromPackedArr((long[])expectedArr,expectedCursor)?(byte)1:(byte)0,(byte)output);
      
        break;
      }
      case CHAR:{

          Assertions.assertEquals(BitSetUtil.getFromPackedArr((long[])expectedArr,expectedCursor)?(char)1:(char)0,(char)output);
      
        break;
      }
      case SHORT:{

          Assertions.assertEquals(BitSetUtil.getFromPackedArr((long[])expectedArr,expectedCursor)?(short)1:(short)0,(short)output);
      
        break;
      }
      case INT:{

          Assertions.assertEquals(BitSetUtil.getFromPackedArr((long[])expectedArr,expectedCursor)?1:0,(int)output);
      
        break;
      }
      case LONG:{

          Assertions.assertEquals(BitSetUtil.getFromPackedArr((long[])expectedArr,expectedCursor)?1L:0L,(long)output);
       
        break;
      }
      case FLOAT:{

          Assertions.assertEquals(BitSetUtil.getFromPackedArr((long[])expectedArr,expectedCursor)?1F:0F,(float)output);
       
        break;
      }
      case DOUBLE:{
          Assertions.assertEquals(BitSetUtil.getFromPackedArr((long[])expectedArr,expectedCursor)?1D:0D,(double)output);
      
        break;
      }
      case REF:{
          Assertions.assertEquals(BitSetUtil.getFromPackedArr((long[])expectedArr,expectedCursor),output);
        
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
          if(expectedTail < expectedHead){
            verifyBooleanFragmentedRemoveIf(result,filter);
          }else{
            verifyBooleanNonfragmentedRemoveIf(result,filter);
          }

      }
    }
    private void verifyBooleanFragmentedRemoveIf(boolean result,MonitoredRemoveIfPredicate filter){
      final int head=expectedHead;
      final int tail=expectedTail;
      final int expectedCapacity=this.expectedCapacity;
      final var expectedArr=(long[])this.expectedArr;
      int trueCount=0;
      int size;
      for(int i=tail;;--i){
        if(BitSetUtil.getFromPackedArr(expectedArr,i)){
          ++trueCount;
        }
        if(i == 0){
          for(i=(size=expectedCapacity) - 1;;--i){
            if(BitSetUtil.getFromPackedArr(expectedArr,i)){
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
      final var expectedArr=(long[])this.expectedArr;
      int trueCount=0;
      for(int i=head;;++i){
        if(BitSetUtil.getFromPackedArr(expectedArr,i)){
          ++trueCount;
        }
        if(i == tail){
          break;
        }
      }
      verifyBooleanRemoveIfHelper(result,filter,expectedArr,trueCount,tail - head + 1);
    }
    private void verifyBooleanRemoveIfHelper(boolean result,MonitoredRemoveIfPredicate filter,
        final long[] expectedArr,int trueCount,int size){
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
              BitSetUtil.storeInPackedArr(expectedArr,i,false);
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
              BitSetUtil.storeInPackedArr(expectedArr,i,true);
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
    @Override PackedBooleanArrDeq initDeq(){
      if(checkedType.checked) {
        return new PackedBooleanArrDeq.Checked();
      }
      return new PackedBooleanArrDeq();
    }

    @Override PackedBooleanArrDeq initDeq(int capacity){
      if(checkedType.checked) {
        return new PackedBooleanArrDeq.Checked(capacity);
      }
      return new PackedBooleanArrDeq(capacity);
    }

    @Override void rotate(int numToRotate){
      if(expectedSize <= 0){ return; }
      assert expectedHead == 0;
      assert expectedTail == expectedSize - 1;
      numToRotate%=expectedCapacity;
      if(numToRotate <= 0){ return; }
      long[] actualArr;
      long[] tmp=null;
      final int newHead=-numToRotate + expectedCapacity;
      int newTail=newHead + expectedSize - 1;
      if(newTail >= expectedCapacity){
        newTail-=expectedCapacity;
      }
      final int overflow=Math.min(numToRotate - (expectedCapacity - expectedSize),expectedSize - numToRotate);
      final var seq=this.seq;
      actualArr=seq.words;
      seq.head=newHead;
      seq.tail=newTail;
      if(overflow > 0){
        tmp=new long[BitSetUtil.getPackedCapacity(overflow)];
      }
      if(tmp != null){
        if(expectedSize - numToRotate > overflow){
          BitSetUtil.uncheckedCopy(actualArr,expectedSize - overflow,tmp,0,overflow);
          BitSetUtil.uncheckedCopy(actualArr,0,actualArr,expectedCapacity - numToRotate,numToRotate);
          int tmpLength;
          BitSetUtil.semicheckedCopy(actualArr,numToRotate,actualArr,0,tmpLength=expectedSize - numToRotate - overflow);
          BitSetUtil.uncheckedCopy(tmp,0,actualArr,tmpLength,overflow);
        }else{
          BitSetUtil.uncheckedCopy(actualArr,expectedSize - overflow,tmp,0,overflow);
          BitSetUtil.uncheckedCopy(actualArr,0,actualArr,expectedCapacity - numToRotate,numToRotate);
          BitSetUtil.uncheckedCopy(tmp,0,actualArr,0,overflow);
        }
      }else{
        if(numToRotate >= expectedSize){
          BitSetUtil.semicheckedCopy(actualArr,0,actualArr,newHead,expectedSize);
        }else{
          BitSetUtil.uncheckedCopy(actualArr,0,actualArr,expectedCapacity - numToRotate,numToRotate);
          BitSetUtil.semicheckedCopy(actualArr,numToRotate,actualArr,0,expectedSize - numToRotate);
        }
      }
      expectedHead=newHead;
      expectedTail=newTail;
      BitSetUtil.uncheckedCopy(actualArr,0,(long[])expectedArr,0,expectedCapacity);
    }
    
    
    
  }
  private static interface AddTest{
    private void runAllTests(String testName){
      for(final var checkedType:CheckedType.values()){
        for(final var functionCallType:DataType.BOOLEAN.validFunctionCalls){
          for(int tmpInitCap=0;tmpInitCap <= 15;tmpInitCap+=5){
            final int initCap=tmpInitCap;
            for(int tmpNumToRotate=0;tmpNumToRotate <= 10;++tmpNumToRotate){
              final int numToRotate=tmpNumToRotate;
              TestExecutorService.submitTest(()->{
                final var monitor=new PackedBooleanArrDeqMonitor(checkedType,initCap);
                if(numToRotate != 0){
                  monitor.add(0);
                  monitor.rotate(numToRotate);
                }
                for(int i=0;i < 100;++i){
                  callMethod(monitor,DataType.BOOLEAN.convertValUnchecked(i),DataType.BOOLEAN,functionCallType);
                }
              });
            }
          }
        }
      }
      TestExecutorService.completeAllTests(testName);
    }
    void callMethod(PackedBooleanArrDeqMonitor monitor,Object inputVal,DataType inputType,FunctionCallType functionCallType);
  }
  private static interface BasicTest{
    private void runAllTests(String testName){
      for(final var checkedType:CheckedType.values()){
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
                    .initialize(new PackedBooleanArrDeqMonitor(checkedType,initCap),size,0);
                monitor.rotate(numToRotate);
                runTest(monitor);
              });
            }
          }
        }
      }
      TestExecutorService.completeAllTests(testName);
    }
    void runTest(PackedBooleanArrDeqMonitor monitor);
  }
  private static interface GetTest{
    private void runAllTests(String testName,boolean throwsOnEmpty){
        for(final var outputType:DataType.BOOLEAN.validOutputTypes()){
          for(final var checkedType:CheckedType.values()){
            for(int tmpInitCap=0;tmpInitCap <= 105;tmpInitCap+=5){
              final int initCap=tmpInitCap;
              for(int tmpNumToRotate=0;tmpNumToRotate <= 50;tmpNumToRotate+=5){
                final int numToRotate=tmpNumToRotate;
                TestExecutorService.submitTest(()->{
                  final var monitor=SequenceInitialization.Ascending
                      .initialize(new PackedBooleanArrDeqMonitor(checkedType,initCap),100,0);
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
      
      TestExecutorService.completeAllTests(testName);
    }
    void processNext(PackedBooleanArrDeqMonitor monitor,DataType outputType);
  }
  private static interface MonitoredFunctionTest{
    private void runAllTests(String testName,long maxRand){
      for(final var checkedType:CheckedType.values()){
        for(final var size:SIZES){
          final int interval=Math.max(1,size / 10);
          final int rotateBound=size / 2 + interval;
          final int initValBound=size != 0?1:0;
          for(int tmpInitCap=0,initCapBound=size + interval;tmpInitCap <= initCapBound;tmpInitCap+=interval){
            final int initCap=tmpInitCap;
            for(int tmpNumToRotate=0;tmpNumToRotate <= rotateBound;tmpNumToRotate+=interval){
              final int numToRotate=tmpNumToRotate;
              for(final var functionGen:StructType.ArrDeq.validMonitoredFunctionGens){
                if(functionGen.expectedException == null || checkedType.checked){
                  for(final var functionCallType:DataType.BOOLEAN.validFunctionCalls){
                    final long randSeedBound=size > 1 && functionGen.randomized && !functionCallType.boxed?maxRand:0;
                    for(long tmpRandSeed=0;tmpRandSeed <= randSeedBound;++tmpRandSeed){
                      final long randSeed=tmpRandSeed;
                      for(int tmpInitVal=0;tmpInitVal <= initValBound;++tmpInitVal){
                        final int initVal=tmpInitVal;
                        TestExecutorService.submitTest(()->{
                          final var monitor=SequenceInitialization.Ascending
                              .initialize(new PackedBooleanArrDeqMonitor(checkedType,initCap),size,initVal);
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
      TestExecutorService.completeAllTests(testName);
    }
    void runTest(PackedBooleanArrDeqMonitor monitor,MonitoredFunctionGen functionGen,FunctionCallType functionCallType,
        long randSeed);
  }
  private static interface QueryTest{
    private void runAllTests(String testName){
        for(final var queryVal:QueryVal.values()){
          if(DataType.BOOLEAN.isValidQueryVal(queryVal)){
            queryVal.validQueryCombos.forEach((modification,castTypesToInputTypes)->{
              castTypesToInputTypes.forEach((castType,inputTypes)->{
                for(final var inputType:inputTypes){
                  final boolean queryCanReturnTrue
                      =queryVal.queryCanReturnTrue(modification,castType,inputType,DataType.BOOLEAN);
                  for(final var checkedType:CheckedType.values()){
                    for(final var size:SHORT_SIZES){
                      final int interval=1;
                      for(int tmpInitCap=size,initCapBound=size + interval;tmpInitCap <= initCapBound;
                          tmpInitCap+=interval){
                        final int initCap=tmpInitCap;
                        for(int tmpNumToRotate=0;tmpNumToRotate <= initCapBound;tmpNumToRotate+=interval){
                          final int numToRotate=tmpNumToRotate;
                          
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
                              runTest(new PackedBooleanArrDeqMonitor(checkedType,initCap),queryVal,modification,
                                  castType,inputType,size,position,numToRotate);
                            });
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
      
      TestExecutorService.completeAllTests(testName);
    }
    private void runTest(PackedBooleanArrDeqMonitor monitor,QueryVal queryVal,
        QueryVal.QueryValModification modification,QueryCastType castType,DataType inputType,int size,double position,int numToRotate){
      if(position < 0){

          queryVal.initDoesNotContain((OmniCollection.OfBoolean)monitor.getCollection(),size,0,modification);
       
      }else{
          queryVal.initContains((OmniCollection.OfBoolean)monitor.getCollection(),size,0,position,modification);
        
      }
      monitor.updateCollectionState();
      monitor.rotate(numToRotate);
      callAndVerifyResult(monitor,queryVal,modification,castType,inputType,size,position);
    }
    void callAndVerifyResult(PackedBooleanArrDeqMonitor monitor,QueryVal queryVal,QueryVal.QueryValModification modification,
        QueryCastType castType,DataType inputType,int size,double position);
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
            final int initValBound=size > 0?1:0;
            for(final var checkedType:CheckedType.values()){
              for(int tmpInitVal=0;tmpInitVal <= initValBound;++tmpInitVal){
                final int initVal=tmpInitVal;
                TestExecutorService.submitTest(()->{
                  final var monitor=SequenceInitialization.Ascending
                      .initialize(new PackedBooleanArrDeqMonitor(checkedType,initCap),size,initVal);
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
      TestExecutorService.completeAllTests(testName);
    }
    void callRaw(PackedBooleanArrDeq seq);
    void callVerify(PackedBooleanArrDeqMonitor monitor);
  }
  @Test public void testadd_val(){
    TestExecutorService.setNumWorkers(1);

    final AddTest test=(monitor,inputVal,inputType,functionCallType)->Assertions
        .assertTrue(monitor.verifyAdd(inputVal,inputType,functionCallType));
    test.runAllTests("PackedBooleanArrDeqTest.testadd_val");
  }
  @Test public void testaddFirst_val(){
    TestExecutorService.setNumWorkers(1);

    final AddTest test=PackedBooleanArrDeqMonitor::verifyAddFirst;
    test.runAllTests("PackedBooleanArrDeqTest.testaddFirst_val");
  }
  @Test public void testaddLast_val(){
    TestExecutorService.setNumWorkers(1);

    final AddTest test=PackedBooleanArrDeqMonitor::verifyAddLast;
    test.runAllTests("PackedBooleanArrDeqTest.testaddLast_val");
  }
  @Test public void testclear_void(){
    final BasicTest test=PackedBooleanArrDeqMonitor::verifyClear;
    test.runAllTests("PackedBooleanArrDeqTest.testclear_void");
  }
  @Test public void testclone_void(){
    TestExecutorService.setNumWorkers(1);

    final BasicTest test=PackedBooleanArrDeqMonitor::verifyClone;
    test.runAllTests("PackedBooleanArrDeqTest.testclone_void");
  }
  @Test public void testConstructor_int(){
    TestExecutorService.setNumWorkers(1);
    for(final var checkedType:CheckedType.values()){
        for(int tmpInitCap=0;tmpInitCap <= 15;tmpInitCap+=5){
          final int initCap=tmpInitCap;
          TestExecutorService.submitTest(()->{
            new PackedBooleanArrDeqMonitor(checkedType,initCap).verifyCollectionState();
          });
        }
      
    }
    TestExecutorService.completeAllTests("PackedBooleanArrDeqTest.testConstructor_int");
  }
  @Test public void testConstructor_void(){
    TestExecutorService.setNumWorkers(1);

    for(final var checkedType:CheckedType.values()){
      TestExecutorService.submitTest(()->{
        new PackedBooleanArrDeqMonitor(checkedType).verifyCollectionState();
      });
    }
    TestExecutorService.completeAllTests("PackedBooleanArrDeqTest.testConstructor_void");
  }
  @Test public void testcontains_val(){
    TestExecutorService.setNumWorkers(1);

    final QueryTest test=(monitor,queryVal,modification,castType,inputType,size,position)->{
        Assertions.assertEquals(position >= 0,monitor.verifyContains(queryVal,inputType,castType,modification));
    };
    test.runAllTests("PackedBooleanArrDeqTest.testcontains_val");
  }
  @Test public void testdescendingIterator_void(){
    TestExecutorService.setNumWorkers(1);

    final BasicTest test=monitor->{
      monitor.getMonitoredDescendingIterator().verifyIteratorState();
      monitor.verifyCollectionState();
    };
    test.runAllTests("PackedBooleanArrDeqTest.testdescendingIterator_void");
  }
  @Test public void testelement_void(){
    final GetTest test=(monitor,outputType)->{
      monitor.verifyElement(outputType);
      if(!monitor.isEmpty()){
        monitor.removeFirst();
      }
    };
    test.runAllTests("PackedBooleanArrDeqTest.testelement_void",true);
  }
  @Test public void testequals_Object(){
    final BasicTest test=(monitor)->Assertions.assertFalse(monitor.getCollection().equals(null));
    test.runAllTests("PackedBooleanArrDeqTest.testequals_Object");
}
  @Test public void testforEach_Consumer(){
    final MonitoredFunctionTest test=(monitor,functionGen,functionCallType,randSeed)->{
      if(functionGen.expectedException == null || monitor.isEmpty()){
        monitor.verifyForEach(functionGen,functionCallType,randSeed);
      }else{
        Assertions.assertThrows(functionGen.expectedException,
            ()->monitor.verifyForEach(functionGen,functionCallType,randSeed));
      }
    };
    test.runAllTests("PackedBooleanArrDeqTest.testforEach_Consumer",100);
  }
  @Test public void testgetFirst_void(){
    final GetTest test=(monitor,outputType)->{
      monitor.verifyGetFirst(outputType);
      if(!monitor.isEmpty()){
        monitor.removeFirst();
      }
    };
    test.runAllTests("PackedBooleanArrDeqTest.testgetFirst_void",true);
  }
  @Test public void testgetLast_void(){
    final GetTest test=(monitor,outputType)->{
      monitor.verifyGetLast(outputType);
      if(!monitor.isEmpty()){
        monitor.removeLast();
      }
    };
    test.runAllTests("PackedBooleanArrDeqTest.testgetLast_void",true);
  }
  @Test public void testhashCode_void(){
    TestExecutorService.setNumWorkers(1);

    final ToStringAndHashCodeTest test=new ToStringAndHashCodeTest(){
      @Override public void callRaw(PackedBooleanArrDeq seq){
        seq.hashCode();
      }
      @Override public void callVerify(PackedBooleanArrDeqMonitor monitor){
        monitor.verifyHashCode();
      }
    };
    test.runAllTests("PackedBooleanArrDeqTest.testhashCode_void");
  }
  @Test public void testisEmpty_void(){
    final BasicTest test=PackedBooleanArrDeqMonitor::verifyIsEmpty;
    test.runAllTests("PackedBooleanArrDeqTest.testisEmpty_void");
  }
  @Test public void testiterator_void(){
    final BasicTest test=monitor->{
      monitor.getMonitoredIterator().verifyIteratorState();
      monitor.verifyCollectionState();
    };
    test.runAllTests("PackedBooleanArrDeqTest.testiterator_void");
  }
  @Test public void testItrclone_void(){
    TestExecutorService.setNumWorkers(1);
    final BasicTest test=monitor->{
      final int size=monitor.size();
      for(final var itrType:StructType.PackedBooleanArrDeq.validItrTypes){
        for(final var position:POSITIONS){
          if(position >= 0){
            monitor.getMonitoredIterator((int)(position * size),itrType).verifyClone();
          }
        }
      }
    };
    test.runAllTests("PackedBooleanArrDeqTest.testItrclone_void");
  }
  @Test public void testItrforEachRemaining_Consumer(){
    TestExecutorService.setNumWorkers(1);

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
            for(final var functionCallType:DataType.BOOLEAN.validFunctionCalls){
              for(final var itrType:StructType.PackedBooleanArrDeq.validItrTypes){
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
                                      .initialize(new PackedBooleanArrDeqMonitor(checkedType,initCap),size,0);
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
    TestExecutorService.completeAllTests("PackedBooleanArrDeqTest.testItrforEachRemaining_Consumer");
  }
  @Test public void testItrhasNext_void(){
    TestExecutorService.setNumWorkers(1);

    final BasicTest test=monitor->{
      for(final var itrType:StructType.PackedBooleanArrDeq.validItrTypes){
        final var itrMonitor=monitor.getMonitoredIterator(itrType);
        while(itrMonitor.verifyHasNext()){
          itrMonitor.iterateForward();
        }
      }
    };
    test.runAllTests("PackedBooleanArrDeqTest.testItrhasNext_void");
  }
  @Test public void testItrnext_void(){
    TestExecutorService.setNumWorkers(1);

    for(final var size:SIZES){
      final int interval=Math.max(1,size / 10);
      final int rotateBound=size / 2 + interval;
      final int initCapBound=size + interval;
      for(var tmpNumToRotate=0;tmpNumToRotate <= rotateBound;tmpNumToRotate+=interval){
        final int numToRotate=tmpNumToRotate;
        for(var tmpInitCap=0;tmpInitCap <= initCapBound;tmpInitCap+=interval){
          final int initCap=tmpInitCap;
          for(final var itrType:StructType.PackedBooleanArrDeq.validItrTypes){
            for(final var illegalMod:itrType.validPreMods){
              for(final var checkedType:CheckedType.values()){
                if(checkedType.checked || size > 0 && illegalMod.expectedException == null){
                    for(final var outputType:DataType.BOOLEAN.validOutputTypes()){
                      if(illegalMod.expectedException == null){
                        TestExecutorService.submitTest(()->{
                          final var seqMonitor=SequenceInitialization.Ascending
                              .initialize(new PackedBooleanArrDeqMonitor(checkedType,initCap),size,0);
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
                                  .initialize(new PackedBooleanArrDeqMonitor(checkedType,initCap),size,0);
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
    TestExecutorService.completeAllTests("PackedBooleanArrDeqTest.testItrnext_void");
  }
  @Test public void testItrremove_void(){
    TestExecutorService.setNumWorkers(1);

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
                  for(final var itrType:StructType.PackedBooleanArrDeq.validItrTypes){
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
                                  .initialize(new PackedBooleanArrDeqMonitor(checkedType,initCap),size,0);
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
    TestExecutorService.completeAllTests("PackedBooleanArrDeqTest.testItrremove_void");
  }
  @Test public void testMASSIVEtoString(){
    //TODO
//      int seqSize;
//      if((seqSize=DataType.BOOLEAN.massiveToStringThreshold + 1) == 0){
//        continue;
//      }
//      OmniDeque<?> checkedNonFragmented;
//      OmniDeque<?> uncheckedNonFragmented;
//      OmniDeque<?> checkedFragmented;
//      OmniDeque<?> uncheckedFragmented;
//        long[] arr;
//        checkedNonFragmented=new PackedBooleanArrDeq.Checked(0,arr=new long[seqSize],seqSize - 1);
//        uncheckedNonFragmented=new PackedBooleanArrDeq(0,arr,seqSize - 1);
//        checkedFragmented=new PackedBooleanArrDeq.Checked((seqSize >> 1) + 1,arr,seqSize >> 1);
//        uncheckedFragmented=new PackedBooleanArrDeq((seqSize >> 1) + 1,arr,seqSize >> 1);
//        for(int i=0;i < seqSize;++i){
//          arr[i]=true;
//        }
//      
//        DataType.BOOLEAN.verifyMASSIVEToString(checkedNonFragmented.toString(),seqSize,
//          "Packed"+DataType.BOOLEAN.classPrefix + "ArrDeqTest.Checked.NonFragmented.testMassiveToString");
//        DataType.BOOLEAN.verifyMASSIVEToString(uncheckedNonFragmented.toString(),seqSize,
//          "Packed"+DataType.BOOLEAN.classPrefix + "ArrDeqTest.NonFragmented.testMassiveToString");
//        DataType.BOOLEAN.verifyMASSIVEToString(checkedFragmented.toString(),seqSize,
//          "Packed"+DataType.BOOLEAN.classPrefix + "ArrDeqTest.Checked.Fragmented.testMassiveToString");
//        DataType.BOOLEAN.verifyMASSIVEToString(uncheckedFragmented.toString(),seqSize,
//          "Packed"+DataType.BOOLEAN.classPrefix + "ArrDeqTest.Fragmented.testMassiveToString");
    
  }
  @Test public void testoffer_val(){
    TestExecutorService.setNumWorkers(1);

    final AddTest test=(monitor,inputVal,inputType,functionCallType)->Assertions
        .assertTrue(monitor.verifyOffer(inputVal,inputType,functionCallType));
    test.runAllTests("PackedBooleanArrDeqTest.testoffer_val");
  }
  @Test public void testofferFirst_val(){
    TestExecutorService.setNumWorkers(1);

    final AddTest test=(monitor,inputVal,inputType,functionCallType)->Assertions
        .assertTrue(monitor.verifyOfferFirst(inputVal,inputType,functionCallType));
    test.runAllTests("PackedBooleanArrDeqTest.testofferFirst_val");
  }
  @Test public void testofferLast_val(){
    TestExecutorService.setNumWorkers(1);

    final AddTest test=(monitor,inputVal,inputType,functionCallType)->Assertions
        .assertTrue(monitor.verifyOfferLast(inputVal,inputType,functionCallType));
    test.runAllTests("PackedBooleanArrDeqTest.testofferLast_val");
  }
  @Test public void testpeek_void(){
    final GetTest test=(monitor,outputType)->{
      monitor.verifyPeek(outputType);
      if(!monitor.isEmpty()){
        monitor.removeFirst();
      }
    };
    test.runAllTests("PackedBooleanArrDeqTest.testpeek_void",false);
  }
  @Test public void testpeekFirst_void(){
    final GetTest test=(monitor,outputType)->{
      monitor.verifyPeekFirst(outputType);
      if(!monitor.isEmpty()){
        monitor.removeFirst();
      }
    };
    test.runAllTests("PackedBooleanArrDeqTest.testpeekFirst_void",false);
  }
  @Test public void testpeekLast_void(){
    final GetTest test=(monitor,outputType)->{
      monitor.verifyPeekLast(outputType);
      if(!monitor.isEmpty()){
        monitor.removeLast();
      }
    };
    test.runAllTests("PackedBooleanArrDeqTest.testpeekLast_void",false);
  }
  @Test public void testpoll_void(){
    final GetTest test=PackedBooleanArrDeqMonitor::verifyPoll;
    test.runAllTests("PackedBooleanArrDeqTest.testpoll_void",false);
  }
  @Test public void testpollFirst_void(){
    final GetTest test=PackedBooleanArrDeqMonitor::verifyPollFirst;
    test.runAllTests("PackedBooleanArrDeqTest.testpollFirst_void",false);
  }
  @Test public void testpollLast_void(){
    final GetTest test=PackedBooleanArrDeqMonitor::verifyPollLast;
    test.runAllTests("PackedBooleanArrDeqTest.testpollLast_void",false);
  }
  @Test public void testpop_void(){
    final GetTest test=PackedBooleanArrDeqMonitor::verifyPop;
    test.runAllTests("PackedBooleanArrDeqTest.testpop_void",true);
  }
  @Test public void testpush_val(){
    TestExecutorService.setNumWorkers(1);

    final AddTest test=PackedBooleanArrDeqMonitor::verifyPush;
    test.runAllTests("PackedBooleanArrDeqTest.testpush_val");
  }
  @Test public void testReadAndWrite(){
    TestExecutorService.setNumWorkers(1);

    final MonitoredFunctionTest test=(monitor,functionGen,functionCallType,randSeed)->{
      if(functionGen.expectedException == null){
        Assertions.assertDoesNotThrow(()->monitor.verifyReadAndWrite(functionGen));
      }else{
        Assertions.assertThrows(functionGen.expectedException,()->monitor.verifyReadAndWrite(functionGen));
      }
    };
    test.runAllTests("PackedBooleanArrDeqTest.testReadAndWrite",0);
  }
  @Test public void testremove_void(){
    final GetTest test=PackedBooleanArrDeqMonitor::verifyRemove;
    test.runAllTests("PackedBooleanArrDeqTest.testremove_void",true);
  }
  @Test public void testremoveFirst_void(){
    final GetTest test=PackedBooleanArrDeqMonitor::verifyRemoveFirst;
    test.runAllTests("PackedBooleanArrDeqTest.testremoveFirst_void",true);
  }
  @Test public void testremoveFirstOccurrence_val(){
    TestExecutorService.setNumWorkers(1);

    final QueryTest test=(monitor,queryVal,modification,castType,inputType,size,position)->{
        Assertions.assertEquals(position >= 0,
            monitor.verifyRemoveFirstOccurrence(queryVal,inputType,castType,modification));

    };
    test.runAllTests("PackedBooleanArrDeqTest.testremoveFirstOccurrence_val");
  }
  @Test public void testremoveIf_Predicate(){
    TestExecutorService.setNumWorkers(1);

      for(final var checkedType:CheckedType.values()){
        for(final var filterGen:StructType.PackedBooleanArrDeq.validMonitoredRemoveIfPredicateGens){
          if(filterGen.expectedException == null || checkedType.checked){
            for(final var functionCallType:DataType.BOOLEAN.validFunctionCalls){
              for(final var size:SHORT_SIZES){
                int periodBound;
                int initValBound;
                int periodInc;
                int periodOffset;
                if(size > 0){
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
                                .initialize(new PackedBooleanArrDeqMonitor(checkedType,size),size,initVal,period);
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
    
    TestExecutorService.completeAllTests("PackedBooleanArrDeqTest.testremoveIf_Predicate");
  }
  @Test public void testremoveLast_void(){
    final GetTest test=PackedBooleanArrDeqMonitor::verifyRemoveLast;
    test.runAllTests("PackedBooleanArrDeqTest.testremoveLast_void",true);
  }
  @Test public void testremoveLastOccurrence_val(){
    TestExecutorService.setNumWorkers(1);

    final QueryTest test=(monitor,queryVal,modification,castType,inputType,size,position)->{
        Assertions.assertEquals(position >= 0,
            monitor.verifyRemoveLastOccurrence(queryVal,inputType,castType,modification));

    };
    test.runAllTests("PackedBooleanArrDeqTest.testremoveLastOccurrence_val");
  }
  @Test public void testremoveVal_val(){
    TestExecutorService.setNumWorkers(1);

    final QueryTest test=(monitor,queryVal,modification,castType,inputType,size,position)->{
        Assertions.assertEquals(position >= 0,monitor.verifyRemoveVal(queryVal,inputType,castType,modification));
    };
    test.runAllTests("PackedBooleanArrDeqTest.testremoveVal_val");
  }
  @Test public void testsearch_val(){
    TestExecutorService.setNumWorkers(1);

    final QueryTest test=(monitor,queryVal,modification,castType,inputType,size,position)->{
        int expectedIndex;
        if(position >= 0){
          expectedIndex=1 + (int)Math.round(position * size);
        }else{
          expectedIndex=-1;
        }
        Assertions.assertEquals(expectedIndex,monitor.verifySearch(queryVal,inputType,castType,modification));
    };
    test.runAllTests("PackedBooleanArrDeqTest.testsearch_val");
  }
  @Test public void testsize_void(){
    final BasicTest test=PackedBooleanArrDeqMonitor::verifySize;
    test.runAllTests("PackedBooleanArrDeqTest.testsize_void");
  }
  @Test public void testtoArray_IntFunction(){
    final MonitoredFunctionTest test=(monitor,functionGen,functionCallType,randSeed)->{
      if(functionGen.expectedException == null){
        monitor.verifyToArray(functionGen);
      }else{
        Assertions.assertThrows(functionGen.expectedException,()->monitor.verifyToArray(functionGen));
      }
    };
    test.runAllTests("PackedBooleanArrDeqTest.testtoArray_IntFunction",0);
  }
  @Test public void testtoArray_ObjectArray(){
    final BasicTest test=(monitor)->{
      final int size=monitor.size();
      final int inc=Math.max(1,size / 10);
      for(int paramArrLength=0,bound=size + inc;paramArrLength <= bound;paramArrLength+=inc){
        monitor.verifyToArray(new Object[paramArrLength]);
      }
    };
    test.runAllTests("PackedBooleanArrDeqTest.testtoArray_ObjectArray");
  }
  @Test public void testtoArray_void(){
    final BasicTest test=(monitor)->{
      for(final var outputType:monitor.dataType.validOutputTypes()){
        outputType.verifyToArray(monitor);
      }
    };
    test.runAllTests("PackedBooleanArrDeqTest.testtoArray_void");
  }
  @Test public void testtoString_void(){
    final ToStringAndHashCodeTest test=new ToStringAndHashCodeTest(){
      @Override public void callRaw(PackedBooleanArrDeq seq){
        seq.toString();
      }
      @Override public void callVerify(PackedBooleanArrDeqMonitor monitor){
        monitor.verifyToString();
      }
    };
    test.runAllTests("PackedBooleanArrDeqTest.testhashCode_void");
  }
  @org.junit.jupiter.api.AfterEach public void verifyAllExecuted(){
    int numTestsRemaining;
    if((numTestsRemaining=TestExecutorService.getNumRemainingTasks()) != 0){
      System.err.println("Warning: there were " + numTestsRemaining + " tests that were not completed");
    }
    TestExecutorService.reset();
  }
  @org.junit.jupiter.api.BeforeEach public void setNumWorkers(){
    TestExecutorService.setNumWorkers(Runtime.getRuntime().availableProcessors());
  }
}
