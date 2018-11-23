package omni.impl;
public abstract class AbstractFloatItr
{
  public Float next()
  {
    return nextFloat();
  }
  protected abstract float nextFloat();
}
