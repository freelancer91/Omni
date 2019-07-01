package omni.impl.set;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import org.junit.jupiter.api.Assertions;
import omni.api.OmniIterator;
import omni.api.OmniSet;
import omni.impl.CheckedType;
import omni.impl.DataType;
import omni.impl.FunctionCallType;
import omni.impl.IllegalModification;
import omni.impl.IteratorType;
import omni.impl.MonitoredCollection;
import omni.impl.MonitoredFunction;
import omni.impl.MonitoredFunctionGen;
import omni.impl.MonitoredObjectOutputStream;
import omni.impl.MonitoredRemoveIfPredicate;
import omni.impl.MonitoredSet;
import omni.impl.QueryVal;
import omni.impl.QueryVal.QueryValModification;
import omni.impl.StructType;
import omni.util.OmniArray;
import omni.util.TestExecutorService;
public class IntegralOpenAddressHashSetTest{
  private static class IntegralOpenAddressHashSetMonitor implements MonitoredSet<AbstractIntegralTypeOpenAddressHashSet<?>>{
    abstract class AbstractItrMonitor
    implements MonitoredSet.MonitoredSetIterator<OmniIterator<?>,AbstractIntegralTypeOpenAddressHashSet<?>>{
        final OmniIterator<?> itr;
        int expectedOffset;
        int expectedNumLeft;
        AbstractItrMonitor(OmniIterator<?> itr,int expectedOffset,int expectedNumLeft){
            this.itr=itr;
            this.expectedOffset=expectedOffset;
            this.expectedNumLeft=expectedNumLeft;
        }
        @Override
        public void iterateForward(){
            MonitoredSet.MonitoredSetIterator.super.iterateForward();
            --expectedNumLeft;
        }
        @Override public OmniIterator<?> getIterator(){
            return itr;
        }
        @Override public MonitoredCollection<AbstractIntegralTypeOpenAddressHashSet<?>> getMonitoredCollection(){
            return IntegralOpenAddressHashSetMonitor.this;
        }
        @Override public int getNumLeft(){
            return expectedNumLeft;
        }
        @Override public boolean hasNext(){
            return expectedOffset != -1;
        }
        @Override public void updateItrNextState(){
            int expectedOffset;
            if((expectedOffset=this.expectedOffset) < 256){
                updateItrNextFromWords(expectedOffset);
            }else{
                updateItrNextFromTable(expectedOffset - 256);
            }
            --expectedNumLeft;
        }
        @Override
        public void modItr(){
            MonitoredSet.MonitoredSetIterator.super.modItr();
            --expectedNumLeft;
        }
        private int verifyForEachRemainingHelper(MonitoredFunction function,int expectedLastRet){
            final var monitoredFunctionItr=function.iterator();
            int expectedOffset;
            if((expectedOffset=this.expectedOffset) != -1){
                switch(dataType){
                case CHAR:{
                    if(expectedOffset < 256){
                        do{
                            if((expectedWords[expectedOffset >> 6] & 1L << expectedOffset) != 0){
                                final var v=(Character)monitoredFunctionItr.next();
                                Assertions.assertEquals(v.charValue(),expectedOffset);
                                expectedLastRet=expectedOffset;
                            }
                        }while(++expectedOffset != 256);
                    }
                    expectedOffset-=256;
                    int expectedTableSize;
                    if((expectedTableSize=IntegralOpenAddressHashSetMonitor.this.expectedTableSize) != 0){
                        final char[] table=(char[])expectedTable;
                        for(;;++expectedOffset){
                            char tableVal;
                            if(((tableVal=table[expectedOffset]) & -2) != 0){
                                final var v=(Character)monitoredFunctionItr.next();
                                Assertions.assertEquals(v.charValue(),tableVal);
                                expectedLastRet=expectedOffset + 256;
                                if(--expectedTableSize == 0){
                                    break;
                                }
                            }
                        }
                    }
                    break;
                }
                case SHORT:{
                    if(expectedOffset < 256){
                        do{
                            if((expectedWords[expectedOffset >> 6] & 1L << expectedOffset) != 0){
                                final var v=(Short)monitoredFunctionItr.next();
                                Assertions.assertEquals(v.shortValue(),expectedOffset - 128);
                                expectedLastRet=expectedOffset;
                            }
                        }while(++expectedOffset != 256);
                    }
                    expectedOffset-=256;
                    int expectedTableSize;
                    if((expectedTableSize=IntegralOpenAddressHashSetMonitor.this.expectedTableSize) != 0){
                        final short[] table=(short[])expectedTable;
                        for(;;++expectedOffset){
                            short tableVal;
                            if(((tableVal=table[expectedOffset]) & -2) != 0){
                                final var v=(Short)monitoredFunctionItr.next();
                                Assertions.assertEquals(v.shortValue(),tableVal);
                                expectedLastRet=expectedOffset + 256;
                                if(--expectedTableSize == 0){
                                    break;
                                }
                            }
                        }
                    }
                    break;
                }
                case INT:{
                    if(expectedOffset < 256){
                        do{
                            if((expectedWords[expectedOffset >> 6] & 1L << expectedOffset) != 0){
                                final var v=(Integer)monitoredFunctionItr.next();
                                Assertions.assertEquals(v.intValue(),expectedOffset - 128);
                                expectedLastRet=expectedOffset;
                            }
                        }while(++expectedOffset != 256);
                    }
                    expectedOffset-=256;
                    int expectedTableSize;
                    if((expectedTableSize=IntegralOpenAddressHashSetMonitor.this.expectedTableSize) != 0){
                        final int[] table=(int[])expectedTable;
                        for(;;++expectedOffset){
                            int tableVal;
                            if(((tableVal=table[expectedOffset]) & -2) != 0){
                                final var v=(Integer)monitoredFunctionItr.next();
                                Assertions.assertEquals(v.intValue(),tableVal);
                                expectedLastRet=expectedOffset + 256;
                                if(--expectedTableSize == 0){
                                    break;
                                }
                            }
                        }
                    }
                    break;
                }
                case LONG:{
                    if(expectedOffset < 256){
                        do{
                            if((expectedWords[expectedOffset >> 6] & 1L << expectedOffset) != 0){
                                final var v=(Long)monitoredFunctionItr.next();
                                Assertions.assertEquals(v.longValue(),expectedOffset - 128);
                                expectedLastRet=expectedOffset;
                            }
                        }while(++expectedOffset != 256);
                    }
                    expectedOffset-=256;
                    int expectedTableSize;
                    if((expectedTableSize=IntegralOpenAddressHashSetMonitor.this.expectedTableSize) != 0){
                        final long[] table=(long[])expectedTable;
                        for(;;++expectedOffset){
                            long tableVal;
                            if(((tableVal=table[expectedOffset]) & -2) != 0){
                                final var v=(Long)monitoredFunctionItr.next();
                                Assertions.assertEquals(v.longValue(),tableVal);
                                expectedLastRet=expectedOffset + 256;
                                if(--expectedTableSize == 0){
                                    break;
                                }
                            }
                        }
                    }
                    break;
                }
                default:
                    throw DataType.invalidDataType(dataType);
                }
            }
            Assertions.assertFalse(monitoredFunctionItr.hasNext());
            this.expectedOffset=-1;
            this.expectedNumLeft=0;
            return expectedLastRet;
        }
        void updateItrNextFromTable(int expectedOffset){
            switch(dataType){
            case CHAR:{
                char[] table;
                for(final int tableLength=(table=(char[])expectedTable).length;;){
                    if(++expectedOffset == tableLength){
                        this.expectedOffset=-1;
                        break;
                    }
                    if((table[expectedOffset] & -2) != 0){
                        this.expectedOffset=expectedOffset + 256;
                        break;
                    }

                }
                break;
            }
            case SHORT:{
                short[] table;
                for(final int tableLength=(table=(short[])expectedTable).length;;){
                    if(++expectedOffset == tableLength){
                        this.expectedOffset=-1;
                        break;
                    }
                    if((table[expectedOffset] & -2) != 0){
                        this.expectedOffset=expectedOffset + 256;
                        break;
                    }

                }
                break;
            }
            case INT:{
                int[] table;
                for(final int tableLength=(table=(int[])expectedTable).length;;){
                    if(++expectedOffset == tableLength){
                        this.expectedOffset=-1;
                        break;
                    }
                    if((table[expectedOffset] & -2) != 0){
                        this.expectedOffset=expectedOffset + 256;
                        break;
                    }

                }
                break;
            }
            case LONG:{
                long[] table;
                for(final int tableLength=(table=(long[])expectedTable).length;;){
                    if(++expectedOffset == tableLength){
                        this.expectedOffset=-1;
                        break;
                    }
                    if((table[expectedOffset] & -2) != 0){
                        this.expectedOffset=expectedOffset + 256;
                        break;
                    }

                }
                break;
            }
            default:
                throw DataType.invalidDataType(dataType);
            }
        }
        void updateItrNextFromWords(int expectedOffset){
            while(++expectedOffset < 256){
                if((expectedWords[expectedOffset >> 6] & 1L << expectedOffset) != 0){
                    this.expectedOffset=expectedOffset;
                    return;
                }
            }
            expectedOffset-=256;
            if(expectedTableSize != 0){
                switch(dataType){
                case CHAR:{
                    final char[] table=(char[])expectedTable;
                    for(;;++expectedOffset){
                        if((table[expectedOffset] & -2) != 0){
                            break;
                        }
                    }
                    break;
                }
                case SHORT:{
                    final short[] table=(short[])expectedTable;
                    for(;;++expectedOffset){
                        if((table[expectedOffset] & -2) != 0){
                            break;
                        }
                    }
                    break;
                }
                case INT:{
                    final int[] table=(int[])expectedTable;
                    for(;;++expectedOffset){
                        if((table[expectedOffset] & -2) != 0){
                            break;
                        }
                    }
                    break;
                }
                case LONG:{
                    final long[] table=(long[])expectedTable;
                    for(;;++expectedOffset){
                        if((table[expectedOffset] & -2L) != 0){
                            break;
                        }
                    }
                    break;
                }
                default:
                    throw DataType.invalidDataType(dataType);
                }
                this.expectedOffset=expectedOffset + 256;
            }else{
                this.expectedOffset=-1;
            }

        }
        @Override
        public void verifyNextResult(DataType outputType,Object result){
            Object expectedResult;
            if(expectedOffset < 256){

                switch(dataType){
                case CHAR:
                    expectedResult=outputType.convertVal((char)expectedOffset);
                    break;
                case SHORT:
                    expectedResult=outputType.convertVal((short)(expectedOffset - 128));
                    break;
                case INT:
                    expectedResult=outputType.convertVal(expectedOffset - 128);
                    break;
                case LONG:
                    expectedResult=outputType.convertVal((long)(expectedOffset - 128));
                    break;
                default:
                    throw DataType.invalidDataType(dataType);
                }

            }else{
                switch(dataType){
                case CHAR:
                    expectedResult=outputType.convertVal(((char[])expectedTable)[expectedOffset - 256]);
                    break;
                case SHORT:
                    expectedResult=outputType.convertVal(((short[])expectedTable)[expectedOffset - 256]);
                    break;
                case INT:
                    expectedResult=outputType.convertValUnchecked(((int[])expectedTable)[expectedOffset - 256]);
                    break;
                case LONG:
                    expectedResult=outputType.convertValUnchecked(((long[])expectedTable)[expectedOffset - 256]);
                    break;
                default:
                    throw DataType.invalidDataType(dataType);
                }
            }
            Assertions.assertEquals(expectedResult,result);
        }
    }
    class CheckedItrMonitor extends AbstractItrMonitor{
        int expectedItrModCount;
        int expectedItrLastRet;
        CheckedItrMonitor(OmniIterator<?> itr,int expectedOffset,int expectedNumLeft,int expectedItrModCount){
            super(itr,expectedOffset,expectedNumLeft);
            this.expectedItrModCount=expectedItrModCount;
            expectedItrLastRet=-1;
        }
        @Override public void updateIteratorState(){
            switch(dataType){
            case CHAR:
                expectedOffset=FieldAndMethodAccessor.CharOpenAddressHashSet.Checked.Itr.offset(itr);
                expectedItrModCount=FieldAndMethodAccessor.CharOpenAddressHashSet.Checked.Itr.modCount(itr);
                expectedItrLastRet=FieldAndMethodAccessor.CharOpenAddressHashSet.Checked.Itr.lastRet(itr);
                break;
            case SHORT:
                expectedOffset=FieldAndMethodAccessor.ShortOpenAddressHashSet.Checked.Itr.offset(itr);
                expectedItrModCount=FieldAndMethodAccessor.ShortOpenAddressHashSet.Checked.Itr.modCount(itr);
                expectedItrLastRet=FieldAndMethodAccessor.ShortOpenAddressHashSet.Checked.Itr.lastRet(itr);
                break;
            case INT:
                expectedOffset=FieldAndMethodAccessor.IntOpenAddressHashSet.Checked.Itr.offset(itr);
                expectedItrModCount=FieldAndMethodAccessor.IntOpenAddressHashSet.Checked.Itr.modCount(itr);
                expectedItrLastRet=FieldAndMethodAccessor.IntOpenAddressHashSet.Checked.Itr.lastRet(itr);
                break;
            case LONG:
                expectedOffset=FieldAndMethodAccessor.LongOpenAddressHashSet.Checked.Itr.offset(itr);
                expectedItrModCount=FieldAndMethodAccessor.LongOpenAddressHashSet.Checked.Itr.modCount(itr);
                expectedItrLastRet=FieldAndMethodAccessor.LongOpenAddressHashSet.Checked.Itr.lastRet(itr);
                break;
            default:
                throw DataType.invalidDataType(dataType);
            }
        }
        @Override public void updateItrRemoveState(){
            ++expectedModCount;
            ++expectedItrModCount;
            --expectedSize;
            if(expectedItrLastRet < 256){
                expectedWords[expectedItrLastRet >> 6]&=~(1L << expectedItrLastRet);
            }else{
                switch(dataType){
                case CHAR:
                    ((char[])expectedTable)[expectedItrLastRet - 256]=1;
                    break;
                case SHORT:
                    ((short[])expectedTable)[expectedItrLastRet - 256]=1;
                    break;
                case INT:
                    ((int[])expectedTable)[expectedItrLastRet - 256]=1;
                    break;
                case LONG:
                    ((long[])expectedTable)[expectedItrLastRet - 256]=1;
                    break;
                default:
                    throw DataType.invalidDataType(dataType);
                }
                --expectedTableSize;
            }
            expectedItrLastRet=-1;
        }
        @Override public void verifyForEachRemaining(MonitoredFunction function){
            expectedItrLastRet=super.verifyForEachRemainingHelper(function,expectedItrLastRet);
        }
        @Override public void verifyIteratorState(Object itr){
            switch(dataType){
            case CHAR:
                Assertions.assertSame(set,FieldAndMethodAccessor.CharOpenAddressHashSet.Checked.Itr.root(itr));
                Assertions.assertEquals(expectedOffset,FieldAndMethodAccessor.CharOpenAddressHashSet.Checked.Itr.offset(itr));
                Assertions.assertEquals(expectedItrModCount,
                        FieldAndMethodAccessor.CharOpenAddressHashSet.Checked.Itr.modCount(itr));
                Assertions.assertEquals(expectedItrLastRet,
                        FieldAndMethodAccessor.CharOpenAddressHashSet.Checked.Itr.lastRet(itr));
                break;
            case SHORT:
                Assertions.assertSame(set,FieldAndMethodAccessor.ShortOpenAddressHashSet.Checked.Itr.root(itr));
                Assertions.assertEquals(expectedOffset,FieldAndMethodAccessor.ShortOpenAddressHashSet.Checked.Itr.offset(itr));
                Assertions.assertEquals(expectedItrModCount,
                        FieldAndMethodAccessor.ShortOpenAddressHashSet.Checked.Itr.modCount(itr));
                Assertions.assertEquals(expectedItrLastRet,
                        FieldAndMethodAccessor.ShortOpenAddressHashSet.Checked.Itr.lastRet(itr));
                break;
            case INT:
                Assertions.assertSame(set,FieldAndMethodAccessor.IntOpenAddressHashSet.Checked.Itr.root(itr));
                Assertions.assertEquals(expectedOffset,FieldAndMethodAccessor.IntOpenAddressHashSet.Checked.Itr.offset(itr));
                Assertions.assertEquals(expectedItrModCount,
                        FieldAndMethodAccessor.IntOpenAddressHashSet.Checked.Itr.modCount(itr));
                Assertions.assertEquals(expectedItrLastRet,
                        FieldAndMethodAccessor.IntOpenAddressHashSet.Checked.Itr.lastRet(itr));
                break;
            case LONG:
                Assertions.assertSame(set,FieldAndMethodAccessor.LongOpenAddressHashSet.Checked.Itr.root(itr));
                Assertions.assertEquals(expectedOffset,FieldAndMethodAccessor.LongOpenAddressHashSet.Checked.Itr.offset(itr));
                Assertions.assertEquals(expectedItrModCount,
                        FieldAndMethodAccessor.LongOpenAddressHashSet.Checked.Itr.modCount(itr));
                Assertions.assertEquals(expectedItrLastRet,
                        FieldAndMethodAccessor.LongOpenAddressHashSet.Checked.Itr.lastRet(itr));
                break;
            default:
                throw DataType.invalidDataType(dataType);
            }
        }
        @Override void updateItrNextFromTable(int expectedOffset){
            expectedItrLastRet=this.expectedOffset;
            super.updateItrNextFromTable(expectedOffset);
        }
        @Override void updateItrNextFromWords(int expectedOffset){
            expectedItrLastRet=this.expectedOffset;
            super.updateItrNextFromWords(expectedOffset);
        }
    }
    class UncheckedItrMonitor extends AbstractItrMonitor{
        UncheckedItrMonitor(OmniIterator<?> itr,int expectedOffset,int expectedNumLeft){
            super(itr,expectedOffset,expectedNumLeft);
        }
        @Override public void updateIteratorState(){
            switch(dataType){
            case CHAR:
                expectedOffset=FieldAndMethodAccessor.CharOpenAddressHashSet.Itr.offset(itr);
                break;
            case SHORT:
                expectedOffset=FieldAndMethodAccessor.ShortOpenAddressHashSet.Itr.offset(itr);
                break;
            case INT:
                expectedOffset=FieldAndMethodAccessor.IntOpenAddressHashSet.Itr.offset(itr);
                break;
            case LONG:
                expectedOffset=FieldAndMethodAccessor.LongOpenAddressHashSet.Itr.offset(itr);
                break;
            default:
                throw DataType.invalidDataType(dataType);
            }
        }
        @Override public void updateItrRemoveState(){
            --expectedSize;
            int expectedOffset;
            int bitIndex;
            switch(bitIndex=(expectedOffset=this.expectedOffset - 1) >> 6){
            default:
                if(expectedTableLength != 0){
                    if(expectedOffset == -1){
                        expectedOffset=expectedTableLength;
                    }else{
                        expectedOffset-=256;
                    }
                    switch(dataType){
                    case CHAR:{
                        final char[] table=(char[])expectedTable;
                        for(;;){
                            if((table[--expectedOffset] & -2) != 0){
                                table[expectedOffset]=1;
                                --expectedTableSize;
                                return;
                            }
                            if(expectedOffset == 0){
                                break;
                            }
                        }
                        break;
                    }
                    case SHORT:{
                        final short[] table=(short[])expectedTable;
                        for(;;){
                            if((table[--expectedOffset] & -2) != 0){
                                table[expectedOffset]=1;
                                --expectedTableSize;
                                return;
                            }
                            if(expectedOffset == 0){
                                break;
                            }
                        }
                        break;
                    }
                    case INT:{
                        final int[] table=(int[])expectedTable;
                        for(;;){
                            if((table[--expectedOffset] & -2) != 0){
                                table[expectedOffset]=1;
                                --expectedTableSize;
                                return;
                            }
                            if(expectedOffset == 0){
                                break;
                            }
                        }
                        break;
                    }
                    case LONG:{
                        final long[] table=(long[])expectedTable;
                        for(;;){
                            if((table[--expectedOffset] & -2) != 0){
                                table[expectedOffset]=1;
                                --expectedTableSize;
                                return;
                            }
                            if(expectedOffset == 0){
                                break;
                            }
                        }
                        break;
                    }
                    default:
                        throw DataType.invalidDataType(dataType);
                    }
                }else{
                    expectedOffset=0;
                }
                bitIndex=3;
            case 3:
            case 2:
            case 1:
            case 0:
                for(;;){
                    long word;
                    if((expectedOffset=Long.numberOfLeadingZeros((word=expectedWords[bitIndex]) << -expectedOffset)) != 64){
                        expectedWords[bitIndex]=word & ~(1L << -1 - expectedOffset);
                        return;
                    }
                    expectedOffset=0;
                    --bitIndex;
                }
            }
        }
        @Override public void verifyForEachRemaining(MonitoredFunction function){
            super.verifyForEachRemainingHelper(function,-1);
        }
        @Override public void verifyIteratorState(Object itr){
            switch(dataType){
            case CHAR:
                Assertions.assertSame(set,FieldAndMethodAccessor.CharOpenAddressHashSet.Itr.root(itr));
                Assertions.assertEquals(expectedOffset,FieldAndMethodAccessor.CharOpenAddressHashSet.Itr.offset(itr));
                break;
            case SHORT:
                Assertions.assertSame(set,FieldAndMethodAccessor.ShortOpenAddressHashSet.Itr.root(itr));
                Assertions.assertEquals(expectedOffset,FieldAndMethodAccessor.ShortOpenAddressHashSet.Itr.offset(itr));
                break;
            case INT:
                Assertions.assertSame(set,FieldAndMethodAccessor.IntOpenAddressHashSet.Itr.root(itr));
                Assertions.assertEquals(expectedOffset,FieldAndMethodAccessor.IntOpenAddressHashSet.Itr.offset(itr));
                break;
            case LONG:
                Assertions.assertSame(set,FieldAndMethodAccessor.LongOpenAddressHashSet.Itr.root(itr));
                Assertions.assertEquals(expectedOffset,FieldAndMethodAccessor.LongOpenAddressHashSet.Itr.offset(itr));
                break;
            default:
                throw DataType.invalidDataType(dataType);
            }
        }
    }


