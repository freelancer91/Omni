package omni.impl;

import java.util.Comparator;
import java.util.function.IntBinaryOperator;
import omni.function.BooleanComparator;
import omni.function.ByteComparator;
import omni.function.CharComparator;
import omni.function.DoubleComparator;
import omni.function.FloatComparator;
import omni.function.LongComparator;
import omni.function.ShortComparator;

@SuppressWarnings("rawtypes")
public class MonitoredComparator
implements
Comparator,
BooleanComparator,
ByteComparator,
CharComparator,
ShortComparator,
IntBinaryOperator,
LongComparator,
FloatComparator,
DoubleComparator{
    protected void throwingCall(){
    }
    protected int impl(double val1,double val2){
        return Double.compare(val1,val2);
    }
    protected int impl(long val1,long val2){
        return Long.compare(val1,val2);
    }
    @SuppressWarnings("unchecked")
    protected int impl(Object val1,Object val2){
        return ((Comparable)val1).compareTo(val2);
    }
    @Override
    public int compare(double val1,double val2){
        throwingCall();
        return impl(val1,val2);
    }
    @Override
    public int compare(float val1,float val2){
        throwingCall();
        return impl(val1,val2);
    }
    @Override
    public int compare(long val1,long val2){
        throwingCall();
        return impl(val1,val2);
    }
    @Override
    public int applyAsInt(int val1,int val2){
        throwingCall();
        return impl(val1,val2);
    }
    @Override
    public int compare(short val1,short val2){
        throwingCall();
        return impl(val1,val2);
    }
    @Override
    public int compare(char val1,char val2){
        throwingCall();
        return impl(val1,val2);
    }
    @Override
    public int compare(byte val1,byte val2){
        throwingCall();
        return impl(val1,val2);
    }
    @Override
    public int compare(boolean val1,boolean val2){
        throwingCall();
        return impl(val1?1L:0L,val2?1L:0L);
    }
    @Override
    public int compare(Object val1,Object val2){
        throwingCall();
        return impl(val1,val2);
    }
}
