package omni.impl.seq;

import java.io.Externalizable;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import omni.api.OmniDeque;
import omni.api.OmniIterator;
import omni.impl.CheckedType;
import omni.impl.DataType;
import omni.impl.FunctionCallType;
import omni.impl.IteratorType;
import omni.impl.MonitoredCollection;
import omni.impl.MonitoredDeque;
import omni.impl.MonitoredFunction;
import omni.impl.MonitoredFunctionGen;
import omni.impl.MonitoredObjectOutputStream;
import omni.impl.MonitoredRemoveIfPredicate;
import omni.impl.QueryCastType;
import omni.impl.QueryVal;
import omni.impl.QueryVal.QueryValModification;
import omni.impl.StructType;
import omni.util.OmniArray;
import omni.util.TestExecutorService;
@TestMethodOrder(OrderAnnotation.class)
@Tag("NewTest")
public class ArrDeqTest{
    
    
    
    private static class ArrDeqMonitor implements MonitoredDeque<OmniDeque<?>>{
        final CheckedType checkedType;
        final DataType dataType;
        final OmniDeque<?> seq;
        Object expectedArr;
        int expectedHead;
        int expectedTail;
        int expectedSize;
        int expectedModCount;
        int expectedCapacity;
        
        ArrDeqMonitor(CheckedType checkedType,DataType dataType,int initCap){
            this.checkedType=checkedType;
            this.dataType=dataType;
            final var checked=checkedType.checked;
            switch(dataType) {
            case BOOLEAN:
                if(checked) {
                    this.seq=new BooleanArrDeq.Checked(initCap);
                }else {
                    this.seq=new BooleanArrDeq(initCap);
                }
                break;
            case BYTE:
                if(checked) {
                    this.seq=new ByteArrDeq.Checked(initCap);
                }else {
                    this.seq=new ByteArrDeq(initCap);
                }
                break;
            case CHAR:
                if(checked) {
                    this.seq=new CharArrDeq.Checked(initCap);
                }else {
                    this.seq=new CharArrDeq(initCap);
                }
                break;
            case DOUBLE:
                if(checked) {
                    this.seq=new DoubleArrDeq.Checked(initCap);
                }else {
                    this.seq=new DoubleArrDeq(initCap);
                }
                break;
            case FLOAT:
                if(checked) {
                    this.seq=new FloatArrDeq.Checked(initCap);
                }else {
                    this.seq=new FloatArrDeq(initCap);
                }
                break;
            case INT:
                if(checked) {
                    this.seq=new IntArrDeq.Checked(initCap);
                }else {
                    this.seq=new IntArrDeq(initCap);
                }
                break;
            case LONG:
                if(checked) {
                    this.seq=new LongArrDeq.Checked(initCap);
                }else {
                    this.seq=new LongArrDeq(initCap);
                }
                break;
            case REF:
                if(checked) {
                    this.seq=new RefArrDeq.Checked<>(initCap);
                }else {
                    this.seq=new RefArrDeq<>(initCap);
                }
                break;
            case SHORT:
                if(checked) {
                    this.seq=new ShortArrDeq.Checked(initCap);
                }else {
                    this.seq=new ShortArrDeq(initCap);
                }
                break;
            default:
                throw dataType.invalid();
            }
            updateCollectionState();
        }
        ArrDeqMonitor(CheckedType checkedType,DataType dataType){
            this.checkedType=checkedType;
            this.dataType=dataType;
            final var checked=checkedType.checked;
            switch(dataType) {
            case BOOLEAN:
                if(checked) {
                    this.seq=new BooleanArrDeq.Checked();
                }else {
                    this.seq=new BooleanArrDeq();
                }
                break;
            case BYTE:
                if(checked) {
                    this.seq=new ByteArrDeq.Checked();
                }else {
                    this.seq=new ByteArrDeq();
                }
                break;
            case CHAR:
                if(checked) {
                    this.seq=new CharArrDeq.Checked();
                }else {
                    this.seq=new CharArrDeq();
                }
                break;
            case DOUBLE:
                if(checked) {
                    this.seq=new DoubleArrDeq.Checked();
                }else {
                    this.seq=new DoubleArrDeq();
                }
                break;
            case FLOAT:
                if(checked) {
                    this.seq=new FloatArrDeq.Checked();
                }else {
                    this.seq=new FloatArrDeq();
                }
                break;
            case INT:
                if(checked) {
                    this.seq=new IntArrDeq.Checked();
                }else {
                    this.seq=new IntArrDeq();
                }
                break;
            case LONG:
                if(checked) {
                    this.seq=new LongArrDeq.Checked();
                }else {
                    this.seq=new LongArrDeq();
                }
                break;
            case REF:
                if(checked) {
                    this.seq=new RefArrDeq.Checked<>();
                }else {
                    this.seq=new RefArrDeq<>();
                }
                break;
            case SHORT:
                if(checked) {
                    this.seq=new ShortArrDeq.Checked();
                }else {
                    this.seq=new ShortArrDeq();
                }
                break;
            default:
                throw dataType.invalid();
            }
            updateCollectionState();
        }
        
        @Override
        public void updateRemoveLastState() {
            if(dataType==DataType.REF) {
                ((Object[])expectedArr)[expectedTail]=null;
            }
            if(expectedHead==expectedTail) {
                this.expectedTail=-1;
            }else if(--expectedTail==-1) {
                this.expectedTail=expectedCapacity-1;
            }
            --expectedSize;
            ++expectedModCount;
        }
        @Override
        public void updateRemoveFirstState() {
            if(dataType==DataType.REF) {
                ((Object[])expectedArr)[expectedHead]=null;
            }
            if(expectedHead==expectedTail) {
                this.expectedTail=-1;
            }else if(++expectedHead==expectedCapacity) {
                this.expectedHead=0;
            }
            --expectedSize;
            ++expectedModCount;
        }
       

