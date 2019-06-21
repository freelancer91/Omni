package omni.impl;

import java.util.Set;
public enum StructType{
    ArrDeq(Set.of(DataType.BOOLEAN,DataType.BYTE,DataType.CHAR,DataType.SHORT,DataType.INT,DataType.LONG,DataType.FLOAT,
            DataType.DOUBLE,DataType.REF),
            Set.of(QueryMethod.Contains,QueryMethod.RemoveVal,QueryMethod.Search,QueryMethod.RemoveFirstOccurrence,
                    QueryMethod.RemoveLastOccurrence)),
    ArrList(Set.of(DataType.BOOLEAN,DataType.BYTE,DataType.CHAR,DataType.SHORT,DataType.INT,DataType.LONG,
            DataType.FLOAT,DataType.DOUBLE,DataType.REF),
            Set.of(QueryMethod.Contains,QueryMethod.RemoveVal,QueryMethod.IndexOf,QueryMethod.LastIndexOf)),
    ArrStack(
            Set.of(DataType.BOOLEAN,DataType.BYTE,DataType.CHAR,DataType.SHORT,DataType.INT,DataType.LONG,
                    DataType.FLOAT,DataType.DOUBLE,DataType.REF),
            Set.of(QueryMethod.Contains,QueryMethod.RemoveVal,QueryMethod.Search)),
    ArrSubList(
            Set.of(DataType.BOOLEAN,DataType.BYTE,DataType.CHAR,DataType.SHORT,DataType.INT,DataType.LONG,
                    DataType.FLOAT,DataType.DOUBLE,DataType.REF),
            Set.of(QueryMethod.Contains,QueryMethod.RemoveVal,QueryMethod.IndexOf,QueryMethod.LastIndexOf)),
    DblLnkList(
            Set.of(DataType.BOOLEAN,DataType.BYTE,DataType.CHAR,DataType.SHORT,DataType.INT,DataType.LONG,
                    DataType.FLOAT,DataType.DOUBLE,DataType.REF),
            Set.of(QueryMethod.Contains,QueryMethod.RemoveVal,QueryMethod.IndexOf,QueryMethod.LastIndexOf,
                    QueryMethod.Search,QueryMethod.RemoveFirstOccurrence,QueryMethod.RemoveLastOccurrence)),
    DblLnkSubList(
            Set.of(DataType.BOOLEAN,DataType.BYTE,DataType.CHAR,DataType.SHORT,DataType.INT,DataType.LONG,
                    DataType.FLOAT,DataType.DOUBLE,DataType.REF),
            Set.of(QueryMethod.Contains,QueryMethod.RemoveVal,QueryMethod.IndexOf,QueryMethod.LastIndexOf)),
    SnglLnkQueue(Set.of(DataType.BOOLEAN,DataType.BYTE,DataType.CHAR,DataType.SHORT,DataType.INT,DataType.LONG,
            DataType.FLOAT,DataType.DOUBLE,DataType.REF),Set.of(QueryMethod.Contains,QueryMethod.RemoveVal)),
    SnglLnkStack(
            Set.of(DataType.BOOLEAN,DataType.BYTE,DataType.CHAR,DataType.SHORT,DataType.INT,DataType.LONG,
                    DataType.FLOAT,DataType.DOUBLE,DataType.REF),
            Set.of(QueryMethod.Contains,QueryMethod.RemoveVal,QueryMethod.Search)),
    BooleanSetImpl(Set.of(DataType.BOOLEAN),Set.of(QueryMethod.Contains,QueryMethod.RemoveVal)),
    ByteSetImpl(Set.of(DataType.BYTE),Set.of(QueryMethod.Contains,QueryMethod.RemoveVal)),
    IntegralOpenAddressHashSet(Set.of(DataType.CHAR,DataType.SHORT,DataType.INT,DataType.LONG),
            Set.of(QueryMethod.Contains,QueryMethod.RemoveVal)),
    OpenAddressHashSet(Set.of(DataType.FLOAT,DataType.DOUBLE,DataType.REF),
            Set.of(QueryMethod.Contains,QueryMethod.RemoveVal));
    public final Set<DataType> validDataTypes;
    public final Set<QueryMethod> validQueryMethods;
    StructType(Set<DataType> validDataTypes,Set<QueryMethod> validQueryMethods){
        this.validDataTypes=validDataTypes;
        this.validQueryMethods=validQueryMethods;
    }
}
