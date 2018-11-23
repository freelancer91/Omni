package omni.impl.seq;
import omni.util.TypeUtil;
public abstract class AbstractIntList extends AbstractSeq.OfSignedIntegralPrimitive{
    protected AbstractIntList(){
        super();
    }
    protected AbstractIntList(int size){
        super(size);
    }
    public void add(int index,Integer val){
        add(index,(int)val);
    }
    public Integer get(int index){
        return getInt(index);
    }
    public int lastIndexOf(boolean val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedLastIndexOfMatch(size,TypeUtil.castToByte(val));
        }
        return -1;
    }
    public int lastIndexOf(double val){
        final int size,v;
        if((size=this.size)!=0&&val==(v=(int)val)){
            return uncheckedLastIndexOfMatch(size,v);
        }
        return -1;
    }
    public int lastIndexOf(float val){
        final int size,v;
        if((size=this.size)!=0&&(double)val==(double)(v=(int)val)){
            return uncheckedLastIndexOfMatch(size,v);
        }
        return -1;
    }
    public int lastIndexOf(int val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedLastIndexOfMatch(size,val);
        }
        return -1;
    }
    public int lastIndexOf(long val){
        final int size,v;
        if((size=this.size)!=0&&val==(v=(int)val)){
            return uncheckedLastIndexOfMatch(size,v);
        }
        return -1;
    }
    public int lastIndexOf(Object val){
        final int size;
        if((size=this.size)!=0&&val instanceof Integer){
            return uncheckedLastIndexOfMatch(size,(int)val);
        }
        return -1;
    }
    public Integer remove(int index){
        return removeIntAt(index);
    }
    public Integer set(int index,Integer val){
        return set(index,(int)val);
    }
    protected abstract void add(int index,int val);
    protected abstract int getInt(int index);
    protected abstract int removeIntAt(int index);
    protected abstract int set(int index,int val);
    protected abstract int uncheckedLastIndexOfMatch(int size,int val);
}
