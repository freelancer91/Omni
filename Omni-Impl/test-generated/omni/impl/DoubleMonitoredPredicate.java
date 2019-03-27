package omni.impl;
import java.util.function.Predicate;
import java.util.function.DoublePredicate;
import java.util.Random;
import java.util.HashSet;
import omni.api.OmniCollection;
public abstract class DoubleMonitoredPredicate implements DoublePredicate
  ,Predicate<Double>
{
  public final HashSet<Double> removedVals=new HashSet<>();
  public int callCounter;
  abstract boolean testImpl(double val);
  @Override public DoubleMonitoredPredicate negate(){
    //not worth implementing but must declare
    return null;
  }
  @Override public boolean test(double val)
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
  @Override public boolean test(Double val)
  {
    return test((double)val);
  }
  public static class RemoveAll extends DoubleMonitoredPredicate
  {
    @Override boolean testImpl(double val){
      return true;
    }
  }
  public static class RemoveNone extends DoubleMonitoredPredicate
  {
    @Override boolean testImpl(double val){
      return false;
    }
  }
  public static class NonThrowing extends DoubleMonitoredPredicate
  {
    final Random rand;
    final double threshold;
    public NonThrowing(Random rand,double threshold){
      this.rand=rand;
      this.threshold=threshold;
    }
    @Override boolean testImpl(double val){
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
    @Override boolean testImpl(double val){
      if(callCounter>rand.nextInt(numExpectedCalls))
      {
        throw new IndexOutOfBoundsException();
      }
      return super.testImpl(val);
    }
  }
  public static class Modding extends Throwing
  {
    final OmniCollection.OfDouble col;
    public Modding(Random rand,int numExpectedCalls,OmniCollection.OfDouble col){
      super(rand,numExpectedCalls);
      this.col=col;
    }
    @Override boolean testImpl(double val){
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
    public ModdingAndThrowing(Random rand,int numExpectedCalls,OmniCollection.OfDouble col){
      super(rand,numExpectedCalls,col);
    }
    @Override boolean testImpl(double val){
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
