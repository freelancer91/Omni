package omni.impl;
import omni.api.OmniIterator;
public abstract class AbstractCharItr implements OmniIterator.OfChar
{
  @Override
  public Character next()
  {
    return (Character)(nextChar());
  }
  @Override
  public double nextDouble()
  {
    return (double)(nextChar());
  }
  @Override
  public float nextFloat()
  {
    return (float)(nextChar());
  }
  @Override
  public long nextLong()
  {
    return (long)(nextChar());
  }
  @Override
  public int nextInt()
  {
    return (int)(nextChar());
  }
}
