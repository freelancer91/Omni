package omni.impl;
import omni.api.OmniIterator;
public abstract class AbstractShortItr implements OmniIterator.OfShort
{
  @Override
  public Short next()
  {
    return (Short)(nextShort());
  }
  @Override
  public double nextDouble()
  {
    return (double)(nextShort());
  }
  @Override
  public float nextFloat()
  {
    return (float)(nextShort());
  }
  @Override
  public long nextLong()
  {
    return (long)(nextShort());
  }
  @Override
  public int nextInt()
  {
    return (int)(nextShort());
  }
}
