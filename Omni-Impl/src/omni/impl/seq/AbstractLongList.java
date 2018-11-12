package omni.impl.seq;
import omni.util.TypeUtil;
public abstract class AbstractLongList extends AbstractSeq.OfSignedIntegralPrimitive{
  protected AbstractLongList(){
    super();
  }
  protected AbstractLongList(int size){
    super(size);
  }
  protected abstract long getLong(int index);
  public Long get(int index){
    return getLong(index);
  }
  protected abstract long removeLongAt(int index);
  public Long remove(int index){
    return removeLongAt(index);
  }
  protected abstract long set(int index,long val);
  public Long set(int index,Long val){
    return set(index,(long)val);
  }
  protected abstract void add(int index,long val);
  public void add(int index,Long val){
    add(index,(long)val);
  }
  public int lastIndexOf(boolean val){
    final int size;
    if((size=this.size)!=0){ return uncheckedLastIndexOfMatch(size,TypeUtil.castToLong(val)); }
    return -1;
  }
  public int lastIndexOf(double val){
    final int size;
    final long v;
    if((size=this.size)!=0&&TypeUtil.doubleEquals(val,v=(long)val)){ return uncheckedLastIndexOfMatch(size,v); }
    return -1;
  }
  public int lastIndexOf(float val){
    final int size;
    final long v;
    if((size=this.size)!=0&&TypeUtil.floatEquals(val,v=(long)val)){ return uncheckedLastIndexOfMatch(size,v); }
    return -1;
  }
  public int lastIndexOf(int val){
    final int size;
    if((size=this.size)!=0){ return uncheckedLastIndexOfMatch(size,val); }
    return -1;
  }
  public int lastIndexOf(long val){
    final int size;
    if((size=this.size)!=0){ return uncheckedLastIndexOfMatch(size,val); }
    return -1;
  }
  public int lastIndexOf(Object val){
    final int size;
    if((size=this.size)!=0&&val instanceof Long){ return uncheckedLastIndexOfMatch(size,(long)val); }
    return -1;
  }
  protected abstract int uncheckedLastIndexOfMatch(int size,long val);
}
