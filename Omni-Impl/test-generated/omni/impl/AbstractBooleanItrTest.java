package omni.impl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.util.booleanArrayBuilder;
import omni.util.EqualityUtil;
import omni.util.ArrCopy;
import java.util.Random;
import java.util.function.Consumer;
import omni.util.TypeUtil;
import omni.function.BooleanConsumer;
public class AbstractBooleanItrTest 
{
  private static class TestItr extends AbstractBooleanItr implements Cloneable
  {
    boolean[] arr;
    int index;
    TestItr(){
      this.index=0;
      this.arr=new boolean[1000];
      booleanArrayBuilder.Randomized.buildUnchecked(this.arr,0,arr.length,new Random(),0);
    }
    private TestItr(int index,boolean[] arr)
    {
      this.index=index;
      this.arr=arr;
    }
    public Object clone()
    {
      boolean[] copy=new boolean[arr.length];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
      return new TestItr(this.index,copy);
    }
    @Override
    public void remove(){
      throw new UnsupportedOperationException();
    }
    @Override
    public void forEachRemaining(BooleanConsumer action){
      throw new UnsupportedOperationException();
    }
    @Override
    public void forEachRemaining(Consumer<? super Boolean> action){
      throw new UnsupportedOperationException();
    }
    @Override
    public boolean hasNext(){
      return this.index<arr.length;
    }
    @Override
    public boolean nextBoolean()
    {
      return (boolean)arr[this.index++];
    }
  };
  @Test
  public void testNext()
  {
    TestItr itr=new TestItr();
    TestItr copy=(TestItr)itr.clone();
    while(itr.hasNext())
    {
      Assertions.assertTrue(EqualityUtil.isEqual((Boolean)(itr.nextBoolean()),copy.next()));
    }
  }
  @Test
  public void testNextDouble()
  {
    TestItr itr=new TestItr();
    TestItr copy=(TestItr)itr.clone();
    while(itr.hasNext())
    {
      Assertions.assertTrue(EqualityUtil.isEqual(TypeUtil.castToDouble(itr.nextBoolean()),copy.nextDouble()));
    }
  }
  @Test
  public void testNextFloat()
  {
    TestItr itr=new TestItr();
    TestItr copy=(TestItr)itr.clone();
    while(itr.hasNext())
    {
      Assertions.assertTrue(EqualityUtil.isEqual(TypeUtil.castToFloat(itr.nextBoolean()),copy.nextFloat()));
    }
  }
  @Test
  public void testNextLong()
  {
    TestItr itr=new TestItr();
    TestItr copy=(TestItr)itr.clone();
    while(itr.hasNext())
    {
      Assertions.assertTrue(EqualityUtil.isEqual(TypeUtil.castToLong(itr.nextBoolean()),copy.nextLong()));
    }
  }
  @Test
  public void testNextInt()
  {
    TestItr itr=new TestItr();
    TestItr copy=(TestItr)itr.clone();
    while(itr.hasNext())
    {
      Assertions.assertTrue(EqualityUtil.isEqual((int)TypeUtil.castToByte(itr.nextBoolean()),copy.nextInt()));
    }
  }
  @Test
  public void testNextShort()
  {
    TestItr itr=new TestItr();
    TestItr copy=(TestItr)itr.clone();
    while(itr.hasNext())
    {
      Assertions.assertTrue(EqualityUtil.isEqual((short)TypeUtil.castToByte(itr.nextBoolean()),copy.nextShort()));
    }
  }
  @Test
  public void testNextByte()
  {
    TestItr itr=new TestItr();
    TestItr copy=(TestItr)itr.clone();
    while(itr.hasNext())
    {
      Assertions.assertTrue(EqualityUtil.isEqual(TypeUtil.castToByte(itr.nextBoolean()),copy.nextByte()));
    }
  }
  @Test
  public void testNextChar()
  {
    TestItr itr=new TestItr();
    TestItr copy=(TestItr)itr.clone();
    while(itr.hasNext())
    {
      Assertions.assertTrue(EqualityUtil.isEqual(TypeUtil.castToChar(itr.nextBoolean()),copy.nextChar()));
    }
  }
}
