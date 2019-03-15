package omni.impl.seq;
import omni.util.TypeUtil;
abstract interface CharSubListDefault extends CharListDefault
{
  @Override
  public default boolean add(Character val){
    return add((char)val);
  }
  @Override
  public default boolean add(boolean val){
    return add((char)TypeUtil.castToChar(val));
  }
}
