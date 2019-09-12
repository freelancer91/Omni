package omni.impl.set;
import java.util.function.IntFunction;
import omni.api.OmniSet;
import omni.impl.AbstractOmniCollection;
import omni.api.OmniIterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
/**
 * TODO make not abstract
 */
public abstract class  DoubleTreeSet
extends AbstractOmniCollection<Double>
implements OmniSet.OfDouble{
  private static final long serialVersionUID=1L;
  static class Node{
    transient double val;
    transient Node left;
    transient Node right;
    transient Node parent;
    transient boolean color;
    Node(double val){
      this.val=val;
    }
  }
  transient Node root;
  DoubleTreeSet(){
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
  public OmniIterator.OfDouble iterator(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  public void forEach(DoubleConsumer action){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  public void forEach(Consumer<? super Double> action){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  public boolean removeIf(DoublePredicate filter){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  public boolean removeIf(Predicate<? super Double> filter){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  public double[] toDoubleArray(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  } 
  public Double[] toArray(){
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
