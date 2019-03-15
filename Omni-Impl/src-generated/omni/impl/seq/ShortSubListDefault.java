package omni.impl.seq;
import omni.util.TypeUtil;
abstract interface ShortSubListDefault extends ShortListDefault
{
  @Override
  public default boolean add(Short val){
    return add((short)val);
  }
  @Override
  public default boolean add(boolean val){
    return add((short)(short)TypeUtil.castToByte(val));
  }
}
