package omni.impl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.util.floatArrayBuilder;
import omni.util.EqualityUtil;
import omni.util.ArrCopy;
import java.util.Random;
import java.util.function.Consumer;
import omni.function.FloatConsumer;
public class AbstractFloatItrTest 
{
  private static class TestItr extends AbstractFloatItr implements Cloneable
  {
    float[] arr;
    int index;
    TestItr(){
      this.index=0;
      this.arr=new float[1000];
      floatArrayBuilder.Randomized.buildUnchecked(this.arr,0,arr.length,new Random(),0);
    }
    private TestItr(int index,float[] arr)
    {
      this.index=index;
      this.arr=arr;
    }
    public Object clone()
    {
      float[] copy=new float[arr.length];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
      return new TestItr(this.index,copy);
    }
    @Override
    public void remove(){
      throw new UnsupportedOperationException();
    }
    @Override
    public void forEachRemaining(FloatConsumer action){
      throw new UnsupportedOperationException();
    }
    @Override
    public void forEachRemaining(Consumer<? super Float> action){
      throw new UnsupportedOperationException();
    }
    @Override
    public boolean hasNext(){
      return this.index<arr.length;
    }
    @Override
    public float nextFloat()
    {
      return (float)arr[this.index++];
    }
  };
  @Test
  public void testNext()
  {
    TestItr itr=new TestItr();
    TestItr copy=(TestItr)itr.clone();
    while(itr.hasNext())
    {
      Assertions.assertTrue(EqualityUtil.isEqual((Float)(itr.nextFloat()),copy.next()));
    }
  }
  @Test
  public void testNextDouble()
  {
    TestItr itr=new TestItr();
    TestItr copy=(TestItr)itr.clone();
    while(itr.hasNext())
    {
      Assertions.assertTrue(EqualityUtil.isEqual((double)(itr.nextFloat()),copy.nextDouble()));
    }
  }
}
