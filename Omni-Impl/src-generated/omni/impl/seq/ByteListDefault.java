package omni.impl.seq;
import omni.api.OmniList;
abstract interface ByteListDefault extends OmniList.OfByte
{
  @Override
  public default void add(int index,Byte val)
  {
    add(index,(byte)val);
  }
  @Override
  public default Byte get(int index)
  {
    return getByte(index);
  }
  @Override
  public default Byte set(int index,Byte val)
  {
    return set(index,(byte)val);
  }
  @Override
  public default  Byte remove(int index)
  {
    return removeByteAt(index);
  }
}
