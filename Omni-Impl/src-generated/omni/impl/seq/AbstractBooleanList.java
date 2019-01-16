package omni.impl.seq;
import omni.api.OmniCollection;
abstract class AbstractBooleanList extends AbstractSeq implements OmniCollection.OfBoolean{
  protected AbstractBooleanList(){
    super();
  }
  protected AbstractBooleanList(int size){
    super(size);
  }
  @Override public final boolean add(Boolean val){
    return add((boolean)val);
  }
  public final void add(int index,Boolean val){
    add(index,(boolean)val);
  }
  public final Boolean get(int index){
    return getBoolean(index);
  }
  public final Boolean remove(int index){
    return removeBooleanAt(index);
  }
  public final Boolean set(int index,Boolean val){
    return set(index,(boolean)val);
  }
  protected abstract void add(int index,boolean val);
  protected abstract boolean getBoolean(int index);
  protected abstract boolean removeBooleanAt(int index);
  protected abstract boolean set(int index,boolean val);
}
