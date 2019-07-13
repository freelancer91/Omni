package omni.impl.seq;

import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import omni.api.OmniDeque;
import omni.api.OmniIterator;
import omni.api.OmniList;
import omni.api.OmniListIterator;
import omni.impl.CheckedType;
import omni.impl.DataType;
import omni.impl.IteratorType;
import omni.impl.MonitoredDeque;
import omni.impl.MonitoredList;
import omni.impl.MonitoredObjectOutputStream;
import omni.impl.MonitoredRemoveIfPredicate;
import omni.impl.StructType;

public class DblLnkSeqTest{
    
    
    private static class DblLnkSeqMonitor<LSTDEQ extends AbstractSeq<E>&OmniDeque<E>&OmniList<E>,E> extends AbstractSequenceMonitor<LSTDEQ> implements MonitoredDeque<LSTDEQ>,MonitoredList<LSTDEQ>{
        
        DblLnkSeqMonitor(CheckedType checkedType,DataType dataType,int capacity){
            super(checkedType,dataType,capacity);
            updateCollectionState();
        }
        DblLnkSeqMonitor(CheckedType checkedType,DataType dataType){
            super(checkedType,dataType);
            updateCollectionState();
        }
        @Override
        public MonitoredIterator<? extends OmniIterator<?>,LSTDEQ> getMonitoredIterator(IteratorType itrType){
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException();
        }
        @Override
        public MonitoredIterator<? extends OmniIterator<?>,LSTDEQ> getMonitoredIterator(){
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException();
        }
        @Override
        public MonitoredIterator<? extends OmniIterator<?>,LSTDEQ> getMonitoredIterator(int index,IteratorType itrType){
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException();
        }
        @Override
        public MonitoredListIterator<? extends OmniListIterator<?>,LSTDEQ> getMonitoredListIterator(){
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException();
        }

        @Override
        public MonitoredListIterator<? extends OmniListIterator<?>,LSTDEQ> getMonitoredListIterator(int index){
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException();
        }

        @Override
        public MonitoredList<?> getMonitoredSubList(int fromIndex,int toIndex){
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException();
        }

