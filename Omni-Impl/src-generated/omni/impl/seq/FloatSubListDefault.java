package omni.impl.seq;
import omni.util.TypeUtil;
abstract interface FloatSubListDefault extends FloatListDefault
{
  //@Override
  //public default boolean add(Float val){
  //  return add((float)val);
  //}
  @Override
  public default boolean add(boolean val){
    return add((float)TypeUtil.castToFloat(val));
  }
  @Override
  public default boolean add(int val)
  {
    return add((float)val);
  }
  @Override
  public default boolean add(char val)
  {
    return add((float)val);
  }
  @Override
  public default boolean add(short val)
  {
    return add((float)val);
  }
  @Override
  public default boolean add(long val)
  {
    return add((float)val);
  }
}
