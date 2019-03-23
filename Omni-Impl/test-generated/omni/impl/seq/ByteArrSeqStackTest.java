package omni.impl.seq;
/*
import omni.impl.seq.ByteArrSeq.UncheckedStack;
import omni.impl.seq.ByteArrSeq.CheckedStack;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
@SuppressWarnings({"rawtypes","unchecked"}) 
*/
public class ByteArrSeqStackTest
{
/*
  @Test
  public void testUncheckedStackpush_byte_initialCapacityDEFAULT()
  {
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTobyte(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_byte_initialCapacityNULL()
  {
    UncheckedStack seq=new UncheckedStack(0,null);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTobyte(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_byte_initialCapacity50()
  {
    UncheckedStack seq=new UncheckedStack(50);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTobyte(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_Byte_initialCapacityDEFAULT()
  {
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToByte(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_Byte_initialCapacityNULL()
  {
    UncheckedStack seq=new UncheckedStack(0,null);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToByte(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_Byte_initialCapacity50()
  {
    UncheckedStack seq=new UncheckedStack(50);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToByte(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_boolean_initialCapacityDEFAULT()
  {
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_boolean_initialCapacityNULL()
  {
    UncheckedStack seq=new UncheckedStack(0,null);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_boolean_initialCapacity50()
  {
    UncheckedStack seq=new UncheckedStack(50);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_Boolean_initialCapacityDEFAULT()
  {
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToBoolean(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_Boolean_initialCapacityNULL()
  {
    UncheckedStack seq=new UncheckedStack(0,null);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToBoolean(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_Boolean_initialCapacity50()
  {
    UncheckedStack seq=new UncheckedStack(50);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToBoolean(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_byte_initialCapacityDEFAULT()
  {
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTobyte(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_byte_initialCapacityNULL()
  {
    CheckedStack seq=new CheckedStack(0,null);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTobyte(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_byte_initialCapacity50()
  {
    CheckedStack seq=new CheckedStack(50);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTobyte(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_Byte_initialCapacityDEFAULT()
  {
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToByte(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_Byte_initialCapacityNULL()
  {
    CheckedStack seq=new CheckedStack(0,null);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToByte(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_Byte_initialCapacity50()
  {
    CheckedStack seq=new CheckedStack(50);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToByte(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_boolean_initialCapacityDEFAULT()
  {
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_boolean_initialCapacityNULL()
  {
    CheckedStack seq=new CheckedStack(0,null);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_boolean_initialCapacity50()
  {
    CheckedStack seq=new CheckedStack(50);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_Boolean_initialCapacityDEFAULT()
  {
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToBoolean(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_Boolean_initialCapacityNULL()
  {
    CheckedStack seq=new CheckedStack(0,null);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToBoolean(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_Boolean_initialCapacity50()
  {
    CheckedStack seq=new CheckedStack(50);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToBoolean(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(i),seq.arr[i]);
    }
  }
*/
}
