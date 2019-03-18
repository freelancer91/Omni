package omni.impl.seq;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.impl.seq.ShortArrSeq.UncheckedStack;
import omni.util.TypeConversionUtil;
import omni.util.EqualityUtil;
import omni.util.OmniArray;
import omni.function.ShortSupplier;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class ShortArrSeqUncheckedStackTest
{
  @Test
  public void testConstructors()
  {
    var stack=new UncheckedStack();
    Assertions.assertEquals(stack.size(),0);
    Assertions.assertTrue(stack.isEmpty());
    Assertions.assertTrue(stack.arr==OmniArray.OfShort.DEFAULT_ARR);
    stack=new UncheckedStack(0);
    Assertions.assertEquals(stack.size(),0);
    Assertions.assertTrue(stack.isEmpty());
    Assertions.assertTrue(stack.arr==null);
    stack=new UncheckedStack(OmniArray.DEFAULT_ARR_SEQ_CAP);
    Assertions.assertEquals(stack.size(),0);
    Assertions.assertTrue(stack.isEmpty());
    Assertions.assertTrue(stack.arr==OmniArray.OfShort.DEFAULT_ARR);
    for(int i=1;i<OmniArray.DEFAULT_ARR_SEQ_CAP;++i)
    {
      stack=new UncheckedStack(i);
      Assertions.assertEquals(stack.size(),0);
      Assertions.assertTrue(stack.isEmpty());
      Assertions.assertEquals(stack.arr.length,i);
    }
  }
  private static UncheckedStack generateStack(int length,ShortSupplier supplier)
  {
    UncheckedStack stack=new UncheckedStack(length);
    for(int i=0;i<length;++i)
    {
      short v=supplier.getAsShort();
      stack.push(v);
    }
    return stack;
  }
  private static ArrayList generateList(int length,ShortSupplier supplier)
  {
    ArrayList stack=new ArrayList(length);
    for(int i=0;i<length;++i)
    {
      short v=supplier.getAsShort();
      stack.add(v);
    }
    return stack;
  }
  @Test
  public void testClone()
  {
    var stack=new UncheckedStack();
    Object clonedObject=stack.clone();
    Assertions.assertTrue(clonedObject instanceof UncheckedStack);
    var clonedStack=(UncheckedStack)clonedObject;
    Assertions.assertTrue(clonedStack.arr==stack.arr);
    Assertions.assertEquals(clonedStack.size(),stack.size());
    Assertions.assertTrue(stack!=clonedStack);
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertToshort(i);
      stack.push(val);
    }
    Assertions.assertEquals(stack.size(),100);
    clonedObject=stack.clone();
    Assertions.assertTrue(clonedObject instanceof UncheckedStack);
    clonedStack=(UncheckedStack)clonedObject;
    Assertions.assertTrue(clonedStack.arr!=stack.arr);
    Assertions.assertEquals(stack.size(),clonedStack.size());
    EqualityUtil.uncheckedparallelassertarraysAreEqual(stack.arr,0,clonedStack.arr,0,stack.size());
  }
  @Test
  public void testEmptyHashCode()
  {
    Assertions.assertEquals(generateList(0,null).hashCode(),generateList(0,null).hashCode());
  }
  @Test
  public void testHashCode()
  {
    int length=100;
    ShortSupplier stackGenerator=new ShortSupplier()
    {
      int index=0;
      public short getAsShort()
      {
        return TypeConversionUtil.convertToshort(index++);
      }
    };
    ShortSupplier listGenerator=new ShortSupplier()
    {
      int index=length;
      public short getAsShort()
      {
        return TypeConversionUtil.convertToshort(--index);
      }
    };
    Assertions.assertEquals(generateList(length,listGenerator).hashCode(),generateStack(length,stackGenerator).hashCode());
  }
}
