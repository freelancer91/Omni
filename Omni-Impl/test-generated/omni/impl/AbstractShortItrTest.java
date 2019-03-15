package omni.impl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.util.shortArrayBuilder;
import omni.util.EqualityUtil;
import omni.util.ArrCopy;
import java.util.Random;
import java.util.function.Consumer;
import omni.function.ShortConsumer;
public class AbstractShortItrTest 
{
  private static class TestItr extends AbstractShortItr implements Cloneable
  {
    short[] arr;
    int index;
    TestItr(){
      this.index=0;
      this.arr=new short[1000];
      shortArrayBuilder.Randomized.buildUnchecked(this.arr,0,arr.length,new Random(),0);
    }
    private TestItr(int index,short[] arr)
    {
      this.index=index;
      this.arr=arr;
    }
    public Object clone()
    {
      short[] copy=new short[arr.length];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
      return new TestItr(this.index,copy);
    }
    @Override
    public void remove(){
      throw new UnsupportedOperationException();
    }
    @Override
    public void forEachRemaining(ShortConsumer action){
      throw new UnsupportedOperationException();
    }
    @Override
    public void forEachRemaining(Consumer<? super Short> action){
      throw new UnsupportedOperationException();
    }
    @Override
    public boolean hasNext(){
      return this.index<arr.length;
    }
    @Override
    public short nextShort()
    {
      return (short)arr[this.index++];
    }
  };
  @Test
  public void testNext()
  {
    TestItr itr=new TestItr();
    TestItr copy=(TestItr)itr.clone();
    while(itr.hasNext())
    {
      Assertions.assertTrue(EqualityUtil.isEqual((Short)(itr.nextShort()),copy.next()));
    }
  }
  @Test
  public void testNextDouble()
  {
    TestItr itr=new TestItr();
    TestItr copy=(TestItr)itr.clone();
    while(itr.hasNext())
    {
      Assertions.assertTrue(EqualityUtil.isEqual((double)(itr.nextShort()),copy.nextDouble()));
    }
  }
  @Test
  public void testNextFloat()
  {
    TestItr itr=new TestItr();
    TestItr copy=(TestItr)itr.clone();
    while(itr.hasNext())
    {
      Assertions.assertTrue(EqualityUtil.isEqual((float)(itr.nextShort()),copy.nextFloat()));
    }
  }
  @Test
  public void testNextLong()
  {
    TestItr itr=new TestItr();
    TestItr copy=(TestItr)itr.clone();
    while(itr.hasNext())
    {
      Assertions.assertTrue(EqualityUtil.isEqual((long)(itr.nextShort()),copy.nextLong()));
    }
  }
  @Test
  public void testNextInt()
  {
    TestItr itr=new TestItr();
    TestItr copy=(TestItr)itr.clone();
    while(itr.hasNext())
    {
      Assertions.assertTrue(EqualityUtil.isEqual((int)(itr.nextShort()),copy.nextInt()));
    }
  }
}
