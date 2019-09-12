package omni.impl.set;
import java.util.function.IntFunction;
import omni.api.OmniSet;
import omni.impl.AbstractOmniCollection;
import omni.api.OmniIterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
/**
 * TODO make not abstract
 */
public abstract class  IntTreeSet
extends AbstractOmniCollection<Integer>
implements OmniSet.OfInt{
  private static final long serialVersionUID=1L;
  static class Node{
    transient int val;
    transient Node left;
    transient Node right;
    transient Node parent;
    transient boolean color;
    Node(int val){
      this.val=val;
    }
  }
  transient Node root;
  IntTreeSet(){
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
  public OmniIterator.OfInt iterator(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  public void forEach(IntConsumer action){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  public void forEach(Consumer<? super Integer> action){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  public boolean removeIf(IntPredicate filter){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  public boolean removeIf(Predicate<? super Integer> filter){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  public int[] toIntArray(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  } 
  public Integer[] toArray(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  } 
  public double[] toDoubleArray(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  } 
  public float[] toFloatArray(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  } 
  public long[] toLongArray(){
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
