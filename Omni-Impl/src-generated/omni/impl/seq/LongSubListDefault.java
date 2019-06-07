package omni.impl.seq;
import omni.util.TypeUtil;
abstract interface LongSubListDefault extends LongListDefault
{
  @Override
  public default boolean add(Long val){
    return add((long)val);
  }
  @Override
  public default boolean add(boolean val){
    return add((long)TypeUtil.castToLong(val));
  }
  @Override
  public default boolean add(char val){
    return add((long)val);
  }
  @Override
  public default boolean add(byte val){
    return add((long)val);
  }
  @Override
  public default boolean add(int val)
  {
    return add((long)val);
  }
}
