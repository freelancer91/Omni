package omni.impl.seq;
import omni.api.OmniList;
abstract interface FloatListDefault extends OmniList.OfFloat
{
  @Override
  public default void add(int index,Float val)
  {
    add(index,(float)val);
  }
  @Override
  public default Float get(int index)
  {
    return getFloat(index);
  }
  @Override
  public default Float set(int index,Float val)
  {
    return set(index,(float)val);
  }
  @Override
  public default  Float remove(int index)
  {
    return removeFloatAt(index);
  }
}
