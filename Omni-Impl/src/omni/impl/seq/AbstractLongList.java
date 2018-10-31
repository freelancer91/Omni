package omni.impl.seq;
import omni.util.TypeUtil;
public abstract class AbstractLongList extends AbstractSeq.OfSignedIntegralPrimitive{
  protected AbstractLongList(){
    super();
  }
  protected AbstractLongList(int size){
    super(size);
  }
  public int lastIndexOf(boolean val){
    final int size;
    if((size=this.size)!=0){ return uncheckedLastIndexOf(size,TypeUtil.castToLong(val)); }
    return -1;
  }
  public int lastIndexOf(double val){
    final int size;
    final long v;
    if((size=this.size)!=0&&TypeUtil.doubleEquals(val,v=(long)val)){ return uncheckedLastIndexOf(size,v); }
    return -1;
  }
  public int lastIndexOf(float val){
    final int size;
    final long v;
    if((size=this.size)!=0&&TypeUtil.floatEquals(val,v=(long)val)){ return uncheckedLastIndexOf(size,v); }
    return -1;
  }
  public int lastIndexOf(int val){
    final int size;
    if((size=this.size)!=0){ return uncheckedLastIndexOf(size,val); }
    return -1;
  }
  public int lastIndexOf(long val){
    final int size;
    if((size=this.size)!=0){ return uncheckedLastIndexOf(size,val); }
    return -1;
  }
  public int lastIndexOf(Object val){
    final int size;
    if((size=this.size)!=0&&val instanceof Long){ return uncheckedLastIndexOf(size,(long)val); }
    return -1;
  }
  protected abstract int uncheckedLastIndexOf(int size,long val);
}
