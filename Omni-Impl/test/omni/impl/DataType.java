package omni.impl;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import omni.util.TypeConversionUtil;

public enum DataType{
    Boolean(boolean.class,Boolean.class){
        @Override
        public java.lang.Object convertVal(int valToConvert){
            return TypeConversionUtil.convertToboolean(valToConvert);
        }
    },
    Byte(byte.class,Byte.class){
        @Override
        public java.lang.Object convertVal(int valToConvert){
            return TypeConversionUtil.convertTobyte(valToConvert);
        }

    },
    Char(char.class,Character.class){
        @Override
        public java.lang.Object convertVal(int valToConvert){
            return TypeConversionUtil.convertTochar(valToConvert);
        }


    },
    Short(short.class,Short.class){
        @Override
        public java.lang.Object convertVal(int valToConvert){
            return TypeConversionUtil.convertToshort(valToConvert);
        }

    },
    Int(int.class,Integer.class){
        @Override
        public java.lang.Object convertVal(int valToConvert){
            return TypeConversionUtil.convertToint(valToConvert);
        }

    },
    Long(long.class,Long.class){
        @Override
        public java.lang.Object convertVal(int valToConvert){
            return TypeConversionUtil.convertTolong(valToConvert);
        }


    },
    Float(float.class,Float.class){
        @Override
        public java.lang.Object convertVal(int valToConvert){
            return TypeConversionUtil.convertTofloat(valToConvert);
        }


    },
    Double(double.class,Double.class){
        @Override
        public java.lang.Object convertVal(int valToConvert){
            return TypeConversionUtil.convertTodouble(valToConvert);
        }
    },
    Object(Object.class,Object.class){
        @Override
        public java.lang.Object convertVal(int valToConvert){
            return TypeConversionUtil.convertToObject(valToConvert);
        }



    };

    public final Set<DataType> mayBeCastTo;
    public final Set<DataType> mayBeCastFrom;
    public final Set<QueryInputVal> mayContainVals;
    public final Class<?> preferredInputType;
    public final Class<?> boxedType;

    DataType(Class<?> preferredInputType,Class<?> boxedType){
        this.mayBeCastTo=initMayBeCastTo(this);
        this.mayBeCastFrom=initMayBeCastFrom(this);
        this.mayContainVals=initMayContainVals(this);
        this.preferredInputType=preferredInputType;
        this.boxedType=boxedType;
    }
    public abstract Object convertVal(int valToConvert);


    private static Set<QueryInputVal> initMayContainVals(DataType dataType){
        Stream.Builder<QueryInputVal> builder=Stream.builder();
        for(var queryInputVal:QueryInputVal.values()){
            if(queryInputVal.dataTypesWhichCanContain.contains(dataType)){
                builder.accept(queryInputVal);
            }
        }
        return builder.build().collect(Collectors.toUnmodifiableSet());
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
