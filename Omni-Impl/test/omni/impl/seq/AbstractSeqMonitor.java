package omni.impl.seq;

import omni.api.OmniCollection;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import omni.impl.CheckedType;
import omni.impl.DataType;
import omni.impl.ItrType;
import omni.impl.StructType;
import omni.impl.UnderlyingStruct;
public abstract class AbstractSeqMonitor{
    public final DataType dataType;
    public final CheckedType checkedType;
    public final UnderlyingStruct underlyingStruct;
    public final StructType structType;
    public final OmniCollection seq;
    public int expectedSeqSize;
    public int expectedSeqModCount;
    AbstractSeqMonitor(DataType dataType,CheckedType checkedType,UnderlyingStruct underlyingStruct,
            StructType structType,OmniCollection seq,int expectedSeqSize,int expectedSeqModCount){
        this.dataType=dataType;
        this.checkedType=checkedType;
        this.underlyingStruct=underlyingStruct;
        this.structType=structType;
        this.seq=seq;
        this.expectedSeqSize=expectedSeqSize;
        this.expectedSeqModCount=expectedSeqModCount;
    }
    public abstract void verifyStructuralIntegrity();
    // TODO lots more
    public static abstract class AbstractItrMonitor{
        public final ItrType itrType;
        public final OmniIterator<?> itr;
        AbstractItrMonitor(ItrType itrType,OmniIterator<?> itr){
            this.itrType=itrType;
            this.itr=itr;
        }
        public abstract void verifyItrIntegrity();
        public abstract DataType getDataType();
        public abstract StructType getStructType();
        public abstract UnderlyingStruct getUnderlyingStruct();
        public abstract void verifyNext(int expectedVal,DataType outputType);
        // TODO for each remaining
        public int nextIndex(){
            int index=((OmniListIterator<?>)itr).nextIndex();
            verifyItrIntegrity();
            return index;
        }
        public int previousIndex(){
            int index=((OmniListIterator<?>)itr).previousIndex();
            verifyItrIntegrity();
            return index;
        }
        public boolean hasNext(){
            boolean ret=itr.hasNext();
            verifyItrIntegrity();
            return ret;
        }
        public boolean hasPrevious(){
            boolean ret=((OmniListIterator<?>)itr).hasPrevious();
            verifyItrIntegrity();
            return ret;
        }
        public void verifyPrevious(int expectedVal,DataType outputType){
            throw new UnsupportedOperationException();
        }
        public void add(int inputVal,DataType inputType){
            throw new UnsupportedOperationException();
        }
        public void set(int inputVal,DataType inputType){
            throw new UnsupportedOperationException();
        }
    }
}
