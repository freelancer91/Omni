package omni.impl;

import java.util.Set;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import java.util.function.DoubleUnaryOperator;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongPredicate;
import java.util.function.LongUnaryOperator;
import omni.function.BooleanComparator;
import omni.function.BooleanConsumer;
import omni.function.BooleanPredicate;
import omni.function.ByteComparator;
import omni.function.ByteConsumer;
import omni.function.BytePredicate;
import omni.function.ByteUnaryOperator;
import omni.function.CharComparator;
import omni.function.CharConsumer;
import omni.function.CharPredicate;
import omni.function.CharUnaryOperator;
import omni.function.DoubleComparator;
import omni.function.FloatComparator;
import omni.function.FloatConsumer;
import omni.function.FloatPredicate;
import omni.function.FloatUnaryOperator;
import omni.function.LongComparator;
import omni.function.ShortComparator;
import omni.function.ShortConsumer;
import omni.function.ShortPredicate;
import omni.function.ShortUnaryOperator;
public enum DataType{
    BOOLEAN(true,false,false,Boolean.class,boolean.class,Boolean[].class,boolean[].class,"Boolean","Boolean",
            BooleanPredicate.class,BooleanConsumer.class,BooleanComparator.class,BooleanPredicate.class,Boolean.FALSE,
            BooleanDblLnkNode.class,BooleanSnglLnkNode.class,"removeBooleanAt",boolean.class,"test","compare",
            boolean.class,"booleanElement"),
    BYTE(true,false,true,Byte.class,byte.class,Byte[].class,byte[].class,"Byte","Byte",BytePredicate.class,
            ByteConsumer.class,ByteComparator.class,ByteUnaryOperator.class,Byte.MIN_VALUE,ByteDblLnkNode.class,
            ByteSnglLnkNode.class,"removeByteAt",int.class,"applyAsByte","compare",byte.class,"byteElement"),
    CHAR(true,false,false,Character.class,char.class,Character[].class,char[].class,"Char","Char",CharPredicate.class,
            CharConsumer.class,CharComparator.class,CharUnaryOperator.class,Character.MIN_VALUE,CharDblLnkNode.class,
            CharSnglLnkNode.class,"removeCharAt",int.class,"applyAsChar","compare",char.class,"charElement"),
    SHORT(true,false,true,Short.class,short.class,Short[].class,short[].class,"Short","Short",ShortPredicate.class,
            ShortConsumer.class,ShortComparator.class,ShortUnaryOperator.class,Short.MIN_VALUE,ShortDblLnkNode.class,
            ShortSnglLnkNode.class,"removeShortAt",int.class,"applyAsShort","compare",short.class,"shortElement"),
    INT(true,false,true,Integer.class,int.class,Integer[].class,int[].class,"Int","Int",IntPredicate.class,
            IntConsumer.class,IntBinaryOperator.class,IntUnaryOperator.class,Integer.MIN_VALUE,IntDblLnkNode.class,
            IntSnglLnkNode.class,"removeIntAt",int.class,"applyAsInt","applyAsInt",int.class,"intElement"),
    LONG(true,false,true,Long.class,long.class,Long[].class,long[].class,"Long","Long",LongPredicate.class,
            LongConsumer.class,LongComparator.class,LongUnaryOperator.class,Long.MIN_VALUE,LongDblLnkNode.class,
            LongSnglLnkNode.class,"removeLongAt",long.class,"applyAsLong","compare",long.class,"longElement"),
    FLOAT(false,true,true,Float.class,float.class,Float[].class,float[].class,"Float","Float",FloatPredicate.class,
            FloatConsumer.class,FloatComparator.class,FloatUnaryOperator.class,Float.NaN,FloatDblLnkNode.class,
            FloatSnglLnkNode.class,"removeFloatAt",int.class,"applyAsFloat","compare",float.class,"floatElement"),
    DOUBLE(false,true,true,Double.class,double.class,Double[].class,double[].class,"Double","Double",
            DoublePredicate.class,DoubleConsumer.class,DoubleComparator.class,DoubleUnaryOperator.class,Double.NaN,
            DoubleDblLnkNode.class,DoubleSnglLnkNode.class,"removeDoubleAt",long.class,"applyAsDouble","compare",
            double.class,"doubleElement"),
    REF(false,false,false,null,Object.class,null,Object[].class,"Ref","",null,null,null,null,null,RefDblLnkNode.class,
            RefSnglLnkNode.class,"remove",Object.class,"apply","compare",Comparable.class,"element");
    public final Set<DataType> mayBeCastTo;
    public final Set<DataType> mayBeCastFrom;
    public final Set<DataType> safeCastTo;
    public final Set<DataType> safeCastFrom;
    public final boolean isIntegral;
    public final boolean isFloatingPoint;
    public final boolean isSigned;
    public final Class<?> boxedClass;
    public final Class<?> primitiveClass;
    public final Class<?> boxedArrayClass;
    public final Class<?> primitiveArrayClass;
    public final String classPrefix;
    public final String typeNameModifier;
    public final Class<?> predicateClass;
    public final Class<?> consumerClass;
    public final Class<?> comparatorClass;
    public final Class<?> unaryOperatorClass;
    public final Object defaultVal;
    public final Class<?> dblLnkNodeClass;
    public final Class<?> snglLnkNodeClass;
    public final String removeAtIndexMethodName;
    public final Class<?> queryParameterType;
    public final String applyMethodName;
    public final String compareMethodName;
    public final Class<?> comparableType;
    public final String elementMethodName;
    DataType(boolean isIntegral,boolean isFloatingPoint,boolean isSigned,Class<?> boxedClass,Class<?> primitiveClass,
            Class<?> boxedArrayClass,Class<?> primitiveArrayClass,String classPrefix,String typeNameModifier,
            Class<?> predicateClass,Class<?> consumerClass,Class<?> comparatorClass,Class<?> unaryOperatorClass,
            Object defaultVal,Class<?> dblLnkNodeClass,Class<?> snglLnkNodeClass,String removeAtIndexMethodName,
            Class<?> queryParameterType,String applyMethodName,String compareMethodName,Class<?> comparableType,
            String elementMethodName){
        this.isIntegral=isIntegral;
        this.isFloatingPoint=isFloatingPoint;
        this.isSigned=isSigned;
        this.boxedClass=boxedClass;
        this.primitiveClass=primitiveClass;
        this.boxedArrayClass=boxedArrayClass;
        this.primitiveArrayClass=primitiveArrayClass;
        this.classPrefix=classPrefix;
        this.typeNameModifier=typeNameModifier;
        this.predicateClass=predicateClass;
        this.consumerClass=consumerClass;
        this.comparatorClass=comparatorClass;
        this.unaryOperatorClass=unaryOperatorClass;
        this.defaultVal=defaultVal;
        this.dblLnkNodeClass=dblLnkNodeClass;
        this.snglLnkNodeClass=snglLnkNodeClass;
        this.removeAtIndexMethodName=removeAtIndexMethodName;
        this.queryParameterType=queryParameterType;
        this.applyMethodName=applyMethodName;
        this.compareMethodName=compareMethodName;
        this.comparableType=comparableType;
        this.elementMethodName=elementMethodName;
        this.mayBeCastTo=initMayBeCastTo(this);
        this.mayBeCastFrom=initMayBeCastFrom(this);
        this.safeCastTo=initSafeCastTo(this);
        this.safeCastFrom=initSafeCastFrom(this);
    }

