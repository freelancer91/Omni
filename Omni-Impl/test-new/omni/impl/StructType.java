package omni.impl;

import java.util.Set;
public enum StructType{
    ArrDeq("ArrDeq"),ArrList("ArrList"),ArrStack("ArrStack"),ArrSubList("ArrSubList"),DblLnkList("DblLnkList"),
    DblLnkSubList("DblLnkSubList"),SnglLnkQueue("SnglLnkQueue"),SnglLnkStack("SnglLnkStack"),
    BooleanSetImpl("BooleanSetImpl"),ByteSetImpl("ByteSetImpl"),
    IntegralOpenAddressHashSet("IntegralOpenAddressHashSet"),OpenAddressHashSet("OpenAddressHashSet");
    public final String name;
    public final Set<DataType> validDataTypes;
    public final Set<QueryMethod> validQueryMethods;
    public final Set<MonitoredFunctionGen> validMonitoredFunctionGens;
    public final Set<MonitoredRemoveIfPredicateGen> validMonitoredRemoveIfPredicateGens;
    public final Set<IllegalModification> validPreMods;
    StructType(String name){
        this.name=name;
        this.validDataTypes=initValidDataTypes(this);
        this.validQueryMethods=initQueryMethodSet(this);
        this.validMonitoredFunctionGens=initValidMonitoredFunctionGens(this);
        this.validMonitoredRemoveIfPredicateGens=initValidMonitoredRemoveIfPredicateGens(this);
        this.validPreMods=initValidPreMods(this);
    }
    private static Set<MonitoredRemoveIfPredicateGen> initValidMonitoredRemoveIfPredicateGens(StructType structType){
        switch(structType.name){
        case "ArrDeq":
        case "ArrList":
        case "ArrStack":
        case "DblLnkList":
        case "ByteSetImpl":
        case "IntegralOpenAddressHashSet":
        case "OpenAddressHashSet":
        case "SnglLnkQueue":
        case "SnglLnkStack":
            return Set.of(MonitoredRemoveIfPredicateGen.ModCollection,MonitoredRemoveIfPredicateGen.Random,
                    MonitoredRemoveIfPredicateGen.RemoveAll,MonitoredRemoveIfPredicateGen.RemoveFalse,
                    MonitoredRemoveIfPredicateGen.RemoveNone,MonitoredRemoveIfPredicateGen.RemoveTrue,
                    MonitoredRemoveIfPredicateGen.Throw,MonitoredRemoveIfPredicateGen.ThrowModCollection);
        case "ArrSubList":
        case "DblLnkSubList":
            return Set.of(MonitoredRemoveIfPredicateGen.ModCollection,MonitoredRemoveIfPredicateGen.Random,
                    MonitoredRemoveIfPredicateGen.RemoveAll,MonitoredRemoveIfPredicateGen.RemoveFalse,
                    MonitoredRemoveIfPredicateGen.RemoveNone,MonitoredRemoveIfPredicateGen.RemoveTrue,
                    MonitoredRemoveIfPredicateGen.Throw,MonitoredRemoveIfPredicateGen.ThrowModCollection,
                    MonitoredRemoveIfPredicateGen.ModParent,MonitoredRemoveIfPredicateGen.ModRoot,
                    MonitoredRemoveIfPredicateGen.ThrowModParent,MonitoredRemoveIfPredicateGen.ThrowModRoot);
        case "BooleanSetImpl":
            return Set.of(MonitoredRemoveIfPredicateGen.ModCollection,MonitoredRemoveIfPredicateGen.RemoveAll,
                    MonitoredRemoveIfPredicateGen.RemoveFalse,MonitoredRemoveIfPredicateGen.RemoveNone,
                    MonitoredRemoveIfPredicateGen.RemoveTrue,MonitoredRemoveIfPredicateGen.Throw,
                    MonitoredRemoveIfPredicateGen.ThrowModCollection);
        }
        throw new UnsupportedOperationException("Unknown structType " + structType);
    }
    private static Set<IllegalModification> initValidPreMods(StructType structType){
        switch(structType.name){
        case "ArrDeq":
        case "ArrList":
        case "ArrStack":
        case "BooleanSetImpl":
        case "ByteSetImpl":
        case "DblLnkList":
        case "IntegralOpenAddressHashSet":
        case "OpenAddressHashSet":
        case "SnglLnkQueue":
        case "SnglLnkStack":
            return Set.of(IllegalModification.NoMod);
        case "ArrSubList":
        case "DblLnkSubList":
            return Set.of(IllegalModification.NoMod,IllegalModification.ModParent,IllegalModification.ModRoot);
        }
        throw new UnsupportedOperationException("Unknown structType " + structType);
    }
    private static Set<MonitoredFunctionGen> initValidMonitoredFunctionGens(StructType structType){
        switch(structType.name){
        case "ArrDeq":
        case "ArrList":
        case "ArrStack":
        case "BooleanSetImpl":
        case "ByteSetImpl":
        case "DblLnkList":
        case "IntegralOpenAddressHashSet":
        case "OpenAddressHashSet":
        case "SnglLnkQueue":
        case "SnglLnkStack":
            return Set.of(MonitoredFunctionGen.ModCollection,MonitoredFunctionGen.NoThrow,MonitoredFunctionGen.ThrowIOB,
                    MonitoredFunctionGen.ThrowIOBModCollection);
        case "ArrSubList":
        case "DblLnkSubList":
            return Set.of(MonitoredFunctionGen.ModCollection,MonitoredFunctionGen.NoThrow,MonitoredFunctionGen.ThrowIOB,
                    MonitoredFunctionGen.ThrowIOBModCollection,MonitoredFunctionGen.ModParent,
                    MonitoredFunctionGen.ModRoot,MonitoredFunctionGen.ThrowIOBModParent,
                    MonitoredFunctionGen.ThrowIOBModRoot);
        }
        throw new UnsupportedOperationException("Unknown structType " + structType);
    }
    private static Set<QueryMethod> initQueryMethodSet(StructType structType){
        switch(structType.name){
        case "IntegralOpenAddressHashSet":
        case "OpenAddressHashSet":
        case "ByteSetImpl":
        case "SnglLnkQueue":
        case "BooleanSetImpl":
            return QueryMethod.BASIC_COLLECTION_METHODS;
        case "SnglLnkStack":
        case "ArrStack":
            return QueryMethod.STACK_METHODS;
        case "DblLnkSubList":
        case "ArrSubList":
        case "ArrList":
            return QueryMethod.LIST_METHODS;
        case "ArrDeq":
            return QueryMethod.DEQUE_METHODS;
        case "DblLnkList":
            return QueryMethod.LISTDEQUE_METHODS;
        }
        throw new UnsupportedOperationException("Unknown structType " + structType);
    }
    private static Set<DataType> initValidDataTypes(StructType structType){
        switch(structType.name){
        case "BooleanSetImpl":
            return DataType.getDataTypeSet("BOOLEAN");
        case "ByteSetImpl":
            return DataType.getDataTypeSet("BYTE");
        case "IntegralOpenAddressHashSet":
            return DataType.getDataTypeSet("CHAR,SHORT,INT,LONG");
        case "OpenAddressHashSet":
            return DataType.getDataTypeSet("FLOAT,DOUBLE,REF");
        case "ArrDeq":
        case "ArrList":
        case "ArrSubList":
        case "DblLnkList":
        case "DblLnkSubList":
        case "SnglLnkQueue":
        case "ArrStack":
        case "SnglLnkStack":
            return DataType.getDataTypeSet("BOOLEAN,BYTE,CHAR,SHORT,INT,LONG,FLOAT,DOUBLE,REF");
        }
        throw new UnsupportedOperationException("Unknown structType " + structType);
    }
}
