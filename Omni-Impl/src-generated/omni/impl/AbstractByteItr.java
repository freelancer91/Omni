package omni.impl;
import omni.api.OmniIterator;
public abstract class AbstractByteItr implements OmniIterator.OfByte{
  @Override public Byte next(){
    return nextByte();
  }
}
