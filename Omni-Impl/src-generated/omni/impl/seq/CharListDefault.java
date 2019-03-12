package omni.impl.seq;
import omni.api.OmniList;
abstract interface CharListDefault extends OmniList.OfChar
{
  @Override
  public default void add(int index,Character val)
  {
    add(index,(char)val);
  }
  @Override
  public default Character get(int index)
  {
    return getChar(index);
  }
  @Override
  public default Character set(int index,Character val)
  {
    return set(index,(char)val);
  }
  @Override
  public default  Character remove(int index)
  {
    return removeCharAt(index);
  }
}
