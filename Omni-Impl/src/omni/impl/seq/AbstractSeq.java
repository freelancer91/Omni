package omni.impl.seq;

import java.io.Serializable;
import omni.api.OmniCollection;

public abstract class AbstractSeq implements OmniCollection,Serializable,Cloneable{
  private static final long serialVersionUID=1L;
  transient int size;
  AbstractSeq(){
  }
  AbstractSeq(int size){
    this.size=size;
  }
  @Override public int size() {
    return this.size;
  }
  @Override public boolean isEmpty() {
    return this.size==0;
  }
  @Override public abstract Object clone();
  @Override public abstract String toString();
  @Override public abstract boolean equals(Object val);
  @Override public abstract int hashCode();
}