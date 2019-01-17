package omni.impl;
import omni.api.OmniIterator;
public abstract class AbstractCharItr implements OmniIterator.OfChar
{
  @Override
  public Character next()
  {
    return nextChar();
  }
}
