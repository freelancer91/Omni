package omni.impl.seq;
import omni.util.TypeUtil;
abstract interface DoubleSubListDefault extends DoubleListDefault
{
  //@Override
  //public default boolean add(Double val){
  //  return add((double)val);
  //}
  @Override
  public default boolean add(boolean val){
    return add((double)TypeUtil.castToDouble(val));
  }
  @Override
  public default boolean add(int val)
  {
    return add((double)val);
  }
  @Override
  public default boolean add(long val)
  {
    return add((double)val);
  }
  @Override
  public default boolean add(float val)
  {
    return add((double)val);
  }
}