    private static void quickInsert(char[] table,char val){
        int tableLength;
        int hash;
        for(hash=val & (tableLength=table.length - 1);;){
            if(table[hash] == 0){
                table[hash]=val;
                return;
            }
            hash=hash + 1 & tableLength;
        }
    }
    private static void quickInsert(int[] table,int val){
        int tableLength;
        int hash;
        for(hash=val & (tableLength=table.length - 1);;){
            if(table[hash] == 0){
                table[hash]=val;
                return;
            }
            hash=hash + 1 & tableLength;
        }
    }
    private static void quickInsert(long[] table,long val){
        int tableLength;
        int hash;
        for(hash=(int)(val ^ val >>> 32) & (tableLength=table.length - 1);;){
            if(table[hash] == 0){
                table[hash]=val;
                return;
            }
            hash=hash + 1 & tableLength;
        }
    }
    private static void quickInsert(short[] table,short val){
        int tableLength;
        int hash;
        for(hash=val & (tableLength=table.length - 1);;){
            if(table[hash] == 0){
                table[hash]=val;
                return;
            }
            hash=hash + 1 & tableLength;
        }
    }
    final AbstractIntegralTypeOpenAddressHashSet<?> set;
    final CheckedType checkedType;
    final DataType dataType;
    final long[] expectedWords;
    int expectedSize;
    int expectedMaxTableSize;
    float expectedLoadFactor;
    int expectedTableSize;
    int expectedModCount;
    int expectedTableLength;
    Object expectedTable;
    IntegralOpenAddressHashSetMonitor(DataType dataType,CheckedType checkedType,int initialCapacity,float loadFactor){
        switch(dataType){
        case CHAR:
            if(checkedType.checked){
                this.set=new CharOpenAddressHashSet.Checked(initialCapacity,loadFactor);
            }else{
                this.set=new CharOpenAddressHashSet(initialCapacity,loadFactor);
            }
            break;
        case SHORT:
            if(checkedType.checked){
                this.set=new ShortOpenAddressHashSet.Checked(initialCapacity,loadFactor);
            }else{
                this.set=new ShortOpenAddressHashSet(initialCapacity,loadFactor);
            }
            break;
        case INT:
            if(checkedType.checked){
                this.set=new IntOpenAddressHashSet.Checked(initialCapacity,loadFactor);
            }else{
                this.set=new IntOpenAddressHashSet(initialCapacity,loadFactor);
            }
            break;
        case LONG:
            if(checkedType.checked){
                this.set=new LongOpenAddressHashSet.Checked(initialCapacity,loadFactor);
            }else{
                this.set=new LongOpenAddressHashSet(initialCapacity,loadFactor);
            }
            break;
        default:
            throw DataType.invalidDataType(dataType);
        }
        this.checkedType=checkedType;
        this.dataType=dataType;
        this.expectedWords=new long[4];
        updateCollectionState();
    }
    IntegralOpenAddressHashSetMonitor(DataType dataType,CheckedType checkedType,float loadFactor){
        switch(dataType){
        case CHAR:
            if(checkedType.checked){
                this.set=new CharOpenAddressHashSet.Checked(loadFactor);
            }else{
                this.set=new CharOpenAddressHashSet(loadFactor);
            }
            break;
        case SHORT:
            if(checkedType.checked){
                this.set=new ShortOpenAddressHashSet.Checked(loadFactor);
            }else{
                this.set=new ShortOpenAddressHashSet(loadFactor);
            }
            break;
        case INT:
            if(checkedType.checked){
                this.set=new IntOpenAddressHashSet.Checked(loadFactor);
            }else{
                this.set=new IntOpenAddressHashSet(loadFactor);
            }
            break;
        case LONG:
            if(checkedType.checked){
                this.set=new LongOpenAddressHashSet.Checked(loadFactor);
            }else{
                this.set=new LongOpenAddressHashSet(loadFactor);
            }
            break;
        default:
            throw DataType.invalidDataType(dataType);
        }
        this.checkedType=checkedType;
        this.dataType=dataType;
        this.expectedWords=new long[4];
        updateCollectionState();
    }
    IntegralOpenAddressHashSetMonitor(DataType dataType,CheckedType checkedType,int initialCapacity){
        switch(dataType){
        case CHAR:
            if(checkedType.checked){
                this.set=new CharOpenAddressHashSet.Checked(initialCapacity);
            }else{
                this.set=new CharOpenAddressHashSet(initialCapacity);
            }
            break;
        case SHORT:
            if(checkedType.checked){
                this.set=new ShortOpenAddressHashSet.Checked(initialCapacity);
            }else{
                this.set=new ShortOpenAddressHashSet(initialCapacity);
            }
            break;
        case INT:
            if(checkedType.checked){
                this.set=new IntOpenAddressHashSet.Checked(initialCapacity);
            }else{
                this.set=new IntOpenAddressHashSet(initialCapacity);
            }
            break;
        case LONG:
            if(checkedType.checked){
                this.set=new LongOpenAddressHashSet.Checked(initialCapacity);
            }else{
                this.set=new LongOpenAddressHashSet(initialCapacity);
            }
            break;
        default:
            throw DataType.invalidDataType(dataType);
        }
        this.checkedType=checkedType;
        this.dataType=dataType;
        this.expectedWords=new long[4];
        updateCollectionState();
    }
    IntegralOpenAddressHashSetMonitor(DataType dataType,CheckedType checkedType){
        switch(dataType){
        case CHAR:
            if(checkedType.checked){
                this.set=new CharOpenAddressHashSet.Checked();
            }else{
                this.set=new CharOpenAddressHashSet();
            }
            break;
        case SHORT:
            if(checkedType.checked){
                this.set=new ShortOpenAddressHashSet.Checked();
            }else{
                this.set=new ShortOpenAddressHashSet();
            }
            break;
        case INT:
            if(checkedType.checked){
                this.set=new IntOpenAddressHashSet.Checked();
            }else{
                this.set=new IntOpenAddressHashSet();
            }
            break;
        case LONG:
            if(checkedType.checked){
                this.set=new LongOpenAddressHashSet.Checked();
            }else{
                this.set=new LongOpenAddressHashSet();
            }
            break;
        default:
            throw DataType.invalidDataType(dataType);
        }
        this.checkedType=checkedType;
        this.dataType=dataType;
        this.expectedWords=new long[4];
        updateCollectionState();
    }
    IntegralOpenAddressHashSetMonitor(DataType dataType,CheckedType checkedType,int initialCapacity,float loadFactor,
            long...expectedWords){
        switch(dataType){
        case CHAR:
            if(checkedType.checked){
                this.set=new CharOpenAddressHashSet.Checked(initialCapacity,loadFactor,expectedWords[0],
                        expectedWords[1],expectedWords[2],expectedWords[3]);
            }else{
                this.set=new CharOpenAddressHashSet(initialCapacity,loadFactor,expectedWords[0],expectedWords[1],
                        expectedWords[2],expectedWords[3]);
            }
            break;
        case SHORT:
            if(checkedType.checked){
                this.set=new ShortOpenAddressHashSet.Checked(initialCapacity,loadFactor,expectedWords[0],
                        expectedWords[1],expectedWords[2],expectedWords[3]);
            }else{
                this.set=new ShortOpenAddressHashSet(initialCapacity,loadFactor,expectedWords[0],expectedWords[1],
                        expectedWords[2],expectedWords[3]);
            }
            break;
        case INT:
            if(checkedType.checked){
                this.set=new IntOpenAddressHashSet.Checked(initialCapacity,loadFactor,expectedWords[0],expectedWords[1],
                        expectedWords[2],expectedWords[3]);
            }else{
                this.set=new IntOpenAddressHashSet(initialCapacity,loadFactor,expectedWords[0],expectedWords[1],
                        expectedWords[2],expectedWords[3]);
            }
            break;
        case LONG:
            if(checkedType.checked){
                this.set=new LongOpenAddressHashSet.Checked(initialCapacity,loadFactor,expectedWords[0],
                        expectedWords[1],expectedWords[2],expectedWords[3]);
            }else{
                this.set=new LongOpenAddressHashSet(initialCapacity,loadFactor,expectedWords[0],expectedWords[1],
                        expectedWords[2],expectedWords[3]);
            }
            break;
        default:
            throw DataType.invalidDataType(dataType);
        }
        this.checkedType=checkedType;
        this.dataType=dataType;
        this.expectedWords=new long[4];
        updateCollectionState();
    }
    