        @Override
        public MonitoredIterator<?,LSTDEQ> getMonitoredDescendingIterator(){
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateRemoveLastOccurrenceState(Object inputVal,DataType inputType){
            final int index=findRemoveLastOccurrenceIndex(inputVal,inputType,0,expectedSize);
            updateRemoveIndexState(index);
        }
        private int findRemoveLastOccurrenceIndex(Object inputVal,DataType inputType,int fromIndex,int toIndex) {
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
                for(int i=toIndex-1;;--i){
                    if(expectedArr[i] == inputCast){
                        return i;
                    }
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
                for(int i=toIndex-1;;--i){
                    if(expectedArr[i] == inputCast){
                        return i;
                    }
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
                for(int i=toIndex-1;;--i){
                    if(expectedArr[i] == inputCast){
                        return i;
                    }
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
                for(int i=toIndex-1;;--i){
                    if(expectedArr[i] == inputCast){
                        return i;
                    }
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
                for(int i=toIndex-1;;--i){
                    if(expectedArr[i] == inputCast){
                        return i;
                    }
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
                for(int i=toIndex-1;;--i){
                    if(expectedArr[i] == inputCast){
                        return i;
                    }
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
                    for(int i=toIndex-1;;--i){
                        if(expectedArr[i] == inputCast){
                            return i;
                        }
                    }
                }else{
                    for(int i=toIndex-1;;--i){
                        float v;
                        if((v=expectedArr[i]) != v){
                            return i;
                        }
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
                    for(int i=toIndex-1;;--i){
                        if(expectedArr[i] == inputCast){
                            return i;
                        }
                    }
                }else{
                    for(int i=toIndex-1;;--i){
                        double v;
                        if((v=expectedArr[i]) != v){
                            return i;
                        }
                    }
                }
            }
            case REF:{
                final var expectedArr=(Object[])this.expectedArr;
                if(inputVal == null){
                    for(int i=toIndex-1;;--i){
                        if(expectedArr[i] == null){
                            return i;
                        }
                    }
                }else{
                    for(int i=toIndex-1;;--i){
                        if(inputVal.equals(expectedArr[i])){
                            return i;
                        }
                    }
                }
            }
            default:
                throw dataType.invalid();
            }
        }
        @Override
        public int findRemoveValIndex(Object inputVal,DataType inputType,int fromIndex,int toIndex) {
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
                for(int i=fromIndex;;++i){
                    if(expectedArr[i] == inputCast){
                        return i;
                    }
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
                for(int i=fromIndex;;++i){
                    if(expectedArr[i] == inputCast){
                        return i;
                    }
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
                for(int i=fromIndex;;++i){
                    if(expectedArr[i] == inputCast){
                        return i;
                    }
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
                for(int i=fromIndex;;++i){
                    if(expectedArr[i] == inputCast){
                        return i;
                    }
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
                for(int i=fromIndex;;++i){
                    if(expectedArr[i] == inputCast){
                        return i;
                    }
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
                for(int i=fromIndex;;++i){
                    if(expectedArr[i] == inputCast){
                        return i;
                    }
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
                    for(int i=fromIndex;;++i){
                        if(expectedArr[i] == inputCast){
                            return i;
                        }
                    }
                }else{
                    for(int i=fromIndex;;++i){
                        float v;
                        if((v=expectedArr[i]) != v){
                            return i;
                        }
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
                    for(int i=fromIndex;;++i){
                        if(expectedArr[i] == inputCast){
                            return i;
                        }
                    }
                }else{
                    for(int i=fromIndex;;++i){
                        double v;
                        if((v=expectedArr[i]) != v){
                            return i;
                        }
                    }
                }
            }
            case REF:{
                final var expectedArr=(Object[])this.expectedArr;
                if(inputVal == null){
                    for(int i=fromIndex;;++i){
                        if(expectedArr[i] == null){
                            return i;
                        }
                    }
                }else{
                    for(int i=fromIndex;;++i){
                        if(inputVal.equals(expectedArr[i])){
                            return i;
                        }
                    }
                }
            }
            default:
                throw dataType.invalid();
            }
        }
        @Override
        public StructType getStructType(){
            return StructType.DblLnkList;
        }
        @Override
        public void modCollection(){
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
        @Override
        public void copyListContents() {
            int expectedSize=seq.size;
            int oldExpectedSize=this.expectedSize;
           
            switch(dataType) {
            case BOOLEAN:{
                var cast=(BooleanDblLnkSeq)seq;
                var expectedArr=(boolean[])this.expectedArr;
               
                var currNode=cast.tail;
                for(int i=expectedSize;;) {
                    if(currNode==null) {
                        break;
                    }
                    expectedArr[--i]=currNode.val;
                    currNode=currNode.prev;
                }
                break;
            }
            case BYTE:{
                var cast=(ByteDblLnkSeq)seq;
                var expectedArr=(byte[])this.expectedArr;
                
                var currNode=cast.tail;
                for(int i=expectedSize;;) {
                    if(currNode==null) {
                        break;
                    }
                    expectedArr[--i]=currNode.val;
                    currNode=currNode.prev;
                }
                break;
            }
            case CHAR:{
                var cast=(CharDblLnkSeq)seq;
                var expectedArr=(char[])this.expectedArr;
                
                var currNode=cast.tail;
                for(int i=expectedSize;;) {
                    if(currNode==null) {
                        break;
                    }
                    expectedArr[--i]=currNode.val;
                    currNode=currNode.prev;
                }
                break;
            }
            case DOUBLE:{
                var cast=(DoubleDblLnkSeq)seq;
                var expectedArr=(double[])this.expectedArr;
                
                var currNode=cast.tail;
                for(int i=expectedSize;;) {
                    if(currNode==null) {
                        break;
                    }
                    expectedArr[--i]=currNode.val;
                    currNode=currNode.prev;
                }
                break;
            }
            case FLOAT:{
                var cast=(FloatDblLnkSeq)seq;
                var expectedArr=(float[])this.expectedArr;
                
                var currNode=cast.tail;
                for(int i=expectedSize;;) {
                    if(currNode==null) {
                        break;
                    }
                    expectedArr[--i]=currNode.val;
                    currNode=currNode.prev;
                }
                break;
            }
            case INT:{
                var cast=(IntDblLnkSeq)seq;
                var expectedArr=(int[])this.expectedArr;
                
                var currNode=cast.tail;
                for(int i=expectedSize;;) {
                    if(currNode==null) {
                        break;
                    }
                    expectedArr[--i]=currNode.val;
                    currNode=currNode.prev;
                }
                break;
            }
            case LONG:{
                var cast=(LongDblLnkSeq)seq;
                var expectedArr=(long[])this.expectedArr;
               
                var currNode=cast.tail;
                for(int i=expectedSize;;) {
                    if(currNode==null) {
                        break;
                    }
                    expectedArr[--i]=currNode.val;
                    currNode=currNode.prev;
                }
                break;
            }
            case REF:{
                @SuppressWarnings("unchecked")
                var cast=(RefDblLnkSeq<E>)seq;
                var expectedArr=(Object[])this.expectedArr;
                
                var currNode=cast.tail;
                for(int i=expectedSize;;) {
                    if(currNode==null) {
                        break;
                    }
                    expectedArr[--i]=currNode.val;
                    currNode=currNode.prev;
                }
                while(oldExpectedSize>expectedSize) {
                    expectedArr[--oldExpectedSize]=null;
                }
                break;
            }
            case SHORT:{
                var cast=(ShortDblLnkSeq)seq;
                var expectedArr=(short[])this.expectedArr;
               
                var currNode=cast.tail;
                for(int i=expectedSize;;) {
                    if(currNode==null) {
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


        @Override
        public void verifyClone(Object clone){
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException();
        }

        @Override
        public void verifyRemoveIf(boolean result,MonitoredRemoveIfPredicate filter){
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException();
        }

        @Override
        public void verifyArrayIsCopy(Object arr,boolean emptyArrayMayBeSame){
            //nothing to do
        }

        @Override
        public void writeObjectImpl(MonitoredObjectOutputStream oos) throws IOException{
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException();
        }

      



        @Override
        public void verifyCollectionState(boolean refIsSame){
            final int expectedSize;
            Assertions.assertEquals(expectedSize=this.expectedSize,((AbstractSeq<?>)seq).size);
            
            switch(dataType) {
            case BOOLEAN:{
                
                
                var cast=(BooleanDblLnkSeq)seq;
                var expectedArr=(boolean[])this.expectedArr;
                if(checkedType.checked) {
                    Assertions.assertEquals(expectedModCount,((BooleanDblLnkSeq.CheckedList)cast).modCount);
                }
                var headNode=cast.head;
                var tailNode=cast.tail;
                if(expectedSize==0) {
                    Assertions.assertNull(headNode);
                    Assertions.assertNull(tailNode);
                }else {
                    var prevNode=headNode.prev;
                    Assertions.assertNull(prevNode);
                    for(int i=0;i<expectedSize;++i) {
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
                
                var cast=(ByteDblLnkSeq)seq;
                var expectedArr=(byte[])this.expectedArr;
                if(checkedType.checked) {
                    Assertions.assertEquals(expectedModCount,((ByteDblLnkSeq.CheckedList)cast).modCount);
                }
                var headNode=cast.head;
                var tailNode=cast.tail;
                if(expectedSize==0) {
                    Assertions.assertNull(headNode);
                    Assertions.assertNull(tailNode);
                }else {
                    var prevNode=headNode.prev;
                    Assertions.assertNull(prevNode);
                    for(int i=0;i<expectedSize;++i) {
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
                var cast=(CharDblLnkSeq)seq;
                var expectedArr=(char[])this.expectedArr;
                if(checkedType.checked) {
                    Assertions.assertEquals(expectedModCount,((CharDblLnkSeq.CheckedList)cast).modCount);
                }
                var headNode=cast.head;
                var tailNode=cast.tail;
                if(expectedSize==0) {
                    Assertions.assertNull(headNode);
                    Assertions.assertNull(tailNode);
                }else {
                    var prevNode=headNode.prev;
                    Assertions.assertNull(prevNode);
                    for(int i=0;i<expectedSize;++i) {
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
                var cast=(DoubleDblLnkSeq)seq;
                var expectedArr=(double[])this.expectedArr;
                if(checkedType.checked) {
                    Assertions.assertEquals(expectedModCount,((DoubleDblLnkSeq.CheckedList)cast).modCount);
                }
                var headNode=cast.head;
                var tailNode=cast.tail;
                if(expectedSize==0) {
                    Assertions.assertNull(headNode);
                    Assertions.assertNull(tailNode);
                }else {
                    var prevNode=headNode.prev;
                    Assertions.assertNull(prevNode);
                    for(int i=0;i<expectedSize;++i) {
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
                var cast=(FloatDblLnkSeq)seq;
                var expectedArr=(float[])this.expectedArr;
                if(checkedType.checked) {
                    Assertions.assertEquals(expectedModCount,((FloatDblLnkSeq.CheckedList)cast).modCount);
                }
                var headNode=cast.head;
                var tailNode=cast.tail;
                if(expectedSize==0) {
                    Assertions.assertNull(headNode);
                    Assertions.assertNull(tailNode);
                }else {
                    var prevNode=headNode.prev;
                    Assertions.assertNull(prevNode);
                    for(int i=0;i<expectedSize;++i) {
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
                var cast=(IntDblLnkSeq)seq;
                var expectedArr=(int[])this.expectedArr;
                if(checkedType.checked) {
                    Assertions.assertEquals(expectedModCount,((IntDblLnkSeq.CheckedList)cast).modCount);
                }
                var headNode=cast.head;
                var tailNode=cast.tail;
                if(expectedSize==0) {
                    Assertions.assertNull(headNode);
                    Assertions.assertNull(tailNode);
                }else {
                    var prevNode=headNode.prev;
                    Assertions.assertNull(prevNode);
                    for(int i=0;i<expectedSize;++i) {
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
                var cast=(IntDblLnkSeq)seq;
                var expectedArr=(long[])this.expectedArr;
                if(checkedType.checked) {
                    Assertions.assertEquals(expectedModCount,((IntDblLnkSeq.CheckedList)cast).modCount);
                }
                var headNode=cast.head;
                var tailNode=cast.tail;
                if(expectedSize==0) {
                    Assertions.assertNull(headNode);
                    Assertions.assertNull(tailNode);
                }else {
                    var prevNode=headNode.prev;
                    Assertions.assertNull(prevNode);
                    for(int i=0;i<expectedSize;++i) {
                        Assertions.assertEquals(headNode.val,(int)expectedArr[i]);
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
                @SuppressWarnings("unchecked")
                var cast=(RefDblLnkSeq<E>)seq;
                var expectedArr=(Object[])this.expectedArr;
                if(checkedType.checked) {
                    Assertions.assertEquals(expectedModCount,((RefDblLnkSeq.CheckedList<E>)cast).modCount);
                }
                var headNode=cast.head;
                var tailNode=cast.tail;
                if(expectedSize==0) {
                    Assertions.assertNull(headNode);
                    Assertions.assertNull(tailNode);
                }else {
                    var prevNode=headNode.prev;
                    Assertions.assertNull(prevNode);
                    if(refIsSame) {
                        for(int i=0;i<expectedSize;++i) {
                            Assertions.assertSame(headNode.val,expectedArr[i]);
                            Assertions.assertSame(prevNode,headNode.prev);
                            prevNode=headNode;
                            headNode=headNode.next;
                        }
                    }else {
                        for(int i=0;i<expectedSize;++i) {
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
                var cast=(ShortDblLnkSeq)seq;
                var expectedArr=(short[])this.expectedArr;
                if(checkedType.checked) {
                    Assertions.assertEquals(expectedModCount,((ShortDblLnkSeq.CheckedList)cast).modCount);
                }
                var headNode=cast.head;
                var tailNode=cast.tail;
                if(expectedSize==0) {
                    Assertions.assertNull(headNode);
                    Assertions.assertNull(tailNode);
                }else {
                    var prevNode=headNode.prev;
                    Assertions.assertNull(prevNode);
                    for(int i=0;i<expectedSize;++i) {
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



       

        @Override
        public void updateAddFirstState(Object inputVal,DataType inputType){
            updateAddState(0,inputVal,inputType);
        }
        @SuppressWarnings("unchecked")
        @Override
        LSTDEQ initSeq(){
            switch(dataType) {
            case BOOLEAN:
                if(checkedType.checked) {
                    return (LSTDEQ)new BooleanDblLnkSeq.CheckedList();
                }else {
                    return (LSTDEQ)new BooleanDblLnkSeq.UncheckedList();
                }
            case BYTE:
                if(checkedType.checked) {
                    return (LSTDEQ)new ByteDblLnkSeq.CheckedList();
                }else {
                    return (LSTDEQ)new ByteDblLnkSeq.UncheckedList();
                }
            case CHAR:
                if(checkedType.checked) {
                    return (LSTDEQ)new CharDblLnkSeq.CheckedList();
                }else {
                    return (LSTDEQ)new CharDblLnkSeq.UncheckedList();
                }
            case SHORT:
                if(checkedType.checked) {
                    return (LSTDEQ)new ShortDblLnkSeq.CheckedList();
                }else {
                    return (LSTDEQ)new ShortDblLnkSeq.UncheckedList();
                }
            case INT:
                if(checkedType.checked) {
                    return (LSTDEQ)new IntDblLnkSeq.CheckedList();
                }else {
                    return (LSTDEQ)new IntDblLnkSeq.UncheckedList();
                }
            case LONG:
                if(checkedType.checked) {
                    return (LSTDEQ)new LongDblLnkSeq.CheckedList();
                }else {
                    return (LSTDEQ)new LongDblLnkSeq.UncheckedList();
                }
            case FLOAT:
                if(checkedType.checked) {
                    return (LSTDEQ)new FloatDblLnkSeq.CheckedList();
                }else {
                    return (LSTDEQ)new FloatDblLnkSeq.UncheckedList();
                }
            case DOUBLE:
                if(checkedType.checked) {
                    return (LSTDEQ)new DoubleDblLnkSeq.CheckedList();
                }else {
                    return (LSTDEQ)new DoubleDblLnkSeq.UncheckedList();
                }
            case REF:
                if(checkedType.checked) {
                    return (LSTDEQ)new RefDblLnkSeq.CheckedList<>();
                }else {
                    return (LSTDEQ)new RefDblLnkSeq.UncheckedList<>();
                }
            default:
                throw dataType.invalid();
            }
        }
        @Override
        LSTDEQ initSeq(int initCap){
            return initSeq();
        }
        @Override
        void updateModCount(){
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
}
