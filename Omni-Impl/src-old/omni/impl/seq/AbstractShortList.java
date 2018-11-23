package omni.impl.seq;
import omni.util.TypeUtil;
public abstract class AbstractShortList extends AbstractSeq.Of16BitPrimitive{
    protected AbstractShortList(){
        super();
    }
    protected AbstractShortList(int size){
        super(size);
    }
    public void add(int index,Short val){
        add(index,(short)val);
    }
    public Short get(int index){
        return getShort(index);
    }
    public int lastIndexOf(boolean val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedLastIndexOfMatch(size,TypeUtil.castToByte(val));
        }
        return -1;
    }
    public int lastIndexOf(char val){
        final int size;
        if(val<=Short.MAX_VALUE&&(size=this.size)!=0){
            return uncheckedLastIndexOfMatch(size,val);
        }
        return -1;
    }
    public int lastIndexOf(double val){
        final int size,v;
        if((size=this.size)!=0&&val==(v=(short)val)){
            return uncheckedLastIndexOfMatch(size,v);
        }
        return -1;
    }
    public int lastIndexOf(float val){
        final int size,v;
        if((size=this.size)!=0&&val==(v=(short)val)){
            return uncheckedLastIndexOfMatch(size,v);
        }
        return -1;
    }
    public int lastIndexOf(int val){
        final int size;
        if((size=this.size)!=0&&val==(short)val){
            return uncheckedLastIndexOfMatch(size,val);
        }
        return -1;
    }
    public int lastIndexOf(long val){
        final int size,v;
        if((size=this.size)!=0&&val==(v=(short)val)){
            return uncheckedLastIndexOfMatch(size,v);
        }
        return -1;
    }
    public int lastIndexOf(Object val){
        final int size;
        if((size=this.size)!=0&&val instanceof Short){
            return uncheckedLastIndexOfMatch(size,(short)val);
        }
        return -1;
    }
    public int lastIndexOf(short val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedLastIndexOfMatch(size,val);
        }
        return -1;
    }
    public Short remove(int index){
        return removeShortAt(index);
    }
    public Short set(int index,Short val){
        return set(index,(short)val);
    }
    protected abstract void add(int index,short val);
    protected abstract short getShort(int index);
    protected abstract short removeShortAt(int index);
    protected abstract short set(int index,short val);
    protected abstract int uncheckedLastIndexOfMatch(int size,int val);
}
