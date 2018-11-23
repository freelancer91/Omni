package omni.impl;
public abstract class AbstractBooleanItr{
  public Boolean next(){
    return nextBoolean();
  }
  protected abstract boolean nextBoolean();
}
