package omni.impl.seq;
import omni.api.OmniList;
abstract interface IntListDefault extends OmniList.OfInt
{
  @Override
  public default void add(int index,Integer val)
  {
    add(index,(int)val);
  }
  @Override
  public default Integer get(int index)
  {
    return getInt(index);
  }
  @Override
  public default Integer set(int index,Integer val)
  {
    return set(index,(int)val);
  }
  @Override
  public default  Integer remove(int index)
  {
    return removeIntAt(index);
  }
}
