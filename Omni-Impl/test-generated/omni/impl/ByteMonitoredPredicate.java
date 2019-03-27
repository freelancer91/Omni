package omni.impl;
import java.util.function.Predicate;
import omni.function.BytePredicate;
import java.util.Random;
import java.util.HashSet;
import omni.api.OmniCollection;
public abstract class ByteMonitoredPredicate implements BytePredicate
  ,Predicate<Byte>
{
  public final HashSet<Byte> removedVals=new HashSet<>();
  public int callCounter;
  abstract boolean testImpl(byte val);
  @Override public ByteMonitoredPredicate negate(){
    //not worth implementing but must declare
    return null;
  }
  @Override public boolean test(byte val)
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
  @Override public boolean test(Byte val)
  {
    return test((byte)val);
  }
  public static class RemoveAll extends ByteMonitoredPredicate
  {
    @Override boolean testImpl(byte val){
      return true;
    }
  }
  public static class RemoveNone extends ByteMonitoredPredicate
  {
    @Override boolean testImpl(byte val){
      return false;
    }
  }
  public static class NonThrowing extends ByteMonitoredPredicate
  {
    final Random rand;
    final double threshold;
    public NonThrowing(Random rand,double threshold){
      this.rand=rand;
      this.threshold=threshold;
    }
    @Override boolean testImpl(byte val){
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
    @Override boolean testImpl(byte val){
      if(callCounter>rand.nextInt(numExpectedCalls))
      {
        throw new IndexOutOfBoundsException();
      }
      return super.testImpl(val);
    }
  }
  public static class Modding extends Throwing
  {
    final OmniCollection.OfByte col;
    public Modding(Random rand,int numExpectedCalls,OmniCollection.OfByte col){
      super(rand,numExpectedCalls);
      this.col=col;
    }
    @Override boolean testImpl(byte val){
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
    public ModdingAndThrowing(Random rand,int numExpectedCalls,OmniCollection.OfByte col){
      super(rand,numExpectedCalls,col);
    }
    @Override boolean testImpl(byte val){
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
