package omni.impl;

import java.util.Set;

public enum StructType{
  List,
  SubList,
  Stack,
  Queue;
  
  public final Set<ItrType> itrTypes;
  
  StructType(){
    this.itrTypes=initItrTypes(this);
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
