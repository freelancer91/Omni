package omni.impl.seq;
import omni.util.TypeUtil;
public abstract class AbstractIntList extends AbstractSeq.OfSignedIntegralPrimitive{
    protected AbstractIntList(){
        super();
    }
    protected AbstractIntList(int size){
        super(size);
    }
    public int lastIndexOf(boolean val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedLastIndexOf(size,TypeUtil.castToByte(val));
        }
        return -1;
    }
    public int lastIndexOf(double val){
        final int size,v;
        if((size=this.size)!=0&&val==(v=(int)val)){
            return uncheckedLastIndexOf(size,v);
        }
        return -1;
    }
    public int lastIndexOf(float val){
        final int size,v;
        if((size=this.size)!=0&&(double)val==(double)(v=(int)val)){
            return uncheckedLastIndexOf(size,v);
        }
        return -1;
    }
    public int lastIndexOf(int val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedLastIndexOf(size,val);
        }
        return -1;
    }
    public int lastIndexOf(long val){
        final int size,v;
        if((size=this.size)!=0&&val==(v=(int)val)){
            return uncheckedLastIndexOf(size,v);
        }
        return -1;
    }
    public int lastIndexOf(Object val){
        final int size;
        if((size=this.size)!=0&&val instanceof Integer){
            return uncheckedLastIndexOf(size,(int)val);
        }
        return -1;
    }
    protected abstract int uncheckedLastIndexOf(int size,int val);
}