    private static UnsupportedOperationException getUnknownDataTypeException(DataType dataType){
        return new UnsupportedOperationException("Unknown dataType " + dataType);
    }
    private static Set<DataType> initMayBeCastTo(DataType dataType){
        switch(dataType){
        case BOOLEAN:
            return Set.of(DataType.REF,DataType.DOUBLE,DataType.FLOAT,DataType.LONG,DataType.INT,DataType.SHORT,
                    DataType.CHAR,DataType.BYTE,DataType.BOOLEAN);
        case BYTE:
            return Set.of(DataType.REF,DataType.DOUBLE,DataType.FLOAT,DataType.LONG,DataType.INT,DataType.SHORT,
                    DataType.BYTE);
        case CHAR:
            return Set.of(DataType.REF,DataType.DOUBLE,DataType.FLOAT,DataType.LONG,DataType.INT,DataType.CHAR);
        case DOUBLE:
            return Set.of(DataType.REF,DataType.DOUBLE);
        case FLOAT:
            return Set.of(DataType.REF,DataType.DOUBLE,DataType.FLOAT);
        case INT:
            return Set.of(DataType.REF,DataType.DOUBLE,DataType.FLOAT,DataType.LONG,DataType.INT);
        case LONG:
            return Set.of(DataType.REF,DataType.DOUBLE,DataType.FLOAT,DataType.LONG);
        case REF:
            return Set.of(DataType.REF);
        case SHORT:
            return Set.of(DataType.REF,DataType.DOUBLE,DataType.FLOAT,DataType.LONG,DataType.INT,DataType.SHORT);
        }
        throw getUnknownDataTypeException(dataType);
    }
    private static Set<DataType> initMayBeCastFrom(DataType dataType){
        switch(dataType){
        case BOOLEAN:
            return Set.of(DataType.REF,DataType.BOOLEAN);
        case BYTE:
            return Set.of(DataType.REF,DataType.BOOLEAN,DataType.BYTE);
        case CHAR:
            return Set.of(DataType.REF,DataType.BOOLEAN,DataType.CHAR);
        case FLOAT:
            return Set.of(DataType.REF,DataType.BOOLEAN,DataType.BYTE,DataType.CHAR,DataType.SHORT,DataType.INT,
                    DataType.LONG,DataType.FLOAT);
        case INT:
            return Set.of(DataType.REF,DataType.BOOLEAN,DataType.BYTE,DataType.CHAR,DataType.SHORT,DataType.INT);
        case LONG:
            return Set.of(DataType.REF,DataType.BOOLEAN,DataType.BYTE,DataType.CHAR,DataType.SHORT,DataType.INT,
                    DataType.LONG);
        case DOUBLE:
        case REF:
            return Set.of(DataType.REF,DataType.BOOLEAN,DataType.BYTE,DataType.CHAR,DataType.SHORT,DataType.INT,
                    DataType.LONG,DataType.FLOAT,DataType.DOUBLE);
        case SHORT:
            return Set.of(DataType.REF,DataType.BOOLEAN,DataType.BYTE);
        }
        throw getUnknownDataTypeException(dataType);
    }
    private static Set<DataType> initSafeCastTo(DataType dataType){
        switch(dataType){
        case BOOLEAN:
        case BYTE:
        case CHAR:
        case DOUBLE:
        case FLOAT:
        case REF:
        case SHORT:
            return dataType.mayBeCastTo;
        case INT:
            return Set.of(DataType.REF,DataType.DOUBLE,DataType.LONG,DataType.INT);
        case LONG:
            return Set.of(DataType.REF,DataType.LONG);
        }
        throw getUnknownDataTypeException(dataType);
    }
    private static Set<DataType> initSafeCastFrom(DataType dataType){
        switch(dataType){
        case BOOLEAN:
            return Set.of(DataType.BOOLEAN);
        case BYTE:
            return Set.of(DataType.BOOLEAN,DataType.BYTE);
        case CHAR:
            return Set.of(DataType.BOOLEAN,DataType.CHAR);
        case DOUBLE:
            return Set.of(DataType.BOOLEAN,DataType.BYTE,DataType.CHAR,DataType.SHORT,DataType.INT,DataType.FLOAT,
                    DataType.DOUBLE);
        case FLOAT:
            return Set.of(DataType.BOOLEAN,DataType.BYTE,DataType.CHAR,DataType.SHORT,DataType.FLOAT);
        case INT:
            return Set.of(DataType.BOOLEAN,DataType.BYTE,DataType.CHAR,DataType.SHORT,DataType.INT);
        case LONG:
            return Set.of(DataType.BOOLEAN,DataType.BYTE,DataType.CHAR,DataType.SHORT,DataType.INT,DataType.LONG);
        case REF:
            return dataType.mayBeCastFrom;
        case SHORT:
            return Set.of(DataType.BOOLEAN,DataType.BYTE,DataType.SHORT);
        }
        throw getUnknownDataTypeException(dataType);
    }
}
