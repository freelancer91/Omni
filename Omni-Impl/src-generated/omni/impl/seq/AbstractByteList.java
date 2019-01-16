package omni.impl.seq;
import omni.api.OmniCollection;
import omni.util.TypeUtil;
abstract class AbstractByteList extends AbstractSeq implements OmniCollection.OfByte{
  protected AbstractByteList(){
    super();
  }
  protected AbstractByteList(int size){
    super(size);
  }
  @Override public final boolean add(boolean val){
    return add(TypeUtil.castToByte(val));
  }
  @Override public final boolean add(Byte val){
    return add((byte)val);
  }
  public final void add(int index,Byte val){
    add(index,(byte)val);
  }
  public final Byte get(int index){
    return getByte(index);
  }
  public final Byte remove(int index){
    return removeByteAt(index);
  }
  public final Byte set(int index,Byte val){
    return set(index,(byte)val);
  }
  protected abstract void add(int index,byte val);
  protected abstract byte getByte(int index);
  protected abstract byte removeByteAt(int index);
  protected abstract byte set(int index,byte val);
}
