package omni.impl;

import java.util.Set;

public enum StructType{
    List,
    SubList,
    Stack,
    Queue;

    public final Set<ItrType> itrTypes;
    public final Set<IllegalMod> validPreMods;

    StructType(){
        this.itrTypes=initItrTypes(this);
        this.validPreMods=initPreMods(this);
    }
    private static Set<IllegalMod> initPreMods(StructType structType){
        switch(structType){
        case List:
        case Stack:
        case Queue:
            return Set.of(IllegalMod.NoMod);
        case SubList:
            return Set.of(IllegalMod.NoMod,IllegalMod.ModParent,IllegalMod.ModRoot);
        default:
            throw new Error("Unknown structType " + structType);
        }
    }
    private static Set<ItrType> initItrTypes(StructType structType){
        switch(structType) {
        case List:
        case SubList:
            return Set.of(ItrType.Itr,ItrType.ListItr);
        case Stack:
        case Queue:
            return Set.of(ItrType.Itr);
        default:
            throw new Error("Unknown structType "+structType);
        }
    }
}
