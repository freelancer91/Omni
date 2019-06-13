package omni.impl;

import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.LongConsumer;
import org.junit.jupiter.api.Assertions;
import omni.api.OmniCollection;
import omni.function.BooleanConsumer;
import omni.function.ByteConsumer;
import omni.function.CharConsumer;
import omni.function.FloatConsumer;
import omni.function.ShortConsumer;
public abstract class AbstractCollectionMonitor<COL extends OmniCollection>{
    protected int expectedSize;
    protected final COL col;
    public final DataType dataType;

    protected AbstractCollectionMonitor(int expectedSize,COL col,DataType dataType){
        this.expectedSize=expectedSize;
        this.col=col;
        this.dataType=dataType;
    }
    public abstract void verifyStructuralIntegrity();
    public abstract void verifyAdd_val(DataType dataType,boolean expectedResult,int inputVal);
    public abstract void verifyRemove_val(DataType dataType,boolean expectedResult,int inputVal);
    public abstract void verifyClear_void();

    public abstract void verifyClone();

    public Object callToArray(DataType outputType){
        try{
            switch(outputType){
            case REF:
                return col.toArray();
            case BOOLEAN:
                return ((OmniCollection.OfBoolean)col).toBooleanArray();
            case BYTE:
                return ((OmniCollection.ByteOutput<?>)col).toByteArray();
            case CHAR:
                return ((OmniCollection.CharOutput<?>)col).toCharArray();
            case SHORT:
                return ((OmniCollection.ShortOutput<?>)col).toShortArray();
            case INT:
                return ((OmniCollection.IntOutput<?>)col).toIntArray();
            case LONG:
                return ((OmniCollection.LongOutput<?>)col).toLongArray();
            case FLOAT:
                return ((OmniCollection.FloatOutput<?>)col).toFloatArray();
            case DOUBLE:
                return ((OmniCollection.DoubleOutput<?>)col).toDoubleArray();
            default:
                throw new Error("Unknown dataType " + dataType);
            }
        }finally{
            verifyStructuralIntegrity();
        }
    }
    public void verifySize(){
        int expectedSize=this.expectedSize;
        int actualSize;
        try{
            actualSize=col.size();
        }finally{
            verifyStructuralIntegrity();
        }
        Assertions.assertEquals(expectedSize,actualSize);
    }
    public void verifyIsEmpty(){
        boolean expected=this.expectedSize == 0;
        boolean actual;
        try{
            actual=col.isEmpty();
        }finally{
            verifyStructuralIntegrity();
        }
        Assertions.assertEquals(expected,actual);
    }
    public <T> T[] callToArray(IntFunction<T[]> arrConstructor){
        try{
            return col.toArray(arrConstructor);
        }finally{
            verifyStructuralIntegrity();
        }
    }
    public <T> T[] callToArray(T[] arr){
        try{
            return col.toArray(arr);
        }finally{
            verifyStructuralIntegrity();
        }
    }
    @SuppressWarnings("unchecked")
    public void callForEach(FunctionCallType functionCallType,Object consumer){
        try{
            switch(dataType){
            case REF:
                ((OmniCollection.OfRef<?>)col).forEach((Consumer<Object>)consumer);
                break;
            case BOOLEAN:
            {
                var cast=(OmniCollection.OfBoolean)col;
                if(functionCallType.boxed){
                    cast.forEach((Consumer<? super Boolean>)consumer);
                }else{
                    cast.forEach((BooleanConsumer)consumer);
                }
                break;
            }
            case BYTE:
            {
                var cast=(OmniCollection.OfByte)col;
                if(functionCallType.boxed){
                    cast.forEach((Consumer<? super Byte>)consumer);
                }else{
                    cast.forEach((ByteConsumer)consumer);
                }
                break;
            }
            case CHAR:
            {
                var cast=(OmniCollection.OfChar)col;
                if(functionCallType.boxed){
                    cast.forEach((Consumer<? super Character>)consumer);
                }else{
                    cast.forEach((CharConsumer)consumer);
                }
                break;
            }
            case SHORT:
            {
                var cast=(OmniCollection.OfShort)col;
                if(functionCallType.boxed){
                    cast.forEach((Consumer<? super Short>)consumer);
                }else{
                    cast.forEach((ShortConsumer)consumer);
                }
                break;
            }
            case INT:
            {
                var cast=(OmniCollection.OfInt)col;
                if(functionCallType.boxed){
                    cast.forEach((Consumer<? super Integer>)consumer);
                }else{
                    cast.forEach((IntConsumer)consumer);
                }
                break;
            }
            case LONG:
            {
                var cast=(OmniCollection.OfLong)col;
                if(functionCallType.boxed){
                    cast.forEach((Consumer<? super Long>)consumer);
                }else{
                    cast.forEach((LongConsumer)consumer);
                }
                break;
            }
            case FLOAT:
            {
                var cast=(OmniCollection.OfFloat)col;
                if(functionCallType.boxed){
                    cast.forEach((Consumer<? super Float>)consumer);
                }else{
                    cast.forEach((FloatConsumer)consumer);
                }
                break;
            }
            case DOUBLE:
            {
                var cast=(OmniCollection.OfDouble)col;
                if(functionCallType.boxed){
                    cast.forEach((Consumer<? super Double>)consumer);
                }else{
                    cast.forEach((DoubleConsumer)consumer);
                }
                break;
            }
            default:
                throw new Error("Unknown dataType " + dataType);
            }
        }finally{
            verifyStructuralIntegrity();
        }
    }
}
