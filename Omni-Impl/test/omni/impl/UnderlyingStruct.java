package omni.impl;

import java.util.Set;

public enum UnderlyingStruct{
  ArrSeq,
  SnglLnkSeq;
  
  public final Set<StructType> nestedTypes;
  
  UnderlyingStruct(){
    this.nestedTypes=initNestedTypes(this);
  }
  
  private static Set<StructType> initNestedTypes(UnderlyingStruct underlyingStruct){
    switch(underlyingStruct) {
    case ArrSeq:
      return Set.of(StructType.List,StructType.SubList,StructType.Stack);
    case SnglLnkSeq:
      return Set.of(StructType.Stack,StructType.Queue);
    default:
      throw new Error("Unknown underlyingStruct "+underlyingStruct);
    }
  }
}
