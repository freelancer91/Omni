package omni.impl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.util.intArrayBuilder;
import omni.util.EqualityUtil;
import omni.util.ArrCopy;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
public class AbstractIntItrTest 
{
  private static class TestItr extends AbstractIntItr implements Cloneable
  {
    int[] arr;
    int index;
    TestItr(){
      this.index=0;
      this.arr=new int[1000];
      intArrayBuilder.Randomized.buildUnchecked(this.arr,0,arr.length,new Random(),0);
    }
    private TestItr(int index,int[] arr)
    {
      this.index=index;
      this.arr=arr;
    }
    public Object clone()
    {
      int[] copy=new int[arr.length];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
      return new TestItr(this.index,copy);
    }
    @Override
    public void remove(){
      throw new UnsupportedOperationException();
    }
    @Override
    public void forEachRemaining(IntConsumer action){
      throw new UnsupportedOperationException();
    }
    @Override
    public void forEachRemaining(Consumer<? super Integer> action){
      throw new UnsupportedOperationException();
    }
    @Override
    public boolean hasNext(){
      return this.index<arr.length;
    }
    @Override
    public int nextInt()
    {
      return (int)arr[this.index++];
    }
  };
  @Test
  public void testNext()
  {
    TestItr itr=new TestItr();
    TestItr copy=(TestItr)itr.clone();
    while(itr.hasNext())
    {
      Assertions.assertTrue(EqualityUtil.isEqual((Integer)(itr.nextInt()),copy.next()));
    }
  }
  @Test
  public void testNextDouble()
  {
    TestItr itr=new TestItr();
    TestItr copy=(TestItr)itr.clone();
    while(itr.hasNext())
    {
      Assertions.assertTrue(EqualityUtil.isEqual((double)(itr.nextInt()),copy.nextDouble()));
    }
  }
  @Test
  public void testNextFloat()
  {
    TestItr itr=new TestItr();
    TestItr copy=(TestItr)itr.clone();
    while(itr.hasNext())
    {
      Assertions.assertTrue(EqualityUtil.isEqual((float)(itr.nextInt()),copy.nextFloat()));
    }
  }
  @Test
  public void testNextLong()
  {
    TestItr itr=new TestItr();
    TestItr copy=(TestItr)itr.clone();
    while(itr.hasNext())
    {
      Assertions.assertTrue(EqualityUtil.isEqual((long)(itr.nextInt()),copy.nextLong()));
    }
  }
}