    @Override
    public void verifyClear(){
        set.clear();
        if(expectedSize != 0){
            if(expectedTableSize != 0){
                switch(dataType){
                case CHAR:{
                    var table=(char[])expectedTable;
                    for(int i=table.length;--i >= 0;){
                        table[i]=0;
                    }
                    break;
                }
                case SHORT:{
                    var table=(short[])expectedTable;
                    for(int i=table.length;--i >= 0;){
                        table[i]=0;
                    }
                    break;
                }
                case INT:{
                    var table=(int[])expectedTable;
                    for(int i=table.length;--i >= 0;){
                        table[i]=0;
                    }
                    break;
                }
                case LONG:{
                    var table=(long[])expectedTable;
                    for(int i=table.length;--i >= 0;){
                        table[i]=0;
                    }
                    break;
                }
                default:
                    throw DataType.invalidDataType(dataType);
                }
                expectedTableSize=0;
            }
            for(int i=0;i < 4;++i){
                expectedWords[i]=0;
            }
            expectedSize=0;
            ++expectedModCount;
        }
        verifyCollectionState();
    }
    @Override public CheckedType getCheckedType(){
        return checkedType;
    }
    @Override public AbstractIntegralTypeOpenAddressHashSet<?> getCollection(){
        return set;
    }
    @Override public DataType getDataType(){
        return dataType;
    }
    @Override public MonitoredIterator<? extends OmniIterator<?>,AbstractIntegralTypeOpenAddressHashSet<?>>
    getMonitoredIterator(){
        int expectedOffset;
        int numLeft;
        if((numLeft=expectedSize) != 0){
            outer:for(;;){
                switch(dataType){
                case CHAR:{
                    for(expectedOffset=0;expectedOffset < 256;++expectedOffset){
                        if((expectedWords[expectedOffset >> 6] & 1L << expectedOffset) != 0){
                            break outer;
                        }
                    }
                    final char[] table=(char[])expectedTable;
                    for(expectedOffset=0;;++expectedOffset){
                        if(table[expectedOffset] > 1){
                            expectedOffset+=256;
                            break outer;
                        }
                    }
                }
                case SHORT:{
                    for(expectedOffset=-128;expectedOffset < 128;++expectedOffset){
                        if((expectedWords[(expectedOffset >> 6) + 2] & 1L << expectedOffset) != 0){
                            expectedOffset+=128;
                            break outer;
                        }
                    }
                    final short[] table=(short[])expectedTable;
                    for(expectedOffset=0;;++expectedOffset){
                        if((table[expectedOffset] & -2) != 0){
                            expectedOffset+=256;
                            break outer;
                        }
                    }
                }
                case INT:{
                    for(expectedOffset=-128;expectedOffset < 128;++expectedOffset){
                        if((expectedWords[(expectedOffset >> 6) + 2] & 1L << expectedOffset) != 0){
                            expectedOffset+=128;
                            break outer;
                        }
                    }
                    final int[] table=(int[])expectedTable;
                    for(expectedOffset=0;;++expectedOffset){
                        if((table[expectedOffset] & -2) != 0){
                            expectedOffset+=256;
                            break outer;
                        }
                    }
                }
                case LONG:{
                    for(expectedOffset=-128;expectedOffset < 128;++expectedOffset){
                        if((expectedWords[(expectedOffset >> 6) + 2] & 1L << expectedOffset) != 0){
                            expectedOffset+=128;
                            break outer;
                        }
                    }
                    final long[] table=(long[])expectedTable;
                    for(expectedOffset=0;;++expectedOffset){
                        if((table[expectedOffset] & -2) != 0){
                            expectedOffset+=256;
                            break outer;
                        }
                    }
                }
                default:
                    throw DataType.invalidDataType(dataType);
                }
            }
        }else{
            expectedOffset=-1;
        }
        final var itr=set.iterator();
        if(checkedType.checked){ return new CheckedItrMonitor(itr,expectedOffset,numLeft,expectedModCount); }
        return new UncheckedItrMonitor(itr,expectedOffset,numLeft);
    }
    @Override public StructType getStructType(){
        return StructType.IntegralOpenAddressHashSet;
    }
    @Override public boolean isEmpty(){
        return expectedSize == 0;
    }
    @Override public void modCollection(){
        outer:for(;;){
            switch(dataType){
            case CHAR:{
                final var cast=(CharOpenAddressHashSet)set;
                for(int i=0;i <= Character.MAX_VALUE;++i){
                    if(cast.add((char)i)){
                        break outer;
                    }
                }
                break;
            }
            case SHORT:{
                final var cast=(ShortOpenAddressHashSet)set;
                for(int i=-128;i <= Short.MAX_VALUE;++i){
                    if(cast.add((short)i)){
                        break outer;
                    }
                }
                for(int i=Short.MIN_VALUE;i != -128;++i){
                    if(cast.add((short)i)){
                        break outer;
                    }
                }
                break;
            }
            case INT:
            case LONG:{
                final var cast=(OmniSet.IntInput<?>)set;
                for(int i=-128;;++i){
                    if(cast.add(i)){
                        break outer;
                    }
                }
            }
            default:
                throw DataType.invalidDataType(dataType);
            }
            set.clear();
            break;
        }
    updateCollectionState();
    }
    @Override public void removeFromExpectedState(QueryVal queryVal,QueryValModification modification){
        final Object inputVal=queryVal.getInputVal(dataType,modification);
        switch(dataType){
        case CHAR:{
            removeVal((char)inputVal);
            break;
        }
        case INT:{
            removeVal((int)inputVal);
            break;
        }
        case LONG:{
            removeVal((long)inputVal);
            break;
        }
        case SHORT:{
            removeVal((short)inputVal);
            break;
        }
        default:
            throw DataType.invalidDataType(dataType);
        }
        ++expectedModCount;
    }
    @Override public int size(){
        return expectedSize;
    }
    @Override
    public void updateAddState(Object inputVal,DataType inputType,boolean result){
        if(result){
            switch(dataType){
            case CHAR:{
                char v;
                switch(inputType){
                case BOOLEAN:
                    v=(boolean)inputVal?(char)1:(char)0;
                    break;
                case CHAR:
                    v=(char)inputVal;
                    break;
                default:
                    throw DataType.invalidDataType(inputType);
                }
                if(v < 256){
                    expectedWords[v >> 6]|=1L << v;
                }else{
                    insertInTable(v);
                }
                break;
            }
            case SHORT:{
                short v;
                switch(inputType){
                case BOOLEAN:
                    v=(boolean)inputVal?(short)1:(short)0;
                    break;
                case BYTE:
                    v=(byte)inputVal;
                    break;
                case SHORT:
                    v=(short)inputVal;
                    break;
                default:
                    throw DataType.invalidDataType(inputType);
                }
                if(v<=Byte.MAX_VALUE && v>=Byte.MIN_VALUE){
                    expectedWords[(v >> 6) + 2]|=1L << v;
                }else{
                    insertInTable(v);
                }
                break;
            }
            case INT:{
                int v;
                switch(inputType){
                case BOOLEAN:
                    v=(boolean)inputVal?1:0;
                    break;
                case BYTE:
                    v=(byte)inputVal;
                    break;
                case SHORT:
                    v=(short)inputVal;
                    break;
                case CHAR:
                    v=(char)inputVal;
                    break;
                case INT:
                    v=(int)inputVal;
                    break;
                default:
                    throw DataType.invalidDataType(inputType);
                }
                if(v<=Byte.MAX_VALUE && v>=Byte.MIN_VALUE){
                    expectedWords[(v >> 6) + 2]|=1L << v;
                }else{
                    insertInTable(v);
                }
                break;
            }
            case LONG:{
                long v;
                switch(inputType){
                case BOOLEAN:
                    v=(boolean)inputVal?1L:0L;
                    break;
                case BYTE:
                    v=(byte)inputVal;
                    break;
                case SHORT:
                    v=(short)inputVal;
                    break;
                case CHAR:
                    v=(char)inputVal;
                    break;
                case INT:
                    v=(int)inputVal;
                    break;
                case LONG:
                    v=(long)inputVal;
                    break;
                default:
                    throw DataType.invalidDataType(inputType);
                }
                if(v<=Byte.MAX_VALUE && v>=Byte.MIN_VALUE){
                    int i;
                    expectedWords[((i=(int)v) >> 6) + 2]|=1L << i;
                }else{
                    insertInTable(v);
                    
                }
                break;
            }
            default:
                throw DataType.invalidDataType(dataType);
            }
            ++expectedModCount;
            ++expectedSize;
        }
    }
    @Override public void updateCollectionState(){
        AbstractIntegralTypeOpenAddressHashSet<?> set;
        expectedSize=(set=this.set).size;
        expectedLoadFactor=set.loadFactor;
        expectedMaxTableSize=set.maxTableSize;
        expectedTableSize=set.tableSize;
        long[] expectedWords;
        (expectedWords=this.expectedWords)[0]=set.word0;
        expectedWords[1]=set.word1;
        expectedWords[2]=set.word2;
        expectedWords[3]=set.word3;
        switch(dataType){
        case CHAR:{
            final var castSet=(CharOpenAddressHashSet)set;
            if(checkedType.checked){
                expectedModCount=((CharOpenAddressHashSet.Checked)castSet).modCount;
            }
            char[] table;
            if((table=castSet.table) == null){
                expectedTable=null;
                expectedTableLength=0;
            }else{
                int expectedTableLength,tableLength;
                if((expectedTableLength=this.expectedTableLength) != (tableLength=table.length)){
                    Object newExpectedTable;
                    System.arraycopy(table,0,newExpectedTable=new char[tableLength],0,tableLength);
                    expectedTable=newExpectedTable;
                    this.expectedTableLength=tableLength;
                }else{
                    System.arraycopy(table,0,expectedTable,0,expectedTableLength);
                }
            }
            break;
        }
        case INT:{
            final var castSet=(IntOpenAddressHashSet)set;
            if(checkedType.checked){
                expectedModCount=((IntOpenAddressHashSet.Checked)castSet).modCount;
            }
            int[] table;
            if((table=castSet.table) == null){
                expectedTable=null;
                expectedTableLength=0;
            }else{
                int expectedTableLength,tableLength;
                if((expectedTableLength=this.expectedTableLength) != (tableLength=table.length)){
                    Object newExpectedTable;
                    System.arraycopy(table,0,newExpectedTable=new int[tableLength],0,tableLength);
                    expectedTable=newExpectedTable;
                    this.expectedTableLength=tableLength;
                }else{
                    System.arraycopy(table,0,expectedTable,0,expectedTableLength);
                }
            }
            break;
        }
        case LONG:{
            final var castSet=(LongOpenAddressHashSet)set;
            if(checkedType.checked){
                expectedModCount=((LongOpenAddressHashSet.Checked)castSet).modCount;
            }
            long[] table;
            if((table=castSet.table) == null){
                expectedTable=null;
                expectedTableLength=0;
            }else{
                int expectedTableLength,tableLength;
                if((expectedTableLength=this.expectedTableLength) != (tableLength=table.length)){
                    Object newExpectedTable;
                    System.arraycopy(table,0,newExpectedTable=new long[tableLength],0,tableLength);
                    expectedTable=newExpectedTable;
                    this.expectedTableLength=tableLength;
                }else{
                    System.arraycopy(table,0,expectedTable,0,expectedTableLength);
                }
            }
            break;
        }
        case SHORT:{
            final var castSet=(ShortOpenAddressHashSet)set;
            if(checkedType.checked){
                expectedModCount=((ShortOpenAddressHashSet.Checked)castSet).modCount;
            }
            short[] table;
            if((table=castSet.table) == null){
                expectedTable=null;
                expectedTableLength=0;
            }else{
                int expectedTableLength,tableLength;
                if((expectedTableLength=this.expectedTableLength) != (tableLength=table.length)){
                    Object newExpectedTable;
                    System.arraycopy(table,0,newExpectedTable=new short[tableLength],0,tableLength);
                    expectedTable=newExpectedTable;
                    this.expectedTableLength=tableLength;
                }else{
                    System.arraycopy(table,0,expectedTable,0,expectedTableLength);
                }
            }
            break;
        }
        default:
            throw DataType.invalidDataType(dataType);
        }
    }
    @Override public void verifyArrayIsCopy(Object arr){
        switch(dataType){
        case CHAR:
            Assertions.assertNotSame(((CharOpenAddressHashSet)set).table,arr);
            break;
        case SHORT:
            Assertions.assertNotSame(((ShortOpenAddressHashSet)set).table,arr);
            break;
        case INT:
            Assertions.assertNotSame(((IntOpenAddressHashSet)set).table,arr);
            break;
        case LONG:
            Assertions.assertNotSame(((LongOpenAddressHashSet)set).table,arr);
            break;
        default:
            throw DataType.invalidDataType(dataType);
        }
    }
    @Override public void verifyClone(Object clone){
        AbstractIntegralTypeOpenAddressHashSet<?> cloneSet;
        Assertions.assertEquals(expectedSize,(cloneSet=(AbstractIntegralTypeOpenAddressHashSet<?>)clone).size);
        Assertions.assertEquals(expectedTableSize,cloneSet.tableSize);
        Assertions.assertEquals(expectedMaxTableSize,cloneSet.maxTableSize);
        Assertions.assertEquals(expectedLoadFactor,cloneSet.loadFactor);
        Assertions.assertEquals(expectedWords[0],cloneSet.word0);
        Assertions.assertEquals(expectedWords[1],cloneSet.word1);
        Assertions.assertEquals(expectedWords[2],cloneSet.word2);
        Assertions.assertEquals(expectedWords[3],cloneSet.word3);
        int expectedTableLength=this.expectedTableLength;
        switch(dataType){
        case CHAR:
            if(checkedType.checked){
                Assertions.assertEquals(0,((CharOpenAddressHashSet.Checked)cloneSet).modCount);
            }
            if(expectedTableLength == 0){
                Assertions.assertNull(((CharOpenAddressHashSet)cloneSet).table);
            }else{
                char[] expectedTable;
                Assertions.assertEquals((expectedTable=(char[])this.expectedTable).length,
                        ((CharOpenAddressHashSet)cloneSet).table.length);
                while(--expectedTableLength >= 0){
                    char v;
                    switch(v=expectedTable[expectedTableLength]){
                    default:
                        Assertions.assertTrue(cloneSet.contains(v));
                    case 0:
                    case 1:
                    }
                }
            }
            break;
        case INT:
            if(checkedType.checked){
                Assertions.assertEquals(0,((IntOpenAddressHashSet.Checked)cloneSet).modCount);
            }
            if(expectedTableLength == 0){
                Assertions.assertNull(((IntOpenAddressHashSet)cloneSet).table);
            }else{
                int[] expectedTable;
                Assertions.assertEquals((expectedTable=(int[])this.expectedTable).length,
                        ((IntOpenAddressHashSet)cloneSet).table.length);
                while(--expectedTableLength >= 0){
                    int v;
                    switch(v=expectedTable[expectedTableLength]){
                    default:
                        Assertions.assertTrue(cloneSet.contains(v));
                    case 0:
                    case 1:
                    }
                }
            }
            break;
        case LONG:
            if(checkedType.checked){
                Assertions.assertEquals(0,((LongOpenAddressHashSet.Checked)cloneSet).modCount);
            }
            if(expectedTableLength == 0){
                Assertions.assertNull(((LongOpenAddressHashSet)cloneSet).table);
            }else{
                long[] expectedTable;
                Assertions.assertEquals((expectedTable=(long[])this.expectedTable).length,
                        ((LongOpenAddressHashSet)cloneSet).table.length);
                while(--expectedTableLength >= 0){
                    long v;
                    if((v=expectedTable[expectedTableLength]) != 0 && v != 1){
                        Assertions.assertTrue(cloneSet.contains(v));
                    }
                }
            }
            break;
        case SHORT:
            if(checkedType.checked){
                Assertions.assertEquals(0,((ShortOpenAddressHashSet.Checked)cloneSet).modCount);
            }
            if(expectedTableLength == 0){
                Assertions.assertNull(((ShortOpenAddressHashSet)cloneSet).table);
            }else{
                short[] expectedTable;
                Assertions.assertEquals((expectedTable=(short[])this.expectedTable).length,
                        ((ShortOpenAddressHashSet)cloneSet).table.length);
                while(--expectedTableLength >= 0){
                    short v;
                    switch(v=expectedTable[expectedTableLength]){
                    default:
                        Assertions.assertTrue(cloneSet.contains(v));
                    case 0:
                    case 1:
                    }
                }
            }
            break;
        default:
            throw DataType.invalidDataType(dataType);
        }
        verifyStructuralIntegrity(cloneSet);
    }
    @Override public void verifyCollectionState(){
        AbstractIntegralTypeOpenAddressHashSet<?> set;
        Assertions.assertEquals(expectedSize,(set=this.set).size);
        Assertions.assertEquals(expectedMaxTableSize,set.maxTableSize);
        Assertions.assertEquals(expectedLoadFactor,set.loadFactor);
        Assertions.assertEquals(expectedTableSize,set.tableSize);
        long[] expectedWords;
        Assertions.assertEquals((expectedWords=this.expectedWords)[0],set.word0);
        Assertions.assertEquals(expectedWords[1],set.word1);
        Assertions.assertEquals(expectedWords[2],set.word2);
        Assertions.assertEquals(expectedWords[3],set.word3);
        switch(dataType){
        case CHAR:
            if(checkedType.checked){
                Assertions.assertEquals(expectedModCount,((CharOpenAddressHashSet.Checked)set).modCount);
            }
            if(expectedTableLength == 0){
                Assertions.assertNull(((CharOpenAddressHashSet)set).table);
            }else{
                Assertions.assertArrayEquals((char[])expectedTable,((CharOpenAddressHashSet)set).table);
            }
            break;
        case SHORT:
            if(checkedType.checked){
                Assertions.assertEquals(expectedModCount,((ShortOpenAddressHashSet.Checked)set).modCount);
            }
            if(expectedTableLength == 0){
                Assertions.assertNull(((ShortOpenAddressHashSet)set).table);
            }else{
                Assertions.assertArrayEquals((short[])expectedTable,((ShortOpenAddressHashSet)set).table);
            }
            break;
        case INT:
            if(checkedType.checked){
                Assertions.assertEquals(expectedModCount,((IntOpenAddressHashSet.Checked)set).modCount);
            }
            if(expectedTableLength == 0){
                Assertions.assertNull(((IntOpenAddressHashSet)set).table);
            }else{
                Assertions.assertArrayEquals((int[])expectedTable,((IntOpenAddressHashSet)set).table);
            }
            break;
        case LONG:
            if(checkedType.checked){
                Assertions.assertEquals(expectedModCount,((LongOpenAddressHashSet.Checked)set).modCount);
            }
            if(expectedTableLength == 0){
                Assertions.assertNull(((LongOpenAddressHashSet)set).table);
            }else{
                Assertions.assertArrayEquals((long[])expectedTable,((LongOpenAddressHashSet)set).table);
            }
            break;
        default:
            throw DataType.invalidDataType(dataType);
        }
        verifyStructuralIntegrity(set);
    }
    @Override public void verifyRemoveIf(boolean result,MonitoredRemoveIfPredicate filter){
        Assertions.assertEquals(expectedSize,filter.numCalls);
        Assertions.assertEquals(filter.numRemoved,filter.removedVals.size());
        Assertions.assertEquals(filter.numRetained,filter.retainedVals.size());
        if(result){
            ++expectedModCount;
            final var removedValsItr=filter.removedVals.iterator();
            Assertions.assertTrue(removedValsItr.hasNext());
            final var retainedValsItr=filter.retainedVals.iterator();
            switch(dataType){
            case CHAR:{
                do{
                    char v;
                    Assertions.assertFalse(set.contains(v=(char)removedValsItr.next()));
                    removeVal(v);
                }while(removedValsItr.hasNext());
                while(retainedValsItr.hasNext()){
                    Assertions.assertTrue(set.contains((char)retainedValsItr.next()));
                }
                break;
            }
            case SHORT:{
                do{
                    short v;
                    Assertions.assertFalse(set.contains(v=(short)removedValsItr.next()));
                    removeVal(v);
                }while(removedValsItr.hasNext());
                while(retainedValsItr.hasNext()){
                    Assertions.assertTrue(set.contains((short)retainedValsItr.next()));
                }
                break;
            }
            case INT:{
                do{
                    int v;
                    Assertions.assertFalse(set.contains(v=(int)removedValsItr.next()));
                    removeVal(v);
                }while(removedValsItr.hasNext());
                while(retainedValsItr.hasNext()){
                    Assertions.assertTrue(set.contains((int)retainedValsItr.next()));
                }
                break;
            }
            case LONG:{
                do{
                    long v;
                    Assertions.assertFalse(set.contains(v=(long)removedValsItr.next()));
                    removeVal(v);
                }while(removedValsItr.hasNext());
                while(retainedValsItr.hasNext()){
                    Assertions.assertTrue(set.contains((long)retainedValsItr.next()));
                }
                break;
            }
            default:
                throw DataType.invalidDataType(dataType);
            }
        }
    }
    private void insert(char[] table,int hash,char inputVal){
        int tableSize;
        if((tableSize=++expectedTableSize) >= expectedMaxTableSize){
            expectedMaxTableSize=(int)((hash=table.length << 1) * expectedLoadFactor);
            char[] newTable;
            expectedTable=newTable=new char[expectedTableLength=hash];
            for(int i=0;;++i){
                char tableVal;
                if((tableVal=table[i]) > 1){
                    quickInsert(newTable,tableVal);
                    if(--tableSize == 1){
                        quickInsert(newTable,inputVal);
                        return;
                    }
                }
            }
        }else{
            table[hash]=inputVal;
        }
    }
    private void insert(int[] table,int hash,int inputVal){
        int tableSize;
        if((tableSize=++expectedTableSize) >= expectedMaxTableSize){
            expectedMaxTableSize=(int)((hash=table.length << 1) * expectedLoadFactor);
            int[] newTable;
            expectedTable=newTable=new int[expectedTableLength=hash];
            for(int i=0;;++i){
                int tableVal;
                if(((tableVal=table[i]) & -2) != 0){
                    quickInsert(newTable,tableVal);
                    if(--tableSize == 1){
                        quickInsert(newTable,inputVal);
                        return;
                    }
                }
            }
        }else{
            table[hash]=inputVal;
        }
    }
    private void insert(long[] table,int hash,long inputVal){
        int tableSize;
        if((tableSize=++expectedTableSize) >= expectedMaxTableSize){
            expectedMaxTableSize=(int)((hash=table.length << 1) * expectedLoadFactor);
            long[] newTable;
            expectedTable=newTable=new long[expectedTableLength=hash];
            for(int i=0;;++i){
                long tableVal;
                if(((tableVal=table[i]) & -2) != 0){
                    quickInsert(newTable,tableVal);
                    if(--tableSize == 1){
                        quickInsert(newTable,inputVal);
                        return;
                    }
                }
            }
        }else{
            table[hash]=inputVal;
        }
    }
    private void insert(short[] table,int hash,short inputVal){
        int tableSize;
        if((tableSize=++expectedTableSize) >= expectedMaxTableSize){
            expectedMaxTableSize=(int)((hash=table.length << 1) * expectedLoadFactor);
            short[] newTable;
            expectedTable=newTable=new short[expectedTableLength=hash];
            for(int i=0;;++i){
                short tableVal;
                if(((tableVal=table[i]) & -2) != 0){
                    quickInsert(newTable,tableVal);
                    if(--tableSize == 1){
                        quickInsert(newTable,inputVal);
                        return;
                    }
                }
            }
        }else{
            table[hash]=inputVal;
        }
    }
    private void insertInTable(char v){
      char[] t;
      if((t=(char[])expectedTable)==null) {
        int expectedTableLength;
        expectedTable=t=new char[this.expectedTableLength=expectedTableLength=expectedMaxTableSize];
        t[v&expectedTableLength-1]=v;
        expectedMaxTableSize=(int)(expectedTableLength*expectedLoadFactor);
        expectedTableSize=1;
      }else {
        int hash;
        int insertHere=-1;
        int expectedTableLength;
        insertInTable:for(final int initialHash=hash=v&(expectedTableLength=this.expectedTableLength-1);;) {
          switch(t[hash]) {
          case 0:
            if(insertHere==-1) {
              insertHere=hash;
            }
            break insertInTable;
          case 1:
            insertHere=hash;
          default:
            if((hash=hash+1&(expectedTableLength))==initialHash) {
              break insertInTable;
            }
          }
        }
        insert(t,insertHere,v);
      }
    }
    private void insertInTable(int v){
      int[] t;
      if((t=(int[])expectedTable)==null) {
        int expectedTableLength;
        expectedTable=t=new int[this.expectedTableLength=expectedTableLength=expectedMaxTableSize];
        t[v&expectedTableLength-1]=v;
        expectedMaxTableSize=(int)(expectedTableLength*expectedLoadFactor);
        expectedTableSize=1;
      }else {
        int hash;
        int insertHere=-1;
        int expectedTableLength;
        insertInTable:for(final int initialHash=hash=v&(expectedTableLength=this.expectedTableLength-1);;) {
          switch(t[hash]) {
          case 0:
            if(insertHere==-1) {
              insertHere=hash;
            }
            break insertInTable;
          case 1:
            insertHere=hash;
          default:
            if((hash=hash+1&(expectedTableLength))==initialHash) {
              break insertInTable;
            }
          }
        }
        insert(t,insertHere,v);
      }
    }
    private void insertInTable(long v) {


      long[] t;
      if((t=(long[])expectedTable)==null) {
        int expectedTableLength;
        expectedTable=t=new long[this.expectedTableLength=expectedTableLength=expectedMaxTableSize];
        t[(int)(v ^ v >>> 32) & expectedTableLength - 1]=v;
        expectedMaxTableSize=(int)(expectedTableLength*expectedLoadFactor);
        expectedTableSize=1;
      }else {
        int hash;
        int insertHere=-1;
        int expectedTableLength;
        insertInTable:for(final int initialHash=hash=(int)(v ^ v >>> 32) & (expectedTableLength=this.expectedTableLength-1);;){
          long tableVal;
          if((tableVal=t[hash]) == 0L){
              if(insertHere == -1){
                  insertHere=hash;
              }
              break insertInTable;
          }else if(tableVal == 1L){
              insertHere=hash;
          }
          if((hash=hash + 1 & expectedTableLength) == initialHash){
              break insertInTable;
          }
        }
        insert(t,insertHere,v);
      }
    }
//    private void insertInTable(long v){
//      long[] t;
//      if((t=(long[])expectedTable)==null) {
//        int expectedTableLength;
//        expectedTable=t=new long[this.expectedTableLength=expectedTableLength=expectedMaxTableSize];
//        t[(int)(v ^ v >>> 32) & expectedTableLength - 1]=v;
//        expectedMaxTableSize=(int)(expectedTableLength*expectedLoadFactor);
//        expectedTableSize=1;
//      }else {
//        int hash;
//        int insertHere=-1;
//        int expectedTableLength;
//        insertInTable:for(final int initialHash=hash=(int)(v ^ v >>> 32) & (expectedTableLength=this.expectedTableLength-1);;){
//          long tableVal;
//          if((tableVal=t[hash]) == 0L){
//              if(insertHere == -1){
//                  insertHere=hash;
//              }
//              break insertInTable;
//          }else if(tableVal == 1L){
//              insertHere=hash;
//          }
//          if((hash=hash + 1 & expectedTableLength) == initialHash){
//              break insertInTable;
//          }
//        }
//        insert(t,insertHere,v);
//      }
//    }
    private void insertInTable(short v){
        short[] t;
        if((t=(short[])expectedTable)==null) {
          int expectedTableLength;
          expectedTable=t=new short[this.expectedTableLength=expectedTableLength=expectedMaxTableSize];
          t[v&expectedTableLength-1]=v;
          expectedMaxTableSize=(int)(expectedTableLength*expectedLoadFactor);
          expectedTableSize=1;
        }else {
          int hash;
          int insertHere=-1;
          int expectedTableLength;
          insertInTable:for(final int initialHash=hash=v&(expectedTableLength=this.expectedTableLength-1);;) {
            switch(t[hash]) {
            case 0:
              if(insertHere==-1) {
                insertHere=hash;
              }
              break insertInTable;
            case 1:
              insertHere=hash;
            default:
              if((hash=hash+1&(expectedTableLength))==initialHash) {
                break insertInTable;
              }
            }
          }
          insert(t,insertHere,v);
        }
    }
    private void removeVal(char v){
        if(v < 256){
            expectedWords[v >> 6]&=~(1L << v);
        }else{
            final char[] expectedTable=(char[])this.expectedTable;
            int expectedTableLength;
            int hash=v & (expectedTableLength=this.expectedTableLength - 1);
            for(;;){
                if(expectedTable[hash] == v){
                    expectedTable[hash]=1;
                    break;
                }
                hash=hash + 1 & expectedTableLength;
            }
            --expectedTableSize;
        }
        --expectedSize;
    }
    private void removeVal(int v){
        if((v & 0xffffff00) == 0){
            expectedWords[(v >> 6) + 2]&=~(1L << v);
        }else{
            final int[] expectedTable=(int[])this.expectedTable;
            int expectedTableLength;
            int hash=v & (expectedTableLength=this.expectedTableLength - 1);
            for(;;){
                if(expectedTable[hash] == v){
                    expectedTable[hash]=1;
                    break;
                }
                hash=hash + 1 & expectedTableLength;
            }
            --expectedTableSize;
        }
        --expectedSize;
    }
    private void removeVal(long v){
        if((v & 0xffffffffffffff00L) == 0){
            int i;
            expectedWords[((i=(int)v) >> 6) + 2]&=~(1L << i);
        }else{
            final long[] expectedTable=(long[])this.expectedTable;
            int expectedTableLength;
            int hash=(int)(v ^ v >>> 32) & (expectedTableLength=this.expectedTableLength - 1);
            for(;;){
                if(expectedTable[hash] == v){
                    expectedTable[hash]=1;
                    break;
                }
                hash=hash + 1 & expectedTableLength;
            }
            --expectedTableSize;
        }
        --expectedSize;
    }
    private void removeVal(short v){
        if((v & 0xffffff00) == 0){
            expectedWords[(v >> 6) + 2]&=~(1L << v);
        }else{
            final short[] expectedTable=(short[])this.expectedTable;
            int expectedTableLength;
            int hash=v & (expectedTableLength=this.expectedTableLength - 1);
            for(;;){
                if(expectedTable[hash] == v){
                    expectedTable[hash]=1;
                    break;
                }
                hash=hash + 1 & expectedTableLength;
            }
            --expectedTableSize;
        }
        --expectedSize;
    }
    private void verifyStructuralIntegrity(AbstractIntegralTypeOpenAddressHashSet<?> set){
        Assertions.assertEquals(Long.bitCount(set.word0) + Long.bitCount(set.word1) + Long.bitCount(set.word2)
        + Long.bitCount(set.word3) + set.tableSize,set.size);
        //Assertions.assertTrue(set.tableSize < set.maxTableSize);
       // final HashSet<Object> encounteredVals=new HashSet<>();
        int encounteredVals=0;
        switch(dataType){
        case CHAR:{
            char[] table;
            if((table=((CharOpenAddressHashSet)set).table) != null){
                int i=table.length;
                // check that it is a power of 2
                Assertions.assertEquals(1,i >>> Integer.numberOfTrailingZeros(i));
                Assertions.assertEquals((int)(i * set.loadFactor),set.maxTableSize);
                while(--i >= 0){
                    char v;
                    switch(v=table[i]){
                    case 0:
                    case 1:
                        break;
                    default:
                      ++encounteredVals;
                       // Assertions.assertTrue(encounteredVals.add(v));
                        Assertions.assertTrue(v >= 256);
                    }
                }
            }
            break;
        }
        case SHORT:{
            short[] table;
            if((table=((ShortOpenAddressHashSet)set).table) != null){
                int i=table.length;
                // check that it is a power of 2
                Assertions.assertEquals(1,i >>> Integer.numberOfTrailingZeros(i));
                Assertions.assertEquals((int)(i * set.loadFactor),set.maxTableSize);
                while(--i >= 0){
                    short v;
                    switch(v=table[i]){
                    case 0:
                    case 1:
                        break;
                    default:
                      ++encounteredVals;
                       // Assertions.assertTrue(encounteredVals.add(v));
                        Assertions.assertTrue(v < -128 || v > 127);
                    }
                }
            }
            break;
        }
        case INT:{
            int[] table;
            if((table=((IntOpenAddressHashSet)set).table) != null){
                int i=table.length;
                // check that it is a power of 2
                Assertions.assertEquals(1,i >>> Integer.numberOfTrailingZeros(i));
                Assertions.assertEquals((int)(i * set.loadFactor),set.maxTableSize);
                while(--i >= 0){
                    int v;
                    switch(v=table[i]){
                    case 0:
                    case 1:
                        break;
                    default:
                      ++encounteredVals;
                        //Assertions.assertTrue(encounteredVals.add(v));
                        Assertions.assertTrue(v < -128 || v > 127);
                    }
                }
            }
            break;
        }
        case LONG:{
            long[] table;
            if((table=((LongOpenAddressHashSet)set).table) != null){
                int i=table.length;
                // check that it is a power of 2
                Assertions.assertEquals(1,i >>> Integer.numberOfTrailingZeros(i));
                Assertions.assertEquals((int)(i * set.loadFactor),set.maxTableSize);
                while(--i >= 0){
                    long v;
                    if((v=table[i]) != 0 && v != 1){
                      ++encounteredVals;
                        //Assertions.assertTrue(encounteredVals.add(v));
                        Assertions.assertTrue(v < -128 || v > 127);
                    }
                }
            }
            break;
        }
        default:
            throw DataType.invalidDataType(dataType);
        }
        Assertions.assertEquals(encounteredVals,set.tableSize);
    }
    @Override
    public void writeObjectImpl(MonitoredObjectOutputStream oos) throws IOException{
        set.writeExternal(oos);
    }

}

