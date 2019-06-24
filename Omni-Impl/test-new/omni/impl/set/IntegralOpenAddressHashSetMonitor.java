package omni.impl.set;

import java.util.HashSet;
import org.junit.jupiter.api.Assertions;
import omni.api.OmniIterator;
import omni.api.OmniSet;
import omni.impl.CheckedType;
import omni.impl.DataType;
import omni.impl.MonitoredCollection;
import omni.impl.MonitoredFunction;
import omni.impl.MonitoredRemoveIfPredicate;
import omni.impl.MonitoredSet;
import omni.impl.QueryVal;
import omni.impl.QueryVal.QueryValModification;
import omni.impl.StructType;
public class IntegralOpenAddressHashSetMonitor implements MonitoredSet<AbstractIntegralTypeOpenAddressHashSet<?>>{
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
    IntegralOpenAddressHashSetMonitor(AbstractIntegralTypeOpenAddressHashSet<?> set,CheckedType checkedType,
            DataType dataType){
        this.set=set;
        this.checkedType=checkedType;
        this.dataType=dataType;
        this.expectedWords=new long[4];
        updateCollectionState();
    }

    private static UnsupportedOperationException getUOE(DataType dataType){
        return new UnsupportedOperationException("Unknown dataType " + dataType);
    }
    @Override
    public CheckedType getCheckedType(){
        return checkedType;
    }
    @Override
    public AbstractIntegralTypeOpenAddressHashSet<?> getCollection(){
        return set;
    }
    @Override
    public DataType getDataType(){
        return dataType;
    }
    @Override
    public MonitoredIterator<? extends OmniIterator<?>,AbstractIntegralTypeOpenAddressHashSet<?>> getMonitoredIterator(){
        int expectedOffset;
        int numLeft=expectedSize;
        if((numLeft=expectedSize) != 0){
            outer:for(;;){
                int bitIndex=0;
                while(bitIndex < 4){
                    if((expectedOffset=Long.numberOfTrailingZeros(expectedWords[bitIndex])) != 64){
                        expectedOffset+=bitIndex << 6;
                        break outer;
                    }
                    ++bitIndex;
                }
                switch(dataType){
                case CHAR:{
                    char[] table=(char[])expectedTable;
                    for(expectedOffset=0;;++expectedOffset){
                        if(table[expectedOffset] > 1){
                            expectedOffset+=256;
                            break outer;
                        }
                    }
                }
                case SHORT:{
                    short[] table=(short[])expectedTable;
                    for(expectedOffset=0;;++expectedOffset){
                        if((table[expectedOffset] & -2) != 0){
                            expectedOffset+=256;
                            break outer;
                        }
                    }
                }
                case INT:{
                    int[] table=(int[])expectedTable;
                    for(expectedOffset=0;;++expectedOffset){
                        if((table[expectedOffset] & -2) != 0){
                            expectedOffset+=256;
                            break outer;
                        }
                    }
                }
                case LONG:{
                    long[] table=(long[])expectedTable;
                    for(expectedOffset=0;;++expectedOffset){
                        if((table[expectedOffset] & -2) != 0){
                            expectedOffset+=256;
                            break outer;
                        }
                    }
                }
                default:
                    throw getUOE(dataType);
                }
            }
        }else{
            expectedOffset=-1;
        }
        var itr=set.iterator();
        if(checkedType.checked){
            return new CheckedItrMonitor(itr,expectedOffset,numLeft,expectedModCount);
        }
        return new UncheckedItrMonitor(itr,expectedOffset,numLeft);
    }
    @Override
    public StructType getStructType(){
        return StructType.IntegralOpenAddressHashSet;
    }
    @Override
    public boolean isEmpty(){
        return expectedSize == 0;
    }
    @Override
    public void modCollection(){
        outer:for(;;){
            switch(this.dataType){
            case CHAR:{
                var cast=(CharOpenAddressHashSet)this.set;
                for(int i=0;i <= Character.MAX_VALUE;++i){
                    if(cast.add((char)i)){
                        break outer;
                    }
                }
                break;
            }
            case SHORT:{
                var cast=(ShortOpenAddressHashSet)this.set;
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
                var cast=(OmniSet.IntInput<?>)this.set;
                for(int i=-128;i <= Integer.MAX_VALUE;++i){
                    if(cast.add(i)){
                        break outer;
                    }
                }
                break;
            }
            default:
                throw getUOE(dataType);
            }
            this.set.clear();
            break;
        }
    updateCollectionState();
    }
    @Override
    public int size(){
        return expectedSize;
    }
    @Override
    public void updateCollectionState(){
        AbstractIntegralTypeOpenAddressHashSet<?> set;
        this.expectedSize=(set=this.set).size;
        this.expectedLoadFactor=set.loadFactor;
        this.expectedMaxTableSize=set.maxTableSize;
        this.expectedTableSize=set.tableSize;
        long[] expectedWords;
        (expectedWords=this.expectedWords)[0]=set.word0;
        expectedWords[1]=set.word1;
        expectedWords[2]=set.word2;
        expectedWords[3]=set.word3;
        switch(dataType){
        case CHAR:{
            var castSet=(CharOpenAddressHashSet)set;
            if(checkedType.checked){
                this.expectedModCount=((CharOpenAddressHashSet.Checked)castSet).modCount;
            }
            char[] table;
            if((table=castSet.table) == null){
                this.expectedTable=null;
                this.expectedTableLength=0;
            }else{
                int expectedTableLength,tableLength;
                if((expectedTableLength=this.expectedTableLength) != (tableLength=table.length)){
                    Object newExpectedTable;
                    System.arraycopy(table,0,newExpectedTable=new char[tableLength],0,expectedTableLength);
                    this.expectedTable=newExpectedTable;
                }else{
                    System.arraycopy(table,0,this.expectedTable,0,expectedTableLength);
                }
            }
            break;
        }
        case INT:{
            var castSet=(IntOpenAddressHashSet)set;
            if(checkedType.checked){
                this.expectedModCount=((IntOpenAddressHashSet.Checked)castSet).modCount;
            }
            int[] table;
            if((table=castSet.table) == null){
                this.expectedTable=null;
                this.expectedTableLength=0;
            }else{
                int expectedTableLength,tableLength;
                if((expectedTableLength=this.expectedTableLength) != (tableLength=table.length)){
                    Object newExpectedTable;
                    System.arraycopy(table,0,newExpectedTable=new int[tableLength],0,expectedTableLength);
                    this.expectedTable=newExpectedTable;
                }else{
                    System.arraycopy(table,0,this.expectedTable,0,expectedTableLength);
                }
            }
            break;
        }
        case LONG:{
            var castSet=(LongOpenAddressHashSet)set;
            if(checkedType.checked){
                this.expectedModCount=((LongOpenAddressHashSet.Checked)castSet).modCount;
            }
            long[] table;
            if((table=castSet.table) == null){
                this.expectedTable=null;
                this.expectedTableLength=0;
            }else{
                int expectedTableLength,tableLength;
                if((expectedTableLength=this.expectedTableLength) != (tableLength=table.length)){
                    Object newExpectedTable;
                    System.arraycopy(table,0,newExpectedTable=new long[tableLength],0,expectedTableLength);
                    this.expectedTable=newExpectedTable;
                }else{
                    System.arraycopy(table,0,this.expectedTable,0,expectedTableLength);
                }
            }
            break;
        }
        case SHORT:{
            var castSet=(ShortOpenAddressHashSet)set;
            if(checkedType.checked){
                this.expectedModCount=((ShortOpenAddressHashSet.Checked)castSet).modCount;
            }
            short[] table;
            if((table=castSet.table) == null){
                this.expectedTable=null;
                this.expectedTableLength=0;
            }else{
                int expectedTableLength,tableLength;
                if((expectedTableLength=this.expectedTableLength) != (tableLength=table.length)){
                    Object newExpectedTable;
                    System.arraycopy(table,0,newExpectedTable=new short[tableLength],0,expectedTableLength);
                    this.expectedTable=newExpectedTable;
                }else{
                    System.arraycopy(table,0,this.expectedTable,0,expectedTableLength);
                }
            }
            break;
        }
        default:
            throw getUOE(dataType);
        }
    }
    private void verifyStructuralIntegrity(AbstractIntegralTypeOpenAddressHashSet<?> set){
        Assertions.assertEquals(Long.bitCount(set.word0) + Long.bitCount(set.word1) + Long.bitCount(set.word2)
        + Long.bitCount(set.word3) + set.tableSize,set.size);
        Assertions.assertTrue(set.tableSize < set.maxTableSize);
        HashSet<Object> encounteredVals=new HashSet<>();
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
                        Assertions.assertTrue(encounteredVals.add(v));
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
                        Assertions.assertTrue(encounteredVals.add(v));
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
                        Assertions.assertTrue(encounteredVals.add(v));
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
                        Assertions.assertTrue(encounteredVals.add(v));
                        Assertions.assertTrue(v < -128 || v > 127);
                    }
                }
            }
            break;
        }
        default:
            throw getUOE(dataType);
        }
        Assertions.assertEquals(encounteredVals.size(),set.tableSize);
    }

    @Override
    public void verifyCollectionState(){
        AbstractIntegralTypeOpenAddressHashSet<?> set;
        Assertions.assertEquals(expectedSize,(set=this.set).size);
        Assertions.assertEquals(this.expectedMaxTableSize,set.maxTableSize);
        Assertions.assertEquals(this.expectedLoadFactor,set.loadFactor);
        Assertions.assertEquals(this.expectedTableSize,set.tableSize);
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
                Assertions.assertArrayEquals((char[])this.expectedTable,((CharOpenAddressHashSet)set).table);
            }
            break;
        case SHORT:
            if(checkedType.checked){
                Assertions.assertEquals(expectedModCount,((ShortOpenAddressHashSet.Checked)set).modCount);
            }
            if(expectedTableLength == 0){
                Assertions.assertNull(((ShortOpenAddressHashSet)set).table);
            }else{
                Assertions.assertArrayEquals((short[])this.expectedTable,((ShortOpenAddressHashSet)set).table);
            }
            break;
        case INT:
            if(checkedType.checked){
                Assertions.assertEquals(expectedModCount,((IntOpenAddressHashSet.Checked)set).modCount);
            }
            if(expectedTableLength == 0){
                Assertions.assertNull(((IntOpenAddressHashSet)set).table);
            }else{
                Assertions.assertArrayEquals((int[])this.expectedTable,((IntOpenAddressHashSet)set).table);
            }
            break;
        case LONG:
            if(checkedType.checked){
                Assertions.assertEquals(expectedModCount,((LongOpenAddressHashSet.Checked)set).modCount);
            }
            if(expectedTableLength == 0){
                Assertions.assertNull(((LongOpenAddressHashSet)set).table);
            }else{
                Assertions.assertArrayEquals((long[])this.expectedTable,((LongOpenAddressHashSet)set).table);
            }
            break;
        default:
            throw getUOE(dataType);
        }
        verifyStructuralIntegrity(set);
    }
    @Override
    public void verifyClone(Object clone){
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
            throw getUOE(dataType);
        }
        verifyStructuralIntegrity(cloneSet);
    }

    abstract class AbstractItrMonitor
            implements
            MonitoredSet.MonitoredSetIterator<OmniIterator<?>,AbstractIntegralTypeOpenAddressHashSet<?>>{
        final OmniIterator<?> itr;
        int expectedOffset;
        int expectedNumLeft;
        AbstractItrMonitor(OmniIterator<?> itr,int expectedOffset,int expectedNumLeft){
            this.itr=itr;
            this.expectedOffset=expectedOffset;
            this.expectedNumLeft=expectedNumLeft;
        }
        @Override
        public OmniIterator<?> getIterator(){
            return itr;
        }
        @Override
        public MonitoredCollection<AbstractIntegralTypeOpenAddressHashSet<?>> getMonitoredCollection(){
            return IntegralOpenAddressHashSetMonitor.this;
        }
        @Override
        public boolean hasNext(){
            return this.expectedOffset != -1;
        }
        @Override
        public int getNumLeft(){
            return this.expectedNumLeft;
        }
        @Override
        public void updateItrNextState(){
            int expectedOffset;
            if((expectedOffset=this.expectedOffset) < 256){
                updateItrNextFromWords(expectedOffset);
            }else{
                updateItrNextFromTable(expectedOffset - 256);
            }
        }
        void updateItrNextFromWords(int expectedOffset){
            switch(dataType){
            case CHAR:{
                int bitIndex=++expectedOffset >> 6;
            if(bitIndex < 4){
                do{
                    if((expectedOffset=Long
                                .numberOfTrailingZeros(expectedWords[bitIndex] >>> expectedOffset)) != 64){
                        this.expectedOffset=expectedOffset + (bitIndex << 6);
                        return;
                    }
                    expectedOffset=0;
                }while(++bitIndex < 4);
            }else{
                expectedOffset-=256;
            }
                if(expectedTableSize != 0){
                char[] table=(char[])expectedTable;
                for(;;++expectedOffset){
                    if((table[expectedOffset] & -2) != 0){
                        this.expectedOffset=expectedOffset + 256;
                        return;
                    }
                }
            }else{
                this.expectedOffset=-1;
            }
            break;
            }
            case SHORT:{
                int bitIndex=(++expectedOffset >> 6) + 2;
                if(bitIndex < 4){
                    do{
                        if((expectedOffset=Long
                                .numberOfTrailingZeros(expectedWords[bitIndex] >>> expectedOffset)) != 64){
                            this.expectedOffset=expectedOffset + (bitIndex << 6);
                            return;
                        }
                        expectedOffset=0;
                    }while(++bitIndex < 4);
                }else{
                    expectedOffset-=128;
                }
                if(expectedTableSize != 0){
                    short[] table=(short[])expectedTable;
                    for(;;++expectedOffset){
                        if((table[expectedOffset] & -2) != 0){
                            this.expectedOffset=expectedOffset + 256;
                            return;
                        }
                    }
                }else{
                    this.expectedOffset=-1;
                }
                break;
            }
            case INT:{
                int bitIndex=(++expectedOffset >> 6) + 2;
                if(bitIndex < 4){
                    do{
                        if((expectedOffset=Long
                                .numberOfTrailingZeros(expectedWords[bitIndex] >>> expectedOffset)) != 64){
                            this.expectedOffset=expectedOffset + (bitIndex << 6);
                            return;
                        }
                        expectedOffset=0;
                    }while(++bitIndex < 4);
                }else{
                    expectedOffset-=128;
                }
                if(expectedTableSize != 0){
                    int[] table=(int[])expectedTable;
                    for(;;++expectedOffset){
                        if((table[expectedOffset] & -2) != 0){
                            this.expectedOffset=expectedOffset + 256;
                            return;
                        }
                    }
                }else{
                    this.expectedOffset=-1;
                }
                break;
            }
            case LONG:{
                int bitIndex=(++expectedOffset >> 6) + 2;
                if(bitIndex < 4){
                    do{
                        if((expectedOffset=Long
                                .numberOfTrailingZeros(expectedWords[bitIndex] >>> expectedOffset)) != 64){
                            this.expectedOffset=expectedOffset + (bitIndex << 6);
                            return;
                        }
                        expectedOffset=0;
                    }while(++bitIndex < 4);
                }else{
                    expectedOffset-=128;
                }
                if(expectedTableSize != 0){
                    long[] table=(long[])expectedTable;
                    for(;;++expectedOffset){
                        if((table[expectedOffset] & -2) != 0){
                            this.expectedOffset=expectedOffset + 256;
                            return;
                        }
                    }
                }else{
                    this.expectedOffset=-1;
                }
                break;
            }
            default:
                throw getUOE(dataType);
            }
        }
        void updateItrNextFromTable(int expectedOffset){
            switch(dataType){
            case CHAR:{
                char[] table;
                for(final int tableLength=(table=(char[])expectedTable).length;;){
                    if((table[expectedOffset] & -2) != 0){
                        this.expectedOffset=expectedOffset + 256;
                        break;
                    }
                    if(++expectedOffset == tableLength){
                        this.expectedOffset=-1;
                        break;
                    }
                }
                break;
            }
            case SHORT:{
                short[] table;
                for(final int tableLength=(table=(short[])expectedTable).length;;){
                    if((table[expectedOffset] & -2) != 0){
                        this.expectedOffset=expectedOffset + 256;
                        break;
                    }
                    if(++expectedOffset == tableLength){
                        this.expectedOffset=-1;
                        break;
                    }
                }
                break;
            }
            case INT:{
                int[] table;
                for(final int tableLength=(table=(int[])expectedTable).length;;){
                    if((table[expectedOffset] & -2) != 0){
                        this.expectedOffset=expectedOffset + 256;
                        break;
                    }
                    if(++expectedOffset == tableLength){
                        this.expectedOffset=-1;
                        break;
                    }
                }
                break;
            }
            case LONG:{
                long[] table;
                for(final int tableLength=(table=(long[])expectedTable).length;;){
                    if((table[expectedOffset] & -2) != 0){
                        this.expectedOffset=expectedOffset + 256;
                        break;
                    }
                    if(++expectedOffset == tableLength){
                        this.expectedOffset=-1;
                        break;
                    }
                }
                break;
            }
            default:
                throw getUOE(dataType);
            }
        }
    }
    class CheckedItrMonitor extends AbstractItrMonitor{
        int expectedItrModCount;
        int expectedItrLastRet;
        CheckedItrMonitor(OmniIterator<?> itr,int expectedOffset,int expectedNumLeft,int expectedItrModCount){
            super(itr,expectedOffset,expectedNumLeft);
            this.expectedItrModCount=expectedItrModCount;
            this.expectedItrLastRet=-1;
        }
        @Override
        public void verifyIteratorState(Object itr){
            switch(dataType){
            case CHAR:
                Assertions.assertSame(set,FieldAndMethodAccessor.CharOpenAddressHashSet.Checked.Itr.root(itr));
                Assertions.assertEquals(expectedOffset,
                        FieldAndMethodAccessor.CharOpenAddressHashSet.Checked.Itr.offset(itr));
                Assertions.assertEquals(expectedItrModCount,
                        FieldAndMethodAccessor.CharOpenAddressHashSet.Checked.Itr.modCount(itr));
                Assertions.assertEquals(expectedItrLastRet,
                        FieldAndMethodAccessor.CharOpenAddressHashSet.Checked.Itr.lastRet(itr));
                break;
            case SHORT:
                Assertions.assertSame(set,FieldAndMethodAccessor.ShortOpenAddressHashSet.Checked.Itr.root(itr));
                Assertions.assertEquals(expectedOffset,
                        FieldAndMethodAccessor.ShortOpenAddressHashSet.Checked.Itr.offset(itr));
                Assertions.assertEquals(expectedItrModCount,
                        FieldAndMethodAccessor.ShortOpenAddressHashSet.Checked.Itr.modCount(itr));
                Assertions.assertEquals(expectedItrLastRet,
                        FieldAndMethodAccessor.ShortOpenAddressHashSet.Checked.Itr.lastRet(itr));
                break;
            case INT:
                Assertions.assertSame(set,FieldAndMethodAccessor.IntOpenAddressHashSet.Checked.Itr.root(itr));
                Assertions.assertEquals(expectedOffset,
                        FieldAndMethodAccessor.IntOpenAddressHashSet.Checked.Itr.offset(itr));
                Assertions.assertEquals(expectedItrModCount,
                        FieldAndMethodAccessor.IntOpenAddressHashSet.Checked.Itr.modCount(itr));
                Assertions.assertEquals(expectedItrLastRet,
                        FieldAndMethodAccessor.IntOpenAddressHashSet.Checked.Itr.lastRet(itr));
                break;
            case LONG:
                Assertions.assertSame(set,FieldAndMethodAccessor.LongOpenAddressHashSet.Checked.Itr.root(itr));
                Assertions.assertEquals(expectedOffset,
                        FieldAndMethodAccessor.LongOpenAddressHashSet.Checked.Itr.offset(itr));
                Assertions.assertEquals(expectedItrModCount,
                        FieldAndMethodAccessor.LongOpenAddressHashSet.Checked.Itr.modCount(itr));
                Assertions.assertEquals(expectedItrLastRet,
                        FieldAndMethodAccessor.LongOpenAddressHashSet.Checked.Itr.lastRet(itr));
                break;
            default:
                throw getUOE(dataType);
            }
        }
        @Override
        void updateItrNextFromWords(int expectedOffset){
            this.expectedItrLastRet=this.expectedOffset;
            super.updateItrNextFromWords(expectedOffset);
        }
        @Override
        void updateItrNextFromTable(int expectedOffset){
            this.expectedItrLastRet=this.expectedOffset;
            super.updateItrNextFromTable(expectedOffset);
        }
        @Override
        public void updateItrRemoveState(){
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
                    throw getUOE(dataType);
                }
                --expectedTableSize;
            }
            this.expectedItrLastRet=-1;
        }
        @Override
        public void verifyForEachRemaining(MonitoredFunction function){
            // TODO Auto-generated method stub
        }
        @Override
        public void updateIteratorState(){
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
                throw getUOE(dataType);
            }
        }
    }
    class UncheckedItrMonitor extends AbstractItrMonitor{
        UncheckedItrMonitor(OmniIterator<?> itr,int expectedOffset,int expectedNumLeft){
            super(itr,expectedOffset,expectedNumLeft);
        }
        @Override
        public void verifyIteratorState(Object itr){
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
                throw getUOE(dataType);
            }
        }
        @Override
        public void updateItrRemoveState(){
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
                        char[] table=(char[])expectedTable;
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
                        short[] table=(short[])expectedTable;
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
                        int[] table=(int[])expectedTable;
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
                        long[] table=(long[])expectedTable;
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
                        throw getUOE(dataType);
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
                    if((expectedOffset=Long
                            .numberOfLeadingZeros((word=expectedWords[bitIndex]) << -expectedOffset)) != 64){
                        expectedWords[bitIndex]=word & ~(1L << -1 - expectedOffset);
                        return;
                    }
                    expectedOffset=0;
                    --bitIndex;
                }
            }
        }
        @Override
        public void verifyForEachRemaining(MonitoredFunction function){
            // TODO Auto-generated method stub
        }
        @Override
        public void updateIteratorState(){
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
                throw getUOE(dataType);
            }
        }
    }
    @Override
    public void verifyRemoveIf(boolean result,MonitoredRemoveIfPredicate filter){
        Assertions.assertEquals(expectedSize,filter.numCalls);
        Assertions.assertEquals(filter.numRemoved,filter.removedVals.size());
        Assertions.assertEquals(filter.numRetained,filter.retainedVals.size());
        if(result){
            ++expectedModCount;
            var removedValsItr=filter.removedVals.iterator();
            Assertions.assertTrue(removedValsItr.hasNext());
            var retainedValsItr=filter.retainedVals.iterator();
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
                throw getUOE(dataType);
            }
        }
    }
    @Override
    public void verifyArrayIsCopy(Object arr){
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
            throw getUOE(dataType);
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
    private void insert(long[] table,int hash,long inputVal){
        int tableSize;
        if((tableSize=++this.expectedTableSize) >= this.expectedMaxTableSize){
            expectedMaxTableSize=(int)((hash=table.length << 1) * expectedLoadFactor);
            long[] newTable;
            this.expectedTable=newTable=new long[hash];
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
    private void insert(char[] table,int hash,char inputVal){
        int tableSize;
        if((tableSize=++this.expectedTableSize) >= expectedMaxTableSize){
            expectedMaxTableSize=(int)((hash=table.length << 1) * expectedLoadFactor);
            char[] newTable;
            this.expectedTable=newTable=new char[hash];
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
    private void insert(short[] table,int hash,short inputVal){
        int tableSize;
        if((tableSize=++this.expectedTableSize) >= expectedMaxTableSize){
            expectedMaxTableSize=(int)((hash=table.length << 1) * expectedLoadFactor);
            short[] newTable;
            this.expectedTable=newTable=new short[hash];
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
    private void insert(int[] table,int hash,int inputVal){
        int tableSize;
        if((tableSize=++this.expectedTableSize) >= expectedMaxTableSize){
            expectedMaxTableSize=(int)((hash=table.length << 1) * expectedLoadFactor);
            int[] newTable;
            this.expectedTable=newTable=new int[hash];
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
    private void insertInTable(char v){
        char[] t;
        int expectedTableLength;
        if((expectedTableLength=this.expectedTableLength) == 0){
            this.expectedTable=t=new char[this.expectedTableLength=expectedTableLength=expectedMaxTableSize];
            t[v & expectedTableLength - 1]=v;
            this.expectedMaxTableSize=(int)(expectedTableLength * expectedLoadFactor);
            this.expectedTableSize=1;
        }else{
            int hash;
            t=(char[])this.expectedTable;
            int insertHere=-1;
            insertInTable:for(final int initialHash=hash=v & --expectedTableLength;;){
                switch(t[hash]){
                case 0:
                    if(insertHere == -1){
                        insertHere=hash;
                    }
                    break insertInTable;
                case 1:
                    insertHere=hash;
                default:
                    if((hash=hash + 1 & expectedTableLength) == initialHash){
                        break insertInTable;
                    }
                }
            }
            insert(t,insertHere,v);
        }
    }
    private void insertInTable(short v){
        short[] t;
        int expectedTableLength;
        if((expectedTableLength=this.expectedTableLength) == 0){
            this.expectedTable=t=new short[this.expectedTableLength=expectedTableLength=expectedMaxTableSize];
            t[v & expectedTableLength - 1]=v;
            this.expectedMaxTableSize=(int)(expectedTableLength * expectedLoadFactor);
            this.expectedTableSize=1;
        }else{
            int hash;
            t=(short[])this.expectedTable;
            int insertHere=-1;
            insertInTable:for(final int initialHash=hash=v & --expectedTableLength;;){
                switch(t[hash]){
                case 0:
                    if(insertHere == -1){
                        insertHere=hash;
                    }
                    break insertInTable;
                case 1:
                    insertHere=hash;
                default:
                    if((hash=hash + 1 & expectedTableLength) == initialHash){
                        break insertInTable;
                    }
                }
            }
            insert(t,insertHere,v);
        }
        ++this.expectedTableSize;
    }
    private void insertInTable(int v){
        int[] t;
        int expectedTableLength;
        if((expectedTableLength=this.expectedTableLength) == 0){
            this.expectedTable=t=new int[this.expectedTableLength=expectedTableLength=expectedMaxTableSize];
            t[v & expectedTableLength - 1]=v;
            this.expectedMaxTableSize=(int)(expectedTableLength * expectedLoadFactor);
            this.expectedTableSize=1;
        }else{
            int hash;
            t=(int[])this.expectedTable;
            int insertHere=-1;
            insertInTable:for(final int initialHash=hash=v & --expectedTableLength;;){
                switch(t[hash]){
                case 0:
                    if(insertHere == -1){
                        insertHere=hash;
                    }
                    break insertInTable;
                case 1:
                    insertHere=hash;
                default:
                    if((hash=hash + 1 & expectedTableLength) == initialHash){
                        break insertInTable;
                    }
                }
            }
            insert(t,insertHere,v);
        }
        ++this.expectedTableSize;
    }
    private void insertInTable(long v){
        long[] t;
        int expectedTableLength;
        if((expectedTableLength=this.expectedTableLength) == 0){
            this.expectedTable=t=new long[this.expectedTableLength=expectedTableLength=expectedMaxTableSize];
            t[(int)(v ^ v >>> 32) & expectedTableLength - 1]=v;
            this.expectedMaxTableSize=(int)(expectedTableLength * expectedLoadFactor);
            this.expectedTableSize=1;
        }else{
            int hash;
            t=(long[])this.expectedTable;
            int insertHere=-1;
            insertInTable:for(final int initialHash=hash=(int)(v ^ v >>> 32) & --expectedTableLength;;){
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
        ++this.expectedTableSize;
    }

    @Override
    public void updateAddState(Object inputVal,DataType inputType,boolean boxed,boolean result){
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
                    throw getUOE(inputType);
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
                    throw getUOE(inputType);
                }
                if((v & 0xffffff00) == 0){
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
                    throw getUOE(inputType);
                }
                if((v & 0xffffff00) == 0){
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
                    throw getUOE(inputType);
                }
                if((v & 0xffffffffffffff00L) == 0){
                    int i;
                    expectedWords[((i=(int)v) >> 6) + 2]|=1L << i;
                }else{
                    insertInTable(v);
                }
                break;
            }
            default:
                throw getUOE(dataType);
            }
            ++expectedModCount;
            ++expectedSize;
        }
    }
    private void removeVal(short v){
        if((v & 0xffffff00) == 0){
            expectedWords[(v >> 6) + 2]&=~(1L << v);
        }else{
            short[] expectedTable=(short[])this.expectedTable;
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
            int[] expectedTable=(int[])this.expectedTable;
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
    private void removeVal(char v){
        if(v < 256){
            expectedWords[v >> 6]&=~(1L << v);
        }else{
            char[] expectedTable=(char[])this.expectedTable;
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
            long[] expectedTable=(long[])this.expectedTable;
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
    @Override
    public void removeFromExpectedState(QueryVal queryVal,QueryValModification modification){
        Object inputVal=queryVal.getInputVal(dataType,modification);
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
            throw getUOE(dataType);
        }
        ++expectedModCount;
    }
}
