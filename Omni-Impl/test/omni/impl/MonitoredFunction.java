package omni.impl;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleUnaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntUnaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongUnaryOperator;
import java.util.function.UnaryOperator;
import omni.function.BooleanConsumer;
import omni.function.BooleanPredicate;
import omni.function.ByteConsumer;
import omni.function.ByteUnaryOperator;
import omni.function.CharConsumer;
import omni.function.CharUnaryOperator;
import omni.function.FloatConsumer;
import omni.function.FloatUnaryOperator;
import omni.function.ShortConsumer;
import omni.function.ShortUnaryOperator;
@SuppressWarnings("rawtypes")
public abstract class MonitoredFunction extends ArrayList<Object>
implements
Consumer,
BooleanConsumer,
ByteConsumer,
CharConsumer,
ShortConsumer,
IntConsumer,
LongConsumer,
FloatConsumer,
DoubleConsumer,
UnaryOperator,
BooleanPredicate,
ByteUnaryOperator,
CharUnaryOperator,
ShortUnaryOperator,
IntUnaryOperator,
LongUnaryOperator,
FloatUnaryOperator,
DoubleUnaryOperator{
    /**
     *
     */
    private static final long serialVersionUID=1L;

    public abstract MonitoredFunctionGen getMonitoredFunctionGen();


    protected abstract void throwingCall();
    @Override
    public void accept(Object t){
        super.add(t);
        throwingCall();
    }
    @Override
    public void accept(double value){
        super.add(value);
        throwingCall();
    }
    @Override
    public void accept(float val){
        super.add(val);
        throwingCall();
    }
    @Override
    public void accept(long value){
        super.add(value);
        throwingCall();
    }
    @Override
    public void accept(int value){
        super.add(value);
        throwingCall();
    }
    @Override
    public void accept(short val){
        super.add(val);
        throwingCall();
    }
    @Override
    public void accept(char val){
        super.add(val);
        throwingCall();
    }
    @Override
    public void accept(byte val){
        super.add(val);
        throwingCall();
    }
    @Override
    public void accept(boolean val){
        super.add(val);
        throwingCall();
    }
    @Override
    public Object apply(Object t){
        if(t instanceof Boolean){
            return test((boolean)t);
        }else if(t instanceof Byte){
            return applyAsByte((byte)t);
        }else if(t instanceof Character){
            return applyAsChar((char)t);
        }else if(t instanceof Short){
            return applyAsShort((short)t);
        }else if(t instanceof Integer){
            return applyAsInt((int)t);
        }else if(t instanceof Long){
            return applyAsLong((long)t);
        }else if(t instanceof Float){
            return applyAsFloat((float)t);
        }else if(t instanceof Double){
            return applyAsDouble((double)t);
        }else{
            throw new UnsupportedOperationException("the value t=" + t + " is of an unknown type");
        }
    }
    @Override
    public double applyAsDouble(double operand){
        accept(operand);
        return -operand;
    }
    @Override
    public float applyAsFloat(float val){
        accept(val);
        return -val;
    }
    @Override
    public long applyAsLong(long operand){
        accept(operand);
        return operand + Long.MAX_VALUE;
    }
    @Override
    public int applyAsInt(int operand){
        accept(operand);
        return operand + Integer.MAX_VALUE;
    }
    @Override
    public short applyAsShort(short val){
        accept(val);
        return (short)(val + Short.MAX_VALUE);
    }
    @Override
    public char applyAsChar(char val){
        accept(val);
        return (char)(val + Character.MAX_VALUE);
    }
    @Override
    public byte applyAsByte(byte val){
        accept(val);
        return (byte)(val + Byte.MAX_VALUE);
    }
    @Override
    public boolean test(boolean val){
        accept(val);
        return !val;
    }
}
