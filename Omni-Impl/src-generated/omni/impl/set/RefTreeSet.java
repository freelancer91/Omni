package omni.impl.set;
import java.util.function.IntFunction;
import omni.api.OmniSet;
import omni.impl.AbstractOmniCollection;
import omni.api.OmniIterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
/**
 * TODO make not abstract
 */
public abstract class  RefTreeSet<E>
extends AbstractOmniCollection<E>
implements OmniSet.OfRef<E>{
  private static final long serialVersionUID=1L;
  static class Node<E>{
    transient E val;
    transient Node<E> left;
    transient Node<E> right;
    transient Node<E> parent;
    transient boolean color;
    Node(E val){
      this.val=val;
    }
  }
  transient Node<E> root;
  RefTreeSet(){
    super();
  }
  public Object clone(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  public String toString(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  public OmniIterator.OfRef<E> iterator(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  public void forEach(Consumer<? super E> action){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  public boolean removeIf(Predicate<? super E> filter){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  public Object[] toArray(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  } 
  public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  public <T> T[] toArray(T[] dst){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  public void clear(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
}
