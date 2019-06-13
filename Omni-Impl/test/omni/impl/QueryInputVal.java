package omni.impl;

import java.util.Set;
import omni.util.TypeUtil;

public enum QueryInputVal{
    Null(0L) {
        @Override
        public Set<QueryCastType> getValidCasts(DataType inputType){
            switch(inputType) {
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case DOUBLE:
            case FLOAT:
            case INT:
            case LONG:
            case SHORT:
                return Set.of(QueryCastType.ToBoxed);
            case REF:
                return Set.of(QueryCastType.ToObject);
            }
            throw new Error("Unknown inputType "+inputType);
        }
        @Override
        public Object getInputVal(DataType inputType){
            return null;
        }
    },
    NonNull(1L) {
        @Override
        public Set<QueryCastType> getValidCasts(DataType inputType){
            switch(inputType) {
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case DOUBLE:
            case FLOAT:
            case INT:
            case LONG:
            case SHORT:
                return Set.of();
            case REF:
                return Set.of(QueryCastType.ToObject);
            }
            throw new Error("Unknown inputType "+inputType);
        }
        @Override
        public Object getInputVal(DataType inputType){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public Object getNonMatchingVal(DataType collectionType,DataType inputType,QueryCastType queryCastType){
            // TODO
            return null;
        }
        @Override
        public Object getMatchingVal(DataType collectionType,DataType inputType,QueryCastType queryCastType){
            // TODO
            return null;
        }
    },
    Pos0(0L){
        @Override
        public Object getInputVal(DataType inputType){
            switch(inputType){
            case DOUBLE:
                return (double)bits;
            case FLOAT:
                return (float)bits;
            case LONG:
                return (long)bits;
            case INT:
                return (int)bits;
            case SHORT:
                return (short)bits;
            case CHAR:
                return (char)bits;
            case BYTE:
                return (byte)bits;
            case BOOLEAN:
                return false;
            case REF:
            }
            throw new UnsupportedOperationException("Invalid inputType " + inputType + " for QueryInputVal " + this);
        }
        @Override
        public Object getNonMatchingVal(DataType collectionType,DataType inputType,QueryCastType queryCastType){
            switch(collectionType){
            case BOOLEAN:
                return true;
            case BYTE:
                return (byte)1;
            case CHAR:
                return (char)1;
            case DOUBLE:
                return (double)1;
            case FLOAT:
                return (float)1;
            case INT:
                return (int)1;
            case LONG:
                return (long)1;
            case REF:
                return new Object();
            case SHORT:
                return (short)1;
            default:
            }
            throw new UnsupportedOperationException("collectionType = " + collectionType + ", inputType = " + inputType
                    + " queryCastType = " + queryCastType);
        }
    },
    Pos1(1L){
        @Override
        public Object getInputVal(DataType inputType){
            switch(inputType){
            case DOUBLE:
                return (double)bits;
            case FLOAT:
                return (float)bits;
            case LONG:
                return (long)bits;
            case INT:
                return (int)bits;
            case SHORT:
                return (short)bits;
            case CHAR:
                return (char)bits;
            case BYTE:
                return (byte)bits;
            case BOOLEAN:
                return true;
            case REF:
            }
            throw new UnsupportedOperationException("Invalid inputType " + inputType + " for QueryInputVal " + this);
        }
    },
    Pos2(2L){
        @Override
        public Object getInputVal(DataType inputType){
            switch(inputType){
            case DOUBLE:
                return (double)bits;
            case FLOAT:
                return (float)bits;
            case LONG:
                return (long)bits;
            case INT:
                return (int)bits;
            case SHORT:
                return (short)bits;
            case CHAR:
                return (char)bits;
            case BYTE:
                return (byte)bits;
            case BOOLEAN:
            case REF:
            }
            throw new UnsupportedOperationException("Invalid inputType " + inputType + " for QueryInputVal " + this);
        }
    },
    Neg1(-1L){
        @Override
        public Object getInputVal(DataType inputType){
            switch(inputType){
            case DOUBLE:
                return (double)bits;
            case FLOAT:
                return (float)bits;
            case LONG:
                return (long)bits;
            case INT:
                return (int)bits;
            case SHORT:
                return (short)bits;
            case BYTE:
                return (byte)bits;
            case CHAR:
            case BOOLEAN:
            case REF:
            }
            throw new UnsupportedOperationException("Invalid inputType " + inputType + " for QueryInputVal " + this);
        }
    },
    ByteMax(Byte.MAX_VALUE){
        @Override
        public Object getInputVal(DataType inputType){
            switch(inputType){
            case DOUBLE:
                return (double)bits;
            case FLOAT:
                return (float)bits;
            case LONG:
                return (long)bits;
            case INT:
                return (int)bits;
            case SHORT:
                return (short)bits;
            case CHAR:
                return (char)bits;
            case BYTE:
                return (byte)bits;
            case BOOLEAN:
            case REF:
            }
            throw new UnsupportedOperationException("Invalid inputType " + inputType + " for QueryInputVal " + this);
        }
    },
    ByteMin(Byte.MIN_VALUE){
        @Override
        public Object getInputVal(DataType inputType){
            switch(inputType){
            case DOUBLE:
                return (double)bits;
            case FLOAT:
                return (float)bits;
            case LONG:
                return (long)bits;
            case INT:
                return (int)bits;
            case SHORT:
                return (short)bits;
            case BYTE:
                return (byte)bits;
            case BOOLEAN:
            case CHAR:
            case REF:
            }
            throw new UnsupportedOperationException("Invalid inputType " + inputType + " for QueryInputVal " + this);
        }
    },
    ByteMaxPlus1(Byte.MAX_VALUE + 1){
        @Override
        public Object getInputVal(DataType inputType){
            switch(inputType){
            case DOUBLE:
                return (double)bits;
            case FLOAT:
                return (float)bits;
            case LONG:
                return (long)bits;
            case INT:
                return (int)bits;
            case SHORT:
                return (short)bits;
            case CHAR:
                return (char)bits;
            case BOOLEAN:
            case BYTE:
            case REF:
            }
            throw new UnsupportedOperationException("Invalid inputType " + inputType + " for QueryInputVal " + this);
        }
    },
    ByteMinMinus1(Byte.MIN_VALUE - 1){
        @Override
        public Object getInputVal(DataType inputType){
            switch(inputType){
            case DOUBLE:
                return (double)bits;
            case FLOAT:
                return (float)bits;
            case LONG:
                return (long)bits;
            case INT:
                return (int)bits;
            case SHORT:
                return (short)bits;
            case CHAR:
            case BOOLEAN:
            case BYTE:
            case REF:
            }
            throw new UnsupportedOperationException("Invalid inputType " + inputType + " for QueryInputVal " + this);
        }
    },
    CharMax(Character.MAX_VALUE){
        @Override
        public Object getInputVal(DataType inputType){
            switch(inputType){
            case DOUBLE:
                return (double)bits;
            case FLOAT:
                return (float)bits;
            case LONG:
                return (long)bits;
            case INT:
                return (int)bits;
            case CHAR:
                return (char)bits;
            case SHORT:
            case BOOLEAN:
            case BYTE:
            case REF:
            }
            throw new UnsupportedOperationException("Invalid inputType " + inputType + " for QueryInputVal " + this);
        }
    },
    CharMaxPlus1(Character.MAX_VALUE + 1){
        @Override
        public Object getInputVal(DataType inputType){
            switch(inputType){
            case DOUBLE:
                return (double)bits;
            case FLOAT:
                return (float)bits;
            case LONG:
                return (long)bits;
            case INT:
                return (int)bits;
            case CHAR:
            case SHORT:
            case BOOLEAN:
            case BYTE:
            case REF:
            }
            throw new UnsupportedOperationException("Invalid inputType " + inputType + " for QueryInputVal " + this);
        }
    },
    ShortMax(Short.MAX_VALUE){
        @Override
        public Object getInputVal(DataType inputType){
            switch(inputType){
            case DOUBLE:
                return (double)bits;
            case FLOAT:
                return (float)bits;
            case LONG:
                return (long)bits;
            case INT:
                return (int)bits;
            case SHORT:
                return (short)bits;
            case CHAR:
                return (char)bits;
            case BOOLEAN:
            case BYTE:
            case REF:
            }
            throw new UnsupportedOperationException("Invalid inputType " + inputType + " for QueryInputVal " + this);
        }
    },
    ShortMin(Short.MIN_VALUE){
        @Override
        public Object getInputVal(DataType inputType){
            switch(inputType){
            case DOUBLE:
                return (double)bits;
            case FLOAT:
                return (float)bits;
            case LONG:
                return (long)bits;
            case INT:
                return (int)bits;
            case SHORT:
                return (short)bits;
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case REF:
            }
            throw new UnsupportedOperationException("Invalid inputType " + inputType + " for QueryInputVal " + this);
        }
    },
    ShortMaxPlus1(Short.MAX_VALUE + 1){
        @Override
        public Object getInputVal(DataType inputType){
            switch(inputType){
            case DOUBLE:
                return (double)bits;
            case FLOAT:
                return (float)bits;
            case LONG:
                return (long)bits;
            case INT:
                return (int)bits;
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case REF:
            case SHORT:
            }
            throw new UnsupportedOperationException("Invalid inputType " + inputType + " for QueryInputVal " + this);
        }
    },
    ShortMinMinus1(Short.MIN_VALUE - 1){
        @Override
        public Object getInputVal(DataType inputType){
            switch(inputType){
            case DOUBLE:
                return (double)bits;
            case FLOAT:
                return (float)bits;
            case LONG:
                return (long)bits;
            case INT:
                return (int)bits;
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case REF:
            case SHORT:
            }
            throw new UnsupportedOperationException("Invalid inputType " + inputType + " for QueryInputVal " + this);
        }
    },
    IntMax(Integer.MAX_VALUE){
        @Override
        public Object getInputVal(DataType inputType){
            switch(inputType){
            case DOUBLE:
                return (double)bits;
            case LONG:
                return (long)bits;
            case INT:
                return (int)bits;
            case FLOAT:
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case REF:
            case SHORT:
            }
            throw new UnsupportedOperationException("Invalid inputType " + inputType + " for QueryInputVal " + this);
        }
    },
    IntMin(Integer.MIN_VALUE){
        @Override
        public Object getInputVal(DataType inputType){
            switch(inputType){
            case DOUBLE:
                return (double)bits;
            case LONG:
                return (long)bits;
            case INT:
                return (int)bits;
            case FLOAT:
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case REF:
            case SHORT:
            }
            throw new UnsupportedOperationException("Invalid inputType " + inputType + " for QueryInputVal " + this);
        }
    },
    IntMaxPlus1((long)Integer.MAX_VALUE + 1){
        @Override
        public Object getInputVal(DataType inputType){
            switch(inputType){
            case DOUBLE:
                return (double)bits;
            case LONG:
                return (long)bits;
            case FLOAT:
            case INT:
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case REF:
            case SHORT:
            }
            throw new UnsupportedOperationException("Invalid inputType " + inputType + " for QueryInputVal " + this);
        }
    },
    IntMinMinus1((long)Integer.MIN_VALUE - 1){
        @Override
        public Object getInputVal(DataType inputType){
            switch(inputType){
            case DOUBLE:
                return (double)bits;
            case LONG:
                return (long)bits;
            case FLOAT:
            case INT:
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case REF:
            case SHORT:
            }
            throw new UnsupportedOperationException("Invalid inputType " + inputType + " for QueryInputVal " + this);
        }
    },
    LongMax(Long.MAX_VALUE){
        @Override
        public Object getInputVal(DataType inputType){
            switch(inputType){
            case LONG:
                return (long)bits;
            case DOUBLE:
            case FLOAT:
            case INT:
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case REF:
            case SHORT:
            }
            throw new UnsupportedOperationException("Invalid inputType " + inputType + " for QueryInputVal " + this);
        }
    },
    LongMin(Long.MIN_VALUE){
        @Override
        public Object getInputVal(DataType inputType){
            switch(inputType){
            case LONG:
                return (long)bits;
            case DOUBLE:
            case FLOAT:
            case INT:
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case REF:
            case SHORT:
            }
            throw new UnsupportedOperationException("Invalid inputType " + inputType + " for QueryInputVal " + this);
        }
    },
    MaxSafeInt(TypeUtil.MAX_SAFE_INT){
        @Override
        public Object getInputVal(DataType inputType){
            switch(inputType){
            case DOUBLE:
                return (double)bits;
            case FLOAT:
                return (float)bits;
            case LONG:
                return (long)bits;
            case INT:
                return (int)bits;
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case REF:
            case SHORT:
            }
            throw new UnsupportedOperationException("Invalid inputType " + inputType + " for QueryInputVal " + this);
        }
    },
    MinSafeInt(TypeUtil.MIN_SAFE_INT){
        @Override
        public Object getInputVal(DataType inputType){
            switch(inputType){
            case DOUBLE:
                return (double)bits;
            case FLOAT:
                return (float)bits;
            case LONG:
                return (long)bits;
            case INT:
                return (int)bits;
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case REF:
            case SHORT:
            }
            throw new UnsupportedOperationException("Invalid inputType " + inputType + " for QueryInputVal " + this);
        }
    },
    MaxSafeIntPlus1(TypeUtil.MAX_SAFE_INT + 1){
        @Override
        public Object getInputVal(DataType inputType){
            switch(inputType){
            case DOUBLE:
                return (double)bits;
            case LONG:
                return (long)bits;
            case INT:
                return (int)bits;
            default:
            }
            throw new UnsupportedOperationException("Invalid inputType " + inputType + " for QueryInputVal " + this);
        }
    },
    MinSafeIntMinus1(TypeUtil.MIN_SAFE_INT - 1){
        @Override
        public Object getInputVal(DataType inputType){
            switch(inputType){
            case DOUBLE:
                return (double)bits;
            case LONG:
                return (long)bits;
            case INT:
                return (int)bits;
            default:
            }
            throw new UnsupportedOperationException("Invalid inputType " + inputType + " for QueryInputVal " + this);
        }
    },
    MaxSafeLong(TypeUtil.MAX_SAFE_LONG){
        @Override
        public Object getInputVal(DataType inputType){
            switch(inputType){
            case DOUBLE:
                return (double)bits;
            case LONG:
                return (long)bits;
            default:
            }
            throw new UnsupportedOperationException("Invalid inputType " + inputType + " for QueryInputVal " + this);
        }
    },
    MinSafeLong(TypeUtil.MIN_SAFE_LONG){
        @Override
        public Object getInputVal(DataType inputType){
            switch(inputType){
            case DOUBLE:
                return (double)bits;
            case LONG:
                return (long)bits;
            default:
            }
            throw new UnsupportedOperationException("Invalid inputType " + inputType + " for QueryInputVal " + this);
        }
    },
    MaxSafeLongPlus1(TypeUtil.MAX_SAFE_LONG + 1){
        @Override
        public Object getInputVal(DataType inputType){
            switch(inputType){
            case LONG:
                return (long)bits;
            default:
            }
            throw new UnsupportedOperationException("Invalid inputType " + inputType + " for QueryInputVal " + this);
        }
    },
    MinSafeLongMinus1(TypeUtil.MIN_SAFE_LONG - 1){
        @Override
        public Object getInputVal(DataType inputType){
            switch(inputType){
            case LONG:
                return (long)bits;
            default:
            }
            throw new UnsupportedOperationException("Invalid inputType " + inputType + " for QueryInputVal " + this);
        }
    },
    FloatMaxValue(Float.floatToRawIntBits(Float.MAX_VALUE)){
        @Override
        public Object getInputVal(DataType inputType){
            switch(inputType){
            case DOUBLE:
                return (double)Float.intBitsToFloat((int)bits);
            case FLOAT:
                return (float)Float.intBitsToFloat((int)bits);
            default:
            }
            throw new UnsupportedOperationException("Invalid inputType " + inputType + " for QueryInputVal " + this);
        }
    },
    FloatMinValue(Float.floatToRawIntBits(Float.MIN_VALUE)){
        @Override
        public Object getInputVal(DataType inputType){
            switch(inputType){
            case DOUBLE:
                return (double)Float.intBitsToFloat((int)bits);
            case FLOAT:
                return (float)Float.intBitsToFloat((int)bits);
            default:
            }
            throw new UnsupportedOperationException("Invalid inputType " + inputType + " for QueryInputVal " + this);
        }
    },
    FloatMinNormal(Float.floatToRawIntBits(Float.MIN_NORMAL)){
        @Override
        public Object getInputVal(DataType inputType){
            switch(inputType){
            case DOUBLE:
                return (double)Float.intBitsToFloat((int)bits);
            case FLOAT:
                return (float)Float.intBitsToFloat((int)bits);
            default:
            }
            throw new UnsupportedOperationException("Invalid inputType " + inputType + " for QueryInputVal " + this);
        }
    },
    DoubleMaxValue(Double.doubleToRawLongBits(Double.MAX_VALUE)){
        @Override
        public Object getInputVal(DataType inputType){
            switch(inputType){
            case DOUBLE:
                return (double)Double.longBitsToDouble(bits);
            default:
            }
            throw new UnsupportedOperationException("Invalid inputType " + inputType + " for QueryInputVal " + this);
        }
    },
    DoubleMinValue(Double.doubleToRawLongBits(Double.MIN_VALUE)){
        @Override
        public Object getInputVal(DataType inputType){
            switch(inputType){
            case DOUBLE:
                return (double)Double.longBitsToDouble(bits);
            default:
            }
            throw new UnsupportedOperationException("Invalid inputType " + inputType + " for QueryInputVal " + this);
        }
    },
    DoubleMinNormal(Double.doubleToRawLongBits(Double.MIN_NORMAL)){
        @Override
        public Object getInputVal(DataType inputType){
            switch(inputType){
            case DOUBLE:
                return (double)Double.longBitsToDouble(bits);
            default:
            }
            throw new UnsupportedOperationException("Invalid inputType " + inputType + " for QueryInputVal " + this);
        }
    },
    PosInfinity(Double.doubleToRawLongBits(Double.POSITIVE_INFINITY)){
        @Override
        public Object getInputVal(DataType inputType){
            switch(inputType){
            case DOUBLE:
                return (double)Double.longBitsToDouble(bits);
            case FLOAT:
                return (float)Double.longBitsToDouble(bits);
            default:
            }
            throw new UnsupportedOperationException("Invalid inputType " + inputType + " for QueryInputVal " + this);
        }
    },
    NegInfinity(Double.doubleToRawLongBits(Double.NEGATIVE_INFINITY)){
        @Override
        public Object getInputVal(DataType inputType){
            switch(inputType){
            case DOUBLE:
                return (double)Double.longBitsToDouble(bits);
            case FLOAT:
                return (float)Double.longBitsToDouble(bits);
            default:
            }
            throw new UnsupportedOperationException("Invalid inputType " + inputType + " for QueryInputVal " + this);
        }
    },
    NaN(Double.doubleToRawLongBits(Double.NaN)){
        @Override
        public Object getInputVal(DataType inputType){
            switch(inputType){
            case DOUBLE:
                return (double)Double.longBitsToDouble(bits);
            case FLOAT:
                return (float)Double.longBitsToDouble(bits);
            default:
            }
            throw new UnsupportedOperationException("Invalid inputType " + inputType + " for QueryInputVal " + this);
        }
    };

    public final long bits;

    public final Set<DataType> safeCastTo;


    private QueryInputVal(long bits){
        this.bits=bits;
        this.safeCastTo=initSafeCastTo(this);
    }
    public abstract Object getInputVal(DataType inputType);
    public Object getMatchingVal(DataType collectionType,DataType inputType,QueryCastType queryCastType){
        if(collectionType == DataType.REF){
            return getInputVal(inputType);
        }
        return getInputVal(collectionType);
    }
    public Object getNonMatchingVal(DataType collectionType,DataType inputType,QueryCastType queryCastType){
        switch(collectionType){
        case BOOLEAN:
            return false;
        case BYTE:
            return (byte)0;
        case CHAR:
            return (char)0;
        case DOUBLE:
            return (double)0;
        case FLOAT:
            return (float)0;
        case INT:
            return (int)0;
        case LONG:
            return (long)0;
        case REF:
            return new Object();
        case SHORT:
            return (short)0;
        default:
        }
        throw new UnsupportedOperationException("collectionType = " + collectionType + ", inputType = " + inputType
                + " queryCastType = " + queryCastType);
    }
    // TODO monitordObject support for NonNull

    public Set<QueryCastType> getValidCasts(DataType inputType){
        switch(inputType) {
        case BOOLEAN:
        case BYTE:
        case CHAR:
        case DOUBLE:
        case FLOAT:
        case INT:
        case LONG:
        case REF:
        case SHORT:
            return Set.of(QueryCastType.values());
        }
        throw new Error("Unknown inputType "+inputType);
    }

    private static Set<DataType> initSafeCastTo(QueryInputVal queryInputVal){
        switch(queryInputVal) {
        case Null:
            return Set.of(DataType.values());
        case Pos0:
        case Pos1:
            return Set.of(DataType.BOOLEAN,DataType.BYTE,DataType.CHAR,DataType.SHORT,DataType.INT,DataType.LONG,DataType.FLOAT,DataType.DOUBLE);
        case ByteMax:
        case Pos2:
            return Set.of(DataType.BYTE,DataType.CHAR,DataType.SHORT,DataType.INT,DataType.LONG,DataType.FLOAT,DataType.DOUBLE);
        case ByteMaxPlus1:
        case ShortMax:
            return Set.of(DataType.CHAR,DataType.SHORT,DataType.INT,DataType.LONG,DataType.FLOAT,DataType.DOUBLE);
        case ByteMin:
        case Neg1:
            return Set.of(DataType.BYTE,DataType.SHORT,DataType.INT,DataType.LONG,DataType.FLOAT,DataType.DOUBLE);
        case CharMax:
        case ShortMaxPlus1:
            return Set.of(DataType.CHAR,DataType.INT,DataType.LONG,DataType.FLOAT,DataType.DOUBLE);
        case ByteMinMinus1:
        case ShortMin:
            return Set.of(DataType.SHORT,DataType.INT,DataType.LONG,DataType.FLOAT,DataType.DOUBLE);
        case CharMaxPlus1:
        case MaxSafeInt:
        case MinSafeInt:
        case ShortMinMinus1:
            return Set.of(DataType.INT,DataType.LONG,DataType.FLOAT,DataType.DOUBLE);
        case IntMin:
        case IntMax:
        case MaxSafeIntPlus1:
        case MinSafeIntMinus1:
            return Set.of(DataType.INT,DataType.LONG,DataType.DOUBLE);
        case IntMaxPlus1:
        case IntMinMinus1:
        case MaxSafeLong:
        case MinSafeLong:
            return Set.of(DataType.LONG,DataType.DOUBLE);
        case NaN:
        case NegInfinity:
        case PosInfinity:
        case FloatMaxValue:
        case FloatMinNormal:
        case FloatMinValue:
            return Set.of(DataType.FLOAT,DataType.DOUBLE);
        case LongMax:
        case LongMin:
        case MaxSafeLongPlus1:
        case MinSafeLongMinus1:
            return Set.of(DataType.LONG);
        case DoubleMinNormal:
        case DoubleMaxValue:
        case DoubleMinValue:
            return Set.of(DataType.DOUBLE);
        case NonNull:
            return Set.of(DataType.REF);
        }
        throw new Error("Unknown queryInputVal "+queryInputVal);
    }


}
