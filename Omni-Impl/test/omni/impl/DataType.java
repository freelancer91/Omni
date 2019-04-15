package omni.impl;

import java.util.Set;

public enum DataType{
  Boolean {
    
  },
  Byte {
    

  },
  Char {
    

    
  },
  Short {
    

  },
  Int {
    
    
  },
  Long {
    


  },
  Float {
    

  },
  Double {
    
  },
  Object {
    

  };
  
  public final Set<DataType> mayBeCastTo;
  public final Set<DataType> mayBeCastFrom;
    
  DataType(){
    this.mayBeCastTo=initMayBeCastTo(this);
    this.mayBeCastFrom=initMayBeCastFrom(this);
  }

  

  private static Set<DataType> initMayBeCastFrom(DataType dataType){
    switch(dataType) {
    case Boolean:
      return Set.of(DataType.Boolean);
    case Byte:
      return Set.of(DataType.Byte,DataType.Boolean);
    case Char:
      return Set.of(DataType.Char,DataType.Boolean);
    case Short:
      return Set.of(DataType.Short,DataType.Byte,DataType.Boolean);
    case Int:
      return Set.of(DataType.Int,DataType.Short,DataType.Char,DataType.Byte,DataType.Boolean);
    case Long:
      return Set.of(DataType.Long,DataType.Int,DataType.Short,DataType.Char,DataType.Byte,DataType.Boolean);
    case Float:
      return Set.of(DataType.Float,DataType.Long,DataType.Int,DataType.Short,DataType.Char,DataType.Byte,DataType.Boolean);
    case Double:
      return Set.of(DataType.Double,DataType.Float,DataType.Long,DataType.Int,DataType.Short,DataType.Char,DataType.Byte,DataType.Boolean);
    case Object:
      return Set.of(DataType.Object);
    default:
      throw new Error("Unknown dataType "+dataType);
    }
  }
  private static Set<DataType> initMayBeCastTo(DataType dataType){
    switch(dataType) {
    case Boolean:
      return Set.of(DataType.values());
    case Byte:
      return Set.of(DataType.Byte,DataType.Short,DataType.Int,DataType.Long,DataType.Float,DataType.Double,DataType.Object);
    case Char:
      return Set.of(DataType.Char,DataType.Int,DataType.Long,DataType.Float,DataType.Double,DataType.Object);
    case Short:
      return Set.of(DataType.Short,DataType.Int,DataType.Long,DataType.Float,DataType.Double,DataType.Object);
    case Int:
      return Set.of(DataType.Int,DataType.Long,DataType.Float,DataType.Double,DataType.Object);
    case Long:
      return Set.of(DataType.Long,DataType.Float,DataType.Double,DataType.Object);
    case Float:
      return Set.of(DataType.Float,DataType.Double,DataType.Object);
    case Double:
      return Set.of(DataType.Double,DataType.Object);
    case Object:
      return Set.of(DataType.Object);
    default:
      throw new Error("Unknown dataType "+dataType);
    }
  }
}
