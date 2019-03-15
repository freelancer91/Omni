package omni.impl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.util.charArrayBuilder;
import omni.util.EqualityUtil;
import omni.util.ArrCopy;
import java.util.Random;
import java.util.function.Consumer;
import omni.function.CharConsumer;
public class AbstractCharItrTest 
{
  private static class TestItr extends AbstractCharItr implements Cloneable
  {
    char[] arr;
    int index;
    TestItr(){
      this.index=0;
      this.arr=new char[1000];
      charArrayBuilder.Randomized.buildUnchecked(this.arr,0,arr.length,new Random(),0);
    }
    private TestItr(int index,char[] arr)
    {
      this.index=index;
      this.arr=arr;
    }
    public Object clone()
    {
      char[] copy=new char[arr.length];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
      return new TestItr(this.index,copy);
    }
    @Override
    public void remove(){
      throw new UnsupportedOperationException();
    }
    @Override
    public void forEachRemaining(CharConsumer action){
      throw new UnsupportedOperationException();
    }
    @Override
    public void forEachRemaining(Consumer<? super Character> action){
      throw new UnsupportedOperationException();
    }
    @Override
    public boolean hasNext(){
      return this.index<arr.length;
    }
    @Override
    public char nextChar()
    {
      return (char)arr[this.index++];
    }
  };
  @Test
  public void testNext()
  {
    TestItr itr=new TestItr();
    TestItr copy=(TestItr)itr.clone();
    while(itr.hasNext())
    {
      Assertions.assertTrue(EqualityUtil.isEqual((Character)(itr.nextChar()),copy.next()));
    }
  }
  @Test
  public void testNextDouble()
  {
    TestItr itr=new TestItr();
    TestItr copy=(TestItr)itr.clone();
    while(itr.hasNext())
    {
      Assertions.assertTrue(EqualityUtil.isEqual((double)(itr.nextChar()),copy.nextDouble()));
    }
  }
  @Test
  public void testNextFloat()
  {
    TestItr itr=new TestItr();
    TestItr copy=(TestItr)itr.clone();
    while(itr.hasNext())
    {
      Assertions.assertTrue(EqualityUtil.isEqual((float)(itr.nextChar()),copy.nextFloat()));
    }
  }
  @Test
  public void testNextLong()
  {
    TestItr itr=new TestItr();
    TestItr copy=(TestItr)itr.clone();
    while(itr.hasNext())
    {
      Assertions.assertTrue(EqualityUtil.isEqual((long)(itr.nextChar()),copy.nextLong()));
    }
  }
  @Test
  public void testNextInt()
  {
    TestItr itr=new TestItr();
    TestItr copy=(TestItr)itr.clone();
    while(itr.hasNext())
    {
      Assertions.assertTrue(EqualityUtil.isEqual((int)(itr.nextChar()),copy.nextInt()));
    }
  }
}
