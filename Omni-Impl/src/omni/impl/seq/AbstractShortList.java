package omni.impl.seq;
import omni.util.TypeUtil;
public abstract class AbstractShortList extends AbstractSeq.Of16BitPrimitive{
  protected AbstractShortList(){
    super();
  }
  protected AbstractShortList(int size){
    super(size);
  }
  public int lastIndexOf(boolean val){
    final int size;
    if((size=this.size)!=0){ return uncheckedLastIndexOf(size,TypeUtil.castToByte(val)); }
    return -1;
  }
  public int lastIndexOf(char val){
    final int size;
    if(val<=Short.MAX_VALUE&&(size=this.size)!=0){ return uncheckedLastIndexOf(size,val); }
    return -1;
  }
  public int lastIndexOf(double val){
    final int size,v;
    if((size=this.size)!=0&&val==(v=(short)val)){ return uncheckedLastIndexOf(size,v); }
    return -1;
  }
  public int lastIndexOf(float val){
    final int size,v;
    if((size=this.size)!=0&&val==(v=(short)val)){ return uncheckedLastIndexOf(size,v); }
    return -1;
  }
  public int lastIndexOf(int val){
    final int size;
    if((size=this.size)!=0&&val==(short)val){ return uncheckedLastIndexOf(size,val); }
    return -1;
  }
  public int lastIndexOf(long val){
    final int size,v;
    if((size=this.size)!=0&&val==(v=(short)val)){ return uncheckedLastIndexOf(size,v); }
    return -1;
  }
  public int lastIndexOf(Object val){
    final int size;
    if((size=this.size)!=0&&val instanceof Short){ return uncheckedLastIndexOf(size,(short)val); }
    return -1;
  }
  public int lastIndexOf(short val){
    final int size;
    if((size=this.size)!=0){ return uncheckedLastIndexOf(size,val); }
    return -1;
  }
  protected abstract int uncheckedLastIndexOf(int size,int val);
}
