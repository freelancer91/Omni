package omni.impl;
public abstract class AbstractCharItr
{
  public Character next()
  {
    return nextChar();
  }
  protected abstract char nextChar();
}
