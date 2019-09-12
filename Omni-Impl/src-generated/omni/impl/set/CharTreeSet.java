package omni.impl.set;
import java.util.function.IntFunction;
import omni.api.OmniSet;
import omni.impl.AbstractOmniCollection;
import omni.api.OmniIterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import omni.function.CharConsumer;
import omni.function.CharPredicate;
/**
 * TODO make not abstract
 */
public abstract class  CharTreeSet
extends AbstractOmniCollection<Character>
implements OmniSet.OfChar{
  private static final long serialVersionUID=1L;
  static class Node{
    transient char val;
    transient Node left;
    transient Node right;
    transient Node parent;
    transient boolean color;
    Node(char val){
      this.val=val;
    }
  }
  transient Node root;
  CharTreeSet(){
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
  public OmniIterator.OfChar iterator(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  public void forEach(CharConsumer action){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  public void forEach(Consumer<? super Character> action){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  public boolean removeIf(CharPredicate filter){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  public boolean removeIf(Predicate<? super Character> filter){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  public char[] toCharArray(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  } 
  public Character[] toArray(){
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
