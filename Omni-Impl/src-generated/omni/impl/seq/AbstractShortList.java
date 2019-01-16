package omni.impl.seq;
import omni.api.OmniCollection;
import omni.util.TypeUtil;
abstract class AbstractShortList extends AbstractSeq.Of16BitPrimitive implements OmniCollection.OfShort{
  protected AbstractShortList(){
    super();
  }
  protected AbstractShortList(int size){
    super(size);
  }
  @Override public final boolean add(boolean val){
    return add((short)TypeUtil.castToByte(val));
  }
  public final void add(int index,Short val){
    add(index,(short)val);
  }
  @Override public final boolean add(Short val){
    return add((short)val);
  }
  public final Short get(int index){
    return getShort(index);
  }
  public final Short remove(int index){
    return removeShortAt(index);
  }
  public final Short set(int index,Short val){
    return set(index,(short)val);
  }
  protected abstract void add(int index,short val);
  protected abstract short getShort(int index);
  protected abstract short removeShortAt(int index);
  protected abstract short set(int index,short val);
}
