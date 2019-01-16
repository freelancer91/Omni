package omni.impl;
import omni.api.OmniIterator;
public abstract class AbstractShortItr implements OmniIterator.OfShort{
  @Override public Short next(){
    return nextShort();
  }
}
