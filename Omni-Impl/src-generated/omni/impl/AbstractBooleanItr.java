package omni.impl;
import omni.api.OmniIterator;
public abstract class AbstractBooleanItr implements OmniIterator.OfBoolean
{
  @Override
  public Boolean next()
  {
    return nextBoolean();
  }
}
