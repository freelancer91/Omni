package omni.impl;
import java.util.function.Predicate;
import java.util.function.IntPredicate;
import java.util.Random;
import java.util.HashSet;
import omni.api.OmniCollection;
public abstract class IntMonitoredPredicate implements IntPredicate
  ,Predicate<Integer>
{
  public final HashSet<Integer> removedVals=new HashSet<>();
  public int callCounter;
  abstract boolean testImpl(int val);
  @Override public IntMonitoredPredicate negate(){
    //not worth implementing but must declare
    return null;
  }
  @Override public boolean test(int val)
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
  @Override public boolean test(Integer val)
  {
    return test((int)val);
  }
  public static class RemoveAll extends IntMonitoredPredicate
  {
    @Override boolean testImpl(int val){
      return true;
    }
  }
  public static class RemoveNone extends IntMonitoredPredicate
  {
    @Override boolean testImpl(int val){
      return false;
    }
  }
  public static class NonThrowing extends IntMonitoredPredicate
  {
    final Random rand;
    final double threshold;
    public NonThrowing(Random rand,double threshold){
      this.rand=rand;
      this.threshold=threshold;
    }
    @Override boolean testImpl(int val){
      return rand.nextDouble()>=threshold;
    }
  }
  public static class Throwing extends NonThrowing
  {
    final int numExpectedCalls;
    public Throwing(Random rand,int numExpectedCalls){
      super(rand,.5);
      this.numExpectedCalls=numExpectedCalls;
    }
    @Override boolean testImpl(int val){
      if(callCounter>rand.nextInt(numExpectedCalls))
      {
        throw new IndexOutOfBoundsException();
      }
      return super.testImpl(val);
    }
  }
  public static class Modding extends Throwing
  {
    final OmniCollection.OfInt col;
    public Modding(Random rand,int numExpectedCalls,OmniCollection.OfInt col){
      super(rand,numExpectedCalls);
      this.col=col;
    }
    @Override boolean testImpl(int val){
      if(callCounter>rand.nextInt(numExpectedCalls))
      {
        col.removeVal(val);
        col.add(val);
      }
      return rand.nextBoolean();
    }
  }
  public static class ModdingAndThrowing extends Modding
  {
    public ModdingAndThrowing(Random rand,int numExpectedCalls,OmniCollection.OfInt col){
      super(rand,numExpectedCalls,col);
    }
    @Override boolean testImpl(int val){
      if(callCounter>rand.nextInt(numExpectedCalls))
      {
        col.removeVal(val);
        col.add(val);
        throw new IndexOutOfBoundsException();
      }
      return rand.nextBoolean();
    }
  }
}
