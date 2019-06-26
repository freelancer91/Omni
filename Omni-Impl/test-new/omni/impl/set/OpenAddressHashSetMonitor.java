package omni.impl.set;
import java.io.IOException;
import java.util.HashSet;
import org.junit.jupiter.api.Assertions;
import omni.api.OmniIterator;
import omni.impl.CheckedType;
import omni.impl.DataType;
import omni.impl.MonitoredObjectOutputStream;
import omni.impl.MonitoredRemoveIfPredicate;
import omni.impl.MonitoredSet;
import omni.impl.QueryVal;
import omni.impl.QueryVal.QueryValModification;
import omni.impl.StructType;
public class OpenAddressHashSetMonitor implements MonitoredSet<AbstractOpenAddressHashSet<?>>{

    final AbstractOpenAddressHashSet<?> set;
    final CheckedType checkedType;
    final DataType dataType;
    int expectedSize;
    int expectedMaxTableSize;
    float expectedLoadFactor;
    int expectedModCount;
    int expectedTableLength;
    Object expectedTable;
    OpenAddressHashSetMonitor(AbstractOpenAddressHashSet<?> set,CheckedType checkedType,DataType dataType){
        this.set=set;
        this.checkedType=checkedType;
        this.dataType=dataType;
        updateCollectionState();
    }
    @Override
    public void verifyClear(){
        set.clear();
        if(expectedSize != 0){
            switch(dataType){
            case FLOAT:{
                var table=(int[])expectedTable;
                for(int i=table.length;--i >= 0;){
                    table[i]=0;
                }
                break;
            }
            case DOUBLE:{
                var table=(long[])expectedTable;
                for(int i=table.length;--i >= 0;){
                    table[i]=0;
                }
                break;
            }
            case REF:{
                var table=(Object[])expectedTable;
                for(int i=table.length;--i >= 0;){
                    table[i]=null;
                }
                break;
            }
            default:
                throw DataType.invalidDataType(dataType);
            }
            expectedSize=0;
            ++expectedModCount;
        }
        verifyCollectionState();
    }
    @Override public CheckedType getCheckedType(){
        return checkedType;
    }
    @Override public AbstractOpenAddressHashSet<?> getCollection(){
        return set;
    }
    @Override public DataType getDataType(){
        return dataType;
    }
    @Override public MonitoredIterator<? extends OmniIterator<?>,AbstractOpenAddressHashSet<?>> getMonitoredIterator(){
        // TODO Auto-generated method stub
        return null;
    }
    @Override public StructType getStructType(){
        return StructType.OpenAddressHashSet;
    }
    @Override public boolean isEmpty(){
        return expectedSize == 0;
    }
    @SuppressWarnings("unchecked") @Override public void modCollection(){
        switch(dataType){
        case FLOAT:{
            final var cast=(FloatOpenAddressHashSet)set;
            for(int i=-128;;++i){
                if(cast.add(i)){
                    break;
                }
            }
            break;
        }
        case DOUBLE:{
            final var cast=(DoubleOpenAddressHashSet)set;
            for(int i=-128;;++i){
                if(cast.add(i)){
                    break;
                }
            }
            break;
        }
        case REF:{
            @SuppressWarnings("rawtypes") final var cast=(RefOpenAddressHashSet)set;
            for(int i=-128;;++i){
                if(cast.add(i)){
                    break;
                }
            }
            break;
        }
        default:
            throw DataType.invalidDataType(dataType);
        }
        updateCollectionState();
    }
    @Override public void removeFromExpectedState(QueryVal queryVal,QueryValModification modification){
        // TODO Auto-generated method stub
    }
    @Override public int size(){
        return expectedSize;
    }
    @Override
    public void updateAddState(Object inputVal,DataType inputType,boolean result){
        // TODO Auto-generated method stub
    }
    @SuppressWarnings("rawtypes") @Override public void updateCollectionState(){
        AbstractOpenAddressHashSet<?> set;
        expectedSize=(set=this.set).size;
        expectedLoadFactor=set.loadFactor;
        expectedMaxTableSize=set.maxTableSize;
        switch(dataType){
        case FLOAT:{
            final var castSet=(FloatOpenAddressHashSet)set;
            if(checkedType.checked){
                expectedModCount=((FloatOpenAddressHashSet.Checked)castSet).modCount;
            }
            int[] table;
            if((table=castSet.table) == null){
                expectedTable=null;
                expectedTableLength=0;
            }else{
                int expectedTableLength,tableLength;
                if((expectedTableLength=this.expectedTableLength) != (tableLength=table.length)){
                    Object newExpectedTable;
                    System.arraycopy(table,0,newExpectedTable=new int[tableLength],0,expectedTableLength);
                    expectedTable=newExpectedTable;
                }else{
                    System.arraycopy(table,0,expectedTable,0,expectedTableLength);
                }
            }
            break;
        }
        case DOUBLE:{
            final var castSet=(DoubleOpenAddressHashSet)set;
            if(checkedType.checked){
                expectedModCount=((DoubleOpenAddressHashSet.Checked)castSet).modCount;
            }
            long[] table;
            if((table=castSet.table) == null){
                expectedTable=null;
                expectedTableLength=0;
            }else{
                int expectedTableLength,tableLength;
                if((expectedTableLength=this.expectedTableLength) != (tableLength=table.length)){
                    Object newExpectedTable;
                    System.arraycopy(table,0,newExpectedTable=new long[tableLength],0,expectedTableLength);
                    expectedTable=newExpectedTable;
                }else{
                    System.arraycopy(table,0,expectedTable,0,expectedTableLength);
                }
            }
            break;
        }
        case REF:{
            final var castSet=(RefOpenAddressHashSet)set;
            if(checkedType.checked){
                expectedModCount=((RefOpenAddressHashSet.Checked)castSet).modCount;
            }
            Object[] table;
            if((table=castSet.table) == null){
                expectedTable=null;
                expectedTableLength=0;
            }else{
                int expectedTableLength,tableLength;
                if((expectedTableLength=this.expectedTableLength) != (tableLength=table.length)){
                    Object newExpectedTable;
                    System.arraycopy(table,0,newExpectedTable=new Object[tableLength],0,expectedTableLength);
                    expectedTable=newExpectedTable;
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
        Assertions.assertNotSame(arr,expectedTable);
    }
    @Override public void verifyClone(Object clone){
        // TODO Auto-generated method stub
    }
    @SuppressWarnings("rawtypes") @Override public void verifyCollectionState(){
        AbstractOpenAddressHashSet<?> set;
        Assertions.assertEquals(expectedSize,(set=this.set).size);
        Assertions.assertEquals(expectedMaxTableSize,set.maxTableSize);
        Assertions.assertEquals(expectedLoadFactor,set.loadFactor);
        switch(dataType){
        case FLOAT:
            if(checkedType.checked){
                Assertions.assertEquals(expectedModCount,((IntOpenAddressHashSet.Checked)set).modCount);
            }
            if(expectedTableLength == 0){
                Assertions.assertNull(((IntOpenAddressHashSet)set).table);
            }else{
                Assertions.assertArrayEquals((int[])expectedTable,((IntOpenAddressHashSet)set).table);
            }
            break;
        case DOUBLE:
            if(checkedType.checked){
                Assertions.assertEquals(expectedModCount,((DoubleOpenAddressHashSet.Checked)set).modCount);
            }
            if(expectedTableLength == 0){
                Assertions.assertNull(((DoubleOpenAddressHashSet)set).table);
            }else{
                Assertions.assertArrayEquals((long[])expectedTable,((DoubleOpenAddressHashSet)set).table);
            }
            break;
        case REF:
            if(checkedType.checked){
                Assertions.assertEquals(expectedModCount,((RefOpenAddressHashSet.Checked)set).modCount);
            }
            if(expectedTableLength == 0){
                Assertions.assertNull(((RefOpenAddressHashSet)set).table);
            }else{
                Assertions.assertArrayEquals((Object[])expectedTable,((RefOpenAddressHashSet)set).table);
            }
            break;
        default:
            throw DataType.invalidDataType(dataType);
        }
        verifyStructuralIntegrity(set);
    }
    @Override public void verifyRemoveIf(boolean result,MonitoredRemoveIfPredicate filter){
        // TODO Auto-generated method stub
    }
    @SuppressWarnings("rawtypes") private void verifyStructuralIntegrity(AbstractOpenAddressHashSet<?> set){
        Assertions.assertTrue(set.size < set.maxTableSize);
        final HashSet<Object> encounteredVals=new HashSet<>();
        switch(dataType){
        case FLOAT:{
            int[] table;
            if((table=((FloatOpenAddressHashSet)set).table) != null){
                int i=table.length;
                // check that it is a power of 2
                Assertions.assertEquals(1,i >>> Integer.numberOfTrailingZeros(i));
                Assertions.assertEquals((int)(i * set.loadFactor),set.maxTableSize);
                while(--i >= 0){
                    int v;
                    switch(v=table[i]){
                    case 0:
                    case 0x7fe00000:
                        break;
                    default:
                        Assertions.assertTrue(encounteredVals.add(v));
                    }
                }
            }
            break;
        }
        case DOUBLE:{
            long[] table;
            if((table=((DoubleOpenAddressHashSet)set).table) != null){
                int i=table.length;
                // check that it is a power of 2
                Assertions.assertEquals(1,i >>> Integer.numberOfTrailingZeros(i));
                Assertions.assertEquals((int)(i * set.loadFactor),set.maxTableSize);
                while(--i >= 0){
                    long v;
                    if((v=table[i]) != 0 && v != 0x7ffc000000000000L){
                        Assertions.assertTrue(encounteredVals.add(v));
                    }
                }
            }
            break;
        }
        case REF:{
            Object[] table;
            if((table=((RefOpenAddressHashSet)set).table) != null){
                int i=table.length;
                // check that it is a power of 2
                Assertions.assertEquals(1,i >>> Integer.numberOfTrailingZeros(i));
                Assertions.assertEquals((int)(i * set.loadFactor),set.maxTableSize);
                while(--i >= 0){
                    Object v;
                    if((v=table[i]) != null && v != FieldAndMethodAccessor.RefOpenAddressHashSet.DELETED){
                        Assertions.assertTrue(encounteredVals.add(v));
                    }
                }
            }
            break;
        }
        default:
            throw DataType.invalidDataType(dataType);
        }
        Assertions.assertEquals(encounteredVals.size(),set.size);
    }
    @Override
    public void writeObjectImpl(MonitoredObjectOutputStream oos) throws IOException{
        set.writeExternal(oos);
    }

}
