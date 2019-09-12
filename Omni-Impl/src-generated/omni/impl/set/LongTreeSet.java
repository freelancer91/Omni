package omni.impl.set;
import java.util.function.IntFunction;
import omni.api.OmniSet;
import omni.impl.AbstractOmniCollection;
import omni.api.OmniIterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.LongConsumer;
import java.util.function.LongPredicate;
/**
 * TODO make not abstract
 */
public abstract class  LongTreeSet
extends AbstractOmniCollection<Long>
implements OmniSet.OfLong{
  private static final long serialVersionUID=1L;
  static class Node{
    transient long val;
    transient Node left;
    transient Node right;
    transient Node parent;
    transient boolean color;
    Node(long val){
      this.val=val;
    }
  }
  transient Node root;
  LongTreeSet(){
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
  public OmniIterator.OfLong iterator(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  public void forEach(LongConsumer action){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  public void forEach(Consumer<? super Long> action){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  public boolean removeIf(LongPredicate filter){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  public boolean removeIf(Predicate<? super Long> filter){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  public long[] toLongArray(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  } 
  public Long[] toArray(){
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
