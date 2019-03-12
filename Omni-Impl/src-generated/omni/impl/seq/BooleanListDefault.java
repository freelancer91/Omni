package omni.impl.seq;
import omni.api.OmniList;
abstract interface BooleanListDefault extends OmniList.OfBoolean
{
  @Override
  public default void add(int index,Boolean val)
  {
    add(index,(boolean)val);
  }
  @Override
  public default Boolean get(int index)
  {
    return getBoolean(index);
  }
  @Override
  public default Boolean set(int index,Boolean val)
  {
    return set(index,(boolean)val);
  }
  @Override
  public default  Boolean remove(int index)
  {
    return removeBooleanAt(index);
  }
}
