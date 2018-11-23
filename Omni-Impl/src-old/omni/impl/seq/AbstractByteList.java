package omni.impl.seq;
import omni.util.TypeUtil;
public abstract class AbstractByteList extends AbstractSeq{
    protected AbstractByteList(){
        super();
    }
    protected AbstractByteList(int size){
        super(size);
    }
    public void add(int index,Byte val){
        add(index,(byte)val);
    }
    public Byte get(int index){
        return getByte(index);
    }
    public int lastIndexOf(boolean val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedLastIndexOfMatch(size,TypeUtil.castToByte(val));
        }
        return -1;
    }
    public int lastIndexOf(byte val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedLastIndexOfMatch(size,val);
        }
        return -1;
    }
    public int lastIndexOf(char val){
        final int size;
        if(val<=Byte.MAX_VALUE&&(size=this.size)!=0){
            return uncheckedLastIndexOfMatch(size,val);
        }
        return -1;
    }
    public int lastIndexOf(double val){
        final int size,v;
        if((size=this.size)!=0&&val==(v=(byte)val)){
            return uncheckedLastIndexOfMatch(size,v);
        }
        return -1;
    }
    public int lastIndexOf(float val){
        final int size,v;
        if((size=this.size)!=0&&val==(v=(byte)val)){
            return uncheckedLastIndexOfMatch(size,v);
        }
        return -1;
    }
    public int lastIndexOf(int val){
        final int size;
        if((size=this.size)!=0&&val==(byte)val){
            return uncheckedLastIndexOfMatch(size,val);
        }
        return -1;
    }
    public int lastIndexOf(long val){
        final int size,v;
        if((size=this.size)!=0&&val==(v=(byte)val)){
            return uncheckedLastIndexOfMatch(size,v);
        }
        return -1;
    }
    public int lastIndexOf(Object val){
        final int size;
        if((size=this.size)!=0&&val instanceof Byte){
            return uncheckedLastIndexOfMatch(size,(byte)val);
        }
        return -1;
    }
    public Byte remove(int index){
        return removeByteAt(index);
    }
    public Byte set(int index,Byte val){
        return set(index,(byte)val);
    }
    protected abstract void add(int index,byte val);
    protected abstract byte getByte(int index);
    protected abstract byte removeByteAt(int index);
    protected abstract byte set(int index,byte val);
    protected abstract int uncheckedLastIndexOfMatch(int size,int val);
}
