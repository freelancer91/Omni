package omni.impl.set;
import java.util.function.IntFunction;
import omni.api.OmniSet;
import omni.impl.AbstractOmniCollection;
import omni.api.OmniIterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import omni.function.FloatConsumer;
import omni.function.FloatPredicate;
/**
 * TODO make not abstract
 */
public abstract class  FloatTreeSet
extends AbstractOmniCollection<Float>
implements OmniSet.OfFloat{
  private static final long serialVersionUID=1L;
  static class Node{
    transient float val;
    transient Node left;
    transient Node right;
    transient Node parent;
    transient boolean color;
    Node(float val){
      this.val=val;
    }
  }
  transient Node root;
  FloatTreeSet(){
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
  public OmniIterator.OfFloat iterator(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  public void forEach(FloatConsumer action){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  public void forEach(Consumer<? super Float> action){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  public boolean removeIf(FloatPredicate filter){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  public boolean removeIf(Predicate<? super Float> filter){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  public float[] toFloatArray(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  } 
  public Float[] toArray(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  } 
  public double[] toDoubleArray(){
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
