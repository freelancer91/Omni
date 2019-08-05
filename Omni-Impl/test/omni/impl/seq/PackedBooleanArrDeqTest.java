package omni.impl.seq;

import java.util.function.IntConsumer;
import org.junit.jupiter.api.Assertions;
import omni.api.OmniDeque;
import omni.api.OmniIterator;
import omni.impl.CheckedType;
import omni.impl.DataType;
import omni.impl.IteratorType;
import omni.impl.MonitoredFunction;
import omni.impl.MonitoredRemoveIfPredicate;
import omni.impl.StructType;
import omni.util.OmniArray;
import omni.util.BitSetUtil;

public class PackedBooleanArrDeqTest{
  private static class PackedBooleanArrDeqMonitor extends AbstractArrDeqMonitor<PackedBooleanArrDeq,Boolean>{
    private abstract class AbstractItrMonitor extends AbstractArrDeqMonitor<PackedBooleanArrDeq,Boolean>.AbstractItrMonitor{
      AbstractItrMonitor(OmniIterator<?> itr,int expectedCursor,int numLeft){
        super(itr,expectedCursor,numLeft);
      }
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
            final var arr=expectedArr;
            if(tail < headDist){
              BitSetUtil.arraycopy(arr,0,arr,arrBound,1);
              BitSetUtil.arraycopy(arr,1,arr,0,tail);
              System.arraycopy(arr,1,arr,0,tail);
              expectedTail=tail == 0?arrBound:tail - 1;
              expectedCursor=arrBound;
            }else{
              int tmp;
              BitSetUtil.arraycopy(arr,tmp=head,arr,++head,headDist);
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
            }else{
              BitSetUtil.arraycopy(expectedArr,tail=head,arr,++head,headDist);
              expectedHead=head;
            }
          }else{
            BitSetUtil.arraycopy(arr,lastRet + 1,arr,lastRet,tailDist);
            BitSetUtil.arraycopy(arr,0,arr,arrBound,1);
            BitSetUtil.arraycopy(arr,1,arr,0,tail);
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
              BitSetUtil.arraycopy(arr,lastRet + 1,arr,lastRet,tailDist);
              expectedTail=tail - 1;
              expectedCursor=lastRet;
            }
          }else{
            BitSetUtil.arraycopy(arr,0,arr,1,lastRet);
            BitSetUtil.arraycopy(arr,arrBound,arr,0,1);
            int tmp;
            BitSetUtil.arraycopy(arr,tmp=head,arr,++head,headDist);
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
          BitSetUtil.arraycopy(arr,tmp=head,arr,++head,headDist);
          expectedHead=head;
        }else{
          BitSetUtil.arraycopy(arr,lastRet + 1,arr,lastRet,tailDist);
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
            BitSetUtil.arraycopy(arr,tmp=head,arr,++head,headDist);
            expectedHead=head;
          }else{
            BitSetUtil.arraycopy(arr,lastRet + 1,arr,lastRet,tailDist);
            BitSetUtil.arraycopy(arr,0,arr,arrBound,1);
            BitSetUtil.arraycopy(arr,1,arr,0,tail);
            expectedTail=tail == 0?arrBound:tail - 1;
            expectedCursor=lastRet;
          }
        }else{
          final int tailDist=tail - lastRet;
          headDist=arrBound - head;
          if(tailDist <= headDist + lastRet + 1){
            BitSetUtil.arraycopy(arr,lastRet + 1,arr,lastRet,tailDist);
            expectedTail=tail - 1;
            expectedCursor=lastRet;
          }else{
            BitSetUtil.arraycopy(arr,0,arr,1,lastRet);
            BitSetUtil.arraycopy(arr,arrBound,arr,0,1);
            int tmp;
            BitSetUtil.arraycopy(arr,tmp=head,arr,++head,headDist);
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
            }else{
              BitSetUtil.arraycopy(arr,tail=head,arr,++head,headDist);
              expectedHead=head;
              expectedCursor=lastRet;
            }
          }else{
            BitSetUtil.arraycopy(arr,lastRet + 1,arr,lastRet,tailDist);
            BitSetUtil.arraycopy(arr,0,arr,arrBound,1);
            BitSetUtil.arraycopy(arr,1,arr,0,tail);
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
              BitSetUtil.arraycopy(arr,lastRet + 1,arr,lastRet,tailDist);
              expectedTail=tail - 1;
            }
          }else{
            BitSetUtil.arraycopy(arr,0,arr,1,lastRet);
            BitSetUtil.arraycopy(arr,arrBound,arr,0,1);
            int tmp;
            BitSetUtil.arraycopy(arr,tmp=head,arr,++head,headDist);
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
          BitSetUtil.arraycopy(arr,lastRet + 1,arr,lastRet,tailDist);
          expectedTail=tail - 1;
        }else{
          int tmp;
          BitSetUtil.arraycopy(arr,tmp=head,arr,++head,headDist);
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
            BitSetUtil.arraycopy(arr,1,arr,0,tail);
            expectedTail=tail == 0?arrBound:tail - 1;
          }else{
            BitSetUtil.arraycopy(arr,arrBound,arr,0,1);
            int tmp;
            BitSetUtil.arraycopy(arr,tmp=head,arr,++head,cursor);
            expectedHead=cursor == 0?0:head;
            expectedCursor=0;
          }
        }else{
          int headDist=++cursor - head;
          if(headDist > 0){
            final int tailDist=arrBound - cursor;
            if(headDist <= tailDist + tail + 1){
              BitSetUtil.arraycopy(arr,tail=head,arr,++head,headDist);
              expectedHead=head;
              expectedCursor=cursor;
            }else{
              BitSetUtil.arraycopy(arr,cursor + 1,arr,cursor,tailDist);
              BitSetUtil.arraycopy(arr,0,arr,arrBound,1);
              BitSetUtil.arraycopy(arr,1,arr,0,tail);
              expectedTail=tail == 0?arrBound:tail - 1;
            }
          }else{
            final int tailDist=tail - cursor;
            headDist=arrBound - head;
            if(tailDist <= headDist + cursor + 1){
              BitSetUtil.arraycopy(arr,cursor + 1,arr,cursor,tailDist);
              expectedTail=tail - 1;
            }else{
              BitSetUtil.arraycopy(arr,0,arr,1,cursor);
              BitSetUtil.arraycopy(arr,arrBound,arr,0,1);
              int tmp;
              BitSetUtil.arraycopy(arr,tmp=head,arr,++head,headDist);
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
      //TODO
      throw new UnsupportedOperationException();
    }

    @Override public void updateAddState(Object inputVal,DataType inputType){
      //TODO
      throw new UnsupportedOperationException();
    }

    @Override public void updateCollectionState(){
      //TODO
      throw new UnsupportedOperationException();
    }

    @Override public void updateRemoveLastOccurrenceState(Object inputVal,DataType inputType){
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException();
    }

    @Override public void updateRemoveValState(Object inputVal,DataType inputType){
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException();

    }

    @Override public void verifyArrayIsCopy(Object arr,boolean emptyArrayMayBeSame){
      //TODO
      throw new UnsupportedOperationException();
    }

    @Override public void verifyClone(Object clone){
      //TODO
      throw new UnsupportedOperationException();
    }

    @Override public void verifyCollectionState(){
      //TODO
      throw new UnsupportedOperationException();
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
      Object actualArr;
      Object tmp=null;
      final int newHead=-numToRotate + expectedCapacity;
      int newTail=newHead + expectedSize - 1;
      if(newTail >= expectedCapacity){
        newTail-=expectedCapacity;
      }
      final int overflow=Math.min(numToRotate - (expectedCapacity - expectedSize),expectedSize - numToRotate);
      final var seq=(PackedBooleanArrDeq)this.seq;
      actualArr=seq.words;
      seq.head=newHead;
      seq.tail=newTail;
      if(overflow > 0){
        tmp=new long[BitSetUtil.getPackedCapacity(overflow)];
      }
      if(tmp != null){
        if(expectedSize - numToRotate > overflow){
          BitSetUtil.arraycopy(actualArr,expectedSize - overflow,tmp,0,overflow);
          BitSetUtil.arraycopy(actualArr,0,actualArr,expectedCapacity - numToRotate,numToRotate);
          int tmpLength;
          BitSetUtil.arraycopy(actualArr,numToRotate,actualArr,0,tmpLength=expectedSize - numToRotate - overflow);
          BitSetUtil.arraycopy(tmp,0,actualArr,tmpLength,overflow);
        }else{
          BitSetUtil.arraycopy(actualArr,expectedSize - overflow,tmp,0,overflow);
          BitSetUtil.arraycopy(actualArr,0,actualArr,expectedCapacity - numToRotate,numToRotate);
          BitSetUtil.arraycopy(tmp,0,actualArr,0,overflow);
        }
      }else{
        if(numToRotate >= expectedSize){
          BitSetUtil.arraycopy(actualArr,0,actualArr,newHead,expectedSize);
        }else{
          BitSetUtil.arraycopy(actualArr,0,actualArr,expectedCapacity - numToRotate,numToRotate);
          BitSetUtil.arraycopy(actualArr,numToRotate,actualArr,0,expectedSize - numToRotate);
        }
      }
      expectedHead=newHead;
      expectedTail=newTail;
      BitSetUtil.arraycopy(actualArr,0,expectedArr,0,expectedCapacity);
    }
    
    
    
  }
  
}