        private void verifyGetResult(int expectedCursor,Object output,DataType outputType) {
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

        @Override
        public void verifyGetFirstResult(Object result,DataType outputType){
            verifyGetResult(expectedHead,result,outputType);
        }
        @Override
        public void verifyGetLastResult(Object result,DataType outputType){
            verifyGetResult(expectedTail,result,outputType);
            }

        @Override
        public void updateAddState(Object inputVal,DataType inputType){
            if(expectedSize==0) {
                if(expectedCapacity==0) {
                    if(expectedArr==null) {
                        this.expectedArr=dataType.newArray(this.expectedCapacity=1);
                    }else {
                        this.expectedArr=dataType.newArray(this.expectedCapacity=OmniArray.DEFAULT_ARR_SEQ_CAP);
                    }
                }
                this.expectedHead=0;
                this.expectedTail=0;
                dataType.convertValAndStoreInArray(0,this.expectedArr,inputVal,inputType);
            }else {
                int head;
                int tail;
                if((tail=this.expectedTail+1)==(head=this.expectedHead)) {
                    this.expectedHead=0;
                    tail=expectedCapacity;
                    Object newArr=dataType.newArray(expectedCapacity=OmniArray.growBy50Pct(tail));
                    dataType.convertValAndStoreInArray(tail,newArr,inputVal,inputType);
                    this.expectedTail=tail;
                    System.arraycopy(expectedArr,head,newArr,0,tail-=head);
                    System.arraycopy(expectedArr,0,newArr,tail,head);
                    this.expectedArr=newArr;
                }else {
                    if(tail==expectedCapacity) {
                        if(head==0) {
                            System.arraycopy(expectedArr,0,expectedArr=dataType.newArray(expectedCapacity=OmniArray.growBy50Pct(tail)),0,tail);
                        }else {
                            tail=0;
                        }
                    }
                    dataType.convertValAndStoreInArray(tail,expectedArr,inputVal,inputType);
                    this.expectedTail=tail;
                }
            }
            ++expectedModCount;
            ++expectedSize;
        }
        
        @Override
        public void updateRemoveValState(Object inputVal,DataType inputType){
            int head=this.expectedHead;
            int tail=this.expectedTail;
            IntPredicate indexSearcher=dataType.getArrayIndexSearcher(expectedArr,inputVal,inputType);
            ++expectedModCount;
            --expectedSize;
            if(tail<head) {
                int index,bound;
                for(index=head,bound=expectedCapacity-1;index<=bound;++index) {
                    if(indexSearcher.test(index)) {
                        int headDist,tailDist;
                        if((headDist=index-head)<=(tailDist=bound-index)+tail) {
                            System.arraycopy(expectedArr,head,expectedArr,tail=head+1,headDist);
                            if(dataType==DataType.REF) {
                                ((Object[])expectedArr)[head]=null;
                            }
                            this.expectedHead=tail>bound?0:tail;
                        }else {
                            System.arraycopy(expectedArr,index+1,expectedArr,index,tailDist);
                            System.arraycopy(expectedArr,0,expectedArr,bound,1);
                            System.arraycopy(expectedArr,1,expectedArr,0,tail);
                            if(dataType==DataType.REF) {
                                ((Object[])expectedArr)[tail]=null;
                            }
                            this.expectedTail=--tail==-1?bound:tail;
                        }
                        return;
                    }
                }
                for(index=0;;++index) {
                    if(indexSearcher.test(index)) {
                        int headDist,tailDist;
                        if((headDist=bound-head)+index+1<(tailDist=tail-index)) {
                            System.arraycopy(expectedArr,0,expectedArr,1,index);
                            System.arraycopy(expectedArr,bound,expectedArr,0,1);
                            System.arraycopy(expectedArr,head,expectedArr,tail=head+1,headDist);
                            if(dataType==DataType.REF) {
                                ((Object[])expectedArr)[head]=null;
                            }
                            this.expectedHead=tail>bound?0:tail;
                        }else {
                            System.arraycopy(expectedArr,index+1,expectedArr,index,tailDist);
                            if(dataType==DataType.REF) {
                                ((Object[])expectedArr)[tail]=null;
                            }
                            this.expectedTail=--tail==-1?bound:tail;
                        }
                        return;
                    }
                }
            }else {
               int index;
               for(index=head;;++index) {
                   if(indexSearcher.test(index)) {
                       int headDist,tailDist;
                       if((tailDist=tail-index)<=(headDist=index-head)) {
                           if(headDist==0) {
                               this.expectedTail=-1;
                           }else {
                               System.arraycopy(expectedArr,index+1,expectedArr,index,tailDist);
                               this.expectedTail=tail-1;
                           }
                           if(dataType==DataType.REF) {
                               ((Object[])expectedArr)[tail]=null;
                           }
                       }else {
                           System.arraycopy(expectedArr,head,expectedArr,tail=head+1,headDist);
                           if(dataType==DataType.REF) {
                               ((Object[])expectedArr)[head]=null;
                           }
                           this.expectedHead=tail;
                       }
                       return;
                   }
               }
            }
        }

        @Override
        public Object get(int iterationIndex,DataType outputType){
            iterationIndex+=expectedHead;
            if(iterationIndex>=expectedCapacity) {
                iterationIndex-=expectedCapacity;
            }
            return outputType.convertVal(dataType.getFromArray(iterationIndex,expectedArr));
        }

        @Override
        public MonitoredIterator<? extends OmniIterator<?>,OmniDeque<?>> getMonitoredIterator(IteratorType itrType){
            switch(itrType) {
            case AscendingItr:
                return getMonitoredIterator();
            case DescendingItr:
                return getMonitoredDescendingIterator();
            default:
                throw itrType.invalid();
            }
        }

        @Override
        public CheckedType getCheckedType(){
            return checkedType;
        }

        @Override
        public OmniDeque<?> getCollection(){
            return seq;
        }

        @Override
        public DataType getDataType(){
            return dataType;
        }
        
        private void removeLast() {
            switch(dataType) {
            case BOOLEAN:
                ((BooleanArrDeq)seq).removeLastBoolean();
                break;
            case BYTE:
                ((ByteArrDeq)seq).removeLastByte();
                break;
            case CHAR:
                ((CharArrDeq)seq).removeLastChar();
                break;
            case DOUBLE:
                ((DoubleArrDeq)seq).removeLastDouble();
                break;
            case FLOAT:
                ((FloatArrDeq)seq).removeLastFloat();
                break;
            case INT:
                ((IntArrDeq)seq).removeLastInt();
                break;
            case LONG:
                ((LongArrDeq)seq).removeLastLong();
                break;
            case REF:
                ((RefArrDeq<?>)seq).removeLast();
                ((Object[])expectedArr)[expectedTail]=null;
                break;
            case SHORT:
                ((ShortArrDeq)seq).removeLastShort();
                break;
            default:
                throw dataType.invalid();
            }
            if(expectedHead==expectedTail) {
                this.expectedTail=-1;
            }else if(--expectedTail==-1) {
                this.expectedTail=expectedCapacity-1;
            }
            --expectedSize;
            ++expectedModCount;
        }
        private void removeFirst() {
            switch(dataType) {
            case BOOLEAN:
                ((BooleanArrDeq)seq).popBoolean();
                break;
            case BYTE:
                ((ByteArrDeq)seq).popByte();
                break;
            case CHAR:
                ((CharArrDeq)seq).popChar();
                break;
            case DOUBLE:
                ((DoubleArrDeq)seq).popDouble();
                break;
            case FLOAT:
                ((FloatArrDeq)seq).popFloat();
                break;
            case INT:
                ((IntArrDeq)seq).popInt();
                break;
            case LONG:
                ((LongArrDeq)seq).popLong();
                break;
            case REF:
                ((RefArrDeq<?>)seq).pop();
                ((Object[])expectedArr)[expectedHead]=null;
                break;
            case SHORT:
                ((ShortArrDeq)seq).popShort();
                break;
            default:
                throw dataType.invalid();
            }
            if(expectedHead==expectedTail) {
                this.expectedTail=-1;
            }else if(++expectedHead==expectedCapacity) {
                this.expectedHead=0;
            }
            --expectedSize;
            ++expectedModCount;
        }

        
        private abstract class AbstractItrMonitor implements MonitoredCollection.MonitoredIterator<OmniIterator<?>,OmniDeque<?>>{
            final OmniIterator<?> itr;
            int expectedCursor;
            int expectedLastRet;
            int expectedItrModCount;
            int numLeft;
            AbstractItrMonitor(OmniIterator<?> itr,int expectedCursor,int numLeft){
                this.itr=itr;
                this.expectedCursor=expectedCursor;
                this.expectedLastRet=-1;
                this.expectedItrModCount=expectedModCount;
                this.numLeft=numLeft;
            }
            @Override
            public boolean nextWasJustCalled(){
                return this.expectedLastRet!=-1;
            }
            
            @Override
            public void verifyNextResult(DataType outputType,Object result){
                verifyGetResult(expectedCursor,result,outputType);
            }
          
            @Override
            public OmniIterator<?> getIterator(){
                return itr;
            }
           
            @Override
            public MonitoredCollection<OmniDeque<?>> getMonitoredCollection(){
                return ArrDeqMonitor.this;
            }
            @Override
            public boolean hasNext(){
                return numLeft>0;
            }
            @Override
            public int getNumLeft(){
                return numLeft;
            }
            private IntConsumer getForEachRemainingVerifier(MonitoredFunction function){
                var functionItr=function.iterator();
                switch(dataType) {
                case BOOLEAN:{
                    var expectedArr=(boolean[])ArrDeqMonitor.this.expectedArr;
                    return index->Assertions.assertEquals((boolean)functionItr.next(),expectedArr[index]);
                }
                case BYTE:{
                    var expectedArr=(byte[])ArrDeqMonitor.this.expectedArr;
                    return index->Assertions.assertEquals((byte)functionItr.next(),expectedArr[index]);
                }
                case CHAR:{
                    var expectedArr=(char[])ArrDeqMonitor.this.expectedArr;
                    return index->Assertions.assertEquals((char)functionItr.next(),expectedArr[index]);
                }
                case SHORT:{
                    var expectedArr=(short[])ArrDeqMonitor.this.expectedArr;
                    return index->Assertions.assertEquals((short)functionItr.next(),expectedArr[index]);
                }
                case INT:{
                    var expectedArr=(int[])ArrDeqMonitor.this.expectedArr;
                    return index->Assertions.assertEquals((int)functionItr.next(),expectedArr[index]);
                }
                case LONG:{
                    var expectedArr=(long[])ArrDeqMonitor.this.expectedArr;
                    return index->Assertions.assertEquals((long)functionItr.next(),expectedArr[index]);
                }
                case FLOAT:{
                    var expectedArr=(float[])ArrDeqMonitor.this.expectedArr;
                    return index->Assertions.assertEquals((float)functionItr.next(),expectedArr[index]);
                }
                case DOUBLE:{
                    var expectedArr=(double[])ArrDeqMonitor.this.expectedArr;
                    return index->Assertions.assertEquals((double)functionItr.next(),expectedArr[index]);
                }
                case REF:{
                    var expectedArr=(Object[])ArrDeqMonitor.this.expectedArr;
                    return index->Assertions.assertSame(functionItr.next(),expectedArr[index]);
                }
                default:
                    throw dataType.invalid();
                }
            }
            abstract void fragmentedRemove();
            abstract void nonfragmentedRemove();
            @Override
            public void updateItrRemoveState(){
                ++expectedItrModCount;
                ++expectedModCount;
                int tail=expectedTail;
                switch(Integer.signum(tail-expectedHead)) {
                case -1:
                    fragmentedRemove();
                    break;
                case 0:
                    expectedTail=-1;
                    if(dataType==DataType.REF) {
                        ((Object[])expectedArr)[tail]=null;
                    }
                    break;
                default:
                    nonfragmentedRemove();
                }
                this.expectedLastRet=-1;
                
            }
            
        }
        private class DescendingItrMonitor extends AbstractItrMonitor{
            DescendingItrMonitor(){
                super(seq.descendingIterator(),expectedTail,expectedSize);
            }
            @Override
            public void updateItrNextState(){
                this.expectedLastRet=expectedCursor;
                --numLeft;
                if(expectedCursor==expectedHead) {
                    expectedCursor=-1;
                }else if(--expectedCursor==-1) {
                    expectedCursor=expectedCapacity-1;
                }
            }
            @Override
            public IteratorType getIteratorType(){
                return IteratorType.DescendingItr;
            }
            @Override
            public void verifyForEachRemaining(MonitoredFunction function){
                if(expectedCursor!=-1) {
                    IntConsumer verifier=super.getForEachRemainingVerifier(function);
                    int i=expectedCursor;
                    if(i<expectedHead) {
                        for(;i>=0;--i) {
                            verifier.accept(i);
                        }
                        i=expectedCapacity-1;
                    }
                    for(;i>=expectedHead;++i) {
                        verifier.accept(i);
                    }
                    this.expectedLastRet=expectedHead;
                    this.expectedCursor=-1;
                    Assertions.assertEquals(function.size(),numLeft);
                    this.numLeft=0;
                }else {
                    Assertions.assertTrue(function.isEmpty());
                }
            }
            @Override
            public void verifyCloneHelper(Object clone){
                final var checked=checkedType.checked;
                switch(dataType) {
                case BOOLEAN:
                   Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.BooleanArrDeq.AbstractDeqItr.cursor(itr));
                   if(checked) {
                       Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanArrDeq.Checked.DescendingItr.root(itr));
                       Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.BooleanArrDeq.Checked.DescendingItr.modCount(itr));
                       Assertions.assertEquals(expectedLastRet,FieldAndMethodAccessor.BooleanArrDeq.Checked.DescendingItr.lastRet(itr));
                   }else {
                       Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanArrDeq.DescendingItr.root(itr));
                   }
                   break;
                case BYTE:
                    Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.ByteArrDeq.AbstractDeqItr.cursor(itr));
                    if(checked) {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.ByteArrDeq.Checked.DescendingItr.root(itr));
                        Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.ByteArrDeq.Checked.DescendingItr.modCount(itr));
                        Assertions.assertEquals(expectedLastRet,FieldAndMethodAccessor.ByteArrDeq.Checked.DescendingItr.lastRet(itr));
                    }else {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.ByteArrDeq.DescendingItr.root(itr));
                    }
                    break;
                case CHAR:
                    Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.CharArrDeq.AbstractDeqItr.cursor(itr));
                    if(checked) {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.CharArrDeq.Checked.DescendingItr.root(itr));
                        Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.CharArrDeq.Checked.DescendingItr.modCount(itr));
                        Assertions.assertEquals(expectedLastRet,FieldAndMethodAccessor.CharArrDeq.Checked.DescendingItr.lastRet(itr));
                    }else {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.CharArrDeq.DescendingItr.root(itr));
                    }
                    break;
                case SHORT:
                    Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.ShortArrDeq.AbstractDeqItr.cursor(itr));
                    if(checked) {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.ShortArrDeq.Checked.DescendingItr.root(itr));
                        Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.ShortArrDeq.Checked.DescendingItr.modCount(itr));
                        Assertions.assertEquals(expectedLastRet,FieldAndMethodAccessor.ShortArrDeq.Checked.DescendingItr.lastRet(itr));
                    }else {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.ShortArrDeq.DescendingItr.root(itr));
                    }
                    break;
                case INT:
                    Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.IntArrDeq.AbstractDeqItr.cursor(itr));
                    if(checked) {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.IntArrDeq.Checked.DescendingItr.root(itr));
                        Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.IntArrDeq.Checked.DescendingItr.modCount(itr));
                        Assertions.assertEquals(expectedLastRet,FieldAndMethodAccessor.IntArrDeq.Checked.DescendingItr.lastRet(itr));
                    }else {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.IntArrDeq.DescendingItr.root(itr));
                    }
                    break;
                case LONG:
                    Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.LongArrDeq.AbstractDeqItr.cursor(itr));
                    if(checked) {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.LongArrDeq.Checked.DescendingItr.root(itr));
                        Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.LongArrDeq.Checked.DescendingItr.modCount(itr));
                        Assertions.assertEquals(expectedLastRet,FieldAndMethodAccessor.LongArrDeq.Checked.DescendingItr.lastRet(itr));
                    }else {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.LongArrDeq.DescendingItr.root(itr));
                    }
                    break;
                case FLOAT:
                    Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.FloatArrDeq.AbstractDeqItr.cursor(itr));
                    if(checked) {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.FloatArrDeq.Checked.DescendingItr.root(itr));
                        Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.FloatArrDeq.Checked.DescendingItr.modCount(itr));
                        Assertions.assertEquals(expectedLastRet,FieldAndMethodAccessor.FloatArrDeq.Checked.DescendingItr.lastRet(itr));
                    }else {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.FloatArrDeq.DescendingItr.root(itr));
                    }
                    break;
                case DOUBLE:
                    Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.DoubleArrDeq.AbstractDeqItr.cursor(itr));
                    if(checked) {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleArrDeq.Checked.DescendingItr.root(itr));
                        Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.DoubleArrDeq.Checked.DescendingItr.modCount(itr));
                        Assertions.assertEquals(expectedLastRet,FieldAndMethodAccessor.DoubleArrDeq.Checked.DescendingItr.lastRet(itr));
                    }else {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleArrDeq.DescendingItr.root(itr));
                    }
                    break;
                case REF:
                    Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.RefArrDeq.AbstractDeqItr.cursor(itr));
                    if(checked) {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.RefArrDeq.Checked.DescendingItr.root(itr));
                        Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.RefArrDeq.Checked.DescendingItr.modCount(itr));
                        Assertions.assertEquals(expectedLastRet,FieldAndMethodAccessor.RefArrDeq.Checked.DescendingItr.lastRet(itr));
                    }else {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.RefArrDeq.DescendingItr.root(itr));
                    }
                    break;
                default:
                    throw dataType.invalid();
                }
            }
            @Override
            void fragmentedRemove() {
                int head=expectedHead;
                int tail=expectedTail;
                int lastRet=expectedLastRet;
                int arrBound=expectedCapacity-1;
                var arr=ArrDeqMonitor.this.expectedArr;
                int headDist=expectedLastRet-head;
                if(headDist>=0) {
                    int tailDist=arrBound-lastRet;
                    if(headDist<=tailDist+tail+1) {
                        if(headDist==0) {
                            if(tailDist==0) {
                                expectedHead=0;
                            }else {
                                expectedHead=head+1;
                            }
                            if(dataType==DataType.REF) {
                                ((Object[])arr)[head]=null;
                            }
                        }else {
                            System.arraycopy(arr,tail=head,arr,++head,headDist);
                            if(dataType==DataType.REF) {
                                ((Object[])arr)[tail]=null;
                            }
                            expectedHead=head;
                            this.expectedCursor=lastRet;
                        }
                    }else {
                        System.arraycopy(arr,lastRet+1,arr,lastRet,tailDist);
                        System.arraycopy(arr,0,arr,arrBound,1);
                        System.arraycopy(arr,1,arr,0,tail);
                        if(dataType==DataType.REF) {
                            ((Object[])arr)[tail]=null;
                        }
                        if(tail==0) {
                            expectedTail=arrBound;
                        }else {
                            expectedTail=tail-1;
                        }
                    }
                }else {
                    int tailDist=tail-lastRet;
                    headDist=arrBound-head;
                    if(tailDist<=headDist+lastRet+1) {
                        if(tailDist==0) {
                            if(lastRet==0) {
                                expectedTail=arrBound;
                            }else {
                                expectedTail=tail-1;
                            }
                        }else {
                            System.arraycopy(arr,lastRet+1,arr,lastRet,tailDist);
                            expectedTail=tail-1;
                        }
                        if(dataType==DataType.REF) {
                            ((Object[])arr)[tail]=null;
                        }
                    }else {
                        System.arraycopy(arr,0,arr,1,lastRet);
                        System.arraycopy(arr,arrBound,arr,0,1);
                        int tmp;
                        System.arraycopy(arr,tmp=head,arr,++head,headDist);
                        if(dataType==DataType.REF) {
                            ((Object[])arr)[tmp]=null;
                        }
                        expectedHead=head;
                        this.expectedCursor=lastRet;
                    }
                }
                
            }
            @Override
            void nonfragmentedRemove() {
                int head=expectedHead;
                int tail=expectedTail;
                int lastRet=expectedLastRet;
                int headDist=lastRet-head;
                int tailDist=tail-lastRet;
                final var arr=expectedArr;
                if(tailDist<=headDist) {
                    System.arraycopy(arr,lastRet+1,arr,lastRet,tailDist);
                    if(dataType==DataType.REF) {
                        ((Object[])arr)[tail]=null;
                    }
                    expectedTail=tail-1;
                   
                }else {
                    //root.head=pullUp(root.arr,head,headDist);
                    int tmp;
                    System.arraycopy(arr,tmp=head,arr,++head,headDist);
                    if(dataType==DataType.REF) {
                        ((Object[])arr)[tmp]=null;
                    }
                    expectedHead=head;
                    this.expectedCursor=lastRet;
                }
                
            }
        }
        private class AscendingItrMonitor extends AbstractItrMonitor{
            AscendingItrMonitor(){
                super(seq.iterator(),expectedTail!=-1?expectedHead:-1,expectedSize);
            }
            @Override
            public void updateItrNextState(){
                this.expectedLastRet=expectedCursor;
                --numLeft;
                if(expectedCursor==expectedTail) {
                    expectedCursor=-1;
                }else if(++expectedCursor==expectedCapacity) {
                    expectedCursor=0;
                }
            }
            @Override
            public IteratorType getIteratorType(){
                return IteratorType.AscendingItr;
            }
            @Override
            public void verifyForEachRemaining(MonitoredFunction function){
                if(expectedCursor!=-1) {
                    IntConsumer verifier=super.getForEachRemainingVerifier(function);
                    int i=expectedCursor;
                    if(i>expectedTail) {
                        for(;i<expectedCapacity;++i) {
                            verifier.accept(i);
                        }
                        i=0;
                    }
                    for(;i<=expectedTail;++i) {
                        verifier.accept(i);
                    }
                    this.expectedLastRet=expectedTail;
                    this.expectedCursor=-1;
                    Assertions.assertEquals(function.size(),numLeft);
                    this.numLeft=0;
                }else {
                    Assertions.assertTrue(function.isEmpty());
                }
            }
            @Override
            public void verifyCloneHelper(Object clone){
                final var checked=checkedType.checked;
                switch(dataType) {
                case BOOLEAN:
                   Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.BooleanArrDeq.AbstractDeqItr.cursor(itr));
                   if(checked) {
                       Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanArrDeq.Checked.AscendingItr.root(itr));
                       Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.BooleanArrDeq.Checked.AscendingItr.modCount(itr));
                       Assertions.assertEquals(expectedLastRet,FieldAndMethodAccessor.BooleanArrDeq.Checked.AscendingItr.lastRet(itr));
                   }else {
                       Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanArrDeq.AscendingItr.root(itr));
                   }
                   break;
                case BYTE:
                    Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.ByteArrDeq.AbstractDeqItr.cursor(itr));
                    if(checked) {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.ByteArrDeq.Checked.AscendingItr.root(itr));
                        Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.ByteArrDeq.Checked.AscendingItr.modCount(itr));
                        Assertions.assertEquals(expectedLastRet,FieldAndMethodAccessor.ByteArrDeq.Checked.AscendingItr.lastRet(itr));
                    }else {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.ByteArrDeq.AscendingItr.root(itr));
                    }
                    break;
                case CHAR:
                    Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.CharArrDeq.AbstractDeqItr.cursor(itr));
                    if(checked) {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.CharArrDeq.Checked.AscendingItr.root(itr));
                        Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.CharArrDeq.Checked.AscendingItr.modCount(itr));
                        Assertions.assertEquals(expectedLastRet,FieldAndMethodAccessor.CharArrDeq.Checked.AscendingItr.lastRet(itr));
                    }else {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.CharArrDeq.AscendingItr.root(itr));
                    }
                    break;
                case SHORT:
                    Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.ShortArrDeq.AbstractDeqItr.cursor(itr));
                    if(checked) {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.ShortArrDeq.Checked.AscendingItr.root(itr));
                        Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.ShortArrDeq.Checked.AscendingItr.modCount(itr));
                        Assertions.assertEquals(expectedLastRet,FieldAndMethodAccessor.ShortArrDeq.Checked.AscendingItr.lastRet(itr));
                    }else {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.ShortArrDeq.AscendingItr.root(itr));
                    }
                    break;
                case INT:
                    Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.IntArrDeq.AbstractDeqItr.cursor(itr));
                    if(checked) {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.IntArrDeq.Checked.AscendingItr.root(itr));
                        Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.IntArrDeq.Checked.AscendingItr.modCount(itr));
                        Assertions.assertEquals(expectedLastRet,FieldAndMethodAccessor.IntArrDeq.Checked.AscendingItr.lastRet(itr));
                    }else {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.IntArrDeq.AscendingItr.root(itr));
                    }
                    break;
                case LONG:
                    Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.LongArrDeq.AbstractDeqItr.cursor(itr));
                    if(checked) {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.LongArrDeq.Checked.AscendingItr.root(itr));
                        Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.LongArrDeq.Checked.AscendingItr.modCount(itr));
                        Assertions.assertEquals(expectedLastRet,FieldAndMethodAccessor.LongArrDeq.Checked.AscendingItr.lastRet(itr));
                    }else {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.LongArrDeq.AscendingItr.root(itr));
                    }
                    break;
                case FLOAT:
                    Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.FloatArrDeq.AbstractDeqItr.cursor(itr));
                    if(checked) {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.FloatArrDeq.Checked.AscendingItr.root(itr));
                        Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.FloatArrDeq.Checked.AscendingItr.modCount(itr));
                        Assertions.assertEquals(expectedLastRet,FieldAndMethodAccessor.FloatArrDeq.Checked.AscendingItr.lastRet(itr));
                    }else {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.FloatArrDeq.AscendingItr.root(itr));
                    }
                    break;
                case DOUBLE:
                    Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.DoubleArrDeq.AbstractDeqItr.cursor(itr));
                    if(checked) {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleArrDeq.Checked.AscendingItr.root(itr));
                        Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.DoubleArrDeq.Checked.AscendingItr.modCount(itr));
                        Assertions.assertEquals(expectedLastRet,FieldAndMethodAccessor.DoubleArrDeq.Checked.AscendingItr.lastRet(itr));
                    }else {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleArrDeq.AscendingItr.root(itr));
                    }
                    break;
                case REF:
                    Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.RefArrDeq.AbstractDeqItr.cursor(itr));
                    if(checked) {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.RefArrDeq.Checked.AscendingItr.root(itr));
                        Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.RefArrDeq.Checked.AscendingItr.modCount(itr));
                        Assertions.assertEquals(expectedLastRet,FieldAndMethodAccessor.RefArrDeq.Checked.AscendingItr.lastRet(itr));
                    }else {
                        Assertions.assertSame(seq,FieldAndMethodAccessor.RefArrDeq.AscendingItr.root(itr));
                    }
                    break;
                default:
                    throw dataType.invalid();
                }
            }
            @Override
            void fragmentedRemove() {
                int head=expectedHead;
                int tail=expectedTail;
                int lastRet=expectedLastRet;
                int arrBound=expectedCapacity-1;
                var arr=ArrDeqMonitor.this.expectedArr;
                int headDist=expectedLastRet-head;
                if(headDist>=0) {
                    int tailDist=arrBound-lastRet;
                    if(headDist<=tailDist+tail+1) {
                        if(headDist==0) {
                            if(tailDist==0) {
                                expectedHead=0;
                            }else {
                                expectedHead=head+1;
                            }
                            if(dataType==DataType.REF) {
                                ((Object[])arr)[head]=null;
                            }
                        }else {
                            System.arraycopy(expectedArr,tail=head,arr,++head,headDist);
                            if(dataType==DataType.REF) {
                                ((Object[])arr)[tail]=null;
                            }
                            expectedHead=head;
                        }
                    }else {
                        System.arraycopy(arr,lastRet+1,arr,lastRet,tailDist);
                        System.arraycopy(arr,0,arr,arrBound,1);
                        System.arraycopy(arr,1,arr,0,tail);
                        if(dataType==DataType.REF) {
                            ((Object[])arr)[tail]=null;
                        }
                        if(tail==0) {
                            expectedTail=arrBound;
                        }else {
                            expectedTail=tail-1;
                        }
                        this.expectedCursor=lastRet;
                    }
                }else {
                    int tailDist=tail-lastRet;
                    headDist=arrBound-head;
                    if(tailDist<=headDist+lastRet+1) {
                        if(tailDist==0) {
                            if(lastRet==0) {
                                expectedTail=arrBound;
                            }else {
                                expectedTail=tail-1;
                            }
                        }else {
                            System.arraycopy(arr,lastRet+1,arr,lastRet,tailDist);
                            expectedTail=tail-1;
                            expectedCursor=lastRet;
                        }
                        if(dataType==DataType.REF) {
                            ((Object[])arr)[tail]=null;
                        }
                    }else {
                        System.arraycopy(arr,0,arr,1,lastRet);
                        System.arraycopy(arr,arrBound,arr,0,1);
                        int tmp;
                        System.arraycopy(arr,tmp=head,arr,++head,headDist);
                        if(dataType==DataType.REF) {
                            ((Object[])arr)[tmp]=null;
                        }
                        expectedHead=head;
                    }
                }
            
            }
            @Override
            void nonfragmentedRemove() {
                int head=expectedHead;
                int tail=expectedTail;
                int lastRet=expectedLastRet;
                int headDist=lastRet-head;
                int tailDist=tail-lastRet;
                final var arr=expectedArr;
                if(headDist<=tailDist) {
                    int tmp;
                    System.arraycopy(arr,tmp=head,arr,++head,headDist);
                    if(dataType==DataType.REF) {
                        ((Object[])arr)[tmp]=null;
                    }
                    expectedHead=head;
                }else {
                    System.arraycopy(arr,lastRet+1,arr,lastRet,tailDist);
                    if(dataType==DataType.REF) {
                        ((Object[])arr)[tail]=null;
                    }
                    this.expectedCursor=lastRet;
                    expectedTail=tail-1;
                }
                
            }
            
        }
        
        
        @Override
        public MonitoredIterator<? extends OmniIterator<?>,OmniDeque<?>> getMonitoredIterator(){
            return new AscendingItrMonitor();
        }

        @Override
        public MonitoredIterator<?,OmniDeque<?>> getMonitoredDescendingIterator(){
            return new DescendingItrMonitor();
        }

        @Override
        public MonitoredIterator<? extends OmniIterator<?>,OmniDeque<?>> getMonitoredIterator(int index,
                IteratorType itrType){
            var itrMonitor=getMonitoredIterator(itrType);
            while(--index>=0 && itrMonitor.hasNext()) {
                itrMonitor.iterateForward();
            }
            return itrMonitor;
        }

        @Override
        public StructType getStructType(){
            return StructType.ArrDeq;
        }

        @Override
        public void modCollection(){
            switch(dataType) {
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

        @Override
        public void modParent(){
            throw new UnsupportedOperationException();
        }

        @Override
        public void modRoot(){
            throw new UnsupportedOperationException();
            }

        @Override
        public int size(){
            return this.expectedSize;
        }

        @Override
        public void updateClearState(){
            if(expectedSize!=0) {
                if(dataType==DataType.REF) {
                    final var expectedArr=(Object[])this.expectedArr;
                    int tail,head;
                    if((tail=expectedTail)<(head=expectedHead)) {
                        int bound=expectedCapacity;
                        do {
                            expectedArr[head]=null;
                        }while(++head!=bound);
                        head=0;
                    }
                    do {
                        expectedArr[head]=null;
                    }while(++head<=tail);
                }
                this.expectedTail=-1;
                this.expectedSize=0;
                ++expectedModCount;
            }
        }

        @Override
        public void updateCollectionState(){
            Object actualArr;
            int actualCapacity;
            final var checked=checkedType.checked;
            switch(dataType) {
            case BOOLEAN:{
                var cast=(BooleanArrDeq)seq;
                this.expectedHead=cast.head;
                this.expectedTail=cast.tail;
                actualArr=cast.arr;
                actualCapacity=cast.arr.length;
                if(checked) {
                    this.expectedModCount=((BooleanArrDeq.Checked)cast).modCount;
                }
                break;
            }
            case BYTE:{
                var cast=(ByteArrDeq)seq;
                this.expectedHead=cast.head;
                this.expectedTail=cast.tail;
                actualArr=cast.arr;
                actualCapacity=cast.arr.length;
                if(checked) {
                    this.expectedModCount=((ByteArrDeq.Checked)cast).modCount;
                }
                break;
            }
            case CHAR:{
                var cast=(CharArrDeq)seq;
                this.expectedHead=cast.head;
                this.expectedTail=cast.tail;
                actualArr=cast.arr;
                actualCapacity=cast.arr.length;
                if(checked) {
                    this.expectedModCount=((CharArrDeq.Checked)cast).modCount;
                }
                break;
            }
            case SHORT:{
                var cast=(ShortArrDeq)seq;
                this.expectedHead=cast.head;
                this.expectedTail=cast.tail;
                actualArr=cast.arr;
                actualCapacity=cast.arr.length;
                if(checked) {
                    this.expectedModCount=((ShortArrDeq.Checked)cast).modCount;
                }
                break;
            }
            case INT:{
                var cast=(IntArrDeq)seq;
                this.expectedHead=cast.head;
                this.expectedTail=cast.tail;
                actualArr=cast.arr;
                actualCapacity=cast.arr.length;
                if(checked) {
                    this.expectedModCount=((IntArrDeq.Checked)cast).modCount;
                }
                break;
            }
            case LONG:{
                var cast=(LongArrDeq)seq;
                this.expectedHead=cast.head;
                this.expectedTail=cast.tail;
                actualArr=cast.arr;
                actualCapacity=cast.arr.length;
                if(checked) {
                    this.expectedModCount=((LongArrDeq.Checked)cast).modCount;
                }
                break;
            }
            case FLOAT:{
                var cast=(FloatArrDeq)seq;
                this.expectedHead=cast.head;
                this.expectedTail=cast.tail;
                actualArr=cast.arr;
                actualCapacity=cast.arr.length;
                if(checked) {
                    this.expectedModCount=((FloatArrDeq.Checked)cast).modCount;
                }
                break;
            }
            case DOUBLE:{
                var cast=(DoubleArrDeq)seq;
                this.expectedHead=cast.head;
                this.expectedTail=cast.tail;
                actualArr=cast.arr;
                actualCapacity=cast.arr.length;
                if(checked) {
                    this.expectedModCount=((DoubleArrDeq.Checked)cast).modCount;
                }
                break;
            }
            case REF:{
                var cast=(RefArrDeq<?>)seq;
                this.expectedHead=cast.head;
                this.expectedTail=cast.tail;
                actualArr=cast.arr;
                actualCapacity=cast.arr.length;
                if(checked) {
                    this.expectedModCount=((RefArrDeq.Checked<?>)cast).modCount;
                }
                break;
            }
            default:
                throw dataType.invalid();
            }
            if(actualArr==null || actualArr==dataType.defaultArr) {
                this.expectedArr=actualArr;
                this.expectedCapacity=0;
                this.expectedSize=0;
            }else if(expectedCapacity!=actualCapacity) {
                this.expectedCapacity=actualCapacity;
                this.expectedArr=dataType.newArray(actualCapacity);
                if(expectedTail==-1) {
                    this.expectedSize=0;
                }else if(expectedTail<expectedHead) {
                    System.arraycopy(actualArr,expectedHead,expectedArr,expectedHead,actualCapacity-expectedHead);
                    System.arraycopy(actualArr,0,expectedArr,0,expectedTail+1);
                    this.expectedSize=actualCapacity-expectedHead+expectedTail+1;
                }else {
                    System.arraycopy(actualArr,expectedHead,expectedArr,expectedHead,expectedSize=expectedTail+1-expectedHead);
                }
            }else if(dataType==DataType.REF) {
                System.arraycopy(actualArr,0,expectedArr,0,actualCapacity);
                if(expectedTail==-1) {
                    this.expectedSize=0;
                }else {
                    expectedSize=expectedTail+1-expectedHead;
                    if(expectedSize<=0) {
                        expectedSize+=actualCapacity;
                    }
                }
            }else if(expectedTail==-1) {
                this.expectedSize=0;
            }else if(expectedTail<expectedHead) {
                System.arraycopy(actualArr,expectedHead,expectedArr,expectedHead,actualCapacity-expectedHead);
                System.arraycopy(actualArr,0,expectedArr,0,expectedTail+1);
                this.expectedSize=actualCapacity-expectedHead+expectedTail+1;
            }else {
                System.arraycopy(actualArr,expectedHead,expectedArr,expectedHead,expectedSize=expectedTail+1-expectedHead);
            }
        }

        
        
        @Override
        public void verifyCollectionState(){
            int actualHead;
            int actualTail;
            int actualCapacity;
            Object actualArr;
            final var checked=this.checkedType.checked;
            IntConsumer indexVerifier;
            switch(dataType) {
            case BOOLEAN:{
                var castSeq=(BooleanArrDeq)seq;
                actualHead=castSeq.head;
                actualTail=castSeq.tail;
                var castActualArr=castSeq.arr;
                actualArr=castActualArr;
                actualCapacity=castActualArr==null?0:castActualArr.length;
                if(checked) {
                    Assertions.assertEquals(expectedModCount,((BooleanArrDeq.Checked)castSeq).modCount);
                }
                var expectedArr=(boolean[])this.expectedArr;
                indexVerifier=index->Assertions.assertEquals(expectedArr[index],castActualArr[index]);
                break;
            }
            case BYTE:{
                var castSeq=(ByteArrDeq)seq;
                actualHead=castSeq.head;
                actualTail=castSeq.tail;
                var castActualArr=castSeq.arr;
                actualArr=castActualArr;
                actualCapacity=castActualArr==null?0:castActualArr.length;
                if(checked) {
                    Assertions.assertEquals(expectedModCount,((ByteArrDeq.Checked)castSeq).modCount);
                }
                var expectedArr=(byte[])this.expectedArr;
                indexVerifier=index->Assertions.assertEquals(expectedArr[index],castActualArr[index]);
                break;
            }
            case CHAR:{
                var castSeq=(CharArrDeq)seq;
                actualHead=castSeq.head;
                actualTail=castSeq.tail;
                var castActualArr=castSeq.arr;
                actualArr=castActualArr;
                actualCapacity=castActualArr==null?0:castActualArr.length;
                if(checked) {
                    Assertions.assertEquals(expectedModCount,((CharArrDeq.Checked)castSeq).modCount);
                }
                var expectedArr=(char[])this.expectedArr;
                indexVerifier=index->Assertions.assertEquals(expectedArr[index],castActualArr[index]);
                break;
            }
            case SHORT:{
                var castSeq=(ShortArrDeq)seq;
                actualHead=castSeq.head;
                actualTail=castSeq.tail;
                var castActualArr=castSeq.arr;
                actualArr=castActualArr;
                actualCapacity=castActualArr==null?0:castActualArr.length;
                if(checked) {
                    Assertions.assertEquals(expectedModCount,((ShortArrDeq.Checked)castSeq).modCount);
                }
                var expectedArr=(short[])this.expectedArr;
                indexVerifier=index->Assertions.assertEquals(expectedArr[index],castActualArr[index]);
                break;
            }
            case INT:{
                var castSeq=(IntArrDeq)seq;
                actualHead=castSeq.head;
                actualTail=castSeq.tail;
                var castActualArr=castSeq.arr;
                actualArr=castActualArr;
                actualCapacity=castActualArr==null?0:castActualArr.length;
                if(checked) {
                    Assertions.assertEquals(expectedModCount,((IntArrDeq.Checked)castSeq).modCount);
                }
                var expectedArr=(int[])this.expectedArr;
                indexVerifier=index->Assertions.assertEquals(expectedArr[index],castActualArr[index]);
                break;
            }
            case LONG:{
                var castSeq=(LongArrDeq)seq;
                actualHead=castSeq.head;
                actualTail=castSeq.tail;
                var castActualArr=castSeq.arr;
                actualArr=castActualArr;
                actualCapacity=castActualArr==null?0:castActualArr.length;
                if(checked) {
                    Assertions.assertEquals(expectedModCount,((LongArrDeq.Checked)castSeq).modCount);
                }
                var expectedArr=(long[])this.expectedArr;
                indexVerifier=index->Assertions.assertEquals(expectedArr[index],castActualArr[index]);
                break;
            }
            case FLOAT:{
                var castSeq=(FloatArrDeq)seq;
                actualHead=castSeq.head;
                actualTail=castSeq.tail;
                var castActualArr=castSeq.arr;
                actualArr=castActualArr;
                actualCapacity=castActualArr==null?0:castActualArr.length;
                if(checked) {
                    Assertions.assertEquals(expectedModCount,((FloatArrDeq.Checked)castSeq).modCount);
                }
                var expectedArr=(float[])this.expectedArr;
                indexVerifier=index->Assertions.assertEquals(expectedArr[index],castActualArr[index]);
                break;
            }
            case DOUBLE:{
                var castSeq=(DoubleArrDeq)seq;
                actualHead=castSeq.head;
                actualTail=castSeq.tail;
                var castActualArr=castSeq.arr;
                actualArr=castActualArr;
                actualCapacity=castActualArr==null?0:castActualArr.length;
                if(checked) {
                    Assertions.assertEquals(expectedModCount,((DoubleArrDeq.Checked)castSeq).modCount);
                }
                var expectedArr=(double[])this.expectedArr;
                indexVerifier=index->Assertions.assertEquals(expectedArr[index],castActualArr[index]);
                break;
            }
            case REF:{
                var castSeq=(RefArrDeq<?>)seq;
                actualHead=castSeq.head;
                actualTail=castSeq.tail;
                var castActualArr=castSeq.arr;
                actualArr=castActualArr;
                actualCapacity=castActualArr==null?0:castActualArr.length;
                if(checked) {
                    Assertions.assertEquals(expectedModCount,((RefArrDeq.Checked<?>)castSeq).modCount);
                }
                
                var expectedArr=(Object[])this.expectedArr;
                indexVerifier=index->Assertions.assertSame(expectedArr[index],castActualArr[index]);
                if(actualTail==-1) {
                    if(actualCapacity!=0) {
                        for(int i=actualCapacity;--i>=0;) {
                            Assertions.assertNull(castActualArr[i]);
                        }
                    }
                }else {
                    if(actualTail<actualHead) {
                        for(int i=actualTail+1;i<actualHead;++i) {
                            Assertions.assertNull(castActualArr[i]);
                        }
                    }else {
                        for(int i=0;i<actualHead;++i) {
                            Assertions.assertNull(castActualArr[i]);
                        }
                        for(int i=actualTail;++i<actualCapacity;) {
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
            Assertions.assertTrue(actualCapacity>0 || actualArr==null || actualArr==dataType.defaultArr);
            if(actualTail==-1) {
                Assertions.assertEquals(0,expectedSize);
            }else {
                if(actualTail<actualHead) {
                    Assertions.assertEquals(expectedSize,actualTail+1+actualCapacity-actualHead);
                    for(int i=actualHead;i<actualCapacity;++i) {
                        indexVerifier.accept(i);
                    }
                    for(int i=0;i<=actualTail;++i) {
                        indexVerifier.accept(i);
                    }
                }else {
                    Assertions.assertEquals(expectedSize,actualTail+1-actualHead);
                    for(int i=actualHead;i<=actualTail;++i) {
                        indexVerifier.accept(i);
                    }
                }
            }
        }

        private static interface CloneVerifier{
            void verifyIndices(int thisIndex,int cloneIndex);
        }
        
        @Override
        public void verifyClone(Object clone){
            final var checked=checkedType.checked;
            int cloneHead;
            int cloneTail;
            int cloneCapacity;
            int thisHead;
            int thisTail;
            Object cloneArr;
            CloneVerifier cloneVerifier;
            switch(dataType) {
            case BOOLEAN:{
                var cloneCast=(BooleanArrDeq)clone;
                var thisCast=(BooleanArrDeq)seq;
                thisHead=thisCast.head;
                thisTail=thisCast.tail;
                cloneHead=cloneCast.head;
                cloneTail=cloneCast.tail;
                final var cloneCastArr=cloneCast.arr;
                cloneCapacity=cloneCastArr==null?0:cloneCastArr.length;
                cloneArr=cloneCastArr;
                Assertions.assertEquals(checked,cloneCast instanceof BooleanArrDeq.Checked);
                if(checked) {
                    Assertions.assertEquals(0,((BooleanArrDeq.Checked)cloneCast).modCount);
                }
                final var thisArr=(boolean[])expectedArr;
                cloneVerifier=(thisIndex,cloneIndex)->Assertions.assertEquals(thisArr[thisIndex],cloneCastArr[cloneIndex]);
                break;
            }
            case BYTE:{
                var cloneCast=(ByteArrDeq)clone;
                var thisCast=(ByteArrDeq)seq;
                thisHead=thisCast.head;
                thisTail=thisCast.tail;
                cloneHead=cloneCast.head;
                cloneTail=cloneCast.tail;
                final var cloneCastArr=cloneCast.arr;
                cloneCapacity=cloneCastArr==null?0:cloneCastArr.length;
                cloneArr=cloneCastArr;
                Assertions.assertEquals(checked,cloneCast instanceof ByteArrDeq.Checked);
                if(checked) {
                    Assertions.assertEquals(0,((ByteArrDeq.Checked)cloneCast).modCount);
                }
                final var thisArr=(byte[])expectedArr;
                cloneVerifier=(thisIndex,cloneIndex)->Assertions.assertEquals(thisArr[thisIndex],cloneCastArr[cloneIndex]);
                break;
            }
            case CHAR:{
                var cloneCast=(CharArrDeq)clone;
                var thisCast=(CharArrDeq)seq;
                thisHead=thisCast.head;
                thisTail=thisCast.tail;
                cloneHead=cloneCast.head;
                cloneTail=cloneCast.tail;
                final var cloneCastArr=cloneCast.arr;
                cloneCapacity=cloneCastArr==null?0:cloneCastArr.length;
                cloneArr=cloneCastArr;
                Assertions.assertEquals(checked,cloneCast instanceof CharArrDeq.Checked);
                if(checked) {
                    Assertions.assertEquals(0,((CharArrDeq.Checked)cloneCast).modCount);
                }
                final var thisArr=(char[])expectedArr;
                cloneVerifier=(thisIndex,cloneIndex)->Assertions.assertEquals(thisArr[thisIndex],cloneCastArr[cloneIndex]);
                break;
            }
            case SHORT:{
                var cloneCast=(ShortArrDeq)clone;
                var thisCast=(ShortArrDeq)seq;
                thisHead=thisCast.head;
                thisTail=thisCast.tail;
                cloneHead=cloneCast.head;
                cloneTail=cloneCast.tail;
                final var cloneCastArr=cloneCast.arr;
                cloneCapacity=cloneCastArr==null?0:cloneCastArr.length;
                cloneArr=cloneCastArr;
                Assertions.assertEquals(checked,cloneCast instanceof ShortArrDeq.Checked);
                if(checked) {
                    Assertions.assertEquals(0,((ShortArrDeq.Checked)cloneCast).modCount);
                }
                final var thisArr=(short[])expectedArr;
                cloneVerifier=(thisIndex,cloneIndex)->Assertions.assertEquals(thisArr[thisIndex],cloneCastArr[cloneIndex]);
                break;
            }
            case INT:{
                var cloneCast=(IntArrDeq)clone;
                var thisCast=(IntArrDeq)seq;
                thisHead=thisCast.head;
                thisTail=thisCast.tail;
                cloneHead=cloneCast.head;
                cloneTail=cloneCast.tail;
                final var cloneCastArr=cloneCast.arr;
                cloneCapacity=cloneCastArr==null?0:cloneCastArr.length;
                cloneArr=cloneCastArr;
                Assertions.assertEquals(checked,cloneCast instanceof IntArrDeq.Checked);
                if(checked) {
                    Assertions.assertEquals(0,((IntArrDeq.Checked)cloneCast).modCount);
                }
                final var thisArr=(int[])expectedArr;
                cloneVerifier=(thisIndex,cloneIndex)->Assertions.assertEquals(thisArr[thisIndex],cloneCastArr[cloneIndex]);
                break;
            }
            case LONG:{
                var cloneCast=(LongArrDeq)clone;
                var thisCast=(LongArrDeq)seq;
                thisHead=thisCast.head;
                thisTail=thisCast.tail;
                cloneHead=cloneCast.head;
                cloneTail=cloneCast.tail;
                final var cloneCastArr=cloneCast.arr;
                cloneCapacity=cloneCastArr==null?0:cloneCastArr.length;
                cloneArr=cloneCastArr;
                Assertions.assertEquals(checked,cloneCast instanceof LongArrDeq.Checked);
                if(checked) {
                    Assertions.assertEquals(0,((LongArrDeq.Checked)cloneCast).modCount);
                }
                final var thisArr=(long[])expectedArr;
                cloneVerifier=(thisIndex,cloneIndex)->Assertions.assertEquals(thisArr[thisIndex],cloneCastArr[cloneIndex]);
                break;
            }
            case FLOAT:{
                var cloneCast=(FloatArrDeq)clone;
                var thisCast=(FloatArrDeq)seq;
                thisHead=thisCast.head;
                thisTail=thisCast.tail;
                cloneHead=cloneCast.head;
                cloneTail=cloneCast.tail;
                final var cloneCastArr=cloneCast.arr;
                cloneCapacity=cloneCastArr==null?0:cloneCastArr.length;
                cloneArr=cloneCastArr;
                Assertions.assertEquals(checked,cloneCast instanceof FloatArrDeq.Checked);
                if(checked) {
                    Assertions.assertEquals(0,((FloatArrDeq.Checked)cloneCast).modCount);
                }
                final var thisArr=(float[])expectedArr;
                cloneVerifier=(thisIndex,cloneIndex)->Assertions.assertEquals(thisArr[thisIndex],cloneCastArr[cloneIndex]);
                break;
            }
            case DOUBLE:{
                var cloneCast=(DoubleArrDeq)clone;
                var thisCast=(DoubleArrDeq)seq;
                thisHead=thisCast.head;
                thisTail=thisCast.tail;
                cloneHead=cloneCast.head;
                cloneTail=cloneCast.tail;
                final var cloneCastArr=cloneCast.arr;
                cloneCapacity=cloneCastArr==null?0:cloneCastArr.length;
                cloneArr=cloneCastArr;
                Assertions.assertEquals(checked,cloneCast instanceof DoubleArrDeq.Checked);
                if(checked) {
                    Assertions.assertEquals(0,((DoubleArrDeq.Checked)cloneCast).modCount);
                }
                final var thisArr=(double[])expectedArr;
                cloneVerifier=(thisIndex,cloneIndex)->Assertions.assertEquals(thisArr[thisIndex],cloneCastArr[cloneIndex]);
                break;
            }
            case REF:{
                var cloneCast=(RefArrDeq<?>)clone;
                var thisCast=(RefArrDeq<?>)seq;
                thisHead=thisCast.head;
                thisTail=thisCast.tail;
                cloneHead=cloneCast.head;
                cloneTail=cloneCast.tail;
                final var cloneCastArr=cloneCast.arr;
                cloneCapacity=cloneCastArr==null?0:cloneCastArr.length;
                cloneArr=cloneCastArr;
                Assertions.assertEquals(checked,cloneCast instanceof RefArrDeq.Checked);
                if(checked) {
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
            if(expectedSize==0) {
                Assertions.assertEquals(-1,cloneTail);
                Assertions.assertEquals(0,cloneHead);
                Assertions.assertSame(dataType.defaultArr,cloneArr);
            }else {
                Assertions.assertNotSame(cloneArr,expectedArr);
                Assertions.assertEquals(expectedSize,cloneTail+1-cloneHead);
                Assertions.assertEquals(expectedSize,cloneCapacity);
                int cloneIndex=0;
                if(thisTail<thisHead) {
                    int bound=this.expectedCapacity;
                    for(int thisIndex=thisHead;thisIndex<bound;++thisIndex,++cloneIndex) {
                        cloneVerifier.verifyIndices(thisIndex,cloneIndex);
                    }
                    for(int thisIndex=0;thisIndex<=thisTail;++thisIndex,++cloneIndex) {
                        cloneVerifier.verifyIndices(thisIndex,cloneIndex);
                    }
                }else {
                    for(int thisIndex=thisHead;thisIndex<=thisTail;++thisIndex,++cloneIndex) {
                        cloneVerifier.verifyIndices(thisIndex,cloneIndex);
                    }
                }
            }
        }

        private void verifyNonBooleanRemoveIf(boolean result,MonitoredRemoveIfPredicate filter) {
            if(result) {
                for(var removedVal:filter.removedVals) {
                    Assertions.assertFalse(seq.contains(removedVal));
                }
                
            }else {
                Assertions.assertEquals(expectedSize,filter.numRetained);
                
            }
            var itr=seq.iterator();
            while(itr.hasNext()) {
                Assertions.assertTrue(filter.retainedVals.contains(itr.next()));
            }
            updateCollectionState();
        }
        private void verifyBooleanFragmentedRemoveIf(boolean result,MonitoredRemoveIfPredicate filter){
            int head=this.expectedHead;
            int tail=this.expectedTail;
            int expectedCapacity=this.expectedCapacity;
            final var expectedArr=(boolean[])this.expectedArr;
            int trueCount=0;
            int size;
            for(int i=tail;;--i) {
                if(expectedArr[i]) {
                    ++trueCount;
                }
                if(i==0) {
                    for(i=(size=expectedCapacity)-1;;--i) {
                        if(expectedArr[i]) {
                            ++trueCount;
                        }
                        if(i==head) {
                            break;
                        }
                    }
                    size+=tail+1-head;
                    break;
                }
            }
            verifyBooleanRemoveIfHelper(result,filter,expectedArr,trueCount,size);
        }
        private void verifyBooleanRemoveIfHelper(boolean result,MonitoredRemoveIfPredicate filter,
                final boolean[] expectedArr,int trueCount,int size){
            if(trueCount==size) {
                Assertions.assertFalse(filter.removedVals.contains(Boolean.FALSE));
                Assertions.assertFalse(filter.retainedVals.contains(Boolean.FALSE));
                Assertions.assertEquals(1,filter.numCalls);
                if(filter.removedVals.contains(Boolean.TRUE)) {
                    Assertions.assertTrue(result);
                    this.expectedTail=-1;
                    this.expectedSize=0;
                    ++expectedModCount;
                }else {
                    Assertions.assertFalse(result);
                }
            }else if(trueCount==0) {
                Assertions.assertFalse(filter.removedVals.contains(Boolean.TRUE));
                Assertions.assertFalse(filter.retainedVals.contains(Boolean.TRUE));
                Assertions.assertEquals(1,filter.numCalls);
                if(filter.removedVals.contains(Boolean.FALSE)) {
                    Assertions.assertTrue(result);
                    this.expectedTail=-1;
                    this.expectedSize=0;
                    ++expectedModCount;
                }else {
                    Assertions.assertFalse(result);
                }
            }else {
                Assertions.assertEquals(2,filter.numCalls);
                if(filter.removedVals.contains(Boolean.TRUE)) {
                    Assertions.assertTrue(result);
                    ++expectedModCount;
                    if(filter.removedVals.contains(Boolean.FALSE)) {
                        this.expectedTail=-1;
                        this.expectedSize=0;
                        Assertions.assertTrue(filter.retainedVals.isEmpty());
                        Assertions.assertEquals(0,filter.retainedVals);
                        Assertions.assertEquals(2,filter.numRemoved);
                    }else {
                        for(int i=size-=trueCount+1;;--i){
                            expectedArr[i]=false;
                            if(i==0) {
                                break;
                            }
                        }
                        this.expectedHead=0;
                        this.expectedTail=size;
                        this.expectedSize=size+1;
                        Assertions.assertTrue(filter.retainedVals.contains(Boolean.FALSE));
                        Assertions.assertFalse(filter.retainedVals.contains(Boolean.TRUE));
                        Assertions.assertEquals(1,filter.numRetained);
                        Assertions.assertEquals(1,filter.numRemoved);
                    }
                }else {
                    if(filter.removedVals.contains(Boolean.FALSE)) {
                        Assertions.assertTrue(result);
                        ++expectedModCount;
                        for(int i=--trueCount;;--i) {
                            expectedArr[i]=true;
                            if(i==0) {
                                break;
                            }
                        }
                        this.expectedHead=0;
                        this.expectedTail=trueCount;
                        this.expectedSize=trueCount+1;
                        Assertions.assertTrue(filter.retainedVals.contains(Boolean.TRUE));
                        Assertions.assertFalse(filter.retainedVals.contains(Boolean.FALSE));
                        Assertions.assertEquals(1,filter.numRetained);
                        Assertions.assertEquals(1,filter.numRemoved);
                    }else {
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
        private void verifyBooleanNonfragmentedRemoveIf(boolean result,MonitoredRemoveIfPredicate filter) {
            int head=this.expectedHead;
            int tail=this.expectedTail;
            final var expectedArr=(boolean[])this.expectedArr;
            int trueCount=0;
            for(int i=head;;++i) {
                if(expectedArr[i]) {
                    ++trueCount;
                }
                if(i==tail) {
                    break;
                }
            }
            verifyBooleanRemoveIfHelper(result,filter,expectedArr,trueCount,tail-head+1);
        }
        
        @Override
        public void verifyRemoveIf(boolean result,MonitoredRemoveIfPredicate filter){
            Assertions.assertNotEquals(result,filter.removedVals.isEmpty());
            Assertions.assertNotEquals(result,filter.numRemoved == 0);
            if(expectedTail==-1) {
                Assertions.assertFalse(result);
                Assertions.assertTrue(filter.retainedVals.isEmpty());
                Assertions.assertEquals(0,filter.numRetained);
                Assertions.assertEquals(0,filter.numCalls);
            }else {
                if(dataType==DataType.BOOLEAN) {
                    if(expectedTail<expectedHead) {
                        verifyBooleanFragmentedRemoveIf(result,filter);
                    }else {
                        verifyBooleanNonfragmentedRemoveIf(result,filter);
                    }
                }else {
                    verifyNonBooleanRemoveIf(result,filter);
                }
                
            }
        }

        @Override
        public void verifyArrayIsCopy(Object arr,boolean emptyArrayMayBeSame){
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

        @Override
        public void writeObjectImpl(MonitoredObjectOutputStream oos) throws IOException{
            ((Externalizable)seq).writeExternal(oos);
        }

        @Override
        public void updateRemoveLastOccurrenceState(Object inputVal,DataType inputType){
            int head=this.expectedHead;
            int tail=this.expectedTail;
            IntPredicate indexSearcher=dataType.getArrayIndexSearcher(expectedArr,inputVal,inputType);
            ++expectedModCount;
            --expectedSize;
            if(tail<head) {
                int index,bound;
                for(index=tail,bound=expectedCapacity-1;index>=0;--index) {
                    if(indexSearcher.test(index)) {
                        int headDist,tailDist;
                        if((headDist=bound-head)+index+1<(tailDist=tail-index)) {
                            System.arraycopy(expectedArr,0,expectedArr,1,index);
                            System.arraycopy(expectedArr,bound,expectedArr,0,1);
                            System.arraycopy(expectedArr,head,expectedArr,tail=head+1,headDist);
                            if(dataType==DataType.REF) {
                                ((Object[])expectedArr)[head]=null;
                            }
                            this.expectedHead=tail>bound?0:tail;
                        }else {
                            System.arraycopy(expectedArr,index+1,expectedArr,index,tailDist);
                            if(dataType==DataType.REF) {
                                ((Object[])expectedArr)[tail]=null;
                            }
                            this.expectedTail=--tail==-1?bound:tail;
                        }
                        return;
                    }
                }
                for(index=bound;;--index) {
                    if(indexSearcher.test(index)) {
                        int headDist,tailDist;
                        if((headDist=index-head)<=(tailDist=bound-index)+tail+1) {
                            System.arraycopy(expectedArr,head,expectedArr,tail=head+1,headDist);
                            if(dataType==DataType.REF) {
                                ((Object[])expectedArr)[head]=null;
                            }
                            this.expectedHead=tail>bound?0:tail;
                        }else {
                            System.arraycopy(expectedArr,index+1,expectedArr,index,tailDist);
                            System.arraycopy(expectedArr,0,expectedArr,bound,1);
                            System.arraycopy(expectedArr,1,expectedArr,0,tail);
                            if(dataType==DataType.REF) {
                                ((Object[])expectedArr)[tail]=null;
                            }
                            this.expectedTail=--tail==-1?bound:tail;
                        }
                        return;
                    }
                }
            }else {
                int index;
                for(index=tail;;--index) {
                    if(indexSearcher.test(index)) {
                        int headDist,tailDist;
                        if((tailDist=tail-index)<=(headDist=index-head)) {
                            if(headDist==0) {
                                this.expectedTail=-1;
                            }else {
                                System.arraycopy(expectedArr,index+1,expectedArr,index,tailDist);
                                this.expectedTail=tail-1;
                            }
                            if(dataType==DataType.REF) {
                                ((Object[])expectedArr)[tail]=null;
                            }
                        }else {
                            System.arraycopy(expectedArr,head,expectedArr,tail=head+1,headDist);
                            if(dataType==DataType.REF) {
                                ((Object[])expectedArr)[head]=null;
                            }
                            this.expectedHead=tail;
                        }
                        return;
                    }
                }
             }
        }

        @Override
        public void updateAddFirstState(Object inputVal,DataType inputType){
            if(expectedSize==0) {
                if(expectedCapacity==0) {
                    if(expectedArr==null) {
                        this.expectedArr=dataType.newArray(this.expectedCapacity=1);
                    }else {
                        this.expectedArr=dataType.newArray(this.expectedCapacity=OmniArray.DEFAULT_ARR_SEQ_CAP);
                    }
                }
                this.expectedHead=expectedCapacity-1;
                this.expectedTail=expectedCapacity-1;
                dataType.convertValAndStoreInArray(expectedCapacity-1,this.expectedArr,inputVal,inputType);
            }else {
                int head;
                int tail;
                if((tail=this.expectedTail)==(head=this.expectedHead-1)) {
                    Object newArr;
                    int newCap,size;
                    this.expectedTail=(newCap=OmniArray.growBy50Pct(head+(size=expectedCapacity)))-1;
                    this.expectedCapacity=newCap;
                    System.arraycopy(expectedArr,0,newArr=dataType.newArray(newCap),newCap-=++tail,tail);
                    System.arraycopy(expectedArr,head+1,newArr,head=newCap-(size-=tail),size);
                    this.expectedArr=newArr;
                    --head;
                }else if(head==-1 && tail==(head=expectedCapacity-1)){
                    int newCap;
                    this.expectedTail=(newCap=OmniArray.growBy50Pct(++tail))-1;
                    this.expectedCapacity=newCap;
                    System.arraycopy(expectedArr,0,expectedArr=dataType.newArray(newCap),head=newCap-tail,tail);
                    --head;
                }
                dataType.convertValAndStoreInArray(head,expectedArr,inputVal,inputType);
                this.expectedHead=head;
            }
            ++expectedModCount;
            ++expectedSize;
        }
        @Override
        public boolean verifyAdd(Object inputVal,DataType inputType,FunctionCallType functionCallType){
            Assertions.assertTrue(inputType.callAdd(inputVal,seq,functionCallType));
            updateAddState(inputVal,inputType);
            verifyCollectionState();
            return true;
        }
        @Override
        public boolean verifyRemoveVal(QueryVal queryVal,DataType inputType,QueryCastType queryCastType,
                QueryValModification modification){
            Object inputVal=queryVal.getInputVal(inputType,modification);
            boolean result=queryCastType.callremoveVal(seq,inputVal,inputType);
            if(result) {
                updateRemoveValState(inputVal,inputType);
            }
            verifyCollectionState();
            return result;
        }
        @Override
        public void verifyHashCode(int hashCode){
            int expectedHash=1;
            switch(dataType) {
            case BOOLEAN:{
                var itr=(OmniIterator.OfBoolean)seq.iterator();
                while(itr.hasNext()) {
                    expectedHash=expectedHash*31+Boolean.hashCode(itr.nextBoolean());
                }
                break;
            }
            case BYTE:{
                var itr=(OmniIterator.OfByte)seq.iterator();
                while(itr.hasNext()) {
                    expectedHash=expectedHash*31+Byte.hashCode(itr.nextByte());
                }
                break;
            }
            case CHAR:{
                var itr=(OmniIterator.OfChar)seq.iterator();
                while(itr.hasNext()) {
                    expectedHash=expectedHash*31+Character.hashCode(itr.nextChar());
                }
                break;
            }
            case DOUBLE:{
                var itr=(OmniIterator.OfDouble)seq.iterator();
                while(itr.hasNext()) {
                    expectedHash=expectedHash*31+Double.hashCode(itr.nextDouble());
                }
                break;
            }
            case FLOAT:{
                var itr=(OmniIterator.OfFloat)seq.iterator();
                while(itr.hasNext()) {
                    expectedHash=expectedHash*31+Float.hashCode(itr.nextFloat());
                }
                break;
            }
            case INT:{
                var itr=(OmniIterator.OfInt)seq.iterator();
                while(itr.hasNext()) {
                    expectedHash=expectedHash*31+Integer.hashCode(itr.nextInt());
                }
                break;
            }
            case LONG:{
                var itr=(OmniIterator.OfLong)seq.iterator();
                while(itr.hasNext()) {
                    expectedHash=expectedHash * 31 + Long.hashCode(itr.nextLong());
                }
                break;
            }
            case REF:{
                var itr=(OmniIterator.OfRef<?>)seq.iterator();
                while(itr.hasNext()) {
                    expectedHash=expectedHash*31+Objects.hashCode(itr.next());
                }
                break;
            }
            case SHORT:{
                var itr=(OmniIterator.OfShort)seq.iterator();
                while(itr.hasNext()) {
                    expectedHash=expectedHash*31+Short.hashCode(itr.nextShort());
                }
                break;
            }
            default:
                throw dataType.invalid();
            }
            Assertions.assertEquals(expectedHash,hashCode);
        }
        @SuppressWarnings("unchecked")
        @Override
        public boolean add(int val){
            Object inputVal;
            switch(dataType) {
            case BOOLEAN:{
                boolean v;
                ((BooleanArrDeq)seq).addLast(v=(val&1)!=0);
                inputVal=v;
                break;
            }
            case BYTE:{
                byte v;
                ((ByteArrDeq)seq).addLast(v=(byte)val);
                inputVal=v;
                break;
            }
            case CHAR:{
                char v;
                ((CharArrDeq)seq).addLast(v=(char)val);
                inputVal=v;
                break;
            }
            case SHORT:{
                short v;
                ((ShortArrDeq)seq).addLast(v=(short)val);
                inputVal=v;
                break;
            }
            case INT:{
                int v;
                ((IntArrDeq)seq).addLast(v=val);
                inputVal=v;
                break;
            }
            case LONG:{
                long v;
                ((LongArrDeq)seq).addLast(v=val);
                inputVal=v;
                break;
            }
            case FLOAT:{
                float v;
                ((FloatArrDeq)seq).addLast(v=val);
                inputVal=v;
                break;
            }
            case DOUBLE:{
                double v;
                ((DoubleArrDeq)seq).addLast(v=val);
                inputVal=v;
                break;
            }
            case REF:{
                ((RefArrDeq<Object>)seq).addLast(inputVal=val);
                break;
            }
            default:
                throw dataType.invalid();
            }
            updateAddState(inputVal,dataType);
            return true;
        }
        
        private void rotate(int numToRotate) {
                this.expectedModCount+=2*numToRotate;
                switch(dataType) {
                case BOOLEAN:{
                    var castSeq=(BooleanArrDeq)seq;
                    for(int i=0;i<numToRotate;++i) {
                        var tmp=castSeq.booleanElement();
                        castSeq.addLast(tmp);
                        castSeq.popBoolean();
                    }
                    this.expectedHead=castSeq.head;
                    this.expectedTail=castSeq.tail;
                    System.arraycopy(castSeq.arr,0,expectedArr,0,expectedCapacity);
                    break;
                }
                case BYTE:{
                    var castSeq=(ByteArrDeq)seq;
                    for(int i=0;i<numToRotate;++i) {
                        var tmp=castSeq.byteElement();
                        castSeq.addLast(tmp);
                        castSeq.popByte();
                    }
                    this.expectedHead=castSeq.head;
                    this.expectedTail=castSeq.tail;
                    System.arraycopy(castSeq.arr,0,expectedArr,0,expectedCapacity);
                    break;
                }
                case CHAR:{
                    var castSeq=(CharArrDeq)seq;
                    for(int i=0;i<numToRotate;++i) {
                        var tmp=castSeq.charElement();
                        castSeq.addLast(tmp);
                        castSeq.popChar();
                    }
                    this.expectedHead=castSeq.head;
                    this.expectedTail=castSeq.tail;
                    System.arraycopy(castSeq.arr,0,expectedArr,0,expectedCapacity);
                    break;
                }
                case SHORT:{
                    var castSeq=(ShortArrDeq)seq;
                    for(int i=0;i<numToRotate;++i) {
                        var tmp=castSeq.shortElement();
                        castSeq.addLast(tmp);
                        castSeq.popShort();
                    }
                    this.expectedHead=castSeq.head;
                    this.expectedTail=castSeq.tail;
                    System.arraycopy(castSeq.arr,0,expectedArr,0,expectedCapacity);
                    break;
                }
                case INT:{
                    var castSeq=(IntArrDeq)seq;
                    for(int i=0;i<numToRotate;++i) {
                        var tmp=castSeq.intElement();
                        castSeq.addLast(tmp);
                        castSeq.popInt();
                    }
                    this.expectedHead=castSeq.head;
                    this.expectedTail=castSeq.tail;
                    System.arraycopy(castSeq.arr,0,expectedArr,0,expectedCapacity);
                    break;
                }
                case LONG:{
                    var castSeq=(LongArrDeq)seq;
                    for(int i=0;i<numToRotate;++i) {
                        var tmp=castSeq.longElement();
                        castSeq.addLast(tmp);
                        castSeq.popLong();
                    }
                    this.expectedHead=castSeq.head;
                    this.expectedTail=castSeq.tail;
                    System.arraycopy(castSeq.arr,0,expectedArr,0,expectedCapacity);
                    break;
                }
                case FLOAT:{
                    var castSeq=(FloatArrDeq)seq;
                    for(int i=0;i<numToRotate;++i) {
                        var tmp=castSeq.floatElement();
                        castSeq.addLast(tmp);
                        castSeq.popFloat();
                    }
                    this.expectedHead=castSeq.head;
                    this.expectedTail=castSeq.tail;
                    System.arraycopy(castSeq.arr,0,expectedArr,0,expectedCapacity);
                    break;
                }
                case DOUBLE:{
                    var castSeq=(DoubleArrDeq)seq;
                    for(int i=0;i<numToRotate;++i) {
                        var tmp=castSeq.doubleElement();
                        castSeq.addLast(tmp);
                        castSeq.popDouble();
                    }
                    this.expectedHead=castSeq.head;
                    this.expectedTail=castSeq.tail;
                    System.arraycopy(castSeq.arr,0,expectedArr,0,expectedCapacity);
                    break;
                }
                case REF:{
                    @SuppressWarnings("unchecked")
                    var castSeq=(RefArrDeq<Object>)seq;
                    for(int i=0;i<numToRotate;++i) {
                        var tmp=castSeq.element();
                        castSeq.addLast(tmp);
                        castSeq.pop();
                    }
                    this.expectedHead=castSeq.head;
                    this.expectedTail=castSeq.tail;
                    System.arraycopy(castSeq.arr,0,expectedArr,0,expectedCapacity);
                    break;
                }
                default:
                    throw dataType.invalid();
                }
            }
           
        
        
        
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
    public void testadd_val(){
        AddTest test=(monitor,inputVal,inputType,functionCallType)->Assertions.assertTrue(monitor.verifyAdd(inputVal,inputType,functionCallType));
        test.runAllTests("ArrDeqTest.testadd_val");
    }
    @Test
    public void testaddFirst_val(){
        AddTest test=ArrDeqMonitor::verifyAddFirst;
        test.runAllTests("ArrDeqTest.testaddFirst_val");
    }
    @Test
    public void testaddLast_val(){
        AddTest test=ArrDeqMonitor::verifyAddLast;
        test.runAllTests("ArrDeqTest.testaddLast_val");
    }
    @Test
    public void testclear_void(){
        BasicTest test=ArrDeqMonitor::verifyClear;
        test.runAllTests("ArrDeqTest.testclear_void");
    }
    @Test
    public void testclone_void(){
        BasicTest test=ArrDeqMonitor::verifyClone;
        test.runAllTests("ArrDeqTest.testclone_void");
    }
    @Test
    public void testConstructor_void(){
        for(var checkedType:CheckedType.values()) {
            for(var collectionType:DataType.values()) {
                TestExecutorService.submitTest(()->{
                    new ArrDeqMonitor(checkedType,collectionType).verifyCollectionState();
                });
            }
        }
        TestExecutorService.completeAllTests("ArrDeqTest.testConstructor_void");
    }
    @Test
    public void testConstructor_int(){
        for(var checkedType:CheckedType.values()) {
            for(var collectionType:DataType.values()) {
                for(int tmpInitCap=0;tmpInitCap<=15;tmpInitCap+=5) {
                    final int initCap=tmpInitCap;
                    TestExecutorService.submitTest(()->{
                        new ArrDeqMonitor(checkedType,collectionType,initCap).verifyCollectionState();
                    });
                }
                
            }
        }
        TestExecutorService.completeAllTests("ArrDeqTest.testConstructor_int");
    }
    @Test
    public void testcontains_val(){
        //TODO
        throw new UnsupportedOperationException();
    }
    @Test
    public void testdescendingIterator_void(){
        BasicTest test=monitor->{
            monitor.getMonitoredDescendingIterator().verifyIteratorState();
            monitor.verifyCollectionState();
          };
          test.runAllTests("ArrDeqTest.testdescendingIterator_void");
      }
    
    
    
    @Test
    public void testelement_void(){
        GetTest test=(monitor,outputType)->{
            monitor.verifyElement(outputType);
            if(!monitor.isEmpty()) {
                monitor.removeFirst();
            }
        };
        test.runAllTests("ArrDeqTest.testelement_void",true);
    }
    @Test
    public void testforEach_Consumer(){
        final MonitoredFunctionTest test=(monitor,functionGen,functionCallType,
                randSeed)->{
            if(functionGen.expectedException == null || monitor.isEmpty()){
                monitor.verifyForEach(functionGen,functionCallType,randSeed);
            }else{
                Assertions.assertThrows(functionGen.expectedException,
                        ()->monitor.verifyForEach(functionGen,functionCallType,randSeed));
            }
            
        };
        test.runAllTests("ArrDeqTest.testforEach_Consumer",100);
    }
    @Test
    public void testgetFirst_void(){
        GetTest test=(monitor,outputType)->{
            monitor.verifyGetFirst(outputType);
            if(!monitor.isEmpty()) {
                monitor.removeFirst();
            }
        };
        test.runAllTests("ArrDeqTest.testgetFirst_void",true);
    }
    @Test
    public void testgetLast_void(){
        GetTest test=(monitor,outputType)->{
            monitor.verifyGetLast(outputType);
            if(!monitor.isEmpty()) {
                monitor.removeLast();
            }
        };
        test.runAllTests("ArrDeqTest.testgetLast_void",true);
    }
    @Test
    public void testhashCode_void(){
        //TODO
        throw new UnsupportedOperationException();
    }
    @Test
    public void testisEmpty_void(){
        BasicTest test=ArrDeqMonitor::verifyIsEmpty;
        test.runAllTests("ArrDeqTest.testisEmpty_void");
    }
    @Test
    public void testiterator_void(){
        BasicTest test=monitor->{
          monitor.getMonitoredIterator().verifyIteratorState();
          monitor.verifyCollectionState();
        };
        test.runAllTests("ArrDeqTest.testiterator_void");
    }
    @Test
    public void testItrclone_void(){
        //TODO
        throw new UnsupportedOperationException();
    }
    @Test
    public void testItrforEachRemaining_Consumer(){
        //TODO
        throw new UnsupportedOperationException();
    }
    @Test
    public void testItrhasNext_void(){
        //TODO
        throw new UnsupportedOperationException();
    }
    @Test
    public void testItrnext_void(){
        //TODO
        throw new UnsupportedOperationException();
    }
    @Test
    public void testItrremove_void(){
        //TODO
        throw new UnsupportedOperationException();
    }
    @Test
    public void testMASSIVEtoString(){
        //TODO
        throw new UnsupportedOperationException();
    }
    @Test
    public void testoffer_val(){
        AddTest test=(monitor,inputVal,inputType,functionCallType)->Assertions.assertTrue(monitor.verifyOffer(inputVal,inputType,functionCallType));
        test.runAllTests("ArrDeqTest.testoffer_val");
    }
    @Test
    public void testofferFirst_val(){
        AddTest test=(monitor,inputVal,inputType,functionCallType)->Assertions.assertTrue(monitor.verifyOfferFirst(inputVal,inputType,functionCallType));
        test.runAllTests("ArrDeqTest.testofferFirst_val");
    }
    @Test
    public void testofferLast_val(){
        AddTest test=(monitor,inputVal,inputType,functionCallType)->Assertions.assertTrue(monitor.verifyOfferLast(inputVal,inputType,functionCallType));
        test.runAllTests("ArrDeqTest.testofferLast_val");
    }
    @Test
    public void testpeek_void(){
        GetTest test=(monitor,outputType)->{
            monitor.verifyPeek(outputType);
            if(!monitor.isEmpty()) {
                monitor.removeFirst();
            }
        };
        test.runAllTests("ArrDeqTest.testpeek_void",false);
    }
    @Test
    public void testpeekFirst_void(){
        GetTest test=(monitor,outputType)->{
            monitor.verifyPeekFirst(outputType);
            if(!monitor.isEmpty()) {
                monitor.removeFirst();
            }
        };
        test.runAllTests("ArrDeqTest.testpeekFirst_void",false);
    }
    @Test
    public void testpeekLast_void(){
        GetTest test=(monitor,outputType)->{
            monitor.verifyPeekLast(outputType);
            if(!monitor.isEmpty()) {
                monitor.removeLast();
            }
        };
        test.runAllTests("ArrDeqTest.testpeekLast_void",false);
    }
    @Test
    public void testpoll_void(){
        GetTest test=ArrDeqMonitor::verifyPoll;
        test.runAllTests("ArrDeqTest.testpoll_void",false);
    }
    @Test
    public void testpollFirst_void(){
        GetTest test=ArrDeqMonitor::verifyPollFirst;
        test.runAllTests("ArrDeqTest.testpollFirst_void",false);
    }
    @Test
    public void testpollLast_void(){
        GetTest test=ArrDeqMonitor::verifyPollLast;
        test.runAllTests("ArrDeqTest.testpollLast_void",false);
    }
    @Test
    public void testpop_void(){
        GetTest test=ArrDeqMonitor::verifyPop;
        test.runAllTests("ArrDeqTest.testpop_void",true);
    }
    @Test
    public void testpush_val(){
        AddTest test=ArrDeqMonitor::verifyPush;
        test.runAllTests("ArrDeqTest.testpush_val");
    }
    @Test
    public void testReadAndWrite(){
        final MonitoredFunctionTest test=(monitor,functionGen,functionCallType,
                randSeed)->{
            if(functionGen.expectedException == null){
                Assertions.assertDoesNotThrow(()->monitor.verifyReadAndWrite(functionGen));
            }else{
                Assertions.assertThrows(functionGen.expectedException,()->monitor.verifyReadAndWrite(functionGen));
            }
        };
        test.runAllTests("ArrDeqTest.testReadAndWrite",0);
    }
    @Test
    public void testremove_void(){
        GetTest test=ArrDeqMonitor::verifyRemove;
        test.runAllTests("ArrDeqTest.testremove_void",true);
    }
    @Test
    public void testremoveFirst_void(){
        GetTest test=ArrDeqMonitor::verifyRemoveFirst;
        test.runAllTests("ArrDeqTest.testremoveFirst_void",true);
    }
    @Test
    public void testremoveFirstOccurrence_val(){
        //TODO
        throw new UnsupportedOperationException();
    }
    @Test
    public void testremoveIf_Predicate(){
        //TODO
        throw new UnsupportedOperationException();
    }
    @Test
    public void testremoveLast_void(){
        GetTest test=ArrDeqMonitor::verifyRemoveLast;
        test.runAllTests("ArrDeqTest.testremoveLast_void",true);
    }
    @Test
    public void testremoveLastOccurrence_val(){
        //TODO
        throw new UnsupportedOperationException();
    }
    @Test
    public void testremoveVal_val(){
        //TODO
        throw new UnsupportedOperationException();
    }
    @Test
    public void testsearch_val(){
        //TODO
        throw new UnsupportedOperationException();
    }
    @Test
    public void testsize_void(){
        BasicTest test=ArrDeqMonitor::verifySize;
        test.runAllTests("ArrDeqTest.testsize_void");
    }
    @Test
    public void testtoArray_IntFunction(){
        final MonitoredFunctionTest test=(monitor,functionGen,functionCallType,
                randSeed)->{
            if(functionGen.expectedException == null){
                monitor.verifyToArray(functionGen);
            }else{
                Assertions.assertThrows(functionGen.expectedException,()->monitor.verifyToArray(functionGen));
            }
        };
        test.runAllTests("ArrDeqTest.testtoArray_IntFunction",0);
    }
    @Test
    public void testtoArray_ObjectArray(){
        BasicTest test=(monitor)->{
          int size=monitor.size();
          int inc=Math.max(1,size/10);
          for(int paramArrLength=0,bound=size+inc;paramArrLength<=bound;paramArrLength+=inc) {
              monitor.verifyToArray(new Object[paramArrLength]);
          }
        };
        test.runAllTests("ArrDeqTest.testtoArray_ObjectArray");
    }
    @Test
    public void testtoArray_void(){
        BasicTest test=(monitor)->{
            for(var outputType:monitor.dataType.validOutputTypes()) {
                outputType.verifyToArray(monitor);
            }
        };
        test.runAllTests("ArrDeqTst.testtoArray_void");
    }
    @Test
    public void testtoString_void(){
        //TODO
        throw new UnsupportedOperationException();
    }
    private static final int[] SIZES=new int[] {0,1,2,3,4,5,6,7,8,9,10,15,20,30,40,50,60,70,80,90,100};
    private static interface MonitoredFunctionTest{
        void runTest(ArrDeqMonitor monitor,MonitoredFunctionGen functionGen,FunctionCallType functionCallType,long randSeed);
        private void runAllTests(String testName,long maxRand) {
            for(var checkedType:CheckedType.values()) {
                for(var collectionType:DataType.values()) {
                    for(var size:SIZES) {
                        final int initValBound=collectionType == DataType.BOOLEAN && size != 0?1:0;
                        for(int tmpInitCap=0;tmpInitCap<=size;tmpInitCap+=5) {
                            final int initCap=tmpInitCap;
                            for(int tmpNumToRotate=0;tmpNumToRotate<=10;++tmpNumToRotate) {
                                final int numToRotate=tmpNumToRotate;
                                for(final var functionGen:StructType.ArrDeq.validMonitoredFunctionGens){
                                    if(functionGen.expectedException == null || checkedType.checked) {
                                        for(final var functionCallType:collectionType.validFunctionCalls){
                                            final long randSeedBound=size > 1 && functionGen.randomized
                                                    && !functionCallType.boxed?maxRand:0;
                                            for(long tmpRandSeed=0;tmpRandSeed <= randSeedBound;++tmpRandSeed){
                                                final long randSeed=tmpRandSeed;
                                                for(int tmpInitVal=0;tmpInitVal <= initValBound;++tmpInitVal){
                                                    final int initVal=tmpInitVal;
                                                    TestExecutorService.submitTest(()->{
                                                        var monitor=SequenceInitialization.Ascending.initialize(new ArrDeqMonitor(checkedType,collectionType,initCap),size,initVal);
                                                        if(size>0 && numToRotate>0) {
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
        }
    }
    private static interface BasicTest{
        void runTest(ArrDeqMonitor monitor);
        private void runAllTests(String testName) {
            for(var checkedType:CheckedType.values()) {
                for(var collectionType:DataType.values()) {
                    for(int tmpSize=0;tmpSize<=100;tmpSize+=5) {
                        final int size=tmpSize;
                        for(int tmpInitCap=0;tmpInitCap<=size;tmpInitCap+=5) {
                            final int initCap=tmpInitCap;
                            for(int tmpNumToRotate=0;tmpNumToRotate<=10;++tmpNumToRotate) {
                                final int numToRotate=tmpNumToRotate;
                                TestExecutorService.submitTest(()->{
                                    var monitor=SequenceInitialization.Ascending.initialize(new ArrDeqMonitor(checkedType,collectionType,initCap),size,0);
                                    if(size>0 && numToRotate>0) {
                                        monitor.rotate(numToRotate);
                                    }
                                    runTest(monitor);
                                });
                            }
                        }
                    }
                }
            }
            TestExecutorService.completeAllTests(testName);
        }
    }
    private static interface GetTest{
        void processNext(ArrDeqMonitor monitor,DataType outputType);
        private void runAllTests(String testName,boolean throwsOnEmpty) {
            for(var collectionType:DataType.values()) {
                for(var outputType:collectionType.validOutputTypes()) {
                    for(var checkedType:CheckedType.values()) {
                        for(int tmpInitCap=0;tmpInitCap<=15;tmpInitCap+=5) {
                            final int initCap=tmpInitCap;
                            for(int tmpNumToRotate=0;tmpNumToRotate<=10;++tmpNumToRotate) {
                                final int numToRotate=tmpNumToRotate;
                                TestExecutorService.submitTest(()->{
                                    var monitor=SequenceInitialization.Ascending.initialize(new ArrDeqMonitor(checkedType,collectionType,initCap),100,0);
                                    if(numToRotate>0) {
                                        monitor.rotate(numToRotate);
                                    }
                                    for(int i=0;i<100;++i) {
                                        processNext(monitor,outputType);
                                    }
                                    if(throwsOnEmpty) {
                                        if(checkedType.checked) {
                                            Assertions.assertThrows(NoSuchElementException.class,()->processNext(monitor,outputType));
                                        }
                                    }else {
                                        processNext(monitor,outputType);
                                    }
                                });
                            }
                        }
                    }
                }
            }
        }
    }
    private static interface AddTest{
        void callMethod(ArrDeqMonitor monitor,Object inputVal,DataType inputType,FunctionCallType functionCallType);
        private void runAllTests(String testName) {
            for(var checkedType:CheckedType.values()) {
                for(var collectionType:DataType.values()) {
                    for(var inputType:collectionType.mayBeAddedTo()) {
                        for(var functionCallType:inputType.validFunctionCalls) {
                            for(int tmpInitCap=0;tmpInitCap<=15;tmpInitCap+=5) {
                                final int initCap=tmpInitCap;
                                for(int tmpNumToRotate=0;tmpNumToRotate<=10;++tmpNumToRotate) {
                                    final int numToRotate=tmpNumToRotate;
                                    TestExecutorService.submitTest(()->{
                                        var monitor=new ArrDeqMonitor(checkedType,collectionType,initCap);
                                        if(numToRotate!=0) {
                                          monitor.add(0);
                                          monitor.rotate(numToRotate);
                                        }
                                        for(int i=0;i<100;++i) {
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
    }
}
