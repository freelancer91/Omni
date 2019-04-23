package omni.impl;

import java.util.Set;

public enum StructType{
    List,
    SubList,
    Stack,
    Queue,ListDeque,Deque;

    public final Set<ItrType> itrTypes;
    public final Set<IllegalMod> validPreMods;
    public final Set<IllegalMod> validFunctionMods;

    StructType(){
        this.itrTypes=initItrTypes(this);
        this.validPreMods=initPreMods(this);
        this.validFunctionMods=initFunctionMods(this);
    }
    private static Set<IllegalMod> initFunctionMods(StructType structType){
        switch(structType){
        case List:
        case Stack:
        case Queue:
        case ListDeque:
        case Deque:
            return Set.of(IllegalMod.NoMod,IllegalMod.ModSeq);
        case SubList:
            return Set.of(IllegalMod.NoMod,IllegalMod.ModSeq,IllegalMod.ModParent,IllegalMod.ModRoot);
        }
        throw new Error("Unknown structType " + structType);
    }
    private static Set<IllegalMod> initPreMods(StructType structType){
        switch(structType){
        case List:
        case Stack:
        case Queue:
        case ListDeque:
        case Deque:
            return Set.of(IllegalMod.NoMod);
        case SubList:
            return Set.of(IllegalMod.NoMod,IllegalMod.ModParent,IllegalMod.ModRoot);
        }
        throw new Error("Unknown structType " + structType);
    }
    private static Set<ItrType> initItrTypes(StructType structType){
        switch(structType) {
        case List:
            return Set.of(ItrType.Itr,ItrType.ListItr);
        case SubList:
            return Set.of(ItrType.SubItr,ItrType.SubListItr);
        case Stack:
        case Queue:
            return Set.of(ItrType.Itr);
        case ListDeque:
            return Set.of(ItrType.Itr,ItrType.Descending,ItrType.ListItr);
        case Deque:
            return Set.of(ItrType.Itr,ItrType.Descending);
        }
        throw new Error("Unknown structType " + structType);
    }
}
