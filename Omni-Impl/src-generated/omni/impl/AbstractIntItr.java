package omni.impl;
import omni.api.OmniIterator;
public abstract class AbstractIntItr implements OmniIterator.OfInt
{
  @Override
  public Integer next()
  {
    return (Integer)(nextInt());
  }
  @Override
  public double nextDouble()
  {
    return (double)(nextInt());
  }
  @Override
  public float nextFloat()
  {
    return (float)(nextInt());
  }
  @Override
  public long nextLong()
  {
    return (long)(nextInt());
  }
}
