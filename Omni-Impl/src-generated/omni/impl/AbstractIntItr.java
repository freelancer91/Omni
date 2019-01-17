package omni.impl;
import omni.api.OmniIterator;
public abstract class AbstractIntItr implements OmniIterator.OfInt
{
  @Override
  public Integer next()
  {
    return nextInt();
  }
}
