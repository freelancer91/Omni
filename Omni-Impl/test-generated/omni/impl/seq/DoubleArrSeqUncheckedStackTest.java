package omni.impl.seq;
import java.util.ArrayList;
import omni.api.OmniCollection;
import omni.util.OmniArray;
import omni.api.OmniList;
import omni.api.OmniStack;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.Comparator;
import omni.util.ArrCopy;
import omni.util.DoubleSortUtil;
import omni.impl.CheckedCollection;
import java.util.NoSuchElementException;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import java.util.function.IntFunction;
import java.util.function.UnaryOperator;
import java.util.function.DoubleSupplier;
import omni.util.TypeUtil;
import java.util.ConcurrentModificationException;
import java.util.function.DoubleUnaryOperator;
import omni.function.DoubleComparator;
import java.util.function.DoublePredicate;
import java.util.function.DoubleConsumer;
import omni.util.BitSetUtil;
import omni.impl.AbstractDoubleItr;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.impl.seq.DoubleArrSeq.UncheckedStack;
import omni.util.TypeConversionUtil;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class DoubleArrSeqUncheckedStackTest
{
  private static final double MIN_LENGTH_STRING_VAL=0;
  private static final int MIN_TOSTRING_LENGTH=String.valueOf(MIN_LENGTH_STRING_VAL).length();
  @Test
  public void testConstructors()
  {
    var stack=new UncheckedStack();
    Assertions.assertEquals(stack.size(),0);
    Assertions.assertTrue(stack.isEmpty());
    Assertions.assertTrue(stack.arr==OmniArray.OfDouble.DEFAULT_ARR);
    stack=new UncheckedStack(0);
    Assertions.assertEquals(stack.size(),0);
    Assertions.assertTrue(stack.isEmpty());
    Assertions.assertTrue(stack.arr==null);
    stack=new UncheckedStack(OmniArray.DEFAULT_ARR_SEQ_CAP);
    Assertions.assertEquals(stack.size(),0);
    Assertions.assertTrue(stack.isEmpty());
    Assertions.assertTrue(stack.arr==OmniArray.OfDouble.DEFAULT_ARR);
    for(int i=1;i<OmniArray.DEFAULT_ARR_SEQ_CAP;++i)
    {
      stack=new UncheckedStack(i);
      Assertions.assertEquals(stack.size(),0);
      Assertions.assertTrue(stack.isEmpty());
      Assertions.assertEquals(stack.arr.length,i);
    }
  }
  private static UncheckedStack generateStack(int length,DoubleSupplier supplier)
  {
    UncheckedStack stack=new UncheckedStack(length);
    for(int i=0;i<length;++i)
    {
      double v=supplier.getAsDouble();
      stack.push(v);
    }
    return stack;
  }
  private static ArrayList generateList(int length,DoubleSupplier supplier)
  {
    ArrayList stack=new ArrayList(length);
    for(int i=0;i<length;++i)
    {
      double v=supplier.getAsDouble();
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
      var val=TypeConversionUtil.convertTodouble(i);
      stack.push(val);
    }
    Assertions.assertEquals(stack.size(),100);
    clonedObject=stack.clone();
    Assertions.assertTrue(clonedObject instanceof UncheckedStack);
    clonedStack=(UncheckedStack)clonedObject;
    Assertions.assertTrue(clonedStack.arr!=stack.arr);
    Assertions.assertEquals(stack.size(),clonedStack.size());
    for(int i=0;i<stack.size();++i)
    {
      Assertions.assertTrue(stack.arr[i]==clonedStack.arr[i]);
    }
  }
  @Test
  public void testEmptyToString()
  {
    var stack=generateStack(0,null);
    var arrayList=generateList(0,null);
    Assertions.assertEquals(stack.toString(),arrayList.toString());
  }
  @Test
  public void testEmptyHashCode()
  {
    var stack=generateStack(0,null);
    var arrayList=generateList(0,null);
    Assertions.assertEquals(stack.hashCode(),arrayList.hashCode());
  }
  @Test
  public void testHashCode()
  {
    int length=100;
    DoubleSupplier stackGenerator=new DoubleSupplier()
    {
      int index=0;
      public double getAsDouble()
      {
        return TypeConversionUtil.convertTodouble(index++);
      }
    };
    DoubleSupplier listGenerator=new DoubleSupplier()
    {
      int index=length;
      public double getAsDouble()
      {
        return TypeConversionUtil.convertTodouble(--index);
      }
    };
    var stack=generateStack(length,stackGenerator);
    var arrayList=generateList(length,listGenerator);
    Assertions.assertEquals(stack.hashCode(),arrayList.hashCode());
  }
  @Test
  public void testOOMToString()
  {
    int length=Integer.MAX_VALUE/(MIN_TOSTRING_LENGTH+2);
    var stack=new UncheckedStack(length+1);
    var arrayList=new ArrayList<>(length+1);
    for(int i=0;i<length;++i)
    {
      stack.push(MIN_LENGTH_STRING_VAL);
      arrayList.add(MIN_LENGTH_STRING_VAL);
    }
    Assertions.assertEquals(stack.toString(),arrayList.toString());
    stack.push(MIN_LENGTH_STRING_VAL);
    Assertions.assertThrows(OutOfMemoryError.class,()->stack.toString());
  }
  @Test
  public void testSmallToString()
  {
    int length=100;
    DoubleSupplier stackGenerator=new DoubleSupplier()
    {
      int index=0;
      public double getAsDouble()
      {
        return TypeConversionUtil.convertTodouble(index++);
      }
    };
    DoubleSupplier listGenerator=new DoubleSupplier()
    {
      int index=length;
      public double getAsDouble()
      {
        return TypeConversionUtil.convertTodouble(--index);
      }
    };
    var stack=generateStack(length,stackGenerator);
    var arrayList=generateList(length,listGenerator);
    Assertions.assertEquals(stack.toString(),arrayList.toString());
  }
}
