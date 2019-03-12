package omni.impl.seq;
import omni.api.OmniList;
abstract interface DoubleListDefault extends OmniList.OfDouble
{
  @Override
  public default void add(int index,Double val)
  {
    add(index,(double)val);
  }
  @Override
  public default Double get(int index)
  {
    return getDouble(index);
  }
  @Override
  public default Double set(int index,Double val)
  {
    return set(index,(double)val);
  }
  @Override
  public default  Double remove(int index)
  {
    return removeDoubleAt(index);
  }
}
