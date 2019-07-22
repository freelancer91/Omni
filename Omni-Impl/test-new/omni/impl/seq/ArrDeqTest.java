package omni.impl.seq;

import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import omni.api.OmniDeque;
import omni.api.OmniIterator;
import omni.impl.CheckedType;
import omni.impl.DataType;
import omni.impl.FunctionCallType;
import omni.impl.IteratorType;
import omni.impl.MonitoredDeque;
import omni.impl.MonitoredObjectOutputStream;
import omni.impl.MonitoredRemoveIfPredicate;
import omni.impl.QueryCastType;
import omni.impl.QueryVal;
import omni.impl.QueryVal.QueryValModification;
import omni.impl.StructType;

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
            //TODO
        }

        @Override
        public void updateRemoveValState(Object inputVal,DataType inputType){
            // TODO Auto-generated method stub
            
        }

        @Override
        public Object get(int iterationIndex,DataType outputType){
            iterationIndex+=expectedHead;
            if(iterationIndex>=expectedCapacity) {
                iterationIndex-=expectedCapacity;
            }
            
            switch(dataType) {
            case BOOLEAN:
                return outputType.convertVal(((boolean[])expectedArr)[iterationIndex]);
            case BYTE:
                return outputType.convertVal(((byte[])expectedArr)[iterationIndex]);
            case CHAR:
                return outputType.convertVal(((char[])expectedArr)[iterationIndex]);
            case SHORT:
                return outputType.convertVal(((short[])expectedArr)[iterationIndex]);
            case INT:
                return outputType.convertVal(((int[])expectedArr)[iterationIndex]);
            case LONG:
                return outputType.convertVal(((long[])expectedArr)[iterationIndex]);
            case FLOAT:
                return outputType.convertVal(((float[])expectedArr)[iterationIndex]);
            case DOUBLE:
                return outputType.convertVal(((double[])expectedArr)[iterationIndex]);
            case REF:
                return outputType.convertVal(((Object[])expectedArr)[iterationIndex]);
            default:
                throw dataType.invalid();
            }
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

        @Override
        public MonitoredIterator<? extends OmniIterator<?>,OmniDeque<?>> getMonitoredIterator(){
            // TODO Auto-generated method stub
            return null;
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
            // TODO Auto-generated method stub
            
        }

        @Override
        public void updateCollectionState(){
            // TODO Auto-generated method stub
            
        }

        @Override
        public void verifyCollectionState(){
            // TODO Auto-generated method stub
            
        }

        @Override
        public void verifyClone(Object clone){
            // TODO Auto-generated method stub
            
        }

        @Override
        public void verifyRemoveIf(boolean result,MonitoredRemoveIfPredicate filter){
            // TODO Auto-generated method stub
            
        }

        @Override
        public void verifyArrayIsCopy(Object arr,boolean emptyArrayMayBeSame){
            // TODO Auto-generated method stub
            
        }

        @Override
        public void writeObjectImpl(MonitoredObjectOutputStream oos) throws IOException{
            // TODO Auto-generated method stub
            
        }

        @Override
        public MonitoredIterator<?,OmniDeque<?>> getMonitoredDescendingIterator(){
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void updateRemoveLastOccurrenceState(Object inputVal,DataType inputType){
            // TODO Auto-generated method stub
            
        }

        @Override
        public void updateAddFirstState(Object inputVal,DataType inputType){
            // TODO Auto-generated method stub
            
        }
        @Override
        public boolean verifyAdd(Object inputVal,DataType inputType,FunctionCallType functionCallType){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        public boolean verifyRemoveVal(QueryVal queryVal,DataType inputType,QueryCastType queryCastType,
                QueryValModification modification){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        public void verifyHashCode(int hashCode){
            // TODO Auto-generated method stub
            
        }
        @Override
        public boolean add(int val){
            // TODO Auto-generated method stub
            return false;
        }
        
    }
    
}
