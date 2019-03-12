package omni.impl;
import omni.api.OmniIterator;
public abstract class AbstractFloatItr implements OmniIterator.OfFloat
{
  @Override
  public Float next()
  {
    return (Float)(nextFloat());
  }
  @Override
  public double nextDouble()
  {
    return (double)(nextFloat());
  }
}
