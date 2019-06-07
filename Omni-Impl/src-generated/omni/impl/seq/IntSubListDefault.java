package omni.impl.seq;
import omni.util.TypeUtil;
abstract interface IntSubListDefault extends IntListDefault
{
  @Override
  public default boolean add(Integer val){
    return add((int)val);
  }
  @Override
  public default boolean add(boolean val){
    return add((int)(int)TypeUtil.castToByte(val));
  }
  @Override
  public default boolean add(char val){
    return add((int)val);
  }
  @Override
  public default boolean add(byte val){
    return add((int)val);
  }
}
