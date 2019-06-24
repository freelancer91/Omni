package omni.impl.set;

import org.junit.jupiter.api.Assertions;
import omni.api.OmniIterator;
import omni.impl.CheckedType;
import omni.impl.DataType;
import omni.impl.MonitoredCollection;
import omni.impl.MonitoredFunction;
import omni.impl.MonitoredRemoveIfPredicate;
import omni.impl.MonitoredSet;
import omni.impl.QueryVal;
import omni.impl.QueryVal.QueryValModification;
import omni.impl.StructType;

public class ByteSetImplMonitor implements MonitoredSet<ByteSetImpl>{
    final CheckedType checkedType;
    final ByteSetImpl set;
    final long[] expectedWords;
    int expectedSize;
    int expectedModCount;

    private int getExpectedSize() {
        int size=0;
        for(var word:expectedWords) {
            size+=Long.bitCount(word);
        }
        return size;
    }
    ByteSetImplMonitor(CheckedType checkedType,ByteSetImpl set){
        this.checkedType=checkedType;
        this.set=set;
        this.expectedWords=new long[4];
        expectedWords[0]=set.word0;
        expectedWords[1]=set.word1;
        expectedWords[2]=set.word2;
        expectedWords[3]=set.word3;
        this.expectedSize=getExpectedSize();
        if(checkedType.checked) {
            expectedModCount=((ByteSetImpl.Checked)set).modCount;
        }
    }
    @Override public CheckedType getCheckedType(){
        return this.checkedType;
    }
    @Override public ByteSetImpl getCollection(){
        return set;
    }
    @Override public DataType getDataType(){
        return DataType.BYTE;
    }

    abstract class AbstractMonitoredItr implements MonitoredSet.MonitoredSetIterator<OmniIterator.OfByte,ByteSetImpl>{
        final OmniIterator.OfByte itr;
        int expectedValOffset;
        AbstractMonitoredItr(OmniIterator.OfByte itr,int expectedValOffset){
            this.itr=itr;
            this.expectedValOffset=expectedValOffset;
        }
        @Override public OmniIterator.OfByte getIterator(){
            return itr;
        }
        @Override public MonitoredCollection<ByteSetImpl> getMonitoredCollection(){
            return ByteSetImplMonitor.this;
        }
        @Override public boolean hasNext(){
            return expectedValOffset!=128;
        }
        @Override public int getNumLeft(){
            int numLeft=0;
            int expectedValOffset=this.expectedValOffset;
            for(int i=(expectedValOffset>>6)+2;i<4;++i) {
                numLeft+=Long.bitCount(expectedWords[i]>>expectedValOffset);
                expectedValOffset=0;
            }
            return numLeft;
        }


    }
    class UncheckedMonitoredItr extends AbstractMonitoredItr{

        UncheckedMonitoredItr(OmniIterator.OfByte itr,int expectedValOffset){
            super(itr,expectedValOffset);
        }
        @Override public void verifyForEachRemaining(MonitoredFunction function){
            var monitoredFunctionItr=function.iterator();
            int expectedValOffset;
            for(expectedValOffset=this.expectedValOffset;expectedValOffset < 128;++expectedValOffset){
                if((expectedWords[(expectedValOffset >> 6) + 2] & 1L << expectedValOffset) != 0){
                    var v=(Byte)monitoredFunctionItr.next();
                    Assertions.assertEquals(v.byteValue(),expectedValOffset);
                }
            }
            Assertions.assertFalse(monitoredFunctionItr.hasNext());
            this.expectedValOffset=128;
        }


        @Override public void updateIteratorState(){
            this.expectedValOffset=FieldAndMethodAccessor.ByteSetImpl.Itr.valOffset(itr);
        }


        @Override
        public void updateItrNextState(){
            int expectedValOffset;
            switch((expectedValOffset=this.expectedValOffset + 1) >> 6){
            case -2:
                if((expectedValOffset=Long.numberOfTrailingZeros(expectedWords[0] >>> expectedValOffset)) != 64){
                    this.expectedValOffset=expectedValOffset - 128;
                    break;
                }
                expectedValOffset=0;
            case -1:
                if((expectedValOffset=Long.numberOfTrailingZeros(expectedWords[1] >>> expectedValOffset)) != 64){
                    this.expectedValOffset=expectedValOffset - 64;
                    break;
                }
                expectedValOffset=0;
            case 0:
                if((expectedValOffset=Long.numberOfTrailingZeros(expectedWords[2] >>> expectedValOffset)) != 64){
                    this.expectedValOffset=expectedValOffset;
                    break;
                }
                expectedValOffset=0;
            case 1:
                this.expectedValOffset=Long.numberOfTrailingZeros(expectedWords[3] >>> expectedValOffset) + 64;
                break;
            default:
                this.expectedValOffset=128;
            }
        }

