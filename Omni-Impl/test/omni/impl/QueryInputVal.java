package omni.impl;

import java.util.Set;
import org.junit.jupiter.api.Assertions;

public enum QueryInputVal{
    Null_Val(new DataType[]{DataType.RefType},
            new QueryInputType[]{QueryInputType.ObjectType,QueryInputType.BoxedBooleanType,QueryInputType.BoxedByteType,
                    QueryInputType.BoxedCharacterType,QueryInputType.BoxedShortType,QueryInputType.BoxedIntegerType,
                    QueryInputType.BoxedLongType,QueryInputType.BoxedFloatType,QueryInputType.BoxedDoubleType}) {
        @Override
        public Object getEqualsVal(DataType dataType,QueryInputType queryInputType){
            // TODO Auto-generated method stub
            return null;
        }
    },
    True_Val(
            new DataType[]{DataType.ByteType,DataType.CharType,DataType.ShortType,DataType.IntType,DataType.LongType,
                    DataType.FloatType,DataType.DoubleType,DataType.BooleanType,DataType.RefType},
            new QueryInputType[]{QueryInputType.PrimitivebooleanType,QueryInputType.BoxedBooleanType,
                    QueryInputType.BooleanToObjectType}) {
        @Override
        public Object getEqualsVal(DataType dataType,QueryInputType queryInputType){
            // TODO Auto-generated method stub
            return null;
        }
    },
    False_Val(
            new DataType[]{DataType.ByteType,DataType.CharType,DataType.ShortType,DataType.IntType,DataType.LongType,
                    DataType.FloatType,DataType.DoubleType,DataType.BooleanType,DataType.RefType},
            new QueryInputType[]{QueryInputType.PrimitivebooleanType,QueryInputType.BoxedBooleanType,
                    QueryInputType.BooleanToObjectType}) {
        @Override
        public Object getEqualsVal(DataType dataType,QueryInputType queryInputType){
            // TODO Auto-generated method stub
            return null;
        }
    },
    Pos0_Val(
            new DataType[]{DataType.ByteType,DataType.CharType,DataType.ShortType,DataType.IntType,DataType.LongType,
                    DataType.FloatType,DataType.DoubleType,DataType.BooleanType,DataType.RefType},
            new QueryInputType[]{QueryInputType.PrimitivebyteType,QueryInputType.BoxedByteType,
                    QueryInputType.ByteToObjectType,QueryInputType.PrimitivecharType,QueryInputType.BoxedCharacterType,
                    QueryInputType.CharacterToObjectType,QueryInputType.PrimitiveshortType,
                    QueryInputType.BoxedShortType,QueryInputType.ShortToObjectType,QueryInputType.PrimitiveintType,
                    QueryInputType.BoxedIntegerType,
                    QueryInputType.IntegerToObjectType,QueryInputType.PrimitivelongType,QueryInputType.BoxedLongType,
                    QueryInputType.LongToObjectType,QueryInputType.PrimitivefloatType,QueryInputType.BoxedFloatType,
                    QueryInputType.FloatToObjectType,QueryInputType.PrimitivedoubleType,QueryInputType.BoxedDoubleType,
                    QueryInputType.DoubleToObjectType}) {
        @Override
        public Object getEqualsVal(DataType dataType,QueryInputType queryInputType){
            // TODO Auto-generated method stub
            return null;
        }
    },
    Pos1Val(new DataType[]{DataType.ByteType,DataType.CharType,DataType.ShortType,DataType.IntType,DataType.LongType,
            DataType.FloatType,DataType.DoubleType,DataType.BooleanType,DataType.RefType},
            new QueryInputType[]{QueryInputType.PrimitivebyteType,QueryInputType.BoxedByteType,
                    QueryInputType.ByteToObjectType,QueryInputType.PrimitivecharType,QueryInputType.BoxedCharacterType,
                    QueryInputType.CharacterToObjectType,QueryInputType.PrimitiveshortType,
                    QueryInputType.BoxedShortType,QueryInputType.ShortToObjectType,QueryInputType.PrimitiveintType,
                    QueryInputType.BoxedIntegerType,
                    QueryInputType.IntegerToObjectType,QueryInputType.PrimitivelongType,QueryInputType.BoxedLongType,
                    QueryInputType.LongToObjectType,QueryInputType.PrimitivefloatType,QueryInputType.BoxedFloatType,
                    QueryInputType.FloatToObjectType,QueryInputType.PrimitivedoubleType,QueryInputType.BoxedDoubleType,
                    QueryInputType.DoubleToObjectType}) {
        @Override
        public Object getEqualsVal(DataType dataType,QueryInputType queryInputType){
            // TODO Auto-generated method stub
            return null;
        }
    },
    Neg1Val(new DataType[]{DataType.ByteType,DataType.ShortType,DataType.IntType,DataType.LongType,DataType.FloatType,
            DataType.DoubleType,DataType.RefType},
            new QueryInputType[]{QueryInputType.PrimitivebyteType,QueryInputType.BoxedByteType,
                    QueryInputType.ByteToObjectType,QueryInputType.PrimitiveshortType,QueryInputType.BoxedShortType,
                    QueryInputType.ShortToObjectType,QueryInputType.PrimitiveintType,QueryInputType.BoxedIntegerType,
                    QueryInputType.IntegerToObjectType,QueryInputType.PrimitivelongType,QueryInputType.BoxedLongType,
                    QueryInputType.LongToObjectType,QueryInputType.PrimitivefloatType,QueryInputType.BoxedFloatType,
                    QueryInputType.FloatToObjectType,QueryInputType.PrimitivedoubleType,QueryInputType.BoxedDoubleType,
                    QueryInputType.DoubleToObjectType}) {
        @Override
        public Object getEqualsVal(DataType dataType,QueryInputType queryInputType){
            // TODO Auto-generated method stub
            return null;
        }
    },
    Pos2Val(new DataType[]{DataType.ByteType,DataType.CharType,DataType.ShortType,DataType.IntType,DataType.LongType,
            DataType.FloatType,DataType.DoubleType,DataType.RefType},
            new QueryInputType[]{QueryInputType.PrimitivebyteType,QueryInputType.BoxedByteType,
                    QueryInputType.ByteToObjectType,QueryInputType.PrimitivecharType,QueryInputType.BoxedCharacterType,
                    QueryInputType.CharacterToObjectType,QueryInputType.PrimitiveshortType,
                    QueryInputType.BoxedShortType,QueryInputType.ShortToObjectType,QueryInputType.PrimitiveintType,
                    QueryInputType.BoxedIntegerType,
                    QueryInputType.IntegerToObjectType,QueryInputType.PrimitivelongType,QueryInputType.BoxedLongType,
                    QueryInputType.LongToObjectType,QueryInputType.PrimitivefloatType,QueryInputType.BoxedFloatType,
                    QueryInputType.FloatToObjectType,QueryInputType.PrimitivedoubleType,QueryInputType.BoxedDoubleType,
                    QueryInputType.DoubleToObjectType}) {
        @Override
        public Object getEqualsVal(DataType dataType,QueryInputType queryInputType){
            // TODO Auto-generated method stub
            return null;
        }
    },
    MaxByte_Plus1_Val(
            new DataType[]{DataType.CharType,DataType.ShortType,DataType.IntType,DataType.LongType,DataType.FloatType,
                    DataType.DoubleType,DataType.RefType},
            new QueryInputType[]{QueryInputType.PrimitivecharType,QueryInputType.BoxedCharacterType,
                    QueryInputType.CharacterToObjectType,QueryInputType.PrimitiveshortType,
                    QueryInputType.BoxedShortType,QueryInputType.ShortToObjectType,QueryInputType.PrimitiveintType,
                    QueryInputType.BoxedIntegerType,
                    QueryInputType.IntegerToObjectType,QueryInputType.PrimitivelongType,QueryInputType.BoxedLongType,
                    QueryInputType.LongToObjectType,QueryInputType.PrimitivefloatType,QueryInputType.BoxedFloatType,
                    QueryInputType.FloatToObjectType,QueryInputType.PrimitivedoubleType,QueryInputType.BoxedDoubleType,
                    QueryInputType.DoubleToObjectType}) {
        @Override
        public Object getEqualsVal(DataType dataType,QueryInputType queryInputType){
            // TODO Auto-generated method stub
            return null;
        }
    },
    MinByte_Minus1_Val(
            new DataType[]{DataType.ShortType,DataType.IntType,DataType.LongType,DataType.FloatType,DataType.DoubleType,
                    DataType.RefType},
            new QueryInputType[]{QueryInputType.PrimitiveshortType,QueryInputType.BoxedShortType,
                    QueryInputType.ShortToObjectType,QueryInputType.PrimitiveintType,QueryInputType.BoxedIntegerType,
                    QueryInputType.IntegerToObjectType,QueryInputType.PrimitivelongType,QueryInputType.BoxedLongType,
                    QueryInputType.LongToObjectType,QueryInputType.PrimitivefloatType,QueryInputType.BoxedFloatType,
                    QueryInputType.FloatToObjectType,QueryInputType.PrimitivedoubleType,QueryInputType.BoxedDoubleType,
                    QueryInputType.DoubleToObjectType}) {
        @Override
        public Object getEqualsVal(DataType dataType,QueryInputType queryInputType){
            // TODO Auto-generated method stub
            return null;
        }
    },
    MaxChar_Plus1_Val(
            new DataType[]{DataType.IntType,DataType.LongType,DataType.FloatType,DataType.DoubleType,DataType.RefType},
            new QueryInputType[]{QueryInputType.PrimitiveintType,QueryInputType.BoxedIntegerType,
                    QueryInputType.IntegerToObjectType,QueryInputType.PrimitivelongType,QueryInputType.BoxedLongType,
                    QueryInputType.LongToObjectType,QueryInputType.PrimitivefloatType,QueryInputType.BoxedFloatType,
                    QueryInputType.FloatToObjectType,QueryInputType.PrimitivedoubleType,QueryInputType.BoxedDoubleType,
                    QueryInputType.DoubleToObjectType}) {
        @Override
        public Object getEqualsVal(DataType dataType,QueryInputType queryInputType){
            // TODO Auto-generated method stub
            return null;
        }
    },
    MaxShort_Plus1_Val(
            new DataType[]{DataType.CharType,DataType.IntType,DataType.LongType,DataType.FloatType,DataType.DoubleType,
                    DataType.RefType},
            new QueryInputType[]{QueryInputType.PrimitivecharType,QueryInputType.BoxedCharacterType,
                    QueryInputType.CharacterToObjectType,QueryInputType.PrimitiveintType,
                    QueryInputType.BoxedIntegerType,
                    QueryInputType.IntegerToObjectType,QueryInputType.PrimitivelongType,QueryInputType.BoxedLongType,
                    QueryInputType.LongToObjectType,QueryInputType.PrimitivefloatType,QueryInputType.BoxedFloatType,
                    QueryInputType.FloatToObjectType,QueryInputType.PrimitivedoubleType,QueryInputType.BoxedDoubleType,
                    QueryInputType.DoubleToObjectType}) {
        @Override
        public Object getEqualsVal(DataType dataType,QueryInputType queryInputType){
            // TODO Auto-generated method stub
            return null;
        }
    },
    MinShort_Minus1_Val(
            new DataType[]{DataType.IntType,DataType.LongType,DataType.FloatType,DataType.DoubleType,DataType.RefType},
            new QueryInputType[]{QueryInputType.PrimitiveintType,QueryInputType.BoxedIntegerType,
                    QueryInputType.IntegerToObjectType,QueryInputType.PrimitivelongType,QueryInputType.BoxedLongType,
                    QueryInputType.LongToObjectType,QueryInputType.PrimitivefloatType,QueryInputType.BoxedFloatType,
                    QueryInputType.FloatToObjectType,QueryInputType.PrimitivedoubleType,QueryInputType.BoxedDoubleType,
                    QueryInputType.DoubleToObjectType}) {
        @Override
        public Object getEqualsVal(DataType dataType,QueryInputType queryInputType){
            // TODO Auto-generated method stub
            return null;
        }
    },
    MaxSafeInt_Plus1_Val(new DataType[]{DataType.IntType,DataType.LongType,DataType.DoubleType,DataType.RefType},
            new QueryInputType[]{QueryInputType.PrimitiveintType,QueryInputType.BoxedIntegerType,
                    QueryInputType.IntegerToObjectType,QueryInputType.PrimitivelongType,QueryInputType.BoxedLongType,
                    QueryInputType.LongToObjectType,QueryInputType.PrimitivedoubleType,QueryInputType.BoxedDoubleType,
                    QueryInputType.DoubleToObjectType}) {
        @Override
        public Object getEqualsVal(DataType dataType,QueryInputType queryInputType){
            // TODO Auto-generated method stub
            return null;
        }
    },
    MinSafeInt_Minus1_Val(new DataType[]{DataType.IntType,DataType.LongType,DataType.DoubleType,DataType.RefType},
            new QueryInputType[]{QueryInputType.PrimitiveintType,QueryInputType.BoxedIntegerType,
                    QueryInputType.IntegerToObjectType,QueryInputType.PrimitivelongType,QueryInputType.BoxedLongType,
                    QueryInputType.LongToObjectType,QueryInputType.PrimitivedoubleType,QueryInputType.BoxedDoubleType,
                    QueryInputType.DoubleToObjectType}) {
        @Override
        public Object getEqualsVal(DataType dataType,QueryInputType queryInputType){
            // TODO Auto-generated method stub
            return null;
        }
    },
    MaxInt_Plus1_Val(new DataType[]{DataType.LongType,DataType.DoubleType,DataType.RefType},
            new QueryInputType[]{QueryInputType.PrimitivelongType,QueryInputType.BoxedLongType,
                    QueryInputType.LongToObjectType,QueryInputType.PrimitivefloatType,QueryInputType.BoxedFloatType,
                    QueryInputType.FloatToObjectType,QueryInputType.PrimitivedoubleType,QueryInputType.BoxedDoubleType,
                    QueryInputType.DoubleToObjectType}) {
        @Override
        public Object getEqualsVal(DataType dataType,QueryInputType queryInputType){
            // TODO Auto-generated method stub
            return null;
        }
    },
    MinInt_Minus1_Val(new DataType[]{DataType.LongType,DataType.DoubleType,DataType.RefType},
            new QueryInputType[]{QueryInputType.PrimitivelongType,QueryInputType.BoxedLongType,
                    QueryInputType.LongToObjectType,QueryInputType.PrimitivefloatType,QueryInputType.BoxedFloatType,
                    QueryInputType.FloatToObjectType,QueryInputType.PrimitivedoubleType,QueryInputType.BoxedDoubleType,
                    QueryInputType.DoubleToObjectType}) {
        @Override
        public Object getEqualsVal(DataType dataType,QueryInputType queryInputType){
            // TODO Auto-generated method stub
            return null;
        }
    },
    MaxSafeLong_Plus1_Val(new DataType[]{DataType.LongType,DataType.RefType},
            new QueryInputType[]{QueryInputType.PrimitivelongType,QueryInputType.BoxedLongType,
                    QueryInputType.LongToObjectType}) {
        @Override
        public Object getEqualsVal(DataType dataType,QueryInputType queryInputType){
            // TODO Auto-generated method stub
            return null;
        }
    },
    MinSafeLong_Minus1_Val(new DataType[]{DataType.LongType,DataType.RefType},
            new QueryInputType[]{QueryInputType.PrimitivelongType,QueryInputType.BoxedLongType,
                    QueryInputType.LongToObjectType}) {
        @Override
        public Object getEqualsVal(DataType dataType,QueryInputType queryInputType){
            // TODO Auto-generated method stub
            return null;
        }
    },
    MaxLong_Plus1_Val(new DataType[]{DataType.DoubleType,DataType.RefType},
            new QueryInputType[]{QueryInputType.PrimitivefloatType,QueryInputType.BoxedFloatType,
                    QueryInputType.FloatToObjectType,QueryInputType.PrimitivedoubleType,QueryInputType.BoxedDoubleType,
                    QueryInputType.DoubleToObjectType}) {
        @Override
        public Object getEqualsVal(DataType dataType,QueryInputType queryInputType){
            // TODO Auto-generated method stub
            return null;
        }
    },
    MinLong_Minus1_Val(new DataType[]{DataType.DoubleType,DataType.RefType},
            new QueryInputType[]{QueryInputType.PrimitivefloatType,QueryInputType.BoxedFloatType,
                    QueryInputType.FloatToObjectType,QueryInputType.PrimitivedoubleType,QueryInputType.BoxedDoubleType,
                    QueryInputType.DoubleToObjectType}) {
        @Override
        public Object getEqualsVal(DataType dataType,QueryInputType queryInputType){
            //TODO
            return null;
        }
    },
    MaxFloat_Val(new DataType[]{DataType.FloatType,DataType.DoubleType,DataType.RefType},
            new QueryInputType[]{QueryInputType.PrimitivefloatType,QueryInputType.BoxedFloatType,
                    QueryInputType.FloatToObjectType,QueryInputType.PrimitivedoubleType,QueryInputType.BoxedDoubleType,
                    QueryInputType.DoubleToObjectType}) {
        @Override
        public Object getEqualsVal(DataType dataType,QueryInputType queryInputType){
            switch(dataType){
            case DoubleType:
                queryInputType=QueryInputType.PrimitivedoubleType;
            case RefType:
                switch(queryInputType) {
                default:
                    throw super.getInvalidInputTypeError(queryInputType);
                case PrimitivedoubleType:
                case BoxedDoubleType:
                case DoubleToObjectType:
                    return (double)Float.MAX_VALUE;
                case PrimitivefloatType:
                case BoxedFloatType:
                case FloatToObjectType:
                }
            case FloatType:
                return (float)Float.MAX_VALUE;
            default:
                throw super.getInvalidDataTypeError(dataType);
            }
        }
    },
    MinFloat_Val(new DataType[]{DataType.FloatType,DataType.DoubleType,DataType.RefType},
            new QueryInputType[]{QueryInputType.PrimitivefloatType,QueryInputType.BoxedFloatType,
                    QueryInputType.FloatToObjectType,QueryInputType.PrimitivedoubleType,QueryInputType.BoxedDoubleType,
                    QueryInputType.DoubleToObjectType}) {
        @Override
        public Object getEqualsVal(DataType dataType,QueryInputType queryInputType){
            switch(dataType){
            case DoubleType:
                queryInputType=QueryInputType.PrimitivedoubleType;
            case RefType:
                switch(queryInputType) {
                default:
                    throw super.getInvalidInputTypeError(queryInputType);
                case PrimitivedoubleType:
                case BoxedDoubleType:
                case DoubleToObjectType:
                    return (double)Float.MIN_VALUE;
                case PrimitivefloatType:
                case BoxedFloatType:
                case FloatToObjectType:
                }
            case FloatType:
                return (float)Float.MIN_VALUE;
            default:
                throw super.getInvalidDataTypeError(dataType);
            }
        }
    },
    MaxDouble_Val(new DataType[]{DataType.DoubleType,DataType.RefType},
            new QueryInputType[]{QueryInputType.PrimitivedoubleType,QueryInputType.BoxedDoubleType,
                    QueryInputType.DoubleToObjectType}) {
        @Override
        public Object getEqualsVal(DataType dataType,QueryInputType queryInputType){
            switch(dataType) {
            case RefType:
                switch(queryInputType) {
                default:
                    throw super.getInvalidInputTypeError(queryInputType);
                case PrimitivedoubleType:
                case BoxedDoubleType:
                case DoubleToObjectType:
                }
            case DoubleType:
                return (double)Double.MAX_VALUE;
            default:
                throw super.getInvalidDataTypeError(dataType);
            }
        }
    },
    MinDouble_Val(new DataType[]{DataType.DoubleType,DataType.RefType},
            new QueryInputType[]{QueryInputType.PrimitivedoubleType,QueryInputType.BoxedDoubleType,
                    QueryInputType.DoubleToObjectType}) {
        @Override
        public Object getEqualsVal(DataType dataType,QueryInputType queryInputType){
            switch(dataType) {
            case RefType:
                switch(queryInputType) {
                default:
                    throw super.getInvalidInputTypeError(queryInputType);
                case PrimitivedoubleType:
                case BoxedDoubleType:
                case DoubleToObjectType:
                }
            case DoubleType:
                return (double)Double.MIN_VALUE;
            default:
                throw super.getInvalidDataTypeError(dataType);
            }
        }
    },
    Neg0_Val(
            new DataType[]{DataType.DoubleType,DataType.FloatType,DataType.LongType,DataType.IntType,DataType.ShortType,
                    DataType.CharType,DataType.ByteType,DataType.BooleanType,DataType.RefType},
            new QueryInputType[]{QueryInputType.PrimitivefloatType,QueryInputType.BoxedFloatType,
                    QueryInputType.FloatToObjectType,QueryInputType.PrimitivedoubleType,QueryInputType.BoxedDoubleType,
                    QueryInputType.DoubleToObjectType}) {
        @Override
        public Object getEqualsVal(DataType dataType,QueryInputType queryInputType){
            switch(dataType) {
            case LongType:
                return 0L;
            case IntType:
                return (int)0;
            case ShortType:
                return (short)0;
            case CharType:
                return (char)0;
            case ByteType:
                return (byte)0;
            case BooleanType:
                return false;
            case FloatType:
                queryInputType=QueryInputType.PrimitivefloatType;
            case RefType:
                switch(queryInputType) {
                default:
                    throw super.getInvalidInputTypeError(queryInputType);
                case PrimitivefloatType:
                case BoxedFloatType:
                case FloatToObjectType:
                    return -0.0f;
                case PrimitivedoubleType:
                case BoxedDoubleType:
                case DoubleToObjectType:
                }
            case DoubleType:
                return -0.0d;
            default:
                throw super.getInvalidDataTypeError(dataType);
            }
        }
    },
    Nan_Val(new DataType[]{DataType.DoubleType,DataType.FloatType,DataType.RefType},
            new QueryInputType[]{QueryInputType.PrimitivefloatType,QueryInputType.BoxedFloatType,
                    QueryInputType.PrimitivedoubleType,QueryInputType.BoxedDoubleType,QueryInputType.FloatToObjectType,
                    QueryInputType.DoubleToObjectType}) {
        @Override
        public Object getEqualsVal(DataType dataType,QueryInputType queryInputType){
            switch(dataType) {
            
            case FloatType:
                queryInputType=QueryInputType.PrimitivefloatType:
            case RefType:
                switch(queryInputType) {
                default:
                    throw super.getInvalidInputTypeError(queryInputType);
                case PrimitivefloatType:
                case BoxedFloatType:
                case FloatToObjectType:
                    return Float.NaN;
                case PrimitivedoubleType:
                case BoxedDoubleType:
                case DoubleToObjectType:
                }
            case DoubleType:
                return Double.NaN;
            default:
                throw super.getInvalidDataTypeError(dataType);
            }
        }
    };

    final Set<QueryInputType> validInputTypes;
    final Set<DataType> typesWhichMayContain;
    QueryInputVal(DataType[] typesWhichMayContain,QueryInputType[] validInputTypes){
        this.validInputTypes=Set.of(validInputTypes);
        this.typesWhichMayContain=Set.of(typesWhichMayContain);
    }

    public abstract Object getEqualsVal(DataType dataType,QueryInputType queryInputType);

    private Error getInvalidInputTypeError(QueryInputType queryInputType) {
        Assertions.assertFalse(validInputTypes.contains(queryInputType),"The validInputTypes set contains "+queryInputType+" even though the switch statement in getEqualsVal(DataType,QueryInputType) doesn't agree");
        return new Error("The query input value "+this+" cannot be converted to the query input type "+queryInputType);
    }
    private Error getInvalidDataTypeError(DataType dataType) {
        Assertions.assertFalse(typesWhichMayContain.contains(dataType),"The typesWhichMayContain set contains "+dataType+" even though the switch statement in getEqualsVal(DataType,QueryInputType) doesn't agree");
        return new Error("The query input value "+this+" cannot be contained in a container of data type "+dataType);
    }
    


}
