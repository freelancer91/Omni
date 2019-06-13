package omni.impl;

import java.util.Set;
public enum DataType{
    REF(Object.class,Object.class),BOOLEAN(Boolean.class,boolean.class),BYTE(Byte.class,byte.class),
    CHAR(Character.class,char.class),SHORT(Short.class,short.class),INT(Integer.class,int.class),
    LONG(Long.class,long.class),FLOAT(Float.class,float.class),DOUBLE(Double.class,double.class);
    public final Set<DataType> safeCastTo;
    public final Set<DataType> safeCastFrom;
    public final Set<DataType> mayBeCastTo;
    public final Set<DataType> mayBeCastFrom;
    public final Class<?> boxedClass;
    public final Class<?> primitiveClass;
    private DataType(Class<?> boxedClass,Class<?> primitiveClass){
        this.boxedClass=boxedClass;
        this.primitiveClass=primitiveClass;
        this.safeCastTo=initSafeCastTo(this);
        this.safeCastFrom=initSafeCastFrom(this);
        this.mayBeCastTo=initMayBeCastTo(this);
        this.mayBeCastFrom=initMayBeCastFrom(this);
    }
    private static Set<DataType> initSafeCastTo(DataType dataType){
        switch(dataType){
        case REF:
            return Set.of(DataType.REF);
        case BOOLEAN:
            return Set.of(DataType.values());
        case BYTE:
            return Set.of(DataType.REF,DataType.BYTE,DataType.SHORT,DataType.INT,DataType.LONG,DataType.FLOAT,
                    DataType.DOUBLE);
        case CHAR:
            return Set.of(DataType.REF,DataType.CHAR,DataType.INT,DataType.LONG,DataType.FLOAT,DataType.DOUBLE);
        case SHORT:
            return Set.of(DataType.REF,DataType.SHORT,DataType.INT,DataType.LONG,DataType.FLOAT,DataType.DOUBLE);
        case INT:
            return Set.of(DataType.REF,DataType.INT,DataType.LONG,DataType.DOUBLE);
        case LONG:
            return Set.of(DataType.REF,DataType.LONG);
        case FLOAT:
            return Set.of(DataType.REF,DataType.FLOAT,DataType.DOUBLE);
        case DOUBLE:
            return Set.of(DataType.REF,DataType.DOUBLE);
        }
        throw new Error("Unknown data type " + dataType);
    }
    private static Set<DataType> initSafeCastFrom(DataType dataType){
        switch(dataType){
        case REF:
            return dataType.safeCastTo;
        case BOOLEAN:
            return Set.of(DataType.BOOLEAN);
        case BYTE:
            return Set.of(DataType.BOOLEAN,DataType.BYTE);
        case CHAR:
            return Set.of(DataType.BOOLEAN,DataType.CHAR);
        case SHORT:
            return Set.of(DataType.BOOLEAN,DataType.BYTE,DataType.SHORT);
        case INT:
            return Set.of(DataType.BOOLEAN,DataType.BYTE,DataType.CHAR,DataType.SHORT,DataType.INT);
        case LONG:
            return Set.of(DataType.BOOLEAN,DataType.BYTE,DataType.CHAR,DataType.SHORT,DataType.INT,DataType.LONG);
        case FLOAT:
            return Set.of(DataType.BOOLEAN,DataType.BYTE,DataType.CHAR,DataType.SHORT,DataType.FLOAT);
        case DOUBLE:
            return Set.of(DataType.BOOLEAN,DataType.BYTE,DataType.CHAR,DataType.SHORT,DataType.INT,DataType.FLOAT,
                    DataType.DOUBLE);
        }
        throw new Error("Unknown data type " + dataType);
    }
    private static Set<DataType> initMayBeCastTo(DataType dataType){
        switch(dataType){
        case REF:
        case BOOLEAN:
        case BYTE:
        case CHAR:
        case SHORT:
        case FLOAT:
        case DOUBLE:
            return dataType.safeCastTo;
        case INT:
            return Set.of(DataType.REF,DataType.INT,DataType.LONG,DataType.FLOAT,DataType.DOUBLE);
        case LONG:
            return Set.of(DataType.REF,DataType.LONG,DataType.FLOAT,DataType.DOUBLE);
        }
        throw new Error("Unknown data type " + dataType);
    }
    private static Set<DataType> initMayBeCastFrom(DataType dataType){
        switch(dataType){
        case REF:
        case BOOLEAN:
        case BYTE:
        case CHAR:
        case SHORT:
        case INT:
        case LONG:
            return dataType.safeCastFrom;
        case FLOAT:
            return Set.of(DataType.BOOLEAN,DataType.BYTE,DataType.CHAR,DataType.SHORT,DataType.INT,DataType.LONG,
                    DataType.FLOAT);
        case DOUBLE:
            return Set.of(DataType.BOOLEAN,DataType.BYTE,DataType.CHAR,DataType.SHORT,DataType.INT,DataType.LONG,
                    DataType.FLOAT,DataType.DOUBLE);
        }
        // TODO
        throw new Error("Unknown data type " + dataType);
    }
}
