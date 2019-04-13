package omni.impl;

import java.util.Set;
public enum DataType{
    BooleanType(IOType.PrimitivebooleanType,IOType.BoxedDoubleType,
            new IOType[]{IOType.PrimitivebooleanType,IOType.BoxedBooleanType},
            new IOType[]{IOType.PrimitivebooleanType,IOType.PrimitivebyteType,IOType.PrimitivecharType,
                    IOType.PrimitiveshortType,IOType.PrimitiveintType,IOType.PrimitivelongType,
                    IOType.PrimitivefloatType,IOType.PrimitivedoubleType,IOType.BoxedBooleanType}),
    ByteType(IOType.PrimitivebyteType,IOType.BoxedByteType,
            new IOType[]{IOType.PrimitivebyteType,IOType.BoxedByteType,IOType.PrimitivebooleanType,
                    IOType.BoxedBooleanType},
            new IOType[]{IOType.PrimitivebyteType,IOType.PrimitiveshortType,IOType.PrimitiveintType,
                    IOType.PrimitivelongType,IOType.PrimitivefloatType,IOType.PrimitivedoubleType,
                    IOType.BoxedByteType}),
    CharType(IOType.PrimitivecharType,IOType.BoxedCharacterType,
            new IOType[]{IOType.PrimitivecharType,IOType.BoxedCharacterType,IOType.PrimitivebooleanType,
                    IOType.BoxedBooleanType},
            new IOType[]{IOType.PrimitivecharType,IOType.PrimitiveintType,IOType.PrimitivelongType,
                    IOType.PrimitivefloatType,IOType.PrimitivedoubleType,IOType.BoxedCharacterType}),
    ShortType(IOType.PrimitiveshortType,IOType.BoxedShortType,
            new IOType[]{IOType.PrimitiveshortType,IOType.BoxedShortType,IOType.PrimitivebyteType,IOType.BoxedByteType,
                    IOType.PrimitivebooleanType,IOType.BoxedBooleanType},
            new IOType[]{IOType.PrimitiveshortType,IOType.PrimitiveintType,IOType.PrimitivelongType,
                    IOType.PrimitivefloatType,IOType.PrimitivedoubleType,IOType.BoxedShortType}),
    IntType(IOType.PrimitiveintType,IOType.BoxedIntegerType,
            new IOType[]{IOType.PrimitiveintType,IOType.BoxedIntegerType,IOType.PrimitiveshortType,
                    IOType.BoxedShortType,IOType.PrimitivecharType,IOType.BoxedCharacterType,IOType.PrimitivebyteType,
                    IOType.BoxedByteType,IOType.PrimitivebooleanType,IOType.BoxedBooleanType},
            new IOType[]{IOType.PrimitiveintType,IOType.PrimitivelongType,IOType.PrimitivefloatType,
                    IOType.PrimitivedoubleType,IOType.BoxedIntegerType}),
    LongType(IOType.PrimitivelongType,IOType.BoxedLongType,
            new IOType[]{IOType.PrimitivelongType,IOType.BoxedLongType,IOType.PrimitiveintType,IOType.BoxedIntegerType,
                    IOType.PrimitiveshortType,IOType.BoxedShortType,IOType.PrimitivecharType,IOType.BoxedCharacterType,
                    IOType.PrimitivebyteType,IOType.BoxedByteType,IOType.PrimitivebooleanType,IOType.BoxedBooleanType},
            new IOType[]{IOType.PrimitivelongType,IOType.PrimitivefloatType,IOType.PrimitivedoubleType,
                    IOType.BoxedLongType}),
    FloatType(IOType.PrimitivefloatType,IOType.BoxedFloatType,
            new IOType[]{IOType.PrimitivefloatType,IOType.BoxedFloatType,IOType.PrimitivelongType,IOType.BoxedLongType,
                    IOType.PrimitiveintType,IOType.BoxedIntegerType,IOType.PrimitiveshortType,IOType.BoxedShortType,
                    IOType.PrimitivecharType,IOType.BoxedCharacterType,IOType.PrimitivebyteType,IOType.BoxedByteType,
                    IOType.PrimitivebooleanType,IOType.BoxedBooleanType},
            new IOType[]{IOType.PrimitivefloatType,IOType.PrimitivedoubleType,IOType.BoxedFloatType}),
    DoubleType(IOType.PrimitivedoubleType,IOType.BoxedDoubleType,
            new IOType[]{IOType.PrimitivedoubleType,IOType.BoxedDoubleType,IOType.PrimitivefloatType,
                    IOType.BoxedFloatType,IOType.PrimitivelongType,IOType.BoxedLongType,IOType.PrimitiveintType,
                    IOType.BoxedIntegerType,IOType.PrimitiveshortType,IOType.BoxedShortType,IOType.PrimitivecharType,
                    IOType.BoxedCharacterType,IOType.PrimitivebyteType,IOType.BoxedByteType,IOType.PrimitivebooleanType,
                    IOType.BoxedBooleanType},
            new IOType[]{IOType.PrimitivedoubleType,IOType.BoxedDoubleType}),
    RefType(IOType.ObjectType,null,new IOType[]{IOType.ObjectType},new IOType[]{IOType.ObjectType});
    public final IOType preferredIOType;
    public final IOType preferredBoxedType;
    public final Set<IOType> inputTypes;
    public final Set<IOType> outputTypes;
    DataType(IOType preferredIOType,IOType preferredBoxedType,IOType[] inputTypes,IOType[] outputTypes){
        this.preferredIOType=preferredIOType;
        this.preferredBoxedType=preferredBoxedType;
        this.inputTypes=Set.of(inputTypes);
        this.outputTypes=Set.of(outputTypes);
    }
}