        @Override
        public void updateItrRemoveState(){

            long word;
            int valOffset;
            switch((valOffset=this.expectedValOffset) - 1 >> 6){
            case 1:
                if((valOffset=Long.numberOfLeadingZeros((word=expectedWords[3]) << -valOffset)) != 64){
                    expectedWords[3]=word & ~(1L << -1 - valOffset);
                    return;
                }
                valOffset=0;
            case 0:
                if((valOffset=Long.numberOfLeadingZeros((word=expectedWords[2]) << -valOffset)) != 64){
                    expectedWords[2]=word & ~(1L << -1 - valOffset);
                    return;
                }
                valOffset=0;
            case -1:
                if((valOffset=Long.numberOfLeadingZeros((word=expectedWords[1]) << -valOffset)) != 64){
                    expectedWords[1]=word & ~(1L << -1 - valOffset);
                    return;
                }
                valOffset=0;
            default:
                expectedWords[0]=(word=expectedWords[0]) & ~(1L << -1 - Long.numberOfLeadingZeros(word << -valOffset));
            }
        }
        @Override
        public void verifyIteratorState(Object itr){
            Assertions.assertEquals(expectedValOffset,FieldAndMethodAccessor.ByteSetImpl.Itr.valOffset(itr));
            Assertions.assertSame(set,FieldAndMethodAccessor.ByteSetImpl.Itr.root(itr));
        }


    }

