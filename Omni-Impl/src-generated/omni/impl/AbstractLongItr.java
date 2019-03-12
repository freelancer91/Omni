package omni.impl;
import omni.api.OmniIterator;
public abstract class AbstractLongItr implements OmniIterator.OfLong
{
  @Override
  public Long next()
  {
    return (Long)(nextLong());
  }
  @Override
  public double nextDouble()
  {
    return (double)(nextLong());
  }
  @Override
  public float nextFloat()
  {
    return (float)(nextLong());
  }
}
