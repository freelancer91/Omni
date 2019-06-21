package omni.impl;
import java.util.Set;
import omni.util.TypeUtil;
public enum QueryVal{
  Pos0{
    @Override Object getVal(DataType dataType){
      switch(dataType){
      case BOOLEAN:
        return (boolean)(false);
      case BYTE:
        return (byte)(0);
      case CHAR:
        return (char)(0);
      case SHORT:
        return (short)(0);
      case INT:
        return (int)(0);
      case LONG:
        return (long)(0);
      case FLOAT:
        return (float)(0);
      case DOUBLE:
        return (double)(0);
      case REF:
      }
      throw getUOE(dataType);
    }
  },
  Neg0{
    @Override Object getVal(DataType dataType){
      switch(dataType){
      case FLOAT:
        return (float)-0.0f;
      case DOUBLE:
        return (double)-0.0d;
      case BOOLEAN:
      case BYTE:
      case CHAR:
      case SHORT:
      case INT:
      case LONG:
      case REF:
      }
      throw getUOE(dataType);
    }
  },
  Neg1{
    @Override Object getVal(DataType dataType){
      switch(dataType){
      case BYTE:
        return (byte)(-1);
      case SHORT:
        return (short)(-1);
      case INT:
        return (int)(-1);
      case LONG:
        return (long)(-1);
      case FLOAT:
        return (float)(-1);
      case DOUBLE:
        return (double)(-1);
      case BOOLEAN:
      case CHAR:
      case REF:
      }
      throw getUOE(dataType);
    }
  },
  MaxBoolean{
    @Override Object getVal(DataType dataType){
      switch(dataType){
      case BOOLEAN:
        return (boolean)(true);
      case BYTE:
        return (byte)(1);
      case CHAR:
        return (char)(1);
      case SHORT:
        return (short)(1);
      case INT:
        return (int)(1);
      case LONG:
        return (long)(1);
      case FLOAT:
        return (float)(1);
      case DOUBLE:
        return (double)(1);
      case REF:
      }
      throw getUOE(dataType);
    }
  },
  MaxBooleanPlus1{
    @Override Object getVal(DataType dataType){
      switch(dataType){
      case BYTE:
        return (byte)(2);
      case CHAR:
        return (char)(2);
      case SHORT:
        return (short)(2);
      case INT:
        return (int)(2);
      case LONG:
        return (long)(2);
      case FLOAT:
        return (float)(2);
      case DOUBLE:
        return (double)(2);
      case BOOLEAN:
      case REF:
      }
      throw getUOE(dataType);
    }
  },
  MaxByte{
    @Override Object getVal(DataType dataType){
      switch(dataType){
      case BYTE:
        return (byte)(Byte.MAX_VALUE);
      case CHAR:
        return (char)(Byte.MAX_VALUE);
      case SHORT:
        return (short)(Byte.MAX_VALUE);
      case INT:
        return (int)(Byte.MAX_VALUE);
      case LONG:
        return (long)(Byte.MAX_VALUE);
      case FLOAT:
        return (float)(Byte.MAX_VALUE);
      case DOUBLE:
        return (double)(Byte.MAX_VALUE);
      case BOOLEAN:
      case REF:
      }
      throw getUOE(dataType);
    }
  },
  MaxBytePlus1{
    @Override Object getVal(DataType dataType){
      switch(dataType){
      case CHAR:
        return (char)(Byte.MAX_VALUE + 1);
      case SHORT:
        return (short)(Byte.MAX_VALUE + 1);
      case INT:
        return (int)(Byte.MAX_VALUE + 1);
      case LONG:
        return (long)(Byte.MAX_VALUE + 1);
      case FLOAT:
        return (float)(Byte.MAX_VALUE + 1);
      case DOUBLE:
        return (double)(Byte.MAX_VALUE + 1);
      case BOOLEAN:
      case BYTE:
      case REF:
      }
      throw getUOE(dataType);
    }
  },
  MinByte{
    @Override Object getVal(DataType dataType){
      switch(dataType){
      case BYTE:
        return (byte)(Byte.MIN_VALUE);
      case SHORT:
        return (short)(Byte.MIN_VALUE);
      case INT:
        return (int)(Byte.MIN_VALUE);
      case LONG:
        return (long)(Byte.MIN_VALUE);
      case FLOAT:
        return (float)(Byte.MIN_VALUE);
      case DOUBLE:
        return (double)(Byte.MIN_VALUE);
      case BOOLEAN:
      case CHAR:
      case REF:
      }
      throw getUOE(dataType);
    }
  },
  MinByteMinus1{
    @Override Object getVal(DataType dataType){
      switch(dataType){
      case SHORT:
        return (short)(Byte.MIN_VALUE - 1);
      case INT:
        return (int)(Byte.MIN_VALUE - 1);
      case LONG:
        return (long)(Byte.MIN_VALUE - 1);
      case FLOAT:
        return (float)(Byte.MIN_VALUE - 1);
      case DOUBLE:
        return (double)(Byte.MIN_VALUE - 1);
      case BOOLEAN:
      case BYTE:
      case CHAR:
      case REF:
      }
      throw getUOE(dataType);
    }
  },
  MaxChar{
    @Override Object getVal(DataType dataType){
      switch(dataType){
      case CHAR:
        return (char)(Character.MAX_VALUE);
      case INT:
        return (int)(Character.MAX_VALUE);
      case LONG:
        return (long)(Character.MAX_VALUE);
      case FLOAT:
        return (float)(Character.MAX_VALUE);
      case DOUBLE:
        return (double)(Character.MAX_VALUE);
      case BOOLEAN:
      case BYTE:
      case SHORT:
      case REF:
      }
      throw getUOE(dataType);
    }
  },
  MaxCharPlus1{
    @Override Object getVal(DataType dataType){
      switch(dataType){
      case INT:
        return (int)(Character.MAX_VALUE + 1);
      case LONG:
        return (long)(Character.MAX_VALUE + 1);
      case FLOAT:
        return (float)(Character.MAX_VALUE + 1);
      case DOUBLE:
        return (double)(Character.MAX_VALUE + 1);
      case BOOLEAN:
      case BYTE:
      case CHAR:
      case SHORT:
      case REF:
      }
      throw getUOE(dataType);
    }
  },
  MaxShort{
    @Override Object getVal(DataType dataType){
      switch(dataType){
      case CHAR:
        return (char)Short.MIN_VALUE;
      case SHORT:
        return (short)Short.MIN_VALUE;
      case INT:
        return (int)Short.MIN_VALUE;
      case LONG:
        return (long)(Short.MIN_VALUE);
      case FLOAT:
        return (float)(Short.MIN_VALUE);
      case DOUBLE:
        return (double)(Short.MIN_VALUE);
      case BOOLEAN:
      case BYTE:
      case REF:
      }
      throw getUOE(dataType);
    }
  },
  MaxShortPlus1{
    @Override Object getVal(DataType dataType){
      switch(dataType){
      case INT:
        return Short.MAX_VALUE + 1;
      case LONG:
        return (long)(Short.MAX_VALUE + 1);
      case FLOAT:
        return (float)(Short.MAX_VALUE + 1);
      case DOUBLE:
        return (double)(Short.MAX_VALUE + 1);
      case BOOLEAN:
      case BYTE:
      case CHAR:
      case SHORT:
      case REF:
      }
      throw getUOE(dataType);
    }
  },
  MinShort{
    @Override Object getVal(DataType dataType){
      switch(dataType){
      case SHORT:
        return (short)Short.MIN_VALUE;
      case INT:
        return (int)Short.MIN_VALUE;
      case LONG:
        return (long)(Short.MIN_VALUE);
      case FLOAT:
        return (float)(Short.MIN_VALUE);
      case DOUBLE:
        return (double)(Short.MIN_VALUE);
      case BOOLEAN:
      case BYTE:
      case CHAR:
      case REF:
      }
      throw getUOE(dataType);
    }
  },
  MinShortMinus1{
    @Override Object getVal(DataType dataType){
      switch(dataType){
      case INT:
        return Short.MIN_VALUE - 1;
      case LONG:
        return (long)(Short.MIN_VALUE - 1);
      case FLOAT:
        return (float)(Short.MIN_VALUE - 1);
      case DOUBLE:
        return (double)(Short.MIN_VALUE - 1);
      case BOOLEAN:
      case BYTE:
      case CHAR:
      case SHORT:
      case REF:
      }
      throw getUOE(dataType);
    }
  },
  MaxSafeInt{
    @Override Object getVal(DataType dataType){
      switch(dataType){
      case INT:
        return TypeUtil.MAX_SAFE_INT;
      case LONG:
        return (long)(TypeUtil.MAX_SAFE_INT);
      case FLOAT:
        return (float)(TypeUtil.MAX_SAFE_INT);
      case DOUBLE:
        return (double)(TypeUtil.MAX_SAFE_INT);
      case BOOLEAN:
      case BYTE:
      case CHAR:
      case SHORT:
      case REF:
      }
      throw getUOE(dataType);
    }
  },
  MaxSafeIntPlus1{
    @Override Object getVal(DataType dataType){
      switch(dataType){
      case INT:
        return TypeUtil.MAX_SAFE_INT + 1;
      case LONG:
        return (long)(TypeUtil.MAX_SAFE_INT + 1);
      case DOUBLE:
        return (double)(TypeUtil.MAX_SAFE_INT + 1);
      case BOOLEAN:
      case BYTE:
      case CHAR:
      case SHORT:
      case FLOAT:
      case REF:
      }
      throw getUOE(dataType);
    }
  },
  MinSafeInt{
    @Override Object getVal(DataType dataType){
      switch(dataType){
      case INT:
        return TypeUtil.MIN_SAFE_INT;
      case LONG:
        return (long)(TypeUtil.MIN_SAFE_INT);
      case FLOAT:
        return (float)(TypeUtil.MIN_SAFE_INT);
      case DOUBLE:
        return (double)(TypeUtil.MIN_SAFE_INT);
      case BOOLEAN:
      case BYTE:
      case CHAR:
      case SHORT:
      case REF:
      }
      throw getUOE(dataType);
    }
  },
  MinSafeIntMinus1{
    @Override Object getVal(DataType dataType){
      switch(dataType){
      case INT:
        return TypeUtil.MIN_SAFE_INT - 1;
      case LONG:
        return (long)(TypeUtil.MIN_SAFE_INT - 1);
      case DOUBLE:
        return (double)(TypeUtil.MIN_SAFE_INT - 1);
      case BOOLEAN:
      case BYTE:
      case CHAR:
      case SHORT:
      case FLOAT:
      case REF:
      }
      throw getUOE(dataType);
    }
  },
  MaxInt{
    @Override Object getVal(DataType dataType){
      switch(dataType){
      case INT:
        return Integer.MAX_VALUE;
      case LONG:
        return (long)Integer.MAX_VALUE;
      case DOUBLE:
        return (double)Integer.MAX_VALUE;
      case BOOLEAN:
      case BYTE:
      case CHAR:
      case SHORT:
      case FLOAT:
      case REF:
      }
      throw getUOE(dataType);
    }
  },
  MaxIntPlus1{
    @Override Object getVal(DataType dataType){
      switch(dataType){
      case LONG:
        return ((long)Integer.MAX_VALUE) + 1;
      case DOUBLE:
        return (double)(((long)Integer.MAX_VALUE) + 1);
      case BOOLEAN:
      case BYTE:
      case CHAR:
      case SHORT:
      case INT:
      case FLOAT:
      case REF:
      }
      throw getUOE(dataType);
    }
  },
  MinInt{
    @Override Object getVal(DataType dataType){
      switch(dataType){
      case INT:
        return Integer.MIN_VALUE;
      case LONG:
        return (long)Integer.MIN_VALUE;
      case DOUBLE:
        return (double)Integer.MIN_VALUE;
      case BOOLEAN:
      case BYTE:
      case CHAR:
      case SHORT:
      case FLOAT:
      case REF:
      }
      throw getUOE(dataType);
    }
  },
  MinIntMinus1{
    @Override Object getVal(DataType dataType){
      switch(dataType){
      case LONG:
        return ((long)Integer.MIN_VALUE) - 1;
      case DOUBLE:
        return (double)(((long)Integer.MIN_VALUE) - 1);
      case BOOLEAN:
      case BYTE:
      case CHAR:
      case SHORT:
      case INT:
      case FLOAT:
      case REF:
      }
      throw getUOE(dataType);
    }
  },
  MaxSafeLong{
    @Override Object getVal(DataType dataType){
      switch(dataType){
      case LONG:
        return TypeUtil.MAX_SAFE_LONG;
      case DOUBLE:
        return (double)TypeUtil.MAX_SAFE_LONG;
      case BOOLEAN:
      case BYTE:
      case CHAR:
      case SHORT:
      case INT:
      case FLOAT:
      case REF:
      }
      throw getUOE(dataType);
    }
  },
  MaxSafeLongPlus1{
    @Override Object getVal(DataType dataType){
      switch(dataType){
      case LONG:
        return TypeUtil.MAX_SAFE_LONG + 1;
      case BOOLEAN:
      case BYTE:
      case CHAR:
      case SHORT:
      case INT:
      case FLOAT:
      case DOUBLE:
      case REF:
      }
      throw getUOE(dataType);
    }
  },
  MinSafeLong{
    @Override Object getVal(DataType dataType){
      switch(dataType){
      case LONG:
        return TypeUtil.MIN_SAFE_LONG;
      case DOUBLE:
        return (double)TypeUtil.MIN_SAFE_LONG;
      case BOOLEAN:
      case BYTE:
      case CHAR:
      case SHORT:
      case INT:
      case FLOAT:
      case REF:
      }
      throw getUOE(dataType);
    }
  },
  MinSafeLongMinus1{
    @Override Object getVal(DataType dataType){
      switch(dataType){
      case LONG:
        return TypeUtil.MIN_SAFE_LONG - 1;
      case BOOLEAN:
      case BYTE:
      case CHAR:
      case SHORT:
      case INT:
      case FLOAT:
      case DOUBLE:
      case REF:
      }
      throw getUOE(dataType);
    }
  },
  MaxLong{
    @Override Object getVal(DataType dataType){
      switch(dataType){
      case LONG:
        return Long.MAX_VALUE;
      case BOOLEAN:
      case BYTE:
      case CHAR:
      case SHORT:
      case INT:
      case FLOAT:
      case DOUBLE:
      case REF:
      }
      throw getUOE(dataType);
    }
  },
  MinLong{
    @Override Object getVal(DataType dataType){
      switch(dataType){
      case LONG:
        return Long.MIN_VALUE;
      case BOOLEAN:
      case BYTE:
      case CHAR:
      case SHORT:
      case INT:
      case FLOAT:
      case DOUBLE:
      case REF:
      }
      throw getUOE(dataType);
    }
  },
  MaxFloat{
    @Override Object getVal(DataType dataType){
      switch(dataType){
      case FLOAT:
        return (float)Float.MAX_VALUE;
      case DOUBLE:
        return (double)Float.MAX_VALUE;
      case BOOLEAN:
      case BYTE:
      case CHAR:
      case SHORT:
      case INT:
      case LONG:
      case REF:
      }
      throw getUOE(dataType);
    }
  },
  MaxFloatPlusEpsilon{
    @Override Object getVal(DataType dataType){
      switch(dataType){
      case DOUBLE:
        return ((double)Float.MAX_VALUE) + Double.MIN_VALUE;
      case BOOLEAN:
      case BYTE:
      case CHAR:
      case SHORT:
      case INT:
      case LONG:
      case FLOAT:
      case REF:
      }
      throw getUOE(dataType);
    }
  },
  MinFloat{
    @Override Object getVal(DataType dataType){
      switch(dataType){
      case FLOAT:
        return (float)Float.MIN_VALUE;
      case DOUBLE:
        return (double)Float.MIN_VALUE;
      case BOOLEAN:
      case BYTE:
      case CHAR:
      case SHORT:
      case INT:
      case LONG:
      case REF:
      }
      throw getUOE(dataType);
    }
  },
  MinFloatMinusEpsilon{
    @Override Object getVal(DataType dataType){
      switch(dataType){
      case DOUBLE:
        return ((double)Float.MIN_VALUE) - Double.MIN_VALUE;
      case BOOLEAN:
      case BYTE:
      case CHAR:
      case SHORT:
      case INT:
      case LONG:
      case FLOAT:
      case REF:
      }
      throw getUOE(dataType);
    }
  },
  MaxDouble{
    @Override Object getVal(DataType dataType){
      switch(dataType){
      case DOUBLE:
        return Double.MAX_VALUE;
      case BOOLEAN:
      case BYTE:
      case CHAR:
      case SHORT:
      case INT:
      case LONG:
      case FLOAT:
      case REF:
      }
      throw getUOE(dataType);
    }
  },
  MinDouble{
    @Override Object getVal(DataType dataType){
      switch(dataType){
      case DOUBLE:
        return Double.MIN_VALUE;
      case BOOLEAN:
      case BYTE:
      case CHAR:
      case SHORT:
      case INT:
      case LONG:
      case FLOAT:
      case REF:
      }
      throw getUOE(dataType);
    }
  },
  PosInfinity{
    @Override Object getVal(DataType dataType){
      switch(dataType){
      case FLOAT:
        return Float.POSITIVE_INFINITY;
      case DOUBLE:
        return Double.POSITIVE_INFINITY;
      case BOOLEAN:
      case BYTE:
      case CHAR:
      case SHORT:
      case INT:
      case LONG:
      case REF:
      }
      throw getUOE(dataType);
    }
  },
  NegInfinity{
    @Override Object getVal(DataType dataType){
      switch(dataType){
      case FLOAT:
        return Float.NEGATIVE_INFINITY;
      case DOUBLE:
        return Double.NEGATIVE_INFINITY;
      case BOOLEAN:
      case BYTE:
      case CHAR:
      case SHORT:
      case INT:
      case LONG:
      case REF:
      }
      throw getUOE(dataType);
    }
  },
  NaN{
    @Override Object getVal(DataType dataType){
      switch(dataType){
      case FLOAT:
        return Float.NaN;
      case DOUBLE:
        return Double.NaN;
      case BOOLEAN:
      case BYTE:
      case CHAR:
      case SHORT:
      case INT:
      case LONG:
      case REF:
      }
      throw getUOE(dataType);
    }
  };
  public final Set<DataType> validDataTypes;
  QueryVal(){
    this.validDataTypes=initValidDataTypes(this);
  }
  abstract Object getVal(DataType dataType);
  private static UnsupportedOperationException getUOE(DataType dataType){
    return new UnsupportedOperationException("Invalid dataType " + dataType);
  }
  private static Set<DataType> initValidDataTypes(QueryVal queryVal){
    switch(queryVal){
    case MaxLong:
    case MinLong:
    case MaxSafeLongPlus1:
    case MinSafeLongMinus1:
      return Set.of(DataType.LONG);
    case MaxDouble:
    case MinDouble:
    case MaxFloatPlusEpsilon:
    case MinFloatMinusEpsilon:
      return Set.of(DataType.DOUBLE);
    case MinSafeLong:
    case MaxSafeLong:
    case MaxIntPlus1:
    case MinIntMinus1:
      return Set.of(DataType.DOUBLE,DataType.LONG);
    case NaN:
    case Neg0:
    case NegInfinity:
    case PosInfinity:
    case MaxFloat:
    case MinFloat:
      return Set.of(DataType.DOUBLE,DataType.FLOAT);
    case MaxInt:
    case MinInt:
    case MaxSafeIntPlus1:
    case MinSafeIntMinus1:
      return Set.of(DataType.DOUBLE,DataType.LONG,DataType.INT);
    case MinSafeInt:
    case MaxSafeInt:
    case MaxCharPlus1:
    case MinShortMinus1:
      return Set.of(DataType.DOUBLE,DataType.FLOAT,DataType.LONG,DataType.INT);
    case MaxChar:
    case MaxShortPlus1:
      return Set.of(DataType.DOUBLE,DataType.FLOAT,DataType.LONG,DataType.INT,DataType.CHAR);
    case MinShort:
    case MinByteMinus1:
      return Set.of(DataType.DOUBLE,DataType.FLOAT,DataType.LONG,DataType.INT,DataType.SHORT);
    case MinByte:
    case Neg1:
      return Set.of(DataType.DOUBLE,DataType.FLOAT,DataType.LONG,DataType.INT,DataType.SHORT,DataType.BYTE);
    case MaxShort:
    case MaxBytePlus1:
      return Set.of(DataType.DOUBLE,DataType.FLOAT,DataType.LONG,DataType.INT,DataType.SHORT,DataType.CHAR);
    case MaxByte:
    case MaxBooleanPlus1:
      return Set.of(DataType.DOUBLE,DataType.FLOAT,DataType.LONG,DataType.INT,DataType.SHORT,DataType.CHAR,
          DataType.BYTE);
    case MaxBoolean:
    case Pos0:
      return Set.of(DataType.DOUBLE,DataType.FLOAT,DataType.LONG,DataType.INT,DataType.SHORT,DataType.CHAR,
          DataType.BYTE,DataType.BOOLEAN);
    }
    throw new UnsupportedOperationException("Unknown queryVal " + queryVal);
  }
}
