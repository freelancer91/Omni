package omni.impl.seq;
import omni.util.TypeUtil;
abstract interface ByteSubListDefault extends ByteListDefault
{
  @Override
  public default boolean add(Byte val){
    return add((byte)val);
  }
  @Override
  public default boolean add(boolean val){
    return add((byte)TypeUtil.castToByte(val));
  }
}
