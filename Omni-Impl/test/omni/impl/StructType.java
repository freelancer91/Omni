package omni.impl;

import java.util.EnumSet;
public enum StructType{
    ArrDeq("ArrDeq"),ArrList("ArrList"),ArrStack("ArrStack"),ArrSubList("ArrSubList"),DblLnkList("DblLnkList"),
    DblLnkSubList("DblLnkSubList"),SnglLnkQueue("SnglLnkQueue"),SnglLnkStack("SnglLnkStack"),
    BooleanSetImpl("BooleanSetImpl"),
    BooleanSetEmpty("BooleanSetEmpty"),
    BooleanSetFullView("BooleanSetFullView"),
    BooleanSetSingleView("BooleanSetSingleView"),
    ByteSetImpl("ByteSetImpl"),
    IntegralOpenAddressHashSet("IntegralOpenAddressHashSet"),OpenAddressHashSet("OpenAddressHashSet"),
    PackedBooleanArrStack("PackedBooleanArrStack"),PackedBooleanArrList("PackedBooleanArrList"),PackedBooleanArrSubList("PackedBooleanArrSubList"),PackedBooleanArrDeq("PackedBooleanArrDeq");
    public final String name;
    public final EnumSet<DataType> validDataTypes;
    public final EnumSet<QueryMethod> validQueryMethods;
    public final EnumSet<MonitoredFunctionGen> validMonitoredFunctionGens;
    public final EnumSet<MonitoredComparatorGen> validComparatorGens;
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
        this.validComparatorGens=initValidComparatorGens(this);
    }
    private static EnumSet<MonitoredComparatorGen> initValidComparatorGens(StructType structType){
        switch(structType.name){
        case "BooleanSetImpl":
        case "BooleanSetEmpty":
        case "BooleanSetFullView":
        case "BooleanSetSingleView":
          return EnumSet.of(MonitoredComparatorGen.ModCollectionAscending,MonitoredComparatorGen.ModCollectionDescending);
        case "ByteSetImpl":
        case "IntegralOpenAddressHashSet":
        case "OpenAddressHashSet":
        case "ArrStack":
        case "SnglLnkQueue":
        case "SnglLnkStack":
        case "ArrDeq":
        case "PackedBooleanArrDeq":
        case "PackedBooleanArrStack":
            return EnumSet.noneOf(MonitoredComparatorGen.class);
        case "ArrList":
        case "DblLnkList":
            return EnumSet.of(MonitoredComparatorGen.NoThrowUnsorted,MonitoredComparatorGen.ModCollectionAscending,
                    MonitoredComparatorGen.ModCollectionDescending,MonitoredComparatorGen.ModCollectionThrowAIOB,
                    MonitoredComparatorGen.ModCollectionThrowIOB,MonitoredComparatorGen.NoThrowAscending,
                    MonitoredComparatorGen.NoThrowDescending,MonitoredComparatorGen.NullComparator,
                    MonitoredComparatorGen.NullComparatorModCollection,
                    MonitoredComparatorGen.NullComparatorModCollectionThrowAIOB,
                    MonitoredComparatorGen.NullComparatorModCollectionThrowIOB,
                    MonitoredComparatorGen.NullComparatorThrowAIOB,MonitoredComparatorGen.NullComparatorThrowIOB,
                    MonitoredComparatorGen.ThrowAIOB,MonitoredComparatorGen.ThrowIOB);
        case "ArrSubList":
        case "DblLnkSubList":
            return EnumSet.allOf(MonitoredComparatorGen.class);
        case "PackedBooleanArrList":
            return EnumSet.of(MonitoredComparatorGen.NoThrowUnsorted,MonitoredComparatorGen.ModCollectionAscending,
                    MonitoredComparatorGen.ModCollectionDescending,
                    MonitoredComparatorGen.ModCollectionThrowIOB,MonitoredComparatorGen.NoThrowAscending,
                    MonitoredComparatorGen.NoThrowDescending,MonitoredComparatorGen.NullComparator,MonitoredComparatorGen.ThrowIOB);
        case "PackedBooleanArrSubList":
            return EnumSet.of(MonitoredComparatorGen.NoThrowUnsorted,MonitoredComparatorGen.ModCollectionAscending,MonitoredComparatorGen.ModCollectionDescending,MonitoredComparatorGen.ModCollectionThrowIOB,MonitoredComparatorGen.ModParentAscending,MonitoredComparatorGen.ModParentDescending,MonitoredComparatorGen.ModParentThrowIOB,MonitoredComparatorGen.ModRootAscending,MonitoredComparatorGen.ModRootDescending,MonitoredComparatorGen.ModRootThrowIOB,MonitoredComparatorGen.NoThrowAscending,MonitoredComparatorGen.NoThrowDescending,MonitoredComparatorGen.NullComparator,MonitoredComparatorGen.ThrowIOB);
        }
        throw structType.invalid();
    }
    private static EnumSet<IteratorType> initValidItrTypes(StructType structType){
        switch(structType.name){
        
        case "ByteSetImpl":
        case "IntegralOpenAddressHashSet":
        case "OpenAddressHashSet":
        case "ArrStack":
        case "SnglLnkQueue":
        case "SnglLnkStack":
        case "PackedBooleanArrStack":
            return EnumSet.of(IteratorType.AscendingItr);
        case "ArrDeq":
        case "PackedBooleanArrDeq":
        case "BooleanSetImpl":
        case "BooleanSetEmpty":
        case "BooleanSetFullView":
        case "BooleanSetSingleView":
            return EnumSet.of(IteratorType.AscendingItr,IteratorType.DescendingItr);
        case "ArrList":
        case "PackedBooleanArrList":
            return EnumSet.of(IteratorType.AscendingItr,IteratorType.BidirectionalItr);
        case "ArrSubList":
        case "DblLnkSubList":
        case "PackedBooleanArrSubList":
            return EnumSet.of(IteratorType.SubAscendingItr,IteratorType.SubBidirectionalItr);
        case "DblLnkList":
            return EnumSet.of(IteratorType.AscendingItr,IteratorType.DescendingItr,IteratorType.BidirectionalItr);
        }
        throw structType.invalid();
    }
    private static EnumSet<MonitoredObjectGen> initValidMonitoredObjectGens(StructType structType){
        switch(structType.name){
        case "BooleanSetImpl":
        case "BooleanSetEmpty":
        case "BooleanSetFullView":
        case "BooleanSetSingleView":
        case "ByteSetImpl":
        case "IntegralOpenAddressHashSet":
        case "PackedBooleanArrList":
        case "PackedBooleanArrStack":
        case "PackedBooleanArrSubList":
        case "PackedBooleanArrDeq":
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
        case "BooleanSetEmpty":
          return EnumSet.noneOf(MonitoredRemoveIfPredicateGen.class);
        case "ArrList":
        case "ArrStack":
        case "ByteSetImpl":
        case "IntegralOpenAddressHashSet":
        case "OpenAddressHashSet":
        case "SnglLnkQueue":
        case "SnglLnkStack":
            return EnumSet.of(MonitoredRemoveIfPredicateGen.ModCollection,MonitoredRemoveIfPredicateGen.Random,
                    MonitoredRemoveIfPredicateGen.RemoveAll,MonitoredRemoveIfPredicateGen.RemoveFalse,
                    MonitoredRemoveIfPredicateGen.RemoveNone,MonitoredRemoveIfPredicateGen.RemoveTrue,
                    MonitoredRemoveIfPredicateGen.Throw,MonitoredRemoveIfPredicateGen.ThrowModCollection);
        case "PackedBooleanArrList":
        case "PackedBooleanArrStack":
          return EnumSet.of(MonitoredRemoveIfPredicateGen.ModCollection,MonitoredRemoveIfPredicateGen.Random,
              MonitoredRemoveIfPredicateGen.RemoveAll,MonitoredRemoveIfPredicateGen.RemoveFalse,
              MonitoredRemoveIfPredicateGen.RemoveNone,MonitoredRemoveIfPredicateGen.RemoveTrue,
              MonitoredRemoveIfPredicateGen.Throw,MonitoredRemoveIfPredicateGen.ThrowModCollection);
        case "PackedBooleanArrSubList":
          return EnumSet.of(MonitoredRemoveIfPredicateGen.ModCollection,MonitoredRemoveIfPredicateGen.Random,
              MonitoredRemoveIfPredicateGen.RemoveAll,MonitoredRemoveIfPredicateGen.RemoveFalse,
              MonitoredRemoveIfPredicateGen.RemoveNone,MonitoredRemoveIfPredicateGen.RemoveTrue,
              MonitoredRemoveIfPredicateGen.Throw,MonitoredRemoveIfPredicateGen.ThrowModCollection,
              MonitoredRemoveIfPredicateGen.ModParent,MonitoredRemoveIfPredicateGen.ModRoot,
              MonitoredRemoveIfPredicateGen.ThrowModParent,MonitoredRemoveIfPredicateGen.ThrowModRoot);
        case "ArrSubList":
            return EnumSet.of(MonitoredRemoveIfPredicateGen.ModCollection,MonitoredRemoveIfPredicateGen.Random,
                    MonitoredRemoveIfPredicateGen.RemoveAll,MonitoredRemoveIfPredicateGen.RemoveFalse,
                    MonitoredRemoveIfPredicateGen.RemoveNone,MonitoredRemoveIfPredicateGen.RemoveTrue,
                    MonitoredRemoveIfPredicateGen.Throw,MonitoredRemoveIfPredicateGen.ThrowModCollection,
                    MonitoredRemoveIfPredicateGen.ModParent,MonitoredRemoveIfPredicateGen.ModRoot,
                    MonitoredRemoveIfPredicateGen.ThrowModParent,MonitoredRemoveIfPredicateGen.ThrowModRoot);
        case "ArrDeq":
        case "DblLnkList":
          return EnumSet.of(MonitoredRemoveIfPredicateGen.RemoveFirst,MonitoredRemoveIfPredicateGen.RemoveLast,MonitoredRemoveIfPredicateGen.RemoveFirstAndLast,MonitoredRemoveIfPredicateGen.RemoveAllButFirst,MonitoredRemoveIfPredicateGen.RemoveAllButLast,MonitoredRemoveIfPredicateGen.RemoveAllButFirstAndLast,MonitoredRemoveIfPredicateGen.ModCollection,MonitoredRemoveIfPredicateGen.Random,
              MonitoredRemoveIfPredicateGen.RemoveFalse,MonitoredRemoveIfPredicateGen.RemoveTrue,
              MonitoredRemoveIfPredicateGen.RemoveNone,MonitoredRemoveIfPredicateGen.RemoveAll,
              MonitoredRemoveIfPredicateGen.Throw,MonitoredRemoveIfPredicateGen.ThrowModCollection);
        case "PackedBooleanArrDeq":
            return EnumSet.of(MonitoredRemoveIfPredicateGen.ModCollection,
                    MonitoredRemoveIfPredicateGen.RemoveFalse,MonitoredRemoveIfPredicateGen.RemoveTrue,
                    MonitoredRemoveIfPredicateGen.RemoveNone,MonitoredRemoveIfPredicateGen.RemoveAll,
                    MonitoredRemoveIfPredicateGen.Throw,MonitoredRemoveIfPredicateGen.ThrowModCollection);
        case "DblLnkSubList":
            return EnumSet.of(MonitoredRemoveIfPredicateGen.RemoveFirst,MonitoredRemoveIfPredicateGen.RemoveLast,MonitoredRemoveIfPredicateGen.RemoveFirstAndLast,MonitoredRemoveIfPredicateGen.RemoveAllButFirst,MonitoredRemoveIfPredicateGen.RemoveAllButLast,MonitoredRemoveIfPredicateGen.RemoveAllButFirstAndLast,MonitoredRemoveIfPredicateGen.ModCollection,MonitoredRemoveIfPredicateGen.Random,
                    MonitoredRemoveIfPredicateGen.RemoveFalse,MonitoredRemoveIfPredicateGen.RemoveTrue,
                    MonitoredRemoveIfPredicateGen.RemoveNone,MonitoredRemoveIfPredicateGen.RemoveAll,
                    MonitoredRemoveIfPredicateGen.Throw,MonitoredRemoveIfPredicateGen.ThrowModCollection,
                    MonitoredRemoveIfPredicateGen.ModParent,MonitoredRemoveIfPredicateGen.ModRoot,
                    MonitoredRemoveIfPredicateGen.ThrowModParent,MonitoredRemoveIfPredicateGen.ThrowModRoot);
        case "BooleanSetImpl":
        case "BooleanSetFullView":
        case "BooleanSetSingleView":
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
        case "BooleanSetEmpty":
        case "BooleanSetFullView":
        case "BooleanSetSingleView":
        case "ByteSetImpl":
        case "DblLnkList":
        case "IntegralOpenAddressHashSet":
        case "OpenAddressHashSet":
        case "SnglLnkQueue":
        case "SnglLnkStack":
        case "PackedBooleanArrList":
        case "PackedBooleanArrStack":
        case "PackedBooleanArrDeq":
            return EnumSet.of(IllegalModification.NoMod);
        case "ArrSubList":
        case "DblLnkSubList":
        case "PackedBooleanArrSubList":
            return EnumSet.of(IllegalModification.NoMod,IllegalModification.ModParent,IllegalModification.ModRoot);
        }
        throw structType.invalid();
    }
    public final UnsupportedOperationException invalid(){
        return new UnsupportedOperationException("Invalid StructType " + this);
    }
    private static EnumSet<MonitoredFunctionGen> initValidMonitoredFunctionGens(StructType structType){
        switch(structType.name){
        case "BooleanSetEmpty":
          return EnumSet.noneOf(MonitoredFunctionGen.class);
        case "ArrDeq":
        case "ArrList":
        case "ArrStack":
        case "BooleanSetImpl":
        case "BooleanSetFullView":
        case "BooleanSetSingleView":
        case "ByteSetImpl":
        case "DblLnkList":
        case "IntegralOpenAddressHashSet":
        case "OpenAddressHashSet":
        case "SnglLnkQueue":
        case "SnglLnkStack":
        case "PackedBooleanArrList":
        case "PackedBooleanArrStack":
        case "PackedBooleanArrDeq":
            return EnumSet.of(MonitoredFunctionGen.ModCollection,MonitoredFunctionGen.NoThrow,
                    MonitoredFunctionGen.ThrowIOB,
                    MonitoredFunctionGen.ThrowIOBModCollection);
        case "ArrSubList":
        case "DblLnkSubList":
        case "PackedBooleanArrSubList":
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
        case "BooleanSetEmpty":
        case "BooleanSetFullView":
        case "BooleanSetSingleView":
            return QueryMethod.BASIC_COLLECTION_METHODS;
        case "SnglLnkStack":
        case "ArrStack":
        case "PackedBooleanArrStack":
            return QueryMethod.STACK_METHODS;
        case "DblLnkSubList":
        case "ArrSubList":
        case "ArrList":
        case "PackedBooleanArrList":
        case "PackedBooleanArrSubList":
            return QueryMethod.LIST_METHODS;
        case "ArrDeq":
        case "PackedBooleanArrDeq":
            return QueryMethod.DEQUE_METHODS;
        case "DblLnkList":
            return QueryMethod.LISTDEQUE_METHODS;
        }
        throw structType.invalid();
    }
    private static EnumSet<DataType> initValidDataTypes(StructType structType){
        switch(structType.name){
        case "BooleanSetEmpty":
        case "BooleanSetImpl":
        case "BooleanSetFullView":
        case "PackedBooleanArrList":
        case "PackedBooleanArrStack":
        case "PackedBooleanArrSubList":
        case "PackedBooleanArrDeq":
        case "BooleanSetSingleView":
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
