package omni.impl;



@SuppressWarnings("rawtypes")
public class MonitoredComparator{
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
    public int compare(double val1,double val2){
        throwingCall();
        return impl(val1,val2);
    }
    public int compare(float val1,float val2){
        throwingCall();
        return impl(val1,val2);
    }
    public int compare(long val1,long val2){
        throwingCall();
        return impl(val1,val2);
    }
    public int compare(int val1,int val2){
        throwingCall();
        return impl(val1,val2);
    }
    public int compare(short val1,short val2){
        throwingCall();
        return impl(val1,val2);
    }
    public int compare(char val1,char val2){
        throwingCall();
        return impl(val1,val2);
    }
    public int compare(byte val1,byte val2){
        throwingCall();
        return impl(val1,val2);
    }
    public int compare(boolean val1,boolean val2){
        throwingCall();
        return impl(val1?1L:0L,val2?1L:0L);
    }
    public int compare(Object val1,Object val2){
        throwingCall();
        return impl(val1,val2);
    }
}
