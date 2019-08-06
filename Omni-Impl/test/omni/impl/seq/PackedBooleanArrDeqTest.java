package omni.impl.seq;

import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import org.junit.jupiter.api.Assertions;
import omni.api.OmniIterator;
import omni.impl.CheckedType;
import omni.impl.DataType;
import omni.impl.IteratorType;
import omni.impl.MonitoredFunction;
import omni.impl.MonitoredRemoveIfPredicate;
import omni.impl.StructType;
import omni.util.BitSetUtil;
import omni.util.OmniArray;

public class PackedBooleanArrDeqTest{
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
    PackedBooleanArrDeqMonitor(CheckedType checkedType,DataType dataType){
      super(checkedType,dataType);
    }

    PackedBooleanArrDeqMonitor(CheckedType checkedType,DataType dataType,int capacity){
      super(checkedType,dataType,capacity);
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
            dataType.convertValAndStoreInArray(head,expectedArr,inputVal,inputType);
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

    @Override public void verifyGetResult(int index,Object result,DataType outputType){
      //TODO
      throw new UnsupportedOperationException();
    }

    @Override public void verifyRemoveIf(boolean result,MonitoredRemoveIfPredicate filter){
      //TODO
      throw new UnsupportedOperationException();
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
  
}
