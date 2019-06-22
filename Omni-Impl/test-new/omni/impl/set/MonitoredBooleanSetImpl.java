package omni.impl.set;

import org.junit.jupiter.api.Assertions;
import omni.impl.CheckedType;
import omni.impl.DataType;
import omni.impl.FunctionCallType;
import omni.impl.IllegalModification;
import omni.impl.IteratorType;
import omni.impl.MonitoredFunctionGen;
import omni.impl.MonitoredObject;
import omni.impl.MonitoredRemoveIfPredicateGen;
import omni.impl.MonitoredSet;
import omni.impl.QueryCastType;
import omni.impl.QueryVal;
import omni.impl.StructType;

public class MonitoredBooleanSetImpl implements MonitoredSet<BooleanSetImpl>{
    BooleanSetImpl set;
    int expectedState;
    CheckedType checkedType;
    @Override
    public BooleanSetImpl getCollection(){
        return set;
    }
    @Override
    public void verifyCollectionState(){
        Assertions.assertEquals(expectedState,set.state);
    }
    @Override
    public int verifySize(){
        int expectedState=this.expectedState;
        int actualSize=set.size();
        Assertions.assertEquals(expectedState,set.state);
        Assertions.assertEquals(Integer.bitCount(expectedState),actualSize);
        return actualSize;
    }
    @Override
    public int size(){
        return Integer.bitCount(expectedState);
    }
    @Override
    public boolean verifyIsEmpty(){
        int expectedState=this.expectedState;
        boolean actualResult=set.isEmpty();
        Assertions.assertEquals(expectedState,set.state);
        Assertions.assertEquals(expectedState == 0,actualResult);
        return actualResult;
    }
    @Override
    public boolean isEmpty(){
        return expectedState == 0;
    }
    @Override
    public String verifyToString(){
        int expectedState=this.expectedState;
        String actualResult=set.toString();
        Assertions.assertEquals(expectedState,set.state);
        String expectedResult;
        switch(expectedState){
        case 0b00:
            expectedResult="[]";
            break;
        case 0b01:
            expectedResult="[false]";
            break;
        case 0b10:
            expectedResult="[true]";
            break;
        default:
            expectedResult="[false, true]";
        }
        Assertions.assertEquals(expectedResult,actualResult);
        return actualResult;
    }
    @Override
    public int verifyHashCode(){
        int expectedState=this.expectedState;
        int actualResult=set.hashCode();
        Assertions.assertEquals(expectedState,set.state);
        int expectedResult;
        switch(expectedState){
        case 0b00:
            expectedResult=0;
            break;
        case 0b01:
            expectedResult=Boolean.hashCode(false);
            break;
        case 0b10:
            expectedResult=Boolean.hashCode(true);
            break;
        default:
            expectedResult=Boolean.hashCode(false) + Boolean.hashCode(true);
        }
        Assertions.assertEquals(expectedResult,actualResult);
        return actualResult;
    }
    @Override
    public DataType getDataType(){
        return DataType.BOOLEAN;
    }
    @Override
    public StructType getStructType(){
        return StructType.BooleanSetImpl;
    }
    @Override
    public CheckedType getCheckedType(){
        return this.checkedType;
    }
    @Override
    public Object verifyClone(){
        int expectedState=this.expectedState;
        Object clone=set.clone();
        Assertions.assertEquals(expectedState,set.state);
        if(checkedType.checked){
            Assertions.assertTrue(clone instanceof BooleanSetImpl.Checked);
        }else{
            Assertions.assertFalse(clone instanceof BooleanSetImpl.Checked);
            Assertions.assertTrue(clone instanceof BooleanSetImpl);
        }
        Assertions.assertEquals(expectedState,((BooleanSetImpl)clone).state);
        return clone;
    }
    @Override
    public boolean add(int val){
        boolean v=(val & 1) != 0;
        boolean result=set.add(v);
        expectedState=set.state;
        return result;
    }
    @Override
    public boolean add(MonitoredObject monitoredObject){
        throw new UnsupportedOperationException();
    }
    @Override
    public boolean verifyAdd(Object inputVal,DataType inputType,boolean boxed){
        switch(inputType){
        case BOOLEAN:{
            int expectedOriginalState=this.expectedState;
            boolean v=(boolean)inputVal;
            int expectedNewState=expectedOriginalState | (v?2:1);
            boolean actualResult;
            if(boxed){
                actualResult=set.add((Boolean)v);
            }else{
                actualResult=set.add(v);
            }
            Assertions.assertEquals(expectedNewState,set.state);
            this.expectedState=expectedNewState;
            Assertions.assertEquals(expectedNewState != expectedOriginalState,actualResult);
            return actualResult;
        }
        case BYTE:
        case CHAR:
        case DOUBLE:
        case FLOAT:
        case INT:
        case LONG:
        case REF:
        case SHORT:
        }
        throw new UnsupportedOperationException("Invalid inputType " + inputType);
    }
    @Override
    public boolean verifyRemoveVal(QueryVal queryVal,DataType inputType,QueryCastType queryCastType,
            QueryVal.QueryValModification modification){
        int expectedOriginalState=this.expectedState;
        boolean result=queryCastType.callremoveVal(set,queryVal.getInputVal(inputType,modification),inputType);
        int expectedNewState;
        if(result){
            expectedNewState=expectedOriginalState & (queryVal.getBooleanVal()?~2:~1);
        }else{
            expectedNewState=expectedOriginalState;
        }
        Assertions.assertEquals(expectedNewState,set.state);
        this.expectedState=expectedNewState;
        return result;
    }
    @Override
    public boolean verifyContains(QueryVal queryVal,DataType inputType,QueryCastType queryCastType,
            QueryVal.QueryValModification modification){
        int expectedOriginalState=this.expectedState;
        boolean result=queryCastType.callremoveVal(set,queryVal.getInputVal(inputType,modification),inputType);
        Assertions.assertEquals(expectedOriginalState,set.state);
        return result;
    }

