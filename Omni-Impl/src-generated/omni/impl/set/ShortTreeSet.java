package omni.impl.set;
import java.util.function.IntFunction;
import omni.api.OmniSet;
import omni.impl.AbstractOmniCollection;
import omni.api.OmniIterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import omni.function.ShortConsumer;
import omni.function.ShortPredicate;
/**
 * TODO make not abstract
 */
public abstract class  ShortTreeSet
extends AbstractOmniCollection<Short>
implements OmniSet.OfShort{
  private static final long serialVersionUID=1L;
  static class Node{
    transient short val;
    transient Node left;
    transient Node right;
    transient Node parent;
    transient boolean color;
    Node(short val){
      this.val=val;
    }
  }
  transient Node root;
  ShortTreeSet(){
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
  public OmniIterator.OfShort iterator(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  public void forEach(ShortConsumer action){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  public void forEach(Consumer<? super Short> action){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  public boolean removeIf(ShortPredicate filter){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  public boolean removeIf(Predicate<? super Short> filter){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  public short[] toShortArray(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  } 
  public Short[] toArray(){
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
  public int[] toIntArray(){
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