    class CheckedMonitoredItr extends AbstractMonitoredItr{
        int expectedItrModCount;
        int expectedItrLastRet;
        CheckedMonitoredItr(OmniIterator.OfByte itr,int expectedValOffset,int expectedItrModCount){
            super(itr,expectedValOffset);
            this.expectedItrModCount=expectedItrModCount;
            this.expectedItrLastRet=-129;
        }
        @Override public void verifyForEachRemaining(MonitoredFunction function){
            var monitoredFunctionItr=function.iterator();
            int expectedValOffset;
            int expectedLastRet;
            for(expectedLastRet=expectedValOffset=this.expectedValOffset;expectedValOffset < 128;++expectedValOffset){
                if((expectedWords[(expectedValOffset >> 6) + 2] & 1L << expectedValOffset) != 0){
                    var v=(Byte)monitoredFunctionItr.next();
                    Assertions.assertEquals(v.byteValue(),expectedValOffset);
                    expectedLastRet=expectedValOffset;
                }
            }
            Assertions.assertFalse(monitoredFunctionItr.hasNext());
            this.expectedValOffset=128;
            this.expectedItrLastRet=expectedLastRet;
        }
        @Override public void updateIteratorState(){
            this.expectedValOffset=FieldAndMethodAccessor.ByteSetImpl.Checked.Itr.valOffset(itr);
            this.expectedItrModCount=FieldAndMethodAccessor.ByteSetImpl.Checked.Itr.modCount(itr);
            this.expectedItrLastRet=FieldAndMethodAccessor.ByteSetImpl.Checked.Itr.lastRet(itr);
        }
        @Override
        public void verifyIteratorState(Object clone){
            Assertions.assertEquals(expectedValOffset,FieldAndMethodAccessor.ByteSetImpl.Checked.Itr.valOffset(clone));
            Assertions.assertEquals(expectedItrLastRet,FieldAndMethodAccessor.ByteSetImpl.Checked.Itr.lastRet(clone));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.ByteSetImpl.Checked.Itr.modCount(clone));
            Assertions.assertSame(set,FieldAndMethodAccessor.ByteSetImpl.Checked.Itr.root(clone));
        }
        @Override
        public void updateItrRemoveState(){
            expectedWords[(expectedItrLastRet >> 6) + 2]&=~(1L << expectedItrLastRet);
            --expectedSize;
            ++expectedModCount;
            ++expectedItrModCount;
            expectedItrLastRet=-129;
        }
        @Override
        public void updateItrNextState(){
            this.expectedItrLastRet=expectedValOffset;
            int expectedValOffset;
            switch((expectedValOffset=this.expectedValOffset + 1) >> 6){
            case -2:
                if((expectedValOffset=Long.numberOfTrailingZeros(expectedWords[3] >>> expectedValOffset)) != 64){
                    this.expectedValOffset=expectedValOffset - 128;
                    break;
                }
                expectedValOffset=0;
            case -1:
                if((expectedValOffset=Long.numberOfTrailingZeros(expectedWords[3] >>> expectedValOffset)) != 64){
                    this.expectedValOffset=expectedValOffset - 64;
                    break;
                }
                expectedValOffset=0;
            case 0:
                if((expectedValOffset=Long.numberOfTrailingZeros(expectedWords[3] >>> expectedValOffset)) != 64){
                    this.expectedValOffset=expectedValOffset;
                    break;
                }
                expectedValOffset=0;
            case 1:
                this.expectedValOffset=Long.numberOfTrailingZeros(expectedWords[3] >>> expectedValOffset) + 64;
                break;
            default:
                this.expectedValOffset=128;
            }
        }


    }

    @Override public MonitoredIterator<? extends OmniIterator<?>,ByteSetImpl> getMonitoredIterator(){
        int expectedValOffset;
        for(expectedValOffset=-128;expectedValOffset < 128;++expectedValOffset){
            if((expectedWords[(expectedValOffset >> 6) + 2] & 1L << expectedValOffset) != 0){
                break;
            }
        }
        var itr=set.iterator();
        if(checkedType.checked){
            return new CheckedMonitoredItr(itr,expectedValOffset,expectedModCount);
        }
        return new UncheckedMonitoredItr(itr,expectedValOffset);
    }
    @Override public StructType getStructType(){
        return StructType.ByteSetImpl;
    }
    @Override public boolean isEmpty(){
        return expectedSize==0;
    }
    @Override public void modCollection(){
        for(int i=Byte.MIN_VALUE;i <= Byte.MAX_VALUE;++i){
            if(set.add((byte)i)){
                expectedWords[(i >> 6) + 2]|=1L << i;
                ++expectedSize;
                ++expectedModCount;
                return;
            }
        }
        set.clear();
        for(int i=0;i < 4;++i){
            expectedWords[i]=0;
        }
        expectedSize=0;
        ++expectedModCount;
    }
    @Override public int size(){
        return expectedSize;
    }
    @Override public void updateCollectionState(){
        if(checkedType.checked) {
            expectedModCount=((ByteSetImpl.Checked)set).modCount;
        }
        expectedWords[0]=set.word0;
        expectedWords[1]=set.word1;
        expectedWords[2]=set.word2;
        expectedWords[3]=set.word3;
        expectedSize=getExpectedSize();

    }
    @Override public void verifyCollectionState(){
        if(checkedType.checked) {
            ByteSetImpl.Checked cast;
            Assertions.assertEquals(expectedModCount,(cast=(ByteSetImpl.Checked)set).modCount);
            Assertions.assertEquals(expectedSize,cast.size);
        }
        Assertions.assertEquals(expectedWords[0],set.word0);
        Assertions.assertEquals(expectedWords[1],set.word1);
        Assertions.assertEquals(expectedWords[2],set.word2);
        Assertions.assertEquals(expectedWords[3],set.word3);
    }
    @Override public void verifyClone(Object clone){
        ByteSetImpl cast;
        Assertions.assertEquals(expectedWords[0],(cast=(ByteSetImpl)clone).word0);
        Assertions.assertEquals(expectedWords[1],cast.word1);
        Assertions.assertEquals(expectedWords[2],cast.word2);
        Assertions.assertEquals(expectedWords[3],cast.word3);
        if(checkedType.checked) {
            ByteSetImpl.Checked checked;
            Assertions.assertEquals(0,(checked=(ByteSetImpl.Checked)cast).modCount);
            Assertions.assertEquals(expectedSize,checked.size);
        }
    }



    @Override public void verifyRemoveIf(boolean result,MonitoredRemoveIfPredicate filter){
        Assertions.assertEquals(expectedSize,filter.numCalls);
        if(result) {
            ++expectedModCount;
        }
        Assertions.assertEquals(!result,filter.removedVals.isEmpty());
        for(var removedVal:filter.removedVals) {
            byte v=(byte)removedVal;
            expectedWords[(v>>6)+2]&=~(1L<<v);
            --expectedSize;
        }
        Assertions.assertEquals(expectedSize,filter.retainedVals.size());
    }
    @Override public void verifyArrayIsCopy(Object arr){
        //nothing to do
    }
    @Override public void updateAddState(Object inputVal,DataType inputType,boolean boxed,boolean result){
        byte v;
        switch(inputType) {
        case BOOLEAN:
            v=(boolean)inputVal?(byte)1:(byte)0;
            break;
        case BYTE:
            v=(byte)inputVal;
            break;
        default:
            throw new UnsupportedOperationException("Unknown inputType "+inputType);
        }
        expectedWords[(v>>6)+2]|=1L<<v;
        if(result) {
            ++expectedSize;
            ++expectedModCount;
        }
    }
    @Override public void removeFromExpectedState(QueryVal queryVal,QueryValModification modification){
        byte v=(byte)queryVal.getInputVal(DataType.BYTE,modification);
        expectedWords[(v>>6)+2]&=~(1L<<v);
        --expectedSize;
        ++expectedModCount;
    }

}
