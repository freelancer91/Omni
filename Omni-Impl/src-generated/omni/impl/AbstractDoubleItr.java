package omni.impl;
import omni.api.OmniIterator;
public abstract class AbstractDoubleItr implements OmniIterator.OfDouble
{
  @Override public abstract Object clone();
  @Override
  public Double next()
  {
    return (Double)(nextDouble());
  }
}
