package omni.impl;
import java.util.function.Predicate;
import omni.function.ShortPredicate;
import java.util.Random;
import java.util.HashSet;
import omni.api.OmniCollection;
public abstract class ShortMonitoredPredicate implements ShortPredicate
  ,Predicate<Short>
{
  public final HashSet<Short> removedVals=new HashSet<>();
  public int callCounter;
  abstract boolean testImpl(short val);
  @Override public ShortMonitoredPredicate negate(){
    //not worth implementing but must declare
    return null;
  }
  @Override public boolean test(short val)
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
  @Override public boolean test(Short val)
  {
    return test((short)val);
  }
  public static class RemoveAll extends ShortMonitoredPredicate
  {
    @Override boolean testImpl(short val){
      return true;
    }
  }
  public static class RemoveNone extends ShortMonitoredPredicate
  {
    @Override boolean testImpl(short val){
      return false;
    }
  }
  public static class NonThrowing extends ShortMonitoredPredicate
  {
    final Random rand;
    final double threshold;
    public NonThrowing(Random rand,double threshold){
      this.rand=rand;
      this.threshold=threshold;
    }
    @Override boolean testImpl(short val){
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
    @Override boolean testImpl(short val){
      if(callCounter>rand.nextInt(numExpectedCalls))
      {
        throw new IndexOutOfBoundsException();
      }
      return super.testImpl(val);
    }
  }
  public static class Modding extends Throwing
  {
    final OmniCollection.OfShort col;
    public Modding(Random rand,int numExpectedCalls,OmniCollection.OfShort col){
      super(rand,numExpectedCalls);
      this.col=col;
    }
    @Override boolean testImpl(short val){
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
    public ModdingAndThrowing(Random rand,int numExpectedCalls,OmniCollection.OfShort col){
      super(rand,numExpectedCalls,col);
    }
    @Override boolean testImpl(short val){
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
