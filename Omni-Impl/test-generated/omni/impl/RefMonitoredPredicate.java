package omni.impl;
import java.util.function.Predicate;
import java.util.Random;
import java.util.HashSet;
import omni.api.OmniCollection;
public abstract class RefMonitoredPredicate<E> implements Predicate<E>
{
  public final HashSet<E> removedVals=new HashSet<>();
  public int callCounter;
  abstract boolean testImpl(E val);
  @Override public RefMonitoredPredicate<E> negate(){
    //not worth implementing but must declare
    return null;
  }
  @Override public boolean test(E val)
  {
    ++callCounter;
    if(removedVals.contains(val))
    {
      return true;
    }
    if(testImpl(val))
    {
      removedVals.add(val);
      return true;
    }
    return false;
  }
  public static class RemoveAll<E> extends RefMonitoredPredicate<E>
  {
    @Override boolean testImpl(E val){
      return true;
    }
  }
  public static class RemoveNone<E> extends RefMonitoredPredicate<E>
  {
    @Override boolean testImpl(E val){
      return false;
    }
  }
  public static class NonThrowing<E> extends RefMonitoredPredicate<E>
  {
    final Random rand;
    final double threshold;
    public NonThrowing(Random rand,double threshold){
      this.rand=rand;
      this.threshold=threshold;
    }
    @Override boolean testImpl(E val){
      return rand.nextDouble()>=threshold;
    }
  }
  public static class Throwing<E> extends NonThrowing<E>
  {
    final int numExpectedCalls;
    public Throwing(Random rand,int numExpectedCalls){
      super(rand,.5);
      this.numExpectedCalls=numExpectedCalls;
    }
    @Override boolean testImpl(E val){
      if(callCounter>rand.nextInt(numExpectedCalls))
      {
        throw new IndexOutOfBoundsException();
      }
      return super.testImpl(val);
    }
  }
  public static class Modding<E> extends Throwing<E>
  {
    final OmniCollection.OfRef<E> col;
    public Modding(Random rand,int numExpectedCalls,OmniCollection.OfRef<E> col){
      super(rand,numExpectedCalls);
      this.col=col;
    }
    @Override boolean testImpl(E val){
      if(callCounter>rand.nextInt(numExpectedCalls))
      {
        col.remove(val);
        col.add(val);
      }
      return rand.nextBoolean();
    }
  }
  public static class ModdingAndThrowing<E> extends Modding<E>
  {
    public ModdingAndThrowing(Random rand,int numExpectedCalls,OmniCollection.OfRef<E> col){
      super(rand,numExpectedCalls,col);
    }
    @Override boolean testImpl(E val){
      if(callCounter>rand.nextInt(numExpectedCalls))
      {
        col.remove(val);
        col.add(val);
        throw new IndexOutOfBoundsException();
      }
      return rand.nextBoolean();
    }
  }
}
