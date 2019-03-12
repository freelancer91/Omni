package omni.impl.seq;
import omni.api.OmniList;
abstract interface LongListDefault extends OmniList.OfLong
{
  @Override
  public default void add(int index,Long val)
  {
    add(index,(long)val);
  }
  @Override
  public default Long get(int index)
  {
    return getLong(index);
  }
  @Override
  public default Long set(int index,Long val)
  {
    return set(index,(long)val);
  }
  @Override
  public default  Long remove(int index)
  {
    return removeLongAt(index);
  }
}
