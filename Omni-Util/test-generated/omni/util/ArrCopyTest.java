package omni.util;
import org.junit.Test;
import org.junit.Assert;
import java.util.Random;
import java.util.Objects;
public class ArrCopyTest
{
  @Test
  public void testSemiCheckedCopybooleanBoolean() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      boolean[] src;
      JunitUtil.booleanArrayBuilder.Randomized.build(src=new boolean[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      Boolean[] dst=new Boolean[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopybooleanBoolean() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      boolean[] src;
      JunitUtil.booleanArrayBuilder.Randomized.build(src=new boolean[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      Boolean[] dst=new Boolean[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
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
      byte[] src;
      JunitUtil.byteArrayBuilder.Randomized.build(src=new byte[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      Byte[] dst=new Byte[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopybyteByte() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      byte[] src;
      JunitUtil.byteArrayBuilder.Randomized.build(src=new byte[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      Byte[] dst=new Byte[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
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
      char[] src;
      JunitUtil.charArrayBuilder.Randomized.build(src=new char[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      Character[] dst=new Character[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopycharCharacter() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      char[] src;
      JunitUtil.charArrayBuilder.Randomized.build(src=new char[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      Character[] dst=new Character[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
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
      short[] src;
      JunitUtil.shortArrayBuilder.Randomized.build(src=new short[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      Short[] dst=new Short[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopyshortShort() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      short[] src;
      JunitUtil.shortArrayBuilder.Randomized.build(src=new short[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      Short[] dst=new Short[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
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
      int[] src;
      JunitUtil.intArrayBuilder.Randomized.build(src=new int[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      Integer[] dst=new Integer[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopyintInteger() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      int[] src;
      JunitUtil.intArrayBuilder.Randomized.build(src=new int[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      Integer[] dst=new Integer[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
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
      long[] src;
      JunitUtil.longArrayBuilder.Randomized.build(src=new long[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      Long[] dst=new Long[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopylongLong() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      long[] src;
      JunitUtil.longArrayBuilder.Randomized.build(src=new long[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      Long[] dst=new Long[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
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
      float[] src;
      JunitUtil.floatArrayBuilder.Randomized.build(src=new float[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      Float[] dst=new Float[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopyfloatFloat() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      float[] src;
      JunitUtil.floatArrayBuilder.Randomized.build(src=new float[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      Float[] dst=new Float[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
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
      double[] src;
      JunitUtil.doubleArrayBuilder.Randomized.build(src=new double[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      Double[] dst=new Double[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopydoubleDouble() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      double[] src;
      JunitUtil.doubleArrayBuilder.Randomized.build(src=new double[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      Double[] dst=new Double[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedCopybooleanObject() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      boolean[] src;
      JunitUtil.booleanArrayBuilder.Randomized.build(src=new boolean[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      Object[] dst=new Object[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(Objects.equals(src[srcOffset+j],dst[dstOffset+j]));
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopybooleanObject() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      boolean[] src;
      JunitUtil.booleanArrayBuilder.Randomized.build(src=new boolean[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      Object[] dst=new Object[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(Objects.equals(src[srcOffset+copyLength-1-j],dst[dstOffset+j]));
      }
    }
  }
  @Test
  public void testSemiCheckedCopybyteObject() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      byte[] src;
      JunitUtil.byteArrayBuilder.Randomized.build(src=new byte[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      Object[] dst=new Object[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(Objects.equals(src[srcOffset+j],dst[dstOffset+j]));
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopybyteObject() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      byte[] src;
      JunitUtil.byteArrayBuilder.Randomized.build(src=new byte[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      Object[] dst=new Object[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(Objects.equals(src[srcOffset+copyLength-1-j],dst[dstOffset+j]));
      }
    }
  }
  @Test
  public void testSemiCheckedCopycharObject() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      char[] src;
      JunitUtil.charArrayBuilder.Randomized.build(src=new char[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      Object[] dst=new Object[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(Objects.equals(src[srcOffset+j],dst[dstOffset+j]));
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopycharObject() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      char[] src;
      JunitUtil.charArrayBuilder.Randomized.build(src=new char[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      Object[] dst=new Object[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(Objects.equals(src[srcOffset+copyLength-1-j],dst[dstOffset+j]));
      }
    }
  }
  @Test
  public void testSemiCheckedCopyshortObject() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      short[] src;
      JunitUtil.shortArrayBuilder.Randomized.build(src=new short[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      Object[] dst=new Object[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(Objects.equals(src[srcOffset+j],dst[dstOffset+j]));
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopyshortObject() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      short[] src;
      JunitUtil.shortArrayBuilder.Randomized.build(src=new short[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      Object[] dst=new Object[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(Objects.equals(src[srcOffset+copyLength-1-j],dst[dstOffset+j]));
      }
    }
  }
  @Test
  public void testSemiCheckedCopyintObject() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      int[] src;
      JunitUtil.intArrayBuilder.Randomized.build(src=new int[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      Object[] dst=new Object[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(Objects.equals(src[srcOffset+j],dst[dstOffset+j]));
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopyintObject() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      int[] src;
      JunitUtil.intArrayBuilder.Randomized.build(src=new int[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      Object[] dst=new Object[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(Objects.equals(src[srcOffset+copyLength-1-j],dst[dstOffset+j]));
      }
    }
  }
  @Test
  public void testSemiCheckedCopylongObject() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      long[] src;
      JunitUtil.longArrayBuilder.Randomized.build(src=new long[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      Object[] dst=new Object[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(Objects.equals(src[srcOffset+j],dst[dstOffset+j]));
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopylongObject() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      long[] src;
      JunitUtil.longArrayBuilder.Randomized.build(src=new long[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      Object[] dst=new Object[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(Objects.equals(src[srcOffset+copyLength-1-j],dst[dstOffset+j]));
      }
    }
  }
  @Test
  public void testSemiCheckedCopyfloatObject() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      float[] src;
      JunitUtil.floatArrayBuilder.Randomized.build(src=new float[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      Object[] dst=new Object[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(Objects.equals(src[srcOffset+j],dst[dstOffset+j]));
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopyfloatObject() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      float[] src;
      JunitUtil.floatArrayBuilder.Randomized.build(src=new float[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      Object[] dst=new Object[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(Objects.equals(src[srcOffset+copyLength-1-j],dst[dstOffset+j]));
      }
    }
  }
  @Test
  public void testSemiCheckedCopydoubleObject() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      double[] src;
      JunitUtil.doubleArrayBuilder.Randomized.build(src=new double[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      Object[] dst=new Object[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(Objects.equals(src[srcOffset+j],dst[dstOffset+j]));
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopydoubleObject() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      double[] src;
      JunitUtil.doubleArrayBuilder.Randomized.build(src=new double[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      Object[] dst=new Object[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(Objects.equals(src[srcOffset+copyLength-1-j],dst[dstOffset+j]));
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
      Boolean[] src;
      JunitUtil.booleanArrayBuilder.Randomized.build(src=new Boolean[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      boolean[] dst=new boolean[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopyBooleanboolean() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      Boolean[] src;
      JunitUtil.booleanArrayBuilder.Randomized.build(src=new Boolean[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      boolean[] dst=new boolean[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
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
      Byte[] src;
      JunitUtil.byteArrayBuilder.Randomized.build(src=new Byte[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      byte[] dst=new byte[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopyBytebyte() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      Byte[] src;
      JunitUtil.byteArrayBuilder.Randomized.build(src=new Byte[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      byte[] dst=new byte[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedCopyCharacterchar() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      Character[] src;
      JunitUtil.charArrayBuilder.Randomized.build(src=new Character[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      char[] dst=new char[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopyCharacterchar() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      Character[] src;
      JunitUtil.charArrayBuilder.Randomized.build(src=new Character[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      char[] dst=new char[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
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
      Short[] src;
      JunitUtil.shortArrayBuilder.Randomized.build(src=new Short[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      short[] dst=new short[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopyShortshort() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      Short[] src;
      JunitUtil.shortArrayBuilder.Randomized.build(src=new Short[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      short[] dst=new short[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
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
      Integer[] src;
      JunitUtil.intArrayBuilder.Randomized.build(src=new Integer[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      int[] dst=new int[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopyIntegerint() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      Integer[] src;
      JunitUtil.intArrayBuilder.Randomized.build(src=new Integer[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      int[] dst=new int[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
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
      Long[] src;
      JunitUtil.longArrayBuilder.Randomized.build(src=new Long[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      long[] dst=new long[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopyLonglong() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      Long[] src;
      JunitUtil.longArrayBuilder.Randomized.build(src=new Long[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      long[] dst=new long[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
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
      Float[] src;
      JunitUtil.floatArrayBuilder.Randomized.build(src=new Float[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      float[] dst=new float[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopyFloatfloat() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      Float[] src;
      JunitUtil.floatArrayBuilder.Randomized.build(src=new Float[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      float[] dst=new float[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
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
      Double[] src;
      JunitUtil.doubleArrayBuilder.Randomized.build(src=new Double[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      double[] dst=new double[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopyDoubledouble() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      Double[] src;
      JunitUtil.doubleArrayBuilder.Randomized.build(src=new Double[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      double[] dst=new double[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
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
      boolean[] src;
      JunitUtil.booleanArrayBuilder.Randomized.build(src=new boolean[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      boolean[] dst=new boolean[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopybooleanboolean() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      boolean[] src;
      JunitUtil.booleanArrayBuilder.Randomized.build(src=new boolean[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      boolean[] dst=new boolean[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
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
      boolean[] src;
      JunitUtil.booleanArrayBuilder.Randomized.build(src=new boolean[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      byte[] dst=new byte[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(TypeUtil.castToByte(src[srcOffset+j])==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopybooleanbyte() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      boolean[] src;
      JunitUtil.booleanArrayBuilder.Randomized.build(src=new boolean[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      byte[] dst=new byte[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(TypeUtil.castToByte(src[srcOffset+copyLength-1-j])==dst[dstOffset+j]);
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
      boolean[] src;
      JunitUtil.booleanArrayBuilder.Randomized.build(src=new boolean[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      char[] dst=new char[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(TypeUtil.castToByte(src[srcOffset+j])==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopybooleanchar() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      boolean[] src;
      JunitUtil.booleanArrayBuilder.Randomized.build(src=new boolean[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      char[] dst=new char[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(TypeUtil.castToByte(src[srcOffset+copyLength-1-j])==dst[dstOffset+j]);
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
      boolean[] src;
      JunitUtil.booleanArrayBuilder.Randomized.build(src=new boolean[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      short[] dst=new short[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(TypeUtil.castToByte(src[srcOffset+j])==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopybooleanshort() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      boolean[] src;
      JunitUtil.booleanArrayBuilder.Randomized.build(src=new boolean[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      short[] dst=new short[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(TypeUtil.castToByte(src[srcOffset+copyLength-1-j])==dst[dstOffset+j]);
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
      boolean[] src;
      JunitUtil.booleanArrayBuilder.Randomized.build(src=new boolean[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      int[] dst=new int[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(TypeUtil.castToByte(src[srcOffset+j])==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopybooleanint() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      boolean[] src;
      JunitUtil.booleanArrayBuilder.Randomized.build(src=new boolean[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      int[] dst=new int[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(TypeUtil.castToByte(src[srcOffset+copyLength-1-j])==dst[dstOffset+j]);
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
      boolean[] src;
      JunitUtil.booleanArrayBuilder.Randomized.build(src=new boolean[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      long[] dst=new long[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(TypeUtil.castToByte(src[srcOffset+j])==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopybooleanlong() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      boolean[] src;
      JunitUtil.booleanArrayBuilder.Randomized.build(src=new boolean[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      long[] dst=new long[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(TypeUtil.castToByte(src[srcOffset+copyLength-1-j])==dst[dstOffset+j]);
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
      boolean[] src;
      JunitUtil.booleanArrayBuilder.Randomized.build(src=new boolean[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      float[] dst=new float[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(TypeUtil.castToByte(src[srcOffset+j])==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopybooleanfloat() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      boolean[] src;
      JunitUtil.booleanArrayBuilder.Randomized.build(src=new boolean[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      float[] dst=new float[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(TypeUtil.castToByte(src[srcOffset+copyLength-1-j])==dst[dstOffset+j]);
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
      boolean[] src;
      JunitUtil.booleanArrayBuilder.Randomized.build(src=new boolean[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      double[] dst=new double[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(TypeUtil.castToByte(src[srcOffset+j])==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopybooleandouble() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      boolean[] src;
      JunitUtil.booleanArrayBuilder.Randomized.build(src=new boolean[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      double[] dst=new double[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(TypeUtil.castToByte(src[srcOffset+copyLength-1-j])==dst[dstOffset+j]);
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
      byte[] src;
      JunitUtil.byteArrayBuilder.Randomized.build(src=new byte[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      byte[] dst=new byte[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopybytebyte() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      byte[] src;
      JunitUtil.byteArrayBuilder.Randomized.build(src=new byte[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      byte[] dst=new byte[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
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
      byte[] src;
      JunitUtil.byteArrayBuilder.Randomized.build(src=new byte[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      short[] dst=new short[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopybyteshort() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      byte[] src;
      JunitUtil.byteArrayBuilder.Randomized.build(src=new byte[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      short[] dst=new short[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
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
      byte[] src;
      JunitUtil.byteArrayBuilder.Randomized.build(src=new byte[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      int[] dst=new int[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopybyteint() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      byte[] src;
      JunitUtil.byteArrayBuilder.Randomized.build(src=new byte[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      int[] dst=new int[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
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
      byte[] src;
      JunitUtil.byteArrayBuilder.Randomized.build(src=new byte[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      long[] dst=new long[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopybytelong() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      byte[] src;
      JunitUtil.byteArrayBuilder.Randomized.build(src=new byte[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      long[] dst=new long[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
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
      byte[] src;
      JunitUtil.byteArrayBuilder.Randomized.build(src=new byte[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      float[] dst=new float[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopybytefloat() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      byte[] src;
      JunitUtil.byteArrayBuilder.Randomized.build(src=new byte[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      float[] dst=new float[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
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
      byte[] src;
      JunitUtil.byteArrayBuilder.Randomized.build(src=new byte[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      double[] dst=new double[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopybytedouble() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      byte[] src;
      JunitUtil.byteArrayBuilder.Randomized.build(src=new byte[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      double[] dst=new double[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
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
      char[] src;
      JunitUtil.charArrayBuilder.Randomized.build(src=new char[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      char[] dst=new char[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopycharchar() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      char[] src;
      JunitUtil.charArrayBuilder.Randomized.build(src=new char[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      char[] dst=new char[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
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
      char[] src;
      JunitUtil.charArrayBuilder.Randomized.build(src=new char[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      int[] dst=new int[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopycharint() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      char[] src;
      JunitUtil.charArrayBuilder.Randomized.build(src=new char[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      int[] dst=new int[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
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
      char[] src;
      JunitUtil.charArrayBuilder.Randomized.build(src=new char[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      long[] dst=new long[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopycharlong() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      char[] src;
      JunitUtil.charArrayBuilder.Randomized.build(src=new char[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      long[] dst=new long[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
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
      char[] src;
      JunitUtil.charArrayBuilder.Randomized.build(src=new char[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      float[] dst=new float[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopycharfloat() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      char[] src;
      JunitUtil.charArrayBuilder.Randomized.build(src=new char[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      float[] dst=new float[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
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
      char[] src;
      JunitUtil.charArrayBuilder.Randomized.build(src=new char[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      double[] dst=new double[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopychardouble() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      char[] src;
      JunitUtil.charArrayBuilder.Randomized.build(src=new char[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      double[] dst=new double[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
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
      short[] src;
      JunitUtil.shortArrayBuilder.Randomized.build(src=new short[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      short[] dst=new short[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopyshortshort() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      short[] src;
      JunitUtil.shortArrayBuilder.Randomized.build(src=new short[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      short[] dst=new short[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
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
      short[] src;
      JunitUtil.shortArrayBuilder.Randomized.build(src=new short[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      int[] dst=new int[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopyshortint() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      short[] src;
      JunitUtil.shortArrayBuilder.Randomized.build(src=new short[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      int[] dst=new int[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
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
      short[] src;
      JunitUtil.shortArrayBuilder.Randomized.build(src=new short[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      long[] dst=new long[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopyshortlong() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      short[] src;
      JunitUtil.shortArrayBuilder.Randomized.build(src=new short[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      long[] dst=new long[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
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
      short[] src;
      JunitUtil.shortArrayBuilder.Randomized.build(src=new short[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      float[] dst=new float[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopyshortfloat() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      short[] src;
      JunitUtil.shortArrayBuilder.Randomized.build(src=new short[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      float[] dst=new float[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
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
      short[] src;
      JunitUtil.shortArrayBuilder.Randomized.build(src=new short[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      double[] dst=new double[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopyshortdouble() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      short[] src;
      JunitUtil.shortArrayBuilder.Randomized.build(src=new short[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      double[] dst=new double[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
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
      int[] src;
      JunitUtil.intArrayBuilder.Randomized.build(src=new int[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      int[] dst=new int[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopyintint() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      int[] src;
      JunitUtil.intArrayBuilder.Randomized.build(src=new int[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      int[] dst=new int[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
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
      int[] src;
      JunitUtil.intArrayBuilder.Randomized.build(src=new int[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      long[] dst=new long[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopyintlong() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      int[] src;
      JunitUtil.intArrayBuilder.Randomized.build(src=new int[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      long[] dst=new long[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
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
      int[] src;
      JunitUtil.intArrayBuilder.Randomized.build(src=new int[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      float[] dst=new float[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopyintfloat() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      int[] src;
      JunitUtil.intArrayBuilder.Randomized.build(src=new int[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      float[] dst=new float[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
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
      int[] src;
      JunitUtil.intArrayBuilder.Randomized.build(src=new int[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      double[] dst=new double[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopyintdouble() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      int[] src;
      JunitUtil.intArrayBuilder.Randomized.build(src=new int[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      double[] dst=new double[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
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
      long[] src;
      JunitUtil.longArrayBuilder.Randomized.build(src=new long[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      long[] dst=new long[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopylonglong() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      long[] src;
      JunitUtil.longArrayBuilder.Randomized.build(src=new long[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      long[] dst=new long[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
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
      long[] src;
      JunitUtil.longArrayBuilder.Randomized.build(src=new long[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      float[] dst=new float[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopylongfloat() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      long[] src;
      JunitUtil.longArrayBuilder.Randomized.build(src=new long[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      float[] dst=new float[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
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
      long[] src;
      JunitUtil.longArrayBuilder.Randomized.build(src=new long[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      double[] dst=new double[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopylongdouble() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      long[] src;
      JunitUtil.longArrayBuilder.Randomized.build(src=new long[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      double[] dst=new double[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
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
      float[] src;
      JunitUtil.floatArrayBuilder.Randomized.build(src=new float[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      float[] dst=new float[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopyfloatfloat() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      float[] src;
      JunitUtil.floatArrayBuilder.Randomized.build(src=new float[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      float[] dst=new float[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
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
      float[] src;
      JunitUtil.floatArrayBuilder.Randomized.build(src=new float[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      double[] dst=new double[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopyfloatdouble() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      float[] src;
      JunitUtil.floatArrayBuilder.Randomized.build(src=new float[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      double[] dst=new double[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
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
      double[] src;
      JunitUtil.doubleArrayBuilder.Randomized.build(src=new double[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      double[] dst=new double[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopydoubledouble() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      double[] src;
      JunitUtil.doubleArrayBuilder.Randomized.build(src=new double[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      double[] dst=new double[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
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
      String[] src;
      JunitUtil.StringArrayBuilder.Randomized.build(src=new String[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      String[] dst=new String[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+j]==dst[dstOffset+j]);
      }
    }
  }
  @Test
  public void testSemiCheckedReverseCopyStringString() throws Throwable
  {
    Random rand=new Random(0);
    for(int i=0;i<1000;++i)
    {
      int srcLength=rand.nextInt(1000);
      String[] src;
      JunitUtil.StringArrayBuilder.Randomized.build(src=new String[srcLength],rand);
      int dstLength=srcLength+rand.nextInt(1000);
      String[] dst=new String[dstLength];
      int copyLength=rand.nextInt(srcLength+1);
      int srcOffset=rand.nextInt(srcLength-copyLength+1);
      int dstOffset=rand.nextInt(dstLength-copyLength+1);
      ArrCopy.semicheckedReverseCopy(src,srcOffset,dst,dstOffset,copyLength);
      for(int j=0;j<copyLength;++j)
      {
        Assert.assertTrue(src[srcOffset+copyLength-1-j]==dst[dstOffset+j]);
      }
    }
  }
}
