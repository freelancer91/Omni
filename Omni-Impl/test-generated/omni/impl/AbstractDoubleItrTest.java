package omni.impl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.util.doubleArrayBuilder;
import omni.util.EqualityUtil;
import omni.util.ArrCopy;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
public class AbstractDoubleItrTest 
{
  private static class TestItr extends AbstractDoubleItr implements Cloneable
  {
    double[] arr;
    int index;
    TestItr(){
      this.index=0;
      this.arr=new double[1000];
      doubleArrayBuilder.Randomized.buildUnchecked(this.arr,0,arr.length,new Random(),0);
    }
    private TestItr(int index,double[] arr)
    {
      this.index=index;
      this.arr=arr;
    }
    public Object clone()
    {
      double[] copy=new double[arr.length];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
      return new TestItr(this.index,copy);
    }
    @Override
    public void remove(){
      throw new UnsupportedOperationException();
    }
    @Override
    public void forEachRemaining(DoubleConsumer action){
      throw new UnsupportedOperationException();
    }
    @Override
    public void forEachRemaining(Consumer<? super Double> action){
      throw new UnsupportedOperationException();
    }
    @Override
    public boolean hasNext(){
      return this.index<arr.length;
    }
    @Override
    public double nextDouble()
    {
      return (double)arr[this.index++];
    }
  };
  @Test
  public void testNext()
  {
    TestItr itr=new TestItr();
    TestItr copy=(TestItr)itr.clone();
    while(itr.hasNext())
    {
      Assertions.assertTrue(EqualityUtil.isEqual((Double)(itr.nextDouble()),copy.next()));
    }
  }
}
