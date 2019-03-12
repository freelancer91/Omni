package omni.impl.seq;
import omni.api.OmniList;
abstract interface ShortListDefault extends OmniList.OfShort
{
  @Override
  public default void add(int index,Short val)
  {
    add(index,(short)val);
  }
  @Override
  public default Short get(int index)
  {
    return getShort(index);
  }
  @Override
  public default Short set(int index,Short val)
  {
    return set(index,(short)val);
  }
  @Override
  public default  Short remove(int index)
  {
    return removeShortAt(index);
  }
}
