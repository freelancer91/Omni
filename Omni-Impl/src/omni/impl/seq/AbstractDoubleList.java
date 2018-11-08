package omni.impl.seq;
import omni.util.TypeUtil;
public abstract class AbstractDoubleList extends AbstractSeq{
    protected AbstractDoubleList(){
        super();
    }
    protected AbstractDoubleList(int size){
        super(size);
    }
    public int lastIndexOf(boolean val){
        final int size;
        if((size=this.size)!=0){
            if(val){
                return uncheckedLastIndexOfDblBits(size,TypeUtil.DBL_TRUE_BITS);
            }
            return uncheckedLastIndexOfDbl0(size);
        }
        return -1;
    }
    public int lastIndexOf(double val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedLastIndexOf(size,val);
        }
        return -1;
    }
    public int lastIndexOf(float val){
        final int size;
        if((size=this.size)!=0){
            if(val==val){
                return uncheckedLastIndexOfDblBits(size,Double.doubleToRawLongBits(val));
            }
            return uncheckedLastIndexOfDblNaN(size);
        }
        return -1;
    }
    public int lastIndexOf(int val){
        final int size;
        if((size=this.size)!=0){
            if(val!=0){
                return uncheckedLastIndexOfDblBits(size,Double.doubleToRawLongBits(val));
            }
            return uncheckedLastIndexOfDbl0(size);
        }
        return -1;
    }
    public int lastIndexOf(long val){
        final int size;
        if((size=this.size)!=0){
            if(val!=0){
                if(!TypeUtil.checkCastToDouble(val)){
                    return -1;
                }
                return uncheckedLastIndexOfDblBits(size,Double.doubleToRawLongBits(val));
            }
            return uncheckedLastIndexOfDbl0(size);
        }
        return -1;
    }
    public int lastIndexOf(Object val){
        final int size;
        if((size=this.size)!=0&&val instanceof Double){
            return uncheckedLastIndexOf(size,(double)val);
        }
        return -1;
    }
    protected abstract int uncheckedLastIndexOfDbl0(int size);
    protected abstract int uncheckedLastIndexOfDblBits(int size,long dblBits);
    protected abstract int uncheckedLastIndexOfDblNaN(int size);
    private int uncheckedLastIndexOf(int size,double val){
        if(val==val){
            return uncheckedLastIndexOfDblBits(size,Double.doubleToRawLongBits(val));
        }
        return uncheckedLastIndexOfDblNaN(size);
    }
}
