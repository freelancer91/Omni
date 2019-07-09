package omni.impl;

import java.util.EnumSet;
public enum StructType{
    ArrDeq("ArrDeq"),ArrList("ArrList"),ArrStack("ArrStack"),ArrSubList("ArrSubList"),DblLnkList("DblLnkList"),
    DblLnkSubList("DblLnkSubList"),SnglLnkQueue("SnglLnkQueue"),SnglLnkStack("SnglLnkStack"),
    BooleanSetImpl("BooleanSetImpl"),ByteSetImpl("ByteSetImpl"),
    IntegralOpenAddressHashSet("IntegralOpenAddressHashSet"),OpenAddressHashSet("OpenAddressHashSet");
    public final String name;
    public final EnumSet<DataType> validDataTypes;
    public final EnumSet<QueryMethod> validQueryMethods;
    public final EnumSet<MonitoredFunctionGen> validMonitoredFunctionGens;
    public final EnumSet<MonitoredRemoveIfPredicateGen> validMonitoredRemoveIfPredicateGens;
    public final EnumSet<IllegalModification> validPreMods;
    public final EnumSet<MonitoredObjectGen> validMonitoredObjectGens;
    public final EnumSet<IteratorType> validItrTypes;
    StructType(String name){
        this.name=name;
        this.validDataTypes=initValidDataTypes(this);
        this.validQueryMethods=initQueryMethodSet(this);
        this.validMonitoredFunctionGens=initValidMonitoredFunctionGens(this);
        this.validMonitoredRemoveIfPredicateGens=initValidMonitoredRemoveIfPredicateGens(this);
        this.validPreMods=initValidPreMods(this);
        this.validMonitoredObjectGens=initValidMonitoredObjectGens(this);
        this.validItrTypes=initValidItrTypes(this);
    }
    private static EnumSet<IteratorType> initValidItrTypes(StructType structType){
        switch(structType.name){
        case "BooleanSetImpl":
        case "ByteSetImpl":
        case "IntegralOpenAddressHashSet":
        case "OpenAddressHashSet":
        case "ArrStack":
        case "SnglLnkQueue":
        case "SnglLnkStack":
            return EnumSet.of(IteratorType.AscendingItr);
        case "ArrDeq":
            return EnumSet.of(IteratorType.AscendingItr,IteratorType.DescendingItr);
        case "ArrList":
        case "DblLnkList":
            return EnumSet.of(IteratorType.AscendingItr,IteratorType.BidirectionalItr);
        case "ArrSubList":
        case "DblLnkSubList":
            return EnumSet.of(IteratorType.SubAscendingItr,IteratorType.SubBidirectionalItr);
        }
        throw structType.invalid();
    }
    private static EnumSet<MonitoredObjectGen> initValidMonitoredObjectGens(StructType structType){
        switch(structType.name){
        case "BooleanSetImpl":
        case "ByteSetImpl":
        case "IntegralOpenAddressHashSet":
            return EnumSet.noneOf(MonitoredObjectGen.class);
        case "ArrDeq":
        case "ArrList":
        case "ArrStack":
        case "DblLnkList":
        case "OpenAddressHashSet":
        case "SnglLnkQueue":
        case "SnglLnkStack":
            return EnumSet.of(MonitoredObjectGen.ModCollection,MonitoredObjectGen.NoThrow,MonitoredObjectGen.ThrowIOB,
                    MonitoredObjectGen.ModCollectionThrowIOB);
        case "ArrSubList":
        case "DblLnkSubList":
            return EnumSet.of(MonitoredObjectGen.ModCollection,MonitoredObjectGen.NoThrow,MonitoredObjectGen.ThrowIOB,
                    MonitoredObjectGen.ModCollectionThrowIOB,MonitoredObjectGen.ModParent,MonitoredObjectGen.ModRoot,
                    MonitoredObjectGen.ModParentThrowIOB,MonitoredObjectGen.ModRootThrowIOB);
        }
        throw structType.invalid();
    }
    private static EnumSet<MonitoredRemoveIfPredicateGen> initValidMonitoredRemoveIfPredicateGens(
            StructType structType){
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
            return EnumSet.of(MonitoredRemoveIfPredicateGen.ModCollection,MonitoredRemoveIfPredicateGen.Random,
                    MonitoredRemoveIfPredicateGen.RemoveAll,MonitoredRemoveIfPredicateGen.RemoveFalse,
                    MonitoredRemoveIfPredicateGen.RemoveNone,MonitoredRemoveIfPredicateGen.RemoveTrue,
                    MonitoredRemoveIfPredicateGen.Throw,MonitoredRemoveIfPredicateGen.ThrowModCollection);
        case "ArrSubList":
        case "DblLnkSubList":
            return EnumSet.of(MonitoredRemoveIfPredicateGen.ModCollection,MonitoredRemoveIfPredicateGen.Random,
                    MonitoredRemoveIfPredicateGen.RemoveAll,MonitoredRemoveIfPredicateGen.RemoveFalse,
                    MonitoredRemoveIfPredicateGen.RemoveNone,MonitoredRemoveIfPredicateGen.RemoveTrue,
                    MonitoredRemoveIfPredicateGen.Throw,MonitoredRemoveIfPredicateGen.ThrowModCollection,
                    MonitoredRemoveIfPredicateGen.ModParent,MonitoredRemoveIfPredicateGen.ModRoot,
                    MonitoredRemoveIfPredicateGen.ThrowModParent,MonitoredRemoveIfPredicateGen.ThrowModRoot);
        case "BooleanSetImpl":
            return EnumSet.of(MonitoredRemoveIfPredicateGen.ModCollection,MonitoredRemoveIfPredicateGen.RemoveAll,
                    MonitoredRemoveIfPredicateGen.RemoveFalse,MonitoredRemoveIfPredicateGen.RemoveNone,
                    MonitoredRemoveIfPredicateGen.RemoveTrue,MonitoredRemoveIfPredicateGen.Throw,
                    MonitoredRemoveIfPredicateGen.ThrowModCollection);
        }
        throw structType.invalid();
    }
    private static EnumSet<IllegalModification> initValidPreMods(StructType structType){
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
            return EnumSet.of(IllegalModification.NoMod);
        case "ArrSubList":
        case "DblLnkSubList":
            return EnumSet.of(IllegalModification.NoMod,IllegalModification.ModParent,IllegalModification.ModRoot);
        }
        throw structType.invalid();
    }
    public final UnsupportedOperationException invalid(){
        return new UnsupportedOperationException("Invalid StructType " + this);
    }
    private static EnumSet<MonitoredFunctionGen> initValidMonitoredFunctionGens(StructType structType){
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
            return EnumSet.of(MonitoredFunctionGen.ModCollection,MonitoredFunctionGen.NoThrow,
                    MonitoredFunctionGen.ThrowIOB,
                    MonitoredFunctionGen.ThrowIOBModCollection);
        case "ArrSubList":
        case "DblLnkSubList":
            return EnumSet.of(MonitoredFunctionGen.ModCollection,MonitoredFunctionGen.NoThrow,
                    MonitoredFunctionGen.ThrowIOB,
                    MonitoredFunctionGen.ThrowIOBModCollection,MonitoredFunctionGen.ModParent,
                    MonitoredFunctionGen.ModRoot,MonitoredFunctionGen.ThrowIOBModParent,
                    MonitoredFunctionGen.ThrowIOBModRoot);
        }
        throw structType.invalid();
    }
    private static EnumSet<QueryMethod> initQueryMethodSet(StructType structType){
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
        throw structType.invalid();
    }
    private static EnumSet<DataType> initValidDataTypes(StructType structType){
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
        throw structType.invalid();
    }
}
