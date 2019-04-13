package omni.impl;

import omni.util.TypeConversionUtil;
public enum IOType{
    PrimitivebooleanType(boolean.class){
        @Override
        public Object convertToInputVal(int val){
            return TypeConversionUtil.convertToboolean(val);
        }
    },
    PrimitivebyteType(byte.class){
        @Override
        public Object convertToInputVal(int val){
            return TypeConversionUtil.convertTobyte(val);
        }
    },
    PrimitivecharType(char.class){
        @Override
        public Object convertToInputVal(int val){
            return TypeConversionUtil.convertTochar(val);
        }
    },
    PrimitiveshortType(short.class){
        @Override
        public Object convertToInputVal(int val){
            return TypeConversionUtil.convertToshort(val);
        }
    },
    PrimitiveintType(int.class){
        @Override
        public Object convertToInputVal(int val){
            return TypeConversionUtil.convertToint(val);
        }
    },
    PrimitivelongType(long.class){
        @Override
        public Object convertToInputVal(int val){
            return TypeConversionUtil.convertTolong(val);
        }
    },
    PrimitivefloatType(float.class){
        @Override
        public Object convertToInputVal(int val){
            return TypeConversionUtil.convertTofloat(val);
        }
    },
    PrimitivedoubleType(double.class){
        @Override
        public Object convertToInputVal(int val){
            return TypeConversionUtil.convertTodouble(val);
        }
    },
    ObjectType(Object.class){
        @Override
        public Object convertToInputVal(int val){
            // TODO
            throw new UnsupportedOperationException();
        }
    },
    BoxedBooleanType(Boolean.class){
        @Override
        public Object convertToInputVal(int val){
            return TypeConversionUtil.convertToBoolean(val);
        }
    },
    BoxedByteType(Byte.class){
        @Override
        public Object convertToInputVal(int val){
            return TypeConversionUtil.convertToByte(val);
        }
    },
    BoxedCharacterType(Character.class){
        @Override
        public Object convertToInputVal(int val){
            return TypeConversionUtil.convertToCharacter(val);
        }
    },
    BoxedShortType(Short.class){
        @Override
        public Object convertToInputVal(int val){
            return TypeConversionUtil.convertToShort(val);
        }
    },
    BoxedIntegerType(Integer.class){
        @Override
        public Object convertToInputVal(int val){
            return TypeConversionUtil.convertToInteger(val);
        }
    },
    BoxedLongType(Long.class){
        @Override
        public Object convertToInputVal(int val){
            return TypeConversionUtil.convertToLong(val);
        }
    },
    BoxedFloatType(Float.class){
        @Override
        public Object convertToInputVal(int val){
            return TypeConversionUtil.convertToFloat(val);
        }
    },
    BoxedDoubleType(Double.class){
        @Override
        public Object convertToInputVal(int val){
            return TypeConversionUtil.convertToDouble(val);
        }
    };
    public final Class<?> clazz;
    IOType(Class<?> clazz){
        this.clazz=clazz;
    }
    public abstract Object convertToInputVal(int val);
}
