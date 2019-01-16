package omni.impl;
import omni.api.OmniIterator;
public abstract class AbstractLongItr implements OmniIterator.OfLong{
  @Override public Long next(){
    return nextLong();
  }
}
