package omni.impl;
public abstract class AbstractLongItr
{
  public Long next()
  {
    return nextLong();
  }
  protected abstract long nextLong();
}
