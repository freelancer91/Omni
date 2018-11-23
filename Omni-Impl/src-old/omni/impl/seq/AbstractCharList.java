package omni.impl.seq;
import omni.util.TypeUtil;
public abstract class AbstractCharList extends AbstractSeq.Of16BitPrimitive{
    protected AbstractCharList(){
        super();
    }
    protected AbstractCharList(int size){
        super(size);
    }
    public void add(int index,Character val){
        add(index,(char)val);
    }
    public Character get(int index){
        return getChar(index);
    }
    public int lastIndexOf(boolean val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedLastIndexOfMatch(size,TypeUtil.castToChar(val));
        }
        return -1;
    }
    public int lastIndexOf(char val){
        final int size;
        if((size=this.size)!=0){
            return uncheckedLastIndexOfMatch(size,val);
        }
        return -1;
    }
    public int lastIndexOf(double val){
        final int size,v;
        if((size=this.size)!=0&&val==(v=(char)val)){
            return uncheckedLastIndexOfMatch(size,v);
        }
        return -1;
    }
    public int lastIndexOf(float val){
        final int size,v;
        if((size=this.size)!=0&&val==(v=(char)val)){
            return uncheckedLastIndexOfMatch(size,v);
        }
        return -1;
    }
    public int lastIndexOf(int val){
        final int size;
        if((size=this.size)!=0&&val==(char)val){
            return uncheckedLastIndexOfMatch(size,val);
        }
        return -1;
    }
    public int lastIndexOf(long val){
        final int size,v;
        if((size=this.size)!=0&&val==(v=(char)val)){
            return uncheckedLastIndexOfMatch(size,v);
        }
        return -1;
    }
    public int lastIndexOf(Object val){
        final int size;
        if((size=this.size)!=0&&val instanceof Character){
            return uncheckedLastIndexOfMatch(size,(char)val);
        }
        return -1;
    }
    public int lastIndexOf(short val){
        final int size;
        if(val>=0&&(size=this.size)!=0){
            return uncheckedLastIndexOfMatch(size,val);
        }
        return -1;
    }
    public Character remove(int index){
        return removeCharAt(index);
    }
    public Character set(int index,Character val){
        return set(index,(char)val);
    }
    protected abstract void add(int index,char val);
    protected abstract char getChar(int index);
    protected abstract char removeCharAt(int index);
    protected abstract char set(int index,char val);
    protected abstract int uncheckedLastIndexOfMatch(int size,int val);
}
