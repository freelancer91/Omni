package omni.impl.seq;
import omni.api.OmniCollection;
import omni.util.TypeUtil;
abstract class AbstractLongList extends AbstractSeq.OfSignedIntegralPrimitive implements OmniCollection.OfLong{
  protected AbstractLongList(){
    super();
  }
  protected AbstractLongList(int size){
    super(size);
  }
  @Override public final boolean add(boolean val){
    return add(TypeUtil.castToLong(val));
  }
  @Override public final boolean add(int val){
    return add((long)val);
  }
  public final void add(int index,Long val){
    add(index,(long)val);
  }
  @Override public final boolean add(Long val){
    return add((long)val);
  }
  public final Long get(int index){
    return getLong(index);
  }
  public final Long remove(int index){
    return removeLongAt(index);
  }
  public final Long set(int index,Long val){
    return set(index,(long)val);
  }
  protected abstract void add(int index,long val);
  protected abstract long getLong(int index);
  protected abstract long removeLongAt(int index);
  protected abstract long set(int index,long val);
}
