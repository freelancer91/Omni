package omni.impl;
public abstract class AbstractShortItr
{
  public Short next()
  {
    return nextShort();
  }
  protected abstract short nextShort();
}
