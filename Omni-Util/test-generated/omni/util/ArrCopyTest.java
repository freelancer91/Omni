package omni.util;
import org.junit.Test;
import org.junit.Assert;
import java.util.Random;
public class ArrCopyTest
{
  @Test
  public void testSemiCheckedCopybooleanBoolean() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      boolean[] src=getbooleanArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      Boolean[] dst=new Boolean[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(dst[dstOffset+j]==src[srcOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedCopybyteByte() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      byte[] src=getbyteArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      Byte[] dst=new Byte[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertEquals(dst[dstOffset+j],src[srcOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedCopycharCharacter() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      char[] src=getcharArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      Character[] dst=new Character[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertEquals(dst[dstOffset+j],src[srcOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedCopyshortShort() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      short[] src=getshortArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      Short[] dst=new Short[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertEquals(dst[dstOffset+j],src[srcOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedCopyintInteger() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      int[] src=getintArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      Integer[] dst=new Integer[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertEquals(dst[dstOffset+j],src[srcOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedCopylongLong() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      long[] src=getlongArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      Long[] dst=new Long[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertEquals(dst[dstOffset+j],src[srcOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedCopyfloatFloat() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      float[] src=getfloatArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      Float[] dst=new Float[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        float dstVal=dst[dstOffset+j];
        if(dstVal==dstVal)
        {
        }
      }
    }
  }
  @Test
  public void testSemiCheckedCopydoubleDouble() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      double[] src=getdoubleArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      Double[] dst=new Double[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        double dstVal=dst[dstOffset+j];
        if(dstVal==dstVal)
        {
        }
      }
    }
  }
  @Test
  public void testSemiCheckedCopyBooleanboolean() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      Boolean[] src=getBooleanArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      boolean[] dst=new boolean[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertEquals(dst[dstOffset+j],src[srcOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedCopyBytebyte() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      Byte[] src=getByteArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      byte[] dst=new byte[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertEquals(dst[dstOffset+j],src[srcOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedCopyCharactercar() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      Character[] src=getCharacterArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      car[] dst=new car[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertEquals(dst[dstOffset+j],src[srcOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedCopyShortshort() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      Short[] src=getShortArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      short[] dst=new short[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertEquals(dst[dstOffset+j],src[srcOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedCopyIntegerint() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      Integer[] src=getIntegerArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      int[] dst=new int[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertEquals(dst[dstOffset+j],src[srcOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedCopyLonglong() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      Long[] src=getLongArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      long[] dst=new long[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertEquals(dst[dstOffset+j],src[srcOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedCopyFloatfloat() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      Float[] src=getFloatArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      float[] dst=new float[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        float dstVal=dst[dstOffset+j];
        if(dstVal==dstVal)
        {
          Assert.assertTrue(dstVal==src[srcOffset+j]);
        }
        else
        {
          Assert.assertTrue((dstVal=src[srcOffset+j])!=dstVal);
        }
      }
    }
  }
  @Test
  public void testSemiCheckedCopyDoubledouble() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      Double[] src=getDoubleArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      double[] dst=new double[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        double dstVal=dst[dstOffset+j];
        if(dstVal==dstVal)
        {
          Assert.assertTrue(dstVal==src[srcOffset+j]);
        }
        else
        {
          Assert.assertTrue((dstVal=src[srcOffset+j])!=dstVal);
        }
      }
    }
  }
  @Test
  public void testSemiCheckedCopybooleanboolean() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      boolean[] src=getbooleanArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      boolean[] dst=new boolean[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(dst[dstOffset+j]==src[srcOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedCopybooleanbyte() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      boolean[] src=getbooleanArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      byte[] dst=new byte[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(dst[dstOffset+j]==TypeUtil.castToByte(src[srcOffset+j]));
      }
    }
  }
  @Test
  public void testSemiCheckedCopybooleanchar() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      boolean[] src=getbooleanArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      char[] dst=new char[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(dst[dstOffset+j]==TypeUtil.castToByte(src[srcOffset+j]));
      }
    }
  }
  @Test
  public void testSemiCheckedCopybooleanshort() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      boolean[] src=getbooleanArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      short[] dst=new short[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(dst[dstOffset+j]==TypeUtil.castToByte(src[srcOffset+j]));
      }
    }
  }
  @Test
  public void testSemiCheckedCopybooleanint() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      boolean[] src=getbooleanArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      int[] dst=new int[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(dst[dstOffset+j]==TypeUtil.castToByte(src[srcOffset+j]));
      }
    }
  }
  @Test
  public void testSemiCheckedCopybooleanlong() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      boolean[] src=getbooleanArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      long[] dst=new long[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(dst[dstOffset+j]==TypeUtil.castToLong(src[srcOffset+j]));
      }
    }
  }
  @Test
  public void testSemiCheckedCopybooleanfloat() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      boolean[] src=getbooleanArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      float[] dst=new float[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(dst[dstOffset+j]==TypeUtil.castToFloat(src[srcOffset+j]));
      }
    }
  }
  @Test
  public void testSemiCheckedCopybooleandouble() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      boolean[] src=getbooleanArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      double[] dst=new double[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(dst[dstOffset+j]==TypeUtil.castToDouble(src[srcOffset+j]));
      }
    }
  }
  @Test
  public void testSemiCheckedCopybytebyte() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      byte[] src=getbyteArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      byte[] dst=new byte[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertEquals(dst[dstOffset+j],src[srcOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedCopybyteshort() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      byte[] src=getbyteArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      short[] dst=new short[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertEquals(dst[dstOffset+j],src[srcOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedCopybyteint() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      byte[] src=getbyteArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      int[] dst=new int[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertEquals(dst[dstOffset+j],src[srcOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedCopybytelong() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      byte[] src=getbyteArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      long[] dst=new long[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertEquals(dst[dstOffset+j],src[srcOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedCopybytefloat() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      byte[] src=getbyteArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      float[] dst=new float[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        float dstVal=dst[dstOffset+j];
        if(dstVal==dstVal)
        {
          Assert.assertTrue(dstVal==src[srcOffset+j]);
        }
        else
        {
          Assert.assertTrue((dstVal=src[srcOffset+j])!=dstVal);
        }
      }
    }
  }
  @Test
  public void testSemiCheckedCopybytedouble() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      byte[] src=getbyteArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      double[] dst=new double[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        double dstVal=dst[dstOffset+j];
        if(dstVal==dstVal)
        {
          Assert.assertTrue(dstVal==src[srcOffset+j]);
        }
        else
        {
          Assert.assertTrue((dstVal=src[srcOffset+j])!=dstVal);
        }
      }
    }
  }
  @Test
  public void testSemiCheckedCopycharchar() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      char[] src=getcharArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      char[] dst=new char[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertEquals(dst[dstOffset+j],src[srcOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedCopycharint() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      char[] src=getcharArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      int[] dst=new int[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertEquals(dst[dstOffset+j],src[srcOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedCopycharlong() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      char[] src=getcharArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      long[] dst=new long[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertEquals(dst[dstOffset+j],src[srcOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedCopycharfloat() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      char[] src=getcharArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      float[] dst=new float[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        float dstVal=dst[dstOffset+j];
        if(dstVal==dstVal)
        {
          Assert.assertTrue(dstVal==src[srcOffset+j]);
        }
        else
        {
          Assert.assertTrue((dstVal=src[srcOffset+j])!=dstVal);
        }
      }
    }
  }
  @Test
  public void testSemiCheckedCopychardouble() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      char[] src=getcharArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      double[] dst=new double[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        double dstVal=dst[dstOffset+j];
        if(dstVal==dstVal)
        {
          Assert.assertTrue(dstVal==src[srcOffset+j]);
        }
        else
        {
          Assert.assertTrue((dstVal=src[srcOffset+j])!=dstVal);
        }
      }
    }
  }
  @Test
  public void testSemiCheckedCopyshortshort() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      short[] src=getshortArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      short[] dst=new short[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertEquals(dst[dstOffset+j],src[srcOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedCopyshortint() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      short[] src=getshortArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      int[] dst=new int[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertEquals(dst[dstOffset+j],src[srcOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedCopyshortlong() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      short[] src=getshortArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      long[] dst=new long[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertEquals(dst[dstOffset+j],src[srcOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedCopyshortfloat() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      short[] src=getshortArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      float[] dst=new float[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        float dstVal=dst[dstOffset+j];
        if(dstVal==dstVal)
        {
          Assert.assertTrue(dstVal==src[srcOffset+j]);
        }
        else
        {
          Assert.assertTrue((dstVal=src[srcOffset+j])!=dstVal);
        }
      }
    }
  }
  @Test
  public void testSemiCheckedCopyshortdouble() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      short[] src=getshortArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      double[] dst=new double[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        double dstVal=dst[dstOffset+j];
        if(dstVal==dstVal)
        {
          Assert.assertTrue(dstVal==src[srcOffset+j]);
        }
        else
        {
          Assert.assertTrue((dstVal=src[srcOffset+j])!=dstVal);
        }
      }
    }
  }
  @Test
  public void testSemiCheckedCopyintint() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      int[] src=getintArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      int[] dst=new int[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertEquals(dst[dstOffset+j],src[srcOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedCopyintlong() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      int[] src=getintArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      long[] dst=new long[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertEquals(dst[dstOffset+j],src[srcOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedCopyintfloat() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      int[] src=getintArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      float[] dst=new float[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        float dstVal=dst[dstOffset+j];
        if(dstVal==dstVal)
        {
          Assert.assertTrue(dstVal==src[srcOffset+j]);
        }
        else
        {
          Assert.assertTrue((dstVal=src[srcOffset+j])!=dstVal);
        }
      }
    }
  }
  @Test
  public void testSemiCheckedCopyintdouble() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      int[] src=getintArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      double[] dst=new double[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        double dstVal=dst[dstOffset+j];
        if(dstVal==dstVal)
        {
          Assert.assertTrue(dstVal==src[srcOffset+j]);
        }
        else
        {
          Assert.assertTrue((dstVal=src[srcOffset+j])!=dstVal);
        }
      }
    }
  }
  @Test
  public void testSemiCheckedCopylonglong() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      long[] src=getlongArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      long[] dst=new long[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertEquals(dst[dstOffset+j],src[srcOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedCopylongfloat() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      long[] src=getlongArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      float[] dst=new float[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        float dstVal=dst[dstOffset+j];
        if(dstVal==dstVal)
        {
          Assert.assertTrue(dstVal==src[srcOffset+j]);
        }
        else
        {
          Assert.assertTrue((dstVal=src[srcOffset+j])!=dstVal);
        }
      }
    }
  }
  @Test
  public void testSemiCheckedCopylongdouble() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      long[] src=getlongArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      double[] dst=new double[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        double dstVal=dst[dstOffset+j];
        if(dstVal==dstVal)
        {
          Assert.assertTrue(dstVal==src[srcOffset+j]);
        }
        else
        {
          Assert.assertTrue((dstVal=src[srcOffset+j])!=dstVal);
        }
      }
    }
  }
  @Test
  public void testSemiCheckedCopyfloatfloat() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      float[] src=getfloatArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      float[] dst=new float[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        float dstVal=dst[dstOffset+j];
        if(dstVal==dstVal)
        {
          Assert.assertTrue(dstVal==src[srcOffset+j]);
        }
        else
        {
          Assert.assertTrue((dstVal=src[srcOffset+j])!=dstVal);
        }
      }
    }
  }
  @Test
  public void testSemiCheckedCopyfloatdouble() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      float[] src=getfloatArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      double[] dst=new double[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        double dstVal=dst[dstOffset+j];
        if(dstVal==dstVal)
        {
          Assert.assertTrue(dstVal==src[srcOffset+j]);
        }
        else
        {
          Assert.assertTrue((dstVal=src[srcOffset+j])!=dstVal);
        }
      }
    }
  }
  @Test
  public void testSemiCheckedCopydoubledouble() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      double[] src=getdoubleArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      double[] dst=new double[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        double dstVal=dst[dstOffset+j];
        if(dstVal==dstVal)
        {
          Assert.assertTrue(dstVal==src[srcOffset+j]);
        }
        else
        {
          Assert.assertTrue((dstVal=src[srcOffset+j])!=dstVal);
        }
      }
    }
  }
  @Test
  public void testSemiCheckedCopyStringString() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      String[] src=getStringArr(rand,srcLength);
      int dstLength=srcLength+rand.nextInt(1000);
      String[] dst=new String[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertEquals(dst[dstOffset+j],src[srcOffset+j]);
      }
    }
  }
  private static boolean[] getbooleanArr(Random rand,int length)
  {
    boolean[] src=new boolean[length];
    for(int i=0;i<length;++i)
    {
      src[i]=rand.nextBoolean();
    }
    return src;
  }
  private static byte[] getbyteArr(Random rand,int length)
  {
    byte[] src=new byte[length];
    for(int i=0;i<length;++i)
    {
      src[i]=(byte)(Byte.MIN_VALUE+rand.nextInt(Byte.MAX_VALUE-Byte.MIN_VALUE));
    }
    return src;
  }
  private static char[] getcharArr(Random rand,int length)
  {
    char[] src=new char[length];
    for(int i=0;i<length;++i)
    {
      src[i]=(char)(rand.nextInt(Character.MAX_VALUE));
    }
    return src;
  }
  private static short[] getshortArr(Random rand,int length)
  {
    short[] src=new short[length];
    for(int i=0;i<length;++i)
    {
      src[i]=(short)(Short.MIN_VALUE+rand.nextInt(Short.MAX_VALUE-Short.MIN_VALUE));
    }
    return src;
  }
  private static int[] getintArr(Random rand,int length)
  {
    int[] src=new int[length];
    for(int i=0;i<length;++i)
    {
      src[i]=rand.nextInt();
    }
    return src;
  }
  private static long[] getlongArr(Random rand,int length)
  {
    long[] src=new long[length];
    for(int i=0;i<length;++i)
    {
      src[i]=rand.nextLong();
    }
    return src;
  }
  private static float[] getfloatArr(Random rand,int length)
  {
    float[] src=new float[length];
    for(int i=0;i<length;++i)
    {
      src[i]=rand.nextFloat();
    }
    return src;
  }
  private static double[] getdoubleArr(Random rand,int length)
  {
    double[] src=new double[length];
    for(int i=0;i<length;++i)
    {
      src[i]=rand.nextDouble();
    }
    return src;
  }
  private static Boolean[] getBooleanArr(Random rand,int length)
  {
    Boolean[] src=new Boolean[length];
    for(int i=0;i<length;++i)
    {
      src[i]=rand.nextBoolean();
    }
    return src;
  }
  private static Byte[] getByteArr(Random rand,int length)
  {
    Byte[] src=new Byte[length];
    for(int i=0;i<length;++i)
    {
      src[i]=(byte)(Byte.MIN_VALUE+rand.nextInt(Byte.MAX_VALUE-Byte.MIN_VALUE));
    }
    return src;
  }
  private static Character[] getCharacterArr(Random rand,int length)
  {
    Character[] src=new Character[length];
    for(int i=0;i<length;++i)
    {
      src[i]=(char)(rand.nextInt(Character.MAX_VALUE));
    }
    return src;
  }
  private static Short[] getShortArr(Random rand,int length)
  {
    Short[] src=new Short[length];
    for(int i=0;i<length;++i)
    {
      src[i]=(short)(Short.MIN_VALUE+rand.nextInt(Short.MAX_VALUE-Short.MIN_VALUE));
    }
    return src;
  }
  private static Integer[] getIntegerArr(Random rand,int length)
  {
    Integer[] src=new Integer[length];
    for(int i=0;i<length;++i)
    {
      src[i]=rand.nextInt();
    }
    return src;
  }
  private static Long[] getLongArr(Random rand,int length)
  {
    Long[] src=new Long[length];
    for(int i=0;i<length;++i)
    {
      src[i]=rand.nextLong();
    }
    return src;
  }
  private static Float[] getFloatArr(Random rand,int length)
  {
    Float[] src=new Float[length];
    for(int i=0;i<length;++i)
    {
      src[i]=rand.nextFloat();
    }
    return src;
  }
  private static Double[] getDoubleArr(Random rand,int length)
  {
    Double[] src=new Double[length];
    for(int i=0;i<length;++i)
    {
      src[i]=rand.nextDouble();
    }
    return src;
  }
  private static String[] getStringArr(Random rand,int length)
  {
    String[] src=new String[length];
    for(int i=0;i<length;++i)
    {
      src[i]=Long.toString(rand.nextLong());
    }
    return src;
  }
}
