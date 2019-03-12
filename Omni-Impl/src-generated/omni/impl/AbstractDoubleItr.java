package omni.impl;
import omni.api.OmniIterator;
public abstract class AbstractDoubleItr implements OmniIterator.OfDouble
{
  @Override
  public Double next()
  {
    return (Double)(nextDouble());
  }
}