    // private static final double[] RANDOM_THRESHOLDS=new
    // double[]{0.01,0.05,0.10,0.25,0.50,0.75,0.90,0.95,0.99};
    // private static final double[] NON_RANDOM_THRESHOLD=new double[]{0.5};
    private static final long[] WORDSTATES=new long[]{0x0000000000000000L,0x0000000000000001L,0x0000000000000002L,
            0x0000000000000004L,0x7fffffffffffffffL,0x8000000000000000L,0xfffffffffffffffeL,0xffffffffffffffffL,};
    private static final float[] LOAD_FACTORS=new float[7];
    private static final float DEFAULT_LOAD_FACTOR=0.75f;
    private static final int DEFAULT_INITIAL_CAPACITY=16;
    private static final int[] INITIAL_CAPACITIES=new int[5 + 29 * 3];
    private static final int[] ILLEGAL_INITIAL_CAPACITIES=new int[INITIAL_CAPACITIES.length + 2];
    private static final float[] ILLEGAL_LOAD_FACTORS=new float[LOAD_FACTORS.length + 7];
    static{
        INITIAL_CAPACITIES[0]=0;
        INITIAL_CAPACITIES[1]=1;
        INITIAL_CAPACITIES[2]=2;
        int i=2;
        for(int pow=2;pow <= 30;++pow){
            INITIAL_CAPACITIES[++i]=(1 << pow) - 1;
            INITIAL_CAPACITIES[++i]=1 << pow;
            INITIAL_CAPACITIES[++i]=(1 << pow) + 1;
        }
        INITIAL_CAPACITIES[++i]=OmniArray.MAX_ARR_SIZE;
        INITIAL_CAPACITIES[++i]=Integer.MAX_VALUE;
        System.arraycopy(INITIAL_CAPACITIES,0,ILLEGAL_INITIAL_CAPACITIES,0,INITIAL_CAPACITIES.length);
        ILLEGAL_INITIAL_CAPACITIES[++i]=-1;
        ILLEGAL_INITIAL_CAPACITIES[++i]=Integer.MIN_VALUE;
        LOAD_FACTORS[0]=0.1f;
        LOAD_FACTORS[1]=0.25f;
        LOAD_FACTORS[2]=0.5f;
        LOAD_FACTORS[3]=0.75f;
        LOAD_FACTORS[4]=0.9f;
        LOAD_FACTORS[5]=0.95f;
        LOAD_FACTORS[6]=1.0f;
        System.arraycopy(LOAD_FACTORS,0,ILLEGAL_LOAD_FACTORS,0,LOAD_FACTORS.length);
        ILLEGAL_LOAD_FACTORS[i=LOAD_FACTORS.length]=1.1f;
        ILLEGAL_LOAD_FACTORS[++i]=2.0f;
        ILLEGAL_LOAD_FACTORS[++i]=0.0f;
        ILLEGAL_LOAD_FACTORS[++i]=-1f;
        ILLEGAL_LOAD_FACTORS[++i]=-.75f;
        ILLEGAL_LOAD_FACTORS[++i]=-.5f;
        ILLEGAL_LOAD_FACTORS[++i]=Float.NaN;
    }
    @org.junit.jupiter.api.Test
    public void testConstructor_void(){
        for(var collectionType:StructType.IntegralOpenAddressHashSet.validDataTypes){
            for(var checkedType:CheckedType.values()){
                TestExecutorService.submitTest(
                        ()->new IntegralOpenAddressHashSetMonitor(collectionType,checkedType).verifyCollectionState());
            }
        }
        TestExecutorService.completeAllTests("IntegralOpenAddressHashSetTest.testConstructor_void");
    }
    private static void testConstructor_intHelper(int initialCapacity,DataType collectionType,CheckedType checkedType){
        if(initialCapacity >= 0){
            new IntegralOpenAddressHashSetMonitor(collectionType,checkedType,initialCapacity).verifyCollectionState();
        }else{
            Assertions.assertThrows(IllegalArgumentException.class,
                    ()->new IntegralOpenAddressHashSetMonitor(collectionType,checkedType,initialCapacity));
        }
    }
    @org.junit.jupiter.api.Test
    public void testConstructor_int(){
        for(var collectionType:StructType.IntegralOpenAddressHashSet.validDataTypes){
            for(var checkedType:CheckedType.values()){
                for(int initialCapacity:ILLEGAL_INITIAL_CAPACITIES){
                    if(checkedType.checked || initialCapacity >= 0){
                        TestExecutorService
                        .submitTest(()->testConstructor_intHelper(initialCapacity,collectionType,checkedType));
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("IntegralOpenAddressHashSetTest.testConstructor_int");
    }
    private static void testConstructor_floatHelper(float loadFactor,DataType collectionType,CheckedType checkedType){
        if(loadFactor == loadFactor && loadFactor <= 1.0f && loadFactor > 0){
            new IntegralOpenAddressHashSetMonitor(collectionType,checkedType,loadFactor).verifyCollectionState();
        }else{
            Assertions.assertThrows(IllegalArgumentException.class,
                    ()->new IntegralOpenAddressHashSetMonitor(collectionType,checkedType,loadFactor));
        }
    }
    @org.junit.jupiter.api.Test
    public void testConstructor_float(){
        for(var collectionType:StructType.IntegralOpenAddressHashSet.validDataTypes){
            for(var checkedType:CheckedType.values()){
                for(float loadFactor:ILLEGAL_LOAD_FACTORS){
                    if(checkedType.checked || loadFactor == loadFactor && loadFactor <= 1.0f && loadFactor > 0){
                        TestExecutorService
                        .submitTest(()->testConstructor_floatHelper(loadFactor,collectionType,checkedType));
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("IntegralOpenAddressHashSetTest.testConstructor_float");
    }
    private static void testConstructor_intfloatHelper(int initialCapacity,float loadFactor,DataType collectionType,
            CheckedType checkedType){
        if(initialCapacity >= 0 && loadFactor == loadFactor && loadFactor <= 1.0f && loadFactor > 0){
            new IntegralOpenAddressHashSetMonitor(collectionType,checkedType,initialCapacity,loadFactor)
            .verifyCollectionState();
        }else{
            Assertions.assertThrows(IllegalArgumentException.class,
                    ()->new IntegralOpenAddressHashSetMonitor(collectionType,checkedType,initialCapacity,loadFactor));
        }
    }
    @org.junit.jupiter.api.Test
    public void testConstructor_intfloat(){
        for(var collectionType:StructType.IntegralOpenAddressHashSet.validDataTypes){
            for(var checkedType:CheckedType.values()){
                for(int initialCapacity:ILLEGAL_INITIAL_CAPACITIES){
                    if(checkedType.checked || initialCapacity >= 0){
                        for(float loadFactor:ILLEGAL_LOAD_FACTORS){
                            if(checkedType.checked || loadFactor == loadFactor && loadFactor <= 1.0f && loadFactor > 0){
                                TestExecutorService.submitTest(()->testConstructor_intfloatHelper(initialCapacity,
                                        loadFactor,collectionType,checkedType));
                            }
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("IntegralOpenAddressHashSetTest.testConstructor_intfloat");
    }
    private static enum TableInitializationSequence{
        Empty{
            @Override
            IntegralOpenAddressHashSetMonitor initialize(IntegralOpenAddressHashSetMonitor monitor){
                // nothing to do
                return monitor;
            }
        },
        AddPrime{
            @Override
            IntegralOpenAddressHashSetMonitor initialize(IntegralOpenAddressHashSetMonitor monitor){
                monitor.add(1231);
                return monitor;
            }
        },
        AddFibSeq{
            @Override
            IntegralOpenAddressHashSetMonitor initialize(IntegralOpenAddressHashSetMonitor monitor){
                DataType dataType=monitor.getDataType();
                switch(dataType){
                case CHAR:{
                    var set=(CharOpenAddressHashSet)monitor.set;
                    set.add((char)0);
                    set.add((char)1);
                    set.add((char)2);
                    long pPrev=1;
                    long prev=2;
                    for(int i=3;i < 100;++i){
                        long curr=pPrev + prev;
                        set.add((char)curr);
                        pPrev=prev;
                        prev=curr;
                    }
                    break;
                }
                case SHORT:{
                    var set=(ShortOpenAddressHashSet)monitor.set;
                    set.add((short)0);
                    set.add((short)1);
                    set.add((short)2);
                    long pPrev=1;
                    long prev=2;
                    for(int i=3;i < 100;++i){
                        long curr=pPrev + prev;
                        set.add((short)curr);
                        pPrev=prev;
                        prev=curr;
                    }
                    break;
                }
                case INT:{
                    var set=(IntOpenAddressHashSet)monitor.set;
                    set.add(0);
                    set.add(1);
                    set.add(2);
                    long pPrev=1;
                    long prev=2;
                    for(int i=3;i < 100;++i){
                        long curr=pPrev + prev;
                        set.add((int)curr);
                        pPrev=prev;
                        prev=curr;
                    }
                    break;
                }
                case LONG:{
                    var set=(LongOpenAddressHashSet)monitor.set;
                    set.add((long)0);
                    set.add((long)1);
                    set.add((long)2);
                    long pPrev=1;
                    long prev=2;
                    for(int i=3;i < 100;++i){
                        long curr=pPrev + prev;
                        set.add(curr);
                        pPrev=prev;
                        prev=curr;
                    }
                    break;
                }
                default:
                    throw DataType.invalidDataType(dataType);
                }
                monitor.updateCollectionState();
                return monitor;
            }
        };
        abstract IntegralOpenAddressHashSetMonitor initialize(IntegralOpenAddressHashSetMonitor monitor);
    }
    @org.junit.jupiter.api.Test
    public void testiterator_void(){
        for(var collectionType:StructType.IntegralOpenAddressHashSet.validDataTypes){
            for(var checkedType:CheckedType.values()){
                for(var word0:WORDSTATES){
                    for(var word1:WORDSTATES){
                        for(var word2:WORDSTATES){
                            for(var word3:WORDSTATES){
                                for(var tableInitSeq:TableInitializationSequence.values()){
                                    TestExecutorService.submitTest(()->
                                    tableInitSeq
                                    .initialize(new IntegralOpenAddressHashSetMonitor(collectionType,
                                            checkedType,DEFAULT_INITIAL_CAPACITY,DEFAULT_LOAD_FACTOR,word0,
                                            word1,word2,word3))
                                    .getMonitoredIterator().verifyIteratorState()
                                            )
                                    ;
                                }
                            }
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("IntegralOpenAddressHashSetTest.testiterator_void");
    }
    private static void testItrnext_voidHelper(IllegalModification preMod,DataType outputType,DataType collectionType,
            CheckedType checkedType,TableInitializationSequence tableInitSeq,long...expectedWords){
        var setMonitor=tableInitSeq.initialize(new IntegralOpenAddressHashSetMonitor(collectionType,checkedType,
                DEFAULT_INITIAL_CAPACITY,DEFAULT_LOAD_FACTOR,expectedWords));
        var itrMonitor=setMonitor.getMonitoredIterator();
        itrMonitor.illegalMod(preMod);
        if(preMod.expectedException == null){
            while(itrMonitor.hasNext()){
                itrMonitor.verifyNext(outputType);
            }
            Assertions.assertFalse(itrMonitor.getIterator().hasNext());
            if(checkedType.checked){
                Assertions.assertThrows(NoSuchElementException.class,()->itrMonitor.verifyNext(outputType));
            }
        }else{
            Assertions.assertThrows(preMod.expectedException,()->itrMonitor.verifyNext(outputType));
        }
        itrMonitor.verifyIteratorState();
        setMonitor.verifyCollectionState();
    }
    @org.junit.jupiter.api.Test
    public void testItrnext_void(){
        for(var collectionType:StructType.IntegralOpenAddressHashSet.validDataTypes){
            for(var outputType:collectionType.validOutputTypes()){
                for(var checkedType:CheckedType.values()){
                    for(final var preMod:IteratorType.AscendingItr.validPreMods){
                        if(checkedType.checked || preMod.expectedException == null){
                            for(var word0:WORDSTATES){
                                for(var word1:WORDSTATES){
                                    for(var word2:WORDSTATES){
                                        for(var word3:WORDSTATES){
                                            for(var tableInitSeq:TableInitializationSequence.values()){
                                                TestExecutorService.submitTest(()->
                                                testItrnext_voidHelper(preMod,outputType,collectionType,checkedType,
                                                        tableInitSeq,word0,word1,word2,word3)
                                                        )
                                                ;
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
        TestExecutorService.completeAllTests("IntegralOpenAddressHashSetTest.testItrnext_void");
    }
        private static int getSize(long...words){
            int size=0;
            for(final var word:words){
                size+=Long.bitCount(word);
            }
            return size;
        }
        private static void testItrforEachRemaining_ConsumerHelper(int itrScenario,IllegalModification preMod,
                MonitoredFunctionGen functionGen,FunctionCallType functionCallType,long randSeed,DataType collectionType,
                CheckedType checkedType,TableInitializationSequence tableInitSeq,long...expectedWords){
            var setMonitor=tableInitSeq.initialize(new IntegralOpenAddressHashSetMonitor(collectionType,checkedType,
                    DEFAULT_INITIAL_CAPACITY,DEFAULT_LOAD_FACTOR,expectedWords));
            final var itrMonitor=setMonitor.getMonitoredIterator();
            switch(itrScenario){
            case 1:
                itrMonitor.iterateForward();
                break;
            case 2:
                itrMonitor.iterateForward();
                itrMonitor.remove();
            default:
            }
            final int numLeft=itrMonitor.getNumLeft();
            itrMonitor.illegalMod(preMod);
            final Class<? extends Throwable> expectedException=numLeft == 0?null
                    :preMod.expectedException == null?functionGen.expectedException:preMod.expectedException;
            if(expectedException == null){
                itrMonitor.verifyForEachRemaining(functionGen,functionCallType,randSeed);
            }else{
                Assertions.assertThrows(expectedException,
                        ()->itrMonitor.verifyForEachRemaining(functionGen,functionCallType,randSeed));
                itrMonitor.verifyIteratorState();
                setMonitor.verifyCollectionState();
            }
        }
    @org.junit.jupiter.api.Test
    public void testadd_val(){
        final float[] loadFactors=new float[]{0.25f,0.5f,0.75f,0.9f,1.0f};
        final int[] initialCapacities=new int[]{0,1,2,4,8,16,32,64,128,256,512,1024,2048,4096,8192};
        for(var collectionType:StructType.IntegralOpenAddressHashSet.validDataTypes){
            for(final var inputType:collectionType.mayBeAddedTo()){
                for(final var checkedType:CheckedType.values()){
                    for(float loadFactor:loadFactors){
                        for(int initialCapacity:initialCapacities){
                            //for(final long word0:WORDSTATES)
                            {
                                //for(final long word1:WORDSTATES)
                                {
                                    //for(final long word2:WORDSTATES)
                                    {
                                        //for(final long word3:WORDSTATES)
                                        {
                                            //for(final var tableInitSeq:TableInitializationSequence.values())
                                          {
                                                for(final var functionCallType:FunctionCallType.values()){
                                                    TestExecutorService.submitTest(()->
                                                    testadd_valHelper(collectionType,
                                                            inputType,checkedType,loadFactor,initialCapacity,functionCallType)
                                                    )
                                                    ;
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
        TestExecutorService.completeAllTests("IntegralOpenAddressHashSetTest.testadd_val");
    }
        @org.junit.jupiter.api.Test
        public void testItrforEachRemaining_Consumer(){
            for(var collectionType:StructType.IntegralOpenAddressHashSet.validDataTypes){
                for(final var checkedType:CheckedType.values()){
                    for(var word0:WORDSTATES){
                        for(var word1:WORDSTATES){
                            for(var word2:WORDSTATES){
                                for(var word3:WORDSTATES){
                                    for(var tableInitSeq:TableInitializationSequence.values()){
                                        int wordSize=getSize(word0,word1,word2,word3);
                                        int sizeScenario;
                                        outer:for(;;){
                                            switch(tableInitSeq){
                                            case Empty:
                                                sizeScenario=Math.min(2,wordSize);
                                                break outer;
                                            case AddPrime:
                                                sizeScenario=wordSize == 0?1:2;
                                                break outer;
                                            case AddFibSeq:
                                                sizeScenario=2;
                                                break outer;
                                            }
                                            throw new UnsupportedOperationException("Unknown tableInitSeq " + tableInitSeq);
                                        }
                                        for(final var functionGen:IteratorType.AscendingItr.validMonitoredFunctionGens){
                                            if(checkedType.checked || functionGen.expectedException == null
                                                    || sizeScenario == 0){
                                                for(final var preMod:IteratorType.AscendingItr.validPreMods){
                                                    if(checkedType.checked || preMod.expectedException == null
                                                            || sizeScenario == 0){
                                                        int itrScenarioMax=0;
                                                        if(sizeScenario > 1){
                                                            itrScenarioMax=2;
                                                        }
                                                        IntStream.rangeClosed(0,itrScenarioMax).forEach(itrScenario->{
                                                            LongStream.rangeClosed(0,
                                                                    preMod.expectedException == null
                                                                    && functionGen.randomized && sizeScenario > 1
                                                                    && itrScenario == 0?100:0)
                                                            .forEach(randSeed->{
                                                                for(final var functionCallType:FunctionCallType
                                                                        .values()){
                                                                            TestExecutorService.submitTest(()->
                                                                    testItrforEachRemaining_ConsumerHelper(
                                                                            itrScenario,preMod,functionGen,
                                                                            functionCallType,randSeed,
                                                                            collectionType,checkedType,
                                                                            tableInitSeq,word0,word1,word2,
                                                                            word3)
                                                                    )
                                                                    ;
                                                                }
                                                            });
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
            TestExecutorService.completeAllTests("IntegralOpenAddressHashSetTest.testItrforEachRemaining_Consumer");
        }
    private static void testadd_valHelper(DataType collectionType,DataType inputType,CheckedType checkedType,
            float loadFactor,int initialCapacity,
            FunctionCallType functionCallType){
        var monitor=new IntegralOpenAddressHashSetMonitor(collectionType,checkedType,
                initialCapacity,loadFactor);
        long pPrev=0;
        long prev=1;
        for(int i=0;i < 10000;++i){
            long curr=pPrev + prev;
            boolean alreadyContains;
            Object inputVal=inputType.convertValUnchecked(curr);
            switch(collectionType){
            case CHAR:
            {
              var set=(CharOpenAddressHashSet)monitor.set;
              switch(inputType) {
              case BOOLEAN:
                alreadyContains=set.contains((boolean)inputVal);
                break;
              case CHAR:
                alreadyContains=set.contains((char)inputVal);
                break;
              default:
                throw DataType.invalidDataType(inputType);
              }
              break;
            }
            case SHORT:
            {
              var set=(ShortOpenAddressHashSet)monitor.set;
              switch(inputType) {
              case BOOLEAN:
                alreadyContains=set.contains((boolean)inputVal);
                break;
              case BYTE:
                alreadyContains=set.contains((byte)inputVal);
                break;
              case SHORT:
                alreadyContains=set.contains((short)inputVal);
                break;
              default:
                throw DataType.invalidDataType(inputType);
              }
              break;
            }
            case INT:
            {
              var set=(IntOpenAddressHashSet)monitor.set;
              switch(inputType) {
              case BOOLEAN:
                alreadyContains=set.contains((boolean)inputVal);
                break;
              case BYTE:
                alreadyContains=set.contains((byte)inputVal);
                break;
              case CHAR:
                alreadyContains=set.contains((char)inputVal);
                break;
              case SHORT:
                alreadyContains=set.contains((short)inputVal);
                break;
              case INT:
                alreadyContains=set.contains((int)inputVal);
                break;
              default:
                throw DataType.invalidDataType(inputType);
              }
              break;
            }
            case LONG:
            {
              var set=(LongOpenAddressHashSet)monitor.set;
              switch(inputType) {
              case BOOLEAN:
                alreadyContains=set.contains((boolean)inputVal);
                break;
              case BYTE:
                alreadyContains=set.contains((byte)inputVal);
                break;
              case CHAR:
                alreadyContains=set.contains((char)inputVal);
                break;
              case SHORT:
                alreadyContains=set.contains((short)inputVal);
                break;
              case INT:
                alreadyContains=set.contains((int)inputVal);
                break;
              case LONG:
                alreadyContains=set.contains((long)inputVal);
                break;
              default:
                throw DataType.invalidDataType(inputType);
              }
              break;
            }
            default:
                throw DataType.invalidDataType(collectionType);
            }
            Assertions.assertNotEquals(alreadyContains,monitor.verifyAdd(inputVal,inputType,functionCallType));
            pPrev=prev;
            prev=curr;
        }
    }
}
