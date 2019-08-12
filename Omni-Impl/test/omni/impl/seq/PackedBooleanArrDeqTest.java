package omni.impl.seq;

import java.util.NoSuchElementException;
import java.util.Random;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
import omni.util.ArrCopy;
import omni.util.BitSetUtil;
import omni.util.OmniArray;
import omni.util.TestExecutorService;

@TestMethodOrder(OrderAnnotation.class) 
public class PackedBooleanArrDeqTest{
  private static final int[] SIZES=new int[]{0,1,2,3,4,5,6,7,8,9,10,15,20,30,40,50,60,70,80,90,100};
  private static final int[] SHORT_SIZES;
  
  static {
    IntStream.Builder builder=IntStream.builder();
    builder.accept(0);
    builder.accept(1);
    builder.accept(2);
    for(int i=1;i<20;++i) {
      int baseSize=i*64;
      for(int j=-2;j<=2;++j) {
        builder.accept(baseSize+j);
      }
    }
    SHORT_SIZES=builder.build().toArray();
  }
  
  //private static final int[] SHORT_SIZES=new int[]{0,1,63,64,65,127,128,129,191,192,193};
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
      updateCollectionState();
    }

    PackedBooleanArrDeqMonitor(CheckedType checkedType,int capacity){
      super(checkedType,DataType.BOOLEAN,capacity);
      updateCollectionState();
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
      ++expectedSize;
      ++expectedModCount;
      final var val=(boolean)inputVal;
      final var words=(long[])expectedArr;
      if(expectedCapacity!=0) {
        int tail;
        if((tail=this.expectedTail)==-1) {
          words[(tail=words.length)-1]=val?-1L:0L;
          this.expectedHead=tail=(tail<<6)-1;
          this.expectedTail=tail;
        }else {
          int head;
          final long[] newWords;
          int newTailWordBound;
          int oldCap,newCap;
          if(tail==(head=this.expectedHead-1)) {
            newCap=OmniArray.growBy50Pct(oldCap=words.length);
            if((tail&63)==0) {
              (newWords=new long[newCap])[(head=newCap-oldCap)-1]=val?-1L:0L;
              this.expectedHead=(head<<6)-1;
              this.expectedTail=(newCap<<6)-1;
              ArrCopy.semicheckedCopy(words,tail=(tail>>6)+1,newWords,head,oldCap-=tail);
              ArrCopy.uncheckedCopy(words,0,newWords,head+oldCap,tail);
            }else {
              ArrCopy.uncheckedCopy(words,0,newWords=new long[newCap],0,(newTailWordBound=tail>>6)+1);
              this.expectedHead=head=(newCap<<6)-((oldCap<<6)-(tail+1))-1;
              ArrCopy.semicheckedCopy(words,newTailWordBound+1,newWords,oldCap=(tail=head>>6)+1,newCap-oldCap);
              if(val) {
                newWords[tail]=words[newTailWordBound]|1L<<head;
              }else {
                newWords[tail]=words[newTailWordBound]&~(1L<<head);
              }
            }
            this.expectedArr=newWords;
            this.expectedCapacity=newWords.length<<6;
            return;
          }else if(head==-1 && tail==(head=((oldCap=words.length)<<6)-1)) {
            this.expectedTail=((newCap=OmniArray.growBy50Pct(oldCap))<<6)-1;
            ArrCopy.uncheckedCopy(words,0,newWords=new long[newCap],head=newCap-oldCap,oldCap);
            this.expectedHead=(--head<<6)+63;
            if(val) {
                newWords[head]=-1L;
            }
            this.expectedArr=newWords;
            this.expectedCapacity=newWords.length<<6;

            return;
          }else {
            newWords=words;
            this.expectedHead=head;
          }
          if(val) {
            newWords[head>>6]|=1L<<head;
          }else {
            newWords[head>>6]&=~(1L<<head);
          }
        }
      }else {
        this.expectedHead=63;
        this.expectedTail=63;
        this.expectedArr=new long[] {val?-1L:0L};
        this.expectedCapacity=64;
      }
    }

    @Override public void updateAddState(Object inputVal,DataType inputType){
        ++expectedSize;
        ++expectedModCount;
        final long[] words=(long[])this.expectedArr;
        if(expectedCapacity!=0) {
          int tail;
          if((tail=this.expectedTail)==-1) {
            words[0]=(boolean)inputVal?1L:0L;
            this.expectedHead=0;
            this.expectedTail=0;
          }else {
            int head;
            final long[] newWords;
            int newTailWordBound;
            if(++tail==(head=this.expectedHead)) {
              if((tail&63)==0) {
                this.expectedHead=0;
                (newWords=new long[OmniArray.growBy50Pct(tail=words.length)])[tail]=(boolean)inputVal?1L:0L;
                this.expectedTail=tail<<6;
                ArrCopy.uncheckedCopy(words,head>>=6,newWords,0,tail-=head);
                ArrCopy.uncheckedCopy(words,0,newWords,tail,head);
              }else {
                this.expectedTail=tail;
                int oldCap,newCap;
                ArrCopy.semicheckedCopy(words,0,newWords=new long[newCap=OmniArray.growBy50Pct(oldCap=words.length)],0,newTailWordBound=tail>>6);
                if((boolean)inputVal) {
                  newWords[newTailWordBound]=words[newTailWordBound]|1L<<tail;
                }else {
                  newWords[newTailWordBound]=words[newTailWordBound]&~(1L<<tail);
                }
                this.expectedHead=head=(newCap<<6)-((oldCap<<6)-tail);
                ArrCopy.uncheckedCopy(words,newTailWordBound,newWords,head>>=6,newCap-head);
              }
              this.expectedCapacity=newWords.length<<6;
              this.expectedArr=newWords;
            }else {
              if((newTailWordBound=tail>>6)==words.length) {
                if(head==0) {
                  ArrCopy.uncheckedCopy(words,0,newWords=new long[OmniArray.growBy50Pct(newTailWordBound)],0,newTailWordBound);
                  this.expectedCapacity=newWords.length<<6;
                  this.expectedArr=newWords;
                  if((boolean)inputVal) {
                      newWords[newTailWordBound]=1L;
                  }
                  this.expectedTail=tail;
                }else {
                  if((boolean)inputVal) {
                    words[0]|=1L;
                  }else {
                    words[0]&=-2L;
                  }
                  this.expectedTail=0;
                }
              }else {
                if((boolean)inputVal) {
                  words[newTailWordBound]|=1L<<tail;
                }else {
                  words[newTailWordBound]&=~(1L<<tail);
                }
                this.expectedTail=tail;
              }
            }
          }
        }else {
          this.expectedHead=0;
          this.expectedTail=0;
          this.expectedArr=new long[] {(boolean)inputVal?1L:0L};
          this.expectedCapacity=64;
        }
      
        
//        if(expectedSize == 0){
//            if(expectedCapacity == 0){
//
//                expectedArr=new long[BitSetUtil.getPackedCapacity(expectedCapacity=64)];
//            }
//            expectedHead=0;
//            expectedTail=0;
//            BitSetUtil.storeInPackedArr((long[])expectedArr,0,(boolean)inputVal);
//          }else{
//            int head;
//            int tail;
//            if((tail=expectedTail + 1) == (head=expectedHead)){
//              expectedHead=0;
//              tail=expectedCapacity;
//              final long[] newArr=new long[BitSetUtil.getPackedCapacity(expectedCapacity=OmniArray.growBy50Pct(tail))];
//              BitSetUtil.storeInPackedArr(newArr,tail,(boolean)inputVal);
//              expectedTail=tail;
//              BitSetUtil.semicheckedCopy((long[])expectedArr,head,newArr,0,tail-=head);
//              BitSetUtil.semicheckedCopy((long[])expectedArr,0,newArr,tail,head);
//              expectedArr=newArr;
//            }else{
//              if(tail == expectedCapacity){
//                if(head == 0){
//                 BitSetUtil.semicheckedCopy((long[])expectedArr,0,(long[])(expectedArr=new long[BitSetUtil.getPackedCapacity(expectedCapacity=OmniArray.growBy50Pct(tail))]),0,tail);
//                }else{
//                  tail=0;
//                }
//              }
//              BitSetUtil.storeInPackedArr((long[])expectedArr,tail,(boolean)inputVal);
//              expectedTail=tail;
//            }
//          }
//          ++expectedModCount;
//          ++expectedSize;
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
        Assertions.assertNotSame(clone,seq);
        final var cloneCast=(PackedBooleanArrDeq)clone;
        Assertions.assertEquals(checkedType.checked,cloneCast instanceof PackedBooleanArrDeq.Checked);
        if(checkedType.checked){
          Assertions.assertEquals(0,((PackedBooleanArrDeq.Checked)cloneCast).modCount);
        }
        if(expectedSize == 0){
          Assertions.assertEquals(-1,cloneCast.tail);
          Assertions.assertEquals(0,cloneCast.head);
          Assertions.assertNull(cloneCast.words);
        }else{
          final int cloneCapacity=cloneCast.words.length<<6;
          Assertions.assertNotSame(cloneCast.words,expectedArr);
          Assertions.assertEquals((expectedSize-1>>6)+1<<6,cloneCapacity);
          int cloneSize=cloneCast.tail+1-cloneCast.head;
          if(cloneSize<=0) {
              cloneSize+=cloneCapacity;
          }
          Assertions.assertEquals(expectedSize,cloneSize);
        }
        var cloneItr=cloneCast.iterator();
        var thisItr=seq.iterator();
        while(cloneItr.hasNext()) {
            Assertions.assertEquals(thisItr.nextBoolean(),cloneItr.nextBoolean());
        }
        Assertions.assertFalse(thisItr.hasNext());
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
        if(expectedSize<=0) {
            return;
        }
        int expectedCapacity;
        numToRotate%=expectedCapacity=this.expectedCapacity;
        if(numToRotate<=0) {
            return;
        }
        int currHead=seq.head;
        int newHead=currHead+numToRotate;
        if(newHead>=expectedCapacity) {
            newHead-=expectedCapacity;
        }
        int currTail=seq.tail;
        int newTail=currTail+numToRotate;
        if(newTail>=expectedCapacity) {
            newTail-=expectedCapacity;
        }
        long[] actualArr=seq.words;
        long[] expectedArr=(long[])this.expectedArr;
        Assertions.assertEquals(actualArr.length,expectedArr.length);
        this.expectedHead=newHead;
        this.expectedTail=newTail;
        seq.head=newHead;
        seq.tail=newTail;
        if(newTail<newHead) {
            if(currTail<currHead) {
                if(currTail<newTail) {
                    BitSetUtil.uncheckedSrcAlignedCopy(actualArr,0,expectedArr,numToRotate,currTail+1);
                    BitSetUtil.uncheckedDstAlignedCopy(actualArr,expectedCapacity-numToRotate,expectedArr,0,numToRotate);
                    BitSetUtil.uncheckedCopy(actualArr,currHead,expectedArr,newHead,expectedCapacity-newHead);
                }else {
                    BitSetUtil.uncheckedCopy(actualArr,currHead,expectedArr,newHead,expectedCapacity-currHead);
                    BitSetUtil.uncheckedSrcAlignedCopy(actualArr,0,expectedArr,expectedCapacity-numToRotate,numToRotate);
                    BitSetUtil.uncheckedDstAlignedCopy(actualArr,numToRotate,expectedArr,0,currTail+1-numToRotate);
                }
            }else {
                BitSetUtil.uncheckedDstAlignedCopy(actualArr,currTail-newTail,expectedArr,0,newTail+1);
                BitSetUtil.uncheckedCopy(actualArr,currHead,expectedArr,newHead,expectedSize-(newTail+1));
            }
            ArrCopy.uncheckedCopy(expectedArr,0,actualArr,0,(newTail>>6)+1);
            ArrCopy.uncheckedCopy(expectedArr,newHead>>=6,actualArr,newHead,(expectedCapacity>>6)-newHead);
        }else {
            if(currTail<currHead) {
                BitSetUtil.uncheckedSrcAlignedCopy(actualArr,0,expectedArr,newTail-currTail,currTail+1);
                BitSetUtil.uncheckedCopy(actualArr,currHead,expectedArr,newHead,expectedSize-currTail-1);
            }else {
                BitSetUtil.uncheckedCopy(actualArr,currHead,expectedArr,newHead,expectedSize);
            }
            ArrCopy.uncheckedCopy(expectedArr,newHead>>=6,actualArr,newHead,(newTail>>6)+1-newHead);
        }
    }
    
    
    
  }
  private static int getExpectedFinalCapacity(int initCap,int numAdded) {
      if(numAdded==0) {
          return 0;
      }
      initCap=initCap+64&-64;
      for(int i=0;;) {
          if(++i>initCap) {
              initCap>>=6;
              initCap+=initCap>>1;
          }
          if(i==numAdded) {
              return initCap;
          }
      }
  }
  
  private static interface AddTest{
    private void runAllTests(String testName){
      for(final var checkedType:CheckedType.values()){
        for(final var functionCallType:DataType.BOOLEAN.validFunctionCalls){
          for(int tmpInitCap=0;tmpInitCap <= 512;tmpInitCap+=64){
            final int initCap=tmpInitCap;
            final int rotateBound=getExpectedFinalCapacity(initCap,1)-1;
            for(int tmpNumToRotate=0;tmpNumToRotate <= rotateBound;++tmpNumToRotate){
              final int numToRotate=tmpNumToRotate;
              TestExecutorService.submitTest(()->{
                final var monitor=new PackedBooleanArrDeqMonitor(checkedType,initCap);
                if(numToRotate != 0){
                  monitor.add(0);
                  monitor.rotate(numToRotate);
                }
                for(int i=0;i < 512;++i){
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
      void runTest(PackedBooleanArrDeqMonitor monitor);
      private void runAllTests(String testName,int[] sizes) {
          for(var size:sizes) {
              final int initCapBound=size+64&-64;
              final int initValBound=size==0?0:1;
              for(int tmpInitCap=0;tmpInitCap<=initCapBound;tmpInitCap+=64) {
                  final int initCap=tmpInitCap;
                  final int rotateBound=getExpectedFinalCapacity(initCap,size);
                  for(int tmpInitVal=0;tmpInitVal<=initValBound;++tmpInitVal) {
                      final int initVal=tmpInitVal;
                      for(var checkedType:CheckedType.values()) {
                          TestExecutorService.submitTest(()->{
                              final var monitor=SequenceInitialization.Ascending.initialize(new PackedBooleanArrDeqMonitor(checkedType,initCap),size,initVal);
                              for(int i=0;i<rotateBound;++i) {
                                  monitor.rotate(1);
                                  runTest(monitor);
                              }
                          });
                      }
                  }
              }
          }
          TestExecutorService.completeAllTests(testName);
      }
      
  }

  
  
  private static interface GetTest{
    private void runAllTests(String testName,boolean throwsOnEmpty){
        for(final var outputType:DataType.BOOLEAN.validOutputTypes()){
          for(final var checkedType:CheckedType.values()){
            for(int tmpInitCap=0;tmpInitCap <=512;tmpInitCap+=64){
              final int initCap=tmpInitCap;
              final int rotateBound=getExpectedFinalCapacity(initCap,100)-1;
              
              for(int tmpNumToRotate=0;tmpNumToRotate <= rotateBound;++tmpNumToRotate){
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
      
      
      void runTest(PackedBooleanArrDeqMonitor monitor,MonitoredFunctionGen functionGen,FunctionCallType functionCallType,long randSeed);
      private void runAllTests(String testName,long randMax,int[] sizes) {
          for(var size:sizes) {
              final int initCapBound=size+64&-64;
              final int initValBound=size==0?0:1;
              for(int tmpInitCap=0;tmpInitCap<=initCapBound;tmpInitCap+=64) {
                  final int initCap=tmpInitCap;
                  final int rotateBound=getExpectedFinalCapacity(initCap,size);
                  for(int tmpInitVal=0;tmpInitVal<=initValBound;++tmpInitVal) {
                      final int initVal=tmpInitVal;
                      for(var checkedType:CheckedType.values()) {
                          //TestExecutorService.submitTest(()->{
                              final var monitor=SequenceInitialization.Ascending.initialize(new PackedBooleanArrDeqMonitor(checkedType,initCap),size,initVal);
                              for(int i=0;i<rotateBound;++i) {
                                  monitor.rotate(1);
                                  for(final var functionGen:StructType.ArrDeq.validMonitoredFunctionGens){
                                      if(functionGen.expectedException == null || monitor.checkedType.checked){
                                          for(final var functionCallType:DataType.BOOLEAN.validFunctionCalls){
                                              final long randSeedBound=monitor.expectedSize > 1 && functionGen.randomized && !functionCallType.boxed?randMax:0;
                                              for(long randSeed=0;randSeed<=randSeedBound;++randSeed) {
                                                if(size==66 && initCap==64 && i==126 && functionGen==MonitoredFunctionGen.NoThrow && testName=="PackedBooleanArrDeqTest.testReadAndWrite") {
                                                  TestExecutorService.suspend();
                                                }
                                                runTest(monitor,functionGen,functionCallType,randSeed);
                                              }
                                          }
                                      }
                                  }
                              }
                          //});
                      }
                  }
              }
          }
          TestExecutorService.completeAllTests(testName);
      }
      
  }
  
//  private static interface MonitoredFunctionTest{
//    private void runAllTests(String testName,long maxRand){
//      for(final var checkedType:CheckedType.values()){
//        for(final var size:SIZES){
//          final int initValBound=size != 0?1:0;
//          for(int tmpInitCap=0,initCapBound=size+64&-64;tmpInitCap <= initCapBound;tmpInitCap+=64){
//            final int initCap=tmpInitCap;
//            final int rotateBound=getExpectedFinalCapacity(initCapBound,size)-1;
//            for(int tmpNumToRotate=0;tmpNumToRotate <= rotateBound;++tmpNumToRotate){
//              final int numToRotate=tmpNumToRotate;
//              for(final var functionGen:StructType.ArrDeq.validMonitoredFunctionGens){
//                if(functionGen.expectedException == null || checkedType.checked){
//                  for(final var functionCallType:DataType.BOOLEAN.validFunctionCalls){
//                    final long randSeedBound=size > 1 && functionGen.randomized && !functionCallType.boxed?maxRand:0;
//                    for(long tmpRandSeed=0;tmpRandSeed <= randSeedBound;++tmpRandSeed){
//                      final long randSeed=tmpRandSeed;
//                      for(int tmpInitVal=0;tmpInitVal <= initValBound;++tmpInitVal){
//                        final int initVal=tmpInitVal;
//                        TestExecutorService.submitTest(()->{
//                          final var monitor=SequenceInitialization.Ascending
//                              .initialize(new PackedBooleanArrDeqMonitor(checkedType,initCap),size,initVal);
//                          if(size > 0 && numToRotate > 0){
//                            monitor.rotate(numToRotate);
//                          }
//                          runTest(monitor,functionGen,functionCallType,randSeed);
//                        });
//                      }
//                    }
//                  }
//                }
//              }
//            }
//          }
//        }
//        
//      }
//      TestExecutorService.completeAllTests(testName);
//    }
//    void runTest(PackedBooleanArrDeqMonitor monitor,MonitoredFunctionGen functionGen,FunctionCallType functionCallType,
//        long randSeed);
//  }
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
                      for(int tmpInitCap=0,initCapBound=size+64&-64;tmpInitCap <= initCapBound;tmpInitCap+=64){

                        final int initCap=tmpInitCap;
                        final int rotateBound=getExpectedFinalCapacity(initCap,size+1)-1;
                        for(int tmpNumToRotate=0;tmpNumToRotate <= rotateBound;++tmpNumToRotate){
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

          queryVal.initDoesNotContain(monitor.getCollection(),size,0,modification);
       
      }else{
          queryVal.initContains(monitor.getCollection(),size,0,position,modification);
        
      }
      monitor.updateCollectionState();
      monitor.rotate(numToRotate);
      callAndVerifyResult(monitor,queryVal,modification,castType,inputType,size,position);
    }
    void callAndVerifyResult(PackedBooleanArrDeqMonitor monitor,QueryVal queryVal,QueryVal.QueryValModification modification,
        QueryCastType castType,DataType inputType,int size,double position);
  }
//  private static interface ToStringAndHashCodeTest{
//    private void runAllTests(String testName){
//      for(final var size:SHORT_SIZES){
//        final int interval=Math.max(1,size / 10);
//        for(int tmpInitCap=0,initCapBound=size+64&-64;tmpInitCap <= initCapBound;tmpInitCap+=64){
//          final int initCap=tmpInitCap;
//          final int rotateBound=getExpectedFinalCapacity(initCap,size)-1;
//
//          for(int tmpNumToRotate=0;tmpNumToRotate <= rotateBound;tmpNumToRotate+=interval){
//            final int numToRotate=tmpNumToRotate;
//            final int initValBound=size > 0?1:0;
//            for(final var checkedType:CheckedType.values()){
//              for(int tmpInitVal=0;tmpInitVal <= initValBound;++tmpInitVal){
//                final int initVal=tmpInitVal;
//                TestExecutorService.submitTest(()->{
//                  final var monitor=SequenceInitialization.Ascending
//                      .initialize(new PackedBooleanArrDeqMonitor(checkedType,initCap),size,initVal);
//                  if(size > 0 && numToRotate > 0){
//                    monitor.rotate(numToRotate);
//                  }
//                  callVerify(monitor);
//                });
//              }
//            }
//          }
//        }
//      }
//      TestExecutorService.completeAllTests(testName);
//    }
//    void callVerify(PackedBooleanArrDeqMonitor monitor);
//  }
  @Order(11520)
  @Test public void testadd_val(){
    final AddTest test=(monitor,inputVal,inputType,functionCallType)->Assertions
        .assertTrue(monitor.verifyAdd(inputVal,inputType,functionCallType));
    test.runAllTests("PackedBooleanArrDeqTest.testadd_val");
  }
  @Order(11520)
  @Test public void testaddFirst_val(){
    final AddTest test=PackedBooleanArrDeqMonitor::verifyAddFirst;
    test.runAllTests("PackedBooleanArrDeqTest.testaddFirst_val");
  }
  @Order(11520)
  @Test public void testaddLast_val(){
    final AddTest test=PackedBooleanArrDeqMonitor::verifyAddLast;
    test.runAllTests("PackedBooleanArrDeqTest.testaddLast_val");
  }
  @Order(144)
  @Test public void testclear_void(){
    final BasicTest test=PackedBooleanArrDeqMonitor::verifyClear;
    test.runAllTests("PackedBooleanArrDeqTest.testclear_void",SHORT_SIZES);
  }
  @Order(144)
  @Test public void testclone_void(){
    final BasicTest test=PackedBooleanArrDeqMonitor::verifyClone;
    test.runAllTests("PackedBooleanArrDeqTest.testclone_void",SHORT_SIZES);
  }
  @Order(130) @Test public void testConstructor_int(){
    for(final var checkedType:CheckedType.values()){
        for(int tmpInitCap=0;tmpInitCap <= 512;tmpInitCap+=8){
          final int initCap=tmpInitCap;
          TestExecutorService.submitTest(()->{
            new PackedBooleanArrDeqMonitor(checkedType,initCap).verifyCollectionState();
          });
        }
      
    }
    TestExecutorService.completeAllTests("PackedBooleanArrDeqTest.testConstructor_int");
  }
  @Order(2)
  @Test public void testConstructor_void(){
    for(final var checkedType:CheckedType.values()){
      TestExecutorService.submitTest(()->{
        new PackedBooleanArrDeqMonitor(checkedType).verifyCollectionState();
      });
    }
    TestExecutorService.completeAllTests("PackedBooleanArrDeqTest.testConstructor_void");
  }
  @Disabled
  @Order(6164256)
  @Test public void testcontains_val(){
      TestExecutorService.setNumWorkers(1);

    final QueryTest test=(monitor,queryVal,modification,castType,inputType,size,position)->{
        Assertions.assertEquals(position >= 0,monitor.verifyContains(queryVal,inputType,castType,modification));
    };
    test.runAllTests("PackedBooleanArrDeqTest.testcontains_val");
  }
  @Order(144)
  @Test public void testdescendingIterator_void(){
    final BasicTest test=monitor->{
      monitor.getMonitoredDescendingIterator().verifyIteratorState();
      monitor.verifyCollectionState();
    };
    test.runAllTests("PackedBooleanArrDeqTest.testdescendingIterator_void",SHORT_SIZES);
  }
  @Order(50688)
  @Test public void testelement_void(){
    final GetTest test=(monitor,outputType)->{
      monitor.verifyElement(outputType);
      if(!monitor.isEmpty()){
        monitor.removeFirst();
      }
    };
    test.runAllTests("PackedBooleanArrDeqTest.testelement_void",true);
  }
  @Order(144)
  @Test public void testequals_Object(){
    final BasicTest test=(monitor)->Assertions.assertFalse(monitor.getCollection().equals(null));
    test.runAllTests("PackedBooleanArrDeqTest.testequals_Object",SHORT_SIZES);
  }
  @Order(144)
  @Test public void testforEach_Consumer(){
    final MonitoredFunctionTest test=(monitor,functionGen,functionCallType,randSeed)->{
      if(functionGen.expectedException == null || monitor.isEmpty()){
        monitor.verifyForEach(functionGen,functionCallType,randSeed);
      }else{
        Assertions.assertThrows(functionGen.expectedException,
            ()->monitor.verifyForEach(functionGen,functionCallType,randSeed));
      }
    };
    test.runAllTests("PackedBooleanArrDeqTest.testforEach_Consumer",100,SHORT_SIZES);
  }
  @Order(50688)
  @Test public void testgetFirst_void(){
    final GetTest test=(monitor,outputType)->{
      monitor.verifyGetFirst(outputType);
      if(!monitor.isEmpty()){
        monitor.removeFirst();
      }
    };
    test.runAllTests("PackedBooleanArrDeqTest.testgetFirst_void",true);
  }
  @Order(50688)
  @Test public void testgetLast_void(){
    final GetTest test=(monitor,outputType)->{
      monitor.verifyGetLast(outputType);
      if(!monitor.isEmpty()){
        monitor.removeLast();
      }
    };
    test.runAllTests("PackedBooleanArrDeqTest.testgetLast_void",true);
  }
  @Order(144)
  @Test public void testhashCode_void(){
    BasicTest test=PackedBooleanArrDeqMonitor::verifyHashCode;
    test.runAllTests("PackedBooleanArrDeqTest.testhashCode_void",SHORT_SIZES);
  }
  @Order(144)
  @Test public void testisEmpty_void(){
    final BasicTest test=PackedBooleanArrDeqMonitor::verifyIsEmpty;
    test.runAllTests("PackedBooleanArrDeqTest.testisEmpty_void",SHORT_SIZES);
  }
  @Order(144)
  @Test public void testiterator_void(){
    final BasicTest test=monitor->{
      monitor.getMonitoredIterator().verifyIteratorState();
      monitor.verifyCollectionState();
    };
    test.runAllTests("PackedBooleanArrDeqTest.testiterator_void",SHORT_SIZES);
  }
  @Order(144) @Test public void testItrclone_void(){
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
    test.runAllTests("PackedBooleanArrDeqTest.testItrclone_void",SHORT_SIZES);
  }
  
 
  @Disabled
  @Order(144)
  @Test public void testItrforEachRemaining_Consumer(){
    TestExecutorService.setNumWorkers(1);
    BasicTest test=(monitor)->{
        int prevNumToIterate=-1;
        for(final var position:POSITIONS){
            int numToIterate;
            if(position >= 0 && (numToIterate=(int)(position * monitor.expectedSize)) != prevNumToIterate){
                prevNumToIterate=numToIterate;
                final int numLeft=monitor.expectedSize - numToIterate;
                for(final var functionCallType:DataType.BOOLEAN.validFunctionCalls){
                    for(final var itrType:StructType.PackedBooleanArrDeq.validItrTypes){
                        for(final var illegalMod:itrType.validPreMods){
                            if(illegalMod.expectedException == null || monitor.checkedType.checked){
                                for(final var functionGen:itrType.validMonitoredFunctionGens){
                                    if(monitor.checkedType.checked || monitor.expectedSize == 0 || functionGen.expectedException == null){
                                        final long randSeedBound=!functionCallType.boxed && numLeft > 1 && functionGen.randomized && illegalMod.expectedException == null?100:0;
                                        for(var randSeed=0;randSeed <= randSeedBound;++randSeed){
                                            final var itrMonitor=monitor.getMonitoredIterator(numToIterate,itrType);
                                            itrMonitor.illegalMod(illegalMod);
                                            if(illegalMod.expectedException == null || numLeft == 0){
                                                if(functionGen.expectedException == null || numLeft == 0){
                                                    itrMonitor.verifyForEachRemaining(functionGen,functionCallType,randSeed);
                                                }else{
                                                    final long finalRand=randSeed;
                                                    Assertions.assertThrows(functionGen.expectedException,()->itrMonitor.verifyForEachRemaining(functionGen,functionCallType,finalRand));
                                                }
                                            }else{
                                                final long finalRand=randSeed;
                                                Assertions.assertThrows(illegalMod.expectedException,()->itrMonitor.verifyForEachRemaining(functionGen,functionCallType,finalRand));
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
    };
    test.runAllTests("PackedBooleanArrDeqTest.testItrforEachRemaining_Consumer",SHORT_SIZES);
   
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
  @Order(144)
  @Test public void testItrhasNext_void(){
    final BasicTest test=monitor->{
      for(final var itrType:StructType.PackedBooleanArrDeq.validItrTypes){
        final var itrMonitor=monitor.getMonitoredIterator(itrType);
        while(itrMonitor.verifyHasNext()){
          itrMonitor.iterateForward();
        }
      }
    };
    test.runAllTests("PackedBooleanArrDeqTest.testItrhasNext_void",SHORT_SIZES);
  }
  @Order(144)
  @Test public void testItrnext_void(){
      BasicTest test=monitor->{
          for(var itrType:StructType.PackedBooleanArrDeq.validItrTypes) {
              for(var illegalMod:itrType.validPreMods) {
                  if(monitor.checkedType.checked || illegalMod.expectedException==null) {
                      for(var outputType:DataType.BOOLEAN.validOutputTypes()) {
                          if(illegalMod.expectedException==null) {
                              final var itrMonitor=monitor.getMonitoredIterator(itrType);
                              while(itrMonitor.hasNext()){
                                itrMonitor.verifyNext(outputType);
                              }
                              if(monitor.checkedType.checked){
                                Assertions.assertThrows(NoSuchElementException.class,()->itrMonitor.verifyNext(outputType));
                              }
                          }else {
                              for(final var position:POSITIONS){
                                  if(position >= 0){
                                      final int index=(int)(monitor.expectedSize * position);
                                      final var itrMonitor=monitor.getMonitoredIterator(index,itrType);
                                      itrMonitor.illegalMod(illegalMod);
                                      Assertions.assertThrows(illegalMod.expectedException,()->itrMonitor.verifyNext(outputType));
                                  }
                              }
                          }
                      }
                  }
              }
          }
      };
      test.runAllTests("PackedBooleanArrDeqTest.testItrnext_void",SHORT_SIZES);
  }
  @Disabled
  @Order(61318)
  @Test public void testItrremove_void(){
    TestExecutorService.setNumWorkers(1);
    //TODO rework this to make it less redundant
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
  private static void verifyMASSIVEToStringHelper(String result,int seqSize,String testName) {
      int offset;
      Assertions.assertEquals('[',result.charAt(offset=0));
      Assertions.assertEquals('f',result.charAt(++offset));
      Assertions.assertEquals('a',result.charAt(++offset));
      Assertions.assertEquals('l',result.charAt(++offset));
      Assertions.assertEquals('s',result.charAt(++offset));
      Assertions.assertEquals('e',result.charAt(++offset));
      ++offset;
      int numBatches=TestExecutorService.getNumWorkers();
      int batchSize=(seqSize-2)/numBatches * 13;
      for(int batchCount=0;batchCount<numBatches;++batchCount) {
          final int batchOffset=offset;
          final int batchBound=batchCount == numBatches?result.length() - 1:offset + batchSize;
          TestExecutorService.submitTest(()->{
              for(int i=batchOffset;;++i) {
                if(i>=batchBound) {
                    break;
                }
                Assertions.assertEquals(',',result.charAt(i));
                Assertions.assertEquals(' ',result.charAt(++i));
                Assertions.assertEquals('t',result.charAt(++i));
                Assertions.assertEquals('r',result.charAt(++i));
                Assertions.assertEquals('u',result.charAt(++i));
                Assertions.assertEquals('e',result.charAt(++i));
                if(++i>=batchBound) {
                    break;
                }
                Assertions.assertEquals(',',result.charAt(i));
                Assertions.assertEquals(' ',result.charAt(++i));
                Assertions.assertEquals('f',result.charAt(++i));
                Assertions.assertEquals('a',result.charAt(++i));
                Assertions.assertEquals('l',result.charAt(++i));
                Assertions.assertEquals('s',result.charAt(++i));
                Assertions.assertEquals('e',result.charAt(++i));
              }
          });
      }
      Assertions.assertEquals(',',result.charAt(offset=result.length()-7));
      Assertions.assertEquals(' ',result.charAt(++offset));
      Assertions.assertEquals('t',result.charAt(++offset));
      Assertions.assertEquals('r',result.charAt(++offset));
      Assertions.assertEquals('u',result.charAt(++offset));
      Assertions.assertEquals('e',result.charAt(++offset));
      Assertions.assertEquals(']',result.charAt(++offset));
      TestExecutorService.completeAllTests(testName);
  }
  @Disabled
  @Test public void testMASSIVEtoString(){
      int seqSize;
      long[] words;
      for(int i=(words=new long[((seqSize=DataType.BOOLEAN.massiveToStringThreshold+1)-1>>6)+1]).length;--i>=0;) {
          words[i]=0xAAAAAAAAAAAAAAAAL;
      }
      PackedBooleanArrDeq deq=new PackedBooleanArrDeq(0,words,seqSize-1);
      verifyMASSIVEToStringHelper(deq.toString(),seqSize,"PackedBooleanArrDeqTest.testMASSIVEtoString nonfragmented");
      //TODO test checked
      deq.tail=seqSize/2+1;
      deq.head=deq.head+1;
      verifyMASSIVEToStringHelper(deq.toString(),seqSize,"PackedBooleanArrDeqTest.testMASSIVEtoString fragmented");
      deq=new PackedBooleanArrDeq.Checked(0,words,seqSize-1);
      verifyMASSIVEToStringHelper(deq.toString(),seqSize,"PackedBooleanArrDeqTest.Checked.testMASSIVEtoString nonfragmented");
      //TODO test checked
      deq.tail=seqSize/2+1;
      deq.head=deq.head+1;
      verifyMASSIVEToStringHelper(deq.toString(),seqSize,"PackedBooleanArrDeqTest.Checked.testMASSIVEtoString fragmented");
      
    
  }
  @Order(11520)
  @Test public void testoffer_val(){
    final AddTest test=(monitor,inputVal,inputType,functionCallType)->Assertions
        .assertTrue(monitor.verifyOffer(inputVal,inputType,functionCallType));
    test.runAllTests("PackedBooleanArrDeqTest.testoffer_val");
  }
  @Order(11520)
  @Test public void testofferFirst_val(){
    final AddTest test=(monitor,inputVal,inputType,functionCallType)->Assertions
        .assertTrue(monitor.verifyOfferFirst(inputVal,inputType,functionCallType));
    test.runAllTests("PackedBooleanArrDeqTest.testofferFirst_val");
  }
  @Order(11520)
  @Test public void testofferLast_val(){
    final AddTest test=(monitor,inputVal,inputType,functionCallType)->Assertions
        .assertTrue(monitor.verifyOfferLast(inputVal,inputType,functionCallType));
    test.runAllTests("PackedBooleanArrDeqTest.testofferLast_val");
  }
  @Order(50688)
  @Test public void testpeek_void(){
    final GetTest test=(monitor,outputType)->{
      monitor.verifyPeek(outputType);
      if(!monitor.isEmpty()){
        monitor.removeFirst();
      }
    };
    test.runAllTests("PackedBooleanArrDeqTest.testpeek_void",false);
  }
  @Order(50688) @Test public void testpeekFirst_void(){
    final GetTest test=(monitor,outputType)->{
      monitor.verifyPeekFirst(outputType);
      if(!monitor.isEmpty()){
        monitor.removeFirst();
      }
    };
    test.runAllTests("PackedBooleanArrDeqTest.testpeekFirst_void",false);
  }
  @Order(50688)
  @Test public void testpeekLast_void(){
    final GetTest test=(monitor,outputType)->{
      monitor.verifyPeekLast(outputType);
      if(!monitor.isEmpty()){
        monitor.removeLast();
      }
    };
    test.runAllTests("PackedBooleanArrDeqTest.testpeekLast_void",false);
  }
  @Order(50688)
  @Test public void testpoll_void(){
    final GetTest test=PackedBooleanArrDeqMonitor::verifyPoll;
    test.runAllTests("PackedBooleanArrDeqTest.testpoll_void",false);
  }
  @Order(50688)
  @Test public void testpollFirst_void(){
    final GetTest test=PackedBooleanArrDeqMonitor::verifyPollFirst;
    test.runAllTests("PackedBooleanArrDeqTest.testpollFirst_void",false);
  }
  @Order(50688)
  @Test public void testpollLast_void(){
    final GetTest test=PackedBooleanArrDeqMonitor::verifyPollLast;
    test.runAllTests("PackedBooleanArrDeqTest.testpollLast_void",false);
  }
  @Order(50688)
  @Test public void testpop_void(){
    final GetTest test=PackedBooleanArrDeqMonitor::verifyPop;
    test.runAllTests("PackedBooleanArrDeqTest.testpop_void",true);
  }
  @Order(11520) @Test public void testpush_val(){
    final AddTest test=PackedBooleanArrDeqMonitor::verifyPush;
    test.runAllTests("PackedBooleanArrDeqTest.testpush_val");
  }
  @Order(144)
  @Test public void testReadAndWrite(){
    final MonitoredFunctionTest test=(monitor,functionGen,functionCallType,randSeed)->{
      if(functionGen.expectedException == null){
        Assertions.assertDoesNotThrow(()->monitor.verifyReadAndWrite(functionGen));
      }else{
        Assertions.assertThrows(functionGen.expectedException,()->monitor.verifyReadAndWrite(functionGen));
      }
    };
    test.runAllTests("PackedBooleanArrDeqTest.testReadAndWrite",0,SHORT_SIZES);
  }
  @Order(50688)
  @Test public void testremove_void(){
    final GetTest test=PackedBooleanArrDeqMonitor::verifyRemove;
    test.runAllTests("PackedBooleanArrDeqTest.testremove_void",true);
  }
  @Order(50688)
  @Test public void testremoveFirst_void(){
    final GetTest test=PackedBooleanArrDeqMonitor::verifyRemoveFirst;
    test.runAllTests("PackedBooleanArrDeqTest.testremoveFirst_void",true);
  }
  @Disabled
  @Order(6164256)
  @Test public void testremoveFirstOccurrence_val(){
    TestExecutorService.setNumWorkers(1);

    final QueryTest test=(monitor,queryVal,modification,castType,inputType,size,position)->{
        Assertions.assertEquals(position >= 0,
            monitor.verifyRemoveFirstOccurrence(queryVal,inputType,castType,modification));

    };
    test.runAllTests("PackedBooleanArrDeqTest.testremoveFirstOccurrence_val");
  }
  @Disabled
  @Order(925690)
  @Test public void testremoveIf_Predicate(){
    TestExecutorService.setNumWorkers(1);
    //TODO
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
  @Order(50688)
  @Test public void testremoveLast_void(){
    final GetTest test=PackedBooleanArrDeqMonitor::verifyRemoveLast;
    test.runAllTests("PackedBooleanArrDeqTest.testremoveLast_void",true);
  }
  @Disabled
  @Order(6164256)
  @Test public void testremoveLastOccurrence_val(){
    TestExecutorService.setNumWorkers(1);

    final QueryTest test=(monitor,queryVal,modification,castType,inputType,size,position)->{
        Assertions.assertEquals(position >= 0,
            monitor.verifyRemoveLastOccurrence(queryVal,inputType,castType,modification));

    };
    test.runAllTests("PackedBooleanArrDeqTest.testremoveLastOccurrence_val");
  }
  @Disabled
  @Order(6164256)
  @Test public void testremoveVal_val(){
    TestExecutorService.setNumWorkers(1);

    final QueryTest test=(monitor,queryVal,modification,castType,inputType,size,position)->{
        Assertions.assertEquals(position >= 0,monitor.verifyRemoveVal(queryVal,inputType,castType,modification));
    };
    test.runAllTests("PackedBooleanArrDeqTest.testremoveVal_val");
  }
  @Disabled
  @Order(6164256)
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
  @Order(144)
  @Test public void testsize_void(){
    final BasicTest test=PackedBooleanArrDeqMonitor::verifySize;
    test.runAllTests("PackedBooleanArrDeqTest.testsize_void",SHORT_SIZES);
  }
  @Order(144)
  @Test public void testtoArray_IntFunction(){
    final MonitoredFunctionTest test=(monitor,functionGen,functionCallType,randSeed)->{
      if(functionGen.expectedException == null){
        monitor.verifyToArray(functionGen);
      }else{
        Assertions.assertThrows(functionGen.expectedException,()->monitor.verifyToArray(functionGen));
      }
    };
    test.runAllTests("PackedBooleanArrDeqTest.testtoArray_IntFunction",0,SHORT_SIZES);
  }
  @Order(144)
  @Test public void testtoArray_ObjectArray(){
    final BasicTest test=(monitor)->{
      final int size=monitor.size();
      final int inc=Math.max(1,size / 10);
      for(int paramArrLength=0,bound=size + inc;paramArrLength <= bound;paramArrLength+=inc){
        monitor.verifyToArray(new Object[paramArrLength]);
      }
    };
    test.runAllTests("PackedBooleanArrDeqTest.testtoArray_ObjectArray",SHORT_SIZES);
  }
  @Order(144)
  @Test public void testtoArray_void(){
    final BasicTest test=(monitor)->{
      for(final var outputType:monitor.dataType.validOutputTypes()){
        outputType.verifyToArray(monitor);
      }
    };
    test.runAllTests("PackedBooleanArrDeqTest.testtoArray_void",SHORT_SIZES);
  }
  @Order(5164)
  @Test public void testtoString_void(){
      BasicTest test=PackedBooleanArrDeqMonitor::verifyToString;
      test.runAllTests("PackedBooleanArrDeqTest.testtoString_void",SHORT_SIZES);
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
