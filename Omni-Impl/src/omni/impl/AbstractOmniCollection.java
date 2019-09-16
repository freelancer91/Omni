package omni.impl;

import java.io.Serializable;
import omni.api.OmniCollection;

public abstract class AbstractOmniCollection<E> implements OmniCollection<E>,Serializable,Cloneable{
  private static final long serialVersionUID=1L;
  public transient int size;
  protected AbstractOmniCollection(){
  }
  protected AbstractOmniCollection(int size){
    this.size=size;
  }
  @Override public int size() {
    return this.size;
  }
  @Override public boolean isEmpty() {
    return this.size==0;
  }
  @Override public abstract String toString();
  @Override public abstract Object clone();
  
  
  
}
