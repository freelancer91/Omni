package omni.impl;

import java.util.Set;
public enum StructType{
    ArrDeq(),ArrList(),ArrStack(),ArrSubList(),DblLnkList(),DblLnkSubList(),SnglLnkQueue(),SnglLnkStack(),
    BooleanSetImpl(),ByteSetImpl(),IntegralOpenAddressHashSet(),OpenAddressHashSet();
    public final Set<DataType> validDataTypes;
    public final Set<QueryMethod> validQueryMethods;
    StructType(){
        this.validDataTypes=initValidDataTypes(this);
        this.validQueryMethods=initQueryMethodSet(this);
    }

    private static Set<QueryMethod> initQueryMethodSet(StructType structType){
        switch(structType){
        case IntegralOpenAddressHashSet:
        case OpenAddressHashSet:
        case ByteSetImpl:
        case SnglLnkQueue:
        case BooleanSetImpl:
            return QueryMethod.BASIC_COLLECTION_METHODS;
        case SnglLnkStack:
        case ArrStack:
            return QueryMethod.STACK_METHODS;
        case DblLnkSubList:
        case ArrSubList:
        case ArrList:
            return QueryMethod.LIST_METHODS;
        case ArrDeq:
            return QueryMethod.DEQUE_METHODS;
        case DblLnkList:
            return QueryMethod.LISTDEQUE_METHODS;
        }
        throw new UnsupportedOperationException("Unknown structType " + structType);
    }
    private static Set<DataType> initValidDataTypes(StructType structType){
        switch(structType){
        case BooleanSetImpl:
            return DataType.getDataTypeSet(DataType.BOOLEAN);
        case ByteSetImpl:
            return DataType.getDataTypeSet(DataType.BYTE);
        case IntegralOpenAddressHashSet:
            return DataType.getDataTypeSet(DataType.CHAR,DataType.SHORT,DataType.INT,DataType.LONG);
        case OpenAddressHashSet:
            return DataType.getDataTypeSet(DataType.FLOAT,DataType.DOUBLE,DataType.REF);
        case ArrDeq:
        case ArrList:
        case ArrSubList:
        case DblLnkList:
        case DblLnkSubList:
        case SnglLnkQueue:
        case ArrStack:
        case SnglLnkStack:
            return DataType.getDataTypeSet(DataType.BOOLEAN,DataType.BYTE,DataType.CHAR,DataType.SHORT,DataType.INT,
                    DataType.LONG,DataType.FLOAT,DataType.DOUBLE,DataType.REF);
        }
        throw new UnsupportedOperationException("Unknown structType " + structType);
    }
}