    @Override
    public void initContains(QueryVal queryVal,int setSize,double containsPosition,Object initialVal){
        queryVal.initContains(set,setSize,(int)convertInitialVal(initialVal),containsPosition);
        this.expectedState=set.state;
    }
    @Override
    public void initDoesNotContains(QueryVal queryVal,int setSize,Object initialVal){
        queryVal.initDoesNotContain(set,setSize,(int)convertInitialVal(initialVal));
        this.expectedState=set.state;
    }
    @Override
    public void verifyForEach(MonitoredFunctionGen functionGen,FunctionCallType functionCallType){
        // TODO Auto-generated method stub
    }
    @Override
    public boolean verifyRemoveIf(MonitoredRemoveIfPredicateGen filterGen,FunctionCallType functionCallType){
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public Object verifyToArray(DataType outputType){
        // TODO
        return null;
    }
    @Override
    public Object[] verifyToArray(MonitoredFunctionGen monitoredFunctionGen){
        // TODO Auto-generated method stub
        return null;
    }
    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] verifyToArray(T[] arr){
        int expectedState=this.expectedState;
        int setSize=Integer.bitCount(expectedState);
        int arrLength=arr.length;
        for(int i=setSize;i < arrLength;++i){
            arr[i]=(T)Integer.valueOf(i);
        }
        T[] result=set.toArray(arr);
        Assertions.assertEquals(expectedState,set.state);
        switch(Integer.signum(setSize - arrLength)){
        case -1:
            Assertions.assertNull(result[setSize]);
            for(int i=setSize + 1;i < arrLength;++i){
                Assertions.assertEquals(Integer.valueOf(i),result[i]);
            }
        case 0:
            Assertions.assertSame(arr,result);
            break;
        default:
            Assertions.assertNotSame(arr,result);
            Assertions.assertEquals(setSize,result.length);
            for(int i=setSize;i < arrLength;++i){
                Assertions.assertEquals(Integer.valueOf(i),arr[i]);
            }
        }
        switch(expectedState){
        case 0b10:
            Assertions.assertEquals(Boolean.TRUE,result[0]);
            break;
        default:
            Assertions.assertEquals(Boolean.TRUE,result[1]);
        case 0b01:
            Assertions.assertEquals(Boolean.FALSE,result[0]);
        case 0b00:
            break;
        }
        return result;
    }
    @Override
    public void illegalMod(IllegalModification modType){
        switch(modType){
        case ModCollection:
            switch(expectedState){
            case 0b00:
                set.state=expectedState=0b01;
                break;
            case 0b01:
                set.state=expectedState=0b10;
                break;
            case 0b10:
                set.state=expectedState=0b11;
                break;
            default:
                set.state=expectedState=0b00;
            }
        case NoMod:
            return;
        case ModItr:
        case ModParent:
        case ModRoot:
        }
        throw new UnsupportedOperationException("Unknown modType " + modType);
    }
    @Override
    public MonitoredIterator<BooleanSetImpl> getMonitoredIterator(){
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public MonitoredIterator<BooleanSetImpl> getMonitoredIterator(IteratorType itrType){
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public MonitoredIterator<BooleanSetImpl> getMonitoredIterator(IteratorType itrType,int index){
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public void verifyReadAndWrite(MonitoredFunctionGen monitoredFunctionGen){
        // TODO Auto-generated method stub
    }
}
