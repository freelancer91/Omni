package omni.impl.seq;
import omni.util.TypeUtil;
public abstract class AbstractCharList extends AbstractSeq.Of16BitPrimitive{
  protected AbstractCharList(){
    super();
  }
  protected AbstractCharList(int size){
    super(size);
  }
  public int lastIndexOf(boolean val){
    final int size;
    if((size=this.size)!=0){ return uncheckedLastIndexOf(size,TypeUtil.castToChar(val)); }
    return -1;
  }
  public int lastIndexOf(char val){
    final int size;
    if((size=this.size)!=0){ return uncheckedLastIndexOf(size,val); }
    return -1;
  }
  public int lastIndexOf(double val){
    final int size,v;
    if((size=this.size)!=0&&val==(v=(char)val)){ return uncheckedLastIndexOf(size,v); }
    return -1;
  }
  public int lastIndexOf(float val){
    final int size,v;
    if((size=this.size)!=0&&val==(v=(char)val)){ return uncheckedLastIndexOf(size,v); }
    return -1;
  }
  public int lastIndexOf(int val){
    final int size;
    if((size=this.size)!=0&&val==(char)val){ return uncheckedLastIndexOf(size,val); }
    return -1;
  }
  public int lastIndexOf(long val){
    final int size,v;
    if((size=this.size)!=0&&val==(v=(char)val)){ return uncheckedLastIndexOf(size,v); }
    return -1;
  }
  public int lastIndexOf(Object val){
    final int size;
    if((size=this.size)!=0&&val instanceof Character){ return uncheckedLastIndexOf(size,(char)val); }
    return -1;
  }
  public int lastIndexOf(short val){
    final int size;
    if(val>=0&&(size=this.size)!=0){ return uncheckedLastIndexOf(size,val); }
    return -1;
  }
  protected abstract int uncheckedLastIndexOf(int size,int val);
}
