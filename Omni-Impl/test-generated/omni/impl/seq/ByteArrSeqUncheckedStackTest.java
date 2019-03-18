package omni.impl.seq;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.impl.seq.ByteArrSeq.UncheckedStack;
import omni.util.TypeConversionUtil;
import omni.util.EqualityUtil;
import omni.util.OmniArray;
import omni.function.ByteSupplier;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class ByteArrSeqUncheckedStackTest
{
  @Test
  public void testConstructors()
  {
    var stack=new UncheckedStack();
    Assertions.assertEquals(stack.size(),0);
    Assertions.assertTrue(stack.isEmpty());
    Assertions.assertTrue(stack.arr==OmniArray.OfByte.DEFAULT_ARR);
    stack=new UncheckedStack(0);
    Assertions.assertEquals(stack.size(),0);
    Assertions.assertTrue(stack.isEmpty());
    Assertions.assertTrue(stack.arr==null);
    stack=new UncheckedStack(OmniArray.DEFAULT_ARR_SEQ_CAP);
    Assertions.assertEquals(stack.size(),0);
    Assertions.assertTrue(stack.isEmpty());
    Assertions.assertTrue(stack.arr==OmniArray.OfByte.DEFAULT_ARR);
    for(int i=1;i<OmniArray.DEFAULT_ARR_SEQ_CAP;++i)
    {
      stack=new UncheckedStack(i);
      Assertions.assertEquals(stack.size(),0);
      Assertions.assertTrue(stack.isEmpty());
      Assertions.assertEquals(stack.arr.length,i);
    }
  }
  private static UncheckedStack generateStack(int length,ByteSupplier supplier)
  {
    UncheckedStack stack=new UncheckedStack(length);
    for(int i=0;i<length;++i)
    {
      byte v=supplier.getAsByte();
      stack.push(v);
    }
    return stack;
  }
  private static ArrayList generateList(int length,ByteSupplier supplier)
  {
    ArrayList stack=new ArrayList(length);
    for(int i=0;i<length;++i)
    {
      byte v=supplier.getAsByte();
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
      var val=TypeConversionUtil.convertTobyte(i);
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
    ByteSupplier stackGenerator=new ByteSupplier()
    {
      int index=0;
      public byte getAsByte()
      {
        return TypeConversionUtil.convertTobyte(index++);
      }
    };
    ByteSupplier listGenerator=new ByteSupplier()
    {
      int index=length;
      public byte getAsByte()
      {
        return TypeConversionUtil.convertTobyte(--index);
      }
    };
    Assertions.assertEquals(generateList(length,listGenerator).hashCode(),generateStack(length,stackGenerator).hashCode());
  }
}
